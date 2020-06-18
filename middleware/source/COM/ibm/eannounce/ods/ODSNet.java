// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2003
// All Rights Reserved.
//
// $Log: ODSNet.java,v $
// Revision 1.57  2005/12/02 21:15:43  gregg
// ok, putting ALL code back in
//
// Revision 1.56  2005/12/02 21:14:50  gregg
// putting code back in
//
// Revision 1.55  2005/12/02 20:50:25  gregg
// debugging
//
// Revision 1.54  2005/12/02 20:41:29  gregg
// temporarily disabling all but software
//
// Revision 1.53  2005/09/10 16:39:16  dave
// parm change
//
// Revision 1.52  2005/09/10 16:11:15  dave
// comment out
//
// Revision 1.50  2005/08/11 21:57:14  gregg
// trace stmts
//
// Revision 1.49  2005/07/29 17:00:23  gregg
// put back in resetMultiAttrTable
//
// Revision 1.48  2005/07/27 20:42:02  gregg
// SQL change for multiattributes + temporarily disable someof init.
//
// Revision 1.47  2005/05/13 17:37:10  gregg
// more fixes
//
// Revision 1.46  2005/05/13 17:30:34  gregg
// fixes
//
// Revision 1.45  2005/05/13 17:00:44  gregg
// more prepping rollup logic for init
//
// Revision 1.44  2005/04/26 18:02:41  gregg
// rollup attributes
//
// Revision 1.43  2005/02/16 17:52:48  dave
// fixing ODSNet
//
// Revision 1.42  2005/02/08 21:57:32  dave
// missing Jtest chintax
//
// Revision 1.41  2005/02/08 21:51:45  dave
// Jtest clean up
//
// Revision 1.40  2004/09/11 19:57:44  dave
// uncommenting for full run
//
// Revision 1.39  2004/09/11 19:41:44  dave
// temp comment to focus on software
//
// Revision 1.38  2004/08/21 16:06:35  dave
// put back
//
// Revision 1.37  2004/08/21 15:13:26  dave
// more surgery
//
// Revision 1.36  2004/08/21 07:41:39  dave
// minor change
//
// Revision 1.35  2004/08/21 05:01:42  dave
// putting bad
//
// Revision 1.34  2004/08/21 04:13:55  dave
// change
//
// Revision 1.33  2004/08/21 03:22:11  dave
// special ODS run
//
// Revision 1.32  2004/08/09 17:57:41  dave
// fixing ODSNET to run all vs software update only
//
// Revision 1.31  2004/08/09 14:47:14  dave
// syntax and software only (again) for ECCM
//
// Revision 1.30  2004/08/06 22:28:15  dave
// put back total package run
//
// Revision 1.29  2004/08/06 21:35:22  dave
// just for software only...
//
// Revision 1.28  2004/07/11 12:41:15  dave
// back to normal
//
// Revision 1.27  2004/07/11 12:02:29  dave
// now.. blob only
//
// Revision 1.26  2004/07/11 11:34:44  dave
// back to the original
//
// Revision 1.25  2004/07/11 11:30:04  dave
// back to blob software combo
//
// Revision 1.24  2004/07/11 11:09:56  dave
// now.. just for the software table
//
// Revision 1.23  2004/07/11 10:55:41  dave
// special run of dmnet just for blob
//
// Revision 1.22  2004/04/07 18:02:18  gregg
// sync w/ v12
//
// Revision 1.21  2004/03/23 22:35:27  gregg
// updateDecsriptionChanges (sync w/ v1.2)
//
// Revision 1.20  2004/02/24 21:52:49  gregg
// call setSwitches()
//
// Revision 1.19  2004/02/24 20:23:25  gregg
// updateSoftware switch
//
// Revision 1.18  2004/02/20 00:21:41  gregg
// updateSoftware()
//
// Revision 1.17  2004/02/14 07:38:24  dave
// DMNET fixes
//
// Revision 1.16  2003/12/30 07:57:20  dave
// cannot distinct on an blob field
//
// Revision 1.15  2003/12/30 07:54:14  dave
// backout change because it was never tested
//
// Revision 1.14  2003/12/29 22:44:16  dave
// adding PDH connection to DMNET
//
// Revision 1.13  2003/12/16 22:17:11  dave
// added blob to dmnet
//
// Revision 1.12  2003/12/08 18:57:54  dave
// syntax fixes
//
// Revision 1.11  2003/12/08 18:48:54  dave
// Attempting to add the DFFLockUnlock stuff
//
// Revision 1.10  2003/10/06 03:05:04  dave
// ODSNet II
//
// Revision 1.9  2003/10/03 22:19:00  dave
// more DMNet simplification
//
// Revision 1.8  2003/10/03 22:01:33  dave
// adding unique file name, adding odsNET timetable
//
// Revision 1.7  2003/10/03 21:39:48  dave
// more DMNet main fixes
//
// Revision 1.6  2003/10/03 20:45:35  dave
// fixed syntax
//
// Revision 1.5  2003/10/03 20:04:24  dave
// simplification of isRelator Check
//
// Revision 1.4  2003/10/03 17:18:51  dave
// Net Changes I
//
// Revision 1.3  2003/10/02 19:17:03  dave
// softwarre Table for ECCM
//
// Revision 1.2  2003/04/17 01:27:50  bala
// add the ods table check to ALTER it if its necessary
//
// Revision 1.1  2003/04/15 18:27:12  bala
// Initial Checkin
//
//
package COM.ibm.eannounce.ods;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.MetaEntityList;

/**
 *  The main program for the ODSNet class. This moves the changed data from the PDH to the ODS
 *
 *@author Bala
 *@created    February 20, 2003
 */
public class ODSNet extends ODSMethods {

    private boolean m_bUpdateSoftware = false;

    { // Class initialization code

        D.isplay("ODSNet:" + getVersion());
    }

    /**
     * main
     *
     * @param arg
     *  @author David Bigelow
     */
    public static void main(String[] arg) {

        D.ebug(MiddlewareServerProperties.getTrace());
        D.ebug(D.EBUG_INFO, "ODSNet... tracing enabled");
        D.ebug(D.EBUG_INFO, "ODSNet... debug trace level " + MiddlewareServerProperties.getDebugTraceLevel());
        D.ebugLevel(MiddlewareServerProperties.getDebugTraceLevel());

        ODSNet onet = new ODSNet(arg);

    }

    /*
    * The action starts here
    */
    /**
     *  Constructor for the ODSNet object
     *
     *@param  _arg  Description of the Parameter
     */
    public ODSNet(String[] _arg) {

        // OK. here we go.
        try {

            MetaEntityList mel = null;


            setProfile();
            m_prof = getProfile();

            setSwitches(_arg);
            setODSConnection();
            setPDHConnection();

            setDateTimeVars();

            if (!canDMNetRun()) {
                D.ebug(D.EBUG_INFO, "===========================");
                D.ebug(D.EBUG_INFO, " Cannot Run DMNET,  Process 8,11, or 19 still processing");
                D.ebug(D.EBUG_INFO, "===========================");
                System.exit(-1);
            }

            //
            // Its a go..we canrun..so..lets go

            calculateLastRuntime();
            setIFMLock();
            D.ebug(D.EBUG_INFO, "===========================");
            D.ebug(D.EBUG_INFO, "Profile:" + m_prof.dump(false));
            D.ebug(D.EBUG_INFO, "===========================");


            generateGeoMap();

            mel = new MetaEntityList(m_dbPDH, m_prof);



            D.ebug("ODSNet.Entities first ...");

            for (int ii = 0; ii < mel.getEntityGroupCount(); ii++) {
                EntityGroup egShell = mel.getEntityGroup(ii);
                if (!egShell.isRelator()) {
                    if (ODSServerProperties.includeTable(m_strODSSchema, egShell.getEntityType())) {
                        EntityGroup eg = new EntityGroup(null, m_dbPDH, m_prof, egShell.getEntityType(), "Extract");
                        mel.putEntityGroup(eg);
                        checkODSTable(eg);
                        updateODSTable(eg);
                    } else {
                        D.ebug("ODSNet.Excluding table (" + egShell.getEntityType() + ").  Not in Include List");
                    }
                }
            }

            D.ebug("ODSNet.Relators second ...");

            for (int ii = 0; ii < mel.getEntityGroupCount(); ii++) {
                EntityGroup egShell = mel.getEntityGroup(ii);
                if (egShell.isRelator()) {
                    if (ODSServerProperties.includeTable(m_strODSSchema, egShell.getEntityType())) {
                        EntityGroup eg = new EntityGroup(null, m_dbPDH, m_prof, egShell.getEntityType(), "Extract");
                        mel.putEntityGroup(eg);
                        checkODSTable(eg);
                        updateODSTable(eg);
                    } else {
                        D.ebug("ODSNet.Excluding table (" + egShell.getEntityType() + ").  Not in Include List");
                    }
                }
            }

            D.ebug("ODSNet.Blobs Third ...");
            updateBlobTable();


            if (m_bUpdateSoftware) {
                D.ebug("ODSNet.Software Fourth ...");
                updateSoftware();
            }


            D.ebug("ODSNet.DescriptionChanges Fifth...");
            updateDescriptionChanges(mel);

			//
			if(ODSServerProperties.performRollupMultiAttributes(m_strODSSchema) &&
			   ODSServerProperties.initMultiAttributeInNet(m_strODSSchema)) {
			    resetMultiAttributeTable();
				initMultiAttributes();
			}
			//

            if(ODSServerProperties.performRollupMultiAttributes(m_strODSSchema)) {
				D.ebug("ODSNet.performRollupMultiAttributes...");
				processNetMultiAttributes();
			} else {
				D.ebug("SKIPPING ODSNet.performRollupMultiAttributes...");
			}


            setTimestampInTimetable();
            clearIFMLock();

        } catch (Exception x) {

            D.ebug(D.EBUG_ERR, "ODSNet:main" + x);
            x.printStackTrace();
            //If test fails on any connection, then fail hard
            System.exit(-1);
        }

        D.ebug(D.EBUG_INFO, "ODSNet Complete*******************");
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

        // first .. search for the Update Software
        for (int ii = 0; ii < _astr.length; ii++) {
            if (_astr[ii].equals("-s")) {
                m_bUpdateSoftware = true;
                D.ebug(D.EBUG_INFO, "PARMCHECK, updateSoftware ENABLED");
                break;
            }
        }
    }

    /**
     * getVersion
     *
     * @return
     *  @author David Bigelow
     */
    public final String getVersion() {
        return "$Id: ODSNet.java,v 1.57 2005/12/02 21:15:43 gregg Exp $";
    }
}
