package COM.ibm.eannounce.catalog.ods;

import COM.ibm.eannounce.catalog.ods.CatODSServerProperties;
import COM.ibm.eannounce.catalog.ods.CatODSMethods;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * Licensed Materials -- Property of IBM

 * (c) Copyright International Business Machines Corporation, 2003
 *  All Rights Reserved.
 *
 * @author not attributable
 * @version 1.0
 */
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.MetaEntityList;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;

import COM.ibm.opicmpdh.middleware.T;
import COM.ibm.eannounce.catalog.*;

/**
 *  The main program for the CatODSInit class. This creates the ODS from the information contained in
 *  in the ods.server.properties file. This program takes command line arguments which are specified
 * in the documentation for the main method.

 *@author     Bala
 *@created    February 20, 2003
 */
public class CatODSInit
    extends CatODSMethods {
  { // Class initialization code

    D.isplay("CatODSInit:" + getVersion());
  }

  // Here is where we get things moving

  /**
   * This is where this guy gets launched
   * @param arg
   * @author Dave
   */
  public static void main(String[] arg) {

    D.ebug(MiddlewareServerProperties.getTrace());
    D.ebug(D.EBUG_INFO, "CatODSInit... tracing enabled");
    D.ebug(D.EBUG_INFO,
           "CatODSInit... debug trace level " + MiddlewareServerProperties.getDebugTraceLevel());
    D.ebugLevel(MiddlewareServerProperties.getDebugTraceLevel());

    // GAB 042105 added thisline back in so we actually do something
    CatODSInit catODSInit = new CatODSInit(arg);

  }

  /*
   * The action starts here
   */
  /**
   *  Constructor for the CatODSInit object
   *
   *@param  _arg  Description of the Parameter
   */
  public CatODSInit(String[] _arg) {

    // OK. here we go.
    try {

      MetaEntityList mel = null;
      Catalog cat = new Catalog();

      if (CatODSMethods.canCatalogRun(cat)) {
        CatODSMethods.setIFMLock(cat);

        setSwitches(_arg);

        setCatalog(cat);
        setDatabaseObject(getCatalog().getPDHDatabase());
        setProfile();
        m_prof = getProfile();
        setConnection();

        setDateTimeVars();

        D.ebug(D.EBUG_INFO, "===========================");
        D.ebug(D.EBUG_INFO, "CatDB ODS Profile:" + m_prof.dump(false));
        D.ebug(D.EBUG_INFO, "===========================");

        initializeRestartTable();

        // Only drop all if we call this out here ..
        if (m_bDropAll && !m_bRestart) {
          dropAllTables();
        }

        if (m_bMetaDesc) {
          rebuildMetaDescription();
        }

        if (m_bRebuildBlob) {
          rebuildBlobTable();
        }

        if (m_bMultiFlag && !m_bRestart) {
          resetMultiFlagTable();
        }
        //if(CatODSServerProperties.performRollupMultiAttributes(m_strODSSchema)) {
        //    resetMultiAttributeTable();
        //}

        mel = new MetaEntityList(m_dbPDH, m_prof);

        // Was an init request made

        if (m_bInit) {
          if (m_bInitAll) {

            initializeRestartTable();

            // Here we must reset the multiFlagValue Table if we are in a clean start up mode.
            // Lets throw in the
            if (!m_bRestart) {
              dropAllTables();
              resetMultiFlagTable();
              //if(CatODSServerProperties.performRollupMultiAttributes(m_strODSSchema)) {
              //    resetMultiAttributeTable();
              //}

            }

            // Now lets rebuild all the entities  then the relators second

            D.ebug("CatODSInit.Entitites first...");

            for (int ii = 0; ii < mel.getEntityGroupCount(); ii++) {
              EntityGroup egShell = mel.getEntityGroup(ii);
              EntityGroup eg = new EntityGroup(null, m_dbPDH, m_prof, egShell.getEntityType(),
                                               "Extract");
              D.ebug("include table for Entity:" + eg.getEntityType() + ": returns :" +
                     CatODSServerProperties.includeTable(m_strODSSchema, eg.getEntityType()));
              if (!eg.isRelator()) {
                if (CatODSServerProperties.includeTable(m_strODSSchema, eg.getEntityType()) &&
                    !isInRestartTable(eg.getEntityType())) {
                  resetODSTable(eg);
                  if (m_bRebuild) {
                    populateODSTable(eg);
                  }

                  // Out of restart
                  m_bRestart = false;
                }
                else {
                  D.ebug("CatODSInit.Excluding table (" + eg.getEntityType() + ") from init. --> " +
                         (CatODSServerProperties.includeTable(m_strODSSchema, eg.getEntityType()) ?
                          "In restart mode and this is complete" : "Not in Include List"));
                }
                if (!m_bRestart) {
                  addToRestartTable(eg.getEntityType());
                }
              }
            }

            D.ebug("CatODSInit.Relators second ...");

            for (int ii = 0; ii < mel.getEntityGroupCount(); ii++) {
              EntityGroup egShell = mel.getEntityGroup(ii);
              EntityGroup eg = new EntityGroup(null, m_dbPDH, m_prof, egShell.getEntityType(),
                                               "Extract");
              if (eg.isRelator()) {
                if (CatODSServerProperties.includeTable(m_strODSSchema, eg.getEntityType()) &&
                    !isInRestartTable(eg.getEntityType())) {
                  resetODSTable(eg);
                  if (m_bRebuild) {
                    populateODSTable(eg);
                  }

                  // Out of restart
                  m_bRestart = false;
                }
                else {
                  D.ebug("CatODSInit.Excluding table (" + eg.getEntityType() + ") from init. --> " +
                         (CatODSServerProperties.includeTable(m_strODSSchema, eg.getEntityType()) ?
                          "In restart mode and this is complete" : "Not in Include List"));
                }
                if (!m_bRestart) {
                  addToRestartTable(eg.getEntityType());
                }
              }
            }

            rebuildDeleteLog();
            initializeTimeTable();
            setTimestampInTimetable();
            clearRestartTable();
          }
          else if (m_bInitOne) {
            EntityGroup egShell = mel.getEntityGroup(m_strTableName);
            EntityGroup eg = new EntityGroup(null, m_dbPDH, m_prof, egShell.getEntityType(),
                                             "Extract");
            T.est(eg != null, "*** Error *** cannot find entitygroup for:" + m_strTableName);
            if (eg != null) {
              resetODSTable(eg);
              if (m_bRebuild) {
                populateODSTable(eg);
              }
            }
          }
        }

        if (m_bRebuildDeleteLog) {
          rebuildDeleteLog();
        }
        
        // Move approved data to '_F' tables.
        populateFinalData();
        
        CatODSMethods.clearIFMLock(cat);
      }
      else {
        D.ebug("system is busy processing data.. cannot run at this time");
      }

    }
    catch (Exception x) {

      D.ebug(D.EBUG_ERR, "CatODSInit:" + x);
      x.printStackTrace();
      //If test fails on any connection, then fail hard
      System.exit( -1);
    }

    D.ebug(D.EBUG_INFO, "CatODSInit Run End****************");
  }

  /**
   *  Return the versoin of this class
   *
   *@return    The version value
   */
  public final String getVersion() {
    return "$Id: CatODSInit.java,v 1.3 2011/05/05 11:22:00 wendy Exp $";
  }

  /**
   *  Sets the switches attribute of the CatODSInit object
   *
   *@param  _astr  The new switches value
   */
  private void setSwitches(String[] _astr) {

    for (int ii = 0; ii < _astr.length; ii++) {
      D.ebug(D.EBUG_INFO, ii + ":" + _astr[ii]);
    }

    // lets see if we have an attribute rebuild
    for (int ii = 0; ii < _astr.length; ii++) {
      if (_astr[ii].equals("-r")) {
        m_bRebuild = true;
        D.ebug(D.EBUG_INFO, "PARMCHECK, REBUILD ENABLED");
        break;
      }
    }

    // now .. search for the target flag
    for (int ii = 0; ii < _astr.length; ii++) {
      if (_astr[ii].equals("-i")) {
        m_bInit = true;
        D.ebug(D.EBUG_INFO, "PARMCHECK, INITIALIZATION ENABLED");
        if (_astr[ii + 1].toUpperCase().equals("ALL")) {
          m_bInitAll = true;
          D.ebug(D.EBUG_INFO, "PARMCHECK, INITIALIZING ALL TABLES");
        }
        else {
          m_strTableName = _astr[ii + 1].toUpperCase();
          m_bInitOne = true;
          D.ebug(D.EBUG_INFO, "PARMCHECK, INITIALIZING TABLE (" + m_strTableName + ")");
        }
        break;
      }
    }

    // now .. search for the flag table regen ...
    for (int ii = 0; ii < _astr.length; ii++) {
      if (_astr[ii].equals("-f")) {
        m_bMultiFlag = true;
        D.ebug(D.EBUG_INFO, "PARMCHECK, MULTI-FLAG REGEN ENABLED");
        break;
      }
    }

    // now .. search for the flag table regen ...
    for (int ii = 0; ii < _astr.length; ii++) {
      if (_astr[ii].equals("-s")) {
        m_bSoftwareFlag = true;
        D.ebug(D.EBUG_INFO, "PARMCHECK, SOFTWARE REGEN ENABLED");
        break;
      }
    }

    // now .. search for the Schema drop flag ...
    for (int ii = 0; ii < _astr.length; ii++) {
      if (_astr[ii].equals("-d")) {
        m_bDropAll = true;
        D.ebug(D.EBUG_INFO, "PARMCHECK, SCHEMA TABLE DROP ENABLED");
        break;
      }
    }

    // now .. search for the metadescription regen flag ...
    for (int ii = 0; ii < _astr.length; ii++) {
      if (_astr[ii].equals("-m")) {
        m_bMetaDesc = true;
        D.ebug(D.EBUG_INFO, "PARMCHECK, METADESCRIPTION REGEN ENABLED");
        break;
      }
    }

    // now .. search for the metadescription regen flag ...
    for (int ii = 0; ii < _astr.length; ii++) {
      if (_astr[ii].equals("-b")) {
        m_bRebuildBlob = true;
        D.ebug(D.EBUG_INFO, "PARMCHECK, BLOB REBUILD ENABLED");
        break;
      }
    }
  }

}
/*
 $Log: CatODSInit.java,v $
 Revision 1.3  2011/05/05 11:22:00  wendy
 src from IBMCHINA

 Revision 1.2  2007/10/09 08:11:49  sulin
 no message

 Revision 1.1.1.1  2007/06/05 02:09:38  jingb
 no message

 Revision 1.1.1.1  2006/03/30 17:36:29  gregg
 Moving catalog module from middleware to
 its own module.

 Revision 1.10  2006/01/17 22:46:10  bala
 Added/using canCatalogRun, setIFMLock,clearIFMlock to warn other apps so that they dont fall over each other

 Revision 1.9  2005/10/20 17:49:38  bala
 changes to get new custom profile for cat ods datamover

 Revision 1.8  2005/10/18 21:11:34  bala
 take out generateGeoMap

 Revision 1.7  2005/10/11 18:12:13  bala
 remove resetattributetable reference

 Revision 1.6  2005/10/07 16:10:31  bala
 added setConnection method

 Revision 1.5  2005/10/07 01:15:43  bala
 more nullpointer fixes

 Revision 1.4  2005/10/07 00:58:41  bala
 fix nullpointer

 Revision 1.3  2005/10/07 00:41:56  bala
 plug in catalog classes

 Revision 1.2  2005/09/26 22:38:52  bala
 Add debug comments

 Revision 1.1  2005/09/26 17:30:25  bala
 <No Comment Entered>

 */
