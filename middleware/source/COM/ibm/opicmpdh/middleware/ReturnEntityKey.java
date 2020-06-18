//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ReturnEntityKey.java,v $
// Revision 1.16  2006/06/15 22:12:12  dave
// backing out trace
//
// Revision 1.15  2006/06/15 21:21:55  dave
// mre trace
//
// Revision 1.14  2005/01/28 22:06:46  gregg
// DependentAttributeValue
//
// Revision 1.13  2005/01/17 17:45:28  gregg
// Rule51Group
//
// Revision 1.12  2005/01/05 23:27:37  gregg
// s'more for UniqueAttributeGroup
//
// Revision 1.11  2005/01/05 22:58:26  gregg
// more UniqueAttributeGroup
//
// Revision 1.10  2005/01/05 22:37:26  gregg
// store UniqueAttribtueGroup on ReturnEntityKey
//
// Revision 1.9  2002/04/19 23:48:54  dave
// minor project fix
//
// Revision 1.8  2002/04/19 23:44:17  dave
// null pointer trap in dump statement
//
// Revision 1.7  2002/04/19 22:06:19  dave
// making sense of swapkey = resetKey
//
// Revision 1.6  2001/08/22 16:53:02  roger
// Removed author RM
//
// Revision 1.5  2001/03/26 16:33:22  roger
// Misc formatting clean up
//
// Revision 1.4  2001/03/21 00:01:09  roger
// Implement java class file branding in getVersion method
//
// Revision 1.3  2001/03/16 15:52:24  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

import java.io.*;
import java.util.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.Rule51Group;

/**
 * This holds the information for a UI Entity Update.
 * @version @date
 * @see Database
 * @see DatabasePool
 * @see RemoteDatabase
 * @see ReturnData
 * @see ReturnInteger
 * @see ReturnID
 */
public final class ReturnEntityKey implements OPICMObject, Serializable, Cloneable {
  public String m_strEntityType;
  public int m_iEntityID;
  protected boolean m_bisActive = true;
  protected boolean m_bisPosted = false;
  private ReturnID m_rid = null;
  public Vector m_vctAttributes = null;
  private Vector m_vctUniqueAttGroups = null;
  private Rule51Group m_rule51Grp = null;
  private Vector m_vctDepAttVals = null;

  /**
   * Creates a ReturnEntityKey
   * @param s1 EntityType
   * @param i1 OriginalEntityID
   * @param rid1 ReturnID
   */
  public ReturnEntityKey(String _s1, int i1, boolean _b1) {
    super();

    m_strEntityType = _s1;
    m_iEntityID = i1;
    m_bisActive = _b1;
    m_bisPosted = false;
    m_vctAttributes = null;// Need a place to house attributes
  }

  /* Returns a hashkey (entitytype + entityID)
  * @return the hashkey (entitytype + entityID)
  */
  public final String hashkey() {
    return m_strEntityType + m_iEntityID;
  }

  /* Returns the EID assigned by middleware
  * @return the EID assigned by middelware
  */
  public int getReturnID() {
    return m_rid.intValue();
  }

  /* Sets the EID assigned by middleware
  * @return the EID assigned by middelware
  */
  public void setReturnID(ReturnID _rid) {
    m_rid = _rid;
  }

  /**
   * @return the date/time this class was generated
   */
  public final String getVersion() {
    return new String("$Id: ReturnEntityKey.java,v 1.16 2006/06/15 22:12:12 dave Exp $");
  }

  /**
   *
   */
  public int getEntityID() {
    return m_iEntityID;
  }

  /**
   *
   */
  public String getEntityType() {
    return m_strEntityType;
  }

  /**
   *
   */
  public boolean isActive() {
    return m_bisActive;
  }

  /**
   *
   */
  public boolean isPosted() {
    return m_bisPosted;
  }

  /**
   *
   */
  public void setPosted(boolean _b) {
    m_bisPosted = _b;
  }

  public final void setUniqueAttributeGroups(Vector _v) {
      m_vctUniqueAttGroups = _v;
  }

  public final Vector getUniqueAttributeGroups() {
      return m_vctUniqueAttGroups;
  }

  public final boolean hasUniqueAttributeGroups() {
      return (m_vctUniqueAttGroups != null && m_vctUniqueAttGroups.size() > 0);
  }

  public final void setDependentAttributeValues(Vector _v) {
      m_vctDepAttVals = _v;
  }

  public final Vector getDependentAttributeValues() {
      return m_vctDepAttVals;
  }

  public final boolean hasDependentAttributeValues() {
      return (m_vctDepAttVals != null && m_vctDepAttVals.size() > 0);
  }

  public final void setRule51Group(Rule51Group _rg) {
      m_rule51Grp = _rg;
  }

  public final Rule51Group getRule51Group() {
      return m_rule51Grp;
  }

  public final boolean hasRule51Group() {
      return (m_rule51Grp != null);
  }

 /**
   *
   */
  public String toString() {
    return m_strEntityType + ":" + m_iEntityID + ":" + (m_rid == null ? "No RetID" : getReturnID() + "") + ":" +  m_bisPosted + ":" + m_bisActive;
  }
}
