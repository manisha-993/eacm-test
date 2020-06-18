// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2003
// All Rights Reserved.
//
// $Log: ODSInitMultiAttributes.java,v $
// Revision 1.2  2005/09/07 20:06:41  gregg
// remove dead multiatt columns/disable resetMultiAttributeTable
//
// Revision 1.1  2005/08/03 21:18:34  gregg
// initial load
//

package COM.ibm.eannounce.ods;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.MetaEntityList;

/**
 *  The main program for the MultiAttribute class. This moves the changed data from the PDH to the ODS
 *
 *@author Bala
 *@created    February 20, 2003
 */
public class ODSInitMultiAttributes extends ODSMethods {


    { // Class initialization code

        D.isplay("ODSInitMultiAttributes:" + getVersion());
    }

    /**
     * main
     *
     * @param arg
     *  @author David Bigelow
     */
    public static void main(String[] arg) {

        D.ebug(MiddlewareServerProperties.getTrace());
        D.ebug(D.EBUG_INFO, "ODSInitMultiAttributes... tracing enabled");
        D.ebug(D.EBUG_INFO, "ODSInitMultiAttributes... debug trace level " + MiddlewareServerProperties.getDebugTraceLevel());
        D.ebugLevel(MiddlewareServerProperties.getDebugTraceLevel());

        ODSInitMultiAttributes oima = new ODSInitMultiAttributes(arg);

    }

    /*
    * The action starts here
    */
    /**
     *  Constructor for the ODSInitMultiAttributes object
     *
     *@param  _arg  Description of the Parameter
     */
    public ODSInitMultiAttributes(String[] _arg) {

        // OK. here we go.
        try {

            setProfile();
            m_prof = getProfile();

            setODSConnection();
            setPDHConnection();

            setDateTimeVars();

            if (!canDMNetRun()) {
                D.ebug(D.EBUG_INFO, "===========================");
                D.ebug(D.EBUG_INFO, " Cannot Run DM INIT MULTIATTRIBUTES,  Process 8,11, or 19 still processing");
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

			//
		    if(ODSServerProperties.performRollupMultiAttributes(m_strODSSchema)) {
			    //resetMultiAttributeTable();
				initMultiAttributes();
			}
			//

            clearIFMLock();

        } catch (Exception x) {

            D.ebug(D.EBUG_ERR, "ODSInitMultiAttributes:main" + x);
            x.printStackTrace();
            //If test fails on any connection, then fail hard
            System.exit(-1);
        }

        D.ebug(D.EBUG_INFO, "ODSInitMultiAttribut[es Complete*******************");
    }

    /**
     * getVersion
     *
     * @return
     *  @author David Bigelow
     */
    public final String getVersion() {
        return "$Id: ODSInitMultiAttributes.java,v 1.2 2005/09/07 20:06:41 gregg Exp $";
    }
}
