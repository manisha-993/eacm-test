/*    */ package COM.ibm.eannounce.abr.sg.rfc;
/*    */ 
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ public class StringNullAdapter
/*    */   extends TypeAdapter<String>
/*    */ {
/*    */   public String read(JsonReader paramJsonReader) throws IOException {
/* 14 */     if (paramJsonReader.peek() == JsonToken.NULL) {
/* 15 */       paramJsonReader.nextNull();
/* 16 */       return "";
/*    */     } 
/* 18 */     return paramJsonReader.nextString();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(JsonWriter paramJsonWriter, String paramString) throws IOException {
/* 23 */     if (paramString == null) {
/* 24 */       paramJsonWriter.nullValue();
/*    */       return;
/*    */     } 
/* 27 */     paramJsonWriter.value(paramString);
/*    */   }
/*    */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\rfc\StringNullAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */