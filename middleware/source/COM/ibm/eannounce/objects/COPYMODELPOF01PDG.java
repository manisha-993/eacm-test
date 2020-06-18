// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: COPYMODELPOF01PDG.java,v $
// Revision 1.18  2008/02/11 20:47:49  wendy
// Added copying PRODSTRUCTBOM for 'Copy Model Only' action
//
// Revision 1.17  2007/08/23 16:11:31  wendy
// MN32841099 WGMODEL replaced by WGMODELA
//
// Revision 1.16  2006/11/15 18:55:23  joan
// memory
//
// Revision 1.15  2006/11/15 16:51:06  joan
// memory
//
// Revision 1.14  2006/02/20 21:39:45  joan
// clean up System.out.println
//
// Revision 1.13  2005/04/15 15:21:30  joan
// fixes
//
// Revision 1.12  2005/02/28 20:29:51  joan
// add throw exception
//
// Revision 1.11  2005/02/10 00:54:53  joan
// add throw exception
//
// Revision 1.10  2005/02/04 20:57:15  joan
// fixes
//
// Revision 1.9  2005/02/03 18:53:53  joan
// fixes
//
// Revision 1.8  2005/02/03 18:51:53  joan
// fixes
//
// Revision 1.7  2005/02/02 20:36:27  joan
// fixes
//
// Revision 1.6  2005/02/02 20:14:44  joan
// CR3046
//
// Revision 1.5  2005/02/02 16:51:34  joan
// CR3046
//
// Revision 1.4  2005/01/17 22:57:19  joan
// fixes
//
// Revision 1.3  2005/01/17 16:50:06  joan
// fixes
//
// Revision 1.2  2005/01/15 00:40:56  joan
// fixes
//
// Revision 1.1  2005/01/14 19:09:07  joan
// initial load
//


package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import java.util.*;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;

import COM.ibm.opicmpdh.transactions.OPICMList;


public class COPYMODELPOF01PDG extends PDGActionItem {
	static final long serialVersionUID = 20011106L;

	private String m_strCopyOption = null;
	private String m_strCopyPerf = null;
    private Vector m_FCVec = new Vector();
    private Vector m_ModelVec = new Vector();
    private OPICMList m_FCList = new OPICMList();
    private EntityItem m_eiMODEL = null;

    private static final String MODELONLY = "0010"; 	 // Copy Model Only
    private static final String FEATUREONLY = "0020"; 	 // Copy Feature Only
    private static final String ALL = "0030"; 			 // Copy both Model and Feature
    private static final String LINKEXISTMODEL = "0040"; // Copy Features and link to existing Model

	private EANList m_FeatureList = new EANList();
	private boolean m_bAllFeatures = false;
  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return new String("$Id: COPYMODELPOF01PDG.java,v 1.18 2008/02/11 20:47:49 wendy Exp $");
  	}


	public COPYMODELPOF01PDG(EANMetaFoundation  _mf, COPYMODELPOF01PDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
		m_bCollectInfo = true;
		m_iCollectStep = 1;
	}

	/**
	* This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
	*/
	public COPYMODELPOF01PDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	  	m_bCollectInfo = true;
		m_iCollectStep = 1;
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("COPYMODELPOF01PDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return strbResult.toString();
  	}

  	public String getPurpose() {
  		return "COPYMODELPOF01PDG";
  	}

	/************
	* This is displayed on the button
	*/
	public String getStepDescription(int iStep) {
		if (iStep == 1) {
			return "Select Model(s)";
		}
		return "N/A";
	}

	/************
	* Take user selected Models and put type and id into the AFINFO attribute
	*/
	public void setPDGCollectInfo(PDGCollectInfoList _cl, int _iStep, RowSelectableTable _eiRst)
		throws SBRException, MiddlewareException
	{
		if (_iStep == 1) {
			if (m_eiPDG != null) {
				m_InfoList = _cl;

				StringBuffer sb = new StringBuffer();
				boolean bFirst = true;
				for (int i=0; i < m_InfoList.getCollectInfoItemCount(); i++) {
					PDGCollectInfoItem pi = m_InfoList.getCollectInfoItem(i);
					if (pi.isSelected()) {
						String strDesc = pi.toString();
						addDebug("Selected model = "+strDesc);
						sb.append((!bFirst? "\n": "") + strDesc);
						bFirst = false;
					}
				}
				int r = _eiRst.getRowIndex(m_eiPDG.getEntityType() + ":AFINFO:C");
				if (r >= 0 && r < _eiRst.getRowCount()) {
					try {
						_eiRst.put(r,1,sb.toString());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	/************
	* Called by UIs to select Models
	* SG	MODELAF7	MODEL	AFIRMT7	Relator	L
	*/
	public PDGCollectInfoList collectPDGInfo(Database _db, Profile _prof, int _iStep)
		throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
	{
		//String strTraceBase = "COPYMODELPOF01PDG collectPDGInfo";
		if (_iStep == 1) {
			if (m_eiPDG != null) {
				String strOption = m_utility.getAttrValue(m_eiPDG, "AF7COPYOPTION");
				if (!strOption.equals(LINKEXISTMODEL)) {
					m_SBREx = new SBRException();
					m_SBREx.add("Select Models is not required for this option.");
					throw m_SBREx;
				}
				_prof = m_utility.setProfValOnEffOn(_db, _prof);

				ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTCOPYINFOPDG1");
/* root AFIRMT7= m_eiPDG
SG  Action/Entity   EXTCOPYINFOPDG1 WGMODELA    D   2
SG  Action/Entity   EXTCOPYINFOPDG1 MODEL   Filter1 COFCAT100
SG  Action/Entity   EXTCOPYINFOPDG1 MODELAF7    U   0
SG  Action/Entity   EXTCOPYINFOPDG1 WGMODELA    U   1  this is wrong.. should be MODELWGA D 1

replaced with
SG	Action/Entity	EXTCOPYINFOPDG1	MODELWGA	D	1	2007-05-30 11:29:55.290242	9999-12-31 00:00:00.0	2007-05-30 11:29:55.290242	9999-12-31 00:00:00.0	123	1126
this change populates the list with all models meeting the COFCAT100 filter

*/
				EntityItem[] eiParm = {m_eiPDG};

				EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);

				m_InfoList = new PDGCollectInfoList(this, getProfile(), "");
				m_InfoList.setMatrix(false);
				m_InfoList.setColNames(new String[]{"Selected", "Entity Display Name"});

				EntityGroup egModel = el.getEntityGroup("MODEL");
				for(int i=0; i < egModel.getEntityItemCount(); i++) {
					EntityItem eiModel = egModel.getEntityItem(i);

					String strDesc = eiModel.getEntityType() + ":" + eiModel.getEntityID();
					PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, _prof, false, eiModel.getEntityType(), eiModel.getEntityID() + "", strDesc);
					pi.m_aColInfos = new String[] {"Selected", eiModel.toString()};
					m_InfoList.putCollectInfoItem(pi);
				}

				// repopulate the list with previous selections
				String strSel = m_utility.getAttrValue(m_eiPDG, "AFINFO");
				StringTokenizer st = new StringTokenizer(strSel, "\n");
				while (st.hasMoreTokens()) {
					String str = st.nextToken();
					StringTokenizer st1 = new StringTokenizer(str, ":");
					String strEntityType = st1.nextToken().trim();
					int iEntityID = Integer.parseInt(st1.nextToken().trim());

					PDGCollectInfoItem pi = m_InfoList.getCollectInfoItem(strEntityType + iEntityID);
					if (pi != null) {
						pi.setSelected(true);
					}
				}
			}
		}
		return m_InfoList;
	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " COPYMODELPOF01PDG checkMissingData method ";
		_db.debug(D.EBUG_DETAIL, strTraceBase+_bGenData);
		StringBuffer sbReturn = new StringBuffer();
		String strFileName = "";

		EntityGroup egFEATURE = m_ABReList.getEntityGroup("FEATURE");
		_db.test(egFEATURE != null, "checkMissingData:EntityGroup FEATURE is null");
		EntityGroup egPOF = m_ABReList.getEntityGroup("POF");
		EntityGroup egPRODSTRUCT = m_ABReList.getEntityGroup("PRODSTRUCT");

		OPICMList infoList = new OPICMList();

		if (m_strCopyOption.equals(ALL)) {
			infoList.put("MODEL", m_eiMODEL);
			m_eiList.put("MODEL", m_eiMODEL);

			strFileName = "PDGtemplates/COPYMODELPOF01.txt";
			String strAttributes = "map_INTERNALNAME=Copy of " + m_utility.getAttrValue(m_eiMODEL, "INTERNALNAME");
			EntityItem eiMODEL = null;
			for (int i=0; i < m_FeatureList.size(); i++) {

				// this is to find the already copied MODEL
				if (eiMODEL == null) {
					_db.debug(D.EBUG_SPEW,strTraceBase + " eiMODEL is null");
					eiMODEL = m_utility.findEntityItem(m_eiList, "MODEL", strAttributes);
				}

				m_eiList = new EANList();
				m_eiList.put("MODEL", m_eiMODEL);
				if (eiMODEL!=null){
					m_eiList.put(eiMODEL);//MN32841099 allow it to be found in generation step
				}

				EntityItem eiFEATURE = (EntityItem) m_FeatureList.getAt(i);
				infoList.put("FEATURE", eiFEATURE);
				m_eiList.put("FEATURE", eiFEATURE);

				for (int k=0; k < egPRODSTRUCT.getEntityItemCount(); k++) {
					EntityItem eiPRODSTRUCT = egPRODSTRUCT.getEntityItem(k);
					EntityItem eiP = (EntityItem)eiPRODSTRUCT.getUpLink(0);
					EntityItem eiC = (EntityItem)eiPRODSTRUCT.getDownLink(0);
					if (eiP == null || eiC == null) {
						_db.debug(D.EBUG_SPEW,strTraceBase + " eiPRODSTRUCT doesn't have eiP or eiC");
						continue;
					}
					if (eiC.getKey().equals(m_eiMODEL.getKey()) && eiP.getKey().equals(eiFEATURE.getKey())) {
						infoList.put("PRODSTRUCT", eiPRODSTRUCT);
						m_eiList.put("PRODSTRUCT", eiPRODSTRUCT);
						break;
					}
				}

				Vector vPOF = m_utility.getChildrenEntityIds(m_ABReList, eiFEATURE.getEntityType(), eiFEATURE.getEntityID(), "POF", "FCPOF");
				if (vPOF.size() > 0) {
					_db.debug(D.EBUG_DETAIL, strTraceBase+" POF found!!");
					for (int j=0; j < vPOF.size(); j++) {
						int iPofID = ((Integer)vPOF.elementAt(j)).intValue();
						EntityItem eiPOF = egPOF.getEntityItem(egPOF.getEntityType() + iPofID);

						infoList.put("POF", eiPOF);
						m_eiList.put("POF", eiPOF);

						_prof = m_utility.setProfValOnEffOn(_db, _prof);
						//TestPDGII pdgObject = new TestPDGII(_db, _prof, eiMODEL, infoList, m_PDGxai, strFileName);
						TestPDGII pdgObject = new TestPDGII(_db, _prof, eiMODEL, infoList, strFileName);
						StringBuffer sbMissing = pdgObject.getMissingEntities();
						sbReturn.append(sbMissing.toString());
						_db.debug(D.EBUG_DETAIL, strTraceBase+" getmissingEntities returned:"+sbMissing.toString());
						if (_bGenData && sbMissing.toString().length() > 0) {
							generateDataII(_db, _prof, sbMissing,"");
						}
						pdgObject = null;
					}
				} else {
					// no physical offering
					_db.debug(D.EBUG_DETAIL, strTraceBase+" No POF found");
					_prof = m_utility.setProfValOnEffOn(_db, _prof);
					//TestPDGII pdgObject = new TestPDGII(_db, _prof, eiMODEL, infoList, m_PDGxai, strFileName);
					TestPDGII pdgObject = new TestPDGII(_db, _prof, eiMODEL, infoList, strFileName);
					StringBuffer sbMissing = pdgObject.getMissingEntities();
					sbReturn.append(sbMissing.toString());
					_db.debug(D.EBUG_DETAIL, strTraceBase+" getmissingEntities returned:"+sbMissing.toString());
					if (_bGenData && sbMissing.toString().length() > 0) {
						generateDataII(_db, _prof, sbMissing,"");
					}
					pdgObject = null;
				}

				m_utility.memory(false);
			}
		} else if (m_strCopyOption.equals(MODELONLY)) {
			String strBomFileName = "PDGtemplates/COPYPSBOM.txt";
			infoList.put("MODEL", m_eiMODEL);
			m_eiList.put("MODEL", m_eiMODEL);

			strFileName = "PDGtemplates/COPYMODELPOF02.txt";

			String strAttributes = "map_INTERNALNAME=Copy of " + m_utility.getAttrValue(m_eiMODEL, "INTERNALNAME");
			EntityItem eiMODEL = null;
			for (int i=0; i < m_FeatureList.size(); i++) {
				// this is to find the already copied MODEL
				if (eiMODEL == null) {
					eiMODEL = m_utility.findEntityItem(m_eiList, "MODEL", strAttributes);
				}

				m_eiList = new EANList();
				m_eiList.put("MODEL", m_eiMODEL);
				if (eiMODEL!=null){
					m_eiList.put(eiMODEL);//MN32841099 allow it to be found in generation step
				}

				EntityItem eiPRODSTRUCT = null;
				EntityItem eiFEATURE = (EntityItem) m_FeatureList.getAt(i);
				infoList.put("FEATURE", eiFEATURE);
				m_eiList.put("FEATURE", eiFEATURE);
    			addDebug("copying  "+eiFEATURE.getKey());

				psloop:for (int p=0; p<eiFEATURE.getDownLinkCount(); p++){
					eiPRODSTRUCT = (EntityItem)eiFEATURE.getDownLink(p);
					if (eiPRODSTRUCT.getEntityType().equals("PRODSTRUCT")){
						// find out if this prodstruct is linked to the specified model
						for (int d=0; d<eiPRODSTRUCT.getDownLinkCount(); d++){
							EntityItem item = (EntityItem)eiPRODSTRUCT.getDownLink(0);
							if (item.getKey().equals(m_eiMODEL.getKey())) {
    							addDebug("found "+eiPRODSTRUCT.getKey());
								infoList.put("PRODSTRUCT", eiPRODSTRUCT);
								m_eiList.put("PRODSTRUCT", eiPRODSTRUCT);
								break psloop;
							}
						}
					} // end is prodstruct
				}

				if (eiPRODSTRUCT!=null){
					//_prof = m_utility.setProfValOnEffOn(_db, _prof);
					// OpicmList only handles one entity per type, so multiple PRODSTRUCTBOM are not handled
					TestPDGII pdgObject = new TestPDGII(_db, _prof, eiMODEL, infoList, strFileName);
					StringBuffer sbMissing = pdgObject.getMissingEntities();
					sbReturn.append(sbMissing.toString());
					_db.debug(D.EBUG_DETAIL, strTraceBase+" part1 getmissingEntities returned:"+sbMissing.toString());
					if (_bGenData && sbMissing.toString().length() > 0) {
						generateDataII(_db, _prof, sbMissing,"");
					}

					infoList.remove("FEATURE");
					infoList.remove("PRODSTRUCT");
					m_eiList.remove("FEATURE");
					m_eiList.remove("PRODSTRUCT");

					// get the newly created PRODSTRUCT, this is really bad code but use the root to pull this
					// as the parent for the prodstructbom
					EntityItem newProdstructItem = null;
					for (int e = 0; e<m_eiList.size(); e++)
					{
						EANObject obj = m_eiList.getAt(e);
						if (obj instanceof EntityItem){
							EntityItem ei2 = (EntityItem)obj;
							if (ei2.getEntityType().equals("PRODSTRUCT")){
								newProdstructItem = ei2;
								break;
							}
						}
					}

					m_eiRoot = newProdstructItem; // base code will pick this up and use as parent of BOM
					if (m_eiRoot !=null){
    					addDebug("newly created "+newProdstructItem.getKey());
						m_eiList.remove(m_eiRoot);

						// now do each PRODSTRUCTBOM now
						for (int d=0; d<eiPRODSTRUCT.getDownLinkCount(); d++){
							EntityItem item = (EntityItem)eiPRODSTRUCT.getDownLink(d);
							if (item.getEntityType().equals("PRODSTRUCTBOM")) {
								EntityItem bomitem = (EntityItem)item.getDownLink(0);
								infoList.put(item.getEntityType(), item);
								infoList.put(bomitem.getEntityType(), bomitem);
								m_eiList.put(item.getEntityType(), item);
								m_eiList.put(bomitem.getEntityType(), bomitem);

    							addDebug("copying "+item.getKey()+" to "+bomitem.getKey());

								pdgObject = new TestPDGII(_db, _prof, eiMODEL, infoList, strBomFileName);
								sbMissing = pdgObject.getMissingEntities();
								sbReturn.append(sbMissing.toString());
								_db.debug(D.EBUG_DETAIL, strTraceBase+" part2 getmissingEntities returned:"+sbMissing.toString());
								if (_bGenData && sbMissing.toString().length() > 0) {
									generateDataII(_db, _prof, sbMissing,"");
								}

								infoList.remove("PRODSTRUCTBOM");
								infoList.remove("BOM");
								m_eiList.remove("PRODSTRUCTBOM");
								m_eiList.remove("BOM");
							}
						}
					}else{
    					addDebug("COULD NOT find newly created prodstruct for "+eiPRODSTRUCT.getKey());
					}

					pdgObject = null;
				}else{
					// prodstruct not found for this feature.. cant really happen here
				}

				eiFEATURE = null;
				eiPRODSTRUCT = null;
				m_utility.memory(false);
			}
		} else if (m_strCopyOption.equals(FEATUREONLY)) {
			infoList.put("MODEL", m_eiMODEL);
			strFileName = "PDGtemplates/COPYMODELPOF03.txt";

			for (int i=0; i < m_FeatureList.size(); i++) {
				EntityItem eiFEATURE = (EntityItem) m_FeatureList.getAt(i);
				m_eiList = new EANList();
				m_eiList.put("MODEL", m_eiMODEL);
				infoList.put("FEATURE", eiFEATURE);
				m_eiList.put("FEATURE", eiFEATURE);

				for (int k=0; k < egPRODSTRUCT.getEntityItemCount(); k++) {
					EntityItem eiPRODSTRUCT = egPRODSTRUCT.getEntityItem(k);
					EntityItem eiP = (EntityItem)eiPRODSTRUCT.getUpLink(0);
					EntityItem eiC = (EntityItem)eiPRODSTRUCT.getDownLink(0);
					if (eiP == null || eiC == null) {
						_db.debug(D.EBUG_SPEW,strTraceBase + " eiPRODSTRUCT doesn't have eiP or eiC");
						continue;
					}
					if (eiC.getKey().equals(m_eiMODEL.getKey()) && eiP.getKey().equals(eiFEATURE.getKey())) {
						infoList.put("PRODSTRUCT", eiPRODSTRUCT);
						m_eiList.put("PRODSTRUCT", eiPRODSTRUCT);
						break;
					}
				}

				Vector vPOF = m_utility.getChildrenEntityIds(m_ABReList, eiFEATURE.getEntityType(), eiFEATURE.getEntityID(), "POF", "FCPOF");
				if (vPOF.size() > 0) {
					_db.debug(D.EBUG_DETAIL, strTraceBase+" POF found!!");
					for (int j=0; j < vPOF.size(); j++) {
						int iPofID = ((Integer)vPOF.elementAt(j)).intValue();
						EntityItem eiPOF = egPOF.getEntityItem(egPOF.getEntityType() + iPofID);

						infoList.put("POF", eiPOF);
						m_eiList.put("POF", eiPOF);

						_prof = m_utility.setProfValOnEffOn(_db, _prof);
						//TestPDGII pdgObject = new TestPDGII(_db, _prof, m_eiMODEL, infoList, m_PDGxai, strFileName);
						TestPDGII pdgObject = new TestPDGII(_db, _prof, m_eiMODEL, infoList, strFileName);
						StringBuffer sbMissing = pdgObject.getMissingEntities();
						sbReturn.append(sbMissing.toString());
						_db.debug(D.EBUG_DETAIL, strTraceBase+" getmissingEntities returned:"+sbMissing.toString());
						if (_bGenData && sbMissing.toString().length() > 0) {
							generateDataII(_db, _prof, sbMissing,"");
						}
						pdgObject = null;
					}
				} else {
					// no physical offering
					_db.debug(D.EBUG_DETAIL, strTraceBase+" No POF found");
					_prof = m_utility.setProfValOnEffOn(_db, _prof);
					//TestPDGII pdgObject = new TestPDGII(_db, _prof, m_eiMODEL, infoList, m_PDGxai, strFileName);
					TestPDGII pdgObject = new TestPDGII(_db, _prof, m_eiMODEL, infoList, strFileName);
					StringBuffer sbMissing = pdgObject.getMissingEntities();
					sbReturn.append(sbMissing.toString());
					_db.debug(D.EBUG_DETAIL, strTraceBase+" getmissingEntities returned:"+sbMissing.toString());
					if (_bGenData && sbMissing.toString().length() > 0) {
						generateDataII(_db, _prof, sbMissing,"");
					}
					pdgObject = null;
				}
				m_utility.memory(false);
			}
		} else if (m_strCopyOption.equals(LINKEXISTMODEL)) {
			strFileName = "PDGtemplates/COPYMODELPOF03.txt";
			EntityGroup egModel = new EntityGroup(null, _db, _prof, "MODEL", "Edit",false);
			for (int m=0; m < m_ModelVec.size(); m++) {
				String strModel = (String)m_ModelVec.elementAt(m);
				StringTokenizer stModel = new StringTokenizer(strModel, ":");
				String strET = stModel.nextToken();
				int iID = Integer.parseInt(stModel.nextToken());
				EntityItem eiMODEL = new EntityItem(egModel, _prof, _db, strET, iID);

				infoList.put("MODEL", eiMODEL);
				//m_eiList.put("MODEL", m_eiMODEL);

				_db.debug(D.EBUG_SPEW,strTraceBase + " LINKEXISTMODEL " + eiMODEL.getKey() + ":" + eiMODEL.toString());
				for (int i=0; i < m_FeatureList.size(); i++) {
					EntityItem eiFEATURE = (EntityItem) m_FeatureList.getAt(i);

					m_eiList = new EANList();

					infoList.put("FEATURE", eiFEATURE);
					m_eiList.put("FEATURE", eiFEATURE);
					m_eiList.put(eiMODEL); //MN32841099 allow for generatedata to find it

					for (int k=0; k < egPRODSTRUCT.getEntityItemCount(); k++) {
						EntityItem eiPRODSTRUCT = egPRODSTRUCT.getEntityItem(k);
						EntityItem eiP = (EntityItem)eiPRODSTRUCT.getUpLink(0);
						EntityItem eiC = (EntityItem)eiPRODSTRUCT.getDownLink(0);
						if (eiP == null || eiC == null) {
							_db.debug(D.EBUG_SPEW,strTraceBase + " eiPRODSTRUCT doesn't have eiP or eiC");
							continue;
						}
						if (eiC.getKey().equals(m_eiMODEL.getKey()) && eiP.getKey().equals(eiFEATURE.getKey())) {
							infoList.put("PRODSTRUCT", eiPRODSTRUCT);
							m_eiList.put("PRODSTRUCT", eiPRODSTRUCT);
							break;
						}
					}

					Vector vPOF = m_utility.getChildrenEntityIds(m_ABReList, eiFEATURE.getEntityType(), eiFEATURE.getEntityID(), "POF", "FCPOF");
					if (vPOF.size() > 0) {
						_db.debug(D.EBUG_DETAIL, strTraceBase+" POF found!!");
						for (int j=0; j < vPOF.size(); j++) {
							int iPofID = ((Integer)vPOF.elementAt(j)).intValue();
							EntityItem eiPOF = egPOF.getEntityItem(egPOF.getEntityType() + iPofID);

							infoList.put("POF", eiPOF);
							m_eiList.put("POF", eiPOF);

							_prof = m_utility.setProfValOnEffOn(_db, _prof);
							//TestPDGII pdgObject = new TestPDGII(_db, _prof, eiMODEL, infoList, m_PDGxai, strFileName);
							TestPDGII pdgObject = new TestPDGII(_db, _prof, eiMODEL, infoList, strFileName);
							StringBuffer sbMissing = pdgObject.getMissingEntities();
							sbReturn.append(sbMissing.toString());
							_db.debug(D.EBUG_DETAIL, strTraceBase+" getmissingEntities returned:"+sbMissing.toString());
							if (_bGenData && sbMissing.toString().length() > 0) {
								generateDataII(_db, _prof, sbMissing,"");
							}
							pdgObject = null;
						}
					} else {
						// no physical offering
						_db.debug(D.EBUG_DETAIL, strTraceBase+" No POF found");
						_prof = m_utility.setProfValOnEffOn(_db, _prof);
						//TestPDGII pdgObject = new TestPDGII(_db, _prof, eiMODEL, infoList, m_PDGxai, strFileName);
						TestPDGII pdgObject = new TestPDGII(_db, _prof, eiMODEL, infoList, strFileName);
						StringBuffer sbMissing = pdgObject.getMissingEntities();
						sbReturn.append(sbMissing.toString());
						_db.debug(D.EBUG_DETAIL, strTraceBase+" getmissingEntities returned:"+sbMissing.toString());
						if (_bGenData && sbMissing.toString().length() > 0) {
							generateDataII(_db, _prof, sbMissing,"");
						}
						pdgObject = null;
					}
					m_utility.memory(false);
				}
			}
		}

		return sbReturn;
	}

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
		//String strTraceBase = " COPYMODELPOF01PDG checkPDGAttribute method";
		for (int i =0; i < _afirmEI.getAttributeCount(); i++) {
			EANAttribute att = _afirmEI.getAttribute(i);
			String textAtt = "";
			String sFlagAtt = "";
			String sFlagClass = "";
			Vector mFlagAtt = new Vector();
			Vector mFlagClass = new Vector();

			//int index = -1;
			if (att instanceof EANTextAttribute) {
				textAtt = ((String)att.get()).trim();
			} else if (att instanceof EANFlagAttribute) {
				if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
					MetaFlag[] amf = (MetaFlag[])att.get();
					for (int f=0; f < amf.length; f++) {
						if (amf[f].isSelected()) {
							sFlagAtt = amf[f].getLongDescription().trim();
							sFlagClass = amf[f].getFlagCode().trim();
							//index = f;
							break;
						}
					}
				} else if (att instanceof MultiFlagAttribute) {
					MetaFlag[] amf = (MetaFlag[])att.get();
					for (int f=0; f < amf.length; f++) {
						if (amf[f].isSelected()) {
							mFlagAtt.addElement(amf[f].getLongDescription().trim());
							mFlagClass.addElement(amf[f].getFlagCode().trim());
						}
					}
				}
			}

			if (att.getKey().equals("AF7COPYOPTION")) {
				m_strCopyOption = sFlagClass;
			} else if (att.getKey().equals("AF7COPYPERFDATA")) {
				m_strCopyPerf = sFlagAtt;
			} else if (att.getKey().equals("AF7FEATURES")) { // new line delimited list of featurecodes
				m_FCVec = m_utility.sepLongText(textAtt);
			} else if (att.getKey().equals("AFINFO")) { // this attr is populated when 'select models' button is pressed
				m_ModelVec = m_utility.sepLongText(textAtt);
			}
		}

		if (m_strCopyOption == null || m_strCopyOption.length() <=0) {
			m_SBREx.add("Copy Options is required.");
		} else {
			if (m_strCopyOption.equals(LINKEXISTMODEL)) {
				if (m_ModelVec.size() <= 0) {
					m_SBREx.add("Please select a Model to link to copied Features.");
				}
			}
		}

		if (m_strCopyPerf == null || m_strCopyPerf.length() <=0) {
			m_SBREx.add("Copy Performance Related Data is required.");
		} else {
			if (m_strCopyPerf.equals("No")) {
				setExcludeCopy("001");
			} else {
				setExcludeCopy("0");
			}

		}

		if (m_FCVec == null || m_FCVec.size() <=0) {
			m_SBREx.add("Feature Codes is required.");
		} else {
			for (int i=0; i < m_FCVec.size(); i++) {
				String strFC = (String) m_FCVec.elementAt(i);
				if (strFC.equalsIgnoreCase("ALL")) {
					m_bAllFeatures = true;
					break;
				}
				m_FCList.put(strFC, strFC);
			}
		}

	}

	protected void resetVariables() {
		m_strCopyOption = null;
		m_strCopyPerf = null;
		m_FCVec = new Vector();
	 	m_ModelVec = new Vector();
     	m_FCList = new OPICMList();
     	m_eiMODEL = null;

		m_FeatureList = new EANList();
	 	m_bAllFeatures = false;
	}

	public byte[] viewMissing(Database _db, Profile _prof)
		throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
	{
		String strTraceBase = " COPYMODELPOF01PDG viewMissing method";
		m_SBREx = new SBRException();
		//String strData = "";

		_db.debug(D.EBUG_DETAIL, strTraceBase);
		if (m_eiPDG == null) {
			_db.debug(D.EBUG_SPEW,"MODEL entity is null");
			return null;
		}

		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		m_sbActivities = new StringBuffer();
		ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT7");
/* root = AFIRMT7
SG  Action/Entity   EXTAFIRMT7  FEATUREPROC D   2
SG  Action/Entity   EXTAFIRMT7  MODELAF7    U   0
SG  Action/Entity   EXTAFIRMT7  PRODSTRUCT  U   1
SG  Action/Entity   EXTAFIRMT7  WGMODELA    U   1  this is wrong too should be MODELWGA D 1

i added
SG	Action/Entity	EXTAFIRMT7	PRODSTRUCTBOM	D	2	2008-02-11 09:49:46.920815	9999-12-31 00:00:00.0	2008-02-11 09:49:46.920815	9999-12-31 00:00:00.0	-1	-1
SG	Action/Entity	EXTAFIRMT7	MODELWGA	D	1	2007-05-30 11:29:55.225472	9999-12-31 00:00:00.0	2007-05-30 11:29:55.225472	9999-12-31 00:00:00.0	123	1126


*/
		EntityItem[] eiParm = {m_eiPDG};
		m_ABReList = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
		//_db.debug(D.EBUG_SPEW,strTraceBase + m_ABReList.dump(false));

		EntityGroup eg = m_ABReList.getParentEntityGroup();
		m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
		checkPDGAttribute(_db, _prof, m_eiPDG);
		// validate data
		checkDataAvailability(_db, _prof, m_eiPDG);
		if (m_SBREx.getErrorCount() > 0) {
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
			m_ABReList.dereference();
			throw m_SBREx;
		}

		m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
		String s = checkMissingData(_db, _prof, false).toString();
		if (!runBySPDG() && s.length() <= 0) {
			s = "Generating data is complete";
		}
		m_sbActivities.append(m_utility.getViewXMLString(s));
		m_utility.savePDGViewXML(_db, _prof, m_eiPDG, m_sbActivities.toString());
		m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_PASSED, "", getLongDescription());

		m_ABReList.dereference();
		return s.getBytes();
	}

	public void executeAction(Database _db, Profile _prof)
		throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
	{
		String strTraceBase = " COPYMODELPOF01PDG executeAction method ";
		m_SBREx = new SBRException();
		String strData = "";

		if (m_eiPDG == null) {
			_db.debug(D.EBUG_SPEW,"PDG entity is null");
			return;
		}

		_db.debug(D.EBUG_DETAIL, strTraceBase + m_eiPDG.getKey());
		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		m_sbActivities = new StringBuffer();

		ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT7");
		EntityItem[] eiParm = {m_eiPDG};
		m_ABReList = EntityList.getEntityList(_db, _prof, eaItem, eiParm);

		EntityGroup eg = m_ABReList.getParentEntityGroup();
		m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
		checkPDGAttribute(_db, _prof, m_eiPDG);
		// validate data
		checkDataAvailability(_db, _prof, m_eiPDG);
		if (m_SBREx.getErrorCount() > 0) {
			throw m_SBREx;
		}

		strData = checkMissingData(_db, _prof, true).toString();

		m_sbActivities.append(m_utility.getActivities().toString());

		m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
		OPICMList attList = new OPICMList();
		attList.put("AFINFO", "AFINFO= ");
		m_utility.updateAttribute(_db, _prof, m_eiPDG, attList);

		if (strData.length() <= 0) {
			m_SBREx.add("Generating data is complete.  No data created during this run.");
			m_ABReList.dereference();
			throw m_SBREx;
		}
		m_ABReList.dereference();
	}
    private void addDebug(String msg){
        m_sbActivities.append("<DEBUG>"+msg+"</DEBUG>"+ NEW_LINE);
    }
    /*****************************
    * check entitylist for model and specified feature(s)
    */
	protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " COPYMODELPOF01PDG checkDataAvailability method ";

/*MN32841099
This is no longer a link to WG, WGMODEL relator was changed to WGMODELA assoc
    	EntityGroup egWG = m_ABReList.getEntityGroup("WG");
		_db.test(egWG != null, strTraceBase + " EntityGroup WG is null");
		_db.test(egWG.getEntityItemCount() > 0, strTraceBase + " EntityGroup WG is empty");
    	m_eiRoot = egWG.getEntityItem(0);                                 //This is wg
    	*/

    	EntityGroup egMODEL = m_ABReList.getEntityGroup("MODEL");
    	_db.test(egMODEL != null,  strTraceBase + "EntityGroup MODEL is null");
    	_db.test(egMODEL.getEntityItemCount() > 0, strTraceBase + " EntityGroup MODEL is empty");
    	m_eiMODEL = egMODEL.getEntityItem(0);
    	addDebug("copying "+m_eiMODEL.getKey());

    	EntityGroup egFEATURE = m_ABReList.getEntityGroup("FEATURE");
    	_db.test(egFEATURE != null,  strTraceBase + "EntityGroup FEATURE is null");

    	for (int i=0; i < egFEATURE.getEntityItemCount(); i++) {
			EntityItem eiFEATURE = egFEATURE.getEntityItem(i);
			String strFC = m_utility.getAttrValue(eiFEATURE, "FEATURECODE");
			if (m_bAllFeatures) {
				m_FeatureList.put(eiFEATURE);
    			addDebug("using [all] "+eiFEATURE.getKey());
			} else {
				if (m_FCList.get(strFC) != null) {
					m_FeatureList.put(eiFEATURE);
    				addDebug("using "+eiFEATURE.getKey());
				}
			}
		}

		if (m_FeatureList.size() <= 0) {
			m_SBREx.add("Unable to find any matched FEATUREs under the selected MODEL.");
		}
	}
}
