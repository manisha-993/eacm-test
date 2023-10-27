/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.OutputData;
/*     */ import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBase_zdm_geo_to_class;
/*     */ import COM.ibm.eannounce.abr.util.EACMWebServiceUtil;
/*     */ import COM.ibm.eannounce.abr.util.RfcConfigProperties;
/*     */ import com.google.gson.ExclusionStrategy;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
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
/*     */ public abstract class RdhBase
/*     */ {
/*     */   @SerializedName("PIMS_IDENTITY")
/*     */   protected String pims_identity;
/*     */   @SerializedName("RFA_NUM")
/*     */   protected String rfa_num;
/*     */   @SerializedName("DEFAULT_MANDT")
/*     */   protected String default_mandt;
/*     */   @SerializedName("CLIENT_NAME")
/*     */   private String client_name;
/*     */   @Foo
/*     */   private String rfc_name;
/*     */   @Foo
/*     */   protected RdhBase_zdm_geo_to_class zdm_geo_to_class;
/*     */   @Foo
/*  42 */   private String input = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SerializedName("N45BZDM_GEO_TO_CLASS")
/*     */   private List<RdhBase_zdm_geo_to_class> geo_to_class_list;
/*     */ 
/*     */ 
/*     */   
/*     */   @Foo
/*     */   private int rfcrc;
/*     */ 
/*     */ 
/*     */   
/*     */   @Foo
/*     */   private String error_text;
/*     */ 
/*     */ 
/*     */   
/*     */   private HashMap<String, List<HashMap<String, String>>> RETURN_MULTIPLE_OBJ;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RdhBase(String paramString1, String paramString2, String paramString3) {
/*  68 */     this.pims_identity = "T";
/*  69 */     this.rfa_num = paramString1;
/*  70 */     this.rfc_name = paramString2;
/*  71 */     this.default_mandt = RfcConfigProperties.getPropertys("rdh.service.default.Mandt");
/*  72 */     this.client_name = RfcConfigProperties.getPropertys("rdh.service.client.name");
/*  73 */     this.zdm_geo_to_class = new RdhBase_zdm_geo_to_class("WW");
/*  74 */     this.geo_to_class_list = new ArrayList<>();
/*  75 */     this.geo_to_class_list.add(this.zdm_geo_to_class);
/*  76 */     setDefaultValues();
/*     */   }
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
/*     */   protected void setDefaultValues() {}
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
/*     */   protected abstract boolean isReadyToExecute();
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
/*     */   public void execute() throws Exception {
/* 113 */     if (isReadyToExecute()) {
/*     */ 
/*     */       
/*     */       try {
/* 117 */         this.input = generateJson();
/* 118 */         GsonBuilder gsonBuilder = new GsonBuilder();
/* 119 */         Gson gson = gsonBuilder.disableHtmlEscaping().create();
/*     */         
/* 121 */         OutputData outputData = (OutputData)gson.fromJson(EACMWebServiceUtil.callService(this.input, this.rfc_name), OutputData.class);
/* 122 */         saveRfcResults(outputData);
/* 123 */         if (!isValidRfcrc()) {
/* 124 */           throw new Exception(this.error_text);
/*     */         }
/* 126 */       } catch (Exception exception) {
/*     */         
/* 128 */         this.rfcrc = 8;
/* 129 */         this.error_text = "Something wrong when calling webservice: " + this.rfc_name + " - " + exception.getMessage();
/* 130 */         throw new Exception(createLogEntry());
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 139 */       throw new Exception(createLogEntry());
/*     */     } 
/*     */   }
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
/*     */   public String generateJson() throws Exception {
/* 153 */     GsonBuilder gsonBuilder = new GsonBuilder();
/* 154 */     Gson gson = gsonBuilder.setExclusionStrategies(new ExclusionStrategy[] { new RdhExclusionStrategy() }).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).disableHtmlEscaping().create();
/*     */     
/* 156 */     return gson.toJson(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveRfcResults(OutputData paramOutputData) {
/* 166 */     this.rfcrc = paramOutputData.getRFCRC();
/* 167 */     this.error_text = paramOutputData.getERROR_TEXT();
/* 168 */     this.RETURN_MULTIPLE_OBJ = paramOutputData.getRETURN_MULTIPLE_OBJ();
/*     */   }
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
/*     */   public void setDeleteAction() {}
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
/*     */   public String createLogEntry() {
/* 197 */     String str = "";
/*     */     try {
/* 199 */       str = generateJson();
/* 200 */     } catch (Exception exception) {
/* 201 */       this.rfcrc = 8;
/* 202 */       this.error_text = "Something wrong when generated Json: " + exception.getMessage();
/*     */     } 
/* 204 */     return "RFC Result: " + (
/* 205 */       isValidRfcrc() ? "Success" : ("FAILED Reason: " + this.error_text)) + " " + 
/* 206 */       System.getProperty("line.separator") + " Remote function " + this.rfc_name + " : " + 
/* 207 */       addBlankToJson(str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String addBlankToJson(String paramString) {
/* 215 */     return paramString.replaceAll("([0-9]+|\"|]|}),", "$1, ")
/* 216 */       .replace("\\u003d", "=").replace("\\u0027", "'")
/* 217 */       .replace("\\u003c", "<").replace("\\u003e", ">");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRfcrc() {
/* 226 */     return this.rfcrc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRfcrc(int paramInt) {
/* 235 */     this.rfcrc = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getError_text() {
/* 244 */     return this.error_text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setError_text(String paramString) {
/* 253 */     this.error_text = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isNullOrBlank(String paramString) {
/* 265 */     return (paramString == null || paramString.length() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidRfcrc() {
/* 273 */     return (this.rfcrc == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkFieldsNotEmplyOrNull(Object paramObject, List<String> paramList, boolean paramBoolean) {
/*     */     try {
/* 286 */       Field[] arrayOfField = paramObject.getClass().getDeclaredFields();
/* 287 */       for (Field field : arrayOfField) {
/*     */         
/* 289 */         if (paramList.contains(field.getName())) {
/* 290 */           field.setAccessible(true);
/* 291 */           Object object = field.get(paramObject);
/* 292 */           if (object == null || object.toString().length() == 0) {
/*     */ 
/*     */             
/* 295 */             setRfcrc(8);
/* 296 */             String str = "The ";
/* 297 */             if (!paramBoolean)
/*     */             {
/* 299 */               str = "One ";
/*     */             }
/* 301 */             setError_text(str + paramObject.getClass().getSimpleName() + "." + field
/* 302 */                 .getName() + " attribute is not set to a value");
/*     */             
/* 304 */             return false;
/*     */           } 
/*     */         } 
/*     */       } 
/* 308 */     } catch (Exception exception) {
/* 309 */       setRfcrc(8);
/* 310 */       setError_text("something wrong when check Null or Empty : " + getClass().getName());
/* 311 */       return false;
/*     */     } 
/* 313 */     return true;
/*     */   }
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
/*     */   protected <T> boolean checkFieldsNotEmplyOrNullInCollection(List<T> paramList, List<String> paramList1) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   6: astore_3
/*     */     //   7: aload_3
/*     */     //   8: invokeinterface hasNext : ()Z
/*     */     //   13: ifeq -> 45
/*     */     //   16: aload_3
/*     */     //   17: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   22: astore #4
/*     */     //   24: aload_0
/*     */     //   25: aload #4
/*     */     //   27: aload_2
/*     */     //   28: iconst_0
/*     */     //   29: invokevirtual checkFieldsNotEmplyOrNull : (Ljava/lang/Object;Ljava/util/List;Z)Z
/*     */     //   32: istore #5
/*     */     //   34: iload #5
/*     */     //   36: ifne -> 42
/*     */     //   39: iload #5
/*     */     //   41: ireturn
/*     */     //   42: goto -> 7
/*     */     //   45: iconst_1
/*     */     //   46: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #325	-> 0
/*     */     //   #327	-> 24
/*     */     //   #328	-> 34
/*     */     //   #330	-> 39
/*     */     //   #332	-> 42
/*     */     //   #333	-> 45
/*     */   }
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
/*     */   protected <T> boolean checkFieldsNotEmplyOrNullInCollection(List<T> paramList, String paramString) {
/*     */     // Byte code:
/*     */     //   0: new java/util/ArrayList
/*     */     //   3: dup
/*     */     //   4: invokespecial <init> : ()V
/*     */     //   7: astore_3
/*     */     //   8: aload_3
/*     */     //   9: aload_2
/*     */     //   10: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   15: pop
/*     */     //   16: aload_1
/*     */     //   17: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   22: astore #4
/*     */     //   24: aload #4
/*     */     //   26: invokeinterface hasNext : ()Z
/*     */     //   31: ifeq -> 64
/*     */     //   34: aload #4
/*     */     //   36: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   41: astore #5
/*     */     //   43: aload_0
/*     */     //   44: aload #5
/*     */     //   46: aload_3
/*     */     //   47: iconst_0
/*     */     //   48: invokevirtual checkFieldsNotEmplyOrNull : (Ljava/lang/Object;Ljava/util/List;Z)Z
/*     */     //   51: istore #6
/*     */     //   53: iload #6
/*     */     //   55: ifne -> 61
/*     */     //   58: iload #6
/*     */     //   60: ireturn
/*     */     //   61: goto -> 24
/*     */     //   64: iconst_1
/*     */     //   65: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #345	-> 0
/*     */     //   #346	-> 8
/*     */     //   #347	-> 16
/*     */     //   #349	-> 43
/*     */     //   #350	-> 53
/*     */     //   #352	-> 58
/*     */     //   #354	-> 61
/*     */     //   #355	-> 64
/*     */   }
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
/*     */   public String getRFCName() {
/* 358 */     return this.rfc_name;
/*     */   }
/*     */   public String getRFCNum() {
/* 361 */     return this.rfa_num;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkFieldsNotEmplyOrNull(Object paramObject, String paramString) {
/* 371 */     ArrayList<String> arrayList = new ArrayList();
/* 372 */     arrayList.add(paramString);
/* 373 */     return checkFieldsNotEmplyOrNull(paramObject, arrayList, true);
/*     */   }
/*     */   public String getInput() {
/* 376 */     return this.input;
/*     */   }
/*     */   
/*     */   public HashMap<String, List<HashMap<String, String>>> getRETURN_MULTIPLE_OBJ() {
/* 380 */     return this.RETURN_MULTIPLE_OBJ;
/*     */   }
/*     */   
/*     */   public void setRETURN_MULTIPLE_OBJ(HashMap<String, List<HashMap<String, String>>> paramHashMap) {
/* 384 */     this.RETURN_MULTIPLE_OBJ = paramHashMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RdhBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */