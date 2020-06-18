// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2001, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: Profile.java,v $
// Revision 1.109  2014/05/15 15:14:53  wendy
// RCQ 297831 - set link limit in profile
//
// Revision 1.108  2009/01/08 17:52:04  wendy
// Correct null ptr when using profile created with getProfileForRoleCode()
//
// Revision 1.107  2007/08/08 17:32:05  wendy
// RQ0713072645 Allow override of domain check
//
// Revision 1.106  2007/07/27 13:50:29  wendy
// RQ0713072645, Added method to access just PDHDOMAIN flags
//
// Revision 1.105  2006/05/02 17:13:08  tony
// improved logic
//
// Revision 1.104  2006/05/01 14:07:58  tony
// CR103103686
// Variable record limit
//
// Revision 1.103  2006/03/01 05:06:17  dave
// cleaner 8602
//
// Revision 1.102  2006/02/13 22:13:19  dave
// backout change
//
// Revision 1.101  2006/02/13 22:00:52  dave
// no frills profile constructor
//
// Revision 1.100  2005/03/23 20:20:57  joan
// work on flag maint
//
// Revision 1.99  2005/01/18 21:46:52  dave
// more parm debug cleanup
//
// Revision 1.98  2004/08/02 19:34:01  joan
// add CGActionItem
//
// Revision 1.97  2004/07/15 15:48:40  joan
// return null if doesn't have write language
//
// Revision 1.96  2004/03/15 17:21:00  joan
// add method to get profile by role code
//
// Revision 1.95  2004/01/14 17:44:47  dave
// removing some weight - Associations phased out of profile
//
// Revision 1.94  2003/10/17 19:28:52  joan
// adjust dump method
//
// Revision 1.93  2003/10/17 17:43:15  joan
// add methods to display PDHDomain
//
// Revision 1.92  2003/09/15 01:31:58  dave
// more NLS fixes
//
// Revision 1.91  2003/08/27 16:51:31  dave
// postETSTranslation VE pre compare
//
// Revision 1.90  2003/08/22 18:32:57  dave
// Lets try remote procedure calls in contructors
//
// Revision 1.89  2003/08/21 18:57:20  dave
// update suyntex
//
// Revision 1.88  2003/08/21 18:31:35  dave
// Adding now to profile when created, and when new
// instance is spawned
//
// Revision 1.87  2003/05/15 19:27:25  dave
// implementing isReadOnly I
//
// Revision 1.86  2003/05/15 17:58:55  dave
// adding ROLE FUntion view WG locks and Read Only
//
// Revision 1.85  2003/05/14 20:30:59  dave
// need a quick fix for release that allows you to create
// a profile from an OPWGID that existed but is not current
//
// Revision 1.84  2003/05/12 18:08:53  roger
// Set a value for login time
//
// Revision 1.83  2003/05/12 18:02:13  roger
// New mutator for login time
//
// Revision 1.82  2003/05/12 18:00:44  roger
// Set a value for email address
//
// Revision 1.81  2003/05/12 17:27:47  roger
// Include properties/accessors for email address and login time
//
// Revision 1.80  2003/05/11 00:35:56  dave
// looking at getnow sequencing
//
// Revision 1.79  2003/04/14 22:13:30  dave
// clean up , and checking to ensure usEnglishOnly can actually
// write in USEnglish
//
// Revision 1.78  2003/04/08 02:49:21  dave
// commit(*)
//
// Revision 1.77  2003/04/03 00:39:41  dave
// syntax
//
// Revision 1.76  2003/04/03 00:17:08  dave
// simplifing the make profile thingie to simply copy
// over protected var.. vs. recreating them from the db
//
// Revision 1.75  2003/04/02 16:35:52  dave
// final push to dup sessiontag info when a new instand of a profile
// is created from an existing profile
//
// Revision 1.74  2003/03/27 00:40:46  gregg
// null ptr fix
//
// Revision 1.73  2003/03/26 23:31:07  gregg
// add Remote getNewInstance method
//
// Revision 1.72  2003/03/26 21:35:29  gregg
// ok, remove sessionID from toString and place it directly in equals check.
//
// Revision 1.71  2003/03/26 21:20:39  gregg
// put session id in brief dump for toString() (so that correct indexOf's are returned)
//
// Revision 1.70  2003/03/26 21:07:57  gregg
// add sessionID in dump
//
// Revision 1.69  2003/03/26 20:57:36  gregg
// setMiscObject System.out null ptr fix
//
// Revision 1.68  2003/03/26 19:00:10  gregg
// more getNewInstance: set properties on new Profile not necessarily set by Constructor.
//
// Revision 1.67  2003/03/26 18:50:39  gregg
// getNewProfileInstance method
//
// Revision 1.66  2003/03/26 00:12:07  gregg
// ROLE_FUNCTION_ATTR_ORDER_DEFAULT
//
// Revision 1.65  2003/01/14 21:02:42  dave
// adding str_valOn to dump
//
// Revision 1.64  2002/12/30 18:23:36  joan
// fix rolefunction
//
// Revision 1.63  2002/12/24 18:05:31  joan
// adjust rolefunction
//
// Revision 1.62  2002/11/08 20:25:23  dave
// more reversing stuff on opwg
//
// Revision 1.61  2002/11/01 18:32:25  roger
// Need some space
//
// Revision 1.60  2002/10/31 19:54:30  dave
// more memory trapping
//
// Revision 1.59  2002/08/30 18:00:22  roger
// Make public for a while
//
// Revision 1.58  2002/08/29 22:25:18  roger
// Protect the new constructor
//
// Revision 1.57  2002/08/29 18:45:10  roger
// TaskMaster is not same package as Middleware (protected won't cut it)
//
// Revision 1.56  2002/08/29 17:30:11  roger
// Clean up
//
// Revision 1.55  2002/08/29 17:21:46  roger
// Comment
//
// Revision 1.54  2002/08/29 17:14:52  roger
// Changes
//
// Revision 1.53  2002/08/29 16:50:41  roger
// Factor-out the common code
//
// Revision 1.52  2002/08/29 16:32:11  roger
// Fix temporarily
//
// Revision 1.51  2002/08/29 16:22:11  roger
// Changes for Profile variation
//
// Revision 1.50  2002/08/29 00:09:07  roger
// Fix it
//
// Revision 1.49  2002/08/28 23:47:47  roger
// Changes for new Profile variation
//
// Revision 1.48  2002/07/30 16:07:23  dave
// Added Default WorkGroup the process
//
// Revision 1.47  2002/07/18 20:41:14  roger
// Remove message
//
// Revision 1.46  2002/07/08 21:12:10  roger
// Chasing a bug
//
// Revision 1.45  2002/07/08 20:25:57  roger
// Found a bug in profile code
//
// Revision 1.44  2002/07/08 20:10:02  roger
// Fix some strange exception handling
//
// Revision 1.43  2002/07/03 18:03:38  gregg
// getOPName method
//
// Revision 1.42  2002/06/26 16:47:24  roger
// Clean up
//
// Revision 1.41  2002/06/24 22:07:43  roger
// Chase freeStatement
//
// Revision 1.40  2002/06/21 16:55:48  roger
// freeStatement in finally
//
// Revision 1.39  2002/05/07 18:05:40  dave
// fixing some if statements for profile
//
// Revision 1.38  2002/05/03 18:26:44  gregg
// replaced gbl6001 w/gbl8601
//
// Revision 1.37  2002/04/09 00:46:48  dave
// fixing up isEditable to include looking at read/write language
//
// Revision 1.36  2002/04/09 00:27:59  dave
// null pointer fix when changing nlsitem from multi value to unque
//
// Revision 1.35  2002/04/08 23:19:23  dave
// syntax and introduction of isLanguageUpdatable()
//
// Revision 1.34  2002/04/01 22:44:15  gregg
// getRoleFunctionDescription() accessor method added
//
// Revision 1.33  2002/03/19 04:57:44  dave
// first pass at context sensitive defaults
//
// Revision 1.32  2002/03/13 19:26:12  joan
// add getWGName method
//
// Revision 1.31  2002/03/08 22:02:43  dave
// more sp fixes and added a new Role Function for default
// values maintainer
//
// Revision 1.30  2002/03/08 21:44:45  dave
// added a defaultindex for default values testing
//
// Revision 1.29  2002/01/09 21:09:31  dave
// syntax fix
//
// Revision 1.28  2002/01/09 20:53:08  dave
// exposed the Misc Object property
//
// Revision 1.27  2002/01/02 17:50:06  dave
// sync between 1.0 and 1.1
//
// Revision 1.26  2001/12/11 01:07:46  dave
// carry forward fix from 1.0 on Flag Definitions
//
// Revision 1.25  2001/12/10 23:54:50  roger
// Added Meta and Data Translate Role Functions
//
// Revision 1.24  2001/12/07 18:48:44  dave
// right triming enterprise stuff
//
// Revision 1.23  2001/10/29 18:46:16  dave
// stripping NavPointGroups from whereused NavigateObject
//
// Revision 1.22  2001/10/24 22:01:50  roger
// Changed RoleFunction constant names to be more meaningful
//
// Revision 1.21  2001/10/24 21:54:37  roger
// RoleFunction no longer has int
//
// Revision 1.20  2001/10/24 21:44:13  roger
// Had to fix constant names
//
// Revision 1.19  2001/10/24 21:38:43  roger
// RoleFunction has new values
//
// Revision 1.18  2001/09/26 19:42:42  roger
// Fixes
//
// Revision 1.17  2001/09/26 18:12:48  roger
// Fix
//
// Revision 1.16  2001/09/26 18:05:49  roger
// Associations only come from WG attributes
//
// Revision 1.15  2001/09/20 23:27:03  roger
// Needed enterprise cause OPWGID is not unique across DB
//
// Revision 1.14  2001/09/20 20:49:19  roger
// New accessors
//
// Revision 1.13  2001/09/19 23:16:06  roger
// Equals method needs to include Enterprise
//
// Revision 1.12  2001/09/19 21:10:36  roger
// Needed to show RoleDescription as well
//
// Revision 1.11  2001/09/19 16:57:54  roger
// Needed accessor
//
// Revision 1.10  2001/09/19 16:47:37  roger
// Include RoleDescription in Profile
//
// Revision 1.9  2001/09/18 19:09:59  dave
// meta fix for NLSID 10.. needed to be 1
//
// Revision 1.8  2001/09/17 20:24:57  roger
// Put final on methods
//
// Revision 1.7  2001/09/12 21:18:16  roger
// Profile changes
//
// Revision 1.6  2001/09/12 20:48:21  roger
// Profile changes
//
// Revision 1.5  2001/09/12 17:50:04  roger
// Added comment
//
// Revision 1.4  2001/09/12 17:15:19  roger
// New Accessor
//
// Revision 1.3  2001/09/11 00:03:18  roger
// Start index with 0
//
// Revision 1.2  2001/09/10 19:59:24  roger
// Needed accessor for OPWGID
//
// Revision 1.1  2001/09/07 22:19:41  roger
// Move the Profile stuff
//
// Revision 1.76  2001/09/07 20:51:49  roger
// Set default language to 1st entry (if there is no first entry attempts to use language will throw exception)
//
// Revision 1.75  2001/09/07 17:20:30  roger
// Make toString more concise
//
// Revision 1.74  2001/08/23 18:53:51  roger
// Start using DatePackage
//
// Revision 1.73  2001/08/23 18:04:18  roger
// Misc
//
// Revision 1.72  2001/08/23 16:23:54  roger
// Comment out and remove some code
//
// Revision 1.71  2001/08/23 15:55:55  roger
// Needed a get for Associations
//
// Revision 1.70  2001/08/22 16:52:50  roger
// Removed author RM
//
// Revision 1.69  2001/08/21 21:37:10  roger
// Misc
//
// Revision 1.68  2001/08/21 19:47:36  roger
// Start log again
//
package COM.ibm.opicmpdh.middleware;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import COM.ibm.eannounce.objects.LinkActionItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.transactions.NLSItem;
import java.rmi.RemoteException;

/**
 *  Description of the Class
 *
 * @author     davidbig
 * @created    April 14, 2003
 */
class RoleFunction implements Serializable {
  private String m_strRole = null;
  private String m_strRoleDescription = null;


  /**
   *Constructor for the RoleFunction object
   *
   * @param  _strRole             Description of the Parameter
   * @param  _strRoleDescription  Description of the Parameter
   */
  public RoleFunction(String _strRole, String _strRoleDescription) {
    m_strRole = _strRole;
    m_strRoleDescription = _strRoleDescription;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public final String dump() {
    return dump(false);
  }


  /**
   *  Description of the Method
   *
   * @param  _bBrief  Description of the Parameter
   * @return          Description of the Return Value
   */
  public final String dump(boolean _bBrief) {

    StringBuffer strbResult = new StringBuffer();

    if (_bBrief) {
      strbResult.append(m_strRoleDescription);
    } else {
      strbResult.append("Role: " + m_strRole);
      strbResult.append("\nDescription: " + m_strRoleDescription);
    }

    return strbResult.toString();
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public final String toString() {
    return dump(true);
  }


  /**
   *  Description of the Method
   *
   * @param  _o  Description of the Parameter
   * @return     Description of the Return Value
   */
  public final boolean equals(Object _o) {
    return ((_o instanceof RoleFunction) && (m_strRole.equals((((RoleFunction) _o).m_strRole))));
  }


  /**
   *  Gets the description attribute of the RoleFunction object
   *
   * @return    The description value
   */
  protected String getDescription() {
    return m_strRoleDescription;
  }
  protected String getRole(){
	  return m_strRole;
  }
}

class PDHDomain implements Serializable {
  private String m_strPDHDomain = null;
  private String m_strPDHDomainDescription = null;


  /**
   *Constructor for the PDHDomain object
   *
   * @param  _strPDHDomain             Description of the Parameter
   * @param  _strPDHDomainDescription  Description of the Parameter
   */
  public PDHDomain(String _strPDHDomain, String _strPDHDomainDescription) {
    m_strPDHDomain = _strPDHDomain;
    m_strPDHDomainDescription = _strPDHDomainDescription;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public final String dump() {
    return dump(false);
  }


  /**
   *  Description of the Method
   *
   * @param  _bBrief  Description of the Parameter
   * @return          Description of the Return Value
   */
  public final String dump(boolean _bBrief) {

    StringBuffer strbResult = new StringBuffer();

    if (_bBrief) {
      strbResult.append(m_strPDHDomainDescription);
    } else {
      strbResult.append("PDHDomain: " + m_strPDHDomain);
      strbResult.append("\nDescription: " + m_strPDHDomainDescription);
    }

    return strbResult.toString();
  }

  /**  RQ0713072645
   * get flagcode for this pdhdomain
   * @return flag code for this pdhdomain
   */
  public final String dumpFlagCode() {
      return m_strPDHDomain;
  }

  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public final String toString() {
    return dump(true);
  }


  /**
   *  Description of the Method
   *
   * @param  _o  Description of the Parameter
   * @return     Description of the Return Value
   */
  public final boolean equals(Object _o) {
    return ((_o != null) && (_o instanceof PDHDomain) && (m_strPDHDomain.equals((((PDHDomain) _o).m_strPDHDomain))));
  }


  /**
   *  Gets the description attribute of the PDHDomain object
   *
   * @return    The description value
   */
  protected String getDescription() {
    return m_strPDHDomainDescription;
  }
}

/**
 * A class which defines the Login Profile object
 *
 * @author     davidbig
 * @created    April 14, 2003
 * @version
 * @date
 */
public final class Profile implements Serializable {


  // Class constants

  /**
   *  Description of the Field
   */
  public final static String OP_TYPE = new String("OP");
  public final static String WG_TYPE = new String("WG");
  public final static String OPWG_TYPE = new String("OPWG");
  private final static int DUMMY_INT = 0;
  public final static NLSItem ENGLISH_LANGUAGE = new NLSItem(1, "English");
  public final static NLSItem GERMAN_LANGUAGE = new NLSItem(2, "German");
  public final static NLSItem ITALIAN_LANGUAGE = new NLSItem(3, "Italian");
  public final static NLSItem JAPANESE_LANGUAGE = new NLSItem(4, "Japanese");
  public final static NLSItem FRENCH_LANGUAGE = new NLSItem(5, "French");
  public final static NLSItem SPANISH_LANGUAGE = new NLSItem(6, "Spanish");
  public final static NLSItem UK_ENGLISH_LANGUAGE = new NLSItem(7, "UK English");
  public final static NLSItem KOREAN_LANGUAGE = new NLSItem(8, "Korean");
  public final static NLSItem CHINESE_LANGUAGE = new NLSItem(9, "Chinese");
  public final static NLSItem FRENCH_CANADIAN_LANGUAGE = new NLSItem(10, "French Canadian");
  public final static NLSItem CHINESE_SIMPLIFIED_LANGUAGE = new NLSItem(11, "Chinese Simplified");
  public final static NLSItem SPANISH_LATINAMERICAN_LANGUAGE = new NLSItem(12, "Spanish (Latin American)");
  public final static NLSItem PORTUGUESE_BRAZILIAN_LANGUAGE = new NLSItem(13, "Portuguese (Braziliian)");

  public final static RoleFunction ROLE_FUNCTION_ATTR_ORDER_DEFAULT = new RoleFunction("0005", "Attribute Order Default Setting");
  public final static RoleFunction ROLE_FUNCTION_READONLY = new RoleFunction("1000", "Read Only");
  public final static RoleFunction ROLE_FUNCTION_DEFAULT_VALUES = new RoleFunction("0020", "Workgroup Default Setting");
  public final static RoleFunction ROLE_FUNCTION_SUBMIT_DG = new RoleFunction("0030", "SubmitDG");
  public final static RoleFunction ROLE_FUNCTION_SUPERVISOR = new RoleFunction("0040", "Supervisor");
  public final static RoleFunction ROLE_FUNCTION_USER_SUPERVISOR = new RoleFunction("0050", "User Supervisor");
  public final static RoleFunction ROLE_FUNCTION_ADMINISTRATOR_UPDATE = new RoleFunction("0060", "ADMINISTRATOR");
  public final static RoleFunction ROLE_FUNCTION_ADMINISTRATOR = new RoleFunction("0070", "Administrator");
  public final static RoleFunction ROLE_FUNCTION_DEVELOPER_UPDATE = new RoleFunction("0080", "DEVELOPER");
  public final static RoleFunction ROLE_FUNCTION_DEVELOPER = new RoleFunction("0090", "Developer");
  public final static RoleFunction ROLE_FUNCTION_FLAGTRANSLATE = new RoleFunction("0100", "FLAGTRANSLATE");
  public final static RoleFunction ROLE_FUNCTION_MAINTAINER_UPDATE = new RoleFunction("0110", "MAINTAINER");
  public final static RoleFunction ROLE_FUNCTION_MAINTAINER = new RoleFunction("0120", "Maintainer");
  public final static RoleFunction ROLE_FUNCTION_VIEWER_UPDATE = new RoleFunction("0130", "VIEWER");
  public final static RoleFunction ROLE_FUNCTION_VIEWER = new RoleFunction("0140", "Viewer");
  public final static RoleFunction ROLE_FUNCTION_FLAGTRANSLATION = new RoleFunction("0150", "Flagtranslation");
  public final static RoleFunction ROLE_FUNCTION_METATRANSLATE_SUBMISSION = new RoleFunction("0160", "Meta Translation Submission");
  public final static RoleFunction ROLE_FUNCTION_METATRANSLATE_VALIDATION = new RoleFunction("0180", "Meta Translation Validation");
  public final static RoleFunction ROLE_FUNCTION_DATATRANSLATE = new RoleFunction("0170", "Data Translation");
  public final static RoleFunction ROLE_FUNCTION_WGLOCKS = new RoleFunction("WGLOCKS", "View Workgroup Locks");
  public final static RoleFunction ROLE_FUNCTION_ADDFLAG = new RoleFunction("0140", "Add Flag");

  public final static String FOREVER = "9999-12-31-00.00.00.000000";
  public final static String EPOCH = "1980-01-01-00.00.00.000000";

  /**
   * @serial
   */
  final static long serialVersionUID = 1L;
  protected String m_strEnterprise = null;
  protected int m_iOPID = -1;
  protected String m_strOPName = null;
  protected int m_iWGID = -1;
  protected String m_strWGName = null;
  protected int m_iOPWGID = -1;
  protected String m_strOPWGName = null;
  protected String m_strRoleCode = null;
  protected String m_strRoleDescription = null;
  protected Vector m_vctRoleFunctions = null;
  protected Vector m_vctNLSReads = null;
  protected Vector m_vctNLSWrites = null;
  protected Vector m_vctPDHDomain = null;
  protected String m_strNLSRead = null;
  protected String m_strNLSWrite = null;
  protected String m_strValOn = null;
  protected String m_strEffOn = null;
  protected String m_strNow = null;
  protected String m_strEndOfDay = null;
  protected int m_iTranID = DUMMY_INT;
  protected transient Object mt_objMisc = null;
  protected int m_iNLSRead = 0;
  protected int m_iNLSWrite = 0;
  protected int m_iSessionID = 0;
  protected ControlBlock cbControlBlock = null;
  protected int m_iDefaultIndex = -1;
  protected String m_strEmailAddress = null;
  protected String m_strLoginTime = null;
  protected int m_iLimit = 250;		//CR103103686
  private int m_iLinkLimit = LinkActionItem.LINKLIMIT;		//RCQ297831

  private boolean m_enforceDomain = false;  //RQ0713072645

  /**
   *Constructor for the Profile object
   */
  protected Profile() { }

  public Profile(Database _dbCurrent, String _strEnterprise, int _iOPWGID) throws SQLException, MiddlewareException {
    this(_dbCurrent,_strEnterprise,_iOPWGID,false);
  }

  /**
   *Constructor for the Profile object
   *
   * @param  _dbCurrent               Description of the Parameter
   * @param  _strEnterprise           Description of the Parameter
   * @param  _iOPWGID                 Description of the Parameter
   * @exception  SQLException         Description of the Exception
   * @exception  MiddlewareException  Description of the Exception
   */
  public Profile(Database _dbCurrent, String _strEnterprise, int _iOPWGID, boolean _bAny) throws SQLException, MiddlewareException {

    ReturnStatus returnStatus = new ReturnStatus(-1);
    ResultSet rs = null;
    DatePackage dpCurrent = null;
    //Profile prof = null;

    try {
      _dbCurrent.freeStatement();
      _dbCurrent.isPending("before getDates in Profile OPWGID");

      dpCurrent = _dbCurrent.getDates();

      String strNow = dpCurrent.getNow();
      m_strNow = dpCurrent.getNow();

      // Determine op+opwg+wg info for given opwgid
      D.ebug(D.EBUG_SPEW, "get 8602 info for OPWGID: " + _iOPWGID);

      rs = _dbCurrent.callGBL8602(returnStatus, _strEnterprise, Profile.OP_TYPE, Profile.OPWG_TYPE, _iOPWGID, strNow, strNow, (_bAny ? 1 : 0));

      if (rs.next()) {
          // create a profile for the given info from 8602
          getDetail(_dbCurrent, rs.getString(1), rs.getInt(3), rs.getString(4).trim(), rs.getInt(9), rs.getString(10), rs.getInt(6), rs.getString(7), rs.getString(11), rs.getString(12));
      } else {
          throw new LoginException("_iOPWGID is not valid:" + _iOPWGID);
      }
      _dbCurrent.freeStatement();
      _dbCurrent.isPending("after 8602 call in Profile");
    } catch (Exception z) {
      D.ebug("something happened while building profile using OPWGID " + z);
    }

    try {
      rs.close();
    } catch (Exception x) {
      D.ebug("close had trouble in profile constructor using OPWGID" + x);
    }

    rs = null;
    _dbCurrent.commit();
    _dbCurrent.freeStatement();
    _dbCurrent.isPending("at end of Profile constructor OPWGID");

  }


  /**
   *Constructor for the Profile object
   *
   * @param  _dbCurrent               Description of the Parameter
   * @param  _strEnterprise           Description of the Parameter
   * @param  _iOPID                   Description of the Parameter
   * @param  _strOPName               Description of the Parameter
   * @param  _iWGID                   Description of the Parameter
   * @param  _strWGName               Description of the Parameter
   * @param  _iOPWGID                 Description of the Parameter
   * @param  _strOPWGName             Description of the Parameter
   * @param  _strRoleCode             Description of the Parameter
   * @param  _strRoleDescription      Description of the Parameter
   * @exception  SQLException         Description of the Exception
   * @exception  MiddlewareException  Description of the Exception
   */
  protected Profile(Database _dbCurrent, String _strEnterprise, int _iOPID, String _strOPName, int _iWGID, String _strWGName, int _iOPWGID, String _strOPWGName, String _strRoleCode, String _strRoleDescription) throws SQLException, MiddlewareException {

    getDetail(_dbCurrent, _strEnterprise, _iOPID, _strOPName, _iWGID, _strWGName, _iOPWGID, _strOPWGName, _strRoleCode, _strRoleDescription);
  }


  /**
   * Make a new profile to minimal stuff
   *
   * @param _passphrase String
   */
/*  public Profile(String _strEnterprise, String _passphrase) {

      m_strEnterprise = _strEnterprise.trim();
      m_iOPID = 1;
      m_strOPName = "MFG OP " + _passphrase ;
      m_iWGID = 1;
      m_strWGName = "MFG WG" ;
      m_iOPWGID = 8080;
      m_strOPWGName = "Mfg OPWG";
      m_strRoleCode = "VIEWALL";
      m_strRoleDescription = "VIEW ALL";
      m_vctRoleFunctions = new Vector();
      m_vctPDHDomain = new Vector();
      m_vctNLSReads = new Vector();
      m_vctNLSReads.add(new NLSItem(1, "US English"));
      m_vctNLSWrites = new Vector();
      m_strEmailAddress = "na";
  }
*/

  /**
   *  Get detail information for a Profile
   *
   * @param  _dbCurrent               Description of the Parameter
   * @param  _strEnterprise           Description of the Parameter
   * @param  _iOPID                   Description of the Parameter
   * @param  _strOPName               Description of the Parameter
   * @param  _iWGID                   Description of the Parameter
   * @param  _strWGName               Description of the Parameter
   * @param  _iOPWGID                 Description of the Parameter
   * @param  _strOPWGName             Description of the Parameter
   * @param  _strRoleCode             Description of the Parameter
   * @param  _strRoleDescription      Description of the Parameter
   * @exception  SQLException         Description of the Exception
   * @exception  MiddlewareException  Description of the Exception
   */
  protected final void getDetail(Database _dbCurrent, String _strEnterprise, int _iOPID, String _strOPName, int _iWGID, String _strWGName, int _iOPWGID, String _strOPWGName, String _strRoleCode, String _strRoleDescription) throws SQLException, MiddlewareException {

    ReturnStatus returnStatus = new ReturnStatus(-1);
    ResultSet rs = null;
    DatePackage dpCurrent = null;

    m_strEnterprise = _strEnterprise.trim();
    m_iOPID = _iOPID;
    m_strOPName = _strOPName.trim();
    m_iWGID = _iWGID;
    m_strWGName = _strWGName.trim();
    m_iOPWGID = _iOPWGID;
    m_strOPWGName = _strOPWGName.trim();
    m_strRoleCode = _strRoleCode.trim();
    m_strRoleDescription = _strRoleDescription.trim();
    m_vctRoleFunctions = new Vector();
    m_vctPDHDomain = new Vector();
    m_vctNLSReads = new Vector();
    m_vctNLSWrites = new Vector();
    m_strEmailAddress = _strOPName.trim();
	m_iLimit = 250;			//CR103103686
	m_iLinkLimit = LinkActionItem.LINKLIMIT;		//RCQ297831
	
	m_enforceDomain = MiddlewareServerProperties.getEnforcePDHDomain(); //RQ0713072645

    try {
      _dbCurrent.freeStatement();
      _dbCurrent.isPending("before getDates in Profile");

      dpCurrent = _dbCurrent.getDates();

      String strNow = dpCurrent.getNow();
      m_strLoginTime = strNow;
      m_strNow = strNow;
      m_iSessionID = _dbCurrent.getNewSessionID();

      _dbCurrent.freeStatement();
      _dbCurrent.isPending("before call 8601 in Profile");

      // Get OPWG properties for this profile
      rs = _dbCurrent.callGBL8601(returnStatus, m_strEnterprise, Profile.OPWG_TYPE, m_iOPWGID, strNow, strNow);

      D.ebug(D.EBUG_SPEW, "get 8601 info for OPWGID: " + m_iOPWGID);
      
      // reduce memory by reusing existing RoleFunction and NLSItems
      Hashtable roleFuncTbl = loadRoleFunctions();
      Hashtable nlsTbl = loadNLSItems();
      
      while (rs.next()) {
        String strWhich = rs.getString(1);
        int iOPWGID = rs.getInt(2);
        // Attribute Type is "AttributeCode"
        String strAttributeType = rs.getString(3).trim();
        // Entity Class is TFU
        String strEntityClass = rs.getString(4).trim();
        // First AV is #### if flag
        String strAttributeValue1 = rs.getString(5).trim();
        String strAttributeValue2 = rs.getString(6).trim();

        D.ebug(D.EBUG_SPEW, "8601 - " + iOPWGID + " " + strWhich + " " + strAttributeType + " " + strEntityClass + " " + strAttributeValue1 + " " + strAttributeValue2);

        if (strWhich.equalsIgnoreCase("OPWG")) {
          if (strAttributeType.equalsIgnoreCase("ROLEFUNCTION")) {
        	  RoleFunction rf = (RoleFunction)roleFuncTbl.get(strAttributeValue1);
        	  if(rf ==null){
        		  rf = new RoleFunction(strAttributeValue1, strAttributeValue2);
        	  }
              m_vctRoleFunctions.addElement(rf);
          } else if (strAttributeType.equalsIgnoreCase("NLSREAD")) {
        	  NLSItem nls = (NLSItem)nlsTbl.get(strAttributeValue1);
        	  if(nls == null){
        		  nls = new NLSItem(Integer.parseInt(strAttributeValue1), strAttributeValue2);
        	  }
              m_vctNLSReads.addElement(nls);
          } else if (strAttributeType.equalsIgnoreCase("NLSWRITE")) {
        	  NLSItem nls = (NLSItem)nlsTbl.get(strAttributeValue1);
        	  if(nls == null){
        		  nls = new NLSItem(Integer.parseInt(strAttributeValue1), strAttributeValue2);
        	  }
              m_vctNLSWrites.addElement(nls);
          } else if (strAttributeType.equalsIgnoreCase("RECORDLIMIT")) {	//CR103103686
			setRecordLimit(strAttributeValue1);								//CR103103686
		  }else if (strAttributeType.equalsIgnoreCase("LINKLIMIT")) {	      //RCQ297831
			setLinkLimit(strAttributeValue1);								  //RCQ297831
		  }
        } else if (strWhich.equalsIgnoreCase("WG")) {
          if (strAttributeType.equalsIgnoreCase("DEFAULTINDEX")) {
            m_iDefaultIndex = -(Integer.valueOf(strAttributeValue1).intValue());
          } else if (strAttributeType.equalsIgnoreCase("PDHDOMAIN")) {
            m_vctPDHDomain.addElement(new PDHDomain(strAttributeValue1, strAttributeValue2));
          }
        }
      }
      
      roleFuncTbl.clear();
      roleFuncTbl = null;
      nlsTbl.clear();
      nlsTbl = null;
    } catch (Exception z) {
      D.ebug("something happened while building profiles " + z);
    }

    try {
      rs.close();
      _dbCurrent.commit();
    } catch (Exception x) {
      D.ebug("close had trouble in profile constructor " + x);
    }

    rs = null;

    _dbCurrent.freeStatement();
    _dbCurrent.isPending("at end of Profile constructor");

    D.ebug(D.EBUG_SPEW, "created profile " + this.dump(false));
  }

 /**
  * get all existing rolefunction objects
  * @return
  */
  private Hashtable loadRoleFunctions(){
	  Hashtable roleFuncTbl = new Hashtable();
	  roleFuncTbl.put(ROLE_FUNCTION_ATTR_ORDER_DEFAULT.getRole(), ROLE_FUNCTION_ATTR_ORDER_DEFAULT);
	  roleFuncTbl.put(ROLE_FUNCTION_READONLY.getRole(), ROLE_FUNCTION_READONLY);
	  roleFuncTbl.put(ROLE_FUNCTION_DEFAULT_VALUES.getRole(), ROLE_FUNCTION_DEFAULT_VALUES);
	  roleFuncTbl.put(ROLE_FUNCTION_SUBMIT_DG.getRole(), ROLE_FUNCTION_SUBMIT_DG);
	  roleFuncTbl.put(ROLE_FUNCTION_SUPERVISOR.getRole(), ROLE_FUNCTION_SUPERVISOR);
	  roleFuncTbl.put(ROLE_FUNCTION_USER_SUPERVISOR.getRole(), ROLE_FUNCTION_USER_SUPERVISOR);
	  roleFuncTbl.put(ROLE_FUNCTION_ADMINISTRATOR_UPDATE.getRole(), ROLE_FUNCTION_ADMINISTRATOR_UPDATE);
	  roleFuncTbl.put(ROLE_FUNCTION_ADMINISTRATOR.getRole(), ROLE_FUNCTION_ADMINISTRATOR);
	  roleFuncTbl.put(ROLE_FUNCTION_DEVELOPER_UPDATE.getRole(), ROLE_FUNCTION_DEVELOPER_UPDATE);
	  roleFuncTbl.put(ROLE_FUNCTION_DEVELOPER.getRole(), ROLE_FUNCTION_DEVELOPER);
	  roleFuncTbl.put(ROLE_FUNCTION_FLAGTRANSLATE.getRole(), ROLE_FUNCTION_FLAGTRANSLATE);
	  roleFuncTbl.put(ROLE_FUNCTION_MAINTAINER_UPDATE.getRole(), ROLE_FUNCTION_MAINTAINER_UPDATE);
	  roleFuncTbl.put(ROLE_FUNCTION_MAINTAINER.getRole(), ROLE_FUNCTION_MAINTAINER);
	  roleFuncTbl.put(ROLE_FUNCTION_VIEWER_UPDATE.getRole(), ROLE_FUNCTION_VIEWER_UPDATE);
	  roleFuncTbl.put(ROLE_FUNCTION_VIEWER.getRole(), ROLE_FUNCTION_VIEWER);
	  roleFuncTbl.put(ROLE_FUNCTION_FLAGTRANSLATION.getRole(), ROLE_FUNCTION_FLAGTRANSLATION);
	  roleFuncTbl.put(ROLE_FUNCTION_METATRANSLATE_SUBMISSION.getRole(), ROLE_FUNCTION_METATRANSLATE_SUBMISSION);
	  roleFuncTbl.put(ROLE_FUNCTION_METATRANSLATE_VALIDATION.getRole(), ROLE_FUNCTION_METATRANSLATE_VALIDATION);
	  roleFuncTbl.put(ROLE_FUNCTION_DATATRANSLATE.getRole(), ROLE_FUNCTION_DATATRANSLATE);
	  roleFuncTbl.put(ROLE_FUNCTION_WGLOCKS.getRole(), ROLE_FUNCTION_WGLOCKS);
	  roleFuncTbl.put(ROLE_FUNCTION_ADDFLAG.getRole(), ROLE_FUNCTION_ADDFLAG);

	  return roleFuncTbl;
  }
  /**
   * get all existing nls objects
   * @return
   */
   private Hashtable loadNLSItems(){
 	  Hashtable nlsTbl = new Hashtable();
 	  nlsTbl.put(Integer.toString(ENGLISH_LANGUAGE.getNLSID()), ENGLISH_LANGUAGE);
 	  nlsTbl.put(Integer.toString(GERMAN_LANGUAGE.getNLSID()), GERMAN_LANGUAGE);
 	  nlsTbl.put(Integer.toString(ITALIAN_LANGUAGE.getNLSID()), ITALIAN_LANGUAGE);
 	  nlsTbl.put(Integer.toString(JAPANESE_LANGUAGE.getNLSID()), JAPANESE_LANGUAGE);
 	  nlsTbl.put(Integer.toString(FRENCH_LANGUAGE.getNLSID()), FRENCH_LANGUAGE);
 	  nlsTbl.put(Integer.toString(SPANISH_LANGUAGE.getNLSID()), SPANISH_LANGUAGE);
 	  nlsTbl.put(Integer.toString(UK_ENGLISH_LANGUAGE.getNLSID()), UK_ENGLISH_LANGUAGE);
 	  nlsTbl.put(Integer.toString(KOREAN_LANGUAGE.getNLSID()), KOREAN_LANGUAGE);
 	  nlsTbl.put(Integer.toString(CHINESE_LANGUAGE.getNLSID()), CHINESE_LANGUAGE);
 	  nlsTbl.put(Integer.toString(FRENCH_CANADIAN_LANGUAGE.getNLSID()), FRENCH_CANADIAN_LANGUAGE);
 	  nlsTbl.put(Integer.toString(CHINESE_SIMPLIFIED_LANGUAGE.getNLSID()), CHINESE_SIMPLIFIED_LANGUAGE);
 	  nlsTbl.put(Integer.toString(SPANISH_LATINAMERICAN_LANGUAGE.getNLSID()), SPANISH_LATINAMERICAN_LANGUAGE);
 	  nlsTbl.put(Integer.toString(PORTUGUESE_BRAZILIAN_LANGUAGE.getNLSID()), PORTUGUESE_BRAZILIAN_LANGUAGE);

 	  return nlsTbl;
   }

  /**
   * Get a copy of this profile: same properties, but w/ a newly generated sessionid.
   *
   * @param  _db                      Description of the Parameter
   * @return                          The newInstance value
   * @exception  SQLException         Description of the Exception
   * @exception  MiddlewareException  Description of the Exception
   */
  public Profile getNewInstance(Database _db) throws SQLException, MiddlewareException {

    Profile profNew = new Profile();

    profNew.m_strEnterprise = m_strEnterprise;
    profNew.m_iOPID = m_iOPID;
    profNew.m_strOPName = m_strOPName;
    profNew.m_iWGID = m_iWGID;
    profNew.m_strWGName = m_strWGName;
    profNew.m_iOPWGID = m_iOPWGID;
    profNew.m_strOPWGName = m_strOPWGName;
    profNew.m_strRoleCode = m_strRoleCode;
    profNew.m_strRoleDescription = m_strRoleDescription;
    profNew.m_vctRoleFunctions = m_vctRoleFunctions;
    profNew.m_vctPDHDomain = m_vctPDHDomain;
    profNew.m_vctNLSReads = m_vctNLSReads;
    profNew.m_vctNLSWrites = m_vctNLSWrites;
    profNew.m_strNLSRead = m_strNLSRead;
    profNew.m_strNLSWrite = m_strNLSWrite;
    profNew.m_strValOn = m_strValOn;
    profNew.m_strEffOn = m_strEffOn;
    profNew.m_strNow = m_strNow;
    profNew.m_strEndOfDay = m_strEndOfDay;
    profNew.m_iTranID = m_iTranID;
    profNew.mt_objMisc = mt_objMisc;
    profNew.m_iNLSRead = m_iNLSRead;
    profNew.m_iNLSWrite = m_iNLSWrite;
    profNew.cbControlBlock = cbControlBlock;
    profNew.m_iDefaultIndex = m_iDefaultIndex;
	profNew.m_iLimit = m_iLimit;				//CR103103686
	profNew.m_iLinkLimit = m_iLinkLimit;		//RCQ297831
	profNew.m_enforceDomain = m_enforceDomain;  //RQ0713072645


    // Get the new SessionID
    profNew.m_iSessionID = _db.getNewSessionID();


    // Now transfer any tagging infor the new Profile OPWG/SessionID combo
    ReturnStatus returnStatus = new ReturnStatus(-1);
    _db.callGBL7548(returnStatus, m_strEnterprise, m_iSessionID, m_iOPWGID, profNew.m_iSessionID);
    _db.commit();
    _db.freeStatement();
    _db.isPending();

    // Set up a new Now
    DatePackage dp = _db.getDates();
    profNew.m_strNow = dp.getNow();

    return profNew;
  }

  public Profile getProfileForRoleCode(Database _db, String _strRoleCode, String _strRoleDescription, int _iNLSRead) throws SQLException, MiddlewareException {
	  Profile profNew = new Profile();

	  // was missing setting up some things like m_vctRoleFunctions, got null ptr when tried to use the profile
	  // get the detail, then use original values as overrides
	  profNew.getDetail(_db, m_strEnterprise, m_iOPID, m_strOPName, m_iWGID, m_strWGName, m_iOPWGID,
			  m_strOPWGName, _strRoleCode, _strRoleDescription);
	  // profNew.m_strEnterprise = m_strEnterprise;
	  //profNew.m_iOPID = m_iOPID;
	  //profNew.m_strOPName = m_strOPName;
	  //profNew.m_iWGID = m_iWGID;
	  //profNew.m_strWGName = m_strWGName;
	  //profNew.m_iOPWGID = m_iOPWGID;
	  //profNew.m_strOPWGName = m_strOPWGName;
	  //profNew.m_strRoleCode = _strRoleCode;
	  //profNew.m_strRoleDescription = _strRoleDescription;
	  
	  profNew.m_vctNLSReads = m_vctNLSReads;
	  profNew.m_vctNLSWrites = m_vctNLSWrites;
	  profNew.m_strNLSRead = m_strNLSRead;
	  profNew.m_strNLSWrite = m_strNLSWrite;
	  profNew.m_strValOn = m_strValOn;
	  profNew.m_strEffOn = m_strEffOn;
	  profNew.m_strEndOfDay = m_strEndOfDay;
	  profNew.m_iTranID = m_iTranID;
	  profNew.mt_objMisc = mt_objMisc;
	  profNew.m_iNLSRead = _iNLSRead;
	  profNew.m_iNLSWrite = m_iNLSWrite;
	  profNew.cbControlBlock = cbControlBlock;
	  profNew.m_iDefaultIndex = m_iDefaultIndex;
	  profNew.m_iLimit = m_iLimit;		//CR103103686
	  profNew.m_iLinkLimit = m_iLinkLimit;		//RCQ297831
	  profNew.m_enforceDomain = m_enforceDomain;  //RQ0713072645


	  // Set up a new Now
	  DatePackage dp = _db.getDates();
	  profNew.m_strNow = dp.getNow();

	  return profNew;
  }

  /**
   * Get a copy of this profile: same properties, but w/ a newly generated sessionid.
   *
   * @param  _rdi                                       Description of the Parameter
   * @return                                            The newInstance value
   * @exception  RemoteException                        Description of the Exception
   * @exception  MiddlewareException                    Description of the Exception
   * @exception  MiddlewareRequestException             Description of the Exception
   * @exception  MiddlewareShutdownInProgressException  Description of the Exception
   */
  public Profile getNewInstance(RemoteDatabaseInterface _rdi) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
    return _rdi.getNewProfileInstance(this);
  }


  /**
   * @param  _roleFunction  Description of the Parameter
   * @return                Description of the Return Value
   */
  public final boolean hasRoleFunction(RoleFunction _roleFunction) {
    return m_vctRoleFunctions.contains(_roleFunction);
  }

  /**
   * @param  _pdhDomain  Description of the Parameter
   * @return                Description of the Return Value
   */
  public final boolean hasPDHDomain(PDHDomain _pdhDomain) {
    return m_vctPDHDomain.contains(_pdhDomain);
  }

  /**
   * @param  _roleFunction  Description of the Parameter
   * @return                The roleFunctionDescription value
   */
  public final String getRoleFunctionDescription(RoleFunction _roleFunction) {
    return _roleFunction.getDescription();
  }


  /**
   * @param  _pdhDomain  Description of the Parameter
   * @return                The roleFunctionDescription value
   */
  public final String getPDHDomainDescription(PDHDomain _pdhDomain) {
    return _pdhDomain.getDescription();
  }

  /**
   * @return    The enterprise value
   */
  public final String getEnterprise() {
    return m_strEnterprise;
  }


  /**
   * @return    The roleCode value
   */
  public final String getRoleCode() {
    return m_strRoleCode;
  }


  /**
   * @return    The roleDescription value
   */
  public final String getRoleDescription() {
    return m_strRoleDescription;
  }


  /**
   * @return    The sessionID value
   */
  public final int getSessionID() {
    return m_iSessionID;
  }


  /**
   * @return    The miscObject value
   */
  public final Object getMiscObject() {
    System.out.println("Profile get MiscObject is being accessed:");
    return mt_objMisc;
  }


  /**
   * @return    The defaultIndex value
   */
  public final int getDefaultIndex() {
    return m_iDefaultIndex;
  }


  /**
   * @return    The valOn value
   */
  public final String getValOn() {
    return m_strValOn;
  }


  /**
  * @param  _strValOn  The new valOn value
  */
  public final void setValOn(String _strValOn) {
    m_strValOn = _strValOn;
  }

  public final void setNow(String _str) {
    m_strNow = _str;
  }
  public final void setEndOfDay(String _str) {
    m_strEndOfDay = _str;
  }


  /**
   * @return    The effOn value
   */
  public final String getEffOn() {
    return m_strEffOn;
  }

  public final String getNow() {
    return m_strNow;
  }

  public final String getEndOfDay() {
    return m_strEndOfDay;
  }


  /**
   * @param  _obj  The new miscObject value
   */
  public final void setMiscObject(Object _obj) {
    if (_obj != null) {
      System.out.println("Profile set MiscObject is being accessed:" + _obj.getClass().getName());
    } else {
      System.out.println("Profile set MiscObject - object is null");
    }
    mt_objMisc = _obj;
  }


  /**
   * @param  _strEffOn  The new effOn value
   */
  public final void setEffOn(String _strEffOn) {
    m_strEffOn = _strEffOn;
  }


  /**
   * @param  _strValOn  The new valOnEffOn value
   * @param  _strEffOn  The new valOnEffOn value
   */
  public final void setValOnEffOn(String _strValOn, String _strEffOn) {
    m_strValOn = _strValOn;
    m_strEffOn = _strEffOn;
  }


  /**
   * @return    The readLanguages value
   */
  public final Vector getReadLanguages() {
    return m_vctNLSReads;
  }


  /**
   * @return    The writeLanguages value
   */
  public final Vector getWriteLanguages() {
    return m_vctNLSWrites;
  }


  /**
   * @param  _nlsItem  Description of the Parameter
   * @return           Description of the Return Value
   */
  public final boolean hasReadLanguage(NLSItem _nlsItem) {
    return m_vctNLSReads.contains(_nlsItem);
  }


  /**
   * @param  _nlsItem  Description of the Parameter
   * @return           Description of the Return Value
   */
  public final boolean hasWriteLanguage(NLSItem _nlsItem) {
    return m_vctNLSWrites.contains(_nlsItem);
  }


  /**
   *  Gets the languageUpdatable attribute of the Profile object
   *
   * @return    The languageUpdatable value
   */
  public final boolean isLanguageUpdatable() {
    return hasWriteLanguage(getReadLanguage());
  }


  /**
   *  Gets the uSEnglishUpdatable attribute of the Profile object
   *
   * @return    The uSEnglishUpdatable value
   */
  public final boolean isUSEnglishUpdatable() {
    return hasWriteLanguage(ENGLISH_LANGUAGE);
  }


  /**
   * @param  _i  The new readLanguage value
   */
  public final void setReadLanguage(int _i) {
    NLSItem nlsItem = (NLSItem) m_vctNLSReads.elementAt(_i);

    m_iNLSRead = _i;
  }


  /**
   * @param  _nlsItem  The new readLanguage value
   */
  public final void setReadLanguage(NLSItem _nlsItem) {
    setReadLanguage(m_vctNLSReads.indexOf(_nlsItem));
  }


  /**
   * @param  _i  The new writeLanguage value
   */
  public final void setWriteLanguage(int _i) {
    NLSItem nlsItem = (NLSItem) m_vctNLSWrites.elementAt(_i);

    m_iNLSWrite = _i;
  }


  /**
   * @param  _nlsItem  The new writeLanguage value
   */
  public final void setWriteLanguage(NLSItem _nlsItem) {
    setWriteLanguage(m_vctNLSWrites.indexOf(_nlsItem));
  }


  /**
   * @return    The readLanguage value
   */
  public final NLSItem getReadLanguage() {
    return (NLSItem) m_vctNLSReads.elementAt(m_iNLSRead);
  }


  /**
   * @return    The writeLanguage value
   */
  public final NLSItem getWriteLanguage() {
    if (m_vctNLSWrites.isEmpty()) {
      return null;
    }
    return (NLSItem) m_vctNLSWrites.elementAt(m_iNLSWrite);
  }

  public final NLSItem getReadLanguage(int _i) {
    return (NLSItem) m_vctNLSReads.elementAt(_i);
  }

///**

// *
// */
//  public final Object clone() {
//    return new Profile();
//  }
//
  /**
   * @param  _o  Description of the Parameter
   * @return     Description of the Return Value
   */
  public final boolean equals(Object _o) {
    return ((_o != null) && (_o instanceof Profile) && (m_strEnterprise.equals((((Profile) _o).m_strEnterprise))) && (m_iOPWGID == (((Profile) _o).m_iOPWGID)) && (m_iSessionID == (((Profile) _o).m_iSessionID)));
  }


  /**
   * @return    The oPID value
   */
  public final int getOPID() {
    return m_iOPID;
  }


  /**
   * @return    The oPWGID value
   */
  public final int getOPWGID() {
    return m_iOPWGID;
  }


  /**
   * @return    The wGID value
   */
  public final int getWGID() {
    return m_iWGID;
  }


  /**
   * @return    The wGName value
   */
  public final String getWGName() {
    return m_strWGName;
  }


  /**
   * @return    The oPName value
   */
  public final String getOPName() {
    return m_strOPName;
  }


  /**
   * @return    The tranID value
   */
  public final int getTranID() {
    return m_iTranID;
  }

  public void setTranID(int _i) {
  m_iTranID = _i;
  }

  public void resetTranID() {
  m_iTranID = DUMMY_INT;
  }

  /**
   * @return    Description of the Return Value
   */
  public final String dump() {
    return dump(false);
  }


  /**
   *  Gets the oPWGName attribute of the Profile object
   *
   * @return    The oPWGName value
   */
  public final String getOPWGName() {
    return m_strOPWGName;
  }

    public final String getEmailAddress() {
    return m_strEmailAddress;
  }

  public final String getLoginTime() {
    return m_strLoginTime;
  }

  public final void setLoginTime(String _strLoginTime) {
    m_strLoginTime = _strLoginTime;
  }

  public final boolean isReadOnly() {
    return hasRoleFunction(ROLE_FUNCTION_READONLY);
  }

  /**
   * @param  _bBrief  Description of the Parameter
   * @return          Description of the Return Value
   */
  public final String dump(boolean _bBrief) {

    StringBuffer strbResult = new StringBuffer();

    if (_bBrief) {
      strbResult.append(m_strOPWGName);
    } else {
      strbResult.append("OPWG Name: " + m_strOPWGName);
      strbResult.append("\nOPWG ID: " + m_iOPWGID);
      strbResult.append("\nEnterprise: " + m_strEnterprise);
      strbResult.append("\nWG Name: " + m_strWGName);
      strbResult.append("\nWG ID: " + m_iWGID);
      strbResult.append("\nOperator Name: " + m_strOPName);
      strbResult.append("\nOperator ID: " + m_iOPID);
      strbResult.append("\nRole Code: " + m_strRoleCode);
      strbResult.append("\nRole Description: " + m_strRoleDescription);
      strbResult.append("\nRole Functions: " + m_vctRoleFunctions);
      strbResult.append("\nReads: " + m_vctNLSReads);
      strbResult.append("\nWrites: " + m_vctNLSWrites);
      strbResult.append("\nTran ID: " + m_iTranID);
      strbResult.append("\nDefaultIndex: " + m_iDefaultIndex);
      strbResult.append("\nPDHDomain: " + m_vctPDHDomain);
      strbResult.append("\nValOn: " + m_strValOn);
      strbResult.append("\nEffOn: " + m_strEffOn);
      strbResult.append("\nDefaultIndex: " + m_iDefaultIndex);
      strbResult.append("\nReading: " + (((m_iNLSRead >= 0) && (m_iNLSRead < m_vctNLSReads.size())) ? "" + (NLSItem) m_vctNLSReads.elementAt(m_iNLSRead) : "none selected"));
      strbResult.append("\nWriting: " + (((m_iNLSWrite >= 0) && (m_iNLSWrite < m_vctNLSWrites.size())) ? "" + (NLSItem) m_vctNLSWrites.elementAt(m_iNLSWrite) : "none selected"));
      strbResult.append("\nobjMisc: " + mt_objMisc);
      strbResult.append("\nsessionID: " + m_iSessionID);
      strbResult.append("\nrecordLimit: " + m_iLimit);				//CR103103686
      strbResult.append("\nlinkLimit: " + m_iLinkLimit);			//RCQ297831
    }

    return strbResult.toString();
  }


  /**
   * @return    Description of the Return Value
   */
  public final String toString() {
    return dump(true);
  }

	public final String dumpPDHDomain(boolean _bBrief) {
		StringBuffer strbResult = new StringBuffer();
		for (int i=0; i < m_vctPDHDomain.size(); i++) {
			PDHDomain domain = (PDHDomain) m_vctPDHDomain.elementAt(i);
			strbResult.append((i==0 ? "" : ", ") + domain.dump(_bBrief));
		}

		return strbResult.toString();
	}
	//RQ0713072645
    public final String getPDHDomainFlagCodes() {
		StringBuffer strbResult = new StringBuffer();
		for (int i=0; i < m_vctPDHDomain.size(); i++) {
			PDHDomain domain = (PDHDomain) m_vctPDHDomain.elementAt(i);
			strbResult.append((i==0 ? "" : ",") + domain.dumpFlagCode());
		}

    	return strbResult.toString();
    }


  /**
   * Return the date/time this class was generated
   *
   * @return    the date/time this class was generated
   */
  public final static String getVersion() {
    return new String("$Id: Profile.java,v 1.109 2014/05/15 15:14:53 wendy Exp $");
  }

/*
 CR103103686
 */
  public int getRecordLimit() {
	  return m_iLimit;
  }

  private void setRecordLimit(String _s) {
	  try {
		  setRecordLimit(Integer.parseInt(_s));
	  } catch (NumberFormatException _nfe) {
	  }
  }

  public void setRecordLimit(int _i) {
	  m_iLimit = _i;
  }
//RCQ297831
  public int getLinkLimit() {
	  return m_iLinkLimit;
  }
  private void setLinkLimit(String _s) {
	  try {
		  m_iLinkLimit = Integer.parseInt(_s);
	  } catch (NumberFormatException _nfe) {
	  }
  }
	/**
	* RQ0713072645 enforce pdhdomain for actions, allow override in mw properties file
	* @return  boolean
	*/
	public final boolean getEnforcePDHDomain() {
		return m_enforceDomain;
	}
}

