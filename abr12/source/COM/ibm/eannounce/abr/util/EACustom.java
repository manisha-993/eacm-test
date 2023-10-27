/*     */ package COM.ibm.eannounce.abr.util;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EACustom
/*     */ {
/*  63 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*     */   
/*  65 */   public static final String NEWLINE = new String(FOOL_JTEST);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getCSS() {
/*  87 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDocTypeHtml() {
/*  97 */     return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" + NEWLINE + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getTitle(String paramString) {
/* 110 */     return "<title>EACM | " + paramString + "</title>";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMetaTags(String paramString) {
/* 121 */     StringBuffer stringBuffer = new StringBuffer();
/* 122 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 123 */     stringBuffer.append(NEWLINE + "<base href=\"http://\" />" + NEWLINE);
/* 124 */     stringBuffer.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" + NEWLINE);
/* 125 */     stringBuffer.append("<link rel=\"schema.DC\" href=\"http://purl.org/DC/elements/1.0/\"/>" + NEWLINE);
/* 126 */     stringBuffer.append("<link rel=\"SHORTCUT ICON\" href=\"http://w3.ibm.com/favicon.ico\"/>" + NEWLINE);
/* 127 */     stringBuffer.append("<meta name=\"description\" content=\"" + paramString + "\" />" + NEWLINE);
/* 128 */     stringBuffer.append("<meta name=\"keywords\" content=\"EACM\" />" + NEWLINE);
/* 129 */     stringBuffer.append("<meta name=\"owner\" content=\"opicma@us.ibm.com\" />" + NEWLINE);
/* 130 */     stringBuffer.append("<meta name=\"robots\" content=\"noindex,nofollow\" />" + NEWLINE);
/* 131 */     stringBuffer.append("<meta name=\"security\" content=\"internal use only\" />" + NEWLINE);
/* 132 */     stringBuffer.append("<meta name=\"source\" content=\"Template Generator\" />" + NEWLINE);
/* 133 */     stringBuffer.append("<meta name=\"IBM.Country\" content=\"ZZ\" />" + NEWLINE);
/* 134 */     stringBuffer.append("<meta name=\"dc.date\" scheme=\"iso8601\" content=\"" + simpleDateFormat
/* 135 */         .format(new Date()) + "\" />" + NEWLINE);
/* 136 */     stringBuffer.append("<meta name=\"dc.language\" scheme=\"rfc1766\" content=\"en-US\" />" + NEWLINE);
/* 137 */     stringBuffer.append("<meta name=\"dc.rights\" content=\"Copyright (c) 2000,2014 by IBM Corporation\"  />" + NEWLINE);
/* 138 */     stringBuffer.append("<meta name=\"feedback\" content=\"opicma@us.ibm.com\" />" + NEWLINE);
/* 139 */     stringBuffer.append("<link href=\"//1.w3.s81c.com/common/v17/css/w3.css\" rel=\"stylesheet\" title=\"w3\" type=\"text/css\" />" + NEWLINE);
/* 140 */     stringBuffer.append("<script src=\"//1.w3.s81c.com/common/js/dojo/w3.js\" type=\"text/javascript\">//</script>" + NEWLINE);
/*     */     
/* 142 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMastheadDiv() {
/* 151 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     stringBuffer.append("<div id=\"ibm-top\" class=\"ibm-popup\">" + NEWLINE);
/* 164 */     stringBuffer.append("<!-- MASTHEAD_BEGIN -->" + NEWLINE);
/* 165 */     stringBuffer.append("<div id=\"ibm-masthead\">" + NEWLINE);
/* 166 */     stringBuffer.append("<div id=\"ibm-mast-options\"><ul>" + NEWLINE);
/* 167 */     stringBuffer.append("<li id=\"ibm-home\"><a href=\"http://w3.ibm.com\">w3</a></li>" + NEWLINE);
/* 168 */     stringBuffer.append("<li id=\"ibm-title\">EACM | ABR</li>" + NEWLINE);
/* 169 */     stringBuffer.append("</ul></div>" + NEWLINE);
/* 170 */     stringBuffer.append("<div id=\"ibm-universal-nav\">" + NEWLINE);
/* 171 */     stringBuffer.append("<p id=\"ibm-site-title\"><em>site title</em></p>" + NEWLINE);
/* 172 */     stringBuffer.append("<ul id=\"ibm-menu-links\">" + NEWLINE);
/* 173 */     stringBuffer.append("<li><a href=\"http://w3.ibm.com/sitemap/us/en/\">Site map</a></li>" + NEWLINE);
/* 174 */     stringBuffer.append("</ul>" + NEWLINE);
/* 175 */     stringBuffer.append("<div id=\"ibm-search-module\">" + NEWLINE);
/* 176 */     stringBuffer.append("<form method=\"get\" action=\"http://w3.ibm.com/search/do/search\" id=\"ibm-search-form\">" + NEWLINE);
/* 177 */     stringBuffer.append("<p>" + NEWLINE);
/* 178 */     stringBuffer.append("<label for=\"q\"><span class=\"ibm-access\">Search</span></label>" + NEWLINE);
/* 179 */     stringBuffer.append("<input id=\"q\" maxlength=\"100\" name=\"qt\" type=\"text\" value=\"\" />" + NEWLINE);
/* 180 */     stringBuffer.append("<input name=\"v\" type=\"hidden\" value=\"17\"/>" + NEWLINE);
/* 181 */     stringBuffer.append("<input value=\"Submit\" class=\"ibm-btn-search\" id=\"ibm-search\" type=\"submit\"/>" + NEWLINE);
/* 182 */     stringBuffer.append("</p>" + NEWLINE);
/* 183 */     stringBuffer.append("</form>" + NEWLINE);
/* 184 */     stringBuffer.append("</div>" + NEWLINE);
/* 185 */     stringBuffer.append("</div>" + NEWLINE);
/* 186 */     stringBuffer.append("</div>" + NEWLINE);
/* 187 */     stringBuffer.append("<!-- MASTHEAD_END -->" + NEWLINE);
/* 188 */     stringBuffer.append("<!-- LEADSPACE_BEGIN -->" + NEWLINE);
/* 189 */     stringBuffer.append("<div id=\"ibm-leadspace-head\" class=\"ibm-alternate\">" + NEWLINE);
/* 190 */     stringBuffer.append("<div id=\"ibm-leadspace-body\">" + NEWLINE);
/* 191 */     stringBuffer.append("</div>" + NEWLINE);
/* 192 */     stringBuffer.append("</div>" + NEWLINE);
/* 193 */     stringBuffer.append("<!-- LEADSPACE_END -->" + NEWLINE);
/* 194 */     stringBuffer.append("<div id=\"ibm-pcon\">" + NEWLINE);
/* 195 */     stringBuffer.append("<!-- CONTENT_BEGIN -->" + NEWLINE);
/* 196 */     stringBuffer.append("<div id=\"ibm-content\">" + NEWLINE);
/*     */     
/* 198 */     stringBuffer.append("<!-- CONTENT_BODY -->" + NEWLINE);
/* 199 */     stringBuffer.append("<div id=\"ibm-content-body\">" + NEWLINE);
/* 200 */     stringBuffer.append("<div id=\"ibm-content-main\">" + NEWLINE);
/*     */     
/* 202 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getTOUDiv() {
/* 212 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 244 */     stringBuffer.append("<div id=\"popup-footer\">" + NEWLINE);
/* 245 */     stringBuffer.append("  <div class=\"hrule-dots\">&nbsp;</div>" + NEWLINE);
/* 246 */     stringBuffer.append("    <div class=\"content\">" + NEWLINE);
/* 247 */     stringBuffer.append("      <script type=\"text/javascript\" language=\"JavaScript\">" + NEWLINE);
/* 248 */     stringBuffer.append("       <!--" + NEWLINE);
/* 249 */     stringBuffer.append("       function printReport() { top.window.print();}" + NEWLINE);
/* 250 */     stringBuffer.append("       //-->" + NEWLINE);
/* 251 */     stringBuffer.append("      </script>" + NEWLINE);
/* 252 */     stringBuffer.append("\t     <div style=\"padding:0 1em 0.4em 34px; float:right;\">" + NEWLINE);
/* 253 */     stringBuffer.append("\t       <a class=\"ibm-print-link\" href=\"javascript:printReport();\">Print</a>" + NEWLINE);
/* 254 */     stringBuffer.append("        <a style=\"padding:0 1em 0.4em 4px;\" href=\"javascript:close();\">Close Window</a>" + NEWLINE);
/* 255 */     stringBuffer.append(" \t </div>" + NEWLINE);
/* 256 */     stringBuffer.append("    </div>" + NEWLINE);
/* 257 */     stringBuffer.append("    <div style=\"clear:both;\">&nbsp;</div>" + NEWLINE);
/* 258 */     stringBuffer.append("  </div>" + NEWLINE);
/* 259 */     stringBuffer.append("</div>" + NEWLINE);
/*     */     
/* 261 */     stringBuffer.append("</div>" + NEWLINE);
/* 262 */     stringBuffer.append("</div>" + NEWLINE);
/* 263 */     stringBuffer.append("<!-- CONTENT_BODY_END -->" + NEWLINE);
/* 264 */     stringBuffer.append("<!-- NAVIGATION_BEGIN -->" + NEWLINE);
/* 265 */     stringBuffer.append("<div id=\"ibm-navigation\">" + NEWLINE);
/* 266 */     stringBuffer.append("<!-- hidden in popup-page -->" + NEWLINE);
/* 267 */     stringBuffer.append("</div>" + NEWLINE);
/* 268 */     stringBuffer.append("<!-- NAVIGATION_END -->" + NEWLINE);
/* 269 */     stringBuffer.append("</div>" + NEWLINE);
/* 270 */     stringBuffer.append("<div id=\"ibm-related-content\"></div>" + NEWLINE);
/* 271 */     stringBuffer.append("</div>" + NEWLINE);
/* 272 */     stringBuffer.append("<!-- CONTENT_END -->" + NEWLINE);
/* 273 */     stringBuffer.append("<!-- FOOTER_BEGIN -->" + NEWLINE);
/* 274 */     stringBuffer.append("<div id=\"ibm-footer-module\"></div>" + NEWLINE);
/* 275 */     stringBuffer.append("<div id=\"ibm-footer\">" + NEWLINE);
/* 276 */     stringBuffer.append("<h2 class=\"ibm-access\">Footer links</h2>" + NEWLINE);
/* 277 */     stringBuffer.append("<ul>" + NEWLINE);
/* 278 */     stringBuffer.append("<li><a href=\"http://w3.ibm.com/w3/info_terms_of_use.html\">Terms of use</a></li>" + NEWLINE);
/* 279 */     stringBuffer.append("</ul>" + NEWLINE);
/* 280 */     stringBuffer.append("</div>" + NEWLINE);
/* 281 */     stringBuffer.append("<!-- FOOTER_END -->" + NEWLINE);
/* 282 */     stringBuffer.append("<div id=\"ibm-metrics\">" + NEWLINE);
/* 283 */     stringBuffer.append("<script src=\"//w3.ibm.com/w3webmetrics/js/ntpagetag.js\" type=\"text/javascript\">//</script>" + NEWLINE);
/* 284 */     stringBuffer.append("</div>" + NEWLINE);
/*     */     
/* 286 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDefNotPopulated() {
/* 293 */     return "<em>** Not Populated **</em>";
/*     */   }
/*     */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\ab\\util\EACustom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */