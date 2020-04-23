package com.ibm.eannounce.wwprt.model;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.Log;
import com.ibm.eannounce.wwprt.model.price.FEAPrice;
import com.ibm.eannounce.wwprt.model.price.FUPPrice;
import com.ibm.eannounce.wwprt.model.price.GRDPrice;
import com.ibm.eannounce.wwprt.model.price.GROPrice;
import com.ibm.eannounce.wwprt.model.price.IgnoredPrice;
import com.ibm.eannounce.wwprt.model.price.MODPrice;
import com.ibm.eannounce.wwprt.model.price.MUPPrice;
import com.ibm.eannounce.wwprt.model.price.NoTypePrice;
import com.ibm.eannounce.wwprt.model.price.QTOPrice;
import com.ibm.eannounce.wwprt.model.price.QTYPrice;
import com.ibm.eannounce.wwprt.model.price.RPQPrice;
import com.ibm.eannounce.wwprt.model.price.SWFPrice;
import com.ibm.eannounce.wwprt.model.price.TFUPrice;
import com.ibm.eannounce.wwprt.model.price.TMUPrice;
import com.ibm.eannounce.wwprt.model.price.VALPrice;
import com.ibm.eannounce.wwprt.model.price.VAOPrice;
import com.ibm.eannounce.wwprt.model.price.VARPrice;
import com.ibm.eannounce.wwprt.model.price.WSFPrice;

public class Prices {

	public static final String STATUS_NEW = "New";
	public static final String STATUS_COMPLETE = "Complete";
	public static final String STATUS_ERROR = "Error";
	public static final String VERSION_V1 ="V1";
	public static final String VERSION_V2 ="V2";
	public static String offeringtypes = Context.get().getOfferingtypes();
	
	private static final Hashtable<String, String> OFFERINGTYPE_TBL; 
    static{
    	OFFERINGTYPE_TBL = new Hashtable<String, String>();
    	if(offeringtypes==null || "".endsWith(offeringtypes)){
    		//RTC defect 839128 change PID to SEO, 
    		//also need change the price.offeringtypes in the wwprt-inbound.properties
    		offeringtypes = "SEO,EEE,OSP,MLP,CON,SWS";
    	}
    	
    	String offeringtype[] = offeringtypes.split(",");
    	for (int i =0;i< offeringtype.length;i++) {
    		OFFERINGTYPE_TBL.put(offeringtype[i],offeringtype[i]);
		}	
	}
	
	private NodeList priceList;

	private int priceIndex;

	private Map<String, PriceTable> priceTypeMap;
	private Document document;

	public Prices() {
		priceTypeMap = new HashMap<String, PriceTable>();
	}
	
	public void loadXML(Document document) {
		this.document = document;
		priceIndex = 0;
		priceList = document.getElementsByTagName("price");
	}

	public boolean hasValidId() {
		String id = getId();
		return (id != null) && (id.length() > 0);
	}

	public String getId() {
		return document.getDocumentElement().getAttribute("id");
	}
	
	public boolean hasNextPrice() {
		return priceIndex < priceList.getLength();
	}
	
	/**
	 * get the next price 
	 * @return
	 * @throws IllegalStateException
	 */
	public PriceTable nextPrice() throws IllegalStateException {
		if (priceIndex < priceList.getLength()) {
			Node item = priceList.item(priceIndex);
			priceIndex++;
			if (item.getNodeType() == Node.ELEMENT_NODE
					&& item.getNodeName().equals("price")) {
				Element priceElement = (Element) item;
				NodeList offeringtype = priceElement.getElementsByTagName("offeringtype");
				String Version = "";
				if (offeringtype.getLength() > 0) {
					Version = VERSION_V2;
				} else {
					Version = VERSION_V1;
				}
				NodeList types = null;
				String type = null;
				if(Version.equals(VERSION_V2)){
					types = priceElement.getElementsByTagName("varianttype");
					/**
					 * For V2 of the inbound XML, <varianttype> is used to determine the derivation used for the columns of the PRICE TABLE. 
					 * If it is empty, then <offeringtype> will be used in three cases (values):
					 *	1.	SEO
					 *	2.	EEE
					 *	3.	OSP
					 *  All other values are ignored and the inbound XML will be ignored
					 */
					String varianttype_value = "";
					if (types.getLength() > 0) {
						varianttype_value = types.item(0).getTextContent();
					}else{
						varianttype_value = "";
					}			
					if("".equals(varianttype_value)){
						String offeringtype_value = offeringtype.item(0).getTextContent();
						if(!OFFERINGTYPE_TBL.containsKey(offeringtype_value)){
							Log.i("offeringtype_value="+offeringtype_value +", the offeringtype is not correct! available offeringtypes is " + offeringtypes);
							return null;
						}						
					}
					priceTypeMap.put("NoType", new NoTypePrice(Version));
					priceTypeMap.put("MOD", new MODPrice(Version));
					priceTypeMap.put("FEA", new FEAPrice(Version));
					priceTypeMap.put("FUP", new FUPPrice(Version));
					priceTypeMap.put("MUP", new MUPPrice(Version));
					priceTypeMap.put("RPQ", new RPQPrice(Version));
					priceTypeMap.put("SWF", new SWFPrice(Version));
					priceTypeMap.put("WSF", new WSFPrice(Version));
					priceTypeMap.put("TFU", new TFUPrice(Version));
					priceTypeMap.put("TMU", new TMUPrice(Version));
					priceTypeMap.put("VAR", new VARPrice(Version));
					priceTypeMap.put("SBB", new IgnoredPrice(Version));
					priceTypeMap.put("GRD", new GRDPrice(Version));
					priceTypeMap.put("QTY", new QTYPrice(Version));
					priceTypeMap.put("VAL", new VALPrice(Version));
					priceTypeMap.put("GRO", new GROPrice(Version));
					priceTypeMap.put("VAO", new VAOPrice(Version));
					priceTypeMap.put("QTO", new QTOPrice(Version));
					
				}else{
					types = priceElement.getElementsByTagName("pricepointtype");
					priceTypeMap.put("NoType", new NoTypePrice());
					priceTypeMap.put("MOD", new MODPrice());
					priceTypeMap.put("FEA", new FEAPrice());
					priceTypeMap.put("FUP", new FUPPrice());
					priceTypeMap.put("MUP", new MUPPrice());
					priceTypeMap.put("RPQ", new RPQPrice());
					priceTypeMap.put("SWF", new SWFPrice());
					priceTypeMap.put("WSF", new WSFPrice());
					priceTypeMap.put("TFU", new TFUPrice());
					priceTypeMap.put("TMU", new TMUPrice());
					priceTypeMap.put("VAR", new VARPrice());
					priceTypeMap.put("SBB", new IgnoredPrice());
				}				
				if (types.getLength() > 0) {
					type = types.item(0).getTextContent();
					if("".equals(type)){
						type ="NoType";
					}
				} else {
					type = "NoType";
				}
				PriceTable price = priceTypeMap.get(type);
				if (price != null) {
					price.setValues(this, priceElement);
					return price;
				} else {
					throw new IllegalStateException("No prices handler found for pricepointype: "+type);
				}
			}
		}
		throw new IllegalStateException("No more prices in list");
		
		
//		if (priceIndex < priceList.getLength()) {
//			Node item = priceList.item(priceIndex);
//			priceIndex++;
//			if (item.getNodeType() == Node.ELEMENT_NODE
//					&& item.getNodeName().equals("price")) {
//				Element priceElement = (Element) item;
//				NodeList types = priceElement
//						.getElementsByTagName("pricepointtype");
//				String type = null;
//				if (types.getLength() > 0) {
//					type = types.item(0).getTextContent();
//				} else {
//					type = "NoType";
//				}
//				PriceTable price = priceTypeMap.get(type);
//				if (price != null) {
//					price.setValues(this, priceElement);
//					return price;
//				} else {
//					throw new IllegalStateException("No prices handler found for pricepointype: "+type);
//				}
//			}
//		}
//		throw new IllegalStateException("No more prices in list");
	}
}
