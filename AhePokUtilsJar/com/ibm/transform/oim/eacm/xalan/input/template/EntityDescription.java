package com.ibm.transform.oim.eacm.xalan.input.template;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <EntityDescription>
*/
public class EntityDescription extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public EntityDescription()
  {
  }
  
  public void setAttributeDescription(AttributeDescription AttributeDescription)
  {
    setElementValue("AttributeDescription", AttributeDescription);
  }
  
  public AttributeDescription getAttributeDescription()
  {
    return (AttributeDescription) getElementValue("AttributeDescription", "AttributeDescription");
  }
  
  public boolean removeAttributeDescription()
  {
    return removeElement("AttributeDescription");
  }
  
  public void setEntityID(int entityID)
  {
    setAttributeValue("entityID", entityID);
  }
  
  public int getEntityID()
  {
    return getAttributeIntegerValue("entityID");
  }
  
  public boolean removeEntityID()
  {
    return removeAttribute("entityID");
  }
  
  public void setEntityType(String entityType)
  {
    setAttributeValue("entityType", entityType);
  }
  
  public String getEntityType()
  {
    return getAttributeValue("entityType");
  }
  
  public boolean removeEntityType()
  {
    return removeAttribute("entityType");
  }
  
}

