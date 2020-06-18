//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: DatePackage.java,v $
// Revision 1.15  2008/01/31 22:55:00  wendy
// Cleanup RSA warnings
//
// Revision 1.14  2006/06/24 02:56:00  dave
// ok ..lets try a new way to get Now
//
// Revision 1.13  2003/04/17 17:56:15  dave
// clean up link,deactivate, tagging
//
// Revision 1.12  2003/01/15 17:35:11  dave
// fixing end of day date
//
// Revision 1.11  2003/01/14 17:30:50  dave
// Added EndOfDay to DatePackage and used it somewhere
//
// Revision 1.10  2002/09/23 17:22:17  roger
// ABRStatus and DatePackage changes
//
// Revision 1.9  2002/09/23 16:05:29  roger
// Clean up and simplify
//
// Revision 1.8  2002/09/20 21:59:52  roger
// Need serial#
//
// Revision 1.7  2002/09/20 21:44:17  roger
// Change method name
//
// Revision 1.6  2002/09/20 21:42:21  roger
// Keep values returned from DB
//
// Revision 1.5  2002/09/20 21:39:59  roger
// Make this whole process easier
//
// Revision 1.4  2001/08/23 20:29:06  roger
// Clean up
//
// Revision 1.3  2001/08/23 20:28:25  roger
// Added log
//
package COM.ibm.opicmpdh.middleware;

import java.io.Serializable;

/**
 * A class useful for returning the basic dates
 * @version @date
 */
public final class DatePackage implements Serializable {
  /**
   * @serial
   */
  static final long serialVersionUID = 1L;
  // Instance variables
  public String m_strNow = null;
  public String m_strForever = null;
  public String m_strEpoch = null;
  public String m_strEndOfDay = null;

/**
 * Main method which performs a simple test of this class
 */
  public static void main(String arg[]) {
  }
/**
 * Constructor
 */
  protected DatePackage() {
  }
/**
 * Constructor
 */
  public DatePackage(Database _db) {
    try {
       _db.getNow();
    } catch (Exception x) {
      D.ebug("trouble doing getDatePackage");
    }

    m_strNow = Database.c_strNow;
    m_strEndOfDay = m_strNow.substring(0,10) + "-23.59.59.999999";
    m_strForever = Database.c_strForever;
    m_strEpoch = Database.c_strEpoch;
  }
/**
 * Constructor
 */
  protected DatePackage(String _strNow, String _strForever, String _strEpoch) {
    m_strNow = _strNow;
    m_strEndOfDay = m_strNow.substring(0,10) + "-23.59.59.999999";
    m_strForever = _strForever;
    m_strEpoch = _strEpoch;
  }

/**
 * @return the date/time this class was generated
 */
  public final String getVersion() {
    return new String("$Id: DatePackage.java,v 1.15 2008/01/31 22:55:00 wendy Exp $");
  }
/**
 * Return the current time from the package
 */
  public final String getNow() {
    return m_strNow;
  }

  public final String getEndOfDay() {
    return m_strEndOfDay;
  }


/**
 * Return the current time from the package
 */
  public final String getNow(Database _db) {
    DatePackage dp = new DatePackage(_db);
    return dp.m_strNow;
//
//    try {
//      _db.getNow();
//
//      m_strNow = _db.c_strNow;
//      m_strForever = _db.c_strForever;
//      m_strEpoch = _db.c_strEpoch;
//    } catch (Exception x) {
//      D.ebug("trouble doing getNow");
//    }
//
//    return m_strNow;
  }
/**
 * Return the forever time from the package
 */
  public final String getForever() {
    return m_strForever;
  }
/**
 * Return the forever time from the package
 */
  public final String getForever(Database _db) {
    DatePackage dp = new DatePackage(_db);
    return dp.m_strForever;
//
//    try {
//      _db.getNow();
//
//      m_strNow = _db.c_strNow;
//      m_strForever = _db.c_strForever;
//      m_strEpoch = _db.c_strEpoch;
//    } catch (Exception x) {
//      D.ebug("trouble doing getForever");
//    }
//
//    return m_strForever;
  }
/**
 * Return the epoch time from the package
 */
  public final String getEpoch() {
    return m_strEpoch;
  }
/**
 * Return the epoch time from the package
 */
  public final String getEpoch(Database _db) {
    DatePackage dp = new DatePackage(_db);
    return dp.m_strEpoch;
//
//    try {
//      _db.getNow();
//
//      m_strNow = _db.c_strNow;
//      m_strForever = _db.c_strForever;
//      m_strEpoch = _db.c_strEpoch;
//    } catch (Exception x) {
//      D.ebug("trouble doing getEpoch");
//    }
//
//    return m_strEpoch;
  }
/**
 *
 */
  public final String dump() {
    return dump(false);
  }
/**
 *
 */
  public final String dump(boolean _bBrief) {

    StringBuffer strbResult = new StringBuffer();

    if (_bBrief) {
      strbResult.append("Now: " + m_strNow);
      strbResult.append(" Forever: " + m_strForever);
      strbResult.append(" Epoch: " + m_strEpoch);
    } else {
      strbResult.append("Now: " + m_strNow);
      strbResult.append("\nForever: " + m_strForever);
      strbResult.append("\nEpoch: " + m_strEpoch);
    }

    return new String(strbResult);
  }
/**
 *
 */
  public final String toString() {
    return dump(true);
  }
}
