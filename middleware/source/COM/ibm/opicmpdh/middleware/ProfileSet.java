//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ProfileSet.java,v $
// Revision 1.23  2009/03/10 13:40:27  wendy
// Add placeholder for CQ14860
//
// Revision 1.22  2006/02/13 22:00:52  dave
// no frills profile constructor
//
// Revision 1.21  2004/05/11 15:56:48  roger
// Changes to support login/logout logging
//
// Revision 1.20  2003/05/12 18:09:26  roger
// Ensure all profiles in ProfileSet have same login time
//
// Revision 1.19  2003/04/16 20:31:57  gregg
// replaceProfile method
//
// Revision 1.18  2003/04/08 02:49:30  dave
// commit()
//
// Revision 1.17  2003/03/21 16:52:13  joan
// add check if _i >= 0  in setActiveProfile method
//
// Revision 1.16  2002/07/08 20:10:02  roger
// Fix some strange exception handling
//
// Revision 1.15  2002/06/26 16:47:24  roger
// Clean up
//
// Revision 1.14  2002/06/25 14:40:08  roger
// Added debug messages
//
// Revision 1.13  2002/06/24 22:07:43  roger
// Chase freeStatement
//
// Revision 1.12  2002/06/21 21:01:45  roger
// freeStatement
//
// Revision 1.11  2002/06/21 16:54:41  roger
// freeStatement in finally
//
// Revision 1.10  2002/06/21 16:36:26  roger
// Statement not freed after getDates
//
// Revision 1.9  2002/05/03 18:27:21  gregg
// replaced gbl6000 w/ gbl8600
//
// Revision 1.8  2002/03/26 20:53:07  joan
// reveal active profile index
//
// Revision 1.7  2001/10/25 20:20:21  dave
// fix bad syntax by me
//
// Revision 1.6  2001/10/25 20:12:37  dave
// null pointer fix
//
// Revision 1.5  2001/10/24 20:48:59  roger
// getActiveProfile now returns null if no profile is set
//
// Revision 1.4  2001/09/28 17:21:12  roger
// throw LoginException on bad UserToken
//
// Revision 1.3  2001/09/19 16:47:37  roger
// Include RoleDescription in Profile
//
// Revision 1.2  2001/09/10 17:20:33  roger
// Not needed
//
// Revision 1.1  2001/09/07 22:19:41  roger
// Move the Profile stuff
//
// Revision 1.49  2001/09/07 21:10:35  roger
// Show the index of the active profile
//
// Revision 1.48  2001/09/07 20:51:49  roger
// Set default language to 1st entry (if there is no first entry attempts to use language will throw exception)
//
// Revision 1.47  2001/09/07 18:31:04  roger
// class cast expection fix for toArray
//
// Revision 1.46  2001/09/07 15:41:50  roger
// Added setActiveProfile & getActiveProfile
//
// Revision 1.45  2001/08/23 23:34:37  roger
// Make public until things working
//
// Revision 1.44  2001/08/23 18:59:11  roger
// Use method
//
// Revision 1.43  2001/08/23 18:53:51  roger
// Start using DatePackage
//
// Revision 1.42  2001/08/22 16:52:50  roger
// Removed author RM
//
// Revision 1.41  2001/08/21 22:13:14  roger
// Serializable import
//
// Revision 1.40  2001/08/21 21:37:10  roger
// Misc
//
// Revision 1.39  2001/08/21 19:47:36  roger
// Start log again
//
package COM.ibm.opicmpdh.middleware;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

class HoldInfo {
  protected String m_strEnterprise = null;
  protected int m_iOPID = -1;
  protected String m_strOPName = null;
  protected int m_iOPWGID = -1;
  protected String m_strOPWGName = null;
  protected int m_iWGID = -1;
  protected String m_strWGName = null;
  protected String m_strRoleCode = null;
  protected String m_strRoleDescription = null;

  public HoldInfo(String _strEnterprise, int _iOPID, String _strOPName, int _iOPWGID, String _strOPWGName, int _iWGID, String _strWGName, String _strRoleCode, String _strRoleDescription) {
    m_strEnterprise = _strEnterprise;
    m_iOPID = _iOPID;
    m_strOPName = _strOPName;
    m_iOPWGID = _iOPWGID;
    m_strOPWGName = _strOPWGName;
    m_iWGID = _iWGID;
    m_strWGName = _strWGName;
    m_strRoleCode = _strRoleCode;
    m_strRoleDescription = _strRoleDescription;
  }
  public String dump() {
    return dump(false);
  }
  public String dump(boolean _bBrief) {
    StringBuffer strbResult = new StringBuffer();

    strbResult.append("strEnterprise: " + m_strEnterprise);
    strbResult.append("\niOPID: " + m_iOPID);
    strbResult.append("\nstrOPName: " + m_strOPName);
    strbResult.append("\niOPWGID: " + m_iOPWGID);
    strbResult.append("\nstrOPWGName: " + m_strOPWGName);
    strbResult.append("\niWGID: " + m_iWGID);
    strbResult.append("\nstrWGName: " + m_strWGName);
    strbResult.append("\nstrRoleCode: " + m_strRoleCode);
    strbResult.append("\nstrRoleDescription: " + m_strRoleDescription);

    return strbResult.toString();
  }
  public String toString() {
    return dump(true);
  }
}

/**
 * A class which defines the login ProfileSet object
 * @version @date
 */
public final class ProfileSet implements Serializable {
  /**
   * @serial
   */
  static final long serialVersionUID = 1L;
  protected Vector m_vctVector = null;
  protected int m_iActiveProfile = -1;
  protected String m_strLoginTime = null;
  private String m_strLastLoginTime = null; // CQ14860

  public ProfileSet() {
    m_vctVector = new Vector();
  }
  public ProfileSet(Database _dbCurrent, String _strOPName) throws LoginException {

    ReturnStatus returnStatus = new ReturnStatus(-1);
    ResultSet rs = null;
    Vector m_vctHold = new Vector();
    DatePackage dpCurrent = null;

    m_vctVector = new Vector();

    try {

      _dbCurrent.freeStatement();
      _dbCurrent.isPending("before getDates in ProfileSet");

      dpCurrent = _dbCurrent.getDates();

      String strNow = dpCurrent.getNow();
      m_strLoginTime = strNow;

      _dbCurrent.freeStatement();
      _dbCurrent.isPending("before call to 8600 in ProfileSet");

      // Retrieve basic info for all profiles for given operator
      rs = _dbCurrent.callGBL8600(returnStatus, Profile.OP_TYPE, Profile.WG_TYPE, Profile.OPWG_TYPE, _strOPName, strNow, strNow);

      while (rs.next()) {
        // Assemble values for a new profile and hold onto them
        HoldInfo hiThis = new HoldInfo(rs.getString(1), rs.getInt(3), rs.getString(4).trim(), rs.getInt(6), rs.getString(7).trim(), rs.getInt(9), rs.getString(10).trim(), rs.getString(11).trim(), rs.getString(12).trim());

        m_vctHold.addElement(hiThis);
      }

      rs.close();

      rs = null;
      _dbCurrent.commit();
      _dbCurrent.freeStatement();
      _dbCurrent.isPending("before creating new Profile in ProfileSet");

      if (m_vctHold.size() < 1) {
        throw new LoginException("invalid LoginToken");
      }

      for (int i = 0; i < m_vctHold.size(); i++) {
        HoldInfo hiThis = (HoldInfo) m_vctHold.elementAt(i);
        Profile pThis = new Profile(_dbCurrent, hiThis.m_strEnterprise, hiThis.m_iOPID, hiThis.m_strOPName, hiThis.m_iWGID, hiThis.m_strWGName, hiThis.m_iOPWGID, hiThis.m_strOPWGName, hiThis.m_strRoleCode, hiThis.m_strRoleDescription);
        pThis.setLoginTime(m_strLoginTime);

        // Add the new profile to the vector
        m_vctVector.addElement(pThis);
      }
    } catch (SQLException sx) {
      D.ebug("sql exception " + sx);
    } catch (MiddlewareException mx) {
      D.ebug("mw exception " + mx);
    } finally {
      _dbCurrent.freeStatement();
      _dbCurrent.isPending("in finally in ProfileSet");
    }
  }

  /**
   * CQ14860 set last successful login dts
   *
   * @param dbCurrent
   */
  protected void setLastLoginTime(Database dbCurrent) 
  {   
	  m_strLastLoginTime = m_strLoginTime;  // default to current time
	  /* restore when CQ is approved
	  String strTraceBase = "ProfileSet.setLastLoginTime ";
	  if (this.m_vctVector.size()>0){
		  Profile profile = (Profile)m_vctVector.firstElement();
		  String attrCode = "USERNAMEXXX"; //fixme get correct attribute
		  // give this profile a valon
		  String valon = profile.getValOn();
		  String effon = profile.getEffOn();
		  profile.setValOnEffOn(m_strLoginTime, m_strLoginTime);

		  try{
			  // find OP with this emailaddress - get eid
			  int eid = 0;
			  ResultSet rs = null;
			  String strSQL1 =
				  "SELECT entityid " +
				  "FROM opicm.text "+
				  "WHERE enterprise = ? " +
				  "AND EntityType = 'OP' " +
				  "AND AttributeCode = 'USERTOKEN' " +
				  "AND AttributeValue = ? " +
				  "AND ValFrom <= current timestamp AND  current timestamp < ValTo " +
				  "AND EffFrom <= current timestamp AND current timestamp < EffTo " +
				  "with ur";

			  PreparedStatement ps = null;
			  Connection con = dbCurrent.getPDHConnection();

			  try {
				  dbCurrent.debug(D.EBUG_SPEW, strTraceBase + " setting:" + profile.getEnterprise() + ":" + profile.getEmailAddress());
				  dbCurrent.debug(D.EBUG_SPEW, strTraceBase + " strSQL1:" + strSQL1);
				  ps = con.prepareStatement(strSQL1);

				  ps.setString(1,profile.getEnterprise());
				  ps.setString(2,profile.getEmailAddress());
				  rs = ps.executeQuery();

				  dbCurrent.debug(D.EBUG_SPEW, strTraceBase + " executed SQL1.");

				  while(rs.next()) {
					  eid = rs.getInt(1);						
				  }
				  dbCurrent.debug(D.EBUG_SPEW, strTraceBase + " eid: " + eid);

			  } finally {
				  if(rs != null) {
					  rs.close();
					  rs = null;
				  }
				  if (ps != null) {
					  ps.close();
					  ps = null;
				  }
				  dbCurrent.commit();
				  dbCurrent.freeStatement();
				  dbCurrent.isPending();
			  }	  

			  // set last login time to now
			  if (eid != 0){
				  EntityGroup eg = new EntityGroup(null, dbCurrent, profile, "OP", "Edit");
				  EntityItem ei = new EntityItem(eg, profile, dbCurrent, "OP", eid);
				  EANMetaAttribute ma = eg.getMetaAttribute(attrCode);
				  if (ma!=null){
					  if (ma.getAttributeType().charAt(0) == 'T')
					  {
						  EANAttribute textAttr = ei.getAttribute(attrCode);
						  if (textAttr == null) {
							  textAttr = new TextAttribute(ei, null, (MetaTextAttribute) ma);
							  ei.putAttribute(textAttr);
						  }else{
							  m_strLastLoginTime = textAttr.get().toString();				
						  }			 
						  textAttr.put(m_strLoginTime);
					  }

					  ei.commit(dbCurrent, null);
				  }else{
					  dbCurrent.debug(D.EBUG_ERR, strTraceBase + " ERROR: " + attrCode+" was not in meta for OP");
				  }
			  }
		  }catch(Exception exc){
			  dbCurrent.debug(D.EBUG_ERR, exc.getMessage());
			  exc.printStackTrace();
		  }

		  // reset profile
		  profile.setValOnEffOn(valon, effon);
	  }
	  */
  }
  
  /**
   * CQ14860 get last successful login
   * @return String
   */
  public String getLastLoginTime() { return m_strLastLoginTime;}
/**
 *
 */
  protected ProfileSet(int _iCapacity) {
    m_vctVector = new Vector(_iCapacity);
  }
/**
 *
 */
  protected ProfileSet(int _iCapacity, int _iCapacityIncrement) {
    m_vctVector = new Vector(_iCapacity, _iCapacityIncrement);
  }
/**
 *
 */
  protected ProfileSet(Collection _c) {
// how to ensure they are profile objects?
    m_vctVector = new Vector(_c);
  }
/**
 *
 */
  public boolean addAll(Collection _c) {
// how to ensure they are profile objects?
    return m_vctVector.addAll(_c);
  }
/**
 *
 */
  public boolean addAll(int _i, Collection _c) {
// how to ensure they are profile objects?
    return m_vctVector.addAll(_i, _c);
  }
/**
 *
 */
  public int capacity() {
    return m_vctVector.capacity();
  }
/**
 *
 */
  public void clear() {
    m_vctVector.clear();
  }
/**
 *
 */
  public boolean containsAll(Collection _c) {
// how to ensure they are profile objects?
    return m_vctVector.containsAll(_c);
  }
/**
 *
 */
  public Enumeration elements() {
    return m_vctVector.elements();
  }
/**
 *
 */
  public void ensureCapacity(int _i) {
    m_vctVector.ensureCapacity(_i);
  }
/**
 *
 */
  public int hashCode() {
    return m_vctVector.hashCode();
  }
/**
 *
 */
  public boolean isEmpty() {
    return m_vctVector.isEmpty();
  }
/**
 *
 */
  public Iterator iterator() {
    return m_vctVector.iterator();
  }
/**
 *
 */
  public ListIterator listIterator() {
    return m_vctVector.listIterator();
  }
/**
 *
 */
  public ListIterator listIterator(int _i) {
    return m_vctVector.listIterator(_i);
  }
/**
 *
 */
  public boolean removeAll(Collection _c) {
// how to ensure they are profile objects?
    return m_vctVector.removeAll(_c);
  }
/**
 *
 */
  public void removeAllElements() {
    m_vctVector.removeAllElements();
  }
/**
 *
 */
  public void removeElementAt(int _i) {
    m_vctVector.removeElementAt(_i);
  }
/**
 *
 */
  public boolean retainAll(Collection _c) {
// how to ensure they are profile objects?
    return m_vctVector.retainAll(_c);
  }
/**
 *
 */
  public void setSize(int _i) {
    m_vctVector.setSize(_i);
  }
/**
 *
 */
  public int size() {
    return m_vctVector.size();
  }
/**
 *
 */
  public List subList(int _fi, int _ti) {
    return m_vctVector.subList(_fi, _ti);
  }
/**
 *
 */
  public String toString() {
    return m_vctVector.toString();
  }
/**
 *
 */
  public void trimToSize() {
    m_vctVector.trimToSize();
  }
/**
 *
 */
  public void add(int _i, Profile _prof) {
    m_vctVector.add(_i, _prof);
  }
/**
 *
 */
  public boolean add(Profile _prof) {
    return m_vctVector.add(_prof);
  }
/**
 *
 */
  public void addElement(Profile _prof) {
    m_vctVector.addElement(_prof);
  }
/**
 *
 */
  public Object clone() {
    ProfileSet psClone = new ProfileSet();

    psClone.m_vctVector = (Vector) m_vctVector.clone();

    return psClone;
  }
/**
 *
 */
  public boolean contains(Profile _prof) {
    return m_vctVector.contains(_prof);
  }
/**
 *
 */
  public void copyInto(Profile[] _aprof) {
    m_vctVector.copyInto(_aprof);
  }
/**
 *
 */
  public Profile elementAt(int _i) {
    return (Profile) m_vctVector.elementAt(_i);
  }
/**
 *
 */
  public boolean equals(Object _o) {
    return m_vctVector.equals(_o);
  }
/**
 *
 */
  public int indexOf(Profile _prof) {
    return m_vctVector.indexOf(_prof);
  }
/**
 *
 */
  public int indexOf(Profile _prof, int _i) {
    return m_vctVector.indexOf(_prof, _i);
  }
/**
 *
 */
  public void insertElementAt(Profile _prof, int _i) {
    m_vctVector.insertElementAt(_prof, _i);
  }
/**
 *
 */
  public Profile lastElement() {
    return (Profile) m_vctVector.lastElement();
  }
/**
 *
 */
  public int lastIndexOf(Profile _prof) {
    return m_vctVector.lastIndexOf(_prof);
  }
/**
 *
 */
  public int lastIndexOf(Profile _prof, int _i) {
    return m_vctVector.lastIndexOf(_prof, _i);
  }
/**
 *
 */
  public Profile remove(int _i) {
    return (Profile) m_vctVector.remove(_i);
  }
/**
 *
 */
  public boolean remove(Profile _prof) {
    return m_vctVector.remove(_prof);
  }
/**
 *
 */
  public void removeElement(Profile _prof) {
    m_vctVector.removeElement(_prof);
  }
/**
 *
 */
  public Profile set(int _i, Profile _prof) {
    return (Profile) m_vctVector.set(_i, _prof);
  }
/**
 *
 */
  public void setElementAt(Profile _prof, int _i) {
    m_vctVector.setElementAt(_prof, _i);
  }
/**
 *
 */
  public Profile[] toArray() {
    Object[] ao = m_vctVector.toArray();
    Profile[] aprof = new Profile[ao.length];

    // Added to populate array
    for (int x = 0; x < aprof.length; x++) {
      aprof[x] = (Profile) ao[x];
    }

    return aprof;
  }
/**
 *
 */
  public Profile[] toArray(Profile[] _aprof) {
    return (Profile[]) m_vctVector.toArray(_aprof);
  }
/**
 *
 */
  public void setActiveProfile(Profile _profile) {
    setActiveProfile(m_vctVector.indexOf(_profile));
  }
/**
 *
 */
  public void setActiveProfile(int _i) {
    if (_i >= 0) {
        //Profile profItem = (Profile) 
        m_vctVector.elementAt(_i);
    }
    m_iActiveProfile = _i;
  }
/**
 *
 */
  public Profile getActiveProfile() {

    Profile profReturn = null;

    if (m_iActiveProfile == -1) {
      return null;
    }

    if (m_iActiveProfile < m_vctVector.size()) {
      profReturn = (Profile) m_vctVector.elementAt(m_iActiveProfile);
    }

    return profReturn;
  }
/**
 * Return the date/time this class was generated
 * @return the date/time this class was generated
 */
  public final static String getVersion() {
    return "$Id: ProfileSet.java,v 1.23 2009/03/10 13:40:27 wendy Exp $";
  }
  public int getActiveProfileIndex() {
    return m_iActiveProfile;
  }

/**
 * Replace the Profile w/ the same Enterprise+OPWGID
 * @return whether or not the Profile was found + replaced
 */
  public boolean replaceProfile(Profile _prof) {
    int iOldIndex = -1;
    for(int i = 0; i < m_vctVector.size(); i++) {
      Profile profFromList = (Profile)m_vctVector.elementAt(i);
      if(profFromList.getEnterprise().equals(_prof.getEnterprise()) && profFromList.getOPWGID() == _prof.getOPWGID()) {
          iOldIndex = i;
          i = size(); //break
          continue;
      }
    }
    if(iOldIndex == -1) {
      return false;
    }
    m_vctVector.remove(iOldIndex);
    m_vctVector.add(iOldIndex,_prof);
    return true;
  }

  public final String getLoginTime() {
    return m_strLoginTime;
  }

}
