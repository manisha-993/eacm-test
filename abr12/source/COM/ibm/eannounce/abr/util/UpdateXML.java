/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import java.util.LinkedList;
/*     */ import java.util.Vector;
/*     */ import javax.xml.transform.TransformerFactoryConfigurationError;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UpdateXML
/*     */ {
/*     */   public LinkedList store(Document paramDocument) {
/*  26 */     LinkedList<WarrElem> linkedList = new LinkedList();
/*     */     
/*     */     try {
/*  29 */       Element element = paramDocument.getDocumentElement();
/*     */       
/*  31 */       NodeList nodeList = element.getElementsByTagName("WARRELEMENT");
/*     */       
/*  33 */       for (byte b = 0; b < nodeList.getLength(); b++) {
/*  34 */         Element element1 = (Element)nodeList.item(b);
/*  35 */         boolean bool = false;
/*  36 */         WarrElem warrElem = new WarrElem();
/*     */         
/*  38 */         Element element2 = (Element)element1.getElementsByTagName("WARRACTION").item(0);
/*  39 */         warrElem.setAction(element2.getTextContent());
/*     */         
/*  41 */         Element element3 = (Element)element1.getElementsByTagName("WARRENTITYTYPE").item(0);
/*  42 */         warrElem.setEntityType(element3.getTextContent());
/*     */         
/*  44 */         Element element4 = (Element)element1.getElementsByTagName("WARRENTITYID").item(0);
/*  45 */         warrElem.setEntityId(element4.getTextContent());
/*     */         
/*  47 */         Element element5 = (Element)element1.getElementsByTagName("WARRID").item(0);
/*  48 */         warrElem.setWarrid(element5.getTextContent());
/*     */         
/*  50 */         Element element6 = (Element)element1.getElementsByTagName("WARRPRIOD").item(0);
/*  51 */         warrElem.setWarrpriod(element6.getTextContent());
/*     */         
/*  53 */         Element element7 = (Element)element1.getElementsByTagName("WARRDESC").item(0);
/*  54 */         warrElem.setWarrdesc(element7.getTextContent());
/*     */         
/*  56 */         Element element8 = (Element)element1.getElementsByTagName("PUBFROM").item(0);
/*  57 */         warrElem.setPubFrom(element8.getTextContent());
/*     */         
/*  59 */         Element element9 = (Element)element1.getElementsByTagName("PUBTO").item(0);
/*  60 */         warrElem.setPubTo(element9.getTextContent());
/*     */         
/*  62 */         Element element10 = (Element)element1.getElementsByTagName("DEFWARR").item(0);
/*  63 */         if ("Yes".equals(element10.getTextContent())) {
/*  64 */           bool = true;
/*     */         }
/*  66 */         warrElem.setDefwarr(element10.getTextContent());
/*     */         
/*  68 */         NodeList nodeList1 = element1.getElementsByTagName("COUNTRYELEMENT");
/*     */         
/*  70 */         Vector<Ctry> vector = new Vector();
/*     */         
/*  72 */         for (byte b1 = 0; b1 < nodeList1.getLength(); b1++) {
/*  73 */           Ctry ctry = new Ctry();
/*  74 */           Element element11 = (Element)nodeList1.item(0);
/*  75 */           Element element12 = (Element)element1.getElementsByTagName("COUNTRYACTION").item(b1);
/*  76 */           ctry.setAction(element12.getTextContent());
/*  77 */           Element element13 = (Element)element1.getElementsByTagName("COUNTRY_FC").item(b1);
/*  78 */           ctry.setCtry(element13.getTextContent());
/*     */           
/*  80 */           vector.add(ctry);
/*     */         } 
/*     */ 
/*     */         
/*  84 */         warrElem.setCountryList(vector);
/*     */         
/*  86 */         if (bool) {
/*  87 */           linkedList.addLast(warrElem);
/*     */         } else {
/*  89 */           linkedList.addFirst(warrElem);
/*     */         }
/*     */       
/*     */       } 
/*  93 */     } catch (TransformerFactoryConfigurationError transformerFactoryConfigurationError) {
/*     */       
/*  95 */       transformerFactoryConfigurationError.printStackTrace();
/*     */     } 
/*     */     
/*  98 */     return linkedList;
/*     */   }
/*     */ 
/*     */   
/*     */   public Document update(Document paramDocument, LinkedList<WarrElem> paramLinkedList) {
/* 103 */     Object object = null;
/*     */     
/*     */     try {
/* 106 */       Element element = paramDocument.getDocumentElement();
/*     */       
/* 108 */       NodeList nodeList = element.getElementsByTagName("WARRELEMENT");
/*     */       
/* 110 */       for (byte b = 0; b < nodeList.getLength(); b++) {
/* 111 */         Element element1 = (Element)nodeList.item(b);
/*     */         
/* 113 */         WarrElem warrElem = paramLinkedList.get(b);
/*     */         
/* 115 */         Element element2 = (Element)element1.getElementsByTagName("WARRACTION").item(0);
/* 116 */         element2.setTextContent(warrElem.getAction());
/*     */         
/* 118 */         Element element3 = (Element)element1.getElementsByTagName("WARRENTITYTYPE").item(0);
/* 119 */         element3.setTextContent(warrElem.getEntityType());
/*     */         
/* 121 */         Element element4 = (Element)element1.getElementsByTagName("WARRENTITYID").item(0);
/* 122 */         element4.setTextContent(warrElem.getEntityId());
/*     */         
/* 124 */         Element element5 = (Element)element1.getElementsByTagName("WARRID").item(0);
/* 125 */         element5.setTextContent(warrElem.getWarrid());
/*     */         
/* 127 */         Element element6 = (Element)element1.getElementsByTagName("WARRPRIOD").item(0);
/* 128 */         element6.setTextContent(warrElem.getWarrpriod());
/*     */         
/* 130 */         Element element7 = (Element)element1.getElementsByTagName("WARRDESC").item(0);
/* 131 */         element7.setTextContent(warrElem.getWarrdesc());
/*     */         
/* 133 */         Element element8 = (Element)element1.getElementsByTagName("PUBFROM").item(0);
/* 134 */         element8.setTextContent(warrElem.getPubFrom());
/*     */         
/* 136 */         Element element9 = (Element)element1.getElementsByTagName("PUBTO").item(0);
/* 137 */         element9.setTextContent(warrElem.getPubTo());
/*     */         
/* 139 */         Element element10 = (Element)element1.getElementsByTagName("DEFWARR").item(0);
/* 140 */         element10.setTextContent(warrElem.getDefwarr());
/*     */         
/* 142 */         Node node = element1.getElementsByTagName("COUNTRYLIST").item(0);
/* 143 */         node.getParentNode().removeChild(node);
/*     */         
/* 145 */         Vector<Ctry> vector = warrElem.getCountryList();
/*     */         
/* 147 */         Element element11 = paramDocument.createElement("COUNTRYLIST");
/*     */         
/* 149 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 150 */           Element element12 = paramDocument.createElement("COUNTRYELEMENT");
/* 151 */           Ctry ctry = vector.get(b1);
/* 152 */           Element element13 = paramDocument.createElement("COUNTRYACTION");
/* 153 */           element13.setTextContent(ctry.getAction());
/* 154 */           Element element14 = paramDocument.createElement("COUNTRY_FC");
/* 155 */           element14.setTextContent(ctry.getCtry());
/* 156 */           element12.appendChild(element13);
/* 157 */           element12.appendChild(element14);
/*     */           
/* 159 */           element11.appendChild(element12);
/*     */         } 
/* 161 */         element1.appendChild(element11);
/*     */       }
/*     */     
/*     */     }
/* 165 */     catch (TransformerFactoryConfigurationError transformerFactoryConfigurationError) {
/*     */       
/* 167 */       transformerFactoryConfigurationError.printStackTrace();
/*     */     } 
/*     */     
/* 170 */     return paramDocument;
/*     */   }
/*     */   
/*     */   private static class WarrElem
/*     */   {
/*     */     public static final String CHEAT = "@@";
/* 176 */     String action = "@@";
/* 177 */     String entityType = "@@";
/* 178 */     String entityId = "@@";
/* 179 */     String warrid = "@@";
/* 180 */     String Warrpriod = "@@";
/* 181 */     String warrdesc = "@@";
/* 182 */     String pubFrom = "@@";
/* 183 */     String pubTo = "@@";
/* 184 */     String defwarr = "@@";
/* 185 */     Vector countryList = new Vector();
/*     */     
/*     */     public String getAction() {
/* 188 */       return this.action;
/*     */     }
/*     */     public void setAction(String param1String) {
/* 191 */       this.action = param1String;
/*     */     }
/*     */     public String getEntityType() {
/* 194 */       return this.entityType;
/*     */     }
/*     */     public void setEntityType(String param1String) {
/* 197 */       this.entityType = param1String;
/*     */     }
/*     */     public String getEntityId() {
/* 200 */       return this.entityId;
/*     */     }
/*     */     public void setEntityId(String param1String) {
/* 203 */       this.entityId = param1String;
/*     */     }
/*     */     public String getWarrid() {
/* 206 */       return this.warrid;
/*     */     }
/*     */     public void setWarrid(String param1String) {
/* 209 */       this.warrid = param1String;
/*     */     }
/*     */     public String getWarrpriod() {
/* 212 */       return this.Warrpriod;
/*     */     }
/*     */     public void setWarrpriod(String param1String) {
/* 215 */       this.Warrpriod = param1String;
/*     */     }
/*     */     public String getWarrdesc() {
/* 218 */       return this.warrdesc;
/*     */     }
/*     */     public void setWarrdesc(String param1String) {
/* 221 */       this.warrdesc = param1String;
/*     */     }
/*     */     public String getPubFrom() {
/* 224 */       return this.pubFrom;
/*     */     }
/*     */     public void setPubFrom(String param1String) {
/* 227 */       this.pubFrom = param1String;
/*     */     }
/*     */     public String getPubTo() {
/* 230 */       return this.pubTo;
/*     */     }
/*     */     public void setPubTo(String param1String) {
/* 233 */       this.pubTo = param1String;
/*     */     }
/*     */     public String getDefwarr() {
/* 236 */       return this.defwarr;
/*     */     }
/*     */     public void setDefwarr(String param1String) {
/* 239 */       this.defwarr = param1String;
/*     */     }
/*     */     public Vector getCountryList() {
/* 242 */       return this.countryList;
/*     */     }
/*     */     public void setCountryList(Vector param1Vector) {
/* 245 */       this.countryList = param1Vector;
/*     */     }
/*     */     
/*     */     private WarrElem() {} }
/*     */   
/*     */   private static class Ctry {
/*     */     public static final String CHEAT = "@@";
/* 252 */     String action = "@@";
/* 253 */     String ctry = "@@";
/*     */     
/*     */     public String getAction() {
/* 256 */       return this.action;
/*     */     }
/*     */     public void setAction(String param1String) {
/* 259 */       this.action = param1String;
/*     */     }
/*     */     public String getCtry() {
/* 262 */       return this.ctry;
/*     */     }
/*     */     public void setCtry(String param1String) {
/* 265 */       this.ctry = param1String;
/*     */     }
/*     */     
/*     */     private Ctry() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\UpdateXML.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */