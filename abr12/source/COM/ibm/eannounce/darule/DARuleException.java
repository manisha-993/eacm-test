//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.darule;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/*********************************
 * Exception used when an error occurs deriving catalog attribute data
 * 
 */ 
// $Log: DARuleException.java,v $
// Revision 1.1  2011/03/15 21:12:10  wendy
// Init for BH FS ABR Catalog Attr Derivation 20110221b.doc
//
public class DARuleException extends MiddlewareRequestException {
    private static final long serialVersionUID = 1L;

    /**
     * @param s
     */
    public DARuleException(String s) {
        super(s);
    }
}
