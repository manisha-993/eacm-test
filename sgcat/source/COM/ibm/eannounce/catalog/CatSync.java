/*
 * Created on May 26, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

import java.util.Vector;

/**
 * Simple interface that certain objects implement
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface CatSync {


    boolean hasSyncMapCollection();
    SyncMapCollection getSmc();
    void setSmc(SyncMapCollection _smc);
    void processSyncMap (Catalog _cat);

}

/*
* $Log: CatSync.java,v $
* Revision 1.2  2011/05/05 11:21:34  wendy
* src from IBMCHINA
*
* Revision 1.1.1.1  2007/06/05 02:09:10  jingb
* no message
*
* Revision 1.1.1.1  2006/03/30 17:36:28  gregg
* Moving catalog module from middleware to
* its own module.
*
* Revision 1.5  2005/06/22 21:17:15  gregg
* change signature for processSyncMap
*
* Revision 1.4  2005/06/05 16:54:52  dave
* rolling in the feature concept
*
* Revision 1.3  2005/06/01 03:32:14  dave
* JTest clean up
*
* Revision 1.2  2005/05/27 21:46:59  dave
* trying to split it up
*
* Revision 1.1  2005/05/27 02:03:09  dave
* ok.. first attempt at focusing on isolating GBL8104 from
* the world
*
*/
