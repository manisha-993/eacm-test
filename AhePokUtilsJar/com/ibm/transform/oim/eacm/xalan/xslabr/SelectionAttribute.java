package com.ibm.transform.oim.eacm.xalan.xslabr;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <SelectionAttribute>
*/
public class SelectionAttribute extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public SelectionAttribute()
  {
  }
  
  public void setOption(int index, Option Option)
  {
    setElementValue(index, "Option", Option);
  }
  
  public Option getOption(int index)
  {
    return (Option) getElementValue("Option", "Option", index);
  }
  
  public int getOptionCount()
  {
    return sizeOfElement("Option");
  }
  
  public boolean removeOption(int index)
  {
    return removeElement(index, "Option");
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

