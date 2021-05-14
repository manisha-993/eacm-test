package COM.ibm.eannounce.catalog.ods;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */


import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.MetaEntityList;
import COM.ibm.eannounce.catalog.Catalog;

/**
 *  The main program for the CatODSNet class. This moves the changed data from the PDH to the ODS
 *
 *@author Bala
 *@created    February 20, 2003
 */
public class CatODSNet
    extends CatODSMethods {

  private boolean m_bUpdateSoftware = false;

  { // Class initialization code

    D.isplay("CatODSNet:" + getVersion());
  }

  /**
   * main
   *
   * @param arg
   *  @author David Bigelow
   */
  public static void main(String[] arg) {

    D.ebug(MiddlewareServerProperties.getTrace());
    D.ebug(D.EBUG_INFO, "CatODSNet... tracing enabled");
    D.ebug(D.EBUG_INFO,
           "CatODSNet... debug trace level " + MiddlewareServerProperties.getDebugTraceLevel());
    D.ebugLevel(MiddlewareServerProperties.getDebugTraceLevel());

    CatODSNet onet = new CatODSNet(arg);

  }

  /*
   * The action starts here
   */
  /**
   *  Constructor for the CatODSNet object
   *
   *@param  _arg  Description of the Parameter
   */
  public CatODSNet(String[] _arg) {

    // OK. here we go.
    try {

      MetaEntityList mel = null;

      Catalog cat = new Catalog();

      if (CatODSMethods.canCatalogRun(cat)) {
        CatODSMethods.setIFMLock(cat);

        setCatalog(new Catalog());
        setDatabaseObject(getCatalog().getPDHDatabase());
        setProfile();
        m_prof = getProfile();
        setConnection();

        setSwitches(_arg);

        setDateTimeVars();

        calculateLastRuntime();
        D.ebug(D.EBUG_INFO, "===========================");
        D.ebug(D.EBUG_INFO, "Profile:" + m_prof.dump(false));
        D.ebug(D.EBUG_INFO, "===========================");

        generateGeoMap();

        mel = new MetaEntityList(m_dbPDH, m_prof, false);

        D.ebug("CatODSNet.Entities first ...");

        for (int ii = 0; ii < mel.getEntityGroupCount(); ii++) {
          EntityGroup egShell = mel.getEntityGroup(ii);
          if (!egShell.isRelator()) {
            if (CatODSServerProperties.includeTable(m_strODSSchema, egShell.getEntityType())) {
              EntityGroup eg = new EntityGroup(null, m_dbPDH, m_prof, egShell.getEntityType(),
                                               "Extract");
              mel.putEntityGroup(eg);
              checkODSTable(eg);
              updateODSTable(eg);
            }
            else {
              D.ebug("CatODSNet.Excluding table (" + egShell.getEntityType() +
                     ").  Not in Include List");
            }
          }
        }

        D.ebug("CatODSNet.Relators second ...");

        for (int ii = 0; ii < mel.getEntityGroupCount(); ii++) {
          EntityGroup egShell = mel.getEntityGroup(ii);
          if (egShell.isRelator()) {
            if (CatODSServerProperties.includeTable(m_strODSSchema, egShell.getEntityType())) {
              EntityGroup eg = new EntityGroup(null, m_dbPDH, m_prof, egShell.getEntityType(),
                                               "Extract");
              mel.putEntityGroup(eg);
              checkODSTable(eg);
              updateODSTable(eg);
            }
            else {
              D.ebug("CatODSNet.Excluding table (" + egShell.getEntityType() +
                     ").  Not in Include List");
            }
          }
        }

/*
        D.ebug("CatODSNet.Blobs Third ...");
        updateBlobTable();
*/
        D.ebug("CatODSNet.DescriptionChanges Fifth...");
        updateDescriptionChanges(mel);

        // Move final data to '_F' tables.
        populateFinalData();

        setTimestampInTimetable();
        CatODSMethods.clearIFMLock(cat);
      }
      else {
        D.ebug("system is busy processing data.. cannot run at this time");
      }

    }
    catch (Exception x) {

      D.ebug(D.EBUG_ERR, "CatODSNet:main" + x);
      x.printStackTrace();
      //If test fails on any connection, then fail hard
      System.exit( -1);
    }

    D.ebug(D.EBUG_INFO, "CatODSNet Complete*******************");
  }

  /**
   *  Sets the switches attribute of the ODSInit object
   *
   *@param  _astr  The new switches value
   */
  private void setSwitches(String[] _astr) {

    for (int ii = 0; ii < _astr.length; ii++) {
      D.ebug(D.EBUG_INFO, ii + ":" + _astr[ii]);
    }

  }

  /**
   * getVersion
   *
   * @return
   *  @author David Bigelow
   */
  public final String getVersion() {
    return "$Id: CatODSNet.java,v 1.3 2011/05/05 11:22:00 wendy Exp $";
  }
}
/*
   $Log: CatODSNet.java,v $
   Revision 1.3  2011/05/05 11:22:00  wendy
   src from IBMCHINA

   Revision 1.3  2008/11/12 04:46:31  yang
   need to skip QueryGroup getViewActions

   Revision 1.2  2007/10/09 08:11:49  sulin
   no message

   Revision 1.1.1.1  2007/06/05 02:09:45  jingb
   no message

   Revision 1.1.1.1  2006/03/30 17:36:30  gregg
   Moving catalog module from middleware to
   its own module.

   Revision 1.5  2006/02/15 23:49:54  bala
   disable updateBlob method

   Revision 1.4  2006/01/17 22:46:10  bala
   Added/using canCatalogRun, setIFMLock,clearIFMlock to warn other apps so that they dont fall over each other

   Revision 1.3  2005/10/28 02:42:57  bala
   bring upto sync

   Revision 1.2  2005/09/26 22:46:53  bala
   Checkin

 */
