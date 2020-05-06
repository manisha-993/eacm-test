<?xml version="1.0" encoding="UTF-8"?>
<!--
(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.

  $Log: standard-includes.xsl,v $
  Revision 1.9  2008/08/26 21:20:50  wendy
  Compress output

  Revision 1.8  2007/03/07 00:19:28  chris
  changes for generalized XSLReportABR

  Revision 1.7  2006/10/19 21:31:02  chris
  Enhancements for Taxonomy ABR's

  Revision 1.6  2006/02/23 15:50:34  wendy
  Changes for AHE

  Revision 1.5  2006/01/26 15:10:30  wendy
  AHE copyright

  Revision 1.4  2005/10/06 17:56:49  wendy
  New AHE format

  Revision 1.3  2005/09/08 19:11:35  wendy
  New pkg

  Revision 1.3  2005/02/28 14:52:07  wendy
  Alternate row highlighting for mode=ls

  Revision 1.2  2005/02/24 18:54:20  chris
  fix typo

  Revision 1.1  2005/02/23 21:13:02  chris
  Initial XSL Report ABR Code

  -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:xalan="http://xml.apache.org/xslt" xmlns:eann="xalan://com.ibm.transform.oim.eacm.xalan.Eann" 
extension-element-prefixes="eann">
    <xsl:template match="ABRJob">
      <xsl:comment>
        Information about the java code
        - - - - - - - - - - - - - - - -
        ABR Class is <xsl:value-of select="ABRCode/@className" />
        CVS Revision is <xsl:value-of select="ABRCode/@revision" />
      </xsl:comment>

      <xsl:comment>
        Information about the entity instance
        - - - - - - - - - - - - - - - - - - -
        Entity Type is <xsl:value-of select="EntityDescription/@entityType" /> (<xsl:value-of select="EntityDescription" />)
        Entity ID is <xsl:value-of select="EntityDescription/@entityID" />
        Attribute Code is <xsl:value-of select="EntityDescription/AttributeDescription/@code" />
      </xsl:comment>

      <xsl:comment>
        Information about the ABR job
        - - - - - - - - - - - - - - -
        Taskmaster job number is <xsl:value-of select="@jobNumber" />
        Timestamp is <xsl:value-of select="@timestamp" />
      </xsl:comment>

      <xsl:comment>
        Information about who invoked the report
        - - - - - - - - - - - - - - - - - - - - -
        User ID is <xsl:value-of select="UserProfile/@userToken" />
        Enterprise is <xsl:value-of select="UserProfile/ProfileEnterprise/@code" />
        Workgroup is <xsl:value-of select="UserProfile/Workgroup" /> (<xsl:value-of select="UserProfile/@wid" />)
        Role is <xsl:value-of select="UserProfile/Role" /> (<xsl:value-of select="UserProfile/Role/@roleCode" />)
        Read Language is <xsl:value-of select="UserProfile/ReadLanguage" /> (<xsl:value-of select="UserProfile/ReadLanguage/@nlsID" />)
        WriteLanguage is <xsl:value-of select="UserProfile/WriteLanguage" /> (<xsl:value-of select="UserProfile/WriteLanguage/@nlsID" />)
      </xsl:comment>
    </xsl:template>

    <xsl:template match="abr">
      <xsl:comment>
        Information about the java code
        - - - - - - - - - - - - - - - -
        ABR Class is <xsl:value-of select="class" />
        CVS Revision is <xsl:value-of select="revision" />
      </xsl:comment>

      <xsl:comment>
        Information about the entity instance
        - - - - - - - - - - - - - - - - - - -
        Entity Type is <xsl:value-of select="entity/@type" /> (<xsl:value-of select="entity" />)
        Entity ID is <xsl:value-of select="entity/@id" />
        Attribute Code is <xsl:value-of select="code" />
      </xsl:comment>

      <xsl:comment>
        Information about the ABR job
        - - - - - - - - - - - - - - -
        Taskmaster job number is <xsl:value-of select="job-id" />
        Timestamp is <xsl:value-of select="timestamp" />
      </xsl:comment>

      <xsl:comment>
        Information about who invoked the report
        - - - - - - - - - - - - - - - - - - - - -
        User ID is <xsl:value-of select="user-token" />
        Enterprise is <xsl:value-of select="enterprise" />
        Workgroup is <xsl:value-of select="workgroup" /> (<xsl:value-of select="workgroup/@id" />)
        Role is <xsl:value-of select="role" /> (<xsl:value-of select="role/@code" />)
        Read Language is <xsl:value-of select="language/read" /> (<xsl:value-of select="language/read/@id" />)
        WriteLanguage is <xsl:value-of select="language/write" /> (<xsl:value-of select="language/write/@id" />)
      </xsl:comment>
    </xsl:template>
 
    <!-- Variables are instanciated when template is instantiated -->
    <xsl:template name="standardTable">
      <xsl:param name="tableRef" />
      <!-- This will call the query method on the $products instance (Rember methods wit ExpressionContext as first arg take precedence) -->
      <xsl:variable name="table" select="eann:getTableAsXML($tableRef)" />
      <table summary="layout">
        <xsl:apply-templates select="$table/data/metadata" mode="layout" />
        <xsl:apply-templates select="$table/data/row-set" mode="layout" />
      </table>
      <!-- Thinking of using this for dereference -->
      <xsl:value-of select="eann:dereference($tableRef)" />
    </xsl:template>

    <!-- Override this when you need to concatenate two columns or change the column order -->
    <xsl:template match="row">
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
        <xsl:apply-templates select="col" />
      </xsl:element>
    </xsl:template> 

    <!-- base and cnt are used to generate a headers tag attribute, rest is any other value that needs to be in the attr -->
    <xsl:template match="col">
      <xsl:param name="cnt" select="position()" />
      <xsl:param name="rest" select="''" />
      <xsl:param name="base" select="'COL'" />
      <xsl:element name="td">
          <!-- AHE accessibility requires headers attribute if th is in the table -->
          <xsl:attribute name="headers">
             <xsl:value-of select="$base"/><xsl:value-of select="$cnt"/> <xsl:value-of select="$rest"/>
          </xsl:attribute>
        <xsl:apply-templates mode="no-td" select="." />
      </xsl:element>
    </xsl:template>

    <xsl:template match="col" mode="no-td">
      <xsl:choose>
        <xsl:when test="@type='X'">
          <xsl:value-of select="text()"  disable-output-escaping="yes" />
        </xsl:when>
        <xsl:when test="@type='T'">
          <xsl:value-of select="translate(text(),'&#34;','&quot;')" />
        </xsl:when>
        <xsl:when test="@type='F'">
          <xsl:apply-templates select="flag" />
        </xsl:when>
        <xsl:when test="@type='Not Populated'">
          <em>** <xsl:value-of select="@type" /> **</em>
        </xsl:when>
        <xsl:when test="@type='Unhandled Type'">
          <em>** <xsl:value-of select="@type" /> **</em>
        </xsl:when>
      </xsl:choose>
    </xsl:template>
    
    <xsl:template match="flag">
      <xsl:value-of select="translate(text(),'&#34;','&quot;')" />
      <xsl:if test="position() != last()">
        <br/>
      </xsl:if>
    </xsl:template>

    <xsl:template match="metadata">
        <tr style="background-color:#aaa;">
          <xsl:apply-templates select="column-header"/>
        </tr>
    </xsl:template>
    
    <!-- base and cnt are used to generate a unique id tag attribute, rest is any other value that needs to be in the id attr -->
    <xsl:template match="column-header">
      <xsl:param name="cnt" select="position()" />
      <xsl:param name="rest" select="''" />
      <xsl:param name="base" select="'COL'" />
      <xsl:element name="th">
          <!-- AHE accessibility requires id attribute if th is in the table -->
          <xsl:attribute name="id">
             <xsl:value-of select="$base"/><xsl:value-of select="$cnt"/> <xsl:value-of select="$rest"/>
         </xsl:attribute>
          <xsl:value-of select="translate(text(),'&#34;','&quot;')" />
      </xsl:element>
    </xsl:template>
    
    <xsl:template match="metadata" mode="layout">
        <tr>
          <xsl:apply-templates select="column-header" mode="layout"/>
        </tr>
    </xsl:template> 
      
    <xsl:template match="column-header" mode="layout">
      <xsl:element name="td">
          <b><xsl:value-of select="translate(text(),'&#34;','&quot;')" /></b>
      </xsl:element>
    </xsl:template>
    
    <xsl:template match="entity" mode="two-column-layout">
      <xsl:param name="uniqueID" select="concat(../@entityType,@entityID)" />
      <xsl:param name="header-class">grey-dark</xsl:param>
      <table>
        <tr  class="{$header-class}">
         <th id="n1{$uniqueID}">Name</th>
         <th id="v1{$uniqueID}">Value</th>
         <th id="n2{$uniqueID}">Name</th>
         <th id="v2{$uniqueID}">Value</th>
        </tr>
        <!-- the midpoint is 4 when there are 7 or 8 attributes -->
        <xsl:variable name="midpoint" select="ceiling(count(attribute) div 2)" />
        <xsl:apply-templates select="attribute">
          <xsl:with-param name="uniqueID" select="$uniqueID"></xsl:with-param>
          <xsl:with-param name="midpoint" select="$midpoint"></xsl:with-param>
        </xsl:apply-templates>
      </table>
    </xsl:template>
    
    <xsl:template match="attribute">
      <xsl:param name="uniqueID"></xsl:param>
      <xsl:param name="midpoint"></xsl:param>
      <xsl:param name="meta" select="../../meta" />
      <!-- attributes up to and including the midpoint (1, 2, 3, 4) 
           are in the first column. they also start each row.
           we don't output a row until end of list 
      -->
      <xsl:variable name="code" select="@code" />
      <xsl:comment>order of <xsl:value-of select="$meta/description[@attributecode = $code]"/> (<xsl:value-of select="$code" />) is <xsl:value-of select="@order" />, position is <xsl:value-of select="position()" /></xsl:comment>
      <xsl:if test="position() &lt;= $midpoint" >
        <xsl:variable name="rowClass">
          <xsl:choose>
            <xsl:when test="position() mod 2 = 1">odd</xsl:when>
            <xsl:otherwise>even</xsl:otherwise>
          </xsl:choose>
        </xsl:variable>
        <!-- the second column of attributes is after the midpoint 
             (5, 6, 7, 8) so the midpoint plus the current position 
             is the second column of attributes -->
        <xsl:variable name="indexNext" select="position() + $midpoint" />
        <xsl:comment>
          This row is <xsl:value-of select="position()"/> and <xsl:value-of select="$indexNext"/>
          of <xsl:value-of select="count(../attribute)"/> attributes. 
          With <xsl:value-of select="count(following-sibling::attribute)"/> siblings.
        </xsl:comment>
        <!-- had to put sort in XMLABR because  
             ../attribute[$indexNext] and following-sibling::attribute[$midpoint] 
             always give you document order instead of the sort order
        -->
        <xsl:variable name="nextAttribute" select="following-sibling::attribute[position() = $midpoint]" />
        <tr class="{$rowClass}">
          <td headers="n1{$uniqueID}"><b><xsl:value-of select="$meta/description[@attributecode = $code]"/>:</b></td>
          <td headers="v1{$uniqueID}"><xsl:value-of select="."/></td>
          <!-- for an odd number of attributes the last value in the 
               second column should just be space -->
          <xsl:choose>
          <!-- if I had a template to output column 2 I could sort the list again -->
          <xsl:when test="$indexNext &lt;= last()">
          <td headers="n2{$uniqueID}"><b><xsl:value-of select="$meta/description[@attributecode = $nextAttribute/@code]"/>:</b></td>
          <td headers="v2{$uniqueID}"><xsl:value-of select="$nextAttribute"/></td>
          </xsl:when>
          <xsl:otherwise>
          <td headers="n2{$uniqueID}"><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text></td>
          <td headers="v2{$uniqueID}"><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text></td>
          </xsl:otherwise>
          </xsl:choose>
        </tr>
      </xsl:if>
    </xsl:template>    

    <xsl:template name="logic-development">
      <xsl:param name="abrDevDoc" ></xsl:param>
      <h2>Test elements</h2>
      <ol>
      <xsl:apply-templates select="$abrDevDoc/test" mode="development"/>
      </ol>
      <h2>Text elements</h2>
      <ol>
      <xsl:apply-templates select="$abrDevDoc/text" mode="development"/>
      </ol>
      <h2>Data elements</h2>
      <xsl:apply-templates select="$abrDevDoc/data/group" mode="development"/>
    </xsl:template>
    
    <xsl:template match="test" mode="development">
      <li><xsl:value-of select="@method" /> returned <xsl:value-of select="@result" /></li>
    </xsl:template>

    <xsl:template match="text" mode="development">
      <li><xsl:value-of select="@method" /> returned <xsl:value-of select="." /></li>
    </xsl:template>

    <xsl:template match="group" mode="development">
      <h3><xsl:value-of select="@entityType"/></h3>
      <xsl:apply-templates select="entity" mode="two-column-layout"/>
    </xsl:template>
</xsl:stylesheet>
