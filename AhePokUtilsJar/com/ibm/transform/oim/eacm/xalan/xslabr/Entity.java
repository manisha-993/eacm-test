package com.ibm.transform.oim.eacm.xalan.xslabr;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <Entity>
*/
public class Entity extends ComplexType
{
	private static final long serialVersionUID = 1L;
  public Entity()
  {
  }
  
  public void setAttribute(int index, Attribute Attribute)
  {
    setElementValue(index, "Attribute", Attribute);
  }
  
  public Attribute getAttribute(int index)
  {
    return (Attribute) getElementValue("Attribute", "Attribute", index);
  }
  
  public int getAttributeCount()
  {
    return sizeOfElement("Attribute");
  }
  
  public boolean removeAttribute(int index)
  {
    return removeElement(index, "Attribute");
  }
  
  public void setType(String type)
  {
    setAttributeValue("type", type);
  }
  
  public String getType()
  {
    return getAttributeValue("type");
  }
  
  public boolean removeType()
  {
    return removeAttribute("type");
  }
  
}

