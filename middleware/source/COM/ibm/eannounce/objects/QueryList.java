//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.objects;

import java.util.*;
import COM.ibm.opicmpdh.middleware.*;

import java.sql.*;
import java.rmi.RemoteException;

/**
 *  execute a db2 view
 *
 */
//$Log: QueryList.java,v $
//Revision 1.6  2009/05/11 15:43:00  wendy
//Support dereference for memory release
//
//Revision 1.5  2009/03/10 13:26:32  wendy
//Support view in ODS
//
//Revision 1.4  2008/11/11 23:01:02  wendy
//Add 'with ur' to qry
//
//Revision 1.3  2008/08/08 21:43:12  wendy
//CQ00006067-WI : LA CTO - More support for QueryAction
//
//Revision 1.2  2008/08/01 12:28:19  wendy
//Change action type to View
//
//Revision 1.1  2008/07/31 18:59:06  wendy
//CQ00006067-WI : LA CTO - Added support for QueryAction
//

public class QueryList extends EANMetaEntity implements EANTableWrapper
{
	final static long serialVersionUID = 1L;
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2008  All Rights Reserved.";
	/** cvs revision number */
	public static final String VERSION = "$Revision: 1.6 $";

	private QueryActionItem m_qai = null;
	private QueryGroup queryGroup = null;

	/********************
	 * Constructor for the QueryList object
	 * @param _db
	 * @param _prof
	 * @param _qai
	 * @throws SQLException
	 * @throws MiddlewareRequestException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws RemoteException 
	 */
	public QueryList(Database _db, Profile _prof, QueryActionItem _qai) 
	throws SQLException, MiddlewareRequestException, MiddlewareException, 
	RemoteException, MiddlewareShutdownInProgressException
	{
		super(null, _prof, _qai.getKey());
		String strTraceBase = "QueryList.init";
		ResultSet rs=null;
		PreparedStatement ps=null;

		try {
			EntityItem[] aei = _qai.getEntityItemArray();
			_db.test(aei != null, strTraceBase+" EntityItem array is null");
			_db.test(aei.length > 0, strTraceBase+" EntityItem array is zero");

			m_qai = _qai;
			putLongDescription("View "+_qai.getViewName());

			// select * from price.juicatlseo where entityid in (852582)
			StringBuffer querySb = new StringBuffer("select * from "+_qai.getViewName()+" where entityid in (");
			// build up a new prepared statement, do one call with all ids
			for (int i=0; i<aei.length; i++){
				querySb.append(aei[i].getEntityID());
				if ((i+1)<aei.length){
					querySb.append(", ");
				}
			}
			querySb.append(") with ur");

			Connection con = null;
			if (_qai.usesPDH()){
				con = _db.getPDHConnection();
			}else {
				con = _db.getODSConnection();				
			}
				
			ps = con.prepareStatement(querySb.toString());
			_db.debug(D.EBUG_DETAIL, strTraceBase + " executing SQL:" + querySb);

			rs = ps.executeQuery();

			// How many cols in output ResultSet?
			ResultSetMetaData rsmd = rs.getMetaData();
			int iCols = rsmd.getColumnCount();
			int entityIDCol = 1;
			// get the column names for display
			for (int i=1; i<=iCols; i++){
				try {
					EANMetaAttribute meta1 = new MetaTextAttribute(null, getProfile(), 
							rsmd.getColumnLabel(i),	"T","R");
					meta1.putShortDescription(rsmd.getColumnLabel(i));
					meta1.putLongDescription(rsmd.getColumnLabel(i));					
					if ("entityid".equalsIgnoreCase(rsmd.getColumnLabel(i))){
						entityIDCol = i;
					}
					this.getMeta().put(meta1);
				} catch (Exception x) {
					x.printStackTrace();
				}
			}

			int rowCount=0;
			while (rs.next()) {
				Hashtable colTbl = new Hashtable();
				String rowkey = "";
				// Process all columns
				for (int iCol = 1; iCol <= iCols; iCol++) {
					String strReturnData = rs.getString(iCol);
					if (strReturnData != null) {
						strReturnData = Unicode.rtrim(strReturnData);
						MetaTextAttribute ml1 = (MetaTextAttribute)getMeta().getAt(iCol-1);					
						colTbl.put(ml1.getKey(),strReturnData);
					}

					if (iCol==entityIDCol){
						rowkey = strReturnData+rowCount;
						rowCount++;
					}
				}

				this.putData(new QueryItem(null, getProfile(), rowkey, colTbl));
			}
			// get the group for column order			
			queryGroup = new QueryGroup(_db,getProfile(), m_qai.getViewName(), getMeta());
			this.resetMeta();
		}
		finally{
			if (ps!=null){
				ps.close();
			}
			if (rs!=null){
				rs.close();
			}
			_db.commit();
			_db.freeStatement();
			_db.isPending();
		}
	}

	/**
	 * release references
	 */
	public void dereference() {
		for (int i = 0; i < getDataCount(); i++) {
			QueryItem qi = (QueryItem)getData().getAt(i);
			qi.dereference();
		}
		if (m_qai != null){
			m_qai.dereference();
			m_qai = null;
		}
		
		if (queryGroup!= null){
			queryGroup.dereference();
			queryGroup = null;
		}
		super.dereference();
	}

	/******************
	 * @see COM.ibm.eannounce.objects.EANMetaEntity#dump(boolean)
	 */
	public String dump(boolean _bBrief) {
		StringBuffer strbResult = new StringBuffer();
		strbResult.append("QueryList: " + getKey());
		if (!_bBrief) {
			strbResult.append(" QueryItems ");
			for (int i = 0; i < getDataCount(); i++) {
				QueryItem qi = (QueryItem)getData().getAt(i);
				strbResult.append(qi.dump(_bBrief));
			}
		}

		return strbResult.toString();
	}

	/***********************
	 * Returns a basic table adaptor for client rendering of QueryLists
	 *
	 *@return    The table
	 */
	public RowSelectableTable getTable() {
		return new RowSelectableTable(this, getLongDescription());
	}

	/******************
	 *  Gets the parentActionItem
	 *
	 *@return    The parentActionItem value
	 */
	public QueryActionItem getParentActionItem() {
		return m_qai;
	}

	/*******************
	 *  Gets the column information
	 *
	 *@return    The columnList value
	 */
	public EANList getColumnList() {
		return queryGroup.getColumnList();
	}

	/***************
	 *  Gets the rowList
	 *
	 *@return    The rowList value
	 */
	public EANList getRowList() {
		return getData();
	}

	// All of these must be defined for EANTableWrapper but are not used
	//-----------------------------------------------------------------------------
	public boolean canCreate() {
		return false;
	}
	public boolean canEdit() {
		return false;
	}
	public Object getMatrixValue(String _str) {
		return null;
	}
	public boolean addRow() {
		return false;
	}
	public boolean addRow(String _strKey) {
		return false;
	}
	public void removeRow(String _strKey) { }
	public boolean hasChanges() {
		return false;
	}
	public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
	}
	public void putMatrixValue(String _str, Object _o) { }
	public boolean isMatrixEditable(String _str) {
		return false;
	}
	public void rollbackMatrix() { }
	public void addColumn(EANFoundation _ean) { }
	public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) {
		return null;
	}
	public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey, EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException {
		return null;
	}
	public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey)
	throws EANBusinessRuleException {
			return false;
	}
	public EntityList create(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) {
		return null;
	}
	public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException,RemoteException {
		return null;
	}
	public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey, StringBuffer errSb)
	throws SQLException, MiddlewareException, MiddlewareRequestException,
	MiddlewareShutdownInProgressException,RemoteException{
		return null;
	}
	public boolean duplicate(String _strKey, int _iDup) {
		return false;
	}
	public Object linkAndRefresh(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException, RemoteException {
		return null;
	}
	public boolean isDynaTable() {
		return false;
	}
	public WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {
		return null;
	}
	public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException,RemoteException {
		return null;
	}
}

