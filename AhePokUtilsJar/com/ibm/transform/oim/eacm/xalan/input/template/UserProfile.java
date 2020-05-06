package com.ibm.transform.oim.eacm.xalan.input.template;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <UserProfile>
*/
public class UserProfile extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public UserProfile()
  {
  }
  
  public void setProfileEnterprise(ProfileEnterprise ProfileEnterprise)
  {
    setElementValue("ProfileEnterprise", ProfileEnterprise);
  }
  
  public ProfileEnterprise getProfileEnterprise()
  {
    return (ProfileEnterprise) getElementValue("ProfileEnterprise", "ProfileEnterprise");
  }
  
  public boolean removeProfileEnterprise()
  {
    return removeElement("ProfileEnterprise");
  }
  
  public void setWorkgroup(Workgroup Workgroup)
  {
    setElementValue("Workgroup", Workgroup);
  }
  
  public Workgroup getWorkgroup()
  {
    return (Workgroup) getElementValue("Workgroup", "Workgroup");
  }
  
  public boolean removeWorkgroup()
  {
    return removeElement("Workgroup");
  }
  
  public void setReadLanguage(ReadLanguage ReadLanguage)
  {
    setElementValue("ReadLanguage", ReadLanguage);
  }
  
  public ReadLanguage getReadLanguage()
  {
    return (ReadLanguage) getElementValue("ReadLanguage", "ReadLanguage");
  }
  
  public boolean removeReadLanguage()
  {
    return removeElement("ReadLanguage");
  }
  
  public void setWriteLanguage(WriteLanguage WriteLanguage)
  {
    setElementValue("WriteLanguage", WriteLanguage);
  }
  
  public WriteLanguage getWriteLanguage()
  {
    return (WriteLanguage) getElementValue("WriteLanguage", "WriteLanguage");
  }
  
  public boolean removeWriteLanguage()
  {
    return removeElement("WriteLanguage");
  }
  
  public void setRole(Role Role)
  {
    setElementValue("Role", Role);
  }
  
  public Role getRole()
  {
    return (Role) getElementValue("Role", "Role");
  }
  
  public boolean removeRole()
  {
    return removeElement("Role");
  }
  
  public void setUserToken(String userToken)
  {
    setAttributeValue("userToken", userToken);
  }
  
  public String getUserToken()
  {
    return getAttributeValue("userToken");
  }
  
  public boolean removeUserToken()
  {
    return removeAttribute("userToken");
  }
  
}

