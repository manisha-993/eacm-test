//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SPDGActionItem.java,v $
// Revision 1.29  2009/05/14 15:07:18  wendy
// Support dereference for memory release
//
// Revision 1.28  2008/06/16 19:30:30  wendy
// MN35609290 fix revealed memory leaks, deref() needed
//
// Revision 1.27  2008/02/01 22:10:07  wendy
// Cleanup RSA warnings
//
// Revision 1.26  2007/08/03 11:25:45  wendy
// RQ0713072645-1 Make actions sensitive to the PROFILE's PDHDOMAINs
//
// Revision 1.25  2005/02/15 00:48:17  joan
// fixes
//
// Revision 1.24  2005/01/18 21:46:51  dave
// more parm debug cleanup
//
// Revision 1.23  2004/11/08 19:21:39  joan
// fix PDG
//
// Revision 1.22  2004/11/03 19:04:43  joan
// fixes
//
// Revision 1.21  2004/11/03 00:19:16  joan
// fixes
//
// Revision 1.20  2004/01/21 00:09:13  joan
// debug
//
// Revision 1.19  2004/01/09 20:32:43  joan
// fix fb
//
// Revision 1.18  2003/12/18 22:10:02  joan
// work on CR
//
// Revision 1.17  2003/12/17 18:59:00  joan
// fix error
//
// Revision 1.16  2003/12/17 18:15:11  joan
// fix compile
//
// Revision 1.15  2003/12/17 17:58:27  joan
// work on SG PDG
//
// Revision 1.14  2003/12/16 23:40:50  joan
// work on CR
//
// Revision 1.13  2003/10/30 02:21:32  dave
// more null pointer traps
//
// Revision 1.12  2003/10/30 00:43:34  dave
// fixing all the profile references
//
// Revision 1.11  2003/10/24 18:04:49  joan
// fb52626
//
// Revision 1.10  2003/09/22 15:09:19  joan
// work on upgrade paths
//
// Revision 1.9  2003/09/19 19:42:02  joan
// add log
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.*;
import java.lang.reflect.Constructor;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.transactions.OPICMList;


public class SPDGActionItem  extends PDGTemplateActionItem {
  static final long serialVersionUID = 20011106L;

  private String m_strEntityType = null;  // Holds the EntityType we are updating
  private OPICMList m_sl = new OPICMList();
  private EntityItem m_eiPDG = null;
  protected boolean m_bRunByABR = false;
  protected String m_strABR = null;
  private NavActionItem m_PDGnai = null;
  private EditActionItem m_PDGeai = null;
  private CreateActionItem m_PDGcai = null;
  private DeleteActionItem m_PDGdai = null;

  public void dereference() { 
	  super.dereference();
	  m_strEntityType = null;  
	  if(m_sl != null){
		  m_sl.clear();
		  m_sl = null;
	  }

	  if (m_eiPDG!=null){
		  m_eiPDG.dereference();
		  m_eiPDG = null;
	  }
	  m_strABR = null;
	  m_PDGnai = null;
	  m_PDGeai = null;
	  m_PDGcai = null;
	  m_PDGdai = null;
  }
	
    /*
    * Version info
    */
    public String getVersion() {
      return "$Id: SPDGActionItem.java,v 1.29 2009/05/14 15:07:18 wendy Exp $";
    }


  public SPDGActionItem(EANMetaFoundation  _mf, SPDGActionItem _ai) throws MiddlewareRequestException {
    super(_mf, _ai);
    setEntityType(_ai.getEntityType());
    setPDGNavAction(_ai.getPDGNavAction());
    setPDGEditAction(_ai.getPDGEditAction());
    setPDGCreateAction(_ai.getPDGCreateAction());
    setRunByABR(_ai.m_bRunByABR);
    setABR(_ai.m_strABR);
    setPDGDeleteAction(_ai.getPDGDeleteAction());
  }

  /**
  * This represents a SPDGActionItem.  It can only be constructed via a database connection, a Profile, and an Action Item Key
  */
  public SPDGActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {

      super(_emf, _db,  _prof, _strActionItemKey);
    try {
    	setDomainCheck(true);
        ReturnStatus returnStatus = new ReturnStatus(-1);
      ResultSet rs = null;
      ReturnDataResultSet rdrs;
      Profile prof = getProfile();

      rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
      rdrs = new ReturnDataResultSet(rs);
      rs.close();
      rs = null;
      _db.commit();
      _db.freeStatement();
      _db.isPending();

      // Set the class and description...
      Vector v = new Vector();
      for (int ii = 0; ii < rdrs.size();ii++) {
        String strType =  rdrs.getColumn(ii,0);
        String strCode = rdrs.getColumn(ii,1);
        String strValue = rdrs.getColumn(ii,2);

          _db.debug(D.EBUG_SPEW, "SPDGActionItem gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");

        if (strType.equals("TYPE") && strCode.equals("EntityType")){
          setEntityType(strValue);
        } else if (strType.equals("TYPE") && strCode.equals("Navigate")){
          setPDGNavAction(new NavActionItem(null, _db, prof, strValue));
        } else if (strType.equals("TYPE") && strCode.equals("Edit")){
          setPDGEditAction(new EditActionItem(null, _db, prof, strValue));
        } else if (strType.equals("TYPE") && strCode.equals("Create")){
          setPDGCreateAction(new CreateActionItem(null, _db, prof, strValue));
        } else if (strType.equals("TYPE") && strCode.equals("Delete")){
          setPDGDeleteAction(new DeleteActionItem(null, _db, prof, strValue));
        } else if (strType.equals("STEP")){
          v.addElement(strCode + "[" + strValue);
        } else if (strType.equals("TYPE") && strCode.equals("ABR")){
          setRunByABR(true);
          setABR(strValue);
        } else if (strType.equals("TYPE") && strCode.equals("DomainCheck")) { // default is on, allow off
		    setDomainCheck(!strValue.equals("N")); //RQ0713072645
        } else {
          _db.debug(D.EBUG_ERR,"SPDGActionItem No home for this SPDG Action/Attribute" + strType + ":" + strCode + ":" + strValue);
        }
      }

      // sort  the steps
      m_sl = sortSteps(v);
    } finally {
      _db.commit();
      _db.freeStatement();
      _db.isPending();
    }

  }

  private OPICMList sortSteps(Vector v) {
    String[] as = new String[v.size()];
    v.copyInto(as);
    Arrays.sort(as);
    OPICMList ol = new OPICMList();
    for (int i=0; i < as.length; i++) {
      String s = as[i];
      int index = s.indexOf(":");
      ol.put(s.substring(0,index), s.substring(index+1));
    }
    return ol;
  }

    public String dump(boolean _bBrief) {
      StringBuffer strbResult = new StringBuffer();
      strbResult.append("SPDGActionItem:" + getKey() + ":desc:" + getLongDescription());
      strbResult.append(":purpose:" + getPurpose());
      strbResult.append(":entitytype:" + getEntityType() + "/n");
      return strbResult.toString();
    }

    public String getPurpose() {
      return "SPDGActionItem";
    }

  public String getEntityType() {
    return m_strEntityType;
  }

  protected void setEntityType (String _str) {
    m_strEntityType = _str;
  }

  public OPICMList getStepList() {
    return m_sl;
  }

  protected void setStepList (OPICMList _sl) {
    m_sl = _sl;
  }

  public NavActionItem getPDGNavAction() {
    return m_PDGnai;
  }

  protected void setPDGNavAction(NavActionItem _nai) {
    m_PDGnai = _nai;
  }

  public EditActionItem getPDGEditAction() {
    return m_PDGeai;
  }

  protected void setPDGEditAction(EditActionItem _eai) {
    m_PDGeai = _eai;
  }

  public DeleteActionItem getPDGDeleteAction() {
    return m_PDGdai;
  }

  protected void setPDGDeleteAction(DeleteActionItem _dai) {
    m_PDGdai = _dai;
  }

  public CreateActionItem getPDGCreateAction() {
    return m_PDGcai;
  }

  protected void setPDGCreateAction(CreateActionItem _cai) {
    m_PDGcai = _cai;
  }

  public boolean runByABR() {
    return m_bRunByABR;
  }

  protected void setRunByABR(boolean _b) {
    m_bRunByABR = _b;
  }

  protected void setABR(String _s) {
    m_strABR = _s;
  }

  public String getABR() {
    return m_strABR;
  }

  public void setEntityItem(EntityItem _ei) {
    resetEntityItem();
    // loop through and place the entityItem in the EANList

    EntityItem processedEI = null;
    boolean bMatch = false;
    if (!_ei.getEntityType().equals(getEntityType())) {
      EntityGroup eg = (EntityGroup)_ei.getParent();
      // It cannot be added to the list
      if (eg == null) {
        return;
      }
      if (eg.isRelator() || eg.isAssoc()) {
        //check the child entity item
        EntityItem eic = (EntityItem)_ei.getDownLink(0);
        if (!eic.getEntityType().equals(getEntityType())) {
          //check the parent entity item
          EntityItem eip = (EntityItem)_ei.getUpLink(0);
          if (! eip.getEntityType().equals(getEntityType())) {
            bMatch = false;
          } else {
            processedEI = eip;
            bMatch = true;
          }
        } else {
          processedEI = eic;
          bMatch = true;
        }
      }
    } else {
      processedEI = _ei;
      bMatch = true;
    }

    // If you found a match on entitytype.. please put it on the list
    if (bMatch) {
      m_eiPDG = processedEI;
    } else {
      System.out.println("No match for entityItem and EntityType in action:" + getEntityType() + ":" + _ei.getKey());
    }
  }

    public EntityItem getEntityItem() {
        return m_eiPDG;
    }

  public void resetEntityItem() {
    m_eiPDG = null;
  }

  public void queuedABR(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    String strTraceBase = " SPDGActionItem queuedABR method";
    _db.debug(D.EBUG_DETAIL, strTraceBase);

    if (m_eiPDG == null) {
      System.out.println("PDG entity is null");
      return;
    }

    OPICMList attL = new OPICMList();
    attL.put(m_strABR, m_strABR + "=0020");

    PDGUtility utility = new PDGUtility();
    _prof = utility.setProfValOnEffOn(_db, _prof);
    utility.updateAttribute(_db, _prof, m_eiPDG, attL);
  }

  public void exec(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SBRException {
	EntityList.checkDomain(_prof,this,m_eiPDG); //RQ0713072645
    if (runByABR()) {
      _db.queuedABR(_prof, this);
    } else {
      _db.executeAction(_prof, this);
    }
  }

  public void rexec(RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException, SBRException {
    m_eiPDG =  new EntityItem(null,_prof,m_eiPDG.getEntityType(), m_eiPDG.getEntityID());
    EANFoundation ef = getParent();
    setParent(null);
	EntityList.checkDomain(_prof,this,m_eiPDG); //RQ0713072645

    if (runByABR()) {
      _rdi.queuedABR(_prof, this);
    } else {
      _rdi.executeAction(_prof, this);
    }
    setParent(ef);
  }

  public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    String strTraceBase = " SPDGActionItem executeAction method";
    PDGUtility util = new PDGUtility();
    // need to get the entity attribute
    EntityGroup eg = new EntityGroup(null, _db, _prof, m_eiPDG.getEntityType(), "Edit");
    _prof = util.setProfValOnEffOn(_db, _prof);
    m_eiPDG = new EntityItem(eg, _prof, _db, m_eiPDG.getEntityType(), m_eiPDG.getEntityID());

	EntityList.checkDomain(_prof,this,m_eiPDG); //RQ0713072645

    boolean bDataCreated = false;
    for (int i=0; i < m_sl.size(); i++) {
      String s = (String)m_sl.getAt(i);
      StringTokenizer st = new StringTokenizer(s, "[");

      String strPDG = st.nextToken();
      String strCond = st.nextToken();

      boolean bProceed = true;
      if (!strCond.equals("NONE") && !EvaluatorII.test(m_eiPDG, strCond)) {
        bProceed = false;
      }

      if (bProceed) {
        PDGActionItem pdg = null;

        if (strPDG.equals("SWSUBSPRODPDG")) {
          pdg = new SWSubsProdPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUBSPRODINITIALPDG")) {
          pdg = new SWSubsProdInitialPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUBSPRODSUPPPDG")) {
          pdg = new SWSubsProdSuppPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUBSFEATPDG")) {
          pdg = new SWSubsFeatPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUBSFEATINITIALPDG")) {
          pdg = new SWSubsFeatInitialPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUBSFEATSUPPPDG")) {
          pdg = new SWSubsFeatSuppPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWMAINTPRODPDG")) {
          pdg = new SWMaintProdPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWMAINTPRODINITIALPDG")) {
          pdg = new SWMaintProdInitialPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWMAINTPRODSUPPPDG")) {
          pdg = new SWMaintProdSuppPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWMAINTFEATPDG")) {
          pdg = new SWMaintFeatPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWMAINTFEATINITIALPDG")) {
          pdg = new SWMaintFeatInitialPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWMAINTFEATSUPPPDG")) {
          pdg = new SWMaintFeatSuppPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUPPPRODPDG")) {
          pdg = new SWSuppProdPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUPPPRODINITIALPDG")) {
          pdg = new SWSuppProdInitialPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUPPPRODSUPPPDG")) {
          pdg = new SWSuppProdSuppPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUPPFEATPDG")) {
          pdg = new SWSuppFeatPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUPPFEATINITIALPDG")) {
          pdg = new SWSuppFeatInitialPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUPPFEATSUPPPDG")) {
          pdg = new SWSuppFeatSuppPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWPRODBASEPDG")) {
          pdg = new HWProdBasePDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWPRODINITIALPDG")) {
          pdg = new HWProdInitialPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWFEATUREPDG")) {
          pdg = new HWFeaturePDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWUPMODELPDG")) {
          pdg = new HWUpgradeModelPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWFEATCONVPDG")) {
          pdg = new HWFeatureConvertPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWUPPIPDG")) {
          pdg = new HWUpgradePIPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWUPPTOPIPDG")) {
          pdg = new HWUpgradePToPIPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWUPPIPDG")) {
          pdg = new HWUpgradePIPDG(this, _db, _prof, strPDG);
        } else {
          try {
            Class c = Class.forName("COM.ibm.eannounce.objects." + strPDG);
            Class[] ac = {Class.forName("COM.ibm.eannounce.objects.EANMetaFoundation"),
                    Class.forName("COM.ibm.opicmpdh.middleware.Database"),
                    Class.forName("COM.ibm.opicmpdh.middleware.Profile"),
                    Class.forName("java.lang.String")};
            Constructor con = c.getConstructor(ac);
            Object[] ao = {this, _db, _prof, strPDG};
            pdg = (PDGActionItem)con.newInstance(ao);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }

        _db.test(pdg != null, strTraceBase + ": PDGActionItem " + strPDG + " is not supported.");

        pdg.setRunBySPDG(true);
        pdg.setEntityItem(m_eiPDG);
        pdg.exec(_db, _prof);
        if (!bDataCreated) {
          bDataCreated = pdg.isDataCreated();
        }
        pdg.dereference();
        System.gc();
      }
    }

    util.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, "", getLongDescription());

    if (!bDataCreated) {
      SBRException sbrEx = new SBRException();
      sbrEx.add("Generating data is complete.  No data created during this run. (ok)");
      throw sbrEx;
    }
  }

  public byte[] viewMissingData(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SBRException {
    return _db.viewMissingData(_prof, this);
  }

  public byte[] rviewMissingData(RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException, SBRException {
    m_eiPDG =  new EntityItem(null,_prof,m_eiPDG.getEntityType(), m_eiPDG.getEntityID());
    EANFoundation ef = getParent();
    setParent(null);
    byte[] ba = _rdi.viewMissingData(_prof, this);
    setParent(ef);
    return ba;
  }

  public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    String strTraceBase = " SPDGActionItem viewMissingData method";
    StringBuffer sb = new StringBuffer();
    PDGUtility util = new PDGUtility();
    // need to get the entity attribute
    EntityGroup eg = new EntityGroup(null, _db, _prof, m_eiPDG.getEntityType(), "Edit", false);
    _prof = util.setProfValOnEffOn(_db, _prof);
    m_eiPDG = new EntityItem(eg, _prof, _db, m_eiPDG.getEntityType(), m_eiPDG.getEntityID());

    for (int i=0; i < m_sl.size(); i++) {
      String s = (String)m_sl.getAt(i);
      StringTokenizer st = new StringTokenizer(s, "[");

      String strPDG = st.nextToken();
      String strCond = st.nextToken();

      boolean bProceed = true;
      if (!strCond.equals("NONE") && !EvaluatorII.test(m_eiPDG, strCond)) {
        bProceed = false;
      }

      if (bProceed) {
        PDGActionItem pdg = null;
        if (strPDG.equals("SWSUBSPRODPDG")) {
          pdg = new SWSubsProdPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUBSPRODINITIALPDG")) {
          pdg = new SWSubsProdInitialPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUBSPRODSUPPPDG")) {
          pdg = new SWSubsProdSuppPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUBSFEATPDG")) {
          pdg = new SWSubsFeatPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUBSFEATINITIALPDG")) {
          pdg = new SWSubsFeatInitialPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUBSFEATSUPPPDG")) {
          pdg = new SWSubsFeatSuppPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWMAINTPRODPDG")) {
          pdg = new SWMaintProdPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWMAINTPRODINITIALPDG")) {
          pdg = new SWMaintProdInitialPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWMAINTPRODSUPPPDG")) {
          pdg = new SWMaintProdSuppPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWMAINTFEATPDG")) {
          pdg = new SWMaintFeatPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWMAINTFEATINITIALPDG")) {
          pdg = new SWMaintFeatInitialPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWMAINTFEATSUPPPDG")) {
          pdg = new SWMaintFeatSuppPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUPPPRODPDG")) {
          pdg = new SWSuppProdPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUPPPRODINITIALPDG")) {
          pdg = new SWSuppProdInitialPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUPPPRODSUPPPDG")) {
          pdg = new SWSuppProdSuppPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUPPFEATPDG")) {
          pdg = new SWSuppFeatPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUPPFEATINITIALPDG")) {
          pdg = new SWSuppFeatInitialPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("SWSUPPFEATSUPPPDG")) {
          pdg = new SWSuppFeatSuppPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWPRODBASEPDG")) {
          pdg = new HWProdBasePDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWPRODINITIALPDG")) {
          pdg = new HWProdInitialPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWFEATUREPDG")) {
          pdg = new HWFeaturePDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWUPMODELPDG")) {
          pdg = new HWUpgradeModelPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWFEATCONVPDG")) {
          pdg = new HWFeatureConvertPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWUPPIPDG")) {
          pdg = new HWUpgradePIPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWUPPTOPIPDG")) {
          pdg = new HWUpgradePToPIPDG(this, _db, _prof, strPDG);
        } else if (strPDG.equals("HWUPPIPDG")) {
          pdg = new HWUpgradePIPDG(this, _db, _prof, strPDG);
        } else {
          try {
            Class c = Class.forName("COM.ibm.eannounce.objects." + strPDG);
            Class[] ac = {Class.forName("COM.ibm.eannounce.objects.EANMetaFoundation"),
                    Class.forName("COM.ibm.opicmpdh.middleware.Database"),
                    Class.forName("COM.ibm.opicmpdh.middleware.Profile"),
                    Class.forName("java.lang.String")};
            Constructor con = c.getConstructor(ac);
            Object[] ao = {this, _db, _prof, strPDG};
            pdg = (PDGActionItem)con.newInstance(ao);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }

        _db.test(pdg != null, strTraceBase + ": PDGActionItem " + strPDG + " is not supported.");

        pdg.setEntityItem(m_eiPDG);
        pdg.setRunBySPDG(true);
        byte[] ba = pdg.viewMissingData(_db, _prof);
        pdg.dereference();
        sb.append(new String(ba));
      }
    }

    if(sb.toString().length() <= 0 ) {
      sb.append("Generating data is complete.");
    }

    util.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_PASSED, "", getLongDescription());
    return sb.toString().getBytes();
  }


/**
 * @return true if successful, false if nothing to update or unsuccessful
 */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
      return true;
  }
}
