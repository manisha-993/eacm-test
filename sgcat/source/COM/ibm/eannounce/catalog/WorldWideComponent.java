package COM.ibm.eannounce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

public class WorldWideComponent extends CatItem implements Databaseable, XMLable {
	public static final int COMPGRP_REFERENCE = 0;

	private String m_strEnterprise = null;
	private int m_iCompEntityID = 0;
	private String m_strValFrom = null;
	private String m_strValTo = null;

	private SyncMapCollection m_smc = null;
	private WorldWideProductCollection m_wwpc = null;
	private ComponentGroupCollection m_cgc = null;
	private HashMap m_AttCollection = new HashMap();

	public WorldWideComponent(WorldWideComponentId _wwcmpid) {
		super(_wwcmpid);
	}

	public WorldWideComponent(WorldWideComponentId _wwcmpid, Catalog _cat) {
		super(_wwcmpid);
		get(_cat);
	}

	public String dump(boolean _brief) {
		StringBuffer sb = new StringBuffer();
		sb.append(getWorldWideComponentId().dump(_brief) + "\n");
		sb.append("Active: " + isActive() + "\n");
		sb.append("Val From: " + m_strValFrom + "\n");
		sb.append("Val To: " + m_strValTo + "\n");
		return sb.toString();
	}

	public static void main(String[] args) {
	}

	public WorldWideComponentId getWorldWideComponentId() {
		return (WorldWideComponentId) getId();
	}

	public void get(Catalog _cat) {
		String strTraceBase = "WorldWideComponent get method";
		ResultSet rs = null;
		Database db = _cat.getCatalogDatabase();
		ReturnStatus returnStatus = new ReturnStatus(-1);
		ReturnDataResultSet rdrs;
		GeneralAreaMapItem gami = getGami();

		String strEnterprise = gami.getEnterprise();
		int iNLSID = gami.getNLSID();

		WorldWideComponentId wwcmpid = (WorldWideComponentId) this.getId();
		WorldWideComponentCollectionId wwcmpcid = wwcmpid.getWorldWideComponentCollectionId();

		if (wwcmpcid.isByInterval() && wwcmpcid.isFromPDH()) {
			System.out.println(strTraceBase + " 03.");
		} else if (wwcmpcid.isFromCAT()) {

			try {
				rs = db.callGBL4050(returnStatus, strEnterprise, getWWEntityType(), getWWEntityID(), getCompEntityType(), getCompEntityID());
				rdrs = new ReturnDataResultSet(rs);

				rs.close();
				db.commit();
				db.freeStatement();
				db.isPending();

				for (int ii = 0; ii < rdrs.size(); ii++) {
					setValFrom(rdrs.getColumn(ii, 0).trim());
					setValTo(rdrs.getColumn(ii, 1).trim());
					setActive(rdrs.getColumnInt(ii, 2) == 1);
				}

			} catch (SQLException _ex) {
				_ex.printStackTrace();
			} catch (MiddlewareException e) {
				e.printStackTrace();

			} finally {
				try {
					rs.close();
					db.commit();
					db.freeStatement();
					db.isPending();
				} catch (SQLException _ex) {
					_ex.printStackTrace();
				}
			}
		}
	}

	public final String toString() {
		return "";
	}


	/**
	 * @return
	 */
	public String getValFrom() {
		return m_strValFrom;
	}

	/**
	 * @return
	 */
	public String getValTo() {
		return m_strValTo;
	}

	/**
	 * @return
	 */
	public int getWWEntityID() {
		return getWorldWideComponentId().getWWEntityID();
	}

	/**
	 * @return
	 */
	public String getWWEntityType() {
		return getWorldWideComponentId().getWWEntityType();
	}

	/**
	* @return
	*/
	public String getCompEntityType() {
		return getWorldWideComponentId().getCmpntEntityType();
	}

	/**
	 * @param _s
	 */
	public void setValFrom(String _s) {
		m_strValFrom = _s;
	}

	/**
	 * @param _s
	 */
	public void setValTo(String _s) {
		m_strValTo = _s;
	}


	/**
	 * @return
	 */
	public int getCompEntityID() {
		return getWorldWideComponentId().getCmpntEntityID();
	}

	/**
	 * @return
	 */
	public String getEnterprise() {
		return m_strEnterprise;
	}


	/**
	 * @param string
	 */
	public void setEnterprise(String _s) {
		m_strEnterprise = _s;
	}

	public GeneralAreaMapItem getGami() {
		return getWorldWideComponentId().getGami();
	}


	public void put(Catalog _cat, boolean _bcommit) {
		if (Catalog.isDryRun()) {
			return;
		}
		Database db = _cat.getCatalogDatabase();
		ReturnStatus rets = new ReturnStatus(-1);
		WorldWideComponentId wwpid = this.getWorldWideComponentId();
		GeneralAreaMapItem gami = wwpid.getGami();

		String strEnterprise = gami.getEnterprise();
		String strWWEntityType = wwpid.getWWEntityType();
		int iWWEntityID = wwpid.getWWEntityID();
		String strCompEType = wwpid.getCmpntEntityType();
		int iWWCompID = wwpid.getCmpntEntityID();

		try {
			db.callGBL8400(rets, strEnterprise, strWWEntityType, iWWEntityID, strCompEType, iWWCompID, (this.isActive() ? 1 : 0));

			if (_bcommit) {
				db.commit();
			}
			db.freeStatement();
			db.isPending();

		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deactivate(Catalog _cat, boolean _bcommit) {
		if (Catalog.isDryRun()) {
			return;
		}
		Database db = _cat.getCatalogDatabase();
		ReturnStatus rets = new ReturnStatus(-1);
		WorldWideComponentId wwpid = this.getWorldWideComponentId();
		GeneralAreaMapItem gami = wwpid.getGami();

		String strEnterprise = gami.getEnterprise();
		String strWWEntityType = wwpid.getWWEntityType();
		int iWWEntityID = wwpid.getWWEntityID();
		String strCompEType = wwpid.getCmpntEntityType();
		int iWWCompID = wwpid.getCmpntEntityID();

		try {
			db.callGBL8400(rets, strEnterprise, strWWEntityType, iWWEntityID, strCompEType, iWWCompID,  0);

			if (_bcommit) {
				db.commit();
			}
			db.freeStatement();
			db.isPending();

		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void merge(CatItem _ci) {
		// TODO Auto-generated method stub

	}

	public void setAttribute(String _strTag, Object _oAtt) {
		return;
	}

	public Object getAttribute(String _strTag) {
		if (m_AttCollection.containsKey(_strTag)) {
			return m_AttCollection.get(_strTag);
		} else {
			System.out.println("attribute not found for " + _strTag);
		}
		return null;
	}

	/**
	 * get attribute keys
	 * 20050808
	 * @return keys[]
	 * @author tony
	 */
	public String[] getAttributeKeys() {
		if (m_AttCollection != null) {
			Set keys = m_AttCollection.keySet();
			if (keys != null) {
				return (String[]) keys.toArray(new String[m_AttCollection.size()]);
			}
		}
		return null;
	}

	public void setWorldWideProductCollection(WorldWideProductCollection collection) {
		m_wwpc = collection;
	}

	public WorldWideProductCollection getWorldWideProductCollection() {
		return m_wwpc;
	}
	public SyncMapCollection getSmc() {
		return m_smc;
	}

	/**
	 * setSmc
	 * @param collection
	 */
	public void setSmc(SyncMapCollection collection) {
		m_smc = collection;
	}

	public final boolean hasSyncMapCollection() {
		return this.getSmc() != null;
	}

	public void setComponentGroupCollection(ComponentGroupCollection collection) {
		m_cgc = collection;
	}

	public ComponentGroupCollection getComponentGroupCollection() {
		return m_cgc;
	}

	public final void getReferences(Catalog _cat, int _icase) {
		String strTraceBase = "WorldWideComponent getReferences method ";
		System.out.println(strTraceBase + " 00");
		WorldWideComponentCollectionId wwccid = this.getWorldWideComponentId().getWorldWideComponentCollectionId();

		switch (_icase) {

		case COMPGRP_REFERENCE :

			ComponentGroupCollectionId cgcid = new ComponentGroupCollectionId(this.getWorldWideComponentId(), wwccid.getInterval(), wwccid.getSource(), wwccid.getType());
			setComponentGroupCollection(new ComponentGroupCollection(cgcid));
			//
			// Lets share the SMC stuff
			//
			if (this.hasSyncMapCollection()) {
				D.ebug(this, D.EBUG_DETAIL, "getReferences(COMPGRP_REFERENCE) - setting COMPGRP 's SMC");
				getComponentGroupCollection().setSmc(this.getSmc());
			}

			D.ebug(this, D.EBUG_DETAIL, "getReferences(COMPGRP_REFERENCE) - lets go stub out the COMPGRP" );
			getComponentGroupCollection().get(_cat);
			setDeep(true);

			break;

		default :

			break;
		}
	}
}

/*
$Log: WorldWideComponent.java,v $
Revision 1.2  2011/05/05 11:21:33  wendy
src from IBMCHINA

Revision 1.1.1.1  2007/06/05 02:09:31  jingb
no message

Revision 1.1.1.1  2006/03/30 17:36:31  gregg
Moving catalog module from middleware to
its own module.

Revision 1.24  2005/11/29 19:19:48  dave
formatting

Revision 1.23  2005/11/21 23:46:14  joan
fixes

Revision 1.22  2005/10/05 20:59:29  joan
fixes

Revision 1.21  2005/10/05 19:30:26  joan
fixes

Revision 1.20  2005/10/04 20:35:30  joan
fixes

Revision 1.19  2005/10/03 22:35:19  joan
fixes

Revision 1.18  2005/10/03 22:08:33  joan
work on component

Revision 1.17  2005/08/30 22:04:42  dave
ok.. we try this

Revision 1.16  2005/08/30 21:00:54  joan
remove comments made by cvs to compile

Revision 1.15  2005/08/30 17:39:14  dave
new cat comments

<<<<<<< WorldWideComponent.java
Revision 1.12  2005/07/12 23:03:09  bala
sync up

=======
Revision 1.14  2005/08/08 20:47:16  tony
Added getAttribute logic

Revision 1.13  2005/08/08 18:54:24  tony
get attribute keys
20050808

Revision 1.12  2005/07/12 23:03:09  bala
sync up

>>>>>>> 1.14
Revision 1.11  2005/06/23 01:45:32  bala
bring up to speed

Revision 1.10  2005/06/13 04:35:34  dave
! needs to be not !

Revision 1.9  2005/06/13 04:02:06  dave
new dryrun feature to keep things from being updated

Revision 1.8  2005/06/07 04:34:51  dave
working on commit control

Revision 1.7  2005/06/02 04:49:55  dave
more clean up

Revision 1.6  2005/06/01 20:36:41  gregg
get/setAttribute

Revision 1.5  2005/05/27 00:55:17  dave
adding the merge method.

Revision 1.4  2005/05/26 07:20:10  dave
new SP and introduction of the Catalog Object

Revision 1.3  2005/05/26 00:11:54  dave
missed saves in jtest

Revision 1.2  2005/05/25 21:49:00  bala
fix typo

Revision 1.1  2005/05/25 21:06:57  bala
first cut

*/
