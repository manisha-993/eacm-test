/*
 * Created on May 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * $Log: ProductStructCollection.java,v $
 * Revision 1.2  2011/05/05 11:21:34  wendy
 * src from IBMCHINA
 *
 * Revision 1.20  2008/11/18 21:17:06  rick
 * LACTO
 *
 * Revision 1.19  2008/04/07 05:59:54  yang
 * *** empty log message ***
 *
 * Revision 1.18  2007/11/15 03:16:53  liubdl
 * *** empty log message ***
 *
 * Revision 1.17  2007/11/15 02:59:10  liubdl
 * *** empty log message ***
 *
 * Revision 1.16  2007/11/14 07:14:26  liubdl
 * *** empty log message ***
 *
 * Revision 1.15  2007/11/14 06:54:28  liubdl
 * *** empty log message ***
 *
 * Revision 1.14  2007/11/14 06:52:50  liubdl
 * *** empty log message ***
 *
 * Revision 1.13  2007/10/29 09:32:20  jingb
 * Change code in method syncToCatDb():
 * delete the temp table data by sessionID
 *
 * Revision 1.12  2007/10/19 07:51:40  jiehou
 * *** empty log message ***
 *
 * Revision 1.11  2007/10/19 03:44:00  jiehou
 * *** empty log message ***
 *
 * Revision 1.10  2007/10/19 03:33:40  jiehou
 * *** empty log message ***
 *
 * Revision 1.9  2007/10/19 02:38:15  jingb
 * *** empty log message ***
 *
 * Revision 1.8  2007/10/19 01:51:04  jiehou
 * *** empty log message ***
 *
 * Revision 1.7  2007/10/18 03:28:46  jiehou
 * *** empty log message ***
 *
 * Revision 1.6  2007/10/17 09:27:26  jingb
 * *** empty log message ***
 *
 * Revision 1.5  2007/10/16 09:52:05  jiehou
 * *** empty log message ***
 *
 * Revision 1.4  2007/10/16 08:53:44  jiehou
 * *** empty log message ***
 *
 * Revision 1.3  2007/10/16 05:37:22  jingb
 * *** empty log message ***
 *
 * Revision 1.2  2007/10/09 06:45:46  jingb
 * *** empty log message ***
 *
 * Revision 1.1  2007/09/19 05:49:29  jingb
 * added by guobin 20070919
 *
 * Revision 1.6  2007/09/19 08:48:31  GuoBin
 * Copy ProductCollection to ProductStructCollection
 * Change VE name,split ProductStructCollection from ProductCollection
 *
 * Revision 1.5  2007/09/13 08:48:31  jiehou
 * add configurable NLSID for parallelSyncToCatDb()
 *
 * Revision 1.4  2007/09/13 05:54:52  sulin
 * no message
 *
 * Revision 1.3  2007/09/12 03:02:44  jiehou
 * *** empty log message ***
 *
 * Revision 1.2  2007/09/10 06:45:43  sulin
 * no message
 *
 * Revision 1.1.1.1  2007/06/05 02:09:28  jingb
 * no message
 *
 * Revision 1.22  2007/03/05 13:57:04  rick
 * 0308 deploy - reset cache attr list in synch
 * case so feature does not pick it up.
 *
 * Revision 1.21  2006/09/01 21:45:56  gregg
 * gamap element... fix?
 *
 * Revision 1.20  2006/09/01 20:53:51  gregg
 * debugs
 *
 * Revision 1.19  2006/08/24 20:21:37  gregg
 * no smcOuter.clearAll at end of nls loop!
 *
 * Revision 1.18  2006/08/10 21:33:57  gregg
 * Use new SyncMapCollection constructor for chunky munky
 *
 * Revision 1.17  2006/08/10 17:06:10  gregg
 * delta chunky munky
 *
 * Revision 1.16  2006/08/02 23:03:37  gregg
 * some monkying with prod.matchesCATLGPUB(_cat)
 *
 * Revision 1.15  2006/08/02 22:45:46  gregg
 * closing up a hole for the case where ALL products are removed via catlgpub pruning.
 *
 * Revision 1.14  2006/07/12 20:44:49  gregg
 * 1) setActive on Products based on thecriteria of having AT LEAST one active smi.
 * 2) ignoring smi's which are not active in our pruning logic.
 *
 * Revision 1.13  2006/07/11 18:25:14  gregg
 * pulling back debugs to spew
 *
 * Revision 1.12  2006/05/23 22:11:50  gregg
 * PRODSTRUCT/SWPRODSTRUCT pruning for MODEL (LCTO)'s
 *
 * Revision 1.11  2006/05/22 23:17:28  gregg
 * remove some debugging code
 *
 * Revision 1.10  2006/05/17 20:34:13  gregg
 * fix
 *
 * Revision 1.9  2006/05/17 20:32:20  gregg
 * more debugging
 *
 * Revision 1.8  2006/05/16 21:53:20  gregg
 * beginning changes for 050506 spec update
 *
 * Revision 1.7  2006/05/16 17:29:00  gregg
 * CATLGPUB filtering for products
 *
 * Revision 1.6  2006/05/04 15:46:55  gregg
 * some debugs for prune SMC
 *
 * Revision 1.5  2006/05/02 17:16:18  gregg
 * !NLS ReadLanguage setting on profile for sync/idl
 *
 * Revision 1.4  2006/04/26 17:22:00  gregg
 * addingle LSEOBUNDLEVE into syncToCatDb
 *
 * Revision 1.3  2006/04/26 17:02:56  gregg
 * enable pruneSmc by country for LSEO's
 *
 * Revision 1.2  2006/04/04 21:50:17  gregg
 * deactivate structure first on product syncs
 *
 * Revision 1.1.1.1  2006/03/30 17:36:31  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.86  2006/03/25 21:49:37  gregg
 * yeehaw! Jackpot typo fix.
 *
 * Revision 1.85  2006/03/25 21:35:20  gregg
 * debug
 *
 * Revision 1.84  2006/03/25 19:58:47  gregg
 * debug for pruning
 *
 * Revision 1.83  2006/03/25 01:19:50  gregg
 * temporarily take out prune code
 *
 * Revision 1.82  2006/03/22 22:28:12  gregg
 * debug + prune for LCTO not MODEL
 *
 * Revision 1.81  2006/01/20 18:51:51  gregg
 * buildLanguageMapper in here..
 *
 * Revision 1.80  2005/12/02 22:39:27  bala
 * undo paste error
 *
 * Revision 1.79  2005/12/02 01:10:33  bala
 * <No Comment Entered>
 *
 * Revision 1.78  2005/11/09 18:40:06  gregg
 * going for LSEOBUNDLES
 *
 * Revision 1.77  2005/10/25 07:05:49  dave
 * memory cleanup
 *
 * Revision 1.76  2005/10/25 04:09:24  dave
 * curtailing smc's to spew
 *
 * Revision 1.75  2005/10/25 02:52:21  dave
 * commenting out some debugs
 *
 * Revision 1.74  2005/10/25 00:41:58  dave
 * no need to dump xml
 *
 * Revision 1.73  2005/10/24 19:13:05  dave
 * new chunking for product
 *
 * Revision 1.72  2005/09/30 21:31:11  gregg
 * Hows about we actually enable both VEs. Thanks.
 *
 * Revision 1.71  2005/09/21 20:55:05  gregg
 * fixx
 *
 * Revision 1.70  2005/09/21 20:27:27  gregg
 * compile fixx
 *
 * Revision 1.69  2005/09/16 18:25:40  gregg
 * temporarily removing LSEO VE
 *
 * Revision 1.68  2005/09/15 23:00:08  gregg
 * some changes
 *
 * Revision 1.67  2005/09/14 16:34:24  gregg
 * logging smc change
 *
 * Revision 1.66  2005/09/13 03:20:03  dave
 * enhanced looping for other areas
 *
 * Revision 1.65  2005/09/12 22:55:45  gregg
 * chunking logic
 *
 * Revision 1.64  2005/09/12 00:12:24  dave
 * new sql file to quickly ensure everything is accurate
 * in our catdb setup for data
 *
 * Revision 1.63  2005/06/29 18:01:44  gregg
 * some main() work
 *
 * Revision 1.62  2005/06/29 17:57:22  gregg
 * syncToCatDB
 *
 * Revision 1.61  2005/06/24 17:08:18  gregg
 * setting valon on profile
 *
 * Revision 1.60  2005/06/22 22:46:38  gregg
 * starting on pruneSmc
 *
 * Revision 1.59  2005/06/22 21:21:20  gregg
 * lets look at SyncMap Collection (debug)
 *
 * Revision 1.58  2005/06/22 21:17:16  gregg
 * change signature for processSyncMap
 *
 * Revision 1.57  2005/06/22 20:08:56  gregg
 * adding in MODEL VE logic
 *
 * Revision 1.56  2005/06/15 17:29:52  gregg
 * let us put Product in IDLSyncToDB
 *
 * Revision 1.55  2005/06/14 21:40:52  gregg
 * fix
 *
 * Revision 1.54  2005/06/14 21:36:06  gregg
 * null ptr fix
 *
 * Revision 1.53  2005/06/14 20:37:54  gregg
 * filling out data from PDH and such
 *
 * Revision 1.52  2005/06/13 04:35:34  dave
 * ! needs to be not !
 *
 * Revision 1.51  2005/06/13 04:02:06  dave
 * new dryrun feature to keep things from being updated
 *
 * Revision 1.50  2005/06/09 19:52:12  gregg
 * null ptr fix for product-based PDH pull
 *
 * Revision 1.49  2005/06/09 18:25:22  gregg
 * some more pdh pull logic
 *
 * Revision 1.48  2005/06/08 23:31:18  gregg
 * more SyncMap/pull from PDH stuff
 *
 * Revision 1.47  2005/06/08 23:01:33  gregg
 * more syncmap
 *
 * Revision 1.46  2005/06/08 22:38:45  gregg
 * basic prepping for SynMap stuff
 *
 * Revision 1.45  2005/06/07 04:34:51  dave
 * working on commit control
 *
 * Revision 1.44  2005/06/02 20:34:09  gregg
 * fixing the parent/child collection reference mayhem
 *
 * Revision 1.43  2005/06/02 18:25:53  gregg
 * some debugs
 *
 * Revision 1.42  2005/06/02 18:07:43  gregg
 * compile
 *
 * Revision 1.41  2005/06/02 18:01:24  gregg
 * s'more XML + dumps
 *
 * Revision 1.40  2005/05/27 22:52:33  dave
 * lets see if we can get something from the PDH
 *
 * Revision 1.39  2005/05/27 00:55:17  dave
 * adding the merge method.
 *
 * Revision 1.38  2005/05/26 20:31:06  gregg
 * cleaning up source, type, case CONSTANTS.
 * let's also make sure we check for source in building objects within our collections.
 *
 * Revision 1.37  2005/05/26 07:32:30  dave
 * some minor syntax
 *
 * Revision 1.36  2005/05/26 00:06:06  dave
 * adding put to design by contract
 *
 * Revision 1.35  2005/05/26 00:01:52  gregg
 * include ProductCollection.ALL check
 *
 * Revision 1.34  2005/05/25 23:43:57  gregg
 * updates
 *
 * Revision 1.33  2005/05/25 22:49:22  gregg
 * some dumping
 *
 * Revision 1.32  2005/05/25 20:57:39  gregg
 * getFatAndDeep methods to traverse the tree.
 *
 * Revision 1.31  2005/05/25 16:52:41  gregg
 * more storing id references, building prodstruct, etc
 *
 * Revision 1.30  2005/05/24 23:06:33  gregg
 * add to dump
 *
 * Revision 1.29  2005/05/24 22:53:53  gregg
 * variable name changes
 *
 * Revision 1.28  2005/05/24 22:03:48  gregg
 * some fixes
 *
 * Revision 1.27  2005/05/24 21:44:43  gregg
 * re-write
 *
 *
 */
package COM.ibm.eannounce.catalog;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.transactions.NLSItem;

/**
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ProductStructCollection
    extends CatList implements XMLable, CatSync {

  /**
   * FIELD - LSEO VE NAME
   */
  public static final String LSEO_VE1 = "LSEOPS1";

  public static final String MODEL_VE1 = "LCTOPS1";

  public static final String LSEOBUNDLE_VE1 = "LSEOBPS1";
  //Added by Guo Bin, 2007-8-03
  private static String SP_SUFFIX = "H";

  /**
   * Holds the answer  to GBL8104
   */
  private SyncMapCollection m_smc = null;
  /**
   * ProductStructCollection
   *
   */
  public ProductStructCollection(ProductCollectionId _cid) {
    super(_cid);
  }

  /**
   * ProductStructCollection
   *
   */
  public ProductStructCollection(Catalog _cat, ProductCollectionId _cid) {
    this(_cid);
    get(_cat);
  }

  /**
   * A simple test of this method
   *
   * @param args
   */
  public static void main(String[] args) {

    if (args == null || args.length == 0) {
      System.err.println("No arguments passed to main()! Options are \"1\" (Sync mode), or \"2\" (idl mode). Bailing out...");
    }

    if (args[0].equals("1")) {
      ProductStructCollection.syncToCatDb();
    }
    else if (args[0].equals("2")) {
      ProductStructCollection.idlToCatDb();
    }
    else {
      System.err.println("illegal argument in main() method. Passed = \"" +
                         args[0] + "\"");
    }

  }

  /**
   * Do non-brief and we will recurse the tree...
   */
  public final String dump(boolean _b) {
    ProductCollectionId pcid = getProductCollectionId();
    WorldWideProductId wwpid = pcid.getWorldWideProductId();
    String strHeader = "**ProductStructCollection **\n";
    if (wwpid != null) {
      strHeader += "=== WWPRODUCT: " + wwpid.getEntityType() + ":" +
          wwpid.getEntityID();
    }
    StringBuffer sb = new StringBuffer();
    sb.append(strHeader);
    if (!_b) {
      sb.append("\n     << " + values().size() + " Products >>");
      Iterator it = values().iterator();
      while (it.hasNext()) {
        Product prod = (Product) it.next();
        sb.append("\n     " + prod.dump(_b));
      }
    }
    return sb.toString();
  }

  public final String toString() {
    return "TBD";
  }

  public final boolean equals(Object obj) {
    return false;
  }

  public void getReferences(Catalog _cat, int _icase) {
    //
  }

  /**
   * This is going to fill out ALL Data (fat) and ALL references (deep) _iLvl's deep.
   */
  public void getFatAndDeep(Catalog _cat, int _iLvl) {
    if (_iLvl < 0) {
      return;
    }
    _iLvl--;
    get(_cat);
    getReferences(_cat, -1);
    Iterator it = values().iterator();
    while (it.hasNext()) {
      Product prod = (Product) it.next();
      prod.getFatAndDeep(_cat, _iLvl);
    }
  }

  /**
   * For debugging XML
   */
  public void dumpXML(XMLWriter _xml, boolean _bDeep) {
    Iterator it = values().iterator();
    if (_bDeep) {
      while (it.hasNext()) {
        Product prod = (Product) it.next();
        prod.dumpXML(_xml, _bDeep); //true
      }
    }
  }

  /**
    * deactivateStructure
    *
    * @param _wwpid WorldWideProductId
    */
   public void deactivateStructure(ProductId _pid, Catalog _cat,boolean _bcommit) {

     if (Catalog.isDryRun()) {
         return;
     }
      Database db = _cat.getCatalogDatabase();
//    Added by Guo Bin, 2007-08-03
      DatabaseExt dbe = new DatabaseExt(db);
      //ProductId pid = _prod.getProductId();
      String strLocEntityType = _pid.getEntityType();
      int iLocEntityID = _pid.getEntityId();
      GeneralAreaMapItem gami = _pid.getGami();
      String strEnterprise = gami.getEnterprise();
      int iNLSID = gami.getNLSID();
     String strCountryCode = gami.getCountry();
     String strLanguageCode = gami.getLanguage();


     /*
     Call the sp to deactivate all its relationships betw itself and prodstruct
     */
    try {
    	dbe.callGBL4025(new ReturnStatus( -1), strEnterprise, strLocEntityType, iLocEntityID, strCountryCode,
            strLanguageCode, iNLSID);
        if (_bcommit) {
            db.commit();
        }
        db.freeStatement();
        db.isPending();

    }
    catch (MiddlewareException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }


   }


  /**
   * Here is where we query and fill out the ProductId's
   */
  public void get(Catalog _cat) {

    ReturnDataResultSet rdrs = null;
    ResultSet rs = null;
    Database db = _cat.getCatalogDatabase();

    ProductCollectionId pcid = getProductCollectionId();
    GeneralAreaMapItem gami = pcid.getGami();
    String strEnterprise = gami.getEnterprise();
    int iNLSID = gami.getNLSID();
    String strWWEntityType = null;
    int iWWEntityID = -1;
    if (pcid.isFromCAT() && pcid.getWorldWideProductId() != null) {
      strWWEntityType = pcid.getWorldWideProductId().getEntityType();
      iWWEntityID = pcid.getWorldWideProductId().getEntityID();
    }
    ReturnStatus returnStatus = new ReturnStatus( -1);

    try {
      /*
       * This is the case where we are going by interval and we need
       * to create a SyncMap for each Root EntityType this guy represents.
       */
      if (pcid.isByInterval() && pcid.isFromPDH()) {

        this.processSyncMap(_cat);

      }
      if (pcid.isFromCAT()) {
        D.ebug(D.EBUG_SPEW,"ProductStructCollection: isFromCat()");
        if (pcid.getCollectionType() ==
            ProductCollectionId.BY_WWENTITYTYPE_WWENTITYID) {
          D.ebug(D.EBUG_SPEW,"ProductStructCollection: pcid.getCollectionType() == ProductCollectionId.BY_WWENTITYTYPE_WWENTITYID");
          try {
            rs = db.callGBL4016(returnStatus, strEnterprise, strWWEntityType,
                                iWWEntityID, iNLSID);
            rdrs = new ReturnDataResultSet(rs);
          }
          finally {
            rs.close();
            rs = null;
            db.commit();
            db.freeStatement();
            db.isPending();
          }

          for (int i = 0; i < rdrs.size(); i++) {

            String strLocEntityType = rdrs.getColumn(i, 0);
            int iLocEntityID = rdrs.getColumnInt(i, 1);

            //
            // Lets build one in the context of this list
            // we do this by passing the wwpcid into to wwpid
            //
            // this is much better than passing the entire structure as
            // we currently do in the eannounce object model
            //
            ProductId pid = new ProductId(strLocEntityType, iLocEntityID, gami,
                                          getProductCollectionId());
            Product prod = new Product(pid);
            //prod.setProductCollectionId(getProductCollectionId());
            this.put(prod);
          }
        }

      }
      else {
        //NOTHING YET!!!
      }
    }
    catch (MiddlewareException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public final ProductCollectionId getProductCollectionId() {
    return (ProductCollectionId)this.getId();
  }

  /**
   *  put - Since we are collection, this guy manages relationships between the
   *  things in this list.. and the controlling parent ID
   *
   * @param _cat
   * @param _bcommit
   */
  public void put(Catalog _cat, boolean _bcommit) {
    if (Catalog.isDryRun()) {
      D.ebug(D.EBUG_SPEW,
             this.getClass().getName() + " >>> Catalog.isDryRun() == true!!! Skipping put()! <<<");
      return;
    }
  }

  public void merge(CatItem _ci) {
    // TODO Auto-generated method stub

  }

  /**
   * First attempt at an XML writer usage - This sends the output to
   * Standard out for now.
   */
  public void generateXML() {

    Iterator it = null;

    XMLWriter xml = new XMLWriter();
    ProductCollectionId pcid = this.getProductCollectionId();
    CatalogInterval cati = pcid.getInterval();
    String strFromTimestamp = (pcid.isByInterval() ? cati.getStartDate() :
                               EPOCH);
    String strToTimestamp = (pcid.isByInterval() ? cati.getEndDate() : FOREVER);

    int iNumberOfElements = this.size();

    try {

      xml.writeEntity("ProductFile");

      xml.writeEntity("FromTimestamp");
      xml.write(strFromTimestamp);
      xml.endEntity();

      xml.writeEntity("ToTimestamp");
      xml.write(strToTimestamp);
      xml.endEntity();

      xml.writeEntity("NumberOfElements");
      xml.write(iNumberOfElements + "");
      xml.endEntity();

      /*
       * For now .. we just have the update command..
       */
      xml.writeEntity("Command");
      xml.write("update");
      xml.endEntity();

      xml.writeEntity("Source");
      xml.write("STG");
      xml.endEntity();

      it = values().iterator();

      while (it.hasNext()) {
        Product prod = (Product) it.next();
        prod.generateXMLFragment(xml);
      }

      xml.endEntity();

      xml.finishEntity();

    }
    catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  ///////////////////
  // SyncMap Stuff //
  ///////////////////
  /**
   * syncToCatDb
   * This guy will take all known root entities and process them in one
   * big chunk.. if we run out of of memory, we start employing memory
   * tricks we know all to well to chunk through them
   *
   */
  public static void syncToCatDb() {

    SyncMapCollection smcOuter = null;

    String strEntityType = null;
    String strVE = null;

    GeneralAreaMapGroup gamp = null;
    Enumeration en = null;

    int ichunk = 1000;
    
    //
    // Lets try to clean up the temp tables first
    //
//    try {
//        Catalog.execUnixShell(CatalogProperties.getIDLTempTableCleanUpScript(ProductCollection.class));
//    }
//    catch (InterruptedException ex1) {
//        ex1.printStackTrace();
//        System.exit( -1);
//    }
//    catch (IOException ex1) {
//        ex1.printStackTrace();
//        System.exit( -1);
//    }

    try {

      Class mclass = ProductCollection.class;
      String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);
      Catalog cat = new Catalog();
      cat.buildLanguageMapper();
      Database db = cat.getCatalogDatabase();
      GeneralAreaMap gam = cat.getGam();
      Profile prof = cat.getCatalogProfile();
      String strEnterprise = cat.getEnterprise();
      String strRoleCode = prof.getRoleCode();

      ReturnDataResultSet rdrs = null;
      ResultSet rs = null;

      Database pdhdb = cat.getPDHDatabase();
      Class tclass = ProductStructCollection.class;
      CatalogInterval cati = new CatalogInterval(tclass, cat);

      prof.setValOnEffOn(cati.getEndDate(), cati.getEndDate());

      int isLimitCountryCode=CatalogProperties.getLimitCountryCode(ProductStructCollection.class,ProdStruct.class);
      D.ebug(D.EBUG_SPEW, "ProductStructCollection.ProdStruct.LIMITCOUNTRYCODE is : "+isLimitCountryCode);
		if (db.getPDHConnection() == null) {
			db.connect();
		}	
		
      for (int icycle = 0; icycle < 3; icycle++) {
        //
        // lets clear it out
        //
        if (smcOuter != null) {
          smcOuter.clearAll();
        }

          if(icycle == 0) {
      		// LSEO section
      		strEntityType = Product.LSEO_ENTITYTYPE;
      		strVE = ProductStructCollection.LSEO_VE1;
            //smc1 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
		  }

          if(icycle == 1) {
		      strEntityType = Product.MODEL_ENTITYTYPE;
		      strVE = ProductStructCollection.MODEL_VE1;
		      //smc2 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
		  }
      	  //smc1.merge(smc2);

          if(icycle == 2) {
		      strEntityType = Product.LSEOBUNDLE_ENTITYTYPE;
		      strVE = ProductStructCollection.LSEOBUNDLE_VE1;
		      //smc3 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
		  }
          
          //smc1.merge(smc3);

          //D.ebug(D.EBUG_SPEW, smc1.toString());

          gamp = gam.lookupGeneralArea(strGeneralArea);
          en = gamp.elements();

          if (!en.hasMoreElements()) {
            D.ebug(D.EBUG_WARN,"no gami to find!!!");
          }

          //
          // Newest version of chunking
          //

          //
          // We want to load up trsnetter pass 1 here via gbl8184....!!!
          // We might want to abstract this call to SyncMapCoillectio later on....
          //
          int iSessionID = db.getNewSessionID();
          List sessionIdList = new ArrayList();
          sessionIdList.add(new Integer(iSessionID));
          try {
		 	db.callGBL8184(
		  		new ReturnStatus(-1),
		  		iSessionID,
		  		strEnterprise,
				strEntityType,
				strVE,
				strRoleCode,
				cati.getStartDate(),
				cati.getEndDate(),
				9, "", -1,1);
          }
          finally {
            db.commit();
            db.freeStatement();
            db.isPending();
          }

          int iChunk = CatalogProperties.getIDLChunkSize(mclass,strVE);
          try {
            rs = db.callGBL9012(new ReturnStatus( -1), strEnterprise,strEntityType, iSessionID, iChunk);
            rdrs = new ReturnDataResultSet(rs);
          }
          finally {
          if (rs != null) {
            rs.close();
            rs = null;
          }
            db.commit();
            db.freeStatement();
            db.isPending();
          }

          int iFloor = 0;
          int iCeiling = 0;
          int iStart = 0;
          int iEnd = 0;
          for (int x = 0; x < rdrs.size(); x++) {

          	iFloor = rdrs.getColumnInt(x, 0);
          	iCeiling = rdrs.getColumnInt(x, 1);
          	iStart = rdrs.getColumnInt(x, 2);
          	iEnd = rdrs.getColumnInt(x, 3);
          	D.ebug(D.EBUG_SPEW,
          	       "GBL9012:et" + strEntityType + ":floor:" + iFloor +
          	       ":ceiling:" + iCeiling + ":start:" + iStart +
          	       ":finish:" + iEnd);


            D.ebug(D.EBUG_SPEW,"Before new SyncMapCollection");
          	smcOuter = new SyncMapCollection(cat, strVE, strEntityType, iStart, iEnd, iSessionID, sessionIdList);
            D.ebug(D.EBUG_SPEW,"After new SyncMapCollection");

          	///
          	///

            //
          	if (!en.hasMoreElements()) {
          	  D.ebug(D.EBUG_WARN,"A ... no gami to find");
          	} else {
		      D.ebug(D.EBUG_WARN,"A gami elements found.");
			}

          	gamp = gam.lookupGeneralArea(strGeneralArea);
          	en = gamp.elements();

          	if (!en.hasMoreElements()) {
          	  D.ebug(D.EBUG_WARN,"B ... no gami to find");
          	} else {
		      D.ebug(D.EBUG_WARN,"B gami elements found.");
			}
            //

      		while (en.hasMoreElements()) {

		      D.ebug(D.EBUG_SPEW,"In en loop");

      		  Iterator it = null;

      		  GeneralAreaMapItem gami = (GeneralAreaMapItem) en.nextElement();
				D.ebug(D.EBUG_SPEW,"A");
      		  //
      		  // Lets set the NLSItem we need to be working in right now
      		  //
      		  Vector vct = prof.getReadLanguages();
      		  for (int i = 0; i < vct.size(); i++) {
      		      NLSItem nlsi = (NLSItem) vct.elementAt(i);
      		      D.ebug(D.EBUG_SPEW,"ProductStructCollection gami/prof check:nlsi.getNLSID()=" + nlsi.getNLSID() + ",gami.getNLSID()=" + gami.getNLSID());
      		      if (nlsi.getNLSID() == gami.getNLSID()) {
      		          D.ebug(D.EBUG_SPEW,"ProductStructCollection gami/prof check: setting read language to:" + nlsi.getNLSID());
      		          prof.setReadLanguage(nlsi);
      		          break;
      		      }
      		  }

      		  D.ebug(D.EBUG_SPEW,"B");

      		  ProductCollectionId pcid = new ProductCollectionId(cati, gami,
      		      CollectionId.PDH_SOURCE, CollectionId.FULL_IMAGES);

              D.ebug(D.EBUG_SPEW,"C");

      		  ProductStructCollection pc = new ProductStructCollection(pcid);
      		  pc.setSmc(smcOuter);

              D.ebug(D.EBUG_SPEW,"D");

      		  //
      		  // Lets go fill this out!
      		  // within here we processSyncMap, prune per THIS gami (per loop we are IN)..
      		  //
      		  pc.get(cat);

      		  //
      		  // ok.. we have stubbed it out. lets make them fat!
      		  //

      		  int icount = 0;

      		  it = pc.values().iterator();



      		  while (it.hasNext()) {

      		    icount++;

      		    Product prod = (Product) it.next();
      		    ProductId pid = prod.getProductId();

      		    //For each product, stub out its chidren
      		    D.ebug(D.EBUG_SPEW, "Deactivating structure for " + pid.dump(false));
      		    pc.deactivateStructure(pid, cat, false);

      		    D.ebug(D.EBUG_SPEW,"syncToCatDb() .. getting prod and its prod struct collection... " +
      		           prod.toString());
				prod.get(cat);
      		    //
      		    // Now.. lets
      		    //
      		    D.ebug(D.EBUG_SPEW,"syncToCatDb() .. updating the prod to the CatDb... " +
      		           prod.toString());
      		    // delete by guobin 2007-10-16
      		    //prod.put(cat, false);    				

      		    //the following is for ProdStruct under this PRODENTITYID
      		    prod.getReferences(cat, -9 /*i dunno!?!?*/);
      		    //
      		    // DWB .. maybe we can pull when needed
      		    //
      		    ProdStructCollection psc = prod.getProdStructCollection();
      		    D.ebug(D.EBUG_SPEW,"syncToCatDb() ProdStructCollection size is :" + psc.size());
      		    Iterator it2 = psc.values().iterator();
      		    while (it2.hasNext()) {
      		      ProdStruct ps = (ProdStruct) it2.next();
      		      ps.get(cat);
      		      //
      		      // we would normally compare here
      		      // before we do a put
      		      //
      		      if (isLimitCountryCode==0 || (isLimitCountryCode==1 && !hasCountryCode(db.getPDHConnection(),gami.getEnterprise(),gami.getCountry(), ps))) {
    					ps.put(cat, false);
    				  }
      		    }            		

       		    if (icount == ichunk) {
      		      db.commit();
      		      D.ebug(D.EBUG_SPEW, "syncToCatDb() *** COMMITING ***");
      		      icount = 0;
      		    }

      		  }

		      db.commit();

              //
		      // lets clear it out
		      //
		      //if (smcOuter != null) {
		      //    smcOuter.clearAll();
              //}
		  }

	    }
          
//        Let delete the temptables by sessionID
            DatabaseExt dbe = new DatabaseExt(db);
            for (int i = 0; i < sessionIdList.size(); i++) {
                int sessionId = ((Integer) sessionIdList.get(i)).intValue();
                try {
                    dbe.callGBL8109A(new ReturnStatus(-1), sessionId,
                            strEnterprise);
                } catch (MiddlewareException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        db.commit();
                        db.freeStatement();
                        db.isPending();
                    } catch (SQLException _ex) {
                        _ex.printStackTrace();
                    }
                }
            }
            try {
                Catalog.execUnixShell(CatalogProperties.getIDLTempTableReorgScript(ProductStructCollection.class));
            }
            catch (InterruptedException ex1) {
                ex1.printStackTrace();
                System.exit( -1);
            }
            catch (IOException ex1) {
                ex1.printStackTrace();
                System.exit( -1);
            }

      }

      cati.put(cat);
      db.close();
      Catalog.resetLists();

    }
    catch (MiddlewareException ex) {
      ex.printStackTrace();
    }
    catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
  /**
   * parallelSyncToCatDb
   * 
   * Added by  Guo Bin, 2007-08-03 For break-up stored procedures based on
   * Collection
   */
  public static void parallelSyncToCatDb() {

    SyncMapCollection smcOuter = null;

    String strEntityType = null;
    String strVE = null;

    GeneralAreaMapGroup gamp = null;
    Enumeration en = null;

    int ichunk = 1000;

    try {

      Class mclass = ProductCollection.class;
      String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);
      Catalog cat = new Catalog();
      cat.buildLanguageMapper();
      Database db = cat.getCatalogDatabase();
      GeneralAreaMap gam = cat.getGam();
      Profile prof = cat.getCatalogProfile();
      String strEnterprise = cat.getEnterprise();
      String strRoleCode = prof.getRoleCode();
      //Added by Guo Bin, 2007-08-03
      DatabaseExt dbe = new DatabaseExt(db);
      
      ReturnDataResultSet rdrs = null;
      ResultSet rs = null;

      Database pdhdb = cat.getPDHDatabase();
      Class tclass = ProductStructCollection.class;
      CatalogInterval cati = new CatalogInterval(tclass, cat);

      prof.setValOnEffOn(cati.getEndDate(), cati.getEndDate());

      int isLimitCountryCode=CatalogProperties.getLimitCountryCode(ProductStructCollection.class,ProdStruct.class);
      D.ebug(D.EBUG_SPEW, "ProductStructCollection.ProdStruct.LIMITCOUNTRYCODE is : "+isLimitCountryCode);
		if (db.getPDHConnection() == null) {
			db.connect();
		}	
		
      for (int icycle = 0; icycle < 3; icycle++) {
        //
        // lets clear it out
        //
        if (smcOuter != null) {
          smcOuter.clearAll();
        }

          if(icycle == 0) {
      		// LSEO section
      		strEntityType = Product.LSEO_ENTITYTYPE;
      		strVE = ProductStructCollection.LSEO_VE1;
            //smc1 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
		  }

          if(icycle == 1) {
		      strEntityType = Product.MODEL_ENTITYTYPE;
		      strVE = ProductStructCollection.MODEL_VE1;
		      //smc2 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
		  }
      	  //smc1.merge(smc2);

          if(icycle == 2) {
		      strEntityType = Product.LSEOBUNDLE_ENTITYTYPE;
		      strVE = ProductStructCollection.LSEOBUNDLE_VE1;
		      //smc3 = new SyncMapCollection(cat, strEntityType, strVE, cati, 2);
		  }
          //smc1.merge(smc3);

          gamp = gam.lookupGeneralArea(strGeneralArea);
          en = gamp.elements();

          if (!en.hasMoreElements()) {
            D.ebug(D.EBUG_WARN,"no gami to find!!!");
          }

          //
          // Newest version of chunking
          //

          //
          // We want to load up trsnetter pass 1 here via gbl8184....!!!
          // We might want to abstract this call to SyncMapCoillectio later on....
          //
          int iSessionID = db.getNewSessionID();
          try {
		 	dbe.callGBL8184(
		  		new ReturnStatus(-1),
		  		iSessionID,
		  		strEnterprise,
				strEntityType,
				strVE,
				strRoleCode,
				cati.getStartDate(),
				cati.getEndDate(),
				9, "", -1,1,SP_SUFFIX);
          }
          finally {
            db.commit();
            db.freeStatement();
            db.isPending();
          }

          int iChunk = CatalogProperties.getIDLChunkSize(mclass,strVE);
          try {
            rs = dbe.callGBL9012(new ReturnStatus( -1), strEnterprise,strEntityType, iSessionID, iChunk,SP_SUFFIX);
            rdrs = new ReturnDataResultSet(rs);
          }
          finally {
          if (rs != null) {
            rs.close();
            rs = null;
          }
            db.commit();
            db.freeStatement();
            db.isPending();
          }

          int iFloor = 0;
          int iCeiling = 0;
          int iStart = 0;
          int iEnd = 0;
          for (int x = 0; x < rdrs.size(); x++) {

          	iFloor = rdrs.getColumnInt(x, 0);
          	iCeiling = rdrs.getColumnInt(x, 1);
          	iStart = rdrs.getColumnInt(x, 2);
          	iEnd = rdrs.getColumnInt(x, 3);
          	D.ebug(D.EBUG_SPEW,
          	       "GBL9012:et" + strEntityType + ":floor:" + iFloor +
          	       ":ceiling:" + iCeiling + ":start:" + iStart +
          	       ":finish:" + iEnd);


            D.ebug(D.EBUG_SPEW,"Before new SyncMapCollection");
          	smcOuter = new SyncMapCollection(cat, strVE, strEntityType, iStart, iEnd, iSessionID,SP_SUFFIX);
            D.ebug(D.EBUG_SPEW,"After new SyncMapCollection");

          	///
          	///

            //
          	if (!en.hasMoreElements()) {
          	  D.ebug(D.EBUG_WARN,"A ... no gami to find");
          	} else {
		      D.ebug(D.EBUG_WARN,"A gami elements found.");
			}

          	gamp = gam.lookupGeneralArea(strGeneralArea);
          	en = gamp.elements();

          	if (!en.hasMoreElements()) {
          	  D.ebug(D.EBUG_WARN,"B ... no gami to find");
          	} else {
		      D.ebug(D.EBUG_WARN,"B gami elements found.");
			}
            //

      		while (en.hasMoreElements()) {

		      D.ebug(D.EBUG_SPEW,"In en loop");

      		  Iterator it = null;

      		  GeneralAreaMapItem gami = (GeneralAreaMapItem) en.nextElement();
				D.ebug(D.EBUG_SPEW,"A");
      		  //
      		  // Lets set the NLSItem we need to be working in right now
      		  //
      		  Vector vct = prof.getReadLanguages();
      		  for (int i = 0; i < vct.size(); i++) {
      		      NLSItem nlsi = (NLSItem) vct.elementAt(i);
      		      D.ebug(D.EBUG_SPEW,"ProductStructCollection gami/prof check:nlsi.getNLSID()=" + nlsi.getNLSID() + ",gami.getNLSID()=" + gami.getNLSID());
      		      if (nlsi.getNLSID() == gami.getNLSID()) {
      		          D.ebug(D.EBUG_SPEW,"ProductStructCollection gami/prof check: setting read language to:" + nlsi.getNLSID());
      		          prof.setReadLanguage(nlsi);
      		          break;
      		      }
      		  }

      		  D.ebug(D.EBUG_SPEW,"B");

      		  ProductCollectionId pcid = new ProductCollectionId(cati, gami,
      		      CollectionId.PDH_SOURCE, CollectionId.FULL_IMAGES);

              D.ebug(D.EBUG_SPEW,"C");

      		  ProductStructCollection pc = new ProductStructCollection(pcid);
      		  pc.setSmc(smcOuter);

              D.ebug(D.EBUG_SPEW,"D");

      		  //
      		  // Lets go fill this out!
      		  // within here we processSyncMap, prune per THIS gami (per loop we are IN)..
      		  //
      		  pc.get(cat);

      		  //
      		  // ok.. we have stubbed it out. lets make them fat!
      		  //

      		  int icount = 0;
      		  

      		  it = pc.values().iterator();
              if(it.hasNext()){
            	  D.ebug(D.EBUG_SPEW,"syncToCatDb() .. have Product");
              }else{
            	  D.ebug(D.EBUG_SPEW,"syncToCatDb() ..don't have Product"); 
              }
              
      		  while (it.hasNext()) {

      		    icount++;

      		    Product prod = (Product) it.next();
      		    ProductId pid = prod.getProductId();

      		    //For each product, stub out its chidren
      		    D.ebug(D.EBUG_SPEW, "Deactivating structure for " + pid.dump(false));
      		    pc.deactivateStructure(pid, cat, false);

      		    D.ebug(D.EBUG_SPEW,"syncToCatDb() .. getting prod and its prod struct collection... " +
      		           prod.toString());
      		    
				prod.get(cat);
      		    //
      		    // Now.. lets
      		    //
      		    D.ebug(D.EBUG_SPEW,"syncToCatDb() .. updating the prod to the CatDb... " +
      		           prod.toString());
      		     //  prod.put(cat, false);    				

      		    //the following is for ProdStruct under this PRODENTITYID
      		    prod.getReferences(cat, -9 /*i dunno!?!?*/);
      		    //
      		    // DWB .. maybe we can pull when needed
      		    //  		    
      		    ProdStructCollection psc = prod.getProdStructCollection();
      		    D.ebug(D.EBUG_SPEW,"syncToCatDb() ProdStructCollection size is :" + psc.size());
      		    Iterator it2 = psc.values().iterator();
      		    while (it2.hasNext()) {
      		      ProdStruct ps = (ProdStruct) it2.next();
      		      ps.get(cat);
      		      //
      		      // we would normally compare here
      		      // before we do a put
      		      //  		     
      		      if (isLimitCountryCode==0 || (isLimitCountryCode==1 && !hasCountryCode(db.getPDHConnection(),gami.getEnterprise(),gami.getCountry(), ps))) {
  					ps.put(cat, false);
  				  }
      		    }	    

      		    if (icount == ichunk) {
      		      db.commit();
      		      D.ebug(D.EBUG_SPEW, "syncToCatDb() *** COMMITING ***");
      		      icount = 0;
      		    }

      		  }

		      db.commit();

              //
		      // lets clear it out
		      //
		      //if (smcOuter != null) {
		      //    smcOuter.clearAll();
              //}
		  }

	    }

      }

      cati.put(cat);
      db.close();
      Catalog.resetLists();

    }
    catch (MiddlewareException ex) {
      ex.printStackTrace();
    }
    catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  /**
   * idlToCatDb
   * This guy will take all known root entities and process them in one
   * big chunk.. if we run out of of memory, we start employing memory
   * tricks we know all to well to chunk through them
   *
   */
  public static void idlToCatDb() {

    SyncMapCollection smc1 = null;
    SyncMapCollection smc2 = null;

    String strEntityType = null;
    String strVE = null;

    GeneralAreaMapGroup gamp = null;
    Enumeration en = null;
    ReturnDataResultSet rdrs = null;
    ResultSet rs = null;
    
    //
    // Lets try to clean up the temp tables first
    //
//    try {
//        Catalog.execUnixShell(CatalogProperties.getIDLTempTableCleanUpScript(ProductStructCollection.class));
//    }
//    catch (InterruptedException ex1) {
//        ex1.printStackTrace();
//        System.exit( -1);
//    }
//    catch (IOException ex1) {
//        ex1.printStackTrace();
//        System.exit( -1);
//    }

    try {

      Class mclass = ProductCollection.class;
      String strGeneralArea = CatalogProperties.getGeneralAreaKey(mclass);
      Catalog cat = new Catalog();
      cat.buildLanguageMapper();
      GeneralAreaMap gam = cat.getGam();
      Profile prof = cat.getCatalogProfile();
      String strEnterprise = cat.getEnterprise();
      Database db = cat.getCatalogDatabase();
      Database pdhdb = cat.getPDHDatabase();

      CatalogInterval cati = new CatalogInterval(mclass, cat);
      prof.setValOnEffOn(cati.getEndDate(), cati.getEndDate());

      //
      // OK lets do this loop for real
      // we will make two passes..
      //

      for (int icycle = 0; icycle < 3; icycle++) {
        if (icycle == 0) {
          strEntityType = Product.MODEL_ENTITYTYPE;
          strVE = ProductStructCollection.MODEL_VE1;
        }
        else if(icycle == 1) {
          strEntityType = Product.LSEO_ENTITYTYPE;
          strVE = ProductStructCollection.LSEO_VE1;
        } else {
          strEntityType = Product.LSEOBUNDLE_ENTITYTYPE;
          strVE = ProductStructCollection.LSEOBUNDLE_VE1;
        }
        //add by houjie&liubing 2007-09-12, get configurable NLSID information for Product,ProdStruct
        int iNLSSave[]=new int[2];
        iNLSSave[0] = CatalogProperties.getSaveNLSID(ProductStructCollection.class,Product.class, strVE);
        iNLSSave[1] = CatalogProperties.getSaveNLSID(ProductStructCollection.class,ProdStruct.class, strVE);
        D.ebug(D.EBUG_SPEW,"ProductStructCollection.Product."+strVE+"_nlssave="+iNLSSave[0]);
        D.ebug(D.EBUG_SPEW,"ProductStructCollection.ProdStruct."+strVE+"_nlssave="+iNLSSave[1]);
        //
        // Newest version of chunking
        //
        int iChunk = 1000;
        try {
          rs = pdhdb.callGBL9010(new ReturnStatus( -1), strEnterprise,
                                 strEntityType, iChunk);
          rdrs = new ReturnDataResultSet(rs);
        }
        finally {
          if (rs != null) {
            rs.close();
            rs = null;
          }
          pdhdb.commit();
          pdhdb.freeStatement();
          pdhdb.isPending();
        }

        int iFloor = 0;
        int iCeiling = 0;
        int iStart = 0;
        int iEnd = 0;
        for (int x = 0; x < rdrs.size(); x++) {

          iFloor = rdrs.getColumnInt(x, 0);
          iCeiling = rdrs.getColumnInt(x, 1);
          iStart = rdrs.getColumnInt(x, 2);
          iEnd = rdrs.getColumnInt(x, 3);
          D.ebug(D.EBUG_SPEW,
                 "GBL9010:et" + strEntityType + ":floor:" + iFloor +
                 ":ceiling:" + iCeiling + ":start:" + iStart + ":finish:" +
                 iEnd);

          smc1 = new SyncMapCollection(cat, strVE, strEntityType, iStart, iEnd);

          D.ebug(D.EBUG_SPEW, smc1.toString());

          gamp = gam.lookupGeneralArea(strGeneralArea);
          en = gamp.elements();

          if (!en.hasMoreElements()) {
            System.out.println("no gami to find!!!");
          }

          while (en.hasMoreElements()) {

            Iterator it = null;

            GeneralAreaMapItem gami = (GeneralAreaMapItem) en.nextElement();

        	//
        	// Lets set the NLSItem we need to be working in right now
        	//
        	Vector vct = prof.getReadLanguages();
        	for (int i = 0; i < vct.size(); i++) {
        	    NLSItem nlsi = (NLSItem) vct.elementAt(i);
        	    D.ebug(D.EBUG_SPEW,"ProductStructCollection gami/prof check:nlsi.getNLSID()=" + nlsi.getNLSID() + ",gami.getNLSID()=" + gami.getNLSID());
        	    if (nlsi.getNLSID() == gami.getNLSID()) {
        	        D.ebug(D.EBUG_SPEW,"ProductStructCollection gami/prof check: setting read language to:" + nlsi.getNLSID());
        	        prof.setReadLanguage(nlsi);
        	        break;
        	    }
        	}

            ProductCollectionId pcid = new ProductCollectionId(cati, gami,
                CollectionId.PDH_SOURCE, CollectionId.FULL_IMAGES);

            ProductStructCollection pc = new ProductStructCollection(pcid);
            pc.setSmc(smc1);

            //
            // Lets go fill this out!
            // within here we processSyncMap, prune per THIS gami (per loop we are IN)..
            //
            pc.get(cat);

            //
            // ok.. we have stubbed it out. lets make them fat!
            //

            int icount = 0;

            it = pc.values().iterator();

            while (it.hasNext()) {

              icount++;

              Product prod = (Product) it.next();

              SyncMapCollection smc = prod.getSmc();
              D.ebug(D.EBUG_SPEW, smc.toString());

              D.ebug(D.EBUG_SPEW,
                  "idlToCatDb() .. getting prod and its prod struct collection... " +
                     prod.toString());
              //add by houjie&liubing 2007-09-12 : check NLSID for Product
			if(iNLSSave[0]==0 || iNLSSave[0]==gami.getNLSID()){  
				prod.get(cat);
	              //
	              // Now.. lets
	              //
	              D.ebug(D.EBUG_SPEW,
	                     "idlToCatDb() .. updating the prod to the CatDb... " +
	                     prod.toString());
	            prod.put(cat, false);				
			}else{
                D.ebug(D.EBUG_SPEW,
                        ">>Skipping Product save for " + prod.getId() + ".  Targeting NLSID of " +
                        iNLSSave[0] + " not " + gami.getNLSID());                            		
        	}
        	//add by houjie&liubing 2007-09-12 : check NLSID for ProdStruct
        	if(iNLSSave[1]==0 || iNLSSave[1]==gami.getNLSID()){			              
        		prod.getReferences(cat, -9 /*i dunno!?!?*/);
                //
                // DWB .. maybe we can pull when needed
                //
                ProdStructCollection psc = prod.getProdStructCollection();
                D.ebug(D.EBUG_SPEW,
                       "idlToCatDb() ProdStructCollection size is :" + psc.size());
                Iterator it2 = psc.values().iterator();
                while (it2.hasNext()) {
                  ProdStruct ps = (ProdStruct) it2.next();
                  ps.get(cat);
                  //
                  // we would normally compare here
                  // before we do a put
                  //
                  ps.put(cat, false);
                }
        	}else{
                D.ebug(D.EBUG_SPEW,
                        ">>Skipping ProdStruct save for " + prod.getId() + ".  Targeting NLSID of " +
                        iNLSSave[1] + " not " + gami.getNLSID());                            		
        	}
              if (icount == iChunk) {
                db.commit();
                D.ebug(D.EBUG_SPEW, "idlToCatDb() *** COMMITING ***");
                icount = 0;
              }

            }

            //
            // ok.. lets see what we get!
            //
            //pc.generateXML();

            D.ebug(D.EBUG_SPEW, pc.dump(false));

            db.commit();

          }
        }
      }

      cati.put(cat);
      db.close();
      Catalog.resetLists();

    }
    catch (MiddlewareException ex) {
      ex.printStackTrace();
    }
    catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  /**
   * This is they guy that drives reading and drilling down through the SyncMapCollection and
   * bringing the Catalog Up to speed The sync map is  going to be the same one no matter the
   * language and intent.. we will need to sort this one out as we move further down the
   * road
   */
  public void processSyncMap(Catalog _cat) {

    ProductCollectionId pcid = this.getProductCollectionId();
    GeneralAreaMapItem gami = pcid.getGami();
    SyncMapCollection smc = this.getSmc();

    // Lets make some Product Ids
    // And lets get t

    for (int x = 0; x < smc.getCount(); x++) {
      SyncMapItem smi = smc.get(x);

      D.ebug(D.EBUG_SPEW,"processSyncMap:" + smi.toString());

      //
      Database db = _cat.getCatalogDatabase();
      Profile prof = _cat.getCatalogProfile();
      EntityItem eiRoot = Catalog.getEntityItem(_cat, smi.getRootType(), smi.getRootID());
      D.ebug(D.EBUG_SPEW,">>>> check smi:" + smi.getChildType() + ":" + smi.getRootType());
      /*
      try {
      	if(smi.getChildType().equals("PRODSTRUCT") && smi.getChildID() == 250756) {
      		ExtractActionItem xai = new ExtractActionItem(null, db, prof, "VEMODELPRODUCT1");

      		xai.setSkipCleanup(true);

      		EntityList elFudge = EntityList.getEntityList(db, prof, xai, new EntityItem[]{eiRoot});
      		D.ebug(D.EBUG_SPEW,"dumping elFudge:" + elFudge.dump(false));
			}
		} catch(Exception exc) {
			D.ebug(D.EBUG_ERR,"elFudge error:" + exc.toString());
			exc.printStackTrace(System.err);
		}
		*/
      //

      ProductId pid = new ProductId(smi.getRootType(), smi.getRootID(), gami,pcid);
      Product prod = (Product)this.get(pid);
      if (prod == null) {
        prod = new Product(pid);
        prod.setSmc(new SyncMapCollection());
        this.put(prod);
      }

      // Here's my thinking... let's set false initially, and if we find ANY records ON, we'll turn it on.
      prod.setActive(false);
      prod.getSmc().add(smi);
      //D.ebug(D.EBUG_DETAIL,"prod.smc count is:" + prod.getSmc().getCount());
    }

    // Now prune SMC based on gami...
    // let's do this grouped by Products...
    pruneSmc(_cat);
    ///

    // GAB check for isACtive...we want to find ONE!!
    Iterator it = this.values().iterator();
    while (it.hasNext()) {
         Product prod = (Product) it.next();
       SMI_LOOP:
     	 for (int x = 0; x < prod.getSmc().getCount(); x++) {
             SyncMapItem smi = prod.getSmc().get(x);
             String strroottype = smi.getRootType();
             int introotid = smi.getRootID();
             if(smi.getChildTran().equals("ON") && (prod.isCountryList(_cat,gami,strroottype,introotid)
            		 || strroottype.equals("MODEL"))) 
             {
				 prod.setActive(true);
				 break SMI_LOOP;
			 }
		 }
    }


  }

  /**
   * Remove any Products from our list which do not match the gami we are working in.
   *
   */
  private void pruneSmc(Catalog _cat) {
      ProductCollectionId pcid = this.getProductCollectionId();
      GeneralAreaMapItem gami = pcid.getGami();
      String strCountryList = gami.getCountryList();
      Iterator it = this.values().iterator();
      while (it.hasNext()) {
          Product prod = (Product) it.next();
          // no pruning for LSEOBUNDLE we are assuming...
          if (prod.getStringVal(Product.LOCENTITYTYPE).equals("LCTO") || prod.getStringVal(Product.LOCENTITYTYPE).equals("LSEO")) {
              D.ebug(D.EBUG_SPEW,"pruneSmc: ok, going off to prune:" + prod.getStringVal(Product.LOCENTITYTYPE) + ":" + prod.getStringVal(Product.LOCENTITYID));
              //For CR072607687
              prod.pruneSmcForStruct(_cat);

          }
      }
      // Per Spec Change 050506- we don't want to remove PRODUCTS per se.
      // Products are now filtered out by whether or not they have matching CATLGPUB
  }

  /**
   * getSmc
   *
   * @return
   */
  public SyncMapCollection getSmc() {
    return m_smc;
  }

  /**
   * setSmc
   *
   * @param collection
   */
  public void setSmc(SyncMapCollection collection) {
    m_smc = collection;
  }

  /**
   *  (non-Javadoc)
   *
   * @see COM.ibm.eannounce.catalog.CatSync#hasSyncMapCollection()
   */
  public final boolean hasSyncMapCollection() {
    return m_smc != null;
  }


  /**
   * Remove any Products from our list which do not match a CATLGPUB
   *
   */
   /*
  private void filterByCATLGPUBS(Catalog _cat) {
      Vector vctRemoves = new Vector();
      Iterator it = this.values().iterator();
      while (it.hasNext()) {
          Product prod = (Product) it.next();

      	  if(!prod.matchesCATLGPUB(_cat)) {
          	vctRemoves.addElement(prod);
          }

      }
      for (int i = 0; i < vctRemoves.size(); i++) {
    	  Product prod = (Product) vctRemoves.elementAt(i);
          D.ebug(D.EBUG_SPEW, "filterByCATLGPUBS->Removing Product:" + prod + " from list");
          //this.remove(prod);
          prod.setActive(false);
      }
  }*/

//  private static boolean hasCountryCode(HashMap map, String countryCode,String key) {
//		boolean isExist = false;
//		//String key=String.valueOf(ps.getSTRUCTENTITYID());
//		HashMap countryMap=(HashMap)map.get(key);//for each STRUCTENTITYID
//		if(countryMap!=null){
//			if(countryMap.get(countryCode)!=null){
//				isExist=true;
//			}else{
//				countryMap.put(countryCode,countryCode);
//			}
//		}else{
//			countryMap=new HashMap();
//			countryMap.put(countryCode,countryCode);
//			map.put(key,countryMap);
//		}
//		return isExist;
//	}
  
  private static boolean hasCountryCode(Connection conn, String enterprise,
			String countrycode, ProdStruct ps) {
		boolean isExist = false;
		PreparedStatement stmt = null;
		
		String sql = "select COUNTRYCODE from GBLI.ProdStruct where COUNTRYCODE=? and ENTERPRISE=? and PRODENTITYTYPE=? and PRODENTITYID=? and STRUCTENTITYTYPE=? and STRUCTENTITYID=? and FEATENTITYTYPE=? and FEATENTITYID=? and isactive=1 fetch first 1 rows only";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, countrycode);
			stmt.setString(2, enterprise);
			stmt.setString(3, ps.getPRODENTITYTYPE());
			stmt.setInt(4, ps.getPRODENTITYID());
			stmt.setString(5, ps.getSTRUCTENTITYTYPE());
			stmt.setInt(6, ps.getSTRUCTENTITYID());
			stmt.setString(7, ps.getFEATENTITYTYPE());
			stmt.setInt(8, ps.getFEATENTITYID());

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				isExist = true;
			} else {
				isExist = false;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isExist;
	}    
}
