// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: EANFlagAttribute.java,v $
// Revision 1.258  2012/04/16 16:33:28  wendy
// fillcopy/fillpaste perf updates
//
// Revision 1.257  2011/10/05 00:08:46  wendy
// correct rollback and restrictions
//
// Revision 1.256  2010/11/08 18:53:43  wendy
// Replace EANList with Vector to reduce memory requirements
//
// Revision 1.255  2010/11/01 18:20:57  wendy
// Post ABRs after Status attributes
//
// Revision 1.254  2010/07/20 20:04:47  wendy
// MN43669195 - when a transition cleared a child flag, it was not getting saved in pdh
//
// Revision 1.253  2010/07/12 21:00:28  wendy
// BH SR87, SR655 - extended combounique rule
//
// Revision 1.252  2008/10/09 22:24:22  wendy
// CQ16205 for MN36915802 and MN36915859 handle overridden attributetype
//
// Revision 1.251  2008/02/01 22:10:06  wendy
// Cleanup RSA warnings
//
// Revision 1.250  2007/05/29 17:26:27  wendy
// RQ1103065049 support sets of dependent flags
//
// Revision 1.249  2006/05/02 19:36:33  roger
// Log level
//
// Revision 1.248  2006/05/02 17:35:58  roger
// Removed my displays
//
// Revision 1.247  2006/05/01 21:19:50  roger
// Trying to figure out reverse lookup
//
// Revision 1.246  2006/05/01 20:41:46  roger
// Doc fix
//
// Revision 1.245  2006/05/01 20:35:54  roger
// Doc
//
// Revision 1.244  2006/03/02 20:30:07  joan
// not runAutoSelect for search
//
// Revision 1.243  2006/02/20 21:39:45  joan
// clean up System.out.println
//
// Revision 1.242  2006/02/06 21:28:28  gregg
// CR4823: setting m_strComboUniqueGrouping on attributes for later rules processing.
//
// Revision 1.241  2006/01/12 23:14:18  joan
// work on CR0817052746
//
// Revision 1.240  2005/02/14 17:57:47  dave
// more Jtest
//
// Revision 1.239  2004/10/25 23:18:05  dave
// lets see what we got
//
// Revision 1.238  2004/10/21 23:37:26  dave
// trace and no sort if arry is one
//
// Revision 1.237  2004/10/21 16:49:53  dave
// trying to share compartor
//
// Revision 1.236  2004/10/20 23:29:43  dave
// oops minor change
//
// Revision 1.235  2004/10/20 23:24:26  dave
// new single instance Boolean concept
//
// Revision 1.234  2004/08/03 20:31:42  gregg
// ok - comboUniqueOptional is only for Text values fernow
//
// Revision 1.233  2004/08/03 18:24:56  gregg
// isComboUniqueOptionalRequiredAttribute
//
// Revision 1.232  2004/08/02 21:16:25  gregg
// combo unique optional
//
// Revision 1.231  2004/07/19 14:43:59  dave
// Changing log level
//
// Revision 1.230  2004/06/22 21:39:10  joan
// work on CR
//
// Revision 1.229  2004/04/20 19:52:53  joan
// work on duplicate
//
// Revision 1.228  2004/01/15 00:11:54  dave
// final sqeeze on meta flag
//
// Revision 1.227  2004/01/14 23:15:39  dave
// syntax
//
// Revision 1.226  2004/01/14 22:56:02  dave
// more trimming.. targeting reverse lookup function
//
// Revision 1.225  2004/01/13 22:41:11  dave
// more space squeezing
//
// Revision 1.224  2004/01/13 20:27:57  dave
// null pointer
//
// Revision 1.223  2004/01/13 20:10:24  dave
// paren problems
//
// Revision 1.222  2004/01/13 19:54:40  dave
// space Saver Phase II  m_hsh1 and m_hsh2 created
// as needed instead of always there
//
// Revision 1.221  2004/01/09 21:38:11  dave
// making Task Attributes deferred
//
// Revision 1.220  2003/12/03 21:38:58  dave
// more deferred locking
//
// Revision 1.219  2003/12/03 21:06:21  dave
// fix for the deferred restricted
//
// Revision 1.218  2003/11/21 21:53:04  dave
// deferred restriction refresh
//
// Revision 1.217  2003/10/30 02:10:00  dave
// more profile fixing
//
// Revision 1.216  2003/10/30 01:13:43  dave
// removing boos
//
// Revision 1.215  2003/10/29 02:08:37  dave
// remove trace
//
// Revision 1.214  2003/10/29 01:44:05  dave
// more trace
//
// Revision 1.213  2003/10/29 00:48:02  dave
// syntax
//
// Revision 1.212  2003/10/29 00:36:19  dave
// post drop changes for next wave
//
// Revision 1.211  2003/09/26 20:45:51  joan
// change to public
//
// Revision 1.210  2003/09/23 18:48:53  gregg
// check for nulls in runAutoSelects()
//
// Revision 1.209  2003/09/18 21:58:05  joan
// remove System.out
//
// Revision 1.208  2003/09/18 19:31:55  joan
// debug
//
// Revision 1.207  2003/09/18 18:21:44  joan
// add println to debug
//
// Revision 1.206  2003/09/15 18:49:32  dave
// syntax
//
// Revision 1.205  2003/09/15 18:34:06  dave
// misc fixes and usenglish only flag for flag support
//
// Revision 1.204  2003/09/10 19:12:26  gregg
// System.out cleanup
//
// Revision 1.203  2003/09/08 23:56:48  gregg
// runAutoSelects() in refreshDefaults() method (Fix for FB 52093).
//
// Revision 1.202  2003/08/14 20:54:55  dave
// fixing unique part number check
//
// Revision 1.201  2003/07/08 20:13:59  dave
// translation package changes II
//
// Revision 1.200  2003/07/02 20:58:32  dave
// front end combo checker
//
// Revision 1.199  2003/07/02 20:36:33  dave
// both way check
//
// Revision 1.198  2003/07/02 20:19:03  dave
// regioncheck removal
//
// Revision 1.197  2003/07/02 19:41:06  dave
// making changes for flag -> text combo check
//
// Revision 1.196  2003/06/30 22:50:52  joan
// move changes from v111
//
// Revision 1.195  2003/06/26 23:52:33  dave
// adding the abstract stuff
//
// Revision 1.194  2003/06/26 23:35:22  gregg
// check for isSelectHelper in put()
//
// Revision 1.193  2003/06/20 18:14:24  dave
// new pdg support changes
//
// Revision 1.192  2003/06/19 21:22:35  gregg
// new runAutoSelects logic
//
// Revision 1.191  2003/06/19 20:32:00  dave
// changes to ChangeHistoryItem
//
// Revision 1.190  2003/05/28 17:57:21  dave
// Trace Cleanup
//
// Revision 1.189  2003/05/20 18:31:15  dave
// streamlining abit to remove the looping
//
// Revision 1.188  2003/05/19 16:03:40  dave
// Clean up for descriptions, and exception flagging
//
// Revision 1.187  2003/05/13 19:38:03  gregg
// auto-UNselect
//
// Revision 1.186  2003/05/08 20:07:42  dave
// trace for Put on Null
//
// Revision 1.185  2003/05/08 17:59:22  dave
// tracking setDefauls for classificaitons...
//
// Revision 1.184  2003/05/07 16:50:30  dave
// do not refresh defaults when we turn off a field
//
// Revision 1.183  2003/05/02 17:24:56  dave
// unique EntiyType/AttributeCode/AttributeValue chcck
//
// Revision 1.182  2003/05/01 22:31:17  gregg
// phicks
//
// Revision 1.181  2003/05/01 22:29:28  gregg
// remove unecessary loop from runAutoSelects
//
// Revision 1.180  2003/05/01 22:15:49  gregg
// fix some old comments
//
// Revision 1.179  2003/05/01 20:38:26  gregg
// more autoselects
//
// Revision 1.178  2003/05/01 20:22:23  gregg
// runAutoSelect logic to union autoselects on multi-selected values
//
// Revision 1.177  2003/04/29 20:36:59  dave
// making getFirstActiveFlagCode public
//
// Revision 1.176  2003/04/28 23:18:52  gregg
// MetaFlag.runAutoSelects signature change
//
// Revision 1.175  2003/04/28 21:45:49  joan
// fix null pointer
//
// Revision 1.174  2003/04/28 20:53:05  gregg
// autoselect flags
//
// Revision 1.173  2003/04/24 19:03:23  dave
// more freshses of classifications
//
// Revision 1.172  2003/04/24 18:32:16  dave
// getting rid of traces and system out printlns
//
// Revision 1.171  2003/04/24 01:30:45  dave
// put to recalc isClassified if need be
//
// Revision 1.170  2003/04/24 01:13:05  dave
// isEditable trace cleanup
//
// Revision 1.169  2003/04/24 00:54:12  dave
// traces and selective recalculation on changing a flag value
//
// Revision 1.168  2003/04/21 19:13:43  bala
// Checking for NULL entityitem during put
//
// Revision 1.167  2003/04/17 17:28:46  bala
// allow refreshdefaults to run when null Entitygroup or
// EntityList encountered
//
// Revision 1.166  2003/03/25 21:26:47  dave
// making public the internal put
//
// Revision 1.165  2003/03/12 00:08:17  bala
// checking for null values of entity group and
// entity list in method refreshDefaults
//
// Revision 1.164  2003/01/29 20:37:30  dave
// exposting the m_hsh1 table
//
// Revision 1.163  2003/01/09 19:51:57  dave
// change protected to public for select methods for more robust
// api to internal developers
//
// Revision 1.162  2002/12/13 23:19:10  dave
// whoopsie..
//
// Revision 1.161  2002/12/13 22:57:10  dave
// more fixes to cyntgacx
//
// Revision 1.160  2002/12/13 22:35:14  dave
// syntax
//
// Revision 1.159  2002/12/06 18:22:32  joan
// debug Search
//
// Revision 1.158  2002/12/06 18:07:47  joan
// debug Search
//
// Revision 1.157  2002/12/05 22:15:26  dave
// Out Of Circulation Logic
//
// Revision 1.156  2002/11/14 22:01:06  dave
// deabled state maching in search context
//
// Revision 1.155  2002/11/14 00:25:59  dave
// trace statements
//
// Revision 1.154  2002/11/13 23:42:39  dave
// have to set it back to active on refreshDefaults
//
// Revision 1.153  2002/11/13 23:26:38  dave
// missing semi colon
//
// Revision 1.152  2002/11/13 23:13:06  dave
// making refreshDefaults consitient on deactivate
//
// Revision 1.151  2002/11/12 17:18:27  dave
// System.out.println clean up
//
// Revision 1.150  2002/11/12 01:46:53  dave
// more trace
//
// Revision 1.149  2002/11/12 01:37:07  dave
// more trace
//
// Revision 1.148  2002/11/12 01:15:08  dave
// trace statement
//
// Revision 1.147  2002/11/11 20:58:17  dave
// syntax ()
//
// Revision 1.146  2002/11/11 20:48:00  dave
// misc fixes
//
// Revision 1.145  2002/11/08 19:19:58  joan
// remove System.out
//
// Revision 1.144  2002/11/06 19:55:20  joan
// fix null pointer
//
// Revision 1.143  2002/11/06 18:13:16  joan
// debug null pointer
//
// Revision 1.142  2002/11/06 17:51:55  joan
// debug null pointer
//
// Revision 1.141  2002/11/06 17:43:22  joan
// debug null pointer
//
// Revision 1.140  2002/11/06 00:39:32  dave
// working w/ refreshrestrictions in more places
//
// Revision 1.139  2002/11/04 21:31:34  dave
// Trace Statements
//
// Revision 1.138  2002/11/04 19:54:49  dave
// backing out of the last search change
//
// Revision 1.137  2002/11/04 19:06:18  dave
// final default trap
//
// Revision 1.136  2002/11/04 18:43:46  dave
// closing out refreshDefaults when in search mode
//
// Revision 1.135  2002/10/30 20:40:08  joan
// check classCast in put method
//
// Revision 1.134  2002/10/22 17:50:04  dave
// misc fixes
//
// Revision 1.133  2002/10/14 19:45:37  dave
// Trapping a null pointer
//
// Revision 1.132  2002/10/09 16:49:19  dave
// fixing a fix to null pointer
//
// Revision 1.131  2002/09/11 17:23:24  dave
// making isSelected Public
//
// Revision 1.130  2002/08/09 23:20:19  joan
// null pointer
//
// Revision 1.129  2002/08/09 23:05:50  joan
// debug
//
// Revision 1.128  2002/08/09 23:00:11  joan
// debug
//
// Revision 1.127  2002/08/09 22:39:20  joan
// debug
//
// Revision 1.126  2002/04/18 20:12:24  dave
// putting back the refreshdefaults on the exposed put
//
// Revision 1.125  2002/04/18 19:17:41  dave
// syntax fixes
//
// Revision 1.124  2002/04/18 18:57:44  dave
// first attempt at fixing the set in StateTransition
//
// Revision 1.123  2002/04/17 23:55:06  dave
// fix
//
// Revision 1.122  2002/04/17 23:45:13  dave
// syntax
//
// Revision 1.121  2002/04/17 23:09:41  dave
// finishing touches on the put for EANFlagAttributes
//
// Revision 1.120  2002/04/17 22:41:34  dave
// syntax
//
// Revision 1.119  2002/04/17 22:33:19  dave
// syntax on System.print...
//
// Revision 1.118  2002/04/17 22:23:15  dave
// trace for controlled flag put
//
// Revision 1.117  2002/04/17 21:50:15  dave
// hsh table fix on getFirstActiveFlagCode
//
// Revision 1.116  2002/04/17 21:36:56  dave
// do not refreshdefaults on the get here
//
// Revision 1.115  2002/04/17 20:32:10  dave
// Falg to Flag typo
//
// Revision 1.114  2002/04/17 20:30:08  dave
// syntax fixes
//
// Revision 1.113  2002/04/17 19:17:26  dave
// only allow puts that are a subset of the get on EANFlagAttribute
//
// Revision 1.112  2002/04/17 18:56:27  dave
// syntax
//
// Revision 1.111  2002/04/17 18:39:52  dave
// whoop.. needed to get the set call for the statetransition into
// the put for the EAN flag Attribute
//
// Revision 1.110  2002/04/17 17:54:08  dave
// changes to test set login on state transition
//
// Revision 1.109  2002/04/15 20:40:37  joan
// fixing errors
//
// Revision 1.108  2002/04/15 18:35:28  joan
// work on exception
//
// Revision 1.107  2002/04/15 16:55:04  joan
// syntax
//
// Revision 1.106  2002/04/15 16:31:13  joan
// fixing exception
//
// Revision 1.105  2002/04/12 23:14:01  joan
// catch exception
//
// Revision 1.104  2002/04/12 23:04:21  joan
// syntax
//
// Revision 1.103  2002/04/12 22:44:17  joan
// throws exception
//
// Revision 1.102  2002/04/12 21:34:01  joan
// null pointer
//
// Revision 1.101  2002/04/12 20:52:40  joan
// exception
//
// Revision 1.100  2002/04/12 19:43:47  joan
// work on exception
//
// Revision 1.99  2002/04/12 18:12:33  joan
// work on exception
//
// Revision 1.98  2002/04/12 16:53:31  dave
// syntax
//
// Revision 1.97  2002/04/11 18:15:08  dave
// Trace statement adjustment and null pointer fix
//
// Revision 1.96  2002/04/10 23:54:04  dave
// syntax
//
// Revision 1.95  2002/04/10 23:41:24  dave
// new containsMetaFlag method
//
// Revision 1.94  2002/04/10 23:28:46  dave
// Skipping flag codes that no longer apply in putPDHFlag
//
// Revision 1.93  2002/04/09 23:57:05  dave
// syntax
//
// Revision 1.92  2002/04/09 23:29:05  dave
// first attempt at making the state transition compile
//
// Revision 1.91  2002/04/09 22:08:16  dave
// typo fix
//
// Revision 1.90  2002/04/09 21:58:13  dave
// more trace
//
// Revision 1.89  2002/04/09 21:38:41  dave
// timing issue on put and deactivate
//
// Revision 1.88  2002/04/09 21:12:15  dave
// rollback of deactive = active first
// and more trace
//
// Revision 1.87  2002/04/09 20:21:29  dave
// syntax fix
//
// Revision 1.86  2002/04/09 20:10:11  dave
// EANFlagAttribute and put for deactiveate work
//
// Revision 1.85  2002/04/09 18:23:27  dave
// fix to put on deactivate.. deactivate after building list of dependent atts
//
// Revision 1.84  2002/04/09 17:39:34  dave
// variable problems
//
// Revision 1.83  2002/04/09 17:31:20  dave
// including deactivate in the check dependencies logic
//
// Revision 1.82  2002/04/05 20:06:00  dave
// syntax errors fixed
//
// Revision 1.81  2002/04/04 18:32:16  dave
// added more refreshReset stuff
//
// Revision 1.80  2002/04/03 18:38:09  dave
// clearing out attributes on the edit..
//
// Revision 1.79  2002/04/03 18:13:28  dave
// trace statements to find out why we have multiple status flags on the toString
//
// Revision 1.78  2002/04/03 01:08:18  dave
// more deactivate and default values
//
// Revision 1.77  2002/04/02 23:58:28  dave
// trace statements
//
// Revision 1.76  2002/04/02 19:21:58  dave
// Syntax
//
// Revision 1.75  2002/04/02 19:12:37  dave
// more isRequiredStuff
//
// Revision 1.74  2002/04/02 01:21:15  joan
// remove System.out
//
// Revision 1.73  2002/04/02 01:10:41  joan
// syntax
//
// Revision 1.72  2002/04/02 00:46:32  joan
// working on toString
//
// Revision 1.71  2002/04/02 00:31:05  joan
// working on toString
//
// Revision 1.70  2002/04/01 23:12:00  joan
// sort toString result
//
// Revision 1.69  2002/03/30 00:08:22  dave
// added return to deactivate in EANFlagAttribute
//
// Revision 1.68  2002/03/29 22:35:43  dave
// first attempt at deactivate.. and ting all flags in for the Flag Put
//
// Revision 1.67  2002/03/27 19:24:31  dave
// better tracing in check dependencies
//
// Revision 1.66  2002/03/27 19:01:14  dave
// syntax fixes
//
// Revision 1.65  2002/03/27 17:19:10  dave
// changed checkDependencies to use the put logic for
// dependency chaining
//
// Revision 1.64  2002/03/26 23:30:54  dave
// syntax fixes
//
// Revision 1.63  2002/03/26 23:12:55  dave
// Syntax error fixes in checkDependencies
//
// Revision 1.62  2002/03/26 23:00:47  dave
// minor put changes
//
// Revision 1.61  2002/03/26 22:57:21  dave
// write the check dependencies code to turn off flags
// that no longer should be turned on
//
// Revision 1.60  2002/03/26 21:30:00  dave
// fix null pointer
//
// Revision 1.59  2002/03/26 21:11:00  dave
// syntx
//
// Revision 1.58  2002/03/26 20:58:11  dave
// adding commit local logic so we have cornered updates
//
// Revision 1.57  2002/03/25 20:33:36  dave
// More trace statements
//
// Revision 1.56  2002/03/25 20:09:36  dave
// syntax fix
//
// Revision 1.55  2002/03/25 20:00:38  dave
// import fix
//
// Revision 1.54  2002/03/25 19:33:22  dave
// ensuring we are setting defered to true
//
// Revision 1.53  2002/03/25 19:27:51  dave
// gettting single flag
//
// Revision 1.52  2002/03/23 01:08:25  dave
// first attempt at update
//
// Revision 1.51  2002/03/21 22:43:40  dave
// removing some displays
//
// Revision 1.50  2002/03/21 21:48:29  dave
// trace statements
//
// Revision 1.49  2002/03/21 01:55:22  dave
// removing System.out info
//
// Revision 1.48  2002/03/20 22:53:10  dave
// syntax cleanup
//
// Revision 1.47  2002/03/20 22:41:02  dave
// Syntax work
//
// Revision 1.46  2002/03/20 21:57:50  dave
// syntax error fixes
//
// Revision 1.45  2002/03/20 21:21:11  dave
// syntax fixes, and rollback on the attribute
//
// Revision 1.44  2002/03/20 00:21:19  dave
// fix for MultiValued Display
//
// Revision 1.43  2002/03/19 19:40:47  dave
// make sure we refresh defaults after every put. to
// ensure default values are not left blank
//
// Revision 1.42  2002/03/19 18:31:45  dave
// syntax fix
//
// Revision 1.41  2002/03/19 18:22:59  dave
// more default management
//
// Revision 1.40  2002/03/19 04:58:36  dave
// syntax error
//
// Revision 1.39  2002/03/19 04:21:47  dave
// ensuring we are creating default on flag when no value exists
//
// Revision 1.38  2002/03/19 03:47:33  dave
// first attempt a setting defaults
//
// Revision 1.37  2002/03/18 19:32:44  dave
// remove EANAddressable from external interface
//
// Revision 1.36  2002/03/14 20:03:31  dave
// more trace
//
// Revision 1.35  2002/03/14 18:52:26  dave
// trace for dependent flag values
//
// Revision 1.34  2002/03/14 03:46:00  dave
// minor fix to filter on get
//
// Revision 1.33  2002/03/14 03:28:08  dave
// syntax fix
//
// Revision 1.32  2002/03/14 03:19:34  dave
// null pointer fix on put to EANList in put
//
// Revision 1.31  2002/03/14 03:03:39  dave
// various fixes to put in the flag section
//
// Revision 1.30  2002/03/14 02:43:09  dave
// trace for flag selection
//
// Revision 1.29  2002/03/14 01:42:53  dave
// syntax check
//
// Revision 1.28  2002/03/14 01:30:28  dave
// trace in dump for null pointer
//
// Revision 1.27  2002/03/14 01:01:41  dave
// fix syntax
//
// Revision 1.26  2002/03/14 00:48:29  dave
// error fixes
//
// Revision 1.25  2002/03/13 23:19:39  dave
// more fixes
//
// Revision 1.24  2002/03/13 23:07:07  dave
// fix for EANFlagAttribute
//
// Revision 1.23  2002/03/13 22:48:24  dave
// First attempt at caching
//
// Revision 1.22  2002/03/13 22:31:18  dave
// syntax
//
// Revision 1.21  2002/03/13 22:16:28  dave
// attempt at calculating flag dependencies
//
// Revision 1.20  2002/03/13 16:21:34  dave
// syntax on put
//
// Revision 1.19  2002/03/13 16:10:24  dave
// changed the put to turn all passed flag values on
//
// Revision 1.18  2002/03/13 15:55:52  dave
// null pointer fix.. MetaFlag parent cannot be null.
//
// Revision 1.17  2002/03/12 23:19:45  dave
// applying a simple put for flag
//
// Revision 1.16  2002/03/12 22:56:19  dave
// needed a try catch block
//
// Revision 1.15  2002/03/12 22:48:12  dave
// class cast problem on MetaFlag Constructor
//
// Revision 1.14  2002/03/12 22:32:01  dave
// final errors are fixes
//
// Revision 1.13  2002/03/12 22:14:50  dave
// missing ')'
//
// Revision 1.12  2002/03/12 22:04:45  dave
// more Array , import, cleanup stuff
//
// Revision 1.11  2002/03/12 21:49:33  dave
// syntax
//
// Revision 1.10  2002/03/12 21:39:53  dave
// new Comparitor
//
// Revision 1.9  2002/03/12 19:12:23  dave
// added return
//
// Revision 1.8  2002/03/12 19:02:41  dave
// fix on abstract
//
// Revision 1.7  2002/03/12 18:33:08  dave
// clean up on EANAddressable - removed the int indexes
// because they make no sense.
// Added standard put /get methods to the EANAttibute
//
// Revision 1.6  2002/02/28 21:34:22  dave
// syntax fix
//
// Revision 1.5  2002/02/28 21:25:36  dave
// syntax fix
//
// Revision 1.4  2002/02/28 21:20:01  dave
// null pointer fix
//
// Revision 1.3  2002/02/12 22:16:36  dave
// more generic fixes
//
// Revision 1.2  2002/02/12 22:06:27  dave
// more fixes to syntax
//
// Revision 1.1  2002/02/12 21:54:45  dave
// more attribute objects
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.objects.Attribute;
import COM.ibm.opicmpdh.objects.MultipleFlag;
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.opicmpdh.objects.ControlBlock;

import java.util.Iterator;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Arrays;


/**
 * EANFlagAttribute
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class EANFlagAttribute extends EANAttribute {

    // Instance variables
    /**
    *@serial
    */
    final static long serialVersionUID = 1L;

    /**
     * FIELD
     */
    protected final static Boolean BON = new Boolean(true);
    /**
     * FIELD
     */
    protected final static Boolean BOFF = new Boolean(false);

    private static EANComparator c_cmpUp = new EANComparator();

    /**
    *  Gets the version attribute of the EANFlagAttribute object
    *
    *@return    The version value
    */
    public String getVersion() {
        return "$Id: EANFlagAttribute.java,v 1.258 2012/04/16 16:33:28 wendy Exp $";
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
    *@param  _mfa                            Description of the Parameter
    *@exception  MiddlewareRequestException  Description of the Exception
    */
    public EANFlagAttribute(EANDataFoundation _edf, Profile _prof, EANMetaFlagAttribute _mfa) throws MiddlewareRequestException {
        super(_edf, _prof, _mfa);
    }

    /*
    *  Puts the PDHFlag into the PDH storage structure and
    *  of this object and always turns it on
    */
    /**
     * putPDHFlag
     *
     * @param _strFlagValue
     *  @author David Bigelow
     */
    public void putPDHFlag(String _strFlagValue) {
        // First .. check to see if it is a valid flag value
        EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) getMetaAttribute();
        if (mfa == null) {
            D.ebug(D.EBUG_SPEW,"put PDHFlag mfa is null " + _strFlagValue);
            return;
        }
        if (mfa.containsMetaFlag(_strFlagValue)) {
            if (m_hsh1 == null) {
                m_hsh1 = new Hashtable();
            }
            // Lets attempt to reuse here
            m_hsh1.put((mfa.getMetaFlag(_strFlagValue)).getFlagCode(), BON);
        } else {
            // Comment changed to verbose
            D.ebug(D.EBUG_SPEW, "EANFlagAttribute,putPDHFlag.***WARNING *** flagcode does not exist - skipping " + getKey() + ":" + _strFlagValue);
        }
    }

    /**
     *  Puts The flag value into this structure.
     *  It bypasses all the controls and refreshes
     *  for the UI interface
     *  The caller is responsible for ensuring the state
     *  of the entity is intact!
     *
     * @return boolean
     * @param _strFlagValue
     * @param _b
     */
    public boolean put(String _strFlagValue, boolean _b) {

        // first .. check  to see if the flag is in the PDH structure..
        // if so .. manipulate that one
        // if not .. put it in the local one...

        EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) getMetaAttribute();
        EntityItem ei = (EntityItem) getParent();

        if (mfa.containsMetaFlag(_strFlagValue)) {
            if (m_hsh1 != null && m_hsh1.containsKey(_strFlagValue)) {
            	// reuse flag object m_hsh1.put(_strFlagValue, (_b ? BON : BOFF));
                m_hsh1.put(mfa.getMetaFlag(_strFlagValue).getFlagCode(), (_b ? BON : BOFF));
            } else {
                if (m_hsh2 == null) {
                    m_hsh2 = new Hashtable();
                }
              
                // reuse flag object m_hsh2.put(_strFlagValue, (_b ? BON : BOFF));
                m_hsh2.put(mfa.getMetaFlag(_strFlagValue).getFlagCode(), (_b ? BON : BOFF));
            }

            if (ei != null) {
                ei.addToStack(this);
                return true;
            } else {
                D.ebug(D.EBUG_ERR,"EANFlagAttribute:put: ** ERROR *** EntityItem is NULL - skipping " + getKey() + ":" + _strFlagValue);
            }
        } else {
            D.ebug(D.EBUG_ERR,"EANFlagAttribute:put: ** ERROR *** flagcode does not exist - skipping " + getKey() + ":" + _strFlagValue);
        }

        return false;

    }

    /**
     * putViaDescription
     *
     * @param _strDescription
     * @param _b
     * @return
     *  @author David Bigelow
     */
    public boolean putViaDescription(String _strDescription, boolean _b) {
        EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) getMetaAttribute();
        if (mfa.m_hshReverseLookUp != null) {
            MetaFlag mf = (MetaFlag) mfa.m_hshReverseLookUp.get(_strDescription);
            if (mf != null) {
                return put(mf.getFlagCode(), _b);
            }
        }
        return false;
    }

    /**
    *  Generic Update routine for Flags.. Currently it does not honor any
    *  transitions. etc. It just sets them .. because State Machines have been
    *  modeled differently, etc After the code stablizes we should be able to
    *  subclass this out
    *
    *@param  _aMetaFlag                    Description of the Parameter
    *@exception  EANBusinessRuleException  Description of the Exception
    */
    public void put(Object _aMetaFlag) throws EANBusinessRuleException {
        // First check to see that everything is ok.
        // If we pass the business rules.. we

        //
        // We have to check to see which flags turned back on
        // DWB .. If we are deactivated.. we have to possibly
        // go over turn other flags off.. that are dependant
        // on these values

        EANList elControlledFlagAttributes = new EANList();
        EANMetaFlagAttribute ma = (EANMetaFlagAttribute) getMetaAttribute();
        MetaFlag[] amfOrig = (MetaFlag[]) get();
        EntityItem ei = (EntityItem) getParent();

        MetaFlag[] amf = null;
        EANList elOn = null;
        EANList elMetaFlagController = null;

        StateTransitionException ste = new StateTransitionException();

        if (_aMetaFlag == null) {

            if (this instanceof StatusAttribute && !ei.isUsedInSearch()) {
                return;
            }

            // Pull the list
            // set active to false
            // refresh its defaults

            amf = (MetaFlag[]) get();
            setActive(false);

            for (int ii = 0; ii < amf.length; ii++) {
                MetaFlag mf = ma.getMetaFlag(amf[ii].getFlagCode());
                // if the mf was selected in the array and its MetaFlag in the structure controls
                // other flag values.. we need to look closer
                if (amf[ii].isSelected() && mf.isController()) {
					// look for normal pairs of controller
                    for (int iy = 0; iy < mf.getControllerCount(); iy++) {
                        MetaFlag mfc = mf.getController(iy);
                        EANMetaFlagAttribute ma1 = (EANMetaFlagAttribute) mfc.getParent();
                        EANFlagAttribute fa1 = (EANFlagAttribute) ((EntityItem) getParent()).getAttribute(ma1.getKey());
                        // Only put other flag values on this list..
                        if (fa1 != this && fa1 != null) {
                            elControlledFlagAttributes.put(fa1);
                        }
                    }
					//RQ1103065049 look for sets of controllers
					for (int x = 0; x < mf.getControllerSetCount(); x++) {
						MetaFlag mfc = mf.getControllerSetMF(x);
						EANMetaFlagAttribute ma1 = (EANMetaFlagAttribute) mfc.getParent();
						EANFlagAttribute fa1 = (EANFlagAttribute) ((EntityItem) getParent()).getAttribute(ma1.getKey());
						// Only put other flag values on this list..
						if (fa1 != this && fa1 != null) {
							elControlledFlagAttributes.put(fa1);
						}
					}
                }

                // O.K.  Lets turn it off now...
                put(amf[ii].getFlagCode(), false);
            }

            // No refresh of defaults ...
            //refreshDefaults();

            // We should now have a clean list to go off and recalc What is now required. and what needs to be reset
            if (ei != null) {
                // DWB .. we may need to be smarter about this...
                // we need to build a list of attributes that are referenced in equations
                // and we need to onlyh do this if they control other values..
                ei.refreshRequired();
                ei.refreshRestrictions(false);
                ei.refreshResets();

                if (ma.isClassified()) {
                    ei.refreshClassifications();
                }
                ei.addToStack(this);
            }

            // Now go check the dependencies of other flag values
            for (int ii = 0; ii < elControlledFlagAttributes.size(); ii++) {
                EANFlagAttribute fa = (EANFlagAttribute) elControlledFlagAttributes.getAt(ii);
                fa.checkDependencies();
            }

            return;

        }

        //
        // We have a real put
        //

        if (!(_aMetaFlag instanceof MetaFlag[])) {
            D.ebug(D.EBUG_SPEW,"EANFlagAttribute.put(Object).***You are not putting an array of MetaFlags");
            return;
        }

        setActive(true);

        amf = (MetaFlag[]) _aMetaFlag;
        elOn = new EANList();
        elMetaFlagController = new EANList();

        //Turn all the ones that need to be on in a list..
        // and turn them on in the structure
        // We want to turn all possible on first to ensure we do not
        // get any random answers in the state machine


        for (int ii = 0; ii < amf.length; ii++) {
            if (amf[ii].isSelected()) {
                if (allowedValue(amfOrig, amf[ii].getFlagCode())) {
                    // If it is a state machine.. we need to check and advance the state
                    if (this instanceof StatusAttribute) {

                        MetaStatusAttribute msa = (MetaStatusAttribute) getMetaAttribute();

                        String strFlagTo = amf[ii].getFlagCode();
                        String strFlagFrom = getFirstActiveFlagCode();
                        StateTransition st = null;

                        ste.validate(this, strFlagTo);

                        // If something bad happened through the error
                        if (ste.getErrorCount() > 0) {
                            throw ste;
                        }

                        // We made it so.. lets perform all the sets...
                        st = msa.getStateTransition(strFlagFrom, strFlagTo);
                        if (st != null) {
                            st.set((EntityItem) getParent());
                        }
                    }
                } else {

                    // The caller is attempting to set a flag value illegally

                    ste.add(this, amf[ii].getFlagCode() + ":is not an allowed value for the state of the given entity.");
                    throw ste;
                }

                // update the structures
                elOn.put(amf[ii]);
                put(amf[ii].getFlagCode(), true);
            }
        }

        // Now go through and turn off the others that could be on
        if (m_hsh1 != null) {
            Iterator it = m_hsh1.keySet().iterator();
            while (it.hasNext()) {
                String strFlagCode = (String) it.next();
                String strKey = getAttributeCode() + strFlagCode;
                boolean bOn = ((Boolean) m_hsh1.get(strFlagCode)).booleanValue();
                // If it was on.. but turned off turn it off .. Refresh things on a controller list.
                if (!elOn.containsKey(strKey) && bOn) {
                    MetaFlag mf = ma.getMetaFlag(strFlagCode);
                    put(strFlagCode, false);
                    if (ma == null) {
                        D.ebug(D.EBUG_SPEW,"EANFlagAttribute.put().FlagCode Cannot Be Found:" + strFlagCode);
                    } else {
                        if (mf.isController()) {
                            elMetaFlagController.put(mf);
                        }
                    }
                }
            }
        }

        if (m_hsh2 != null) {
            Iterator it = m_hsh2.keySet().iterator();
            while (it.hasNext()) {
                String strFlagCode = (String) it.next();
                String strKey = getAttributeCode() + strFlagCode;
                boolean bOn = ((Boolean) m_hsh2.get(strFlagCode)).booleanValue();
                // If it was on.. but turned off turn it off .. Refresh things on a controller list.
                if (!elOn.containsKey(strKey) && bOn) {
                    MetaFlag mf = ma.getMetaFlag(strFlagCode);
                    put(strFlagCode, false);
                    if (mf.isController()) {
                        elMetaFlagController.put(mf);
                    }
                }
            }
        }

        // now loop through the list of controller attributes and turn things off based upon what is up
        // And make sure we do not call ourselves here..
        // We can conrol ourselves in the state machine...
        for (int ii = 0; ii < elMetaFlagController.size(); ii++) {
            MetaFlag mf = (MetaFlag) elMetaFlagController.getAt(ii);
            // look for normal pairs
            for (int iy = 0; iy < mf.getControllerCount(); iy++) {
                MetaFlag mfc = mf.getController(iy);
                EANMetaFlagAttribute ma1 = (EANMetaFlagAttribute) mfc.getParent();
                EANFlagAttribute fa1 = (EANFlagAttribute) ((EntityItem) getParent()).getAttribute(ma1.getKey());
                // Only put other flag values on this list..
                if (fa1 != this && fa1 != null) {
                    elControlledFlagAttributes.put(fa1);
                }
            }
			//RQ1103065049 look for sets of controllers
            for (int x = 0; x < mf.getControllerSetCount(); x++) {
                MetaFlag mfc = mf.getControllerSetMF(x);
                EANMetaFlagAttribute ma1 = (EANMetaFlagAttribute) mfc.getParent();
                EANFlagAttribute fa1 = (EANFlagAttribute) ((EntityItem) getParent()).getAttribute(ma1.getKey());
                // Only put other flag values on this list..
                if (fa1 != this && fa1 != null) {
                    elControlledFlagAttributes.put(fa1);
                }
            }
        }

        // Now force a refresh on all effected FlagAttributes.
        // The put should do all the proper filtering...

        // sDo we have to set some default values back?

        // refreshDefaults();
        if (ei != null) {
            ei.refreshRequired();
            ei.refreshRestrictions(false);
            ei.refreshResets();
            if (ma.isClassified()) {
                ei.refreshClassifications();
            }
            ei.refreshDefaults();
        }

        for (int ii = 0; ii < elControlledFlagAttributes.size(); ii++) {
            EANFlagAttribute fa = (EANFlagAttribute) elControlledFlagAttributes.getAt(ii);
            fa.checkDependencies();
        }

        // GAB 042803 - autoselects

        if (ma.isSelectHelper() && !ei.isUsedInSearch()) {
            runAutoSelects(amfOrig);
        }

    }

    /**
     * Run AutoSelect feature for any selected MetaFlags.
     * Note we are assuming :
     *     a) each attribute controls one and only one other attribute.
     *     b) attributes involved must be autoselects.
     *
     * @param _amfOrig
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     */
    protected void runAutoSelects(MetaFlag[] _amfOrig) throws EANBusinessRuleException {
      MetaFlag[] amfCurr = null;
        EANList eListOffs = null;
        EANList eListOns = null;

        EANFlagAttribute efa2 = null;
        MetaFlag[] amf2 = null;

        // FIRST: go through and figure out if there are any autoselect flags to worry about..
        boolean bAutoSelectsFound = false;
        for (int i = 0; i < _amfOrig.length; i++) {
            if (_amfOrig[i].hasAutoSelects()) {
                bAutoSelectsFound = true;
                break;
            }
        }
        // no autoselects to run so bail now.
        if (!bAutoSelectsFound) {
            return;
        }
        // current.
        amfCurr = (MetaFlag[]) get();
        if (amfCurr == null) {
            return;
        }
        //Find Diffs b/c we only want to run autoselect logic for values that have *changed*
        eListOffs = new EANList();
        eListOns = new EANList();

        for (int iOrig = 0; iOrig < _amfOrig.length; iOrig++) {
            MetaFlag mfOrig = _amfOrig[iOrig];
            CURR : for (int iCurr = 0; iCurr < amfCurr.length; iCurr++) {
                MetaFlag mfCurr = amfCurr[iCurr];
                if (mfOrig.getFlagCode().equals(mfCurr.getFlagCode())) {
                    // turned off
                    if (mfOrig.isSelected() && !mfCurr.isSelected()) {
                        for (int iAutos = 0; iAutos < mfOrig.getAutoSelectFlagCount(); iAutos++) {
                            eListOffs.put(mfOrig.getAutoSelectFlag(iAutos));
                        }
                    }
                    // turned on
                    else if (!mfOrig.isSelected() && mfCurr.isSelected()) {
                        for (int iAutos = 0; iAutos < mfOrig.getAutoSelectFlagCount(); iAutos++) {
                            eListOns.put(mfOrig.getAutoSelectFlag(iAutos));
                        }
                    }
                    break CURR;
                }
            }
        }
        // We now have a complete list of which values were turned on/off in *THIS* attribute.
        // Now - go through controllee values and set these accordingly
        for (int i = 0; i < _amfOrig.length; i++) {
            if (_amfOrig[i].hasAutoSelects()) {
                // GAB 092303 - some null checking, but same functionality otherwise
                String strFlagKey = _amfOrig[i].getAutoSelectFlag(0).getParent().getKey();
                EntityItem eiParent = (EntityItem) getParent();
                if (eiParent == null) {
                    System.err.println("EANFlagAttribute.runAutoSelects():eiParent is null [" + i + "], strFlagKey = " + strFlagKey);
                    break;
                }
                efa2 = (EANFlagAttribute) eiParent.getAttribute(strFlagKey);
                if (efa2 == null) {
                    System.err.println("EANFlagAttribute.runAutoSelects():efa2 is null [" + i + "], strFlagKey = " + strFlagKey + ". Try clearing cache for " + eiParent.getEntityType());
                    break;
                }
                amf2 = (MetaFlag[]) efa2.get();
                break;
            }
        }
        if (efa2 == null || amf2 == null) {
            System.err.println("runAutoSelects():error retrieving attribute 2 values (" + getAttributeCode() + ")...");
            return;
        }
        for (int i = 0; i < amf2.length; i++) {
            // 1) if this is on AND it should be turned off...
            if (amf2[i].isSelected() && (eListOffs.indexOf(amf2[i]) > -1)) {
                amf2[i].setSelected(false);
            }
            // 2) if this is off AND it should be turned on...
            if (!amf2[i].isSelected() && (eListOns.indexOf(amf2[i]) > -1)) {
                amf2[i].setSelected(true);
            }
        }
        // now put() back!!
        efa2.put(amf2);
    }

    private void checkFilter(MetaFlag mfFilter, Hashtable hsh){
        EntityItem ei = (EntityItem) getParent();
		EANMetaFlagAttribute mfaFilter = (EANMetaFlagAttribute) mfFilter.getParent();
		EANFlagAttribute fa = (EANFlagAttribute) ei.getAttribute(mfaFilter.getAttributeCode());
		if (fa == null) {
			// this causes NPE hsh.put(fa.getKey(), BOFF);
			hsh.put(mfaFilter.getAttributeCode(), BOFF);
		} else if (!fa.isActive()) {
			hsh.put(fa.getKey(), BOFF);
		} else if (!hsh.containsKey(fa.getKey()) ||
			(!((Boolean) hsh.get(fa.getKey())).booleanValue() && fa.isSelected(mfFilter))) {
			hsh.put(fa.getKey(), (fa.isSelected(mfFilter) ? BON : BOFF));
		}
	}

    /*
    *  This loops through all the flag values and turns off all that
    *  can no longer be on because of some dependency...
    */
    /**
    *  Description of the Method
    */
    protected void checkDependencies() {
        // Look at each flag that is turned on and turn it off if the qualifier is missing
        // Now go through and turn off the others that could be on

        // Get the ones that are currently on via the Get call..

        MetaFlag[] amf = (MetaFlag[]) get();

        MetaFlag[] amfPut = null;

        EANList eOn = new EANList();

        //EntityItem ei = (EntityItem) getParent();
        EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) getMetaAttribute();

        for (int ii = 0; ii < amf.length; ii++) {
            if (amf[ii].isSelected()) {
                MetaFlag mf = mfa.getMetaFlag(amf[ii].getFlagCode());
                Hashtable hsh = new Hashtable();
                Iterator it = null;
                boolean bKeep = true;
                // check for normal pairs of filters
                for (int iy = 0; iy < mf.getFilterCount(); iy++) {
                    MetaFlag mfFilter = mf.getFilter(iy);
                    checkFilter(mfFilter, hsh);
                }
                //RQ1103065049 check for set of filters
                for (int iy = 0; iy < mf.getFilterSetCount(); iy++) {
                    //memchg EANList mflist = mf.getFilterSet(iy);
                    Vector mflist = mf.getFilterSet(iy);
                    for (int m=0; m<mflist.size(); m++){
						//memchg MetaFlag mfFilter = (MetaFlag)mflist.getAt(m);
						MetaFlag mfFilter = (MetaFlag)mflist.elementAt(m);//memchg .getAt(m);
                    	checkFilter(mfFilter, hsh);
					}
                }

                // Now loop through everything in the hshtable if all are true we can move the flag to the EANList
                it = hsh.keySet().iterator();
                while (it.hasNext()) {
                    String strAttributeCode = (String) it.next();
                    if (!((Boolean) hsh.get(strAttributeCode)).booleanValue()) {
                        bKeep = false;
                        break;
                    }
                }
                // If there were no more left...
                if (bKeep) {
                    eOn.put(amf[ii]);
                }
            }
        }

        // Now we have a true list of what is supposed to be on (I think)
        amfPut = new MetaFlag[eOn.size()];
        eOn.copyTo(amfPut);

        try {
            put(amfPut);
        } catch (EANBusinessRuleException bre) {
            bre.printStackTrace();
        }
    }

    /**
    *  Gets the selected attribute of the EANFlagAttribute object
    *
    *@param  _mf  Description of the Parameter
    *@return      The selected value
    */
    public boolean isSelected(MetaFlag _mf) {
        if (m_hsh1 != null) {
            Boolean bln = (Boolean) m_hsh1.get(_mf.getFlagCode());
            if (bln != null) {
                return bln.booleanValue();
            }
        }
        if (m_hsh2 != null) {
            Boolean bln = (Boolean) m_hsh2.get(_mf.getFlagCode());
            if (bln != null) {
                return bln.booleanValue();
            }
        }

        return false;
    }

    /**
     * isSelected
     *
     * @param _strFlagCode
     * @return
     *  @author David Bigelow
     */
    public boolean isSelected(String _strFlagCode) {
        if (m_hsh1 != null) {
            Boolean bln = (Boolean) m_hsh1.get(_strFlagCode);
            if (bln != null) {
                return bln.booleanValue();
            }
        }
        if (m_hsh2 != null) {
            Boolean bln = (Boolean) m_hsh2.get(_strFlagCode);
            if (bln != null) {
                return bln.booleanValue();
            }

        }

        return false;

    }

    /**
    *  Returns an array of 'Selectable' display flag items. That could be
    *  filtered.. If all the controlling flags are within the same FlagAttribute..
    *  it can be assumed that this is a state machine.. and the controllers are
    *  'ORED'
    *
    *@return    Description of the Return Value
    */

    public Object get() {
        MetaFlag[] amf = null;

        // First get the meta Attribute
        EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) getMetaAttribute();
        // Now get the entityItem
        EntityItem ei = (EntityItem) getParent();
        EntityGroup eg = ei.getEntityGroup();

        int iFlagCount = mfa.getMetaFlagCount();
        Vector vct = new Vector();
        boolean bOneFound = false;

        for (int ii = 0; ii < iFlagCount; ii++) {
            MetaFlag mf = mfa.getMetaFlag(ii);
            boolean bfound = false;
            if (this instanceof StatusAttribute && ei.isUsedInSearch()) {
                bfound = true;
            } else if (!isSelected(mf) && mf.isFiltered() && !mf.isExpired()) {
                Iterator it = null;
                Hashtable hsh = new Hashtable();
                bfound = true;
                // For flag dependencies we need to know what is allowable..
                // look for normal pairs of filters
                for (int iy = 0; iy < mf.getFilterCount(); iy++) {
                    MetaFlag mfFilter = mf.getFilter(iy);
                    checkFilter(mfFilter, hsh);
                }

                //RQ1103065049 check for set of filters
                for (int iy = 0; iy < mf.getFilterSetCount(); iy++) {
                    //memchg EANList mflist = mf.getFilterSet(iy);
                    Vector mflist = mf.getFilterSet(iy);
                    for (int m=0; m<mflist.size(); m++){
						MetaFlag mfFilter = (MetaFlag)mflist.elementAt(m);//memchg .getAt(m);
                    	checkFilter(mfFilter, hsh);
					}
                }

                // Now loop through everything in the hshtable if anyone is false.. we set bfound to false and break
                it = hsh.keySet().iterator();
                while (it.hasNext()) {
                    String strAttributeCode = (String) it.next();
                    if (!((Boolean) hsh.get(strAttributeCode)).booleanValue()) {
                        bfound = false;
                        break;
                    }
                }
            } else if (mf.isExpired() && isSelected(mf)) {
                bfound = true;
            } else if (!mf.isExpired() && !eg.isOutOfCirculation(mf)) {
                bfound = true;
            }

            // You have to display it.. it is active regardless of the filter
            if (bfound) {
                if (!bOneFound && isSelected(mf)) {
                    bOneFound = true;
                }
                try {
                    MetaFlag mfx = new MetaFlag(getMetaAttribute(), null, mf.getFlagCode());
                    mfx.putLongDescription(mf.getLongDescription());
                    mfx.putShortDescription(mf.getShortDescription());
                    mfx.setSelected(isSelected(mf));
                    mfx.setRestricted(mf.isRestricted()); 
                    mfx.setAutoSelects(mf.getAutoSelects());
                    vct.addElement(mfx);
                } catch (Exception x) {
                    // Should never happen
                    x.printStackTrace();
                }
            }
        }

        amf = new MetaFlag[vct.size()];
        vct.copyInto(amf);
        Arrays.sort(amf, c_cmpUp);
        return amf;
    }

    /**
     * getFlagLongDescription
     *
     * @param _strFlagCode
     * @return
     *  @author David Bigelow
     */
    public String getFlagLongDescription(String _strFlagCode) {
        MetaFlag mf = null;
        EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) getMetaAttribute();
        if (mfa == null) {
            // 22270
            D.ebug(D.EBUG_SPEW,"EANFlagAttribute.getFlagLongDescription(): no meta for this flag value");
            return "92 - MetaAttribute object missing:" + getKey();
        }
        mf = mfa.getMetaFlag(_strFlagCode);
        if (mf == null) {
            return "93 no MetaFlag exists for:" + mfa.getKey() + ":" + _strFlagCode;
        }
        return (mfa.isUSEnglishOnly() ? mf.getLongDescription(1) : mf.getLongDescription());
    }

    /**
     * getAttributeType
     *
     * @return
     *  @author David Bigelow
     */
    public String getAttributeType() {
        return getMetaAttribute().getAttributeType();
    }

    /**
    *  Description of the Method
    *
    *@return    Description of the Return Value
    */
    public String toString() {

        Object[] aResult = null;

        StringBuffer strbResult = new StringBuffer();
        Vector vResult = new Vector();
        boolean bFirstPass = true;

        if (!isActive()) {
            return "";
        }

        if (m_hsh1 != null) {
            Iterator it = m_hsh1.keySet().iterator();
            while (it.hasNext()) {
                String strFlagCode = (String) it.next();
                if (((Boolean) m_hsh1.get(strFlagCode)).booleanValue()) {
                    if (this instanceof MultiFlagAttribute) {
                        vResult.addElement("* " + getFlagLongDescription(strFlagCode));
                    } else {
                        vResult.addElement("" + getFlagLongDescription(strFlagCode));
                    }
                }
            }
        }

        if (m_hsh2 != null) {
            Iterator it = m_hsh2.keySet().iterator();
            while (it.hasNext()) {
                String strFlagCode = (String) it.next();
                if (((Boolean) m_hsh2.get(strFlagCode)).booleanValue()) {
                    if (this instanceof MultiFlagAttribute) {
                        vResult.addElement("* " + getFlagLongDescription(strFlagCode));
                    } else {
                        vResult.addElement("" + getFlagLongDescription(strFlagCode));
                    }
                }
            }
        }

        if (vResult.size() == 0) {
            return "";
        }

        if (vResult.size() == 1) {
            return (String) vResult.elementAt(0);
        }

        //
        // Else .. we have to sort
        //
        aResult = vResult.toArray();
        Arrays.sort(aResult, c_cmpUp);
        for (int i = 0; i < aResult.length; i++) {
            String temp = (String) aResult[i];
            strbResult.append((bFirstPass ? "" : "\n") + temp);
            bFirstPass = false;
        }
        return strbResult.toString();
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

        // Look through the local ones if found return it
        if (m_hsh2 != null) {
            Iterator it = m_hsh2.keySet().iterator();
            while (it.hasNext()) {
                String strFlagCode = (String) it.next();
                if (((Boolean) m_hsh2.get(strFlagCode)).booleanValue()) {
                    return true;
                }
            }
        }

        if (m_hsh1 != null) {
            // Look through the PDH ones for an on value
            Iterator it = m_hsh1.keySet().iterator();
            while (it.hasNext()) {
                String strFlagCode = (String) it.next();
                if (((Boolean) m_hsh1.get(strFlagCode)).booleanValue()) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
    *  Gets the firstActiveFlagCode attribute of the EANFlagAttribute object
    *
    *@return    The firstActiveFlagCode value
    */
    public String getFirstActiveFlagCode() {
        if (!isActive()) {
            return "";
        }

        // Look through the local ones if found return it
        if (m_hsh2 != null) {
            Iterator it = m_hsh2.keySet().iterator();
            while (it.hasNext()) {
                String strFlagCode = (String) it.next();
                if (((Boolean) m_hsh2.get(strFlagCode)).booleanValue()) {
                    return strFlagCode;
                }
            }
        }

        if (m_hsh1 != null) {
            Iterator it = m_hsh1.keySet().iterator();
            while (it.hasNext()) {
                String strFlagCode = (String) it.next();
                if (((Boolean) m_hsh1.get(strFlagCode)).booleanValue()) {
                    return strFlagCode;
                }
            }
        }

        return "";
    }

    /**
    *  Description of the Method
    *
    *@param  _bBrief  Description of the Parameter
    *@return          Description of the Return Value
    */
    public String dump(boolean _bBrief) {
        return "EANFlagAttribute:" + getKey() + ":" + (getMetaAttribute() == null ? "ma is null" : getMetaAttribute().toString()) + ":" + toString();
    }

    /*
    *  This refreshes the Defaults for this attribute
    *  We first check for any defaults that the parent may have.. (this is the get, keep the ones that
    *  are sacred trick.. then perform the put with this modified list
    *  put trick).
    */
    /**
     * (non-Javadoc)
     * refreshDefaults
     *
     * @see COM.ibm.eannounce.objects.EANAttribute#refreshDefaults()
     */
    public void refreshDefaults() {

        // Get the default EntityItem from the Parent.. then let us
        // then we basically do a put.. and if there is no parent defaults..
        // we rely on the Universal defaults

        // First check to see if any default values exist in the
        // context of an EntityGroup/WorkGroup

        // No default items
        // when used in search
        MetaFlag[] amfOrig = null;

        EntityItem ei = getEntityItem();
        EANMetaAttribute ma = getMetaAttribute();

        if (ei.isUsedInSearch()) {
            return;
        }

        // GAB save the OG flags off for runAuoSelects() below. Remember that this is a de-referenced copy, so we're safe.
        amfOrig = (MetaFlag[]) get();

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
                EANFlagAttribute attDef = (EANFlagAttribute) eidef.getAttribute(getKey());
                if (attDef != null) {
                    MetaFlag[] amf = (MetaFlag[]) attDef.get();
                    for (int ii = 0; ii < amf.length; ii++) {
                        if (amf[ii].isSelected()) {
                            put(amf[ii].getFlagCode(), true);
                            setActive(true);
                        }
                    }

                    // GAB 090803 - autoselects for refresh defaults()
                    if (ma.isSelectHelper()) {
                        try {
                            runAutoSelects(amfOrig);
                        } catch (EANBusinessRuleException eanBRExc) {
                            System.err.println("Error in refreshDefaults().runAutoSelects():");
                            eanBRExc.printStackTrace(System.err);
                        }
                    }
                    // END GAB 090803

                    return;
                }
            }

            // O.K.  If we have made it this far.. we must rely on defaults

            if (ma.hasDefaultValue()) {
                put(ma.getDefaultValue(), true);
                setActive(true);

                // GAB 090803 - autoselects for refresh defaults()
                if (ma.isSelectHelper()) {
                    try {
                        runAutoSelects(amfOrig);
                    } catch (EANBusinessRuleException eanBRExc) {
                        System.err.println("Error in refreshDefaults().runAutoSelects():");
                        eanBRExc.printStackTrace(System.err);
                    }
                }
                // END GAB 090803

            }

            // for WG default
            if (ma.isWGDEFAULT()) {
            	String[] as = ma.getWGDefaultValues();
            	if (as != null) {
            		// the meta may have an override for the attribute type, only turn on one flag for single flags
            		for (int i=0; i < as.length; i++) {
            			if (ma instanceof MetaSingleFlagAttribute){ //MN36915802/36915859
            				put(as[i], i==0); // only turn on the first one
                		}else{
                			put(as[i], true);
                		}

            			setActive(true);
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

        if (!isActive()) {
            setActive(true);
        }

        // need to rollback any other attributes that were set because of the transition getting rolledback
        String strFlagTo =null;
        if (this instanceof StatusAttribute) {
        	// get value before the rollback 
        	MetaFlag[] amfBefore = (MetaFlag[]) get();
        	for (int ii = 0; ii < amfBefore.length; ii++) {
        		if (amfBefore[ii].isSelected()) {
        			strFlagTo = amfBefore[ii].getFlagCode();
        			break;
        		}
        	}
        }
        
        m_hsh2 = null;
        // Loop through m_hsh1 and turn everything back on
        if (m_hsh1 != null) {
            Iterator it = m_hsh1.keySet().iterator();
            while (it.hasNext()) {
                String strFlagCode = (String) it.next();
                m_hsh1.put(strFlagCode, BON);
            }
        }

        ei.removeFromStack(this);
        ei.refreshRestrictions();//false); need to completely reset the restrictions or a previous entity might have been used
        ei.refreshRequired();
        if (ma != null && ma.isClassified()) {
            ei.refreshClassifications();
        }

        refreshDefaults();
       
        // If it is a state machine.. we need to check and remove anything that was previously set by this transition
        if (strFlagTo !=null) {
            MetaStatusAttribute msa = (MetaStatusAttribute) getMetaAttribute();
            String strFlagFrom = getFirstActiveFlagCode();
            StateTransition st = msa.getStateTransition(strFlagFrom, strFlagTo);
            if (st != null) {
            	EntityItem parent = (EntityItem) getParent();
                if (st.evaluate(parent)) {
        			for (int ii = 0; ii <  st.getSetItemCount();ii++) {
        				SetItem si = st.getSetItem(ii);
        				String strAttributeCode = si.getMetaAttribute().getAttributeCode();
        				String strEntityType = parent.getEntityType();
        				String strKey = strEntityType + ":" + strAttributeCode;
        				// Make sure an object is there
        				EANAttribute att = (EANAttribute)parent.getEANObject(strKey);
        				if(att!=null){
        					att.rollback();
        				}
        			}
                }
            }
        }
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

        EntityItem ei = (EntityItem) getParent();
        EANMetaAttribute ma = getMetaAttribute();

        // Only package a vector if the business rules have been met
        Vector vctReturn = null;

        ControlBlock cbOn = null;
        ControlBlock cbOff = null;

        boolean bFound = false;
    //   boolean bComboUnique = false;

   //     String strComboAttributeCode = null;
    //    String strComboAttributeValue = null;
    //    String strComboAttributeDesc = null;

        if (!checkBusinessRules()) {
            return null;
        }

        vctReturn = new Vector();

        cbOn = new ControlBlock(getEffFrom(), getEffTo(), getEffFrom(), getEffTo(), getProfile().getOPWGID());
        cbOff = new ControlBlock(getEffFrom(), getEffFrom(), getEffFrom(), getEffFrom(), getProfile().getOPWGID());

       /* orig if (ma.isComboUnique()) {  BH SR87, SR655 - extended combounique rule
            for (int i = 0; i < ma.getComboUniqueAttributeCode().size(); i++) {
                String strAttCode = ma.getComboUniqueAttributeCode(i);
                // We need to check this only if there is a value in the sister attribute
                EANAttribute att = ei.getAttribute(strAttCode);
                if (att != null) {
                    if (att.isActive()) {
                        bComboUnique = true;
                        strComboAttributeCode = strAttCode;
                        strComboAttributeValue = att.toString();
                        strComboAttributeDesc = att.getMetaAttribute().getLongDescription();
                    }
                    break;
                }
            }
        }*/

        // extended support for combounique BH SR87, SR655
        String comboArray[] = updateUniquenessAttrs(ei, ma,null);

        // If we are a status flag, and unique flag, or a Task..
        // we generate one SINGLE flag answer.
        if (this instanceof SingleFlagAttribute || this instanceof StatusAttribute || this instanceof TaskAttribute) {
            if (!isActive()) {
                // Look in the PDH side and we turn off everything in the PDH holding side of the object
                if (m_hsh1 != null) {
                    Iterator it = m_hsh1.keySet().iterator();
                    while (it.hasNext()) {
                        String strFlagCode = (String) it.next();
                        SingleFlag sf = new SingleFlag(getProfile().getEnterprise(), ei.getEntityType(), ei.getEntityID(), getAttributeCode(), strFlagCode, 1, cbOff);
                        vctReturn.addElement(sf);
                        bFound = true;
                        // Do not forget to set deferred to true if it is a status flag
                        if (this instanceof StatusAttribute || this instanceof TaskAttribute) {
                            sf.setDeferredPost(true);
                            sf.setDeferredPostMustBeLast(this instanceof TaskAttribute);// ABRs must be after STATUS
                        }
                    }
                }
            } else {
                if (m_hsh2 != null) {
                    Iterator it = m_hsh2.keySet().iterator();
                    while (it.hasNext()) {
                        String strFlagCode = (String) it.next();
                        if (((Boolean) m_hsh2.get(strFlagCode)).booleanValue()) {
                            // We found value that was turned on
                            SingleFlag sf = new SingleFlag(getProfile().getEnterprise(), ei.getEntityType(), ei.getEntityID(), getAttributeCode(), strFlagCode, 1, cbOn);
                            vctReturn.addElement(sf);
                            bFound = true;
                            // Do not forget to set deferred to true if it is a status flag
                            if (this instanceof StatusAttribute || this instanceof TaskAttribute) {
                                sf.setDeferredPost(true);
                                sf.setDeferredPostMustBeLast(this instanceof TaskAttribute);// ABRs must be after STATUS
                            }
                            // It there is a unique requirement on this Single Flag.. we need to set it up in the object
                            updateUniquenessAttrs(ei, ma, sf, toString(), comboArray); // BH SR87, SR655 - extended combounique rule
                            /*if (ma.isUnique()) {
                                sf.m_bUnique = true;
                                sf.m_strUniqueClass = ma.getUniqueClass();
                                sf.m_strUniqueType = ma.getUniqueType();
                                sf.m_strFlagDescription = toString();
                                sf.m_strDescription = ma.getLongDescription();
                            }
                            if (bComboUnique) {
                                sf.m_bComboUnique = ma.isComboUnique();
                                sf.m_strComboAttributeCode = strComboAttributeCode;
                                sf.m_strComboAttributeValue = strComboAttributeValue;
                                sf.m_strComboAttributeDesc = strComboAttributeDesc;
                                sf.m_strFlagDescription = toString();
                                sf.m_strLongDescription = ma.getLongDescription();
                                sf.m_strComboUniqueGrouping = ma.getComboUniqueGrouping();
                                sf.m_objReference = ei.getProfile().getRoleCode(); // needed to determine comboattr type
                            }*/

                            break;
                        }
                    }
                } else {
                	//MN43669195
                	// if the parent dependent choice was changed and a child existed that was blanked out
                	// but no new child value was specified, it is getting left active, turn it off here
                	/*LSCRSMKTCHAPPRIM
                	isActive true
                	m_hsh1 {50170=false}
                	m_hsh2 {50390=true}
                	LSCRSMKTSUBCHAPPRI
                	isActive true
                	m_hsh1 {10340=false}
                	m_hsh2 null*/
                	if(m_hsh1!=null){
                		Iterator it = m_hsh1.keySet().iterator();
                		while (it.hasNext()) {
                			String strFlagCode = (String) it.next();
                			Boolean onOff = (Boolean)m_hsh1.get(strFlagCode);
                			if(BOFF==onOff){
                				SingleFlag sf = new SingleFlag(getProfile().getEnterprise(), ei.getEntityType(), ei.getEntityID(), getAttributeCode(), strFlagCode, 1, cbOff);
                				vctReturn.addElement(sf);
                				bFound = true;
                				// Do not forget to set deferred to true if it is a status flag
                				if (this instanceof StatusAttribute || this instanceof TaskAttribute) {
                					sf.setDeferredPost(true);
                			        sf.setDeferredPostMustBeLast(this instanceof TaskAttribute);// ABRs must be after STATUS
                				}
                			    setActive(false);
                			}
                		}
                	}
                } // end m_hsh2 was null
            }// end Singleflag attr is active
        } else {

            // Here we loop through both and make multiflag records for the ones that need to be turned off
            // and turned on

            if (!isActive()) {
                // Look in the PDH side and we turn off everything in the PDH holding side of the object
                if (m_hsh1 != null) {
                    Iterator it = m_hsh1.keySet().iterator();
                    while (it.hasNext()) {
                        String strFlagCode = (String) it.next();
                        vctReturn.addElement(new MultipleFlag(getProfile().getEnterprise(), ei.getEntityType(), ei.getEntityID(), getAttributeCode(), strFlagCode, 1, cbOff));
                        bFound = true;
                    }
                }
            } else {

                if (m_hsh1 != null) {
                    // Look PDH turn off everything that is off
                    Iterator it = m_hsh1.keySet().iterator();
                    while (it.hasNext()) {
                        String strFlagCode = (String) it.next();
                        if (!((Boolean) m_hsh1.get(strFlagCode)).booleanValue()) {
                            vctReturn.addElement(new MultipleFlag(getProfile().getEnterprise(), ei.getEntityType(), ei.getEntityID(), getAttributeCode(), strFlagCode, 1, cbOff));
                            bFound = true;
                        }
                    }
                }

                if (m_hsh2 != null) {
                    Iterator it = m_hsh2.keySet().iterator();
                    while (it.hasNext()) {
                        String strFlagCode = (String) it.next();
                        if (((Boolean) m_hsh2.get(strFlagCode)).booleanValue()) {
                            MultipleFlag mf = new MultipleFlag(getProfile().getEnterprise(), ei.getEntityType(), ei.getEntityID(), getAttributeCode(), strFlagCode, 1, cbOn);
                            vctReturn.addElement(mf);
                            bFound = true;
                            // BH SR87, SR655 - extended combounique rule
                            updateUniquenessAttrs(ei, ma, mf,this.toString(), comboArray);
                            /*if (bComboUnique) {
                                mf.m_bComboUnique = ma.isComboUnique();
                                mf.m_strComboAttributeCode = strComboAttributeCode;
                                mf.m_strComboAttributeValue = strComboAttributeValue;
                                mf.m_strComboAttributeDesc = strComboAttributeDesc;
                                mf.m_strFlagDescription = toString();
                                mf.m_strLongDescription = ma.getLongDescription();
                                mf.m_strComboUniqueGrouping = ma.getComboUniqueGrouping();
                                mf.m_objReference = ei.getProfile().getRoleCode(); // needed to determine comboattr type
                            }*/
                        }
                    }
                }
            }
        }

        // So .. do we have anything to update?
        if (bFound) {
            return vctReturn;
        }

        return null;
    }
    /**
     *  BH SR87, SR655 - extended combounique rule
     * @param ei
     * @param ma
     * @param mf
     * @param flagdesc
     * @param comboArray
     */
    public static void updateUniquenessAttrs(EntityItem ei, EANMetaAttribute ma, MultipleFlag mf,
    		String flagdesc, String[] comboArray)
    {
        if (comboArray!=null) {
            mf.m_bComboUnique = ma.isComboUnique();
            mf.m_strComboAttributeCode = comboArray[0];
            mf.m_strComboAttributeValue = comboArray[1];
            mf.m_strComboAttributeDesc = comboArray[2];
            mf.m_strFlagDescription = flagdesc;
            mf.m_strLongDescription = ma.getLongDescription();
            mf.m_strComboUniqueGrouping = ma.getComboUniqueGrouping();
            mf.m_objReference = ei.getProfile().getRoleCode(); // needed to determine comboattr type
        }
    }
    /**
     *  BH SR87, SR655 - extended combounique rule
     * @param ei
     * @param ma
     * @param sf
     * @param flagdesc
     * @param comboArray
     */
    public static void updateUniquenessAttrs(EntityItem ei, EANMetaAttribute ma, SingleFlag sf,
    		String flagdesc, String[] comboArray)
    {
        if (ma.isUnique()) {
            sf.m_bUnique = true;
            sf.m_strUniqueClass = ma.getUniqueClass();
            sf.m_strUniqueType = ma.getUniqueType();
            sf.m_strFlagDescription = flagdesc;
            sf.m_strDescription = ma.getLongDescription();
        }
        if (comboArray!=null) {
            sf.m_bComboUnique = ma.isComboUnique();
            sf.m_strComboAttributeCode = comboArray[0];
            sf.m_strComboAttributeValue = comboArray[1];
            sf.m_strComboAttributeDesc = comboArray[2];
            sf.m_strFlagDescription = flagdesc;
            sf.m_strLongDescription = ma.getLongDescription();
            sf.m_strComboUniqueGrouping = ma.getComboUniqueGrouping();
            sf.m_objReference = ei.getProfile().getRoleCode(); // needed to determine comboattr type
        }
    }
    /**
     * determine attributes needed for combo uniqueness
     *  BH SR87, SR655 - extended combounique rule
     * @param ei
     * @param ma
     * @param attrValueTbl - used by PDGUtility
     * @return
     */
    public static String[] updateUniquenessAttrs(EntityItem ei, EANMetaAttribute ma, Hashtable attrValueTbl)
    {
    	String comboArray[] = null;
        if (ma.isComboUnique()) {
        	int cnt=0;
        	StringBuffer sbAttrCode = new StringBuffer();
         	StringBuffer sbAttrValue = new StringBuffer();
          	StringBuffer sbAttrDesc = new StringBuffer();
            for (int i = 0; i < ma.getComboUniqueAttributeCode().size(); i++) {
                String strAttCode = ma.getComboUniqueAttributeCode(i);
                // We need to check this only if there is a value in all of the sister attributes
                EANAttribute att = ei.getAttribute(strAttCode);
                if(attrValueTbl!=null){
                    String str = (String) attrValueTbl.get(ei.getEntityType() + ":" + strAttCode);
                    if (str != null && str.length() > 0) {
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
            	comboArray = new String[3];
            	comboArray[0] = sbAttrCode.toString();
            	comboArray[1] =  sbAttrValue.toString();
            	comboArray[2] =  sbAttrDesc.toString();
            }
        }
    	return comboArray;
    }
    /**
    *  Description of the Method
    */
    public void commitLocal() {

        Hashtable hsh1 = m_hsh1;
        Hashtable hsh2 = m_hsh2;

        resetHash1();
        resetHash2();

        // Only Populate if it is active
        if (isActive()) {
            if (hsh1 != null) {
                Iterator it = hsh1.keySet().iterator();
                while (it.hasNext()) {
                    String strFlagCode = (String) it.next();
                    if (((Boolean) hsh1.get(strFlagCode)).booleanValue()) {
                        putPDHFlag(strFlagCode);
                    }
                }
            }

            if (hsh2 != null) {
                Iterator it = hsh2.keySet().iterator();
                while (it.hasNext()) {
                    String strFlagCode = (String) it.next();
                    if (((Boolean) hsh2.get(strFlagCode)).booleanValue()) {
                        putPDHFlag(strFlagCode);
                    }
                }
            }

        }
    }

    /**
    *  validates that the flagcode was is the curret 'get' of the object
    *  Description of the Method
    *
    *@param  _amf      Description of the Parameter
    *@param  _strFlag  Description of the Parameter
    *@return           Description of the Return Value
    */
    private boolean allowedValue(MetaFlag[] _amf, String _strFlag) {

        for (int ii = 0; ii < _amf.length; ii++) {
            if (_amf[ii].getFlagCode().equals(_strFlag)) {
                return true;
            }
        }

        return false;
    }

    /**
    *  Gets the pDHHashtable attribute of the EANFlagAttribute object
    *
    *@return    The pDHHashtable value
    */
    public Hashtable getPDHHashtable() {
        if (m_hsh1 == null) {
            return new Hashtable();
        }
        return m_hsh1;
    }

    /**
     * getFlagCodes
     *
     * @return
     *  @author David Bigelow
     */
    public String getFlagCodes() {

        String strReturn = "";
        boolean bFirstTime = true;

        if (!isActive()) {
            return strReturn;
        }

        if (m_hsh1 != null) {
            Iterator it = m_hsh1.keySet().iterator();
            while (it.hasNext()) {
                String strFlagCode = (String) it.next();
                if (((Boolean) m_hsh1.get(strFlagCode)).booleanValue()) {
                    if (bFirstTime) {
                        strReturn = strFlagCode;
                        bFirstTime = false;
                    } else {
                        strReturn = strReturn + ":" + strFlagCode;
                    }
                }
            }
        }

        if (m_hsh2 != null) {
            Iterator it = m_hsh2.keySet().iterator();
            while (it.hasNext()) {
                String strFlagCode = (String) it.next();
                if (((Boolean) m_hsh2.get(strFlagCode)).booleanValue()) {
                    if (bFirstTime) {
                        strReturn = strFlagCode;
                        bFirstTime = false;
                    } else {
                        strReturn = strReturn + ":" + strFlagCode;
                    }
                }
            }
        }

        return strReturn;

    }

    /**
     * (non-Javadoc)
     * duplicate
     *
     * @see COM.ibm.eannounce.objects.EANAttribute#duplicate(java.lang.Object)
     */
    protected void duplicate(Object _aMetaFlag) {

        MetaFlag[] amf = null;

        getMetaAttribute();
        get();
        getParent();

        if (_aMetaFlag == null) {
            return;
        }

        if (!(_aMetaFlag instanceof MetaFlag[])) {
            D.ebug(D.EBUG_SPEW,"EANFlagAttributge.put(Object).***You are not putting an array of MetaFlagss");
            return;
        }

        setActive(true);

        amf = (MetaFlag[]) _aMetaFlag;

        for (int i = 0; i < amf.length; i++) {
            MetaFlag mf = amf[i];
            if (mf.isSelected()) {
                String strFlagCode = mf.getFlagCode();
                put(strFlagCode, true);
            }
        }
    }

}
