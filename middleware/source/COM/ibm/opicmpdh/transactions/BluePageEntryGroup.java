// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2009  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

// $Log: BluePageEntryGroup.java,v $
// Revision 1.4  2009/02/18 16:49:13  wendy
// CQ00021335 - BluePages API updates
//
// Revision 1.3  2005/01/26 20:45:36  dave
// Jtest cleanup effort
//
// Revision 1.2  2004/12/07 23:48:02  gregg
// some fixes + use new URL for Blue Page search.
// (See comments at bottom of BluePageEntryGroup code.)
//
// Revision 1.1  2004/08/11 22:12:10  roger
// Needed lookup by LastName + FirstName and return multiple matches in a collection
//

package COM.ibm.opicmpdh.transactions;


import COM.ibm.opicmpdh.middleware.BluePageException;
import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;


/**
 * This holds a collection of Blue Page entries from IBM BluePages
 * @version @date
 */
public class BluePageEntryGroup extends Vector {
    /**
     * @serial
     */
    static final long serialVersionUID = 1L;

    //private static final String BLUEPAGE_URL = "http://bluepages.ibm.com/cgi-bin/bluepagesAPI.pl?allByName=";
    // api location is not guaranteed now, must find it
   //obsolete private static final String BLUEPAGE_URL = "http://bluepages.ibm.com/BpHttpApis/wsapi?allByName=";
    private static final String BLUEPAGE_API_URL = "http://bluepages.ibm.com/BpHttpApisv3/apilocator";
    public static final String ALLBYNAME = "allByName";
    public static final String BYINTERNETADDR = "byInternetAddr";
    
    /****************
     * api location is not guaranteed now, must find it
     * 
     * @param api
     * @return
     * @throws BluePageException
     */
    public static String getBluePageApiUrl(String api) throws BluePageException
    {
    	String apiurl=null;
    	InputStreamReader isr = null;
    	BufferedReader rdr = null;
        try {
        	String s="";
            URL locatorUrl = new URL(BLUEPAGE_API_URL);
            
            isr = new InputStreamReader(locatorUrl.openStream());
            rdr = new BufferedReader(isr);
            // look for NAME: api, then use next URL: line
            while((s=rdr.readLine()) !=null){
                if (s.startsWith("NAME:")){
                	String name = s.substring(5).trim();
                	if (name.equals(api)){
                		String url = rdr.readLine();
                		if (url!=null){
                			apiurl = url.substring(4).trim(); // remove leading 'URL:'
                			break;
                		}
                	}
                }
            }
        } catch (Exception x) {
        	x.printStackTrace();
            throw new BluePageException(
                "Error locating BluePageApi - " + x);
        }finally{
        	if (isr != null){
        		try {
					isr.close();
				} catch (IOException e) {}
        		isr = null;
        	}
        	if (rdr != null){
        		try {
					rdr.close();
				} catch (IOException e) {}
        		rdr = null;
        	}
        }

        if (apiurl==null) {
            throw new BluePageException("Can't locate BluePage api: "+api);
        }
    	
    	return apiurl;
    }

    /**
     * @param _strLastName
     * @param _strFirstName
     * @throws BluePageException
     */
    public BluePageEntryGroup(String _strLastName, String _strFirstName)
    throws BluePageException 
    {
       	InputStreamReader isr = null;
    	BufferedReader rdr = null;
        try {
        	String s="";
    		String apiUrl = getBluePageApiUrl(ALLBYNAME);
    		URL nameUrl =	new URL(apiUrl
    					+ _strLastName.trim()
    					+ "%2C+"
    					+ _strFirstName.trim()
    					+ "%");
            
            isr = new InputStreamReader(nameUrl.openStream());
            rdr = new BufferedReader(isr);
            // append lines until done
            StringBuffer sb = new StringBuffer();
            String firstrec=null;
            while((s=rdr.readLine()) !=null){
            	if (firstrec==null){ // hang onto the first record returned for an employee
            		int colonid = s.indexOf(":");
            		if (colonid!= -1){
            			firstrec = s.substring(0,colonid+1);
            		}else{ // not good but hardcode it
            			firstrec = "CNUM:";
            		}
            	}
            	if (s.startsWith(firstrec) && sb.length()>0){ // starting a new employee
            		this.add(new BluePageEntry(sb));
            		sb = new StringBuffer();
            	}
            	sb.append(s+"\n");
            }
        	if (sb.length()>0){ // add last employee
        		this.add(new BluePageEntry(sb));
        	}           
        } catch (Exception x) {
        	x.printStackTrace();
            throw new BluePageException(
                "Error locating BluePageEntryGroup - " + x);
        }finally{
        	if (isr != null){
        		try {
					isr.close();
				} catch (IOException e) {}
        		isr = null;
        	}
        	if (rdr != null){
        		try {
					rdr.close();
				} catch (IOException e) {}
        		rdr = null;
        	}
        }

    	if (this.size() < 1) {
    		throw new BluePageException("Can't locate BluePage entry last: "+_strLastName+" first: "+ _strFirstName);
    	}
    }
    /**
     * dump this object
     * @return
     * @author Dave
     */
    public final String dump() {

        StringBuffer strbResult = new StringBuffer();
        BluePageEntry bp = null;
        int i = 0;

        Enumeration e = this.elements();
        while (e.hasMoreElements()) {
            ++i;
            bp = (BluePageEntry) e.nextElement();
            strbResult.append("\nItem # [" + i + "] " + bp);
        }

        return strbResult.toString();
    }
    /**
     * Return the date/time this class was generated
     * @return String
     */
    public String getVersion() {
        return "$Id: BluePageEntryGroup.java,v 1.4 2009/02/18 16:49:13 wendy Exp $";
    }
    /**
     * @see java.lang.Object#toString()
     * @author Dave
     */
    public String toString() {
        return this.dump();
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {

        BluePageEntryGroup bpg = null;

        try {
            System.out.println("get "+ALLBYNAME+" "+getBluePageApiUrl(ALLBYNAME));
            System.out.println("get "+BYINTERNETADDR+" "+getBluePageApiUrl(BYINTERNETADDR));
            bpg = new BluePageEntryGroup("McCarty", "R");
        } catch (Exception x) {
            System.out.println("Can't find blue page entry " + x);
        }

        System.out.println("BluePageEntryGroup: " + bpg);
    }
    /*
     * BluePages HTTP APIs, v3.1
    For Reference:

    http://bluepages.ibm.com/BpHttpApisv3/apilocator returns
    NAME: workLoc
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?workLoc=
    NAME: bySerial
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?bySerial=
    NAME: allByNotesIDLite
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?allByNotesIDLite=
    NAME: managerChainFor
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?managerChainFor=
    NAME: depts
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?depts=
    NAME: byInternetAddr
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?byInternetAddr=
    NAME: membersOfDeptLite
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?membersOfDeptLite=
    NAME: directReportsOf
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?directReportsOf=
    NAME: byCnum
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?byCnum=
    NAME: eCodes
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?eCodes=
    NAME: allByNotesID
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?allByNotesID=
    NAME: SLAPHAPI
    URL: http://bluepages.ibm.com/BpHttpApisv3/slaphapi?
    NAME: cCodes
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?cCodes=
    NAME: allByNameLite
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?allByNameLite=
    NAME: allByNameFuzzy
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?allByNameFuzzy=
    NAME: allByNameFuzzyLite
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?allByNameFuzzyLite=
    NAME: membersOfDept
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?membersOfDept=
    NAME: allByName
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?allByName=
    NAME: directReportsOfLite
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?directReportsOfLite=
    NAME: orgCodes
    URL: http://bluepages.ibm.com/BpHttpApisv3/wsapi?orgCodes=
    # rc=0, count=20, message=Success

    BluePageEntryGroup url:http://bluepages.ibm.com/BpHttpApisv3/wsapi?allByName=McCarty%2C+R%
    CNUM: 987109897
    TIE: 678-8703
    TIEA:
    XPHONE: 1-512-838-8703
    XPHONEA:
    INFOPHONES:
    FAXTIE:
    FAX: 1-512-436-1788
    PAGER:
    PAGERID:
    PAGERTYPE:
    CELLULAR: 1-512-797-7786
    NAME: McCarty, Richard J. (Rick)
    DEPT: L0LA
    DIV: 46
    EMPTYPE: P
    MGR: N
    SHIFT: 1
    JOBRESPONSIB: Security Architect and Practice Leader
    ADDITIONAL:
    EMAILADDRESS: CN=Rick McCarty/OU=Austin/O=IBM@IBMUS
    NOTESID: CN=Rick McCarty/OU=Austin/O=IBM@IBMUS
    INTERNET: rmccarty@us.ibm.com
    USERID: RMCCARTY
    NODE: IBMUS
    USERIDA: MCCARTY
    NODEA: RALVM17
    BLDG: 901
    OFFICE: HOME
    FLOOR: NA
    WORKLOC: BNT
    LOCCITY:
    C: us
    COUNTRY: USA
    COMPANY:
    IMAD: 9133
    DIRECTORY: AUSTIN
    MGRNUM: 812139
    MGRCC: 897
    EMPNUM: 987109
    EMPCC: 897
    PDIF: 1
    BACKNUM: 808916
    BACKCC: 897
    SECNUM:
    SECCC:
    WORKPLACEIND: H
    ORGCODE: T2
    HRACTIVE: A
    HRASSIGNEE:
    HRASSIGNMENT:
    HRCOMPANYCODE: 0147
    HRCOUNTRYCODE: US
    HRDEPARTMENT: 46L0LA
    HRDIVISION: 46
    HREMPLOYEETYPE: RF
    HRFAMILYNAME: J
    HRFIRSTNAME: Richard
    HRINITIAL: RJ
    HRLASTNAME: McCarty
    HRMANAGERSERIAL: 812139
    HRMANAGERINDICATOR: N
    HRMANAGERPSC: 897
    HRMIDDLENAME: J
    HROTHERNAME:
    HRPREFERREDNAME: Rick
    HRPSC: 897
    HRSERIALNUMBER: 987109
    MNUM: 812139897
    CNUM: 095611616
    TIE:
    TIEA:
    XPHONE:
    XPHONEA:
    INFOPHONES:
    FAXTIE:
    FAX:
    PAGER:
    PAGERID:
    PAGERTYPE:
    CELLULAR:
    NAME: McCarty, Rob *CONTRACTOR*
    DEPT: E0S
    DIV: 02
    EMPTYPE: C
    MGR: N
    SHIFT:
    JOBRESPONSIB: IT Specialist
    ADDITIONAL:
    EMAILADDRESS: CN=Rob McCarty/OU=Australia/OU=Contr/O=IBM@IBMAU
    NOTESID: CN=Rob McCarty/OU=Australia/OU=Contr/O=IBM@IBMAU
    INTERNET: rmccarty@au1.ibm.com
    USERID: RMCCARTY
    NODE: IBMAU
    USERIDA:
    NODEA:
    BLDG: SOUT
    OFFICE: SG16
    FLOOR: 16
    WORKLOC: SGA
    LOCCITY: Melbourne
    C: au
    COUNTRY: Australia
    COMPANY: IBM
    IMAD:
    DIRECTORY: DRMS
    MGRNUM: 705748
    MGRCC: 616
    EMPNUM: 095611
    EMPCC: 616
    PDIF: 1
    BACKNUM:
    BACKCC:
    SECNUM:
    SECCC:
    WORKPLACEIND: S
    ORGCODE: KQ
    HRACTIVE:
    HRASSIGNEE:
    HRASSIGNMENT:
    HRCOMPANYCODE: 0008
    HRCOUNTRYCODE:
    HRDEPARTMENT:
    HRDIVISION:
    HREMPLOYEETYPE:
    HRFAMILYNAME:
    HRFIRSTNAME:
    HRINITIAL:
    HRLASTNAME:
    HRMANAGERSERIAL:
    HRMANAGERINDICATOR:
    HRMANAGERPSC:
    HRMIDDLENAME:
    HROTHERNAME:
    HRPREFERREDNAME:
    HRPSC:
    HRSERIALNUMBER:
    MNUM: 705748616
    CNUM: 6D7171897
    TIE: 340-2133
    TIEA:
    XPHONE: 1-631-269-3400
    XPHONEA:
    INFOPHONES:
    FAXTIE:
    FAX:
    PAGER:
    PAGERID:
    PAGERTYPE:
    CELLULAR: 1-917-626-0456
    NAME: MCCARTY, RICHARD J.
    DEPT: 3IPA
    DIV: 12
    EMPTYPE: P
    MGR: N
    SHIFT: 1
    JOBRESPONSIB: Storage Sales Specialist
    ADDITIONAL:
    EMAILADDRESS: CN=Richard J McCarty/OU=Jericho/O=IBM@IBMUS
    NOTESID: CN=Richard J McCarty/OU=Jericho/O=IBM@IBMUS
    INTERNET: rjmccart@us.ibm.com
    USERID: RJMCCART
    NODE: IBMUS
    USERIDA:
    NODEA:
    BLDG: 8187
    OFFICE: MOBILE
    FLOOR: NA
    WORKLOC: JCO
    LOCCITY:
    C: us
    COUNTRY: USA
    COMPANY:
    IMAD:
    DIRECTORY: NEAREA
    MGRNUM: 453222
    MGRCC: 897
    EMPNUM: 6D7171
    EMPCC: 897
    PDIF: 1
    BACKNUM:
    BACKCC:
    SECNUM:
    SECCC:
    WORKPLACEIND: M
    ORGCODE: GF
    HRACTIVE: A
    HRASSIGNEE:
    HRASSIGNMENT:
    HRCOMPANYCODE: 0147
    HRCOUNTRYCODE: US
    HRDEPARTMENT: 123IPA
    HRDIVISION: 12
    HREMPLOYEETYPE: RF
    HRFAMILYNAME: J
    HRFIRSTNAME: RICHARD
    HRINITIAL: RJ
    HRLASTNAME: MCCARTY
    HRMANAGERSERIAL: 453222
    HRMANAGERINDICATOR: N
    HRMANAGERPSC: 897
    HRMIDDLENAME: J
    HROTHERNAME:
    HRPREFERREDNAME: RICHARD
    HRPSC: 897
    HRSERIALNUMBER: 6D7171
    MNUM: 453222897
    CNUM: A796BZCA7
    TIE:
    TIEA:
    XPHONE: 1-403-476-6401
    XPHONEA: 1-403-476-6300
    INFOPHONES:
    FAXTIE:
    FAX:
    PAGER:
    PAGERID:
    PAGERTYPE:
    CELLULAR:
    NAME: McCarty, Robert *CONTRACTOR*
    DEPT: IP4
    DIV: FN
    EMPTYPE: C
    MGR: N
    SHIFT: 1
    JOBRESPONSIB: network support
    ADDITIONAL:
    EMAILADDRESS: CN=Robert McCarty/OU=Canada/OU=AT&T/O=IDE@IBMCA
    NOTESID: CN=Robert McCarty/OU=Canada/OU=AT&T/O=IDE@IBMCA
    INTERNET: rmccarty@ca.ibm.com
    USERID: RMCCARTY
    NODE: IBMCA
    USERIDA:
    NODEA:
    BLDG: R48
    OFFICE: ATT
    FLOOR: 06
    WORKLOC: 1AC
    LOCCITY:
    C: ca
    COUNTRY: CANADA
    COMPANY:
    IMAD:
    DIRECTORY: DRMS
    MGRNUM: 054113
    MGRCC: 649
    EMPNUM: A796BZ
    EMPCC: CA7
    PDIF:
    BACKNUM:
    BACKCC:
    SECNUM:
    SECCC:
    WORKPLACEIND: S
    ORGCODE: 9Y
    HRACTIVE:
    HRASSIGNEE:
    HRASSIGNMENT:
    HRCOMPANYCODE: 0026
    HRCOUNTRYCODE:
    HRDEPARTMENT:
    HRDIVISION:
    HREMPLOYEETYPE:
    HRFAMILYNAME:
    HRFIRSTNAME:
    HRINITIAL:
    HRLASTNAME:
    HRMANAGERSERIAL:
    HRMANAGERINDICATOR:
    HRMANAGERPSC:
    HRMIDDLENAME:
    HROTHERNAME:
    HRPREFERREDNAME:
    HRPSC:
    HRSERIALNUMBER:
    MNUM: 054113649
    # rc=0, count=4, message=Success
    */    
}

