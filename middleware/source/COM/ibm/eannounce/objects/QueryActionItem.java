//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package COM.ibm.eannounce.objects;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;

import COM.ibm.opicmpdh.middleware.*;


/**********************************************************************************
 * This class executes a query and returns it to the JUI for display
 *
 *select * from price.lseo where entityid=656
 *select * from price.juicatlseo where entityid=852582
opicm.metadescription
SG  QRYJUIMODEL View    View Catalog Information    View Catalog Information    1 
opicm.metaentity
SG  QRYJUIMODEL View    
opicm.metalinkattr
SG  Action/Attribute    QRYJUIMODEL ENTITYTYPE  Link    MODEL   
SG  Action/Attribute    QRYJUIMODEL TYPE    ViewName    price.juimodel  
SG  Action/Attribute    QRYJUIMODEL TYPE    DataBase    ODS  ---->default is PDH
SG  Action/Category     QRYJUIMODEL MODELCAT    Link    Y 
 */
//$Log: QueryActionItem.java,v $
//Revision 1.8  2009/05/11 15:51:52  wendy
//Support dereference for memory release
//
//Revision 1.7  2009/04/01 14:54:27  wendy
//Add query action to mdui
//
//Revision 1.6  2009/03/25 15:50:56  wendy
//added comment
//
//Revision 1.5  2009/03/10 13:25:45  wendy
//Support view in ODS
//
//Revision 1.4  2008/08/08 21:43:12  wendy
//CQ00006067-WI : LA CTO - More support for QueryAction
//
//Revision 1.3  2008/08/07 12:54:18  wendy
//Make getviewname public
//
//Revision 1.2  2008/08/01 12:28:19  wendy
//Change action type to View
//
//Revision 1.1  2008/07/31 18:59:05  wendy
//CQ00006067-WI : LA CTO - Added support for QueryAction
//
public class QueryActionItem extends EANActionItem {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2009  All Rights Reserved.";
	/** cvs revision number */
	public static final String VERSION = "$Revision: 1.8 $";

	private static final long serialVersionUID = 20011106L;
	private String viewName = null;
	private EANList m_el = null;
	private boolean usePDH=true;

    protected void dereference(){
    	super.dereference();
    	viewName = null;
       	if (m_el !=null){
    		for (int i=0; i<m_el.size(); i++){
    			EntityItem mt = (EntityItem) m_el.getAt(i);
    			if (mt != null){
    				mt.dereference();
    			}
    		}
    		m_el.clear();
    		m_el = null;
    	}
    }
	public String getViewName() { return viewName;}
	private void setViewName(String n) { viewName = n;}

	/*********
	 * This represents a Query Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
	 * @param _emf
	 * @param _db
	 * @param _prof
	 * @param _strActionItemKey
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareRequestException
	 */
	public QueryActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, 
			String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException
	{
		this(_emf, _db, _prof,_strActionItemKey, true);
	}
	/*********
	 * This represents a Query Action Item. 
	 * @param _emf
	 * @param _db
	 * @param _prof
	 * @param _strActionItemKey
	 * @param chkView - When used by meta ui, viewname may not be set yet so prevent exception
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareRequestException
	 */
	public QueryActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, 
			String _strActionItemKey, boolean chkView) throws SQLException, MiddlewareException, MiddlewareRequestException
	{
		super(_emf, _db, _prof, _strActionItemKey);
		ResultSet rs = null;

		// Lets go get the information pertinent to the Query Action Item
		try {
			ReturnStatus returnStatus = new ReturnStatus(-1);
			ReturnDataResultSet rdrs;
			Profile prof = getProfile();

			rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), getKey(), prof.getValOn(), prof.getEffOn());
			rdrs = new ReturnDataResultSet(rs);

			for (int ii = 0; ii < rdrs.size(); ii++) {
				String strType = rdrs.getColumn(ii, 0);
				String strCode = rdrs.getColumn(ii, 1);
				String strValue = rdrs.getColumn(ii, 2);

				_db.debug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");

				// Collect the attributes
				if (strType.equals("TYPE") && strCode.equalsIgnoreCase("ViewName")) {
					setViewName(strValue);
				} else if (strType.equals("TYPE") && strCode.equalsIgnoreCase("DataBase")) {
					usePDH = !(strValue.equalsIgnoreCase("ODS"));
				}else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
			        setAssociatedEntityType(strValue);
		        }else {
					_db.debug(D.EBUG_ERR, "QueryActionItem "+getKey()+" *** ACTION ITEM ATTRIBUTE *** No home for this Action/Attribute " + strType + ":" + strCode + ":" + strValue);
				}
			}
			if(chkView){ // metaui needs to create without any actual meta
				_db.test(getViewName()!= null, "QueryActionItem "+getKey()+" ViewName is null");
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
	}
	/**
	 *Constructor for the QueryActionItem object
	 *
	 * @param  _mf                             Description of the Parameter
	 * @param  _ai                             Description of the Parameter
	 * @exception  MiddlewareRequestException  Description of the Exception
	 */
	public QueryActionItem(EANMetaFoundation _mf, QueryActionItem _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
		setViewName(_ai.getViewName());
	}
	/**************
	 *  This is the array of EntityItems that will be Reported Against
	 *  Sets the entityItems
	 *
	 * @param  _aei EntityItem[]
	 */
	public void setEntityItems(EntityItem[] _aei) {
		m_el = new EANList();
		// loop through and place the entityItem in the EANList
		for (int ii = 0; ii < _aei.length; ii++) {
			EntityItem eitmp = _aei[ii];
			// prep the information for RDI since if this is a remote call we need to strip
			try {
				m_el.put(new EntityItem(null, eitmp.getProfile(), eitmp.getEntityType(), eitmp.getEntityID()));
			} catch (MiddlewareRequestException e) {
				e.printStackTrace();
			}
		}
	}

	/***********************
	 * if true PDH is database, if false use ODS
	 * @return
	 */
	public boolean usesPDH(){
		return usePDH;
	}
	/****************
	 * the purpose of this object
	 * @return String
	 */
	public String getPurpose() {
		return "View";
	}

	/******************
	 *  Get information about this object
	 *
	 * @param  _bBrief  boolean
	 * @return   String
	 */
	public String dump(boolean _bBrief) {
		StringBuffer strbResult = new StringBuffer();
		strbResult.append("QueryActionItem:" + getKey() + ":desc:" + getLongDescription());
		strbResult.append(":purpose:" + getPurpose());
		strbResult.append(":viewname:" + getViewName() + "/n");
		return strbResult.toString();
	}

	/******************
	 * Use RMI to get the QueryList
	 * @param _rdi
	 * @param _prof
	 * @return QueryList
	 * @throws SQLException
	 * @throws MiddlewareRequestException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws MiddlewareException
	 * @throws RemoteException
	 */
	public QueryList rexec(RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException
	{
		D.ebug("TRACE:RDI QueryActionItem.rexec: num ei:" + m_el.size());
	    // careful not to serialize parents!
		QueryList qlReturn = null;
        EANFoundation ef = getParent();
        setTransientParent();
        setParent(null);
        qlReturn = _rdi.executeAction(_prof, this);
        // now set back
        setParent(ef);
        resetTransientParent();
        return qlReturn;
	}

	/**********
	 * Use direct jdbc to get the QueryList
	 * @param _db
	 * @param _prof
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws RemoteException 
	 */
	public QueryList exec(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {
		_db.debug(D.EBUG_SPEW, "QueryActionItem.exec num ei:" + m_el.size());
		return new QueryList(_db, _prof, this);
	}
	/*************
	 * Create the QueryList
	 * @param _db
	 * @param _prof
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws RemoteException 
	 */
	public QueryList executeAction(Database _db, Profile _prof) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException 
	{
		_db.debug(D.EBUG_SPEW, "QueryActionItem.executeAction num ei:" + m_el.size());
		return exec(_db, _prof);
	}

	/****************
	 *  Gets the entityItemArray
	 *
	 * @return    The entityItemArray value
	 */
	protected EntityItem[] getEntityItemArray() {
		int size = m_el.size();
		EntityItem[] aeiReturn = new EntityItem[size];
		for (int i = 0; i < size; i++) {
			aeiReturn[i] = (EntityItem) m_el.getAt(i);
		}
		return aeiReturn;
	}

	/**************
	 * used by metaui
	 * SG  Action/Attribute    QRYJUIMODEL TYPE    ViewName    price.juimodel 
	 * SG  Action/Attribute    QRYJUIMODEL TYPE    DataBase    ODS  ---->default is PDH
	 * @return boolean
	 */
	protected boolean updatePdhMeta(Database _db, boolean _bExpire)	throws MiddlewareException {
		String strValFrom = _db.getDates().getNow();
        String strValTo = (_bExpire ? strValFrom : _db.getDates().getForever());
        if (viewName!=null){
        	new MetaLinkAttrRow(getProfile(), "Action/Attribute", getActionItemKey(), "TYPE", "ViewName", 
        			viewName, strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
        	new MetaLinkAttrRow(getProfile(), "Action/Attribute", getActionItemKey(), "TYPE", "DataBase", 
        			usePDH?"PDH":"ODS", strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
        	return true;
        }
		return false;
	}

	/********************
	 *  Gets the version
	 *
	 * @return  String  The version value
	 */
	public String getVersion() {
		return "$Id: QueryActionItem.java,v 1.8 2009/05/11 15:51:52 wendy Exp $";
	}
    /*********
     * meta ui must update action
     */
    public void setActionClass(){
    	setActionClass("View");
    }	
    public void updateAction(boolean pdh, String view){
    	usePDH = pdh;
    	viewName = view;
    }
}
