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
package COM.ibm.eannounce.abr;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ibm.eacm.AES256Utils;
import com.ibm.transform.oim.eacm.util.Log;
import com.ibm.transform.oim.eacm.xalan.BinaryReport;
import com.ibm.transform.oim.eacm.xalan.DataView;
import com.ibm.transform.oim.eacm.xalan.EntityParam;
import com.ibm.transform.oim.eacm.xalan.Init;
import com.ibm.transform.oim.eacm.xalan.JarURIResolver;
import com.ibm.transform.oim.eacm.xalan.ODSConnection;
import com.ibm.transform.oim.eacm.xalan.PDHAccess;
import com.ibm.transform.oim.eacm.xalan.ReturnCode;
import com.ibm.transform.oim.eacm.xalan.table.EntityTable;

import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;

/**
 * Based on the XSLReportABRConfiguration.xml this class will create and populate an abr document
 * and style it using the specified xsl template. It will instanciate any classes required as parameters to the style sheet.
 * If it cannot find a configuration it will use the XSLTReportABRConfiguration.xsl
 * @deprecated replaced by {@link com.ibm.transform.oim.transform.oim.eacm.XSLReportABR}
 * <pre>
 * $Log: XSLReportABR.java,v $
 * Revision 1.13  2007/09/28 15:26:57  chris
 * deprecated class
 *
 * Revision 1.12  2006/12/20 14:41:40  chris
 * Design change for TIR 6WJMRP to prevent abr from running when DMNET is running
 *
 * Revision 1.11  2006/12/14 13:51:29  chris
 * Switch from ODS to PDH properties
 *
 * Revision 1.10  2006/12/13 21:00:47  chris
 * SPML Drop
 *
 * Revision 1.9  2006/10/19 21:33:37  chris
 * Enhancements for Taxonomy ABR's
 *
 * Revision 1.8  2006/05/03 20:13:44  chris
 * Add support for optional param attribute initBefore to force code to run before xslt
 *
 * Revision 1.7  2006/02/26 23:15:50  wendy
 * Jtest changes
 *
 * Revision 1.6  2006/02/23 15:45:52  wendy
 * Moved dgsubmit inside body tag
 *
 * Revision 1.5  2006/01/26 15:32:58  wendy
 * AHE copyright
 *
 * Revision 1.4  2005/11/14 12:44:38  wendy
 * New AHE format
 *
 * Revision 1.3  2005/03/03 15:32:48  chris
 * Remove some debug prints
 *
 * Revision 1.2  2005/03/02 18:21:16  chris
 * Updated to allow classes passed to XLST to alter return code.
 *
 * Revision 1.1  2005/02/23 21:15:57  chris
 * Initial XSL Report ABR Code
 *
 * </pre>
 * @author cstolpe
 *
 */
public class XSLReportABR extends PokBaseABR {
    //  This resolver can get files from jar or filesystem
    private final JarURIResolver resolver = new JarURIResolver();
    private String configError = null;
    private String xslFilename = null;
    private MessageFormat mf = null;
    private final String[] mfArgs = new String[10];
    private EntityItem ei = null;
    private Vector returnCodes = new Vector();
	private BinaryReport binaryReport = null;
    private Connection conODS = null;
    private String dgReportNameOveride = null;

    /**
     * Executes the stylesheet on the abr document
     * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
     */
    public void execute_run() {
        boolean returnCode = true;
        String reportName = "XSL Report ABR";
        PrintWriter utf8Writer = null;
        try {
            DocumentBuilderFactory docBuilderFactory;
            DocumentBuilder docBuilder;
            Source input;
            Result output;
            Transformer xslt;
            EntityGroup eg;
            Document abrDoc;
            start_ABRBuild(false);
            // Try to override PrintWriter for UTF-8 output TODO Bala want's to put this in taskmaster so we can remove it at some point
            try {
                utf8Writer =
                    new PrintWriter(
                        new OutputStreamWriter(
                            new FileOutputStream(getABRItem().getFileName(), false),
                            "UTF-8"));  //$NON-NLS-1$
                setPrintWriter(utf8Writer);
            } catch (UnsupportedEncodingException e) {
                logError(e, "Unable to override PrintWriter for UTF-8 output");  //$NON-NLS-1$
            } catch (FileNotFoundException e) {
                logError(e, "Unable to override PrintWriter for UTF-8 output");  //$NON-NLS-1$
            }

            setReturnCode(PASS);
            eg =  new EntityGroup(null, m_db, m_prof, m_abri.getEntityType(), "Navigate");  //$NON-NLS-1$
            ei = new EntityItem(eg, m_prof, m_db, m_abri.getEntityType(), m_abri.getEntityID());
            eg.put(ei.getKey(), ei);
            docBuilderFactory = DocumentBuilderFactory.newInstance();
            if (docBuilderFactory == null) {
                throw new Exception("XSLReportABR.execute_run(): could not create document builder factory");  //$NON-NLS-1$
            }
            docBuilder = docBuilderFactory.newDocumentBuilder();
            if (docBuilder == null) {
                throw new Exception("XSLReportABR.execute_run(): could not create document builder");  //$NON-NLS-1$
            }

            // The input for the transformation is now set up
            abrDoc = (Document)initializeABRTemplate(docBuilder);
            input = new DOMSource(abrDoc);
            // This would be ABR Stream
            output = new StreamResult(getPrintWriter());
            xslt = initializeStyleSheet(docBuilder);
            // return code is known before it is transformed
            for (int i = 0; i < returnCodes.size(); i++) {
                ReturnCode rc = (ReturnCode) returnCodes.elementAt(i);
                returnCode &= rc.hasPassed();
            }
            if (returnCode) {
                setReturnCode(PASS);
                if (binaryReport != null) {
                	byte[] tmp = binaryReport.getBytes();
                	if (tmp == null || tmp.length == 0) {
                		
                	}
                	setAttachByteForDG(tmp);
                }
            }
            else {
                setReturnCode(FAIL);
            }
            updateABRTemplate(abrDoc); // add dg submit string to abr.xml
            // transform puts out invalid xhtml if output method=html, method=xml works better
			xslt.transform(input, output);

            reportName = getDGTitle(false);
        } catch (Exception exc) {
			String Error_EXCEPTION="<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
			String Error_STACKTRACE="<pre>{0}</pre>";
			java.io.StringWriter exBuf = new java.io.StringWriter();
			setReturnCode(FAIL);
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			// Put exception into document
			mf = new MessageFormat(Error_EXCEPTION);
			mfArgs[0] = exc.getMessage();
			println(mf.format(mfArgs));
			mf = new MessageFormat(Error_STACKTRACE);
			mfArgs[0] = exBuf.getBuffer().toString();
			println(mf.format(mfArgs));
			logError("Exception: "+exc.getMessage());
			logError(exBuf.getBuffer().toString());
        }
        finally {
        	setDGName(ei,m_abri.getABRCode());
        	if (dgReportNameOveride != null) {
				reportName = dgReportNameOveride + ((getReturnCode() == PASS) ? " has Passed" : " has Failed");
				//TODO add method to OPICMABRItem to set the file extension and set it back to html here
        	}
			setDGTitle(reportName);
            setDGRptName(reportName);
            setDGRptClass(m_abri.getABRCode());  //$NON-NLS-1$
            // this is in wrong place, it is outside of the html here
            //printDGSubmitString(); but if there was an exception set dg anyway
            if (reportName.equals("XSL Report ABR")) { // if true, didn't get to getDGTitle()
				printDGSubmitString();
			}

            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }

            if (utf8Writer!=null) {
				utf8Writer.close();
			}
			
			if (conODS != null) {
				try {
					conODS.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        }
    }

    private Element getStyleSheet(Document config) throws TransformerException, SAXException, IOException, Exception {

        Element node = null;
        NodeList nl = config.getElementsByTagName(getEntityType());
        if (nl == null || nl.getLength() == 0) {
            mf = new MessageFormat("XSLReportABR.getStyleSheet(DocumentBuilder): Could not find config for Entity Type: {0}"); //$NON-NLS-1$
            mfArgs[0] = getEntityType();
            setConfigError(mf.format(mfArgs));
        }
        else if (nl.getLength() > 1) {
            mf = new MessageFormat("XSLReportABR.getStyleSheet(DocumentBuilder): More than one config for Entity Type: {0}"); //$NON-NLS-1$
            mfArgs[0] = getEntityType();
            setConfigError(mf.format(mfArgs));
        }
        else {
            node = (Element) nl.item(0); // Entity Type
            nl = node.getElementsByTagName(m_abri.getABRCode());
            if (nl == null || nl.getLength() == 0) {
                mf = new MessageFormat("XSLReportABR.getStyleSheet(DocumentBuilder): Could not find config for ABR Code: {0}"); //$NON-NLS-1$
                mfArgs[0] = m_abri.getABRCode();
                setConfigError(mf.format(mfArgs));
            }
            else if (nl.getLength() > 1) {
                mf = new MessageFormat("XSLReportABR.getStyleSheet(DocumentBuilder): More than one config for ABR Code: {0}"); //$NON-NLS-1$
                mfArgs[0] = getEntityType();
                setConfigError(mf.format(mfArgs));
            }
            else {
                node = (Element) nl.item(0); // ABR Code
                nl = node.getElementsByTagName("style");  //$NON-NLS-1$
                if (nl == null || nl.getLength() == 0) {
                    mf = new MessageFormat("XSLReportABR.getStyleSheet(DocumentBuilder): Could not find style for: Entity Type {0}, Attribute Code {1}"); //$NON-NLS-1$
                    mfArgs[0] = getEntityType();
                    mfArgs[1] = m_abri.getABRCode();
                    setConfigError(mf.format(mfArgs));
                }
                else if (nl.getLength() > 1) {
                    mf = new MessageFormat("XSLReportABR.getStyleSheet(DocumentBuilder): More than one style for: Entity Type {0}, Attribute Code {1}"); //$NON-NLS-1$
                    mfArgs[0] = getEntityType();
                    mfArgs[1] = m_abri.getABRCode();
                    setConfigError(mf.format(mfArgs));
                }
                else {
                    Element style = (Element) nl.item(0);
                    setXslFilename(style.getAttribute("xsl"));  //$NON-NLS-1$
                }
            }
        }
        return node;
    }

    private Document getConfiguration(DocumentBuilder docBuilder)
        throws TransformerException, Exception, SAXException, IOException {
        // Get the XML configuration that maps entity type/attribute code to a report
        StreamSource configSource = (StreamSource)resolver.resolve("/com/ibm/transform/oim/eacm/xalan/XSLReportABRConfiguration.xml","");  //$NON-NLS-2$  //$NON-NLS-1$
        Document config;
        if (configSource == null) {
        	setReturnCode(FAIL);
            throw new Exception("XSLReportABR.getConfiguration(DocumentBuilder): could not resolve XSLReportABRConfiguration.xml");  //$NON-NLS-1$
        }
        config = docBuilder.parse(configSource.getInputStream());
        if (config == null) {
			setReturnCode(FAIL);
            throw new Exception("XSLReportABR.getConfiguration(DocumentBuilder): could not create config document");  //$NON-NLS-1$
        }
        return config;
    }

    private Transformer initializeStyleSheet(DocumentBuilder docBuilder)
        throws
    TransformerException,
    Exception,
    TransformerConfigurationException,
    InstantiationException,
    IllegalAccessException,
    ClassNotFoundException
    {
        Document config = getConfiguration(docBuilder);
        Element node = getStyleSheet(config);
        NodeList nl;
        // Instanciate the style sheet based on the <style> in the configuration
        Source xsl = null;
        Transformer xslt = null;
        TransformerFactory factory = TransformerFactory.newInstance();
        if (factory == null) {
            throw new Exception("XSLReportABR.initializeStyleSheet(DocumentBuilder): could not create transformer factory");  //$NON-NLS-1$
        }
        factory.setURIResolver(resolver);
        if (getConfigError() != null) {
            xsl = resolver.resolve(getXslFilename(),"");  //$NON-NLS-1$
            if (xsl == null) {
                mf = new MessageFormat("XSLReportABR.initializeStyleSheet(DocumentBuilder): could not resolve configuration exception style sheet: {0}"); //$NON-NLS-1$
                mfArgs[0] = getXslFilename();
                throw new Exception(mf.format(mfArgs));
            }
            xslt = factory.newTransformer(xsl);
            xslt.setParameter("configError", getConfigError());  //$NON-NLS-1$
        }
        else {
            xsl = resolver.resolve(getXslFilename(),"");  //$NON-NLS-1$
            if (xsl == null) {
                mf = new MessageFormat("Could not resolve: {0} for Entity Type {1}, Attribute Code {2}"); //$NON-NLS-1$
                mfArgs[0] = getXslFilename();
                mfArgs[1] = getEntityType();
                mfArgs[2] = m_abri.getABRCode();
                setConfigError(mf.format(mfArgs));
                xsl = resolver.resolve(getXslFilename(),"");  //$NON-NLS-1$
                if (xsl == null) {
                    mf = new MessageFormat("XSLReportABR.initializeStyleSheet(DocumentBuilder): could not resolve configuration exception style sheet: {0}"); //$NON-NLS-1$
                    mfArgs[0] = getXslFilename();
                    throw new Exception(mf.format(mfArgs));
                }
                xslt = factory.newTransformer(xsl);
                xslt.setParameter("configError", getConfigError());  //$NON-NLS-1$
            }
            else {
                xslt = factory.newTransformer(xsl);
                // Set all the parameters specified in the configuration
                nl = node.getElementsByTagName("xslparam");  //$NON-NLS-1$
                for (int i = 0; i < nl.getLength(); i++) {
                    Element param = (Element) nl.item(i);
                    String name = param.getAttribute("name");  //$NON-NLS-1$
                    Object value = null;
                    if (param.hasAttribute("class")) {
						value = initilizeClass(param);
                    }
                    else {
                        // This is just text parameter
                        value = param.getFirstChild().getNodeValue();
                    }
                    xslt.setParameter(name, value);
                }
				nl = node.getElementsByTagName("dgTitle");  //$NON-NLS-1$
				if (nl != null && nl.getLength() > 0) {
					//TODO make bullet proof
					dgReportNameOveride = nl.item(0).getFirstChild().getNodeValue();
				}
            }
        }
        return xslt;
    }

	private Object initilizeClass(Element paramElement) throws Exception {
		final String signature = getClass().getName() + ".initializeClass(Element): ";
		Object instance = null;
		String classname = paramElement.getAttribute("class");  //$NON-NLS-1$
		try {
			Class instanceClass;
			instanceClass = Class.forName(classname);
			instance = instanceClass.newInstance();
			if (instance instanceof EntityParam) {
				EntityParam tmp = (EntityParam) instance;
				tmp.setEntityType(m_abri.getEntityType());
				tmp.setEntityID(m_abri.getEntityID());
			}
			if (instance instanceof BinaryReport) {
				binaryReport = (BinaryReport) instance;
			}
			if (instance instanceof ODSConnection) {
				Class.forName("COM.ibm.db2.jdbc.app.DB2Driver");
				if (conODS == null) {
					conODS = DriverManager.getConnection(
						MiddlewareServerProperties.getPDHDatabaseURL(),
						MiddlewareServerProperties.getPDHDatabaseUser(),
						AES256Utils.decrypt(MiddlewareServerProperties.getPDHDatabasePassword())
					);
				}
				ODSConnection tmp = (ODSConnection) instance;
				tmp.setODSconnection(conODS);
			}
			if (instance instanceof PDHAccess) {
				PDHAccess tmp = (PDHAccess) instance;
				tmp.setDatabase(m_db);
				tmp.setProfile(m_prof);
			}
			if (instance instanceof Log) {
				Log tmp = (Log) instance;
				tmp.setIdentifier(this.toString());
			}
			if (instance instanceof ReturnCode) {
				if (!paramElement.hasAttribute("excludeReturnCode")) {
					ReturnCode rc = (ReturnCode) instance;
					returnCodes.add(rc);
				}
			}
			if (instance instanceof DataView) {
				// instance of DataView needs veName before init called if it uses a VE
				DataView dv = (DataView) instance;
				dv.setAttributeCodeOfABR(m_abri.getABRCode());
				if (paramElement.hasAttribute("veName")) {
					dv.setVEName(paramElement.getAttribute("veName"));
				}
			}
			// set all objparam's before initialization
			if (paramElement.hasChildNodes()) {
				Node child = paramElement.getFirstChild();
				do {
					if (child.getNodeType() == Node.ELEMENT_NODE) {
						Element el = (Element) child;
						if (el.getTagName().equals("objparam")) {
							if (el.hasAttribute("method")) {
								String mName = el.getAttribute("method");
								if (el.hasAttribute("class")) {
									// create an array of parameter instances
									Object[] parameterInstances = new Object[] {
										initilizeClass(el)
									};
									// create an array of parameter classes from the instances
									Class[] parameterClasses = new Class[] {
										parameterInstances[0].getClass()
									};
									// Get the method using the method name and array of parameter classes
									Method method = null;
									try {
										method = 
											instanceClass.getMethod(
												mName,
												parameterClasses);
									} catch (NoSuchMethodException e1) {
										// getMethod will throw an exception of you need to cast the object for the class to match
										Method[] methods = instanceClass.getMethods();
										for (int i=0; i<methods.length;i++) {
											if (methods[i].getName().equals(mName)) {
												method = methods[i];
												// TODO should check the parameter type and number against what is being passed
											}
										}
										if (method == null) {
											throw e1;
										}
										else {
											Class[] tmp = method.getParameterTypes();
											if (!tmp[0].isInstance(parameterInstances[0])) {
												System.err.print(signature);
												System.err.print(instance.getClass().getName());
												System.err.print(" method ");
												System.err.print(mName);
												System.err.print(" requires ");
												System.err.print(tmp[0].getName());
												System.err.print(" not ");
												System.err.println(parameterInstances[0].getClass().getName());
												e1.printStackTrace();
											}
										}
									}
									// invoke the method
									Object result = method.invoke(instance, parameterInstances);
									if (result != null && result instanceof Boolean) {
										if (Boolean.FALSE.equals(result)) {
											// assume false means a problem
											System.err.print(signature);
											System.err.print(method.toString());
											System.err.println(" returned false");
										}
									}
								}
								else {
									System.err.print(signature);
									System.err.println(" class is a required attribute of objparam");
								}
							}
							else {
								System.err.print(signature);
								System.err.println(" method is a required attribute of objparam");
							}
						}
						else {
							System.err.print(signature);
							System.err.println("ignored unrecognized tag: " + el.getTagName());
						}
					}
					child = child.getNextSibling();
				}
				while (child != null);			
			}
			if (instance instanceof Init) {
				Init tmp = (Init) instance;
				if (paramElement.hasAttribute("initBefore") && "true".equals(paramElement.getAttribute("initBefore"))) {
					// Forcing code to run before xslt
					if (!tmp.initialize()) { 
						System.err.print(signature);
						System.err.println("initialization of " + classname + " failed");
					}
				}
			}
			if (instance instanceof EntityTable) {
				// must specify column attribute codes
				NodeList nlColumns = paramElement.getElementsByTagName("column");  //$NON-NLS-1$
				String[] codes = new String[nlColumns.getLength()];
				for (int col = 0; col < codes.length; col++) {
					Element column = (Element) nlColumns.item(col);
					codes[col] = column.getAttribute("code");  //$NON-NLS-1$
				}
				if (codes.length > 0) {
					EntityTable table = (EntityTable) instance;
					table.setColumnAttributeCodes(codes);
				}
				else {
					throw new Exception(signature + "no attribute codes specified for the EntityTable in the configuration");  //$NON-NLS-1$
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;
	}
    private void updateABRTemplate(Document template)
        throws Exception
    {
		String dgStr;
        // Get the ABR Template
        Element abr = template.getDocumentElement();
        // get the printwriter and replace it for now, need the dgsubmit string
        PrintWriter origPw = getPrintWriter();
        StringWriter sw = new StringWriter();
        PrintWriter tmpPw = new PrintWriter(sw);
        try{
		setPrintWriter(tmpPw);
		printDGSubmitString();

        dgStr = sw.toString();
        // Set the <dgsubmit> in the template, needed because dgsubmit comment must be inside html body
        setElementValue(abr, "dgsubmit", dgStr);  //$NON-NLS-1$
        setPrintWriter(origPw);
		}catch(Exception e) {
			throw e;
		}
		finally{
			tmpPw.close();
		}
	}
    private Node initializeABRTemplate(DocumentBuilder docBuilder)
        throws
    Exception,
    ParserConfigurationException,
    TransformerException,
    SAXException,
    IOException
    {

        // Get the ABR Template
        Document template;
        Element abr;
        Element node;
        InputStream abrTemplateSource = resolver.getInputStream("/com/ibm/transform/oim/eacm/xalan/input/template/abr.xml");  //$NON-NLS-1$
        if (abrTemplateSource == null) {
            throw new Exception("XSLReportABR.initializeABRTemplate(DocumentBuilder): could not resolve abr.xml template");  //$NON-NLS-1$
        }
        template = docBuilder.parse(abrTemplateSource);
        abr = template.getDocumentElement();
        if (template == null) {
            throw new Exception("XSLReportABR.initializeABRTemplate(DocumentBuilder): could not create abr document");  //$NON-NLS-1$
        }
        // Set the <class> in the template
        setElementValue(abr, "class", this.getClass().getName());  //$NON-NLS-1$

        // Set the <code> in the template
        setElementValue(abr, "code", getABRCode());  //$NON-NLS-1$

        // Set the <entity> in the template
        node = setElementValue(abr, "entity", ei.getLongDescription());  //$NON-NLS-1$
        node.setAttribute("type", getEntityType());  //$NON-NLS-1$
        node.setAttribute("id", Integer.toString(getEntityID()));  //$NON-NLS-1$

        // Set the <revision> in the template
        setElementValue(abr, "revision", getABRVersion());  //$NON-NLS-1$

        // Set the <job-id> in the template
        setElementValue(abr, "job-id", this.toString());  //$NON-NLS-1$

        // Set the <timestamp> in the template
        setElementValue(abr, "timestamp", m_prof.getNow());  //$NON-NLS-1$

        // Set the <user-token> in the template
        setElementValue(abr, "user-token", m_prof.getEmailAddress());  //$NON-NLS-1$

        // Set the <workgroup> in the template

        node = setElementValue(abr, "workgroup", m_prof.getWGName());  //$NON-NLS-1$
        node.setAttribute("id", Integer.toString(m_prof.getWGID()));  //$NON-NLS-1$

        // Set the <read> language in the template
        node = setElementValue(abr, "read", m_prof.getReadLanguage().getNLSDescription());  //$NON-NLS-1$
        node.setAttribute("id",Integer.toString(m_prof.getReadLanguage().getNLSID()));  //$NON-NLS-1$

        // Set the <write> language in the template
        node = setElementValue(abr, "write", m_prof.getWriteLanguage().getNLSDescription());  //$NON-NLS-1$
        node.setAttribute("id",Integer.toString(m_prof.getWriteLanguage().getNLSID()));  //$NON-NLS-1$

        // Set the <enterprise> in the template
        setElementValue(abr, "enterprise", m_prof.getEnterprise());  //$NON-NLS-1$

        // Set the <role> in the template
        node = setElementValue(abr, "role", m_prof.getRoleDescription());  //$NON-NLS-1$
        node.setAttribute("code",m_prof.getRoleCode());  //$NON-NLS-1$
        return template;
    }
    private Element setElementValue(Element parent, String tagName, String value) throws Exception {
        NodeList nl = parent.getElementsByTagName(tagName);
        Element node;
        Node textNode;
        if (nl == null || nl.getLength() == 0) {
            mf = new MessageFormat("XSLReportABR.setElementValue(Element,String,String): could not find {0}"); //$NON-NLS-1$
            mfArgs[0] = tagName;
            throw new Exception(mf.format(mfArgs));
        }
        if (nl.getLength() > 1) {
            mf = new MessageFormat("XSLReportABR.setElementValue(Element,String,String): only one {0} allowed in abr template");  //$NON-NLS-1$
            mfArgs[0] = tagName;
            throw new Exception(mf.format(mfArgs));
        }
        node = (Element) nl.item(0);
        textNode = node.getFirstChild();
        textNode.setNodeValue(value);
        return node;
    }

    /**
     * Get the DG title based on the entity Nav attributes if ignore is false and the ABR is
     * one of the Nav attributes it will use the ABR return code to determine the value otherwise
     * it uses the value of the attribute from the extract
     *
     * @param ignore Wether to ignore the ABR navigate attribute.
     * @exception  java.sql.SQLException  Description of the Exception
     */
    private  String getDGTitle(boolean ignore)
    throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        StringBuffer navName = new StringBuffer();
        // NAME is navigate attributes
        EntityGroup eg =  ei.getEntityGroup();
        Iterator itMeta = eg.getMetaAttribute().values().iterator();
        while (itMeta.hasNext()) {
            EANMetaAttribute ma = (EANMetaAttribute) itMeta.next();
            if (!ignore && ma.getAttributeCode().equals(getShortClassName(getClass()))) { // It's actual value is in proccess or queued
                navName.append((getReturnCode() == PASS) ? "Passed" : "Failed");  //$NON-NLS-1$  //$NON-NLS-2$
            }
            else {
                navName.append(getAttributeValue(ei, ma.getAttributeCode()));
            }
            if (itMeta.hasNext()) {
                navName.append(" ");  //$NON-NLS-1$
            }
        }
        navName.append(" ");  //$NON-NLS-1$
        navName.append(
            getMetaAttributeDescription(
                new EntityGroup(
                    null,
                    m_db,
                    m_prof,
                    m_abri.getEntityType(),
                    "Edit"),  //$NON-NLS-1$
                m_abri.getABRCode()));
        return navName.toString();
    }

    /**
     * The Description of the ABR
     * @see COM.ibm.eannounce.abr.util.PokBaseABR#getDescription()
     */
    public String getDescription() {
        return "XSL Report ABR";  //$NON-NLS-1$
    }

    /**
     * The CVS Revision of the ABR
     * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
     */
    public String getABRVersion() {
        return "$Revision: 1.13 $";  //$NON-NLS-1$
    }

    /**
     * Setter for the configError. Also logs message and sets style sheet to config error style.
     * @param error
     */
    public void setConfigError(String error) {
        setReturnCode(FAIL);
        this.configError = error;
        logError(error);
        xslFilename = "/com/ibm/transform/oim/eacm/xalan/style/XSLReportABRConfigurationError.xsl";  //$NON-NLS-1$
    }

    /**
     * Getter for the configError
     * @return
     */
    public String getConfigError() {
        return configError;
    }

    /**
     * Setter for xslFilename
     * @param filename
     */
    public void setXslFilename(String filename) {
        this.xslFilename = filename;
    }

    /**
     * Getter for xslFilename
     * @return
     */
    public String getXslFilename() {
        return xslFilename;
    }
}
