//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MKTGCOMPATPDG.java,v $
// Revision 1.14  2008/09/04 21:55:53  wendy
// Cleanup RSA warnings
//
// Revision 1.13  2006/03/04 18:13:47  joan
// fixes
//
// Revision 1.12  2006/02/20 21:39:47  joan
// clean up System.out.println
//
// Revision 1.11  2005/12/20 20:01:01  joan
// fixes
//
// Revision 1.10  2005/12/20 16:36:52  joan
// changes
//
// Revision 1.9  2005/12/09 19:11:14  joan
// fixes
//
// Revision 1.8  2005/12/07 00:24:20  joan
// add method
//
// Revision 1.7  2005/11/21 18:16:13  joan
// fixes
//
// Revision 1.6  2005/11/18 17:22:56  joan
// fixes
//
// Revision 1.5  2005/10/20 16:26:13  joan
// fixes
//
// Revision 1.4  2005/09/08 16:47:58  joan
// fixes
//
// Revision 1.3  2005/09/07 22:07:13  joan
// fixes
//
// Revision 1.2  2005/09/07 16:51:30  joan
// fixes
//
// Revision 1.1  2005/09/02 16:38:38  joan
// add new pdg
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.transactions.OPICMList;


/**
 * MKTGCOMPATPDG
 *
 */
public class MKTGCOMPATPDG extends PDGActionItem {
    static final long serialVersionUID = 20011106L;


    private String m_strMT = null;
    private String m_strCOUNTRY = null;
    private String m_strLSEO = null;
   // private EANList m_LSEOList = new EANList();

    /*
    * Version info
    */
    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: MKTGCOMPATPDG.java,v 1.14 2008/09/04 21:55:53 wendy Exp $";
    }

    /**
     * MKTGCOMPATPDG
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public MKTGCOMPATPDG(EANMetaFoundation _mf, MKTGCOMPATPDG _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
    }

    /**
     * This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strActionItemKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public MKTGCOMPATPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _strActionItemKey);
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("MKTGCOMPATPDG:" + getKey() + ":desc:" + getLongDescription());
        strbResult.append(":purpose:" + getPurpose());
        strbResult.append(":entitytype:" + getEntityType() + "/n");
        return new String(strbResult);
    }

    /**
     * (non-Javadoc)
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return "MKTGCOMPATPDG";
    }

	private EntityItem[] getModels(Database _db, Profile _prof, String _strPDHDomain) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		// Initialize some SP specific objects needed in this method
	//	String strTraceBase = " CATLGPUBPDG getModels method";
		ReturnDataResultSet rdrs = null;
		ResultSet rs = null;
		ReturnStatus returnStatus = new ReturnStatus(-1);

		// Placeholders for dates
		String strNow = null;
		//String strForever = null;
		//String strEndOfDay = null;

		// Pull some profile info...
		//int iOpenID = _prof.getOPWGID();
		//int iTranID = _prof.getTranID();
		//int iSessionID = _prof.getSessionID();
		String strEnterprise = _prof.getEnterprise();
		//String strRoleCode = _prof.getRoleCode();

		//StringBuffer sb = new StringBuffer();
		EntityItem[] aeiReturn = null;

		// Search for all MODEL within the Workgroup’s PDHDOMAINs where COFCAT= {Service | Software}
		// or COFSUBCAT= {Option},

		DatePackage dpNow = _db.getDates();
		strNow = dpNow.getNow();
	//	strForever = dpNow.getForever();
	//	strEndOfDay = dpNow.getEndOfDay();
		EANList modelList = new EANList();

		if (_strPDHDomain.indexOf(",") >= 0) {
			StringTokenizer st = new StringTokenizer(_strPDHDomain, ",");
			while (st.hasMoreTokens()) {
				String sDomain = st.nextToken();
				_db.debug(D.EBUG_SPEW,"gbl9208 parms: " + strEnterprise + ":" + sDomain);
				rs = _db.callGBL9208(returnStatus, strEnterprise, "MODEL", sDomain, strNow, strNow);
				rdrs = new ReturnDataResultSet(rs);
				rs.close();
				rs = null;
				_db.freeStatement();
				_db.isPending();

				for (int ii = 0 ;ii < rdrs.size();ii++) {
					String strEntityType = rdrs.getColumn(ii,0).trim();
					int iEntityID = rdrs.getColumnInt(ii,1);
					_db.debug(D.EBUG_SPEW,"gbl9208 answer: " + strEntityType  + ":" + iEntityID + ":");
					EntityItem ei = new EntityItem(null, _prof, strEntityType, iEntityID);
					modelList.put(ei);
				}
			}
		} else {
			_db.debug(D.EBUG_SPEW,"gbl9208 parms: " + strEnterprise + ":" + _strPDHDomain);
			rs = _db.callGBL9208(returnStatus, strEnterprise, "MODEL", _strPDHDomain, strNow, strNow);
			rdrs = new ReturnDataResultSet(rs);
			rs.close();
			rs = null;
			_db.freeStatement();
			_db.isPending();

			for (int ii = 0 ;ii < rdrs.size();ii++) {
				String strEntityType = rdrs.getColumn(ii,0).trim();
				int iEntityID = rdrs.getColumnInt(ii,1);
				_db.debug(D.EBUG_SPEW,"gbl9208 answer: " + strEntityType  + ":" + iEntityID + ":");
				EntityItem ei = new EntityItem(null, _prof, strEntityType, iEntityID);
				modelList.put(ei);
			}
		}

		aeiReturn = new EntityItem[modelList.size()];
		modelList.copyTo(aeiReturn);
		return aeiReturn;

	}

	private EntityItem[] getParentModel(EntityList _el, EntityItem _eiMatchLSEO) {
		//D.ebug(D.EBUG_SPEW,"getParentModel : " + _el.dump(false));
    	Vector vWWSEO = m_utility.getParentEntityIds(_el, _eiMatchLSEO.getEntityType(), _eiMatchLSEO.getEntityID(), "WWSEO", "WWSEOLSEO");

		EntityGroup egMODEL = _el.getEntityGroup("MODEL");
		if (_el.getParentEntityGroup().getEntityType().equals("MODEL")) {
			egMODEL = _el.getParentEntityGroup();
		}
		EANList eiList = new EANList();
		D.ebug(D.EBUG_SPEW,"getParentModel vWWSEO size: " + vWWSEO.size());
		//D.ebug(D.EBUG_SPEW,"getParentModel egMODEL: " + egMODEL.dump(false));
		for (int i =0; i < vWWSEO.size(); i++) {
			int iWWSEO = ((Integer)vWWSEO.elementAt(i)).intValue();
			D.ebug(D.EBUG_SPEW,"getParentModel iWWSEO: " + iWWSEO);
			Vector vMODEL = m_utility.getParentEntityIds(_el, "WWSEO", iWWSEO, "MODEL", "MODELWWSEO");
			D.ebug(D.EBUG_SPEW,"getParentModel vMODEL size: " + vMODEL.size());
			for (int j = 0; j < vMODEL.size(); j++) {
				int iMODEL = ((Integer)vMODEL.elementAt(j)).intValue();
				try {
					EntityItem eiMODEL = new EntityItem(egMODEL, null, "MODEL", iMODEL);
					eiMODEL = egMODEL.getEntityItem(eiMODEL.getKey());
					if (eiMODEL != null) {
						eiList.put(eiMODEL);
					}
				} catch (MiddlewareRequestException ex) {
					ex.printStackTrace();
				}
			}
		}
		EntityItem[] aeiMODEL = new EntityItem[eiList.size()];
		eiList.copyTo(aeiMODEL);
		return aeiMODEL;
	}

	private EntityItem[] getParentWWSEO(EntityList _el, EntityItem _eiMatchLSEO) {
		//D.ebug(D.EBUG_SPEW,"getParentWWSEO : " + _el.dump(false));
    	Vector vWWSEO = m_utility.getParentEntityIds(_el, _eiMatchLSEO.getEntityType(), _eiMatchLSEO.getEntityID(), "WWSEO", "WWSEOLSEO");

		EntityGroup egWWSEO = _el.getEntityGroup("WWSEO");
		if (_el.getParentEntityGroup().getEntityType().equals("WWSEO")) {
			egWWSEO = _el.getParentEntityGroup();
		}
		EANList eiList = new EANList();
		D.ebug(D.EBUG_SPEW,"getParentModel vWWSEO size: " + vWWSEO.size());
		//D.ebug(D.EBUG_SPEW,"getParentModel egMODEL: " + egMODEL.dump(false));
		for (int i =0; i < vWWSEO.size(); i++) {
			int iWWSEO = ((Integer)vWWSEO.elementAt(i)).intValue();
			D.ebug(D.EBUG_SPEW,"getParentModel iWWSEO: " + iWWSEO);
			try {
				EntityItem eiWWSEO = new EntityItem(egWWSEO, null, "WWSEO", iWWSEO);
				eiWWSEO = egWWSEO.getEntityItem(eiWWSEO.getKey());
				if (eiWWSEO != null) {
					eiList.put(eiWWSEO);
				}
			} catch (MiddlewareRequestException ex) {
				ex.printStackTrace();
			}
		}
		EntityItem[] aeiWWSEO = new EntityItem[eiList.size()];
		eiList.copyTo(aeiWWSEO);
		return aeiWWSEO;
	}

    /**
     * (non-Javadoc)
     * checkMissingData
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkMissingData(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, boolean)
     */
    protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase =  "MKTGCOMPATPDG checkMissingData method ";
        StringBuffer sbReturn = new StringBuffer();

		DatePackage dp = _db.getDates();
		String strNow = dp.getNow();
		String strCurrentDate = strNow.substring(0, 10);

		String strFileName = "PDGtemplates/MKTGCOMPAT01.txt";

		EntityList el = null;
		//EntityGroup egMODEL = null;
		StringBuffer sb = new StringBuffer();
		String strSai = (String) m_saiList.get("MODEL");
		String strPDHDomain = m_utility.getAttrValue(m_eiPDG, "PDHDOMAIN");
		//StringBuffer sbDomain = new StringBuffer();
		//sbDomain.append("map_PDHDOMAIN=" + strPDHDomain);

        EntityItem[] aei = getModels(_db, _prof, strPDHDomain);

        if (aei != null && aei.length > 0) {
			el = EntityList.getEntityList(_db, _prof, m_PDGxai, aei);
			EntityGroup egLSEO = el.getEntityGroup("LSEO");

			if (egLSEO == null || egLSEO.getEntityItemCount() <= 0) {
				m_SBREx.add("MODEL doesn't have LSEO");
			} else {

					for (int k=0; k < egLSEO.getEntityItemCount(); k++) {
						EntityItem eiLSEO = egLSEO.getEntityItem(k);
						EntityChangeHistoryGroup echg = new EntityChangeHistoryGroup(_db,_prof, eiLSEO);
						EntityChangeHistoryItem echi = (EntityChangeHistoryItem)getCurrentChangeItem(echg);
						//String strChangeDate = echi.getChangeDate().substring(0, 10);
						String strChangeDate = echi.getChangeDate();
						//, find LSEO where CATLGCNTRY.OFFCOUNTRY is in LSEO.COUNTRYLIST
						//and the LSEO has changed (or added) since CATLGCNTRY.CATLGMKTGLASTRUN for
						//CATLGCNTRY.OFFCOUNTRY.

						D.ebug(D.EBUG_SPEW,strTraceBase + " checking: " + eiLSEO.getKey() + ":" + eiLSEO.toString());

						int iDate =m_utility.longDateCompare(strChangeDate, m_utility.getAttrValue(m_eiPDG, "CATLGMKTGLASTRUN"));
						if (iDate != PDGUtility.EARLIER) {
							D.ebug(D.EBUG_SPEW,strTraceBase + ":" + eiLSEO.getKey() + " updated after last run.");
							EANList eiLSEOList = new EANList();
							eiLSEOList.put(eiLSEO);
							StringBuffer sbSearch = new StringBuffer();
							sbSearch.append("COUNTRYLIST=" +  m_utility.getAttrValue(m_eiPDG, "OFFCOUNTRY"));

							EntityItem eiMatchLSEO = m_utility.findEntityItem(eiLSEOList, "LSEO", sbSearch.toString(), false);
							if(eiMatchLSEO != null) {
								D.ebug(D.EBUG_SPEW,strTraceBase + ":" + eiLSEO.getKey() + " has matching OFFCOUNTRY");

								String strLSEOPUBDATEMTRGT = m_utility.getAttrValue(eiLSEO, "LSEOPUBDATEMTRGT");
								String strLSEOUNPUBDATEMTRGT = m_utility.getAttrValue(eiLSEO, "LSEOUNPUBDATEMTRGT");
/*
1) LSEOPUBDATEMTRGT must be less than or equat to NOW(), i.e. if LSEOPUBDATEMTRGT is blank, ignore the LSEO

2) if LSEOUNPUBDATEMTRGT is not blank, then it must be greater than or equal to NOW()
or if easier, use the current rule BUT if LSEOUNPUBDATEMTRGT is blank, treat it as if it is 9999-12-31
*/
								if (strLSEOPUBDATEMTRGT == null || strLSEOPUBDATEMTRGT.length() <= 0) {
									//m_SBREx.add("LSEO " + eiLSEO.toString() + ". LSEOPUBDATEMTRGT is blank.");
									D.ebug(D.EBUG_SPEW,strTraceBase + "LSEO " + eiLSEO.toString() + ". LSEOPUBDATEMTRGT is blank.");
									continue;
								}

								int iDate1 = m_utility.dateCompare(strLSEOPUBDATEMTRGT, strCurrentDate);

								int iDate2 = -1;
								if (strLSEOUNPUBDATEMTRGT == null || strLSEOUNPUBDATEMTRGT.length() <= 0) {
									iDate2 = PDGUtility.LATER;
								} else {
									iDate2 = m_utility.dateCompare(strLSEOUNPUBDATEMTRGT, strCurrentDate);
								}

								// find CATLGMKTGCOMPAT with CATLGMKTGCOMPAT.CATSEOID = LSEO.SEOID and CATLGMKTGCOMPAT.OFFCOUNTRY = CATLGCNTRY.OFFCOUNTRY
								sb = new StringBuffer();
					            sb.append("map_CATSEOID=" + m_utility.getAttrValue(eiMatchLSEO, "SEOID") + ";");
					            sb.append("map_OFFCOUNTRY=" +  m_utility.getAttrValue(m_eiPDG, "OFFCOUNTRY"));
					            strSai =  (String) m_saiList.get("CATLGMKTGCOMPAT");
								EntityItem[] aeiCATLGMKTGCOMPAT = m_utility.dynaSearch(_db, _prof, null, strSai, "CATLGMKTGCOMPAT", sb.toString());
								if (aeiCATLGMKTGCOMPAT == null || aeiCATLGMKTGCOMPAT.length <= 0) {
									//If not found, add an entry if NOW() is within the LSEO’s dates –
									//LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT
									D.ebug(D.EBUG_SPEW,strTraceBase + " not found CATLGMKTGCOMPAT");
									if (iDate1 != PDGUtility.LATER && iDate2 != PDGUtility.EARLIER) {
										EntityItem[] aeiMODEL = getParentModel(el, eiMatchLSEO);

										if (aeiMODEL == null || aeiMODEL.length <= 0) {
											D.ebug(D.EBUG_SPEW,strTraceBase + "Unable to find parent MODEL for LSEO:" + eiMatchLSEO.getKey());
											m_SBREx.add("Unable to find parent MODEL for LSEO:" + eiMatchLSEO.getKey());
										}

										EntityItem[] aeiWWSEO = getParentWWSEO(el, eiMatchLSEO);
										EntityItem eiWWSEO = null;
										if (aeiWWSEO != null && aeiWWSEO.length > 0) {
											eiWWSEO = aeiWWSEO[0];
										}
										for (int j = 0; j < aeiMODEL.length; j++) {
											EntityItem eiMODEL = aeiMODEL[j];
											OPICMList infoList = new OPICMList();
											infoList.put("LSEO", eiMatchLSEO);
											infoList.put("MODEL", eiMODEL);
											infoList.put("WG", m_eiRoot);
											infoList.put("CATLGCNTRY", m_eiPDG);
											if (eiWWSEO != null) {
												infoList.put("WWSEO", eiWWSEO);
											}
											_prof = m_utility.setProfValOnEffOn(_db, _prof);
											TestPDG pdgObject = new TestPDG(_db, _prof, null, infoList, strFileName );
											StringBuffer sbMissing = pdgObject.getMissingEntities();
											D.ebug(D.EBUG_SPEW,strTraceBase + " creating entity. ");
											generateData(_db, _prof, sbMissing, "");
										}
									} else {
										D.ebug(D.EBUG_SPEW,strTraceBase + " not in date range LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT. " + iDate1 + ":" + iDate2);
									}
								} else {
									D.ebug(D.EBUG_SPEW,strTraceBase + " found CATLGMKTGCOMPAT");
									//If found and NOW() is not within the LSEO’s dates, set OKTOPUB = No.
									if (iDate1 != PDGUtility.LATER && iDate2 != PDGUtility.EARLIER) {
										D.ebug(D.EBUG_SPEW,strTraceBase + " found and in date range LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT. " + iDate1 + ":" + iDate2);
									} else {
										for (int j=0; j < aeiCATLGMKTGCOMPAT.length; j++) {
											EntityItem eiCATLGMKTGCOMPAT = aeiCATLGMKTGCOMPAT[j];
											String strWorkFlow = m_utility.getAttrValue(eiCATLGMKTGCOMPAT, "CATWORKFLOW");
											OPICMList attList = new OPICMList();
											attList.put("OKTOPUB", "OKTOPUB=No");
											if (strWorkFlow.equals("Override")) {
												attList.put("CATWORKFLOW", "CATWORKFLOW=SalesStatusOverride");
											} else if (strWorkFlow.equals("Accept")) {
												attList.put("CATWORKFLOW", "CATWORKFLOW=Change");
											} else if (strWorkFlow.equals("New")) {
												attList.put("CATWORKFLOW", "CATWORKFLOW=Change");
											}

											D.ebug(D.EBUG_SPEW,strTraceBase + " updating entity. " + eiCATLGMKTGCOMPAT.getKey());
											m_utility.updateAttribute(_db, _prof, eiCATLGMKTGCOMPAT, attList);
										}

									}
								}
							}
						}
					}
				}

		} else {
			m_SBREx.add("There isn't a MODEL exist with COFCAT=Service|Software or COFSUBCAT=Option");
		}


        return sbReturn;
    }

    /**
     * (non-Javadoc)
     * checkPDGAttribute
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkPDGAttribute(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem)
     */
    protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _pdgEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
        for (int i = 0; i < _pdgEI.getAttributeCount(); i++) {
            EANAttribute att = _pdgEI.getAttribute(i);
            String textAtt = "";
          //  String sFlagAtt = "";
            String sFlagClass = "";
            Vector mFlagAtt = new Vector();

           // int index = -1;
            if (att instanceof EANTextAttribute) {
                textAtt = ((String) att.get()).trim();
            } else if (att instanceof EANFlagAttribute) {
                if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                        //    sFlagAtt = amf[f].getLongDescription().trim();
                            sFlagClass = amf[f].getFlagCode().trim();
                          //  index = f;
                            break;
                        }
                    }
                } else if (att instanceof MultiFlagAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                            mFlagAtt.addElement(amf[f].getLongDescription().trim());
                        }
                    }
                }
            }

            if (att.getKey().equals("MACHTYPEATR")) {
                m_strMT = sFlagClass;
            } else if (att.getKey().equals("OFFCOUNTRY")) {
                m_strCOUNTRY = sFlagClass;
            } else if (att.getKey().equals("LSEOIDLIST")) {
                m_strLSEO = textAtt;
            }
        }

        if (m_strMT == null) {
            m_SBREx.add("Machine Type is empty.");
            return;
        }

        if (m_strCOUNTRY == null) {
            m_SBREx.add("Offering Country is empty.");
            return;
        }

        if (m_strLSEO == null) {
            m_SBREx.add("LSEO ID List is empty.");
            return;
        }
    }

    /**
     * (non-Javadoc)
     * resetVariables
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#resetVariables()
     */
    protected void resetVariables() {
        m_eiList = new EANList();
    }

    /**
     * (non-Javadoc)
     * viewMissing
     *
     * @see COM.ibm.eannounce.objects.PDGTemplateActionItem#viewMissing(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile)
     */
    public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        return null;
    }

	private static ChangeHistoryItem getCurrentChangeItem(ChangeHistoryGroup _chg) {
		for (int i = 0; i < _chg.getChangeHistoryItemCount(); i++) {
			ChangeHistoryItem chi = (ChangeHistoryItem) _chg.getChangeHistoryItem(i);

			if (chi.isValid()) {
				return chi;
			}
		}

		return null;
	}

    /**
     * (non-Javadoc)
     * executeAction
     *
     * @see COM.ibm.eannounce.objects.PDGTemplateActionItem#executeAction(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile)
     */
    public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {

      //  ExtractActionItem eaItem = null;
      //  EntityItem[] eiParm = new EntityItem[1];
      //  EntityList el = null;
      //  EntityGroup eg = null;

        String strTraceBase = " MKTGCOMPATPDG executeAction method ";
        m_SBREx = new SBRException();
        String strData = "";
        resetVariables();
        try {
            _db.debug(D.EBUG_DETAIL, strTraceBase);
            if (m_eiPDG == null) {
                D.ebug(D.EBUG_SPEW,"PDG entity is null");
                return;
            }

            _prof = m_utility.setProfValOnEffOn(_db, _prof);
			// get WG
			EntityGroup egWG = new EntityGroup(null, _db, _prof, "WG", "Edit", false);
			m_eiRoot = new EntityItem(egWG, _prof, _db, _prof.getWGName(), _prof.getWGID());

            // validate data
            if (m_SBREx.getErrorCount() > 0) {
                throw m_SBREx;
            }

            m_utility.resetActivities();
            m_sbActivities = new StringBuffer();
            m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
            strData = checkMissingData(_db, _prof, true).toString();
            m_sbActivities.append(strData);

        } catch (SBRException ex) {
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
            throw ex;
        }
    }

    /**
     * (non-Javadoc)
     * checkDataAvailability
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkDataAvailability(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem)
     */
    protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _pdgEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    }

}
