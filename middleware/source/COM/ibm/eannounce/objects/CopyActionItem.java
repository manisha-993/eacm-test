//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: CopyActionItem.java,v $
// Revision 1.12  2009/05/19 12:30:19  wendy
// Support dereference for memory release
//
// Revision 1.11  2009/05/11 13:50:08  wendy
// Support turning off domain check for all actions
//
// Revision 1.10  2008/10/09 14:37:33  wendy
// Check for rs != null before close()
//
// Revision 1.9  2007/08/03 11:25:44  wendy
// RQ0713072645-1 Make actions sensitive to the PROFILE's PDHDOMAINs
//
// Revision 1.8  2006/02/20 21:39:45  joan
// clean up System.out.println
//
// Revision 1.7  2005/08/10 16:14:23  tony
// improved catalog viewer functionality.
//
// Revision 1.6  2005/08/03 17:09:43  tony
// added datasource logic for catalog mod
//
// Revision 1.5  2005/02/10 01:22:25  dave
// JTest fixes
//
// Revision 1.4  2005/01/18 21:33:09  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.3  2004/07/15 17:28:59  joan
// add Single input
//
// Revision 1.2  2004/06/15 20:48:25  joan
// fix compile
//
// Revision 1.1  2004/06/15 20:37:31  joan
// initial load
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

// Temp made this non abstract because we need to compile in general before we whip up
// how actions are suppose to differentiate
// Actually we should be storing the metalink used to do this right
/**
 * CopyActionItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CopyActionItem extends EANActionItem {

    static final long serialVersionUID = 20011106L;

    private String m_strFormKey = null;
    private int m_iDefaultIndex = 0;
    private boolean m_bDefaultIndex = false;
    private String m_strTargetEntity = null;
    public void dereference(){
    	super.dereference();
    	m_strTargetEntity = null;
    	m_strFormKey = null;
    }
    /**
     * FIELD
     */
    protected boolean m_bUI = true;
    /**
     * FIELD
     */
    protected int m_iNumOfCopy = 0;

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
        return "$Id: CopyActionItem.java,v 1.12 2009/05/19 12:30:19 wendy Exp $";
    }

    /*
    * Instantiate a new ActionItem based upon a dereferenced version of the Existing One
    */
    /**
     * CopyActionItem
     *
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public CopyActionItem(CopyActionItem _ai) throws MiddlewareRequestException {
        super(_ai);
        setFormKey(_ai.getFormKey());
        setDefaultIndex(_ai.getDefaultIndex());
        setHasDefaultIndex(_ai.hasDefaultIndex());
        setTargetEntity(_ai.getTargetEntity());
        m_bUI = _ai.m_bUI;
        m_iNumOfCopy = _ai.m_iNumOfCopy;
    }

    /**
     * CopyActionItem
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public CopyActionItem(EANMetaFoundation _mf, CopyActionItem _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
        setFormKey(_ai.getFormKey());
        setDefaultIndex(_ai.getDefaultIndex());
        setHasDefaultIndex(_ai.hasDefaultIndex());
        setTargetEntity(_ai.getTargetEntity());
        m_bUI = _ai.m_bUI;
        m_iNumOfCopy = _ai.m_iNumOfCopy;
    }

    /**
     * This represents an Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key*
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strActionItemKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public CopyActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_emf, _db, _prof, _strActionItemKey);

        // Lets go get the information pertinent to the Create Action Item

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
            	if (rs != null){
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

                // Collect the attributes
                if (strType.equals("TYPE") && strCode.equals("Form")) {
                    setFormKey(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("Entity")) {
                    setTargetEntity(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("DefaultValueIndex")) {
                    setDefaultIndex(Integer.valueOf(strValue).intValue());
                    setHasDefaultIndex(true);
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
        strbResult.append("CopyActionItem:" + super.dump(_bBrief));
        strbResult.append(":isFormCapable:" + isFormCapable());
        strbResult.append(":FormKey:" + getFormKey());
        strbResult.append(":DefaultIndex:" + hasDefaultIndex());
        strbResult.append(":" + getDefaultIndex());

        return new String(strbResult);
    }

    /**
     * (non-Javadoc)
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return "Copy";
    }

    /**
     * getFormKey
     *
     * @return
     *  @author David Bigelow
     */
    public String getFormKey() {
        return m_strFormKey;
    }

    /**
     * setFormKey
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setFormKey(String _str) {
        m_strFormKey = _str;
    }

    /**
     * isFormCapable
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isFormCapable() {
        return m_strFormKey != null;
    }

    /**
     * hasDefaultIndex
     *
     * @return
     *  @author David Bigelow
     */
    public boolean hasDefaultIndex() {
        return m_bDefaultIndex;
    }

    /**
     * setHasDefaultIndex
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setHasDefaultIndex(boolean _b) {
        m_bDefaultIndex = _b;
    }

    /**
     * getDefaultIndex
     *
     * @return
     *  @author David Bigelow
     */
    public int getDefaultIndex() {
        return m_iDefaultIndex;
    }

    /**
     * setDefaultIndex
     *
     * @param _i
     *  @author David Bigelow
     */
    protected void setDefaultIndex(int _i) {
        m_iDefaultIndex = _i;
    }

    /**
     * getNumOfCopy
     *
     * @return
     *  @author David Bigelow
     */
    public int getNumOfCopy() {
        return m_iNumOfCopy;
    }

    /**
     * setNumOfCopy
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setNumOfCopy(int _i) {
        m_iNumOfCopy = _i;
    }

    /**
     * setTargetEntity
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setTargetEntity(String _str) {
        D.ebug(D.EBUG_SPEW,"CopyActionItem.setTargetEntity(): setting to " + _str);
        m_strTargetEntity = _str;
    }

    /**
     * getTargetEntity
     *
     * @return
     *  @author David Bigelow
     */
    public String getTargetEntity() {
        return m_strTargetEntity;
    }


    /**
     * (non-Javadoc)
     * updatePdhMeta
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#updatePdhMeta(COM.ibm.opicmpdh.middleware.Database, boolean)
     */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        return true;
    }

    /**
     * disableUIAfterCache
     *
     *  @author David Bigelow
     */
    protected void disableUIAfterCache() {
        m_bUI = false;
    }

    /**
     * enableUIAfterCache
     *
     *  @author David Bigelow
     */
    protected void enableUIAfterCache() {
        m_bUI = true;
    }

    /**
     * isUIAfterCacheEnabled
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isUIAfterCacheEnabled() {
        return m_bUI;
    }

}
