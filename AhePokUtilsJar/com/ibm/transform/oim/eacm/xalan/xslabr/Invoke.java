package com.ibm.transform.oim.eacm.xalan.xslabr;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <Invoke>
*/
public class Invoke extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public Invoke()
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
  
  public void setMethod(String method)
  {
    setAttributeValue("method", method);
  }
  
  public String getMethod()
  {
    return getAttributeValue("method");
  }
  
  public boolean removeMethod()
  {
    return removeAttribute("method");
  }
  
}

