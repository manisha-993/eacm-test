// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: CATLGPUBLSEOPDG.java,v $
// Revision 1.3  2008/03/04 15:29:53  wendy
// Added more debug output
//
// Revision 1.2  2008/02/01 22:10:07  wendy
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
 * CATLGPUBLSEOPDG
 *
 */
public class CATLGPUBLSEOPDG extends CATLGPUBPDG {
    static final long serialVersionUID = 20011106L;

    private static final String ATT_CATLGPUBLSEOLASTRUN = "CATLGPUBLSEOLASTRUN";
    private static final String ATT_LSEOMKTGDESC = "LSEOMKTGDESC";
    private static final String ATT_LSEOPUBDATEMTRGT = "LSEOPUBDATEMTRGT";
    private static final String ATT_LSEOUNPUBDATEMTRGT = "LSEOUNPUBDATEMTRGT";

    /**
     * Version info
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: CATLGPUBLSEOPDG.java,v 1.3 2008/03/04 15:29:53 wendy Exp $";
    }

    /**
     * CATLGPUBLSEOPDG
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public CATLGPUBLSEOPDG(EANMetaFoundation _mf, CATLGPUBLSEOPDG _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
    }

    /**
     * constructor from abr
     * @param _emf
     * @param _db
     * @param _prof
     * @param _domains
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public CATLGPUBLSEOPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _domains) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _domains);
    }

    /**
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("CATLGPUBLSEOPDG:" + getKey() + ":desc:" + getLongDescription());
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
        return "CATLGPUBLSEOPDG";
    }

   /**
    * checkCatlgpub
    * Find changed lseo within time range with parent models of specified cofcat or cofgrp
    * handle subsets pulling an extract for each set
    * check for specbid on wwseo, use ctry on lseo if is specbid otherwise
    * find avails for the lseo to match to CATLGCNTRY.offcountry, use these for pubto and pubfrom
    * dates, check for CATLGOR overrides
    * find catlgpub for each audience specified and for the offcountry for the root CATLGCNTRY
    * create or update catlgpub as needed
    * For the VE:
    *
    *0	LSEO	Relator	LSEOAVAIL	AVAIL	D
    *0	LSEO	Relator	LSEOCATLGOR 	CATLGOR	D
    *0	WWSEO	Relator	WWSEOLSEO	LSEO	U
    *1	MODEL	Relator	MODELWWSEO	WWSEO	U
    *
    *-	where LSEO ? WWSEO ? MODEL.COFCAT = 'Hardware' (100 )  and MODEL.COFGRP = Base (150)
    *     OR
    *MODEL.COFCAT = Service (102)
    *     OR
    *MODEL.COFCAT = Software (101) and MODEL.COFGRP = Base (150)
    *
    *@param _db    Database
    *@param _prof  Profile
    */
    protected void checkCatlgpub(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase =  "CATLGPUBLSEOPDG.checkCatlgpub method ";

        String strCATLGCNTRY_OFFCOUNTRY = m_utility.getAttrValue(m_eiPDG, ATT_OFFCOUNTRY);
        addDebug(_db,D.EBUG_SPEW,strTraceBase,"Check LSEO entered for "+m_eiPDG.getKey()+" for offctry: "+strCATLGCNTRY_OFFCOUNTRY);

        EntityItem[] aeiChangedLSEO = getChangedRoots(_db, _prof,"LSEO", "EXTCATLGPUBPDGLSEO2",ATT_CATLGPUBLSEOLASTRUN);

        if (aeiChangedLSEO == null || aeiChangedLSEO.length <= 0) {
            addDebug(_db,D.EBUG_INFO,strTraceBase,"no changed LSEO found for "+m_eiPDG.getKey()+" for offctry: "+strCATLGCNTRY_OFFCOUNTRY);
            return;
        }
        addDebug(_db,D.EBUG_INFO,strTraceBase,"number of changed LSEO: " + aeiChangedLSEO.length);

        ExtractActionItem xaiLSEOGAA = new ExtractActionItem(null, _db, _prof, "EXTLSEOGAA1");
        ExtractActionItem xaiLSEO = new ExtractActionItem(null, _db, _prof, "EXTCATLGPUBPDGLSEO1");

        String strFileName = "PDGtemplates/CATLGPUB02.txt";

        Vector vaChanged = getChunksOfEntities(aeiChangedLSEO, m_iChunk);// work on subsets
        for (int v=0; v < vaChanged.size(); v++) {
            _db.debug(D.EBUG_INFO,strTraceBase + " at chunk #: " + v);
            //EntityItem[] aeiChanged = (EntityItem[]) vaChanged.elementAt(v);
            EntityList ellseo = EntityList.getEntityList(_db, _prof, xaiLSEO,
            	(EntityItem[]) vaChanged.elementAt(v));

            EntityGroup egCATLGPUB = ellseo.getEntityGroup("CATLGPUB");

            // check each lseo for domain and then for parent model criteria
            EntityGroup lseogrp = ellseo.getParentEntityGroup();
            for (int i=0; i<lseogrp.getEntityItemCount(); i++){
				EntityItem lseoitem = lseogrp.getEntityItem(i);
 				if (!domainNeedsCatlgpub(lseoitem)){ // LSEO must be in one of the listed domains
					continue;
				}
				String strAud = m_utility.getAttrValueDesc(lseoitem, ATT_AUDIEN);
                String strCatAud = getCatAudValue(_db, _prof, strAud);

//  addDebug(_db,D.EBUG_SPEW,strTraceBase,"check " + lseoitem.getKey()+" strCatAud: "+strCatAud+
//  	" seoid: "+m_utility.getAttrValue(lseoitem, ATT_SEOID));

				Vector catlgpubVct = getCatlgPubForCtry(lseoitem,"LSEOCATLGPUB", strCATLGCNTRY_OFFCOUNTRY);

                if (strCatAud == null || strCatAud.length() <= 0) {
  					addDebug(_db,D.EBUG_SPEW,strTraceBase,lseoitem.getKey()+" has strCatAud: "+strCatAud+" seoid: "+
  						m_utility.getAttrValue(lseoitem, ATT_SEOID)+" for strAud: "+strAud);
					// no audience match, but catlgpub may exist
					for (int e = 0; e<catlgpubVct.size(); e++) {
						EntityItem catlgpubitem = (EntityItem)catlgpubVct.elementAt(e);
						addDebug(_db,D.EBUG_INFO,strTraceBase,"Setting "+catlgpubitem.getKey()+" to Inactive because " + lseoitem.getKey()+" doesnt have audience");
						setInactiveCATLGPUB(_db, _prof, catlgpubitem);
						m_processedList.put(catlgpubitem.getKey(), catlgpubitem.getKey());
					}
					catlgpubVct.clear();
					continue;
                }

                String strDate1 = m_utility.getAttrValue(lseoitem, ATT_LSEOPUBDATEMTRGT);

				//where LSEO -> WWSEO -> MODEL.COFCAT = 'Hardware' (100 )  and MODEL.COFGRP = Base (150)
				//OR MODEL.COFCAT = Service (102)
				//OR MODEL.COFCAT = Software (101) and MODEL.COFGRP = Base (150)
				Vector wwseoVct = getAllLinkedEntities(lseoitem, "WWSEOLSEO", "WWSEO");
				Vector mdlVct = getAllLinkedEntities(wwseoVct, "MODELWWSEO", "MODEL");
				// should only be one model
				for (int m=0; m<mdlVct.size(); m++){
					EntityItem mdlitem = (EntityItem)mdlVct.elementAt(m);
 					String cofgrp =m_utility.getAttrValue(mdlitem, ATT_COFGRP);
 					String cofcat =m_utility.getAttrValue(mdlitem, ATT_COFCAT);
					if ((cofgrp.equals("150") && cofcat.equals("100")) || // hw base
						(cofcat.equals("102")) || // svc
						(cofgrp.equals("150") && cofcat.equals("101")))  // sw base
					{
						boolean ctryFound = false;
	                    // get values for PUBFROM, PUBTO
	                    String strPF = "";
	                    String strPT = "";

						// find country match by looking at specbid first
						EntityItem eiWWSEO = (EntityItem)wwseoVct.elementAt(0);
						String strSPECBID = m_utility.getAttrValue(eiWWSEO, "SPECBID");
						if (strSPECBID.equals("11458")) {
						    String strCOUNTRYLIST = m_utility.getAttrValue(lseoitem, ATT_COUNTRYLIST);
							addDebug(_db,D.EBUG_SPEW,strTraceBase,lseoitem.getKey()+" is SPECBID from "+eiWWSEO.getKey()+
								" COUNTRYLIST "+strCOUNTRYLIST);
                    		if (strCOUNTRYLIST.indexOf(strCATLGCNTRY_OFFCOUNTRY) >= 0) {
                    		    ctryFound = true;
		                        strPF = m_utility.getAttrValue(lseoitem, ATT_LSEOPUBDATEMTRGT);
		                        strPT = m_utility.getAttrValue(lseoitem, ATT_LSEOUNPUBDATEMTRGT);
		                        addDebug(_db,D.EBUG_SPEW,strTraceBase,lseoitem.getKey()+" SPECBID strPF "+strPF+" strPT "+strPT);
                	    	}
                		} else {
							//LSEO via relator LSEOAVAIL to AVAIL where AVAIL.AVAILTYPE = 'Planned Availability' (146).
							//This AVAIL has EFFECTIVEDATE and COUNTRYLIST.
							Vector availVct = getAllLinkedEntities(lseoitem, "LSEOAVAIL", "AVAIL");
							for (int j=0; j < availVct.size(); j++) {
								EntityItem eiAVAIL = (EntityItem)availVct.elementAt(j);
								String strAVAILTYPE = m_utility.getAttrValue(eiAVAIL, "AVAILTYPE");
								String strCOUNTRYLIST = m_utility.getAttrValue(eiAVAIL, ATT_COUNTRYLIST);
								addDebug(_db,D.EBUG_SPEW,strTraceBase,lseoitem.getKey()+" not SPECBID "+eiAVAIL.getKey()+".COUNTRYLIST "+strCOUNTRYLIST+" strAVAILTYPE " + strAVAILTYPE);
								if (strCOUNTRYLIST.indexOf(strCATLGCNTRY_OFFCOUNTRY) >= 0) {
									ctryFound = true;
									if (strAVAILTYPE.equals("146")) {
                            			strPF = m_utility.getAttrValue(eiAVAIL, ATT_EFFDATE);
                            			addDebug(_db,D.EBUG_SPEW,strTraceBase,lseoitem.getKey()+" "+eiAVAIL.getKey()+" strPF "+strPF);
									} else if (strAVAILTYPE.equals("149")) {
                           		 		strPT = m_utility.getAttrValue(eiAVAIL, ATT_EFFDATE);
                            			addDebug(_db,D.EBUG_SPEW,strTraceBase,lseoitem.getKey()+" "+eiAVAIL.getKey()+" strPT "+strPT);
									}
								}
							}
						}// end not specbid

						if (ctryFound) {
							//getting CATLGOR entity
							Vector catlgorVct = getAllLinkedEntities(lseoitem, "LSEOCATLGOR", "CATLGOR");
							for (int j=0; j < catlgorVct.size(); j++) {
								EntityItem eiCATLGOR = (EntityItem)catlgorVct.elementAt(j);
								String strOrOFFCOUNTRY = m_utility.getAttrValue(eiCATLGOR, ATT_OFFCOUNTRY);
								if (strOrOFFCOUNTRY.indexOf(strCATLGCNTRY_OFFCOUNTRY) >= 0) {
									addDebug(_db,D.EBUG_SPEW,strTraceBase,lseoitem.getKey()+" has override "+eiCATLGOR.getKey());
									if (m_utility.getAttrValue(eiCATLGOR, ATT_PF).length() > 0) {
										strPF =  m_utility.getAttrValue(eiCATLGOR, ATT_PF);
										addDebug(_db,D.EBUG_SPEW,strTraceBase,lseoitem.getKey()+" "+eiCATLGOR.getKey()+" override strPF "+strPF);
									}
									if (m_utility.getAttrValue(eiCATLGOR, ATT_PT).length() > 0) {
										strPT = m_utility.getAttrValue(eiCATLGOR, ATT_PT);
										addDebug(_db,D.EBUG_SPEW,strTraceBase,lseoitem.getKey()+" "+eiCATLGOR.getKey()+" override strPT "+strPT);
									}
									break;
								}
							}
							catlgorVct.clear();

							//get value for CATNEWOFF
							//If NOW() < LSEOPUBDATEMTRGT + 30 days then "Yes" else "No"
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
								sb.append("map_CATSEOID=" +  m_utility.getAttrValue(lseoitem, ATT_SEOID) + ";");
								sb.append("map_CATOFFTYPE=LSEO");

								String strSai =  (String) m_saiList.get("CATLGPUB");
								_prof = m_utility.setProfValOnEffOn(_db, _prof);

								// extract didnt have any, try search
								EntityItem aeiCATLGPUB[] = m_utility.dynaSearch(_db, _prof, null, strSai, "CATLGPUB", sb.toString());

								if (aeiCATLGPUB != null && aeiCATLGPUB.length > 0) {
									for (int a=0; a<aeiCATLGPUB.length; a++){
										verifyAudience(_db, _prof, aeiCATLGPUB[a], catlgpubTbl);
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
									addDebug(_db,D.EBUG_SPEW,strTraceBase,lseoitem.getKey()+" Creating CATLGPUB for audience "+strCatAudience);
									OPICMList infoList = new OPICMList();
									infoList.put("PDG", m_eiPDG);

									infoList.put("LSEO", lseoitem);
									infoList.put("WG", m_eiRoot);
									infoList.put("AUDIENVALUE", strCatAudience);

									infoList.put("PF", strPF);
									infoList.put("PT", strPT);
									infoList.put("CATNEWOFFVALUE", strCATNEWOFF);
									infoList.put("UPDATEDBY", m_strUpdatedBy);
									infoList.put("WWSEO", eiWWSEO);

									// getting values from Sales Status
									// getting GENERALAREA based on ASSOCIATION
									//for LSEO - use LSEOGAA from LSEO.COUNTRYLIST
									String seoid = m_utility.getAttrValue(lseoitem, ATT_SEOID);
									String strMtrnCodes = getSalesStatus(_db, _prof, strCATLGCNTRY_OFFCOUNTRY,
										xaiLSEOGAA, lseoitem, lseoitem, seoid);

									// load in buyable, Hide, Cart
									infoList.put("SALESSTATUS", strMtrnCodes);

									String priced = m_utility.getAttrValue(lseoitem, ATT_PRCINDC);
									if (priced==null){
										priced = "no";
									}
									String sskey = cofcat+":"+priced+":"+strSPECBID;
									SalesStatusInfo sshbac = SalesStatusInfo.getSalesStatusInfo(strMtrnCodes,sskey);//MN33416775
									addDebug(_db,D.EBUG_SPEW,strTraceBase,lseoitem.getKey()+" sskey: "+strMtrnCodes+":" + sskey+" returned "+sshbac);

									//If COFCAT = 'Hardware'  and COFGRP = 'Base' then "Yes" else "No"
									/*MN33416775
									if (m_utility.getAttrValue(mdlitem, ATT_COFCAT).equals("100") &&
										m_utility.getAttrValue(mdlitem, ATT_COFGRP).equals("150")) {
										infoList.put("CUSTIMIZEVALUE", "Yes");
									} else {
										infoList.put("CUSTIMIZEVALUE", "No");
									}*/

									//  if MODEL. SYSIDUNIT = SIU-CPU (S00010) then "Lease" else "None"
									if (m_utility.getAttrValue(mdlitem, ATT_SYSIDUNIT).equals("S00010")) {
										infoList.put("CATRATETYPEVALUE", "LEASE");
									} else {
										infoList.put("CATRATETYPEVALUE", "NONE");
									}
									infoList.put("CUSTIMIZEVALUE", sshbac.getCustimize()); //MN33416775
									infoList.put("CARTVALUE", sshbac.getAddToCart()); //MN33416775
									infoList.put("BUYABLEVALUE", sshbac.getBuyable()); //MN33416775
									infoList.put("HIDEVALUE", sshbac.getHide()); //MN33416775

									_prof = m_utility.setProfValOnEffOn(_db, _prof);
									TestPDGII pdgObject = new TestPDGII(_db, _prof, lseoitem, infoList, strFileName );
									StringBuffer sbMissing = pdgObject.getMissingEntities();
									addDebug(_db,D.EBUG_SPEW,strTraceBase,"creating CATLGPUB for "+lseoitem.getKey());

									m_savedEIList = new EANList();
									generateDataII(_db, _prof, sbMissing, "");
									for (int e=0; e < m_savedEIList.size(); e++) {
										EntityItem ei = (EntityItem)m_savedEIList.getAt(e);
										if (ei.getEntityType().equals("CATLGPUB")) {
											addDebug(_db,D.EBUG_SPEW,strTraceBase,lseoitem.getKey()+" Created "+ei.getKey()+" for audience "+strCatAudience);
											m_processedList.put(ei.getKey(), ei.getKey());
										}
									}

									pdgObject = null;
									infoList = null;

								} else { // update the current one
									// update attributes CATNEWOFF, PRCINDC, PUBFROM, PUBTO, CATLGMKTGDESC, Set CATWORKFLOW to 'Change' if updated.
									String strPROJCDNAM = m_utility.getAttrValue(eiWWSEO, ATT_PROJCDNAM);
									String strMODMKTGDESC = m_utility.getAttrValue(lseoitem, ATT_LSEOMKTGDESC);
									String strPRCINDC = m_utility.getAttrValue(lseoitem, ATT_PRCINDC); //RQ022507373 and MN31580435

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
									//LSEO.STATUS =  Ready for Review (0040) 'logical OR'
									//LSEO.STATUS =  Final (0020), then if
									//  Set CATLGPUB.CATACTIVE = 'Active' (Active) else
									//  Set CATLGPUB.CATACTIVE = 'Inactive' (Inactive)

									if ( (m_utility.dateCompare(strPF, m_utility.getDate(strCurrentDate, 15)) == PDGUtility.EARLIER)
										|| m_utility.getAttrValue(lseoitem, ATT_STATUS).equals("0040")
										|| m_utility.getAttrValue(lseoitem, ATT_STATUS).equals("0020") ) {
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
											m_utility.linkEntities(_db, _prof, lseoitem, aeiChild, "LSEOCATLGPUB");
										}
										addDebug(_db,D.EBUG_SPEW,strTraceBase,lseoitem.getKey()+" Updated "+eiCATLGPUB.getKey()+" for audience "+strCatAudience+" with "+attL);
									}else{
										addDebug(_db,D.EBUG_SPEW,strTraceBase,lseoitem.getKey()+" no updates needed for "+eiCATLGPUB.getKey()+" for audience "+strCatAudience);
										checkForInactive(_db, _prof, eiCATLGPUB);
									}

									m_processedList.put(eiCATLGPUB.getKey(), eiCATLGPUB.getKey());

									attL = null;
									catlgpubTbl.remove(strCatAudience);
                        		}// end catlgpub item found
                        	}// end audience loop
							// anything left in the hashtable can be set to inactive because it doesnt have
							// a corresponding audience in the lseo
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
							addDebug(_db,D.EBUG_INFO,strTraceBase,lseoitem.getKey()+" did not have OFFCOUNTRY " + strCATLGCNTRY_OFFCOUNTRY);
							// no country match, but catlgpub may exist
							for (int e = 0; e<catlgpubVct.size(); e++) {
								EntityItem catlgpubitem = (EntityItem)catlgpubVct.elementAt(e);
								addDebug(_db,D.EBUG_INFO,strTraceBase,"Setting "+catlgpubitem.getKey()+" to Inactive because parent doesnt match offcountry");
								setInactiveCATLGPUB(_db, _prof, catlgpubitem);
								m_processedList.put(catlgpubitem.getKey(), catlgpubitem.getKey());
							}
						}
					}// end model criteria met
					else{
	        			addDebug(_db,D.EBUG_INFO,strTraceBase,"skipping "+lseoitem.getKey()+" "+
	        				mdlitem.getKey()+" has cofgrp "+cofgrp+" cofcat "+cofcat);
						// no model match, but catlgpub may exist if model was chgd
						for (int e = 0; e<catlgpubVct.size(); e++) {
							EntityItem catlgpubitem = (EntityItem)catlgpubVct.elementAt(e);
							addDebug(_db,D.EBUG_INFO,strTraceBase,"Setting "+catlgpubitem.getKey()+" to Inactive because parent model had invalid classification");
							setInactiveCATLGPUB(_db, _prof, catlgpubitem);
							m_processedList.put(catlgpubitem.getKey(), catlgpubitem.getKey());
						}
					}
				}// end model loop
				wwseoVct.clear();
				mdlVct.clear();

				catlgpubVct.clear();
			}// end lseo loop

			ellseo.dereference();
            m_utility.memory(true);
        } // end subset of chgd items
    }

    /**
    * countryStillValid checks to see if the parent model, lseo or lseobundle still has the
    * catlgpub.offcountry in its countrylist.  each parent checks different places
    * to find the countrylist.
    *@param parentItem    EntityItem
    *@param offctry  String with catlgpub.OFFCOUNTRY value
    *@return boolean
    */
	protected boolean countryStillValid(EntityItem parentItem, String offctry)
	{
		boolean bFoundCountry = false;
		// check specialbid
		Vector wwseoVct = getAllLinkedEntities(parentItem, "WWSEOLSEO", "WWSEO");
		Vector mdlVct = getAllLinkedEntities(wwseoVct, "MODELWWSEO", "MODEL");

		boolean bSpecBid = false;
		for (int w=0; w < wwseoVct.size(); w++) {
			EntityItem eiWWSEO = (EntityItem)wwseoVct.elementAt(w);
			String strSPECBID = m_utility.getAttrValue(eiWWSEO, "SPECBID");
			if (strSPECBID.equals("11458")) {
				bSpecBid = true;
				break;
			}
		}

		if (bSpecBid) {
			// check LSEO 's COUNTRYLIST
			String strCOUNTRYLIST =  m_utility.getAttrValue(parentItem, ATT_COUNTRYLIST);
			if (strCOUNTRYLIST.indexOf(offctry) >= 0) {
				bFoundCountry = true;
			}else{
				addDebug("countryStillValid: "+offctry+" country not found for "+parentItem.getKey()+" bSpecBid: "+
					bSpecBid+" lseo.countrylist: "+ strCOUNTRYLIST);
			}
		} else {
			Vector availVct = getAllLinkedEntities(parentItem, "LSEOAVAIL", "AVAIL");
			for (int j=0; j < availVct.size(); j++) {
				EntityItem eiAVAIL = (EntityItem)availVct.elementAt(j);
				String strCOUNTRYLIST = m_utility.getAttrValue(eiAVAIL, ATT_COUNTRYLIST);

				if (strCOUNTRYLIST.indexOf(offctry) >= 0) {
					bFoundCountry = true;
					break;
				}
			}
			if (!bFoundCountry){
				addDebug("countryStillValid: "+offctry+" country not found for "+parentItem.getKey()+" bSpecBid: "+
					bSpecBid+" wwseoVct: "+ wwseoVct+" availVct: "+availVct);
			}
			availVct.clear();
		}

		//add check for model type, if invalid return false
		if (bFoundCountry){
			// should only be one model
			for (int m=0; m<mdlVct.size(); m++){
				EntityItem mdlitem = (EntityItem)mdlVct.elementAt(m);
				String cofgrp =m_utility.getAttrValue(mdlitem, ATT_COFGRP);
				String cofcat =m_utility.getAttrValue(mdlitem, ATT_COFCAT);
				if ((cofgrp.equals("150") && cofcat.equals("100")) || // hw base
					(cofcat.equals("102")) || // svc
					(cofgrp.equals("150") && cofcat.equals("101")))  // sw base
				{}else{
					addDebug("countryStillValid: found invalid "+mdlitem.getKey()+" for "+parentItem.getKey()+" with cofgrp "+cofgrp+" cofcat "+cofcat);
					bFoundCountry = false;
					break;
				}
			}
		}

		wwseoVct.clear();
		mdlVct.clear();

		return bFoundCountry;
	}

    /**
    * @return String cvs revision numbers
    */
    public String getRevision() {
        return new String("$Revision: 1.3 $");
    }
}
