//
// Copyright (c) 2001,2010 International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Attribute.java,v $
// Revision 1.34  2010/11/01 18:22:57  wendy
// Post ABRs after Status attributes
//
// Revision 1.33  2010/07/12 21:02:44  wendy
// BH SR87, SR655 - extended combounique rule
//
// Revision 1.32  2008/01/31 21:42:00  wendy
// Cleanup RSA warnings
//
// Revision 1.31  2006/02/06 20:55:52  gregg
// m_strComboUniqueGrouping
//
// Revision 1.30  2005/03/11 22:42:54  dave
// removing some auto genned stuff
//
// Revision 1.29  2005/01/27 04:02:36  dave
// removing automated readObject from Jtest
//
// Revision 1.28  2005/01/27 02:42:20  dave
// Jtest format cleanup
//
// Revision 1.27  2004/08/03 19:25:27  gregg
// compile fix
//
// Revision 1.26  2004/08/03 18:24:57  gregg
// isComboUniqueOptionalRequiredAttribute
//
// Revision 1.25  2004/08/02 21:03:58  gregg
// remove m_strComboOptAttributeCode, etc... reuse m_strComboAttributeCode
//
// Revision 1.24  2004/08/02 20:59:05  gregg
// more combo unique optional
//
// Revision 1.23  2004/08/02 20:51:41  gregg
// m_bComboUniqueOptional
//
// Revision 1.22  2003/07/02 20:04:25  dave
// more region removal
//
// Revision 1.21  2003/07/02 19:41:07  dave
// making changes for flag -> text combo check
//
// Revision 1.20  2002/01/18 20:30:20  dave
// implementing defered status flag update for 1.1
//
// Revision 1.19  2001/09/19 15:32:32  roger
// Formatting
//
// Revision 1.18  2001/09/19 15:24:58  roger
// Remove constructors with Profile as parm
//
// Revision 1.17  2001/09/17 22:28:13  roger
// Undo more
//
// Revision 1.16  2001/09/17 22:17:48  roger
// Remove protected from constructors
//
// Revision 1.15  2001/09/17 22:07:32  roger
// Needed import
//
// Revision 1.14  2001/09/17 22:00:00  roger
// Open up constructors and members
//
// Revision 1.13  2001/09/17 21:48:34  roger
// New accessors + mutators
//
// Revision 1.12  2001/09/17 20:32:10  roger
// Remove final from methods
//
// Revision 1.11  2001/09/17 20:24:27  roger
// Use Profile for values of Enterprise, OPENID, TranID, etc
//
// Revision 1.10  2001/08/22 16:53:05  roger
// Removed author RM
//
// Revision 1.9  2001/06/08 02:38:35  dave
// Country Part Number stuff V2
//
// Revision 1.8  2001/06/07 06:14:45  dave
// Phase II unique software
//
// Revision 1.7  2001/06/07 05:30:41  dave
// first pass at general uniqueness logic for attributes
//
// Revision 1.6  2001/03/26 16:46:03  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:11  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:26  roger
// Added Log keyword
//


package COM.ibm.opicmpdh.objects;


import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;
import COM.ibm.opicmpdh.middleware.ReturnStatus;


/**
 * Attribute
 * @version @date
 * @see Blob
 * @see ControlBlock
 * @see EntitiesAndRelator
 * @see Entity
 * @see Flag
 * @see LongText
 * @see MetaEntity
 * @see MultipleFlag
 * @see Relator
 * @see SingleFlag
 * @see Text
 */
public abstract class Attribute implements Serializable, Cloneable {

    // Class constants
	// need unique delimiter for attribute values and descriptions
	public static final String UNIQUE_DELIMITER = MiddlewareServerProperties.getMetaDataDelimiter();
	// extended support for combounique BH SR87, SR655

    /**
     * @serial
     */
    static final long serialVersionUID = 10L;

    // Class variables
    // Instance variables
    /**
     * FIELD
     */
    public String m_strEnterprise = null;
    /**
     * FIELD
     */
    public String m_strEntityType = null;
    /**
     * FIELD
     */
    public int m_iEntityID = 0;
    /**
     * FIELD
     */
    public String m_strAttributeCode = null;
    /**
     * FIELD
     */
    public String m_strAttributeValue = null;
    /**
     * FIELD
     */
    public int m_iNLSID = 0;
    /**
     * FIELD
     */
    public ControlBlock m_cbControlBlock = null;
    /**
     * FIELD
     */
    public String m_strShortDescription = null;
    /**
     * FIELD
     */
    public String m_strLongDescription = null;
    /**
     * FIELD
     */
    public Object m_objReference = null;

    /**
     * FIELD
     */
    public boolean m_bUnique = false;
    /**
     * FIELD
     */
    public String m_strUniqueType = "";
    /**
     * FIELD
     */
    public String m_strUniqueClass = "";
    /**
     * FIELD
     */
    public boolean m_bComboUnique = false;
    /**
     * FIELD
     */
    public String m_strComboAttributeCode = "";
    /**
     * FIELD
     */
    public String m_strComboAttributeValue = "";
    /**
     * FIELD
     */
    public String m_strComboAttributeDesc = "";
    /**
     * FIELD
     */
    public boolean m_bComboUniqueOptRequiredAtt = false;
    /**
     * FIELD
     */
    public boolean m_bComboUniqueOptional = false;

    private boolean m_bDeferred = false; // S and A type attrs need to be posted last
    private boolean m_bDeferredLast = false; // A type attrs must be posted last after S type

    public String m_strComboUniqueGrouping = null;

    /**
     * Main method which performs a simple test of this class
     *
     * @param args
     */
    public static void main(String args[]) {
    }

    /**
     * Construct a default <code>Attribute</code>
     */
    public Attribute() {
        this("", "", 0, "", "", 0, new ControlBlock());
    }

    /**
     * Construct an <code>Attribute</code>
     *
     * @param strEnterprise
     * @param strEntityType
     * @param iEntityID
     * @param strAttributeCode
     * @param strAttributeValue
     * @param iNLSID
     */
    public Attribute(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID) {
        m_strEnterprise = strEnterprise;
        m_strEntityType = strEntityType;
        m_iEntityID = iEntityID;
        m_strAttributeCode = strAttributeCode;
        m_strAttributeValue = strAttributeValue;
        m_iNLSID = iNLSID;
    }

    /**
     * Construct an <code>Attribute</code>
     *
     * @param strEnterprise
     * @param strEntityType
     * @param iEntityID
     * @param strAttributeCode
     * @param strAttributeValue
     * @param iNLSID
     * @param cbControlBlock
     */
    public Attribute(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID, ControlBlock cbControlBlock) {
        m_strEnterprise = strEnterprise;
        m_strEntityType = strEntityType;
        m_iEntityID = iEntityID;
        m_strAttributeCode = strAttributeCode;
        m_strAttributeValue = strAttributeValue;
        m_iNLSID = iNLSID;
        m_cbControlBlock = cbControlBlock;
    }

    /**
     * Construct an <code>Attribute</code>
     *
     * @param strEnterprise
     * @param strEntityType
     * @param iEntityID
     * @param strAttributeCode
     * @param strAttributeValue
     * @param iNLSID
     * @param strValFrom
     * @param strValTo
     * @param strEffFrom
     * @param strEffTo
     * @param openID
     */
    public Attribute(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID, String strValFrom, String strValTo, String strEffFrom, String strEffTo, int openID) {
        m_strEnterprise = strEnterprise;
        m_strEntityType = strEntityType;
        m_iEntityID = iEntityID;
        m_strAttributeCode = strAttributeCode;
        m_strAttributeValue = strAttributeValue;
        m_iNLSID = iNLSID;
        m_cbControlBlock = new ControlBlock(strValFrom, strValTo, strEffFrom, strEffTo, openID);
    }

    /**
     * getEnterprise
     * @return String
     */
    public String getEnterprise() {
        return m_strEnterprise;
    }

    /**
     * setEnterprise
     *
     * @param _strEnterprise
     */
    public void setEnterprise(String _strEnterprise) {
        this.m_strEnterprise = _strEnterprise;
    }

    /**
     * getEntityType
     * @return String
     */
    public String getEntityType() {
        return m_strEntityType;
    }

    /**
     * setEntityType
     *
     * @param _strEntityType
     */
    public void setEntityType(String _strEntityType) {
        this.m_strEntityType = _strEntityType;
    }

    /**
     * getEntityID
     * @return int
     */
    public int getEntityID() {
        return m_iEntityID;
    }

    /**
     * setEntityID
     *
     * @param _iEntityID
     */
    public void setEntityID(int _iEntityID) {
        this.m_iEntityID = _iEntityID;
    }

    /**
     * getAttributeCode
     * @return String
     */
    public String getAttributeCode() {
        return m_strAttributeCode;
    }

    /**
     * setAttributeCode
     * @param _strAttributeCode
     */
    public void setAttributeCode(String _strAttributeCode) {
        this.m_strAttributeCode = _strAttributeCode;
    }

    /**
     * getAttributeValue
     * @return String
     */
    public String getAttributeValue() {
        return m_strAttributeValue;
    }

    /**
     * setAttributeValue
     *
     * @param _strAttributeValue
     */
    public void setAttributeValue(String _strAttributeValue) {
        this.m_strAttributeValue = _strAttributeValue;
    }

    /**
     * getNLSID
     * @return int
     */
    public int getNLSID() {
        return m_iNLSID;
    }

    /**
     * setNLSID
     *
     * @param _iNLSID
     */
    public void setNLSID(int _iNLSID) {
        this.m_iNLSID = _iNLSID;
    }

    /**
     * getControlBlock
     * @return ControlBlock
     */
    public ControlBlock getControlBlock() {
        return m_cbControlBlock;
    }

    /**
     * setControlBlock
     *
     * @param _cbControlBlock
     */
    public void setControlBlock(ControlBlock _cbControlBlock) {
        this.m_cbControlBlock = _cbControlBlock;
    }

    /**
     * getShortDescription
     * @return String
     */
    public String getShortDescription() {
        return m_strShortDescription;
    }

    /**
     * setShortDescription
     *
     * @param _strShortDescription
     */
    public void setShortDescription(String _strShortDescription) {
        this.m_strShortDescription = _strShortDescription;
    }

    /**
     * getLongDescription
     * @return String
     */
    public String getLongDescription() {
        return m_strLongDescription;
    }

    /**
     * setLongDescription
     *
     * @param _strLongDescription
     */
    public void setLongDescription(String _strLongDescription) {
        this.m_strLongDescription = _strLongDescription;
    }

    /**
     * getObjectReference
     *
     * @return Object
     */
    public Object getObjectReference() {
        return m_objReference;
    }

    /**
     * setObjectReference
     *
     * @param _objReference
     */
    public void setObjectReference(Object _objReference) {
        this.m_objReference = _objReference;
    }

    /**
     * getUniqueType
     * @return
     * @author Dave
     */
    public String getUniqueType() {
        return m_strUniqueType;
    }

    /**
     * setUniqueType
     *
     * @author Dave
     * @param _strUniqueType
     */
    public void setUniqueType(String _strUniqueType) {
        this.m_strUniqueType = _strUniqueType;
    }

    /**
     * getUniqueClass
     * @return
     * @author Dave
     */
    public String getUniqueClass() {
        return m_strUniqueClass;
    }

    /**
     * setUniqueClass
     *
     * @author Dave
     * @param _strUniqueClass
     */
    public void setUniqueClass(String _strUniqueClass) {
        this.m_strUniqueClass = _strUniqueClass;
    }

    /**
     * getUnique
     * @return
     * @author Dave
     */
    public boolean getUnique() {
        return m_bUnique;
    }

    /**
     * setUnique
     *
     * @author Dave
     * @param _bUnique
     */
    public void setUnique(boolean _bUnique) {
        this.m_bUnique = _bUnique;
    }


    /**
     * The <code>Attribute</code> as a String
     * @return <code>Attribute</code> definition as a <code>String</code>
     */
    public String toString() {
        return "Enterprise:" + m_strEnterprise + " EntityType:" + m_strEntityType + " EntityID:" + m_iEntityID + " AttributeCode:" + m_strAttributeCode + " AttributeValue:" + m_strAttributeValue + " NLSID:" + m_iNLSID + m_cbControlBlock;
    }

    /**
     * setDeferredPost
     * @param _b
     * @author Dave
     */
    public void setDeferredPost(boolean _b) {
        m_bDeferred = _b;
    }

    /**
     * isDeferredPost
     * @return
     * @author Dave
     */
    public boolean isDeferredPost() {
        return m_bDeferred;
    }
    /**
     * ABR attributes must be posted last, after STATUS
     * @param _b
     */
    public void setDeferredPostMustBeLast(boolean _b) {
    	m_bDeferredLast = _b;
    	setDeferredPost(true);
    }

    /**
     * isDeferredPost and must be last
     * @return
     */
    public boolean isDeferredPostMustBeLast() {
        return m_bDeferred && m_bDeferredLast;
    }

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public String getVersion() {
        return "$Id: Attribute.java,v 1.34 2010/11/01 18:22:57 wendy Exp $";
    }

    /**
     * parse string of values delimited with UNIQUE_DELIMITER
     * BH SR87, SR655
     * @param values
     * @return
     */
    protected static Vector parseComboUniqueExt(String values){
        Vector vct = new Vector();
        int id = values.indexOf(UNIQUE_DELIMITER);
        if (id!=-1){
            while(id != -1) {
                id = values.indexOf(UNIQUE_DELIMITER);
                if (id != -1) {
                    vct.addElement(values.substring(0,id));
                    values = values.substring(id+UNIQUE_DELIMITER.length());
                }else{
					vct.add(values);
				}
            }
        }else{
            vct.add(values);
        }

        return vct;
    }

    /**
     * determine the attribute type of the attribute with the specified attribute code
     * BH SR87, SR655
     * @param _db
     * @param attrCode
     * @return
     * @throws MiddlewareException
     * @throws SQLException
     */
    protected String getAttrType(Database _db, String attrCode) throws MiddlewareException, SQLException
	{
    	String attrType = "";
		ReturnStatus returnStatus = new ReturnStatus(-1);
		ResultSet rs = null;
		// Get the type
		try {
			String strNow = _db.getDates().getNow();
		    rs = _db.callGBL8610(returnStatus, m_strEnterprise, m_objReference.toString().trim(), // rolecode held here
		    		attrCode, m_iNLSID, strNow, strNow);

		    if (rs.next()) {
		    	attrType = rs.getString(1).trim(); //attribute type - EntityClass
		      //  String s2 = rs.getString(2).trim(); // capability
		      //  int i3 = rs.getInt(3); // nlsid
		      //  String s4 = rs.getString(4).trim(); // short desc
		      //  String s5 = rs.getString(5).trim(); // long desc
		      //  String s6 = rs.getString(6).trim(); //ValFrom
		      //  String s7 = rs.getString(7).trim();  //ValTo
		    }
		} finally {
			if (rs!=null){
				rs.close();
				rs = null;
			}
			_db.commit();
			_db.freeStatement();
			_db.isPending();
		}

		return attrType;
	}
	/**
	 * check for uniqueness between text and flag values for this entitytype and id
	 * BH SR87, SR655
	 * called from a Text or a SingleFlag or a MultiFlag
	 * @param db
	 * @param flagAttrCode
	 * @param attrValue
	 * @param textAttrCode
	 * @param textAttrValue
	 * @param isFirstAttr - if true just add to fndidtbl, if false increment fndcount in fndidtbl
	 * @param fndIdTbl - hashtable used to track count of matching attr per entityid
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	protected void checkTextFlagComboUniqueness(Database db,String flagAttrCode,
			String attrValue, String textAttrCode,
			String textAttrValue, boolean isFirstAttr, Hashtable fndIdTbl) throws MiddlewareException, SQLException
	{
    	// Here .. we have to parse out all the flag codes ...
		StringTokenizer st = new StringTokenizer(attrValue, ":");
		String strFlagCode = st.nextToken();
		checkTextFlagCombo(db,flagAttrCode, strFlagCode,textAttrCode, textAttrValue, isFirstAttr, fndIdTbl);

		// now check any other flagcodes for this attr, must match ids already found
		while (st.hasMoreTokens()) {
			strFlagCode = st.nextToken();
			checkTextFlagCombo(db,flagAttrCode, strFlagCode,textAttrCode, textAttrValue, false, fndIdTbl);
		}
	}
	/**
	 * find entities with matching text and flag attrs and update count of matches found
	 * BH SR87, SR655
	 * @param db
	 * @param flagAttrCode
	 * @param flagAttrValue
	 * @param textAttrCode
	 * @param textAttrValue
	 * @param isFirstAttr - if true just add to fndidtbl, if false increment fndcount in fndidtbl
	 * @param fndIdTbl - hashtable used to track count of matching attr per entityid
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void checkTextFlagCombo(Database db,String flagAttrCode,
			String flagAttrValue, String textAttrCode,
			String textAttrValue, boolean isFirstAttr, Hashtable fndIdTbl) throws MiddlewareException, SQLException
	{
		ReturnStatus returnStatus = new ReturnStatus(-1);
		ResultSet rs = null;
		db.debug(D.EBUG_SPEW,"Attribute.checkTextFlagCombo: entered " + m_strEntityType + ":" +this.m_iEntityID
				+ " flagAttrCode "+flagAttrCode+" flagAttrValue "+flagAttrValue+
				" textAttrCode "+textAttrCode+" textAttrValue "+textAttrValue+" isFirstAttr "+isFirstAttr);
		try{
			rs = db.callGBL2929(returnStatus, m_strEnterprise, flagAttrCode, flagAttrValue,  // these are flag attr
					textAttrCode,textAttrValue, m_strEntityType); // these are Text attr
			int idFnd = 0;
			//hang onto all ids found, all values must match to fail this check
			while (rs.next()) {
				idFnd = rs.getInt(1);
				if(idFnd != m_iEntityID){
					Integer idFndInt = new Integer(idFnd);
					if(isFirstAttr){
						fndIdTbl.put(idFndInt,new Integer(1));
					}else{
						Integer fndIdCnt = (Integer)fndIdTbl.get(idFndInt);
						if(fndIdCnt !=null){
							fndIdTbl.put(idFndInt,new Integer(fndIdCnt.intValue()+1));
						}// if id not already there, we dont care about it
					}
				}
			}
		}finally{
			if(rs!=null){
				rs.close();
				rs = null;
			}
			db.freeStatement();
			db.isPending();
		}
	}
}
