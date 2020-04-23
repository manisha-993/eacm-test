package COM.ibm.eannounce.abr.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;


import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

public class XSLTUtil {
	static Map xsltCache = new HashMap();
	static Map entityMap = new HashMap();
	static Set entityTypeSet = new HashSet();
	public static final String XSLT_ENABLE = "XSLT_enable";
	public static final String ENTITY_TYPE_LIST = "_typelist";
	

	public static String transferXML(String sourceXML, String abrCode, String entityType, boolean reportModel) {

		String key = abrCode + "_" + entityType;
		System.out.println(key);
		if (!isXSLTEnable())
			// || !checkEntityType(abrCode, entityType)
			return null;

		try {
			SAXReader reader = new SAXReader();
			InputStream is = null;
			Transformer transformer = getTransformer(abrCode, entityType);
			/*if (reportModel) {
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			}

			else {
				transformer.setOutputProperty(OutputKeys.INDENT, "no");
			}*/

			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			is = new ByteArrayInputStream(sourceXML.getBytes("UTF-8"));
			Document doc = reader.read(is);
			java.io.StringWriter sw = new java.io.StringWriter();
			StreamResult result = new StreamResult(sw);
			DocumentSource source = new DocumentSource(doc);
			// DocumentResult result = new DocumentResult();
			transformer.transform(source, result);

			/*
			 * java.io.StringWriter sw = new java.io.StringWriter(); StreamResult result =
			 * new StreamResult(sw); // DOMSource source = new DOMSource(doc); Do
			 * cumentSource source = new DocumentSource(doc); // DocumentResult result = new
			 * DocumentResult(); transformer.transform(source, result); String xmlString =
			 * XMLElem.removeCheat(sw.toString()); System.out.println(xmlString);
			 * System.out.println("---------------------------");
			 * System.out.println(result.getWriter().toString());
			 */

			return sw.toString();

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	
	private static Transformer getTransformer(String abrCode, String entityType)
			throws TransformerConfigurationException {
		Transformer transformer;
		String key = abrCode + "_" + entityType;
		Templates cachedXSLT = (Templates) xsltCache.get(key);

		// Create new xlst template and cache it.
		if (cachedXSLT == null) {
			synchronized (xsltCache) {
				// Double check if required xslt exists in cache or not.
				cachedXSLT = (Templates) xsltCache.get(key);
				if (cachedXSLT == null) {
					// Create an XSLT transformer factory.
					TransformerFactory tFactory = TransformerFactory.newInstance();
					// Determine the XSLT transform file to use.
					String xsltPath = getXsltPath(abrCode, entityType);
					System.out.println("Loading xslt..." + xsltPath);
					cachedXSLT = tFactory.newTemplates(new StreamSource(xsltPath));
					System.out.println("success to load xslt - " + xsltPath);
					// cache xslt
					xsltCache.put(key, cachedXSLT);
				}
			}
		}
		// Create the transformer using the XSLT file.
		transformer = cachedXSLT.newTransformer();// tFactory.newTransformer(new StreamSource(xsltPath));

		return transformer;
	}


	/**
	 * get the abr configuration to store the xml file
	 * 
	 * @return xml file path
	 */
	public static String getXmlPath(String abrCode, String entityType) {
		System.out.println("getXmlPath:" + abrCode + "_" + entityType);
		String xmlPath = ABRServerProperties.getValue(abrCode, "_" + entityType, null);

		return xmlPath;
	}

	/**
	 * 
	 * @return xslt file path
	 */
	public static String getXsltPath(String abrCode, String entityType) {
		System.out.println("getXsltPath:" + abrCode + "_" + entityType);
		String xsltFilePath = ABRServerProperties.getValue(abrCode, "_" + entityType, null);

		return xsltFilePath;
	}

	public static boolean isXSLTEnable() {
		String enable = ABRServerProperties.getValue(XSLT_ENABLE, "", null);
		if (null != enable && "TRUE".equals(enable.toUpperCase().trim()))
			return true;
		return false;
	}

	public static boolean checkEntityType(String abrCode, String entityType) {
		Set set = (Set) entityMap.get(abrCode);
		if (set == null) {
			synchronized (entityMap) {
				set = (Set) entityMap.get(abrCode);
				if (set == null) {
					String entityTypeStr = ABRServerProperties.getValue(abrCode, ENTITY_TYPE_LIST, null);
					set = new HashSet();
					if (null != entityTypeStr) {
						String[] typrs = entityTypeStr.split(",");
						for (int i = 0; i < typrs.length; i++) {
							set.add(typrs[i].trim());
						}
					}
					entityMap.put(abrCode, set);
				}
			}
		}
		if (set != null && set.contains(entityType))
			return true;

		return false;
	}
}