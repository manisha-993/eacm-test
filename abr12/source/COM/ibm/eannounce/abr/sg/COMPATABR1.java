//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//COMPATABR1.java,v
//Revision 1.14  2008/01/30 20:02:00  wendy
//Cleanup RSA warnings
//
//Revision 1.13  2006/05/12 18:22:38  yang
//adding extra log
//
//Revision 1.12  2006/05/10 16:03:16  joan
//fixes
//
//Revision 1.11  2006/05/08 19:46:25  joan
//fixes
//
//Revision 1.10  2006/05/07 15:52:55  joan
//fixes
//
//Revision 1.9  2006/05/07 14:24:36  joan
//fixes
//
//Revision 1.8  2006/05/06 17:23:43  joan
//changes
//
//Revision 1.7  2006/05/05 22:52:15  joan
//changes
//
//Revision 1.6  2006/03/30 22:11:53  joan
//fixes
//
//Revision 1.5  2006/03/30 21:54:52  joan
//fixes
//
//Revision 1.4  2006/03/23 21:20:46  joan
//changes
//
//Revision 1.3  2006/03/23 02:05:33  joan
//fixes
//
//Revision 1.2  2006/03/22 21:42:05  joan
//fix compile
//
//Revision 1.1  2006/03/22 21:39:01  joan
//add new abr
//
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;
import java.io.*;

/**
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class COMPATABR1 extends PokBaseABR {
  /**
  *  Execute ABR.
  *
  */

  // Class constants
    public final static String ABR = new String("COMPATABR1");
    private final static String ROOT_ENTITY = new String("MODEL");

    private final static String EG_MDLCGOSMDL = new String("MDLCGOSMDL");
    private final static String EG_MODEL = new String("MODEL");
    private final static String ATT_ANNDATE = new String("ANNDATE");
    private final static String ATT_MT = new String("MACHTYPEATR");
    private final static String ATT_MODEL = new String("MODELATR");
    private final static String ATT_PDHDOMAIN = new String("PDHDOMAIN");
    private final static String ATT_OS = new String("OS");
    private final static int MODEL1 = 1;
    private final static int MODEL2 = 2;

    private EntityItem m_ei = null;
    private EntityList m_el = null;
    private PDGUtility m_utility = new PDGUtility();
    private boolean m_bAlert1 = false;
    private boolean m_bAlert2 = false;
    private boolean m_bError = false;
    private OPICMList m_cat2List = new OPICMList();
    private ExtractActionItem m_xai1 = null;
    private ExtractActionItem m_xai2 = null;


    public void execute_run() {
        try {
            EntityGroup egMODEL = null;
            EntityGroup egParent = null;
            EntityGroup egMDLCGOSMDL = null;
            start_ABRBuild(false);
            buildReportHeaderII();
            m_xai1 = new ExtractActionItem(null, m_db, m_prof, m_abri.getVEName());
            //Hashtable hshMap = m_xai1.generateVESteps(m_db, m_prof, EG_MODEL);
            m_xai2 = new ExtractActionItem(null, m_db, m_prof, "EXTCOMPATABR01");

            setReturnCode(PASS);
            m_ei = new EntityItem(null, m_prof, m_abri.getEntityType(), m_abri.getEntityID());
            if (m_ei.getEntityType().equals(EG_MDLCGOSMDL)) {
                EntityItem[] aeiRootModel = getRootModel(m_db, m_prof, m_ei);

                if (aeiRootModel == null || aeiRootModel.length <= 0) {
                    setCreateDGEntity(false);
                    System.out.println(getABRVersion() + " not in compatibility " + m_ei.getKey());
                    return;
                }

                m_el = EntityList.getEntityList(m_db, m_prof, m_xai1, aeiRootModel);

                egMODEL = m_el.getEntityGroup(EG_MODEL);
                egMDLCGOSMDL = m_el.getEntityGroup(EG_MDLCGOSMDL);
                egParent = m_el.getParentEntityGroup();

                m_ei = egMDLCGOSMDL.getEntityItem(m_ei.getKey());
                printNavigateAttributes(m_ei, egMDLCGOSMDL, true);
                for (int i=0; i < egParent.getEntityItemCount(); i++) {
                    EntityItem eiRoot = egParent.getEntityItem(i);
                    if (m_ei.getEntityType().equals(EG_MDLCGOSMDL)) {
                        // Case 1 updating MDLCGOSMDL ANNDATE
                        EntityItem eiMDL = egMDLCGOSMDL.getEntityItem(m_ei.getKey());

                        if (eiMDL != null) {
                            EntityItem eiModel1 = egParent.getEntityItem(eiRoot.getKey());
                            EntityItem eiModel2 = (EntityItem) eiMDL.getDownLink(0);
                            String strMDLCGOSMDLANNDATE = m_utility.getAttrValue(eiMDL, ATT_ANNDATE);
                            String strPreviousValue = getPreviousValue(eiMDL, ATT_ANNDATE);
                            if (strMDLCGOSMDLANNDATE != null && strMDLCGOSMDLANNDATE.length() > 0) {
                                String strMODEL1ANNDATE = m_utility.getAttrValue(eiModel1, ATT_ANNDATE);
                                int iDC1 = PDGUtility.LATER;

                                if (strMODEL1ANNDATE != null && strMODEL1ANNDATE.length() > 0) {
                                    iDC1 = m_utility.dateCompare(strMDLCGOSMDLANNDATE, strMODEL1ANNDATE);
                                }

                                int iDC2 = PDGUtility.LATER;
                                String strMODEL2ANNDATE = m_utility.getAttrValue(eiModel2, ATT_ANNDATE);
                                if (strMODEL2ANNDATE != null && strMODEL2ANNDATE.length() > 0) {
                                    iDC2 = m_utility.dateCompare(strMDLCGOSMDLANNDATE, strMODEL2ANNDATE);
                                }

                                int iDC3 = m_utility.dateCompare(strMODEL1ANNDATE, strMODEL2ANNDATE);
                                if (iDC3 == PDGUtility.EARLIER) {
                                    if (iDC1 != PDGUtility.LATER || iDC2 != PDGUtility.LATER) {
                                        println("<br>MODEL1.ANNDATE is earlier than MODEL2.ANNDATE");
                                        println("<br><b><font color=red>Error. MDLCGOSMDL.ANNDATE is changed to be earlier than or equal to MODEL 2. ANNDATE and/or MODEL 1. ANNDATE</font></b>");
                                        println("<br>" + printTable(eiModel1, eiModel2, eiMDL, (EntityItem)eiMDL.getUpLink(0), strPreviousValue, 1));
                                        m_bError = true;
                                        m_bAlert1 = true;
                                        getPDHDomain(eiModel1, MODEL1);
                                    } else {
                                        println("<br>MODEL1.ANNDATE is earlier than MODEL2.ANNDATE");
                                        println("<br><b>MDLCGOSMDL.ANNDATE is changed.</b>");
                                        println("<br>" + printTable(eiModel1, eiModel2, eiMDL, (EntityItem)eiMDL.getUpLink(0), strPreviousValue, 1));
                                        m_bAlert2 = true;
                                        getPDHDomain(eiModel2, MODEL2);
                                    }
                                } else if (iDC3 == PDGUtility.LATER) {
                                    if (iDC1 != PDGUtility.LATER || iDC2 != PDGUtility.LATER) {
                                        println("<br>MODEL2.ANNDATE is earlier than MODEL1.ANNDATE");
                                        println("<br><b><font color=red>Error. MDLCGOSMDL.ANNDATE is changed to be earlier than or equal to MODEL 2. ANNDATE and/or MODEL 1. ANNDATE</font></b>");
                                        println("<br>" + printTable(eiModel1, eiModel2, eiMDL, (EntityItem)eiMDL.getUpLink(0), strPreviousValue, 1));
                                        m_bError = true;
                                        m_bAlert1 = true;
                                        getPDHDomain(eiModel1, MODEL1);
                                    } else {
                                        //Override (MDLCGOSMDL.ANNDATE) Legal Change to the Override Date
                                        //the override could be made Earlier or Later than it had been
                                        //Alert MODEL 2. PDHDOMAIN
                                        println("<br>MODEL2.ANNDATE is earlier than MODEL1.ANNDATE");
                                        println("<br><b>MDLCGOSMDL.ANNDATE is changed.</font></b>");
                                        println("<br>" + printTable(eiModel1, eiModel2, eiMDL, (EntityItem)eiMDL.getUpLink(0), strPreviousValue, 1));
                                        m_bAlert2 = true;
                                        getPDHDomain(eiModel2, MODEL2);
                                    }
                                }
                            } else {
                                if (strPreviousValue != null && strPreviousValue.length() > 0) {
                                    m_bAlert2 = true;
                                    println("<br>MDLCGOSMDL.ANNDATE is removed.");
                                    println("<br>" + printTable(eiModel1, eiModel2, eiMDL, (EntityItem)eiMDL.getUpLink(0), strPreviousValue, 1));
                                    getPDHDomain(eiModel2, MODEL2);
                                }
                            }
                        }
                    }
                }
            } else if (m_ei.getEntityType().equals(EG_MODEL)) {
                // check whether MODEL1s get changed
                EntityItem[] aei = {m_ei};

                m_el = EntityList.getEntityList(m_db, m_prof, m_xai1, aei);

                egMODEL = m_el.getEntityGroup(EG_MODEL);
                egMDLCGOSMDL = m_el.getEntityGroup(EG_MDLCGOSMDL);
                egParent = m_el.getParentEntityGroup();

                m_ei = egParent.getEntityItem(m_ei.getKey());
                printNavigateAttributes(m_ei, egParent, true);
                T.est(egMODEL != null, "EntityGroup MODEL is null for EXTTECHCOMPMAINT1");
                if (egMODEL != null && egMODEL.getEntityItemCount() > 0) {
                    // MODEL1.ANNDATE gets changed
                    EntityItem eiModel1 = egParent.getEntityItem(m_ei.getKey());
                    String strMODEL1ANNDATE = m_utility.getAttrValue(eiModel1, ATT_ANNDATE);
                    String strPreviousDate = getPreviousValue(eiModel1, ATT_ANNDATE);

                    for (int j=0; j < egMODEL.getEntityItemCount(); j++) {
                        EntityItem eiModel2 = egMODEL.getEntityItem(j);
                        String strMODEL2ANNDATE = m_utility.getAttrValue(eiModel2, ATT_ANNDATE);
                        EntityItem eiMDL = getMDLCGOSMDL(m_el, eiModel1, eiModel2);
                        if(eiMDL == null) {
                            continue;
                        }

                        String strMDLCGOSMDLANNDATE = m_utility.getAttrValue(eiMDL, ATT_ANNDATE);

                        int iDC1 = m_utility.dateCompare(strPreviousDate, strMODEL2ANNDATE);
                        int iDC2 = m_utility.dateCompare(strMODEL1ANNDATE, strMODEL2ANNDATE);

                        if (iDC1 == PDGUtility.EARLIER) {
                            //Case 2 - previous MODEL 1.ANNDATE is earlier than MODEL 2.ANNDATE
                            if (iDC2 == PDGUtility.EARLIER) {
                                //change MODEL 1. ANNDATE   earlier than MODEL 2. ANNDATE   OK
                                System.out.println(getABRVersion() + " MODEL 1. ANNDATE earlier than MODEL 2. ANNDATE   OK " + eiModel1.getKey());
                            } else if(iDC2 == PDGUtility.LATER) {
                                //change MODEL 1. ANNDATE   later than MODEL 2. ANNDATE
                                //Alert Both MODEL 1. PDHDOMAIN and MODEL 2. PDHDOMAIN
                                m_bAlert1 = true;
                                m_bAlert2 = true;
                                println("<br>Previous MODEL1.ANNDATE is earlier than MODEL2.ANNDATE." );
                                println("<br><b>MODEL 1. ANNDATE is changed to be later than MODEL 2. ANNDATE.</b>" );
                                println("<br>" + printTable(eiModel1, eiModel2, eiMDL, (EntityItem)eiMDL.getUpLink(0), strPreviousDate, 2));
                                getPDHDomain(eiModel1, MODEL1);
                                getPDHDomain(eiModel2, MODEL2);
                            }
                        } else if (iDC1 == PDGUtility.LATER) {
                            //Case 3 MODEL2.ANNDATE is earlier than previous MODEL1.ANNDATE
                            if (iDC2 == PDGUtility.EARLIER) {
                                //CHANGE MODEL 1. ANNDATE   earlier than MODEL 2. ANNDATE
                                //Alert Both MODEL 1. PDHDOMAIN and MODEL 2. PDHDOMAIN
                                m_bAlert1 = true;
                                m_bAlert2 = true;
                                println("<br>Previous MODEL1.ANNDATE is later than MODEL2.ANNDATE." );
                                println("<br><b>MODEL 1. ANNDATE is changed to be earlier than MODEL 2. ANNDATE.</b>" );
                                println("<br>" + printTable(eiModel1, eiModel2, eiMDL, (EntityItem)eiMDL.getUpLink(0), strPreviousDate, 2));
                                getPDHDomain(eiModel1, MODEL1);
                                getPDHDomain(eiModel2, MODEL2);
                            } else if (iDC2 == PDGUtility.LATER) {
                                //Case 3 previous MODEL 2.ANNDATE is earlier than MODEL 1.ANNDATE
                                //CHANGE MODEL 1. ANNDATE   later than MODEL 2. ANNDATE
                                //Alert MODEL 2. PDHDOMAIN
                                m_bAlert2 = true;
                                println("<br>Previous MODEL1.ANNDATE is later than MODEL2.ANNDATE." );
                                println("<br>MODEL 1. ANNDATE is changed to be later than MODEL 2. ANNDATE." );
                                println("<br>" + printTable(eiModel1, eiModel2, eiMDL, (EntityItem)eiMDL.getUpLink(0), strPreviousDate, 2));
                                getPDHDomain(eiModel2, MODEL2);
                            }
                        }

                        if (strMDLCGOSMDLANNDATE != null && strMDLCGOSMDLANNDATE.length() > 0) {
                            //Case 4 - a valid Override (MDLCGOSMDL.ANNDATE) date exists
                            int iDC4 = m_utility.dateCompare(strMODEL1ANNDATE, strMDLCGOSMDLANNDATE);

                            if (iDC4 == PDGUtility.EARLIER) {
                                //change MODEL 1. ANNDATE   earlier than Override (MDLCGOSMDL.ANNDATE)  OK
                                System.out.println(getABRVersion() + " MODEL 1. ANNDATE earlier than Override (MDLCGOSMDL.ANNDATE)  OK " + eiModel1.getKey());
                            } else if (iDC4 == PDGUtility.LATER) {
                                //CHANGE MODEL 1. ANNDATE   later than Override (MDLCGOSMDL.ANNDATE)
                                //Error - Alert MODEL 1. PDHDOMAIN
                                m_bError = true;
                                m_bAlert1 = true;
                                getPDHDomain(eiModel1, MODEL1);
                                println("<br>A valid Override (MDLCGOSMDL.ANNDATE) date exists.");
                                println("<br><b><font color=red>Error. MODEL1.ANNDATE is changed to be later than MDLCGOSMDL.ANNDATE.</font></b>");
                                println("<br>" + printTable(eiModel1, eiModel2, eiMDL, (EntityItem)eiMDL.getUpLink(0), strPreviousDate, 2));
                            }
                        }
                    }
                }

                // check whether MODEL2 gets changed
                m_el = EntityList.getEntityList(m_db, m_prof, m_xai2, aei);

                egMODEL = m_el.getEntityGroup(EG_MODEL);
                egMDLCGOSMDL = m_el.getEntityGroup(EG_MDLCGOSMDL);
                egParent = m_el.getParentEntityGroup();

                m_ei = egParent.getEntityItem(m_ei.getKey());

                T.est(egMODEL != null, "EntityGroup MODEL is null for EXTCOMPATABR01");
                if (egMODEL != null && egMODEL.getEntityItemCount() > 0) {
                    // MODEL2.ANNDATE gets changed
                    System.out.println(getABRVersion() + " MODEL2 gets changed");
                    EntityItem eiModel2 = egParent.getEntityItem(m_ei.getKey());
                    String strMODEL2ANNDATE = m_utility.getAttrValue(eiModel2, ATT_ANNDATE);
                    String strPreviousDate = getPreviousValue(eiModel2, ATT_ANNDATE);

                    for (int j=0; j < egMODEL.getEntityItemCount(); j++) {
                        EntityItem eiModel1 = egMODEL.getEntityItem(j);

                        String strMODEL1ANNDATE = m_utility.getAttrValue(eiModel1, ATT_ANNDATE);
                        EntityItem eiMDL = getMDLCGOSMDL(m_el, eiModel1, eiModel2);

                        if(eiMDL == null) {
                            continue;
                        }

                        String strMDLCGOSMDLANNDATE = m_utility.getAttrValue(eiMDL, ATT_ANNDATE);

                        int iDC1 = m_utility.dateCompare(strMODEL2ANNDATE, strPreviousDate);
                        int iDC2 = m_utility.dateCompare(strMODEL2ANNDATE, strMODEL1ANNDATE);
                        int iDC3 = m_utility.dateCompare(strMODEL1ANNDATE, strPreviousDate);

                        if (iDC3 == PDGUtility.EARLIER) {
                            //Case 2 - MODEL 1.ANNDATE is earlier than previous MODEL 2.ANNDATE
                            if (iDC2 == PDGUtility.LATER) {
                                //Change MODEL 2. ANNDATE   later than MODEL 1. ANNDATE Alert Both
                                m_bAlert1 = true;
                                m_bAlert2 = true;
                                getPDHDomain(eiModel1, MODEL1);
                                getPDHDomain(eiModel2, MODEL2);
                                println("<br>MODEL1. ANNDATE is earlier than previous MODEL2.ANNDATE");
                                println("<br>MODEL 2. ANNDATE is changed to be later than MODEL 1. ANNDATE");
                                println("<br>" + printTable(eiModel1, eiModel2, eiMDL, (EntityItem)eiMDL.getUpLink(0), strPreviousDate, 3));
                            } else {
                                //Change MODEL 2. ANNDATE   not later than MODEL 1. ANNDATE OK
                                System.out.println(getABRVersion() + " MODEL1. ANNDATE is earlier than previous MODEL2.ANNDATE");
                                System.out.println(getABRVersion() + " Change MODEL 2. ANNDATE  not later than MODEL 1. ANNDATE OK " + eiModel1.getKey());
                            }
                        } else if (iDC3 == PDGUtility.LATER) {
                            //Case 3 - previous MODEL 2.ANNDATE is earlier than MODEL 1.ANNDATE
                            if (iDC2 == PDGUtility.LATER) {
                                //Change MODEL 2. ANNDATE   later than MODEL 1. ANNDATE Alert Both
                                m_bAlert1 = true;
                                m_bAlert2 = true;
                                getPDHDomain(eiModel1, MODEL1);
                                getPDHDomain(eiModel2, MODEL2);
                                println("<br>Previous MODEL 2.ANNDATE is earlier than MODEL 1.ANNDATE");
                                println("<br>MODEL 2. ANNDATE is changed to be later than MODEL 1. ANNDATE");
                                println("<br>" + printTable(eiModel1, eiModel2, eiMDL, (EntityItem)eiMDL.getUpLink(0), strPreviousDate, 3));
                            } else {
                                //Change MODEL 2. ANNDATE   not later than MODEL 1. ANNDATE OK
                                System.out.println(getABRVersion() + "Previous MODEL 2.ANNDATE is earlier than MODEL 1.ANNDATE");
                                System.out.println(getABRVersion() + "Change MODEL 2. ANNDATE   not later than MODEL 1. ANNDATE OK " + eiModel1.getKey());
                            }
                        }


                        if (strMDLCGOSMDLANNDATE != null && strMDLCGOSMDLANNDATE.length() > 0) {
                            //Case 4 - a valid Override (MDLCGOSMDL.ANNDATE) date exists
                            int iDC5 = m_utility.dateCompare(strMODEL2ANNDATE, strMDLCGOSMDLANNDATE);
                            if (iDC1 == PDGUtility.EARLIER) {
                                m_bAlert1 = true;
                                getPDHDomain(eiModel1, MODEL1);
                                println("<br>A valid Override (MDLCGOSMDL.ANNDATE) date exists. ");
                                println("<br><b>MODEL 2. ANNDATE is changed to be earlier than its previous value.</b> ");
                                println("<br>" + printTable(eiModel1, eiModel2, eiMDL, (EntityItem)eiMDL.getUpLink(0), strPreviousDate, 3));
                            }

                            if (iDC1 == PDGUtility.LATER && iDC5 == PDGUtility.EARLIER) {
                                m_bAlert1 = true;
                                getPDHDomain(eiModel1, MODEL1);
                                println("<br>A valid Override (MDLCGOSMDL.ANNDATE) date exists. ");
                                println("<br><b>MDLCGOSMDL.ANNDATE is changed to be later than the original MODEL 2. ANNDATE, but earlier than Override (MDLCGOSMDL.ANNDATE).</b> ");
                                println("<br>" + printTable(eiModel1, eiModel2, eiMDL, (EntityItem)eiMDL.getUpLink(0), strPreviousDate, 3));
                            } else if (iDC5 == PDGUtility.LATER) {
                                m_bError = true;
                                m_bAlert1 = true;
                                m_bAlert2 = true;
                                getPDHDomain(eiModel1, MODEL1);
                                getPDHDomain(eiModel2, MODEL2);
                                println("<br>A valid Override (MDLCGOSMDL.ANNDATE) date exists. ");
                                println("<br><b><font color=red>Error.MODEL 2. ANNDATE is changed to be later than Override (MDLCGOSMDL.ANNDATE).</font></b>");
                                println("<br>" + printTable(eiModel1, eiModel2, eiMDL, (EntityItem)eiMDL.getUpLink(0), strPreviousDate, 3));
                            }
                        }
                    }
                }
            }

            if (m_bError) {
                setReturnCode(FAIL);
            }
        } catch (LockPDHEntityException le) {
            setReturnCode(UPDATE_ERROR);
            println(
                "<h3><font color=red>" +
                ERR_IAB1007E +
                "<br />" +
                le.getMessage() +
                "</font></h3>");
            logError(le.getMessage());
        } catch (UpdatePDHEntityException le) {
            setReturnCode(UPDATE_ERROR);
            println(
                "<h3><font color=red>UpdatePDH error: " +
                le.getMessage() +
                "</font></h3>");
            logError(le.getMessage());
        } catch (SBRException _sbrex) {
            String strError = _sbrex.toString();
            int i = strError.indexOf("(ok)");
            if (i < 0) {
                setReturnCode(UPDATE_ERROR);
                println("<h3><font color=red>Generate Data error: " + replace(strError, "\n", "<br>") + "</font></h3>");
                logError(_sbrex.toString());
            } else {
                strError = strError.substring(0,i);
                println(replace(strError, "\n", "<br>"));
            }
        } catch (Exception exc) {
          // Report this error to both the datbase log and the PrintWriter
            println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
            println("" + exc);
            exc.printStackTrace();

            StringWriter writer = null;
            String x = null;
            writer = new StringWriter();
            exc.printStackTrace(new PrintWriter(writer));
            x = writer.toString();
            println(x);

            // don't overwrite an update exception
            if (getABRReturnCode() != UPDATE_ERROR) {
                setReturnCode(INTERNAL_ERROR);
            }
        } finally {
            log(buildLogMessage(
                MSG_IAB2016I,
                new String[]{
                    getABRDescription(),
                    (getReturnCode() == PASS ? "Passed" : "Failed")}));

            // set DG title
            String strDgName = getABRDescription() +  ":" + m_abri.getEntityType() + ":" + m_abri.getEntityID();
            if (strDgName.length() > 64) {
                strDgName = strDgName.substring(0,64);
            }
            setDGTitle(strDgName);
            setDGRptName(ABR);

            // setCat2
            String[] astrCat2 = new String[m_cat2List.size()];
            m_cat2List.copyTo(astrCat2);
            setDGCat2(astrCat2);

            // set DG submit string
            setDGString(getABRReturnCode());
            printDGSubmitString();      //Stuff into report for subscription and notification

            // Tack on the DGString

            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
        }
    }

    private EntityItem[] getRootModel(Database _db, Profile _prof, EntityItem _ei) {
        //String strTraceBase = " COMPATABR1 getRootModel method ";
        EntityItem[] aeiReturn = null;
        try {
            EntityChangeHistoryGroup echg = new EntityChangeHistoryGroup(_db, _prof, _ei);
            EntityChangeHistoryItem echi = (EntityChangeHistoryItem)EannToEpimsInt.getCurrentChangeItem(echg);
            String strStartTime = echi.getChangeDate().substring(0, 11) + "00.00.00.000000";
            String strEndTime = echi.getChangeDate().substring(0, 11) + "23.59.59.999999";

            if (_ei.getEntityType().equals(EG_MDLCGOSMDL)) {
                aeiReturn = EannToEpimsInt.getChangedRootEntities(_db, _prof, ROOT_ENTITY, m_abri.getVEName(), _ei.getEntityType(), _ei.getEntityID(), strStartTime, strEndTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aeiReturn;
    }

    private void getPDHDomain(EntityItem _ei, int _iType) {
        if (_ei == null) {
            return;
        }
        EANAttribute att = _ei.getAttribute(ATT_PDHDOMAIN);
        if (att != null) {
            MetaFlag[] mfa = (MetaFlag[]) att.get();

            for (int f = 0; f < mfa.length; f++) {
                MetaFlag mf = mfa[f];
                if (mf.isSelected()) {
                    String flagCode = mf.getFlagCode();
                    m_cat2List.put(flagCode, flagCode);
                }
            }
        }
    }

    /*private EntityItem[] getModel2(EntityList _el, EntityItem _eiRoot, Hashtable _hshMap) {
        if (_eiRoot != null && _eiRoot.getEntityType().equals(EG_MODEL)) {
/ *
0:0:E:MODEL:84638:MODEL:84638:Root
1:0:R:MDLCGMDL:41:MODEL:84638:U
2:0:E:MODELCG:33:MDLCGMDL:41:U
3:1:R:MDLCGMDLCGOS:16:MODELCG:33:D
4:1:E:MODELCGOS:16:MDLCGMDLCGOS:16:D
5:2:R:MDLCGOSMDL:29:MODELCGOS:16:D
6:2:E:MODEL:84646:MDLCGOSMDL:29:D
5:2:R:MDLCGOSMDL:32:MODELCGOS:16:D
6:2:E:MODEL:84639:MDLCGOSMDL:32:D
5:2:R:MDLCGOSMDL:38:MODELCGOS:16:D
6:2:E:MODEL:84644:MDLCGOSMDL:38:D
* /
            StringBuffer sbModel = EntityList.pull(_eiRoot, 0, _eiRoot, new Hashtable(), 0, "Root", _hshMap);
            System.out.println(getABRVersion() + sbModel.toString());
            StringTokenizer st1 = new StringTokenizer(sbModel.toString(), NEW_LINE);
            EntityGroup egMODEL = _el.getEntityGroup(EG_MODEL);
            System.out.println(getABRVersion() + "getModel2 egMODEL: " + egMODEL.dump(false));

            EANList eiList = new EANList();
            while (st1.hasMoreTokens()) {
                StringTokenizer st2 = new StringTokenizer(st1.nextToken(), ":");
                if (st2.hasMoreTokens()) {
                    int iDepth = Integer.parseInt(st2.nextToken());
                    int iLevel = Integer.parseInt(st2.nextToken());
                    String strClass = st2.nextToken();
                    String strET = st2.nextToken();
                    int iEID =  Integer.parseInt(st2.nextToken());
                    String strPET = st2.nextToken();
                    int iPEID =  Integer.parseInt(st2.nextToken());
                    String strDir = st2.nextToken();

                    if (!strDir.equals("Root")) {
                        if (strET.equals(EG_MODEL)) {

                            EntityItem ei = egMODEL.getEntityItem(strET + iEID);
                            if (ei != null) {
                                System.out.println(getABRVersion() + "getModel2 put in list: " + ei.getKey());
                                eiList.put(ei);
                            }
                        }
                    }
                }
            }
            EntityItem[] aeiModel2 = new EntityItem[eiList.size()];
            eiList.copyTo(aeiModel2);
            return aeiModel2;
        }
        return null;
    }*/

    private String getPreviousValue(EntityItem _ei, String _strAttrCode) {
        String strValue = "";
        //EANAttribute att = _ei.getAttribute(_strAttrCode);
        if (_ei == null) {
            return strValue;
        }
        EANAttribute att = (EANAttribute) _ei.getEANObject(_ei.getEntityType() + ":" + _strAttrCode);
        if (att != null) {
            try {
                AttributeChangeHistoryGroup achg = new AttributeChangeHistoryGroup(m_db, m_prof, att);

                String[] aSort = new String[achg.getChangeHistoryItemCount()];
                for (int i=0; i < achg.getChangeHistoryItemCount(); i++) {
                    AttributeChangeHistoryItem chi = (AttributeChangeHistoryItem) achg.getChangeHistoryItem(i);

                    D.ebug(D.EBUG_SPEW, getABRVersion() + chi.dump(false));
                    String strChangeDate = chi.getChangeDate();
                    aSort[i] = strChangeDate + ":" + chi.getKey();
                }
                Arrays.sort(aSort);

                int iP = aSort.length - 2;
                if (iP > 0) {
                    String s = aSort[iP];
                    int iColon = s.indexOf(":");
                    String strKey = s.substring(iColon+1);
                    AttributeChangeHistoryItem preItem = (AttributeChangeHistoryItem)achg.getChangeHistoryItem(strKey);
                    strValue = preItem.getAttributeValue();
                }
            } catch (Exception ex) {
                System.out.println(getABRVersion() + ex.toString());
            }

        }
        return strValue;
    }

    private EntityItem getMDLCGOSMDL(EntityList _el, EntityItem _eiModel1, EntityItem _eiModel2) {
        String strTraceBase = " COMPATABR1 getMDLCGOSMDL method ";
        D.ebug(D.EBUG_SPEW,strTraceBase + " eiModel1: " + _eiModel1.getKey() + ", eiModel2: " + _eiModel2.getKey());
/*
'MDLCGMDL','U','0',
'MDLCGMDLCGOS','D','1',
'MDLCGOSMDL','D','2',

MDLCGMDL, MODELCG, MODEL
MDLCGMDLCGOS, MODELCG, MODELCGOS
MDLCGOSMDL  MODELCGOS   MODEL

*/
            Vector vMODELCG = m_utility.getParentEntityIds(_el, _eiModel1.getEntityType(), _eiModel1.getEntityID(), "MODELCG", "MDLCGMDL");

            //EntityGroup egMODELCG = _el.getEntityGroup("MODELCG");
            //EntityGroup egMODEL = _el.getEntityGroup("MODEL");
            EntityGroup egMDLCGOSMDL = _el.getEntityGroup(EG_MDLCGOSMDL);
            EntityGroup  egMODELCGOS = _el.getEntityGroup("MODELCGOS");

            D.ebug(D.EBUG_SPEW,strTraceBase + " vMODELCG size: " + vMODELCG.size());

            for (int i =0; i < vMODELCG.size(); i++) {
                int iMODELCG = ((Integer)vMODELCG.elementAt(i)).intValue();
                D.ebug(D.EBUG_SPEW, strTraceBase + " iMODELCG: " + iMODELCG);
                Vector vMODELCGOS  = m_utility.getChildrenEntityIds(_el, "MODELCG", iMODELCG, "MODELCGOS", "MDLCGMDLCGOS");

                D.ebug(D.EBUG_SPEW,strTraceBase + " vMODELCGOS size: " + vMODELCGOS.size());
                for (int j = 0; j < vMODELCGOS.size(); j++) {
                    int iMODELCGOS = ((Integer)vMODELCGOS.elementAt(j)).intValue();
                    D.ebug(D.EBUG_SPEW, strTraceBase + " iMODELCGOS: " + iMODELCGOS);

                    EntityItem eiMODELCGOS = egMODELCGOS.getEntityItem("MODELCGOS" + iMODELCGOS);
                    Vector vMODEL2  = m_utility.getChildrenEntityIds(_el, "MODELCGOS", iMODELCGOS, "MODEL", "MDLCGOSMDL");
                    D.ebug(D.EBUG_SPEW,strTraceBase + " vMODEL2 size: " + vMODEL2.size());

                    for (int k = 0; k < vMODEL2.size(); k++) {
                        int iMODEL2 = ((Integer)vMODEL2.elementAt(k)).intValue();
                        D.ebug(D.EBUG_SPEW, strTraceBase + " iMODEL2: " + iMODEL2);

                        if (_eiModel2.getEntityID() == iMODEL2) {
                            for (int l = 0; l < egMDLCGOSMDL.getEntityItemCount(); l++) {
                                EntityItem ei = egMDLCGOSMDL.getEntityItem(l);
                                EntityItem eiu = (EntityItem)ei.getUpLink(0);
                                EntityItem eid = (EntityItem)ei.getDownLink(0);
                                if (eiu.getKey().equals(eiMODELCGOS.getKey()) && eid.getKey().equals(_eiModel2.getKey())) {
                                    return ei;
                                }
                            }
                        }
                    }
                }
            }

        return null;
    }

    private String printTable(EntityItem _eiModel1, EntityItem _eiModel2, EntityItem _eiMDL, EntityItem _eiMDLCGOS, String _strPreviousDate, int _iCase) {
        StringBuffer sb = new StringBuffer();
        sb.append("<table>");
        sb.append("<tr><td>&nbsp;</td><td>Model 1: " + _eiModel1.getKey() + "</td><td>&nbsp;</td>");
        sb.append("<td>&nbsp;</td><td>Model 2: " + _eiModel2.getKey() + "</td><td>&nbsp;</td>");
        sb.append("<td>Operating System (OS)</td><td>OverRide AnnDate</td></tr>");
        sb.append("<tr><td>MT</td><td>Model</td><td>AnnDate</td><td>MT</td><td>Model</td><td>AnnDate</td></tr>");
        sb.append("<tr>");
        sb.append("<td>" + m_utility.getAttrValue(_eiModel1, ATT_MT) +"</td>");
        sb.append("<td>" + m_utility.getAttrValue(_eiModel1, ATT_MODEL) +"</td>");
        sb.append("<td>");
        if (_iCase == 2) {
            sb.append("*" + _strPreviousDate + "<br>");
        }
        sb.append(m_utility.getAttrValue(_eiModel1, ATT_ANNDATE));
        sb.append("</td>");
        sb.append("<td>" + m_utility.getAttrValue(_eiModel2, ATT_MT) +"</td>");
        sb.append("<td>" + m_utility.getAttrValue(_eiModel2, ATT_MODEL) +"</td>");
        sb.append("<td>");
        if (_iCase == 3) {
            sb.append("*" + _strPreviousDate + "<br>");
        }
        sb.append(m_utility.getAttrValue(_eiModel2, ATT_ANNDATE));
        sb.append("</td>");
        sb.append("<td>" + m_utility.getAttrValueDesc(_eiMDLCGOS, ATT_OS) +"</td>");
        sb.append("<td>");
        if (_iCase == 1) {
            sb.append("*" + _strPreviousDate + "<br>");
        }
        sb.append( m_utility.getAttrValue(_eiMDL, ATT_ANNDATE));
        sb.append("</td>");
        sb.append("</tr>");
        sb.append("</table>");
//"<table><tr><td>" + resultSt + "</td><td ALIGN=RIGHT>&nbsp;&nbsp;" + strMaintenancePopup + "&nbsp;&nbsp;" + historyPopupSt + "</td></tr></table>";

//Model 1   Model 2 Operating System (OS)   OverRide AnnDate
//MT    Model   AnnDate MT  Model   AnnDate
//2107  921 1/1/2005
//          7011    220 * 2/1/2006
//2/15/2006 AIX 5.3 3/15/2006
//
        return sb.toString();
    }

    private String replace(String _s, String _s1, String _s2) {
        String sResult = "";
        int iTab = _s.indexOf(_s1);

        while (_s.length() > 0 && iTab >=0) {
            sResult = sResult + _s.substring(0, iTab) + _s2;
            _s = _s.substring(iTab+_s1.length());
            iTab = _s.indexOf(_s1);
        }
        sResult = sResult + _s;
        return sResult;
    }

    /**
    *  Get the entity description to use in error messages
    *
    *@param  entityType  Description of the Parameter
    *@param  entityId    Description of the Parameter
    *@return             String
    */
    protected String getABREntityDesc(String entityType, int entityId) {
        return null;
    }

    /**
     *  Get ABR description
     *
     *@return    java.lang.String
     */
    public String getDescription() {
        return "COMPATABR1";
    }

    /**
     *  Get any style that should be used for this page. Derived classes can
     *  override this to set styles They must include the <style>...</style> tags
     *
     *@return    String
     */
    protected String getStyle() {
        // Print out the PSG stylesheet
        return "";
    }

    public String getRevision() {
        return new String("1.14");
    }

    public static String getVersion() {
        return ("COMPATABR1.java,v 1.14 2008/01/30 20:02:00 wendy Exp");
    }

    public String getABRVersion() {
        return "COMPATABR1.java";
    }
}
