//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: WorkflowActionItem.java,v $
// Revision 1.108  2014/04/17 20:50:28  wendy
// RCQ00302088 - readonly workflow action
//
// Revision 1.107  2011/11/10 20:45:32  wendy
// fix dump newline
//
// Revision 1.106  2009/05/18 12:09:43  wendy
// Support dereference for memory release
//
// Revision 1.105  2009/05/11 14:03:37  wendy
// Support turning off domain check for all actions
//
// Revision 1.104  2009/03/12 14:59:13  wendy
// Add methods for metaui access
//
// Revision 1.103  2008/03/14 20:34:02  wendy
// MN34992400 - default to not enforcing domain checks
//
// Revision 1.102  2008/01/21 13:37:45  wendy
// Added support to turn off domain check
//
// Revision 1.101  2007/08/03 11:25:45  wendy
// RQ0713072645-1 Make actions sensitive to the PROFILE's PDHDOMAINs
//
// Revision 1.100  2005/08/10 16:14:24  tony
// improved catalog viewer functionality.
//
// Revision 1.99  2005/08/03 17:09:45  tony
// added datasource logic for catalog mod
//
// Revision 1.98  2005/02/15 00:26:28  dave
// CR1124045246 - more work on it
//
// Revision 1.97  2005/02/15 00:02:20  dave
// CR1124045246 - making a place for any
// clear Change Group function to be triggered
//
// Revision 1.96  2005/01/18 21:46:52  dave
// more parm debug cleanup
//
// Revision 1.95  2004/07/15 17:28:59  joan
// add Single input
//
// Revision 1.94  2003/11/18 18:11:34  joan
// add SessionEntity
//
// Revision 1.93  2003/10/30 00:43:35  dave
// fixing all the profile references
//
// Revision 1.92  2003/10/30 00:05:13  dave
// more profile fixing
//
// Revision 1.91  2003/10/29 22:16:19  dave
// removed getProfile from many many new.. statements
// when the parent is not null
//
// Revision 1.90  2003/10/29 21:52:57  dave
// More trace for workflow
//
// Revision 1.89  2003/10/29 20:22:22  dave
// trace
//
// Revision 1.88  2003/10/29 00:36:20  dave
// post drop changes for next wave
//
// Revision 1.87  2003/10/27 23:42:49  dave
// trying a new remote exec
//
// Revision 1.86  2003/10/27 21:26:04  dave
// removed parent on workflow action item for Edit Action Item
//
// Revision 1.85  2003/09/24 21:39:33  gregg
// ensure profile.getValOn is not null when setting $now step
//
// Revision 1.84  2003/09/24 21:08:51  gregg
// implementing STEP for $now
//
// Revision 1.83  2003/09/23 22:59:41  dave
// trapping w/f null pointer
//
// Revision 1.82  2003/09/23 22:57:12  dave
// Debugging
//
// Revision 1.81  2003/09/23 22:16:10  dave
// First attempt at trapping rexec null pointer
//
// Revision 1.80  2003/09/23 19:46:21  dave
// adding the passing of the entity items in the wf constructor
//
// Revision 1.79  2003/08/18 21:18:53  dave
// isLocked Modifier
//
// Revision 1.78  2003/06/30 18:30:13  dave
// Addressing minor syntax
//
// Revision 1.77  2003/06/30 17:42:24  dave
// syntax
//
// Revision 1.76  2003/06/30 17:30:39  dave
// syntax
//
// Revision 1.75  2003/06/30 17:10:18  dave
// added group vs individual queue save
//
// Revision 1.74  2003/06/30 17:00:02  dave
// added the SavetoGroupQueue function
// so after a workflow.. we can auto post
// the any group queue.
//
// Revision 1.73  2003/04/08 02:58:06  dave
// commit()
//
// Revision 1.72  2003/03/25 21:43:51  joan
// null pointer
//
// Revision 1.71  2003/03/10 17:18:02  dave
// attempting to remove GBL7030 from the abstract Action Item
//
// Revision 1.70  2003/03/07 21:27:08  dave
// need another parenthasis
//
// Revision 1.69  2003/03/07 21:00:51  dave
// making interval used for GBL8116, GBL8114 to
// only pull an interval's worth of data from the Queue
// table to the Navigate table
//
// Revision 1.68  2003/02/05 01:13:24  gregg
// now use IntervalItem --> if set only update records which satisfy interval criteria
//
// Revision 1.67  2002/11/19 23:39:05  joan
// fix compile
//
// Revision 1.66  2002/11/19 23:26:54  joan
// fix hasLock method
//
// Revision 1.65  2002/11/19 18:39:43  joan
// fix compile
//
// Revision 1.64  2002/11/19 18:27:43  joan
// adjust lock, unlock
//
// Revision 1.63  2002/11/19 00:06:27  joan
// adjust isLocked method
//
// Revision 1.62  2002/11/15 19:06:19  dave
// indented nicely
//
// Revision 1.61  2002/10/30 23:50:32  dave
// more syntax
//
// Revision 1.60  2002/10/30 23:36:57  dave
// more throwing fixes
//
// Revision 1.59  2002/08/28 17:51:55  gregg
// more step updates
//
// Revision 1.58  2002/08/28 17:31:32  gregg
// update for steps list
//
// Revision 1.57  2002/08/28 01:04:40  gregg
// more updatePdhMeta
//
// Revision 1.56  2002/08/28 00:49:55  gregg
// fix expire
//
// Revision 1.55  2002/08/28 00:35:47  gregg
// some expires in updatePdhMeta
//
// Revision 1.54  2002/08/27 23:38:26  gregg
// more updatePdhMeta...
//
// Revision 1.53  2002/08/27 23:13:37  gregg
// rooting out nulls in updatePdhMeta
//
// Revision 1.52  2002/08/26 17:51:53  gregg
// first pass at guts of updatePdhMeta(db) method
//
// Revision 1.51  2002/08/23 21:59:54  gregg
// updatePdhMeta method throws MiddlewareException
//
// Revision 1.50  2002/08/23 21:29:44  gregg
// updatePdhMeta(Database,boolean) method
//
// Revision 1.49  2002/08/12 21:40:49  gregg
// set ALL attributes in clone constructor (e.g. setQueueType...)
//
// Revision 1.48  2002/08/05 19:59:16  gregg
// use Queue object's updateTpPdh() method for queueTypeExecuteAction
//
// Revision 1.47  2002/08/01 23:16:13  gregg
// typo in debug
//
// Revision 1.46  2002/08/01 21:48:28  gregg
// debug stmt for gbl7522
//
// Revision 1.45  2002/08/01 00:53:30  gregg
// added in QUEUE logic
//
// Revision 1.44  2002/06/27 17:43:22  joan
// fix bugs
//
// Revision 1.43  2002/06/05 17:51:58  joan
// fix ActionItem
//
// Revision 1.42  2002/05/29 19:47:14  gregg
// in constructor: changed 'Flag' attribute to 'Update'
//
// Revision 1.41  2002/05/28 17:29:08  joan
// change some get methods from public to protected
//
// Revision 1.40  2002/05/28 17:27:21  joan
// adjust code to set lock and flag according to meta
//
// Revision 1.39  2002/05/24 18:23:25  joan
// add logic to set locking entities optional and use new db.update method
//
// Revision 1.38  2002/05/17 20:37:14  gregg
// getEntityItems method added
//
// Revision 1.37  2002/05/16 22:28:46  joan
// add logic to prevent deactivating status in short hand way
//
// Revision 1.36  2002/05/16 21:55:17  joan
// working on turning off
//
// Revision 1.35  2002/05/16 20:20:16  joan
// working on setting off attributes
//
// Revision 1.34  2002/05/16 15:28:58  joan
// rework on workflow
//
// Revision 1.33  2002/05/15 23:18:41  joan
// work on turning off attribute
//
// Revision 1.32  2002/05/14 18:07:43  joan
// working on LockActionItem
//
// Revision 1.31  2002/05/10 21:06:19  joan
// compiling errors
//
// Revision 1.30  2002/05/10 15:31:33  joan
// add commit()
//
// Revision 1.29  2002/05/09 23:13:13  joan
// call rollback when there's exception on commit
//
// Revision 1.28  2002/05/09 22:43:27  joan
// fix throwing exceptions
//
// Revision 1.27  2002/05/09 21:45:20  joan
// working on throw exception
//
// Revision 1.26  2002/05/09 20:40:42  joan
// fix throw exceptions
//
// Revision 1.25  2002/05/09 20:27:21  joan
// working on throwing exception
//
// Revision 1.24  2002/05/09 17:01:05  joan
// add logic to put object in flag attribute
//
// Revision 1.23  2002/05/08 23:51:07  joan
// fixing bugs
//
// Revision 1.22  2002/05/08 23:38:01  joan
// debug
//
// Revision 1.21  2002/05/08 23:19:37  joan
// debug
//
// Revision 1.20  2002/05/08 23:03:49  joan
// debug
//
// Revision 1.19  2002/05/08 22:51:08  joan
// fix errors
//
// Revision 1.18  2002/05/08 22:14:23  joan
// debug
//
// Revision 1.17  2002/05/08 21:34:17  joan
// fix error
//
// Revision 1.16  2002/05/08 21:23:06  joan
// fixing error
//
// Revision 1.15  2002/05/08 20:55:13  joan
// debug errors
//
// Revision 1.14  2002/05/08 20:13:29  joan
// fixing setEntityItem
//
// Revision 1.13  2002/05/08 18:16:15  joan
// error
//
// Revision 1.12  2002/05/08 18:05:11  joan
// debugging
//
// Revision 1.11  2002/05/08 17:45:14  joan
// debug
//
// Revision 1.10  2002/05/08 16:48:40  joan
// compile error
//
// Revision 1.9  2002/05/08 16:22:05  joan
// working on Workflow
//
// Revision 1.8  2002/05/07 22:36:33  joan
// fixing
//
// Revision 1.7  2002/05/06 23:41:23  joan
// fixing error
//
// Revision 1.6  2002/05/06 23:21:28  joan
// fixing errors
//
// Revision 1.5  2002/05/06 23:06:56  joan
// add some debug statements
//
// Revision 1.4  2002/05/06 20:58:52  joan
// add logic for WorkflowActionItem
//
// Revision 1.3  2002/04/22 20:23:52  dave
// syntax
//
// Revision 1.2  2002/04/22 20:06:39  dave
// syntax errors
//
// Revision 1.1  2002/04/22 19:44:47  dave
// new object
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.StringTokenizer;
import java.util.Vector;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.T;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.transactions.OPICMList;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.objects.LongText;
import COM.ibm.opicmpdh.objects.MultipleFlag;
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.opicmpdh.objects.Text;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;

/**
 * WorkflowActionItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WorkflowActionItem extends EANActionItem {

    static final long serialVersionUID = 20011106L;

    private String m_strEntityType = null; // Holds the EntityType we are updating
    /**
     * FIELD
     */
    protected EANList m_el = new EANList();
    private OPICMList m_sl = new OPICMList();
    private EntityGroup m_eg = null;
    private EditActionItem m_eai = null;
    private boolean m_bLongWay = false;
    private boolean m_bLockEntity = false;
    private boolean m_bReadOnly = false; //RCQ00302088
    private boolean m_bAttributeOnly = false;
    private String m_strQueueSource = null;
    private boolean m_bQueueType = false;
    private int m_iStatusValue = -1;
    /**
     * FIELD
     */
    public static final String SETON = "ON";
    /**
     * FIELD
     */
    public static final String SETOFF = "OFF";
    private IntervalItem m_intervalItem = null;
    /**
     * FIELD
     */
    protected boolean m_bSaveToQueue = false;

    /**
     * FIELD
     */
    protected boolean m_bSaveToGroupQueue = false;
    /**
     * FIELD
     */
    protected String m_strSaveToQueue = "";

    public void dereference(){
    	super.dereference();
    	m_strEntityType = null;
    	if (m_el !=null){
    		/*this may be shared
    		for (int i=0; i<m_el.size(); i++){
    			EntityItem mt = (EntityItem) m_el.getAt(i);
    			if (mt != null){
    				mt.dereference();
    			}
    		}*/
    		m_el.clear();
    		m_el = null;
    	}
    	if (m_sl != null){
    		m_sl.clear();
    		m_sl = null;
    	}
    	if (m_eg!= null){
    		m_eg.dereference();
    		m_eg = null;
    	}
       
    	if (m_eai!=null){
    		m_eai.dereference();
    		m_eai = null;
    	}
        m_strQueueSource = null;
        if (m_intervalItem!= null){
        	m_intervalItem.dereference();
        	m_intervalItem = null;
        }
        
        m_strSaveToQueue = null;
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
        return "$Id: WorkflowActionItem.java,v 1.108 2014/04/17 20:50:28 wendy Exp $";
    }

    /**
     * WorkflowActionItem
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public WorkflowActionItem(EANMetaFoundation _mf, WorkflowActionItem _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
        setEntityType(_ai.getEntityType());
        setStepsList(_ai.getStepsList());
        setEntityGroup(_ai.getEntityGroup());
        setEditActionItem(_ai.getEditActionItem());
        setLongWay(_ai.useLongWay());
        setQueueType(_ai.isQueueType());
        setQueueSource(_ai.getQueueSource());
        setAttributeOnly(_ai.updateAttributeOnly());
        setStatusValue(_ai.getStatusValue());
        m_bSaveToGroupQueue = _ai.m_bSaveToGroupQueue;
        m_bSaveToQueue = _ai.m_bSaveToQueue;
        m_bReadOnly = _ai.m_bReadOnly; //RCQ00302088
        m_strSaveToQueue = _ai.m_strSaveToQueue;
        // must pass this on
        m_el = _ai.m_el;
    }
    /**
     * This represents a Workflow Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strActionItemKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public WorkflowActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _strActionItemKey);

        // Lets go get the information pertinent to the Workflow Action Item

        try {
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs;

            Profile prof = getProfile();

            try {
                rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
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
            //LINKTYPE:	 Attribute/Attribute
			//LINKTYPE1: _strActionItemKey
			//LINKTYPE2: TYPE
			//LINKCODE:	 ReadOnly
			//LINKVALUE: Y - not used
            
            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strType = rdrs.getColumn(ii, 0);
                String strCode = rdrs.getColumn(ii, 1);
                String strValue = rdrs.getColumn(ii, 2);

                _db.debug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");

                // Collect the attributes for Set, order and action
                if (strType.equals("STEP")) {
                    m_sl.put(strCode, strValue);
                } else if (strType.equals("TYPE") && strCode.equals("EntityType")) {
                    setEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("Update")) {
                    setAttributeOnly((strValue.equals("Attribute")) ? true : false);
                } else if (strType.equals("TYPE") && strCode.equals("Lock")) {
                    setLockEntity((strValue.charAt(0) == 'Y') ? true : false);
                } else if (strType.equals("TYPE") && strCode.equals("EditAction")) {
                    setEditActionItem(new EditActionItem(null, _db, _prof, strValue));
                    setLongWay(true);
                } else if (strType.equals("TYPE") && strCode.equals("QUEUESOURCE")) {
                    setQueueType(true);
                    setQueueSource(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SaveToQueue")) {
                    m_bSaveToQueue = true;
                    m_strSaveToQueue = strValue;
                } else if (strType.equals("TYPE") && strCode.equals("SaveToGroupQueue")) {
                    m_bSaveToQueue = true;
                    m_bSaveToGroupQueue = true;
                    m_strSaveToQueue = strValue;
                } else if (strType.equals("TYPE") && strCode.equals("STATUS")) {
                    try {
                        int iValue = Integer.parseInt(strValue);
                        setStatusValue(iValue);
                    } catch (NumberFormatException nfe) {
                        _db.debug(D.EBUG_ERR, "WorkflowActionItem: Invalid Status value (" + strValue + ") - must be an Integer!" + ":" + nfe.getMessage());
                    }
                } else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
                    super.setAssociatedEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SingleInput")) {
                    setSingleInput(true);
                } else if (strType.equals("TYPE") && strCode.equals("ClearTargetChangeGroup")) {
                    setClearTargetChangeGroup(true);
				} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
					setDataSource(strValue);											//catalog enhancement
				} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
					setAdditionalParms(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("DomainCheck")) {
				    setDomainCheck(strValue.equals("Y")); //RQ0713072645 - must turn it on
                } else if (strType.equals("TYPE") && strCode.equalsIgnoreCase("ReadOnly")) { //RCQ00302088
					m_bReadOnly = true;
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
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("WorkflowActionItem:" + getKey() + ":desc:" + getLongDescription());
        strbResult.append(":purpose:" + getPurpose());
        strbResult.append(":entitytype:" + getEntityType() + "\n");
        return strbResult.toString();
    }

    /**
     * (non-Javadoc)
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return "Workflow";
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
     * setEntityType
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setEntityType(String _str) {
        m_strEntityType = _str;
    }

    /**
     * getEntityGroup
     *
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getEntityGroup() {
        return m_eg;
    }

    /**
     * getStepsList
     *
     * @return
     *  @author David Bigelow
     */
    public OPICMList getStepsList() {
        return m_sl;
    }

    /**
     * setStepsList
     *
     * @param _el
     *  @author David Bigelow
     */
    protected void setStepsList(OPICMList _el) {
        m_sl = _el;
    }

    /**
     * setEntityGroup
     *
     * @param _eg
     *  @author David Bigelow
     */
    protected void setEntityGroup(EntityGroup _eg) {
        m_eg = _eg;
    }

    /**
     * getEditActionItem
     *
     * @return
     *  @author David Bigelow
     */
    public EditActionItem getEditActionItem() {
        return m_eai;
    }

    /**
     * setEditActionItem
     *
     * @param _eai
     *  @author David Bigelow
     */
    protected void setEditActionItem(EditActionItem _eai) {
        m_eai = _eai;
    }

    /**
     * useLongWay
     *
     * @return
     *  @author David Bigelow
     */
    public boolean useLongWay() {
        return m_bLongWay;
    }

    /**
     * setLongWay
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setLongWay(boolean _b) {
        m_bLongWay = _b;
    }

    /**
     * getLockEntity
     *
     * @return
     *  @author David Bigelow
     */
    public boolean getLockEntity() {
        return m_bLockEntity;
    }

    /*
    * b = true: lock entities before updating them in short way execution
    * b = false: don't need to lock entities
    */
    /**
     * setLockEntity
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setLockEntity(boolean _b) {
        m_bLockEntity = _b;
    }
    /*********
     * meta ui must update action
     */
    public void setActionClass(){
    	setActionClass("Workflow");
    }
    public void updateAction(String strEntityType,boolean bQueueType, String strQueueSource,
    		int iStatusValue, boolean lock, boolean attrOnly, EditActionItem eai, 
    		boolean longway, OPICMList steps)
    {
    	setEntityType(strEntityType);
    	setQueueType(bQueueType);
    	setQueueSource(strQueueSource);
    	setStatusValue(iStatusValue);
    	setLockEntity(lock);
    	setAttributeOnly(attrOnly);
    	setEditActionItem(eai);
    	setLongWay(longway);
    	setStepsList(steps);
    }
    /**
     * updateAttributeOnly
     *
     * @return
     *  @author David Bigelow
     */
    public boolean updateAttributeOnly() {
        return m_bAttributeOnly;
    }

    /**
     * setAttributeOnly
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setAttributeOnly(boolean _b) {
        m_bAttributeOnly = _b;
    }

    /**
     * setQueueSource
     *
     * @param _s
     *  @author David Bigelow
     */
    protected void setQueueSource(String _s) {
        m_strQueueSource = _s;
    }

    /**
     * setStatusValue
     *
     * @param _i
     *  @author David Bigelow
     */
    protected void setStatusValue(int _i) {
        m_iStatusValue = _i;
    }

    /**
     * getQueueSource
     *
     * @return
     *  @author David Bigelow
     */
    public String getQueueSource() {
        return m_strQueueSource;
    }

    /**
     * getStatusValue
     *
     * @return
     *  @author David Bigelow
     */
    public int getStatusValue() {
        return m_iStatusValue;
    }

    /**
     * setQueueType
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setQueueType(boolean _b) {
        m_bQueueType = _b;
    }

    /**
     * Is this Workflow updating the QUEUE table?
     *
     * @return boolean
     */
    public boolean isQueueType() {
        return m_bQueueType;
    }

    /*
    * This is the array of EntityItems that will be Reported Against
    * It will search any uplinks or downlinks if the type of
    * entity passed was in a Group that is a relator.. and a
    * native match could not  be found
    */
    /**
     * setEntityItems
     *
     * @param _aei
     *  @author David Bigelow
     */
    public void setEntityItems(EntityItem[] _aei) {
        resetEntityItems();
        // loop through and place the entityItem in the EANList
        for (int ii = 0; ii < _aei.length; ii++) {
            EntityItem ei = _aei[ii];
            EntityItem processedEI = null;
            boolean bMatch = false;
            if (!ei.getEntityType().equals(getEntityType())) {
                EntityGroup eg = (EntityGroup) ei.getParent();
                // It cannot be added to the list
                if (eg == null) {
                    continue;
                }
                if (eg.isRelator() || eg.isAssoc()) {
                    //check the child entity item
                    EntityItem eic = (EntityItem) ei.getDownLink(0);
                    if (!eic.getEntityType().equals(getEntityType())) {
                        //check the parent entity item
                        EntityItem eip = (EntityItem) ei.getUpLink(0);
                        if (!eip.getEntityType().equals(getEntityType())) {
                            bMatch = false;
                        } else {
                            processedEI = eip;
                            bMatch = true;
                        }
                    } else {
                        processedEI = eic;
                        bMatch = true;
                    }
                }
            } else {
                processedEI = ei;
                bMatch = true;
            }

            // If you found a match on entitytype.. please put it on the list
            if (bMatch) {
                if (useLongWay()) {
                    // This is needed for creating the EntityList
                    m_el.put(ei);
                } else {
                    m_el.put(processedEI);
                }
            } else {
                System.err.println("WorkFlowActionItem.setEntityItems: WARNING No match for entityItem and EntityType in action:" + getEntityType() + ":" + ei.getKey());
            }
        }
    }

    /**
     * getEntityItems
     *
     * @return
     *  @author David Bigelow
     */
    public EANList getEntityItems() {
        return m_el;
    }

    /**
     * resetEntityItems
     *
     *  @author David Bigelow
     */
    public void resetEntityItems() {
        m_el = new EANList();
    }

    /**
     * resetStepList
     *
     *  @author David Bigelow
     */
    public void resetStepList() {
        m_sl = new OPICMList();
    }

    private EntityItem[] getEntityItemArray() {
        int size = m_el.size();
        EntityItem[] aeiReturn = new EntityItem[size];
        for (int i = 0; i < size; i++) {
            EntityItem ei = (EntityItem) m_el.getAt(i);
            aeiReturn[i] = ei;
        }
        return aeiReturn;
    }

    private RowSelectableTable getRowSelectableTable(EntityList _eList) {
        int ii = _eList.getEntityGroupCount();
        for (int i = 0; i < ii; ++i) {
            EntityGroup eg = _eList.getEntityGroup(i);
            if (eg.isDisplayable()) {
                return eg.getEntityGroupTable();
            }
        }
        return null;
    }

    /**
     * exec
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.LockException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.eannounce.objects.WorkflowException
     *  @author David Bigelow
     */
    public void exec(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, LockException, MiddlewareShutdownInProgressException, RemoteException, WorkflowException {
        _db.executeAction(_prof, this);
        resetEntityItems();
    }

    /**
     * rexec
     *
     * @param _rdi
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.LockException
     * @throws COM.ibm.eannounce.objects.WorkflowException
     *  @author David Bigelow
     */
    public void rexec(RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException, LockException, WorkflowException {
		EntityList.checkDomain(_prof,this,m_el); //RQ0713072645

        EANFoundation ef = getParent();
        T.est(_rdi != null, "you are passing a null remote connect to me");
        //if use EditActionItem, we should not strip EntityItems, because their down and up links
        // are needed for getEntityList

        if (!useLongWay()) {
            EANList el = new EANList();

            // This guy preps the information for RDI since this is a remote call we need to strip out
            // any dependencies

            for (int ii = 0; ii < m_el.size(); ii++) {
                EntityItem ei = (EntityItem) m_el.getAt(ii);
                el.put(new EntityItem(null, _prof, ei.getEntityType(), ei.getEntityID()));
            }

            m_el = el;

        } else if (isQueueType()) {
            EANList el = new EANList();

            // This guy preps the information for RDI since this is a remote call we need to strip out
            // any dependencies

            for (int ii = 0; ii < m_el.size(); ii++) {
                EntityItem ei = (EntityItem) m_el.getAt(ii);
                el.put(new EntityItem(null, _prof, ei.getEntityType(), ei.getEntityID()));
            }

            m_el = el;
        }

        setParent(null);
        // _rdi.executeAction(_prof, new WorkflowActionItem(null, this));
        _rdi.executeAction(_prof, this);
        setParent(ef);
        resetEntityItems();
    }

    private void longWayExecuteAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, WorkflowException {
        String strTraceBase = " WorkflowActionItem longWayExecuteAction method";

        WorkflowException we = new WorkflowException();
        LockList lockL = new LockList(_prof);
        EntityItem[] aei = getEntityItemArray();
        EntityList el = EntityList.getEntityList(_db, _prof, getEditActionItem(), aei);
        EntityItem lockOwnerEI = new EntityItem(null, _prof, Profile.OPWG_TYPE, _prof.getOPWGID());
        RowSelectableTable rst = getRowSelectableTable(el);

        if (rst == null) {
            _db.debug(D.EBUG_DETAIL, strTraceBase + " the RowSelectableTable is null");
            return;
        }

        for (int r = 0; r < rst.getRowCount(); r++) {
            EANFoundation ean = rst.getRow(r);

            STEPSLOOP : for (int j = 0; j < m_sl.size(); j++) {
                String str = (String) m_sl.getAt(j);
                StringTokenizer st = new StringTokenizer(str, ":");
                String strSet = st.nextToken();
                String strAttributeCode = st.nextToken();
                String strAttributeValue = st.nextToken();

                int c = rst.getColumnIndex(getEntityType() + ":" + strAttributeCode);

                if (c >= 0 && c < rst.getColumnCount()) {
                    EANFoundation eaf = rst.getEANObject(r, c);

                    if (rst.isLocked(r, c, null, _db, lockL, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL, "NOOP", true)) {
                        if (eaf instanceof EANFlagAttribute) {
                            EANFlagAttribute fa = (EANFlagAttribute) eaf;
                            MetaFlag[] amf = (MetaFlag[]) fa.get();

                            for (int f = 0; f < amf.length; f++) {
                                String flagCode = amf[f].getFlagCode();
                                if (flagCode.equals(strAttributeValue)) {
                                    amf[f].setSelected(true);
                                    break;
                                }
                            }
                            try {
                                if (strSet.equalsIgnoreCase(SETON)) {
                                    _db.debug(D.EBUG_DETAIL, strTraceBase + " updating " + eaf.getKey());
                                    rst.put(r, c, amf);
                                } else if (strSet.equalsIgnoreCase(SETOFF)) {
                                    _db.debug(D.EBUG_DETAIL, strTraceBase + " deactivating " + eaf.getKey());
                                    rst.put(r, c, null);
                                }
                            } catch (EANBusinessRuleException bre) {
                                we.add(ean, bre.toString());
                            }
                        } else if (eaf instanceof EANTextAttribute) {
                            try {
                                if (strSet.equalsIgnoreCase(SETON)) {
                                    _db.debug(D.EBUG_DETAIL, strTraceBase + " updating " + eaf.getKey());
                                    // GAB 092403 - if token == $now
                                    // insert the current date as a text record in the form of YYYY-MM-DD
                                    if (strAttributeValue.length() > 0 && strAttributeValue.charAt(0) == '$') {
                                        if (strAttributeValue.substring(1, strAttributeValue.trim().length()).equalsIgnoreCase("now")) {
                                            if (_prof.getValOn() == null) {
                                                _db.debug(D.EBUG_ERR, "ERROR setting $now step value in executeAction():Profile.getValOn() is null!!");
                                                continue STEPSLOOP;
                                            }
                                            strAttributeValue = _prof.getValOn().substring(0, 10).replace('.', '-');
                                        }
                                    }
                                    // end GAB 092403
                                    rst.put(r, c, strAttributeValue);
                                } else if (strSet.equalsIgnoreCase(SETOFF)) {
                                    _db.debug(D.EBUG_DETAIL, strTraceBase + " deactivating " + eaf.getKey());
                                    rst.put(r, c, null);
                                }
                            } catch (EANBusinessRuleException bre) {
                                we.add(ean, bre.toString());
                            }
                        }
                    } else {
                        we.add(ean, " The entity is locked.. please try again later");
                    }
                }
            }

            try {
                rst.commit(_db);
            } catch (EANBusinessRuleException bre) {
                rst.rollback(r);
                we.add(ean, bre.toString());
            } catch (RemoteException bre) {
                rst.rollback(r);
                we.add(ean, bre.toString());
            }
        }
        rst.unlock(null, _db, lockL, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL);

        if (we.getErrorCount() > 0) {
            throw we;
        }
    }

    private void shortWayExecuteAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, WorkflowException {
        String strTraceBase = " WorkflowActionItem shortWayExecuteAction method";
        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        ReturnStatus returnStatus = new ReturnStatus(-1);

        // Placeholders for dates
        String strNow = null;
        String strForever = null;

        ControlBlock cbOn = null;
        ControlBlock cbOff = null;
        EntityItem lockOwnerEI = null;

        // Pull some profile info...
        int iOPWGID = _prof.getOPWGID();
        int iTranID = _prof.getTranID();
        String strEnterprise = _prof.getEnterprise();
        int iSessionID = _prof.getSessionID();

        LockList lockL = new LockList(_prof);
        WorkflowException we = new WorkflowException();
        Vector vctReturnEntityKeys = new Vector();

        try {
            DatePackage dpNow = _db.getDates();
            strNow = dpNow.getNow();
            strForever = dpNow.getForever();

            if (m_eg == null) {
                setEntityGroup(new EntityGroup(null, _db, _prof, getEntityType(), "Edit"));
            }

            cbOn = new ControlBlock(strNow, strForever, strNow, strForever, iOPWGID, iTranID);
            cbOff = new ControlBlock(strNow, strNow, strNow, strNow, iOPWGID, iTranID);
            lockOwnerEI = new EntityItem(null, _prof, Profile.OPWG_TYPE, _prof.getOPWGID());

            _db.debug(D.EBUG_DETAIL, strTraceBase);
            _db.test(m_el.size() > 0, "WorkflowActionItem.shortWayExecute(). 104035 No Items are in the m_el to update.");

            _db.debug(D.EBUG_DETAIL, strTraceBase + " ready to lock entity items and updating them");
            for (int i = 0; i < m_el.size(); i++) {
                EntityItem ei = (EntityItem) m_el.getAt(i);

                // try to lock the entity item
                boolean bEntityLocked = true;
                if (m_bLockEntity) {
                    bEntityLocked = ei.isLocked(null, _db, lockL, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL, "NOOP2", true);
                }

                if (bEntityLocked) {
                    String strEntityType = ei.getEntityType();
                    int iEntityID = ei.getEntityID();
                    ReturnEntityKey rek = new ReturnEntityKey(strEntityType, iEntityID, true);
                    Vector vctAtts = new Vector();

                    _db.debug(D.EBUG_DETAIL, strTraceBase + " creating entity: " + strEnterprise + ":" + strEntityType + ":" + iEntityID);


                    STEPSLOOP : for (int j = 0; j < m_sl.size(); j++) {
                        String strAttributeClass = null;
                        Text t = null;
                        MultipleFlag mf = null;
                        LongText lt = null;
                        SingleFlag sf = null;

                        String str = (String) m_sl.getAt(j);
                        StringTokenizer st = new StringTokenizer(str, ":");
                        String strSet = st.nextToken();
                        String strAttributeCode = st.nextToken();
                        String strAttributeValue = st.nextToken();
                        EANMetaAttribute ma = m_eg.getMetaAttribute(strAttributeCode);
                        if (ma == null) {
                            System.out.println("Meta Attribute is null for " + strAttributeCode);
                            continue;
                        }
                        strAttributeClass = ma.getAttributeType();
                        switch (strAttributeClass.charAt(0)) {
                        case 'T' :
                        case 'I' :
                            t = null;
                            if (strSet.equalsIgnoreCase(SETON)) {
                                _db.debug(D.EBUG_DETAIL, strTraceBase + " creating text attribute: " + strEnterprise + ":" + strEntityType + ":" + iEntityID + ":" + strAttributeCode + ":" + strAttributeValue);
                                // GAB 092403 - if token == $now
                                // insert the current date as a text record in the form of YYYY-MM-DD
                                if (strAttributeValue.length() > 0 && strAttributeValue.charAt(0) == '$') {
                                    if (strAttributeValue.substring(1, strAttributeValue.trim().length()).equalsIgnoreCase("now")) {
                                        if (_prof.getValOn() == null) {
                                            _db.debug(D.EBUG_ERR, "ERROR setting $now step value in executeAction():Profile.getValOn() is null!!");
                                            continue STEPSLOOP;
                                        }
                                        strAttributeValue = _prof.getValOn().substring(0, 10).replace('.', '-');
                                    }
                                }
                                // end GAB 092403

                                // if token == $SessionEntity
                                // insert the tag entity as a text record in the form of EntityType:EntityID
                                if (strAttributeValue.length() > 0 && strAttributeValue.charAt(0) == '$') {
                                    if (strAttributeValue.substring(1, strAttributeValue.trim().length()).equalsIgnoreCase("SessionEntity")) {
                                        try {
                                            rs = _db.callGBL7546(returnStatus, strEnterprise, iSessionID, iOPWGID);
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
                                        for (int ii = 0; ii < rdrs.size(); ii++) {
                                            String strSessionEntityType = rdrs.getColumn(ii, 0).trim();
                                            int iSessionEntityID = rdrs.getColumnInt(ii, 1);
                                            _db.debug(D.EBUG_SPEW, "gbl7546:answer:" + strSessionEntityType + ":" + iSessionEntityID);
                                            strAttributeValue = strSessionEntityType + ":" + iSessionEntityID;
                                            break;
                                        }

                                        if (strAttributeValue == null) {
                                            _db.debug(D.EBUG_ERR, "ERROR setting $SessionEntity step value in executeAction():Profile.getValOn() is null!!");
                                            continue STEPSLOOP;
                                        }

                                    }
                                }

                                t = new Text(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, 1, cbOn);
                            } else if (strSet.equalsIgnoreCase(SETOFF)) {
                                _db.debug(D.EBUG_DETAIL, strTraceBase + " deactivating text attribute: " + strEnterprise + ":" + strEntityType + ":" + iEntityID + ":" + strAttributeCode + ":" + strAttributeValue);
                                t = new Text(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, 1, cbOff);
                            }
                            if (t != null) {
                                vctAtts.addElement(t);
                            }
                            break;
                        case 'F' :
                            mf = null;
                            if (strSet.equalsIgnoreCase(SETON)) {
                                _db.debug(D.EBUG_DETAIL, strTraceBase + " creating multiple flag attribute: " + strEnterprise + ":" + strEntityType + ":" + iEntityID + ":" + strAttributeCode + ":" + strAttributeValue);
                                mf = new MultipleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, 1, cbOn);
                            } else if (strSet.equalsIgnoreCase(SETOFF)) {
                                _db.debug(D.EBUG_DETAIL, strTraceBase + " deactivating multiple flag attribute: " + strEnterprise + ":" + strEntityType + ":" + iEntityID + ":" + strAttributeCode + ":" + strAttributeValue);
                                mf = new MultipleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, 1, cbOff);
                            }
                            if (mf != null) {
                                vctAtts.addElement(mf);
                            }
                            break;
                        case 'U' :
                        case 'S' :
                        case 'A' :
                            sf = null;
                            if (strSet.equalsIgnoreCase(SETON)) {
                                _db.debug(D.EBUG_DETAIL, strTraceBase + " creating single flag attribute: " + strEnterprise + ":" + strEntityType + ":" + iEntityID + ":" + strAttributeCode + ":" + strAttributeValue);
                                sf = new SingleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, 1, cbOn);
                            } else if (strSet.equalsIgnoreCase(SETOFF)) {
                                if (strAttributeClass.charAt(0) != 'S') {
                                    _db.debug(D.EBUG_DETAIL, strTraceBase + " deactivating single flag attribute: " + strEnterprise + ":" + strEntityType + ":" + iEntityID + ":" + strAttributeCode + ":" + strAttributeValue);
                                    sf = new SingleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, 1, cbOff);
                                } else {
                                    _db.debug(D.EBUG_DETAIL, strTraceBase + " can't deactivate status: " + strEnterprise + ":" + strEntityType + ":" + iEntityID + ":" + strAttributeCode);
                                }
                            }
                            if (sf != null) {
                                vctAtts.addElement(sf);
                            }
                            break;
                        case 'L' :
                            lt = null;
                            if (strSet.equalsIgnoreCase(SETON)) {
                                _db.debug(D.EBUG_DETAIL, strTraceBase + " creating longtext attribute: " + strEnterprise + ":" + strEntityType + ":" + iEntityID + ":" + strAttributeCode + ":" + strAttributeValue);
                                lt = new LongText(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, 1, cbOn);
                            } else if (strSet.equalsIgnoreCase(SETOFF)) {
                                _db.debug(D.EBUG_DETAIL, strTraceBase + " deactivating longtext attribute: " + strEnterprise + ":" + strEntityType + ":" + iEntityID + ":" + strAttributeCode + ":" + strAttributeValue);
                                lt = new LongText(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, 1, cbOff);
                            }
                            if (lt != null) {
                                vctAtts.addElement(lt);
                            }
                            break;
                        default :
                            _db.debug(D.EBUG_ERR, "**No Workflow home for AttributeClass" + strAttributeClass + ":");
                        }
                    }

                    rek.m_vctAttributes = vctAtts;
                    vctReturnEntityKeys.addElement(rek);

                } else {
                    we.add(ei, " The entity is locked.. please try again later");
                }
            }

            //updating entity items
            _db.update(_prof, vctReturnEntityKeys, false, m_bAttributeOnly);
            _db.commit();

            // O.K. if we have to save to any group queue's now is the time...

            if (m_bSaveToQueue) {
                _db.debug(D.EBUG_DETAIL, strTraceBase + " Adding to Group Queue:" + m_strSaveToQueue);
                for (int i = 0; i < m_el.size(); i++) {
                    EntityItem ei = (EntityItem) m_el.getAt(i);
                    _db.callGBL8009(new ReturnStatus(-1), strEnterprise, (m_bSaveToGroupQueue ? 0 : _prof.getOPWGID()), 0, m_strSaveToQueue, 0, ei.getEntityType(), ei.getEntityID(), "1980-01-01-00.00.00.000000", "9999-12-31-00.00.00.000000");
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
            }

            _db.debug(D.EBUG_DETAIL, strTraceBase + " ready to unlock entity items");
            //unlock entity items
            if (m_bLockEntity) {
                for (int i = 0; i < m_el.size(); i++) {
                    EntityItem ei = (EntityItem) m_el.getAt(i);
                    if (ei.hasLock(lockOwnerEI, _prof)) {
                        ei.unlock(null, _db, lockL, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL);
                    }
                }
            }

            if (we.getErrorCount() > 0) {
                throw we;
            }
        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * If specified, the WorkflowAction will update a record in the QUEUE table
     */
    private void queueTypeExecuteAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, WorkflowException {
        String strEnterprise = _prof.getEnterprise();
        String strQueue = getQueueSource();
        String strEntityType = getEntityType();
        int iStatus = getStatusValue();
        String strPullDate = _db.getDates().getNow();
        //push date is inconsequential here...it's just used to satisfy Queue's constructor
        String strPushDate = _db.getDates().getEpoch();
        try {
            for (int i = 0; i < getEntityItems().size(); i++) {
                int iEntityID = 0;
                IntervalItem intervalItem = null;
                QueueItem q = null;

                EntityItem ei = (EntityItem) getEntityItems().getAt(i);
                if (!ei.getEntityType().equals(strEntityType)) {
                    continue;
                }

                iEntityID = ei.getEntityID();
                //use existing IntervalItem if it exists --> else use an IntervalItem which encompasses ALL dates
                intervalItem = (hasIntervalItem() ? getIntervalItem() : new IntervalItem(null, _prof, strQueue, _db.getDates().getEpoch(), _db.getDates().getForever()));
                q = new QueueItem(null, _prof, intervalItem, iStatus, strEntityType, iEntityID, strPushDate, strPullDate);
                q.updateToPdh(_db);

                // O.K. if we have to save to any group queue's now is the time...

                if (m_bSaveToQueue) {
                    _db.debug(D.EBUG_DETAIL, "WorkflowActionItem.queueTypeExecuteAction() method:" + " Adding to Group Queue:" + m_strSaveToQueue);
                    _db.callGBL8009(new ReturnStatus(-1), strEnterprise, (m_bSaveToGroupQueue ? 0 : _prof.getOPWGID()), 0, m_strSaveToQueue, 0, ei.getEntityType(), ei.getEntityID(), "1980-01-01-00.00.00.000000", "9999-12-31-00.00.00.000000");
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
            }
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, "WorkflowActionItem.queueTypeExecuteAction() method:" + exc.toString());
        }
    }

    /**
     * executeAction
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.WorkflowException
     *  @author David Bigelow
     */
    public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, WorkflowException {
		EntityList.checkDomain(_prof,this,m_el); //RQ0713072645

        if (isQueueType()) {
            queueTypeExecuteAction(_db, _prof);
        } else if (useLongWay()) {
            longWayExecuteAction(_db, _prof);
        } else {
            shortWayExecuteAction(_db, _prof);
        }
    }
    
    /**
     * RCQ00302088
     * A readonly role needs to execute some workflow actions, tag the ones available
     * @return
     */
    public boolean isReadOnly(){
    	return m_bReadOnly;
    }

    /**
     * setIntervalItem
     *
     * @param _intervalItem
     *  @author David Bigelow
     */
    public void setIntervalItem(IntervalItem _intervalItem) {
        m_intervalItem = _intervalItem;
    }

    private IntervalItem getIntervalItem() {
        return m_intervalItem;
    }

    private boolean hasIntervalItem() {
        return getIntervalItem() != null;
    }

    ////////////// UPDATE META ///////////

    /**
     * (non-Javadoc)
     * updatePdhMeta
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#updatePdhMeta(COM.ibm.opicmpdh.middleware.Database, boolean)
     */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {

        OPICMList stepsList_db = null;
        OPICMList stepsList = null;

        String strQueueSource_db = null;
        String strQueueSource = null;
        String strEditAction = null;
        String strEditAction_db = null;

        try {

            //lets get a fresh object from the database
            WorkflowActionItem wf_db = new WorkflowActionItem(null, _db, getProfile(), getActionItemKey());
            boolean bNewWorkflow = false;
            //check for new
            if (wf_db.getActionClass() == null) {
                bNewWorkflow = true;
            }

            //first expires...

            if (_bExpire && !bNewWorkflow) {
                //EntityType
                if (wf_db.getEntityType() != null) {
                    updateMetaLinkAttrRow(_db, true, "Action/Attribute", wf_db.getActionItemKey(), "TYPE", "EntityType", wf_db.getEntityType());
                }
                //updaAttributeOnly
                updateMetaLinkAttrRow(_db, true, "Action/Attribute", wf_db.getActionItemKey(), "TYPE", "Update", (wf_db.updateAttributeOnly() ? "Attribute" : "Entity"));
                //Lock Entity
                updateMetaLinkAttrRow(_db, true, "Action/Attribute", wf_db.getActionItemKey(), "TYPE", "Lock", (wf_db.getLockEntity() ? "Y" : "N"));
                //Edit Action Item
                if (wf_db.getEditActionItem() != null) {
                    updateMetaLinkAttrRow(_db, true, "Action/Attribute", wf_db.getActionItemKey(), "TYPE", "EditAction", wf_db.getEditActionItem().getActionItemKey());
                }
                // Steps
                stepsList_db = wf_db.getStepsList();
                if (stepsList_db == null) {
                    stepsList_db = new OPICMList();
                }
                // 1) expire all
                for (int i = 1; i <= stepsList_db.size(); i++) {
                    updateMetaLinkAttrRow(_db, true, "Action/Attribute", wf_db.getActionItemKey(), "STEP", wf_db.getStepValue(i), (String) stepsList_db.get(wf_db.getStepValue(i)));
                }
                //QueueSource
                wf_db.getQueueSource();
                // 1) was queutype , now expire queuesource
                if (wf_db.getQueueSource() != null) {
                    updateMetaLinkAttrRow(_db, true, "Action/Attribute", wf_db.getActionItemKey(), "TYPE", "QUEUESOURCE", wf_db.getQueueSource());
                }
                //status
                // 1) had status -> now expire
                if (wf_db.getStatusValue() != -1) {
                    updateMetaLinkAttrRow(_db, true, "Action/Attribute", wf_db.getActionItemKey(), "TYPE", "STATUS", String.valueOf(wf_db.getStatusValue()));
                }
                return true;
            }

            //UPDATES
            //EntityType
            if ((bNewWorkflow && this.getEntityType() != null) || (wf_db.getEntityType() == null && this.getEntityType() != null) || ((wf_db.getEntityType() != null && this.getEntityType() != null) && !wf_db.getEntityType().equals(this.getEntityType()))) {
                updateMetaLinkAttrRow(_db, false, "Action/Attribute", this.getActionItemKey(), "TYPE", "EntityType", this.getEntityType());

            } else if (wf_db.getEntityType() != null && this.getEntityType() == null) {
                updateMetaLinkAttrRow(_db, true, "Action/Attribute", wf_db.getActionItemKey(), "TYPE", "EntityType", wf_db.getEntityType());
            }
            //Update Attribute Only
            if (bNewWorkflow || wf_db.updateAttributeOnly() != this.updateAttributeOnly()) {
                updateMetaLinkAttrRow(_db, false, "Action/Attribute", getActionItemKey(), "TYPE", "Update", (updateAttributeOnly() ? "Attribute" : "Entity"));
            }
            //Lock Entity
            if (bNewWorkflow || wf_db.getLockEntity() != this.getLockEntity()) {
                updateMetaLinkAttrRow(_db, false, "Action/Attribute", getActionItemKey(), "TYPE", "Lock", (getLockEntity() ? "Y" : "N"));
            }
            //Edit Action Item
            strEditAction = null;
            if (getEditActionItem() != null) {
                strEditAction = getEditActionItem().getActionItemKey();
            }
            strEditAction_db = null;
            if (wf_db.getEditActionItem() != null) {
                strEditAction_db = wf_db.getEditActionItem().getActionItemKey();
            }
            // 1) used to exist -> now expire
            if (strEditAction_db != null && strEditAction == null) {
                updateMetaLinkAttrRow(_db, true, "Action/Attribute", getActionItemKey(), "TYPE", "EditAction", strEditAction_db);
            //2) all other updates to current wf

            } else if ((bNewWorkflow && strEditAction != null) || (strEditAction != null && strEditAction_db == null) || (strEditAction_db != null && strEditAction != null && strEditAction_db != strEditAction)) {
                updateMetaLinkAttrRow(_db, false, "Action/Attribute", getActionItemKey(), "TYPE", "EditAction", strEditAction);
            }

            // Steps
            stepsList_db = wf_db.getStepsList();
            if (stepsList_db == null) {
                stepsList_db = new OPICMList();
            }
            stepsList = this.getStepsList();
            if (stepsList == null) {
                stepsList = new OPICMList();
            }
            // 1) expire all
            if (stepsList_db.size() != 0 && stepsList.size() == 0) {
                for (int i = 1; i <= stepsList_db.size(); i++) {
                    updateMetaLinkAttrRow(_db, true, "Action/Attribute", getActionItemKey(), "STEP", wf_db.getStepValue(i), (String) stepsList_db.get(wf_db.getStepValue(i)));
                }
            }
            // 2) add all (all new)
            if (bNewWorkflow || stepsList_db.size() == 0 && stepsList.size() != 0) {
                for (int i = 1; i <= stepsList.size(); i++) {
                    updateMetaLinkAttrRow(_db, false, "Action/Attribute", getActionItemKey(), "STEP", getStepValue(i), (String) stepsList.get(getStepValue(i)));
                }
            }
            // 3) check row by row...go through curr stepsList (steps should correspond due to unique keys on STEP value):
            //        - a) if row is !null in _db, null in current -> expire current row
            //        - b) if row IS null in _db, !null in current -> add current row
            //        - c) if row exists in both -> check for change in step value & update if different
            //        - else do nothing
            // a)
            for (int i = 1; i <= stepsList_db.size(); i++) {
                if (stepsList.get(wf_db.getStepValue(i)) == null) {
                    updateMetaLinkAttrRow(_db, true, "Action/Attribute", getActionItemKey(), "STEP", wf_db.getStepValue(i), (String) stepsList_db.get(wf_db.getStepValue(i)));
                }
            }
            // b) & c)
            for (int i = 1; i <= stepsList.size(); i++) {
                if (stepsList_db.get(wf_db.getStepValue(i)) == null) {
                    updateMetaLinkAttrRow(_db, false, "Action/Attribute", getActionItemKey(), "STEP", getStepValue(i), (String) stepsList.get(getStepValue(i)));

                } else { //neither should be null here..
                    String strVal_db = (String) stepsList_db.get(wf_db.getStepValue(i));
                    String strValCurr = (String) stepsList.get(getStepValue(i));
                    if (!strVal_db.equals(strValCurr)) {
                        updateMetaLinkAttrRow(_db, false, "Action/Attribute", getActionItemKey(), "STEP", getStepValue(i), (String) stepsList.get(getStepValue(i)));
                    }
                }
            }
            //QueueSource
            strQueueSource_db = wf_db.getQueueSource();
            strQueueSource = this.getQueueSource();
            // 1) was queutype , now expire queuesource
            if (wf_db.isQueueType() && !this.isQueueType() && wf_db.getQueueSource() != null) {
                updateMetaLinkAttrRow(_db, true, "Action/Attribute", getActionItemKey(), "TYPE", "QUEUESOURCE", wf_db.getQueueSource());
            // 2) was !queuetype , now is queuetype -> update accordingly

            } else if (!wf_db.isQueueType() && this.isQueueType() && this.getQueueSource() != null) {
                updateMetaLinkAttrRow(_db, false, "Action/Attribute", getActionItemKey(), "TYPE", "QUEUESOURCE", getQueueSource());
            // 3) queuesource changed or new

            } else if ((bNewWorkflow && strQueueSource != null) || (strQueueSource != null && strQueueSource_db != null && wf_db.isQueueType() && this.isQueueType() && strQueueSource_db != strQueueSource)) {
                updateMetaLinkAttrRow(_db, false, "Action/Attribute", getActionItemKey(), "TYPE", "QUEUESOURCE", getQueueSource());
            }
            //status
            // 1) had status -> now expire
            if (wf_db.getStatusValue() != -1 && this.getStatusValue() == -1) {
                updateMetaLinkAttrRow(_db, true, "Action/Attribute", getActionItemKey(), "TYPE", "STATUS", String.valueOf(wf_db.getStatusValue()));
            // 2) staus changed or added or new

            } else if ((bNewWorkflow && this.getStatusValue() != -1) || (wf_db.getStatusValue() != this.getStatusValue())) {
                updateMetaLinkAttrRow(_db, false, "Action/Attribute", getActionItemKey(), "TYPE", "STATUS", String.valueOf(getStatusValue()));
            }

        } catch (SQLException sqlExc) {
            _db.debug(D.EBUG_ERR, "WorkflowactionItem 793 " + sqlExc.toString());
            _db.freeStatement();
            _db.isPending();
            return false;
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return true;
    }

    /**
     * to simplify things a bit
     */
    private void updateMetaLinkAttrRow(Database _db, boolean _bExpire, String _strLinkType, String _strLinkType1, String _strLinkType2, String _strLinkCode, String _strLinkValue) throws MiddlewareException {
        String strValFrom = _db.getDates().getNow();
        String strValTo = (_bExpire ? strValFrom : _db.getDates().getForever());
        new MetaLinkAttrRow(getProfile(), _strLinkType, _strLinkType1, _strLinkType2, _strLinkCode, _strLinkValue, strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
    }

    /**
     * expect 2-digit String value for steps (i.e. "00" - "99")
     */
    private String getStepValue(int _i) {
        String strStepValue = String.valueOf(_i);
        if (_i < 10) { // i.e. single digits
            strStepValue = "0" + strStepValue;
        }
        return strStepValue;
    }

}
