
//Copyright (c) 2001,2010 International Business Machines Corp., Ltd.
//All Rights Reserved.
//Licensed for use in connection with IBM business only.

//$Log: Text.java,v $
//Revision 1.16  2010/07/12 21:02:44  wendy
//BH SR87, SR655 - extended combounique rule
//
//Revision 1.15  2008/01/31 21:42:00  wendy
//Cleanup RSA warnings

//Revision 1.14  2001/09/19 15:32:32  roger
//Formatting

//Revision 1.13  2001/09/19 15:24:58  roger
//Remove constructors with Profile as parm

//Revision 1.12  2001/09/17 22:17:50  roger
//Remove protected from constructors

//Revision 1.11  2001/09/17 22:07:33  roger
//Needed import

//Revision 1.10  2001/09/17 22:00:00  roger
//Open up constructors and members

//Revision 1.9  2001/09/17 20:24:28  roger
//Use Profile for values of Enterprise, OPENID, TranID, etc

//Revision 1.8  2001/08/22 16:53:09  roger
//Removed author RM

//Revision 1.7  2001/04/03 21:18:38  dave
//misc @see tag clean up

//Revision 1.6  2001/03/26 16:46:04  roger
//Misc formatting clean up

//Revision 1.5  2001/03/21 00:01:12  roger
//Implement java class file branding in getVersion method

//Revision 1.4  2001/03/16 15:52:27  roger
//Added Log keyword


package COM.ibm.opicmpdh.objects;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareSFRuleException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 * Text
 * @version @date
 * @see Attribute
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
 */
public final class Text extends Attribute implements Serializable, Cloneable {

	// Class constants

	/**
	 * @serial
	 */
	static final long serialVersionUID = 10L;

	// Class variables
	// Instance variables

	/**
	 * Main method which performs a simple test of this class
	 */
	public static void main(String args[]) {
	}

//	/**
//	* Construct a <code>Text</code>
//	*/
//	public Text(Profile _prof, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, ControlBlock cbControlBlock) {
//	super(_prof.getEnterprise(), strEntityType, iEntityID, strAttributeCode, strAttributeValue, _prof.getReadLanguage().getNLSID(), cbControlBlock);
//	}


	/**
	 * Construct a default <code>Text</code>
	 */
	public Text() {
		this("", "", 0, "", "", 0, new ControlBlock());
	}

	/**
	 * Construct a <code>Text</code>
	 */
	public Text(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID) {
		super(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID);
	}

	/**
	 * Construct a <code>Text</code>
	 */
	public Text(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID, ControlBlock cbControlBlock) {
		super(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, cbControlBlock);
	}

	/**
	 * Construct a <code>Text</code>
	 */
	public Text(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID, String strValFrom, String strValTo, String strEffFrom, String strEffTo, int openID) {
		super(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, new ControlBlock(strValFrom, strValTo, strEffFrom, strEffTo, openID));
	}

	/**
	 * The <code>Text</code> as a String
	 * @return <code>Text</code> definition as a <code>String</code>
	 */
	public final String toString() {
		return super.toString();
	}

//	/**
//	* Save the object in the database
//	*/
//	public void saveToDatabase() {
//	}


	/**
	 * Return the date/time this class was generated
	 * @return 
	 */
	public final String getVersion() {
		return "$Id: Text.java,v 1.16 2010/07/12 21:02:44 wendy Exp $";
	}

	/**
	 * perform extended combouniqueness check from this text attr to a set of attr, text and/or flag
	 * BH SR87, SR655
	 * @param db
	 * @throws SQLException 
	 * @throws MiddlewareException 
	 */
	public void checkComboExt(Database db) throws MiddlewareException, SQLException
	{
		//parse attr codes
		Vector attrVct = parseComboUniqueExt(m_strComboAttributeCode);
		//parse attr values
		Vector valueVct = parseComboUniqueExt(m_strComboAttributeValue);
		//parse attr descs
		Vector descVct = parseComboUniqueExt(m_strComboAttributeDesc);

		Hashtable fndIdTbl = new Hashtable();
		try{
			int totalMatchCnt = 0;
			// get first attr, hang onto all ids found, all values must match to fail this check
			String attrCode = attrVct.firstElement().toString();
			String attrValue = valueVct.firstElement().toString();
			// determine attr type and call correct sp
			String attrType = getAttrType(db, attrCode);

			if(attrType.equals("U")||attrType.equals("F")||attrType.equals("S")){ // is a flagtype
				StringTokenizer st = new StringTokenizer(attrValue, ":");
				totalMatchCnt +=st.countTokens();
				
				checkTextFlagComboUniqueness(db,attrCode, attrValue, m_strAttributeCode,
						m_strAttributeValue, true, fndIdTbl);
			} // end first attr is flag
			else if(attrType.equals("T")){  // it must be a Text pair
				totalMatchCnt++;
				checkTextComboUniqueness(db,attrCode,attrValue, true, fndIdTbl);
			} // end first attr is text
			
			if(fndIdTbl.size()>0){ // match on first attr, so look at the rest
				// look at all of the other attributes for uniqueness
				for (int i=1; i<attrVct.size(); i++){
					attrCode = attrVct.elementAt(i).toString();
					attrValue = valueVct.elementAt(i).toString();
					// determine attr type and call correct sp
					attrType = getAttrType(db, attrCode);

					if(attrType.equals("U")||attrType.equals("F")||attrType.equals("S")){ // is a flagtype
						StringTokenizer st = new StringTokenizer(attrValue, ":");
						totalMatchCnt +=st.countTokens();

						checkTextFlagComboUniqueness(db,attrCode, attrValue, m_strAttributeCode,
								m_strAttributeValue, false, fndIdTbl);
					} // end attr is flag
					else if(attrType.equals("T")){  // it must be a Text pair
						totalMatchCnt++;
						checkTextComboUniqueness(db,attrCode,attrValue, false, fndIdTbl);
					} // end attr is text
				}

				// now look at each id found, if it has the same attr count as totalmatchcnt, then fail
				for (Enumeration e = fndIdTbl.keys(); e.hasMoreElements();)	{
					Integer entityidkey = (Integer)e.nextElement();
					Integer attrCnt = (Integer)fndIdTbl.get(entityidkey);
					if(totalMatchCnt==attrCnt.intValue()){
						StringBuffer sbdesc = new StringBuffer(this.m_strLongDescription+" ("+m_strAttributeValue+") ");
						for (int i=0; i<attrVct.size(); i++){
							sbdesc.append(" and "+descVct.elementAt(i).toString()+" ("+valueVct.elementAt(i).toString()+") ");
						}

						String strExcMessage = "Combined Attributes Uniqueness check failed for "+m_strEntityType + ":" +
							m_iEntityID +" " + sbdesc.toString()+" is already in PDH for " + m_strEntityType+ ":" +
							entityidkey + ". (ok)";  
						db.debug(D.EBUG_ERR, "Text.checkComboExt: COMBOCHECK failed. " + strExcMessage);

						throw new MiddlewareSFRuleException(strExcMessage);
					}
				}
			}
		}finally{
			attrVct.clear();
			valueVct.clear();
			descVct.clear();
			fndIdTbl.clear();
		}
	}

	/**
	 * find entities with matching text attrs and update count of matches found
	 * BH SR87, SR655
	 * @param db
	 * @param attrCode
	 * @param attrValue
	 * @param isFirstAttr - if true just add to fndidtbl, if false increment fndcount in fndidtbl
	 * @param fndIdTbl - hashtable used to track count of matching attr per entityid
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void checkTextComboUniqueness(Database db,String attrCode, 
			String attrValue, boolean isFirstAttr, Hashtable fndIdTbl) throws MiddlewareException, SQLException
	{
		ReturnStatus returnStatus = new ReturnStatus(-1);
		ResultSet rs = null;
		try{
			db.debug(D.EBUG_SPEW,"Text.checkTextComboUniqueness: entered m_strAttributeCode "+ 
					m_strAttributeCode + " m_strAttributeValue " + m_strAttributeValue + 
					" other attrCode " + attrCode+" attrValue "+attrValue+" isFirstAttr "+isFirstAttr);
			
			rs = db.callGBL2931(returnStatus, m_strEnterprise,
					m_strAttributeCode, m_strAttributeValue, // this text attr's data
					attrCode); // other text attrcode
			ReturnDataResultSet rdrs = new ReturnDataResultSet(rs);
			for(int iRow = 0; iRow < rdrs.getRowCount(); iRow++) {
				String strEntityType_rs = rdrs.getColumn(iRow,0).trim();
				int iEntityID_rs = rdrs.getColumnInt(iRow,1);
				String strAttValOpt_rs = rdrs.getColumn(iRow,2).trim();

				db.debug(D.EBUG_SPEW,"gbl2931 answer[" + iRow + "]:" + strEntityType_rs + ":" + iEntityID_rs + ":" + strAttValOpt_rs);

				// if we got back ourself as answer --> we dont care
				if(strEntityType_rs.equals(m_strEntityType) && iEntityID_rs == this.m_iEntityID) {
					continue;
				}
				if(!strEntityType_rs.equals(m_strEntityType)) { // not same entitytype
					continue;
				}

				if(strAttValOpt_rs.equals(attrValue)) {
					//hang onto all ids found, all combovalues must match to fail this check
					Integer idFndInt = new Integer(iEntityID_rs);
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
