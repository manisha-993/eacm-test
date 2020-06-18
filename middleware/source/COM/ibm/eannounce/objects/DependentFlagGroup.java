//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: DependentFlagGroup.java,v $
// Revision 1.25  2005/03/03 21:25:16  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.24  2005/02/14 17:18:33  dave
// more jtest fixing
//
// Revision 1.23  2004/04/22 19:42:47  gregg
// fix contains logic to speed up thangs
//
// Revision 1.22  2004/04/22 18:57:12  gregg
// more debugs
//
// Revision 1.21  2004/04/22 18:13:18  gregg
// debugs
//
// Revision 1.20  2003/07/17 23:29:41  gregg
// more setStateTransition
//
// Revision 1.19  2003/07/17 18:02:09  gregg
// refining StateTransition logic: attempt to set this object on DFItem if on exists in the MetaStatusAttribute object
//
// Revision 1.18  2003/04/24 18:32:15  dave
// getting rid of traces and system out printlns
//
// Revision 1.17  2003/04/17 22:47:04  gregg
// update...
//
// Revision 1.16  2003/01/23 21:45:50  gregg
// setParentList method
//
// Revision 1.15  2002/10/28 20:48:21  gregg
// more updatePdhMeta for Trans/Attribute
//
// Revision 1.14  2002/10/28 19:14:10  gregg
// some more updatePdhMeta logic for Trans/Attribute
//
// Revision 1.13  2002/10/28 18:46:28  gregg
// getDependentFlagItem(strFlagCode,strFlagCode) method
//
// Revision 1.12  2002/10/25 23:46:04  gregg
// getDependentFlagItem(String) method
//
// Revision 1.11  2002/09/17 17:12:12  gregg
// now setStateMachine in this class
//
// Revision 1.10  2002/09/13 23:15:17  gregg
// some more dumping...
//
// Revision 1.9  2002/08/14 23:22:31  gregg
// removed getTransitionCode() --> this should really be in DependentFlagItem object only
//
// Revision 1.8  2002/08/13 19:54:35  gregg
// getVersion() method
//
// Revision 1.7  2002/08/13 18:03:39  gregg
// only add DFI's to DFG's if the attcodes match up
//
// Revision 1.6  2002/08/07 22:28:02  gregg
// isStateMAchine(), setStateMachine() methods
//
// Revision 1.5  2002/07/29 23:50:02  gregg
// fix to insertPdhMEta
//
// Revision 1.4  2002/07/29 23:33:35  gregg
// PDH Meta Updates
//
// Revision 1.3  2002/07/03 20:11:35  gregg
// removeMeta method
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

///////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////// DependentFlagGroup /////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * DependentFlagGroup manages the Dependent Flags for a pair of two MetaAttributes within the 
 * context of a DependentFlagList.
 */
public class DependentFlagGroup extends EANMetaEntity {

    private EANMetaFlagAttribute m_emfa1 = null;
    private EANMetaFlagAttribute m_emfa2 = null;
    private boolean m_bStateMachine = false;
 
    /**
     * DependentFlagGroup
     *
     * @param _dfl
     * @param _prof
     * @param _mfa1
     * @param _mfa2
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public DependentFlagGroup(DependentFlagList _dfl, Profile _prof, EANMetaFlagAttribute _mfa1, EANMetaFlagAttribute _mfa2) throws MiddlewareRequestException {
        super(_dfl, _prof, _mfa1.getAttributeCode() + ":" + _mfa2.getAttributeCode());
        setAttribute1(_mfa1);
        setAttribute2(_mfa2);
        // flag a stateMachine or not
        if (getAttribute1().getAttributeCode().equals(getAttribute2().getAttributeCode()) && getAttribute1().getAttributeType().equals("S")) {
            setStateMachine(true);
        }
        //fish out all of the values here...
        for (int i = 0; i < _mfa1.getMetaFlagCount(); i++) {
            MetaFlag mf1 = _mfa1.getMetaFlag(i);
            for (int j = 0; j < mf1.getControllerCount(); j++) {
                MetaFlag mf2 = mf1.getController(j);
                EANMetaFlagAttribute parent1 = (EANMetaFlagAttribute) mf1.getParent();
                EANMetaFlagAttribute parent2 = (EANMetaFlagAttribute) mf2.getParent();
                String strAttCode1 = parent1.getAttributeCode();
                String strAttCode2 = parent2.getAttributeCode();
                //only add this if the attcodes match up`
                if (strAttCode1.equals(getAttribute1().getAttributeCode()) && strAttCode2.equals(getAttribute2().getAttributeCode())) {
                    DependentFlagItem dfi = new DependentFlagItem(this, _prof, mf1, mf2);
                    // dig out StateTransition Object
                    if (isStateMachine()) {
                        // this should be the case, but lets check anyways to be sure...
                        // also remember that at this point attcode1 == attcode2
                        if (getAttribute1() instanceof MetaStatusAttribute) {
                            for (int iSM = 0; iSM < ((MetaStatusAttribute) getAttribute1()).getStateTransitionCount(); iSM++) {
                                StateTransition st = ((MetaStatusAttribute) getAttribute1()).getStateTransition(iSM);
                                if (st.getFlag1().equals(mf1.getFlagCode()) && st.getFlag2().equals(mf2.getFlagCode())) {
                                    dfi.setStateTransition(((MetaStatusAttribute) getAttribute1()).getStateTransition(iSM));
                                }
                            }
                        }
                    }
                    putDependentFlagItem(dfi);
                }
            }
        }
    }

    /**
     * setParentList
     *
     * @param _dfl
     *  @author David Bigelow
     */
    public void setParentList(DependentFlagList _dfl) {
        this.setParent(_dfl);
    }

    /**
     * setStateMachine
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setStateMachine(boolean _b) {
        m_bStateMachine = _b;
    }

    /**
     * Does this DependentFlagGroup represent a Statemachine?
     *
     * @return boolean
     */
    public boolean isStateMachine() {
        return m_bStateMachine;
    }

    /**
     * set the "Controlling" attribute
     *
     * @param _emfa 
     */
    public void setAttribute1(EANMetaFlagAttribute _emfa) {
        m_emfa1 = _emfa;
    }

    /**
     * set the "Controlled" attribute
     *
     * @param _emfa 
     */
    public void setAttribute2(EANMetaFlagAttribute _emfa) {
        m_emfa2 = _emfa;
    }

    /**
     * get the "Controlling" attribute
     *
     * @return EANMetaFlagAttribute
     */
    public EANMetaFlagAttribute getAttribute1() {
        return m_emfa1;
    }

    /**
     * get the "Controlled" attribute
     *
     * @return EANMetaFlagAttribute
     */
    public EANMetaFlagAttribute getAttribute2() {
        return m_emfa2;
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
        DependentFlagList dfl = (DependentFlagList) getParent();
        return dfl.getEntityGroup();
    }

    /**
     * putDependentFlagItem
     *
     * @param _dfi
     *  @author David Bigelow
     */
    public void putDependentFlagItem(DependentFlagItem _dfi) {
        putMeta(_dfi);
        return;
    }

    /**
     * removeDependentFlagItem
     *
     * @param _dfi
     *  @author David Bigelow
     */
    public void removeDependentFlagItem(DependentFlagItem _dfi) {
        removeMeta(_dfi);
        return;
    }

    /**
     * getDependentFlagItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public DependentFlagItem getDependentFlagItem(int _i) {
        return (DependentFlagItem) getMeta(_i);
    }

    /**
     * getDependentFlagItem
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public DependentFlagItem getDependentFlagItem(String _s) {
        return (DependentFlagItem) getMeta(_s);
    }

    /**
     * Get the DependentFlagItem with flagcode1, flagcode2.
     *
     * @return DependentFlagItem
     * @param _strFlagCode1
     * @param _strFlagCode2 
     */
    public DependentFlagItem getDependentFlagItem(String _strFlagCode1, String _strFlagCode2) {
        return (DependentFlagItem) getMeta(_strFlagCode1 + ":" + _strFlagCode2);
    }

    /**
     * getDependentFlagItemCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getDependentFlagItemCount() {
        return getMetaCount();
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _brief) {
        StringBuffer sb = new StringBuffer(toString() + NEW_LINE);
        for (int i = 0; i < getDependentFlagItemCount(); i++) {
            sb.append(getDependentFlagItem(i).dump(_brief) + NEW_LINE);
        }
        return sb.toString();
    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        //return getKey();
        return getAttribute1().getAttributeCode() + ":" + getAttribute2().getAttributeCode();
    }
 
    /**
     * contains
     *
     * @param _dfi
     * @return
     *  @author David Bigelow
     */
    public boolean contains(DependentFlagItem _dfi) {
        if (getDependentFlagItem(_dfi.getKey()) == null) {
            return false;
        }
        return true;
    }

    /**
     * for convenience -- but ONLY if this is NOT a StateTransition
     *
     * @return String
     */
    protected String getTransitionCode() {
        if (!isStateMachine()) {
            return getEntityType() + "DFS";
        }
        return null;
    }
    /////////////////////////////////////// UPDATE META ///////////////////////////////////////////////////////////////////

    /**
     * expire ALL DependentFlagItems contained in this group
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    protected void expirePdhMeta(Database _db) throws java.sql.SQLException, MiddlewareException, MiddlewareRequestException {
        for (int i = 0; i < getDependentFlagItemCount(); i++) {
            getDependentFlagItem(i).expirePdhMeta(_db);
        }
    }

    /**
     * update changes relative to a 'fresh' DependentFlagGroup from database
     *
     * @param _db
     * @param _dfg_db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    protected void updatePdhMeta(Database _db, DependentFlagGroup _dfg_db) throws java.sql.SQLException, MiddlewareException, MiddlewareRequestException {
        _db.debug(D.EBUG_SPEW, "begin DependentFlagGroup.updatePDHMeta:" + toString() + "count:" + _dfg_db.getDependentFlagItemCount() + "...");
        // 1) in db, !in current object -> expire the db record.
        for (int i = 0; i < _dfg_db.getDependentFlagItemCount(); i++) {
            _db.debug(D.EBUG_SPEW, "DependentFlagGroup.updatePDHMeta:checking item [" + i + "] for expire:" + _dfg_db.getDependentFlagItem(i).toString());
            if (!this.contains(_dfg_db.getDependentFlagItem(i))) {
                _db.debug(D.EBUG_SPEW, "DependentFlagGroup.updatePDHMeta: ok -- expiring this guy!");
                _dfg_db.getDependentFlagItem(i).expirePdhMeta(_db);
            }
        }
        // 2) in current object, !in db -> insert current record.
        for (int i = 0; i < this.getDependentFlagItemCount(); i++) {
            _db.debug(D.EBUG_SPEW, "DependentFlagGroup.updatePDHMeta:checking item [" + i + "] for update:" + this.getDependentFlagItem(i).toString());
            if (!_dfg_db.contains(this.getDependentFlagItem(i))) {
                _db.debug(D.EBUG_SPEW, "DependentFlagGroup.updatePDHMeta: ok -- updating this guy!");
                this.getDependentFlagItem(i).updatePdhMeta(_db);
            }
        }
        // 3) in db && in current object -> do nothing.
        _db.debug(D.EBUG_SPEW, "... end DependentFlagGroup.updatePDHMeta:" + toString());
    }


    /**
     * this is a new group to be inserted
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    protected void insertPdhMeta(Database _db) throws java.sql.SQLException, MiddlewareException, MiddlewareRequestException {
        // 1) in db, !in current object -> expire the db record.
        for (int i = 0; i < this.getDependentFlagItemCount(); i++) {
            this.getDependentFlagItem(i).updatePdhMeta(_db);
        }
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: DependentFlagGroup.java,v 1.25 2005/03/03 21:25:16 dave Exp $";
    }

} //DependentFlagList
