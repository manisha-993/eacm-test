//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ReportActionItem.java,v $
// Revision 1.45  2009/05/19 13:16:31  wendy
// Support dereference for memory release
//
// Revision 1.44  2009/05/11 14:00:10  wendy
// Support turning off domain check for all actions
//
// Revision 1.43  2009/03/12 14:59:14  wendy
// Add methods for metaui access
//
// Revision 1.42  2005/08/10 16:14:24  tony
// improved catalog viewer functionality.
//
// Revision 1.41  2005/08/03 17:09:44  tony
// added datasource logic for catalog mod
//
// Revision 1.40  2005/03/24 20:38:54  steve
// added more parens to if statements in genDGSubmitString
//
// Revision 1.39  2005/03/24 20:32:37  steve
// added parens to *if* condition in genDGSubmitString
//
// Revision 1.38  2005/03/10 20:42:47  dave
// JTest daily ritual
//
// Revision 1.37  2005/01/18 21:46:51  dave
// more parm debug cleanup
//
// Revision 1.36  2004/07/15 17:28:59  joan
// add Single input
//
// Revision 1.35  2004/03/23 19:45:08  bala
// Change genDGSubmitString to use additional subscription
// properties
//
// Revision 1.34  2004/01/26 23:11:46  bala
// change getTitle to getLongDescription as Dave has suppressed short desc in object
//
// Revision 1.33  2003/11/24 21:33:30  bala
// add methods, vars to get and put ExtMail and IntMail for DG
//
// Revision 1.32  2003/10/30 00:43:34  dave
// fixing all the profile references
//
// Revision 1.31  2003/08/26 22:17:03  bala
// comment out RPTNAME from printing in printDGSubmitString
//
// Revision 1.30  2003/08/21 21:28:07  dave
// removing DGTYPE from ReportActionItem
//
// Revision 1.29  2003/08/21 21:06:16  bala
// add \n after reportname
//
// Revision 1.28  2003/08/21 00:37:33  bala
// tweaking a DGsubmit parameter
//
// Revision 1.27  2003/08/20 18:15:53  bala
// add return statement
//
// Revision 1.26  2003/08/20 18:00:28  bala
// fix typo
//
// Revision 1.25  2003/08/20 17:49:28  bala
// one more typo
//
// Revision 1.24  2003/08/20 17:47:44  bala
// fix compile errors
//
// Revision 1.23  2003/08/20 17:29:31  bala
// add the genDGSubmitString method
//
// Revision 1.22  2003/08/19 23:08:54  joan
// add code for tags
//
// Revision 1.21  2003/08/18 19:46:43  dave
// forgot the word boolean
//
// Revision 1.20  2003/08/18 19:37:23  dave
// adding BYPASSPOPUP attribute to the report action item
//
// Revision 1.19  2003/04/08 02:59:10  dave
// commit()
//
// Revision 1.18  2003/03/10 17:18:00  dave
// attempting to remove GBL7030 from the abstract Action Item
//
// Revision 1.17  2002/11/01 00:13:44  dave
// making the owner of the extract action item
// in the Report Action item.. the Extract Action Item
//
// Revision 1.16  2002/11/01 00:12:37  dave
// fixing a ReportActionItem leak
//
// Revision 1.15  2002/10/23 19:40:33  dave
// Added DGType (get & set) to the ReportActionItem
//
// Revision 1.14  2002/08/23 21:59:55  gregg
// updatePdhMeta method throws MiddlewareException
//
// Revision 1.13  2002/08/23 21:29:44  gregg
// updatePdhMeta(Database,boolean) method
//
// Revision 1.12  2002/08/14 21:25:42  dave
// fixes and general clean up
//
// Revision 1.11  2002/07/30 01:41:30  dave
// more clean up
//
// Revision 1.10  2002/05/17 22:07:26  joan
// add ExtractActionItem
//
// Revision 1.9  2002/05/17 19:57:18  joan
// fixing bugs
//
// Revision 1.8  2002/05/17 18:26:34  joan
// work on report
//
// Revision 1.7  2002/05/17 17:27:57  joan
// working on ReportActionItem
//
// Revision 1.6  2002/04/19 21:30:59  dave
// Added constructor to ReportActionItem
//
// Revision 1.5  2002/04/17 21:23:14  dave
// Syntax
//
// Revision 1.4  2002/04/17 21:13:57  dave
// missing method setReportItem
//
// Revision 1.3  2002/04/17 21:04:59  dave
// import  goof
//
// Revision 1.2  2002/04/17 20:57:08  dave
// import statements
//
// Revision 1.1  2002/04/17 20:17:06  dave
// new XMLAttribute and its MetaPartner
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 * ReportActionItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ReportActionItem extends EANActionItem {

    static final long serialVersionUID = 20011106L;
    private static final char YES = 'Y';

    private String m_strURL = null;
    private boolean m_bDGCapable = false;
    private boolean m_bMulti = false;
    private ExtractActionItem m_extractAI = null;
    /**
     * FIELD
     */
    protected boolean m_bPopUpContext = true;
    private String m_strCat1 = null;
    private String m_strCat2 = null;
    private String m_strCat3 = null;
    private String m_strCat4 = null;
    private String m_strCat5 = null;
    private String m_strCat6 = null;
    private String m_strCatVE = null;
    private String m_strExtMail = null;
    private String m_strIntMail = null;
    private String m_strSubscription = null;
    private String m_strNotifyOnError = null;
    
    public void dereference(){
    	super.dereference();
    	m_strURL = null;
    	if (m_extractAI!= null){
    		m_extractAI.setParent(null);
    		m_extractAI = null;
    	}
       
        m_strCat1 = null;
        m_strCat2 = null;
        m_strCat3 = null;
        m_strCat4 = null;
        m_strCat5 = null;
        m_strCat6 = null;
        m_strCatVE = null;
        m_strExtMail = null;
        m_strIntMail = null;
        m_strSubscription = null;
        m_strNotifyOnError = null;
    }
    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /*
    * Version info
    */
    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: ReportActionItem.java,v 1.45 2009/05/19 13:16:31 wendy Exp $";
    }

    /**
     * ReportActionItem
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ReportActionItem(EANMetaFoundation _mf, ReportActionItem _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
        setURL(_ai.getURL());
        setDGCapable(_ai.isDGCapable());
        setMulti(_ai.isMulti());
        setExtractActionItem(_ai.getExtractActionItem());
        m_bPopUpContext = _ai.m_bPopUpContext;
        setCat1(_ai.getCat1());
        setCat2(_ai.getCat2());
        setCat3(_ai.getCat3());
        setCat4(_ai.getCat4());
        setCat5(_ai.getCat5());
        setCat6(_ai.getCat6());
        setCatVE(_ai.getCatVE());
        setExtMail(_ai.getExtMail());
        setIntMail(_ai.getIntMail());
    }

    /**
     * This represents a Delete Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strActionItemKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public ReportActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_emf, _db, _prof, _strActionItemKey);

        try {
        	setDomainCheck(true);
        	
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs;
            Profile prof = getProfile();

            try {
                rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs!=null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {

                String strType = rdrs.getColumn(ii, 0);
                String strCode = rdrs.getColumn(ii, 1);
                String strValue = rdrs.getColumn(ii, 2);

                _db.debug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");

                // Collect the attributes for the Action

                if (strType.equals("TYPE") && strCode.equals("URL")) {
                    setURL(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("DGCapable")) {
                    setDGCapable((strValue.charAt(0) == YES) ? true : false);
                } else if (strType.equals("TYPE") && strCode.equals("Multi")) {
                    setMulti((strValue.charAt(0) == YES) ? true : false);
                } else if (strType.equals("TYPE") && strCode.equals("Extract")) {
                    setExtractActionItem(new ExtractActionItem(this, _db, _prof, strValue));
                } else if (strType.equals("TYPE") && strCode.equals("BYPASSPOPUP")) {
                    m_bPopUpContext = (strValue.charAt(0) == YES ? false : true);
                } else if (strType.equals("TYPE") && strCode.equals("CAT1")) {
                    setCat1(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("CAT2")) {
                    setCat2(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("CAT3")) {
                    setCat3(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("CAT4")) {
                    setCat4(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("CAT5")) {
                    setCat5(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("CAT6")) {
                    setCat6(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("CATVE")) {
                    setCatVE(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("EXTMAIL")) {
                    setExtMail(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("INTMAIL")) {
                    setIntMail(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SUBSCR_ENABLED")) {
                    setSubscription(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SUBSCR_NOTIFY_ON_ERROR")) {
                    setSubscrNotify(strValue);
                } else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
                    super.setAssociatedEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SingleInput")) {
                    setSingleInput(true);
				} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
					setDataSource(strValue);											//catalog enhancement
				} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
					setAdditionalParms(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("DomainCheck")) { // default is on, allow off
				    setDomainCheck(!strValue.equals("N")); //RQ0713072645
                } else {
                    _db.debug(D.EBUG_ERR, "*** ACTION ITEM ATTRIBUTE *** No home for this Action/Attribute" + strType + ":" + strCode + ":" + strValue);
                }
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("ReportActionItem:" + getKey() + ":desc:" + getLongDescription());
        strbResult.append(":purpose:" + getPurpose());
        return strbResult.toString();
    }

    /**
     * (non-Javadoc)
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return "Report";
    }

    /**
     * getURL
     *
     * @return
     *  @author David Bigelow
     */
    public String getURL() {
        return m_strURL;
    }

    /**
     * setURL
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setURL(String _str) {
        m_strURL = _str;
    }
    /*********
     * meta ui must update action
     */
    public void setActionClass(){
    	setActionClass("Report");
    }
    public void updateAction(String url){
    	setURL(url);
    }
    /**
     * isDGCapable
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isDGCapable() {
        return m_bDGCapable;
    }

    /**
     * setDGCapable
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setDGCapable(boolean _b) {
        m_bDGCapable = _b;
    }

    /**
     * isMulti
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isMulti() {
        return m_bMulti;
    }

    /**
     * setMulti
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setMulti(boolean _b) {
        m_bMulti = _b;
    }

    /**
     * getTitle
     *
     * @return
     *  @author David Bigelow
     */
    public String getTitle() {
        //return getShortDescription();
        return getLongDescription();
    }

    /**
     * getDesc
     *
     * @return
     *  @author David Bigelow
     */
    public String getDesc() {
        return getLongDescription();
    }

    /**
     * getHelp
     *
     * @return
     *  @author David Bigelow
     */
    public String getHelp() {
        return getHelp(getKey());
    }

    /**
     * setCat1
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setCat1(String _str) {
        m_strCat1 = _str;
    }

    /**
     * getCat1
     *
     * @return
     *  @author David Bigelow
     */
    public String getCat1() {
        return m_strCat1;
    }

    /**
     * setCat2
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setCat2(String _str) {
        m_strCat2 = _str;
    }

    /**
     * getCat2
     *
     * @return
     *  @author David Bigelow
     */
    public String getCat2() {
        return m_strCat2;
    }

    /**
     * setCat3
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setCat3(String _str) {
        m_strCat3 = _str;
    }

    /**
     * getCat3
     *
     * @return
     *  @author David Bigelow
     */
    public String getCat3() {
        return m_strCat3;
    }

    /**
     * setCat4
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setCat4(String _str) {
        m_strCat4 = _str;
    }

    /**
     * getCat4
     *
     * @return
     *  @author David Bigelow
     */
    public String getCat4() {
        return m_strCat4;
    }

    /**
     * setCat5
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setCat5(String _str) {
        m_strCat5 = _str;
    }

    /**
     * getCat5
     *
     * @return
     *  @author David Bigelow
     */
    public String getCat5() {
        return m_strCat5;
    }

    /**
     * setCat6
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setCat6(String _str) {
        m_strCat6 = _str;
    }

    /**
     * getCat6
     *
     * @return
     *  @author David Bigelow
     */
    public String getCat6() {
        return m_strCat6;
    }

    /**
     * setCatVE
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setCatVE(String _str) {
        m_strCatVE = _str;
    }

    /**
     * getCatVE
     *
     * @return
     *  @author David Bigelow
     */
    public String getCatVE() {
        return m_strCatVE;
    }

    /**
     * setExtMail
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setExtMail(String _str) {
        m_strExtMail = _str;
    }

    /**
     * getExtMail
     *
     * @return
     *  @author David Bigelow
     */
    public String getExtMail() {
        return m_strExtMail;
    }

    /**
     * setIntMail
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setIntMail(String _str) {
        m_strIntMail = _str;
    }

    /**
     * getIntMail
     *
     * @return
     *  @author David Bigelow
     */
    public String getIntMail() {
        return m_strIntMail;
    }

    /**
     * setSubscription
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setSubscription(String _str) {
        m_strSubscription = _str;
    }

    /**
     * getSubscription
     *
     * @return
     *  @author David Bigelow
     */
    public String getSubscription() {
        return m_strSubscription;
    }
    /**
     * setSubscrNotify
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setSubscrNotify(String _str) {
        m_strNotifyOnError = _str;
    }

    /**
     * getSubscrNotify
     *
     * @return
     *  @author David Bigelow
     */
    public String getSubscrNotify() {
        return m_strNotifyOnError;
    }

    /**
     * setExtractActionItem
     *
     * @param _eai
     *  @author David Bigelow
     */
    public void setExtractActionItem(ExtractActionItem _eai) {
        m_extractAI = _eai;
        if (m_extractAI != null) {
            m_extractAI.setParent(this);
        }
    }

    /**
     * isPopUpEnabled
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isPopUpEnabled() {
        return m_bPopUpContext;
    }

    /**
     * getExtractActionItem
     *
     * @return
     *  @author David Bigelow
     */
    public ExtractActionItem getExtractActionItem() {
        return m_extractAI;
    }
    /**
     * updatePdhMeta
     * @return true if successful, false if nothing to update or unsuccessful
     * @param _db
     * @param _bExpire
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        return true;
    }

    /**
     * genDGSubmitString
     *
     * @param _bPassFail
     * @return
     *  @author David Bigelow
     */
    public String genDGSubmitString(boolean _bPassFail) {
        StringBuffer strReturn = new StringBuffer("<!--DGSUBMITBEGIN" + NEW_LINE);
        strReturn.append("TASKSTATUS=" + ((_bPassFail) ? "TSPA" : "TSFAIL") + "" + NEW_LINE);
        strReturn.append("SUBSCRVE=" + getCatVE() + "" + NEW_LINE);
        strReturn.append(getCat1() != null ? "CAT1=" + getCat1() + "" + NEW_LINE : "");
        strReturn.append(getCat2() != null ? "CAT2=" + getCat2() + "" + NEW_LINE : "");
        strReturn.append(getCat3() != null ? "CAT3=" + getCat3() + "" + NEW_LINE : "");
        strReturn.append(getCat4() != null ? "CAT4=" + getCat4() + "" + NEW_LINE : "");
        strReturn.append(getCat5() != null ? "CAT5=" + getCat5() + "" + NEW_LINE : "");
        strReturn.append(getCat6() != null ? "CAT6=" + getCat6() + "" + NEW_LINE : "");
        strReturn.append(getExtMail() != null ? "EXTMAIL=" + getExtMail() + "" + NEW_LINE : "");
        strReturn.append(getIntMail() != null ? "INTMAIL=" + getIntMail() + "" + NEW_LINE : "");
        strReturn.append(getSubscription() != null ? "SUBSCR_ENABLED=" + getSubscription().toUpperCase() + "" + NEW_LINE : "SUBSCR_ENABLED=REQUIRED" + NEW_LINE);
        strReturn.append(getSubscrNotify() != null ? "SUBSCR_NOTIFY_ON_ERROR=" + getSubscrNotify().toUpperCase() + "" + NEW_LINE : "SUBSCR_NOTIFY_ON_ERROR=YES" + NEW_LINE);
        strReturn.append("DGSUBMITEND-->" + NEW_LINE);
        return strReturn.toString();
    }
}
