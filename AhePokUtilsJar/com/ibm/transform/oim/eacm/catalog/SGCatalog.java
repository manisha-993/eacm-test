// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.catalog;

import java.util.*;
import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;
import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
 * This class supports SG Catalog reports
 *
 */
// $Log: SGCatalog.java,v $
// Revision 1.4  2014/01/13 13:48:54  wendy
// migration to V17
//
// Revision 1.3  2006/10/02 18:57:54  wendy
// Pass application name for images
//
// Revision 1.2  2006/10/02 17:21:31  wendy
// Added check for missing search action
//
// Revision 1.1  2006/09/22 14:52:03  wendy
// Init for XCC Catalog reports
//
//
abstract public class SGCatalog
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.4 $";

    /** nlsid used with javascript */
    public static final String NLS_ID = "nls_id";
    /** audience used with javascript */
    public static final String AUDIENCE_ID = "audience_id";
    /** partnumber used with javascript */
    public static final String PN_STR = "part_num";

    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    private static final String PRICE_SQL = "select priceamount from eacm.finalprice where matnr=? and varcondtype=? " +
        "and countrycode=? and distributionchannel=? and pricevalidfromdate<=? and ?<=pricevalidtodate";

    private static final String FEATID_SQL = "select FEATENTITYID, CONFQTY, PRODENTITYID from GBLI.PRODSTRUCT where "+
        "COUNTRYCODE = ? and NLSID = ? and PRODENTITYTYPE = ? and FEATENTITYTYPE = ? "+
        "and VALFROM <= ? and VALTO >= ? and ISACTIVE = ? and PRODENTITYID ";

    private static final String DETAIL_SQL = "select ATTVALUE, FEATENTITYID from GBLI.FEATUREDETAIL where "+
        "COUNTRYCODE = ? and NLSID = ? and ITEMENTITYTYPE = ? and ATTCODE = ? "+
        "and VALFROM <= ? and VALTO >= ? and FEATENTITYID in (";

    private static final String WWATTR_SQL = "select attributevalue from GBLI.WWATTRIBUTES where "+
        "COUNTRYCODE = ? and NLSID = ? and WWENTITYTYPE = ? and WWENTITYID =? and ATTRIBUTECODE = ? "+
        "and VALFROM <= ? and VALTO >= ? and ISACTIVE =?";

    private static final String BRULE_SQL = "select CATTABLENAME from GBLI.BASICRULE where "+
        "NLSID = ? and CATITEMTYPE = ? and CATATTRIBUTECODE = ? "+
        "and VALFROM <= ? and VALTO >= ? and ISACTIVE =?";

    private String audienceFlag;
    private String ctryFlag;
    private String ctryCode;
    private EntityList theList;
    private Timestamp curTimestamp;
    private String curTime;
    private Connection connection;
    //PreparedStatements for repeated operations
    private PreparedStatement priceStatement, featidStatement, wwattrStatement, bruleStatement;

    private int nlsId;
    private Vector offeringVct = new Vector();
    private Vector inactiveOfferingVct = new Vector();
    private Vector modelVct = new Vector();
    private static final int I10 = 10;
    private static final int I11 = 11;
    private static final int I19 = 19;

    /** header spacer */
    protected static final String SPACER_DESC ="<td bgcolor=\"#EEEEEE\" height=\"1\" width=\"4\">&nbsp;</td>";
    /** data spacer */
    protected static final String SPACER_COL = "<td bgcolor=\"#FFFFFF\" height=\"1\" width=\"4\">&nbsp;</td>";


    /********************************************************************************
    * Get report info for display of this particular catalog
    *@param out javax.servlet.jsp.JspWriter for output
    *@param applicationName
    *@throws java.io.IOException
    *@throws java.sql.SQLException
    */
    abstract protected void outputReport(javax.servlet.jsp.JspWriter out, String applicationName) throws java.sql.SQLException, java.io.IOException;

    /********************************************************************************
    * Constructor for VAM and MDP report
    *
    *@param  list       EntityList for data
    *@param  nls        int id for selected NLS
    *@param  ctry       String flagcode for selected country
    *@param  code       String ctryCode for selected country
    *@param  aud        String flagcode for selected audience
    *@param  curtime    String current time in ISO format
    */
    public SGCatalog(EntityList list,int nls, String ctry, String code, String aud, String curtime)
    {
        theList = list;
        //Timestamp format must be yyyy-mm-dd hh:mm:ss.fffffffff
        // iso format is yyyy-mm-dd-hh.mm.ss.ffffff
        curTimestamp = Timestamp.valueOf(curtime.substring(0,10)+" "+
            curtime.substring(I11,I19).replace('.',':')+curtime.substring(I19));

        curTime = curtime.substring(0,I10);
        nlsId = nls;
        ctryFlag = ctry;
        ctryCode = code;
        audienceFlag = aud;
    }

    /********************************************************************************
    * Get report info for display of this catalog
    *@param out javax.servlet.jsp.JspWriter for output
    *@param applicationName
    *@throws java.io.IOException
    *@throws java.sql.SQLException
    */
    public void getReportInfo(javax.servlet.jsp.JspWriter out, String applicationName) throws java.sql.SQLException, java.io.IOException
    {
        try {
            setupConnection();
            outputReport(out,applicationName);
            out.flush();
        }
        finally{
            closeConnection();
        }
    }

    /********************************************************************************
    * Release memory
    */
    public void dereference()
    {
        theList = null;
        curTime = null;
        curTimestamp = null;
        ctryFlag = null;
        ctryCode = null;
        audienceFlag = null;

        for (int i=0; i<offeringVct.size(); i++){
            SGOffering theOff = (SGOffering)offeringVct.elementAt(i);
            theOff.dereference();
        }
        offeringVct.clear();
        offeringVct = null;

        for (int i=0; i<inactiveOfferingVct.size(); i++){
            SGOffering theOff = (SGOffering)inactiveOfferingVct.elementAt(i);
            theOff.dereference();
        }
        inactiveOfferingVct.clear();
        inactiveOfferingVct = null;

        for (int i=0; i<modelVct.size(); i++){
            SGOffering theOff = (SGOffering)modelVct.elementAt(i);
            theOff.dereference();
        }
        modelVct.clear();
        modelVct=null;
    }

    /********************************************************************************
    * get the list
    * @return EntityList
    */
    protected EntityList getEntityList() {return theList;}
    /********************************************************************************
    * get the active offerings LSEO and LSEOBUNDLE
    * @return Vector
    */
    protected Vector getActiveOfferings() {return offeringVct;}
    /********************************************************************************
    * get the inactive offerings LSEO and LSEOBUNDLE and MODEL
    * @return Vector
    */
    protected Vector getInactiveOfferings() {return inactiveOfferingVct;}
    /********************************************************************************
    * get the active Models
    * @return Vector
    */
    protected Vector getActiveModels() {return modelVct;}
    /********************************************************************************
    * get the current date
    * @return String
    */
    protected String getNow() {return curTime;}
    /********************************************************************************
    * get the selected audience
    * @return String
    */
    protected String getAudienceFlag() {return audienceFlag;}
    /********************************************************************************
    * get the selected country
    * @return String
    */
    protected String getCountryFlag() {return ctryFlag;}

    /********************************************************************************
    * find all offerings, create one SGOffering for each one
    * Products shown are found as follows:
    *
    *   For LSEOs, use the association PROJWWSEOA and then the relator WWSEOLSEO to get the LSEOs.
    *   Now you have WWSEO and its children LSEOs including their entityids.
    *
    *   For LSEOBUNDLEs, use the association PROJLSEOBUNDLEA to get the LSEOBUNDLE. Then use the
    *   relator LSEOBUNDLELSEO to get the LSEOs. Then for each LSEO, use the relator WWSEOLSEO to
    *   get the WWSEOs. Now you have the LSEOs and their parent WWSEOs including their entityids.
    *   This is used to find the attribute values referenced by the layout.
    *
    * Wendy hi.. another question.. do the LSEO that are part of the LSEOBUNDLE get their own row of output too?
    * Rupal no
    * Rupal lseobundle only
    *@param isVAM
    *@param out
    *@throws IOException
    */
    protected void findAllOfferings(boolean isVAM, javax.servlet.jsp.JspWriter out) throws IOException
    {
        EntityGroup eGrp = theList.getEntityGroup("PROJ");
        Vector wwseoVct = PokUtils.getAllLinkedEntities(eGrp, "PROJWWSEOA", "WWSEO");
        Vector lseoVct = PokUtils.getAllLinkedEntities(wwseoVct, "WWSEOLSEO", "LSEO");

        out.println("<!--findAllOfferings() Check all LSEO lseoVct.size(): "+lseoVct.size()+" -->");
        for (int t=0; t<lseoVct.size(); t++){
            SGOffering theOff = new SGLseoOffering(this, ((EntityItem)lseoVct.elementAt(t)),
                audienceFlag,ctryFlag,curTime,out);
            if (theOff.showInVAMSection()){
                offeringVct.add(theOff);
                // but this may have an invalid catpublg so check
                if (theOff.getInvalidCatlgpub().size()>0){
                    inactiveOfferingVct.add(theOff);
                }
            }else{
                inactiveOfferingVct.add(theOff);
            }
        }
        lseoVct.clear();
        wwseoVct.clear();

        eGrp = theList.getEntityGroup("LSEOBUNDLE");
        out.println("<!--findAllOfferings() Check all LSEOBUNDLE count: "+eGrp.getEntityItemCount()+" -->");
        for (int i=0; i<eGrp.getEntityItemCount(); i++){
            EntityItem item = eGrp.getEntityItem(i);
            SGOffering theOff = new SGLseoBundleOffering(this, item,audienceFlag,ctryFlag,curTime,out);
            if (theOff.showInVAMSection()){
                offeringVct.add(theOff);
                // but this may have an invalid catpublg so check
                if (theOff.getInvalidCatlgpub().size()>0){
                    inactiveOfferingVct.add(theOff);
                }
            }else{
                inactiveOfferingVct.add(theOff);
            }
        }

        if (isVAM) {
            Vector mdlVct;
            // get models for build your own section.. only active ones
            eGrp = theList.getEntityGroup("PROJ");
            mdlVct = PokUtils.getAllLinkedEntities(eGrp, "PROJMODELA", "MODEL");
            out.println("<!--findAllOfferings() Check all MODEL mdlVct.size(): "+mdlVct.size()+" -->");
            for (int t=0; t<mdlVct.size(); t++){
                SGOffering theOff = new SGMdlOffering(this, ((EntityItem)mdlVct.elementAt(t)),
                    audienceFlag,ctryFlag,curTime,out);
                if (theOff.showInVAMSection()){
                    modelVct.add(theOff);
                    // but this may have an invalid catpublg so check
                    if (theOff.getInvalidCatlgpub().size()>0){
                        inactiveOfferingVct.add(theOff);
                    }
                }else{
                    inactiveOfferingVct.add(theOff);
                }
            }
            // sort on partnumber
            mdlVct.clear();
            Collections.sort(modelVct);
        }

        // sort on partnumber
        Collections.sort(offeringVct);
    }

    /********************************************************************************
    * setup the connection and preparedstatements
    * fixme how to get this connection info for AHE?
    */
    private void setupConnection()
        throws java.sql.SQLException
    {
        // get the connection for gbli.prodstruct, gbli.featuredetails and eacm.finalprice
        connection =
            DriverManager.getConnection(
                MiddlewareServerProperties.getPDHDatabaseURL(),
                MiddlewareServerProperties.getPDHDatabaseUser(),
                MiddlewareServerProperties.getPDHDatabasePassword());

        connection.setAutoCommit(false);

        // reuse these statements for each offering
        priceStatement = connection.prepareStatement(PRICE_SQL);
        featidStatement = connection.prepareStatement(FEATID_SQL+" = ?"); // used with single prodid
        wwattrStatement = connection.prepareStatement(WWATTR_SQL);
        bruleStatement = connection.prepareStatement(BRULE_SQL);
    }

    /********************************************************************************
    * Finding Attribute Values based on CATENTITYTYPE and CATATTRIBUTE:
    * Find the corresponding BASICRULE where CATENTITYTYPE = CATITEMTYPE and CATATTRIBUTE = CATATTRIBUTECODE.
    * Results:
    *   Catalog DB Table Name (CATTABLENAME)
    *   Entity Type (CATENTITYTYPE) - not used in this report yet
    * Get tablename from gbli.basicrule
    *
    *   From GBLI.BASICRULE where
    *       NLSID = 1
    *       CATITEMTYPE = from CATVAMATTR.CATENTITYTYPE
    *       CATATTRIBUTECODE = from CATVAMATTR.CATATTRIBUTECODE
    *       ATTRIBUTECODE = CATATTRIBUTE
    *       VALFROM <= Current Time Stamp
    *       VALTO >= Current Time Stamp
    *       ISACTIVE = 1
    *   Results
    *       CATTABLENAME
    *
    *@param  catType  String
    *@param  catCode  String
    *@param  out  javax.servlet.jsp.JspWriter for debug
    *@return  String
    *@throws java.sql.SQLException
    *@throws IOException
    */
    protected String getCatTableName(String catType, String catCode,javax.servlet.jsp.JspWriter out)
        throws java.sql.SQLException, IOException
    {
        String tblname = null;
        ResultSet attrRs=null;
        try{
            // get CATTABLENAME
            bruleStatement.clearParameters();
            bruleStatement.setInt(1, 1);            // NLSID=1
            bruleStatement.setString(2, catType);   // CATITEMTYPE
            bruleStatement.setString(3, catCode);           // CATATTRIBUTECODE
            bruleStatement.setTimestamp(4,curTimestamp);    // current timestamp
            bruleStatement.setTimestamp(5,curTimestamp);    // current timestamp
            bruleStatement.setInt(6, 1);            // ISACTIVE=1

            attrRs = bruleStatement.executeQuery();

            while(attrRs.next())    {
                if (tblname!=null){  // get all that match.. should only be one though
                    out.println("<!--getCatTableName() Duplicate match found in GBLI.BASICRULE for CATITEMTYPE: "+catType+
                        " CATATTRIBUTECODE:"+catCode+" first CATTABLENAME:"+tblname+
                        " next CATTABLENAME:"+attrRs.getString(1).trim()+" -->");
                }else{
                    tblname = attrRs.getString(1).trim();
                }
            }
            if (tblname==null){
                out.println("<!--getCatTableName() no GBLI.BASICRULE match found for CATITEMTYPE:"+catType+
                    " CATATTRIBUTECODE:"+catCode+" ISACTIVE:1 NLSID:1"+
                    " valon:"+curTimestamp+"-->");
            }

        }
        catch(SQLException t) {
            throw(t);
        }
        catch(IOException t) {
            throw(t);
        }
        finally{
            if (attrRs!=null){
                attrRs.close();
            }
        }

        return tblname;
    }

    /********************************************************************************
    * Get column 2 - price info
    *   EACM.FINALPRICE get PRICEAMOUNT where
    *   Matnr = SEOID for LSEO and LSEOBUNDLE
    *   Varcondtype = SEO for LSEO and LSEOBUNDLE
    *   Countrycode = GENAREACODE derived from the original prompt
    *   Distributionchannel = '00'
    *   Pricevalidfromdate <= Current Timestamp <= Pricevalidtodate
    *@param  partnum  String
    *@param  varcondtype  String
    *@param  out  javax.servlet.jsp.JspWriter for debug
    *@return  String
    *@throws java.sql.SQLException
    *@throws IOException
    */
    protected String getPrice(String partnum, String varcondtype,javax.servlet.jsp.JspWriter out)
        throws java.sql.SQLException, IOException
    {
        String price =null;
        ResultSet result=null;

        try{
            priceStatement.clearParameters();
            priceStatement.setString(1, partnum);//"matnr"
            priceStatement.setString(2, varcondtype);//"partnum"
            priceStatement.setString(3, ctryCode);//"Countrycode"
            priceStatement.setString(4, "00");//"distributionchannel"
            priceStatement.setString(5, curTime);//"timestamp"
            priceStatement.setString(6, curTime);//"timestamp"

            result = priceStatement.executeQuery();
            while(result.next()) {
                if (price==null){
                    price = ""+result.getDouble(1);
                }else{
                    price = price+"<br />"+getErrorHtml(""+result.getDouble(1));
                    out.println("<!--getPrice() ERROR EACM.FINALPRICE more than one match found for MATNR:"+partnum+" VARCONDTYPE:"+varcondtype+
                        " COUNTRYCODE:"+ctryCode+" Distributionchannel = '00'"+" valon:"+   curTimestamp+" -->");
                }
            }
            if (price==null){
                price = getErrorHtml("No price found");
                out.println("<!--getPrice() ERROR no EACM.FINALPRICE match found for MATNR:"+partnum+" VARCONDTYPE:"+varcondtype+
                    " COUNTRYCODE:"+ctryCode+" Distributionchannel = '00'"+" valon:"+   curTimestamp+" -->");
            }
        }
        catch(SQLException t) {
            throw(t);
        }
        catch(IOException t) {
            throw(t);
        }
        finally{
            if (result!=null){
                result.close();
            }
        }
        return price;
    }

    /********************************************************************************
    * Get column 3-6 info
    * If CATTABLENAME = 'FEATUREDETAIL' then
    *   From GBLI.PRODSTRUCT where
    *       COUNTRYCODE = GENAREACODE
    *       NLSID = prompt value
    *       PRODENTITYTYPE = 'WWSEO' (or 'LSEO')
    *       PRODENTITYID = entityid of the WWSEO (or 'LSEO')
    *       FEATENTITYTYPE = 'FEATURE'
    *       VALFROM <= Current Time Stamp
    *       VALTO >= Current Time Stamp
    *       ISACTIVE = 1
    *
    * Results
    *   FEATENTITYID
    *   CONFQTY  fixme skipping this for now.. doesn't seem to be used
    *
    *@param  prodType   String  PRODENTITYTYPE
    *@param  prodIds    int[]   PRODENTITYID
    *@param  out  javax.servlet.jsp.JspWriter for debug
    *@return  String
    *@throws java.sql.SQLException
    *@throws IOException
    */
    protected Vector getFeatIds(String prodType, int[] prodIds, javax.servlet.jsp.JspWriter out)
        throws java.sql.SQLException, IOException
    {
        Vector featVct = new Vector();

        if (prodIds.length>0){  // make sure there is at least one id
            ResultSet featIdRs=null;
            try{
                PreparedStatement ps;
                int cursor=8;

                if (prodIds.length==1){  // use same prepared statement
                    ps = featidStatement;
                }else{  // build up a new prepared statement, do one call with all prodids
                    StringBuffer sb = new StringBuffer(" in (");
                    for (int i=0; i<prodIds.length; i++){
                        sb.append("?");
                        if ((i+1)<prodIds.length){
                            sb.append(", ");
                        }
                    }
                    sb.append(")");
                    ps = connection.prepareStatement(FEATID_SQL+sb.toString());
                }

                // get all featentityid for these LSEO or WWSEO
                // get FEATENTITYID, CONFQTY
                ps.clearParameters();
                ps.setString(1, ctryCode);          //"COUNTRYCODE"
                ps.setInt(2, nlsId);                // nlsid
                ps.setString(3, prodType);          //"PRODENTITYTYPE"
                ps.setString(4, "FEATURE");         //"FEATENTITYTYPE"
                ps.setTimestamp(5,curTimestamp);    //timestamp
                ps.setTimestamp(6,curTimestamp);    //timestamp
                ps.setInt(7,1);                     //ISACTIVE
                for (int i=0; i<prodIds.length; i++){
                    int prodId = prodIds[i];
                    ps.setInt(cursor++, prodId);    // PRODENTITYID
                }

                featIdRs = ps.executeQuery();
                while(featIdRs.next()) {
                    int featId = featIdRs.getInt(1);
                    String confQty = featIdRs.getString(2);
                    int matchProdId = featIdRs.getInt(3);
                    out.println("<!--getFeatIds() GBLI.PRODSTRUCT matched FEATENTITYID "+featId+" CONFQTY "+confQty+
                        " PRODENTITYTYPE "+prodType+" PRODENTITYID "+matchProdId+" -->");
                    if (!featVct.contains(""+featId)){ // make sure no dupes.. probably can't happen
                        featVct.add(""+featId);
                    }
                }
                if (featVct.size()==0) {
                    out.print("<!--getFeatIds() No GBLI.PRODSTRUCT match found for COUNTRYCODE:"+ctryCode+" NLSID:"+nlsId+
                            " PRODENTITYTYPE:"+prodType+" PRODENTITYID:");
                    for (int i=0; i<prodIds.length; i++){
                        out.print(""+prodIds[i]+((i+1)<prodIds.length?", ":""));
                    }
                    out.println(" FEATENTITYTYPE:FEATURE valon:"+curTimestamp+"-->");
                }

                if (ps!=featidStatement){
                    ps.close();
                }
            }
            catch(SQLException t) {
                throw(t);
            }
            catch(IOException t) {
                throw(t);
            }
            finally{
                if (featIdRs!=null){
                    featIdRs.close();
                }
            }
        }

        return featVct;
    }

    /********************************************************************************
    * Get info from gbli.featuredetail table
    *
    * Then
    *   from GBLI.FEATUREDETAIL
    *   COUNTRYCODE = GENAREACODE
    *   NLSID = prompt value
    *   FEATENTITYID = from the prior step Results
    *   ITEMENTITYTYPE = CATENTITYTYPE
    *   ATTCODE = CATATTRIBUTECODE
    *   VALFROM >= Current Time Stamp
    *   VALTO >= Current Time Stamp
    *
    * Results
    *   ATTVALUE
    *@param  featIdVct  Vector of featentityid
    *@param  catType  String
    *@param  catCode  String
    *@param  out  javax.servlet.jsp.JspWriter for debug
    *@return  String[] [0]=value [1]=tooltip
    *@throws java.sql.SQLException
    *@throws IOException
    */
    protected String[] getCellFromFeatDetail(Vector featIdVct, String catType, String catCode,
        javax.servlet.jsp.JspWriter out)
        throws java.sql.SQLException, IOException
    {
        String[] retvalue = new String[2];

        if (featIdVct.size()>0) {
            ResultSet detailRs=null;
            PreparedStatement ps=null;
            try{
                int cursor=7;

                // build up a new prepared statement, do one call with all featids
                StringBuffer sb = new StringBuffer();
                for (int i=0; i<featIdVct.size(); i++){
                    sb.append("?");
                    if ((i+1)<featIdVct.size()){
                        sb.append(", ");
                    }
                }
                sb.append(")");
                ps = connection.prepareStatement(DETAIL_SQL+sb.toString());

                // get ATTVALUE
                ps.setString(1, ctryCode);          // COUNTRYCODE
                ps.setInt(2, nlsId);                // NLSID
                ps.setString(3, catType);           // ITEMENTITYTYPE = CATENTITYTYPE
                ps.setString(4, catCode);           // ATTCODE = CATATTRIBUTECODE
                ps.setTimestamp(5,curTimestamp);    // current timestamp
                ps.setTimestamp(6,curTimestamp);    // current timestamp
                for (int f=0; f<featIdVct.size(); f++){
                    int featId = Integer.parseInt(featIdVct.elementAt(f).toString());
                    ps.setInt(cursor++, featId);    // FEATENTITYID
                }
                detailRs = ps.executeQuery();
                while(detailRs.next())  {
                    if (retvalue[0]!=null){  // get all that match.. should only be one though
                        retvalue[0]=retvalue[0]+" "+getErrorHtml("Dupe:"+detailRs.getString(1),"Multiple matches");
                        retvalue[1]=retvalue[1]+NEWLINE+"Dupe:"+catType+":"+catCode+":FEATID"+detailRs.getInt(2);
                        out.println("<!--getCellFromFeatDetail() Duplicate match found in GBLI.FEATUREDETAIL for "+retvalue[1]+"-->");
                    }else{
                        retvalue[0]=detailRs.getString(1);
                        retvalue[1]="FEATUREDETAIL:"+catType+":"+catCode+":FEATID"+detailRs.getInt(2);
                    }

                }
                if (retvalue[0]==null){
                    out.println("<!--getCellFromFeatDetail() no GBLI.FEATUREDETAIL match found for FEATENTITYID:"+featIdVct+
                        " COUNTRYCODE:"+ctryCode+" NLSID:"+nlsId+
                        " ITEMENTITYTYPE:"+catType+" ATTCODE:"+catCode+" valon:"+
                        curTimestamp+"-->");
                }
            }
            catch(SQLException t) {
                throw(t);
            }
            catch(IOException t) {
                throw(t);
            }
            finally{
                if (ps!=null){
                    ps.close();
                }
                if (detailRs!=null){
                    detailRs.close();
                }
            }
        }

        return retvalue;
    }

    /********************************************************************************
    * Get info from gbli.wwattributes
    *
    * If CATTABLENAME = 'WWATTRIBUTES' then
    *   From GBLI.WWATTRIBUTES where
    *       COUNTRYCODE = GENAREACODE
    *       NLSID = prompt value
    *       WWENTITYTYPE = 'WWSEO' (or 'LSEOBUNDLE')
    *       WWENTITYID = entityid of the WWSEO (or LSEOBUNDLE)
    *       ATTRIBUTECODE = CATATTRIBUTE
    *       VALFROM <= Current Time Stamp
    *       VALTO >= Current Time Stamp
    *       ISACTIVE = 1
    *   Results
    *       ATTRIBUTEVALUE
    *
    *@param  theOff
    *@param  catCode  String
    *@param  out  javax.servlet.jsp.JspWriter for debug
    *@return  String[] [0]=value [1]=tooltip
    *@throws java.sql.SQLException
    *@throws IOException
    */
    protected String[] getCellFromWWAttr(SGOffering theOff, String catCode,javax.servlet.jsp.JspWriter out)
        throws java.sql.SQLException, IOException
    {
        String[] retvalue = new String[2];

        ResultSet attrRs=null;
        try{
            // get ATTRIBUTEVALUE
            wwattrStatement.clearParameters();
            wwattrStatement.setString(1, ctryCode);         // COUNTRYCODE
            wwattrStatement.setInt(2, nlsId);               // NLSID
            wwattrStatement.setString(3, theOff.getWWENTITYTYPE());         // WWENTITYTYPE
            wwattrStatement.setInt(4, theOff.getWWENTITYID());          // WWENTITYID
            wwattrStatement.setString(5, catCode);          // ATTRIBUTECODE
            wwattrStatement.setTimestamp(6,curTimestamp);   // current timestamp
            wwattrStatement.setTimestamp(7,curTimestamp);   // current timestamp
            wwattrStatement.setInt(8, 1);               // ISACTIVE

            attrRs = wwattrStatement.executeQuery();

            while(attrRs.next())    {
                if (retvalue[0]!=null){  // get all that match.. should only be one though
                    retvalue[0]=retvalue[0]+" "+getErrorHtml("Dupe:"+attrRs.getString(1),"Multiple matches");
                    retvalue[1]=retvalue[1]+NEWLINE+"Dupe:"+catCode+":"+theOff.getWWENTITYTYPE()+theOff.getWWENTITYID();
                    out.println("<!--getCellFromWWAttr() Duplicate match found in GBLI.WWATTRIBUTES for "+retvalue[1]+"-->");
                }else{
                    retvalue[0]=attrRs.getString(1);
                    retvalue[1]="WWATTRIBUTES:"+catCode+":"+theOff.getWWENTITYTYPE()+":"+theOff.getWWENTITYID();
                }
            }
            if (retvalue[0]==null){
                out.println("<!--getCellFromWWAttr() no GBLI.WWATTRIBUTES match found for WWENTITYTYPE:"+theOff.getWWENTITYTYPE()+
                    " WWENTITYID:"+theOff.getWWENTITYID()+" COUNTRYCODE:"+ctryCode+" NLSID:"+nlsId+
                    " attributecode:"+catCode+" valon:"+curTimestamp+"-->");
            }

        }
        catch(SQLException t) {
            throw(t);
        }
        catch(IOException t) {
            throw(t);
        }
        finally{
            if (attrRs!=null){
                attrRs.close();
            }
        }

        return retvalue;
    }

    /********************************************************************************
    * close the connection and preparedstatements
    */
    private void closeConnection() throws java.sql.SQLException
    {
        try {
            if (priceStatement!=null) {
                priceStatement.close();
                priceStatement=null;
            }
        }catch(Exception e){
            System.err.println("closeConnection(), unable to close price statement. "+ e);
        }
        try {
            if (featidStatement!=null) {
                featidStatement.close();
                featidStatement=null;
            }
        }catch(Exception e){
            System.err.println("closeConnection(), unable to close feature statement. "+ e);
        }
        try {
            if (wwattrStatement!=null) {
                wwattrStatement.close();
                wwattrStatement=null;
            }
        }catch(Exception e){
            System.err.println("closeConnection(), unable to close wwattr statement. "+ e);
        }
        try {
            if (bruleStatement!=null) {
                bruleStatement.close();
                bruleStatement=null;
            }
        }catch(Exception e){
            System.err.println("closeConnection(), unable to close brule statement. "+ e);
        }

        if(connection != null) {
            try {
                connection.rollback();
            }
            catch (Throwable ex) {
                System.err.println("closeConnection(), unable to rollback. "+ ex);
            }
            finally {
                connection.close();
                connection = null;
            }
        }
    }

    /********************************************************************************
    * Get error msg that meets accessibility standards
    *@param  msg  String
    *@return  String
    */
    public static String getErrorHtml(String msg) {
        return getErrorHtml(msg,"Error");
    }

    /********************************************************************************
    * Get error msg that meets accessibility standards
    *@param  msg  String
    *@param  alt  String
    *@return  String
    */
    public static String getErrorHtml(String msg,String alt) {
        //return "<img src=\"http://w3.ibm.com/ui/v8/images/icon-system-status-alert.gif\" width=\"12\" "+
          //  "height=\"10\" alt=\""+alt+"\" />" +
        return "<span class=\"ibm-caution-link\" style=\"color:#c00; font-weight:bold;\">&nbsp;"+msg+"</span>";
    }

    /********************************************************************************
    * Build the form used to get report type, or date range and country list if needed
    *
    *@param  request     HttpServletRequest with information to pass on
    *@param  appName     String with application name
    *@param  usePN     boolean if true, display partnumber
    *@param  url         String with url for action
    *@param  db   Database
    *@param  prof     Profile
    *@param entityId
    *@param bundle
    *@return String with javascript and form to select report type
    *@throws java.sql.SQLException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareException
    *@throws SGCatException
    */
    public static String getCtryNlsForm(HttpServletRequest request, String appName,
        boolean usePN, String url, Database db, Profile prof, int entityId,
        ResourceBundle bundle)
        throws java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        SGCatException
    {
        StringBuffer sb = new StringBuffer();
        EntityList list;
        MetaFlag[] mfArray;
        int count=0;
        EntityItem catnavItem;
        EANFlagAttribute fAtt;
        int ctrycnt=0;
        Enumeration e = request.getParameterNames();
        String actionName = "/"+appName+"/DG.wss"; // DG servlet as action

        if (usePN) {  // check part number and country
            sb.append(getCtryPnValidationScript(bundle));
        }else{  // check country
            sb.append(getCtryValidationScript(bundle));
        }

        // must output msg about javascript
        sb.append(PokUtils.getJavaScriptWarning());

        if (request.getParameter(PokUtils.DG_INDICATOR)==null) {// not from DGServlet, from cmd line
            actionName = "/"+appName+url;
        }

        // build date entry form using DG servlet as action if was from DG
        sb.append("<form method=\"post\" name=\"ctryNlsForm\" action=\""+actionName+"\" ");

        if (usePN){
            sb.append("onsubmit=\"return checkInput(this."+SGUtils.CTRY_LIST+", this."+PN_STR+");\">");
        }else{
            sb.append("onsubmit=\"return checkInput(this."+SGUtils.CTRY_LIST+");\">");
        }

        sb.append(NEWLINE);

        // output all request parameters as hidden fields
        while(e.hasMoreElements())
        {
            String name = (String)e.nextElement();
            String vals[] = request.getParameterValues(name);
            if(vals != null)  {
                for(int i = 0; i<vals.length; i++) {
                    sb.append("<input type=\"hidden\" name=\""+name+"\" value=\""+vals[i]+"\"/>"+NEWLINE);
                }
            }
        }

        sb.append("<p>"+PokUtils.REQUIRED_FLD_TXT+"<br /></p>"+NEWLINE);

        sb.append("<table border=\"0\" summary=\"layout\">"+NEWLINE);

        // add countries selection
        list = db.getEntityList(prof,
            new ExtractActionItem(null, db, prof, "DUMMY"),
            new EntityItem[] { new EntityItem(null, prof, "CATNAV",entityId)});
        // get set of country from this CATNAV
        catnavItem = list.getParentEntityGroup().getEntityItem(0);
        fAtt = (EANFlagAttribute)catnavItem.getAttribute("COUNTRYLIST");
        if (fAtt==null){
			throw new SGCatException("COUNTRYLIST is null, report cannot execute");
		}
        // Get the selected Flag codes.
        mfArray = (MetaFlag[]) fAtt.get();
        for (int i = 0; i < mfArray.length; i++){
            // get selection
            if (mfArray[i].isSelected()){
                ctrycnt++;
            }
        }

        sb.append("<tr><td style=\"vertical-align:top;\"><label for=\""+SGUtils.CTRY_LIST+"\">*"+
            bundle.getString("Label_CountryFilter") +"&nbsp;&nbsp;</label></td>"+NEWLINE);
        sb.append("<td>"+NEWLINE+
            "<select size=\""+(ctrycnt<8?""+ctrycnt:"8")+"\" id=\""+SGUtils.CTRY_LIST+"\" name=\""+SGUtils.CTRY_LIST+"\" onkeydown=\"typeAhead()\">"+NEWLINE);
        for (int i = 0; i < mfArray.length; i++){
            // get selection
            if (mfArray[i].isSelected()){
                sb.append("<option value=\""+mfArray[i].getFlagCode()+"|"+mfArray[i].toString()+"\">");
                sb.append(mfArray[i].toString());
                sb.append("</option>"+NEWLINE);
            }
        }
        sb.append("</select></td></tr>");
        sb.append(NEWLINE);

        // add audience selection
        fAtt = (EANFlagAttribute)catnavItem.getAttribute("CATAUDIENCE");
        // Get the selected Flag codes.
        mfArray = (MetaFlag[]) fAtt.get();
        for (int i = 0; i < mfArray.length; i++){
            // get selection
            if (mfArray[i].isSelected()){
                String radioId = "CATAUD_"+i;
                if (count==0){
                    sb.append("<tr><td>"+
                        bundle.getString("Label_Audience") +
                        "&nbsp;&nbsp;</td>"+NEWLINE);
                }else{
                    sb.append("<tr><td>&nbsp;&nbsp;</td>"+NEWLINE);
                }
                count++;
                sb.append("<td style=\"vertical-align:top;\">"+NEWLINE);
                sb.append("<input type=\"radio\" name=\""+AUDIENCE_ID+"\""+
                    (count==1?" checked=\"checked\"":"")+  // default to first one
                    " value=\""+mfArray[i].getFlagCode()+"|"+mfArray[i]+"\" id=\""+radioId+"\" /><label for=\""+
                    radioId+"\">"+ mfArray[i]+"</label>"+NEWLINE);

                sb.append("</td></tr>");
            }
        }

        // add nls id selection
        for (int ix = 0; ix < prof.getReadLanguages().size(); ix++) {
            NLSItem nlsitem = prof.getReadLanguage(ix);
            String radioId = "NLS_"+nlsitem.getNLSID();
            if (ix==0){
                sb.append("<tr><td>"+
                    bundle.getString("Label_Language") +
                    "&nbsp;&nbsp;</td>"+NEWLINE);
            }else{
                sb.append("<tr><td>&nbsp;&nbsp;</td>"+NEWLINE);
            }
            sb.append("<td style=\"vertical-align:top;\">"+NEWLINE);
            sb.append("<input type=\"radio\" name=\""+NLS_ID+"\""+
                (nlsitem.getNLSID()==1?" checked=\"checked\"":"")+  // default to nlsid=1  US english
                " value=\""+nlsitem.getNLSID()+"\" id=\""+radioId+"\" /><label for=\""+radioId+"\">"+
                nlsitem+"</label>"+NEWLINE);

            sb.append("</td></tr>");
        }

        if (usePN) {  // add part number
            sb.append("<tr><td>"+
                "*"+bundle.getString("Label_PartNumber")+
                "</td>"+NEWLINE);
            sb.append("<td><label for=\""+PN_STR+"\"><input name=\""+PN_STR+"\" id=\""+ PN_STR + "\" type=\"text\" "+
                "size=\"10\" maxlength=\"7\" />"+"</label></td></tr>"+NEWLINE);
        }

        // add buttons
        sb.append("<tr><td style=\"text-align:right;\" colspan=\"2\">"+NEWLINE+
            "<table summary=\"layout\" cellspacing=\"3\">"+NEWLINE+
            "<tr><td><span class=\"button-blue\"><input type=\"submit\" name=\"okButton\" value=\" " + bundle.getString("Label_Ok") + " \"/></span></td>"+NEWLINE+
            "    <td><span class=\"button-blue\"><input type=\"button\" name=\"cancelButton\" value=\"" + bundle.getString("Label_Cancel") + "\" onclick=\"window.close();\"/>"+NEWLINE+
            "</span></td></tr></table>"+NEWLINE);
        sb.append("</td></tr></table>"+NEWLINE);
        sb.append("</form>");

        return sb.toString();
    }

    /********************************************************************************
    * build javascript to validate ctry list
    */
    private static String getCtryValidationScript(ResourceBundle bundle)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<script type=\"text/javascript\" language=\"javascript\">"+NEWLINE);
        sb.append("<!--"+NEWLINE);
        sb.append("function checkInput(ctryList) {"+NEWLINE);
        sb.append("if (ctryList.options.selectedIndex == -1) {"+NEWLINE);
        sb.append("   alert(\"" +bundle.getString("Error_SelectOneCountry") +"\");"+NEWLINE);
        sb.append("   ctryList.focus();"+NEWLINE);
        sb.append("   return false;}"+NEWLINE);

        sb.append("return true;}"+NEWLINE);
        sb.append("//  -->"+NEWLINE);
        sb.append("</script>"+NEWLINE);

        return sb.toString();
    }

    /********************************************************************************
    * build javascript to validate pn and ctry list
    */
    private static String getCtryPnValidationScript(ResourceBundle bundle)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<script type=\"text/javascript\" language=\"javascript\">"+NEWLINE);
        sb.append("<!--"+NEWLINE);
        sb.append("function checkInput(ctryList, pn) {"+NEWLINE);
        sb.append("if (ctryList.options.selectedIndex == -1) {"+NEWLINE);
        sb.append("   alert(\"" +bundle.getString("Error_SelectOneCountry") +"\");"+NEWLINE);
        sb.append("   ctryList.focus();"+NEWLINE);
        sb.append("   return false;}"+NEWLINE);

        sb.append("if (pn.value.length<7) {"+NEWLINE);
        sb.append("   alert(\"" +bundle.getString("Error_SelectPN") +"\");"+NEWLINE);
        sb.append("   pn.focus();"+NEWLINE);
        sb.append("   return false;}"+NEWLINE);

        sb.append("return true;}"+NEWLINE);
        sb.append("//  -->"+NEWLINE);
        sb.append("</script>"+NEWLINE);

        return sb.toString();
    }

    /*************************************************************************************
    * The selected COUNTRYLIST is then matched to General Area (GENERALAREA.GENAREANAME) to
    * get General Area Code (GENAREACODE) where GENAREATYPE = 'Country' (2452).
    *
    *@param dbCurrent       Database object
    *@param profile         Profile object
    *@param ctryflag        String with flag code
    *@param out             for debug
    *
    *@return String with countrycode
    *@throws COM.ibm.eannounce.objects.EANBusinessRuleException
    *@throws java.sql.SQLException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    *@throws java.rmi.RemoteException
    *@throws IOException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    */
    public static String getCountryCode(Database dbCurrent, Profile profile, String ctryflag,
        javax.servlet.jsp.JspWriter out)
        throws COM.ibm.eannounce.objects.EANBusinessRuleException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.rmi.RemoteException,
        IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        String actionName="SRDGENERALAREA";
        String genCode=null;
        RowSelectableTable searchTable;
        EntityList list;
        EntityGroup eg;

        // find any entity ids with this type that match the partnumber using a dynamic search
        SearchActionItem sai = new SearchActionItem(null, dbCurrent, profile, actionName);
        sai.setCheckLimit(false);  // allow more than 2500

        searchTable = sai.getDynaSearchTable(dbCurrent);
        if (searchTable==null){
			throw new SGCatException("Error using "+actionName+" search action.  No searchtable returned.");
		}

        // must get MetaFlags
        list = dbCurrent.getEntityList(
            profile,
            new ExtractActionItem(null, dbCurrent, profile, "DUMMY"),
            new EntityItem[] { new EntityItem(null, profile, "GENERALAREA", 0) });

        // add flag for GENAREANAME
        setupSearch(searchTable, list.getParentEntityGroup(), "GENAREANAME", ctryflag,out);
        // add GENAREATYPE = 'Country' (2452)
        setupSearch(searchTable, list.getParentEntityGroup(), "GENAREATYPE", "2452",out);

        searchTable.commit(dbCurrent);

        list = sai.executeAction(dbCurrent,profile);

        // debug display list of groups
        out.println("<!-- EntityList for "+actionName+" contains the following entities: ");
        out.println(PokUtils.outputList(list)+" -->");
        out.flush();

        eg = list.getEntityGroup("GENERALAREA");
        // group will be null if no matches are found
        if (eg!=null&&eg.getEntityItemCount() >0)
        {
            EntityItem genItem = eg.getEntityItem(0);
            // dont get flag code here
            genCode = PokUtils.getAttributeValue(genItem, "GENAREACODE","",null,false);
        }
        try{
            list.dereference();
        }catch(Exception e){
            // get nullptr sometimes.. ignore it
            System.out.println(e);
        }

        return genCode;
    }
    /*************************************************************************************
    * Find metaflag and select it for search
    *
    *@param searchTable
    *@param grp
    *@param attCode
    *@param flagValue
    *@param out
    *
    *@throws COM.ibm.eannounce.objects.EANBusinessRuleException
    *@throws IOException
    */
    private static void setupSearch(RowSelectableTable searchTable, EntityGroup grp, String attCode,
        String flagValue,javax.servlet.jsp.JspWriter out)
        throws COM.ibm.eannounce.objects.EANBusinessRuleException,
        IOException
    {
        String keyStr = "GENERALAREA:"+attCode;
        int row = searchTable.getRowIndex(keyStr);
        if (row < 0) {
            row = searchTable.getRowIndex(keyStr + ":C");
        }
        if (row < 0) {
            row = searchTable.getRowIndex(keyStr + ":R");
        }
        if (row != -1)
        {
            // must get MetaFlags
            EANMetaFlagAttribute metaFlagAtt =(EANMetaFlagAttribute)grp.getMetaAttribute(attCode);

            int count = metaFlagAtt.getMetaFlagCount();
            MetaFlag[] metaFlags = new MetaFlag[count];
            for (int i = 0; i < count; i++) {
                try {
                    metaFlags[i] = metaFlagAtt.getMetaFlag(i);
                    metaFlags[i].setSelected(false);
                } catch (Exception em) {
                    System.out.println(em);  // jtest req.. ignore it
                }
            }

            try {
                MetaFlag tempMetaFlag = metaFlagAtt.getMetaFlag(flagValue);
                if (tempMetaFlag != null) {
                    tempMetaFlag.setSelected(true);
                }
            } catch (Exception em) {
                System.out.println(em);  // jtest req.. ignore it
            }

            searchTable.put(row, 1, metaFlags); // add flags for this attcode
        }else{
            out.println("<!-- setupSearch() row not found for "+attCode+" -->");
        }
    }
}
