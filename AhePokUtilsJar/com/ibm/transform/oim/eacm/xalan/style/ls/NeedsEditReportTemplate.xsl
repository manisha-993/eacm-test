<?xml version="1.0" encoding="UTF-8"?>
<!--
(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.

  $Log: NeedsEditReportTemplate.xsl,v $
  Revision 1.4  2006/02/23 15:50:34  wendy
  Changes for AHE

  Revision 1.3  2006/01/26 15:11:42  wendy
  AHE copyright

  Revision 1.2  2005/10/06 17:57:05  wendy
  New AHE format

  Revision 1.1  2005/09/08 19:12:05  wendy
  New pkg

  Revision 1.2  2005/02/24 19:00:28  chris
  fix comparison

  Revision 1.1  2005/02/23 21:13:03  chris
  Initial XSL Report ABR Code

  -->
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    version="1.0" 
    xmlns:xalan="http://xml.apache.org/xslt" 
    xmlns:eann="xalan://com.ibm.transform.oim.eacm.xalan.Eann" 
    xmlns:table="xalan://com.ibm.transform.oim.eacm.xalan.table.ls.RequiresOrUnderEditTable" 
    extension-element-prefixes="eann"
    exclude-result-prefixes="eann table">
    
    <xsl:import href="/com/ibm/transform/oim/eacm/xalan/style/eacustom.xsl" />
    <xsl:import href="/com/ibm/transform/oim/eacm/xalan/style/standard-includes.xsl" />
    <xsl:output method="xml" encoding="UTF-8" indent="yes" omit-xml-declaration="yes" 
       doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" 
       doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"/>

    <xsl:param name="countryTable" />
    <xsl:param name="RequiresOrUnderEditTable" />
    
    <xsl:variable name="country" select="eann:new($countryTable)" />
    <xsl:variable name="EditTable" select="eann:new($RequiresOrUnderEditTable)" />

    <xsl:template match="/">
      <html xml:lang="en" lang="en">
        <xsl:element name="head">
          <xsl:call-template name="metaTags">
            <xsl:with-param name="desc" select="'NeedsEditReport'"/>
          </xsl:call-template>
          <xsl:call-template name="css"/>
          <title>e-announce | In-Country Courses Needing or Under Edit</title>
        </xsl:element>
        <body id="w3-ibm-com">
        <xsl:call-template name="masthead"/>
        <!-- start content //////////////////////////////////////////// -->
        <div id="content">
            <!-- start main content -->
            <div id="content-main">        
                <xsl:apply-templates select="abr" />
                <xsl:call-template name="title" />
                <p>The current language is <xsl:value-of select="abr/language/read" /></p>
                <table cellspacing="1" cellpadding="0" class="basic-table" summary="Courses needing or under edit">
                <xsl:call-template name="tableData" >
                  <xsl:with-param name="tableRef" select="$EditTable"/>
                  <xsl:with-param name="status" select="table:setRequiresEdit($RequiresOrUnderEditTable)"/>
                  <xsl:with-param name="header" select="boolean(1)"/>
                  <xsl:with-param name="label" select="'REQ'"/>
                </xsl:call-template>
                <xsl:call-template name="tableData" >
                  <xsl:with-param name="tableRef" select="$EditTable"/>
                  <xsl:with-param name="label" select="'UND'"/>
                  <xsl:with-param name="status" select="table:setUnderEdit($RequiresOrUnderEditTable)"/>
                </xsl:call-template>
                </table>
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
      <xsl:variable name="table" select='eann:getTableAsXML($country)' />

      <h1>
        Courses requiring Edit or under Edit for 
        <xsl:value-of select="$table/data/metadata/column-header[1]" /> 
        <xsl:text> </xsl:text>
        <xsl:value-of select="$table/data/row-set/row[1]/col[1]" /> 
        <xsl:text> </xsl:text>
        <xsl:value-of select="$table/data/row-set/row[1]/col[2]" /> 
        grouped by Catalog Editing Status:
      </h1>

      <xsl:comment>dereferenced=<xsl:value-of select="eann:dereference($country)" /></xsl:comment>
    </xsl:template>
    
    <xsl:template name="tableData">
      <xsl:param name="tableRef" />
      <xsl:param name="status" />
      <xsl:param name="header" select="boolean(0)" />
      <xsl:param name="label" />

      <xsl:variable name="table" select='eann:getTableAsXML($tableRef)' />

      <xsl:if test="$header=true()">
        <xsl:apply-templates select="$table/data/metadata" />
      </xsl:if>
      
      <tr style="background-color:#cef;">
        <xsl:element name="th">
          <xsl:attribute name="colspan">
            <xsl:value-of select="'8'"/>
          </xsl:attribute>
          <xsl:attribute name="id">
            <xsl:value-of select="$label"/>
          </xsl:attribute>
          <xsl:value-of select="$status" />
        </xsl:element>
      </tr>

      <xsl:if test="count($table/data/row-set/row) = 0">
        <tr class="odd">
        <xsl:element name="td">
          <xsl:attribute name="colspan">
            <xsl:value-of select="'8'"/>
          </xsl:attribute>
          <xsl:attribute name="headers">
            <xsl:value-of select="$label"/>
            <xsl:text> COL1 COL2 COL3 COL4 COL5 COL6 COL7 COL8</xsl:text>
          </xsl:attribute>
          <xsl:text>None</xsl:text>
        </xsl:element>        
        </tr>
      </xsl:if>

      <xsl:apply-templates select="$table/data/row-set/row" >
        <xsl:with-param name="rest" select="$label" />
        <xsl:sort order="ascending" select="col[7]"/>
      </xsl:apply-templates>

      <xsl:comment>dereferenced=<xsl:value-of select="eann:dereference($tableRef)" /></xsl:comment>
    </xsl:template>
    
    <!-- override 'meta-data' defined in standard-includes.xsl to output columns in a different order and concatenate  WWID and title -->
    <xsl:template match="metadata">
        <tr style="background-color:#aaa;">
          <!-- Get column-label attribute from each column-header-->
          <xsl:apply-templates select="column-header[1]"><!-- In-Country Course Code -->
            <xsl:with-param name="cnt" select="1" />
          </xsl:apply-templates>
          <xsl:apply-templates select="column-header[2]" ><!-- Global Reporting English Title -->
            <xsl:with-param name="cnt" select="2" />
          </xsl:apply-templates>
          <xsl:apply-templates select="column-header[3]" ><!-- Pricing Status -->
             <xsl:with-param name="cnt" select="3" />
          </xsl:apply-templates>
         <xsl:apply-templates select="column-header[7]" ><!-- Awaiting Approvals Timestamp -->
             <xsl:with-param name="cnt" select="4" />
          </xsl:apply-templates>

         <xsl:element name="th">
          <xsl:attribute name="id">
             <xsl:value-of select="'COL5'"/>
             </xsl:attribute>Worldwide Course
          </xsl:element>
          <xsl:apply-templates select="column-header[4]" ><!-- Delivery Method -->
             <xsl:with-param name="cnt" select="6" />
          </xsl:apply-templates>          
          <xsl:apply-templates select="column-header[5]" ><!-- Sub Delivery Method -->
             <xsl:with-param name="cnt" select="7" />
          </xsl:apply-templates>
          <xsl:apply-templates select="column-header[6]" ><!-- Active Course Languages -->
             <xsl:with-param name="cnt" select="8" />
          </xsl:apply-templates>          
        </tr>
    </xsl:template>
    
    <!-- override 'row' defined in standard-includes.xsl to output columns in a different order and concatenate  WWID and title -->
    <xsl:template match="row">
      <xsl:param name="rest" />
      <xsl:element name="tr">
         <xsl:attribute name="class">
         <!-- alternate attribute row color -->
            <xsl:choose>
              <xsl:when test="(position()+1) mod 2=0">
                <xsl:value-of select="'odd'"/>
              </xsl:when>
              <xsl:otherwise>
                <xsl:value-of select="'even'"/>
              </xsl:otherwise>
            </xsl:choose>
          </xsl:attribute>                
        
        <xsl:apply-templates select="col[1]" >
           <xsl:with-param name="cnt" select="1" />
           <xsl:with-param name="rest" select="$rest" />
        </xsl:apply-templates>        
        <xsl:apply-templates select="col[2]" >
           <xsl:with-param name="cnt" select="2" />
           <xsl:with-param name="rest" select="$rest" />             
        </xsl:apply-templates>        
        <xsl:apply-templates select="col[3]" >         
           <xsl:with-param name="cnt" select="3" />
           <xsl:with-param name="rest" select="$rest" />
        </xsl:apply-templates>
        <xsl:element name="td">
          <xsl:attribute name="headers">
             <xsl:value-of select="'COL4'"/> <xsl:value-of select="$rest"/>
          </xsl:attribute>
          <xsl:value-of select="substring(col[7], 0, 11)" />
        </xsl:element>
        
         <xsl:element name="td">
          <xsl:attribute name="headers">
             <xsl:value-of select="'COL5'"/> <xsl:value-of select="$rest"/>
          </xsl:attribute>
          <xsl:value-of select="col[8]" />
          <xsl:text> </xsl:text>
          <xsl:value-of select="col[9]" />
        </xsl:element>
        <xsl:apply-templates select="col[4]" >
           <xsl:with-param name="cnt" select="6" />
           <xsl:with-param name="rest" select="$rest" />
        </xsl:apply-templates>
        <xsl:apply-templates select="col[5]" >
           <xsl:with-param name="cnt" select="7" />
           <xsl:with-param name="rest" select="$rest" />
        </xsl:apply-templates>        
        <xsl:apply-templates select="col[6]" >
           <xsl:with-param name="cnt" select="8" />
           <xsl:with-param name="rest" select="$rest" />
        </xsl:apply-templates>        
      </xsl:element>
    </xsl:template>
</xsl:stylesheet>
