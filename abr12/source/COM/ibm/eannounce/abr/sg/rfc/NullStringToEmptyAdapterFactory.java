/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.google.gson.reflect.TypeToken;
/*    */ 
/*    */ public class NullStringToEmptyAdapterFactory<T>
/*    */   implements TypeAdapterFactory {
/*    */   public <T> TypeAdapter<T> create(Gson paramGson, TypeToken<T> paramTypeToken) {
/* 11 */     Class<String> clazz = paramTypeToken.getRawType();
/* 12 */     if (clazz != String.class) {
/* 13 */       return null;
/*    */     }
/* 15 */     return new StringNullAdapter();
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\NullStringToEmptyAdapterFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */