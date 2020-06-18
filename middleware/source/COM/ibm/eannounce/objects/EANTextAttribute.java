//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANTextAttribute.java,v $
// Revision 1.144  2014/02/28 21:16:37  wendy
// RCQ242344 Translation support fallback
//
// Revision 1.143  2011/10/05 00:09:16  wendy
// correct rollback and restrictions
//
// Revision 1.142  2010/07/12 21:00:28  wendy
// BH SR87, SR655 - extended combounique rule
//
// Revision 1.141  2010/06/11 19:43:16  wendy
// add attr description for combouniqueoptional
//
// Revision 1.140  2007/02/27 04:05:28  tony
// Updatedefault on text attribute
//
// Revision 1.139  2006/04/28 22:02:01  roger
// Fix
//
// Revision 1.138  2006/04/28 21:51:59  roger
// New method for migration
//
// Revision 1.137  2006/04/26 21:29:12  tony
// MN27700077
// added to the array of available languages.
// changed to 125 possible languages.
//
// Revision 1.136  2006/03/01 07:35:23  dave
// formating and fixing array exception
//
// Revision 1.135  2006/02/06 21:28:28  gregg
// CR4823: setting m_strComboUniqueGrouping on attributes for later rules processing.
//
// Revision 1.134  2005/02/28 19:43:53  dave
// more Jtest fixes from over the weekend
//
// Revision 1.133  2005/02/01 00:42:37  dave
// null pointer issur
//
// Revision 1.132  2005/02/01 00:14:57  dave
// more syntax
//
// Revision 1.131  2005/02/01 00:06:24  dave
// Syntax issues
//
// Revision 1.130  2005/01/31 23:56:42  dave
// general rule to convert text to uppercase if isUpper is true
//
// Revision 1.129  2005/01/07 18:57:39  tony
// fixed null pointer
//
// Revision 1.128  2004/11/16 22:56:41  gregg
// some fixes found while looking at EntityList Edit constructor
//
// Revision 1.127  2004/10/25 23:56:09  dave
// tax on sin
//
// Revision 1.126  2004/10/25 23:51:22  dave
// more attempted reuse
//
// Revision 1.125  2004/10/21 16:49:54  dave
// trying to share compartor
//
// Revision 1.124  2004/08/04 00:02:49  gregg
// more combouniqueoptinoal
//
// Revision 1.123  2004/08/03 23:20:05  gregg
// trigger ComboUniqueOptional rule check in update even when att2 is empty/null.  hence the optional.
//
// Revision 1.122  2004/08/03 18:24:57  gregg
// isComboUniqueOptionalRequiredAttribute
//
// Revision 1.121  2004/08/02 21:16:25  gregg
// combo unique optional
//
// Revision 1.120  2004/05/07 19:37:01  gregg
// more derived EID for new Entities
//
// Revision 1.119  2004/04/21 19:11:20  gregg
// let's not addToStack if we're a derived attribute
//
// Revision 1.118  2004/04/20 19:52:53  joan
// work on duplicate
//
// Revision 1.117  2004/03/31 18:40:52  gregg
// justa comment
//
// Revision 1.116  2004/03/31 17:46:13  gregg
// going for derived entityid attribute
//
// Revision 1.115  2004/03/03 22:06:15  gregg
// backed out previous changes
//
// Revision 1.114  2004/03/03 21:40:36  gregg
// in putPDHData: if null value is put --> remove whatever's there
//
// Revision 1.113  2004/01/13 20:03:36  dave
// syntax
//
// Revision 1.112  2004/01/13 19:54:41  dave
// space Saver Phase II  m_hsh1 and m_hsh2 created
// as needed instead of always there
//
// Revision 1.111  2003/12/03 21:38:58  dave
// more deferred locking
//
// Revision 1.110  2003/11/21 21:53:04  dave
// deferred restriction refresh
//
// Revision 1.109  2003/10/01 21:46:30  dave
// containsNLS() method
//
// Revision 1.108  2003/08/21 18:57:20  dave
// update suyntex
//
// Revision 1.107  2003/08/21 18:31:35  dave
// Adding now to profile when created, and when new
// instance is spawned
//
// Revision 1.106  2003/08/14 21:09:44  dave
// syntax  fixes for combo unique
//
// Revision 1.105  2003/08/14 20:54:56  dave
// fixing unique part number check
//
// Revision 1.104  2003/07/02 20:58:32  dave
// front end combo checker
//
// Revision 1.103  2003/07/02 20:36:33  dave
// both way check
//
// Revision 1.102  2003/06/27 20:20:10  dave
// only check rules for text if we are not in search mode
//
// Revision 1.101  2003/06/26 23:52:33  dave
// adding the abstract stuff
//
// Revision 1.100  2003/05/16 21:42:40  dave
// if :xxx cannot be found.. we put the orig string in there
//
// Revision 1.99  2003/05/13 22:21:44  joan
// fix report
//
// Revision 1.98  2003/05/07 16:50:30  dave
// do not refresh defaults when we turn off a field
//
// Revision 1.97  2003/04/24 20:10:12  dave
// trying to *not* post deactivated records that are new
// for text
//
// Revision 1.96  2003/04/24 19:02:08  dave
// syntax fix
//
// Revision 1.95  2003/04/24 18:32:16  dave
// getting rid of traces and system out printlns
//
// Revision 1.94  2003/04/23 18:36:50  dave
// spec changes and syntax
//
// Revision 1.93  2003/04/21 21:27:01  dave
// operator mismatch
//
// Revision 1.92  2003/04/21 21:12:54  dave
// fixing decimal zero rule.. and attributecode
//
// Revision 1.91  2003/04/17 17:06:55  bala
// remove double declaration of entitylist
//
// Revision 1.90  2003/04/17 17:06:08  bala
// Allow refreshDefaults to perform the rest of the code
// instead of returning when null entitylist or entitygroup
// encountered
//
// Revision 1.89  2003/04/17 16:46:32  bala
// Check for null values of EntityGroup and EntityItem in the refreshdefaults method
//
// Revision 1.88  2003/04/14 20:57:29  dave
// fixing the updateUSEnglishOnly meta rule
//
// Revision 1.87  2003/03/14 22:23:47  dave
// print statements
//
// Revision 1.86  2003/03/14 22:07:16  dave
// more importing
//
// Revision 1.85  2003/03/14 22:05:48  dave
// import need
//
// Revision 1.84  2003/03/14 21:10:57  dave
// syntax fix
//
// Revision 1.83  2003/03/14 20:31:32  dave
// setting block size to 2500
//
// Revision 1.82  2003/01/20 18:21:42  joan
// debug save WG default
//
// Revision 1.81  2003/01/09 19:51:57  dave
// change protected to public for select methods for more robust
// api to internal developers
//
// Revision 1.80  2002/11/14 00:25:59  dave
// trace statements
//
// Revision 1.79  2002/11/13 23:13:06  dave
// making refreshDefaults consitient on deactivate
//
// Revision 1.78  2002/11/12 01:46:53  dave
// more trace
//
// Revision 1.77  2002/11/12 01:37:07  dave
// more trace
//
// Revision 1.76  2002/11/12 01:28:50  dave
// more trace
//
// Revision 1.75  2002/11/06 20:58:38  joan
// debug null pointer
//
// Revision 1.74  2002/11/06 00:39:33  dave
// working w/ refreshrestrictions in more places
//
// Revision 1.73  2002/11/04 18:43:47  dave
// closing out refreshDefaults when in search mode
//
// Revision 1.72  2002/11/04 18:10:38  dave
// Variable override
//
// Revision 1.71  2002/11/04 18:00:19  dave
// fixes to put method
//
// Revision 1.70  2002/11/04 17:48:09  dave
// fixing 22756 (removing required and default value checks
// if you are isUsedInSearch is true)
//
// Revision 1.69  2002/10/31 23:20:08  dave
// bad fix in put
//
// Revision 1.68  2002/10/31 23:05:55  dave
// fix for null put, and deactivate
//
// Revision 1.67  2002/10/30 21:07:58  dave
// move classCast up and dumped out they class that was passed
// when there is a mismatch
//
// Revision 1.66  2002/10/30 20:40:08  joan
// check classCast in put method
//
// Revision 1.65  2002/10/16 21:44:59  dave
// bad patch to say if the entity is deactivated and the string you
// are setting it to must be length zero in order to fail a required
// check.. otherwise it is on its way to being set
//
// Revision 1.64  2002/10/10 23:03:38  dave
// do not check for required - until you save it
//
// Revision 1.63  2002/08/20 21:20:26  joan
// debug
//
// Revision 1.62  2002/08/15 22:43:13  bala
// add Debug statement
//
// Revision 1.61  2002/07/18 22:28:42  dave
// minor SP adjustments
//
// Revision 1.60  2002/06/19 17:46:21  joan
// add logic to auto generate for attribute NOW
//
// Revision 1.59  2002/05/24 23:11:41  joan
// fix get strNow
//
// Revision 1.58  2002/05/24 22:27:58  joan
// fix errors
//
// Revision 1.57  2002/05/24 21:55:09  joan
// working on generating ID
//
// Revision 1.56  2002/05/24 21:37:02  joan
// add auto generating id in refreshDefaults
//
// Revision 1.55  2002/05/16 20:19:49  joan
// remove system.out
//
// Revision 1.54  2002/05/16 18:49:34  joan
// fix 19979
//
// Revision 1.53  2002/05/16 18:26:09  joan
// fixing 19979
//
// Revision 1.52  2002/05/16 17:24:20  joan
// debug 19979
//
// Revision 1.51  2002/05/16 16:43:22  joan
// debug fb 19979
//
// Revision 1.50  2002/05/16 00:34:55  dave
// fix to rollback on EANTextAttribute
//
// Revision 1.49  2002/05/15 23:28:44  dave
// fix to allow deactivates to go onto the change stack
//
// Revision 1.48  2002/05/15 18:36:17  dave
// attempted fix at required
//
// Revision 1.47  2002/04/26 23:17:29  joan
// debug
//
// Revision 1.46  2002/04/26 22:35:04  joan
// debug
//
// Revision 1.45  2002/04/26 22:22:27  joan
// debug
//
// Revision 1.44  2002/04/19 23:06:34  dave
// found the infinite loop
//
// Revision 1.43  2002/04/19 23:03:12  dave
// why are we refreshingdefaults on a get
//
// Revision 1.42  2002/04/19 23:00:03  dave
// tracing enabled for infinite loop in put
//
// Revision 1.41  2002/04/18 23:35:11  dave
// ignore same value puts
//
// Revision 1.40  2002/04/17 20:17:05  dave
// new XMLAttribute and its MetaPartner
//
// Revision 1.39  2002/04/15 20:40:37  joan
// fixing errors
//
// Revision 1.38  2002/04/15 18:35:28  joan
// work on exception
//
// Revision 1.37  2002/04/15 17:43:06  joan
// catch exception
//
// Revision 1.36  2002/04/15 17:28:47  joan
// syntax
//
// Revision 1.35  2002/04/15 17:18:27  joan
// catch exception
//
// Revision 1.34  2002/04/15 16:31:13  joan
// fixing exception
//
// Revision 1.33  2002/04/12 21:27:51  dave
// syntax engish to english
//
// Revision 1.32  2002/04/12 21:18:02  dave
// introduced english only concept in EANMetaAttribute
//
// Revision 1.31  2002/04/04 18:32:16  dave
// added more refreshReset stuff
//
// Revision 1.30  2002/04/03 01:08:18  dave
// more deactivate and default values
//
// Revision 1.29  2002/04/02 23:58:29  dave
// trace statements
//
// Revision 1.28  2002/04/02 19:30:35  dave
// extra EntityItem
//
// Revision 1.27  2002/04/02 19:21:58  dave
// Syntax
//
// Revision 1.26  2002/04/02 19:12:37  dave
// more isRequiredStuff
//
// Revision 1.25  2002/03/29 22:35:44  dave
// first attempt at deactivate.. and getting all flags in for the Flag Put
//
// Revision 1.24  2002/03/26 20:58:11  dave
// adding commit local logic so we have cornered updates
//
// Revision 1.23  2002/03/25 19:50:49  dave
// more update traces
//
// Revision 1.22  2002/03/25 17:56:48  dave
// more syntax
//
// Revision 1.21  2002/03/25 17:46:33  dave
// fix the import statement
//
// Revision 1.20  2002/03/25 17:30:37  dave
// added approp. import statement
//
// Revision 1.19  2002/03/25 17:19:01  dave
// added control block when generating entity update
//
// Revision 1.18  2002/03/23 02:15:09  dave
// syntax
//
// Revision 1.17  2002/03/23 02:05:59  dave
// more syntax fix
//
// Revision 1.16  2002/03/23 01:08:26  dave
// first attempt at update
//
// Revision 1.15  2002/03/21 22:43:40  dave
// removing some displays
//
// Revision 1.14  2002/03/21 21:48:29  dave
// trace statements
//
// Revision 1.13  2002/03/20 22:53:10  dave
// syntax cleanup
//
// Revision 1.12  2002/03/20 22:41:02  dave
// Syntax work
//
// Revision 1.11  2002/03/20 21:57:51  dave
// syntax error fixes
//
// Revision 1.10  2002/03/20 21:21:11  dave
// syntax fixes, and rollback on the attribute
//
// Revision 1.9  2002/03/19 19:40:47  dave
// make sure we refresh defaults after every put. to
// ensure default values are not left blank
//
// Revision 1.8  2002/03/19 17:40:46  dave
// syntax
//
// Revision 1.7  2002/03/19 17:32:27  dave
// name fix
//
// Revision 1.6  2002/03/19 03:47:33  dave
// first attempt a setting defaults
//
// Revision 1.5  2002/03/12 18:33:08  dave
// clean up on EANAddressable - removed the int indexes
// because they make no sense.
// Added standard put /get methods to the EANAttibute
//
// Revision 1.4  2002/02/12 18:21:35  dave
// more dump rationale
//
// Revision 1.3  2002/02/12 18:10:22  dave
// more changes
//
// Revision 1.2  2002/02/11 07:35:08  dave
// adding data side
//
// Revision 1.1  2002/02/11 07:23:08  dave
// new objects to commit
//
// Revision 1.2  2002/02/05 16:39:13  dave
// more expansion of abstract model
//
// Revision 1.1  2002/02/04 18:02:46  dave
// added new Abstract EANAttribute
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.objects.Attribute;
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.opicmpdh.objects.Text;
import COM.ibm.opicmpdh.objects.LongText;
import COM.ibm.opicmpdh.objects.ControlBlock;

import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class EANTextAttribute extends EANAttribute {

    /**
    *@serial
    */
    final static long serialVersionUID = 1L;
    private final static String[] NLS_MAP = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                                   "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                                   "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
                                   "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
                                   "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
                                   "50", "51", "52", "53", "54", "55", "56", "57", "58", "59",
                                   "60", "61", "62", "63", "64", "65", "66", "67", "68", "69",
                                   "70", "71", "72", "73", "74", "75", "76", "77", "78", "79",
                                   "80", "81", "82", "83", "84", "85", "86", "87", "88", "89",
                                   "90", "91", "92", "93", "94", "95", "96", "97", "98", "99",
                                   "100", "101", "102", "103", "104", "105", "106", "107", "108", "109",
                                   "110", "111", "112", "113", "114", "115", "116", "117", "118", "119",
                                   "120", "121", "122", "123", "124", "125"};

    /**
    *  Gets the version attribute of the EANTextAttribute object
    *
    *@return    The version value
    */
    public String getVersion() {
        return "$Id: EANTextAttribute.java,v 1.144 2014/02/28 21:16:37 wendy Exp $";
    }

    /**
    *  Main method which performs a simple test of this class
    *
    *@param  arg  Description of the Parameter
    */
    public static void main(String arg[]) {
    }

    /**
    *  Manages EANTextAttributes in the e-announce data model
    *
    *@param  _edf                            Description of the Parameter
    *@param  _prof                           Description of the Parameter
    *@param  _mta                            Description of the Parameter
    *@exception  MiddlewareRequestException  Description of the Exception
    */
    public EANTextAttribute(EANDataFoundation _edf, Profile _prof, EANMetaTextAttribute _mta) throws MiddlewareRequestException {
        super(_edf, _prof, _mta);
    }

    /**
    *  Puts the PDHData into the PDH storage structure of this object
    *
    *@param  _iNLS      Description of the Parameter
    *@param  _strValue  Description of the Parameter
    */
    public void putPDHData(int _iNLS, String _strValue) {
        if (m_hsh1 == null) {
            m_hsh1 = new Hashtable();
        }
        //
        // There seems to be some bogus NSID's out there
        //
        if (_iNLS < NLS_MAP.length) {
            m_hsh1.put(NLS_MAP[_iNLS], _strValue);
        }
    }

    public void putLocalData(int _iNLS, String _strValue) {
        if (m_hsh2 == null) {
            m_hsh2 = new Hashtable();
        }
        //
        // There seems to be some bogus NSID's out there
        //
        if (_iNLS < NLS_MAP.length) {
            m_hsh2.put(NLS_MAP[_iNLS], _strValue);
        }
        EntityItem ei = getEntityItem();
        ei.addToStack(this);
    }

    /**
    *  Puts Local Data back to this attribute, NLSID is always infered from
    *  attached or derived profile. Here is where all the rules get fired...
    *
    *@param  _strValue                     Description of the Parameter
    *@exception  EANBusinessRuleException  Description of the Exception
    */
    public void put(Object _strValue) throws EANBusinessRuleException {

        //
        // Lets set up our local variables here
        // just like we do for procedural language
        //
        String strValue = null;
        int iNLSID = 1;

        EANMetaAttribute ma = getMetaAttribute();
        EntityItem ei = getEntityItem();

        // Check to make sure this is the right instanceof
        if (_strValue != null && !(_strValue instanceof String)) {
            System.out.println("EANTextAttribute.put() wrong class for passed object _strValue: " + (_strValue == null ? " is null" : _strValue.getClass().getName()));
            return;
        }

        iNLSID = (ma.isUSEnglishOnly() ? 1 : getNLSID());
        strValue = (String) _strValue;

        if (strValue == null || (strValue != null && strValue.length() == 0)) {
            // Only deactivate if already active
            if (isActive()) {
                // We are out of active mode ..
                // EANUtility.validate(this, null);
                // DWB lets not deactive this yet ..
                // We will catch requirds on the save
                //need to put something in m_hsh2. Otherwise, if there's no
                //default values, the attribute cann't get deactivated.
                if (toString().length() > 0) {
                    if (m_hsh2 == null) {
                        m_hsh2 = new Hashtable();
                    }
                    m_hsh2.put(NLS_MAP[iNLSID], toString());
                }
                setActive(false);

                // Add this to the stack if it is not new..
                // else if they are sending a null.. and its new..
                // simply remove from the Stack
                if (ei != null && !ei.isNew() && !ma.isDerived()) {
                    ei.addToStack(this);
                } else if (ei != null && ei.isNew()) {
                    ei.removeFromStack(this);
                }
            }
            return;
        }

        // If we are asking for today.. please put it in
        if (strValue.toUpperCase().equals(":NOW")) {
            SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
            String strToday = sdfDay.format(new Date());
            strValue = strToday;
        }

        // if we have an UPPER rule.. let convert to upper case..
        if (ma.isUpper()) {
            strValue = strValue.toUpperCase();
        }

        //
        // If we have a ":" in this answer.. we should be able to grab
        // the value from another attribute code.
        //
        if (strValue.indexOf(":") == 0) {
            EANAttribute att = ei.getAttribute(strValue.substring(1));
            strValue = (att == null ? strValue : att.toString());
        }

        // Check to see if it is used in a Search if so.. do not validate
        if (!ei.isUsedInSearch()) {
            EANUtility.validate(this, strValue);
        }

        if (toString().equals(strValue)) {
            return;
        }

        // this is to prevent putting an empty string into m_hsh2
        if (strValue.length() > 0) {
            setActive(true);
            if (m_hsh2 == null) {
                m_hsh2 = new Hashtable();
            }
            m_hsh2.put(NLS_MAP[iNLSID], strValue);
        } else {
            if (toString().length() > 0) {
                if (m_hsh2 == null) {
                    m_hsh2 = new Hashtable();
                }
                m_hsh2.put(NLS_MAP[iNLSID], toString());
            }
            setActive(false);
        }

        if (ei != null && !ma.isDerived()) {
            ei.addToStack(this);
        }
        refreshDefaults();
        if (ei != null) {
            ei.refreshRequired();
            ei.refreshRestrictions(false);
            ei.refreshResets();
            if (ei.isVEEdit()) {
            	ei.refreshDefaults();
            }
        }
    }

    /**
    *  Description of the Method
    *
    *@return    Description of the Return Value
    */
    public String toString() {

        EANMetaAttribute ma = null;
        String strAnswer = null;
        int iNLSID = 0;

        if (!isActive()) {
            return "";
        }

        ma = getMetaAttribute();

        if (ma != null) {
            if (ma.isDerived()) {
                if (ma.getAttributeCode().equals("DERIVED_EID")) {
                    if (getEntityItem().isNew()) {
                        return "Unassigned";
                    }
                    return String.valueOf(getEntityItem().getEntityID());
                }
            }
        }

        iNLSID = getNLSID();
        if (m_hsh2 != null) {
            strAnswer = (String) m_hsh2.get(NLS_MAP[iNLSID]);
        }
        if (strAnswer == null && m_hsh1 != null) {
            strAnswer = (String) m_hsh1.get(NLS_MAP[iNLSID]);
        }
        if (strAnswer == null && m_hsh2 != null) {
            strAnswer = (String) m_hsh2.get(NLS_MAP[1]);
        }
        if (strAnswer == null && m_hsh1 != null) {
            strAnswer = (String) m_hsh1.get(NLS_MAP[1]);
        }
        if (strAnswer == null) {
            strAnswer = "";
        }
        return strAnswer;
    }

    /**
    *  Description of the Method
    *
    *@param  _bBrief  Description of the Parameter
    *@return          Description of the Return Value
    */
    public String dump(boolean _bBrief) {
        return "EANTextAttribute:" + getKey() + ":" + (getMetaAttribute() == null ? "ma is null" : getMetaAttribute().toString()) + ":" + toString();
    }

    /*
    *  If there is no value.. and there is a default.. we need to apply it here..
    */
    /**
    *  Description of the Method
    *
    *@return    Description of the Return Value
    */
    public Object get() {
        // refreshDefaults(); DWB
        return toString();
    }

    /**
    *  Description of the Method
    */
    public void refreshDefaults() {

        EntityItem ei = getEntityItem();
        EANMetaAttribute ma = getMetaAttribute();

        // No default items
        // when used in search
        if (ei.isUsedInSearch()) {
            return;
        }

        // First check to see if any default values exist in the
        // context of an EntityGroup/WorkGroup
        if (toString().equals("") || !isActive()) {
            EntityGroup eg = null;
            EntityList elst = null;
            EntityItem eidef = null;

            eg = (EntityGroup) ei.getParent();
            if (eg != null) {
                elst = (EntityList) eg.getParent();
            }

            if (elst != null) {
                eidef = elst.getDefaultEntityItem(eg.getEntityType(), getProfile().getDefaultIndex());
            }

            if (eidef != null) {
                EANAttribute attDef = eidef.getAttribute(getKey());
                if (attDef != null) {
                    try {
                        put(attDef.get());
                    } catch (EANBusinessRuleException bre) {
                        bre.printStackTrace();
                    }
                    return;
                }
            }

            // O.K.  If we have made it this far.. we must rely on defaults

            if (ma.hasDefaultValue()) {
                try {
                    put(ma.getDefaultValue());
                } catch (EANBusinessRuleException bre) {
                    bre.printStackTrace();
                }
            }

            // auto generate for attribute ID
            if ((ma.getAttributeType().equals("I") || ma.getAttributeType().equals("T")) && getAttributeCode().equals("ID")) {
                Profile p = getProfile();
                if (p != null) {
                    int iOPWGID = p.getOPWGID();
                    String strNow = p.getNow();
                    try {
                        put(iOPWGID + "--" + strNow);
                    } catch (EANBusinessRuleException bre) {
                        bre.printStackTrace();
                    }
                }
            }

            // auto generate for attribute NOW
            if (getAttributeCode().equals("NOW")) {
                Profile p = getProfile();
                if (p != null) {
                    String strNow = p.getValOn();
                    try {
                        put(strNow);
                    } catch (EANBusinessRuleException bre) {
                        bre.printStackTrace();
                    }
                }
            }
        }
    }

    /**
    *  Description of the Method
    */
    public void rollback() {

        EntityItem ei = (EntityItem) getParent();
        EANMetaAttribute ma = getMetaAttribute();

        // If not active reactivate it
        if (!isActive()) {
            setActive(true);
        }

        resetHash2();
        
        ei.removeFromStack(this);
        ei.refreshRestrictions();//false); need to completely reset the restrictions or a previous entity might have been used
        ei.refreshRequired();
        if (ma != null && ma.isClassified()) {
            ei.refreshClassifications();
        }
        refreshDefaults();

    }

    /**
    *  Description of the Method
    *
    *@return    Description of the Return Value
    */
    protected boolean checkBusinessRules() {
        return true;
    }

    /**
    *  Description of the Method
    *
    *@return    Description of the Return Value
    */
    protected Vector generateUpdateAttribute() {
        // Only package a vector if the business rules have been met
        Vector vctReturn = new Vector();
        EANMetaAttribute ma = getMetaAttribute();
        EntityItem ei = (EntityItem) getParent();
        Iterator it = null;

        if (!checkBusinessRules()) {
            return null;
        }

        // We do not need control block yet

        if (m_hsh2 == null) {
            return vctReturn;
        }

        ControlBlock cbOn = new ControlBlock(getEffFrom(), getEffTo(), getEffFrom(), getEffTo(), getProfile().getOPWGID());
        ControlBlock cbOff = new ControlBlock(getEffFrom(), getEffFrom(), getEffFrom(), getEffFrom(), getProfile().getOPWGID());

        //RCQ242344 Translation support fallback
        boolean expireOtherNls = false;
        if(ma.isExpireOtherNLS()){
        	// check if nlsid 1 was updated
            Iterator it2 = m_hsh2.keySet().iterator();
            while (it2.hasNext()) {
                String strNLS = (String) it2.next();
                int iNLSID = Integer.valueOf(strNLS).intValue();
                if (1 == iNLSID) {
                	expireOtherNls = true;
                	break;
                }
            }
        }
        if(!expireOtherNls){
        	it = m_hsh2.keySet().iterator();
        	while (it.hasNext()) {
        		String strNLS = (String) it.next();
        		int iNLSID = Integer.valueOf(strNLS).intValue();
        		String strValue = (String) m_hsh2.get(strNLS);
        		if (this instanceof TextAttribute) {
        			Text text1 = new Text(getProfile().getEnterprise(), ei.getEntityType(), ei.getEntityID(), getAttributeCode(), strValue, iNLSID, (isActive() ? cbOn : cbOff));
        			vctReturn.addElement(text1);

        			updateUniquenessAttrs(ei, text1, ma, isActive(),null);  // BH SR87, SR655

        		} else if (this instanceof LongTextAttribute || this instanceof XMLAttribute) {
        			LongText lt1 = new LongText(getProfile().getEnterprise(), ei.getEntityType(), ei.getEntityID(), getAttributeCode(), strValue, iNLSID, (isActive() ? cbOn : cbOff));
        			vctReturn.addElement(lt1);
        		}
        	}
        }else{
        	// expire all nls id other than nlsid=1 and set the specified flag
        	//look thru m_hsh1 - the pdh values and deactivate all but nlsid==1
        	if(m_hsh1!=null){ // cant happen, but just in case
        		it = m_hsh1.keySet().iterator();
        		while (it.hasNext()) {
        			String strNLS = (String) it.next();
        			int iNLSID = Integer.valueOf(strNLS).intValue();
        			if(1==iNLSID){
        				continue;
        			}

        			// deactivate all others
        			String strValue = (String) m_hsh1.get(strNLS);
        			if (this instanceof TextAttribute) {
        				Text text1 = new Text(getProfile().getEnterprise(), ei.getEntityType(), ei.getEntityID(), getAttributeCode(), strValue, iNLSID,cbOff);
        				vctReturn.addElement(text1);
        				updateUniquenessAttrs(ei, text1, ma, false,null);  // BH SR87, SR655
        			} else if (this instanceof LongTextAttribute || this instanceof XMLAttribute) {
        				LongText lt1 = new LongText(getProfile().getEnterprise(), ei.getEntityType(), ei.getEntityID(), getAttributeCode(), strValue, iNLSID, cbOff);
        				vctReturn.addElement(lt1);
        			}
        			m_hsh2.remove(strNLS); // remove it if it existed
        		}
        	}
        	// now go thru and do the nlsid 1 update
        	it = m_hsh2.keySet().iterator();
        	while (it.hasNext()) {
        		String strNLS = (String) it.next();
        		int iNLSID = Integer.valueOf(strNLS).intValue();
        		String strValue = (String) m_hsh2.get(strNLS);

        		if (this instanceof TextAttribute) {
        			Text text1 = new Text(getProfile().getEnterprise(), ei.getEntityType(), ei.getEntityID(), getAttributeCode(), strValue, iNLSID, (isActive() ? cbOn : cbOff));
        			vctReturn.addElement(text1);
        			updateUniquenessAttrs(ei, text1, ma, isActive(),null);  // BH SR87, SR655
        		} else if (this instanceof LongTextAttribute || this instanceof XMLAttribute) {
        			LongText lt1 = new LongText(getProfile().getEnterprise(), ei.getEntityType(), ei.getEntityID(), getAttributeCode(), strValue, iNLSID, (isActive() ? cbOn : cbOff));
        			vctReturn.addElement(lt1);
        		}
        	}
        	//update the flag attribute if it is specified and active
        	String flagattrval = ma.getExpireOtherNLSAttr(); 
        	if(isActive() && flagattrval!=null){ // only do this if active
        		int colonid = flagattrval.indexOf(':');
        		if (colonid != -1){
        			String attr = flagattrval.substring(0,colonid);
        			String strFlagCode = flagattrval.substring(colonid+1);
                    SingleFlag sf = new SingleFlag(getProfile().getEnterprise(), ei.getEntityType(), ei.getEntityID(), attr, strFlagCode, 1, cbOn);
                    //sf.setDeferredPost(true); 
                    vctReturn.addElement(sf);
        		}
        	}
        }

        return vctReturn;
    }

    /**
     * set any uniqueness checks for this attribute
     *  BH SR87, SR655 - extended combounique rule
     * @param ei
     * @param text1
     * @param ma
     * @param isActive
     * @param attrValueTbl - used by PDGUtility
     */
    public static void updateUniquenessAttrs(EntityItem ei, Text text1, EANMetaAttribute ma, 
    		boolean isActive, Hashtable attrValueTbl)
    {
        text1.m_strLongDescription = ma.getLongDescription();
        if (ma.isUnique()) {
            text1.m_bUnique = true;
            text1.m_strUniqueClass = ma.getUniqueClass();
            text1.m_strUniqueType = ma.getUniqueType();
        }
        if (ma.isComboUnique() && isActive) {
           /*orig for (int i = 0; i < ma.getComboUniqueAttributeCode().size(); i++) {
                String strAttCode = ma.getComboUniqueAttributeCode(i);
                // We need to check this only if there is a value in the sister attribute
                EANAttribute att = ei.getAttribute(strAttCode);
                if (att != null && att.isActive()) {
                    text1.m_bComboUnique = true;
                    text1.m_strComboAttributeCode = strAttCode;
                    text1.m_strComboAttributeDesc = att.getMetaAttribute().getLongDescription();
                    text1.m_strComboUniqueGrouping = att.getMetaAttribute().getComboUniqueGrouping();
                    if (att instanceof EANFlagAttribute) {
                        EANFlagAttribute fa = (EANFlagAttribute) att;
                        text1.m_strComboAttributeValue = fa.getFlagCodes();
                    } else {
                        text1.m_strComboAttributeValue = att.toString();
                    }
                }
            }*/
        	// extended support for combounique BH SR87, SR655
        	int cnt=0;
        	StringBuffer sbAttrCode = new StringBuffer();
        	StringBuffer sbAttrValue = new StringBuffer();
        	StringBuffer sbAttrDesc = new StringBuffer();
        	for (int i = 0; i < ma.getComboUniqueAttributeCode().size(); i++) {
        		String strAttCode = ma.getComboUniqueAttributeCode(i);
           		// We need to check this only if there is a value in all of the sister attributes
        		EANAttribute att = ei.getAttribute(strAttCode);
        		
        	 	//PDGUtility may have value in hashtable
        	 	if(attrValueTbl!=null){
        	 	   String str = (String) attrValueTbl.get(ei.getEntityType() + ":" + strAttCode);
                   if (str != null && str.length() > 0) { // use this value for the combo
                       StringTokenizer st = new StringTokenizer(str, "=");
                       //String attcode = dont need this, same as strAttCode 
                       st.nextToken();
                       String attvalue=st.nextToken();
                       cnt++;

                       if(sbAttrCode.length()>0){
                           sbAttrCode.append(Attribute.UNIQUE_DELIMITER);
                           sbAttrValue.append(Attribute.UNIQUE_DELIMITER);
                           sbAttrDesc.append(Attribute.UNIQUE_DELIMITER);
                       }
                       sbAttrCode.append(strAttCode);
                       sbAttrValue.append(attvalue);
                       if(att!=null){
                           sbAttrDesc.append(att.getMetaAttribute().getLongDescription());
                       }else{
                           sbAttrDesc.append(attvalue);
                       }
                       continue; 
                   }
        	 	}        	 	
 
        		if (att != null && att.toString().length()>0 && att.isActive()) {
        			cnt++;

        			if(sbAttrCode.length()>0){
        				sbAttrCode.append(Attribute.UNIQUE_DELIMITER);
        				sbAttrValue.append(Attribute.UNIQUE_DELIMITER);
        				sbAttrDesc.append(Attribute.UNIQUE_DELIMITER);
        			}
        			sbAttrCode.append(strAttCode);
        			if (att instanceof EANFlagAttribute) {
        				EANFlagAttribute fa = (EANFlagAttribute) att;
        				sbAttrValue.append(fa.getFlagCodes());
        			} else {
        				sbAttrValue.append(att.toString());
        			}
        			sbAttrDesc.append(att.getMetaAttribute().getLongDescription());
        		}
        	}

        	if(cnt==ma.getComboUniqueAttributeCode().size()) {// there was a value for each attributecode
        	    text1.m_bComboUnique = true;
                text1.m_strComboAttributeCode = sbAttrCode.toString();
                text1.m_strComboAttributeDesc = sbAttrDesc.toString();
                text1.m_bComboUniqueOptRequiredAtt = false;
                text1.m_strComboUniqueGrouping = ma.getComboUniqueGrouping();// this is the entitytype
                text1.m_strComboAttributeValue = sbAttrValue.toString();
                text1.m_objReference = ei.getProfile().getRoleCode(); // needed to determine comboattr type
        	}
        } else if (ma.isComboUniqueOptional()) {  /*&&isActive*/
            // we dont care about isActive(??) or if there is more than one? this overwrites
            for (int i = 0; i < ma.getComboUniqueAttributeCode().size(); i++) {
                String strAttCode = ma.getComboUniqueAttributeCode(i);
                EANAttribute att = ei.getAttribute(strAttCode);
                String attvalue=null;
        	 	if(attrValueTbl!=null){
         	 	   String str = (String) attrValueTbl.get(ei.getEntityType() + ":" + strAttCode);
                    if (str != null && str.length() > 0) { // use this value for the combo
                        StringTokenizer st = new StringTokenizer(str, "=");
                        //String attcode = dont need this, same as strAttCode
                        st.nextToken();
                        attvalue=st.nextToken();
                    }
         	 	}    
                text1.m_bComboUniqueOptional = true;
                text1.m_strComboAttributeCode = strAttCode;
                text1.m_strComboAttributeDesc = att.getMetaAttribute().getLongDescription();
                text1.m_bComboUniqueOptRequiredAtt = ma.isComboUniqueOptionalRequiredAttribute();
                text1.m_strComboUniqueGrouping = ma.getComboUniqueGrouping();
                // We need to check this only if there is a value in the sister attribute
                if (attvalue==null && att != null && att.toString().length()>0 && att.isActive()) {
                	attvalue = att.toString();
                }
                if(attvalue!=null){
                	text1.m_strComboAttributeValue =attvalue;
                }
            }
        }    	
    }
    /**
    *  Description of the Method
    */
    public void commitLocal() {
        Iterator it = null;

        if (!isActive()) {
            resetHash1();
            resetHash2();
            return;
        }

        // Loop through and move things up from local to PDH structure
        if (m_hsh2 == null) {
            return;
        }
        it = m_hsh2.keySet().iterator();
        while (it.hasNext()) {
            String strNLS = (String) it.next();
            int iNLSID = Integer.valueOf(strNLS).intValue();
            String strValue = (String) m_hsh2.get(strNLS);
            putPDHData(iNLSID, strValue);
        }

        resetHash2();

    }

    /**
     * (non-Javadoc)
     * hasData
     *
     * @see COM.ibm.eannounce.objects.EANAttribute#hasData()
     */
    protected boolean hasData() {
        if (!isActive()) {
            return false;
        }
        if (m_hsh1 != null && m_hsh1.size() > 0) {
            return true;
        }
        if (m_hsh2 != null && m_hsh2.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * This guy will return true if information
     * for the given NLSID is contianed in the Text Data
     *
     * @return boolean
     * @param _inls
     */
    public boolean containsNLS(int _inls) {
        if (m_hsh1 != null) {
            Iterator it = m_hsh1.keySet().iterator();
            while (it.hasNext()) {
                String strNLS = (String) it.next();
                int iNLSID = Integer.valueOf(strNLS).intValue();
                if (_inls == iNLSID) {
                    return true;
                }
            }
        }
        if (m_hsh2 != null) {
            Iterator it = m_hsh2.keySet().iterator();
            while (it.hasNext()) {
                String strNLS = (String) it.next();
                int iNLSID = Integer.valueOf(strNLS).intValue();
                if (_inls == iNLSID) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * (non-Javadoc)
     * duplicate
     *
     * @see COM.ibm.eannounce.objects.EANAttribute#duplicate(java.lang.Object)
     */
    protected void duplicate(Object _strValue) {
        // Check to make sure this is the right instanceof

        String strValue = null;

        EANMetaAttribute ma = getMetaAttribute();
        int iNLSID = (ma.isUSEnglishOnly() ? 1 : getNLSID());

        if (_strValue != null && !(_strValue instanceof String)) {
            System.out.println("EANTextAttribute.duplicate() wrong class for passed object _strValue: " + (_strValue == null ? " is null" : _strValue.getClass().getName()));
            return;
        }

        strValue = (String) _strValue;
        if (strValue.length() > 0) {
            EntityItem ei = (EntityItem) getParent();
            setActive(true);
            if (m_hsh2 == null) {
                m_hsh2 = new Hashtable();
            }
            m_hsh2.put(NLS_MAP[iNLSID], strValue);
            if (ei != null) {
                ei.addToStack(this);
            }
        }
    }

}
