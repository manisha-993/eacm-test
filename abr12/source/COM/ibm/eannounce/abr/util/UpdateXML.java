package COM.ibm.eannounce.abr.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class UpdateXML {


	public LinkedList store(String xml) {
		LinkedList warrList = new LinkedList();
		
		try {
			DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = newDocumentBuilder.parse(new InputSource(new StringReader(xml)));
			Element root = doc.getDocumentElement();	

			NodeList warrVec = root.getElementsByTagName("WARRELEMENT");
	
		    for (int i=0; i < warrVec.getLength();i++) {
		        Element warrElement = (Element) warrVec.item(i);;
		        boolean sort = false;
		        WarrElem warr = new WarrElem();
		        
		        Element action = (Element) warrElement.getElementsByTagName("WARRACTION").item(0);
		        warr.setAction(action.getTextContent());
		       
		        Element entityType = (Element) warrElement.getElementsByTagName("WARRENTITYTYPE").item(0);
		        warr.setEntityType(entityType.getTextContent());
		        
		        Element entityId = (Element) warrElement.getElementsByTagName("WARRENTITYID").item(0);
		        warr.setEntityId(entityId.getTextContent());
		        
		        Element warrid = (Element) warrElement.getElementsByTagName("WARRID").item(0);
		        warr.setWarrid(warrid.getTextContent());
		       
		        Element warrpriod = (Element) warrElement.getElementsByTagName("WARRPRIOD").item(0);
		        warr.setWarrpriod(warrpriod.getTextContent());
		        
		        Element pubFrom = (Element) warrElement.getElementsByTagName("PUBFROM").item(0);
		        warr.setPubFrom(pubFrom.getTextContent());
		        
		        Element pubTo = (Element) warrElement.getElementsByTagName("PUBTO").item(0);
		        warr.setPubTo(pubTo.getTextContent());
		        
		        Element defwarr = (Element) warrElement.getElementsByTagName("DEFWARR").item(0);
		        if("Yes".equals(defwarr.getTextContent())) {
		        	sort = true;
		        }
		        warr.setDefwarr(defwarr.getTextContent());
		        
		        NodeList countryList = warrElement.getElementsByTagName("COUNTRYELEMENT");
		        	
		        Vector ctryList = new Vector();
		        	
		        for(int j = 0;j < countryList.getLength();j++) {
		        	Ctry ctry = new Ctry();
		        	Element countryElement = (Element) countryList.item(i);
		        	Element caction = (Element) countryElement.getElementsByTagName("COUNTRYACTION").item(j);
		        	ctry.setAction(caction.getTextContent());
			       	Element ctry_fc = (Element) countryElement.getElementsByTagName("COUNTRY_FC").item(j);
			       	ctry.setCtry(ctry_fc.getTextContent());
			        	
			       	ctryList.add(ctry);
		        }
		        
	            warr.setCountryList(ctryList);
		            
		        if(sort) {
		           	warrList.addLast(warr);
		        }else {
		           	warrList.addFirst(warr);
		        }
		        
		    }
		}catch (TransformerFactoryConfigurationError e) {
			
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		} catch (SAXException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	        	   
	    return warrList;
	}
	
	public String update(String xml, LinkedList list) {
		
		StreamResult result = null;
		try {
			DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = newDocumentBuilder.parse(new InputSource(new StringReader(xml)));
			Element root = doc.getDocumentElement();
		
			NodeList warrList = root.getElementsByTagName("WARRELEMENT");

			for (int i=0; i < warrList.getLength();i++) {
		        Element warrElement = (Element) warrList.item(i);
		        
		        WarrElem warr = (WarrElem) list.get(i);

		        Element action = (Element) warrElement.getElementsByTagName("WARRACTION").item(0);
		        action.setTextContent(warr.getAction());
		        
		        Element entityType = (Element) warrElement.getElementsByTagName("WARRENTITYTYPE").item(0);
		        entityType.setTextContent(warr.getEntityType());
		        
		        Element entityId = (Element) warrElement.getElementsByTagName("WARRENTITYID").item(0);
		        entityId.setTextContent(warr.getEntityId());
		        
		        Element warrid = (Element) warrElement.getElementsByTagName("WARRID").item(0);
		        warrid.setTextContent(warr.getWarrid());
		        
		        Element warrpriod = (Element) warrElement.getElementsByTagName("WARRPRIOD").item(0);
		        warrpriod.setTextContent(warr.getWarrpriod());
		        
		        Element pubFrom = (Element) warrElement.getElementsByTagName("PUBFROM").item(0);
		        pubFrom.setTextContent(warr.getPubFrom());
		        
		        Element pubTo = (Element) warrElement.getElementsByTagName("PUBTO").item(0);
		        pubTo.setTextContent(warr.getPubTo());
		        
		        Element defwarr = (Element) warrElement.getElementsByTagName("DEFWARR").item(0);		      
		        defwarr.setTextContent(warr.getDefwarr());
		       		        
		        Node nodeDel = warrElement.getElementsByTagName("COUNTRYLIST").item(0);		        
		        nodeDel.getParentNode().removeChild(nodeDel);
		        		        		     		        
		        Vector ctryList = warr.getCountryList();
		        
		        Element countryElem = doc.createElement("COUNTRYLIST");
		        
		        for(int j = 0;j < ctryList.size();j++) {
		        	Element country = doc.createElement("COUNTRYELEMENT");
		        	Ctry ctry = (Ctry) ctryList.get(j);		        	
		        	Element caction = doc.createElement("COUNTRYACTION");
		        	caction.setTextContent(ctry.getAction());
			       	Element ctry_fc = doc.createElement("COUNTRY_FC");
			       	ctry_fc.setTextContent(ctry.getCtry());
			       	country.appendChild(caction);
			       	country.appendChild(ctry_fc);
			       	
			       	countryElem.appendChild(country);
		        }
		        warrElement.appendChild(countryElem);
			}
			
			Transformer transformer;
		
			transformer = TransformerFactory.newInstance().newTransformer();
			
			Source source = new DOMSource(doc);
			
			result = new StreamResult( new StringWriter());
			transformer.transform(source, result);
					
			
		} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
			
			e.printStackTrace();
		} catch (TransformerException e) {
			
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		} catch (SAXException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	
		return result.getWriter().toString();
	}
	
	private static class WarrElem{
		
		public static final String CHEAT = "@@";
		String action = CHEAT;
		String entityType = CHEAT;
		String entityId = CHEAT;
		String warrid = CHEAT;
		String Warrpriod = CHEAT;
		String pubFrom = CHEAT;
		String pubTo = CHEAT;
		String defwarr = CHEAT;
		Vector countryList = new Vector();
		
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public String getEntityType() {
			return entityType;
		}
		public void setEntityType(String entityType) {
			this.entityType = entityType;
		}
		public String getEntityId() {
			return entityId;
		}
		public void setEntityId(String entityId) {
			this.entityId = entityId;
		}
		public String getWarrid() {
			return warrid;
		}
		public void setWarrid(String warrid) {
			this.warrid = warrid;
		}
		public String getWarrpriod() {
			return Warrpriod;
		}
		public void setWarrpriod(String warrpriod) {
			Warrpriod = warrpriod;
		}
		public String getPubFrom() {
			return pubFrom;
		}
		public void setPubFrom(String pubFrom) {
			this.pubFrom = pubFrom;
		}
		public String getPubTo() {
			return pubTo;
		}
		public void setPubTo(String pubTo) {
			this.pubTo = pubTo;
		}
		public String getDefwarr() {
			return defwarr;
		}
		public void setDefwarr(String defwarr) {
			this.defwarr = defwarr;
		}
		public Vector getCountryList() {
			return countryList;
		}
		public void setCountryList(Vector countryList) {
			this.countryList = countryList;
		}
				
	}
	
	private static class Ctry{
		public static final String CHEAT = "@@";
		String action = CHEAT;
		String ctry = CHEAT;
		
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public String getCtry() {
			return ctry;
		}
		public void setCtry(String ctry) {
			this.ctry = ctry;
		}
			
	}
	
}
