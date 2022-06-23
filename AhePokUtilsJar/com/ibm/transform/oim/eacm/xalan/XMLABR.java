/**
 * (c) Copyright International Business Machines Corporation, 2006
 * All Rights Reserved.
 */
package com.ibm.transform.oim.eacm.xalan;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xalan.extensions.ExpressionContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ibm.transform.oim.eacm.util.Log;
import com.ibm.transform.oim.eacm.util.Logger;

import COM.ibm.eannounce.objects.BlobAttribute;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.MetaColumnOrderGroup;
import COM.ibm.eannounce.objects.MetaColumnOrderItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.eannounce.objects.XMLAttribute;

/**
 * (c) Copyright International Business Machines Corporation, 2003,2004,2005,2006
 * All Rights Reserved.
 */
public class XMLABR implements Log {
	private Object abrCode = null;
	private final boolean isInitialized = false;
	private JarURIResolver uriResolver = new JarURIResolver();
	private Logger log = new Logger();
	private final DOMAttributeComparator comparator = new DOMAttributeComparator();
	
	/**
     * default constructor
     */
    public XMLABR() {
	}
	/**
	 * Setter method for the ABR code
	 * It gets the log identifier from the abr code at this point via the Log or Data interfaces
     * @param code
     * @return true if code is not null
     */
    public boolean setABRCode(ReturnCode code) {
    	boolean result = code != null;
    	if (result) {
			abrCode = code;
			if (abrCode instanceof Log) {
				Log tmp = (Log) abrCode;
				result &= log.setIdentifier(tmp.getIdentifier());
			}
			else if (abrCode instanceof Data) {
				Data tmp = (Data) abrCode;
				result &= log.setIdentifier(tmp.getDataView().getIdentifier());
			}
    	}
		return result;
	}
	/**
	 * Add an entity element to the group with the attributes requested in the XML document.
	 * @param domGroup
	 * @param attributeCodes
	 * @param entity
	 */
	private void addEntityElement(Element domGroup, Vector attributeCodes, EntityItem entity) {
		final String signature = ".addEntityElement(Element,Vector,EntityItem): ";
		EntityGroup eg = entity.getEntityGroup();
		MetaColumnOrderGroup order = eg.getMetaColumnOrderGroup();
		Document doc = domGroup.getOwnerDocument();
		int nAttributes = attributeCodes.size();
		Element domEntity = doc.createElement("entity");
		Set sortedAttributes = new TreeSet(comparator);
		Iterator attributes;
		
		log.print(getClass().getName());
		log.print(signature);
		log.print("adding entity for ");
		log.println(eg.getEntityType());

		domEntity.setAttribute("entityID",Integer.toString(entity.getEntityID()));
		
		for (int j = 0; j < nAttributes; j++) {
			EANAttribute att = entity.getAttribute((String)attributeCodes.elementAt(j));
			if (att == null) {
				log.print(getClass().getName());
				log.print(signature);
				log.print("attributecode ");
				log.print(attributeCodes.elementAt(j));
				log.print(" is not populated for ");
				log.println(entity.getKey());
			}
			else {
				Element attribute = doc.createElement("attribute");
				MetaColumnOrderItem mcoi = order.getMetaColumnOrderItem(eg.getKey() + ":" + att.getKey() + ":C");

				attribute.setAttribute("code",att.getAttributeCode().trim());
				String typeStr = att.getMetaAttribute().getAttributeType().trim();
				if(validate(typeStr))
				attribute.setAttribute("type",typeStr);

				if (mcoi != null) {
					attribute.setAttribute("order",Integer.toString(mcoi.getColumnOrder()));
				}
				else {
					attribute.setAttribute("order","-1");
				}
				if (att instanceof EANFlagAttribute) {
					EANFlagAttribute flagAtt = (EANFlagAttribute) att;
					MetaFlag[] mfa = (MetaFlag[]) flagAtt.get();
					for (int k = 0; k < mfa.length; k++) {
						if (mfa[k].isSelected()) {
							Element flag = doc.createElement("flag");
							attribute.appendChild(flag);
							String flagcode = mfa[k].getFlagCode();
							if(validate(typeStr))
							flag.setAttribute("code" ,flagcode );
							flag.appendChild(doc.createTextNode(mfa[k].getLongDescription()));
						}								
					}
				}
				else if (att instanceof XMLAttribute) {
					attribute.appendChild(doc.createCDATASection(att.toString()));
				}
				else if (att instanceof BlobAttribute) {
				}
				else {
					attribute.appendChild(doc.createTextNode(att.toString()));
				}
				sortedAttributes.add(attribute);
				domGroup.appendChild(domEntity);
			}
		}
		attributes = sortedAttributes.iterator();
		while (attributes.hasNext()) {
			domEntity.appendChild((Node) attributes.next());
		}
		log.print(getClass().getName());
		log.print(signature);
		log.print("completed entity for ");
		log.println(eg.getEntityType());
	}
	public boolean validate(String value) {
		if(value==null) {
			return true;
		}
		return true;
	}
	/**
	 * Adds a meta element to the group with all the descriptions of the attribute codes and flag codes
	 * @param eg
	 * @param attributeCodes
	 * @param domGroup
	 */
	private void addMetaElement(EntityGroup eg, Vector attributeCodes, Element domGroup) {
		final String signature = ".addMetaNode(EntityGroup, Vector, Element): ";
		Document doc = domGroup.getOwnerDocument();
		Element meta = doc.createElement("meta");
		Element type = doc.createElement("type");
		int nAttributes = attributeCodes.size();

		meta.appendChild(type);
		type.appendChild(doc.createTextNode(eg.getLongDescription()));
		domGroup.appendChild(meta);
		log.print(getClass().getName());
		log.print(signature);
		log.print("start meta for ");
		log.println(eg.getEntityType());

		for (int j = 0; j < nAttributes; j++) {
			Element description = doc.createElement("description");
			String code = (String)attributeCodes.elementAt(j);
			EANMetaAttribute ma = eg.getMetaAttribute(code);
			meta.appendChild(description);
			description.setAttribute("attributecode", code.trim());
			if (ma != null) {
				String desc = ma.getActualLongDescription();
				if (desc != null && desc.length() > 0) {
					description.appendChild(doc.createTextNode(desc));
				}
				else {
					log.print(getClass().getName());
					log.print(signature);
					log.print("no long description was found for meta attribute ");
					log.print(code);
					log.print(" in the meta for ");
					log.println(eg.getEntityType());
				}
			}
			else {
				log.print(getClass().getName());
				log.print(signature);
				log.print("attributecode ");
				log.print(code);
				log.print(" was not found in the meta for ");
				log.println(eg.getEntityType());
		
				log.print(getClass().getName());
				log.print(signature);
				log.print("# of meta attributes is ");
				log.print(eg.getMetaAttributeCount());
				log.print(", set = ");
				log.println(eg.getMetaAttribute().keySet());
			}
		}
		log.print(getClass().getName());
		log.print(signature);
		log.print("completed meta for ");
		log.println(eg.getEntityType());
	}
	/**
	 * Gets all attribute codes from the entity group unless the group element in the XML document
	 * has include elements. Then it will only retrieve the included ones. At some point I will implement an exclude as well.
	 * So you will be able to use the most convient way.
	 * @param domGroup
	 * @param eg
	 * @return
	 */
	private Vector getAttributeCodes(Element domGroup, EntityGroup eg) {
		Vector attributeCodes = new Vector();
		if (domGroup.hasChildNodes()) {
			NodeList includes = domGroup.getElementsByTagName("include");
			int nIncludes = includes.getLength();
			for (int j = 0; j < nIncludes; j++) {
				 Element include = (Element) includes.item(j);
				 attributeCodes.add(include.getAttribute("attributecode"));
			}
		}
		else {
			// do all
			int nMetaAttributes = eg.getMetaAttributeCount();
			for (int j = 0; j < nMetaAttributes; j++) {
				attributeCodes.add(eg.getMetaAttribute(j).getAttributeCode());
			}
		}
		return attributeCodes;
	}
	/**
     * Populates the specified XML document using the abrCode and then dereferences the abr.
     *
     * @param exprContext
     * @throws javax.xml.parsers.ParserConfigurationException
     * @return
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     * @param fileName 
     */
	public Node getDOM(ExpressionContext exprContext, String fileName) throws ParserConfigurationException, SAXException, IOException {
		final String signature = ".getDOM(ExpressionContext, String): ";
	    Document doc = null;	    
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		if (exprContext == null) {
			log.print(getClass().getName());
			log.print(signature);
			log.print(abrCode.getClass().getName());
			log.println("ExpressionContext is null");  //$NON-NLS-1$
		}

		doc = builder.parse(uriResolver.getInputStream(fileName));
	    if (!isInitialized && abrCode instanceof Init) {
	        Init tmp = (Init) abrCode;
	        if (!tmp.initialize()) {
	        	log.print(getClass().getName());
	        	log.print(signature);
				log.print(abrCode.getClass().getName());
	            log.println(".initialize() returned false");  //$NON-NLS-1$
	        }
	    }
	    if (abrCode == null) {
			log.print(getClass().getName());
			log.print(signature);
	        log.println("abrCode is null");  //$NON-NLS-1$
	    }
	    else {
	        NodeList domGroups = doc.getElementsByTagName("group");
			int nDOMGroups = domGroups.getLength();
			
			// get the name(s) of the test methods and record the result(s) in the DOM
			updateTestElements(doc);
			// get the name(s) of the text methods and record the result(s) in the DOM
			updateTextElements(doc);
			// get the name(s) of the xml methods and append the root node to the xml element
			// This should be <p> for the data a user enters, and ABR might create something else since it bypasses the XMLEditor
			updateXMLElements(doc);
			
			for (int i = 0; i < nDOMGroups; i++) {
				Element domGroup = (Element) domGroups.item(i);
				updateGroupElement(domGroup);
			}
	    }
	    if (abrCode instanceof PDHAccess) {
			((PDHAccess)abrCode).dereference();
	    }
	    return doc.getDocumentElement();
	}
	/**
	 * Looks for the EntityGroup in the Vector returned by the ABR specified by the xml document
	 * it also removes the group from the vector.
	 * @param domEntityType
	 * @return EntityGroup if found null otherwize
	 */
	private EntityGroup getEntityGroup(String domEntityType) {
		EntityGroup egFound = null;
		DataView dv = ((Data) abrCode).getDataView();
		Set entityGroups = dv.getEntityGroups();
		if (entityGroups.contains(domEntityType)) {
			egFound = dv.getEntityGroup(domEntityType);
		}
		return egFound;
	}
	/**
	 * If the entity group type specified in the xml document is present
	 * this will add the meta and entities returned by the abr java code
	 * @param domGroup
	 */
	private void updateGroupElement(Element domGroup) {
		final String signature = ".updateDOMGroupNode(Element): ";
		String domEntityType = domGroup.getAttribute("entityType");
		EntityGroup eg;

		if (domGroup.hasAttribute("rootEntityType")) {
			domEntityType = ((Data) abrCode).getDataView().getEntityType();
			domGroup.setAttribute("entityType", domEntityType);
		}
		eg = getEntityGroup(domEntityType);
		if (eg != null) {
			Vector attributeCodes = getAttributeCodes(domGroup, eg);
			Iterator ei = eg.getEntityItem().values().iterator();

			attributeCodes.size();
			domGroup.getOwnerDocument();
			addMetaElement(eg, attributeCodes, domGroup);
			
			while (ei.hasNext()) {
				EntityItem entity = (EntityItem) ei.next();
				addEntityElement(domGroup, attributeCodes, entity);
			}
		}
		else {
			log.print(getClass().getName());
			log.print(signature);
			log.print("entity group ");
			log.print(domEntityType);
			log.println(" was not found int the getData() Vector");
		}
	}
	/**
	 * Go through all the test elements in the xml document and invoke the name methods and update the result attribute
	 * @param doc
	 */
	private void updateTestElements(Document doc) {
		final String signature = ".updateTestElements(Document): ";
		NodeList tests = doc.getElementsByTagName("test");
		int nTests = tests.getLength();
		for (int i = 0; i < nTests; i++) {
			Element test = (Element) tests.item(i);
			String methodName = test.getAttribute("method");
			try {
				Method method = abrCode.getClass().getMethod(methodName, null);
				Object result = method.invoke(abrCode, null);
				test.setAttribute("result", result.toString());
			} catch (SecurityException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.println(e.getMessage());
			} catch (NoSuchMethodException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.print(" no such method: ");
				log.println(e.getMessage());
			} catch (IllegalArgumentException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.println(e.getMessage());
			} catch (IllegalAccessException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.println(e.getMessage());
			} catch (InvocationTargetException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.println(e.getMessage());
			}
		}
	}
	/**
	 * Go through all the text elements in the xml document and invoke the name methods and update the result attribute
	 * A special case is the getDisplayName. It expects the abr to implement the Data and ReturnCode interfaces and will invoke the DataView.getDisplayName(ReturnCode.hasPassed())
	 * @param doc
	 */
	private void updateTextElements(Document doc) {
		final String signature = ".updateTextElements(Document): ";
		NodeList textElements = doc.getElementsByTagName("text");
		int nElements = textElements.getLength();
		for (int i = 0; i < nElements; i++) {
			Element text = (Element) textElements.item(i);
			String methodName = text.getAttribute("method");
			try {
				Object result = "No Value";
				Text value;
				if (methodName.equals("getDisplayName")) {
					// Special case
					if (abrCode instanceof Data) {
						Data d = (Data) abrCode;
						if (abrCode instanceof ReturnCode) {
							ReturnCode r = (ReturnCode) abrCode; 
							result = d.getDataView().getDisplayName(r.hasPassed());
						}
						else {
							log.print(getClass().getName());
							log.print(signature);
							log.print(abrCode.getClass().getName());
							log.println(" does not implement the ReturnCode interface.");
						}
					}
					else {
						log.print(getClass().getName());
						log.print(signature);
						log.print(abrCode.getClass().getName());
						log.println(" does not implement the Data interface.");
					}
				}
				else {
					Method method = abrCode.getClass().getMethod(methodName, null);
					result = method.invoke(abrCode, null);
				}
				value = doc.createTextNode(result.toString());
				text.appendChild(value);
			} catch (SecurityException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.println(e.getMessage());
			} catch (NoSuchMethodException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.print(" no such method: ");
				log.println(e.getMessage());
			} catch (IllegalArgumentException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.println(e.getMessage());
			} catch (IllegalAccessException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.println(e.getMessage());
			} catch (InvocationTargetException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.println(e.getMessage());
			}
		}
	}
	/**
	 * Go through all the xml elements in the xml document and invoke the name methods and update the result attribute
	 * A special case is the getDisplayName. It expects the abr to implement the Data and ReturnCode interfaces and will invoke the DataView.getDisplayName(ReturnCode.hasPassed())
	 * @param doc
	 */
	private void updateXMLElements(Document doc) {
		final String signature = ".updateXMLElements(Document): ";
		NodeList textElements = doc.getElementsByTagName("xml");
		int nElements = textElements.getLength();
		for (int i = 0; i < nElements; i++) {
			Element text = (Element) textElements.item(i);
			String methodName = text.getAttribute("method");
			StringReader sr = null;
			
			try {
				Object result = "<p>No Value</p>";
				Method method = abrCode.getClass().getMethod(methodName, null);
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

				result = method.invoke(abrCode, null);
				if (docBuilderFactory != null) {
					DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
					sr = new StringReader(result.toString());
					if (docBuilder != null) {
						InputSource is = new InputSource(sr);
						Document docAttribute = docBuilder.parse(is);
						Node value = docAttribute.getDocumentElement();
						value = doc.importNode(value, true);
						text.appendChild(value);
					}
				}
			} catch (SecurityException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.println(e.getMessage());
			} catch (NoSuchMethodException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.print(" no such method: ");
				log.println(e.getMessage());
			} catch (IllegalArgumentException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.println(e.getMessage());
			} catch (IllegalAccessException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.println(e.getMessage());
			} catch (InvocationTargetException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.println(e.getMessage());
			} catch (SAXException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.println(e.getMessage());
			} catch (IOException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.println(e.getMessage());
			} catch (ParserConfigurationException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(abrCode.getClass().getName());
				log.println(e.getMessage());
			}
			finally {
				if (sr != null) {
					sr.close();
				}
			}
		}
	}
	/**
     * Implements setter method of Log interface
     *
     * @param anIdentifyer
     * @return boolean true if anIdentifyer is not null
     */
	public boolean setIdentifier(String anIdentifyer) {
		return log.setIdentifier(anIdentifyer);
	}
	/**
	 * Getter method of Log interface
	 * @return String identifier
	 */
	public String getIdentifier() {
		return log.getIdentifier();
	}
}
