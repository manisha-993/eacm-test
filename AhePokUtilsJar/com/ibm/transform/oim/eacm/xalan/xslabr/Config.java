package com.ibm.transform.oim.eacm.xalan.xslabr;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <Config>
*/
public class Config extends ComplexType
{
	private static final long serialVersionUID = 1L;
  public Config()
  {
  }
  
  public void setEnterprise(int index, Enterprise Enterprise)
  {
    setElementValue(index, "Enterprise", Enterprise);
  }
  
  public Enterprise getEnterprise(int index)
  {
    return (Enterprise) getElementValue("Enterprise", "Enterprise", index);
  }
  
  public int getEnterpriseCount()
  {
    return sizeOfElement("Enterprise");
  }
  
  public boolean removeEnterprise(int index)
  {
    return removeElement(index, "Enterprise");
  }
  
}

