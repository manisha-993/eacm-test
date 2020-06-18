//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SearchBinder.java,v $
// Revision 1.44  2014/04/17 18:24:00  wendy
// RCQ 216919 EACM BH - Add New Attributes to Search
//
// Revision 1.43  2013/10/24 17:32:24  wendy
// provide access to EntityGroups
//
// Revision 1.42  2007/07/17 17:29:15  wendy
// Prevent null ptr if m_ei is not set in toString()
//
// Revision 1.41  2006/05/12 17:19:52  tony
// fix relator search
//
// Revision 1.40  2006/05/09 16:09:51  tony
// cr 0428066447
//
// Revision 1.39  2006/02/20 21:39:48  joan
// clean up System.out.println
//
// Revision 1.38  2005/11/04 14:52:11  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.37  2005/01/10 21:47:50  joan
// work on multiple edit
//
// Revision 1.36  2005/01/05 19:24:09  joan
// add new method
//
// Revision 1.35  2004/11/30 17:08:32  gregg
// getRowList for m_eg
//
// Revision 1.34  2004/11/24 17:44:39  gregg
// remove debugs
//
// Revision 1.33  2004/11/23 21:09:16  gregg
// more ColOrder on SearchActions
//
// Revision 1.32  2004/11/23 18:20:20  gregg
// more for Column Orders on SearchActionItems
//
// Revision 1.31  2004/11/22 23:01:05  gregg
// MetaColumnOrderGroup on SearchBinder
//
// Revision 1.30  2004/11/22 22:57:28  gregg
// hasMetaColumnOrderGroup()
//
// Revision 1.29  2004/11/22 22:32:45  gregg
// set/getMetaColumnOrderGroup
//
// Revision 1.28  2004/08/17 22:43:31  joan
// work on search
//
// Revision 1.27  2004/06/08 17:51:32  joan
// throw exception
//
// Revision 1.26  2004/06/08 17:28:34  joan
// add method
//
// Revision 1.25  2004/04/09 19:37:21  joan
// add duplicate method
//
// Revision 1.24  2003/08/28 16:28:09  joan
// adjust link method to have link option
//
// Revision 1.23  2003/06/25 18:44:02  joan
// move changes from v111
//
// Revision 1.22  2003/04/10 20:42:28  gregg
// applyColumnOrders in getRowList
//
// Revision 1.21  2003/01/21 00:20:36  joan
// adjust link method to test VE lock
//
// Revision 1.20  2003/01/14 22:05:06  joan
// adjust removeLink method
//
// Revision 1.19  2003/01/08 21:44:06  joan
// add getWhereUsedList
//
// Revision 1.18  2002/10/30 22:57:18  dave
// syntax on import
//
// Revision 1.17  2002/10/30 22:39:14  dave
// more cleanup
//
// Revision 1.16  2002/10/30 22:36:20  dave
// clean up
//
// Revision 1.15  2002/10/30 22:02:34  dave
// added exception throwing to commit
//
// Revision 1.14  2002/10/29 00:02:56  dave
// backing out row commit for 1.1
//
// Revision 1.13  2002/10/28 23:49:15  dave
// attempting the first commit with a row index
//
// Revision 1.12  2002/10/28 20:39:44  dave
// Feedback 22529.  Changed column title of searchBinder
// and tried to remove the asterek from the get when
// we are in DynaTable mode
//
// Revision 1.11  2002/10/18 20:18:54  joan
// add isMatrixEditable method
//
// Revision 1.10  2002/10/09 21:32:58  dave
// added isDynaTable to EANTableWrapper interface
//
// Revision 1.9  2002/10/09 20:26:38  dave
// making sure we can edit things in an EntityGroup when used
// as part of a SearchActionItem
//
// Revision 1.8  2002/10/09 19:00:24  dave
// making canEdit true in all cases
//
// Revision 1.7  2002/10/09 18:15:02  dave
// Syntax fix
//
// Revision 1.6  2002/10/09 18:13:51  dave
// more trace on SearchBinder
//
// Revision 1.5  2002/10/09 18:03:20  dave
// more tracing
//
// Revision 1.4  2002/10/09 17:44:16  dave
// debug/display statements
//
// Revision 1.3  2002/10/09 15:33:26  dave
// DynaSearch II
//
// Revision 1.2  2002/10/07 17:41:39  joan
// add getLockGroup method
//
// Revision 1.1  2002/09/27 21:40:03  dave
// Finishing off dyna search user stub
//
//

package COM.ibm.eannounce.objects;

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



/*
 * Houses The search Attributes as selected by the user
 */
public class SearchBinder extends EANMetaEntity implements EANTableWrapper {

	/**
	 * @serial
	 */
	static final long serialVersionUID = 1L;

	private EntityGroup m_eg = null;
	private EntityItem m_ei = null;
	private EntityGroup m_eg1 = null;
	private EntityGroup m_eg2 = null;
	private EntityItem m_ei1 = null;
	private EntityItem m_ei2 = null;
	private MetaColumnOrderGroup m_mcog = null;

	/**
	 * Main method which performs a simple test of this class
	 */
	public static void main(String arg[]) {
	}

	/*
	 * Version info
	 */
	public String getVersion() {
		return "$Id: SearchBinder.java,v 1.44 2014/04/17 18:24:00 wendy Exp $";
	}

	public SearchBinder(EntityGroup _eg) throws MiddlewareRequestException {
		super(null, _eg.getProfile(), _eg.getKey());
		m_eg = _eg;
		m_eg.setUsedInSearch(true);
		m_ei = _eg.getEntityItem(0);
		//D.ebug(D.EBUG_SPEW,"GAB new SearchBinder for:" +  m_eg.getEntityType());
	}


	public SearchBinder(EntityGroup _eg1, EntityGroup _eg2) throws MiddlewareRequestException {
		super(null, _eg1.getProfile(), _eg1.getKey() + _eg2.getKey());
		m_eg1 = _eg1;
		m_eg1.setUsedInSearch(true);
		m_ei1 = _eg1.getEntityItem(0);
		m_eg2 = _eg2;
		m_eg2.setUsedInSearch(true);
		m_ei2 = _eg2.getEntityItem(0);
		//D.ebug(D.EBUG_SPEW,"GAB new SearchBinder for:" +  m_eg1.getEntityType() + "," + m_eg2.getEntityType());

	}
	/**
	 * RCQ216919
	 * @param _eg
	 * @param _eg1
	 * @param _eg2
	 * @throws MiddlewareRequestException
	 */
	public SearchBinder(EntityGroup _eg, EntityGroup _eg1, EntityGroup _eg2) throws MiddlewareRequestException {
		super(null, _eg1.getProfile(), _eg1.getKey() + _eg.getKey() +_eg2.getKey());
		m_eg = _eg;
		m_eg.setUsedInSearch(true);
		m_ei = _eg.getEntityItem(0);
		m_eg1 = _eg1;
		m_eg1.setUsedInSearch(true);
		m_ei1 = _eg1.getEntityItem(0);
		m_eg2 = _eg2;
		m_eg2.setUsedInSearch(true);
		m_ei2 = _eg2.getEntityItem(0);
	}

	protected void setMetaColumnOrderGroup(MetaColumnOrderGroup _mcog) {
		m_mcog = _mcog;
	}

	protected MetaColumnOrderGroup getMetaColumnOrderGroup() {
		return m_mcog;
	}

	protected boolean hasMetaColumnOrderGroup() {
		return (m_mcog != null);
	}

	/**
	 * provide access to entitygroups
	 * @return
	 */
	protected EntityGroup getEntityGroup(){
		return m_eg;
	}
	protected EntityGroup getEntityGroup1(){
		return m_eg1;
	}
	protected EntityGroup getEntityGroup2(){
		return m_eg2;
	}

	public String dump(boolean _bBrief) {

		StringBuffer strbResult = new StringBuffer();
		if (_bBrief) {
			strbResult.append("SearchBinder:" + getKey());
		} else {
			strbResult.append("SearchBinder:" + getKey());
			strbResult.append("\nEntityGroup:" + m_eg.dump(_bBrief));
			if(m_eg1!=null){
				strbResult.append("\nEntityGroup1:" + m_eg1.dump(_bBrief));
			}
			if(m_eg2!=null){
				strbResult.append("\nEntityGroup2:" + m_eg2.dump(_bBrief));
			}
		}

		return strbResult.toString();

	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (m_ei!=null){
			sb.append(m_ei.toString());
		}
		if (m_ei1!=null){
			if(sb.length()>0){
				sb.append(" ");
			}
			sb.append(m_ei1.toString());
		}
		if (m_ei2!=null){
			if(sb.length()>0){
				sb.append(" ");
			}
			sb.append(m_ei2.toString());
		}

		return sb.toString();
	}

	//
	// This is the table wrapper stuff to Render its Action Groups
	//

	/*
	 * return the Row Information Here
	 * This is simply the entityItem
	 */
	public EANList getRowList() {

		resetData();
		EANList el = new EANList();
		if (m_eg != null) {
			String reltag = ":C"; // RCQ216919
			if(m_eg.isRelator()){
				reltag = ":R"; 
			}

			for (int ii = 0 ;ii < m_eg.getMetaAttributeCount();ii++) {
				EANMetaAttribute ma = m_eg.getMetaAttribute(ii);
				try {
					String strKey = m_eg.getEntityType() + ":" + ma.getKey() + reltag;
					D.ebug(D.EBUG_SPEW,"SearchBinder getRowList m_eg strKey: " + strKey);
					/*
 CR 0428066447
 adjust logic here to verify the visibility
 add to existing if statement

 if it is not visible here then prefill the attribute, if applicable.
													//cr0428066447
					 */
					//cr0428066447                    if (ma.isSearchable()) {
					//cr0428066447	                    putData(new Implicator(m_ei.getEANObject(strKey), null, strKey));
					//cr0428066447                    }

					if (ma.isSearchable()) {											//cr0428066447
						EANFoundation eanF = m_ei.getEANObject(strKey);				//cr0428066447
						String sKey = getParentActionKey();							//cr0428066447
						if (sKey != null && ma.isSearchHidden(sKey)) {					//cr0428066447
							String sPrefill = ma.getSearchPrefill(sKey);				//cr0428066447
							if (sPrefill != null) {										//cr0428066447
								if (eanF instanceof EANAttribute) {						//cr0428066447
									if (eanF instanceof EANFlagAttribute) {				//cr0428066447
										((EANFlagAttribute)eanF).put(sPrefill,true);	//cr0428066447
									} else {											//cr0428066447
										((EANAttribute)eanF).put(sPrefill);				//cr0428066447
									}													//cr0428066447
								}														//cr0428066447
							}															//cr0428066447
						} else {														//cr0428066447
							putData(new Implicator(eanF,null,strKey));					//cr0428066447
						}																//cr0428066447
					}																	//cr0428066447

				} catch (Exception x) {
					x.printStackTrace();
				}
			}
			// apply MetaColumnOrderGroup if we have one...
			el = getData();
			//MetaColumnOrderGroup mcog = m_eg.getMetaColumnOrderGroup();
			//if(mcog != null) {
			//    el = RowSelectableTable.applyColumnOrders(mcog,el);
			//}
		}

		if (m_eg1 != null && m_eg2 != null) {
			for (int ii = 0 ;ii < m_eg1.getMetaAttributeCount();ii++) {
				EANMetaAttribute ma = m_eg1.getMetaAttribute(ii);
				try {
					String strKey = m_eg1.getEntityType() + ":" + ma.getKey() + ":P";
					D.ebug(D.EBUG_SPEW,"SearchBinder getRowList m_eg1 strKey: " + strKey);
					/*
 CR 0428066447
 adjust logic here to verify the visibility
 add to existing if statement

 if it is not visible here then prefill the attribute, if applicable.
					 */
					//cr0428066447                    if (ma.isSearchable()) {
					//cr0428066447                        putData(new Implicator(m_ei1.getEANObject(strKey), null, strKey));
					//cr0428066447                    }
					if (ma.isSearchable()) {											//cr0428066447
						EANFoundation eanF = m_ei1.getEANObject(strKey);				//cr0428066447
						String sKey = getParentActionKey();							//cr0428066447
						if (sKey != null && ma.isSearchHidden(sKey)) {					//cr0428066447
							String sPrefill = ma.getSearchPrefill(sKey);				//cr0428066447
							if (sPrefill != null) {										//cr0428066447
								if (eanF instanceof EANAttribute) {						//cr0428066447
									if (eanF instanceof EANFlagAttribute) {				//cr0428066447
										((EANFlagAttribute)eanF).put(sPrefill,true);	//cr0428066447
									} else {											//cr0428066447
										((EANAttribute)eanF).put(sPrefill);				//cr0428066447
									}													//cr0428066447
								}														//cr0428066447
							}															//cr0428066447
						} else {														//cr0428066447
							putData(new Implicator(eanF,null,strKey));					//cr0428066447
						}																//cr0428066447
					}																	//cr0428066447
				} catch (Exception x) {
					x.printStackTrace();
				}
			}

			for (int ii = 0 ;ii < m_eg2.getMetaAttributeCount();ii++) {
				EANMetaAttribute ma = m_eg2.getMetaAttribute(ii);
				try {
					String strKey = m_eg2.getEntityType() + ":" + ma.getKey() + ":C";
					D.ebug(D.EBUG_SPEW,"SearchBinder getRowList m_eg2 strKey: " + strKey);
					/*
 CR 0428066447
 adjust logic here to verify the visibility
 add to existing if statement

 if it is not visible here then prefill the attribute, if applicable.
					 */

					//cr0428066447                    if (ma.isSearchable()) {
					//cr0428066447                        putData(new Implicator(m_ei2.getEANObject(strKey), null, strKey));
					//cr0428066447                    }
					if (ma.isSearchable()) {											//cr0428066447
						EANFoundation eanF = m_ei2.getEANObject(strKey);				//cr0428066447
						String sKey = getParentActionKey();							//cr0428066447
						if (sKey != null && ma.isSearchHidden(sKey)) {					//cr0428066447
							String sPrefill = ma.getSearchPrefill(sKey);				//cr0428066447
							if (sPrefill != null) {										//cr0428066447
								if (eanF instanceof EANAttribute) {						//cr0428066447
									if (eanF instanceof EANFlagAttribute) {				//cr0428066447
										((EANFlagAttribute)eanF).put(sPrefill,true);	//cr0428066447
									} else {											//cr0428066447
										((EANAttribute)eanF).put(sPrefill);				//cr0428066447
									}													//cr0428066447
								}														//cr0428066447
							}															//cr0428066447
						} else {														//cr0428066447
							putData(new Implicator(eanF,null,strKey));					//cr0428066447
						}																//cr0428066447
					}																	//cr0428066447
				} catch (Exception x) {
					x.printStackTrace();
				}
			}

			// apply MetaColumnOrderGroup if we have one...
			el = getData();
		}

		if(hasMetaColumnOrderGroup()) {
			if(getMetaColumnOrderGroup().getMetaColumnOrderItemCount()>0) {
				el = RowSelectableTable.applyColumnOrders(getMetaColumnOrderGroup(),el);
			}
			//D.ebug(D.EBUG_SPEW,"GAB 1 : searchBinder.hasMetaColumnOrderGroup?" + hasMetaColumnOrderGroup());
			//for(int i = 0; i < el.size(); i++) {
			//    D.ebug(D.EBUG_SPEW,"1 el[" + i + "]:" + el.getAt(i).getKey());
			//}
		} else if(m_eg != null) {
			MetaColumnOrderGroup mcog = m_eg.getMetaColumnOrderGroup();
			if(mcog != null && mcog.getMetaColumnOrderItemCount()>0) {
				el = RowSelectableTable.applyColumnOrders(mcog,el);
			}
		}

		return el;
	}

	/*
	 *   Return the column information here
	 */
	public EANList getColumnList() {

		resetMeta();
		try {
			MetaLabel ml1 = new MetaLabel(this,getProfile(),EANAttribute.DESCRIPTION,"Description",String.class);
			ml1.putShortDescription("Description");
			MetaLabel ml2 = new MetaLabel(this,getProfile(),EANAttribute.VALUE,"Search Criteria",String.class);
			ml2.putShortDescription("Search Criteria");
			putMeta(ml1);
			putMeta(ml2);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return getMeta();

	}


	protected EANMetaAttribute getMetaAttribute(String _strEntityType, String _strAttributeCode) {
		EANMetaAttribute ema = null;
		if(m_eg != null && _strEntityType.equals(m_eg.getEntityType())) {
			ema = m_eg.getMetaAttribute(_strAttributeCode);
		} else if(m_eg1 != null && _strEntityType.equals(m_eg1.getEntityType())) {
			ema = m_eg1.getMetaAttribute(_strAttributeCode);
		} else if(m_eg2 != null && _strEntityType.equals(m_eg2.getEntityType())) {
			ema = m_eg2.getMetaAttribute(_strAttributeCode);
		}
		return ema;
	}

	protected EANActionItem getParentActionItem() {
		EntityGroup eg = m_eg;
		if(eg == null) {
			eg = m_eg1;
		}
		if(eg == null) {
			eg = m_eg2;
		}
		EntityList elParent = (EntityList)eg.getParent();
		return elParent.getParentActionItem();

	}

	public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
	}

	// Needs to be similar to the one in EntityItem...
	public boolean canEdit() {
		return true;
	}

	// Not needed here
	public boolean canCreate() {
		return false;
	}

	// Not needed here
	public boolean addRow() {
		return false;
	}

	public boolean addRow(String _strKey) {
		return false;
	}

	// Not needed here
	public void removeRow(String _strKey) {
	}

	// Same as Entity Item
	public boolean hasChanges() {
		return m_ei.hasChanges();
	}

	// Not needed here
	public Object getMatrixValue(String _str) {
		return null;
	}

	// Not Needed here
	public void putMatrixValue(String _str, Object _o) {
	}

	public boolean isMatrixEditable(String _str) {
		return false;
	}

	// Not needed here
	public void rollbackMatrix() {
	}

	//Not needed Here
	public void addColumn(EANFoundation _ean) {
	}

	// Not needed here
	public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
		return null;
	}

	// Not Needed here
	public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey) throws EANBusinessRuleException {
		return false;
	}

	// Not Needed Here
	public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey, EntityItem[] _aeiChild, String _strLinkOption)  throws EANBusinessRuleException {
		return null;
	}

	// Not Needed here
	public EntityList create(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
		return null;
	}

	// Not Needed here
	public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException,RemoteException {
		return null;
	}

	public WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
		return null;
	}

	public void rollback(String _str) {
		m_ei.rollback(_str);
	}

	public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
		return null;
	}

	public boolean isDynaTable() {
		return true;
	}
	public boolean duplicate(String _strKey, int _iDup) {
		return false;
	}
	public Object linkAndRefresh(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException, RemoteException {
		return null;
	}

	/*
 cr0428066447
	 */
	 private String getParentActionKey() {
		 EANActionItem ean = getParentActionItem();
		 if (ean != null) {
			 return ean.getKey();
		 }
		 return null;
	 }
}
