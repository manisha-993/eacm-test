//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eProductUpdater.java,v $
// Revision 1.166  2008/01/31 21:05:17  wendy
// Cleanup RSA warnings
//
// Revision 1.165  2004/11/09 00:19:52  gregg
// slowly adding in debugs
//
// Revision 1.164  2004/11/08 20:52:07  gregg
// to many debugs these days ...
// we need to start over on these to see whats goin on
//
// Revision 1.163  2004/11/05 18:43:41  gregg
// update fix
//
// Revision 1.162  2004/10/29 21:08:39  gregg
// working on new product update logic
//
// Revision 1.161  2004/10/28 19:49:40  gregg
// commit at end when products are pulled
//
// Revision 1.160  2004/10/27 20:30:50  gregg
// some updates - think we're working again w/ eDocAdapter class
//
// Revision 1.159  2004/10/25 17:45:30  gregg
// opening up some accessors in eProductUpdater
//
// Revision 1.158  2004/10/25 17:24:36  gregg
// getNextProduct returns array of all NLS for an eProduct
//
// Revision 1.157  2004/10/25 17:17:42  gregg
// compile fix
//
// Revision 1.156  2004/10/25 17:12:52  gregg
// new eDocAdapter class:
//   moving all eProduct object building logic from eProdctUpdater up into eDocAdapter
//
// Revision 1.155  2004/10/19 21:00:40  dave
// more client settings
//
// Revision 1.154  2004/10/15 20:56:04  dave
// trying section w/o properties file
//
// Revision 1.153  2004/10/15 20:39:57  dave
// more trace
//
// Revision 1.152  2004/10/15 20:25:32  dave
// added TIMING trace
//
// Revision 1.151  2004/10/15 19:45:56  gregg
// whack unreachable code
//
// Revision 1.150  2004/10/15 19:40:19  gregg
// switch'n it up w/ class type checks
//
// Revision 1.149  2004/10/15 18:09:29  gregg
// avoiding instanceof
//
// Revision 1.148  2004/10/15 17:58:55  gregg
// more ticky tack tweaks
//
// Revision 1.147  2004/10/15 17:49:12  gregg
// oops fix
//
// Revision 1.146  2004/10/15 17:48:09  gregg
// sweatin the small stuff
//
// Revision 1.145  2004/10/15 00:24:44  dave
// moving nulling back to top of outer loop
//
// Revision 1.144  2004/10/15 00:09:07  dave
// more trace
//
// Revision 1.143  2004/10/14 23:56:26  dave
// more trace
//
// Revision 1.142  2004/10/14 22:39:30  dave
// some tracking
//
// Revision 1.141  2004/10/14 20:50:11  dave
// whoops ... wrong order
//
// Revision 1.140  2004/10/14 20:02:17  dave
// speed up
//
// Revision 1.139  2004/10/14 18:42:01  dave
// more trace for timing
//
// Revision 1.138  2004/10/14 17:56:27  dave
// some cleanup
//
// Revision 1.137  2004/10/14 17:19:02  dave
// more trace for ECCM Stuff
//
// Revision 1.136  2004/10/07 05:31:22  dave
// ok from protected to public
//
// Revision 1.135  2004/10/07 05:29:44  dave
// some close cleanup
//
// Revision 1.134  2004/10/06 20:56:29  gregg
// fixing some publish/unpublish logic
//
// Revision 1.133  2004/10/06 17:51:02  gregg
// ok -- delete product on process one entity only, but tag product for insert new
//
// Revision 1.132  2004/10/06 17:49:22  gregg
// dont first delete product in process one entity only mode
//
// Revision 1.131  2004/09/30 02:17:11  dave
// more trace and chunky monkey display
//
// Revision 1.130  2004/09/30 01:01:00  dave
// more sp fix
//
// Revision 1.129  2004/09/30 00:45:31  dave
// more syntax
//
// Revision 1.128  2004/09/30 00:22:40  dave
// syntax
//
// Revision 1.127  2004/09/29 22:48:57  gregg
// more debugerry
//
// Revision 1.126  2004/09/29 22:38:26  gregg
// move more debugs to D.ebug
//
// Revision 1.125  2004/09/29 21:53:39  gregg
// more self correcting logic .... in case of an exception updating, we try for a new complete snapshot of our product
//
// Revision 1.124  2004/09/29 21:05:57  gregg
// remove Thread.dumpStack
//
// Revision 1.123  2004/09/29 21:04:36  gregg
// logging some PROD updates for timing purposes
//
// Revision 1.122  2004/09/29 17:59:31  gregg
// debug EntityItemCount on EntityGroups in getEntityItem()
//
// Revision 1.121  2004/09/29 17:55:05  gregg
// auto-fix missing product header row.
//
// Revision 1.120  2004/09/28 20:49:58  gregg
// eProduct.deleteProductDetails method
//
// Revision 1.119  2004/09/28 17:56:28  gregg
// more tuning
//
// Revision 1.118  2004/09/28 16:38:54  gregg
// property-ize MAX_ENTITYITEMS_STORED
//
// Revision 1.117  2004/09/28 16:36:12  gregg
//   MAX_ENTITYITEMS_STORED variable
//
// Revision 1.116  2004/09/28 16:34:28  gregg
// some experimental tuning
//
// Revision 1.115  2004/09/24 22:14:42  gregg
// notha fix
//
// Revision 1.114  2004/09/24 22:08:22  gregg
// sum fixez
//
// Revision 1.113  2004/09/24 22:00:18  gregg
// de-singletonize PreparedStatementCollection.
// This is now accessed through eProductUpdater.
//
// Revision 1.112  2004/09/24 01:07:38  dave
// syntax
//
// Revision 1.111  2004/09/24 00:54:29  dave
// more trace
//
// Revision 1.110  2004/09/24 00:09:25  dave
// trace
//
// Revision 1.109  2004/09/23 20:32:49  gregg
// processInBoundRules on update of product
//
// Revision 1.108  2004/09/23 16:12:43  gregg
// more props
//
// Revision 1.107  2004/09/23 15:58:25  gregg
// remove debugs
//
// Revision 1.106  2004/09/23 15:36:25  gregg
// restrict processing products to specified genareas
//
// Revision 1.105  2004/09/22 21:56:52  gregg
// wereSequencesUpdated() on eProductDetail + fix isEquivalent
//
// Revision 1.104  2004/09/22 18:51:30  gregg
// some Database passing for Steve
//
// Revision 1.103  2004/09/22 17:49:59  gregg
// some updates
//
// Revision 1.102  2004/09/21 20:06:11  gregg
// change some err logging to debug
//
// Revision 1.101  2004/09/21 17:09:29  gregg
// some PreparedStatement reuse
//
// Revision 1.100  2004/09/20 23:52:14  gregg
// static reordering
//
// Revision 1.99  2004/09/20 21:49:48  gregg
// blob file name
//
// Revision 1.98  2004/09/20 19:06:34  gregg
// remove debugs
//
// Revision 1.97  2004/09/20 19:03:36  gregg
// fix flag mappings for CCECONTROLOVERRIDES
//
// Revision 1.96  2004/09/20 18:38:34  gregg
// CCECONTROLOVERRIDES int setting fix thing
//
// Revision 1.95  2004/09/20 18:33:04  gregg
// fix audience int setting
//
// Revision 1.94  2004/09/19 23:54:24  dave
// audience or ccecontrol overrides do not have
// to be present
//
// Revision 1.93  2004/09/17 20:45:37  gregg
// tossing audience/control flag update logic over the proverbial wall...
//
// Revision 1.92  2004/09/17 17:48:09  gregg
// 12 new control flags - init to 0
//
// Revision 1.91  2004/09/16 21:30:50  gregg
// fix updateSequences
//
// Revision 1.90  2004/09/16 18:58:34  gregg
// some debugs on switches
//
// Revision 1.89  2004/09/16 18:56:24  gregg
// skip product check for sessionid oulls, entityid pulls
//
// Revision 1.88  2004/09/16 18:46:57  gregg
// skipProductCheck for SBB
//
// Revision 1.87  2004/09/16 17:21:41  gregg
// limit redundant updateSequence calls.
//
// Revision 1.86  2004/09/15 18:48:16  gregg
// removing debugs, conserving object creation, re-route logging
//
// Revision 1.85  2004/09/14 18:33:13  gregg
// fix
//
// Revision 1.84  2004/09/14 18:19:12  gregg
// getProductCommitChunk(), store more PreparedStatements.
//
// Revision 1.83  2004/09/14 17:18:13  gregg
// some timings
//
// Revision 1.82  2004/09/13 17:02:57  gregg
// compile fix
//
// Revision 1.81  2004/09/13 16:56:45  gregg
// PreparedStatementCollection stuff
//
// Revision 1.80  2004/09/10 20:56:51  gregg
// save off EntityGroups
//
// Revision 1.79  2004/09/10 20:21:10  gregg
// null ptr fix
//
// Revision 1.78  2004/09/10 19:57:05  gregg
// updateProduct -- refresh incomplete PRODUCT logic
//
// Revision 1.77  2004/09/10 17:25:05  gregg
// blobExistsInODS
//
// Revision 1.76  2004/09/09 21:57:03  gregg
// remove dumpStack
//
// Revision 1.75  2004/09/09 21:14:56  gregg
// update for blobs
//
// Revision 1.74  2004/09/09 20:25:03  gregg
// debugging valfrom on blob attribute
//
// Revision 1.73  2004/09/09 20:09:32  gregg
// removeAllProductDetails()
//
// Revision 1.72  2004/09/09 19:59:08  gregg
// phickz
//
// Revision 1.71  2004/09/09 19:48:59  gregg
// delete
//
// Revision 1.70  2004/09/09 18:40:32  gregg
// more queueUnpublishAllDetails(
//
// Revision 1.69  2004/09/09 18:15:46  gregg
// fixx unpublishAllDetails(
//
// Revision 1.68  2004/09/09 18:06:35  gregg
// queueAllDetailForUnpublish
//
// Revision 1.67  2004/09/09 17:52:13  gregg
// fix for strPublishFlag for processOneEntity
//
// Revision 1.66  2004/09/09 17:20:02  gregg
// build root detail if isProcessOneEntity
//
// Revision 1.65  2004/09/09 17:04:24  gregg
// unpublishAllProductDetails() if isProcessOneEntity()
//
// Revision 1.64  2004/09/09 16:30:27  gregg
// fix
//
// Revision 1.63  2004/09/09 16:23:06  gregg
// VALID_FLAG_NO_VAL
//
// Revision 1.62  2004/09/09 15:34:05  gregg
// Date in logging
//
// Revision 1.61  2004/09/09 15:27:30  gregg
// some publish filtering for rules
//
// Revision 1.60  2004/09/09 05:40:06  dave
// static cling
//
// Revision 1.59  2004/09/09 05:31:32  dave
// commit test
//
// Revision 1.58  2004/09/09 05:30:20  dave
// testing out block commit
//
// Revision 1.57  2004/09/09 01:03:04  gregg
// commit sum change
//
// Revision 1.56  2004/09/09 00:02:24  gregg
// more update
//
// Revision 1.55  2004/09/08 23:54:22  gregg
// update logic fix
//
// Revision 1.54  2004/09/08 22:56:28  gregg
// skipprodcheck
//
// Revision 1.53  2004/09/08 22:34:03  gregg
// debug for row count
//
// Revision 1.52  2004/09/08 22:31:17  gregg
// more more skip blob
//
// Revision 1.51  2004/09/08 22:21:16  gregg
// more skip blob logic
//
// Revision 1.50  2004/09/08 22:18:53  gregg
// skip blob logic
//
// Revision 1.49  2004/09/08 21:38:01  gregg
// use da sliding window on updates
//
// Revision 1.48  2004/09/08 20:53:51  gregg
// pullBySessionID
//
// Revision 1.47  2004/09/08 18:45:30  gregg
// skip timetable update when processing one entity only
//
// Revision 1.46  2004/09/08 18:16:44  gregg
// fix eDoc parms in populateIncompleteRootProducts()
//
// Revision 1.45  2004/09/08 18:11:11  gregg
// minor change
//
// Revision 1.44  2004/09/08 18:00:37  gregg
// some work in main
//
// Revision 1.43  2004/09/08 17:23:38  gregg
// getNewSessionID
//
// Revision 1.42  2004/09/08 17:06:53  gregg
// db freestatements etc
//
// Revision 1.41  2004/09/08 16:46:55  gregg
// debug
//
// Revision 1.40  2004/09/08 16:45:18  gregg
// updateIncompleteRootProducts()
//
// Revision 1.39  2004/09/07 21:56:46  gregg
// rearranging
//
// Revision 1.38  2004/09/07 19:34:26  gregg
// audience flag logic
//
// Revision 1.37  2004/09/07 18:20:50  gregg
// isRootProductDetailAttribute
//
// Revision 1.36  2004/09/03 19:18:11  gregg
// more blob stuff
//
// Revision 1.35  2004/09/03 18:32:28  gregg
// fix
//
// Revision 1.34  2004/09/03 18:22:25  gregg
// blobs
//
// Revision 1.33  2004/09/03 08:43:28  dave
// sntax
//
// Revision 1.32  2004/09/03 08:35:33  dave
// bypassing root logging
//
// Revision 1.31  2004/09/02 21:55:31  gregg
// collectionID passing for Longs
//
// Revision 1.30  2004/09/02 18:58:38  gregg
// process one EntityID only option
//
// Revision 1.29  2004/09/02 18:48:18  gregg
// we must store EntityItems
//
// Revision 1.28  2004/09/02 18:37:23  gregg
// store EntityItems in hashtable for performance
//
// Revision 1.27  2004/09/01 22:26:24  gregg
// some long text fishing
//
// Revision 1.26  2004/09/01 19:04:19  gregg
// remove eProductUpdater param in eProduct constructor
//
// Revision 1.25  2004/09/01 18:26:00  gregg
// logging stuff
//
// Revision 1.24  2004/09/01 17:36:57  gregg
// putting back in longtext
//
// Revision 1.23  2004/09/01 16:31:56  gregg
// skip puts of blobs for now
//
// Revision 1.22  2004/09/01 16:13:24  gregg
// fix
//
// Revision 1.21  2004/09/01 16:05:51  gregg
// changing blob/longtext tables
//
// Revision 1.20  2004/09/01 15:41:58  gregg
// more PRODUCT columns!
//
// Revision 1.19  2004/08/31 23:26:29  gregg
// new columns added
//
// Revision 1.18  2004/08/31 21:40:51  gregg
// more blob stuff
//
// Revision 1.17  2004/08/30 19:33:25  gregg
// inbound/outbound rule implementation
//
// Revision 1.16  2004/08/30 18:11:40  gregg
// fix
//
// Revision 1.15  2004/08/30 18:03:34  gregg
// setting up for outbound/inbound rules processing
//
// Revision 1.14  2004/08/26 22:06:46  gregg
// minor cleanup
//
// Revision 1.13  2004/08/25 21:09:58  gregg
// remember childid == 0 means derived
//
// Revision 1.12  2004/08/25 20:55:19  gregg
// isPublished() method
//
// Revision 1.11  2004/08/25 20:32:32  gregg
// some update vs. unpublish logic
//
// Revision 1.10  2004/08/24 23:00:58  gregg
// loop fixing
//
// Revision 1.9  2004/08/24 22:53:28  gregg
// put back in priorRootID logic into rdrs loop
//
// Revision 1.8  2004/08/24 22:02:22  gregg
// debug statement
//
// Revision 1.7  2004/08/24 21:38:48  gregg
// rdrs loop fix + better error throwing
//
// Revision 1.6  2004/08/24 21:28:13  gregg
// some more NLS processing
//
// Revision 1.5  2004/08/24 18:12:25  gregg
// set Profile's NLSID
//
// Revision 1.4  2004/08/23 23:39:29  gregg
// getVersion() method
//
// Revision 1.3  2004/08/23 20:58:06  gregg
// some rearranging to decouple eProductUpdater/eProduct objects a bit
//
// Revision 1.2  2004/08/23 20:34:37  gregg
// change signature of eProduct constructor -- pass in Database object
//
// Revision 1.1  2004/08/23 16:42:20  gregg
// load to middleware module
//
//

package COM.ibm.eannounce.hula;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.sql.*;
import java.io.*;
import java.text.*;


/**
* take an eDoc object and convert it to a form which can be inserted directly into ods as products.
*/
public final class eProductUpdater {

  static final long serialVersionUID = 20011106L;

  private static final int SESSION_ID_SPECIAL = -99;

  private static final SimpleDateFormat c_sdfTimestamp = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");

  private static final int CHUNKY = eProductProperties.getProductCommitChunk();
  private static final String m_strODSSchema = eProductProperties.getDatabaseSchema();
  private static int c_iChunk = 0;

  //private static final int PS_CLOSE_AT = 1000;
  //private int m_iPsCount = 0;



  // logs
  private static PrintStream c_psLogUpdate = null;
  private static PrintStream c_psErr = null;
  //private static PrintStream c_psDebug = null;
  // command-line parms
  private String m_strVEName = null;
  private String m_strEnterprise = null;
  private String m_strRootType = null;
  private int m_iOPWGID = 1;
  private boolean m_bUpdateTimetable = true;
  private int m_iRootID = -1;
  // times
  private String m_strNow = null;
  private String m_strLastRun = null;
  private String m_strForever = null;
  private String m_strEpoch = null;
  // connection-related members
  private Database m_dbPDH = null;
  private Connection m_conODS = null;
  //
  private Profile m_profStartDate = null;
  private Profile m_profEndDate = null; // represents ~current~ Profile

  //
  private boolean m_bPullBySessionID = false;
  private boolean m_bSkipBlob = false;
  private boolean m_bSkipProductCheck = false;

  // save these for performance....
  private ExtractActionItem m_ai = null;
  //
  private PreparedStatementCollection m_psc = null;
  //




  public static void main(String _argv[]) {
    eProductUpdater epu = null;
    try {
      Stopwatch sw = new Stopwatch();
      sw.start();
      //
      Database db = null;
      try {
        db = new Database();
        db.connect();
      } catch(Exception exc) {
        System.err.println("ERROR getting Database connection..." + exc.toString());
        System.exit(-1);
      }

      epu = new eProductUpdater(db,_argv);
      epu.updateODS();

      if(epu.isUpdateTimetable()) {
        epu.setTimestampInTimetable();
      }

      epu.getPreparedStatementCollection().closeAll();

      debug("\n\n***Entire program took:" + sw.finish());
    } catch (Exception x) {
      err(x.toString());
      x.printStackTrace();
    } finally {
      if (epu != null) {
        epu.closeAllPS();
      }
    }
  }


  private final void closeAllPS() {
    try {
      getPreparedStatementCollection().closeAll();
    } catch(Exception x) {
      err("ERROR in closeAllPS():" + x.toString());
    }
  }

  /**
  * Constructor
  */
  public eProductUpdater(Database _db, String _argv[]) throws SQLException, MiddlewareException {

    m_dbPDH = _db;
    m_psc = new PreparedStatementCollection();

    try {

      // initialize vars
      setParms(_argv);

      // setup logs
      c_psLogUpdate = new PrintStream(new FileOutputStream("./log/" + m_strVEName + ".log"));
      c_psErr = new PrintStream(new FileOutputStream("./log/" + m_strVEName + ".err"));
      //c_psDebug = new PrintStream(new FileOutputStream("./log/" + m_strVEName + ".debug"));

      //setPDHConnection();
      setODSConnection();
      setDateTimeVars();

      if(eProductProperties.redirectOut()) {
        D.ebugSetOut(m_strVEName + ".out");
      }

    } catch(Exception x) {
      m_dbPDH.debug(D.EBUG_ERR,"Main Error:" + x.toString());
      err("Main Error:" + x.toString());
      x.printStackTrace(c_psErr);
      x.printStackTrace();
      System.exit(-1);
    }
  } // end Constructor

  public final void updateODS() throws Exception {
    // generate our eDoc
    m_ai = new ExtractActionItem(null,m_dbPDH, getProfile(), getVEName());

    eDoc ed = null;
    if(isPullBySessionID()) {
      // SESSION ID
      ed = new eDoc(m_dbPDH,getProfile(),SESSION_ID_SPECIAL,m_ai,getRootType(),getNow());
    } else if(isProcessOneEntity()) {
      // ONE ROOT ENTITY ONLY
      ed = new eDoc(m_dbPDH, getProfile(), m_ai, getRootType(), getRootID(), getNow());
    } else {
      // NET CHANGE FLAVOR
      ed = new eDoc(m_dbPDH, getProfile(), m_ai, getRootType(), m_strLastRun, getNow());
    }
    eDocAdapter eda = new eDocAdapter(ed,this);
    if (eda.hasProducts()) {
        while(eda.hasMoreProducts()) {
            eProduct[] aProd = eda.getNextProduct(m_dbPDH);
            for(int i = 0; i < aProd.length; i++) {
                updateProduct(aProd[i]);
            }
        }
        m_conODS.commit();
    }
  }



  /**
  * update a product!
  */
  private final void updateProduct(eProduct _prod) throws Exception {

    try {
//      int iChildUpdateCount = _prod.getProductDetailCount();

      try {
        String strRootType = _prod.getStringVal(eProduct.ENTITYTYPE);
        int iRootID = _prod.getIntVal(eProduct.ENTITYID);

        D.ebug(D.EBUG_SPEW,"TRACK updating eProduct:" + strRootType + ":" + iRootID);

        // if one entity mode --> wipe everything out first...
        if(isProcessOneEntity()) {
            // first lets delete the old copy in ODS because we're puttin in a fresh image
            _prod.delete(m_dbPDH.getODSConnection());
            _prod.setInsert(true);
            _prod.deleteProductDetails(m_dbPDH);
        }
        _prod.update(m_dbPDH,false);
        c_iChunk++;
      } catch(Exception excUpdate1) {
          err("ERROR on PRODUCT: " + _prod.getKey() + " bailing out.. find a solution...");
          throw excUpdate1;
      }

      if (CHUNKY == c_iChunk) {
        m_conODS.commit();
        c_iChunk=0;
      }

    } catch(Exception exc) {
      err("while updating Product " + _prod.dump(true));
      exc.printStackTrace(c_psErr);
      throw exc;
    }
  }



  ////////////////////////////////////
  // End eProduct Acessors/Mutators //
  ////////////////////////////////////

  public final String getNow() {
    return m_strNow;
  }

  /**
  *  Sets the ODSConnection attribute
  */
  private final void setODSConnection() {
    try {
      //D.ebug(D.EBUG_DETAIL, "Connecting to ODS");
      m_conODS = m_dbPDH.getODSConnection();
      m_conODS.setAutoCommit(false);
    } catch (Exception ex) {
      err("odsConnectionError:" + ex.getMessage());
      System.exit(-1);
    }
  }

  public final Profile getProfile() {
    return m_profEndDate;
  }

  /**
  *  Sets the profile,now and forever  attributes of the ODSMethods object
  */
  private final void setDateTimeVars() throws SQLException, MiddlewareException {
    DatePackage dpNow = new DatePackage(m_dbPDH);
    m_strNow = dpNow.getNow();
    m_strForever = dpNow.getForever();
    m_strLastRun = getLastRuntimeFromTimetable();
    // setup profiles
    m_profEndDate = new Profile(m_dbPDH, m_strEnterprise, m_iOPWGID);
    m_profEndDate.setValOn(getNow());
    m_profEndDate.setEffOn(getNow());

    trace(m_profEndDate.dump(false));

    m_profStartDate = new Profile(m_dbPDH, m_strEnterprise, m_iOPWGID);
    m_profStartDate.setValOn(m_strLastRun);
    m_profStartDate.setEffOn(m_strLastRun);
    trace("getNow() = " + getNow());
    trace("m_strLastRun = " + m_strLastRun);
    trace("m_strForever = " + m_strForever);
    trace("m_strEpoch = " + m_strEpoch);
  }

  private final String getLastRuntimeFromTimetable() {
    String strStatement = "SELECT ENDDATE FROM " + m_strODSSchema + ".NETTERTIMETABLE " +
      " WHERE  ENTERPRISE = '" + m_strEnterprise + "' AND VENAME = '" + m_strVEName + "' " +
      " ORDER BY ENDDATE DESC";
    String strAnswer = null;
    try {
      Statement stmt = m_conODS.createStatement();
      ResultSet rs = stmt.executeQuery(strStatement);
      ReturnDataResultSet rdrs = new ReturnDataResultSet(rs);
      if(rdrs.getRowCount() > 0) {
        strAnswer = rdrs.getColumnDate(0,0);
      } else {
        m_dbPDH.debug(D.EBUG_ERR, "getLastRuntimeFromTimetable:ERROR - No Runtimes Found!");
        System.exit(-1);
      }
      rs.close();
      stmt.close();
      stmt = null;
      rs = null;
    } catch (SQLException se) {
      m_dbPDH.debug(D.EBUG_ERR, "getLastRuntimeFromTimetable:ERROR:" + se.getMessage());
      System.exit(-1);
    }
    return strAnswer;
  }

  private final void setTimestampInTimetable() {
    String strInsertTimestampStmt = "INSERT INTO " + m_strODSSchema + ".NETTERTIMETABLE " +
      "(ENTERPRISE,VENAME,RUNTYPE,STARTDATE,ENDDATE) " +
      " VALUES(" +
      "'" + m_strEnterprise + "'," +
      "'" + m_strVEName + "'," +
      "'I'," +
      "'" + m_strLastRun + "'," +
      "'" + getNow() + "')";
    try {
      Statement stmtInsertTimeStamp = m_conODS.createStatement();
      stmtInsertTimeStamp.execute(strInsertTimestampStmt);
      stmtInsertTimeStamp.close();
      m_conODS.commit();
    } catch (SQLException e) {
      m_dbPDH.debug(D.EBUG_ERR, "setTimestampInTimetable:ERROR:Insert into timetable:" + e.getMessage());
      System.exit(-1);
    }
  }

  /**
  * set our runtime parms
  */
  private final void setParms(String _astr[]) {

    m_strEnterprise = eProductProperties.getEnterprise();
    m_iOPWGID       = eProductProperties.getOPWGID();

    for (int ii = 0; ii < _astr.length;ii++) {
      trace(ii +":" + _astr[ii]);
      if (_astr[ii].equals("-v")) {
        m_strVEName = _astr[ii+1];
        trace("PARMCHECK: The VE Name is... " + m_strVEName);
      }
      //if (_astr[ii].equals("-x")) {
      //    m_strEnterprise = _astr[ii+1];
      //    trace("PARMCHECK: The Enterprise is... " + m_strEnterprise);
      //}
      //if (_astr[ii].equals("-i")) {
      //    m_iOPWGID = Integer.parseInt(_astr[ii+1]);
      //    trace("PARMCHECK: The OPWGID is... " + m_iOPWGID);
      //}
      if (_astr[ii].equals("-t")) {
        m_bUpdateTimetable = false;
        trace("PARMCHECK: -t passed, skipping setTimestampInTimetable");
      }
      if (_astr[ii].equals("-e")) {
        m_iRootID = Integer.parseInt(_astr[ii+1]);
        m_bUpdateTimetable = false; // DONT update tIMETABLE!!!!
        trace("PARMCHECK: **PROCESS ONE ENTITY ONLY!**: The ROOT ID to process is... " + m_iRootID);
      }
      if (_astr[ii].equals("-s")) {
        m_bPullBySessionID = true;
        m_bUpdateTimetable = false;
        trace("PARMCHECK: -s passed, pulling by session ID");
      }
      if (_astr[ii].equals("-skipblob")) {
        m_bSkipBlob = true;
        trace("PARMCHECK: -skipblob passed, skipping updates of blobs");
      }
      if (_astr[ii].equals("-skipprodcheck")) {
        m_bSkipProductCheck = true;
        trace("PARMCHECK: -skipprodcheck passed, skipping updates of PRODUCTS w/ -3 PROJECTID");
      }
    }
    debug("isSkipProductCheck():" + isSkipProductCheck());
    debug("isProcessOneEntity():" + isProcessOneEntity());
    debug("isUpdateTimetable():" + isUpdateTimetable());
    debug("isSkipBlob():" + isSkipBlob());
    debug("isPullBySessionID():" + isPullBySessionID());
    m_strRootType = eProductProperties.getRootType(getVEName());
  }

  ///////////////
  // Accessors //
  ///////////////
  protected final boolean isProcessOneEntity() {
    return (m_iRootID > 0);
  }
  protected final String getVEName() {
    return m_strVEName;
  }
  protected final String getRootType() {
    return m_strRootType;
  }
  protected final int getRootID() {
    return m_iRootID;
  }
  protected final boolean isUpdateTimetable() {
    return m_bUpdateTimetable;
  }
  protected final boolean isPullBySessionID() {
    return m_bPullBySessionID;
  }
  protected final boolean isSkipBlob() {
    return m_bSkipBlob;
  }
  protected final boolean isSkipProductCheck() {
    return (m_bSkipProductCheck || eProductProperties.isSkipProductCheck(getVEName()) || isPullBySessionID() || isProcessOneEntity());
  }

  protected final String getLastRuntime() {
      return m_strLastRun;
  }

  ///////////////////
  // Print methods //
  ///////////////////

  public static final void err(String _s) {
    //if(c_sdfTimestamp == null) {
    //  c_sdfTimestamp = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
    //  System.err.println("NULL DAGNABBIT!!!!!!!!");
    //}
    String strDate = c_sdfTimestamp.format(new java.util.Date());
    System.err.println(strDate + ":" + _s);
    if(c_psErr != null) {
      c_psErr.println(strDate + ":" + _s);
      return;
    }
  }

  public static final void trace(String _s) {
    String strDate = c_sdfTimestamp.format(new java.util.Date());
    System.out.println(strDate + ":" + _s);
  }

  public static final void logUpdate(String _s) {
    String strDate = c_sdfTimestamp.format(new java.util.Date());
    if(c_psLogUpdate == null) {
      System.out.println(strDate + ":" + _s);
      return;
    }
    c_psLogUpdate.println(strDate + ":" + _s);
  }

  public static final void debug(String _s) {
    if(!eProductProperties.debug()) {
      return;
    }
   // String strDate = c_sdfTimestamp.format(new java.util.Date());
    //if(c_psDebug == null) {
    //  System.out.println(strDate + ":" + _s);
    //  return;
    //}
    D.ebug(D.EBUG_SPEW,_s);
  }

  public final PreparedStatementCollection getPreparedStatementCollection() {
    return m_psc;
  }

  /*
  * Version info
  */
  public String getVersion() {
    return new String("$Id: eProductUpdater.java,v 1.166 2008/01/31 21:05:17 wendy Exp $");
  }

}
