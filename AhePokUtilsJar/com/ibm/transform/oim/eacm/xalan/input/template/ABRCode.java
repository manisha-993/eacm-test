package com.ibm.transform.oim.eacm.xalan.input.template;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <ABRCode>
*/
public class ABRCode extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public ABRCode()
  {
  }
  
  public void setClassName(String className)
  {
    setAttributeValue("className", className);
  }
  
  public String getClassName()
  {
    return getAttributeValue("className");
  }
  
  public boolean removeClassName()
  {
    return removeAttribute("className");
  }
  
  public void setRevision(String revision)
  {
    setAttributeValue("revision", revision);
  }
  
  public String getRevision()
  {
    return getAttributeValue("revision");
  }
  
  public boolean removeRevision()
  {
    return removeAttribute("revision");
  }
  
}

