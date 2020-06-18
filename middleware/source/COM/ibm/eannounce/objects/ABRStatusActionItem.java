//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ABRStatusActionItem.java,v $
// Revision 1.7  2009/05/11 15:48:29  wendy
// Support dereference for memory release
//
// Revision 1.6  2005/08/10 16:14:23  tony
// improved catalog viewer functionality.
//
// Revision 1.5  2005/08/03 17:09:43  tony
// added datasource logic for catalog mod
//
// Revision 1.4  2005/02/09 22:13:43  dave
// more JTest Cleanup
//
// Revision 1.3  2005/01/18 21:33:09  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.2  2004/07/15 17:28:59  joan
// add Single input
//
// Revision 1.1  2004/06/21 15:30:25  joan
// initial load
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

// Temp made this non abstract because we need to compile in general before we whip up
// how actions are suppose to differentiate
// Actually we should be storing the metalink used to do this right
/**
 * ABRStatusActionItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ABRStatusActionItem extends EANActionItem {

    static final long serialVersionUID = 20011106L;

    private String m_strTargetEntity = null;
    protected void dereference(){
    	super.dereference();
    	m_strTargetEntity = null;
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

     /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: ABRStatusActionItem.java,v 1.7 2009/05/11 15:48:29 wendy Exp $";
    }

    /**
     * ABRStatusActionItem
     *
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ABRStatusActionItem(ABRStatusActionItem _ai) throws MiddlewareRequestException {
        super(_ai);
        setTargetEntity(_ai.getTargetEntity());
    }

    /**
     * ABRStatusActionItem
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public ABRStatusActionItem(EANMetaFoundation _mf, ABRStatusActionItem _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
        setTargetEntity(_ai.getTargetEntity());
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
    public ABRStatusActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_emf, _db, _prof, _strActionItemKey);

        try {
            // Lets go get the information pertinent to the Create Action Item
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs;
            Profile prof = getProfile();
            try {
                rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
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
                if (strType.equals("TYPE") && strCode.equals("Entity")) {
                    setTargetEntity(strValue);
                } else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
                    super.setAssociatedEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SingleInput")) {
                    setSingleInput(true);
				} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
					setDataSource(strValue);											//catalog enhancement
				} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
					setAdditionalParms(strValue);
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
        strbResult.append("ABRStatusActionItem:" + super.dump(_bBrief));
        return new String(strbResult);
    }

    /**
     * (non-Javadoc)
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return "ABRStatus";
    }

    /**
     * setTargetEntity
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setTargetEntity(String _str) {
        System.out.println("ABRStatusActionItem.setTargetEntity(): setting to" + _str);
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

}
