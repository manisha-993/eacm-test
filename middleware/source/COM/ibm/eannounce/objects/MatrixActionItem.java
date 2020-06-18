//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MatrixActionItem.java,v $
// Revision 1.46  2011/11/10 20:37:11  wendy
// fix dump newline
//
// Revision 1.45  2009/05/19 11:55:26  wendy
// Support dereference for memory release
//
// Revision 1.44  2009/05/11 13:55:21  wendy
// Support turning off domain check for all actions
//
// Revision 1.43  2007/08/03 11:25:45  wendy
// RQ0713072645-1 Make actions sensitive to the PROFILE's PDHDOMAINs
//
// Revision 1.42  2006/05/10 15:37:03  tony
// cr 0428066810
//
// Revision 1.41  2005/08/22 20:28:22  tony
// improved keying of objects
//
// Revision 1.40  2005/08/10 16:14:23  tony
// improved catalog viewer functionality.
//
// Revision 1.39  2005/08/03 17:09:44  tony
// added datasource logic for catalog mod
//
// Revision 1.38  2005/03/04 21:35:51  dave
// Jtest work
//
// Revision 1.37  2005/01/18 21:46:50  dave
// more parm debug cleanup
//
// Revision 1.36  2004/11/03 23:08:33  joan
// work on searh picklist
//
// Revision 1.35  2004/07/15 17:28:59  joan
// add Single input
//
// Revision 1.34  2004/06/30 16:32:00  joan
// debug
//
// Revision 1.33  2004/06/29 22:56:42  joan
// work on feature matrix
//
// Revision 1.32  2004/06/21 22:09:28  joan
// add method
//
// Revision 1.31  2004/06/11 20:57:24  joan
// work on feature matrix
//
// Revision 1.30  2004/06/10 21:27:11  joan
// more work on special matrix
//
// Revision 1.29  2004/06/08 20:52:13  joan
// work on special matrix
//
// Revision 1.28  2003/10/30 02:21:32  dave
// more null pointer traps
//
// Revision 1.27  2003/10/30 00:43:34  dave
// fixing all the profile references
//
// Revision 1.26  2003/10/15 18:08:56  joan
// fix null pointer
//
// Revision 1.25  2003/08/26 21:40:08  dave
// forgot a change
//
// Revision 1.23  2003/08/26 21:28:08  dave
// wondering what ActionItemArray is all about
//
// Revision 1.22  2003/08/26 21:21:45  dave
// streamlining the Matrx Action Item
//
// Revision 1.21  2003/08/21 23:55:12  dave
// fix
//
// Revision 1.20  2003/08/21 23:46:08  dave
// minor error
//
// Revision 1.19  2003/08/21 23:33:41  dave
// Attempting an alternate way of getting the picklist through
// rmi
//
// Revision 1.18  2003/08/21 22:58:19  dave
// syntax and rmi leak plug
//
// Revision 1.17  2003/08/21 22:50:05  dave
// more streamlining
//
// Revision 1.16  2003/08/21 22:31:24  dave
// plugging leak in Where Used and Matrix
//
// Revision 1.15  2003/08/21 22:09:10  dave
// syntax
//
// Revision 1.14  2003/08/21 21:54:34  dave
// Attempting to streamline matrixActionItem
//
// Revision 1.13  2003/04/22 21:22:29  dave
// removed trace statements
//
// Revision 1.12  2003/04/22 21:07:32  dave
// trace to text Matrix
//
// Revision 1.11  2003/04/22 16:24:12  dave
// some Syntax error fixing
//
// Revision 1.10  2003/04/22 16:00:49  dave
// cleaning up Matrix.. only columns in the Nav Action Item
// need be shown.. and we are deriving the MetaLink
//
// Revision 1.9  2003/04/08 02:59:25  dave
// commit()
//
// Revision 1.8  2003/03/10 17:18:00  dave
// attempting to remove GBL7030 from the abstract Action Item
//
// Revision 1.7  2002/08/23 21:59:55  gregg
// updatePdhMeta method throws MiddlewareException
//
// Revision 1.6  2002/08/23 21:29:45  gregg
// updatePdhMeta(Database,boolean) method
//
// Revision 1.5  2002/07/17 18:12:24  joan
// fix getActionItemAsArray
//
// Revision 1.4  2002/07/16 21:32:21  joan
// working on getActionItemArray
//
// Revision 1.3  2002/06/19 15:52:19  joan
// work on add column in matrix
//
// Revision 1.2  2002/06/18 18:28:08  joan
// working on picklist
//
// Revision 1.1  2002/06/05 17:38:18  joan
// initial load
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.Vector;

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

/**
 *  Description of the Class
 *
 *@author     davidbig
 *@created    April 22, 2003
 */
public class MatrixActionItem extends EANActionItem {

    final static long serialVersionUID = 20011106L;

    /**
     * FIELD
     */
    protected String m_strEntityType = null;
    /**
     * FIELD
     */
    protected EANList m_el = new EANList();
    /**
     * FIELD
     */
    protected NavActionItem m_nai = null;
    /**
     * FIELD
     */
    protected EANList m_pickNavList = new EANList();
    /**
     * FIELD
     */
    protected EANList m_strRelAttrList = new EANList();
    /**
     * FIELD
     */
    protected EANList m_searchList = new EANList();

    /**
     * cr0428066810
     */
    protected int m_iDisplayLimit = 0;
    
    public void dereference(){
    	super.dereference();
    	m_strEntityType = null;
        
    	if (m_el !=null){
    		/*for (int i=0; i<m_el.size(); i++){
    			EntityItem mt = (EntityItem) m_el.getAt(i);
    			if (mt != null){
    				mt.dereference();
    			}
    		}
    		*/
    		m_el.clear();
    		m_el = null;
    	}
    	if (m_nai!=null){
    		m_nai.dereference();
    		m_nai = null;
    	}
        
        if (m_pickNavList!=null){
        	m_pickNavList.clear();
        	m_pickNavList = null;
        }
        if (m_strRelAttrList!=null){
        	m_strRelAttrList.clear();
        	m_strRelAttrList = null;
        }
        if (m_searchList!=null){
        	m_searchList.clear();
        	m_searchList = null;
        }
    }

    /**
     *  Main method which performs a simple test of this class
     *
     *@param  arg  Description of the Parameter
     */
    public static void main(String arg[]) {
    }

    /**
     *  Gets the version attribute of the MatrixActionItem object
     *
     *@return    The version value
     */
    public String getVersion() {
        return "$Id: MatrixActionItem.java,v 1.46 2011/11/10 20:37:11 wendy Exp $";
    }

    /**
     *  Constructor for the MatrixActionItem object
     *
     *@param  _mf                             Description of the Parameter
     *@param  _ai                             Description of the Parameter
     *@exception  MiddlewareRequestException  Description of the Exception
     */
    public MatrixActionItem(EANMetaFoundation _mf, MatrixActionItem _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
        m_strEntityType = _ai.m_strEntityType;
        m_el = _ai.m_el;
        m_nai = _ai.m_nai;
        m_pickNavList = _ai.m_pickNavList;
        m_strRelAttrList = _ai.m_strRelAttrList;
        m_searchList = _ai.m_searchList;
        m_iDisplayLimit = _ai.getDisplayLimit();			//cr0428066810
    }

    /**
     *  Constructor for the MatrixActionItem object
     *
     *@param  _ai                             Description of the Parameter
     *@exception  MiddlewareRequestException  Description of the Exception
     */
    public MatrixActionItem(MatrixActionItem _ai) throws MiddlewareRequestException {
        super(_ai);
        m_strEntityType = _ai.m_strEntityType;
        m_el = _ai.m_el;
        m_nai = _ai.m_nai;
        m_pickNavList = _ai.m_pickNavList;
        m_strRelAttrList = _ai.m_strRelAttrList;
        m_searchList = _ai.m_searchList;
        m_iDisplayLimit = _ai.getDisplayLimit();			//cr0428066810
    }

    /**
     *  This represents a Workflow Action Item. It can only be constructed via a
     *  database connection, a Profile, and an Action Item Key
     *
     *@param  _emf                            Description of the Parameter
     *@param  _db                             Description of the Parameter
     *@param  _prof                           Description of the Parameter
     *@param  _strActionItemKey               Description of the Parameter
     *@exception  SQLException                Description of the Exception
     *@exception  MiddlewareException         Description of the Exception
     *@exception  MiddlewareRequestException  Description of the Exception
     */
    public MatrixActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_emf, _db, _prof, _strActionItemKey);

        // Lets go get the information pertinent to the Matrix Action Item

        try {
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs;
            Profile prof = getProfile();

            try {
                rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
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
            setDomainCheck(true);

            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strType = rdrs.getColumn(ii, 0);
                String strCode = rdrs.getColumn(ii, 1);
                String strValue = rdrs.getColumn(ii, 2);

                _db.debug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");

                // Collect the attributes
                if (strType.equals("TYPE") && strCode.equals("EntityType")) {
                    setEntityType(strValue);
                    setTargetType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("NavAction")) {
                    setNavActionItem(new NavActionItem(null, _db, prof, strValue));
                } else if (strType.equals("PICK")) {
                    m_pickNavList.put(strCode, strValue);
                } else if (strType.equals("SEARCH")) {
                    m_searchList.put(strCode, strValue);
                } else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
                    super.setAssociatedEntityType(strValue);
                } else if (strType.equals("RELATTR")) {
                    m_strRelAttrList.put(strCode, strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SingleInput")) {
                    setSingleInput(true);
				} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
					setDataSource(strValue);											//catalog enhancement
				} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
					setAdditionalParms(strValue);
				} else if (strType.equals("TYPE") && strCode.equals("DisplayLimit")) {	//cr0428066810
					setDisplayLimit(strValue);											//cr0428066810
                } else if (strType.equals("TYPE") && strCode.equals("DomainCheck")) { // default is on, allow off
				    setDomainCheck(!strValue.equals("N")); //RQ0713072645
                } else {
                    _db.debug(D.EBUG_ERR, "*** ACTION ITEM ATTRIBUTE *** No home for this Action/Attribute" + strType + ":" + strCode + ":" + strValue);
                }
            }
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
        strbResult.append("MatrixActionItem:" + getKey() + ":desc:" + getLongDescription());
        strbResult.append(":purpose:" + getPurpose());
        strbResult.append(":entitytype:" + getEntityType() + "\n");
        return strbResult.toString();
    }

    /**
     *  Gets the purpose attribute of the MatrixActionItem object
     *
     *@return    The purpose value
     */
    public String getPurpose() {
        return "Matrix";
    }

    /**
     *  Gets the entityType attribute of the MatrixActionItem object
     *
     *@return    The entityType value
     */
    public String getEntityType() {
        return m_strEntityType;
    }

    /**
     *  Sets the entityType attribute of the MatrixActionItem object
     *
     *@param  _str  The new entityType value
     */
    protected void setEntityType(String _str) {
        m_strEntityType = _str;
    }

    /**
     *  Gets the navActionItem attribute of the MatrixActionItem object
     *
     *@return    The navActionItem value
     */
    public NavActionItem getNavActionItem() {
        return m_nai;
    }

    /**
     *  Sets the navActionItem attribute of the MatrixActionItem object
     *
     *@param  _nai  The new navActionItem value
     */
    protected void setNavActionItem(NavActionItem _nai) {
        m_nai = _nai;
        m_nai.setDisplayLimit(getDisplayLimit());		//cr0428066810
    }

    /**
     *  Gets the pickNavActionList attribute of the MatrixActionItem object
     *
     *@return    The pickNavActionList value
     */
    public EANList getPickNavActionList() {
        return m_pickNavList;
    }

    /**
     *  Sets the pickNavActionList attribute of the MatrixActionItem object
     *
     *@param  _el  The new pickNavActionList value
     */
    protected void setPickNavActionList(EANList _el) {
        m_pickNavList = _el;
    }

    /**
     *  Gets the SearchActionList attribute of the MatrixActionItem object
     *
     *@return    The SearchActionList value
     */
    public EANList getSearchActionList() {
        return m_searchList;
    }

    /**
     *  Sets the SearchActionList attribute of the MatrixActionItem object
     *
     *@param  _el  The new SearchActionList value
     */
    protected void setSearchActionList(EANList _el) {
        m_searchList = _el;
    }

    /*
     *  This is the array of EntityItems that will be Reported Against
     *  It will search any uplinks or downlinks if the type of
     *  entity passed was in a Group that is a relator.. and a
     *  native match could not  be found
     */
    /**
     *  Sets the entityItems attribute of the MatrixActionItem object
     *
     *@param  _aei  The new entityItems value
     */
    public void setEntityItems(EntityItem[] _aei) {
        resetEntityItems();
        // loop through and place the entityItem in the EANList
        for (int ii = 0; ii < _aei.length; ii++) {
            EntityItem ei = _aei[ii];
            //EntityItem processedEI = null;
            boolean bMatch = false;
            if (!ei.getEntityType().equals(getEntityType())) {
                EntityGroup eg = (EntityGroup) ei.getParent();
                // It cannot be added to the list
                if (eg == null) {
                    continue;
                }
                if (eg.isRelator() || eg.isAssoc()) {
                    //check the child entity item
                    EntityItem eic = (EntityItem) ei.getDownLink(0);
                    if (!eic.getEntityType().equals(getEntityType())) {
                        //check the parent entity item
                        EntityItem eip = (EntityItem) ei.getUpLink(0);
                        if (!eip.getEntityType().equals(getEntityType())) {
                            bMatch = false;
                        } else {
                            ei = eip;
                            bMatch = true;
                        }
                    } else {
                        ei = eic;
                        bMatch = true;
                    }
                }
            } else {
                bMatch = true;
            }

            // If you found a match on entitytype.. please put it on the list
            if (bMatch) {
                m_el.put(ei);
            } else {
                System.err.println("MatrixActionItem.setEntityItems. Warning: No match for entityItem and EntityType in action:" + getEntityType() + ":" + ei.getKey());
            }
        }
    }

    /**
     *  Gets the entityItems attribute of the MatrixActionItem object
     *
     *@return    The entityItems value
     */
    public EANList getEntityItems() {
        return m_el;
    }

    /**
     *  Description of the Method
     */
    public void resetEntityItems() {
        m_el = new EANList();
    }

    /**
     *  Gets the entityItemArray attribute of the MatrixActionItem object
     *
     *@return    The entityItemArray value
     */
    protected EntityItem[] getEntityItemArray() {
        int size = m_el.size();
        EntityItem[] aeiReturn = new EntityItem[size];
        for (int i = 0; i < size; i++) {
            EntityItem ei = (EntityItem) m_el.getAt(i);
            aeiReturn[i] = ei;
        }
        return aeiReturn;
    }

    /**
     * getEntityItemArray
     *
     * @param _el
     * @return
     *  @author David Bigelow
     */
    protected static EntityItem[] getEntityItemArray(EANList _el) {
        int size = _el.size();
        EntityItem[] aeiReturn = new EntityItem[size];
        for (int i = 0; i < size; i++) {
            EntityItem ei = (EntityItem) _el.getAt(i);
            aeiReturn[i] = ei;
        }
        return aeiReturn;
    }

    /**
     * exec
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.LockException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    public MatrixList exec(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, LockException, MiddlewareShutdownInProgressException, RemoteException {
        MatrixList ml = _db.executeAction(_prof, this);
        resetEntityItems();
        return ml;
    }

    /**
     * rexec
     *
     * @param _rdi
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    public MatrixList rexec(RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException {
		EntityList.checkDomain(_prof,this,m_el); //RQ0713072645

        MatrixList ml = null;
        MatrixActionItem mai = new MatrixActionItem(this);

        EANList el = new EANList();
        for (int ii = 0; ii < m_el.size(); ii++) {
            //el.put(new EntityItem((EntityItem) m_el.getAt(ii)));
            EntityItem ei = (EntityItem) m_el.getAt(ii);
            el.put(new EntityItem(null, ei.getProfile(), ei.getEntityType(), ei.getEntityID()));
        }
        mai.m_el = el;

        ml = _rdi.executeAction(_prof, mai);
        resetEntityItems();
        return ml;
    }

    /**
     * executeAction
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @return
     *  @author David Bigelow
     */
    public MatrixList executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
        _db.debug(D.EBUG_DETAIL, " MatrixActionItem executeAction method");
		EntityList.checkDomain(_prof,this,m_el); //RQ0713072645

        return new MatrixList(_db, _prof, this);
    }

    /**
     * rgeneratePickList
     *
     * @param _rdi
     * @param _prof
     * @param _strRelatorType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    public EntityList rgeneratePickList(RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException {

        String strTraceBase = " MatrixActionItem rgetPickList method";

        EANList eanl = new EANList();
        EntityItem[] aei = null;
        String strPL = null;

        D.ebug(D.EBUG_DETAIL, strTraceBase + _strRelatorType);
        // Build a stubbed entity item list
        for (int ii = 0; ii < m_el.size(); ii++) {
            EntityItem ei = (EntityItem) m_el.getAt(ii);
            eanl.put(new EntityItem(null, ei.getProfile(), ei.getEntityType(), ei.getEntityID()));
        }
        //now.. lets set up the call..
        aei = getEntityItemArray(eanl);
        strPL = (String) m_pickNavList.get(_strRelatorType);
        if (strPL != null) {
            NavActionItem nai = new NavActionItem(null, _rdi, _prof, strPL);
            return EntityList.getEntityList(_rdi, _prof, nai, aei);
        }

        return null;
    }

    /**
     *  Gets the EntityList(picklist) of the MatrixActionItem object.  The database version
     *
     * @param _db
     * @param _prof
     * @param _strRelatorType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @return EntityList
     */
    public EntityList getPickList(Database _db, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {

        String strTraceBase = "MatrixActionItem getPickList method ";
        EntityItem[] aei = getEntityItemArray();
        String strPL = (String) m_pickNavList.get(_strRelatorType);

        _db.debug(D.EBUG_DETAIL, strTraceBase + _strRelatorType);

        if (strPL != null && strPL.length() > 0) {
            NavActionItem nai = new NavActionItem(null, _db, _prof, strPL);
            if (nai != null) {
                return EntityList.getEntityList(_db, _prof, nai, aei);
            }
        }

        return null;
    }

    /**
     * generatePickList
     *
     * @param _db
     * @param _prof
     * @param _strRelatorType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.LockException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    public EntityList generatePickList(Database _db, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareException, MiddlewareRequestException, LockException, MiddlewareShutdownInProgressException, RemoteException {
        return getPickList(_db, _prof, _strRelatorType);
    }

    /**
     * getActionItemArray
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _strRelatorType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    public Object[] getActionItemArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        //get picklist NavActionItem
        Vector aiVector = new Vector();

        String strNai = (String) m_pickNavList.get(_strRelatorType);

        String strSearch = (String) m_searchList.get(_strRelatorType);

        if (strNai != null && strNai.length() > 0) {
            NavActionItem nai = null;
            if (_db != null) {
                nai = new NavActionItem(null, _db, _prof, strNai);
            } else {
                nai = (NavActionItem) ObjectPool.getInstance().getActionItem(strNai);
                if (nai == null) {
                    nai = _rdi.getNavActionItem(null, _prof, strNai);
                    ObjectPool.getInstance().putActionItem(nai);
                } else {
                    D.ebug("getActionItemArray:Found NavActionItem in Object Pool" + nai);
                }

            }
            if (nai != null) {
                aiVector.addElement(nai);
            }
        }

        if (strSearch != null && strSearch.length() > 0) {
            SearchActionItem sai = null;
            if (_db != null) {
                sai = new SearchActionItem(null, _db, _prof, strSearch);
            } else {
                sai = (SearchActionItem) ObjectPool.getInstance().getActionItem(strSearch);
                if (sai == null) {
                    sai = _rdi.getSearchActionItem(null, _prof, strSearch);
                    ObjectPool.getInstance().putActionItem(sai);
                } else {
                    D.ebug("getActionItemArray:Found SearchActionItem in Object Pool" + sai);
                }
            }

            if (sai != null) {
                aiVector.addElement(sai);
            }
        }

        if (aiVector.size() > 0) {
            return aiVector.toArray();
        } else {
            return null;
        }

    }

    /**
     * (non-Javadoc)
     * updatePdhMeta
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#updatePdhMeta(COM.ibm.opicmpdh.middleware.Database, boolean)
     */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        return true;
    }
    // for special matrix
    /**
     * isFeatureMatrix
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isFeatureMatrix() {
        if (m_strRelAttrList.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * showRelAttr
     *
     * @param _strRelatorType
     * @return
     *  @author David Bigelow
     */
    public boolean showRelAttr(String _strRelatorType) {
        if (m_strRelAttrList.get(_strRelatorType) != null) {
            return true;
        }
        return false;
    }

    /**
     * getRelAttrCode
     *
     * @param _strRelatorType
     * @return
     *  @author David Bigelow
     */
    public String getRelAttrCode(String _strRelatorType) {
        return (String) m_strRelAttrList.get(_strRelatorType);
    }

/*
 cr0428066810
 */
 	protected int getDisplayLimit() {
		return m_iDisplayLimit;
	}

	protected void setDisplayLimit(String _s) {
		try {
			setDisplayLimit(Integer.parseInt(_s));
		} catch (NumberFormatException _nfe) {
		}
		return;
	}

	protected void setDisplayLimit(int _i) {
		m_iDisplayLimit = _i;
		if (m_nai != null) {
			m_nai.setDisplayLimit(_i);
		}
		return;
	}

}
