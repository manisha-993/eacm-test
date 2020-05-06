package com.ibm.transform.oim.eacm.xalan.input.template;

/**
* A sample program to show how to use the generated Java beans to:
*  - create and save an XML document
*  - load the XML document and print its content
*/
public class Sample
{
  ABRJobFactory iABRJobFactory;
  ABRJob iABRJob;
  
  public Sample()
  {
  }
  
  /**
  * Create the Java bean ABRCode
  *   @param name The element tag name
  *   @return ABRCode The Java bean for this element
  */
  ABRCode createABRCode(String name)
  {
    ABRCode iABRCode = iABRJobFactory.createABRCode(name);
    
    return initABRCode(iABRCode);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param ABRCode The Java bean
  *   @return ABRCode The Java bean
  */
  ABRCode initABRCode(ABRCode iABRCode)
  {
    iABRCode.setClassName("className");
    iABRCode.setRevision("revision");
    
    return iABRCode;
  }
  
  /**
  * Print the element represented by the Java bean ABRCode
  */
  void printABRCode(ABRCode iABRCode)
  {
    System.out.println(iABRCode.getClassName());
    System.out.println(iABRCode.getRevision());
  }
  
  /**
  * Create the root element in the document
  */
  void createABRJob()
  {
    iABRJob.setABRCode(createABRCode("ABRCode"));
    iABRJob.setEntityDescription(createEntityDescription("EntityDescription"));
    iABRJob.setUserProfile(createUserProfile("UserProfile"));
    iABRJob.setDGSubmit("DGSubmit");
    iABRJob.setJobNumber("jobNumber");
    iABRJob.setTimestamp("timestamp");
  }
  
  /**
  * Print the content of the root element
  */
  void printABRJob()
  {
    printABRCode(iABRJob.getABRCode());
    printEntityDescription(iABRJob.getEntityDescription());
    printUserProfile(iABRJob.getUserProfile());
    System.out.println(iABRJob.getDGSubmit());
    System.out.println(iABRJob.getJobNumber());
    System.out.println(iABRJob.getTimestamp());
  }
  
  /**
  * Create the Java bean AttributeDescription
  *   @param name The element tag name
  *   @return AttributeDescription The Java bean for this element
  */
  AttributeDescription createAttributeDescription(String name)
  {
    AttributeDescription iAttributeDescription = iABRJobFactory.createAttributeDescription(name);
    
    return initAttributeDescription(iAttributeDescription);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param AttributeDescription The Java bean
  *   @return AttributeDescription The Java bean
  */
  AttributeDescription initAttributeDescription(AttributeDescription iAttributeDescription)
  {
    iAttributeDescription.setCode("code");
    
    return iAttributeDescription;
  }
  
  /**
  * Print the element represented by the Java bean AttributeDescription
  */
  void printAttributeDescription(AttributeDescription iAttributeDescription)
  {
    System.out.println(iAttributeDescription.getCode());
  }
  
  /**
  * Set the element DGSubmit to the specified value
  *   @param iDGSubmit The Java bean for this element
  *   @return DGSubmit The Java bean
  */
  DGSubmit initDGSubmit(DGSubmit iDGSubmit)
  {
    iDGSubmit.updateElementValue("DGSubmit");
    return iDGSubmit;
  }
  
  /**
  * Print the element value represented by the Java bean DGSubmit
  *   @param iDGSubmit The Java bean for this element
  */
  void printDGSubmit(DGSubmit iDGSubmit)
  {
    System.out.println(iDGSubmit.getElementValue());
  }
  
  /**
  * Create the Java bean ProfileEnterprise
  *   @param name The element tag name
  *   @return ProfileEnterprise The Java bean for this element
  */
  ProfileEnterprise createProfileEnterprise(String name)
  {
    ProfileEnterprise iProfileEnterprise = iABRJobFactory.createProfileEnterprise(name);
    
    return initProfileEnterprise(iProfileEnterprise);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param ProfileEnterprise The Java bean
  *   @return ProfileEnterprise The Java bean
  */
  ProfileEnterprise initProfileEnterprise(ProfileEnterprise iProfileEnterprise)
  {
    iProfileEnterprise.setCode("code");
    
    return iProfileEnterprise;
  }
  
  /**
  * Print the element represented by the Java bean ProfileEnterprise
  */
  void printProfileEnterprise(ProfileEnterprise iProfileEnterprise)
  {
    System.out.println(iProfileEnterprise.getCode());
  }
  
  /**
  * Create the Java bean EntityDescription
  *   @param name The element tag name
  *   @return EntityDescription The Java bean for this element
  */
  EntityDescription createEntityDescription(String name)
  {
    EntityDescription iEntityDescription = iABRJobFactory.createEntityDescription(name);
    
    return initEntityDescription(iEntityDescription);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param EntityDescription The Java bean
  *   @return EntityDescription The Java bean
  */
  EntityDescription initEntityDescription(EntityDescription iEntityDescription)
  {
    iEntityDescription.setAttributeDescription(createAttributeDescription("AttributeDescription"));
    iEntityDescription.setEntityID(0);
    iEntityDescription.setEntityType("entityType");
    
    return iEntityDescription;
  }
  
  /**
  * Print the element represented by the Java bean EntityDescription
  */
  void printEntityDescription(EntityDescription iEntityDescription)
  {
    printAttributeDescription(iEntityDescription.getAttributeDescription());
    System.out.println(iEntityDescription.getEntityID());
    System.out.println(iEntityDescription.getEntityType());
  }
  
  /**
  * Create the Java bean ReadLanguage
  *   @param name The element tag name
  *   @return ReadLanguage The Java bean for this element
  */
  ReadLanguage createReadLanguage(String name)
  {
    ReadLanguage iReadLanguage = iABRJobFactory.createReadLanguage(name);
    
    return initReadLanguage(iReadLanguage);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param ReadLanguage The Java bean
  *   @return ReadLanguage The Java bean
  */
  ReadLanguage initReadLanguage(ReadLanguage iReadLanguage)
  {
    iReadLanguage.setNlsID(0);
    
    return iReadLanguage;
  }
  
  /**
  * Print the element represented by the Java bean ReadLanguage
  */
  void printReadLanguage(ReadLanguage iReadLanguage)
  {
    System.out.println(iReadLanguage.getNlsID());
  }
  
  /**
  * Create the Java bean Role
  *   @param name The element tag name
  *   @return Role The Java bean for this element
  */
  Role createRole(String name)
  {
    Role iRole = iABRJobFactory.createRole(name);
    
    return initRole(iRole);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param Role The Java bean
  *   @return Role The Java bean
  */
  Role initRole(Role iRole)
  {
    iRole.setCode("code");
    
    return iRole;
  }
  
  /**
  * Print the element represented by the Java bean Role
  */
  void printRole(Role iRole)
  {
    System.out.println(iRole.getCode());
  }
  
  /**
  * Create the Java bean UserProfile
  *   @param name The element tag name
  *   @return UserProfile The Java bean for this element
  */
  UserProfile createUserProfile(String name)
  {
    UserProfile iUserProfile = iABRJobFactory.createUserProfile(name);
    
    return initUserProfile(iUserProfile);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param UserProfile The Java bean
  *   @return UserProfile The Java bean
  */
  UserProfile initUserProfile(UserProfile iUserProfile)
  {
    iUserProfile.setProfileEnterprise(createProfileEnterprise("ProfileEnterprise"));
    iUserProfile.setWorkgroup(createWorkgroup("Workgroup"));
    iUserProfile.setReadLanguage(createReadLanguage("ReadLanguage"));
    iUserProfile.setWriteLanguage(createWriteLanguage("WriteLanguage"));
    iUserProfile.setRole(createRole("Role"));
    iUserProfile.setUserToken("userToken");
    
    return iUserProfile;
  }
  
  /**
  * Print the element represented by the Java bean UserProfile
  */
  void printUserProfile(UserProfile iUserProfile)
  {
    printProfileEnterprise(iUserProfile.getProfileEnterprise());
    printWorkgroup(iUserProfile.getWorkgroup());
    printReadLanguage(iUserProfile.getReadLanguage());
    printWriteLanguage(iUserProfile.getWriteLanguage());
    printRole(iUserProfile.getRole());
    System.out.println(iUserProfile.getUserToken());
  }
  
  /**
  * Create the Java bean Workgroup
  *   @param name The element tag name
  *   @return Workgroup The Java bean for this element
  */
  Workgroup createWorkgroup(String name)
  {
    Workgroup iWorkgroup = iABRJobFactory.createWorkgroup(name);
    
    return initWorkgroup(iWorkgroup);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param Workgroup The Java bean
  *   @return Workgroup The Java bean
  */
  Workgroup initWorkgroup(Workgroup iWorkgroup)
  {
    iWorkgroup.setWid(0);
    
    return iWorkgroup;
  }
  
  /**
  * Print the element represented by the Java bean Workgroup
  */
  void printWorkgroup(Workgroup iWorkgroup)
  {
    System.out.println(iWorkgroup.getWid());
  }
  
  /**
  * Create the Java bean WriteLanguage
  *   @param name The element tag name
  *   @return WriteLanguage The Java bean for this element
  */
  WriteLanguage createWriteLanguage(String name)
  {
    WriteLanguage iWriteLanguage = iABRJobFactory.createWriteLanguage(name);
    
    return initWriteLanguage(iWriteLanguage);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param WriteLanguage The Java bean
  *   @return WriteLanguage The Java bean
  */
  WriteLanguage initWriteLanguage(WriteLanguage iWriteLanguage)
  {
    iWriteLanguage.setNlsID(0);
    
    return iWriteLanguage;
  }
  
  /**
  * Print the element represented by the Java bean WriteLanguage
  */
  void printWriteLanguage(WriteLanguage iWriteLanguage)
  {
    System.out.println(iWriteLanguage.getNlsID());
  }
  
  /**
  * Create a new XML document using the generated ABRJobFactory class
  *   @param filename The XML file name
  */
  void createNewInstance(String filename)
  {
    iABRJobFactory = new ABRJobFactory();
    iABRJobFactory.setPackageName("com.ibm.transform.oim.eacm.xalan.input.template");
    
    // include schemaLocation hint for validation
    iABRJobFactory.setXSDFileName("ABRJob.xsd");
    
    // encoding for output document
    iABRJobFactory.setEncoding("UTF8");
    
    // encoding tag for xml declaration
    iABRJobFactory.setEncodingTag("UTF-8");
    
    // Create the root element in the document using the specified root element name
    iABRJob = (ABRJob) iABRJobFactory.createRoot("ABRJob");
    createABRJob();
    
    iABRJobFactory.save(filename);
  }
  
  /**
  * Load an XML document using the generated ABRJobFactory class
  *   @param filename An existing XML file name
  */
  void loadExistingInstance(String filename)
  {
    iABRJobFactory = new ABRJobFactory();
    iABRJobFactory.setPackageName("com.ibm.transform.oim.eacm.xalan.input.template");
    
    // Load the document
    iABRJob = (ABRJob) iABRJobFactory.loadDocument(filename);
    printABRJob();
  }
  
  /**
  * The main program.
  * Creates an example XML document and then loads it
  */
  public static void main(String args[])
  {
    Sample sample = new Sample();
    sample.createNewInstance("C:/Documents and Settings/Administrator/My Documents/IBM/wsappdevie51/workspace/AhePokUtilsJar/ABRJobSample.xml");
    sample.loadExistingInstance("C:/Documents and Settings/Administrator/My Documents/IBM/wsappdevie51/workspace/AhePokUtilsJar/ABRJobSample.xml");
  }
}

