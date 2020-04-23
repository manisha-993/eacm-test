//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.darule;

import java.util.Vector;

/*********************************
* Exception used when an DARULEs have invalid DALIFECYCLEs
* NOTE: only used for IDL 
*/  
//$Log: InvalidDARuleException.java,v $
//Revision 1.1  2011/03/15 21:12:10  wendy
//Init for BH FS ABR Catalog Attr Derivation 20110221b.doc
//
public class InvalidDARuleException extends DARuleException {
	private static final long serialVersionUID = 1L;
	private Vector entityItemVct = null;

	/**
	 * @param s
	 * @param errVct - DARULE entityitems that are invalid
	 */
	public InvalidDARuleException(String s, Vector errVct) {
		super(s);
		entityItemVct = errVct;
	}
	/**
	 * get all invalid entityitems
	 * @return
	 */
	public Vector getEntityItems(){
		return entityItemVct;
	}
	/**
	 * release memory
	 */
	public void dereference(){
		entityItemVct.clear();
		entityItemVct = null;
	}
}
