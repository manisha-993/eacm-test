package com.ibm.transform.oim.eacm.xalan.input.template;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <ABRJob>
*/
public class ABRJob extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public ABRJob()
  {
  }
  
  public void setABRCode(ABRCode ABRCode)
  {
    setElementValue("ABRCode", ABRCode);
  }
  
  public ABRCode getABRCode()
  {
    return (ABRCode) getElementValue("ABRCode", "ABRCode");
  }
  
  public boolean removeABRCode()
  {
    return removeElement("ABRCode");
  }
  
  public void setEntityDescription(EntityDescription EntityDescription)
  {
    setElementValue("EntityDescription", EntityDescription);
  }
  
  public EntityDescription getEntityDescription()
  {
    return (EntityDescription) getElementValue("EntityDescription", "EntityDescription");
  }
  
  public boolean removeEntityDescription()
  {
    return removeElement("EntityDescription");
  }
  
  public void setUserProfile(UserProfile UserProfile)
  {
    setElementValue("UserProfile", UserProfile);
  }
  
  public UserProfile getUserProfile()
  {
    return (UserProfile) getElementValue("UserProfile", "UserProfile");
  }
  
  public boolean removeUserProfile()
  {
    return removeElement("UserProfile");
  }
  
  public void setDGSubmit(String DGSubmit)
  {
    setElementValue("DGSubmit", DGSubmit);
  }
  
  public String getDGSubmit()
  {
    return getElementValue("DGSubmit");
  }
  
  public boolean removeDGSubmit()
  {
    return removeElement("DGSubmit");
  }
  
  public void setJobNumber(String jobNumber)
  {
    setAttributeValue("jobNumber", jobNumber);
  }
  
  public String getJobNumber()
  {
    return getAttributeValue("jobNumber");
  }
  
  public boolean removeJobNumber()
  {
    return removeAttribute("jobNumber");
  }
  
  public void setTimestamp(String timestamp)
  {
    setAttributeValue("timestamp", timestamp);
  }
  
  public String getTimestamp()
  {
    return getAttributeValue("timestamp");
  }
  
  public boolean removeTimestamp()
  {
    return removeAttribute("timestamp");
  }
  
  public void setMessage(String msg)
  {
    setElementValue("Message", msg);
  }
  
  public String getMessage()
  {
    return getElementValue("Message");
  }
  
  public boolean removeMessage()
  {
    return removeElement("Message");
  }
}

