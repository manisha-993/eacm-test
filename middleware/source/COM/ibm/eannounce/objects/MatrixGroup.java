//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MatrixGroup.java,v $
// Revision 1.76  2010/09/15 17:44:51  wendy
// Create a unique new entity for each matrix item, was losing meta info for bg color in UI when id=-1 was reused
//
// Revision 1.75  2010/07/12 18:14:59  wendy
// use constant in msg
//
// Revision 1.74  2009/05/19 12:23:17  wendy
// Support dereference for memory release
//
// Revision 1.73  2009/01/12 13:21:00  wendy
// Check for rs != null before rs.close()
//
// Revision 1.72  2005/11/04 14:52:11  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.71  2005/03/04 21:35:51  dave
// Jtest work
//
// Revision 1.70  2005/01/31 18:37:57  gregg
// gbl0004 now gbl0006
//
// Revision 1.69  2005/01/20 22:57:00  gregg
// cleaning up after the scourge of Rule51
//
// Revision 1.68  2005/01/20 21:31:26  gregg
// fix
//
// Revision 1.67  2005/01/20 21:17:40  gregg
// fixes
//
// Revision 1.66  2005/01/20 21:08:25  gregg
// sum more rule51 for link
//
// Revision 1.65  2005/01/20 19:17:03  gregg
// some Rule51 rrk
//
// Revision 1.64  2005/01/18 23:01:07  gregg
// setRule51Group on MetaLink when it flies by in getMetaLink()
//
// Revision 1.63  2005/01/10 21:47:49  joan
// work on multiple edit
//
// Revision 1.62  2005/01/05 19:24:08  joan
// add new method
//
// Revision 1.61  2004/12/17 21:42:54  joan
// fix on matrix
//
// Revision 1.60  2004/11/12 01:16:23  joan
// work on search picklist
//
// Revision 1.59  2004/11/05 18:57:02  joan
// fixes
//
// Revision 1.58  2004/11/05 17:37:50  joan
// work on picklist
//
// Revision 1.57  2004/06/22 16:12:06  joan
// work on matrix
//
// Revision 1.56  2004/06/11 20:57:24  joan
// work on feature matrix
//
// Revision 1.55  2004/06/10 21:27:12  joan
// more work on special matrix
//
// Revision 1.54  2004/06/08 20:57:34  joan
// exception
//
// Revision 1.53  2004/06/08 20:52:13  joan
// work on special matrix
//
// Revision 1.50  2004/04/09 19:37:21  joan
// add duplicate method
//
// Revision 1.49  2003/09/19 22:35:32  dave
// unraveling matrix lock commit, etc
//
// Revision 1.48  2003/09/19 21:42:28  dave
// O.K. lets see what we broken for this one
//
// Revision 1.47  2003/08/28 16:28:07  joan
// adjust link method to have link option
//
// Revision 1.46  2003/06/25 18:44:01  joan
// move changes from v111
//
// Revision 1.45  2003/04/23 18:59:38  dave
// syntax
//
// Revision 1.44  2003/04/23 18:36:50  dave
// spec changes and syntax
//
// Revision 1.43  2003/04/23 18:23:11  dave
// finding the right MetaLink
//
// Revision 1.42  2003/04/22 16:24:12  dave
// some Syntax error fixing
//
// Revision 1.41  2003/04/22 16:00:49  dave
// cleaning up Matrix.. only columns in the Nav Action Item
// need be shown.. and we are deriving the MetaLink
//
// Revision 1.40  2003/02/05 21:10:05  joan
// check link limit
//
// Revision 1.39  2003/02/05 19:16:13  joan
// test link limit
//
// Revision 1.38  2003/01/23 17:41:58  joan
// remove System.out
//
// Revision 1.37  2003/01/21 21:26:22  joan
// debug
//
// Revision 1.36  2003/01/21 21:14:31  joan
// debug
//
// Revision 1.34  2003/01/21 19:02:17  joan
// check VE lock for link
//
// Revision 1.33  2003/01/21 00:20:36  joan
// adjust link method to test VE lock
//
// Revision 1.32  2003/01/14 22:05:05  joan
// adjust removeLink method
//
// Revision 1.31  2003/01/14 00:56:33  joan
// add checkOrphan
//
// Revision 1.30  2003/01/08 21:44:06  joan
// add getWhereUsedList
//
// Revision 1.29  2002/10/30 22:57:18  dave
// syntax on import
//
// Revision 1.28  2002/10/30 22:39:13  dave
// more cleanup
//
// Revision 1.27  2002/10/30 22:36:20  dave
// clean up
//
// Revision 1.26  2002/10/30 22:02:34  dave
// added exception throwing to commit
//
// Revision 1.25  2002/10/29 00:02:56  dave
// backing out row commit for 1.1
//
// Revision 1.24  2002/10/28 23:49:15  dave
// attempting the first commit with a row index
//
// Revision 1.23  2002/10/18 20:18:53  joan
// add isMatrixEditable method
//
// Revision 1.22  2002/10/09 21:32:57  dave
// added isDynaTable to EANTableWrapper interface
//
// Revision 1.21  2002/09/27 17:11:00  dave
// made addRow a boolean
//
// Revision 1.20  2002/07/16 15:38:20  joan
// working on method to return the array of actionitems
//
// Revision 1.19  2002/07/08 17:53:42  joan
// fix link method
//
// Revision 1.18  2002/07/08 16:05:30  joan
// fix link method
//
// Revision 1.17  2002/06/25 20:36:09  joan
// add create method for whereused
//
// Revision 1.16  2002/06/25 17:49:37  joan
// add link and removeLink methods
//
// Revision 1.15  2002/06/19 23:38:18  joan
// fix errors
//
// Revision 1.14  2002/06/19 22:00:11  joan
// fix error
//
// Revision 1.13  2002/06/19 16:47:19  joan
// work on add column
//
// Revision 1.12  2002/06/19 15:52:19  joan
// work on add column in matrix
//
// Revision 1.11  2002/06/18 22:16:22  joan
// work on rollback and hasChanges
//
// Revision 1.10  2002/06/17 23:53:46  joan
// add addColumn method
//
// Revision 1.9  2002/06/17 18:08:51  joan
// work on matrix
//
// Revision 1.8  2002/06/07 16:44:06  joan
// working on column headers
//
// Revision 1.7  2002/06/07 15:32:04  joan
// working on commit
//
// Revision 1.6  2002/06/06 21:32:51  joan
// fixing errors
//
// Revision 1.5  2002/06/06 20:54:02  joan
// working on link
//
//

package COM.ibm.eannounce.objects;

import java.util.Vector;
import java.util.Stack;
import java.util.StringTokenizer;
import java.sql.ResultSet;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSetGroup;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.transactions.OPICMList;
import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.T;

// Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.LockException;

import java.sql.SQLException;

import java.rmi.RemoteException;

/**
 *  Description of the Class
 *
 *@author     davidbig
 *@created    April 22, 2003
 */
public class MatrixGroup extends EANMetaEntity implements EANTableWrapper {
    /**
     *@serial
     */
    final static long serialVersionUID = 1L;

    private static final int LINK_LIMIT = 500;

    private Stack m_stk = null;
    private EntityGroup m_eg = null;
    
    protected void dereference(){
    	if (m_stk!=null){
    		m_stk.clear();
    		m_stk = null;
    	}
    	for (int i=0; i<getMatrixItemCount(); i++) {
    		MatrixItem mi = getMatrixItem(i); 
    		mi.dereference();
    	}
        m_eg = null;
    	super.dereference();
    }

    /**
     *  Main method which performs a simple test of this class
     *
     *@param  arg  Description of the Parameter
     */
    public static void main(String arg[]) {
    }

    /**
     *  Constructor for the MatrixGroup object
     *
     *@param  _ef                             Description of the Parameter
     *@param  _prof                           Description of the Parameter
     *@param  _eg                             Description of the Parameter
     *@exception  MiddlewareRequestException  Description of the Exception
     */
    public MatrixGroup(EANMetaFoundation _ef, Profile _prof, EntityGroup _eg) throws MiddlewareRequestException {
        super(_ef, _prof, _eg.getKey());

        try {
            EntityGroup eg = _eg;
            m_stk = new Stack();
            putLongDescription(_eg.getLongDescription());
            putShortDescription(_eg.getShortDescription());
            setEntityGroup(_eg);

            // O.K.  Lets loop through these guys and establish the Matrix Items
            for (int j = 0; j < eg.getEntityItemCount(); j++) {
                EntityItem ei = eg.getEntityItem(j);
                EntityItem eip = (EntityItem) ei.getUpLink(0);
                EntityItem eic = (EntityItem) ei.getDownLink(0);
                MatrixItem mi = getMatrixItem(eip.getKey() + eic.getKey());
                if (mi == null) {
                    mi = new MatrixItem(this, null, eip, eic);
                    putMatrixItem(mi);
                }
                mi.put(Integer.parseInt(mi.get()) + 1);
                mi.commitLocal();
                mi.putRelator(ei);
            }
        } finally {
        }
    }

    /**
     *  Description of the Method
     *
     *@param  _bBrief  Description of the Parameter
     *@return          Description of the Return Value
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        if (_bBrief) {
            strbResult.append(NEW_LINE + "MatrixGroup : " + getKey());
        } else {
            strbResult.append(NEW_LINE + "MatrixGroup : " + getKey());
            for (int i = 0; i < getMatrixItemCount(); i++) {
                MatrixItem mi = getMatrixItem(i);
                strbResult.append(mi.dump(_bBrief));
            }
        }

        return strbResult.toString();
    }

    /*
     *  return the column information here
     */
    /**
     *  Gets the columnList attribute of the MatrixGroup object
     *
     *@return    The columnList value
     */
    public EANList getColumnList() {
        EANList el = new EANList();
        for (int i = 0; i < getMatrixItemCount(); i++) {
            MatrixItem mg = getMatrixItem(i);
            EntityItem eic = mg.getChildEntity();
            try {
                el.put(new Implicator(eic, getProfile(), eic.getKey()));
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
        return el;
    }

    /*
     *  Return only visible rows
     */
    /**
     *  Gets the rowList attribute of the MatrixGroup object
     *
     *@return    The rowList value
     */
    public EANList getRowList() {
        MatrixList ml = (MatrixList) getParent();
        EANList el = ml.getRowList();
        return el;
    }

    /*
     *  Returns a basic table adaptor for client rendering of EntityLists
     */
    /**
     *  Gets the table attribute of the MatrixGroup object
     *
     *@return    The table value
     */
    public RowSelectableTable getTable() {
        return new RowSelectableTable(this, getLongDescription());
    }

    /**
     *  Gets the linkable attribute of the MatrixGroup object
     *
     *@param  _list            Description of the Parameter
     *@param  _aLockOwner      Description of the Parameter
     *@param  _strEntityType   Description of the Parameter
     *@param  _strRelatorType  Description of the Parameter
     *@return                  The linkable value
     */

    /**
     *  Description of the Method
     *
     * @param _db
     * @param _rdi
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     */
    public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        EntityItemException eie = new EntityItemException();
        MatrixList ml = getMatrixList();
        EntityGroup eg = ml.getParentEntityGroup();
        String strEntityType = eg.getEntityType();
        ReturnStatus returnStatus = new ReturnStatus(-1);
        String strEnterprise = getProfile().getEnterprise();

        Rule51Group r51g = null;
        boolean b51 = false;

        int iCount = 0;

        if (_db == null && _rdi == null) {
            return;
        }

        if (_db != null) {
            try {
                rs = _db.callGBL0006(returnStatus, strEnterprise, eg.getEntityType());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if(rs!= null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            if (rdrs.size() > 0) {
                b51 = true;
            }
        } else {
            ReturnDataResultSetGroup rdrsg = _rdi.remoteGBL0006(strEnterprise, eg.getEntityType());
            if (rdrsg.getResultSetCount() > 0) {
                rdrs = rdrsg.getResultSet(0);
                if (rdrs.size() > 0) {
                    b51 = true;
                }
            }
        }
        if (b51) {
            EntityGroup egEdit = null;
            if (_db != null) {
                egEdit = new EntityGroup(null, _db, getProfile(), strEntityType, "Edit");
            } else {
                egEdit = new EntityGroup(null, _rdi, getProfile(), strEntityType, "Edit");
            }

            if (egEdit.hasRule51Group()) {
                r51g = egEdit.getRule51Group();
            } else {
                System.out.println("ERROR: Rule51 cannot grab r51g from eg......in MAtrix Group for:" + strEntityType);
            }
        }

        for (int i = 0; i < getMatrixItemCount(); i++) {
            MatrixItem mi = getMatrixItem(i);
            iCount = iCount + mi.getNewRelatorCount();
        }

        //		System.out.println("testing getNewRelatorCount: " + iCount);
        // Lets test the size of the link here...
        T.est(iCount <= LINK_LIMIT, "This link request generates over " + iCount + 
        		" Relationships.  You are only allowed to generate "+LINK_LIMIT+" per transaction. Please reduce your selection and try again.(ok)");

        for (int i = 0; i < getMatrixItemCount(); i++) {

            //get the information
            MatrixItem mi = getMatrixItem(i);
            Vector vctReturnRelatorKeys = mi.generateLinkRelators();

            //
            if (r51g != null) {
                for (int j = 0; j < vctReturnRelatorKeys.size(); j++) {
                    String strAttVal = null;
                    if (vctReturnRelatorKeys.elementAt(j) instanceof ReturnRelatorKey) {
                        ReturnRelatorKey rrk = (ReturnRelatorKey) vctReturnRelatorKeys.elementAt(j);
                        r51g = r51g.getCopy();
                        r51g.addDomainEntityID(rrk.getEntity2ID());
                        r51g.setParentEntityID(rrk.getEntity1ID());
                        if (_db != null) {
                            strAttVal = r51g.getAttributeValue(_db, rrk.getEntity1Type());
                        } else {
                            strAttVal = r51g.getAttributeValue(_rdi, rrk.getEntity1Type());
                        }
                        r51g.setAttributeValue(strAttVal);
                        ((ReturnRelatorKey) vctReturnRelatorKeys.elementAt(j)).setRule51Group(r51g);
                        System.out.println("Rule51 setting articial Rule51 group on rrk...for " + strEntityType);
                    }

                }
            }
            //

            if (vctReturnRelatorKeys.size() > 0) {
                OPICMList olResult = null;
                if (_rdi != null) {
                    olResult = _rdi.update(getProfile(), vctReturnRelatorKeys);
                } else if (_db != null) {
                    olResult = _db.update(getProfile(), vctReturnRelatorKeys);
                }

                // update matrix items
                try {
                    mi.updateRelList(olResult);
                } catch (EANBusinessRuleException _bre) {
                    eie.addException(_bre);
                }
                removeFromStack(mi);
            }
        }

    }

    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public boolean canEdit() {
        return true;
    }

    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public boolean canCreate() {
        return true;
    }

    /**
     *  Adds a feature to the Row attribute of the MatrixGroup object
     *
     *@return    Description of the Return Value
     */
    public boolean addRow() {
        return false;
    }

    /**
     *  Adds a feature to the Row attribute of the MatrixGroup object
     *
     *@return    Description of the Return Value
     */
    public boolean addRow(String _strKey) {
        return false;
    }

    /**
     *  Description of the Method
     *
     *@param  _strKey  Description of the Parameter
     */
    public void removeRow(String _strKey) {
    }

    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public boolean hasChanges() {
        return !m_stk.isEmpty();
    }

    /**
     *  Gets the matrixItemCount attribute of the MatrixGroup object
     *
     *@return    The matrixItemCount value
     */
    public int getMatrixItemCount() {
        return getDataCount();
    }

    /**
     *  Description of the Method
     *
     *@param  _mi  Description of the Parameter
     */
    public void putMatrixItem(MatrixItem _mi) {
        putData(_mi);
        _mi.setParent(this);
        _mi.setNewRelatorId(this.getMatrixItemCount());
    }

    /**
     *  Gets the matrixItem attribute of the MatrixGroup object
     *
     *@param  _i  Description of the Parameter
     *@return     The matrixItem value
     */
    public MatrixItem getMatrixItem(int _i) {
        return (MatrixItem) getData(_i);
    }

    /**
     *  Gets the matrixItem attribute of the MatrixGroup object
     *
     *@param  _s  Description of the Parameter
     *@return     The matrixItem value
     */
    public MatrixItem getMatrixItem(String _s) {
        return (MatrixItem) getData(_s);
    }

    /**
     *  Gets the matrixValue attribute of the MatrixGroup object
     *
     *@param  _str  Description of the Parameter
     *@return       The matrixValue value
     */
    public Object getMatrixValue(String _str) {
        //System.out.println(strTraceBase + _str);
        StringTokenizer st = new StringTokenizer(_str, ":");
        String strParentEntityKey = st.nextToken();
        String strChildEntityKey = st.nextToken();
        StringBuffer strbResult = new StringBuffer();

        MatrixItem mi = getMatrixItem(strParentEntityKey + strChildEntityKey);
        if (mi == null) {
            EANList parentList = getRowList();
            EANList childList = getColumnList();
            EANFoundation ean1 = (EANFoundation) parentList.get(strParentEntityKey);
            EANFoundation ean2 = (EANFoundation) childList.get(strChildEntityKey);
            if (ean1 == null || ean2 == null) {
                return null;
            }

            if (ean1 instanceof Implicator) {
                ean1 = ean1.getParent();
            }
            if (ean2 instanceof Implicator) {
                ean2 = ean2.getParent();
            }

            if ((ean1 instanceof EntityItem) && (ean2 instanceof EntityItem)) {
                EntityItem eip = (EntityItem) ean1;
                EntityItem eic = (EntityItem) ean2;
                try {
                    mi = new MatrixItem(this, getProfile(), eip, eic);
                    putMatrixItem(mi);
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        }

        if (showRelAttr()) {
            return mi.getRelAttr(getRelAttrCode());
        } else {
            if (Integer.parseInt(mi.get()) > 0) {
                strbResult.append(mi.get());
            }
        }
        return strbResult.toString();
    }

    /**
     *  Description of the Method
     *
     *@param  _str  Description of the Parameter
     *@param  _o    Description of the Parameter
     */
    public void putMatrixValue(String _str, Object _o) {
        //System.out.println(strTraceBase);
        StringTokenizer st = new StringTokenizer(_str, ":");
        String strParentEntityKey = st.nextToken();
        String strChildEntityKey = st.nextToken();

        MatrixItem mi = getMatrixItem(strParentEntityKey + strChildEntityKey);
        if (mi != null) {
            if (showRelAttr()) {
                mi.putRelAttr(getRelAttrCode(), _o);
            } else {
                if (_o instanceof String) {
                    mi.put(Integer.parseInt((String) _o));
                }
            }
        } else {
            EANList parentList = getRowList();
            EANList childList = getColumnList();
            EANFoundation ean1 = (EANFoundation) parentList.get(strParentEntityKey);
            EANFoundation ean2 = (EANFoundation) childList.get(strChildEntityKey);
            if (ean1 == null || ean2 == null) {
                return;
            }

            if (ean1 instanceof Implicator) {
                ean1 = ean1.getParent();
            }
            if (ean2 instanceof Implicator) {
                ean2 = ean2.getParent();
            }

            if ((ean1 instanceof EntityItem) && (ean2 instanceof EntityItem)) {
                EntityItem eip = (EntityItem) ean1;
                EntityItem eic = (EntityItem) ean2;
                try {
                    mi = new MatrixItem(this, getProfile(), eip, eic);
                    putMatrixItem(mi);
                    if (showRelAttr()) {
                        mi.putRelAttr(getRelAttrCode(), _o);
                    } else {
                        if (_o instanceof String) {
                            mi.put(Integer.parseInt((String) _o));
                        }
                    }
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        }
    }

    /**
     *  Gets the matrixEditable attribute of the MatrixGroup object
     *
     *@param  _str  Description of the Parameter
     *@return       The matrixEditable value
     */
    public boolean isMatrixEditable(String _str) {
        return true;
    }

    /**
     *  Description of the Method
     */
    public void rollbackMatrix() {
        for (int i = 0; i < getMatrixItemCount(); i++) {
            MatrixItem mi = getMatrixItem(i);
            mi.rollback();
        }
    }

    /**
     *  Adds a feature to the ToStack attribute of the MatrixGroup object
     *
     *@param  _mi  The feature to be added to the ToStack attribute
     */
    public void addToStack(MatrixItem _mi) {
        if (_mi == null) {
            return;
        }

        if (m_stk == null) {
            return;
        }

        if (m_stk.search(_mi) != -1) {
            m_stk.removeElementAt(m_stk.lastIndexOf(_mi));
        }
        m_stk.push(_mi);
    }

    /**
     *  Description of the Method
     *
     *@param  _mi  Description of the Parameter
     *@return      Description of the Return Value
     */
    protected boolean removeFromStack(MatrixItem _mi) {
        if (m_stk.search(_mi) != -1) {
            m_stk.removeElementAt(m_stk.lastIndexOf(_mi));
            return true;
        }
        return false;
    }

    /**
     *  Gets the metaLink attribute of the MatrixGroup object
     *
     *@return    The metaLink value
     */
    public MetaLink getMetaLink() {
        MatrixList ml = getMatrixList();
        EntityGroup eg = ml.getParentEntityGroup();
        MetaLinkGroup mlg = eg.getMetaLinkGroup();
        //System.out.println("Rule51 Matrix Group...getParentEntityGroup is:" + eg.getEntityType());
        MetaLink mel = mlg.getMetaLink(MetaLinkGroup.DOWN, getEntityType());
        return mel;
    }

    /**
     * getEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityType() {
        return m_eg.getEntityType();
    }

    /**
     *  Sets the entityGroup attribute of the MatrixGroup object
     *
     *@param  _eg  The new entityGroup value
     */
    protected void setEntityGroup(EntityGroup _eg) {
        m_eg = _eg;
    }

    /**
     *  Gets the entityGroup attribute of the MatrixGroup object
     *
     *@return    The entityGroup value
     */
    public EntityGroup getEntityGroup() {
        return m_eg;
    }

    /**
     *  Adds a feature to the Column attribute of the MatrixGroup object
     *
     *@param  _ean  The feature to be added to the Column attribute
     */
    public void addColumn(EANFoundation _ean) {

        EANList el = null;
        EntityItem eip = null;

        if (_ean instanceof EntityItem) {
            EntityItem eic = (EntityItem) _ean;
            // this is to avoid add the same column
            EANList cl = getColumnList();
            EANFoundation mfCol = (EANFoundation) cl.get(eic.getKey());
            if (mfCol != null) {
                return;
            }

            // add matrixitems for new column
            el = getRowList();
            for (int i = 0; i < el.size(); i++) {
                EANFoundation mfRow = (EANFoundation) el.getAt(i);
                if (mfRow instanceof Implicator) {
                    mfRow = mfRow.getParent();
                }
                eip = (EntityItem) mfRow;
                try {
                    MatrixItem mi = new MatrixItem(this, getProfile(), eip, eic);
                    putMatrixItem(mi);
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        }
    }

    /**
     *  Gets the matrixList attribute of the MatrixGroup object
     *
     *@return    The matrixList value
     */
    public MatrixList getMatrixList() {
        return (MatrixList) getParent();
    }

    /**
     *  Description of the Method
     *
     *@param  _db              Description of the Parameter
     *@param  _rdi             Description of the Parameter
     *@param  _prof            Description of the Parameter
     *@param  _strRelatorType  Description of the Parameter
     *@return                  Description of the Return Value
     */
    public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return getMatrixList().generatePickList(_db, _rdi, _prof, _strRelatorType);
    }

    /**
     *  Description of the Method
     *
     *@param  _db                           Description of the Parameter
     *@param  _rdi                          Description of the Parameter
     *@param  _prof                         Description of the Parameter
     *@param  _strRowKey                    Description of the Parameter
     *@return                               Description of the Return Value
     *@exception  EANBusinessRuleException  Description of the Exception
     */
    public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey) throws EANBusinessRuleException {
        return false;
    }

    /**
     *  Description of the Method
     *
     *@param  _db                           Description of the Parameter
     *@param  _rdi                          Description of the Parameter
     *@param  _prof                         Description of the Parameter
     *@param  _strRowKey                    Description of the Parameter
     *@param  _aeiChild                     Description of the Parameter
     *@return                               Description of the Return Value
     *@exception  EANBusinessRuleException  Description of the Exception
     * @param _strLinkOption
     */
    public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey, EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException {
        return null;
    }

    /**
     *  Description of the Method
     *
     *@param  _db              Description of the Parameter
     *@param  _rdi             Description of the Parameter
     *@param  _prof            Description of the Parameter
     *@param  _strRelatorType  Description of the Parameter
     *@return                  Description of the Return Value
     */
    public EntityList create(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }

    /**
     *  Description of the Method
     *
     *@param  _db              Description of the Parameter
     *@param  _rdi             Description of the Parameter
     *@param  _prof            Description of the Parameter
     *@return                  Description of the Return Value
     * @param _astrKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     */
    public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        return null;
    }

    /**
     *  Gets the whereUsedList attribute of the MatrixGroup object
     *
     *@param  _db              Description of the Parameter
     *@param  _rdi             Description of the Parameter
     *@param  _prof            Description of the Parameter
     *@param  _strRelatorType  Description of the Parameter
     *@return                  The whereUsedList value
     */
    public WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }

    /**
     *  Gets the actionItemsAsArray attribute of the MatrixGroup object
     *
     *@param  _strKey  Description of the Parameter
     *@return          The actionItemsAsArray value
     * @param _db
     * @param _rdi
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     */
    public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        MatrixList ml = getMatrixList();
        if (ml != null) {
            return ml.getActionItemsAsArray(_db, _rdi, _prof, getKey());
        }

        return null;
    }

    /**
     *  Gets the dynaTable attribute of the MatrixGroup object
     *
     *@return    The dynaTable value
     */
    public boolean isDynaTable() {
        return false;
    }
    /**
     * (non-Javadoc)
     * duplicate
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#duplicate(java.lang.String, int)
     */
    public boolean duplicate(String _strKey, int _iDup) {
        return false;
    }

    // for special matrix
    /**
     * showRelAttr
     *
     * @return
     *  @author David Bigelow
     */
    public boolean showRelAttr() {
        MatrixList ml = (MatrixList) getParent();
        return ml.showRelAttr(m_eg.getKey());
    }

    /**
     * getRelAttrCode
     *
     * @return
     *  @author David Bigelow
     */
    public String getRelAttrCode() {
        MatrixList ml = (MatrixList) getParent();
        return ml.getRelAttrCode(m_eg.getKey());
    }

    /**
     * (non-Javadoc)
     * linkAndRefresh
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#linkAndRefresh(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.LinkActionItem)
     */
    public Object linkAndRefresh(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException, RemoteException {
        return null;
    }
}
