// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: WhereUsedItem.java,v $
// Revision 1.30  2010/09/15 17:37:25  wendy
// Implement getEANObject for UI msgs
//
// Revision 1.29  2009/05/11 15:35:33  wendy
// Support dereference for memory release
//
// Revision 1.28  2008/01/31 22:56:01  wendy
// Cleanup RSA warnings
//
// Revision 1.27  2007/06/21 19:54:50  wendy
// Added links to support edit after a link action
//
// Revision 1.26  2006/05/12 18:24:49  tony
// custom display needs to start from the proper location
//
// Revision 1.25  2006/05/04 21:47:33  tony
// cr042806607
// add functionality to first and last column.
//
// Revision 1.24  2006/05/04 17:17:16  tony
// testing of cr042806607
//
// Revision 1.23  2006/05/04 16:08:32  tony
// cr 042806607
//
// Revision 1.22  2004/06/23 19:25:06  joan
// work on remove parent links
//
// Revision 1.21  2004/06/18 17:24:07  joan
// work on edit relator
//
// Revision 1.20  2004/01/13 00:44:50  dave
// trace for how big the WhereUsed guy is
//
// Revision 1.19  2004/01/12 21:48:39  dave
// more space claiming
//
// Revision 1.18  2004/01/06 22:03:46  joan
// fb fix
//
// Revision 1.17  2003/09/11 17:55:48  joan
// 52110
//
// Revision 1.16  2003/08/18 21:05:10  dave
// Adding  the sequencing chain to the islocked to not
// induced each cell for being locked in the islocked of
// entityItem (kludge)
//
// Revision 1.15  2003/04/18 20:59:53  dave
// fixed massive taxing errors
//
// Revision 1.14  2003/04/18 20:12:30  dave
// Where Used re-write to get Associations in I
//
// Revision 1.13  2003/04/17 22:11:18  dave
// fixing where used null pointer
//
// Revision 1.12  2002/11/19 23:26:56  joan
// fix hasLock method
//
// Revision 1.11  2002/11/19 18:27:43  joan
// adjust lock, unlock
//
// Revision 1.10  2002/11/19 00:06:27  joan
// adjust isLocked method
//
// Revision 1.9  2002/11/14 19:09:52  joan
// add getNavAttrDescription method
//
// Revision 1.8  2002/10/07 17:41:39  joan
// add getLockGroup method
//
// Revision 1.7  2002/08/08 20:07:27  joan
// fix setParentEntityItem
//
// Revision 1.6  2002/07/08 18:18:17  joan
// fix link method
//
// Revision 1.5  2002/06/28 20:35:44  joan
// debug
//
// Revision 1.4  2002/06/25 17:49:37  joan
// add link and removeLink methods
//
// Revision 1.3  2002/06/24 17:13:50  joan
// display entity description
//
// Revision 1.2  2002/06/21 21:21:56  joan
// fix null pointer
//
// Revision 1.1  2002/06/21 15:47:46  joan
// initial load
//
//


package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *  Description of the Class
 *
 * @author     davidbig
 * @created    April 17, 2003
 */
public class WhereUsedItem extends EANDataFoundation implements EANAddressable {

  private EntityItem m_eiOriginal = null;
  private EntityItem m_eiRelated = null;
  private EntityItem m_eiRelator = null;
  //private String m_strRelationship = null;
  private String m_strDirection = null;

  public final static String ORIGINALENTITY = "0";
  public final static String RELATIONSHIP = "1";
  public final static String DIRECTION = "2";
  public final static String RELATEDENTITYTYPE = "3";
  public final static String RELATEDENTITY = "4";

  final static long serialVersionUID = 1L;

  protected void dereference() {
	  m_eiOriginal = null;
	  m_eiRelated = null;
	  m_eiRelator = null;
	  m_strDirection = null;
	  super.dereference();
  }
  
  public String getVersion() {
    return "$Id: WhereUsedItem.java,v 1.30 2010/09/15 17:37:25 wendy Exp $";
  }


  public static void main(String arg[]) {
  }


  /**
   *Constructor for the WhereUsedItem object
   *
   * @param  _mi                             Description of the Parameter
   * @exception  MiddlewareRequestException  Description of the Exception
   */
 // public WhereUsedItem(WhereUsedItem _wui) throws MiddlewareRequestException {
 //   super(null, _wui.getProfile(), _wui.getKey());
 //   setOriginalEntityItem(_wui.getOriginalEntityItem());
 //   setRelatedEntityItem(_wui.getRelatedEntityItem());
 //   setRelatorEntityItem(_wui.getRelatorEntityItem());
 //   setDirection(_wui.getDirection());
 // }


	/**
	*Constructor for the WhereUsedItem object
	*
	* @param  _f                              Description of the Parameter
	* @param  _prof                           Description of the Parameter
	* @param  _originalEI                     Description of the Parameter
	* @param  _relatedEI                      Description of the Parameter
	* @param  _eir                            Description of the Parameter
	* @exception  MiddlewareRequestException  Description of the Exception
	*/
	public WhereUsedItem(EANFoundation _f, Profile _prof, EntityItem _eiOriginal, EntityItem _eiRelated, EntityItem _eir, String _strDirection) throws MiddlewareRequestException {
		super(_f, _prof, _eir.getKey() + _eiOriginal.getKey() + _eiRelated.getKey() + _strDirection);
		setOriginalEntityItem(_eiOriginal);
		setRelatedEntityItem(_eiRelated);
		setRelatorEntityItem(_eir);
		setDirection(_strDirection);
		// link action puts an entity item in the whereusedlist but the up and down links are null so a subsequent edit fails
		if (_strDirection.equals("Child")){
			// make sure the up and down links exist
			if (_eir.getUpLinkCount()==0){
				_eir.putUpLink(_eiOriginal);
				_eir.putDownLink(_eiRelated);
			}
		}else{
			// make sure the up and down links exist
			if (_eir.getDownLinkCount()==0){
				_eir.putDownLink(_eiOriginal);
				_eir.putUpLink(_eiRelated);
			}
		}
	}

	/**
	*  Gets the whereUsedGroup attribute of the WhereUsedItem object
	*
	* @return    The whereUsedGroup value
	*/
	public WhereUsedGroup getWhereUsedGroup() {
		return (WhereUsedGroup) getParent();
	}

  /**
   *  Description of the Method
   *
   * @param  _bBrief  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String dump(boolean _bBrief) {
    StringBuffer strbResult = new StringBuffer();
    strbResult.append("\nWhereUsedItem:" + getKey() + ":");
    if (m_eiOriginal != null) {
      strbResult.append("\nOriginal EntityItem:" + m_eiOriginal.getKey() + ":");
    }
    if (m_eiRelated != null) {
      strbResult.append("\nRelated EntityItem:" + m_eiRelated.getKey() + ":");
    }
    if (m_eiRelator != null) {
      strbResult.append("\nRelator EntityItem:" + m_eiRelator.getKey() + ":");
    }
    return strbResult.toString();
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public String toString() {
    StringBuffer strbResult = new StringBuffer();
    strbResult.append(getKey());
    return strbResult.toString();
  }


  /**
   *  Sets the originalEntity attribute of the WhereUsedItem object
   *
   * @param  _ei  The new originalEntity value
   */
  protected void setOriginalEntityItem(EntityItem _ei) {
    m_eiOriginal = _ei;
  }


  /**
   *  Gets the originalEntity attribute of the WhereUsedItem object
   *
   * @return    The originalEntity value
   */
  public EntityItem getOriginalEntityItem() {
    return m_eiOriginal;
  }


  /**
   *  Sets the relatedEntity attribute of the WhereUsedItem object
   *
   * @param  _ei  The new relatedEntity value
   */
  protected void setRelatedEntityItem(EntityItem _ei) {
    m_eiRelated = _ei;
  }


  /**
   *  Gets the relatedEntity attribute of the WhereUsedItem object
   *
   * @return    The relatedEntity value
   */
  public EntityItem getRelatedEntityItem() {
    return m_eiRelated;
  }

  /**
   *  Sets the direction of the WhereUsedItem object
   *
   * @param  _s  PARENT, CHILD, ASSOC
   */
  protected void setDirection(String _s) {
    m_strDirection = _s;
  }


  /**
   *  Gets the direction attribute of the WhereUsedItem object
   *
   * @return    The direction value
   */
  public String getDirection() {
    return m_strDirection;
  }

  /**
   *  Sets the relatorEntity attribute of the WhereUsedItem object
   *
   * @param  _ei  The new relatorEntity value
   */
  protected void setRelatorEntityItem(EntityItem _ei) {
    m_eiRelator = _ei;
  }


  /**
   *  Gets the relatorEntity attribute of the WhereUsedItem object
   *
   * @return    The relatorEntity value
   */
  public EntityItem getRelatorEntityItem() {
    return m_eiRelator;
  }


  /**
   *  Description of the Method
   *
   * @param  _bCreate  Description of the Parameter
   * @return           Description of the Return Value
   */
  public Vector generateLinkRelators(boolean _bCreate) {

    Vector vctReturnRelatorKeys = new Vector();
    String strEntity1Type = m_eiOriginal.getEntityType();
    int iEntity1ID = m_eiOriginal.getEntityID();
    String strEntityType = m_eiRelator.getEntityType();
    int iEntityID = m_eiRelator.getEntityID();
    String strEntity2Type = m_eiRelated.getEntityType();
    int iEntity2ID = m_eiRelated.getEntityID();

    if(getWhereUsedGroup().isParent()) {
    	vctReturnRelatorKeys.addElement(new ReturnRelatorKey(strEntityType, iEntityID, strEntity2Type, iEntity2ID, strEntity1Type, iEntity1ID, _bCreate));
	} else {
    	vctReturnRelatorKeys.addElement(new ReturnRelatorKey(strEntityType, iEntityID, strEntity1Type, iEntity1ID, strEntity2Type, iEntity2ID, _bCreate));
	}

    return vctReturnRelatorKeys;

  }


  // methods for EANAddressable
  /**
   *  Gets the eANObject attribute of the WhereUsedItem object
   *
   * Used in UI user msgs
   * @param  _str  Description of the Parameter
   * @return       The eANObject value
   */
  public EANFoundation getEANObject(String _str) {
	  if (_str.equals(ORIGINALENTITY)) {
		  return m_eiOriginal;
	  } else if (_str.equals(RELATIONSHIP)) {
		  return m_eiRelator;
	  } else if (_str.equals(DIRECTION)) {
		  return null;
	  } else if (_str.equals(RELATEDENTITYTYPE)) {
		  return m_eiRelated;
	  } else if (_str.equals(RELATEDENTITY)) {
		  return m_eiRelated;
	  }
	  return null;
  }


  /**
   *  Description of the Method
   *
   * @param  _s  Description of the Parameter
   * @param  _b  Description of the Parameter
   * @return     Description of the Return Value
   */
  public Object get(String _s, boolean _b) {

	  WhereUsedGroup wug = getWhereUsedGroup();
	  if (_s.equals(ORIGINALENTITY)) {
		  String sCust = getCustomDisplay(m_eiOriginal.getEntityType());					//cr042806607
		  if (sCust != null) {															//cr042806607
			  return getDisplayData(m_eiOriginal,sCust);												//cr042806607
		  }
		  return m_eiOriginal.getNavAttrDescription();
	  } else if (_s.equals(RELATIONSHIP)) {
		  return wug.getLongDescription();
	  } else if (_s.equals(DIRECTION)) {
		  return wug.getDirectionString();
	  } else if (_s.equals(RELATEDENTITYTYPE)) {
		  if (m_eiRelated!=null && m_eiRelated.getEntityGroup()!=null){
			  return m_eiRelated.getEntityGroup().getLongDescription();
		  }
	  } else if (_s.equals(RELATEDENTITY)) {
		  String sCust = getCustomDisplay(m_eiRelated.getEntityType());					//cr042806607
		  if (sCust != null) {															//cr042806607
			  return getDisplayData(m_eiRelated,sCust);												//cr042806607
		  } else {																		//cr042806607
			  if (m_eiRelated.getEntityID() > 0) {
				  String navdesc = m_eiRelated.getNavAttrDescription();
				  if (navdesc.length()==0){
					  navdesc="No Navigate Attribute description found";
				  }
				  return navdesc;
			  } else {
				  return "";
			  }
		  }
	  }
	  return null;
  }


  /**
   *  Description of the Method
   *
   * @param  _s                            Description of the Parameter
   * @param  _o                            Description of the Parameter
   * @return                               Description of the Return Value
   * @exception  EANBusinessRuleException  Description of the Exception
   */
  public boolean put(String _s, Object _o) throws EANBusinessRuleException {
    return false;
  }


  /**
   *  Gets the editable attribute of the WhereUsedItem object
   *
   * @param  _s  Description of the Parameter
   * @return     The editable value
   */
  public boolean isEditable(String _s) {
    return false;
  }


  /**
   *  Gets the locked attribute of the WhereUsedItem object
   *
   * @param  _s            Description of the Parameter
   * @param  _rdi          Description of the Parameter
   * @param  _db           Description of the Parameter
   * @param  _ll           Description of the Parameter
   * @param  _prof         Description of the Parameter
   * @param  _lockOwnerEI  Description of the Parameter
   * @param  _iLockType    Description of the Parameter
   * @param  _bCreateLock  Description of the Parameter
   * @return               The locked value
   */
  public boolean isLocked(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType, String _strTime, boolean _bCreateLock) {
    return false;
  }


  /*
   *  No LockGroup to return
   */
  /**
   *  Gets the lockGroup attribute of the WhereUsedItem object
   *
   * @param  _s  Description of the Parameter
   * @return     The lockGroup value
   */
  public LockGroup getLockGroup(String _s) {
    return null;
  }


  /**
   *  Description of the Method
   *
   * @param  _str          Description of the Parameter
   * @param  _lockOwnerEI  Description of the Parameter
   * @param  _prof         Description of the Parameter
   * @return               Description of the Return Value
   */
  public boolean hasLock(String _str, EntityItem _lockOwnerEI, Profile _prof) {
    return false;
  }


  /**
   *  Description of the Method
   *
   * @param  _str  Description of the Parameter
   */
  public void rollback(String _str) { }


  /**
   *  Gets the help attribute of the WhereUsedItem object
   *
   * @param  _str  Description of the Parameter
   * @return       The help value
   */
  public String getHelp(String _str) {
    return null;
  }


  /**
   *  Description of the Method
   *
   * @param  _s            Description of the Parameter
   * @param  _rdi          Description of the Parameter
   * @param  _db           Description of the Parameter
   * @param  _ll           Description of the Parameter
   * @param  _prof         Description of the Parameter
   * @param  _lockOwnerEI  Description of the Parameter
   * @param  _iLockType    Description of the Parameter
   */
  public void unlock(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) { }


  /**
   *  Description of the Method
   *
   * @param  _s   Description of the Parameter
   * @param  _ll  Description of the Parameter
   */
  public void resetLockGroup(String _s, LockList _ll) { }


//  public void setParentEntityItem(String _s, EntityItem _ei) {}

  /**
   *  Sets the parentEntityItem attribute of the WhereUsedItem object
   *
   * @param  _ei  The new parentEntityItem value
   */
  public void setParentEntityItem(EntityItem _ei) { }
    public boolean isParentAttribute(String _str) { return false;}
    public boolean isChildAttribute(String _str) {return false;}
/*
 cr042806607
	plug in etype to see waht should display
	.. should be based on where used then subgrouped by etype.
		or based only on entitytype
	should work with VEPath functionality

		linktype			linktype1		linktype2	linkCode				linkvalue
		Action/Attribute	WUSED<action>	TYPE		CustomDisplay:Entity	Entity,DIRECTION.Entity,Direction:att.att.att|Entity,DIRECTION.Entity,Direction:att.att.att
 */
	private String getCustomDisplay(String _s) {
//System.out.println("WhereUsedItem.getCustomDisplay(" + _s + ")");
		WhereUsedGroup wug = getWhereUsedGroup();
		if (wug != null) {
			WhereUsedList wul = wug.getWhereUsedList();
			if (wul != null) {
				WhereUsedActionItem wuai = wul.getParentActionItem();
				if (wuai != null) {
					if (wuai.hasCustomDisplay(_s)) {
//						System.out.println("    display is:  " + wuai.getCustomDisplay(_s));
						return wuai.getCustomDisplay(_s);
					}
				}
			}
		}
		return null;
	}

	private String getDisplayData(EntityItem _ei, String _s) {
		//System.out.println("WhereUsedItem.getDisplayData(" + _s + ")");
		boolean bFirst = true;
		StringTokenizer st = new StringTokenizer(_s,"|");
		//String sToken = null;
		StringBuffer sb = new StringBuffer();
		while (st.hasMoreTokens()) {
			if (bFirst) {
				bFirst = false;
			} else {
				sb.append(":");
			}
			sb.append(getDisplayString(_ei,st.nextToken()));
		}
		//System.out.println("    display data is: " + sb.toString());
		return sb.toString();
	}

	private String getDisplayString(EntityItem _ei, String _s) {
//		System.out.print("getDisplayString(" + _s + ")");
		StringTokenizer st = new StringTokenizer(_s,":");
		boolean bFirst = true;
		String sPath = null;
		String sAtt = null;
		EntityItem eiTarget = null;
		StringTokenizer stAtt = null;
		StringBuffer sbOut = null;
		if (st.hasMoreTokens()) {
			sPath = st.nextToken();
		}
		if (st.hasMoreTokens()) {
			sAtt = st.nextToken();
		}
		if (sPath != null) {
			eiTarget = getTarget(_ei,sPath);
		}
		if (eiTarget != null && sAtt != null) {
			stAtt = new StringTokenizer(sAtt,".");
			sbOut = new StringBuffer();
			while (stAtt.hasMoreTokens()) {
				EANAttribute att = eiTarget.getAttribute(stAtt.nextToken());
				if (att != null) {
					if (bFirst) {
						bFirst = false;
					} else {
						sbOut.append(":");
					}
					sbOut.append(att.toString());
				}
			}
		}
//		System.out.println(":  " + sbOut.toString());
		return sbOut.toString();
	}

	private EntityItem getTarget(EntityItem _ei, String _s) {
//		System.out.println("getTarget(" + _s + ")");
		StringTokenizer st = new StringTokenizer(_s,".");
		String sEntity = null;
		String sDirection = null;
		EntityItem ei = _ei;
		while (st.hasMoreTokens()) {
			StringTokenizer st2 = new StringTokenizer(st.nextToken(),",");
			if (st2.hasMoreTokens()) {
				sEntity = st2.nextToken();
			}
			if (st2.hasMoreTokens()) {
				sDirection = st2.nextToken();
			}
			if (sEntity != null && sDirection != null) {
				ei = getTarget(ei,sEntity,sDirection);
			}
		}
//		System.out.println("    found:  " + ei.getKey());
		return ei;
	}

	private EntityItem getTarget(EntityItem _ei, String _sEnt, String _sDir) {
		if (_ei != null) {
			if (_ei.getEntityType().equals(_sEnt)) {
				return _ei;
			}
			if (_sDir.equals("U")) {
//				System.out.println("looking up on " + _ei.getEntityType() + " for " + _sEnt);
				if (_ei.hasUpLinks()) {
					int ii = _ei.getUpLinkCount();
					for (int i=0;i<ii;++i) {
						EntityItem tmp = (EntityItem)_ei.getUpLink(i);
						if (tmp != null) {
							if (tmp.getEntityType().equals(_sEnt)) {
//								System.out.println("    found: " + tmp.getKey());
								return tmp;
							}
						}
					}
				}
			} else if (_sDir.equals("D")) {
//				System.out.println("looking down on " + _ei.getEntityType() + " for " + _sEnt);
				if (_ei.hasDownLinks()) {
					int ii = _ei.getDownLinkCount();
					for (int i=0;i<ii;++i) {
						EntityItem tmp = (EntityItem)_ei.getDownLink(i);
						if (tmp != null) {
							if (tmp.getEntityType().equals(_sEnt)) {
//								System.out.println("    found: " + tmp.getKey());
								return tmp;
							}
						}
					}
				}
			}
		}
		return _ei;
	}
}

