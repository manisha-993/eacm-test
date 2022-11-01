/*
 * Created on Jan 13, 2005
 *
 * Licensed Materials -- Property of IBM
 *
 * (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
 *
 */
package com.ibm.transform.oim.eacm.xalan;

import java.io.ByteArrayOutputStream;
//import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//import org.w3c.dom.Element;
import com.ibm.eacm.AES256Utils;
import org.xml.sax.InputSource;

import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask;

import com.ibm.transform.oim.eacm.util.Log;
import com.ibm.transform.oim.eacm.util.Logger;
import com.ibm.transform.oim.eacm.xalan.input.template.ABRCode;
import com.ibm.transform.oim.eacm.xalan.input.template.ABRJob;
import com.ibm.transform.oim.eacm.xalan.input.template.ABRJobFactory;
import com.ibm.transform.oim.eacm.xalan.input.template.AttributeDescription;
import com.ibm.transform.oim.eacm.xalan.input.template.EntityDescription;
import com.ibm.transform.oim.eacm.xalan.input.template.ProfileEnterprise;
import com.ibm.transform.oim.eacm.xalan.input.template.ReadLanguage;
import com.ibm.transform.oim.eacm.xalan.input.template.Role;
import com.ibm.transform.oim.eacm.xalan.input.template.UserProfile;
import com.ibm.transform.oim.eacm.xalan.input.template.Workgroup;
import com.ibm.transform.oim.eacm.xalan.input.template.WriteLanguage;
import com.ibm.transform.oim.eacm.xalan.xslabr.Attribute;
import com.ibm.transform.oim.eacm.xalan.xslabr.Config;
import com.ibm.transform.oim.eacm.xalan.xslabr.ConfigFactory;
import com.ibm.transform.oim.eacm.xalan.xslabr.DynamicSQL;
import com.ibm.transform.oim.eacm.xalan.xslabr.Enterprise;
import com.ibm.transform.oim.eacm.xalan.xslabr.Entity;
import com.ibm.transform.oim.eacm.xalan.xslabr.InsertAttribute;
import com.ibm.transform.oim.eacm.xalan.xslabr.Invoke;
import com.ibm.transform.oim.eacm.xalan.xslabr.Obj;
import com.ibm.transform.oim.eacm.xalan.xslabr.Option;
import com.ibm.transform.oim.eacm.xalan.xslabr.SelectionAttribute;
import com.ibm.transform.oim.eacm.xalan.xslabr.Style;
import com.ibm.transform.oim.eacm.xalan.xslabr.XSLParam;

/**
 * Based on the Configuration.xml this class will create and populate an abr document
 * and style it using the specified xsl template. It will instanciate any classes required as parameters to the style sheet.
 * If it cannot find a configuration it will use the Configuration.xsl
 * <pre>
 * $Log: XSLReportABR.java,v $
 * Revision 1.19  2015/12/04 09:14:09  wangyul
 * Task1430425 - Storage Extract report needs to be able to run under the announcement and model entities - development
 *
 * Revision 1.18  2015/09/22 13:05:16  wangyul
 * Task 1386798 - Storage SS report for a given Machine Type
 *
 * Revision 1.17  2015/06/01 13:57:54  wangyul
 * RCQ00276438-WI /RCQ00188990-RQ Storage SS
 *
 * Revision 1.17  2015/06/01 21:46:15  Luis
 * RCQ00276438-WI /RCQ00188990-RQ Storage SS - support number list for dynamic sql
 * 
 * Revision 1.16  2008/11/06 16:24:45  wendy
 * Release memory
 *
 * Revision 1.15  2008/11/03 21:08:38  wendy
 * Restored reliance on pw in config file because taskmaster does not connect to ODS so it is not available thru mw
 *
 * Revision 1.14  2008/10/17 17:42:38  wendy
 * Removed reliance on config password, use Database connection instead
 *
 * Revision 1.13  2008/10/16 15:12:03  wendy
 * added error msgs
 *
 * Revision 1.12  2008/09/24 20:57:38  wendy
 * CQ00006066-WI updates
 *
 * Revision 1.11  2008/09/03 15:10:56  wendy
 * Added 'with ur' to query
 *
 * Revision 1.10  2008/08/26 21:27:45  wendy
 * Add support for list in sql for RQ0611073018 - XCC GX SR - Feature Code Where Used Report � Update
 *
 * Revision 1.9  2008/01/08 05:23:45  yang
 * *** empty log message ***
 *
 * Revision 1.8  2008/01/08 05:12:28  yang
 * *** empty log message ***
 *
 * Revision 1.7  2008/01/08 04:53:08  yang
 * *** empty log message ***
 *
 * Revision 1.6  2008/01/08 04:44:19  yang
 * *** empty log message ***
 *
 * Revision 1.5  2007/07/17 17:04:28  chris
 * Fix so base SQL will be output even when no extra parameters are populated.
 *
 * Revision 1.4  2007/07/16 17:37:41  chris
 * Fixes for unit test
 *
 * Revision 1.3  2007/07/03 16:45:27  chris
 * some more tweaks
 *
 * Revision 1.2  2007/07/03 13:08:46  chris
 * changes for pricing reports
 *
 * Revision 1.1  2007/03/07 00:21:17  chris
 * new
 *
 *
 * </pre>
 * @author cstolpe
 *
 */
public class XSLReportABR extends AbstractTask {
	// These are configuration XSDBEANS
	private Config config = null;
	private ABRJob abrJob = null;
	private Attribute abrAttribute = null;
	private Enterprise abrEnterprise = null;
	private Entity abrEntityType = null;
	private Style abrStyle = null;
	private SelectionAttribute abrSelectionAttribute = null;
	private Option abrOption = null;
	private StringBuffer messageSb = null;
	private ABRJobFactory jobFactory = new ABRJobFactory();
	// Interface for classes to implement a binary report
	private BinaryReport binaryReport = null;
	private boolean compress = false;
	// Some JDBC connections
	private Hashtable connections = new Hashtable();

	// The default report name is nav attributes + ABR attribute code description 
	private String dgReportName = "XSL Report ABR";
	private String dgReportClass = null;
	private EntityItem ei = null;

	private Logger log = new Logger();
	//  This resolver can get files from jar or filesystem
	private static final JarURIResolver resolver = new JarURIResolver();
	private Vector returnCodes = new Vector();
	private Transformer xslt = null;
	// Check required attribute for Storage Extract Report
	private StorageExtractSetupEntityChecker storageExtractSetupEntityChecker = new StorageExtractSetupEntityChecker();
	
	public XSLReportABR() {
		super();
	}
	
	/***
	 * release memory
	 * 
	 * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#dereference()
	 */
	public void dereference(){
		super.dereference();
		config = null;
		abrJob = null;
		abrAttribute = null;
		abrEnterprise = null;
		abrEntityType = null;
		abrStyle = null;
		abrSelectionAttribute = null;
		abrOption = null;
		messageSb = null;
		jobFactory = null;
		binaryReport = null;
		if (connections != null){
			connections.clear();
			connections = null;
		}
		dgReportName = null;
		dgReportClass = null;
		ei = null;
		log = null;

		if (returnCodes != null){
			returnCodes.clear();
			returnCodes = null;
		}
		xslt = null;
	}

	/**
	 * Executes the stylesheet on the abr document
	 * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
	 */
	public void execute_run() {		
		final String signature = ".execute_run(): ";
		boolean returnCode = true;
		log.setIdentifier(this.toString());
		dgReportClass = getABRItem().getABRCode();
		PrintWriter utf8Writer = null;

		try {
			EntityGroup eg;
			// Try to override PrintWriter for UTF-8 output 
			try {
				utf8Writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getABRItem().getFileName(), false), "UTF-8")); //$NON-NLS-1$
				setPrintWriter(utf8Writer);
			} catch (UnsupportedEncodingException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(e.getClass().getName());
				log.println(e.getMessage());
			} catch (FileNotFoundException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(e.getClass().getName());
				log.println(e.getMessage());
			}

			setReturnCode(AbstractTask.RETURNCODE_SUCCESS);
			eg = new EntityGroup(null, getDatabase(), getProfile(), getABRItem().getEntityType(), "Edit"); //$NON-NLS-1$
			ei =
				new EntityItem(
					eg,
					getProfile(),
					getDatabase(),
					getABRItem().getEntityType(),
					getABRItem().getEntityID());
			eg.put(ei.getKey(), ei);
			String ei2String = ei.toString();
			if (ei2String.length()>50) {// if name is too long ss doesnt extract from zip file CQ00006066-WI  
				ei2String = ei2String.substring(0, 50);
			}
			dgReportName = ei2String + getABRItem().getABRCode();
			
			if(storageExtractSetupEntityChecker.isRequiredAttributeEmpty(ei, getABRItem().getABRCode())) {
				setReturnCode(AbstractTask.RETURNCODE_FAILURE);
				print("<html><head><title>ABR failed</title></head><body>");
				print("MACHTYPE or ANNNUMBER can not be empty for Storage Extract Report ABR");
				print("<h2>Entity Item</h2>");
				print(ei.toString());
				print("</h2><h2>ABR Item</h2>");
				print(getABRItem().toString());
				print("<h2>Profile</h2>");
				print(getProfile().toString());	
				print("<p>ABR Failed</p>");
				print("</body></html>");
			} else {
				// The input for the transformation is now set up
				if (loadABRTemplate() && loadConfiguration() && processStyle()) {
					// return code is known before it is transformed
					for (int i = 0; i < returnCodes.size(); i++) {
						ReturnCode rc = (ReturnCode) returnCodes.elementAt(i);
						returnCode &= rc.hasPassed();
						if (!rc.hasPassed()){
							addMessage(rc.getMessage());
						}
					}
					if (returnCode) {
						setReturnCode(AbstractTask.RETURNCODE_SUCCESS);
						if (binaryReport != null) {
							byte[] tmp = binaryReport.getBytes();
							if (tmp != null && tmp.length > 0) {
								if (compress) {
									ByteArrayOutputStream os = new ByteArrayOutputStream();
									ZipOutputStream zos = new ZipOutputStream(os);
									String xlsname = dgReportName+"."+getFileExtension();
									zos.putNextEntry(new ZipEntry(xlsname));
									zos.write(tmp);
									zos.closeEntry();
									zos.flush();
									zos.close();
									
									setAttachByteForDG(os.toByteArray());
									getABRItem().setFileExtension("zip");

									if (getABRItem().getKeepFile()){//for debug write it to file
										try {
											FileOutputStream fos = new FileOutputStream(
													getABRItem().getFileName()+".zip");
											zos = new ZipOutputStream(fos);
											zos.putNextEntry(new ZipEntry(xlsname));
											zos.write(tmp);
											zos.closeEntry();
											zos.flush();
											zos.close();
											fos.flush();
											fos.close();            
										} catch (Throwable t) {
											System.out.println("Error while writing zipfile: " + t);
											t.printStackTrace();
										}	
									}
								}
								else {
									setAttachByteForDG(tmp);
								}
							}
						}
					} 
					else {
						setReturnCode(AbstractTask.RETURNCODE_FAILURE);
					}
					updateABRTemplate(); // add dg submit string to abr.xml

					Source input = new DOMSource(jobFactory.getDocument());
					// This would be ABR Stream
					Result output = new StreamResult(getPrintWriter());
					// transform puts out invalid xhtml if output method=html, method=xml works better
					xslt.transform(input, output);
				}
				else {
					setReturnCode(AbstractTask.RETURNCODE_FAILURE);
					dgReportName = getDGTitle(false);
					String msg = (messageSb==null?"See Log for failure information":messageSb.toString());
					throw new Exception(msg);
				}
			}

			if (getReturnCode() == AbstractTask.RETURNCODE_FAILURE) {
				getABRItem().setFileExtension("htm");
			}

			dgReportName = getDGTitle(false);
		} catch (Exception e) {
			setReturnCode(AbstractTask.RETURNCODE_FAILURE);
			log.print(getClass().getName());
			log.print(signature);
			log.print(e.getClass().getName());
			log.println(e.getMessage());
			
			java.io.StringWriter exBuf = new java.io.StringWriter();
			e.printStackTrace(new java.io.PrintWriter(exBuf));
			log.println(exBuf.getBuffer().toString());
			// Try and create a html report from the exception
			getABRItem().setFileExtension("htm");

			print("<html><head><title>An Exception Has Occurred</title></head><body><h3><span style=\"color:#c00; font-weight:bold;\">Report this error to operations</span></h3>");
			print(e.getMessage());
			print("<h2>Entity Item</h2>");
			print(ei.toString());
			print("</h2><h2>ABR Item</h2>");
			print(getABRItem().toString());
			print("<h2>Profile</h2>");
			print(getProfile().toString());
			print("<pre>");
			print(exBuf.getBuffer().toString());
			print("</pre></body></html>");
		} finally {
			setDGRptName(ei.toString() + getABRItem().getABRCode());
			setDGTitle(dgReportName);
			setDGRptName(dgReportName);
			setDGRptClass(dgReportClass); //$NON-NLS-1$
			// this is in wrong place, it is outside of the html here
			//printDGSubmitString(); but if there was an exception set dg anyway
			if (dgReportName.equals("XSL Report ABR")) {
				// if true, didn't get to getDGTitle()
				printDGSubmitString();
			}
			
			// make sure the lock is released
			if (!getABRItem().getReadOnly()) {
				//clearSoftLock();
			}

			if (utf8Writer != null) {
				utf8Writer.close();
			}

			if (!connections.isEmpty()) {
				
				try {
					Iterator i = connections.values().iterator();
					while (i.hasNext()) {
						((Connection)i.next()).close();
					}
				} catch (SQLException e) {
					log.print(getClass().getName());
					log.print(signature);
					log.print("SQLException ");
					log.println(e.getMessage());
				}
				connections.clear();
			}
		}
	}

	/**
	 * The CVS Revision of the ABR
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
	 */
	public String getABRVersion() {
		return "$Revision: 1.19 $"; //$NON-NLS-1$
	}

	/**
	 * The Description of the ABR
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getDescription()
	 */
	public String getDescription() {
		return "XSL Report ABR"; //$NON-NLS-1$
	}

	/**********************************************************************************
	 * get timestamp for queueing this ABR
	 */
	private String getABRQueuedTime()
	{
		String attCode = getABRItem().getABRCode();
		log.println("getABRQueuedTime entered for "+ei.getKey()+" "+attCode);
		try{
			EANAttribute att = ei.getAttribute(attCode);
			if (att != null) {
				AttributeChangeHistoryGroup achg = new AttributeChangeHistoryGroup(getDatabase(), getProfile(), att);
				if (achg.getChangeHistoryItemCount()>1){
					// last chghistory is the current one(in process), -2 is queued
					int i=achg.getChangeHistoryItemCount()-2;
					AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem)achg.getChangeHistoryItem(i);
					log.println("getABRQueuedTime ["+i+"] isActive: "+
							achi.isActive()+" isValid: "+achi.isValid()+" chgdate: "+
							achi.getChangeDate()+" flagcode: "+achi.getFlagCode());

					return achi.getChangeDate();
				} // has history items
				else {
					log.println("getABRQueuedTime for "+ei.getKey()+" "+attCode+" has no history");
				}
			} else {
				log.println("getABRQueuedTime for "+ei.getKey()+" "+attCode+" was null");
			}
		}catch(COM.ibm.opicmpdh.middleware.MiddlewareRequestException exc){
			exc.printStackTrace();			
		}
	
		return getProfile().getValOn();
	}

	/**
	 * Get the DG title based on the entity Nav attributes if ignore is false and the ABR is
	 * one of the Nav attributes it will use the ABR return code to determine the value otherwise
	 * it uses the value of the attribute from the extract
	 *
	 * @param ignore Wether to ignore the ABR navigate attribute.
	 * @exception  java.sql.SQLException  Description of the Exception
	 */
	private String getDGTitle(boolean ignore)
		throws
			java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareException {
		StringBuffer navName = new StringBuffer();
		// NAME is navigate attributes
		EntityGroup eg = new EntityGroup(null, getDatabase(), getProfile(), getABRItem().getEntityType(), "Navigate");
		EANMetaAttribute ma = null;
		Iterator itMeta = eg.getMetaAttribute().values().iterator();
		while (itMeta.hasNext()) {
			ma = (EANMetaAttribute) itMeta.next();
			Object val = "";
			if (!ignore
				&& ma.getAttributeCode().equals(
					getABRItem().getABRCode())) { // It's actual value is in proccess or queued
				val = (getReturnCode() == AbstractTask.RETURNCODE_SUCCESS) ? "Passed" : "Failed";
			} else {
				val = ei.getAttribute(ma.getAttributeCode());
			}
			if (val != null) {
				navName.append(val.toString());
				if (itMeta.hasNext()) {
					navName.append(" "); 
				}
			}
		}
		navName.append(" "); //$NON-NLS-1$
		eg = new EntityGroup(null, getDatabase(), getProfile(), getABRItem().getEntityType(), "Edit");
		ma = eg.getMetaAttribute(getABRItem().getABRCode());
		navName.append(ma.getLongDescription());
		return navName.toString();
	}

	private void setJDBCConnection(Object instance, String con) {
		final String signature = ".setJDBCConnection(Object,String): ";
		if (instance instanceof JDBCConnection) {
			int nConnections = abrStyle.getUsesConnectionCount();
			for (int i = 0; i < nConnections; i++) {
				String connection = abrStyle.getUsesConnection(i);
				if (connection != null && connection.equals(con)) {
					if (!connections.containsKey(con)) {
						int nCon = abrEnterprise.getConnectionCount();
						for (int j = 0; j < nCon; j++) {
							com.ibm.transform.oim.eacm.xalan.xslabr.Connection abrCon =	abrEnterprise.getConnection(j);
							if (abrCon.getId().equals(con)) {
								try {
									Class.forName(abrCon.getDatabaseDriver());
									Connection dbConnect = DriverManager.getConnection(
											abrCon.getDatabaseURL(),
											abrCon.getUserID(),
											AES256Utils.decrypt( abrCon.getPassword()));
											
									/* cant do this because taskmaster doesnt connect to the ODS
									if (con.equals("PDH")){
										dbConnect = this.getDatabase().getPDHConnection();
									}else{
										dbConnect = this.getDatabase().getODSConnection();
									}*/
									connections.put(con,dbConnect);
									
									JDBCConnection tmp = (JDBCConnection) instance;
									tmp.setConnection((Connection)connections.get(con), con);
								} catch (ClassNotFoundException e) {
									log.print(getClass().getName());
									log.print(signature);
									log.println(e.getMessage());
								} catch (SQLException e) {
									log.print(getClass().getName());
									log.print(signature);
									log.println(e.getMessage());
								} /*catch (MiddlewareException e) {
									log.print(getClass().getName());
									log.print(signature);
									log.println(e.getMessage());
								}*/
							}
						}
					} else {
						JDBCConnection tmp = (JDBCConnection) instance;
						tmp.setConnection((Connection)connections.get(con), con);
					}
				}
			}
		}
	}
	private boolean processStyle() throws TransformerException {
		//final String signature = ".processStyle(): ";
		boolean result = abrStyle != null;
		if (result) {
			// Instantiate the style sheet based on the <style> in the configuration
			compress = abrStyle.getZip();
			Source xsl = resolver.resolve(abrStyle.getXsl(), "");
			TransformerFactory factory = TransformerFactory.newInstance();
			factory.setURIResolver(resolver);
			xslt = factory.newTransformer(xsl);
			// Set all the parameters specified in the configuration
			int nParams = abrStyle.getXSLParamCount();
			for (int i = 0; i < nParams; i++) {
				XSLParam param = abrStyle.getXSLParam(i);
				String name = param.getName();
				Object value = "";
				if (param.getObjCount() > 0) {
					value = processObj(param.getObj(0));
				}
				else {
					value = param.getElementValue();
				}
				xslt.setParameter(name, value);
			}
			String dgTitle = abrStyle.getDGTitle();
			if (dgTitle != null && dgTitle.trim().length() > 0) {
				dgReportName = dgTitle.trim();
			}
			String dgClass = abrStyle.getDGClass();
			if (dgClass != null && dgClass.trim().length() > 0) {
				dgReportClass = dgClass.trim();
			}
		}
		return result;
	}

	/**
	 * Invoke could have no arguments like init,
	 * init will be a special case where we check for Init interface
	 * This is where
	 * @param invoke
	 * @param parent
	 * @return 
	 */
	private boolean processInvoke(Invoke invoke, Object parent) {
		final String signature = ".processInvoke(Invoke,Object): ";
		boolean result = true;
		String methodName = invoke.getMethod();
		Class parentClass = parent.getClass();
		// if invoke has a type it is the only parameter for the method and the parent is the target
		
		if (parent != null && methodName != null) {
			// get our parameters if any
			Object[] parameterInstances = null;
			Class[] parameterClasses = null;
			int nObjs = invoke.getObjCount();
			if (nObjs > 0) {
				// create an array of parameter instances
				parameterInstances = new Object[nObjs];
				// create an array of parameter classes from the instances
				parameterClasses = new Class[nObjs];
				for (int i = 0; i < nObjs; i++) {
					Obj obj = invoke.getObj(i);
					// create an array of parameter instances
					parameterInstances[i] = processObj(obj);
					// create an array of parameter classes from the instances
					parameterClasses[i] = parameterInstances[i].getClass();
				}
			}			

			// Look for the method
			Method method = null;
			Method[] methods = parentClass.getMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().equals(methodName)) {
					method = methods[i];
					Class[] args = method.getParameterTypes();
					if (args.length == nObjs) {
						// right number of arguments now lets look at the types
						for (int j = 0; j < nObjs; j++) {
							if (!args[j].isAssignableFrom(parameterClasses[j])) {
								result = false;
								log.print(parent.getClass().getName());
								log.print(signature);
								log.print(" argument type mismatch, cannot assign ");
								log.print(parameterClasses[j].getName());
								log.print(" to ");
								log.print(args[j].getName());
								log.print(" in method ");
								log.println(methodName);
							}
						}
					}
					else {
						result = false;
						log.print(parent.getClass().getName());
						log.print(signature);
						log.print(" wrong number of arguments for method: ");
						log.println(methodName);
					}
				}
			}
			
			// now try to invoke it
			if (method != null) {
				try {
					// invoke the method
					Object callResult = method.invoke(parent, parameterInstances);
					if (callResult != null && callResult instanceof Boolean) {
						if (Boolean.FALSE.equals(callResult)) {
							// assume false means a problem
							result = false;
							log.print(parent.getClass().getName());
							log.print(signature);
							log.print(method.toString());
							log.println(" returned false");
						}
						else {
							result = true;
						}
					}
					else {
						result = true;
					}
				} catch (IllegalAccessException e) {
					result = false;
					log.print(method.toString());
					log.print(": ");
					log.println(e.getMessage());
					log.print(getClass().getName());
					log.print(signature);
					log.print(e.getClass().getName());
					log.println(e.getMessage());
			
					java.io.StringWriter exBuf = new java.io.StringWriter();
					e.printStackTrace(new java.io.PrintWriter(exBuf));
					log.println(exBuf.getBuffer().toString());
				} catch (InvocationTargetException e) {
					result = false;
					log.print(method.toString());
					log.print(": ");
					log.println(e.getMessage());
					log.print(getClass().getName());
					log.print(signature);
					log.print(e.getClass().getName());
					log.println(e.getMessage());
			
					java.io.StringWriter exBuf = new java.io.StringWriter();
					e.printStackTrace(new java.io.PrintWriter(exBuf));
					log.println(exBuf.getBuffer().toString());
				}
			}
			else {
				result = false;
				log.print(parent.getClass().getName());
				log.print(signature);
				log.print(" method not found: ");
				log.println(methodName);
			}
		}
		return result;
	}
	private Object processObj(Obj obj) {
		final String signature = ".processObj(Obj): ";
		Object instance = null;
		String classname = obj.getType();
		if (classname == null) {
			DynamicSQL dsql = obj.getDynamicSQL();
			if (dsql != null) {
				return processDynamicSQL(dsql);				
			}
			else {
				return obj.getElementValue();
			}
		}
		try {
			Class instanceClass;
			instanceClass = Class.forName(classname);
			instance = instanceClass.newInstance();

			if (instance instanceof EntityParam) {
				EntityParam tmp = (EntityParam) instance;
				tmp.setEntityType(getABRItem().getEntityType());
				tmp.setEntityID(getABRItem().getEntityID());
			}
			if (instance instanceof BinaryReport) {
				binaryReport = (BinaryReport) instance;
				getABRItem().setFileExtension(binaryReport.getExtension());
			}
			setJDBCConnection(instance,"ODS");
			setJDBCConnection(instance,"PDH");
			if (instance instanceof PDHAccess) {
				PDHAccess tmp = (PDHAccess) instance;
				tmp.setDatabase(getDatabase());
				tmp.setProfile(getProfile());
			}
			if (instance instanceof Log) {
				Log tmp = (Log) instance;
				tmp.setIdentifier(this.toString());
			}
			if (instance instanceof ReturnCode) {
				if (!obj.getExcludeReturnCode()) {
					ReturnCode rc = (ReturnCode) instance;
					returnCodes.add(rc);
				}
			}
			if (instance instanceof DataView) {
				DataView tmp = (DataView) instance;
				tmp.setAttributeCodeOfABR(getABRItem().getABRCode());
			}
			int nInvokes = obj.getInvokeCount();
			for (int i = 0; i < nInvokes; i++) {
				processInvoke(obj.getInvoke(i), instance);
			}
		} catch (ClassNotFoundException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.print("ClassNotFoundException ");
			log.println(e.getMessage());
		} catch (InstantiationException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.print("InstantiationException ");
			log.println(e.getMessage());
		} catch (IllegalAccessException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.print("IllegalAccessException ");
			log.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.print("IllegalArgumentException ");
			log.println(e.getMessage());
		}

		return instance;
	}

	/**
	 * We have required and optional parameters. So we will compose the SQL dynamically.
	 * The DynamicSQL element must contain a BaseSQL element and one or more 
	 * InsertAttribute elements. The BaseSQL is static and contains everything 
	 * up to the WHERE clause. The WHERE clause is inserted only if there are 
	 * InsertAttribute elements that successfully return a result. For the 
	 * InsertAttribute to successfully return a result the attribute it is 
	 * looking for must be populated and the SQL fragment in the element must 
	 * have 1 question mark. More than 1 question mark will result in invalid 
	 * SQL being created. Each successful InsertAttribute is added as a AND 
	 * clause to the SQL. So the SQL fragment should not begin with an AND or a OR.
	 * If the attribute being checked is a EANFlagAttribute we only use the first
	 * active flag code asthe value.
	 * 
	 * @param dsql the DynamicSQL node from the configuration
	 * @return String (null String when problem occurs)
	 */
	private String processDynamicSQL(DynamicSQL dsql) {
		final String signature = ".processDynamicSQL(DynamicSQL): ";
		StringBuffer sbSQL = new StringBuffer();
		String baseSQL = dsql.getBaseSQL();
		if (baseSQL == null || baseSQL.trim().length() == 0) { 
			log.print(getClass().getName());
			log.print(signature);
			log.println("BaseSQL missing from DynamicSQL or is empty");
		}
		int nClauses = dsql.getInsertAttributeCount();
		if (nClauses == 0) {
			log.print(getClass().getName());
			log.print(signature);
			log.println("InsertAttribute missing from DynamicSQL. One or more are required.");
		}
		
		for (int i = 0; i < nClauses; i++) {
			InsertAttribute clause = dsql.getInsertAttribute(i);
			if (clause != null) {
				String attrCode = clause.getCode();
				String attrValue = "";
				EANAttribute att = ei.getAttribute(attrCode);
				if (att != null || 
					attrCode.equals("ABRQUEUEDDTS")){ // get the abr timestamp
					if (att instanceof EANFlagAttribute) {
						EANFlagAttribute fAtt = (EANFlagAttribute) att;
						if (clause.getUseFlagCode()) {
							attrValue = fAtt.getFirstActiveFlagCode();
						}
						else {
							attrValue = fAtt.getFlagLongDescription(fAtt.getFirstActiveFlagCode());
						}						
					}
					else {
						if (att != null) {						
							attrValue = att.get().toString();
						}else{
							if (attrCode.equals("ABRQUEUEDDTS")){
								attrValue = getABRQueuedTime(); //"ABRQUEUEDDTS"
							}
						}
						// must support list of values now
						if (clause.getIsList()) {
							// get max list allowed
							String maxlistStr = clause.getMaxList();
							int maxlist = 0;
							if (maxlistStr != null && maxlistStr.length()>0){
								maxlist = Integer.parseInt(maxlistStr);
							}
							
							boolean isNumber = clause.getIsNumber();
							
							// tokenize on comma and build quoted list
							StringBuffer listsb = new StringBuffer();
							StringTokenizer st = new StringTokenizer(attrValue,",");
							int tokenCnt = 1;
							while(st.hasMoreTokens()){	
								if (maxlist>0&&tokenCnt>maxlist){	
									StringBuffer msgSb = new StringBuffer(
										"Warning: "+att.getMetaAttribute().getActualLongDescription()+
										" exceeds the maximum of "+maxlist+". Will not process: ");
									while(st.hasMoreTokens()){
										msgSb.append(st.nextToken()+" ");
									}
									addMessage(msgSb.toString());
									break;
								}
								if (listsb.length()>0){
									listsb.append(",");
								}
								String token = st.nextToken().trim();
								
								if(!isNumber) {
									if (!token.startsWith("'")){
										token = "'"+token;
									}
									if (!token.endsWith("'")){
										token = token +"'";
									}
								}
							
								tokenCnt++;
								listsb.append(token);
							}
							attrValue = listsb.toString();	
						}
					}
					
					String sqlFragment = clause.getElementValue();
					int position = sqlFragment.indexOf('?');
					if (position >= 0) {
						if (sbSQL.length() > 0) {
							sbSQL.append(" AND ");
						}
						
						sbSQL.append(sqlFragment.substring(0,position));
						sbSQL.append(attrValue);
						sbSQL.append(sqlFragment.substring(position + 1));
					}
					else {
						log.print(getClass().getName());
						log.print(signature);
						log.println("InsertAttribute does not contain a '?' to indicate where to insert the value.");
					}
				}
				else {
					if (D.ebugShow() >= D.EBUG_DETAIL) {
						log.print(getClass().getName());
						log.print(signature);
						log.print(attrCode);
						log.print(" is null for ");
						log.println(ei.getKey());
					}					
				}
			}
			else {
				log.print(getClass().getName());
				log.print(signature);
				log.println("InsertAttribute is empty");
			}
		}
		
		if (sbSQL.length() > 0) {
			if (baseSQL.toUpperCase().indexOf("WHERE") == -1) { 
				sbSQL.insert(0, " WHERE ");
			}
		}
		sbSQL.insert(0, baseSQL);
		if (sbSQL.toString().toUpperCase().indexOf("WITH UR") == -1) { 
			sbSQL.append(" WITH UR");
		}
		addMessage("Executing: "+sbSQL.toString());
		if (D.ebugShow() >= D.EBUG_DETAIL) {
			log.print(getClass().getName());
			log.print(signature);
			log.print("SQL Returned: ");
			log.println(sbSQL);
		}
		return sbSQL.toString();
	}

	private void addMessage(String msg){
		if (msg.length()==0){
			return;
		}
		if (this.messageSb==null){
			messageSb = new StringBuffer(msg);
		}else{
			if (messageSb.length()>0){
				messageSb.append("\n");
			}
			messageSb.append(msg);
		}
	}
	private boolean loadABRTemplate() {
		//final String signature = ".loadABRTemplate(): ";
		boolean result = true;

		// Get the XML configuration that maps entity type/attribute code to a report
		InputSource configSource = new InputSource(resolver.getInputStream("/com/ibm/transform/oim/eacm/xalan/input/template/ABRJob.xml")); 
		jobFactory.setPackageName("com.ibm.transform.oim.eacm.xalan.input.template");
		// Load the document
		abrJob = (ABRJob) jobFactory.loadDocument("ABRJob", configSource);

		// Set timestamp and job id
		abrJob.setJobNumber(this.toString());
		abrJob.setTimestamp(getProfile().getNow());

		// Set the class and revision being run
		ABRCode code = abrJob.getABRCode();
		code.setClassName(this.getClass().getName());
		code.setRevision(getABRVersion());

		EntityDescription eDesc = abrJob.getEntityDescription();
		eDesc.setEntityType(getABRItem().getEntityType());
		eDesc.setEntityID(getABRItem().getEntityID());
		eDesc.updateElementValue(ei.getLongDescription());
		
		AttributeDescription xsdAD = eDesc.getAttributeDescription();
		xsdAD.setCode(getABRItem().getABRCode());
		xsdAD.updateElementValue(getABRItem().getABRDescription());

		// Set the profile information
		UserProfile  uProf = abrJob.getUserProfile();
		uProf.setUserToken(getProfile().getEmailAddress());

		Workgroup xsdWG = uProf.getWorkgroup();
		xsdWG.setWid(getProfile().getWGID());
		xsdWG.updateElementValue(getProfile().getWGName());

		ReadLanguage xsdRL = uProf.getReadLanguage();
		xsdRL.setNlsID(getProfile().getReadLanguage().getNLSID());
		xsdRL.updateElementValue(getProfile().getReadLanguage().getNLSDescription());

		WriteLanguage xsdWL = uProf.getWriteLanguage();
		xsdWL.setNlsID(getProfile().getWriteLanguage().getNLSID());
		xsdWL.updateElementValue(getProfile().getWriteLanguage().getNLSDescription()); 

		ProfileEnterprise xsdEnterprise = uProf.getProfileEnterprise();
		xsdEnterprise.setCode(getProfile().getEnterprise());
		xsdEnterprise.updateElementValue(" Enterprise");// TODO: Get a description of the enterprise

		Role xsdRole = uProf.getRole();
		xsdRole.setCode(getProfile().getRoleCode());
		xsdRole.updateElementValue(getProfile().getRoleDescription());

		return result;
	}

	private boolean loadConfiguration() {
		final String signature = ".loadConfiguration(): ";
		boolean result = true;

		// Get the XML configuration that maps entity type/attribute code to a report
		InputSource configSource = new InputSource(resolver.getInputStream("/com/ibm/transform/oim/eacm/xalan/Configuration.xml")); //$NON-NLS-2$  //$NON-NLS-1$
		ConfigFactory cf = new ConfigFactory();
		cf.setPackageName("com.ibm.transform.oim.eacm.xalan.xslabr");
		// Load the document
		config = (Config) cf.loadDocument("Config", configSource);

		// Get the configuration for this Enterprise
		int nEnterprises = config.getEnterpriseCount();
		for (int i = 0; i < nEnterprises; i++) {
			abrEnterprise = config.getEnterprise(i);
			if (getProfile().getEnterprise().equals(abrEnterprise.getAttributeValue("code"))) {
				i = nEnterprises;
			} else {
				abrEnterprise = null;
			}
		}
		
		// Get the configuration for this Entity Type
		if (abrEnterprise != null) {
			int nEntityTypes = abrEnterprise.getEntityCount();
			for (int i = 0; i < nEntityTypes; i++) {
				abrEntityType = abrEnterprise.getEntity(i);
				if (getABRItem().getEntityType().equals(abrEntityType.getType())) {
					i = nEntityTypes;
				} else {
					abrEntityType = null;
				}
			}
		} 
		else {
			result = false;
			log.print(getClass().getName());
			log.print(signature);
			log.print("configuration missing enterprise: ");
			log.println(getProfile().getEnterprise());
			addMessage("configuration missing enterprise: "+getProfile().getEnterprise());
		}

		// Get the configuration for this Attribute Code
		if (abrEntityType != null) {
			int nABRs = abrEntityType.getAttributeCount();
			for (int i = 0; i < nABRs; i++) {
				abrAttribute = abrEntityType.getAttribute(i);
				if (getABRItem().getABRCode().equals(abrAttribute.getCode())) {
					i = nABRs;
				} else {
					abrAttribute = null;
				}
			}
		}
		else {
			result = false;
			log.print(getClass().getName());
			log.print(signature);
			log.print("configuration missing entity type: ");
			log.print(getABRItem().getEntityType());
			log.print(" for this enterprise: ");
			log.println(getProfile().getEnterprise());
			addMessage("configuration missing entity type: "+getABRItem().getEntityType()+" for this enterprise: "+getProfile().getEnterprise());
		}
		
		if (abrAttribute != null) {
			abrSelectionAttribute = abrAttribute.getSelectionAttribute();
			if (abrSelectionAttribute == null) {
				abrStyle = abrAttribute.getStyle();
			}
		} 
		else {
			result = false;
			log.print(getClass().getName());
			log.print(signature);
			log.print("configuration missing attribute code: ");
			log.print(getABRItem().getABRCode());
			log.print(" for entity type: ");
			log.print(getABRItem().getEntityType());
			log.print(" for enterprise: ");
			log.println(getProfile().getEnterprise());
			addMessage("configuration missing attribute code: "+getABRItem().getABRCode()+
					" for entity type: "+getABRItem().getEntityType()+" for enterprise: "+getProfile().getEnterprise());
		}
		
		if (abrSelectionAttribute != null) {
			// This is optional now. But it could become the dominant approach
			int nOptions = abrSelectionAttribute.getOptionCount();
			String attrCode = abrSelectionAttribute.getCode();
			String selection = null;
			EANAttribute att = ei.getAttribute(attrCode);
			dgReportName = dgReportName + " " +att.getLongDescription();
			if (att instanceof EANFlagAttribute) {
				EANFlagAttribute fAtt = (EANFlagAttribute) att;
				selection = fAtt.getFirstActiveFlagCode();
				dgReportName = dgReportName + " " +fAtt.getFlagLongDescription(selection);
			}
			for (int i = 0; i < nOptions; i++) {
				abrOption = abrSelectionAttribute.getOption(i);
				String optionCode = abrOption.getCode();
				if (optionCode.equals(selection)) {
					abrStyle = abrOption.getStyle();
					i = nOptions;
				}
				else {
					abrOption = null;
				}
			}
		}

		if (abrStyle == null) {
			result = false;
			log.print(getClass().getName());
			log.print(signature);
			log.print(" style missing for attribute code: ");
			log.print(getABRItem().getABRCode());
			log.print(" for entity type: ");
			log.print(getABRItem().getEntityType());
			log.print(" for enterprise: ");
			log.println(getProfile().getEnterprise());
			addMessage(" style missing for attribute code: "+getABRItem().getABRCode()+" for entity type: "+
					getABRItem().getEntityType()+" for enterprise: "+getProfile().getEnterprise());
		}
		return result;
	}
	private void updateABRTemplate() throws Exception {
		String dgStr;
		// get the printwriter and replace it for now, need the dgsubmit string
		PrintWriter origPw = getPrintWriter();
		StringWriter sw = new StringWriter();
		PrintWriter tmpPw = new PrintWriter(sw);
		try {
			setPrintWriter(tmpPw);
			printDGSubmitString();

			dgStr = sw.toString();
			// Set the <dgsubmit> in the template, needed because dgsubmit comment must be inside html body
			abrJob.setDGSubmit(dgStr);
			setPrintWriter(origPw);
			// set any message
			if (messageSb!=null){
				abrJob.setMessage(messageSb.toString());
			}
		} catch (Exception e) {
			throw e;
		} finally {
			tmpPw.close();
		}
	}
}
