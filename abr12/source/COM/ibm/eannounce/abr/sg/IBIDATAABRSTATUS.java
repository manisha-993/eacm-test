package COM.ibm.eannounce.abr.sg;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
import COM.ibm.opicmpdh.objects.ControlBlock;

public class IBIDATAABRSTATUS extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();
	private String ffFileName = null;
	private String modelFileName = null;
	private String prodFileName = null;
	private String swFileName = null;
	private String dir = null;
	private String lineStr = null;
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);
	private Object[] args = new String[10];
	private static final String SUCCESS = "SUCCESS";
	private static final String FAILD = "FAILD";

	private static final String IBIPRTPATH = "_ibiprtpath";
	private static final String IBIINIPATH = "_ibiinipath";
	private static final String SFTPSCRPATH = "_sftpscrpath";
	private static final String TMODNAME = "_tmodname";
	private static final String TPRODNAME = "_tprodname";
	private static final String TSWPRODNAME = "_tswprodname";
	public static final String US_ASCII = "ASCII"; 

	String t1 = null;
	String t2 = null;
	String tmodel = null;
	String tprod = null;
	String tswprod = null;
	String type = "FULL";
	private static final String ENTITYTYPE = "ENTITYTYPE";
	private static final String MACHTYPEATR = "MACHTYPEATR";
	private static final String MODELATR = "MODELATR";
	private static final String MKTGNAME = "MKTGNAME";
	private static final String ANNDATE = "ANNDATE";
	private static final String WITHDRAWDATE = "WITHDRAWDATE";
	private static final String WTHDRWEFFCTVDATE = "WTHDRWEFFCTVDATE";
	private static final String INSTALL = "INSTALL";
	private static final String STATUS = "STATUS";
	private static final String VALFROM = "VALFROM";
	private static final String FEATURECODE = "FEATURECODE";
	private static final String EFFECTIVEDATE = "EFFECTIVEDATE";
	private static final String PANNDATE = "PANNDATE";
	private static final String LANNDATE = "LANNDATE";
	boolean sentFile = true;
	EntityItem rootEntity = null;
	private static final String COFCAT = "COFCAT";
	private static final String DIV = "DIV";
	private static final String COFSUBCAT = "COFSUBCAT";
	private static final String COFSUBGRP = "COFSUBGRP";
	Pattern p = Pattern.compile("\t|\r|\n");
	 byte bytes[] = {(byte) 0xC2,(byte) 0xA0};
	private String UTFSpace  = null;
	private static final String[] MODELCOLUMNS = new String[] { ENTITYTYPE, MACHTYPEATR, MODELATR, MKTGNAME, ANNDATE,
			WITHDRAWDATE, WTHDRWEFFCTVDATE, INSTALL, STATUS, VALFROM };
	private static final String[] PRODCOLUMNS = new String[] { ENTITYTYPE, MACHTYPEATR, MODELATR, FEATURECODE, MKTGNAME,
			ANNDATE, WITHDRAWDATE, WTHDRWEFFCTVDATE, INSTALL, STATUS, VALFROM };
	private static final String[] SWPRODCOLUMNS = new String[] { ENTITYTYPE, MACHTYPEATR, MODELATR, FEATURECODE,
			MKTGNAME, PANNDATE, LANNDATE, EFFECTIVEDATE, STATUS, VALFROM };

	private static final Hashtable COLUMN_LENGTH;
	static {
		COLUMN_LENGTH = new Hashtable();
		COLUMN_LENGTH.put(ENTITYTYPE, "15");
		COLUMN_LENGTH.put(MACHTYPEATR, "4");
		COLUMN_LENGTH.put(MODELATR, "3");
		COLUMN_LENGTH.put(MKTGNAME, "128");
		COLUMN_LENGTH.put(ANNDATE, "10");
		COLUMN_LENGTH.put(PANNDATE, "10");
		COLUMN_LENGTH.put(LANNDATE, "10");
		COLUMN_LENGTH.put(WITHDRAWDATE, "10");
		COLUMN_LENGTH.put(WTHDRWEFFCTVDATE, "10");
		COLUMN_LENGTH.put(INSTALL, "5");
		COLUMN_LENGTH.put(STATUS, "6");
		COLUMN_LENGTH.put(VALFROM, "26");
		COLUMN_LENGTH.put(FEATURECODE, "6");
		COLUMN_LENGTH.put(EFFECTIVEDATE, "10");

	}

	// m_elist.getParentEntityGroup().getEntityItem(0)
	private final static String QUERY = "select m.machtypeatr,m.modelatr,m.mktgname,m.install,m.cofcat,m.cofsubcat,m.cofsubgrp,m.anndate,m.withdrawdate,m.WTHDRWEFFCTVDATE,S.div,m.status from price.model m join price.SGMNTACRNYM S on m.PRFTCTR=S.PRFTCTR where m.nlsid=1  and m.cofcat='Hardware' and m.status in ('Final','Ready for Review') and s.nlsid=1 and S.DIV<>'71 Retail Store Systems' and m.INSTALL='CE' with ur";

	// private final static String QUERY_MODEL = "select
	// ENTITYTYPE,MACHTYPEATR,MODELATR,MKTGNAME,ANNDATE,WITHDRAWDATE,WTHDRWEFFCTVDATE,INSTALL,STATUS,VALFROM
	// from OPICM.MODEL where VALFROM>= ? and VALFROM < ? and nlsid =1 and
	// status='Final' with UR";

	private final static String QUERY_MODEL = "with dump(machtypeatr,modelatr) as (select machtypeatr, modelatr from opicm.model where nlsid = 1 and cofcat = 'Software' and status = 'Final'  "
			+ "group by machtypeatr, modelatr having count(*) >1 ),dump1(id) as( "
			+ "select m.entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and entityid not in  "
			+ "(select entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and m.status ='Final' and m.COFGRP = 'Base' and m.COFSUBCAT = 'Application'  and m.valto > current timestamp and m.nlsid=1 ))  "
			+ "select distinct m.ENTITYTYPE,m.MACHTYPEATR,m.MODELATR,m.MKTGNAME,m.ANNDATE,m.WITHDRAWDATE,m.WTHDRWEFFCTVDATE,m.INSTALL,m.STATUS,m.VALFROM from opicm.model m  "
			+ "left join opicm.SGMNTACRNYM S on m.prftctr = S.prftctr and S.nlsid=1  "
			+ "where   m.status='Final' and m.nlsid=1 and (S.DIV<>'71' or S.div is null) and m.entityid not in (select id from dump1)and  m.VALFROM>= ? and m.VALFROM < ?  "
			+ "with ur";
	// private final static String QUERY_PRODSTRUCT = "select
	// r.entitytype,m.MACHTYPEATR,m.MODELATR,f.FEATURECODE,p.MKTGNAME,p.ANNDATE,p.WITHDRAWDATE,p.WTHDRWEFFCTVDATE,p.INSTALL,p.STATUS,p.VALFROM
	// from OPICM.MODEL m join opicm.relator r on r.entitytype='PRODSTRUCT' and
	// r.entity2id=m.entityid join opicm.feature f on f.entityid=r.entity1id and
	// f.status='Final' and f.nlsid=1 join opicm.prodstruct p on
	// p.entityid=r.entityid and p.nlsid=1 and p.status='Final' where
	// p.VALFROM>= ? and p.VALFROM < ? and m.status='Final' and m.nlsid=1 with
	// ur";
	private final static String QUERY_PRODSTRUCT = "with dump(machtypeatr,modelatr) as (select machtypeatr, modelatr from opicm.model where nlsid = 1 and cofcat = 'Software' and status = 'Final' "
			+ "group by machtypeatr, modelatr having count(*) >1 ),dump1(id) as( "
			+ "select m.entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and entityid not in "
			+ "(select entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and m.status ='Final' and m.COFGRP = 'Base' and m.COFSUBCAT = 'Application'  and m.valto > current timestamp and valto > current timestamp ))"
			+ "select  r.entitytype,m.MACHTYPEATR,m.MODELATR,f.FEATURECODE,p.MKTGNAME,p.ANNDATE,p.WITHDRAWDATE,p.WTHDRWEFFCTVDATE,p.INSTALL,p.STATUS,p.VALFROM from OPICM.MODEL m join opicm.relator r on r.entitytype='PRODSTRUCT' and r.entity2id=m.entityid join opicm.feature f on f.entityid=r.entity1id and f.status='Final' and f.nlsid=1 join opicm.prodstruct p on p.entityid=r.entityid and p.nlsid=1 and p.status='Final' "
			+ "left join opicm.SGMNTACRNYM S on m.prftctr = S.prftctr and S.nlsid=1 "
			+ "where  p.VALFROM>= ? and p.VALFROM < ? and m.status='Final' and  m.nlsid=1  and m.entityid not in (select id from dump1)  and (S.DIV<>'71' or S.div is null) with ur";

	/*private final static String QUERY_SWPRODSTRUCT = "with temp as ("
			+ "select sw.entitytype ,m.MACHTYPEATR,m.MODELATR,f.FEATURECODE,sw.MKTGNAME,coalesce(annp.ANNDATE,'9999-12-32') AS PANNDATE,coalesce( annl.ANNDATE,'9999-12-32') as LANNDATE,coalesce(a1.EFFECTIVEDATE,'9999-12-32') as EFFECTIVEDATE,sw.STATUS,sw.VALFROM,max(coalesce(sw.VALFROM,'1980-01-01 00:00:00.000000') ,coalesce(a1.VALFROM,'1980-01-01 00:00:00.000000'),coalesce(a.VALFROM,'1980-01-01 00:00:00.000000'),coalesce(annp.VALFROM,'1980-01-01 00:00:00.000000'),coalesce(annl.VALFROM,'1980-01-01 00:00:00.000000')) as MAXDATA from opicm.SWPRODSTRUCT sw "
			+ "join opicm.relator r2 on r2.entitytype = 'SWPRODSTRUCT' and r2.entityid = sw.entityid "
			+ "join opicm.model m on m.nlsid =1 and m.entityid = r2.entity2id and m.status='Final' "
			+ "join opicm.swfeature f on f.nlsid =1 and f.entityid = r2.entity1id and f.status='Final' "
			+ "left join  opicm.relator r on  r.entitytype = 'SWPRODSTRUCTAVAIL' and r.entity1id = sw.entityid "
			+ "left join  opicm.avail a on a.entityid = r.entity2id and a.nlsid=1 and  a.AVAILTYPE='Planned Availability' and a.status='Final' "
			+ "left join opicm.announcement annp on  a.anncodename=annp.anncodename and annp.nlsid=1 and annp.ANNSTATUS ='Final' "
			+ "left join  opicm.relator r1 on  r1.entitytype = 'SWPRODSTRUCTAVAIL' and r1.entity1id = sw.entityid "
			+ "left join  opicm.avail a1 on a1.entityid = r1.entity2id and a1.nlsid=1 and  a1.AVAILTYPE='Last Order'  AND a1.status='Final'  "
			+ "left join opicm.announcement annl on  a1.anncodename=annl.anncodename and annl.nlsid=1  and annl.ANNSTATUS ='Final' "
			+ "left join opicm.SGMNTACRNYM S on m.prftctr = S.prftctr and S.nlsid=1 and (S.DIV<>'71' or S.div is null) "
			+ "where  sw.status='Final' and sw.nlsid=1  )"
			+ "select distinct entitytype ,MACHTYPEATR,MODELATR,FEATURECODE,MKTGNAME,(case min(PANNDATE) when '9999-12-32' then '' else min(PANNDATE) end )as PANNDATE,(case min(LANNDATE) when '9999-12-32' then '' else min(LANNDATE) end ) as LANNDATE,(case min(EFFECTIVEDATE) when '9999-12-32' then '' else min(EFFECTIVEDATE) end ) as EFFECTIVEDATE,STATUS,VALFROM from temp "
			+ "group by entitytype ,MACHTYPEATR,MODELATR,FEATURECODE,MKTGNAME,VALFROM,STATUS "
			+ "having max(MAXDATA) >= ? and max(MAXDATA) < ?  " + "with ur";*/
	private final static String QUERY_SWPRODSTRUCT = "with dump(machtypeatr,modelatr) as (select machtypeatr, modelatr from opicm.model where nlsid = 1 and cofcat = 'Software' and status = 'Final' "
			+ "group by machtypeatr, modelatr having count(*) >1 ),dump1(id) as( "
			+ "select m.entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and entityid not in "
			+ "(select entityid from dump d inner join opicm.model m on d.machtypeatr = m .machtypeatr and m.modelatr=d.modelatr and m.status ='Final' and m.COFGRP = 'Base' and m.COFSUBCAT = 'Application'  and m.valto > current timestamp and valto > current timestamp ))"
			+",temp as ("
			+ "select sw.entitytype ,m.MACHTYPEATR,m.MODELATR,f.FEATURECODE,sw.MKTGNAME,coalesce(annp.ANNDATE,'9999-12-32') AS PANNDATE,coalesce( annl.ANNDATE,'9999-12-32') as LANNDATE,coalesce(a1.EFFECTIVEDATE,'9999-12-32') as EFFECTIVEDATE,sw.STATUS,sw.VALFROM,max(coalesce(sw.VALFROM,'1980-01-01 00:00:00.000000') ,coalesce(a1.VALFROM,'1980-01-01 00:00:00.000000'),coalesce(a.VALFROM,'1980-01-01 00:00:00.000000'),coalesce(annp.VALFROM,'1980-01-01 00:00:00.000000'),coalesce(annl.VALFROM,'1980-01-01 00:00:00.000000')) as MAXDATA from opicm.SWPRODSTRUCT sw "
			+ "join opicm.relator r2 on r2.entitytype = 'SWPRODSTRUCT' and r2.entityid = sw.entityid "
			+ "join opicm.model m on m.nlsid =1 and m.entityid = r2.entity2id and m.status='Final' "
			+ "join opicm.swfeature f on f.nlsid =1 and f.entityid = r2.entity1id and f.status='Final' "
			+ "left join  opicm.relator r on  r.entitytype = 'SWPRODSTRUCTAVAIL' and r.entity1id = sw.entityid "
			+ "left join  opicm.avail a on a.entityid = r.entity2id and a.nlsid=1 and  a.AVAILTYPE='Planned Availability' and a.status='Final' "
			+ "left join opicm.announcement annp on  a.anncodename=annp.anncodename and annp.nlsid=1 and annp.ANNSTATUS ='Final' "
			+ "left join  opicm.relator r1 on  r1.entitytype = 'SWPRODSTRUCTAVAIL' and r1.entity1id = sw.entityid "
			+ "left join  opicm.avail a1 on a1.entityid = r1.entity2id and a1.nlsid=1 and  a1.AVAILTYPE='Last Order'  AND a1.status='Final'  "
			+ "left join opicm.announcement annl on  a1.anncodename=annl.anncodename and annl.nlsid=1  and annl.ANNSTATUS ='Final' "
			+ "left join opicm.SGMNTACRNYM S on m.prftctr = S.prftctr and S.nlsid=1 "
			+ "where  sw.status='Final' and sw.nlsid=1  and m.entityid not in (select id from dump1) )  and (S.DIV<>'71' or S.div is null) "
			+ "select distinct entitytype ,MACHTYPEATR,MODELATR,FEATURECODE,MKTGNAME,(case min(PANNDATE) when '9999-12-32' then '' else min(PANNDATE) end )as PANNDATE,(case min(LANNDATE) when '9999-12-32' then '' else min(LANNDATE) end ) as LANNDATE,(case min(EFFECTIVEDATE) when '9999-12-32' then '' else min(EFFECTIVEDATE) end ) as EFFECTIVEDATE,STATUS,VALFROM from temp "
			+ "group by entitytype ,MACHTYPEATR,MODELATR,FEATURECODE,MKTGNAME,VALFROM,STATUS "
			+ "having max(MAXDATA) >= ? and max(MAXDATA) < ?  " + "with ur";

	private void setFileName() throws IOException {
		// FILE_PREFIX=EACM_TO_QSM_
		String fileprefix = ABRServerProperties.getFilePrefix(m_abri.getABRCode());
		// ABRServerProperties.getOutputPath()
		// ABRServerProperties.get
		StringBuffer sb = new StringBuffer();
		String dts = getNow();
		// replace special characters
		dts = dts.replace(' ', '_');
		sb.append(dts + ".txt");
		dir = ABRServerProperties.getValue(m_abri.getABRCode(), IBIPRTPATH, "/Dgq");
		tmodel = ABRServerProperties.getValue(m_abri.getABRCode(), TMODNAME);
		tprod = ABRServerProperties.getValue(m_abri.getABRCode(), TPRODNAME);
		tswprod = ABRServerProperties.getValue(m_abri.getABRCode(), TSWPRODNAME);
		if (!dir.endsWith("/")) {
			dir = dir + "/";
		}
		ffFileName = sb.toString();
		modelFileName = dir + fileprefix.trim() + "MODEL" + ffFileName;
		prodFileName = dir + fileprefix.trim() + "PRODSTRUCT" + ffFileName;
		swFileName = dir + fileprefix.trim() + "SWPRODSTRUCT" + ffFileName;

		// addDebug("ffPathName: " + ffPathName + " ffFileName: " + ffFileName);
	}

	public void execute_run() {

		// TODO Auto-generated method stub
		String HEADER = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE
				+ EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">"
				+ EACustom.getMastheadDiv() + NEWLINE
				+ "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
		String HEADER2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE
				+ "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>"
				+ NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Result: </th><td>{4}</td></tr>"
				+ NEWLINE + "</table>" + "<!-- {5} -->" + NEWLINE;
		MessageFormat msgf;
		String abrversion = "";
		Object[] args = new String[10];
		String header1 = "";
		boolean creatFile = true;

		try {
			start_ABRBuild(false);
			setReturnCode(PASS);
			UTFSpace =  new String(bytes,"utf-8");
			// m_elist = getEntityList("dummy");
			m_elist = m_db.getEntityList(m_prof, new ExtractActionItem(null, m_db, m_prof, "dummy"),
					new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });

			// get the root entity using current timestamp, need this to get the
			// timestamps or info for VE pulls
			/*
			 * m_elist = m_db.getEntityList(m_prof, new ExtractActionItem(null,
			 * m_db, m_prof,"dummy"), new EntityItem[] { new EntityItem(null,
			 * m_prof, getEntityType(), getEntityID()) });
			 */
			rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

			t1 = PokUtils.getAttributeValue(rootEntity, "IBIDATADTS", "", "1980-01-01-00.00.00.000000");
			// System.out.println("temp:"+temp);
			if ("1980-01-01-00.00.00.000000".equals(t1)) {
				type = "FULL";
			} else {
				type = "DELTA";
			}
			t2 = getNow();
			msgf = new MessageFormat(HEADER);
			args[0] = getShortClassName(getClass());
			args[1] = "ABR";
			header1 = msgf.format(args);

			setDGTitle("IBIDATAABRSTATUS report");
			setDGString(getABRReturnCode());
			setDGRptName("IBIDATAABRSTATUS"); // Set the report name
			setDGRptClass("IBIDATAABRSTATUS"); // Set the report class
			setFileName();
			generateFlatFile();

		} catch (Exception e) {

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
			creatFile = false;
		}

		// getReturnCode();
		finally {
			StringBuffer sb = new StringBuffer();
			msgf = new MessageFormat(HEADER2);
			args[0] = m_prof.getOPName();
			args[1] = m_prof.getRoleDescription();
			args[2] = m_prof.getWGName();
			args[3] = getNow();
			sb.append("Time start from " + t1 + " to " + t2 + "<br>");
			sb.append(creatFile ? "generated the IBI data report file successful "
					: "generated the IBI data report file faild");
			sb.append(",");
			sb.append(sentFile ? "send the IBI data report file successful " : "sent the IBI data report file faild");
			/*
			 * if (!sentFile) sb.append(lineStr.replaceFirst(FAILD, ""));
			 */
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

	private void generateModel(/* EntityItem rootEntity */) throws IOException, MiddlewareException, SQLException {
		// generate file name

		FileOutputStream fos = new FileOutputStream(modelFileName);
		// OutputStreamWriter will convert from characters to bytes using
		// the specified character encoding or the platform default if none
		// is specified. Output as unicode
		OutputStreamWriter wOut = new OutputStreamWriter(fos,US_ASCII);

		// build the text file
		String dtsDb = getNow(); // format this
		String date = dtsDb.substring(0, 10);
		String time = dtsDb.substring(11, 19);
		String sec = dtsDb.substring(19);
		time = time.replace('.', ':');
		String dts = date + " " + time + sec;
		Connection conn = m_db.getODS2Connection();
		PreparedStatement ps = conn.prepareStatement(QUERY_MODEL, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ps.setString(1, t1);
		ps.setString(2, t2);
		System.out.println("query:" + QUERY_MODEL + ":" + t1 + ":" + t2);
		ResultSet rs = ps.executeQuery();
		int count = 0;

		if (rs.last()) {
			count = rs.getRow();
			wOut.write(type + "EACM" + dts + count);
			wOut.write(NEW_LINE);
			rs.first();
		}
		if (count == 0) {
			wOut.write(type + "EACM" + dts + count);
			wOut.write(NEW_LINE);
			wOut.close();
			return;
		}
		do {

			for (int i = 0; i < MODELCOLUMNS.length; i++) {
				String value = rs.getString(MODELCOLUMNS[i]);
				value = value == null ? "" : value.trim();
				wOut.write(getValue(value, MODELCOLUMNS[i]));
				if (INSTALL.equals(MODELCOLUMNS[i])) {
					if ("CE".equals(value)) {
						wOut.write("IBI");
					} else if ("CIF".equals(value)) {
						
						wOut.write("CSU");
					} else {
						wOut.write("   ");
					}
				}
			}
			wOut.write(NEW_LINE);
			wOut.flush();
		} while (rs.next());
		wOut.flush();
	}

	private void generateProd(/* EntityItem rootEntity */) throws IOException, MiddlewareException, SQLException {
		// generate file name

		FileOutputStream fos = new FileOutputStream(prodFileName);
		// OutputStreamWriter will convert from characters to bytes using
		// the specified character encoding or the platform default if none
		// is specified. Output as unicode
		OutputStreamWriter wOut = new OutputStreamWriter(fos, US_ASCII);

		// build the text file
		String dtsDb = getNow(); // format this
		String date = dtsDb.substring(0, 10);
		String time = dtsDb.substring(11, 19);
		String sec = dtsDb.substring(19);
		time = time.replace('.', ':');
		String dts = date + " " + time + sec;

		Connection conn = m_db.getODS2Connection();
		PreparedStatement ps = conn.prepareStatement(QUERY_PRODSTRUCT, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ps.setString(1, t1);
		ps.setString(2, t2);
		System.out.println("query:" + QUERY_PRODSTRUCT + ":" + t1 + ":" + t2);
		ResultSet rs = ps.executeQuery();
		int count = 0;
		if (rs.last()) {
			count = rs.getRow();
			wOut.write(type + "EACM" + dts + count);
			wOut.write(NEW_LINE);

			// println("EACM" + dts + " " + count);
			rs.first();
		}
		if (count == 0) {
			wOut.write(type + "EACM" + dts + count);
			wOut.write(NEW_LINE);
			wOut.close();
			return;
		}
		do {

			for (int i = 0; i < PRODCOLUMNS.length; i++) {
				String value = rs.getString(PRODCOLUMNS[i]);
				wOut.write(getValue(value, PRODCOLUMNS[i]));
				if (INSTALL.equals(PRODCOLUMNS[i])) {
					if ("CE".equals(value)) {
						wOut.write("IBI");
					} else if ("CIF".equals(value)) {

						wOut.write("CSU");
					} else {
						wOut.write("   ");
					}
				}

			}
			wOut.write(NEW_LINE);
			wOut.flush();
		} while (rs.next());
		wOut.flush();
	}

	private void generateSWProd(/* EntityItem rootEntity */) throws IOException, MiddlewareException, SQLException {
		// generate file name

		FileOutputStream fos = new FileOutputStream(swFileName);
		// OutputStreamWriter will convert from characters to bytes using
		// the specified character encoding or the platform default if none
		// is specified. Output as unicode
		OutputStreamWriter wOut = new OutputStreamWriter(fos, US_ASCII);

		// build the text file
		String dtsDb = getNow(); // format this
		String date = dtsDb.substring(0, 10);
		String time = dtsDb.substring(11, 19);
		String sec = dtsDb.substring(19);
		time = time.replace('.', ':');
		String dts = date + " " + time + sec;

		Connection conn = m_db.getODS2Connection();
		PreparedStatement ps = conn.prepareStatement(QUERY_SWPRODSTRUCT, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ps.setString(1, t1);
		ps.setString(2, t2);
		System.out.println("query:" + QUERY_SWPRODSTRUCT + ":" + t1 + ":" + t2);
		ResultSet rs = ps.executeQuery();
		int count = 0;
		if (rs.last()) {
			count = rs.getRow();
			wOut.write(type + "EACM" + dts + count);
			wOut.write(NEW_LINE);
			// println("EACM" + dts + " " + count);
			rs.first();
		}
		if (count == 0) {
			wOut.write(type + "EACM" + dts + count);
			wOut.write(NEW_LINE);
			wOut.close();
			return;
		}
		do {

			for (int i = 0; i < SWPRODCOLUMNS.length; i++) {
				String value = rs.getString(SWPRODCOLUMNS[i]);
				wOut.write(getValue(value, SWPRODCOLUMNS[i]));
			}
			wOut.write(NEW_LINE);
			wOut.flush();
		} while (rs.next());
		wOut.flush();
	}

	private void generateFlatFile(/* EntityItem rootEntity */) throws IOException, MiddlewareException, SQLException {
		// generate file name
		try {
			generateModel();
			sentFile = exeFtpShell(modelFileName, tmodel, "'SMS.UTIL.CNTL(CSF0005)'");
			generateProd();
			sentFile = exeFtpShell(prodFileName, tprod, "'SMS.UTIL.CNTL(CSF0006)'");
			generateSWProd();
			sentFile = exeFtpShell(swFileName, tswprod, "'SMS.UTIL.CNTL(CSF0007)'");
			setTextValue(m_elist.getProfile(), "IBIDATADTS", t2, rootEntity);
		} catch (Exception e) {
			e.printStackTrace();
			sentFile = false;
		}

	}

	protected String getValue(String columnValue, String columnName) {
		
		columnValue = remove(columnValue);
		try {
			columnValue = new String(columnValue.getBytes("UTF-8"),US_ASCII);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int columnValueLength = columnValue == null ? 0 : columnValue.length();
		int columnNameLength = Integer.parseInt(COLUMN_LENGTH.get(columnName).toString());
		if (columnValueLength == columnNameLength)
			return columnValue;
		if (columnValueLength > columnNameLength)
			return columnValue.substring(0, columnNameLength);

		return columnValue + getBlank(columnNameLength - columnValueLength);
	}

	public String replaceBlank(String str) {
		String dest = "";
		if (str != null) {

			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	protected String getBlank(int count) {
		StringBuffer sb = new StringBuffer();
		while (count > 0) {
			sb.append(" ");
			count--;
		}
		return sb.toString();
	}

	public boolean exeFtpShell(String fileName, String tarName, String triger) {
		// String cmd =
		// "/usr/bin/rsync -av /var/log/www.solive.kv/access_log
		// testuser@10.0.1.219::store --password-file=/etc/client/rsync.pwd";

		String cmd = ABRServerProperties.getValue(m_abri.getABRCode(), SFTPSCRPATH, null) + " -f " + fileName + " -t '"+ tarName + "'" + " -c " + triger;
		String ibiinipath = ABRServerProperties.getValue(m_abri.getABRCode(), IBIINIPATH, null);
		if (ibiinipath != null)
			cmd += " -i " + ibiinipath;
		Runtime run = Runtime.getRuntime();
		String result = "";
		BufferedReader br = null;
		BufferedInputStream in = null;
		System.out.println("cmd:" + cmd);
		try {
			Process p = run.exec(cmd);
			if (p.waitFor() != 0) {
				return false;
			}
			in = new BufferedInputStream(p.getInputStream());
			br = new BufferedReader(new InputStreamReader(in));
			while ((lineStr = br.readLine()) != null) {
				result += lineStr;
				if (lineStr.indexOf(FAILD) > -1) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (br != null) {
				try {
					br.close();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result == null ? false : true;
	}

	public String getDescription() {
		return "IBIDATAABRSTATUS";
	}

	/*
	 * protected void setTextValue(){ m_db.update(arg0, arg1);
	 * 
	 * }
	 */
	protected void setTextValue(Profile profile, String _sAttributeCode, String _sAttributeValue, EntityItem eitem) {
		logMessage(
				getDescription() + " ***** " + eitem.getKey() + " " + _sAttributeCode + " set to: " + _sAttributeValue);

		// if meta does not have this attribute, there is nothing to do
		EANMetaAttribute metaAttr = eitem.getEntityGroup().getMetaAttribute(_sAttributeCode);
		if (metaAttr == null) {
			logMessage(getDescription() + " ***** " + _sAttributeCode + " was not in meta for " + eitem.getEntityType()
					+ ", nothing to do");
			return;
		}

		if (_sAttributeValue != null) {
			if (m_cbOn == null) {
				setControlBlock(); // needed for attribute updates
			}
			ControlBlock cb = m_cbOn;
			if (_sAttributeValue.length() == 0) { // deactivation is now needed
				EANAttribute att = eitem.getAttribute(_sAttributeCode);
				String efffrom = att.getEffFrom();
				cb = new ControlBlock(efffrom, efffrom, efffrom, efffrom, profile.getOPWGID());
				_sAttributeValue = att.toString();
			}
			Vector vctAtts = new Vector();
			Vector vctReturnsEntityKeys = new Vector();
			// look at each key to see if this item is there yet
			ReturnEntityKey rek = new ReturnEntityKey(eitem.getEntityType(), eitem.getEntityID(), true);
			rek.m_vctAttributes = vctAtts;
			vctReturnsEntityKeys.addElement(rek);

			COM.ibm.opicmpdh.objects.Text sf = new COM.ibm.opicmpdh.objects.Text(profile.getEnterprise(),
					eitem.getEntityType(), eitem.getEntityID(), _sAttributeCode, _sAttributeValue, 1, cb);
			vctAtts.addElement(sf);
			try {
				m_db.update(profile, vctReturnsEntityKeys);
			} catch (MiddlewareBusinessRuleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MiddlewareException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private EntityList getEntityList(String veName)
			throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException {

		ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, veName);

		EntityList list = m_db.getEntityList(m_prof, eaItem,
				new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });

		// debug display list of groups

		return list;
	}

	/***********************************************
	 * Get the version
	 * 
	 * @return java.lang.String
	 */
	public String getABRVersion() {
		return "1.0";
	}
	public String remove(String sReturn) {
		sReturn = replaceBlank(sReturn).trim().replace(UTFSpace, " ");
		sReturn = replaceBlank(sReturn).trim().replace(UTFSpace, " ");
		//“Hardware”
		sReturn = sReturn.replace("“", "\"");
		sReturn = sReturn.replace("”", "\"");
		sReturn = sReturn.replace("’", "'").replace("‘", "'");
		// 6756N92RTS for VMware ESXi - 2 Sockets - 3yr
		return sReturn;
	}
}
