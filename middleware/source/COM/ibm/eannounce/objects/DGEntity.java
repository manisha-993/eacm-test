//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: DGEntity.java,v $
// Revision 1.132  2014/11/18 20:18:46  wendy
// Populate DGENTITY.CREATEDBY attribute - WI 332259
//
// Revision 1.131  2014/07/24 21:10:18  wendy
// RCQ00303672 - provide way to turn off all email and RCQ00316123 use attachment txt file name for save dialog in the in-basket
//
// Revision 1.130  2014/05/15 15:19:04  wendy
// RCQ00303672 - add autogen txt files to reports in-basket in JUI
//
// Revision 1.129  2009/06/12 19:09:43  wendy
// Allow sendmail from application ids
//
// Revision 1.128  2008/12/11 23:18:09  wendy
// Support dg submit on body types that are not html
//
// Revision 1.127  2008/12/10 15:33:15  wendy
// Support getting abr categories on multiple root types
//
// Revision 1.126  2008/11/06 17:48:32  wendy
// Attempt to find correct rpt name len to avoid exception
//
// Revision 1.125  2008/02/01 22:10:07  wendy
// Cleanup RSA warnings
//
// Revision 1.124  2008/01/31 22:56:00  wendy
// Cleanup RSA warnings
//
// Revision 1.123  2006/06/23 15:29:29  joan
// checking for end of dg
//
// Revision 1.122  2006/03/23 19:04:10  joan
// add setCat2
//
// Revision 1.121  2006/02/20 21:39:45  joan
// clean up System.out.println
//
// Revision 1.120  2005/09/07 17:03:01  joan
// set file extension
//
// Revision 1.119  2005/08/04 15:53:12  joan
// work on xml
//
// Revision 1.118  2005/08/04 14:47:45  joan
// try to create xml file
//
// Revision 1.117  2005/02/16 18:26:07  bala
// change the startfile break tag to gml comment
//
// Revision 1.116  2005/02/14 17:18:33  dave
// more jtest fixing
//
// Revision 1.115  2005/02/10 17:17:49  bala
// correct filebreaktag for searching
//
// Revision 1.114  2005/02/10 04:33:12  bala
// some more fixes for sending attachments
//
// Revision 1.113  2005/02/09 19:46:45  bala
// throw exception
//
// Revision 1.112  2005/02/09 19:38:03  bala
// fix typo
//
// Revision 1.111  2005/02/09 19:30:55  bala
// Adding capacity to create multiple attachments during mail
// send using html comment tags
//
// Revision 1.110  2005/01/24 22:49:26  bala
// Add date-timestamp to create date
//
// Revision 1.109  2005/01/18 21:33:09  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.108  2005/01/12 22:20:59  joan
// remove dump entitylist
//
// Revision 1.107  2004/11/17 01:06:03  gregg
// adding ":C" for rst.get,put
//
// Revision 1.106  2004/03/24 00:07:28  bala
// fix compiler error
//
// Revision 1.105  2004/03/23 23:30:16  bala
// Add subscription option REQUIRED/NONE/OPTIONAL and
// NotifyLastEditor YES/NO
//
// Revision 1.104  2004/01/09 00:55:51  bala
// return UniqueEmailId's before sending
//
// Revision 1.103  2003/12/11 22:30:16  bala
// comma separate values from multiple entity items in the getAttributevalue method
//
// Revision 1.102  2003/12/11 21:24:36  bala
// fix ls subscription problems
//
// Revision 1.101  2003/12/11 20:32:37  bala
// debugging extmail
//
// Revision 1.100  2003/11/24 23:12:45  bala
// fix compile errors
//
// Revision 1.99  2003/11/24 22:54:06  bala
// add ExtEmail and IntEmail logic
//
// Revision 1.98  2003/11/21 19:32:22  bala
// report when no entityItems found
//
// Revision 1.97  2003/11/20 21:24:05  bala
// display version when created, display entitytype and id with attrcode when extracting from VE
//
// Revision 1.96  2003/11/06 19:32:03  bala
// More Debug info added for subscription
//
// Revision 1.95  2003/11/03 23:57:06  bala
// add debug statements for external email address
//
// Revision 1.94  2003/10/30 21:28:44  bala
// truncate title text to 64 chars when its longer
//
// Revision 1.93  2003/10/30 01:50:53  bala
// fix compile error
//
// Revision 1.92  2003/10/30 01:40:10  bala
// sendmail, ensure that person who triggered will get email on failure when subscribers are found, no email to be sent to person in nosubgrpemail but add to inbasket
//
// Revision 1.91  2003/10/28 17:46:09  bala
// set default title when spaces are passed to it
//
// Revision 1.90  2003/10/25 23:31:30  bala
// dont need the the entitygroup dump
//
// Revision 1.89  2003/10/24 21:44:37  bala
// dont return null when nothing found
//
// Revision 1.88  2003/10/23 21:31:31  bala
// Skip if CAt is not assigned any value
//
// Revision 1.87  2003/10/23 16:59:39  bala
// fix rptclass to set/pull flags instead of text
//
// Revision 1.86  2003/10/22 23:44:10  bala
// fix compile error
//
// Revision 1.85  2003/10/22 23:33:48  bala
// add logic to get external email address without submitting to queue
//
// Revision 1.84  2003/10/15 19:05:37  bala
// fix compiler error
//
// Revision 1.83  2003/10/15 18:48:31  bala
// fix compiler error
//
// Revision 1.82  2003/10/15 18:47:46  bala
// fix compiler error
//
// Revision 1.81  2003/10/15 18:15:45  bala
// send mail to abr editor when taskfailed
//
// Revision 1.80  2003/09/18 20:02:07  bala
// adding methods to put and get value of the attribute RPTCLASs
//
// Revision 1.79  2003/09/17 18:46:24  bala
// change the Que name from DGQUEUE to DGWORKQUEUE
//
// Revision 1.78  2003/09/17 17:01:52  bala
// get the DGEntity from WGDGentity when getting
// Entityid
//
// Revision 1.77  2003/09/17 04:47:22  bala
// missed the break in the for
//
// Revision 1.76  2003/09/17 04:30:25  bala
// one more
//
// Revision 1.75  2003/09/17 04:29:23  bala
// fix typo
//
// Revision 1.74  2003/09/17 04:17:21  bala
// try get the 'real' entityid after save
//
// Revision 1.73  2003/09/17 02:19:27  bala
// get Flagcode for taskstatus
//
// Revision 1.72  2003/09/16 18:06:50  bala
// detect TASKSTATUS and get the correct value from DGEntity
// during setDGCategories
//
// Revision 1.71  2003/09/11 22:46:38  bala
// take care of a case where RPTSTATUS is passed as attributecode in setdgcategories
//
// Revision 1.70  2003/09/08 20:02:36  bala
// plug in the piece where the deliverables are submitted to
// the subscribers workflow queue.
//
// Revision 1.69  2003/08/25 23:50:50  bala
// fix typo
//
// Revision 1.68  2003/08/25 23:25:12  bala
// fix compile error
//
// Revision 1.67  2003/08/25 22:52:34  bala
// get Actual Flag code in getAttributes than longdescription
//
// Revision 1.66  2003/08/25 20:15:11  bala
// get rid of noSuchElementexception error
//
// Revision 1.65  2003/08/21 21:40:12  bala
// change setDGCategories to accept entityItem array as parm
//
// Revision 1.64  2003/08/21 00:39:23  bala
// Added setDGCategories and supporting methods to
// extract the Categories from the VE and save it in the
// DGentity
//
// Revision 1.63  2003/08/12 18:14:24  bala
// syntax fix
//
// Revision 1.62  2003/08/12 17:57:32  bala
// Change to calling gbl9118 instead of 8119 and 8117.
// Save DGEntity after setting the Categories
//
// Revision 1.61  2003/08/05 17:20:25  bala
// go to end of report if DGSubmit tag not found
//
// Revision 1.60  2003/08/04 23:16:19  bala
// syntax fix
//
// Revision 1.59  2003/08/04 22:31:40  bala
// add clean up code for the dynasearch table, add search of SUBSCRGRPNOMAIL before mail sent
//
// Revision 1.58  2003/08/01 23:59:42  bala
// fix syntax again!
//
// Revision 1.57  2003/08/01 23:47:53  bala
// method to set text attributes
//
// Revision 1.56  2003/08/01 23:07:42  bala
// fix typo
//
// Revision 1.55  2003/08/01 22:55:44  bala
// syntax fix
//
// Revision 1.54  2003/08/01 22:37:05  bala
// fix setDGParameters so that it can pick up the DGSubmit tag
// at the end of the report
//
// Revision 1.53  2003/08/01 01:25:53  bala
// fine tune the DGSubmit parser
//
// Revision 1.52  2003/07/31 23:26:37  bala
// fix syntax
//
// Revision 1.51  2003/07/31 22:57:26  bala
// add methods to parse out the DGsubmit string from the blob
//
// Revision 1.50  2003/07/31 00:05:53  bala
// add the category to operator search sequence
//
// Revision 1.49  2003/07/30 21:05:39  joan
// add some code for DG rpt name
//
// Revision 1.48  2003/07/15 23:06:49  bala
// missing semicolon
//
// Revision 1.47  2003/07/15 22:57:03  bala
// fix getBodyBytes
//
// Revision 1.46  2003/07/15 22:22:37  bala
// change setCat1 method and add debug statements
//
// Revision 1.45  2003/07/15 22:05:35  dave
// final override
//
// Revision 1.44  2003/07/15 21:34:58  dave
// trace mail
//
// Revision 1.43  2003/07/09 21:24:03  bala
// more debug...why doesnt m_rst return entityitem with attr?
//
// Revision 1.42  2003/07/08 21:50:38  bala
// fix typo
//
// Revision 1.41  2003/07/08 21:35:31  bala
// more debug...why null pointer?
//
// Revision 1.40  2003/07/08 18:11:15  bala
// Change getBodyBytes method
//
// Revision 1.39  2003/07/07 18:06:43  bala
// complete send mail sequence by picking up byte array from
// stored blob
//
// Revision 1.38  2003/06/20 23:14:50  bala
// adding throws
//
// Revision 1.37  2003/06/20 23:05:45  bala
// comment out the from and to addresses for now
//
// Revision 1.36  2003/06/20 22:49:08  bala
// adding import statement
//
// Revision 1.35  2003/06/20 22:41:38  bala
// Plug in sendmail into dgentity
//
// Revision 1.34  2003/06/20 21:21:53  bala
// fix typo
//
// Revision 1.33  2003/06/20 20:58:21  bala
// adding CAT1 Attribute and associated methods for abr processing
//
// Revision 1.32  2003/05/29 18:53:34  dave
// clean up and removed the null from the table def
//
// Revision 1.31  2003/04/24 18:32:15  dave
// getting rid of traces and system out printlns
//
// Revision 1.30  2002/11/26 21:50:13  dave
// comment out default setting
//
// Revision 1.29  2002/11/12 17:18:26  dave
// System.out.println clean up
//
// Revision 1.28  2002/11/06 22:45:33  dave
// Added the tracking of ABR outcomes to the DGEntity, per
// IBM change request
//
// Revision 1.27  2002/11/05 18:50:18  dave
// Syntax
//
// Revision 1.26  2002/11/05 18:19:47  dave
// adding CREATEDATE to DGEntity
//
// Revision 1.25  2002/10/15 18:11:03  dave
// found a place where c_bBusyDB needs to be set prior
// to throwing an MiddlewareWaitTimeOut exception
//
// Revision 1.24  2002/10/04 20:26:00  dave
// re-arrange the order so WGID and OPID are correct
//
// Revision 1.23  2002/10/04 17:59:33  dave
// System.out.messages
//
// Revision 1.22  2002/10/02 20:08:21  dave
// adding DGSubscription to the Mix
//
// Revision 1.21  2002/09/19 20:23:55  dave
// sin tax error
//
// Revision 1.20  2002/09/19 20:08:43  dave
// do not allow the user to pass a null for setTitle
//
// Revision 1.19  2002/09/17 18:34:56  dave
// fix
//
// Revision 1.18  2002/09/17 17:22:59  dave
// changes to DGEntity for Status Setting
//
// Revision 1.17  2002/09/13 18:44:40  dave
// tracing enabled for DGentity in setType
//
// Revision 1.16  2002/09/12 21:11:30  dave
// simplifying the constructor of DGEntity
//
// Revision 1.15  2002/09/11 22:52:52  dave
// role sec bypass on put for DGEntity
//
// Revision 1.14  2002/09/09 17:55:01  dave
// casting error
//
// Revision 1.13  2002/09/09 17:47:07  dave
// syntax fixes
//
// Revision 1.12  2002/09/09 17:36:08  dave
// attempting to fix DGEntity and Flag information
//
// Revision 1.11  2002/09/06 22:27:45  dave
// syntax errors
//
// Revision 1.10  2002/09/06 22:14:37  joan
// fix compile errors
//
// Revision 1.9  2002/09/06 21:53:59  dave
// DGEntity Changes
//
// Revision 1.8  2002/09/05 21:49:29  dave
// added parm for GBL8007
//
// Revision 1.7  2002/09/05 20:18:50  dave
// syntax
//
// Revision 1.6  2002/09/05 20:08:34  dave
// more DGEntity Stuff
//
// Revision 1.5  2002/09/05 19:59:52  dave
// syntax fix
//
// Revision 1.4  2002/09/05 19:57:13  dave
// new SP just for the DGEntity
//
// Revision 1.3  2002/08/29 21:52:56  dave
// added required default title in contrsuctor
//
// Revision 1.2  2002/08/28 20:56:33  dave
// minor cosmetic  changes
//
// Revision 1.1  2002/08/28 20:42:39  dave
// new object to manage the DGEntity creation and saving
//

package COM.ibm.eannounce.objects;

import java.util.*;

import java.sql.SQLException;
import javax.mail.internet.InternetAddress;
import javax.mail.Message;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import java.sql.ResultSet;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareMail;
import COM.ibm.opicmpdh.transactions.OPICMBlobValue;

// This guy manages the entire DG Object

/**
 *  Description of the Class
 *
 *@author     Administrator
 *@created    August 12, 2003
 */
public class DGEntity extends EANMetaEntity {

	final static long serialVersionUID = 20011106L;
	// This object needs to be present in the meta in order for this to work
	/**
	 *  Description of the Field
	 */
	public final static String CAI_KEY = "CRWGDGENTITY";
	/**
	 *  Description of the Field
	 */
	public final static String DG_TYPE_DGTYPE01 = "DGTYPE01";
	/**
	 *  Description of the Field
	 */
	public final static String DG_STATUS_DGST01 = "DGST01";
	/**
	 *  Description of the Field
	 */
	public final static String DG_SUBSCRIPTION_DGS00 = "DGS00";
	/**
	 *  Description of the Field
	 */
	public final static String DG_TASKSTATUS_TSFAIL = "TSFAIL";
	/**
	 *  Description of the Field
	 */
	public final static String DG_TASKSTATUS_TSPA = "TSPA";
	/**
	 *  Description of the Field
	 */
	public final static String DG_TASKSTATUS_TSREPORT = "TSREPORT";
	private RowSelectableTable m_rst;
	//    private RowSelectableTable m_rst1;
	private CreateActionItem m_cai;

	private int m_iWGID = 0;
	private int m_iOPWGID = 0;
	//    private int m_iOPID = 0;

	private String m_strEnterprise;
	private EntityGroup m_eg = null;
	private String m_strExternalEmail = "";
	private String m_strInternalEmail = "";
	private int[] m_iIDs = null;
	private String m_strSubscription = "";
	private String m_strNotifyOnError = "";
	private String m_strFileExtension = "";
	private String m_strDgInfo=""; // needed when body is not html
	private HashSet attachFileSet = null; //RCQ00303672

	/**
	 *  Main method which performs a simple test of this class
	 *
	 *@param  arg  Description of the Parameter
	 */
	public static void main(String arg[]) {
	}

	/*
	 * Version info
	 */
	/**
	 *  Gets the version attribute of the DGEntity object
	 *
	 *@return    The version value
	 */
	public String getVersion() {
		return "$Id: DGEntity.java,v 1.132 2014/11/18 20:18:46 wendy Exp $";
	}

	/**
	 *  This represents an Creating a new DGEntity From Scratch This gets stored
	 *  and hung off the profile's work group Pretty tricky egh?
	 *
	 *@param  _db                             Description of the Parameter
	 *@param  _prof                           Description of the Parameter
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	public DGEntity(Database _db, Profile _prof) throws MiddlewareRequestException {

		super(null, _prof, "NOKEY");
		D.ebug(D.EBUG_SPEW,"Creating a new DGENTITY:" + getVersion());
		m_iWGID = _prof.getWGID();
		m_iOPWGID = _prof.getOPWGID();
		//     m_iOPID = _prof.getOPID();
		m_strEnterprise = _prof.getEnterprise();

		try {

			// Get all the pieces you need to create an existing DG Entity

			DatePackage dpDates = new DatePackage(_db);

			String strNow = dpDates.m_strNow;

			m_cai = new CreateActionItem(this, _db, _prof, CAI_KEY);

			EntityItem eiWG = new EntityItem(null, _prof, "WG", _prof.getWGID());
			EntityItem[] aeiWG = { eiWG };
			EntityList elDGEntity = _db.getEntityList(_prof, m_cai, aeiWG);
			EntityGroup eg = elDGEntity.getEntityGroup("WGDGENTITY");
			m_rst = eg.getEntityGroupTable();
			D.ebug(D.EBUG_SPEW,"DGEntity:Creating entitygroup for DGEntity");
			m_eg = new EntityGroup(null, _db, _prof, "DGENTITY", "Create"); //Need this for future updates

			setTitle("Deliverables Generator Document Default Title");
			//setStatus(DGEntity.DG_STATUS_DGST01);
			//setSubscription(DGEntity.DG_SUBSCRIPTION_DGS00);
			setCreateDate(strNow); //CR 5629 Add date-timestamp to create date
			setCreatedBy(_prof.getOPName()); // IN4221666 -WI332259
			// We now have everything...

		} catch (Exception x) {
			System.out.println("DGEntity exception: " + x);
			x.printStackTrace();
		} finally {
			_db.freeStatement();
			_db.isPending();
		}
	}

	//
	// ACCESSORS
	//

	/**
	 *  Gets the entityID attribute of the DGEntity object
	 *
	 *@return    The entityID value
	 */
	public int getEntityID() {
		EntityItem eiwdg = null;
		EntityItem eidg = null;
		for (int r = 0; r < m_rst.getRowCount(); r++) {
			if (m_rst.getRow(r) instanceof EntityItem) {
				eiwdg = (EntityItem) m_rst.getRow(r);
				eidg = (EntityItem) eiwdg.getDownLink(0); //Get the DGEntity from WGDGEntity
				break;
			}
		}

		return (eidg != null) ? eidg.getEntityID() : 0;
	}

	/**
	 *  Gets the title attribute of the DGEntity object
	 *
	 *@return    The title value
	 */
	public String getTitle() {
		return (String) m_rst.get(0, m_rst.getColumnIndex("DGENTITY:NAME:C"));
	}

	/**
	 *  Gets the bodyBytes attribute of the DGEntity object
	 *
	 *@return    The bodyBytes value
	 */
	public byte[] getBodyBytes() {
		EntityItem ei = (EntityItem) m_rst.getRow(0);
		EANBlobAttribute eba = (EANBlobAttribute) m_rst.getEANObject(0, m_rst.getColumnIndex("DGENTITY:DGBLOB:C"));
		if (eba == null) {
			D.ebug(D.EBUG_SPEW,"DGEntity:******************************DGBLOB not found...printing all attributes");
			for (int i = 0; i < ei.getAttributeCount(); i++) {
				D.ebug(D.EBUG_SPEW,i + " :" + ei.getAttribute(i).getAttributeCode());
			}
			return null;
		}
		byte[] obv = eba.getBlobValue();
		return obv;
	}

	/**
	 *  Gets the types attribute of the DGEntity object
	 *
	 *@return    The types value
	 */
	public String getTypes() {
		return (String) m_rst.get(0, m_rst.getColumnIndex("DGENTITY:DGTYPE:C"), true);
	}

	/**
	 *  Gets the rptName attribute of the DGEntity object
	 *
	 *@return    The rptName value
	 */
	public String getRptName() {
		return (String) m_rst.get(0, m_rst.getColumnIndex("DGENTITY:RPTNAME:C"), true);
	}

	/**
	 *  Gets the dGStatuses attribute of the DGEntity object
	 *
	 *@return    The dGStatuses value
	 */
	public String getDGStatuses() {
		return (String) m_rst.get(0, m_rst.getColumnIndex("DGENTITY:DGSTATUS:C"), true);
	}

	/**
	 *  Gets the subscriptions attribute of the DGEntity object
	 *
	 *@return    The subscriptions value
	 */
	public String getSubscriptions() {
		return (String) m_rst.get(0, m_rst.getColumnIndex("DGENTITY:DGSUBSCRIPTION:C"), true);
	}

	/**
	 *  Gets the taskStatus attribute of the DGEntity object
	 *
	 *@return    The taskStatus value
	 */
	public String getTaskStatus() {
		return (String) m_rst.get(0, m_rst.getColumnIndex("DGENTITY:TASKSTATUS:C"), true);
	}

	public String getTaskStatusFlagCode() {
		return (String) m_rst.get(0, m_rst.getColumnIndex("DGENTITY:TASKSTATUS:C"), true);
	}

	/**
	 *  Gets the createDate attribute of the DGEntity object
	 *
	 *@return    The createDate value
	 */
	public String getCreateDate() {
		return (String) m_rst.get(0, m_rst.getColumnIndex("DGENTITY:CREATEDATE:C"));
	}

	/**
	 *  Gets the dGFlagAttribute attribute of the DGEntity object
	 *
	 *@param  _strFlagAttribute  Description of the Parameter
	 *@return                    The dGFlagAttribute value
	 */
	public EANFlagAttribute getDGFlagAttribute(String _strFlagAttribute) {
		if (m_rst == null) {
			D.ebug(D.EBUG_SPEW,"DGEntity:getDGFlagAttribute:RST is null");
		}
		EntityItem ei = getDGEntityItem();
		D.ebug(D.EBUG_SPEW,"DGEntity:getDGFlagAttribute:Getting Flag Attribute " + _strFlagAttribute+
				" for "+ ei.getKey());
		return (EANFlagAttribute) ei.getEANObject("DGENTITY:" + _strFlagAttribute + ":C");
		//return (EANFlagAttribute) m_rst.getEANObject(0, m_rst.getColumnIndex("DGENTITY:" + _strFlagAttribute));
	}

	/**
	 *  Gets the dginfo needed when body is not html
	 *
	 *@return    dginfo 
	 */
	public String getDgInfo() {
		return m_strDgInfo;
	}

	/**
	 *  Gets the cat1 attribute of the DGEntity object
	 *
	 *@return    The cat1 value
	 */
	public EANFlagAttribute getCat1() {
		return (EANFlagAttribute) m_rst.getEANObject(0, m_rst.getColumnIndex("DGENTITY:CAT1:C"));
	}    

	/**
	 *  Gets the cat2 attribute of the DGEntity object
	 *
	 *@return    The cat1 value
	 */
	public EANFlagAttribute getCat2() {
		return (EANFlagAttribute) m_rst.getEANObject(0, m_rst.getColumnIndex("DGENTITY:CAT2:C"));
	}

	/**
	 *  Gets the dGStatus attribute of the DGEntity object
	 *
	 *@return    The dGStatus value
	 */
	private EANFlagAttribute getDGStatus() {
		return (EANFlagAttribute) m_rst.getEANObject(0, m_rst.getColumnIndex("DGENTITY:DGSTATUS:C"));
	}

	/**
	 *  Gets the dGType attribute of the DGEntity object
	 *
	 *@return    The dGType value
	 */
	private EANFlagAttribute getDGType() {
		return (EANFlagAttribute) m_rst.getEANObject(0, m_rst.getColumnIndex("DGENTITY:DGTYPE:C"));
	}

	/**
	 *  Gets the dGSubscription attribute of the DGEntity object
	 *
	 *@return    The dGSubscription value
	 */
	private EANFlagAttribute getDGSubscription() {
		return (EANFlagAttribute) m_rst.getEANObject(0, m_rst.getColumnIndex("DGENTITY:DGSUBSCRIPTION:C"));
	}

	/**
	 *  Gets the dGTaskStatus attribute of the DGEntity object
	 *
	 *@return    The dGTaskStatus value
	 */
	private EANFlagAttribute getDGTaskStatus() {
		return (EANFlagAttribute) m_rst.getEANObject(0, m_rst.getColumnIndex("DGENTITY:TASKSTATUS:C"));
	}

	/**
	 *  Gets the dGCreateDate attribute of the DGEntity object
	 *
	 *@return    The dGCreateDate value
	 */
	public EANTextAttribute getDGCreateDate() {
		return (EANTextAttribute) m_rst.getEANObject(0, m_rst.getColumnIndex("DGENTITY:CREATEDATE:C"));
	}

	//
	// MUTATORS
	//

	/**
	 *  Sets the title attribute of the DGEntity object
	 *
	 *@param  _str  The new title value
	 */
	public void setTitle(String _str) {
		if (_str == null) {
			D.ebug(D.EBUG_ERR, this +"someone passed null in for the DGTitle:" + _str);
			_str = "null title from Abstract Task";
		}

		if (_str.trim().length() == 0) {
			D.ebug(D.EBUG_ERR, this +"someone passed spaces for the DGTitle:" + _str);
			_str = "Empty title from Abstract Task";
		}

		if (_str.trim().length() > 64) {
			D.ebug(D.EBUG_ERR, this +"Length is too long for the DGTitle, truncating to 64 chars:" + _str);
			_str = _str.substring(0, 63);
		}

		try {
			EntityItem ei = (EntityItem) m_rst.getRow(0);
			ei.put("DGENTITY:NAME:C", _str);
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, this +" trouble setting the Title:" + _str + ":" + x);
			x.printStackTrace();
		}
	}

	/**
	 *  Sets the rptName attribute of the DGEntity object
	 *
	 *@param  _str  The new rptName value
	 */
	public void setRptName(String _str) {
		if (_str == null) {
			D.ebug(D.EBUG_ERR, this +"someone passed null in for the RptName:" + _str);
			_str = "null rpt name from Abstract Task";
		}

		try {
			EntityItem ei = (EntityItem) m_rst.getRow(0);
			EntityGroup eg = ei.getEntityGroup();
			// attempt to find maxlen and truncate if needed
			if (eg != null){
				EntityList list = eg.getEntityList();
				if (list != null){
					eg = list.getEntityGroup("DGENTITY");
					if (eg != null){
						EANMetaAttribute ma = eg.getMetaAttribute("RPTNAME");
						if (ma != null){
							int maxlen = ma.getMaxLen();
							int strLen = _str.length();
							if (maxlen != 0 && strLen != 0 && strLen > maxlen) {
								D.ebug(D.EBUG_ERR, this +" rpt name:" + _str + ": exceeds maxlen: " + maxlen+" and will be truncated");
								_str = _str.substring(0, maxlen-1);
							}
						}
					}
				}
			}

			ei.put("DGENTITY:RPTNAME:C", _str);
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, this +" trouble setting rpt name:" + _str + ":" + x);
			x.printStackTrace();
		}
	}

	public void setRptClass(String _str) {
		setDGFlagAttribute("RPTCLASS", _str);
	}

	/**
	 *  Sets the createDate attribute of the DGEntity object
	 *
	 *@param  _str  The new createDate value
	 */
	public void setCreateDate(String _str) {
		if (_str == null) {
			D.ebug(D.EBUG_ERR, this +"someone passed null in for the Create Date" + _str);
			_str = "null Create Date from Abstract Task";
		}

		try {
			EntityItem ei = (EntityItem) m_rst.getRow(0);
			ei.put("DGENTITY:CREATEDATE:C", _str);
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, this +" trouble setting the Create Date:" + _str + ":" + x);
			x.printStackTrace();
		}
	}
	
	/**
	 * set the created by attribute  IN4221666 - WI332259
	 * @param opname
	 */
	private void setCreatedBy(String opname){
		if(opname==null || opname.length()==0){
			D.ebug(D.EBUG_ERR, this +" setCreatedBy opname does not have a value");
			return;
		}
		try {
			EntityItem ei = (EntityItem) m_rst.getRow(0);
			ei.put("DGENTITY:CREATEDBY:C", opname);
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, this +" trouble setting Create By:" + opname + ":" + x);
			x.printStackTrace();
		}
	}

	/**
	 *  Sets the textAttribute attribute of the DGEntity object
	 *
	 *@param  _strAttribute  The new textAttribute value
	 *@param  _str           The new textAttribute value
	 */
	public void setTextAttribute(String _strAttribute, String _str) {
		if (_str == null) {
			D.ebug(D.EBUG_ERR, this +"someone passed null in for the " + _strAttribute + ":" + _str);
			_str = "null " + _strAttribute + " from Abstract Task";
		}

		try {
			EntityItem ei = (EntityItem) m_rst.getRow(0);
			ei.put("DGENTITY:" + _strAttribute + ":C", _str);
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, this +" trouble setting " + _strAttribute + ":" + _str + ":" + x);
			x.printStackTrace();
		}
	}

	/**
	 *  Sets the body attribute of the DGEntity object
	 *
	 * @param  _ab  The new body value
	 */
	public void setBody(byte[] _ab) {
		String fn = ((m_strFileExtension.length() > 0)? "dg-output." + m_strFileExtension : "dg-output.html");
		setBody(_ab,fn); //RCQ00316123 
	}

	/**
	 *  Sets the body attribute of the DGEntity object
	 *
	 * @param _ab  The new body value
	 * @param filename RCQ00316123
	 */
	public void setBody(byte[] _ab,String filename) {
		try {
			// Get the EntityItem in question
			EntityItem ei = (EntityItem) m_rst.getRow(0);
			// ei.put("DGENTITY:DGBLOB:C", new OPICMBlobValue(1, ((m_strFileExtension.length() > 0)? "dg-output." + m_strFileExtension : "dg-output.html"), _ab));
			ei.put("DGENTITY:DGBLOB:C", new OPICMBlobValue(1, filename, _ab));
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, this +" trouble setting the DG Body " + x);
			x.printStackTrace();
		}
	}
	/**
	 *  Sets the type attribute of the DGEntity object
	 *
	 *@param  _strFlagCode  The new type value
	 */
	public void setType(String _strFlagCode) {
		EANFlagAttribute fa = getDGType();
		if (fa == null) {
			D.ebug(D.EBUG_SPEW,"DGEntity.setType(). cannot find attribute to set.");
			return;
		}
		fa.put(_strFlagCode, true);
	}

	/**
	 *  Sets the taskStatus attribute of the DGEntity object
	 *
	 *@param  _strFlagCode  The new taskStatus value
	 */
	public void setTaskStatus(String _strFlagCode) {
		EANFlagAttribute fa = getDGTaskStatus();
		if (fa == null) {
			D.ebug(D.EBUG_SPEW,"DGEntity.setTaskStatus(). cannot find attribute to set.");
			return;
		}
		fa.put(_strFlagCode, true);
	}

	/**
	 *  Sets the status attribute of the DGEntity object
	 *
	 *@param  _strFlagCode  The new status value
	 */
	public void setStatus(String _strFlagCode) {
		EANFlagAttribute fa = getDGStatus();
		if (fa == null) {
			D.ebug(D.EBUG_SPEW,"DGEntity.setStatus(). cannot find attribute to set.");
			return;
		}

		try {
			// Get the EntityItem in question
			// EntityItem ei = (EntityItem) m_rst.getRow(0);
			fa.put(_strFlagCode, true);
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, this +" trouble setting the Status " + x);
			x.printStackTrace();
		}

	}

	/**
	 *  Sets the subscription attribute of the DGEntity object
	 *
	 *@param  _strFlagCode  The new subscription value
	 */
	public void setSubscription(String _strFlagCode) {
		EANFlagAttribute fa = getDGSubscription();
		if (fa == null) {
			D.ebug(D.EBUG_SPEW,"DGEntity.setSubscription(). cannot find attribute to set.");
			return;
		}
		fa.put(_strFlagCode, true);
	}

	/**
	 *  Sets the cat1 attribute of the DGEntity object
	 *
	 *@param  _strFlagCode  The new cat1 value
	 */
	public void setCat1(String _strFlagCode) {
		EANFlagAttribute fa = getCat1();
		D.ebug(D.EBUG_DETAIL, "DGEntity:SETTING CAT1");

		if (fa == null) {
			D.ebug(D.EBUG_SPEW,"DGEntity.setCat1(). cannot find attribute to set.");
			return;
		}

		D.ebug(D.EBUG_DETAIL, "DGEntity:setting CAT1 to " + _strFlagCode);
		try {
			fa.put(_strFlagCode, true);
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, this +" trouble setting the CAT1 " + x);
			x.printStackTrace();
		}
	}

	/**
	 *  Sets the dginfo needed when body is not html
	 *
	 *@param  _dgtemplate  The dg template
	 */
	public void setDGInfo(String _dgtemplate) {
		m_strDgInfo = _dgtemplate;
	}

	/**
	 *  Sets the cat2 attribute of the DGEntity object
	 *
	 *@param  _strFlagCode  The new cat2 value
	 */
	public void setCat2(String[] _astrFlagCode) {
		EANFlagAttribute fa = getCat2();
		D.ebug(D.EBUG_DETAIL, "DGEntity:GETTING CAT2");

		if (fa == null) {
			D.ebug(D.EBUG_SPEW,"DGEntity.setCat2(). cannot find attribute to set.");
			return;
		}

		for (int i =0; i < _astrFlagCode.length; i++) {
			String strFlagCode = _astrFlagCode[i];
			D.ebug(D.EBUG_DETAIL, "DGEntity:setting CAT2 to " + strFlagCode);
			try {
				fa.put(strFlagCode, true);
			} catch (Exception x) {
				D.ebug(D.EBUG_ERR, this +" trouble setting the CAT2 " + x);
				x.printStackTrace();
			}
		}
	}

	/**
	 *  Sets the dGFlagAttribute attribute of the DGEntity object
	 *
	 *@param  _strFlagAttr  AttributeCode
	 *@param  _strFlagCode  FlagValue
	 */
	public void setDGFlagAttribute(String _strFlagAttr, String _strFlagCode) {
		if (_strFlagCode == null) {
			D.ebug(D.EBUG_ERR, this +"someone passed null in for the " + _strFlagAttr);
			return;
		}

		boolean bMultiValues = false;
		bMultiValues = _strFlagCode.indexOf(",") > 0; //Check whether we are getting all the values at once

		EANFlagAttribute fa = getDGFlagAttribute(_strFlagAttr);
		D.ebug(D.EBUG_DETAIL, "DGEntity:GETTING " + _strFlagAttr);

		if (fa == null) {
			D.ebug(D.EBUG_SPEW,"DGEntity.setDGFlagAttribute(). cannot find attribute to set.");
			return;
		}

		D.ebug(D.EBUG_DETAIL, "DGEntity:setting " + _strFlagAttr + " to |" + _strFlagCode + "|");
		try {
			// Get the EntityItem in question

			//EntityItem ei = (EntityItem) m_rst.getRow(0);
			if (bMultiValues) {
				StringTokenizer stNextString = new StringTokenizer(_strFlagCode, ",", false);
				String strNextToken = null;
				while (stNextString.hasMoreElements()) {
					strNextToken = stNextString.nextToken().trim();
					fa.put(strNextToken, true);
				}
			} else {
				fa.put(_strFlagCode.trim(), true);
			}
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, this +" trouble setting the" + _strFlagAttr + " " + x);
			x.printStackTrace();
		}

	}

	String getUniqueEmailId(String _strEmailId) {
		Hashtable hId = new Hashtable();
		String strReturnId = "";

		boolean bMultiId = false;
		bMultiId = _strEmailId.indexOf(",") > 0; //Check whether we have many id's
		if (!bMultiId)
			return _strEmailId; //Dont do anything if multiple id's NOT found

		StringTokenizer stNextId = new StringTokenizer(_strEmailId, ",", false);
		String strNextId = null;
		while (stNextId.hasMoreElements()) {
			strNextId = stNextId.nextToken().trim();
			if (hId.containsKey(strNextId)) {
				continue;
			}
			hId.put(strNextId, "Unique ID");
			strReturnId += strReturnId.equals("") ? strNextId : "," + strNextId;
		}
		return strReturnId;
	}

	/*
	 * This controls the Save
	 * In 1.1 we add on an SP call so we can dup the flags from the profile
	 * WG associated with the profile
	 */
	/**
	 *  Saves the contents of this deliverable to the database
	 *
	 *@param  _db  Database
	 */
	public void save(Database _db) {
		try {
			m_rst.commit(_db);

			EntityItem ei = getDGEntityItem();

			D.ebug(D.EBUG_SPEW, "DGEntity:Saved "+ei.getKey()+"....found " + m_rst.getColumnCount());
			for (int i = 0; i < m_rst.getColumnCount(); i++) {
				String key = m_rst.getColumnKey(i);
				EANFoundation attr = ei.getEANObject(key);
				if (attr instanceof EANBlobAttribute){
					D.ebug(D.EBUG_DETAIL, key);
				}else{
					D.ebug(D.EBUG_DETAIL, key+" value: "+attr);
				}
			}

			D.ebug(D.EBUG_SPEW,"DGEntity:Creating reference to parent group");
			m_eg.putEntityItem(ei); //Store the reference to parent
			//D.ebug(D.EBUG_SPEW,m_eg.dump(false));

			int iDGID = getEntityID();
			_db.callGBL8007(new ReturnStatus(-1), m_iOPWGID, m_strEnterprise, m_iWGID, iDGID);

		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, this +" trouble saving the DG entity " + x);
			x.printStackTrace();
		}finally{
			try {
				_db.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			_db.freeStatement();
			_db.isPending();			
		}
	}
	/**
	 * RCQ00303672 create a new DGEntity for each file in this list when attachments are created
	 * xseries autogen only uses charges.txt, prodnum.txt and xcharges.txt
	 * 
	 * @param filelist
	 */
	public void setAttachmentFiles(String filelist){
		if(filelist != null){
			attachFileSet = new HashSet();
			//check will be lowercase
			StringTokenizer st = new StringTokenizer(filelist, ",");
			while (st.hasMoreTokens()) {
				attachFileSet.add(st.nextToken().trim());
			}
		}
	}

	/**
	 *  Sends mail to the subscribers of this deliverable
	 *
	 *@param  _db                 Database
	 *@param  _prof               Profile
	 *@param  _strSubject         Subject
	 *@param  _strOutputFileName  External file mail attatchment was written to
	 *@return                     Description of the Return Value
	 *@exception  Exception       Description of the Exception
	 *@exception  SQLException    Description of the Exception
	 */
	public boolean sendMail(Database _db, Profile _prof, String _strSubject, String _strOutputFileName) throws Exception, SQLException {

		if (m_strSubscription.equals("NONE")) { //Subscription has been disabled for this report, exit gracefully
			D.ebug(D.EBUG_DETAIL, "DGEntity:sendMail:Subscription has been disabled, report property has been set NOT to send mail");
			return true;
		}
	
		MiddlewareMail mmDG = null;
		if(MiddlewareServerProperties.getAllowSendingMail()){ //RCQ00303672 
			mmDG =_db.getMiddlewareMail();
			mmDG.setFileExtension(getFileExtension());
		}else{
			D.ebug(D.EBUG_DETAIL, "DGEntity.sendmail is not allowed");
		}
		
		byte[] byteDGBlob = getBodyBytes();
		String strReport = new String(byteDGBlob);
		String strSendMail = null;
		String strEmailId = null;

		D.ebug(D.EBUG_SPEW,"DGEntity:Blob returned in Sendmail" + strReport);
		String emailAddr = _prof.getEmailAddress();
		// make sure email address has a domain, cron jobs may queue abr with an application id
		// and notification wont work without it
		if (emailAddr.indexOf("@")==-1){
			emailAddr+="@us.ibm.com";
			D.ebug(D.EBUG_WARN, "DGEntity:sendMail Adding domain to emailaddress " + emailAddr);
		}
		if(mmDG!=null){
			mmDG.setFrom(new InternetAddress(emailAddr));
		}
		Hashtable hMailTo = new Hashtable(); //Check for duplicates

		//Call gbl9118 to get the valid Operator addresses
		ReturnStatus returnStatus = new ReturnStatus(-1);
		ResultSet rs = null;
		ReturnDataResultSet rdrs = null;
		String strNow = null;
		String strForever = null;
		int iSessionID = _db.getNewSessionID();
		try {
			DatePackage dp = _db.getDates();
			strNow = dp.getNow();
			strForever = dp.getForever();

			rs = _db.callGBL9118(returnStatus, m_strEnterprise, getEntityID());
			rdrs = new ReturnDataResultSet(rs);
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, this +" trouble retrieving the operators for sendmail " + x);
					x.printStackTrace();
		}finally{
			if(rs!=null){
				rs.close();
				rs = null;
			}
			_db.freeStatement();
			_db.isPending();
		}

		boolean bSendMail = true;
		//boolean bTaskFailed = false;
		MetaFlag[] mfAttr = (MetaFlag[]) getDGTaskStatus().get();
		for (int i = 0; i < mfAttr.length; i++) {
			D.ebug(D.EBUG_SPEW, "DGEntity:Checking flag Status Value :" + mfAttr[i].getFlagCode());
			if (mfAttr[i].isSelected()) {
				D.ebug(D.EBUG_SPEW, "DGEntity:********"+mfAttr[i]+" is selected");
				if (mfAttr[i].getFlagCode().equals(DG_TASKSTATUS_TSFAIL)) {
					D.ebug(D.EBUG_SPEW, "DGEntity, Task has failed..");
					//          bTaskFailed = true;
					bSendMail = false;
					break;
				}
			}
		}
		String strRecipients = "";
		Vector opidVct = new Vector(); //hang onto ids that will be able to see this in the inbasket RCQ00303672

		strRecipients = m_strExternalEmail; //Create list for external email
		D.ebug(D.EBUG_SPEW, "DGEntity:Sending mail to external email address [" + m_strExternalEmail+"]");
		D.ebug(D.EBUG_SPEW, "DGEntity:Sending Mail to Internal email address [" + m_strInternalEmail+"]");
		strRecipients += (m_strInternalEmail.length() > 0) ? "," + m_strInternalEmail : ""; //add Internal Email to it
		int iOPid = 0;
		if (m_iIDs != null) { //Add the Internal  ID's to the que which are outside subscription
			D.ebug(D.EBUG_SPEW, "DGEntity:Creating entity in the Que table for the following operators:" + m_strInternalEmail);

			for (int i = 0; i < m_iIDs.length; i++) {
				/*
				 * Now add to the queue table the Internal List of Operators
				 */
				iOPid = -m_iIDs[i];
				opidVct.add(new Integer(iOPid)); //RCQ00303672
				try{
					_db.callGBL8009(new ReturnStatus(-1), m_strEnterprise, iOPid, iSessionID, "DGWORKQUEUE", 0, "DGENTITY", getEntityID(), strNow, strForever);
				}finally{
					_db.commit();
					_db.freeStatement();
					_db.isPending();
				}
			}
		}

		if (rdrs.size() > 0) { //Subscription has succeeded
			for (int ii = 0; ii < rdrs.size(); ii++) {
				strEmailId = rdrs.getColumn(ii, 0).trim();
				if (hMailTo.containsKey(strEmailId)) {
					continue;
				}
				strSendMail = rdrs.getColumn(ii, 2).trim(); //This will indicate whether this person should recieve mail
				hMailTo.put(strEmailId, strSendMail);

				if (strSendMail.equals("Y")) { //Build email address only if indicator says Y
					D.ebug(D.EBUG_SPEW, "DGEntity:Sending Mail to Subscriber" + strEmailId);
					strRecipients += strRecipients.equals("") ? strEmailId : "," + strEmailId;
				}

				iOPid = rdrs.getColumnInt(ii, 1);
				iOPid = -iOPid; //Store the negative value in the Queue table to indicate this is an OP, not OPWG

				opidVct.add(new Integer(iOPid)); //RCQ00303672
				/*
				 * Now add to the queue table the list of subscribers
				 */
				try{
					_db.callGBL8009(new ReturnStatus(-1), m_strEnterprise, iOPid, iSessionID, "DGWORKQUEUE", 0, "DGENTITY", getEntityID(), strNow, strForever);
				}finally{
					_db.commit();
					_db.freeStatement();
					_db.isPending();
				}

			}
			if(mmDG!=null){
				mmDG.setRecipients(Message.RecipientType.TO, getUniqueEmailId(strRecipients));
			}
		} else {
			/*       Now check for Subscription type, if optional then Notifylasteditor will matter
			 */
			if (m_strSubscription.equals("OPTIONAL") && m_strNotifyOnError.equals("NO")) {
				bSendMail = true;
			} else {
				_strSubject = _strSubject + " but the subscribers could not be found.";
				bSendMail = false;
			}
		}


		iOPid = -_prof.getOPID(); //Store the negative value in the Queue table to indicate this is an OP, not OPWG

		if (!bSendMail) { //Send mail to person who triggered when abr fails or no subscribers found
			strRecipients += strRecipients.equals("") ? (emailAddr) : ("," + emailAddr);
			if(mmDG!=null){
				mmDG.setRecipients(Message.RecipientType.TO, getUniqueEmailId(strRecipients));
			}

			opidVct.add(new Integer(iOPid)); //RCQ00303672
			//Add to Queue table....this is with the id of the person who triggered it
			try{
				_db.callGBL8009(new ReturnStatus(-1), m_strEnterprise, iOPid, iSessionID, "DGWORKQUEUE", 0, "DGENTITY", getEntityID(), strNow, strForever);
			}finally{
				_db.commit();
				_db.freeStatement();
				_db.isPending();
			}
		} 
		
		if(mmDG==null && !opidVct.contains(new Integer(iOPid))){ //RCQ00303672
			if(strRecipients.length()==0){ // make sure the attachment processing is done
				strRecipients += strRecipients.equals("") ? (emailAddr) : ("," + emailAddr);
			}
			//make sure report ends up in the in-basket, email will not be sent
			opidVct.add(new Integer(iOPid)); 
			//Add to Queue table....this is with the id of the person who triggered it
			try{
				_db.callGBL8009(new ReturnStatus(-1), m_strEnterprise, iOPid, iSessionID, "DGWORKQUEUE", 0, "DGENTITY", getEntityID(), strNow, strForever);
			}finally{
				_db.commit();
				_db.freeStatement();
				_db.isPending();
			}
		}
		
		if (strRecipients.length() > 0) {
			_db.debug(D.EBUG_DETAIL, "DGEntity:Sendmail:Sending " + _strSubject + " to " + strRecipients);

			//".* <!--STARTFILEBREAKFORMAIL:filename:
			String strDGFileBreakTag = ".* <!--STARTFILEBREAKFORMAIL:";
			//Check for file break tag
			if (strReport.indexOf(strDGFileBreakTag) != -1) {
				//Parse the content to convert to multiple attachments
				Vector dgaVct = setDGAttachments(strReport, mmDG);
				//RCQ00303672
				for(int i=0; i<dgaVct.size(); i++){
					DGAttachment dga = (DGAttachment)dgaVct.elementAt(i);
					//RCQ00303672 create a new DGEntity for each file
					//is there any filtering of files saved? xseries only uses charges.txt, prodnum.txt and xcharges.txt
					if(attachFileSet !=null && (attachFileSet.contains(dga.filename.toLowerCase())
							|| attachFileSet.contains("all"))){
						createDGentityForAttachment(_db,_prof,dga,opidVct, iSessionID,strNow,strForever);
					}
					dga.dereference(); 
				}
				dgaVct.clear();
				dgaVct = null;
				if(attachFileSet !=null){
					attachFileSet.clear();
					attachFileSet = null;
				}
			} else {
				if(mmDG!=null){
					mmDG.setContentByteArray(byteDGBlob);
				}
			}
			
			if(mmDG!=null){
				// try this a couple times if can't connect to SMTP server
				int maxTries = Math.max(1, MiddlewareServerProperties.getSendMailMaxTries());
				for (int i=0; i<maxTries; i++){
					try{
						mmDG.setSubject(_strSubject);
						mmDG.send();
						break;
					}catch(javax.mail.MessagingException mme){
						D.ebug(D.EBUG_ERR, this +" trouble sending mail (attempt["+(i+1)+"] of "+maxTries+") "+
								mme.getMessage());
					}
				}
			}	
			
		} else {
			_db.debug(D.EBUG_DETAIL, "DGEntity:Sendmail:did not send mail - No recipients found");
		}
		
		opidVct.clear();
		opidVct = null;

		return bSendMail;
	}


	/**
	 * RCQ00303672 create a new DGEntity for each file
	 * @param db
	 * @param _prof
	 * @param dga
	 * @param opidVct
	 * @param iSessionID
	 * @param strNow
	 * @param strForever
	 * @throws EANBusinessRuleException
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void createDGentityForAttachment(Database db, Profile _prof, DGAttachment dga,
			Vector opidVct, int iSessionID,String strNow, String strForever) 
					throws EANBusinessRuleException, MiddlewareException, SQLException{

		//must replace blob, fileext, rptname etc - no email would be done

		DGEntity dgfile = new DGEntity(db,_prof);
		dgfile.getDGEntityItem().duplicate(getDGEntityItem());

		dgfile.setFileExtension(dga.getFileExtension());

		dgfile.setRptName(dga.filename); //only 20 long, cant append

		byte[] baBlob = dga.contents.getBytes(); //Create a byte array
		dgfile.setBody(baBlob,dga.filename); //RCQ00316123

		dgfile.save(db);

		// this must be done so the rpt shows up in the inbasket
		for (int i = 0; i < opidVct.size(); i++) {
			/*
			 * Now add to the queue table the List of Operators
			 */
			int iOPid = ((Integer)opidVct.elementAt(i)).intValue();
			try{
				db.callGBL8009(new ReturnStatus(-1), m_strEnterprise, iOPid, iSessionID, "DGWORKQUEUE", 0, "DGENTITY", dgfile.getEntityID(), strNow, strForever);
			}finally{
				db.commit();
				db.freeStatement();
				db.isPending();
			}
		}
	}

	//
	// Generic Stuff
	//
	/**
	 *  Description of the Method
	 *
	 *@param  _bBrief  Description of the Parameter
	 *@return          Description of the Return Value
	 */
	public String dump(boolean _bBrief) {

		StringBuffer strbResult = new StringBuffer();
		strbResult.append("Not much yet" + getKey());
		return strbResult.toString();
	}

	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Return Value
	 */
	public String toString() {
		return getTitle();
	}

	public EntityItem getDGEntityItem() {
		EntityItem eiReturn = null;
		try {
			EntityItem ei = (EntityItem) m_rst.getRow(0);
			eiReturn = (EntityItem) ei.getDownLink(0);
		} catch (Exception x) {
			D.ebug(D.EBUG_ERR, this +" trouble getting DGEntity");
			x.printStackTrace();
		}
		return eiReturn;
	}

	/**
	 *
	 *@param  _db      Database Object
	 *@param  _prof    Profile Object
	 *@param  _eiRoot  Root EntityItem from which the VE should start
	 */
	public void setDGCategories(Database _db, Profile _prof, EntityItem[] _eiRoot) {
		D.ebug(D.EBUG_SPEW,"DGEntity:setDGCategories:**************Start "+_eiRoot[0].getKey());
		//String strNextToken = null;
		String strFromToken = null;
		String strAttrCode = null;
		Hashtable hDGParameters = new Hashtable(); //Store the DGparameters and value recd from the html

		EntityList elist = null;

		byte[] byteDGBlob = getBodyBytes(); //Get the html from the DGEntity
		String strReport = new String(byteDGBlob);

		String strDGTAG = "<!--DGSUBMITBEGIN";
		String strDGEND = "DGSUBMITEND-->";

		String strDGString = "";
		// body may not be html, must get template from attribute in that case
		if (strReport.indexOf(strDGTAG) != -1){
			//Find the location of the Tag in the report, if not found, go to the end of the report
			int intDGBegin = strReport.indexOf(strDGTAG) != -1 ? strReport.indexOf(strDGTAG) + strDGTAG.length() : strReport.length();
			strDGString = strReport.substring(intDGBegin);        
			int intDGEnd = strDGString.indexOf(strDGEND);
			if (intDGEnd!= -1){
				strDGString = strDGString.substring(0,intDGEnd+strDGEND.length());
			}
		}else{
			strDGString = getDgInfo();
		}

		D.ebug(D.EBUG_SPEW,"DGEntity:AFter parsing Dgsubmit :\n" + strDGString);
		StringTokenizer stString = new StringTokenizer(strDGString, "\n", false);
		while (stString.hasMoreElements()) {
			strFromToken = stString.nextToken();
			if (strFromToken.equals(strDGEND)) {
				break;
			}
			//find the "="
			if (strFromToken.lastIndexOf("=") <= 0) { //if = not found in line, we are not interested
				continue;
			} else {
				strAttrCode = strFromToken.substring(0, strFromToken.lastIndexOf("=")).trim(); //move to the last word
				D.ebug(D.EBUG_SPEW,"DGEntity:Attribute code is " + strAttrCode + ":");
			}

			D.ebug(D.EBUG_SPEW,"DGEntity:This token is " + strFromToken.substring(strFromToken.lastIndexOf("=")));
			strFromToken = strFromToken.substring(strFromToken.lastIndexOf("=") + 1);
			if (strFromToken.trim().length() == 0) { //Skip if CAT is not assigned any value
				continue;
			}
			if (!hDGParameters.containsKey(strAttrCode)) {
				hDGParameters.put(strAttrCode, strFromToken);
			}
		}

		/*
		 * Now we have all the html parms , so generate the VE and extract the info
		 */
		try {

			if ((String) hDGParameters.get("SUBSCRVE") == null) {
				D.ebug(D.EBUG_SPEW,"DGEntity:setDGCategories:Cannot Extract since SUBSCRVE is null");
				hDGParameters.clear();
				return;
			}
			ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, (String) hDGParameters.get("SUBSCRVE"));

			elist = _db.getEntityList(_prof, eaItem, _eiRoot);
		} catch (Exception me) {
			D.ebug(D.EBUG_SPEW,"DGEntity:setDGCategories:Problem during Extract" + me.getMessage());
			me.printStackTrace();
			hDGParameters.clear();
			return;
		} 

		/*
		 * VE has been extracted...now get the values for the attributes stored in our hash table
		 * if there is no ve, look for the
		 */
		Enumeration e = hDGParameters.keys();
		String strCatValue = null;
		String strAttrValue = null;
		EANFlagAttribute fa = null;
		while (e.hasMoreElements()) {
			strAttrCode = (String) e.nextElement();
			strCatValue = (String) hDGParameters.get(strAttrCode);
			D.ebug(D.EBUG_SPEW,"DGEntity:setDGCategories:attributecode " + strAttrCode + ":CatValue:" + strCatValue);

			if (strCatValue.equalsIgnoreCase("RPTSTATUS") || strCatValue.equalsIgnoreCase("TASKSTATUS")) {
				fa = getDGFlagAttribute("TASKSTATUS");
				strAttrValue = fa.getFirstActiveFlagCode();
			} else if (strCatValue.equalsIgnoreCase("RPTNAME")) {
				strAttrValue = getRptName();
			} else if (strCatValue.equalsIgnoreCase("RPTCLASS")) {
				fa = getDGFlagAttribute("RPTCLASS");
				strAttrValue = fa.getFirstActiveFlagCode();
			} else if (strCatValue.equalsIgnoreCase("RPTROOTTYPE")) { // support pulling root type
				strAttrValue = _eiRoot[0].getEntityType();
			} else {
				strAttrValue = getAttributeValue(elist, strCatValue, _eiRoot);
			}

			if (strAttrCode.equalsIgnoreCase("EXTMAIL")) {
				m_strExternalEmail = strAttrValue; //Set it to a variable...we use it when its time to send mail
				D.ebug(D.EBUG_SPEW,"DGEntity:setDGCategories:Setting External email to " + m_strExternalEmail);
			} else if (strAttrCode.equalsIgnoreCase("SUBSCR_ENABLED")) { //Possible values are REQUIRED, NONE, OPTIONAL
				m_strSubscription = strAttrValue; //Set it to a variable...we use it when its time to send mail
				D.ebug(D.EBUG_SPEW,"DGEntity:setDGCategories:Subscription is : " + m_strSubscription);
			} else if (strAttrCode.equalsIgnoreCase("SUBSCR_NOTIFY_ON_ERROR")) { //Possible values are YES, NO
				m_strNotifyOnError = strAttrValue; //Set it to a variable...we use it when its time to send mail
				D.ebug(D.EBUG_SPEW,"DGEntity:setDGCategories:Notification on Error is : " + m_strNotifyOnError);
			} else if (strAttrCode.equalsIgnoreCase("INTMAIL")) {
				strAttrValue = getAttributeValue(elist, strCatValue, _eiRoot, true); //Store the OPerator Id's
				m_strInternalEmail = strAttrValue; //Set it to a variable...we use it when its time to send mail
				D.ebug(D.EBUG_SPEW,"DGEntity:setDGCategories:Setting Internal email to " + m_strInternalEmail);
			} else if (strAttrCode.startsWith("CAT")) { //Look whether its CAT1, CAT2 etc
				setDGFlagAttribute(strAttrCode, strAttrValue);
			} else if (!strAttrCode.endsWith("MAIL")) { //bypass INTMAIL and EXTMAIL
				setTextAttribute(strAttrCode, strAttrValue);
			}
		}

		hDGParameters.clear();
		this.save(_db); //Save it
		D.ebug(D.EBUG_SPEW,"DGEntity:setDGCategories:**************End");
	}

	public String getAttributeValue(EntityList _elist, String _strEntityAttribute, EntityItem[] _eiRoot) {
		return getAttributeValue(_elist, _strEntityAttribute, _eiRoot, false);
	}

	/**
	 *  Gets the attributeValue attribute of the DGEntity object
	 *
	 *@param  _elist               Description of the Parameter
	 *@param  _strEntityAttribute  Description of the Parameter
	 *@return                      The attributeValue value
	 */
	public String getAttributeValue(EntityList _elist, String _strEntityAttribute, EntityItem[] _eiRoot, boolean _bAddEntityId) {
		String strRetValue = "";
		//String strFlagValues = null;
		String strEntityType = null;
		String strAttrCode = null;
		StringTokenizer st = new StringTokenizer(_strEntityAttribute, ".", false);
		if (st.hasMoreElements()) {
			strEntityType = st.nextToken();
		}
		if (st.hasMoreElements()) {
			strAttrCode = st.nextToken();
		} else {
			return _strEntityAttribute; //this is not an Entitytype, attrcode pair..so nothing to search
		}

		EntityGroup eg = _elist.getEntityGroup(strEntityType);

		if (strEntityType.equals(_eiRoot[0].getEntityType())|| //get the parent entity group for root entity
				strEntityType.equalsIgnoreCase("ROOTTYPE")) {  // support pulling root type
			eg = _elist.getParentEntityGroup();
		}

		if (eg == null) {
			D.ebug(D.EBUG_SPEW,"DGEntity:getAttributeValue:entitygroup for " + strEntityType + " not found in the VE");
			//D.ebug(D.EBUG_SPEW,"This is the EntityList from the Subscription VE:\n"+_elist.dump(false));
			return null;
		}

		if (eg.getEntityItemCount() == 0) {
			D.ebug(D.EBUG_SPEW,"DGEntity:getAttributeValue:No EntityItems found for " + strEntityType + " this will not return any value");
			//D.ebug(D.EBUG_SPEW,"This is the EntityList from the Subscription VE:\n"+_elist.dump(false));
			//D.ebug(D.EBUG_SPEW,"***************End of List Subscription VE");
		} else {
			if (_bAddEntityId) {
				m_iIDs = new int[eg.getEntityItemCount()]; //Set the array to store the id's
			}
		}

		String strAttrValue = "";
		boolean bFirst = true;
		for (int i = 0; i < eg.getEntityItemCount(); i++) {
			EntityItem ei = eg.getEntityItem(i);

			if (_bAddEntityId) {
				m_iIDs[i] = ei.getEntityID(); //Keep track of the entityIDs we need to insert in the  que
				D.ebug(D.EBUG_SPEW, "DGEntity:getAttributeValue:Adding Operator " + m_iIDs[i]);
			}

			EANAttribute EANAttr = ei.getAttribute(strAttrCode);
			D.ebug(D.EBUG_SPEW,"DGEntity:getAttributeValue EI:" + ei.getKey() + ":ET:" + strEntityType + ":strAttrCode:" + strAttrCode);

			if (EANAttr == null) {
				D.ebug(D.EBUG_SPEW,"DGEntity:getAttributeValue:entityattribute is null");
				//D.ebug(D.EBUG_SPEW,"This is the EntityList from the Subscription VE:\n"+_elist.dump(false));
				return "";
			}
			strAttrValue = EANAttr.toString();

			EANMetaAttribute eanMetaAtt = eg.getMetaAttribute(strAttrCode);
			switch (eanMetaAtt.getAttributeType().charAt(0)) {
			case 'F' :
			case 'U' :
			case 'S' :
				MetaFlag[] mfAttr = (MetaFlag[]) EANAttr.get();
				for (int j = 0; j < mfAttr.length; j++) {
					if (mfAttr[j].isSelected()) {
						if (bFirst) {
							bFirst = false;
							strAttrValue = mfAttr[j].getFlagCode();
						} else {
							strAttrValue += "," + mfAttr[j].getFlagCode(); //Separate multiple values with Commas
						}
					}
				}

				break;
			default :

			}
			D.ebug(D.EBUG_SPEW,"DGEntity:Attribute values are " + strAttrValue);
			strRetValue += strRetValue.equals("") ? strAttrValue : "," + strAttrValue;
		}

		return strRetValue;
	}

	/* private EntityGroup getDGEntityGroup() {
        if (m_eg == null) {
            D.ebug(D.EBUG_SPEW,"DGEntity:getDGEntityGroup:Somehow lost the reference to EntityGroup");
        }
        return m_eg;
    }*/

	/*private void setDGAttachments(String _strReport, MiddlewareMail _mmDG) throws Exception {
        String strDGFileBreakTag = ".* <!--STARTFILEBREAKFORMAIL:";
        int intDGBegin = _strReport.indexOf(strDGFileBreakTag);
        int intDGEnd = 0;

        String strDGString = null;

        int intFilenameBegin = 0;
        int intFilenameEnd = 0;

        String strFilename = null;
        do {
            //D.ebug(D.EBUG_SPEW,"DGBegin is now:"+intDGBegin);
            if (_strReport.substring(intDGBegin + 1).indexOf(strDGFileBreakTag) != -1) {
                intDGEnd = _strReport.substring(intDGBegin + 1).indexOf(strDGFileBreakTag) + 1;
                //D.ebug(D.EBUG_SPEW,"intDGEnd is now:"+intDGEnd);
                intDGEnd += intDGBegin;
            } else {
                intDGEnd = _strReport.length();
            }
            //intDGEnd = _strReport.substring(intDGBegin+1).indexOf(strDGFileBreakTag) != -1 ? _strReport.substring(intDGBegin+1).indexOf(strDGFileBreakTag)+2  : _strReport.length();
            //D.ebug(D.EBUG_SPEW,"intDGEnd is now again:"+intDGEnd);

            strDGString = _strReport.substring(intDGBegin, intDGEnd);
            //D.ebug(D.EBUG_SPEW,"String to process is"+strDGString);

            intFilenameBegin = strDGString.indexOf(":") + 1;
            intFilenameEnd = intFilenameBegin + strDGString.substring(intFilenameBegin + 1).indexOf(":") + 1;
            //D.ebug(D.EBUG_SPEW,"Filename starts :"+intFilenameBegin+":Ends:"+intFilenameEnd);
            // byte[] getBytes()                  //do we need to be aware of the charset?
            strFilename = strDGString.substring(intFilenameBegin, intFilenameEnd);
            D.ebug(D.EBUG_SPEW,"DGEntity:File name is :" + strFilename);
            byte[] byteAttachment = strDGString.getBytes(); //Create a byte array
            _mmDG.setContentByteArray(byteAttachment, strFilename);
            intDGBegin = intDGEnd;

        } while (intDGEnd < _strReport.length());
    }*/

	//RCQ00303672
	private Vector setDGAttachments(String _strReport, MiddlewareMail _mmDG) throws Exception {
		Vector dgaVct = getDGAttachments(_strReport);
		if(_mmDG !=null){
			for(int i=0; i<dgaVct.size(); i++){
				DGAttachment dga = (DGAttachment)dgaVct.elementAt(i);
				D.ebug(D.EBUG_SPEW,"DGEntity:File name is :" + dga.filename);
				byte[] byteAttachment = dga.contents.getBytes(); //Create a byte array
				_mmDG.setContentByteArray(byteAttachment, dga.filename);
			}
		}
		return dgaVct;
	}

	public void setFileExtension(String _s) {
		m_strFileExtension = _s;
	}

	public String getFileExtension() {
		return m_strFileExtension;
	}

	/**
	 * RCQ00303672-WI EACM - Changes for handling AUTOGEN ABR
	 * get all the attachments
	 * @param _strReport
	 * @return
	 */
	private Vector getDGAttachments(String _strReport) {
		Vector dgaVct = new Vector();
		String strDGFileBreakTag = ".* <!--STARTFILEBREAKFORMAIL:";
		int intDGBegin = _strReport.indexOf(strDGFileBreakTag);
		int intDGEnd = 0;

		String strDGString = null;

		int intFilenameBegin = 0;
		int intFilenameEnd = 0;

		String strFilename = null;
		do {
			if (_strReport.substring(intDGBegin + 1).indexOf(strDGFileBreakTag) != -1) {
				intDGEnd = _strReport.substring(intDGBegin + 1).indexOf(strDGFileBreakTag) + 1;
				intDGEnd += intDGBegin;
			} else {
				intDGEnd = _strReport.length();
			}

			strDGString = _strReport.substring(intDGBegin, intDGEnd);

			intFilenameBegin = strDGString.indexOf(":") + 1;
			intFilenameEnd = intFilenameBegin + strDGString.substring(intFilenameBegin + 1).indexOf(":") + 1;

			strFilename = strDGString.substring(intFilenameBegin, intFilenameEnd);
			D.ebug(D.EBUG_SPEW,"DGEntity:File name is :" + strFilename);
			DGAttachment dga = new DGAttachment(strFilename,strDGString);
			dgaVct.add(dga);
			intDGBegin = intDGEnd;

		} while (intDGEnd < _strReport.length());
		return dgaVct;
	}

	/**
	 * RCQ00303672-WI EACM - Changes for handling AUTOGEN ABR
	 * file name and contents for each txt file attached to the AUTOGEN email
	 */
	private class DGAttachment {
		String filename;
		String contents;
		DGAttachment(String fn, String val){
			filename = fn;
			contents = val;
		}
		void dereference(){
			filename = null;
			contents = null;
		}
		String getFileExtension() {
			String ext = "";
			int id = filename.indexOf('.');
			if(id!= -1){
				ext = filename.substring(id+1);
			}
			return ext;
		}
	}

}
