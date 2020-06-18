
//Copyright (c) 2001, International Business Machines Corp., Ltd.
//All Rights Reserved.
//Licensed for use in connection with IBM business only.

//$Log: WhereUsedList.java,v $
//Revision 1.112  2013/09/19 15:06:14  wendy
//IN4311970:  Avails linked to incorrect product structures
//
//Revision 1.111  2011/03/22 20:59:17  wendy
//prevent null ptr after adding entity to exception (use diff constructor)
//
//Revision 1.110  2011/01/21 20:55:56  wendy
//Must create entitygroup for Navigate instead of Search, missed some attrs
//
//Revision 1.109  2010/09/15 17:37:42  wendy
//Added subset() for JUI checks
//
//Revision 1.108  2009/11/03 18:57:34  wendy
//SR11, SR15 and SR17 restrict create of relator - recognize this error

//Revision 1.107  2009/05/28 14:35:21  wendy
//Performance updates

//Revision 1.106  2009/05/08 15:58:59  wendy
//remove domain check for link

//Revision 1.105  2008/06/18 19:20:37  wendy
//MN35080480 deactivate entity table AND relator table when removelink

//Revision 1.104  2008/01/31 22:56:00  wendy
//Cleanup RSA warnings

//Revision 1.103  2007/08/15 14:36:37  wendy
//RQ0713072645- Enhancement 3

//Revision 1.102  2007/08/09 13:48:02  wendy
//RQ0713072645 whereused create check domain

//Revision 1.101  2007/08/09 01:21:14  wendy
//Allow exception to get back to UI from getWhereUsedList()

//Revision 1.100  2007/08/08 17:56:16  wendy
//RQ0713072645 find actions to use for domain check

//Revision 1.99  2007/08/03 11:25:45  wendy
//RQ0713072645-1 Make actions sensitive to the PROFILE's PDHDOMAINs

//Revision 1.98  2005/11/04 14:52:11  tony
//VEEdit_Iteration2
//updated VEEdit logic to meet new requirements.

//Revision 1.97  2005/09/02 17:49:29  tony
//CR 0817053929
//Allow for parent link from a picklist

//Revision 1.96  2005/05/10 23:25:38  joan
//fix for Delete/Entity/Relator

//Revision 1.95  2005/04/15 17:25:27  gregg
//add (ok) to VE locked dialogue

//Revision 1.94  2005/01/18 21:46:52  dave
//more parm debug cleanup

//Revision 1.93  2005/01/10 21:47:50  joan
//work on multiple edit

//Revision 1.92  2005/01/05 21:35:56  joan
//fixes

//Revision 1.91  2005/01/05 19:39:31  joan
//fix compile

//Revision 1.90  2005/01/05 19:32:30  joan
//fix compile

//Revision 1.89  2005/01/05 19:24:09  joan
//add new method

//Revision 1.88  2004/06/23 22:11:57  joan
//work on delete parent links

//Revision 1.87  2004/06/23 19:25:06  joan
//work on remove parent links

//Revision 1.86  2004/06/08 20:41:11  joan
//fix error

//Revision 1.85  2004/06/08 17:51:32  joan
//throw exception

//Revision 1.84  2004/06/08 17:28:34  joan
//add method

//Revision 1.83  2004/06/08 17:05:22  joan
//add methods

//Revision 1.82  2004/04/09 19:37:21  joan
//add duplicate method

//Revision 1.81  2004/01/15 18:49:10  dave
//making sure we use long description in Matrix List

//Revision 1.80  2004/01/14 18:41:23  dave
//more squeezing of the short description

//Revision 1.79  2004/01/13 01:31:04  dave
//syntax and import

//Revision 1.78  2004/01/13 01:24:27  dave
//lets try building the WhereUsedList on this client side

//Revision 1.77  2004/01/12 20:26:15  dave
//breaking for performance Where Used I

//Revision 1.76  2004/01/08 22:55:38  dave
//syntax

//Revision 1.75  2004/01/08 22:49:03  dave
//more clean up for ActionTree I

//Revision 1.74  2004/01/06 23:25:03  joan
//fb 53496

//Revision 1.73  2004/01/06 22:03:46  joan
//fb fix

//Revision 1.72  2003/12/02 22:16:49  joan
//fb fix

//Revision 1.71  2003/11/21 21:35:16  joan
//check for velock when remove relator

//Revision 1.70  2003/10/28 21:54:45  joan
//fb fixes

//Revision 1.69  2003/10/23 21:31:26  joan
//fix fb52690

//Revision 1.68  2003/08/28 16:28:10  joan
//adjust link method to have link option

//Revision 1.67  2003/06/25 18:44:02  joan
//move changes from v111

//Revision 1.66.2.4  2003/06/25 00:36:51  joan
//fix compile

//Revision 1.66.2.3  2003/06/25 00:21:52  joan
//fix compile

//Revision 1.66.2.2  2003/06/25 00:15:29  joan
//fix compile

//Revision 1.66.2.1  2003/06/24 23:37:31  joan
//fix WhereUsedActionItem constructor

//Revision 1.66  2003/05/09 19:38:41  dave
//making a blobnow

//Revision 1.65  2003/04/24 21:04:52  dave
//trying to fix where used and equivelence?

//Revision 1.64  2003/04/23 21:21:11  dave
//adding all fields back to grid

//Revision 1.63  2003/04/23 19:16:07  dave
//syntaxes

//Revision 1.62  2003/04/23 18:59:39  dave
//syntax

//Revision 1.61  2003/04/21 16:57:41  dave
//adding metalink to the specific function in the Where
//Used List as opposed to propogating throughout
//the structure

//Revision 1.60  2003/04/18 22:19:05  dave
//syntax

//Revision 1.59  2003/04/18 22:13:17  dave
//alternate entitygroup building

//Revision 1.58  2003/04/18 21:37:42  dave
//exception throwing

//Revision 1.57  2003/04/18 21:29:29  dave
//more Syntax

//Revision 1.56  2003/04/18 21:16:41  dave
//more fixes

//Revision 1.55  2003/04/18 20:59:54  dave
//fixed massive taxing errors

//Revision 1.54  2003/04/18 20:12:30  dave
//Where Used re-write to get Associations in I

//Revision 1.53  2003/04/17 21:49:00  dave
//more null pointer plugs

//Revision 1.52  2003/04/17 20:41:44  dave
//more syntax

//Revision 1.51  2003/04/17 20:33:28  dave
//fixing syntax

//Revision 1.50  2003/04/17 20:24:26  dave
//missing a braket

//Revision 1.49  2003/04/17 20:16:39  dave
//trying to encorporate Associations into Where used

//Revision 1.48  2003/04/14 15:37:29  dave
//clean up and verification on getMetaLinkGroup

//Revision 1.47  2003/04/02 20:33:26  dave
//misc clean up

//Revision 1.46    2003/03/25 23:11:47  dave
//adding trace to see why where used comes back empty

//Revision 1.45    2003/02/27 21:28:51  joan
//fix id

//Revision 1.44    2003/02/27 20:54:30  joan
//change WhereUsedGroup key to identify parent and child of the same relator type

//Revision 1.43    2003/01/21 19:02:18  joan
//check VE lock for link

//Revision 1.42    2003/01/21 00:20:36  joan
//adjust link method to test VE lock

//Revision 1.41    2003/01/15 01:18:49  joan
//fix getWhereUsedList

//Revision 1.40    2003/01/14 22:44:17  joan
//fix null pointer

//Revision 1.39    2003/01/14 22:15:42  joan
//fix compile errors

//Revision 1.38    2003/01/14 22:05:04  joan
//adjust removeLink method

//Revision 1.37    2003/01/14 00:56:33  joan
//add checkOrphan

//Revision 1.36    2003/01/09 01:28:53  joan
//fix test method

//Revision 1.35    2003/01/08 23:45:34  joan
//fix bug

//Revision 1.34    2003/01/08 23:44:33  joan
//fix bug

//Revision 1.33    2003/01/08 22:56:39  joan
//debug

//Revision 1.32    2003/01/08 21:44:06  joan
//add getWhereUsedList

//Revision 1.31    2002/10/30 22:57:19  dave
//syntax on import

//Revision 1.30    2002/10/30 22:39:14  dave
//more cleanup

//Revision 1.29    2002/10/30 22:36:21  dave
//clean up

//Revision 1.28    2002/10/30 22:02:34  dave
//added exception throwing to commit

//Revision 1.27    2002/10/29 00:02:56  dave
//backing out row commit for 1.1

//Revision 1.26    2002/10/28 23:49:15  dave
//attempting the first commit with a row index

//Revision 1.25    2002/10/18 20:18:54  joan
//add isMatrixEditable method

//Revision 1.24    2002/10/09 21:32:58  dave
//added isDynaTable to EANTableWrapper interface

//Revision 1.23    2002/09/27 17:11:01  dave
//made addRow a boolean

//Revision 1.22    2002/07/16 21:32:21  joan
//working on getActionItemArray

//Revision 1.21    2002/07/16 15:38:21  joan
//working on method to return the array of actionitems

//Revision 1.20    2002/07/09 17:21:52  joan
//fix link method

//Revision 1.19    2002/07/08 21:51:33  joan
//remove System.out

//Revision 1.18    2002/07/08 18:18:17  joan
//fix link method

//Revision 1.17    2002/07/08 17:53:43  joan
//fix link method

//Revision 1.16    2002/07/08 16:05:30  joan
//fix link method

//Revision 1.15    2002/06/28 22:45:01  joan
//fixing bugs

//Revision 1.14    2002/06/28 20:35:44  joan
//debug

//Revision 1.13    2002/06/28 18:54:59  joan
//fix bugs

//Revision 1.12    2002/06/27 15:52:44  joan
//fixing bugs

//Revision 1.11    2002/06/26 22:54:09  joan
//debug

//Revision 1.10    2002/06/26 22:27:04  joan
//fixing bugs

//Revision 1.9  2002/06/25 20:36:09    joan
//add create method for whereused

//Revision 1.8  2002/06/25 17:59:41    joan
//fix errors

//Revision 1.7  2002/06/25 17:49:37    joan
//add link and removeLink methods

//Revision 1.6  2002/06/24 17:44:29    joan
//fix errors

//Revision 1.5  2002/06/21 22:40:41    joan
//add more work

//Revision 1.4  2002/06/21 22:19:14    joan
//null pointer

//Revision 1.3  2002/06/21 21:21:56    joan
//fix null pointer

//Revision 1.2  2002/06/21 16:01:59    joan
//fix errors

//Revision 1.1  2002/06/21 15:47:46    joan
//initial load



package COM.ibm.eannounce.objects;

import java.util.*;
import java.sql.ResultSet;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSetGroup;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Stopwatch;
import COM.ibm.opicmpdh.transactions.OPICMList;
import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.T;

//Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.LockException;
import java.sql.SQLException;
import java.rmi.RemoteException;

/**
 *  Description of the Class
 *
 *@author     davidbig
 *@created    April 17, 2003
 */
public class WhereUsedList extends EANMetaEntity implements EANTableWrapper {

	/**
	 *@serial
	 */
	final static long serialVersionUID = 1L;

	private EntityList m_el = null;
	private WhereUsedActionItem m_wuai = null;
	private EntityGroup m_egParent = null;
	private int m_iRID = -1;
	private Hashtable actionTbl = new Hashtable();  //RQ0713072645

	public void dereference(){
		for (int i = 0; i < getDataCount(); i++) {
			WhereUsedGroup qi = (WhereUsedGroup)getData().getAt(i);
			qi.dereference();
		}
		m_wuai = null; // dont deref here, it is reused

		if (m_el!= null){
			m_el.dereference();
			m_el = null;
		}
		if (actionTbl!= null){
			Enumeration e = actionTbl.elements();
			while (e.hasMoreElements()) {
				Vector vct = (Vector) e.nextElement();
				// dont deref actions here, they are reused
				vct.clear();
			}
			actionTbl.clear();
			actionTbl = null;
		}

		m_egParent = null;

		super.dereference();
	}

	/**
	 *  Main method which performs a simple test of this class
	 *
	 *@param  arg  Description of the Parameter
	 */
	public static void main(String arg[]) {
	}


	/**
	 *  Constructor for the WhereUsedList object
	 *
	 *@param  _prof                           Description of the Parameter
	 *@param  _db                             Description of the Parameter
	 *@param  _ai                             Description of the Parameter
	 *@exception  MiddlewareRequestException  Description of the Exception
	 *@exception  SQLException                Description of the Exception
	 *@exception  MiddlewareException         Description of the Exception
	 */
	public WhereUsedList(Database _db, Profile _prof, WhereUsedActionItem _ai, boolean _bMeta) throws SQLException, MiddlewareRequestException, MiddlewareException {

		super(null, _prof, _ai.getKey());
		try {

			EntityItem[] aei = _ai.getEntityItemArray();
			NavActionItem nai = _ai.getNavActionItem();

			_db.test(aei != null, "WhereUsed.init.EntityItem array is null");
			_db.test(aei.length > 0, "WhereUsed.ini.EntityItem array is zero");

			setParentActionItem(_ai);
			// DWB Action Tree Candidate
			setEntityList(new EntityList(_db, _prof, nai, aei, aei[0].getEntityType(), _bMeta));
			setParentEntityGroup(getEntityList().getParentEntityGroup());

			// Set up some needed refs
			EntityList el = getEntityList();
			EntityGroup egParent = getParentEntityGroup();
			String strEntityType = egParent.getEntityType();
			String strEnterprise = _prof.getEnterprise();

			putLongDescription(egParent.getLongDescription());
			putShortDescription(egParent.getShortDescription());

			// Lets look at some trace information ...

			ResultSet rs;
			ReturnDataResultSet rdrs;
			ReturnStatus returnStatus = new ReturnStatus(-1);

			rs = _db.callGBL8004(returnStatus, strEnterprise, strEntityType, nai.getKey());
			rdrs = new ReturnDataResultSet(rs);
			rs.close();
			rs = null;
			_db.commit();
			_db.freeStatement();
			_db.isPending();

			//int iStep = 1;
			for (int ii = 0; ii < rdrs.size(); ii++) {

				int iLevel = rdrs.getColumnInt(ii, 0);
				int iLeaf = rdrs.getColumnInt(ii, 1);
				String strFromEntity = rdrs.getColumn(ii, 2);
				String strToEntity = rdrs.getColumn(ii, 3);
				String strRelator = rdrs.getColumn(ii, 4);
				String strPDirection = rdrs.getColumn(ii, 5);
				String strCDirection = rdrs.getColumn(ii, 6);
				String strCategory = rdrs.getColumn(ii, 7);
				String strRClass = rdrs.getColumn(ii, 8);

				_db.debug(D.EBUG_SPEW, "gbl8004:answers:" + iLevel + ":" + iLeaf + ":" + strFromEntity + ":" + strToEntity + ":" + strRelator + ":" + strPDirection + ":" + strCDirection + ":" + strCategory + ":" + strRClass);
				if (iLevel == 0) {
					EntityGroup eg = el.getEntityGroup(strRelator);
					if (eg != null) {
						putWhereUsedGroup(new WhereUsedGroup(this, null, eg, (strCDirection.equals("U") ? WhereUsedGroup.PARENT : WhereUsedGroup.CHILD)));
					} else {
						_db.debug(D.EBUG_ERR, "WhereUsedList.init. cannot find EntityGroup:" + strRelator + ":");
					}
				}
			}

		} finally {

			_db.freeStatement();
			_db.isPending();

		}
	}

	/**
	 *  Constructor for the WhereUsedList object
	 *
	 *@param  _prof                           Description of the Parameter
	 *@param  _db                             Description of the Parameter
	 *@param  _ai                             Description of the Parameter
	 *@exception  MiddlewareRequestException  Description of the Exception
	 *@exception  SQLException                Description of the Exception
	 *@exception  MiddlewareException         Description of the Exception
	 */
	public WhereUsedList(RemoteDatabaseInterface _rdi, Profile _prof, WhereUsedActionItem _ai, EntityList _el)
	throws RemoteException, MiddlewareRequestException, MiddlewareException
	{
		super(null, _prof, _ai.getKey());
		EntityItem[] aei = _ai.getEntityItemArray();
		NavActionItem nai = _ai.getNavActionItem();

		T.est(aei != null, "WhereUsed.init.EntityItem array is null");
		T.est(aei.length > 0, "WhereUsed.ini.EntityItem array is zero");

		setParentActionItem(_ai);
		setEntityList(_el);

		// Set up some needed refs
		EntityList el = getEntityList();

		setParentEntityGroup(el.getParentEntityGroup());

		EntityGroup egParent = getParentEntityGroup();
		String strEntityType = egParent.getEntityType();
		String strEnterprise = _prof.getEnterprise();
	    
		putLongDescription(egParent.getLongDescription());
		//putShortDescription(egParent.getShortDescription());

		// Lets look at some trace information ...
		//ResultSet rs;
		ReturnDataResultSet rdrs;

		ReturnDataResultSetGroup rdrsg = _rdi.remoteGBL8004(strEnterprise, strEntityType, nai.getKey());
		rdrs = rdrsg.getResultSet(0);

		//int iStep = 1;
		for (int ii = 0; ii < rdrs.size(); ii++) {
			int iLevel = rdrs.getColumnInt(ii, 0);
			int iLeaf = rdrs.getColumnInt(ii, 1);
			String strFromEntity = rdrs.getColumn(ii, 2);
			String strToEntity = rdrs.getColumn(ii, 3);
			String strRelator = rdrs.getColumn(ii, 4);
			String strPDirection = rdrs.getColumn(ii, 5);
			String strCDirection = rdrs.getColumn(ii, 6);
			String strCategory = rdrs.getColumn(ii, 7);
			String strRClass = rdrs.getColumn(ii, 8);

			D.ebug(D.EBUG_SPEW, "gbl8004:answers:" + iLevel + ":" + iLeaf + ":" + strFromEntity + ":" + strToEntity + ":" + strRelator + ":" + strPDirection + ":" + strCDirection + ":" + strCategory + ":" + strRClass);
					  
			if (iLevel == 0) {
				EntityGroup eg = el.getEntityGroup(strRelator);
				if (eg != null) {
					putWhereUsedGroup(new WhereUsedGroup(this, null, eg, (strCDirection.equals("U") ? WhereUsedGroup.PARENT : WhereUsedGroup.CHILD)));
				} else {
					D.ebug(D.EBUG_ERR, "WhereUsedList.init. cannot find EntityGroup:" + strRelator + ":");
				}
			}
		}
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _bBrief  Description of the Parameter
	 *@return          Description of the Return Value
	 */
	public String dump(boolean _bBrief) {
		StringBuffer strbResult = new StringBuffer();
		strbResult.append("WhereUsedList: " + getKey());
		if (!_bBrief) {
			strbResult.append(" WhereUsedGroups ");
			for (int i = 0; i < getWhereUsedGroupCount(); i++) {
				WhereUsedGroup mg = getWhereUsedGroup(i);
				strbResult.append(mg.dump(_bBrief));
			}
		}

		return strbResult.toString();
	}


	/*
	 *  Returns a basic table adaptor for client rendering of EntityLists
	 */
	/**
	 *  Gets the table attribute of the WhereUsedList object
	 *
	 *@return    The table value
	 */
	public RowSelectableTable getTable() {
		return new RowSelectableTable(this, getLongDescription());
	}


	/**
	 *  Gets the whereUsedGroupCount attribute of the WhereUsedList object
	 *
	 *@return    The whereUsedGroupCount value
	 */
	public int getWhereUsedGroupCount() {
		return getDataCount();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  _mg  Description of the Parameter
	 */
	protected void putWhereUsedGroup(WhereUsedGroup _mg) {
		putData(_mg);
	}


	/**
	 *  Gets the whereUsedGroup attribute of the WhereUsedList object
	 *
	 *@param  _i  Description of the Parameter
	 *@return     The whereUsedGroup value
	 */
	public WhereUsedGroup getWhereUsedGroup(int _i) {
		return (WhereUsedGroup) getData(_i);
	}


	/**
	 *  Gets the whereUsedGroup attribute of the WhereUsedList object
	 *
	 *@param  _s  Description of the Parameter
	 *@return     The whereUsedGroup value
	 */
	public WhereUsedGroup getWhereUsedGroup(String _s) {
		return (WhereUsedGroup) getData(_s);
	}


	/**
	 *  Sets the entityList attribute of the WhereUsedList object
	 *
	 *@param  _el  The new entityList value
	 */
	protected void setEntityList(EntityList _el) {
		m_el = _el;
	}


	/**
	 *  Gets the entityList attribute of the WhereUsedList object
	 *
	 *@return    The entityList value
	 */
	public EntityList getEntityList() {
		return m_el;
	}


	/**
	 *  Sets the parentActionItem attribute of the WhereUsedList object
	 *
	 *@param  _mai  The new parentActionItem value
	 */
	protected void setParentActionItem(WhereUsedActionItem _mai) {
		m_wuai = _mai;
	}


	/**
	 *  Gets the parentActionItem attribute of the WhereUsedList object
	 *
	 *@return    The parentActionItem value
	 */
	public WhereUsedActionItem getParentActionItem() {
		return m_wuai;
	}


	/**
	 *  Sets the originalEntityGroup attribute of the WhereUsedList object
	 *
	 *@param  _eg  The new originalEntityGroup value
	 */
	protected void setParentEntityGroup(EntityGroup _eg) {
		m_egParent = _eg;
	}


	/**
	 *  Gets the originalEntityGroup attribute of the WhereUsedList object
	 *
	 *@return    The originalEntityGroup value
	 */
	public EntityGroup getParentEntityGroup() {
		return m_egParent;
	}


	/**
	 *  Gets the matrixValue attribute of the WhereUsedList object
	 *
	 *@param  _str  Description of the Parameter
	 *@return       The matrixValue value
	 */
	public Object getMatrixValue(String _str) {
		return null;
	}


	/*
	 *  return the column information here
	 */
	/**
	 *  Gets the columnList attribute of the WhereUsedList object
	 *
	 *@return    The columnList value
	 * Change for long description
	 */
	public EANList getColumnList() {
		EANList colList = new EANList();
		try {
			MetaLabel ml1 = new MetaLabel(this, getProfile(), WhereUsedItem.ORIGINALENTITY, getParentEntityGroup().getLongDescription(), String.class);
			ml1.putShortDescription(getParentEntityGroup().getLongDescription());
			MetaLabel ml2 = new MetaLabel(this, getProfile(), WhereUsedItem.RELATIONSHIP, "Relationship Description", String.class);
			ml2.putShortDescription("Relationship Description");
			MetaLabel ml3 = new MetaLabel(this, getProfile(), WhereUsedItem.DIRECTION, "Direction", String.class);
			ml3.putShortDescription("Direction");
			MetaLabel ml4 = new MetaLabel(this, getProfile(), WhereUsedItem.RELATEDENTITYTYPE, "Entity Description", String.class);
			ml4.putShortDescription("Entity Description");
			MetaLabel ml5 = new MetaLabel(this, getProfile(), WhereUsedItem.RELATEDENTITY, "Entity Display Name", String.class);
			ml5.putShortDescription("Entity Display Name");
			colList.put(ml1);
			colList.put(ml2);
			colList.put(ml3);
			colList.put(ml4);
			colList.put(ml5);
		} catch (Exception x) {
			x.printStackTrace();
		}
		return colList;
	}


	/*
	 *  Return only visible rows
	 */
	/**
	 *  Gets the rowList attribute of the WhereUsedList object
	 *
	 *@return    The rowList value
	 */
	public EANList getRowList() {
		EANList rowList = new EANList();
		for (int i = 0; i < getWhereUsedGroupCount(); i++) {
			WhereUsedGroup wug = getWhereUsedGroup(i);
			for (int j = 0; j < wug.getWhereUsedItemCount(); j++) {
				WhereUsedItem wui = wug.getWhereUsedItem(j);
				try {
					rowList.put(new Implicator(wui, getProfile(), wui.getKey()));
				} catch (Exception x) {
					x.printStackTrace();
				}
			}
		}
		return rowList;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Return Value
	 */
	public boolean canCreate() {
		return false;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Return Value
	 */
	public boolean canEdit() {
		return false;
	}


	/**
	 *  Adds a feature to the Row attribute of the WhereUsedList object
	 *
	 *@return    Description of the Return Value
	 */
	public boolean addRow() {
		return false;
	}

	/**
	 *  Adds a feature to the Row attribute of the WhereUsedList object
	 *
	 *@return    Description of the Return Value
	 */
	public boolean addRow(String _strKey) {
		return false;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _strKey  Description of the Parameter
	 */
	public void removeRow(String _strKey) { }


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Return Value
	 */
	public boolean hasChanges() {
		return false;
	}


	public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
	}


	public void putMatrixValue(String _str, Object _o) { }

	public boolean isMatrixEditable(String _str) {
		return false;
	}


	/**
	 *  Description of the Method
	 */
	public void rollbackMatrix() { }


	/**
	 *  Adds a feature to the Column attribute of the WhereUsedList object
	 *
	 *@param  _ean  The feature to be added to the Column attribute
	 */
	public void addColumn(EANFoundation _ean) { }


	/**
	 *  Gets the originalEIArray attribute of the WhereUsedList object
	 *
	 *@return    The originalEIArray value
	 */
	private EntityItem[] getOriginalEIArray() {
		EntityGroup egParent = getParentEntityGroup();
		int size = egParent.getEntityItemCount();
		EntityItem[] aeiReturn = new EntityItem[size];
		for (int i = 0; i < size; i++) {
			aeiReturn[i] = egParent.getEntityItem(i);
		}
		return aeiReturn;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  _db      Description of the Parameter
	 *@param  _rdi     Description of the Parameter
	 *@param  _prof    Description of the Parameter
	 *@param  _strKey  Description of the Parameter
	 *@return          Description of the Return Value
	 */
	public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) {

		if (_db == null && _rdi == null) {
			return null;
		}
		if (m_wuai == null) {
			return null;
		}
		EntityList elReturn = null;

		// get strRelatorType
		String strRelatorType = null;
		for (int i = 0; i < getWhereUsedGroupCount(); i++) {
			WhereUsedGroup wug = getWhereUsedGroup(i);
			if (wug.isPickListable()) {
				WhereUsedItem wui = wug.getWhereUsedItem(_strKey);
				if (wui != null) {
					strRelatorType = wug.getEntityType();
				}
			}
		}

		if (strRelatorType != null) {
			m_wuai.setEntityItems(getOriginalEIArray());
			try {
				if (_db != null) {
					elReturn = m_wuai.generatePickList(_db, _prof, strRelatorType);
				} else {
					elReturn = m_wuai.rgeneratePickList(_rdi, _prof, strRelatorType);
				}
			} catch (Exception x) {
				x.printStackTrace();
			}
		}

		return elReturn;
	}


	/**
	 *  Gets the linkable attribute of the WhereUsedList object
	 *
	 *@param  _list            Description of the Parameter
	 *@param  _aLockOwner      Description of the Parameter
	 *@param  _strEntityType   Description of the Parameter
	 *@param  _strRelatorType  Description of the Parameter
	 *@return                  The linkable value
	 */
	private boolean isLinkable(VELockERList _list, String[] _aLockOwner, String _strEntityType, String _strRelatorType) {
		for (int i = 0; i < _aLockOwner.length; i++) {
			String strLockOwner = _aLockOwner[i];
			if (_list.test(strLockOwner, _strEntityType, _strRelatorType)) {
				return true;
			}
		}

		return false;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _db                           Description of the Parameter
	 *@param  _rdi                          Description of the Parameter
	 *@param  _prof                         Description of the Parameter
	 *@param  _strKey                       Description of the Parameter
	 *@param  _aeiChild                     Description of the Parameter
	 *@return                               Description of the Return Value
	 *@exception  EANBusinessRuleException  Description of the Exception
	 */
	public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey, EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException {

		//
		// we need to get the metaLink record out of the
		// I think we need to get the Where Used Item as a parm.. and not the _strKey
		//

		if (_db == null && _rdi == null) {
			return null;
		}
		if (_aeiChild.length <= 0) {
			return null;
		}

		EntityItemException eie = new EntityItemException();
		Vector wuiVector = new Vector();
		long starttime = System.currentTimeMillis();
		for (int i = 0; i < getWhereUsedGroupCount(); i++) {
			WhereUsedGroup wug = getWhereUsedGroup(i);
			//CR 0817053929      if (wug != null && (!wug.isParent()) && wug.isPickListable()) {
			if (wug != null && wug.isPickListable()) {
				if (wug.isParent()) {                                                                                                                                               //CR 0817053929
					WhereUsedItem wui = wug.getWhereUsedItem(_strKey);                                                                                                              //CR 0817053929
					if (wui != null) {                                                                                                                                              //CR 0817053929
						OPICMList ol = null;                                                                                                                                        //CR 0817053929
						try {                                                                                                                                                       //CR 0817053929

							EntityItem eiChild = wui.getOriginalEntityItem();                                                                                                       //CR 0817053929

							MetaLink ml = getParentEntityGroup().getMetaLinkGroup().getMetaLink(MetaLinkGroup.UP, wug.getEntityType());                                             //CR 0817053929
							if (ml == null) {                                                                                                                                       //CR 0817053929
								eie.add(new EntityItem(null, _prof, eiChild.getEntityType(), eiChild.getEntityID()), "Unable to link because no MetaLink Object can be found in the Where Used List for " + wug.getEntityType());
								continue;                                                                                                                                           //CR 0817053929
							}                                                                                                                                                       //CR 0817053929
							long curtime1 = System.currentTimeMillis();
							VELockERList pList = null;                                                                                                                              //CR 0817053929
							VELockERList cList = null;                                                                                                                              //CR 0817053929

							if (_db != null) {                                                                                                                                      //CR 0817053929
								pList = _db.getVELockERList(_prof, eiChild.getEntityType());                                                                                        //CR 0817053929
								cList = _db.getVELockERList(_prof, _aeiChild[0].getEntityType());                                                                                   //CR 0817053929
							} else {                                                                                                                                                //CR 0817053929
								pList = _rdi.getVELockERList(_prof, eiChild.getEntityType());                                                                                       //CR 0817053929
								cList = _rdi.getVELockERList(_prof, _aeiChild[0].getEntityType());                                                                                  //CR 0817053929
							}
							long curtime = System.currentTimeMillis();
							D.ebug(D.EBUG_DETAIL,"WhereUsedList.link child time to getVELockERList "+Stopwatch.format(curtime-curtime1));
							// test first entity's VELock
							String[] apVELock = null;                                                                                                                               //CR 0817053929
							if (_db != null) {                                                                                                                                      //CR 0817053929
								apVELock = _db.getVELockOwners(_prof, eiChild);                                                                                                     //CR 0817053929
							} else {                                                                                                                                                //CR 0817053929
								apVELock = _rdi.getVELockOwners(_prof, eiChild);                                                                                                    //CR 0817053929
							}                                                                                                                                                       //CR 0817053929
							curtime1 = System.currentTimeMillis();
							D.ebug(D.EBUG_DETAIL,"WhereUsedList.link time to getVELockOwners1 "+Stopwatch.format(curtime1-curtime));
							if (apVELock.length > 0 && (!isLinkable(pList, apVELock, wug.getEntityType(),eiChild.getEntityType()))) {                                               //CR 0817053929
								eie.add(new EntityItem(null, _prof, eiChild.getEntityType(), eiChild.getEntityID()), "Unable to link because the entity is VE locked (ok)");        //CR 0817053929
								continue;                                                                                                                                           //CR 0817053929
							}                                                                                                                                                       //CR 0817053929

							// test second entity's VELock
							Vector v = new Vector();                                                                                                                                //CR 0817053929
							for (int j = 0; j < _aeiChild.length; j++) {                                                                                                            //CR 0817053929
								String[] acVELock = null;                                                                                                                           //CR 0817053929
								EntityItem eic = _aeiChild[j];                                                                                                                      //CR 0817053929
								if (_db != null) {                                                                                                                                  //CR 0817053929
									acVELock = _db.getVELockOwners(_prof, eic);                                                                                                     //CR 0817053929
								} else {                                                                                                                                            //CR 0817053929
									acVELock = _rdi.getVELockOwners(_prof, eic);                                                                                                    //CR 0817053929
								}                                                                                                                                                   //CR 0817053929
								if (acVELock.length > 0 && (!isLinkable(cList, acVELock, eic.getEntityType(), wug.getEntityType()))) {                                              //CR 0817053929
									eie.add(new EntityItem(null, _prof, eic.getEntityType(), eic.getEntityID()), "Unable to link because the entity is VE locked (ok)");            //CR 0817053929
									continue;                                                                                                                                       //CR 0817053929
								} else {                                                                                                                                            //CR 0817053929
									v.addElement(eic);                                                                                                                              //CR 0817053929
								}                                                                                                                                                   //CR 0817053929
							}                                                                                                                                                       //CR 0817053929
							curtime = System.currentTimeMillis();
							D.ebug(D.EBUG_DETAIL,"WhereUsedList.link time to all getVELockOwners2 "+Stopwatch.format(curtime-curtime1));

							EntityItem[] aeiChild = {eiChild};                                                                                                                      //CR 0817053929
							EntityItem[] aei = new EntityItem[v.size()];                                                                                                            //CR 0817053929
							v.copyInto(aei);                                                                                                                                        //CR 0817053929

							/*
							 * whereused dont have link actions and rupal says link should always be allowed
                            try{
                                LinkActionItem lai = findLinkAction(eiChild,aei);
                                EntityList.checkDomain(_prof,lai,aeiChild); //RQ0713072645
                                EntityList.checkDomain(_prof,lai,aei); //RQ0713072645
                            }catch(DomainException de){ //RQ0713072645
                                for (int i2=0; i2<de.getErrorCount(); i2++){
                                    eie.add(de.getItem(i2), de.getMessage(i2));
                                }
                                de.dereference();
                                continue;
                            }*/

							if (_rdi != null) {                                                                                                                                     //CR 0817053929
								ol = EANUtility.linkEntityItems(_rdi, _prof, _strLinkOption, aei,aeiChild, ml, EANUtility.LINK_DEFAULT, 1, true);                                   //CR 0817053929
							} else {                                                                                                                                                //CR 0817053929
								ol = EANUtility.linkEntityItems(_db, _prof, _strLinkOption, aei,aeiChild, ml, EANUtility.LINK_DEFAULT, 1, true);                                    //CR 0817053929
							}
							curtime1 = System.currentTimeMillis();
							D.ebug(D.EBUG_DETAIL,"WhereUsedList.link time to do link "+Stopwatch.format(curtime1-curtime));

						}catch (Exception x) {                                                                                                                                      //CR 0817053929
							x.printStackTrace();
						}                                                                                                                                                           //CR 0817053929

						Hashtable relGrpTbl = new Hashtable();
						for (int j = 0; j < ol.size(); j++) {                                                                                                                       //CR 0817053929
							ReturnRelatorKey rrk = (ReturnRelatorKey) ol.getAt(j);                                                                                                  //CR 0817053929
							boolean bposted = rrk.isPosted();                                                                                                                       //CR 0817053929
							boolean bactive = rrk.isActive();                                                                                                                       //CR 0817053929

							if (bposted && bactive) {                                                                                                                               //CR 0817053929
								try {                                                                                                                                               //CR 0817053929
									EntityItem eiRelated = null;                                                                                                                    //CR 0817053929
									for (int x = 0; x < _aeiChild.length; x++) {                                                                                                    //CR 0817053929
										EntityItem ei = _aeiChild[x];                                                                                                               //CR 0817053929
										if (ei.getKey().equals(rrk.getEntity1Type() + rrk.getEntity1ID())) {                                                                        //CR 0817053929
											eiRelated = ei;                                                                                                                         //CR 0817053929
										}                                                                                                                                           //CR 0817053929
									}                                                                                                                                               //CR 0817053929
									if (eiRelated != null) {  
										// add the newly created relator                                      
										EntityItem eiRelator = new EntityItem(wug.getEntityGroup(), _prof, rrk.getEntityType(), rrk.getReturnID());  

										// clone the eiRelated, parent elist may be dereferenced
										EntityGroup relatedGrp = null;
										if(this.getEntityList()!=null){
											relatedGrp = this.getEntityList().getEntityGroup(eiRelated.getEntityType());
										}
										if(relatedGrp ==null){
											relatedGrp = (EntityGroup)relGrpTbl.get(eiRelated.getEntityType());
										}
										if(relatedGrp ==null){
											if (_rdi != null){ // use rdi
												relatedGrp = new EntityGroup(null, _rdi, _prof, eiRelated.getEntityType(), "Navigate");
											}else{// use db
												relatedGrp = new EntityGroup(null, _db, _prof, eiRelated.getEntityType(), "Navigate");
											}
											relGrpTbl.put(eiRelated.getEntityType(),relatedGrp);
										}

										EntityItem eiRelatedClone = new EntityItem(relatedGrp,eiRelated); 
										relatedGrp.putEntityItem(eiRelatedClone);

										WhereUsedItem wuiReturn = new WhereUsedItem(wug, _prof, wui.getOriginalEntityItem(), eiRelatedClone, eiRelator, wug.getDirectionString());       //CR 0817053929
										wug.putWhereUsedItem(wuiReturn);                                                                                                            //CR 0817053929
										wuiVector.addElement(wuiReturn);                                                                                                            //CR 0817053929

										// remove dummy WhereUsedItem
										EntityItem ei = wui.getRelatorEntityItem();                                                                                                 //CR 0817053929
										if (ei.getEntityID() < 0) {                                                                                                                 //CR 0817053929
											wug.removeWhereUsedItem(wui);                                                                                                           //CR 0817053929
										}                                                                                                                                           //CR 0817053929
									}                                                                                                                                               //CR 0817053929
								} catch (Exception x) {                                                                                                                             //CR 0817053929
									x.printStackTrace();                                                                                                                            //CR 0817053929
								}                                                                                                                                                   //CR 0817053929
							}                                                                                                                                                       //CR 0817053929
						}                                                                                                                                                           //CR 0817053929
						relGrpTbl.clear();
						relGrpTbl = null;
					}                                                                                                                                                               //CR 0817053929
				} else {                                                                                                                                                            //CR 0817053929
					WhereUsedItem wui = wug.getWhereUsedItem(_strKey);
					if (wui != null) {
						OPICMList ol = null;
						try {

							EntityItem eiParent = wui.getOriginalEntityItem();
							MetaLink ml = getParentEntityGroup().getMetaLinkGroup().getMetaLink(MetaLinkGroup.DOWN, wug.getEntityType());
							if (ml == null) {
								eie.add(new EntityItem(null, _prof, eiParent.getEntityType(), eiParent.getEntityID()), "Unable to link because no MetaLink Object can be found in the Where Used List for " + wug.getEntityType());
								continue;
							}
							long curtime1 = System.currentTimeMillis();
							VELockERList pList = null;
							VELockERList cList = null;

							if (_db != null) {
								pList = _db.getVELockERList(_prof, eiParent.getEntityType());
								cList = _db.getVELockERList(_prof, _aeiChild[0].getEntityType());
							} else {
								pList = _rdi.getVELockERList(_prof, eiParent.getEntityType());
								cList = _rdi.getVELockERList(_prof, _aeiChild[0].getEntityType());
							}
							long curtime = System.currentTimeMillis();
							D.ebug(D.EBUG_DETAIL,"WhereUsedList.link parent time to getVELockERList "+Stopwatch.format(curtime-curtime1));

							// test first entity's VELock
							String[] apVELock = null;
							if (_db != null) {
								apVELock = _db.getVELockOwners(_prof, eiParent);
							} else {
								apVELock = _rdi.getVELockOwners(_prof, eiParent);
							}
							curtime1 = System.currentTimeMillis();
							D.ebug(D.EBUG_DETAIL,"WhereUsedList.link time to getVELockOwners1 "+Stopwatch.format(curtime1-curtime));

							if (apVELock.length > 0 && (!isLinkable(pList, apVELock, eiParent.getEntityType(), wug.getEntityType()))) {
								eie.add(new EntityItem(null, _prof, eiParent.getEntityType(), eiParent.getEntityID()), "Unable to link because the entity is VE locked (ok)");
								continue;
							}

							// test second entity's VELock
							Vector v = new Vector();
							for (int j = 0; j < _aeiChild.length; j++) {
								String[] acVELock = null;
								EntityItem eic = _aeiChild[j];
								if (_db != null) {
									acVELock = _db.getVELockOwners(_prof, eic);
								} else {
									acVELock = _rdi.getVELockOwners(_prof, eic);
								}

								if (acVELock.length > 0 && (!isLinkable(cList, acVELock, eic.getEntityType(), wug.getEntityType()))) {
									eie.add(new EntityItem(null, _prof, eic.getEntityType(), eic.getEntityID()), "Unable to link because the entity is VE locked (ok)");
									continue;
								} else {
									v.addElement(eic);
								}
							}
							curtime = System.currentTimeMillis();
							D.ebug(D.EBUG_DETAIL,"WhereUsedList.link time to all getVELockOwners2 "+Stopwatch.format(curtime-curtime1));

							EntityItem[] aeiParent = {eiParent};
							EntityItem[] aei = new EntityItem[v.size()];
							v.copyInto(aei);

							/*
							 * whereused dont have link actions and rupal says link should always be allowed
                            try{
                                LinkActionItem lai = findLinkAction(eiParent,aei);
                                EntityList.checkDomain(_prof,lai,aeiParent); //RQ0713072645
                                EntityList.checkDomain(_prof,lai,aei); //RQ0713072645
                            }catch(DomainException de){ //RQ0713072645
                                for (int i2=0; i2<de.getErrorCount(); i2++){
                                    eie.add(de.getItem(i2), de.getMessage(i2));
                                }
                                de.dereference();
                                continue;
                            }*/

							if (_rdi != null) {
								ol = EANUtility.linkEntityItems(_rdi, _prof, _strLinkOption, aeiParent, aei, ml, EANUtility.LINK_DEFAULT, 1, true);
							} else {
								ol = EANUtility.linkEntityItems(_db, _prof, _strLinkOption, aeiParent, aei, ml, EANUtility.LINK_DEFAULT, 1, true);
							}

							curtime1 = System.currentTimeMillis();
							D.ebug(D.EBUG_DETAIL,"WhereUsedList.link time to do link "+Stopwatch.format(curtime1-curtime));
						}
						catch (Exception x) {
							x.printStackTrace();
						}
						
						Hashtable relGrpTbl = new Hashtable();

						for (int j = 0; j < ol.size(); j++) {
							ReturnRelatorKey rrk = (ReturnRelatorKey) ol.getAt(j);
							boolean bposted = rrk.isPosted();
							boolean bactive = rrk.isActive();

							if (bposted && bactive) {
								try {
									EntityItem eiRelated = null;
									for (int x = 0; x < _aeiChild.length; x++) {
										EntityItem ei = _aeiChild[x];
										if (ei.getKey().equals(rrk.getEntity2Type() + rrk.getEntity2ID())) {
											eiRelated = ei;
										}
									}
									if (eiRelated != null) {
										// add the newly created relator                                      
										EntityItem eiRelator = new EntityItem(wug.getEntityGroup(), _prof, rrk.getEntityType(), rrk.getReturnID());  
										EntityGroup relatedGrp = null;
										if(this.getEntityList()!=null){
											relatedGrp = this.getEntityList().getEntityGroup(eiRelated.getEntityType());
										}
										// clone the eiRelated, parent elist may be dereferenced
										if(relatedGrp ==null){
											relatedGrp = (EntityGroup)relGrpTbl.get(eiRelated.getEntityType());
										}
										if(relatedGrp ==null){
											if (_rdi != null){ // use rdi
												relatedGrp = new EntityGroup(null, _rdi, _prof, eiRelated.getEntityType(), "Navigate");
											}else{// use db
												relatedGrp = new EntityGroup(null, _db, _prof, eiRelated.getEntityType(), "Navigate");
											}
											relGrpTbl.put(eiRelated.getEntityType(),relatedGrp);
										}
									
										EntityItem eiRelatedClone = new EntityItem(relatedGrp,eiRelated); 
										relatedGrp.putEntityItem(eiRelatedClone);

										WhereUsedItem wuiReturn = new WhereUsedItem(wug, _prof, wui.getOriginalEntityItem(), eiRelatedClone, eiRelator, wug.getDirectionString());
										wug.putWhereUsedItem(wuiReturn);
										wuiVector.addElement(wuiReturn);

										// remove dummy WhereUsedItem
										EntityItem ei = wui.getRelatorEntityItem();
										if (ei.getEntityID() < 0) {
											wug.removeWhereUsedItem(wui);
										}
									}
								} catch (Exception x) {
									x.printStackTrace();
								}
							}
						}
						relGrpTbl.clear();
						relGrpTbl = null;
					}
				}
			}                       //CR 0817053929
		}

		// convert vector to array
		int size = wuiVector.size();
		WhereUsedItem wuiArray[] = new WhereUsedItem[size];

		for (int j = 0; j < size; j++) {
			wuiArray[j] = (WhereUsedItem) wuiVector.elementAt(j);
		}


		D.ebug(D.EBUG_DETAIL,"WhereUsedList.link total time to link "+Stopwatch.format(System.currentTimeMillis()-starttime));

		if (eie.getErrorCount() > 0) {
			throw eie;
		}

		return wuiArray;
	}

	/**
	 * Just get this once per types
	 * @param _db
	 * @param _rdi
	 * @param _prof
	 * @param VELockERListTbl
	 * @param linkEntityType
	 * @param childEntityType
	 */
	private void getVELockERLists(Database _db, RemoteDatabaseInterface _rdi,
			Profile _prof, Hashtable VELockERListTbl,
			String linkEntityType,String childEntityType)
	{
		VELockERList pList = (VELockERList)VELockERListTbl.get(linkEntityType);                                                                                                                              
		VELockERList cList = (VELockERList)VELockERListTbl.get(childEntityType);                                                                                                                             
		try{
			if (pList==null){
				if (_db != null) {
					pList = _db.getVELockERList(_prof, linkEntityType);
					cList = _db.getVELockERList(_prof, childEntityType);
				} else {
					pList = _rdi.getVELockERList(_prof, linkEntityType);
					cList = _rdi.getVELockERList(_prof, childEntityType);
				}
				VELockERListTbl.put(linkEntityType, pList);
				VELockERListTbl.put(childEntityType, cList);
			}
		}catch(Exception exc){
			exc.printStackTrace();
		}
	}
	/**
	 *  link all keys at once - perf improvement
	 * @param _db
	 * @param _rdi
	 * @param _prof
	 * @param _strKeys
	 * @param _aeiChild
	 * @param _strLinkOption
	 * @return
	 * @throws EANBusinessRuleException
	 */
	public EANFoundation[] linkMultiple(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _strKeys, EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException {
		if (_db == null && _rdi == null) {
			return null;
		}
		if (_aeiChild.length <= 0) {
			return null;
		}
		long starttime = System.currentTimeMillis();
		Hashtable VELockERListTbl = new Hashtable();
		EntityItemException eie = null;
		Vector wuiVector = new Vector();
		for (int i = 0; i < getWhereUsedGroupCount(); i++) {
			WhereUsedGroup wug = getWhereUsedGroup(i);
			//CR 0817053929      if (wug != null && (!wug.isParent()) && wug.isPickListable()) {
			if (wug != null && wug.isPickListable()) {
				// assumption is all keys will either be parents or children, not a mix
				MetaLink ml = null;
				OPICMList ol = null; 
				Vector eiToLinkVct = new Vector();
				Hashtable wuiTbl = new Hashtable();
				try { 
					long curtime = System.currentTimeMillis();
					// loop on all keys here
					for (int k=0; k<_strKeys.length; k++){
						String strKey = _strKeys[k];
						WhereUsedItem wui = wug.getWhereUsedItem(strKey);
						if (wui != null) {
							EntityItem eiToLink = wui.getOriginalEntityItem();
							wuiTbl.put(eiToLink.getKey(), wui);
							int direction = MetaLinkGroup.DOWN;
							if (wug.isParent()) { 
								direction = MetaLinkGroup.UP;
							}
							if (ml==null){
								ml = getParentEntityGroup().getMetaLinkGroup().getMetaLink(direction, wug.getEntityType());   
							}
							if (ml == null) {     
								if(eie==null){
									eie = new EntityItemException();
								}
								eie.add(new EntityItem(null, _prof, eiToLink.getEntityType(), eiToLink.getEntityID()), "Unable to link because no MetaLink Object can be found in the Where Used List for " + wug.getEntityType());
								continue;                                                                                                                                          
							}

							eiToLinkVct.add(eiToLink);
							// get VELockERLists
							getVELockERLists(_db, _rdi, _prof,  VELockERListTbl,eiToLink.getEntityType(),_aeiChild[0].getEntityType());
						}
					}// end of key loop

					if (eiToLinkVct.size()>0){
						//IN4311970:  Avails linked to incorrect product structures
						// make sure items to link are in the metalink
						for (int j = 0; j < _aeiChild.length; j++) {                                                                                                                          
							EntityItem linkitem = _aeiChild[j]; 
							if(linkitem.getEntityType().equals(ml.getEntity1Type()) ||
									linkitem.getEntityType().equals(ml.getEntity2Type())){
								continue;
							}
							if(eie==null){
								eie = new EntityItemException();
							}
							eie.add(new EntityItem(null, _prof, linkitem.getEntityType(), 
									linkitem.getEntityID()), 
									"Unable to link because EntityType did not match MetaLink types: "+ml.getEntity1Type()+" or "+
											ml.getEntity2Type());
					
							eiToLinkVct.clear(); // stop the link
						}
					}
					if (eiToLinkVct.size()>0){
						long curtime1 = System.currentTimeMillis();
						D.ebug(D.EBUG_DETAIL,"WhereUsedList.linkMultiple time to getVELockERLists["+i+"] "+Stopwatch.format(curtime1-curtime));

						EntityItem eia[] = new EntityItem[eiToLinkVct.size()+_aeiChild.length];
						eiToLinkVct.copyInto(eia);
						// add children
						int eiToLinkCnt = eiToLinkVct.size();
						for (int j = 0; j < _aeiChild.length; j++) {                                                                                                                          
							eia[j+eiToLinkCnt] = _aeiChild[j]; 
						}

						// check for all locks at once
						String hasLocks[][]= null;
						//get all owners at once
						if (_db!=null){
							hasLocks = _db.checkVELockOwners(_prof, eia);
						}else{
							hasLocks = _rdi.checkVELockOwners(_prof, eia);
						}

						Vector validToLinkVct = new Vector();
						Vector v = new Vector(); 
						// check for locks at one time
						for (int b=0; b<hasLocks.length; b++) { 
							if (b<eiToLinkCnt){
								//if the relator is VE locked, we can't  link
								VELockERList pList = (VELockERList)VELockERListTbl.get(eia[b].getEntityType());

								String eType = null;
								String relType = null;
								if (wug.isParent()){
									eType = wug.getEntityType();
									relType = eia[b].getEntityType();
								}else{
									eType = eia[b].getEntityType();
									relType = wug.getEntityType();
								}

								if (hasLocks[b].length > 0 && (!isLinkable(pList, hasLocks[b], eType, relType))) {  
									if(eie==null){
										eie = new EntityItemException();
									}
									eie.add(new EntityItem(null, _prof, eia[b].getEntityType(), eia[b].getEntityID()), 
											"Unable to link because the entity is VE locked (ok)");        
									continue;  
								}  
								validToLinkVct.add(eia[b]);
							}else{ // checking children
								//test second entity's VELock
								VELockERList cList = (VELockERList)VELockERListTbl.get(eia[b].getEntityType());

								if (hasLocks[b].length > 0 && 
										(!isLinkable(cList, hasLocks[b], eia[b].getEntityType(), wug.getEntityType()))) {
									if(eie==null){
										eie = new EntityItemException();
									}
									eie.add(new EntityItem(null, _prof, eia[b].getEntityType(), eia[b].getEntityID()), "Unable to link because the entity is VE locked (ok)");            
									continue;                                                                                                                                       
								} else {                                                                                                                                            
									v.addElement(eia[b]);  
								}  
							}
						}// end haslocks loop

						curtime = System.currentTimeMillis();
						D.ebug(D.EBUG_DETAIL,"WhereUsedList.linkMultiple time to checkVELockOwners["+i+"] "+Stopwatch.format(curtime-curtime1));
						if (validToLinkVct.size()>0 &&v.size()>0){	
							EntityItem[] aeiLink = new EntityItem[validToLinkVct.size()];   
							validToLinkVct.copyInto(aeiLink);
							EntityItem[] aei = new EntityItem[v.size()];                                                                                                            
							v.copyInto(aei);                                                                                                                                        

							if (_rdi != null) { 
								if (wug.isParent()){
									ol = EANUtility.linkEntityItems(_rdi, _prof, _strLinkOption, aei,aeiLink, ml, EANUtility.LINK_DEFAULT, 1, true);
								}else{
									ol = EANUtility.linkEntityItems(_rdi, _prof, _strLinkOption, aeiLink, aei, ml, EANUtility.LINK_DEFAULT, 1, true);    							
								}
							} else {  
								if (wug.isParent()){
									ol = EANUtility.linkEntityItems(_db, _prof, _strLinkOption, aei,aeiLink, ml, EANUtility.LINK_DEFAULT, 1, true);
								}else{
									ol = EANUtility.linkEntityItems(_db, _prof, _strLinkOption, aeiLink, aei, ml, EANUtility.LINK_DEFAULT, 1, true);
								}
							}

							curtime1 = System.currentTimeMillis();
							D.ebug(D.EBUG_DETAIL,"WhereUsedList.linkMultiple time to do link["+i+"] "+Stopwatch.format(curtime1-curtime));
							
							Hashtable relGrpTbl = new Hashtable();
							for (int j = 0; j < ol.size(); j++) {                                  
								ReturnRelatorKey rrk = (ReturnRelatorKey) ol.getAt(j);             
								boolean bposted = rrk.isPosted();                                  
								boolean bactive = rrk.isActive();                                  

								if (bposted && bactive) {    
									EntityItem eiRelated = null;  
									try { 
										WhereUsedItem wui = null;
										for (int x = 0; x < aei.length; x++) {                       
											EntityItem ei = aei[x]; 
											if (wug.isParent()) {
												if (ei.getKey().equals(rrk.getEntity1Type() + rrk.getEntity1ID())) {
													eiRelated = ei;   
													wui = (WhereUsedItem)wuiTbl.get(rrk.getEntity2Type() + rrk.getEntity2ID());
												}                
											}else{
												if (ei.getKey().equals(rrk.getEntity2Type() + rrk.getEntity2ID())) {  
													eiRelated = ei;  
													wui = (WhereUsedItem)wuiTbl.get(rrk.getEntity1Type() + rrk.getEntity1ID());
												}    
											}
										}           
										
										if (eiRelated != null) {           								
											// add the newly created relator                                      
											EntityItem eiRelator = new EntityItem(wug.getEntityGroup(), _prof, rrk.getEntityType(), rrk.getReturnID());  
											// clone the eiRelated, parent elist may be dereferenced
											EntityGroup relatedGrp = null;
											if(this.getEntityList()!=null){
												relatedGrp = this.getEntityList().getEntityGroup(eiRelated.getEntityType());
											}
											if(relatedGrp ==null){
												relatedGrp = (EntityGroup)relGrpTbl.get(eiRelated.getEntityType());
											}
											if(relatedGrp ==null){
												if (_rdi != null){ // use rdi
													relatedGrp = new EntityGroup(null, _rdi, _prof, eiRelated.getEntityType(), "Navigate");
												}else{// use db
													relatedGrp = new EntityGroup(null, _db, _prof, eiRelated.getEntityType(), "Navigate");
												}
												relGrpTbl.put(eiRelated.getEntityType(),relatedGrp);
											}
											EntityItem eiRelatedClone = new EntityItem(relatedGrp,eiRelated); 
											relatedGrp.putEntityItem(eiRelatedClone);

											// restore up and dn links for qualified relator name in wu table
											if(relatedGrp.isRelator()){
												//find original entityitem and duplicate the up and dn links
												EntityItem origRelator=null;
												for (int r=0;r<v.size();r++){
													EntityItem ei = (EntityItem)v.elementAt(r);
													if(ei.getKey().equals(eiRelated.getKey())){
														origRelator = ei;
														break;
													}
												}
												if(origRelator==null){
													for (int r=0;r<validToLinkVct.size();r++){
														EntityItem ei = (EntityItem)validToLinkVct.elementAt(r);
														if(ei.getKey().equals(eiRelated.getKey())){
															origRelator = ei;
															break;
														}
													}
												}
												if(origRelator !=null){
													// restore any up and down links so wu table has correct info
													Vector links = origRelator.getUpLink();
													for(int x=0;x<links.size();x++){
														EntityItem linkeditem = (EntityItem)links.elementAt(x);
														// clone the link, parent elist may be dereferenced
														EntityGroup grp = null;
														if(this.getEntityList()!=null){
															grp = this.getEntityList().getEntityGroup(linkeditem.getEntityType());
														}
														if(grp ==null){
															grp = (EntityGroup)relGrpTbl.get(linkeditem.getEntityType());
														}
														
														if(grp ==null){
															if (_rdi != null){ // use rdi
																grp = new EntityGroup(null, _rdi, _prof, linkeditem.getEntityType(), "Navigate");
															}else{// use db
																grp = new EntityGroup(null, _db, _prof, linkeditem.getEntityType(), "Navigate");
															}
															relGrpTbl.put(linkeditem.getEntityType(),grp);
														}
														EntityItem clone = new EntityItem(grp,linkeditem); 
														grp.putEntityItem(clone);														
														eiRelatedClone.putUpLink(clone);
													}
													links = origRelator.getDownLink();
													for(int x=0;x<links.size();x++){
														EntityItem linkeditem = (EntityItem)links.elementAt(x);
														// clone the link, parent elist may be dereferenced
														EntityGroup grp = null;
														if(this.getEntityList()!=null){
															grp = this.getEntityList().getEntityGroup(linkeditem.getEntityType());
														}
														if(grp ==null){
															grp = (EntityGroup)relGrpTbl.get(linkeditem.getEntityType());
														}
														if(grp ==null){
															if (_rdi != null){ // use rdi
																grp = new EntityGroup(null, _rdi, _prof, linkeditem.getEntityType(), "Navigate");
															}else{// use db
																grp = new EntityGroup(null, _db, _prof, linkeditem.getEntityType(), "Navigate");
															}
															relGrpTbl.put(linkeditem.getEntityType(),grp);
														}
														EntityItem clone = new EntityItem(grp,linkeditem); 
														grp.putEntityItem(clone);
														eiRelatedClone.putDownLink(clone);
													}
												}
											}
											
											WhereUsedItem wuiReturn = new WhereUsedItem(wug, _prof, wui.getOriginalEntityItem(), eiRelatedClone, eiRelator, wug.getDirectionString()); 
											wug.putWhereUsedItem(wuiReturn);                    
											wuiVector.addElement(wuiReturn);   
											
											// remove dummy WhereUsedItem
											EntityItem ei = wui.getRelatorEntityItem();     
											if (ei.getEntityID() < 0) {                     
												wug.removeWhereUsedItem(wui);               
											}                                               
										}                                                   
									} catch (Exception x) {                                 
										x.printStackTrace(); 
										if(eie==null){
											eie = new EntityItemException();
										}
										eie.add(eiRelated, x.getMessage());// tell caller something failed
									}                                                      
								}                                                           
							}   // end ol loop 

							relGrpTbl.clear();
							relGrpTbl = null;
						} // end something to link found
					} // eiToLinkVct exist
				}catch(MiddlewareBusinessRuleException mbre){
					// tell caller something failed
					if(eie==null){
						eie = new EntityItemException();
					}
					eie.add((EANFoundation)mbre.m_vctFailures.firstElement(), mbre.toString());
				} catch (Exception x) {
					EntityItem bogus = null;
					if(eiToLinkVct.size()>0){
						bogus = (EntityItem)eiToLinkVct.firstElement();
					}else{
						try{
							bogus =	new EntityItem(null, _prof, "", 0);  
						}catch(Exception ee){}
					}
					x.printStackTrace();
					if(eie==null){
						eie = new EntityItemException();
					}
					eie.add(bogus, x.getMessage()); // tell caller something failed
				}

				eiToLinkVct.clear();
			} // end wug.ispicklistable
		} // end wug loop

		// convert vector to array
		int size = wuiVector.size();
		WhereUsedItem wuiArray[] = new WhereUsedItem[size];
		wuiVector.copyInto(wuiArray);

		// release memory
		Enumeration e = VELockERListTbl.elements();
		while (e.hasMoreElements()) {
			VELockERList uag = (VELockERList) e.nextElement();
			uag.dereference();
		}
		VELockERListTbl.clear();
		D.ebug(D.EBUG_DETAIL, "WhereUsedList.linkMultiple total time to link "+Stopwatch.format(System.currentTimeMillis()-starttime));
		if (eie != null && eie.getErrorCount() > 0) {
			throw eie;
		}

		return wuiArray;
	}

	/**
	 * try to find the linkaction for the domain check RQ0713072645
	 *
	 *@param  item       EntityItem to be linked
	 *@param  aei         EntityItem[] to be linked, only need info from one
	 *@return  LinkActionItem
	 * /
    private LinkActionItem findLinkAction(EntityItem item, EntityItem[] aei)
    {
        LinkActionItem lai = null;
        EntityGroup eg = item.getEntityGroup();
        if (eg != null) {
            ActionGroup ag = eg.getActionGroup();
            if (ag != null) {
                for (int i2 = 0; i2 < ag.getActionItemCount(); ++i2) {
                    EANActionItem actitem = ag.getActionItem(i2);
                    if (actitem instanceof LinkActionItem) {
                        lai = (LinkActionItem) actitem;
                        break;
                    }
                }
            }
        }
        if (lai ==null && aei !=null){
            eg = aei[0].getEntityGroup();
            if (eg != null) {
                ActionGroup ag = eg.getActionGroup();
                if (ag != null) {
                    for (int i2 = 0; i2 < ag.getActionItemCount(); ++i2) {
                        EANActionItem actitem = ag.getActionItem(i2);
                        if (actitem instanceof LinkActionItem) {
                            lai = (LinkActionItem) actitem;
                            break;
                        }
                    }
                }
            }
        }
        return lai;
    }*/

	/**
	 *  Description of the Method
	 *
	 *@param  _db                           Description of the Parameter
	 *@param  _rdi                          Description of the Parameter
	 *@param  _prof                         Description of the Parameter
	 *@param  _strKey                       Description of the Parameter
	 *@return                               Description of the Return Value
	 *@exception  EANBusinessRuleException  Description of the Exception
	 * /
  public boolean removeLinkOrig(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey)
  throws EANBusinessRuleException {

    if (_db == null && _rdi == null) {
      return false;
    }

    EntityItemException eie = new EntityItemException();
    boolean bResult = false;
    try {
      for (int i = 0; i < getWhereUsedGroupCount(); i++) {
        WhereUsedGroup wug = getWhereUsedGroup(i);
//        if (wug != null & (!wug.isParent())) {
        if (wug != null) {
          WhereUsedItem wui = wug.getWhereUsedItem(_strKey);
          if (wui != null) {
            EntityItem eiRelator = wui.getRelatorEntityItem();

            // hack to find deleteaction item RQ0713072645
            DeleteActionItem dai = null;
            Vector actVct = (Vector)actionTbl.get(eiRelator.getEntityType());
            if (actVct !=null){
                for (int ii=0; ii<actVct.size(); ii++){
                    EANActionItem item = (EANActionItem)actVct.elementAt(ii);
                    if (item instanceof DeleteActionItem) {
                        dai = (DeleteActionItem) item;
                        break;
                    }
                }
            }

            EntityList.checkDomain(_prof,dai,eiRelator); //RQ0713072645

            String[] aVELock = null;
            if (_db != null) {
              aVELock = _db.getVELockOwners(_prof, eiRelator);
            } else {
              aVELock = _rdi.getVELockOwners(_prof, eiRelator);
            }

            //if the relator is VE locked, we can't remove the link
            if (aVELock.length > 0) {
              eie.add(eiRelator, "Unable to remove because the entity is VE locked (ok)");
            }

            if (eiRelator.getEntityID() > 0) {

              Vector vctReturnRelatorKeys = wui.generateLinkRelators(false);

              OPICMList ol = null;
              //try {
                if (_db != null) {
                  ol = _db.link(getProfile(), vctReturnRelatorKeys, "", EANUtility.LINK_DEFAULT, 1, true);
                } else {
                  ol = _rdi.link(getProfile(), vctReturnRelatorKeys, "", EANUtility.LINK_DEFAULT, 1, true);
                }

                // update WhereUsedGroup
                for (int j = 0; j < ol.size(); j++) {
                  ReturnRelatorKey rrk = (ReturnRelatorKey) ol.getAt(j);
                  EntityItem relatorEI = wui.getRelatorEntityItem();

                  if (relatorEI.getKey().equals(rrk.hashkey())) {
                    boolean bposted = rrk.isPosted();
                    boolean bactive = rrk.isActive();

                    if (bposted && !bactive) {
                      wug.removeWhereUsedItem(wui);

                      // for removing wui with the same relator and parent direction
                      int iChild = _strKey.indexOf(WhereUsedGroup.CHILD);
                      if (iChild > 0) {
                        removeLink(_strKey.substring(0, iChild) + WhereUsedGroup.PARENT);
                      }

                      if (!bResult) {
                        bResult = true;
                      }
                    } else if (!bposted && !bactive) {
                      eie.add(new EntityItem(eiRelator), " Unable to remove this relationship because it creates an orphan");
                    }
                  }
                }
              //} catch (Exception x) {
              //  x.printStackTrace();
              //}
            }
          }

          wug.createDummy();
        }
      }
    } catch (MiddlewareBusinessRuleException _ex) {
        for (int i=0; i < _ex.m_vctFailures.size(); i++) {
            Object o = _ex.m_vctFailures.elementAt(i);
            if (o instanceof EntityItem) {
                eie.add((EntityItem)o, (String) _ex.m_vctMessages.elementAt(i));
            }
        }
    } catch(DomainException de){ //RQ0713072645
        for (int i2=0; i2<de.getErrorCount(); i2++){
            eie.add(de.getItem(i2), de.getMessage(i2));
        }
        de.dereference();
    }catch (Exception ex) {
      ex.printStackTrace();
    }

    if (eie.getErrorCount() > 0) {
      throw eie;
    }

    return bResult;
  }*/
	/**
	 *  Description of the Method
	 *
	 *@param  _db                           Description of the Parameter
	 *@param  _rdi                          Description of the Parameter
	 *@param  _prof                         Description of the Parameter
	 *@param  _strKey                       Description of the Parameter
	 *@return                               Description of the Return Value
	 *@exception  EANBusinessRuleException  Description of the Exception
	 */
	public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey)
	throws EANBusinessRuleException {

		if (_db == null && _rdi == null) {
			return false;
		}

		EntityItemException eie = new EntityItemException();
		boolean bResult = false;
		try {
			for (int i = 0; i < getWhereUsedGroupCount(); i++) {
				WhereUsedGroup wug = getWhereUsedGroup(i);
				if (wug != null) {
					WhereUsedItem wui = wug.getWhereUsedItem(_strKey);
					if (wui != null) {
						EntityItem eiRelator = wui.getRelatorEntityItem();
						// hack to find deleteaction item RQ0713072645
						DeleteActionItem dai = null;
						Vector actVct = (Vector)actionTbl.get(eiRelator.getEntityType());
						if (actVct !=null){
							for (int ii=0; ii<actVct.size(); ii++){
								EANActionItem item = (EANActionItem)actVct.elementAt(ii);
								if (item instanceof DeleteActionItem) {
									dai = (DeleteActionItem) item;
									break;
								}
							}
						}
						//  this should never happen
						if (dai==null) {
							eie.add(eiRelator, "Unable to remove link because the delete action could not be found");
							continue;
						}
						EntityList.checkDomain(_prof,dai,eiRelator); //RQ0713072645

						String[] aVELock = null;
						if (_db != null) {
							aVELock = _db.getVELockOwners(_prof, eiRelator);
						} else {
							aVELock = _rdi.getVELockOwners(_prof, eiRelator);
						}

						//if the relator is VE locked, we can't remove the link
						if (aVELock.length > 0) {
							eie.add(eiRelator, "Unable to remove because the entity is VE locked (ok)");
							continue;
						}

						if (eiRelator.getEntityID() > 0) {
							Vector vctReturnRelatorKeys = wui.generateLinkRelators(false);
							OPICMList ol = null;
							// this only expires the relator table entries
							if (_db != null) {
								ol = _db.link(getProfile(), vctReturnRelatorKeys, "", EANUtility.LINK_DEFAULT, 1, true);
							} else {
								ol = _rdi.link(getProfile(), vctReturnRelatorKeys, "", EANUtility.LINK_DEFAULT, 1, true);
							}

							// update WhereUsedGroup
							for (int j = 0; j < ol.size(); j++) {
								ReturnRelatorKey rrk = (ReturnRelatorKey) ol.getAt(j);
								EntityItem relatorEI = wui.getRelatorEntityItem();

								if (relatorEI.getKey().equals(rrk.hashkey())) {
									boolean bposted = rrk.isPosted();
									boolean bactive = rrk.isActive();

									if (bposted && !bactive) {
										wug.removeWhereUsedItem(wui);

										// for removing wui with the same relator and parent direction
										int iChild = _strKey.indexOf(WhereUsedGroup.CHILD);
										if (iChild > 0) {
											removeLink(_strKey.substring(0, iChild) + WhereUsedGroup.PARENT);
										}

										// send this thru the deleteaction to make sure both the entity and relator tables are expired
										// previous code does other checks so leave it too
										try{
											doDeleteAction(_db, _rdi, _prof, new EntityItem[] {eiRelator}, dai);//MN35080480
											bResult = true;
										}catch(Exception exc){
											//eie.add(new EntityItem(eiRelator), exc.getMessage());
											eie.add(new EntityItem(null, _prof, eiRelator.getEntityType(), eiRelator.getEntityID()), exc.getMessage());
										}
									} else if (!bposted && !bactive) {
										//eie.add(new EntityItem(eiRelator), " Unable to remove this relationship because it creates an orphan");
										eie.add(new EntityItem(null, _prof, eiRelator.getEntityType(), eiRelator.getEntityID())," Unable to remove this relationship because it creates an orphan");
									}
								}
							}
						}
					}

					wug.createDummy();
				} // end wug!=null
			} // end for loop
		} catch (MiddlewareBusinessRuleException _ex) {
			for (int i=0; i < _ex.m_vctFailures.size(); i++) {
				Object o = _ex.m_vctFailures.elementAt(i);
				if (o instanceof EntityItem) {
					eie.add((EntityItem)o, (String) _ex.m_vctMessages.elementAt(i));
				}
			}
		} catch(DomainException de){ //RQ0713072645
			for (int i2=0; i2<de.getErrorCount(); i2++){
				eie.add(de.getItem(i2), de.getMessage(i2));
			}
			de.dereference();
		}catch (Exception ex) {
			ex.printStackTrace();
		}

		if (eie.getErrorCount() > 0) {
			throw eie;
		}

		return bResult;
	}
	/**
	 * Group all unlinks together to improve performance
	 * @param _db
	 * @param _rdi
	 * @param _prof
	 * @param _strKeyArray
	 * @return
	 * @throws EANBusinessRuleException
	 */
	public boolean removeLinks(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _strKeyArray)
	throws EANBusinessRuleException 
	{

		if (_db == null && _rdi == null) {
			return false;
		}

		EntityItemException eie = new EntityItemException();
		boolean bResult = false;
		long starttime = System.currentTimeMillis();

		try {	
			for (int i = 0; i < getWhereUsedGroupCount(); i++) {
				WhereUsedGroup wug = getWhereUsedGroup(i);
				if (wug != null) {
					//System.err.println("WhereUsedList.removeLinks wug["+i+"] "+wug);
					Vector linkVct = new Vector();
					Hashtable wuiTbl = new Hashtable();
					Vector relatorVct = new Vector();
					// hack to find deleteaction item RQ0713072645
					DeleteActionItem dai = null;

					for (int k=0; k<_strKeyArray.length; k++){
						String _strKey = _strKeyArray[k];
						//System.err.println("WhereUsedList.removeLinks _strKey["+k+"] "+_strKey);
						WhereUsedItem wui = wug.getWhereUsedItem(_strKey);
						if (wui != null) {
							EntityItem eiRelator = wui.getRelatorEntityItem();
							//System.err.println("WhereUsedList.removeLinks eiRelator "+eiRelator.getKey());
							if (eiRelator.getEntityID() <= 0) {
								continue;
							}

							if (dai==null){ 
								Vector actVct = (Vector)actionTbl.get(eiRelator.getEntityType());
								if (actVct !=null){
									for (int ii=0; ii<actVct.size(); ii++){
										EANActionItem item = (EANActionItem)actVct.elementAt(ii);
										if (item instanceof DeleteActionItem) {
											dai = (DeleteActionItem) item;
											break;
										}
									}
								}else{ // try to get the actions
									// actionTbl only has actions if getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey)
									// was called, if multiple types are selected this may not have been called 
									getActionItemsAsArray(_db, _rdi,_prof, _strKey);
									actVct = (Vector)actionTbl.get(eiRelator.getEntityType());
									if (actVct !=null){
										for (int ii=0; ii<actVct.size(); ii++){
											EANActionItem item = (EANActionItem)actVct.elementAt(ii);
											if (item instanceof DeleteActionItem) {
												dai = (DeleteActionItem) item;
												break;
											}
										}
									}
								}
							}
							//  this should never happen
							if (dai==null) {
								eie.add(eiRelator, "Unable to remove link because the delete action could not be found");
								continue;
							}
							EntityList.checkDomain(_prof,dai,eiRelator); //RQ0713072645
							relatorVct.add(eiRelator);
							wuiTbl.put(eiRelator.getKey(), wui);
						}
					}// end get relator loop for each strkey

					if (relatorVct.size()>0){
						long curtime = System.currentTimeMillis();

						EntityItem eia[] = new EntityItem[relatorVct.size()];
						relatorVct.copyInto(eia);
						String hasLocks[][]= null;
						//get all owners at once
						if (_db!=null){
							hasLocks = _db.checkVELockOwners(_prof, eia);
						}else{
							hasLocks = _rdi.checkVELockOwners(_prof, eia);
						}
						long curtime2 = System.currentTimeMillis();
						D.ebug(D.EBUG_DETAIL,"WhereUsedList.removeLinks time to getlockowners "+Stopwatch.format(curtime2-curtime));
						Vector vctReturnRelatorKeys = new Vector();
						// link all at one time too
						for (int b=0; b<hasLocks.length; b++) { 					
							//if the relator is VE locked, we can't remove the link
							if (hasLocks[b].length>0) {
								eie.add(eia[b], "Unable to remove because the entity is VE locked (ok)");
								continue;
							}
							WhereUsedItem wui = (WhereUsedItem)wuiTbl.get(eia[b].getKey());
							Vector vctReturnRelatorKeysTmp = wui.generateLinkRelators(false);
							vctReturnRelatorKeys.addAll(vctReturnRelatorKeysTmp);
						}

						if (vctReturnRelatorKeys.size()>0){
							curtime2 = System.currentTimeMillis();
							OPICMList ol = null;
							// this only expires the relator table entries
							if (_db != null) {
								ol = _db.link(getProfile(), vctReturnRelatorKeys, "", EANUtility.LINK_DEFAULT, 1, true);
							} else {
								ol = _rdi.link(getProfile(), vctReturnRelatorKeys, "", EANUtility.LINK_DEFAULT, 1, true);
							}
							D.ebug(D.EBUG_DETAIL,"WhereUsedList.removeLinks time to unlink "+Stopwatch.format(System.currentTimeMillis()-curtime2));

							// update WhereUsedGroup
							for (int j = 0; j < ol.size(); j++) {
								ReturnRelatorKey rrk = (ReturnRelatorKey) ol.getAt(j);
								WhereUsedItem wui = (WhereUsedItem)wuiTbl.get(rrk.hashkey());
								EntityItem relatorEI = wui.getRelatorEntityItem();

								boolean bposted = rrk.isPosted();
								boolean bactive = rrk.isActive();

								if (bposted && !bactive) {
									wug.removeWhereUsedItem(wui);

									// for removing wui with the same relator and parent direction
									int iChild = wui.getKey().indexOf(WhereUsedGroup.CHILD);
									if (iChild > 0) {
										removeLink(wui.getKey().substring(0, iChild) + WhereUsedGroup.PARENT);
									}
									linkVct.add(relatorEI);

								} else if (!bposted && !bactive) {
									//eie.add(new EntityItem(relatorEI), " Unable to remove this relationship because it creates an orphan");
									eie.add(new EntityItem(null, _prof, relatorEI.getEntityType(), relatorEI.getEntityID())," Unable to remove this relationship because it creates an orphan");									
								}
							}// end ol loop

							// send this thru the deleteaction to make sure both the entity and relator tables are expired
							// previous code does other checks so leave it too
							if (linkVct.size()>0){
								curtime2=System.currentTimeMillis();
								EntityItem[] relArray = new EntityItem[linkVct.size()];
								linkVct.copyInto(relArray);
								try{
									doDeleteAction(_db, _rdi, _prof, relArray, dai);//MN35080480
									bResult = true;
								}catch(Exception exc){
									for(int r=0; r<relArray.length; r++){
										//eie.add(new EntityItem(relArray[r]), exc.getMessage());
										eie.add(new EntityItem(null, _prof, relArray[r].getEntityType(), relArray[r].getEntityID()), exc.getMessage());
									}
								}
								D.ebug(D.EBUG_DETAIL,"WhereUsedList.removeLinks time to do all delete "+Stopwatch.format(System.currentTimeMillis()-curtime2));
							} // unlinks done
							relatorVct.clear();
							vctReturnRelatorKeys.clear();
						}
					} // end relators found
					wuiTbl.clear();
					linkVct.clear();

					wug.createDummy();
				} // end wug!=null
			} // end for loop
		} catch (MiddlewareBusinessRuleException _ex) {
			for (int i=0; i < _ex.m_vctFailures.size(); i++) {
				Object o = _ex.m_vctFailures.elementAt(i);
				if (o instanceof EntityItem) {
					eie.add((EntityItem)o, (String) _ex.m_vctMessages.elementAt(i));
				}
			}
		} catch(DomainException de){ //RQ0713072645
			for (int i2=0; i2<de.getErrorCount(); i2++){
				eie.add(de.getItem(i2), de.getMessage(i2));
			}
			de.dereference();
		}catch (Exception ex) {
			ex.printStackTrace();
		}

		if (eie.getErrorCount() > 0) {
			throw eie;
		}

		D.ebug(D.EBUG_DETAIL,"WhereUsedList.removelinks total time "+Stopwatch.format(System.currentTimeMillis()-starttime));
		return bResult;
	}
	/******************************************************************************
	 * Deactivate this entity/relator
	 * @param relator EntityItem to deactivate
	 */
	private static void doDeleteAction(Database _db, RemoteDatabaseInterface _rdi, Profile profile,
			EntityItem[] relator, DeleteActionItem deleteActionItem )  throws
			COM.ibm.opicmpdh.middleware.LockException,
			COM.ibm.opicmpdh.middleware.MiddlewareException,
			COM.ibm.eannounce.objects.EANBusinessRuleException,
			COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
			java.sql.SQLException,
			java.rmi.RemoteException
			{
		if(_db != null){
			deleteActionItem.setEntityItems(relator);
			deleteActionItem.executeAction(_db, profile);
		}else{
			deleteActionItem.setEntityItems(relator);
			_rdi.executeAction(profile, deleteActionItem) ;
		}
			}

	/**
	 *  Description of the Method
	 *
	 *@param  _strKey  Description of the Parameter
	 */
	private void removeLink(String _strKey) {
		for (int i = 0; i < getWhereUsedGroupCount(); i++) {
			WhereUsedGroup wug = getWhereUsedGroup(i);
			WhereUsedItem wui = wug.getWhereUsedItem(_strKey);
			if (wui != null) {
				wug.removeWhereUsedItem(wui);
			}

			// need to create some dummy if all WUIs removed
			wug.createDummy();
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  _db      Description of the Parameter
	 *@param  _rdi     Description of the Parameter
	 *@param  _prof    Description of the Parameter
	 *@param  _strKey  Description of the Parameter
	 *@return          Description of the Return Value
	 */
	public EntityList create(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) {
		EntityList elReturn = null;
		try {
			elReturn = create2(_db,  _rdi, _prof, _strKey);
		} catch (Exception x) {
			x.printStackTrace();
		}

		return elReturn;
	}

	// need to capture create domain msg RQ0713072645
	public EntityList create2(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey)
	throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException
	{

		if (_db == null && _rdi == null) {
			return null;
		}
		if (m_wuai == null) {
			return null;
		}
		EntityList elReturn = null;

		// get strRelatorType
		String strRelatorType = null;
		EntityItem aeiParent[] = new EntityItem[1];
		for (int i = 0; i < getWhereUsedGroupCount(); i++) {
			WhereUsedGroup wug = getWhereUsedGroup(i);
			WhereUsedItem wui = wug.getWhereUsedItem(_strKey);
			if (wui != null) {
				strRelatorType = wug.getEntityType();
				aeiParent[0] = wui.getOriginalEntityItem();
			}
		}

		if (strRelatorType != null) {

			// hack to find createaction item RQ0713072645
			CreateActionItem cai = null;
			Vector actVct = (Vector)actionTbl.get(strRelatorType);
			if (actVct !=null){
				for (int ii=0; ii<actVct.size(); ii++){
					EANActionItem item = (EANActionItem)actVct.elementAt(ii);
					if (item instanceof CreateActionItem) {
						cai = (CreateActionItem) item;
						break;
					}
				}
			}

			EntityList.checkDomain(_prof,cai,aeiParent); //RQ0713072645

			m_wuai.setEntityItems(aeiParent);
			if (_db != null) {
				elReturn = m_wuai.create(_db, _prof, strRelatorType);
			} else {
				elReturn = m_wuai.rcreate(_rdi, _prof, strRelatorType);
			}
		}

		return elReturn;
	}

	/**
	 *  Gets the whereUsedList attribute of the WhereUsedList object
	 *
	 *@param  _db      Description of the Parameter
	 *@param  _rdi     Description of the Parameter
	 *@param  _prof    Description of the Parameter
	 *@param  _strKey  Description of the Parameter
	 *@return          The whereUsedList value
	 */
	public WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException,RemoteException {
		if (_db == null && _rdi == null) {
			return null;
		}
		if (m_wuai == null) {
			return null;
		}
		WhereUsedList wulReturn = null;

		// get strRelatorType
		String strRelatorType = null;
		EntityItem aei[] = new EntityItem[1];
		for (int i = 0; i < getWhereUsedGroupCount(); i++) {
			WhereUsedGroup wug = getWhereUsedGroup(i);
			WhereUsedItem wui = wug.getWhereUsedItem(_strKey);
			if (wui != null) {
				strRelatorType = wug.getEntityType();
				EntityItem ei = wui.getRelatedEntityItem();
				if (ei.getEntityID() < 0) {
					return null;
				}
				aei[0] = ei;
			}
		}

		if (strRelatorType != null) {
			WhereUsedActionItem wuai = m_wuai.getWhereUsedActionItem(_db, _rdi, _prof, strRelatorType);
			if (wuai != null) {
				wuai.setEntityItems(aei);
				//try {
					if (_db != null) {
						wulReturn = wuai.exec(_db, _prof);
					} else {
						wulReturn = wuai.rexec(_rdi, _prof);
					}
					//} catch (Exception x) { RQ0713072645 must allow exceptions to go back
					//  x.printStackTrace();
					//}
			}
		}

		return wulReturn;
	}

	public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException,RemoteException {
		return edit(_db, _rdi, _prof, _astrKey, null);
	}

	// need to capture edit domain msg RQ0713072645
	public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey, StringBuffer errSb)
	throws SQLException, MiddlewareException, MiddlewareRequestException,
	MiddlewareShutdownInProgressException,RemoteException
	{
		if (_db == null && _rdi == null) {
			return null;
		}
		if (m_wuai == null) {
			return null;
		}
		EntityList elReturn = null;

		// get strRelatorType
		String strRelatorType = null;
		boolean bFirst = true;

		Vector v = new Vector();

		for (int j=0; j < _astrKey.length; j++) {
			String strKey = _astrKey[j];
			for (int i = 0; i < getWhereUsedGroupCount(); i++) {
				WhereUsedGroup wug = getWhereUsedGroup(i);
				WhereUsedItem wui = wug.getWhereUsedItem(strKey);
				if (wui != null) {
					if (bFirst) {
						strRelatorType = wug.getEntityType().trim();
						bFirst = false;
					} else {
						T.est(strRelatorType.equals(wug.getEntityType().trim()), "More than one type of entities selected to edit.");
					}

					EntityItem ei = wui.getRelatorEntityItem();
					if (ei.getEntityID() > 0) {
						v.addElement(ei);
					}
				}
			}
		}
		EntityItem aei[] = new EntityItem[v.size()];
		v.toArray(aei);
		if (strRelatorType != null) {
			EditActionItem eai = m_wuai.getEditActionItem(_db, _rdi, _prof, strRelatorType);
			if (eai != null) {
				if (errSb !=null && eai.canEdit()){ // RQ0713072645 check domain
					try{
						EntityList.checkDomain(_prof,eai,aei);
					}catch(DomainException de) { // RQ0713072645
						errSb.append(de.getMessage());
						de.dereference();
					}
				}
				if (_db != null) {
					elReturn =  EntityList.getEntityList(_db, _prof, eai, aei);
				} else {
					elReturn = EntityList.getEntityList(_rdi, _prof, eai, aei);
				}
			}
		}

		return elReturn;
	}

	/**
	 *  Gets the actionItemsAsArray attribute of the WhereUsedList object
	 *
	 *@param  _strKey  Description of the Parameter
	 *@return          The actionItemsAsArray value
	 */
	public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException,RemoteException {
		if (m_wuai == null) {
			return null;
		}
		Object[] aiReturn = null;

		// get strRelatorType
		String strRelatorType = null;
		//boolean bParent = false;
		for (int i = 0; i < getWhereUsedGroupCount(); i++) {
			WhereUsedGroup wug = getWhereUsedGroup(i);
			WhereUsedItem wui = wug.getWhereUsedItem(_strKey);
			if (wui != null) {
				strRelatorType = wug.getEntityType();
				/*if (wug.isParent()) {
          bParent = true;
        }*/
				break;
			}
		}


		if (strRelatorType != null) {
			// i dont know why these actions arent saved or even used, but hang onto them
			// for domain action check if removelink is called
			Vector actVct = (Vector)actionTbl.get(strRelatorType); //RQ0713072645
			if (actVct !=null){
				actVct.clear();  // this was recalled.. not sure why, but remove the old ones for now
			}else{
				actVct = new Vector();
				actionTbl.put(strRelatorType, actVct);
			}
			aiReturn = m_wuai.getActionItemArray(_db, _rdi, _prof, strRelatorType);

			if(aiReturn !=null){
				for (int i=0; i < aiReturn.length; i++) {
					Object ai = aiReturn[i];
					actVct.add(ai);
				}
			}
		}


		// remove deleteActionItem if the group is parent
//		if (bParent && aiReturn != null) {
//		Vector v = new Vector();
//		for (int i=0; i < aiReturn.length; i++) {
//		Object ai = aiReturn[i];
//		if (!(ai instanceof DeleteActionItem)) {
//		v.addElement(ai);
//		}
//		}

//		aiReturn = v.toArray();
//		}
		return aiReturn;
	}


	/**
	 *  Gets the dynaTable attribute of the WhereUsedList object
	 *
	 *@return    The dynaTable value
	 */
	public boolean isDynaTable() {
		return false;
	}


	/**
	 *  Gets the nextRID attribute of the WhereUsedList object
	 *
	 *@return    The nextRID value
	 */
	protected int getNextRID() {
		return m_iRID--;
	}


	/**
	 * look for a match on the action and match of all entityitems
	 *
	 *@param  _eia  Description of the Parameter
	 *@param  _ai   Description of the Parameter
	 *@return      true if the entityItems match and its the same action item
	 */
	public boolean equivalent(EntityItem[] _aei, EANActionItem _ai) {

		if (_aei == null || _aei.length == 0) {
			return false;
		}

		if (!_ai.getActionItemKey().equals(getParentActionItem().getActionItemKey())) {
			return false;
		}

		EntityGroup eg1 = _aei[0].getEntityGroup();
		if (eg1 == null) {
			return false;
		}

		EntityGroup egParent = getParentEntityGroup();
		if (eg1.getKey().equals(egParent.getKey())) {
			for (int y = 0; y < _aei.length; y++) {
				EntityItem ei = egParent.getEntityItem(_aei[y].getKey());
				if (ei == null) {
					return false;
				}
			}
			return true;
		} else if (eg1.isRelator() || eg1.isAssoc()) {
			for (int y = 0; y < _aei.length; y++) {
				// boolean equal = false;
				EntityItem eic = (EntityItem)_aei[y].getDownLink(0);
				EntityItem ei = egParent.getEntityItem(eic.getKey());
				if (ei == null) {
					return false;
				}
			}
			return true;
		}

		return false;
	}
    /**
     * look for a match on the action and any match of entityitem
     * @param _eia
     * @param _ai
     * @return
     */
	public boolean subset(EntityItem[] _aei, EANActionItem _ai) {
       	boolean anymatch = false;
		if (_aei == null || _aei.length == 0) {
			return anymatch;
		}

		if (!_ai.getActionItemKey().equals(getParentActionItem().getActionItemKey())) {
			return anymatch;
		}

		EntityGroup eg1 = _aei[0].getEntityGroup();
		if (eg1 == null) {
			return anymatch;
		}

		EntityGroup egParent = getParentEntityGroup();
		if (eg1.getKey().equals(egParent.getKey())) {
			for (int y = 0; y < _aei.length; y++) {
				EntityItem ei = egParent.getEntityItem(_aei[y].getKey());
				if (ei != null) {
					anymatch = true;
					break;
				}
			}
		} else if (eg1.isRelator() || eg1.isAssoc()) {
			for (int y = 0; y < _aei.length; y++) {
				EntityItem eic = (EntityItem)_aei[y].getDownLink(0);
				EntityItem ei = egParent.getEntityItem(eic.getKey());
				if (ei != null) {
					anymatch = true;
					break;
				}
			}
		}

		return anymatch;
	}
	
	public boolean duplicate(String _strKey, int _iDup) {
		return false;
	}

	public Object linkAndRefresh(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException, RemoteException {
		//String strTraceBase = "WhereUsedList linkAndRefresh ";
		if (_db == null && _rdi == null) {
			return null;
		}

		Object objReturn = null;
		Vector v = new Vector();
		if (_db != null) {
			v = _db.executeLink(_prof, _lai);
		} else {
			v = _rdi.executeLink(_prof, _lai);
		}

		for (int i = 0; i < v.size(); i++) {
			Object o = v.elementAt(i);
			if (o instanceof OPICMList) {
				OPICMList ol = (OPICMList) o;
				for (int j = 0; j < ol.size(); j++) {
					ReturnRelatorKey rrk = (ReturnRelatorKey) ol.getAt(j);
					boolean bposted = rrk.isPosted();
					boolean bactive = rrk.isActive();

					if (bposted && bactive) {
						try {
							String strRelatorType = rrk.getEntityType();
							int iRelatorID = rrk.getReturnID();
							String strEntity1Type = rrk.getEntity1Type();
							int iEntity1ID = rrk.getEntity1ID();
							String strEntity2Type = rrk.getEntity2Type();
							int iEntity2ID = rrk.getEntity2ID();

							WhereUsedGroup wug = getWhereUsedGroup(strRelatorType + WhereUsedGroup.CHILD);
							if (wug != null) {
								EntityGroup egRelated = m_el.getEntityGroup(strEntity2Type);
								EntityItem eiRelated = new EntityItem(null, _prof, strEntity2Type, iEntity2ID);
								if (_db != null) {
									eiRelated = _db.refreshEntityItem(_prof, egRelated, eiRelated);
								} else {
									eiRelated = _rdi.refreshEntityItem(_prof, egRelated, eiRelated);
								}

								EntityItem eiRelator = new EntityItem(wug.getEntityGroup(), _prof, strRelatorType, iRelatorID);

								EntityGroup egParent = m_el.getParentEntityGroup();
								EntityItem eiParent = egParent.getEntityItem(strEntity1Type + iEntity1ID);

								WhereUsedItem wuiReturn = new WhereUsedItem(wug, _prof, eiParent, eiRelated, eiRelator, wug.getDirectionString());
								wug.putWhereUsedItem(wuiReturn);

								// remove dummy WhereUsedItem
								for (int k=0; k < wug.getWhereUsedItemCount(); k++) {
									WhereUsedItem wui = wug.getWhereUsedItem(k);
									EntityItem ei = wui.getRelatorEntityItem();
									if (ei.getEntityID() < 0) {
										wug.removeWhereUsedItem(wui);
									}
								}
							}
						} catch (Exception x) {
							x.printStackTrace();
						}
					}
				}
			} else {
				objReturn = o;
			}
		}

		return objReturn;
	}
}

