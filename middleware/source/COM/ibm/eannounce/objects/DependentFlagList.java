//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: DependentFlagList.java,v $
// Revision 1.22  2005/03/03 21:32:10  dave
// NEW_LINE cleanup
//
// Revision 1.21  2005/03/03 21:25:16  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.20  2005/02/14 17:18:33  dave
// more jtest fixing
//
// Revision 1.19  2004/01/13 17:56:33  dave
// more squeezing
//
// Revision 1.18  2003/04/17 22:47:04  gregg
// update...
//
// Revision 1.17  2003/04/16 23:26:36  gregg
// skip 'Retired' flags
//
// Revision 1.16  2003/01/23 23:55:34  gregg
// some changes to updatePdhMeta -- commented out part where we clean up lists...
//
// Revision 1.15  2002/11/06 22:38:18  gregg
// removed display statements
//
// Revision 1.14  2002/10/29 01:23:59  gregg
// in updatePdhMeta -> clean up dfg's b4 update by removing any w/out items
//
// Revision 1.13  2002/10/29 00:35:51  gregg
// some debug stmts in updatePdhMeta
//
// Revision 1.12  2002/10/28 20:48:21  gregg
// more updatePdhMeta for Trans/Attribute
//
// Revision 1.11  2002/10/28 18:39:42  gregg
// getDependentFlagGroup(strAttCode,strAttCode) method added
//
// Revision 1.10  2002/09/17 17:10:09  gregg
// removed setStateMachine on dfg's from here into dfg's themselves
//
// Revision 1.9  2002/09/16 22:56:30  gregg
// setValOnEffOn for profile
//
// Revision 1.8  2002/09/13 23:15:17  gregg
// some more dumping...
//
// Revision 1.7  2002/08/13 19:54:35  gregg
// getVersion() method
//
// Revision 1.6  2002/08/13 17:01:43  gregg
// modified creation of list to accurately represent unique att1->att2 mappings
//
// Revision 1.5  2002/08/07 22:31:54  gregg
// setStateMachine() on DependentFlagGroups
//
// Revision 1.4  2002/07/30 00:15:56  gregg
// purge all EG caches upon meta update
//
// Revision 1.3  2002/07/29 23:33:35  gregg
// PDH Meta Updates
//
// Revision 1.2  2002/07/03 20:11:35  gregg
// removeMeta method
//
// Revision 1.1  2002/06/26 18:08:08  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * This structure manages the Dependent flags contained within the context of an EntityGroup
 */
public class DependentFlagList extends EANMetaEntity {


    /**
     * DependentFlagList
     *
     * @param _eg
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public DependentFlagList(EntityGroup _eg, Profile _prof) throws MiddlewareRequestException {
        super(_eg, _prof, _eg.getEntityType() + "DFS");
        //fish out all the pairs of controlling + controllee MetaFlagAttributes
        //Hashtable hashPairs = new Hashtable();
        try {
            Vector vctKeys = new Vector();
            //1) setup keys
            for (int i = 0; i < _eg.getMetaAttributeCount(); i++) {
                if (_eg.getMetaAttribute(i) instanceof EANMetaFlagAttribute) {
                    EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) _eg.getMetaAttribute(i);
                    for (int j = 0; j < mfa.getMetaFlagCount(); j++) {
                        MetaFlag mf1 = mfa.getMetaFlag(j);
                        for (int k = 0; k < mf1.getControllerCount(); k++) {
                            MetaFlag mf2 = mf1.getController(k);
                            EANMetaFlagAttribute mfa1 = (EANMetaFlagAttribute) mf1.getParent();
                            EANMetaFlagAttribute mfa2 = (EANMetaFlagAttribute) mf2.getParent();
                            String strAttCode1 = mfa1.getAttributeCode();
                            String strAttCode2 = mfa2.getAttributeCode();
                            //if this is a new pairing
                            if (!vctKeys.contains(strAttCode1 + ":" + strAttCode2)) {
                                vctKeys.addElement(strAttCode1 + ":" + strAttCode2);
                                //hashPairs.put(strAttCode1,strAttCode2);
                            }
                        }
                    }
                }
            }
            //2) now fish out EANMetaFlagAttribute pairs for DependentFlagGroups...
            for (int i = 0; i < vctKeys.size(); i++) {
                DependentFlagGroup dfGroup = null;
                String strKey = (String) vctKeys.elementAt(i);
                StringTokenizer st = new StringTokenizer(strKey, ":");
                String strAttCode1 = st.nextToken();
                String strAttCode2 = st.nextToken();
                EANMetaFlagAttribute mfa1 = (EANMetaFlagAttribute) _eg.getMetaAttribute(strAttCode1);
                EANMetaFlagAttribute mfa2 = (EANMetaFlagAttribute) _eg.getMetaAttribute(strAttCode2);
                if (mfa1 == null) {
                    throw new MiddlewareRequestException("DependentFlagList:could not find attribute " + strAttCode1 + " in EntityGroup " + getEntityType() + " for role " + _prof.getRoleCode());
                }
                if (mfa2 == null) {
                    throw new MiddlewareRequestException("DependentFlagList:could not find attribute " + strAttCode2 + " in EntityGroup " + getEntityType() + " for role " + _prof.getRoleCode());
                }
                dfGroup = new DependentFlagGroup(this, _prof, mfa1, mfa2);
                putDependentFlagGroup(dfGroup);
            }
        } finally {
        }
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
        return (EntityGroup) getParent();
    }

    /**
     * putDependentFlagGroup
     *
     * @param _dfg
     *  @author David Bigelow
     */
    public void putDependentFlagGroup(DependentFlagGroup _dfg) {
        putMeta(_dfg);
        return;
    }

    /**
     * removeDependentFlagGroup
     *
     * @param _dfg
     *  @author David Bigelow
     */
    public void removeDependentFlagGroup(DependentFlagGroup _dfg) {
        removeMeta(_dfg);
        return;
    }

    /**
     * getDependentFlagGroup
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public DependentFlagGroup getDependentFlagGroup(int _i) {
        return (DependentFlagGroup) getMeta(_i);
    }

    /**
     * getDependentFlagGroup
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public DependentFlagGroup getDependentFlagGroup(String _s) {
        for (int i = 0; i < getDependentFlagGroupCount(); i++) {
            if (getDependentFlagGroup(i).toString().equals(_s)) {
                return getDependentFlagGroup(i);
            }
        }
        return null;
    }

    /**
     * get the DependentFlagGroup w/ attribute1, attribute2.
     *
     * @return DependentFlagGroup
     * @param _strAttCode1
     * @param _strAttCode2 
     */
    public DependentFlagGroup getDependentFlagGroup(String _strAttCode1, String _strAttCode2) {
        return getDependentFlagGroup(_strAttCode1 + ":" + _strAttCode2);
    }

    /**
     * getDependentFlagGroupCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getDependentFlagGroupCount() {
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
        for (int i = 0; i < getDependentFlagGroupCount(); i++) {
             sb.append(getDependentFlagGroup(i).dump(_brief) + NEW_LINE);
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
        return getKey();
    }

    /**
     * contains
     *
     * @param _dfg
     * @return
     *  @author David Bigelow
     */
    public boolean contains(DependentFlagGroup _dfg) {
        for (int i = 0; i < this.getDependentFlagGroupCount(); i++) {
            if (this.getDependentFlagGroup(i).toString().equals(_dfg.toString())) {
                return true;
            }
        }
        return false;
    }

    private String getDefaultTransitionCode() {
        return getEntityType() + "DFS";
    }

    private int getStateMachineCount() {
        int iCount = 0;
        for (int i = 0; i < getDependentFlagGroupCount(); i++) {
            if (getDependentFlagGroup(i).isStateMachine()) {
                iCount++;
            }
        }
        return iCount;
    }

    private int getNonStateMachineCount() {
        return getDependentFlagGroupCount() - getStateMachineCount();
    }

    /////////////////////////////////////////////// UPDATE META ///////////////////////////////////////////////////////////

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
        //grab an EG from the Database -- if we have done our job, then a cached one should work..
        EntityGroup eg_db = new EntityGroup(null, _db, getProfile(), getEntityType(), "Edit");
        DependentFlagList dfl_db = new DependentFlagList(eg_db, getProfile());
        boolean bUpdateTransAttr = false;
        boolean bExpire = false;

        // 1) in db, !in current object -> expire db records
        for (int i = 0; i < dfl_db.getDependentFlagGroupCount(); i++) {
            DependentFlagGroup dfg_db = dfl_db.getDependentFlagGroup(i);
            if (!this.contains(dfg_db)) {
                dfg_db.expirePdhMeta(_db);
            }
        }
        // 2a) in current object, !in db -> insert these records
        for (int i = 0; i < this.getDependentFlagGroupCount(); i++) {
            DependentFlagGroup dfg_this = this.getDependentFlagGroup(i);
            if (!dfl_db.contains(dfg_this)) {
                dfg_this.insertPdhMeta(_db);
            
            } else { // 2b) in db && in current object -> let the dfg figure out the changes for existing ones...
                dfg_this.updatePdhMeta(_db, dfl_db.getDependentFlagGroup(dfg_this.toString()));
            }
        }

        // 3) update Trans/Attribute for NON-stateMachines
        // a) none existed, now there are some...
        if (dfl_db.getNonStateMachineCount() == 0 && this.getNonStateMachineCount() > 0) {
            //if there are some that are NOT stateMachines...
            for (int i = 0; i < this.getDependentFlagGroupCount(); i++) {
                if (!this.getDependentFlagGroup(i).isStateMachine()) {
                    bUpdateTransAttr = true;
                    bExpire = false;
                    i = this.getDependentFlagGroupCount();
                    continue; //break
                }
            }
        }
        // b) used to exist, but not anymore...
        else if (dfl_db.getNonStateMachineCount() > 0 && this.getNonStateMachineCount() == 0) {
            for (int i = 0; i < dfl_db.getDependentFlagGroupCount(); i++) {
                if (!dfl_db.getDependentFlagGroup(i).isStateMachine()) {
                    bUpdateTransAttr = true;
                    bExpire = true;
                    i = dfl_db.getDependentFlagGroupCount();
                    continue; //break
                }
            }
        }
        if (bUpdateTransAttr) {
            updateTransAttribute(_db, bExpire, getEntityType(), "Entity", "L");
        }

        //Purge all Caches for this EntityGroup -- these ALL need to be refreshed, regardless of role or nls.
        getProfile().setValOnEffOn(_db.getDates().getNow(), _db.getDates().getNow());
        new MetaCacheManager(getProfile()).expireEGCacheAllRolesAllNls(_db, getEntityType());
        //Update our local EntityGroup reference
        getProfile().setValOnEffOn(_db.getDates().getNow(), _db.getDates().getNow());
        setParent(new EntityGroup(null, _db, getProfile(), getEntityType(), "Edit"));

    }

    /**
     * to simplify things a bit
     */
    private void updateTransAttribute(Database _db, boolean _bExpire, String _strLinkType2, String _strLinkCode, String _strLinkValue) throws MiddlewareException {
        String strValFrom = _db.getDates().getNow();
        String strValTo = (_bExpire ? strValFrom : _db.getDates().getForever());
        new MetaLinkAttrRow(getProfile(), "Trans/Attribute", getDefaultTransitionCode(), _strLinkType2, _strLinkCode, _strLinkValue, strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: DependentFlagList.java,v 1.22 2005/03/03 21:32:10 dave Exp $";
    }

} //DependentFlagList
