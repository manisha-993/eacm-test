//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ReturnRelatorKey.java,v $
// Revision 1.14  2009/05/11 15:53:34  wendy
// Support dereference for memory release
//
// Revision 1.13  2005/08/25 21:27:33  tony
// removed getHeritageKey
//
// Revision 1.12  2005/08/23 18:14:24  tony
// adjusted Heritage Key
//
// Revision 1.11  2005/08/22 16:24:03  tony
// Test_acl20050822
//
// Revision 1.10  2005/01/18 01:05:57  gregg
// storing Rule51Group on object
//
// Revision 1.9  2002/02/22 17:51:59  dave
// more syntax fixes
//
// Revision 1.8  2002/02/22 17:35:11  dave
// added EANObject as in interface
//
// Revision 1.7  2001/10/12 21:42:00  dave
// commit for where used stuff
//
// Revision 1.6  2001/08/22 16:53:03  roger
// Removed author RM
//
// Revision 1.5  2001/03/26 16:33:22  roger
// Misc formatting clean up
//
// Revision 1.4  2001/03/21 00:01:09  roger
// Implement java class file branding in getVersion method
//
// Revision 1.3  2001/03/16 15:52:25  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

import java.io.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.EANObject;
import COM.ibm.eannounce.objects.Rule51Group;

/**
 * @version @date
 * @see ReturnData
 * @see ReturnInteger
 * @see ReturnID
 */
public final class ReturnRelatorKey implements OPICMObject, EANObject, Serializable, Cloneable {

  protected String m_strEntityType = null;
  protected int m_iEntityID = 0;
  public String m_strEntity1Type = null;
  public int m_iEntity1ID = 0;
  public String m_strEntity2Type = null;
  public int m_iEntity2ID = 0;
  protected boolean m_bisActive = true;
  protected boolean m_bisPosted = false;
  private ReturnID m_rid = null;
  private Rule51Group m_rule51Grp = null;
  

  public void dereference(){
	  m_strEntityType = null;
	  m_strEntity1Type = null;
	  m_strEntity2Type = null;
	  m_rid = null;
	  if (m_rule51Grp != null){
		  m_rule51Grp.dereference();
		  m_rule51Grp = null;
	  }
  }
  	

  /**
   * Creates a ReturnRelatorKey
   * @param s1 EntityType
   * @param i1 OriginalEntityID
   * @param s2 Entity1Type
   * @param i2 Entity1ID
   * @param s2 Entity2Type
   * @param i2 Entity2ID
   * @param b1 isActive?
   */
  public ReturnRelatorKey(String _s1, int _i1, String _s2, int _i2, String _s3, int _i3, boolean _b1) {

    super();

    m_strEntityType = _s1;
    m_iEntityID = _i1;
    m_strEntity1Type = _s2;
    m_iEntity1ID = _i2;
    m_strEntity2Type = _s3;
    m_iEntity2ID = _i3;
    m_bisActive = _b1;
    m_bisPosted = false;
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
    if (m_rid == null) return 0;
    return m_rid.intValue();
  }

  /* Sets the EID assigned by middleware
  * @return the EID assigned by middelware
  */
  public void setReturnID(ReturnID _rid) {
    m_rid = _rid;
  }
  public ReturnID fetchReturnID() {
    return m_rid;
  }

  /* Returns the EntityID
  */
  public int getEntityID() {
    return m_iEntityID;
  }

  /* Returns the Entity2ID
  */
  public int getEntity2ID() {
    return m_iEntity2ID;
  }

  /* Returns the Entity1ID
  */
  public int getEntity1ID() {
    return m_iEntity1ID;
  }

  /* Returns the EntityType
  */
  public String getEntityType() {
    return m_strEntityType;
  }

  /* Returns the Entity1Type
  */
  public String getEntity1Type() {
    return m_strEntity1Type;
  }

  /* Returns the Entity2Type
  */
  public String getEntity2Type() {
    return m_strEntity2Type;
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

  /**
   * @return the date/time this class was generated
   */
  public final String getVersion() {
    return new String("$Id: ReturnRelatorKey.java,v 1.14 2009/05/11 15:53:34 wendy Exp $");
  }

  /**
   * Display the object values for testing and debuging.
   */
  public void display(java.io.PrintStream out) {
    out.print("rrk:" + this.m_strEntityType + ":" + this.m_iEntityID + ":" + this.m_strEntity1Type + ":" + this.m_iEntity1ID + ":" + this.m_strEntity2Type + ":" + this.m_iEntity2ID + ":" + this.m_bisPosted + ":" + this.m_bisActive);
    out.println(":RETURNID:" + getReturnID());
  }

  /**
   *
   */
  public String toString() {
    return m_strEntityType + ":" + m_iEntityID + ":" + m_strEntity1Type + ":" + m_iEntity1ID + ":" + m_strEntity2Type + ":" + m_iEntity2ID + ":" + m_bisPosted + ":" + m_bisActive;
  }

  public final String getKey() {
    return hashkey();
  }

  public Profile getProfile() {
  	return null;
  }

  public NLSItem getNLSItem() {
  	return null;
  }

  public int getNLSID() {
  	return 1;
  }

  public boolean isSelected() {
  	return true;
  }

  public void setSelected(boolean _b) {
  }

  public String dump(boolean _b) {
   return getKey();
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
}
