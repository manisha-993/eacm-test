//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: WhereUsedActionItem.java,v $
// Revision 1.69  2011/11/10 19:43:19  wendy
// fix dump newline
//
// Revision 1.68  2009/05/20 01:16:04  wendy
// Support dereference for memory release
//
// Revision 1.67  2009/05/13 19:11:06  wendy
// Get WG defaults for domain checks
//
// Revision 1.66  2009/05/11 14:02:34  wendy
// Support turning off domain check for all actions
//
// Revision 1.65  2009/03/12 14:59:14  wendy
// Add methods for metaui access
//
// Revision 1.64  2008/01/31 22:56:01  wendy
// Cleanup RSA warnings
//
// Revision 1.63  2007/08/15 18:07:36  wendy
// Change default to always check domain, allow turning it off
//
// Revision 1.62  2007/08/15 14:36:37  wendy
// RQ0713072645- Enhancement 3
//
// Revision 1.61  2007/08/09 13:48:02  wendy
// RQ0713072645 whereused create check domain
//
// Revision 1.60  2007/08/09 01:21:14  wendy
// Allow exception to get back to UI from getWhereUsedList()
//
// Revision 1.59  2007/08/03 11:25:45  wendy
// RQ0713072645-1 Make actions sensitive to the PROFILE's PDHDOMAINs
//
// Revision 1.58  2006/05/04 17:17:16  tony
// testing of cr042806607
//
// Revision 1.57  2006/05/04 16:08:32  tony
// cr 042806607
//
// Revision 1.56  2005/08/22 20:28:22  tony
// improved keying of objects
//
// Revision 1.55  2005/08/10 16:14:24  tony
// improved catalog viewer functionality.
//
// Revision 1.54  2005/08/03 17:09:45  tony
// added datasource logic for catalog mod
//
// Revision 1.53  2005/01/18 21:46:52  dave
// more parm debug cleanup
//
// Revision 1.52  2005/01/05 19:24:09  joan
// add new method
//
// Revision 1.51  2004/11/03 23:08:33  joan
// work on searh picklist
//
// Revision 1.50  2004/07/15 17:28:59  joan
// add Single input
//
// Revision 1.49  2004/06/23 19:25:06  joan
// work on remove parent links
//
// Revision 1.48  2004/01/13 21:16:11  dave
// more ObjectPooling
//
// Revision 1.47  2004/01/13 01:31:03  dave
// syntax and import
//
// Revision 1.46  2004/01/13 01:24:26  dave
// lets try building the WhereUsedList on this client side
//
// Revision 1.45  2004/01/13 00:52:47  dave
// minor syntax
//
// Revision 1.44  2004/01/13 00:44:50  dave
// trace for how big the WhereUsed guy is
//
// Revision 1.43  2004/01/13 00:01:56  dave
// trace to time where used
//
// Revision 1.42  2004/01/12 20:56:11  dave
// some more syntax
//
// Revision 1.41  2004/01/12 20:43:11  dave
// abstract errors
//
// Revision 1.40  2004/01/12 20:35:03  dave
// fixing buggs #1
//
// Revision 1.39  2004/01/12 20:26:15  dave
// breaking for performance Where Used I
//
// Revision 1.38  2003/10/30 02:17:12  dave
// more profile stuff
//
// Revision 1.37  2003/10/30 00:43:35  dave
// fixing all the profile references
//
// Revision 1.36  2003/09/24 23:28:16  gregg
// more updatePdhMeta() : update for strLinVals in list
//
// Revision 1.35  2003/09/24 22:21:38  gregg
// fic for updatePDHMeta (null in 7504)
//
// Revision 1.34  2003/09/17 01:00:46  gregg
// updatePdhMeta method
//
// Revision 1.33  2003/09/15 22:18:41  dave
// plugging null pointer
//
// Revision 1.32  2003/08/26 21:38:59  dave
// clean up here and there
//
// Revision 1.31  2003/08/22 18:32:57  dave
// Lets try remote procedure calls in contructors
//
// Revision 1.30  2003/08/22 17:10:23  dave
// syntx
//
// Revision 1.29  2003/08/22 16:57:09  dave
// RMI trickery I
//
// Revision 1.28  2003/08/21 22:58:19  dave
// syntax and rmi leak plug
//
// Revision 1.27  2003/08/21 22:31:24  dave
// plugging leak in Where Used and Matrix
//
// Revision 1.26  2003/06/25 23:56:17  joan
// fix bug
//
// Revision 1.25  2003/06/25 18:44:02  joan
// move changes from v111
//
// Revision 1.24.2.2  2003/06/25 00:15:28  joan
// fix compile
//
// Revision 1.24.2.1  2003/06/24 23:37:30  joan
// fix WhereUsedActionItem constructor
//
// Revision 1.24  2003/05/27 22:15:53  dave
// syntax fix
//
// Revision 1.23  2003/05/27 21:02:12  dave
// more WhereUsedActionItem changes
//
// Revision 1.22  2003/05/27 20:55:29  dave
// Looking at Where Used
//
// Revision 1.21  2003/04/22 21:07:32  dave
// trace to text Matrix
//
// Revision 1.20  2003/04/21 16:57:40  dave
// adding metalink to the specific function in the Where
// Used List as opposed to propogating throughout
// the structure
//
// Revision 1.19  2003/04/18 20:59:53  dave
// fixed massive taxing errors
//
// Revision 1.18  2003/04/18 20:12:30  dave
// Where Used re-write to get Associations in I
//
// Revision 1.17  2003/04/08 02:58:59  dave
// commit()
//
// Revision 1.16  2003/03/27 16:24:38  joan
// fix null pointer
//
// Revision 1.15  2003/03/26 00:27:40  dave
// when the entity item is passes.. and it does not match the
// target entitytype.. we need to shift down at the
// time of the setEntityItem..
//
// Revision 1.14  2003/03/25 23:58:48  dave
// trace
//
// Revision 1.13  2003/03/25 20:17:42  dave
// removing the rmi sending of all entityitems
// when the stripped down parent is the
// only thing that is needed
//
// Revision 1.12  2003/03/10 17:18:02  dave
// attempting to remove GBL7030 from the abstract Action Item
//
// Revision 1.11  2003/01/08 22:34:01  joan
// fix error
//
// Revision 1.10  2003/01/08 21:44:06  joan
// add getWhereUsedList
//
// Revision 1.9  2002/08/23 21:59:54  gregg
// updatePdhMeta method throws MiddlewareException
//
// Revision 1.8  2002/08/23 21:29:44  gregg
// updatePdhMeta(Database,boolean) method
//
// Revision 1.7  2002/07/18 18:38:14  joan
// add code to handle deleteActionItem
//
// Revision 1.6  2002/07/16 22:25:13  joan
// work on action item
//
// Revision 1.5  2002/07/16 21:32:21  joan
// working on getActionItemArray
//
// Revision 1.4  2002/07/16 15:38:21  joan
// working on method to return the array of actionitems
//
// Revision 1.3  2002/07/08 21:51:33  joan
// remove System.out
//
// Revision 1.2  2002/06/25 20:36:09  joan
// add create method for whereused
//
// Revision 1.1  2002/06/21 15:47:46  joan
// initial load
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.*;
import java.io.*;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.LockException;

/**
 *  Description of the Class
 *
 * @author     davidbig
 * @created    April 18, 2003
 */
public class WhereUsedActionItem extends EANActionItem {

  final static long serialVersionUID = 20011106L;

  private String m_strEntityType = null;
  // Holds the EntityType we are doing matrix with
  protected EANList m_el = new EANList();
  private NavActionItem m_nai = null;
  private EANList m_pickNavList = new EANList();
  private EANList m_createActionList = new EANList();
  private EANList m_deleteActionList = new EANList();
  private EANList m_wusedActionList = new EANList();
  private EANList m_searchActionList = new EANList();
  private EANList m_editActionList = new EANList();
  private Hashtable m_hshDisplay = null;
  private String wgDomainStr = null; //RQ0713072645-3

  public void dereference(){
	  super.dereference();
	  m_strEntityType = null;
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
	  if (m_nai!= null){
		  m_nai.dereference();
		  m_nai = null;
	  }
	  if (m_pickNavList!= null){
		  m_pickNavList.clear();
		  m_pickNavList = null;
	  }
	  if (m_createActionList!= null){
		  m_createActionList.clear();
		  m_createActionList = null;
	  }
	  if (m_deleteActionList!= null){
		  m_deleteActionList.clear();
		  m_deleteActionList = null;
	  }
	  if (m_wusedActionList!= null){
		  m_wusedActionList.clear();
		  m_wusedActionList = null;
	  }
	  if (m_searchActionList!= null){
		  m_searchActionList.clear();
		  m_searchActionList = null;
	  }
	  if (m_editActionList!= null){
		  m_editActionList.clear();
		  m_editActionList = null;
	  }
	  if (m_hshDisplay!= null){
		  m_hshDisplay.clear();
		  m_hshDisplay = null;
	  }
	
	  wgDomainStr = null; 
  }
  /**
   * Main method which performs a simple test of this class
   *
   * @param  arg  Description of the Parameter
   */
  public static void main(String arg[]) {
  }


  /*
   *  Version info
   */
  /**
   *  Gets the version attribute of the WhereUsedActionItem object
   *
   * @return    The version value
   */
  public String getVersion() {
    return "$Id: WhereUsedActionItem.java,v 1.69 2011/11/10 19:43:19 wendy Exp $";
  }


/**
   *Constructor for the WhereUsedActionItem object
   *
   * @param  _mf                             Description of the Parameter
   * @param  _ai                             Description of the Parameter
   * @exception  MiddlewareRequestException  Description of the Exception
   */
  public WhereUsedActionItem(WhereUsedActionItem _ai) throws MiddlewareRequestException {
    super(_ai);
    setEntityType(_ai.getEntityType());
    setNavActionItem(_ai.getNavActionItem());
    setPickNavActionList(_ai.getPickNavActionList());
    setCreateActionList(_ai.getCreateActionList());
    setDeleteActionList(_ai.getDeleteActionList());
    setWusedActionList(_ai.getWusedActionList());
    setEntityItems(_ai.getEntityItems());
    setSearchActionList(_ai.getSearchActionList());
    setEditActionList(_ai.getEditActionList());
	setCustomDisplay(_ai.getCustomDisplay());	//cr042806607
    wgDomainStr = _ai.wgDomainStr; //RQ0713072645-3
    // what about the Parent Entity Items?
  }


  /**
   *Constructor for the WhereUsedActionItem object
   *
   * @param  _mf                             Description of the Parameter
   * @param  _ai                             Description of the Parameter
   * @exception  MiddlewareRequestException  Description of the Exception
   */
  public WhereUsedActionItem(EANMetaFoundation _mf, WhereUsedActionItem _ai) throws MiddlewareRequestException {
    super(_mf, _ai);
    setEntityType(_ai.getEntityType());
    setNavActionItem(_ai.getNavActionItem());
    setPickNavActionList(_ai.getPickNavActionList());
    setCreateActionList(_ai.getCreateActionList());
    setDeleteActionList(_ai.getDeleteActionList());
    setWusedActionList(_ai.getWusedActionList());
    setEntityItems(_ai.getEntityItems());
    setSearchActionList(_ai.getSearchActionList());
    setEditActionList(_ai.getEditActionList());
    setCustomDisplay(_ai.getCustomDisplay());	//cr042806607
    wgDomainStr = _ai.wgDomainStr; //RQ0713072645-3
    // what about the Parent Entity Items?
  }


  /**
   * This represents a Workflow Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
   *
   * @param  _emf                            Description of the Parameter
   * @param  _db                             Description of the Parameter
   * @param  _prof                           Description of the Parameter
   * @param  _strActionItemKey               Description of the Parameter
   * @exception  SQLException                Description of the Exception
   * @exception  MiddlewareException         Description of the Exception
   * @exception  MiddlewareRequestException  Description of the Exception
   */
  public WhereUsedActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {

    super(_emf, _db, _prof, _strActionItemKey);

    // Lets go get the information pertinent to the WhereUsed Action Item

    try {
    	setDomainCheck(true);
      ReturnStatus returnStatus = new ReturnStatus(-1);
      ResultSet rs = null;
      ReturnDataResultSet rdrs;
      Profile prof = getProfile();

      rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
      rdrs = new ReturnDataResultSet(rs);
      rs.close();
      rs = null;
      _db.commit();
      _db.freeStatement();
      _db.isPending();

      // Set the class and description...
      for (int ii = 0; ii < rdrs.size(); ii++) {
        String strType = rdrs.getColumn(ii, 0);
        String strCode = rdrs.getColumn(ii, 1);
        String strValue = rdrs.getColumn(ii, 2);

        _db.debug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");

        // Collect the attributes
        if (strType.equals("TYPE") && strCode.equals("EntityType")) {
          setEntityType(strValue);
          setTargetType(strValue);
        } else if (strType.equals("TYPE") && strCode.equals("NavAction")) {
          setNavActionItem(new NavActionItem(null, _db, prof, strValue));
        } else if (strType.equals("PICK")) {
          m_pickNavList.put(strCode, strValue);
        } else if (strType.equals("CREATE")) {
          m_createActionList.put(strCode, strValue);
        } else if (strType.equals("DELETE")) {
          m_deleteActionList.put(strCode, strValue);
        } else if (strType.equals("WUSED")) {
          m_wusedActionList.put(strCode, strValue);
        } else if (strType.equals("SEARCH")) {
          m_searchActionList.put(strCode, strValue);
        } else if (strType.equals("EDIT")) {
          m_editActionList.put(strCode, strValue);
        } else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
          super.setAssociatedEntityType(strValue);
        } else if (strType.equals("TYPE") && strCode.equals("SingleInput")) {
          setSingleInput(true);
		} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
			setDataSource(strValue);											//catalog enhancement
		} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
			setAdditionalParms(strValue);
		} else if (strType.equals("TYPE") && strCode.startsWith("CustomDisplay:")) {		//cr042806607
			setCustomDisplay(strCode.substring(14),strValue);								//cr042806607
        } else if (strType.equals("TYPE") && strCode.equals("DomainCheck")) {//RQ0713072645-3 allow this to be turned off
        	setDomainCheck(!strValue.equals("N")); //RQ0713072645
        } else {
          _db.debug(D.EBUG_ERR, "WhereUsedActionItem "+_strActionItemKey+" *** ACTION ITEM ATTRIBUTE *** No home for this Action/Attribute" + strType + ":" + strCode + ":" + strValue);
        }
      }

      if (mustCheckDomain()){
		  getWGPDHDomain(_db, _prof);
	  }else{
          _db.debug(D.EBUG_WARN, "Warning: WhereUsedActionItem "+_strActionItemKey+" has DomainCheck=N, no domain checking will be done");
	  }

    } finally {
      _db.freeStatement();
      _db.isPending();
    }
  }

	/**
	* RQ0713072645- Enhancement 3
	* A new WhereUsed that filters the specified relators by WG.PDHDOMAIN.
	*/
	private void getWGPDHDomain(Database _db, Profile _prof)
	{
		// get WG
		Profile profile = _prof;
		if (profile == null) {
			profile = getProfile();
		}
		if (profile == null) {
			_db.debug(D.EBUG_ERR,"WhereUsedActionItem.getWGPDHDomain "+getKey()+" ERROR cannot execute, profile cannot be found");
			return;
		}
		try{
			EntityGroup egWG = new EntityGroup(null, _db, profile, "WG", "Edit",false);
			EntityItem wgItem = new EntityItem(egWG, profile, _db, "WG", profile.getWGID());
			EANFlagAttribute fAtt = (EANFlagAttribute)wgItem.getAttribute("PDHDOMAIN");

			_db.debug(D.EBUG_SPEW,"WhereUsedActionItem.getWGPDHDomain "+getKey()+" fatt: '"+fAtt+"' item: "+wgItem.getKey());

			if (fAtt!=null && fAtt.toString().length()>0){
				StringBuffer wgDomainSb = new StringBuffer();
				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
				for (int mfi = 0; mfi < mfArray.length; mfi++) {
					// get selected domains
					if (mfArray[mfi].isSelected()){
						if (wgDomainSb.length()>0){
							wgDomainSb.append(",");
						}
						wgDomainSb.append(mfArray[mfi].getFlagCode());
					}
				}
				wgDomainStr = wgDomainSb.toString();
			}
		}catch(Exception exc){
			StringWriter writer = new StringWriter();
			_db.debug(D.EBUG_ERR, "WhereUsedActionItem.getWGPDHDomain exception: " + exc);
			exc.printStackTrace(new PrintWriter(writer));
			_db.debug(D.EBUG_ERR, "" + writer.toString());
		}
	}

	/**
	* RQ0713072645- Enhancement 3
	* A new WhereUsed that filters the specified relators by WG.PDHDOMAIN.
	* @return WG PDHDomain flag codes
	*/
	public String getWGPDHDomain(){
		return wgDomainStr;
	}

  /**
   *  Description of the Method
   *
   * @param  _bBrief  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String dump(boolean _bBrief) {
    StringBuffer strbResult = new StringBuffer();
    strbResult.append("WhereUsedActionItem:" + getKey() + ":desc:" + getLongDescription());
    strbResult.append(":purpose:" + getPurpose());
    strbResult.append(":entitytype:" + getEntityType() + "\n");
    return strbResult.toString();
  }


  /**
   *  Gets the purpose attribute of the WhereUsedActionItem object
   *
   * @return    The purpose value
   */
  public String getPurpose() {
    return "WhereUsed";
  }


  /**
   *  Gets the entityType attribute of the WhereUsedActionItem object
   *
   * @return    The entityType value
   */
  public String getEntityType() {
    return m_strEntityType;
  }


  /**
   *  Sets the entityType attribute of the WhereUsedActionItem object
   *
   * @param  _str  The new entityType value
   */
  protected void setEntityType(String _str) {
    m_strEntityType = _str;
  }


  /**
   *  Gets the navActionItem attribute of the WhereUsedActionItem object
   *
   * @return    The navActionItem value
   */
  public NavActionItem getNavActionItem() {
    return m_nai;
  }


  /**
   *  Sets the navActionItem attribute of the WhereUsedActionItem object
   *
   * @param  _nai  The new navActionItem value
   */
  protected void setNavActionItem(NavActionItem _nai) {
    m_nai = _nai;
    _nai.setParentActionItem(this);//RQ0713072645-3

  }
  /*********
   * meta ui must update action
   */
  public void setActionClass(){
  	setActionClass("WhereUsed");
  }
  public void updateAction(String strEntityType, NavActionItem _nai){
	  setEntityType(strEntityType);
      setNavActionItem(_nai);
  }

  /**
   *  Gets the pickNavActionList attribute of the WhereUsedActionItem object
   *
   * @return    The pickNavActionList value
   */
  public EANList getPickNavActionList() {
    return m_pickNavList;
  }


  /**
   *  Sets the pickNavActionList attribute of the WhereUsedActionItem object
   *
   * @param  _el  The new pickNavActionList value
   */
  protected void setPickNavActionList(EANList _el) {
    m_pickNavList = _el;
  }


  /**
   *  Gets the createActionList attribute of the WhereUsedActionItem object
   *
   * @return    The createActionList value
   */
  public EANList getCreateActionList() {
    return m_createActionList;
  }


  /**
   *  Sets the createActionList attribute of the WhereUsedActionItem object
   *
   * @param  _el  The new createActionList value
   */
  protected void setCreateActionList(EANList _el) {
    m_createActionList = _el;
  }


  /**
   *  Gets the deleteActionList attribute of the WhereUsedActionItem object
   *
   * @return    The deleteActionList value
   */
  public EANList getDeleteActionList() {
    return m_deleteActionList;
  }


  /**
   *  Sets the deleteActionList attribute of the WhereUsedActionItem object
   *
   * @param  _el  The new deleteActionList value
   */
  protected void setDeleteActionList(EANList _el) {
    m_deleteActionList = _el;
  }

  /**
   *  Gets the editActionList attribute of the WhereUsedActionItem object
   *
   * @return    The editActionList value
   */
  public EANList getEditActionList() {
    return m_editActionList;
  }


  /**
   *  Sets the editActionList attribute of the WhereUsedActionItem object
   *
   * @param  _el  The new editActionList value
   */
  protected void setEditActionList(EANList _el) {
    m_editActionList = _el;
  }

  /**
   *  Gets the wusedActionList attribute of the WhereUsedActionItem object
   *
   * @return    The wusedActionList value
   */
  public EANList getWusedActionList() {
    return m_wusedActionList;
  }


  /**
   *  Sets the wusedActionList attribute of the WhereUsedActionItem object
   *
   * @param  _el  The new wusedActionList value
   */
  protected void setWusedActionList(EANList _el) {
    m_wusedActionList = _el;
  }

  /**
   *  Gets the searchActionList attribute of the SearchActionItem object
   *
   * @return    The searchActionList value
   */
  public EANList getSearchActionList() {
    return m_searchActionList;
  }


  /**
   *  Sets the wusedActionList attribute of the WhereUsedActionItem object
   *
   * @param  _el  The new wusedActionList value
   */
  protected void setSearchActionList(EANList _el) {
    m_searchActionList = _el;
  }

  /**
   *  This is the array of EntityItems that will be Reported Against
   *  It will search any uplinks or downlinks if the type of
   *  entity passed was in a Group that is a relator.. and a
   *  native match could not  be found

   *  Sets the entityItems attribute of the WhereUsedActionItem object
   *
   * @param  _aei  The new entityItems value
   */
  public void setEntityItems(EntityItem[] _aei) {
    resetEntityItems();
    // loop through and place the entityItem in the EANList

    for (int ii = 0; ii < _aei.length; ii++) {
      EntityItem ei = _aei[ii];
      //EntityItem processedEI = null;
      boolean bMatch = false;
      if (!ei.getEntityType().equals(getEntityType())) {
        EntityGroup eg = (EntityGroup) ei.getParent();
        // It cannot be added to the list
        if (eg == null) {
          continue;
        }
        if (eg.isRelator() || eg.isAssoc()) {
          //check the child entity item
          EntityItem eic = (EntityItem) ei.getDownLink(0);
          if (!eic.getEntityType().equals(getEntityType())) {
            //check the parent entity item
            EntityItem eip = (EntityItem) ei.getUpLink(0);
            if (!eip.getEntityType().equals(getEntityType())) {
              bMatch = false;
            } else {
              bMatch = true;
              ei = eip;
            }
          } else {
            bMatch = true;
            ei = eic;
          }
        }
      } else {
        bMatch = true;
      }

      // If you found a match on entitytype.. please put it on the list

      if (bMatch) {
        m_el.put(ei);
      } else {
        System.out.println("WhereUsedActionItem.setEntityItems() no match for EntityItem and EntityType in action:" + getEntityType() + ":" + ei.getKey());
      }
    }
  }

  public void setEntityItems(EANList _el) {
    m_el = _el;
  }

  /**
  *  Gets the entityItems attribute of the WhereUsedActionItem object
  *
  * @return    The entityItems value
  */
  public EANList getEntityItems() {
    return m_el;
  }


  /**
   *  Description of the Method
   */
  public void resetEntityItems() {
    m_el = new EANList();
  }


  /**
   *  Gets the entityItemArray attribute of the WhereUsedActionItem object
   *
   * @return    The entityItemArray value
   */
  protected EntityItem[] getEntityItemArray() {
    int size = m_el.size();
    EntityItem[] aeiReturn = new EntityItem[size];
    for (int i = 0; i < size; i++) {
      EntityItem ei = (EntityItem) m_el.getAt(i);
      aeiReturn[i] = ei;
    }
    return aeiReturn;
  }


  /**
   *  Description of the Method
   *
   * @param  _db                                        Description of the Parameter
   * @param  _prof                                      Description of the Parameter
   * @return                                            Description of the Return Value
   * @exception  SQLException                           Description of the Exception
   * @exception  MiddlewareException                    Description of the Exception
   * @exception  MiddlewareRequestException             Description of the Exception
   * @exception  LockException                          Description of the Exception
   * @exception  MiddlewareShutdownInProgressException  Description of the Exception
   * @exception  RemoteException                        Description of the Exception
   */
  public WhereUsedList exec(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
    WhereUsedList ml = executeAction(_db, _prof, true);
    // Why are we doing this?
    resetEntityItems();
    return ml;
  }


  /**
   *  Description of the Method
   *
   * @param  _rdi                                       Description of the Parameter
   * @param  _prof                                      Description of the Parameter
   * @return                                            Description of the Return Value
   * @exception  SQLException                           Description of the Exception
   * @exception  MiddlewareRequestException             Description of the Exception
   * @exception  MiddlewareShutdownInProgressException  Description of the Exception
   * @exception  MiddlewareException                    Description of the Exception
   * @exception  RemoteException                        Description of the Exception
   */
  public WhereUsedList rexec(RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException {

	//EntityList.checkDomain(_prof,this,m_el); //RQ0713072645  handled by enhancement3 now

    EANList el = new EANList();

    // This guy preps the information for RDI since this is a remote call we need to strip
    // O.k. lets make sure we are using the right profile.. here
    //
    for (int ii = 0; ii < m_el.size(); ii++) {
      EntityItem eitmp = (EntityItem) m_el.getAt(ii);
      el.put(new EntityItem(null, _prof, eitmp.getEntityType(), eitmp.getEntityID()));
    }

    m_el = el;

    D.ebug("TRACE:Starting WHERE USED: num ei:" + m_el.size());

    WhereUsedActionItem ai = new WhereUsedActionItem(this);
    NavActionItem nai = this.getNavActionItem();
    EntityItem[] aei = ai.getEntityItemArray();
    EntityList elst = EntityList.getEntityList(_rdi, _prof, nai, aei);

    D.ebug("TRACE:Starting WHERE USED: Nav Piece finished num ei: " + m_el.size());
    
    // need wg defaults for domain check
    elst.enforceWorkGroupDomain(_rdi, _prof);

    WhereUsedList ml = new WhereUsedList(_rdi, _prof, ai, elst);

    resetEntityItems();
/* why is this here?
    byte[] ba = null;
    ByteArrayOutputStream baosObject = null;
    ObjectOutputStream oosObject = null;

    try {
      baosObject = new ByteArrayOutputStream();
      oosObject = new ObjectOutputStream(baosObject);

      oosObject.writeObject(ml);
      oosObject.flush();
      ba = new byte[baosObject.size()];
      ba = baosObject.toByteArray();
      oosObject.close();
      baosObject.close();

    } catch (Exception x) {
      D.ebug(D.EBUG_ERR, x.getMessage());
    }

    D.ebug("TRACE:Finishing WHERE USED:" + ba.length);*/
    D.ebug("TRACE:Finishing WHERE USED:");

    return ml;
  }


  /**
   *  Description of the Method
   *
   * @param  _db                                        Description of the Parameter
   * @param  _prof                                      Description of the Parameter
   * @return                                            Description of the Return Value
   * @exception  SQLException                           Description of the Exception
   * @exception  MiddlewareException                    Description of the Exception
   * @exception  MiddlewareShutdownInProgressException  Description of the Exception
   */
  public WhereUsedList executeAction(Database _db, Profile _prof, boolean _bMeta) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
    _db.debug(D.EBUG_SPEW, "WhereUsedActionItem.executeAction method");
	//EntityList.checkDomain(_prof,this,m_el); //RQ0713072645 handled by enhancement3 now

    return new WhereUsedList(_db, _prof, this, _bMeta);
  }


  /**
   *  Description of the Method
   *
   * @param  _db                                        Description of the Parameter
   * @param  _prof                                      Description of the Parameter
   * @param  _strRelatorType                            Description of the Parameter
   * @return                                            Description of the Return Value
   * @exception  SQLException                           Description of the Exception
   * @exception  MiddlewareException                    Description of the Exception
   * @exception  MiddlewareRequestException             Description of the Exception
   * @exception  LockException                          Description of the Exception
   * @exception  MiddlewareShutdownInProgressException  Description of the Exception
   * @exception  RemoteException                        Description of the Exception
   */
  public EntityList generatePickList(Database _db, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareException, MiddlewareRequestException, LockException, MiddlewareShutdownInProgressException, RemoteException {
    EntityList el = _db.generatePickList(_prof, this, _strRelatorType);
    resetEntityItems();
    return el;
  }


  /**
   *  Description of the Method
   *
   * @param  _rdi                                       Description of the Parameter
   * @param  _prof                                      Description of the Parameter
   * @param  _strRelatorType                            Description of the Parameter
   * @return                                            Description of the Return Value
   * @exception  SQLException                           Description of the Exception
   * @exception  MiddlewareRequestException             Description of the Exception
   * @exception  MiddlewareShutdownInProgressException  Description of the Exception
   * @exception  MiddlewareException                    Description of the Exception
   * @exception  RemoteException                        Description of the Exception
   */
  public EntityList rgeneratePickList(RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException {
    String strTraceBase = "WhereUsedActionItem rgeneratePickList method";
    D.ebug(D.EBUG_SPEW, strTraceBase);
    EntityItem[] aei = getEntityItemArray();
    String strPL = (String)m_pickNavList.get(_strRelatorType);
    if (strPL != null && strPL.length() > 0) {
      NavActionItem nai = (NavActionItem)ObjectPool.getInstance().getActionItem(strPL);
      if (nai == null) {
        nai = new NavActionItem(null, _rdi, _prof, strPL);
        ObjectPool.getInstance().putActionItem(nai);
      }
      if (nai != null) {
        return EntityList.getEntityList(_rdi, _prof, nai, aei);
      }
    }
    return null;
  }


  /**
   *  Gets the pickList attribute of the WhereUsedActionItem object
   *
   * @param  _db                                        Description of the Parameter
   * @param  _prof                                      Description of the Parameter
   * @param  _strRelatorType                            Description of the Parameter
   * @return                                            The pickList value
   * @exception  SQLException                           Description of the Exception
   * @exception  MiddlewareException                    Description of the Exception
   * @exception  MiddlewareShutdownInProgressException  Description of the Exception
   */
  public EntityList getPickList(Database _db, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
    String strTraceBase = " WhereUsedActionItem getPickList method";
    _db.debug(D.EBUG_SPEW, strTraceBase);
    EntityItem[] aei = getEntityItemArray();
    String strPL = (String)m_pickNavList.get(_strRelatorType);
    if (strPL != null && strPL.length() > 0) {
      NavActionItem nai = new NavActionItem(null, _db, _prof, strPL);
      if (nai != null) {
        return EntityList.getEntityList(_db, _prof, nai, aei);
      }
    }

    return null;
  }


  /**
   *  Description of the Method
   *
   * @param  _db                                        Description of the Parameter
   * @param  _prof                                      Description of the Parameter
   * @param  _strRelatorType                            Description of the Parameter
   * @return                                            Description of the Return Value
   * @exception  SQLException                           Description of the Exception
   * @exception  MiddlewareException                    Description of the Exception
   * @exception  MiddlewareRequestException             Description of the Exception
   * @exception  LockException                          Description of the Exception
   * @exception  MiddlewareShutdownInProgressException  Description of the Exception
   * @exception  RemoteException                        Description of the Exception
   */
  public EntityList create(Database _db, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
    EntityList el = _db.createEntity(_prof, this, _strRelatorType);
    resetEntityItems();
    return el;
  }


  /**
   *  Description of the Method
   *
   * @param  _rdi                                       Description of the Parameter
   * @param  _prof                                      Description of the Parameter
   * @param  _strRelatorType                            Description of the Parameter
   * @return                                            Description of the Return Value
   * @exception  SQLException                           Description of the Exception
   * @exception  MiddlewareRequestException             Description of the Exception
   * @exception  MiddlewareShutdownInProgressException  Description of the Exception
   * @exception  MiddlewareException                    Description of the Exception
   * @exception  RemoteException                        Description of the Exception
   */
  public EntityList rcreate(RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException {
    EANFoundation ef = getParent();
    setParent(null);
    EntityList el = _rdi.createEntity(_prof, this, _strRelatorType);
    setParent(ef);
    resetEntityItems();
    return el;
  }


  /**
   *  Description of the Method
   *
   * @param  _db                                        Description of the Parameter
   * @param  _prof                                      Description of the Parameter
   * @param  _strRelatorType                            Description of the Parameter
   * @return                                            Description of the Return Value
   * @exception  SQLException                           Description of the Exception
   * @exception  MiddlewareException                    Description of the Exception
   * @exception  MiddlewareShutdownInProgressException  Description of the Exception
   */
  public EntityList createEntity(Database _db, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
    String strTraceBase = " WhereUsedActionItem createEntity method";
    _db.debug(D.EBUG_DETAIL, strTraceBase);

    EntityItem[] aei = getEntityItemArray();
    String strCai = (String) m_createActionList.get(_strRelatorType);
    if (strCai != null && strCai.length() > 0) {
      CreateActionItem cai = new CreateActionItem(null, _db, _prof, strCai);
      if (cai != null) {
		EntityList.checkDomain(_prof,cai,m_el); //RQ0713072645
        EntityList el = EntityList.getEntityList(_db, _prof, cai, aei);
        return el;
      }
    }

    return null;
  }


  /**
   *  Gets the whereUsedActionItem attribute of the WhereUsedActionItem object
   *
   * @param  _strRelatorType  Description of the Parameter
   * @return                  The whereUsedActionItem value
   */
  public WhereUsedActionItem getWhereUsedActionItem(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException,RemoteException {
	  String strWai = (String)m_wusedActionList.get(_strRelatorType);
	  if (strWai != null && strWai.length() > 0) {
		  WhereUsedActionItem wuai = null;
		  if (_db != null) {
			  wuai = new WhereUsedActionItem(null, _db, _prof, strWai);
		  } else {
			  wuai = (WhereUsedActionItem)ObjectPool.getInstance().getActionItem(strWai);
			  if (wuai == null) {
				  wuai = _rdi.getWhereUsedActionItem(null, _prof, strWai);
				  ObjectPool.getInstance().putActionItem(wuai);
			  } else {
				  D.ebug(D.EBUG_SPEW,"WhereUsedActionItem.getWhereUsedActionItem().Found WhereUsedActionItem in Object Pool " + wuai);
			  }
		  }

		  return wuai;
	  }
	  return null;
  }

  /**
   *  Gets the EditActionItem attribute of the WhereUsedActionItem object
   *
   * @param  _strRelatorType  Description of the Parameter
   * @return                  The whereUsedActionItem value
   */
  public EditActionItem getEditActionItem(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException,RemoteException {
    String strEai = (String)m_editActionList.get(_strRelatorType);
    if (strEai != null && strEai.length() > 0) {
      EditActionItem eai = null;
      if (_db != null) {
        eai = new EditActionItem(null, _db, _prof, strEai);
      } else {
        eai = (EditActionItem)ObjectPool.getInstance().getActionItem(strEai);
        if (eai == null) {
          eai = _rdi.getEditActionItem(null, _prof, strEai);
          ObjectPool.getInstance().putActionItem(eai);
        } else {
          D.ebug(D.EBUG_SPEW,"WhereUsedActionItem.getEditActionItem().Found EditActionItem in Object Pool " + eai);
        }
      }

      return eai;
    }
    return null;
  }

  /**
   *  Gets the actionItemArray attribute of the WhereUsedActionItem object
   *
   * @param  _strRelatorType  Description of the Parameter
   * @return                  The actionItemArray value
   */
  public Object[] getActionItemArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
	  String strTraceBase = "WhereUsedActionItem.getActionItemArray ";

	  D.ebug(D.EBUG_SPEW,strTraceBase+"enterd for " + _strRelatorType);
	  //get picklist NavActionItem
	  Vector aiVector = new Vector();
	  String strNai = (String) m_pickNavList.get(_strRelatorType);
	  if (strNai != null && strNai.length() > 0) {
		  NavActionItem nai = null;
		  if (_db != null) {
			  nai = new NavActionItem(null, _db, _prof, strNai);
		  } else {
			  nai = (NavActionItem)ObjectPool.getInstance().getActionItem(strNai);
			  if (nai == null) {
				  nai = _rdi.getNavActionItem(null, _prof, strNai);
				  ObjectPool.getInstance().putActionItem(nai);
			  } else {
				  D.ebug(D.EBUG_SPEW,strTraceBase+"Found NavActionItem in Object Pool " + nai);
			  }
		  }
		  if (nai != null) {
			  aiVector.addElement(nai);
		  }
	  }

	  String strCai = (String)m_createActionList.get(_strRelatorType);
	  if (strCai != null && strCai.length() > 0) {
		  CreateActionItem cai = null;
		  if (_db != null) {
			  cai = new CreateActionItem(null, _db, _prof, strCai);
		  } else {
			  cai = (CreateActionItem)ObjectPool.getInstance().getActionItem(strCai);
			  if (cai == null) {
				  cai = _rdi.getCreateActionItem(null, _prof, strCai);
				  ObjectPool.getInstance().putActionItem(cai);
			  } else {
				  D.ebug(D.EBUG_SPEW,strTraceBase+"Found CreateActionItem in Object Pool " + cai);
			  }
		  }

		  if (cai != null) {
			  aiVector.addElement(cai);
		  }
	  }

	  String strDai = (String)m_deleteActionList.get(_strRelatorType);
	  if (strDai != null && strDai.length() > 0) {
		  DeleteActionItem dai = null;
		  if (_db != null) {
			  dai = new DeleteActionItem(null, _db, _prof, strDai);
		  } else {
			  dai = (DeleteActionItem)ObjectPool.getInstance().getActionItem(strDai);
			  if (dai == null) {
				  dai = _rdi.getDeleteActionItem(null, _prof, strDai);
				  ObjectPool.getInstance().putActionItem(dai);
			  } else {
				  D.ebug(D.EBUG_SPEW,strTraceBase+"Found DeleteActionItem in Object Pool " + dai);
			  }
		  }
		  if (dai != null) {
			  aiVector.addElement(dai);
		  }
	  }

	  String strWai = (String)m_wusedActionList.get(_strRelatorType);
	  if (strWai != null && strWai.length() > 0) {
		  WhereUsedActionItem wuai = null;
		  if (_db != null) {
			  wuai = new WhereUsedActionItem(null, _db, _prof, strWai);
		  } else {
			  wuai = (WhereUsedActionItem)ObjectPool.getInstance().getActionItem(strWai);
			  if (wuai == null) {
				  wuai = _rdi.getWhereUsedActionItem(null, _prof, strWai);
				  ObjectPool.getInstance().putActionItem(wuai);
			  } else {
				  D.ebug(D.EBUG_SPEW,strTraceBase+"Found WhereUsedActionItem in Object Pool " + wuai);
			  }
		  }
		  if (wuai != null) {
			  aiVector.addElement(wuai);
		  }
	  }

	  String strSearch = (String)m_searchActionList.get(_strRelatorType);
	  if (strSearch != null && strSearch.length() > 0) {
		  SearchActionItem sai = null;
		  if (_db != null) {
			  sai = new SearchActionItem(null, _db, _prof, strSearch);
		  } else {
			  sai = (SearchActionItem)ObjectPool.getInstance().getActionItem(strSearch);
			  if (sai == null) {
				  sai = _rdi.getSearchActionItem(null, _prof, strSearch);
				  ObjectPool.getInstance().putActionItem(sai);
			  } else {
				  D.ebug(D.EBUG_SPEW,strTraceBase+"Found SearchActionItem in Object Pool " + sai);
			  }
		  }
		  if (sai != null) {
			  aiVector.addElement(sai);
		  }
	  }

	  String strEdit = (String)m_editActionList.get(_strRelatorType);
	  if (strEdit != null && strEdit.length() > 0) {
		  EditActionItem eai = null;
		  if (_db != null) {
			  eai = new EditActionItem(null, _db, _prof, strEdit);
		  } else {
			  eai = (EditActionItem)ObjectPool.getInstance().getActionItem(strEdit);
			  if (eai == null) {
				  eai = _rdi.getEditActionItem(null, _prof, strEdit);
				  ObjectPool.getInstance().putActionItem(eai);
			  } else {
				  D.ebug(D.EBUG_SPEW,strTraceBase+"Found EditActionItem in Object Pool " + eai);
			  }
		  }
		  if (eai != null) {
			  aiVector.addElement(eai);
		  }
	  }
	  if (aiVector.size() > 0) {
		  return aiVector.toArray();
	  } else {
		  return null;
	  }
  }


  /**
   * @param  _db                      Description of the Parameter
   * @param  _bExpire                 Description of the Parameter
   * @return                          true if successful, false if nothing to update or unsuccessful
   * @exception  MiddlewareException  Description of the Exception
   */
  protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        try {
            //lets get a fresh object from the database
            WhereUsedActionItem wu_db = new WhereUsedActionItem(null,_db,getProfile(),getActionItemKey());
            boolean bNewAction = false;
            //check for new
            if(wu_db.getActionClass() == null) {
                bNewAction = true;
            }

            //EXPIRES
            if(_bExpire && !bNewAction) {
                //NavActionItem
                if(wu_db.getNavActionItem() != null) {
                    updateActionAttribute(_db,true,"TYPE","NavAction",wu_db.getNavActionItem().getActionItemKey());
                }
                //Form
                if(wu_db.getEntityType() != null) {
                    updateActionAttribute(_db,true,"TYPE","EntityType",wu_db.getEntityType());
                }

            } else { // UPDATE
                //NavActionItem
                if(wu_db.getNavActionItem() != null && this.getNavActionItem() != null) {
                    String strNav_db = wu_db.getNavActionItem().getActionItemKey();
                    String strNav_this = this.getNavActionItem().getActionItemKey();
                    //only need to insert since this will vary only by linkvalue
                    if(bNewAction || !strNav_db.equals(strNav_this)) {
                        updateActionAttribute(_db,false,"TYPE","NavAction",strNav_this);
                    }
                } else if(wu_db.getNavActionItem() == null && this.getNavActionItem() != null) {
                    updateActionAttribute(_db,false,"TYPE","NavAction",getNavActionItem().getActionItemKey());
                }

                //EntityType
                if(getEntityType() != null) {
                    if(bNewAction || wu_db == null || (!this.getEntityType().equals(wu_db.getEntityType()))) {
                        //only need to insert since this will vary only by linkvalue
                        if(!this.getEntityType().equals("")) {
                            updateActionAttribute(_db,false,"TYPE","EntityType",this.getEntityType());
                        } else if(this.getEntityType().equals("") && wu_db.getEntityType() != null && !bNewAction) {
                            updateActionAttribute(_db,true,"TYPE","EntityType",wu_db.getEntityType());
                        }
                    }
                } else { //expire
                    if(wu_db.getEntityType() != null) {
                        updateActionAttribute(_db,true,"TYPE","EntityType",wu_db.getEntityType());
                    }
                }

                // PICK
                Enumeration enumKeys_this = this.getPickNavActionList().keys();
                Enumeration enumKeys_db = wu_db.getPickNavActionList().keys();
                //expire
                if(_bExpire && !bNewAction) {
                    while(enumKeys_db.hasMoreElements()) {
                        String strLinkCode = (String)enumKeys_db.nextElement();
                        String strLinkVal = (String)wu_db.getPickNavActionList().get(strLinkCode);
                        updateActionAttribute(_db,true,"PICK",strLinkCode,strLinkVal);
                    }
                } else if (bNewAction) { //insert all new
                    while(enumKeys_this.hasMoreElements()) {
                        String strLinkCode = (String)enumKeys_this.nextElement();
                        String strLinkVal = (String)this.getPickNavActionList().get(strLinkCode);
                        updateActionAttribute(_db,false,"PICK",strLinkCode,strLinkVal);
                    }
                } else { //compare to database and expire/update accordingly

                    // 1) go through and expire any records that are in db, !in current object
                    while(enumKeys_db.hasMoreElements()) {
                        String strLinkCode_db = (String)enumKeys_db.nextElement();
                        String strLinkVal_this = (String)this.getWusedActionList().get(strLinkCode_db);
                        if(strLinkVal_this == null) {
                            String strLinkVal_db = (String)wu_db.getWusedActionList().get(strLinkCode_db);
                            updateActionAttribute(_db,true,"PICK",strLinkCode_db,strLinkVal_db);
                        }
                    }

                    // 2) go through and update any records that are !in db, in current object
                    //    * also, check any of CURRENT records which share same keys, but changed values.
                    while(enumKeys_this.hasMoreElements()) {
                        String strLinkCode_this = (String)enumKeys_this.nextElement();
                        String strLinkVal_db = (String)wu_db.getWusedActionList().get(strLinkCode_this);
                        String strLinkVal_this = (String)this.getWusedActionList().get(strLinkCode_this);
                        if(strLinkVal_db == null || (!strLinkVal_this.equals(strLinkVal_db))) {
                            updateActionAttribute(_db,false,"PICK",strLinkCode_this,strLinkVal_this);
                        }
                    }

                }


                // CREATE
                enumKeys_this = this.getCreateActionList().keys();
                enumKeys_db = wu_db.getCreateActionList().keys();
                //expire
                if(_bExpire && !bNewAction) {
                    while(enumKeys_db.hasMoreElements()) {
                        String strLinkCode = (String)enumKeys_db.nextElement();
                        String strLinkVal = (String)wu_db.getCreateActionList().get(strLinkCode);
                        updateActionAttribute(_db,true,"CREATE",strLinkCode,strLinkVal);
                    }
                } else if (bNewAction) { //insert all new
                    while(enumKeys_this.hasMoreElements()) {
                        String strLinkCode = (String)enumKeys_this.nextElement();
                        String strLinkVal = (String)this.getCreateActionList().get(strLinkCode);
                        updateActionAttribute(_db,false,"CREATE",strLinkCode,strLinkVal);
                    }
                } else { //compare to database and expire/update accordingly

                    // 1) go through and expire any records that are in db, !in current object
                    while(enumKeys_db.hasMoreElements()) {
                        String strLinkCode_db = (String)enumKeys_db.nextElement();
                        String strLinkVal_this = (String)this.getWusedActionList().get(strLinkCode_db);
                        if(strLinkVal_this == null) {
                            String strLinkVal_db = (String)wu_db.getWusedActionList().get(strLinkCode_db);
                            updateActionAttribute(_db,true,"CREATE",strLinkCode_db,strLinkVal_db);
                        }
                    }

                    // 2) go through and update any records that are !in db, in current object
                    //    * also, check any of CURRENT records which share same keys, but changed values.
                    while(enumKeys_this.hasMoreElements()) {
                        String strLinkCode_this = (String)enumKeys_this.nextElement();
                        String strLinkVal_db = (String)wu_db.getWusedActionList().get(strLinkCode_this);
                        String strLinkVal_this = (String)this.getWusedActionList().get(strLinkCode_this);
                        if(strLinkVal_db == null || (!strLinkVal_this.equals(strLinkVal_db))) {
                            updateActionAttribute(_db,false,"CREATE",strLinkCode_this,strLinkVal_this);
                        }
                    }

                }


                // DELETE
                enumKeys_this = this.getDeleteActionList().keys();
                enumKeys_db = wu_db.getDeleteActionList().keys();
                //expire
                if(_bExpire && !bNewAction) {
                    while(enumKeys_db.hasMoreElements()) {
                        String strLinkCode = (String)enumKeys_db.nextElement();
                        String strLinkVal = (String)wu_db.getDeleteActionList().get(strLinkCode);
                        updateActionAttribute(_db,true,"DELETE",strLinkCode,strLinkVal);
                    }
                } else if (bNewAction) { //insert all new
                    while(enumKeys_this.hasMoreElements()) {
                        String strLinkCode = (String)enumKeys_this.nextElement();
                        String strLinkVal = (String)this.getDeleteActionList().get(strLinkCode);
                        updateActionAttribute(_db,false,"DELETE",strLinkCode,strLinkVal);
                    }
                } else { //compare to database and expire/update accordingly

                    // 1) go through and expire any records that are in db, !in current object
                    while(enumKeys_db.hasMoreElements()) {
                        String strLinkCode_db = (String)enumKeys_db.nextElement();
                        String strLinkVal_this = (String)this.getWusedActionList().get(strLinkCode_db);
                        if(strLinkVal_this == null) {
                            String strLinkVal_db = (String)wu_db.getWusedActionList().get(strLinkCode_db);
                            updateActionAttribute(_db,true,"DELETE",strLinkCode_db,strLinkVal_db);
                        }
                    }

                    // 2) go through and update any records that are !in db, in current object
                    //    * also, check any of CURRENT records which share same keys, but changed values.
                    while(enumKeys_this.hasMoreElements()) {
                        String strLinkCode_this = (String)enumKeys_this.nextElement();
                        String strLinkVal_db = (String)wu_db.getWusedActionList().get(strLinkCode_this);
                        String strLinkVal_this = (String)this.getWusedActionList().get(strLinkCode_this);
                        if(strLinkVal_db == null || (!strLinkVal_this.equals(strLinkVal_db))) {
                            updateActionAttribute(_db,false,"DELETE",strLinkCode_this,strLinkVal_this);
                        }
                    }
                }


                // WUSED
                enumKeys_this = this.getWusedActionList().keys();
                enumKeys_db = wu_db.getWusedActionList().keys();
                //expire
                if(_bExpire && !bNewAction) {
                    while(enumKeys_db.hasMoreElements()) {
                        String strLinkCode = (String)enumKeys_db.nextElement();
                        String strLinkVal = (String)wu_db.getWusedActionList().get(strLinkCode);
                        updateActionAttribute(_db,true,"WUSED",strLinkCode,strLinkVal);
                    }
                } else if (bNewAction) { //insert all new
                    while(enumKeys_this.hasMoreElements()) {
                        String strLinkCode = (String)enumKeys_this.nextElement();
                        String strLinkVal = (String)this.getWusedActionList().get(strLinkCode);
                        updateActionAttribute(_db,false,"WUSED",strLinkCode,strLinkVal);
                    }
                } else { //compare to database and expire/update accordingly

                    // 1) go through and expire any records that are in db, !in current object
                    while(enumKeys_db.hasMoreElements()) {
                        String strLinkCode_db = (String)enumKeys_db.nextElement();
                        String strLinkVal_this = (String)this.getWusedActionList().get(strLinkCode_db);
                        if(strLinkVal_this == null) {
                            String strLinkVal_db = (String)wu_db.getWusedActionList().get(strLinkCode_db);
                            updateActionAttribute(_db,true,"WUSED",strLinkCode_db,strLinkVal_db);
                        }
                    }

                    // 2) go through and update any records that are !in db, in current object
                    //    * also, check any of CURRENT records which share same keys, but changed values.
                    while(enumKeys_this.hasMoreElements()) {
                        String strLinkCode_this = (String)enumKeys_this.nextElement();
                        String strLinkVal_db = (String)wu_db.getWusedActionList().get(strLinkCode_this);
                        String strLinkVal_this = (String)this.getWusedActionList().get(strLinkCode_this);
                        if(strLinkVal_db == null || (!strLinkVal_this.equals(strLinkVal_db))) {
                            updateActionAttribute(_db,false,"WUSED",strLinkCode_this,strLinkVal_this);
                        }
                    }

                }

            }

        } catch(SQLException sqlExc) {
            _db.debug(D.EBUG_ERR,"WhereUsedActionItem updatePdhMeta " + sqlExc.toString());
            return false;
        } finally {
          _db.freeStatement();
          _db.isPending();
        }
        return true;
  }

/**
 * to simplify things a bit
 */
    private void updateActionAttribute(Database _db, boolean _bExpire, String _strLinkType2, String _strLinkCode, String _strLinkValue) throws MiddlewareException {
        String strValFrom = _db.getDates().getNow();
        String strValTo = (_bExpire?strValFrom:_db.getDates().getForever());
        new MetaLinkAttrRow(getProfile(),"Action/Attribute",getActionItemKey(),_strLinkType2,_strLinkCode,_strLinkValue,strValFrom,strValTo,strValFrom,strValTo,2).updatePdh(_db);
    }

/*
 cr042806607
 */
	private void setCustomDisplay(String _sEntity, String _val) {
//		System.out.println("***ACL WhereUsedActionItem.setCustomDisplay(" + _sEntity + ", " + _val + ")");
		if (m_hshDisplay == null) {
			m_hshDisplay = new Hashtable();
		}
		if (!m_hshDisplay.containsKey(_sEntity)) {
			m_hshDisplay.put(_sEntity,_val);
		}
	}

	private Hashtable getCustomDisplay() {
		return m_hshDisplay;
	}

	private void setCustomDisplay(Hashtable _t) {
		if (_t != null) {
			Enumeration e = _t.keys();
			while (e.hasMoreElements()) {
				String key = (String)e.nextElement();
				setCustomDisplay(key,(String)_t.get(key));
			}
		}
	}

	protected boolean hasCustomDisplay(String _s) {
		if (m_hshDisplay != null) {
			return m_hshDisplay.containsKey(_s);
		}
		return false;
	}

	protected String getCustomDisplay(String _s) {
		if (hasCustomDisplay(_s)) {
			return (String)m_hshDisplay.get(_s);
		}
		return null;
	}

}

