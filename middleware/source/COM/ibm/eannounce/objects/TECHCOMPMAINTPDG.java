//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TECHCOMPMAINTPDG.java,v $
// Revision 1.19  2008/09/08 17:32:47  wendy
// Cleanup RSA warnings
//
// Revision 1.18  2007/09/11 14:06:07  wendy
// MN32841099 prevent null parent
//
// Revision 1.17  2006/07/25 16:39:58  joan
// changes
//
// Revision 1.16  2006/04/28 18:12:55  joan
// fixes
//
// Revision 1.15  2006/04/28 16:21:33  joan
// fixes
//
// Revision 1.14  2006/04/28 16:13:25  joan
// fixes
//
// Revision 1.13  2006/03/04 17:52:53  joan
// fixes
//
// Revision 1.12  2006/03/04 16:59:11  joan
// fixes
//
// Revision 1.11  2005/08/29 19:10:56  joan
// add file
//
// Revision 1.10  2005/08/29 18:16:18  joan
// fixes
//
// Revision 1.9  2005/08/09 18:56:17  joan
// fixes
//
// Revision 1.8  2005/08/08 20:00:11  joan
// fixes
//
// Revision 1.7  2005/07/20 18:36:02  joan
// fixes
//
// Revision 1.6  2005/07/19 15:55:23  joan
// fixes
//
// Revision 1.5  2005/07/18 23:10:45  joan
// fixes
//
// Revision 1.4  2005/07/14 19:34:33  joan
// fixes
//
// Revision 1.3  2005/07/14 16:36:31  joan
// update code
//
// Revision 1.2  2005/07/13 22:04:12  joan
// fixes
//
// Revision 1.1  2005/07/12 21:02:07  joan
// initial load
//
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
 * TECHCOMPMAINTPDG
 *
 * @author David Bigelow
 * To REPLACE the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TECHCOMPMAINTPDG extends PDGActionItem {
    static final long serialVersionUID = 20011106L;


    private String m_strOP = null;
    private String m_strMT = null;
    private String m_strMODEL = null;
    private String m_strSM1MT = null;
    private String m_strSM2MT = null;
    private String m_strSM1MODEL = null;
    private String m_strSM2MODEL = null;
    private String m_strOS = null;
    private String m_strANNDATE = null;
    private EANList m_SM1List = new EANList();
    private EANList m_SM2List = new EANList();
    private EANList m_MODELList = new EANList();

    private static final String ADD = "OP01";
    private static final String REMOVE = "OP02";
    private static final String REPLACE = "OP03";
    private static final String ADDMATCH = "OP04";
    private static final String REMOVEMATCH = "OP05";
    private static final String UPDATEANNDATE = "OP07";


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
        return "$Id: TECHCOMPMAINTPDG.java,v 1.19 2008/09/08 17:32:47 wendy Exp $";
    }

    /**
     * TECHCOMPMAINTPDG
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public TECHCOMPMAINTPDG(EANMetaFoundation _mf, TECHCOMPMAINTPDG _ai) throws MiddlewareRequestException {
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
    public TECHCOMPMAINTPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
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
        strbResult.append("TECHCOMPMAINTPDG:" + getKey() + ":desc:" + getLongDescription());
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
        return "TECHCOMPMAINTPDG";
    }

    private StringBuffer add(Database _db, Profile _prof, EANList _SMList, EANList _MODELList, String _strFileName, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = "TECHCOMPMAINTPDG add method ";

        StringBuffer sbReturn = new StringBuffer();
        OPICMList infoList = null;
        EntityItem eiSM = null;
        EntityItem eiMODEL = null;
        EntityList el = null;
        EntityItem eiMODELCG = null;
        TestPDGII pdgObject = null;
        StringBuffer sbMissing = null;
        EntityGroup egMDLCGMDL = null;
        ExtractActionItem xaiMODELCG = new ExtractActionItem(null, _db, _prof, "EXTTECHCOMPMAINT2");
		_db.debug(D.EBUG_SPEW, strTraceBase+" m_eiPDG: "+m_eiPDG.getKey());

        for (int i = 0; i < _MODELList.size(); i++) {
            eiMODEL = (EntityItem) _MODELList.getAt(i);
			_db.debug(D.EBUG_SPEW, strTraceBase+" eiMODEL: "+eiMODEL.getKey());
            EntityItem[] aeip = {eiMODEL};
			eiMODELCG = null;
            for (int j = 0; j < _SMList.size(); j++) {
				if (eiMODELCG == null) {
					el = EntityList.getEntityList(_db, _prof, m_PDGxai, aeip);
					egMDLCGMDL = el.getEntityGroup("MDLCGMDL");
					if (egMDLCGMDL.getEntityItemCount() > 0) {
						_strFileName = "PDGtemplates/TECHCOMPMAIN02.txt";
						EntityItem eiMDLCGMDL = egMDLCGMDL.getEntityItem(0);
						_db.debug(D.EBUG_SPEW, strTraceBase+" eiMDLCGMDL: "+eiMDLCGMDL.getKey());
						//System.out.println(strTraceBase + "eiMDLCGMDL: " + eiMDLCGMDL.dump(false));
						eiMODELCG = (EntityItem)eiMDLCGMDL.getUpLink(0);
						_db.debug(D.EBUG_SPEW, strTraceBase+" eiMODELCG: "+eiMODELCG.getKey());
					}
				}

				eiSM = (EntityItem) _SMList.getAt(j);
				_db.debug(D.EBUG_SPEW, strTraceBase+" eiSM: "+(eiSM==null?"null":eiSM.getKey()));
                //System.out.println(strTraceBase + "eiSM: " + eiSM.getKey());
                infoList = new OPICMList();
                infoList.put("PDG", m_eiPDG);
                infoList.put("SM", eiSM);
                infoList.put("MODEL", eiMODEL);
                infoList.put("AD", m_utility.getAttrValue(m_eiPDG, "ANNDATE"));
                if (eiMODELCG != null) {
                	infoList.put("MODELCG", eiMODELCG);
				}
                _prof = m_utility.setProfValOnEffOn(_db, _prof);

				if (egMDLCGMDL != null && egMDLCGMDL.getEntityItemCount() <= 0) {
					//	Needs MODELCG-->MDLCGMDL-->MODEL but MODELCG does not exist so this
					// doesnt work at all, assumption in template that MODELCG exists and
					// MDLCGMDL should be found

					sbReturn.append("<MSG>Cannot Add Supported Model " + eiSM+ " to "+
						eiMODEL+". A Model Compatibility Group does not exist.</MSG>");
					return sbReturn;

                	//pdgObject = new TestPDGII(_db, _prof, eiMODEL, infoList, m_PDGxai, _strFileName );
				} else {
					pdgObject = new TestPDGII(_db, _prof, eiMODELCG, infoList, xaiMODELCG, _strFileName );
				}
                sbMissing = pdgObject.getMissingEntities();
				_db.debug(D.EBUG_DETAIL, strTraceBase+" sbmissing "+sbMissing);

                pdgObject = null;
                infoList = null;
                m_eiList = new EANList();
                m_eiList.put(eiSM);
                m_eiList.put(eiMODEL);
                if (_bGenData) {
                    m_bGetParentEIAttrs = true;
                    generateDataII(_db, _prof, sbMissing, "");
                }
                if (sbMissing.toString().length() > 0) {
					sbReturn.append("<MSG>Add Supported Model " + eiSM.toString() + "</MSG>");
				} else {
					sbReturn.append("<MSG>Supported Model " + eiSM.toString() + " already exists</MSG>");
				}

            }
        }
        return sbReturn;
    }

    /*private void remove(Database _db, Profile _prof, EANList _SMList, EANList _MODELList) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "TECHCOMPMAINTPDG remove method ";
        EntityItem[] aeip = new EntityItem[_MODELList.size()];
        _MODELList.copyTo(aeip);

        EntityList el = EntityList.getEntityList(_db, _prof, m_PDGxai, aeip);

		EntityGroup eg = el.getEntityGroup("MDLCGOSMDL");
		for (int i=0; i < eg.getEntityItemCount(); i++) {
			EntityItem ei = eg.getEntityItem(i);
			EntityItem eic = (EntityItem) ei.getDownLink(0);
			if (_SMList.get(eic.getKey()) != null) {
				EntityItem[] eip = {ei};
				EntityItem[] aeic = {eic};
				m_utility.removeLink(_db, _prof, el, eip, aeic, "MDLCGOSMDL");
			}
		}
    }

	private String getAnnDateValue(EntityItem _eiMODEL, EntityItem _eiSM) {
		String strTraceBase = "TECHCOMPMAINTPDG getAnnDateValue method ";
		StringBuffer sb = new StringBuffer();

		if (m_strANNDATE == null || m_strANNDATE.length() <= 0) {
			String strMDate = m_utility.getAttrValue(_eiMODEL, "ANNDATE");
			String strSMDate = m_utility.getAttrValue(_eiSM, "ANNDATE");
			//System.out.println(strTraceBase + " strMDate: " + strMDate + ", strSMDate: " + strSMDate);
			if (strMDate.length() > 0 && strSMDate.length() <= 0) {
				sb.append(strMDate);
			} else if (strMDate.length() <= 0 && strSMDate.length() > 0) {
				sb.append(strSMDate);
			} if (strMDate.length() > 0 && strSMDate.length() > 0) {
				if (m_utility.dateCompare(strMDate, strSMDate) == m_utility.LATER) {
					sb.append(strMDate);
				} else {
					sb.append(strSMDate);
				}
			} else {
				sb.append(" ");
			}
		} else {
			sb.append(m_strANNDATE);
		}

		return sb.toString();
	}*/

    /**
     * (non-Javadoc)
     * checkMissingData
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#checkMissingData(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, boolean)
     */
    protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
      //  String strTraceBase =  "TECHCOMPMAINTPDG checkMissingData method ";
        StringBuffer sbReturn = new StringBuffer();
        EntityList el = null;
        EntityGroup eg = null;

        if (m_strOP.equals(ADD)) { // add PRODSTRUCT
            sbReturn = add(_db, _prof, m_SM1List, m_MODELList, "PDGtemplates/TECHCOMPMAIN01.txt", _bGenData);
        } else if (m_strOP.equals(REMOVE)) { // remove PRODSTRUCT
			EntityItem[] aeiMODEL = new EntityItem[m_MODELList.size()];
			m_MODELList.copyTo(aeiMODEL);

			el = EntityList.getEntityList(_db, _prof, m_PDGxai, aeiMODEL);
			eg = el.getEntityGroup("MDLCGOSMDL");
			boolean bRemove = false;
			for (int i=0; i < eg.getEntityItemCount(); i++) {
				EntityItem ei = eg.getEntityItem(i);
				EntityItem eip = (EntityItem) ei.getUpLink(0);
				EntityItem eic = (EntityItem) ei.getDownLink(0);
				String strOS = m_utility.getAttrValue(eip, "OS");
				if (m_SM1List.get(eic.getKey()) != null && strOS.equals(m_strOS)) {
					EntityItem[] aeip = {eip};
					EntityItem[] aeic = {eic};
					m_utility.removeLink(_db, _prof, el, aeip, aeic, "MDLCGOSMDL");
					sbReturn.append("<MSG>Remove Supported Model " + eic.toString() + "</MSG>");
					bRemove = true;
				}
			}

			if (!bRemove) {
				sbReturn.append("<MSG>Supported Model not removed." + "</MSG>");
			}
		} else if (m_strOP.equals(REPLACE)) { // replace
			boolean bReplace = false;
			for (int m=0; m < m_MODELList.size(); m++) {
				EntityItem eiMODEL = (EntityItem) m_MODELList.getAt(m);
				EntityItem[] aeiMODEL = {eiMODEL};

				el = EntityList.getEntityList(_db, _prof, m_PDGxai, aeiMODEL);
				eg = el.getEntityGroup("MDLCGOSMDL");

				for (int i=0; i < eg.getEntityItemCount(); i++) {
					EntityItem ei = eg.getEntityItem(i);
					EntityItem eip = (EntityItem) ei.getUpLink(0);
					EntityItem eic = (EntityItem) ei.getDownLink(0);
					String strOS = m_utility.getAttrValue(eip, "OS");
					if (m_SM1List.get(eic.getKey()) != null && strOS.equals(m_strOS)) {
						EntityItem[] aeip = {eip};
						EntityItem[] aeic = {eic};
						m_utility.removeLink(_db, _prof, el, aeip, aeic, "MDLCGOSMDL");
						bReplace = true;
						// add
						for (int j=0; j < m_SM2List.size(); j++) {
							EntityItem eiSM2 = (EntityItem)m_SM2List.getAt(j);
							EntityItem[] aeiSM2 = {eiSM2};
							//String strRelatorInfo = "MDLCGOSMDL[ANNDATE=" + getAnnDateValue(eiMODEL, eiSM2) + "]";
							String strRelatorInfo = "MDLCGOSMDL[ANNDATE=" + m_utility.getAttrValue(m_eiPDG, "ANNDATE") + "]";
							m_utility.linkEntities(_db, _prof, eip, aeiSM2, strRelatorInfo);
							sbReturn.append("<MSG>Add Supported Model " + eiSM2.toString() + "</MSG>");
						}
					}
				}
			}
			if (!bReplace) {
				sbReturn.append("<MSG>Supported Model not replaced." + "</MSG>");
			}
        } else if (m_strOP.equals(ADDMATCH)) { // add matching
        	boolean bAddMatch = false;
        	boolean bFindSM1 = false;
        	boolean bFindOS = false;
			for (int m=0; m < m_MODELList.size(); m++) {
				EntityItem eiMODEL = (EntityItem) m_MODELList.getAt(m);
				EntityItem[] aeiMODEL = {eiMODEL};

				el = EntityList.getEntityList(_db, _prof, m_PDGxai, aeiMODEL);
				eg = el.getEntityGroup("MDLCGOSMDL");
				for (int i=0; i < eg.getEntityItemCount(); i++) {
					EntityItem ei = eg.getEntityItem(i);
					EntityItem eip = (EntityItem) ei.getUpLink(0);
					EntityItem eic = (EntityItem) ei.getDownLink(0);
					String strOS = m_utility.getAttrValue(eip, "OS");
					if (m_SM1List.get(eic.getKey()) != null) {
						bFindSM1 = true;
					}

					if (strOS.equals(m_strOS)) {
						bFindOS = true;
					}

					if (m_SM1List.get(eic.getKey()) != null && strOS.equals(m_strOS)) {
						// add
						for (int j=0; j < m_SM2List.size(); j++) {
							EntityItem eiSM2 = (EntityItem)m_SM2List.getAt(j);
							EntityItem[] aeiSM2 = {eiSM2};
							String strRelatorInfo = "MDLCGOSMDL[ANNDATE=" + m_utility.getAttrValue(m_eiPDG, "ANNDATE") + "]";
							bAddMatch = true;
							m_utility.linkEntities(_db, _prof, eip, aeiSM2, strRelatorInfo);
							sbReturn.append("<MSG>Add Supported Model " + eiSM2.toString() + "</MSG>");
						}
					}
				}
			}

			if (!bAddMatch) {
				sbReturn.append("<MSG>Supported Model not added." + "</MSG>");
				if (!bFindSM1) {
					sbReturn.append("<MSG>SM1 doesn't link to any MODELCGOS." + "</MSG>");
				}

				if (!bFindOS) {
					sbReturn.append("<MSG>Unable to find matching OS." + "</MSG>");
				}
			}

        } else if (m_strOP.equals(REMOVEMATCH)) { // remove matching
        	boolean bRemoveMatch = false;
        	boolean bFindSM1 = false;
        	boolean bFindOS = false;

			EntityItem[] aeiSM2 = new EntityItem[m_SM2List.size()];
			m_SM2List.copyTo(aeiSM2);

			for (int m=0; m < m_MODELList.size(); m++) {
				EntityItem eiMODEL = (EntityItem) m_MODELList.getAt(m);
				EntityItem[] aeiMODEL = {eiMODEL};

				el = EntityList.getEntityList(_db, _prof, m_PDGxai, aeiMODEL);
				eg = el.getEntityGroup("MDLCGOSMDL");
				for (int i=0; i < eg.getEntityItemCount(); i++) {
					EntityItem ei = eg.getEntityItem(i);
					EntityItem eip = (EntityItem) ei.getUpLink(0);
					EntityItem eic = (EntityItem) ei.getDownLink(0);
					String strOS = m_utility.getAttrValue(eip, "OS");
					if (m_SM1List.get(eic.getKey()) != null) {
						bFindSM1 = true;
					}

					if (strOS.equals(m_strOS)) {
						bFindOS = true;
					}

					if (m_SM1List.get(eic.getKey()) != null && strOS.equals(m_strOS)) {
						EntityItem[] aeip = {eip};
						m_utility.removeLink(_db, _prof, el, aeip, aeiSM2, "MDLCGOSMDL");
						bRemoveMatch = true;
						sbReturn.append("<MSG>Remove Supported Model " + eic.toString() + "</MSG>");
					}
				}
			}
			if (!bRemoveMatch) {
				sbReturn.append("<MSG>Supported Model not removed." + "</MSG>");
				if (!bFindSM1) {
					sbReturn.append("<MSG>SM1 doesn't link to any MODELCGOS." + "</MSG>");
				}

				if (!bFindOS) {
					sbReturn.append("<MSG>Unable to find matching OS." + "</MSG>");
				}
			}

        } else if (m_strOP.equals(UPDATEANNDATE)) {
			EntityItem[] aeiMODEL = new EntityItem[m_MODELList.size()];
			m_MODELList.copyTo(aeiMODEL);

			el = EntityList.getEntityList(_db, _prof, m_PDGxai, aeiMODEL);
			eg = el.getEntityGroup("MDLCGOSMDL");
			for (int i=0; i < eg.getEntityItemCount(); i++) {
				EntityItem ei = eg.getEntityItem(i);
				OPICMList attList = new OPICMList();
			    attList.put("ANNDATE", "ANNDATE=" + m_utility.getAttrValue(m_eiPDG, "ANNDATE"));
			    m_utility.updateAttribute(_db, _prof, ei, attList);
			}
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
     //       String sFlagAtt = "";
            String sFlagClass = "";
            Vector mFlagAtt = new Vector();

     //       int index = -1;
            if (att instanceof EANTextAttribute) {
                textAtt = ((String) att.get()).trim();
            } else if (att instanceof EANFlagAttribute) {
                if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
     //                       sFlagAtt = amf[f].getLongDescription().trim();
                            sFlagClass = amf[f].getFlagCode().trim();
      //                      index = f;
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

            if (att.getKey().equals("PDGOPER02")) {
                m_strOP = sFlagClass;
            } else if (att.getKey().equals("AFMACHTYPEATR")) {
                m_strMT = textAtt;
            } else if (att.getKey().equals("AFMODELATR")) {
                m_strMODEL = textAtt;
            } else if (att.getKey().equals("PDGSM1MACHTYPEATR")) {
                m_strSM1MT = textAtt;
            } else if (att.getKey().equals("PDGSM2MACHTYPEATR")) {
                m_strSM2MT = textAtt;
            } else if (att.getKey().equals("PDGSM1MODELATR")) {
                m_strSM1MODEL = textAtt;
            } else if (att.getKey().equals("PDGSM2MODELATR")) {
                m_strSM2MODEL = textAtt;
            } else if (att.getKey().equals("OS")) {
                m_strOS = sFlagClass;
            } else if (att.getKey().equals("ANNDATE")) {
                m_strANNDATE = textAtt;
            }
        }

        if (m_strMT == null) {
            m_SBREx.add("Machine Type is empty.");
            return;
        }

        if (m_strMODEL == null) {
            m_SBREx.add("Model is empty.");
            return;
        }

        if (m_strSM1MT == null) {
            m_SBREx.add("SM1 Machine Type is empty.");
            return;
        }

        if (m_strSM1MODEL == null) {
            m_SBREx.add("SM1 Model is empty.");
            return;
        }

        if (m_strOP.equals(UPDATEANNDATE) && m_strANNDATE == null) {
            m_SBREx.add("ANNDATE is required for Update Announcement Date.");
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

        ExtractActionItem eaItem = null;
        EntityItem[] eiParm = new EntityItem[1];
        EntityList el = null;
        EntityGroup eg = null;
        String s = null;

        String strTraceBase = " TECHCOMPMAINTPDG viewMissingEntities method";

        _db.debug(D.EBUG_DETAIL, strTraceBase);
        m_SBREx = new SBRException();
        resetVariables();
        if (m_eiPDG == null) {
            s = "PDG entity is null";
            return s.getBytes();
        }
        _prof = m_utility.setProfValOnEffOn(_db, _prof);
        eaItem = new ExtractActionItem(null, _db, _prof, "EXTPDGENTFMR2");
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

        String strTraceBase = " TECHCOMPMAINTPDG executeAction method ";
        m_SBREx = new SBRException();
        String strData = "";
        resetVariables();
        try {
            _db.debug(D.EBUG_DETAIL, strTraceBase);
            if (m_eiPDG == null) {
                System.out.println("PDG entity is null");
                return;
            }

            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

            eaItem = new ExtractActionItem(null, _db, _prof, "EXTPDGENTFMR2");
            eiParm[0] =  m_eiPDG;
            _prof = m_utility.setProfValOnEffOn(_db, _prof);
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

        String[] aFlagCodes = null;
      //  EntityItem[] aeiMACHTYPE = null;
        EntityItem[] aeiMODEL = null;
        //EntityItem[] aeiFC = null;
        //EntityItem[] aeiFC2 = null;
        EntityItem[] aei = null;

        String strSai = null;

        //Vector vMACHTYPE = new Vector();
        Vector v = new Vector();
        StringBuffer sb = new StringBuffer();


		// search for MODEL
        strSai = (String) m_saiList.get("MODEL");
        aFlagCodes = m_utility.getFlagCodeForLikedDesc(_db, _prof, "MACHTYPEATR", m_strMT.replace('?', '_'));

        for (int i = 0; i < aFlagCodes.length; i++) {
			String strFlagCode = aFlagCodes[i];
            sb = new StringBuffer();
            sb.append("map_MACHTYPEATR=" + strFlagCode + ";");
            sb.append("map_MODELATR=" + m_strMODEL.replace('?', '_'));

            aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());
            if (aei != null && aei.length > 0) {
                for (int j = 0; j < aei.length; j++) {
                    v.addElement(aei[j]);
                }
            }
        }

        aeiMODEL = new EntityItem[v.size()];
        v.copyInto(aeiMODEL);
        if (aeiMODEL != null && aeiMODEL.length > 0) {
            for (int i = 0; i < aeiMODEL.length; i++) {
                m_MODELList.put(aeiMODEL[i]);
			}
        } else {
            m_SBREx.add("There are no MODELs for MACHTYPEATR=" + m_strMT + " and MODELATR=" + m_strMODEL + ".");
        }

		// search for supported MODEL 1
		strSai = "SRDMODEL6";
        aFlagCodes = m_utility.getFlagCodeForLikedDesc(_db, _prof, "MACHTYPEATR", m_strSM1MT.replace('?', '_'));
		v = new Vector();
        for (int i = 0; i < aFlagCodes.length; i++) {
            String strFlagCode = aFlagCodes[i];
            sb = new StringBuffer();
            sb.append("map_MACHTYPEATR=" + strFlagCode + ";");
            sb.append("map_MODELATR=" + m_strSM1MODEL.replace('?', '_'));

            aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());
            if (aei != null && aei.length > 0) {
                for (int j = 0; j < aei.length; j++) {
                    v.addElement(aei[j]);
                }
            }
        }

        aeiMODEL = new EntityItem[v.size()];
        v.copyInto(aeiMODEL);
        if (aeiMODEL != null && aeiMODEL.length > 0) {
            for (int i = 0; i < aeiMODEL.length; i++) {
                m_SM1List.put(aeiMODEL[i]);
            }
        } else {
            m_SBREx.add("There are no MODELs for MACHTYPEATR=" + m_strSM1MT + " and MODELATR=" + m_strSM1MODEL + ".");
        }

		if (m_strOP.equals(REPLACE)|| m_strOP.equals(ADDMATCH) || m_strOP.equals(REMOVEMATCH)) {
            if (m_strSM2MT == null) {
                m_SBREx.add("SM2 Machine Type is empty.");
                return;
            }

            if (m_strSM2MODEL == null) {
                m_SBREx.add("SM2 Model is empty.");
                return;
            }

			// search for supported MODEL 2
            aFlagCodes = m_utility.getFlagCodeForLikedDesc(_db, _prof, "MACHTYPEATR", m_strSM2MT.replace('?', '_'));
			v = new Vector();
            for (int i = 0; i < aFlagCodes.length; i++) {
                String strFlagCode = aFlagCodes[i];
                sb = new StringBuffer();
                sb.append("map_MACHTYPEATR=" + strFlagCode + ";");
                sb.append("map_MODELATR=" + m_strSM2MODEL.replace('?', '_'));

                aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());
                if (aei != null && aei.length > 0) {
                    for (int j = 0; j < aei.length; j++) {
                        v.addElement(aei[j]);
                    }
                }
            }

            aeiMODEL = new EntityItem[v.size()];
            v.copyInto(aeiMODEL);
            if (aeiMODEL != null && aeiMODEL.length > 0) {
                for (int i = 0; i < aeiMODEL.length; i++) {
                    m_SM2List.put(aeiMODEL[i]);
                }
            } else {
                m_SBREx.add("There are no MODELs for MACHTYPEATR=" + m_strSM2MT + " and MODELATR=" + m_strSM2MODEL + ".");
            }

		}
    }

}
