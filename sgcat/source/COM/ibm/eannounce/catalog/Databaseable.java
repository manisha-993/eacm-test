package COM.ibm.eannounce.catalog;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;

// Interface to define what methods needed for Database input/output capability

public interface Databaseable {
	/**
	 * get
	 * 
	 * This gets all immediate information regarding the
	 * object at hand from the database 
	 * (its Attributes)
	 * @param _db
	 */
	void get(Catalog _cat);

	/**
	 * This gets specifc reference data as it pertains to
	 * the object Like a Localize Product List for World Wide Product
	 * 
	 * The case is meant to be an internal switch to target specific
	 * types of reference lists inside the object to avoid having to 
	 * go fill out everything at once
	 * 
	 * @param _db
	 * @param _icase
	 */ 
	void getReferences(Catalog _cat, int _icase);

	/**
	 * ok.. we can only update the Catalog Database stuff
	 * and never the PDH.. so any put calls end up 
	 * updating that set of tables controlled by that object
	 * 
	 * @param _cat - catalog 
	 * @param _bcommit - commit the put or not
	 */
	void put(Catalog _cat, boolean _bcommit);
}

/*
 * $Log: Databaseable.java,v $
 * Revision 1.2  2011/05/05 11:21:34  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:15  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:28  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.10  2005/06/07 04:34:50  dave
 * working on commit control
 *
 * Revision 1.9  2005/05/26 07:20:10  dave
 * new SP and introduction of the Catalog Object
 *
 * Revision 1.8  2005/05/26 00:06:06  dave
 * adding put to design by contract
 *
 * Revision 1.7  2005/05/19 03:20:48  dave
 * adding getReference concept and changing DDL abit
 * to remove the not nulls
 *
 * Revision 1.6  2005/05/18 01:06:00  dave
 * trying to write a main test
 *
 * Revision 1.5  2005/05/17 23:34:49  dave
 * simple exposure of the databaseable method get
 *
 * Revision 1.4  2005/05/13 20:39:49  roger
 * Turn on logging in source
 *
 */
