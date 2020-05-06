<?xml version="1.0" encoding="UTF-8"?>
<!--
(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.

  $Log: AdvanceStatus.xsl,v $
  Revision 1.1  2006/10/19 21:21:56  chris
  Taxonomy ABR's

  
  -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="1.0"
    xmlns:xalan="http://xml.apache.org/xslt"
    xmlns:xmlabr="xalan://com.ibm.transform.oim.eacm.xalan.XMLABR"
    extension-element-prefixes="xmlabr"
    exclude-result-prefixes="xmlabr">
    <!-- /com/ibm/transform/oim/eacm/xalan/style -->
    <!--<xsl:import href="/com/ibm/transform/oim/eacm/xalan/style/eacustom.xsl" />-->
    <xsl:import href="/com/ibm/transform/oim/eacm/xalan/style/standard-includes.xsl" />
    
    <xsl:param name="xmlabr" />
    <xsl:param name="displayName" >ABR Advance Status</xsl:param>
    
    <xsl:template match="/">
      <xsl:variable name="abrDoc" select="xmlabr:getDOM($xmlabr, '/com/ibm/transform/oim/eacm/sg/xalan/input/template/AdvanceStatus.xml')"/>
      <xsl:variable name="root" select="$abrDoc/data/group[@rootEntityType='true']"/>
      <html xml:lang="en" lang="en">
        <head>
          <!--<xsl:call-template name="metaTags">
            <xsl:with-param name="desc" select="'ABR Advance Status Report'"/>
          </xsl:call-template>
          <xsl:call-template name="css"/>-->
          <title>e-announce | <xsl:value-of select="$displayName"/></title>
        </head>
        <body id="w3-ibm-com">
        <!--<xsl:call-template name="masthead"/>-->
        <!-- start content //////////////////////////////////////////// -->
        <div id="content">
            <!-- start main content -->
            <div id="content-main">             
          <xsl:apply-templates select="abr" />
          <h1><xsl:value-of select="$displayName"/></h1>
          <p>
            <b>Date: </b><xsl:value-of select="/abr/timestamp"/><br/>
            <b>User: </b><xsl:value-of select="/abr/user-token" /> (<xsl:value-of select="/abr/role" />)<br />
            <b>Description: </b> 
            <xsl:value-of select="$root/meta/type" />: 
            <xsl:value-of select="$root/entity/attribute[@code='DATAQUALITY']" /> Data Quality Checks.
          </p>
          <p><xsl:value-of select="$abrDoc/text[@method='getDisplayName']"/></p>
          <xsl:choose>
            <xsl:when test="$abrDoc/test[@method='hasPassed']/@result='true'">
	          <p>Passed - Status was advanced to <xsl:value-of select="$abrDoc/text[@method='getStatus']"></xsl:value-of>.</p>
            </xsl:when>
            <xsl:otherwise>
              <xsl:choose>
    	        <xsl:when test="$abrDoc/test[@method='isITS']/@result='false'">
    	          <p>Failed - There was a problem advancing Status.</p>
    	        </xsl:when>
    	        <xsl:otherwise>
	              <p>Failed - Status unchanged and Data Quality returned to <xsl:value-of select="$abrDoc/text[@method='getDataQuality']"></xsl:value-of>.</p>
    	        </xsl:otherwise>
              </xsl:choose>
            </xsl:otherwise>
          </xsl:choose>
          
          <!--<xsl:call-template name="footer"/>-->

          </div>
          <!-- stop main content -->
        </div>
        <!-- stop content //////////////////////////////////////////// -->                
         <xsl:value-of select="abr/dgsubmit" disable-output-escaping="yes"/>         
        </body>
      </html>
    </xsl:template>  
      
</xsl:stylesheet>
