package COM.ibm.eannounce.abr.sg;

//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.middleware.ReturnID;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
import COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask;
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.opicmpdh.objects.Text;

import com.ibm.transform.oim.eacm.util.PokUtils;

//$Log: MXABRSTATUS.java,v $
//Revision 1.13  2014/01/13 13:52:40  wendy
//migration to V17
//
//Revision 1.12  2012/07/09 12:05:15  wangyulo
//Fix defect 742829-- correct Date/timestamp format for STRTOSVC and ENDOFSVC
//
//Revision 1.11  2012/07/03 07:50:01  wangyulo
//Fix defect 742829-- correct Date/timestamp format for STRTOSVC and ENDOFSVC
//
//Revision 1.10  2012/06/26 00:59:59  wangyulo
//defect 742829-- Error found during prod IDL of ABR  incorrect Date/ timestamp format
//
//Revision 1.9  2012/04/25 15:39:01  lucasrg
//Allow empty values when validating the PRODUCTACTIVITY tag
//
//Revision 1.8  2012/04/24 14:58:43  lucasrg
//<PRODUCTACTIVITY> for REFOFER FEAT is now mapped as '' (Empty) for Updates and 'Remove' for Deletes
//
//Revision 1.7  2012/03/16 20:59:01  lucasrg
//Storing entity attributes in the correct order
//
//Revision 1.6  2012/03/13 20:27:31  lucasrg
//REFOFER and REFOFER_FEAT mapping changes
//Using 'Update' as default for <PRODUCTACTIVITY>
//
//Revision 1.5  2011/11/04 12:08:53  lucasrg
//Added MFRPRODID mapping to REFOFER
//
//Revision 1.4  2011/10/31 17:22:09  lucasrg
//Minor change to output error details in the report when deleting a REFOFERFEAT with no relator.
//
//Revision 1.3  2011/10/19 20:47:13  lucasrg
//Keep the timestamp from MIW (don't convert to PDH's timezone)
//Allow REFOFERFEAT with same FEATID but different PRODUCTID
//Use only the Date part when deleting "REFOFER" in ENDOFSVC attribute
//Output the message about deleting the REFOFER in the report
//Fail the ABR when Creating an REFOFER "Skeleton"
//Fail the ABR when REFOFERREFOFERFEAT relator is not found and PRODUCTIDACTIVITY = "Delete"
//Added mappings: PRODSUPRTCD MFRLNGPRODDESC
//Add PDHDOMAIN attribute in REFOFERFEAT
//Add DTSMIWCREATE attribute in REFOFER and REFOFERFEAT
//Change MAINTANNBILLELIGINDC to Flag attribute (MAINN / MAINY)
//Variable SUBSCRVE depending if MXTYPE is REFOFER or REFOFERFEAT
//
//
public class MXABRSTATUS extends PokBaseABR {

	private static final String DQ_FINAL = "FINAL";
	private static final String DQ_DRAFT = "DRAFT";
	private static final String STATUS_DRAFT = "0010";
	private static final String STATUS_FINAL = "0020";
	private static final String STATUS_CHANGE_REQUEST = "0050";
	private static final String ABR_QUEUE = "0020";
	static final String RPT_EXCEPTION = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
	static final String RPT_STACKTRACE = "<pre>{0}</pre>";
	private static final String MIW_PDHDOMAIN = "MIW";
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);

	private StringBuffer rptSb = new StringBuffer();
	private String SUBSCRVE;

	public void execute_run() {
		setDGTitle("MXABRSTATUS");
		setDGRptName(getShortClassName(getClass()));
		setDGRptClass(getABRCode());
		String title = getShortClassName(getClass()) + " - MIW Inbound";
		String HEADER = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE
			+ EACustom.getTitle(title) + NEWLINE 
			+ "</head>" + NEWLINE 
			+ "<body id=\"ibm-com\">"
			+ EACustom.getMastheadDiv() + NEWLINE + 
			"<p class=\"ibm-intro ibm-alternate-three\"><em>"+title+"</em></p>" + NEWLINE;
		
		println(EACustom.getDocTypeHtml()); // Output the doctype and html
		println(HEADER);
		SUBSCRVE = "UNDEFINED";
		try {
			start_ABRBuild(false);
			setNow();
			setControlBlock();

			EntityList list = m_db.getEntityList(m_prof, new ExtractActionItem(null, m_db, m_prof,
					"dummy"), new EntityItem[] { new EntityItem(null, m_prof, getEntityType(),
					getEntityID()) });
			EntityItem MIWXMLEntity = list.getParentEntityGroup().getEntityItem(0);

			String MXTYPE = PokUtils.getAttributeValue(MIWXMLEntity, "MXTYPE", null, null);

			String xml = PokUtils.getAttributeValue(MIWXMLEntity, "MXMSG", null, null, false);
			addDebug(xml);
			Document document = parseXML(xml);
			Element root = document.getDocumentElement();

			if ("REFOFER".equals(MXTYPE)) {
				SUBSCRVE = "REFOFERVE"; 
				handleRefofer(MIWXMLEntity, root);
			} else if ("REFOFERFEAT".equals(MXTYPE)) {
				SUBSCRVE = "REFOFERFEATVE";
				handleRefoferFeat(MIWXMLEntity, root);
			}
		} catch (Throwable exception) {
			StringWriter stackBuffer = new StringWriter();
			MessageFormat msgf = new MessageFormat(RPT_EXCEPTION);
			setReturnCode(INTERNAL_ERROR);
			exception.printStackTrace(new PrintWriter(stackBuffer));

			// Put exception into document
			String[] args = { exception.getMessage() };
			rptSb.append(msgf.format(args));
			rptSb.append("\n");

			msgf = new MessageFormat(RPT_STACKTRACE);
			String[] stackArgs = { stackBuffer.getBuffer().toString() };
			rptSb.append(msgf.format(stackArgs));
			rptSb.append("\n");
			logError("Exception: " + exception.getMessage());
			logError(stackBuffer.getBuffer().toString());
		}

		println(rptSb.toString()); // Output the Report
		printDGSubmitString();
		println(EACustom.getTOUDiv());
		buildReportFooter(); // Print </html>
	}

	private void handleRefofer(EntityItem entity, Element root) throws Throwable {
		/* 1. Search for Reference Offering (REFOFER) using: PRODUCTID = <PRODUCTID>
		 * 
		 * 2. If REFOFER
		 *	Found � then update any attributes changed on REFOFER 
		 *	Not Found � then create a new instance of REFOFER
		 *
		 * 3. REFOFER - Set attributes
		 *	DataQuality (DATAQUALITY) = "Final" (Final)
		 *	Status (STATUS) = "Final" (0020)
		 *	"ADS XML Feed ABR" (ADSABRSTATUS) = "Queued" (0020)
		*/

		String PRODUCTID = PokUtils.getAttributeValue(entity, "MXPRODUCTID", null, null);

		addDebug("MIW MESSAGE TYPE: REFOFER, PRODUCTID = " + PRODUCTID);

		//Search all REFOFER entity ids for ProductID and PDHDOMAIN = 'MIW'

		EntityItem refofer = findRefofer(PRODUCTID);
		EntityWrapper refoferWrapper = null;

		if (refofer != null) {
			addDebug("REFOFER found: " + refofer.getKey());
		} else {
			addDebug("REFOFER not found!");
		}

		RefoferModel refoferModel = new RefoferModel(root);
		try {
			refoferModel.validate();
			if ("Delete".equalsIgnoreCase(refoferModel.ACTIVITY)) {
				if (refofer == null) {
					//If Activity = Delete, throw error
					addError(refoferModel.toString());
					fail("Reference Offering was marked for Delete; however, the Reference Offering does not exist in the PDH");
				} else {
					//Set <ENDOFSVC> = <DTSOFMSG>
					refoferWrapper = new EntityWrapper(refofer);
					//Get the DATE part only
					String endOfSvc = refoferModel.DTSOFMSG.substring(0, 10);
					refoferWrapper.text("ENDOFSVC", endOfSvc);
					refoferWrapper.flag("DATAQUALITY", DQ_FINAL);
					refoferWrapper.flag("STATUS", STATUS_FINAL);
					refoferWrapper.flag("ADSABRSTATUS", ABR_QUEUE);
					refoferWrapper.end();
					addOutput(refoferModel.toString());
					addOutput("Reference Offering was marked for Delete; however, the Reference Offering was not deleted. The End of Service was updated to match this change ("+endOfSvc+"). This change was feed to downstream systems.");
				}
			} else if ("Update".equalsIgnoreCase(refoferModel.ACTIVITY)) {
				if (refofer == null) {
					//REFOFER not found, create a new one
					EntityGroup eg = m_db.getEntityGroup(m_prof, "REFOFER", "Edit");
					refofer = new EntityItem(eg, m_prof, "REFOFER", 0);
					addOutput("New REFOFER created");
				}
				//Update attributes
				refoferWrapper = new EntityWrapper(refofer);
				refoferWrapper.text("PRODUCTID", refoferModel.PRODUCTID);
				refoferWrapper.text("DTSMIWCREATE", refoferModel.DTSMIWCREATE);
				refoferWrapper.flag("PDHDOMAIN", MIW_PDHDOMAIN);
				refoferWrapper.text("MFRPRODTYPE", refoferModel.MFRPRODTYPE, 30);
				refoferWrapper.text("MFRPRODDESC", refoferModel.MFRPRODDESC, 32);
				refoferWrapper.text("MKTGDIV", refoferModel.MKTGDIV, 2);
				refoferWrapper.flag("PRFTCTR", refoferModel.PRFTCTR);
				refoferWrapper.text("CATGSHRTDESC", refoferModel.CATGSHRTDESC, 30);
				refoferWrapper.text("STRTOFSVC", refoferModel.STRTOSVC);
				refoferWrapper.text("ENDOFSVC", refoferModel.ENDOFSVC);
				refoferWrapper.text("VENDNAM", refoferModel.VENDNAM, 30);
				refoferWrapper.text("MACHRATECATG", refoferModel.MACHRATECATG, 1);
				refoferWrapper.text("CECSPRODKEY", refoferModel.CECSPRODKEY, 1);
				// FLAG N = MAINN / Y = MAINY
				refoferWrapper.flag("MAINTANNBILLELIGINDC", "Y".equals(refoferModel.MAINTANNBILLELIGINDC) ? "MAINY"	: "MAINN");
				// REFOFER_DATA/FSLMCPU -> SYSIDUNIT  
				// If input='Y', value is "SIU-CPU" (S00010); if input='N', value is "SIU-Non CPU" (S00020)
				refoferWrapper.flag("SYSIDUNIT", "Y".equals(refoferModel.FSLMCPU) ? "S00010" : "S00020");
				refoferWrapper.text("PRODSUPRTCD", refoferModel.PRODSUPRTCD, 3);
				if(refoferModel.DOMAIN !=null || !"".equals(refoferModel.DOMAIN)) {
					refoferWrapper.text("DOMAIN", refoferModel.DOMAIN);
				}
				refoferWrapper.flag("DATAQUALITY", DQ_FINAL);
				refoferWrapper.flag("STATUS", STATUS_FINAL);
				refoferWrapper.flag("ADSABRSTATUS", ABR_QUEUE);
				refoferWrapper.end();
				setReturnCode(RETURNCODE_SUCCESS);
				addOutput("REFOFER attributes updated");
			}

		} catch (Exception e) {
			fail("Invalid REFOFER message: " + e.getMessage());
		}

	}

	private void handleRefoferFeat(EntityItem entity, Element root) throws Throwable {
		/* 1. Search without PDHDOMAIN for Reference Offering Feature (REFOFERFEAT) using: FEATID = <FEATID>
		 * 
		 * 2. <Activity> = Update
		 * 2.1. Found - update any attributes changed on the REFOFERFEAT
		 * 2.2. Not Found - create a new instance of REFOFERFEAT and Set attributes from the XML
		 * 
		 * 3. <Activity> = Delete
		 * 3.1. Found - set STATUS = �Change Request� and indicate an error (Do not proceed)
		 * 3.2. Not Found - indicate an error (Do not proceed)
		 * 
		 * 4. If UPDATED:
		 * 	REFOFERFEAT.DATAQUALITY=Final
		 * 	REFOFERFEAT.STATUS=0020 (Final)
		 * 	REFOFERFEAT.ADSABRSTATUS=0020 (Queued)
		 * 	MIWXML.MXABRSTATUS = 0030 (Passed)
		 * 
		 * 5. Search with PDHDOMAIN = �MIW� (MIW) for REFOFER using <PRODUCTID>
		 * 
		 * 6. If <PRODUCTIDACTIVITY> = Update
		 * 6.1 Found - Search for REFOFERREFOFERFEAT using REFOFER (PRODUCTID) and REFOFERFEAT (FEATID)
		 * 6.1.1 Found - Nothing to do
		 * 6.1.2 Not Found - Create the relator (REFOFERREFOFERFEAT) from the REFOFER to REFOFERFEAT
		 * 
		 * 6.2 Not Found - this is an error
		 * 	Create a REFOFER with
		 * 		PRODUCTID = <PRODUCTID>
		 * 		MKTGNAME = �Error � not received from MIW�
		 * 		STATUS = �Draft� (0010)
		 * 		DATAQUALITY = �Draft� (DRAFT)
		 * 	Create a REFOFERREFOFERFEAT from the REFOFER to the REFOFERFEAT
		 * 	Then create the error message
		 * 
		 * 7. <PRODUCTIDACTIVITY> = Delete
		 * 7.1 Found - delete REFOFERREFOFERFEAT
		 * 7.2 Not Found - create error message (Do not proceed)
		 * 
		 * 8. If REFOFERREFOFERFEAT was updated - Set the following attributes for the REFOFERFEAT:
		 * 		DataQuality (DATAQUALITY) = �Final� (Final)
		 * 		Status (STATUS) = �Final� (0020)
		 * 		�ADS XML Feed ABR� (ADSABRSTATUS) = �Queued� (0020)
		 * 		�MIW ABR Status� (MXABRSTATS) = �Passed� (0030)
		 * 
		 */

		addDebug("MIW MESSAGE TYPE: REFOFERFEAT");

		RefoferFeatModel model = new RefoferFeatModel(root);
		try {
			model.validate();

			//1
			String featID = PokUtils.getAttributeValue(entity, "MXFEATID", null, null);

			EntityItem refoferFeat = findRefoferFeat(featID);
			if (refoferFeat != null) {
				addDebug("Found REFOFERFEAT for FEATID = " + featID + ": " + refoferFeat.getKey());
			} else {
				addDebug("REFOFERFEAT *not found* for FEATID = " + featID);
			}

			EntityWrapper featWrapper = null;
			int featEntityID = 0;

			if ("Update".equalsIgnoreCase(model.ACTIVITY)) {
				//2
				if (refoferFeat != null) {
					//2.1 (update attributes only)
					featEntityID = refoferFeat.getEntityID();
				} else {
					//2.2 - Create new ReofferFeat
					featEntityID = m_db.getNextEntityID(m_prof, "REFOFERFEAT");
					EntityGroup eg = m_db.getEntityGroup(m_prof, "REFOFERFEAT", "Edit");
					refoferFeat = new EntityItem(eg, m_prof, "REFOFERFEAT", featEntityID);
					addOutput("New REFOFERFEAT created " + refoferFeat.getKey());
				}
				//Update attributes
				featWrapper = new EntityWrapper(refoferFeat);
				featWrapper.text("FEATID", model.FEATID, 40);
				featWrapper.text("DTSMIWCREATE", model.DTSMIWCREATE);
				featWrapper.text("MFRFEATID", model.MFRFEATID, 30);
				featWrapper.text("MFRFEATDESC", model.MFRFEATDESC, 128);
				featWrapper.text("MFRFEATLNGDESC", model.MFRFEATLNGDESC, 128);
				featWrapper.text("MKTGDIV", model.MKTGDIV, 2);
				featWrapper.flag("PRFTCTR", model.PRFTCTR);
				featWrapper.flag("PDHDOMAIN", MIW_PDHDOMAIN);
				addOutput("REFOFERFEAT atributes updated");
			} else if ("Delete".equalsIgnoreCase(model.ACTIVITY)) {
				//3
				if (refoferFeat != null) {
					//3.1
					setFlagValue(refoferFeat, "STATUS", STATUS_CHANGE_REQUEST);
					addError(model.toString());
					fail("Reference Offering Feature was marked for Delete. The STATUS was set to 'Change Request'. Please review and decide how to proceed.");
				} else {
					///3.2
					addError(model.toString());
					fail("Reference Offering Feature was marked for Delete; however, the Reference Offering Feature does not exist in the PDH");
				}
				//Do not proceed with any further processing of this XML message.
				return;
			}

			//5

			EntityItem refofer = findRefofer(model.PRODUCTID);
			if (refofer != null) {
				addDebug("Found REFOFER for PRODUCTID = " + model.PRODUCTID + ": "
						+ refofer.getKey());
			} else {
				addDebug("REFOFER *not found* for PRODUCTID = " + model.PRODUCTID);
			}
			if ("".equals(model.PRODUCTACTIVITY) 
					|| "Update".equalsIgnoreCase(model.PRODUCTACTIVITY)) {
				//6
				if (refofer != null) {
					//6.1
					EntityItem relator = findReofferFeatRelator(model.PRODUCTID, refoferFeat);
					if (relator == null) {
						addDebug("Creating new relator REFOFERREFOFERFEAT");
						//6.1.2
						createRelator("REFOFERREFOFERFEAT", "REFOFER", refofer.getEntityID(),
								"REFOFERFEAT", refoferFeat.getEntityID());
						if (featWrapper == null) {
							featWrapper = new EntityWrapper(refoferFeat);
						}
						featWrapper.flag("DATAQUALITY", DQ_FINAL);
						featWrapper.flag("STATUS", STATUS_FINAL);
						featWrapper.flag("ADSABRSTATUS", ABR_QUEUE);
					}
					addOutput("REFOFERFEAT was updated succesfully");
				} else {
					//6.2 Create REFOFER as STATUS_DRAFT
					int refoferID = m_db.getNextEntityID(m_prof, "REFOFER");
					EntityGroup egReoffer = m_db.getEntityGroup(m_prof, "REFOFER", "Edit");
					refofer = new EntityItem(egReoffer, m_prof, "REFOFER", refoferID);
					EntityWrapper refoferWrapper = new EntityWrapper(refofer);
					refoferWrapper.text("PRODUCTID", model.PRODUCTID);
					refoferWrapper.text("MKTGNAME", "Error � not received from MIW");
					refoferWrapper.flag("PDHDOMAIN", MIW_PDHDOMAIN);
					refoferWrapper.flag("STATUS", STATUS_DRAFT);
					refoferWrapper.flag("DATAQUALITY", DQ_DRAFT);
					refoferWrapper.end();
					createRelator("REFOFERREFOFERFEAT", "REFOFER", refoferID, "REFOFERFEAT",
							featEntityID);
					addDebug("Created skeleton for REFOFER: " + refoferID
							+ " and relator REFOFERREFOFERFEAT for REFOFERFEAT " + featEntityID);
					addError(model.toString());
					if (featWrapper != null) {
						featWrapper.end();
					}
					fail("Referenced Reference Offering does not exist but skeleton REFOFER was created");
					return;
				}
			} else if ("Remove".equalsIgnoreCase(model.PRODUCTACTIVITY)
					|| "Delete".equalsIgnoreCase(model.PRODUCTACTIVITY)) {
				//7
				if (refofer != null) {
					//7.1 delete REFOFERREFOFERFEAT
					EntityItem relator = findReofferFeatRelator(model.PRODUCTID, refoferFeat);
					if (relator == null) {
						addError(model.toString());
						fail("the relator \"Reference Offering to Reference Feature\" (REFOFERREFOFERFEAT) does not exist and hence there is nothing to delete.");
						return;
					} else {
						addDebug("Deleting relator REFOFERREFOFERFEAT " + relator.getKey());
						ReturnStatus returnStatus = new ReturnStatus();
						m_db.callGBL2099(returnStatus, m_cbOn.m_iOPWGID, m_prof.getEnterprise(),
								"REFOFERREFOFERFEAT", relator.getEntityID(), m_cbOn.m_iTRANID);
						m_db.commit();
						m_db.freeStatement();
						m_db.isPending();
						addOutput("REFOFERFEAT's relator was deactivated succesfully");
					}

					if (featWrapper == null) {
						featWrapper = new EntityWrapper(refoferFeat);
					}
				} else {
					//7.2
					addError(model.toString());
					fail("Reference Offering does not exist and hence there isn't a 'Reference Offering to Reference Feature' to delete.");
					return;
				}
			}

			if (featWrapper != null) {
				//At this point, the REFOFERFEAT is ok
				featWrapper.flag("DATAQUALITY", DQ_FINAL);
				featWrapper.flag("STATUS", STATUS_FINAL);
				featWrapper.flag("ADSABRSTATUS", ABR_QUEUE);
				featWrapper.end();
			}
			setReturnCode(RETURNCODE_SUCCESS);

		} catch (Exception e) {
			fail("Invalid REFOFERFEAT message: " + e.getMessage());
		}

	}

	private EntityItem findRefofer(String productID) throws Exception {
		//Search all REFOFER entity ids for ProductID and PDHDOMAIN = 'MIW'
		Vector attrs = new Vector();
		attrs.addElement("PRODUCTID");
		Vector vals = new Vector();
		vals.addElement(productID);
		StringBuffer debugSb = new StringBuffer();
		try {
			EntityItem[] list = ABRUtil.doSearch(m_db, m_prof, "SRDREFOFER1", "REFOFER", false,
					attrs, vals, debugSb);
			for (int i = 0; i < list.length; i++) {
				EntityItem ei = list[i];
				String id = PokUtils.getAttributeValue(ei, "PRODUCTID", null, null);
				String domain = PokUtils.getAttributeFlagValue(ei, "PDHDOMAIN");
				addDebug("Looking at " + ei.getKey() + " [" + id + "," + domain + "]");
				if (productID.equalsIgnoreCase(id) && MIW_PDHDOMAIN.equals(domain)) {
					//Found REFOFERFEAT
					return ei;
				}
			}
		} catch (Exception e) {
			addError(debugSb.toString());
			throw e;
		}
		return null;
	}

	private EntityItem findRefoferFeat(String featID) throws Exception {
		//Search all REFOFERFEAT entity ids for FeatID
		Vector attrs = new Vector();
		attrs.addElement("FEATID");
		Vector vals = new Vector();
		vals.addElement(featID);
		StringBuffer debugSb = new StringBuffer();
		try {
			EntityItem[] list = ABRUtil.doSearch(m_db, m_prof, "SRDREFOFERFEAT", "REFOFERFEAT",
					false, attrs, vals, debugSb);
			for (int i = 0; i < list.length; i++) {
				EntityItem ei = list[i];
				String id = PokUtils.getAttributeValue(ei, "FEATID", null, null);
				if (featID.equalsIgnoreCase(id)) {
					//Found REFOFERFEAT
					return ei;
				}
			}
		} catch (Exception e) {
			addError(debugSb.toString());
			throw e;
		}
		return null;
	}

	private EntityItem findReofferFeatRelator(String productID, EntityItem refoferFeat)
			throws SQLException, MiddlewareException {
		if (refoferFeat.getEntityID() <= 0) {
			return null;
		}
		EntityList el = m_db.getEntityList(m_prof, new ExtractActionItem(null, m_db, m_prof, "BHREFOFERFEATEXT"),
				new EntityItem[] { new EntityItem(refoferFeat) });
		EntityGroup relators = el.getEntityGroup("REFOFERREFOFERFEAT");
		for (int i = 0; i < relators.getEntityItemCount(); i++) {
			EntityItem ei = relators.getEntityItem(i);
			if (ei.getUpLinkCount() > 0 && ei.getDownLinkCount() > 0) {
				EntityItem refofer = (EntityItem) ei.getUpLink(0);
				String pid = PokUtils.getAttributeValue(refofer, "PRODUCTID", null, null);
				addDebug("Looking for relator with "+refofer.getKey()+" PRODUCTID "+pid+"="+productID);
				if (productID.equals(pid)) {
					addDebug("Relator found for "+refoferFeat.getKey()+" + "+productID);
					return ei;
				}
			}
		}
		return null;
	}

	public String getABRVersion() {
		return "1.0";
	}

	public String getDescription() {
		return "MXABRSTATUS";
	}

	private Document parseXML(String xml) throws Exception {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			StringReader sr = new StringReader(xml);
			InputSource is = new InputSource(sr);
			return builder.parse(is);
		} catch (SAXParseException exception) {
			addError(xml);
			throw exception;
		}
	}

	/******************************************************************************
	 * Get tag value, if doesnt exist use default
	 * @param element
	 * @param tagName
	 */
	public String getNodeValue(Element element, String tagName) {
		String value = null;
		NodeList nlist = element.getElementsByTagName(tagName);
		if (nlist == null) {
			addDebug("getNodeValue: " + tagName + " element returned null");
		} else if (nlist.getLength() == 0) {
			addDebug("getNodeValue: " + tagName + " was not found in the XML");
		} else if (nlist.getLength() > 1) {
			addDebug("getNodeValue: " + tagName + " has more then 1 element");
		} else {
			Element node = (Element) nlist.item(0);
			Element el = (Element) nlist.item(0);
			if (node.hasChildNodes()) {
				value = el.getFirstChild().getNodeValue();
			} else {
				value = el.getNodeValue();
			}
			//addDebug("getNodeValue: " + tagName + " = " + value);
		}
		return value;
	}

	public ReturnStatus createRelator(String relatorType, String entityFromType, int entityFromID,
			String entityToType, int entityToID) throws MiddlewareException {
		ReturnStatus returnStatus = new ReturnStatus(-1);
		String enterprise = m_prof.getEnterprise();
		int openID = m_cbOn.getOPENID();
		int tranID = m_cbOn.getTranID();
		String effFrom = m_cbOn.getEffFrom();
		String effTo = m_cbOn.getEffTo();
		int sessionID = m_prof.getSessionID();
		m_db.callGBL2098(returnStatus, openID, sessionID, enterprise, relatorType, new ReturnID(),
				entityFromType, entityFromID, entityToType, entityToID, tranID, effFrom, effTo, 1);
		m_db.freeStatement();
		m_db.isPending();
		return returnStatus;
	}

	public void addDebug(String msg) {
		rptSb.append("<!-- " + msg + " -->\n");
	}

	public void addOutput(String msg) {
		rptSb.append("<p>" + msg + "</p>\n");
	}

	/**********************************
	 * add error info and fail abr
	 * @param msg
	 */
	public void addError(String msg) {
		addOutput(msg);
	}

	public void fail(String msg) {
		addError(msg);
		setReturnCode(FAIL);
	}

	class Model {

		public String ACTIVITY;

		public String DTSOFMSG;

		public String DTSMIWCREATE;

		public void rejectIfNullOrEmpty(String name, String value) throws Exception {
			if (value == null || value.length() == 0) {
				throw new Exception("Element '" + name + "' cannot be empty.");
			}
		}

		public String parseTimestampAndRejectIfInvalid(String name, String value) throws Exception {
			try {
				return timestamp(value);
			} catch (ParseException e) {
				throw new Exception("Element '" + name + "' timestamp '" + value + "' is invalid: "
						+ e.getMessage());
			}
		}

		public String parseDateAndRejectIfInvalid(String name, String value) throws Exception {
			try {
				return date(value);
			} catch (ParseException e) {
				throw new Exception("Element '" + name + "' date '" + value + "' is invalid: "
						+ e.getMessage());
			}
		}
	}

	class RefoferModel extends Model {

		public String PRODUCTID;
		public String MFRPRODTYPE;
		public String MFRPRODDESC;
		public String MKTGDIV;
		public String PRFTCTR;
		public String CATGSHRTDESC;
		public String STRTOSVC;
		public String ENDOFSVC;
		public String VENDNAM;
		public String MACHRATECATG;
		public String CECSPRODKEY;
		public String MAINTANNBILLELIGINDC;
		public String FSLMCPU;
		public String PRODSUPRTCD;
		public String DOMAIN;

		public RefoferModel(Element element) {
			ACTIVITY = getNodeValue(element, "ACTIVITY");
			DTSOFMSG = getNodeValue(element, "DTSOFMSG");
			DTSMIWCREATE = getNodeValue(element, "DTSMIWCREATE");
			PRODUCTID = getNodeValue(element, "PRODUCTID");
			MFRPRODTYPE = getNodeValue(element, "MFRPRODTYPE");
			MFRPRODDESC = getNodeValue(element, "MFRPRODDESC");
			MKTGDIV = getNodeValue(element, "MKTGDIV");
			PRFTCTR = getNodeValue(element, "PRFTCTR");
			CATGSHRTDESC = getNodeValue(element, "CATGSHRTDESC");
			STRTOSVC = getNodeValue(element, "STRTOSVC");
			ENDOFSVC = getNodeValue(element, "ENDOFSVC");
			VENDNAM = getNodeValue(element, "VENDNAM");
			MACHRATECATG = getNodeValue(element, "MACHRATECATG");
			CECSPRODKEY = getNodeValue(element, "CECSPRODKEY");
			MAINTANNBILLELIGINDC = getNodeValue(element, "MAINTANNBILLELIGINDC");
			FSLMCPU = getNodeValue(element, "FSLMCPU");
			PRODSUPRTCD = getNodeValue(element, "PRODSUPRTCD");
			DOMAIN = getNodeValue(element, "DOMAIN");

		}

		public void validate() throws Exception {
			rejectIfNullOrEmpty("ACTIVITY", ACTIVITY);
			rejectIfNullOrEmpty("DTSOFMSG", DTSOFMSG);
			rejectIfNullOrEmpty("DTSMIWCREATE", DTSMIWCREATE);
			rejectIfNullOrEmpty("PRODUCTID", PRODUCTID);
			rejectIfNullOrEmpty("MFRPRODTYPE", MFRPRODTYPE);
			rejectIfNullOrEmpty("MFRPRODDESC", MFRPRODDESC);
			rejectIfNullOrEmpty("MKTGDIV", MKTGDIV);
			rejectIfNullOrEmpty("PRFTCTR", PRFTCTR);
			rejectIfNullOrEmpty("CATGSHRTDESC", CATGSHRTDESC);
			rejectIfNullOrEmpty("STRTOSVC", STRTOSVC);
			rejectIfNullOrEmpty("ENDOFSVC", ENDOFSVC);
			rejectIfNullOrEmpty("VENDNAM", VENDNAM);
			rejectIfNullOrEmpty("MACHRATECATG", MACHRATECATG);
			rejectIfNullOrEmpty("CECSPRODKEY", CECSPRODKEY);
			rejectIfNullOrEmpty("MAINTANNBILLELIGINDC", MAINTANNBILLELIGINDC);
			rejectIfNullOrEmpty("FSLMCPU", FSLMCPU);
			rejectIfNullOrEmpty("PRODSUPRTCD", PRODSUPRTCD);

			DTSOFMSG = parseTimestampAndRejectIfInvalid("DTSOFMSG", DTSOFMSG);
			DTSMIWCREATE = parseTimestampAndRejectIfInvalid("DTSMIWCREATE", DTSMIWCREATE);
			STRTOSVC = parseDateAndRejectIfInvalid("STRTOSVC", STRTOSVC);
			ENDOFSVC = parseDateAndRejectIfInvalid("ENDOFSVC", ENDOFSVC);
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("Date/Time=" + DTSOFMSG);
			sb.append(" \nMIW Create=" + DTSMIWCREATE);
			sb.append(" \nMessage Type=REFOFER");
			sb.append(" \nProduct ID=" + PRODUCTID);
			sb.append("\n");
			return sb.toString();
		}
	}

	class RefoferFeatModel extends Model {

		public String PRODUCTID;
		public String PRODUCTACTIVITY;
		public String FEATID;
		public String MFRFEATID;
		public String MFRFEATDESC;
		public String MFRFEATLNGDESC;
		public String MKTGDIV;
		public String PRFTCTR;

		public RefoferFeatModel(Element element) {
			ACTIVITY = getNodeValue(element, "ACTIVITY");
			String pa = getNodeValue(element, "PRODUCTACTIVITY");
			if (pa == null || pa.length() == 0) {
				PRODUCTACTIVITY = "";
			} else {
				PRODUCTACTIVITY = pa;
			}
			DTSOFMSG = getNodeValue(element, "DTSOFMSG");
			DTSMIWCREATE = getNodeValue(element, "DTSMIWCREATE");
			FEATID = getNodeValue(element, "FEATID");
			PRODUCTID = getNodeValue(element, "PRODUCTID");
			MFRFEATID = getNodeValue(element, "MFRFEATID");
			MFRFEATDESC = getNodeValue(element, "MFRFEATDESC");
			MFRFEATLNGDESC = getNodeValue(element, "MFRFEATLNGDESC");
			MKTGDIV = getNodeValue(element, "MKTGDIV");
			PRFTCTR = getNodeValue(element, "PRFTCTR");
		}

		public void validate() throws Exception {
			rejectIfNullOrEmpty("ACTIVITY", ACTIVITY);
			rejectIfNullOrEmpty("DTSOFMSG", DTSOFMSG);
			rejectIfNullOrEmpty("DTSMIWCREATE", DTSMIWCREATE);
			rejectIfNullOrEmpty("PRODUCTID", PRODUCTID);
			rejectIfNullOrEmpty("FEATID", FEATID);
			rejectIfNullOrEmpty("MFRFEATID", MFRFEATID);
			rejectIfNullOrEmpty("MFRFEATDESC", MFRFEATDESC);
			rejectIfNullOrEmpty("MFRFEATLNGDESC", MFRFEATLNGDESC);
			rejectIfNullOrEmpty("MKTGDIV", MKTGDIV);
			rejectIfNullOrEmpty("PRFTCTR", PRFTCTR);
			DTSOFMSG = parseTimestampAndRejectIfInvalid("DTSOFMSG", DTSOFMSG);
			DTSMIWCREATE = parseTimestampAndRejectIfInvalid("DTSMIWCREATE", DTSMIWCREATE);
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("Message Type=REFOFERFEAT");
			sb.append(" \nDate/Time=" + DTSOFMSG);
			sb.append(" \nMIW Create=" + DTSMIWCREATE);
			sb.append(" \nFeature Id=" + FEATID);
			sb.append(" \nProduct Id=" + PRODUCTID);
			sb.append("\n");
			return sb.toString();
		}
	}

	class EntityWrapper {

		EntityItem ei;

		ReturnEntityKey rek;

		Vector attrs;
		Map map;

		public EntityWrapper(EntityItem ei) {
			this.ei = ei;
			rek = new ReturnEntityKey(ei.getEntityType(), ei.getEntityID(), true);
			map = new HashMap();
			attrs = new Vector();
		}
		
		private void put(String key, Object value) {
			Object oldValue = map.get(key);
			if (oldValue != null) {
				//Replace old value
				int index = attrs.indexOf(oldValue);
				if (index >= 0) {
					attrs.remove(index);
					attrs.insertElementAt(value, index);
				}
			} else {
				attrs.add(value);
			}
			map.put(key, value);
		}

		public void flag(String attributeCode, String attributeValue) throws Exception {
			SingleFlag sf = new SingleFlag(m_prof.getEnterprise(), rek.getEntityType(), rek
					.getEntityID(), attributeCode, attributeValue, 1, m_cbOn);
			put(attributeCode, sf);
		}

		public void text(String attributeCode, String attributeValue) {
			Text sf = new Text(m_prof.getEnterprise(), rek.getEntityType(), rek.getEntityID(),
					attributeCode, attributeValue, 1, m_cbOn);
			put(attributeCode, sf);
		}

		public void text(String attributeCode, String value, int length) throws Exception {
			if (value.length() > length) {
				value = value.substring(0, length);
			}
			text(attributeCode, value);
		}

		public void end() throws Exception {
			Vector vctReturnsEntityKeys = new Vector();
			rek.m_vctAttributes = attrs;
			vctReturnsEntityKeys.addElement(rek);
			try {
				m_db.update(m_prof, vctReturnsEntityKeys, false, false);
				m_db.commit();
			} catch (Exception e) {
				throw new Exception("Unable to set text attributes for " + ei.getKey() + ": "
						+ e.getClass().getName() + " " + e.getMessage());

			}
		}

	}
	
	static String sDateFormat ="yyyy-MM-dd";
	static DateFormat inDateFormat = new SimpleDateFormat(sDateFormat);
	static DateFormat outFormat = new SimpleDateFormat(sDateFormat);//
	static int shortDateLength = sDateFormat.length();
	
	static String sDateTimeFormat = "yyyy-MM-dd hh:mm:ss.SSSSSS";
	static DateFormat inDateTimeFormat = new SimpleDateFormat(sDateTimeFormat);
	static int shortTimestampLength = sDateTimeFormat.length();

	public static String date(String inputDate) throws ParseException {
		Date date;
		if (inputDate.length() == shortDateLength) {
			date = inDateFormat.parse(inputDate);
		} else {
			if(inputDate.length()>shortDateLength){
				inputDate = inputDate.substring(0,inputDate.length());
			}
			date = inDateFormat.parse(inputDate);
		}
		return outFormat.format(date);
	}

	/**
	 * Fix Defect 742829 : Error found during prod IDL of MIW data - 
	 * Incorrect Date/timestamp forma
	 * The dateformat is 2012-12-31 12:03:15.000000 (yyyy-MM-dd hh:mm:ss.SSSSSS)
	 * @param inputDate
	 * @return
	 * @throws ParseException
	 */
	public static String timestamp(String inputDate) throws ParseException {
		int dateLength= inputDate.length();
		if (dateLength == shortTimestampLength) {
			inDateTimeFormat.parse(inputDate);
		} else {
			if(dateLength>shortTimestampLength){
				inputDate = inputDate.substring(0,shortTimestampLength);
			}
			inDateTimeFormat.parse(inputDate);
		}
		return inputDate;
	}

	/**
	 * Overridden to support multiple SUBSCRVE as described in spec:
	 * The subscription information for the Reports will be:
	 * CAT1= MIW
	 * CAT2=
	 * CAT3= TASKSTATUS
	 * SUBSCRVE=REFOFERVE if report is for a Reference Offering
	 * or
	 * SUBSCRVE=REFOFERFEATVE if the report is for a Reference Offering Feature
	 * 
	 * It may break if the parent method is changed.
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

		
		println("SUBSCRVE=" + SUBSCRVE);
		sb.append("SUBSCRVE=" + SUBSCRVE + "\n");

		strPrintString = ABRServerProperties.getCategory(strReport, "CAT1");
		if (strPrintString != null) {
			println("CAT1=" + strPrintString);
			sb.append("CAT1=" + strPrintString + "\n");
		}
		strPrintString = ABRServerProperties.getCategory(strReport, "CAT2");
		if (strPrintString != null) {
			println("CAT2=" + strPrintString);
			sb.append("CAT2=" + strPrintString + "\n");
		}

		strPrintString = ABRServerProperties.getCategory(strReport, "CAT3");
		if (strPrintString != null) {
			println("CAT3=" + strPrintString);
			sb.append("CAT3=" + strPrintString + "\n");
		}

		strPrintString = ABRServerProperties.getCategory(strReport, "CAT4");
		if (strPrintString != null) {
			println("CAT4=" + strPrintString);
			sb.append("CAT4=" + strPrintString + "\n");
		}

		strPrintString = ABRServerProperties.getCategory(strReport, "CAT5");
		if (strPrintString != null) {
			println("CAT5=" + strPrintString);
			sb.append("CAT5=" + strPrintString + "\n");
		}

		strPrintString = ABRServerProperties.getCategory(strReport, "CAT6");
		if (strPrintString != null) {
			println("CAT6=" + strPrintString);
			sb.append("CAT6=" + strPrintString + "\n");
		}

		strPrintString = ABRServerProperties.getExtMail(strReport);
		if (strPrintString != null) {
			println("EXTMAIL=" + strPrintString);
			sb.append("EXTMAIL=" + strPrintString + "\n");
		}

		strPrintString = ABRServerProperties.getIntMail(strReport);
		if (strPrintString != null) {
			println("INTMAIL=" + strPrintString);
			sb.append("INTMAIL=" + strPrintString + "\n");
		}

		strPrintString = ABRServerProperties.getSubscrEnabled(strReport);
		if (strPrintString != null) {
			println("SUBSCR_ENABLED=" + strPrintString);
			sb.append("SUBSCR_ENABLED=" + strPrintString + "\n");
		}

		strPrintString = ABRServerProperties.getSubscrNotifyOnError(strReport);
		if (strPrintString != null) {
			println("SUBSCR_NOTIFY_ON_ERROR=" + strPrintString);
			sb.append("SUBSCR_NOTIFY_ON_ERROR=" + strPrintString + "\n");
		}

		println("DGSUBMITEND-->");
		sb.append("DGSUBMITEND-->\n");
		//dgtemplate = sb.toString(); //needed when body is not html
	}

}
