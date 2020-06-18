//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2013  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.objects;

import java.util.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask;
import COM.ibm.opicmpdh.transactions.OPICMABRGroup;
import COM.ibm.opicmpdh.transactions.OPICMABRItem;

import java.sql.*;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
*  get list of ABRs queued and inprocess
*
*/
//$Log: ABRQueueStatusList.java,v $
//Revision 1.1  2013/09/19 15:06:57  wendy
//add abr queue status
//

public class ABRQueueStatusList implements EANTableWrapper, Serializable
{
	final static long serialVersionUID = 1L;
	private static final String INTERLEAVEDMODE = "N";
	private static final String USERTOKEN="USERTOKEN";
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2013  All Rights Reserved.";
	/** cvs revision number */
	public static final String VERSION = "$Revision: 1.1 $";
	
	private static final String[] COLUMNS = new String[]{
		"ENTITYTYPE","ENTITYID","ABRCODE","ABRDESC",
		"STATUS","ABRVALFROM",USERTOKEN
	};
	private static final Hashtable ABR_COLS_TBL;
	static{
		ABR_COLS_TBL = new Hashtable();
		ABR_COLS_TBL.put("ENTITYTYPE", new Integer(OPICMABRItem.ENTITYTYPE));
		ABR_COLS_TBL.put("ENTITYID", new Integer(OPICMABRItem.ENTITYID));
		ABR_COLS_TBL.put("ABRCODE", new Integer(OPICMABRItem.ABRCODE));
		ABR_COLS_TBL.put("ABRDESC", new Integer(OPICMABRItem.ABRDESC));
		ABR_COLS_TBL.put("STATUS", new Integer(-1));
		ABR_COLS_TBL.put("ABRVALFROM", new Integer(OPICMABRItem.ABRVALFROM));
		ABR_COLS_TBL.put(USERTOKEN, new Integer(OPICMABRItem.OPENID));
	}

	private EANList abrList;
	private EANList columnList;
	private Profile profile;
	private Hashtable userTokTbl;

	/********************
	 * Constructor for the ABRQueueStatusList object
	 * creates an empty table for loading tab in JUI
	 * @param prof
	 * @throws SQLException
	 * @throws MiddlewareRequestException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws RemoteException 
	 */
	public ABRQueueStatusList(Profile prof) throws MiddlewareRequestException
	{
		init(prof);
	}
	/********************
	 * Constructor for the ABRQueueStatusList object
	 * @param db
	 * @param prof
	 * @throws SQLException
	 * @throws MiddlewareRequestException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException 
	 * @throws RemoteException 
	 */
	public ABRQueueStatusList(Database db, Profile prof) 
			throws SQLException, MiddlewareRequestException, MiddlewareException, 
			RemoteException, MiddlewareShutdownInProgressException
	{
		init(prof);

		OPICMABRGroup abrGroup = new OPICMABRGroup(profile.getEnterprise());

		// get all queued abrs
		db.refreshOPICMABRGroup(
				profile,
				abrGroup,
				AbstractTask.ABR_STATUS_QUEUED.getAttributeValue(),
				INTERLEAVEDMODE);

		addABRs(db,abrGroup,AbstractTask.ABR_STATUS_QUEUED.getStatusDescription());

		abrGroup.resetOPICMABRItems();

		//get all inprocess abrs
		db.refreshOPICMABRGroup(
				profile,
				abrGroup,
				AbstractTask.ABR_STATUS_INPROCESS.getAttributeValue(),
				INTERLEAVEDMODE);

		addABRs(db,abrGroup,AbstractTask.ABR_STATUS_INPROCESS.getStatusDescription());
		
		abrGroup.resetOPICMABRItems();
	}
	
	/**
	 * add abrs to the list
	 * @param db
	 * @param abrGroup
	 * @param status
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void addABRs(Database db, OPICMABRGroup abrGroup, String status) throws MiddlewareException, SQLException
	{
		for(int rowCount=0; rowCount<abrGroup.getOPICMABRItemCount();rowCount++) {
			OPICMABRItem abri = abrGroup.getOPICMABRItem(rowCount);
			Hashtable colTbl = new Hashtable();
			String rowkey = abri.getEntityID()+":"+rowCount;

			// Process all columns
			for (int iCol=0;iCol<COLUMNS.length;iCol++){
				String col = COLUMNS[iCol];
				int colid = 0;
				Integer abrcol = (Integer)ABR_COLS_TBL.get(col);
				if(abrcol!=null){
					String strReturnData=null;
					colid=abrcol.intValue();
					if(colid==-1){
						strReturnData = status;
					}else{
						strReturnData = abri.get(colid).toString();
						if(col.equals(USERTOKEN)){
							//find usertoken for openid (opwgid)
							strReturnData = getUserToken(db,strReturnData);
						}
					}

					MetaTextAttribute ml1 = (MetaTextAttribute)columnList.getAt(iCol);	
					colTbl.put(ml1.getKey(),strReturnData);
				}else{
					D.ebug(D.EBUG_ERR, "Invalid ABRQueueStatusList column: "+col);
				}
			}

			abrList.put(new ABRQueueStatusItem(null, profile, rowkey, colTbl));
		}
	}
	
	/**
	 * get the user token for the opwgid in the abr record
	 * @param db
	 * @param opwgid
	 * @return
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private String getUserToken(Database db, String opwgid) throws MiddlewareException, SQLException{
		String strUserTok = (String)userTokTbl.get(opwgid);
		if(strUserTok==null){
			int iOPWGID = Integer.parseInt(opwgid);
			int iTRANID = 0;
			ResultSet rs=null;
			ReturnDataResultSet rdrs;
			try {
				rs = db.callGBL7552(new ReturnStatus(-1), getProfile().getEnterprise(), iOPWGID, iTRANID, getProfile().getReadLanguage().getNLSID());
				rdrs = new ReturnDataResultSet(rs);
			} finally {
				if (rs != null){
					rs.close();
					rs = null;
				}
				db.commit();
				db.freeStatement();
				db.isPending();
			}

			strUserTok = rdrs.getColumn(0, 0);
			//String strRoleDesc = rdrs.getColumn(0, 1);
			//String strChgDesc = rdrs.getColumn(0, 2);

			userTokTbl.put(opwgid, strUserTok);
		}

		return strUserTok;
	}
	
	/**
	 * initial instance variables
	 * @param prof
	 * @throws MiddlewareRequestException
	 */
	private void init(Profile prof) throws MiddlewareRequestException{
		profile = prof;
		createColumnList();
		abrList = new EANList();
		userTokTbl = new Hashtable();
	}

	/**
	 * create the column headers
	 * @throws MiddlewareRequestException
	 */
	private void createColumnList() throws MiddlewareRequestException{
		columnList = new EANList();
		// get the column names for display
		for (int i=0;i<COLUMNS.length;i++){
			String col = COLUMNS[i];
			EANMetaAttribute meta1 = new MetaTextAttribute(null, profile, col,"T","R");
			meta1.putShortDescription(col);
			meta1.putLongDescription(col);					
			columnList.put(meta1);
		}
	}
	/**
	 * release references
	 */
	public void dereference() {
		for (int i = 0; i < columnList.size(); i++) {
			EANMetaAttribute qi = (EANMetaAttribute)columnList.getAt(i);
			qi.dereference();
		}
		columnList.removeAll();
		columnList = null;
		
		for (int i = 0; i < abrList.size(); i++) {
			ABRQueueStatusItem qi = (ABRQueueStatusItem)abrList.getAt(i);
			qi.dereference();
		}
		abrList.removeAll();
		abrList = null;
		
		profile = null;
		
		userTokTbl.clear();
		userTokTbl = null;
	}
	
	public Profile getProfile(){
		return profile;
	}

	/******************
	 * @see COM.ibm.eannounce.objects.EANMetaEntity#dump(boolean)
	 */
	public String dump(boolean bBrief) {
		StringBuffer strbResult = new StringBuffer();
		strbResult.append("ABRQueueStatusList: ");
		if (!bBrief) {
			for (int i = 0; i < abrList.size(); i++) {
				ABRQueueStatusItem asi = (ABRQueueStatusItem)abrList.getAt(i);
				strbResult.append(asi.dump(bBrief)+"\n");
			}
		}

		return strbResult.toString();
	}

	/***********************
	 * Returns a basic table adaptor for client rendering of ABRQueueStatusLists
	 *
	 *@return    The table
	 */
	public RowSelectableTable getTable() {
		return new RowSelectableTable(this, "ABR Queue Status");
	}

	/*******************
	 *  Gets the column information
	 *
	 *@return    The columnList value
	 */
	public EANList getColumnList() {
		return columnList;
	}

	/***************
	 *  Gets the rowList
	 *
	 *@return    The rowList value
	 */
	public EANList getRowList() {
		return abrList;
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

