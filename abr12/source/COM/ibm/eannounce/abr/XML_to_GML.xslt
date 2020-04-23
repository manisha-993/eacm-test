<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">
  <xsl:output method="text" indent="no" encoding="iso-8859-1"/>
  <xsl:strip-space elements="*"/>

  <!--========== defined variables =========================================-->
  <xsl:variable name="newLine">
<xsl:text>
</xsl:text>
  </xsl:variable>
  <xsl:variable name="lineMax" select="70"/> 
  <xsl:variable name="break" select="' '"/> 

  <!--======================================================================-->
  <!-- Template for the document element.                                   -->
  <!--======================================================================-->
  <!--========== process document element: <eAnnounceData> tag =============-->
  <xsl:template match="eAnnounceData">
    <xsl:apply-templates/>
  </xsl:template>

  <!--======================================================================-->
  <!-- Templates for headings.                                              -->
  <!--======================================================================-->
  <!--========== process <h1> tags =========================================-->
  <xsl:template match="h1">
    <xsl:text>:h1.</xsl:text>
    <xsl:apply-templates/>
  </xsl:template>

  <!--========== process <h2> tags =========================================-->
  <xsl:template match="h2">
    <xsl:text>:h2.</xsl:text>
    <xsl:apply-templates/>
  </xsl:template>

  <!--========== process <h3> tags =========================================-->
  <xsl:template match="h3">
    <xsl:text>:h3.</xsl:text>
    <xsl:apply-templates/>
  </xsl:template>

  <!--========== process <h4> tags =========================================-->
  <xsl:template match="h4">
    <xsl:text>:h5.</xsl:text>
    <xsl:apply-templates/>
  </xsl:template>

  <!--========== process <h5> tags =========================================-->
  <xsl:template match="h5">
    <xsl:text>:h5.</xsl:text>
    <xsl:apply-templates/>
  </xsl:template>

  <!--======================================================================-->
  <!-- Templates for tables.                                                -->
  <!--======================================================================-->
  <!--========== process <table> tags ======================================-->
  <xsl:template match="table">
    <xsl:text>:rdef id=table cwidths=&apos;</xsl:text>
    <!-- output an asterisk for each column -->
    <xsl:variable name="columnAsterisks">
      <xsl:choose>
        <xsl:when test="child::tablevar/child::tr/child::td">
          <xsl:for-each select="child::tablevar/child::tr[last()]/child::td">
            <xsl:text>* </xsl:text>
          </xsl:for-each>
        </xsl:when>
        <xsl:otherwise>
          <xsl:for-each select="child::tr[last()]/child::td">
            <xsl:text>* </xsl:text>
          </xsl:for-each>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:value-of select="normalize-space($columnAsterisks)"/>
    <xsl:text>&apos;</xsl:text>
    <xsl:value-of select="$newLine"/>
    <xsl:text>:table refid=table split=yes.</xsl:text>
    <xsl:value-of select="$newLine"/>
    <xsl:apply-templates/>
    <xsl:text>:etable.</xsl:text>
    <xsl:value-of select="$newLine"/>
  </xsl:template>

  <!--========== process <tr> tags =========================================-->
  <xsl:template match="tr">
    <xsl:choose>
      <xsl:when test="child::th">
        <xsl:text>:thd.</xsl:text>
        <xsl:value-of select="$newLine"/>
        <xsl:apply-templates/>
        <xsl:text>:ethd.</xsl:text>
        <xsl:value-of select="$newLine"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:text>:row.</xsl:text>
        <xsl:value-of select="$newLine"/>
        <xsl:apply-templates/>
        <xsl:text>:erow.</xsl:text>
        <xsl:value-of select="$newLine"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <!--========== process <th> or <td> tags =================================-->
  <xsl:template match="th|td">
    <xsl:text>:c.</xsl:text>
    <xsl:apply-templates/>
    <xsl:text>:ec.</xsl:text>
    <xsl:value-of select="$newLine"/>
  </xsl:template>

  <!--======================================================================-->
  <!-- Templates for lists.                                                 -->
  <!--======================================================================-->
  <!--========== process <ol> tags =========================================-->
  <xsl:template match="ol">
    <xsl:choose>
      <xsl:when test="@compact='y'">
        <xsl:text>:ol compact.</xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <xsl:text>:ol.</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:value-of select="$newLine"/>
    <xsl:apply-templates/>
    <xsl:text>:eol.</xsl:text>
    <xsl:value-of select="$newLine"/>
  </xsl:template>

  <!--========== process <sl> tags =========================================-->
  <xsl:template match="sl">
    <xsl:choose>
      <xsl:when test="@compact='y'">
        <xsl:text>:sl compact.</xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <xsl:text>:sl.</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:value-of select="$newLine"/>
    <xsl:apply-templates/>
    <xsl:text>:esl.</xsl:text>
    <xsl:value-of select="$newLine"/>
  </xsl:template>

  <!--========== process <ul> tags =========================================-->
  <xsl:template match="ul">
    <xsl:choose>
      <xsl:when test="@compact='y'">
        <xsl:text>:ul compact.</xsl:text>
      </xsl:when>
      <xsl:otherwise>
        <xsl:text>:ul.</xsl:text>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:value-of select="$newLine"/>
    <xsl:apply-templates/>
    <xsl:text>:eul.</xsl:text>
    <xsl:value-of select="$newLine"/>
  </xsl:template>

  <!--========== process <li> tags =========================================-->
  <xsl:template match="li">
    <xsl:text>:li.</xsl:text>
    <xsl:apply-templates/>
  </xsl:template>

  <!--======================================================================-->
  <!-- Templates for formatting text.                                       -->
  <!--======================================================================-->
  <!--========== process <i> tags ==========================================-->
  <xsl:template match="i">
    <xsl:text>:hp1.</xsl:text>
    <xsl:apply-templates/>
    <xsl:text>:ehp1.</xsl:text>
    <xsl:value-of select="$newLine"/>
  </xsl:template>

  <!--========== process <b> tags ==========================================-->
  <xsl:template match="b">
    <xsl:text>:hp2.</xsl:text>
    <xsl:apply-templates/>
    <xsl:text>:ehp2.</xsl:text>
    <xsl:value-of select="$newLine"/>
  </xsl:template>

  <!--========== process <u> tags (not allowed downstream in VM) ===========-->
  <xsl:template match="u">
    <xsl:apply-templates/>
  </xsl:template>

  <!--========== process <p> tags ==========================================-->
  <xsl:template match="p">
    <xsl:text>:p.</xsl:text>
    <xsl:value-of select="$newLine"/>
    <xsl:apply-templates/>
  </xsl:template>

  <!--========== process <br> tags =========================================-->
  <xsl:template match="br">
    <xsl:text>.br</xsl:text>
    <xsl:value-of select="$newLine"/>
    <xsl:apply-templates/>
  </xsl:template>

  <!--========== process <pre> tags ========================================-->
  <xsl:template match="pre">
    <xsl:text>:xmp.</xsl:text>
    <xsl:value-of select="$newLine"/>
    <xsl:text>.kp off</xsl:text>
    <xsl:value-of select="$newLine"/>
    <xsl:apply-templates/>
    <xsl:text>:exmp.</xsl:text>
    <xsl:value-of select="$newLine"/>
  </xsl:template>

  <!--========== process <pre/text()> tags ================================-->
  <xsl:template match="pre/text()">
    <xsl:value-of select="."/>
    <xsl:value-of select="$newLine"/>
  </xsl:template>

  <!--======================================================================-->
  <!-- Template to handle unknown tags.                                     -->
  <!--======================================================================-->
  <!--========== process unknown tags ======================================-->
  <xsl:template match="*">
    <xsl:text>.**** UNKNOWN TAG: </xsl:text>
    <xsl:value-of select="name()"/>
    <xsl:value-of select="$newLine"/>
    <xsl:apply-templates/>
  </xsl:template>

  <!--======================================================================-->
  <!-- Template to output comments.                                         --> 
  <!--======================================================================-->
  <!--========== output the text of the node ===============================-->
  <xsl:template match="comment()">
    <xsl:text>.*</xsl:text>
    <xsl:value-of select="normalize-space(.)"/>
    <xsl:value-of select="$newLine"/>
  </xsl:template>

  <!--======================================================================-->
  <!-- Template to output text.  If the text string is longer than the      --> 
  <!-- allowed maximum (lineMax) then call the breakText template.          -->
  <!--======================================================================-->
  <!--========== output the text of the node ===============================-->
  <xsl:template match="text()">
    <xsl:variable name="textString">
      <xsl:value-of select="normalize-space(.)"/>
    </xsl:variable>
    <xsl:if test="contains(substring($textString,1,1),'.') or
                  contains(substring($textString,1,1),':')">
      <xsl:text>.ct</xsl:text>
    </xsl:if>
    <xsl:value-of select="$textString"/>
    <xsl:value-of select="$newLine"/>
  </xsl:template>

</xsl:stylesheet>
