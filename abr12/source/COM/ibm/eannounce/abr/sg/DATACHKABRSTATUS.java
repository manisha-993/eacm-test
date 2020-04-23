/*package COM.ibm.eannounce.abr.sg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;


import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;

public class DATACHKABRSTATUS extends PokBaseABR {

	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);
	private Hashtable metaTbl = new Hashtable();
	private String navName = "";
	private int abr_debuglvl = D.EBUG_ERR;
	private String fromDate = null;
	private String toDate = null;
	public static final String FINNAL = "Final";
	private static final String WINNEWLINE = "";
	StringBuffer report = new StringBuffer("ENTITYTYPE,ENTITYID,DATA");
	private Set prodSet = new HashSet();
	private Set swprodSet = new HashSet();
	private Set modSet = new HashSet();
	private Set covertSet = new HashSet();
	private int secount;
	private boolean timelimit = false;

	public String getDescription() {
		// TODO Auto-generated method stub
		return "DATACHKREPORTABR";
	}

	public String getABRVersion() {
		// TODO Auto-generated method stub
		return "1.0";
	}

	public void execute_run() {
		String HEADER = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE
				+ EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">"
				+ EACustom.getMastheadDiv() + NEWLINE
				+ "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
		String HEADER2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE
				+ "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>"
				+ NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE
				+ "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->"
				+ NEWLINE;

		String header1 = "";

		MessageFormat msgf;
		String abrversion = "";
		String rootDesc = "";

		Object[] args = new String[10];

		try {
			msgf = new MessageFormat(HEADER);
			args[0] = getShortClassName(getClass());
			args[1] = "ABR";
			header1 = msgf.format(args);
			setDGTitle("DATACHKREPORTABR report");
			setDGString(getABRReturnCode());
			setDGRptName("DATACHKREPORTABR"); // Set the report name
			setDGRptClass("DATACHKREPORTABR"); // Set the report class
			// Default set to pass
			setReturnCode(PASS);

			start_ABRBuild(false); // pull the VE

			abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties
					.getABRDebugLevel(m_abri.getABRCode());

			// get the root entity using current timestamp, need this to get the
			// timestamps or info for VE pulls
			m_elist = getEntityList(getVEName());
			
			 * m_db.getEntityList(m_prof, new ExtractActionItem(null, m_db,
			 * m_prof,"dummy"), new EntityItem[] { new EntityItem(null, m_prof,
			 * getEntityType(), getEntityID()) });
			 

			// EntityItem rootEntity =
			// m_elist.getParentEntityGroup().getEntityItem(0);
			// addDebug("*****mlm rootEntity = " + rootEntity.getEntityType() +
			// rootEntity.getEntityID());
			// NAME is navigate attributes - only used if error rpt is generated
			if (m_elist != null) {
				navName = getNavigationName();
				rootDesc = m_elist.getParentEntityGroup().getLongDescription();
				addDebug("navName=" + navName);
				generateReport();
				sendMail();
				addDebug("rootDesc" + rootDesc);
			}

			// build the text file

			
			 * generateFlatFile(rootEntity); sendMail(ffPathName);
			 
			// exeFtpShell(ffPathName);
			// ftpFile();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// println(e.toString());
			setReturnCode(FAIL);
			java.io.StringWriter exBuf = new java.io.StringWriter();
			String Error_EXCEPTION = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
			String Error_STACKTRACE = "<pre>{0}</pre>";
			msgf = new MessageFormat(Error_EXCEPTION);
			setReturnCode(INTERNAL_ERROR);
			e.printStackTrace(new java.io.PrintWriter(exBuf));
			// Put exception into document
			args[0] = e.getMessage();
			rptSb.append(msgf.format(args) + NEWLINE);
			msgf = new MessageFormat(Error_STACKTRACE);
			args[0] = exBuf.getBuffer().toString();
			rptSb.append(msgf.format(args) + NEWLINE);
			logError("Exception: " + e.getMessage());
			logError(exBuf.getBuffer().toString());
			// was an error make sure user gets report
			setCreateDGEntity(true);

			// sentFile=exeFtpShell(ffPathName);
		} finally {
			StringBuffer sb = new StringBuffer();
			msgf = new MessageFormat(HEADER2);
			args[0] = m_prof.getOPName();
			args[1] = m_prof.getRoleDescription();
			args[2] = m_prof.getWGName();
			args[3] = getNow();
			
			 * sb.append(creatFile ?
			 * "generated the Pipackage report file successful " :
			 * "generated the Pipackage report file faild"); sb.append(","); if
			 * (!hasData) sb.append("No EMEA Data!"); else
			 * sb.append("Generated the Pipackage report file successful ");
			 
			int count = modSet.size() + covertSet.size() + prodSet.size() + swprodSet.size() + secount;
			modSet.clear();
			covertSet.clear();
			prodSet.clear();
			swprodSet.clear();
			secount = 0;
			if (timelimit) {
				sb.append("The time period between FromDate:" + fromDate + " ToDate:" + toDate
						+ " is too long,it should be less than 3 years ");
			}

			else
				sb.append("Total count of non-Final entities from " + fromDate + " to " + toDate + " : " + count);
			// sb.append("From Date:" + fromDate + " To Date:" + toDate);
			sb.append("<br>");
			sb.append(report.toString().replaceAll("\n", "<br>"));
			args[4] = sb.toString();
			args[5] = abrversion + " " + getABRVersion();

			rptSb.insert(0, header1 + msgf.format(args) + NEWLINE);

			println(EACustom.getDocTypeHtml()); // Output the doctype and html
			println(rptSb.toString()); // Output the Report
			printDGSubmitString();

			println(EACustom.getTOUDiv());
			buildReportFooter(); // Print </html>
		}
	}

	private String getVEName() {
		// TODO Auto-generated method stub
		return "EXRPTANN";
	}

	private String getNavigationName() throws java.sql.SQLException, MiddlewareException {
		return getNavigationName(m_elist.getParentEntityGroup().getEntityItem(0));
	}

	*//**********************************************************************************
	 * Get Name based on navigation attributes for specified entity
	 *
	 * @return java.lang.String
	 *//*
	private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException {
		StringBuffer navName = new StringBuffer();
		// NAME is navigate attributes
		// check hashtable to see if we already got this meta
		EANList metaList = (EANList) metaTbl.get(theItem.getEntityType());
		if (metaList == null) {
			EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
			metaList = eg.getMetaAttribute(); // iterator does not maintain
												// navigate order
			metaTbl.put(theItem.getEntityType(), metaList);
		}
		for (int ii = 0; ii < metaList.size(); ii++) {
			EANMetaAttribute ma = (EANMetaAttribute) metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(), ", ", "", false));
			if (ii + 1 < metaList.size()) {
				navName.append(" ");
			}
		}
		return navName.toString();
	}

	*//**********************************
	 * add debug info as html comment
	 *//*
	protected void addDebug(String msg) {
		rptSb.append("<!-- " + msg + " -->" + NEWLINE);
	}

	*//******************************************
	 * get entitylist used for compares
	 *//*
	private EntityList getEntityList(String veName)
			throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException {
		addDebug("*****mlmVE name is " + getVEName());
		ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, veName);

		addDebug("*****mlmCreating Entity List");
		addDebug("*****mlmProfile is " + m_prof);
		addDebug("*****mlmRole is " + m_prof.getRoleCode() + " : " + m_prof.getRoleDescription());
		addDebug("*****mlmExtract action Item is" + eaItem);
		List annList = getAnnounceList();
		EntityList list = null;
		EntityItem[] entityItems = new EntityItem[annList.size()];
		if (annList.size() > 0) {

			for (int i = 0; i < annList.size(); i++) {

				entityItems[i] = new EntityItem(null, m_prof, "ANNOUNCEMENT",
						Integer.parseInt(annList.get(i).toString()));

				// debug display list of groups

			}
			list = m_db.getEntityList(m_prof, eaItem, entityItems);
			addDebug("EntityList for " + m_prof.getValOn() + " extract " + veName
					+ " contains the following entities: \n" + PokUtils.outputList(list));

		}

		return list;
	}

	private Vector getAvail() {
		int count = m_elist.getParentEntityGroup().getEntityItemCount();
		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
		addDebug("getParentEntityGroup Count:" + count);
		Set set = new HashSet();
		for (int i = 0; i < count; i++) {
			EntityItem ann = m_elist.getParentEntityGroup().getEntityItem(i);
			Vector vector = PokUtils.getAllLinkedEntities(ann, "ANNAVAILA", "AVAIL");
			set.addAll(vector);
		}
		addDebug("AVAIL:" + set);
		return new Vector(set);
	}

	private void generateReport() throws IOException, SQLException, MiddlewareException {
		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
		addDebug("avail:" + m_elist.getEntityGroup("AVAIL"));
		// Vector availGrp = getAvail();
		int availSize = availGrp == null ? 0 : availGrp.getEntityItemCount();
		addDebug("*****mlm availGrp.getEntityItemCount() = " + availSize);
		report.append(NEW_LINE);
		StringBuffer modelSb = new StringBuffer();
		StringBuffer modelCovertSb = new StringBuffer();
		StringBuffer prodSb = new StringBuffer();
		StringBuffer swProdSb = new StringBuffer();
		for (int availI = 0; availI < availSize; availI++) {
			EntityItem availEI = availGrp.getEntityItem(availI);
			String strAvailStautus = PokUtils.getAttributeValue(availEI, "STATUS", "", "");
			String strAvailType = PokUtils.getAttributeValue(availEI, "AVAILTYPE", "", "");
			String strAvailName = PokUtils.getAttributeValue(availEI, "AVAILANNTYPE", "", "");
			if ("Final".equals(strAvailStautus) && "Planned Availability".equals(strAvailType)) {

				String modelData = getModel(availEI);
				modelSb.append(modelData);
				String modelCovertData = getModelCovert(availEI);
				modelCovertSb.append(modelCovertData);
				String prodData = getProduct(availEI);
				prodSb.append(prodData);
				String swProdData = getSWProduct(availEI);
				swProdSb.append(swProdData);

			}

		}
		report.append(modelSb);
		report.append(modelCovertSb);
		report.append(prodSb);
		report.append(swProdSb);

		String svcModelData = getSVCModel();
		String fctData = getFCTransaction();

		report.append(svcModelData);
		report.append(fctData);

	}

	private String getModel(EntityItem availEI) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		Vector modelVect = PokUtils.getAllLinkedEntities(availEI, "MODELAVAIL", "MODEL");
		for (int i = 0; i < modelVect.size(); i++) {

			EntityItem eiModel = (EntityItem) modelVect.elementAt(i);
			if (modSet.contains(eiModel.getEntityID() + ""))
				continue;

			String status = PokUtils.getAttributeValue(eiModel, "STATUS", "", "");
			if (!FINNAL.equals(status)) {
				String machtype = PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", "", "");
				String modelart = PokUtils.getAttributeValue(eiModel, "MODELATR", "", "");
				sb.append("MODEL," + eiModel.getEntityID() + "," + machtype + "|" + modelart);
				sb.append(NEW_LINE);
				modSet.add(eiModel.getEntityID() + "");
			}

		}
		return sb.toString();
	}

	private String getProduct(EntityItem availEI) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		Vector productVect = PokUtils.getAllLinkedEntities(availEI, "OOFAVAIL", "PRODSTRUCT");
		String ids = "";

		for (int i = 0; i < productVect.size(); i++) {
			String machtype = null;
			String modelart = null;
			String featureCode = null;
			EntityItem eiProduct = (EntityItem) productVect.elementAt(i);
			if (prodSet.contains(eiProduct.getEntityID() + ""))
				continue;
			String status = PokUtils.getAttributeValue(eiProduct, "STATUS", "", "");
			if (!FINNAL.equals(status)) {
				ids += eiProduct.getEntityID() + ",";
				// eiProduct.getu
				Vector feaVec = eiProduct.getUpLink(); // PokUtils.getAllLinkedEntities(eiProduct,
														// "PRODUCT", "MODEL");
				Vector modelVec = eiProduct.getDownLink();// PokUtils.getAllLinkedEntities(eiProduct,
															// "PRODUCT",
															// "FEATURE");
				
				 * addDebug("uplink type" +
				 * eiProduct.getUpLink(0).getEntityType()); addDebug("down type"
				 * + eiProduct.getDownLink(0).getEntityType());
				 
				for (int j = 0; j < modelVec.size(); j++) {
					EntityItem eiModel = (EntityItem) modelVec.get(j);
					if ("MODEL".equals(eiModel.getEntityType())) {
						machtype = PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", "", "");
						modelart = PokUtils.getAttributeValue(eiModel, "MODELATR", "", "");
						break;
					}
				}
				for (int j = 0; j < feaVec.size(); j++) {
					EntityItem eiFeature = (EntityItem) feaVec.get(j);
					featureCode = PokUtils.getAttributeValue(eiFeature, "FEATURECODE", "", "");
					break;
				}

				
				 * addDebug("type:" + eiProduct.getUpLink(0).getEntityType());
				 * String machtype =
				 * eiProduct.getDownLink(0).getAttribute("MACHTYPEATR") == null
				 * ? "" : eiProduct.getDownLink(0).getAttribute("MACHTYPEATR").
				 * getLongDescription(); String modelart =
				 * eiProduct.getDownLink(0).getAttribute("MODELATR") == null ?
				 * "" : eiProduct.getDownLink(0).getAttribute("MODELATR").
				 * getLongDescription(); String featureCode =
				 * eiProduct.getUpLink(0).getAttribute("FEATURECODE") == null ?
				 * "" : eiProduct.getUpLink(0).getAttribute("FEATURECODE").
				 * getLongDescription();
				 
				prodSet.add(eiProduct.getEntityID() + "");

				sb.append(
						"PRODSTRUCT," + eiProduct.getEntityID() + "," + machtype + "|" + modelart + "|" + featureCode);
				sb.append(NEW_LINE);
			}

		}
		return sb.toString();
	}

	private String getSWProduct(EntityItem availEI) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		Vector swproductVect = PokUtils.getAllLinkedEntities(availEI, "SWPRODSTRUCTAVAIL", "SWPRODSTRUCT");

		String ids = "";
		for (int i = 0; i < swproductVect.size(); i++) {
			String machtype = null;
			String modelart = null;
			String featureCode = null;

			EntityItem eiSWProduct = (EntityItem) swproductVect.elementAt(i);
			if (swprodSet.contains(eiSWProduct.getEntityID() + ""))
				continue;
			String status = PokUtils.getAttributeValue(eiSWProduct, "STATUS", "", "");
			if (!FINNAL.equals(status)) {
				ids += eiSWProduct.getEntityID() + ",";
				Vector feaVec = eiSWProduct.getUpLink(); // PokUtils.getAllLinkedEntities(eiProduct,
															// "PRODUCT",
															// "MODEL");
				Vector modelVec = eiSWProduct.getDownLink();

				
				 * addDebug("type:" + eiSWProduct.getUpLink(0).getEntityType());
				 * addDebug("down type" +
				 * eiSWProduct.getDownLink(0).getEntityType());
				 
				// Vector modelVec = PokUtils.getAllLinkedEntities(eiSWProduct,
				// "SWPRODSTRUCT", "MODEL");
				// Vector feaVec = PokUtils.getAllLinkedEntities(eiSWProduct,
				// "SWPRODSTRUCT", "SWFEATURE");
				for (int j = 0; j < modelVec.size(); j++) {
					EntityItem eiModel = (EntityItem) modelVec.get(j);
					if ("MODEL".equals(eiModel.getEntityType())) {
						machtype = PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", "", "");
						modelart = PokUtils.getAttributeValue(eiModel, "MODELATR", "", "");
						break;
					}
				}
				for (int j = 0; j < feaVec.size(); j++) {
					EntityItem eiFeature = (EntityItem) feaVec.get(j);
					featureCode = PokUtils.getAttributeValue(eiFeature, "FEATURECODE", "", "");
					break;
				}
				
				 * String machtype = PokUtils.getAttributeValue(eiSWProduct,
				 * "MACHTYPEATR", "", ""); String modelart =
				 * PokUtils.getAttributeValue(eiSWProduct, "MODELATR", "", "");
				 * String featureCode = PokUtils.getAttributeValue(eiSWProduct,
				 * "FEATURECODE", "", "");
				 
				String data = "SWPRODSTRUCT," + eiSWProduct.getEntityID() + "," + machtype + "|" + modelart + "|"
						+ featureCode;

				swprodSet.add(eiSWProduct.getEntityID() + "");
				sb.append(data);
				sb.append(NEW_LINE);

			}

		}
		return sb.toString();
	}

	private String getModelCovert(EntityItem availEI) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		Vector modelCovertVect = PokUtils.getAllLinkedEntities(availEI, "MODELCONVERTAVAIL", "MODELCONVERT");
		for (int i = 0; i < modelCovertVect.size(); i++) {

			EntityItem eiModelCovert = (EntityItem) modelCovertVect.elementAt(i);
			if (covertSet.contains(eiModelCovert.getEntityID() + ""))
				continue;
			String status = PokUtils.getAttributeValue(eiModelCovert, "STATUS", "", "");
			if (!FINNAL.equals(status)) {

				String fromType = PokUtils.getAttributeValue(eiModelCovert, "FROMMACHTYPE", "", "");
				String fromModel = PokUtils.getAttributeValue(eiModelCovert, "FROMMODEL", "", "");
				String toType = PokUtils.getAttributeValue(eiModelCovert, "TOMACHTYPE", "", "");
				String toModel = PokUtils.getAttributeValue(eiModelCovert, "TOMODEL", "", "");
				sb.append("MODELCONVERT," + eiModelCovert.getEntityID() + "," + fromType + "|" + fromModel + "|"
						+ toType + "|" + toModel);
				sb.append(NEW_LINE);
				covertSet.add(eiModelCovert.getEntityID() + "");
				addDebug("ModelCovert:avail: type" + availEI.getEntityType() + "   id:" + availEI.getEntityID());
			}

		}
		return sb.toString();
	}

	private String getSVCModel() throws SQLException, MiddlewareException {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		Connection conn = null;
		// String sql = "select t2.ATTRIBUTEVALUE as
		// SMACHTYPEATR,t3.ATTRIBUTEVALUE as MODELATR,t2.entityid as ENTITYID
		// FROM opicm.text t1 inner join opicm.text t2 on t1.entitytype =
		// t2.entitytype and t1.entityid=t2.entityid and
		// t2.ATTRIBUTECODE='SMACHTYPEATR' inner join opicm.text t3 on
		// t3.entitytype=t1.entitytype and t3.entityid=t1.entityid and
		// t3.ATTRIBUTECODE='MODELATR' inner join opicm.flag status on
		// t1.entityid = status.entityid and t1.entitytype = status.entitytype
		// where t1.entitytype='SVCMOD' and t1.ATTRIBUTECODE = 'ANNDATE' and
		// t1.ATTRIBUTEVALUE between ? and ? and status.ATTRIBUTECODE='STATUS'
		// and status.ATTRIBUTEVALUE =? and t1.EFFTO>current timestamp and
		// t1.valto>current timestamp and t2.EFFTO>current timestamp and
		// t2.valto>current timestamp and t3.EFFTO>current timestamp and
		// t3.valto >current timestamp and status.EFFTO>current timestamp and
		// status.valto>current timestamp with ur";
		String sql = "select distinct t1.entityid as ENTITYID,t2.ATTRIBUTEVALUE as SMACHTYPEATR,t3.ATTRIBUTEVALUE as MODELATR FROM opicm.text t1 left join opicm.text t2 on t1.entitytype = t2.entitytype and t1.entityid=t2.entityid and t2.ATTRIBUTECODE='SMACHTYPEATR' and t2.EFFTO>current timestamp and t2.valto>current timestamp left join opicm.text t3 on t3.entitytype=t1.entitytype and t3.entityid=t1.entityid and t3.ATTRIBUTECODE='MODELATR' and t3.EFFTO>current timestamp and t3.valto >current timestamp where t1.entitytype='SVCMOD' and t1.EFFTO>current timestamp and t1.valto>current timestamp  and t1.entityid in (select distinct f.entityid from opicm.text t join opicm.flag f on t.entitytype=f.entitytype and t.entityid=f.entityid where t.entitytype='SVCMOD' and t.attributecode='ANNDATE' and (t.attributevalue between ? and ?) and t.valto> current timestamp and t.effto > current timestamp and ((f.attributecode='STATUS' and f.attributevalue<>'0020' and f.valto> current timestamp and f.effto > current timestamp) or not exists (select e1.entityid from opicm.flag e1 where e1.entityid=f.entityid and e1.entitytype=f.entitytype and e1.attributecode='STATUS' and e1.valto> current timestamp and e1.effto > current timestamp)))  with ur";
		addDebug(sql);
		conn = m_db.getPDHConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, fromDate);
		ps.setString(2, toDate);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {

			String smatr = rs.getString("SMACHTYPEATR");
			String atr = rs.getString("MODELATR");
			String entityID = rs.getString("ENTITYID");
			sb.append("SVCMOD," + entityID + "," + getDate(smatr) + "|" + getDate(atr));
			sb.append(NEW_LINE);
			// addDebug("sb:" + sb);

			secount++;
		}

		return sb.toString();
	}

	private String getFCTransaction() throws MiddlewareException, SQLException {
		// TODO Auto-generated method stub
		// FCTRANSACTION
		StringBuffer sb = new StringBuffer();
		Connection conn = null;
		// String sql = "select distinct fc.ATTRIBUTEVALUE as
		// FROMFEATURECODE,ft.ATTRIBUTEVALUE as FROMMACHTYPE,fm.ATTRIBUTEVALUE
		// as FROMMODEL,tc.ATTRIBUTEVALUE as TOFEATURECODE,tt.ATTRIBUTEVALUE as
		// TOMACHTYPE,tm.ATTRIBUTEVALUE as TOMODEL,tm.entityid as ENTITYID from
		// opicm.text fct inner join opicm.flag status on
		// fct.entitytype=status.entitytype and fct.entityid=status.entityid
		// inner join opicm.text fc on fc.entitytype = fct.entitytype and
		// fc.entityid=fct.entityid and fc.ATTRIBUTECODE='FROMFEATURECODE' inner
		// join opicm.text ft on ft.entitytype = fct.entitytype and
		// ft.entityid=fct.entityid and ft.ATTRIBUTECODE='FROMMACHTYPE' inner
		// join opicm.text fm on fm.entitytype = fct.entitytype and
		// fm.entityid=fct.entityid and fm.ATTRIBUTECODE='FROMMODEL' inner join
		// opicm.text tc on tc.entitytype = fct.entitytype and
		// tc.entityid=fct.entityid and tc.ATTRIBUTECODE='TOFEATURECODE' inner
		// join opicm.text tt on tt.entitytype = fct.entitytype and
		// tt.entityid=fct.entityid and tt.ATTRIBUTECODE='TOMACHTYPE' inner join
		// opicm.text tm on tm.entitytype = fct.entitytype and
		// tm.entityid=fct.entityid and tm.ATTRIBUTECODE='TOMODEL' where
		// fct.entitytype='FCTRANSACTION' and fct.ATTRIBUTECODE='ANNDATE' and
		// fct.ATTRIBUTEVALUE between ? and ? and status.ATTRIBUTECODE='STATUS'
		// and status.ATTRIBUTEVALUE =? and fct.effto>current timestamp and
		// fct.valto>current timestamp and status.valto>current timestamp and
		// status.effto>current timestamp and fc.effto>current timestamp and
		// fc.valto>current timestamp and ft.effto>current timestamp and
		// ft.valto>current timestamp and fm.effto>current timestamp and
		// fm.valto>current timestamp and tc.effto>current timestamp and
		// tc.valto>current timestamp and tt.effto>current timestamp and
		// tt.valto>current timestamp and tm.effto>current timestamp and
		// tm.valto>current timestamp with ur";
		String sql = "select distinct fct.entityid as ENTITYID,fc.ATTRIBUTEVALUE as FROMFEATURECODE,ft.ATTRIBUTEVALUE as FROMMACHTYPE,fm.ATTRIBUTEVALUE as FROMMODEL,tc.ATTRIBUTEVALUE as TOFEATURECODE,tt.ATTRIBUTEVALUE as TOMACHTYPE,tm.ATTRIBUTEVALUE as TOMODEL from opicm.text fct left join opicm.text fc on fc.entitytype = fct.entitytype and fc.entityid=fct.entityid and fc.ATTRIBUTECODE='FROMFEATURECODE' and fc.valto>current timestamp and fc.EFFTO>current timestamp left  join opicm.text ft on ft.entitytype = fct.entitytype and ft.entityid=fct.entityid and ft.ATTRIBUTECODE='FROMMACHTYPE' and ft.valto>current timestamp and ft.EFFTO>current timestamp left join opicm.text fm on fm.entitytype = fct.entitytype and fm.entityid=fct.entityid and fm.ATTRIBUTECODE='FROMMODEL' and fm.valto>current timestamp and fm.EFFTO>current timestamp left  join opicm.text tc on tc.entitytype = fct.entitytype and tc.entityid=fct.entityid and tc.ATTRIBUTECODE='TOFEATURECODE' and tc.valto>current timestamp and tc.EFFTO>current timestamp left join opicm.text tt on tt.entitytype = fct.entitytype and tt.entityid=fct.entityid and tt.ATTRIBUTECODE='TOMACHTYPE' and tt.valto>current timestamp and tt.EFFTO>current timestamp left join opicm.text tm on tm.entitytype = fct.entitytype and tm.entityid=fct.entityid and tm.ATTRIBUTECODE='TOMODEL' and tm.valto>current timestamp and tm.EFFTO>current timestamp where fct.entitytype='FCTRANSACTION' and fct.valto>current timestamp and fct.EFFTO>current timestamp and fct.entityid in (select distinct f.entityid from opicm.text t join opicm.flag f on t.entitytype=f.entitytype and t.entityid=f.entityid where t.entitytype='FCTRANSACTION' and t.attributecode='ANNDATE' and (t.attributevalue between ? and ?) and t.valto> current timestamp and t.effto > current timestamp and ((f.attributecode='STATUS' and f.attributevalue<>'0020' and f.valto> current timestamp and f.effto > current timestamp) or not exists (select e1.entityid from opicm.flag e1 where e1.entityid=f.entityid and e1.entitytype=f.entitytype and e1.attributecode='STATUS' and e1.valto> current timestamp and e1.effto > current timestamp))) with ur";
		addDebug(sql);
		conn = m_db.getPDHConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, fromDate);
		ps.setString(2, toDate);
		ResultSet rs = ps.executeQuery();
		addDebug("date" + rs.toString());
		addDebug("Result Set" + rs.toString());
		while (rs.next()) {

			String fromFeacode = rs.getString("FROMFEATURECODE");
			String fromType = rs.getString("FROMMACHTYPE");
			String fromModel = rs.getString("FROMMODEL");
			String toFeacode = rs.getString("TOFEATURECODE");
			String toType = rs.getString("TOMACHTYPE");
			String toModel = rs.getString("TOMODEL");
			String entityID = rs.getString("ENTITYID");
			sb.append("FCTransaction," + entityID + "," + getDate(fromFeacode) + "|" + getDate(fromType) + "|"
					+ getDate(fromModel) + "|" + getDate(toFeacode) + "|" + getDate(toType) + "|" + getDate(toModel));
			sb.append(NEW_LINE);
			secount++;
			// addDebug("sb:" + sb);

		}

		return sb.toString();

	}

	private List getAnnounceList() throws MiddlewareException, SQLException {
		List list = new ArrayList();
		// String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new
		// Date());
		// String data = PokUtils.getAttributeValue(rootEntity,
		// "DATACHKABRSTATUS", ",", "", false);
		// fromDate = PokUtils.getAttributeValue(rootEntity, "FROMDATE", ",",
		// currentDate, false);
		// toDate = PokUtils.getAttributeValue(rootEntity, "TODATE", ",",
		// currentDate, false);
		initDate();
		if (verifyDate(fromDate, toDate)) {

			// String sql = "select distinct entityid from opicm.text where
			// entitytype='ANNOUNCEMENT' AND ATTRIBUTECODE='ANNDATE' and
			// ATTRIBUTEVALUE between ? and ? with ur";
			String annSql = "select distinct ann.entityid from opicm.text ann inner join  opicm.flag  ANNTYPE  on ann.entitytype = ANNTYPE.entitytype and ann.entityid=ANNTYPE.entityid  and ANNTYPE.ATTRIBUTECODE='ANNTYPE' and ANNTYPE.valto>current timestamp and ANNTYPE.valto>current timestamp inner join opicm.metadescription meta on meta.DESCRIPTIONTYPE = ANNTYPE.ATTRIBUTECODE and meta.DESCRIPTIONCLASS = ANNTYPE.ATTRIBUTEVALUE and meta.NLSID=1 and meta.LONGDESCRIPTION = 'New' and meta.valto>current timestamp and meta.effto>current timestamp inner join opicm.flag STATUS on ann.entitytype = STATUS.entitytype and ann.entityid=STATUS.entityid and STATUS.ATTRIBUTECODE='ANNSTATUS' and STATUS.ATTRIBUTEVALUE='0020' and STATUS.valto>current timestamp and STATUS.effto>current timestamp where ann.entitytype='ANNOUNCEMENT' AND ann.ATTRIBUTECODE='ANNDATE' and ann.ATTRIBUTEVALUE between ? and ? and ann.valto>current timestamp and ann.effto>current timestamp with ur";
			Connection conn;
			try {

				conn = m_db.getPDHConnection();

				PreparedStatement ps = conn.prepareStatement(annSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, fromDate);
				ps.setString(2, toDate);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {

					list.add(rs.getString(1));
				}

				addDebug("ANNOUNCEMENT size:" + list.size() + "," + list);
			} catch (MiddlewareException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return list;
	}

	public void initDate() throws MiddlewareException, SQLException {
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		Connection conn;

		String sql = "select distinct t1.ATTRIBUTEVALUE FROMDATE,t2.ATTRIBUTEVALUE TODATE from opicm.text t1 inner join opicm.text t2 on t1.entityid =t2.entityid and t1.entitytype=t2.entitytype where t1.entitytype=? and t1.entityid=? and t1.ATTRIBUTECODE='FROMDATE' AND T2.ATTRIBUTECODE='TODATE' and t1.effto > current timestamp and t1.valto > current timestamp and t2.effto > current timestamp and t2.valto > current timestamp with ur";

		conn = m_db.getPDHConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		addDebug("Abr entitytype:" + getABREntityType() + "  ABR entityid:" + getABREntityID());
		ps.setString(1, getABREntityType());
		ps.setInt(2, getABREntityID());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {

			fromDate = rs.getString("FROMDATE");
			toDate = rs.getString("TODATE");
			if (toDate == null || toDate.trim().equals(""))
				toDate = currentDate;
			break;
		}

	}

	public boolean verifyDate(String DATE1, String DATE2) {

		String dateFormat = "\\d{4}-\\d{2}-\\d{2}";

		Pattern pattern = Pattern.compile(dateFormat);
		boolean faild = !(pattern.matcher(DATE1).find() && pattern.matcher(DATE2).find());
		if (faild) {
			addDebug("Data format is iscorrectly!");
			addDebug("FROMDATE =  " + DATE1);
			addDebug("TODATE = " + DATE2);
			return false;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);

			if (dt1.getTime() > dt2.getTime()) {
				return false;
			} else {
				dt1.setYear(dt1.getYear() + 3);
				if (dt1.getTime() < dt2.getTime()) {
					timelimit = true;
					return false;
				}

				return true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	public String getDate(String data) {
		return data == null || data.trim().equals("") ? "null" : data;
	}

	public void sendMail() {
		if (timelimit)
			report = new StringBuffer("The time period between FromDate:" + fromDate + " ToDate:" + toDate
					+ " is too long,it should be less than 3 years ");
		if (report.length() > 0) {
			setAttachByteForDG(report.toString().getBytes());
			getABRItem().setFileExtension("csv");
			addDebug("Sending mail");
		}

	}
}
*/