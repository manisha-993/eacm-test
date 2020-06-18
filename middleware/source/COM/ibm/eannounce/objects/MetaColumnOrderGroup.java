//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaColumnOrderGroup.java,v $
// Revision 1.85  2014/04/17 18:27:40  wendy
// RCQ 216919 EACM BH - Add New Attributes to Search
//
// Revision 1.84  2013/08/06 20:31:19  wendy
// change debug level for 'could not locate column order attribute' msg
//
// Revision 1.83  2010/11/08 18:49:31  wendy
// Reuse string objects to reduce memory requirements
//
// Revision 1.82  2010/11/03 19:58:56  wendy
// ColumnOrder needs to check for relatortags too
//
// Revision 1.81  2008/08/08 21:41:44  wendy
// CQ00006067-WI : LA CTO - More support for QueryAction
//
// Revision 1.80  2008/02/01 22:10:07  wendy
// Cleanup RSA warnings
//
// Revision 1.79  2007/10/31 18:45:40  wendy
// Added check for null before rs.close()
//
// Revision 1.78  2007/07/17 17:25:36  wendy
// more debug output
//
// Revision 1.77  2007/06/04 19:36:47  wendy
// Added info to warning msg
//
// Revision 1.76  2005/03/07 21:23:22  dave
// Jtest Syntax
//
// Revision 1.75  2005/03/07 21:10:26  dave
// Jtest cleanup
//
// Revision 1.74  2005/02/25 17:53:04  gregg
// fixing MetacolumnOrderGroup build, remove debugs!
//
// Revision 1.73  2004/12/15 17:43:08  gregg
// nothing
//
// Revision 1.72  2004/12/08 00:27:55  gregg
// fix in getEntityTypeFromParent()
//
// Revision 1.71  2004/11/24 19:30:05  gregg
// fix null getParentEntityGroup thang
//
// Revision 1.70  2004/11/23 22:56:13  gregg
// more colorders for Search Actions
//
// Revision 1.69  2004/11/23 18:20:20  gregg
// more for Column Orders on SearchActionItems
//
// Revision 1.68  2004/11/22 23:01:06  gregg
// MetaColumnOrderGroup on SearchBinder
//
// Revision 1.67  2004/11/18 19:39:05  gregg
// fix in Constructor to grab the correct EntityGroup....
//
// Revision 1.66  2004/11/15 22:35:22  gregg
// work on column orders for ActionItems where parent.entitytype == child.entitytype
//
// Revision 1.65  2004/11/15 19:37:36  gregg
// add RelTag property to structure
//
// Revision 1.64  2004/11/04 18:02:10  gregg
// fix
//
// Revision 1.63  2004/11/04 16:53:37  gregg
// skip null/missing EntityGroups and report error for EnittyList/ActionItem type colorder
//
// Revision 1.62  2004/11/04 16:52:04  gregg
// lower sum debug levels
//
// Revision 1.61  2004/11/03 19:15:17  gregg
// storing MetaColumnOrderGroup object in EANActionItem; built on parent EntityList, where applicaple only
//
// Revision 1.60  2004/11/03 00:02:16  gregg
// more ActionItem stuff
//
// Revision 1.59  2004/11/02 23:45:19  gregg
// mnore work on colorder by actionitem
//
// Revision 1.58  2004/11/02 22:14:05  gregg
// more action item
//
// Revision 1.57  2004/11/02 21:44:19  gregg
// more MetaColumnOrder logic based on ActionItemKey
//
// Revision 1.56  2004/11/02 00:36:49  gregg
// work on ActionItem-based MetaColumnOrders
//
// Revision 1.55  2004/04/09 19:37:21  joan
// add duplicate method
//
// Revision 1.54  2003/12/17 17:22:30  gregg
// hasHiddenItems()
//
// Revision 1.53  2003/12/16 22:09:17  gregg
// getMetaColumnOrderGroupForMatrix() in rollback().
//
// Revision 1.52  2003/12/16 21:05:30  gregg
// fix for building MetaColumnOrderItems from gbl7541 (Matrix)
//
// Revision 1.51  2003/12/16 20:36:44  gregg
// iCheckDefaults param for GBL7541
//
// Revision 1.50  2003/12/16 18:56:09  gregg
// some more key-fixing
//
// Revision 1.49  2003/12/16 18:51:28  gregg
// more tweaking for Matrix...
//
// Revision 1.48  2003/12/16 18:34:52  gregg
// some more tweaking for use w/ Matrix
//
// Revision 1.47  2003/12/16 18:24:34  gregg
// ClassCastException in commit
//
// Revision 1.46  2003/12/16 00:42:48  gregg
// constructor fix
//
// Revision 1.45  2003/12/16 00:13:05  gregg
// setting up for use w/ MatrixList
//
// Revision 1.44  2003/12/11 18:59:02  dave
// trying to add column order to search in jui
//
// Revision 1.43  2003/07/01 17:10:59  gregg
// DEAFULT --> DEFAULT
//
// Revision 1.42  2003/04/03 23:24:58  gregg
// always update default records regardless of isPending or not
//
// Revision 1.41  2003/04/03 18:37:19  gregg
// fix for rollback
//
// Revision 1.40  2003/04/03 00:23:23  gregg
// fix for resetToDefaults
//
// Revision 1.39  2003/04/02 18:07:57  gregg
// resetToDefaults
//
// Revision 1.38  2003/03/28 19:33:47  gregg
// _db.commit();
//
// Revision 1.37  2003/03/28 00:36:24  gregg
// saveClonedItems
//
// Revision 1.36  2003/03/27 23:24:55  gregg
// go back to rollback from database
//
// Revision 1.35  2003/03/27 22:54:13  gregg
// more cleanup
//
// Revision 1.34  2003/03/27 20:06:02  gregg
// 2nd MetaColumnOrderGroup Constructor
//
// Revision 1.33  2003/03/27 19:21:51  gregg
// compile fix
//
// Revision 1.32  2003/03/27 18:59:18  gregg
// changes to getMetaColumnOrderGroup to avoid passing EnttiyGroup object
//
// Revision 1.31  2003/03/27 00:14:51  gregg
// debugs stmts for GBL7541
//
// Revision 1.30  2003/03/26 18:16:31  gregg
// default updates
//
// Revision 1.29  2003/03/26 01:14:40  gregg
// default order update logic
//
// Revision 1.28  2003/03/26 00:00:57  gregg
// tying off apply MetaColumnOrder to RST
//
// Revision 1.27  2003/03/25 21:32:17  gregg
// compikle fix for gbl7541 parms
//
// Revision 1.26  2003/03/25 21:23:28  gregg
// changes to 7541 to grab all attributes for an entitytype
//
// Revision 1.25  2003/03/25 01:25:41  gregg
// fix for rollback
//
// Revision 1.24  2003/03/21 22:00:53  gregg
// clip parent's parent + parent's data on rollback (we need parent EntityGroup though)
//
// Revision 1.23  2003/03/21 21:02:19  gregg
// order list by columnorder property in buildData
//
// Revision 1.22  2003/03/21 00:40:40  gregg
// return true on canCreate
//
// Revision 1.21  2003/03/21 00:17:08  gregg
// canCreate returns true
//
// Revision 1.20  2003/03/20 21:16:43  gregg
// remove EntityType, AttributeCode, AttirbuteType columns
//
// Revision 1.19  2003/03/20 21:06:32  gregg
// canCreate == false
//
// Revision 1.18  2003/03/14 22:47:18  gregg
// changes to update/commit
//
// Revision 1.17  2003/03/14 18:51:10  gregg
// avoid serializing parents in rollback method
//
// Revision 1.16  2003/03/14 18:03:43  gregg
// update moved down into item + use db, rdi methods (not MetaRow objects directly)
//
// Revision 1.15  2003/03/13 23:42:06  gregg
// ok, compile fix for real this time
//
// Revision 1.14  2003/03/13 23:39:33  gregg
// compile fix
//
// Revision 1.13  2003/03/13 23:11:09  gregg
// hasChanges method
//
// Revision 1.12  2003/03/13 22:51:11  gregg
// rollback
//
// Revision 1.11  2003/03/13 18:45:41  gregg
// remove addColumn, removeColumn methods from EANCommitableTableTemplate interface
//
// Revision 1.10  2003/03/13 18:28:18  gregg
// return an EANMetaEntity on performSort so we can sort & construct table in one step
//
// Revision 1.9  2003/03/12 23:43:02  gregg
// sorting list ...
//
// Revision 1.8  2003/03/12 23:22:56  gregg
// free db resources!!
//
// Revision 1.7  2003/03/12 23:02:28  gregg
// getTable method returns a new MetaColumnOrderTable
//
// Revision 1.6  2003/03/12 21:14:44  gregg
// use MetaColOrder table + updates
//
// Revision 1.5  2003/03/11 23:27:53  gregg
// is visible column.
// NOTE: still getting attr order the 'old' way until table implementaion is fully tested, etc
//
// Revision 1.4  2003/03/11 22:22:15  gregg
// Grab Attributes from E.G.'s getColumnList! This means that we are REALLY grabbing attributes for ent2type of relators/assocs also!
//
// Revision 1.3  2003/03/11 00:42:27  gregg
// getCommitableTable method
//
// Revision 1.2  2003/03/10 21:02:27  gregg
// include Database in Constructor for later use
//
// Revision 1.1  2003/03/10 20:41:41  gregg
// initial load
//
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.Stopwatch;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Hashtable;
import java.rmi.RemoteException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Manage a collection of MetaColumnOrderItems<BR>
 * NOTE: this object can currently be applied EntityGroup and MatrixList.<BR>
 *       + for EntityGroup, we are ordering on columns of EANMetaAttributes. <BR>
 *             - EntityType = EntityGroup.EntityType<BR>
 *             - Attributecode = EANMetaAttribute.AttributeCode<BR>
 *             - MetaColumnOrderItem.getKey() == EntityType + ":" + AttributeCode<BR>
 *       + for MatrixList, we are ordering on columns of MatrixGroups<BR>
 *             - EntityType = "Matrix" + MatrixList.getParentEntityGroup().getEntityType().<BR>
 *             - Attributecode = MatrixGroup.getKey(). [this is a Relator EntityType]. <BR>
 *             - MetaColumnOrderItem.getKey() == AttributeCode (above).<BR>
 */
public final class MetaColumnOrderGroup extends EANMetaEntity implements EANCommitableTableTemplate {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    private static final int DEFAULT_SEQ = 999;

    /**************************
     * Table Column Constants *
     **************************/
    // Entity Description
    protected static final String ENTDESC_KEY = MetaColumnOrderItem.ENTDESC_KEY;
    /**
     * FIELD
     */
    protected static final String ENTDESC_DESC = MetaColumnOrderItem.ENTDESC_DESC;
    // Attribute Description
    /**
     * FIELD
     */
    protected static final String ATTDESC_KEY = MetaColumnOrderItem.ATTDESC_KEY;
    /**
     * FIELD
     */
    protected static final String ATTDESC_DESC = MetaColumnOrderItem.ATTDESC_DESC;
    // Column Order
    /**
     * FIELD
     */
    protected static final String COLORDER_KEY = MetaColumnOrderItem.COLORDER_KEY;
    /**
     * FIELD
     */
    protected static final String COLORDER_DESC = MetaColumnOrderItem.COLORDER_DESC;
    // Is Visible
    /**
     * FIELD
     */
    protected static final String VISIBLE_KEY = MetaColumnOrderItem.VISIBLE_KEY;
    /**
     * FIELD
     */
    protected static final String VISIBLE_DESC = MetaColumnOrderItem.VISIBLE_DESC;
    //
    /**
     * FIELD
     */
    protected static final String MATRIX_KEY_PREFIX = "Matrix";
    //
    private boolean m_bUpdateDefaults = false;
    private byte[] m_byteArrayItems = null;

    /**
     * MetaColumnOrderGroup
     *
     * @param _db
     * @param _eme
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public MetaColumnOrderGroup(Database _db, EANMetaEntity _eme) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_eme, null, "MetaColumnOrderGroup:Generic");

        try {

            //
            ///// Build Columns
            // Entity Desc
            MetaLabel ml1 = new MetaLabel(null, getProfile(), ENTDESC_KEY, ENTDESC_DESC, SimpleTextAttribute.class);
            MetaLabel ml2 = new MetaLabel(null, getProfile(), ATTDESC_KEY, ATTDESC_DESC, SimpleTextAttribute.class);
            MetaLabel ml3 = new MetaLabel(null, getProfile(), COLORDER_KEY, COLORDER_DESC, SimpleTextAttribute.class);
            MetaLabel ml4 = new MetaLabel(null, getProfile(), VISIBLE_KEY, VISIBLE_DESC, SimplePicklistAttribute.class);
            ml1.putShortDescription(ENTDESC_DESC);
            putMeta(ml1);

            // Attribute Desc
            ml2.putShortDescription(ATTDESC_DESC);
            putMeta(ml2);

            // Column Order
            ml3.putShortDescription(COLORDER_DESC);
            putMeta(ml3);

            // Visible
            ml4.putShortDescription(VISIBLE_DESC);
            putMeta(ml4);

            ///// End Build Columns
            buildData(_db, _eme);

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * Encapsulate all Data-building here so we can rebuild on rollback
     */
    private void buildData(Database _db, EANMetaEntity _emeParent) throws SQLException, MiddlewareException, MiddlewareRequestException {

        Stopwatch sw1 = new Stopwatch();

        try {

            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;

            String strEntityType = getEntityTypeFromParent();
            String strEnterprise = getProfile().getEnterprise();
            int iOPWGID = getProfile().getOPWGID();
            int iCheckDefaults = 0; // dont check

            resetData();

            D.ebug(D.EBUG_DETAIL, "Building MetaColumnOrderGroup for:" + strEntityType);


            // Only check defaults for EntityGroup.
            if (_emeParent instanceof EntityGroup) {
                iCheckDefaults = 1;
            }
            if (_emeParent instanceof QueryGroup) {
                iCheckDefaults = 0; // dont check these view types
            }

            if (_emeParent instanceof EntityList) {
                String strActionItemKeyParent = ((EntityList) _emeParent).getParentActionItem().getActionItemKey();
                try {
                    rs = _db.callGBL7566(new ReturnStatus(-1), strEnterprise, iOPWGID, strActionItemKeyParent);
                    rdrs = new ReturnDataResultSet(rs);
                }finally {
					if (rs != null){
						rs.close();
						rs = null;
					}
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
            } else if (_emeParent instanceof SearchBinder) {

                SearchBinder searchBinder = (SearchBinder) _emeParent;
                EANActionItem aiParent = searchBinder.getParentActionItem();
                String strActionItemKeyParent = "SB:" + aiParent.getActionItemKey();

                try {
                    rs = _db.callGBL7566(new ReturnStatus(-1), strEnterprise, iOPWGID, strActionItemKeyParent);
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

            }

            if (rdrs == null || rdrs.getRowCount() == 0) {
                try {
                    rs = _db.callGBL7541(new ReturnStatus(-1), strEnterprise, iOPWGID, strEntityType, iCheckDefaults);
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
            }

            // lets first put any col order definitions that are defined in the MetaColOrderTable.
            // BUT
            // only if the attributes exist in this entity.

            sw1.start();

            //attempt to use less memory, hang onto strings already used
	        Hashtable memTbl = new Hashtable();
	        // preload "C", used frequently
	        memTbl.put("C", "C");
	
            //RDRS_LOOP : 
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strAttributeCode = EANUtility.reuseObjects(memTbl,rdrs.getColumn(row, 1));
                int iOrder = rdrs.getColumnInt(row, 2);
                boolean bVisible = (rdrs.getColumnInt(row, 3) != 0);
                String strDefault = EANUtility.reuseObjects(memTbl,rdrs.getColumn(row, 4));
                String strRelTag = EANUtility.reuseObjects(memTbl,rdrs.getColumn(row, 5));

                strEntityType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(row, 0));
                
                _db.debug(D.EBUG_SPEW, "GBL7541 answers=" + strEntityType + ":" + strAttributeCode + "," + iOrder + "," + bVisible + "," + strDefault + ":" + strRelTag);

                try {
                    // careful here ... thses MUST be EANMetaFoundations (getMeta really returns an EANFoundation)
                    EANMetaFoundation emf = null;
                    if (_emeParent instanceof EntityGroup) {
                        EntityGroup egParent = (EntityGroup) _emeParent;
                        emf = egParent.getMetaAttribute(strAttributeCode);
                        _db.debug(D.EBUG_SPEW, "ColOrder for EntityGroup:" + strEntityType + "," + strAttributeCode + " = " + iOrder);
                        if(egParent.isRelator() && !strRelTag.equals("R")){ //RCQ216919
                            _db.debug(D.EBUG_SPEW, "Override reltag for Relator EntityGroup:" + strEntityType + " from "+strRelTag+" to R");
                        	strRelTag = EANUtility.reuseObjects(memTbl,"R"); // default is "C" if opicm.metacolorder does not have a reltag value
                        }
                    } else if (_emeParent instanceof EntityList) {
                        EntityList elParent = (EntityList) _emeParent;
                        EntityGroup eg = (strRelTag.equals("P") ? elParent.getParentEntityGroup() : elParent.getEntityGroup(strEntityType));
                        _db.debug(D.EBUG_SPEW, "ColOrder for EntityList:" + strEntityType + "," + strAttributeCode + " = " + iOrder);
                        //
                        if ((eg != null && !eg.getEntityType().equals(strEntityType)) || eg == null) {
                            eg = elParent.getEntityGroup(strEntityType);
                        }
                        // LOOK AT THIS LATER .... WHY IS IT NOT THe RIGHT EG????
                        if (eg == null) {
                            D.ebug(D.EBUG_ERR, "ERROR! Could not find EntityGroup for MetaColumnOrderGroup on EntityList:" + strEntityType);
                        } else {
                            emf = eg.getMetaAttribute(strAttributeCode);
                        }
                    } else if (_emeParent instanceof MatrixList) {
                        emf = ((MatrixList) _emeParent).getMatrixGroup(strAttributeCode);
                    } else if (_emeParent instanceof SearchBinder) {
                        SearchBinder sb = (SearchBinder) _emeParent;
                        emf = sb.getMetaAttribute(strEntityType, strAttributeCode);
                        //
                    } else {
                        emf = (EANMetaFoundation) _emeParent.getMeta(strAttributeCode);
                    }
                    if (emf != null) {
                        putData(new MetaColumnOrderItem(this, emf, iOrder, bVisible, strRelTag));
                    } else {
                        _db.debug(D.EBUG_DETAIL, "1 MetaColumnOrderGroup:could not locate column order attribute:" +
                        	strAttributeCode+" for:" + strEntityType+" parent: "+_emeParent.getClass().getName());
                    }

                } catch (Exception exc) {
                    exc.getMessage();
                    _db.debug(D.EBUG_DETAIL, "2 MetaColumnOrderGroup:could not locate column order attribute:" +
                    	strAttributeCode+" for:" + strEntityType+" parent: "+_emeParent.getClass().getName());
                }
            }

            //_db.debug("GB - sw1 took:" + sw1.finish());
            // OK, now lets go through and put any left over attributes into the group in any particular order...
            if (_emeParent instanceof EntityGroup) {           	
                EntityGroup egParent = (EntityGroup) _emeParent;
                for (int i = 0; i < egParent.getMetaAttributeCount(); i++) {
                    EANMetaAttribute ema = egParent.getMetaAttribute(i);
                    String strColItemKey = MetaColumnOrderItem.getKeyFromFoundation(ema);                

                    String strRelTag = EANUtility.reuseObjects(memTbl,"C");

                    //if already exists -> skip it
                    // try, C, P, and R!!!!
                    if (getMetaColumnOrderItem(strColItemKey) != null) {
                        continue;
                    }
                    if (getMetaColumnOrderItem(strColItemKey + ":C") != null) {
                        continue;
                    }
                    if (getMetaColumnOrderItem(strColItemKey + ":P") != null) {
                        continue;
                    }
                    if (getMetaColumnOrderItem(strColItemKey + ":R") != null) {
                        continue;
                    }

                    // att doesnt have a colorderItem, so create one....
                    if (egParent.isParentLike()) {
                        strRelTag = EANUtility.reuseObjects(memTbl,"P");
                    } else if (egParent.isRelator()) {
                        strRelTag = EANUtility.reuseObjects(memTbl,"R");
                    }
                    putMetaColumnOrderItem(new MetaColumnOrderItem(this, ema, DEFAULT_SEQ, true, strRelTag));
                }
            } else if (_emeParent instanceof MatrixList) {
                MatrixList mlParent = (MatrixList) _emeParent;
                for (int i = 0; i < mlParent.getMatrixGroupCount(); i++) {
                    MatrixGroup mg = mlParent.getMatrixGroup(i);
                    String strColItemKey = MetaColumnOrderItem.getKeyFromFoundation(mg);
                    //if already exists -> skip it
                    // try, C, P, and R!!!!
                    if (getMetaColumnOrderItem(strColItemKey) != null) {
                        continue;
                    }
                    if (getMetaColumnOrderItem(strColItemKey + ":C") != null) {
                        continue;
                    }
                    if (getMetaColumnOrderItem(strColItemKey + ":P") != null) {
                        continue;
                    }
                    if (getMetaColumnOrderItem(strColItemKey + ":R") != null) {
                        continue;
                    }
                    // att doesnt have a colorderItem, so create one....
                    putMetaColumnOrderItem(new MetaColumnOrderItem(this, mg, DEFAULT_SEQ, true, 
                    		EANUtility.reuseObjects(memTbl,"C")));
                }
            } else if (_emeParent instanceof SearchBinder) {
                /*
                SearchBinder sbParent = (SearchBinder)_emeParent;
                for(int i = 0; i < mlParent.getMatrixGroupCount(); i++) {
                    MatrixGroup mg = mlParent.getMatrixGroup(i);
                    String strColItemKey = MetaColumnOrderItem.getKeyFromFoundation(mg);
                    //if already exists -> skip it
                    if(getMetaColumnOrderItem(strColItemKey) != null) {
                        continue;
                    }
                    // att doesnt have a colorderItem, so create one....
                    putMetaColumnOrderItem(new MetaColumnOrderItem(this,mg,DEFAULT_SEQ,true,"C"));
                }
                */
            } else {
                for (int i = 0; i < _emeParent.getMetaCount(); i++) {
                    EANMetaFoundation emf = (EANMetaFoundation) _emeParent.getMeta(i);
                    String strColItemKey = MetaColumnOrderItem.getKeyFromFoundation(emf);
                    //if already exists -> skip it
                    if (getMetaColumnOrderItem(strColItemKey) != null) {
                        continue;
                    }
                    // att doesnt have a colorderItem, so create one....
                    putMetaColumnOrderItem(new MetaColumnOrderItem(this, emf, DEFAULT_SEQ, true, 
                    		EANUtility.reuseObjects(memTbl,"C")));
                }
            }
            
            memTbl.clear();

            //
            performSort(COLORDER_KEY, true);

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
       
    } //end buildData method

    /**
     * Because the 'EntityType' field can mean different things for different Object Classes, keep the mapping logic
     * for this String all here in one place for ensured consistency.
     *
     * @return String
     */
    protected String getEntityTypeFromParent() {
        EANMetaEntity emeParent = (EANMetaEntity) getParent();
        String strEntityType = null;
        if (emeParent instanceof EntityGroup) {
            strEntityType = ((EntityGroup) emeParent).getEntityType();
        } else if (emeParent instanceof MatrixList) {
            strEntityType = MATRIX_KEY_PREFIX + ((MatrixList) emeParent).getParentEntityGroup().getEntityType();
        } else if (emeParent instanceof EntityList) {
            strEntityType = ((EntityList) emeParent).getParentActionItem().getActionItemKey();
        } else {
            strEntityType = emeParent.getKey();
        }
        //System.out.println("getEntityTypeFromParent():" + strEntityType  + ": class is:" + emeParent.getClass().getName());
        return strEntityType;
    }

    /**********************************
     * Misc public Accessors/Mutators *
     **********************************/
    /**
     * Get a ~new~ Table for this MetaColumnOrderGroup
     *
     * @return MetaColumnOrderTable
     * @param _strTitle
     */
    public MetaColumnOrderTable getTable(String _strTitle) {
        return new MetaColumnOrderTable(this, _strTitle);
    }

    /**
     * getMetaColumnOrderItemCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getMetaColumnOrderItemCount() {
        return getDataCount();
    }

    /**
     * getMetaColumnOrderItem
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    public MetaColumnOrderItem getMetaColumnOrderItem(String _strKey) {
        return (MetaColumnOrderItem) getData(_strKey);
    }

    /**
     * getMetaColumnOrderItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public MetaColumnOrderItem getMetaColumnOrderItem(int _i) {
        return (MetaColumnOrderItem) getData(_i);
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
     * getEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityType() {
        String s = null;
        if (getParent() instanceof EntityGroup) {
            s = getEntityGroup().getEntityType();
        } else {
            s = getEntityTypeFromParent();
        }
        return s;
    }

    private void putMetaColumnOrderItem(MetaColumnOrderItem _mcoi) {
        putData(_mcoi);
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer sb = new StringBuffer();
        sb.append("======== MetaColumnOrderGroup:" + getKey() + " ========" + NEW_LINE);
        sb.append("EntityType:" + getEntityType());
        sb.append("MetaColumnOrderItemCount:" + getMetaColumnOrderItemCount()+ NEW_LINE);
        if (!_bBrief) {
            for (int i = 0; i < getMetaColumnOrderItemCount(); i++) {
                sb.append(getMetaColumnOrderItem(i).dump(_bBrief));
            }
        }
        return sb.toString();
    }

    /**
     * Are there any hidden MetaColumnItems?
     *
     * @return boolean
     */
    public boolean hasHiddenItems() {
        for (int i = 0; i < getMetaColumnOrderItemCount(); i++) {
            if (!getMetaColumnOrderItem(i).isVisible()) {
                return true;
            }
        }
        return false;
    }

    /******************************
     * EANCommitableTableTemplate *
     *
     * @return boolean
     ******************************/
    public boolean hasChanges() {
        for (int i = 0; i < getMetaColumnOrderItemCount(); i++) {
            if (getMetaColumnOrderItem(i).pendingChanges()) {
                return true;
            }
        }
        return false;
    }

    private byte[] cloneItems() {
        byte[] ba = null;
        ByteArrayOutputStream baosObject = null;
        ObjectOutputStream oosObject = null;
        try {
            //
            for (int i = 0; i < getData().size(); i++) {
                MetaColumnOrderItem mcoi = (MetaColumnOrderItem) getData(i);
                mcoi.setTransientParent();
                mcoi.setParent(null);
            }

            try {
                baosObject = new ByteArrayOutputStream();
                oosObject = new ObjectOutputStream(baosObject);
                oosObject.writeObject(getData());
                oosObject.flush();
                oosObject.reset();
                ba = new byte[baosObject.size()];
                ba = baosObject.toByteArray();
            } finally {
            	if (oosObject != null){
            		oosObject.close();
            	}
                if (baosObject != null){
                	baosObject.close();
                }
            }
            //
            for (int i = 0; i < getData().size(); i++) {
                MetaColumnOrderItem mcoi = (MetaColumnOrderItem) getData(i);
                //System.out.println("cloneItems() [" + i + "] = " + mcoi.getKey() + ":" + mcoi.getColumnOrder());
                mcoi.setParent(this);
                mcoi.resetTransientParent();
            }
            //
        } catch (IOException ioe) {
            System.err.println("MetaColumnOrderGroup.cloneItems Exception:" + ioe);
        }
        if (ba == null) {
            System.out.println("MetaColumnOrderGroup.cloneItems - byte array is null!!");
        } 
        return ba;
    }

    /**
     * saveClonedItems
     *
     *  @author David Bigelow
     */
    protected void saveClonedItems() {
        m_byteArrayItems = cloneItems();
    }

    private EANList getClonedItems() {
        EANList el = null;
        ByteArrayInputStream baisObject = null;
        ObjectInputStream oisObject = null;
        try {
            try {
                baisObject = new ByteArrayInputStream(m_byteArrayItems);
                oisObject = new ObjectInputStream(baisObject);
                el = (EANList) oisObject.readObject();
            } finally {
            	if (oisObject != null){
            		oisObject.close();
            	}
            	if (baisObject!= null){
            		baisObject.close();
            	}
            }
        } catch (Exception e) {
            System.err.println("MetaColumnOrderGroup.getClonedItems Exception:" + e);
        }
        return el;
    }

    /**
     * (non-Javadoc)
     * rollback
     *
     * @see COM.ibm.eannounce.objects.EANCommitableTableTemplate#rollback(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface)
     */
    public void rollback(Database _db, RemoteDatabaseInterface _rdi) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {

        resetData();
        if (m_byteArrayItems != null) {
            EANList el = getClonedItems();
            for (int i = 0; i < el.size(); i++) {
                MetaColumnOrderItem mcoi = (MetaColumnOrderItem) el.getAt(i);
                //System.out.println("rollback() [" + i + "] = " + mcoi.getKey() + ":" + mcoi.getColumnOrder());
                mcoi.setParent(this);
            }
            setData(el);
        } else {
            if (_db != null) {
                buildData(_db, (EANMetaEntity) getParent());
            } else {
                MetaColumnOrderGroup mcog_refresh = null;
                if (getParent() instanceof MatrixList) {
                    MatrixList ml = (MatrixList) getParent();
                    String strActionItemKey = ml.getParentActionItem().getActionItemKey();
                    String strEntityType = ml.getParentEntityGroup().getEntityType();
                    mcog_refresh = _rdi.getMetaColumnOrderGroupForMatrix(strEntityType, strActionItemKey, getProfile());
                } else if (getParent() instanceof QueryGroup) {
                    mcog_refresh = _rdi.getMetaColumnOrderGroup(getEntityGroup().getEntityType(), getProfile(),true);
                } else if (getParent() instanceof EntityGroup) {
                    mcog_refresh = _rdi.getMetaColumnOrderGroup(getEntityGroup().getEntityType(), getProfile());
                }else {
                    mcog_refresh = _rdi.getMetaColumnOrderGroup(getEntityTypeFromParent(), getProfile());
                }
                setData(mcog_refresh.getData());
                for (int i = 0; i < getData().size(); i++) {
                    MetaColumnOrderItem mcoi = (MetaColumnOrderItem) getData().getAt(i);
                    mcoi.setParent(this);
                }
            }
        }
    }

    /**
     * This will effectively remove all column orders for this Enterprise/OPWGID/EntityType from the database and
     * use the current default orders.
     *
     * @param _db
     * @param _rdi
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     */
    public void resetToDefaults(Database _db, RemoteDatabaseInterface _rdi) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        // 1) remove current from database
        if (_db != null) {
            _db.debug(D.EBUG_DETAIL, "callGBL7549(" + getProfile().getEnterprise() + ":" + getProfile().getOPWGID() + ":" + getEntityType() + ")");
            _db.callGBL7549(new ReturnStatus(-1), getProfile().getEnterprise(), getProfile().getOPWGID(), getEntityType());
        } else if (_rdi != null) {
            _rdi.remoteGBL7549(getProfile().getEnterprise(), getProfile().getOPWGID(), getEntityType());
        }
        // make sure we rollback to whats in database!
        m_byteArrayItems = null;
        // 2) rebuid data from database
        rollback(_db, _rdi);
    }

    /**
     * (non-Javadoc)
     * getRowList
     *
     * @see COM.ibm.eannounce.objects.EANSimpleTableTemplate#getRowList()
     */
    public EANList getRowList() {
        return getData();
    }

    /**
     * (non-Javadoc)
     * getColumnList
     *
     * @see COM.ibm.eannounce.objects.EANSimpleTableTemplate#getColumnList()
     */
    public EANList getColumnList() {
        return getMeta();
    }

    /**
     * (non-Javadoc)
     * getRow
     *
     * @see COM.ibm.eannounce.objects.EANSimpleTableTemplate#getRow(java.lang.String)
     */
    public EANTableRowTemplate getRow(String _strKey) {
        return getMetaColumnOrderItem(_strKey);
    }

    /**
     * (non-Javadoc)
     * canEdit
     *
     * @see COM.ibm.eannounce.objects.EANCommitableTableTemplate#canEdit()
     */
    public boolean canEdit() {
        return true;
        //return getEntityGroup().canEdit();
    }

    /**
     * (non-Javadoc)
     * canCreate
     *
     * @see COM.ibm.eannounce.objects.EANCommitableTableTemplate#canCreate()
     */
    public boolean canCreate() {
        //return getEntityGroup().canCreate();
        return true;
    }

    /**
     * No addRow() for MetaColumnOrderGroup
     *
     * @return boolean
     */
    public boolean addRow() {
        return false;
    }
    /**
     * No removeRow() for MetaColumnOrderGroup
     *
     * @param _strRowKey
     */
    public void removeRow(String _strRowKey) {
    }

    /**
     * (non-Javadoc)
     * commit
     *
     * @see COM.ibm.eannounce.objects.EANCommitableTableTemplate#commit(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface)
     */
    public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
        // 1) get 'fresh' MetaColOrderGroup from database...
        //EntityGroup eg = (EntityGroup)getParent();
        // 2) Update changes if applicable
        for (int i = 0; i < this.getMetaColumnOrderItemCount(); i++) {
            MetaColumnOrderItem mcoi_this = this.getMetaColumnOrderItem(i);
            if (mcoi_this.pendingChanges()) {
                mcoi_this.updateMetaColOrderTable(_db, _rdi);
            }

            if (isUpdateDefaults()) {
                mcoi_this.updateMetaColOrderTableDefaults(_db, _rdi);
            }
        }
    }

    /**
     * Order the list of items by the specified columnkey.
     *
     * @return EANMetaEntity
     * @param _strKey
     * @param _bAscending
     */
    public EANMetaEntity performSort(String _strKey, boolean _bAscending) {
        MetaColumnOrderItem[] aC = new MetaColumnOrderItem[getMetaColumnOrderItemCount()];
        EANComparator ec = new EANComparator(_bAscending);
        for (int i = 0; i < getMetaColumnOrderItemCount(); i++) {
            MetaColumnOrderItem mcoi = getMetaColumnOrderItem(i);
            mcoi.setCompareField(_strKey);
            aC[i] = mcoi;
        }
        Arrays.sort(aC, ec);
        resetData();
        for (int i = 0; i < aC.length; i++) {
            putData(aC[i]);
        }
        return this;
    }

    /**
     * Set whether or not this guy will update the Default Column Orders
     *
     * @param _b
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    protected void setUpdateDefaults(boolean _b) throws MiddlewareRequestException {
        if (_b && !getProfile().hasRoleFunction(Profile.ROLE_FUNCTION_ATTR_ORDER_DEFAULT)) {
            throw new MiddlewareRequestException("Your Profile does not have authority to update Default Column Orders!");
        }
        m_bUpdateDefaults = _b;
    }

    /**
     * isUpdateDefaults
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isUpdateDefaults() {
        return m_bUpdateDefaults;
    }

    /**
     * duplicate
     *
     * @param _strKey
     * @param _iDup
     * @return
     *  @author David Bigelow
     */
    public boolean duplicate(String _strKey, int _iDup) {
        return false;
    }

}
