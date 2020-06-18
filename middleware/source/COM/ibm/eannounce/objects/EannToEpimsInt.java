//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EannToEpimsInt.java,v $
// Revision 1.58  2008/09/08 17:45:31  wendy
// Cleanup RSA warnings
//
// Revision 1.57  2008/03/08 22:15:12  wendy
// Undo RSA changes
//
// Revision 1.56  2008/02/05 21:50:19  yang
// Adding EpimAttrVerf method to verify attrs
//
// Revision 1.55  2008/02/01 22:10:07  wendy
// Cleanup RSA warnings
//
// Revision 1.54  2006/09/19 21:07:51  joan
// changes
//
// Revision 1.53  2006/07/30 23:53:36  joan
// fixes
//
// Revision 1.52  2006/07/25 21:16:34  joan
// fixes
//
// Revision 1.51  2006/07/25 19:55:53  joan
// use gbl6033 for max valfrom
//
// Revision 1.50  2006/07/22 15:02:10  joan
// fixes
//
// Revision 1.49  2006/07/10 00:20:54  joan
// fixes
//
// Revision 1.48  2006/07/09 23:55:30  joan
// changes
//
// Revision 1.47  2006/06/22 23:49:01  joan
// changes
//
// Revision 1.46  2006/06/22 20:54:07  joan
// changes for pulling all nlsid
//
// Revision 1.45  2006/05/03 19:33:58  joan
// changes
//
// Revision 1.44  2006/05/03 17:59:07  joan
// changes
//
// Revision 1.43  2006/05/02 16:30:14  joan
// changes
//
// Revision 1.42  2006/04/13 19:34:40  joan
// fixes
//
// Revision 1.41  2006/03/30 21:32:44  gregg
// putting back "real" classes after bootstrap for sgcat.jar
//
// Revision 1.39  2006/03/22 21:05:19  joan
// adjusting
//
// Revision 1.38  2006/03/15 18:45:46  joan
// fixes
//
// Revision 1.37  2006/02/20 17:41:09  joan
// change System.out.println to D.ebug statements
//
// Revision 1.36  2006/02/13 16:37:59  joan
// fixes
//
// Revision 1.35  2006/02/06 20:14:32  joan
// fixes
//
// Revision 1.34  2006/02/01 00:54:31  joan
// working on Pass4
//
// Revision 1.33  2006/01/30 22:33:07  joan
// try to add Pass=4 to gbl8104
//
// Revision 1.32  2005/11/09 20:28:18  joan
// fixes
//
// Revision 1.31  2005/11/09 18:30:28  joan
// fixes
//
// Revision 1.30  2005/11/03 19:04:00  joan
// fixes
//
// Revision 1.29  2005/11/03 16:42:30  joan
// fixes
//
// Revision 1.28  2005/10/25 18:25:40  joan
// fixes
//
// Revision 1.27  2005/10/25 16:03:58  joan
// fixes
//
// Revision 1.26  2005/10/25 16:03:14  joan
// fixes
//
// Revision 1.25  2005/10/25 16:00:42  joan
// fixes
//
// Revision 1.24  2005/10/25 00:29:32  joan
// fixes
//
// Revision 1.23  2005/10/24 23:56:44  joan
// fixes
//
// Revision 1.22  2005/10/24 23:38:32  joan
// fixes
//
// Revision 1.21  2005/10/24 21:54:44  joan
// fixes
//
// Revision 1.20  2005/10/24 19:34:26  joan
// fixes
//
// Revision 1.19  2005/10/24 19:28:48  joan
// fixes
//
// Revision 1.18  2005/10/24 18:58:23  joan
// fixes
//
// Revision 1.17  2005/10/22 00:21:09  joan
// fixes
//
// Revision 1.16  2005/09/12 18:22:59  joan
// fixes
//
// Revision 1.15  2005/09/09 17:59:32  joan
// fixes
//
// Revision 1.14  2005/08/26 17:20:29  joan
// fixes
//
// Revision 1.13  2005/08/25 17:53:13  joan
// fixes
//
// Revision 1.12  2005/08/25 17:42:04  joan
// fixes
//
// Revision 1.11  2005/08/23 20:28:52  joan
// adjust for MQ
//
// Revision 1.10  2005/08/22 18:13:46  joan
// fixes
//
// Revision 1.9  2005/08/22 15:27:26  joan
// fixes
//
// Revision 1.8  2005/08/19 21:54:34  joan
// add mq
//
// Revision 1.7  2005/08/10 22:37:25  joan
// add method
//
// Revision 1.6  2005/08/09 19:02:08  joan
// fixes
//
// Revision 1.5  2005/08/09 18:56:17  joan
// fixes
//
// Revision 1.4  2005/08/04 23:26:07  joan
// add more methods
//
// Revision 1.3  2005/08/04 14:47:45  joan
// try to create xml file
//
// Revision 1.2  2005/08/01 19:40:45  joan
// add createXML properties
//
// Revision 1.1  2005/07/29 20:47:46  joan
// initial load
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.*;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareException;


/**
 * EannToEpimsInt
 *
 * @author Joan Tran
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EannToEpimsInt {

    /**
     * EannToEpimsInt
     *
     *  @author Joan Tran
     */

	private static PDGUtility m_utility = new PDGUtility();
	private static String ENTITY_LSEO = new String("LSEO");
	private static String ENTITY_LSEOBUNDLE = new String("LSEOBUNDLE");
	private static String ENTITY_WWSEO = new String("WWSEO");
	private static String ENTITY_ANNOUNCEMENT = new String("ANNOUNCEMENT");

	private static String ATT_STATUS = new String("STATUS");
	private static String ATT_ANNSTATUS = new String("ANNSTATUS");
	private static String ATT_SEOID = new String("SEOID");
	private static String ATT_ANNNUMBER= new String("ANNNUMBER");

	private static String VALUE_FINAL = new String("0020");

	private StringBuffer m_sbEPIMS = new StringBuffer();
	private StringBuffer m_sbWWPRT = new StringBuffer();

    public static EntityItem[] getChangedRootEntities(Database _db, Profile _prof, String _strEntityType, String _strVE, String _strChildType, int _iChildID, String _strStartTime, String _strEndTime) throws SQLException, MiddlewareException {
		Vector v = m_utility.getChangedEntities( _db,  _prof, _strEntityType, _strVE, _strStartTime, _strEndTime, 4, 0, _strChildType, _iChildID);
		EANList eiList = new EANList();
		for (int x=0; x < v.size(); x++) {
			String strChanged = (String) v.elementAt(x);
			StringTokenizer st = new StringTokenizer(strChanged, ":");
			//String strGenArea = 
				st.nextToken().trim();
			String strRootType = st.nextToken().trim();
			int iRootId = Integer.parseInt(st.nextToken().trim());
			//String strRootTran = 
				st.nextToken().trim();
            String strChildType = st.nextToken().trim();
            int iChildID = Integer.parseInt(st.nextToken().trim());
            if (strRootType.equals(_strEntityType) && strChildType.equals(_strChildType) && iChildID == _iChildID) {
				eiList.put(new EntityItem(null, _prof, strRootType, iRootId));
			} else if (strRootType.equals(_strEntityType) && strRootType.equals(_strChildType) && iRootId == _iChildID) {
				eiList.put(new EntityItem(null, _prof, strRootType, iRootId));
			}
		}

/*
		Catalog cat = new Catalog(_db, _prof);
		Class mclass = EannToEpimsInt.class;
		CatalogInterval cati = new CatalogInterval(mclass, _strStartTime, _strEndTime);
		SyncMapCollection smc = new SyncMapCollection(cat, _strEntityType, _strVE, cati, 4, _strChildType, _iChildID);
		EANList eiList = new EANList();
        for (int x = 0; x < smc.getCount(); x++) {
            SyncMapItem smi = smc.get(x);
            D.ebug(D.EBUG_SPEW, smi.dump(false));

            String strRootType = smi.getRootType();
            int iRootId = smi.getRootID();
            String strChildType = smi.getChildType();
            int iChildID = smi.getChildID();
            if (strRootType.equals(_strEntityType) && strChildType.equals(_strChildType) && iChildID == _iChildID) {
				eiList.put(new EntityItem(null, _prof, strRootType, iRootId));
			} else if (strRootType.equals(_strEntityType) && strRootType.equals(_strChildType) && iRootId == _iChildID) {
				eiList.put(new EntityItem(null, _prof, strRootType, iRootId));
			}
		}
*/
		EntityItem[] aei = new EntityItem[eiList.size()];
		eiList.copyTo(aei);
		return aei;
	}

	public static AttributeChangeHistoryItem getFirstCurrentFinal(AttributeChangeHistoryGroup _achg, String _strAttrValue) {
		String strTraceBase = "EanntoEpimsInt isFirstFinal method ";

		int iFinal = 0;
		AttributeChangeHistoryItem achiCurrent = null;
		for (int i = 0; i < _achg.getChangeHistoryItemCount(); i++) {
			AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) _achg.getChangeHistoryItem(i);

			D.ebug(D.EBUG_SPEW, strTraceBase + " achi: " + achi.dump(false));
			D.ebug(D.EBUG_SPEW, strTraceBase + achi.getFlagCode());
			if (achi.getFlagCode().equals(_strAttrValue)) {
				iFinal++;
			}
			if (achi.isValid()) {
				achiCurrent = achi;
			}
		}

		if (achiCurrent != null) {
			if (achiCurrent.getFlagCode().equals(_strAttrValue) && iFinal==1) {
				return achiCurrent;
			}
		}
		return null;
	}

	private String getId(EntityItem _ei) {
		if (_ei.getEntityType().equals(ENTITY_LSEO) || _ei.getEntityType().equals(ENTITY_LSEOBUNDLE)) {
			return m_utility.getAttrValue(_ei, ATT_SEOID);
		} else if (_ei.getEntityType().equals(ENTITY_ANNOUNCEMENT)) {
			return m_utility.getAttrValue(_ei, ATT_ANNNUMBER);
		}
		return "";
	}

	public EANAttribute getStatus(EntityItem _ei) {
		if (_ei.getEntityType().equals(ENTITY_LSEO) || _ei.getEntityType().equals(ENTITY_LSEOBUNDLE) || _ei.getEntityType().equals(ENTITY_WWSEO)) {
			return _ei.getAttribute(ATT_STATUS);
		} else if (_ei.getEntityType().equals(ENTITY_ANNOUNCEMENT)) {
			return _ei.getAttribute(ATT_ANNSTATUS);
		}
		return null;
	}

	private void prepareWWPRTMessage(EntityItem _ei, String _strChangeDate, String _strEnterprise, boolean _bChange) {
		m_sbWWPRT.append("<WWPRTNotification>");
		m_sbWWPRT.append("<EntityType>" + _ei.getEntityType() + "</EntityType>");
		m_sbWWPRT.append("<EntityId>" + _ei.getEntityID() + "</EntityId>");
		m_sbWWPRT.append("<Id>" + getId(_ei) + "</Id>");
		m_sbWWPRT.append("<Change>" + (_bChange?"yes":"no") + "</Change>");
		m_sbWWPRT.append("<NotificationTime>" + _strChangeDate + "</NotificationTime>");
		m_sbWWPRT.append("<Enterprise>" + _strEnterprise + "</Enterprise>");
		m_sbWWPRT.append("</WWPRTNotification>");
		m_sbWWPRT.append("\n");
	}

	public void getFirstFinalNotification(Database _db, Profile _prof, String _strEntityType, int _iEntityId, String _strVE) {
		String strTraceBase = "EanntoEpimsInt getFinalNotification method ";
		//StringBuffer sb = new StringBuffer();
		try {
			EntityItem[] aei = {new EntityItem(null, _prof, _strEntityType, _iEntityId)};
			ExtractActionItem xai = new ExtractActionItem(null, _db, _prof, _strVE);
			EntityList el = EntityList.getEntityList(_db, _prof, xai, aei);
			EntityGroup eg = el.getEntityGroup(_strEntityType);
			if (el.getParentEntityGroup().getEntityType().equals(_strEntityType)) {
				eg = el.getParentEntityGroup();
			}

			EntityItem ei = new EntityItem(null, _prof, _strEntityType, _iEntityId);
			ei = eg.getEntityItem(ei.getKey());

			//verify on a specific attribute from an entity
			EpimAttrVerf(ei,"LSEO","ACCTASGNGRP");
			EANAttribute att = getStatus(ei);
			if (att != null) {
				AttributeChangeHistoryGroup achg = new AttributeChangeHistoryGroup(_db, _prof, att);
				AttributeChangeHistoryItem achi = getFirstCurrentFinal(achg, VALUE_FINAL);

				if (achi != null) {
					if (_strEntityType.equals(ENTITY_LSEO)) {
						// for EPIMS

						m_sbEPIMS.append("<LseoFinalNotification>");
						m_sbEPIMS.append("<Enterprise>" + _prof.getEnterprise() + "</Enterprise>");
						m_sbEPIMS.append("<EntityType>" + _strEntityType + "</EntityType>");
						m_sbEPIMS.append("<EntityId>" + _iEntityId + "</EntityId>");
						m_sbEPIMS.append("<LseoId>" + getId(ei) + "</LseoId>");
						m_sbEPIMS.append("<NotificationTime>" + achi.getChangeDate() + "</NotificationTime>");
						m_sbEPIMS.append("</LseoFinalNotification>");
						m_sbEPIMS.append("\n");

					} else if (_strEntityType.equals(ENTITY_LSEOBUNDLE)) {
						// for EPIMS

						m_sbEPIMS.append("<LseoBundleFinalNotification>");
						m_sbEPIMS.append("<Enterprise>" + _prof.getEnterprise() + "</Enterprise>");
						m_sbEPIMS.append("<EntityType>" + _strEntityType + "</EntityType>");
						m_sbEPIMS.append("<EntityId>" + _iEntityId + "</EntityId>");
						m_sbEPIMS.append("<LseoBundleId>" + getId(ei) + "</LseoBundleId>");
						m_sbEPIMS.append("<NotificationTime>" + achi.getChangeDate() + "</NotificationTime>");
						m_sbEPIMS.append("</LseoBundleFinalNotification>");
						m_sbEPIMS.append("\n");
					}

					// for WWPRT

					prepareWWPRTMessage(ei, achi.getChangeDate(), _prof.getEnterprise(), false);

				} else {
					D.ebug(D.EBUG_SPEW,strTraceBase +" achi is null.");
				}
			} else {
				D.ebug(D.EBUG_SPEW,strTraceBase + ATT_STATUS + " is blank.");
			}

		} catch (Exception ex) {
			System.out.println("Exception in " + strTraceBase);
			ex.printStackTrace();
		}
	}

	public void EpimAttrVerf(EntityItem ei, String _Entitytype, String _Attr){
		//verify on a specific attribute from an entity is process by getFirstFinalNotification method
		if (ei.getEntityType().equals(_Entitytype)){
			if (ei.containsAttribute(_Attr)){
				System.out.println("EPIM:"+ _Entitytype + ":"+_Attr.toString()+":" + ei.getEntityType()+ei.getEntityID()+ei.getAttribute("ACCTASGNGRP"));
			} else {
				System.out.println("This" + _Entitytype + " does not have: " + _Attr.toString() + ei.getEntityType()+ei.getEntityID());
			}
		}
	}

	public String[] getEPIMSMessage() {
		if (m_sbEPIMS.toString().length() > 0) {
			StringTokenizer st = new StringTokenizer(m_sbEPIMS.toString(), "\n");
			Vector v = new Vector();
			while (st.hasMoreTokens()) {
				v.addElement(st.nextToken());
			}
			String[] aStr = new String[v.size()];
			v.toArray(aStr);
			return aStr;
			//return transformXML(m_sbEPIMS.toString());
		}

		return null;
	}

	public String[] getWWPRTSMessage() {
		if (m_sbWWPRT.toString().length() > 0) {
			StringTokenizer st = new StringTokenizer(m_sbWWPRT.toString(), "\n");
			Vector v = new Vector();
			while (st.hasMoreTokens()) {
				v.addElement(st.nextToken());
			}
			String[] aStr = new String[v.size()];
			v.toArray(aStr);
			return aStr;
		}

		return null;
	}

	public static ChangeHistoryItem getCurrentChangeItem(ChangeHistoryGroup _chg) {
		//String strLatestChangeDate = "";
		ChangeHistoryItem latestItem = null;

		String[] aSort = new String[_chg.getChangeHistoryItemCount()];
		for (int i=0; i < _chg.getChangeHistoryItemCount(); i++) {
			ChangeHistoryItem chi = (ChangeHistoryItem) _chg.getChangeHistoryItem(i);

			D.ebug(D.EBUG_SPEW, chi.dump(false));
			String strChangeDate = chi.getChangeDate();
			aSort[i] = strChangeDate + ":" + chi.getKey();
		}
	    Arrays.sort(aSort);

	    String s = aSort[aSort.length-1];
	    int iColon = s.indexOf(":");
	    String strKey = s.substring(iColon+1);
	    latestItem = _chg.getChangeHistoryItem(strKey);

		return latestItem;
	}

	public void getChangedInFinalNotification(Database _db, Profile _prof, String _strRootEntityType, String _strEntityType, int _iEntityId, String _strVE, boolean _bGetRoot) {
		String strTraceBase = "EanntoEpimsInt getChangedInFinalNotification method ";
		try {
			D.ebug(D.EBUG_SPEW, strTraceBase + ":" + _strRootEntityType + ":" + _strEntityType + ":" + _iEntityId + ":" + _strVE);
			//DatePackage dpNow = _db.getDates();
			//String m_strTimeStampNow = dpNow.getNow();
			ReturnStatus returnStatus = new ReturnStatus( -1);
			EntityItem ei = new EntityItem(null, _prof, _strEntityType, _iEntityId);

			ResultSet rs = _db.callGBL6033(returnStatus, _prof.getOPWGID(), _prof.getEnterprise(), _strEntityType, _iEntityId);
            ReturnDataResultSet rdrs = new ReturnDataResultSet(rs);
            rs.close();
            rs = null;
            _db.commit();
            _db.freeStatement();
            _db.isPending();

  			String strChangeTime = "";
            for (int ii = 0; ii < rdrs.size(); ii++) {
                strChangeTime = rdrs.getColumnDate(ii, 0);

                _db.debug(D.EBUG_SPEW, "gbl6033:answer:" + strChangeTime);

                break;
            }

            if (strChangeTime == null || strChangeTime.length() <= 0) {
				System.out.println(strTraceBase + " gbl6033 doesn't return max timestamp");
				EntityChangeHistoryGroup echg = new EntityChangeHistoryGroup(_db, _prof, ei);
				EntityChangeHistoryItem echi = (EntityChangeHistoryItem)getCurrentChangeItem(echg);
				strChangeTime = echi.getChangeDate();
			}
			String strStartTime = strChangeTime.substring(0, 11) + "00.00.00.000000";
			String strEndTime = strChangeTime.substring(0, 11) + "23.59.59.999999";

			_prof.setValOn(strChangeTime);
			_prof.setEffOn(strChangeTime);

			EntityItem[] aei = {ei};
			if (_bGetRoot) {
				aei = getChangedRootEntities(_db, _prof, _strRootEntityType, _strVE, _strEntityType, _iEntityId, strStartTime, strEndTime);
			}

			if (aei == null || aei.length <= 0) {
				return;
			}

			ExtractActionItem xai = new ExtractActionItem(null, _db, _prof, _strVE);
			EntityList el = EntityList.getEntityList(_db, _prof, xai, aei);
			EntityGroup eg = el.getEntityGroup(_strRootEntityType);
			if (el.getParentEntityGroup().getEntityType().equals(_strRootEntityType)) {
				eg = el.getParentEntityGroup();
			}

			//boolean bFirst = true;
			EANList eiList = new EANList();

			for (int i=0; i < aei.length; i++) {
				EntityItem eiRoot = aei[i];
				D.ebug(D.EBUG_SPEW, strTraceBase + "checking eiRoot: " + eiRoot.getKey());
				if (eiList.get(eiRoot) == null) {
					eiList.put(eiRoot);
					eiRoot = eg.getEntityItem(eiRoot.getKey());
					EANAttribute att = getStatus(eiRoot);
					String strValue = "";
					if (att != null) {
						strValue = m_utility.getAttrValue(eiRoot, att.getKey());
					}

					if (strValue.equals(VALUE_FINAL)) {
						//dpNow = _db.getDates();
						//m_strTimeStampNow = dpNow.getNow();

						D.ebug(D.EBUG_SPEW, strTraceBase + " eiRoot: " + eiRoot.getKey() + " strValue: " + strValue);

						String strNotifyTime = strChangeTime;
						if (eiRoot.getEntityType().equals(ENTITY_LSEO)) {
							m_sbEPIMS.append("<LseoChangedNotification>");
							m_sbEPIMS.append("<Enterprise>" + _prof.getEnterprise() + "</Enterprise>");
							m_sbEPIMS.append("<EntityType>" + eiRoot.getEntityType() + "</EntityType>");
							m_sbEPIMS.append("<EntityId>" + eiRoot.getEntityID() + "</EntityId>");
							m_sbEPIMS.append("<LseoId>" + m_utility.getAttrValue(eiRoot, ATT_SEOID) + "</LseoId>");
							m_sbEPIMS.append("<NotificationTime>" + strNotifyTime + "</NotificationTime>");
							m_sbEPIMS.append("</LseoChangedNotification>");
							m_sbEPIMS.append("\n");
						} else if (eiRoot.getEntityType().equals(ENTITY_LSEOBUNDLE)) {
							m_sbEPIMS.append("<LseoBundleChangedNotification>");
							m_sbEPIMS.append("<Enterprise>" + _prof.getEnterprise() + "</Enterprise>");
							m_sbEPIMS.append("<EntityType>" + eiRoot.getEntityType() + "</EntityType>");
							m_sbEPIMS.append("<EntityId>" + eiRoot.getEntityID() + "</EntityId>");
							m_sbEPIMS.append("<LseoBundleId>" + m_utility.getAttrValue(eiRoot, ATT_SEOID) + "</LseoBundleId>");
							m_sbEPIMS.append("<NotificationTime>" + strNotifyTime + "</NotificationTime>");
							m_sbEPIMS.append("</LseoBundleChangedNotification>");
							m_sbEPIMS.append("\n");
						} else if (eiRoot.getEntityType().equals(ENTITY_WWSEO)) {
							m_sbEPIMS.append("<WwseoChangedNotification>");
							m_sbEPIMS.append("<Enterprise>" + _prof.getEnterprise() + "</Enterprise>");
							m_sbEPIMS.append("<EntityType>" + eiRoot.getEntityType() + "</EntityType>");
							m_sbEPIMS.append("<EntityId>" + eiRoot.getEntityID() + "</EntityId>");
							m_sbEPIMS.append("<WwseoId>" + m_utility.getAttrValue(eiRoot, ATT_SEOID) + "</WwseoId>");
							m_sbEPIMS.append("<NotificationTime>" + strNotifyTime + "</NotificationTime>");
							m_sbEPIMS.append("</WwseoChangedNotification>");
							m_sbEPIMS.append("\n");
						}

						prepareWWPRTMessage(eiRoot, strNotifyTime, _prof.getEnterprise(), true);
					}
				}
			}

		} catch (Exception ex) {
			System.out.println("Exception in " + strTraceBase);
			ex.printStackTrace();
		}
	}

	public void getControlTableChangedNotification(Database _db, Profile _prof, String _strEntityType, int _iEntityId) {
		String strTraceBase = "EanntoEpimsInt getControlTableChangedNotification method ";
		//StringBuffer sb = new StringBuffer();
		try {
			//EntityItem ei = new EntityItem(null, _prof, _strEntityType, _iEntityId);
			DatePackage dpNow = _db.getDates();
			String m_strTimeStampNow = dpNow.getNow();
			String strNotifyTime = m_strTimeStampNow;

			m_sbEPIMS.append("<EntityChangedNotification>");
			m_sbEPIMS.append("<Enterprise>" + _prof.getEnterprise() + "</Enterprise>");
			m_sbEPIMS.append("<EntityType>" + _strEntityType + "</EntityType>");
			m_sbEPIMS.append("<EntityId>" + _iEntityId + "</EntityId>");
			m_sbEPIMS.append("<NotificationTime>" + strNotifyTime + "</NotificationTime>");
			m_sbEPIMS.append("</EntityChangedNotification>");
			m_sbEPIMS.append("\n");

		} catch (Exception ex) {
			System.out.println("Exception in " + strTraceBase);
			ex.printStackTrace();
		}
	}

	public static String transformXML(String _strXml) {
	    _strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> " + _strXml  ;
		return _strXml;
  	}

}
