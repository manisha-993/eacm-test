// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2009  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

// $Log: BluePageEntry.java,v $
// Revision 1.16  2011/01/11 20:34:17  wendy
// Support collections sort with comparable
//
// Revision 1.15  2009/02/18 16:49:28  wendy
// CQ00021335 - BluePages API updates
//
// Revision 1.14  2005/01/27 03:40:37  dave
// More JTest cleanup
//
// Revision 1.13  2005/01/26 20:45:36  dave
// Jtest cleanup effort
//
// Revision 1.12  2004/12/07 23:48:02  gregg
// some fixes + use new URL for Blue Page search.
// (See comments at bottom of BluePageEntryGroup code.)
//
// Revision 1.11  2004/12/07 22:43:11  gregg
// fix
//
// Revision 1.10  2004/09/15 21:49:14  joan
// fix toString method to be shorter
//
// Revision 1.9  2004/08/13 16:55:38  roger
// Fix it
//
// Revision 1.8  2004/08/13 16:41:03  roger
// Fix it
//
// Revision 1.7  2004/08/13 16:25:22  roger
// Clean up
//
// Revision 1.6  2004/08/11 22:12:10  roger
// Needed lookup by LastName + FirstName and return multiple matches in a collection
//
// Revision 1.5  2004/08/11 21:57:57  roger
// New primitive constructor used by group
//
// Revision 1.4  2004/06/18 19:34:06  roger
// Validate the email address parm
//
// Revision 1.3  2004/06/18 19:29:34  roger
// Want to use specialized BluePageException
//
// Revision 1.2  2004/06/18 18:45:15  roger
// Needed generic property accessor
//
// Revision 1.1  2004/06/18 16:10:58  roger
// Support new BluePageEntry feature and object
//
//


package COM.ibm.opicmpdh.transactions;


import COM.ibm.opicmpdh.middleware.BluePageException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.mail.internet.InternetAddress;


/**
 * This holds a Blue Page entry from IBM BluePages
 * @version @date
 */
public class BluePageEntry extends Hashtable implements Comparable {
    /**
     * @serial
     */
    static final long serialVersionUID = 1L;
//  api location is not guaranteed now, must find it
   // private static final String BLUEPAGE_URL = "http://bluepages.ibm.com/BpHttpApis/wsapi?byInternetAddr=";

    /**
     * Main method which performs a simple test of this class
     *Item # [1] McCarty, Richard J. (Rick), rmccarty@us.ibm.com
Item # [2] McCarty, Rob *CONTRACTOR*, rmccarty@au1.ibm.com
Item # [3] MCCARTY, RICHARD J., rjmccart@us.ibm.com
Item # [4] McCarty, Robert *CONTRACTOR*, rmccarty@ca.ibm.com
     * @param arg 
     */
    public static void main(String arg[]) {

    	BluePageEntry bp = null;

    	try {
    		bp = new BluePageEntry(new InternetAddress("rmccart1@us.ibm.com"));
    	} catch (Exception x) {
    		System.out.println(x);
    	}
   		System.out.println("bp "+bp);
    	try {
    		bp = new BluePageEntry(new InternetAddress("rmccarty@us.ibm.com"));
    	} catch (Exception x) {
    		System.out.println(x);
    	}
   		System.out.println("bp "+bp);
    }

    /**
     * Creates an BluePageEntry Object
     *
     * @param _strbBuffer
     * @throws COM.ibm.opicmpdh.middleware.BluePageException 
     */
    public BluePageEntry(StringBuffer _strbBuffer) throws BluePageException {
        saveValues(_strbBuffer);

        if (this.size() < 1) {
            throw new BluePageException("Can't locate BluePage entry");
        }
    }

    /**
     * Creates an BluePageEntry Object
     *
     * @param _iaEmailAddress
     * @throws COM.ibm.opicmpdh.middleware.BluePageException 
     */
    public BluePageEntry(InternetAddress _iaEmailAddress) throws BluePageException 
    {
       	InputStreamReader isr = null;
    	BufferedReader rdr = null;
        try {
        	String s;
            String apiUrl = BluePageEntryGroup.getBluePageApiUrl(BluePageEntryGroup.BYINTERNETADDR);
            URL u = new URL(apiUrl+ _iaEmailAddress);

            isr = new InputStreamReader(u.openStream());
            rdr = new BufferedReader(isr);
            // save each name that has a value
            //INTERNET: rmccarty@us.ibm.com
            while((s=rdr.readLine()) !=null){
            	saveValue(s);
            }
        } catch (Exception x) {
        	x.printStackTrace();
            throw new BluePageException("Error creating BluePageEntry - " + x);
        } finally {
            if (isr != null) {
                try {
                	isr.close();
                } catch (Exception x) {}
            }
            if (rdr != null){
        		try {
					rdr.close();
				} catch (IOException e) {}
        		rdr = null;
        	}
        }

        if (this.size() < 1) {
            throw new BluePageException("Can't locate BluePage entry for "+_iaEmailAddress);
        }
    }
    /**
     * getName
     * @return String
     * @author Dave
     */
    public String getName() {
        return (String) this.get("NAME");
    }
    /**
     * getEmployeeNumber
     * 
     * @return String
     * @author Dave
     */
    public String getEmployeeNumber() {
        return (String) this.get("EMPNUM");
    }
    /**
     * getPhoneNumber
     * 
     * @return String
     * @author Dave
     */
    public String getPhoneNumber() {
        return (String) this.get("XPHONE");
    }
    /** 
     * getTieLine
     * @return String
     * @author Dave
     */
    public String getTieLine() {
        return (String) this.get("TIE");
    }
    /**
     * getEmail Address
     * @return
     * @author Dave
     */
    public String getEmailAddress() {
        return (String) this.get("INTERNET");
    }
    /**
     * getProperty
     * @param _strProperty
     * @return
     * @author Dave
     */
    public String getProperty(String _strProperty) {
        return (String) this.get(_strProperty);
    }
    /**
     * dump
     * @return
     * @author Dave
     */
    public final String dump() {
        StringBuffer strbResult = new StringBuffer();
        Enumeration e = this.keys();

        while (e.hasMoreElements()) {
            String strKey = (String) e.nextElement();
            String strValue = (String) this.get(strKey);

            strbResult.append(strKey + "=" + strValue + "\n");
        }

        return strbResult.toString();
    }

    private final void saveValues(StringBuffer _strbBuffer) {
        StringTokenizer stLine = new StringTokenizer(_strbBuffer.toString(), "\n");

        while (stLine.hasMoreTokens()) {
            String strLine = stLine.nextToken();
            if (strLine.length() > 0) {
            	saveValue(strLine);
            }
        }
    }
    private final void saveValue(String strLine) {
    	int iPos = strLine.indexOf(":");

    	if (iPos >= 0 && strLine.length() >= (iPos + 2)) {
    		// GB 120704: if no val then skip it

    		String strKey = strLine.substring(0, iPos);
    		String strValue = strLine.substring(iPos + 2);

    		this.put(strKey, strValue);
    	}
    }
    
    /**
     * Return the date/time this class was generated
     * @return String
     */
    public String getVersion() {
        return "$Id: BluePageEntry.java,v 1.16 2011/01/11 20:34:17 wendy Exp $";
    }
    /**
     * @see java.lang.Object#toString()
     * @author Dave
     */
    public String toString() {
        return getName() + ", " + getEmailAddress();
    }
    /*
     * BluePages HTTP APIs, v3.1
 For Reference:
 # rc=0, count=0, message=Success
COM.ibm.opicmpdh.middleware.BluePageException: Can't locate BluePage entry for rmccart1@us.ibm.com
bp null
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
# rc=0, count=1, message=Success
bp McCarty, Richard J. (Rick), rmccarty@us.ibm.com
     */

	public int compareTo(Object o) {
		String other = "";
		if(o!=null){
			other = o.toString().toLowerCase();
		}
		return this.toString().toLowerCase().compareTo(other);
	}
}
