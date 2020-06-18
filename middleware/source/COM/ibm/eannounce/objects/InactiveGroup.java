//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: InactiveGroup.java,v $
// Revision 1.20  2010/05/13 17:32:43  wendy
// check for null before rs.close
//
// Revision 1.19  2009/05/19 16:22:02  wendy
// Support dereference for memory release
//
// Revision 1.18  2005/11/04 14:52:11  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.17  2005/03/03 22:39:32  dave
// JTest working and cleanup
//
// Revision 1.16  2005/01/18 21:46:50  dave
// more parm debug cleanup
//
// Revision 1.15  2005/01/10 21:47:49  joan
// work on multiple edit
//
// Revision 1.14  2005/01/05 19:24:08  joan
// add new method
//
// Revision 1.13  2004/06/08 17:51:31  joan
// throw exception
//
// Revision 1.12  2004/06/08 17:28:34  joan
// add method
//
// Revision 1.11  2004/04/09 19:37:20  joan
// add duplicate method
//
// Revision 1.10  2003/08/28 16:28:06  joan
// adjust link method to have link option
//
// Revision 1.9  2003/08/20 22:17:28  joan
// fb 51812
//
// Revision 1.8  2003/07/24 18:05:12  joan
// adjust col
//
// Revision 1.7  2003/07/24 17:12:35  joan
// adjust return
//
// Revision 1.6  2003/07/24 16:22:23  joan
// throw exception
//
// Revision 1.5  2003/07/24 15:51:16  joan
// add InactiveGroup
//
// Revision 1.4  2003/07/23 21:59:41  joan
// throw exception
//
// Revision 1.3  2003/07/23 21:48:50  joan
// fix error
//
// Revision 1.2  2003/07/23 21:33:40  joan
// fix compile
//
// Revision 1.1  2003/07/23 21:21:35  joan
// initial load
//
//

package COM.ibm.eannounce.objects;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

// Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

import java.sql.SQLException;
import java.rmi.RemoteException;
import COM.ibm.opicmpdh.middleware.LockException;

/**
 * InactiveGroup
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InactiveGroup extends EANMetaEntity implements EANTableWrapper {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    static final int UNRESTRICTED = 2;
    static final int RESTRICTED = 1;

    public void dereference(){
    	for (int i=0; i<getInactiveItemCount(); i++){
    		InactiveItem li = getInactiveItem(i);
    		li.dereference();
    	}
    	super.dereference();
    }
    
    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates a new InactiveGroup for the given Profile
     *
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public InactiveGroup(Profile _prof) throws MiddlewareException, MiddlewareRequestException {
        super(null, _prof, _prof.getEnterprise() + "");
    }

    /**
     * InactiveGroup
     *
     * @param _db
     * @param _prof
     * @param _strStartDate
     * @param _bViewAll
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws java.sql.SQLException
     *  @author David Bigelow
     */
    public InactiveGroup(Database _db, Profile _prof, String _strStartDate, boolean _bViewAll) throws MiddlewareException, MiddlewareRequestException, SQLException {
        super(null, _prof, _prof.getEnterprise() + "");

        String strTraceBase = "InactiveGroup constructor";

        try {

            // The stored procedure ReturnStatus
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ReturnDataResultSet rdrs = null;
            ResultSet rs = null;

            // Get now
            DatePackage dpNow = _db.getDates();
            String strNow = dpNow.getNow();

            String strEnterprise = _prof.getEnterprise();
            int iOpenID = _prof.getOPWGID();

            // Set up some variables
            String strEntityClass = null;
            String strEntityType = null;
            int iEntityID = 0;
            String strDeactivatedDate = null;
            String strEntityDescription = null;
            String strDisplayName = null;
            String strUserName = null;
            String strRoleInfo = null;
            int iCase = RESTRICTED;

            if (_bViewAll) {
                iCase = UNRESTRICTED;
            }

            try {
                rs = _db.callGBL2935(returnStatus, strEnterprise, iCase, iOpenID, _strStartDate, strNow, strNow, strNow);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if(rs!=null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            for (int j = 0; j < rdrs.size(); j++) {
                InactiveItem iai = null;
                strEntityClass = rdrs.getColumn(j, 0).trim();
                strEntityType = rdrs.getColumn(j, 1).trim();
                iEntityID = rdrs.getColumnInt(j, 2);
                strDeactivatedDate = rdrs.getColumn(j, 3).trim();
                strEntityDescription = rdrs.getColumn(j, 4).trim();
                strDisplayName = rdrs.getColumn(j, 5).trim();
                strUserName = rdrs.getColumn(j, 6).trim();
                strRoleInfo = rdrs.getColumn(j, 7).trim();

                _db.debug(D.EBUG_SPEW, strTraceBase + " gbl2935" + strEntityClass + ":" + strEntityType + ":" + iEntityID + ":" + strDeactivatedDate + ":" + strEntityDescription + ":" + strDisplayName + ":" + strUserName + ":" + strRoleInfo);

                // Pull it .. and Put it in the group
                iai = new InactiveItem(this, strEntityClass, strEntityType, iEntityID, strDeactivatedDate, strEntityDescription, strDisplayName, strUserName, strRoleInfo);
                putInactiveItem(iai);
            }

            try {
            	rs = _db.callGBL2936(returnStatus, strEnterprise, iCase, iOpenID, _strStartDate, strNow, strNow, strNow);
            	rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if(rs!=null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            for (int j = 0; j < rdrs.size(); j++) {
                InactiveItem iai = null;
                strEntityClass = rdrs.getColumn(j, 0).trim();
                strEntityType = rdrs.getColumn(j, 1).trim();
                iEntityID = rdrs.getColumnInt(j, 2);
                strDeactivatedDate = rdrs.getColumn(j, 3).trim();
                strEntityDescription = rdrs.getColumn(j, 4).trim();
                strDisplayName = rdrs.getColumn(j, 5).trim();
                strUserName = rdrs.getColumn(j, 6).trim();
                strRoleInfo = rdrs.getColumn(j, 7).trim();

                _db.debug(D.EBUG_SPEW, "gbl2936" + strEntityClass + ":" + strEntityType + ":" + iEntityID + ":" + strDeactivatedDate + ":" + strEntityDescription + ":" + strDisplayName + ":" + strUserName + ":" + strRoleInfo);

                // Pull it .. and Put it in the group
                iai = new InactiveItem(this, strEntityClass, strEntityType, iEntityID, strDeactivatedDate, strEntityDescription, strDisplayName, strUserName, strRoleInfo);

                putInactiveItem(iai);
            }

        } catch (RuntimeException rx) {
            StringWriter writer = new StringWriter();
            String x = writer.toString();
            _db.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strTraceBase + " " + rx);
            rx.printStackTrace(new PrintWriter(writer));
            _db.debug(D.EBUG_ERR, "" + x);
            throw new MiddlewareException("RuntimeException trapped at: " + strTraceBase + rx);
        } finally {

            // Free any statement
            _db.freeStatement();
            _db.isPending();
            _db.debug(D.EBUG_DETAIL, strTraceBase + " complete");
        }
    }

    /**
     * putInactiveItem
     *
     * @param _iai
     *  @author David Bigelow
     */
    public void putInactiveItem(InactiveItem _iai) {
        putData(_iai);
    }
    /**
     * returns the number of SoftInactiveItems in this list
     *
     * @return int
     */
    public int getInactiveItemCount() {
        return getDataCount();
    }

    /**
     * returns the EANList that contains all the tracked InactiveItems
     *
     * @return EANList
     */
    public EANList getInactiveItem() {
        return getData();
    }

    /**
     * returns the InactiveItem at index i
     *
     * @return InactiveItem
     * @param _i
     */
    public InactiveItem getInactiveItem(int _i) {
        return (InactiveItem) getData(_i);
    }

    /**
     * returns the InactiveItem based upon the passed Key (EntityType + EntityID)
     *
     * @return InactiveItem
     * @param _str
     */
    public InactiveItem getInactiveItem(String _str) {
        return (InactiveItem) getData(_str);
    }

    /**
     * returns the InactiveItem based upon the passed EntityItem
     *
     * @return InactiveItem
     * @param _ei
     */
    public InactiveItem getInactiveItem(EntityItem _ei) {
        return (InactiveItem) getData(_ei.getKey());
    }

    /*
    * Resets the InactiveItems in this list
    */
    /**
     * removeInactiveItem
     *
     *  @author David Bigelow
     */
    public void removeInactiveItem() {
        resetData();
    }

    /*
    * Removes the InactiveItem in the list based upon the passed Key
    * @param str (EntityType + EntityID)
    */
    /**
     * removeInactiveItem
     *
     * @param _str
     *  @author David Bigelow
     */
    public void removeInactiveItem(String _str) {
        getData().remove(_str);
    }

    /*
    * Removes the InactiveItem in the list based upon the passed index
    * @param i index
    */
    /**
     * removeInactiveItem
     *
     * @param _i
     *  @author David Bigelow
     */
    public void removeInactiveItem(int _i) {
        getData().remove(_i);
    }

    /*
    * Removes the InactiveItem in the list based upon EntityItem's getKey
    */
    /**
     * removeInactiveItem
     *
     * @param _ei
     *  @author David Bigelow
     */
    public void removeInactiveItem(EntityItem _ei) {
        getData().remove(_ei.getKey());
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: InactiveGroup.java,v 1.20 2010/05/13 17:32:43 wendy Exp $";
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("InactiveGroup:" + getKey());
        if (!_bBrief) {
            for (int i = 0; i < getInactiveItemCount(); i++) {
                InactiveItem lg = getInactiveItem(i);
                strbResult.append(NEW_LINE + lg.dump(_bBrief));
            }
        }
        return strbResult.toString();
    }

    /**
     * getTable
     *
     * @return
     *  @author David Bigelow
     */
    public RowSelectableTable getTable() {
        return new RowSelectableTable(this, getLongDescription());
    }


    // Returns the column Stuff
    /**
     * (non-Javadoc)
     * getColumnList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getColumnList()
     */
    public EANList getColumnList() {
        EANList colList = new EANList();
        try {
            MetaLabel ml1 = new MetaLabel(this, getProfile(), InactiveItem.CLASS, "Entity Class", String.class);
            MetaLabel ml2 = new MetaLabel(this, getProfile(), InactiveItem.TYPE, "Entity Type", String.class);
            MetaLabel ml3 = new MetaLabel(this, getProfile(), InactiveItem.ID, "Entity ID", String.class);
            MetaLabel ml4 = new MetaLabel(this, getProfile(), InactiveItem.INACTIVEDATE, "Inactive Date", String.class);
            MetaLabel ml5 = new MetaLabel(this, getProfile(), InactiveItem.DESCRIPTION, "Entity Description", String.class);
            MetaLabel ml6 = new MetaLabel(this, getProfile(), InactiveItem.NAME, "Entity Name", String.class);
            MetaLabel ml7 = new MetaLabel(this, getProfile(), InactiveItem.USER, "User Name", String.class);
            MetaLabel ml8 = new MetaLabel(this, getProfile(), InactiveItem.ROLE, "Role", String.class);

            ml1.putShortDescription("Entity Class");
            ml2.putShortDescription("Entity Type");
            ml3.putShortDescription("Entity ID");
            ml4.putShortDescription("Inactive Date");
            ml5.putShortDescription("Entity Description");
            ml6.putShortDescription("Entity Name");
            ml7.putShortDescription("User Name");
            ml8.putShortDescription("Role");

            colList.put(ml1);
            colList.put(ml2);
            colList.put(ml3);
            colList.put(ml4);
            colList.put(ml5);
            colList.put(ml6);
            colList.put(ml7);
            colList.put(ml8);
        } catch (Exception x) {
            x.printStackTrace();
        }
        return colList;
    }

    /**
     * (non-Javadoc)
     * getRowList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getRowList()
     */
    public EANList getRowList() {
        EANList rowList = new EANList();
        for (int i = 0; i < getInactiveItemCount(); i++) {
            InactiveItem iai = getInactiveItem(i);
            try {
                rowList.put(new Implicator(iai, getProfile(), iai.getKey()));
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
        return rowList;
    }

    /**
     * deactivatedUndo
     *
     * @param _db
     * @param _prof
     * @param _aiai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.sql.SQLException
     * @return
     *  @author David Bigelow
     */
    public InactiveGroup deactivatedUndo(Database _db, Profile _prof, InactiveItem[] _aiai) throws MiddlewareException, SQLException {
        // The stored procedure ReturnStatus

        String strEntityType = null;
        int iEntityID = 0;

        ReturnStatus returnStatus = new ReturnStatus(-1);

        // Information about exception location
        String strMethod = "InactiveGroup deactivatedUndo";

        DatePackage dpNow = _db.getDates();
        String strNow = dpNow.getNow();
        String strEnterprise = _prof.getEnterprise();
        int iOpenID = _prof.getOPWGID();

        try {
            _db.debug(D.EBUG_DETAIL, strMethod + " transaction");
            _db.debug(D.EBUG_DETAIL, "deactivatedUndo:Enterprise: " + strEnterprise);
            _db.debug(D.EBUG_DETAIL, "deactivatedUndo:OPENID: " + iOpenID);
            _db.debug(D.EBUG_DETAIL, "deactivatedUndo:STRNOW: " + strNow); //17577
            // If we are not already connected, now is the time!

            // Process updates for each Entity/Relator
            for (int i = 0; i < _aiai.length; i++) {
                InactiveItem iai = _aiai[i];

                if (iai == null) {
                    _db.debug(D.EBUG_DETAIL, "deactivatedUndo:InactiveItem is null - skipping it.");
                    continue;
                }

                strEntityType = iai.getEntityType();
                iEntityID = iai.getEntityID();
                _db.debug(D.EBUG_DETAIL, "deactivateUNDO:" + strEntityType);
                _db.debug(D.EBUG_DETAIL, "deactivateUNDO:" + iEntityID);

                _db.callGBL2934(returnStatus, strEnterprise, iOpenID, strEntityType, iEntityID, _prof.getTranID());
                _db.commit();
                _db.freeStatement();
                _db.isPending();

                removeInactiveItem(iai.getKey());
            }
        } catch (RuntimeException rx) {
            StringWriter writer = new StringWriter();
            String x = writer.toString();
            _db.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + " " + rx);
            rx.printStackTrace(new PrintWriter(writer));
            _db.debug(D.EBUG_ERR, "" + x);

            throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
        } finally {
            // Free any statement
            _db.freeStatement();
            _db.isPending();
            _db.debug(D.EBUG_DETAIL, strMethod + " complete");
        }
        return this;
    }

    /**
     * removeInactiveItem
     *
     * @param _db
     * @param _rdi
     * @param _prof
     * @param _aiai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    public InactiveGroup removeInactiveItem(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, InactiveItem[] _aiai) throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException, RemoteException {
        if (_db == null && _rdi == null) {
            System.out.println("InactiveGroup removeInactiveItem: both db and rdi are null");
            return null;
        }

        if (_aiai != null) {
            if (_db != null) {
                return _db.deactivatedUndo(_prof, this, _aiai);
            } else {
                return _rdi.deactivatedUndo(_prof, this, _aiai);
            }
        }
        return null;
    }

    /**
     * (non-Javadoc)
     * canCreate
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#canCreate()
     */
    public boolean canCreate() {
        return false;
    }
    /**
     * (non-Javadoc)
     * canEdit
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#canEdit()
     */
    public boolean canEdit() {
        return false;
    }
    /**
     * (non-Javadoc)
     * addRow
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#addRow()
     */
    public boolean addRow() {
        return false;
    }

    /**
     * (non-Javadoc)
     * addRow
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#addRow(String)
     */
    public boolean addRow(String _strKey) {
        return false;
    }

    /**
     * (non-Javadoc)
     * removeRow
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#removeRow(java.lang.String)
     */
    public void removeRow(String _strKey) {
    }
    /**
     * (non-Javadoc)
     * hasChanges
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#hasChanges()
     */
    public boolean hasChanges() {
        return false;
    }
    /**
     * (non-Javadoc)
     * commit
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#commit(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface)
     */
    public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
    }
    /**
     * (non-Javadoc)
     * getMatrixValue
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getMatrixValue(java.lang.String)
     */
    public Object getMatrixValue(String _str) {
        return null;
    }
    /**
     * (non-Javadoc)
     * putMatrixValue
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#putMatrixValue(java.lang.String, java.lang.Object)
     */
    public void putMatrixValue(String _str, Object _o) {
    }
    /**
     * (non-Javadoc)
     * isMatrixEditable
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#isMatrixEditable(java.lang.String)
     */
    public boolean isMatrixEditable(String _str) {
        return false;
    }
    /**
     * (non-Javadoc)
     * rollbackMatrix
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#rollbackMatrix()
     */
    public void rollbackMatrix() {
    }
    /**
     * (non-Javadoc)
     * addColumn
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#addColumn(COM.ibm.eannounce.objects.EANFoundation)
     */
    public void addColumn(EANFoundation _ean) {
    }
    /**
     * (non-Javadoc)
     * generatePickList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#generatePickList(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }
    /**
     * (non-Javadoc)
     * removeLink
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#removeLink(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey) throws EANBusinessRuleException {
        return false;
    }
    /**
     * (non-Javadoc)
     * link
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#link(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String, COM.ibm.eannounce.objects.EntityItem[], java.lang.String)
     */
    public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey, EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException {
        return null;
    }
    /**
     * (non-Javadoc)
     * create
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#create(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
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
     * (non-Javadoc)
     * getWhereUsedList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getWhereUsedList(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }
    /**
     * (non-Javadoc)
     * getActionItemsAsArray
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getActionItemsAsArray(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, java.lang.String)
     */
    public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) {
        return null;
    }
    /**
     * (non-Javadoc)
     * isDynaTable
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#isDynaTable()
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
