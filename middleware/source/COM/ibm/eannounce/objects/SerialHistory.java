//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SerialHistory.java,v $
// Revision 1.24  2008/02/01 22:10:05  wendy
// Cleanup RSA warnings
//
// Revision 1.23  2003/10/30 00:43:35  dave
// fixing all the profile references
//
// Revision 1.22  2002/08/15 23:26:46  bala
// Add debug statement
//
// Revision 1.21  2002/08/05 23:15:35  joan
// get ValFrom value
//
// Revision 1.20  2002/05/03 18:25:07  gregg
// replaced gbl3007 w/ gbl8307
//
// Revision 1.19  2002/03/13 23:36:51  dave
// more fixes
//
// Revision 1.18  2002/03/13 23:28:37  dave
// import cleanup
//
// Revision 1.17  2002/02/26 21:44:00  dave
// ensuring I am setting the rs = null after a close
//
// Revision 1.16  2002/02/02 20:56:54  dave
// tightening up code
//
// Revision 1.15  2002/01/31 23:25:58  dave
// more foundation fixes
//
// Revision 1.14  2002/01/31 21:32:22  dave
// more mass abstract changes
//
// Revision 1.13  2002/01/29 19:05:27  joan
// fixing deactivateBlob
//
// Revision 1.12  2002/01/21 21:34:42  dave
// more fixes for NLSItem migration
//
// Revision 1.11  2002/01/21 21:23:29  dave
// more NLSItem to Profile simplication steps
//
// Revision 1.10  2002/01/21 20:59:51  dave
// bit the bullet and use profile to imply NLSItem
//
// Revision 1.9  2002/01/07 21:25:10  joan
// add logic for serialhistorygroup and deactivateBlob
//
// Revision 1.8  2001/12/26 22:47:41  joan
// use new putBlob and getBlob methods
//
// Revision 1.7  2001/11/29 22:56:38  joan
// fixes
//
// Revision 1.6  2001/11/22 00:54:42  joan
// change constructor
//
// Revision 1.5  2001/11/20 18:44:32  joan
// add comments
//
// Revision 1.4  2001/11/20 18:25:45  joan
// fixes
//
// Revision 1.3  2001/11/19 21:52:22  joan
// put more work
//
// Revision 1.2  2001/11/15 19:05:42  joan
// add more codes
//
// Revision 1.1  2001/11/06 19:31:52  joan
// initial load
//
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.Blob;
import COM.ibm.opicmpdh.objects.ControlBlock;

public class SerialHistory extends EANMetaEntity {
  static final long serialVersionUID = 20011106L;
  private String m_strAttributeCode = null;
  private byte[] m_baAttributeValue = null;
  private final String m_strEntityType = "OPWG";
  private int m_iEntityID = -1;
  private String m_strBlobExtension = null;
  private boolean m_bActive = false;
  private String m_strTreeKind = null;
  private String m_strValFrom = null;

  /**
  * Main method which performs a simple test of this class
  */
  public static void main(String arg[]) {
  }

  /**
  * Instantiates a new object from an existing blob
  */
  public SerialHistory(EANMetaFoundation _emf, Database _db, Profile _prof, String _strAttributeCode, boolean _bGetValue) throws MiddlewareException, MiddlewareRequestException {
      super(_emf, _prof, _strAttributeCode);
    setAttributeCode(_strAttributeCode);
    Profile prof = getProfile();
      setEntityID(prof.getOPWGID());

      ReturnStatus returnStatus = new ReturnStatus(-1);
    ResultSet rs = null;
    String strEnterprise = getProfile().getEnterprise();
   // String strValOn = getProfile().getValOn();
   // String strEffOn = getProfile().getEffOn();
    int iOPWGID = getProfile().getOPWGID();

    try {

      _db.test(_prof != null,"Profile is null");

      rs = _db.callGBL8307(returnStatus, iOPWGID, strEnterprise, m_strAttributeCode);

      if (rs.next()) {
        m_strBlobExtension = rs.getString(1).trim();
        _db.debug(D.EBUG_SPEW,"SerialHistory:blob Extension is "+m_strBlobExtension);

        m_strValFrom = rs.getString(2).trim();
        _db.debug(D.EBUG_SPEW,"SerialHistory:Blob Valfrom is "+m_strValFrom);
      }

        rs.close();
        rs = null;
      _db.freeStatement();
      _db.isPending();

      if (_bGetValue) {
        Blob b = _db.getBlob(getProfile(), m_strEntityType, m_iEntityID, m_strAttributeCode, 1);
        m_baAttributeValue = b.getBAAttributeValue();
      }

    } catch (Exception x) {
      System.out.println("SerialHistory exception: " + x );
    } finally {
      _db.freeStatement();
      _db.isPending();
    }
  }

  /**
  * Instantiates a new object that doesn't exist in database
  */
  public SerialHistory(EANMetaFoundation _emf, Profile _prof, String _strAttributeCode, String _strBlobExtension)  throws MiddlewareRequestException {
    super(_emf, _prof, _strAttributeCode);
    Profile prof = getProfile();
    setAttributeCode(_strAttributeCode);
    setBlobExtension(_strBlobExtension);
    setEntityID(prof.getOPWGID());
  }

  /**
  * returns the blob value in byte array
  */
  public byte[] getByteArray() {
    return m_baAttributeValue;
  }

  /**
  * gets the blob value from database and returns it in byte array
  */
  public byte[] getByteArray(RemoteDatabaseInterface _rdi) {
    Blob b = null;
    byte[] byteValue = null;

    try {
      b = _rdi.getBlob(getProfile(), m_strEntityType, m_iEntityID, m_strAttributeCode, 1);
      byteValue = b.getBAAttributeValue();
    } catch (Exception x) {
      System.out.println("SerialHistory getByteArray exception: " + x );
    }
    return byteValue;
  }

    /**
  * sets the attribute value of the blob
  */
  public void setByteArray(byte[] _baAttributeValue) {
    m_baAttributeValue = _baAttributeValue;
  }

  /**
  * saves to database
  */
  public void updatePDH(RemoteDatabaseInterface _rdi) {
    //create ControlBlock
    String current = null;
    String forever = null;
    try {
      ReturnDataResultSetGroup oRDRSG = _rdi.remoteGBL2028();
      current = oRDRSG.getColumn(0,0,0);
      forever = oRDRSG.getColumn(0,0,1);
    } catch(Exception x) {
      System.out.println("SerialHistory call remoteGBL2028 exception: " + x );
    }

    ControlBlock cb = new ControlBlock(current, forever, current, forever, getProfile().getOPWGID());
    if (isActive()) {
      setAttributeCode(getTreeKind() + "CURRENT");
//      System.out.println("-->  new AttributeCode is: " + getAttributeCode());
    }

    //create Blob
    Blob b = new Blob(getProfile().getEnterprise(), m_strEntityType, m_iEntityID, m_strAttributeCode, m_baAttributeValue, m_strBlobExtension, 1, cb);
    try {
      _rdi.putBlob(getProfile(), b,1);
    } catch (Exception x) {
      System.out.println("SerialHistory updatePDH exception: " + x );
    }
  }

  /**
  * removes the serial history from database
  */
  public void deletePDH(RemoteDatabaseInterface _rdi) {
    try {
      _rdi.deactivateBlob(getProfile(), m_strEntityType, m_iEntityID, m_strAttributeCode, 1);
    } catch (Exception x) {
      System.out.println("SerialHistory deletePDH exception: " + x );
    }
    return;
  }

  public boolean isActive() {
    return m_bActive;
  }

  public void setActive(boolean _bActive) {
    m_bActive = _bActive;
    return;
  }

  public void clearByteArray() {
    m_baAttributeValue = null;
    return;
  }

  public final String dump(boolean _brief) {
    StringBuffer strbResult = new StringBuffer();
    strbResult.append("Entity Type: " + m_strEntityType);
    strbResult.append("\nEntity ID: " + m_iEntityID);
    strbResult.append("\nAttribute Code: " + m_strAttributeCode);
    strbResult.append("\nBlob Extension: " + m_strBlobExtension);
    return new String(strbResult);
  }

  public String toString() {
    return m_strBlobExtension;
  }

  public String getAttributeCode() {
    return m_strAttributeCode;
  }

  public void setAttributeCode(String _strAttributeCode) {
    m_strAttributeCode = _strAttributeCode;
  }

  public String getBlobExtension() {
    return m_strBlobExtension;
  }

  public void setBlobExtension(String _strBlobExtension) {
    m_strBlobExtension = _strBlobExtension;
  }

  public String getValFrom() {
    return m_strValFrom;
  }

  protected void setValFrom(String _strValFrom) {
    m_strValFrom = _strValFrom;
  }

  public int getEntityID() {
    return m_iEntityID;
  }

  public void setEntityID(int _iEntityID) {
    m_iEntityID = _iEntityID;
  }

  public String getTreeKind() {
    return m_strTreeKind;
  }

  public void setTreeKind(String _strTreeKind) {
    m_strTreeKind = _strTreeKind;
  }


    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
      return new String("$Id: SerialHistory.java,v 1.24 2008/02/01 22:10:05 wendy Exp $");
    }
}
