package COM.ibm.eannounce.catalog;

import COM.ibm.opicmpdh.middleware.D;

public class ProdStructCollections {

	 /**
	   * parallelSyncToCatDb
	   * 
	   * Added by  Guo Bin, 2007-10-09 For Call the method parallelSyncToCatDb() 
	   * of ProductStructCollection and WorldWideProductCollection orderly
	   */
	  public static void parallelSyncToCatDb() {
		// TODO Auto-generated method stub
		D.ebug(D.EBUG_INFO,"ProdStructCollections: begin run ProductStructCollection.");
		ProductStructCollection.parallelSyncToCatDb();
		D.ebug(D.EBUG_INFO,"ProdStructCollections: begin run WorldWideProductStructCollection.");
		WorldWideProductStructCollection.parallelSyncToCatDb();
	}
}
