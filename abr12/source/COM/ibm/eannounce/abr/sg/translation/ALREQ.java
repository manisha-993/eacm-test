// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.translation;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;

import com.ibm.transform.oim.eacm.util.PokUtils;

/*******************************************************************************
 * ALREQ (Add new translation package)
 */

// ALREQ.java,v
public final class ALREQ extends PokBaseABR {

	private static final Map ENTITY_PROCESS_MAP = new HashMap();
	
	private final StringBuffer exceptionReportBuffer = new StringBuffer();

	private int requestNLSID;

	private String requestEntityType;
	
	private XLATEGRPHandler relatorHandler = new XLATEGRPHandler();
	
	private int handledEntityCount = 0;
	
	static {
		ENTITY_PROCESS_MAP.put("XCATNAV", new CATNAV_AddTranslationProcess());
		ENTITY_PROCESS_MAP.put("XFB", new FB_AddTranslationProcess());
		ENTITY_PROCESS_MAP.put("XFEATURE", new FEATURE_AddTranslationProcess());
		ENTITY_PROCESS_MAP.put("XIPSCFEAT",
				new IPSCFEAT_AddTranslationProcess());
		ENTITY_PROCESS_MAP.put("XLSEO", new LSEO_AddTranslationProcess());
		ENTITY_PROCESS_MAP.put("XLSEOBUNDLE",
				new LSEOBUNDLE_AddTranslationProcess());
		ENTITY_PROCESS_MAP.put("XMM", new MM_AddTranslationProcess());
		ENTITY_PROCESS_MAP.put("XMODEL", new MODEL_AddTranslationProcess());
		ENTITY_PROCESS_MAP.put("XSVCMOD", new SVCMOD_AddTranslationProcess());
		ENTITY_PROCESS_MAP.put("XSWFEATURE",
				new SWFEATURE_AddTranslationProcess());
	}

	public void execute_run() {
		try {
			start_ABRBuild(false);
			setNow();
			setControlBlock();
			buildRptHeader();
			processAbr();
			
			print("<h2>Added translation groups: ");
			print(""+relatorHandler.translationGroupCount);
			print("</h2>");
			println("<br/>");
			
			print("<h2>Handled entities: ");
			print(""+handledEntityCount);
			print("</h2>");
			println("<br/>");
			
			if (exceptionReportBuffer.length() > 0) {
				println("<br/>");
				println("<h1>Exceptions:</h1>");
				println(exceptionReportBuffer.toString());
			}

			setReturnCode(PASS);
		} catch (Exception e) {
			setReturnCode(FAIL);
			println("<h3>Uncaught exception</h3>");
			e.printStackTrace(getPrintWriter());
		} finally {
			setDGString(getABRReturnCode());
			setDGRptName(getShortClassName(getClass()));
			setDGRptClass(getABRCode());
			// make sure the lock is released
			if (!isReadOnly()) {
				clearSoftLock();
			}
			printDGSubmitString();
			println(EACustom.getTOUDiv());
			buildReportFooter();
		}
	}

	/*
	 * 1. Get entities of type received in "XENTITYTYPE" attribute 2. Check if
	 * entity meets the criteria: STATUS = FINAL, PUBFROM < TARGETDATE and PUBTO >
	 * TARGETDATE 3. Create relator from the XLATEGRP to the entity
	 */
	private void processAbr() throws Exception {
		D.ebug("Processing ABR for "+m_abri.getEntityType());
		EntityGroup eg = m_db.getEntityGroup(m_prof, m_abri.getEntityType(),
				"Edit");
		EntityItem ei = new EntityItem(eg, m_prof, m_db,
				m_abri.getEntityType(), m_abri.getEntityID());

		
		final String xEntityDisplayName = PokUtils.getAttributeValue(ei, "XENTITYTYPE", null, null);
		EANFlagAttribute xEntityAttribute = (EANFlagAttribute) ei.getAttribute("XENTITYTYPE");
		String xEntityType = xEntityAttribute.getFirstActiveFlagCode();
		if (xEntityType == null)
			throw new IllegalArgumentException("XENTITYTYPE cannot be null");

		EANFlagAttribute xNLSWriteAttribute = (EANFlagAttribute) ei.getAttribute("NLSWRITE");
		String xNLSWrite = xNLSWriteAttribute.getFirstActiveFlagCode();
		
		if (xNLSWrite == null)
			throw new IllegalArgumentException("NLSWRITE cannot be null");

		try {
			requestNLSID = Integer.parseInt(xNLSWrite);
		} catch (Exception e) {
			throw new IllegalArgumentException("NLSWRITE "+xNLSWrite+" is not valid!");
		}
		
		String xTgtDate = PokUtils.getAttributeValue(ei, "XTGTDATE", null, null);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		final Date xTargetDate = dateFormat.parse(xTgtDate);
		
		println("<h2>Translation Add Language request</h2>");
		println("<ul>");
		println("<li>XENTITYTYPE="+xEntityDisplayName+" ("+xEntityType+")</li>");
		println("<li>NLSWRITE="+xNLSWrite+"</li>");
		println("<li>XTGTDATE="+xTgtDate+"</li>");
		println("</ul>");
		println("<br/>");

		D.ebug("xEntityType = "+xEntityType);
		D.ebug("xTgtDate / xTargetDate  = "+xTgtDate+" / "+xTargetDate);
		D.ebug("xNLSWrite / nslid = "+xNLSWrite+" / "+requestNLSID);
		

		final AddTranslationProcess translationProcess = (AddTranslationProcess) ENTITY_PROCESS_MAP
				.get(xEntityType);

		if (translationProcess == null) {
			throw new IllegalArgumentException(xEntityType + " is not mapped");
		}

		D.ebug("AddTranslationProcess  for "+xEntityType+" is "+translationProcess.getClass());

		// Remove the first X from the entity type
		if (xEntityType.charAt(0) == 'X') {
			requestEntityType = xEntityType.substring(1);
		} else {
			requestEntityType = xEntityType;
		}
		D.ebug("Working with EntityType: "+requestEntityType);
		handledEntityCount = 0;

		// 1. Get entities of type received in "XENTITYTYPE" attribute
		EntityHandler.withAllEntitiesDo(m_db, m_prof, m_cbOn, requestEntityType, requestNLSID,
				new EntityHandler.Callback() {

					public void process(EntityHandler entityHandler) {
						try {
							// 2. Check if entity meets the criteria
							if (translationProcess.isValid(entityHandler,
									xTargetDate)) {
								D.ebug("Creating relators for: "+entityHandler.getEntityType()+
										" - ID: "+entityHandler.getEntityID());
								// 3. Create relator from the XLATEGRP to the entity
								translationProcess.createRelators(relatorHandler, entityHandler);
								handledEntityCount++;
								
							}
						} catch (Exception e) {
							handleException(e, entityHandler.getEntityType(), entityHandler.getEntityID());
						}
					}

				});
		//Final commit
		m_db.commit();
	}

	public String getABRVersion() {
		return "1.0";
	}

	public String getDescription() {
		return "ALREQ - Add Translation ABR";
	}

	private void handleException(Exception ex, String entityType, int entityId) {
		exceptionReportBuffer.append("<p style='color:#c00'>Exception: ");
		exceptionReportBuffer.append(ex.getMessage());
		exceptionReportBuffer.append("<p>Entity:");
		exceptionReportBuffer.append(entityType);
		exceptionReportBuffer.append(" - ");
		exceptionReportBuffer.append(entityId);
		exceptionReportBuffer.append("</p>");
		exceptionReportBuffer.append("</p>");
		ex.printStackTrace();
	}
	

	/*
	 * builds the standard header and submits it to the passed PrintWriter
	 */
	private void buildRptHeader() throws SQLException, MiddlewareException {

		String strVersion = getVersion();
		String astr1[] = new String[] { getABRDescription(), strVersion,
				getEnterprise() };
		String strMessage1 = buildMessage(MSG_IAB0001I, astr1);

		println(EACustom.getDocTypeHtml());
		println("<head>");
		println(EACustom.getMetaTags(getClass().getName() + ".java"));
		println(EACustom.getCSS());
		println(EACustom.getTitle(getShortClassName(getClass())));
		println("</head>");
		println("<body id=\"ibm-com\">");
		println(EACustom.getMastheadDiv());

		println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + strMessage1 + "</em></p>");
		println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">"
				+ NEW_LINE
				+ "<tr><td width=\"25%\"><b>Abr Version: </b></td><td>"
				+ getABRVersion()
				+ "</td></tr>"
				+ NEW_LINE
				+ "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>"
				+ getValOn()
				+ "</td></tr>"
				+ NEW_LINE
				+ "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>"
				+ getEffOn()
				+ "</td></tr>"
				+ NEW_LINE
				+ "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>"
				+ getNow() + "</td></tr>" + NEW_LINE + "</table>");
		println("<h3>Description: </h3>" + getDescription() + NEW_LINE
				+ "<hr />" + NEW_LINE);

	}

	private class XLATEGRPHandler extends RelatorHandler {

		int translationGroupCount = 0;

		public XLATEGRPHandler() {
			super("XLATEGRP");
		}

		public EntityHandler createParentEntity() throws MiddlewareException {
			D.ebug("RelatorHandler: Inserting new XLATEGRP");
			EntityHandler entity = new EntityHandler(m_db, m_prof,m_cbOn, "XLATEGRP",-1, requestNLSID);
			entity.insert();
			D.ebug("RelatorHandler: XLATEGRP new ID = "	+ entity.getEntityID());
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String name = "Add Lang. Package "+requestEntityType+" "+dateFormat.format(new Date());
			entity.setTextAttribute("XLATENAME", name);
			
			entity.setFlagAttribute("BILLINGCODE", "SGA");
			entity.setFlagAttribute("FAMILY", "F00050");
			entity.setFlagAttribute("PDHDOMAIN", "0050");
			entity.setFlagAttribute("XLATEABR01", "0020");
			entity.setFlagAttribute("XLATEGRPSTATUS", "D0010");
			entity.setFlagAttribute("XLSTATUS"+requestNLSID, "XL20");
			entity.setFlagAttribute("XLLANGUAGES", ""+requestNLSID);
			
			println("<h3>Added XLATEGRP: "+name+"</h3>");
			
			translationGroupCount++;
			
			try {
				m_db.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return entity;
		}
		
	}

}
