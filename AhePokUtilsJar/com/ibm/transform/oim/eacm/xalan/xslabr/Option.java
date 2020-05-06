package com.ibm.transform.oim.eacm.xalan.xslabr;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <Option>
*/
public class Option extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public Option()
  {
  }
  
  public void setStyle(Style Style)
  {
    setElementValue("Style", Style);
  }
  
  public Style getStyle()
  {
    return (Style) getElementValue("Style", "Style");
  }
  
  public boolean removeStyle()
  {
    return removeElement("Style");
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

