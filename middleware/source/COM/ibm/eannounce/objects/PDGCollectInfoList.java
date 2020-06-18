//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: PDGCollectInfoList.java,v $
// Revision 1.18  2006/02/20 21:50:04  joan
// clean up System.out.println
//
// Revision 1.17  2006/02/20 21:39:48  joan
// clean up System.out.println
//
// Revision 1.16  2005/11/04 14:52:11  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.15  2005/10/12 18:03:09  joan
// fixes
//
// Revision 1.14  2005/03/10 00:17:47  dave
// more Jtest work
//
// Revision 1.13  2005/01/10 21:47:49  joan
// work on multiple edit
//
// Revision 1.12  2005/01/05 19:24:09  joan
// add new method
//
// Revision 1.11  2004/11/10 21:39:41  joan
// fixes
//
// Revision 1.10  2004/08/04 17:43:04  joan
// fix bug
//
// Revision 1.9  2004/08/04 17:30:55  joan
// add new PDG
//
// Revision 1.6  2004/04/09 19:37:21  joan
// add duplicate method
//
// Revision 1.5  2003/10/31 19:21:27  joan
// change constructor signature
//
// Revision 1.4  2003/10/17 17:42:39  joan
// remove println
//
// Revision 1.3  2003/08/28 16:28:08  joan
// adjust link method to have link option
//
// Revision 1.2  2003/06/26 22:40:41  joan
// initial load
//
// Revision 1.1.2.4  2003/06/25 00:15:28  joan
// fix compile
//
// Revision 1.1.2.3  2003/06/24 23:37:29  joan
// fix WhereUsedActionItem constructor
//
// Revision 1.1.2.2  2003/06/19 19:54:02  joan
// working on HW Upgrade
//

package COM.ibm.eannounce.objects;

import java.util.StringTokenizer;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.Profile;
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
 * PDGCollectInfoList
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PDGCollectInfoList extends EANMetaEntity implements EANTableWrapper {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    private EANList m_infoList = new EANList();
    private boolean m_bMatrix = true;
    private String[] m_aColNames = null;
    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * PDGCollectInfoList
     *
     * @param _mf
     * @param _prof
     * @param _strKey
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public PDGCollectInfoList(EANMetaFoundation _mf, Profile _prof, String _strKey) throws MiddlewareRequestException {
        super(_mf, _prof, _strKey);
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("PDGCollectInfoList: " + getKey());
        if (!_bBrief) {
            for (int i = 0; i < getCollectInfoItemCount(); i++) {
                PDGCollectInfoItem pi = getCollectInfoItem(i);
                strbResult.append(pi.dump(_bBrief));
            }
        }
        return new String(strbResult);
    }

    /*
    * return the column information here
    */
    /**
     * (non-Javadoc)
     * getColumnList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getColumnList()
     */
    public EANList getColumnList() {
        EANList colList = new EANList();
        if (m_bMatrix) {
            for (int i = 0; i < getCollectInfoItemCount(); i++) {
                PDGCollectInfoItem ci = getCollectInfoItem(i);
                String str = ci.getSecondItem();
                try {
                    MetaLabel ml = new MetaLabel(this, getProfile(), str, str, String.class);
                    colList.put(ml);
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        } else {
            for (int i = 0; i < m_aColNames.length; i++) {
                String str = m_aColNames[i];
                try {
                    MetaLabel ml = new MetaLabel(this, getProfile(), str, str, String.class);
                    colList.put(ml);
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        }
        return colList;
    }

    /*
    *	Return only visible rows
    */
    /**
     * (non-Javadoc)
     * getRowList
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getRowList()
     */
    public EANList getRowList() {
        EANList rowList = new EANList();
        if (m_bMatrix) {
            for (int i = 0; i < getCollectInfoItemCount(); i++) {
                PDGCollectInfoItem ci = getCollectInfoItem(i);
                String str = ci.getFirstItem();
                try {
                    MetaLabel ml = new MetaLabel(this, getProfile(), str, str, String.class);

                    rowList.put(ml);
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }
        } else {
            rowList = getData();
        }

        return rowList;
    }

    /**
     * setMatrix
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setMatrix(boolean _b) {
        m_bMatrix = _b;
    }

    /**
     * isMatrix
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isMatrix() {
        return m_bMatrix;
    }

    /**
     * setColNames
     *
     * @param _aNames
     *  @author David Bigelow
     */
    public void setColNames(String[] _aNames) {
        m_aColNames = _aNames;
    }

    /*
    * Returns a basic table adaptor for client rendering of EntityLists
    */
    /**
     * getTable
     *
     * @return
     *  @author David Bigelow
     */
    public RowSelectableTable getTable() {
        return new RowSelectableTable(this, getKey());
    }

    /**
     * getCollectInfoItemCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getCollectInfoItemCount() {
        return getDataCount();
    }

    /**
     * putCollectInfoItem
     *
     * @param _ci
     *  @author David Bigelow
     */
    public void putCollectInfoItem(PDGCollectInfoItem _ci) {
        putData(_ci);
    }

    /**
     * getCollectInfoItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public PDGCollectInfoItem getCollectInfoItem(int _i) {
        return (PDGCollectInfoItem) getData(_i);
    }

    /**
     * getCollectInfoItem
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public PDGCollectInfoItem getCollectInfoItem(String _s) {
        return (PDGCollectInfoItem) getData(_s);
    }

    /**
     * removeCollectInfoItem
     *
     *  @author David Bigelow
     */
    public void removeCollectInfoItem() {
        resetData();
        m_infoList = new EANList();
    }

    /**
     * removeCollectInfoItem
     *
     * @param _str
     *  @author David Bigelow
     */
    public void removeCollectInfoItem(String _str) {
        getData().remove(_str);
    }

    /**
     * removeCollectInfoItem
     *
     * @param _i
     *  @author David Bigelow
     */
    public void removeCollectInfoItem(int _i) {
        getData().remove(_i);
    }

    /**
     * equivalent
     *
     * @param _eia
     * @param _ai
     * @return
     *  @author David Bigelow
     */
    public boolean equivalent(EntityItem[] _eia, EANActionItem _ai) {
        return false;
    }

    /**
     * (non-Javadoc)
     * getMatrixValue
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#getMatrixValue(java.lang.String)
     */
    public Object getMatrixValue(String _str) {
        //		D.ebug(D.EBUG_SPEW,"PDGCollectInfoList getMatrixValue(" + _str);
        StringTokenizer st = new StringTokenizer(_str, ":");
        String str1 = st.nextToken();
        String str2 = st.nextToken();

        if (m_bMatrix) {
            PDGCollectInfoItem ci = getCollectInfoItem(str1 + str2);
            if (ci != null) {
                return ci.getSelected();
            } else {
                return null;
            }
        } else {
            //			D.ebug(D.EBUG_SPEW,"PDGCollectInfoList getMatrixValue in not matrix");
            PDGCollectInfoItem ci = getCollectInfoItem(str1);
            if (ci != null) {
                for (int i = 0; i < m_aColNames.length; i++) {
                    String strName = m_aColNames[i].trim();
                    if (strName.equals(str2.trim())) {
                        if (i == 0) {
                            //							D.ebug(D.EBUG_SPEW,"PDGCollectInfoList getMatrixValue i==0");
                            return ci.getSelected();
                        } else {
                            return ci.m_aColInfos[i];
                        }
                    }
                }
            }

            return null;
        }
    }

    /**
     * (non-Javadoc)
     * putMatrixValue
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#putMatrixValue(java.lang.String, java.lang.Object)
     */
    public void putMatrixValue(String _str, Object _o) {
        StringTokenizer st = new StringTokenizer(_str, ":");
        String str1 = st.nextToken();
        String str2 = st.nextToken();

        D.ebug(D.EBUG_SPEW,"PDGCollectInfoList setMatrixValue(" + _str);

        if (!(_o instanceof Boolean)) {
            return;
        }

        if (m_bMatrix) {
            PDGCollectInfoItem ci = getCollectInfoItem(str1 + str2);
            if (ci != null) {
                if (ci.isEditable()) {
                    boolean b = ((Boolean) _o).booleanValue();
                    ci.setSelected(b);
                    D.ebug(D.EBUG_SPEW,"set value for ci: " + ci.getKey());
                    if (b) {
                        m_infoList.put(ci);
                    } else {
                        m_infoList.remove(ci);
                    }
                }
            }
        } else {
            PDGCollectInfoItem ci = getCollectInfoItem(str1);
            D.ebug(D.EBUG_SPEW,"PDGCollectInfoList setMatrixValue in not matrix");
            if (ci != null) {
                if (ci.isEditable()) {
                    boolean b = ((Boolean) _o).booleanValue();
                    ci.setSelected(b);
                    D.ebug(D.EBUG_SPEW,"set value for ci: " + ci.getKey());
                    if (b) {
                        m_infoList.put(ci);
                    } else {
                        m_infoList.remove(ci);
                    }
                }
            }
        }
    }

    /**
     * getInfoList
     *
     * @return
     *  @author David Bigelow
     */
    public EANList getInfoList() {
        return m_infoList;
    }

    /**
     * (non-Javadoc)
     * isMatrixEditable
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#isMatrixEditable(java.lang.String)
     */
    public boolean isMatrixEditable(String _str) {

        StringTokenizer st = new StringTokenizer(_str, ":");
        String strRow = st.nextToken();
        String strCol = st.nextToken();
        D.ebug(D.EBUG_SPEW,"PDGCollectInfoList isMatrixEditable(" + _str);

        if (m_bMatrix) {
            PDGCollectInfoItem ci = getCollectInfoItem(strRow + strCol);
            if (ci != null) {
                return ci.isEditable();
            } else {
                return false;
            }
        } else {
            PDGCollectInfoItem ci = getCollectInfoItem(strRow);
//            D.ebug(D.EBUG_SPEW,"PDGCollectInfoList isMatrixEditable in not matrix");
            if (ci != null) {
                for (int i = 0; i < m_aColNames.length; i++) {
                    String strName = m_aColNames[i].trim();
                    if (strName.equals(strCol.trim())) {
                        if (i == 0) {
//                            D.ebug(D.EBUG_SPEW,"PDGCollectInfoList isMatrixEditable i==0");
                            return ci.isEditable();
                        } else {
//                            D.ebug(D.EBUG_SPEW,"PDGCollectInfoList isMatrixEditable i != 0");
                            return false;
                        }
                    }
                }
            }
        }
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
     * canEdit
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#canEdit()
     */
    public boolean canEdit() {
        return true;
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
     * @see COM.ibm.eannounce.objects.EANTableWrapper#addRow(java.lang.String)
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
