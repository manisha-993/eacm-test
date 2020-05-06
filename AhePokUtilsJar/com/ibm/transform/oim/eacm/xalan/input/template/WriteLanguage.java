package com.ibm.transform.oim.eacm.xalan.input.template;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <WriteLanguage>
*/
public class WriteLanguage extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public WriteLanguage()
  {
  }
  
  public void setNlsID(int nlsID)
  {
    setAttributeValue("nlsID", nlsID);
  }
  
  public int getNlsID()
  {
    return getAttributeIntegerValue("nlsID");
  }
  
  public boolean removeNlsID()
  {
    return removeAttribute("nlsID");
  }
  
}

