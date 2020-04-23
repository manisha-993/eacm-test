package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.eannounce.objects.EntityItem;

//$Log: SEOCGOSSVCSEOABRSTATUS.java,v $
//Revision 1.3  2011/04/22 07:17:06  guobin
//queue COMPATGENABR
//
//Revision 1.2  2011/02/10 12:59:56  lucasrg
//Added CVS Log annotation
//

/**
 * SEOCGOSSVCSEO ABR Class
 * 
 * Set (from BH FS ABR Data Qualtity Sets 20110113b.xls)
 * 213,00 R1.0 SEOCGOSSVCSEO 
 * 214,00 R1.0 SET SEOCGOSSVCSEO
 * 215,00 R1.0 END
 * 
 * SEOCGOSSVCSEOABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.SEOCGOSSVCSEOABRSTATUS
 * SEOCGOSSVCSEOABRSTATUS_enabled=true
 * SEOCGOSSVCSEOABRSTATUS_idler_class=A
 * SEOCGOSSVCSEOABRSTATUS_keepfile=true
 * SEOCGOSSVCSEOABRSTATUS_report_type=DGTYPE01
 * 
 * @author lucasrg
 * 
 */
public class SEOCGOSSVCSEOABRSTATUS extends DQABRSTATUS {

	/**********************************
	 * complete abr processing after status moved to readyForReview; (status was chgreq)
	 */
	protected void completeNowR4RProcessing() throws java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException,
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException {
		setFlagValue(m_elist.getProfile(),"COMPATGENABR", getRFRQueuedValue("COMPATGENABR"));
	}

	/**********************************
	 * complete abr processing after status moved to final; (status was r4r)
	 */
	protected void completeNowFinalProcessing() throws java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException,
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException {
		addDebug(" status now final");
		setFlagValue(m_elist.getProfile(),"COMPATGENABR", getQueuedValue("COMPATGENABR"));
	}

	/**********************************
	 * nothing needed
	 */
	protected boolean isVEneeded(String statusFlag) {
		return false;
	}

	protected void doDQChecking(EntityItem rootEntity, String statusFlag)
			throws Exception {
		addDebug("No checking required");
	}

	public String getDescription() {
		return "SEOCGOSSVCSEO ABR";
	}

	/***********************************************
	    *  Get the version
	    *
	    *@return java.lang.String
	    */
	public String getABRVersion() {
		return "$Revision: 1.3 $";
	}
}
