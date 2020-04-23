/**
 * <pre>
 * CR1010032718
 *
 * World Wide Master Index and Offering Contract Invoice Title
 *
 * The World Wide Master Index (OF.OFWWMI and VAR.VARWWMI) and the Offering Contract Invoice Title
 * (OF.OFCONTRCTINVTITLE and VAR.VARCONTRCTINVTITLE) are derived from attributes on the series and SBB/elements
 * attached to the offering.  The attributes that are used to compose the derived data differ based on the offering
 * type and brand code value in the related Project.  While we anticipate that the system should derive this data
 * properly, exceptions are expected.  To accommodate, DIVADMIN will retain write access to this field while all
 * other roles will change to read access.
 *
 * Element information can be found via direct links from the Offering (OF), under a Sales Building Block (SBB)
 * via a link to the OF, and under an SBB linked to a Variant (VAR).  In all cases documented below, OFWWMI is
 * interchangeable with VARWWMI and OFCONTRCTINVTITLE is interchangeable with VARCONTRCTINVTITLE based on the entity
 * type of the launching entity.
 *
 * The WWMI field has a 60 character limit and we have requested limits on specific pieces of data to stay within
 * this limit.  The Contract Invoice Title field has a 28 character limit and, in addition to limiting individual
 * attribute lengths, it is requested that the ABR only include each successive attribute if it does not take the
 * complete derived string over the 28 character minimum.  Do not include partial data (e.g. If the string is 26
 * characters after you add the Operating System abbreviation, you would not add a partial string from the Optical
 * Device type/speed or any further data).  In the cases where data is not found, the data will be skipped and the
 * space following it, if requested, should be skipped.
 *
 * Note that some of the fields used in these calculations are derived data themselves and having their calculations
 * complete would be a pre-requisite to running this ABR. The other alternative is to calculate those in the same ABR.
 * This ABR would be run manually using a workflow action for selected offerings or variants.
 *
 *
 * (c) Copyright International Business Machines Corporation, 2003
 * All Rights Reserved.
 *
 * $Log: WWMIABR.java,v $
 * Revision 1.7  2006/01/24 22:15:37  yang
 * Jtest changes
 *
 * Revision 1.6  2005/10/06 14:59:17  wendy
 * Conform to new jtest config
 *
 * Revision 1.5  2005/02/08 18:29:11  joan
 * changes for Jtest
 *
 * Revision 1.4  2005/01/31 16:30:07  joan
 * make changes for Jtest
 *
 * Revision 1.3  2004/01/05 15:26:57  wendy
 * Allow truncation of first value (seriesname may exceed 28 chars)
 *
 * Revision 1.2  2003/12/18 14:28:24  chris
 * Change report class to WWABR
 *
 * Revision 1.1  2003/12/17 19:28:44  chris
 * Initial for CR1010032718
 *
 *
 * </pre>
 *
 *@author     Wendy Stimpson
 *@created    December 10, 2003
 */
package COM.ibm.eannounce.abr.psg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import java.util.*;
import java.text.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Administrator
 */
public class WWMIABR extends PokBaseABR
{
    private ResourceBundle bundle = null;

    private StringBuffer rptSb = new StringBuffer();
    private StringBuffer traceSb = new StringBuffer();

    private static final String PR_BRANDCODE_CDT = "10010"; // spec has "CDT" (0020), "Intellistation" (0030)
    private static final String PR_BRANDCODE_INTELLISTATION = "10011";
    private static final String PR_BRANDCODE_NETFINITY = "10015"; // spec has Netfinity (0070)
    private static final String PR_BRANDCODE_MOBILE = "10014";   // spec has Mobile (0060)
    private static final String OF_OFFERINGTYPE_SYSTEM = "0080";
    private static final String FAM_FAMILYNAME_THINKPAD = "0330";
    private static final String FAM_FAMILYNAME_WORKPAD = "0340";
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    private static final int CHAR_LIMIT =15;

/*
From note to Pam:
During testing I have found that some of the SE.SERIESNAME values exceed 28 characters.  The spec states that partial
values should not be used.  In this case the (OF/VAR)CONTRCTINVTITLE will not have a value but the (OF/VAR)WWMI will.
How should I handle this situation?  Should I set the WWMI attribute in this case?  Should the ABR be marked as Passed
when I can calculate one value but not the other?

    You can show partial values for the first value in the derivation in any case so we have at least some data appearing
    in the field.  This should cover both fields.

I have also found that when PRC.PROCMFR = 'AMD' some of the flag short descriptions for the  PRC.PROCESSORTYPE are blank
and some exceed 15 characters.  Should I truncate these values to the specified 15 characters?

    We'll clean this up in production, but yes, take the first 15 characters only.  In the cases where I give a character
    limitation, you can truncate the data.  The truncation issue was mainly for the last possible value that fit in the
    field based on length.  If it doesn't fit within either the limit of the field length or in the truncation length provided, it shouldn't be shown (the exception being the first value in the derivation as stated above).

Also, just a reminder, I need to know if the values can be truncated in the Default cases.  The OF.OFFERINGPNUMBDESC or
VAR.VARPNUMBDESC may exceed 28 characters.

    Yes, please truncate these to show as much as you can for each field.

*/
// not following business rules or process when updating, some required attributes are not set!
    private static final int WWMI_LIMIT = 60; // max of 60 chars
    private static final int CONTRCTINVTITLE_LIMIT = 28; // max of 28 chars

    /**
     *  Execute ABR.
     *
     */
    public void execute_run()
    {
        String HEADER =
        "<html><head><title>{0} {1}</title></head>"+NEWLINE+"<body><h1>{0}: {1}</h1>"+NEWLINE+
        "<p><b>Date: </b>{2}<br/>"+NEWLINE+"<b>User: </b>{3} ({4})<br />"+NEWLINE+"<b>Description: </b>{5}</p>"+NEWLINE+"<!-- {6} -->";
        String navName = "";
        MessageFormat msgf = null;
        Object[] args = new String[10];
        try {
            DerivedString bufWWMI,bufCONTRCTINVTITLE;
            boolean success;

            start_ABRBuild();

            bundle = ResourceBundle.getBundle(this.getClass().getName(), getLocale(m_prof.getReadLanguage().getNLSID()));

            traceSb.append("WWMIABR entered for "+getEntityType()+":"+getEntityID());
            // debug display list of groups
            traceSb.append(NEWLINE+"EntityList contains the following entities: ");
            for (int i=0; i<m_elist.getEntityGroupCount(); i++)
            {
                EntityGroup eg =m_elist.getEntityGroup(i);
                traceSb.append(NEWLINE+""+eg.getEntityType()+" : "+eg.getEntityItemCount()+" entity items. ");
                if (eg.getEntityItemCount()>0)
                {
                    traceSb.append("IDs(");
                    for (int e=0; e<eg.getEntityItemCount(); e++) {
                        traceSb.append(" "+eg.getEntityItem(e).getEntityID());
                    }
                    traceSb.append(")");
                }
            }
            traceSb.append(NEWLINE+"");

            // NAME is navigate attributes
            navName = getNavigationName();

            bufWWMI = new DerivedString(WWMI_LIMIT,"WWMI:");
            bufCONTRCTINVTITLE = new DerivedString(CONTRCTINVTITLE_LIMIT,"CONTRCTINVTITLE:");

            success = deriveWWMIandCONTRCTINVTITLE(bufWWMI,bufCONTRCTINVTITLE);
            if(success)  // rptSb will have error msg if failure occurred
            {
//              success = updatePDHwithBusinessRules(bufCONTRCTINVTITLE.toString(), bufWWMI.toString());

                if (bufWWMI.getCurLen()>0)
                {
                    setTextDirectly(getEntityType()+"WWMI", bufWWMI.toString().trim()); // trim trailing blanks.. could happen if data is missing
                }
                else
                {
                    EntityGroup eg = m_elist.getParentEntityGroup();
                    EANMetaAttribute mAttr = eg.getMetaAttribute(getEntityType()+"WWMI");
                    msgf = new MessageFormat(bundle.getString("Error_DERIVE_MSG"));
                    args[0] = mAttr.getActualLongDescription();
                    rptSb.append(msgf.format(args));
                    traceSb.append(NEWLINE+""+getEntityType() + "WWMI derivation failed. String was empty!");
                }
                if (bufCONTRCTINVTITLE.getCurLen()>0)
                {
                    setTextDirectly(getEntityType()+"CONTRCTINVTITLE", bufCONTRCTINVTITLE.toString().trim());// trim trailing blanks.. could happen if data is missing
                }
                else
                {
                    EntityGroup eg = m_elist.getParentEntityGroup();
                    EANMetaAttribute mAttr = eg.getMetaAttribute(getEntityType() + "CONTRCTINVTITLE");
                    msgf = new MessageFormat(bundle.getString("Error_DERIVE_MSG"));
                    args[0] = mAttr.getActualLongDescription();
                    rptSb.append(msgf.format(args));
                    traceSb.append(NEWLINE+""+getEntityType() + "CONTRCTINVTITLE derivation failed. String was empty!");
                }
                // fail if both values could not be updated..
                if (bufWWMI.getCurLen()==0 && bufCONTRCTINVTITLE.getCurLen()==0) {// neither could be calculated
                    success = false;
                }
            }
            else {
                setReturnCode(FAIL);
            }

            if(success) {  // rptSb will have error msg if failure occurred
                setReturnCode(PASS);

            } else {
                setReturnCode(FAIL);
            }
        }
        catch (Exception exc)
        {
            String Error_EXCEPTION="<h3><font color=red>Error: {0}</font></h3>";
            String Error_STACKTRACE="<pre>{0}</pre>";
            java.io.StringWriter exBuf = new java.io.StringWriter();
            // Put exception into document
            msgf = new MessageFormat(bundle==null?Error_EXCEPTION:bundle.getString("Error_EXCEPTION"));
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            args[0] = exc.getMessage();
            rptSb.append(msgf.format(args));
            msgf = new MessageFormat(bundle==null?Error_STACKTRACE:bundle.getString("Error_STACKTRACE"));
            args[0] = exBuf.getBuffer().toString();
            rptSb.append(msgf.format(args));
            logError(exBuf.getBuffer().toString());
            setReturnCode(FAIL);
        }
        finally
        {
            setDGTitle(navName);
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass("WWABR");
            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
        }

        // Print everything up to </html>
/*
HEADER = "<html><head><title>{0} {1}</title></head>\n<body><h1>{0}: {1}</h1>\n
<p><b>Date: </b>{2}<br/>\n
<b>User: </b>{3} ({4})<br />\n
<b>Description: </b>{5}</p>\n
<!-- {6} -->";*/
        logMessage("building rpt bdl: "+bundle);

        // Insert Header into beginning of report
        msgf = new MessageFormat(bundle==null?HEADER:bundle.getString("HEADER"));
        args[0] = getShortClassName(getClass());
        args[1] = navName+((getReturnCode() == PASS) ? " Passed" : " Failed");
        args[2] = getNow();
        args[3] = m_prof.getOPName();
        args[4] = m_prof.getRoleDescription();
        args[5] = getDescription();
        args[6] = getABRVersion();
        rptSb.insert(0, msgf.format(args));
        rptSb.append("<!-- DEBUG: "+traceSb.toString()+" -->");

        println(rptSb.toString()); // Output the Report
        printDGSubmitString();
        buildReportFooter();    // Print </html>

    }

    /************************************************************************************************
    The following information describes the expected value based on the Offering Type and Brand Code.
    The data should be included with the exact spacing and in the exact order specified.

    If (EntityType = VAR) or (EntityType = OF and OF.OFFERINGTYPE = "SYSTEM" (0080))
    Select Case PR.BRANDCODE

    Case "CDT" (0020), "Intellistation" (0030)
    World Wide Master Index (OFWWMI): CDT, Intellistation
    1). SE.SERIESNAME, one character space, then '*', one character space,
    2). DD.NUMPROCSTD (if value of NUMPROCSTD is greater than 1, show/type a lower case 'x' immediately following the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x' OR the value of 1 itself)
    3). If PRC.PROCMFR = 'AMD', then show the flag value short description for PRC.PROCESSORTYPE, (char limit of 15), one character space; otherwise show PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), show PRC.PROCCLKSPDUNITS only if the value is 'GHz', one character space,
    4a). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field), no space, DD.TOTL2CACHESTDUNITS, one character space,
    4b). If MB.L3CACHE is populated and > 0 then include the text 'L2', one character space, MB.L3CACHE (char limit of 4 imposed on the attribute/field), no space, MB.L3CACHEUNITS, one character space, the text 'L3', one character space,
    5). DD.MEMRAMSTD (char  limit of 4 imposed on the attribute/field), no space, DD.MEMRAMSTDUNITS, one character space,
    6). HD.HDDCAPACITY, no space, HD.HDDCAPACITYUNITS, one character space,
    7). HDC.INTERFACEBUS_CO (char limit of 4 imposed on the attribute/field), one character space,
    8). POS.POSABBREV, one character space,
    9). CDR.CDROPTITYPE (if the value of this attribute/field is 'CD-ROM' (0010) then go to the CDSPEED of this CDR element and use only the first three characters of the CDSPEED value; if the value of the CDROPTITYPE is not 'CD-ROM' then use whatever the value is), one character space,
    10). MB.TOTCARDSLOTS, no space, show/type a lower case 'x' immediately following this attribute value, no space,
    11). PP.TOTBAYS.

    *Example of above using a NUMPROCSTD value of 1 (see #2 directly above) and GHz (#3):
    OFWWMI = A21 * 1GHz 512KB 128MB 40GB ATA W2000 48X 2x3
    *Example of above using a NUMPROCSTD value of 2 (see #2 directly above) and L3CACHE (#4):
    OFWWMI = A21 * 2x933 512KB L2 1GB L3 128MB 40GB ATA W2000 48X 2x3
    *Example of above using a NUMPROCSTD value of 1 (see #2 above), AMD (#3), and L3CACHE (#4):
    OFWWMI = A21 * Opteron 240 512KB L2 1GB L3 128MB 40GB ATA W2000 48X 2x3
    Offering Contract Invoice Title (OFCONTRCTINVTITLE): CDT, Intellistation
    1). SE.SERIESNAME, one character space,
    2). DD.NUMPROCSTD (if value of NUMPROCSTD is greater than 1, show/type a lower case 'x' immediately following the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x' OR the value of 1 itself)
    3). PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), if PRC.PROCCLKSPDUNITS = 'GHz' then show 'G', one character space,
    4). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field), one character space,
    5). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field), no space, type a ' / ' immediately following the number with no spaces before or after the ' / ',
    6). HD.HDDCAPACITY, one character space,
    7). POS.POSABBREV, one character space,
    8). CDR.CDROPTITYPE (if the value of this attribute/field is 'CD-ROM' then go to the CDSPEED of this CDR element and use only the first three characters of the CDSPEED value; if the value of the CDROPTITYPE is not 'CD-ROM' then use whatever the value is).
    *Example of above using a NUMPROCSTD value of 1 (see #2 directly above) and GHz (#3):
    OFCONTRCTINVTITLE= A21 1G 512 128/40 W2000 48X
    *Example of above using a NUMPROCSTD value of 2 (see #2 directly above):
    OFCONTRCTINVTITLE = A21 2x933 512 128/40 W2000 48X
    In this example the derived calculated data is over 28 characters, data remaining after 28 should drop off see below:
    OFCONTRCTINVTITLE = A21 2x933 512 128/40 W2000
    Case "Netfinity" (0070)
    World Wide Master Index (OFWWMI): Netfinity
    1). SE.SERIESNAME, one character space, then '*', one character space,
    2). DD.NUMPROCSTD (if value of NUMPROCSTD is greater than 1, show/type a lower case 'x' immediately following the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x' OR the value of 1 itself), one character space,
    3). If PRC.PROCMFR = 'AMD', then show the flag value short description for PRC.PROCESSORTYPE, (char limit of 15), one character space; otherwise show PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), show PRC.PROCCLKSPDUNITS only if the value is 'GHz', one character space,
    4). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field), no space, DD.TOTL2CACHESTDUNITS, one character space,
    5). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field), no space, DD.MEMRAMSTDUNITS, one character space,
    6). DD.NUMINSTHD (if value of NUMINSTHD is greater than 1, show/type a lower case 'x' immediately following the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x' OR the value of 1 itself),
    7). HD.HDDCAPACITY, no space, HD.HDDCAPACITYUNITS, one character space,
    8). HDC.INTERFACEBUS_CO (char limit of 4 imposed on the attribute/field), one character space,
    9). MB.TOTCARDSLOTS, no space, show/type a lower case 'x' immediately following this attribute value, no space,
    10). PP.TOTBAYS.

    *Example of above using a NUMPROCSTD value of 1 (see #2 directly above) and a NUMINSTHD value of 1 (see #6 directly above)
    OFWWMI = A21 * 933 512KB 128MB 40GB ATA 2x3
    *Example of above using a NUMPROCSTD value of 2 (see #2 directly above) and AMD (#3):
    OFWWMI = A21 * 2xOpteron 240 512KB 128MB 2x40GB ATA 2x3
    Offering Contract Invoice Title (OFCONTRCTINVTITLE): Netfinity
    1). SE.SERIESNAME, one character space,
    2). DD.NUMPROCSTD (if value of NUMPROCSTD is greater than 1, show/type a lower case 'x' immediately following the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x' OR the value of 1 itself), one character space,
    3). PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), if PRC.PROCCLKSPDUNITS = 'GHz' then show 'G', one character space,
    4). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field),  one character space,
    5). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field), type a ' / ' immediately following the number with no spaces before or after the ' / '
    6). DD.NUMINSTHD (if value of NUMINSTHD is greater than 1, show/type a lower case 'x' immediately following the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x' OR the value of 1 itself),
    7). HD.HDDCAPACITY.

    *Example of above using a NUMPROCSTD value of 1 (see #2 directly above) and a NUMINSTHD value of 1 (see #6 directly above) and GHz (#3)
    OFCONTRCTINVTITLE = A21 1.2G 512 128/40
    *Example of above using a NUMPROCSTD value of 2 (see #2 directly above):
    OFCONTRCTINVTITLE = A21 2x933 512 128/2x40

    Case "Mobile" (0060)
    IF FAM.FAMILYNAME Contains ThinkPad (0330)
    World Wide Master Index (OFWWMI): Mobile | ThinkPad
    1). SE.SERIESNAME, one character space, then '*', one character space,
    2). If PRC.PROCMFR = 'AMD', then show the flag value short description for PRC.PROCESSORTYPE, (char limit of 15), one character space; otherwise show PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), show PRC.PROCCLKSPDUNITS only if the value is 'GHz', one character space,
    3). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field), no space, DD.TOTL2CACHESTDUNITS, one character space,
    4). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field), no space, DD.MEMRAMSTDUNITS, one character space,
    5). HD.HDDCAPACITY, no space, HD.HDDCAPACITYUNITS, one character space,
    6). POS.POSABBREV, one character space,
    7). MON.SCREENSIZENOM_IN, one character space,
    8). MON.TUBETYPE, one character space (char limit of 4 imposed on the attribute/field),
    9). CDR.CDROPTITYPE (if the value of this attribute/field is 'CD-ROM' then go to the CDSPEED of this CDR element and use only the first three characters of the CDSPEED value; if the value of the CDROPTITYPE is not 'CD-ROM' then use whatever the value is)

    *Example of above not AMD (#3):
    OFWWMI = A21 * 933 512KB 128MB 40GB W2000 14.1 TFT active matrix LCD 48X
    *Example of above using AMD (#3):
    OFWWMI = A21 * Opteron 240 512KB 128MB 40GB W2000 14.1 TFT active matrix LCD 48X

    Offering Contract Invoice Title (OFCONTRCTINVTITLE): Mobile | ThinkPad
    1). SE.SERIESNAME, one character space,
    2). PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), if PRC.PROCCLKSPDUNITS = 'GHz' then show 'G', one character space,
    3). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field), one character space,
    4). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field, type a ' / ' immediately following the number with no spaces before or after the ' / '
    5). HD.HDDCAPACITY, one character space,
    6). POS.POSABBREV, one character space,
    7). MON.SCREENSIZENOM_IN, one character space,
    8). CDR.CDROPTITYPE (if the value of this attribute/field is 'CD-ROM' then go to the CDSPEED of this CDR element and use only the first three characters of the CDSPEED value; if the value of the CDROPTITYPE is not 'CD-ROM' then use whatever the value is)

    *Example of above:
    OFCONTRCTINVTITLE = A21 512 128/40 W2000 14.1 48X

    ELSE IF FAM.FAMILYNAME Contains "WorkPad" (0340)
    World Wide Master Index (OFWWMI): Mobile | WorkPad
    1). SE.SERIESNAME, one character space, then '*', one character space,
    2). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field), no space, DD.MEMRAMSTDUNITS.

    *Example of above:
    OFWWMI = A21 * 128MB

    Offering Contract Invoice Title (OFCONTRCTINVTITLE): Mobile | WorkPad
    1). SE.SERIESNAME, one character space,
    2). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field).

    *Example of above:
    OFCONTRCTINVTITLE = A21 128
    ELSE
    World Wide Master Index (OFWWMI): Default
    OF: OF.OFWWMI = OF.OFFERINGPNUMBDESC
    VAR:    VAR.VARWWMI = VAR.VARPNUMBDESC

    Offering Contract Invoice Title (OFCONTRCTINVTITLE): Default
    OF: OF.OFCONTRCTINVTITLE = OF.OFFERINGPNUMBDESC
    VAR:    VAR.VARCONTRCTINVTITLE = VAR.VARPNUMBDESC

    Case Else (Default, Includes Options/Services)
    World Wide Master Index (OFWWMI): Default
    OF: OF.OFWWMI = OF.OFFERINGPNUMBDESC
    VAR:    VAR.VARWWMI = VAR.VARPNUMBDESC

    Offering Contract Invoice Title (OFCONTRCTINVTITLE): Default
    OF: OF.OFCONTRCTINVTITLE = OF.OFFERINGPNUMBDESC
    VAR:    VAR.VARCONTRCTINVTITLE = VAR.VARPNUMBDESC

    End Select

    If (EntityType = OF) and (OF.OFFERINGTYPE <> "SYSTEM" (0080))   (Else)

    World Wide Master Index (OFWWMI): Default
    OFWWMI = OFFERINGPNUMBDESC

    Offering Contract Invoice Title (OFCONTRCTINVTITLE): Default
    OFCONTRCTINVTITLE = OFFERINGPNUMBDESC

    */
    private boolean deriveWWMIandCONTRCTINVTITLE(DerivedString bufWWMI, DerivedString bufCONTRCTINVTITLE)
    {
        String strOFType = "";
        String strBrand = "";
        String strFamily = "";

        boolean success = true;

        // get the PR.BRANDCODE
        EntityGroup eg = m_elist.getEntityGroup("PR");
        if (eg!=null && eg.getEntityItemCount()>0)
        {
            egDebugInfo(eg);
            strBrand = getAttributeFlagEnabledValue("PR",eg.getEntityItem(0).getEntityID(), "BRANDCODE", "");
            traceSb.append(NEWLINE+"PR.BRANDCODE = ["+strBrand+"] "+
                    getAttributeValue("PR", eg.getEntityItem(0).getEntityID(), "BRANDCODE",""));
        }
        else {
            traceSb.append(NEWLINE+"PR EntityGroup not found");
        }

        // if root entity is OF, get OF.OFFERINGTYPE
        if ("OF".equals(getEntityType()))
        {
            strOFType = getAttributeFlagEnabledValue("OF",getEntityID(), "OFFERINGTYPE", "");
            traceSb.append(NEWLINE+"OF.OFFERINGTYPE = ["+strOFType+"] "+
                    getAttributeValue("OF",getEntityID(), "OFFERINGTYPE", ""));
        }

        // get the family name
        eg = m_elist.getEntityGroup("FAM");
        if (eg!=null && eg.getEntityItemCount()>0)
        {
            egDebugInfo(eg);
            strFamily = getAttributeFlagEnabledValue("FAM",eg.getEntityItem(0).getEntityID(), "FAMILYNAME", "");
            traceSb.append(NEWLINE+"FAM.FAMILYNAME = ["+strFamily+"] "+
                    getAttributeValue("FAM",eg.getEntityItem(0).getEntityID(), "FAMILYNAME", ""));
        }
        else {
            traceSb.append(NEWLINE+"FAM EntityGroup not found");
        }

        // (EntityType = VAR) or (EntityType = OF and OF.OFFERINGTYPE = "SYSTEM" (0080))
        if ("VAR".equals(getEntityType()) ||
            ("OF".equals(getEntityType()) && OF_OFFERINGTYPE_SYSTEM.equals(strOFType)))
        {
            // Select Case PR.BRANDCODE
            if (strBrand.equals(PR_BRANDCODE_CDT) ||  // CDT (0020), Intellistation (0030)
                strBrand.equals(PR_BRANDCODE_INTELLISTATION))
            {
                traceSb.append(NEWLINE+"Using Case CDT, Intellistation");
                success = getCDT_Intellistation(bufWWMI,bufCONTRCTINVTITLE);
            }
            else if (strBrand.equals(PR_BRANDCODE_NETFINITY)) // Netfinity (0070)
            {
                traceSb.append(NEWLINE+"Using Case Netfinity");
                success = getNetfinity(bufWWMI,bufCONTRCTINVTITLE);
            }
            else if (strBrand.equals(PR_BRANDCODE_MOBILE)) // Mobile (0060)
            {
                traceSb.append(NEWLINE+"Using Case Mobile");
                // IF FAM.FAMILYNAME Contains ThinkPad (0330)
                if (strFamily.equals(FAM_FAMILYNAME_THINKPAD)) // ThinkPad (0330)
                {
                    traceSb.append(" ThinkPad");
                    success = getMobileThinkPad(bufWWMI,bufCONTRCTINVTITLE);
                }
                else // IF FAM.FAMILYNAME Contains "WorkPad" (0340)
                    if (strFamily.equals(FAM_FAMILYNAME_WORKPAD)) // WorkPad (0340)
                    {
                        traceSb.append(" WorkPad");
                        success = getMobileWorkPad(bufWWMI,bufCONTRCTINVTITLE);
                    }
                    else // according to karyn hamilton, handle like SYSTEM default
                    {
                        traceSb.append(" NOT ThinkPad or WorkPad, Using SYSTEM default");
                        success = getSystemDefault(bufWWMI,bufCONTRCTINVTITLE);
                    }
            }
            else // SYSTEM Default
            {
                traceSb.append(NEWLINE+"Using Case SYSTEM default");
                success = getSystemDefault(bufWWMI,bufCONTRCTINVTITLE);
            }
        }
        else  // must be OF with offering type != SYSTEM (0080)
        {
            traceSb.append(NEWLINE+"Using Case OF Type not SYSTEM default");
            success = getOFNotSystemDefault(bufWWMI,bufCONTRCTINVTITLE);
        }

        return success;
    }

    /********************************************************************************
    * Set text attribute value the old way, bypass all business rule checks
    * Not following business rules or process when updating, some required attributes are not set!
    */
    private void setTextDirectly(String attrCode, String attrValue)
        throws COM.ibm.opicmpdh.middleware.MiddlewareException, java.sql.SQLException
    {
        ReturnEntityKey rek = new ReturnEntityKey(getEntityType(), getEntityID(), true);
        DatePackage dbNow = m_db.getDates();
        String strNow = dbNow.getNow();
        String strForever = dbNow.getForever();
        ControlBlock cbOn = new ControlBlock(strNow,strForever,strNow,strForever, m_prof.getOPWGID(), m_prof.getTranID());
        Text sf = new Text (m_prof.getEnterprise(), rek.getEntityType(), rek.getEntityID(), attrCode, attrValue, 1, cbOn);
        Vector vctAtts = new Vector();
        Vector vctReturnsEntityKeys = new Vector();

        traceSb.append(NEWLINE+"Orig "+attrCode+": !"+
                getAttributeValue(getEntityType(), getEntityID(), attrCode,"")+"! New: !"+attrValue+"!");

        try{
            MessageFormat msgf = new MessageFormat(bundle.getString("SET_MSG"));
            Object[] args = new String[2];
            EntityGroup eg;
            EANMetaAttribute mAttr;

            vctAtts.addElement(sf);
            rek.m_vctAttributes = vctAtts;
            vctReturnsEntityKeys.addElement(rek);
            m_db.update(m_prof, vctReturnsEntityKeys, false, false);
            m_db.commit();

            eg = m_elist.getParentEntityGroup();
            mAttr = eg.getMetaAttribute(attrCode);
            args[0] = mAttr.getActualLongDescription();
            args[1] = attrValue;;
            rptSb.append(msgf.format(args));
        }
        catch(java.sql.SQLException x)
        {
            logMessage(this + " trouble updating text value " + x);
            throw x;
        }
        catch(COM.ibm.opicmpdh.middleware.MiddlewareException x)
        {
            logMessage(this + " trouble updating text value " + x);
            throw x;
        }
        finally {
            m_db.freeStatement();
            m_db.isPending("finally after update in Text value");
        }
    }


    /**********************************************************************************
    * Update the PDH. This approach will follow business rules and role restrictions
    * It will fail if other required attributes are not set such as ALWRSTATUS.
    *
    private boolean updatePDHwithBusinessRules(String strCONTRCTINVTITLE, String strWWMI) throws
        java.sql.SQLException,java.rmi.RemoteException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        traceSb.append(NEWLINE+"Orig WWMI: "+
                getAttributeValue(getEntityType(), getEntityID(), getEntityType()+"WWMI","")+
                " new WWMI: "+strWWMI);
        traceSb.append(NEWLINE+"Orig CONTRCTINVTITLE: "+
                getAttributeValue(getEntityType(), getEntityID(), getEntityType()+"CONTRCTINVTITLE","")+
                " new CONTRCTINVTITLE: "+strCONTRCTINVTITLE);
// JUI when try to edit will populate this field..ALWRSTATUS
// Error: *ALWR Status - empty value: Required information missing
// but this way fails to save WWMI because ALWRSTATUS is empty..
        EntityGroup eg = m_elist.getParentEntityGroup();
        EntityItem ei = eg.getEntityItem(0);

        EANMetaAttribute mAttr = eg.getMetaAttribute(getEntityType() + "WWMI");
        String wwmiDesc = "";
        String ctrcDesc="";
        if (mAttr instanceof MetaTextAttribute)
        {
            wwmiDesc = mAttr.getActualLongDescription();
            if (strWWMI.length()>0)  // something was calculated
            {
                EANTextAttribute tAttr = new TextAttribute(ei, m_prof, (MetaTextAttribute) mAttr);
                tAttr.put(strWWMI);
                ei.putAttribute(tAttr);
            }
            else
                traceSb.append(NEWLINE+""+getEntityType() + "WWMI derivation failed. String was empty!");
        }
        else
        {
            traceSb.append(NEWLINE+""+getEntityType() + "WWMI is not a MetaTextAttribute.  It can not be updated!");
        }

        mAttr = eg.getMetaAttribute(getEntityType() + "CONTRCTINVTITLE");
        if (mAttr instanceof MetaTextAttribute)
        {
            ctrcDesc = mAttr.getActualLongDescription();
            if (strCONTRCTINVTITLE.length()>0)  // something was calculated
            {
                EANTextAttribute tAttr = new TextAttribute(ei, m_prof, (MetaTextAttribute) mAttr);
                tAttr.put(strCONTRCTINVTITLE);
                ei.putAttribute(tAttr);
            }
            else
                traceSb.append(NEWLINE+""+getEntityType() + "CONTRCTINVTITLE derivation failed. String was empty!");
        }
        else
        {
            traceSb.append(NEWLINE+""+getEntityType() + "CONTRCTINVTITLE is not a MetaTextAttribute.  It can not be updated!");
        }

        // commit changes here if any
        if (ei.hasChanges())
        {
            ei.commit(m_db, null);
            // output informational msg
            MessageFormat msgf = new MessageFormat(bundle.getString("SET_MSG"));
            MessageFormat msgf2 = new MessageFormat(bundle.getString("Error_DERIVE_MSG"));
            Object[] args = new String[2];
            args[0] = wwmiDesc;
            args[1] = strWWMI;
            if (strWWMI.length()>0)
                rptSb.append(msgf.format(args));
            else
                rptSb.append(msgf2.format(args));
            args[0] = ctrcDesc;
            args[1] = strCONTRCTINVTITLE;
            if (strCONTRCTINVTITLE.length()>0)
                rptSb.append(msgf.format(args));
            else
                rptSb.append(msgf2.format(args));

            return true;
        }
        else
        {
            traceSb.append(NEWLINE+""+getEntityType()+" does not have changes.  It was NOT updated!");
            // output informational msg
            MessageFormat msgf = new MessageFormat(bundle.getString("Error_DERIVE_MSG"));
            Object[] args = new String[1];
            args[0] = wwmiDesc;
            if (strWWMI.length()>0)
                rptSb.append(msgf.format(args));
            args[0] = ctrcDesc;
            if (strCONTRCTINVTITLE.length()>0)
                rptSb.append(msgf.format(args));

            return false;
        }
    }*/

    /**********************************************************************************
    * Case "CDT" (0020), "Intellistation" (0030)
    *  World Wide Master Index (OFWWMI): CDT, Intellistation
    *   1). SE.SERIESNAME, one character space, then '*', one character space,
    *   2). DD.NUMPROCSTD (if value of NUMPROCSTD is greater than 1, show/type a lower case 'x' immediately following
    *   the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case
    *   'x' OR the value of 1 itself)
    *   3). If PRC.PROCMFR = 'AMD', then show the flag value short description for PRC.PROCESSORTYPE, (char limit of 15),
    *   one character space; otherwise show PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field),
    *   show PRC.PROCCLKSPDUNITS only if the value is 'GHz', one character space,
    *   4a). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field), no space, DD.TOTL2CACHESTDUNITS,
    *   one character space,
    *   4b). If MB.L3CACHE is populated and > 0 then include the text 'L2', one character space, MB.L3CACHE (char
    *   limit of 4 imposed on the attribute/field), no space, MB.L3CACHEUNITS, one character space, the text 'L3',
    *   one character space,
    *   5). DD.MEMRAMSTD (char  limit of 4 imposed on the attribute/field), no space, DD.MEMRAMSTDUNITS, one
    *   character space,
    *   6). HD.HDDCAPACITY, no space, HD.HDDCAPACITYUNITS, one character space,
    *   7). HDC.INTERFACEBUS_CO (char limit of 4 imposed on the attribute/field), one character space,
    *   8). POS.POSABBREV, one character space,
    *   9). CDR.CDROPTITYPE (if the value of this attribute/field is 'CD-ROM' (0010) then go to the CDSPEED of this
    *   CDR element and use only the first three characters of the CDSPEED value; if the value of the CDROPTITYPE is
    *   not 'CD-ROM' then use whatever the value is), one character space,
    *   10). MB.TOTCARDSLOTS, no space, show/type a lower case 'x' immediately following this attribute value, no space,
    *   11). PP.TOTBAYS.
    *
    * *Example of above using a NUMPROCSTD value of 1 (see #2 directly above) and GHz (#3):
    * OFWWMI = A21 * 1GHz 512KB 128MB 40GB ATA W2000 48X 2x3
    * *Example of above using a NUMPROCSTD value of 2 (see #2 directly above) and L3CACHE (#4):
    * OFWWMI = A21 * 2x933 512KB L2 1GB L3 128MB 40GB ATA W2000 48X 2x3
    * *Example of above using a NUMPROCSTD value of 1 (see #2 above), AMD (#3), and L3CACHE (#4):
    * OFWWMI = A21 * Opteron 240 512KB L2 1GB L3 128MB 40GB ATA W2000 48X 2x3
    *
    * Offering Contract Invoice Title (OFCONTRCTINVTITLE): CDT, Intellistation
    *   1). SE.SERIESNAME, one character space,
    *   2). DD.NUMPROCSTD (if value of NUMPROCSTD is greater than 1, show/type a lower case 'x' immediately following
    *   the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case
    *   'x' OR the value of 1 itself),
    *   3). PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), if PRC.PROCCLKSPDUNITS = 'GHz' then
    *   show 'G', one character space,
    *   4). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field), one character space,
    *   5). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field), no space, type a ' / ' immediately following
    *   the number with no spaces before or after the ' / ',
    *   6). HD.HDDCAPACITY, one character space,
    *   7). POS.POSABBREV, one character space,
    *   8). CDR.CDROPTITYPE (if the value of this attribute/field is 'CD-ROM' then go to the CDSPEED of this CDR element
    *   and use only the first three characters of the CDSPEED value; if the value of the CDROPTITYPE is not 'CD-ROM' then
    *   use whatever the value is).
    *
    * *Example of above using a NUMPROCSTD value of 1 (see #2 directly above) and GHz (#3):
    * OFCONTRCTINVTITLE= A21 1G 512 128/40 W2000 48X
    * *Example of above using a NUMPROCSTD value of 2 (see #2 directly above):
    * OFCONTRCTINVTITLE = A21 2x933 512 128/40 W2000 48X
    * In this example the derived calculated data is over 28 characters, data remaining after 28 should drop off see below:
    * OFCONTRCTINVTITLE = A21 2x933 512 128/40 W2000
    */
    private boolean getCDT_Intellistation(DerivedString bufWWMI, DerivedString bufCONTRCTINVTITLE)
    {
        boolean ok=true;
        String totL2CacheStd = ""; //(2)
        String totL2CacheStdUnits = "";
        String memRamStd = ""; //(2)
        String memRamStdUnits = "";
        String numProcStd = "";
        String seriesName = ""; //(1)
        EntityGroup eg = m_elist.getEntityGroup("SE");
        if (eg!=null && eg.getEntityItemCount()>0)
        {
            seriesName = getAttributeValue("SE", eg.getEntityItem(0).getEntityID(), "SERIESNAME","");
            egDebugInfo(eg);
        }
        else {
            traceSb.append(NEWLINE+"SE EntityGroup not found");
        }

        eg = m_elist.getEntityGroup("DD");
        if (eg!=null && eg.getEntityItemCount()>0)
        {
            String procMfrFlag = ""; // (3)
            String procType = ""; // (3)
            String procClkSpd = ""; // (3)
            String procClkSpdUnits = ""; // (3)
            String hddCapacity = ""; // (7)
            String hddCapacityUnits = "";
            String interfaceBus = ""; // (8)
            String totCardSlots = ""; // (9)
            String l3cache = "";
            String l3cacheUnits = "";
            String totBays = ""; // (10)
            String posAbbrev = ""; // (6)
            String cdrType = ""; // (8)CTRCT (9)WWMI
            String cdrFlagType = ""; // (8)CTRCT (9)WWMI
            String cdSpeed = ""; // (8)CTRCT (9)WWMI

            memRamStd = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "MEMRAMSTD","");
            memRamStdUnits = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "MEMRAMSTDUNITS","");
            totL2CacheStd = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "TOT_L2_CACHE_STD","");
            totL2CacheStdUnits = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "TOTL2CACHESTDUNITS","");
            numProcStd = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "NUMPROCSTD","");
            egDebugInfo(eg);

            eg = m_elist.getEntityGroup("PRC");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                EANAttribute att;
                egDebugInfo(eg);
                procMfrFlag = getAttributeFlagEnabledValue("PRC",eg.getEntityItem(0).getEntityID(), "PROCMFR", "");
                traceSb.append(NEWLINE+"PRC.PROCMFR value: "+
                    getAttributeValue("PRC", eg.getEntityItem(0).getEntityID(), "PROCMFR","")
                    +" flag: "+procMfrFlag);

                procClkSpd = getAttributeValue("PRC", eg.getEntityItem(0).getEntityID(), "PROCCLKSPD","");
                procClkSpdUnits = getAttributeValue("PRC", eg.getEntityItem(0).getEntityID(), "PROCCLKSPDUNITS","");
                //flag value short description for PRC.PROCESSORTYPE
                att= eg.getEntityItem(0).getAttribute("PROCESSORTYPE");
                if (att!=null && att instanceof EANFlagAttribute)
                {
                    EANFlagAttribute fAtt = (EANFlagAttribute) att;
                    MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
                    for (int i = 0; i < mfArray.length; i++)
                    {
                        if (mfArray[i].isSelected())
                        {
                            procType = mfArray[i].getShortDescription();
                            break;
                        }
                    }
                }
                traceSb.append(NEWLINE+"PRC.PROCESSORTYPE value: "+
                    getAttributeValue("PRC", eg.getEntityItem(0).getEntityID(), "PROCESSORTYPE","")
                    +" flag: "+
                    getAttributeFlagEnabledValue("PRC",eg.getEntityItem(0).getEntityID(), "PROCESSORTYPE", "")+
                    " shortdesc: "+procType);
            }
            else {
                traceSb.append(NEWLINE+"PRC EntityGroup not found");
            }

            eg = m_elist.getEntityGroup("HD");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                egDebugInfo(eg);
                hddCapacity = getAttributeValue("HD", eg.getEntityItem(0).getEntityID(), "HDDCAPACITY","");
                hddCapacityUnits = getAttributeValue("HD", eg.getEntityItem(0).getEntityID(), "HDDCAPACITYUNITS","");
            }
            else {
                traceSb.append(NEWLINE+"HD EntityGroup not found");
            }

            eg = m_elist.getEntityGroup("HDC");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                egDebugInfo(eg);
                interfaceBus = getAttributeValue("HDC", eg.getEntityItem(0).getEntityID(), "INTERFACEBUS_CO","");
            }
            else {
                traceSb.append(NEWLINE+"HDC EntityGroup not found");
            }

            eg = m_elist.getEntityGroup("MB");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                egDebugInfo(eg);
                totCardSlots = getAttributeValue("MB", eg.getEntityItem(0).getEntityID(), "TOTCARDSLOTS","");
                l3cache = getAttributeValue("MB", eg.getEntityItem(0).getEntityID(), "L3CACHE","");
                l3cacheUnits = getAttributeValue("MB", eg.getEntityItem(0).getEntityID(), "L3CACHEUNITS","");
            }
            else {
                traceSb.append(NEWLINE+"MB EntityGroup not found");
            }

            eg = m_elist.getEntityGroup("PP");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                egDebugInfo(eg);
                totBays = getAttributeValue("PP", eg.getEntityItem(0).getEntityID(), "TOTBAYS","");
            }
            else {
                traceSb.append(NEWLINE+"PP EntityGroup not found");
            }

            eg = m_elist.getEntityGroup("POS");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                egDebugInfo(eg);
                posAbbrev = getAttributeValue("POS", eg.getEntityItem(0).getEntityID(), "POSABBREV","");
            }
            else {
                traceSb.append(NEWLINE+"POS EntityGroup not found");
            }

            eg = m_elist.getEntityGroup("CDR");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                egDebugInfo(eg);
                cdrType = getAttributeValue("CDR", eg.getEntityItem(0).getEntityID(), "CDROPTITYPE","");
                cdSpeed = getAttributeValue("CDR", eg.getEntityItem(0).getEntityID(), "CDSPEED","");
                cdrFlagType = getAttributeFlagEnabledValue("CDR",eg.getEntityItem(0).getEntityID(), "CDROPTITYPE", "");
            }
            else {
                traceSb.append(NEWLINE+"CDR EntityGroup not found");
            }

            // start building derived attribute
            if (seriesName.length()>0) // SE was found (1)
            {
    //WWMI 1). SE.SERIESNAME, one character space, then '*', one character space,
                appendAndTrace(bufWWMI, "SE.SERIESNAME", seriesName); // (1)
                bufWWMI.append(" ");  // append one character space (1)
                bufWWMI.append("*");
                bufWWMI.append(" ");

    //CTRC 1). SE.SERIESNAME, one character space,
                appendAndTrace(bufCONTRCTINVTITLE, "SE.SERIESNAME", seriesName); // (1)
                bufCONTRCTINVTITLE.append(" ");  // append one character space (1)
            }
            else {
                traceSb.append(NEWLINE+"SE.SERIESNAME has no value");
            }

            if (numProcStd.length() >0) // DD was found (2)
            {
    //WWMI  2). DD.NUMPROCSTD (if value of NUMPROCSTD is greater than 1, show/type a lower case 'x' immediately following
    //    the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x'
    //    OR the value of 1 itself), one character space,
    //CTRC 2). DD.NUMPROCSTD (if value of NUMPROCSTD is greater than 1, show/type a lower case 'x' immediately following
    //    the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x'
    //    OR the value of 1 itself)
                if (!numProcStd.equals("1") && !numProcStd.equals("0")) // not a 1
                {
                    appendAndTrace(bufWWMI, "DD.NUMPROCSTD", numProcStd);
                    bufWWMI.append("x");
                    appendAndTrace(bufCONTRCTINVTITLE, "DD.NUMPROCSTD", numProcStd);
                    bufCONTRCTINVTITLE.append("x");
                }
                else
                {
                    traceSb.append(NEWLINE+"Not using DD.NUMPROCSTD: "+ numProcStd);
                }
            }
            else {
                traceSb.append(NEWLINE+"DD.NUMPROCSTD has no value");
            }

            if (procClkSpd.length() >0 || procType.length() >0) // PRC was found (3)
            {
    //WWMI  3). If PRC.PROCMFR = 'AMD', then show the flag value short description for PRC.PROCESSORTYPE, (char limit of 15),
    //    one character space; otherwize show PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), show
    //    PRC.PROCCLKSPDUNITS only if the value is 'GHz', one character space,
    //NOTE: PRC.PROCESSORTYPE, short desc exceeds char limit of 15 and some do not have values set!!!
                if (procMfrFlag.equals("0020")) // AMD
                {
                    if (procType.length() >0) {
                        appendAndTrace(bufWWMI, "PRC.PROCESSORTYPE", procType, CHAR_LIMIT);

                    } else {
                        traceSb.append(NEWLINE+""+bufWWMI.getType()+" PRC.PROCMFR was AMD but no short desc for PRC.PROCESSORTYPE");
                    }
                }
                else
                {
                    appendAndTrace(bufWWMI, "PRC.PROCCLKSPD", procClkSpd, 4);
                    if (procClkSpdUnits.toLowerCase().startsWith("g"))
                    {
                        appendAndTrace(bufWWMI, "PRC.PROCCLKSPDUNITS", procClkSpdUnits);
                    }
                    else {
                        traceSb.append(NEWLINE+""+bufWWMI.getType()+" not using PRC.PROCCLKSPDUNITS = "+procClkSpdUnits);
                    }
                }
                bufWWMI.append(" ");
    //CTRC 3). PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), one character space,
    //    if PRC.PROCCLKSPDUNITS = 'GHz' then show 'G', one character space
                appendAndTrace(bufCONTRCTINVTITLE, "PRC.PROCCLKSPD", procClkSpd, 4);
                if (procClkSpdUnits.toLowerCase().startsWith("g"))
                {
                    appendAndTrace(bufCONTRCTINVTITLE, "PRC.PROCCLKSPDUNITS", procClkSpdUnits, 1);
                }
                else {
                    traceSb.append(NEWLINE+""+bufCONTRCTINVTITLE.getType()+" not using PRC.PROCCLKSPDUNITS = "+procClkSpdUnits);
                }

                bufCONTRCTINVTITLE.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"PRC.PROCCLKSPD and PRC.PROCESSORTYPE has no value");
            }

            if (totL2CacheStd.length() >0)
            {
    //WWMI  4a). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field), no space, DD.TOTL2CACHESTDUNITS,
    //    one character space,
                appendAndTrace(bufWWMI, "DD.TOT_L2_CACHE_STD", totL2CacheStd, 4);
                appendAndTrace(bufWWMI, "DD.TOTL2CACHESTDUNITS", totL2CacheStdUnits);
                bufWWMI.append(" ");

    //CTRC 4). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field),one character space,
                appendAndTrace(bufCONTRCTINVTITLE, "DD.TOT_L2_CACHE_STD", totL2CacheStd, 4);
                bufCONTRCTINVTITLE.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"DD.TOT_L2_CACHE_STD has no value");
            }

    //WWMI  4b). If MB.L3CACHE is populated and > 0 then include the text 'L2', one character space, MB.L3CACHE (char
    //    limit of 4 imposed on the attribute/field), no space, MB.L3CACHEUNITS, one character space, the text 'L3',
    //    one character space.
            if (l3cache.length()>0 && !l3cache.equals("0"))
            {
                bufWWMI.append("L2");
                bufWWMI.append(" ");
                appendAndTrace(bufWWMI, "MB.L3CACHE", l3cache, 4);
                appendAndTrace(bufWWMI, "MB.L3CACHEUNITS", l3cacheUnits);
                bufWWMI.append(" ");
                bufWWMI.append("L3");
                bufWWMI.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"MB.L3CACHE has no value");
            }

            if (memRamStd.length() >0)
            {
    //WWMI  5). DD.MEMRAMSTD (char  limit of 4 imposed on the attribute/field), no space, DD.MEMRAMSTDUNITS, one character
    //    space,
                appendAndTrace(bufWWMI, "DD.MEMRAMSTD", memRamStd, 4);
                appendAndTrace(bufWWMI, "DD.MEMRAMSTDUNITS", memRamStdUnits);
                bufWWMI.append(" ");
    //CTRC 5). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field), no space, type a ' / ' immediately following
    //    the number with no spaces before or after the ' / ',
                appendAndTrace(bufCONTRCTINVTITLE, "DD.MEMRAMSTD", memRamStd, 4);
                bufCONTRCTINVTITLE.append("/");
            }
            else {
                traceSb.append(NEWLINE+"DD.MEMRAMSTD has no value");
            }

            if (hddCapacity.length() >0) // HD was found (5)
            {
    //WWMI  6). HD.HDDCAPACITY, no space, HD.HDDCAPACITYUNITS, one character space,
                appendAndTrace(bufWWMI, "HD.HDDCAPACITY", hddCapacity);
                appendAndTrace(bufWWMI, "HD.HDDCAPACITYUNITS", hddCapacityUnits);
                bufWWMI.append(" ");
    //CTRC 6). HD.HDDCAPACITY, one character space,
                appendAndTrace(bufCONTRCTINVTITLE, "HD.HDDCAPACITY", hddCapacity);
                bufCONTRCTINVTITLE.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"HD.HDDCAPACITY has no value");
            }

            if (interfaceBus.length() >0)
            {
    //WWMI  7). HDC.INTERFACEBUS_CO (char limit of 4 imposed on the attribute/field), one character space,
                appendAndTrace(bufWWMI, "HDC.INTERFACEBUS_CO", interfaceBus,4);
                bufWWMI.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"HDC.INTERFACEBUS_CO has no value");
            }

            if (posAbbrev.length() >0) // POS was found (6)
            {
    //WWMI  8). POS.POSABBREV, one character space,
                appendAndTrace(bufWWMI, "POS.POSABBREV", posAbbrev);
                bufWWMI.append(" ");
    //CTRC 7). POS.POSABBREV, one character space,
                appendAndTrace(bufCONTRCTINVTITLE, "POS.POSABBREV", posAbbrev);
                bufCONTRCTINVTITLE.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"POS.POSABBREV has no value");
            }

            if (cdrType.length() >0) // CDR was found (WWMI 9, CTRCT 8)
            {
    //WWMI  9). CDR.CDROPTITYPE (if the value of this attribute/field is 'CD-ROM' (0010) then go to the CDSPEED of this
    //    CDR element and use only the first three characters of the CDSPEED value; if the value of the CDROPTITYPE is
    //    not 'CD-ROM' then use whatever the value is), one character space,
    //CTRC 8). CDR.CDROPTITYPE (if the value of this attribute/field is 'CD-ROM' then go to the CDSPEED of this CDR element
    //    and use only the first three characters of the CDSPEED value; if the value of the CDROPTITYPE is not 'CD-ROM'
    //    then use whatever the value is).
                if (cdrFlagType.equals("0010")) // CD-ROM
                {
                    appendAndTrace(bufWWMI, "CDR.CDSPEED", cdSpeed, 3);
                    appendAndTrace(bufCONTRCTINVTITLE, "CDR.CDSPEED", cdSpeed, 3);
                }
                else
                {
                    appendAndTrace(bufWWMI, "CDR.CDROPTITYPE", cdrType);
                    appendAndTrace(bufCONTRCTINVTITLE, "CDR.CDROPTITYPE", cdrType);
                }
                bufWWMI.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"CDR.CDROPTITYPE has no value");
            }

            if (totCardSlots.length() >0)
            {
    //WWMI  10). MB.TOTCARDSLOTS, no space, show/type a lower case 'x' immediately following this attribute value, no space,
                appendAndTrace(bufWWMI, "MB.TOTCARDSLOTS", totCardSlots);
                bufWWMI.append("x");
            }
            else {
                traceSb.append(NEWLINE+"MB.TOTCARDSLOTS has no value");
            }

    //WWMI 11). PP.TOTBAYS.
            appendAndTrace(bufWWMI, "PP.TOTBAYS", totBays);
        }
        else
        {
            MessageFormat msgf = new MessageFormat(bundle.getString("Error_NO_DD"));
            Object[] args = new String[1];
            traceSb.append(NEWLINE+"DD EntityGroup not found");
            //  Fail the ABR with a message that there is no derived data entity for the selected OF/VAR.
            eg = m_elist.getEntityGroup(getRootEntityType());
            args[0] = eg.getLongDescription();
            rptSb.append(msgf.format(args));
            ok= false;
        }

        return ok;
    }

    /**************************************************************************************
    * Case "Netfinity" (0070)
    *   World Wide Master Index (OFWWMI): Netfinity
    *   1). SE.SERIESNAME, one character space, then '*', one character space,
    *   2). DD.NUMPROCSTD (if value of NUMPROCSTD is greater than 1, show/type a lower case 'x' immediately following the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x' OR the value of 1 itself)
    *   3). If PRC.PROCMFR = 'AMD', then show the flag value short description for PRC.PROCESSORTYPE, (char limit of 15), one character space; otherwise show PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), show PRC.PROCCLKSPDUNITS only if the value is 'GHz', one character space,
    *   4). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field), no space, DD.TOTL2CACHESTDUNITS, one character space,
    *   5). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field), no space, DD.MEMRAMSTDUNITS, one character space,
    *   6). DD.NUMINSTHD (if value of NUMINSTHD is greater than 1, show/type a lower case 'x' immediately following the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x' OR the value of 1 itself),
    *   7). HD.HDDCAPACITY, no space, HD.HDDCAPACITYUNITS, one character space,
    *   8). HDC.INTERFACEBUS_CO (char limit of 4 imposed on the attribute/field), one character space,
    *   9). MB.TOTCARDSLOTS, no space, show/type a lower case 'x' immediately following this attribute value, no space,
    *   10). PP.TOTBAYS.
    *
    *   *Example of above using a NUMPROCSTD value of 1 (see #2 directly above) and a NUMINSTHD value of 1 (see #6 directly above)
    *   OFWWMI = A21 * 933 512KB 128MB 40GB ATA 2x3
    *   *Example of above using a NUMPROCSTD value of 2 (see #2 directly above) and AMD (#3):
    *   OFWWMI = A21 * 2xOpteron 240 512KB 128MB 2x40GB ATA 2x3
    *
    *   Offering Contract Invoice Title (OFCONTRCTINVTITLE): Netfinity
    *   1). SE.SERIESNAME, one character space,
    *   2). DD.NUMPROCSTD (if value of NUMPROCSTD is greater than 1, show/type a lower case 'x' immediately following the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x' OR the value of 1 itself)
    *   3). PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), if PRC.PROCCLKSPDUNITS = 'GHz' then show 'G', one character space,
    *   4). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field),  one character space,
    *   5). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field), type a ' / ' immediately following the number with no spaces before or after the ' / '
    *   6). DD.NUMINSTHD (if value of NUMINSTHD is greater than 1, show/type a lower case 'x' immediately following the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x' OR the value of 1 itself),
    *   7). HD.HDDCAPACITY.
    *
    *   *Example of above using a NUMPROCSTD value of 1 (see #2 directly above) and a NUMINSTHD value of 1 (see #6 directly above) and GHz (#3)
    *   OFCONTRCTINVTITLE = A21 1.2G 512 128/40
    *   *Example of above using a NUMPROCSTD value of 2 (see #2 directly above):
    *   OFCONTRCTINVTITLE = A21 2x933 512 128/2x40
    */
    private boolean getNetfinity(DerivedString bufWWMI, DerivedString bufCONTRCTINVTITLE)
    {
        boolean ok = true;
        String seriesName = ""; //(1)
        String totL2CacheStd = ""; //(2)
        String totL2CacheStdUnits = "";
        String memRamStd = ""; //(2)
        String memRamStdUnits = "";
        String numProcStd = "";
        String numInstHD = "";

        EntityGroup eg = m_elist.getEntityGroup("SE");
        if (eg!=null && eg.getEntityItemCount()>0)
        {
            egDebugInfo(eg);
            seriesName = getAttributeValue("SE", eg.getEntityItem(0).getEntityID(), "SERIESNAME","");
        }
        else {
            traceSb.append(NEWLINE+"SE EntityGroup not found");
        }

        eg = m_elist.getEntityGroup("DD");
        if (eg!=null && eg.getEntityItemCount()>0)
        {
            String procMfrFlag = ""; // (3)
            String procType = ""; // (3)
            String procClkSpd = ""; // (3)
            String procClkSpdUnits = ""; // (3)
            String hddCapacity = ""; // (7)
            String hddCapacityUnits = "";
            String interfaceBus = ""; // (8)
            String totCardSlots = ""; // (9)
            String totBays = ""; // (10)

            egDebugInfo(eg);
            memRamStd = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "MEMRAMSTD","");
            memRamStdUnits = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "MEMRAMSTDUNITS","");
            totL2CacheStd = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "TOT_L2_CACHE_STD","");
            totL2CacheStdUnits = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "TOTL2CACHESTDUNITS","");
            numProcStd = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "NUMPROCSTD","");
            numInstHD = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "NUMINSTHD","");

            eg = m_elist.getEntityGroup("PRC");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                EANAttribute att;
                egDebugInfo(eg);
                procMfrFlag = getAttributeFlagEnabledValue("PRC",eg.getEntityItem(0).getEntityID(), "PROCMFR", "");
                traceSb.append(NEWLINE+"PRC.PROCMFR value: "+
                    getAttributeValue("PRC", eg.getEntityItem(0).getEntityID(), "PROCMFR","")
                    +" flag: "+procMfrFlag);

                procClkSpd = getAttributeValue("PRC", eg.getEntityItem(0).getEntityID(), "PROCCLKSPD","");
                procClkSpdUnits = getAttributeValue("PRC", eg.getEntityItem(0).getEntityID(), "PROCCLKSPDUNITS","");
                //flag value short description for PRC.PROCESSORTYPE
                att= eg.getEntityItem(0).getAttribute("PROCESSORTYPE");
                if (att!=null && att instanceof EANFlagAttribute)
                {
                    EANFlagAttribute fAtt = (EANFlagAttribute) att;
                    MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
                    for (int i = 0; i < mfArray.length; i++)
                    {
                        if (mfArray[i].isSelected())
                        {
                            procType = mfArray[i].getShortDescription();
                            break;
                        }
                    }
                }
                traceSb.append(NEWLINE+"PRC.PROCESSORTYPE value: "+
                    getAttributeValue("PRC", eg.getEntityItem(0).getEntityID(), "PROCESSORTYPE","")
                    +" flag: "+
                    getAttributeFlagEnabledValue("PRC",eg.getEntityItem(0).getEntityID(), "PROCESSORTYPE", "")+
                    " shortdesc: "+procType);
            }
            else {
                traceSb.append(NEWLINE+"PRC EntityGroup not found");
            }

            eg = m_elist.getEntityGroup("HD");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                egDebugInfo(eg);
                hddCapacity = getAttributeValue("HD", eg.getEntityItem(0).getEntityID(), "HDDCAPACITY","");
                hddCapacityUnits = getAttributeValue("HD", eg.getEntityItem(0).getEntityID(), "HDDCAPACITYUNITS","");
            }
            else {
                traceSb.append(NEWLINE+"HD EntityGroup not found");
            }

            eg = m_elist.getEntityGroup("HDC");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                egDebugInfo(eg);
                interfaceBus = getAttributeValue("HDC", eg.getEntityItem(0).getEntityID(), "INTERFACEBUS_CO","");
            }
            else {
                traceSb.append(NEWLINE+"HDC EntityGroup not found");
            }

            eg = m_elist.getEntityGroup("MB");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                egDebugInfo(eg);
                totCardSlots = getAttributeValue("MB", eg.getEntityItem(0).getEntityID(), "TOTCARDSLOTS","");
            }
            else {
                traceSb.append(NEWLINE+"MB EntityGroup not found");
            }

            eg = m_elist.getEntityGroup("PP");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                egDebugInfo(eg);
                totBays = getAttributeValue("PP", eg.getEntityItem(0).getEntityID(), "TOTBAYS","");
            }
            else {
                traceSb.append(NEWLINE+"PP EntityGroup not found");
            }

            // start building derived attribute
            if (seriesName.length()>0) // SE was found (1)
            {
    //WWMI 1). SE.SERIESNAME, one character space, then '*', one character space,
                appendAndTrace(bufWWMI, "SE.SERIESNAME", seriesName); // (1)
                bufWWMI.append(" ");  // append one character space (1)
                bufWWMI.append("*");
                bufWWMI.append(" ");

    //CTRC 1). SE.SERIESNAME, one character space,
                appendAndTrace(bufCONTRCTINVTITLE, "SE.SERIESNAME", seriesName); // (1)
                bufCONTRCTINVTITLE.append(" ");  // append one character space (1)
            }
            else {
                traceSb.append(NEWLINE+"SE.SERIESNAME has no value");
            }

            if (numProcStd.length() >0) // DD was found (2)
            {
    //WWMI 2). DD.NUMPROCSTD (if value of NUMPROCSTD is greater than 1, show/type a lower case 'x' immediately following
    //   the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x'
    //   OR the value of 1 itself)
    //CTRC 2). DD.NUMPROCSTD (if value of NUMPROCSTD is greater than 1, show/type a lower case 'x' immediately following
    //   the number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x'
    //   OR the value of 1 itself)
                if (!numProcStd.equals("1") && !numProcStd.equals("0")) // not a 1
                {
                    appendAndTrace(bufWWMI, "DD.NUMPROCSTD", numProcStd);
                    bufWWMI.append("x");
                    appendAndTrace(bufCONTRCTINVTITLE, "DD.NUMPROCSTD", numProcStd);
                    bufCONTRCTINVTITLE.append("x");
                }
                else
                {
                    traceSb.append(NEWLINE+"Not using DD.NUMPROCSTD: "+ numProcStd);
                }
            }
            else {
                traceSb.append(NEWLINE+"DD.NUMPROCSTD has no value");
            }

            if (procClkSpd.length() >0 || procType.length() >0) // PRC was found (3)
            {
    //WWMI 3). If PRC.PROCMFR = 'AMD', then show the flag value short description for PRC.PROCESSORTYPE, (char limit of 15),
    //   one character space;otherwize show PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), show
    //   PRC.PROCCLKSPDUNITS only if the value is 'GHz', one character space,
    //NOTE: PRC.PROCESSORTYPE, short desc exceeds char limit of 15 and some do not have values set!!!
                if (procMfrFlag.equals("0020")) // AMD
                {
                    if (procType.length() >0) {
                        appendAndTrace(bufWWMI, "PRC.PROCESSORTYPE", procType, CHAR_LIMIT);

                    } else {
                        traceSb.append(NEWLINE+""+bufWWMI.getType()+" PRC.PROCMFR was AMD but no short desc for PRC.PROCESSORTYPE");
                    }
                }
                else
                {
                    appendAndTrace(bufWWMI, "PRC.PROCCLKSPD", procClkSpd, 4);
                    if (procClkSpdUnits.toLowerCase().startsWith("g"))
                    {
                        appendAndTrace(bufWWMI, "PRC.PROCCLKSPDUNITS", procClkSpdUnits);
                    }
                    else {
                        traceSb.append(NEWLINE+""+bufWWMI.getType()+" not using PRC.PROCCLKSPDUNITS = "+procClkSpdUnits);
                    }
                }
                bufWWMI.append(" ");
    //CTRC 3). PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), if PRC.PROCCLKSPDUNITS = 'GHz' then show 'G',
    //   one character space,
                appendAndTrace(bufCONTRCTINVTITLE, "PRC.PROCCLKSPD", procClkSpd, 4);
                if (procClkSpdUnits.toLowerCase().startsWith("g"))
                {
                    appendAndTrace(bufCONTRCTINVTITLE, "PRC.PROCCLKSPDUNITS", procClkSpdUnits, 1);
                }
                else {
                    traceSb.append(NEWLINE+""+bufCONTRCTINVTITLE.getType()+" not using PRC.PROCCLKSPDUNITS = "+procClkSpdUnits);
                }

                bufCONTRCTINVTITLE.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"PRC.PROCCLKSPD and PRC.PROCESSORTYPE has no value");
            }

            if (totL2CacheStd.length() >0)
            {
    //WWMI 4). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field), no space, DD.TOTL2CACHESTDUNITS, one
    //   character space,
                appendAndTrace(bufWWMI, "DD.TOT_L2_CACHE_STD", totL2CacheStd, 4);
                appendAndTrace(bufWWMI, "DD.TOTL2CACHESTDUNITS", totL2CacheStdUnits);
                bufWWMI.append(" ");
    //CTRC 4). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field),  one character space,
                appendAndTrace(bufCONTRCTINVTITLE, "DD.TOT_L2_CACHE_STD", totL2CacheStd, 4);
                bufCONTRCTINVTITLE.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"DD.TOT_L2_CACHE_STD has no value");
            }

            if (memRamStd.length() >0)
            {
    //WWMI 5). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field), no space, DD.MEMRAMSTDUNITS, one character space,
                appendAndTrace(bufWWMI, "DD.MEMRAMSTD", memRamStd, 4);
                appendAndTrace(bufWWMI, "DD.MEMRAMSTDUNITS", memRamStdUnits);
                bufWWMI.append(" ");
    //CTRC 5). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field), type a '/' immediately following the number
    //   with no spaces before or after the '/'
                appendAndTrace(bufCONTRCTINVTITLE, "DD.MEMRAMSTD", memRamStd, 4);
                bufCONTRCTINVTITLE.append("/");
            }
            else {
                traceSb.append(NEWLINE+"DD.MEMRAMSTD has no value");
            }
            if (numInstHD.length() >0)
            {
    //WWMI 6). DD.NUMINSTHD (if value of NUMINSTHD is greater than 1, show/type a lower case 'x' immediately following the
    //   number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x'
    //   OR the value of 1 itself),
    //CTRC 6). DD.NUMINSTHD (if value of NUMINSTHD is greater than 1, show/type a lower case 'x' immediately following the
    //   number with no spaces before or after the 'x' ; if the value equals 1 then DO NOT show/type a lower case 'x'
    //   OR the value of 1 itself),
                if (!numInstHD.equals("1")&&!numInstHD.equals("0")) // not a 1
                {
                    appendAndTrace(bufWWMI, "DD.NUMINSTHD", numInstHD);
                    bufWWMI.append("x");
                    appendAndTrace(bufCONTRCTINVTITLE, "DD.NUMINSTHD", numInstHD);
                    bufCONTRCTINVTITLE.append("x");
                }
                else {
                    traceSb.append(NEWLINE+"Not using DD.NUMINSTHD: "+ numInstHD);
                }
            }
            else {
                traceSb.append(NEWLINE+"DD.NUMINSTHD has no value");
            }
            if (hddCapacity.length() >0) // HD was found (5)
            {
    //WWMI 7). HD.HDDCAPACITY, no space, HD.HDDCAPACITYUNITS, one character space,
                appendAndTrace(bufWWMI, "HD.HDDCAPACITY", hddCapacity);
                appendAndTrace(bufWWMI, "HD.HDDCAPACITYUNITS", hddCapacityUnits);
                bufWWMI.append(" ");
    //CTRC 7). HD.HDDCAPACITY.
                appendAndTrace(bufCONTRCTINVTITLE, "HD.HDDCAPACITY", hddCapacity);
            }
            else {
                traceSb.append(NEWLINE+"HD.HDDCAPACITY has no value");
            }
            if (interfaceBus.length() >0)
            {
    //WWMI 8). HDC.INTERFACEBUS_CO (char limit of 4 imposed on the attribute/field), one character space,
                appendAndTrace(bufWWMI, "HDC.INTERFACEBUS_CO", interfaceBus,4);
                bufWWMI.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"HDC.INTERFACEBUS_CO has no value");
            }
            if (totCardSlots.length() >0)
            {
    //WWMI 9). MB.TOTCARDSLOTS, no space, show/type a lower case 'x' immediately following this attribute value, no space,
                appendAndTrace(bufWWMI, "MB.TOTCARDSLOTS", totCardSlots);
                bufWWMI.append("x");
            }
            else {
                traceSb.append(NEWLINE+"MB.TOTCARDSLOTS has no value");
            }
    //WWMI 10). PP.TOTBAYS.
            appendAndTrace(bufWWMI, "PP.TOTBAYS", totBays);
        }
        else
        {
            MessageFormat msgf = new MessageFormat(bundle.getString("Error_NO_DD"));
            Object[] args = new String[1];
            traceSb.append(NEWLINE+"DD EntityGroup not found");
            //  Fail the ABR with a message that there is no derived data entity for the selected OF/VAR.
            eg = m_elist.getEntityGroup(getRootEntityType());
            args[0] = eg.getLongDescription();
            rptSb.append(msgf.format(args));
            ok= false;
        }

        return ok;
    }

    /**************************************************************************************
    * Case "Mobile" (0060)
    * IF FAM.FAMILYNAME Contains ThinkPad (0330)
    *   World Wide Master Index (OFWWMI): Mobile | ThinkPad
    *   1). SE.SERIESNAME, one character space, then '*', one character space,
    *   2). If PRC.PROCMFR = 'AMD', then show the flag value short description for PRC.PROCESSORTYPE, (char limit of 15), one character space; otherwise show PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), show PRC.PROCCLKSPDUNITS only if the value is 'GHz', one character space,
    *   3). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field), no space, DD.TOTL2CACHESTDUNITS, one character space,
    *   4). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field), no space, DD.MEMRAMSTDUNITS, one character space,
    *   5). HD.HDDCAPACITY, no space, HD.HDDCAPACITYUNITS, one character space,
    *   6). POS.POSABBREV, one character space,
    *   7). MON.SCREENSIZENOM_IN, one character space,
    *   8). MON.TUBETYPE, one character space (char limit of 4 imposed on the attribute/field),
    *   9). CDR.CDROPTITYPE (if the value of this attribute/field is 'CD-ROM' then go to the CDSPEED of this CDR element and use only the first three characters of the CDSPEED value; if the value of the CDROPTITYPE is not 'CD-ROM' then use whatever the value is)
    *
    *   *Example of above not AMD (#3):
    *   OFWWMI = A21 * 933 512KB 128MB 40GB W2000 14.1 TFT active matrix LCD 48X
    *   *Example of above using AMD (#3):
    *   OFWWMI = A21 * Opteron 240 512KB 128MB 40GB W2000 14.1 TFT active matrix LCD 48X
    *
    *   Offering Contract Invoice Title (OFCONTRCTINVTITLE): Mobile | ThinkPad
    *   1). SE.SERIESNAME, one character space,
    *   2). PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), if PRC.PROCCLKSPDUNITS = 'GHz' then show 'G', one character space,
    *   3). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field), one character space,
    *   4). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field, type a ' / ' immediately following the number with no spaces before or after the ' / '
    *   5). HD.HDDCAPACITY, one character space,
    *   6). POS.POSABBREV, one character space,
    *   7). MON.SCREENSIZENOM_IN, one character space,
    *   8). CDR.CDROPTITYPE (if the value of this attribute/field is 'CD-ROM' then go to the CDSPEED of this CDR element and use only the first three characters of the CDSPEED value; if the value of the CDROPTITYPE is not 'CD-ROM' then use whatever the value is)
    *
    *   *Example of above:
    *   OFCONTRCTINVTITLE = A21 512 128/40 W2000 14.1 48X
    */
    private boolean getMobileThinkPad(DerivedString bufWWMI, DerivedString bufCONTRCTINVTITLE)
    {
        boolean ok=true;
        String seriesName = ""; //(1)
        String procMfrFlag = ""; // (2) WWMI
        String procType = ""; // (2) WWMI
        String procClkSpd = ""; // (2)
        String procClkSpdUnits = ""; // (2)
        String totL2CacheStd = ""; //(3)
        String totL2CacheStdUnits = "";
        String memRamStd = ""; //(4)
        String memRamStdUnits = "";

        EntityGroup eg = m_elist.getEntityGroup("SE");
        if (eg!=null && eg.getEntityItemCount()>0)
        {
            egDebugInfo(eg);
            seriesName = getAttributeValue("SE", eg.getEntityItem(0).getEntityID(), "SERIESNAME","");
        }
        else {
            traceSb.append(NEWLINE+"SE EntityGroup not found");
        }

        eg = m_elist.getEntityGroup("PRC");
        if (eg!=null && eg.getEntityItemCount()>0)
        {
            EANAttribute att;
            egDebugInfo(eg);
            procMfrFlag = getAttributeFlagEnabledValue("PRC",eg.getEntityItem(0).getEntityID(), "PROCMFR", "");
            traceSb.append(NEWLINE+"PRC.PROCMFR value: "+
                getAttributeValue("PRC", eg.getEntityItem(0).getEntityID(), "PROCMFR","")
                +" flag: "+procMfrFlag);

            procClkSpd = getAttributeValue("PRC", eg.getEntityItem(0).getEntityID(), "PROCCLKSPD","");
            procClkSpdUnits = getAttributeValue("PRC", eg.getEntityItem(0).getEntityID(), "PROCCLKSPDUNITS","");
            //flag value short description for PRC.PROCESSORTYPE
            att= eg.getEntityItem(0).getAttribute("PROCESSORTYPE");
            if (att!=null && att instanceof EANFlagAttribute)
            {
                EANFlagAttribute fAtt = (EANFlagAttribute) att;
                MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
                for (int i = 0; i < mfArray.length; i++)
                {
                    if (mfArray[i].isSelected())
                    {
                        procType = mfArray[i].getShortDescription();
                        break;
                    }
                }
            }
            traceSb.append(NEWLINE+"PRC.PROCESSORTYPE value: "+
                getAttributeValue("PRC", eg.getEntityItem(0).getEntityID(), "PROCESSORTYPE","")
                +" flag: "+
                getAttributeFlagEnabledValue("PRC",eg.getEntityItem(0).getEntityID(), "PROCESSORTYPE", "")+
                " shortdesc: "+procType);
        }
        else {
            traceSb.append(NEWLINE+"PRC EntityGroup not found");
        }

        eg = m_elist.getEntityGroup("DD");
        if (eg!=null && eg.getEntityItemCount()>0)
        {
            String hddCapacity = ""; // (5)
            String hddCapacityUnits = "";
            String posAbbrev = ""; // (6)
            String monScrSize = ""; // (7)
            String monTubeType = ""; // (8)WWMI
            String cdrType = ""; // (8)CTRCT (9)WWMI
            String cdrFlagType = ""; // (8)CTRCT (9)WWMI
            String cdSpeed = ""; // (8)CTRCT (9)WWMI

            egDebugInfo(eg);
            memRamStd = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "MEMRAMSTD","");
            totL2CacheStd = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "TOT_L2_CACHE_STD","");
            memRamStdUnits = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "MEMRAMSTDUNITS","");
            totL2CacheStdUnits = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "TOTL2CACHESTDUNITS","");

            eg = m_elist.getEntityGroup("HD");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                egDebugInfo(eg);
                hddCapacity = getAttributeValue("HD", eg.getEntityItem(0).getEntityID(), "HDDCAPACITY","");
                hddCapacityUnits = getAttributeValue("HD", eg.getEntityItem(0).getEntityID(), "HDDCAPACITYUNITS","");
            }
            else {
                traceSb.append(NEWLINE+"HD EntityGroup not found");
            }

            eg = m_elist.getEntityGroup("POS");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                egDebugInfo(eg);
                posAbbrev = getAttributeValue("POS", eg.getEntityItem(0).getEntityID(), "POSABBREV","");
            }
            else {
                traceSb.append(NEWLINE+"POS EntityGroup not found");
            }

            eg = m_elist.getEntityGroup("MON");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                egDebugInfo(eg);
                monScrSize = getAttributeValue("MON", eg.getEntityItem(0).getEntityID(), "SCREENSIZENOM_IN","");
                monTubeType = getAttributeValue("MON", eg.getEntityItem(0).getEntityID(), "TUBETYPE","");
            }
            else {
                traceSb.append(NEWLINE+"MON EntityGroup not found");
            }

            eg = m_elist.getEntityGroup("CDR");
            if (eg!=null && eg.getEntityItemCount()>0)
            {
                egDebugInfo(eg);
                cdrType = getAttributeValue("CDR", eg.getEntityItem(0).getEntityID(), "CDROPTITYPE","");
                cdSpeed = getAttributeValue("CDR", eg.getEntityItem(0).getEntityID(), "CDSPEED","");
                cdrFlagType = getAttributeFlagEnabledValue("CDR",eg.getEntityItem(0).getEntityID(), "CDROPTITYPE", "");

            }
            else {
                traceSb.append(NEWLINE+"CDR EntityGroup not found");
            }

            // start building derived attribute
            if (seriesName.length()>0) // SE was found (1)
            {
    //WWMI 1). SE.SERIESNAME, one character space, then '*', one character space,
                appendAndTrace(bufWWMI, "SE.SERIESNAME", seriesName); // (1)
                bufWWMI.append(" ");  // append one character space (1)
                bufWWMI.append("*");
                bufWWMI.append(" ");
    //CTRC 1). SE.SERIESNAME, one character space,
                appendAndTrace(bufCONTRCTINVTITLE, "SE.SERIESNAME", seriesName); // (1)
                bufCONTRCTINVTITLE.append(" ");  // append one character space (1)
            }
            else {
                traceSb.append(NEWLINE+"SE.SERIESNAME has no value");
            }

            if (procClkSpd.length() >0 || procType.length() >0) // PRC was found (2)
            {
    //WWMI 2). if PRC.PROCMFR = 'AMD', then show the flag value short description for PRC.PROCESSORTYPE, (char limit of 15),
    //    one character space; otherwize show PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field),
    //    show PRC.PROCCLKSPDUNITS only if this value is 'GHz', one character space,
    //NOTE: PRC.PROCESSORTYPE, short desc exceeds char limit of 15 and some do not have values set!!!
                if (procMfrFlag.equals("0020")) // AMD
                {
                    if (procType.length() >0) {
                        appendAndTrace(bufWWMI, "PRC.PROCESSORTYPE", procType, CHAR_LIMIT);

                    } else {
                        traceSb.append(NEWLINE+""+bufWWMI.getType()+" PRC.PROCMFR was AMD but no short desc for PRC.PROCESSORTYPE");
                    }
                }
                else
                {
                    appendAndTrace(bufWWMI, "PRC.PROCCLKSPD", procClkSpd, 4);
                    if (procClkSpdUnits.toLowerCase().startsWith("g"))
                    {
                        appendAndTrace(bufWWMI, "PRC.PROCCLKSPDUNITS", procClkSpdUnits);
                    }
                    else {
                        traceSb.append(NEWLINE+""+bufWWMI.getType()+" not using PRC.PROCCLKSPDUNITS = "+procClkSpdUnits);
                    }
                }
                bufWWMI.append(" ");
    //CTRC 2). PRC.PROCCLKSPD (char limit of 4 imposed on the attribute/field), if PRC.PROCCLKSPDUNITS = 'GHz' then show 'G',
    //    one character space,
                appendAndTrace(bufCONTRCTINVTITLE, "PRC.PROCCLKSPD", procClkSpd, 4);
                if (procClkSpdUnits.toLowerCase().startsWith("g"))
                {
                    appendAndTrace(bufCONTRCTINVTITLE, "PRC.PROCCLKSPDUNITS", procClkSpdUnits, 1);
                }
                else {
                    traceSb.append(NEWLINE+""+bufCONTRCTINVTITLE.getType()+" not using PRC.PROCCLKSPDUNITS = "+procClkSpdUnits);
                }

                bufCONTRCTINVTITLE.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"PRC.PROCCLKSPD and PRC.PROCESSORTYPE has no value");
            }
            if (totL2CacheStd.length() >0) // DD was found (3)
            {
    //WWMI 3). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field), no space, DD.TOTL2CACHESTDUNITS, one
    //    character space,
                appendAndTrace(bufWWMI, "DD.TOT_L2_CACHE_STD", totL2CacheStd, 4);
                appendAndTrace(bufWWMI, "DD.TOTL2CACHESTDUNITS", totL2CacheStdUnits);
                bufWWMI.append(" ");
    //CTRC 3). DD.TOT_L2_CACHE_STD (char limit of 4 imposed on the attribute/field), one character space,
                appendAndTrace(bufCONTRCTINVTITLE, "DD.TOT_L2_CACHE_STD", totL2CacheStd, 4);
                bufCONTRCTINVTITLE.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"DD.TOT_L2_CACHE_STD has no value");
            }
            if (memRamStd.length() >0) // DD was found (4)
            {
    //WWMI 4). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field), no space, DD.MEMRAMSTDUNITS, one character space,
                appendAndTrace(bufWWMI, "DD.MEMRAMSTD", memRamStd, 4);
                appendAndTrace(bufWWMI, "DD.MEMRAMSTDUNITS", memRamStdUnits);
                bufWWMI.append(" ");
    //CTRC 4). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field, type a '/' immediately following the number with
    //    no spaces before or after the '/'
                appendAndTrace(bufCONTRCTINVTITLE, "DD.MEMRAMSTD", memRamStd, 4);
                bufCONTRCTINVTITLE.append("/");
            }
            else {
                traceSb.append(NEWLINE+"DD.MEMRAMSTD has no value");
            }
            if (hddCapacity.length() >0) // HD was found (5)
            {
    //WWMI 5). HD.HDDCAPACITY, no space, HD.HDDCAPACITYUNITS, one character space,
                appendAndTrace(bufWWMI, "HD.HDDCAPACITY", hddCapacity);
                appendAndTrace(bufWWMI, "HD.HDDCAPACITYUNITS", hddCapacityUnits);
                bufWWMI.append(" ");
    //CTRC 5). HD.HDDCAPACITY, one character space,
                appendAndTrace(bufCONTRCTINVTITLE, "HD.HDDCAPACITY", hddCapacity);
                bufCONTRCTINVTITLE.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"HD.HDDCAPACITY has no value");
            }
            if (posAbbrev.length() >0) // POS was found (6)
            {
    //WWMI 6). POS.POSABBREV, one character space,
                appendAndTrace(bufWWMI, "POS.POSABBREV", posAbbrev);
                bufWWMI.append(" ");
    //CTRC 6). POS.POSABBREV, one character space,
                appendAndTrace(bufCONTRCTINVTITLE, "POS.POSABBREV", posAbbrev);
                bufCONTRCTINVTITLE.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"POS.POSABBREV has no value");
            }
            if (monScrSize.length() >0) // MON was found (7)
            {
    //WWMI 7). MON.SCREENSIZENOM_IN, one character space,
                appendAndTrace(bufWWMI, "MON.SCREENSIZENOM_IN", monScrSize);
                bufWWMI.append(" ");
    //CTRC 7). MON.SCREENSIZENOM_IN, one character space,
                appendAndTrace(bufCONTRCTINVTITLE, "MON.SCREENSIZENOM_IN", monScrSize);
                bufCONTRCTINVTITLE.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"MON.SCREENSIZENOM_IN has no value");
            }
            if (monTubeType.length() >0) // MON was found (WWMI 8)
            {
    //WWMI 8). MON.TUBETYPE, one character space (char limit of 4 imposed on the attribute/field),
                appendAndTrace(bufWWMI, "MON.TUBETYPE", monTubeType, 4);
                bufWWMI.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"MON.TUBETYPE has no value");
            }
            if (cdrType.length() >0) // CDR was found (WWMI 9, CTRCT 8)
            {
    //WWMI 9). CDR.CDROPTITYPE (if the value of this attribute/field is 'CD-ROM' then go to the CDSPEED of this CDR element and
    //    use only the first three characters of the CDSPEED value; if the value of the CDROPTITYPE is not 'CD-ROM' then use
    //    whatever the value is)
    //CTRC 8). CDR.CDROPTITYPE (if the value of this attribute/field is 'CD-ROM' then go to the CDSPEED of this CDR element
    //    and use only the first three characters of the CDSPEED value; if the value of the CDROPTITYPE is not 'CD-ROM'
    //    then use whatever the value is)
                if (cdrFlagType.equals("0010")) // CD-ROM
                {
                    appendAndTrace(bufWWMI, "CDR.CDSPEED", cdSpeed, 3);
                    appendAndTrace(bufCONTRCTINVTITLE, "CDR.CDSPEED", cdSpeed, 3);
                }
                else
                {
                    appendAndTrace(bufWWMI, "CDR.CDROPTITYPE", cdrType);
                    appendAndTrace(bufCONTRCTINVTITLE, "CDR.CDROPTITYPE", cdrType);
                }
            }
            else {
                traceSb.append(NEWLINE+"CDR.CDROPTITYPE has no value");
            }
        }
        else
        {
            MessageFormat msgf = new MessageFormat(bundle.getString("Error_NO_DD"));
            Object[] args = new String[1];
            traceSb.append(NEWLINE+"DD EntityGroup not found");
            //  Fail the ABR with a message that there is no derived data entity for the selected OF/VAR.
            eg = m_elist.getEntityGroup(getRootEntityType());
            args[0] = eg.getLongDescription();
            rptSb.append(msgf.format(args));
            ok= false;
        }

        return ok;
    }

    /**********************************************************************************
    * Case "Mobile" (0060)
    * IF FAM.FAMILYNAME Contains "WorkPad" (0340)
    *   World Wide Master Index (OFWWMI): Mobile | WorkPad
    *   1). SE.SERIESNAME, one character space, then '*', one character space,
    *   2). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field), no space, DD.MEMRAMSTDUNITS.
    *
    *   *Example of above:
    *   OFWWMI = A21 * 128MB
    *
    *   Offering Contract Invoice Title (OFCONTRCTINVTITLE): Mobile | WorkPad
    *   1). SE.SERIESNAME, one character space,
    *   2). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field).
    *
    *   *Example of above:
    *   OFCONTRCTINVTITLE = A21 128
    */
    private boolean getMobileWorkPad(DerivedString bufWWMI, DerivedString bufCONTRCTINVTITLE)
    {
        boolean ok = true;
        String seriesName = "";
        String memRamStd = "";
        String memRamStdUnits = "";

        EntityGroup eg = m_elist.getEntityGroup("SE");
        if (eg!=null && eg.getEntityItemCount()>0)
        {
            egDebugInfo(eg);
            seriesName = getAttributeValue("SE", eg.getEntityItem(0).getEntityID(), "SERIESNAME","");
        }
        else {
            traceSb.append(NEWLINE+"SE EntityGroup not found");
        }

        eg = m_elist.getEntityGroup("DD");
        if (eg!=null && eg.getEntityItemCount()>0)
        {
            egDebugInfo(eg);
            memRamStd = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "MEMRAMSTD","");
            memRamStdUnits = getAttributeValue("DD", eg.getEntityItem(0).getEntityID(), "MEMRAMSTDUNITS","");

// for WWMI
//1). SE.SERIESNAME, one character space, then '*', one character space,
//2). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field), no space, DD.MEMRAMSTDUNITS.

// for CONTRCTINVTITLE
//1). SE.SERIESNAME, one character space,
//2). DD.MEMRAMSTD (char limit of 4 imposed on the attribute/field).

            // fill in derived attributes
            if (seriesName.length()>0) // SE was found
            {
                appendAndTrace(bufCONTRCTINVTITLE, "SE.SERIESNAME", seriesName);// (1)
                bufCONTRCTINVTITLE.append(" ");  // append one character space (1)

                appendAndTrace(bufWWMI, "SE.SERIESNAME", seriesName);// (1)
                bufWWMI.append(" ");  // append one character space (1)
                bufWWMI.append("*");
                bufWWMI.append(" ");
            }
            else {
                traceSb.append(NEWLINE+"SE.SERIESNAME has no value");
            }

            if (memRamStd.length() >0) // DD was found
            {
                appendAndTrace(bufWWMI, "DD.MEMRAMSTD", memRamStd, 4); // (2)
                appendAndTrace(bufWWMI, "DD.MEMRAMSTDUNITS", memRamStdUnits);

                appendAndTrace(bufCONTRCTINVTITLE, "DD.MEMRAMSTD", memRamStd, 4); // (2)
            }
            else {
                traceSb.append(NEWLINE+"DD.MEMRAMSTD has no value");
            }
        }
        else
        {
            MessageFormat msgf = new MessageFormat(bundle.getString("Error_NO_DD"));
            Object[] args = new String[1];
            traceSb.append(NEWLINE+"DD EntityGroup not found");
            //  Fail the ABR with a message that there is no derived data entity for the selected OF/VAR.
            eg = m_elist.getEntityGroup(getRootEntityType());
            args[0] = eg.getLongDescription();
            rptSb.append(msgf.format(args));
            ok= false;
        }

        return ok;
    }

    /**********************************************************************************
    * Case Else (Default, Includes Options/Services)
    *   World Wide Master Index (OFWWMI): Default
    *   OF: OF.OFWWMI = OF.OFFERINGPNUMBDESC
    *   VAR:    VAR.VARWWMI = VAR.VARPNUMBDESC
    *
    *   Offering Contract Invoice Title (OFCONTRCTINVTITLE): Default
    *   OF: OF.OFCONTRCTINVTITLE = OF.OFFERINGPNUMBDESC
    *   VAR:    VAR.VARCONTRCTINVTITLE = VAR.VARPNUMBDESC
    */
    private boolean getSystemDefault(DerivedString bufWWMI, DerivedString bufCONTRCTINVTITLE)
    {
        String value;
        boolean trunc;
        String attrCode = "VARPNUMBDESC";
        if ("OF".equals(getEntityType())) {
            attrCode = "OFFERINGPNUMBDESC";
        }

// for WWMI
//OF:   OF.OFWWMI = OF.OFFERINGPNUMBDESC
//VAR:  VAR.VARWWMI = VAR.VARPNUMBDESC

        value = getAttributeValue(getRootEntityType(), getRootEntityID(), attrCode,"");
        // only one attribute used for derivation, truncate if needed

        trunc = bufWWMI.appendAndTrunc(value);
        traceSb.append(NEWLINE+""+bufWWMI.getType()+" "+getRootEntityType()+"."+attrCode+" = !"+value+"!");
        if (trunc) {
            traceSb.append(" was truncated to !"+bufWWMI.toString()+"!");
        }

// for CONTRCTINVTITLE
//OF:   OF.OFCONTRCTINVTITLE = OF.OFFERINGPNUMBDESC
//VAR:  VAR.VARCONTRCTINVTITLE = VAR.VARPNUMBDESC
        trunc = bufCONTRCTINVTITLE.appendAndTrunc(value);
        traceSb.append(NEWLINE+""+bufCONTRCTINVTITLE.getType()+" "+getRootEntityType()+"."+attrCode+" = !"+value+"!");
        if (trunc) {
            traceSb.append(" was truncated to !"+bufCONTRCTINVTITLE.toString()+"!");
        }

        return true;
    }

    /**********************************************************************************
    * If (EntityType = OF) and (OF.OFFERINGTYPE <> "SYSTEM" (0080))   (Else)
    *
    *   World Wide Master Index (OFWWMI): Default
    *   OFWWMI = OFFERINGPNUMBDESC
    *
    *   Offering Contract Invoice Title (OFCONTRCTINVTITLE): Default
    *   OFCONTRCTINVTITLE = OFFERINGPNUMBDESC
    */
    private boolean getOFNotSystemDefault(DerivedString bufWWMI, DerivedString bufCONTRCTINVTITLE)
    {
// for WWMI
//OFWWMI = OFFERINGPNUMBDESC
        String value = getAttributeValue(getRootEntityType(), getRootEntityID(), "OFFERINGPNUMBDESC","");
        // only one attribute used for derivation, truncate if needed
        boolean trunc = bufWWMI.appendAndTrunc(value);
        traceSb.append(NEWLINE+""+bufWWMI.getType()+" OF.OFFERINGPNUMBDESC = !"+value+"!");
        if (trunc) {
            traceSb.append(" was truncated to !"+bufWWMI.toString()+"!");
        }

// for CONTRCTINVTITLE
//OFCONTRCTINVTITLE = OFFERINGPNUMBDESC
        trunc = bufCONTRCTINVTITLE.appendAndTrunc(value);
        traceSb.append(NEWLINE+""+bufCONTRCTINVTITLE.getType()+" OF.OFFERINGPNUMBDESC = !"+value+"!");
        if (trunc) {
            traceSb.append(" was truncated to !"+bufCONTRCTINVTITLE.toString()+"!");
        }

        return true;
    }

    /**********************************************************************************
    * Try to append string and output debug messages
    * @return    boolean
    */
    private boolean appendAndTrace(DerivedString buf, String source, String value)
    {
        boolean success = false;
        if (!buf.isOk())
        {
            traceSb.append(NEWLINE+""+buf.getType()+" has previously exceeded or reached the max value, can not append "+
                    source+" !"+value+"!");
        }
        else {
            traceSb.append(NEWLINE+""+buf.getType()+" "+source+" = "+value);
            success = buf.append(value);
            if (!success)
            {
                traceSb.append(NEWLINE+""+buf.getType()+" Could not append "+source+" !"+value+
                        "! maxlen: "+buf.getMaxLen()+" curlen: "+buf.getCurLen());
            }
            traceSb.append(" curStr: !"+buf.toString()+"!");
        }

        return success;
    }

    /**********************************************************************************
    * Try to append string and output debug messages
    * @return    boolean
    */
    private boolean appendAndTrace(DerivedString buf, String source, String value, int len)
    {
        boolean success = false;
        if (!buf.isOk())
        {
            traceSb.append(NEWLINE+""+buf.getType()+" has previously exceeded or reached the max value, can not append "+
                    source+" !"+value+"!");
        }
        else {
            traceSb.append(NEWLINE+""+buf.getType()+" "+source+" = "+value);
            if (value.length()>len) {
                traceSb.append(" exceeds specified len: "+len+". It will be truncated!");
            }
            success = buf.append(value, len);
            if (!success)
            {
                traceSb.append(NEWLINE+""+buf.getType()+" Could not append "+source+" !"+value+
                        "! maxlen: "+buf.getMaxLen()+" curlen: "+buf.getCurLen());
            }
            traceSb.append(" curStr: !"+buf.toString()+"!");
        }

        return success;
    }

    /**********************************************************************************
    *  Get Name based on navigation attributes
    *
    *@return    java.lang.String
    */
    private String getNavigationName() throws java.sql.SQLException, MiddlewareException
    {
        StringBuffer navName = new StringBuffer();
        // NAME is navigate attributes
        EntityGroup eg =  new EntityGroup(null, m_db, m_prof, getRootEntityType(), "Navigate");
        EANList metaList = eg.getMetaAttribute(); // iterator does not maintain navigate order
        for (int ii=0; ii<metaList.size(); ii++)
        {
            EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
            navName.append(getAttributeValue(getRootEntityType(), getRootEntityID(), ma.getAttributeCode()));
            navName.append(" ");
        }

        return navName.toString();
    }

    /***********************************************
    *  Get ABR description
    *
    *@return    java.lang.String
    */
    public String getDescription()
    {
        String desc =
        "The World Wide Master Index (OF.OFWWMI and VAR.VARWWMI) and the Offering Contract Invoice Title "+
        "(OF.OFCONTRCTINVTITLE and VAR.VARCONTRCTINVTITLE) are derived from attributes on the series and "+
        "SBB/elements attached to the offering.";

        if (bundle!=null) {
            desc = bundle.getString("DESCRIPTION");
        }

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return    java.lang.String
    */
    public String getABRVersion() {
        return "$Revision: 1.7 $";
    }

    private Locale getLocale(int nlsID) {
        Locale locale = null;
        switch (nlsID) {
        case 1:
            locale = Locale.US;
        case 2:
            locale = Locale.GERMAN;
            break;
        case 3:
            locale = Locale.ITALIAN;
            break;
        case 4:
            locale = Locale.JAPANESE;
            break;
        case 5:
            locale = Locale.FRENCH;
            break;
        case 6:
            locale = new Locale("es", "ES");
            break;
        case 7:
            locale = Locale.UK;
            break;
        default:
            locale = Locale.US;
        }
        return locale;
    }

    private void egDebugInfo(EntityGroup eg)
    {
        traceSb.append(NEWLINE+""+eg.getEntityType()+" EntityGroup has EntityItem IDs:");
        for(int x=0; x<eg.getEntityItemCount(); x++) {
            traceSb.append(" "+eg.getEntityItem(x).getEntityID());
        }
        traceSb.append(". Using entityItem ID: "+eg.getEntityItem(0).getEntityID());
    }

    /***********************************************
    *  Convenience class to manage string
    */
    private class DerivedString
    {
        private int maxlen; // maximum chars this string can have
        private StringBuffer sb = new StringBuffer();
        private String typeStr;
        private boolean bisOk = true; // once max len is hit, nothing can be appended or even tried
        DerivedString(int max, String type)
        {
            maxlen = max;
            typeStr = type;
        }
        // append entire string if it will fit without truncation
        // return false if string did not fit
        boolean append(String str)
        {
            boolean ok = true;
            if (str.length()>0) { // something to append
                if (!bisOk) {
                    ok=false;
                } // already tried to exceed maxlen
                else {
                    if (sb.length()+str.length()>maxlen)
                    {
                        bisOk=false;  // nothing else can be attempted after this is set
                        if (sb.length()==0) // this is first attempt to append, allow truncation so some text is set
                        {
                            sb.append(str);
                            sb.setLength(maxlen);
                            traceSb.append(NEWLINE+"Truncating and appending first str !"+str+"! ");
                        }
                        else {
                            ok=false;
                        }
                    }
                    else {
                        sb.append(str);
                    }
                }
            }
            return ok;
        }

        // append string of max length=len if it will fit without truncation
        // return false if string did not fit
        boolean append(String str, int len)
        {
            boolean ok = true;
            if (str.length()>0) {  // something to append
                if (!bisOk) {
                    ok = false;
                } // already tried to exceed maxlen
                else {
                    if (str.length()>len) {
                        str = str.substring(0,len);
                    }

                    ok = append(str);
                }
            }

            return ok;
        }

        // append entire string, then truncate if needed
        // return true if truncated
        boolean appendAndTrunc(String str)
        {
            boolean ok = false;

            if (str.length()>0) {
                sb.append(str);
                if (sb.length()>maxlen)
                {
                    sb.setLength(maxlen);
                    ok= true;
                }
            }
            return ok;
        }

        public String toString() { return sb.toString(); }
        int getMaxLen() { return maxlen; }
        int getCurLen() { return sb.length(); }
        String getType() { return typeStr;} // debug msg only
        void clear() { sb.setLength(0);
            bisOk=true;} // debug only
        boolean isOk() { return bisOk;}
    }
}
/*
PR.BRANDCODE Brand Code U [flagCode:shortDesc]
Archive [10009:Archive]Commercial Desktop Products [10010:CDT]IntelliStation [10011:IntelliStation]
Misc PartnerInfo Projects [10013:MiscPI]Mobile Systems [10014:Mobile]Netfinity [10015:Netfinity]
Network Hardware [10016:NetworkHardware]Options & Peripherals [10017:Options]Printers [10018:Printers]
PS/55 Systems [10019:PS/55]RS/6000 Client [10020:RS/6000]Services [10021:Services]Software [10022:Software]
Visual Products [10023:Visuals]PSG Division [10024:PSG]
*/
/*
OF.OFFERINGTYPE Type U
DISPLAY [0010:DISPLAY]MONITOR [0020:MONITOR]NO TYPE [0030:NO TYPE]OPTION [0040:OPTION]PRINTER [0050:PRINTER]
SERVICE [0060:SERVICE]SOFTWARE [0070:SOFTWARE]SYSTEM [0080:SYSTEM]INVALID SELECTION [NEW0:INVALID SELECTION]
*/
/*
FAM.FAMILYNAME Family U
AAP Solutions [0010:AAP Solutions]Aptiva [0020:Aptiva]CDT Options [0030:CDT Options]Desktop [0040:Desktop]
Display Opt [0050:Display Opt]Displays [0060:Displays]Docking [0070:Docking]Enhanced [0080:Enhanced]Flat Panels [0090:Flat Panels]
Intellistation [0100:Intellistation]Misc PI [0110:Misc PI]MiscPI [0120:MiscPI]Mobile AAP Publications [0130:Mobile AAP Publications]
Monitors [0140:Monitors]NCD Options [0150:NCD Options]NS Options - OBI [0160:NS Options - OBI]Netfinity [0170:Netfinity]
Netfinity 35XX [0180:Netfinity 35XX]Netfinity 3XXX [0190:Netfinity 3XXX]Netfinity 4000R [0200:Netfinity 4000R]
Network Hardware [0210:Network Hardware]Network Station [0220:Network Station]Options - OBI [0230:Options - OBI]
Options - Server [0240:Options - Server]Orphan [0250:Orphan]PC Server [0260:PC Server]PS/55 Systems [0270:PS/55 Systems]
Printers [0280:Printers]RS/6000 Client [0290:RS/6000 Client]Server Opt. [0300:Server Opt.]Services [0310:Services]
Software [0320:Software]ThinkPad [0330:ThinkPad]WorkPad [0340:WorkPad]NetVista [0350:NetVista]IBM eServer [0360:IBM eServer]
NetVista Internet Appliance [0370:NetVista Internet Appliance]NetVista Thin Client [0380:NetVista Thin Client]
IBM TotalStorage [0390:IBM TotalStorage]Options - Services [0400:Options - Services]Options - Odyssey [0410:Options - Odyssey]
Appliance Division [0420:Appliance Division]ThinkCentre [0430:ThinkCentre]Flat Panel [0440:Flat Panel]
CRT [0450:CRT]Unknown Family [0460:]
*/
/* PRC.PROCESSORTYPE NOT all AMD have short descriptions!! and some short desc >15 chars!
PROCESSORTYPE Processor Type U
-- [0010:--]486BL2 [0020:486BL2]486DX2 [0030:486DX2]
486DX4 [0040:486DX4]486SLC2 [0050:486SLC2]486SX [0060:486SX]5x86 [0070:5x86]6x86 [0080:6x86]6x86 PR150+ [0090:6x86 PR150+]
6x86 PR166+ [0100:6x86 PR166+]6x86MX PR166 MMX-Enhanced [0110:6x86MX PR166 MMX-Enhanced]80960 HD [0120:80960 HD]
80960 JD [0130:80960 JD]80960 JF [0140:80960 JF]960CF [0150:960CF]AMD Athlon [0160:AMD Athlon]
AMD-K6 266 MHz [0170:AMD-K6 266 MHz]AMD-K6 300 MHz [0180:AMD-K6 300 MHz]Intel Celeron [0190:Intel Celeron]
Cyrix 6x86-133 [0200:Cyrix 6x86-133]Cyrix 6x86-150 [0210:Cyrix 6x86-150]Cyrix 6x86-166 [0220:Cyrix 6x86-166]
Cyrix MediaGX 150MHz [0230:Cyrix MediaGX 150MHz]Cyrix MediaGX 166MHz [0240:Cyrix MediaGX 166MHz]
Cyrix MediaGX 180MHz [0250:Cyrix MediaGX 180MHz]Cyrix MediaGX 200MHz [0260:Cyrix MediaGX 200MHz]
Cyrix MediaGXm 233 MHz processor, MMX enabled [0270:Cyrix MediaGXm 233 MHz processor, MMX enabled]
Cyrix MediaGXm 266 MHz - MMX enhanced [0280:Cyrix MediaGXm 266 MHz - MMX enhanced]
IBM 300 Performance Rated MMX-Enhanced [0290:IBM 300 Performance Rated MMX-Enhanced]K6 [0300:K6]
K6 MMX-Enhanced [0310:K6 MMX-Enhanced]K6 with 3D Now! Technology [0320:K6 with 3D Now! Technology]
K6-2 [0330:K6-2]K6-2 366MHz with 3D, MMX-Enhanced [0340:K6-2 366MHz with 3D, MMX-Enhanced]
K6-2 400MHz with 3D, MMX-Enhanced [0350:K6-2 400MHz with 3D, MMX-Enhanced]
K6-2 with 3D Now! Technology [0360:K6-2 with 3D Now! Technology]
K6-2/350MHz with 3D Now! Technology [0370:K6-2/350MHz with 3D Now! Technology]
K6-2/450MHz with 3D Now! Technology [0380:K6-2/450MHz with 3D Now! Technology]
K6-2/475MHz with 3D Now! Technology [0390:K6-2/475MHz with 3D Now! Technology]
K7 with 3D Now! Technology [0400:K7 with 3D Now! Technology]
Mobile Intel Celeron processor [0410:Mobile Intel Celeron processor]
Mobile Pentium II [0420:Mobile Pentium II]Open [0430:Open]Pentium [0440:Pentium]
Pentium (PC Server)/S/390 microprocessor complex [0450:Pentium (PC Server)/S/390 microprocessor complex]
Pentium II [0460:Pentium II]Pentium II Xeon [0470:Pentium II Xeon]
Pentium II with MMX technology [0480:Pentium II with MMX technology]
Pentium III [0490:Pentium III]Pentium III Xeon [0500:Pentium III Xeon]
Pentium MMX [0510:Pentium MMX]Pentium Pro [0520:Pentium Pro]Pentium with MMX technology [0530:Pentium with MMX technology]
PowerPC 403 [0540:PowerPC 403]PowerPC 403GA [0550:PowerPC 403GA]PowerPC 403GCX [0560:PowerPC 403GCX]
PowerPC 603eV [0570:PowerPC 603eV]R4600/R4700 [0580:R4600/R4700]RISC [0590:RISC]i960HD [0600:i960HD]
Mobile Intel Pentium III w/ SpeedStep Technology [0610:Mobile Intel Pentium III w/ SpeedStep Technology]
National Geode GXLV-233 [0620:National Geode GXLV-233]x86 [0630:x86]Duron [0640:Duron]Pentium 4 [0650:Pentium 4]
uFCPGA2 [0660:uFCPGA2]Mobile Intel Pentium 4 processor - M [0670:Mobile Intel Pentium 4 processor - M]
Crusoe TM5400 CMS4.1.3 [0680:Crusoe TM5400 CMS4.1.3]Crusoe TM5600 CMS4.1.3 [0690:Crusoe TM5600 CMS4.1.3]
Xeon [0700:Xeon]Intel Itanium [0710:Intel Itanium]
Mobile Intel Pentium III processor - M [0720:Mobile Intel Pentium III processor - M]
Pentium III With SpeedStep Technology LV [0730:Pentium III With SpeedStep Technology LV]
Pentium III With SpeedStep Technology ULV [0740:Pentium III With SpeedStep Technology ULV]
PMC-Sierra RM5231A-350 [0750:PMC-Sierra RM5231A-350]Intel Xeon MP [0760:Intel Xeon MP]
LV Mobile Intel Pentium III processor - M [0770:LV Mobile Intel Pentium III processor - M]
ULV Mobile Intel Pentium III processor - M [0780:ULV Mobile Intel Pentium III processor - M]
Intel Celeron [0790:Intel Celeron]Intel Pentium III [0800:Intel Pentium III]
Intel Pentium 4 [0810:Intel Pentium 4]Intel Xeon [0820:Intel Xeon]Intel Pentium III Xeon [0830:Intel Pentium III Xeon]
Intel Pentium M processor [0840:Intel Pentium M processor]
LV Intel Pentium M processor [0850:LV Intel Pentium M processor]
ULV Intel Pentium M processor [0860:ULV Intel Pentium M processor]
LV Mobile Intel Celeron processor [0870:LV Mobile Intel Celeron processor]
ULV Mobile Intel Celeron processor [0880:ULV Mobile Intel Celeron processor]
Athlon Thoroughbred [0890:Athlon Thoroughbred]AMD Athlon XP 1700 [0900:AMD Athlon XP 1700]
AMD Athlon XP 1800 [0910:AMD Athlon XP 1800]AMD Athlon XP 1900 [0920:AMD Athlon XP 1900]
AMD Athlon XP 2000 [0930:AMD Athlon XP 2000]AMD Athlon XP 2100 [0940:AMD Athlon XP 2100]
AMD Athlon XP 2200 [0950:AMD Athlon XP 2200]
Mobile Intel Pentium 4 processor - T [0960:Mobile Intel Pentium 4 processor - T]
Mobile Intel Dothan Processor - M [0970:Mobile Intel Dothan Processor - M]
Intel Itanium 2 [0980:Intel Itanium 2]Mobile Intel Pentium 4 processor [0990:Mobile Intel Pentium 4 processor]
Intel Pentium 4 with Hyper-Threading Technology [1000:]Mobile Intel Prescott processor [1010:]Intel Foster [1020:]
WILLIAMETTE/MT BLANC [1030:]Intel Pentium [1040:]National Semiconductor Geode GXI [1050:]
National Semiconductor Geode x86 [1060:]Via C3 [1070:]AMD Opteron Processor Model 240 [1080:]
AMD Opteron Processor Model 242 [1090:]AMD Opteron Processor Model 244 [1100:]
AMD Opteron Processor Model 246 [1110:]PowerPC 970 [1120:]Intel Celeron M processor [1130:]
*/
/*
SERIESNAME Series Name U
1000 [0010:1000]235 [0020:235]240 [0030:240]3000 [0040:3000]315 [0050:315]3270 [0060:3270]3500 [0070:3500]
365 [0080:365]380 [0090:380]390 [0100:390]3XXX [0105:3XXX]35XX [0106:35XX]3500 M10 [0108:3500 M10]4XX [0110:4XX]
4000R [0115:4000R]4500R [0117:4500R]5000 [0120:5000]5100 [0125:5100]5100R [0130:5100R]5250 [0140:5250]535 [0150:535]
5500 [0160:5500]5500-M10 [0170:5500-M10]5500-M20 [0180:5500-M20]560 [0190:560]5600 [0200:5600]56XX [0210:56XX]
570 [0220:570]5XXX [0230:5XXX]5XXXR [0240:5XXXR]600 [0250:600]6000 [0260:6000]6XXX [0270:6XXX]7000 [0280:7000]
7000-M10 [0290:7000-M10]730 [0300:730]7500 [0310:7500]760 [0320:760]765 [0330:765]770 [0340:770]7XXX [0350:7XXX]
8500R [0360:8500R]9XXX [0370:9XXX]XXXX [0375:XXXX]AAP Pubs [0380:AAP Pubs]AAP Solution [0390:AAP Solution]
ASCII [0400:ASCII]AVATAR [0410:AVATAR]Acc_General [0420:Acc_General]Acc_Mobile [0430:Acc_Mobile]Aptivas [0440:Aptivas]
Artic [0450:Artic]CDT Other [0460:CDT Other]COASTAL [0470:COASTAL]COASTAL REFRESH [0480:COASTAL REFRESH]
Cluster [0490:Cluster]Communications/O [0500:Communications/O]Communications/S [0510:Communications/S]
Comp Products [0520:Comp Products]Crossbrand [0530:Crossbrand]Desktop Expansion [0540:Desktop Expansion]
Display Options [0550:Display Options]Docking [0560:Docking]Docking Accessories [0570:Docking Accessories]
E Pro [0580:E Pro]E Series [0590:E Series]Enh Accessories [0600:Enh Accessories]Entry [0610:Entry]Fibre [0620:Fibre]
Flat Panel [0630:Flat Panel]General Purpose [0640:General Purpose]Home Director [0650:Home Director]
INTIMIDATOR [0660:INTIMIDATOR]INTIMIDATOR REF [0670:INTIMIDATOR REF]INTIMIDATOR REF 2 [0680:INTIMIDATOR REF 2]
M Pro [0690:M Pro]MOB Other [0700:MOB Other]Mechanical [0710:Mechanical]Memory/O [0720:Memory/O]
Memory/S [0730:Memory/S]Misc PI-NF [0740:Misc PI-NF]Mobile AAP Pubs [0750:Mobile AAP Pubs]Multimedia [0760:Multimedia]
NCD 100 [0770:NCD 100]NCD 1000 [0780:NCD 1000]NCD 300 [0790:NCD 300]NCD 390 [0800:NCD 390]NCD 400 [0810:NCD 400]
NCD 6000 [0820:NCD 6000]NCD Option [0830:NCD Option]NS_Networking [0840:NS_Networking]
Network-Hardware [0850:Network-Hardware]Networking [0860:Networking]Networking/O [0870:Networking/O]
Options - OBI [0880:Options - OBI]Options-OBI [0890:Options-OBI]Orphans [0900:Orphans]PC [0910:PC]
PC 100 [0920:PC 100]PC 300 [0930:PC 300]PC 300GL [0940:PC 300GL]PC 300PL [0950:PC 300PL]PC 700 [0960:PC 700]
PCS 310 [0970:PCS 310]PCS 315 [0980:PCS 315]PCS 320 [0990:PCS 320]PCS 325 [1000:PCS 325]PCS 330 [1010:PCS 330]
PCS 520 [1020:PCS 520]PCS 704 [1030:PCS 704]PCS 720 [1040:PCS 720]PS/55 System [1050:PS/55 System]
PWS Other [1060:PWS Other]Printer [1070:Printer]Processor/O [1080:Processor/O]Processor/S [1090:Processor/S]
Professional [1100:Professional]Pub Pack/O [1110:Pub Pack/O]Pub Pack/S [1120:Pub Pack/S]RAID/SCSI [1130:RAID/SCSI]
RS/6000 Clients [1140:RS/6000 Clients]Rack [1150:Rack]Riser [1160:Riser]S Series [1170:S Series]SSD [1180:SSD]
Security/O [1190:Security/O]Security/S [1200:Security/S]Server Options [1210:Server Options]
IBM ServicePac [1220:IBM ServicePac]Software Applications [1230:Software Applications]Storage [1240:Storage]
Storage/O [1250:Storage/O]Storage/S [1260:Storage/S]VLH [1270:VLH]IBM WorkPad [1280:IBM WorkPad]Z Pro [1290:Z Pro]
6000R [1300:6000R]7100 [1310:7100]7600 [1320:7600]4100R [1330:4100R]A60p [1340:A60p]A40 [1350:A40]A40p [1360:A40p]
A40i [1370:A40i]X40 [1380:X40]S40 [1390:S40]S40p [1400:S40p]A20 [1410:A20]64EP [1420:64EP]A Series [1430:A Series]
T Series [1440:T Series]A60 [1450:A60]3500 M20 [1460:3500 M20]A100 [1470:A100]A200 [1480:A200]A500 [1490:A500]
3600 [1500:3600]X40i [1510:X40i]A60i [1520:A60i]A20i [1530:A20i]xSeries 330 [1540:xSeries 330]
xSeries 220 [1550:xSeries 220]xSeries 340 [1560:xSeries 340]xSeries 230 [1570:xSeries 230]xSeries 240 [1580:xSeries 240]
xSeries 200 [1590:xSeries 200]xSeries 150 [1600:xSeries 150]xSeries 130 [1610:xSeries 130]xSeries 135 [1620:xSeries 135]
xSeries 380 [1630:xSeries 380]xSeries 350 [1640:xSeries 350]xSeries 370 [1650:xSeries 370]i Series [1660:i Series]
X Series [1670:X Series]ThinkPad TransNote [1680:ThinkPad TransNote]S40i [1690:S40i]xSeries 250 [1700:xSeries 250]
xSeries xxx [1710:xSeries xxx]A10 [1720:A10]NetVista J320 [1730:NetVista J320]N2200 [1740:N2200]N2800 [1750:N2800]
R Pro [1760:R Pro]xSeries 450 [1770:xSeries 450]xSeries 342 [1780:xSeries 342]xSeries 232 [1790:xSeries 232]
xSeries 430 [1800:xSeries 430]xSeries 242 [1810:xSeries 242]130 Series [1820:130 Series]s Series [1830:s Series]
R Series [1840:R Series]xSeries 300 [1850:xSeries 300]xSeries 252 [1860:xSeries 252]xSeries 352 [1870:xSeries 352]
xSeries 440 [1880:xSeries 440]xSeries 460 [1890:xSeries 460]Service [1900:Service]Printer Features [1910:Printer Features]
A41 [1920:A41]A400 [1930:A400]xSeries 260 [1940:xSeries 260]A21 [1950:A21]A21i [1960:A21i]
NAS 7000/7000G [1970:NAS 7000/7000G]Service Options [1980:Service Options]M40i [1990:M40i]M40 [2000:M40]
NetVista Internet Appliance I50 [2010:NetVista Internet Appliance I50]M41 [2020:M41]M41z [2030:M41z]X41 [2040:X41]
X41z [2050:X41z]A22 [2060:A22]A22i [2070:A22i]M42 [2080:M42]M42z [2090:M42z]X42 [2100:X42]X42z [2110:X42z]W50 [2120:W50]
NAS 200/IP200i [2130:NAS 200/IP200i]NAS 300/300G [2140:NAS 300/300G]NetVista Internet Appliance I30 [2150:NetVista Internet Appliance I30]
A25 [2160:A25]A22p [2170:A22p]M Series [2180:M Series]xSeries 360 [2190:xSeries 360]N70 [2200:N70]N2200I [2210:N2200I]
Monitors [2220:Monitors]Software [2230:Software]xSeries 234 [2240:xSeries 234]NetVista Thin Client Software [2250:NetVista Thin Client Software]
Appliance a180 for Work Directing [2260:Appliance a180 for Work Directing]Appliance a160 for Forward Caching [2270:Appliance a160 for Forward Caching]
Appliance a160 for Reverse Caching [2280:Appliance a160 for Reverse Caching]Internet Caching Appliance [2290:Internet Caching Appliance]
N50 [2300:N50]xSeries 345 [2310:xSeries 345]xSeries 235 [2320:xSeries 235]xSeries 305 [2330:xSeries 305]
xSeries 225 [2340:xSeries 225]xSeries 330T [2350:xSeries 330T]Acc_General-Odyssey [2360:Acc_General-Odyssey]
Docking-Odyssey [2370:Docking-Odyssey]Monitors-Odyssey [2380:Monitors-Odyssey]Memory-Odyssey [2390:Memory-Odyssey]
Multimedia-Odyssey [2400:Multimedia-Odyssey]Networking-Odyssey [2410:Networking-Odyssey]Printer-Odyssey [2420:Printer-Odyssey]
Processor-Odyssey [2430:Processor-Odyssey]Security-Odyssey [2440:Security-Odyssey]Service Options-Odyssey [2450:Service Options-Odyssey]
Software-Odyssey [2460:Software-Odyssey]Storage-Odyssey [2470:Storage-Odyssey]VLH-Odyssey [2480:VLH-Odyssey]
Communications-Odyssey [2490:Communications-Odyssey]xSeries 343 [2500:xSeries 343]NetVista Options [2510:NetVista Options]
xSeries 335 [2520:xSeries 335]xSeries 255 [2530:xSeries 255]xSeries 205 [2540:xSeries 205]
xSeries Hosting Appliances [2550:xSeries Hosting Appliances]xSeries Caching Appliances [2560:xSeries Caching Appliances]
xSeries Load Balancing Appliances [2570:xSeries Load Balancing Appliances]xSeries Web Serving Appliances [2580:xSeries Web Serving Appliances]
xSeries Streaming Media Appliances [2590:xSeries Streaming Media Appliances]xSeries 445 [2600:xSeries 445]xSeries 120 [2610:xSeries 120]
S42 [2620:S42]xSeries 365 [2630:xSeries 365]xSeries 410 [2640:xSeries 410]xSeries 110 [2650:xSeries 110]NAS 100 [2660:NAS 100]
BladeCenter HS20 [2670:BladeCenter HS20]xSeries 344 [2680:xSeries 344]BladeCenter [2690:BladeCenter]xSeries 455 [2700:xSeries 455]
G Series [2710:G Series]A50 series [2720:A50 series]M50 series [2730:M50 series]S50 Series [2740:S50 Series]
CRT Essential [2750:CRT Essential]CRT Performance [2760:CRT Performance]LCD Essential [2770:LCD Essential]
LCD Performance [2780:LCD Performance]xSeries 382 [2790:]BladeCenter HS40 [2800:]eServer 325 [2810:]xSeries 236 [2820:]
xSeries 336 [2830:]xSeries 346 [2840:]xSeries 306 [2850:]xSeries 206 [2860:]xSeries 226 [2870:]BladeCenter JS20 [2880:]
Unknown Series [2890:]
*/
/*
PROCMFR Processor Manufacturer U -- [0010:--]AMD [0020:AMD]Cyrix MMX [0030:Cyrix MMX]
Cyrix/IMD [0040:Cyrix/IMD]IBM [0050:IBM]Intel [0060:Intel]MIPS [0070:MIPS]Motorola [0080:Motorola]
SPARC [0090:SPARC]National Semi Conductor [0100:National Semi Conductor]Transmeta [0110:Transmeta]
*/
/*
CDROPTITYPE Optical Drive Type U CD-ROM [0010:CD-ROM]
CD-RW [0020:CD-RW]DVD [0030:DVD]DVD-RAM [0040:DVD-RAM]DVD-ROM [0050:DVD-ROM]
CD-RW/DVD-ROM Combination [0060:CD-RW/DVD-ROM Combination]CD-RW/DVD-ROM Combo [0070:CD-RW/DVD-ROM Combo]
CD-RW/DVD-ROM Combo II [0080:CD-RW/DVD-ROM Combo II]CD-RW/DVD-ROM Combo III [0090:CD-RW/DVD-ROM Combo III]
CD-RW/DVD-ROM Combo IV [0100:CD-RW/DVD-ROM Combo IV]CD-RW/DVD-ROM Combo V [0110:]DVD Recordable [0120:]
*/
