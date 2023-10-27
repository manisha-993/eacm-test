package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.poi.hssf.usermodel.HSSFCell;

public interface QSMABRInterface {
  public static final String STATUS_FINAL = "0020";
  
  public static final String DEFAULT_PUBFROM = "1980-01-01";
  
  public static final String DEFAULT_PUBTO = "9999-12-31";
  
  String getVeName();
  
  String getVersion();
  
  int getColumnCount();
  
  String getColumnLabel(int paramInt);
  
  String getFFColumnLabel(int paramInt);
  
  int getColumnWidth(int paramInt);
  
  boolean canGenerateReport(EntityItem paramEntityItem, QSMRPTABRSTATUS paramQSMRPTABRSTATUS);
  
  boolean canGenerateReport(EntityList paramEntityList, QSMRPTABRSTATUS paramQSMRPTABRSTATUS);
  
  boolean withinDateRange(EntityItem paramEntityItem, String paramString, QSMRPTABRSTATUS paramQSMRPTABRSTATUS);
  
  boolean isChanged(String paramString, Hashtable paramHashtable, int paramInt);
  
  void setColumnValue(HSSFCell paramHSSFCell, String paramString, Hashtable paramHashtable, int paramInt);
  
  String getColumnValue(String paramString, Hashtable paramHashtable, int paramInt);
  
  Vector getRowItems(EntityList paramEntityList, Hashtable paramHashtable, String paramString, QSMRPTABRSTATUS paramQSMRPTABRSTATUS);
  
  String getRowOne(EntityItem paramEntityItem);
  
  String getRowTwoPrefix();
}


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\QSMABRInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */