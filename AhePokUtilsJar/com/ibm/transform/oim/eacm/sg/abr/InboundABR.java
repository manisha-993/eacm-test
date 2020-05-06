package com.ibm.transform.oim.eacm.sg.abr;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.transform.oim.eacm.util.Log;
import com.ibm.transform.oim.eacm.util.Logger;
import com.ibm.transform.oim.eacm.xalan.Data;
import com.ibm.transform.oim.eacm.xalan.DataView;
import com.ibm.transform.oim.eacm.xalan.PDHAccess;

public class InboundABR implements Log, Data, PDHAccess {
	private final Logger log = new Logger();
	private DataView dataView = null;
	private Database db = null;
	private Profile prof = null;

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.util.Log#getIdentifier()
	 */
	public String getIdentifier() {
		return log.getIdentifier();
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.util.Log#setIdentifier(java.lang.String)
	 */
	public boolean setIdentifier(String anIdentifier) {
		return log.setIdentifier(anIdentifier);
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xalan.Data#getDataView()
	 */
	public DataView getDataView() {
		return dataView;
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xalan.Data#setDataView(com.ibm.transform.oim.eacm.xalan.DataView)
	 */
	public boolean setDataView(DataView dv) {
		boolean result = dv != null;
		if (result) {
			dataView = dv;
			result &= initialize();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xalan.PDHAccess#dereference()
	 */
	public boolean dereference() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xalan.PDHAccess#setDatabase(COM.ibm.opicmpdh.middleware.Database)
	 */
	public boolean setDatabase(Database database) {
		db = database;
		return db != null;
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xalan.PDHAccess#setProfile(COM.ibm.opicmpdh.middleware.Profile)
	 */
	public boolean setProfile(Profile profile) {
		prof = profile;
		return prof != null;
	}
	
	/**
	 * The ABR will check the EPWAITINGQUEUE to see if there are any "offerings" waiting on this item. 
	 * If there is an "offering" waiting, it will:
	 * 1) update its ePIMS status if applicable
	 * 2) check to see if the "offering" that was waiting has anything else that it is waiting for and if not, then 
	 * 3) notify ePIMS of this "offering" if applicable
	 * 4) see if any "offering" is waiting for this "offering" that was just sent to ePIMS
	 * 
	 * For example, a LSEO could be waiting for a WWSEO that is waiting for a MODEL to be "Promoted".
	 * Set A = Who is waiting for this.
	 * Set B = Who is waiting for A;
	 * @return
	 */
	private boolean initialize() {
		boolean result = false;
		return result;
	}

}
