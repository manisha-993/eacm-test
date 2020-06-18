// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: SWPRODINITIAL30APDG.java,v $
// Revision 1.25  2008/06/17 20:03:58  wendy
// MN35609432 finding wrong SWPRODSTRUCT
//
// Revision 1.24  2008/06/16 19:30:30  wendy
// MN35609290 fix revealed memory leaks, deref() needed
//
// Revision 1.23  2007/08/29 14:23:47  couto
// MN32841099 WGMODEL replaced by WGMODELA
//
// Revision 1.22  2006/05/16 00:08:11  joan
// changes
//
// Revision 1.21  2006/05/15 16:10:38  joan
// changes
//
// Revision 1.20  2006/05/08 21:25:14  joan
// add print trace
//
// Revision 1.19  2006/02/21 23:05:15  joan
// fixes
//
// Revision 1.18  2006/02/20 22:35:33  joan
// clean up system.out
//
// Revision 1.17  2005/02/17 16:58:05  joan
// work on CR
//
// Revision 1.16  2005/01/13 00:40:57  joan
// fixes for null billing template
//
// Revision 1.15  2005/01/10 21:47:50  joan
// work on multiple edit
//
// Revision 1.14  2005/01/10 18:55:30  joan
// fixes
//
// Revision 1.13  2005/01/10 18:32:21  joan
// fixes
//
// Revision 1.12  2005/01/06 20:47:26  joan
// fixes
//
// Revision 1.11  2005/01/05 19:24:09  joan
// add new method
//
// Revision 1.10  2004/11/12 20:44:59  joan
// adjust error messages
//
// Revision 1.9  2004/10/21 20:19:01  joan
// add catch MiddlewareRequest
//
// Revision 1.8  2004/10/11 22:10:31  joan
// fixes
//
// Revision 1.7  2004/10/05 16:05:02  joan
// fixes
//
// Revision 1.6  2004/09/03 20:20:13  joan
// fixes
//
// Revision 1.5  2004/09/03 18:14:06  joan
// fixes
//
// Revision 1.4  2004/08/26 20:46:16  joan
// fixes
//
// Revision 1.3  2004/08/25 22:38:20  joan
// fixes
//
// Revision 1.2  2004/08/25 19:51:21  joan
// fix error
//
// Revision 1.1  2004/08/25 19:44:51  joan
// add new pdgs
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
import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;

public class SWPRODINITIAL30APDG extends PDGActionItem {
	static final long serialVersionUID = 20011106L;

    private String m_strAfReqType = null;
    private String m_strAfOptFeature = "f00";
    private String m_strAfBillingTemplate = null;
    private String m_strAfMachType = null;
    private String m_strAfModel = null;
    private String m_strAfCreateMES = null;
    private String m_strAnnCodeName = null;
    private Vector m_vctReturnEntityKeys = new Vector();
	private EANList m_opList = new EANList();
    /*
    * Version info
    */
    public String getVersion() {
        return "$Id: SWPRODINITIAL30APDG.java,v 1.25 2008/06/17 20:03:58 wendy Exp $";
    }

    public SWPRODINITIAL30APDG(EANMetaFoundation  _mf, SWPRODINITIAL30APDG _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
    }

    public SWPRODINITIAL30APDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db,  _prof, _strActionItemKey);
    }

    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("SWPRODINITIAL30APDG:" + getKey() + ":desc:" + getLongDescription());
        strbResult.append(":purpose:" + getPurpose());
        strbResult.append(":entitytype:" + getEntityType() + "/n");
        return strbResult.toString();
    }

    public String getPurpose() {
        return "SWPRODINITIAL30APDG";
    }

    protected void generateDataII(Database _db, Profile _prof, StringBuffer _sbMissing, String _strSearchKey) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = "SWPRODINITIAL30APDG generateDataII ";
		try {
			StringTokenizer st = new StringTokenizer(_sbMissing.toString(),"\n");
			Hashtable ht = new Hashtable();
			while (st.hasMoreTokens()) {
				String s = st.nextToken();
				addDebug("Processing "+s);
				_db.debug(D.EBUG_SPEW,strTraceBase + s);
				StringTokenizer st1 = new StringTokenizer(s,"|");
				if (st1.hasMoreTokens()) {
					String strParentEntity = st1.nextToken().trim();
					int iLevel = Integer.parseInt(st1.nextToken());
					String strDirection = st1.nextToken().trim();
					addDebug("strParentEntity "+strParentEntity+" iLevel "+iLevel+" strDirection "+strDirection);
					// get parent for later links
					EntityItem eiParent = null;
					if (strParentEntity != null && strParentEntity.length() > 0) {
						StringTokenizer stParent = new StringTokenizer(strParentEntity,"-");
						if (stParent.hasMoreTokens()) {
							String strParentType = stParent.nextToken();
							int iParentID = Integer.parseInt(stParent.nextToken());
							addDebug("strParentType "+strParentType+" iParentID "+iParentID);
	 						eiParent = (EntityItem)ht.get((iLevel-1) + "");
							if (eiParent !=null){
								if(!eiParent.getKey().equals(strParentType+iParentID)){
									eiParent = null;  // wrong one
								}
								addDebug("parent from ht "+(eiParent==null?"null":eiParent.getKey()));
							}
							if (eiParent==null){ // try by key
								eiParent = (EntityItem)m_eiList.get(strParentType+iParentID);
								addDebug("parent from m_eilist using key "+(eiParent==null?"null":eiParent.getKey()));
							}
							if (eiParent==null){ // try by entitytype
								eiParent = (EntityItem)m_eiList.get(strParentType);
								if(eiParent!=null && !eiParent.getKey().equals(strParentType+iParentID)){
									eiParent = null;  // wrong one
								}
								addDebug("parent from m_eilist using type "+(eiParent==null?"null":eiParent.getKey()));
							}

							if (eiParent ==null) { // create it
								eiParent = new EntityItem(null, _prof, strParentType, iParentID);
								addDebug("parent create new "+eiParent.getKey());
							}
						}
					} else {
						eiParent = (EntityItem)ht.get((iLevel-1) + "");
						addDebug("parent from ht "+(eiParent==null?"null":eiParent.getKey()));
					}

					// get stuff for Entity
					String strEntity = st1.nextToken();
					int i1 = strEntity.indexOf(":");
					String strEntityType = strEntity;
					String strAttributes = "";
					if (i1 > -1 ){
						strEntityType = strEntity.substring(0, i1);
						strAttributes = strEntity.substring(i1+1);
					}

					String strAction = st1.nextToken();
					String strRelatorInfo = st1.nextToken();
					addDebug("strEntityType "+strEntityType+" strAttributes "+strAttributes+
        					" strAction "+strAction+" strRelatorInfo "+strRelatorInfo);

					//find the item if needed
					int iFind = strAction.indexOf("find");
					EntityItem currentEI = null;
					if (iFind > -1) {
						addDebug("Finding a "+strEntityType+" with this attribute list "+strAttributes);
						if (strAttributes.indexOf("map") >= 0) {
							int iEqual = strAttributes.indexOf("=");
							String strHead = strAttributes.substring(4, iEqual);
							currentEI = m_utility.findEntityItem(m_eiList, strEntityType, strAttributes);
							addDebug("find currentEI "+(currentEI==null?"null":currentEI.getKey()));
							if (currentEI == null) {
								String strSai = (String)m_saiList.get(strEntityType);
								EntityItem[] aei = null;
								if (strHead.indexOf(":") >= 0) {
									aei = m_utility.dynaSearchII(_db, _prof, m_eiPDG, strSai, strEntityType, strAttributes);
								} else {
									aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, strEntityType, strAttributes);
								}

								if (aei.length > 0) {
									currentEI = aei[0];
									for (int x=0; x<aei.length; x++){
										addDebug("search returned "+aei[x].getKey());
										aei[x]=null;
									}
								}
								aei = null;
								addDebug("after search currentEI "+(currentEI==null?"null":currentEI.getKey()));
							}
							if (currentEI != null) {
								ht.put(iLevel + "", currentEI);
								// save for later search
								m_eiList.put(currentEI);
							}
						}
					}

					// link them if there's command link
					int iLink = strAction.indexOf("linkParent");
					if (iLink > -1 && currentEI != null) {
						// use parent entity, relator,link

						if (eiParent != null) {
							OPICMList ol = null;
							if (strDirection.equals("U")) {
								EntityItem[] aei = {eiParent};
								addDebug("linking eiParent: "+eiParent.getKey()+" to currentEI "+currentEI.getKey());
								ol = m_utility.linkEntities(_db, _prof, currentEI, aei, strRelatorInfo);
							} else {
								EntityItem[] aei = {currentEI};
								addDebug("linking currentEI: "+currentEI.getKey()+" to eiParent "+eiParent.getKey());
								ol = m_utility.linkEntities(_db, _prof, eiParent, aei, strRelatorInfo);
							}

							if (ol !=null){
								for (int i=0; i < ol.size(); i++) {
									Object obj = ol.getAt(i);
									//_db.debug(D.EBUG_SPEW,strTraceBase + " obj: " + obj.toString());
									if (obj instanceof ReturnRelatorKey) {
										ReturnRelatorKey rrk = (ReturnRelatorKey)obj;
										String strRType = rrk.getEntityType();
										EntityGroup eg = m_utility.getEntityGroup(strRType);
										if (eg ==null) {
											eg = new EntityGroup(null, _db, _prof, strRType, "Edit", false);
										}
										_prof = m_utility.setProfValOnEffOn(_db, _prof);
										EntityItem ei = new EntityItem(eg, _prof, _db, rrk.getEntityType(), rrk.getReturnID());
										_db.debug(D.EBUG_SPEW,strTraceBase + " ei: " + ei.dump(false));
										m_eiList.put(ei);
									}
								}
							}else {
    							addDebug("Link error? ol was null");
    							_db.debug(D.EBUG_SPEW,strTraceBase + "linkParent OPICMList ol is null from link");
    						}
							// update attributes for found ei
							//OPICMList attList = m_utility.getAttributeListForUpdate(currentEI.getEntityType(), strAttributes);
							//m_utility.updateAttribute(_db, _prof, currentEI, attList);
							//attList = null;
						}
						continue;
					}

					// create the item
					int iCreate = strAction.indexOf("create");
					if (iCreate > -1) {
						addDebug("create a "+strEntityType+" with this attribute list "+strAttributes);
						String strRelatorType = "";
						int iAttrO = strRelatorInfo.indexOf("[");
						if (iAttrO > -1) {
							strRelatorType = strRelatorInfo.substring(0,iAttrO);
						} else {
							strRelatorType = strRelatorInfo;
						}

						if (eiParent == null) {
							eiParent = m_eiRoot;
						}
						addDebug("create action: eiParent "+(eiParent==null?"null":eiParent.getKey()));
						//prepare the list of attributes
						addDebug("Relator Type is :"+strRelatorType+" :Relator Info is "+strRelatorInfo);
						OPICMList attList = m_utility.getAttributeListForCreate(strEntityType, strAttributes, strRelatorInfo);
						String strCai = (String)m_caiList.get(strRelatorType);
						if (strDirection.equals("U")) {
							// create stand alone entity
							attList = m_utility.getAttributeListForCreate(strEntityType, strAttributes, "");
							strCai = (String)m_caiList.get(strEntityType);
						}
						currentEI = m_utility.createEntityByRST(_db, _prof, eiParent, attList, strCai, strRelatorType, strEntityType);
						addDebug("create action byRST: currentEI "+(currentEI==null?"null":currentEI.getKey()));

						_db.test(currentEI != null, strTraceBase + " ei is null for: " + s);
						ht.put(iLevel + "", currentEI);
						m_eiList.put(currentEI);

						if (strDirection.equals("U")) {
							// link to 1 level up
							EntityItem[] aei = {eiParent};
							addDebug("create action: linking eiParent "+eiParent.getKey()+" to "+currentEI.getKey());
							OPICMList ol = m_utility.linkEntities(_db, _prof, currentEI, aei, strRelatorInfo);
							//_db.debug(D.EBUG_SPEW,strTraceBase + " ol size: " + ol.size());
							if (ol !=null){
								for (int i=0; i < ol.size(); i++) {
									Object obj = ol.getAt(i);
									//_db.debug(D.EBUG_SPEW,strTraceBase + " obj: " + obj.toString());
									if (obj instanceof ReturnRelatorKey) {
										ReturnRelatorKey rrk = (ReturnRelatorKey)obj;
										String strRType = rrk.getEntityType();
										EntityGroup eg = m_utility.getEntityGroup(strRType);
										if (eg ==null) {
											eg = new EntityGroup(null, _db, _prof, strRType, "Edit", false);
										}
										_prof = m_utility.setProfValOnEffOn(_db, _prof);
										EntityItem ei = new EntityItem(eg, _prof, _db, rrk.getEntityType(), rrk.getReturnID());
										//_db.debug(D.EBUG_SPEW,strTraceBase + " ei: " + ei.dump(false));
										m_eiList.put(ei);
									}
								}
							} else {
								_db.debug(D.EBUG_SPEW,strTraceBase + "create OPICMList ol is null from link");
								addDebug("create action Link error? ol was null");
							}
						}

						// save entities for later link to Offering Project
						int iLinkRoot = strAction.indexOf("linkRoot");
						if (iLinkRoot > -1) {
							String strLinkRoot = strAction.substring(iLinkRoot);
							int iEnd = strLinkRoot.indexOf(";");
							if (iEnd > -1) {
								strLinkRoot=strLinkRoot.substring(0,iEnd);
							}
							int iU = strLinkRoot.indexOf("_");
							if (iU > -1) {
								String strRelator = strLinkRoot.substring(iU+1);
								if (m_opList.get(currentEI) == null) {
									m_vctReturnEntityKeys.addElement(new ReturnRelatorKey(strRelator, -1, m_eiRoot.getEntityType(), m_eiRoot.getEntityID(), currentEI.getEntityType(), currentEI.getEntityID(), true));
									m_opList.put(currentEI);
								}
							}
						}
					}
				}
			}

			if (m_vctReturnEntityKeys.size() > 0) {
				m_utility.link(_db, _prof, m_vctReturnEntityKeys);
				m_vctReturnEntityKeys.clear();
			}
		} catch (SBRException ex) {
			// link entities to Offering Project
			if (m_vctReturnEntityKeys.size() > 0) {
				m_utility.link(_db, _prof, m_vctReturnEntityKeys);
			}
			throw ex;
		}
	}

    protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        //String strTraceBase = " SWPRODINITIAL30APDG checkMissingData method";
        StringBuffer sbReturn = new StringBuffer();
        EntityItem eiBaseCOF = (EntityItem)m_eiList.get("BASE");

        String strFileName = "";
        if (m_strAfBillingTemplate.equals("IPLA06")) {
            strFileName = "PDGtemplates/BillingTemplateIPLA06_2_30a.txt";
            if (m_strAfCreateMES != null && m_strAfCreateMES.equals("Yes")) {
                strFileName = "PDGtemplates/BillingTemplateIPLA06_1_30a.txt";
            }
        } else if (m_strAfBillingTemplate.equals("IPLA22")) {
            strFileName = "PDGtemplates/BillingTemplateIPLA22_30a.txt";
        } else if (m_strAfBillingTemplate.equals("IPLA25")) {
            strFileName = "PDGtemplates/BillingTemplateIPLA25_30a.txt";
        } else if (m_strAfBillingTemplate.equals("NOBILLING")) {
			return sbReturn;
		} else {
			strFileName = "PDGtemplates/BillingTemplate_30a.txt";
		}

		m_sbActivities.append("<TEMPLATEFILE>" + strFileName + "</TEMPLATEFILE>\n");

        OPICMList infoList = new OPICMList();
        infoList.put("PDG", m_eiPDG);
        infoList.put("WG", m_eiRoot);
        infoList.put("OFID", m_utility.getOptFeatIDAbr(m_strAfOptFeature));
		infoList.put("GEOIND", "GENAREASELECTION");

        _prof = m_utility.setProfValOnEffOn(_db, _prof);
        TestPDGII pdgObject = new TestPDGII(_db, _prof, eiBaseCOF, infoList, m_PDGxai, strFileName);
        StringBuffer sbMissing = pdgObject.getMissingEntities();
        pdgObject.dereference();
        pdgObject = null;
        infoList.clear();
        infoList = null;
        if (_bGenData) {
            generateDataII(_db, _prof, sbMissing,"");
        }

        sbReturn.append(sbMissing.toString());

        return sbReturn;
    }

    protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
        String strTraceBase = " SWPRODINITIAL30APDG checkPDGAttribute method";
        _db.debug(D.EBUG_SPEW, strTraceBase);
        for (int i =0; i < _afirmEI.getAttributeCount(); i++) {

            EANAttribute att = _afirmEI.getAttribute(i);
            String textAtt = "";
            String sFlagAtt = "";
           // String sFlagClass = "";
            Vector mFlagAtt = new Vector();
            //int index = -1;
            if (att instanceof EANTextAttribute) {
                textAtt = ((String)att.get()).trim();
            } else if (att instanceof EANFlagAttribute) {
                if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
                    MetaFlag[] amf = (MetaFlag[])att.get();
                    for (int f=0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                            sFlagAtt = amf[f].getLongDescription().trim();
                            //sFlagClass = amf[f].getFlagCode().trim();
                            //index = f;
                            break;
                        }
                    }
                } else if (att instanceof MultiFlagAttribute) {
                    MetaFlag[] amf = (MetaFlag[])att.get();
                    for (int f=0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                            mFlagAtt.addElement(amf[f].getLongDescription().trim());
                        }
                    }
                }

            }

            if (att.getKey().equals("AFSWREQTYPE")) {
                m_strAfReqType = sFlagAtt;
            } else if (att.getKey().equals("OPTFEATUREID")) {
				m_SBREx = m_utility.checkOptFeatureIDFormat(textAtt, PDGUtility.OF_PRODUCT, true, m_SBREx);
                m_strAfOptFeature = textAtt;
            } else if (att.getKey().equals("GENAREASELECTION")) {
                m_SBREx = m_utility.checkGenAreaOverlap(mFlagAtt, m_SBREx);
            } else if (att.getKey().equals("AFBILLINGTEMPLATE")) {
                m_strAfBillingTemplate = sFlagAtt;
            } else if (att.getKey().equals("MACHTYPEATR")) {
                m_strAfMachType = sFlagAtt;
            } else if (att.getKey().equals("MODELATR")) {
                m_strAfModel = textAtt;
            } else if (att.getKey().equals("AFSWCREATEMES")) {
                m_strAfCreateMES = sFlagAtt;
            } else if (att.getKey().equals("GENAREASELECTION")) {
				m_SBREx = m_utility.checkGenAreaOverlap(mFlagAtt, m_SBREx);
			} else if (att.getKey().equals("ANNCODENAME")) {
				m_strAnnCodeName = sFlagAtt;
			}
        }
		if ( ! m_strAfReqType.equals("SWProduct")) {
			SBRException ex = new SBRException();
			ex.add(" Request Type:" + m_strAfReqType + ". This action item is for SW Product Request.");
			throw ex;
		}

		if (m_strAnnCodeName == null || m_strAnnCodeName.length() <=0) {
			m_SBREx.add("ANNCODENAME is required.");
		}

		if (m_strAfBillingTemplate == null || m_strAfBillingTemplate.length() <=0) {
			m_SBREx.add("BILLINGTEMPLATE is required.");
		}
    }

    protected void resetVariables() {
        m_strAfReqType = null;
        m_strAfOptFeature = "f00";
        m_strAfBillingTemplate = null;
        m_strAfMachType = null;
        m_strAfModel = null;
		m_strAfCreateMES = null;
        m_eiList = new EANList();
        m_opList = new EANList();
        m_vctReturnEntityKeys = new Vector();
        m_sbActivities = new StringBuffer();
        m_SBREx = new SBRException();
    }

    public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = " SWPRODINITIAL30APDG viewMissing method";

        _db.debug(D.EBUG_DETAIL, strTraceBase);
		resetVariables();
		if (m_eiPDG == null) {
			String s="PDG entity is null";
			return s.getBytes();
		}

		m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
		addDebug("SWPRODINITIAL30APDG viewMissing entered "+m_eiPDG.getKey());

		//_prof = m_utility.setProfValOnEffOn(_db, _prof);
        ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT1");
        EntityItem[] eiParm = {m_eiPDG};
        EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
        EntityGroup eg = el.getParentEntityGroup();
        m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
		eg = el.getEntityGroup("WG");
		if (eg != null) {
			if (eg.getEntityItemCount() > 0) {
				m_eiRoot = eg.getEntityItem(0);
			}
		}

        checkPDGAttribute(_db, _prof, m_eiPDG);

        // validate data
       	checkDataAvailability(_db, _prof, m_eiPDG);
        if (m_SBREx.getErrorCount() > 0) {
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
            throw m_SBREx;
        }

        String s = checkMissingData(_db, _prof,false).toString();
		if (!runBySPDG() && s.length() <= 0) {
			s = "Generating data is complete";
		}
		m_sbActivities.append(m_utility.getViewXMLString(s));
		m_utility.savePDGViewXML(_db, _prof, m_eiPDG, m_sbActivities.toString());
		m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_PASSED, "", getLongDescription());
		return s.getBytes();
    }

    public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = " SWPRODINITIAL30APDG executeAction method";
		resetVariables();
		String strData = "";
		EntityList el = null;
		try {
			_db.debug(D.EBUG_DETAIL, strTraceBase);

			if (m_eiPDG == null) {
				_db.debug(D.EBUG_SPEW,"PDG entity is null");
				return;
			}
			m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
			addDebug("SWPRODINITIAL30APDG executeAction entered "+m_eiPDG.getKey());

			//_prof = m_utility.setProfValOnEffOn(_db, _prof);
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

			ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT1");
			EntityItem[] eiParm = {m_eiPDG};
			el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
			EntityGroup eg = el.getParentEntityGroup();
			m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
			eg = el.getEntityGroup("WG");
			if (eg != null) {
				if (eg.getEntityItemCount() > 0) {
					m_eiRoot = eg.getEntityItem(0);
				}
			}
			_db.test(m_eiRoot != null, "Work Group entity is null");
			checkPDGAttribute(_db, _prof, m_eiPDG);

			// validate data
			checkDataAvailability(_db, _prof, m_eiPDG);
			if (m_SBREx.getErrorCount() > 0) {
				m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
				throw m_SBREx;
			}
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_RUNNING, "", getLongDescription());
			m_utility.resetActivities();

			strData = checkMissingData(_db, _prof,true).toString();
			m_sbActivities.append(m_utility.getActivities().toString());
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
		} catch (SBRException ex) {
			ex.printStackTrace();
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
			throw ex;
		} catch (MiddlewareException mex) {
			mex.printStackTrace();
			m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, mex.toString(), getLongDescription());
			throw mex;
		}finally{
			if (el != null){
				el.dereference();
				el = null;
			}
		}

		if (strData.length() <= 0) {
			if (!runBySPDG()) {
				m_SBREx.add("Generating data is complete.  No data created during this run.(ok)");
				throw m_SBREx;
			}
		} else {
			setDataCreated(true);
			if ( !(m_strAfBillingTemplate.equals("IPLA06")
					|| m_strAfBillingTemplate.equals("IPLA22")
					|| m_strAfBillingTemplate.equals("IPLA25")
					|| m_strAfBillingTemplate.equals("NOBILLING"))) {
				m_SBREx.add("Billing codes must be added manually. (ok)");
				throw m_SBREx;
			}
		}
    }

    protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		StringBuffer sb = new StringBuffer();
		// make sure the Base COMMERCIALOF for the MTM already exists
		sb.append("map_COFCAT=101;");
		sb.append("map_COFSUBCAT=127;");
		sb.append("map_COFGRP=150;");
		sb.append("map_COFSUBGRP=010;");
		sb.append("map_MACHTYPEATR=" + m_strAfMachType + ";");
		sb.append("map_MODELATR=" + m_strAfModel+ ";");

		String strSai = (String)m_saiList.get("MODEL");
		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		EntityItem[] aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());

		if (aeiCOM.length <= 0) {
			if (!runBySPDG()) {
				m_SBREx.add("The MODEL with classifications SW-Application-Base-N/A, MACHTYPE=" + m_strAfMachType + ", MODELATR=" + m_strAfModel+ " must be created before Initial MODEL.");
			}
		} else if (aeiCOM.length > 1) {
			m_SBREx.add("There are " + aeiCOM.length + " existing MODELs with classifications SW-Application-Base-N/A, MACHTYPE=" + m_strAfMachType + ", MODELATR=" + m_strAfModel);
		} else {
			m_eiList.put("BASE", aeiCOM[0]);
			EANAttribute att = (EANAttribute)aeiCOM[0].getAttribute("BILLINGTEMPLATE");
			if (att != null && att instanceof EANFlagAttribute) {
				MetaFlag[] amf = (MetaFlag[])att.get();
				for (int f=0; f < amf.length; f++) {
					if (amf[f].isSelected()) {
						String sFlagAtt = amf[f].getLongDescription().trim();
						if (!sFlagAtt.equals(m_strAfBillingTemplate)) {
							m_SBREx.add(" Billing Template on the request must match Billing Template on the Base MODEL " + sFlagAtt);
						}
						break;
					}
				}
			}
		}
    }
    private void addDebug(String msg){
        m_sbActivities.append("<DEBUG>"+msg+"\n</DEBUG>"+ NEW_LINE);
    }
}
