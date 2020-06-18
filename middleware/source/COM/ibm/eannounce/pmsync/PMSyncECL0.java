//
// Copyright (c) 2001-2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
//$Log: PMSyncECL0.java,v $
//Revision 1.27  2007/07/31 13:03:45  chris
//Rational Software Architect v7
//
//Revision 1.26  2004/05/24 15:53:01  joan
//catch Exception
//
//Revision 1.25  2004/05/11 17:16:32  gregg
//massive readjusting yeehaw
//
//Revision 1.24  2004/04/06 21:29:03  gregg
//update report columns
//
//Revision 1.23  2004/03/17 20:54:13  gregg
//make some methods static so we can reuse outside of class
//
//Revision 1.22  2004/03/17 16:18:28  gregg
//link EC to ECL0 logic (search for converse PN etc)
//
//Revision 1.21  2004/03/16 20:54:37  gregg
//ECL0TRANSDATE
//
//Revision 1.20  2004/03/15 22:36:46  gregg
//sum updates
//
//Revision 1.19  2003/11/26 18:25:10  gregg
//just some comments for javadoc
//
//Revision 1.18  2003/09/10 17:16:22  gregg
//CTO_DESC_ATTCODE, SBB_DESC_ATTCODE constants
//
//Revision 1.17  2003/08/26 16:53:02  gregg
//add sae.getDynaSearchTable(_rdi) flavour
//
//Revision 1.16  2003/08/25 20:22:32  dave
//Some cleanup on streamlining searchactionitem
//
//Revision 1.15  2003/08/12 16:34:34  gregg
//fix DEBUG_MODE
//
//Revision 1.14  2003/08/07 20:48:15  gregg
//more debug logging
//
//Revision 1.13  2003/08/07 17:59:15  gregg
//compile fix
//
//Revision 1.12  2003/08/07 17:44:33  gregg
//ensure c_log != null before print
//
//Revision 1.11  2003/08/07 17:35:31  gregg
//some logging
//
//Revision 1.10  2003/05/29 22:40:48  gregg
//update
//
//Revision 1.9  2003/05/29 19:53:58  gregg
//back to RMI implementation
//
//Revision 1.8  2003/05/28 19:30:46  gregg
//SBB_PNUMB_ATTCODE
//
//Revision 1.7  2003/05/28 17:27:37  gregg
//SBB_ENTTYPE constant
//
//Revision 1.6  2003/05/28 16:27:08  gregg
//create SearchActionItem from constructor (not from ActionGroup)
//
//Revision 1.5  2003/05/27 17:50:54  gregg
//static method searchForCTOs for reuse
//
//Revision 1.4  2003/05/27 17:33:05  gregg
//make static constants protected
//
//Revision 1.3  2003/05/23 22:38:35  gregg
//use Properties instead of XProperties
//
//Revision 1.2  2003/05/20 21:35:38  gregg
//cleanup
//
//Revision 1.1  2003/05/20 19:14:36  gregg
//initial load
//
//
//

package COM.ibm.eannounce.pmsync;

import java.rmi.RemoteException;
import java.sql.SQLException;

import COM.ibm.eannounce.objects.ActionGroup;
import COM.ibm.eannounce.objects.CreateActionItem;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EANMetaEntity;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.LinkActionItem;
import COM.ibm.eannounce.objects.RowSelectableTable;
import COM.ibm.eannounce.objects.SearchActionItem;
import COM.ibm.eannounce.objects.TextAttribute;
import COM.ibm.eannounce.objects.WorkflowException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;

/**
 * Convert a Level0BOM object from PM to an eannounce 'Engineering Change Level 0' Entity.
 * This object really reperesents ONE ECL0 EntityItem.
 */
public final class PMSyncECL0 extends EANMetaEntity {

    // entitytypes + attcodes
    protected static final String ECL0_ENTTYPE           = PostECs.getMetaProperties().getProperty("ECL0_ENTTYPE");
    protected static final String ECECL0_REL_ENTTYPE     = PostECs.getMetaProperties().getProperty("ECECL0_REL_ENTTYPE");
    protected static final String ECL0_PNUMB_ATTCODE     = PostECs.getMetaProperties().getProperty("ECL0_PNUMB_ATTCODE");
    protected static final String ECL0_DESC_ATTCODE      = PostECs.getMetaProperties().getProperty("ECL0_DESC_ATTCODE");
    protected static final String ECL0_ID_ATTCODE        = PostECs.getMetaProperties().getProperty("ECL0_ID_ATTCODE");
    protected static final String ECL0_NAME_ATTCODE      = PostECs.getMetaProperties().getProperty("ECL0_NAME_ATTCODE");
    protected static final String ECL0_TRANSDATE_ATTCODE = PostECs.getMetaProperties().getProperty("ECL0_TRANSDATE_ATTCODE");
    //
    protected static final String CR_ECL0_ACTIONKEY    = PostECs.getMetaProperties().getProperty("CR_ECL0_ACTIONKEY");
    protected static final String SR_CTO_ACTIONKEY     = PostECs.getMetaProperties().getProperty("SR_CTO_ACTIONKEY");
    protected static final String LINK_CTO_ACTIONKEY   = PostECs.getMetaProperties().getProperty("LINK_CTO_ACTIONKEY");
    //
    protected static final String CTO_PNUMB_ATTCODE    = PostECs.getMetaProperties().getProperty("CTO_PNUMB_ATTCODE");
    protected static final String CTO_ENTTYPE          = PostECs.getMetaProperties().getProperty("CTO_ENTTYPE");
    protected static final String CTO_PNUMB_SUFFIX     = PostECs.getMetaProperties().getProperty("CTO_PNUMB_SUFFIX");
    protected static final String CTO_ECL0_REL_ENTTYPE = PostECs.getMetaProperties().getProperty("CTO_ECL0_REL_ENTTYPE");
    protected static final String CTO_DESC_ATTCODE     = PostECs.getMetaProperties().getProperty("CTO_DESC_ATTCODE");

    //
    protected static final String SBB_ENTTYPE               = PostECs.getMetaProperties().getProperty("SBB_ENTTYPE");
    protected static final String SBB_PNUMB_ATTCODE         = PostECs.getMetaProperties().getProperty("SBB_PNUMB_ATTCODE");
    protected static final String SBB_DESC_ATTCODE          = PostECs.getMetaProperties().getProperty("SBB_DESC_ATTCODE");
    protected static final String SBB_TYPE_ATTCODE          = PostECs.getMetaProperties().getProperty("SBB_TYPE_ATTCODE");
    protected static final String SBB_PLANNINGRELEV_ATTCODE = PostECs.getMetaProperties().getProperty("SBB_PLANNINGRELEV_ATTCODE");


/**
 * Pull out ECL0 EntityGroup from database, and fill out all data. Then recursively set up ECL1's.
 */
    public PMSyncECL0(PMSyncEC _EANec, RemoteDatabaseInterface _rdi, Profile _prof, Level0BOM _bom) throws RemoteException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, SQLException {
        super(_EANec,_prof,_bom.m_strItemNumber);

        // log what we've got!!!
        debug("***New PMSyncECL0***");
        debug("bom.itemNumber:" + _bom.m_strItemNumber);
        debug("bom.description:" + _bom.m_strDescription);
        debug("bom.compList.size():" + _bom.m_vctCompList.size());

        // 0) before we do anything, lets validate the integrity of the passed object ...
        validateBOM(_bom);
        // 1) set up EC EntityGroup + EntityItem + Attributes
        setupData(_rdi,_bom);
        // 2) now set up children
        setupChildren(_rdi,_bom);
    }

/**
 * Build our local objects.
 * Called from constructor.
 */
    private void setupData(RemoteDatabaseInterface _rdi, Level0BOM _bom) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, SQLException {

        // Create a new EntityItem
        ActionGroup ag = getParentEC().getEntityGroup().getActionGroup();
        //EANActionItem eai = ag.getActionItem(CR_ECL0_ACTIONKEY);
  	//	CreateActionItem createAi = (CreateActionItem)eai;

        CreateActionItem createAi = _rdi.getCreateActionItem(null,getProfile(),CR_ECL0_ACTIONKEY);

  		EntityItem eiEC = getParentEC().getEntityItem();
        EntityList elCreate = EntityList.getEntityList(_rdi,getProfile(),createAi,new EntityItem[]{eiEC});
        EntityGroup egECL0 = elCreate.getEntityGroup(ECL0_ENTTYPE);
        EntityGroup egECECL0 = elCreate.getEntityGroup(ECECL0_REL_ENTTYPE);
        EntityItem eiECL0 = egECL0.getEntityItem(0);

        debug("setupData()--> dumping EntityItem ECL0 ~before~ put data:" + eiECL0.dump(false));

        // *** now the important part -- put all data from Level0EC!!
        ((TextAttribute)eiECL0.getAttribute(ECL0_PNUMB_ATTCODE)).put(_bom.m_strItemNumber);
        ((TextAttribute)eiECL0.getAttribute(ECL0_DESC_ATTCODE)).put(_bom.m_strDescription);
        // NAME,ID --> store ec number??
        ((TextAttribute)eiECL0.getAttribute(ECL0_ID_ATTCODE)).put(_bom.m_strItemNumber);
        ((TextAttribute)eiECL0.getAttribute(ECL0_NAME_ATTCODE)).put(_bom.m_strItemNumber);
        //
        ((TextAttribute)eiECL0.getAttribute(ECL0_TRANSDATE_ATTCODE)).put(getECTransDate());
        // *** data put()'s done

        // put the newly created EntityItem in EntityGroup
        egECL0.putEntityItem(eiECL0);
        // check business rules -- let whatever Exceptions be thrown be thrown!!!
        eiECL0.checkBusinessRules();
        // now store it all locally
        putMeta(egECL0);
        putMeta(egECECL0);
        // store this for execute!!!
        debug("setupData()--> dumping EntityItem ECL0 ~after~ put data:" + eiECL0.dump(false));
        return;
    }

/**
 * Validate the passed Level0EC's data.
 * Called from constructor.
 * What does this mean?? (I only know that we need to do this, not what bad data will actually look like yet...)
 */
    private void validateBOM(Level0BOM _bom) throws MiddlewareRequestException {
        String strKey = _bom.m_strItemNumber;
        if(_bom.m_strDescription == null) {
            throw new MiddlewareRequestException("EC:" + strKey + " - null " + ECL0_DESC_ATTCODE);
        }
        if(_bom.m_vctCompList == null) {
             throw new MiddlewareRequestException("EC:" + strKey + " - null children");
        }
        return;
    }

/**
 * Go through and setup child data.
 * Called from constructor.
 */
    private void setupChildren(RemoteDatabaseInterface _rdi, Level0BOM _bom) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, SQLException {
        for(int iComps = 0; iComps < _bom.m_vctCompList.size(); iComps++) {
            PMSyncECL1 ecl1 = new PMSyncECL1(this,_rdi,getProfile(),(Level0Comp)_bom.m_vctCompList.elementAt(iComps));
            putECL1(ecl1);
        }
        return;
    }


/**
 * commit()
 */

    public void feedToPdh(RemoteDatabaseInterface _rdi) throws LockException, EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        if(((EntityGroup)getMeta(ECECL0_REL_ENTTYPE)).getEntityItem(0) == null) {
            throw new MiddlewareRequestException("PMSyncEC: ECECL0 EntityItem is null!!!");
        }
        EntityItem eiRel = ((EntityGroup)getMeta(ECECL0_REL_ENTTYPE)).getEntityItem(0);
        //need to link to updated parent
        eiRel.removeUpLink(eiRel.getUpLink(0));
        eiRel.putUpLink(getParentEC().getEntityItem());

        debug("committing EntityItem (eiRel) :" + eiRel.dump(false));

        eiRel.commit(null,_rdi);

        debug("After commit (eiRel), EntityID = " + eiRel.getEntityID());

        // now children!!!
        debug("now going for children (" + getChildCount() + " children)");
        for(int i = 0; i < getChildCount(); i++) {
            getChild(i).feedToPdh(_rdi);
        }
        EntityGroup egSearchChildren = searchForCTOs(_rdi,getEntityGroup(),getEntityItem());
        if(egSearchChildren != null) {
            debug("egSearchChildren:" + egSearchChildren.dump(false));
       	 	//ActionGroup ag = egSearchChildren.getActionGroup();
        	//LinkActionItem lai = (LinkActionItem)ag.getActionItem(LINK_CTO_ACTIONKEY);
                LinkActionItem lai = _rdi.getLinkActionItem(null,getProfile(),LINK_CTO_ACTIONKEY);
        	lai.setParentEntityItems(new EntityItem[]{getEntityItem()});
        	lai.setChildEntityItems(egSearchChildren.getEntityItemsAsArray());
        	lai.setOption(LinkActionItem.OPT_DEFAULT);
        	try {
        		lai.rexec(_rdi,getProfile());
			} catch (WorkflowException we) {
				we.printStackTrace();
			}
        }

    }

    protected static final synchronized EntityGroup searchForCTOs(Database _db, EntityGroup _egECL0, EntityItem _eiECL0) throws  MiddlewareShutdownInProgressException, EANBusinessRuleException, SQLException, MiddlewareRequestException, MiddlewareException {
        try {
            return searchForCTOs(_db,null,_egECL0,_eiECL0);
        } catch(RemoteException exc) {
            // this will NEVER happen b/c we are using Database here!
        }
        return null;
    }

    protected static final synchronized EntityGroup searchForCTOs(RemoteDatabaseInterface _rdi, EntityGroup _egECL0, EntityItem _eiECL0) throws  MiddlewareShutdownInProgressException, RemoteException, EANBusinessRuleException, SQLException, MiddlewareRequestException, MiddlewareException {
        return searchForCTOs(null,_rdi,_egECL0,_eiECL0);
    }

    private static final synchronized EntityGroup searchForCTOs(Database _db, RemoteDatabaseInterface _rdi, EntityGroup _egECL0, EntityItem _eiECL0) throws MiddlewareShutdownInProgressException, RemoteException, EANBusinessRuleException, SQLException, MiddlewareRequestException, MiddlewareException {

        SearchActionItem sae = null;

        if(_db != null) {
            sae = _db.getSearchActionItem(null,_egECL0.getProfile(),SR_CTO_ACTIONKEY);
        } else {
            sae = _rdi.getSearchActionItem(null,_egECL0.getProfile(),SR_CTO_ACTIONKEY);
        }

        RowSelectableTable rst = null;
        if(_db != null) {
            rst = sae.getDynaSearchTable(_db);
        } else {
            rst = sae.getDynaSearchTable(_rdi);
        }

        for(int i = 0; i < rst.getRowCount(); i++) {
            if(rst.getRowKey(i).equals(CTO_ENTTYPE + ":" + CTO_PNUMB_ATTCODE)) {
                String strMatchPartNumber = _eiECL0.getAttribute(ECL0_PNUMB_ATTCODE).toString().substring(5,12);
                // ensure that last 3 chars are 'CTO'
                strMatchPartNumber = strMatchPartNumber.substring(0,4) + CTO_PNUMB_SUFFIX;
                rst.put(i,1,strMatchPartNumber);
                i = rst.getRowCount();
                continue;
            }
        }
        EntityList elSearch = null;

        if(_db != null) {
            elSearch = sae.executeAction(_db,_egECL0.getProfile());
        } else {
            elSearch = sae.rexec(_rdi,_egECL0.getProfile());
        }

        if(elSearch != null) {
            return elSearch.getEntityGroup(CTO_ENTTYPE);
        } else {
            System.err.println("PMSyncECL0.searchForCTOs(): elSearch is null!!!");
            return null;
        }
    }

////////////////////
// Accessors, etc //
////////////////////


    private int getChildCount() {
        return getDataCount();
    }

    private PMSyncECL1 getChild(int _i) {
        return (PMSyncECL1)getData(_i);
    }

    protected PMSyncEC getParentEC() {
        return (PMSyncEC)getParent();
    }

    private void putECL1(PMSyncECL1 _ecl1) {
        putData(_ecl1);
    }

    protected EntityGroup getEntityGroup() {
        return (EntityGroup)getMeta(ECL0_ENTTYPE);
    }

    protected EntityItem getEntityItem() {
        return getEntityGroup().getEntityItem(0);
    }

/**
 * ECL0PNUMB attribute
 */
    protected String getPartNumber() {
        return getPartNumber(getEntityItem());
    }

    protected static final String getPartNumber(EntityItem _eiECL0) {
        return _eiECL0.getAttribute(ECL0_PNUMB_ATTCODE).toString();
    }

    protected boolean isREV() {
        return isREV(getEntityItem());
    }

    protected static final boolean isREV(EntityItem _eiECL0) {
        String strSfx = getPartNumber(_eiECL0).substring(getPartNumber(_eiECL0).length()-3,getPartNumber(_eiECL0).length());
        return strSfx.equals("REV");
    }

    protected String getConversePN() {
        return getConversePN(getEntityItem());
    }

/**
 * if REV -> CTO; if CTO -> REV
 */
    protected static final String getConversePN(EntityItem _eiECL0) {
        String strConPN = null;
        // if itsa REV, then we want CTO
        if(isREV(_eiECL0)) {
            strConPN = getPartNumber(_eiECL0).substring(0,getPartNumber(_eiECL0).length()-3) + "CTO";
        } else {
            strConPN = getPartNumber(_eiECL0).substring(0,getPartNumber(_eiECL0).length()-3) + "REV";
        }
        return strConPN;
    }

    private String getECTransDate() {
        PMSyncEC pmEC = getParentEC();
        EntityItem eiEC = pmEC.getEntityItem();
        return eiEC.getAttribute(PMSyncEC.EC_TRANSDATE_ATTCODE).toString();
    }

    //PARENT relator!!
    private EntityGroup getRelatorEntityGroup() {
        return (EntityGroup)getMeta(ECECL0_REL_ENTTYPE);
    }

    //PARENT relator!!
    private EntityItem getRelatorEntityItem() {
        return getRelatorEntityGroup().getEntityItem(0);
    }

////////////////////

    public String dump(boolean _bBrief) {
        return getEntityGroup().dump(_bBrief);
    }

/**
 * Provide a standard means for logging
 */
    protected static void printf(String _s) {
        if(PMSyncEC.c_log != null) {
            PMSyncEC.printf("PMSyncECL0:" + _s);
        }
    }

    protected static void debug(String _s) {
        if(PMSyncEC.c_debugMode == true) {
            printf(_s);
        }
    }


}

