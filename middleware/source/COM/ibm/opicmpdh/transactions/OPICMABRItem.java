//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: OPICMABRItem.java,v $
// Revision 1.77  2011/07/15 11:34:27  wendy
// if keepfile is false, remove any dbg file too
//
// Revision 1.76  2008/11/06 16:23:46  wendy
// Release memory
//
// Revision 1.75  2008/01/31 21:29:04  wendy
// Cleanup RSA warnings
//
// Revision 1.74  2007/06/08 17:11:01  chris
// fix typo
//
// Revision 1.73  2007/06/08 16:52:34  chris
// Added setter methods for filename extension and keeping the file. See comments.
//
// Revision 1.72  2006/05/19 16:30:21  joan
// add mailneeded
//
// Revision 1.71  2006/03/08 01:00:43  yang
// adding ReQueueAbr()
//
// Revision 1.70  2006/02/20 21:39:48  joan
// clean up System.out.println
//
// Revision 1.69  2005/09/07 22:07:14  joan
// fixes
//
// Revision 1.68  2005/09/07 17:13:42  joan
// fixes
//
// Revision 1.67  2005/08/01 19:40:45  joan
// add createXML properties
//
// Revision 1.66  2005/03/11 22:42:54  dave
// removing some auto genned stuff
//
// Revision 1.65  2005/03/11 20:28:56  roger
// Support foreign ABR
//
// Revision 1.64  2005/01/27 04:02:36  dave
// removing automated readObject from Jtest
//
// Revision 1.63  2005/01/26 20:45:36  dave
// Jtest cleanup effort
//
// Revision 1.62  2004/10/13 21:26:52  dave
// more trace on interleave ABR
//
// Revision 1.61  2004/10/05 20:16:06  roger
// Show OPENID at start
//
// Revision 1.60  2004/08/05 17:56:59  roger
// SDF confused by decimals
//
// Revision 1.59  2004/07/13 21:03:22  roger
// Bad pattern, couldn't parse date
//
// Revision 1.58  2004/06/18 16:48:43  roger
// Change the timestamp pattern for parsing
//
// Revision 1.57  2004/06/11 18:37:42  roger
// Make changes to shoot inQueueTime = 0 bug
//
// Revision 1.56  2004/06/02 18:30:21  roger
// Catch exception
//
// Revision 1.55  2004/06/02 18:07:14  roger
// Fix deprecated method usage
//
// Revision 1.54  2004/06/02 16:38:22  roger
// Show ABR time in Queue on PSTAT line (XXX changed to PSTAT)
//
// Revision 1.53  2004/05/05 20:16:57  bala
// change method signature to public
//
// Revision 1.52  2004/05/05 19:27:32  bala
// import sqlexception
//
// Revision 1.51  2004/05/05 19:06:35  bala
// add method setGroupABRStatus to update the GroupABR que
//
// Revision 1.50  2004/05/05 17:54:36  bala
// set return type for setABRQueType
//
// Revision 1.49  2004/05/05 16:39:18  bala
// Adding property Quetype to differenciate queues originating
// from the Queue table
//
// Revision 1.48  2004/03/30 22:49:51  roger
// Show Idler Class
//
// Revision 1.47  2004/03/18 20:44:32  roger
// ABRs need execution class for Idler assignment
//
// Revision 1.46  2004/02/26 21:32:18  dave
// final pass
//
// Revision 1.45  2004/02/26 21:16:47  dave
// more syntax argh
//
// Revision 1.44  2004/02/26 21:11:46  dave
// syntax
//
// Revision 1.43  2004/02/26 20:43:20  dave
// tracking inprocess abri's in the abrGroup
//
// Revision 1.42  2002/11/08 17:23:27  roger
// Why ABR not being changed to Queued?
//
// Revision 1.41  2002/11/07 23:16:50  roger
// Clean up
//
// Revision 1.40  2002/11/06 22:04:41  roger
// Fix it once and for all
//
// Revision 1.39  2002/09/19 18:23:05  roger
// *** empty log message ***
//
// Revision 1.38  2002/09/19 17:17:19  roger
// *** empty log message ***
//
// Revision 1.37  2002/09/17 15:49:06  roger
// *** empty log message ***
//
// Revision 1.36  2002/09/16 23:08:06  roger
// *** empty log message ***
//
// Revision 1.35  2002/09/16 21:32:38  roger
// *** empty log message ***
//
// Revision 1.34  2002/09/16 20:38:12  roger
// *** empty log message ***
//
// Revision 1.33  2002/09/16 20:29:57  roger
// *** empty log message ***
//
// Revision 1.32  2002/09/16 20:27:14  roger
// *** empty log message ***
//
// Revision 1.31  2002/09/16 20:22:13  roger
// *** empty log message ***
//
// Revision 1.30  2002/09/16 20:16:54  roger
// *** empty log message ***
//
// Revision 1.29  2002/09/16 20:13:04  roger
// *** empty log message ***
//
// Revision 1.28  2002/09/16 20:03:05  roger
// Remove unused properties
//
// Revision 1.27  2002/08/29 21:08:39  roger
// Fixed import for TM
//
// Revision 1.26  2002/07/18 20:16:43  roger
// initPrintWriter must return the file name
//
// Revision 1.25  2002/06/26 15:05:28  roger
// strNow not needed ~until~ initPrintWriter
//
// Revision 1.24  2002/06/19 16:11:15  roger
// initPrintWriter must have strNow
//
// Revision 1.23  2002/06/19 15:51:49  roger
// Need to initPrintWriter with a passed strNow
//
// Revision 1.22  2002/06/18 23:27:54  roger
// VE Name
//
// Revision 1.21  2002/06/18 23:08:41  roger
// Need VE Name property for abri
//
// Revision 1.20  2002/06/18 20:56:47  roger
// Flush and close PW
//
// Revision 1.19  2002/06/18 20:27:03  roger
// Needed method to initialize PrintWriter
//
// Revision 1.18  2002/06/18 19:58:46  roger
// Fix a null pointer
//
// Revision 1.17  2002/06/07 17:24:59  roger
// Now string needed in OPICMABRItem
//
// Revision 1.16  2002/06/04 21:22:37  roger
// Show classname in toString output
//
// Revision 1.15  2002/05/31 21:57:02  roger
// Trap exception
//
// Revision 1.14  2002/05/31 21:47:28  roger
// Make PrintWriter part of ABRItem
//
// Revision 1.13  2002/05/30 20:05:23  roger
// Do getProperties in constructor
//
// Revision 1.12  2002/05/30 17:41:46  roger
// Need import
//
// Revision 1.11  2002/05/30 17:40:27  roger
// Fixes
//
// Revision 1.10  2002/05/30 17:24:06  roger
// Merging properties
//
// Revision 1.9  2002/05/29 21:59:04  joan
// fix wrong type return
//
// Revision 1.8  2002/05/29 21:36:59  roger
// Merge property file properties into item
//
// Revision 1.7  2002/01/28 20:53:29  dave
// merging 1.0 maint into 1.1
//
// Revision 1.6  2001/08/22 16:53:14  roger
// Removed author RM
//
// Revision 1.5  2001/05/02 06:56:15  dave
// created SetABRState
//
// Revision 1.4  2001/05/02 06:05:08  dave
// more ABR wave II stuf
//
// Revision 1.3  2001/04/29 22:10:56  dave
// Boolean to new Boolean
//
// Revision 1.2  2001/04/29 21:58:52  dave
// Syntax Fixes Round 1
//
// Revision 1.1  2001/04/29 21:17:43  dave
// ABR Base Function Work
//


package COM.ibm.opicmpdh.transactions;


import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.SQLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This object holds a Basic ABR Item for OPICM ABR Management
 * @author  David Bigelow
 * @version @date
 */
public class OPICMABRItem
    extends Object
    implements OPICMObject, Serializable, Cloneable {
    /**
     * @serial
     */
    static final long serialVersionUID = 1L;
    // used to help define the table model
    /**
     * TBD
     */
    public static final int ENTITYTYPE = 0;
    /**
     * TBD
     */
    public static final int ENTITYID = 1;
    /**
     * TBD
     */
    public static final int ABRCODE = 2;
    /**
     * TBD
     */
    public static final int ABRDESC = 3;
    /**
     * TBD
     */
    public static final int ABRNEW = 4;
    /**
     * TBD
     */
    public static final int ABRQUEUED = 5;
    /**
     * TBD
     */
    public static final int ABRINPROC = 6;
    /**
     * TBD
     */
    public static final int ABRSUCCESS = 7;
    /**
     * TBD
     */
    public static final int ABRFAILURE = 8;
    /**
     * TBD
     */
    public static final int OPENID = 9;
    /**
     * TBD
     */
    public static final int ABRVALFROM = 10;
    /**
     * TBD
     */
    public static final int ABRCLASSNAME = 12;
    /**
     * TBD
     */
    public static final int ABRDIRECTORY = 13;
    /**
     * TBD
     */
    public static final int ABRREADONLY = 22;
    /**
     * TBD
     */
    public static final int ABRKEEPFILE = 24;
    /**
     * TBD
     */
    public static final int ABRFILENAME = 25;
    /**
     * TBD
     */
    public static final int ABRREPORTTYPE = 27;
    private String m_strEntityType = null;
    private int m_iEntityID = 0;
    private String m_strABRCode = null;
    private String m_strABRDescription = null;
    private String m_strABRState = null;
    private int m_iOpenID = 0;
    private int m_iRef_NLSID = 0;
    private String m_strABRValFrom = null;
    private boolean m_bEnabled = true;
    private String m_strABRClassName = null;
    private String m_strDirectory = null;
    private boolean m_bReadOnly = false;
    private boolean m_bKeepFile = false;
    private boolean m_bMailNeeded = true;
    private boolean m_bReQueueAbr = false;
    private String m_strFileName = null;
    private String m_strNow = null;
    private PrintWriter m_pw = null;
    private String m_strVEName = null;
    private String m_strReportType = null;
    private String m_strIdlerClass = null;
    private String m_strAbrQueType = "NonQueue";

    private String m_strFileExtension = "";
    /**
     * Creates a New OPICMABRItem
     *
     * @param _s1
     * @param _i1
     * @param _s2
     * @param _s3
     * @param _i2
     * @param _s4
     * @param _s5
     */
    public OPICMABRItem(
        String _s1,
        int _i1,
        String _s2,
        String _s3,
        int _i2,
        String _s4,
        String _s5,
        int _i3) {

        super();

        // Set from parms
        m_strEntityType = _s1;
        m_iEntityID = _i1;
        m_strABRCode = _s2;
        m_strABRState = _s3;
        m_iOpenID = _i2;
        m_strABRDescription = _s4;
        m_strABRValFrom = _s5;
        m_iRef_NLSID = _i3;

        getProperties();
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }
    /**
     * Display the object values for testing and debuging.
     *
     * @param out
     */
    public void display(java.io.PrintStream out) {
        out.println(
            m_strEntityType
                + ":"
                + m_iEntityID
                + ":"
                + m_strABRDescription
                + ":"
                + m_strABRCode
                + ":"
                + m_strABRState
                + ":"
                + m_strABRClassName
                + ":"
                + m_strIdlerClass);
    }
    /**
     * @see java.lang.Object#toString()
     * @author Dave
     */
    public String toString() {
        return m_iOpenID
        + ":"
        + m_strEntityType
        + ":"
        + m_iEntityID
        + ":"
        + m_strABRDescription
        + ":"
        + m_strABRCode
        + ":"
        + m_strABRState
        + ":"
        + m_strABRClassName
        + ":"
        + m_strIdlerClass
        + ":"
        + m_iRef_NLSID;
    }
    /**
     * getVersion
     * @return the date/time this class was generated
     */
    public String getVersion() {
        return "$Id: OPICMABRItem.java,v 1.77 2011/07/15 11:34:27 wendy Exp $";
    }
    /**
     * hashkey
     *
     * Return the hashkey (EntityType + EntityID + ABRCode)
     * @return the hashkey (EntityType + EntityID + ABRCode)
     */
    public String hashkey() {
        return m_strEntityType + m_iEntityID + m_strABRCode;
    }
    /**
     * getEntityType
     * @return
     * @author Dave
     */
    public String getEntityType() {
        return m_strEntityType;
    }

    /**
     * getEntityID
     * @return
     * @author Dave
     */
    public int getEntityID() {
        return m_iEntityID;
    }
    /**
     * getABRCode
     * @return
     * @author Dave
     */
    public String getABRCode() {
        return m_strABRCode;
    }
    /**
     * getABRDescription
     * @return
     * @author Dave
     */
    public String getABRDescription() {
        return m_strABRDescription;
    }
    /**
     * getABRState
     *
     * @return
     * @author Dave
     */
    public String getABRState() {
        return m_strABRState;
    }
    /**
     * setABRStatus
     *
     * @param _s
     * @author Dave
     */
    public void setABRState(String _s) {
        m_strABRState = _s;
    }
    /**
     * getOPENID
     * @return
     * @author Dave
     */
    public int getOpenID() {
        return m_iOpenID;
    }
    /**
     * getValFrom
     * @return
     * @author Dave
     */
    public String getABRValFrom() {
        return m_strABRValFrom;
    }
    /**
     * isQueued
     * @return
     * @author Dave
     */
    public boolean isQueued() {
        if (m_strABRState.equals("0020")) {
            return true;
        }

        return false;
    }
    /**
     * getInQueueTime
     * @return
     * @author Dave
     */
    public long getInQueueTime() {
        //"2004-04-12 18:58:21.767953"
        //    DateFormat dfISO = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        DateFormat dfISO = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dQueue = null;
        long lNow = 0;
        long lQueue = 0;

        try {
            D.ebug(D.EBUG_SPEW, "getQTime ValFrom " + m_strABRValFrom);
            dQueue = dfISO.parse(m_strABRValFrom.substring(0, 19));
            D.ebug(D.EBUG_SPEW, "getQTime dQueue " + dQueue);
            lNow = System.currentTimeMillis();
            D.ebug(D.EBUG_SPEW, "getQTime lNow " + lNow);
            lQueue = dQueue.getTime();
            D.ebug(D.EBUG_SPEW, "getQTime lQueue " + lQueue);
        } catch (Exception x) {
            D.ebug("getQTime exception " + x);
        }
        return (lNow - lQueue);
    }
    /* Gets the object represented at the index i
    */
    /**
     * get based opon location
     * @param i
     * @return
     * @author Dave
     */
    public Object get(int i) {

        switch (i) {
        case ENTITYTYPE :
            return getEntityType();

        case ENTITYID :
            return new Integer(getEntityID());

        case ABRCODE :
            return getABRCode();

        case ABRDESC :
            return getABRDescription();

        case ABRVALFROM :
            return getABRValFrom();

        case OPENID :
            return new Integer(getOpenID());

        case ABRNEW :
            if (getABRState().equals("0010")) {
                return new Boolean(true);
            }

            return new Boolean(false);

        case ABRQUEUED :

            if (getABRState().equals("0020")) {
                return new Boolean(true);
            }
            return new Boolean(false);

        case ABRINPROC :
            if (getABRState().equals("0030")) {
                return new Boolean(true);
            }

            return new Boolean(false);

        case ABRFAILURE :

            if (getABRState().equals("0050")) {
                return new Boolean(true);
            }

            return new Boolean(false);

        case ABRSUCCESS :

            if (getABRState().equals("0040")) {
                return new Boolean(true);
            }

            return new Boolean(false);

        case ABRCLASSNAME :
        case ABRDIRECTORY :
        case ABRREADONLY :
        case ABRKEEPFILE :
        case ABRFILENAME :
        default :
            return "";
        }
    }
    /**
     * setEnabled
     * @param _b
     * @author Dave
     */
    public void setEnabled(boolean _b) {
        m_bEnabled = _b;
    }
    /**
     * isEnabled
     *
     * @return
     * @author Dave
     */
    public boolean isEnabled() {
        return m_bEnabled;
    }
    /**
     * getABRQueType
     * @return
     * @author Dave
     */
    public String getABRQueType() {
        return m_strAbrQueType;
    }
    /**
     * setABRQueType
     *
     * @param _s
     * @author Dave
     */
    public void setABRQueType(String _s) {
        m_strAbrQueType = _s;
    }
    /**
     * getABRClassName
     * @return
     * @author Dave
     */
    public String getABRClassName() {
        return m_strABRClassName;
    }
    /**
     * getDirectory
     * @return
     * @author Dave
     */
    public String getDirectory() {
        return m_strDirectory;
    }
    /**
     * getEnabled
     *
     * @return
     * @author Dave
     */
    public boolean getEnabled() {
        return m_bEnabled;
    }
    /**
     * getReadOnly
     * @return
     * @author Dave
     */
    public boolean getReadOnly() {
        return m_bReadOnly;
    }

    /**
     * getFileCreateXML
     * @return
     * @author Dave
     */
    public String getFileExtension() {
        return m_strFileExtension;
    }
	/**
	 * You may intend to write a xsl file but some kind of exception occurred
	 * and you want to return a html or txt file with problem details in it.
	 * or you determine the file size is to large and want to zip it. 
	 * So we need to be able to change this outside of abr.server.properties
	 * @param newExtension a String with the file extension
	 * @return true if not null
	 * @author Chris Stolpe
	 */
	public boolean setFileExtension(String newExtension) {
		if (newExtension != null && newExtension.length() > 0) {
			m_strFileExtension = newExtension;
		}
		return m_strFileExtension != null;
	}

    /**
     * getKeepFile
     * @return
     * @author Dave
     */
    public boolean getKeepFile() {
        return m_bKeepFile;
    }

	/**
	 * Normally we do not keep files except for debugging.
	 * This allows us to keep a file if we can catch 
	 * the exception in the ABR
	 * @param keepIt
	 * @author Chris Stole
	 */
	public void setKeepFile(boolean keepIt) {
		m_bKeepFile = keepIt;
	}

    public boolean getReQueueAbr() {
        return m_bReQueueAbr;
    }

    /**
     * getMailNeeded
     * @return
     * @author Dave
     */
    public boolean getMailNeeded() {
        return m_bMailNeeded;
    }

    /**
     * getFileName
     * @return
     * @author Dave
     */
    public String getFileName() {
        return m_strFileName;
    }
    /**
     * getVEName
     * @return
     * @author Dave
     */
    public String getVEName() {
        return m_strVEName;
    }
    /**
     * getReportType
     * @return
     * @author Dave
     */
    public String getReportType() {
        return m_strReportType;
    }
    /**
     * TBD
     * @return
     * @author Dave
     */
    public String getIdlerClass() {
        return m_strIdlerClass;
    }
    /**
     * TBD
     * @return
     * @author Dave
     */
    public int getOPENID() {
        return m_iOpenID;
    }
    /**
     * TBD
     * @return
     * @author Dave
     */
    public int getRef_NLSID() {
        return m_iRef_NLSID;
    }
    /**
     * TBD
     *
     * @author Dave
     */
    public void getProperties() {

        m_strABRClassName = ABRServerProperties.getClassName(m_strABRCode);
        m_strDirectory = ABRServerProperties.getOutputPath();
        m_bEnabled = ABRServerProperties.getEnabled(m_strABRCode);
        m_bReadOnly = ABRServerProperties.getReadOnly(m_strABRCode);
        m_bKeepFile = ABRServerProperties.getKeepFile(m_strABRCode);
        m_bReQueueAbr = ABRServerProperties.getReQueueAbr(m_strABRCode);
        m_strFileExtension = ABRServerProperties.getFileExtension(m_strABRCode);
        m_strFileName =
            m_strDirectory
            + m_strABRCode
            + m_strEntityType
            + m_iEntityID
            + m_strNow
            + ((m_strFileExtension.length() > 0 )? ("." + m_strFileExtension):".htm");
       // D.ebug(D.EBUG_SPEW,"OPICMABRItem getProperties " + m_strFileName);
        m_strVEName = ABRServerProperties.getVEName(m_strABRCode);
        m_strReportType = ABRServerProperties.getReportType(m_strABRCode);
        m_strIdlerClass = ABRServerProperties.getIdlerClass(m_strABRCode);
        m_bMailNeeded = ABRServerProperties.getMailNeeded(m_strABRCode);
    }
    /**
     * getPrintWriter
     * @return
     * @author Dave
     */
    public final PrintWriter getPrintWriter() {
        return m_pw;
    }
    /**
     * closePrintWriter
     * @author Dave
     */
    public final void closePrintWriter() {
        m_pw.flush();
        m_pw.close();
    }
    /**
     * initPrintWriter
     * @param _strNow
     * @return
     * @author Dave
     */
    public final String initPrintWriter(String _strNow) {

        m_strFileName =
            m_strDirectory
            + m_strABRCode
            + m_strEntityType
            + m_iEntityID
            + _strNow
            + (m_strFileExtension.length() > 0? "."+ m_strFileExtension:".htm");

        D.ebug(D.EBUG_SPEW,"OPICMABRItem initPrintWriter " + m_strFileName);
        D.ebug(D.EBUG_SPEW, "OPICMABRItem m_strFileName = " + m_strFileName);

        try {
            m_pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(m_strFileName, true), "UTF-8"));
        } catch (Exception x) {
            m_pw = null;

            D.ebug(D.EBUG_ERR, "trouble creating PrintWriter " + x);
        }

        return m_strFileName;
    }
    /**
     * removeOutputFile
     *
     * @author Dave
     */
    public final void removeOutputFile() {
        try {
            File f1 = new File(m_strFileName);

            if (f1.exists()) {
                f1.delete();
            }
            //if a debug file exists, delete it too
    		int extid = m_strFileName.lastIndexOf(".");
    		if(extid != -1){
    			String dbgfn = m_strFileName.substring(0,extid+1)+"dbg";
    			f1 = new File(dbgfn);
                if (f1.exists()) {
                    f1.delete();
                }
    		}
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
    /**
     * setPrintWriter
     * @param _pw
     * @author Dave
     */
    public final void setPrintWriter(PrintWriter _pw) {
        m_pw = _pw;
    }
    /**
     * print
     *
     * @param _strLine
     * @author Dave
     */
    public final void print(String _strLine) {
        if (m_pw == null) {
            D.ebug(D.EBUG_ERR, "m_pw is null - can't print!");
        } else {
            m_pw.print(_strLine);
        }
    }
    /**
     * print
     * @param _strLine
     * @author Dave
     */
    public final void println(String _strLine) {
        if (m_pw == null) {
            D.ebug(D.EBUG_ERR, "m_pw is null - can't println!");
        } else {
            m_pw.println(_strLine);
        }
    }

    /**
     * equals
     * @see java.lang.Object#equals(java.lang.Object)
     * @author Dave
     */
    public final boolean equals(Object _obj) {

        OPICMABRItem tmp = null;

        if (!(_obj instanceof OPICMABRItem)) {
            return false;
        }

        tmp = (OPICMABRItem) _obj;
        if (!getEntityType().equals(tmp.getEntityType())) {
            return false;
        }
        if (getEntityID() != tmp.getEntityID()) {
            return false;
        }
        if (!getABRCode().equals(tmp.getABRCode())) {
            return false;
        }
        return true;
    }
    /**
     * setGroupABRStatus
     *
     * @param _db
     * @param _strEnterprise
     * @param _strQueue
     * @param _strEntityType
     * @param _iEntityID
     * @param _iCurrentStatus
     * @param _iNextStatus
     * @param _iOPWGid
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @author Dave
     */
    public void setGroupABRStatus(
        Database _db,
        String _strEnterprise,
        String _strQueue,
        String _strEntityType,
        int _iEntityID,
        int _iCurrentStatus,
        int _iNextStatus,
        int _iOPWGid)
        throws
        SQLException,
        MiddlewareException,
        MiddlewareShutdownInProgressException {
        try {
            _db.debug(
                D.EBUG_DETAIL,
                "calling GBL7424("
                    + _strEnterprise
                    + ":"
                    + _strQueue
                    + ":"
                    + _strEntityType
                    + ":"
                    + _iEntityID
                    + ":"
                    + _iCurrentStatus
                    + ":"
                    + _iNextStatus
                    + ":"
                    + _iOPWGid);
            _db.callGBL7424(
                new ReturnStatus(-1),
                _strEnterprise,
                _strQueue,
                _strEntityType,
                _iEntityID,
                _iCurrentStatus,
                _iNextStatus,
                _iOPWGid);
          //  _db.commit();
          //  _db.freeStatement();
          //  _db.isPending();
        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * release memory
     */
    public void dereference(){
    	m_strEntityType = null;
        m_strABRCode = null;
        m_strABRDescription = null;
        m_strABRState = null;
        m_strABRValFrom = null;
        m_strABRClassName = null;
        m_strDirectory = null;
        m_strFileName = null;
        m_strNow = null;
        m_pw = null;
        m_strVEName = null;
        m_strReportType = null;
        m_strIdlerClass = null;
        m_strAbrQueType = null;
        m_strFileExtension = null;	
    }
}
