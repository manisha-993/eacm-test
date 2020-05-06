package com.ibm.transform.oim.eacm.xalan.xslabr;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <Connection>
*/
public class Connection extends ComplexType
{
	private static final long serialVersionUID = 1L;
  public Connection()
  {
  }
  
  public void setUserID(String UserID)
  {
    setElementValue("UserID", UserID);
  }
  
  public String getUserID()
  {
    return getElementValue("UserID");
  }
  
  public boolean removeUserID()
  {
    return removeElement("UserID");
  }
  
  public void setPassword(String Password)
  {
    setElementValue("Password", Password);
  }
  
  public String getPassword()
  {
    return getElementValue("Password");
  }
  
  public boolean removePassword()
  {
    return removeElement("Password");
  }
  
  public void setDatabase(String Database)
  {
    setElementValue("Database", Database);
  }
  
  public String getDatabase()
  {
    return getElementValue("Database");
  }
  
  public boolean removeDatabase()
  {
    return removeElement("Database");
  }
  
  public void setDatabaseURL(String DatabaseURL)
  {
    setElementValue("DatabaseURL", DatabaseURL);
  }
  
  public String getDatabaseURL()
  {
    return getElementValue("DatabaseURL");
  }
  
  public boolean removeDatabaseURL()
  {
    return removeElement("DatabaseURL");
  }
  
  public void setDatabaseDriver(String DatabaseDriver)
  {
    setElementValue("DatabaseDriver", DatabaseDriver);
  }
  
  public String getDatabaseDriver()
  {
    return getElementValue("DatabaseDriver");
  }
  
  public boolean removeDatabaseDriver()
  {
    return removeElement("DatabaseDriver");
  }
  
  public void setId(String id)
  {
    setAttributeValue("id", id);
  }
  
  public String getId()
  {
    return getAttributeValue("id");
  }
  
  public boolean removeId()
  {
    return removeAttribute("id");
  }
  
}

