//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SerialHistoryGroup.java,v $
// Revision 1.20  2008/02/01 22:10:08  wendy
// Cleanup RSA warnings
//
// Revision 1.19  2003/10/30 00:43:35  dave
// fixing all the profile references
//
// Revision 1.18  2002/05/03 18:25:40  gregg
// replaced gbl3006 w/ gbl8306
//
// Revision 1.17  2002/02/26 21:44:00  dave
// ensuring I am setting the rs = null after a close
//
// Revision 1.16  2002/02/13 18:33:36  joan
// remove System.out.
//
// Revision 1.15  2002/02/13 18:18:16  joan
// debug error
//
// Revision 1.14  2002/02/02 21:39:20  dave
// more  fixes to tighen up import
//
// Revision 1.13  2002/02/02 21:22:55  dave
// more clean up
//
// Revision 1.12  2002/02/02 20:56:54  dave
// tightening up code
//
// Revision 1.11  2002/01/31 23:50:34  dave
// more foundation fixes
//
// Revision 1.10  2002/01/31 22:28:20  dave
// more Foundation changes
//
// Revision 1.9  2002/01/21 21:23:29  dave
// more NLSItem to Profile simplication steps
//
// Revision 1.8  2002/01/21 20:59:51  dave
// bit the bullet and use profile to imply NLSItem
//
// Revision 1.7  2002/01/07 21:25:10  joan
// add logic for serialhistorygroup and deactivateBlob
//
// Revision 1.6  2001/11/29 22:56:38  joan
// fixes
//
// Revision 1.5  2001/11/22 01:01:40  joan
// fix to use new sh constructor
//
// Revision 1.4  2001/11/20 18:44:32  joan
// add comments
//
// Revision 1.3  2001/11/20 18:25:45  joan
// fixes
//
// Revision 1.2  2001/11/19 21:57:08  joan
// add copyright title
//

package COM.ibm.eannounce.objects;

// DWB need to destar
import java.sql.ResultSet;
import COM.ibm.opicmpdh.middleware.*;

public class SerialHistoryGroup extends EANMetaEntity {
	static final long serialVersionUID = 20011119L;
	//private final String m_strEntityType = "OPWG";
	private final String m_strCurrent = "CURRENT";
	private int m_iEntityID = -1;
	private int m_iCurrentIndex = -1;
	private String m_strTreeKind = null;
	private SerialHistory m_active = null;

	/**
	* Main method which performs a simple test of this class
	*/
	public static void main(String arg[]) {
	}

	/**
	* instantiates a group of serial histories
	*/
	public SerialHistoryGroup(Database _db, Profile _prof, String _s) throws MiddlewareException, MiddlewareRequestException {

		super(null, _prof, _s);
	
		setEntityID(_prof.getOPWGID());
		m_strTreeKind = _s;
		ReturnStatus returnStatus = new ReturnStatus(-1);
		ResultSet rs = null;
		ReturnDataResultSet rdrs = null;
		String strAttributeCode = null;
		SerialHistory sh = null;
		try {
			_db.test(_prof != null,"Profile is null");
			rs = _db.callGBL8306(returnStatus, _prof.getOPWGID(), _prof.getEnterprise().trim(),_prof.getReadLanguage().getNLSID(), _s);
			rdrs = new ReturnDataResultSet(rs);
			rs.close();
			rs = null;
			_db.freeStatement();
			_db.isPending();

			for (int i = 0 ; i < rdrs.size(); i++) {
				strAttributeCode = rdrs.getColumn(i,0).trim();
				if (strAttributeCode.endsWith(m_strCurrent)) {
					sh = new SerialHistory(this, _db, _prof, strAttributeCode, true);
					m_iCurrentIndex = i;
					setActiveSerialHistory(sh);
					sh.setTreeKind(_s);
				} else {
					sh = new SerialHistory(this, _db, _prof, strAttributeCode, false);
					sh.setTreeKind(_s);
				}
				super.putData(sh);
			}
		} catch (Exception x) {
			System.out.println("SerialHistory exception: " + x );
		} finally {
			_db.freeStatement();
			_db.isPending();
		}
	}

	private SerialHistory setActiveSerialHistory(SerialHistory _sHist) {
		if(_sHist == m_active) return _sHist;
		m_active = _sHist;
		m_active.setActive(true);
		m_active.setByteArray(_sHist.getByteArray());
		return m_active;
	}

	/**
	* returns current serial history
	*/
	public SerialHistory getCurrentSerialHistory() {
		if (m_iCurrentIndex < 0 || m_iCurrentIndex > getSerialHistoryCount())
			return null;
		return (SerialHistory)super.getData(m_iCurrentIndex);
	}

 	public SerialHistory getActiveSerialHistory() {
		return m_active;
	}

	/**
	* sets the active serial history, gets the blob value, and returns it
	*/
	public SerialHistory setActiveSerialHistory(SerialHistory _sHist, RemoteDatabaseInterface _rdi) {
		if (_sHist == m_active) return _sHist;
		if (m_active != null) {
			m_active.setActive(false);
			m_active.clearByteArray();
		}
		m_active = _sHist;
		m_active.setActive(true);
		m_active.setByteArray(m_active.getByteArray(_rdi));
		return m_active;
	}

	/**
	* saves the current active serial history in database
	*/
	public void writeActiveSerialHistory(RemoteDatabaseInterface _rdi, boolean _bClear) {
		if (m_active == null) return;
		updatePDH(m_active, _rdi, _bClear);
		if (_bClear) {
			m_active.setActive(false);
			m_active.clearByteArray();
		}
//		setCurrentHistory(m_active);
		return;
	}

	/**
	* updates a serial history in database
	*/
	public void updatePDH(SerialHistory _sHist, RemoteDatabaseInterface _rdi, boolean _bClear) {
		if (_sHist != null) {
			_sHist.updatePDH(_rdi);
			if (_bClear)
				_sHist.clearByteArray();
		}
		return;
	}

	public SerialHistory revert() {
		return setActiveSerialHistory(getCurrentSerialHistory());
	}

	/**
	* removes a serial history from database
	*/
	public void deletePDH(SerialHistory _sHist, RemoteDatabaseInterface _rdi) {
		_sHist.clearByteArray();
		_sHist.deletePDH(_rdi);
		return;
	}

	/**
	* removes a serial history from the group
	*/
	public void removeSerialHistory(SerialHistory _sHist) {
		super.removeData(_sHist);
	}

	/**
	* returns the serial history at a given index
	*/
	public SerialHistory getSerialHistory(int _index) {
		return (SerialHistory)super.getData(_index);
	}

	/**
	* returns the serial history has the matching blob extension
	*/
	public SerialHistory getSerialHistory(String _s) {
		for ( int i = 0; i < getSerialHistoryCount(); i++ ) {
			SerialHistory sh = getSerialHistory(i);
			String blobExt = sh.getBlobExtension();
			if (blobExt.equals(_s)) {
				return sh;
			}
		}
		return null;
	}

	/**
	* puts a serial history in the group
	*/
	public void putSerialHistory(SerialHistory _sHist) {
		super.putData(_sHist);
	}

	/**
	* creates a serial history, and returns it
	*/
	public SerialHistory createSerialHistory(String _strAttributeCode, String _strBlobExtension) throws MiddlewareRequestException {
		SerialHistory sh = new SerialHistory(this, getProfile(), _strAttributeCode, _strBlobExtension);
		sh.setTreeKind(m_strTreeKind);
		return sh;
	}

	/**
	* returns number of serial history in the group
	*/
	public int getSerialHistoryCount() {
		return super.getDataCount();
	}

	/**
	* returns the serial history group as an array
	*/
	public SerialHistory[] toArray() {
		SerialHistory[] shArray = new SerialHistory[super.getDataCount()];
		for (int i = 0; i < super.getDataCount(); i++) {
			shArray[i] = (SerialHistory)super.getData(i);
		}
		return shArray;
	}

	public int getEntityID() {
		return m_iEntityID;
	}

	public void setEntityID(int _entityID) {
		m_iEntityID = _entityID;
	}

	/**
	* Return the date/time this class was generated
	* @return the date/time this class was generated
	*/
	public String getVersion() {
	  	return new String("$Id: SerialHistoryGroup.java,v 1.20 2008/02/01 22:10:08 wendy Exp $");
  	}

	public String dump(boolean _brief) {
		return "TBD";
	}
}
