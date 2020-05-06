<?xml version="1.0" encoding="UTF-8"?>
<!--
(C) Copyright IBM Corp. 2005, 2008  All Rights Reserved.

  $Log: ExcelReport.xsl,v $
  Revision 1.2  2008/08/26 21:23:36  wendy
  Added outputting a message for the user

  Revision 1.1  2007/03/07 00:18:37  chris
  generalized style sheet for XSLReportABR

  Revision 1.2  2006/12/19 17:00:01  chris
  Design change for TIR 6WJMRP to prevent abr from running when DMNET is running

  Revision 1.1  2006/12/13 21:04:03  chris
  SPML Drop

  
  This is the default report the user would see if there was a problem creating the binary report
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
    <xsl:param name="displayName" >Excel Report</xsl:param>
    <xsl:param name="dgTitle" >Excel Report</xsl:param>
    
    <xsl:template match="/">
      <xsl:variable name="abrDoc" select="xmlabr:getDOM($xmlabr, '/com/ibm/transform/oim/eacm/xalan/input/template/ExcelReport.xml')"/>
      <xsl:variable name="root" select="$abrDoc/data/group[@rootEntityType='true']"/>
      <html xml:lang="en" lang="en">
        <head>
          <!--<xsl:call-template name="metaTags">
            <xsl:with-param name="desc" select="'ABR Advance Status Report'"/>
          </xsl:call-template>
          <xsl:call-template name="css"/>-->
          <title>EACM | <xsl:value-of select="$dgTitle"/></title>
        </head>
        <body id="w3-ibm-com">
        <!--<xsl:call-template name="masthead"/>-->
        <!-- start content //////////////////////////////////////////// -->
        <div id="content">
            <!-- start main content -->
            <div id="content-main">             
          <xsl:apply-templates select="ABRJob" />
          <xsl:variable name="abrCode" select="ABRJob/EntityDescription/AttributeDescription/@code"/>
          <h1><xsl:value-of select="$dgTitle"/></h1>
          <table>
            <tr><th>Date: </th><td><xsl:value-of select="ABRJob/@timestamp"/></td></tr>
            <tr><th>User: </th><td><xsl:value-of select="ABRJob/UserProfile/@userToken" /> (<xsl:value-of select="/ABRJob/UserProfile/Role" />)</td></tr>
            <!--<b>Description: </b> 
            <xsl:value-of select="$root/meta/type" />: 
            <xsl:value-of select="$root/entity/attribute[@code=$abrCode]" /> Error Report.-->
          </table>
          <xsl:choose>
            <xsl:when test="$abrDoc/test[@method='isDependentProcessRunning']/@result='true'">
	          <p>
	          The <xsl:value-of select="$abrDoc/text[@method='getDependentProcessName']"/> 
	          job which started at <xsl:value-of select="$abrDoc/text[@method='getDependentProcessStartTime']"/> 
	          is still running. <br/>We cannot produce the <xsl:value-of select="$dgTitle"/> for you at this time.<br/> 
	          Please try again later.
	          </p>
            </xsl:when>
            <xsl:otherwise>
	          <!--   <p>Failed</p> this makes no sense -->
            </xsl:otherwise>
          </xsl:choose>
           <pre>
          <xsl:value-of select="ABRJob/Message" /> 
          </pre>
          <xsl:choose>
            <xsl:when test="$abrDoc/test[@method='hasPassed']/@result='true'">
	          <p>ABR Passed</p>
            </xsl:when>
            <xsl:otherwise>
	          <p>ABR Failed</p>
            </xsl:otherwise>
          </xsl:choose>
          
          <!--<xsl:call-template name="footer"/>-->

          </div>
          <!-- stop main content -->
        </div>
        <!-- stop content //////////////////////////////////////////// -->                
         <xsl:value-of select="ABRJob/DGSubmit" disable-output-escaping="yes"/>         
        </body>
      </html>
    </xsl:template>  
      
</xsl:stylesheet>
