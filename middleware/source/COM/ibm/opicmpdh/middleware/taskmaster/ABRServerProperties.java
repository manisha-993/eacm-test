//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ABRServerProperties.java,v $
// Revision 1.61  2014/07/24 21:00:22  wendy
// RCQ00303672 - provide way to turn off all email - moved to middleware.server.properties
//
// Revision 1.60  2014/07/24 20:14:23  wendy
// RCQ00303672 - provide way to turn off all email
//
// Revision 1.59  2014/05/15 15:20:35  wendy
// RCQ00303672 - add autogen txt files to reports in-basket in JUI
//
// Revision 1.58  2013/11/04 14:28:33  liuweimi
// 1. Change based on document: BH FS ABR Data Quality 20130904b.doc to fix Defect:  BH 185136
// 2. DQ updates (RCQ00270286-RQ) based on doc(BH FS ABR Data Quality 20131029)
//
// Revision 1.57  2011/08/11 18:53:21  wendy
// RCQ00180384 make turnoffdg more flexible
//
// Revision 1.56  2009/12/30 14:40:45  wendy
// add default to getvalue()
//
// Revision 1.55  2009/12/29 20:24:44  wendy
// Added static constants for queueing
//
// Revision 1.54  2009/12/16 12:34:08  wendy
// change case on RFRqueued key
//
// Revision 1.53  2009/11/03 20:52:12  wendy
// BH Configurable Services
//
// Revision 1.52  2009/08/27 11:48:40  wendy
// Support ABR debug level (SR5)
//
// Revision 1.51  2009/08/06 21:52:25  wendy
// Support turning off DG in properties file
//
// Revision 1.50  2009/06/11 18:20:05  wendy
// SR10, 11, 12, 15, 17
//
// Revision 1.49  2009/05/28 16:43:04  wendy
// support script name
//
// Revision 1.48  2009/02/04 21:14:18  wendy
// CQ00016165 more properties needed
//
// Revision 1.47  2008/06/24 16:18:15  wendy
// update comments
//
// Revision 1.46  2008/06/24 16:09:17  wendy
// CQ00006088-WI
//
// Revision 1.45  2008/04/08 12:10:44  wendy
// CQ00003539 add support for specifying nlsids
//
// Revision 1.44  2008/01/22 18:16:58  wendy
// Cleanup RSA warnings
//
// Revision 1.43  2007/08/30 11:14:44  wendy
// RQ0413071334 added support for specifying domain
//
// Revision 1.42  2006/05/19 21:30:05  joan
// changes
//
// Revision 1.41  2006/05/19 16:30:21  joan
// add mailneeded
//
// Revision 1.40  2006/03/08 01:50:58  yang
// minor fix
//
// Revision 1.39  2006/03/08 01:01:52  yang
// adding ReQueueAbr()
//
// Revision 1.38  2005/09/07 17:03:01  joan
// set file extension
//
// Revision 1.37  2005/09/06 16:52:16  yang
// add GenerateXL properties
//
// Revision 1.36  2005/08/01 19:40:45  joan
// add createXML properties
//
// Revision 1.35  2005/01/25 23:16:50  dave
// more JTest cleanup
//
// Revision 1.34  2005/01/25 20:53:18  dave
// JTest allignment
//
// Revision 1.33  2005/01/24 21:58:57  dave
// starting to clean up per new IBM standards
//
// Revision 1.32  2004/12/14 23:16:00  bala
// set the Idlerclass default to 'A' to match the assignment
// in the taskmaster idler class assignment default
//
// Revision 1.31  2004/03/23 19:15:33  bala
// more control over subscription, adding 2 more properties
//
// Revision 1.30  2004/03/18 20:44:32  roger
// ABRs need execution class for Idler assignment
//
// Revision 1.29  2004/01/26 18:04:11  roger
// New property for requested Idler to use for execution
//
// Revision 1.28  2003/11/24 21:50:08  bala
// add methods for ExtMail and IntMail
//
// Revision 1.27  2003/08/21 20:08:43  bala
// added getSubscrVEName method
//
// Revision 1.26  2003/07/31 21:07:15  bala
// Added getCategory method to retrieve the categories (CAT1, CAT2 etc) of the abr
//
// Revision 1.25  2003/05/28 22:37:09  bala
// added method to get file path of .dtd file
//
// Revision 1.24  2002/10/02 23:06:09  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;


import java.io.FileInputStream;
import java.util.Properties;
import COM.ibm.opicmpdh.transactions.Cipher;
import COM.ibm.opicmpdh.middleware.D;

/**
 * Retrieve configuration properties for the ABR Engine
 * @version @date
 */
public final class ABRServerProperties extends Properties {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final String PROPERTIES_FILENAME = "abr.server.properties"; //$NON-NLS-1$
    private static Properties c_propABR = null;

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
        Log.out(ABRServerProperties.getVersion());
    }

    /**
     * Some class level initialization
     */
    static {
        // Load the properties from file so they are available for each accessor method
        reloadProperties();
    }

    /**
     * Don't let anyone instantiate this class.
     */
    private ABRServerProperties() {
    }

    /**
     * Load the ABR properties from the properties file
     */
    private static final void loadProperties() {

        try {

            if (c_propABR == null) {
                c_propABR = new Properties();
                FileInputStream inProperties = new FileInputStream("./" + PROPERTIES_FILENAME); //$NON-NLS-1$
                c_propABR.load(inProperties);
                inProperties.close();
            }
        } catch (Exception x) {
            Log.out("Unable to loadProperties for " //$NON-NLS-1$
            +PROPERTIES_FILENAME + " " //$NON-NLS-1$
            +x);
        }
    }

    /**
     * Reload the properties from the abr.server.properties file
     */
    public static final void reloadProperties() {
        loadProperties();
    }

    /**
     * Return the fully qualified class name from the abr.server.properties file
     *
     * @param _strABR
     * @return String
     */
    public static final String getClassName(String _strABR) {
        return c_propABR.getProperty(_strABR + "_class", //$NON-NLS-1$
        "ClassName not in " + PROPERTIES_FILENAME + " file"); //$NON-NLS-1$  //$NON-NLS-2$
    }

    /**
     * is this ABR enabled?
     *
     * @param _strABR
     * @return boolean
     */
    public static final boolean getEnabled(String _strABR) {
        return Boolean.valueOf(c_propABR.getProperty(_strABR + "_enabled", "false")) //$NON-NLS-1$  //$NON-NLS-2$
        .booleanValue();
    }

    /**
     * Should this ABR keep its file upon completion?
     *
     * @param _strABR
     * @return boolean
     */
    public static final boolean getKeepFile(String _strABR) {
        return Boolean.valueOf(c_propABR.getProperty(_strABR + "_keepfile", "false")) //$NON-NLS-1$  //$NON-NLS-2$
        .booleanValue();
    }

    /**
     * Returns the output Path of where ABR output needs to be directed to
     *
     * @return String
     */
    public static final String getOutputPath() {
        return c_propABR.getProperty("printwriter_path", "./"); //$NON-NLS-1$  //$NON-NLS-2$
    }

    /**
     * Is this ABR read only?
     *
     * @param _strABR
     * @return boolean
     */
    public static final boolean getReadOnly(String _strABR) {
        return Boolean.valueOf(c_propABR.getProperty(_strABR + "_read_only", "false")) //$NON-NLS-1$  //$NON-NLS-2$
        .booleanValue();
    }

    /**
     * Should this ABR create report file .xml?
     *
     * @param _strABR
     * @return boolean
     */
    public static final String getFileExtension(String _strABR) {
        return c_propABR.getProperty(_strABR + "_fileExtension", ""); //$NON-NLS-1$  //$NON-NLS-2$
    }

    /**
     * What is the report type of this ABR?
     *
     * @param _strABR
     * @return String
     */
    public static final String getReportType(String _strABR) {
        return c_propABR.getProperty(_strABR + "_report_type", //$NON-NLS-1$
        "report type not in " + PROPERTIES_FILENAME + " file"); //$NON-NLS-1$  //$NON-NLS-2$
    }

    /**
     * What VE name is used for this ABR?
     *
     * @param _strABR
     * @return String
     */
    public static final String getVEName(String _strABR) {
        return c_propABR.getProperty(_strABR + "_vename", //$NON-NLS-1$
        "VEName not in " + PROPERTIES_FILENAME + " file"); //$NON-NLS-1$  //$NON-NLS-2$
    }

    /**
     * What VE name is the DTD Path for the given ABR?
     *
     * @param _strABR
     * @return String
     */
    public static final String getDTDFilePath(String _strABR) {
        return c_propABR.getProperty(_strABR + "_DTDFilePath", //$NON-NLS-1$
        "DTDFilePath not in " + PROPERTIES_FILENAME + " file"); //$NON-NLS-1$  //$NON-NLS-2$
    }

    /**
     * What is the Category Associated with the ABR and Attribute?
     *
     * @param _strABR
     * @param _strAttributeName
     * @return String
     */
    public static final String getCategory(
        String _strABR,
        String _strAttributeName) {
        return c_propABR.getProperty(_strABR + "_" + _strAttributeName, null); //$NON-NLS-1$
    }

    /**
     * What is the VE subscriber name for the given ABR?
     *
     * @param _strABR
     * @return String
     */
    public static final String getSubscrVEName(String _strABR) {
        return c_propABR.getProperty(_strABR + "_SUBSCRVE", null); //$NON-NLS-1$
    }

    /**
     * What is the External Mail for the given ABR?
     *
     * @param _strABR
     * @return String
     */
    public static final String getExtMail(String _strABR) {
        return c_propABR.getProperty(_strABR + "_EXTMAIL", null); //$NON-NLS-1$
    }

    /**
     *  What is the Internal Mailing address of the ABR?
     *
     * @param _strABR
     * @return String
     */
    public static final String getIntMail(String _strABR) {
        return c_propABR.getProperty(_strABR + "_INTMAIL", null); //$NON-NLS-1$
    }

    /**
     * What is the ABR's Idler Class
     *
     * @param _strABR
     * @return String
     */
    public static final String getIdlerClass(String _strABR) {
        return c_propABR.getProperty(_strABR + "_idler_class", "A"); //$NON-NLS-1$  //$NON-NLS-2$
    }

    /**
     * Is the Subscription Enabled?
     *
     * @param _strABR
     * @return String
     */
    public static final String getSubscrEnabled(String _strABR) {
        return c_propABR.getProperty(_strABR + "_SUBSCR_ENABLED", "REQUIRED") //$NON-NLS-1$  //$NON-NLS-2$
        .toUpperCase();
    }

    /**
     * Should we notify Subscribers on Failure?
     *
     * @param _strABR
     * @return String
     */
    public static final String getSubscrNotifyOnError(String _strABR) {
        return c_propABR.getProperty(_strABR + "_SUBSCR_NOTIFY_ON_ERROR", "YES") //$NON-NLS-1$  //$NON-NLS-2$
        .toUpperCase();
    }

    /**
     * Return the version Literal
     *
     * @return String
     */
    public static final String getVersionLiteral() {
        return c_propABR.getProperty("middleware_version_literal", //$NON-NLS-1$
        "middleware_version_literal not in " //$NON-NLS-1$
        +PROPERTIES_FILENAME + " file"); //$NON-NLS-1$
    }

    public static final boolean getReQueueAbr(String _strABR) {
        return Boolean.valueOf(c_propABR.getProperty(_strABR + "_requeue_abr", "false"))
        .booleanValue();
    }


    /**
     * Should this ABR keep its file upon completion?
     *
     * @param _strABR
     * @return boolean
     */
    public static final boolean getMailNeeded(String _strABR) {
        return Boolean.valueOf(c_propABR.getProperty(_strABR + "_mailneeded", "true")) //$NON-NLS-1$  //$NON-NLS-2$
        .booleanValue();
    }

    /**
     * RCQ00303672 create dg entities for the attachment files
     * DGEntity will add attachments to the email if ".* <!--STARTFILEBREAKFORMAIL:" is found in the html.
     * Any files in this list will also have DGEntity created for it and be accessible in the JUI in-basket
     * Setting this to 'all' will create DGEntity for all files.
     * CHQAUTOGEN_dgattachment_files=charges.txt,prodnum.txt,xcharges.txt
     * @param _strABR
     * @return String
     */
    public static final String getDGAttachmentFiles(String _strABR) {
        return c_propABR.getProperty(_strABR + "_dgattachment_files",null); //$NON-NLS-1$  
    }
    
    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public static final String getVersion() {
        return "$Id: ABRServerProperties.java,v 1.61 2014/07/24 21:00:22 wendy Exp $"; //$NON-NLS-1$
    }

    /** RQ0413071334
     * Get list of PDHDOMAINs that need rules applied
     * 1) ANNABRSTATUS_domains=0050,0390  =>any not in that list simply pass
	 * 2) not specified => would mean apply rules to all domains
     *
     * @param _strABR
     * @return String
     */
    public static final String getDomains(String _strABR) {
        return c_propABR.getProperty(_strABR + "_domains", "all");
    }
    
    /** SR10, 11, 12, 15, 17
     * Get list of PDHDOMAINs that need rules applied
     * 1) ANNABRSTATUS_domainList1=0050,0390  =>any not in that list do not do the check
	 * 2) not specified => would mean apply rules to all domains
     *
     * @param _strABR
     * @param ruleNumber
     * @return
     */
    public static final String getDomainList(String _strABR, String ruleNumber) {
        return c_propABR.getProperty(_strABR + "_domainList"+ruleNumber, "all");
    }

    /** CQ00003539-WI -  BHC 3.0
     * Get list of NLSIDs
     * 1) ADSABRSTATUS_nlsids=1,7,10
	 * 2) not specified => would mean just NLSID 1 (English)
     *
     * @param _strABR
     * @return String
     */
    public static final String getNLSIDs(String _strABR) {
        return c_propABR.getProperty(_strABR + "_nlsids", "1");
    }
    /** CQ00006088-WI
	 * LA CTO Support - The requirement is to not feed LA products to ePIMS.
     * Get list of COUNTRYs that flow
     * 1) EPIMSABRSTATUS_countrylist=1652,1464  =>any not in that list do not flow
	 * 2) not specified => would mean send all countries
     *
     * @param _strABR
     * @return String
     */
    public static final String getCountryList(String _strABR) {
        return c_propABR.getProperty(_strABR + "_countrylist", "all");
    }
    /** CQ00016165
     * Specify types of files ABR should create - this is not the abr report output
     * QSM wants xls, txt or both types generated
	 * QSMRPTABRSTATUS_fileFormats=xls,txt
     *
     * @param _strABR
     * @return String
     */
    public static final String getFileFormats(String _strABR) {
        return c_propABR.getProperty(_strABR + "_fileFormats", "");
    } 
    /** CQ00016165
     * Specify prefix of file ABR should create - this is not the abr report output
	 * QSMRPTABRSTATUS_filePrefix=EACM_TO_QSM_
     *
     * @param _strABR
     * @return String
     */
    public static final String getFilePrefix(String _strABR) {
        return c_propABR.getProperty(_strABR + "_filePrefix", "");
    }  
    /** CQ00016165
     * username:password@ftp.whatever.com/file.zip;
	 * QSMRPTABRSTATUS_ftpUserid=username
     *
     * @param _strABR
     * @return String
     */
    public static final String getFtpUserid(String _strABR) {
        return c_propABR.getProperty(_strABR + "_ftpUserid", "");
    }   
    /** CQ00016165
     * username:password@ftp.whatever.com/file.zip;
	 * QSMRPTABRSTATUS_ftpPassword=(pw)encrypted using COM.ibm.opicmpdh.transactions.Cipher
     *
     * @param _strABR
     * @return String
     */
    public static final String getFtpPassword(String _strABR) {
    	String pw = c_propABR.getProperty(_strABR + "_ftpPassword", "");
    	if (pw.length()>0){
    		pw = Cipher.codec(pw);
    	}
        return pw;
    }  
    /** CQ00016165
     * username:password@ftp.whatever.com/dir/file.zip;
	 * QSMRPTABRSTATUS_ftpHost=ftp.whatever.com/dir/
     *
     * @param _strABR
     * @return String
     */
    public static final String getFtpHost(String _strABR) {
        return c_propABR.getProperty(_strABR + "_ftpHost", "");
    }    
    /** 
     * Allow ABR to execute a script (ie. to sftp to MVS)
     * @param _strABR
     * @return String
     */
    public static final String getScript(String _strABR) {
        return c_propABR.getProperty(_strABR + "_script", "");
    } 
    
    /** CQ00016165
     * Specify value to use for queueing ABR attribute
     * Data Quality ABR has to be changed to support a properties file specifying the Queued Value for QSM
	 * DQABRSTATUS_QSMRPTABRSTATUS_queuedValue=0090
     *
     * @param _strABR
     * @return String
     */
    public static final String getABRQueuedValue(String _strABR) {
        return c_propABR.getProperty(_strABR + QUEUEDVALUE, "0020");
    }   
    /**
     * BH configured services
     * Specify value to use for setting an abr when status has moved to ready for review
     * @param _strABR
     * @return
     */
    public static final String getABRRFRQueuedValue(String _strABR) {
        return c_propABR.getProperty(_strABR + RFRQUEUEDVALUE, "0020");
    } 

    /*************************
     * Allow DGentity creation to be turned off in properties file
     * if xx_turnOffDG=true then no report will be saved in the pdh and no notification will be done
     * @param _strABR
     * @return
     * /
    public static final boolean turnOffDGEntity(String _strABR) {
    	 return Boolean.valueOf(c_propABR.getProperty(_strABR + "_turnOffDG", "false")).booleanValue();
    }
    /*************************
     * Allow DGentity creation to be turned off in properties file
     * Perf - RCQ00180384 - EACM DG Reports reduce generation and storage for PASS- 
     * if xx_turnOffDG=always then no report will be saved in the pdh and no notification will be done
     * if xx_turnOffDG=onpass then no report will be saved in the pdh if the abr passes
     * @param _strABR  
     * @return
     */
    public static final String turnOffDGEntity(String _strABR) {
    	return c_propABR.getProperty(_strABR + "_turnOffDG", "never");
    }
    /********************
     * allow separate debug level for abr internal debug logging
     * @param _strABR
     * @return
     */
    public static final int getABRDebugLevel(String _strABR) {
        return Integer.parseInt(c_propABR.getProperty(_strABR + "_debugLevel", ""+D.EBUG_ERR));
    } 
    /**
     * allow a way to get any value for an ABR
     * @param _strABR
     * @param key
     * @return
     */
    public static final String getValue(String _strABR,String key) {
        return getValue(_strABR, key, "");
    } 
    /**
     * allow a way to get any value for an ABR and specify a default
     * @param _strABR
     * @param key
     * @param defvalue
     * @return
     */
    public static final String getValue(String _strABR,String key,String defvalue) {
        return c_propABR.getProperty(_strABR + key, defvalue);
    } 
    public static final String QUEUEDVALUE = "_queuedValue";
    public static final String RFRQUEUEDVALUE = "_RFRqueuedValue";
    public static final String RFRFIRSTQUEUEVALUE = "_RFRFIRSTqueuedValue";
    public static final String FINALFIRSTQUEUEVALUE = "_FINALFIRSTqueuedValue";
}
