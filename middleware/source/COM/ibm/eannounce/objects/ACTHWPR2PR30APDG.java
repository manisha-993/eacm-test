// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: ACTHWPR2PR30APDG.java,v $
// Revision 1.21  2008/09/04 20:01:59  wendy
// Cleanup RSA warnings
//
// Revision 1.20  2007/09/14 18:36:09  couto
// MN32841099 WGMODEL replaced by WGMODELA
//
// Revision 1.19  2006/02/20 21:39:44  joan
// clean up System.out.println
//
// Revision 1.18  2005/02/28 20:29:50  joan
// add throw exception
//
// Revision 1.17  2004/11/28 20:28:06  bala
// change the search map for PRODSTRUCT to the new entity:attribute:Parent/Child concept
//
// Revision 1.16  2004/11/17 00:06:11  joan
// changes to getRowIndex key in rowselectable table
//
// Revision 1.15  2004/11/15 17:07:13  joan
// fix compile
//
// Revision 1.14  2004/11/12 23:50:32  bala
// dont check for MODELCONVERT if the from and to models are the same
//
// Revision 1.13  2004/10/06 19:36:39  bala
// fixes
//
// Revision 1.12  2004/10/05 22:45:20  bala
// Add the MODELCONVERT check
//
// Revision 1.11  2004/09/23 03:26:23  bala
// add some more polish
//
// Revision 1.10  2004/09/22 19:01:12  bala
// debug
//
// Revision 1.9  2004/09/22 17:04:11  bala
// change VE names for ABR
//
// Revision 1.8  2004/09/22 04:19:18  bala
// fix till it runs
//
// Revision 1.7  2004/09/21 23:25:48  bala
// debug
//
// Revision 1.6  2004/09/21 23:05:14  bala
// debug
//
// Revision 1.5  2004/09/21 22:25:40  bala
// debug
//
// Revision 1.4  2004/09/21 21:58:47  bala
// more fixes in search
//
// Revision 1.3  2004/09/21 17:40:49  bala
// fix attribute names
//
// Revision 1.2  2004/09/07 21:24:07  bala
// fix typo
//
// Revision 1.1  2004/09/07 19:57:52  bala
// checkin
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
 *  Description of the Class
 *
 *@author     Balagopal
 *@created    September 20, 2004
 */
public class ACTHWPR2PR30APDG extends PDGActionItem {

  final static long serialVersionUID = 20011106L;

  private String m_strAfSerType = null;
  //private String m_strAfFrSerType = null;
  private String m_strToMachType = null;
  private String m_strToModel = null;
  private Vector m_afToFUPVec = new Vector();
  private String m_strAfFromMachType = null;
  private String m_strAfFromModel = null;
  private String m_strAnnCodeName = null;
  private Vector m_afFromFUPVec = new Vector();

  //private EANList m_availList = new EANList();
  //private Vector m_vctReturnEntityKeys = new Vector();
  //private EANList m_opList = new EANList();
  //private EANList m_fupList = new EANList();
  private EntityItem m_eiToProc = null;
  private EntityItem m_eiToICard = null;
  private EntityItem m_eiFromProc = null;
  private EntityItem m_eiFromICard = null;
  private StringBuffer m_sbData = new StringBuffer();
  private Vector m_upgradeVec = new Vector();
  /**
   *  Description of the Field
   */
  public final static int UPGRADELIMIT = 100;
  private OPICMList m_processedComboList = new OPICMList();
  //private OPICMList m_checkedSearchList = new OPICMList();
  //private ExtractActionItem m_xai = null;


  /*
   *  Version info
   */
  /**
   *  Gets the version attribute of the ACTHWPR2PR30APDG object
   *
   *@return    The version value
   */
  public String getVersion() {
    return new String("$Id: ACTHWPR2PR30APDG.java,v 1.21 2008/09/04 20:01:59 wendy Exp $");
  }


  /**
   *  Constructor for the ACTHWPR2PR30APDG object
   *
   *@param  _mf                             Description of the Parameter
   *@param  _ai                             Description of the Parameter
   *@exception  MiddlewareRequestException  Description of the Exception
   */
  public ACTHWPR2PR30APDG(EANMetaFoundation _mf, ACTHWPR2PR30APDG _ai) throws MiddlewareRequestException {
    super(_mf, _ai);
    m_bCollectInfo = true;
    m_iCollectStep = 1;
  }


  /**
   *  Constructor for the ACTHWPR2PR30APDG object
   *
   *@param  _emf                            Description of the Parameter
   *@param  _db                             Description of the Parameter
   *@param  _prof                           Description of the Parameter
   *@param  _strActionItemKey               Description of the Parameter
   *@exception  SQLException                Description of the Exception
   *@exception  MiddlewareException         Description of the Exception
   *@exception  MiddlewareRequestException  Description of the Exception
   */
  public ACTHWPR2PR30APDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
    super(_emf, _db, _prof, _strActionItemKey);
    m_bCollectInfo = true;
    m_iCollectStep = 1;
  }


  /**
   *  Description of the Method
   *
   *@param  _bBrief  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String dump(boolean _bBrief) {
    StringBuffer strbResult = new StringBuffer();
    strbResult.append("ACTHWPR2PR30APDG:" + getKey() + ":desc:" + getLongDescription());
    strbResult.append(":purpose:" + getPurpose());
    strbResult.append(":entitytype:" + getEntityType() + "/n");
    return strbResult.toString();
  }


  /**
   *  Gets the purpose attribute of the ACTHWPR2PR30APDG object
   *
   *@return    The purpose value
   */
  public String getPurpose() {
    return "ACTHWPR2PR30APDG";
  }


  /**
   *  Gets the stepDescription attribute of the ACTHWPR2PR30APDG object
   *
   *@param  iStep  Description of the Parameter
   *@return        The stepDescription value
   */
  public String getStepDescription(int iStep) {
    if (iStep == 1) {
      return "Select Upgrade";
    }
    return "N/A";
  }


  /**
   *  Sets the pDGCollectInfo attribute of the ACTHWPR2PR30APDG object
   *
   *@param  _cl     The new pDGCollectInfo value
   *@param  _iStep  The new pDGCollectInfo value
   *@param  _eiRst  The new pDGCollectInfo value
   */
  public void setPDGCollectInfo(PDGCollectInfoList _cl, int _iStep, RowSelectableTable _eiRst) throws SBRException, MiddlewareException {
    if (_iStep == 1) {
      if (m_eiPDG != null) {
        m_InfoList = _cl;
        EANList upgradeList = m_InfoList.getInfoList();
        StringBuffer sb = new StringBuffer();
        boolean bFirst = true;
        for (int i = 0; i < upgradeList.size(); i++) {
          PDGCollectInfoItem ci = (PDGCollectInfoItem) upgradeList.getAt(i);
          String strFrom = ci.getFirstItem();
          String strTo = ci.getSecondItem();
          sb.append((!bFirst ? "\n" : "") + strFrom + ":" + strTo);
          bFirst = false;
        }

        int r = _eiRst.getRowIndex(m_eiPDG.getEntityType() + ":AFINFO:C");
        if (r >= 0 && r < _eiRst.getRowCount()) {
          try {
            _eiRst.put(r, 1, sb.toString());
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  _iStep  Description of the Parameter
   *@return         Description of the Return Value
   */
  public PDGCollectInfoList collectInfo(int _iStep) {
    if (_iStep == 1) {
      if (m_eiPDG != null) {
        try {
          EANTextAttribute att1 = (EANTextAttribute) m_eiPDG.getAttribute("AFTOFUPS");
          Vector vecTo = new Vector();
          if (att1 != null) {
            String s = (String) att1.get();
            vecTo = m_utility.sepLongText(s);
          }

          Vector vecFrom = new Vector();

          EANTextAttribute att2 = (EANTextAttribute) m_eiPDG.getAttribute("AFFROMFUPS");
          if (att2 != null) {
            String s = (String) att2.get();
            vecFrom = m_utility.sepLongText(s);
          }

          m_InfoList = new PDGCollectInfoList(this, getProfile(), "From\\To");
          for (int i = 0; i < vecFrom.size(); i++) {
            String strFrom = (String) vecFrom.elementAt(i);
            for (int j = 0; j < vecTo.size(); j++) {
              String strTo = (String) vecTo.elementAt(j);
              PDGCollectInfoItem pi = new PDGCollectInfoItem(m_InfoList, getProfile(), false, strFrom, strTo, "From" + strFrom + " To " + strTo);
              if (strFrom.indexOf("|") >= 0 && strTo.indexOf("|") < 0) {
                pi.setEditable(false);
              }
              m_InfoList.putCollectInfoItem(pi);
            }
          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
    return m_InfoList;
  }


  /**
   *  Description of the Method
   *
   *@param  _db                                        Description of the
   *      Parameter
   *@param  _prof                                      Description of the
   *      Parameter
   *@param  _bGenData                                  Description of the
   *      Parameter
   *@return                                            Description of the Return
   *      Value
   *@exception  SQLException                           Description of the
   *      Exception
   *@exception  MiddlewareException                    Description of the
   *      Exception
   *@exception  MiddlewareShutdownInProgressException  Description of the
   *      Exception
   *@exception  SBRException                           Description of the
   *      Exception
   */
  public StringBuffer checkMissingDataAgain(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    return checkMissingData(_db, _prof, _bGenData);
  }


  /**
   *  Description of the Method
   *
   *@param  _db                                        Description of the
   *      Parameter
   *@param  _prof                                      Description of the
   *      Parameter
   *@exception  SQLException                           Description of the
   *      Exception
   *@exception  MiddlewareException                    Description of the
   *      Exception
   *@exception  MiddlewareShutdownInProgressException  Description of the
   *      Exception
   *@exception  SBRException                           Description of the
   *      Exception
   */
  public void savePDGStatus(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    if (m_sbActivities != null && m_utility != null) {
      m_sbActivities.append(m_utility.getActivities().toString());
      m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
    }
  }


  /**
   *  Description of the Method
   *
   *@param  _db                                        Description of the
   *      Parameter
   *@param  _prof                                      Description of the
   *      Parameter
   *@param  _bGenData                                  Description of the
   *      Parameter
   *@return                                            Description of the Return
   *      Value
   *@exception  SQLException                           Description of the
   *      Exception
   *@exception  MiddlewareException                    Description of the
   *      Exception
   *@exception  MiddlewareShutdownInProgressException  Description of the
   *      Exception
   *@exception  SBRException                           Description of the
   *      Exception
   */
  protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    String strTraceBase = " ACTHWPR2PR30APDG checkMissingData method";
    //boolean bProc2Proc = false;
    boolean bProc2ProcIcard = false;
    boolean bProcIcard2ProcIcard = false;

    m_eiBaseCOF = (EntityItem) m_eiList.get("TOBASE");
    _db.debug(D.EBUG_DETAIL, strTraceBase + ":m_eiList:" + m_eiList.toString());

    for (int i = 0; i < m_upgradeVec.size(); i++) {
      //bProc2Proc = false;
      bProc2ProcIcard = false;
      bProcIcard2ProcIcard = false;
      String str = (String) m_upgradeVec.elementAt(i);
      int index = str.indexOf(":");
      String strFrom = str.substring(0, index);
      String strTo = str.substring(index + 1);
      String strFileName = "";
      OPICMList infoList = new OPICMList();

      //Get the number of Processor Icards in the vector
      StringTokenizer st = new StringTokenizer(str, "|");
      int iIcards = st.countTokens();

      if (iIcards == 1) {//No I cards..its a processor to processor transaction
        //bProc2Proc = true;
        strFileName = "PDGtemplates/HWUpgrade30aProc1toProc2.txt";
        if (m_strAfSerType.equals("iSeries")) {
          infoList.put("PROCUPGTYPE", "100");
        }
      } else if (iIcards == 2) {// Its a Processor to Processor I card
        bProc2ProcIcard = true;
        strFileName = "PDGtemplates/HWUpgrade30aProc1toProc2_Icard1.txt";
        if (m_strAfSerType.equals("iSeries")) {
          infoList.put("PROCUPGTYPE", "110");
        }
      } else {//Its a Processor Icard to Processor Icard
        bProcIcard2ProcIcard = true;
      }

      String strFromProc = null;
      String strToProc = null;
      String strFromICard = null;
      String strToICard = null;

      String strToken = null;
      st = new StringTokenizer(str, ":");
      StringTokenizer st1 = null;
      int iCount = 0;

      while (st.hasMoreTokens()) {
        strToken = st.nextToken();
        D.ebug(D.EBUG_SPEW,"strToken:" + strToken);
        if (iCount == 0) {//We are at the From part of the String
          if (bProcIcard2ProcIcard) {//If true check for Icard separator "|"
            st1 = new StringTokenizer(strToken, "|");
            strFromProc = st1.nextToken();
            strFromICard = st1.nextToken();
            m_eiFromProc = (EntityItem) m_eiList.get(strFromProc + "FROMPROC");
            _db.debug(D.EBUG_DETAIL, "checkMissingData:strFromProc:" + strFromProc);
            _db.debug(D.EBUG_DETAIL, "checkMissingData:strFromICard:" + strFromICard);
            m_eiFromICard = (EntityItem) m_eiList.get(strFromICard + "FROMICARD");
          } else {
            strFromProc = strToken;
            m_eiFromProc = (EntityItem) m_eiList.get(strFromProc + "FROMPROC");
            _db.debug(D.EBUG_DETAIL, "checkMissingData:strFromProc:" + strFromProc);
          }
        } else {//this is the To Part
          if (bProcIcard2ProcIcard || bProc2ProcIcard) {//If true check for Icard separator "|"
            st1 = new StringTokenizer(strToken, "|");
            strToProc = st1.nextToken();
            strToICard = st1.nextToken();
            m_eiToProc = (EntityItem) m_eiList.get(strToProc + "TOPROC");
            _db.debug(D.EBUG_DETAIL, "checkMissingData:strToProc:" + strToProc);
            m_eiToICard = (EntityItem) m_eiList.get(strToICard + "TOICARD");
            _db.debug(D.EBUG_DETAIL, "checkMissingData:strToICard:" + strToICard);
          } else {
            strToProc = strToken;
            m_eiToProc = (EntityItem) m_eiList.get(strToProc + "TOPROC");
            _db.debug(D.EBUG_DETAIL, "checkMissingData:strToProc:" + strToProc);
          }
        }
        iCount++;
      }
      if (bProcIcard2ProcIcard) {
          if (!strFromProc.equals(strToProc) && !strFromICard.equals(strToICard)) {
            _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:4");
            //series - iSeries, and rrrr not equal pppp, and dddd not equal iiii
            strFileName = "PDGtemplates/HWUpgrade30aProc1_Icard1toProc2_Icard2.txt";
            if (m_strAfSerType.equals("iSeries")) {
              infoList.put("PROCUPGTYPE", "140");
            }
          } else if (strFromProc.equals(strToProc) && !strFromICard.equals(strToICard)) {
            _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:5");
            //series - iSeries, and rrrr = pppp, and dddd not equal iiii
            strFileName = "PDGtemplates/HWUpgrade30aProc1_Icard1toProc1_Icard2.txt";
            if (m_strAfSerType.equals("iSeries")) {
              infoList.put("PROCUPGTYPE", "120");
            }
          } else if (!strFromProc.equals(strToProc) && strFromICard.equals(strToICard)) {
            _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:6");
            //series - iSeries, and rrrr not equal pppp, and dddd = iiii
            strFileName = "PDGtemplates/HWUpgrade30aProc1_Icard1toProc2_Icard1.txt";
            if (m_strAfSerType.equals("iSeries")) {
              infoList.put("PROCUPGTYPE", "130");
            }
          }
      }

//------------------------------------------------------------------
      if (m_processedComboList.get(strFrom + strTo) == null) {

        if (strFileName.length() > 0) {
//					m_sbActivities.append("<TEMPLATEFILE>" + strFileName + "</TEMPLATEFILE>\n");

          _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:7.1");
          infoList.put("WG", m_eiRoot);
          _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:7.2");
          infoList.put("PDG", m_eiPDG);
          _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:7.3");
          infoList.put("TOPROC", m_eiToProc);
          _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:7.4");
          infoList.put("FROMPROC", m_eiFromProc);
          _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:7.5");
          infoList.put("GEOIND", "GENAREASELECTION");
          _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:8");

          if (m_eiToICard != null) {
            _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:9");
            infoList.put("TOICARD", m_eiToICard);
            EANFlagAttribute att = (EANFlagAttribute) m_eiToICard.getAttribute("HWFCSUBCAT");
            if (att != null) {
              _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:10");
              if (att.isSelected("282")) {//InteractiveCard
                _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:11");
                infoList.put("TOICARDSUBCAT", "600");
              } else {
                _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:12");
                att = (EANFlagAttribute) m_eiToICard.getAttribute("HWFCCAT");
                if (att.isSelected("131")) {//Package
                  infoList.put("TOICARDSUBCAT", "603");
                } else if (att.isSelected("213")) {//Memory
                  infoList.put("TOICARDSUBCAT", "602");
                } else if (att.isSelected("219")) {//Processor
                  infoList.put("TOICARDSUBCAT", "604");
                } else {
                  infoList.put("TOICARDSUBCAT", "405");
                }
              }
            }
          }
          _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:13");
          if (m_eiFromICard != null) {
            infoList.put("FROMICARD", m_eiFromICard);
          }
          _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:14");
          _prof = m_utility.setProfValOnEffOn(_db, _prof);
          _db.debug(D.EBUG_DETAIL, "checkMissingData:aFTER WHILE:15:m_eiBaseCOF" + m_eiBaseCOF.getKey() + ":infoList:" + infoList.toString() + ":m_PDGxai:" + m_PDGxai + ":strFileName:" + strFileName);

          TestPDG pdgObject = new TestPDG(_db, _prof, m_eiBaseCOF, infoList, m_PDGxai, strFileName);
          StringBuffer sbMissing = pdgObject.getMissingEntities();
          pdgObject = null;
          infoList = null;

          if (_bGenData) {
            generateData(_db, _prof, sbMissing, strFrom);
            //should save the combo to prevent from checking it again.
            m_processedComboList.put(strFrom + strTo, strFrom + strTo);
          }
          m_sbData.append(sbMissing.toString());
        }
      }
    }
    return m_sbData;
  }


  /**
   *  Description of the Method
   *
   *@param  _db                                        Description of the
   *      Parameter
   *@param  _prof                                      Description of the
   *      Parameter
   *@param  _afirmEI                                   Description of the
   *      Parameter
   *@exception  SQLException                           Description of the
   *      Exception
   *@exception  MiddlewareRequestException             Description of the
   *      Exception
   *@exception  MiddlewareException                    Description of the
   *      Exception
   *@exception  SBRException                           Description of the
   *      Exception
   *@exception  MiddlewareShutdownInProgressException  Description of the
   *      Exception
   */
  protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
    String strTraceBase = " ACTHWPR2PR30APDG checkPDGAttribute method";
    _db.debug(D.EBUG_DETAIL, strTraceBase);
    _db.debug(D.EBUG_DETAIL, strTraceBase + ":Entity parm is " + _afirmEI.getKey() + ": which has :" + _afirmEI.getAttributeCount());
    for (int i = 0; i < _afirmEI.getAttributeCount(); i++) {
      EANAttribute att = _afirmEI.getAttribute(i);
      _db.debug(D.EBUG_DETAIL, strTraceBase + ":" + att.getAttributeCode());
      String textAtt = "";
      String sFlagAtt = "";
      //String sFlagClass = "";
      Vector mFlagAtt = new Vector();
      Vector mFlagCode = new Vector();

    //  int index = -1;
      if (att instanceof EANTextAttribute) {
        textAtt = ((String) att.get()).trim();
      } else if (att instanceof EANFlagAttribute) {
        if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
          MetaFlag[] amf = (MetaFlag[]) att.get();
          for (int f = 0; f < amf.length; f++) {
            if (amf[f].isSelected()) {
              sFlagAtt = amf[f].getLongDescription().trim();
              //sFlagClass = amf[f].getFlagCode().trim();
             // index = f;
              break;
            }
          }
        } else if (att instanceof MultiFlagAttribute) {
          MetaFlag[] amf = (MetaFlag[]) att.get();
          for (int f = 0; f < amf.length; f++) {
            if (amf[f].isSelected()) {
              mFlagAtt.addElement(amf[f].getLongDescription().trim());
              mFlagCode.addElement(amf[f].getFlagCode().trim());
            }
          }
        }
      }

      if (att.getKey().equals("AFHWSERTYPE")) {
        m_strAfSerType = sFlagAtt;
        _db.debug(D.EBUG_DETAIL, strTraceBase + ":m_strAfSerType:" + m_strAfSerType);
      } else if (att.getKey().equals("MACHTYPEATR")) {
        m_strToMachType = sFlagAtt;
      } else if (att.getKey().equals("MODELATR")) {
        m_strToModel = textAtt;
      } else if (att.getKey().equals("AFTOFUPS")) {
        m_afToFUPVec = m_utility.sepLongText(textAtt);
      } else if (att.getKey().equals("FROMMACHTYPE")) {
        m_strAfFromMachType = textAtt;
      } else if (att.getKey().equals("FROMMODEL")) {
        m_strAfFromModel = textAtt;
      } else if (att.getKey().equals("AFFROMFUPS")) {
        m_afFromFUPVec = m_utility.sepLongText(textAtt);
      } else if (att.getKey().equals("ANNCODENAME")) {
        m_strAnnCodeName = sFlagAtt;
      } else if (att.getKey().equals("AFHWFRSERTYPE")) {
        //m_strAfFrSerType = sFlagAtt;
      } else if (att.getKey().equals("AFINFO")) {
        m_upgradeVec = m_utility.sepLongText(textAtt);
      }
    }

    if (m_afToFUPVec.size() <= 0) {
      m_SBREx.add("To FEATURE(s) is required for HW Upgrade");
    }

    if (m_afFromFUPVec.size() <= 0) {
      m_SBREx.add("From FEATURE(s) is required for HW Upgrade");
    }

    if (m_strAfSerType == null || m_strAfSerType.length() <= 0) {
      m_SBREx.add("ToSeries  is required.");
    }

    if (m_strToMachType == null || m_strToMachType.length() <= 0) {
      m_SBREx.add("TOMACHINETYPE  is required.");
    }
    if (m_strAnnCodeName == null || m_strAnnCodeName.length() <= 0) {
      m_SBREx.add("ANNCODENAME is required.");
    }

    if (m_upgradeVec == null || m_upgradeVec.size() <= 0) {
      m_SBREx.add("Please select upgrade path.");
    } else if (m_upgradeVec.size() > UPGRADELIMIT) {
      m_SBREx.add("Number of selected upgrade paths exceeds the limit of " + UPGRADELIMIT + ". (ok)");
    }
  }


  /**
   *  Description of the Method
   */
  protected void resetVariables() {
    m_strAfSerType = null;
    //m_strAfFrSerType = null;
    m_strToMachType = null;
    m_strToModel = null;
    m_strAfFromMachType = null;
    m_strAfFromModel = null;
    m_afFromFUPVec = new Vector();

    //m_vctReturnEntityKeys = new Vector();
    //m_opList = new EANList();
    m_eiList = new EANList();
    //m_fupList = new EANList();
    m_eiToProc = null;
    m_eiToICard = null;
    m_eiFromProc = null;
    m_eiFromICard = null;
    m_processedComboList = new OPICMList();
    //m_checkedSearchList = new OPICMList();
  }


  /**
   *  Description of the Method
   *
   *@param  _db                                        Description of the
   *      Parameter
   *@param  _prof                                      Description of the
   *      Parameter
   *@exception  SQLException                           Description of the
   *      Exception
   *@exception  MiddlewareException                    Description of the
   *      Exception
   *@exception  MiddlewareShutdownInProgressException  Description of the
   *      Exception
   *@exception  SBRException                           Description of the
   *      Exception
   */
  public void queuedABR(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    String strTraceBase = "ACTHWPR2PR30APDG queuedABR method";
    _db.debug(D.EBUG_DETAIL, strTraceBase);
    m_SBREx = new SBRException();

    if (m_eiPDG == null) {
      D.ebug(D.EBUG_SPEW,"PDG entity is null");
      return;
    }

    OPICMList attL = new OPICMList();
    attL.put("HWPR2PR30APDGABR01", "HWPR2PR30APDGABR01=0020");
    _prof = m_utility.setProfValOnEffOn(_db, _prof);
    m_utility.updateAttribute(_db, _prof, m_eiPDG, attL);
  }


  /**
   *  Description of the Method
   *
   *@param  _db                                        Description of the
   *      Parameter
   *@param  _prof                                      Description of the
   *      Parameter
   *@return                                            Description of the Return
   *      Value
   *@exception  SQLException                           Description of the
   *      Exception
   *@exception  MiddlewareException                    Description of the
   *      Exception
   *@exception  MiddlewareShutdownInProgressException  Description of the
   *      Exception
   *@exception  SBRException                           Description of the
   *      Exception
   */
  public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    String strTraceBase = " ACTHWPR2PR30APDG viewMissing method";

    _db.debug(D.EBUG_DETAIL, strTraceBase);
    _db.debug(D.EBUG_DETAIL, strTraceBase);
    m_SBREx = new SBRException();
    resetVariables();
    if (m_eiPDG == null) {
      String s = "PDG entity is null";
      return s.getBytes();
    }
    _prof = m_utility.setProfValOnEffOn(_db, _prof);
    ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXPDG30MODUPGRABR");
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
    _db.test(m_eiRoot != null, "WG entity is null");

    checkPDGAttribute(_db, _prof, m_eiPDG);
    if (m_SBREx.getErrorCount() > 0) {
      m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
      throw m_SBREx;
    }

    // validate data
    checkDataAvailability(_db, _prof, m_eiPDG);
    if (m_SBREx.getErrorCount() > 0) {
      m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
      throw m_SBREx;
    }

    m_sbActivities = new StringBuffer();
    m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
    m_sbData = new StringBuffer();
    String s = checkMissingData(_db, _prof, false).toString();
    if (s.length() <= 0) {
      s = "Generating data is complete";
    }
    m_sbActivities.append(m_utility.getViewXMLString(s));
    m_utility.savePDGViewXML(_db, _prof, m_eiPDG, m_sbActivities.toString());
    m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_PASSED, "", getLongDescription());

    return s.getBytes();
  }


  /**
   *  Description of the Method
   *
   *@param  _db                                        Description of the
   *      Parameter
   *@param  _prof                                      Description of the
   *      Parameter
   *@exception  SQLException                           Description of the
   *      Exception
   *@exception  MiddlewareException                    Description of the
   *      Exception
   *@exception  MiddlewareShutdownInProgressException  Description of the
   *      Exception
   *@exception  SBRException                           Description of the
   *      Exception
   */
  public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    String strTraceBase = " ACTHWPR2PR30APDG executeAction method";
    String strData = "";
    try {
      _db.debug(D.EBUG_DETAIL, strTraceBase);
      m_SBREx = new SBRException();
      resetVariables();

      if (m_eiPDG == null) {
        D.ebug(D.EBUG_SPEW,"PDG entity is null");
        return;
      }
      _prof = m_utility.setProfValOnEffOn(_db, _prof);
      m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

      ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXPDG30MODUPGRABR");
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
      _db.test(m_eiRoot != null, "Workgroup entity is null");

      checkPDGAttribute(_db, _prof, m_eiPDG);
      checkDataAvailability(_db, _prof, m_eiPDG);
      if (m_SBREx.getErrorCount() > 0) {
        m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
        throw m_SBREx;
      }
      m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_RUNNING, "", getLongDescription());
      m_utility.resetActivities();
      m_sbActivities = new StringBuffer();
      m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
      m_sbData = new StringBuffer();
      //m_xai = new ExtractActionItem(null, _db, _prof, "EXTHWFCGEN30APDG");
      strData = checkMissingData(_db, _prof, true).toString();
      m_sbActivities.append(m_utility.getActivities().toString());
      m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
    } catch (SBRException ex) {
      m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
      throw ex;
    }
    if (strData.length() <= 0) {
      m_SBREx.add("Generating data is complete.  No data created during this run. (ok)");
      throw m_SBREx;
    }
  }


  /**
   *@param  _db                      Description of the Parameter
   *@param  _bExpire                 Description of the Parameter
   *@return                          true if successful, false if nothing to
   *      update or unsuccessful
   *@exception  MiddlewareException  Description of the Exception
   */
  protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  _db                                        Description of the
   *      Parameter
   *@param  _prof                                      Description of the
   *      Parameter
   *@param  _afirmEI                                   Description of the
   *      Parameter
   *@exception  SQLException                           Description of the
   *      Exception
   *@exception  MiddlewareException                    Description of the
   *      Exception
   *@exception  MiddlewareShutdownInProgressException  Description of the
   *      Exception
   *@exception  SBRException                           Description of the
   *      Exception
   */
  protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    //boolean bProc2Proc = false;
    boolean bProc2ProcIcard = false;
    boolean bProcIcard2ProcIcard = false;

    //Search for ModelConvert using the From and to MT and Model as the HW Model Conversion is supposed to be created before this

    String strSai = (String) m_saiList.get("MODELCONVERT");
    StringBuffer sb = new StringBuffer();
    sb.append("map_TOMACHTYPE="+m_strToMachType+";");
    sb.append("map_TOMODEL="+ m_strToModel+";");
    _db.debug(D.EBUG_DETAIL, "checkDataAvailability:Checking MODELCONVERT");

    sb.append("map_FROMMODEL=" + m_strAfFromModel + ";");
    sb.append("map_FROMMACHTYPE=" + m_strAfFromMachType );

    if (!(m_strAfFromModel.trim()).equals(m_strToModel.trim())) {                             //Search for model convert only if from and to models are not the same
      EntityItem[] aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODELCONVERT", sb.toString());
      if (aeiCOM.length <= 0) {
        m_SBREx.add(" ModelConvert must exist for " +m_strAfFromMachType+"-"+m_strAfFromModel+":To:"+m_strToMachType + "-" + m_strToModel);
      } else if (aeiCOM.length > 1) {
        m_SBREx.add("There are " + aeiCOM.length + "  existing ModelConvert instances  for " +m_strAfFromMachType+"-"+m_strAfFromModel+":To:"+m_strToMachType + "-" + m_strToModel);
      }
    }

    // Search for the From and To models
    //Then search for PRODSTRUCT which use FromModel->FromFeature and toModel->toFeature

    strSai = (String) m_saiList.get("MODEL");
    sb = new StringBuffer();
    sb.append("map_COFCAT=100;");//Hardware
    sb.append("map_COFSUBCAT=126;");//System
    sb.append("map_COFGRP=150;");//Base
    _db.debug(D.EBUG_DETAIL, "checkDataAvailability:m_strAfSerType:" + m_strAfSerType);

    sb.append("map_COFSUBGRP=" + m_strAfSerType + ";");
    sb.append("map_MACHTYPEATR=" + m_strToMachType + ";");
    sb.append("map_MODELATR=" + m_strToModel);

    EntityItem[] aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());
    if (aeiCOM.length <= 0) {
      m_SBREx.add("The Hardware-System-Base-" + m_strAfSerType + " Model must exist for " + m_strToMachType + "-" + m_strToModel);
    } else if (aeiCOM.length > 1) {
      m_SBREx.add("There are " + aeiCOM.length + "  existing Hardware-System-Base-" + m_strAfSerType + " Model for " + m_strToMachType + "-" + m_strToModel);
    } else {
      m_eiList.put("TOBASE", aeiCOM[0]);
    }

    // make sure the PRODSTRUCT exists
    // verify upgrade paths
    //String strToFeatureCode = null;
    //String strFromFeatureCode = null;
    for (int i = 0; i < m_upgradeVec.size(); i++) {
      //bProc2Proc = false;
      bProc2ProcIcard = false;
      bProcIcard2ProcIcard = false;

      String strFromProc = null;
      String strToProc = null;
      String strFromICard = null;
      String strToICard = null;

      String strUpgrade = (String) m_upgradeVec.elementAt(i);
      StringTokenizer st = new StringTokenizer(strUpgrade, "|");
      int iIcards = st.countTokens();

      if (iIcards == 1) {//No I cards..its a processor to processor transaction
        //bProc2Proc = true;
      } else if (iIcards == 2) {// Its a Processor to Processor I card
        bProc2ProcIcard = true;
      } else {//Its a Processor Icard to Processor Icard
        bProcIcard2ProcIcard = true;
      }

      _db.debug(D.EBUG_DETAIL, "checkDataAvailability:strUpgrade:" + strUpgrade);
      String strToken = null;
      st = new StringTokenizer(strUpgrade, ":");
      StringTokenizer st1 = null;
      int iCount = 0;

      while (st.hasMoreTokens()) {
        strToken = st.nextToken();
        D.ebug(D.EBUG_SPEW,"strToken:" + strToken);
        if (iCount == 0) {//We are at the From part of the String
          if (bProcIcard2ProcIcard) {//If true check for Icard separator "|"
            st1 = new StringTokenizer(strToken, "|");
            strFromProc = st1.nextToken();
            strFromICard = st1.nextToken();
            _db.debug(D.EBUG_DETAIL, "checkDataAvailability:strFromProc:" + strFromProc);
            _db.debug(D.EBUG_DETAIL, "checkDataAvailability:strFromICard:" + strFromICard);
          } else {
            strFromProc = strToken;
            _db.debug(D.EBUG_DETAIL, "checkDataAvailability:strFromProc:" + strFromProc);
          }
        } else {//this is the To Part
          if (bProcIcard2ProcIcard || bProc2ProcIcard) {//If true check for Icard separator "|"
            st1 = new StringTokenizer(strToken, "|");
            strToProc = st1.nextToken();
            strToICard = st1.nextToken();
            _db.debug(D.EBUG_DETAIL, "checkDataAvailability:strToProc:" + strToProc);
            _db.debug(D.EBUG_DETAIL, "checkDataAvailability:strToICard:" + strToICard);
          } else {
            strToProc = strToken;
            _db.debug(D.EBUG_DETAIL, "checkDataAvailability:strToProc:" + strToProc);
          }
        }
        iCount++;
      }

      String strSai1 = (String) m_saiList.get("PRODSTRUCT");

      // check if the upgrade PRODSTRUCT exists
      if (strFromProc != null) {
        StringBuffer sb1 = new StringBuffer();

        sb1.append("map_FEATURE:FEATURECODE:P=" + strFromProc + ";");
        sb1.append("map_MODEL:MACHTYPEATR:C=" + m_strAfFromMachType + ";");
        sb1.append("map_MODEL:MODELATR:C=" + m_strAfFromModel);


        EntityItem[] aeiUpgrade = m_utility.dynaSearchII(_db, _prof, m_eiPDG, strSai1, "PRODSTRUCT", sb1.toString());
        if (aeiUpgrade == null || aeiUpgrade.length <= 0) {
          m_SBREx.add(" UpgradeFrom PRODSTRUCT for " + strFromProc + " not found.");
        } else {
          _db.debug(D.EBUG_DETAIL, "checkDataAvailability:PRODSTRUCT found for strUpgrade:" + strFromProc + ":" + aeiUpgrade[0].getKey());
          m_eiList.put(strUpgrade, aeiUpgrade[0]);
          //Store the FEATURE ENTITY for later use
          m_eiFromProc = (EntityItem) aeiUpgrade[0].getUpLink(0);
          _db.debug(D.EBUG_DETAIL, "checkDataAvailability:link to FROMPROCESSOR found for :" + m_eiFromProc.getKey());
          m_eiList.put(strFromProc + "FROMPROC", m_eiFromProc);
        }

      }

      if (strFromICard !=null ) {
        StringBuffer sb1 = new StringBuffer();

        sb1.append("map_FEATURE:FEATURECODE:P=" + strFromICard + ";");
        sb1.append("map_MODEL:MACHTYPEATR:C=" + m_strAfFromMachType + ";");
        sb1.append("map_MODEL:MODELATR:C=" + m_strAfFromModel);


        EntityItem[] aeiUpgrade = m_utility.dynaSearchII(_db, _prof, m_eiPDG, strSai1, "PRODSTRUCT", sb1.toString());
        if (aeiUpgrade == null || aeiUpgrade.length <= 0) {
          m_SBREx.add(" UpgradeFrom PRODSTRUCT for " + strFromICard + " not found.");
        } else {
          _db.debug(D.EBUG_DETAIL, "checkDataAvailability:PRODSTRUCT found for strFromICard:" + strFromICard + ":" + aeiUpgrade[0].getKey());
          m_eiList.put(strUpgrade, aeiUpgrade[0]);
          //Store the FEATURE ENTITY for later use
          m_eiFromICard = (EntityItem) aeiUpgrade[0].getUpLink(0);
          _db.debug(D.EBUG_DETAIL, "checkDataAvailability:link to FROMICARD found for :" + m_eiFromICard.getKey());
          m_eiList.put(strFromICard + "FROMICARD", m_eiFromICard);
        }

      }


      if (strToProc!=null) {
        StringBuffer sb1 = new StringBuffer();

        sb1.append("map_FEATURE:FEATURECODE:P=" + strToProc + ";");
        sb1.append("map_MODEL:MACHTYPEATR:C=" + m_strToMachType + ";");
        sb1.append("map_MODEL:MODELATR:C=" + m_strToModel);

        EntityItem[] aeiUpgrade = m_utility.dynaSearchII(_db, _prof, m_eiPDG, strSai1, "PRODSTRUCT", sb1.toString());
        if (aeiUpgrade == null || aeiUpgrade.length <= 0) {
          m_SBREx.add(" UpgradeTo PRODSTRUCT for " + strToProc + " not found.");
        } else {
          _db.debug(D.EBUG_DETAIL, "checkDataAvailability:PRODSTRUCT found for strToProc:" + strToProc + ":" + aeiUpgrade[0].getKey());
          m_eiList.put(strUpgrade, aeiUpgrade[0]);
          //Store the FEATURE ENTITY for later use
          m_eiToProc = (EntityItem) aeiUpgrade[0].getUpLink(0);
          _db.debug(D.EBUG_DETAIL, "checkDataAvailability:TOPROCESSOR found for :" + m_eiToProc.getKey());
          m_eiList.put(strToProc + "TOPROC", m_eiToProc);
        }

      }
      if (strToICard!=null) {
        StringBuffer sb1 = new StringBuffer();

        sb1.append("map_FEATURE:FEATURECODE:P=" + strToICard + ";");
        sb1.append("map_MODEL:MACHTYPEATR:C=" + m_strToMachType + ";");
        sb1.append("map_MODEL:MODELATR:C=" + m_strToModel);

        EntityItem[] aeiUpgrade = m_utility.dynaSearchII(_db, _prof, m_eiPDG, strSai1, "PRODSTRUCT", sb1.toString());
        if (aeiUpgrade == null || aeiUpgrade.length <= 0) {
          m_SBREx.add(" UpgradeTo PRODSTRUCT for " + strToICard + " not found.");
        } else {
          _db.debug(D.EBUG_DETAIL, "checkDataAvailability:PRODSTRUCT found for strToICard:" + strToICard + ":" + aeiUpgrade[0].getKey());
          m_eiList.put(strUpgrade, aeiUpgrade[0]);
          //Store the FEATURE ENTITY for later use
          m_eiToICard = (EntityItem) aeiUpgrade[0].getUpLink(0);
          _db.debug(D.EBUG_DETAIL, "checkDataAvailability:TOICARD found for :" + m_eiToICard.getKey());
          m_eiList.put(strToICard + "TOICARD", m_eiToICard);
        }

      }
    }
  }
}

