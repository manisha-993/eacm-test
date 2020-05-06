<?xml version="1.0" encoding="UTF-8"?>
<!--
(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.

  $Log: eacustom.xsl,v $
  Revision 1.4  2006/02/23 15:50:34  wendy
  Changes for AHE

  Revision 1.3  2006/01/26 15:10:30  wendy
  AHE copyright

  Revision 1.2  2005/10/06 17:56:48  wendy
  New AHE format

  Revision 1.1  2005/09/08 18:20:31  wendy
  new pkg

  Revision 1.2  2005/02/24 18:55:51  wendy
  Added missing style

  Revision 1.1  2005/02/23 21:13:02  chris
  Initial XSL Report ABR Code

  -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:xalan="http://xml.apache.org/xslt">
<xsl:template name="css">
<link rel="stylesheet" type="text/css" href="http://w3.ibm.com/ui/v8/css/v4-screen.css" />
<link rel="stylesheet" type="text/css" href="http://w3.ibm.com/ui/v8/css/v4-interior.css" />
<style type="text/css" media="all">
<xsl:comment>
@import url("http://w3.ibm.com/ui/v8/css/screen.css");
@import url("http://w3.ibm.com/ui/v8/css/interior.css");
@import url("http://w3.ibm.com/ui/v8/css/popup-window.css");
@import url("http://w3.ibm.com/ui/v8/css/tables.css");
</xsl:comment>
</style>
</xsl:template>

<xsl:template name="metaTags">
<xsl:param name="desc" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="e-announce" />
<meta name="owner" content="opicma@us.ibm.com" />
<meta name="robots" content="noindex,nofollow" />
<meta name="security" content="internal use only" />
<meta name="source" content="v8 Template Generator" />
<meta name="ibm.Country" content="ZZ" />
<xsl:element name="meta">
  <xsl:attribute name="name">
    <xsl:value-of select="'dc.date'"/>
  </xsl:attribute>  
  <xsl:attribute name="scheme">
    <xsl:value-of select="'iso8601'"/>
  </xsl:attribute>  
  <xsl:attribute name="content">
    <xsl:value-of select="substring(//abr/timestamp,1,10)"/>
  </xsl:attribute>
</xsl:element>

<xsl:element name="meta">
  <xsl:attribute name="name">
    <xsl:value-of select="'description'"/>
  </xsl:attribute>  
  <xsl:attribute name="content">
    <xsl:value-of select="$desc"/>
  </xsl:attribute>
</xsl:element>

<meta name="dc.language" scheme="rfc1766" content="en-US" />
<meta name="dc.rights" content="Copyright (c) 2000,2006 by IBM Corporation"  />
<meta name="feedback" content="opicma@us.ibm.com" />
</xsl:template>

<xsl:template name="footer">
<!-- start popup footer //////////////////////////////////////////// -->
<div id="popup-footer">
  <div class="hrule-dots">&#160;</div>
    <div class="content">
      <div style="padding:0 1em 0.4em 34px; float:right;">
       <a class="popup-print-link" style="float:none;" href="javascript:print();">Print</a><a style="padding:0 1em 0.4em 4px;" href="javascript:close();">Close Window</a>
      </div>        
    </div>
  <div style="clear:both;">&#160;</div>
</div>
<!-- stop popup footer //////////////////////////////////////////// -->
<script language="JavaScript" type="text/javaScript">
    function openTOU() {
      window.open("http://w3.ibm.com/w3/info_terms_of_use.html", "TOU", "dependent,width=800,height=600,screenX=100,screenY=100,left=100,top=100,titlebar,scrollbars,status,resizable");
    }
</script>
<p class="terms"><a href="javascript:openTOU();">Terms of use</a></p>
</xsl:template>

<xsl:template name="masthead">
<!-- start popup masthead //////////////////////////////////////////// -->
<div id="popup-masthead">
    <img id="popup-w3-sitemark" src="//w3.ibm.com/ui/v8/images/id-w3-sitemark-small.gif" alt="" width="182" height="26" />
</div>
<!-- stop popup masthead //////////////////////////////////////////// -->
</xsl:template>
</xsl:stylesheet>
