package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.MQUsage;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.Blob;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.opicmpdh.transactions.OPICMList;
import java.io.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.ibm.mq.MQException;

public class Catcher {

    ProfileSet psLogin;
    public Profile m_prof;
    private Database m_dbPDH;
    private static Connection m_conPDH = null;
    private String m_strNow;
    private String m_strTimeStampForever;
    private static SimpleDateFormat c_sdfTimestamp = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
    //static final String UPDATEAL = new String("POWERUSER");
    String UPDATEAL = null;
    EntityItem m_pdhi;
    String m_strSchemaPDH;
    String m_strEnterprise;
    String m_strRunMode;
    String m_strLdapId;
    String m_strLdapPwd;
    String m_strOPICMVersion;
    String m_domainAttr;
    String EntityType;
    String EntityIDs;
    String LockAction;
    String FlagAttr;
    String QueueABRSTATUS;
    String abrValue;
    String LongMSG;

    public Catcher() {
        psLogin = null;
        m_prof = null;
        m_dbPDH = null;
        m_strNow = null;
        m_strTimeStampForever = null;
        m_pdhi = null;
        m_strSchemaPDH = null;
        m_strEnterprise = null;
        m_strRunMode = null;
        m_strLdapId = null;
        m_strLdapPwd = null;
        m_strOPICMVersion = null;
        m_domainAttr = null;
        EntityType = null;
        EntityIDs = null;
        LockAction = null;
        FlagAttr = "PDHDOMAIN";
        LongMSG = null;
        QueueABRSTATUS = null;
        abrValue = null;
        //connectPDH();
        System.out.println("HI");
    }

    public void readPropertyFile() {
        printOK("Reading Catcher.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("Catcher.properties"));
        }
        catch (IOException ioexception) {
            printErr("Error reading Catcher.properties");
            System.exit(1);
        }
        m_strSchemaPDH = properties.getProperty("PDHSCHEMA");
        m_strEnterprise = properties.getProperty("ENTERPRISE");
        m_strRunMode = properties.getProperty("RUNMODE");
        m_strLdapId = properties.getProperty("LDAPID", null);
        m_strLdapPwd = properties.getProperty("LDAPPWD", null);
        m_strOPICMVersion = properties.getProperty("OPICMVERSION", null);
        String s = properties.getProperty("ENTITYTYPE", null);
        String s1 = properties.getProperty("ENTITYID", null);
        m_domainAttr = properties.getProperty("PDHDOMAIN", null);
        QueueABRSTATUS = properties.getProperty("ABRQUEUESTATUS", null);
        LongMSG = properties.getProperty("LONGMSG", null);
        UPDATEAL = properties.getProperty("ROLECODE", null);
        abrValue = properties.getProperty("QUEUEVALUE", null);
        
        printOK("****Catcher Properties are:" + m_strSchemaPDH + " " + m_strEnterprise + " " + m_strRunMode + " " + m_strLdapId + " " + m_strLdapPwd + " " + m_strOPICMVersion + " " + s + " " + s1 + " " + m_domainAttr + " "+ QueueABRSTATUS + " "+ LongMSG + " " + UPDATEAL + " "+ abrValue);
        EntityType = s;
        EntityIDs = s1;
    }

    public static void main(String args[]) throws Exception {
        createRptFolder();
        adjustSystemOut();
        long l = System.currentTimeMillis();
        System.out.println("**** 11: " + System.currentTimeMillis());
    	Catcher catcher = new Catcher();
    	if (args!= null && args.length >0 ){
    		if("testDB".equals(args[0])){
                catcher.connectPDH(); 
                printOK("testDB done!");
            }else if ("testMQ".equals(args[0])){
            	Vector mqvct = catcher.getAllMQMessage();
            	printOK("testMQ" + mqvct.toString());
            	mqvct.clear();
            }else if ("test".equals(args[0])){ 
            	catcher.connectPDH();
            	catcher.test();
    	    }       
        }else {
        	catcher.connectPDH();
        	Vector mqvct = catcher.getAllMQMessage();
        	catcher.createXMLMSG(mqvct);
        	mqvct.clear();
        }
        
      
        
        long l1 = System.currentTimeMillis();
        System.out.println("**** 12: " + System.currentTimeMillis());
        long l2 = Math.abs(l - l1);
        System.out.println("**** diff: " + l2);
        long l3 = Math.round(l2 / 1000L);
        System.out.println("number of seconds has passed: " + l3);
    }

    private void connectPDH() {
        printOK("getconnection to PDH");
        try {
            readPropertyFile();
            Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
            System.out.println("***DB settings:" + m_strLdapId + m_strLdapPwd + m_strOPICMVersion);
            m_dbPDH = new Database();
            m_dbPDH.connect();
            if (m_dbPDH == null) {
                System.out.println("bad db" + m_dbPDH);
            }
            m_dbPDH.getNow();
            DatePackage datepackage = m_dbPDH.getDates();
            m_strNow = datepackage.getNow();
            m_strTimeStampForever = datepackage.getForever();
            psLogin = m_dbPDH.login(m_strLdapId, m_strLdapPwd, m_strOPICMVersion);
            if (psLogin == null) {
                m_conPDH.close();
                System.exit(1);
            }
            printOK("Logged in via ldap....Profile set is " + psLogin.size() + " RoleCode:" + UPDATEAL + " Enterprice :" + m_strEnterprise);
            for (int i = 0; i < psLogin.size(); i++) {
                Profile profile = psLogin.elementAt(i);
                if (profile.getRoleCode().trim().equals((UPDATEAL !=null)?UPDATEAL.trim():null) && profile.getEnterprise().trim().equals(m_strEnterprise.trim())) {
                    psLogin.setActiveProfile(i);
                    m_prof = psLogin.getActiveProfile();
                    m_prof.setValOn(m_strNow);
                    m_prof.setEffOn(m_strNow);
                    printOK("set role: " + profile.getRoleCode() + ", OPWGID: " + profile.getOPWGID() + ", enterprise: " + profile.getEnterprise() + ", at i " + i);
                    break;
                }
                printOK("role: " + profile.getRoleCode() + ", OPWGID: " + profile.getOPWGID() + ", enterprise: " + profile.getEnterprise() + ", at i " + i);
            }

            printOK("Logged in via ldap: " + m_strLdapId + ", role: " + m_prof.getRoleCode() + ", OPWGID: " + m_prof.getOPWGID() + ", OP: " + m_prof.getOPID() + ", enterprise: " + m_prof.getEnterprise());
            printOK("TimeStampNow is " + m_strNow + ", TimeStampForever is " + m_strTimeStampForever);
            System.out.println("***" + m_strNow + m_strTimeStampForever + m_prof.getOPWGID() + m_prof.getTranID());
        } catch (Exception exception1) {
            printErr("closeConnection Exception " + exception1.getMessage());
            exception1.printStackTrace();
        }
    }
    
    private void createXMLMSG(Vector mqmsgvct) throws MiddlewareException {
        if (mqmsgvct != null){
        	 try {
                 System.out.println("Creating XML************************************");
                 ControlBlock controlblock = new ControlBlock(m_strNow, m_strTimeStampForever, m_strNow, m_strTimeStampForever, m_prof.getOPWGID(), m_prof.getTranID());
                 EntityItem entityitem = new EntityItem(null, m_prof, EntityType, 0);
                 System.out.println("eiParm:" + entityitem.getEntityType() + entityitem.getEntityID());
                 ReturnEntityKey returnentitykey = new ReturnEntityKey(EntityType, 0, true);
                 System.out.println("rek:" + returnentitykey);

                 for(int i=0;i<mqmsgvct.size();i++){
		             String s=mqmsgvct.get(i).toString();
//		             LongText longtext = new LongText(m_strEnterprise, EntityType, 0, LongMSG, s, 1, controlblock);
		             Object obj = (Object)s;
		             //byte[] baValue = s.getBytes(); 
	                 String strExt = "SPSTinbound.xml";
		             Blob blob = new Blob(m_strEnterprise, EntityType, 0, LongMSG, obj,strExt, 1, controlblock);
		             SingleFlag singleflag = new SingleFlag(m_strEnterprise, EntityType, returnentitykey.getEntityID(), FlagAttr, m_domainAttr, 1, controlblock);
		             //abrValue = "0010";
		             SingleFlag singleflag1 = new SingleFlag(m_strEnterprise, EntityType, returnentitykey.getEntityID(), QueueABRSTATUS, abrValue, 1, controlblock);
		             System.out.println("lt:" + blob.getEnterprise() + "|" + blob.getEntityType() + "|" + blob.getEntityID() + "|" + blob.getAttributeCode());
		             System.out.println("sf:" + singleflag.getEnterprise() + "|" + singleflag.getEntityType() + "|" + singleflag.getEntityID() + "|" + singleflag.getAttributeCode() + "|" + singleflag.getAttributeValue());
		             System.out.println("sf1:" + singleflag1.getEnterprise() + "|" + singleflag1.getEntityType() + "|" + singleflag1.getEntityID() + "|" + singleflag1.getAttributeCode() + "|" + singleflag1.getAttributeValue());
		             Vector vector1 = new Vector();
		             Vector vector2 = new Vector();
		             if (blob != null) {
		                 vector1.addElement(blob);
		                 vector1.addElement(singleflag);
		                 vector1.addElement(singleflag1);
		                 //System.out.println("vctAtts:" + vector1);
		                 returnentitykey.m_vctAttributes = vector1;
		                 //System.out.println("rek.m_vctAttributes:" + returnentitykey.m_vctAttributes);
		                 vector2.addElement(returnentitykey);
		                 OPICMList opicmlist = m_dbPDH.update(m_prof, vector2, false, false);
//		                 System.out.println("ol:" + opicmlist);
		                 returnentitykey = (ReturnEntityKey)opicmlist.getAt(0);
//		                 System.out.println("***rek.stuff()" + returnentitykey.m_strEntityType + returnentitykey.m_iEntityID);
//		                 System.out.println("***rek.stuff()1" + returnentitykey.m_vctAttributes);
		                 System.out.println("***Entityid: " + returnentitykey.getReturnID());
		                 m_dbPDH.commit();
//		                 System.out.println("eiParm.getEntityID()" + entityitem.getEntityType() + " " + entityitem.getEntityID());
		             }
                 }
                 System.out.println("End************************************");
             
             }
             catch (Exception exception1) {
                 printErr("closeConnection Exception " + exception1.getMessage());
                 exception1.printStackTrace();
                 printErr("Get message from MQ error:"+mqmsgvct.toString());
             }
//             Vector vector = new Vector();
//             for (StringTokenizer stringtokenizer = new StringTokenizer(EntityIDs, ","); stringtokenizer.hasMoreTokens(); System.out.println("****m_pdhi: " + m_pdhi)) {
//                 String s1 = stringtokenizer.nextToken();
//                 vector.addElement(s1);
//                 EntityItem aentityitem[] = new EntityItem[vector.size()];
//                 for (int j = 0; j < vector.size(); j++) {
//                     try {
//                         int k = Integer.parseInt((String)vector.elementAt(j));
//                         m_pdhi = new EntityItem(null, m_prof, EntityType, k);
//                         aentityitem[j] = new EntityItem(null, m_prof, EntityType, k);
//                     }
//                     catch (Exception exception2) {
//                     	 printErr("Get message from MQ error:"+ve.toString());
//                     }
//                 }
//
//             }

             System.out.println("Closing Connection");
             m_dbPDH.close();
             m_dbPDH = null;
        	  
        } 
    }

//    private void closeConnection() {
//        try {
//            printOK("Closing Connection");
//            m_conPDH.close();
//            m_conPDH = null;
//        }
//        catch (SQLException sqlexception) {
//            printErr("closeConnection SQLException " + sqlexception.getMessage());
//            sqlexception.printStackTrace();
//        }
//        catch (Exception exception) {
//            printErr("closeConnection Exception " + exception.getMessage());
//            exception.printStackTrace();
//        }
//        finally {
//            System.out.println("Disconnecting");
//        }
//    }

    private static void printOK(String s) {
        System.out.println("ok: " + s);
    }

    private static void printErr(String s) {
        System.err.println("Err: " + s);
    }

    private static void createRptFolder() {
        File file = new File("rpt");
        if (file.exists()) {
            return;
        } else {
            file.mkdir();
            return;
        }
    }

    private static void adjustSystemOut() {
        String s = new String();
        String s1 = c_sdfTimestamp.format(new Date());
        s = "rpt/Catcher" + s1 + ".log";
        String s2 = new String();
        String s3 = c_sdfTimestamp.format(new Date());
        s2 = "rpt/Catcher" + s3 + ".error.log";
        try {
            FileOutputStream fileoutputstream = new FileOutputStream(s, true);
            PrintStream printstream = new PrintStream(fileoutputstream);
            System.setOut(printstream);
            FileOutputStream fileoutputstream1 = new FileOutputStream(s2, true);
            PrintStream printstream1 = new PrintStream(fileoutputstream1);
            System.setErr(printstream1);
        }
        catch (Exception exception) { }
    }

    public static final String getVersion() {
        return new String("$Tester$");
    }

    static  {
        D.isplay("Catcher Class Engine:" + getVersion());
    }
    
    public Vector getAllMQMessage() {
    	Vector vector=new Vector();
		final String MQCID = "MQCID";
		final String NOTIFY = "NOTIFY";
		final String MQUSERID = "MQUSERID";
		final String MQPASSWORD = "MQPASSWORD";
		final String MQPORT = "MQPORT";
		final String MQHOSTNAME = "MQHOSTNAME";
		final String MQCHANNEL = "MQCHANNEL";
		final String MQMANAGER = "MQMANAGER";
		final String MQQUEUE = "MQQUEUE";
		final String MQSSL = "MQSSL";
		final String KSTORE = "KSTORE";
		final String KSPASSWORD = "KSPASSWORD";
		final String TSTORE = "TSTORE";
		final String TSPASSWORD = "TSPASSWORD";
		final String TRUE = "TRUE";
		String PROPERTIES_FILENAME = new String("MQSERIES.properties");
		Properties c_propODS = null;
		try {
			if (c_propODS == null) {
				c_propODS = new Properties();
				FileInputStream inProperties = new FileInputStream("./"
						+ PROPERTIES_FILENAME);
				c_propODS.load(inProperties);
				inProperties.close();
			}
		} catch (Exception x) {

			printErr("Unable to loadProperties for " + PROPERTIES_FILENAME
					+ " " + x);
		}
		Hashtable ht = new Hashtable();
		ht.put(NOTIFY, new Boolean(c_propODS.getProperty("NOTIFY", "")));
		ht.put(MQUSERID, c_propODS.getProperty("MQUSERID", ""));
		ht.put(MQPASSWORD, c_propODS.getProperty("MQPASSWORD", ""));
		ht.put(MQPORT, c_propODS.getProperty("MQPORT", ""));
		ht.put(MQHOSTNAME, c_propODS.getProperty("MQHOSTNAME", ""));
		ht.put(MQCHANNEL, c_propODS.getProperty("MQCHANNEL", ""));
		ht.put(MQMANAGER, c_propODS.getProperty("MQMANAGER", ""));
		ht.put(MQQUEUE, c_propODS.getProperty("MQQUEUE", ""));
		ht.put(MQSSL, c_propODS.getProperty("MQSSL", ""));
		ht.put(KSTORE, c_propODS.getProperty("KSTORE", ""));
		ht.put(KSPASSWORD, c_propODS.getProperty("KSPASSWORD", ""));
		ht.put(TSTORE, c_propODS.getProperty("TSTORE", ""));
		ht.put(TSPASSWORD, c_propODS.getProperty("TSPASSWORD", ""));
		System.out.println("MQPORT:" + c_propODS.getProperty("MQPORT", "").toString()
				+ "MQHOSTNAME:" + c_propODS.getProperty("MQHOSTNAME", "").toString() 
				+ "MQCHANNEL:" + c_propODS.getProperty("MQCHANNEL", "").toString()
				+ "MQMANAGER:" + c_propODS.getProperty("MQMANAGER", "").toString()
			    + "MQQUEUE:" +  c_propODS.getProperty("MQQUEUE", "").toString());
		try {
			 vector = MQUsage.GetAllMQQueue(ht);
			 
		} catch (MQException e) {
			printErr("Get message from MQ error:"+vector.toString());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			printErr("Get message from MQ error:"+vector.toString());
		}

		return vector;
    }
    
    
    /*
	 * the function of TransformerXML is transfer the Document to 
	 * different types such as string, file , outputStream ..
	 * Through set OutputProperty() tell what kind of file to be output.
	 * METHOD --> xml, html, text
	 * ENCODING--> UTF-8, GB2312 (chinese)
	 */
	public static String TransformerXML(Document document){
		
		TransformerFactory transfac = TransformerFactory.newInstance();
		Transformer trans;
		String XMLString = "";
		try {
			trans = transfac.newTransformer();
		    trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		// OIDH can't handle whitespace.. trans.setOutputProperty(OutputKeys.INDENT, "yes");
		    trans.setOutputProperty(OutputKeys.INDENT, "no");
		    trans.setOutputProperty(OutputKeys.METHOD, "xml");
		    trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		    
//		  create string from xml tree
			java.io.StringWriter sw = new java.io.StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(document);
			trans.transform(source, result);
			XMLString = sw.toString();
			XMLString = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + XMLString;
			printOK("XMLString: " + XMLString);
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return XMLString;	
	}
	
	
	 void test() throws SAXException, IOException, ParserConfigurationException, MiddlewareException{
		 String mesg = null;
		 Document result = null;
		 Vector vet = new Vector();
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		 File file = new File("test.xml"); 
		 printOK("beging test read test.xml is :"+ ((file ==null)?"null" : "in not null"));
		 result = factory.newDocumentBuilder().parse(file);
		 mesg = TransformerXML(result);
		 vet.add(mesg);
		 createXMLMSG(vet);		 
	 }
	 
}

