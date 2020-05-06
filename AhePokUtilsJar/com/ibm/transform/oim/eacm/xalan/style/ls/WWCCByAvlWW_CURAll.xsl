<?xml version="1.0" encoding="UTF-8"?>
<!--
(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.

  $Log: WWCCByAvlWW_CURAll.xsl,v $
  Revision 1.4  2006/02/23 15:50:34  wendy
  Changes for AHE

  Revision 1.3  2006/01/26 15:11:43  wendy
  AHE copyright

  Revision 1.2  2005/10/06 17:57:06  wendy
  New AHE format

  Revision 1.1  2005/09/08 19:12:05  wendy
  New pkg

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

    <xsl:import href="/com/ibm/transform/oim/eacm/xalan/style/ls/WWCCByAvlWW_CURInclude.xsl" />
    <xsl:output method="xml" encoding="UTF-8" indent="yes" omit-xml-declaration="yes" 
       doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" 
       doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"/>
       
    <xsl:template name="wwTemplate">
      <xsl:param name="tableRef" />
      <xsl:variable name="table" select='eann:getTableAsXML($tableRef)' />
      <!--
        column 01 is LSWWID – Worldwide Course Code
        column 02 is LSWWEXPDATE – Worldwide Course Expiration Date
        column 03 is LSWWCCLIFECYCLE flag code
        column 04 is LSCRSID In-Country Course Code
        column 05 is LSCRGLOBALREPTITLE Title
        column 06 is LSCRSEXPDATE Expiration Date
        column 07 is LSWWCCCOUNTRY Country ID
        column 08 is LSCRSDELIVERY Delivery Method
        column 09 is LSCRSSUBDELIVERY Sub Delivery Method
        column 10 is LSCRSMEDIA Media
        column 11 is LSCRSDURATION Duration
        column 12 is LSCRSDURATIONUNITS Duration Units
      -->
      <xsl:variable name="meta" select='eann:getTableAsXML($lifecycle)' />
      
      <xsl:apply-templates select="$table/data/metadata"/>

      <xsl:for-each select="$meta/data/row-set/row">
        <xsl:sort select="col[3]"/>

        <xsl:variable name="flagCode" select="col[1]"/>

        <tr style="background-color:#cef;"><th colspan="11"><xsl:value-of select="col[3]" /></th></tr>
        
        <xsl:if test="count($table/data/row-set/row[col[3]=$flagCode]) = 0">
          <tr class="odd"><td colspan="11">None</td></tr>
        </xsl:if>

        <xsl:apply-templates select="$table/data/row-set/row[col[3]=$flagCode]" mode="ww">
          <xsl:sort select="col[1]"/>
        </xsl:apply-templates>
      </xsl:for-each>

      <xsl:comment>
        meta dereferenced=<xsl:value-of select="eann:dereference($lifecycle)" />
        data dereferenced=<xsl:value-of select="eann:dereference($tableRef)" />
      </xsl:comment>
    </xsl:template>
</xsl:stylesheet>
