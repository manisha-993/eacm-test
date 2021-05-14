/**
 * Copyright (c) 2004-2005 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 3.0b 2005/01/17
 * @author Anthony C. Liberto
 *
 * $Log: Deployer.java,v $
 * Revision 1.4  2011/09/09 17:25:29  wendy
 * use securelogin now
 *
 * Revision 1.3  2008/01/30 16:27:10  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.2  2008/01/10 14:45:52  wendy
 * Use proper case to find Version class
 *
 * Revision 1.1  2007/04/18 19:46:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.5  2006/03/07 22:24:19  tony
 * CR 6299
 * update mandatory/optional
 *
 * Revision 1.4  2005/10/27 21:17:42  tony
 * Improved deploy logic
 *
 * Revision 1.3  2005/10/20 20:57:04  tony
 * updated Deployer to take in optional valid and effective dates
 *
 * Revision 1.2  2005/09/09 21:43:21  tony
 * updated code
 *
 * Revision 1.1.1.1  2005/09/09 20:38:53  tony
 * This is the initial load of OPICM
 *
 * Revision 1.19  2005/09/08 17:59:12  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.18  2005/04/05 17:29:06  tony
 * changed reporting logic.
 *
 * Revision 1.17  2005/03/14 23:40:12  tony
 * added reporting logic to improve usability.
 *
 * Revision 1.16  2005/02/28 22:02:03  tony
 * improved java document commenting
 *
 * Revision 1.15  2005/02/28 17:31:04  tony
 * enhanced logic to retrieve the blob extension from the
 * specified updates version.class file.  This will improve
 * functionality by allowing for "non-current" updates.
 * for example, approved MTP versions.
 *
 * Revision 1.14  2005/02/21 17:16:50  tony
 * adjusted versioning logic by separating out
 * versioning logic to improve update functionality.
 *
 * Revision 1.13  2005/02/18 23:10:37  tony
 * added ability to adjust blob extension value from the default.
 *
 * Revision 1.12  2005/02/07 18:43:08  tony
 * improved deployment functionality to allow for
 * extraction in addition to importation of update.
 *
 * Revision 1.11  2005/02/04 18:16:50  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.10  2005/02/03 21:26:15  tony
 * JTest Format Third Pass
 *
 * Revision 1.9  2005/02/03 19:42:22  tony
 * JTest Third pass
 *
 * Revision 1.8  2005/01/28 17:54:21  tony
 * JTest Fromat step 2
 *
 * Revision 1.7  2005/01/26 17:18:50  tony
 * JTest Formatting modifications
 *
 * Revision 1.6  2005/01/20 23:02:54  tony
 * adjusted logic
 *
 * Revision 1.5  2005/01/19 22:41:18  tony
 * *** empty log message ***
 *
 * Revision 1.4  2005/01/19 22:33:28  tony
 * *** empty log message ***
 *
 * Revision 1.3  2005/01/19 20:39:34  tony
 * added user and password overwrite capability to xml file.
 *
 * Revision 1.2  2005/01/19 19:19:36  tony
 * updated logic
 *
 * Revision 1.1  2005/01/18 19:04:56  tony
 * *** empty log message ***
 *
 *
 */

package com.ibm.eannounce.eserver;
import COM.ibm.opicmpdh.objects.Blob;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.transactions.Cipher;
import COM.ibm.opicmpdh.middleware.*;

import com.ibm.eannounce.version.Version;
import com.elogin.EAccessConstants;
import com.elogin.EAccess;
import java.io.*;
import java.lang.reflect.*;
import java.text.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.*;
import java.util.jar.JarFile;
import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class Deployer implements EAccessConstants {
    private final String DEFAULT_USER = "eannounce12taskmasterIGSSVS";
    private final String DEFAULT_PASSWORD = "";
    private final String TMP_JAR = "newVersion.jar";
    private final boolean bRequireLogon = true;

    private String strXMLFile = new String("deploy.xml");

    private SimpleDateFormat dateFormat = new SimpleDateFormat();
 //   private Middleware mw = null;
    private RemoteDatabaseInterface ro = null;
    private Profile actProf = null;

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

	private boolean bOptional = false;				//6299

    private Blob bUpdate = null;

    private Deployer() {
        return;
    }

    /**
     * main
     * @param _args
     * @author Anthony C. Liberto
     */
    public static void main(String[] _args) {
        Deployer deploy = new Deployer();
        if (_args != null) {
	        int ii = _args.length;
			for (int i=0;i<ii; ++i) {
				deploy.setXMLFile(_args[i]);
				deploy.generate();
			}
		}
		deploy.deleteTemporaryJar();
        deploy.exit();
        return;
    }

    private void setXMLFile(String _s) {
		if (_s != null) {
			strXMLFile = new String(_s);
		}
		return;
	}

    /*
     logoff of the given middleware
     */
    private void logoff(RemoteDatabaseInterface _ro) {
        Middleware.disconnect(_ro);
        return;
    }

    /*
     connect to a new middleware
     */
    private RemoteDatabaseInterface connect() throws Exception {
        System.out.println("connect(" + strIP + ", " + iPort + ", " + strName + ")");
        return Middleware.connect(strIP, iPort, strName);
    }

    private boolean logon(RemoteDatabaseInterface _ro) {
        ProfileSet ps = null;
        try {
            System.out.println("logon(" + strUser + ")");
            //ps = ro.login(strUser, strPass, EANNOUNCE_TOKEN, "");
        	byte[][] encrypted = Cipher.encryptUidPw(strUser, strPass);
        	// secure login
        	ps= ro.secureLogin(encrypted,EANNOUNCE_TOKEN, "");
 
            if (ps != null) {
                Profile[] prof = ps.toArray();
                if (prof != null && prof.length > 0) {
                    actProf = prof[0];
                    return true;
                }
            }
        } catch (Exception _ex) {
            _ex.printStackTrace();
        }
        System.out.println("****************");
        System.out.println("* LOGON FAILED *");
        System.out.println("****************");
        return false;
    }

    /*
     update the database with the new blob
     */
    private boolean update(RemoteDatabaseInterface _ro, Profile _prof, Blob _b) {
        try {
            if (_prof != null) {
                System.out.println("updating blob");
                _ro.putSoftwareImage(_prof, "UP", _b);
                return true;
            } else {
                System.out.println("NO ACTIVE PROFILE");
            }
        } catch (Exception _ex) {
            _ex.printStackTrace();
        }
        return false;
    }

    private boolean retrieve(RemoteDatabaseInterface _ro) {
		Blob bIn = null;
		String strAttCode = "IMAGE_UPDATE";
		if (strChain != null) {
			strAttCode = strChain;
		}
        try {
            if (strValOn != null && strEffOn != null) {
                System.out.println("retrieving blob");
                bIn = _ro.getSoftwareImage("UP","UPDATE",1,strAttCode,strValOn,strEffOn);
                if (bIn != null) {
					bIn.saveToFile(strLoad);
					return true;
				}
            } else {
                System.out.println("NO ACTIVE PROFILE");
            }
        } catch (Exception _ex) {
            _ex.printStackTrace();
        }
        return false;
    }

    /*
     generate the blob from the file
     */
    private Blob generateBlob(String _fileName, String _val, String _eff) {
        String strAttCode = null;
        ControlBlock cBlock = null;
        String sNow = null;
		String valFrom = null;
		String effFrom = null;
		String sExt = null;																		//6299
        if (_fileName != null && (strFile == null || !strFile.equalsIgnoreCase(_fileName))) {
            strFile = new String(_fileName);
            strAttCode = "IMAGE_UPDATE";
            if (strChain != null) {
                strAttCode = strChain;
            }
			sNow = getNow("yyyy-MM-dd-HH.mm.ss.SSS000");
            if (_val == null) {
				valFrom = sNow;
			} else {
				valFrom = getNow(_val);
			}
			if (_eff == null) {
				effFrom = sNow;
			} else {
				effFrom = getNow(_eff);
			}
            System.out.println("generateBlob	current time: " + sNow);
            System.out.println("generateBlob	val From    : " + valFrom);
            System.out.println("generateBlob	eff From    : " + effFrom);
            System.out.println("generateBlob	forever     : " + FOREVER);
            cBlock = new ControlBlock(valFrom, FOREVER, effFrom, FOREVER, 1);
            try {
                System.out.println("generateBlob	generating blob from " + strFile);
                if (strDefExt == null) {
					unpackVersionJar(strLoad);
					strDefExt = getUpdateJarVersion();
				}
				if (bOptional) {																		//6299
					sExt = strDefExt + "." + OPTIONAL;													//6299
				} else {																				//6299
					sExt = strDefExt;																	//6299
				}																						//6299
//6299				System.out.println("GENERATE Blob from " + strFile + " with extension: " +  strDefExt);
//6299                bUpdate = new Blob("UP", "UPDATE", 1, strAttCode, strFile, strDefExt, 1, cBlock);
				System.out.println("GENERATE Blob from " + strFile + " with extension: " +  sExt);		//6299
				bUpdate = new Blob("UP", "UPDATE", 1, strAttCode, strFile, sExt, 1, cBlock);			//6299
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
            }
        }
        return bUpdate;
    }

    private void reset() {
        strIP = null;
        iPort = -1;
        strName = null;
        strChain = null;
        actProf = null;
        strLoad = null;
        strValOn = null;
        strEffOn = null;
        strUser = DEFAULT_USER;
        strPass = DEFAULT_PASSWORD;
        bOptional = false;			//6299
        return;
    }

    private boolean ready() {
        return (strIP != null && iPort != -1 && strName != null && strLoad != null);
    }

    private void exit() {
        System.exit(0);
    }

    /*
     helper
     */
    private int getInt(String _s, int _def) {
        if (!have(_s)) {
            return _def;
        }
        try {
            return Integer.valueOf(_s).intValue();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return _def;
    }

    private static boolean have(String _s) {
        String test = null;
        try {
            if (_s == null) {
                return false;
            }
            test = _s.trim();
            if (test == null || test.length() == 0 || test.equals("")) {
                return false;
            } else if (test.equalsIgnoreCase("null")) {
                return false;
            } else {
                return true;
            }
        } catch (Exception _ex) {
            EAccess.report(_ex,false);
        }
        return false;
    }

    private synchronized String formatDate(String _pattern, Date _date) {
        if (_pattern == null || _date == null) {
            return null;
        }
        dateFormat.applyPattern(_pattern);
        return dateFormat.format(_date);
    }

    private synchronized Date parseDate(final String _pattern, final String _date) {
        Date out = null;
        if (_pattern != null && _date != null) {
            try {
                dateFormat.applyPattern(_pattern);
                out = dateFormat.parse(_date);
            } catch (ParseException _pe) {
                _pe.printStackTrace();
            }
        }
        return out;
    }

    private String getNow(String _format) {
        Date tmp = parseDate(FORMAT_IN, getNow());
        return formatDate(_format, tmp);
    }

    private String getNow() {
        ReturnDataResultSetGroup ld = null;
        try {
            ld = ro.remoteGBL2028();
        } catch (Exception _ce) {
            _ce.printStackTrace();
        }
        try {
            String datNow = new String(ld.getColumn(0, 0, 0));
            return datNow.substring(0, 19);
        } catch (Exception _ex) {
            _ex.printStackTrace();
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
     * @author Anthony C. Liberto
     * @param _s
     */
    protected void unpackVersionJar(String _s) {
        ZipFile zip = null;
        FileInputStream fin = null;
        InputStream in = null;
        ZipInputStream zin = null;
        ZipEntry ze = null;
        File f = new File(_s);
        System.out.println("unpackVersionJar(" + _s + ")");
        deleteTemporaryJar();
        if (f != null) {
            try {
                zip = new ZipFile(f);
                fin = new FileInputStream(f);
                in = new BufferedInputStream(fin);
                zin = new ZipInputStream(in);
                ze = null;
                while ((ze = zin.getNextEntry()) != null) {
                    unzipVersionJar(zin, ze);
                }
            } catch (FileNotFoundException _fnfe) {
                _fnfe.printStackTrace();
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
            } finally {
                try {
                    if (zin != null) {
                        zin.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    if (fin != null) {
                        fin.close();
                    }
                    if (zip != null) {
                        zip.close();
                    }
                } catch (IOException _ioe) {
                    _ioe.printStackTrace();
                }
            }
        }
        return;
    }

    /**
     * unzipVersionJar
     *
     * in MTP the update, may not be the most recent codebase
     * as a result retrieve the version of the code from the
     * update package.
     *
     * This method assists by inflating a new temporary jar
     * (the update packages version.jar file)
     * which contains the version.jar from the update
     *
     * @author Anthony C. Liberto
     * @param _zin
     * @param _ze
     */
    protected void unzipVersionJar(ZipInputStream _zin, ZipEntry _ze) {
        int len = 0;
        byte[] b = null;
        FileOutputStream out = null;
        if (!_ze.isDirectory()) {
            try {
				String fName = _ze.getName();
                if (fName.endsWith("version.jar")) {
                    System.out.println("inflating " + _ze.getName() + " to "+TMP_JAR);
                    out = new FileOutputStream(TMP_JAR);
                    b = new byte[512];
                    while ((len = _zin.read(b)) != -1) {
                        out.write(b, 0, len);
                    }
                }
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException _ioe) {
                    _ioe.printStackTrace();
                }
            }
        }
        return;
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
     * that is the version.jar from the update package.
	 *
	 * As a result, it will return the version literal from
	 * the update package which may or may not be the same
	 * as the literal that is used to process the update.
	 *
     * @author Anthony C. Liberto
     * @return
     */
    protected String getUpdateJarVersion() {
		File tmpFile = new File(TMP_JAR);
		JarFile jFile = null;
		String out = getJarVersion();
        Enumeration eNum = null;
		try {
			if (tmpFile != null && tmpFile.exists()) {
				jFile = new JarFile(tmpFile);
				eNum = jFile.entries();
				while (eNum.hasMoreElements()) {
					Object o = eNum.nextElement();
					if (o instanceof ZipEntry) {
						String s = ((ZipEntry)o).getName();
						if (s.endsWith("Version.class")) {
							Class mainClass = null;
							try {
								mainClass = Class.forName("com.ibm.eannounce.version.Version");
							} catch (ClassNotFoundException _cnf) {
								_cnf.printStackTrace();
							}
							if (mainClass != null) {
								try {
									Method method = mainClass.getMethod("getDate",null);
									if (method != null) {
										try {
											Object obj = method.invoke(null,null);
											if (obj instanceof String) {
												Date bldDate = parseDate(BUILD_DATE, (String)obj);
												out = formatDate(UPDATED_DATE, bldDate);
												System.out.println("using UPDATED jar date... " + out);
											}
										} catch (IllegalAccessException _iae) {
											_iae.printStackTrace();
										} catch (IllegalArgumentException _iae) {
											_iae.printStackTrace();
										} catch (InvocationTargetException _ite) {
											_ite.printStackTrace();
										}
									}
								} catch (NoSuchMethodException _nsm) {
									_nsm.printStackTrace();
								}
							}
						}
					}
				}
			}
		} catch (IOException _ioe) {
			_ioe.printStackTrace();
		} finally {
			try {
				if (jFile != null) {
					System.out.println("closing " + TMP_JAR);
					jFile.close();
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
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
		if (tmpFile != null && tmpFile.exists()) {
			bOut = tmpFile.delete();
			if (bOut) {
				System.out.println(TMP_JAR + " deleted.");
			} else {
				System.out.println(TMP_JAR + " NOT deleted.");
			}
		}
		return bOut;
	}

	private void report(String _strIP,int _iPort,String _strName,String _strFile, String _strEff, String _strVal, boolean _upload, boolean _bSuccess) {
		System.out.println("****************************************");
		if (_upload) {
			System.out.println("UPLOAD");
			System.out.println("------");
		} else {
			System.out.println("RETRIEVE");
			System.out.println("--------");
		}
		if (_bSuccess) {
			System.out.println("SUCCESSFUL to: " + _strIP + ":" + _iPort);
		} else {
			System.out.println("FAILED to: " + _strIP + ":" + _iPort);
		}
		System.out.println("Object: " + _strName);
		if (_strEff != null) {
			System.out.println("Effective on: " + _strEff);
		}
		if (_strVal != null) {
			System.out.println("Valid on: " + _strVal);
		}
		System.out.println("File: " + _strFile);
		System.out.println("****************************************");
		return;
	}

    /*
     * parser
     */
    private String getXML(String _xmlFile) {
//        System.out.println("retrieve xml: " + _xmlFile);
        StringBuffer out = new StringBuffer();
        String temp = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader inStream = null;
        try {
            fis = new FileInputStream(_xmlFile);
            isr = new InputStreamReader(fis, EANNOUNCE_FILE_ENCODE);
            inStream = new BufferedReader(isr);
            while ((temp = inStream.readLine()) != null) {
                out.append(temp);
            }
        } catch (FileNotFoundException _fnf) {
            EAccess.report(_fnf,false);
            System.out.println(_xmlFile + " not found.");
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
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
        return out.toString();
    }

    private void processStartDocument() {
        return;
    }

    private void processEndDocument() {
        return;
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
                ro = connect();
                if (bRequireLogon) {
                    if (logon(ro)) {
                        if (strLoad != null && actProf != null) {
                            generateBlob(strLoad,strValOn,strEffOn);
                            bSuccess = update(ro, actProf, bUpdate);
                        }
                    }
                } else {
                    generateBlob(strLoad,strValOn,strEffOn);
                    if (bUpdate != null) {
                        bSuccess = update(ro, null, bUpdate);
                    }
                }
                report(strIP,iPort,strName,strFile,null,null,true, bSuccess);		//acl_20050314
                logoff(ro);
            } catch (Exception _ex) {
                _ex.printStackTrace();
            }
        }else{
			System.out.println("Error missing something   ip: " + strIP+" port: " + iPort
				+" name: " + strName+" load: "+strLoad);
		}
    }

    private void processUpdate(Attributes _atts) {
        reset();
        for (int i = 0; i < _atts.getLength(); ++i) {
            if (_atts.getType(i).equals("CDATA")) {
                processUpdate(_atts.getLocalName(i), _atts.getValue(i));
            }
        }
        return;
    }

    private void processUpdate(String _code, String _value) {
        if (_code.equalsIgnoreCase("ip")) {
            strIP = _value;
        } else if (_code.equalsIgnoreCase("port")) {
            iPort = getInt(_value, -1);
        } else if (_code.equalsIgnoreCase("name")) {
            strName = _value;
        } else if (_code.equalsIgnoreCase("chain")) {
            if (have(_value)) {
                strChain = _value;
            }
        } else if (_code.equalsIgnoreCase("file")) {
            strLoad = _value;
        } else if (_code.equalsIgnoreCase("user")) {
            if (have(_value)) {
                strUser = _value;
            }
        } else if (_code.equalsIgnoreCase("pass")) {
            if (have(_value)) {
                strPass = _value;
            }
        } else if (_code.equalsIgnoreCase("valOn")) {
			if (have(_value)) {
				strValOn = _value;
			}
		} else if (_code.equalsIgnoreCase("effOn")) {
			if (have(_value)) {
				strEffOn = _value;
			}
        } else if (_code.equalsIgnoreCase(OPTIONAL)) {			//6299
			if (have(_value)) {									//6299
				if (_value.equalsIgnoreCase("true")) {			//6299
					bOptional = true;							//6299
				}												//6299
			}													//6299
		}														//6299
        return;
    }

    private void processRetrieve(Attributes _atts) {
        reset();
        for (int i = 0; i < _atts.getLength(); ++i) {
            if (_atts.getType(i).equals("CDATA")) {
                processRetrieve(_atts.getLocalName(i), _atts.getValue(i));
            }
        }
        return;
    }

    private void processRetrieve(String _code, String _value) {
        if (_code.equalsIgnoreCase("ip")) {
            strIP = _value;
        } else if (_code.equalsIgnoreCase("port")) {
            iPort = getInt(_value, -1);
        } else if (_code.equalsIgnoreCase("name")) {
            strName = _value;
        } else if (_code.equalsIgnoreCase("chain")) {
            if (have(_value)) {
                strChain = _value;
            }
        } else if (_code.equalsIgnoreCase("file")) {
            strLoad = _value;
        } else if (_code.equalsIgnoreCase("user")) {
            if (have(_value)) {
                strUser = _value;
            }
        } else if (_code.equalsIgnoreCase("pass")) {
            if (have(_value)) {
                strPass = _value;
            }
        } else if (_code.equalsIgnoreCase("valOn")) {
			if (have(_value)) {
				strValOn = _value;
			}
		} else if (_code.equalsIgnoreCase("effOn")) {
			if (have(_value)) {
				strEffOn = _value;
			}
		}
        return;
    }

    private void processEndRetrieve() {
        String sNow = null;
        boolean bSuccess = false;
        System.out.println("processing retrieve");
        if (ready()) {
            try {
                ro = connect();
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

                if (bRequireLogon) {
                    if (logon(ro)) {
                        if (strLoad != null) {
                            bSuccess = retrieve(ro);
                        }
                    }
                } else {
                    bSuccess = retrieve(ro);
                }
                report(strIP,iPort,strName,strFile,strEffOn,strValOn,false,bSuccess);		//acl_20050314
                logoff(ro);
            } catch (Exception _ex) {
                _ex.printStackTrace();
            }
        }else{
			System.out.println("Error missing something   ip: " + strIP+" port: " + iPort
				+" name: " + strName+" load: "+strLoad);
		}
    }

    private void process(Attributes _atts) {
        reset();
        for (int i = 0; i < _atts.getLength(); ++i) {
            if (_atts.getType(i).equals("CDATA")) {
                process(_atts.getLocalName(i), _atts.getValue(i));
            }
        }
        return;
    }

    private void process(String _code, String _value) {
        System.out.println("    code: " + _code + " value: " + _value);
        return;
    }

    private void processElement(String _tagName, Attributes _atts) {
        if (_tagName.equalsIgnoreCase("UPDATE")) {
            processUpdate(_atts);
		} else if (_tagName.equalsIgnoreCase("RETRIEVE")) {
			processRetrieve(_atts);
        } else {
            System.out.println("bypassing: " + _tagName);
            process(_atts);
        }
        return;
    }

    private void processEndElement(String _tagName) {
        if (_tagName.equalsIgnoreCase("UPDATE")) {
            processEndUpdate();
        } else if (_tagName.equalsIgnoreCase("RETRIEVE")) {
			processEndRetrieve();
		}
        return;
    }

	private void generate() {
		generate(strXMLFile);
		return;
	}

    private void generate(final String _xmlFile) {
        DefaultHandler defaulthandler = new DefaultHandler() {

            public void startElement(String _URI, String _tagName, String _qName, Attributes _atts) {
                processElement(_tagName, _atts);
                return;
            }

            public void endElement(String _URI, String _tagName, String _qName) {
                processEndElement(_tagName);
            }

            public void startDocument() {
                processStartDocument();
            }

            public void endDocument() {
                processEndDocument();
            }

            public void characters(char[] _rgc, int _nStart, int _nLength) throws SAXException {
            }

            public void error(SAXParseException _saxparseexception) throws SAXException {
                throw _saxparseexception;
            }
        };
        SAXParser parser = new SAXParser();
        parser.setContentHandler(defaulthandler);
        try {
            String xml = getXML(_xmlFile);
            if (xml != null) {
                parser.parse(new InputSource(new StringReader(xml)));
            }
        } catch (Exception _x) {
            _x.printStackTrace();
        }
        return;
    }
}
