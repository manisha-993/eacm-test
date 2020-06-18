// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
//$Log: PostECs.java,v $
//Revision 1.19  2011/09/09 17:22:47  wendy
//use securelogin now
//
//Revision 1.18  2007/07/31 13:03:46  chris
//Rational Software Architect v7
//
//Revision 1.17  2004/05/11 18:12:49  gregg
//update
//
//Revision 1.16  2004/05/11 17:16:32  gregg
//massive readjusting yeehaw
//
//Revision 1.15  2004/05/10 21:11:45  gregg
//compile fix
//
//Revision 1.14  2004/05/10 20:53:35  gregg
//start time as member
//
//Revision 1.13  2004/03/15 22:36:46  gregg
//sum updates
//
//Revision 1.12  2003/09/02 20:34:05  gregg
//added main routine for testing
//
//Revision 1.11  2003/05/29 22:45:35  gregg
//update
//
//Revision 1.10  2003/05/29 22:40:49  gregg
//update
//
//Revision 1.9  2003/05/29 20:59:12  gregg
//update - RMI working again
//
//Revision 1.8  2003/05/29 19:53:58  gregg
//back to RMI implementation
//
//Revision 1.7  2003/05/27 18:08:33  gregg
//remove reference to test Level0EC class
//
//Revision 1.6  2003/05/27 17:37:55  gregg
//use URL object trick to get properties from jarfile
//
//Revision 1.5  2003/05/23 22:37:04  gregg
//use Properties instead of XProperties
//
//Revision 1.4  2003/05/20 22:30:09  gregg
//dbCurrent.logout()
//
//Revision 1.3  2003/05/20 21:35:14  gregg
//new constructor
//
//Revision 1.2  2003/05/20 21:14:56  gregg
//update
//
//Revision 1.1  2003/05/20 19:14:35  gregg
//initial load
//
//
//

package COM.ibm.eannounce.pmsync;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ProfileSet;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.transactions.Cipher;


public class PostECs {

    private Profile m_prof = null;

    private static Properties c_props_rmi  = null;
    private static Properties c_props_meta = null;
    // one is for entitytype etc....
    private static final String RMI_PROPS_FILE_NAME = "PMSyncFeed.rmi.login.properties";
    private static final String META_PROPS_FILE_NAME = "PMSyncFeed.meta.properties";
    private RMIConnect m_rmiConnect = null;
    //
    private String m_strCurrTime = null;


/**
 * Use this one if possible
 */
	public PostECs(String _strCurrTime) {
		c_props_rmi = getRMILoginProperties();
        c_props_meta = getMetaProperties();
        m_strCurrTime = _strCurrTime;
	}

/**
 * Dummy Constructor to instantiate for purpose of retrieving path to properties w/in jarfile
 */
    //private PostECs(String _strDummyPurpose) {
    //    return;
    //}

/**
 * For test of this class + login/properties verification
 */
    public static void main(String[] _args) throws Exception, Throwable {
        boolean bGoForIt = false;
        if(_args != null && _args.length > 0) {
            //bGoForIt = (_args[0].equals("-p"));
            bGoForIt = true;
        }

        PostECs test = new PostECs("2001-04-16 00:00:00.000000");
        try {
            test.m_rmiConnect = new RMIConnect();
            RemoteDatabaseInterface rdi = test.m_rmiConnect.connect();
            if(bGoForIt) {
                Level0EC ec = new Level0EC(_args[0]);
                test.postAndLinkECsAndChildren(ec);
            } else {
                test.login(rdi);
            }
        } catch(Exception exc) {
            throw exc;
        }
    }

/**
 * Post the Level0EC to the eannounce PDH.
 */
    public void postAndLinkECsAndChildren(Level0EC _ec) throws Throwable {
        RemoteDatabaseInterface rdi = null;
        // 1) login
        try {
            m_rmiConnect = new RMIConnect();
            rdi = m_rmiConnect.connect();
            login(rdi);
        } catch(Exception exc) {
            throw exc;
        }

        // 2) do the posting thing....
        try {
           PMSyncEC ec = new PMSyncEC(rdi,m_prof,_ec);
           ec.feedToPdh(rdi);
        } catch(Exception exc) {
           throw exc;
        }

        // 3) free resources
        try {
            finalize();
        } catch(Throwable t) {
            throw t;
        }
    }



    private void login(RemoteDatabaseInterface _rdi) throws Exception {
        System.out.println(getRMILoginProperties().getProperty("user"));
       // ProfileSet profileSet = _rdi.login(getRMILoginProperties().getProperty("user"),
       // 		getRMILoginProperties().getProperty("password"),
        //		getRMILoginProperties().getProperty("versionLiteral"));
    	byte[][] encrypted = Cipher.encryptUidPw(getRMILoginProperties().getProperty("user"),
    			getRMILoginProperties().getProperty("password"));
    	// secure login
    	ProfileSet profileSet = _rdi.secureLogin(encrypted,getRMILoginProperties().getProperty("versionLiteral"));
        if(profileSet == null) {
            throw new Exception("couldn't get ProfileSet...");
        } else {
            m_prof = profileSet.elementAt(Integer.parseInt(getRMILoginProperties().getProperty("profileIndex")));
            log("profile is:\n" + m_prof.dump(false));
            //updateValEffOn(_rdi,m_prof);
            m_prof.setValOn(m_strCurrTime);
            m_prof.setEffOn(m_strCurrTime);
        }
	}

/**
* for system cleanup
*/
    protected void finalize() throws Throwable {
        try {
           m_rmiConnect.disconnect();
        } finally {
            super.finalize();
        }
    }

    //private Profile getProfile() {
     //   return m_prof;
    //}

/**
 * Set profilee's current valon/effon times.
 */
    //protected static void updateValEffOn(RemoteDatabaseInterface _rdi, Profile _prof) throws RemoteException, MiddlewareException, SQLException {
	//	String strNow = _rdi.remoteGBL2028().getColumn(0,0,0);
	//	_prof.setValOn(strNow);
	//	_prof.setEffOn(strNow);
    //}

/**
 * Get the version - generated by cvs.
 */
    public static String getVersion() {
        return "$Id: PostECs.java,v 1.19 2011/09/09 17:22:47 wendy Exp $";
    }

////////////////////////
// Properties methods //
////////////////////////

    public static Properties getMetaProperties() {
        //only loads once - on creation
        loadMetaProperties();
        return c_props_meta;
    }

    public static Properties getRMILoginProperties() {
        //only loads once - on creation
        loadRMILoginProperties();
        return c_props_rmi;
    }

/**
 * Loads the properties file for RMI Login
 */
    private synchronized static void loadRMILoginProperties() {
        if(c_props_rmi == null) {
        	c_props_rmi = new Properties();
        	try {
        	    //String strPropsFileName =  "/" + RMI_PROPS_FILE_NAME;
        	    //InputStream is = c_props_rmi.getClass().getResourceAsStream(strPropsFileName );
                    //c_props_rmi.load(is);
                    //fis.close();


                    FileInputStream fis = new FileInputStream(RMI_PROPS_FILE_NAME);
                    c_props_rmi.load(fis);
                    fis.close();

        	} catch(Exception exc){
        	    System.err.println("Unable to load " + RMI_PROPS_FILE_NAME + ": " + exc.toString());
        	}
        }
    }

/**
 * Loads the properties file for Meta definitions
 */
    private synchronized static void loadMetaProperties() {
        if(c_props_meta == null) {
        	c_props_meta = new Properties();
        	try {
        	    InputStream is = new PostECs("dummy constructor").getClass().getResource(META_PROPS_FILE_NAME).openStream();
                c_props_meta.load(is);
        	    is.close();
        	} catch(Exception exc){
        	    System.err.println("Unable to load " + META_PROPS_FILE_NAME + ": " + exc.toString());
        	}
        }
    }

    private void log(String _s) {
        System.out.println(_s);
    }

////////////////////////

}
