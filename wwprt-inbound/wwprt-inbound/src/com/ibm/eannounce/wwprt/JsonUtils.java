package com.ibm.eannounce.wwprt;

import com.ibm.cloud.cloudant.v1.model.Document;
import com.ibm.eannounce.wwprt.model.Price;
import com.ibm.eannounce.wwprt.model.PriceXml;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;

public class JsonUtils {

    public static final String PRICE_ROOT="wwprttxn";
    public static final String PRICE_ELEMENT="price";
    static ThreadLocal<Document> localDoc = new ThreadLocal<>();
    public static void main(String[] args) {
        // JSON string to be converted

            JSONObject json = new JSONObject("{\"root\":{\"child1\":{\"@attribute\":\"value\",\"content\":\"child1 content\"},\"child2\":{\"@attribute\":\"value\",\"content\":\"child2 content\"}}}");

        System.out.println(json);
        int indent = 4; // 设置缩进值
            String xml = XML.toString(XML.toJSONObject(json.toString()), indent);
            System.out.println(xml);

    }

    private static String addAttributesToXml(String xmlString) {
        // Add attribute to the root element
        String rootElementName = "person";
        String rootAttributeKey = "id";
        String rootAttributeValue = "123";

        xmlString = xmlString.replaceFirst("<" + rootElementName + ">", "<" + rootElementName + " " + rootAttributeKey + "=\"" + rootAttributeValue + "\">");

        // Add attribute to the child element
        String childElementName = "name";
        String childAttributeKey = "type";
        String childAttributeValue = "first";

        xmlString = xmlString.replaceFirst("<" + childElementName + ">", "<" + childElementName + " " + childAttributeKey + "=\"" + childAttributeValue + "\">");

        return xmlString;
    }

    public static String Json2Xml(List<Document> list,String id) throws JAXBException {
        PriceXml priceXml = new PriceXml();
        priceXml.setType("price");
        priceXml.setId(id);
        priceXml.setSchemaLocation("/web/server_root/data/finance/tools/wwprt/ATS/PriceXML/xsdAdapter_123/price.xsd");
        JAXBContext context = JAXBContext.newInstance(PriceXml.class);
        // Create a Marshaller instance
        Marshaller marshaller = context.createMarshaller();
        // Set properties for the Marshaller
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        // Create a StringWriter object to write the XML to
        StringWriter writer = new StringWriter();
        for (Document doc:list
        ) {
            localDoc.set(doc);
            Price p = new Price();
            p.setOfferingname(getString("offeringname"));
            p.setOfferingtype(getString("offeringtype"));
            p.setStartdate(getString("startdate"));
            p.setCurrency(getString("currency"));
            p.setCabletype(getString("cabletype"));
            p.setCableid(getString("cableid"));
            p.setReleasets(getString("releasets"));
            p.setPricevalue(getString("pricevalue"));
            p.setVarianttype(getString("varianttype"));
            p.setVariantname(getString("variantname"));
            p.setCountry(getString("country"));
            p.setPricetype(getString("pricetype"));
            p.setOnshore(getString("onshore"));
            p.setEnddate(getString("enddate"));
            p.setPricevalueusd(getString("pricevalueusd"));
            p.setFactor(getString("factor"));
            localDoc.remove();
            priceXml.add(p);
        }
        marshaller.marshal(priceXml, writer);
        return writer.toString();
    }
    public static JSONObject getJsonObject(List<Document> list){

        JSONArray jsonArray = new JSONArray();
        for (Document doc:list
             ) {
            localDoc.set(doc);
            Price p = new Price();
            p.setOfferingname(getString("offeringname"));
            p.setOfferingtype(getString("offeringtype"));
            p.setStartdate(getString("startdate"));
            p.setCurrency(getString("currency"));
            p.setCabletype(getString("cabletype"));
            p.setCableid(getString("cableid"));
            p.setReleasets(getString("releasets"));
            p.setPricevalue(getString("pricevalue"));
            p.setVarianttype(getString("varianttype"));
            p.setVariantname(getString("variantname"));
            p.setCountry(getString("country"));
            p.setPricetype(getString("pricetype"));
            String onshore=  getString("onshore");
            if("Y".equals(onshore)){
                p.setOnshore("true");
            }else {
                p.setOnshore("false");
            }
            p.setEnddate(getString("enddate"));
            p.setPricevalueusd(getString("pricevalueusd"));
            p.setFactor(getString("factor"));
            localDoc.remove();
            jsonArray.put(JSONObject.wrap(p));
        }

        JSONObject object = new JSONObject();
        object.put(PRICE_ELEMENT,jsonArray);
        return object;
    }
    public static String getString(String key){
        Document doc = localDoc.get();
        if(doc.get(key)!=null){
            return doc.get(key).toString();
        }
        return "";
    }
    private static String addAttributesToXml(String xmlString,String tag,String key,String value) {
        xmlString = xmlString.replaceFirst("<" + tag , "<" + tag + " " + key + "=\"" + value + "\"");

        return xmlString;
    }
}