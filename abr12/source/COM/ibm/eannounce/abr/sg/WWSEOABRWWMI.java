//    (c) Copyright International Business Machines Corporation, 2001
//    All Rights Reserved.</pre>
//
//WWSEOABRWWMI.java,v
//Revision 1.14  2008/01/30 19:39:15  wendy
//Cleanup RSA warnings
//
//Revision 1.13  2006/10/06 17:53:38  joan
//spec change
//
//Revision 1.12  2006/05/03 22:56:38  joan
//fixes
//
//Revision 1.11  2006/03/22 21:39:01  joan
//add new abr
//
//Revision 1.10  2006/03/21 23:07:47  joan
//add debug
//
//Revision 1.9  2006/03/16 18:12:26  joan
//add abr
//
//Revision 1.8  2006/03/15 17:21:32  joan
//add new abr
//
//Revision 1.7    2006/03/03 19:23:30    bala
//remove reference to Constants.CSS
//
//Revision 1.6    2006/02/23 16:51:12    joan
//fixes
//
//Revision 1.5    2006/02/22 23:44:46    joan
//changes
//
//Revision 1.4    2006/02/22 19:31:41    joan
//fixes
//
//Revision 1.3    2006/02/21 18:50:26    joan
//changes
//
//Revision 1.2    2006/02/20 18:20:26    joan
//fixes
//
//Revision 1.1    2006/02/20 17:45:32    joan
//initial load
//


package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.OPICMList;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;

/**
 * WWSEOABRWWMI
 *
 *@author         Administrator
 *@created        August 30, 2002
 */
public class WWSEOABRWWMI extends PokBaseABR {
    /**
    *    Execute ABR.
    *
    */

    // Class constants
    private final static String ABR = new String("WWSEOABRWWMI");
    private final static String EG_MODEL = new String("MODEL");
    private final static String EG_FMLY = new String("FMLY");
    private final static String EG_SER = new String("SER");
    private final static String EG_DERIVEDDATA = new String("DERIVEDDATA");
    private final static String EG_PROC = new String("PROC");
    private final static String EG_PLANAR = new String("PLANAR");
    private final static String EG_HDD = new String("HDD");
    private final static String EG_HDC = new String("HDC");
    private final static String EG_OPTCALDVC = new String("OPTCALDVC");
    private final static String EG_MECHPKG = new String("MECHPKG");
    private final static String SPACE = new String(" ");
    private final static String STAR = new String("*");
    private final static String ATT_SERNAM = new String("SERNAM");
    private final static String ATT_WWMASTERINDX = new String ("WWMASTERINDX");
        private final static String ATT_NOOFPROCSTD = new String("NOOFPROCSTD");
    private final static String ATT_CLOCKRATEUNIT = new String("CLOCKRATEUNIT");
    private final static String ATT_TOTL2CACHESTD = new String("TOTL2CACHESTD");
    private final static String ATT_MEMRYRAMSTD = new String("MEMRYRAMSTD");
    private final static String ATT_NOOFINSTHARDDRVS = new String("NOOFINSTHARDDRVS");
    private final static String ATT_HDDCAP = new String("HDDCAP");
    private final static String ATT_CAPUNIT = new String("CAPUNIT");


    private final static String CLOCKRATEUNIT_GHZ = new String("200");
	private final static String MFRNAM_AMD = new String("0020");
	private final static String OPTCALDRIVETYPE_CDROM = new String("0010");
    private EntityGroup m_egParent = null;
    private EntityItem m_ei = null;
    private PDGUtility m_utility = new PDGUtility();
    static {

    }
    /**
         * execute_run
         *
         * @author Owner
         */
        public void execute_run() {

        String strDgName = null;
        EntityGroup egMODEL = null;
        EntityGroup egFMLY = null;
        String RETURN = System.getProperty("line.separator");
        int iModelCase = -1;
        int iFamilyCase = -1;
        try {
            start_ABRBuild();
            // Build the report header
            buildReportHeaderII();

            m_egParent = m_elist.getParentEntityGroup();
            m_ei = m_egParent.getEntityItem(0);
            println("<br><b>WWSEO: " + m_ei.getKey() + "</b>");

            printNavigateAttributes(m_ei, m_egParent, true);
            setReturnCode(PASS);
            //System.out.println(ABR + "m_elist : " +    m_elist.dump(false));

            egMODEL = m_elist.getEntityGroup(EG_MODEL);
            egFMLY = m_elist.getEntityGroup(EG_FMLY);

            if (egMODEL == null || egMODEL.getEntityItemCount() <= 0) {
                setReturnCode(FAIL);
                println("<br /><font color=red>Failed. There are no MODELs linked to WWSEO.</font>");
            } else {
                for (int i=0; i < egMODEL.getEntityItemCount(); i++) {
                    EntityItem eiMODEL = egMODEL.getEntityItem(i);
                    String strCOFCAT = m_utility.getAttrValue(eiMODEL, "COFCAT");
                    String strCOFSUBCAT = m_utility.getAttrValue(eiMODEL, "COFSUBCAT");
                    String strCOFGRP = m_utility.getAttrValue(eiMODEL, "COFGRP");
                    D.ebug(D.EBUG_DETAIL, getABRVersion() + " eiMODEL: " + eiMODEL.getKey() + ", " + strCOFCAT + ", " + strCOFSUBCAT + ", " + strCOFGRP);
                    // checking MODEL.COFCAT = �Hardware� (100) and MODEL.COFSUBCAT = �System� (126) and MODEL.COFGRP = �Base� (150).
                    if (strCOFCAT.equals("100") && strCOFSUBCAT.equals("126") && strCOFGRP.equals("150")) {
                        iModelCase = 1;
                        break;
                    }
                }
            }

            if (egFMLY == null || egFMLY.getEntityItemCount() <= 0) {
                setReturnCode(FAIL);
                println("<br /><font color=red>Failed. There are no FAMILYs linked to WWSEO.</font>");
            } else {
                for (int i=0; i < egFMLY.getEntityItemCount(); i++) {
                    EntityItem eiFMLY = egFMLY.getEntityItem(i);
                    String strFAMILY = m_utility.getAttrValue(eiFMLY, "FAMILY");
                    D.ebug(D.EBUG_DETAIL, getABRVersion() + " eiFMLY: " + eiFMLY.getKey() + ", " + strFAMILY);

                    if (strFAMILY.equals("F00080")) {
                        // check FMLY.FAMILY = �Servers: IntelliStation� (F00080)
                        iFamilyCase = 1;
                        break;
                    } else if (strFAMILY.equals("F00030") || strFAMILY.equals("F01305") || strFAMILY.equals("F00050") || strFAMILY.equals("F01300") 
                    		|| strFAMILY.equals("F01306") || strFAMILY.equals("F01302") || strFAMILY.equals("F01301")) {
                        // check FMLY.FAMILY = {eServer: BladeCenter (F00030) |Lenovo BladeCenter(F01305)| eServer: xSeries (F00050) | Lenovo System x (F01300)} 
                    	// | Lenovo HyperScale (F01306)  | Lenovo Flex System (F01302) |IBM Flex System (F01301) }
                        iFamilyCase = 2;
                        break;

                    }
                }
            }

            if (getReturnCode() == PASS) {
                System.out.println(getABRVersion() + " iModelCase: " + iModelCase + ", iFamilyCase: " + iFamilyCase);
                if (iModelCase == 1 && iFamilyCase == 1) {
                    String strWWMASTERINDX = getValue(m_elist, ATT_WWMASTERINDX, 1);
                    String strPRCFILENAM = getValue(m_elist, "PRCFILENAM", 1);
                    System.out.println(getABRVersion() + " strWWMASTERINDX: " + strWWMASTERINDX + ", strPRCFILENAM: " + strPRCFILENAM);
                    OPICMList attList = new OPICMList();
                    attList.put(ATT_WWMASTERINDX, "WWMASTERINDX=" + strWWMASTERINDX);
                    attList.put("PRCFILENAM", "PRCFILENAM=" + strPRCFILENAM);
                    m_utility.updateAttribute(m_db, m_prof, m_ei, attList);
                } else if (iModelCase == 1 && iFamilyCase == 2) {
                    String strWWMASTERINDX = getValue(m_elist, ATT_WWMASTERINDX, 2);
                    String strPRCFILENAM = getValue(m_elist, "PRCFILENAM", 2);
                    System.out.println(getABRVersion() + " strWWMASTERINDX: " + strWWMASTERINDX + ", strPRCFILENAM: " + strPRCFILENAM);
                    OPICMList attList = new OPICMList();
                    attList.put(ATT_WWMASTERINDX, "WWMASTERINDX=" + strWWMASTERINDX);
                    attList.put("PRCFILENAM", "PRCFILENAM=" + strPRCFILENAM);
                    m_utility.updateAttribute(m_db, m_prof, m_ei, attList);
                } else {
					System.out.println(getABRVersion() + " doesn't satisfy the condition to have the attributes populated: " + m_ei.getKey());
				}
            }

        } catch (LockPDHEntityException le) {
            setReturnCode(UPDATE_ERROR);
            println(
                "<h3><font color=red>"
                    + ERR_IAB1007E
                    + "<br />"
                    + le.getMessage()
                    + "</font></h3>");
            logError(le.getMessage());
        } catch (UpdatePDHEntityException le) {
            setReturnCode(UPDATE_ERROR);
            println(
                "<h3><font color=red>UpdatePDH error: "
                    + le.getMessage()
                    + "</font></h3>");
            logError(le.getMessage());
        } catch (SBRException _sbrex) {
            String strError = _sbrex.toString();
            int i = strError.indexOf("(ok)");
            if (i < 0) {
                setReturnCode(UPDATE_ERROR);
                println(
                    "<h3><font color=red>Generate Data error: "
                        + replace(strError, RETURN, "<br>")
                        + "</font></h3>");
                logError(_sbrex.toString());
            } else {
                strError = strError.substring(0, i);
                println(replace(strError, RETURN, "<br>"));
            }
        } catch (Exception exc) {
            // Report this error to both the datbase log and the PrintWriter
            println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
            println("" + exc);
            exc.printStackTrace();
            // don't overwrite an update exception
            if (getABRReturnCode() != UPDATE_ERROR) {
                setReturnCode(INTERNAL_ERROR);
            }
        } finally {
            println(
                "<br /><b>"
                    + buildMessage(
                        MSG_IAB2016I,
                        new String[] {
                            getABRDescription(),
                            (getReturnCode() == PASS ? "Passed" : "Failed")})
                    + "</b>");

            log(
                buildLogMessage(
                    MSG_IAB2016I,
                    new String[] {
                        getABRDescription(),
                        (getReturnCode() == PASS ? "Passed" : "Failed")}));

            // set DG title
            strDgName = m_ei.toString();
            if (strDgName.length() > 64) {
                strDgName = strDgName.substring(0, 64);
            }
            setDGTitle(strDgName);
            setDGRptName(ABR);

            // set DG submit string
            setDGString(getABRReturnCode());
            printDGSubmitString();
            //Stuff into report for subscription and notification

            // Tack on the DGString
            buildReportFooter();
            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
        }
    }

    private String getString(String _str, int _i) {
        return (_str.length() > _i? _str.substring(0, _i): _str);
    }

    private String getValueDesc(EntityItem _ei, String _strAttrCode) {
        //System.out.println(getABRVersion() + " getValueDesc method" + _strAttrCode);
        if (_ei == null) {
            return "";
        }
        //System.out.println(getABRVersion() + " getValueDesc method 01 ");
        EANAttribute att = _ei.getAttribute(_strAttrCode);
        if (att != null) {
            //System.out.println(getABRVersion() + " getValueDesc method " + att.toString());
            return att.toString();
        }
        return "";
    }

    private String getValue(EntityList _el, String _strAttrCode, int _iCase) throws MiddlewareRequestException {
		String strTraceBase = "WWSEOABRWWMI getValue method ";
        StringBuffer sb = new StringBuffer();
        EntityGroup egSER = _el.getEntityGroup(EG_SER);
        T.est(egSER != null, "EntityGroup SER is null.");
        EntityItem eiSER = null;
        if (egSER.getEntityItemCount() > 0) {
            eiSER = egSER.getEntityItem(0);
            D.ebug(D.EBUG_DETAIL, strTraceBase + " eiSER: " + eiSER.dump(false));
        }
		if (eiSER == null) {
			System.out.println(strTraceBase + " eiSER is null ");
		}
        EntityGroup egDERIVEDDATA = _el.getEntityGroup(EG_DERIVEDDATA);
        T.est(egDERIVEDDATA != null, "EntityGroup DERIVEDDATA is null.");
        EntityItem eiDERIVEDDATA = null;
        if (egDERIVEDDATA.getEntityItemCount() > 0) {
            eiDERIVEDDATA = egDERIVEDDATA.getEntityItem(0);
            D.ebug(D.EBUG_DETAIL, strTraceBase + " eiDERIVEDDATA: " + eiDERIVEDDATA.dump(false));
        }

		if (eiDERIVEDDATA == null) {
			System.out.println(strTraceBase + " eiDERIVEDDATA is null ");
		}

        EntityGroup egPROC = _el.getEntityGroup(EG_PROC);
        T.est(egPROC != null, "EntityGroup PROC is null.");
        EntityItem eiPROC = null;
        if (egPROC.getEntityItemCount() > 0) {
            eiPROC = egPROC.getEntityItem(0);
            D.ebug(D.EBUG_DETAIL, strTraceBase + " eiPROC: " + eiPROC.dump(false));
        }

		if (eiPROC == null) {
			System.out.println(strTraceBase + " eiPROC is null ");
		}

        EntityGroup egPLANAR = _el.getEntityGroup(EG_PLANAR);
        T.est(egPLANAR != null, "EntityGroup PLANAR is null.");
        EntityItem eiPLANAR = null;
        if (egPLANAR.getEntityItemCount() > 0) {
            eiPLANAR = egPLANAR.getEntityItem(0);
            D.ebug(D.EBUG_DETAIL, strTraceBase + " eiPLANAR: " + eiPLANAR.dump(false));
        }

		if (eiPLANAR == null) {
			System.out.println(strTraceBase + " eiPLANAR is null ");
		}

        EntityGroup egHDD = _el.getEntityGroup(EG_HDD);
        T.est(egHDD != null, "EntityGroup HDD is null.");
        EntityItem eiHDD = null;
        if (egHDD.getEntityItemCount() > 0) {
            eiHDD = egHDD.getEntityItem(0);
            D.ebug(D.EBUG_DETAIL, strTraceBase + " eiHDD: " + eiHDD.dump(false));
        }
		if (eiHDD == null) {
			System.out.println(strTraceBase + " eiHDD is null ");
		}

        EntityGroup egHDC = _el.getEntityGroup(EG_HDC);
        T.est(egHDC != null, "EntityGroup HDC is null.");
        EntityItem eiHDC = null;
        if (egHDC.getEntityItemCount() > 0) {
            eiHDC = egHDC.getEntityItem(0);
            D.ebug(D.EBUG_DETAIL, strTraceBase + " eiHDC: " + eiHDC.dump(false));
        }
		if (eiHDC == null) {
			System.out.println(strTraceBase + " eiHDC is null ");
		}

        EntityGroup egOPTCALDVC = _el.getEntityGroup(EG_OPTCALDVC);
        T.est(egOPTCALDVC != null, "EntityGroup OPTCALDVC is null.");
        EntityItem eiOPTCALDVC = null;
        if (egOPTCALDVC.getEntityItemCount() > 0) {
            eiOPTCALDVC = egOPTCALDVC.getEntityItem(0);
            D.ebug(D.EBUG_DETAIL, strTraceBase + " eiOPTCALDVC: " + eiOPTCALDVC.dump(false));
        }
		if (eiOPTCALDVC == null) {
			System.out.println(strTraceBase + " eiOPTCALDVC is null ");
		}

        EntityGroup egMECHPKG = _el.getEntityGroup(EG_MECHPKG);
        T.est(egMECHPKG != null, "EntityGroup MECHPKG is null.");
        EntityItem eiMECHPKG = null;
        if (egMECHPKG.getEntityItemCount() > 0) {
            eiMECHPKG = egMECHPKG.getEntityItem(0);
            D.ebug(D.EBUG_DETAIL, strTraceBase + " eiMECHPKG: " + eiMECHPKG.dump(false));
        }
		if (eiMECHPKG == null) {
			System.out.println(strTraceBase + " eiMECHPKG is null ");
		}

        if (_iCase == 1) {
            D.ebug(D.EBUG_DETAIL, getABRVersion() + " in case 1");
            if (_strAttrCode.equals(ATT_WWMASTERINDX)) {
                D.ebug(D.EBUG_DETAIL, strTraceBase + " for WWMASTERINDX.");
                //1.    Series Name (SER.SERNAM)
                //2.    �#*#�
                if (eiSER != null) {
                    sb.append(getValueDesc(eiSER, "SERNAM"));
                    sb.append(SPACE + STAR + SPACE);
                }
				D.ebug(D.EBUG_DETAIL, strTraceBase + " after 1 and 2: " + sb.toString());

                //3.    If DERIVEDDATA.NOOFPROCSTD > 1, then NOOFPROCSTD & �x� else null
                if (eiDERIVEDDATA != null) {
                    if (isDigit(m_utility.getAttrValue(eiDERIVEDDATA, ATT_NOOFPROCSTD))) {
                        int iNOOFPROCSTD = Integer.parseInt(m_utility.getAttrValue(eiDERIVEDDATA, ATT_NOOFPROCSTD));
                        if (iNOOFPROCSTD > 1) {
                            sb.append(iNOOFPROCSTD + "x");
                        }
                    }
                } else {
					System.out.println(getABRVersion() + " eiDERIVEDDATA is null ");
				}
				D.ebug(D.EBUG_DETAIL, strTraceBase + " after 3: " + sb.toString());

                //4.    If PROC.MFRNAM = �AMD� (0020), then Short Description for MFRNAM (0020) & �#�
                //else LEFT(PROC.CLOCKRATE,4) & if(PROC.CLOCKRATEUNIT=GHz (0020), �GHz�, null)
                //5.    �# �
                String strMFRNAM = m_utility.getAttrValue(eiPROC, "MFRNAM");
                if (strMFRNAM.equals(MFRNAM_AMD)) {
                    sb.append(m_utility.getAttrValueDesc(eiPROC, "MFRNAM"));
                } else {
                    String strCLOCKRATEUNIT = m_utility.getAttrValue(eiPROC, ATT_CLOCKRATEUNIT);
                    sb.append(getString(getValueDesc(eiPROC, "CLOCKRATE"), 4));
                    sb.append(strCLOCKRATEUNIT.equals(CLOCKRATEUNIT_GHZ)? m_utility.getAttrValueDesc(eiPROC, ATT_CLOCKRATEUNIT): "");
                }
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 4 and 5: " + sb.toString());

                //6.    Left(DERIVEDDATA.TOTL2CACHESTD,4) & DERIVEDDATA.TOTL2CACHESTDUNIT
                //7.    �#�
                sb.append(getString(getValueDesc(eiDERIVEDDATA, ATT_TOTL2CACHESTD), 4));
                sb.append(getValueDesc(eiDERIVEDDATA, "TOTL2CACHESTDUNIT"));
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 6 and 7: " + sb.toString());

                //8.    If PLANAR.L3CACHE is not �null� (i.e. specified),then �L2#� & LEFT(PLANAR.L3CACHE,4) & PLANAR.L3CACHEUNIT
                //9.    �#�
                String strL3CACHE = m_utility.getAttrValue(eiPLANAR, "L3CACHE");
                sb.append(strL3CACHE.length() > 0? ("L2" + SPACE + getString(getValueDesc(eiPLANAR,"L3CACHE"), 4) + getValueDesc(eiPLANAR, "L3CACHEUNIT")) : "");
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 8 and 9: " + sb.toString());

                //10. Left(MEMRYRAMSTD,4) & MEMRYRAMSTDUNIT
                //11. �#�
                sb.append(getString(getValueDesc(eiDERIVEDDATA, ATT_MEMRYRAMSTD), 4));
                sb.append(getValueDesc(eiDERIVEDDATA, "MEMRYRAMSTDUNIT"));
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 10 and 11: " + sb.toString());

                //12. HDD.HDDCAP & HDD.CAPUNIT
                //13. �#�
                sb.append(getValueDesc(eiHDD, ATT_HDDCAP));
                sb.append(getValueDesc(eiHDD, ATT_CAPUNIT));
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 12 and 13: " + sb.toString());

                //14. Left(HDC.BUS,4)
                //15. �#�
                sb.append(getString(getValueDesc(eiHDC, "BUS"), 4));
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 14 and 15: " + sb.toString());

                //16. If OPTCALDRIVETYPE = �CD-ROM� (0010),then left(CDROMSPED,3) else CDROMSPED
                //17. �#�
                String strOPTCALDRIVETYPE = m_utility.getAttrValue(eiOPTCALDVC, "OPTCALDRIVETYPE");
                if (strOPTCALDRIVETYPE.equals(OPTCALDRIVETYPE_CDROM)) {
                    sb.append(getString(getValueDesc(eiOPTCALDVC, "CDROMSPED"), 3));
                } else {
                    sb.append(getValueDesc(eiOPTCALDVC, "CDROMSPED"));
                }
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 16 and 17: " + sb.toString());

                //18. TOTCARDSLOT & �x�
                //19. �#�
                sb.append(getValueDesc(eiPLANAR, "TOTCARDSLOT") + "x");
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 18 and 19: " + sb.toString());

                //20. MECHPKG.TOTBAY
                sb.append(getValueDesc(eiMECHPKG, "TOTBAY"));
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 20: " + sb.toString());
            } else if (_strAttrCode.equals("PRCFILENAM")) {
                D.ebug(D.EBUG_DETAIL, strTraceBase + " for PRCFILENAM.");
                //1.    Series Name (SER.SERNAM)
                //2.    �#�
                sb.append(getValueDesc(eiSER, ATT_SERNAM));
                sb.append(SPACE + STAR + SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 1 and 2: " + sb.toString());

                //3.    If DERIVEDDATA.NOOFPROCSTD > 1, then NOOFPROCSTD & �x� else null
                if (isDigit(m_utility.getAttrValue(eiDERIVEDDATA, ATT_NOOFPROCSTD))) {
                    int iNOOFPROCSTD = Integer.parseInt(m_utility.getAttrValue(eiDERIVEDDATA, ATT_NOOFPROCSTD));
                    if (iNOOFPROCSTD > 1) {
                        sb.append(iNOOFPROCSTD + "x");
                    }
                }
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 3: " + sb.toString());

                //4.    LEFT(PROC.CLOCKRATE,4) & if(PROC.CLOCKRATEUNIT=GHz (0020), �G�, null)
                //5.    �#�
                String strCLOCKRATEUNIT = m_utility.getAttrValue(eiPROC, ATT_CLOCKRATEUNIT);
                sb.append(getString(getValueDesc(eiPROC, "CLOCKRATE"), 4));
                sb.append(strCLOCKRATEUNIT.equals(CLOCKRATEUNIT_GHZ)? m_utility.getAttrValueDesc(eiPROC, ATT_CLOCKRATEUNIT): "");
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 4 and 5: " + sb.toString());

                //6.    Left(DERIVEDDATA.TOTL2CACHESTD,4)
                //7.    �#�
                sb.append(getString(getValueDesc(eiDERIVEDDATA, ATT_TOTL2CACHESTD), 4));
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 6 and 7: " + sb.toString());

                //8.    Left(MEMRYRAMSTD,4)
                sb.append(getString(getValueDesc(eiDERIVEDDATA, ATT_MEMRYRAMSTD), 4));
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 8: " + sb.toString());

                //9.    �/� & HDD.HDDCAP
                sb.append("/" + getValueDesc(eiHDD, ATT_HDDCAP));
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 9: " + sb.toString());

                //10. If OPTCALDRIVETYPE = �CD-ROM� (0010),then left(CDROMSPED,3) else CDROMSPED
                String strOPTCALDRIVETYPE = m_utility.getAttrValue(eiOPTCALDVC, "OPTCALDRIVETYPE");
                if (strOPTCALDRIVETYPE.equals(OPTCALDRIVETYPE_CDROM)) {
                    sb.append(getString(getValueDesc(eiOPTCALDVC, "CDROMSPED"), 3));
                } else {
                    sb.append(getValueDesc(eiOPTCALDVC, "CDROMSPED"));
                }
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 10: " + sb.toString());
            }
        } else if (_iCase == 2) {
            D.ebug(D.EBUG_DETAIL, strTraceBase + " in case 2");
            if (_strAttrCode.equals(ATT_WWMASTERINDX)) {
				D.ebug(D.EBUG_DETAIL, strTraceBase + " for WWMASTERINDX.");
                //1.    Series Name (SER.SERNAM)
                //2.    �#*#�
                String strSerName = getValueDesc(eiSER, ATT_SERNAM);
                if (strSerName.indexOf("IBM eServer BladeCenter") >= 0) {
					strSerName = replace(strSerName, "IBM eServer BladeCenter", "BC");
				} else if (strSerName.indexOf("IBM BladeCenter") >= 0) {
					strSerName = replace(strSerName, "IBM BladeCenter", "BC");
				} else if (strSerName.indexOf("BladeCenter") >= 0) {
					strSerName = replace(strSerName, "BladeCenter", "BC");
				} else if (strSerName.indexOf("Lenovo HyperScale") >= 0) {
					strSerName = replace(strSerName, "Lenovo HyperScale", "HS");
				} else if (strSerName.indexOf("Lenovo Flex System") >= 0) {
					strSerName = replace(strSerName, "Lenovo Flex System", "FS");
				} else if (strSerName.indexOf("IBM Flex System") >= 0) {
					strSerName = replace(strSerName, "IBM Flex System", "FS");
				} else if (strSerName.indexOf("xSeries") >= 0) {
					strSerName = replace(strSerName, "xSeries", "x");
				} else if (strSerName.indexOf("System x") >= 0) {
					strSerName = replace(strSerName, "System x", "x");
				} 
                strSerName = removeIBMSubString(strSerName);
                
                sb.append(strSerName);
                sb.append(SPACE + STAR + SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 1 and 2: " + sb.toString());

                //3.    If DERIVEDDATA.NOOFPROCSTD > 1, then NOOFPROCSTD & �x� else null
                if (isDigit(m_utility.getAttrValue(eiDERIVEDDATA, ATT_NOOFPROCSTD))) {
                    int iNOOFPROCSTD = Integer.parseInt(m_utility.getAttrValue(eiDERIVEDDATA, ATT_NOOFPROCSTD));
                    if (iNOOFPROCSTD > 1) {
                        sb.append(iNOOFPROCSTD + "x");
                    }
                }
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 3: " + sb.toString());

                //4.    If PROC.MFRNAM = �AMD� (0020), then Short Description for MFRNAM (0020) & �#�
                //else LEFT(PROC.CLOCKRATE,4) & if(PROC.CLOCKRATEUNIT=GHz (0020), �GHz�, null)
                //5.    �# �
                String strMFRNAM = m_utility.getAttrValue(eiPROC, "MFRNAM");
                if (strMFRNAM.equals(MFRNAM_AMD)) {
                    sb.append(m_utility.getAttrValueDesc(eiPROC, "MFRNAM"));
                } else {
                    String strCLOCKRATEUNIT = m_utility.getAttrValue(eiPROC, ATT_CLOCKRATEUNIT);
                    sb.append(getString(getValueDesc(eiPROC, "CLOCKRATE"), 4));
                    sb.append(strCLOCKRATEUNIT.equals(CLOCKRATEUNIT_GHZ)? m_utility.getAttrValueDesc(eiPROC, ATT_CLOCKRATEUNIT): "");
                }
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 4 and 5: " + sb.toString());

                //6.    Left(DERIVEDDATA.TOTL2CACHESTD,4) & DERIVEDDATA.TOTL2CACHESTDUNIT
                //7.    �#�
                sb.append(getString(getValueDesc(eiDERIVEDDATA, ATT_TOTL2CACHESTD), 4));
                sb.append(getValueDesc(eiDERIVEDDATA, "TOTL2CACHESTDUNIT"));
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 6 and 7: " + sb.toString());

                //8.    Left(MEMRYRAMSTD,4) & MEMRYRAMSTDUNIT
                //9.    �#�
                sb.append(getString(getValueDesc(eiDERIVEDDATA, ATT_MEMRYRAMSTD), 4));
                sb.append(getValueDesc(eiDERIVEDDATA, "MEMRYRAMSTDUNIT"));
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 8 and 9: " + sb.toString());

                //10. If DERIVEDDATA.NOOFINSTHARDDRVS > 1, then NOOFINSTHARDDRVS & �x�
                if (isDigit(m_utility.getAttrValue(eiDERIVEDDATA, ATT_NOOFINSTHARDDRVS))) {
                    int iNOOFINSTHARDDRVS = Integer.parseInt(m_utility.getAttrValue(eiDERIVEDDATA, ATT_NOOFINSTHARDDRVS));
                    if (iNOOFINSTHARDDRVS > 1) {
                        sb.append(iNOOFINSTHARDDRVS + "x");
                    }
                }
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 10: " + sb.toString());

                //11. HDD.HDDCAP & HDD.CAPUNIT
                //12. �#�
                sb.append(getValueDesc(eiHDD, ATT_HDDCAP));
                sb.append(getValueDesc(eiHDD, ATT_CAPUNIT));
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 11 and 12: " + sb.toString());

                //13. Left(HDC.BUS,4)
                //14. �#�
                sb.append(getString(getValueDesc(eiHDC, "BUS"), 4));
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 13 and 14: " + sb.toString());

                //15. TOTCARDSLOT & �x�
                //16. �#�
                sb.append(getValueDesc(eiPLANAR, "TOTCARDSLOT") + "x");
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 15 and 16: " + sb.toString());

                //17. MECHPKG.TOTBAY
                sb.append(getValueDesc(eiMECHPKG, "TOTBAY"));
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 17: " + sb.toString());
            } else if (_strAttrCode.equals("PRCFILENAM")) {
				D.ebug(D.EBUG_DETAIL, strTraceBase + " for PRCFILENAM.");
                //1.    Series Name (SER.SERNAM)
                //2.    �#�
                String strSerName = getValueDesc(eiSER, ATT_SERNAM);
                if (strSerName.indexOf("IBM eServer BladeCenter") >= 0) {
					strSerName = replace(strSerName, "IBM eServer BladeCenter", "BC");
				} else if (strSerName.indexOf("IBM BladeCenter") >= 0) {
					strSerName = replace(strSerName, "IBM BladeCenter", "BC");
				} else if (strSerName.indexOf("BladeCenter") >= 0) {
					strSerName = replace(strSerName, "BladeCenter", "BC");
				} else if (strSerName.indexOf("Lenovo HyperScale") >= 0) {
					strSerName = replace(strSerName, "Lenovo HyperScale", "HS");
				} else if (strSerName.indexOf("Lenovo Flex System") >= 0) {
					strSerName = replace(strSerName, "Lenovo Flex System", "FS");
				} else if (strSerName.indexOf("IBM Flex System") >= 0) {
					strSerName = replace(strSerName, "IBM Flex System", "FS");
				} else if (strSerName.indexOf("xSeries") >= 0) {
					strSerName = replace(strSerName, "xSeries", "x");
				} else if (strSerName.indexOf("System x") >= 0) {
					strSerName = replace(strSerName, "System x", "x");
				} 
                strSerName = removeIBMSubString(strSerName);
                
                sb.append(strSerName);

                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 1 and 2: " + sb.toString());

                //3.    If DERIVEDDATA.NOOFPROCSTD > 1, then NOOFPROCSTD & �x� else null
                if (isDigit(m_utility.getAttrValue(eiDERIVEDDATA, ATT_NOOFPROCSTD))) {
                    int iNOOFPROCSTD = Integer.parseInt(m_utility.getAttrValue(eiDERIVEDDATA, ATT_NOOFPROCSTD));
                    if (iNOOFPROCSTD > 1) {
                        sb.append(iNOOFPROCSTD + "x");
                    }
                }
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 3: " + sb.toString());

                //4.    LEFT(PROC.CLOCKRATE,4) & if(PROC.CLOCKRATEUNIT=GHz (0020), �G�, null)
                //5.    �#�
                String strCLOCKRATEUNIT = m_utility.getAttrValue(eiPROC, ATT_CLOCKRATEUNIT);
                sb.append(getString(getValueDesc(eiPROC, "CLOCKRATE"), 4));
                sb.append(strCLOCKRATEUNIT.equals(CLOCKRATEUNIT_GHZ)? m_utility.getAttrValueDesc(eiPROC, ATT_CLOCKRATEUNIT): "");
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 4 and 5: " + sb.toString());

                //6.    Left(DERIVEDDATA.TOTL2CACHESTD,4)
                //7.    �#�
                sb.append(getString(getValueDesc(eiDERIVEDDATA, ATT_TOTL2CACHESTD), 4));
                sb.append(SPACE);
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 6 and 7: " + sb.toString());

                //8.    Left(MEMRYRAMSTD,4)
                sb.append(getString(getValueDesc(eiDERIVEDDATA, ATT_MEMRYRAMSTD), 4));
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 8: " + sb.toString());

                //9.    �/� & HDD.HDDCAP
                sb.append("/" + getValueDesc(eiHDD, ATT_HDDCAP));
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 9: " + sb.toString());

                //10. If DERIVEDDATA.NOOFINSTHARDDRVS > 1, then NOOFINSTHARDDRVS & �x�
                if (isDigit(m_utility.getAttrValue(eiDERIVEDDATA, ATT_NOOFINSTHARDDRVS))) {
                    int iNOOFINSTHARDDRVS = Integer.parseInt(m_utility.getAttrValue(eiDERIVEDDATA, ATT_NOOFINSTHARDDRVS));
                    if (iNOOFINSTHARDDRVS > 1) {
                        sb.append(iNOOFINSTHARDDRVS + "x");
                    }
                }
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 10: " + sb.toString());
                //11. HDD.HDDCAP & HDD.CAPUNIT
                sb.append(getValueDesc(eiHDD, ATT_HDDCAP));
                sb.append(getValueDesc(eiHDD, ATT_CAPUNIT));
                D.ebug(D.EBUG_DETAIL, strTraceBase + " after 11: " + sb.toString());
            }

        }
        return sb.toString();
    }

    private String removeIBMSubString(String s) {
    	while(s.indexOf("IBM ") >= 0) {
    		s = replace(s, "IBM ", "");
    	}
    	while(s.indexOf("IBM") >= 0) {
    		s = replace(s, "IBM", "");
    	}
    	return s;
    }
    
    private String replace(String _s, String _s1, String _s2) {
        String sResult = "";
        int iTab = _s.indexOf(_s1);

        while (_s.length() > 0 && iTab >= 0) {
            sResult = sResult + _s.substring(0, iTab) + _s2;
            _s = _s.substring(iTab + _s1.length());
            iTab = _s.indexOf(_s1);
        }
        sResult = sResult + _s;
        return sResult;
    }

    /**
    *    Get the entity description to use in error messages
    *
    *@param    entityType    Description of the Parameter
    *@param    entityId        Description of the Parameter
    *@return                         String
    */
    protected String getABREntityDesc(String entityType, int entityId) {
        return null;
    }

    /**
     *    Get ABR description
     *
     *@return        java.lang.String
     */
    public String getDescription() {
        return "WWSEO ALWR With CD ABR.";
    }

    /**
     *    Get any style that should be used for this page. Derived classes can
     *    override this to set styles They must include the <style>...</style> tags
     *
     *@return        String
     */
    protected String getStyle() {
        // Print out the PSG stylesheet
        return "";
    }

    /**
         * getRevision
         *
         * @return
         * @author Owner
         */
        public String getRevision() {
        return new String("1.14");
    }

    /**
         * getVersion
         *
         * @return
         * @author Owner
         */
        public static String getVersion() {
        return ("WWSEOABRWWMI.java,v 1.14 2008/01/30 19:39:15 wendy Exp");
    }

    /**
         * getABRVersion
         *
         * @return
         * @author Owner
         */
        public String getABRVersion() {
        return "WWSEOABRWWMI.java";
    }
}
