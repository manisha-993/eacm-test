package com.ibm.transform.oim.eacm.xalan.input.template;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <Language>
*/
public class Language extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public Language()
  {
  }
  
  public void setRead(Read Read)
  {
    setElementValue("Read", Read);
  }
  
  public Read getRead()
  {
    return (Read) getElementValue("Read", "Read");
  }
  
  public boolean removeRead()
  {
    return removeElement("Read");
  }
  
  public void setWrite(Write Write)
  {
    setElementValue("Write", Write);
  }
  
  public Write getWrite()
  {
    return (Write) getElementValue("Write", "Write");
  }
  
  public boolean removeWrite()
  {
    return removeElement("Write");
  }
  
}

