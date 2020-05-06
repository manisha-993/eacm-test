package com.ibm.transform.oim.eacm.xalan.xslabr;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <Enterprise>
*/
public class Enterprise extends ComplexType
{
	private static final long serialVersionUID = 1L;
  public Enterprise()
  {
  }
  
  public void setConnection(int index, Connection Connection)
  {
    setElementValue(index, "Connection", Connection);
  }
  
  public Connection getConnection(int index)
  {
    return (Connection) getElementValue("Connection", "Connection", index);
  }
  
  public int getConnectionCount()
  {
    return sizeOfElement("Connection");
  }
  
  public boolean removeConnection(int index)
  {
    return removeElement(index, "Connection");
  }
  
  public void setEntity(int index, Entity Entity)
  {
    setElementValue(index, "Entity", Entity);
  }
  
  public Entity getEntity(int index)
  {
    return (Entity) getElementValue("Entity", "Entity", index);
  }
  
  public int getEntityCount()
  {
    return sizeOfElement("Entity");
  }
  
  public boolean removeEntity(int index)
  {
    return removeElement(index, "Entity");
  }
  
  public void setCode(String code)
  {
    setAttributeValue("code", code);
  }
  
  public String getCode()
  {
    return getAttributeValue("code");
  }
  
  public boolean removeCode()
  {
    return removeAttribute("code");
  }
  
}

