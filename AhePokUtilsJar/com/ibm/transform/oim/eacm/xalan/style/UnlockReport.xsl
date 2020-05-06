<?xml version="1.0" encoding="UTF-8"?>
<!--
(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.

  $Log: UnlockReport.xsl,v $
  Revision 1.5  2006/02/23 15:50:34  wendy
  Changes for AHE

  Revision 1.4  2006/01/26 15:10:30  wendy
  AHE copyright

  Revision 1.3  2005/09/08 19:11:35  wendy
  New pkg

  Revision 1.1  2005/03/02 18:39:51  chris
  Unlock function for CR4220

  -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="1.0"
    xmlns:xalan="http://xml.apache.org/xslt"
    xmlns:unlock="xalan://com.ibm.transform.oim.eacm.xalan.Unlock">
    <xsl:import href="/com/ibm/transform/oim/eacm/xalan/style/eacustom.xsl" />
    <xsl:import href="/com/ibm/transform/oim/eacm/xalan/style/standard-includes.xsl" />
    
    <xsl:param name="unlockCode" />
    <xsl:param name="unlockAction" />
    
    <xsl:template match="/">
      <html xml:lang="en" lang="en">
        <head>
          <xsl:call-template name="metaTags">
            <xsl:with-param name="desc" select="'UnlockReport'"/>
          </xsl:call-template>
          <xsl:call-template name="css"/>
          <title>e-announce | Unlock ABR</title>
        </head>
        <body id="w3-ibm-com">
        <xsl:call-template name="masthead"/>
        <!-- start content //////////////////////////////////////////// -->
        <div id="content">
            <!-- start main content -->
            <div id="content-main">             
          <xsl:apply-templates select="abr" />
          <h1>Unlock ABR</h1>
          <p>
            <b>Date: </b><xsl:value-of select="/abr/timestamp"/><br/>
            <b>User: </b><xsl:value-of select="/abr/user-token" /> (<xsl:value-of select="/abr/role" />)<br />
            <b>Description: </b>Unlocks the VE associated with the Entity
            <xsl:comment>unlock action is <xsl:value-of select="$unlockAction"/></xsl:comment>
          </p>
          <xsl:call-template name="unlock"/>
          
          <xsl:call-template name="footer"/>

          </div>
          <!-- stop main content -->
        </div>
        <!-- stop content //////////////////////////////////////////// -->                
         <xsl:value-of select="abr/dgsubmit" disable-output-escaping="yes"/>         
        </body>
      </html>
    </xsl:template>
    
    <xsl:template name="unlock">
      <xsl:variable name="passed" select="unlock:triggerUnLock($unlockCode, $unlockAction)" />
      <p>
      The Un-Lock Action for <xsl:value-of select="unlock:getNavigationName($unlockCode)"/> has 
      <xsl:choose>
        <xsl:when test="$passed=true()">Passed</xsl:when>
        <xsl:otherwise>Failed</xsl:otherwise>
      </xsl:choose>
      </p>
      <xsl:comment>dereferenced=<xsl:value-of select="unlock:dereference($unlockCode)"/></xsl:comment>
    </xsl:template>
</xsl:stylesheet>
