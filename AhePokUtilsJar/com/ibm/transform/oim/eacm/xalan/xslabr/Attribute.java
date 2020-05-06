package com.ibm.transform.oim.eacm.xalan.xslabr;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <Attribute>
*/
public class Attribute extends ComplexType
{
	private static final long serialVersionUID = 1L;
  public Attribute()
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
  
  public void setSelectionAttribute(SelectionAttribute SelectionAttribute)
  {
    setElementValue("SelectionAttribute", SelectionAttribute);
  }
  
  public SelectionAttribute getSelectionAttribute()
  {
    return (SelectionAttribute) getElementValue("SelectionAttribute", "SelectionAttribute");
  }
  
  public boolean removeSelectionAttribute()
  {
    return removeElement("SelectionAttribute");
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

