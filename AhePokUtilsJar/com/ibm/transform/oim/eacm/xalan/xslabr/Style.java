package com.ibm.transform.oim.eacm.xalan.xslabr;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <Style>
*/
public class Style extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public Style()
  {
  }
  
  public void setUsesConnection(int index, String UsesConnection)
  {
    setElementValue(index, "UsesConnection", UsesConnection);
  }
  
  public String getUsesConnection(int index)
  {
    return getElementValue("UsesConnection", index);
  }
  
  public int getUsesConnectionCount()
  {
    return sizeOfElement("UsesConnection");
  }
  
  public boolean removeUsesConnection(int index)
  {
    return removeElement(index, "UsesConnection");
  }
  
  public void setDGTitle(String DGTitle)
  {
    setElementValue("DGTitle", DGTitle);
  }
  
  public String getDGTitle()
  {
    return getElementValue("DGTitle");
  }
  
  public boolean removeDGTitle()
  {
    return removeElement("DGTitle");
  }
  
  public void setDGClass(String DGClass)
  {
    setElementValue("DGClass", DGClass);
  }
  
  public String getDGClass()
  {
    return getElementValue("DGClass");
  }
  
  public boolean removeDGClass()
  {
    return removeElement("DGClass");
  }
  
  public void setXSLParam(int index, XSLParam XSLParam)
  {
    setElementValue(index, "XSLParam", XSLParam);
  }
  
  public XSLParam getXSLParam(int index)
  {
    return (XSLParam) getElementValue("XSLParam", "XSLParam", index);
  }
  
  public int getXSLParamCount()
  {
    return sizeOfElement("XSLParam");
  }
  
  public boolean removeXSLParam(int index)
  {
    return removeElement(index, "XSLParam");
  }
  
  public void setXsl(String xsl)
  {
    setAttributeValue("xsl", xsl);
  }
  
  public String getXsl()
  {
    return getAttributeValue("xsl");
  }
  
  public boolean removeXsl()
  {
    return removeAttribute("xsl");
  }
  
  public void setZip(boolean zip)
  {
    setAttributeValue("zip", zip);
  }
  
  public boolean getZip()
  {
    return getAttributeBooleanValue("zip");
  }
  
  public boolean removeZip()
  {
    return removeAttribute("zip");
  }
  
}

