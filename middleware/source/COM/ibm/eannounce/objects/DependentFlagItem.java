//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: DependentFlagItem.java,v $
// Revision 1.33  2005/03/03 21:25:16  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.32  2005/02/14 17:18:33  dave
// more jtest fixing
//
// Revision 1.31  2005/01/18 21:33:09  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.30  2004/05/13 19:42:45  gregg
// fix for gbl7560
//
// Revision 1.29  2004/05/13 18:27:22  gregg
// use GBL7560 to grab transitioncode in update
//
// Revision 1.28  2004/04/22 18:13:18  gregg
// debugs
//
// Revision 1.27  2003/07/17 18:02:09  gregg
// refining StateTransition logic: attempt to set this object on DFItem if on exists in the MetaStatusAttribute object
//
// Revision 1.26  2003/05/11 02:04:40  dave
// making EANlists bigger
//
// Revision 1.25  2003/05/09 20:41:21  gregg
// removed some debugs
//
// Revision 1.24  2003/04/17 22:47:04  gregg
// update...
//
// Revision 1.23  2003/01/31 18:57:50  gregg
// more stmts null fix
//
// Revision 1.22  2003/01/31 18:45:02  gregg
// more debugging
//
// Revision 1.21  2003/01/31 18:20:19  gregg
// some debug stmts
//
// Revision 1.20  2003/01/30 21:53:17  gregg
// synchMetaFlagControllers method - call in updatePdhMeta
//
// Revision 1.19  2002/10/28 19:14:10  gregg
// some more updatePdhMeta logic for Trans/Attribute
//
// Revision 1.18  2002/10/25 18:17:34  gregg
// updatePdhMEta stuff....for Trans/Attribute
//
// Revision 1.17  2002/10/14 21:30:01  dave
// syntax fixes
//
// Revision 1.16  2002/10/14 19:45:37  dave
// Trapping a null pointer
//
// Revision 1.15  2002/09/17 17:42:55  gregg
// isStateMachine, getTransitionCode, updatePdhMeta tuning-up
//
// Revision 1.14  2002/09/17 00:33:37  gregg
// EntityType property on StateTransition
//
// Revision 1.13  2002/09/13 23:15:17  gregg
// some more dumping...
//
// Revision 1.12  2002/09/13 18:22:36  gregg
// StatTransition att must be a MetaStatusAttribute
//
// Revision 1.11  2002/09/13 18:04:05  gregg
// updatePdhMeta, expirePdhMeta methods
//
// Revision 1.10  2002/09/13 00:35:01  gregg
// updatePdhMeta,expirePdhMeta methods now public
//
// Revision 1.9  2002/09/11 22:39:08  gregg
// build StateTransition object from Database in getStateTransition() method
//
// Revision 1.8  2002/09/09 22:09:27  gregg
// start at getStateTransition -- still need to build the object (sets+check) from db
//
// Revision 1.7  2002/09/04 23:32:56  gregg
// setTransitionCode method
//
// Revision 1.6  2002/08/14 23:24:42  gregg
// getTransitionCode key for SM's
//
// Revision 1.5  2002/08/13 19:54:35  gregg
// getVersion() method
//
// Revision 1.4  2002/08/07 22:30:32  gregg
// isStateMachine() method
//
// Revision 1.3  2002/07/29 23:33:35  gregg
// PDH Meta Updates
//
// Revision 1.2  2002/06/27 17:02:13  gregg
// some comments for clarity
//
// Revision 1.1  2002/06/26 18:08:08  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import java.sql.ResultSet;

///////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////// DependentFlagItem //////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * DependenFlagItem manages the Dependent Flags for two MetaFlags (flag values) - a controller and a controllee -
 * within the context of a DependentFlagGroup.
 */
public class DependentFlagItem extends EANMetaEntity {

    private MetaFlag m_mf1 = null;
    private MetaFlag m_mf2 = null;

    private String NEW_LINE = "\n";
    private StateTransition m_stateTransition = null;

    /**
     * DependentFlagItem
     *
     * @param _dfg
     * @param _prof
     * @param _mf1
     * @param _mf2
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public DependentFlagItem(DependentFlagGroup _dfg, Profile _prof, MetaFlag _mf1, MetaFlag _mf2) throws MiddlewareRequestException {
        super(_dfg, _prof, _mf1.getFlagCode() + ":" + _mf2.getFlagCode());
        setFlag1(_mf1);
        setFlag2(_mf2);
    }

    /**
     * Is the parent DependentFlagGroup a StateMachine?
     *
     * @return boolean
     */
    public boolean isStateMachine() {
        try {
            DependentFlagGroup dfg = (DependentFlagGroup) getParent();
            return dfg.isStateMachine();
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
    }

    /**
     * set the "Controlling" flag
     *
     * @param _mf 
     */
    public void setFlag1(MetaFlag _mf) {
        m_mf1 = _mf;
    }

    /**
     * set the "Controlled" flag
     *
     * @param _mf 
     */
    public void setFlag2(MetaFlag _mf) {
        m_mf2 = _mf;
    }

    /**
     * get the "Controlling" flag
     *
     * @return MetaFlag
     */
    public MetaFlag getFlag1() {
        return m_mf1;
    }

    /**
     * get the "Controlled" flag
     *
     * @return MetaFlag
     */
    public MetaFlag getFlag2() {
        return m_mf2;
    }

    /**
     * getEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityType() {
        return getEntityGroup().getEntityType();
    }

    /**
     * getEntityGroup
     *
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getEntityGroup() {
        DependentFlagGroup dfg = (DependentFlagGroup) getParent();
        return dfg.getEntityGroup();
    }

    /**
     * <PRE>
     * Note: this will return:
     * 1) if ! stateMachine --> EntityType + "DFS"
     * 2) if there is a stateTransition object available for this DependentFlagItem --> the stateTransition's TransitionID
     *    (ie from database)
     * 3) else --> attrCode + flagCode1 + flagCode2
     * </PRE>
     *
     * @return String
     */
    public String getTransitionCode() {

        EANMetaFlagAttribute att1 = null;
        String strAttCode = null;
        
        if (!isStateMachine()) {
            //return getParentGroup().getTransitionCode();
            return getEntityType() + "DFS";
        }

        //else its a SM
        if (m_stateTransition != null) {
            return m_stateTransition.getTransitionID();
        }

        // else fabricate a "default"
        att1 = (EANMetaFlagAttribute) getFlag1().getParent();
        strAttCode = att1.getAttributeCode();
        return strAttCode + getFlag1().getFlagCode() + getFlag2().getFlagCode();
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _brief) {
        return toString();
    }

    /**
     * a unique identifier
     *
     * @return String
     */
    public String toString() {
        //return getKey();
        EANMetaFlagAttribute att1 = (EANMetaFlagAttribute) getFlag1().getParent();
        EANMetaFlagAttribute att2 = (EANMetaFlagAttribute) getFlag2().getParent();
        String strAttCode1 = att1.getAttributeCode();
        String strAttCode2 = att2.getAttributeCode();
        return getTransitionCode() + ":" + strAttCode1 + ":" + getFlag1().getFlagCode() + ":" + strAttCode2 + ":" + getFlag2().getFlagCode();
    }


    private DependentFlagGroup getParentGroup() {
        return (DependentFlagGroup) getParent();
    }

    /**
     * Because this is a distinct object from MetaFlag1, MetFlag2, we must update the m_elControllers in MF2
     * Remember, flag1 is the one w/ controllers (counter-intuitive)
     * Should only have to remove one here at the DFI level
     */
    private void synchMetaFlagControllers(boolean _bExpire) {

        EntityGroup eg = null;
        DependentFlagGroup dfg = null;
        EANMetaFlagAttribute att1 = null;
        EANMetaFlagAttribute att2 = null;
        MetaFlag mf1 = null;
        MetaFlag mf2 = null;

        if (_bExpire) {
            //find our controller and rip it out...
            for (int i = 0; i < getFlag1().getControllerCount(); i++) {
                MetaFlag mfController = getFlag1().getController(i);
                if (mfController.getKey().equals(getFlag2().getKey())) {
                    getFlag1().removeController(mfController);
                    i = getFlag1().getControllerCount();
                    continue; //break out of loop
                }
            }
        } else {
            getFlag1().addController(getFlag2());
        }
        eg = getEntityGroup();
        dfg = getParentGroup();
        att1 = (EANMetaFlagAttribute) eg.getMetaAttribute(dfg.getAttribute1().getAttributeCode());
        att2 = (EANMetaFlagAttribute) eg.getMetaAttribute(dfg.getAttribute2().getAttributeCode());
        mf1 = att1.getMetaFlag(getFlag1().getFlagCode());
        mf2 = att2.getMetaFlag(getFlag2().getFlagCode());
        for (int i = 0; i < mf1.getControllerCount(); i++) {
        }
        for (int i = 0; i < mf2.getControllerCount(); i++) {
        }
        return;
    }

    //////////////////////////////////////////// UPDATE META //////////////////////////////////////////////////////////////

    /**
     * updatePdhMeta
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public void updatePdhMeta(Database _db) throws java.sql.SQLException, MiddlewareException, MiddlewareRequestException {
        updatePdhMeta(_db, false);
    }

    /**
     * expirePdhMeta
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public void expirePdhMeta(Database _db) throws java.sql.SQLException, MiddlewareException, MiddlewareRequestException {
        updatePdhMeta(_db, true);
    }

    /**
     *
     */
    private void updatePdhMeta(Database _db, boolean _bExpire) throws java.sql.SQLException, MiddlewareException, MiddlewareRequestException {
        EANList eList = getMetaTransitionRowsForUpdate(_db, _bExpire);
        String strNow = _db.getDates().getNow();
        _db.debug(D.EBUG_SPEW, "begin DependentFlagItem.updatePDHMeta:" + toString() + "...");

        _db.getDates().getForever();
        for (int i = 0; i < eList.size(); i++) {
            MetaTransitionRow mtRow = (MetaTransitionRow) eList.getAt(i);
            mtRow.updatePdh(_db);
        }
        // if this IS a state machine, then take care of Trans/Attribute here...
        // - the reason for this is that a DependentFlagItem for stateMachine has a unique transcode, while
        //   other Dep.Flag Items share the entityType + "DFS' transitionCode.
        if (isStateMachine()) {
            //MLA Trans/Attribute Entity record
            // ** first see if this is already in db...
            boolean bTransEntityInDB = false;
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            try {
                try {
                    rs = _db.callGBL7503(new ReturnStatus(-1), getProfile().getEnterprise(), "Trans/Attribute", getTransitionCode(), getEntityType(), "Entity", strNow, strNow);
                    rdrs = new ReturnDataResultSet(rs);
                } finally {  
                    rs.close();
                    rs = null;
                    _db.freeStatement();
                    _db.isPending();
                }
                if (rdrs.getRowCount() > 0) {
                    bTransEntityInDB = true;
                }
                // a) if it IS in db, and this IS an expire ->> expire this record
                if (_bExpire && bTransEntityInDB) {
                    updateTransAttribute(_db, true, getEntityType(), "Entity", "L");
                }
                // b) if this is an update AND it is NOT already in database
                else if (!_bExpire && !bTransEntityInDB) {
                    updateTransAttribute(_db, false, getEntityType(), "Entity", "L");
                }

            } catch (Exception exc) {
                _db.debug(D.EBUG_ERR, "DependentFlagItem.updatePdhMeta a:" + exc.toString());
            } finally {
                _db.freeStatement();
                _db.isPending();
            }
        }

        //StateTransition
        if (isStateMachine()) {
            try {
                if (_bExpire) {
                    getStateTransition(_db).expirePdhMeta(_db);
                
                } else {
                    getStateTransition(_db).updatePdhMeta(_db);
                }
            } catch (Exception exc) {
                _db.debug(D.EBUG_ERR, "DependentFlagItem.updatePdhMeta b:" + exc.toString());
            }
        }
  
        this.synchMetaFlagControllers(_bExpire);
        _db.debug(D.EBUG_SPEW, "... end DependentFlagItem.updatePDHMeta:" + toString());
    }

    /**
     * Get the MetaTransitionRows for any changes relavent to MetaTransition table.
     */
    private EANList getMetaTransitionRowsForUpdate(Database _db, boolean _bExpire) throws java.sql.SQLException, MiddlewareException, MiddlewareRequestException {

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        EANList eList = new EANList(EANMetaEntity.LIST_SIZE);

        MetaTransitionRow mtRow = null;
        String strTransitionCode = null;
        EANMetaFlagAttribute att1 = null;
        EANMetaFlagAttribute att2 = null;
        String strAttCode1 = null;
        String strAttCode2 = null;

        String strNow = _db.getDates().getNow();
        String strForever = _db.getDates().getForever();
        String strValEffFrom = strNow;
        String strValEffTo = strForever;
        String strEnterprise = getProfile().getEnterprise();

        if (_bExpire) {
            strValEffTo = strNow;
        }

        strTransitionCode = getTransitionCode();
        att1 = (EANMetaFlagAttribute) getFlag1().getParent();
        att2 = (EANMetaFlagAttribute) getFlag2().getParent();
        strAttCode1 = att1.getAttributeCode();
        strAttCode2 = att2.getAttributeCode();

        try {
            rs = _db.callGBL7560(new ReturnStatus(-1), strEnterprise, getEntityType(), strAttCode1, strAttCode2);
            rdrs = new ReturnDataResultSet(rs);
        } finally {
            rs.close();
            rs = null;
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
        if (rdrs.getRowCount() > 0) {
            String s1 = rdrs.getColumn(0, 0).trim();
            if (!s1.equals(strTransitionCode.trim())) {
                strTransitionCode = s1;
            }
        }

        mtRow = new MetaTransitionRow(getProfile(), strTransitionCode, strAttCode1, getFlag1().getFlagCode(), strAttCode2, getFlag2().getFlagCode(), strValEffFrom, strValEffTo, strValEffFrom, strValEffTo, 2);
        eList.put(mtRow);
        return eList;
    }

    /**
     * resetStateTransition
     *
     *  @author David Bigelow
     */
    protected void resetStateTransition() {
        m_stateTransition = null;
    }

    /**
     * setStateTransition
     *
     * @param _st
     *  @author David Bigelow
     */
    protected void setStateTransition(StateTransition _st) {
        m_stateTransition = _st;
    }

    /**
     * get the StateTransition Object associated w/ this transitionCode
     *
     * @return StateTransition
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public StateTransition getStateTransition(Database _db) throws java.sql.SQLException, MiddlewareException {

        DependentFlagGroup dfgParent = null;
        EANMetaFlagAttribute emfAtt = null;

        if (m_stateTransition != null) {
            return m_stateTransition;
        }
        if (!isStateMachine()) {
            throw new MiddlewareException("This Dependent Flag Item is not a State Machine.");
        }

        dfgParent = (DependentFlagGroup) getParent();
        //re: for State Transitions, att1 == att2
        emfAtt = dfgParent.getAttribute1();
        emfAtt.getAttributeCode();

        if (!(emfAtt instanceof MetaStatusAttribute)) {
            throw new MiddlewareException("DependentFlagItem.getStateTransition():attribute must be a Status Attribute");
        }

        m_stateTransition = new StateTransition(_db, getProfile(), (MetaStatusAttribute) emfAtt, getTransitionCode(), getFlag1().getFlagCode(), getFlag2().getFlagCode());

        if (m_stateTransition == null) {
            _db.debug(D.EBUG_SPEW, "DependentFlagItem.getStateTransition() is null!!");
        } else {
            //    _db.debug(D.EBUG_SPEW,"DependentFlagItem.getStateTransition():" + m_stateTransition.dump(false));
        }
        return m_stateTransition;
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: DependentFlagItem.java,v 1.33 2005/03/03 21:25:16 dave Exp $";
    }

    /**
     * to simplify things a bit
     */
    private void updateTransAttribute(Database _db, boolean _bExpire, String _strLinkType2, String _strLinkCode, String _strLinkValue) throws MiddlewareException {
        String strValFrom = _db.getDates().getNow();
        String strValTo = (_bExpire ? strValFrom : _db.getDates().getForever());
        new MetaLinkAttrRow(getProfile(), "Trans/Attribute", getTransitionCode(), _strLinkType2, _strLinkCode, _strLinkValue, strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
    }

}
