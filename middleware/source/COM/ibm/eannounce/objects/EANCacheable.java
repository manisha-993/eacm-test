//
//$Log: EANCacheable.java,v $
//Revision 1.4  2005/02/14 17:18:34  dave
//more jtest fixing
//
//Revision 1.3  2004/10/21 16:49:53  dave
//trying to share compartor
//
//Revision 1.2  2002/05/02 22:18:59  gregg
//cache key stuff for Extract
//
//Revision 1.1  2002/04/09 20:19:32  gregg
//chacnged interface name to EANCacheable (was Cacheable)
//
//Revision 1.1  2002/04/09 18:45:41  gregg
//initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;

/**
 * EANCacheable
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface EANCacheable {

    /**
     * FIELD
     */
    String CLASS_BRAND = "$Id: EANCacheable.java,v 1.4 2005/02/14 17:18:34 dave Exp $";

    //provide a way to expire the cache externally
    /**
     * expireCache
     *
     * @param _db
     *  @author David Bigelow
     */
    void expireCache(Database _db);

    /**
     * getCacheKey
     *
     * @return
     *  @author David Bigelow
     */
    String getCacheKey();

}
