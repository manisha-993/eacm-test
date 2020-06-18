// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: SWCOPYBILLING30APDG.java,v $
// Revision 1.7  2008/09/05 21:25:48  wendy
// Cleanup RSA warnings
//
// Revision 1.6  2007/08/25 14:50:01  wendy
// MN32841099 WGMODEL replaced by WGMODELA
//
// Revision 1.5  2006/02/20 21:39:48  joan
// clean up System.out.println
//
// Revision 1.4  2005/01/18 21:46:51  dave
// more parm debug cleanup
//
// Revision 1.3  2004/10/12 22:24:47  joan
// fixes
//
// Revision 1.2  2004/09/13 19:46:24  joan
// fixes
//
// Revision 1.1  2004/09/10 22:50:21  joan
// initial load
//


package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.transactions.OPICMList;

public class SWCOPYBILLING30APDG extends PDGActionItem {
  static final long serialVersionUID = 20011106L;

  //private Vector m_vctReturnEntityKeys = new Vector();
  //private EANList m_opList = new EANList();
  private EntityItem m_eiBCOF = null;

    /*
    * Version info
    */
    public String getVersion() {
      return "$Id: SWCOPYBILLING30APDG.java,v 1.7 2008/09/05 21:25:48 wendy Exp $";
    }


  public SWCOPYBILLING30APDG(EANMetaFoundation  _mf, SWCOPYBILLING30APDG _ai) throws MiddlewareRequestException {
    super(_mf, _ai);
  }

  /**
  * This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
  */
  public SWCOPYBILLING30APDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
      super(_emf, _db,  _prof, _strActionItemKey);
  }

    public String dump(boolean _bBrief) {
      StringBuffer strbResult = new StringBuffer();
      strbResult.append("SWCOPYBILLING30APDG:" + getKey() + ":desc:" + getLongDescription());
      strbResult.append(":purpose:" + getPurpose());
      strbResult.append(":entitytype:" + getEntityType() + "/n");
      return strbResult.toString();
    }

    public String getPurpose() {
      return "SWCOPYBILLING30APDG";
    }

  protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    String strTraceBase = " SWCOPYBILLING30APDG checkMissingData method";
    _db.debug(D.EBUG_SPEW,strTraceBase);
    StringBuffer sbReturn = new StringBuffer();
    int iOPWGID = _prof.getOPWGID();
    DatePackage dpNow = _db.getDates();
    String strNow = dpNow.getNow();
    ReturnDataResultSet rdrs = null;
    ResultSet rs = null;
    ReturnStatus returnStatus = new ReturnStatus(-1);
    String strEnterprise = _prof.getEnterprise();

    //StringBuffer sb = new StringBuffer();

    String strFileName = "PDGtemplates/SWCOPYBILLING01_30a.txt";

    EntityGroup egPROD = m_ABReList.getEntityGroup("SWPRODSTRUCT");

    if (egPROD.getEntityItemCount() <=0 ) {
      _db.debug(D.EBUG_SPEW,strTraceBase + " no SWPRODSTRUCT");
      _db.debug(D.EBUG_SPEW,"m_ABReList: " + m_ABReList.dump(false));
    }

    EntityGroup eg = new EntityGroup(null, _db, _prof, "SWFEATURE", "Edit", false);
    for (int i=0; i < egPROD.getEntityItemCount(); i++) {

      EntityItem eiPROD = egPROD.getEntityItem(i);
      String strEntityType =  eiPROD.getEntityType();
      int iEntityID =  eiPROD.getEntityID();
      rs = _db.callGBL2933(returnStatus,  strEnterprise, strEntityType, iEntityID, iOPWGID, strNow, strNow);
      rdrs = new ReturnDataResultSet(rs);
      rs.close();
      rs = null;
      _db.commit();
      _db.freeStatement();
      _db.isPending();

      for (int ii = 0; ii < rdrs.size();ii++) {
        String strEntity1Type =  rdrs.getColumn(ii,2);
        int iEntity1ID = rdrs.getColumnInt(ii,3);

        _db.debug(D.EBUG_SPEW, "PDGActionItem gbl7030 answer is:" + strEntity1Type + ":" + iEntity1ID);

        if (strEntity1Type.equals("SWFEATURE")) {
          EntityItem eiU = new EntityItem(eg, _prof, _db, strEntity1Type, iEntity1ID);
          OPICMList infoList = new OPICMList();
          infoList.put("MODEL", m_eiPDG);

          infoList.put("PROD", eiPROD);
          infoList.put("FEATURE", eiU);

          m_eiList.put(eiPROD);
          m_eiList.put(eiU);

          _prof = m_utility.setProfValOnEffOn(_db, _prof);
          TestPDGII pdgObject = new TestPDGII(_db, _prof, m_eiPDG, infoList, m_PDGxai, strFileName);
          StringBuffer sbMissing = pdgObject.getMissingEntities();

          sbReturn.append(sbMissing.toString());
          if (_bGenData && sbMissing.toString().trim().length() > 0) {
            generateDataII(_db, _prof, sbMissing,"");
          }

          EntityItem[] aeic = {eiPROD};

          m_utility.removeLink(_db, _prof,m_ABReList, m_eiPDG, aeic, "AFMODELSWPROD");
        }
      }
    }
    return sbReturn;
  }

  protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
  }

  protected void resetVariables() {
    //m_vctReturnEntityKeys = new Vector();
    //m_opList = new EANList();
  }

  public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    //String strTraceBase = " SWCOPYBILLING30APDG viewMissing method";
    return null;
  }

  private String adjustActivities(String _s) {
    StringTokenizer st = new StringTokenizer(_s, "\n");
    String sReturn = "";
    while (st.hasMoreTokens()) {
      String s = st.nextToken();
      int i = s.indexOf("<ENTITYDISPLAY>");
      if (i >= 0) {
        s = s.substring(i + 15);
      }

      int j = s.indexOf("</ENTITYDISPLAY>");
      if (j >= 0) {
        s = s.substring(0, j);
      }
      sReturn += "<br/>" + s;
    }

    return sReturn;
  }

  public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    String strTraceBase = " SWCOPYBILLING30APDG executeAction method ";
    m_SBREx = new SBRException();
    String strData = "";
    try {
      _db.debug(D.EBUG_DETAIL, strTraceBase);
      if (m_eiPDG == null) {
        _db.debug(D.EBUG_SPEW,"MODEL entity is null");
        return;
      }

      // validate data
      checkDataAvailability(_db, _prof, m_eiPDG);
      if (m_SBREx.getErrorCount() > 0) {
        throw m_SBREx;
      }

      strData = checkMissingData(_db, _prof, true).toString();
      m_sbActivities.append(adjustActivities(m_utility.getActivities().toString()));
    } catch (SBRException ex) {
      throw ex;
    }
    if (strData.length() <= 0) {
      m_SBREx.add("Generating data is complete.  No data created during this run. (ok)");
      throw m_SBREx;
    }
  }

  protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    _prof = m_utility.setProfValOnEffOn(_db, _prof);

    EntityGroup eg = m_ABReList.getParentEntityGroup();
    m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());

    // get Base MODEL
    eg = m_ABReList.getEntityGroup("MODEL");
    if (eg != null) {
      EANList tempList = new EANList();
      StringBuffer sb = new StringBuffer();

      sb.append("map_COFCAT=101;");
      sb.append("map_COFGRP=150");
      for (int i=0; i < eg.getEntityItemCount(); i++) {
        EntityItem ei = eg.getEntityItem(i);
        tempList.put(ei);
      }
      m_eiBCOF = m_utility.findEntityItem(tempList, "MODEL", sb.toString());
      if (m_eiBCOF != null) {
        m_eiList.put(m_eiBCOF);
      }
    }

    m_eiList.put(m_eiPDG);
  }
}
