//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SearchRequest.java,v $
// Revision 1.10  2008/09/08 17:45:04  wendy
// Cleanup some RSA warnings
//
// Revision 1.9  2005/11/04 15:04:14  tony
// Fixed compile issue
//
// Revision 1.8  2005/11/04 14:52:11  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.7  2005/01/10 21:47:50  joan
// work on multiple edit
//
// Revision 1.6  2005/01/05 19:24:09  joan
// add new method
//
// Revision 1.5  2004/06/08 17:51:32  joan
// throw exception
//
// Revision 1.4  2004/06/08 17:28:34  joan
// add method
//
// Revision 1.3  2004/04/09 19:37:21  joan
// add duplicate method
//
// Revision 1.2  2003/08/28 16:28:09  joan
// adjust link method to have link option
//
// Revision 1.1  2003/08/21 23:51:55  joan
// initial load
//

package COM.ibm.eannounce.objects;


import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.LockException;
import java.sql.SQLException;
import java.rmi.RemoteException;

/**
* This manages SearchTarget information
*/
public class SearchRequest extends EANMetaEntity implements EANTableWrapper{

	/**
	 * Main method which performs a simple test of this class
	 */
	public static void main(String arg[]) {
	}

	public SearchRequest(Profile _prof, String _s1)throws MiddlewareRequestException {
		super(null, _prof, _s1);
	}

	public void putSearchTarget(SearchTarget _st) {
	    putData(_st);
	}

	public SearchTarget getSearchTarget(String _s) {
	    return (SearchTarget)getData(_s);
	}

	public SearchTarget getSearchTarget(int _i) {
	    return (SearchTarget)getData(_i);
	}

	public int getSearchTargetCount() {
	    return getDataCount();
	}

	public EANList getColumnList() {
	    return getMeta();
	}

	public EANList getRowList() {
	    return getData();
	}

	/*
	 * Returns a basic table adaptor for client rendering of SearchTargets
	 */
	public RowSelectableTable getTable() {
	    // Meta for a simple table display
		try {
			putMeta(new MetaLabel(this, getProfile(), SearchTarget.SELECTED_FLAG,"Selected",Boolean.class));
		    putMeta(new MetaLabel(this, getProfile(), SearchTarget.TARGET_DESC,"Target", String.class));
		} catch (Exception x) {
			x.printStackTrace();
		}

	    return new RowSelectableTable(this, getLongDescription());
	}

	public String dump(boolean _bBrief) {
		StringBuffer strbResult = new StringBuffer();
		strbResult.append("SearchRequest: " + getKey());
		strbResult.append(" SearchTarget ");
		for (int i = 0; i < getSearchTargetCount(); i++) {
			SearchTarget st = getSearchTarget(i);
			strbResult.append(st.dump(_bBrief));
		}

		return new String(strbResult);
	}

  /**
  * Return the date/time this class was generated
  * @return the date/time this class was generated
  */
  	public String getVersion() {
  		return new String("$Id: SearchRequest.java,v 1.10 2008/09/08 17:45:04 wendy Exp $");
  	}

	public void commit(Database _db, RemoteDatabaseInterface _rdi) { 	}
	public boolean canEdit() {return true;}
	public boolean canCreate() {return false;}
	public boolean addRow() {return false;}
	public boolean addRow(String _strKey) {return false;}
	public void removeRow(String _strKey) {}
 	public boolean hasChanges() {return false;}
	public void rollbackMatrix() {}
	public void addColumn(EANFoundation _ean) { }
	public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {return null;}
	public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey)	throws EANBusinessRuleException {return false;}
	public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey, EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException {return null;}
	public EntityList create(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {return null;}
  public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException,RemoteException {return null;}

	public WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {return null;}
	public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey)  {return null;}
	public boolean isDynaTable() {	return false;}
	public Object getMatrixValue(String _str) {	return null;}
	public void putMatrixValue(String _str, Object _o) { }
	public boolean isMatrixEditable(String _str) {	return false;}
 	public boolean duplicate(String _strKey, int _iDup) {
 		return false;
 	}
	public Object linkAndRefresh(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException, RemoteException {
		return null;
	}
}
