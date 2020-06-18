//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: WatchdogActionItem.java,v $
// Revision 1.63  2009/05/14 15:09:11  wendy
// Support dereference for memory release
//
// Revision 1.62  2008/01/31 22:56:01  wendy
// Cleanup RSA warnings
//
// Revision 1.61  2005/08/10 16:14:24  tony
// improved catalog viewer functionality.
//
// Revision 1.60  2005/08/03 17:09:44  tony
// added datasource logic for catalog mod
//
// Revision 1.59  2005/01/18 21:46:51  dave
// more parm debug cleanup
//
// Revision 1.58  2003/10/30 00:43:35  dave
// fixing all the profile references
//
// Revision 1.57  2003/10/29 22:16:19  dave
// removed getProfile from many many new.. statements
// when the parent is not null
//
// Revision 1.56  2003/04/08 02:58:48  dave
// commit()
//
// Revision 1.55  2003/03/10 17:18:02  dave
// attempting to remove GBL7030 from the abstract Action Item
//
// Revision 1.54  2003/02/04 23:14:28  gregg
// modified to utilize IntervalItem
//
// Revision 1.53  2002/11/14 22:01:06  dave
// deabled state maching in search context
//
// Revision 1.52  2002/08/23 21:59:55  gregg
// updatePdhMeta method throws MiddlewareException
//
// Revision 1.51  2002/08/23 21:29:44  gregg
// updatePdhMeta(Database,boolean) method
//
// Revision 1.50  2002/07/29 17:46:00  dave
// change to WatchDog to pass additional parms to GBL8002
//
// Revision 1.49  2002/07/10 00:32:06  gregg
// First Test of Attribute Exlusion Stub
//
// Revision 1.48  2002/07/09 22:56:23  gregg
// becnhmarking Watchdog
//
// Revision 1.47  2002/07/09 21:31:31  gregg
// a bit of optimizing in executeAction
//
// Revision 1.46  2002/06/06 22:48:16  gregg
// use ExtractActionItem instead of NavActionItem for Filter type execute
//
// Revision 1.45  2002/06/03 23:54:24  gregg
// execute the WorkflowActionItem for BOTH NetChange & Filter Types
//
// Revision 1.44  2002/06/03 19:58:23  gregg
// pulll out target entityType's entityItems for filterType
//
// Revision 1.43  2002/06/03 19:42:01  gregg
// minor changes in executeAction method
//
// Revision 1.42  2002/05/30 22:48:47  gregg
// setFilterStart, getFilterStart
//
// Revision 1.41  2002/05/30 22:00:10  gregg
// finishing up filter type
//
// Revision 1.40  2002/05/30 00:35:10  gregg
// adding in Filter/NetChange Type...
//
// Revision 1.39  2002/05/29 21:21:01  gregg
// some rearranging of MetaLinkAttr stuff in constructor (NetChange)
//
// Revision 1.38  2002/05/28 22:25:07  gregg
// get a new sessionID everytime executeAction is performed
//
// Revision 1.37  2002/05/28 17:21:40  gregg
// leave setAttributeOnly/setLockEntity up to Meta definition for the WorkFlow itself
//
// Revision 1.36  2002/05/24 20:23:03  gregg
// dont clear out trsWatchdog ~after~ 8002
//
// Revision 1.35  2002/05/24 19:49:46  gregg
// setAttributeOnly, setLockEnity properties for internal Workflow
//
// Revision 1.34  2002/05/17 19:31:16  gregg
// getDefaultHtml must be protected
//
// Revision 1.33  2002/05/17 18:45:03  gregg
// moved getDisplayObject, setDisplayObject up into EANActionItem
//
// Revision 1.32  2002/05/17 00:12:46  gregg
// move HtmlDisplayable implementation up into EANActionItem
//
// Revision 1.31  2002/05/16 22:51:16  gregg
// HtmlDisplayable stuff
//
// Revision 1.30  2002/05/16 21:33:59  gregg
// more setParent for EntityGroup.toString()...
//
// Revision 1.29  2002/05/16 20:49:54  gregg
// more setParents for EntityItems
//
// Revision 1.28  2002/05/16 20:06:43  gregg
// index fix (i/j confusion..)
//
// Revision 1.27  2002/05/16 17:59:40  gregg
// add in parents to EntityItems retreived in executeAction (nulls are bad)
//
// Revision 1.26  2002/05/15 19:47:29  gregg
// return a WatchdogActionItem in execute
//
// Revision 1.25  2002/05/15 19:25:50  gregg
// aarrggh...more debugging for getEntityItems
//
// Revision 1.24  2002/05/15 19:10:07  gregg
// removed some code for debugging purposes
//
// Revision 1.23  2002/05/15 18:51:28  gregg
// getEntityItemsAsArray method
//
// Revision 1.22  2002/05/15 18:25:05  gregg
// return copy in getEntityItems() so that it is not modified by Worfkflowage...
//
// Revision 1.21  2002/05/15 18:15:38  gregg
// some more dump
//
// Revision 1.20  2002/05/15 17:51:12  gregg
// setEntityItems in 1st constructor
//
// Revision 1.19  2002/05/15 17:42:35  gregg
// debug stmts..
//
// Revision 1.18  2002/05/15 00:31:57  gregg
// setEntityItems() for internal WorkflowActionItem
//
// Revision 1.17  2002/05/15 00:26:12  gregg
// new constructor (poor man's clone()..) added
//
// Revision 1.16  2002/05/15 00:13:48  gregg
// use _db.executeAction(this) in exec method
//
// Revision 1.15  2002/05/14 22:54:20  gregg
// throw some more Exceptions for executeAction(WatchdogActionItem)
//
// Revision 1.14  2002/05/14 22:26:12  gregg
// executeaction method, use WorkflowActionItem to set trigger atts...
//
// Revision 1.13  2002/05/14 19:49:00  gregg
// use clearTrsWatchdogTable method
//
// Revision 1.12  2002/05/14 19:28:32  gregg
// get forever in executeAction method
//
// Revision 1.11  2002/05/14 18:23:10  gregg
// fixed some checks for flag update
//
// Revision 1.10  2002/05/14 18:07:13  gregg
// update trigger flag values
//
// Revision 1.9  2002/05/14 00:35:39  gregg
// gbl8003...
//
// Revision 1.8  2002/05/14 00:08:54  gregg
// some more in executeAction
//
// Revision 1.7  2002/05/13 23:52:26  gregg
// added some dumpage..
//
// Revision 1.6  2002/05/13 23:46:21  gregg
// triggerAttributeCode, triggerAttributeValue stuff
//
// Revision 1.5  2002/05/13 23:13:25  gregg
// setPathName, getPathName methods for use by gbl8002 in execute
//
// Revision 1.4  2002/05/13 22:22:03  gregg
// getEntityItems method added
//
// Revision 1.3  2002/05/13 22:06:47  gregg
// more on executeAction method..
//
// Revision 1.2  2002/05/13 21:36:10  gregg
// use gbl7030 to set targetEntityType in constructor
//
// Revision 1.1  2002/05/13 21:04:31  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.*;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.Stopwatch;

/**
 * The WatchdogActionItem is used to setup/prepare entities to be pulled using a corresponding ExtractActionItem
 */
public class WatchdogActionItem extends EANActionItem {

  static final long serialVersionUID = 20011106L;

  private String m_strTargetEntityType      = null;
  private String m_strActionType            = null;
  private EANList m_elEntityItems           = null;
  private EANActionItem m_oActionItem       = null;
  private static int TYPE_NETCHANGE         = 1;
  private static int TYPE_FILTER            = 2;
  private int m_iType                       = -1;
  private EntityItem[] m_aFilterStarts      = null;
    private IntervalItem m_intervalItem       = null;
    
    protected void dereference(){
    	super.dereference();
    	m_strTargetEntityType      = null;
    	m_strActionType            = null;
    	if (m_elEntityItems!= null){
    		m_elEntityItems.clear();
    		m_elEntityItems = null;
    	}

    	if (m_oActionItem!= null){
    		m_oActionItem.dereference();
    		m_oActionItem = null;
    	}

    	m_aFilterStarts      = null;

    	if (m_intervalItem!= null){
    		m_intervalItem.dereference();
    		m_intervalItem = null;
    	}
    }

    /**
    * Main method which performs a simple test of this class
    */
    public static void main(String arg[]) {
    }

    /*
    * Version info
    */
    public String getVersion() {
        return "$Id: WatchdogActionItem.java,v 1.63 2009/05/14 15:09:11 wendy Exp $";
    }

    public WatchdogActionItem(EANMetaFoundation  _mf, WatchdogActionItem _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
        setIntervalItem(_ai.getIntervalItem());
        setTargetEntityType(_ai.getTargetEntityType());
        setPathName(_ai.getPathName());
        setActionItem(_ai.getActionItem());
        setEntityItems(_ai.getEntityItems());
        setType(_ai.getType());
        setFilterStart(_ai.getFilterStart());
    }

/**
 * This represents a Watchdog Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
 */
    public WatchdogActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db,  _prof, _strActionItemKey);

        try {

            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs;
            Profile prof = getProfile();

            rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
            rdrs = new ReturnDataResultSet(rs);
            rs.close();
            rs = null;
            _db.commit();
            _db.freeStatement();
            _db.isPending();

            // get the target entity type out...
            for (int row = 0; row < rdrs.size();row++) {

                String strType =  rdrs.getColumn(row,0);
                String strCode = rdrs.getColumn(row,1);
                String strValue = rdrs.getColumn(row,2);

               _db.debug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");

                // Collect the attributes for the Action

                if (strType.equals("TYPE") && strCode.equals("NetChange")) {
                    StringTokenizer st = new StringTokenizer(strValue, ":");
                    String strPathName = st.nextToken();
                    String strTargetEntityType = st.nextToken();
                    setPathName(strPathName);
                    setTargetEntityType(strTargetEntityType);
                    setType(TYPE_NETCHANGE);
                } else if (strType.equals("TYPE") && strCode.equals("Filter")) {
                    StringTokenizer st = new StringTokenizer(strValue, ":");
                    String strPathName = st.nextToken();
                    String strTargetEntityType = st.nextToken();
                    setPathName(strPathName);
                    setTargetEntityType(strTargetEntityType);
                    setType(TYPE_FILTER);
                } else if (strType.equals("TYPE") && strCode.equals("Workflow")) {
                    setActionItem(new WorkflowActionItem(_emf,_db,null,strValue));
                } else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
                    super.setAssociatedEntityType(strValue);
				} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
					setDataSource(strValue);											//catalog enhancement
				} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
					setAdditionalParms(strValue);
                } else {
                    _db.debug(D.EBUG_ERR,"*** ACTION ITEM ATTRIBUTE *** No home for this Action/Attribute" + strType + ":" + strCode + ":" + strValue);
                }
            }

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

/**
 * set the entityItems which are the source for this Watchdog's Filter
 */
    public void setFilterStart(EntityItem[] _aEi) {
        m_aFilterStarts = _aEi;
        return;
    }

/**
 * get the entityItems which are the source for this Watchdog's Filter
 */
    protected EntityItem[] getFilterStart() {
        return m_aFilterStarts;
    }

/**
 * what breed of WatchDog is this
 */
    public boolean isNetChangeType() {
        return m_iType == TYPE_NETCHANGE;
    }

/**
 * what breed of WatchDog is this
 */
    public boolean isFilterType() {
        return m_iType == TYPE_FILTER;
    }

/**
 * set this WatchDog's type
 */
    public void setType(int _i) {
        m_iType = _i;
    }

/**
 *
 */
    protected int getType() {
        return m_iType;
    }

    public void setIntervalItem(IntervalItem _intervalItem) {
        m_intervalItem = _intervalItem;
    }

    public IntervalItem getIntervalItem() {
        return m_intervalItem;
    }

/**
 * Set the target Entity Type for execute
 */
    protected void setTargetEntityType(String _strEntityType) {
        m_strTargetEntityType = _strEntityType;
        return;
    }
/**
 * Set the Action Type for execute
 */
    protected void setPathName(String _strActionType) {
        m_strActionType = _strActionType;
        return;
    }

/**
 * Set the Action item associated with this watchdog
 */
    protected void setActionItem(EANActionItem _oActionItem) {
        m_oActionItem = _oActionItem;
        return;
    }

/**
 *
 */
    protected void setEntityItems(EANList _elEntityItems) {
        m_elEntityItems = _elEntityItems;
        return;
    }

/**
 * Get the From Date property
 */
    private String getFromDate() {
        if(getIntervalItem() == null)
            return null;
        return getIntervalItem().getStartDate();
    }

/**
 * Get the To Date property
 */
    private String getToDate() {
        if(getIntervalItem() == null)
            return null;
        return getIntervalItem().getEndDate();
    }

/**
 * Get the target Entity Type
 */
    public String getTargetEntityType() {
        return m_strTargetEntityType;
    }

/**
 * Get the Action Type
 */
    public String getPathName() {
        return m_strActionType;
    }

/**
 * Get the Action item associated with this watchdog
 */
    public EANActionItem getActionItem() {
        return m_oActionItem;
    }

/**
 * does this watchdog utilize an internal actionitem
 */
    public boolean hasActionItem() {
        return (getActionItem() != null);
    }

/**
 * Get a copy so that it is not modified by reference
 */
    public EANList getEntityItems() throws MiddlewareRequestException {
        if(m_elEntityItems == null)
            throw new MiddlewareRequestException("WatchdogActionItem:" + getKey() + " must be executed before any EntityItems can be returned!");
        return m_elEntityItems;
    }

/**
 * Get a copy so that it is not modified by reference
 */
    public EntityItem[] getEntityItemsAsArray() throws MiddlewareRequestException {
        EntityItem[] aEntityItems = new EntityItem[getEntityItems().size()];
        for(int i = 0; i < getEntityItems().size(); i++) {
            EntityItem oEi = (EntityItem)getEntityItems().getAt(i);
            aEntityItems[i] = new EntityItem(oEi);
            aEntityItems[i].setParent(oEi.getParent());
        }
        return aEntityItems;
    }

/**
 * Execute using db connection
 */
    public WatchdogActionItem exec(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException, LockException, MiddlewareShutdownInProgressException, RemoteException, WorkflowException {
        return _db.executeAction(this);
    }

/**
 * Execute through RMI
 */
    public WatchdogActionItem rexec(RemoteDatabaseInterface _rdi) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException, LockException, WorkflowException {
        return _rdi.executeAction(this);
    }

/**
 * Get the changes and set target flags through WorkflowActionItem if set
 */
    public WatchdogActionItem executeAction(Database _db) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, WorkflowException {
      Stopwatch sw = new Stopwatch();
      sw.start();
      try {
        if(isNetChangeType()) {
            if(getFromDate() == null) {
                throw new MiddlewareRequestException("Please set the From Date property before executing WatchdogActionItem:" + getKey() + ".");
            } if(getToDate() == null) {
                throw new MiddlewareRequestException("Please set the To Date property before executing WatchdogActionItem:" + getKey() + ".");
              }
              int iSessionID = _db.getNewSessionID();
              ReturnStatus returnStatus = new ReturnStatus(-1);
                    ResultSet rs = null;
                    ReturnDataResultSet rdrs;
//                    ReturnDataResultSetGroup rdrsg = null;
  //                  String strForever = _db.getDates().getForever();
                    int iOPWGID = getProfile().getOPWGID();
                    clearTrsWatchdogTable(_db, iSessionID);

                    rs = _db.callGBL8002(returnStatus, iOPWGID, iSessionID, getProfile().getEnterprise(), getTargetEntityType(), getPathName(), getProfile().getRoleCode(), getFromDate(), getToDate());
                rdrs = new ReturnDataResultSet(rs);
                rs.close();
                    rs = null;
                    _db.freeStatement();
                    _db.isPending();

                    //clearTrsWatchdogTable(_db,iSessionID);

                    EntityGroup oEgParent = new EntityGroup(null,_db,getProfile(),getTargetEntityType(),"Navigate");

                    m_elEntityItems = new EANList(rdrs.size());
                    oEgParent.setEntityItem(m_elEntityItems);


                    // grab our watchdogged entities..
                    for (int row = 0; row < rdrs.size(); row++) {
                        String strEntityType = rdrs.getColumn(row,0);
                        int iEntityID        = rdrs.getColumnInt(row,1);
                    _db.debug(D.EBUG_SPEW, "gbl8002 answer is:" + strEntityType + ":" + iEntityID);
                        oEgParent.putEntityItem(new EntityItem(null, getProfile(), strEntityType, iEntityID));
                    }

                    // DWB maybe latest m_elEntityItems.rehash();

                    //set up parents for the EntityItems
                    //EANList elParents = new EANList();
                    //for(int i = 0; i <m_elEntityItems.size(); i++) {
                    //      EntityItem oEi = (EntityItem)m_elEntityItems.getAt(i);
                    //      boolean bFound = false;
                    //      for(int j = 0; j < elParents.size(); j++) {
                    //          EntityGroup oEgParent = (EntityGroup)elParents.getAt(j);
                    //          if(oEi.getEntityType().equals(oEgParent.getEntityType())) {
                    //              oEi.setParent(oEgParent);
                    //              bFound = true;
                    //              continue;
                    //          }
                    //      }
                    //      if(!bFound) {
                    //          EntityGroup oEgParent = new EntityGroup(null,_db,getProfile(),oEi.getEntityType(),"Navigate");
                    //          elParents.put(oEgParent);
                    //          oEi.setParent(oEgParent);
                    //      }
                    //  }
            //    _db.debug(D.EBUG_SPEW,"getEntityItems().size():" + getEntityItems().size());
            } else if(isFilterType()) {
          if(getFilterStart() == null) {
            throw new MiddlewareRequestException("WatchdogActionItem:" + getKey() + " - getFilterStart() is null; call setFilterStart first!");
                    }
                    if(getFilterStart().length == 0) {
            throw new MiddlewareRequestException("WatchdogActionItem:" + getKey() + " - getFilterStart().length == 0!");
          }
          ExtractActionItem oExtractPath = new ExtractActionItem((EANMetaFoundation)getParent(),_db,getProfile(),getPathName());
          EntityList elFilter = _db.getEntityList(getProfile(),oExtractPath,getFilterStart());
              EntityItem[] aEiFilter = elFilter.getEntityGroup(getTargetEntityType()).getEntityItemsAsArray();
              EANList eList = new EANList();
          if(aEiFilter != null) {
                for(int i = 0; i < aEiFilter.length; i++) {
                      eList.put(aEiFilter[i]);
                    }
                    }
                    setEntityItems(eList);
            } else {
                //we about to throw, so just to be safe free up db...
                _db.freeStatement();
                _db.isPending();
            throw new MiddlewareRequestException("WatchdogActionItem:" + getKey() + " - no TYPE has been defined in the MetaData for this watchdog puppy.");
          }

        ////////////// Workflow ///////////////
            //execute this thing if it has one
            if(hasActionItem()) {
                if(getActionItem() instanceof WorkflowActionItem) {
                    WorkflowActionItem oWfai = (WorkflowActionItem)getActionItem();
                    oWfai.setLongWay(false);
                    oWfai.setEntityItems(getEntityItemsAsArray());
                    //don't touch entity record
                    //oWfai.setAttributeOnly(true);
                    //sneak by locks
                    //oWfai.setLockEntity(false);
                    oWfai.executeAction(_db,getProfile());
                }
                else
                    throw new MiddlewareRequestException("WatchdogActionItem:" + getKey() + " - internal ActionItem cannot be a " + getActionItem().getActionClass() + " check your MetaData.");
            }
        /////////////////////////////////////////

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        _db.debug(D.EBUG_SPEW,"timing WatchDogactionItem.executeAction (" + getKey() + "): " + sw.getFinish()/1000 + "s");
        return this;

    }

/**
 * clear out the trsWatchdog temp table
 */
    private void clearTrsWatchdogTable(Database _db, int _iSessionID) throws MiddlewareException {
        _db.callGBL8003(new ReturnStatus(-1),_iSessionID);
        _db.freeStatement();
        _db.isPending();
        return;
    }

    public String getPurpose() {
        return "Watchdog";
    }

    public String dump(boolean _bBrief) {
        return dump(_bBrief,false);
    }

/**
 *
 */
    private String dump(boolean _bBrief, boolean _bHtml) {
        String strNewLine = "\n";
        if(_bHtml)
            strNewLine = "<BR>";
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("WatchdogActionItem:" + getKey());
        strbResult.append(strNewLine + "purpose:" + getPurpose());
        String strType = "Net Change";
        if(isFilterType())
            strType = "Filter";
        strbResult.append(strNewLine + "WatchDog's Type:" + strType);
        strbResult.append(strNewLine + "targetEntityType:" + getTargetEntityType());
        strbResult.append(strNewLine + "pathType:" + getPathName());
        strbResult.append(strNewLine + "ActionItem:" + (getActionItem()==null?"NONE":(getActionItem().getKey() + "(" + getActionItem().getActionClass() + ")")));
        strbResult.append(strNewLine + "fromDate:" + (getFromDate()==null?"NONE":getFromDate()));
        strbResult.append(strNewLine + "toDate:" + (getToDate()==null?"NONE":getToDate()));
        try {
            strbResult.append(strNewLine + "#EntityItems:" + getEntityItems().size());
        } catch(MiddlewareRequestException mre) {
            strbResult.append(strNewLine + "#EntityItems:NONE");
        }
        return strbResult.toString();
    }

/**
 *
 */
    protected String getDefaultHtml() {
        return dump(false,true);
    }

/**
 * @return true if successful, false if nothing to update or unsuccessful
 */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        return true;
    }

}
