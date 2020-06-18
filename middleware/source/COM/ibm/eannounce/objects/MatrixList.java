//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MatrixList.java,v $
// Revision 1.72  2010/09/15 12:48:36  wendy
// Added subset() for JUI checks
//
// Revision 1.71  2009/05/19 12:23:17  wendy
// Support dereference for memory release
//
// Revision 1.70  2009/05/19 11:56:40  wendy
// Support dereference for memory release
//
// Revision 1.69  2009/01/12 13:21:21  wendy
// Check for rs != null before rs.close()
//
// Revision 1.68  2005/11/04 14:52:11  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.67  2005/03/04 21:35:51  dave
// Jtest work
//
// Revision 1.66  2005/01/19 00:12:20  gregg
// puttin back code
//
// Revision 1.65  2005/01/18 21:46:50  dave
// more parm debug cleanup
//
// Revision 1.64  2005/01/10 21:47:49  joan
// work on multiple edit
//
// Revision 1.63  2005/01/05 19:24:09  joan
// add new method
//
// Revision 1.62  2004/11/05 17:37:51  joan
// work on picklist
//
// Revision 1.61  2004/06/21 22:09:28  joan
// add method
//
// Revision 1.60  2004/06/11 20:57:24  joan
// work on feature matrix
//
// Revision 1.59  2004/06/10 21:27:12  joan
// more work on special matrix
//
// Revision 1.58  2004/06/08 20:57:34  joan
// exception
//
// Revision 1.57  2004/06/08 20:52:13  joan
// work on special matrix
//
// Revision 1.54  2004/04/09 19:37:21  joan
// add duplicate method
//
// Revision 1.53  2004/01/08 22:49:02  dave
// more clean up for ActionTree I
//
// Revision 1.52  2003/12/16 22:08:30  gregg
// set parent reference in getMetaColumnOrderGroup
//
// Revision 1.51  2003/12/16 17:33:53  gregg
// getMetaColumnOrderGroup() method
//
// Revision 1.50  2003/12/16 00:33:17  gregg
// use MetaColumnOrderGroup
//
// Revision 1.49  2003/08/28 16:28:07  joan
// adjust link method to have link option
//
// Revision 1.48  2003/08/26 21:52:29  dave
// missing interface declaration
//
// Revision 1.47  2003/08/26 21:38:59  dave
// clean up here and there
//
// Revision 1.46  2003/08/26 21:28:08  dave
// wondering what ActionItemArray is all about
//
// Revision 1.45  2003/06/25 18:44:01  joan
// move changes from v111
//
// Revision 1.44  2003/05/09 19:38:41  dave
// making a blobnow
//
// Revision 1.43  2003/04/30 17:31:37  dave
// only use matrix downs from a relator
//
// Revision 1.42  2003/04/22 21:47:37  dave
// Find the getBuildRow
//
// Revision 1.41  2003/04/22 21:07:32  dave
// trace to text Matrix
//
// Revision 1.40  2003/04/22 16:32:23  dave
// need to catch a throw
//
// Revision 1.39  2003/04/22 16:24:12  dave
// some Syntax error fixing
//
// Revision 1.38  2003/04/22 16:00:50  dave
// cleaning up Matrix.. only columns in the Nav Action Item
// need be shown.. and we are deriving the MetaLink
//
// Revision 1.37  2003/04/14 16:43:10  dave
// speed and cleanup
//
// Revision 1.36  2003/04/14 15:37:29  dave
// clean up and verification on getMetaLinkGroup
//
// Revision 1.35  2003/01/21 00:20:36  joan
// adjust link method to test VE lock
//
// Revision 1.34  2003/01/14 22:05:05  joan
// adjust removeLink method
//
// Revision 1.33  2003/01/08 21:44:06  joan
// add getWhereUsedList
//
// Revision 1.32  2002/11/11 16:50:53  joan
// use getShortDescription
//
// Revision 1.31  2002/10/30 22:57:18  dave
// syntax on import
//
// Revision 1.30  2002/10/30 22:39:14  dave
// more cleanup
//
// Revision 1.29  2002/10/30 22:02:34  dave
// added exception throwing to commit
//
// Revision 1.28  2002/10/29 00:02:56  dave
// backing out row commit for 1.1
//
// Revision 1.27  2002/10/28 23:49:15  dave
// attempting the first commit with a row index
//
// Revision 1.26  2002/10/18 20:18:54  joan
// add isMatrixEditable method
//
// Revision 1.25  2002/10/09 21:32:57  dave
// added isDynaTable to EANTableWrapper interface
//
// Revision 1.24  2002/09/27 17:11:00  dave
// made addRow a boolean
//
// Revision 1.23  2002/07/17 18:12:24  joan
// fix getActionItemAsArray
//
// Revision 1.22  2002/07/16 21:32:21  joan
// working on getActionItemArray
//
// Revision 1.21  2002/07/16 15:38:20  joan
// working on method to return the array of actionitems
//
// Revision 1.20  2002/07/08 17:53:42  joan
// fix link method
//
// Revision 1.19  2002/07/08 16:05:30  joan
// fix link method
//
// Revision 1.18  2002/06/27 22:51:51  joan
// fix equivalent method
//
// Revision 1.17  2002/06/25 20:36:09  joan
// add create method for whereused
//
// Revision 1.16  2002/06/25 17:49:37  joan
// add link and removeLink methods
//
// Revision 1.15  2002/06/21 15:40:31  joan
// display column header
//
// Revision 1.14  2002/06/20 00:01:48  joan
// fix get values
//
// Revision 1.13  2002/06/19 23:38:18  joan
// fix errors
//
// Revision 1.12  2002/06/19 16:29:24  joan
// fix error
//
// Revision 1.11  2002/06/19 15:52:19  joan
// work on add column in matrix
//
// Revision 1.10  2002/06/18 22:16:22  joan
// work on rollback and hasChanges
//
// Revision 1.9  2002/06/18 18:37:57  joan
// fix error
//
// Revision 1.8  2002/06/18 18:28:09  joan
// working on picklist
//
// Revision 1.7  2002/06/17 23:53:46  joan
// add addColumn method
//
// Revision 1.6  2002/06/17 18:08:52  joan
// work on matrix
//
// Revision 1.5  2002/06/06 20:54:02  joan
// working on link
//
//

package COM.ibm.eannounce.objects;

import java.util.Vector;
import java.util.StringTokenizer;
import java.sql.ResultSet;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.D;

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
 *@created    April 14, 2003
 */
public class MatrixList extends EANMetaEntity implements EANTableWrapper {

    /**
     *@serial
     */
    final static long serialVersionUID = 1L;

    private EntityList m_el = null;
    private EANList m_rowList = null;
    private MatrixActionItem m_mai = null;
    private EntityGroup m_egParent = null;
    private MetaColumnOrderGroup m_mcog = null;

    public void dereference(){
    	for (int i = 0; i < getMatrixGroupCount(); i++) {
    		MatrixGroup mg = getMatrixGroup(i);
    		mg.dereference();
    	}
    	if (m_el!=null){
    		m_el.dereference();
    		m_el = null;
    	}
    	if (m_rowList != null){
    		for (int i=0; i<m_rowList.size(); i++){
    			EANFoundation mt = (EANFoundation) m_rowList.getAt(i);
    			if (mt != null){
    				mt.dereference();
    			}
    		}
    		m_rowList = null;
    	}
      
        m_mai = null;
        m_egParent = null;
        if (m_mcog!=null){
        	m_mcog.dereference();
        	m_mcog = null;
        }
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
     *  Constructor for the MatrixList object
     *
     *@param  _prof                           Description of the Parameter
     *@param  _db                             Description of the Parameter
     *@exception  MiddlewareRequestException  Description of the Exception
     * @param _ai
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    public MatrixList(Database _db, Profile _prof, MatrixActionItem _ai) throws SQLException, MiddlewareRequestException, MiddlewareException {

        super(null, _prof, _ai.getKey());

        try {

            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            ReturnStatus returnStatus = new ReturnStatus(-1);

            EntityList el = null;
            EntityGroup egParent = null;
            String strEntityType = null;
            String strEnterprise = null;

            EntityItem[] aei = _ai.getEntityItemArray();
            NavActionItem nai = _ai.getNavActionItem();

            _db.test(aei != null, "MatrixList.init.EntityItem array is null");
            _db.test(aei.length > 0, "MatrixList.ini.EntityItem array is zero");

            setParentActionItem(_ai);
            // DWB ACTIONTREE CANDIDATE
            setEntityList(new EntityList(_db, _prof, nai, aei, aei[0].getEntityType(), true));

            setParentEntityGroup(getEntityList().getParentEntityGroup());

            // Set up some needed refs
            el = getEntityList();
            egParent = getParentEntityGroup();

            strEntityType = egParent.getEntityType();
            strEnterprise = _prof.getEnterprise();

            putLongDescription(egParent.getLongDescription());
            putShortDescription(egParent.getShortDescription());

            buildRowList();

            // Lets look at some trace information ...

            try {
                rs = _db.callGBL8004(returnStatus, strEnterprise, strEntityType, nai.getKey());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs!= null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            for (int ii = 0; ii < rdrs.size(); ii++) {

                int iLevel = rdrs.getColumnInt(ii, 0);
                int iLeaf = rdrs.getColumnInt(ii, 1);
                String strFromEntity = rdrs.getColumn(ii, 2);
                String strToEntity = rdrs.getColumn(ii, 3);
                String strRelator = rdrs.getColumn(ii, 4);
                String strPDirection = rdrs.getColumn(ii, 5);
                String strCDirection = rdrs.getColumn(ii, 6);
                String strCategory = rdrs.getColumn(ii, 7);
                String strRClass = rdrs.getColumn(ii, 8);

                _db.debug(D.EBUG_SPEW, "gbl8004:answers:" + iLevel + ":" + iLeaf + ":" + strFromEntity + ":" + strToEntity + ":" + strRelator + ":" + strPDirection + ":" + strCDirection + ":" + strCategory + ":" + strRClass);
                if (iLevel == 0 && strCDirection.equals("D")) {
                    EntityGroup eg = el.getEntityGroup(strRelator);
                    if (eg != null && eg.isRelator()) {
                        putMatrixGroup(new MatrixGroup(this, null, eg));
                    } else {
                        _db.debug(D.EBUG_ERR, "MatrxList.init. cannot find EntityGroup:" + strRelator + ":");
                    }
                }
            }

            m_mcog = new MetaColumnOrderGroup(_db, this);

        } finally {

            _db.freeStatement();
            _db.isPending();

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
        strbResult.append("MatrixList: " + getKey());
        if (!_bBrief) {
            for (int i = 0; i < getMatrixGroupCount(); i++) {
                MatrixGroup mg = getMatrixGroup(i);
                strbResult.append(mg.dump(_bBrief));
            }
        }

        return strbResult.toString();
    }

    /*
     *  return the column information here
     */
    /**
     *  Gets the columnList attribute of the MatrixList object
     *
     *@return    The columnList value
     */
    public EANList getColumnList() {
        EANList colList = new EANList();
        for (int i = 0; i < getMatrixGroupCount(); i++) {
            MatrixGroup mg = getMatrixGroup(i);
            try {
                colList.put(new Implicator(mg, getProfile(), mg.getKey()));
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
        colList = RowSelectableTable.applyColumnOrders(m_mcog, colList);
        return colList;
    }

    /**
     * getMetaColumnOrderGroup
     *
     * @return
     *  @author David Bigelow
     */
    public MetaColumnOrderGroup getMetaColumnOrderGroup() {
        // ensure this is the parent (b/c rdi constructs a seperate instance for the mcog...). just so our references are straight.
        m_mcog.setParent(this);
        return m_mcog;
    }

    /*
     *  Return only visible rows
     */
    /**
     *  Gets the rowList attribute of the MatrixList object
     *
     *@return    The rowList value
     */
    public EANList getRowList() {
        return m_rowList;
    }

    /*
     *  Returns a basic table adaptor for client rendering of EntityLists
     */
    /**
     *  Gets the table attribute of the MatrixList object
     *
     *@return    The table value
     */
    public RowSelectableTable getTable() {
        return new RowSelectableTable(this, getLongDescription());
    }

    /**
     *  This commits any internal data changes in this object back to the
     *  e-announce database
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
        for (int i = 0; i < getMatrixGroupCount(); i++) {
            MatrixGroup mg = getMatrixGroup(i);
            mg.commit(_db, _rdi);
        }
    }

    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public boolean canEdit() {
        return false;
    }

    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public boolean canCreate() {
        return false;
    }

    /**
     *  Adds a feature to the Row attribute of the MatrixList object
     *
     *@return    Description of the Return Value
     */
    public boolean addRow() {
        return false;
    }

    /**
     *  Adds a feature to the Row attribute of the MatrixList object
     *
     *@return    Description of the Return Value
     */
    public boolean addRow(String _sKey) {
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
        for (int i = 0; i < getMatrixGroupCount(); i++) {
            MatrixGroup mg = getMatrixGroup(i);
            if (mg.hasChanges()) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Gets the matrixGroupCount attribute of the MatrixList object
     *
     *@return    The matrixGroupCount value
     */
    public int getMatrixGroupCount() {
        return getDataCount();
    }

    /**
     *  Description of the Method
     *
     *@param  _mg  Description of the Parameter
     */
    protected void putMatrixGroup(MatrixGroup _mg) {
        putData(_mg);
    }

    /**
     *  Gets the matrixGroup attribute of the MatrixList object
     *
     *@param  _i  Description of the Parameter
     *@return     The matrixGroup value
     */
    public MatrixGroup getMatrixGroup(int _i) {
        return (MatrixGroup) getData(_i);
    }

    /**
     *  Gets the matrixGroup attribute of the MatrixList object
     *
     *@param  _s  Description of the Parameter
     *@return     The matrixGroup value
     */
    public MatrixGroup getMatrixGroup(String _s) {
        return (MatrixGroup) getData(_s);
    }

    /**
     *  Sets the entityList attribute of the MatrixList object
     *
     *@param  _el  The new entityList value
     */
    protected void setEntityList(EntityList _el) {
        m_el = _el;
    }

    /**
     *  Gets the entityList attribute of the MatrixList object
     *
     *@return    The entityList value
     */
    public EntityList getEntityList() {
        return m_el;
    }

    /**
     *  Sets the parentActionItem attribute of the MatrixList object
     *
     *@param  _mai  The new parentActionItem value
     */
    protected void setParentActionItem(MatrixActionItem _mai) {
        m_mai = _mai;
    }

    /**
     *  Gets the parentActionItem attribute of the MatrixList object
     *
     *@return    The parentActionItem value
     */
    public MatrixActionItem getParentActionItem() {
        return m_mai;
    }

    /**
     * look for a match on the action and match of all entityitems
     *
     *@param  _eia  Description of the Parameter
     *@param  _ai   Description of the Parameter
     *@return       Description of the Return Value
     */
    public boolean equivalent(EntityItem[] _eia, EANActionItem _ai) {

        EntityGroup eg1 = null;
        EntityGroup parentEG = null;

        String strActionItemKey = getParentActionItem().getActionItemKey();

        if (_eia == null || _eia.length == 0) {
            return false;
        }
        if (_ai == null) {
            return false;
        }

        if (!_ai.getActionItemKey().equals(strActionItemKey)) {
            return false;
        }

        eg1 = _eia[0].getEntityGroup();
        if (eg1 == null) {
            return false;
        }
        parentEG = getEntityList().getParentEntityGroup();

        if (eg1.getKey().equals(parentEG.getKey())) {
            for (int y = 0; y < _eia.length; y++) {
                EntityItem ei = parentEG.getEntityItem(_eia[y].getKey());
                if (ei == null) {
                    return false;
                }
            }
            return true;
        } else if (eg1.isRelator() || eg1.isAssoc()) {
            for (int y = 0; y < _eia.length; y++) {
                EntityItem ei1 = _eia[y];
                boolean equal = false;
                for (int i = 0; i < parentEG.getEntityItemCount(); i++) {
                    EntityItem parentEI = parentEG.getEntityItem(i);
                    for (int j = 0; j < parentEI.getDownLinkCount(); j++) {
                        EntityItem eic = (EntityItem) parentEI.getDownLink(j);

                        if (eic.getKey().equals(ei1.getKey())) {
                            equal = true;
                            break;
                        }
                    }
                    if (equal) {
                        break;
                    }
                }

                // if not equal, check the child EI
                if (!equal) {
                    ei1 = (EntityItem) _eia[y].getDownLink(0);
                    if (parentEG.getEntityItem(ei1.getKey()) != null) {
                        equal = true;
                    }
                }

                if (!equal) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    /**
     * look for a match on the action and any match of entityitem
     * @param _eia
     * @param _ai
     * @return
     */
    public boolean subset(EntityItem[] _eia, EANActionItem _ai) {
       	boolean anymatch = false;
        EntityGroup eg1 = null;
        EntityGroup parentEG = null;

        if (_eia == null || _eia.length == 0) {
            return anymatch;
        }
        if (_ai == null || (!_ai.getActionItemKey().equals(getParentActionItem().getActionItemKey()))) {
            return anymatch;
        }

        eg1 = _eia[0].getEntityGroup();
        if (eg1 == null) {
            return anymatch;
        }
        parentEG = getEntityList().getParentEntityGroup();

        if (eg1.getKey().equals(parentEG.getKey())) {
            for (int y = 0; y < _eia.length; y++) {
                EntityItem ei = parentEG.getEntityItem(_eia[y].getKey());
                if (ei != null) {
                	anymatch=true;
                	break;
                }
            }
        } else if (eg1.isRelator() || eg1.isAssoc()) {
        	eialoop:for (int y = 0; y < _eia.length; y++) {
        		EntityItem ei1 = _eia[y];
        		for (int i = 0; i < parentEG.getEntityItemCount(); i++) {
        			EntityItem parentEI = parentEG.getEntityItem(i);
        			for (int j = 0; j < parentEI.getDownLinkCount(); j++) {
        				EntityItem eic = (EntityItem) parentEI.getDownLink(j);
        				if (eic.getKey().equals(ei1.getKey())) {
        					anymatch=true;
        					break eialoop;
        				}
        			}
        		}

        		ei1 = (EntityItem) _eia[y].getDownLink(0);
        		if (parentEG.getEntityItem(ei1.getKey()) != null) {
        			anymatch=true;
        			break eialoop;
        		}
        	} // end eia loop
        } // end relator or assoc

        return anymatch;
    }

    /**
     *  Description of the Method
     */
    private void buildRowList() {

        EntityGroup egParent = null;

        if (m_el == null) {
            return;
        }
        m_rowList = new EANList();
        egParent = m_el.getParentEntityGroup();

        if (egParent != null) {
            for (int ii = 0; ii < egParent.getEntityItemCount(); ii++) {
                EntityItem ei = egParent.getEntityItem(ii);
                try {
                    m_rowList.put(new Implicator(ei, getProfile(), ei.getKey()));
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        }
    }

    /**
     *  Gets the originalEntityGroup attribute of the WhereUsedList object
     *
     * @return    The originalEntityGroup value
     */
    public EntityGroup getParentEntityGroup() {
        return m_egParent;
    }

    /**
     *  Sets the originalEntityGroup attribute of the WhereUsedList object
     *
     * @param  _eg  The new originalEntityGroup value
     */
    protected void setParentEntityGroup(EntityGroup _eg) {
        m_egParent = _eg;
    }

    /**
     *  Gets the matrixValue attribute of the MatrixList object
     *
     *@param  _str  Description of the Parameter
     *@return       The matrixValue value
     */
    public Object getMatrixValue(String _str) {

        StringTokenizer st = new StringTokenizer(_str, ":");
        String strEIKey = st.nextToken();
        String strRelatorType = st.nextToken();
        StringBuffer strbResult = new StringBuffer();
        boolean bFirstTime = true;
        for (int i = 0; i < getMatrixGroupCount(); i++) {
            MatrixGroup mg = getMatrixGroup(i);
            if (mg.getKey().equals(strRelatorType)) {
                for (int j = 0; j < mg.getMatrixItemCount(); j++) {
                    MatrixItem mi = mg.getMatrixItem(j);
                    EntityItem eip = mi.getParentEntity();
                    if (eip.getKey().equals(strEIKey)) {
                        EntityItem eic = mi.getChildEntity();
                        if (Integer.parseInt(mi.get()) > 0) {
                            if (!bFirstTime) {
                                strbResult.append(NEW_LINE);
                            }
                            strbResult.append("[" + mi.get() + "] - " + eic.getShortDescription());
                            bFirstTime = false;
                        }
                    }
                }
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
    }

    /**
     *  Gets the matrixEditable attribute of the MatrixList object
     *
     *@param  _str  Description of the Parameter
     *@return       The matrixEditable value
     */
    public boolean isMatrixEditable(String _str) {
        return false;
    }

    /**
     *  Description of the Method
     */
    public void rollbackMatrix() {
        for (int i = 0; i < getMatrixGroupCount(); i++) {
            MatrixGroup mg = getMatrixGroup(i);
            mg.rollbackMatrix();
        }
    }

    /**
     *  Adds a feature to the Column attribute of the MatrixList object
     *
     *@param  _ean  The feature to be added to the Column attribute
     */
    public void addColumn(EANFoundation _ean) {
    }

    /**
     *  Gets the entityItemArray attribute of the MatrixList object
     *
     *@param  _el  Description of the Parameter
     *@return      The entityItemArray value
     */
    private EntityItem[] getEntityItemArray(EANList _el) {
        int size = _el.size();
        EntityItem[] aeiReturn = new EntityItem[size];
        for (int i = 0; i < size; i++) {
            EANFoundation ean = (EANFoundation) _el.getAt(i);
            if (ean instanceof Implicator) {
                ean = ean.getParent();
            }
            if (ean instanceof EntityItem) {
                aeiReturn[i] = (EntityItem) ean;
            }
        }
        return aeiReturn;
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

        EntityList elReturn = null;

        if (_db == null && _rdi == null) {
            return null;
        }
        if (m_mai == null) {
            return null;
        }

        m_mai.setEntityItems(getEntityItemArray(m_rowList));
        try {
            if (_db != null) {
                elReturn = m_mai.generatePickList(_db, _prof, _strRelatorType);
            } else {
                elReturn = m_mai.rgeneratePickList(_rdi, _prof, _strRelatorType);
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        return elReturn;
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
     * (non-Javadoc)
     * edit
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#edit(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String[])
     */
    public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        return null;
    }

    /**
     *  Gets the whereUsedList attribute of the MatrixList object
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
     *  Gets the actionItemsAsArray attribute of the MatrixList object
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

        EntityGroup eg = null;
        RowSelectableTable rst = null;

        Vector temp = new Vector();
        //get ActionItem from MatrixActionItem
        Object[] aiArray = m_mai.getActionItemArray(_db, _rdi, _prof, _strKey);
        if (aiArray != null && aiArray.length > 0) {
            for (int i = 0; i < aiArray.length; i++) {
                temp.addElement(aiArray[i]);
            }
        }

        // get ActionItems from the EntityList
        eg = m_el.getParentEntityGroup();
        rst = eg.getActionGroupTable();
        for (int r = 0; r < rst.getRowCount(); r++) {
            temp.addElement(rst.getRow(r));
        }

        if (temp.size() > 0) {
            return temp.toArray();
        } else {
            return null;
        }
    }

    /**
     *  Gets the dynaTable attribute of the MatrixList object
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
     * isFeatureMatrix
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isFeatureMatrix() {
        return m_mai.isFeatureMatrix();
    }

    /**
     * showRelAttr
     *
     * @param _strRelatorType
     * @return
     *  @author David Bigelow
     */
    public boolean showRelAttr(String _strRelatorType) {
        return m_mai.showRelAttr(_strRelatorType);
    }

    /**
     * getRelAttrCode
     *
     * @param _strRelatorType
     * @return
     *  @author David Bigelow
     */
    public String getRelAttrCode(String _strRelatorType) {
        return m_mai.getRelAttrCode(_strRelatorType);
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
