// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2003
// All Rights Reserved.
//
// $Log: ODSInit.java,v $
// Revision 1.41  2005/08/29 20:47:52  gregg
// no longer reset multiattr table on init
//
// Revision 1.40  2005/05/13 17:37:10  gregg
// more fixes
//
// Revision 1.39  2005/05/13 17:30:34  gregg
// fixes
//
// Revision 1.38  2005/05/13 17:00:45  gregg
// more prepping rollup logic for init
//
// Revision 1.37  2005/05/09 18:08:14  gregg
// build MULTIATTRIBUTE table
//
// Revision 1.36  2005/04/26 18:02:41  gregg
// rollup attributes
//
// Revision 1.35  2005/04/21 16:38:18  gregg
// instantiate object in constructor
//
// Revision 1.34  2005/01/31 18:37:32  dave
// Jtest  cleanup
//
// Revision 1.33  2004/01/05 01:54:29  dave
// added m_bResetMetaAttributes logic to
// loop through all the entity group and add any new
// attributes that are in the conrol file.. but not yet
// in the table
//
// Revision 1.32  2003/11/17 20:40:46  dave
// Typo rebuildBlob should have been rebuildBlobTable
//
// Revision 1.31  2003/11/17 20:22:53  dave
// rebuild blob flag enabled
//
// Revision 1.30  2003/11/17 18:02:17  dave
// adding the blob gen logic for dminit
//
// Revision 1.29  2003/10/06 20:33:42  dave
// adding the rebuildDeleteLog L
//
// Revision 1.28  2003/10/03 20:01:43  dave
// more dmnet changes
//
// Revision 1.27  2003/10/02 19:17:02  dave
// softwarre Table for ECCM
//
// Revision 1.26  2003/10/01 18:42:58  dave
// adding geomap logic
//
// Revision 1.25  2003/09/29 18:34:58  dave
// Fkey problem
//
// Revision 1.24  2003/09/29 05:11:09  dave
// trace
//
// Revision 1.23  2003/09/29 04:56:22  dave
// strTableName
//
// Revision 1.22  2003/09/29 04:45:47  dave
// found it
//
// Revision 1.21  2003/09/29 04:27:50  dave
// Edit vs Extract?
//
// Revision 1.20  2003/09/29 03:37:57  dave
// fixing ECCMODS nls on flag
//
// Revision 1.19  2003/09/22 00:38:13  dave
// found the missing relator table problem
//
// Revision 1.18  2003/09/22 00:33:10  dave
// more debug
//
// Revision 1.17  2003/09/21 23:58:21  dave
// more cleanup
//
// Revision 1.16  2003/09/21 22:39:14  dave
// syntax resetProdAttTables
//
// Revision 1.15  2003/09/21 21:14:27  dave
// implementing foriegn keys
//
// Revision 1.14  2003/09/21 20:06:22  dave
// NLS fixes
//
// Revision 1.13  2003/09/18 22:59:18  dave
// more odsII changes
//
// Revision 1.12  2003/09/16 17:00:21  dave
// fixing restart
//
// Revision 1.11  2003/09/15 23:43:45  dave
// adding the new PRODATTMAP
//
// Revision 1.10  2003/09/15 22:52:59  dave
// more restart logic
//
// Revision 1.9  2003/09/15 20:38:11  dave
// fix
//
// Revision 1.8  2003/09/15 20:28:17  joan
// fix compile
//
// Revision 1.7  2003/09/15 19:48:28  dave
// adding restart logic
//
// Revision 1.6  2003/09/14 22:53:51  dave
// syntax
//
// Revision 1.5  2003/09/14 22:38:15  dave
// syntax
//
// Revision 1.4  2003/09/14 22:20:35  dave
// converging on ODSInit
//
// Revision 1.3  2003/09/12 22:24:14  dave
// checking in some changes
//
// Revision 1.2  2003/04/14 17:21:25  bala
// cleanup
//
// Revision 1.1  2003/02/21 18:05:26  bala
// Initial Checkin
//
//


package COM.ibm.eannounce.ods;



import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.MetaEntityList;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;

import COM.ibm.opicmpdh.middleware.T;




/**
*  The main program for the ODSInit class. This creates the ODS from the information contained in
*  in the ods.server.properties file. This program takes command line arguments which are specified
* in the documentation for the main method.

 *@author     Bala
 *@created    February 20, 2003
 */
public class ODSInit extends ODSMethods {
    { // Class initialization code

        D.isplay("ODSInit:" + getVersion());
    }

    // Here is where we get things moving

    /**
     * This is where this guy gets launched
     * @param arg
     * @author Dave
     */
    public static void main(String[] arg) {

        D.ebug(MiddlewareServerProperties.getTrace());
        D.ebug(D.EBUG_INFO, "ODSInit... tracing enabled");
        D.ebug(D.EBUG_INFO, "ODSInit... debug trace level " + MiddlewareServerProperties.getDebugTraceLevel());
        D.ebugLevel(MiddlewareServerProperties.getDebugTraceLevel());

        // GAB 042105 added thisline back in so we actually do something
        ODSInit odsInit = new ODSInit(arg);

    }

    /*
    * The action starts here
    */
    /**
     *  Constructor for the ODSInit object
     *
     *@param  _arg  Description of the Parameter
     */
    public ODSInit(String[] _arg) {

        // OK. here we go.
        try {

            MetaEntityList mel = null;
            setProfile();
            m_prof = getProfile();

            setSwitches(_arg);

            setODSConnection();
            setPDHConnection();
            setDateTimeVars();

            D.ebug(D.EBUG_INFO, "===========================");
            D.ebug(D.EBUG_INFO, "Profile:" + m_prof.dump(false));
            D.ebug(D.EBUG_INFO, "===========================");

            initializeRestartTable();

            generateGeoMap();

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
            //if(ODSServerProperties.performRollupMultiAttributes(m_strODSSchema)) {
			//    resetMultiAttributeTable();
			//}

            mel = new MetaEntityList(m_dbPDH, m_prof);

            if (m_bResetMetaAttributes) {
                for (int ii = 0; ii < mel.getEntityGroupCount(); ii++) {
                    EntityGroup egShell = mel.getEntityGroup(ii);
                    EntityGroup eg = new EntityGroup(null, m_dbPDH, m_prof, egShell.getEntityType(), "Extract");
                    addMetaControlRecords(eg);
                }
            }

            // Was an init request made

            if (m_bInit) {
                if (m_bInitAll) {

                    initializeRestartTable();

                    // Here we must reset the multiFlagValue Table if we are in a clean start up mode.
                    // Lets throw in the
                    if (!m_bRestart) {
                        dropAllTables();
                        resetMultiFlagTable();
                        resetProdAttTables();
                        resetAttributeTable();
            			//if(ODSServerProperties.performRollupMultiAttributes(m_strODSSchema)) {
						//    resetMultiAttributeTable();
						//}

                    }

                    // Now lets rebuild all the entities  then the relators second

                    D.ebug("ODSInit.Entitites first...");

                    for (int ii = 0; ii < mel.getEntityGroupCount(); ii++) {
                        EntityGroup egShell = mel.getEntityGroup(ii);
                        EntityGroup eg = new EntityGroup(null, m_dbPDH, m_prof, egShell.getEntityType(), "Extract");
                        if (!eg.isRelator()) {
                            if (ODSServerProperties.includeTable(m_strODSSchema, eg.getEntityType()) && !isInRestartTable(eg.getEntityType())) {
                                resetODSTable(eg);
                                addMetaControlRecords(eg);
                                if (m_bRebuild) {
                                    populateODSTable(eg);
                                }

                                // Out of restart
                                m_bRestart = false;
                            } else {
                                D.ebug("ODSInit.Excluding table (" + eg.getEntityType() + ") from init. --> " + (ODSServerProperties.includeTable(m_strODSSchema, eg.getEntityType()) ? "In restart mode and this is complete" : "Not in Include List"));
                            }
                            if (!m_bRestart) {
                                addToRestartTable(eg.getEntityType());
                            }
                        }
                    }

                    D.ebug("ODSInit.Relators second ...");

                    for (int ii = 0; ii < mel.getEntityGroupCount(); ii++) {
                        EntityGroup egShell = mel.getEntityGroup(ii);
                        EntityGroup eg = new EntityGroup(null, m_dbPDH, m_prof, egShell.getEntityType(), "Extract");
                        if (eg.isRelator()) {
                            if (ODSServerProperties.includeTable(m_strODSSchema, eg.getEntityType()) && !isInRestartTable(eg.getEntityType())) {
                                resetODSTable(eg);
                                addMetaControlRecords(eg);
                                if (m_bRebuild) {
                                    populateODSTable(eg);
                                }

                                // Out of restart
                                m_bRestart = false;
                            } else {
                                D.ebug("ODSInit.Excluding table (" + eg.getEntityType() + ") from init. --> " + (ODSServerProperties.includeTable(m_strODSSchema, eg.getEntityType()) ? "In restart mode and this is complete" : "Not in Include List"));
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
                } else if (m_bInitOne) {
                    EntityGroup egShell = mel.getEntityGroup(m_strTableName);
                    EntityGroup eg = new EntityGroup(null, m_dbPDH, m_prof, egShell.getEntityType(), "Extract");
                    T.est(eg != null, "*** Error *** cannot find entitygroup for:" + m_strTableName);
                    if (eg != null) {
                        resetODSTable(eg);
                        if (m_bRebuild) {
                            populateODSTable(eg);
                        }
                    }
                }
            }

            if (m_bSoftwareFlag) {
                rebuildSoftware();
            }
            if (m_bRebuildDeleteLog) {
                rebuildDeleteLog();
            }

            if(ODSServerProperties.performRollupMultiAttributes(m_strODSSchema)) {
			    initMultiAttributes();
			}

        } catch (Exception x) {

            D.ebug(D.EBUG_ERR, "ODSInit:" + x);
            x.printStackTrace();
            //If test fails on any connection, then fail hard
            System.exit(-1);
        }

        D.ebug(D.EBUG_INFO, "ODSInit Complete*******************");
    }

    /**
     *  Return the versoin of this class
     *
     *@return    The version value
     */
    public final String getVersion() {
        return "$Id: ODSInit.java,v 1.41 2005/08/29 20:47:52 gregg Exp $";
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

        // first .. search for the rebuild flag
        for (int ii = 0; ii < _astr.length; ii++) {
            if (_astr[ii].equals("-a")) {
                m_bResetMetaAttributes = true;
                D.ebug(D.EBUG_INFO, "PARMCHECK, Reset Meta Attributes ENABLED");
                break;
            }
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
                } else {
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
