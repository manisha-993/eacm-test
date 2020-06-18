//
// Copyright (c) 2001-2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
//$Log: PMSyncEC.java,v $
//Revision 1.39  2007/07/31 13:03:46  chris
//Rational Software Architect v7
//
//Revision 1.38  2004/05/24 15:53:01  joan
//catch Exception
//
//Revision 1.37  2004/05/24 15:16:54  joan
//throw exception
//
//Revision 1.36  2004/05/21 23:02:30  joan
//throw exception
//
//Revision 1.35  2004/05/11 17:25:50  gregg
//compile fix
//
//Revision 1.34  2004/05/11 17:16:32  gregg
//massive readjusting yeehaw
//
//Revision 1.33  2004/05/10 19:55:39  gregg
//some more debugs + ecNumber.trim()
//
//Revision 1.32  2004/05/10 19:41:01  gregg
//some debugs
//
//Revision 1.31  2004/04/30 21:27:27  gregg
//some fix
//
//Revision 1.30  2004/04/30 19:49:07  gregg
//fix
//
//Revision 1.29  2004/04/30 19:13:54  gregg
//compile pfhicxks
//
//Revision 1.28  2004/04/30 19:04:32  gregg
//DELEC
//
//Revision 1.27  2004/03/17 18:59:03  gregg
//clarifying some method names
//
//Revision 1.26  2004/03/17 18:58:24  gregg
//update
//
//Revision 1.25  2004/03/17 18:32:20  gregg
//more link logic (REV/CTO stuff per CR5740)
//
//Revision 1.24  2004/03/17 16:18:28  gregg
//link EC to ECL0 logic (search for converse PN etc)
//
//Revision 1.23  2004/01/08 23:01:53  dave
//anothre fix
//
//Revision 1.22  2003/11/26 18:25:09  gregg
//just some comments for javadoc
//
//Revision 1.21  2003/09/12 23:18:12  gregg
//ok, null ptr fix (queueEC() method)
//
//Revision 1.20  2003/09/12 18:00:12  gregg
//ok, adjust queue logic a bit more
//
//Revision 1.19  2003/09/12 17:43:46  gregg
//try to comit() EC lastly
//
//Revision 1.18  2003/08/12 22:32:06  gregg
//that worked. now cleanup.
//
//Revision 1.17  2003/08/12 22:05:04  gregg
//attempting grab new ActionItems from _rdi, rather than from ActionGroup
//
//Revision 1.16  2003/08/12 16:40:43  gregg
//commented out some code: Always create a new EC regardless of duplicate Part Numbers.
//
//Revision 1.15  2003/08/12 16:34:34  gregg
//fix DEBUG_MODE
//
//Revision 1.14  2003/08/07 21:39:24  gregg
//EC NAME is now "PM - EC - " + ecnumber
//
//Revision 1.13  2003/08/07 20:48:15  gregg
//more debug logging
//
//Revision 1.12  2003/08/07 17:44:33  gregg
//ensure c_log != null before print
//
//Revision 1.11  2003/08/07 17:35:31  gregg
//some logging
//
//Revision 1.10  2003/07/10 23:55:30  gregg
//ABR auto-queue
//
//Revision 1.9  2003/07/10 21:45:57  gregg
//queue ABR on create.
//
//Revision 1.8  2003/06/05 20:03:33  gregg
//match to v1.1.1
//
//Revision 1.7  2003/05/29 22:40:48  gregg
//update
//
//Revision 1.6  2003/05/29 20:59:12  gregg
//update - RMI working again
//
//Revision 1.5  2003/05/29 19:53:58  gregg
//back to RMI implementation
//
//Revision 1.4  2003/05/23 22:38:35  gregg
//use Properties instead of XProperties
//
//Revision 1.3  2003/05/20 21:14:57  gregg
//update
//
//Revision 1.2  2003/05/20 19:23:51  gregg
//compile fix
//
//Revision 1.1  2003/05/20 19:14:35  gregg
//initial load
//
//

package COM.ibm.eannounce.pmsync;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import COM.ibm.eannounce.objects.CreateActionItem;
import COM.ibm.eannounce.objects.DeleteActionItem;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EANMetaEntity;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.LinkActionItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.eannounce.objects.NavActionItem;
import COM.ibm.eannounce.objects.RowSelectableTable;
import COM.ibm.eannounce.objects.SearchActionItem;
import COM.ibm.eannounce.objects.SingleFlagAttribute;
import COM.ibm.eannounce.objects.TaskAttribute;
import COM.ibm.eannounce.objects.TextAttribute;
import COM.ibm.eannounce.objects.WorkflowException;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;


/**
 * Convert an Level0EC object from PM to an eannounce 'Engineering Change' Entity.
 * This object really reperesents ONE EC EntityItem.
 */
public final class PMSyncEC extends EANMetaEntity {

    // entitytypes + attcodes
    protected static final String WG_ENTTYPE               = PostECs.getMetaProperties().getProperty("WG_ENTTYPE");
    protected static final String EC_ENTTYPE               = PostECs.getMetaProperties().getProperty("EC_ENTTYPE");
    protected static final String WGEC_REL_ENTTYPE         = PostECs.getMetaProperties().getProperty("WGEC_REL_ENTTYPE");
    protected static final String EC_NUMB_ATTCODE          = PostECs.getMetaProperties().getProperty("EC_NUMB_ATTCODE");      // T
    protected static final String EC_LOC_ATTCODE           = PostECs.getMetaProperties().getProperty("EC_LOC_ATTCODE");       // T
    protected static final String EC_TRANSDATE_ATTCODE     = PostECs.getMetaProperties().getProperty("EC_TRANSDATE_ATTCODE"); // T
    protected static final String EC_STATUS_ATTCODE        = PostECs.getMetaProperties().getProperty("EC_STATUS_ATTCODE");    // U
    //protected static final String EC_COMMENT_ATTCODE     = PostECs.getMetaProperties().getProperty("ECCOMMENT";   // L
    protected static final String EC_ID_ATTCODE            = PostECs.getMetaProperties().getProperty("EC_ID_ATTCODE");          // T
    protected static final String EC_NAME_ATTCODE          = PostECs.getMetaProperties().getProperty("EC_NAME_ATTCODE");        // T
    protected static final String EC_NEW_FLAGCODE          = PostECs.getMetaProperties().getProperty("EC_NEW_FLAGCODE");
    protected static final String NAV_EC_ACTIONKEY         = PostECs.getMetaProperties().getProperty("NAV_EC_ACTIONKEY");
    protected static final String CR_EC_ACTIONKEY          = PostECs.getMetaProperties().getProperty("CR_EC_ACTIONKEY");
    protected static final String DEL_EC_ACTIONKEY          = PostECs.getMetaProperties().getProperty("DEL_EC_ACTIONKEY");
    protected static final String EDIT_EC_ACTIONKEY        = PostECs.getMetaProperties().getProperty("EDIT_EC_ACTIONKEY");
    protected static final String EC_ABR1_ATTCODE          = PostECs.getMetaProperties().getProperty("EC_ABR1_ATTCODE");
    protected static final String EC_ABR1_QUEUED_FLAGCODE  = PostECs.getMetaProperties().getProperty("EC_ABR1_QUEUED");
    //
    protected static final String SR_ECL0_ACTIONKEY        = PostECs.getMetaProperties().getProperty("SR_ECL0_ACTIONKEY");
    protected static final String LINK_ECECL0_ACTIONKEY    = PostECs.getMetaProperties().getProperty("LINK_ECECL0_ACTIONKEY");
    //
    private String m_strEntityItemKey = null;
    protected static boolean c_debugMode = true;
    protected static final String LOG_NAME = "PMSyncFeed.log";
    protected static PMLog c_log = null;
    static {
        try {
            c_debugMode = (PostECs.getRMILoginProperties().getProperty("debugMode").equalsIgnoreCase("true"));
        } catch(Exception e) {
            // do nothing
        }
        try {
            c_log = new PMLog(LOG_NAME);
            c_log.write("init log");
            c_log.flush();
        } catch(IOException e){
            System.err.println("Error Initializing log:");
            e.printStackTrace(System.err);
        }
    }

/**
 * Pull out EC EntityGroup from database, and fill out all data. Then recursively set up ECL0's, ECL1's.
 */
    public PMSyncEC(RemoteDatabaseInterface _rdi, Profile _prof, Level0EC _ec) throws RemoteException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, SQLException, LockException {
        super(null,_prof,_ec.m_strEcNumber);

        // log what we've got!!!
        debug("***New PMSyncEC***");
        debug("ec.ecNumber:" + _ec.m_strEcNumber);
        debug("ec.engLoc:" + _ec.m_strEngLoc);
        debug("ec.fetchTime:" + _ec.m_tsFetchTime.toString());
        debug("ec.bomList.size():" + _ec.m_vctBomList.size());

        // 0) before we do anything, lets validate the integrity of the passed object ...
        validateEC(_ec);
        // 1) set up EC EntityGroup + EntityItem + Attributes
        setupData(_rdi,_ec);
        // 2) now set up children
        setupChildren(_rdi,_ec);
    }

/**
 * Build our local objects.
 * Called from constructor.
 */
    private void setupData(RemoteDatabaseInterface _rdi, Level0EC _ec) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, EANBusinessRuleException, SQLException, LockException {

        EntityList elEC = getECEntityList(_rdi);
        EntityGroup egEC = elEC.getEntityGroup(EC_ENTTYPE);
        EntityItem eiEC = null;
        //see if the EC w/ the current ecNumber is in there....
        for(int i = 0; i < egEC.getEntityItemCount(); i++) {
            // if found --> we need to update the EntityItem

            if(egEC.getEntityItem(i).getAttribute(EC_NUMB_ATTCODE) == null) {
                debug("setupData(): null EC Number on - EC:" + egEC.getEntityItem(i).getEntityID());
            } else {

                //debug("setupData():looking for existing EC Numbers - EC:" + egEC.getEntityItem(i).getEntityID() + " = " + egEC.getEntityItem(i).getAttribute(EC_NUMB_ATTCODE).toString());
                if(egEC.getEntityItem(i).getAttribute(EC_NUMB_ATTCODE).toString().trim().equals(_ec.m_strEcNumber.trim())) {
                    debug("Matching EC found, deleting so we can insert a new one... egEC:\"" + egEC.getEntityItem(i).getAttribute(EC_NUMB_ATTCODE).toString() + "\",_ec.m_strEcNumber:\"" + _ec.m_strEcNumber + "\"");
                    deleteECEntityItem(_rdi,egEC.getEntityItem(i));
                    break;
                } else {
                    debug("NO Matching EC found: egEC:\"" + egEC.getEntityItem(i).getAttribute(EC_NUMB_ATTCODE).toString() + "\",_ec.m_strEcNumber:" + _ec.m_strEcNumber + "\"");
				}
            }
        }
        eiEC = createNewECEntityItem(_rdi,_ec,elEC,egEC);
        if(eiEC == null) {
            String strMessage = "Could not build an EntityItem for the passed Level0EC";
            debug(strMessage);
            throw new MiddlewareRequestException(strMessage);
        }

        return;
    }

/**
 * Update the passed EntityItem w/ the Level0EC data using an EditActionItem
 */
    private void deleteECEntityItem(RemoteDatabaseInterface _rdi, EntityItem _ei) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, EANBusinessRuleException, SQLException, LockException {
		DeleteActionItem dai = _rdi.getDeleteActionItem(null,getProfile(),DEL_EC_ACTIONKEY);
		dai.setEntityItems(new EntityItem[]{_ei});
        dai.rexec(_rdi,getProfile());
    }

/**
 * Obtain an EntityList for EC's
 */
    private EntityList getECEntityList(RemoteDatabaseInterface _rdi) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
    EntityList el = _rdi.getEntityList(getProfile());
    EntityGroup eg = el.getEntityGroup(WG_ENTTYPE);
        putMeta(eg);
    EntityItem[] eiArrSingle = new EntityItem[1];
    //fish out WG for THIS profile
    eiArrSingle[0] = eg.getEntityItem(WG_ENTTYPE + getProfile().getWGID());
    if(eiArrSingle[0] == null) {
        String strMessage = "EntityItem for WG:" + getProfile().getWGID() + " not found!! bailing out....";
        debug(strMessage);
        throw new MiddlewareRequestException(strMessage);
    }

        NavActionItem nai = _rdi.getNavActionItem(null,getProfile(),NAV_EC_ACTIONKEY);

        // DWBIs the action tree needed here?
        el = _rdi.getEntityList(getProfile(),nai,eiArrSingle,true);
    //store relator
    putMeta(el.getEntityGroup(WGEC_REL_ENTTYPE));
        return el;
    }

/**
 * Create a new EntityItem using CreateActionItem and populate it w/ Level0EC data
 */
    private EntityItem createNewECEntityItem(RemoteDatabaseInterface _rdi, Level0EC _ec, EntityList _elEC, EntityGroup _egEC) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, EANBusinessRuleException, SQLException {
        EntityGroup egWG = (EntityGroup)getMeta(WG_ENTTYPE);

        CreateActionItem createAi = _rdi.getCreateActionItem(null,getProfile(),CR_EC_ACTIONKEY);

        EntityItem eiWG = egWG.getEntityItem(WG_ENTTYPE + getProfile().getWGID());
        EntityList elCreate = EntityList.getEntityList(_rdi,getProfile(),createAi,new EntityItem[]{eiWG});
        EntityGroup egCreate = elCreate.getEntityGroup(EC_ENTTYPE);
        // we want to update relator!!
        putMeta(elCreate.getEntityGroup(WGEC_REL_ENTTYPE));
        putMeta(egCreate);
        debug("egCreate.getEntityItemCount():" + egCreate.getEntityItemCount());
        EntityItem eiCreate = egCreate.getEntityItem(0);
        return putECData(egCreate,eiCreate,_ec);
    }

/**
 * Update the passed EntityItem w/ the Level0EC data
 */
    private EntityItem putECData(EntityGroup _egEC, EntityItem _eiEC, Level0EC _ec) throws EANBusinessRuleException, MiddlewareRequestException {
        // *** now the important part -- put all data from Level0EC!!
        debug("putECData()--> dumping EntityItem EC ~before~ put data:" + _eiEC.dump(false));
        ((TextAttribute)_eiEC.getAttribute(EC_NUMB_ATTCODE)).put(_ec.m_strEcNumber);
        ((TextAttribute)_eiEC.getAttribute(EC_LOC_ATTCODE)).put(_ec.m_strEngLoc);
        ((TextAttribute)_eiEC.getAttribute(EC_TRANSDATE_ATTCODE)).put(_ec.m_tsFetchTime.toString());
        MetaFlag mfNew = new MetaFlag(_egEC.getMetaAttribute(EC_STATUS_ATTCODE),getProfile(),EC_NEW_FLAGCODE);
        mfNew.setSelected(true);
        ((SingleFlagAttribute)_eiEC.getAttribute(EC_STATUS_ATTCODE)).put(new MetaFlag[]{mfNew});
        // NAME,ID --> store ec number??
        ((TextAttribute)_eiEC.getAttribute(EC_ID_ATTCODE)).put(_ec.m_strEcNumber);
        ((TextAttribute)_eiEC.getAttribute(EC_NAME_ATTCODE)).put("PM - EC - " + _ec.m_strEcNumber);

        // 9/12/03 - moved this last!!
        // 7/10/03 - QUEUE ABR also!
        //MetaFlag mfABR = new MetaFlag(_egEC.getMetaAttribute(EC_ABR1_ATTCODE),getProfile(),EC_ABR1_QUEUED_FLAGCODE);
        //mfABR.setSelected(true);
        //((TaskAttribute)_eiEC.getAttribute(EC_ABR1_ATTCODE)).put(new MetaFlag[]{mfABR});
        // end 7/10/03

        // *** data put()'s done
        _egEC.putEntityItem(_eiEC);
        // check business rules -- let whatever Exceptions be thrown be thrown!!!
        _eiEC.checkBusinessRules();
         // now finally set key so we can get it back out
        m_strEntityItemKey = _eiEC.getKey();
        debug("putECData()--> dumping EntityItem EC ~after~ put data:" + _eiEC.dump(false));
        return _eiEC;
    }

/**
 * Validate the passed Level0EC's data.
 * Called from constructor.
 * What does this mean?? (I only know that we need to do this, not what bad data will actually look like yet...)
 */
    private void validateEC(Level0EC _ec) throws MiddlewareRequestException {
        String strKey = _ec.m_strEcNumber;
        if(_ec.m_strEngLoc == null) {
            throw new MiddlewareRequestException("EC:" + strKey + " - null " + EC_LOC_ATTCODE);
        }
        if(_ec.m_tsFetchTime == null) {
             throw new MiddlewareRequestException("EC:" + strKey + " - null " + EC_TRANSDATE_ATTCODE);
        }
        if(_ec.m_vctBomList == null) {
             throw new MiddlewareRequestException("EC:" + strKey + " - null children");
        }
        return;
    }

/**
 * Go through and setup child data.
 * Called from constructor.
 */
    private void setupChildren(RemoteDatabaseInterface _rdi, Level0EC _ec) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, SQLException {
        for(int iBoms = 0; iBoms < _ec.m_vctBomList.size(); iBoms++) {
            PMSyncECL0 ecl0 = new PMSyncECL0(this,_rdi,getProfile(),(Level0BOM)_ec.m_vctBomList.elementAt(iBoms));
            putECL0(ecl0);
        }
        return;
    }


/**
 * commit()
 */
    public void feedToPdh(RemoteDatabaseInterface _rdi) throws RemoteException, LockException, EANBusinessRuleException, MiddlewareBusinessRuleException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException, RemoteException {
        if(((EntityGroup)getMeta(WGEC_REL_ENTTYPE)).getEntityItem(0) == null) {
            throw new MiddlewareRequestException("PMSyncEC: WG Relator EntityItem is null!!!");
        }
        EntityItem eiCommit = ((EntityGroup)getMeta(WGEC_REL_ENTTYPE)).getEntityItem(0);
        // remove reference here - we need to replace it after commit
        getEntityGroup().removeEntityItem(eiCommit);

        debug("committing EntityItem:" + eiCommit.dump(false));
        eiCommit.commit(null,_rdi);
        debug("After commit, EntityID = " + eiCommit.getEntityID());

        getEntityGroup().putEntityItem(eiCommit);

        // now children!!!
        debug("now going for children (" + getChildCount() + " children)");
        for(int i = 0; i < getChildCount(); i++) {
            getChild(i).feedToPdh(_rdi);
        }

        queueEC(_rdi);

        // per CR5740: check for older 'REV's if not present...
        sync_REV_CTO(_rdi);
    }

    public void queueEC(RemoteDatabaseInterface _rdi) throws RemoteException, LockException, EANBusinessRuleException, MiddlewareBusinessRuleException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException, RemoteException {
        MetaFlag mfABR = new MetaFlag(((EntityGroup)getMeta(EC_ENTTYPE)).getMetaAttribute(EC_ABR1_ATTCODE),getProfile(),EC_ABR1_QUEUED_FLAGCODE);
        mfABR.setSelected(true);
        EntityItem eiEC = getEntityItem();
        ((TaskAttribute)eiEC.getAttribute(EC_ABR1_ATTCODE)).put(new MetaFlag[]{mfABR});
        eiEC.commit(null,_rdi);
        debug("queued up EC:" + eiEC.toString());
    }

/**
 * See CR 5740 for this one - it's messy!!!
 * Basically, if no REV's are found in this package, then grab the latest prior REV (if it exists).
 * Conversely, the same logic applies to CTO.
 */
    protected void sync_REV_CTO(RemoteDatabaseInterface _rdi) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException, EANBusinessRuleException, LockException {
        for(int i = 0; i < getChildCount(); i++) {
            PMSyncECL0 pmECL0 = getChild(i);
            if(hasECL0WithPartNumber(pmECL0.getConversePN())) {
                continue;
            } else {
                linkECL0WithConversePartNumber(_rdi,pmECL0);
            }
        }
    }

/**
 * Does this EC structure contain an ECL0 w/ the indicated PN?
 */
    private boolean hasECL0WithPartNumber(String _strPN) {
        for(int i = 0; i < getChildCount(); i++) {
            PMSyncECL0 pmECL0 = getChild(i);
            if(pmECL0.getPartNumber().equals(_strPN)) {
                debug("hasECL0WithPartNumber(" + _strPN + ") = true!");
                return true;
            }
        }
        debug("hasECL0WithPartNumber(" + _strPN + ") = false...");
        return false;
    }

/**
 * Search for ECL0s w/ the indicated PN. Then Link these to our current EC via linkECL0 method.
 */
    private void linkECL0WithConversePartNumber(RemoteDatabaseInterface _rdi, PMSyncECL0 _pmECL0) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException, EANBusinessRuleException, LockException {
        String strPN = _pmECL0.getConversePN();
        SearchActionItem sae = _rdi.getSearchActionItem(null,getProfile(),SR_ECL0_ACTIONKEY);
        RowSelectableTable rst = sae.getDynaSearchTable(_rdi);
        for(int i = 0; i < rst.getRowCount(); i++) {
            if(rst.getRowKey(i).equals(PMSyncECL0.ECL0_ENTTYPE + ":" + PMSyncECL0.ECL0_PNUMB_ATTCODE)) {
                rst.put(i,1,strPN);
                break;
            }
        }
        EntityList elSearch = sae.rexec(_rdi,getProfile());
        //
        EntityGroup egECL0 = elSearch.getEntityGroup(PMSyncECL0.ECL0_ENTTYPE);
        if(egECL0 != null) {
            debug("Found ECL0's for:" + strPN);
            EntityItem eiLink = null;
            for(int i = 0; i < egECL0.getEntityItemCount(); i++) {
                EntityItem eiTest = egECL0.getEntityItem(i);
                // RE: we only want ei w/ the LATEST date.
                if(eiLink == null || hasLaterDate(eiTest,eiLink)) {
                    eiLink = eiTest;
                }
            }
            if(eiLink != null) {
                debug("linking: " + eiLink.getKey() + " to: " + getEntityItem().getKey());
                linkECL0(_rdi,eiLink);
            }
        } else {
            debug("Found no ECL0's for:" + strPN);
        }
    }

/**
 * Link One ECL0 as a child of this EC
 */
    private void linkECL0(RemoteDatabaseInterface _rdi, EntityItem _eiECL0) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, LockException, EANBusinessRuleException, SQLException {

        EntityGroup egRel = _rdi.getEntityGroup(getProfile(),PMSyncECL0.ECECL0_REL_ENTTYPE,"Edit");
        EntityItem eiRel = new EntityItem(egRel,getProfile(),egRel.getEntityType(),-1);
        eiRel.putUpLink(getEntityItem());
        eiRel.putDownLink(_eiECL0);
        LinkActionItem lai = _rdi.getLinkActionItem(null,getProfile(),LINK_ECECL0_ACTIONKEY);
        lai.setParentEntityItems(new EntityItem[]{eiRel});
        lai.setChildEntityItems(new EntityItem[]{_eiECL0});
        lai.setOption(LinkActionItem.OPT_DEFAULT);
        try {
        	lai.rexec(_rdi,getProfile());
		} catch(WorkflowException we) {
			we.printStackTrace();
		}
    }

/**
 * Compare the TransDates.
 * eiTest against eiLink
 * @returns true if _eiTest date > _eiCurr date.
 */
    private boolean hasLaterDate(EntityItem _eiTest, EntityItem _eiCurr) {
        EANAttribute attTest = _eiTest.getAttribute(PMSyncECL0.ECL0_TRANSDATE_ATTCODE);
        EANAttribute attCurr = _eiCurr.getAttribute(PMSyncECL0.ECL0_TRANSDATE_ATTCODE);
        if(attTest == null) {
            return false;
        }
        if(attCurr == null) {
            return true;
        }
        String strDateTest = attTest.toString().trim();
        String strDateCurr = attCurr.toString().trim();
        if(strDateTest == null) {
            return false;
        }
        if(strDateCurr == null) {
            return false;
        }
        return (strDateTest.compareTo(strDateCurr) > 0);
    }



////////////////////
// Accessors, etc //
////////////////////

    private int getChildCount() {
        return getDataCount();
    }

    private PMSyncECL0 getChild(int _i) {
        return (PMSyncECL0)getData(_i);
    }

    private void putECL0(PMSyncECL0 _ecl0) {
        putData(_ecl0);
    }

    protected EntityGroup getEntityGroup() {
        return (EntityGroup)getMeta(EC_ENTTYPE);
    }

    protected EntityItem getEntityItem() {
        return getEntityGroup().getEntityItem(0);
    }

    private String getEntityItemKey() {
        return m_strEntityItemKey;
    }

////////////////////

    public String dump(boolean _bBrief) {
        return getEntityGroup().dump(_bBrief);
    }

/**
 * Provide a standard means for logging
 */
    protected static void printf(String _s) {
        if(c_log != null) {
            c_log.printf("PMSyncEC:" + _s);
        }
    }

    protected static void debug(String _s) {
        if(c_debugMode == true) {
            printf(_s);
        }
    }

}
