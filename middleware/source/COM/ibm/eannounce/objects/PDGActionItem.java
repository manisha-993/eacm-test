//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: PDGActionItem.java,v $
// Revision 1.100  2009/05/14 15:06:13  wendy
// Support dereference for memory release
//
// Revision 1.99  2008/06/16 19:30:29  wendy
// MN35609290 fix revealed memory leaks, deref() needed
//
// Revision 1.98  2008/02/11 20:45:15  wendy
// Cleanup RSA warnings
//
// Revision 1.97  2008/01/29 18:47:27  wendy
// convert special chars to prevent error in xml transform
//
// Revision 1.96  2007/09/11 14:05:29  wendy
// more MN32841099, check for null parent before use
//
// Revision 1.95  2007/08/22 19:41:06  wendy
// MN32841099 WGMODEL was replaced by WGMODELA
//
// Revision 1.94  2007/08/03 11:25:45  wendy
// RQ0713072645-1 Make actions sensitive to the PROFILE's PDHDOMAINs
//
// Revision 1.93  2007/02/22 21:10:56  joan
// fixes
//
// Revision 1.92  2006/05/15 16:10:37  joan
// changes
//
// Revision 1.91  2006/05/08 21:38:27  joan
// null pointer
//
// Revision 1.90  2006/04/20 20:02:15  joan
// changes
//
// Revision 1.89  2006/02/28 00:47:20  joan
// work on pdg
//
// Revision 1.88  2006/02/20 22:16:40  joan
// changing debug statements
//
// Revision 1.87  2006/02/20 21:39:47  joan
// clean up System.out.println
//
// Revision 1.86  2005/10/11 18:20:02  joan
// add new method for PDG
//
// Revision 1.85  2005/07/13 22:04:11  joan
// fixes
//
// Revision 1.84  2005/05/24 20:57:59  joan
// fixes
//
// Revision 1.83  2005/04/26 15:49:56  joan
// fixes
//
// Revision 1.82  2005/03/11 23:10:15  joan
// back to 1.78 version
//
// Revision 1.81  2005/03/11 22:21:40  dave
// removing those nasty auto method generations
//
// Revision 1.80  2005/03/11 21:54:59  dave
// more jtest cleanup
//
// Revision 1.79  2005/03/10 00:17:47  dave
// more Jtest work
//
// Revision 1.78  2005/03/07 19:47:31  joan
// fixes
//
// Revision 1.77  2005/03/03 21:12:25  dave
// Jtest cleanup
//
// Revision 1.76  2005/02/28 20:29:51  joan
// add throw exception
//
// Revision 1.75  2005/02/24 00:04:40  joan
// fixes
//
// Revision 1.74  2005/02/23 21:47:18  joan
// fixes
//
// Revision 1.73  2005/02/23 20:03:17  joan
// work on get parent from navigation
//
// Revision 1.72  2005/02/11 00:13:39  joan
// add new PDG
//
// Revision 1.71  2005/02/10 00:54:53  joan
// add throw exception
//
// Revision 1.70  2005/01/20 21:04:10  joan
// working on msg
//
// Revision 1.69  2005/01/18 23:36:15  joan
// fixes
//
// Revision 1.68  2005/01/18 21:46:51  dave
// more parm debug cleanup
//
// Revision 1.67  2005/01/17 16:50:06  joan
// fixes
//
// Revision 1.66  2005/01/13 03:10:45  joan
// fixes
//
// Revision 1.65  2005/01/13 02:20:11  joan
// work on copy relator
//
// Revision 1.64  2005/01/10 18:55:29  joan
// fixes
//
// Revision 1.63  2005/01/10 18:32:20  joan
// fixes
//
// Revision 1.62  2004/11/22 18:37:39  joan
// fixes
//
// Revision 1.61  2004/11/20 00:08:49  joan
// fixes
//
// Revision 1.60  2004/11/08 19:21:39  joan
// fix PDG
//
// Revision 1.59  2004/10/29 16:24:06  joan
// fixes
//
// Revision 1.58  2004/10/07 20:42:38  joan
// fixes
//
// Revision 1.57  2004/10/06 19:59:32  joan
// fixes
//
// Revision 1.56  2004/10/05 16:05:02  joan
// fixes
//
// Revision 1.55  2004/09/16 22:13:25  joan
// update for direction
//
// Revision 1.54  2004/09/02 21:29:16  joan
// debug
//
// Revision 1.53  2004/08/31 16:49:22  joan
// add new pdgs
//
// Revision 1.52  2004/08/05 20:36:16  joan
// work on special bid PDG
//
// Revision 1.51  2004/08/04 17:43:04  joan
// fix bug
//
// Revision 1.49  2004/05/27 21:26:21  joan
// throws exception
//
// Revision 1.48  2004/05/27 21:01:49  joan
// fix compile
//
// Revision 1.47  2004/05/27 20:05:08  joan
// add collectInfo method
//
// Revision 1.46  2004/05/26 22:47:03  joan
// handle SF business rule exception for ALWR
//
// Revision 1.45  2004/04/01 18:21:45  dave
// new SP's
//
// Revision 1.44  2004/03/12 23:19:11  joan
// changes from 1.2
//
// Revision 1.43  2004/01/09 20:32:43  joan
// fix fb
//
// Revision 1.42  2003/12/17 18:59:00  joan
// fix error
//
// Revision 1.41  2003/12/16 23:40:49  joan
// work on CR
//
// Revision 1.40  2003/12/12 21:26:50  joan
// adjust restart
//
// Revision 1.39  2003/12/11 20:13:42  joan
// work on link method
//
// Revision 1.38  2003/12/10 21:01:03  joan
// adjust for ExcludeCopy
//
// Revision 1.37  2003/12/09 22:52:21  joan
// fb fixes
//
// Revision 1.36  2003/11/26 21:19:07  joan
// adjust code
//
// Revision 1.35  2003/11/25 20:34:05  joan
// adjust copy
//
// Revision 1.34  2003/11/18 19:01:50  joan
// fix constructor for extract
//
// Revision 1.33  2003/11/14 23:56:58  joan
// fix null pointer
//
// Revision 1.32  2003/11/14 00:19:49  joan
// fix null pointer profile
//
// Revision 1.31  2003/11/12 20:05:32  joan
// work on CR
//
// Revision 1.30  2003/10/30 17:06:55  joan
// adjust getProfile
//
// Revision 1.29  2003/10/30 02:21:32  dave
// more null pointer traps
//
// Revision 1.28  2003/10/30 00:43:34  dave
// fixing all the profile references
//
// Revision 1.27  2003/10/08 23:06:39  joan
// adjust generateData method
//
// Revision 1.26  2003/10/08 21:27:06  joan
// remove System.out
//
// Revision 1.25  2003/10/06 23:34:21  joan
// work on LS ABR
//
// Revision 1.24  2003/09/29 20:30:45  joan
// throw exception
//
// Revision 1.23  2003/09/26 20:46:28  joan
// add queuedABR method
//
// Revision 1.22  2003/09/22 15:09:19  joan
// work on upgrade paths
//
// Revision 1.21  2003/09/12 16:17:30  joan
// code adjust for ABR run
//
// Revision 1.20  2003/08/13 22:43:30  joan
// fix report
//
// Revision 1.19  2003/07/15 19:36:52  joan
// move changes from v111
//
// Revision 1.18  2003/07/07 19:39:30  dave
// changing etention stuff
//
// Revision 1.17  2003/07/03 22:44:53  joan
// move changes from v111
//
// Revision 1.16  2003/06/25 18:44:01  joan
// move changes from v111
//
// Revision 1.12.2.6  2003/06/19 19:54:02  joan
// working on HW Upgrade
//
// Revision 1.12.2.5  2003/06/16 23:13:54  joan
// work on SPDG
//
// Revision 1.12.2.4  2003/06/06 19:29:46  joan
// fix compile
//
// Revision 1.12.2.3  2003/06/06 19:22:54  joan
// fix compile
//
// Revision 1.12.2.2  2003/06/06 19:15:45  joan
// change signature on method viewMissingData
//
// Revision 1.12.2.1  2003/06/04 00:32:34  joan
// add logic for SPDG
//
// Revision 1.12  2003/05/16 16:01:44  joan
// fix compile
//
// Revision 1.11  2003/04/03 19:53:24  joan
// add linkToParents
//
// Revision 1.10  2003/04/01 18:43:30  joan
// add HWFeaturePDG
//
// Revision 1.9  2003/03/28 00:48:07  joan
// adjust code
//
// Revision 1.8  2003/03/26 00:30:37  joan
// debug
//
// Revision 1.7  2003/03/20 21:33:50  joan
// add SW subscription action items
//
// Revision 1.6  2003/03/17 21:42:50  joan
// fix compile
//
// Revision 1.5  2003/03/14 21:16:48  joan
// fix viewMissingData
//
// Revision 1.4  2003/03/14 18:38:50  joan
// some adjustment
//
// Revision 1.3  2003/03/12 00:48:00  joan
// fix bugs
//
// Revision 1.2  2003/03/11 17:52:24  joan
// add more work
//
// Revision 1.1  2003/03/10 17:00:05  joan
// initial load
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.*;

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
import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;
import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.eannounce.objects.*;

//public abstract class PDGActionItem extends EANActionItem {
public abstract class PDGActionItem extends PDGTemplateActionItem {
    static final long serialVersionUID = 20011106L;

    public static final String NEW_LINE = "\n";

    protected String m_strEntityType = null;  // Holds the EntityType we are updating
    protected EntityItem m_eiPDG = null;
    protected EntityItem m_eiOFPROJ = null;

    protected NavActionItem m_PDGnai = null;
    protected EditActionItem m_PDGeai = null;
    protected CreateActionItem m_PDGcai = null;
    protected DeleteActionItem m_PDGdai = null;
    protected ExtractActionItem m_PDGxai = null;
    protected EANList m_caiList = new EANList();
    protected EANList m_saiList = new EANList();
    protected EANList m_xaiList = new EANList();

    public static final String WW = "1999";
    public static final String US = "6221";
    public static final String LAD = "6204";
    public static final String CCN = "6200";
    public static final String EMEA = "6198";
    public static final String AP = "6199";

    protected SBRException m_SBREx = new SBRException();
    protected EANList m_eiList = new EANList();
    protected PDGUtility m_utility = new PDGUtility();
    protected StringBuffer m_sbActivities = new StringBuffer();
    protected EntityItem m_eiBaseCOF = null;
    protected boolean m_bRunByABR = false;
    protected String m_strABR = null;
    protected boolean m_bRunBySPDG = false;
    protected boolean m_bDataCreated = false;
    protected boolean m_bCollectInfo = false;
    protected int m_iCollectStep = 0;
    protected PDGCollectInfoList m_InfoList = null;
    protected boolean m_bMultipleParents = false;
    protected EntityItem m_eiRoot = null;
    protected EntityList m_ABReList = null;
    protected EANList m_savedEIList = new EANList();
    private String m_strExcludeCopy = "0";
    protected boolean m_bGetParentEIAttrs = false;
    protected boolean m_bGetParentNavInfo = false;
    protected int m_iParentNavInfoLevel = -1;
    protected EntityItem[] m_aParentNavInfo = null;

  	public void dereference() {
  		m_strEntityType = null;
  		if (!runBySPDG() && m_eiPDG!=null){
  			EntityGroup eg = m_eiPDG.getEntityGroup();
  			if (eg != null){
  				EntityList list = eg.getEntityList();
  				if (list != null){
  					list.dereference();
  				}
  			}
  		}
  	    m_eiPDG = null;
  	    if (m_eiOFPROJ!=null){
  			EntityGroup eg = m_eiOFPROJ.getEntityGroup();
  			if (eg != null){
  				EntityList list = eg.getEntityList();
  				if (list != null){
  					list.dereference();
  				}
  			}
  			m_eiOFPROJ = null;
  		}

  	    m_PDGnai = null;
  	    m_PDGeai = null;
  	    m_PDGcai = null;
  	    m_PDGdai = null;
  	    m_PDGxai = null;
  	    if (m_caiList!= null){
  	    	m_caiList.clear();
  	    	m_caiList = null;
  	    }
 	    if (m_saiList!= null){
 	    	m_saiList.clear();
 	    	m_saiList = null;
  	    }
	    if (m_xaiList!= null){
	    	m_xaiList.clear();
	    	m_xaiList = null;
  	    }

  	    m_SBREx = null;

	    if (m_eiList!= null){
	    	for (int i=0; i<m_eiList.size(); i++){
	    		EntityItem ei = (EntityItem)m_eiList.getAt(i);
	    		ei.dereference();
	    	}
	    	m_eiList.clear();
	    	m_eiList = null;
  	    }

  	    if (m_utility!= null){
  	    	m_utility.dereference();
  	    	m_utility = null;
  	    }
  	    m_sbActivities = null;
 	    if (m_eiBaseCOF!=null){
  			EntityGroup eg = m_eiBaseCOF.getEntityGroup();
  			if (eg != null){
  				EntityList list = eg.getEntityList();
  				if (list != null){
  					list.dereference();
  				}
  			}
  			m_eiBaseCOF = null;
  		}

  	    m_strABR = null;

   	    m_InfoList = null;
	    if (m_eiRoot!=null){
  			EntityGroup eg = m_eiRoot.getEntityGroup();
  			if (eg != null){
  				EntityList list = eg.getEntityList();
  				if (list != null){
  					list.dereference();
  				}
  			}
  		    m_eiRoot = null;
  		}


	    if (m_ABReList!= null){
	    	m_ABReList.dereference();
	    	m_ABReList = null;
	    }

	    if (m_savedEIList!= null){
	    	m_savedEIList.clear();
	    	m_savedEIList = null;
  	    }
  	    m_strExcludeCopy = null;

  	    m_aParentNavInfo = null;
  	    
  	    super.dereference();
  	}

    /*
    * Version info
    */
    public String getVersion() {
        return "$Id: PDGActionItem.java,v 1.100 2009/05/14 15:06:13 wendy Exp $";
    }

    public PDGActionItem(EANMetaFoundation  _mf, PDGActionItem _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
        setEntityType(_ai.getEntityType());
        setCreateList(_ai.getCreateList());
        setSearchList(_ai.getSearchList());
        setExtractList(_ai.getExtractList());
        setPDGNavAction(_ai.getPDGNavAction());
        setPDGEditAction(_ai.getPDGEditAction());
        setPDGCreateAction(_ai.getPDGCreateAction());
        setPDGDeleteAction(_ai.getPDGDeleteAction());
        setRunByABR(_ai.m_bRunByABR);
        setRunBySPDG(_ai.m_bRunBySPDG);
        setABR(_ai.m_strABR);
    }

    /**
     * This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
     */
    public PDGActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_emf, _db,  _prof, _strActionItemKey);
        try {
        	setDomainCheck(true);
        	 
            String strTraceBase = "PDGActionItem constructor";
            _db.debug(D.EBUG_SPEW, strTraceBase);
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs;
            Profile prof = getProfile();
            if (prof == null) {
                prof = _prof;
            }

            _db.test(prof != null, strTraceBase + " profile is null");
            rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
            rdrs = new ReturnDataResultSet(rs);
            rs.close();
            rs = null;
            _db.commit();
            _db.freeStatement();
            _db.isPending();

            // Set the class and description...
            for (int ii = 0; ii < rdrs.size();ii++) {
                String strType =  rdrs.getColumn(ii,0);
                String strCode = rdrs.getColumn(ii,1);
                String strValue = rdrs.getColumn(ii,2);

                _db.debug(D.EBUG_SPEW, "PDGActionItem gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");

                if (strType.equals("TYPE") && strCode.equals("EntityType")){
                    setEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("Navigate")){
                    setPDGNavAction(new NavActionItem(null, _db, prof, strValue));
                } else if (strType.equals("TYPE") && strCode.equals("Edit")){
                    setPDGEditAction(new EditActionItem(null, _db, prof, strValue));
                } else if (strType.equals("TYPE") && strCode.equals("Create")){
                    setPDGCreateAction(new CreateActionItem(null, _db, prof, strValue));
                } else if (strType.equals("TYPE") && strCode.equals("Extract")){
                    setPDGExtractAction(new ExtractActionItem(null, _db, prof, strValue));
                } else if (strType.equals("TYPE") && strCode.equals("Delete")){
                    setPDGDeleteAction(new DeleteActionItem(null, _db, prof, strValue));
                } else if (strType.equals("TYPE") && strCode.equals("ABR")){
                    setRunByABR(true);
                    setABR(strValue);
                } else if (strType.equals("CREATE")) {
                    m_caiList.put(strCode, strValue);
                } else if (strType.equals("SEARCH")) {
                    m_saiList.put(strCode, strValue);
                } else if (strType.equals("EXTRACT")) {
                    m_xaiList.put(strCode, strValue);
                } else if (strType.equals("TYPE") && strCode.equals("DomainCheck")) { // default is on, allow off
				    setDomainCheck(!strValue.equals("N")); //RQ0713072645
                } else {
                    _db.debug(D.EBUG_ERR,"PDGActionItem No home for this SBR Action/Attribute" + strType + ":" + strCode + ":" + strValue);
                }
            }
        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }

    }

    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("PDGActionItem:" + getKey() + ":desc:" + getLongDescription());
        strbResult.append(":purpose:" + getPurpose());
        strbResult.append(":entitytype:" + getEntityType() + "/n");
        return strbResult.toString();
    }

    public String getPurpose() {
        return "PDGActionItem";
    }

    public String getEntityType() {
        return m_strEntityType;
    }

    protected void setEntityType (String _str) {
        m_strEntityType = _str;
    }

    public EANList getCreateList() {
        return m_caiList;
    }

    protected void setCreateList (EANList _list) {
        m_caiList = _list;
    }

    public EANList getSearchList() {
        return m_saiList;
    }

    protected void setSearchList (EANList _list) {
        m_saiList = _list;
    }

    public EANList getExtractList() {
        return m_xaiList;
    }

    protected void setExtractList (EANList _list) {
        m_xaiList = _list;
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

    public CreateActionItem getPDGCreateAction() {
        return m_PDGcai;
    }

    protected void setPDGCreateAction(CreateActionItem _cai) {
        m_PDGcai = _cai;
    }

    public DeleteActionItem getPDGDeleteAction() {
        return m_PDGdai;
    }

    protected void setPDGDeleteAction(DeleteActionItem _dai) {
        m_PDGdai = _dai;
    }


    public ExtractActionItem getPDGExtractAction() {
        return m_PDGxai;
    }

    protected void setPDGExtractAction(ExtractActionItem _xai) {
        m_PDGxai = _xai;
    }

    public boolean runByABR() {
        return m_bRunByABR;
    }

    protected void setRunByABR(boolean _b) {
        m_bRunByABR = _b;
    }

    public void enableABR(Database _db, RemoteDatabaseInterface _rdi) {
        return;
    }

    public boolean runBySPDG() {
        return m_bRunBySPDG;
    }

    protected void setRunBySPDG(boolean _b) {
        m_bRunBySPDG = _b;
    }

    public boolean isDataCreated() {
        return m_bDataCreated;
    }

    protected void setDataCreated(boolean _b) {
        m_bDataCreated = _b;
    }

    public boolean isCollectInfo() {
        return m_bCollectInfo;
    }

    protected void setCollectInfo(boolean _b) {
        m_bCollectInfo = _b;
    }

    public int getCollectStep() {
        return m_iCollectStep;
    }

    public boolean isCollectParentNavInfo() {
        return m_bGetParentNavInfo;
    }

    protected void setCollectParentNavInfo(boolean _b) {
        m_bGetParentNavInfo = _b;
    }

    public int getParentNavInfoLevel() {
        return m_iParentNavInfoLevel;
    }

    public void setParentNavInfo(EntityItem[] _aei) {
        m_aParentNavInfo = _aei;
    }

    public EntityItem[] getParentNavInfo() {
        return m_aParentNavInfo;
    }
    public String getStepDescription(int _iStep) {
        return "N/A";
    }

    public void setPDGCollectInfo(PDGCollectInfoList _cl, int _iStep, RowSelectableTable _eiRst) throws SBRException, MiddlewareException {
        m_InfoList = _cl;
    }

    public void setPDGCollectInfo(PDGCollectInfoList _cl, EANMetaAttribute _meta, RowSelectableTable _eiRst) throws SBRException, MiddlewareException {
        m_InfoList = _cl;
    }

    public void resetPDGCollectInfo() {
        m_InfoList = null;
    }

    public StringBuffer getActivities() {
        return m_sbActivities;
    }

    public boolean canHaveMultipleParents() {
        return m_bMultipleParents;
    }

    protected void setMultipleParents(boolean _b) {
        m_bMultipleParents = _b;
    }

    public void linkToParents(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, EntityItem _ei, EntityItem[] _aeiParent) throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException, RemoteException {
        if (_db == null && _rdi == null) return;

        EntityItem[] aeiChild = {_ei};
        CreateActionItem cai = getPDGCreateAction();
        MetaLink ml = cai.getMetaLink();
        if (_db != null) {
            EANUtility.linkEntityItems(_db, _prof, "NODUPES", _aeiParent, aeiChild, ml, EANUtility.LINK_DEFAULT, 1);
        } else {
            EANUtility.linkEntityItems(_rdi, _prof, "NODUPES", _aeiParent, aeiChild, ml, EANUtility.LINK_DEFAULT, 1);
        }
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

    public void setExcludeCopy(String _s) {
        m_strExcludeCopy = _s;
    }

    public String getExcludeCopy() {
        return m_strExcludeCopy;
    }

    protected void setABR(String _s) {
        m_strABR = _s;
    }

    public String getABR() {
        return m_strABR;
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

    public PDGCollectInfoList collectInfo(int iStep) {
        return null;
    }

    public PDGCollectInfoList collectInfo(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, int _iStep) throws SQLException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException, SBRException {
        if (_db != null) {
            return _db.collectInfo(_prof, _iStep, this);
        } else {
            return _rdi.collectInfo(_prof, _iStep, this);
        }
    }

    public PDGCollectInfoList collectPDGInfo(Database _db, Profile _prof, int _iStep) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        return null;
    }

    public PDGCollectInfoList collectInfo(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, EANMetaAttribute _meta) throws SQLException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException, SBRException {
        if (_db != null) {
            return _db.collectInfo(_prof, _meta, this);
        } else {
            return _rdi.collectInfo(_prof, _meta, this);
        }
    }

    public PDGCollectInfoList collectPDGInfo(Database _db, Profile _prof, EANMetaAttribute _meta) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        return null;
    }

    /**
     * @return true if successful, false if nothing to update or unsuccessful
     */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        return true;
    }

    public void setABReList(EntityList _el) {
        m_ABReList = _el;
    }

    protected void generateData(Database _db, Profile _prof, StringBuffer _sbMissing, String _strSearchKey) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "PDGActionItem generateData ";
        //_db.debug(D.EBUG_SPEW,strTraceBase + _sbMissing.toString());
        //m_sbActivities = new StringBuffer();
        Vector vctReturnEntityKeys = new Vector();
        try {
            boolean bRestart = false;
            StringTokenizer st = new StringTokenizer(_sbMissing.toString(),"\n");
            Hashtable ht = new Hashtable();

            while (st.hasMoreTokens()) {
                String s = st.nextToken();
                StringTokenizer st1 = new StringTokenizer(s,"|");

                if (st1.hasMoreTokens()) {
                    boolean bContWCreate = true;
                    EntityItem currentEI = null;

                    String strParentEntity = st1.nextToken().trim();
                    int iLevel = Integer.parseInt(st1.nextToken());

                    // get parent for later links
                    EntityItem eiParent = null;
                    if (strParentEntity != null && strParentEntity.length() > 0) {
                        StringTokenizer stParent = new StringTokenizer(strParentEntity,"-");
                        if (stParent.hasMoreTokens()) {
                            String strParentType = stParent.nextToken();
                            int iParentID = Integer.parseInt(stParent.nextToken());
                            // get parent ei description if needed
                            if (m_bGetParentEIAttrs) {
                                //_db.debug(D.EBUG_SPEW,strTraceBase + " in getParentEIAttrs");
                                EntityGroup eg = m_utility.getEntityGroup(strParentEntity);
                                if (eg ==null) {
                                    eg = new EntityGroup(null, _db, _prof, strParentType, "Edit", false);
                                }
                                _prof = m_utility.setProfValOnEffOn(_db, _prof);
                                eiParent = new EntityItem(eg, _prof, _db, strParentType, iParentID);
                            } else {
                                eiParent = new EntityItem(null, _prof, strParentType, iParentID);
                            }
                        }
                    } else {
                        eiParent = (EntityItem)ht.get((iLevel-1) + "");
                    }

                    if (eiParent == null) {
                        eiParent = m_eiRoot;
                    }

                    // get stuff for Entity
                    String strEntity = st1.nextToken();
                    int i1 = strEntity.indexOf(":");
                    String strEntityType = strEntity;
                    String strAttributes = "";
                    if (i1 > -1 ){
                        strEntityType = strEntity.substring(0, i1);
                        strAttributes = strEntity.substring(i1+1);
                    }

                    String strAction = st1.nextToken();

                    int iSaveAct = strAction.indexOf("saveAct");
                    // get Relator info
                    String strRelatorInfo = st1.nextToken();
                    String strRelatorType = "";
                    int iAttrO = strRelatorInfo.indexOf("[");
                    if (iAttrO > -1) {
                        strRelatorType = strRelatorInfo.substring(0,iAttrO);
                    } else {
                        strRelatorType = strRelatorInfo;
                    }

                    //find the item if needed
                    int iFind = strAction.indexOf("find");
                    if (iFind > -1) {
                        if (strAttributes.indexOf("map") >= 0) {
                            currentEI = m_utility.findEntityItem(m_eiList, strEntityType, strAttributes);
                            if (currentEI == null) {
                                String strSai = (String)m_saiList.get(strEntityType);
                                EntityItem[] aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, strEntityType, strAttributes);
                                if (aei.length > 0) {
                                    currentEI = aei[0];
                                    // save for later search
                                    m_eiList.put(currentEI);
                                }
                            }
                        }
                    }

                    int iUpdate = strAction.indexOf("update");
                    if (iUpdate > -1 && currentEI != null) {
                        OPICMList attList = m_utility.getAttributeListForUpdate(strEntityType, strAttributes);

                        m_utility.updateAttribute(_db, _prof, currentEI, attList);
                        bContWCreate = false;
                    }

                    // link them if there's command link
                    int iLink = strAction.indexOf("linkParent");
                    if (iLink > -1 && currentEI != null) {
                        // use parent entity, relator,link
                        ht.put(iLevel + "", currentEI);
                        EntityItem[] aei = {currentEI};
                        if (eiParent != null) {
                            //_db.debug(D.EBUG_SPEW,strTraceBase + " display eiParent: " + eiParent.toString());
                            m_utility.linkEntities(_db, _prof, eiParent, aei, strRelatorInfo);

                            if (iSaveAct >= 0) {
                                m_sbActivities.append("<br />" + m_utility.getActivities());
                                m_utility.resetActivities();
                            }

                            // after find/link, pull the VE, and test again if required.
                            int iRestart = strAction.indexOf("restart");
                            if (iRestart > -1) {
                                bRestart = true;
                                break;
                            }
                        }

                        bContWCreate = false;
                    }

                    if (bContWCreate) {
                        int iCreate = strAction.indexOf("create");
                        if (iCreate > -1) {
                            //prepare the list of attributes
                            OPICMList attList = m_utility.getAttributeListForCreate(strEntityType, strAttributes, strRelatorInfo);
                            String strCai = (String)m_caiList.get(strRelatorType);

                            try {
	                            _db.test(eiParent != null, " eiParent is null for: " + s);

                                currentEI = m_utility.createEntityByRST(_db, _prof, eiParent, attList, strCai, strRelatorType, strEntityType);
                                _db.test(currentEI != null, " ei is null for: " + s);
                                ht.put(iLevel + "", currentEI);

                                // save for later search
                                m_eiList.put(currentEI);

                                if (iSaveAct >= 0) {
                                    m_sbActivities.append("<br />" + m_utility.getActivities());
                                    m_utility.resetActivities();
                                }
                            } catch (MiddlewareException _mex) {
                                _mex.printStackTrace();
                                if (_mex instanceof MiddlewareSFRuleException || _mex instanceof MiddlewareBusinessRuleException) {
                                    if (this instanceof ALWRCSOLABR001PDG || this instanceof ALWRCSOLABR002PDG || this instanceof ALWRCVARABR001PDG) {
                                        System.out.println(_mex.getMessage());
                                        if (_mex instanceof MiddlewareSFRuleException) {
                                            m_sbActivities.append("<font color=red>" + _mex.getMessage() + "</font>\n");
                                        } else {
                                            m_sbActivities.append("<font color=red>" + _mex.toString() + "</font>\n");
                                        }

                                        return;
                                    }
                                }
                                throw _mex;
                            }
                        }

                        // create the item
                        int iCreateCopy = strAction.indexOf("copyUpdate");
                        if (iCreateCopy > -1) {
                            //prepare the list of attributes
                            //OPICMList attList = m_utility.getAttributeListForCreate(strEntityType, strAttributes, strRelatorInfo);

                            String strCopyUpdate = strAction.substring(iCreateCopy);
                            int iEnd = strCopyUpdate.indexOf(";");
                            if (iEnd > -1) {
                                strCopyUpdate = strCopyUpdate.substring(0,iEnd);
                            }
                            int iU = strCopyUpdate.indexOf("_");
                            EntityItem eiCF = null;
                            if (iU > -1) {
                                String strCF = strCopyUpdate.substring(iU+1);
                                eiCF = (EntityItem)m_eiList.get(strCF);
                            }

                            if (eiCF == null) {
                                continue;
                            }

                            currentEI = m_utility.createAndCopy(_db, _prof, eiCF, strEntityType, m_strExcludeCopy);

                            _db.test(currentEI != null, " ei is null for " + s);
                            ht.put(iLevel + "", currentEI);

                            // update some attributes
                            OPICMList attL = m_utility.getAttributeListForUpdate(strEntityType, strAttributes);

                            _prof = m_utility.setProfValOnEffOn(_db, _prof);
                            try {
                                // O.K. if we made it this far.. we must turn off the LSCRSID for this entitytype...
                                if (this instanceof LSCONVERT01PDG || this instanceof LSCONVERT02PDG) {
                                    _db.debug(D.EBUG_SPEW,"PDGActionItem.generateData():call 0028 off");
                                    _db.callGBL0028(new ReturnStatus(-1),_prof.getEnterprise(),_prof.getOPWGID(),eiCF.getEntityType(), eiCF.getEntityID(),"LSCRSID",0);
                                    _db.commit();
                                    _db.freeStatement();
                                }
                                m_utility.updateAttribute(_db, _prof, currentEI, attL);
                                // turn it back on...
                                if (this instanceof LSCONVERT01PDG || this instanceof LSCONVERT02PDG) {
                                    _db.debug(D.EBUG_SPEW,"PDGActionItem.generateData():call0028 back on");
                                    _db.callGBL0028(new ReturnStatus(-1),_prof.getEnterprise(),_prof.getOPWGID(),eiCF.getEntityType(), eiCF.getEntityID(),"LSCRSID",1);
                                    _db.commit();
                                    _db.freeStatement();
                                }
                            } catch (MiddlewareException _mex) {
                                _mex.printStackTrace();
                                EANUtility.deactivateEntity(_db, _prof, currentEI);
                                // turn it back on...
                                if (this instanceof LSCONVERT01PDG || this instanceof LSCONVERT02PDG) {
                                    _db.debug(D.EBUG_SPEW,"PDGActionItem.generateData():call0028 back on");
                                    _db.callGBL0028(new ReturnStatus(-1),_prof.getEnterprise(),_prof.getOPWGID(),eiCF.getEntityType(), eiCF.getEntityID(),"LSCRSID",1);
                                    _db.freeStatement();
                                    _db.commit();
                                    _db.freeStatement();
                                }
                                if (_mex instanceof MiddlewareSFRuleException || _mex instanceof MiddlewareBusinessRuleException) {
                                    _mex.printStackTrace();
                                    if (this instanceof ALWRCSOLABR001PDG || this instanceof ALWRCSOLABR002PDG || this instanceof ALWRCVARABR001PDG) {
                                        _db.debug(D.EBUG_SPEW,strTraceBase + " calling deactivate : " + currentEI.dump(false));
                                        EANUtility.deactivateEntity(_db, _prof, currentEI);
                                        if (_mex instanceof MiddlewareSFRuleException) {
                                            m_sbActivities.append("<font color=red>" + _mex.getMessage() + "</font>\n");
                                        } else {
                                            m_sbActivities.append("<font color=red>" + _mex.toString() + "</font>\n");
                                        }
                                        continue;
                                    }
                                }
                            }
                            //pull out attributes
                            EntityGroup eg = m_ABReList.getEntityGroup(strEntityType);
                            if (eg != null) {
                                _prof = m_utility.setProfValOnEffOn(_db, _prof);
                                currentEI = new EntityItem(eg, _prof, _db, currentEI.getEntityType(), currentEI.getEntityID());
                            }

                            // save for later search
                            m_eiList.put(currentEI);

                            if (iSaveAct >= 0) {
                                m_sbActivities.append("<ITEM><ACTION>Create</ACTION><ENTITYKEY>" + currentEI.getKey() + "</ENTITYKEY><ENTITYDISPLAY>" + PDGUtility.convertToHTML(currentEI.toString()) + "</ENTITYDISPLAY><PARENT>" + eiParent.getKey() + ":" + PDGUtility.convertToHTML(eiParent.toString()) + "</PARENT></ITEM>\n");
                                m_utility.resetActivities();
                            }

                            EntityItem[] aei = {currentEI};

                            int iCopy = strRelatorInfo.indexOf("copyUpdate");

                            EntityItem eiCR = null;
                            if (iCopy >= 0) {
                                //String strRType = "";
                                String strRAttr = "";
                                int iAO = strRelatorInfo.indexOf("[");
                                int iAC = strRelatorInfo.indexOf("]");
                                if (iAO > -1) {
                                  //  strRType = strRelatorInfo.substring(0,iAO);
                                    strRAttr = strRelatorInfo.substring(iAO+1, (iAC > -1 ? iAC: strRelatorInfo.length()));
                                }

                                String strCopyR = strRAttr.substring(iCopy);
                                int iE = strCopyR.indexOf(";");
                                if (iE > -1) {
                                    strCopyR = strCopyR.substring(0,iE);
                                }
                                int iUn = strCopyR.indexOf("_");

                                if (iUn > -1) {
                                    String strCF = strCopyR.substring(iUn+1);
                                    eiCR = (EntityItem)m_eiList.get(strCF);
                                }
                            }
                            OPICMList ol = m_utility.linkEntities(_db, _prof, eiParent, aei, strRelatorInfo);
                            if (eiCR != null) {
								if (ol !=null){ // MN32841099
									for (int i=0; i < ol.size(); i++) {
										Object obj = ol.getAt(i);
										if (obj instanceof ReturnRelatorKey) {
											ReturnRelatorKey rrk = (ReturnRelatorKey)obj;
											//String strRType = rrk.getEntityType();
											_prof = m_utility.setProfValOnEffOn(_db, _prof);
											EntityItem ei = new EntityItem(null, _prof, rrk.getEntityType(), rrk.getReturnID());

											m_utility.copyEntity(_db, _prof, eiCR, ei, m_strExcludeCopy, true);
										}
									}
								}else {
                                	_db.debug(D.EBUG_SPEW,strTraceBase + "copyupdate OPICMList ol is null from link");
                            	}
                            } else {
                                _db.debug(D.EBUG_SPEW,strTraceBase + " copy from Relator is null");
                            }
                        }
                    }

                    int ilinkAllInEL = strAction.indexOf("linkAllInEL");
                    if (ilinkAllInEL > -1) {
                        if (eiParent == null) {
                            _db.debug(D.EBUG_SPEW,strTraceBase + " linkAllInEL eiParent is null.");
                            continue;
                        }
                        String strLinkAllEI = strAction.substring(ilinkAllInEL);
                        int iEnd = strLinkAllEI.indexOf(";");
                        if (iEnd > -1) {
                            strLinkAllEI=strLinkAllEI.substring(0,iEnd);
                        }

                        int iU = strLinkAllEI.indexOf("_");
                        if (iU > -1) {
                            strLinkAllEI = strLinkAllEI.substring(iU+1);
                        }

                        if (strLinkAllEI != null) {
                            EntityGroup eg = m_ABReList.getEntityGroup(strLinkAllEI);
                            if (eg != null) {
                                for (int i=0; i < eg.getEntityItemCount(); i++) {
                                    EntityItem ei = eg.getEntityItem(i);
                                    EntityItem eic = null;
                                    if (eg.isRelator() || eg.isAssoc()) {
                                        eic = (EntityItem)ei.getDownLink(0);
                                        if (! eic.getEntityType().equals(strEntityType)) {
                                            eic = (EntityItem)ei.getUpLink(0);
                                        }
                                    } else {
                                        eic = ei;
                                    }
                                    if (eic.getEntityType().equals(strEntityType)) {
                                        EntityItem[] aei = {eic};
                                        m_utility.linkEntities(_db, _prof, eiParent, aei, strRelatorInfo);

                                        if (iSaveAct >= 0) {
                                            m_sbActivities.append("<br />" + m_utility.getActivities());
                                            m_utility.resetActivities();
                                        }
                                    }
                                }
                            } else {
                                _db.debug(D.EBUG_SPEW,strTraceBase + " linkAllInEL eg is null");
                            }
                        }
                    }

                    // save entities for later link to root
                    int iLinkRoot = strAction.indexOf("linkRoot");
                    if (iLinkRoot > -1) {
                        if (currentEI == null) {
                            _db.debug(D.EBUG_SPEW,strTraceBase + " linkRoot currentEI is null.");
                            continue;
                        }

                        String strLinkRoot = strAction.substring(iLinkRoot);
                        int iEnd = strLinkRoot.indexOf(";");
                        if (iEnd > -1) {
                            strLinkRoot=strLinkRoot.substring(0,iEnd);
                        }
                        int iU = strLinkRoot.indexOf("_");
                        if (iU > -1) {
                            String strRelator = strLinkRoot.substring(iU+1);
                            EntityItem[] aei = {currentEI};
                            m_utility.linkEntities(_db, _prof, m_eiRoot, aei, strRelator);
                        }
                    }

                    int iSaveEI = strAction.indexOf("saveEI");
                    if (iSaveEI >=0 ) {
                        _db.debug(D.EBUG_SPEW,strTraceBase + " save entity");
                        m_savedEIList.put(currentEI);
                    }
                }
            }

            if (bRestart) {
                checkMissingData(_db, _prof, true);
            }
        } catch (SBRException ex) {
            if (vctReturnEntityKeys.size() > 0) {
                m_utility.link(_db, _prof, vctReturnEntityKeys);
            }

            throw ex;
        }
    }

    protected void generateDataII(Database _db, Profile _prof, StringBuffer _sbMissing, String _strSearchKey) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "PDGActionItem generateDataII ";
        _db.debug(D.EBUG_SPEW, strTraceBase);
        //m_sbActivities = new StringBuffer();
        Vector vctReturnEntityKeys = new Vector();
        try {
            boolean bRestart = false;
            StringTokenizer st = new StringTokenizer(_sbMissing.toString(),"\n");
            Hashtable ht = new Hashtable();
			int stcnt=0;
            while (st.hasMoreTokens()) {
                String s = st.nextToken();
                _db.debug(D.EBUG_SPEW,strTraceBase + "s["+(++stcnt)+"]: " + s);
                StringTokenizer st1 = new StringTokenizer(s,"|");
                if (st1.hasMoreTokens()) {
                    boolean bContWCreate = true;
                    EntityItem currentEI = null;

                    String strParentEntity = st1.nextToken().trim();
                    int iLevel = Integer.parseInt(st1.nextToken());
                    String strDirection = st1.nextToken().trim();

                    // get parent for later links
                    EntityItem eiParent = null;
                    if (strParentEntity != null && strParentEntity.length() > 0) {
                        StringTokenizer stParent = new StringTokenizer(strParentEntity,"-");
                        if (stParent.hasMoreTokens()) {
                            String strParentType = stParent.nextToken();
                            int iParentID = Integer.parseInt(stParent.nextToken());
                            // MN32841099 try to find it in lists
							eiParent = (EntityItem)ht.get((iLevel-1) + "");
							if (eiParent !=null){
								if(!eiParent.getKey().equals(strParentType+iParentID)){
									eiParent = null;  // wrong one
								}
							}
							if (eiParent==null){ // MN32841099 try by key
								eiParent = (EntityItem)m_eiList.get(strParentType+iParentID);
							}
							if (eiParent==null){ // MN32841099 try by entitytype
								eiParent = (EntityItem)m_eiList.get(strParentType);
								if(eiParent!=null && !eiParent.getKey().equals(strParentType+iParentID)){
									eiParent = null;  // wrong one
								}
							}

							if (eiParent ==null) { // create it
	                            eiParent = new EntityItem(null, _prof, strParentType, iParentID);
							}
                        }
                    } else {
                        eiParent = (EntityItem)ht.get((iLevel-1) + "");
                    }

                    if (eiParent == null) {
                        eiParent = m_eiRoot;
                    }

                    // get stuff for Entity
                    String strEntity = st1.nextToken();
                    int i1 = strEntity.indexOf(":");
                    String strEntityType = strEntity;
                    String strAttributes = "";
                    if (i1 > -1 ){
                        strEntityType = strEntity.substring(0, i1);
                        strAttributes = strEntity.substring(i1+1);
                    }

                    String strAction = st1.nextToken();

                    int iSaveAct = strAction.indexOf("saveAct");
                    // get Relator info
                    String strRelatorInfo = st1.nextToken();
                    String strRelatorType = "";
                    int iAttrO = strRelatorInfo.indexOf("[");
                    if (iAttrO > -1) {
                        strRelatorType = strRelatorInfo.substring(0,iAttrO);
                    } else {
                        strRelatorType = strRelatorInfo;
                    }

                    //find the item if needed
                    int iFind = strAction.indexOf("find");
                    if (iFind > -1) {
                        if (strAttributes.indexOf("map") >= 0) {
                            int iEqual = strAttributes.indexOf("=");
                            String strHead = strAttributes.substring(4, iEqual);
                            currentEI = m_utility.findEntityItem(m_eiList, strEntityType, strAttributes);
                            if (currentEI == null) {
                                String strSai = (String)m_saiList.get(strEntityType);
                                EntityItem[] aei = null;
                                if (strHead.indexOf(":") >= 0) {
                                    aei = m_utility.dynaSearchII(_db, _prof, m_eiPDG, strSai, strEntityType, strAttributes);
                                } else {
                                    aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, strEntityType, strAttributes);
                                }

                                if (aei.length > 0) {
                                    currentEI = aei[0];
                                    // save for later search
                                    m_eiList.put(currentEI);
                                }

                                if (currentEI != null) {
                                    ht.put(iLevel + "", currentEI);
                                }
                            }
                        }
                    }

                    int iUpdate = strAction.indexOf("update");
                    if (iUpdate > -1 && currentEI != null) {
                        OPICMList attList = m_utility.getAttributeListForUpdate(strEntityType, strAttributes);

                        m_utility.updateAttribute(_db, _prof, currentEI, attList);
                        bContWCreate = false;
                    }

                    // link them if there's command link
                    int iLink = strAction.indexOf("linkParent");
                    if (iLink > -1 && currentEI != null) {
                        // use parent entity, relator,link
                        if (eiParent != null) {
                            OPICMList ol = null;

                            int iCopy = strRelatorInfo.indexOf("copyUpdate");

                            EntityItem eiCR = null;
                            if (iCopy >= 0) {
                                String strRType = "";
                                String strRAttr = "";
                                int iAO = strRelatorInfo.indexOf("[");
                                int iAC = strRelatorInfo.indexOf("]");
                                if (iAO > -1) {
                                    strRType = strRelatorInfo.substring(0,iAO);
                                    strRAttr = strRelatorInfo.substring(iAO+1, (iAC > -1 ? iAC: strRelatorInfo.length()));
                                }

                                String strCopyR = strRAttr.substring(iCopy);
                                int iE = strCopyR.indexOf(";");
                                if (iE > -1) {
                                    strCopyR = strCopyR.substring(0,iE);
                                }
                                int iUn = strCopyR.indexOf("_");

                                if (iUn > -1) {
                                    String strCF = strCopyR.substring(iUn+1);
                                    eiCR = (EntityItem)m_eiList.get(strCF);
                                    if (eiCR == null) {
                                        _db.debug(D.EBUG_SPEW,strTraceBase + "copy relator 03: eiCR is null");
                                    }
                                }
                                strRelatorInfo = strRType;
                            }

                            if (strDirection.equals("U")) {
                                EntityItem[] aei = {eiParent};
                                ol = m_utility.linkEntities(_db, _prof, currentEI, aei, strRelatorInfo);
                            } else {
                                EntityItem[] aei = {currentEI};
                                ol = m_utility.linkEntities(_db, _prof, eiParent, aei, strRelatorInfo);
                            }

							if (ol !=null){ // MN32841099
								for (int i=0; i < ol.size(); i++) {
									Object obj = ol.getAt(i);
									if (obj instanceof ReturnRelatorKey) {
										ReturnRelatorKey rrk = (ReturnRelatorKey)obj;
										String strRType = rrk.getEntityType();

										EntityItem ei = new EntityItem(null, _prof, rrk.getEntityType(), rrk.getReturnID());
										if (eiCR != null) {
											m_utility.copyEntity(_db, _prof, eiCR, ei, m_strExcludeCopy, true);
										}
										EntityGroup egR = m_utility.getEntityGroup(strRType);
										if (egR ==null) {
											egR = new EntityGroup(null, _db, _prof, strRType, "Edit", false);
										}
										_prof = m_utility.setProfValOnEffOn(_db, _prof);
										ei = new EntityItem(egR, _prof, _db, rrk.getEntityType(), rrk.getReturnID());
										m_eiList.put(ei);
									}
								}
							}else {
                               	_db.debug(D.EBUG_SPEW,strTraceBase + "linkParent OPICMList ol is null from link");
                            }

                            if (iSaveAct >= 0) {
                                m_sbActivities.append("<br />" + m_utility.getActivities());
                                m_utility.resetActivities();
                            }

                            // after find/link, pull the VE, and test again if required.
                            int iRestart = strAction.indexOf("restart");
                            if (iRestart > -1) {
                                bRestart = true;
                                break;
                            }
                        }

                        bContWCreate = false;
                    }

                    if (bContWCreate) {
                        int iCreate = strAction.indexOf("create");
                        if (iCreate > -1) {
                            //prepare the list of attributes
                            OPICMList attList = m_utility.getAttributeListForCreate(strEntityType, strAttributes, strRelatorInfo);
                            String strCai = (String)m_caiList.get(strRelatorType);
                            if (strDirection.equals("U")) {
                                // create stand alone entity
                                attList = m_utility.getAttributeListForCreate(strEntityType, strAttributes, "");
                                strCai = (String)m_caiList.get(strEntityType);
                            }

	                        _db.test(eiParent != null, " eiParent is null for: " + s);
                            currentEI = m_utility.createEntityByRST(_db, _prof, eiParent, attList, strCai, strRelatorType, strEntityType);
                            _db.test(currentEI != null, " ei is null for: " + s);
                            ht.put(iLevel + "", currentEI);

                            // save for later search
                            m_eiList.put(currentEI);

                            if (strDirection.equals("U")) {
                                // link to 1 level up
                                EntityItem[] aei = {eiParent};
                                OPICMList ol = m_utility.linkEntities(_db, _prof, currentEI, aei, strRelatorInfo);

								if (ol !=null){ // MN32841099
									for (int i=0; i < ol.size(); i++) {
										Object obj = ol.getAt(i);
										if (obj instanceof ReturnRelatorKey) {
											ReturnRelatorKey rrk = (ReturnRelatorKey)obj;
											String strRType = rrk.getEntityType();
											EntityGroup eg = m_utility.getEntityGroup(strRType);
											if (eg ==null) {
												eg = new EntityGroup(null, _db, _prof, strRType, "Edit", false);
											}
											_prof = m_utility.setProfValOnEffOn(_db, _prof);
											EntityItem ei = new EntityItem(eg, _prof, _db, rrk.getEntityType(), rrk.getReturnID());
											m_eiList.put(ei);
										}
									}
								}else {
                                	_db.debug(D.EBUG_SPEW,strTraceBase + "create OPICMList ol is null from link");
                            	}
                            }

                            if (iSaveAct >= 0) {
                                m_sbActivities.append("<br />" + m_utility.getActivities());
                                m_utility.resetActivities();
                            }
                        }

                        // create the item
                        int iCreateCopy = strAction.indexOf("copyUpdate");
                        if (iCreateCopy > -1) {
                            //prepare the list of attributes
                            //OPICMList attList = m_utility.getAttributeListForCreate(strEntityType, strAttributes, strRelatorInfo);
                            String strCopyUpdate = strAction.substring(iCreateCopy);
                            int iEnd = strCopyUpdate.indexOf(";");
                            if (iEnd > -1) {
                                strCopyUpdate = strCopyUpdate.substring(0,iEnd);
                            }
                            int iU = strCopyUpdate.indexOf("_");
                            EntityItem eiCF = null;
                            if (iU > -1) {
                                String strCF = strCopyUpdate.substring(iU+1);
                                eiCF = (EntityItem)m_eiList.get(strCF);
                            }

                            if (eiCF == null) {
                                continue;
                            }

                            currentEI = m_utility.createAndCopy(_db, _prof, eiCF, strEntityType, m_strExcludeCopy);

                            _db.test(currentEI != null, " ei is null for " + s);
                            ht.put(iLevel + "", currentEI);

                            // update some attributes
                            OPICMList attL = m_utility.getAttributeListForUpdate(strEntityType, strAttributes);

                            _prof = m_utility.setProfValOnEffOn(_db, _prof);

                            try {
                                // O.K. if we made it this far.. we must turn off the LSCRSID for this entitytype...
                                if (this instanceof LSCONVERT01PDG || this instanceof LSCONVERT02PDG) {
                                    _db.debug(D.EBUG_SPEW,"PDGActionItem.generateData():call 0028 off");
                                    _db.callGBL0028(new ReturnStatus(-1),_prof.getEnterprise(),_prof.getOPWGID(),eiCF.getEntityType(), eiCF.getEntityID(),"LSCRSID",0);
                                    _db.commit();
                                    _db.freeStatement();
                                }
                                m_utility.updateAttribute(_db, _prof, currentEI, attL);

                                // turn it back on...

                                if (this instanceof LSCONVERT01PDG || this instanceof LSCONVERT02PDG) {
                                    _db.debug(D.EBUG_SPEW,"PDGActionItem.generateData():call0028 back on");
                                    _db.callGBL0028(new ReturnStatus(-1),_prof.getEnterprise(),_prof.getOPWGID(),eiCF.getEntityType(), eiCF.getEntityID(),"LSCRSID",1);
                                    _db.commit();
                                    _db.freeStatement();
                                }
                            } catch (MiddlewareException _mex) {
                                _mex.printStackTrace();
                                EANUtility.deactivateEntity(_db, _prof, currentEI);
                                // turn it back on...
                                if (this instanceof LSCONVERT01PDG || this instanceof LSCONVERT02PDG) {
                                    _db.debug(D.EBUG_SPEW,"PDGActionItem.generateData():call0028 back on");
                                    _db.callGBL0028(new ReturnStatus(-1),_prof.getEnterprise(),_prof.getOPWGID(),eiCF.getEntityType(), eiCF.getEntityID(),"LSCRSID",1);
                                    _db.freeStatement();
                                    _db.commit();
                                    _db.freeStatement();
                                }
                                if (_mex instanceof MiddlewareSFRuleException || _mex instanceof MiddlewareBusinessRuleException) {
                                    _mex.printStackTrace();
                                    if (this instanceof ALWRCSOLABR001PDG || this instanceof ALWRCSOLABR002PDG || this instanceof ALWRCVARABR001PDG) {
                                        if (_mex instanceof MiddlewareSFRuleException) {
                                            m_sbActivities.append("<font color=red>" + _mex.getMessage() + "</font>\n");
                                        } else {
                                            m_sbActivities.append("<font color=red>" + _mex.toString() + "</font>\n");
                                        }
                                        continue;
                                    }
                                }
                            }

                            //pull out attributes
                            if (m_ABReList != null) {
                                EntityGroup eg = m_ABReList.getEntityGroup(strEntityType);
                                if (eg != null) {
                                    _prof = m_utility.setProfValOnEffOn(_db, _prof);
                                    currentEI = new EntityItem(eg, _prof, _db, currentEI.getEntityType(), currentEI.getEntityID());
									ht.put(iLevel + "", currentEI); // MN32841099 replace this in table so it is a valid parent
                                }
                            }
                            // save for later search
                            m_eiList.put(currentEI);

                            if (iSaveAct >= 0) {
                                m_sbActivities.append("<ITEM><ACTION>Create</ACTION><ENTITYKEY>" + currentEI.getKey() + "</ENTITYKEY><ENTITYDISPLAY>" + PDGUtility.convertToHTML(currentEI.toString()) + "</ENTITYDISPLAY><PARENT>" + eiParent.getKey() + ":" + PDGUtility.convertToHTML(eiParent.toString()) + "</PARENT></ITEM>\n");
                                m_utility.resetActivities();
                            }

                            int iCopy = strRelatorInfo.indexOf("copyUpdate");

                            EntityItem eiCR = null;
                            if (iCopy >= 0) {
                                String strRType = "";
                                String strRAttr = "";
                                int iAO = strRelatorInfo.indexOf("[");
                                int iAC = strRelatorInfo.indexOf("]");
                                if (iAO > -1) {
                                    strRType = strRelatorInfo.substring(0,iAO);
                                    strRAttr = strRelatorInfo.substring(iAO+1, (iAC > -1 ? iAC: strRelatorInfo.length()));
                                }

                                String strCopyR = strRAttr.substring(iCopy);
                                int iE = strCopyR.indexOf(";");
                                if (iE > -1) {
                                    strCopyR = strCopyR.substring(0,iE);
                                }
                                int iUn = strCopyR.indexOf("_");

                                if (iUn > -1) {
                                    String strCF = strCopyR.substring(iUn+1);
                                    eiCR = (EntityItem)m_eiList.get(strCF);

                                    if (eiCR == null) {
                                        _db.debug(D.EBUG_SPEW,strTraceBase + "copy relator 03: eiCR is null");
                                    }
                                }
                                strRelatorInfo = strRType;

                            }

                            // link to the parent

                            OPICMList ol = null;

                            if (strDirection.equals("U")) {
                                EntityItem[] aei = {eiParent};
                                ol = m_utility.linkEntities(_db, _prof, currentEI, aei, strRelatorInfo);
                            } else {
                                EntityItem[] aei = {currentEI};
                                ol = m_utility.linkEntities(_db, _prof, eiParent, aei, strRelatorInfo);
                            }

							if (ol !=null){ //MN32841099
								for (int i=0; i < ol.size(); i++) {
									Object obj = ol.getAt(i);
									if (obj instanceof ReturnRelatorKey) {
										ReturnRelatorKey rrk = (ReturnRelatorKey)obj;
										String strRType = rrk.getEntityType();
										EntityItem ei = new EntityItem(null, _prof, rrk.getEntityType(), rrk.getReturnID());
										if (eiCR != null) {
											m_utility.copyEntity(_db, _prof, eiCR, ei, m_strExcludeCopy, true);
										}
										EntityGroup egR = m_utility.getEntityGroup(strRType);
										if (egR ==null) {
											egR = new EntityGroup(null, _db, _prof, strRType, "Edit", false);
										}
										_prof = m_utility.setProfValOnEffOn(_db, _prof);
										ei = new EntityItem(egR, _prof, _db, rrk.getEntityType(), rrk.getReturnID());
										m_eiList.put(ei);
									}
								}
							}else {
                               	_db.debug(D.EBUG_SPEW,strTraceBase + "copyupdate OPICMList ol is null from link");
                           	}
                        }
                    }

                    int ilinkAllInEL = strAction.indexOf("linkAllInEL");
                    if (ilinkAllInEL > -1) {
                        String strLinkAllEI = strAction.substring(ilinkAllInEL);
                        int iEnd = strLinkAllEI.indexOf(";");
                        if (iEnd > -1) {
                            strLinkAllEI=strLinkAllEI.substring(0,iEnd);
                        }

                        int iU = strLinkAllEI.indexOf("_");
                        if (iU > -1) {
                            strLinkAllEI = strLinkAllEI.substring(iU+1);
                        }

                        if (strLinkAllEI != null) {
                            EntityGroup eg = m_ABReList.getEntityGroup(strLinkAllEI);
                            if (eg != null) {
                                for (int i=0; i < eg.getEntityItemCount(); i++) {
                                    EntityItem ei = eg.getEntityItem(i);
                                    EntityItem eic = null;
                                    if (eg.isRelator() || eg.isAssoc()) {
                                        eic = (EntityItem)ei.getDownLink(0);
                                        if (! eic.getEntityType().equals(strEntityType)) {
                                            eic = (EntityItem)ei.getUpLink(0);
                                        }
                                    } else {
                                        eic = ei;
                                    }
                                    if (eic.getEntityType().equals(strEntityType)) {
                                        EntityItem[] aei = {eic};
                                        m_utility.linkEntities(_db, _prof, eiParent, aei, strRelatorInfo);

                                        if (iSaveAct >= 0) {
                                            m_sbActivities.append("<br />" + m_utility.getActivities());
                                            m_utility.resetActivities();
                                        }
                                    }
                                }
                            } else {
                                _db.debug(D.EBUG_SPEW,strTraceBase + " linkAllInEL eg is null");
                            }
                        }
                    }

                    // save entities for later link to root
                    int iLinkRoot = strAction.indexOf("linkRoot");
                    if (iLinkRoot > -1) {
                        String strLinkRoot = strAction.substring(iLinkRoot);
                        int iEnd = strLinkRoot.indexOf(";");
                        if (iEnd > -1) {
                            strLinkRoot=strLinkRoot.substring(0,iEnd);
                        }
                        int iU = strLinkRoot.indexOf("_");
                        if (iU > -1) {
                            String strRelator = strLinkRoot.substring(iU+1);
                            EntityItem[] aei = {currentEI};
                            m_utility.linkEntities(_db, _prof, m_eiRoot, aei, strRelator);
                        }
                    }

                    int iSaveEI = strAction.indexOf("saveEI");
                    if (iSaveEI >=0 ) {
                        _db.debug(D.EBUG_SPEW,strTraceBase + " save entity");
                        m_savedEIList.put(currentEI);
                    }
                }
            }

            if (bRestart) {
                checkMissingData(_db, _prof, true);
            }
        } catch (SBRException ex) {
            if (vctReturnEntityKeys.size() > 0) {
                m_utility.link(_db, _prof, vctReturnEntityKeys);
            }

            throw ex;
        }
    }

    public EANList getSavedEIList() {
        return m_savedEIList;
    }

    public void queuedABR(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = " PDGActionItem queuedABR method ";

        if (m_eiPDG == null) {
            _db.debug(D.EBUG_SPEW,strTraceBase+"PDG entity is null");
            return;
        }
        //_db.debug(D.EBUG_SPEW, strTraceBase + " m_eiPDG: " + m_eiPDG.getKey());

        OPICMList attL = new OPICMList();
        attL.put(m_strABR, m_strABR + "=0020");

        _prof = m_utility.setProfValOnEffOn(_db, _prof);
        m_utility.updateAttribute(_db, _prof, m_eiPDG, attL);
    }

    protected abstract StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException;
    protected abstract void checkPDGAttribute(Database _db, Profile _prof, EntityItem _eiPDG) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException;
    protected abstract void resetVariables();
    public abstract byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException;
    public abstract void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException;
    protected abstract void checkDataAvailability(Database _db, Profile _prof, EntityItem _eiPDG) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException;
}

