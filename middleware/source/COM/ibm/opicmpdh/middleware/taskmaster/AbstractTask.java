//
// Copyright (c) 2002, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: AbstractTask.java,v $
// Revision 1.142  2014/05/15 15:19:56  wendy
// RCQ00303672 - add autogen txt files to reports in-basket in JUI
//
// Revision 1.141  2013/03/29 06:53:13  wangyulo
// support RESEND from CACHE and support initialize CACHE after IDL base on BH FS ABR XML System Feed 20121210.doc
//
// Revision 1.140  2012/09/30 07:11:09  guobin
// fix defect 799135 ( RFR data now flowing to BH prod). BH FS ABR XML System Feed20120917.doc
//
// Revision 1.139  2011/08/11 18:53:36  wendy
// RCQ00180384 make turnoffdg more flexible
//
// Revision 1.138  2009/08/06 21:52:26  wendy
// Support turning off DG in properties file
//
// Revision 1.137  2008/12/11 23:18:26  wendy
// Support dg submit on body types that are not html
//
// Revision 1.136  2008/11/06 16:26:52  wendy
// Release memory
//
// Revision 1.135  2006/05/19 22:24:06  joan
// changes
//
// Revision 1.134  2006/05/19 21:54:29  joan
// changes
//
// Revision 1.133  2006/05/19 16:30:21  joan
// add mailneeded
//
// Revision 1.132  2006/03/23 20:25:25  joan
// fixes
//
// Revision 1.131  2006/03/23 19:09:52  joan
// fix compile
//
// Revision 1.130  2006/03/23 19:04:10  joan
// add setCat2
//
// Revision 1.129  2006/03/08 01:28:16  yang
// minor change
//
// Revision 1.128  2006/03/08 01:02:52  yang
// adding ReQueueAbr()
//
// Revision 1.127  2005/09/08 22:48:00  joan
// work on setting blob for DGentity
//
// Revision 1.126  2005/09/07 17:03:02  joan
// set file extension
//
// Revision 1.125  2005/08/05 21:59:26  joan
// test creating dg
//
// Revision 1.124  2005/08/04 14:47:45  joan
// try to create xml file
//
// Revision 1.123  2005/02/01 19:45:58  dave
// some trace for debug
//
// Revision 1.122  2005/01/26 17:53:53  dave
// some fixes
//
// Revision 1.121  2005/01/26 17:47:39  dave
// more JTest reformatting per IBM
//
// Revision 1.120  2005/01/25 23:16:50  dave
// more JTest cleanup
//
// Revision 1.119  2005/01/25 22:34:57  dave
// Jtest Syntax
//
// Revision 1.118  2005/01/25 22:24:35  dave
// JTest clean up effort new formating rules
//
// Revision 1.117  2005/01/24 22:17:53  dave
// and another import fix
//
// Revision 1.116  2005/01/24 22:15:43  dave
// more import fixes
//
// Revision 1.115  2005/01/24 22:10:43  dave
// addressing imports and * IBM Standard
//
// Revision 1.114  2005/01/24 21:58:57  dave
// starting to clean up per new IBM standards
//
// Revision 1.113  2005/01/10 19:58:29  roger
// Log the profile info for ABR
//
// Revision 1.112  2004/10/12 22:46:59  dave
// syntax
//
// Revision 1.111  2004/10/12 22:42:25  dave
// more ABR trace
//
// Revision 1.110  2004/06/09 16:10:14  dave
// fix for praveen ECCM pack_complete
//
// Revision 1.109  2004/06/02 22:35:32  dave
// more and more and more trace
//
// Revision 1.108  2004/05/27 21:04:49  dave
// private to protected
//
// Revision 1.107  2004/05/27 20:47:01  dave
// changed add defaults
//
// Revision 1.106  2004/05/26 18:47:45  dave
// abri was being set to null .. in the finish routine
//
// Revision 1.105  2004/05/05 20:16:31  bala
// sheesh error
//
// Revision 1.104  2004/05/05 20:09:18  bala
// fix compiler error
//
// Revision 1.103  2004/05/05 19:49:28  bala
// change changeStatusABRmethod to call setGroupABRStatus
// when groupABR type of abr is being processed
//
// Revision 1.102  2004/03/23 19:16:31  bala
// change printDgSubmitString to get new abr properities
//
// Revision 1.101  2003/11/24 21:55:26  bala
// fix typo
//
// Revision 1.100  2003/11/24 21:42:08  bala
// print ExtMail and IntMail in DGSubmit string
//
// Revision 1.99  2003/10/25 20:05:46  bala
// commit after save
//
// Revision 1.97  2003/10/25 20:04:01  bala
// trap exception while setting dgcategories
//
// Revision 1.96  2003/10/24 21:45:14  bala
// put back setRptClass in dg save sequence
//
// Revision 1.95  2003/10/23 23:48:21  bala
// remove setRptClass from dg save sequence
//
// Revision 1.94  2003/10/23 23:43:10  bala
// add stacktrace for DG save
//
// Revision 1.93  2003/10/14 19:59:48  bala
// change subject of mail, add debug statements
//
// Revision 1.92  2003/10/08 21:28:01  joan
// add boolean attribute for deactivate abr entity
//
// Revision 1.91  2003/09/18 20:03:00  bala
// new class variable m_strDGRptClass and method to set the value
//
// Revision 1.90  2003/09/17 02:18:17  bala
// add comment
//
// Revision 1.89  2003/09/11 19:30:14  bala
// fix compile error
//
// Revision 1.88  2003/09/11 19:14:17  bala
// call setDgCategories before sending mail
//
// Revision 1.87  2003/08/26 18:03:52  bala
// comment out RPTNAME from printing in printDGSubmitString
//
// Revision 1.86  2003/08/21 21:14:16  joan
// fix compile
//
// Revision 1.85  2003/08/21 20:10:05  bala
// get  printDGSubmitString to print VE name from propfile
//
// Revision 1.84  2003/08/04 22:33:06  bala
// set correct flag code for taskstatus when writing to DGsubmit string
//
// Revision 1.83  2003/08/01 22:55:21  bala
// fix syntax
//
// Revision 1.82  2003/08/01 22:51:57  bala
// fix syntax
//
// Revision 1.81  2003/08/01 22:38:06  bala
// move up the printDGSubmitString here so that it can append
// to the report after execution
//
// Revision 1.80  2003/07/31 00:10:38  bala
// add profile to the list of parameters when calling sendmail
//
// Revision 1.79  2003/07/30 21:05:39  joan
// add some code for DG rpt name
//
// Revision 1.78  2003/07/08 17:01:36  bala
// write error to log
//
// Revision 1.77  2003/07/08 16:41:50  bala
// use StringWriter class in import statement
//
// Revision 1.76  2003/07/07 22:08:09  bala
// Added stack trace to locate sendmail problem
//
// Revision 1.75  2003/07/02 19:41:07  dave
// making changes for flag -> text combo check
//
// Revision 1.74  2003/06/20 23:31:12  bala
// fix typo
//
// Revision 1.73  2003/06/20 23:22:45  bala
// call dgentity sendmail method
//
// Revision 1.72  2003/06/20 21:38:40  bala
// initial plug in of setting category for dgentity
//
// Revision 1.71  2003/06/13 16:28:49  roger
// Remove double space before status
//
// Revision 1.70  2003/06/10 23:16:20  roger
// Change email to BCC
//
// Revision 1.69  2003/05/13 21:48:02  roger
// Order matters ...
//
// Revision 1.68  2003/05/13 21:36:19  roger
// Fixing
//
// Revision 1.67  2003/05/13 21:19:37  roger
// Required imports
//
// Revision 1.66  2003/05/13 21:11:48  roger
// Use the Profile login token as the from/to address on DG email
//
// Revision 1.65  2003/05/13 20:43:07  roger
// Testing
//
// Revision 1.64  2003/05/13 20:29:41  roger
// Testing
//
// Revision 1.63  2003/05/13 16:43:48  roger
// Testing new properties and accessors
//
// Revision 1.62  2003/05/01 21:25:42  roger
// Change FAILURE to FAILED
//
// Revision 1.61  2003/05/01 21:19:55  roger
// Change OK to PASSED for consistency with ABR text
//
// Revision 1.60  2003/05/01 15:46:38  roger
// Clean up
//
// Revision 1.59  2003/04/30 21:13:26  roger
// Include RC in subject of email
//
// Revision 1.58  2003/04/30 20:26:08  roger
// Fix subject of email
//
// Revision 1.57  2003/04/30 17:55:01  roger
// TM will now send email when DG entity inserted
//
// Revision 1.56  2002/11/08 17:23:27  roger
// Why ABR not being changed to Queued?
//
// Revision 1.55  2002/11/07 23:17:40  roger
// Status 0020 is needed
//
// Revision 1.54  2002/11/06 22:56:40  dave
// bad syntax boy!
//
// Revision 1.53  2002/11/06 22:45:34  dave
// Added the tracking of ABR outcomes to the DGEntity, per
// IBM change request
//
// Revision 1.52  2002/11/06 21:50:42  dave
// better syntax error messages here
//
// Revision 1.51  2002/11/06 19:14:19  roger
// Create restart/recovery feature
//
// Revision 1.50  2002/10/02 23:06:10  roger
// CVS change history/log restored
//
//


package COM.ibm.opicmpdh.middleware.taskmaster;


import COM.ibm.eannounce.objects.DGEntity;
import COM.ibm.eannounce.objects.EANUtility;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.objects.SingleFlag;

import COM.ibm.opicmpdh.transactions.OPICMABRItem;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Vector;


/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */
//AbstractTask.java,v
public abstract class AbstractTask implements Runnable {
  private Database m_db = null;
  private int m_iJobID = -1;
  private int m_iReturnCode = AbstractTask.RETURNCODE_FAILURE;
  private long m_lEndTime = 0;
  private long m_lStartTime = 0;
  private String m_strJobName = null;
  private Profile m_prof = null;
  private OPICMABRItem m_abri = null;
  private String m_strOutputFileName = null;
  private DatePackage dpDates = null;
  private byte[] m_attachByteForDG = null;
  private String[] m_astrDGCat2Value = null;
  private String dgtemplate=""; // needed when body is not html
  /**
     * QUEUED
   */
  
  public final static ABRStatus ABR_STATUS_QUEUED = new ABRStatus("0020", "Queued"); //$NON-NLS-1$  //$NON-NLS-2$
  /**
     * PASSED
   */
  public final static ABRStatus ABR_STATUS_PASSED = new ABRStatus("0030", "Passed"); //$NON-NLS-1$  //$NON-NLS-2$
  /**
     * FAILED
   */
  public final static ABRStatus ABR_STATUS_FAILED = new ABRStatus("0040", "Failed"); //$NON-NLS-1$  //$NON-NLS-2$
  /**
   * IN Process
   *
   */
  public final static ABRStatus ABR_STATUS_INPROCESS = new ABRStatus("0050", "In Process"); //$NON-NLS-1$  //$NON-NLS-2$
  
  
  //TODO resendRFR according to BH FS ABR XML System Feed20120917.doc The new RESEND RFR cannot appear as a successful generation of XML for the current STATUS = Final. Hence a new FLAG value of Passed Resend RFR (XMLRFR) is being created for ADS XML Feed ABR (ADSABRSTATUS)
  public final static ABRStatus ABR_STATUS_RESENDRFR = new ABRStatus("XMLRFR", "Passed Resend RFR"); 
  //  RESENDCACHE according to BH FS ABR XML System Feed 20121210.doc
  public final static ABRStatus ABR_STATUS_RESENDCACHE = new ABRStatus("XMLCACHE", "Passed Resend Cache");

  public final static int RETURNCODE_RESENDRFR = 1;
  public final static int RETURNCODE_RESENDCACHE = 2;
  /**
     * RETURNCODE_SUCCESS
   */
  public final static int RETURNCODE_SUCCESS = 0;
  /**
     * RETURNCODE_FAILURE
   */
  public final static int RETURNCODE_FAILURE = -1;


  private String m_strDGEntityName = null;
  private String m_strDGRptName = null;
  private String m_strDGRptClass = null;
  private boolean m_bDeactivateABREntity = false;
  private boolean m_bCreateDGEntity = true;

  /**
   * New Abstract Task
   *
   */
  protected AbstractTask() {
    this("no_job_name", 0);
  }

  /**
   * @param _strJobName
   * @param _iJobID
   */
  protected AbstractTask(String _strJobName, int _iJobID) {
    m_strJobName = _strJobName;
    m_iJobID = _iJobID;
  }
  /**
   * @param _status
   */
  protected final void changeStatusABR(ABRStatus _status) {

    String strNow = null;
    String strForever = null;

    Log.out(this +" changing ABR status to " + _status);

    if (m_abri.getABRQueType().equals("GroupABR")) {
      try {
        m_abri.setGroupABRStatus(
          m_db,
          m_prof.getEnterprise(),
          m_abri.getABRCode(),
          m_abri.getEntityType(),
          m_abri.getEntityID(),
          Integer.valueOf(m_abri.getABRState()).intValue(),
          Integer.valueOf(_status.m_strAttributeValue).intValue(),
          m_abri.getOpenID());

      } catch (Exception x) {
        Log.out(this +" trouble updating GROUP abri status " + x);
      }
    } else {

      int iOPWGID = m_prof.getOPWGID();
      String strEnterprise = m_prof.getEnterprise();
      String strEntityType = m_abri.getEntityType();
      int iEntityID = m_abri.getEntityID();
      String strABRCode = m_abri.getABRCode();
      Vector vctTransactions = new Vector();

      dpDates = new DatePackage(m_db);

      strNow = dpDates.m_strNow;
      strForever = dpDates.m_strForever;

      try {
        ControlBlock cb =
          new ControlBlock(
            strNow,
            strForever,
            strNow,
            strForever,
            iOPWGID);
        ReturnEntityKey rek =
          new ReturnEntityKey(strEntityType, iEntityID, true);
        Vector vctAttributes = new Vector();

        vctAttributes.addElement(
          new SingleFlag(
            strEnterprise,
            strEntityType,
            iEntityID,
            strABRCode,
            _status.m_strAttributeValue,
            1,
            cb));

        rek.m_vctAttributes = vctAttributes;
        vctTransactions.addElement(rek);
        // Change status in database
        m_db.update(m_prof, vctTransactions);
        m_db.commit();
        // Change status in ABR item
        m_abri.setABRState(_status.m_strAttributeValue);
        Log.out(this +"changeStatusABR:"
        +strEnterprise + ":"
        +strEntityType + ":"
        +iEntityID + ":"
        +strABRCode + ":"
        +_status);
      } catch (Exception x) {
        Log.out(this +" trouble updating abri status " + x);
      } finally {
        m_db.freeStatement();
        m_db.isPending("finally after update in AbstractTask");
      }
    }
  }

  /**
  *
  */
  private final void closePrintWriter() {
    m_abri.closePrintWriter();
  }

  /**
   * @param _strType
   */
  protected final void execute(String _strType) {

    Log.out(this +_strType + " begin");
    changeStatusABR(ABR_STATUS_INPROCESS);

    try {
      m_db.freeStatement();
      m_db.isPending("execute begin");
      setStartTime();

      if (_strType.equalsIgnoreCase("run")) {
        execute_run();
      } else {
        execute_restart();
      }
    } catch (Exception x) {
      Log.out(this +" exception trapped in execute " + _strType + " " + x);   //$NON-NLS-2$
      // DWB finding a null pointer
      x.printStackTrace();
    } finally {
      m_db.freeStatement();
      m_db.isPending("execute end");
      setEndTime();
      //TODO 
      /* BH FS ABR XML System Feed20120917.doc 
       * In the preceding logic, a new symbol ABRSTATUSVAR is used to establish which value needs to be set when the code successfully creates XML. The two different values are:
		a.	ABRSTATUSVAR = 鈥淧assed鈥�(0030)
		This is the current value used by the ABR
		b.	ABRSTATUSVAR = 鈥淧assed Resend RFR鈥�(XMLRFR)

       * */
      if (getReturnCode() == AbstractTask.RETURNCODE_SUCCESS) {
        changeStatusABR(ABR_STATUS_PASSED);
      } else if(getReturnCode() == AbstractTask.RETURNCODE_RESENDRFR){
    	  changeStatusABR(ABR_STATUS_RESENDRFR);  
      } else if(getReturnCode() == AbstractTask.RETURNCODE_RESENDCACHE){
    	  changeStatusABR(ABR_STATUS_RESENDCACHE);  
      } else {
        changeStatusABR(ABR_STATUS_FAILED);
      }
      if (m_abri.getReQueueAbr() == true){
          Log.out(this +" " + m_abri.getReQueueAbr() + " m_abri.getReQueueAbr() is true");   //$NON-NLS-2$
        changeStatusABR(ABR_STATUS_QUEUED);
        }
      Log.out(this +" " + _strType + " end");   //$NON-NLS-2$
    }
  }

  /**
  *
  */
  public void execute_restart() {
    Log.out("Default AbstractTask execute_restart method");
  }

  /**
  *
  */
  public void execute_run() {
    Log.out("Default AbstractTask execute_run method");
    Log.out("TMABRPROFILE "
    +"EntityType = "
    +m_abri.getEntityType() + " ID = "
    +m_abri.getEntityID());
    Log.out("TMABRPROFILE " + m_prof.dump(true));
  }

  /**
  * 
  */
  public final void finish() {
    Log.out("Finishing up Abstract Task"+this+"...Start");
    closePrintWriter();

    //Perf - RCQ00180384 - EACM DG Reports reduce generation and storage for PASS- 
    String turnoffDgStr = ABRServerProperties.turnOffDGEntity(m_abri.getABRCode());
    boolean turnoffDg = turnoffDgStr.equalsIgnoreCase("always") || 
    					turnoffDgStr.equalsIgnoreCase("true") ||  // support original value
    					(turnoffDgStr.equalsIgnoreCase("onpass") && wasRunSuccessful());
    
    //if xx_turnOffDG=always then no report will be saved in the pdh and no notification will be done
    //if xx_turnOffDG=onpass then no report will be saved in the pdh if the abr passes
    if(turnoffDg){
    	setCreateDGEntity(false);
    	Log.out(this+" WARNING No DGEntity created. Properties file has _turnOffDG="+turnoffDgStr+" for "+m_abri.getABRCode());
    	// abr code can not turn this back on
    }
        
    if (m_bCreateDGEntity) {
        insertDGEntity(m_db);
    }
    removeOutputFile();

    if (m_bDeactivateABREntity) {
      try {
        EntityItem ei =
          new EntityItem(
            null,
            m_prof,
            m_abri.getEntityType(),
            m_abri.getEntityID());
        EANUtility.deactivateEntity(m_db, m_prof, ei);
      } catch (Exception exc) {
        println("Error in deactivate entity"
        +m_abri.getABRCode() + ":"
        +exc.getMessage());
        println("" + exc);
        exc.printStackTrace();
      }
    }
    Log.out("Finishing up Abstract Task"+this+"...End");
  }

  /**
   * @return String
   */
  public final String getABRDescription() {
    return (m_abri == null) ? ""
    : (m_abri.getOPENID() + ":"
    +m_abri.getEntityType().trim() + ":"
    +m_abri.getEntityID() + " - "
    +m_abri.getABRDescription());
  }

  /**
   * @return OPICMABRItem
   */
  public final OPICMABRItem getABRItem() {
    return m_abri;
  }

  /**
   * @return Database
   */
  public final Database getDatabase() {
    return m_db;
  }

  /**
   * @return long
   */
  public final long getElapsedTime() {

    long lReturn = 0;
    long lNow = System.currentTimeMillis();
    long lStartTime = m_lStartTime;
    long lEndTime = m_lEndTime;

    if (lStartTime > 0) {
      lReturn = lNow - lStartTime;
    }

    if ((lStartTime > 0) && (lEndTime > 0)) {
      lReturn = lEndTime - lStartTime;
    }

    return lReturn;
  }

  /**
   * @return int
   */
  public final int getJobID() {
    return m_iJobID;
  }

  /**
   * @return String
   */
  public final String getJobName() {
    return m_strJobName;
  }

  /**
   * @return PrintWriter
   */
  public final PrintWriter getPrintWriter() {
    return m_abri.getPrintWriter();
  }

  /**
   * @return Profile
   */
  public final Profile getProfile() {
    return m_prof;
  }

  /**
   * @return int
   */
  public final int getReturnCode() {
    return m_iReturnCode;
  }

  /**
   * @param _strNow
   */
  protected final void initPrintWriter(String _strNow) {
    m_strOutputFileName = m_abri.initPrintWriter(_strNow);
  }

  /**
  * Internal method to create the DGentity
  */
  private final void insertDGEntity(Database _db) {

    Log.out(this +" Creating the DG Entity for the ABR");

    try {

      DGEntity dg = new DGEntity(_db, m_prof);
      dg.setFileExtension(getFileExtension());
      FileInputStream fisBlob = new FileInputStream(m_strOutputFileName);
      int iSize = fisBlob.available();
      byte[] baBlob = new byte[iSize];

      Log.out("" + m_prof);
      dg.setTitle(m_strDGEntityName);
      Log.out(this +"DG Title  is " + m_strDGEntityName);
      dg.setRptName(m_strDGRptName);
      Log.out(this +"Report name is " + m_strDGRptName);
      dg.setRptClass(m_strDGRptClass);
      Log.out(this +"DG Class  is " + m_strDGRptClass);

      dg.setType(m_abri.getReportType());

      fisBlob.read(baBlob);
      fisBlob.close();

      if (m_attachByteForDG != null) {
        dg.setBody(m_attachByteForDG);
      } else {
        dg.setBody(baBlob);
      }
      String strStatus = "unknown status ";
        System.out.println("***AbstractTask- getReturnCode(): " + getReturnCode());
        //TODO 
      /*  
       *BH FS ABR XML System Feed20120917.doc 
       * In the preceding logic, a new symbol ABRSTATUSVAR is used to establish which value needs to be set when the code successfully creates XML. The two different values are:
		a.	ABRSTATUSVAR = 鈥淧assed鈥�(0030)
		This is the current value used by the ABR
		b.	ABRSTATUSVAR = 鈥淧assed Resend RFR鈥�(XMLRFR)
		*/  
      if (getReturnCode() == AbstractTask.RETURNCODE_SUCCESS || getReturnCode() ==AbstractTask.RETURNCODE_RESENDRFR || getReturnCode() ==AbstractTask.RETURNCODE_RESENDCACHE ) {
        dg.setTaskStatus(DGEntity.DG_TASKSTATUS_TSPA);
        strStatus = "PASSED";
      } else {
        dg.setTaskStatus(DGEntity.DG_TASKSTATUS_TSFAIL);
        strStatus = "FAILED";
      }
      dg.save(_db);
      /*
      *Set the dgCategories in the dgentity to generate the support info for the subscription
      */
      EntityItem[] eiRoot =
        {
           new EntityItem(
            null,
            getProfile(),
            m_abri.getEntityType(),
            m_abri.getEntityID())};
      //Create a new entity array
      try {
        if (m_astrDGCat2Value != null && m_astrDGCat2Value.length > 0) {
			dg.setCat2(m_astrDGCat2Value);
		}

        dg.setDGInfo(dgtemplate); //needed when body is not html
        dg.setDGCategories(_db, getProfile(), eiRoot);
      
      } catch (Exception ex) {
        StringWriter writer = new StringWriter();
        String str = null;

        Log.out("trouble setting the categories " + ex);
        ex.printStackTrace(new PrintWriter(writer));
        str = writer.toString();
        Log.out(str);

      }

	System.out.println("AbstractTask isMailNeeded(): " + isMailNeeded());
      if (isMailNeeded()) {
	System.out.println("AbstractTask sending mail");
  	    try {
  	      dg.setAttachmentFiles(ABRServerProperties.getDGAttachmentFiles(m_abri.getABRCode()));//RCQ00303672
  	      dg.sendMail(_db, m_prof, m_strDGRptName + " " + strStatus + " " + m_strDGEntityName,   //$NON-NLS-2$
  	      m_strOutputFileName);
  	    } catch (Exception e) {
  	      StringWriter writer = new StringWriter();
  	      String x = null;
  	      Log.out("trouble sending mail " + e);
  	      e.printStackTrace(new PrintWriter(writer));
  	      x = writer.toString();
  	      Log.out(x);
  	    }
  	  }
    } catch (Exception x) {
      Log.out(this +" trouble committing DG Entity " + x);
      x.printStackTrace();
    }

  }

  /**
   * @param _strMessage
   */
  public final void logMessage(String _strMessage) {
    Log.out(this +" " + _strMessage);
  }

  /**
   * @param _strLine
   */
  public final void print(String _strLine) {
    m_abri.print(_strLine);
  }

  /**
   * @param _strLine
   */
  public final void println(String _strLine) {
    m_abri.println(_strLine);
  }

  /**
  *
  */
  private final void removeOutputFile() {
    if (!m_abri.getKeepFile()) {
      m_abri.removeOutputFile();
    }
  }

  /**
  *
  */
  public final void restart() {
    execute("restart");
  }

  /**
  *
  */
  public final void run() {
    execute("run");
  }

  /**
   * @param _abri
   */
  protected final void setABRItem(OPICMABRItem _abri) {
    m_abri = _abri;
  }

  /**
   * This sets the member Database object to what was passed
   *
   * @param _db
   */
  protected final void setDatabase(Database _db) {
    m_db = _db;
  }

  /**
  * set/resets the Defaults of an Abstract Task
  */
  protected final void setDefaults() {
    this.setJobName("");
    this.setJobID(0);
    this.setProfile(null);
    this.setDatabase(null);
  }

  /**
   * @param _strName
   */
  protected final void setDGTitle(String _strName) {
    m_strDGEntityName = _strName;
  }

  /**
   * @param _strRptName
   */
  protected final void setDGRptName(String _strRptName) {
    m_strDGRptName = _strRptName;
  }

  /**
   * @param _strRptClass
   */
  protected final void setDGRptClass(String _strRptClass) {
    m_strDGRptClass = _strRptClass;
  }

  /**
   * @param _strRptClass
   */
  protected final void setDGCat2(String[] _astrCat2Value) {
    m_astrDGCat2Value = _astrCat2Value;
  }

  /**
  *
  */
  protected final void setEndTime() {
    m_lEndTime = System.currentTimeMillis();
  }

  /**
   * @param _iJobID
   */
  protected final void setJobID(int _iJobID) {
    m_iJobID = _iJobID;
  }

  /**
   * @param _strJobName
   */
  protected final void setJobName(String _strJobName) {
    m_strJobName = _strJobName;
  }

  /**
   * @param _pw
   */
  protected final void setPrintWriter(PrintWriter _pw) {
    m_abri.setPrintWriter(_pw);
  }

  /**
   * @param _prof
   */
  protected final void setProfile(Profile _prof) {
    m_prof = _prof;
  }

  /**
   * @param _iReturnCode
   */
  protected final void setReturnCode(int _iReturnCode) {
    m_iReturnCode = _iReturnCode;
  }

  /**
  *
  */
  protected final void setStartTime() {
    m_lStartTime = System.currentTimeMillis();
  }

  /**
   * returns the string representation of the ABR
   *
   * @return String
   */
  public final String toString() {
    String strResult = "AT";

    if (m_iJobID >= 0) {
      strResult = "[" + m_strJobName + "(" + m_iJobID + ")" + "]";   //$NON-NLS-2$  //$NON-NLS-3$  //$NON-NLS-4$
    }

    return strResult;
  }

  /**
  * This prints the  specifics of a DB Submit String
  */
  public void printDGSubmitString() {	  
    String strPrintString = null;
    String strReport = m_abri.getABRCode();
    println("<!--DGSUBMITBEGIN");
    StringBuffer sb = new StringBuffer("<!--DGSUBMITBEGIN\n");
    print("TASKSTATUS=");
    sb.append("TASKSTATUS=");

    if (getReturnCode() == AbstractTask.RETURNCODE_SUCCESS) {
      println("TSPA");
      sb.append("TSPA\n");
    } else {
      println("TSFAIL");
      sb.append("TSFAIL\n");
    }

    println("SUBSCRVE=" + ABRServerProperties.getSubscrVEName(strReport));
    sb.append("SUBSCRVE=" + ABRServerProperties.getSubscrVEName(strReport)+"\n");
    
    strPrintString = ABRServerProperties.getCategory(strReport, "CAT1");
    if (strPrintString != null) {
      println("CAT1=" + strPrintString);
      sb.append("CAT1=" + strPrintString+"\n");
    }
    strPrintString = ABRServerProperties.getCategory(strReport, "CAT2");
    if (strPrintString != null) {
      println("CAT2=" + strPrintString);
      sb.append("CAT2=" + strPrintString+"\n");
    }

    strPrintString = ABRServerProperties.getCategory(strReport, "CAT3");
    if (strPrintString != null) {
      println("CAT3=" + strPrintString);
      sb.append("CAT3=" + strPrintString+"\n");
    }

    strPrintString = ABRServerProperties.getCategory(strReport, "CAT4");
    if (strPrintString != null) {
      println("CAT4=" + strPrintString);
      sb.append("CAT4=" + strPrintString+"\n");
    }

    strPrintString = ABRServerProperties.getCategory(strReport, "CAT5");
    if (strPrintString != null) {
      println("CAT5=" + strPrintString);
      sb.append("CAT5=" + strPrintString+"\n");
    }

    strPrintString = ABRServerProperties.getCategory(strReport, "CAT6");
    if (strPrintString != null) {
      println("CAT6=" + strPrintString);
      sb.append("CAT6=" + strPrintString+"\n");
    }

    strPrintString = ABRServerProperties.getExtMail(strReport);
    if (strPrintString != null) {
      println("EXTMAIL=" + strPrintString);
      sb.append("EXTMAIL=" + strPrintString+"\n");
    }

    strPrintString = ABRServerProperties.getIntMail(strReport);
    if (strPrintString != null) {
      println("INTMAIL=" + strPrintString);
      sb.append("INTMAIL=" + strPrintString+"\n");
    }

    strPrintString = ABRServerProperties.getSubscrEnabled(strReport);
    if (strPrintString != null) {
      println("SUBSCR_ENABLED=" + strPrintString);
      sb.append("SUBSCR_ENABLED=" + strPrintString+"\n");
    }

    strPrintString = ABRServerProperties.getSubscrNotifyOnError(strReport);
    if (strPrintString != null) {
      println("SUBSCR_NOTIFY_ON_ERROR=" + strPrintString);
      sb.append("SUBSCR_NOTIFY_ON_ERROR=" + strPrintString+"\n");
    }

    println("DGSUBMITEND-->");
    sb.append("DGSUBMITEND-->\n");
    dgtemplate = sb.toString(); //needed when body is not html
  }

  /**
  * Returns true if the run was successfull
  * @return true if run was successful
  */
  public final boolean wasRunSuccessful() {
    return (m_iReturnCode == AbstractTask.RETURNCODE_SUCCESS);
  }

    /**
     * @return
     * @author Dave
     */
    public final boolean shouldDeactivate() {
        return m_bDeactivateABREntity;
    }

    protected final void setDeactivateABREntity(boolean _b) {
        m_bDeactivateABREntity = _b;
    }

    protected final void setCreateDGEntity(boolean _b) {
        m_bCreateDGEntity = _b;
    }


  /**
   * Return the date/time this class was generated
   * @return the date/time this class was generated
   */
  public static String getVersion() {
    return "$Id: AbstractTask.java,v 1.142 2014/05/15 15:19:56 wendy Exp $";
  }

  public final String getFileExtension() {
      if (m_abri == null) return "";
      return m_abri.getFileExtension();
  }

  public final void setAttachByteForDG(byte[] _ba) {
      m_attachByteForDG = _ba;
  }

  public final boolean isMailNeeded() {
      if (m_abri == null) return false;
      return m_abri.getMailNeeded();
  }
  
  /**
   * release memory 
   */
  public void dereference(){
	  m_db = null;
	  m_strJobName = null;
	  m_prof = null;
	  m_abri = null;
	  m_strOutputFileName = null;
	  dpDates = null;
	  m_attachByteForDG = null;
	  m_astrDGCat2Value = null;
	  m_strDGEntityName = null;
	  m_strDGRptName = null;
	  m_strDGRptClass = null;
  }
}
