// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: CATLGPUBMDLPDG.java,v $
// Revision 1.2  2008/02/01 22:10:06  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2008/01/04 16:47:46  wendy
// MN33416775 handling of salesstatus chgs, split into derived classes
//
//

package COM.ibm.eannounce.objects;

import java.sql.*;
import java.util.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.OPICMList;

/**
 * CATLGPUBMDLPDG
 *
 */
public class CATLGPUBMDLPDG extends CATLGPUBPDG {
    static final long serialVersionUID = 20011106L;

    private static final String ATT_CATLGPUBMDLLASTRUN = "CATLGPUBMDLLASTRUN";
    private static final String ATT_MODMKTGDESC = "MODMKTGDESC";

    /**
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: CATLGPUBMDLPDG.java,v 1.2 2008/02/01 22:10:06 wendy Exp $";
    }

    /**
     * CATLGPUBMDLPDG
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public CATLGPUBMDLPDG(EANMetaFoundation _mf, CATLGPUBMDLPDG _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
    }

    /**
     *constructor from abr
     * @param _emf
     * @param _db
     * @param _prof
     * @param _domains
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public CATLGPUBMDLPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _domains) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _domains);
    }

    /**
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("CATLGPUBMDLPDG:" + getKey() + ":desc:" + getLongDescription());
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
        return "CATLGPUBMDLPDG";
    }

    /**
    * checkCatlgpub
    * Find changed models within time range, they must have the specified cofcat or cofgrp
    * handle subsets pulling an extract for each set
    * find avails for the model to match to CATLGCNTRY.offcountry, use these for pubto and pubfrom
    * dates, check for CATLGOR overrides
    * find catlgpub for each audience specified and for the offcountry for the root CATLGCNTRY
    * create or update catlgpub as needed
    *@param _db    Database
    *@param _prof  Profile
    */
    protected void checkCatlgpub(Database _db, Profile _prof)
    throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
    {
        String strTraceBase =  "CATLGPUBMDLPDG.checkCatlgpub method ";
        String strFileName = "PDGtemplates/CATLGPUB01.txt";
        String strCATLGCNTRY_OFFCOUNTRY = m_utility.getAttrValue(m_eiPDG, ATT_OFFCOUNTRY);

        addDebug(_db,D.EBUG_SPEW,strTraceBase,"Check MODEL entered for "+m_eiPDG.getKey()+" for offctry: "+strCATLGCNTRY_OFFCOUNTRY);

        EntityItem[] aeiChangedMODEL = getModels(_db, _prof); // these will be grp=base or cat=svc

        if (aeiChangedMODEL == null || aeiChangedMODEL.length <= 0) {
            addDebug(_db,D.EBUG_INFO,strTraceBase,"no changed MODEL found for "+m_eiPDG.getKey()+" for offctry: "+strCATLGCNTRY_OFFCOUNTRY);
            return;
        }

        addDebug(_db,D.EBUG_SPEW,strTraceBase,"number of changed MODEL: " + aeiChangedMODEL.length);

        ExtractActionItem xaiMODEL = new ExtractActionItem(null, _db, _prof, "EXTCATLGPUBPDGMODEL2");
		ExtractActionItem xaiCATLGCNTRYGAA = new ExtractActionItem(null, _db, _prof, "EXTCATLGCNTRYGAA1");

        Vector vaChanged = getChunksOfEntities(aeiChangedMODEL, m_iChunk);
        for (int v=0; v < vaChanged.size(); v++) {
            _db.debug(D.EBUG_INFO,strTraceBase + " at chunk #: " + v);

            EntityList elModel = EntityList.getEntityList(_db, _prof, xaiMODEL,
            	(EntityItem[]) vaChanged.elementAt(v));

            EntityGroup egMODEL = elModel.getParentEntityGroup();
            EntityGroup egCATLGPUB = elModel.getEntityGroup("CATLGPUB");

            for (int i=0; i < egMODEL.getEntityItemCount(); i++) {
                EntityItem eiMODEL = egMODEL.getEntityItem(i);
				String strAud = m_utility.getAttrValueDesc(eiMODEL, ATT_AUDIEN);
				String strCatAud = getCatAudValue(_db, _prof, strAud);
				//addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" has strCatAud: " + strCatAud+" for strAud: "+strAud);
				Vector catlgpubVct = getCatlgPubForCtry(eiMODEL,"MODELCATLGPUB", strCATLGCNTRY_OFFCOUNTRY);
				if (strCatAud == null || strCatAud.length() == 0) {
					// no audience match, but catlgpub may exist
					addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" has strCatAud: " + strCatAud+" for strAud: "+strAud);
					for (int e = 0; e<catlgpubVct.size(); e++) {
						EntityItem catlgpubitem = (EntityItem)catlgpubVct.elementAt(e);
						addDebug(_db,D.EBUG_INFO,strTraceBase,"Setting "+catlgpubitem.getKey()+" to Inactive because "+eiMODEL.getKey()+" doesnt have audience");
						setInactiveCATLGPUB(_db, _prof, catlgpubitem);
						m_processedList.put(catlgpubitem.getKey(), catlgpubitem.getKey());
					}
					catlgpubVct.clear();
					continue;
				}

                //MODEL via relator MODELAVAIL to AVAIL where AVAIL.AVAILTYPE = 'Planned Availability' (146).
                //This AVAIL has EFFECTIVEDATE and COUNTRYLIST.
				Vector availVct = getAllLinkedEntities(eiMODEL, "MODELAVAIL", "AVAIL");
                boolean bInclude = false;
                EntityItem eiAVAILPA = null;
                EntityItem eiAVAILLO = null;
                for (int j=0; j < availVct.size(); j++) {
                    EntityItem eiAVAIL = (EntityItem)availVct.elementAt(j);
                    String strAVAILTYPE = m_utility.getAttrValue(eiAVAIL, "AVAILTYPE");
                    String strCOUNTRYLIST = m_utility.getAttrValue(eiAVAIL, ATT_COUNTRYLIST);

                    addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" has "+eiAVAIL.getKey()+" AVAILTYPE:" + strAVAILTYPE+" COUNTRYLIST: "+strCOUNTRYLIST);
                    if (strCOUNTRYLIST.indexOf(strCATLGCNTRY_OFFCOUNTRY) >= 0) {
                        bInclude = true;
                        if (strAVAILTYPE.equals("146")) {
                            eiAVAILPA = eiAVAIL;
                        } else if (strAVAILTYPE.equals("149")) {
                            eiAVAILLO = eiAVAIL;
                        }
                    }
                }
                availVct.clear();

                if (bInclude) {
                    _db.debug(D.EBUG_SPEW,strTraceBase + eiMODEL.getKey()+" has eiAVAILPA: " + eiAVAILPA+" eiAVAILLO: "+eiAVAILLO+" for OFFCOUNTRY "+strCATLGCNTRY_OFFCOUNTRY);
                    // get CATLGOR entity
					Vector catlgorVct = getAllLinkedEntities(eiMODEL, "MDLCATLGOR", "CATLGOR");
					EntityItem eiCATLGOR = null;
                    for (int j=0; j < catlgorVct.size(); j++) {
                    	EntityItem item = (EntityItem)catlgorVct.elementAt(j);
                        String strOrOFFCOUNTRY = m_utility.getAttrValue(item, ATT_OFFCOUNTRY);
                        if (strOrOFFCOUNTRY.indexOf(strCATLGCNTRY_OFFCOUNTRY) >= 0) {
                            eiCATLGOR = item;
                            addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" has override "+eiCATLGOR.getKey());
                            break;
                        }
                    }
                    catlgorVct.clear();

                    // get values for PUBFROM, PUBTO
/*
PUBFROM - select the first one that produces a date (i.e. priority order)
1.  MODEL via relator MODELAVAIL to AVAIL where AVAIL.AVAILTYPE = 'Planned Availability' (146). This AVAIL has EFFECTIVEDATE and COUNTRYLIST.
Set PUBFROM = EFFECTIVEDATE for each country in the COUNTRYLIST that is in CATLGCNTRY.OFFCOUNTRY.
2.  For each CATLGCNTRY.OFFCOUNTRY where PUBFROM was not derived in step 1, set PUBFROM = MODEL.ANNDATE.
PUBTO - select the first one that produces a date (i.e. priority order)
1.  If MODEL via relator MODELAVAIL to AVAIL where AVAIL.AVAILTYPE = 'Last Order' (149), then this EFFECTIVEDATE is the PUBTO for those countries in this COUNTRYLIST.
Set PUBTO = EFFECTIVEDATE for each country in the COUNTRYLIST that is in CATLGCNTRY.OFFCOUNTRY.
2.  For each CATLGCNTRY.OFFCOUNTRY where PUBTO was not derived in step 1, set PUBTO = MODEL.WITHDRAWDATE

If MODEL via relator MDLCATLGOR to CATLGOR exists where CATLGOR.OFFCOUNTRY matches the CATLGCNTRY, then set PUBFROM = CATLGOR.PUBFROM (if specified) for CATLGOR.OFFCOUNTRY = CATLGCNTRY.OFFCOUNTRY and set PUBTO = CATLGOR.PUBTO (if specified) for CATLGOR.OFFCOUNTRY = CATLGCNTRY.OFFCOUNTRY. This is an override.
*/
                    String strPF = "";
                    String strPT = "";
                    if (eiAVAILPA != null) {
                        strPF = m_utility.getAttrValue(eiAVAILPA, ATT_EFFDATE);
                        addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" has "+eiAVAILPA.getKey()+" strPF: " + strPF);
                    }

                    if (eiAVAILLO != null) {
                        strPT = m_utility.getAttrValue(eiAVAILLO, ATT_EFFDATE);
                        addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" has "+eiAVAILLO.getKey()+" strPT: " + strPT);
                    }

                    if (eiCATLGOR != null) {
                        if (m_utility.getAttrValue(eiCATLGOR, ATT_PF).length() > 0) {
                            strPF =  m_utility.getAttrValue(eiCATLGOR, ATT_PF);
                            addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" has "+eiCATLGOR.getKey()+" override strPF: " + strPF);
                        }
                        if (m_utility.getAttrValue(eiCATLGOR, ATT_PT).length() > 0) {
                            strPT = m_utility.getAttrValue(eiCATLGOR, ATT_PT);
                            addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" has "+eiCATLGOR.getKey()+" override strPT: " + strPT);
                        }
                    }

                    if (strPF.length() <= 0) {
                        //If neither of the preceding two paragraphs results in the derivation of a PUBFROM date,
                        //then use MODEL.ANNDATE
                        strPF = m_utility.getAttrValue(eiMODEL, ATT_ANNDATE);
                        addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" use MODEL.ANNDATE strPF: " + strPF);
                    }

                    if (strPT.length() <= 0) {
                        //If neither of the preceding two paragraphs results in the derivation of a PUBTO date,
                        //then use MODEL.WITHDRAWDATE.
                        strPT = m_utility.getAttrValue(eiMODEL, ATT_WITHDRAWDATE);
                        addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" use MODEL.WITHDRAWDATE strPT: " + strPT);
                    }

                    // get CATNEWOFF value
                    String strCATNEWOFF = "No";
                    //If NOW() < PUBFROM + 30 days then "Yes" else "No"
                    if (strPF.length() > 0) {
                        int iDC2 = m_utility.dateCompare(strCurrentDate, m_utility.getDate(strPF, 30));
                        if (iDC2 == PDGUtility.EARLIER) {
                            strCATNEWOFF = "Yes";
                        }
                    }

					// get all catlgpub by audience for this country
					Hashtable catlgpubTbl = getCatlgPubForCtryByAudience(_db, _prof, catlgpubVct);

					if (catlgpubTbl.size()==0 && doSearch()){
						String strSai =  (String) m_saiList.get("CATLGPUB");
						_prof = m_utility.setProfValOnEffOn(_db, _prof);
						//many needless loops here so avoid unless turned on
						StringBuffer sb = new StringBuffer();
						sb.append("map_OFFCOUNTRY=" +  strCATLGCNTRY_OFFCOUNTRY + ";");
						sb.append("map_CATMACHTYPE=" +  m_utility.getAttrValue(eiMODEL, ATT_MACHTYPE) + ";");
						sb.append("map_CATMODEL=" +  m_utility.getAttrValue(eiMODEL, ATT_MODEL) + ";");
						sb.append("map_CATOFFTYPE=MODEL");

						EntityItem aeiCATLGPUB[] = m_utility.dynaSearch(_db, _prof, null, strSai, "CATLGPUB", sb.toString());

						if (aeiCATLGPUB != null && aeiCATLGPUB.length > 0) {
							for (int a=0; a<aeiCATLGPUB.length; a++){
								String strOFFCOUNTRY = m_utility.getAttrValue(aeiCATLGPUB[a], ATT_OFFCOUNTRY);
								if (strCATLGCNTRY_OFFCOUNTRY.equals(strOFFCOUNTRY)){
									verifyAudience(_db, _prof, aeiCATLGPUB[a], catlgpubTbl);
								}
								aeiCATLGPUB[a] = null;
							}
							aeiCATLGPUB = null;
						}
					}

                    StringTokenizer stCatAud = new StringTokenizer(strCatAud, ",");
                    while (stCatAud.hasMoreTokens()) {
                        String strCatAudience = stCatAud.nextToken();
                        // find CATLGPUB for this audience
						EntityItem eiCATLGPUB = (EntityItem)catlgpubTbl.get(strCatAudience);

                        if (eiCATLGPUB== null) {
                            addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" Creating CATLGPUB for audience "+strCatAudience);
                            OPICMList infoList = new OPICMList();
                            String sskey = m_utility.getAttrValue(eiMODEL, ATT_COFCAT);

							//for MODEL - use CATLGCNTRYGAA from CATLGCNTRY.OFFCOUNTRY
							String mtm = m_utility.getAttrValue(eiMODEL, ATT_MACHTYPE)+m_utility.getAttrValue(eiMODEL, ATT_MODEL);
							String strMtrnCodes = getSalesStatus(_db, _prof, strCATLGCNTRY_OFFCOUNTRY,
								xaiCATLGCNTRYGAA, m_eiPDG, eiMODEL, mtm);

                            // load in buyable, Hide, Cart
                            infoList.put("SALESSTATUS", strMtrnCodes);

                            SalesStatusInfo sshbac = SalesStatusInfo.getSalesStatusInfo(strMtrnCodes,sskey);//MN33416775
                            addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" has sskey: " + sskey+" returned "+sshbac);

                            infoList.put("PDG", m_eiPDG);
                            infoList.put("MODEL", eiMODEL);
                            infoList.put("WG", m_eiRoot);

                            infoList.put("AUDIENVALUE", strCatAudience);
                            infoList.put("PF", strPF);
                            infoList.put("PT", strPT);
                            infoList.put("CATNEWOFFVALUE", strCATNEWOFF);
                            infoList.put("UPDATEDBY", m_strUpdatedBy);
                            //If COFCAT = 'Hardware'  and COFGRP = 'Base' then "Yes" else "No"
                            /*MN33416775
                            if (m_utility.getAttrValue(eiMODEL, ATT_COFCAT).equals("100")) {
                                infoList.put("CUSTIMIZEVALUE", "Yes");
                            } else {
                                infoList.put("CUSTIMIZEVALUE", "No");
                            }*/

                            infoList.put("CUSTIMIZEVALUE", sshbac.getCustimize()); //MN33416775
                            infoList.put("CARTVALUE", sshbac.getAddToCart()); //MN33416775
                            infoList.put("BUYABLEVALUE", sshbac.getBuyable()); //MN33416775
                            infoList.put("HIDEVALUE", sshbac.getHide()); //MN33416775

                            //  if MODEL. SYSIDUNIT = SIU-CPU (S00010) then "Lease" else "None"
                            if (m_utility.getAttrValue(eiMODEL, ATT_SYSIDUNIT).equals("S00010")) {
                                infoList.put("CATRATETYPEVALUE", "LEASE");
                            } else {
                                infoList.put("CATRATETYPEVALUE", "NONE");
                            }

                            TestPDGII pdgObject = new TestPDGII(_db, _prof, eiMODEL, infoList, strFileName );
                            StringBuffer sbMissing = pdgObject.getMissingEntities();
                            addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" creating CATLGPUB using "+infoList);

                            m_savedEIList = new EANList(); // reset base class list
                            generateDataII(_db, _prof, sbMissing, "");
                            for (int e=0; e < m_savedEIList.size(); e++) {
                                EntityItem ei = (EntityItem)m_savedEIList.getAt(e);
                                if (ei.getEntityType().equals("CATLGPUB")) {
                                    addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" created "+ei.getKey());
                                    m_processedList.put(ei.getKey(), ei.getKey());
                                }
                            }

                            pdgObject = null;
                            infoList = null;
                        } else {
							/*
							If a matching CATLGPUB exists (MODEL via relator MODELCATLGPUB to CATLGPUB where each
							value of MODEL.AUDIEN matches an instance of CATLGPUB.CATAUDIENCE (single value) and
							the 'country from above' matches CATLGCNTRY.OFFCOUNTRY), then
							-	if PUBFROM - 15 Days < NOW() ' logical OR' MODEL.STATUS =  Ready for Review (0040) 'logical OR' MODEL.STATUS =  Final (0020), then
								o	Set CATLGPUB.CATACTIVE = 'Active' (Active)
							else
								o	Set CATLGPUB.CATACTIVE = 'Inactive' (Inactive)

							-	also update the following if the value is changed:
								-	CATNEWOFF
								-	PUBFROM
								-	PUBTO
								-	CATLGMKTGDESC
								-	PROJCDNAM
								-	PRCINDC

							If updated, then set CATWORKFLOW based on its current value
								Current Value	Set Value To:
								Accept			Change
								New				Change
								Override		SalesStatusOverride
							*/
                            // update attributes PROJCDNAM, CATNEWOFF, PRCINDC, PUBFROM, PUBTO, CATLGMKTGDESC, Set CATWORKFLOW to 'Change' if updated.

                            String strPROJCDNAM = m_utility.getAttrValue(eiMODEL, ATT_PROJCDNAM);
                            String strMODMKTGDESC = m_utility.getAttrValue(eiMODEL, ATT_MODMKTGDESC);
                            String strPRCINDC = m_utility.getAttrValue(eiMODEL, ATT_PRCINDC); //RQ022507373 and MN31580435

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
							//RQ022507373 and MN31580435
							if (!m_utility.getAttrValue(eiCATLGPUB, ATT_PRCINDC).equals(strPRCINDC)) {
								attL.put(ATT_PRCINDC, ATT_PRCINDC + "=" + strPRCINDC);
							}

							//  if PUBFROM - 15 Days < NOW() ' logical OR'
							// MODEL.STATUS =  Ready for Review (0040) 'logical OR'
							// MODEL.STATUS =  Final (0020), then
							//  Set CATLGPUB.CATACTIVE = 'Active' (Active) else
							//  Set CATLGPUB.CATACTIVE = 'Inactive' (Inactive)
							if ( (m_utility.dateCompare(strPF, m_utility.getDate(strCurrentDate, 15)) == PDGUtility.EARLIER)
								|| m_utility.getAttrValue(eiMODEL, ATT_STATUS).equals("0040")
								|| m_utility.getAttrValue(eiMODEL, ATT_STATUS).equals("0020") ) {
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

								//only needed if found thru search
								if(doSearch() && egCATLGPUB.getEntityItem(eiCATLGPUB.getKey()) == null) {
									EntityItem[] aeiChild = {eiCATLGPUB};
									m_utility.linkEntities(_db, _prof, eiMODEL, aeiChild, "MODELCATLGPUB");
								}
								addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" Updated "+eiCATLGPUB.getKey()+
									" for "+strCatAudience+" with "+attL);
							}else{
								addDebug(_db,D.EBUG_SPEW,strTraceBase,eiMODEL.getKey()+" no update needed for "+eiCATLGPUB.getKey()+
									" for "+strCatAudience);
								// check if this should be removed
								checkForInactive(_db, _prof, eiCATLGPUB);
							}

							m_processedList.put(eiCATLGPUB.getKey(), eiCATLGPUB.getKey());
                            attL = null;

							catlgpubTbl.remove(strCatAudience);
                        }// end catlgpub item found
                    }// end audience loop

                    // anything left in the hashtable can be set to inactive because it doesnt have
                    // a corresponding audience in the model
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
                } else {
                    addDebug(_db,D.EBUG_INFO,strTraceBase,eiMODEL.getKey()+" unable to find a matching AVAIL for OFFCOUNTRY "+strCATLGCNTRY_OFFCOUNTRY);
					// no parent country match, but catlgpub may exist
					for (int e = 0; e<catlgpubVct.size(); e++) {
						EntityItem catlgpubitem = (EntityItem)catlgpubVct.elementAt(e);
						addDebug(_db,D.EBUG_INFO,strTraceBase,"Setting "+catlgpubitem.getKey()+" to Inactive because parent doesnt match offcountry");
						setInactiveCATLGPUB(_db, _prof, catlgpubitem);
						m_processedList.put(catlgpubitem.getKey(), catlgpubitem.getKey());
					}
                }
                m_utility.memory(false);

				catlgpubVct.clear();
            } // end model item loop

			elModel.dereference();
            elModel = null;
            egMODEL = null;
            egCATLGPUB = null;
            m_utility.memory(false);
        } // end changed chunk loop
    }

    /**
    * Level	Entity1	RelType	Relator	Entity2	Direction
	*	0	MODEL	Relator	MODELAVAIL	AVAIL	D
	*	0	MODEL	Relator	MDLCATLGOR 	CATLGOR	D
	*
	* where MODEL.COFGRP = Base (150) or MODEL.COFCAT = Service (102)
	* and the VE was updated (relators and/or attributes of interest) since
	* CATLGCNTRY.CATLGOFFPUBLASTRUN (if CATLGOFFPUBLASTRUN doesn’t have a value, assume 1980-01-01).this is handled in abr code
    *@param _db    Database
    *@param _prof  Profile
    *@return EntityItem[]
    */
    private EntityItem[] getModels(Database _db, Profile _prof)
    throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
    {
        String strTraceBase = " CATLGPUBMDLPDG getModels method ";
        EntityItem[] aeiReturn = null;

        EANList returnList = new EANList();
        EntityItem[] aeiChangedMdl = getChangedRoots(_db, _prof,"MODEL", "EXTCATLGPUBPDGMODEL1",ATT_CATLGPUBMDLLASTRUN);

        if (aeiChangedMdl!=null && aeiChangedMdl.length>0){
			// do dummy extract
			ExtractActionItem xai = new ExtractActionItem(null, _db, _prof, "dummy");
			EntityList mdlList = EntityList.getEntityList(_db, _prof, xai, aeiChangedMdl);
			EntityGroup mdlgrp = mdlList.getParentEntityGroup();
			// check for base or svc
			for (int i=0; i < aeiChangedMdl.length; i++) {
				EntityItem eiMODELsmall = aeiChangedMdl[i];
				EntityItem ei = mdlgrp.getEntityItem(eiMODELsmall.getKey());

 				if (!domainNeedsCatlgpub(ei)){ // MODEL must be in one of the listed domains
					continue;
				}

 				String cofgrp =m_utility.getAttrValue(ei, ATT_COFGRP);
 				String cofcat =m_utility.getAttrValue(ei, ATT_COFCAT);

				if (cofgrp.equals("150") || cofcat.equals("102")) { // must be group=Base or category=Service
					returnList.put(eiMODELsmall);
				}else{
        			addDebug(_db,D.EBUG_SPEW,strTraceBase,"skipping "+ei.getKey()+" with cofgrp "+cofgrp+" cofcat "+cofcat);
        			// if this was something else and had catlgpubs, they will be set inactive when the removecatlgpub
        			// method runs
				}
			}

			mdlList.dereference();

	        aeiReturn = new EntityItem[returnList.size()];
	        returnList.copyTo(aeiReturn);
	        returnList.clear();
		}

        return aeiReturn;
    }

    /**
    * countryStillValid checks to see if the parent model, lseo or lseobundle still has the
    * catlgpub.offcountry in its countrylist.  each parent checks different places
    * to find the countrylist.
    *@param parentItem  EntityItem
    *@param offctry  String with catlgpub.OFFCOUNTRY value
    *@return boolean
    */
	protected boolean countryStillValid(EntityItem parentItem, String offctry)
	{
		boolean bFoundCountry = false;

		Vector availVct = getAllLinkedEntities(parentItem, "MODELAVAIL", "AVAIL");
		for (int j=0; j < availVct.size(); j++) {
			EntityItem eiAVAIL = (EntityItem)availVct.elementAt(j);
			String strCOUNTRYLIST = m_utility.getAttrValue(eiAVAIL, ATT_COUNTRYLIST);
			if (strCOUNTRYLIST.indexOf(offctry) >= 0) {
				bFoundCountry = true;
				break;
			}
		}
		availVct.clear();

		//add check for model type, if invalid return false
		if (bFoundCountry){
			String cofgrp =m_utility.getAttrValue(parentItem, ATT_COFGRP);
			String cofcat =m_utility.getAttrValue(parentItem, ATT_COFCAT);

			if (cofgrp.equals("150") || cofcat.equals("102")) { // must be group=Base or category=Service
			}else{
				addDebug("countryStillValid: found invalid "+parentItem.getKey()+" with cofgrp "+cofgrp+" cofcat "+cofcat);
				bFoundCountry = false;
			}
		}

		return bFoundCountry;
	}

    /**
    * @return String cvs revision numbers
    */
    public String getRevision() {
        return "$Revision: 1.2 $";
    }
}
