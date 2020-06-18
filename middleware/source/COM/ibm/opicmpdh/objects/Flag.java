
//Copyright (c) 2001,2010 International Business Machines Corp., Ltd.
//All Rights Reserved.
//Licensed for use in connection with IBM business only.

//$Log: Flag.java,v $
//Revision 1.18  2010/07/12 21:02:44  wendy
//BH SR87, SR655 - extended combounique rule
//
//Revision 1.17  2008/01/31 21:42:00  wendy
//Cleanup RSA warnings

//Revision 1.16  2001/09/19 15:32:32  roger
//Formatting

//Revision 1.15  2001/09/19 15:24:58  roger
//Remove constructors with Profile as parm

//Revision 1.14  2001/09/17 23:18:55  roger
//More undo

//Revision 1.13  2001/09/17 22:17:49  roger
//Remove protected from constructors

//Revision 1.12  2001/09/17 22:13:58  roger
//Remove final

//Revision 1.11  2001/09/17 22:07:33  roger
//Needed import

//Revision 1.10  2001/09/17 22:00:00  roger
//Open up constructors and members

//Revision 1.9  2001/09/17 21:48:34  roger
//New accessors + mutators

//Revision 1.8  2001/09/17 20:24:27  roger
//Use Profile for values of Enterprise, OPENID, TranID, etc

//Revision 1.7  2001/08/22 16:53:07  roger
//Removed author RM

//Revision 1.6  2001/03/26 16:46:04  roger
//Misc formatting clean up

//Revision 1.5  2001/03/21 00:01:11  roger
//Implement java class file branding in getVersion method

//Revision 1.4  2001/03/16 15:52:27  roger
//Added Log keyword


package COM.ibm.opicmpdh.objects;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareSFRuleException;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 * Flag
 * @version @date
 * @see Attribute
 * @see Blob
 * @see ControlBlock
 * @see EntitiesAndRelator
 * @see Entity
 * @see LongText
 * @see MetaEntity
 * @see MultipleFlag
 * @see Relator
 * @see SingleFlag
 * @see Text
 */
public abstract class Flag extends Attribute implements Serializable, Cloneable {

	// Class constants

	/**
	 * @serial
	 */
	static final long serialVersionUID = 10L;

	// Class variables
	// Instance variables
	public String m_strFlagDescription = null;

//	/**
//	* Construct a <code>Flag</code>
//	*/
//	public Flag(Profile _prof, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, String strFlagDescription, ControlBlock cbControlBlock) {
//	super(_prof.getEnterprise(), strEntityType, iEntityID, strAttributeCode, strAttributeValue, strFlagDescription, _prof.getReadLanguage().getNLSID(), cbControlBlock);
//	}

	/**
	 * Construct a default <code>Flag</code>
	 */
	public Flag() {
		this("", "", 0, "", "", 0, new ControlBlock());
	}

	/**
	 * Construct a <code>Flag</code>
	 */
	public Flag(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID) {
		super(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID);
	}

	/**
	 * Construct a <code>Flag</code>
	 */
	public Flag(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID, ControlBlock cbControlBlock) {
		super(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, cbControlBlock);
	}

	/**
	 * Construct a <code>Flag</code>
	 */
	public Flag(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, int iNLSID, String strValFrom, String strValTo, String strEffFrom, String strEffTo, int openID) {
		super(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, new ControlBlock(strValFrom, strValTo, strEffFrom, strEffTo, openID));
	}

	/**
	 * Construct a <code>Flag</code>
	 */
	public Flag(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strAttributeValue, String strFlagDescription, int iNLSID, String strValFrom, String strValTo, String strEffFrom, String strEffTo, int openID) {
		super(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, new ControlBlock(strValFrom, strValTo, strEffFrom, strEffTo, openID));

		this.m_strFlagDescription = new String(strFlagDescription);
	}

	/**
	 *
	 */
	public String getFlagDescription() {
		return m_strFlagDescription;
	}

	/**
	 *
	 */
	public void setFlagDescription(String m_strFlagDescription) {
		this.m_strFlagDescription = m_strFlagDescription;
	}

	/**
	 * The <code>Flag</code> as a String
	 * @return <code>Flag</code> definition as a <code>String</code>
	 * /
  public String toString() {
    return super.toString();
  }

  /**
	 * Return the date/time this class was generated
	 * @return the date/time this class was generated
	 */
	public String getVersion() {
		return "$Id: Flag.java,v 1.18 2010/07/12 21:02:44 wendy Exp $";
	}

	/**
	 * perform extended combouniqueness check from this flag attr to a set of attr, text and/or flag
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

			if(attrType.equals("T")){ // is a text
				totalMatchCnt++;
				checkTextFlagComboUniqueness(db, m_strAttributeCode, m_strAttributeValue, 
						attrCode, attrValue, true, fndIdTbl);
			} // end first attr is text
			else if(attrType.equals("U")||attrType.equals("F")||attrType.equals("S")){ // is a flagtype
				StringTokenizer st = new StringTokenizer(this.m_strAttributeValue, ":");
				StringTokenizer stCombo = new StringTokenizer(attrValue, ":");
				totalMatchCnt +=(stCombo.countTokens() * st.countTokens()); // count all flags 
				
				checkFlagFlagComboUniqueness(db,attrCode,attrValue, true, fndIdTbl);
			} // end first attr is flag

			if(fndIdTbl.size()>0){ // match on first attr, so look at the rest
				// look at all of the other attributes for uniqueness
				for (int i=1; i<attrVct.size(); i++){
					attrCode = attrVct.elementAt(i).toString();
					attrValue = valueVct.elementAt(i).toString();
					// determine attr type and call correct sp
					attrType = getAttrType(db, attrCode);

					if(attrType.equals("T")){ // is a texttype
						totalMatchCnt++;
						checkTextFlagComboUniqueness(db, m_strAttributeCode,
								m_strAttributeValue, attrCode, attrValue,false, fndIdTbl);
					} // end attr is text
					else if(attrType.equals("U")||attrType.equals("F")||attrType.equals("S")){ // is a flagtype
						StringTokenizer st = new StringTokenizer(this.m_strAttributeValue, ":");
						StringTokenizer stCombo = new StringTokenizer(attrValue, ":");
						totalMatchCnt +=(stCombo.countTokens() * st.countTokens()); // count all flags
					
						checkFlagFlagComboUniqueness(db,attrCode,attrValue, false, fndIdTbl);
					} // end attr is flag
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
						db.debug(D.EBUG_ERR, "Flag.checkComboExt: COMBOCHECK failed. " + strExcMessage);

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
	 * check for other entities with the current flag values and the comboflag values
	 * BH SR87, SR655
	 * @param db
	 * @param comboflagAttrCode
	 * @param attrValue
	 * @param isFirstAttr
	 * @param fndIdTbl
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void checkFlagFlagComboUniqueness(Database db,String comboflagAttrCode, 
			String attrValue, boolean isFirstAttr, Hashtable fndIdTbl) throws MiddlewareException, SQLException
	{
		// Here .. we have to parse out all the flag codes ...
		StringTokenizer st = new StringTokenizer(m_strAttributeValue, ":");
		StringTokenizer stCombo = new StringTokenizer(attrValue, ":");

		boolean firstloop = isFirstAttr;
		// now check any other flagcodes for this attr, must match ids already found
		while (st.hasMoreTokens()) {
			String flagCode = st.nextToken();
			while (stCombo.hasMoreTokens()) {
				String comboFlagCode = stCombo.nextToken();
				checkFlagFlagCombo(db,m_strAttributeCode, flagCode, comboflagAttrCode, comboFlagCode,firstloop, fndIdTbl);
				firstloop = false;
			}
		}
	}

	/**
	 * find entities with matching flag and flag2 attrs and update count of matches found
	 * BH SR87, SR655
	 * @param db
	 * @param flagAttrCode
	 * @param flagAttrValue
	 * @param flagAttrCode2
	 * @param flagAttrValue2
	 * @param isFirstAttr - if true just add to fndidtbl, if false increment fndcount in fndidtbl
	 * @param fndIdTbl - hashtable used to track count of matching attr per entityid
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void checkFlagFlagCombo(Database db,String flagAttrCode, 
			String flagAttrValue, String flagAttrCode2,
			String flagAttrValue2, boolean isFirstAttr, Hashtable fndIdTbl) throws MiddlewareException, SQLException
	{
		ReturnStatus returnStatus = new ReturnStatus(-1);
		ResultSet rs = null;
		db.debug(D.EBUG_SPEW,"Flag.checkFlagFlagCombo: entered " + m_strEntityType + ":" +this.m_iEntityID
				+ " flagAttrCode "+flagAttrCode+" flagAttrValue "+flagAttrValue+
				" flagAttrCode2 "+flagAttrCode2+" flagAttrValue2 "+flagAttrValue2+" isFirstAttr "+isFirstAttr);
		try{
			rs = db.callGBL2928(returnStatus, m_strEnterprise, flagAttrCode, flagAttrValue,  // these are flag attr
					flagAttrCode2,flagAttrValue2, m_strEntityType); // these are combo flag attr
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
