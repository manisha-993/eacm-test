//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EDITEVPPDG.java,v $
// Revision 1.9  2008/09/04 21:05:13  wendy
// Cleanup RSA warnings
//
// Revision 1.8  2006/02/20 21:39:46  joan
// clean up System.out.println
//
// Revision 1.7  2005/12/08 00:37:17  joan
// FIXES
//
// Revision 1.6  2005/11/16 21:03:48  joan
// fixes
//
// Revision 1.5  2005/11/16 20:57:58  joan
// fixes
//
// Revision 1.4  2005/10/12 18:03:09  joan
// fixes
//
// Revision 1.3  2005/10/11 19:07:53  joan
// fixes
//
// Revision 1.2  2005/10/11 18:20:02  joan
// add new method for PDG
//
// Revision 1.1  2005/10/07 22:41:03  joan
// new pdg
//


package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import java.util.Vector;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.transactions.OPICMList;


/**
 * EDITEVPPDG
 *
 */
public class EDITEVPPDG extends PDGActionItem {
    static final long serialVersionUID = 20011106L;
    private String m_strEPN =null;
    private String m_strVPN =null;
    private String m_strPPN =null;
    int m_iSteps = 1;


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
        return "$Id: EDITEVPPDG.java,v 1.9 2008/09/04 21:05:13 wendy Exp $";
    }

    /**
     * EDITEVPPDG
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public EDITEVPPDG(EANMetaFoundation _mf, EDITEVPPDG _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
		m_bCollectInfo = true;
		m_iCollectStep = 1;

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
    public EDITEVPPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _strActionItemKey);
		m_bCollectInfo = true;
		m_iCollectStep = 1;

    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("EDITEVPPDG:" + getKey() + ":desc:" + getLongDescription());
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
        return "EDITEVPPDG";
    }

	public void setPDGCollectInfo(PDGCollectInfoList _cl, EANMetaAttribute _meta, RowSelectableTable _eiRst) throws SBRException, MiddlewareException {
		if (_meta.getKey().equals("PDGCATPARTNUMBER_E") || _meta.getKey().equals("PDGCATPARTNUMBER_V") || _meta.getKey().equals("PDGCATPARTNUMBER_P")) {
			if (m_eiPDG != null) {
				m_InfoList = _cl;
				StringBuffer sb = new StringBuffer();
				boolean bFirst = true;

				for (int i=0; i < m_InfoList.getCollectInfoItemCount(); i++) {
					PDGCollectInfoItem pi = m_InfoList.getCollectInfoItem(i);
					if (pi.isSelected()) {
						String strDesc = pi.toString();
						sb.append((!bFirst? ",": "") + strDesc);
						bFirst = false;
						pi.setSelected(false);
					}
				}

				int r = _eiRst.getRowIndex(m_eiPDG.getEntityType() + ":" + _meta.getKey() + ":C");
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

	public void setPDGCollectInfo(PDGCollectInfoList _cl, int _iStep, RowSelectableTable _eiRst) throws SBRException, MiddlewareException {
		if (_iStep == 1) {
			if (m_eiPDG != null) {
				m_InfoList = _cl;
				StringBuffer sbE = new StringBuffer();
				StringBuffer sbV = new StringBuffer();
				StringBuffer sbP = new StringBuffer();

				boolean bFirstE = true;
				boolean bFirstV = true;
				boolean bFirstP = true;

				for (int i=0; i < m_InfoList.getCollectInfoItemCount(); i++) {
					PDGCollectInfoItem pi = m_InfoList.getCollectInfoItem(i);

					if (pi.isSelected()) {
						String strDesc = pi.toString();
						String strSecondItem = pi.getSecondItem();
						if (strSecondItem.equals("E")) {
							sbE.append((!bFirstE? ",": "") + strDesc);
							bFirstE = false;
						} else if (strSecondItem.equals("V")) {
							sbV.append((!bFirstV? ",": "") + strDesc);
							bFirstV = false;
						} else if (strSecondItem.equals("P")) {
							sbP.append((!bFirstP? ",": "") + strDesc);
							bFirstP = false;
						}

						pi.setSelected(false);
					}
				}

				int rE = _eiRst.getRowIndex(m_eiPDG.getEntityType() + ":PDGCATPARTNUMBER_E:C");
				if (rE >= 0 && rE < _eiRst.getRowCount()) {
					try {
						_eiRst.put(rE,1,sbE.toString());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

				int rV = _eiRst.getRowIndex(m_eiPDG.getEntityType() + ":PDGCATPARTNUMBER_V:C");
				if (rV >= 0 && rV < _eiRst.getRowCount()) {
					try {
						_eiRst.put(rV,1,sbV.toString());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				int rP = _eiRst.getRowIndex(m_eiPDG.getEntityType() + ":PDGCATPARTNUMBER_P:C");
				if (rP >= 0 && rP < _eiRst.getRowCount()) {
					try {
						_eiRst.put(rP,1,sbP.toString());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

			}
		}
	}

	private void getInfoList(Database _db, Profile _prof, EANMetaAttribute _meta) {
		String strTraceBase = "EDITEVPPDG getInfoList method ";
		D.ebug(D.EBUG_SPEW,strTraceBase + _meta.getKey());
		if (m_eiPDG != null) {
			try {
				if (m_eiCATNAV == null) {
					ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTPDGENTFMR5");
					EntityItem[] eiParm =  {m_eiPDG};
					_prof = m_utility.setProfValOnEffOn(_db, _prof);
					EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
					EntityGroup eg = el.getEntityGroup("CATNAV");
					if (eg != null) {
						if (eg.getEntityItemCount() > 0) {
							m_eiCATNAV = eg.getEntityItem(0);
						}
					}
					_db.test(m_eiCATNAV != null, "CATNAV  entity is null");
				}


				//get the offering

				EntityItem[] eiParm = {m_eiCATNAV};

				EntityList el = EntityList.getEntityList(_db, _prof, m_PDGxai, eiParm);

				//EntityGroup egParent = el.getParentEntityGroup();
				EntityGroup egMODEL = el.getEntityGroup("MODEL");
				EntityGroup egLSEO = el.getEntityGroup("LSEO");
				EntityGroup egLSEOBUNDLE = el.getEntityGroup("LSEOBUNDLE");


				m_InfoList = new PDGCollectInfoList(this, getProfile(), "");
				m_InfoList.setMatrix(false);
				m_InfoList.setColNames(new String[]{"Selected", "EVP Values"});

				for (int i=0; i < egMODEL.getEntityItemCount(); i++) {
					EntityItem eiMODEL = egMODEL.getEntityItem(i);
					String strDesc = m_utility.getAttrValue(eiMODEL, "MACHTYPEATR") + m_utility.getAttrValue(eiMODEL, "MODELATR");

					PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, _prof, false, eiMODEL.getKey(), eiMODEL.getKey(), strDesc);
 					pi.m_aColInfos = new String[] {"Selected", m_utility.getAttrValue(eiMODEL, "MACHTYPEATR") + m_utility.getAttrValue(eiMODEL, "MODELATR")};
					m_InfoList.putCollectInfoItem(pi);
				}

				for (int j=0; j < egLSEO.getEntityItemCount(); j++) {
					EntityItem eiLSEO = egLSEO.getEntityItem(j);
					String strDesc = m_utility.getAttrValue(eiLSEO, "SEOID");

					PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, _prof, false, eiLSEO.getKey(), eiLSEO.getKey() , strDesc);
					pi.m_aColInfos = new String[] {"Selected", m_utility.getAttrValue(eiLSEO, "SEOID")};
					m_InfoList.putCollectInfoItem(pi);
				}

				for (int k=0; k < egLSEOBUNDLE.getEntityItemCount(); k++) {
					EntityItem eiLSEOBUNDLE = egLSEOBUNDLE.getEntityItem(k);
					String strDesc = m_utility.getAttrValue(eiLSEOBUNDLE, "SEOID");
					PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, _prof, false, eiLSEOBUNDLE.getKey(), eiLSEOBUNDLE.getKey(), strDesc);
 					pi.m_aColInfos = new String[] {"Selected", m_utility.getAttrValue(eiLSEOBUNDLE, "SEOID")};
					m_InfoList.putCollectInfoItem(pi);
				}

		 	} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	public PDGCollectInfoList collectPDGInfo(Database _db, Profile _prof, EANMetaAttribute _meta) {
		//String strTraceBase = "VARSPECBIDPDG collectPDGInfo meta";
		if (_meta.getKey().equals("PDGCATPARTNUMBER_E") || _meta.getKey().equals("PDGCATPARTNUMBER_V") || _meta.getKey().equals("PDGCATPARTNUMBER_P")) {
			if (m_InfoList == null) {
				getInfoList(_db, _prof, _meta);
			}
		}

		return m_InfoList;
	}

	public String getStepDescription(int iStep) {
		if (iStep == 1) {
			return "Select E, V, P Part Numbers";
		}
		return "N/A";
	}

	public PDGCollectInfoList collectPDGInfo(Database _db, Profile _prof, int _iStep) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = "COPYMODELPOF01PDG collectPDGInfo";
		if (_iStep == 1) {
			if (m_eiPDG != null) {
			try {
				if (m_eiCATNAV == null) {
					ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTPDGENTFMR5");
					EntityItem[] eiParm =  {m_eiPDG};
					_prof = m_utility.setProfValOnEffOn(_db, _prof);
					EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
					EntityGroup eg = el.getEntityGroup("CATNAV");
					if (eg != null) {
						if (eg.getEntityItemCount() > 0) {
							m_eiCATNAV = eg.getEntityItem(0);
						}
					}
					_db.test(m_eiCATNAV != null, "CATNAV  entity is null");
				}


				//get the offering

				EntityItem[] eiParm = {m_eiCATNAV};

				EntityList el = EntityList.getEntityList(_db, _prof, m_PDGxai, eiParm);

				//EntityGroup egParent = el.getParentEntityGroup();
				EntityGroup egMODEL = el.getEntityGroup("MODEL");
				EntityGroup egLSEO = el.getEntityGroup("LSEO");
				EntityGroup egLSEOBUNDLE = el.getEntityGroup("LSEOBUNDLE");


				m_InfoList = new PDGCollectInfoList(this, getProfile(), "E, V, P Part Numbers");

				for (int i=0; i < egMODEL.getEntityItemCount(); i++) {
					EntityItem eiMODEL = egMODEL.getEntityItem(i);
					String strDesc = m_utility.getAttrValue(eiMODEL, "MACHTYPEATR") + m_utility.getAttrValue(eiMODEL, "MODELATR");

					PDGCollectInfoItem piE = new PDGCollectInfoItem(m_InfoList, _prof, false, strDesc, "E", strDesc);
 					m_InfoList.putCollectInfoItem(piE);

					PDGCollectInfoItem piV = new PDGCollectInfoItem(m_InfoList, _prof, false, strDesc, "V", strDesc);
 					m_InfoList.putCollectInfoItem(piV);

					PDGCollectInfoItem piP = new PDGCollectInfoItem(m_InfoList, _prof, false, strDesc, "P", strDesc);
 					m_InfoList.putCollectInfoItem(piP);

				}


				for (int j=0; j < egLSEO.getEntityItemCount(); j++) {
					EntityItem eiLSEO = egLSEO.getEntityItem(j);
					String strDesc = m_utility.getAttrValue(eiLSEO, "SEOID");

					PDGCollectInfoItem piE = new PDGCollectInfoItem(m_InfoList, _prof, false, strDesc, "E" , strDesc);
					m_InfoList.putCollectInfoItem(piE);

					PDGCollectInfoItem piV = new PDGCollectInfoItem(m_InfoList, _prof, false, strDesc, "V" , strDesc);
					m_InfoList.putCollectInfoItem(piV);

					PDGCollectInfoItem piP = new PDGCollectInfoItem(m_InfoList, _prof, false, strDesc, "P" , strDesc);
					m_InfoList.putCollectInfoItem(piP);

				}

				for (int k=0; k < egLSEOBUNDLE.getEntityItemCount(); k++) {
					EntityItem eiLSEOBUNDLE = egLSEOBUNDLE.getEntityItem(k);
					String strDesc = m_utility.getAttrValue(eiLSEOBUNDLE, "SEOID");
					PDGCollectInfoItem piE = new PDGCollectInfoItem(m_InfoList, _prof, false, strDesc, "E", strDesc);
 					m_InfoList.putCollectInfoItem(piE);

					PDGCollectInfoItem piV = new PDGCollectInfoItem(m_InfoList, _prof, false, strDesc, "V", strDesc);
 					m_InfoList.putCollectInfoItem(piV);

					PDGCollectInfoItem piP = new PDGCollectInfoItem(m_InfoList, _prof, false, strDesc, "P", strDesc);
 					m_InfoList.putCollectInfoItem(piP);

				}


		 	} catch (Exception ex) {
				ex.printStackTrace();
			}
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
        //String strTraceBase =  "EDITEVPPDG checkMissingData method ";
        StringBuffer sbReturn = new StringBuffer();

		//DatePackage dp = _db.getDates();
		//String strNow = dp.getNow();
		_prof = m_utility.setProfValOnEffOn(_db, _prof);
        OPICMList attList = new OPICMList();
        attList.put("CATPARTNUMBER_E", "CATPARTNUMBER_E=" + m_utility.getAttrValue(m_eiPDG, "PDGCATPARTNUMBER_E"));
        attList.put("CATPARTNUMBER_V", "CATPARTNUMBER_V=" + m_utility.getAttrValue(m_eiPDG, "PDGCATPARTNUMBER_V"));
        attList.put("CATPARTNUMBER_P", "CATPARTNUMBER_P=" + m_utility.getAttrValue(m_eiPDG, "PDGCATPARTNUMBER_P"));
        m_utility.updateAttribute(_db, _prof, m_eiCATNAV, attList);
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
           // String sFlagAtt = "";
          //  String sFlagClass = "";
            Vector mFlagAtt = new Vector();

          //  int index = -1;
            if (att instanceof EANTextAttribute) {
                textAtt = ((String) att.get()).trim();
            } else if (att instanceof EANFlagAttribute) {
                if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                        //    sFlagAtt = amf[f].getLongDescription().trim();
                       //     sFlagClass = amf[f].getFlagCode().trim();
                       //     index = f;
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

			if (att.getKey().equals("PDGCATPARTNUMBER_E")) {
				m_strEPN = textAtt;
			} else if (att.getKey().equals("PDGCATPARTNUMBER_V")) {
				m_strVPN = textAtt;
			} else if (att.getKey().equals("PDGCATPARTNUMBER_P")) {
				m_strPPN = textAtt;
			}
       }

        if (m_strEPN == null) {
            m_SBREx.add("E Part Number is blank.");
        }

        if (m_strVPN == null) {
            m_SBREx.add("V Part Number is blank.");
        }

        if (m_strPPN == null) {
            m_SBREx.add("P Part Number is blank.");
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

        String strTraceBase = " EDITEVPPDG viewMissingEntities method";

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

        checkPDGAttribute(_db, _prof, m_eiPDG);
        // validate data
        checkDataAvailability(_db, _prof, m_eiPDG);
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

        String strTraceBase = " EDITEVPPDG executeAction method ";
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

            eaItem = new ExtractActionItem(null, _db, _prof, "EXTPDGENTFMR5");
            eiParm[0] =  m_eiPDG;
            _prof = m_utility.setProfValOnEffOn(_db, _prof);
            el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
            eg = el.getParentEntityGroup();
            m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
			// get WG
			eg = el.getEntityGroup("CATNAV");
			if (eg != null) {
				if (eg.getEntityItemCount() > 0) {
					m_eiCATNAV = eg.getEntityItem(0);
				}
			}
			_db.test(m_eiCATNAV != null, "CATNAV  entity is null");

            checkPDGAttribute(_db, _prof, m_eiPDG);
            // validate data
            checkDataAvailability(_db, _prof, m_eiPDG);
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
