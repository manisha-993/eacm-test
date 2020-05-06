<?xml version="1.0" encoding="UTF-8"?>
<!--
(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.

  $Log: XSLReportABRConfigurationError.xsl,v $
  Revision 1.5  2006/02/23 15:50:34  wendy
  Changes for AHE

  Revision 1.4  2006/01/26 15:10:30  wendy
  AHE copyright

  Revision 1.3  2005/09/08 19:11:35  wendy
  New pkg

  Revision 1.1  2005/02/23 21:13:02  chris
  Initial XSL Report ABR Code

  -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="1.0"
    xmlns:xalan="http://xml.apache.org/xslt">
    <xsl:import href="/com/ibm/transform/oim/eacm/xalan/style/eacustom.xsl" />
    <xsl:import href="/com/ibm/transform/oim/eacm/xalan/style/standard-includes.xsl" />
    <xsl:output method="html" encoding="UTF-8" doctype-system="http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd" indent="yes" />
    <xsl:param name="configError" select="'Unspecified Error'"/>
    
    <xsl:template match="/">
      <html>
        <head>
          <xsl:call-template name="metaTags">
            <xsl:with-param name="desc" select="'XSLReportABRConfigurationError'"/>
          </xsl:call-template>
          <title>e-announce | XSL Report ABR Configuration Exception</title>
          <xsl:call-template name="css"/>
        </head>
        <body id="w3-ibm-com">
        <xsl:call-template name="masthead"/>
        <!-- start content //////////////////////////////////////////// -->
        <div id="content">
            <!-- start main content -->
            <div id="content-main">        

          <h1><xsl:value-of select="$configError" /></h1>
          <xsl:apply-templates select="abr" />
          
            <xsl:call-template name="footer"/>

            </div>
            <!-- stop main content -->
        </div>
        <!-- stop content //////////////////////////////////////////// -->                
          
        </body>
      </html>
    </xsl:template>

    <!-- This overrides the abr template in the standard-includes.xml -->    
    <xsl:template match="abr">
        <h2>Information about the java code</h2>
        <table summary="layout">
        <tr><td>ABR Class is</td><td><xsl:value-of select="class" /></td></tr>
        <tr><td>CVS Revision is</td><td><xsl:value-of select="revision" /></td></tr>
        </table>
        
        <h2>Information about the entity instance</h2>
        <table summary="layout">
        <tr><td>Entity Type is</td><td><xsl:value-of select="entity/@type" /> (<xsl:value-of select="entity" />)</td></tr>
        <tr><td>Entity ID is</td><td><xsl:value-of select="entity/@id" /></td></tr>
        <tr><td>Attribute Code is</td><td><xsl:value-of select="code" /></td></tr>
        </table>

        <h2>Information about the ABR job</h2>
        <table summary="layout">
        <tr><td>Taskmaster job number is</td><td><xsl:value-of select="job-id" /></td></tr>
        <tr><td>Timestamp is</td><td><xsl:value-of select="timestamp" /></td></tr>
        </table>
        
        <h2>Information about who invoked the report</h2>
        <table summary="layout">
        <tr><td>User ID is</td><td><xsl:value-of select="user-token" /></td></tr>
        <tr><td>Enterprise is</td><td><xsl:value-of select="enterprise" /></td></tr>
        <tr><td>Workgroup is</td><td><xsl:value-of select="workgroup" /> (<xsl:value-of select="workgroup/@id" />)</td></tr>
        <tr><td>Role is</td><td><xsl:value-of select="role" /> (<xsl:value-of select="role/@code" />)</td></tr>
        <tr><td>Read Language is</td><td><xsl:value-of select="language/read" /> (<xsl:value-of select="language/read/@id" />)</td></tr>
        <tr><td>WriteLanguage is</td><td><xsl:value-of select="language/write" /> (<xsl:value-of select="language/write/@id" />)</td></tr>
        </table>
    </xsl:template>
    
</xsl:stylesheet>
