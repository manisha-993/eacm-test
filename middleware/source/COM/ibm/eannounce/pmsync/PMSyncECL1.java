//
// Copyright (c) 2001-2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
//$Log: PMSyncECL1.java,v $
//Revision 1.18  2007/07/31 13:03:45  chris
//Rational Software Architect v7
//
//Revision 1.17  2005/03/30 01:24:59  dave
//fix maybe?
//
//Revision 1.16  2005/03/30 00:51:54  dave
//adding taxonomy object
//
//Revision 1.15  2004/05/11 17:16:32  gregg
//massive readjusting yeehaw
//
//Revision 1.14  2004/03/15 22:36:46  gregg
//sum updates
//
//Revision 1.13  2003/11/26 18:25:10  gregg
//just some comments for javadoc
//
//Revision 1.12  2003/08/12 16:34:34  gregg
//fix DEBUG_MODE
//
//Revision 1.11  2003/08/07 17:59:15  gregg
//compile fix
//
//Revision 1.10  2003/08/07 17:44:33  gregg
//ensure c_log != null before print
//
//Revision 1.9  2003/08/07 17:35:31  gregg
//some logging
//
//Revision 1.8  2003/05/29 22:40:48  gregg
//update
//
//Revision 1.7  2003/05/29 19:53:58  gregg
//back to RMI implementation
//
//Revision 1.6  2003/05/27 17:33:05  gregg
//make static constants protected
//
//Revision 1.5  2003/05/23 22:38:35  gregg
//use Properties instead of XProperties
//
//Revision 1.4  2003/05/20 19:34:32  gregg
//... more compile
//
//Revision 1.3  2003/05/20 19:30:32  gregg
//compile fix
//
//Revision 1.2  2003/05/20 19:23:51  gregg
//compile fix
//
//Revision 1.1  2003/05/20 19:14:36  gregg
//initial load
//
//

package COM.ibm.eannounce.pmsync;

import java.rmi.RemoteException;
import java.sql.SQLException;

import COM.ibm.eannounce.objects.CreateActionItem;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EANMetaEntity;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.TextAttribute;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;


/**
 * Convert a Level0Comp object from PM to an eannounce 'Engineering Change Level 1' Entity.
 * This object really reperesents ONE EC EntityItem.
 */
public final class PMSyncECL1 extends EANMetaEntity {

    // entitytypes + attcodes
    protected static final String ECL1_ENTTYPE              = PostECs.getMetaProperties().getProperty("ECL1_ENTTYPE");
    protected static final String ECL0ECL1_REL_ENTTYPE      = PostECs.getMetaProperties().getProperty("ECL0ECL1_REL_ENTTYPE");
    protected static final String ECL1_PNUMB_ATTCODE        = PostECs.getMetaProperties().getProperty("ECL1_PNUMB_ATTCODE");
    protected static final String ECL1_DESC_ATTCODE         = PostECs.getMetaProperties().getProperty("ECL1_DESC_ATTCODE");
    protected static final String ECL1_BASICNAME_ATTCODE    = PostECs.getMetaProperties().getProperty("ECL1_BASICNAME_ATTCODE");
    protected static final String ECL1_ID_ATTCODE           = PostECs.getMetaProperties().getProperty("ECL1_ID_ATTCODE");
    protected static final String ECL1_NAME_ATTCODE         = PostECs.getMetaProperties().getProperty("ECL1_NAME_ATTCODE");
    //
    protected static final String CR_ECL1_ACTIONKEY         = PostECs.getMetaProperties().getProperty("CR_ECL1_ACTIONKEY");


/**
 * Pull out ECL0 EntityGroup from database, and fill out all data. Then recursively set up ECL1's.
 */
    public PMSyncECL1(PMSyncECL0 _EANecl0, RemoteDatabaseInterface _rdi, Profile _prof, Level0Comp _comp) throws RemoteException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, SQLException {
        super(_EANecl0,_prof,_comp.m_strItemNumber);

        // log what we've got!!!
        debug("***New PMSyncECL1***");
        debug("comp.itemNumber:" + _comp.m_strItemNumber);
        debug("comp.basicName:" + _comp.m_strBasicName);
        debug("comp.description:" + _comp.m_strDescription);

        // 0) before we do anything, lets validate the integrity of the passed object ...
        validateComp(_comp);
        // 1) set up EC EntityGroup + EntityItem + Attributes
        setupData(_rdi,_comp);
    }

/**
 * Build our local objects.
 * Called from constructor.
 */
    private void setupData(RemoteDatabaseInterface _rdi, Level0Comp _comp) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, SQLException {
        EntityGroup egECL0 = getParentECL0().getEntityGroup();
        // Create a new EntityItem
        //ActionGroup ag = egECL0.getActionGroup();
   	//	EANActionItem eai = ag.getActionItem(CR_ECL1_ACTIONKEY);
  	//	CreateActionItem createAi = (CreateActionItem)eai;
        CreateActionItem createAi = _rdi.getCreateActionItem(null,getProfile(),CR_ECL1_ACTIONKEY);
  		EntityItem eiECL0 = getParentECL0().getEntityItem();
        EntityList elCreate = EntityList.getEntityList(_rdi,getProfile(),createAi,new EntityItem[]{eiECL0});
        EntityGroup egECL1 = elCreate.getEntityGroup(ECL1_ENTTYPE);
        EntityGroup egECL0ECL1 = elCreate.getEntityGroup(ECL0ECL1_REL_ENTTYPE);
        EntityItem eiECL1 = egECL1.getEntityItem(0);

        debug("setupData()--> dumping EntityItem ECL1 ~before~ put data:" + eiECL1.dump(false));

        // *** now the important part -- put all data from Level0EC!!
        ((TextAttribute)eiECL1.getAttribute(ECL1_PNUMB_ATTCODE)).put(_comp.m_strItemNumber);
        ((TextAttribute)eiECL1.getAttribute(ECL1_DESC_ATTCODE)).put(_comp.m_strDescription);
        ((TextAttribute)eiECL1.getAttribute(ECL1_BASICNAME_ATTCODE)).put(_comp.m_strBasicName);
        // NAME,ID --> store ec number??
        ((TextAttribute)eiECL1.getAttribute(ECL1_ID_ATTCODE)).put(_comp.m_strItemNumber);
        ((TextAttribute)eiECL1.getAttribute(ECL1_NAME_ATTCODE)).put(_comp.m_strItemNumber);
        // *** data put()'s done

        // put the newly created EntityItem in EntityGroup
        egECL1.putEntityItem(eiECL1);
        // check business rules -- let whatever Exceptions be thrown be thrown!!!
        eiECL1.checkBusinessRules();
        // now store it all locally
        putMeta(egECL1);
        putMeta(egECL0ECL1);
        debug("setupData()--> dumping EntityItem ECL1 ~after~ put data:" + eiECL1.dump(false));

        return;
    }

/**
 * Validate the passed Level0EC's data.
 * Called from constructor.
 * What does this mean?? (I only know that we need to do this, not what bad data will actually look like yet...)
 */
    private void validateComp(Level0Comp _comp) throws MiddlewareRequestException {
        String strKey = _comp.m_strItemNumber;
        if(_comp.m_strDescription == null) {
            throw new MiddlewareRequestException("EC:" + strKey + " - null " + ECL1_DESC_ATTCODE);
        }
        if(_comp.m_strBasicName == null) {
             throw new MiddlewareRequestException("EC:" + strKey + " - null " + ECL1_BASICNAME_ATTCODE);
        }
        return;
    }


/**
 * commit()
 */

    public void feedToPdh(RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException, RemoteException {
        if(((EntityGroup)getMeta(ECL0ECL1_REL_ENTTYPE)).getEntityItem(0) == null) {
            throw new MiddlewareRequestException("PMSyncEC:  ECL0ECL1 EntityItem is null!!!");
        }
        EntityItem eiRel = ((EntityGroup)getMeta(ECL0ECL1_REL_ENTTYPE)).getEntityItem(0);
        // use updated parent
        eiRel.removeUpLink(eiRel.getUpLink(0));
        eiRel.putUpLink(getParentECL0().getEntityItem());

        debug("committing EntityItem (eiRel) :" + eiRel.dump(false));

        eiRel.commit(null,_rdi);

        debug("After commit (eiRel), EntityID = " + eiRel.getEntityID());
    }


////////////////////
// Accessors, etc //
////////////////////

    protected PMSyncECL0 getParentECL0() {
        return (PMSyncECL0)getParent();
    }

    private void putECL1(PMSyncECL1 _ecl1) {
        putData(_ecl1);
    }

    protected EntityGroup getEntityGroup() {
        return (EntityGroup)getMeta(ECL1_ENTTYPE);
    }

    protected EntityItem getEntityItem() {
        return getEntityGroup().getEntityItem(0);
    }

    //PARENT relator!!
    private EntityGroup getRelatorEntityGroup() {
        return (EntityGroup)getMeta(ECL0ECL1_REL_ENTTYPE);
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
            PMSyncEC.printf("PMSyncECL1:" + _s);
        }
    }

    protected static void debug(String _s) {
        if(PMSyncEC.c_debugMode == true) {
            printf(_s);
        }
    }

}

