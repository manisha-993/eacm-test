package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.WorkflowException;
import COM.ibm.opicmpdh.middleware.LockException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import org.w3c.dom.Element;

public interface LXABRInterface {
  void validateData(LXABRSTATUS paramLXABRSTATUS, Element paramElement) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException;
  
  void execute() throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException, LockException, WorkflowException;
  
  void dereference();
  
  String getVersion();
  
  String getTitle();
  
  String getHeader();
  
  String getDescription();
}


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\LXABRInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */