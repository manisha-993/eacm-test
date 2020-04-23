//javac -classpath $HOME/abrgenerator/script/ibmwebas.jar:.:./db2java.zip:/usr/java_dev2/jre/lib/rt.jar:$HOME/generator/source/middleware.jar PokBaseABR.java > errors
//
// (c) Copyright International Business Machines Corporation, 2001
// All Rights Reserved.
//
//  $Log: PokBaseABR.java,v $
//  Revision 1.216  2013/03/29 06:48:56  wangyulo
//  support RESEND from CACHE and support initialize CACHE after IDL base on BH FS ABR XML System Feed 20121210.doc
//
//  Revision 1.215  2012/09/30 07:08:57  guobin
//  fix defect 799135 ( RFR data now flowing to BH prod)
//
//  Revision 1.214  2011/08/10 12:31:27  wendy
//  tie log messages to debug level
//
//  Revision 1.213  2008/11/06 16:25:58  wendy
//  Release memory
//
//  Revision 1.212  2008/02/19 17:18:25  wendy
//  Cleanup RSA warnings
//
//  Revision 1.211  2007/03/14 01:03:56  bala
//  Add getAttributeLongFlagDesc
//
//  Revision 1.210  2006/02/22 19:31:41  joan
//  fixes
//
//  Revision 1.209  2006/01/22 18:27:56  joan
//  add NEW_LINE static
//
//  Revision 1.208  2005/02/16 18:18:28  bala
//  correct filebreaktag as a gml comment
//
//  Revision 1.207  2005/02/09 16:32:12  bala
//  add method setFileBreakTag
//
//  Revision 1.206  2005/01/10 18:24:01  roger
//  Make abr profile display grep'able
//
//  Revision 1.205  2004/10/15 21:13:26  bala
//  fix A003 message
//
//  Revision 1.204  2004/10/15 19:44:48  bala
//  fix A0002 and A0002 messages
//
//  Revision 1.203  2004/10/14 21:39:31  bala
//  fix for A0002 and A0003
//
//  Revision 1.202  2004/09/29 00:09:44  joan
//  add new abr
//
//  Revision 1.201  2004/09/28 16:50:24  joan
//  add new abrs
//
//  Revision 1.200  2004/09/21 22:51:53  joan
//  fixes
//
//  Revision 1.199  2004/05/12 22:25:35  joan
//  adjust getParent
//
//  Revision 1.198  2004/05/10 21:30:24  joan
//  change method setAttributeValue from private to public
//
//  Revision 1.197  2004/04/08 15:24:49  joan
//  CR7031
//
//  Revision 1.196  2004/03/25 21:25:43  joan
//  fix bug
//
//  Revision 1.195  2004/03/23 00:00:07  joan
//  add method
//
//  Revision 1.194  2004/01/20 21:37:06  joan
//  null pointer
//
//  Revision 1.193  2004/01/20 21:21:43  joan
//  null pointer
//
//  Revision 1.192  2003/12/31 01:47:42  bala
//  supress null value return in getAttributeFlagenabledvalue
//
//  Revision 1.191  2003/12/30 22:35:03  bala
//  changing sortentities signature to public
//
//  Revision 1.190  2003/12/30 22:24:26  bala
//  sorting entities based on attribute array
//
//  Revision 1.189  2003/11/12 00:35:15  joan
//  put refresh method
//
//  Revision 1.188  2003/10/22 17:25:53  bala
//  better error messages in getaTtributevalue, getattributeFlagEnabledValue
//
//  Revision 1.187  2003/10/17 21:20:53  joan
//  fb fixes
//
//  Revision 1.186  2003/10/10 21:14:39  bala
//  add method modifier to getAttributeShortFlagDesc
//
//  Revision 1.185  2003/10/10 20:59:42  bala
//  change getAttributeFlagEnabledValue and getAttributeShortFlagDesc to accept entityitem
//
//  Revision 1.184  2003/10/01 22:21:36  bala
//  beautify, add comments
//
//  Revision 1.183  2003/10/01 18:26:09  bala
//  enhance getAttribvalue and flagvalueequals to accept entityItem
//
//  Revision 1.182  2003/09/23 22:58:19  joan
//  add changes
//
//  Revision 1.181  2003/09/18 16:29:57  joan
//  fix error
//
//  Revision 1.180  2003/09/11 22:24:09  joan
//  fb fixes
//
//  Revision 1.179  2003/08/26 15:52:41  minhthy
//  syntax
//
//
//  Revision 1.177  2003/08/08 15:50:09  bala
//  fixed bug in getParentEntityIds
//
//  Revision 1.176  2003/08/05 17:31:03  bala
//  fix getParentEntityIds() and getChildrenEntityIds() methods
//  to get root entitytype when parm passed is of root entity
//
//  Revision 1.175  2003/07/31 21:08:21  bala
//  fix syntax
//
//  Revision 1.174  2003/07/31 19:47:56  bala
//  more fixes to printattribute
//
//  Revision 1.173  2003/07/31 16:45:02  yang
//  fix feedback 51598 null pointer error.
//
//  Revision 1.172  2003/07/14 21:54:04  yang
//  Adding values to c_hshManagementChildrenGroupEntities
//
//  Revision 1.171  2003/07/14 19:59:42  yang
//  syntax
//
//  Revision 1.170  2003/07/14 19:54:47  yang
//  updated checkS0001 to include Grandchild
//
//  Revision 1.169  2003/07/10 21:44:13  yang
//  Updated S0001 status
//
//  Revision 1.168  2003/07/09 23:44:28  bala
//  move out lock attributes out of the extractactionitem generate
//  condition
//
//  Revision 1.167  2003/07/09 23:19:24  bala
//  Add debug messages
//
//  Revision 1.166  2003/07/09 23:12:49  bala
//  change start_ABRBuild signature so that ExtractAction Item
//  generation can by bypassed
//
//  Revision 1.165  2003/07/09 21:31:21  dave
//  fixing sintx
//
//  Revision 1.164  2003/07/08 17:14:42  dave
//  fixing getParentEntityID's
//
//  Revision 1.163  2003/06/27 21:54:04  minhthy
//  changed A0002
//
//  Revision 1.162  2003/06/27 20:20:48  minhthy
//  changed A0002
//
//  Revision 1.161  2003/06/27 18:12:07  minhthy
//  systax
//
//  Revision 1.160  2003/06/27 17:36:27  minhthy
//  minor updated V0002
//
//  Revision 1.159  2003/06/27 14:57:18  yang
//  Updated A0003
//
//  Revision 1.158  2003/06/20 21:51:11  dave
//  adding more trace
//
//  Revision 1.157  2003/06/19 20:15:05  dave
//  added some trace
//
//  Revision 1.156  2003/06/18 15:37:23  dave
//  syntax
//
//  Revision 1.155  2003/06/18 15:30:39  dave
//  some syntax fixing
//
//  Revision 1.154  2003/06/18 15:20:41  dave
//  Adding new M0005 modifier
//
//  Revision 1.153  2003/06/18 01:38:19  dave
//  fix to print entity routine to also look in
//  the parent entity group
//
//  Revision 1.152  2003/06/18 01:22:34  dave
//  remove null as first value.. if first was not selected
//
//  Revision 1.151  2003/06/18 01:12:23  dave
//  syntax
//
//  Revision 1.150  2003/06/18 01:07:36  dave
//  added logging when ORDEROF is not passed
//  add a return message to the report
//
//  Revision 1.149  2003/06/11 19:51:04  minhthy
//  updated c_hshEntityStatus
//
//  Revision 1.148  2003/06/11 16:04:22  minhthy
//  add entities to c_hshEntityStatus
//
//  Revision 1.147  2003/06/10 20:54:49  dave
//  fixing feedback 51265
//
//  Revision 1.146  2003/06/10 17:43:21  dave
//  typo in hashtable value for COFOOF
//
//  Revision 1.145  2003/06/09 18:20:42  dave
//  found the null pointer
//
//  Revision 1.144  2003/06/09 17:53:57  bala
//  fix prettyprint to eliminate leading spaces
//
//  Revision 1.143  2003/06/09 17:04:56  dave
//  more fixes
//
//  Revision 1.142  2003/06/09 16:24:24  dave
//  trying to trap a null pointer
//
//  Revision 1.141  2003/06/07 00:04:36  yang
//  Adding RULES & SHIPINGFO to hashtable
//
//  Revision 1.140  2003/06/06 23:55:02  yang
//  Adding PUBLICATION to hashtable
//
//  Revision 1.139  2003/06/06 23:35:54  yang
//  Adding ORGANUNIT & PACKAGING & PRICEFININFO to hashtable
//
//  Revision 1.138  2003/06/06 23:18:20  yang
//  Adding IVOCAT & ORDERINFO to hashtable
//
//  Revision 1.137  2003/06/06 23:06:02  yang
//  Adding ENVIRINFO to hashtable
//
//  Revision 1.136  2003/06/06 22:56:02  yang
//  Adding CRYPTO to hashtable
//
//  Revision 1.135  2003/06/06 22:47:13  yang
//  Adding CHANNEL to hashtable
//
//  Revision 1.134  2003/06/06 22:37:48  dave
//  minor syntax fixes
//
//  Revision 1.133  2003/06/06 22:35:18  yang
//  Adding CATINCL to hashtable
//
//  Revision 1.132  2003/06/06 22:31:10  dave
//  syntax
//
//  Revision 1.131  2003/06/06 22:26:36  dave
//  implementing V0002
//
//  Revision 1.130  2003/06/06 22:24:55  yang
//  Additional fix on BPEXHIBIT
//
//  Revision 1.129  2003/06/06 22:07:54  yang
//  Adding BPEXHIBIT to hashtable
//
//  Revision 1.128  2003/06/06 22:00:08  bala
//  more debug statements
//
//  Revision 1.127  2003/06/06 21:38:21  bala
//  more debug
//
//  Revision 1.126  2003/06/06 21:16:52  dave
//  adding V0001
//
//  Revision 1.125  2003/06/06 21:08:52  bala
//  putting debug statements for flagvalueequals
//
//  Revision 1.124  2003/06/06 21:07:16  dave
//  syntax
//
//  Revision 1.123  2003/06/06 21:04:19  dave
//  added H0006
//
//  Revision 1.122  2003/06/06 20:48:07  dave
//  final fixes made to H0003
//
//  Revision 1.121  2003/06/06 20:45:23  dave
//  minor fixes
//
//  Revision 1.120  2003/06/06 20:41:32  dave
//  adding check H0003 I
//
//  Revision 1.119  2003/06/06 20:00:44  dave
//  nextstatus call failure
//
//  Revision 1.118  2003/06/06 19:57:43  dave
//  missing parens
//
//  Revision 1.117  2003/06/06 19:55:33  dave
//  minor fix to M0001
//
//  Revision 1.116  2003/06/06 19:20:58  dave
//  adding avail to the hashtables
//
//  Revision 1.115  2003/06/06 17:44:35  dave
//  minor syntax fix neect '+'
//
//  Revision 1.114  2003/06/06 17:41:22  dave
//  working on A0003
//
//  Revision 1.113  2003/06/06 17:11:26  dave
//  fix to A0002, A0003 (using eiChild)
//
//  Revision 1.112  2003/06/06 16:50:26  dave
//  syntax fix
//
//  Revision 1.111  2003/06/06 16:45:43  dave
//  needs to fail once in a while A0001
//
//  Revision 1.110  2003/06/06 16:44:15  dave
//  child / grandchild split of M0004
//
//  Revision 1.109  2003/06/06 15:14:24  minhthy
//  changed entity "COF" TO "COMMERCIALOF" in checkM0004
//
//  Revision 1.108  2003/06/05 22:37:03  dave
//  syntax
//
//  Revision 1.107  2003/06/05 22:34:13  dave
//  jj to ij
//
//  Revision 1.106  2003/06/05 22:21:49  dave
//  syntax
//
//  Revision 1.105  2003/06/05 22:16:15  dave
//  missing a paren
//
//  Revision 1.104  2003/06/05 22:13:19  dave
//  adding A0002, A0001, AND A0003
//
//  Revision 1.103  2003/06/05 21:43:15  dave
//  return needed to be changes from void
//
//  Revision 1.102  2003/06/05 21:38:54  dave
//  adding A0001 rule
//
//  Revision 1.101  2003/06/05 19:54:38  dave
//  added more info to S0002 checking.. is it unrestricted .. or restricted?
//
//  Revision 1.100  2003/06/05 19:11:07  dave
//  trying to trap nullpointer
//
//  Revision 1.99  2003/06/05 18:29:07  dave
//  fix
//
//  Revision 1.98  2003/06/05 18:21:57  dave
//  some minor fixes
//
//  Revision 1.97  2003/06/05 18:15:00  dave
//  trying to fix the change report sometimes showing two values
//
//  Revision 1.96  2003/06/05 18:03:01  minhthy
//  Updated checkS0002
//
//  Revision 1.95  2003/06/05 17:53:39  dave
//  Small typo in hashtable
//
//  Revision 1.94  2003/06/05 17:41:23  dave
//  hshTableFixes
//
//  Revision 1.93  2003/06/05 17:36:14  dave
//  fixed a save rep
//
//  Revision 1.92  2003/06/05 17:31:59  dave
//  fixed display null pointer
//
//  Revision 1.91  2003/06/05 17:17:23  minhthy
//  *** empty log message ***
//
//  Revision 1.90  2003/06/05 17:10:21  dave
//  M0020 is now ready
//
//  Revision 1.89  2003/06/05 17:07:54  dave
//  more syntax fixes
//
//  Revision 1.88  2003/06/05 17:04:30  dave
//  whoops
//
//  Revision 1.87  2003/06/05 17:01:52  dave
//  fixing M0020
//
//  Revision 1.86  2003/06/05 16:34:12  dave
//  more fixes to syntax
//
//  Revision 1.85  2003/06/05 16:30:06  dave
//  need some declarations
//
//  Revision 1.84  2003/06/05 16:25:53  dave
//  syntax fix
//
//  Revision 1.83  2003/06/05 16:20:00  dave
//  going after COF...
//
//  Revision 1.82  2003/06/05 04:30:40  dave
//  trying to move on M0020 - Spec is not clear
//
//  Revision 1.81  2003/06/05 04:24:07  dave
//  checking in
//
//  Revision 1.80  2003/06/05 04:19:24  dave
//  some minor issues on syntax
//
//  Revision 1.79  2003/06/05 04:15:41  dave
//  putting in M0003
//
//  Revision 1.78  2003/06/05 03:55:35  dave
//  need a return type
//
//  Revision 1.77  2003/06/05 03:53:08  dave
//  adding M0002
//
//  Revision 1.76  2003/06/05 03:41:50  dave
//  syntax
//
//  Revision 1.75  2003/06/05 03:39:37  dave
//  attempting to do M0001
//
//  Revision 1.74  2003/06/05 03:09:31  dave
//  more syn
//
//  Revision 1.73  2003/06/05 03:05:46  dave
//  minor Change to S0001 to pass EntityItem ... Not EntityGroup
//
//  Revision 1.72  2003/06/05 03:01:41  dave
//  more typo
//
//  Revision 1.71  2003/06/05 03:00:00  dave
//  more consolidation
//
//  Revision 1.70  2003/06/05 02:51:04  dave
//  not enough )
//
//  Revision 1.69  2003/06/05 02:46:58  dave
//  more hashtable control
//
//  Revision 1.68  2003/06/05 02:32:39  dave
//  more simplification
//
//  Revision 1.67  2003/06/05 02:29:37  dave
//  adding more stuff to know about the entities
//
//  Revision 1.66  2003/06/05 02:24:56  dave
//  last mohikan
//
//  Revision 1.65  2003/06/05 02:22:20  dave
//  more updates
//
//  Revision 1.64  2003/06/05 02:17:09  dave
//  more status simplification
//
//  Revision 1.63  2003/06/05 02:10:12  dave
//  more simplifications
//
//  Revision 1.62  2003/06/05 02:06:43  dave
//  Status Attribute Checking
//
//  Revision 1.61  2003/06/05 02:01:44  dave
//  more Status code simplifications
//
//  Revision 1.60  2003/06/05 01:18:52  dave
//  foundit.. backing out t race
//
//  Revision 1.59  2003/06/05 01:08:06  dave
//  fix to si
//
//  Revision 1.58  2003/06/05 01:05:57  dave
//  more trace
//
//  Revision 1.57  2003/06/05 00:49:30  dave
//  dump the parent child thing
//
//  Revision 1.56  2003/06/05 00:30:29  dave
//  dysnfunctions
//
//  Revision 1.55  2003/06/05 00:28:22  dave
//  more trace
//
//  Revision 1.54  2003/06/05 00:23:54  dave
//  more trace
//
//  Revision 1.53  2003/06/04 23:59:41  dave
//  final syn
//
//  Revision 1.52  2003/06/04 23:57:17  dave
//  synrt
//
//  Revision 1.51  2003/06/04 23:52:00  dave
//  trace and simplification for M0007
//
//  Revision 1.50  2003/06/04 23:11:49  dave
//  more fixes
//
//  Revision 1.49  2003/06/04 23:09:32  dave
//  checking more changes in
//
//  Revision 1.48  2003/06/04 23:00:57  dave
//  fixing more
//
//  Revision 1.47  2003/06/04 22:33:47  dave
//  cyntex
//
//  Revision 1.46  2003/06/04 22:30:54  dave
//  ski jump
//
//  Revision 1.45  2003/06/04 22:25:21  dave
//  more testing consolidations
//
//  Revision 1.44  2003/06/04 20:44:24  bala
//  removing log messages in flagvalueequals
//
//  Revision 1.43  2003/06/04 19:35:03  dave
//  more tread
//
//  Revision 1.42  2003/06/04 19:32:00  dave
//  retreading tires
//
//  Revision 1.41  2003/06/04 19:28:55  dave
//  big train wreck - here it goes
//
//  Revision 1.40  2003/06/04 18:37:20  dave
//  more fixes
//
//  Revision 1.39  2003/06/04 18:33:35  dave
//  syntax
//
//  Revision 1.38  2003/06/04 18:31:25  dave
//  more syntax fixes
//
//  Revision 1.37  2003/06/04 18:25:35  dave
//  syntax error fixes
//
//  Revision 1.36  2003/06/04 18:18:07  dave
//  more consolidations
//
//  Revision 1.35  2003/06/04 17:48:17  dave
//  adding M0004 check to
//  base
//
//  Revision 1.34  2003/06/04 15:51:19  dave
//  one more time
//
//  Revision 1.33  2003/06/04 15:49:13  dave
//  almost there
//
//  Revision 1.32  2003/06/04 15:44:28  dave
//  more fixes to syntax
//
//  Revision 1.31  2003/06/04 15:37:36  dave
//  more syntax
//
//  Revision 1.30  2003/06/04 15:34:30  dave
//  checking in syntax
//
//  Revision 1.29  2003/06/04 15:32:00  dave
//  commonizing POFPOF
//
//  Revision 1.28  2003/06/04 14:49:20  dave
//  more throws
//
//  Revision 1.27  2003/06/04 14:43:24  dave
//  throws issue fixed
//
//  Revision 1.26  2003/06/04 14:23:04  dave
//  syntax
//
//  Revision 1.25  2003/06/04 14:20:59  dave
//  more standardizing
//
//  Revision 1.24  2003/06/04 14:03:55  dave
//  syntax
//
//  Revision 1.23  2003/06/04 14:00:22  dave
//  syntax
//
//  Revision 1.22  2003/06/04 13:56:07  dave
//  commonizing change report
//
//  Revision 1.21  2003/06/04 05:37:56  dave
//  promoted M0008 into the pokbaseabr class
//
//  Revision 1.20  2003/06/04 05:07:42  dave
//  minor format changes
//
//  Revision 1.19  2003/06/04 05:01:51  dave
//  minor table change
//
//  Revision 1.18  2003/06/04 04:57:56  dave
//  more report fixing
//
//  Revision 1.17  2003/06/04 04:50:09  dave
//  making sure change report comes out properly
//
//  Revision 1.16  2003/06/04 04:37:13  dave
//  some final stuff for TECHCAPABILITY
//
//  Revision 1.15  2003/06/04 04:18:59  dave
//  triggering getNow
//
//  Revision 1.14  2003/06/04 04:00:50  dave
//  simplifying abit on try
//
//  Revision 1.13  2003/06/04 03:56:14  dave
//  founding up syntax
//
//  Revision 1.12  2003/06/04 03:50:46  dave
//  more static tricks
//
//  Revision 1.11  2003/06/04 03:48:57  dave
//  static fixes
//
//  Revision 1.10  2003/06/04 03:23:08  dave
//  more fixes
//
//  Revision 1.9  2003/06/04 03:16:01  dave
//  more fixes
//
//  Revision 1.8  2003/06/04 02:05:26  dave
//  cleaning up banner for correct version control
//
//  Revision 1.7  2003/06/04 01:53:07  dave
//  more commonization
//
//  Revision 1.6  2003/06/04 01:45:48  dave
//  more commonizing on routines
//
//  Revision 1.5  2003/06/03 23:36:35  dave
//  syntax
//
//  Revision 1.4  2003/06/03 23:32:25  dave
//  syntax
//
//  Revision 1.3  2003/06/03 23:28:35  dave
//  commonizing setControlBlock
//
//  Revision 1.2  2003/06/03 23:16:14  dave
//  massive reorg for common routines
//
//  Revision 1.1.1.1  2003/06/03 19:02:25  dave
//  new 1.1.1 abr
//
//  Revision 1.89  2003/06/03 18:27:21  dave
//  removing final for now
//
//  Revision 1.88  2003/06/03 18:17:39  dave
//  preping for common T1 and T2 processing
//
//  Revision 1.87  2003/06/03 18:12:30  dave
//  commonizing routines
//
//  Revision 1.86  2003/05/22 22:18:01  bala
//  drop commit
//
//  Revision 1.85  2003/05/22 01:27:18  bala
//  commit checkpoint
//
//  Revision 1.84  2003/05/14 19:23:46  bala
//  fixes
//
//  Revision 1.83  2003/05/08 18:48:58  bala
//  fix Flagvaluequals
//
//  Revision 1.82  2003/05/06 16:31:48  bala
//  added errorchecking in flagvalueEquals
//
//  Revision 1.81  2003/04/22 17:27:08  bala
//  fixes
//
//  Revision 1.80  2003/03/04 17:59:25  bala
//  correct cutting and pasting problem
//
//  Revision 1.79  2003/03/04 17:58:34  bala
//  buildReportHeader now accepts a string array parameter
//
//  Revision 1.78  2003/02/27 22:29:13  bala
//  fix compile errors
//
//  Revision 1.77  2003/02/27 19:34:51  bala
//  Added error logging statements to getMetadescription
//  when entityGroup returns null.
//
//  Revision 1.76  2003/02/25 18:15:23  bala
//  Changed checkFlagValues to validate enabled flag value if
//  flag value supplied instead of translated value
//
//  Revision 1.75  2002/12/17 21:02:20  minhthy
//  fixed LockGroup
//
//  Revision 1.74  2002/12/12 21:43:17  minhthy
//  added printNavigateAttributes()
//
//  Revision 1.73  2002/12/07 00:52:55  bala
//  fixes
//
//  Revision 1.72  2002/11/27 23:42:19  minhthy
//  set bGoodLocks = true when m_slg.hasExclusiveLock()
//
//  Revision 1.71  2002/11/26 22:41:08  bala
//  fixes
//
//  Revision 1.70  2002/11/23 03:18:50  bala
//  fixes
//
//  Revision 1.69  2002/11/21 00:24:26  bala
//  fixes
//
//  Revision 1.68  2002/11/19 23:18:20  bala
//  Fix prettyprint
//
//  Revision 1.67  2002/11/14 23:54:37  bala
//  fix to pretty print
//
//  Revision 1.66  2002/11/14 00:46:20  bala
//  added getAttributeShortFlagDesc
//
//  Revision 1.65  2002/11/08 23:35:57  bala
//  fixes to pretty print...couldnt handle strings = maxlen+1
//
//  Revision 1.64  2002/11/08 00:21:00  bala
//  use stringtokenizer for pretty print
//
//  Revision 1.63  2002/11/07 01:56:08  bala
//  added prettyPrint method
//
//  Revision 1.62  2002/11/06 17:31:42  bala
//  remove replace of carriage returns for non flag values in getAttributeValue method
//
//  Revision 1.61  2002/11/05 23:56:41  bala
//  removed the replacing of /n with ' 'for getAttributeFlagEnabledValue
//
//  Revision 1.60  2002/10/31 00:48:13  naomi
//  fix setDGName
//
//  Revision 1.59  2002/10/31 00:43:58  naomi
//  added setDGName method
//
//  Revision 1.58  2002/10/31 00:24:44  bala
//  Added method to retrieve flag codes instead of their translated values
//
//  Revision 1.57  2002/10/18 22:38:28  bala
//  take out Carriage returns from getAttributeValue
//
//  Revision 1.56  2002/10/15 20:46:13  bala
//  minor changes
//
//  Revision 1.55  2002/09/24 21:19:40  bala
//  make getVersion method static to match AbstractTask
//
//  Revision 1.54  2002/09/24 21:09:15  bala
//  changed getVersion to static to match the method in AbstractTask
//
//  Revision 1.53  2002/09/19 19:43:26  bala
//  disable report header..use only in individual abr's
//
//  Revision 1.52  2002/09/04 22:09:53  bala
//  print role details before abr runs
//
//  Revision 1.51  2002/09/03 22:10:02  bala
//  changed taskmaster import path
//
//  Revision 1.50  2002/09/03 21:55:32  bala
//  disabling import of taskmaster package since its folded into middleware
//
//  Revision 1.49  2002/08/27 01:42:47  bala
//  change Constant to match abstract task
//
//  Revision 1.48  2002/08/08 23:01:06  bala
//  removed references to PDHTransitionException
//
//  Revision 1.47  2002/08/08 19:42:32  bala
//  remove getABRDescription method as its already declared
//  in abstract task
//
//  Revision 1.46  2002/08/08 19:30:34  bala
//  Remove check OPICMBusinessRuleException from where all used
//
//  Revision 1.45  2002/07/01 21:55:55  bala
//  fix
//
//  Revision 1.44  2002/07/01 21:49:10  bala
//  changed getSoftLock check whether lock is available
//
//  Revision 1.43  2002/06/28 23:02:23  bala
//  correct parm for clearlock
//
//  Revision 1.42  2002/06/28 23:01:35  bala
//  correcting parm for softlock
//
//  Revision 1.41  2002/06/28 22:56:31  bala
//  logError fix
//
//  Revision 1.40  2002/06/28 22:50:35  bala
//  Error Handling
//
//  Revision 1.39  2002/06/28 22:36:59  bala
//  fix getSoftLock problem
//
//  Revision 1.38  2002/06/28 17:15:13  bala
//  checking softlock after the VE was created
//
//  Revision 1.37  2002/06/28 16:56:28  bala
//  debug
//
//  Revision 1.36  2002/06/27 01:52:06  bala
//  added getChildEntityId
//
//  Revision 1.35  2002/06/27 00:57:59  bala
//  added int
//
//  Revision 1.34  2002/06/27 00:56:26  bala
//  fix
//
//  Revision 1.33  2002/06/27 00:54:17  bala
//  Added getParents method
//
//  Revision 1.32  2002/06/27 00:27:29  bala
//  fix
//
//  Revision 1.31  2002/06/27 00:06:27  bala
//  Added setAttributeValue method
//
//  Revision 1.30  2002/06/26 18:21:23  bala
//  typo
//
//  Revision 1.29  2002/06/26 18:19:44  bala
//  fix putDataToPDHGroup
//
//  Revision 1.28  2002/06/26 18:13:24  bala
//  add ;
//
//  Revision 1.27  2002/06/26 18:11:47  bala
//  more fixes to putDataToPDHGroup
//
//  Revision 1.26  2002/06/26 17:48:17  bala
//  fix putDataToPDHGroup
//
//  Revision 1.25  2002/06/26 16:15:39  bala
//  porting getSoftLock
//
//  Revision 1.24  2002/06/25 23:49:43  bala
//  added public keyword to postABRStatusToPDH
//
//  Revision 1.23  2002/06/22 19:09:50  bala
//  add getParentEntityId method
//
//  Revision 1.22  2002/06/22 18:46:31  bala
//  fix getRootEntitytype and ID
//
//  Revision 1.21  2002/06/22 18:35:39  bala
//  fix
//
//  Revision 1.20  2002/06/22 18:29:40  bala
//  more fixes
//
//  Revision 1.19  2002/06/22 18:20:05  bala
//  fix getRootEntityId to getRootEntityID
//
//  Revision 1.18  2002/06/22 18:16:43  bala
//  added getPathCVOF,getPathDIVtoPR,getPathCVSL,getPathPCSL
//
//  Revision 1.17  2002/06/21 21:35:47  bala
//  fix getAllEntities
//
//  Revision 1.16  2002/06/21 21:32:41  bala
//  fix getAllLinkedChildren
//
//  Revision 1.15  2002/06/21 21:29:48  bala
//  fix kbcheck
//
//  Revision 1.14  2002/06/21 21:23:43  bala
//  remove static keyword
//
//  Revision 1.13  2002/06/21 21:18:48  bala
//  add kbCheck
//
//  Revision 1.12  2002/06/21 20:49:45  bala
//  fix getPathCSOL
//
//  Revision 1.11  2002/06/21 20:20:47  bala
//  added new signature for getPathCPSL
//
//  Revision 1.10  2002/06/21 20:17:53  bala
//  fix
//
//  Revision 1.9  2002/06/21 20:14:17  bala
//  remove static keyword
//
//  Revision 1.8  2002/06/21 20:12:42  bala
//  fix
//
//  Revision 1.7  2002/06/21 18:42:18  bala
//  more adds
//
//  Revision 1.6  2002/06/21 17:42:06  bala
//  fix
//
//  Revision 1.5  2002/06/21 17:39:21  bala
//  Add more stuff
//
//  Revision 1.4  2002/06/20 22:54:55  bala
//  fix getPathDIVtoCSGR
//
//  Revision 1.3  2002/06/20 22:22:09  bala
//  fix to getPathDIVtoSG
//
//  Revision 1.2  2002/06/20 22:16:24  bala
//  add more function
//
//  Revision 1.1.1.1  2002/06/20 16:16:59  bala
//  Creating ODS module
//
package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.taskmaster.*;


import java.lang.Integer;
import java.lang.String;
//import java.*;
import java.io.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 *  This is is the base ABR Object of which all other ABR's extend
 */
//$Log: PokBaseABR.java,v $
//Revision 1.216  2013/03/29 06:48:56  wangyulo
//support RESEND from CACHE and support initialize CACHE after IDL base on BH FS ABR XML System Feed 20121210.doc
//
/**
 *  Description of the Class
 *
 *@author     Administrator
 *@created    October 11, 2002
 */
public abstract class PokBaseABR extends AbstractTask implements PokABRMessages, PokLSExtRef, PokPSGExtRef {

    public static final String NEW_LINE = "\n";
  // used for return code to caller
  public final static int NONE = 0;
  public final static int PASS = 0;
  public final static int FAIL = -1;
  public final static int RESENDRFR = 1;
  public final static int RESENDCACHE = 2;
  public final static int UPDATE_ERROR = -2;
  public final static int INTERNAL_ERROR = -3;
  public final static String TOKEN_DELIMITER = "|";
  public final static String DEF_NOT_POPULATED_HTML = "<em>** Not Populated **</em>";

  //GENAREASELECTION
   public static final String GENREGION_US   =  "6197";  //Americas                     (US)
   public static final String GENREGION_EMEA =  "6198";  //Europe/Middle East/Africa    (EMEA)
   public static final String GENREGION_AP   =  "6199";  //Asia Pacific                 (AP)
   public static final String GENREGION_CCN  =  "6200";  //Canada and Caribbean North   (CCN)
   public static final String GENREGION_LAD  =  "6204";  //Latin America                (LAD)


   public static final Hashtable c_hshDraft = new Hashtable();
   public static final Hashtable c_hshCancel = new Hashtable();
   public static final Hashtable c_hshFinal = new Hashtable();
   public static final Hashtable c_hshReadyRev = new Hashtable();
   public static final Hashtable c_hshChangeRev= new Hashtable();
   public static final Hashtable c_hshChangeFinal = new Hashtable();
   public static final Hashtable c_hshEntityStatus = new Hashtable();
   public static final Hashtable c_hshManagementGroupRelators  = new Hashtable();
   public static final Hashtable c_hshManagementGroupEntities  = new Hashtable();
   public static final Hashtable c_hshManagementChildrenGroupEntities  = new Hashtable();

   public static final Hashtable c_hshSkipEntities = new Hashtable();
   public static final Hashtable c_hshDraftAll = new Hashtable();
   public static final Hashtable c_hshRFRAll = new Hashtable();
   public static final Hashtable c_hshCRRFRAll = new Hashtable();
   public static final Hashtable c_hshFinalAll = new Hashtable();
   public static final Hashtable c_hshCRFinalAll = new Hashtable();

   static {

      //OFSTATUS is shared among COMMERCIALOF, ORDEROF, PHYSICALOF
      // this is Draft
      c_hshDraftAll.put ("ANCYCLESTATUS","111");
      c_hshDraftAll.put ("COFCOFSTATUS","111");
      c_hshDraftAll.put ("COFOOFSTATUS","111");
      c_hshDraftAll.put ("OFSTATUS","111");
      c_hshDraftAll.put ("FUPSTATUS","111");
      c_hshDraftAll.put ("FUPFUPSTATUS","111");
      c_hshDraftAll.put ("FUPPOFSTATUS","6060");
      c_hshDraftAll.put ("OOFOOFSTATUS","6085");
      c_hshDraftAll.put ("POFPOFSTATUS","111");
      c_hshDraftAll.put ("TECHCAPSTATUS","111");
      c_hshDraftAll.put ("AVAILSTATUS","111");
      c_hshDraftAll.put ("BPESTATUS","111");
      c_hshDraftAll.put ("CATINCSTATUS","111");
      c_hshDraftAll.put ("CHANSTATUS","111");
      c_hshDraftAll.put ("CRYPTOSTATUS","111");
      c_hshDraftAll.put ("ENVISTATUS","111");
      c_hshDraftAll.put ("IVOCATSTATUS","111");
      c_hshDraftAll.put ("ORDINFSTATUS","111");
      c_hshDraftAll.put ("OUSTATUS","111");
      c_hshDraftAll.put ("PACKSTATUS","111");
      c_hshDraftAll.put ("PRICESTATUS","111");
      c_hshDraftAll.put ("PUBSSTATUS","111");
      c_hshDraftAll.put ("RULESSTATUS","111");
      c_hshDraftAll.put ("SHIPSTATUS","111");

      //
      // OFSTATUS is shared among COMMERCIALOF, ORDEROF, PHYSICALOF
      // ANCYCLESTATUS does not have this
      // this is Ready for Review
      c_hshRFRAll.put ("COFCOFSTATUS","114");
      c_hshRFRAll.put ("COFOOFSTATUS","114");
      c_hshRFRAll.put ("OFSTATUS","115");
      c_hshRFRAll.put ("FUPSTATUS","114");
      c_hshRFRAll.put ("FUPFUPSTATUS","114");
      c_hshRFRAll.put ("FUPPOFSTATUS","6061");
      c_hshRFRAll.put ("OOFOOFSTATUS","6087");
      c_hshRFRAll.put ("POFPOFSTATUS","114");
      c_hshRFRAll.put ("TECHCAPSTATUS","114");
      c_hshRFRAll.put ("AVAILSTATUS","114");
      c_hshRFRAll.put ("BPESTATUS","114");
      c_hshRFRAll.put ("CATINCSTATUS","114");
      c_hshRFRAll.put ("CHANSTATUS","114");
      c_hshRFRAll.put ("CRYPTOSTATUS","114");
      c_hshRFRAll.put ("ENVISTATUS","114");
      c_hshRFRAll.put ("IVOCATSTATUS","114");
      c_hshRFRAll.put ("ORDINFSTATUS","114");
      c_hshRFRAll.put ("OUSTATUS","114");
      c_hshRFRAll.put ("PACKSTATUS","114");
      c_hshRFRAll.put ("PRICESTATUS","114");
      c_hshRFRAll.put ("PUBSSTATUS","114");
      c_hshRFRAll.put ("RULESSTATUS","114");
      c_hshRFRAll.put ("SHIPSTATUS","114");


      // OFSTATUS is shared among COMMERCIALOF, ORDEROF, PHYSICALOF
      // ANCYCLESTATUS does not have this
      // this is Change(Ready for Review)
      c_hshCRRFRAll.put ("COFCOFSTATUS","115");
      c_hshCRRFRAll.put ("COFOOFSTATUS","115");
      c_hshCRRFRAll.put ("OFSTATUS","116");
      c_hshCRRFRAll.put ("FUPSTATUS","115");
      c_hshCRRFRAll.put ("FUPFUPSTATUS","115");
      c_hshCRRFRAll.put ("FUPPOFSTATUS","6058");
      c_hshCRRFRAll.put ("OOFOOFSTATUS","6084");
      c_hshCRRFRAll.put ("POFPOFSTATUS","115");
      c_hshCRRFRAll.put ("TECHCAPSTATUS","115");
      c_hshCRRFRAll.put ("AVAILSTATUS","115");
      c_hshCRRFRAll.put ("BPESTATUS","115");
      c_hshCRRFRAll.put ("CATINCSTATUS","115");
      c_hshCRRFRAll.put ("CHANSTATUS","115");
      c_hshCRRFRAll.put ("CRYPTOSTATUS","115");
      c_hshCRRFRAll.put ("ENVISTATUS","115");
      c_hshCRRFRAll.put ("IVOCATSTATUS","115");
      c_hshCRRFRAll.put ("ORDINFSTATUS","115");
      c_hshCRRFRAll.put ("OUSTATUS","115");
      c_hshCRRFRAll.put ("PACKSTATUS","115");
      c_hshCRRFRAll.put ("PRICESTATUS","115");
      c_hshCRRFRAll.put ("PUBSSTATUS","115");
      c_hshCRRFRAll.put ("RULESSTATUS","115");
      c_hshCRRFRAll.put ("SHIPSTATUS","115");


      // OFSTATUS is shared among COMMERCIALOF, ORDEROF, PHYSICALOF
      // ANCYCLESTATUS does not have this
      // this is Final
      c_hshFinalAll.put ("COFCOFSTATUS","112");
      c_hshFinalAll.put ("COFOOFSTATUS","112");
      c_hshFinalAll.put ("OFSTATUS","112");
      c_hshFinalAll.put ("FUPSTATUS","112");
      c_hshFinalAll.put ("FUPFUPSTATUS","112");
      c_hshFinalAll.put ("FUPPOFSTATUS","6059");
      c_hshFinalAll.put ("OOFOOFSTATUS","6086");
      c_hshFinalAll.put ("POFPOFSTATUS","112");
      c_hshFinalAll.put ("TECHCAPSTATUS","112");
      c_hshFinalAll.put ("AVAILSTATUS","112");
      c_hshFinalAll.put ("BPESTATUS","112");
      c_hshFinalAll.put ("CATINCSTATUS","112");
      c_hshFinalAll.put ("CHANSTATUS","112");
      c_hshFinalAll.put ("CRYPTOSTATUS","112");
      c_hshFinalAll.put ("ENVISTATUS","112");
      c_hshFinalAll.put ("IVOCATSTATUS","112");
      c_hshFinalAll.put ("ORDINFSTATUS","112");
      c_hshFinalAll.put ("OUSTATUS","112");
      c_hshFinalAll.put ("PACKSTATUS","112");
      c_hshFinalAll.put ("PRICESTATUS","112");
      c_hshFinalAll.put ("PUBSSTATUS","112");
      c_hshFinalAll.put ("RULESSTATUS","112");
      c_hshFinalAll.put ("SHIPSTATUS","112");


     // OFSTATUS is shared among COMMERCIALOF, ORDEROF, PHYSICALOF
      // ANCYCLESTATUS does not have this
      // this is Change(Final)
      c_hshCRFinalAll.put ("COFCOFSTATUS","116");
      c_hshCRFinalAll.put ("COFOOFSTATUS","116");
      c_hshCRFinalAll.put ("OFSTATUS","113");
      c_hshCRFinalAll.put ("FUPSTATUS","116");
      c_hshCRFinalAll.put ("FUPFUPSTATUS","116");
      c_hshCRFinalAll.put ("FUPPOFSTATUS","6062");
      c_hshCRFinalAll.put ("OOFOOFSTATUS","6083");
      c_hshCRFinalAll.put ("POFPOFSTATUS","116");
      c_hshCRFinalAll.put ("TECHCAPSTATUS","116");
      c_hshCRFinalAll.put ("AVAILSTATUS","116");
      c_hshCRFinalAll.put ("BPESTATUS","116");
      c_hshCRFinalAll.put ("CATINCSTATUS","116");
      c_hshCRFinalAll.put ("CHANSTATUS","116");
      c_hshCRFinalAll.put ("CRYPTOSTATUS","116");
      c_hshCRFinalAll.put ("ENVISTATUS","116");
      c_hshCRFinalAll.put ("IVOCATSTATUS","116");
      c_hshCRFinalAll.put ("ORDINFSTATUS","116");
      c_hshCRFinalAll.put ("OUSTATUS","116");
      c_hshCRFinalAll.put ("PACKSTATUS","116");
      c_hshCRFinalAll.put ("PRICESTATUS","116");
      c_hshCRFinalAll.put ("PUBSSTATUS","116");
      c_hshCRFinalAll.put ("RULESSTATUS","116");
      c_hshCRFinalAll.put ("SHIPSTATUS","116");


      c_hshSkipEntities.put("OP","HI");

      c_hshManagementGroupEntities.put("COFOOFMGMTGRP","HI");
      c_hshManagementGroupEntities.put("COFCOFMGMTGRP","HI");
      c_hshManagementGroupEntities.put("FUPFUPMGMTGRP","HI");
      c_hshManagementGroupEntities.put("FUPPOFMGMTGRP","HI");
      c_hshManagementGroupEntities.put("OOFOOFMGMTGRP","HI");
      c_hshManagementGroupEntities.put("POFPOFMGMTGRP","HI");

      c_hshManagementChildrenGroupEntities.put("COMMERCIALOF","HI");
      c_hshManagementChildrenGroupEntities.put("RULES","HI");
      c_hshManagementChildrenGroupEntities.put("AVAIL","HI");
      c_hshManagementChildrenGroupEntities.put("ORDEROF","HI");
      c_hshManagementChildrenGroupEntities.put("PHYSICALOF","HI");
      c_hshManagementChildrenGroupEntities.put("FUP","HI");

      c_hshEntityStatus.put("COFOOFMGMTGRP","COFOOFSTATUS");
      c_hshEntityStatus.put("COFCOFMGMTGRP","COFCOFSTATUS");
      c_hshEntityStatus.put("COMMERCIALOF","OFSTATUS");
      c_hshEntityStatus.put("FUP","FUPSTATUS");
      c_hshEntityStatus.put("FUPFUPMGMTGRP","FUPFUPSTATUS");
      c_hshEntityStatus.put("FUPPOFMGMTGRP","FUPPOFSTATUS");
      c_hshEntityStatus.put("OOFOOFMGMTGRP","OOFOOFSTATUS");
      c_hshEntityStatus.put("ORDEROF","OFSTATUS");
      c_hshEntityStatus.put("PHYSICALOF","OFSTATUS");
      c_hshEntityStatus.put("POFPOFMGMTGRP","POFPOFSTATUS");
      c_hshEntityStatus.put("TECHCAPABILITY","TECHCAPSTATUS");
      c_hshEntityStatus.put("AVAIL","AVAILSTATUS");
      c_hshEntityStatus.put("BPEXHIBIT","BPESTATUS");
      c_hshEntityStatus.put("CATINCL","CATINCSTATUS");
      c_hshEntityStatus.put("CHANNEL","CHANSTATUS");
      c_hshEntityStatus.put("CRYPTO","CRYPTOSTATUS");
      c_hshEntityStatus.put("ENVIRINFO","ENVISTATUS");
      c_hshEntityStatus.put("IVOCAT","IVOCATSTATUS");
      c_hshEntityStatus.put("ORDERINFO","ORDINFSTATUS");
      c_hshEntityStatus.put("ORGANUNIT","OUSTATUS");
      c_hshEntityStatus.put("PACKAGING","PACKSTATUS");
      c_hshEntityStatus.put("PRICEFININFO","PRICESTATUS");
      c_hshEntityStatus.put("PUBLICATION","PUBSSTATUS");
      c_hshEntityStatus.put("RULES","RULESSTATUS");
      c_hshEntityStatus.put("SHIPINFO","SHIPSTATUS");
      c_hshEntityStatus.put ("ANNAREAMKTINFO","ANAMISTATUS");
      c_hshEntityStatus.put ("ANNDELIVERABLE","ANDELIVSTATUS");
      c_hshEntityStatus.put ("ANNDESCAREA","ANDESCASTATUS");
      c_hshEntityStatus.put ("ANNOUNCEMENT","ANCYCLESTATUS");
      c_hshEntityStatus.put ("CONFIGURATOR","CONFSTATUS");
      c_hshEntityStatus.put ("EDUCATION","EDSTATUS");
      c_hshEntityStatus.put ("PDSQUESTIONS","PDSQSTATUS");
      c_hshEntityStatus.put ("SALESMANCHG","SALESMANSTATUS");
      c_hshEntityStatus.put ("STANDAMENDTEXT","SATSTATUS");



      // OFSTATUS Attribute & Transition
      c_hshDraft.put ("OFSTATUS:111","115");
      c_hshCancel.put("OFSTATUS:114","NA");
      c_hshFinal.put("OFSTATUS:112","NA");
      c_hshReadyRev.put("OFSTATUS:115","112");
      c_hshChangeRev.put("OFSTATUS:116","115");
      c_hshChangeFinal.put("OFSTATUS:113","112");


      // OOFOOFSTATUS Attribute & Transition
      c_hshDraft.put ("OOFOOFSTATUS:6085","6087");
      c_hshCancel.put("OOFOOFSTATUS:0000","NA");
      c_hshFinal.put("OOFOOFSTATUS:6086","NA");
      c_hshReadyRev.put("OOFOOFSTATUS:6087","6086");
      c_hshChangeRev.put("OOFOOFSTATUS:6084","6087");
      c_hshChangeFinal.put("OOFOOFSTATUS:6083","6086");


      // SHIPSTATUS Attribute & Transition
      c_hshDraft.put ("SHIPSTATUS:111","114");
      c_hshCancel.put("SHIPSTATUS:000","NA");
      c_hshFinal.put("SHIPSTATUS:112","NA");
      c_hshReadyRev.put("SHIPSTATUS:114","112");
      c_hshChangeRev.put("SHIPSTATUS:115","114");
      c_hshChangeFinal.put("SHIPSTATUS:116","112");


      // RULESSTATUS Attribute & Transition
      c_hshDraft.put ("RULESSTATUS:111","114");
      c_hshCancel.put("RULESSTATUS:000","NA");
      c_hshFinal.put("RULESSTATUS:112","NA");
      c_hshReadyRev.put("RULESSTATUS:114","112");
      c_hshChangeRev.put("RULESSTATUS:115","114");
      c_hshChangeFinal.put("RULESSTATUS:116","112");


      // PUBSSTATUS Attribute & Transition
      c_hshDraft.put ("PUBSSTATUS:111","114");
      c_hshCancel.put("PUBSSTATUS:000","NA");
      c_hshFinal.put("PUBSSTATUS:112","NA");
      c_hshReadyRev.put("PUBSSTATUS:114","112");
      c_hshChangeRev.put("PUBSSTATUS:115","114");
      c_hshChangeFinal.put("PUBSSTATUS:116","112");


      // PRICESTATUS Attribute & Transition
      c_hshDraft.put ("PRICESTATUS:111","114");
      c_hshCancel.put("PRICESTATUS:000","NA");
      c_hshFinal.put("PRICESTATUS:112","NA");
      c_hshReadyRev.put("PRICESTATUS:114","112");
      c_hshChangeRev.put("PRICESTATUS:115","114");
      c_hshChangeFinal.put("PRICESTATUS:116","112");


      // PACKSTATUS Attribute & Transition
      c_hshDraft.put ("PACKSTATUS:111","114");
      c_hshCancel.put("PACKSTATUS:000","NA");
      c_hshFinal.put("PACKSTATUS:112","NA");
      c_hshReadyRev.put("PACKSTATUS:114","112");
      c_hshChangeRev.put("PACKSTATUS:115","114");
      c_hshChangeFinal.put("PACKSTATUS:116","112");


      // OUSTATUS Attribute & Transition
      c_hshDraft.put ("OUSTATUS:111","114");
      c_hshCancel.put("OUSTATUS:000","NA");
      c_hshFinal.put("OUSTATUS:112","NA");
      c_hshReadyRev.put("OUSTATUS:114","112");
      c_hshChangeRev.put("OUSTATUS:115","114");
      c_hshChangeFinal.put("OUSTATUS:116","112");


      // ORDINFSTATUS Attribute & Transition
      c_hshDraft.put ("ORDINFSTATUS:111","114");
      c_hshCancel.put("ORDINFSTATUS:000","NA");
      c_hshFinal.put("ORDINFSTATUS:112","NA");
      c_hshReadyRev.put("ORDINFSTATUS:114","112");
      c_hshChangeRev.put("ORDINFSTATUS:115","114");
      c_hshChangeFinal.put("ORDINFSTATUS:116","112");


      // IVOCATSTATUS Attribute & Transition
      c_hshDraft.put ("IVOCATSTATUS:111","114");
      c_hshCancel.put("IVOCATSTATUS:000","NA");
      c_hshFinal.put("IVOCATSTATUS:112","NA");
      c_hshReadyRev.put("IVOCATSTATUS:114","112");
      c_hshChangeRev.put("IVOCATSTATUS:115","114");
      c_hshChangeFinal.put("IVOCATSTATUS:116","112");


      // ENVISTATUS Attribute & Transition
      c_hshDraft.put ("ENVISTATUS:111","114");
      c_hshCancel.put("ENVISTATUS:000","NA");
      c_hshFinal.put("ENVISTATUS:112","NA");
      c_hshReadyRev.put("ENVISTATUS:114","112");
      c_hshChangeRev.put("ENVISTATUS:115","114");
      c_hshChangeFinal.put("ENVISTATUS:116","112");


      // CRYPTOSTATUS Attribute & Transition
      c_hshDraft.put ("CRYPTOSTATUS:111","114");
      c_hshCancel.put("CRYPTOSTATUS:000","NA");
      c_hshFinal.put("CRYPTOSTATUS:112","NA");
      c_hshReadyRev.put("CRYPTOSTATUS:114","112");
      c_hshChangeRev.put("CRYPTOSTATUS:115","114");
      c_hshChangeFinal.put("CRYPTOSTATUS:116","112");


      // CHANSTATUS Attribute & Transition
      c_hshDraft.put ("CHANSTATUS:111","114");
      c_hshCancel.put("CHANSTATUS:000","NA");
      c_hshFinal.put("CHANSTATUS:112","NA");
      c_hshReadyRev.put("CHANSTATUS:114","112");
      c_hshChangeRev.put("CHANSTATUS:115","114");
      c_hshChangeFinal.put("CHANSTATUS:116","112");


      // CATINCSTATUS Attribute & Transition
      c_hshDraft.put ("CATINCSTATUS:111","114");
      c_hshCancel.put("CATINCSTATUS:000","NA");
      c_hshFinal.put("CATINCSTATUS:112","NA");
      c_hshReadyRev.put("CATINCSTATUS:114","112");
      c_hshChangeRev.put("CATINCSTATUS:115","114");
      c_hshChangeFinal.put("CATINCSTATUS:116","112");


      // BPESTATUS Attribute & Transition
      c_hshDraft.put ("BPESTATUS:111","114");
      c_hshCancel.put("BPESTATUS:000","NA");
      c_hshFinal.put("BPESTATUS:112","NA");
      c_hshReadyRev.put("BPESTATUS:114","112");
      c_hshChangeRev.put("BPESTATUS:115","114");
      c_hshChangeFinal.put("BPESTATUS:116","112");


      // AVAILSTATUS Attribute & Transition
      c_hshDraft.put ("AVAILSTATUS:111","114");
      c_hshCancel.put("AVAILSTATUS:000","NA");
      c_hshFinal.put("AVAILSTATUS:112","NA");
      c_hshReadyRev.put("AVAILSTATUS:114","112");
      c_hshChangeRev.put("AVAILSTATUS:115","114");
      c_hshChangeFinal.put("AVAILSTATUS:116","112");

      // POFPOFSTATUS Attribute & Transition
      c_hshDraft.put ("POFPOFSTATUS:111","114");
      c_hshCancel.put("POFPOFSTATUS:0000","NA");
      c_hshFinal.put("POFPOFSTATUS:112","NA");
      c_hshReadyRev.put("POFPOFSTATUS:114","112");
      c_hshChangeRev.put("POFPOFSTATUS:115","114");
      c_hshChangeFinal.put("POFPOFSTATUS:116","112");

      // TECHCAPSTATUS Attribute & Transition
      c_hshDraft.put ("TECHCAPSTATUS:111","114");
      c_hshCancel.put("TECHCAPSTATUS:0000","NA");
      c_hshFinal.put("TECHCAPSTATUS:112","NA");
      c_hshReadyRev.put("TECHCAPSTATUS:114","112");
      c_hshChangeRev.put("TECHCAPSTATUS:115","114");
      c_hshChangeFinal.put("TECHCAPSTATUS:116","112");

      // FUPSTATUS Attribute & Transition
      c_hshDraft.put ("FUPSTATUS:111","114");
      c_hshCancel.put("FUPSTATUS:0000","NA");
      c_hshFinal.put("FUPSTATUS:112","NA");
      c_hshReadyRev.put("FUPSTATUS:114","112");
      c_hshChangeRev.put("FUPSTATUS:115","114");
      c_hshChangeFinal.put("FUPSTATUS:116","112");

      // FUPFUPSTATUS Attribute & Transition
      c_hshDraft.put ("FUPFUPSTATUS:111","114");
      c_hshCancel.put("FUPFUPSTATUS:0000","NA");
      c_hshFinal.put("FUPFUPSTATUS:112","NA");
      c_hshReadyRev.put("FUPFUPSTATUS:114","112");
      c_hshChangeRev.put("FUPFUPSTATUS:115","114");
      c_hshChangeFinal.put("FUPFUPSTATUS:116","112");

      // COFCOFSTATUS Attribute & Transition
      c_hshDraft.put ("COFCOFSTATUS:111","114");
      c_hshCancel.put("COFCOFSTATUS:0000","NA");
      c_hshFinal.put("COFCOFSTATUS:112","NA");
      c_hshReadyRev.put("COFCOFSTATUS:114","112");
      c_hshChangeRev.put("COFCOFSTATUS:115","114");
      c_hshChangeFinal.put("COFCOFSTATUS:116","112");

      // COFOOFSTATUS Attribute & Transition
      c_hshDraft.put ("COFOOFSTATUS:111","114");
      c_hshCancel.put("COFOOFSTATUS:0000","NA");
      c_hshFinal.put("COFOOFSTATUS:112","NA");
      c_hshReadyRev.put("COFOOFSTATUS:114","112");
      c_hshChangeRev.put("COFOOFSTATUS:115","114");
      c_hshChangeFinal.put("COFOOFSTATUS:116","112");

      // FUPPOFSTATUS Attribute & Transition
      c_hshDraft.put ("FUPPOFSTATUS:6060","6061");
      c_hshCancel.put("FUPPOFSTATUS:0000","NA");
      c_hshFinal.put("FUPPOFSTATUS:6059","NA");
      c_hshReadyRev.put("FUPPOFSTATUS:6061","6059");
      c_hshChangeRev.put("FUPPOFSTATUS:6058","6061");
      c_hshChangeFinal.put("FUPPOFSTATUS:6062","6059");

   }

   //
   // What kind of flag is it?
   //

   public static boolean isDraft(EntityItem _ei, String _strFlagCode) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      return (_strFlagCode == null ? false : c_hshDraftAll.containsKey(strAttributeCode));
   }

   public static boolean isReadyForReview(EntityItem _ei, String _strFlagCode) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      return (_strFlagCode == null ? false : c_hshRFRAll.containsKey(strAttributeCode));
   }

   public static boolean isFinal(EntityItem _ei, String _strFlagCode) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      return (_strFlagCode == null ? false : c_hshFinalAll.containsKey(strAttributeCode));
   }

   public static boolean isChangeRev(EntityItem _ei, String _strFlagCode) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      return (_strFlagCode == null ? false : c_hshCRRFRAll.containsKey(strAttributeCode));
   }

   public static boolean isChangeFinal(EntityItem _ei, String _strFlagCode) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      return (_strFlagCode == null ? false : c_hshCRFinalAll.containsKey(strAttributeCode));
   }

   public static boolean isSkippable(EntityItem _ei) {
      return (_ei == null ? false : c_hshSkipEntities.containsKey(_ei.getEntityType()));
   }

   public static boolean isStatusableEntity(EntityItem _ei) {
      return (_ei == null ? false : c_hshEntityStatus.containsKey(_ei.getEntityType()));
   }

   public static boolean isManagementGroupEntity(EntityItem _ei) {
      return (_ei == null ? false : c_hshManagementGroupEntities.containsKey(_ei.getEntityType()));
   }

   public static boolean isManagementGroupChildrenEntity(EntityItem _ei) {
      return (_ei == null ? false : c_hshManagementChildrenGroupEntities.containsKey(_ei.getEntityType()));
   }

   public static String getStatusAttributeCode(EntityItem _ei) {
      return (String)c_hshEntityStatus.get(_ei.getEntityType());
   }

   public static boolean isDraft(EntityItem _ei) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      String strFlagCode = getFlagCode(_ei, strAttributeCode);
      return (strFlagCode == null ? false : c_hshDraft.containsKey(strAttributeCode + ":" + strFlagCode));
   }

   public static String getNextDraftState(EntityItem _ei) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      String strFlagCode = getFlagCode(_ei, strAttributeCode);
      return (strFlagCode == null ? null : (String)c_hshDraft.get(strAttributeCode + ":" + strFlagCode));
   }

   public static boolean isCancel(EntityItem _ei) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      String strFlagCode = getFlagCode(_ei, strAttributeCode);
      return (strFlagCode == null ? false : c_hshCancel.containsKey(strAttributeCode + ":" + strFlagCode));
   }

   public static String getNextCancelState(EntityItem _ei) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      String strFlagCode = getFlagCode(_ei, strAttributeCode);
      return (strFlagCode == null ? null : (String)c_hshCancel.get(strAttributeCode + ":" + strFlagCode));
   }

   public static boolean isReadyForReview(EntityItem _ei) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      String strFlagCode = getFlagCode(_ei, strAttributeCode);
      return (strFlagCode == null ? false : c_hshReadyRev.containsKey(strAttributeCode + ":" + strFlagCode));
   }

   public static String getNextReadyForReviewState(EntityItem _ei) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      String strFlagCode = getFlagCode(_ei, strAttributeCode);
      return (strFlagCode == null ? null : (String)c_hshReadyRev.get(strAttributeCode + ":" + strFlagCode));
   }

   public static boolean isFinal(EntityItem _ei) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      String strFlagCode = getFlagCode(_ei, strAttributeCode);
      return (strFlagCode == null ? false : c_hshFinal.containsKey(strAttributeCode + ":" + strFlagCode));
   }

   public static String getNextFinalState(EntityItem _ei) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      String strFlagCode = getFlagCode(_ei, strAttributeCode);
      return (strFlagCode == null ? null : (String)c_hshFinal.get(strAttributeCode + ":" + strFlagCode));
   }

   public static boolean isChangeRev(EntityItem _ei) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      String strFlagCode = getFlagCode(_ei, strAttributeCode);
      return (strFlagCode == null ? false : c_hshChangeRev.containsKey(strAttributeCode + ":" + strFlagCode));
   }

   public static String getNextChangeRevState(EntityItem _ei) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      String strFlagCode = getFlagCode(_ei, strAttributeCode);
      return (strFlagCode == null ? null : (String)c_hshChangeRev.get(strAttributeCode + ":" + strFlagCode));
   }

   public static boolean isChangeFinal(EntityItem _ei) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      String strFlagCode = getFlagCode(_ei, strAttributeCode);
      return (strFlagCode == null ? false : c_hshChangeFinal.containsKey(strAttributeCode + ":" + strFlagCode));
   }

   public static String getNextChangeFinalState(EntityItem _ei) {
      String strAttributeCode = getStatusAttributeCode(_ei);
      String strFlagCode = getFlagCode(_ei, strAttributeCode);
      return (strFlagCode == null ? null : (String)c_hshChangeFinal.get(strAttributeCode + ":" + strFlagCode));
   }


  // used to control relator search
  private final static int UNIQUE = 0;
  private final static int ONE_OR_MORE = 1;
  private final static int ONE_OR_LESS = 2;

  // private member variables

  private LockGroup m_slg = null;
 // private LockItem m_sli = null;
  private EntityGroup m_pdhg = null;
  private EntityItem m_pdhi = null;
  private int m_iReturnCode = NONE;

  private boolean m_bLocked = false;

  // Control vars ...
  private final static long PAUSE_TIME = 60000;        // wait for 1 minute
  private boolean m_bReadOnly = false;
  private boolean m_bVerbose = false;
  protected boolean m_bOutputAsList = false;           // control outputting messages as Lists
  protected PrintWriter m_pw = null;
  protected Database m_db = null;
  protected Profile m_prof = null;
  protected NLSItem m_nlsi = null;
  protected OPICMABRItem m_abri = null;
  protected String m_strABRSessionID = null;
  protected Hashtable m_htUpdates = new Hashtable();
  protected EntityList m_elist = null;
  protected String m_strDGSubmit = "";
  protected String m_strEpoch = "1980-01-01-00.00.00.000000";
  protected String m_strEndOfDay = null;
  protected String m_strForever = "9999-12-31-00.00.00.000000";
  protected String m_strToday = null;
  protected String m_strNow = null;

   protected ControlBlock m_cbOn = null;

  // Print path variables
  private Set m_hsetSkipType = new HashSet();
  private Hashtable m_hDisplay = new Hashtable();
  private int m_iErrors = 0;
  //private String m_strRootEntityType = null;

  /**
   *  Description of the Field
   */
  public final static int PRINT_DOWN_LEVEL = 15;

  // Abstract Methods that all ABR's must adhere to

  /**
   *  Gets the description attribute of the PokBaseABR object
   *
   *@return    The description value
   */
  public abstract String getDescription();
   public abstract String getABRVersion();

  protected String getABREntityDesc(String _strEntityType, int _iEntityID) {

    String id = "LSCURID";
    String title = "LSCURTITLE";

    if (_strEntityType==null) {
      logError("Passing Null _strEntityType parameter!...need a good one!");
      return null;
    }
    if (_strEntityType.equals("LSWWCC") || _strEntityType.equals("LSCC")) {
      id = "LSCRSID";
      title = "LSCRSTITLE";
    } else if (_strEntityType.equals("LSWW")) {
      id = "LSWWID";
      title = "LSWWTITLE";
    } else if (_strEntityType.equals("LSSC")) {
      id = "LSSCID";
      title = "LSSCTITLE";
    } else if (_strEntityType.equals("LSSEG")) {
      id = "LSSEGID";
      title = "LSSEGTITLE";
    } else if (_strEntityType.equals("LSPRG")) {
      id = "LSPRGID";
      title = "LSPRGTITLE";
    } else if (_strEntityType.equals("LSGRM")) {
      id = "LSGRMID";
      title = "LSGRMTITLE";
    } else if (_strEntityType.equals("LSRC")) {
      id = "LSRCID";
      title = "LSRCTITLE";
    }

    return getEntityDescription(_strEntityType) + " Code: " +
        getAttributeValue(_strEntityType, _iEntityID, id, DEF_NOT_POPULATED_HTML) + " " +
        getAttributeValue(_strEntityType, _iEntityID, title, DEF_NOT_POPULATED_HTML);
  }

  // Accesors

  // Date section..

  /**
   *  Gets the now attribute of the PokBaseABR object
   *
   *@return    The now value
   */
  protected final String getNow() {
    return m_strNow;
  }


  /**
   *  Gets the endOfDay attribute of the PokBaseABR object
   *
   *@return    The endOfDay value
   */
  protected final String getEndOfDay() {
    return m_strEndOfDay;
  }


  /**
   *  Gets the today attribute of the PokBaseABR object
   *
   *@return    The today value
   */
  protected final String getToday() {
    return m_strToday;
  }


  /**
   *  Gets the epoch attribute of the PokBaseABR object
   *
   *@return    The epoch value
   */
  protected final String getEpoch() {
    return m_strEpoch;
  }


  /**
   *  Gets the forever attribute of the PokBaseABR object
   *
   *@return    The forever value
   */
  protected final String getForever() {
    return m_strForever;
  }


  /**
   *  Gets the aBRReturnCode attribute of the PokBaseABR object
   *
   *@return    The aBRReturnCode value
   */
  public int getABRReturnCode() {
    return m_iReturnCode;
  }


  /*
   *  Default for Style is nothing
   */
  /**
   *  Gets the style attribute of the PokBaseABR object
   *
   *@return    The style value
   */
  protected String getStyle() {
    return "";
  }


  /**
   *  Gets the aBRSessionID attribute of the PokBaseABR object
   *
   *@return    The aBRSessionID value
   */
  public final String getABRSessionID() {
    return m_strABRSessionID;
  }

  // Will need more work here..
  /**
   *  Gets the shortClassName attribute of the PokBaseABR class
   *
   *@param  _cls  Description of the Parameter
   *@return       The shortClassName value
   */
  public static String getShortClassName(Class _cls) {
    return _cls.getName().substring(_cls.getName().lastIndexOf(".") + 1);
  }


  /**
   *  Gets the dGSubmitString attribute of the PokBaseABR object
   *
   *@return    The dGSubmitString value
   */
  public final String getDGSubmitString() {
    return m_strDGSubmit;
  }


  /**
   *  The enterprise derived from the Connection Item
   *
   *@return    The enterprise value
   */
  public final String getEnterprise() {
    if (m_prof == null) {
      return "";
    }
    return m_prof.getEnterprise();
  }


  /**
   *  Gets the entityType attribute of the PokBaseABR object
   *
   *@return    The entityType value
   */
  public final String getEntityType() {
    if (m_abri == null) {
      return "";
    }
    return m_abri.getEntityType();
  }


  /**
   *  Gets the entityID attribute of the PokBaseABR object
   *
   *@return    The entityID value
   */
  public final int getEntityID() {
    if (m_abri == null) {
      return -1;
    }
    return m_abri.getEntityID();
  }


  /**
   *  Gets the valOn attribute of the PokBaseABR object
   *
   *@return    The valOn value
   */
  public final String getValOn() throws SQLException, MiddlewareException{
   return m_prof.getValOn();
  }

  public final String getEffOn() throws SQLException, MiddlewareException{
      return m_prof.getEffOn();
  }

   public final void setNow() {
      try {
      DatePackage dbNow = m_db.getDates();
         m_strNow = dbNow.getNow();
      } catch (Exception ex) {
       // Not alot we can do here
      }
   }

  /**
   *  Gets the nLSItem attribute of the PokBaseABR object
   *
   *@return    The nLSItem value
   */
  public NLSItem getNLSItem() {
    return m_nlsi;
  }


  // Control Accessors

  /**
   *  Gets the readOnly attribute of the PokBaseABR object
   *
   *@return    The readOnly value
   */
  public final boolean isReadOnly() {
    return m_bReadOnly;
  }


  /**
   *  Gets the verbose attribute of the PokBaseABR object
   *
   *@return    The verbose value
   */
  public final boolean isVerbose() {
    return m_bVerbose;
  }


  /**
   *  Gets the locked attribute of the PokBaseABR object
   *
   *@return    The locked value
   */
  public final boolean isLocked() {
    return m_bLocked;
  }

  //
  // Mutators
  //

  /**
   *  Sets the entityList attribute of the PokBaseABR object
   *
   *@param  _el  The new entityList value
   */
  protected final void setEntityList(EntityList _el) {
    m_elist = _el;
  }

  /**
   *  Sets the now attribute of the PokBaseABR object
   *
   *@param  _s  The new now value
   */
  protected final void setNow(String _s) {
    m_strNow = _s;
  }

  /**
   *  Sets the endOfDay attribute of the PokBaseABR object
   *
   *@param  _s  The new endOfDay value
   */
  protected final void setEndOfDay(String _s) {
    m_strEndOfDay = _s;
  }

  /**
   *  Sets the today attribute of the PokBaseABR object
   *
   *@param  _s  The new today value
   */
  protected final void setToday(String _s) {
    m_strToday = _s;
  }


  /**
   *  Sets the epoch attribute of the PokBaseABR object
   *
   *@param  _s  The new epoch value
   */
  protected final void setEpoch(String _s) {
    m_strEpoch = _s;
  }


  /**
   *  Sets the forever attribute of the PokBaseABR object
   *
   *@param  _s  The new forever value
   */
  protected final void setForever(String _s) {
    m_strForever = _s;
  }


  /**
   *  Sets the aBRSessionID attribute of the PokBaseABR object
   *
   *@param  _s  The new aBRSessionID value
   */
  public void setABRSessionID(String _s) {
    m_strABRSessionID = _s;
  }


  /**
   *  Sets the nLSItem attribute of the PokBaseABR object
   *
   *@param  _nlsi  The new nLSItem value
   */
  public void setNLSItem(NLSItem _nlsi) {
    m_nlsi = _nlsi;
  }


  /**
   *  Sets the oPICMABRItem attribute of the PokBaseABR object
   *
   *@param  _abri  The new oPICMABRItem value
   */
  public void setOPICMABRItem(OPICMABRItem _abri) {
    m_abri = _abri;
  }


  /**
   *  Sets the readOnly attribute of the PokBaseABR object
   *
   *@param  _b  The new readOnly value
   */
  public void setReadOnly(boolean _b) {
    m_bReadOnly = _b;
  }


  /**
   *  Sets the verbose attribute of the PokBaseABR object
   *
   *@param  _b  The new verbose value
   */
  public final void setVerbose(boolean _b) {
    m_bVerbose = _b;
  }


  /**
   *  Sets the locked attribute of the PokBaseABR object
   *
   *@param  _b  The new locked value
   */
  public final void setLocked(boolean _b) {
    m_bLocked = _b;
  }


  /*
   *  This is what the Individual abr's have to execute
   */
  /**
   *  Description of the Method
   *
   *@exception  LockPDHEntityException    Description of the Exception
   *@exception  UpdatePDHEntityException  Description of the Exception
   *@exception  Exception                 Description of the Exception
   */
  public final void start_ABRBuild(boolean _bGenerateVE) throws LockPDHEntityException, UpdatePDHEntityException, Exception {
    //Here we go!!
    //ReturnStatus returnStatus = new ReturnStatus(-1);
    m_db = getDatabase();
    m_prof = getProfile();
    m_abri = getABRItem();


    //Setting the ABR session id (retrieve from the abstract task)
    setABRSessionID(Integer.toString(getJobID()));

    logMessage("ABRPROFILE " + "EntityType = " + m_abri.getEntityType() + " ID = " + m_abri.getEntityID());
    logMessage("ABRPROFILE " + m_prof.dump(true));

    if (m_db == null) {
      return;
    }

      // Establish Now
      setNow();
    m_pdhi = new EntityItem(null, m_prof, m_abri.getEntityType(), m_abri.getEntityID());

    //This entityItem will be used for softLock
    EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
    java.lang.reflect.Array.set(eiParm, 0, m_pdhi);

    if (_bGenerateVE)  {
      logMessage("VE name is " + m_abri.getVEName());
      ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, m_abri.getVEName());

      logMessage("Creating Entity List");
      logMessage("Profile is " + m_prof);
      logMessage("Role is " + m_prof.getRoleCode() + " : " + m_prof.getRoleDescription());
      logMessage("Extract action Item is" + eaItem);
      logMessage("Entity Item" + eiParm);
      m_elist = m_db.getEntityList(m_prof, eaItem, eiParm);
      logMessage("Entity List Created");
    } else {
      logMessage("start_ABRBuild instructed not to generate Extract Action Item");
    }

    if (!isReadOnly()) {
      if (!getSoftLock()) {
        // some kind of error message must go to the pw (look in getSoftLock)
        // didn't get the lock, so pause this thread just in case the UI is slow in releasing it
        try {
          log("Could not get the lock, sleeping for " + PAUSE_TIME + " milliseconds.");
          Thread.sleep(PAUSE_TIME);
        } catch (InterruptedException ie) {
          return;
        }                                              // ignore
        // Try a second time
        if (!getSoftLock()) {
          //ERR_IAB1006E= "IAB1006E: Could not get soft lock.  Rule execution is terminated.";
          //v1.2      logError(ERR_IAB1006E);
          logWarning(ERR_IAB1006E);
//          println(ERR_IAB1006E);
          //return;
        }
      }
    }

  }

  /*
    Default to true if called without any parameter

    */
    public final void start_ABRBuild() throws LockPDHEntityException, UpdatePDHEntityException, Exception {
      start_ABRBuild(true);
    }


  /**
   *  Gets the aBREntityType attribute of the PokBaseABR object
   *
   *@return    The aBREntityType value
   */
  protected final String getABREntityType() {
    if (m_abri == null) {
      return "";
    }
    return m_abri.getEntityType();
  }


  /**
   *  Gets the aBREntityID attribute of the PokBaseABR object
   *
   *@return    The aBREntityID value
   */
  protected final int getABREntityID() {
    if (m_abri == null) {
      return 0;
    }
    return m_abri.getEntityID();
  }


  /**
   *  Gets the aBRCode attribute of the PokBaseABR object
   *
   *@return    The aBRCode value
   */
  protected final String getABRCode() {
    if (m_abri == null) {
      return "";
    }
    return m_abri.getABRCode();
  }


  /*
   *  Retrieves the information for the given entity on who modified it last
   */
  /**
   *  Gets the lastModifiedId attribute of the PokBaseABR object
   *
   *@return    The lastModifiedId value
   * /
  private String getLastModifiedId() {

    ReturnStatus returnStatus = new ReturnStatus(-1);

    String str = "Userid not found.";
    try {

      ResultSet rs = m_db.callGBL2913(returnStatus, getEnterprise(), getEntityType(), getEntityID(), getValOn(), getEffOn());
      ReturnDataResultSet rdrs2913 = new ReturnDataResultSet(rs);
      rs.close();
      m_db.freeStatement();
      m_db.isPending();

      if (rdrs2913.getRowCount() > 0) {
        str = rdrs2913.getColumn(rdrs2913.getRowCount() - 1, 8) + " (Open id: " + rdrs2913.getColumn(rdrs2913.getRowCount() - 1, 7) + ")";
      } else {
        return "Userid not found.";
      }
    } catch (Exception x) {
      x.printStackTrace();
    }

    return str;
  }*/


  /*
   *  Need to further understand what this is
   *  @params status int with completion status
   */
  /**
   *  Sets the dGString attribute of the PokBaseABR object
   *
   *@param  status  The new dGString value
   */
  protected void setDGString(int status) {
  }


  /**
   *  Description of the Method
   *
   *@param  _strMsg     Description of the Parameter
   *@param  _astrParms  Description of the Parameter
   */
  protected void printTestedMessage(String _strMsg, String[] _astrParms) {
    log(buildLogMessage(_strMsg, _astrParms));
    if (isVerbose()) {
      println(formatMessage(buildMessage(_strMsg, _astrParms), false));
    }
  }


  /**
   *  Description of the Method
   *
   *@param  _strMsg    Description of the Parameter
   *@param  _bisError  Description of the Parameter
   *@return            Description of the Return Value
   */
  protected String formatMessage(String _strMsg, boolean _bisError) {
    // highlight errors in red font if other messages are output too
    if (_bisError && m_bVerbose) {
      if (m_bOutputAsList) {
        return "\n<font color=red><li>" + _strMsg + "</li></font>";
      } else {
        return "\n<font color=red>" + _strMsg + "</font><br />";
      }
    } else {
      if (m_bOutputAsList) {
        return "<li>" + _strMsg + "</li>";
      } else {
        return _strMsg + "<br />";
      }
    }
//    return _strMsg;
  }


  /**
   *  Description of the Method
   *
   *@param  _strMsgTemplate  Description of the Parameter
   *@param  _astrParms       Description of the Parameter
   */
  protected final void printMessage(String _strMsgTemplate, String[] _astrParms) {
    println(formatMessage(buildMessage(_strMsgTemplate, _astrParms), false));
    log(buildLogMessage(_strMsgTemplate, _astrParms));
  }


  /**
   *  Description of the Method
   *
   *@param  _strMessage  Description of the Parameter
   */
  protected final void printMessage(String _strMessage) {
    println(_strMessage);
    log(_strMessage);
  }


  /**
   *  This will format a string to print it within the specified width
   *
   *@param  _strPrint   Description of the Parameter
   *@param  _iColWidth  Description of the Parameter
   */
  public void prettyPrint(String _strPrint, int _iColWidth) {
    /*
     *  things to check
     *  * Length of the line
     *  Splitting line in the middle of a word - split at previous word
     *  next char after splitting line is a line feed -
     */
    //int iTotalLen = 0;
    String strToPrint = null;
    String strFromToken = null;
   // boolean bPrintNewLine = false;
    int iReadNext = 0;
    //int i = 0;

    StringTokenizer stString = new StringTokenizer(_strPrint, "\n", false);
    while (stString.hasMoreElements()) {
      strFromToken = stString.nextToken();
      if (strFromToken.length() > _iColWidth) {
       // iTotalLen = strFromToken.length();

        //System.out.println("Length of this token is "+iTotalLen);

        while (strFromToken.length() > _iColWidth) {
          iReadNext = (strFromToken.length() >= _iColWidth) ? _iColWidth : strFromToken.length();
          strToPrint = strFromToken.substring(0, iReadNext);
          if (!strToPrint.substring(iReadNext - 1).equals(" ")) {// no space found, Is this middle of a word?

            if (strToPrint.lastIndexOf(" ") <= 0) {    //previous word not found or 1st char is space
            } else {
              strToPrint = strToPrint.substring(0, strToPrint.lastIndexOf(" "));//move to the last word
            }
          }
          strFromToken = strFromToken.substring(strToPrint.length());//Initialize the String to process
          println(strToPrint.trim());
        }
        if (strFromToken.trim().length() > 0 )  {
          println(strFromToken.trim());              //Print the remaining stuff
        }
      } else {
        println(strFromToken.trim());
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  _strMsgTemplate  Description of the Parameter
   *@param  _astrParms       Description of the Parameter
   */
  protected final void printWarningMessage(String _strMsgTemplate, String[] _astrParms) {
    println(formatMessage(buildMessage(_strMsgTemplate, _astrParms), false));
    logWarning(buildLogMessage(_strMsgTemplate, _astrParms));
  }


  /*
   *  Needs to write error to two places - html output and answer
   */
  /**
   *  Description of the Method
   *
   *@param  _strMsgTemplate  Description of the Parameter
   *@param  _astrParms       Description of the Parameter
   */
  protected final void printErrorMessage(String _strMsgTemplate, String[] _astrParms) {
    println(formatMessage(buildLogMessage(_strMsgTemplate, _astrParms), true));
    logError(buildLogMessage(_strMsgTemplate, _astrParms));
  }


  /**
   *  Description of the Method
   *
   *@param  _strMsgTemplate  Description of the Parameter
   *@param  _astrParms       Description of the Parameter
   *@return                  Description of the Return Value
   */
  protected String buildMessage(String _strMsgTemplate, String[] _astrParms) {
    return buildMessage(_strMsgTemplate, _astrParms, false);
  }


  /**
   *  Description of the Method
   *
   *@param  _strMsgTemplate  Description of the Parameter
   *@param  _astrParms       Description of the Parameter
   *@return                  Description of the Return Value
   */
  protected String buildLogMessage(String _strMsgTemplate, String[] _astrParms) {
    return buildMessage(_strMsgTemplate, _astrParms, true);
  }


  /**
   *  Build the msg. A '#' following the %Cnt delimiter will prevent
   *
   *@param  _s   Description of the Parameter
   *@param  _as  Description of the Parameter
   *@param  _b   Description of the Parameter
   *@return      Description of the Return Value
   */
  private String buildMessage(String _s, String[] _as, boolean _b) {
    StringBuffer sb = new StringBuffer();
    int iCnt = -1;
    int iStart = 0;
    if (_as != null) {
      for (int i = 0; i < _as.length; i++) {
        iCnt = _s.indexOf("%" + (i + 1), iCnt);
        if (iCnt != -1) {
          boolean bFormat = true;
          if (_s.length() > (iCnt + ("%" + (i + 1)).length()) && _s.charAt(iCnt + ("%" + (i + 1)).length()) == '#') {
            bFormat = false;
          }
          String strTmp = _s.substring(iStart, iCnt);
          if (bFormat) {
            if (_b) {
              sb.append(strTmp + "\"" + _as[i] + "\"");
            } else {
              sb.append(strTmp + "<em>&quot;" + _as[i] + "&quot;</em>");
            }
            iStart = iCnt + ("%" + (i + 1)).length();
          } else {
            sb.append(strTmp + _as[i]);
            iStart = iCnt + ("%" + (i + 1)).length() + 1;
          }
        } else {
          break;
        }
      }
    }
    if (iStart != _s.length()) {
      sb.append(_s.substring(iStart));
    }

    return sb.toString();
  }


  /**
   *  Check for existance of attributes and generate error msg
   *
   *@param  _strEntityType           Description of the Parameter
   *@param  _iEntityID               Description of the Parameter
   *@param  _astrRequiredAttributes  Description of the Parameter
   *@return                          boolean
   */
  protected final boolean checkAttributes(String _strEntityType, int _iEntityID, String[] _astrRequiredAttributes) {

    boolean result = true;

    //check if all required attributes exist
    for (int i = 0; i < _astrRequiredAttributes.length; i++) {

      String[] astrMessage = new String[]{
          getABREntityDesc(_strEntityType, _iEntityID),
          getAttributeDescription(_strEntityType, _astrRequiredAttributes[i])
          };
      String strValue = getAttributeValue(_strEntityType, _iEntityID, _astrRequiredAttributes[i], DEF_NOT_POPULATED_HTML);

      if (strValue == null || strValue.equals("&nbsp;")) {
        printErrorMessage(ERR_IAB1001E, astrMessage);
        result = false;
      } else {
        printTestedMessage(MSG_IAB2011I, astrMessage);
      }
    }

    return result;
  }


  /**
   *  Get the attribute description, null if not found
   *
   *@param  entityType  String specifying the entity type to find
   *@param  attrCode    String attribute code to get value for
   *@return             The attributeDescription value
   *@returns            String attribute description
   */
  public String getAttributeDescription(String entityType, String attrCode) {
    return getMetaDescription(entityType, attrCode, true);
  }


  /**
   *  Get the meta attribute or task description, null if not found
   *
   *@param  _strEtype     Description of the Parameter
   *@param  _strAttrCode  Description of the Parameter
   *@param  _bLongDesc    Description of the Parameter
   *@return               The metaDescription value
   *@returns              String attribute description
   */
  private String getMetaDescription(String _strEtype, String _strAttrCode, boolean _bLongDesc) {
    return getMetaDescription(m_elist, _strEtype, _strAttrCode, _bLongDesc);
  }

  /**
   *  Gets the metaDescription attribute of the PokBaseABR object
   *
   *@param  _elist        Description of the Parameter
   *@param  _strEtype     Description of the Parameter
   *@param  _strAttrCode  Description of the Parameter
   *@param  _bLongDesc    Description of the Parameter
   *@return               The metaDescription value
   */
  private String getMetaDescription(EntityList _elist, String _strEtype, String _strAttrCode, boolean _bLongDesc) {
    EntityGroup entGroup = _elist.getEntityGroup(_strEtype);
    if (entGroup == null) {
      logError("Did not find EntityGroup: "+_strEtype+" in entity list to extract getMetaDescription");
      return null;
    }

    EANMetaAttribute ema = null;
    if (entGroup != null) {
      ema = entGroup.getMetaAttribute(_strAttrCode);
    }
    String desc = null;
    if (ema != null) {
      if (_bLongDesc) {
        desc = ema.getLongDescription();
      } else {
        desc = ema.getShortDescription();
      }
    }
    return desc;
  }


  /**
   *  Get the Meta Flag description for the specified attribute and flag value
   *
   *@param  entityType  String specifying the entity type to find
   *@param  attrCode    String attribute code to get value for
   *@param  flagValue   String flag code to get m_hDisplay value for
   *@return             The attributeMetaFlagDescription value
   *@returns            String flag m_hDisplay text
   */
  public String getAttributeMetaFlagDescription(String entityType, String attrCode, String flagValue) {
    return getMetaFlagDescription(entityType, attrCode, flagValue);
  }


  /**
   *  Get the Meta Flag description for the specified attribute and flag value
   *
   *@param  _strEtype      Description of the Parameter
   *@param  _strAttrCode   Description of the Parameter
   *@param  _strFlagValue  Description of the Parameter
   *@return                The metaFlagDescription value
   *@returns               String flag m_hDisplay text
   */

  private String getMetaFlagDescription(String _strEtype, String _strAttrCode, String _strFlagValue) {

    if (_strFlagValue == null) {
      logError("null FlagCode supplied! Returning Error");
      return null;
    }
    EntityGroup entGroup = m_elist.getEntityGroup(_strEtype);

    if (entGroup == null) {
      logError("Did not find EntityGroup: "+_strEtype+" in entity list to extract MetaFlagDescription");
      return null;
    }

    EntityItem entItem = entGroup.getEntityItem(_strEtype);
    EANAttribute eattr = entItem.getAttribute(_strEtype);
    String desc = null;
    if (eattr instanceof EANFlagAttribute) {
      EANFlagAttribute ema = (EANFlagAttribute) eattr;
      desc = ema.getFlagLongDescription(_strFlagValue);
    }
    return desc;
  }


  /*
   *  Returns true if any of the parents for the given entitytype are considered retired.. (by a status)
   *  may need to have error handling fixed
   */
  /**
   *  Description of the Method
   *
   *@param  _strEntityType     Description of the Parameter
   *@param  _iEntityID         Description of the Parameter
   *@param  _strParentType     Description of the Parameter
   *@param  _strRelator        Description of the Parameter
   *@param  _strAttributeCode  Description of the Parameter
   *@param  _strFlagValue      Description of the Parameter
   *@return                    Description of the Return Value
   */
  protected boolean anyRetiredParents(String _strEntityType, int _iEntityID, String _strParentType, String _strRelator, String _strAttributeCode, String _strFlagValue) {
    boolean bReturn = true;
    Iterator ids = getParentEntityIds(_strEntityType, _iEntityID, _strParentType, _strRelator).iterator();
    while (ids.hasNext()) {
      int iParentID = ((Integer) ids.next()).intValue();
      String flagVal = getAttributeFlagValue(_strParentType, iParentID, _strAttributeCode);
      if (!_strFlagValue.equals(flagVal)) {
        bReturn = false;
        String flagDesc = "<em>** Not Populated **</em>";
        if (flagVal != null) {
          flagDesc = getAttributeMetaFlagDescription(_strParentType, _strAttributeCode, flagVal);
        }

        //ERR_IAB2001E="The %1# cannot be %2# because %3 has a current Life Cycle status value of %4.";
        printErrorMessage(
            ERR_IAB2001E,
            new String[]{
            getEntityDescription(getEntityType()),
            "retired",
            getABREntityDesc(_strParentType, iParentID),
            flagDesc
            });
      } else {
        //MSG_IAB2015I="%1 has a current Life Cycle status value of %2.";
        String strMetaDesc = getAttributeMetaFlagDescription(_strParentType, _strAttributeCode, _strFlagValue);
        printTestedMessage(
            MSG_IAB2015I,
            new String[]{
            getABREntityDesc(_strParentType, iParentID),
            ((strMetaDesc == null) ? "Null value returned for "+_strParentType+":"+_strAttributeCode+":"+_strFlagValue : strMetaDesc)
            });
      }
    }
    return bReturn;
  }


  /*
   *  This checks to see if any Children of a specific entitytype is retired.  Error's may need to be handled by caller
   */
  /**
   *  Description of the Method
   *
   *@param  _strEntityType     Description of the Parameter
   *@param  _iEntityID         Description of the Parameter
   *@param  _strChildType      Description of the Parameter
   *@param  _strRelator        Description of the Parameter
   *@param  _strAttributeCode  Description of the Parameter
   *@param  _strFlagValue      Description of the Parameter
   *@return                    Description of the Return Value
   */
  protected boolean anyRetiredChildren(String _strEntityType, int _iEntityID, String _strChildType, String _strRelator, String _strAttributeCode, String _strFlagValue) {
    boolean bReturn = true;
    Iterator ids = getChildrenEntityIds(_strEntityType, _iEntityID, _strChildType, _strRelator).iterator();
    while (ids.hasNext()) {
      int iChildID = ((Integer) ids.next()).intValue();
      String flagVal = getAttributeFlagValue(_strChildType, iChildID, _strAttributeCode);
      if (!_strFlagValue.equals(flagVal)) {
        bReturn = false;
        String flagDesc = "<em>** Not Populated **</em>";
        if (flagVal != null) {
          flagDesc = getAttributeMetaFlagDescription(_strChildType, _strAttributeCode, flagVal);
        }

        //ERR_IAB2001E="The %1# cannot be %2# because %3 has a current Life Cycle status value of %4.";
        printErrorMessage(
            ERR_IAB2001E,
            new String[]{
            getEntityDescription(getEntityType()),
            "retired",
            getABREntityDesc(_strChildType, iChildID),
            flagDesc
            });
      } else {
        //MSG_IAB2015I="%1 has a current Life Cycle status value of %2.";
        printTestedMessage(
            MSG_IAB2015I,
            new String[]{
            getABREntityDesc(_strChildType, iChildID),
            getAttributeMetaFlagDescription(_strChildType, _strAttributeCode, _strFlagValue)
            });
      }
    }
    return bReturn;
  }


  /*
   *  Checks to ensure that parents Entities have to match the AttributeValue for the given Attribute Code
   */
  /**
   *  Description of the Method
   *
   *@param  _strEntityType     Description of the Parameter
   *@param  _iEntityID         Description of the Parameter
   *@param  _strParentType     Description of the Parameter
   *@param  _strRelator        Description of the Parameter
   *@param  _strAttributeCode  Description of the Parameter
   *@param  _strFlagValue      Description of the Parameter
   *@return                    Description of the Return Value
   */
  protected boolean checkForAvailableParents(String _strEntityType, int _iEntityID, String _strParentType, String _strRelator, String _strAttributeCode, String _strFlagValue) {

    boolean bReturn = true;
    Iterator ids = getParentEntityIds(_strEntityType, _iEntityID, _strParentType, _strRelator).iterator();
    while (ids.hasNext()) {
      int iParentID = ((Integer) ids.next()).intValue();
      String flagVal = getAttributeFlagValue(_strParentType, iParentID, _strAttributeCode);
      if (!_strFlagValue.equals(flagVal)) {
        bReturn = false;
        String flagDesc = "<em>** Not Populated **</em>";
        if (flagVal != null) {
          flagDesc = getAttributeMetaFlagDescription(_strParentType, _strAttributeCode, flagVal);
        }

        //ERR_IAB2001E="The %1# cannot be %2# because %3 has a current Life Cycle status value of %4.";
        printErrorMessage(
            ERR_IAB2001E,
            new String[]{
            getEntityDescription(getEntityType()),
            "made available",
            getABREntityDesc(_strParentType, iParentID),
            flagDesc
            });
      } else {
        //MSG_IAB2015I="%1 has a current Life Cycle status value of %2.";
        printTestedMessage(
            MSG_IAB2015I,
            new String[]{
            getABREntityDesc(_strParentType, iParentID),
            getAttributeMetaFlagDescription(_strParentType, _strAttributeCode, _strFlagValue)
            });
      }
    }
    return bReturn;
  }


  /*
   *  Checks to ensure that parents Entities have to match the AttributeValue for the given Attribute Code
   */
  /**
   *  Description of the Method
   *
   *@param  _strEntityType     Description of the Parameter
   *@param  _iEntityID         Description of the Parameter
   *@param  _strChildType      Description of the Parameter
   *@param  _strRelator        Description of the Parameter
   *@param  _strAttributeCode  Description of the Parameter
   *@param  _strFlagValue      Description of the Parameter
   *@return                    Description of the Return Value
   */
  protected boolean checkForAvailableChildren(String _strEntityType, int _iEntityID, String _strChildType, String _strRelator, String _strAttributeCode, String _strFlagValue) {

    boolean bReturn = true;
    Iterator ids = getChildrenEntityIds(_strEntityType, _iEntityID, _strChildType, _strRelator).iterator();

    while (ids.hasNext()) {
      int iChildID = ((Integer) ids.next()).intValue();
      String flagVal = getAttributeFlagValue(_strChildType, iChildID, _strAttributeCode);
      if (!_strFlagValue.equals(flagVal)) {
        bReturn = false;
        String flagDesc = "<em>** Not Populated **</em>";
        if (flagVal != null) {
          flagDesc = getAttributeMetaFlagDescription(_strChildType, _strAttributeCode, flagVal);
        }

        //ERR_IAB2001E="The %1# cannot be %2# because %3 has a current Life Cycle status value of %4.";
        printErrorMessage(
            ERR_IAB2001E,
            new String[]{
            getEntityDescription(getEntityType()),
            "made available",
            getABREntityDesc(_strChildType, iChildID),
            flagDesc
            });
      } else {
        //MSG_IAB2015I="%1 has a current Life Cycle status value of %2.";
        printTestedMessage(
            MSG_IAB2015I,
            new String[]{
            getABREntityDesc(_strChildType, iChildID),
            getAttributeMetaFlagDescription(_strChildType, _strAttributeCode, _strFlagValue)
            });
      }
    }

    return bReturn;
  }


  /**
   *  Check for children. Rule is One or less child relators may exist
   *
   *@param  _astrReqRelators  Description of the Parameter
   *@return                   boolean
   */
  protected boolean checkOptionalChildren(String[] _astrReqRelators) {
    return checkRelators(_astrReqRelators, ONE_OR_LESS, true);
  }


  /**
   *  Check for entities through the specified relator one or less may exist
   *
   *@param  _strEntityType   Description of the Parameter
   *@param  _strRelatorType  Description of the Parameter
   *@return                  boolean
   */
  protected boolean checkOptionalChild(String _strEntityType, String _strRelatorType) {
    return checkRelator(_strEntityType, _strRelatorType, ONE_OR_LESS, true);
  }


  /**
   *  Check for children and generate error msg One or more child relators must
   *  exist
   *
   *@param  _astrReqRelators  Description of the Parameter
   *@return                   boolean
   */
  protected boolean checkMultipleChildren(String[] _astrReqRelators) {
    return checkRelators(_astrReqRelators, ONE_OR_MORE, true);
  }


  /**
   *  Check for entities through the specified relator one or more must exist.
   *
   *@param  _strEntityType   Description of the Parameter
   *@param  _strRelatorType  Description of the Parameter
   *@return                  boolean
   */
  protected boolean checkMultipleChild(String _strEntityType, String _strRelatorType) {
    return checkRelator(_strEntityType, _strRelatorType, ONE_OR_MORE, true);
  }


  /**
   *  Check for children and generate error msg. One and only one child relator
   *  must exist
   *
   *@param  _astrReqRelators  Description of the Parameter
   *@return                   boolean
   */
  protected boolean checkUniqueChildren(String[] _astrReqRelators) {
    return checkRelators(_astrReqRelators, UNIQUE, true);
  }


  /**
   *  Check for parents and generate error msg. One and only one parent must
   *  exist.
   *
   *@param  _astrReqRelators  Description of the Parameter
   *@return                   boolean
   */
  protected boolean checkUniqueParents(String[] _astrReqRelators) {
    return checkRelators(_astrReqRelators, UNIQUE, false);
  }


  /**
   *  Check for entities through the specified relator one and only one is
   *  required
   *
   *@param  _strEntityType   Description of the Parameter
   *@param  _strRelatorType  Description of the Parameter
   *@return                  boolean
   */
  protected boolean checkUniqueChild(String _strEntityType, String _strRelatorType) {
    return checkRelator(_strEntityType, _strRelatorType, UNIQUE, true);
  }


  /**
   *  Check for entities through the specified relator
   *
   *@param  _strEntityType   Description of the Parameter
   *@param  _strRelatorType  Description of the Parameter
   *@return                  boolean
   */
  protected boolean checkUniqueParent(String _strEntityType, String _strRelatorType) {
    //return checkRelator(_strEntityType, _strRelatorType, UNIQUE, false);
    EntityGroup egRelatorgroup = m_elist.getEntityGroup(_strRelatorType);
    Hashtable hfoundParents = new Hashtable();
    EntityItem eiRelator = null;
    EntityItem eiParent = null;
    if (egRelatorgroup.getEntityItemCount()==0)  {
      return false;
    }
    for (int i=0;i<egRelatorgroup.getEntityItemCount();i++)  {     //Start searching from the children up
      eiRelator = (EntityItem) egRelatorgroup.getEntityItem(i);
      for (int y=0;y<eiRelator.getUpLinkCount();y++) {
        eiParent = (EntityItem) eiRelator.getUpLink(y);         //get Each parent
        if (eiParent.getEntityType().equals(_strEntityType)) {   //Match parent type with parameter
          if (!hfoundParents.containsKey(eiParent.getEntityType()+":"+Integer.toString(getEntityID()))) {
            hfoundParents.put(eiParent.getEntityType()+":"+Integer.toString(getEntityID()),"Found");
          } else  {
            return false;                                       //We found this parent before...so its not unique
          }
        }
      }
    }
    return true;
  }


  /**
   *  Check for relators and generate error msg
   *
   *@param  _astrReqRelators  Description of the Parameter
   *@param  _iState           Description of the Parameter
   *@param  _bChild           Description of the Parameter
   *@return                   boolean
   */
  private boolean checkRelators(String[] _astrReqRelators, int _iState, boolean _bChild) {

    boolean result = true;

    //check if all required relators exist
    for (int i = 0; i < _astrReqRelators.length; i += 2) {
      String strChildType = _astrReqRelators[i];
      String strRelatorType = _astrReqRelators[i + 1];
      if (!checkRelator(strChildType, strRelatorType, _iState, _bChild)) {
        result = false;
      }
    }

    return result;
  }


  /**
   *  Check for entities through the specified relator
   *
   *@param  _strEntityType   Description of the Parameter
   *@param  _strRelatorType  Description of the Parameter
   *@param  _iState          Description of the Parameter
   *@param  _bChild          Description of the Parameter
   *@return                  boolean
   */
  private boolean checkRelator(String _strEntityType, String _strRelatorType, int _iState, boolean _bChild) {

    Vector vct = null;
    String strEntityDesc = getEntityDescription(_strRelatorType);
    String[] astrMessage = new String[]{
        getEntityDescription(getEntityType()),
        getABREntityDesc(getEntityType(), getEntityID()),
        strEntityDesc
        };

    if (_bChild) {
      vct = getChildrenEntityIds(getEntityType(), getEntityID(), _strEntityType, _strRelatorType);
    } else {
      vct = getParentEntityIds(getEntityType(), getEntityID(), _strEntityType, _strRelatorType);
    }

    int iCount = vct.size();

    // avoid hardcoding description, even if entity id=0, the description
    // is valid so get it if possible


    switch (_iState) {
        case UNIQUE:                                   // one and only one required
          switch (iCount) {
              case 0:
                printErrorMessage(ERR_IAB1002E, astrMessage);
                return false;
              case 1:
                break;
              default:
                printErrorMessage(ERR_IAB1003E, astrMessage);
                return false;
          }
          break;
        case ONE_OR_MORE:                              // at least one is required
          if (iCount == 0) {
            printErrorMessage(ERR_IAB1004E, astrMessage);
            return false;
          }
          break;
        case ONE_OR_LESS:                              // may have only one
          if (iCount > 1) {
            printErrorMessage(ERR_IAB1003E, astrMessage);
            return false;
          }
          break;
        default:
          break;
    }

    printTestedMessage(MSG_IAB2012I, astrMessage);
    return true;
  }


   /**
   *  get the soft lock fpr tje database connection. Assume only one thing gets
   *  softlocked
   *
   *@return    The softLock value
   */
  public boolean getSoftLock() {

    m_slg = null;

    // We have a vector

    try {

      //m_sli = new LockItem(null,m_abri.getEntityType(), m_abri.getEntityID(),getProfile().getOPWGID());
      EntityItem  lockEI = new EntityItem(null,m_prof, Profile.OPWG_TYPE, m_prof.getOPWGID());
      m_slg = new LockGroup(m_db, m_prof, lockEI, m_pdhi,lockEI.getKey(), LockGroup.LOCK_NORMAL, true);

    } catch (MiddlewareException ex) {
      //ex.printStackTrace();
      logError(ex, "Error during Softlock");
      return false;
    //} catch (SQLException ex) {
      //logError(ex, "Error during Softlock");
      //ex.printStackTrace();
      //return false;
    } catch (Exception ex) {
      logError(ex, "Error during Softlock");
      return false;
    }

    // If we are still null.  We did not make it
    if (m_slg == null) {
      return false;
    }

    // we have a good m_slg

    boolean bGoodLocks = false;
    try {
      EntityItem  lockEI = new EntityItem(null,m_prof, Profile.OPWG_TYPE, m_prof.getOPWGID());
      if (m_slg.hasExclusiveLock(lockEI,lockEI.getKey(),m_prof)) {
      bGoodLocks = true;
        log(
            buildLogMessage(
            ERR_IAB1005E,
            new String[]{
            m_pdhi.getEntityType(),
            "" + m_pdhi.getEntityID(),
            getShortClassName(getClass())
            }));
      } else {
        bGoodLocks = false;
        logWarning(
            buildLogMessage(
            ERR_IAB1005E,
            new String[]{
            m_pdhi.getEntityType(),
            "" + m_pdhi.getEntityID(),
            getShortClassName(getClass())
            }));
      }

      if (!bGoodLocks) {
        logWarning(ERR_IAB1006E);
      } else  {
        bGoodLocks = true;
      }
      setLocked(bGoodLocks);
    }catch (MiddlewareRequestException me)  {
      logMessage("getSoftLock:MiddlewareRequestException Error while trying to create entityItem");
    }

    return bGoodLocks;
  }


  /*
   *  release the soft lock(s)
   */
  /**
   *  Description of the Method
   */
  public void clearSoftLock() {
    if (m_slg == null) {
      return;
    }
    try {
      EntityItem  lockEI = new EntityItem(null,m_prof, Profile.OPWG_TYPE, m_prof.getOPWGID());
      m_slg = new LockGroup(m_db, m_prof, lockEI, m_pdhi, lockEI.getKey(), LockGroup.LOCK_NORMAL, false);
      m_slg.removeLockItem(m_db, m_pdhi, m_prof, lockEI,lockEI.getKey(),LockGroup.LOCK_NORMAL);
      setLocked(false);
    } catch (Exception x) {
      x.printStackTrace();
    }
  }


  /*
   *  All logs are managed by the database object
   */
  /**
   *  Description of the Method
   *
   *@param  msg  Description of the Parameter
   */
  public void log(String msg) {
    logMessage(getABRSessionID() + ":" + getShortClassName(getClass()) + ":" + msg);
  }


  /*
   *  All logs are managed by the database object
   */
  /**
   *  Description of the Method
   *
   *@param  msg  Description of the Parameter
   */
  public void logWarning(String msg) {
    logMessage(getABRSessionID() + ":" + getShortClassName(getClass()) + ":" + msg);
  }


  /*
   *  All logs are managed by the database object
   */
  /**
   *  Description of the Method
   *
   *@param  msg  Description of the Parameter
   */
  public void logError(String msg) {
    logMessage(getABRSessionID() + ":" + getShortClassName(getClass()) + ":" + msg);
  }


  /**
   *  Description of the Method
   *
   *@param  _ex  Description of the Parameter
   *@param  msg  Description of the Parameter
   */
  public void logError(Exception _ex, String msg) {
    logMessage(getABRSessionID() + ":" + getShortClassName(getClass()) + ":" + msg);
    StringWriter writer = new StringWriter();

    _ex.printStackTrace(new PrintWriter(writer));

    String x = writer.toString();

    logMessage(x);

  }


  /**
   *  Gets the version attribute of the PokBaseABR class
   *
   *@return    The version value
   */
  public static String getVersion() {
    return ("$Id: PokBaseABR.java,v 1.216 2013/03/29 06:48:56 wangyulo Exp $");
  }


  /*
   *  builds the standard header and submits it to the passed PrintWriter
   */
  public void buildReportHeader() throws SQLException, MiddlewareException {
    String strVersion = getVersion();
    String astr1[] = new String[]{getABRDescription(), strVersion, getEnterprise()};
    buildReportHeader(astr1);
  }

  public void buildReportHeader(String[] _strMessage) throws SQLException, MiddlewareException{

    // output html header
    String abrName = getShortClassName(getClass());
    println("<html>\n<head>\n<title>" + abrName + "</title>\n" +
        getStyle() +
        "</head>\n<body>\n");
    String strMessage1 = buildMessage(MSG_IAB0001I, _strMessage);
    println("<p>" + strMessage1 + "</p>");
    println("<table width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n" +
        "<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" +
        getABRVersion() + "</td></tr>\n" +
        "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" +
        getValOn() + "</td></tr>\n" +
        "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" +
        getEffOn() + "</td></tr>\n" +
        "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" +
        getNow() + "</td></tr>\n" +
        "</table>");
    println("<h3>Description: </h3>" + getDescription() + "\n<hr>\n");

  }

  /*
   *  builds the standard header and submits it to the passed PrintWriter
   */
  public void buildReportHeaderII() throws SQLException, MiddlewareException {
    String astr1[] = new String[]{getABRDescription(), getEnterprise()};
    buildReportHeaderII(astr1);
  }

public void buildReportHeaderII(String[] _strMessage) throws SQLException, MiddlewareException{

    // output html header
    String abrName = getShortClassName(getClass());
    println("<html>\n<head>\n<title>" + abrName + "</title>\n" +
        getStyle() +
        "</head>\n<body>\n");
//    String strMessage1 = buildMessage(MSG_IAB0001I, _strMessage);
//    println("<p>" + strMessage1 + "</p>");
    println("<table width=\"750\" cellpadding=\"0\" cellspacing=\"0\">\n" +
    "<tr><td width=\"25%\"><b>ABRName: </b></td><td>" +
        getABRCode() + "</td></tr>\n" +
        "<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" +
        getABRVersion() + "</td></tr>\n" +
        "<tr><td width=\"25%\"><b>Enterprise: </b></td><td>" +
        getEnterprise() + "</td></tr>\n" +
        "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" +
        getValOn() + "</td></tr>\n" +
        "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" +
        getEffOn() + "</td></tr>\n" +
        "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" +
        getNow() + "</td></tr>\n" +
        "</table>");
    println("<h3>Description: </h3>" + getDescription() + "\n<hr>\n");

  }
  /*
   *  builds the standard report Footer
   */
  /**
   *  Description of the Method
   */
  public void buildReportFooter() {
    // always add the dg submit string
    println("\n");
    println(getDGSubmitString() + "\n</body>\n</html>");
  }


  /*
   *  Checks to see if the flag value for the given RootEntityType, RootEntityID, AttributeCode of th VE equals the
   *  passed verified value
   */
  /**
   *  Description of the Method
   *
   *@param  _strAttributeCode  Description of the Parameter
   *@param  _strVerifyVal      Description of the Parameter
   *@return                    Description of the Return Value
   */
  protected boolean checkFlagValues(String _strAttributeCode, String _strVerifyVal) {
    String strCurrentVal = null;
    if (_strVerifyVal.toLowerCase().equals(_strVerifyVal.toUpperCase())) {
      strCurrentVal = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), _strAttributeCode, "not*Found");
    }else {
      strCurrentVal = getAttributeFlagValue(getEntityType(), getEntityID(), _strAttributeCode);
    }
    log("checkFlagValues: Verify "+_strAttributeCode+":"+_strVerifyVal);
    log("checkFlagValues: Retrieved Value for "+_strAttributeCode+":"+strCurrentVal);
    if (strCurrentVal == null && _strVerifyVal == null) {
      return true;
    }
    if (strCurrentVal == null && _strVerifyVal != null) {
      return false;
    }
    if (strCurrentVal.equals(_strVerifyVal)) {
      return true;
    }
    return false;
  }


  /**
   *  We have to figure out how to use this later Used to post success of
   *  failure to an ABR that is not this ABR. We do not check locking here ...
   *
   *@param  _s1  Description of the Parameter
   *@param  _s2  Description of the Parameter
   */
  public final void postABRStatusToPDH(String _s1, String _s2) {
    try {
      ControlBlock cb = new ControlBlock(getNow(), getForever(), getNow(), getForever(), getProfile().getOPWGID());
      ReturnEntityKey myEntity = new ReturnEntityKey(getEntityType(), getEntityID(), true);
      Vector vctAttributes = new Vector();
      vctAttributes.addElement(new SingleFlag(getProfile().getEnterprise(), getEntityType(), getEntityID(), _s1, _s2, 1, cb));
      myEntity.m_vctAttributes = vctAttributes;
      Vector vctTransactions = new Vector();
      vctTransactions.addElement(myEntity);
      m_db.update(getProfile(), vctTransactions);
      m_db.commit();
    } catch (Exception x) {
      logMessage(x.toString());
      //x.printStackTrace();
    }
  }
//
//  protected final PDHGroup getPDHGroup(boolean _bForceNew) throws Exception {
//    if (m_pdhg != null && !_bForceNew) return m_pdhg;
//      NavigateObject noTmp = new NavigateObject(getProfile());
//      noTmp.setNLSItem(getProfile().getReadLanguage());
//      NavigateGroup ng = new NavigateGroup(noTmp,getEntityType());
//      NavigateItem[] ani = new NavigateItem[] {new NavigateItem(ng,getEntityType(),getEntityID())};
//      m_pdhg = m_db.getPDHEntities(getProfile(),ani);
//      m_pdhg.synchOPICMSoftLockGroup(m_slg);
//    return m_pdhg;
//  }
//
//  public final PDHGroup getPDHGroup() {
//    return m_pdhg;
//  }
//
//  protected final PDHItem getPDHItem(boolean _bForceNew) throws Exception{
//    PDHGroup pdhg = getPDHGroup(_bForceNew);
//      pdhg.synchOPICMSoftLockGroup(m_slg);
//    m_pdhi = m_pdhg.getPDHItem(getEntityType()+getEntityID());
//      return m_pdhi;
//  }
//
//  public final PDHItem getPDHItem() {
//      return m_pdhi;
//  }
//
  /*
   *  This guy will take all the attributecodes and their values and post them
   *  back to the PDH.  The attribute codes and their values are found in the passed hashtable
   *  It appears that there are two types of updates allowed..
   *  One is Status Fields.. The other is Text Fields
   */
  /**
   *  Description of the Method
   *
   *@param  _hsh           Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  protected final void putDataToPDHGroup(Hashtable _hsh) throws Exception {

    // Go get the pdhgroup if it is null - We only need to get it once.
    // Do not forget to merge the lock group in...
    // also obtain the softlock if needed.
//    EntityGroup pdhg = m_elist.getEntityGroup(getEntityType());
    EntityGroup pdhg = m_elist.getParentEntityGroup();
    if (pdhg==null) {
        printMessage("putDataToPDHGroup:ERROR!: Cannot retrieve group"+getEntityType() +" from EntityList");
        return;
    }
    EntityItem pdhi = pdhg.getEntityItem(getEntityType(), getEntityID());
    if (pdhi==null) {
        printMessage("putDataToPDHGroup:ERROR!: Cannot retrieve Item "+getEntityType()+":"+getEntityID() +" from EntityGroup");
        return;
    }
    EntityItem  lockEI = new EntityItem(null,m_prof, Profile.OPWG_TYPE, m_prof.getOPWGID());
    if (lockEI==null) {
        printMessage("putDataToPDHGroup:ERROR!: Cannot retrieve Lock Item");
        return;
    }
    LockGroup lgEntity = new LockGroup(m_db, m_prof, lockEI, pdhi, lockEI.getKey(),LockGroup.LOCK_NORMAL, true);
    if (lgEntity==null) {
        printMessage("putDataToPDHGroup:ERROR!: Cannot retrieve Lock group");
        return;
    }

    log("*****LOCK GROUP DUMP*****" + lgEntity.dump(false));

    log("HASHTABLE DUMP"+_hsh.toString());
    // This could be bad if we return here...
    LockItem liEntity = lgEntity.getLockItem(0);      //There is only 1 item in the group anyway
    if (liEntity==null) {
        printMessage("putDataToPDHGroup:ERROR!: Cannot retrieve Item from Lock group");
        return;
    }

//    if (pdhi == null || !pdhi.isEditable(getEntityType() + " : NAME") || !liEntity.getLockedOn().equals(pdhi.getEntityType())) {//pass name as default to check
      Enumeration e = _hsh.keys();
      String strAttributeCode = (e.hasMoreElements() ?(String) e.nextElement() : "NOATTRIBUTEAVAILABLE");
      log("Checking attribute "+strAttributeCode);
      if (!pdhi.isEditable(pdhi.getEntityType()+":"+strAttributeCode)) {
        printMessage("putDataToPDHGroup:Cannot update "+strAttributeCode+" through ABR.. Entity is not Editable");
        return;
      }

      log("putDataToPDHGroup:Locked Entity :"+getEntityType()+getEntityID()+": before updating");

    while (e.hasMoreElements()) {
      strAttributeCode = (String) e.nextElement();
      String strValue = (String) _hsh.get(strAttributeCode);
      EANAttribute eanAttr = pdhi.getAttribute(strAttributeCode);

      if (eanAttr==null)  {
        log("putDataToPDHGroup:ERROR!: Cannot retrieve "+strAttributeCode +" from EntityItem");
        return;
      }

      // now we must post the change based upon the type of attribute it is
      // The only types supported are Status / Unique Flag/ Text

      if (eanAttr instanceof StatusAttribute || eanAttr instanceof SingleFlagAttribute) {
        StatusAttribute stAttr = (StatusAttribute) eanAttr;
        MetaFlag[] mfAttr = (MetaFlag[]) stAttr.get();
        for (int i = 0; i < mfAttr.length; i++) {
          if (mfAttr[i].getFlagCode().equals(strValue)) {
            mfAttr[i].setSelected(true);
            break;
          }
        }
        try {
          log("Setting " + getEntityType() + ":" + getEntityID() + "Flag attribute: " + strAttributeCode + " to " + strValue);
          stAttr.put(mfAttr);
        } catch (EANBusinessRuleException be) {
          log("Business rule error when Setting " + getEntityType() + ":" + getEntityID() + "Flag attribute: " + strAttributeCode + " to " + strValue);
        }
      } else if (eanAttr instanceof TextAttribute) {
        TextAttribute pdht = (TextAttribute) eanAttr;
        if (strValue.equals("")) {
          pdht.setActive(false);
        } else {
          try {
            log("Setting " + getEntityType() + ":" + getEntityID() + "Text attribute: " + strAttributeCode + " to " + strValue);
            pdht.put(strValue);
          } catch (EANBusinessRuleException be) {
            log("Business rule error when Setting " + getEntityType() + ":" + getEntityID() + "Text attribute: " + strAttributeCode + " to " + strValue);
          }
        }
      }
    }
  }


  /**
   *  Commit the updates
   *
   *@exception  Exception  Description of the Exception
   */
  public void commitPDHGroupToPDH() throws Exception {
    m_pdhg.commit(m_db, null);
    log("Commited updates for " + getEntityType() + ":" + getEntityID());
  }

  // Dumps all the innards to a String
  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public String dump() {

    StringBuffer strbResult = new StringBuffer();

    strbResult.append("ABRName: " + getABRCode());
    strbResult.append("\nABRSessionID: " + getABRSessionID());
    strbResult.append("\nDescription: " + getABRDescription());
    strbResult.append("\nEntityType: " + getABREntityType());
    strbResult.append("\nEntityID: " + getABREntityID());

    return new String(strbResult);
  }


  /*
   *  A handy static method to covert a string of tokens to
   *  a String array
   */
  /**
   *  Description of the Method
   *
   *@param  data  Description of the Parameter
   *@return       Description of the Return Value
   */
  public static String[] convertToArray(String data) {

    Vector vct = new Vector();
    StringTokenizer st = new StringTokenizer(data, TOKEN_DELIMITER);
    while (st.hasMoreTokens()) {
      vct.addElement(st.nextToken());
    }
    String astr[] = new String[vct.size()];
    vct.copyInto(astr);
    return astr;
  }


  /**
   *  Added by Bala
   *
   *@param  _strType  Description of the Parameter
   *@return           The entityDescription value
   */
  /**
   *  Methods dealing with Entity
   *
   *@param  _strType  Description of the Parameter
   *@return           The entityDescription value
   */

  /**
   *  Get the entity or relator description, null if not found
   *
   *@param  _strType  Description of the Parameter
   *@return           The entityDescription value
   *@returns          String entity description
   */
  public String getEntityDescription(String _strType) {
    return getEntityDescription(m_elist, _strType);
  }


  /**
   *  Gets the entityDescription attribute of the PokBaseABR object
   *
   *@param  _elist    Description of the Parameter
   *@param  _strType  Description of the Parameter
   *@return           The entityDescription value
   */
  public String getEntityDescription(EntityList _elist, String _strType) {
    EntityGroup entGroup = _elist.getEntityGroup(_strType);
    return entGroup.getLongDescription();
  }


  /**
   *  Get the entity capability
   *
   *@param  _strtype  Description of the Parameter
   *@return           The entityCapability value
   *@returns          String entity capability
   */
  public String getEntityCapability(String _strtype) {
    return getEntityCapability(m_elist, _strtype);
  }


  /**
   *  Gets the entityCapability attribute of the PokBaseABR object
   *
   *@param  _elist  Description of the Parameter
   *@param  type    Description of the Parameter
   *@return         The entityCapability value
   */
  public String getEntityCapability(EntityList _elist, String type) {
    EntityGroup entGroup = _elist.getEntityGroup(type);
    //String desc = null;
    EANMetaAttribute ema = null;
    if (entGroup != null) {
      ema = entGroup.getMetaAttribute(type);
    }
    if (ema != null) {
      return ema.getCapability();
    }
    return "R";
  }


  /**
   *  Added by Bala
   *
   *@param  _strParentType   Description of the Parameter
   *@param  _strRelatorType  Description of the Parameter
   *@return                  The parents value
   */

  /**
   *  Get the parents of entityType through the relator of relatorType
   *
   *@param  _strParentType   Description of the Parameter
   *@param  _strRelatorType  Description of the Parameter
   *@return                  The parents value
   */
  public Vector getParents(String _strParentType, String _strRelatorType) {

    Vector parentVct = new Vector();
    Vector relVct = getAllLinkedParents(getRootEntityType(), getRootEntityID());

    for (int i = 0; i < relVct.size(); i++) {
      EntityItem eiRelator = (EntityItem) relVct.elementAt(i);
      if (eiRelator.getEntityType().equals(_strRelatorType)) {
        parentVct.addElement(eiRelator);
      }
    }

    return parentVct;
  }


  /**
   *  Get all children for the entity type and id through any relator This
   *  returns all children specified in the VE, id may = 0 Note: only one level
   *  will be checked
   *
   *@param  entityType  String specifying the entity type to match
   *@param  entityId    int specifying the entity id to match
   *@return             The allChildren value
   *@returns            Vector of LinkedEntityInfo, one for each child entity
   *      found
   */
  public Vector getAllChildren(String entityType, int entityId) {
    return getAllEntities(entityType, entityId, false, true);
  }


  /**
   *  Get all linked parents for the entity type and id through any relator This
   *  does not return all possible parents, only valid instances. Note: only one
   *  level will be checked
   *
   *@param  entityType  String specifying the entity type to match
   *@param  entityId    int specifying the entity id to match
   *@return             The allLinkedParents value
   *@returns            Vector of LinkedEntityInfo, one for each parent entity
   *      found
   */
  public Vector getAllLinkedParents(String entityType, int entityId) {
    return getAllEntities(entityType, entityId, true, false);
  }


  /**
   *  Get all linked children for the entity type and id through any relator
   *  This does not return all possible children, only valid instances. Note:
   *  only one level will be checked
   *
   *@param  entityType  String specifying the entity type to match
   *@param  entityId    int specifying the entity id to match
   *@return             The allLinkedChildren value
   *@returns            Vector of LinkedEntityInfo, one for each child entity
   *      found
   */
  public Vector getAllLinkedChildren(String entityType, int entityId) {
    return getAllEntities(m_elist, entityType, entityId, false, false);
  }


  /**
   *  Gets the allLinkedChildren attribute of the PokBaseABR object
   *
   *@param  _elist       Description of the Parameter
   *@param  _entityType  Description of the Parameter
   *@param  _entityId    Description of the Parameter
   *@return              The allLinkedChildren value
   */
  public Vector getAllLinkedChildren(EntityList _elist, String _entityType, int _entityId) {
    return getAllEntities(_elist, _entityType, _entityId, false, false);
  }


  /**
   *  Get all entities of the entity type through any relator.
   *
   *@param  _strEntityType  Description of the Parameter
   *@return                 The entityIds value
   *@returns                Vector of Integer, one for each entity found
   */
  public Vector getEntityIds(String _strEntityType) {
    return getEntityIds(m_elist, _strEntityType);
  }


  /**
   *  Gets the entityIds attribute of the PokBaseABR object
   *
   *@param  _elist          Description of the Parameter
   *@param  _strEntityType  Description of the Parameter
   *@return                 The entityIds value
   */
  public Vector getEntityIds(EntityList _elist, String _strEntityType) {

    Vector ids = new Vector(1);

    EntityGroup navGroup = _elist.getEntityGroup(_strEntityType);
    if (navGroup == null) {
      return ids;
    }

    // find all entities of the specified type
    for (int i = 0; i < navGroup.getEntityItemCount(); i++) {
      // one item for each instance of the relator
      EntityItem ni = navGroup.getEntityItem(i);
      if (ni.getEntityType().equals(_strEntityType)) {
        ids.addElement(new Integer(ni.getEntityID()));
      }
    }

    return ids;
  }


  /**
   *  Get all entities for the entity type and id through any relator. id may =0
   *  if getAll=true Note: only one level will be checked
   *
   *@param  _strEntityType  Description of the Parameter
   *@param  _iEntityId      Description of the Parameter
   *@param  _bGetParents    Description of the Parameter
   *@param  _bGetAll        Description of the Parameter
   *@return                 The allEntities value
   *@returns                Vector of EntityItems, one for each entity found
   */
  private Vector getAllEntities(String _strEntityType, int _iEntityId, boolean _bGetParents, boolean _bGetAll) {
    return getAllEntities(m_elist, _strEntityType, _iEntityId, _bGetParents, _bGetAll);
  }


  /**
   *  Gets the allEntities attribute of the PokBaseABR object
   *
   *@param  _elist      Description of the Parameter
   *@param  entityType  Description of the Parameter
   *@param  entityId    Description of the Parameter
   *@param  getParents  Description of the Parameter
   *@param  getAll      Description of the Parameter
   *@return             The allEntities value
   */
  private Vector getAllEntities(EntityList _elist, String entityType, int entityId, boolean getParents, boolean getAll) {
    Vector links = new Vector(1);

    // get the navigation group for the relator
    for (int j = 0; j < _elist.getEntityGroupCount(); j++) {
      EntityGroup entGroup = _elist.getEntityGroup(j);
      // look at each type of entity/relator
      // get any entities defined in the VE that are not instantiated
      // if the entity does not exist there will not be a navigate item for it
      if (getAll && entGroup.getEntityItemCount() == 0) {
        // relators have e1->e2 type information
        if (entGroup.isRelator()) {
          if (getParents) {
            if (entGroup.getEntity1Type().equals(entityType)) {
              links.addElement(entGroup.getEntityItem(entityType));
            }
          } else {
            if (entGroup.getEntity2Type().equals(entityType)) {
              links.addElement(entGroup.getEntityItem(entityType));
            }
          }
        }
      }

      // find all entities of the specified type
      for (int i = 0; i < entGroup.getEntityItemCount(); i++) {
        // one item for each instance of the relator
        EntityItem eiMyItems = entGroup.getEntityItem(i);
        // check links through this relator
        int ilevelZeroLinks = 0;
        if (getParents) {
          ilevelZeroLinks = eiMyItems.getUpLinkCount();
        } else {
          ilevelZeroLinks = eiMyItems.getDownLinkCount();
        }
        for (int t = 0; t < ilevelZeroLinks; t++) {
          EntityItem eio = null;
          if (getParents) {
            eio = (EntityItem) eiMyItems.getUpLink(t);
          } else {
            eio = (EntityItem) eiMyItems.getDownLink(t);
          }
          if (entityId != 0) {
            if (eio.getEntityType().equals(entityType) && eio.getEntityID() == entityId) {
              // returning a set of EntityItems is possible
              // it would be a relator navitem (the eiMyItems)

              links.addElement(eio);
            }
          } else {                                     //Get em if it matches entitytype
            if (eio.getEntityType().equals(entityType)) {
              // returning a set of EntityItems is possible
              // it would be a relator navitem (the eiMyItems)

              links.addElement(eio);
            }
          }
        }
      }
    }

    return links;
  }



  /**
   *  Gets the attributeValue attribute of the PokBaseABR object
   *
   *@param  _strEntityType  Description of the Parameter
   *@param  m_iEntityId     Description of the Parameter
   *@param  m_strAttrCode   Description of the Parameter
   *@return                 The attributeValue value
   */
  public String getAttributeValue(String _strEntityType, int m_iEntityId, String m_strAttrCode) {
    return getAttributeValue(m_elist, _strEntityType, m_iEntityId, m_strAttrCode);
  }


  /**
   *  Gets the attributeValue attribute of the PokBaseABR object
   *
   *@param  _strEntityType   Description of the Parameter
   *@param  m_iEntityId      Description of the Parameter
   *@param  m_strAttrCode    Description of the Parameter
   *@param  m_strDefaultStr  Description of the Parameter
   *@return                  The attributeValue value
   */
  public String getAttributeValue(String _strEntityType, int m_iEntityId, String m_strAttrCode, String m_strDefaultStr) {
    return getAttributeValue(m_elist, _strEntityType, m_iEntityId, m_strAttrCode, m_strDefaultStr);
  }


  /**
   *  Get the current Value for the specified attribute, default string if not
   *  set
   *
   *@param  _elist           Description of the Parameter
   *@param  _strEntityType   Description of the Parameter
   *@param  m_iEntityId      Description of the Parameter
   *@param  m_strAttrCode    Description of the Parameter
   *@param  m_strDefaultStr  Description of the Parameter
   *@return                  The attributeValue value
   *@returns                 String attribute value
   */
  public String getAttributeValue(EntityList _elist, String _strEntityType, int m_iEntityId, String m_strAttrCode, String m_strDefaultStr) {
    String value = getAttributeValue(_elist, _strEntityType, m_iEntityId, m_strAttrCode);
    if (value == null) {
      value = m_strDefaultStr;
    }
    return value;
  }


  /**
   *  Get the current Value for the specified attribute, null if not set
   *
   *@param  _elist          Description of the Parameter
   *@param  _strEntityType  Description of the Parameter
   *@param  _iEid          Description of the Parameter
   *@param  m_strAttrCode   Description of the Parameter
   *@return                 The attributeValue value
   *@returns                String attribute value
   */
  public String getAttributeValue(EntityList _elist, String _strEntityType, int _iEid, String m_strAttrCode) {
    String StrRetValue = null;
    //String strFlagValues = null;

    D.ebug(D.EBUG_SPEW, "In getAttributeValue _strEntityType:" + _strEntityType + ":_iEid:" + _iEid + ":m_strAttrCode:" + m_strAttrCode);
    EntityGroup eg = _elist.getEntityGroup(_strEntityType);

   if (eg == null) {
      EntityGroup egp = _elist.getParentEntityGroup();
      if (egp != null && egp.getEntityType().equals(_strEntityType)) {
         eg = egp;
      }
   }
    if (eg == null) {
      D.ebug(D.EBUG_ERR, "getAttributeValue:entitygroup is null");
      return null;
    }

    if (_strEntityType.equals(getEntityType()) && _iEid==getEntityID()) {      //get the parent entity group for root entity
      eg = _elist.getParentEntityGroup();
    }
    EntityItem ei = eg.getEntityItem(_strEntityType, _iEid);
    if (ei == null) {
      logMessage("getAttributeValue:entityitem is null");
      return null;
    }

    EANAttribute EANAttr = ei.getAttribute(m_strAttrCode);

    if (EANAttr == null) {
      logMessage("getAttributeValue:entityattribute is null");
      return null;
    }
    StrRetValue = EANAttr.toString();

    EANMetaAttribute eanMetaAtt = eg.getMetaAttribute(m_strAttrCode);
    switch (eanMetaAtt.getAttributeType().charAt(0)) {
        case 'F':
        case 'U':
        case 'S':
          StrRetValue = StrRetValue.replace('\n', ' ');//Take out all the Carriage returns for flags
          break;
        default:

    }
    D.ebug(D.EBUG_SPEW,"Attribute values are " + StrRetValue);

    return StrRetValue;
  }

  public String getAttributeValue(EntityItem _ei, String _strAttrCode,String _strDefault) {
    String strRetValue = _strDefault;
    //String strFlagValues = null;

    if (_ei == null) {
      logMessage("getAttributeValue:entityitem is null");
      return strRetValue;
    }

    D.ebug(D.EBUG_SPEW,"In getAttributeValue "+_ei.getKey());
    EntityGroup eg = _ei.getEntityGroup();

    if (eg == null) {
      logMessage("getAttributeValue:entitygroup is null");
      return strRetValue;
    }


    EANAttribute EANAttr = _ei.getAttribute(_strAttrCode);

    if (EANAttr == null) {
      logMessage("getAttributeValue:entityattribute "+_strAttrCode+ " is null");
      return strRetValue;
    }
    strRetValue = EANAttr.toString();

    EANMetaAttribute eanMetaAtt = eg.getMetaAttribute(_strAttrCode);
    switch (eanMetaAtt.getAttributeType().charAt(0)) {
        case 'F':
        case 'U':
        case 'S':
          strRetValue = strRetValue.replace('\n', ' ');//Take out all the Carriage returns for flags
          break;
        default:

    }
    D.ebug(D.EBUG_SPEW,"Attribute values are " + strRetValue);

    return strRetValue;
  }

  /**
   *  Get the current Flag Value for the specified attribute, null if not set
   *
   *@param  _strEntityType  Description of the Parameter
   *@param  _iEntityId      Description of the Parameter
   *@param  _strAttrCode    Description of the Parameter
   *@return                 The attributeFlagValue value
   *@returns                String attribute flag code
   */
  public String getAttributeFlagValue(String _strEntityType, int _iEntityId, String _strAttrCode) {
    return getAttributeValue(_strEntityType, _iEntityId, _strAttrCode);
  }


  /**
   *  Gets the attributeFlagEnabledValue attribute of the PokBaseABR object
   *
   *@param  _strEntityType   Description of the Parameter
   *@param  m_iEntityId      Description of the Parameter
   *@param  m_strAttrCode    Description of the Parameter
   *@param  m_strDefaultStr  Description of the Parameter
   *@return                  The attributeFlagEnabledValue value
   */
  public String getAttributeFlagEnabledValue(String _strEntityType, int m_iEntityId, String m_strAttrCode, String m_strDefaultStr) {
    return getAttributeFlagEnabledValue(m_elist, _strEntityType, m_iEntityId, m_strAttrCode, m_strDefaultStr);
  }


  /**
   *  Gets the attributeFlagEnabledValue attribute of the PokBaseABR object
   *
   *@param  _elist           Description of the Parameter
   *@param  _strEntityType   Description of the Parameter
   *@param  m_iEntityId      Description of the Parameter
   *@param  m_strAttrCode    Description of the Parameter
   *@param  m_strDefaultStr  Description of the Parameter
   *@return                  The attributeFlagEnabledValue value
   */
  public String getAttributeFlagEnabledValue(EntityList _elist, String _strEntityType, int m_iEntityId, String m_strAttrCode, String m_strDefaultStr) {
    String value = getAttributeFlagEnabledValue(_elist, _strEntityType, m_iEntityId, m_strAttrCode);
    if (value == null) {
      value = m_strDefaultStr;
    }
    return value;
  }


  /**
   *  Gets the attributeFlagEnabledValue attribute of the PokBaseABR object
   *
   *@param  _elist          Description of the Parameter
   *@param  _strEntityType  Description of the Parameter
   *@param  _iEid          Description of the Parameter
   *@param  m_strAttrCode   Description of the Parameter
   *@return                 The attributeFlagEnabledValue value
   */
  public String getAttributeFlagEnabledValue(EntityList _elist, String _strEntityType, int _iEid, String m_strAttrCode) {
    String StrRetValue = null;
    String strFlagValues = "";

    D.ebug(D.EBUG_SPEW, "In getAttributeFlagEnabledValue _strEntityType:" + _strEntityType + ":_iEid:" + _iEid + ":m_strAttrCode:" + m_strAttrCode);
    EntityGroup eg = _elist.getEntityGroup(_strEntityType);

    if (eg == null) {
    	D.ebug(D.EBUG_ERR, "getAttributeFlagEnabledValue:entitygroup is null");
      return null;
    }

    if (_strEntityType.equals(getEntityType())) {      //get the parent entity group for root entity
      eg = _elist.getParentEntityGroup();
    }
    EntityItem ei = eg.getEntityItem(_strEntityType, _iEid);
    if (ei == null) {
    	D.ebug(D.EBUG_ERR, "getAttributeFlagEnabledValue:entityitem is null");
      return null;
    }

    EANAttribute EANAttr = ei.getAttribute(m_strAttrCode);

    if (EANAttr == null) {
    	D.ebug(D.EBUG_ERR, "getAttributeValue:entityattribute is null");
      return null;
    }
    StrRetValue = EANAttr.toString();

    EANMetaAttribute eanMetaAtt = eg.getMetaAttribute(m_strAttrCode);
    switch (eanMetaAtt.getAttributeType().charAt(0)) {
        case 'F':
        case 'U':
        case 'S':
          MetaFlag[] mfAttr = (MetaFlag[]) EANAttr.get();
          boolean bFirst = true;
          for (int i = 0; i < mfAttr.length; i++) {
            if (mfAttr[i].isSelected()) {
              if (bFirst) {
               bFirst = false;
                strFlagValues = mfAttr[i].getFlagCode();
              } else {
                strFlagValues += "|" + mfAttr[i].getFlagCode();
              }
            }
          }
          StrRetValue = strFlagValues;
          break;
        default:

    }

    return StrRetValue;
  }


  public String getAttributeFlagEnabledValue(EntityItem ei, String m_strAttrCode) {
    String StrRetValue = "";
    String strFlagValues = "";
    if (ei == null) {
    	D.ebug(D.EBUG_ERR, "getAttributeFlagEnabledValue:entityitem is null");
      return null;
    }

    D.ebug(D.EBUG_SPEW, "In getAttributeFlagEnabledValue "+ei.getKey()+":" + m_strAttrCode);
    EntityGroup eg = ei.getEntityGroup();


    EANAttribute EANAttr = ei.getAttribute(m_strAttrCode);

    if (EANAttr == null) {
    	D.ebug(D.EBUG_ERR, "getAttributeFlagEnabledValue:entityattribute "+m_strAttrCode+ " is null");
      return null;
    }
    StrRetValue = EANAttr.toString();

    EANMetaAttribute eanMetaAtt = eg.getMetaAttribute(m_strAttrCode);
    switch (eanMetaAtt.getAttributeType().charAt(0)) {
        case 'F':
        case 'U':
        case 'S':
          MetaFlag[] mfAttr = (MetaFlag[]) EANAttr.get();
          boolean bFirst = true;
          for (int i = 0; i < mfAttr.length; i++) {
            if (mfAttr[i].isSelected()) {
              if (bFirst) {
               bFirst = false;
                strFlagValues = mfAttr[i].getFlagCode();
              } else {
                strFlagValues += "|" + mfAttr[i].getFlagCode();
              }
            }
          }
          StrRetValue = strFlagValues;
          break;
        default:

    }

    return StrRetValue;
  }



  /**
   *  Gets the getAttributeShortFlagDesc attribute of the PokBaseABR object
   *
   *@param  _strEntityType   Description of the Parameter
   *@param  m_iEntityId      Description of the Parameter
   *@param  m_strAttrCode    Description of the Parameter
   *@param  m_strDefaultStr  Description of the Parameter
   *@return                  The attributeFlagEnabledValue value
   */
  public String getAttributeShortFlagDesc(String _strEntityType, int m_iEntityId, String m_strAttrCode, String m_strDefaultStr) {
    return getAttributeShortFlagDesc(m_elist, _strEntityType, m_iEntityId, m_strAttrCode, m_strDefaultStr);
  }


  /**
   *  Gets the attributeFlagEnabledValue attribute of the PokBaseABR object
   *
   *@param  _elist           Description of the Parameter
   *@param  _strEntityType   Description of the Parameter
   *@param  m_iEntityId      Description of the Parameter
   *@param  m_strAttrCode    Description of the Parameter
   *@param  m_strDefaultStr  Description of the Parameter
   *@return                  The attributeFlagEnabledValue value
   */
  public String getAttributeShortFlagDesc(EntityList _elist, String _strEntityType, int m_iEntityId, String m_strAttrCode, String m_strDefaultStr) {
    String value = getAttributeShortFlagDesc(_elist, _strEntityType, m_iEntityId, m_strAttrCode);
    if (value == null) {
      value = m_strDefaultStr;
    }
    return value;
  }


  /**
   *  Gets the attributeFlagEnabledValue attribute of the PokBaseABR object
   *
   *@param  _elist          Description of the Parameter
   *@param  _strEntityType  Description of the Parameter
   *@param  _iEid          Description of the Parameter
   *@param  m_strAttrCode   Description of the Parameter
   *@return                 The attributeFlagEnabledValue value
   */
  public String getAttributeShortFlagDesc(EntityList _elist, String _strEntityType, int _iEid, String m_strAttrCode) {
    String StrRetValue = null;
    String strFlagValues = "";

    D.ebug(D.EBUG_SPEW, "In getAttributeShortFlagDesc _strEntityType:" + _strEntityType + ":_iEid:" + _iEid + ":m_strAttrCode:" + m_strAttrCode);
    EntityGroup eg = _elist.getEntityGroup(_strEntityType);

    if (eg == null) {
    	D.ebug(D.EBUG_ERR, "getAttributeShortFlagDesc:entitygroup is null");
      return null;
    }

    if (_strEntityType.equals(getEntityType())) {      //get the parent entity group for root entity
      eg = _elist.getParentEntityGroup();
    }
    EntityItem ei = eg.getEntityItem(_strEntityType, _iEid);
    if (ei == null) {
    	D.ebug(D.EBUG_ERR,"getAttributeShortFlagDesc:entityitem is null");
      return null;
    }

    EANAttribute EANAttr = ei.getAttribute(m_strAttrCode);

    if (EANAttr == null) {
    	D.ebug(D.EBUG_ERR,"getAttributeShortFlagDesc:entityattribute is null");
      return null;
    }
    StrRetValue = EANAttr.toString();

    EANMetaAttribute eanMetaAtt = eg.getMetaAttribute(m_strAttrCode);
    switch (eanMetaAtt.getAttributeType().charAt(0)) {
        case 'F':
        case 'U':
        case 'S':
          MetaFlag[] mfAttr = (MetaFlag[]) EANAttr.get();
          for (int i = 0; i < mfAttr.length; i++) {
            if (mfAttr[i].isSelected()) {
              if (mfAttr[i].getShortDescription()!=null)  {
                if (strFlagValues.trim().length() > 0 ) {
                  strFlagValues += ", " + mfAttr[i].getShortDescription();
                } else {
                strFlagValues = mfAttr[i].getShortDescription();
                }
              }else {
            	  D.ebug(D.EBUG_ERR,"getAttributeShortFlagDesc:NULL returned for "+mfAttr[i].getFlagCode());
              }
            }
          }
          StrRetValue = strFlagValues;
          break;
        default:

    }
    D.ebug(D.EBUG_SPEW,"getAttributeShortFlagDesc:Attribute values are " + strFlagValues);

    return StrRetValue;
  }

  public String getAttributeShortFlagDesc(EntityItem _ei, String m_strAttrCode, String m_strDefaultStr) {
    String value = getAttributeShortFlagDesc(_ei, m_strAttrCode);
    if (value == null) {
      value = m_strDefaultStr;
    }
    return value;
  }

  public String getAttributeLongFlagDesc(EntityItem _ei, String _strAttrCode) {
    String strRetValue = null;
    String strFlagValues = "";
    EANAttribute EANAttr = _ei.getAttribute(_strAttrCode);

    if (EANAttr == null) {
    	D.ebug(D.EBUG_ERR,"getAttributeLongFlagDesc:entityattribute is null");
      return null;
    }
    strRetValue = EANAttr.toString();
    EntityGroup eg= _ei.getEntityGroup();

    EANMetaAttribute eanMetaAtt = eg.getMetaAttribute(_strAttrCode);
    switch (eanMetaAtt.getAttributeType().charAt(0)) {
        case 'F':
        case 'U':
        case 'S':
          MetaFlag[] mfAttr = (MetaFlag[]) EANAttr.get();
          for (int i = 0; i < mfAttr.length; i++) {
            if (mfAttr[i].isSelected()) {
              if (mfAttr[i].getShortDescription()!=null)  {
                if (strFlagValues.trim().length() > 0 ) {
                  strFlagValues += "| " + mfAttr[i].getLongDescription();
                } else {
                strFlagValues = mfAttr[i].getLongDescription();
                }
              }else {
            	  D.ebug(D.EBUG_ERR,"getAttributeLongFlagDesc:NULL returned for "+mfAttr[i].getFlagCode());
              }
            }
          }
          strRetValue = strFlagValues;
          break;
        default:

    }
    D.ebug(D.EBUG_SPEW,"getAttributeLongFlagDesc:Attribute values are " + strFlagValues);

    return strRetValue;

  }
  public String getAttributeShortFlagDesc(EntityItem ei, String m_strAttrCode) {
    String StrRetValue = null;
    String strFlagValues = "";
    if (ei == null) {
    	D.ebug(D.EBUG_ERR,"getAttributeShortFlagDesc:entityitem is null");
      return null;
    }

    D.ebug(D.EBUG_SPEW,"In getAttributeShortFlagDesc : "+ei.getKey()+"m_strAttrCode:" + m_strAttrCode);
    EntityGroup eg = ei.getEntityGroup();

    if (eg == null) {
    	D.ebug(D.EBUG_ERR,"getAttributeShortFlagDesc:entitygroup is null");
      return null;
    }



    EANAttribute EANAttr = ei.getAttribute(m_strAttrCode);

    if (EANAttr == null) {
    	D.ebug(D.EBUG_ERR,"getAttributeShortFlagDesc:entityattribute is null");
      return null;
    }
    StrRetValue = EANAttr.toString();

    EANMetaAttribute eanMetaAtt = eg.getMetaAttribute(m_strAttrCode);
    switch (eanMetaAtt.getAttributeType().charAt(0)) {
        case 'F':
        case 'U':
        case 'S':
          MetaFlag[] mfAttr = (MetaFlag[]) EANAttr.get();
          for (int i = 0; i < mfAttr.length; i++) {
            if (mfAttr[i].isSelected()) {
              if (mfAttr[i].getShortDescription()!=null)  {
                if (strFlagValues.trim().length() > 0 ) {
                  strFlagValues += ", " + mfAttr[i].getShortDescription();
                } else {
                strFlagValues = mfAttr[i].getShortDescription();
                }
              }else {
            	  D.ebug(D.EBUG_ERR,"getAttributeShortFlagDesc:NULL returned for "+mfAttr[i].getFlagCode());
              }
            }
          }
          StrRetValue = strFlagValues;
          break;
        default:

    }
    D.ebug(D.EBUG_SPEW,"getAttributeShortFlagDesc:Attribute values are " + strFlagValues);

    return StrRetValue;
  }


  //****************************************************************


  /**
   *  Get all entity ids in the entity list for the entity type through the
   *  relator specified
   *
   *@param  _strEntityType   Description of the Parameter
   *@param  _strRelatorType  Description of the Parameter
   *@return                  The entityIds value
   *@returns                 Vector of int, one for each entity found
   */
  public Vector getEntityIds(String _strEntityType, String _strRelatorType) {
    return getEntityIds(m_elist, _strEntityType, _strRelatorType);
  }


  /**
   *  Gets the entityIds attribute of the PokBaseABR object
   *
   *@param  _elist           Description of the Parameter
   *@param  _strEntityType   Description of the Parameter
   *@param  _strRelatorType  Description of the Parameter
   *@return                  The entityIds value
   */
  public Vector getEntityIds(EntityList _elist, String _strEntityType, String _strRelatorType) {

    Vector ids = new Vector(1);

    // get the navigation group for the relator
    EntityGroup entGroup = _elist.getEntityGroup(_strRelatorType);

    if (entGroup == null) {
      return ids;
    }

    if (entGroup.getEntityItemCount() == 0) {
      return ids;
    }

    // find all entities of the specified type
    for (int i = 0; i < entGroup.getEntityItemCount(); i++) {
      EntityItem ni = entGroup.getEntityItem(i);
      for (int t = 0; t < ni.getDownLinkCount(); t++) {
        if (ni.getDownLink(t).getEntityType().equals(_strEntityType)) {
          Integer eid = new Integer(ni.getDownLink(t).getEntityID());
          if (!ids.contains(eid)) {
            ids.addElement(eid);
          }
        }
      }
      // check ancestors, could be a parent or child
      for (int t = 0; t < ni.getUpLinkCount(); t++) {
        if (ni.getUpLink(t).getEntityType().equals(_strEntityType)) {
          Integer eid = new Integer(ni.getUpLink(t).getEntityID());
          if (!ids.contains(eid)) {
            ids.addElement(eid);
          }
        }
      }
    }
    return ids;
  }


  /**
   *  Get all parent entity ids through the specified relator for the child type
   *  and id
   *
   *@param  _strChildType        Description of the Parameter
   *@param  _intChildID          Description of the Parameter
   *@param  _strParentType       Description of the Parameter
   *@param  _strRelatorType        Description of the Parameter
   *@return                      The parentEntityIds value
   *@returns                     Vector of int, one for each entity found
   */
  public Vector getParentEntityIds(String _strChildType, int _iChildID, String _strParentType, String _strRelatorType) {
    return getParentEntityIds(m_elist, _strChildType, _iChildID, _strParentType, _strRelatorType);
  }


  /**
   *  Gets the parentEntityIds attribute of the PokBaseABR object
   *
   *@param  _elist               Description of the Parameter
   *@param  _strChildType        Description of the Parameter
   *@param  _intChildID          Description of the Parameter
   *@param  _strParentType       Description of the Parameter
   *@param  _strParentChildType  Description of the Parameter
   *@return                      The parentEntityIds value
   */
  public Vector getParentEntityIds(EntityList _elist, String _strChildType, int _iChildID, String _strParentType, String _strRelatorType) {
    Vector ids = new Vector(1);

    // get the navigation group for the relator
    EntityGroup entGroup = null;
    if (_strRelatorType.equals(getEntityType())) {      //get the parent entity group for root entity
      entGroup = _elist.getParentEntityGroup();
    } else {
       entGroup = _elist.getEntityGroup(_strRelatorType);
    }

    if (entGroup == null) {
      return ids;
    }

System.out.println("PokBaseABR getParentEntityIds entGroup: " + entGroup.getEntityType() + ":" + entGroup.getEntityItemCount());
    if (entGroup.getEntityItemCount() == 0) {
      return ids;
    }


    // find all entities of the specified type
    for (int i = 0; i < entGroup.getEntityItemCount(); i++) {
      EntityItem ei = entGroup.getEntityItem(i);

      // entities are children and parents of the relator
      // check if parent matches this item
      for (int t = 0; t < ei.getDownLinkCount(); t++) {
        if (ei.getDownLink(t).getEntityType().equals(_strChildType) && ei.getDownLink(t).getEntityID() == _iChildID) {
          for (int at = 0; at < ei.getUpLinkCount(); at++) {
            // match on destination.. parent here
            if (ei.getUpLink(at).getEntityType().equals(_strParentType)) {
              ids.addElement(new Integer(ei.getUpLink(at).getEntityID()));
            }
          }
        }
      }
    }
/*
    Vector ids = new Vector(1);
    // get the navigation group for the relator
    EntityGroup entGroup = null;
    if (_strChildType.equals(getEntityType())) {      //get the parent entity group for root entity
      entGroup = _elist.getParentEntityGroup();
    } else {
       entGroup = _elist.getEntityGroup(_strChildType);
    }
    if (entGroup == null) {
      return ids;
    }
    // find all entities of the specified type
    for (int ii = 0; ii < entGroup.getEntityItemCount(); ii++) {
      EntityItem ei = entGroup.getEntityItem(ii);
      for (int iy = 0; iy < ei.getUpLinkCount(); iy++) {
         EntityItem eiRelator = (EntityItem)ei.getUpLink(iy);
        if (eiRelator.getEntityType().equals(_strRelatorType)) {
          for (int iz = 0; iz < eiRelator.getUpLinkCount(); iz++) {
            EntityItem eiParent = (EntityItem)eiRelator.getUpLink(iz);

            if (eiParent.getEntityType().equals(_strParentType)) {
              ids.addElement(new Integer(eiParent.getEntityID()));
            }
          }
        }
      }
    }
*/
    return ids;
  }


  /**
   *  Get a single parent entity id through the specified relator
   *
   *@param  childType    String specifying the child type to start from
   *@param  childId      int specifying the child id to start from
   *@param  destType     String specifying the entity type to find
   *@param  relatorType  String specifying the relator type to go thru
   *@return              The parentEntityId value
   *@returns             int entity id, 0 if not found
   */
  public int getParentEntityId(String childType, int childId, String destType, String relatorType) {
    return getParentEntityId(m_elist, childType, childId, destType, relatorType);
  }


  /**
   *  Gets the parentEntityId attribute of the PokBaseABR object
   *
   *@param  _elist       Description of the Parameter
   *@param  childType    Description of the Parameter
   *@param  childId      Description of the Parameter
   *@param  destType     Description of the Parameter
   *@param  relatorType  Description of the Parameter
   *@return              The parentEntityId value
   */
  public int getParentEntityId(EntityList _elist, String childType, int childId, String destType, String relatorType) {
    Vector ids = getParentEntityIds(_elist, childType, childId, destType, relatorType);
    if (ids.size() > 0) {
      Integer entId = (Integer) ids.elementAt(0);
      return entId.intValue();
    }
    return 0;
  }


  /**
   *  Get a single child entity id through the specified relator for the parent
   *  type and id
   *
   *@param  parentType   String specifying the parent type to start from
   *@param  parentId     int specifying the parent id to start from
   *@param  destType     String specifying the entity type to find
   *@param  relatorType  String specifying the relator type to go thru
   *@return              The childEntityId value
   *@returns             int entity id, 0 if not found
   */
  public int getChildEntityId(String parentType, int parentId, String destType, String relatorType) {
    Vector ids = getChildrenEntityIds(parentType, parentId, destType, relatorType);
    if (ids.size() > 0) {
      Integer entId = (Integer) ids.elementAt(0);
      return entId.intValue();
    }
    return 0;
  }


  /**
   *  Gets the childrenEntityIds attribute of the PokBaseABR object
   *
   *@param  _strParentType       Description of the Parameter
   *@param  _iParentId           Description of the Parameter
   *@param  _strChildEtype       Description of the Parameter
   *@param  _strParentChildType  Description of the Parameter
   *@return                      The childrenEntityIds value
   */
  public Vector getChildrenEntityIds(String _strParentType, int _iParentId, String _strChildEtype, String _strParentChildType) {
    return getChildrenEntityIds(m_elist, _strParentType, _iParentId, _strChildEtype, _strParentChildType);
  }


  /**
   *  Get Children Entity Id's of an entity given the child entitytype
   *
   *@param  _elist               Description of the Parameter
   *@param  _strParentType       Description of the Parameter
   *@param  _iParentId           Description of the Parameter
   *@param  _strChildEtype       Description of the Parameter
   *@param  _strParentChildType  Description of the Parameter
   *@return                      Vector of integer entityids.
   */
  public Vector getChildrenEntityIds(EntityList _elist, String _strParentType, int _iParentId, String _strChildEtype, String _strParentChildType) {
    Vector ids = new Vector(1);

    // get the navigation group for the relator
    EntityGroup entGroup = null;
    if (_strParentChildType.equals(getEntityType())) {      //get the parent entity group for root entity
      entGroup = _elist.getParentEntityGroup();
    } else {
       entGroup = _elist.getEntityGroup(_strParentChildType);
    }

    if (entGroup == null) {
      return ids;
    }

    if (entGroup.getEntityItemCount() == 0) {
      return ids;
    }

    // find all entities of the specified type
    for (int i = 0; i < entGroup.getEntityItemCount(); i++) {
      EntityItem ei = entGroup.getEntityItem(i);

      // entities are children and parents of the relator
      // check if parent matches this item
      for (int t = 0; t < ei.getUpLinkCount(); t++) {
        if (ei.getUpLink(t).getEntityType().equals(_strParentType) && ei.getUpLink(t).getEntityID() == _iParentId) {
          for (int at = 0; at < ei.getDownLinkCount(); at++) {
            // match on destination.. parent here
            if (ei.getDownLink(at).getEntityType().equals(_strChildEtype)) {
              ids.addElement(new Integer(ei.getDownLink(at).getEntityID()));
            }
          }
        }
      }
    }

    return ids;
  }
//********************************************************************

  /**
   *  Insert the method's description here. Creation date: (9/4/2001 10:04:33
   *  AM)
   *
   *@param  _strEntityType  Description of the Parameter
   */
  public void hide(String _strEntityType) {
    m_hsetSkipType.add(_strEntityType);
  }


  /**
   *  Insert the method's description here. This may never get out of the
   *  recursive loop, so I introduced a counter.. i
   *
   *@param  _strEntityType  Description of the Parameter
   *@param  _iEntityID      Description of the Parameter
   *@param  _strToType      Description of the Parameter
   *@param  _strPath        Description of the Parameter
   */
  public final void printPathDown(String _strEntityType, int _iEntityID, String _strToType, String _strPath) {
    printPathDown(_strEntityType, _iEntityID, _strToType, _strPath, 0);
    if (m_iErrors > 0) {
      println("<h3>Warning: There were " + m_iErrors + " paths that were not m_hDisplayed because they had more than " + PRINT_DOWN_LEVEL + " entities in them.</h3>");
    }
  }

  // Hard stop at seven levels
  //
  /**
   *  Description of the Method
   *
   *@param  _strEntityType  Description of the Parameter
   *@param  _iEntityID      Description of the Parameter
   *@param  _strToType      Description of the Parameter
   *@param  _strPath        Description of the Parameter
   *@param  _i              Description of the Parameter
   */
  private final void printPathDown(String _strEntityType, int _iEntityID, String _strToType, String _strPath, int _i) {
    if (_strToType.equals(_strEntityType)) {
      println("<tr>\n" + _strPath + "</tr>");
    }
    if (_i > PRINT_DOWN_LEVEL) {
      m_iErrors++;
      return;
    }

    _i++;

    String strNAME = getAttributeValue(_strEntityType, _iEntityID, "NAME", DEF_NOT_POPULATED_HTML);
    if (m_hDisplay.containsKey(_strEntityType)) {
      strNAME =
          getAttributeValue(
          _strEntityType,
          _iEntityID,
          (String) m_hDisplay.get(_strEntityType)
          , DEF_NOT_POPULATED_HTML);
    }

    Vector children = getAllLinkedChildren(_strEntityType, _iEntityID);
    for (int i = 0; i < children.size(); i++) {
      EntityItem child = (EntityItem) children.elementAt(i);
      String strStep = "";
      if (!m_hsetSkipType.contains(_strEntityType)) {
        strStep =
            "<td class=\"PsgText\"><!--" +
            getEntityDescription(_strEntityType) +
            "-->" +
            strNAME +
            "</td>\n";
      }
      printPathDown(
          child.getEntityType(),
          child.getEntityID(),
          _strToType,
          _strPath + strStep, _i);
    }
  }

//********************************************************* Bala

  /**
   *  Insert the method's description here. Creation date: (9/4/2001 10:03:16
   *  AM)
   *
   *@param  _strEntityType  Description of the Parameter
   */
  public void resetDisplay(String _strEntityType) {
    m_hDisplay.remove(_strEntityType);
  }


  /**
   *  Insert the method's description here. Creation date: (9/4/2001 10:03:16
   *  AM)
   *
   *@param  _strEntityType  The new display value
   *@param  _strAttrCode    The new display value
   */
  public void setDisplay(String _strEntityType, String _strAttrCode) {
    m_hDisplay.put(_strEntityType, _strAttrCode);
  }


  /**
   *  Insert the method's description here. Creation date: (9/4/2001 10:04:33
   *  AM)
   *
   *@param  _strEntityType  Description of the Parameter
   */
  public void show(String _strEntityType) {
    m_hsetSkipType.remove(_strEntityType);
  }


  /**
   *  Insert the method's description here. Creation date: (8/1/2001 8:23:46 AM)
   *
   *@param  _strEntityType  Description of the Parameter
   *@param  _iEntityID      Description of the Parameter
   *@param  _iLevel         Description of the Parameter
   */
  public void printEntity(String _strEntityType, int _iEntityID, int _iLevel) {
	  D.ebug(D.EBUG_SPEW,"In printEntity _strEntityType" + _strEntityType + ":_iEntityID:" + _iEntityID + ":_iLevel:" + _iLevel);

    String strPSGClass = "";
    switch (_iLevel) {
        case 0:
        {
          strPSGClass = "PsgReportSection";
          break;
        }
        case 1:
        {
          strPSGClass = "PsgReportSectionII";
          break;
        }
        case 2:
        {
          strPSGClass = "PsgReportSectionIII";
          break;
        }
        case 3:
        {
          strPSGClass = "PsgReportSectionIV";
          break;
        }
        default:
        {
          strPSGClass = "PsgReportSectionV";
          break;
        }
    }
    D.ebug(D.EBUG_SPEW,"Printing table width");
    println("<table width=\"100%\"><tr><td class=\"" + strPSGClass + "\">" + getEntityDescription(_strEntityType) + ": " + getAttributeValue(_strEntityType, _iEntityID, "NAME", DEF_NOT_POPULATED_HTML) + "</td></tr></table>");
    D.ebug(D.EBUG_SPEW,"Printing Attributes");
    printAttributes(_strEntityType, _iEntityID, false, false);
  }


  /**
   *  Insert the method's description here. Creation date: (8/1/2001 8:23:46 AM)
   *
   *@param  _strEntityType  Description of the Parameter
   *@param  _iEntityID      Description of the Parameter
   *@param  _bAll           Description of the Parameter
   *@param  _bShortDesc     Description of the Parameter
   */
  public void printAttributes(String _strEntityType, int _iEntityID, boolean _bAll, boolean _bShortDesc) {
    printAttributes(m_elist, _strEntityType, _iEntityID, _bAll, _bShortDesc);
  }


  /**
   *  Description of the Method
   *
   *@param  _elist          Description of the Parameter
   *@param  _strEntityType  Description of the Parameter
   *@param  _iEntityID      Description of the Parameter
   *@param  _bAll           Description of the Parameter
   *@param  _bShortDesc     Description of the Parameter
   */
  public void printAttributes(EntityList _elist, String _strEntityType, int _iEntityID, boolean _bAll, boolean _bShortDesc) {
    //Get list of attributes for entity
	  D.ebug(D.EBUG_SPEW,"in Print Attributes _strEntityType" + _strEntityType + ":_iEntityID:" + _iEntityID);

    EntityItem entItem = null;
    EntityGroup eg = null;
    if (_strEntityType.equals(getEntityType())) {      //get the parent entity group for root entity
      eg = _elist.getParentEntityGroup();
    } else {
       eg = _elist.getEntityGroup(_strEntityType);
    }

    if (eg == null) {
      println("<h3>Warning: Cannot locate an EnityGroup for " + _strEntityType + " so no attributes will be printed.</h3>");
      return;
      }

      entItem = eg.getEntityItem(_strEntityType,_iEntityID);
      //EntityItem entItem = entGroup.getEntityItem(_strEntityType,_iEntityID);
    if (entItem == null) {
      // Lets display the EntityType, entityId in the root..)
      entItem = eg.getEntityItem(0);
      println("<h3>Warning: Attributes for " + _strEntityType + ":" + _iEntityID + " cannot be printed as it is not available in the Extract.</h3>");
      println("<h3>Warning: Root Entityis " + getEntityType() + ":" + getEntityID() + ".</h3>");
      return;
    }

    String streDesc = eg.getLongDescription();
    D.ebug(D.EBUG_SPEW,"Print Attributes Entity desc is " + streDesc);
    D.ebug(D.EBUG_SPEW,"Attribute count is" + entItem.getAttributeCount());
    Hashtable htValues = new Hashtable();
    Vector vctTmp = new Vector();
    for (int i = 0; i < entItem.getAttributeCount(); i++) {

      EANAttribute EANatt = entItem.getAttribute(i);
      D.ebug(D.EBUG_SPEW,"printAttributes " + EANatt.dump(false));
      D.ebug(D.EBUG_SPEW,"printAttributes " + EANatt.dump(true));

      String strValue = getAttributeValue(_strEntityType, _iEntityID, EANatt.getAttributeCode(), DEF_NOT_POPULATED_HTML);
      String strDesc1 = "";

      // Use short description or long description?
      if (_bShortDesc) {
        strDesc1 = getMetaDescription(_strEntityType, EANatt.getAttributeCode(), false);
      } else {
        strDesc1 = getAttributeDescription(_strEntityType, EANatt.getAttributeCode());
      }
      // Strip entity description from beginning of attribute description
      if (strDesc1.length() > streDesc.length() && strDesc1.substring(0, streDesc.length()).equalsIgnoreCase(streDesc)) {
        strDesc1 = strDesc1.substring(streDesc.length());
      }
      // Did we only want populated attributes?
      if (_bAll || strValue != null) {
        // associate truncated description with its value
        htValues.put(strDesc1, strValue);
        // keep track of attributes to display
        vctTmp.add(strDesc1);
      }
    }
    String[] astrCodeDesc = new String[entItem.getAttributeCount()];

    if (!_bAll) {
      astrCodeDesc = new String[vctTmp.size()];
      for (int i = 0; i < astrCodeDesc.length; i++) {
        astrCodeDesc[i] = (String) vctTmp.elementAt(i);
      }
    }

    // Sort on attribute code description
    SortUtil sort = new SortUtil();
    sort.sort(astrCodeDesc);

    // Output attributes in a two column tale
    println("<table width=\"100%\">");
    int nRows = astrCodeDesc.length - (astrCodeDesc.length / 2);
    for (int i = 0; i < nRows; i++) {
      println("<tr><td class=\"PsgLabel\" valign=\"top\">" +
          astrCodeDesc[i] +
          "</td><td class=\"PsgText\" valign=\"top\">" +
          htValues.get(astrCodeDesc[i]) +
          "</td>");
      int col2Index = nRows + i;
      if (col2Index < astrCodeDesc.length) {
        println(
            "<td class=\"PsgLabel\" valign=\"top\">" +
            astrCodeDesc[col2Index] +
            "</td><td class=\"PsgText\" valign=\"top\">" +
            htValues.get(astrCodeDesc[col2Index]) +
            "</td><tr>");
      } else {
        println(
            "<td class=\"PsgLabel\">" +
            "</td><td class=\"PsgText\">" +
            "</td><tr>");
      }
    }
    println("</table>\n<br />");
  }


  /**
   *  Insert the method's description here. Creation date: (8/14/2001 1:34:30
   *  PM)
   *
   *@param  _htDIV  Description of the Parameter
   *@param  _htBR   Description of the Parameter
   *@param  _htMBR  Description of the Parameter
   *@param  _htSG   Description of the Parameter
   *@return         The pathDIVtoSG value
   */
  public Set getPathDIVtoSG(Hashtable _htDIV, Hashtable _htBR, Hashtable _htMBR, Hashtable _htSG) {
    return getPathDIVtoSG(m_elist, _htDIV, _htBR, _htMBR, _htSG);
  }


  /**
   *  Gets the pathDIVtoSG attribute of the PokBaseABR object
   *
   *@param  _elist  Description of the Parameter
   *@param  htDIV   Description of the Parameter
   *@param  htBR    Description of the Parameter
   *@param  htMBR   Description of the Parameter
   *@param  htSG    Description of the Parameter
   *@return         The pathDIVtoSG value
   */
  public Set getPathDIVtoSG(EntityList _elist, Hashtable htDIV, Hashtable htBR, Hashtable htMBR, Hashtable htSG) {

    // DIV->BR->MBR->GR->SG
    Set pathSet = new HashSet();
    /*String[][] data = {
        {"PSGDIV", null, "PSGDIVBR", "NAME"},          // was PSGGROUPNAMECODE
    {"PSGDIV", "PSGBR", "PSGDIVBR", "NAME"},           // was SUBGROUPNAMECODE
    {"PSGBR", "PSGMBR", "PSGBRMBR", "NAME"},
        {"PSGMBR", "PSGGR", "PSGMBRGR", "NAME"},
        {"PSGGR", "PSGSG", "PSGGRSG", "PSGSUBGEONAME"}
        };*/

    Vector vctDIV = getEntityIds("PSGDIV", "PSGDIVBR");
    for (int iDIV = 0; iDIV < vctDIV.size(); iDIV++) {
      Integer pdhDIV = (Integer) vctDIV.elementAt(iDIV);
      String strDIV = getAttributeValue(_elist, "PSGDIV", pdhDIV.intValue(), "NAME", DEF_NOT_POPULATED_HTML);// was PSGGROUPNAMECODE
      htDIV.put(pdhDIV, strDIV);

      Vector vctBR = getChildrenEntityIds(_elist, "PSGDIV", pdhDIV.intValue(), "PSGBR", "PSGDIVBR");
      for (int iBR = 0; iBR < vctBR.size(); iBR++) {
        Integer pdhBR = (Integer) vctBR.elementAt(iBR);
        String strBR = getAttributeValue(_elist, "PSGBR", pdhBR.intValue(), "NAME", DEF_NOT_POPULATED_HTML);// was PSGSUBGROUPNAMECODE
        htBR.put(pdhBR, strBR);

        Vector vctMBR = getChildrenEntityIds(_elist, "PSGBR", pdhBR.intValue(), "PSGMBR", "PSGBRMBR");
        for (int iMBR = 0; iMBR < vctMBR.size(); iMBR++) {
          Integer pdhMBR = (Integer) vctMBR.elementAt(iMBR);
          String strMBR = getAttributeValue(_elist, "PSGMBR", pdhMBR.intValue(), "NAME", DEF_NOT_POPULATED_HTML);
          htMBR.put(pdhMBR, strMBR);

          Vector vctGR = getChildrenEntityIds(_elist, "PSGMBR", pdhMBR.intValue(), "PSGGR", "PSGMBRGR");
          for (int iGR = 0; iGR < vctGR.size(); iGR++) {
            Integer pdhGR = (Integer) vctGR.elementAt(iGR);
            String strGR = getAttributeValue(_elist, "PSGGR", pdhGR.intValue(), "NAME", DEF_NOT_POPULATED_HTML);

            Vector vctSG = getChildrenEntityIds("PSGGR", pdhGR.intValue(), "PSGSG", "PSGGRSG");
            for (int iSG = 0; iSG < vctSG.size(); iSG++) {
              Integer pdhSG = (Integer) vctSG.elementAt(iSG);
              String strSG = getAttributeValue(_elist, "PSGSG", pdhSG.intValue(), "NAME", DEF_NOT_POPULATED_HTML);// PSGSUBGEONAME
              htSG.put(pdhSG, strSG);
              pathSet.add(
                  "<td class=\"PsgText\"><!--MBR-->" +
                  strMBR +
                  "</td><td class=\"PsgText\"><!--GR-->" +
                  strGR +
                  "</td><td class=\"PsgText\"><!--SG-->" +
                  strSG +
                  "</td>");
            }
          }
        }
      }
    }

    return pathSet;
  }


  /**
   *  Insert the method's description here. Creation date: (8/14/2001 2:11:40
   *  PM)
   *
   *@param  _htDIV  Description of the Parameter
   *@param  _htMBR  Description of the Parameter
   *@param  _htSG   Description of the Parameter
   *@return         java.lang.String
   */
  public String concatDIV_MBR_SG(Hashtable _htDIV, Hashtable _htMBR, Hashtable _htSG) {
    String str1 = "";
    if (_htDIV.size() == 0) {
      str1 += "||Division=ORPHAN-DIV";
    }
    Enumeration e = _htDIV.keys();
    while (e.hasMoreElements()) {
      str1 += "||Division=" + _htDIV.get(e.nextElement());
    }
    e = _htMBR.keys();
    while (e.hasMoreElements()) {
      str1 += "||Category2=" + _htMBR.get(e.nextElement());
    }
    e = _htSG.keys();
    while (e.hasMoreElements()) {
      str1 += "||Category3=" + _htSG.get(e.nextElement());
    }
    return str1;
  }


  /**
   *  Insert the method's description here. Creation date: (8/14/2001 2:11:40
   *  PM)
   *
   *@param  _htDIV  Description of the Parameter
   *@param  _htBR   Description of the Parameter
   *@param  _htMBR  Description of the Parameter
   *@param  _htSG   Description of the Parameter
   *@return         java.lang.String
   */
  public String concatDIV_MBR_SG(Hashtable _htDIV, Hashtable _htBR, Hashtable _htMBR, Hashtable _htSG) {
    String str1 = "";
    if (_htDIV.size() == 0) {
      str1 += "||Division=ORPHAN-DIV";
    }
    Enumeration e = _htDIV.keys();
    while (e.hasMoreElements()) {
      str1 += "||Division=" + _htDIV.get(e.nextElement());
    }
    e = _htBR.keys();
    while (e.hasMoreElements()) {
      str1 += "||Category2=$BR$=" + _htBR.get(e.nextElement());
    }
    e = _htMBR.keys();
    while (e.hasMoreElements()) {
      str1 += "||Category2=$MBR$" + _htMBR.get(e.nextElement());
    }
    e = _htSG.keys();
    while (e.hasMoreElements()) {
      str1 += "||Category3=" + _htSG.get(e.nextElement());
    }
    return str1;
  }


  /**
   *  Insert the method's description here. Creation date: (8/16/2001 2:36:09
   *  PM)
   *
   *@param  _pathSet  Description of the Parameter
   */
  public void printPath(Set _pathSet) {
    SortUtil sort = new SortUtil();
    String[] astrPath = new String[_pathSet.size()];
    Iterator iterator = _pathSet.iterator();
    int i = 0;
    while (iterator.hasNext()) {
      astrPath[i++] = (String) iterator.next();
    }
    sort.sort(astrPath);
    for (i = 0; i < astrPath.length; i++) {
      println("<tr>" + astrPath[i] + "</tr>");
    }
  }


  /**
   *  Insert the method's description here. Creation date: (8/14/2001 1:34:30
   *  PM)
   *
   *@param  htDIV  java.util.Hashtable
   *@param  htMBR  java.util.Hashtable
   *@param  htSG   java.util.Hashtable
   *@param  htBR   Description of the Parameter
   *@return        The pathSOL value
   */
  public final Set getPathSOL(Hashtable htDIV, Hashtable htBR, Hashtable htMBR, Hashtable htSG) {
    Set pathSet = new HashSet();
    Vector vctCSOL = getParentEntityIds(getABREntityType(), getABREntityID(), "PSGCSOL", "PSGCSOLSOL");
    for (int iCSOL = 0; iCSOL < vctCSOL.size(); iCSOL++) {
      Integer pdhCSOL = (Integer) vctCSOL.elementAt(iCSOL);
      String strCSOL = getAttributeValue("PSGCSOL", pdhCSOL.intValue(), "NAME");
      try {
        ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, "EXTPSGVECSOL1");
        EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
        java.lang.reflect.Array.set(eiParm, 0, new EntityItem(null, m_prof, "PSGCSOL", pdhCSOL.intValue()));
        D.ebug(D.EBUG_SPEW,"Creating Entity List for EXTPSGVECSOL1");
        D.ebug(D.EBUG_SPEW,"Profile is " + m_prof);
        D.ebug(D.EBUG_SPEW,"Extractaction Item is" + eaItem);
        D.ebug(D.EBUG_SPEW,"Entity Item" + eiParm);
        EntityList eList = m_db.getEntityList(m_prof, eaItem, eiParm);
        appendPath(pathSet, getPathCSOL(eList, htDIV, htBR, htMBR, htSG), "<!--CSOL-->" + strCSOL);
      } catch (Exception ex) {
    	  D.ebug(D.EBUG_ERR,"Badness #3" + ex.getMessage());
      }
    }
    Vector vctROF = getParentEntityIds(getABREntityType(), getABREntityID(), "PSGROF", "PSGROFSOL");
    for (int iROF = 0; iROF < vctROF.size(); iROF++) {
      Integer pdhROF = (Integer) vctROF.elementAt(iROF);
      String strROF = getAttributeValue("PSGROF", pdhROF.intValue(), "NAME");
      try {
        ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, "EXTPSGVEROF1");
        EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
        java.lang.reflect.Array.set(eiParm, 0, new EntityItem(null, m_prof, "PSGROF", pdhROF.intValue()));
        D.ebug(D.EBUG_SPEW,"Creating Entity List for EXTPSGVEROF1");
        D.ebug(D.EBUG_SPEW,"Profile is " + m_prof);
        D.ebug(D.EBUG_SPEW,"Extractaction Item is" + eaItem);
        D.ebug(D.EBUG_SPEW,"Entity Item" + eiParm);
        EntityList eList = m_db.getEntityList(m_prof, eaItem, eiParm);

        appendPath(pathSet, getPathROF(eList, htDIV, htBR, htMBR, htSG), "<!--ROF-->" + strROF);
      } catch (Exception ex) {
    	  D.ebug(D.EBUG_ERR,"Badness #4" + ex.getMessage());
      }
    }
    Vector vctSGR = getParentEntityIds(getABREntityType(), getABREntityID(), "PSGSGR", "PSGSGRSOL");
    for (int iSGR = 0; iSGR < vctSGR.size(); iSGR++) {
      Integer pdhSGR = (Integer) vctSGR.elementAt(iSGR);
      String strSGR = getAttributeValue("PSGSGR", pdhSGR.intValue(), "NAME");
      try {

        ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, "EXTPSGVESGR1");
        EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
        java.lang.reflect.Array.set(eiParm, 0, new EntityItem(null, m_prof, "PSGSGR", pdhSGR.intValue()));
        D.ebug(D.EBUG_SPEW,"Creating Entity List for EXTPSGVESGR1");
        D.ebug(D.EBUG_SPEW,"Profile is " + m_prof);
        D.ebug(D.EBUG_SPEW,"Extractaction Item is" + eaItem);
        D.ebug(D.EBUG_SPEW,"Entity Item" + eiParm);
        EntityList eList = m_db.getEntityList(m_prof, eaItem, eiParm);

        appendPath(pathSet, getPathDIVtoSG(eList, htDIV, htBR, htMBR, htSG), "<!--SGR-->" + strSGR);
      } catch (Exception ex) {
    	  D.ebug(D.EBUG_ERR,"Badness #5" + ex.getMessage());
      }
    }
    Vector vctCATM = getParentEntityIds(getABREntityType(), getABREntityID(), "PSGCATM", "PSGCATMSOL");
    for (int iCATM = 0; iCATM < vctCATM.size(); iCATM++) {
      Integer pdhCATM = (Integer) vctCATM.elementAt(iCATM);
      String strCATM = getAttributeValue("PSGCATM", pdhCATM.intValue(), "NAME");
      try {
        ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, "EXTPSGVECATM1");
        EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
        java.lang.reflect.Array.set(eiParm, 0, new EntityItem(null, m_prof, "PSGCATM", pdhCATM.intValue()));
        D.ebug(D.EBUG_SPEW,"Creating Entity List for EXTPSGVECATM1");
        D.ebug(D.EBUG_SPEW,"Profile is " + m_prof);
        D.ebug(D.EBUG_SPEW,"Extractaction Item is" + eaItem);
        D.ebug(D.EBUG_SPEW,"Entity Item" + eiParm);
        EntityList eList = m_db.getEntityList(m_prof, eaItem, eiParm);

        appendPath(pathSet, getPathCATM(eList, htDIV, htBR, htMBR, htSG), "<!--CATM-->" + strCATM);
      } catch (Exception ex) {
    	  D.ebug(D.EBUG_ERR,"Badness #6" + ex.getMessage());
      }

    }
    return pathSet;
  }


  /**
   *  Insert the method's description here. Creation date: (8/14/2001 1:34:30
   *  PM)
   *
   *@param  _htDIV  Description of the Parameter
   *@param  _htBR   Description of the Parameter
   *@param  _htMBR  Description of the Parameter
   *@param  _htSG   Description of the Parameter
   *@return         The pathCSOL value
   */
  public final Set getPathCSOL(Hashtable _htDIV, Hashtable _htBR, Hashtable _htMBR, Hashtable _htSG) {
    return getPathCSOL(m_elist, _htDIV, _htBR, _htMBR, _htSG);
  }


  /**
   *  Gets the pathCSOL attribute of the PokBaseABR object
   *
   *@param  _elist  Description of the Parameter
   *@param  htDIV   Description of the Parameter
   *@param  htBR    Description of the Parameter
   *@param  htMBR   Description of the Parameter
   *@param  htSG    Description of the Parameter
   *@return         The pathCSOL value
   */
  public final Set getPathCSOL(EntityList _elist, Hashtable htDIV, Hashtable htBR, Hashtable htMBR, Hashtable htSG) {
    Set pathSet = new HashSet();
    Vector vctCSGR = getEntityIds(_elist, "PSGCSGR", "PSGCSGRCSOL");
    for (int iCSGR = 0; iCSGR < vctCSGR.size(); iCSGR++) {
      Integer pdhCSGR = (Integer) vctCSGR.elementAt(iCSGR);
      try {
        ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, "EXTPSGVECSGR1");
        EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
        java.lang.reflect.Array.set(eiParm, 0, new EntityItem(null, m_prof, "PSGCSGR", pdhCSGR.intValue()));
        D.ebug(D.EBUG_SPEW,"Creating Entity List for EXTPSGVECSGR1");
        D.ebug(D.EBUG_SPEW,"Profile is " + m_prof);
        D.ebug(D.EBUG_SPEW,"Extractaction Item is" + eaItem);
        D.ebug(D.EBUG_SPEW,"Entity Item" + eiParm);
        EntityList eList = m_db.getEntityList(m_prof, eaItem, eiParm);
        pathSet.addAll(getPathDIVtoCSGR(eList, htDIV, htBR, htMBR, htSG));
      } catch (Exception ex) {
    	  D.ebug(D.EBUG_ERR,"Badness:" + ex.getMessage());
      }
    }
    return pathSet;
  }


  /**
   *  Insert the method's description here. Creation date: (8/14/2001 1:34:30
   *  PM)
   *
   *@param  _htDIV  Description of the Parameter
   *@param  _htBR   Description of the Parameter
   *@param  _htMBR  Description of the Parameter
   *@param  _htSG   Description of the Parameter
   *@return         The pathDIVtoCSGR value
   */
  public final Set getPathDIVtoCSGR(Hashtable _htDIV, Hashtable _htBR, Hashtable _htMBR, Hashtable _htSG) {
    return getPathDIVtoCSGR(m_elist, _htDIV, _htBR, _htMBR, _htSG);
  }


  /**
   *  Gets the pathDIVtoCSGR attribute of the PokBaseABR object
   *
   *@param  _elist  Description of the Parameter
   *@param  htDIV   Description of the Parameter
   *@param  htBR    Description of the Parameter
   *@param  htMBR   Description of the Parameter
   *@param  htSG    Description of the Parameter
   *@return         The pathDIVtoCSGR value
   */
  public final Set getPathDIVtoCSGR(EntityList _elist, Hashtable htDIV, Hashtable htBR, Hashtable htMBR, Hashtable htSG) {
    // DIV->BR->MBR->GR->SG->CT->CSGR
    Set pathSet = new HashSet();
    /*String[][] data = {
        {"PSGDIV", null, "PSGDIVBR", "NAME"},          // was PSGGROUPNAMECODE
    {"PSGDIV", "PSGBR", "PSGDIVBR", "NAME"},           // was SUBGROUPNAMECODE
    {"PSGBR", "PSGMBR", "PSGBRMBR", "NAME"},
        {"PSGMBR", "PSGGR", "PSGMBRGR", "NAME"},
        {"PSGGR", "PSGSG", "PSGGRSG", "PSGSUBGEONAME"}
        };*/

    Vector vctDIV = getEntityIds(_elist, "PSGDIV", "PSGDIVBR");
    for (int iDIV = 0; iDIV < vctDIV.size(); iDIV++) {
      Integer pdhDIV = (Integer) vctDIV.elementAt(iDIV);
      String strDIV = getAttributeValue(_elist, "PSGDIV", pdhDIV.intValue(), "NAME");// was PSGGROUPNAMECODE
      htDIV.put(pdhDIV, strDIV);

      Vector vctBR = getChildrenEntityIds(_elist, "PSGDIV", pdhDIV.intValue(), "PSGBR", "PSGDIVBR");
      for (int iBR = 0; iBR < vctBR.size(); iBR++) {
        Integer intPdhBR = (Integer) vctBR.elementAt(iBR);
        String strBR = getAttributeValue(_elist, "PSGBR", intPdhBR.intValue(), "NAME");// was PSGSUBGROUPNAMECODE
        htBR.put(intPdhBR, strBR);

        Vector vctMBR = getChildrenEntityIds(_elist, "PSGBR", intPdhBR.intValue(), "PSGMBR", "PSGBRMBR");
        for (int iMBR = 0; iMBR < vctMBR.size(); iMBR++) {
          Integer intPdhMBR = (Integer) vctMBR.elementAt(iMBR);
          String strMBR = getAttributeValue(_elist, "PSGMBR", intPdhMBR.intValue(), "NAME");
          htMBR.put(intPdhMBR, strMBR);

          Vector vctGR = getChildrenEntityIds(_elist, "PSGMBR", intPdhMBR.intValue(), "PSGGR", "PSGMBRGR");
          for (int iGR = 0; iGR < vctGR.size(); iGR++) {
            Integer intPdhGR = (Integer) vctGR.elementAt(iGR);
            String strGR = getAttributeValue(_elist, "PSGGR", intPdhGR.intValue(), "NAME");

            Vector vctSG = getChildrenEntityIds(_elist, "PSGGR", intPdhGR.intValue(), "PSGSG", "PSGGRSG");
            for (int iSG = 0; iSG < vctSG.size(); iSG++) {
              Integer intPdhSG = (Integer) vctSG.elementAt(iSG);
              String strSG = getAttributeValue(_elist, "PSGSG", intPdhSG.intValue(), "NAME");// PSGSUBGEONAME
              htSG.put(intPdhSG, strSG);
              Vector vctCT = getChildrenEntityIds(_elist, "PSGSG", intPdhSG.intValue(), "PSGCT", "PSGSGCT");
              for (int iCT = 0; iCT < vctCT.size(); iCT++) {
                Integer intPdhCT = (Integer) vctCT.elementAt(iCT);
                String strCT = getAttributeValue(_elist, "PSGCT", intPdhCT.intValue(), "NAME");
                Vector vctCSGR = getChildrenEntityIds(_elist, "PSGCT", intPdhCT.intValue(), "PSGCSGR", "PSGCTCSGR");
                for (int iCSGR = 0; iCSGR < vctCSGR.size(); iCSGR++) {
                  Integer pdhCSGR = (Integer) vctCSGR.elementAt(iCSGR);
                  String strCSGR = getAttributeValue(_elist, "PSGCSGR", pdhCSGR.intValue(), "NAME");
                  pathSet.add(
                      "<td class=\"PsgText\"><!--MBR-->" +
                      strMBR +
                      "</td><td class=\"PsgText\"><!--GR-->" +
                      strGR +
                      "</td><td class=\"PsgText\"><!--SG-->" +
                      strSG +
                      "</td><td class=\"PsgText\"><!--CT-->" +
                      strCT +
                      "</td><td class=\"PsgText\"><!--CSGR-->" +
                      strCSGR +
                      "</td>");
                }
              }
            }
          }
        }
      }
    }

    return pathSet;
  }


  /**
   *  Insert the method's description here. Creation date: (8/14/2001 1:34:30
   *  PM)
   *
   *@param  htDIV  java.util.Hashtable
   *@param  htMBR  java.util.Hashtable
   *@param  htSG   java.util.Hashtable
   *@param  htBR   Description of the Parameter
   *@return        The pathROF value
   */
  public final Set getPathROF(Hashtable htDIV, Hashtable htBR, Hashtable htMBR, Hashtable htSG) {
    return getPathROF(m_elist, htDIV, htBR, htMBR, htSG);
  }


  /**
   *  Gets the pathROF attribute of the PokBaseABR object
   *
   *@param  _elist  Description of the Parameter
   *@param  htDIV   Description of the Parameter
   *@param  htBR    Description of the Parameter
   *@param  htMBR   Description of the Parameter
   *@param  htSG    Description of the Parameter
   *@return         The pathROF value
   */
  public final Set getPathROF(EntityList _elist, Hashtable htDIV, Hashtable htBR, Hashtable htMBR, Hashtable htSG) {
    Set pathSet = new HashSet();
    Set pathSubSet = getPathDIVtoSG(_elist, htDIV, htBR, htMBR, htSG);
    Enumeration sgIds = htSG.keys();
    while (sgIds.hasMoreElements()) {
      Integer pdhSG = (Integer) sgIds.nextElement();
      String strSG = (String) htSG.get(pdhSG);
      Vector vctSGR = getChildrenEntityIds(_elist, "PSGSG", pdhSG.intValue(), "PSGSGR", "PSGSGSGR");
      for (int iSGR = 0; iSGR < vctSGR.size(); iSGR++) {
        Integer pdhSGR = (Integer) vctSGR.elementAt(iSGR);
        String strSGR = getAttributeValue(_elist, "PSGSGR", pdhSGR.intValue(), "NAME");
        Iterator iterator = pathSubSet.iterator();
        while (iterator.hasNext()) {
          String path = (String) iterator.next();
          if (path.endsWith(strSG + "</td>")) {
            pathSet.add(
                path +
                "<td class=\"PsgText\"><!--SGR-->" +
                strSGR +
                "</td>");
          }
        }
      }
    }
    return pathSet;
  }


  /**
   *  Insert the method's description here. Creation date: (8/21/2001 9:46:51
   *  AM)
   *
   *@param  _pathSet         Description of the Parameter
   *@param  _elist           Description of the Parameter
   *@param  _htDIV           Description of the Parameter
   *@param  _htBR            Description of the Parameter
   *@param  _htMBR           Description of the Parameter
   *@param  _htSG            Description of the Parameter
   *@param  _strEntityType   Description of the Parameter
   *@param  _strRelatorType  Description of the Parameter
   *@param  _strAttCode      Description of the Parameter
   *@param  _strVEName       Description of the Parameter
   *@param  _method          Description of the Parameter
   *@return                  java.util.Set
   */
  public final Set appendPath(Set _pathSet, EntityList _elist, Hashtable _htDIV, Hashtable _htBR, Hashtable _htMBR, Hashtable _htSG, String _strEntityType, String _strRelatorType, String _strAttCode, String _strVEName, java.lang.reflect.Method _method) {

    Vector vct = getParentEntityIds(_elist, getRootEntityType(_elist), getRootEntityID(_elist), _strEntityType, _strRelatorType);
    for (int i = 0; i < vct.size(); i++) {
      int iEID = ((Integer) vct.elementAt(i)).intValue();
      String str = getAttributeValue(_elist, _strEntityType, iEID, _strAttCode);
      try {
        ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, _strVEName);
        EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
        java.lang.reflect.Array.set(eiParm, 0, new EntityItem(null, m_prof, _strEntityType, iEID));
        D.ebug(D.EBUG_SPEW,"Creating Entity List for " + _strVEName);
        D.ebug(D.EBUG_SPEW,"Profile is " + m_prof);
        D.ebug(D.EBUG_SPEW,"Extractaction Item is" + eaItem);
        D.ebug(D.EBUG_SPEW,"Entity Item" + eiParm);
        EntityList eList = m_db.getEntityList(m_prof, eaItem, eiParm);

        appendPath(_pathSet, (Set) _method.invoke(_method.getClass(), new Object[]{eList, _htDIV, _htBR, _htMBR, _htSG}), str);
      } catch (Exception x) {
    	  D.ebug(D.EBUG_ERR,"appendPath:" + x.getMessage());
      }
    }
    return _pathSet;
  }


  /**
   *  Insert the method's description here. Creation date: (8/16/2001 2:52:31
   *  PM)
   *
   *@param  _pathSet     Description of the Parameter
   *@param  _pathSubSet  Description of the Parameter
   *@param  _strStep     Description of the Parameter
   */
  public final void appendPath(Set _pathSet, Set _pathSubSet, String _strStep) {
    Iterator i = _pathSubSet.iterator();
    while (i.hasNext()) {
      _pathSet.add(i.next() + "<td class=\"PsgText\">" + _strStep + "</td>");
    }
  }


  /**
   *  Gets the rootEntityType attribute of the PokBaseABR object
   *
   *@return    The rootEntityType value
   */
  public String getRootEntityType() {
    return getRootEntityType(m_elist);
  }


  /**
   *  Gets the rootEntityType attribute of the PokBaseABR object
   *
   *@param  _elist  Description of the Parameter
   *@return         The rootEntityType value
   */
  public String getRootEntityType(EntityList _elist) {
    EntityGroup egParent = _elist.getParentEntityGroup();
    return (egParent == null ? null : egParent.getEntityType());
  }


  /**
   *  Gets the rootEntityID attribute of the PokBaseABR object
   *
   *@return    The rootEntityID value
   */
  public int getRootEntityID() {
    return getRootEntityID(m_elist);
  }


  /**
   *  Gets the rootEntityID attribute of the PokBaseABR object
   *
   *@param  _elist  Description of the Parameter
   *@return         The rootEntityID value
   */
  public int getRootEntityID(EntityList _elist) {
    int iEID = 0;
    EntityGroup egParent = _elist.getParentEntityGroup();
    for (int ij = 0;ij < egParent.getEntityItemCount();ij++) {
      EntityItem ei = egParent.getEntityItem(0);
      iEID = ei.getEntityID();
    }
    return iEID;
  }


  /**
   *  Insert the method's description here. Creation date: (8/14/2001 1:34:30
   *  PM)
   *
   *@param  _htDIV  Description of the Parameter
   *@param  _htBR   Description of the Parameter
   *@param  _htMBR  Description of the Parameter
   *@param  _htSG   Description of the Parameter
   *@return         The pathCATM value
   */
  public final Set getPathCATM(Hashtable _htDIV, Hashtable _htBR, Hashtable _htMBR, Hashtable _htSG) {
    return getPathCATM(m_elist, _htDIV, _htBR, _htMBR, _htSG);
  }


  /**
   *  Gets the pathCATM attribute of the PokBaseABR object
   *
   *@param  _elistCATM  Description of the Parameter
   *@param  _htDIV      Description of the Parameter
   *@param  _htBR       Description of the Parameter
   *@param  _htMBR      Description of the Parameter
   *@param  _htSG       Description of the Parameter
   *@return             The pathCATM value
   */
  public final Set getPathCATM(EntityList _elistCATM, Hashtable _htDIV, Hashtable _htBR, Hashtable _htMBR, Hashtable _htSG) {
    Set pathSet = new HashSet();
    Set pathSubSet = getPathDIVtoSG(_elistCATM, _htDIV, _htBR, _htMBR, _htSG);
    Enumeration sgIds = _htSG.keys();
    while (sgIds.hasMoreElements()) {
      Integer intSGID = (Integer) sgIds.nextElement();
      String strSG = (String) _htSG.get(intSGID);
      Vector vctCT = getChildrenEntityIds(_elistCATM, "PSGSG", intSGID.intValue(), "PSGCT", "PSGSGCT");
      for (int iCT = 0; iCT < vctCT.size(); iCT++) {
        int iCTID = ((Integer) vctCT.elementAt(iCT)).intValue();
        String strCT = getAttributeValue(_elistCATM, "PSGCT", iCTID, "NAME");
        Iterator iterator = pathSubSet.iterator();
        while (iterator.hasNext()) {
          String path = (String) iterator.next();
          if (path.endsWith(strSG + "</td>")) {
            pathSet.add(
                path +
                "<td class=\"PsgText\"><!--CT-->" +
                strCT +
                "</td>");
          }
        }
      }
    }
    return pathSet;
  }


  /**
   *  Get the children of childType as EntityItems through the relator of
   *  relatorType
   *
   *@param  _strChildType    Description of the Parameter
   *@param  _strRelatorType  Description of the Parameter
   *@return                  The children value
   */
  public Vector getChildren(String _strChildType, String _strRelatorType) {
    return getChildren(m_elist, _strChildType, _strRelatorType);
  }


  /**
   *  Gets the children attribute of the PokBaseABR object
   *
   *@param  _elist       Description of the Parameter
   *@param  childType    Description of the Parameter
   *@param  relatorType  Description of the Parameter
   *@return              The children value
   */
  public Vector getChildren(EntityList _elist, String childType, String relatorType) {
    return getAllEntities(_elist, childType, 0, false, true);
  }


  /**
   *  Insert the method's description here. Creation date: (8/16/2001 9:19:00
   *  AM)
   *
   *@param  _vct         Description of the Parameter
   *@param  _strDesc     Description of the Parameter
   *@param  _strAttCode  Description of the Parameter
   *@return              Description of the Return Value
   */
  public final int printStatus(Vector _vct, String _strDesc, String _strAttCode) {

    int iReturnCode = PokBaseABR.PASS;
    println("<hr /><table width=\"50%\"><tr><td class=\"PsgLabel\">" + _strDesc + "</td><td class=\"PsgLabel\">Status</td></tr>");
    if (_vct.size() == 0) {
      iReturnCode = PokBaseABR.FAIL;
      println("<tr><td class=\"PsgText\">None Found</td><td class=\"PsgText\"></td></tr>");
    }
    for (int i = 0; i < _vct.size(); i++) {
      EntityItem e = (EntityItem) _vct.elementAt(i);
      EANAttribute att = e.getAttribute(_strAttCode);
      EANFlagAttribute efa = (EANFlagAttribute) att;

      String strStatus = att.toString();
      if (!"0020".equals(strStatus)) {
        iReturnCode = PokBaseABR.FAIL;
      }

      att = e.getAttribute("NAME");
      String strName = att.toString();

      String strDesc = efa.getFlagLongDescription(strStatus);

      println("<tr><td class=\"PsgText\">" + strName + "</td><td class=\"PsgText\">" + strDesc + "</td></tr>");
    }
    println("</table>\n<br />");
    //println(((iReturnCode == PokBaseABR.PASS) ? Constants.IAB0014I : Constants.IAB0015E));
    return iReturnCode;
  }


  /**
   *  Insert the method's description here. Creation date: (8/14/2001 1:34:30
   *  PM)
   *
   *@param  _htDIV  Description of the Parameter
   *@param  _htBR   Description of the Parameter
   *@param  _htMBR  Description of the Parameter
   *@param  _htSG   Description of the Parameter
   *@return         The pathCPSL value
   */
  public final Set getPathCPSL(Hashtable _htDIV, Hashtable _htBR, Hashtable _htMBR, Hashtable _htSG) {
    return getPathCPSL(m_elist, _htDIV, _htBR, _htMBR, _htSG);
  }


  /**
   *  Gets the pathCPSL attribute of the PokBaseABR object
   *
   *@param  _elist  Description of the Parameter
   *@param  htDIV   Description of the Parameter
   *@param  htBR    Description of the Parameter
   *@param  htMBR   Description of the Parameter
   *@param  htSG    Description of the Parameter
   *@return         The pathCPSL value
   */
  public final Set getPathCPSL(EntityList _elist, Hashtable htDIV, Hashtable htBR, Hashtable htMBR, Hashtable htSG) {

    Set pathSet = new HashSet();
    Vector vctCSGR = getEntityIds(_elist, "PSGCSGR", "PSGCSGRCPSL");
    for (int iCSGR = 0; iCSGR < vctCSGR.size(); iCSGR++) {
      Integer intCSGR = (Integer) vctCSGR.elementAt(iCSGR);
      try {
        ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, "EXTPSGVECSGR1");
        EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
        java.lang.reflect.Array.set(eiParm, 0, new EntityItem(null, m_prof, "PSGCSGR", intCSGR.intValue()));
        D.ebug(D.EBUG_SPEW,"Creating Entity List for EXTPSGVECSGR1");
        D.ebug(D.EBUG_SPEW,"Profile is " + m_prof);
        D.ebug(D.EBUG_SPEW,"Extractaction Item is" + eaItem);
        D.ebug(D.EBUG_SPEW,"Entity Item" + eiParm);
        EntityList elCSGR = m_db.getEntityList(m_prof, eaItem, eiParm);

        //PokPDHVEntity veCSGR = new PokPDHVEntity(_elist.getProfile(), _elist.getDatabase(), "PSGCSGR", intCSGR.intValue(), "PSGVECSGR1");
        pathSet.addAll(getPathDIVtoCSGR(elCSGR, htDIV, htBR, htMBR, htSG));
      } catch (Exception x) {
    	  D.ebug(D.EBUG_ERR,"Badness in getPathCPSL "+x.getMessage());
        return null;
      }
    }

    vctCSGR = getEntityIds(_elist, "PSGCSGR", "PSGCSGRCB");
    for (int iCSGR = 0; iCSGR < vctCSGR.size(); iCSGR++) {
      Integer intCSGR = (Integer) vctCSGR.elementAt(iCSGR);

      EntityList elCSGR = null;
      try {
        ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, "EXTPSGELCSGR1");
        EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
        java.lang.reflect.Array.set(eiParm, 0, new EntityItem(null, m_prof, "PSGCSGR", intCSGR.intValue()));
        D.ebug(D.EBUG_SPEW,"Creating Entity List for EXTPSGELCSGR1");
        D.ebug(D.EBUG_SPEW,"Profile is " + m_prof);
        D.ebug(D.EBUG_SPEW,"Extractaction Item is" + eaItem);
        D.ebug(D.EBUG_SPEW,"Entity Item" + eiParm);
        elCSGR = m_db.getEntityList(m_prof, eaItem, eiParm);

        //elCSGR = new PokPDHVEntity(_elist.getProfile(), _elist.getDatabase(), "PSGCSGR", intCSGR.intValue(), "PSGelCSGR1");
      } catch (Exception x) {
    	  D.ebug(D.EBUG_ERR,"Badness in getPathCPSL");
        return null;
      }
      Vector vctCB = getChildrenEntityIds(_elist, "PSGCSGR", intCSGR.intValue(), "PSGCB", "PSGCSGRCB");
      for (int iCB = 0; iCB < vctCB.size(); iCB++) {
        Integer intCB = (Integer) vctCB.elementAt(iCB);
        String strCB = getAttributeValue("PSGCB", intCB.intValue(), "NAME");
        appendPath(pathSet, getPathDIVtoCSGR(elCSGR, htDIV, htBR, htMBR, htSG), "<!--CB-->" + strCB);
      }
    }

    return pathSet;
  }


  /**
   *  Insert the method's description here. Creation date: (9/19/2001 9:44:41
   *  AM)
   *
   *@return                                            boolean
   *@exception  MiddlewareException                    Description of the
   *      Exception
   *@exception  java.rmi.RemoteException               Description of the
   *      Exception
   *@exception  MiddlewareShutdownInProgressException  Description of the
   *      Exception
   */
  public final int kbCheck() throws MiddlewareException, java.rmi.RemoteException, MiddlewareShutdownInProgressException {
    return kbCheck(m_elist);
  }


  /**
   *  Description of the Method
   *
   *@param  _elist                                     Description of the
   *      Parameter
   *@return                                            Description of the Return
   *      Value
   *@exception  MiddlewareException                    Description of the
   *      Exception
   *@exception  java.rmi.RemoteException               Description of the
   *      Exception
   *@exception  MiddlewareShutdownInProgressException  Description of the
   *      Exception
   */
  public final int kbCheck(EntityList _elist) throws MiddlewareException, java.rmi.RemoteException, MiddlewareShutdownInProgressException {
    // Three possible conditions
    // 0: PASS - Root entity doesn't have a KB attached
    // 1: FAIL - Root has a KB and OF doesn't have an attached KB
    // 2: PASS - Root has a KB and OF has an attached KB
    int retCode = 0;                                   // Assume root doesn't have KB children
    Vector children = getAllLinkedChildren(_elist, getRootEntityType(_elist), getRootEntityID(_elist));
    // Fail if there are KB children
    for (int i = 0; i < children.size(); i++) {
      EntityItem child = (EntityItem) children.elementAt(i);
      if (child.getEntityType().equals("PSGKB")) {
        retCode = 1;
      }
    }

    if (retCode > 0) {
      // Root has KB children. Check the OF.
      Vector vctOF = getEntityIds(_elist, "PSGOF");
      for (int iOF = 0; iOF < vctOF.size(); iOF++) {
        Integer pdhOF = (Integer) vctOF.elementAt(iOF);
        children = getChildrenEntityIds(_elist, "PSGOF", pdhOF.intValue(), "PSGKB", "PSGOFKB");
        if (children.size() > 0) {
          retCode = 2;
        }
      }
    }
    return retCode;
  }


  /**
   *  Insert the method's description here. Creation date: (8/14/2001 1:34:30
   *  PM)
   *
   *@param  htDIV  java.util.Hashtable
   *@param  htMBR  java.util.Hashtable
   *@param  htSG   java.util.Hashtable
   *@param  htBR   Description of the Parameter
   *@return        The pathCVOF value
   */
  public final Set getPathCVOF(Hashtable htDIV, Hashtable htBR, Hashtable htMBR, Hashtable htSG) {
    return getPathCVOF(m_elist, htDIV, htBR, htMBR, htSG);
  }


  /**
   *  Gets the pathCVOF attribute of the PokBaseABR object
   *
   *@param  _elist  Description of the Parameter
   *@param  htDIV   Description of the Parameter
   *@param  htBR    Description of the Parameter
   *@param  htMBR   Description of the Parameter
   *@param  htSG    Description of the Parameter
   *@return         The pathCVOF value
   */
  public final Set getPathCVOF(EntityList _elist, Hashtable htDIV, Hashtable htBR, Hashtable htMBR, Hashtable htSG) {
    Set pathSet = new HashSet();

    try {
      //PokPDHVEntity vePR = new PokPDHVEntity(_elist.getProfile(),_elist.getDatabase(), _elist.getRootEntityType(), _elist.getRootEntityId(), "PSGVECVOF2");
      ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, "EXTPSGVECVOF2");
      EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
      java.lang.reflect.Array.set(eiParm, 0, new EntityItem(null, m_prof, getRootEntityType(_elist), getRootEntityID(_elist)));
      D.ebug(D.EBUG_SPEW,"Creating Entity List for EXTPSGVECVOF2");
      D.ebug(D.EBUG_SPEW,"Profile is " + m_prof);
      D.ebug(D.EBUG_SPEW,"Extractaction Item is" + eaItem);
      D.ebug(D.EBUG_SPEW,"Entity Item" + eiParm);
      EntityList elPR = m_db.getEntityList(m_prof, eaItem, eiParm);

      pathSet.addAll(getPathDIVtoPR(elPR, htDIV, htBR));
    } catch (Exception ex) {
    	D.ebug(D.EBUG_ERR,"Badness in getPathCVOF:vePR" + ex.getMessage());
    }
    Vector vctCVSL = getEntityIds(_elist, "PSGCVSL", "PSGCVSLCVOF");
    for (int iCVSL = 0; iCVSL < vctCVSL.size(); iCVSL++) {
      Integer pdhCVSL = (Integer) vctCVSL.elementAt(iCVSL);
      String strCVSL = getAttributeValue(_elist, "PSGCVSL", pdhCVSL.intValue(), "NAME");
      try {
        //PokPDHVEntity veCVSL = new PokPDHVEntity(_elist.getProfile(),_elist.getDatabase(),"PSGCVSL", pdhCVSL.intValue(), "PSGVECVSL1");
        ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, "EXTPSGVECVSL1");
        EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
        java.lang.reflect.Array.set(eiParm, 0, new EntityItem(null, m_prof, getRootEntityType(_elist), pdhCVSL.intValue()));
        D.ebug(D.EBUG_SPEW,"Creating Entity List for EXTPSGVECVSL1");
        D.ebug(D.EBUG_SPEW,"Profile is " + m_prof);
        D.ebug(D.EBUG_SPEW,"Extractaction Item is" + eaItem);
        D.ebug(D.EBUG_SPEW,"Entity Item" + eiParm);
        EntityList elCVSL = m_db.getEntityList(m_prof, eaItem, eiParm);
        appendPath(pathSet, getPathCVSL(elCVSL, htDIV, htBR, htMBR, htSG), "<!--CVSL-->" + strCVSL);
      } catch (Exception ex) {
    	  D.ebug(D.EBUG_ERR,"Badness in getPathCVOF:CVSL" + ex.getMessage());
      }
    }
    // To do: PSGATM
    return pathSet;
  }


  /**
   *  Insert the method's description here. Creation date: (8/14/2001 1:34:30
   *  PM)
   *
   *@param  htDIV  java.util.Hashtable
   *@param  htBR   Description of the Parameter
   *@return        The pathDIVtoPR value
   */
  public final Set getPathDIVtoPR(Hashtable htDIV, Hashtable htBR) {
    return getPathDIVtoPR(m_elist, htDIV, htBR);
  }


  /**
   *  Gets the pathDIVtoPR attribute of the PokBaseABR object
   *
   *@param  _elist  Description of the Parameter
   *@param  htDIV   Description of the Parameter
   *@param  htBR    Description of the Parameter
   *@return         The pathDIVtoPR value
   */
  public final Set getPathDIVtoPR(EntityList _elist, Hashtable htDIV, Hashtable htBR) {
    // DIV->BR->FAM->SE->PR
    Set pathSet = new HashSet();
    Vector vctDIV = getEntityIds(_elist, "PSGDIV", "PSGDIVBR");
    for (int iDIV = 0; iDIV < vctDIV.size(); iDIV++) {
      Integer pdhDIV = (Integer) vctDIV.elementAt(iDIV);
      String strDIV = getAttributeValue(_elist, "PSGDIV", pdhDIV.intValue(), "NAME");// was PSGGROUPNAMECODE
      htDIV.put(pdhDIV, strDIV);

      Vector vctBR = getChildrenEntityIds(_elist, "PSGDIV", pdhDIV.intValue(), "PSGBR", "PSGDIVBR");
      for (int iBR = 0; iBR < vctBR.size(); iBR++) {
        Integer pdhBR = (Integer) vctBR.elementAt(iBR);
        String strBR = getAttributeValue(_elist, "PSGBR", pdhBR.intValue(), "NAME");// was PSGSUBGROUPNAMECODE
        htBR.put(pdhBR, strBR);

        Vector vctFAM = getChildrenEntityIds(_elist, "PSGBR", pdhBR.intValue(), "PSGFAM", "PSGBRFAM");
        for (int iFAM = 0; iFAM < vctFAM.size(); iFAM++) {
          Integer pdhFAM = (Integer) vctFAM.elementAt(iFAM);
          String strFAM = getAttributeValue(_elist, "PSGFAM", pdhFAM.intValue(), "NAME");

          Vector vctSE = getChildrenEntityIds(_elist, "PSGFAM", pdhFAM.intValue(), "PSGSE", "PSGFAMSE");
          for (int iSE = 0; iSE < vctSE.size(); iSE++) {
            Integer pdhSE = (Integer) vctSE.elementAt(iSE);
            String strSE = getAttributeValue(_elist, "PSGSE", pdhSE.intValue(), "NAME");

            Vector vctPR = getChildrenEntityIds(_elist, "PSGSE", pdhSE.intValue(), "PSGPR", "PSGSEPR");
            for (int iPR = 0; iPR < vctPR.size(); iPR++) {
              Integer pdhPR = (Integer) vctPR.elementAt(iPR);
              String strPR = getAttributeValue(_elist, "PSGPR", pdhPR.intValue(), "NAME");// PSGSUBGEONAME
              pathSet.add(
                  "<td class=\"PsgText\"><!--BR-->" +
                  strBR +
                  "<td class=\"PsgText\"><!--FAM-->" +
                  strFAM +
                  "</td><td class=\"PsgText\"><!--SE-->" +
                  strSE +
                  "</td><td class=\"PsgText\"><!--PR-->" +
                  strPR +
                  "</td>");
            }
          }
        }
      }
    }

    return pathSet;
  }


  /**
   *  Insert the method's description here. Creation date: (8/14/2001 1:34:30
   *  PM)
   *
   *@param  htDIV  java.util.Hashtable
   *@param  htMBR  java.util.Hashtable
   *@param  htSG   java.util.Hashtable
   *@param  htBR   Description of the Parameter
   *@return        The pathCVSL value
   */
  public final Set getPathCVSL(Hashtable htDIV, Hashtable htBR, Hashtable htMBR, Hashtable htSG) {
    return getPathCVSL(m_elist, htDIV, htBR, htMBR, htSG);
  }


  /**
   *  Gets the pathCVSL attribute of the PokBaseABR object
   *
   *@param  _elCVSL  Description of the Parameter
   *@param  htDIV    Description of the Parameter
   *@param  htBR     Description of the Parameter
   *@param  htMBR    Description of the Parameter
   *@param  htSG     Description of the Parameter
   *@return          The pathCVSL value
   */
  public final Set getPathCVSL(EntityList _elCVSL, Hashtable htDIV, Hashtable htBR, Hashtable htMBR, Hashtable htSG) {
    Set pathSet = new HashSet();
    Vector vctSGR = getParentEntityIds(_elCVSL, getRootEntityType(_elCVSL), getRootEntityID(_elCVSL), "PSGSGR", "PSGSGRCVSL");
    for (int iSGR = 0; iSGR < vctSGR.size(); iSGR++) {
      Integer pdhSGR = (Integer) vctSGR.elementAt(iSGR);
      String strSGR = getAttributeValue(_elCVSL, "PSGSGR", pdhSGR.intValue(), "NAME");
      try {
        //PokPDHVEntity veSGR = new PokPDHVEntity(getProfile(), getDatabase(), "PSGSGR", pdhSGR.intValue(), "PSGVESGR1");
        ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, "EXTPSGVESGR1");
        EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
        java.lang.reflect.Array.set(eiParm, 0, new EntityItem(null, m_prof, "PSGSGR", pdhSGR.intValue()));
        D.ebug(D.EBUG_SPEW,"Creating Entity List for EXTPSGVESGR1");
        D.ebug(D.EBUG_SPEW,"Profile is " + m_prof);
        D.ebug(D.EBUG_SPEW,"Extractaction Item is" + eaItem);
        D.ebug(D.EBUG_SPEW,"Entity Item" + eiParm);
        EntityList elSGR = m_db.getEntityList(m_prof, eaItem, eiParm);

        appendPath(pathSet, getPathDIVtoSG(elSGR, htDIV, htBR, htMBR, htSG), "<!--SGR-->" + strSGR);
      } catch (Exception ex) {
        logMessage("Badness in getPathCVSL:#1" + ex.getMessage());
      }
    }

    Vector vctCCSL = getParentEntityIds(_elCVSL, getRootEntityType(_elCVSL), getRootEntityID(_elCVSL), "PSGCCSL", "PSGCCSLCVSL");
    for (int iCCSL = 0; iCCSL < vctCCSL.size(); iCCSL++) {
      Integer pdhCCSL = (Integer) vctCCSL.elementAt(iCCSL);
      String strCCSL = getAttributeValue(_elCVSL, "PSGCCSL", pdhCCSL.intValue(), "NAME");
      try {
        //PokPDHVEntity veCCSL = new PokPDHVEntity(getProfile(), getDatabase(), "PSGCCSL", pdhCCSL.intValue(), "PSGVECCSL1");

        ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, "EXTPSGVECCSL1");
        EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
        java.lang.reflect.Array.set(eiParm, 0, new EntityItem(null, m_prof, "PSGCCSL", pdhCCSL.intValue()));
        D.ebug(D.EBUG_SPEW,"Creating Entity List for EXTPSGVECCSL1");
        D.ebug(D.EBUG_SPEW,"Profile is " + m_prof);
        D.ebug(D.EBUG_SPEW,"Extractaction Item is" + eaItem);
        D.ebug(D.EBUG_SPEW,"Entity Item" + eiParm);
        EntityList elCCSL = m_db.getEntityList(m_prof, eaItem, eiParm);
        appendPath(pathSet, getPathDIVtoCSGR(elCCSL, htDIV, htBR, htMBR, htSG), "<!--CCSL-->" + strCCSL);
      } catch (Exception ex) {
        logMessage("Badness in getPathCVSL:#2" + ex.getMessage());
      }
    }

    Vector vctPCSL = getParentEntityIds(_elCVSL, getRootEntityType(_elCVSL), getRootEntityID(_elCVSL), "PSGPCSL", "PSGPCSLCVSL");
    for (int iPCSL = 0; iPCSL < vctPCSL.size(); iPCSL++) {
      Integer pdhPCSL = (Integer) vctPCSL.elementAt(iPCSL);
      String strPCSL = getAttributeValue(_elCVSL, "PSGPCSL", pdhPCSL.intValue(), "NAME");
      try {
        //PokPDHVEntity vePCSL = new PokPDHVEntity(getProfile(), getDatabase(), "PSGPCSL", pdhPCSL.intValue(), "PSGVEPCSL1");

        ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, "EXTPSGVEPCSL1");
        EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
        java.lang.reflect.Array.set(eiParm, 0, new EntityItem(null, m_prof, "PSGPCSL", pdhPCSL.intValue()));
        D.ebug(D.EBUG_SPEW,"Creating Entity List for EXTPSGVEPCSL1");
        D.ebug(D.EBUG_SPEW,"Profile is " + m_prof);
        D.ebug(D.EBUG_SPEW,"Extractaction Item is" + eaItem);
        D.ebug(D.EBUG_SPEW,"Entity Item" + eiParm);
        EntityList elPCSL = m_db.getEntityList(m_prof, eaItem, eiParm);
        appendPath(pathSet, getPathPCSL(elPCSL, htDIV, htBR, htMBR, htSG), "<!--PCSL-->" + strPCSL);
      } catch (Exception ex) {
        logMessage("Badness in getPathCVOF:CVSL" + ex.getMessage());
      }
    }

    return pathSet;
  }


  /**
   *  Insert the method's description here. Creation date: (8/14/2001 1:34:30
   *  PM)
   *
   *@param  htDIV  java.util.Hashtable
   *@param  htMBR  java.util.Hashtable
   *@param  htSG   java.util.Hashtable
   *@param  htBR   Description of the Parameter
   *@return        The pathPCSL value
   */
  public final Set getPathPCSL(Hashtable htDIV, Hashtable htBR, Hashtable htMBR, Hashtable htSG) {
    return getPathPCSL(m_elist, htDIV, htBR, htMBR, htSG);
  }


  /**
   *  Gets the pathPCSL attribute of the PokBaseABR object
   *
   *@param  _elPCSL  Description of the Parameter
   *@param  htDIV    Description of the Parameter
   *@param  htBR     Description of the Parameter
   *@param  htMBR    Description of the Parameter
   *@param  htSG     Description of the Parameter
   *@return          The pathPCSL value
   */
  public final Set getPathPCSL(EntityList _elPCSL, Hashtable htDIV, Hashtable htBR, Hashtable htMBR, Hashtable htSG) {
    Set pathSet = new HashSet();
    Vector vctSGR = getParentEntityIds(_elPCSL, getRootEntityType(_elPCSL), getRootEntityID(_elPCSL), "PSGSGR", "PSGSGRPCSL");
    for (int iSGR = 0; iSGR < vctSGR.size(); iSGR++) {
      Integer pdhSGR = (Integer) vctSGR.elementAt(iSGR);
      String strSGR = getAttributeValue(_elPCSL, "PSGSGR", pdhSGR.intValue(), "NAME");
      try {
        //PokPDHVEntity veSGR = new PokPDHVEntity(getProfile(), getDatabase(), "PSGSGR", pdhSGR.intValue(), "PSGVESGR1");

        ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, "EXTPSGVESGR1");
        EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
        java.lang.reflect.Array.set(eiParm, 0, new EntityItem(null, m_prof, "PSGSGR", pdhSGR.intValue()));
        D.ebug(D.EBUG_SPEW,"Creating Entity List for EXTPSGVESGR1");
        D.ebug(D.EBUG_SPEW,"Profile is " + m_prof);
        D.ebug(D.EBUG_SPEW,"Extractaction Item is" + eaItem);
        D.ebug(D.EBUG_SPEW,"Entity Item" + eiParm);
        EntityList elSGR = m_db.getEntityList(m_prof, eaItem, eiParm);
        appendPath(pathSet, getPathDIVtoSG(elSGR, htDIV, htBR, htMBR, htSG), "<!--SGR-->" + strSGR);
      } catch (Exception ex) {
        logMessage("Badness in _elPCSL:viSGR" + ex.getMessage());
      }
    }

    Vector vctCPSL = getParentEntityIds(_elPCSL, getRootEntityType(_elPCSL), getRootEntityID(_elPCSL), "PSGCPSL", "PSGCPSLPCSL");
    for (int iCPSL = 0; iCPSL < vctCPSL.size(); iCPSL++) {
      Integer pdhCPSL = (Integer) vctCPSL.elementAt(iCPSL);
      String strCPSL = getAttributeValue(_elPCSL, "PSGCPSL", pdhCPSL.intValue(), "NAME");
      try {
        //PokPDHVEntity veCPSL = new PokPDHVEntity(getProfile(), getDatabase(), "PSGCPSL", pdhCPSL.intValue(), "PSGVECPSL1");

        ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, "EXTPSGVECPSL1");
        EntityItem[] eiParm = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, 1);
        java.lang.reflect.Array.set(eiParm, 0, new EntityItem(null, m_prof, "PSGCPSL", pdhCPSL.intValue()));
        D.ebug(D.EBUG_SPEW,"Creating Entity List for EXTPSGVECPSL1");
        D.ebug(D.EBUG_SPEW,"Profile is " + m_prof);
        D.ebug(D.EBUG_SPEW,"Extractaction Item is" + eaItem);
        D.ebug(D.EBUG_SPEW,"Entity Item" + eiParm);
        EntityList elCPSL = m_db.getEntityList(m_prof, eaItem, eiParm);
        appendPath(pathSet, getPathCPSL(elCPSL, htDIV, htBR, htMBR, htSG), "<!--CPSL-->" + strCPSL);
      } catch (Exception ex) {
        logMessage("Badness in _elPCSL:viCPSL" + ex.getMessage());
      }
    }
    return pathSet;
  }


  /**
   *  Set the new Value for the specified attribute. This is only done locally.
   *  commit() must be called to send it to the PDH. Blobs are not supported at
   *  this time.
   *
   *@param  attrCode                      String attribute code to get value for
   *@param  value                         String with the new attribute value
   *@exception  UpdatePDHEntityException  Description of the Exception
   *@exception  LockPDHEntityException    Description of the Exception
   */
  public void setAttributeValue(String attrCode, String value) throws UpdatePDHEntityException, LockPDHEntityException {
    setAttributeValue(getRootEntityType(), getRootEntityID(), attrCode, value);
  }


  /**
   *  Set the new Value for the specified attribute. This is only done locally.
   *  commit() must be called to send it to the PDH. Blobs are not supported at
   *  this time.
   *
   *@param  _strEntityType                The new attributeValue value
   *@param  _iEntityID                    The new attributeValue value
   *@param  _strAttrCode                  The new attributeValue value
   *@param  _strValue                     The new attributeValue value
   *@exception  UpdatePDHEntityException  Description of the Exception
   *@exception  LockPDHEntityException    Description of the Exception
   */
  public void setAttributeValue(String _strEntityType, int _iEntityID, String _strAttrCode, String _strValue) throws UpdatePDHEntityException, LockPDHEntityException {

    EntityGroup entGroup = m_elist.getEntityGroup(_strEntityType);
    if (entGroup == null) {
      throw new UpdatePDHEntityException("Entitygroup was not found for entity type: " + _strEntityType);
    }

    if (!entGroup.isEditable()) {
      throw new UpdatePDHEntityException("Entitygroup is not editable for entity type: " + _strEntityType);
    }

    EntityItem entItem = entGroup.getEntityItem(_strEntityType, _iEntityID);
    if (entItem == null) {
      throw new UpdatePDHEntityException("Item was not found for entity type: " + _strEntityType + ":id: " + _iEntityID);
    }

    // must know attribute type to set the _strValue!!
    EANMetaAttribute eanMetaAtt = entGroup.getMetaAttribute(_strAttrCode);
    if (eanMetaAtt == null) {
      throw new UpdatePDHEntityException("MetaAttribute was not found for entity type: " + _strEntityType + " id: " + _iEntityID + " code: " + _strAttrCode);
    }

    LockItem sli = m_slg.getLockItem(_strEntityType);
    if (!sli.getLockedOn().equals(_strEntityType)) {
      sli.setLockedOn(_strEntityType);                 //lock if not locked
    }

    // get the current pdh attribute object
    EANAttribute eanAtt = entItem.getAttribute(_strAttrCode);

    //Very bad..unlock this and exit
    if (eanAtt == null) {
      m_slg.removeLockItem(_strEntityType);
      throw new UpdatePDHEntityException("PDHAttribute is not supported for entity type: " + _strEntityType + " id: " + _iEntityID + " code: " + _strAttrCode);
    }

    if (!eanAtt.isEditable()) {
      m_slg.removeLockItem(_strEntityType);
      throw new UpdatePDHEntityException("PDHAttribute is not editable for entity type: " + _strEntityType + " id: " + _iEntityID + " code: " + _strAttrCode);
    }

    // determine the object for put
    switch (eanMetaAtt.getAttributeType().charAt(0)) {
        case 'I':                                      //PDHIDAttribute idAtt = (PDHIDAttribute) eanAtt;
        case 'T':                                      //PDHTextAttribute textAtt = (PDHTextAttribute) eanAtt;
        case 'L':                                      //PDHLongTextAttribute longTextAtt = (PDHLongTextAttribute) eanAtt;
        case 'X':                                      //XML attribute as Long text
          try {
            log("Setting " + _strEntityType + ":" + _iEntityID + " attribute: " + _strAttrCode + " to " + _strValue);
            TextAttribute pdht = (TextAttribute) eanAtt;
            pdht.put(_strValue);
          } catch (EANBusinessRuleException be) {
            log("Business rule error when Setting " + _strEntityType + ":" + _iEntityID + "Text attribute: " + _strAttrCode + " to " + _strValue);
          }
          return;
        case 'F':
        case 'U':
        case 'S':
          StatusAttribute stAttr = (StatusAttribute) eanAtt;
          MetaFlag[] mfAttr = (MetaFlag[]) stAttr.get();
          for (int i = 0; i < mfAttr.length; i++) {
            if (mfAttr[i].getFlagCode().equals(_strValue)) {
              mfAttr[i].setSelected(true);
              break;
            }
          }
          try {
            log("Setting " + _strEntityType + ":" + _iEntityID + "Flag attribute: " + _strAttrCode + " to " + _strValue);
            stAttr.put(mfAttr);
          } catch (EANBusinessRuleException be) {
            log("Business rule error when Setting " + _strEntityType + ":" + _iEntityID + "Flag attribute: " + _strAttrCode + " to " + _strValue);
          }
          return;
        case 'P':                                      // Blob Prose
        case 'B':                                      // Blob Binary
        case 'D':                                      // Blob Document
          break;
    }

    // if you get here, there is something wrong
    m_slg.removeLockItem(_strEntityType);

    throw new UpdatePDHEntityException(_strValue + " is not a valid state for entity type: " + _strEntityType + " id: " + _iEntityID + " code: " + _strAttrCode);
  }


  /**
   *  Used to set NAME attribute in the DGENTITY
   *
   *@param  _ei          The new dGName value
   *@param  _strABRName  The new dGName value
   *@return              a String of ABRname, (EntityType:EntityID),
   *      NavigateValue, NavigateValue,...
   */
  public String setDGName(EntityItem _ei, String _strABRName) {

    String strDgName = null;

    if (_ei != null) {
      String rest = "";
      String entityType = "";
      int iColumn = -1;

      rest = _ei.toString();

      if (rest.length() > 0) {
        iColumn = rest.indexOf(':');
        if (iColumn != -1) {
          entityType = rest.substring(0, iColumn).trim();

          if (rest.substring(iColumn).length() > 1) {
            rest = rest.substring(iColumn + 1).trim();
          } else {
            rest = null;
          }
        }
      }

      strDgName = _strABRName + ", (" + entityType + ":" + _ei.getEntityID() + ")" + (rest == null ? "" : ", " + rest);

      //max length of NAME is 64
      if (strDgName.length() > 64) {
        strDgName = strDgName.substring(0, 64).trim();
        D.ebug(D.EBUG_SPEW,"****** strDgName " + strDgName);
        return strDgName;
      }
      D.ebug(D.EBUG_SPEW,"****** strDgName " + strDgName);
      return strDgName;
    }
    return strDgName;
  }



    /*
    **Get  Attributes of an Entity
    **
    */
   public void printNavigateAttributes(EntityItem entity, EntityGroup eg, boolean navigate){
     // StringBuffer strBuffer = new StringBuffer();
      println("<br><BR><table width=\"100%\">\n");

      for(int i=0;i<eg.getMetaAttributeCount();i++){
         EANMetaAttribute ma = eg.getMetaAttribute(i);
         EANAttribute att = entity.getAttribute(ma.getAttributeCode());
         if(navigate){
            if(i ==0)
               println("<tr><td class=\"PsgLabel\" width=\"50%\">Navigation Attribute</td><td class=\"PsgLabel\" width=\"50%\">Attribute Value</td></tr>");

            if(ma.isNavigate())
               println("<tr><td class=\"PsgText\" width=\"50%\">"+ ma.getLongDescription()+"</td><td class=\"PsgText\" width=\"50%\">" +(att == null || att.toString().length() ==0 ? "** Not Populated **" : att.toString()) + "</td></tr>");
         }
         else {
            if(i ==0)
               println("<tr><td class=\"PsgLabel\" width=\"50%\" >Attribute Description</td><td class=\"PsgLabel\" width=\"50%\">Attribute Value</td></tr>");
            println("<tr><td class=\"PsgText\" width=\"50%\" >"+ ma.getLongDescription()+"</td><td class=\"PsgText\" width=\"50%\">" +(att == null || att.toString().length() ==0 ? "** Not Populated **" : att.toString()) + "</td></tr>");
         }

      }
      println("</table>");

   }


  public boolean flagvalueEquals(String _strEntityType, int _iEntityID, String _strAttrCode, String _strValue)  {
    boolean bRetval = false;
    EntityGroup entGroup = null;
    D.ebug(D.EBUG_SPEW,"**********In flagvalueEquals");
    if (_strEntityType.equals(getEntityType()) && _iEntityID==getEntityID()) {      //get the parent entity group for root entity
      entGroup = m_elist.getParentEntityGroup();
    } else  {
      entGroup = m_elist.getEntityGroup(_strEntityType);
    }

    if (entGroup == null) {
      logError("Entitygroup was not found for entity type: " + _strEntityType);
      return false;
    }


    EntityItem entItem = entGroup.getEntityItem(_strEntityType, _iEntityID);
    if (entItem == null) {
      logError("Item was not found for entity type: " + _strEntityType + ":id: " + _iEntityID);
      return false;
    }

    // must know attribute type to set the _strValue!!
    EANMetaAttribute eanMetaAtt = entGroup.getMetaAttribute(_strAttrCode);
    if (eanMetaAtt == null) {
      logError("MetaAttribute was not found for entity type: " + _strEntityType + " id: " + _iEntityID + " code: " + _strAttrCode);
      return false;
    }


    // get the current pdh attribute object
    EANAttribute eanAtt = entItem.getAttribute(_strAttrCode);
    D.ebug(D.EBUG_SPEW,"**********In flagvalueEquals getting flag values for"+_strAttrCode);


   switch (eanMetaAtt.getAttributeType().charAt(0)) {
        case 'F':
        case 'U':
        case 'S':
          EANFlagAttribute stAttr = (EANFlagAttribute) eanAtt;
          if (stAttr!=null) {
            MetaFlag[] mfAttr = (MetaFlag[]) stAttr.get();
            for (int i = 0; i < mfAttr.length; i++) {
//logMessage("**********In flagvalueEquals comparing values "+mfAttr[i].getFlagCode()+":with"+_strValue);
              if (mfAttr[i].isSelected()) {
            	  D.ebug(D.EBUG_SPEW,"flagvalueEquals****************"+mfAttr[i].getFlagCode()+ " is SELECTED!");
                if (mfAttr[i].getFlagCode().equals(_strValue)) {
                	D.ebug(D.EBUG_SPEW,"flagvalueEquals**********"+_strValue+" Match found!");
                  bRetval=true;
                  break;
                }
              }
            }
          }
    }
   return bRetval;
  }


    public boolean flagvalueEquals(EntityItem _ei, String _strAttrCode, String _strValue)  {
    boolean bRetval = false;
    EntityGroup entGroup = null;
    D.ebug(D.EBUG_SPEW,"**********In flagvalueEquals");
    if (_ei == null) {
      logError("Entity Item is Null");
      return false;
    }
    entGroup = _ei.getEntityGroup();
    if (entGroup == null) {
      logError("Entitygroup was not found for entity type: " + _ei.getKey());
      return false;
    }



    // must know attribute type to set the _strValue!!
    EANMetaAttribute eanMetaAtt = entGroup.getMetaAttribute(_strAttrCode);
    if (eanMetaAtt == null) {
      logError("MetaAttribute was not found for entity type: "+_ei.getKey()+ " code: " + _strAttrCode);
      return false;
    }


    // get the current pdh attribute object
    EANAttribute eanAtt = _ei.getAttribute(_strAttrCode);
    D.ebug(D.EBUG_SPEW,"**********In flagvalueEquals getting flag values for"+_strAttrCode);


   switch (eanMetaAtt.getAttributeType().charAt(0)) {
        case 'F':
        case 'U':
        case 'S':
          EANFlagAttribute stAttr = (EANFlagAttribute) eanAtt;
          if (stAttr!=null) {
            MetaFlag[] mfAttr = (MetaFlag[]) stAttr.get();
            for (int i = 0; i < mfAttr.length; i++) {
//logMessage("**********In flagvalueEquals comparing values "+mfAttr[i].getFlagCode()+":with"+_strValue);
              if (mfAttr[i].isSelected()) {
            	  D.ebug(D.EBUG_SPEW,"flagvalueEquals****************"+mfAttr[i].getFlagCode()+ " is SELECTED!");
                if (mfAttr[i].getFlagCode().equals(_strValue)) {
                	D.ebug(D.EBUG_SPEW,"flagvalueEquals**********"+_strValue+" Match found!");
                  bRetval=true;
                  break;
                }
              }
            }
          }
    }
   return bRetval;
  }

   /**
   * A generic routine to make a hashtable of all
   * attributes for a given entity
   * Add
   */
   public Hashtable processT1Root(EntityGroup _eg) {
      Hashtable hAtt = new Hashtable();
      EntityItem entity = _eg.getEntityItem(0);

      for(int i=0; i < _eg.getMetaAttributeCount(); i++){
         EANMetaAttribute ma = _eg.getMetaAttribute(i);
         String strAttributeCode = ma.getAttributeCode();
         EANAttribute att = entity.getAttribute(ma.getAttributeCode());
         if (att != null) {
            if (!hAtt.containsKey(strAttributeCode)) {
               String strAttValue = att.toString();
               hAtt.put(strAttributeCode, strAttValue);
            }
         }
      }
      return hAtt;
   }

   /**
   * This produces a root change string buffer for basic ABR reporting
   */
   public String processT2Root(EntityGroup _eg, Hashtable _h, String _sT1, String _sT2) {
      StringBuffer strBuffer = new StringBuffer();
      EntityItem entity = _eg.getEntityItem(0);

    // GAB - enforce Classifications if they are defined
    boolean bHasClassificationGroups = (_eg.getClassificationGroupCount() > 0);
    if(bHasClassificationGroups) {
      entity.refreshClassifications();
    }

    // moved this part up here out of loop so it always gets printed
      strBuffer.append("<br><br><b>e-announce structured change report:</b>");
      strBuffer.append("<br><br><b>" + _eg.getLongDescription() + "</b>");
      strBuffer.append("<br><b>T1:</b> " + _sT1);
      strBuffer.append("<br><b>T2:</b> " + _sT2);
      strBuffer.append("<br><br><table border=1 width=\"100%\">\n");
      strBuffer.append("<tr><td class=\"PsgLabel\" width=\"12%\"><b>Transaction</b></td>" +
                       "<td class=\"PsgLabel\" width=\"16%\"><b>Attribute Description</b></td>" +
                       "<td class=\"PsgLabel\" width=\"30%\"><b>Value at T1</b></td>" +
                       "<td class=\"PsgLabel\" width=\"30%\"><b>Value at T2</b></td>" +
                       "<td class=\"PsgLabel\" width=\"12%\"><b>Userid</td></tr>");

    // Make sure it is not null...
      if(_h != null) {
      for(int i= 0; i < _eg.getMetaAttributeCount(); i++){
         String strChangeValue = null;
         String strT1Value = null;
            EANMetaAttribute ma = _eg.getMetaAttribute(i);
        // GAB - if this attribute is NOT to be displayed due to Classifiaction restraints -- skip it
        if(bHasClassificationGroups && !entity.isClassified(ma)) {
           continue;
        }

            String strAttCode = ma.getAttributeCode();
         EANAttribute att = entity.getAttribute(ma.getAttributeCode());
            String strAttDesc = (ma != null ? ma.getLongDescription() : DEF_NOT_POPULATED_HTML);
            String strT2value = (att != null ? att.toString() : DEF_NOT_POPULATED_HTML);

            // If it is in the hashtable.. the value existing previously.. lets get it.
            // it it is not in the hashtable.. it is only new .. if some value exists in T2
            // Other wize it is blank
            strChangeValue = "";
            if (_h.containsKey(strAttCode)) {
               strT1Value = (String)_h.get(strAttCode);
               if(!strT2value.equals(strT1Value)) {
                  strChangeValue = "Change";
               }
            } else if (att != null) {
               strChangeValue = "New";
            }
          strBuffer.append("<tr><td class=\"PsgText\" width=\"12%\">" + strChangeValue + "</td>" +
              "<td class=\"PsgText\" width=\"16%\">" + strAttDesc + "</td>" +
              "<td class=\"PsgText\" width=\"30%\">" + (strT1Value != null ? strT1Value : DEF_NOT_POPULATED_HTML) + "</td>" +
              "<td class=\"PsgText\" width=\"30%\">" + strT2value + "</td>" +
              "<td class=\"PsgText\" width=\"12%\">" + m_prof.getOPName() + "</td></tr>");
         }
      }
      strBuffer.append("</table>");
      return strBuffer.toString();
   }//processT2Root

   /**
   * Given an EntityItem and an AttributeCode
  * return the next to most recent update
  *
   */
   public String getT1(EntityItem _ei)throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      String strT1 = null;
      DatePackage dbNow = m_db.getDates();
      String strNow= dbNow.getNow();
      String strFromDate = strNow.substring(0,10) + "-00.00.00.000000";

      ReturnStatus returnStatus = new ReturnStatus(-1);
      ReturnDataResultSet rdrs = null;
      String strAttributeCode = getStatusAttributeCode(_ei);
      ResultSet rs = m_db.callGBL6032(returnStatus,m_prof.getOPWGID(),m_prof.getEnterprise(), _ei.getEntityType(),_ei.getEntityID(), strAttributeCode, strFromDate, strNow, m_prof.getValOn(), m_prof.getEffOn() );
      rdrs = new ReturnDataResultSet(rs);
      rs.close();
      rs = null;
      m_db.freeStatement();
      m_db.isPending();

      for(int row = 0; row < rdrs.getRowCount(); row++) {
         String strValfrom  = rdrs.getColumn(row,0); //Valfrom
         String strAttValue = rdrs.getColumn(row,1); //Attributevalue
         String strValto    = rdrs.getColumn(row,2); //Valto
         //String strEfffrom  = rdrs.getColumn(row,3); //Efffrom
         //String strEffto    = rdrs.getColumn(row,4); //Effto
         String strOpenID   = rdrs.getColumn(row,5); //Openid

         D.ebug(D.EBUG_SPEW,"getT1: row " + String.valueOf(row) +  ", " + strValfrom + ", " +  strAttValue + ", " + strValto + "," + strOpenID );

         if(row ==(rdrs.getRowCount() -2) && rdrs.getRowCount() > 1){
        	 D.ebug(D.EBUG_SPEW,"getT1: row " + String.valueOf(row) +  ", strValfrom " +  strValfrom + ", strAttValue " +  strAttValue);
             strT1 = strValfrom;
         }
      }

      // Now.. fix the date up if needed
      strT1 = strT1.replace(':', '.');
      strT1 = strT1.replace(' ', '-');

      return strT1;

   }//getT1

   public String displayAttributes(EntityItem _ei, EntityGroup _eg, boolean _bnavigate, int _idummy){
      if (_bnavigate) {
       return displayNavAttributes(_ei, _eg);
      } else {
       return displayAllAttributes(_ei, _eg);
      }
   }

   public String displayAttributes(EntityItem _ei, EntityGroup _eg, boolean _bnavigate){
      if (_bnavigate) {
       return displayNavAttributes(_ei, _eg);
      } else {
       return displayAllAttributes(_ei, _eg);
      }
   }

   public String displayAllAttributes(EntityItem _ei, EntityGroup _eg){
      StringBuffer strBuffer = new StringBuffer();
      strBuffer.append("<br><BR><table width=\"100%\">\n");
      strBuffer.append("<tr><td class=\"PsgLabel\" width=\"35%\">Attribute</td><td class=\"PsgLabel\" width=\"65%\">Value</td></tr>");
      for(int i=0;i < _eg.getMetaAttributeCount();i++){
         EANMetaAttribute ma = _eg.getMetaAttribute(i);
         EANAttribute att = _ei.getAttribute(ma.getAttributeCode());
         strBuffer.append("<tr><td class=\"PsgText\" width=\"35%\">"+ ma.getLongDescription()+"</td><td class=\"PsgText\" width=\"65%\">" +(att == null || att.toString().length() ==0 ? DEF_NOT_POPULATED_HTML : att.toString()) + "</td></tr>");
      }
    strBuffer.append("</table>");
      return strBuffer.toString();
  }

   public String displayNavAttributes(EntityItem _ei, EntityGroup _eg){
      StringBuffer strBuffer = new StringBuffer();
      strBuffer.append("<br><BR><table width=\"100%\">\n");
      strBuffer.append("<tr><td class=\"PsgLabel\" width=\"35%\">Navigation Attribute</td><td class=\"PsgLabel\" width=\"65%\">Value</td></tr>");
      for(int i=0;i < _eg.getMetaAttributeCount();i++){
         EANMetaAttribute ma = _eg.getMetaAttribute(i);
         EANAttribute att = _ei.getAttribute(ma.getAttributeCode());
         if(ma.isNavigate()) {
            strBuffer.append("<tr><td class=\"PsgText\" width=\"35%\">"+ ma.getLongDescription()+"</td><td class=\"PsgText\" width=\"65%\">" +(att == null || att.toString().length() ==0 ? DEF_NOT_POPULATED_HTML : att.toString()) + "</td></tr>");
         }
      }
    strBuffer.append("</table>");
      return strBuffer.toString();
  }

   public String displayStatuses(EntityItem _ei, EntityGroup _eg) {
      StringBuffer strBuffer = new StringBuffer();
      strBuffer.append("<br><BR><table width=\"100%\">\n");
      strBuffer.append("<tr><td class=\"PsgLabel\" width=\"35%\">Status Attribute</td><td class=\"PsgLabel\" width=\"65%\">Value</td></tr>");
      for(int i=0;i < _eg.getMetaAttributeCount();i++){
         EANMetaAttribute ma = _eg.getMetaAttribute(i);
         EANAttribute att = _ei.getAttribute(ma.getAttributeCode());
         if(ma instanceof MetaStatusAttribute) {
            strBuffer.append("<tr><td class=\"PsgText\" width=\"35%\">"+ ma.getLongDescription()+"</td><td class=\"PsgText\" width=\"65%\">" +(att == null || att.toString().length() ==0 ? DEF_NOT_POPULATED_HTML : att.toString()) + "</td></tr>");
         }
      }
    strBuffer.append("</table>");
      return strBuffer.toString();
  }

   /**
   * This sets the ABR's control block...
   */
  public void setControlBlock() {
      m_cbOn = new ControlBlock(m_strNow,m_strForever,m_strNow,m_strForever, m_prof.getOPWGID(), m_prof.getTranID());
   }

   /**
   *  This is a sharable version of the setFlagValue.
   */
   public void setFlagValue(EntityItem _ei, String _strAttributeCode, String _strAttributeValue) {
      if(_strAttributeValue != null ) {
         try {
            ReturnEntityKey rek = new ReturnEntityKey(_ei.getEntityType(), _ei.getEntityID(), true);
            SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), rek.getEntityType(), rek.getEntityID(), _strAttributeCode, _strAttributeValue, 1, m_cbOn);
            Vector vctAtts = new Vector();
            Vector vctReturnsEntityKeys = new Vector();
            vctAtts.addElement(sf);
            rek.m_vctAttributes = vctAtts;
            vctReturnsEntityKeys.addElement(rek);
            m_db.update(m_prof, vctReturnsEntityKeys, false, false);
            m_db.commit();
         } catch (COM.ibm.opicmpdh.middleware.MiddlewareException e) {
        	 D.ebug(D.EBUG_SPEW,"setFlagValue: " + e.getMessage());
         } catch (Exception e) {
        	 D.ebug(D.EBUG_SPEW,"setFlagValue: " + e.getMessage());
         }
      }
   }

   /**
   * This is a generic display header - This will now include status info as well.
   */
   public void displayHeader(EntityGroup _eg, EntityItem _ei) {
      if(_eg != null && _eg != null) {
         println("<FONT SIZE=6><b>" + _eg.getLongDescription() + "</b></FONT><br>");
         println(displayStatuses(_ei, _eg));
     println(displayNavAttributes(_ei, _eg));
      }
   }

   /**
   *  This is a generic way to retrieve a current Flag attribute's set flag code
   */
   public static String getFlagCode(EntityItem _ei, String _strAttributeCode) {
      EANFlagAttribute fa = (EANFlagAttribute)_ei.getAttribute(_strAttributeCode);
      if (fa == null) {
         return "NOT FOUND";
      }
      return fa.getFirstActiveFlagCode();
   }

   public static String getMetaAttributeDescription(EntityGroup _eg, String _strAttributeCode) {
      EANMetaAttribute ma = _eg.getMetaAttribute(_strAttributeCode);
      if (ma == null) {
         return "NOT FOUND";
      }
      return ma.getLongDescription();
   }

   public static String getMetaAttributeDescription(EntityItem _ei, String _strAttributeCode) {
      EANMetaAttribute ma = null;
      EntityGroup eg = _ei.getEntityGroup();
      if (eg == null) {
         EANAttribute att = _ei.getAttribute(_strAttributeCode);
         if (att != null) {
            ma = att.getMetaAttribute();
         }
      } else {
         ma = eg.getMetaAttribute(_strAttributeCode);
      }
      if (ma == null) {
         return "NOT FOUND";
      }
      return ma.getLongDescription();
   }

   public static String getAttributeValue(EntityItem _ei, String _strAttributeCode) {
      EANAttribute att = _ei.getAttribute(_strAttributeCode);
      if (att != null) {
            return att.toString();
      }
      return "";
   }


   /**
   *  This is a generic way to retrieve a current Status attribute's set flag code
   */
   public static String getStatusCode(EntityItem _ei, String _strAttributeCode) {
      return getFlagCode(_ei, _strAttributeCode);
   }


   /**
   * This is a generic check for the isParentClassify iformation...
   */
   public boolean isParentChildClassify(EntityItem _eiParent, EntityItem _eiChild) throws MiddlewareException, SQLException {
      ParentChildList pcl = new ParentChildList(m_db, m_prof, _eiParent,  _eiChild);
      try {
         return pcl.testParentChild(_eiParent, _eiChild);
      } catch (COM.ibm.eannounce.objects.EntityItemException e) {
         logMessage("EntityItemException: " + e.toString());
      }
      return false;
   }

   public String processChangeReport(String _strT1, String _strT2) throws MiddlewareException, LockPDHEntityException,UpdatePDHEntityException, Exception {

      // Build T1 Image

      m_prof.setValOn(_strT1);
      m_prof.setEffOn(_strT1);
      start_ABRBuild();

      EntityGroup egT1 = m_elist.getParentEntityGroup();
      //EntityItem eiT1 = egT1.getEntityItem(0);

      //reRun Extract Action for The T2 Image
      m_prof.setValOn(_strT2);
      m_prof.setEffOn(_strT2);
      start_ABRBuild();

      EntityGroup egT2 = m_elist.getParentEntityGroup();
      //EntityItem eiT2 = egT2.getEntityItem(0);

      return processT2Root(egT2, processT1Root(egT1), _strT1, _strT2);

   }

   public String getNextStatusCode(EntityItem _ei) {
      boolean bDR = isDraft(_ei);
      boolean bRR = isReadyForReview(_ei);
      //boolean bCN = isCancel(_ei);
      //boolean bFN = isFinal(_ei);
      boolean bCR = isChangeRev(_ei);
      boolean bCF = isChangeFinal(_ei);

      if (bDR) {
         return (String)getNextDraftState(_ei);
      }  else if (bCR) {
         return (String)getNextChangeRevState(_ei);
      } else if (bRR) {
         return (String)getNextReadyForReviewState(_ei);
      } else if (bCF) {
         return (String)getNextChangeFinalState(_ei);
      } else {
         return null;
      }
   }

   public boolean isStatusLess(EntityItem _eiParent, EntityItem _eiChild) {
      if(isChangeFinal(_eiParent)) {
         if(isReadyForReview(_eiChild) || isChangeFinal(_eiChild)) {
            return true;
         }
      } else if(isChangeRev(_eiParent)) {
         if(isDraft(_eiChild) || isChangeRev(_eiChild)) {
            return true;
         }
      }
      return false;
   }

   public boolean isChildEqualOrBetterStatus(EntityItem _eiParent, EntityItem _eiChild) {
      if (isFinal (_eiParent)) {
         if (isFinal(_eiChild)) {
            return true;
         }
      } else if(isChangeFinal(_eiParent)) {
         if (isChangeFinal(_eiChild)) {
            return true;
         }
      } else if(isCancel(_eiParent)) {
         if (isCancel(_eiChild)) {
            return true;
         }
      } else if (isChangeRev(_eiParent)) {
         if (isFinal(_eiChild) || isChangeFinal(_eiChild) || isChangeRev(_eiChild)) {
            return true;
         }
      } else if (isReadyForReview(_eiParent)) {
         if (isFinal(_eiChild) || isChangeFinal(_eiChild) || isChangeRev(_eiChild) ||isReadyForReview(_eiChild)) {
            return true;
         }
      } else {
         return true;
      }

      // Did not pass
      return false;
   }

   public boolean isStatusOK(EntityItem _eiParent, EntityItem _eiChild) {
      if(isDraft(_eiParent) || isChangeRev(_eiParent)) {
         if(isReadyForReview(_eiChild) || isFinal(_eiChild)) {
            return true;
         }
      } else if(isReadyForReview(_eiParent) || isChangeFinal(_eiParent) || isFinal(_eiParent)) {
         if(isFinal(_eiChild)) {
            return true;
         }
      }
      return false;
   }

   /**
   * This univeral checkM0008 - as a child.. am I classified correctly w/
   * all my immediate parents..
   */
   public boolean checkM0008(EntityGroup _eg, EntityItem _ei) throws MiddlewareException, SQLException {

      Vector vResult = new Vector();
      EntityItem eiRelator = null;
      EntityItem eiParent = null;

      // You usually have to go through these in an E-R-E
      for(int iU = 0; iU < _ei.getUpLinkCount(); iU++) {
         eiRelator = (EntityItem) _ei.getUpLink(iU);
         eiParent =  (EntityItem)eiRelator.getUpLink(0);
         if(eiParent != null) {
            EntityGroup egParent = eiParent.getEntityGroup();
            if (egParent.isClassified() && _eg.isClassified()) {
               if(!isParentChildClassify(eiParent, _ei)) {
                  EntityItem[] aei = new EntityItem[2];  //ParentEI [0],  ChildEI [1]
                  aei[0] = eiParent;
                  aei[1] = _ei;
                  vResult.addElement(aei);
               }
            }
         }
      }

      // now lets loop through this guy
      if (vResult.size() > 0) {
         for(int i = 0; i < vResult.size(); i++ ) {
            println("<br><br><b>M0008 - The following Child is not valid, given its Parent's current classification:</b>");
            EntityItem[] aeiResult = (EntityItem[])vResult.elementAt(i);
            if(aeiResult != null && aeiResult.length == 2) {
               EntityItem eiP = aeiResult[0];
               EntityItem eiC = aeiResult[1];
               if(eiP != null && eiC != null) {
                  EntityGroup egP = eiP.getEntityGroup();
                  EntityGroup egC = eiC.getEntityGroup();
                  println("<br><br><b>Parent: " + egP.getLongDescription() + "</b>");
                  println(displayNavAttributes(eiP , egP));
                  println("<br><br><b>Child: " + egC.getLongDescription() + "</b>");
                  println(displayNavAttributes(eiC , egC));
               }
            }
         }

         return false;
      }

      return true;

   }//M0008

   /**
   * This univeral checkM0007 - as a Parent. am I classified correctly w/
   * all my immediate Children..
   */
   public boolean checkM0007(EntityGroup _eg, EntityItem _ei) throws MiddlewareException, SQLException {

      Vector vResult = new Vector();
      EntityItem eiRelator = null;
      EntityItem eiChild = null;

      // You usually have to go through these in an E-R-E
      for(int ii = 0; ii < _ei.getDownLinkCount(); ii++) {
         eiRelator = (EntityItem) _ei.getDownLink(ii);
         eiChild =  (EntityItem)eiRelator.getDownLink(0);
         if(eiChild != null) {
            EntityGroup egChild = eiChild.getEntityGroup();
            if (egChild.isClassified() && _eg.isClassified()) {
               if(!isParentChildClassify(_ei, eiChild)) {
                  EntityItem[] aei = new EntityItem[2];  //ParentEI [0],  ChildEI [1]
                  aei[0] = _ei;
                  aei[1] = eiChild;
                  vResult.addElement(aei);
               }
            }
         }

      }

      // now lets loop through this guy
      if (vResult.size() > 0) {
         for(int i = 0; i < vResult.size(); i++ ) {
            println("<br><br><b>M0007 - The following Child is not valid, given its Parent's current classification:</b>");
            EntityItem[] aeiResult = (EntityItem[])vResult.elementAt(i);
            if(aeiResult != null && aeiResult.length == 2) {
               EntityItem eiP = aeiResult[0];
               EntityItem eiC = aeiResult[1];
               if(eiP != null && eiC != null) {
                  EntityGroup egP = eiP.getEntityGroup();
                  EntityGroup egC = eiC.getEntityGroup();
                  println("<br><br><b>Parent: " + egP.getLongDescription() + "</b>");
                  println(displayNavAttributes(eiP , egP));
                  println("<br><br><b>Child: " + egC.getLongDescription() + "</b>");
                  println(displayNavAttributes(eiC , egC));
               }
            }
         }

         return false;
      }

      return true;

   }//M0007

   public boolean checkS0002(EntityItem _ei) {
      return checkS0002(_ei, null);

   }

   public boolean checkS0002(EntityItem _ei,Hashtable _h ) {

      EntityGroup eg = _ei.getEntityGroup();
      boolean bOK = true;

      //
      //  This following the E-R-E Child pattern
      //  We skip any entity that is a managementGroupEntity
      //  This is covered by S0001
      //
      println("<br>S0002 - Checking " + eg.getLongDescription() + " for " + _ei.toString() + "." );
      println("<br>S0002 - This is an" + (_h == null ? " unrestricted " : " restricted " ) + " check.");

      for(int ii = 0; ii < _ei.getDownLinkCount(); ii++) {
         EntityItem eiRelator = (EntityItem) _ei.getDownLink(ii);
         if (eiRelator == null) {
            println("<br><br><b>S0002 - We have downlink to an non-existant relator... from " + _ei.getKey() + "</b>");
            return false;
         }
         EntityItem eiChild = (EntityItem) eiRelator.getDownLink(0);
         if (eiChild == null) {
            println("<br><br><b>S0002 - We have a Relator that points to nowhere..." + eiRelator.getKey() + "</b>");
            return false;
         }
         if (!(_h != null  && !_h.containsKey(eiChild.getEntityType()))) {
            if (isStatusableEntity(eiChild) && !isManagementGroupEntity(eiChild)) {
               if(!isStatusOK(_ei, eiChild)) {
                  EntityGroup egChild = eiChild.getEntityGroup();
                  println("<br><br>S0002 - This child does not have an acceptable status:");
                  println("<br>" + egChild.getLongDescription());
                  println(displayNavAttributes(eiChild , egChild));
                  println(displayStatuses(eiChild , egChild));
                  bOK = false;
               }
            }
         }

      }

      return bOK;

   }


   public boolean  checkM0005(EntityItem _ei, String _strRelatorType) {
      EntityGroup eg = _ei.getEntityGroup();
      EntityGroup egRel = m_elist.getEntityGroup(_strRelatorType); // It may be null..
      if(_ei.getDownLinkCount() == 0) {
         println("<br><br><b>M0005 -  " + eg.getLongDescription() + " requires at least one child entity - none were found.</b>");
         println(displayNavAttributes(_ei,  eg));
         return false;
      } else {
         boolean bfound = false;
         for (int ii = 0;ii < _ei.getDownLinkCount();ii++) {
            EntityItem eiRelator = (EntityItem)_ei.getDownLink(ii);
            if (eiRelator.getEntityType().equals(_strRelatorType)) {
               bfound = true;
               break;
            }
         }
         if (!bfound) {
            println("<br><br><b>M0005 -  " + eg.getLongDescription() + " requires at least one child entity connected via" + (egRel == null ? "Bad Relator passed" : egRel.getLongDescription()) + ".  None were found.</b>");
            println(displayNavAttributes(_ei,  eg));
            return false;
         }
      }

      return true;
   } // CheckM0005 Mod




   /**
   * Any COF (except those COFGRP = Base) must be attached as a child to a COF with COFGRP = Base through a COFCOFMGGRP
   */
   public boolean checkM0001(EntityItem _ei) {

      boolean bOK = true;
      EntityGroup eg = _ei.getEntityGroup();

      if (!_ei.getEntityType().equals("COMMERCIALOF")) {
         println("<br><br><b>M0001 - Skipping Check .. this is not a Commercial Offering, but a " + eg.getLongDescription());
      }

      String strFlagCode = getFlagCode(_ei,"COFGRP");
      // If it is base.. we do not need to worry .. return true
      if(strFlagCode.equals("300") || strFlagCode.equals("301")) {
         println("<br><br><b>M0001 - Skipping Check ..  This commercial offering is a " + getMetaAttributeDescription(eg,"COFGRP") + " Commercial Offering.");
         return bOK;
      }

      println("<br><br><b>M0001 - Checking. This commercial offering is a " + getMetaAttributeDescription(eg,"COFGRP") + " Commercial Offering.");

      // o.k. lets look up a bit.. and see what we can see...
      for(int ii = 0; ii < _ei.getUpLinkCount();ii++) {
         EntityItem eiRelator = (EntityItem)_ei.getUpLink(ii);
         // O.K. We found a link UP
         if (eiRelator.getEntityType().equals("COFMEMBERCOFOMG")) {
            EntityItem eiParent = (EntityItem)eiRelator.getUpLink(0);
            //EntityGroup egParent = eiParent.getEntityGroup();
            for(int iy = 0; iy < eiParent.getUpLinkCount();iy++) {
               EntityItem eiRelator2 = (EntityItem)eiParent.getUpLink(iy);
               if (eiRelator2.getEntityType().equals("COFOWNSCOFOMG")) {
                  EntityItem eiTop = (EntityItem)eiRelator2.getUpLink(0);
                 // EntityGroup egTop = eiTop.getEntityGroup();
                  String strTopFlagCode = getFlagCode(eiTop,"COFGRP");
                  if(strTopFlagCode.equals("300") || strTopFlagCode.equals("301")) {
                     // We found a good one.. lets return true...
                     println("<br><br><b>M0001 - Check Passed.  Located the \"Base\" Commercial Offering parent: " + eiTop.toString());
                     return bOK;
                  }
               }
            }
         }
      }

      if (!isReadyForReview(_ei,getNextStatusCode(_ei))) {
         println("<br><br><b>M0001 - Warning. A Base Commercial Offering Cannot be located..");
      } else if (!isFinal(_ei,getNextStatusCode(_ei))) {
         println("<br><br><b>M0001 - Warning. A Base Commercial Offering Cannot be located..");
         bOK = false;
      }

      return bOK;


   }//checkM0001


   public boolean checkM0002(EntityItem _ei) {

     // EntityGroup eg = _ei.getEntityGroup();
      boolean bOK = false;

      // o.k. lets look up a bit.. and see what we can see...
      for(int ii = 0; ii < _ei.getUpLinkCount();ii++) {
         EntityItem eiRelator = (EntityItem)_ei.getUpLink(ii);
         // O.K. We found a link UP
         if (eiRelator.getEntityType().equals("OOFMEMBERCOFOMG")) {
            EntityItem eiParent = (EntityItem)eiRelator.getUpLink(0);
            //EntityGroup egParent = eiParent.getEntityGroup();
            for(int iy = 0; iy < eiParent.getUpLinkCount();iy++) {
               EntityItem eiRelator2 = (EntityItem)eiParent.getUpLink(iy);
               if (eiRelator2.getEntityType().equals("COFOWNSOOFOMG")) {
                  bOK = true;
               }
            }
         }
      }

      if (!bOK) {
         println("<br><br><b>M0002 - This order Offering is not a child of a Commercial Offering through a Commercial to Order Orffering Management Groop.");
      }

      return bOK;
   }//checkM0002


   public boolean checkM0003(EntityItem _ei) {

      boolean bOK = true;
      boolean bGotOne = false;
      EntityGroup eg = _ei.getEntityGroup();

      if (eg.getEntityType().equals("ORDEROF")) {
         println("<br><br><b>M0003 - Passed.  The Entity Needs to be an ORDEROF. The ABR passed in a " + eg.getLongDescription() + "(" + eg.getEntityType() + ")");
         return bOK;
      }

      String strSubCat = getFlagCode(_ei,"OOFSUBCAT");
      if (!strSubCat.equals("500")) {
         println("<br><br><b>M0003 - Passed.  This is not a feature Code Order Offering.");
         return bOK;
      }

      EANAttribute att = _ei.getAttribute("FEATURECODE");
      String strOOFFeatureCode =  (att == null ? "NONE" : att.toString());

      // Lets Check!
      // Need to know warning vs .. failure still
      for(int j = 0; j < _ei.getDownLinkCount(); j++) {
         EntityItem eiRelator = (EntityItem)_ei.getDownLink(j);
         if (eiRelator.getEntityType().equals("OOFFUP")) {
            if (bGotOne) {
               bOK = false;
               // Dump.. you have too manay
            } else {
               bGotOne = true;
            }
            EntityItem eiChild = (EntityItem) eiRelator.getDownLink(0);
            EntityGroup egChild = eiChild.getEntityGroup();

            EANAttribute att2 = _ei.getAttribute("FEATURECODE");
            String strFUPFeatureCode =  (att2 == null ? "NONE" : att2.toString());
            if (!strFUPFeatureCode.equals(strOOFFeatureCode)) {
               bOK = false;
               // Here comes the message
               println("<br><br><b>Feature Code of " + eg.getLongDescription()   + "(" + strOOFFeatureCode + ") is not identical to the Feature Code of the " + egChild.getLongDescription()  + "(" + strFUPFeatureCode + ")</b>");
               println("<br><br><b> " + egChild.getLongDescription());
               println(displayNavAttributes(eiChild , egChild));
            }
         }
      }

      return bOK;

   }//M0003


   /**
   * This looks for a specific grandchild ..
   * and returns true if it finds one.. Otherwise it fails
   *
   */
   public boolean checkM0004GrandChild(EntityItem _ei) {

      // First.. get the entitytype...

      String strEntityType = _ei.getEntityType();
      String strFlagCode = null;
      String strAttributeCode = null;
      String strRelatorType1 = null;
      String strRelatorType2 = null;
      boolean bContinue = false;
      String strFailMessage = null;
      String strSkipMessage = null;
      String strPassMessage = null;

      //
      // Based upon passed EntityType.. lets set up some rules.. and a generic message for now
      //
      if (strEntityType.equals("COMMERCIALOF")) {  // Grand Child Request
         strAttributeCode = "COFGRP";
         strFlagCode = getFlagCode(_ei, strAttributeCode);
         strRelatorType1 = "COFOWNSOOFOMG";
         strRelatorType2 = "OOFMEMBERCOFOMG";
         strSkipMessage = "Your Commercial Offering's Group attribute is not set to \"initial\". So no check is needed.";
         strPassMessage = "Foung an Order Offering";
         strFailMessage  = "No Order Offerings could be found.  There must be at least one Order Offering, attached through an COF to OOF Management Group from this Commercial Offering";
         bContinue = (strFlagCode == "302" || strFlagCode == "303");
      }

      //
      // If we do not need to continue.. we pass
      //
      if (!bContinue) {
         // Then it passed. because we are not in an initial state...
         println("<br><br><b>M0004 - Passed.  " + strSkipMessage + "</b> ");
         return true;
      }

      // Assume the E-R-E scenario
      for(int ii = 0; ii < _ei.getDownLinkCount(); ii++) {
         EntityItem eiRelator1 = (EntityItem)_ei.getDownLink(ii);
         if (eiRelator1.getEntityType().equals(strRelatorType1)) {
            EntityItem eiChild = (EntityItem)eiRelator1.getDownLink(0);
            for(int iy = 0; iy < eiChild.getDownLinkCount(); iy++) {
               EntityItem eiRelator2 = (EntityItem)eiChild.getDownLink(iy);
               if (eiRelator2.getEntityType().equals(strRelatorType2)) {
                  EntityItem eiGrandChild = (EntityItem)eiRelator2.getDownLink(0);
                  println("<br><br><b>M0004 - Passed.  " + strPassMessage  + ":  " + eiGrandChild.toString() + ". </b>");
                  return true;
               }
            }
         }
      }

      println("<br><br><b> M0004 - Failed.  " + strFailMessage + "<b/>");
      return false;

   }//checkM0004

   /**
   * This looks for a specific grandchild ..
   * and returns true if it finds one.. Otherwise it fails
   *
   */
   public boolean checkM0004Child(EntityItem _ei) {

      String strEntityType = _ei.getEntityType();
      String strRelatorType1 = null;
      boolean bContinue = false;
      String strFailMessage = null;
      String strSkipMessage = null;
      String strPassMessage = null;

      if (strEntityType.equals("COFOOFMGMTGRP")) {  // Child Request
         strRelatorType1 = "OOFMEMBERCOFOMG";
         strSkipMessage = "Your Commercial Offering's Group attribute is not set to \"initial\". So no check is needed.";
         strPassMessage = "Foung an Order Offering";
         strFailMessage  = "No Order Offerings could be found.  There must be at least one Order Offering, attached through an COF to OOF Management Group.";
         bContinue = true;
      }

      //
      // If we do not need to continue.. we pass
      //
      if (!bContinue) {
         // Then it passed. because we are not in an initial state...
         println("<br><br><b>M0004 - Passed.  " + strSkipMessage + "</b> ");
         return true;
      }

      // Assume the E-R-E scenario
      for(int ii = 0; ii < _ei.getDownLinkCount(); ii++) {
         EntityItem eiRelator1 = (EntityItem)_ei.getDownLink(ii);
         if (eiRelator1.getEntityType().equals(strRelatorType1)) {
            EntityItem eiChild = (EntityItem)eiRelator1.getDownLink(0);
            println("<br><br><b>M0004 - Passed.  " + strPassMessage  + ":  " + eiChild.toString() + ". </b>");
            return true;
         }
      }

      println("<br><br><b> M0004 - Failed.  " + strFailMessage + "<b/>");
      return false;

   }//checkM0004

   public boolean  checkM0020(EntityItem _ei) {

      boolean bOK = true;

      println("<br><br><b><I>M0020:</I> Checking " + _ei.toString() + "for a Valid Parent");
      if (!_ei.getEntityType().equals("PHYSICALOF")) {
         println("<br><br><b><I>M0020: is not a Physical Offering.. skipping this check");
         return bOK;
      }

      for(int ii = 0; ii < _ei.getUpLinkCount(); ii++) {
      EntityItem eiRelator = (EntityItem)_ei.getUpLink(ii);
      if(eiRelator.getEntityType().equals("POFPOFMGMTGRP") || eiRelator.getEntityType().equals("FUPPOFMGMTGRP")) {
         println("<br><br><b><I>M0020: Passed.. Found a valid Management group as parent.");
         return bOK;
      }
    }

    //
    // If we make it this far.. we could not find one!
      //

      if (isReadyForReview(_ei,getNextStatusCode(_ei)))  {
        println("<br><br><b><I>Warning:</I> This Physical Offering does not have an acceptable parent. </b>");
      } else if (isFinal(_ei,getNextStatusCode(_ei))) {
         bOK = false;
        println("<br><br><b><I>Failure:</I> This Physical Offering does not have an acceptable parent. </b>");
      }

      return bOK;

   }


   public boolean checkA0001(EntityItem _ei) {

      EntityGroup eg = _ei.getEntityGroup();
      println("<br>A0001 - Checking " + eg.getLongDescription() + " " + _ei.toString());
      for(int ii = 0;ii < _ei.getDownLinkCount();ii++) {
         EntityItem eiRelator = (EntityItem)_ei.getDownLink(ii);
         EntityItem eiChild = (EntityItem)eiRelator.getDownLink(0);
         if (eiChild.getEntityType().equals("AVAIL")) {
            if (getFlagCode(eiChild,"AVAILTYPE").equals("146")) {
               return true;
            }
         }
      }

      if (isReadyForReview(_ei)) {
         println("<br>A0001 - Warning - You need at least one Planned Availability record attached to your " + eg.getLongDescription());
         return true;
      }

      println("<br>A0001 - Failure - You need at least one Planned Availability record attached to your " + eg.getLongDescription());
      return false;

   }//checkA0001


   /**
   * This checks a given Set of Children  that are avails..
   * That none of them overlap, etc, etc
   */
   public boolean  checkA0002(EntityItem _ei){

      Vector v1 = new Vector();
      Vector v2 = new Vector();
      Vector v3 = new Vector();
      Vector v4 = new Vector();
      Vector v5 = new Vector();
      Vector v6 = new Vector();
      Vector v7 = new Vector();
      Vector v8 = new Vector();
      Vector vctSummary = new Vector();
      Hashtable hshMyRow = new Hashtable();
      Hashtable hshCountryList = new Hashtable();

     // EntityGroup eg = _ei.getEntityGroup();

      boolean bOK = true;

      // Lets loop through the Avail Entities here...
      for(int ii = 0; ii < _ei.getDownLinkCount(); ii++ ) {

         EntityItem eiRelator = (EntityItem)_ei.getDownLink(ii);
         EntityItem eiAvail = (EntityItem)eiRelator.getDownLink(0);
         EntityGroup egAvail = eiAvail.getEntityGroup();

         // We got one!!
         if (eiAvail.getEntityType().equals("AVAIL")) {
            String strK1 = null;
           // String strK2 = null;
            String strK3 = null;
            //String strK4 = null;

            EANAttribute   att = eiAvail.getAttribute("AVAILTYPE");
            strK1 = (att == null ? "": att.toString());
            if(strK1.equals("First Order")) {
               v1.addElement(eiAvail);
            } else if (strK1.equals("Planned Availability")) {
               v2.addElement(eiAvail);
            } else if (strK1.equals("Last Initial Order")) {
               v3.addElement(eiAvail);
            } else if (strK1.equals("Last Order")) {
               v4.addElement(eiAvail);
            } else if (strK1.equals("Last Return")) {
               v5.addElement(eiAvail);
            } else if (strK1.equals("End of Service")) {
               v6.addElement(eiAvail);
            } else if (strK1.equals("End of Dev Support")) {
               v7.addElement(eiAvail);
            } else {
               v8.addElement(eiAvail);
            }
            att = eiAvail.getAttribute("EFFECTIVEDATE");
            //strK2 = (att == null ? "" : att.toString());
            att = eiAvail.getAttribute("ANNCODENAME");
            strK3 = (att == null ? "" : att.toString());
            att = eiAvail.getAttribute("COUNTRYLIST");
            //strK4 = (att == null ? "": att.toString());



            String strKey = null;
            if (att != null) {
               MetaFlag[] amf = (MetaFlag[])att.get();
               for (int f=0; f < amf.length; f++) {
                  String strFlagCode = amf[f].getFlagCode();

            //There may be only one row for an AVAILTYPE + Country (from COUNTRYLIST).
                  if (amf[f].isSelected()) {
                     if (hshCountryList.containsKey(strK1+"|"+strFlagCode)) {
                        bOK = false;
                        println("<br><br><b>The following Avail is referencing a country that has already been referenced " + amf[f].getLongDescription() + "</b>");
                        println(displayNavAttributes(eiAvail, egAvail));
                        break;
                     } else {
                        hshCountryList.put(strK1+"|"+strFlagCode,"HI");
                     }

              //For all AVAILS with relators to the same instance of an entity, the Avail ANNCODENAME, AVAILTYPE and "Geo/Country" must be unique.
              strKey = strK1+ "|" +strK3+"|"+ strFlagCode;
              if (hshMyRow.containsKey(strKey)) {
                //println("<br><br><b>The following entity has inconsistent " + egAvail.getLongDescription() + "</b>");
                println("<br><br><b>The following entity has multiple dates for the same Type of AVAIL " + egAvail.getLongDescription() + "</b>");
                println(displayNavAttributes(eiAvail, egAvail));
                bOK = false;
              }   else {
                hshMyRow.put(strKey,"HI");
              }
                  }
               }
            }


         }
      }
      //
      // Pack the ordered vector
      vctSummary.addElement(v1);
      vctSummary.addElement(v2);
      vctSummary.addElement(v3);
      vctSummary.addElement(v4);
      vctSummary.addElement(v5);
      vctSummary.addElement(v6);
      vctSummary.addElement(v7);
      vctSummary.addElement(v8);

      //
      //  Now.. print the table.. when it fails
      //
    if (!bOK) {
      println("<br/>A0002 - Available Partition Check - Summary Information Report:");
      println("<br><BR><table border=1 width=\"100%\">\n");
      println(
        "<tr><td class=\"PsgLabel\" width=\"25%\"><b>Availability Type</b></td>" +
        "<td class=\"PsgLabel\" width=\"25%\"><b>Effective Date</b></td>" +
        "<td class=\"PsgLabel\" width=\"25%\"><b>Announcement Code Name</b></td>" +
        "<td class=\"PsgLabel\" width=\"25%\"><b>Country List</b></td></tr>");
      for(int ii = 0; ii < vctSummary.size(); ii++ ) {
        Vector vct1 = (Vector)vctSummary.elementAt(ii);
        for(int ij = 0; ij  < vct1.size(); ij++) {
          displayAvailRows((EntityItem)vct1.elementAt(ij));
        }
      }
      println("</table>");
    }


      return bOK;

   } //checkA0002

   /*
   * Check all Children of  this group..
   * It they are Management Group Entities..
   * Lets do a check
   */
   public boolean checkS0001(EntityItem _ei) {

      EntityGroup eg = _ei.getEntityGroup();
      // DWB
      // Will be Later expanded to check the grandchildren
      // We will have to introduce a way to implemented an update
      // Vector set to update all statuses..
      boolean bOK = true;
      //We will be following the E-R-E model assumption
      println("<br><b>S0001 - Checking " + eg.getLongDescription() + " for " + _ei.toString() + "." );
      for(int iD = 0; iD < _ei.getDownLinkCount(); iD++) {
         EntityItem eiRelator = (EntityItem) _ei.getDownLink(iD);
         EntityItem eiChild = (EntityItem)eiRelator.getDownLink(0);
         EntityGroup egChild = eiChild.getEntityGroup();
         if (isManagementGroupEntity(eiChild)) {
            if (!isStatusOK(_ei,eiChild)) {
               for(int i = 0; i < eiChild.getDownLinkCount(); i++) {
                  EntityItem eiGRelator = (EntityItem) eiChild.getDownLink(i);
                  EntityItem eiGChild = (EntityItem)eiGRelator.getDownLink(0);
                  EntityGroup egGChild = eiGChild.getEntityGroup();
                  if (isManagementGroupChildrenEntity(eiGChild)) {
                     if (!isStatusOK(_ei,eiGChild)) {
                        println("<br><br><b>S0001 - Management Group and one or more Children do not have an acceptable status");
                        println("<br><br><b>Child: " + egChild.getLongDescription() + "</b>");
                        println(displayNavAttributes(eiChild , egChild));
                        println("<br><br><b>Grand Child: " + egGChild.getLongDescription() + "</b>");
                        println(displayNavAttributes(eiGChild , egGChild));
                        bOK = false;

                     }

                  }

               }

            }
         }

      }

      return bOK;

   }//checkS0001

   /*public boolean checkA0003(EntityItem _ei) {

      EntityGroup eg = _ei.getEntityGroup();

      Vector v1 = new Vector();
      Vector v2 = new Vector();
      Vector v3 = new Vector();
      Vector v4 = new Vector();
      Vector v5 = new Vector();
      Vector v6 = new Vector();
      Vector v7 = new Vector();
      Vector v8 = new Vector();
      Vector vctSummary = new Vector();

      boolean bOK = true;

      // Lets loop through the Avail Entities here...
      for(int ii = 0; ii < _ei.getDownLinkCount(); ii++ ) {

         EntityItem eiRelator = (EntityItem)_ei.getDownLink(ii);
         EntityItem eiAvail = (EntityItem)eiRelator.getDownLink(0);
         EntityGroup egAvail = eiAvail.getEntityGroup();

         // We got one!!
         if (eiAvail.getEntityType().equals("AVAIL")) {
            String strK1 = null;
            String strK2 = null;
            String strK3 = null;
            String strK4 = null;

            EANAttribute   att = eiAvail.getAttribute("AVAILTYPE");
            strK1 = (att == null ? "": att.toString());
            if(strK1.equals("First Order")) {
               v1.addElement(eiAvail);
            } else if (strK1.equals("Planned Availability")) {
               v2.addElement(eiAvail);
            } else if (strK1.equals("Last Initial Order")) {
               v3.addElement(eiAvail);
            } else if (strK1.equals("Last Order")) {
               v4.addElement(eiAvail);
            } else if (strK1.equals("Last Return")) {
               v5.addElement(eiAvail);
            } else if (strK1.equals("End of Service")) {
               v6.addElement(eiAvail);
            } else if (strK1.equals("End of Dev Support")) {
               v7.addElement(eiAvail);
            } else {
               v8.addElement(eiAvail);
            }
         }
      }

      // OK.. now pack the vector
      vctSummary.addElement(v1);
      vctSummary.addElement(v2);
      vctSummary.addElement(v3);
      vctSummary.addElement(v4);
      vctSummary.addElement(v5);
      vctSummary.addElement(v6);
      vctSummary.addElement(v7);
      vctSummary.addElement(v8);

      String strEffDate_Result = null;
      Calendar cl = Calendar.getInstance();
      Calendar cltemp = Calendar.getInstance();

      for(int ii = 0;ii < vctSummary.size();ii++) {
         Vector vct1 = (Vector)vctSummary.elementAt(ii);
         for(int ij = 0; ij < vct1.size(); ij++) {
            EntityItem ei = (EntityItem) vct1.elementAt(ij);
            EANAttribute attEffDate = ei.getAttribute("EFFECTIVEDATE");
            strEffDate_Result = (attEffDate != null ? attEffDate.toString() : DEF_NOT_POPULATED_HTML);
            println("<br><br> Checking effective date: " + strEffDate_Result + " for " + ei.toString());
            if(strEffDate_Result.length() == 10 && !strEffDate_Result.equals(DEF_NOT_POPULATED_HTML)) {
               String strYear = strEffDate_Result.substring(0,4);
               String strMonth = strEffDate_Result.substring(5,7);
               String strDate = strEffDate_Result.substring(8);
               int iYear = new Integer(strYear).intValue();
               int iMonth = new Integer(strMonth).intValue();
               int iDate = new Integer(strDate).intValue();
               if(ii == 0 && ij == 0) {
                  cl.set(iYear, iMonth, iDate);
               } else {
                  cltemp.set(iYear, iMonth, iDate);
                  if(cl.before(cltemp)) {
                     cl.set(iYear, iMonth, iDate);
                  } else {
                     // here is the error...
                     bOK = false;
                     println("<br><br><b>The following entity has inconsistent dates:" + cltemp + " is less than " + cl + "</b>");
                     println("<br><br><b>" + eg.getLongDescription() + "</b>");
                  }
               }
            }
         }
      }

      //
      //  Now.. print the table.. no matter what
      //
      println("<br/>A0002 - Available Partition Check - Summary Information Report:");
      println("<br><BR><table border=1 width=\"100%\">\n");
      println(
         "<tr><td class=\"PsgLabel\" width=\"25%\"><b>Availability Type</b></td>" +
         "<td class=\"PsgLabel\" width=\"25%\"><b>Effective Date</b></td>" +
         "<td class=\"PsgLabel\" width=\"25%\"><b>Announcement Code Name</b></td>" +
         "<td class=\"PsgLabel\" width=\"25%\"><b>Country List</b></td></tr>");
      for(int ii = 0; ii < vctSummary.size(); ii++ ) {
         Vector vct1 = (Vector)vctSummary.elementAt(ii);
         for(int ij = 0; ij  < vct1.size(); ij++) {
            displayAvailRows((EntityItem)vct1.elementAt(ij));
         }
      }
      println("</table>");

      return bOK;

   } // checkA0003 */


   //per spec changes
   public boolean checkA0003(EntityItem _ei) {

    if (!checkA0002(_ei)) return false;

      EntityGroup eg = _ei.getEntityGroup();

      Vector v1 = new Vector();
      Vector v2 = new Vector();
      Vector v3 = new Vector();
      Vector v4 = new Vector();
      Vector v5 = new Vector();
      Vector v6 = new Vector();
      Vector v7 = new Vector();
      Vector v8 = new Vector();
      Vector vctSummary = new Vector();
      //Hashtable hshCountryList = new Hashtable();

      boolean bOK = true;

      // Lets loop through the Avail Entities here...
      for(int ii = 0; ii < _ei.getDownLinkCount(); ii++ ) {

         EntityItem eiRelator = (EntityItem)_ei.getDownLink(ii);
         EntityItem eiAvail = (EntityItem)eiRelator.getDownLink(0);
         //EntityGroup egAvail = eiAvail.getEntityGroup();

         // We got one!!
         if (eiAvail.getEntityType().equals("AVAIL")) {
            String strK1 = null;
            //String strK2 = null;
            //String strK3 = null;
            //String strK4 = null;

        EANAttribute att = eiAvail.getAttribute("AVAILTYPE");
        strK1 = (att == null ? "": att.toString());
        if(strK1.equals("First Order")) {
          v1.addElement(eiAvail);
        } else if (strK1.equals("Planned Availability")) {
          v2.addElement(eiAvail);
        } else if (strK1.equals("Last Initial Order")) {
          v3.addElement(eiAvail);
        } else if (strK1.equals("Last Order")) {
          v4.addElement(eiAvail);
        } else if (strK1.equals("Last Return")) {
          v5.addElement(eiAvail);
        } else if (strK1.equals("End of Service")) {
          v6.addElement(eiAvail);
        } else if (strK1.equals("End of Dev Support")) {
          v7.addElement(eiAvail);
        } else {
          v8.addElement(eiAvail);
        }

         }
      }

      // OK.. now pack the vector
      vctSummary.addElement(v1);
      vctSummary.addElement(v2);
      vctSummary.addElement(v3);
      vctSummary.addElement(v4);
      vctSummary.addElement(v5);
      vctSummary.addElement(v6);
      vctSummary.addElement(v7);
      vctSummary.addElement(v8);

      String strEffDate_Result = null;
      Calendar cl = Calendar.getInstance();
      Calendar cltemp = Calendar.getInstance();

      for(int ii = 0;ii < vctSummary.size();ii++) {
         Vector vct1 = (Vector)vctSummary.elementAt(ii);
         for(int ij = 0; ij < vct1.size(); ij++) {
            EntityItem ei = (EntityItem) vct1.elementAt(ij);
            EANAttribute attEffDate = ei.getAttribute("EFFECTIVEDATE");
            strEffDate_Result = (attEffDate != null ? attEffDate.toString() : DEF_NOT_POPULATED_HTML);
            println("<br><br> Checking effective date: " + strEffDate_Result + " for " + ei.toString());
            if(strEffDate_Result.length() == 10 && !strEffDate_Result.equals(DEF_NOT_POPULATED_HTML)) {
               String strYear = strEffDate_Result.substring(0,4);
               String strMonth = strEffDate_Result.substring(5,7);
               String strDate = strEffDate_Result.substring(8);
               int iYear = new Integer(strYear).intValue();
               int iMonth = new Integer(strMonth).intValue();
               int iDate = new Integer(strDate).intValue();
               if(ii == 0 && ij == 0) {
                  cl.set(iYear, iMonth, iDate);
               } else {
                  cltemp.set(iYear, iMonth, iDate);
                  if(cl.before(cltemp)) {
                     cl.set(iYear, iMonth, iDate);
                  } else {
                     // here is the error...
                     bOK = false;
                     println("<br><br><b>The following entity has inconsistent dates:" + cltemp.get(Calendar.YEAR)+"-"+cltemp.get(Calendar.MONTH)+"-"+cltemp.get(Calendar.DAY_OF_MONTH)+ " is less than " + cl.get(Calendar.YEAR)+"-"+cl.get(Calendar.MONTH)+"-"+cl.get(Calendar.DAY_OF_MONTH)+ "</b>");
                     println("<br><b>for one or more Countries specified via Availability</b>");
                     println("<br><br><b>" + eg.getLongDescription() + "</b>");
                  }
               }
            }
         }
      }

      //
      //  Now.. print the table.. when failure
      //
    if (!bOK) {
      println("<br/>A0003 - Available Partition Check - Summary Information Report:");
      println("<br><BR><table border=1 width=\"100%\">\n");
      println(
        "<tr><td class=\"PsgLabel\" width=\"25%\"><b>Availability Type</b></td>" +
        "<td class=\"PsgLabel\" width=\"25%\"><b>Effective Date</b></td>" +
        "<td class=\"PsgLabel\" width=\"25%\"><b>Announcement Code Name</b></td>" +
        "<td class=\"PsgLabel\" width=\"25%\"><b>Country List</b></td></tr>");
      for(int ii = 0; ii < vctSummary.size(); ii++ ) {
        Vector vct1 = (Vector)vctSummary.elementAt(ii);
        for(int ij = 0; ij  < vct1.size(); ij++) {
          displayAvailRows((EntityItem)vct1.elementAt(ij));
        }
      }
      println("</table>");
    }
      return bOK;

   } // checkA0003 //per spec changes



   public boolean checkH0003(EntityItem _ei) {

      EntityGroup eg = _ei.getEntityGroup();

      String strEntityType = _ei.getEntityType();
      String strCat = getFlagCode(_ei,"OOFCAT");
      String strSubCat = getFlagCode(_ei,"OOFSUBCAT");

      boolean bOK = true;
      boolean bFromFound = false;
      boolean bToFound = false;

      // Only check if the following is true
      if (strEntityType.equals("ORDEROF") && strCat.equals("100") && strSubCat.equals("501")) {

         println("<br/>H0003 - Checking " + eg.getLongDescription() + ":" + _ei.toString());

         String strOOFFromFeatureCode = getAttributeValue(_ei,"FROMFEATURECODE");
         String strOOFFeatureCode = getAttributeValue(_ei,"FEATURECODE");

         for(int ii = 0; ii < _ei.getDownLinkCount();ii++) {
            EntityItem eiRelator = (EntityItem)_ei.getDownLink(ii);
            if (eiRelator.getEntityType().equals("OOFFUP")) {
               String strRelType = getFlagCode(eiRelator,"RELTYPE");
               EntityItem eiFup = (EntityItem)eiRelator.getDownLink(0);
               String strFUPFeatureCode = getAttributeValue(eiFup,"FEATURECODE");
               if (strRelType.equals("6114")) {
                  bFromFound = true;
                  if (!strFUPFeatureCode.equals(strOOFFromFeatureCode)) {
                     println("<br/>H0003 - Failure.  Non Matching feature codes in the *From* Case.  " + strOOFFromFeatureCode + " does not match " + strFUPFeatureCode + " for " + eiFup.toString());
                     bOK = false;
                  }
               } else if (strRelType.equals("6115")) {
                  bToFound = true;
                  if (!strFUPFeatureCode.equals(strOOFFeatureCode)) {
                     println("<br/>H0003 - Failure.  Non Matching feature codes in the *To* Case.  " + strOOFFeatureCode + " does not match " + strFUPFeatureCode + " for " + eiFup.toString());
                     bOK = false;
                  }
               }
            }
         }

         if (!bFromFound) {
            println("<br/>H0003 - Failure.  Did not find any *FROM* Feature Codes to Test.");
            bOK = false;
         }
         if (!bToFound) {
            println("<br/>H0003 - Failure.  Did not find any *TO* Feature Codes to Test.");
            bOK = false;
         }

      }

      return bOK;

  }


   public boolean checkH0006(EntityItem _ei) {

      EntityGroup eg = _ei.getEntityGroup();

      String strEntityType = _ei.getEntityType();
      String strCat = getFlagCode(_ei,"OOFCAT");
      String strSubCat = getFlagCode(_ei,"OOFSUBCAT");

      boolean bOK = true;
      boolean bFromFound = false;
      boolean bToFound = false;

      // Only check if the following is true
      if (strEntityType.equals("ORDEROF") && strCat.equals("100") && (strSubCat.equals("503") ||strSubCat.equals("503"))) {

         println("<br/>H0006 - Checking " + eg.getLongDescription() + ":" + _ei.toString());

         String strOOFFromModel = getAttributeValue(_ei,"FROMMODEL");
         String strOOFModel = getAttributeValue(_ei,"MODEL");

         for(int ii = 0; ii < _ei.getDownLinkCount();ii++) {
            EntityItem eiRelator = (EntityItem)_ei.getDownLink(ii);
            if (eiRelator.getEntityType().equals("OOFFUP")) {
               String strRelType = getFlagCode(eiRelator,"RELTYPE");
               EntityItem eiFup = (EntityItem)eiRelator.getDownLink(0);
               String strFUPModel = getAttributeValue(eiFup,"MODEL");
               if (strRelType.equals("6114")) {
                  bFromFound = true;
                  if (!strFUPModel.equals(strOOFFromModel)) {
                     println("<br/>H0006 - Failure.  Non Matching Models in the *From* Case.  " + strOOFFromModel + " does not match " + strFUPModel + " for " + eiFup.toString());
                     bOK = false;
                  }
               } else if (strRelType.equals("6115")) {
                  bToFound = true;
                  if (!strFUPModel.equals(strOOFModel)) {
                     println("<br/>H0006 - Failure.  Non Matching Models in the *To* Case.  " + strOOFModel + " does not match " + strFUPModel+ " for " + eiFup.toString());
                     bOK = false;
                  }
               }
            }
         }

         if (!bFromFound) {
            println("<br/>H0006 - Failure.  Did not find any *FROM* Models to Test.");
            bOK = false;
         }
         if (!bToFound) {
            println("<br/>H0006 - Failure.  Did not find any *TO* Models to Test.");
            bOK = false;
         }

      }

      return bOK;

  }


   public boolean checkV0001(EntityItem _ei) {

      if (isFinal(_ei, getNextStatusCode(_ei))) {

         EntityGroup eg = _ei.getEntityGroup();
         println("<br><br><b>V0001 - Checking " + eg.getLongDescription() + " : " + _ei.toString());
         String strFeatureCode = getAttributeValue(_ei,"FEATURECODE");
         if (strFeatureCode.indexOf("#") != -1) {
            println("<br><br><b>V0001 - Failure - The Feature Code has a \"#\" present: " + strFeatureCode);
            return false;
         }
      }
      return true;
   }


   /**
   *   Do we need a geo tree
   */
   public boolean checkV0002(EntityItem _ei) {

      // Only look at final things and Order Offerings
      // We need a geo tree here

      boolean bOK = true;
      EntityGroup eg = _ei.getEntityGroup();

      // O.K.  we have a valid Geo Tree.  lets get busy w/ the check
      println("<br><br>V0002 - Checking " + eg.getLongDescription() + ":" + _ei.toString());


      if (isFinal(_ei, getNextStatusCode(_ei)) && _ei.getEntityType().equals("ORDEROF")){

         String strFCode = getAttributeValue(_ei,"FEATURECODE");
         boolean bHardware = (getFlagCode(_ei,"OOFCAT").equals("100"));
         boolean bSoftware = (getFlagCode(_ei,"OOFCAT").equals("101"));

         //
         // Lets get the avail record...
         //
         GeneralAreaList gal = null;
         try {
            gal = new GeneralAreaList(m_db, m_prof);
         }  catch (Exception x) {
            x.printStackTrace();
         }

         if (gal == null) {
            println("<br><br>V0002 - Failure - cannot pull the Geo Tree Object in from the Database:");
            return false;
         }

         for (int ii = 0; ii < _ei.getDownLinkCount();ii++) {

            EntityItem eiRelator = (EntityItem)_ei.getDownLink(ii);
            EntityItem eiAvail = (EntityItem)eiRelator.getDownLink(0);

            // We found one ...

            if (eiAvail.getEntityType().equals("AVAIL")) {
                  boolean  bUS = gal.isRfaGeoUS(eiAvail);
                  boolean  bEMEA = gal.isRfaGeoEMEA(eiAvail);
                  boolean  bLA = gal.isRfaGeoLA(eiAvail);
                  boolean  bCAN = gal.isRfaGeoCAN(eiAvail);
                  boolean  bAP = gal.isRfaGeoAP(eiAvail);

                  if (bHardware || (bSoftware && bUS)) {
                     if(!is4Digit(strFCode.trim())){
                        println("<br><br>Feature Code is not a 4 digit integer</b>");
                        println("<br><BR><table width=\"100%\">\n");
                        println("<tr><td class=\"PsgLabel\" width=\"50%\" >Attribute Description</td><td class=\"PsgLabel\" width=\"50%\">Attribute Value</td></tr>");
                        println("<tr><td class=\"PsgText\" width=\"50%\" >Feature Code</td><td class=\"PsgText\" width=\"50%\">" + (strFCode.equals("") ? DEF_NOT_POPULATED_HTML : strFCode) + "</td></tr>");
                println("</table>");
                        bOK = false;
                     }
                  } else if(bSoftware && (bAP || bCAN)) {
                     if(!is4AlphaInteger(strFCode.trim())){
                        println("<br><br>Feature Code is not a 4 character alpha-numeric</b>");
                        println("<br><BR><table width=\"100%\">\n");
                        println("<tr><td class=\"PsgLabel\" width=\"50%\" >Attribute Description</td><td class=\"PsgLabel\" width=\"50%\">Attribute Value</td></tr>");
                        println("<tr><td class=\"PsgText\" width=\"50%\" >Feature Code</td><td class=\"PsgText\" width=\"50%\">" + (strFCode.equals("") ? DEF_NOT_POPULATED_HTML : strFCode) + "</td></tr>");
                 println("</table>");
                        bOK = false;
                  } else if(bSoftware && (bLA || bEMEA)){
                     if(!is46AlphaInteger(strFCode.trim())){
                        println("<br><b>Feature Code is not a 4 or 6 character alpha-numeric</b>");
                        println("<br><BR><table width=\"100%\">\n");
                        println("<tr><td class=\"PsgLabel\" width=\"50%\" >Attribute Description</td><td class=\"PsgLabel\" width=\"50%\">Attribute Value</td></tr>");
                        println("<tr><td class=\"PsgText\" width=\"50%\" >Feature Code</td><td class=\"PsgText\" width=\"50%\">" + (strFCode.equals("") ? DEF_NOT_POPULATED_HTML : strFCode) + "</td></tr>");
                println("</table>");
                        bOK = false;
                     }
                  }
               }
            }
         }
      }

      if (!bOK) {
         println("<br><br>V0002 - Failed");
      }

      return bOK;

   }

   public boolean isDigit(String _sAttValue) {
	  if (_sAttValue == null || _sAttValue.length() <= 0) {
		  return false;
	  }
      for (int i =0; i < _sAttValue.length(); i++) {
         char c = _sAttValue.charAt(i);
         if (!Character.isDigit(c)) {
            return false;
         }
      }
      return true;
   }

   public boolean isAlphaDigit(String _sAttValue) {
      for (int i =0; i < _sAttValue.length(); i++) {
         char c = _sAttValue.charAt(i);
         if (!Character.isLetterOrDigit(c)) {
            return false;
         }
      }
      return true;
   }

   public boolean containDigit(String _sAttValue) {
      for (int i =0; i < _sAttValue.length(); i++) {
         char c = _sAttValue.charAt(i);
         if (Character.isDigit(c)) {
            return true;
         }
      }
      return false;
   }

    public boolean checkFCFormat(EntityItem _ei) {
      String strFCode = getAttributeValue(_ei,"FEATURECODE");
      boolean bOK = true;
      if (strFCode != null && strFCode.length() > 0) {
         if (strFCode.length() == 4) {
            //all four chars must be numeric
            if (!isAlphaDigit(strFCode)) {
               println("<br><b>Feature Code is not 4 characters alpha-numeric: " + strFCode + "</b>");
               bOK = false;
            }
         } else if (strFCode.length() == 6) {
            if (!containDigit(strFCode)) {
               println("<br><b>Feature Code doesn't contain at least one digit: " + strFCode + "</b>");
               bOK = false;
            }
            //chars can be alpha-numeric
            if (!isAlphaDigit(strFCode)) {
               println("<br><b>Feature Code is not 6 characters alpha-numeric: " + strFCode + "</b>");
               bOK = false;
            }
         } else {
            println("<br><b>Feature Code is invalid length: " + strFCode + "</b>");
            bOK = false;
         }
      }
      if (!bOK) {
         println("<br><br>Check FEATURECODE format - Failed");
      }

      return bOK;
   }

   public String formatMFlag(String _s) {
      String strResult = null;
      if(_s.charAt(0) == '*') {
         strResult = _s.substring(_s.indexOf('*') + 1);
         strResult = strResult.trim();
         strResult = strResult.replace('*', ',');

      }
      return strResult;
   }

   private void displayAvailRows(EntityItem _ei){

      if(_ei != null) {
         EANAttribute att = _ei.getAttribute("AVAILTYPE");
         String strK1 = (att == null ? DEF_NOT_POPULATED_HTML: att.toString());
         att = _ei.getAttribute("EFFECTIVEDATE");
         String strK2 = (att == null ? DEF_NOT_POPULATED_HTML : att.toString());
         att = _ei.getAttribute("ANNCODENAME");
         String strK3 = (att == null ? DEF_NOT_POPULATED_HTML : att.toString());
         att = _ei.getAttribute("COUNTRYLIST");
         String strK4 = (att == null ? DEF_NOT_POPULATED_HTML: att.toString());
         println(
            "<tr><td class=\"PsgText\" width=\"25%\">" + strK1 + "</td>" +
            "<td class=\"PsgText\" width=\"25%\">" + strK2 + "</td>" +
            "<td class=\"PsgText\" width=\"25%\">" + strK3 + "</td>" +
            "<td class=\"PsgText\" width=\"25%\">" + strK4 + "</td></tr>");
      }
   }

   public boolean is4Digit(String _sAttValue) {
      if(_sAttValue.length() == 4) {
         for (int i =0; i < _sAttValue.length(); i++) {
            char c = _sAttValue.charAt(i);
            if (!Character.isDigit(c)) {
               return false;
            }
         }
      } else {
         return false;
      }
      return true;
   }

   public boolean is4AlphaInteger(String _sAttValue) {
      if(_sAttValue.length() == 4) {
         for (int i = 0; i < _sAttValue.length(); i++) {
            char c = _sAttValue.charAt(i);
            if (!(Character.isLetterOrDigit(c) || Character.isLetter(c))) {
               return false;
            }
         }
      } else {
         return false;
      }
      return true;
   }

   public boolean is46AlphaInteger(String _sAttValue) {
      if(_sAttValue.length() == 4 || _sAttValue.length() == 6) {
         for (int i = 0; i < _sAttValue.length(); i++) {
            char c = _sAttValue.charAt(i);
            if (!(Character.isLetterOrDigit(c) || Character.isLetter(c))) {
               return false;
            }
         }
      } else {
         return false;
      }
   return true;
   }

   public Profile refreshProfValOnEffOn(Profile _prof) throws MiddlewareException {
      DatePackage dpNow = m_db.getDates();
      String strTimeStampNow = dpNow.getNow();
      _prof.setValOn(strTimeStampNow);
      _prof.setEffOn(strTimeStampNow);
      _prof.setNow(strTimeStampNow);
      _prof.setEndOfDay(dpNow.getEndOfDay());
      return _prof;
   }

   public StringBuffer getCategories(Database _db, Profile _prof, EntityItem [] _eiRoot) {
      StringBuffer sbReturn = new StringBuffer();
      EntityList elist=null;
      boolean bExtractError=false;

      String strReport = m_abri.getABRCode();
      try {
        ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, ABRServerProperties.getSubscrVEName(strReport));
        elist = _db.getEntityList(_prof, eaItem, _eiRoot);
      } catch (MiddlewareException me)  {
        System.out.println("PokBaseABR:getDGCategories:Problem during Extract"+me.getMessage());
        me.printStackTrace();
        bExtractError=true;
      } catch (SQLException se) {
        System.out.println("PokBaseABR:getDGCategories:Problem during Extract"+se.getMessage());
        se.printStackTrace();
        bExtractError=true;
      }

      if (bExtractError) return sbReturn;

      // get all the categories info
      int i = 1;
      boolean bCat = true;
      do {
         String strCat = ABRServerProperties.getCategory(strReport,"CAT" + i);
         if (strCat != null) {
            int index = strCat.indexOf(".");
            if (strCat.equals("TASKSTATUS")) {
               sbReturn.append("CAT" + i + " = " + (getReturnCode() == PASS ? "Passed" : "Failed") + "\n");
//             println("</br>TASKSTATUS = " + (getReturnCode() == PASS ? "Passed" : "Failed"));
            } else if (index >=0) {
               String strEntityType = strCat.substring(0,index);
               String strAttrCode = strCat.substring(index+1);
               EntityGroup eg = null;
               if (strEntityType.equals(_eiRoot[0].getEntityType())) {
                  eg = elist.getParentEntityGroup();
               } else {
                  eg = elist.getEntityGroup(strEntityType);
               }

               if (eg != null) {
                  if (eg.getEntityItemCount() > 0) {
                     EntityItem ei = eg.getEntityItem(0);
                     EANAttribute att = ei.getAttribute(strAttrCode);
                     if (att != null) {
                        sbReturn.append("CAT" + i + " = " + att.toString() + "\n");
                     }
                  }
               }
            }
         } else {
            bCat = false;
         }
         i++;
      } while (bCat);

      return sbReturn;
   }

  /**
   *  Description of the Method
   *
   *@param  _vEntities    Description of the Parameter
   *@param  _strAttrList  Description of the Parameter
   *@return               Description of the Return Value
   */
  public Vector sortEntities(Vector _vEntities, String[] _strAttrList) {
    //Vector vReturn = new Vector();
    SortUtil mysort = new SortUtil();
    EntityItem[] eArray = getEntityArray(_vEntities);
    mysort.sort(eArray, _strAttrList);
    return getEntityVector(eArray);
  }


  /**
   *  Gets the entityArray attribute of the object
   *
   *@param  _vEntities  Description of the Parameter
   *@return             The entityArray value
   */
  public EntityItem[] getEntityArray(Vector _vEntities) {
    EntityItem[] eiReturn = (EntityItem[]) java.lang.reflect.Array.newInstance(EntityItem.class, _vEntities.size());
    for (int k = 0; k < _vEntities.size(); k++) {
      eiReturn[k] = (EntityItem) _vEntities.elementAt(k);
    }
    return eiReturn;
  }


  /**
   *  Gets the entityVector attribute of the  object
   *
   *@param  _ei  Description of the Parameter
   *@return      The entityVector value
   */
  public Vector getEntityVector(EntityItem[] _ei) {
    Vector vReturn = new Vector();
    for (int k = 0; k < _ei.length; k++) {
      vReturn.add(_ei[k]);
    }
    return vReturn;
  }

  public EANList getChildren(EntityList _elist, String _strParentType, int _iParentId, String _strChildEtype, String _strParentChildType) {
    EANList eiList = new EANList();

    // get the navigation group for the relator
    EntityGroup entGroup = null;
    if (_strParentChildType.equals(getEntityType())) {      //get the parent entity group for root entity
      entGroup = _elist.getParentEntityGroup();
    } else {
       entGroup = _elist.getEntityGroup(_strParentChildType);
    }

    if (entGroup == null) {
      return eiList;
    }

    if (entGroup.getEntityItemCount() == 0) {
      return eiList;
    }

    // find all entities of the specified type
    for (int i = 0; i < entGroup.getEntityItemCount(); i++) {
      EntityItem ei = entGroup.getEntityItem(i);

      // entities are children and parents of the relator
      // check if parent matches this item
      for (int t = 0; t < ei.getUpLinkCount(); t++) {
        if (ei.getUpLink(t).getEntityType().equals(_strParentType) && ei.getUpLink(t).getEntityID() == _iParentId) {
          for (int at = 0; at < ei.getDownLinkCount(); at++) {
            // match on destination.. parent here
            if (ei.getDownLink(at).getEntityType().equals(_strChildEtype)) {
              eiList.put((EntityItem)ei.getDownLink(at));
            }
          }
        }
      }
    }

    return eiList;
  }

  public EANList getParent(EntityList _elist, String _strChildType, int _iChildId, String _strParentEtype, String _strParentChildType) {
    EANList eiList = new EANList();

    // get the navigation group for the relator
    EntityGroup entGroup = null;
    if (_strParentChildType.equals(getEntityType())) {      //get the parent entity group for root entity
      entGroup = _elist.getParentEntityGroup();
    } else {
       entGroup = _elist.getEntityGroup(_strParentChildType);
    }

    if (entGroup == null) {
      return eiList;
    }

    if (entGroup.getEntityItemCount() == 0) {
      return eiList;
    }

    // find all entities of the specified type
    for (int i = 0; i < entGroup.getEntityItemCount(); i++) {
      EntityItem ei = entGroup.getEntityItem(i);

      // entities are children and parents of the relator
      // check if parent matches this item
      for (int t = 0; t < ei.getUpLinkCount(); t++) {
        if (ei.getDownLink(t).getEntityType().equals(_strChildType) && ei.getDownLink(t).getEntityID() == _iChildId) {
          for (int at = 0; at < ei.getUpLinkCount(); at++) {
            // match on destination.. parent here
            if (ei.getUpLink(at).getEntityType().equals(_strParentEtype)) {
              eiList.put((EntityItem)ei.getUpLink(at));
            }
          }
        }
      }
    }

    return eiList;
  }


  public void setFileBreakTag(String _strDescription,String _strFilename)   {
    println(".* <!--STARTFILEBREAKFORMAIL:"+_strFilename+": FOR :"+_strDescription+"-->");

  }

  public void dereference(){
	  super.dereference();

	  m_slg = null;
	  m_pdhg = null;
	  m_pdhi = null;
	  m_pw = null;
	  m_db = null;
	  m_prof = null;
	  m_nlsi = null;
	  m_abri = null;
	  m_strABRSessionID = null;
	  if (m_htUpdates!= null){
		  m_htUpdates.clear();
		  m_htUpdates = null;
	  }
	  if (m_elist!= null){
		  m_elist.dereference();
		  m_elist = null;
	  }
	  
	  m_strDGSubmit = null;
	  m_strEpoch = null;
	  m_strEndOfDay = null;
	  m_strForever = null;
	  m_strToday = null;
	  m_strNow = null;
      m_cbOn = null;
      if (m_hsetSkipType != null){
    	  m_hsetSkipType.clear();
    	  m_hsetSkipType = null;
      }
      if (m_hDisplay != null){
    	  m_hDisplay.clear();
    	  m_hDisplay = null;
      }
  }
}

