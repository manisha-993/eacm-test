<?xml version="1.0" encoding="UTF-8"?>
<!--
(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.

  $Log: WWCCByAvlWW_CURInclude.xsl,v $
  Revision 1.4  2006/02/23 15:50:34  wendy
  Changes for AHE

  Revision 1.3  2006/01/26 15:11:43  wendy
  AHE copyright

  Revision 1.2  2005/10/06 17:57:06  wendy
  New AHE format

  Revision 1.1  2005/09/08 19:12:05  wendy
  New pkg

  Revision 1.2  2005/04/13 18:39:13  chris
  Fix for TIR 6BDJCX. Added Title.

  Revision 1.1  2005/03/02 19:07:43  chris
  Report - Worldwide In-Country Courses Sorted by Available Worldwide Course

  -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="1.0"
    xmlns:xalan="http://xml.apache.org/xslt"
    xmlns:eann="xalan://com.ibm.transform.oim.eacm.xalan.Eann" 
    xmlns:table="xalan://com.ibm.transform.oim.eacm.xalan.table.ls.CURtoWWCCTable"
    xmlns:meta="xalan://com.ibm.transform.oim.eacm.xalan.table.MetaFlagTable"
    extension-element-prefixes="eann"
    exclude-result-prefixes="eann table meta">

    <xsl:import href="/com/ibm/transform/oim/eacm/xalan/style/eacustom.xsl" />
    <xsl:import href="/com/ibm/transform/oim/eacm/xalan/style/standard-includes.xsl" />

    <xsl:param name="tableCURWWCC" />
    <xsl:param name="metaTable" />
    <xsl:param name="extract">EXCURWWCC0010</xsl:param>

    <xsl:variable name="wwcc" select="eann:new($tableCURWWCC)" />
    <xsl:variable name="lifecycle" select="eann:new($metaTable)" />

    <xsl:template match="/">
      <html xml:lang="en" lang="en">
        <head>
          <xsl:call-template name="metaTags">
            <xsl:with-param name="desc" select="'WWCCByAvlWW_CUR'"/>
          </xsl:call-template>
          <xsl:call-template name="css"/>
          <title>e-announce | Worldwide In-Country Courses Sorted by Available Worldwide Course</title>
        </head>
        <body id="w3-ibm-com">
            <xsl:call-template name="masthead"/>
            <!-- start content //////////////////////////////////////////// -->
            <div id="content">
                <!-- start main content -->
                <div id="content-main">        
                <xsl:comment>
                  set extract to <xsl:value-of select="table:setExtract($tableCURWWCC, $extract)"/>
                  set meta attribute to <xsl:value-of select="meta:setAttributeCode($metaTable, 'LSWWCCLIFECYCLE')"/>
                </xsl:comment>

                <xsl:apply-templates select="abr" />

                <h1>Worldwide In-Country Courses per Curriculum by Available WW Course</h1>
                <p>The current language is <xsl:value-of select="abr/language/read" /></p>
                <table cellspacing="1" cellpadding="0" class="basic-table" summary="Worldwide courses">
                  <xsl:call-template name="wwTemplate" >
                    <xsl:with-param name="tableRef" select="$wwcc"/>
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

    <xsl:template match="row" mode="ww">
       <xsl:param name="rest" select="''" />
     <!-- report order
        LSWWID - Worldwide Course Code
        LSCRSID - In-Country Course Code
        LSCRGLOBALREPTITLE - Title
        LSCRSEXPDATE - Expiration Date
        LSWWEXPDATE - Worldwide Course Expiration Date
        LSWWCCCOUNTRY - Country ID
        LSCRSDELIVERY - Delivery Method
        LSCRSSUBDELIVERY - Sub Delivery Method
        LSCRSMEDIA - Media
        LSCRSDURATION - Duration
        LSCRSDURATIONUNITS - Duration Units
      -->

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
        <xsl:apply-templates select="col[4]" >
           <xsl:with-param name="cnt" select="2" />
           <xsl:with-param name="rest" select="$rest" />
        </xsl:apply-templates>          
        <xsl:apply-templates select="col[5]"  >
           <xsl:with-param name="cnt" select="3" />
           <xsl:with-param name="rest" select="$rest" />
        </xsl:apply-templates>                  
        <xsl:apply-templates select="col[6]"  >
           <xsl:with-param name="cnt" select="4" />
           <xsl:with-param name="rest" select="$rest" />
        </xsl:apply-templates>        
        <xsl:apply-templates select="col[2]"  >
           <xsl:with-param name="cnt" select="5" />
           <xsl:with-param name="rest" select="$rest" />
        </xsl:apply-templates>
        <xsl:apply-templates select="col[7]"  >
           <xsl:with-param name="cnt" select="6" />
           <xsl:with-param name="rest" select="$rest" />
        </xsl:apply-templates>          
        <xsl:apply-templates select="col[8]"  >
           <xsl:with-param name="cnt" select="7" />
           <xsl:with-param name="rest" select="$rest" />
        </xsl:apply-templates>                  
        <xsl:apply-templates select="col[9]"  >
           <xsl:with-param name="cnt" select="8" />
           <xsl:with-param name="rest" select="$rest" />
        </xsl:apply-templates>          
        <xsl:apply-templates select="col[10]"  >
           <xsl:with-param name="cnt" select="9" />
           <xsl:with-param name="rest" select="$rest" />
        </xsl:apply-templates> 
        <xsl:apply-templates select="col[11]"  >
           <xsl:with-param name="cnt" select="10" />
           <xsl:with-param name="rest" select="$rest" />
        </xsl:apply-templates>                  
        <xsl:apply-templates select="col[12]"  >
           <xsl:with-param name="cnt" select="11" />
           <xsl:with-param name="rest" select="$rest" />
        </xsl:apply-templates>                  
      </xsl:element>
    </xsl:template>

    <!-- override 'meta-data' defined in standard-includes.xsl to output columns in a different order and concatinate  WWID and title -->
    <xsl:template match="metadata" >
        <tr style="background-color:#aaa;">
          <!-- Get column-label attribute from each column-header-->
          <xsl:apply-templates select="column-header[1]" ><!-- LSWWID - Worldwide Course Code -->
            <xsl:with-param name="cnt" select="1" />
          </xsl:apply-templates>
          <xsl:apply-templates select="column-header[4]" ><!-- LSCRSID In-Country Course Code -->
            <xsl:with-param name="cnt" select="2" />
          </xsl:apply-templates>
          <xsl:apply-templates select="column-header[5]" ><!-- LSCRGLOBALREPTITLE Title -->
            <xsl:with-param name="cnt" select="3" />
          </xsl:apply-templates>          
          <xsl:apply-templates select="column-header[6]" ><!-- LSCRSEXPDATE Expiration Date -->
            <xsl:with-param name="cnt" select="4" />
          </xsl:apply-templates>
          <xsl:apply-templates select="column-header[2]" ><!-- LSWWEXPDATE - Worldwide Course Expiration Date -->
            <xsl:with-param name="cnt" select="5" />
          </xsl:apply-templates>
          <xsl:apply-templates select="column-header[7]" ><!-- LSWWCCCOUNTRY Country ID -->
            <xsl:with-param name="cnt" select="6" />
          </xsl:apply-templates>
          <xsl:apply-templates select="column-header[8]" ><!-- LSCRSDELIVERY Delivery Method -->
            <xsl:with-param name="cnt" select="7" />
          </xsl:apply-templates>
          <xsl:apply-templates select="column-header[9]" ><!-- LSCRSSUBDELIVERY Sub Delivery Method -->
            <xsl:with-param name="cnt" select="8" />
          </xsl:apply-templates>
          <xsl:apply-templates select="column-header[10]" ><!-- LSCRSMEDIA Media -->
            <xsl:with-param name="cnt" select="9" />
          </xsl:apply-templates>
          <xsl:apply-templates select="column-header[11]" ><!-- LSCRSDURATION Duration -->
            <xsl:with-param name="cnt" select="10" />
          </xsl:apply-templates>
          <xsl:apply-templates select="column-header[12]" ><!-- LSCRSDURATIONUNITS Duration Units -->
            <xsl:with-param name="cnt" select="11" />
          </xsl:apply-templates>
        </tr>
    </xsl:template>
</xsl:stylesheet>
