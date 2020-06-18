//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: WhereUsedGroup.java,v $
// Revision 1.26  2013/09/19 15:00:10  wendy
// add warning when data is dropped due to domain check
//
// Revision 1.25  2009/05/14 18:49:16  wendy
// Support dereference for memory release
//
// Revision 1.24  2008/01/31 22:56:01  wendy
// Cleanup RSA warnings
//
// Revision 1.23  2007/08/15 14:36:37  wendy
// RQ0713072645- Enhancement 3
//
// Revision 1.22  2005/04/19 19:09:21  joan
// fixes for mn23525749
//
// Revision 1.21  2004/01/14 18:41:23  dave
// more squeezing of the short description
//
// Revision 1.20  2004/01/12 21:48:39  dave
// more space claiming
//
// Revision 1.19  2004/01/06 22:03:45  joan
// fb fix
//
// Revision 1.18  2003/10/30 21:53:38  dave
// first attempt at par child the same
//
// Revision 1.17  2003/10/28 21:54:45  joan
// fb fixes
//
// Revision 1.16  2003/09/11 17:55:48  joan
// 52110
//
// Revision 1.15  2003/04/23 21:21:11  dave
// adding all fields back to grid
//
// Revision 1.14  2003/04/18 22:32:13  dave
// Fixing dummied up description
//
// Revision 1.13  2003/04/18 22:25:00  dave
// more changes
//
// Revision 1.12  2003/04/18 22:13:17  dave
// alternate entitygroup building
//
// Revision 1.11  2003/04/18 20:59:53  dave
// fixed massive taxing errors
//
// Revision 1.10  2003/04/18 20:12:30  dave
// Where Used re-write to get Associations in I
//
// Revision 1.9  2003/04/17 21:57:26  dave
// typo
//
// Revision 1.8  2003/04/17 21:49:30  dave
// Null Pointer fixes
//
// Revision 1.7  2003/04/17 21:21:23  dave
// fixing null pointer
//
// Revision 1.6  2003/04/17 20:16:39  dave
// trying to encorporate Associations into Where used
//
// Revision 1.5  2003/02/27 20:54:30  joan
// change WhereUsedGroup key to identify parent and child of the same relator type
//
// Revision 1.4  2002/06/25 17:49:37  joan
// add link and removeLink methods
//
// Revision 1.3  2002/06/24 17:13:50  joan
// display entity description
//
// Revision 1.2  2002/06/21 16:01:59  joan
// fix errors
//
// Revision 1.1  2002/06/21 15:47:46  joan
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
 *  Description of the Class
 *
 * @author     davidbig
 * @created    April 17, 2003
 */
public class WhereUsedGroup extends EANMetaEntity {
  /**
   * @serial
   */
  final static long serialVersionUID = 1L;

  private EntityGroup m_eg = null;
  private boolean m_bParent = false;

  public final static String PARENT = "Parent";
  public final static String CHILD = "Child";
  public final static String ASSOC = "Association";
  
  protected void dereference() {
	  for (int i = 0; i < getDataCount(); i++) {
		  WhereUsedItem qi = (WhereUsedItem)getData().getAt(i);
		  qi.dereference();
	  }
	  m_eg = null;
	  super.dereference();
  }
  
  public static void main(String arg[]) {
  }


  /**
   *Constructor for the WhereUsedGroup object
   *
   * @param  _ef                             Description of the Parameter
   * @param  _prof                           Description of the Parameter
   * @param  _eg                             Description of the Parameter
   * @param  _isParent                       Description of the Parameter
   * @exception  MiddlewareRequestException  Description of the Exception
   */
  public WhereUsedGroup(EANMetaFoundation _ef, Profile _prof, EntityGroup _eg, String _strClass) throws MiddlewareRequestException {
    // need to keep the key unque so we add PARENT:CHILD:ASSOC:
    super(_ef, _prof, _eg.getEntityType() + _strClass);

    setEntityGroup(_eg);

    // Not sure how this will react to the NLS changes
    putLongDescription(_eg.getLongDescription());
    //putShortDescription(_eg.getShortDescription());
    setParentFlag(_strClass.equals(PARENT));

    EntityGroup egParent = getWhereUsedList().getParentEntityGroup();
	if (_eg.isAssoc()) {
		if (isParent()) {

		  for (int j = 0; j < _eg.getEntityItemCount(); j++) {
			EntityItem ei = _eg.getEntityItem(j);
			for (int p=0; p < ei.getUpLinkCount(); p++) {
				EntityItem eip = (EntityItem) ei.getUpLink(p);
				for (int c=0; c < ei.getDownLinkCount(); c++) {
					EntityItem eic = (EntityItem) ei.getDownLink(c);
					if (egParent.getEntityItem(eic.getKey()) != null) {
						putWhereUsedItem(new WhereUsedItem(this, null, eic, eip, ei, getDirectionString()));
					}
				}
			}
		  }
		} else {

		  for (int j = 0; j < _eg.getEntityItemCount(); j++) {
			EntityItem ei = _eg.getEntityItem(j);
			for (int p=0; p < ei.getUpLinkCount(); p++) {
				EntityItem eip = (EntityItem) ei.getUpLink(p);
				for (int c=0; c < ei.getDownLinkCount(); c++) {
					EntityItem eic = (EntityItem) ei.getDownLink(c);
					if (egParent.getEntityItem(eip.getKey()) != null) {
						putWhereUsedItem(new WhereUsedItem(this, null, eip, eic, ei, getDirectionString()));
					}
				}
			}
		  }
		}
	} else {
		if (isParent()) {

		  for (int j = 0; j < _eg.getEntityItemCount(); j++) {
			EntityItem ei = _eg.getEntityItem(j);

			EntityItem eip = (EntityItem) ei.getUpLink(0);
			EntityItem eic = (EntityItem) ei.getDownLink(0);
			if (egParent.getEntityItem(eic.getKey()) != null) {
				if (checkDomain(eip, eic, ei)){ //RQ0713072645-3 only check relators
					putWhereUsedItem(new WhereUsedItem(this, null, eic, eip, ei, getDirectionString()));
				}
			}
		  }
		} else {

		  for (int j = 0; j < _eg.getEntityItemCount(); j++) {
			EntityItem ei = _eg.getEntityItem(j);

			EntityItem eip = (EntityItem) ei.getUpLink(0);
			EntityItem eic = (EntityItem) ei.getDownLink(0);
			if (egParent.getEntityItem(eip.getKey()) != null) {
				if (checkDomain(eip, eic, ei)){ //RQ0713072645-3 only check relators
					putWhereUsedItem(new WhereUsedItem(this, null, eip, eic, ei, getDirectionString()));
				}
			}
		  }
		}
	}

    // Fill in any voids
    createDummy();
  }

	/**
	* RQ0713072645- Enhancement 3
	* A new WhereUsed that filters the specified relators by WG.PDHDOMAIN.
	*/
	private boolean checkDomain(EntityItem eip, EntityItem eic, EntityItem ei){
		WhereUsedActionItem wai = getWhereUsedList().getParentActionItem();
		String wgdomain = wai.getWGPDHDomain();
		boolean domainOk = true;

		D.ebug(D.EBUG_SPEW,"WhereUsedGroup.checkDomain entered for wgdomain "+wgdomain+" eip "+
			eip.getKey()+" eic "+eic.getKey()+" ei "+ei.getKey());

		if (!getProfile().getEnforcePDHDomain()){
			D.ebug(D.EBUG_SPEW,"WhereUsedGroup.checkDomain is not turned on in middleware properties (need enforce.pdhdomain=true), so domain check will not be done");
			return domainOk;
		}

		if (wgdomain != null){
			// ei should be the relator, check the rest just in case of relator to relator
			domainOk = checkPDHDomain(ei, wgdomain);
			if (domainOk){
				domainOk = checkPDHDomain(eip, wgdomain);
				if (domainOk){
					domainOk = checkPDHDomain(eic, wgdomain);
					if (!domainOk){
						D.ebug(D.EBUG_WARN,"WhereUsedGroup.checkDomain failed for wgdomain: '"+
							wgdomain+"' dropping row for item: "+eic.getKey());
					}
				}else{
					D.ebug(D.EBUG_WARN,"WhereUsedGroup.checkDomain failed for wgdomain: '"+
						wgdomain+"' dropping row for item: "+eip.getKey());
				}
			}else{
				D.ebug(D.EBUG_WARN,"WhereUsedGroup.checkDomain failed for wgdomain: '"+
					wgdomain+"' dropping row for item: "+ei.getKey());
			}
		}
		return domainOk;
	}
	/**
	* RQ0713072645- Enhancement 3
	* A new WhereUsed that filters the specified relators by WG.PDHDOMAIN.
	*/
	private boolean checkPDHDomain(EntityItem item, String wgdomain)
	{
		boolean domainOk = true;

		if (item.getEntityGroup().isRelator()){
			EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("PDHDOMAIN");

			D.ebug(D.EBUG_SPEW,"WhereUsedGroup.getPDHDomain fatt: '"+
				fAtt+"' item: "+item.getKey());

			// dont need to check if this is a nav attr because a navaction returned this item
			if (fAtt!=null && fAtt.toString().length()>0){
				domainOk=false;
				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
				for (int mfi = 0; mfi < mfArray.length; mfi++) {
					// get selected domains
					if (mfArray[mfi].isSelected() &&
						wgdomain.indexOf(mfArray[mfi].getFlagCode())!= -1) {
						domainOk = true;
						break;
					}
				}
			}
		}

		return domainOk;
	}
  /**
   *  Description of the Method
   *
   * @param  _bBrief  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String dump(boolean _bBrief) {
    StringBuffer strbResult = new StringBuffer();
    if (_bBrief) {
      strbResult.append("\nWhereUsedGroup : " + getKey());
      strbResult.append("\nm_bParent : " + m_bParent);
    } else {
      strbResult.append("\nWhereUsedGroup : " + getKey());
      strbResult.append("\nm_bParent : " + m_bParent);

      strbResult.append("\n   WhereUsedItems  ");
      for (int i = 0; i < getWhereUsedItemCount(); i++) {
        WhereUsedItem wui = getWhereUsedItem(i);
        strbResult.append(wui.dump(_bBrief));
      }
    }

    return new String(strbResult);
  }


  /**
   *  Gets the whereUsedItemCount attribute of the WhereUsedGroup object
   *
   * @return    The whereUsedItemCount value
   */
  public int getWhereUsedItemCount() {
    return getDataCount();
  }


  /**
   *  Description of the Method
   *
   * @param  _mi  Description of the Parameter
   */
  public void putWhereUsedItem(WhereUsedItem _mi) {
    putData(_mi);
    _mi.setParent(this);
  }


  /**
   *  Gets the whereUsedItem attribute of the WhereUsedGroup object
   *
   * @param  _i  Description of the Parameter
   * @return     The whereUsedItem value
   */
  public WhereUsedItem getWhereUsedItem(int _i) {
    return (WhereUsedItem) getData(_i);
  }


  /**
   *  Gets the whereUsedItem attribute of the WhereUsedGroup object
   *
   * @param  _s  Description of the Parameter
   * @return     The whereUsedItem value
   */
  public WhereUsedItem getWhereUsedItem(String _s) {
    return (WhereUsedItem) getData(_s);
  }


  /**
   *  Gets the whereUsedList attribute of the WhereUsedGroup object
   *
   * @return    The whereUsedList value
   */
  public WhereUsedList getWhereUsedList() {
    return (WhereUsedList) getParent();
  }


  /**
   *  Sets the isParent attribute of the WhereUsedGroup object
   *
   * @param  _b          The new parentFlag value
   */
  protected void setParentFlag(boolean _b) {
    m_bParent = _b;
  }

  /**
   *  Gets the parent attribute of the WhereUsedGroup object
   *
   * @return    The parent value
   */
  public boolean isParent() {
    return m_bParent;
  }


  /**
   *  Gets the directionString attribute of the WhereUsedGroup object
   *
   * @return    The directionString value
   */
  public String getDirectionString() {
    if (isParent()) {
      return PARENT;
    } else if (isAssoc()) {
      return ASSOC;
    } else {
      return CHILD;
    }
  }


  /**
   *  Removes a Where used Item from the list
   *
   * @param  _wui  The where used item to be removed
   * @return       The WhereUsedItem  that was just removed
   */
  public WhereUsedItem removeWhereUsedItem(WhereUsedItem _wui) {
    return (WhereUsedItem) removeData(_wui);
  }


  /**
   *  Gets the entityType attribute of the WhereUsedGroup object
   *
   * @return    The entityType value
   */
  public String getEntityType() {
    return m_eg.getEntityType();
  }


  /**
   *  Gets the entity1Type attribute of the WhereUsedGroup object
   *
   * @return    The entity1Type value
   */
  public String getEntity1Type() {
    return m_eg.getEntity1Type();
  }


  /**
   *  Gets the entity2Type attribute of the WhereUsedGroup object
   *
   * @return    The entity2Type value
   */
  public String getEntity2Type() {
    return m_eg.getEntity2Type();
  }


  /**
   *  Gets the pickListable attribute of the WhereUsedGroup object
   *
   * @return    The pickListable value
   */
  public boolean isPickListable() {
    return m_eg.isRelator();
  }


  /**
   *  Gets the assoc attribute of the WhereUsedGroup object
   *
   * @return    The assoc value
   */
  public boolean isAssoc() {
    return m_eg.isAssoc();
  }


  /**
   *  Gets the entityGroup attribute of the WhereUsedGroup object
   *
   * @return    The entityGroup value
   */
  public EntityGroup getEntityGroup() {
    return m_eg;
  }


  /**
   *  Sets the entityGroup attribute of the WhereUsedGroup object
   *
   * @param  _eg  The new entityGroup value
   */
  private void setEntityGroup(EntityGroup _eg) {
    m_eg = _eg;
  }


  /**
   *  This looks in the Parent EntityGroup and creates placeholders in the target Where Used Group
   *  for any parent entity Item that does not have a representative link in this guy
   *
   */
  protected void createDummy() {

    String strRelatedEntityType = null;
    WhereUsedList wul = getWhereUsedList();
    EntityList el = wul.getEntityList();
    EntityGroup egParent = wul.getParentEntityGroup();
    for (int j = 0; j < egParent.getEntityItemCount(); j++) {
      EntityItem eiParent = egParent.getEntityItem(j);
      boolean bFound = false;
      for (int x = 0; x < getWhereUsedItemCount(); x++) {
        WhereUsedItem wui = getWhereUsedItem(x);
        EntityItem ei = wui.getOriginalEntityItem();
        if (ei.getKey().equals(eiParent.getKey())) {
          bFound = true;
          break;
        }
      }
      if (!bFound) {
        int id = wul.getNextRID();
        if (isParent()) {
          strRelatedEntityType = getEntity1Type();
        } else {
          strRelatedEntityType = getEntity2Type();
        }
        try {
          EntityGroup egRelated = el.getEntityGroup(strRelatedEntityType);
          EntityItem eiRelator = new EntityItem(getEntityGroup(), null, getEntityType(), id);
          EntityItem eiRelated = new EntityItem(egRelated, null, strRelatedEntityType, id);
          putWhereUsedItem(new WhereUsedItem(this, null, eiParent, eiRelated, eiRelator, getDirectionString()));
        } catch (Exception x) {
          x.printStackTrace();
        }
      }
    }
  }

}

