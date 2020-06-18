//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eRulesCollection.java,v $
// Revision 1.42  2008/01/31 21:05:17  wendy
// Cleanup RSA warnings
//
// Revision 1.41  2005/01/18 21:33:09  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.40  2004/12/01 22:02:57  gregg
// fixes
//
// Revision 1.39  2004/09/23 19:50:30  dave
// ok.. now we should be segt
//
// Revision 1.38  2004/09/23 19:40:57  dave
// call only once program
//
// Revision 1.37  2004/09/23 19:29:39  dave
// fix on collection key
//
// Revision 1.36  2004/09/23 18:40:24  dave
// missing return statement
//
// Revision 1.35  2004/09/23 18:31:53  dave
// some syntax
//
// Revision 1.34  2004/09/23 18:22:44  dave
// typo
//
// Revision 1.33  2004/09/23 18:13:53  dave
// some quick fixes
//
// Revision 1.32  2004/09/23 18:06:14  dave
// going for layout context in preperation for VAR, CTO, etc
//
// Revision 1.31  2004/09/09 16:06:18  gregg
// getRequiredRule(ProductDetail _pd)
//
// Revision 1.30  2004/09/07 21:01:04  dave
// suintechs
//
// Revision 1.29  2004/09/03 07:08:18  dave
// default to USA FC on genareaname
//
// Revision 1.28  2004/08/31 04:46:57  dave
// need to put in list again
//
// Revision 1.27  2004/08/31 04:34:45  dave
// fixed rulescollection timing
//
// Revision 1.26  2004/08/30 19:19:15  dave
// syntax
//
// Revision 1.25  2004/08/30 19:13:01  dave
// bugs
//
// Revision 1.24  2004/08/30 19:04:29  dave
// rollup rule
//
// Revision 1.23  2004/08/30 18:40:55  dave
// adding rollup rule
//
// Revision 1.22  2004/08/27 20:59:00  dave
// adding Valfrom to all objects
//
// Revision 1.21  2004/08/27 19:40:28  dave
// found a bug.. in member reference
//
// Revision 1.20  2004/08/27 16:53:27  dave
// more statics
//
// Revision 1.19  2004/08/27 16:41:53  dave
// fixed up some statics
//
// Revision 1.18  2004/08/27 16:34:53  dave
// changes
//
// Revision 1.17  2004/08/26 20:37:48  dave
// made isRequired changes
//
// Revision 1.16  2004/08/26 20:09:56  dave
// whoops
//
// Revision 1.15  2004/08/26 19:54:16  dave
// new Required Concat
//
// Revision 1.14  2004/08/26 05:00:14  dave
// syn tracks
//
// Revision 1.13  2004/08/26 04:50:46  dave
// rework on rules
//
// Revision 1.12  2004/08/23 22:09:51  dave
// more cleanup
//
// Revision 1.11  2004/08/23 21:47:54  dave
// syntax
//
// Revision 1.10  2004/08/23 21:39:36  dave
// syntax
//
// Revision 1.9  2004/08/23 21:29:43  dave
// adding required rules
//
// Revision 1.8  2004/08/23 19:42:47  dave
// syntax
//
// Revision 1.7  2004/08/23 18:02:38  dave
// syntax issues
//
// Revision 1.6  2004/08/23 17:50:00  dave
// new Concat basic rule
//
// Revision 1.5  2004/08/23 16:47:35  dave
// more import fixing
//
// Revision 1.4  2004/08/23 16:40:41  dave
// fixing imports
//
// Revision 1.3  2004/08/23 16:20:41  dave
// new import statements
//
// Revision 1.2  2004/08/23 16:18:14  dave
// Move to new package
//
// Revision 1.1  2004/08/23 16:15:20  dave
// moving eObjects to hula subdirectory
//
// Revision 1.23  2004/08/19 00:13:13  dave
// commit null pointer
//
// Revision 1.22  2004/08/18 23:48:41  dave
// change
//
// Revision 1.21  2004/08/18 23:21:49  dave
// syntax
//
// Revision 1.20  2004/08/18 23:13:37  dave
// attributename
//
// Revision 1.19  2004/08/18 23:06:34  dave
// more fixes
//
// Revision 1.18  2004/08/18 22:52:07  dave
// more changes
//
// Revision 1.17  2004/08/18 21:21:18  dave
// syntax
//
// Revision 1.16  2004/08/18 21:09:07  dave
// new eLayoutGroup, eLayoutItem
//
// Revision 1.15  2004/08/18 17:31:49  dave
// syntax
//
// Revision 1.14  2004/08/18 17:15:51  dave
// refining ECCM group / item descriptions
//
// Revision 1.13  2004/08/17 21:19:55  dave
// syntax fix
//
// Revision 1.12  2004/08/17 21:14:00  dave
// SP fixes
//
// Revision 1.11  2004/08/16 22:39:58  dave
// sytax fixes
//
// Revision 1.10  2004/08/16 22:13:14  dave
// more test stuff
//
// Revision 1.9  2004/08/16 22:07:30  dave
// syntax errs
//
// Revision 1.8  2004/08/16 21:59:53  dave
// new eBasicRule
//
// Revision 1.7  2004/08/16 21:19:33  dave
// alligning variables
//
// Revision 1.6  2004/08/16 21:06:12  dave
// 0 needs to be -1
//
// Revision 1.5  2004/08/16 20:26:59  dave
// more change
//
// Revision 1.4  2004/08/16 20:19:53  dave
// alittle more syntax
//
// Revision 1.3  2004/08/16 20:13:04  dave
// adding more columns to 9992
//
// Revision 1.2  2004/08/16 20:08:08  dave
// syntax
//
// Revision 1.1  2004/08/16 19:59:38  dave
// new eRulesCollection off from ODS
//

package COM.ibm.eannounce.hula;

import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.eannounce.objects.*;




/*
* Manages a running set of rulesets for a given project / Country
*/
public class eRulesCollection extends EANMetaFoundation {

  static final long serialVersionUID = 20011106L;

    public static final String CONTEXT_MTM  = "MTM";
    public static final String CONTEXT_VAR  = "VAR";
    public static final String CONTEXT_CTO  = "CTO";
    public static final String CONTEXT_SBB  = "SBB";
    public static final String CONTEXT_MODCMP  = "MODCMP";
    public static final String CONTEXT_NONE  = "NONE";


  private static EANList c_elRules = new EANList();


  private int m_iProjectID = 0;
  private String m_strGenAreaNameFC = null;
  private String m_strCountryCode = null;
  private String m_strContext = null;


    //
    // For our proof of concept, we have to write rules here...
    // We have positional.. and visible.. and concatenation
    //
    // These will hold a collection of eRules
    //
    private EANList m_elLayoutGroup = new EANList();

    private EANList m_elConcatRules = new EANList();
    private EANList m_elRequiredRules = new EANList();
    private EANList m_elRollupRules= new EANList();
    private Hashtable m_hshRevLook = new Hashtable();

    //
    //  Static routines
    //

  public static eRulesCollection getRulesCollection(Database _db, Profile _prof, int _iProjectID, String _strGenAreaNameFC, String _strCountryCode) {
        return getRulesCollection(_db,_prof, _iProjectID,_strGenAreaNameFC, _strCountryCode, eRulesCollection.CONTEXT_MTM);
    }

  public static eRulesCollection getRulesCollection(Database _db, Profile _prof, int _iProjectID, String _strGenAreaNameFC, String _strCountryCode, String _strContext) {
        if (_strGenAreaNameFC == null) {
            _strGenAreaNameFC = "11014";
        }
        String strKey = _iProjectID + "." + _strGenAreaNameFC + "." + _strCountryCode + "." + _strContext;
        if (c_elRules.containsKey(strKey)) {
            return (eRulesCollection)c_elRules.get(strKey);
        }
        eRulesCollection er = null;
        try {
            er = new eRulesCollection(_db, _prof, _iProjectID, _strGenAreaNameFC, _strCountryCode, _strContext);
        } catch (Exception x) {
            x.printStackTrace();
        }

        c_elRules.put(er);

        return er;
    }

  public static eRulesCollection getRulesCollection(Database _db, Profile _prof,eProduct _prd) {

        int iProjectID = _prd.getIntVal(eProduct.PROJECTID);
        String strContext = _prd.getLayoutContext();

        String strGenAreaNameFC = _prd.getStringVal(eProduct.GENAREANAME_FC);
        if (strGenAreaNameFC == null) {
            strGenAreaNameFC = "11014";
        }
        String strCountryCode = _prd.getStringVal(eProduct.COUNTRYCODE);
        String strKey = iProjectID + "." + strGenAreaNameFC + "." + strCountryCode + "." + strContext;
        if (c_elRules.containsKey(strKey)) {
            return (eRulesCollection)c_elRules.get(strKey);
        }
        eRulesCollection er = null;
        try {
            er = new eRulesCollection(_db, _prof, iProjectID, strGenAreaNameFC, strCountryCode, strContext);
        } catch (Exception x) {
            x.printStackTrace();
        }

        c_elRules.put(er);
        return er;
    }

  public static void resetRulesCollection() {
        c_elRules = new EANList();
    }

  /*
  * Version info
  */
  public String getVersion() {
    return new String("$Id: eRulesCollection.java,v 1.42 2008/01/31 21:05:17 wendy Exp $");
  }

  private eRulesCollection(Database _db, Profile _prof, int _iProjectID, String _strGenAreaNameFC, String _strCountryCode, String _strContext) throws SQLException, MiddlewareRequestException, MiddlewareException {

    super(null, _prof, _iProjectID + "." + _strGenAreaNameFC + "." +  _strCountryCode + "." + _strContext);
    m_iProjectID = _iProjectID;
    m_strGenAreaNameFC = _strGenAreaNameFC;
    m_strContext = _strContext;

    try {

            // Lets get all the rule LayoutItems here...
      ReturnStatus returnStatus = new ReturnStatus(-1);
      ResultSet rs = null;
      ReturnDataResultSet rdrs = null;
      //String strEnterprise = getProfile().getEnterprise();
      //String strRoleCode = getProfile().getRoleCode();
      int iNLSID = getNLSID();

      rs = _db.callGBL9992(returnStatus, m_iProjectID, m_strGenAreaNameFC, iNLSID,m_strContext);
      rdrs = new ReturnDataResultSet(rs);
      rs.close();
      rs = null;
      _db.commit();
      _db.freeStatement();
      _db.isPending();

      // We need to get the description of the link group here...
      for (int i = 0; i < rdrs.size();i++) {
                int y=0;

            int iGroupSeq = rdrs.getColumnInt(i,y++);
            int iItemSeq = rdrs.getColumnInt(i,y++);

                String strGroupLayoutID = rdrs.getColumn(i,y++);
              String strGroupDesc = rdrs.getColumn(i,y++);
        String strGroupVisible = rdrs.getColumn(i,y++);
        String strGroupVisibleFc = rdrs.getColumn(i,y++);
        String strGroupValFrom = rdrs.getColumn(i,y++);

                String strItemLayoutID = rdrs.getColumn(i,y++);
              String strItemDesc = rdrs.getColumn(i,y++);
        String strItemVisible = rdrs.getColumn(i,y++);
        String strItemVisibleFc = rdrs.getColumn(i,y++);
        String strItemValFrom = rdrs.getColumn(i,y++);

        _db.debug(D.EBUG_SPEW, "gbl9992 answers:" +
              iGroupSeq + ":" +
        iItemSeq + ":" +
        strGroupLayoutID + ":" +
        strGroupDesc + ":" +
        strGroupVisible + ":" +
        strGroupVisibleFc + ":" +
        strGroupValFrom + ":" +
        strItemLayoutID + ":" +
        strItemDesc + ":" +
        strItemVisible + ":" +
        strItemVisible + ":" +
        strItemVisibleFc + ":" +
        strItemValFrom)
        ;

        //
        // Lets String Tokenize
        //
        StringTokenizer st = new StringTokenizer(strItemLayoutID,".");
        String strHeritage = st.nextToken();
        String strEntityType = st.nextToken();
        String strAttributeCode = st.nextToken();

                // Lets get the group and Item Descriptions tucked away..
                eLayoutGroup lg = null;
                lg = getLayoutGroup(strGroupLayoutID);
                if (lg == null) {
                    //
                    // Come back and address bmulti
                    //
                    lg = new eLayoutGroup(this, strGroupLayoutID, strGroupDesc, iGroupSeq, strGroupVisibleFc.equals("YES"), false, strGroupValFrom);
                    putLayoutGroup(lg);
                }

                eLayoutItem li = null;
                li = lg.getLayoutItem(strItemLayoutID);
                if (li == null) {
                    li = new eLayoutItem(lg, strEntityType, strHeritage, strAttributeCode, strItemDesc, iItemSeq, strItemVisibleFc.equals("YES"), strItemValFrom);
                    lg.putLayoutItem(li);
                }

                //
                // Lets do a reverse lookup
                //
                m_hshRevLook.put(strItemLayoutID, strGroupLayoutID);

            }

      rs = _db.callGBL9991(returnStatus, m_iProjectID, m_strGenAreaNameFC, iNLSID, m_strContext);
      rdrs = new ReturnDataResultSet(rs);
      rs.close();
      rs = null;
      _db.commit();
      _db.freeStatement();
      _db.isPending();

      // We need to get the description of the link group here...
      for (int i = 0; i < rdrs.size();i++) {
                int y=0;

            String strRuleType = rdrs.getColumn(i,y++);
              String strRuleTypeFC = rdrs.getColumn(i,y++);
        String strRuleDesc = rdrs.getColumn(i,y++);
        String strRuleID = rdrs.getColumn(i,y++);
        String strRuleTest = rdrs.getColumn(i,y++);
        String strRulePass = rdrs.getColumn(i,y++);
        String strRuleFail = rdrs.getColumn(i,y++);
            String strRuleTrigger = rdrs.getColumn(i,y++);
              String strRuleTriggerFC = rdrs.getColumn(i,y++);
        String strValFrom = rdrs.getColumn(i,y++);

       _db.debug(D.EBUG_SPEW, "gbl9991 answers:" +
        strRuleType + ":" +
        strRuleTypeFC + ":" +
        strRuleDesc + ":" +
        strRuleID + ":" +
        strRuleTest + ":" +
        strRulePass + ":" +
        strRuleFail + ":" +
        strRuleTrigger + ":" +
        strRuleTriggerFC + ":" +
        strValFrom)
                ;

        //
        // Lets String Tokenize
        //
        StringTokenizer st = new StringTokenizer(strRuleID,".");
        String strHeritage = st.nextToken();
        String strEntityType = st.nextToken();
        String strAttributeCode = st.nextToken();


                if (strRuleTypeFC.equals("RT02")) {
                    eConcatRule ecr = new eConcatRule(this,strEntityType, strHeritage, strAttributeCode ,strRuleDesc, strRuleTest, strRulePass, strRuleFail, strValFrom,strRuleTriggerFC);
                    m_elConcatRules.put(ecr);
              } else if (strRuleTypeFC.equals("RT04")) {
                  eRequiredRule rr = new eRequiredRule(this,strEntityType, strHeritage, strAttributeCode ,strRuleDesc, strRuleTest, strRulePass, strRuleFail, strValFrom,strRuleTriggerFC);
                  m_elRequiredRules.put(rr);
              } else if (strRuleTypeFC.equals("RT03")) {
                  eRollupRule rr = new eRollupRule(this,strEntityType, strHeritage, strAttributeCode ,strRuleDesc, strRuleTest, strRulePass, strRuleFail, strValFrom,strRuleTriggerFC);
                  m_elRollupRules.put(rr);
                }
            }

            c_elRules.put(this);

    } finally {
      _db.freeStatement();
      _db.isPending();
    }
  }

    public String toString() {
        return m_strCountryCode + ":" + m_iProjectID + ":" + m_strGenAreaNameFC;
    }

    public String dump(boolean _bBrief) {
    StringBuffer strbResult = new StringBuffer();
    strbResult.append(getKey());
    return new String(strbResult);

  }


    public int getGroupSeq(String _strChildType, String _strHeritage, String _strAttributeCode) {
        String striKey = _strHeritage + "." + _strChildType + "." + _strAttributeCode;
        String strKey = (String)m_hshRevLook.get(striKey);
        if (strKey == null) {
            return 9999;
        }
        eLayoutGroup lg = getLayoutGroup(strKey);
        if (lg == null) {
            return 9999;
        }
        return lg.getSeq();
    }

    public int getItemSeq(String _strChildType, String _strHeritage, String _strAttributeCode) {
        String striKey = _strHeritage + "." + _strChildType + "." + _strAttributeCode;
        String strKey = (String)m_hshRevLook.get(striKey);
        if (strKey == null) {
            return 9999;
        }
        eLayoutGroup lg = getLayoutGroup(strKey);
        if (lg == null) {
            return 9999;
        }
        eLayoutItem li = lg.getLayoutItem(striKey);
        if (li == null) {
            return 9999;
        }
        return li.getSeq();
    }

    public boolean isGroupVisible(String _strChildType, String _strHeritage, String _strAttributeCode) {
        String striKey = _strHeritage + "." + _strChildType + "." + _strAttributeCode;
        String strKey = (String)m_hshRevLook.get(striKey);
        if (strKey == null) {
            return false;
        }
        eLayoutGroup lg = getLayoutGroup(strKey);
        if (lg == null) {
            return false;
        }
        return lg.isVisible();
    }

    public boolean isItemVisible(String _strChildType, String _strHeritage, String _strAttributeCode) {
        String striKey = _strHeritage + "." + _strChildType + "." + _strAttributeCode;
        String strKey = (String)m_hshRevLook.get(striKey);
        if (strKey == null) {
            return false;
        }
        eLayoutGroup lg = getLayoutGroup(strKey);
        if (lg == null) {
            return false;
        }
        eLayoutItem li = lg.getLayoutItem(striKey);
        if (li == null) {
            return false;
        }
        return li.isVisible();
    }

    public int getLayoutGroupCount() {
        return m_elLayoutGroup.size();
    }

    public eLayoutGroup getLayoutGroup(int _i) {
        return (eLayoutGroup)m_elLayoutGroup.getAt(_i);
    }

    public eLayoutGroup getLayoutGroup(String _str) {
        return (eLayoutGroup)m_elLayoutGroup.get(_str);
    }

    public void putLayoutGroup (eLayoutGroup _lg) {
        if (!m_elLayoutGroup.containsKey(_lg.getKey())) {
            m_elLayoutGroup.put(_lg);
        }
    }

    //
    // Working with Concat Rules
    //

    public int getConcatRuleCount() {
        return m_elConcatRules.size();
    }

    public eConcatRule getConcatRule(int _i) {
        return (eConcatRule)m_elConcatRules.getAt(_i);
    }

    public eConcatRule getConcatRule(eProductDetail _pd) {
        String strChildType = _pd.getStringVal(eProductDetail.ENTITYTYPE);
        String strHeritage = _pd.getStringVal(eProductDetail.HERITAGE);
        String strAttributeCode = _pd.getStringVal(eProductDetail.ATTRIBUTECODE);
        String strKey = strHeritage + "." + strChildType + "." + strAttributeCode;
        return (eConcatRule)m_elConcatRules.get(strKey);
    }

    public void putConcatRule (eConcatRule _cr) {
        if (!m_elConcatRules.containsKey(_cr.getKey())) {
            m_elConcatRules.put(_cr);
        }
    }

    //
    // Working with Required Rules
    //
    public int getRequiredRuleCount() {
        return m_elRequiredRules.size();
    }

    public eRequiredRule getRequiredRule(int _i) {
        return (eRequiredRule)m_elRequiredRules.getAt(_i);
    }

    public boolean isRequired(eLayoutItem _li) {
        return m_elRequiredRules.containsKey(_li.getKey());
    }

    public boolean isRequired(String _strHeritage, String _strChildType, String _strAttributeCode) {
        String strKey = _strHeritage + "." + _strChildType + "." + _strAttributeCode;
        return m_elRequiredRules.containsKey(strKey);
    }

    public void putRequiredRule (eRequiredRule _rr) {
        if (!m_elRequiredRules.containsKey(_rr.getKey())) {
            m_elRequiredRules.put(_rr);
        }
    }

    public eRequiredRule getRequiredRule(eProductDetail _pd) {
        String strChildType = _pd.getStringVal(eProductDetail.ENTITYTYPE);
        String strHeritage = _pd.getStringVal(eProductDetail.HERITAGE);
        String strAttributeCode = _pd.getStringVal(eProductDetail.ATTRIBUTECODE);
        String strKey = strHeritage + "." + strChildType + "." + strAttributeCode;
        return (eRequiredRule)m_elRequiredRules.get(strKey);
    }

    //
    // Working with RoleUp Rules
    //
    public int getRollupRuleCount() {
        return m_elRollupRules.size();
    }

    public eRollupRule getRollupRule(int _i) {
        return (eRollupRule)m_elRollupRules.getAt(_i);
    }

    public eRollupRule getRollupRule(eProductDetail _pd) {
        String strChildType = _pd.getStringVal(eProductDetail.ENTITYTYPE);
        String strHeritage = _pd.getStringVal(eProductDetail.HERITAGE);
        String strAttributeCode = _pd.getStringVal(eProductDetail.ATTRIBUTECODE);
        String strKey = strHeritage + "." + strChildType + "." + strAttributeCode;
        return (eRollupRule)m_elRollupRules.get(strKey);
    }

    public void putRollupRule (eRollupRule _rr) {
        if (!m_elRollupRules.containsKey(_rr.getKey())) {
            m_elRollupRules.put(_rr);
        }
    }

}
