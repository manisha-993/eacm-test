// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: SWPROD30APDG.java,v $
// Revision 1.25  2008/07/08 12:34:47  wendy
// change debug msg
//
// Revision 1.24  2008/06/17 20:03:58  wendy
// MN35609432 finding wrong SWPRODSTRUCT
//
// Revision 1.23  2008/06/16 19:30:30  wendy
// MN35609290 fix revealed memory leaks, deref() needed
//
// Revision 1.22  2007/12/05 17:49:00  bala
// Add debug statements
//
// Revision 1.21  2007/08/29 14:22:22  couto
// MN32841099 WGMODEL replaced by WGMODELA
//
// Revision 1.20  2006/08/29 22:06:00  joan
// changes
//
// Revision 1.19  2006/05/15 16:10:38  joan
// changes
//
// Revision 1.18  2006/05/15 04:21:59  joan
// changes
//
// Revision 1.17  2006/05/08 21:25:14  joan
// add print trace
//
// Revision 1.16  2005/01/13 00:40:57  joan
// fixes for null billing template
//
// Revision 1.15  2005/01/10 18:55:29  joan
// fixes
//
// Revision 1.14  2005/01/10 18:32:21  joan
// fixes
//
// Revision 1.13  2004/11/12 20:44:59  joan
// adjust error messages
//
// Revision 1.12  2004/11/11 01:29:29  joan
// fixes
//
// Revision 1.11  2004/11/10 21:39:41  joan
// fixes
//
// Revision 1.10  2004/11/03 19:45:37  joan
// fixes
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
// Revision 1.2  2004/08/25 19:44:51  joan
// add new pdgs
//
// Revision 1.1  2004/08/20 17:24:59  joan
// add new pdg
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

public class SWPROD30APDG extends PDGActionItem {
    static final long serialVersionUID = 20011106L;

    private String m_strAfReqType = null;
    private String m_strAfSupplyTemplate = null;
    private String m_strAfOptFeature = "f00";
    private String m_strAfPreloadedSW = null;
    private String m_strAfBillingTemplate = null;
    private String m_strAfMachType = null;
    private String m_strAfModel = null;
    private Vector m_afOsLevelVec = new Vector();
    private Vector m_afOLVec = new Vector();
    private String m_strAfHIPOMACHTYPE = null;
    private String m_strAfHIPOMODEL = null;
    private String m_strAnnCodeName = null;
    private Vector m_vctReturnEntityKeys = new Vector();
    private EANList m_opList = new EANList();

    /*
     * Version info
     */
    public String getVersion() {
        return "$Id: SWPROD30APDG.java,v 1.25 2008/07/08 12:34:47 wendy Exp $";
    }

    public SWPROD30APDG(EANMetaFoundation _mf, SWPROD30APDG _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
    }

    /**
     * This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
     */
    public SWPROD30APDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException,
        MiddlewareRequestException {
        super(_emf, _db, _prof, _strActionItemKey);
    }

    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("SWPROD30APDG:" + getKey() + ":desc:" + getLongDescription());
        strbResult.append(":purpose:" + getPurpose());
        strbResult.append(":entitytype:" + getEntityType() + "/n");
        return strbResult.toString();
    }

    public String getPurpose() {
        return "SWPROD30APDG";
    }

    private void linkToHipo(Database _db, Profile _prof, EntityItem _ei) throws SQLException, MiddlewareException,
        MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "SWPROD30APDG linkToHipo ";
        //System.out.println(strTraceBase);
        EntityItem hipoCOF = (EntityItem) m_eiList.get("HIPO");

        // Find Hardware-FeatureCode-HipoSupply COFOOFMGMTGRP
        EntityItem eiMG = m_utility.findHWHipoMG(_db, _prof, hipoCOF);
        if (eiMG == null) {
            // create management group
            OPICMList attList = new OPICMList();
            attList.put("COFOOFMGMTGRP:COFOOFMGCAT", "COFOOFMGMTGRP:COFOOFMGCAT=101");
            attList.put("COFOOFMGMTGRP:COFOOFMGSUBCAT", "COFOOFMGMTGRP:COFOOFMGSUBCAT=144");
            attList.put("COFOOFMGMTGRP:COFOOFMGGRP", "COFOOFMGMTGRP:COFOOFMGGRP=187");
            attList.put("COFOOFMGMTGRP:COFOOFMGSUBGRP", "COFOOFMGMTGRP:COFOOFMGSUBGRP=010");
            attList.put("COFOOFMGMTGRP:COMNAME", "COFOOFMGMTGRP:COMNAME=Hipo Supply");

            String strCai = (String) m_caiList.get("COFOWNSOOFOMG");
            eiMG = m_utility.createEntityByRST(_db, _prof, hipoCOF, attList, strCai, "COFOWNSOOFOMG", "COFOOFMGMTGRP");

            _db.test(eiMG != null, strTraceBase + " Hardware-FeatureCode-HIPOSupply MG is null");
        }

        EntityItem[] aeiOof = {_ei};
        m_utility.linkEntities(_db, _prof, eiMG, aeiOof, "SWMODSTRUCT");
    }

    protected void generateDataII(Database _db, Profile _prof, StringBuffer _sbMissing, String _strSearchKey) throws SQLException,MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "SWPROD30APDG generateDataII ";
        _db.debug(D.EBUG_SPEW, strTraceBase);
        try {
            StringTokenizer st = new StringTokenizer(_sbMissing.toString(), "\n");
            Hashtable ht = new Hashtable();
            while (st.hasMoreTokens()) {
                String s = st.nextToken();
                addDebug("Processing "+s);
                StringTokenizer st1 = new StringTokenizer(s, "|");
                if (st1.hasMoreTokens()) {
                    String strParentEntity = st1.nextToken().trim();
                    int iLevel = Integer.parseInt(st1.nextToken());
                    String strDirection = st1.nextToken().trim();
                    addDebug("strParentEntity "+strParentEntity+" iLevel "+iLevel+" strDirection "+strDirection);
                    // get parent for later links
                    EntityItem eiParent = null;
                    if (strParentEntity != null && strParentEntity.length() > 0) {
                        StringTokenizer stParent = new StringTokenizer(strParentEntity, "-");
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
                    }
                    else {
                        eiParent = (EntityItem) ht.get( (iLevel - 1) + "");
                        addDebug("parent from ht "+(eiParent==null?"null":eiParent.getKey()));
                    }

                    // get stuff for Entity
                    String strEntity = st1.nextToken();
                    int i1 = strEntity.indexOf(":");
                    String strEntityType = strEntity;
                    String strAttributes = "";
                    if (i1 > -1) {
                        strEntityType = strEntity.substring(0, i1);
                        strAttributes = strEntity.substring(i1 + 1);
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
                                String strSai = (String) m_saiList.get(strEntityType);
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
                                // save for later search
                                m_eiList.put(currentEI);
                                ht.put(iLevel + "", currentEI);
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
                            }
                            else {
                                EntityItem[] aei = {currentEI};
                                addDebug("linking currentEI: "+currentEI.getKey()+" to eiParent "+eiParent.getKey());
                                ol = m_utility.linkEntities(_db, _prof, eiParent, aei, strRelatorInfo);
                            }
                            if (ol !=null){
	                            for (int i = 0; i < ol.size(); i++) {
	                                Object obj = ol.getAt(i);

	                                if (obj instanceof ReturnRelatorKey) {
	                                    ReturnRelatorKey rrk = (ReturnRelatorKey) obj;
	                                    String strRType = rrk.getEntityType();
	                                    EntityGroup eg = m_utility.getEntityGroup(strRType);
	                                    if (eg == null) {
	                                        eg = new EntityGroup(null, _db, _prof, strRType, "Edit", false);
	                                    }
	                                    //_prof = m_utility.setProfValOnEffOn(_db, _prof);
	                                    EntityItem ei = new EntityItem(eg, _prof, _db, rrk.getEntityType(), rrk.getReturnID());
	                                    addDebug("adding  ei to m_eilist: "+ei.getKey());
	                                    m_eiList.put(ei);
	                                }
	                            }
                            }else {
    							addDebug("Link error? ol was null");
    							_db.debug(D.EBUG_SPEW,strTraceBase + "linkParent OPICMList ol is null from link");
    						}
                        }

                        // link HipoSupply
                        int iHipo = strAction.indexOf("hipo");
                        if (iHipo > -1) {
                            linkToHipo(_db, _prof, currentEI);
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
                            strRelatorType = strRelatorInfo.substring(0, iAttrO);
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
                        String strCai = (String) m_caiList.get(strRelatorType);
                        if (strDirection.equals("U")) {
                            // create stand alone entity
                            attList = m_utility.getAttributeListForCreate(strEntityType, strAttributes, "");
                            strCai = (String) m_caiList.get(strEntityType);
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
                            if (ol !=null){
	                            for (int i = 0; i < ol.size(); i++) {
	                                Object obj = ol.getAt(i);

	                                if (obj instanceof ReturnRelatorKey) {
	                                    ReturnRelatorKey rrk = (ReturnRelatorKey) obj;
	                                    String strRType = rrk.getEntityType();
	                                    EntityGroup eg = m_utility.getEntityGroup(strRType);
	                                    if (eg == null) {
	                                        eg = new EntityGroup(null, _db, _prof, strRType, "Edit", false);
	                                    }
	                                    _prof = m_utility.setProfValOnEffOn(_db, _prof);
	                                    EntityItem ei = new EntityItem(eg, _prof, _db, rrk.getEntityType(), rrk.getReturnID());

	                                    m_eiList.put(ei);
	                                    // link HipoSupply
	                                    int iHipo = strAction.indexOf("hipo");
	                                    if (iHipo > -1) {
	                                        linkToHipo(_db, _prof, ei);
	                                    }
	                                }
	                            }
	                        }
                        } else {
							_db.debug(D.EBUG_SPEW,strTraceBase + "create OPICMList ol is null from link");
							addDebug("create action Link error? ol was null");
						}

                        // link HipoSupply
                        int iHipo = strAction.indexOf("hipo");
                        if (iHipo > -1) {
                            linkToHipo(_db, _prof, currentEI);
                        }

                        // save entities for later link
                        int iLinkRoot = strAction.indexOf("linkRoot");
                        if (iLinkRoot > -1) {
                            String strLinkRoot = strAction.substring(iLinkRoot);
                            int iEnd = strLinkRoot.indexOf(";");
                            if (iEnd > -1) {
                                strLinkRoot = strLinkRoot.substring(0, iEnd);
                            }
                            int iU = strLinkRoot.indexOf("_");
                            if (iU > -1) {
                                String strRelator = strLinkRoot.substring(iU + 1);
                                if (m_opList.get(currentEI) == null) {
                                    m_vctReturnEntityKeys.addElement(new ReturnRelatorKey(strRelator, -1, m_eiRoot.getEntityType(),
                                        m_eiRoot.getEntityID(), currentEI.getEntityType(), currentEI.getEntityID(), true));
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
            ht.clear();
            ht=null;
        }
        catch (SBRException ex) {
            // link entities to Offering Project
            if (m_vctReturnEntityKeys.size() > 0) {
                m_utility.link(_db, _prof, m_vctReturnEntityKeys);
            }
            throw ex;
        }
    }

    protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException,
        MiddlewareShutdownInProgressException, SBRException {
        StringBuffer sbReturn = new StringBuffer();
        StringBuffer sb = new StringBuffer();
        sb.append("map_COFCAT=101;");
        sb.append("map_COFSUBCAT=127;");
        sb.append("map_COFGRP=150;");
        sb.append("map_COFSUBGRP=010;");
        sb.append("map_MACHTYPEATR=" + m_strAfMachType + ";");
        sb.append("map_MODELATR=" + m_strAfModel);

        String strSai = (String) m_saiList.get("MODEL");

        String strFileName = "";
        if (m_strAfSupplyTemplate.equals("LP/LPO")) {
            strFileName = "PDGtemplates/SupplyTemplateLPLPO_30a.txt";
        } else if (m_strAfSupplyTemplate.equals("PRPQ")) {
            strFileName = "PDGtemplates/SupplyTemplatePRPQ_30a.txt";
        } else if (m_strAfSupplyTemplate.equals("V5/HIPO")) {
            if (m_strAfPreloadedSW != null && m_strAfPreloadedSW.equals("Yes")) {
                strFileName = "PDGtemplates/SupplyTemplateV5HIPO_2_30a.txt";
            } else {
                strFileName = "PDGtemplates/SupplyTemplateV5HIPO_1_30a.txt";
            }
        }
        m_sbActivities.append("<TEMPLATEFILE>" + strFileName + "</TEMPLATEFILE>\n");
        String ofid = m_utility.getOptFeatIDAbr(m_strAfOptFeature);

        m_eiBaseCOF = m_utility.findEntityItem(m_eiList, "MODEL", sb.toString());
        if (m_eiBaseCOF == null) {
        	addDebug("Using "+strSai+" to search for "+sb.toString());
            _prof = m_utility.setProfValOnEffOn(_db, _prof);
            EntityItem[] aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());
            if (aeiCOM != null && aeiCOM.length > 0) {
                m_eiBaseCOF = aeiCOM[0];
            }
        }

        addDebug("Base COMMERCIALOF found "+(m_eiBaseCOF==null?"null":m_eiBaseCOF.getKey()));
        OPICMList infoList = new OPICMList();
        for (int i = 0; i < m_afOsLevelVec.size(); i++) {
            String strOsLevel = (String) m_afOsLevelVec.elementAt(i);
            infoList.clear();
            infoList.put("PDG", m_eiPDG);
            infoList.put("WG", m_eiRoot);
            if (m_strAfHIPOMACHTYPE != null && m_strAfHIPOMODEL != null) {
                infoList.put("HIPOMACHTYPE", m_strAfHIPOMACHTYPE);
                infoList.put("HIPOMODEL", m_strAfHIPOMODEL);
            }

            if (m_strAfBillingTemplate != null) {
                if (m_strAfBillingTemplate.equals("IPLA22") || m_strAfBillingTemplate.equals("IPLA25")) {
                    infoList.put("MAXUSERS", "999");
                } else {
                    infoList.put("MAXUSERS", "0");
                }
            }

            infoList.put("OSLEVEL", (String) m_afOLVec.elementAt(i));
            infoList.put("OSLEVELDESC", strOsLevel);
            if (strOsLevel.length()>3){
            	infoList.put("OSLEVEL4C", strOsLevel.substring(0, 4));
            	infoList.put("OSLEVEL4TH", strOsLevel.charAt(3) + "");
            }else{
            	infoList.put("OSLEVEL4C", " ");
                infoList.put("OSLEVEL4TH", " ");
            }
            infoList.put("OFID", ofid);
            infoList.put("OFID2", m_strAfOptFeature.substring(m_strAfOptFeature.length() - 2));
            infoList.put("GEOIND", "GENAREASELECTION");
            _prof = m_utility.setProfValOnEffOn(_db, _prof);

            TestPDGII pdgObject = new TestPDGII(_db, _prof, m_eiBaseCOF, infoList, m_PDGxai, strFileName);
            StringBuffer sbMissing = pdgObject.getMissingEntities();
            pdgObject.dereference();
            pdgObject = null;

            if (_bGenData) {
                generateDataII(_db, _prof, sbMissing, "");
            }

            sbReturn.append(sbMissing.toString());
        }
        infoList.clear();
        infoList = null;
        return sbReturn;
    }

    private void checkOSLEVELUnique(Vector _v) {
        Hashtable ht = new Hashtable();
        for (int i = 0; i < _v.size(); i++) {
            String s = (String) _v.elementAt(i);
            String s1 = "@"; // allow for '--' to be selected, avoid StringIndexOutOfBoundsException
            if (s.length() > 3) {
                s1 = s.substring(3, 4);
            }
            if (ht.get(s1) == null) {
                ht.put(s1, s);
            } else {
                m_SBREx.add(" The 4th character of all selected OSLEVELs must be unique.");
            }
        }
        ht.clear();
    }

    protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException,
        MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
        //String strTraceBase = " SWPROD30APDG checkPDGAttribute method";
        for (int i = 0; i < _afirmEI.getAttributeCount(); i++) {
            EANAttribute att = _afirmEI.getAttribute(i);
            String textAtt = "";
            String sFlagAtt = "";
           // String sFlagClass = "";
            Vector mFlagAtt = new Vector();
            Vector mFlagClass = new Vector();

           // int index = -1;
            if (att instanceof EANTextAttribute) {
                textAtt = ( (String) att.get()).trim();
            }
            else if (att instanceof EANFlagAttribute) {
                if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                            sFlagAtt = amf[f].getLongDescription().trim();
             //               sFlagClass = amf[f].getFlagCode().trim();
             //               index = f;
                            break;
                        }
                    }
                }
                else if (att instanceof MultiFlagAttribute) {
                    MetaFlag[] amf = (MetaFlag[]) att.get();
                    for (int f = 0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                            mFlagAtt.addElement(amf[f].getLongDescription().trim());
                            mFlagClass.addElement(amf[f].getFlagCode().trim());
                        }
                    }
                }
            }

            if (att.getKey().equals("AFSWREQTYPE")) {
                m_strAfReqType = sFlagAtt;
            }
            else if (att.getKey().equals("AFSUPPLYTEMPLATE")) {
                m_strAfSupplyTemplate = sFlagAtt;
            }
            else if (att.getKey().equals("OPTFEATUREID")) {
                m_SBREx = m_utility.checkOptFeatureIDFormat(textAtt, PDGUtility.OF_PRODUCT, true, m_SBREx);
                m_strAfOptFeature = textAtt;
            }
            else if (att.getKey().equals("GENAREASELECTION")) {
                m_SBREx = m_utility.checkGenAreaOverlap(mFlagAtt, m_SBREx);
            }
            else if (att.getKey().equals("PRELOADEDSW")) {
                m_strAfPreloadedSW = sFlagAtt;
            }
            else if (att.getKey().equals("AFBILLINGTEMPLATE")) {
                m_strAfBillingTemplate = sFlagAtt;
            }
            else if (att.getKey().equals("MACHTYPEATR")) {
                m_strAfMachType = sFlagAtt;
            }
            else if (att.getKey().equals("MODELATR")) {
                m_strAfModel = textAtt;
            }
            else if (att.getKey().equals("OSLEVEL")) {
            	checkOSLEVELUnique(mFlagAtt);
                m_afOsLevelVec = mFlagAtt;
                m_afOLVec = mFlagClass;
            }
            else if (att.getKey().equals("HIPOMACHTYPE")) {
                m_strAfHIPOMACHTYPE = textAtt;
            }
            else if (att.getKey().equals("HIPOMODEL")) {
                m_strAfHIPOMODEL = textAtt;
            }
            else if (att.getKey().equals("ANNCODENAME")) {
                m_strAnnCodeName = sFlagAtt;
            }
        }

        if (!m_strAfReqType.equals("SWProduct")) {
            SBRException ex = new SBRException();
            ex.add(" Request Type:" + m_strAfReqType + ". This action item is for SW Product Request.");
            throw ex;
        }
        if (m_strAnnCodeName == null || m_strAnnCodeName.length() == 0) {
            m_SBREx.add("ANNCODENAME is required.");
        }
        if (m_strAfBillingTemplate == null || m_strAfBillingTemplate.length() == 0) {
            m_SBREx.add("BILLINGTEMPLATE is required.");
        }

    }

    protected void resetVariables() {
        m_strAfReqType = null;
        m_strAfSupplyTemplate = null;
        m_strAfOptFeature = "f00";
        m_strAfPreloadedSW = null;
        m_strAfBillingTemplate = null;
        m_strAfMachType = null;
        m_strAfModel = null;
        m_afOsLevelVec.clear();
        m_afOLVec.clear();
        m_strAfHIPOMACHTYPE = null;
        m_strAfHIPOMODEL = null;
        m_vctReturnEntityKeys = new Vector();
        m_eiList = new EANList();
        m_opList = new EANList();
        m_sbActivities = new StringBuffer();
        m_SBREx = new SBRException();
    }

    public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException,
        SBRException {
        String strTraceBase = " SWPROD30APDG viewMissingEntities method";
        _db.debug(D.EBUG_DETAIL, strTraceBase);

        resetVariables();
        if (m_eiPDG == null) {
            String s = "PDG entity is null";
            return s.getBytes();
        }
        m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
        addDebug("SWPROD30APDG viewMissing entered "+m_eiPDG.getKey());

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
        _db.test(m_eiRoot != null, "Work Group entity is null");

        checkPDGAttribute(_db, _prof, m_eiPDG);
        // validate data
        checkDataAvailability(_db, _prof, m_eiPDG);
        if (m_SBREx.getErrorCount() > 0) {
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
            throw m_SBREx;
        }

        String s = checkMissingData(_db, _prof, false).toString();
        if (!runBySPDG() && s.length() == 0) {
            s = "Generating data is complete";
        }
        m_sbActivities.append(m_utility.getViewXMLString(s));
        m_utility.savePDGViewXML(_db, _prof, m_eiPDG, m_sbActivities.toString());
        m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_PASSED, "", getLongDescription());
        return s.getBytes();
    }

    public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException,
        SBRException {
        String strTraceBase = " SWPROD30APDG executeAction method ";
        EntityList el =null;

        resetVariables();
        String strData = "";
        try {
            _db.debug(D.EBUG_DETAIL, strTraceBase);
            if (m_eiPDG == null) {
                System.out.println("PDG entity is null");
                return;
            }

            m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
            addDebug("SWPROD30APDG executeAction entered "+m_eiPDG.getKey());
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

            strData = checkMissingData(_db, _prof, true).toString();
            m_sbActivities.append(m_utility.getActivities().toString());
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
        }
        catch (SBRException ex) {
            ex.printStackTrace();
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
            throw ex;
        }
        catch (MiddlewareException mex) {
            mex.printStackTrace();
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, mex.toString(), getLongDescription());
            throw mex;
        }finally{
			if (el != null){
				el.dereference();
				el = null;
			}
		}

        if (strData.length() == 0) {
            if (!runBySPDG()) {
                m_SBREx.add("Generating data is complete.  No data created during this run.(ok)");
                throw m_SBREx;
            }
        }
        else {
            setDataCreated(true);
        }
    }

    protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException,
        MiddlewareShutdownInProgressException, SBRException {
        // find SW-HIPO-Initial COF
        if (m_strAfSupplyTemplate.equals("V5/HIPO") &&
        		(m_strAfPreloadedSW != null &&	m_strAfPreloadedSW.equals("Yes"))) {
            StringBuffer sb = new StringBuffer();
            sb.append("map_COFCAT=101;");
            sb.append("map_COFSUBCAT=125;");
            sb.append("map_COFGRP=151;");
            sb.append("map_COFSUBGRP=010;");
            sb.append("map_MACHTYPEATR=" + m_strAfHIPOMACHTYPE + ";");
            sb.append("map_MODELATR=" + m_strAfHIPOMODEL);
            String strSai = (String) m_saiList.get("MODEL");
            EntityItem[] aeiHipo = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());

            if (aeiHipo.length > 0) {
                EntityItem eiCOF = aeiHipo[0];
                m_eiList.put("HIPO", eiCOF);
            } else {
                m_SBREx.add("The SW-HIPO-Initial-N/A MODEL for " + m_strAfHIPOMACHTYPE + "-" + m_strAfHIPOMODEL + " doesn't exist.");
            }
        }
    }

    private void addDebug(String msg){
        m_sbActivities.append("<DEBUG>"+msg+"\n</DEBUG>"+ NEW_LINE);
    }
}
