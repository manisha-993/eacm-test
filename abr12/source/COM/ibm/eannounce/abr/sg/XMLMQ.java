package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.abr.util.XMLElem;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.Vector;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public interface XMLMQ {
  Vector getMQPropertiesFN();
  
  XMLElem getXMLMap();
  
  boolean createXML(EntityItem paramEntityItem);
  
  String getVeName();
  
  String getRoleCode();
  
  String getStatusAttr();
  
  String getMQCID();
  
  void processThis(ADSABRSTATUS paramADSABRSTATUS, Profile paramProfile1, Profile paramProfile2, EntityItem paramEntityItem) throws SQLException, MiddlewareException, ParserConfigurationException, RemoteException, EANBusinessRuleException, MiddlewareShutdownInProgressException, IOException, TransformerException, MissingResourceException;
  
  String getVersion();
}


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\XMLMQ.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */