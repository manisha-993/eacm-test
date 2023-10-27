/*     */ package COM.ibm.eannounce.abr.sg.rfc;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.internal.LinkedTreeMap;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.ParameterizedType;
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
/*     */ public class RdhClassDefaultValuesUtility
/*     */ {
/*  28 */   private static final String json = readJsonFile();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDefaultValues(RdhBase paramRdhBase, String paramString) throws Exception {
/*  39 */     Gson gson = (new GsonBuilder()).create();
/*     */     
/*  41 */     LinkedTreeMap<String, LinkedTreeMap<String, String>> linkedTreeMap = findDefaultValues(paramRdhBase.getClass()
/*  42 */         .getSimpleName(), paramString);
/*  43 */     HashMap<String, Field> hashMap = getAllDeclaredFeilds(paramRdhBase.getClass());
/*     */     
/*  45 */     for (String str : hashMap.keySet()) {
/*     */       
/*  47 */       Field field = hashMap.get(str);
/*  48 */       field.setAccessible(true);
/*     */       
/*  50 */       if (linkedTreeMap.containsKey(str)) {
/*     */         
/*  52 */         LinkedTreeMap linkedTreeMap1 = (LinkedTreeMap)linkedTreeMap.get(str);
/*     */         
/*  54 */         ArrayList<Object> arrayList = new ArrayList();
/*     */         
/*     */         try {
/*  57 */           if (field.getGenericType() instanceof ParameterizedType)
/*     */           {
/*  59 */             ParameterizedType parameterizedType = (ParameterizedType)field.getGenericType();
/*  60 */             Class clazz = (Class)parameterizedType.getActualTypeArguments()[0];
/*  61 */             LinkedTreeMap linkedTreeMap2 = new LinkedTreeMap();
/*  62 */             for (String str1 : linkedTreeMap1.keySet())
/*     */             {
/*  64 */               linkedTreeMap2.put(str1.toUpperCase(), linkedTreeMap1.get(str1));
/*     */             }
/*     */             
/*  67 */             Object object = gson.fromJson(gson.toJson(linkedTreeMap2), clazz);
/*     */             
/*  69 */             arrayList.add(object);
/*  70 */             field.set(paramRdhBase, arrayList);
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*  76 */         catch (IllegalArgumentException illegalArgumentException) {
/*     */           
/*  78 */           illegalArgumentException.printStackTrace();
/*  79 */         } catch (IllegalAccessException illegalAccessException) {
/*     */           
/*  81 */           illegalAccessException.printStackTrace();
/*     */         } 
/*     */         continue;
/*     */       } 
/*  85 */       if (field.getGenericType() instanceof ParameterizedType) {
/*     */         
/*  87 */         ArrayList arrayList = new ArrayList();
/*  88 */         ParameterizedType parameterizedType = (ParameterizedType)field.getGenericType();
/*  89 */         Class clazz = (Class)parameterizedType.getActualTypeArguments()[0];
/*     */         
/*  91 */         arrayList.add(clazz.newInstance());
/*  92 */         field.set(paramRdhBase, arrayList);
/*     */       } 
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
/*     */   public static String getSpecificJson(String paramString1, String paramString2, String paramString3) throws Exception {
/* 144 */     String str = "{}";
/* 145 */     Gson gson = (new GsonBuilder()).create();
/*     */     
/* 147 */     LinkedTreeMap<String, LinkedTreeMap<String, String>> linkedTreeMap = findDefaultValues(paramString1, paramString2);
/*     */     
/* 149 */     if (linkedTreeMap.containsKey(paramString3)) {
/*     */       
/* 151 */       LinkedTreeMap linkedTreeMap1 = (LinkedTreeMap)linkedTreeMap.get(paramString3);
/* 152 */       LinkedTreeMap linkedTreeMap2 = new LinkedTreeMap();
/* 153 */       for (String str1 : linkedTreeMap1.keySet())
/*     */       {
/* 155 */         linkedTreeMap2.put(str1.toUpperCase(), linkedTreeMap1.get(str1));
/*     */       }
/* 157 */       str = gson.toJson(linkedTreeMap2);
/*     */     } 
/*     */     
/* 160 */     return str;
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
/*     */   public static <T> T getSpecificObject(String paramString1, String paramString2, String paramString3, Class<T> paramClass) throws Exception {
/* 175 */     Object object = null;
/* 176 */     Gson gson = (new GsonBuilder()).create();
/*     */     
/* 178 */     String str = getSpecificJson(paramString1, paramString2, paramString3);
/* 179 */     if (str != null)
/*     */     {
/* 181 */       object = gson.fromJson(str, paramClass);
/*     */     }
/* 183 */     return (T)object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String readJsonFile() {
/* 194 */     StringBuffer stringBuffer = new StringBuffer();
/*     */     
/*     */     try {
/* 197 */       File file = null;
/* 198 */       String str1 = ConfigUtils.getProperty("rdh.class.default.values.json");
/*     */       
/* 200 */       if (str1 == null) {
/*     */         
/* 202 */         file = new File("./RdhJavaClassDefaultValues.json");
/*     */       } else {
/*     */         
/* 205 */         file = new File(ConfigUtils.getProperty("rdh.class.default.values.json"));
/*     */       } 
/* 207 */       BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
/* 208 */       String str2 = null;
/* 209 */       while ((str2 = bufferedReader.readLine()) != null)
/*     */       {
/* 211 */         stringBuffer.append(str2);
/*     */       }
/* 213 */     } catch (FileNotFoundException fileNotFoundException) {
/*     */       
/* 215 */       fileNotFoundException.printStackTrace();
/* 216 */     } catch (IOException iOException) {
/*     */       
/* 218 */       iOException.printStackTrace();
/*     */     } 
/* 220 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static LinkedTreeMap<String, LinkedTreeMap<String, String>> findDefaultValues(String paramString1, String paramString2) throws Exception {
/* 228 */     LinkedTreeMap<String, LinkedTreeMap<String, String>> linkedTreeMap = null;
/*     */     
/* 230 */     Gson gson = (new GsonBuilder()).create();
/* 231 */     HashMap hashMap = (HashMap)gson.fromJson(json, HashMap.class);
/*     */     
/* 233 */     List list = (List)hashMap.get("RdhJavaClassDefaultValues");
/*     */     
/* 235 */     if (list == null || list.size() < 1)
/*     */     {
/* 237 */       throw new Exception("Element 'RdhJavaClassDefaultValues' does not exist in Json file");
/*     */     }
/*     */     
/* 240 */     for (LinkedTreeMap linkedTreeMap1 : list) {
/*     */       
/* 242 */       if (linkedTreeMap1.get("rdhClassName") != null && linkedTreeMap1.get("rdhClassName").equals(paramString1)) {
/*     */         
/* 244 */         List list1 = (List)linkedTreeMap1.get("types");
/*     */         
/* 246 */         if (list1 == null || list1.size() < 1)
/*     */         {
/* 248 */           throw new Exception("Element 'types' does not exist in Json file");
/*     */         }
/*     */         
/* 251 */         for (LinkedTreeMap linkedTreeMap2 : list1) {
/*     */           
/* 253 */           if (linkedTreeMap2.get("key") != null && linkedTreeMap2.get("key").equals(paramString2))
/*     */           {
/* 255 */             linkedTreeMap = (LinkedTreeMap)linkedTreeMap2.get("values");
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 260 */     if (linkedTreeMap == null || linkedTreeMap.size() < 1)
/*     */     {
/* 262 */       throw new Exception("Cannot find default value for class " + paramString1 + " compType: " + paramString2);
/*     */     }
/* 264 */     return linkedTreeMap;
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
/*     */   private static HashMap<String, Field> getAllDeclaredFeilds(Class<?> paramClass) {
/* 276 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 281 */       Field[] arrayOfField = paramClass.getDeclaredFields();
/* 282 */       for (Field field : arrayOfField)
/*     */       {
/* 284 */         hashMap.put(field.getName(), field);
/*     */       }
/* 286 */     } catch (Exception exception) {
/*     */       
/* 288 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 291 */     return (HashMap)hashMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\RdhClassDefaultValuesUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */