package com.ibm.transform.oim.eacm.xalan.xslabr;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <XSLParam>
*/
public class XSLParam extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public XSLParam()
  {
  }
  
  public void setObj(int index, Obj Obj)
  {
    setElementValue(index, "Obj", Obj);
  }
  
  public Obj getObj(int index)
  {
    return (Obj) getElementValue("Obj", "Obj", index);
  }
  
  public int getObjCount()
  {
    return sizeOfElement("Obj");
  }
  
  public boolean removeObj(int index)
  {
    return removeElement(index, "Obj");
  }
  
  public void setName(String name)
  {
    setAttributeValue("name", name);
  }
  
  public String getName()
  {
    return getAttributeValue("name");
  }
  
  public boolean removeName()
  {
    return removeAttribute("name");
  }
  
}

