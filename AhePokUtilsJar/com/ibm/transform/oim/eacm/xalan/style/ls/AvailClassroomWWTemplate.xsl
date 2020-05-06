<?xml version="1.0" encoding="UTF-8"?>
<!--
(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.

  $Log: AvailClassroomWWTemplate.xsl,v $
  Revision 1.4  2006/02/23 15:50:34  wendy
  Changes for AHE

  Revision 1.3  2006/01/26 15:11:42  wendy
  AHE copyright

  Revision 1.2  2005/10/06 17:57:05  wendy
  New AHE format

  Revision 1.1  2005/09/08 19:12:05  wendy
  New pkg

  Revision 1.2  2005/04/13 13:02:37  wendy
  Add output revision number

  Revision 1.1  2005/02/28 16:59:22  wendy
  Init for CR1008045141

  -->
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    version="1.0" 
    xmlns:xalan="http://xml.apache.org/xslt" 
    xmlns:eann="xalan://com.ibm.transform.oim.eacm.xalan.Eann" 
    xmlns:table="xalan://com.ibm.transform.oim.eacm.xalan.table.ls.AvailClassroomWWTable" 
    extension-element-prefixes="eann"
    exclude-result-prefixes="eann table">
    
    <xsl:import href="/com/ibm/transform/oim/eacm/xalan/style/eacustom.xsl" />
    <xsl:import href="/com/ibm/transform/oim/eacm/xalan/style/standard-includes.xsl" />
    <xsl:output method="xml" encoding="UTF-8" indent="yes" omit-xml-declaration="yes" 
       doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" 
       doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"/>
       
    <xsl:param name="wwTable" />
    <xsl:param name="AvailClassroomWWTable" />
    
    <xsl:variable name="ww" select="eann:new($wwTable)" />
    <xsl:variable name="ClassroomTable" select="eann:new($AvailClassroomWWTable)" />

    <xsl:template match="/">
      <html xml:lang="en" lang="en">
        <head>
          <xsl:call-template name="metaTags">
            <xsl:with-param name="desc" select="'AvailClassroomWW'"/>
          </xsl:call-template>
          <xsl:call-template name="css"/>
          <title>e-announce | Available Classroom WW In-Country Courses</title>
        </head>
        <body id="w3-ibm-com">
            <xsl:call-template name="masthead"/>
            <!-- start content //////////////////////////////////////////// -->
            <div id="content">
                <!-- start main content -->
                <div id="content-main">        
                <xsl:comment> Code Version=<xsl:value-of select="table:getVersion($AvailClassroomWWTable)" /> </xsl:comment>
                <xsl:apply-templates select="abr" />
                <xsl:call-template name="title" />
                <p>The current language is <xsl:value-of select="abr/language/read" /></p>
                
                <xsl:variable name="table" select='eann:getTableAsXML($ClassroomTable)' />
                
                <xsl:choose>
                    <xsl:when test="count($table/data/row-set/row) = 0">
                        <p>No Available Classroom Worldwide In-Country courses found.</p>
                    </xsl:when> 
                    <xsl:otherwise>
                        <table cellspacing="1" cellpadding="0" class="basic-table" summary="Available courses">
                        <xsl:call-template name="tableData" >
                          <xsl:with-param name="table" select="$table"/>
                        </xsl:call-template>
                        </table>
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:comment>dereferenced=<xsl:value-of select="eann:dereference($ClassroomTable)" /></xsl:comment>
                
                <xsl:call-template name="footer"/>
                
                </div>
                <!-- stop main content -->
            </div>
            <!-- stop content //////////////////////////////////////////// -->                
            <xsl:value-of select="abr/dgsubmit" disable-output-escaping="yes"/>
        </body>
      </html>
    </xsl:template>
    
    <xsl:template name="title">
      <xsl:variable name="table" select='eann:getTableAsXML($ww)' />

      <h1>
        <xsl:text>Available Classroom Worldwide In-Country Courses for Worldwide Course </xsl:text>
        <xsl:value-of select="$table/data/row-set/row[1]/col[1]" /> 
        <xsl:text> (Exp date: </xsl:text>
        <xsl:variable name="expdate" select="$table/data/row-set/row[1]/col[2]" />
        <xsl:choose>
          <xsl:when test="boolean(string($expdate))">
            <xsl:value-of select="$expdate" /> 
          </xsl:when> 
          <xsl:otherwise>
            <em>** Not Populated **</em>
          </xsl:otherwise>
        </xsl:choose>        
        <xsl:text>)</xsl:text>
      </h1>

      <xsl:comment>dereferenced=<xsl:value-of select="eann:dereference($ww)" /></xsl:comment>
    </xsl:template>
    
    <xsl:template name="tableData">
      <xsl:param name="table" />
      <xsl:apply-templates select="$table/data/metadata" />
      <xsl:apply-templates select="$table/data/row-set/row"  />
    </xsl:template>
    
</xsl:stylesheet>
