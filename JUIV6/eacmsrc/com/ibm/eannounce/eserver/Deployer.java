//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eannounce.eserver;

import COM.ibm.opicmpdh.objects.Blob;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.transactions.Cipher;
import COM.ibm.opicmpdh.middleware.*;

import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eannounce.version.Version;
import java.io.*;
import java.lang.reflect.*;
import java.text.*;
import java.util.Date;
import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.*;
import org.xml.sax.helpers.*;


import java.io.IOException;

/**
 * This is used on the server to put the update file into the blob table
 * it is part of eaServer.jar and executes on the server
 * pokxea6-/home/opicmadm/autoUpdate=>deploy-aix deploy.ea6
 * 
deploy-aix:
SUPPORT_JARS="./eaServer.jar:./version.jar:/home/opicmadm/middleware/middleware.jar:/home/opicmadm/middleware/smtp.jar:/home/opicmadm/middleware/mail.jar:/home/opicmadm/middleware/jsse.jar:/home/opicmadm/middleware/jnet.jar:/home/opicmadm/middleware/jcert.jar:/home/opicmadm/middleware/xerces.jar:"
java -Xms16M -Xmx64M -Xss4M -classpath $SUPPORT_JARS com/ibm/eannounce/eserver/Deployer $1 $2 $3 $4 $5
 */
// $Log: Deployer.java,v $
// Revision 1.2  2012/11/12 22:48:07  wendy
// AutoUpdate changes
//
// Revision 1.1  2012/09/27 19:39:24  wendy
// Initial code
//
public class Deployer implements EACMGlobals 
{
    private static final String DEFAULT_USER = "eannounce30taskmastersg";
    private static final String DEFAULT_PASSWORD = "";
    private static final String TMP_JAR = "newVersion.jar";
    private static final String VersionPkgName = "com.ibm.eannounce.version.Version";
  	private static final String versionJarPath = "jre/lib/ext/version.jar";
  	 
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat();

    private RemoteDatabaseInterface ro = null;
 
    private String strIP = null;
    private int iPort = -1;
    private String strName = null;
    private String strChain = null;
    private String strFile = null;
    private String strLoad = null;
    private String strValOn = null;
    private String strEffOn = null;
    private String strDefExt = null;
    private String strUser = DEFAULT_USER;
    private String strPass = DEFAULT_PASSWORD;

	private boolean bOptional = false;		

    private Deployer() {}

    /**
     * main
     * @param args
     */
    public static void main(String[] args) {
        Deployer deploy = new Deployer();
        if (args != null) {
			for (int i=0;i<args.length; ++i) {
				deploy.generate(args[i]);
			}
		}
		deploy.deleteTemporaryJar();
		System.exit(0);
    }

    /**
     * login and get profile
     * @return
     */
    private Profile logon() {
        System.out.println("logon(" + strUser + ")");
        try {
            //ProfileSet ps = ro.login(strUser, strPass, EACM_TOKEN, "");
        	byte[][] encrypted = Cipher.encryptUidPw(strUser, strPass);
        	// secure login
        	ProfileSet ps= ro.secureLogin(encrypted,EACM_TOKEN);
            if (ps != null) {
                Profile[] prof = ps.toArray();
                if (prof != null && prof.length > 0) {
                    return prof[0];
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("****************");
        System.out.println("* LOGON FAILED *");
        System.out.println("****************");
        return null;
    }

    /**
     * update the database with the new blob
     * @param prof
     * @param b
     * @return
     */
    private boolean update(Profile prof, Blob b) {
        try {
        	System.out.println("updating blob");
        	ro.putSoftwareImage(prof, "UP", b);
        	return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * get image update from the blob table
     * @return
     */
    private boolean retrieve() {
		Blob bIn = null;
		String strAttCode = "IMAGE_UPDATE";
		if (strChain != null) {
			strAttCode = strChain;
		}
        try {
            if (strValOn != null && strEffOn != null) {
                System.out.println("retrieving blob");
                bIn = ro.getSoftwareImage("UP","UPDATE",1,strAttCode,strValOn,strEffOn);
                if (bIn != null) {
					bIn.saveToFile(strLoad);
					return true;
				}
            } else {
                System.out.println("NO ACTIVE PROFILE");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     *  generate the blob from the file
     * @return
     */
    private Blob generateBlob(){
    	Blob bUpdate = null;
        String strAttCode = null;
        ControlBlock cBlock = null;
        String sNow = null;
		String valFrom = null;
		String effFrom = null;
		String sExt = null;				
        if (strLoad != null && (strFile == null || !strFile.equalsIgnoreCase(strLoad))) {
            strFile = strLoad;
            strAttCode = "IMAGE_UPDATE";
            if (strChain != null) {
                strAttCode = strChain;
            }
			sNow = getNow("yyyy-MM-dd-HH.mm.ss.SSS000");
            if (strValOn == null) {
				valFrom = sNow;
			} else {
				valFrom = getNow(strValOn);
			}
			if (strEffOn == null) {
				effFrom = sNow;
			} else {
				effFrom = getNow(strEffOn);
			}
			System.out.println("****************************************");
            System.out.println("generateBlob	current time: " + sNow);
            System.out.println("generateBlob	val From    : " + valFrom);
            System.out.println("generateBlob	eff From    : " + effFrom);
            System.out.println("generateBlob	forever     : " + FOREVER);
            cBlock = new ControlBlock(valFrom, FOREVER, effFrom, FOREVER, 1);
            try {
                System.out.println("generateBlob	generating blob from " + strFile);
                if (strDefExt == null) {
					unpackVersionJar();
					strDefExt = getUpdateJarVersion();
				}
				if (bOptional) {																
					sExt = strDefExt + "." + OPTIONAL;										
				} else {																		
					sExt = strDefExt;																
				}																						

				System.out.println("generateBlob	GENERATE Blob from " + strFile + " with extension: " +  sExt);		
				bUpdate = new Blob("UP", "UPDATE", 1, strAttCode, strFile, sExt, 1, cBlock);			
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return bUpdate;
    }

    private void reset() {
        strIP = null;
        iPort = -1;
        strName = null;
        strChain = null;
        strLoad = null;
        strValOn = null;
        strEffOn = null;
        strUser = DEFAULT_USER;
        strPass = DEFAULT_PASSWORD;
        bOptional = false;			
    }

    private boolean ready() {
        return (strIP != null && iPort != -1 && strName != null && strLoad != null);
    }

    /*
     helper
     */
    private int getInt(String s, int def) {
        if (!have(s)) {
            return def;
        }
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return def;
    }

    private static boolean have(String s) {
        String test = null;
        try {
            if (s == null) {
                return false;
            }
            test = s.trim();
            if (test.length() == 0 || test.equalsIgnoreCase("null")) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private synchronized String formatDate(String pattern, Date date) {
        if (pattern == null || date == null) {
            return null;
        }
        dateFormat.applyPattern(pattern);
        return dateFormat.format(date);
    }

    private synchronized Date parseDate(final String pattern, final String date) {
        Date out = null;
        if (pattern != null && date != null) {
            try {
                dateFormat.applyPattern(pattern);
                out = dateFormat.parse(date);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        }
        return out;
    }

    private String getNow(String format) {
        Date tmp = parseDate(FORMAT_IN, getNow());
        return formatDate(format, tmp);
    }

    private String getNow() {
        ReturnDataResultSetGroup ld = null;
        try {
            ld = ro.remoteGBL2028();
            String datNow = ld.getColumn(0, 0, 0);
            return datNow.substring(0, 19);
        } catch (Exception ce) {
            ce.printStackTrace();
        }

        return null;
    }

	/**
	 * This is the current version of the jar
	 * that is being used to run the application.
	 *
	 * It is NOT the version that is contained in the
	 * update package
	 *
	 */
    private String getJarVersion() {
		Date bldDate = parseDate(BUILD_DATE, Version.getDate());
		return formatDate(UPDATED_DATE, bldDate);
	}

    /**
     * unpackVersionJar
     *
     * in MTP the update, may not be the most recent codebase
     * as a result retrieve the version of the code from the
     * update package.
     *
     * This method assists by inflating a new temporary jar
     * (the update packages version.jar file)
     * which contains the version.jar from the update
     *
     */
    private void unpackVersionJar() {
 		deleteTemporaryJar();
 		
    	JarResources jarUpdateResources = new JarResources(strLoad);
    	byte[] versionjarBytes = jarUpdateResources.getResource(versionJarPath);
        System.out.println("unpackVersionJar  "+strLoad);
        if(versionjarBytes!=null){
        	FileOutputStream out = null;
        	try{
        		System.out.println("inflating " + versionJarPath + " to "+TMP_JAR);
        		out = new FileOutputStream(TMP_JAR);
        		out.write(versionjarBytes, 0, versionjarBytes.length);
        	} catch (IOException ioe) {
        		ioe.printStackTrace();
        	} finally {
        		try {
        			if (out != null) {
        				out.close();
        			}
        		} catch (IOException ioe) {
        			ioe.printStackTrace();
        		}
        	}
        }else{
            System.out.println("unpackVersionJar  versionjarBytes is null ");
        }
        
        jarUpdateResources.dereference();
    }
    
	/**
     * getUpdateJarVersion
     *
     * in MTP the update, may not be the most recent codebase
     * as a result retrieve the version of the code from the
     * update package.
     *
     * This method will extract the version.class from the
     * temporary jar file (the update package's version.jar)
     * that is the version.jar from inside the update package.
	 *
	 * As a result, it will return the version literal from
	 * the update package which may or may not be the same
	 * as the literal that is used to process the update.
	 * 
     * @return
     */
    private String getUpdateJarVersion() {
		File tmpFile = new File(TMP_JAR);
		String out = getJarVersion();
		System.out.println("getUpdateJarVersion classpath version " + out);
		try {
			if (tmpFile.exists()) {
				JarClassLoader jcl = new JarClassLoader(TMP_JAR); 				
				Class<?> c = jcl.loadClass(VersionPkgName);
				if(c!=null){
					try {
						Method method = c.getMethod("getDate",(Class<?>[])null);// casting to class<?> chgs signature it looks for
						if (method != null) {
							try {
								Object obj = method.invoke(null,(Object[])null);//casting to object chgs number of args
								if (obj instanceof String) {
									Date bldDate = parseDate(BUILD_DATE, (String)obj);
									out = formatDate(UPDATED_DATE, bldDate);
									System.out.println("getUpdateJarVersion zipped jar version " + out);
								}
							} catch (IllegalAccessException iae) {
								iae.printStackTrace();
							} catch (IllegalArgumentException iae) {
								iae.printStackTrace();
							} catch (InvocationTargetException ite) {
								ite.printStackTrace();
								Throwable t = ite.getCause();
								if (t!=null){
									t.printStackTrace();
								}
							}
						}
					} catch (NoSuchMethodException nsm) {
						nsm.printStackTrace();
					}
				}
				
				jcl.dereference();
			}		
		} catch (ClassNotFoundException ie) {
			ie.printStackTrace();
		}
		
		return out;
	}

	/**
     * in MTP the update, may not be the most recent codebase
     * as a result retrieve the version of the code from the
     * update package.
     *
     * This method removes the temporary jar file (the updates
     * version.jar from the file system)
     */
	private boolean deleteTemporaryJar() {
		boolean bOut = false;
		File tmpFile = new File(TMP_JAR);
		if (tmpFile.exists()) {
			bOut = tmpFile.delete();
			if (bOut) {
				System.out.println(TMP_JAR + " deleted.");
			} else {
				System.out.println(TMP_JAR + " NOT deleted.");
			}
		}
		return bOut;
	}

	private void report(String strIP,int iPort,String strName,String strFile, String strEff, String strVal, boolean upload, boolean bSuccess) {
		System.out.println("****************************************");
		if (upload) {
			System.out.println("UPLOAD");
			System.out.println("------");
		} else {
			System.out.println("RETRIEVE");
			System.out.println("--------");
		}
		if (bSuccess) {
			System.out.println("SUCCESSFUL to: " + strIP + ":" + iPort);
		} else {
			System.out.println("FAILED to: " + strIP + ":" + iPort);
		}
		System.out.println("Object: " + strName);
		if (strEff != null) {
			System.out.println("Effective on: " + strEff);
		}
		if (strVal != null) {
			System.out.println("Valid on: " + strVal);
		}
		System.out.println("File: " + strFile);
		System.out.println("****************************************");
	}

    /*
     * parser
     */
    private String getXML(String xmlFile) {
//        System.out.println("retrieve xml: " + xmlFile);
        StringBuffer out = new StringBuffer();
        String temp = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader inStream = null;
        try {
            fis = new FileInputStream(xmlFile);
            isr = new InputStreamReader(fis, EACM_FILE_ENCODE);
            inStream = new BufferedReader(isr);
            while ((temp = inStream.readLine()) != null) {
                out.append(temp);
            }
        } catch (FileNotFoundException fnf) {
            System.out.println(xmlFile + " not found.");
            return null;
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
        return out.toString();
    }

    private void processEndUpdate() {
        boolean bSuccess = false;
        System.out.println("processing update");
        if (ready()) {
            System.out.println("    ip  : " + strIP);
            System.out.println("    port: " + iPort);
            System.out.println("    name: " + strName);
            System.out.println("    file: " + strFile);
            try {
                System.out.println("connect(" + strIP + ", " + iPort + ", " + strName + ")");
                ro = Middleware.connect(strIP, iPort, strName);

                Profile actProf = logon();
                if (actProf != null) {
                	Blob bUpdate = generateBlob();
                	if(bUpdate!=null){
                		bSuccess = update(actProf, bUpdate);
                	}
                }else {
                    System.out.println("NO ACTIVE PROFILE");
                }

                report(strIP,iPort,strName,strFile,null,null,true, bSuccess);	
                //Middleware.disconnect(ro);  causes "No Such Object to unExport"
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else{
			System.out.println("Error missing something   ip: " + strIP+" port: " + iPort
				+" name: " + strName+" load: "+strLoad);
		}
    }

    private void processUpdate(Attributes atts) {
        reset();
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                processUpdate(atts.getLocalName(i), atts.getValue(i));
            }
        }
    }

    private void processUpdate(String code, String value) {
        if (code.equalsIgnoreCase("ip")) {
            strIP = value;
        } else if (code.equalsIgnoreCase("port")) {
            iPort = getInt(value, -1);
        } else if (code.equalsIgnoreCase("name")) {
            strName = value;
        } else if (code.equalsIgnoreCase("chain")) {
            if (have(value)) {
                strChain = value;
            }
        } else if (code.equalsIgnoreCase("file")) {
            strLoad = value;
        } else if (code.equalsIgnoreCase("user")) {
            if (have(value)) {
                strUser = value;
            }
        } else if (code.equalsIgnoreCase("pass")) {
            if (have(value)) {
                strPass = value;
            }
        } else if (code.equalsIgnoreCase("valOn")) {
			if (have(value)) {
				strValOn = value;
			}
		} else if (code.equalsIgnoreCase("effOn")) {
			if (have(value)) {
				strEffOn = value;
			}
        } else if (code.equalsIgnoreCase(OPTIONAL)) {			
			if (have(value)) {									
				if (value.equalsIgnoreCase("true")) {			
					bOptional = true;							
				}											
			}													
		}												
    }

    private void processRetrieve(Attributes atts) {
        reset();
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
                processRetrieve(atts.getLocalName(i), atts.getValue(i));
            }
        }
    }

    private void processRetrieve(String code, String value) {
        if (code.equalsIgnoreCase("ip")) {
            strIP = value;
        } else if (code.equalsIgnoreCase("port")) {
            iPort = getInt(value, -1);
        } else if (code.equalsIgnoreCase("name")) {
            strName = value;
        } else if (code.equalsIgnoreCase("chain")) {
            if (have(value)) {
                strChain = value;
            }
        } else if (code.equalsIgnoreCase("file")) {
            strLoad = value;
        } else if (code.equalsIgnoreCase("user")) {
            if (have(value)) {
                strUser = value;
            }
        } else if (code.equalsIgnoreCase("pass")) {
            if (have(value)) {
                strPass = value;
            }
        } else if (code.equalsIgnoreCase("valOn")) {
			if (have(value)) {
				strValOn = value;
			}
		} else if (code.equalsIgnoreCase("effOn")) {
			if (have(value)) {
				strEffOn = value;
			}
		}
    }

    private void processEndRetrieve() {
        String sNow = null;
        boolean bSuccess = false;
        System.out.println("processing retrieve");
        if (ready()) {
            try {
                System.out.println("connect(" + strIP + ", " + iPort + ", " + strName + ")");
                ro = Middleware.connect(strIP, iPort, strName);
				sNow = getNow("yyyy-MM-dd-HH.mm.ss.SSS000");
				if (strValOn == null) {
					strValOn = sNow;
				}
				if (strEffOn == null) {
					strEffOn = sNow;
				}

				System.out.println("    ip   : " + strIP);
				System.out.println("    port : " + iPort);
				System.out.println("    name : " + strName);
				System.out.println("    valOn: " + strValOn);
				System.out.println("    effOn: " + strEffOn);
				System.out.println("    file: " + strFile);

				bSuccess = retrieve();

                report(strIP,iPort,strName,strFile,strEffOn,strValOn,false,bSuccess);
                
                //Middleware.disconnect(ro); causes "No Such Object to unExport"
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else{
			System.out.println("Error missing something   ip: " + strIP+" port: " + iPort
				+" name: " + strName+" load: "+strLoad);
		}
    }

    private void process(Attributes atts) {
        reset();
        for (int i = 0; i < atts.getLength(); ++i) {
            if (atts.getType(i).equals("CDATA")) {
            	 System.out.println("    code: " +atts.getLocalName(i)+ " value: " +  atts.getValue(i));
            }
        }
    }

    private void processElement(String tagName, Attributes atts) {
        if (tagName.equalsIgnoreCase("UPDATE")) { //put blob into pdh
            processUpdate(atts);
		} else if (tagName.equalsIgnoreCase("RETRIEVE")) { // get blob from pdh
			processRetrieve(atts);
        } else {
            System.out.println("bypassing: " + tagName); // unsupported
            process(atts);
        }
    }

    private void processEndElement(String tagName) {
        if (tagName.equalsIgnoreCase("UPDATE")) {
            processEndUpdate();
        } else if (tagName.equalsIgnoreCase("RETRIEVE")) {
			processEndRetrieve();
		}
    }

    private void generate(String xmlFile) {
        DefaultHandler defaulthandler = new DefaultHandler() {
            public void startElement(String URI, String tagName, String qName, Attributes atts) {
                processElement(tagName, atts);
            }

            public void endElement(String URI, String tagName, String qName) {
                processEndElement(tagName);
            }

            public void startDocument() {}
            public void endDocument() {}

            public void characters(char[] rgc, int nStart, int nLength) throws SAXException {}

            public void error(SAXParseException saxparseexception) throws SAXException {
                throw saxparseexception;
            }
        };
        SAXParser parser = new SAXParser();
        parser.setContentHandler(defaulthandler);
        try {
            String xml = getXML(xmlFile);
            if (xml != null) {
                parser.parse(new InputSource(new StringReader(xml)));
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
