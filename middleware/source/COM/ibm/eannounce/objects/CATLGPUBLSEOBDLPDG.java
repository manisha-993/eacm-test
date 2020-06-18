// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: CATLGPUBLSEOBDLPDG.java,v $
// Revision 1.2  2008/02/19 18:42:56  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2008/01/04 16:47:46  wendy
// MN33416775 handling of salesstatus chgs, split into derived classes
//
//

package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import java.util.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.OPICMList;

/**
 * CATLGPUBLSEOBDLPDG
 *
 */
public class CATLGPUBLSEOBDLPDG extends CATLGPUBPDG {
    static final long serialVersionUID = 20011106L;

    private static final String ATT_CATLGPUBBNDLLASTRUN = "CATLGPUBBNDLLASTRUN";
    private static final String ATT_BUNDLMKTGDESC = "BUNDLMKTGDESC";
    private static final String ATT_BUNDLPUBDATEMTRGT = "BUNDLPUBDATEMTRGT";
    private static final String ATT_BUNDLUNPUBDATEMTRGT = "BUNDLUNPUBDATEMTRGT";

    /**
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: CATLGPUBLSEOBDLPDG.java,v 1.2 2008/02/19 18:42:56 wendy Exp $";
    }

    /**
     * CATLGPUBLSEOBDLPDG
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public CATLGPUBLSEOBDLPDG(EANMetaFoundation _mf, CATLGPUBLSEOBDLPDG _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
    }

    /**
     * constructor from abr
     * @param _emf
     * @param _db
     * @param _prof
     * @param domains
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public CATLGPUBLSEOBDLPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String domains) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, domains);
    }

    /**
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("CATLGPUBLSEOBDLPDG:" + getKey() + ":desc:" + getLongDescription());
        strbResult.append(":purpose:" + getPurpose());
        strbResult.append(":entitytype:" + getEntityType() + "\n");
        return strbResult.toString();
    }

    /**
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return "CATLGPUBLSEOBDLPDG";
    }

    /**
    * checkCatlgpub
    *
    *@param _db    Database
    *@param _prof  Profile
    */
    protected void checkCatlgpub(Database _db, Profile _prof)
    throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
    {
        String strTraceBase =  "CATLGPUBLSEOBDLPDG.checkCatlgpub method ";

        String strCATLGCNTRY_OFFCOUNTRY = m_utility.getAttrValue(m_eiPDG, ATT_OFFCOUNTRY);
        addDebug(_db,D.EBUG_SPEW,strTraceBase,"Check LSEOBUNDLE entered for "+m_eiPDG.getKey()+" for offctry: "+strCATLGCNTRY_OFFCOUNTRY);

        // this is all changed LSEOBUNDLE, may not be for this offcountry!
        EntityItem[] aeiChangedLSEOBUNDLE = getChangedRoots(_db, _prof,"LSEOBUNDLE", "EXTCATLGPUBPDGBDLE2",ATT_CATLGPUBBNDLLASTRUN);

        if (aeiChangedLSEOBUNDLE == null || aeiChangedLSEOBUNDLE.length <= 0) {
            addDebug(_db,D.EBUG_INFO,strTraceBase ,"no Changed LSEOBUNDLE found for "+m_eiPDG.getKey()+" for offctry: "+strCATLGCNTRY_OFFCOUNTRY);
            return;
        }

        addDebug(_db,D.EBUG_INFO,strTraceBase,"number of changed LSEOBUNDLE: " + aeiChangedLSEOBUNDLE.length);

        ExtractActionItem xaiLSEOBUNDLEGAA = new ExtractActionItem(null, _db, _prof, "EXTLSEOBUNDLEGAA1");
        ExtractActionItem xaiLSEOBUNDLE = new ExtractActionItem(null, _db, _prof, "EXTCATLGPUBPDGBDLE1");

        // handle smaller subsets at a time
        Vector vaChanged = getChunksOfEntities(aeiChangedLSEOBUNDLE, m_iChunk);
        for (int v=0; v < vaChanged.size(); v++) {
            _db.debug(D.EBUG_INFO,strTraceBase + " at chunk #: " + v+" of total "+vaChanged.size());
            EntityItem[] aeiLSEOBUNDLE = (EntityItem[]) vaChanged.elementAt(v);

            EntityList elLSEOBUNDLE = EntityList.getEntityList(_db, _prof, xaiLSEOBUNDLE, aeiLSEOBUNDLE);
            EntityGroup egCATLGPUB = elLSEOBUNDLE.getEntityGroup("CATLGPUB");
            EntityGroup egLSEOBUNDLE = elLSEOBUNDLE.getParentEntityGroup();

            for (int i=0; i < egLSEOBUNDLE.getEntityItemCount(); i++) {
                EntityItem eiLSEOBUNDLE = egLSEOBUNDLE.getEntityItem(i);
 				if (!domainNeedsCatlgpub(eiLSEOBUNDLE)){ // LSEOBUNDLE must be in one of the listed domains
					continue;
				}
				String strAud = m_utility.getAttrValueDesc(eiLSEOBUNDLE, ATT_AUDIEN);
				String strCatAud = getCatAudValue(_db, _prof, strAud);
                //addDebug(_db,D.EBUG_SPEW,strTraceBase,"check " + eiLSEOBUNDLE.getKey()+" strCatAud: "+strCatAud);

				Vector catlgpubVct = getCatlgPubForCtry(eiLSEOBUNDLE,"LSEOBUNDLECATLGPUB", strCATLGCNTRY_OFFCOUNTRY);

                if (strCatAud == null || strCatAud.length() <= 0) {
  					addDebug(_db,D.EBUG_SPEW,strTraceBase,eiLSEOBUNDLE.getKey()+" has strCatAud: "+strCatAud+" seoid: "+
  						m_utility.getAttrValue(eiLSEOBUNDLE, ATT_SEOID)+" for strAud: "+strAud);
					// no audience match, but catlgpub may exist
					for (int e = 0; e<catlgpubVct.size(); e++) {
						EntityItem catlgpubitem = (EntityItem)catlgpubVct.elementAt(e);
						addDebug(_db,D.EBUG_INFO,strTraceBase,"Setting "+catlgpubitem.getKey()+" to Inactive because " + eiLSEOBUNDLE.getKey()+" doesnt have audience");
						setInactiveCATLGPUB(_db, _prof, catlgpubitem);
						m_processedList.put(catlgpubitem.getKey(), catlgpubitem.getKey());
					}
					catlgpubVct.clear();
					continue;
                }

                boolean bSpecBid=false;
                String strSPECBID = m_utility.getAttrValue(eiLSEOBUNDLE, "SPECBID");
                if (strSPECBID.equals("11458")) {
                    bSpecBid = true;
                }

                EntityItem eiAVAILPA = null;
                EntityItem eiAVAILLO = null;
                boolean bCheck3 =false;

                if (bSpecBid) {
                    String strCOUNTRYLIST = m_utility.getAttrValue(eiLSEOBUNDLE, ATT_COUNTRYLIST);
                    addDebug(_db,D.EBUG_SPEW,strTraceBase,eiLSEOBUNDLE.getKey()+" is SPECBID, checking "+eiLSEOBUNDLE.getKey()+".COUNTRYLIST: " +strCOUNTRYLIST);
                    if (strCOUNTRYLIST.indexOf(strCATLGCNTRY_OFFCOUNTRY) >= 0) {
                        bCheck3 = true;
                    }
                } else {
					Vector vAvail = getAllLinkedEntities(eiLSEOBUNDLE, "LSEOBUNDLEAVAIL", "AVAIL");
                    for (int j=0; j < vAvail.size(); j++) {
                        EntityItem eiAVAIL = (EntityItem)vAvail.elementAt(j);
                        String strAVAILTYPE = m_utility.getAttrValue(eiAVAIL, "AVAILTYPE");
                        String strCOUNTRYLIST = m_utility.getAttrValue(eiAVAIL, ATT_COUNTRYLIST);
                        addDebug(_db,D.EBUG_SPEW,strTraceBase,eiLSEOBUNDLE.getKey()+" is not SPECBID, checking "+eiAVAIL.getKey()+" COUNTRYLIST: " +
                            strCOUNTRYLIST+" AVAILTYPE " + strAVAILTYPE);
                        if (strCOUNTRYLIST.indexOf(strCATLGCNTRY_OFFCOUNTRY) >= 0) {
                            bCheck3 = true;
                            if (strAVAILTYPE.equals("146")) {
                                eiAVAILPA = eiAVAIL;
                            } else if (strAVAILTYPE.equals("149")) {
                                eiAVAILLO = eiAVAIL;
                            }

                        }
                    }
                    vAvail.clear();
                    _db.debug(D.EBUG_SPEW,strTraceBase + eiLSEOBUNDLE.getKey()+" eiAVAILPA: " + eiAVAILPA+" eiAVAILLO: "+eiAVAILLO);
                }

                if (bCheck3) {
                    String strFileName = "PDGtemplates/CATLGPUB03.txt";
                    //getting CATLGOR entity
					Vector vCATLGORId = getAllLinkedEntities(eiLSEOBUNDLE, "LSEOBUNDLECATLGOR", "CATLGOR");
                    EntityItem eiCATLGOR = null;
                    for (int j=0; j < vCATLGORId.size(); j++) {
                        EntityItem item = ((EntityItem)vCATLGORId.elementAt(j));
                        String strOrOFFCOUNTRY = m_utility.getAttrValue(item, ATT_OFFCOUNTRY);
                        if (strOrOFFCOUNTRY.indexOf(strCATLGCNTRY_OFFCOUNTRY) >= 0) {
                            eiCATLGOR = item;
                            addDebug(_db,D.EBUG_SPEW,strTraceBase,eiLSEOBUNDLE.getKey()+" CATLGOR override found "+eiCATLGOR.getKey());
                            break;
                        }
                    }

/*
Where LSEOBUNDLE.SPECBID = 'No' (11457) then
    PUBFROM - LSEOBUNDLE via relator LSEOBUNDLEAVAIL to AVAIL where AVAIL.AVAILTYPE = 'Planned Availability' (146). This AVAIL has EFFECTIVEDATE and COUNTRYLIST.
Set PUBFROM = EFFECTIVEDATE for each country in the COUNTRYLIST that is in CATLGCNTRY.OFFCOUNTRY.
    PUBTO - LSEOBUNDLE via relator LSEOBUNDLEAVAIL to AVAIL where AVAIL.AVAILTYPE = 'Last Order' (149), then this EFFECTIVEDATE is the PUBTO for those countries in this COUNTRYLIST.
Set PUBTO = EFFECTIVEDATE for each country in the COUNTRYLIST that is in CATLGCNTRY.OFFCOUNTRY.
Where LSEOBUNDLE.SPECBID = 'Yes' (11458) then
    PUBFROM, PUBTO, and COUNTRYLIST are taken from the LSEOBUNDLE BUNDLPUBDATEMTRGT, BUNDLUNPUBDATEMTRGT and COUNTRYLIST respectively.
.
PUBFROM/PUBTO overrides the prior derivation of PUBFROM & PUBTO.
If LSEOBUNDLE via relator LSEOBUNDLECATLGOR to CATLGOR exists where CATLGOR.OFFCOUNTRY matches the CATLGCNTRY, then set PUBFROM = CATLGOR.PUBFROM (if specified) for CATLGOR.OFFCOUNTRY = CATLGCNTRY.OFFCOUNTRY and set PUBTO = CATLGOR.PUBTO (if specified) for CATLGOR.OFFCOUNTRY = CATLGCNTRY.OFFCOUNTRY. This is an override.
*/
                    // get values for PUBFROM, PUBTO
                    String strPF = "";
                    String strPT = "";

                    if (bSpecBid) {
                        strPF = m_utility.getAttrValue(eiLSEOBUNDLE, ATT_BUNDLPUBDATEMTRGT);
                        strPT = m_utility.getAttrValue(eiLSEOBUNDLE, ATT_BUNDLUNPUBDATEMTRGT);
                        addDebug(_db,D.EBUG_SPEW,strTraceBase,eiLSEOBUNDLE.getKey()+" SPECBID strPF "+strPF+" strPT "+strPT);
                    } else {
                        if (eiAVAILPA != null) {
                            strPF = m_utility.getAttrValue(eiAVAILPA, ATT_EFFDATE);
                            addDebug(_db,D.EBUG_SPEW,strTraceBase,eiAVAILPA.getKey()+" AVAILPA strPF "+strPF);
                        }

                        if (eiAVAILLO != null) {
                            strPT = m_utility.getAttrValue(eiAVAILLO, ATT_EFFDATE);
                            addDebug(_db,D.EBUG_SPEW,strTraceBase,eiLSEOBUNDLE.getKey()+" "+eiAVAILLO.getKey()+" AVAILLO strPT "+strPT);
                        }
                    }

                    if (eiCATLGOR != null) {
                        if (m_utility.getAttrValue(eiCATLGOR, ATT_PF).length() > 0) {
                            strPF =  m_utility.getAttrValue(eiCATLGOR, ATT_PF);
                            addDebug(_db,D.EBUG_SPEW,strTraceBase,eiLSEOBUNDLE.getKey()+" "+eiCATLGOR.getKey()+" override strPF "+strPF);
                        }
                        if (m_utility.getAttrValue(eiCATLGOR, ATT_PT).length() > 0) {
                            strPT = m_utility.getAttrValue(eiCATLGOR, ATT_PT);
                            addDebug(_db,D.EBUG_SPEW,strTraceBase,eiLSEOBUNDLE.getKey()+" "+eiCATLGOR.getKey()+" override strPT "+strPT);
                        }
                    }

                    // get CATNEWOFF value
                    //If NOW() < BUNDLPUBDATEMTRGT + 30 days then "Yes" else "No"
                    String strDate1 = m_utility.getAttrValue(eiLSEOBUNDLE, ATT_BUNDLPUBDATEMTRGT);
                    String strCATNEWOFF = "No";
                    if (strDate1.length() > 0) {
                        int iDC2 = m_utility.dateCompare(strCurrentDate, m_utility.getDate(strDate1, 30));
                        if (iDC2 == PDGUtility.EARLIER) {
                            strCATNEWOFF = "Yes";
                        }
                    }

					// get all catlgpub by audience for this country
					Hashtable catlgpubTbl = getCatlgPubForCtryByAudience(_db, _prof, catlgpubVct);

					if (catlgpubTbl.size()==0 && doSearch()){
						//many needless loops here so avoid unless turned on
						StringBuffer sb = new StringBuffer();
						sb.append("map_OFFCOUNTRY=" +  strCATLGCNTRY_OFFCOUNTRY + ";");
						sb.append("map_CATSEOID=" +  m_utility.getAttrValue(eiLSEOBUNDLE, ATT_SEOID) + ";");
						sb.append("map_CATOFFTYPE=BUNDLE");

						String strSai =  (String) m_saiList.get("CATLGPUB");
						_prof = m_utility.setProfValOnEffOn(_db, _prof);

						// extract didnt pull them, try a search
						EntityItem[] aeiCATLGPUB = m_utility.dynaSearch(_db, _prof, null, strSai, "CATLGPUB", sb.toString());
						if (aeiCATLGPUB != null && aeiCATLGPUB.length > 0) {
							for (int a=0; a<aeiCATLGPUB.length; a++){
								verifyAudience(_db, _prof,aeiCATLGPUB[a], catlgpubTbl);
								aeiCATLGPUB[a] = null;
							}
							aeiCATLGPUB = null;
						}
					}

                    // find CATLGPUB for each audience
                    StringTokenizer stCatAud = new StringTokenizer(strCatAud, ",");
                    while (stCatAud.hasMoreTokens()) {
                        String strCatAudience = stCatAud.nextToken();

						// find CATLGPUB for this audience
						EntityItem eiCATLGPUB = (EntityItem)catlgpubTbl.get(strCatAudience);
						if (eiCATLGPUB== null) {
                            addDebug(_db,D.EBUG_SPEW,strTraceBase,eiLSEOBUNDLE.getKey()+" Creating new CATLGPUB for audience "+strCatAudience+" ctry "+strCATLGCNTRY_OFFCOUNTRY);
                            OPICMList infoList = new OPICMList();
                            infoList.put("PDG", m_eiPDG);
                            infoList.put("LSEOBUNDLE", eiLSEOBUNDLE);
                            infoList.put("WG", m_eiRoot);
                            infoList.put("AUDIENVALUE", strCatAudience);

                            infoList.put("PF", strPF);
                            infoList.put("PT", strPT);

                            //If COFCAT = 'Hardware'  and COFGRP = 'Base' then "Yes" else "No"
                            //infoList.put("CUSTIMIZEVALUE", "Yes"); MN33416775
                            infoList.put("CATNEWOFFVALUE", strCATNEWOFF);
                            infoList.put("UPDATEDBY", m_strUpdatedBy);
                            //  if MODEL. SYSIDUNIT = SIU-CPU (S00010) then "Lease" else "None"

							Vector lseoVct = getAllLinkedEntities(eiLSEOBUNDLE, "LSEOBUNDLELSEO", "LSEO");
							Vector wwseoVct = getAllLinkedEntities(lseoVct, "WWSEOLSEO", "WWSEO");
							Vector mdlVct = getAllLinkedEntities(wwseoVct, "MODELWWSEO", "MODEL");

                            String cofcat = "100";//default to hardware MN33416775
                            // determine HW, SW or SVC from BUNDLETYPE, it is F multiflag
                            //BUNDLETYPE    100 Hardware
                            //BUNDLETYPE    101 Software
                            //BUNDLETYPE    102 Service
                            String btype = m_utility.getAttrValue(eiLSEOBUNDLE, "BUNDLETYPE");
                            if (btype.length()==0){ // default to hw
                                cofcat = "100";
                            }else{
                                if (btype.indexOf("100") != -1){ // if HW selected, use that
                                    cofcat = "100";
                                }else if (btype.indexOf("101") != -1){ // if SW selected, use that
                                    cofcat = "101";
                                }else{
                                    cofcat = "102";
                                }
                            }

                            addDebug(_db,D.EBUG_SPEW,strTraceBase,eiLSEOBUNDLE.getKey()+" using cofcat:"+cofcat+" for btype: "+btype);
/*
if LSEOBUNDLE ? LSEO --> WWSEO --> MODEL.COFCAT = 'Hardware'  and MODEL.COFGRP = 'Base' then
    if MODEL. SYSIDUNIT = SIU-CPU (S00010) then "Lease"
    else "None"
otherwise 'None'
*/
                            String catratevalue = "NONE";
                            if (mdlVct.size() > 0) {
                                // find the HW and Base model if it exists
                                for (int m = 0; m<mdlVct.size(); m++){
                                    EntityItem eiMODEL = (EntityItem)mdlVct.elementAt(m);
                                    if (m_utility.getAttrValue(eiMODEL, ATT_COFGRP).equals("150") // must be group=Base and category=hw
                                        && m_utility.getAttrValue(eiMODEL, ATT_COFCAT).equals("100")) {
                                        addDebug(_db,D.EBUG_SPEW,strTraceBase,eiLSEOBUNDLE.getKey()+" Found hw base parent "+eiMODEL.getKey());
                                        //  if MODEL. SYSIDUNIT = SIU-CPU (S00010) then "Lease" else "None"
                                        if (m_utility.getAttrValue(eiMODEL, ATT_SYSIDUNIT).equals("S00010")) {
                                            catratevalue = "LEASE";
                                        }
                                        break;
                                    }
                                }
                            }
							lseoVct.clear();
							wwseoVct.clear();
							mdlVct.clear();


                            infoList.put("CATRATETYPEVALUE", catratevalue);

                            // getting values from Sales Status
                            // getting GENERALAREA based on ASSOCIATION
							//for LSEOBUNDLE - use LSEOBUNDLEGAA from LSEOBUNDLE.COUNTRYLIST
							String seoid = m_utility.getAttrValue(eiLSEOBUNDLE, ATT_SEOID);
							String strMtrnCodes = getSalesStatus(_db, _prof, strCATLGCNTRY_OFFCOUNTRY,
								xaiLSEOBUNDLEGAA, eiLSEOBUNDLE, eiLSEOBUNDLE, seoid);

                            // load in buyable, Hide, Cart
                            infoList.put("SALESSTATUS", strMtrnCodes);
                            //MN33416775
                            //infoList.put("BUYABLEVALUE", strBuyable);
                            //infoList.put("HIDEVALUE", strHide);
                            //infoList.put("CARTVALUE", strCart);
                            String priced = "yes"; // MN33416775 LSEOBUNDLE assumed to be Priced
                            String sskey = cofcat+":"+priced+":"+strSPECBID;
                            SalesStatusInfo sshbac = SalesStatusInfo.getSalesStatusInfo(strMtrnCodes,sskey);//MN33416775
                            addDebug(_db,D.EBUG_SPEW,strTraceBase,eiLSEOBUNDLE.getKey()+" sskey: " +strMtrnCodes+":"+ sskey+" returned "+sshbac);

                            infoList.put("CUSTIMIZEVALUE", sshbac.getCustimize()); //MN33416775
                            infoList.put("CARTVALUE", sshbac.getAddToCart()); //MN33416775
                            infoList.put("BUYABLEVALUE", sshbac.getBuyable()); //MN33416775
                            infoList.put("HIDEVALUE", sshbac.getHide()); //MN33416775

                            _prof = m_utility.setProfValOnEffOn(_db, _prof);
                            TestPDGII pdgObject = new TestPDGII(_db, _prof, eiLSEOBUNDLE, infoList, strFileName );
                            StringBuffer sbMissing = pdgObject.getMissingEntities();
                            addDebug(_db,D.EBUG_SPEW,strTraceBase,"creating CATLGPUB for " + eiLSEOBUNDLE.getKey());

                            m_savedEIList = new EANList();
                            generateDataII(_db, _prof, sbMissing, "");
                            for (int e=0; e < m_savedEIList.size(); e++) {
                                EntityItem ei = (EntityItem)m_savedEIList.getAt(e);
                                if (ei.getEntityType().equals("CATLGPUB")) {
									m_processedList.put(ei.getKey(), ei.getKey());
                                    addDebug(_db,D.EBUG_SPEW,strTraceBase,eiLSEOBUNDLE.getKey()+" Created " + ei.getKey()+" using "+infoList.toString());
                                }
                            }

                            pdgObject = null;
                            infoList = null;
                        } else {
                            // update attributes CATNEWOFF, PUBFROM, PUBTO, CATLGMKTGDESC, Set CATWORKFLOW to 'Change' if updated.
                            String strPROJCDNAM = m_utility.getAttrValue(eiLSEOBUNDLE, ATT_PROJCDNAM);
                            String strMODMKTGDESC = m_utility.getAttrValue(eiLSEOBUNDLE, ATT_BUNDLMKTGDESC);
                            OPICMList attL = new OPICMList();

							if (!m_utility.getAttrValue(eiCATLGPUB, ATT_PROJCDNAM).equals(strPROJCDNAM)) {
								attL.put(ATT_PROJCDNAM, ATT_PROJCDNAM + "=" + strPROJCDNAM);
							}

							if (!m_utility.getAttrValue(eiCATLGPUB, ATT_CATNEWOFF).equals(strCATNEWOFF)) {
								attL.put(ATT_CATNEWOFF, ATT_CATNEWOFF + "=" + strCATNEWOFF);
							}
							if (!m_utility.getAttrValue(eiCATLGPUB, ATT_PF).equals(strPF)) {
								attL.put(ATT_PF, ATT_PF + "=" + strPF);
							}
							if (!m_utility.getAttrValue(eiCATLGPUB, ATT_PT).equals(strPT)) {
								attL.put(ATT_PT, ATT_PT + "=" + strPT);
							}

							if (!m_utility.getAttrValue(eiCATLGPUB, ATT_CATLGMKTGDESC).equals(strMODMKTGDESC)) {
								attL.put(ATT_CATLGMKTGDESC, ATT_CATLGMKTGDESC + "=" + strMODMKTGDESC);
							}
							/*
							-   if PUBFROM - 15 Days < NOW() ' logical OR'
							LSEOBUNDLE.STATUS =  Ready for Review (0040) '
							logical OR' LSEOBUNDLE.STATUS =  Final (0020), then if
							o   Set CATLGPUB.CATACTIVE = 'Active' (Active)
							else
							o   Set CATLGPUB.CATACTIVE = 'Inctive' (Inctive)
							*/

							if ( (m_utility.dateCompare(strPF, m_utility.getDate(strCurrentDate, 15)) == PDGUtility.EARLIER)
								|| m_utility.getAttrValue(eiLSEOBUNDLE, ATT_STATUS).equals("0040")
								|| m_utility.getAttrValue(eiLSEOBUNDLE, ATT_STATUS).equals("0020") ) {
								if (!m_utility.getAttrValue(eiCATLGPUB, "CATACTIVE").equals("Active")) {
									attL.put("CATACTIVE", "CATACTIVE=Active");
								}
							} else {
								if (!m_utility.getAttrValue(eiCATLGPUB, "CATACTIVE").equals("InActive")) {
									attL.put("CATACTIVE", "CATACTIVE=InActive");
								}
							}

							if (attL.size() > 0) {
								String strWorkFlow = m_utility.getAttrValue(eiCATLGPUB, ATT_CATWORKFLOW);
								if (strWorkFlow.equals("Override")) {
									attL.put(ATT_CATWORKFLOW, "CATWORKFLOW=SalesStatusOverride");
								} else if (strWorkFlow.equals("Accept")) {
									attL.put(ATT_CATWORKFLOW, "CATWORKFLOW=Change");
								} else if (strWorkFlow.equals("New")) {
									attL.put(ATT_CATWORKFLOW, "CATWORKFLOW=Change");
								}

								updateCatlgpub(_db, _prof, eiCATLGPUB, attL);

								if (doSearch() && egCATLGPUB.getEntityItem(eiCATLGPUB.getKey()) == null) {
									EntityItem[] aeiChild = {eiCATLGPUB};
									m_utility.linkEntities(_db, _prof, eiLSEOBUNDLE, aeiChild, "LSEOBUNDLECATLGPUB");
								}
								addDebug(_db,D.EBUG_SPEW,strTraceBase,eiLSEOBUNDLE.getKey()+" Updated "+eiCATLGPUB.getKey()+
									" for "+strCatAudience+" with "+attL);
							}else{
								addDebug(_db,D.EBUG_SPEW,strTraceBase,eiLSEOBUNDLE.getKey()+" no updates needed for "+eiCATLGPUB.getKey()+
									" for "+strCatAudience);
								checkForInactive(_db, _prof, eiCATLGPUB);
							}

							m_processedList.put(eiCATLGPUB.getKey(), eiCATLGPUB.getKey());

                            attL = null;
							catlgpubTbl.remove(strCatAudience);
                        }// end catlgpub found
                    } // end audience loop
					// anything left in the hashtable can be set to inactive because it doesnt have
					// a corresponding audience in the lseobundle
					if (catlgpubTbl.size()>0){
						for (Enumeration e = catlgpubTbl.elements(); e.hasMoreElements();) {
							EntityItem catlgpubitem = (EntityItem)e.nextElement();
							String cataud = m_utility.getAttrValue(catlgpubitem, ATT_CATAUDIENCE);
							addDebug(_db,D.EBUG_INFO,strTraceBase,"Setting "+catlgpubitem.getKey()+" to Inactive because parent does not have "+cataud);
							setInactiveCATLGPUB(_db, _prof, catlgpubitem);
							m_processedList.put(catlgpubitem.getKey(), catlgpubitem.getKey());
						}
					}

					catlgpubTbl.clear();
                }else{
                    addDebug(_db,D.EBUG_INFO,strTraceBase,eiLSEOBUNDLE.getKey()+" did not have OFFCOUNTRY: " +strCATLGCNTRY_OFFCOUNTRY);
					// no country match, but catlgpub may exist
					for (int e = 0; e<catlgpubVct.size(); e++) {
						EntityItem catlgpubitem = (EntityItem)catlgpubVct.elementAt(e);
						addDebug(_db,D.EBUG_INFO,strTraceBase,"Setting "+catlgpubitem.getKey()+" to Inactive because parent doesnt match offcountry");
						setInactiveCATLGPUB(_db, _prof, catlgpubitem);
						m_processedList.put(catlgpubitem.getKey(), catlgpubitem.getKey());
					}
                }
                m_utility.memory(false);

                catlgpubVct.clear();
            }// end entityitems in extract
            elLSEOBUNDLE.dereference();  // release memory
            elLSEOBUNDLE = null;
            egCATLGPUB = null;
            egLSEOBUNDLE = null;

            m_utility.memory(false);
        } // end entityitem chunk vct loop
    }
    /**
    * countryStillValid checks to see if the parent model, lseo or lseobundle still has the
    * catlgpub.offcountry in its countrylist.  each parent checks different places
    * to find the countrylist.
    *@param _db    Database
    *@param _prof  Profile
    *@param offctry  String with catlgpub.OFFCOUNTRY value
    *@return boolean
    */
	protected boolean countryStillValid(EntityItem parentItem, String offctry)
	{
		boolean bFoundCountry = false;
		// check specialbid
		String strSPECBID = m_utility.getAttrValue(parentItem, "SPECBID");
		if (strSPECBID.equals("11458")) {
			String strCOUNTRYLIST =  m_utility.getAttrValue(parentItem, ATT_COUNTRYLIST);
			if (strCOUNTRYLIST.indexOf(offctry) >= 0) {
				bFoundCountry = true;
			}
		}else{
			Vector availVct = getAllLinkedEntities(parentItem, "LSEOBUNDLEAVAIL", "AVAIL");
			for (int j=0; j < availVct.size(); j++) {
				EntityItem eiAVAIL = (EntityItem)availVct.elementAt(j);
				String strCOUNTRYLIST = m_utility.getAttrValue(eiAVAIL, ATT_COUNTRYLIST);

				if (strCOUNTRYLIST.indexOf(offctry) >= 0) {
					bFoundCountry = true;
					break;
				}
			}
			availVct.clear();
		}

		return bFoundCountry;
	}
    /**
    * @return String cvs revision numbers
    */
    public String getRevision() {
        return new String("$Revision: 1.2 $");
    }
}
