// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: ACTPDG13MODUPGR.java,v $
// Revision 1.13  2008/09/04 20:09:27  wendy
// Cleanup RSA warnings
//
// Revision 1.12  2007/09/14 18:38:52  couto
// MN32841099 WGMODEL replaced by WGMODELA
//
// Revision 1.11  2006/02/20 21:39:45  joan
// clean up System.out.println
//
// Revision 1.10  2005/01/18 21:33:09  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.9  2004/09/16 16:55:24  joan
// fixes
//
// Revision 1.8  2004/09/16 16:52:18  joan
// fixes
//
// Revision 1.7  2004/09/15 23:37:04  joan
// fixes
//
// Revision 1.6  2004/09/15 23:34:47  joan
// fixes
//
// Revision 1.5  2004/09/15 22:05:58  bala
// fixes
//
// Revision 1.4  2004/09/07 19:57:06  bala
// fix
//
// Revision 1.3  2004/08/31 18:44:08  bala
// change template file and adjust search attributes
//
// Revision 1.2  2004/08/31 16:08:14  bala
// dynasearch parm fix
//
// Revision 1.1  2004/08/26 20:38:12  bala
// Initial Checkin
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

/*
 *  1) Get a MODEL matching the input parms
 *  2) Find the AVAILS for the ANNCODENAME input
 *  3)
 *@author     Balagopal
 *@created    August 25, 2004
 */
public class ACTPDG13MODUPGR extends PDGActionItem {

  final static long serialVersionUID = 20011106L;

  private String m_strAfSerType = null;
  private String m_strAfFrSerType = null;
  private String m_strAfMachType = null;
  private String m_strAfModel = null;
  //private String m_strAfFromMachType = null;
  //private String m_strAfFromModel = null;
  private String m_strAnnCodeName = null;

  //private EANList m_availList = new EANList();
  //private Vector m_vctReturnEntityKeys = new Vector();
  //private EANList m_opList = new EANList();
  //private EANList m_fupList = new EANList();
  private StringBuffer m_sbData = new StringBuffer();
  //private ExtractActionItem m_xai = null;
  private EntityItem m_eiWG = null;

  /*
   *  Version info
   */
  /**
   *  Gets the version attribute of the ACTPDG13MODUPGR object
   *
   *@return    The version value
   */
  public String getVersion() {
    return new String("$Id: ACTPDG13MODUPGR.java,v 1.13 2008/09/04 20:09:27 wendy Exp $");
  }


  /**
   *  Constructor for the ACTPDG13MODUPGR object
   *
   *@param  _mf                             Description of the Parameter
   *@param  _ai                             Description of the Parameter
   *@exception  MiddlewareRequestException  Description of the Exception
   */
  public ACTPDG13MODUPGR(EANMetaFoundation _mf, ACTPDG13MODUPGR _ai) throws MiddlewareRequestException {
    super(_mf, _ai);
  }


  /**
   *  Constructor for the ACTPDG13MODUPGR object
   *
   *@param  _emf                            Description of the Parameter
   *@param  _db                             Description of the Parameter
   *@param  _prof                           Description of the Parameter
   *@param  _strActionItemKey               Description of the Parameter
   *@exception  SQLException                Description of the Exception
   *@exception  MiddlewareException         Description of the Exception
   *@exception  MiddlewareRequestException  Description of the Exception
   */
  public ACTPDG13MODUPGR(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
    super(_emf, _db, _prof, _strActionItemKey);
  }


  /**
   *  Description of the Method
   *
   *@param  _bBrief  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String dump(boolean _bBrief) {
    StringBuffer strbResult = new StringBuffer();
    strbResult.append("ACTPDG13MODUPGR:" + getKey() + ":desc:" + getLongDescription());
    strbResult.append(":purpose:" + getPurpose());
    strbResult.append(":entitytype:" + getEntityType() + "/n");
    return strbResult.toString();
  }


  /**
   *  Gets the purpose attribute of the ACTPDG13MODUPGR object
   *
   *@return    The purpose value
   */
  public String getPurpose() {
    return "ACTPDG13MODUPGR";
  }

  /**
   *  Description of the Method
   *
   *@param  _db                                        Database Object
   *
   *@param  _prof                                      Profile object
   *      Parameter
   *@param  _bGenData                                  True will generate data
   *      Parameter
   *@return                                            StringBuffer value
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
   // String strTraceBase = " ACTPDG13MODUPGR checkMissingData method";

    m_eiBaseCOF = (EntityItem) m_eiList.get("TOBASE");

  _db.test(m_eiBaseCOF != null, "The Hardware-System-Base-" + m_strAfFrSerType + " TO Model is null");
    OPICMList infoList = new OPICMList();
    String strFileName = "PDGtemplates/HW13UpgradeModel.txt";
/*
    if (!m_strAfFromMachType.equals(m_strAfMachType)) {
      strFileName = "PDGtemplates/HWConvertModel.txt";
    }

 */
    m_sbActivities.append("<TEMPLATEFILE>" + strFileName + "</TEMPLATEFILE>\n");

  infoList.put("WG", m_eiWG);
    infoList.put("PDG", m_eiPDG);
    infoList.put("GEOIND", "GENAREASELECTION");
    //This infolist will map to @PDG:<tags> to the template file. the PDG stands for the PDG EntityItem

    _prof = m_utility.setProfValOnEffOn(_db, _prof);

    TestPDG pdgObject = new TestPDG(_db, _prof,
        m_eiBaseCOF,
    //Root of extractaction
    infoList,
    //Has all the template file mappings needed
    m_PDGxai,
    //Xtract action for the PDGAi
    strFileName);
    //Template file name
    StringBuffer sbMissing = pdgObject.getMissingEntities();
    if (_bGenData) {
      generateData(_db, _prof, sbMissing, "");
    }

    m_sbData.append(sbMissing.toString());
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
    //String strTraceBase = " ACTPDG13MODUPGR checkPDGAttribute method";
    for (int i = 0; i < _afirmEI.getAttributeCount(); i++) {
      EANAttribute att = _afirmEI.getAttribute(i);
      String textAtt = "";
      String sFlagAtt = "";
      //String sFlagClass = "";
      Vector mFlagAtt = new Vector();
      Vector mFlagCode = new Vector();

      //int index = -1;
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
      } else if (att.getKey().equals("MACHTYPEATR")) {
        m_strAfMachType = sFlagAtt;
      } else if (att.getKey().equals("MODELATR")) {
        m_strAfModel = textAtt;
      } else if (att.getKey().equals("FROMMACHTYPE")) {
        //m_strAfFromMachType = textAtt;
      } else if (att.getKey().equals("FROMMODEL")) {
        //m_strAfFromModel = textAtt;
      } else if (att.getKey().equals("ANNCODENAME")) {
        m_strAnnCodeName = sFlagAtt;
      } else if (att.getKey().equals("AFHWFRSERTYPE")) {
        m_strAfFrSerType = sFlagAtt;
      }
    }

    if (m_strAnnCodeName == null || m_strAnnCodeName.length() <= 0) {
      m_SBREx.add("ANNCODENAME is required.");
    }

    if (m_strAfFrSerType == null || m_strAfFrSerType.length() <= 0) {
      m_SBREx.add("From Series is required.");
    }
  }


  /**
   *  Description of the Method
   */
  protected void resetVariables() {
    m_strAfSerType = null;
    m_strAfFrSerType = null;
    //m_availList = new EANList();
    //m_vctReturnEntityKeys = new Vector();
    //m_opList = new EANList();
    m_eiList = new EANList();
    //m_fupList = new EANList();
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
    String strTraceBase = " ACTPDG13MODUPGR viewMissing method";

    _db.debug(D.EBUG_DETAIL, strTraceBase);
    m_SBREx = new SBRException();
    resetVariables();
    if (m_eiPDG == null) {
      String s = "viewMissing:PDG entity is null";
      return s.getBytes();
    }
    _prof = m_utility.setProfValOnEffOn(_db, _prof);
    ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXPDG30MODUPGRABR");
    //Get all the PDG entities from this model
    EntityItem[] eiParm = {m_eiPDG};
    EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
    EntityGroup eg = el.getParentEntityGroup();
    m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());

    eg = el.getEntityGroup("WG");
    if (eg != null) {
      if (eg.getEntityItemCount() > 0) {
        m_eiWG = eg.getEntityItem(0);
      }
    }
    _db.test(m_eiWG != null, "WG entity is null");


    checkPDGAttribute(_db, _prof, m_eiPDG);
    //Collects the attr from the PDG checks whether values exist in the FORM

    // validate data
    checkDataAvailability(_db, _prof, m_eiPDG);
    if (m_SBREx.getErrorCount() > 0) {
      m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
      throw m_SBREx;
    }

    m_sbActivities = new StringBuffer();
    m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
    m_sbData = new StringBuffer();
    //This is not needed as its done in PDGActionItem
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
    String strTraceBase = " ACTPDG13MODUPGR executeAction method";
    _db.debug(D.EBUG_DETAIL, strTraceBase);
    String strData = "";
    try {
      _db.debug(D.EBUG_DETAIL, strTraceBase);
      m_SBREx = new SBRException();
      resetVariables();

      if (m_eiPDG == null) {
        D.ebug(D.EBUG_SPEW,"executeAction:PDG entity is null");
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
          m_eiWG = eg.getEntityItem(0);
        }
      }
      _db.test(m_eiWG != null, "WG entity is null");
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
      strData = checkMissingData(_db, _prof, true).toString();
      //Create the Entities needed under the MODEL as per the criteria
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
    // make sure the Base MODEL  already exists
    String strSai = (String) m_saiList.get("MODEL");
    StringBuffer sb = new StringBuffer();
    sb.append("map_COFCAT=100;");
    sb.append("map_COFSUBCAT=126;");
    sb.append("map_COFGRP=150;");
    sb.append("map_COFSUBGRP=" + m_strAfSerType + ";");
    sb.append("map_MACHTYPEATR=" + m_strAfMachType + ";");
    sb.append("map_MODELATR=" + m_strAfModel);

    EntityItem[] aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());
    //strSai comes from the Search ActionItem in the meta

    if (aeiCOM.length <= 0) {
      m_SBREx.add("The Hardware-System-Base-" + m_strAfSerType + " Model must exist for " + m_strAfMachType + "-" + m_strAfModel);
    } else if (aeiCOM.length > 1) {
      m_SBREx.add("There are " + aeiCOM.length + " existing Hardware-System-Base-" + m_strAfSerType + " Model for " + m_strAfMachType + "-" + m_strAfModel);
    } else {
      m_eiList.put("TOBASE", aeiCOM[0]);
    }

    //Thats it....MODELCONVERT need not exist for the criteria, if it does not, PDG will create it

  }
}

