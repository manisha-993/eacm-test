//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MSOFFERINGPDG.java,v $
// Revision 1.15  2008/09/04 21:55:54  wendy
// Cleanup RSA warnings
//
// Revision 1.14  2006/03/10 00:09:46  joan
// changes
//
// Revision 1.13  2006/02/20 21:39:47  joan
// clean up System.out.println
//
// Revision 1.12  2005/12/20 16:36:52  joan
// changes
//
// Revision 1.11  2005/12/05 23:20:52  joan
// fixes
//
// Revision 1.10  2005/11/16 20:51:09  joan
// fixes
//
// Revision 1.9  2005/10/17 23:11:07  joan
// fixes
//
// Revision 1.8  2005/10/17 21:13:08  joan
// fixes
//
// Revision 1.7  2005/10/17 16:37:21  joan
// fixes
//
// Revision 1.6  2005/10/16 19:14:14  joan
// fixes
//
// Revision 1.5  2005/10/15 22:00:23  joan
// fixes
//
// Revision 1.4  2005/10/14 23:01:28  joan
// fixes
//
// Revision 1.3  2005/10/14 20:19:32  joan
// fixes
//
// Revision 1.2  2005/10/13 22:47:00  joan
// fixes
//
// Revision 1.1  2005/10/13 22:14:09  joan
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


/**
 * MSOFFERINGPDG
 *
 */
public class MSOFFERINGPDG extends PDGActionItem {
    static final long serialVersionUID = 20011106L;
	private static final String ENTITY_CATNAV = "CATNAV";
	private static final String ENTITY_MODEL = "MODEL";
	private static final String ENTITY_LSEO = "LSEO";
	private static final String ENTITY_LSEOBUNDLE = "LSEOBUNDLE";

	private static final String ATT_MACHTYPE = "MACHTYPEATR";
	private static final String ATT_MODEL = "MODELATR";
	private static final String ATT_SEOID = "SEOID";

	private static final String ATT_CATMACHTYPE = "CATMACHTYPE";
	private static final String ATT_CATMODEL = "CATMODEL";
	private static final String ATT_CATSEOID = "CATSEOID";

	private static final String ATT_PDGOFFLIST = "PDGOFFLIST";
	private static final String ATT_MODMKTGDESC = "MODMKTGDESC";
	private static final String ATT_ANNDATE = "ANNDATE";
	private static final String ATT_WITHDRAWDATE = "WITHDRAWDATE";
	private static final String ATT_LSEOMKTGDESC = "LSEOMKTGDESC";
	private static final String ATT_LSEOPUBDATEMTRGT = "LSEOPUBDATEMTRGT";
	private static final String ATT_LSEOUNPUBDATEMTRGT = "LSEOUNPUBDATEMTRGT";
	private static final String ATT_BUNDLMKTGDESC = "BUNDLMKTGDESC";
	private static final String ATT_BUNDLPUBDATEMTRGT = "BUNDLPUBDATEMTRGT";
	private static final String ATT_BUNDLUNPUBDATEMTRGT = "BUNDLUNPUBDATEMTRGT";
	private static final String ATT_CATDEFAULTSORT = "CATDEFAULTSORT";
	private static final String ATT_CATOFFMKTDESC = "CATOFFMKTDESC";
	private static final String ATT_PF = "PUBFROM";
	private static final String ATT_PT = "PUBTO";
	private static final String ATT_CATOFFSEQNUM = "CATOFFSEQNUM";
	private static final String ATT_CATWORKFLOW = "CATWORKFLOW";

    //private String m_strOffList = null;


	private EntityItem m_eiCATNAV  = null;

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
        return "$Id: MSOFFERINGPDG.java,v 1.15 2008/09/04 21:55:54 wendy Exp $";
    }

    /**
     * MSOFFERINGPDG
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public MSOFFERINGPDG(EANMetaFoundation _mf, MSOFFERINGPDG _ai) throws MiddlewareRequestException {
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
    public MSOFFERINGPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
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
        strbResult.append("MSOFFERINGPDG:" + getKey() + ":desc:" + getLongDescription());
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
        return "MSOFFERINGPDG";
    }

	public void setPDGCollectInfo(PDGCollectInfoList _cl, EANMetaAttribute _meta, RowSelectableTable _eiRst) throws SBRException, MiddlewareException {
		if (_meta.getKey().equals(ATT_PDGOFFLIST)) {
			if (m_eiPDG != null) {
				m_InfoList = _cl;
				StringBuffer sb = new StringBuffer();
				boolean bFirst = true;
				//int iPR = 0;
				for (int i=0; i < m_InfoList.getCollectInfoItemCount(); i++) {
					PDGCollectInfoItem pi = m_InfoList.getCollectInfoItem(i);
					if (pi.isSelected()) {
						String strDesc = pi.toString();
						sb.append((!bFirst? ",": "") + strDesc);
						bFirst = false;
						pi.setSelected(false);
					}
				}

				int r = _eiRst.getRowIndex(m_eiPDG.getEntityType() + ":PDGOFFLIST:C");
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

	private void getInfoList(Database _db, Profile _prof, EANMetaAttribute _meta) {
		String strTraceBase = "MSOFFERINGPDG getInfoList method ";
		if (m_eiPDG != null) {
			try {
				if (m_eiCATNAV == null) {
					ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTPDGENTFMR6");
					EntityItem[] eiParm =  {m_eiPDG};
					_prof = m_utility.setProfValOnEffOn(_db, _prof);
					EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
					EntityGroup eg = el.getEntityGroup(ENTITY_CATNAV);
					if (eg != null) {
						if (eg.getEntityItemCount() > 0) {
							m_eiCATNAV = eg.getEntityItem(0);
						}
					}
					_db.test(m_eiCATNAV != null, "CATNAV  entity is null");
				}


				//get the offering

				EntityItem[] eiParm = {m_eiCATNAV};
				ExtractActionItem eai = new ExtractActionItem(null, _db, _prof, "EXTEDITEVPPDG01");
				EntityList el = EntityList.getEntityList(_db, _prof, eai, eiParm);


				EntityGroup egMODEL = el.getEntityGroup(ENTITY_MODEL);
				_db.test(egMODEL != null, "Entity Group MODEL is null");

				EntityGroup egLSEO = el.getEntityGroup(ENTITY_LSEO);
				_db.test(egLSEO != null, "Entity Group LSEO is null");

				EntityGroup egLSEOBUNDLE = el.getEntityGroup(ENTITY_LSEOBUNDLE);
				_db.test(egLSEOBUNDLE != null, "Entity Group LSEOBUNDLE is null");


				m_InfoList = new PDGCollectInfoList(this, getProfile(), "");
				m_InfoList.setMatrix(false);
				m_InfoList.setColNames(new String[]{"Selected", "EVP Values"});

				for (int i=0; i < egMODEL.getEntityItemCount(); i++) {
					EntityItem eiMODEL = egMODEL.getEntityItem(i);

					D.ebug(D.EBUG_SPEW,strTraceBase + " eiMODEL: " + eiMODEL.dump(false));
					String strMachType = m_utility.getAttrValue(eiMODEL, ATT_MACHTYPE);
					String strModel = m_utility.getAttrValue(eiMODEL, ATT_MODEL);
					if (strMachType.length() > 0 || strModel.length() > 0) {
						String strDesc = "MODEL:" + strMachType + "-" + strModel;
						D.ebug(D.EBUG_SPEW,strTraceBase + strDesc);
						PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, _prof, false, eiMODEL.getKey(), eiMODEL.getKey(), strDesc);
 						pi.m_aColInfos = new String[] {"Selected", strMachType + strModel};
 						m_InfoList.putCollectInfoItem(pi);
					}
				}

				for (int j=0; j < egLSEO.getEntityItemCount(); j++) {
					EntityItem eiLSEO = egLSEO.getEntityItem(j);
					String strSEOID = m_utility.getAttrValue(eiLSEO, ATT_SEOID);
					if (strSEOID.length() > 0) {
						String strDesc = "LSEO:" + strSEOID;
						PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, _prof, false, eiLSEO.getKey(), eiLSEO.getKey() , strDesc);
						pi.m_aColInfos = new String[] {"Selected", strSEOID};
						m_InfoList.putCollectInfoItem(pi);
					}
				}

				for (int k=0; k < egLSEOBUNDLE.getEntityItemCount(); k++) {
					EntityItem eiLSEOBUNDLE = egLSEOBUNDLE.getEntityItem(k);
					String strSEOID = m_utility.getAttrValue(eiLSEOBUNDLE, ATT_SEOID);
					if (strSEOID.length() > 0) {
						String strDesc = "LSEOBUNDLE:" + strSEOID;
						PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, _prof, false, eiLSEOBUNDLE.getKey(), eiLSEOBUNDLE.getKey(), strDesc);
 						pi.m_aColInfos = new String[] {"Selected", strSEOID};
						m_InfoList.putCollectInfoItem(pi);
					}
				}

		 	} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public PDGCollectInfoList collectPDGInfo(Database _db, Profile _prof, EANMetaAttribute _meta) {
		//String strTraceBase = "VARSPECBIDPDG collectPDGInfo meta";
		if (_meta.getKey().equals(ATT_PDGOFFLIST)) {
			if (m_InfoList == null) {
				getInfoList(_db, _prof, _meta);
			}
		}

		return m_InfoList;
	}

    /**
     * (non-Javadoc)
     * checkMissingData
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkMissingData(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, boolean)
     */
    protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase =  "MSOFFERINGPDG checkMissingData method ";
        StringBuffer sbReturn = new StringBuffer();

		//DatePackage dp = _db.getDates();
		//String strNow = dp.getNow();
		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		EANList eiCATMatchList = new EANList();
		EANList eiOffList = new EANList();

		String strFileName = "PDGtemplates/MSOFFERINGPDG1.txt";

		// get CATOFFSEQ link to CATNAV
		EntityItem[] aei = {m_eiCATNAV};
		EntityList el = EntityList.getEntityList(_db, _prof, m_PDGxai, aei);
		EntityGroup eg = el.getEntityGroup("CATOFFSEQ");
		_db.test(eg != null, "Entity Group CATOFFSEQ is null");
		for (int i=0; i < eg.getEntityItemCount(); i++) {
			D.ebug(D.EBUG_SPEW,strTraceBase + " put in CATOFFSEQ: " + eg.getEntityItem(i).getKey());
			m_eiList.put(eg.getEntityItem(i));
		}

		// get all the offerings
		EntityItem[] eiParm = {m_eiCATNAV};
		ExtractActionItem eai = new ExtractActionItem(null, _db, _prof, "EXTEDITEVPPDG01");
		EntityList elOff = EntityList.getEntityList(_db, _prof, eai, eiParm);

		EntityGroup egMODEL = elOff.getEntityGroup(ENTITY_MODEL);
		_db.test(egMODEL != null, "Entity Group MODEL is null");
		for (int i=0; i < egMODEL.getEntityItemCount(); i++) {
			eiOffList.put(egMODEL.getEntityItem(i));
		}
		EntityGroup egLSEO = elOff.getEntityGroup(ENTITY_LSEO);
		_db.test(egLSEO != null, "Entity Group LSEO is null");
		for (int i=0; i < egLSEO.getEntityItemCount(); i++) {
			eiOffList.put(egLSEO.getEntityItem(i));
		}

		EntityGroup egLSEOBUNDLE = elOff.getEntityGroup(ENTITY_LSEOBUNDLE);
		_db.test(egLSEOBUNDLE != null, "Entity Group LSEOBUNDLE is null");
		for (int i=0; i < egLSEOBUNDLE.getEntityItemCount(); i++) {
			eiOffList.put(egLSEOBUNDLE.getEntityItem(i));
		}

		// get the workgroup
		EntityGroup egWG = new EntityGroup(null, _db, _prof, "WG", "Edit", false);

		EntityItem eiWG = new EntityItem(egWG,  _prof, _db, "WG", _prof.getWGID());

		for (int i=0; i < eiOffList.size(); i++) {
			StringBuffer sbCATOFFSEQ = new StringBuffer();
            EntityItem eiOFF = (EntityItem)eiOffList.getAt(i);
			String strMktDes = "";
			String strOffType = "";
			String strPF = "";
			String strPT = "";
			String strSEOID = "";
			String strOff = "";
			String strEntityType = "";
            if (eiOFF != null) {
				strEntityType = eiOFF.getEntityType();
				//CATOFFMKTDESC= 	MODMKTGDESC	LSEOMKTGDESC	BUNDLMKTGDESC
				//CATOFFTYPE	* "Model"	* "LSEO"	* "LSEOBUNDLE"
				//PUBFROM	ANNDATE	LSEOPUBDATEMTRGT	BUNDLPUBDATEMTRGT
				//PUBTO	WITHDRAWDATE	LSEOUNPUBDATEMTRGT	BUNDLUNPUBDATEMTRGT
				if (strEntityType.equals(ENTITY_MODEL)) {
					strMktDes = m_utility.getAttrValue(eiOFF, ATT_MODMKTGDESC);
					strOffType = "MODEL";
					strPF = m_utility.getAttrValue(eiOFF, ATT_ANNDATE);
					strPT = m_utility.getAttrValue(eiOFF, ATT_WITHDRAWDATE);
					strOff = m_utility.getAttrValue(eiOFF, ATT_MACHTYPE) + m_utility.getAttrValue(eiOFF, ATT_MODEL);
					sbCATOFFSEQ.append("map_CATMACHTYPE=" + m_utility.getAttrValue(eiOFF, ATT_MACHTYPE) + ";");
					sbCATOFFSEQ.append("map_CATMODEL=" + m_utility.getAttrValue(eiOFF, ATT_MODEL));
				} else if (strEntityType.equals(ENTITY_LSEO)) {
					strMktDes = m_utility.getAttrValue(eiOFF, ATT_LSEOMKTGDESC);
					strOffType = "LSEO";
					strPF = m_utility.getAttrValue(eiOFF, ATT_LSEOPUBDATEMTRGT);
					strPT = m_utility.getAttrValue(eiOFF, ATT_LSEOUNPUBDATEMTRGT);
					strSEOID = m_utility.getAttrValue(eiOFF, ATT_SEOID);
					strOff = strSEOID;
					sbCATOFFSEQ.append("map_CATSEOID=" + strSEOID);
				} else if (strEntityType.equals(ENTITY_LSEOBUNDLE)) {
					strMktDes = m_utility.getAttrValue(eiOFF, ATT_BUNDLMKTGDESC);
					strOffType = "BUNDLE";
					strPF = m_utility.getAttrValue(eiOFF, ATT_BUNDLPUBDATEMTRGT);
					strPT = m_utility.getAttrValue(eiOFF, ATT_BUNDLUNPUBDATEMTRGT);
					strSEOID = m_utility.getAttrValue(eiOFF, ATT_SEOID);
					strOff = strSEOID;
					sbCATOFFSEQ.append("map_CATSEOID=" + strSEOID);
				}
			}

			D.ebug(D.EBUG_SPEW,strTraceBase + " looking for CATOFFSEQ: " + sbCATOFFSEQ.toString());
			EntityItem eiCATOFFSEQ = m_utility.findEntityItem(m_eiList, "CATOFFSEQ", sbCATOFFSEQ.toString());

            if (eiCATOFFSEQ == null) {
				// create new entity
                OPICMList infoList = new OPICMList();
				infoList.put(strEntityType, eiOFF);
				infoList.put("CATNAV", m_eiCATNAV);
				infoList.put("MKTDES", strMktDes);
				infoList.put("OFFTYPE", strOffType);
				infoList.put("SEOID", strSEOID);
				infoList.put("PF", strPF);
				infoList.put("PT", strPT);
                infoList.put("PDG", m_eiPDG);
                infoList.put("WG", eiWG);

                _prof = m_utility.setProfValOnEffOn(_db, _prof);
               	TestPDG pdgObject = new TestPDG(_db, _prof, m_eiCATNAV, infoList, strFileName );
                StringBuffer sbMissing = pdgObject.getMissingEntities();

               	pdgObject = null;
               	infoList = null;
               	if (_bGenData) {
               	    m_bGetParentEIAttrs = true;
               	    generateData(_db, _prof, sbMissing, "");

				}
               	if (sbMissing.toString().length() > 0) {
					sbReturn.append("<MSG>Add CATOFFSEQ for " + strOff + "</MSG>");
				}
			} else {

				// update attributes CATOFFMKTDESC, PUBFROM, PUBTO
				eiCATMatchList.put(eiCATOFFSEQ);
				OPICMList attL = new OPICMList();

				if (!m_utility.getAttrValue(eiCATOFFSEQ, ATT_CATOFFMKTDESC).equals(strMktDes)) {
			    	attL.put(ATT_CATOFFMKTDESC, ATT_CATOFFMKTDESC + "=" + strMktDes);
				}

				if (!m_utility.getAttrValue(eiCATOFFSEQ, ATT_PF).equals(strPF)) {
			    	attL.put(ATT_PF, ATT_PF + "=" + strPF);
				}
				if (!m_utility.getAttrValue(eiCATOFFSEQ, ATT_PT).equals(strPT)) {
					attL.put(ATT_PT, ATT_PT + "=" + strPT);
				}

				if (attL.size() > 0) {
					String strWorkFlow = m_utility.getAttrValue(eiCATOFFSEQ, "CATWORKFLOW");
					if (strWorkFlow.equals("Override")) {
						attL.put(ATT_CATWORKFLOW, ATT_CATWORKFLOW + "=SalesStatusOverride");
					} else if (strWorkFlow.equals("Accept")) {
						attL.put(ATT_CATWORKFLOW, ATT_CATWORKFLOW + "=Change");
					} else if (strWorkFlow.equals("New")) {
						attL.put(ATT_CATWORKFLOW, ATT_CATWORKFLOW + "=Change");
					}

			    	_prof = m_utility.setProfValOnEffOn(_db, _prof);
					m_utility.updateAttribute(_db, _prof, eiCATOFFSEQ, attL);
					sbReturn.append("<MSG>Update CATOFFSEQ for " + strOff + "</MSG>");
				}
			}

			updateCATOFFSEQNUM(_db, _prof, getSavedEIList());
		}

		// remove unmatching CATOFFSEQ
		for (int i=0; i < eg.getEntityItemCount(); i++) {
			EntityItem ei = eg.getEntityItem(i);
			if (eiCATMatchList.get(ei.getKey()) == null) {
				EANUtility.deactivateEntity(_db, _prof, ei);
			}
		}
        return sbReturn;
    }

	private void updateCATOFFSEQNUM(Database _db, Profile _prof, EANList _eiList) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {

		String strValue = m_utility.getAttrValue(m_eiCATNAV, ATT_CATDEFAULTSORT);
		String[] aSort = new String[_eiList.size()];
		for (int i=0; i < _eiList.size(); i++) {
			EntityItem ei = (EntityItem)_eiList.getAt(i);
			String strSort = null;
			if (strValue.equals("2000")) {
				//get description CATOFFMKTDESC
				strSort = m_utility.getAttrValue(ei, ATT_CATOFFMKTDESC);
			} else if (strValue.equals("2020") || strValue.equals("2040")) {
				// get MACHTYPEATR + MODELATR + SEOID
				strSort = m_utility.getAttrValue(ei, ATT_CATMACHTYPE) + m_utility.getAttrValue(ei, ATT_CATMODEL) + m_utility.getAttrValue(ei, ATT_CATSEOID);
			} else {
				// price, processor, or else: 9999
				strSort = "9999";
			}
			aSort[i] = strSort + ":" + ei.getKey();
		}
	    Arrays.sort(aSort);

	    for (int a=0; a < aSort.length; a++) {
			String str = aSort[a];

			int iC = -1;
			for (int c =0; c < str.length(); c++) {
				char ch = str.charAt(c);
				if (ch == ':') {
					iC = c;
				}
			}

			if (iC >= 0) {
				String strEIKey = str.substring(iC+1);
				EntityItem ei = (EntityItem)_eiList.get(strEIKey);
				if (ei != null) {
					OPICMList attL = new OPICMList();
				    attL.put(ATT_CATOFFSEQNUM, ATT_CATOFFSEQNUM + "=" + (a+1));
    				_prof = m_utility.setProfValOnEffOn(_db, _prof);
   					m_utility.updateAttribute(_db, _prof, ei, attL);
				}
			}

		}
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
         //   String textAtt = "";
         //   String sFlagAtt = "";
         //   String sFlagClass = "";
            Vector mFlagAtt = new Vector();

          //  int index = -1;
            if (att instanceof EANTextAttribute) {
           //     textAtt = ((String) att.get()).trim();
            } else if (att instanceof EANFlagAttribute) {
                if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                  //          sFlagAtt = amf[f].getLongDescription().trim();
                  //          sFlagClass = amf[f].getFlagCode().trim();
                  //          index = f;
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

        ExtractActionItem eaItem = null;
        EntityItem[] eiParm = new EntityItem[1];
        EntityList el = null;
        EntityGroup eg = null;
        String s = null;

        String strTraceBase = " MSOFFERINGPDG viewMissingEntities method";

        _db.debug(D.EBUG_DETAIL, strTraceBase);
        m_SBREx = new SBRException();
        resetVariables();
        if (m_eiPDG == null) {
            s = "PDG entity is null";
            return s.getBytes();
        }
        _prof = m_utility.setProfValOnEffOn(_db, _prof);
        eaItem = new ExtractActionItem(null, _db, _prof, "EXTPDGENTFMR3");
        eiParm[0] = m_eiPDG;
        el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
        eg = el.getParentEntityGroup();
        m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());

//        checkPDGAttribute(_db, _prof, m_eiPDG);
        // validate data
//        checkDataAvailability(_db, _prof, m_eiPDG);
        if (m_SBREx.getErrorCount() > 0) {
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
            throw m_SBREx;
        }

        m_sbActivities = new StringBuffer();
        m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");

        s = checkMissingData(_db, _prof, false).toString();
        if (s.length() <= 0) {
            s = "Generating data is complete";
        }

        m_sbActivities.append(m_utility.getViewXMLString(s));
        m_utility.savePDGViewXML(_db, _prof, m_eiPDG, m_sbActivities.toString());
        m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_PASSED, "", getLongDescription());

        return s.getBytes();

    }

    /**
     * (non-Javadoc)
     * executeAction
     *
     * @see COM.ibm.eannounce.objects.PDGTemplateActionItem#executeAction(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile)
     */
    public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {

        ExtractActionItem eaItem = null;
        EntityItem[] eiParm = new EntityItem[1];
        EntityList el = null;
        EntityGroup eg = null;

        String strTraceBase = " MSOFFERINGPDG executeAction method ";
        m_SBREx = new SBRException();
        String strData = "";
        resetVariables();
        try {
            _db.debug(D.EBUG_DETAIL, strTraceBase);
            if (m_eiPDG == null) {
                D.ebug(D.EBUG_SPEW,"PDG entity is null");
                return;
            }

            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

            eaItem = new ExtractActionItem(null, _db, _prof, "EXTPDGENTFMR6");
            eiParm[0] =  m_eiPDG;
            _prof = m_utility.setProfValOnEffOn(_db, _prof);
            el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
            eg = el.getParentEntityGroup();
            m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
			// get WG
			eg = el.getEntityGroup(ENTITY_CATNAV);
			if (eg != null) {
				if (eg.getEntityItemCount() > 0) {
					m_eiCATNAV = eg.getEntityItem(0);
				}
			}
			_db.test(m_eiCATNAV != null, "CATNAV  entity is null");

//            checkPDGAttribute(_db, _prof, m_eiPDG);
            // validate data
//            checkDataAvailability(_db, _prof, m_eiPDG);
            if (m_SBREx.getErrorCount() > 0) {
                m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
                throw m_SBREx;
            }

            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_RUNNING, "", getLongDescription());
            m_utility.resetActivities();
            m_sbActivities = new StringBuffer();
            m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
            strData = checkMissingData(_db, _prof, true).toString();
            m_sbActivities.append(strData);
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
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
		// search for LSEO
	//	StringBuffer sb = new StringBuffer();
    }
}
