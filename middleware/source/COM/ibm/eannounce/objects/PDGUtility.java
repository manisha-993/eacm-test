// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: PDGUtility.java,v $
// Revision 1.249  2011/02/17 16:15:32  wendy
// prevent null ptr if search action doesnt exist
//
// Revision 1.248  2010/10/05 20:34:04  wendy
// Check for domain from template before using default
//
// Revision 1.247  2010/07/12 21:02:01  wendy
// BH SR87, SR655 - extended combounique rule
//
// Revision 1.246  2009/08/05 17:27:54  wendy
// dont reuse search actions, they are dereferenced
//
// Revision 1.245  2009/06/12 17:24:35  wendy
// Don't dereference EntityList when EntityItems are returned - causes NPE
//
// Revision 1.244  2008/06/17 20:03:57  wendy
// MN35609432 finding wrong SWPRODSTRUCT
//
// Revision 1.243  2008/06/16 19:30:29  wendy
// MN35609290 fix revealed memory leaks, deref() needed
//
// Revision 1.242  2008/05/23 20:16:36  yang
// minor log
//
// Revision 1.241  2008/05/23 19:51:01  yang
// adding extra logging to dynaSearch()
//
// Revision 1.240  2008/03/26 19:47:20  wendy
// Clean up RSA warnings and fix column output for viewmissing pdg action
//
// Revision 1.239  2008/02/11 20:46:20  wendy
// Cleanup RSA warnings
//
// Revision 1.238  2008/01/29 18:47:27  wendy
// convert special chars to prevent error in xml transform
//
// Revision 1.237  2008/01/11 17:13:56  wendy
// Removed hardcoded check for WGMODEL, meta is now cleaned up
//
// Revision 1.236  2008/01/04 16:56:08  wendy
// MN33416775 handling of salesstatus chgs
//
// Revision 1.235  2007/12/05 17:47:53  bala
// add debug for DynaSearch
//
// Revision 1.234  2007/08/27 18:36:27  wendy
// Make sure PDHDOMAIN is set when RST is used for create
//
// Revision 1.233  2007/08/22 19:34:20  wendy
// MN32841099 prevent using obsolete WGMODEL
//
// Revision 1.232  2007/08/01 14:38:10  wendy
// Prevent null ptr on rs.close()
//
// Revision 1.231  2007/02/22 21:10:56  joan
// fixes
//
// Revision 1.230  2007/02/22 18:57:13  joan
// fixes
//
// Revision 1.229  2007/02/08 18:35:17  wendy
// Use db server timestamp instead of profile.getNow()
//
// Revision 1.228  2006/10/25 20:55:04  joan
// fixes
//
// Revision 1.227  2006/10/25 18:15:18  joan
// add debug
//
// Revision 1.226  2006/10/05 17:20:27  joan
// work on performance
//
// Revision 1.225  2006/09/27 04:16:21  joan
// add method
//
// Revision 1.224  2006/09/26 00:46:33  joan
// working on deactivate catlgpub with multiple audience values
//
// Revision 1.223  2006/09/20 21:27:44  joan
// changes
//
// Revision 1.222  2006/09/19 21:07:51  joan
// changes
//
// Revision 1.221  2006/09/19 17:55:37  joan
// changes
//
// Revision 1.220  2006/09/11 17:14:25  joan
// change default
//
// Revision 1.219  2006/08/30 00:10:03  joan
// fixes
//
// Revision 1.218  2006/08/29 23:15:47  joan
// changes
//
// Revision 1.217  2006/08/29 22:06:00  joan
// changes
//
// Revision 1.216  2006/08/29 15:54:05  joan
// changes
//
// Revision 1.215  2006/08/25 15:42:56  joan
// changes
//
// Revision 1.214  2006/08/24 16:11:00  joan
// changes
//
// Revision 1.213  2006/08/23 20:24:01  joan
// changes
//
// Revision 1.212  2006/08/22 22:37:13  joan
// fixes
//
// Revision 1.211  2006/08/21 19:33:28  joan
// fixes
//
// Revision 1.210  2006/08/18 23:06:48  joan
// changes
//
// Revision 1.209  2006/08/18 22:44:49  joan
// changes
//
// Revision 1.208  2006/07/15 15:14:38  joan
// check for expired flags
//
// Revision 1.207  2006/06/27 18:56:48  joan
// fixes
//
// Revision 1.206  2006/06/27 17:54:15  joan
// add method
//
// Revision 1.205  2006/06/27 14:37:12  joan
// changes
//
// Revision 1.204  2006/06/26 20:07:00  joan
// changes
//
// Revision 1.203  2006/06/26 19:52:42  joan
// add mehtod
//
// Revision 1.202  2006/06/22 21:10:59  joan
// changes
//
// Revision 1.201  2006/05/22 15:32:08  joan
// add method
//
// Revision 1.200  2006/05/20 23:59:54  joan
// add method
//
// Revision 1.199  2006/05/15 16:10:38  joan
// changes
//
// Revision 1.198  2006/04/21 16:49:38  joan
// changes
//
// Revision 1.197  2006/04/12 22:23:03  joan
// changes
//
// Revision 1.196  2006/04/07 16:56:30  joan
// add noremote
//
// Revision 1.195  2006/04/07 00:16:53  joan
// fixes
//
// Revision 1.194  2006/04/07 00:07:28  joan
// fixes
//
// Revision 1.193  2006/04/06 23:57:50  joan
// add method
//
// Revision 1.192  2006/03/17 16:53:56  joan
// fixes
//
// Revision 1.191  2006/03/10 18:27:40  joan
// fix
//
// Revision 1.190  2006/03/03 00:17:44  joan
// fixes
//
// Revision 1.189  2006/02/28 16:43:59  joan
// fixes
//
// Revision 1.188  2006/02/28 00:47:20  joan
// work on pdg
//
// Revision 1.187  2006/02/22 19:36:17  joan
// fixes
//
// Revision 1.186  2006/02/20 22:16:40  joan
// changing debug statements
//
// Revision 1.185  2006/02/20 21:50:04  joan
// clean up System.out.println
//
// Revision 1.184  2006/02/20 21:39:48  joan
// clean up System.out.println
//
// Revision 1.183  2006/02/13 18:45:02  joan
// fixes
//
// Revision 1.182  2006/02/13 18:36:11  joan
// fixes
//
// Revision 1.181  2006/02/11 01:22:03  joan
// fixes
//
// Revision 1.180  2006/02/09 16:30:26  joan
// fixes
//
// Revision 1.179  2006/02/06 21:28:28  gregg
// CR4823: setting m_strComboUniqueGrouping on attributes for later rules processing.
//
// Revision 1.178  2006/01/26 21:55:20  joan
// fixes
//
// Revision 1.177  2005/12/19 23:29:43  joan
// fixes
//
// Revision 1.176  2005/12/19 20:27:54  joan
// fixes
//
// Revision 1.175  2005/12/09 19:11:14  joan
// fixes
//
// Revision 1.174  2005/12/08 00:37:17  joan
// FIXES
//
// Revision 1.173  2005/12/07 21:59:14  joan
// fixes
//
// Revision 1.172  2005/12/07 19:04:57  joan
// insert the root
//
// Revision 1.171  2005/12/07 00:36:34  joan
// fixes
//
// Revision 1.170  2005/12/07 00:24:20  joan
// add method
//
// Revision 1.169  2005/11/02 17:15:59  joan
// fixes
//
// Revision 1.168  2005/11/02 00:44:38  joan
// fixes
//
// Revision 1.167  2005/11/01 23:23:49  joan
// fixes
//
// Revision 1.166  2005/11/01 16:59:11  joan
// fixes
//
// Revision 1.165  2005/10/31 22:38:05  joan
// fixes
//
// Revision 1.164  2005/10/31 22:04:19  joan
// fixes
//
// Revision 1.163  2005/10/26 17:40:18  joan
// fixes
//
// Revision 1.162  2005/10/19 22:19:50  joan
// fixes
//
// Revision 1.161  2005/10/18 22:09:29  joan
// fixes
//
// Revision 1.160  2005/10/17 16:37:21  joan
// fixes
//
// Revision 1.159  2005/10/07 22:55:28  joan
// add comments
//
// Revision 1.158  2005/09/07 22:07:14  joan
// fixes
//
// Revision 1.157  2005/08/30 20:39:06  joan
// fixes
//
// Revision 1.156  2005/08/30 20:37:54  joan
// fixes
//
// Revision 1.155  2005/08/30 17:39:14  dave
// new cat comments
//
// Revision 1.153  2005/07/19 21:16:19  joan
// fixes
//
// Revision 1.154  2005/07/25 18:18:29  joan
// fixes
//
// Revision 1.153  2005/07/19 21:16:19  joan
// fixes
//
// Revision 1.152  2005/06/15 20:58:49  joan
// fixes
//
// Revision 1.151  2005/05/24 22:31:52  joan
// fixes
//
// Revision 1.150  2005/04/28 21:15:36  joan
// fixes
//
// Revision 1.149  2005/04/26 19:03:29  joan
// fixes
//
// Revision 1.148  2005/04/26 15:49:56  joan
// fixes
//
// Revision 1.147  2005/04/11 19:43:07  joan
// fixes
//
// Revision 1.146  2005/03/28 22:02:14  joan
// input prof NLSID for sp
//
// Revision 1.145  2005/03/25 21:10:49  dave
// more fixes
//
// Revision 1.144  2005/03/11 22:42:54  dave
// removing some auto genned stuff
//
// Revision 1.143  2005/03/11 20:41:16  roger
// Foreign ABRs
//
// Revision 1.142  2005/03/10 20:42:47  dave
// JTest daily ritual
//
// Revision 1.141  2005/03/10 00:25:31  dave
// Jtest readObject removal
//
// Revision 1.140  2005/03/10 00:17:47  dave
// more Jtest work
//
// Revision 1.139  2005/03/07 19:47:31  joan
// fixes
//
// Revision 1.138  2005/03/02 21:33:28  joan
// fixes
//
// Revision 1.137  2005/03/02 20:46:22  joan
// fixes
//
// Revision 1.136  2005/03/01 21:02:45  joan
// fixes
//
// Revision 1.135  2005/02/28 22:39:15  joan
// fixes
//
// Revision 1.134  2005/02/03 18:51:54  joan
// fixes
//
// Revision 1.133  2005/01/26 19:18:16  joan
// fixes
//
// Revision 1.132  2005/01/20 21:04:10  joan
// working on msg
//
// Revision 1.131  2005/01/18 21:46:51  dave
// more parm debug cleanup
//
// Revision 1.130  2005/01/15 00:40:56  joan
// fixes
//
// Revision 1.129  2005/01/13 02:20:11  joan
// work on copy relator
//
// Revision 1.128  2005/01/12 21:34:27  joan
// fixes
//
// Revision 1.127  2005/01/12 21:16:53  joan
// fixes
//
// Revision 1.126  2004/12/03 22:49:51  joan
// fix on search
//
// Revision 1.125  2004/11/23 00:39:08  joan
// add method
//
// Revision 1.124  2004/11/22 22:48:10  joan
// fixes
//
// Revision 1.123  2004/11/22 18:37:39  joan
// fixes
//
// Revision 1.122  2004/11/20 00:08:49  joan
// fixes
//
// Revision 1.121  2004/11/19 22:49:10  joan
// fixes
//
// Revision 1.120  2004/11/18 00:40:31  joan
// fixes
//
// Revision 1.119  2004/11/17 22:11:24  joan
// fixes
//
// Revision 1.118  2004/11/17 19:52:25  joan
// fix getRowIndex
//
// Revision 1.117  2004/11/15 16:51:45  joan
// fix compile
//
// Revision 1.116  2004/11/15 16:36:40  joan
// work on CR
//
// Revision 1.115  2004/11/01 22:32:24  joan
// fixes
//
// Revision 1.114  2004/10/29 16:24:06  joan
// fixes
//
// Revision 1.113  2004/10/22 17:41:31  joan
// adjust code
//
// Revision 1.112  2004/10/21 20:19:01  joan
// add catch MiddlewareRequest
//
// Revision 1.111  2004/10/13 16:37:33  joan
// fixes
//
// Revision 1.110  2004/10/11 22:10:30  joan
// fixes
//
// Revision 1.109  2004/10/07 22:25:51  joan
// fixes
//
// Revision 1.108  2004/10/07 21:55:06  joan
// fixes
//
// Revision 1.107  2004/10/07 20:42:38  joan
// fixes
//
// Revision 1.106  2004/10/05 17:57:44  joan
// fixes
//
// Revision 1.105  2004/10/05 16:05:02  joan
// fixes
//
// Revision 1.104  2004/09/29 17:40:39  joan
// fixes
//
// Revision 1.103  2004/09/28 16:22:42  joan
// fixes
//
// Revision 1.102  2004/09/21 19:54:07  joan
// fixes
//
// Revision 1.101  2004/09/20 19:30:48  joan
// fixes
//
// Revision 1.100  2004/09/14 22:55:09  joan
// fixes
//
// Revision 1.99  2004/09/10 19:32:31  bala
// fix for getParentEntityIds
//
// Revision 1.98  2004/09/10 19:23:31  bala
// add getParentEntityIds
//
// Revision 1.97  2004/09/02 22:16:09  joan
// fixes
//
// Revision 1.96  2004/08/27 16:56:14  joan
// fixes
//
// Revision 1.95  2004/08/25 19:44:50  joan
// add new pdgs
//
// Revision 1.94  2004/08/20 17:24:59  joan
// add new pdg
//
// Revision 1.93  2004/08/12 21:13:55  joan
// fix bug
//
// Revision 1.92  2004/08/12 18:03:03  joan
// work on PDG
//
// Revision 1.91  2004/08/05 20:36:16  joan
// work on special bid PDG
//
// Revision 1.90  2004/08/03 20:30:20  gregg
// ok - comboUniqueOptional is text/text attribute (i.e. we only care about Text Attribute objects fer now)
//
// Revision 1.89  2004/08/03 18:24:57  gregg
// isComboUniqueOptionalRequiredAttribute
//
// Revision 1.88  2004/08/02 21:44:07  gregg
// phicks
//
// Revision 1.87  2004/08/02 21:21:45  gregg
// combo unique optional
//
// Revision 1.86  2004/05/27 20:05:08  joan
// add collectInfo method
//
// Revision 1.85  2004/03/12 23:19:11  joan
// changes from 1.2
//
// Revision 1.84  2004/02/19 18:03:45  joan
// fix bug
//
// Revision 1.83  2004/01/09 20:32:43  joan
// fix fb
//
// Revision 1.82  2003/12/11 20:13:42  joan
// work on link method
//
// Revision 1.81  2003/12/10 21:17:10  joan
// fix compile
//
// Revision 1.80  2003/12/10 21:01:03  joan
// adjust for ExcludeCopy
//
// Revision 1.79  2003/12/08 22:41:43  joan
// make isDigit public
//
// Revision 1.78  2003/12/04 19:03:19  joan
// fix findEntityItem
//
// Revision 1.77  2003/12/01 19:26:53  joan
// update xml attribute
//
// Revision 1.76  2003/11/18 18:25:58  joan
// compile error
//
// Revision 1.75  2003/11/18 18:12:37  joan
// add new method
//
// Revision 1.74  2003/10/29 22:01:39  joan
// comment out debug lines
//
// Revision 1.73  2003/10/29 18:46:25  joan
// adjust update
//
// Revision 1.72  2003/10/24 23:08:24  joan
// fb fixes
//
// Revision 1.71  2003/10/16 20:39:13  joan
// try to make PDG run faster
//
// Revision 1.70  2003/10/14 19:57:18  joan
// work on fb
//
// Revision 1.69  2003/10/07 18:24:49  joan
// fix bug
//
// Revision 1.68  2003/10/06 23:34:22  joan
// work on LS ABR
//
// Revision 1.67  2003/09/22 19:42:51  joan
// fb52329
//
// Revision 1.66  2003/09/22 15:09:19  joan
// work on upgrade paths
//
// Revision 1.65  2003/09/16 16:42:59  joan
// add blob update
//
// Revision 1.64  2003/09/04 21:55:19  joan
// fix fb
//
// Revision 1.63  2003/08/25 20:22:32  dave
// Some cleanup on streamlining searchactionitem
//
// Revision 1.62  2003/08/25 17:15:50  joan
// move changes from v1.1.1
//
// Revision 1.61  2003/07/28 21:20:06  joan
// add ALWRECSOLABR001
//
// Revision 1.60  2003/07/16 21:29:39  joan
// working on PCDPDG
//
// Revision 1.59  2003/06/30 23:17:53  joan
// move changes from v111
//
// Revision 1.58  2003/06/20 18:27:14  dave
// syntax
//
// Revision 1.57  2003/06/20 18:14:25  dave
// new pdg support changes
//
// Revision 1.56  2003/05/29 00:03:38  joan
// fix feedback
//
// Revision 1.55  2003/05/13 22:21:44  joan
// fix report
//
// Revision 1.54  2003/05/13 21:11:53  joan
// work on report
//
// Revision 1.53  2003/05/13 16:11:18  dave
// more trace statements
//
// Revision 1.52  2003/05/11 02:30:57  dave
// removing the parent set on the search action item
// in dynasearch (is not setting correctly.. do we need?)
//
// Revision 1.51  2003/05/11 01:50:19  dave
// more UI turning off
//
// Revision 1.50  2003/05/11 01:46:03  dave
// turning off more unneeded UI in back end code
//
// Revision 1.49  2003/05/11 01:28:59  dave
// syntax
//
// Revision 1.48  2003/05/11 01:22:52  dave
// more getnow and sp cleanup
//
// Revision 1.47  2003/05/11 00:52:17  dave
// syntax
//
// Revision 1.46  2003/05/11 00:35:56  dave
// looking at getnow sequencing
//
// Revision 1.45  2003/05/11 00:03:45  dave
// More Trace
//
// Revision 1.44  2003/05/10 08:45:39  dave
// de-UIing the Create for back end processing
//
// Revision 1.43  2003/05/10 07:39:32  dave
// more trace and streamlining
//
// Revision 1.42  2003/05/10 00:11:52  joan
// remove xml stuff
//
// Revision 1.41  2003/05/09 23:52:20  joan
// fix report
//
// Revision 1.40  2003/05/09 23:23:50  dave
// more trace
//
// Revision 1.39  2003/05/09 22:49:46  dave
// more segmenting the UI function so back end function
// is not burdon'ed with un needed objects
//
// Revision 1.38  2003/05/09 21:18:00  dave
// introducing the concept of turning off not needed things in
// action item execution
//
// Revision 1.37  2003/05/08 17:38:25  joan
// fix fb
//
// Revision 1.36  2003/05/05 21:39:15  joan
// work on xml report
//
// Revision 1.35  2003/05/01 17:08:43  joan
// fix code for xml
//
// Revision 1.34  2003/04/30 21:15:11  joan
// fix bug
//
// Revision 1.33  2003/04/30 20:50:33  joan
// fix bug
//
// Revision 1.32  2003/04/30 20:14:36  joan
// add code for xml display
//
// Revision 1.31  2003/04/24 17:42:28  joan
// return only entity with positive id in search
//
// Revision 1.30  2003/04/23 22:32:41  joan
// set profile valon effon
//
// Revision 1.29  2003/04/21 20:43:15  joan
// fb fix
//
// Revision 1.28  2003/04/17 16:37:04  joan
// fix code
//
// Revision 1.27  2003/04/16 19:52:43  joan
// fix PDG creating entity
//
// Revision 1.26  2003/04/14 16:58:13  joan
// fix fb
//
// Revision 1.25  2003/04/10 17:13:46  joan
// fix compile
//
// Revision 1.24  2003/04/10 17:00:41  joan
// fix checking MTM
//
// Revision 1.23  2003/03/27 22:31:58  joan
// fix bug
//
// Revision 1.22  2003/03/27 21:45:12  joan
// debug
//
// Revision 1.21  2003/03/27 21:20:27  joan
// debug
//
// Revision 1.20  2003/03/27 17:04:17  joan
// call table roll back before search
//
// Revision 1.19  2003/03/27 16:35:46  joan
// remove error msg
//
// Revision 1.18  2003/03/26 22:54:00  joan
// adjust code for optfeature id
//
// Revision 1.17  2003/03/26 20:37:29  joan
// add SW support and maintenance PDGs
//
// Revision 1.16  2003/03/26 01:12:37  joan
// debug
//
// Revision 1.15  2003/03/26 00:30:36  joan
// debug
//
// Revision 1.14  2003/03/25 16:48:00  joan
// fix code
//
// Revision 1.13  2003/03/25 00:19:46  joan
// some adjustments
//
// Revision 1.12  2003/03/19 23:09:47  joan
// remove System.out
//
// Revision 1.11  2003/03/19 18:46:40  joan
// adjust codes
//
// Revision 1.10  2003/03/19 01:17:45  joan
// debug
//
// Revision 1.9  2003/03/18 00:15:21  joan
// adjust code
//
// Revision 1.8  2003/03/17 21:30:30  joan
// fix bugs
//
// Revision 1.7  2003/03/14 18:38:50  joan
// some adjustment
//
// Revision 1.6  2003/03/12 00:48:00  joan
// fix bugs
//
// Revision 1.5  2003/03/11 17:52:24  joan
// add more work
//
// Revision 1.4  2003/03/06 16:07:02  joan
// put more work
//
// Revision 1.3  2003/03/05 19:01:50  joan
// put more work
//
// Revision 1.2  2003/03/04 01:00:15  joan
// add pdg action item
//
// Revision 1.1  2003/03/03 18:38:08  joan
// initial load
//

// RMI and middleware

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.rmi.RemoteException;

import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Hashtable;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Arrays;
import java.sql.PreparedStatement;
import java.sql.Connection;

import java.text.*;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnID;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.transactions.OPICMList;

import COM.ibm.opicmpdh.objects.Blob;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.objects.LongText;
import COM.ibm.opicmpdh.objects.MultipleFlag;
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.opicmpdh.objects.Text;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSetGroup;

/**
 * PDGUtility
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PDGUtility implements Serializable {
    static final long serialVersionUID = 20011106L;

    private EANList m_elList = new EANList();
    private EANList m_egList = new EANList();
    private EANList m_caiList = new EANList();
    private EANList m_caiSList = new EANList();
    private EANList m_saiList = new EANList();
    private EANList m_saiTable = new EANList();
    private EANList m_caiTable = new EANList();
    private static final String[] m_arrFeatId = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
    private static final int MAX_RESULT_LEN = 2000000;

    public void dereference(){
    	if (m_elList != null){
    		for (int x=0; x<m_elList.size(); x++){
    			EntityList list = (EntityList)m_elList.getAt(x);
    			list.dereference();
    		}
    		m_elList.clear();
    		m_elList = null;
    	}
    	if (m_egList != null){
    		m_egList.clear();
    		m_egList = null;
    	}
    	if (m_caiList != null){
    		m_caiList.clear();
    		m_caiList = null;
    	}
       	if (m_caiSList != null){
       		m_caiSList.clear();
       		m_caiSList = null;
    	}
      	if (m_saiList != null){
      		m_saiList.clear();
      		m_saiList = null;
    	}
     	if (m_saiTable != null){
     		m_saiTable.clear();
     		m_saiTable = null;
    	}
     	if (m_caiTable != null){
     		m_caiTable.clear();
     		m_caiTable = null;
    	}

    	m_sbActivities = null;
    }
    /**
     * FIELD
     */

	public final static String FOREVER = "9999-12-31-00.00.00.000000";
    /**
     * FIELD
     */
    public static final String STATUS_SAVED = "Saved ";
    /**
     * FIELD
     */
    public static final String STATUS_PASSED = "Passed ";
    /**
     * FIELD
     */
    public static final String STATUS_SUBMIT = "Submitted ";
    /**
     * FIELD
     */
    public static final String STATUS_ERROR = "Error ";
    /**
     * FIELD
     */
    public static final String STATUS_RUNNING = "Running ";
    /**
     * FIELD
     */
    public static final String STATUS_COMPLETE = "Complete ";
    /**
     * FIELD
     */
    public static final int OF_PRODUCT = 0;
    /**
     * FIELD
     */
    public static final int OF_SUBSCRIPTION = 1;
    /**
     * FIELD
     */
    public static final int OF_MAINTENANCE = 2;
    /**
     * FIELD
     */
    public static final int OF_SUPPORT = 3;
    /**
     * FIELD
     */
    public static final String AVAIL5REGIONS = "PDGtemplates/Avail5Regions.txt";
    ;

    private StringBuffer m_sbActivities = new StringBuffer();

    /**
     * FIELD
     */
    private static final String NEW_LINE = "\n";
    /**
     * FIELD
     */
    public final static int EQUAL = 0;
    /**
     * FIELD
     */
    public final static int LATER = 1;
    /**
     * FIELD
     */
    public final static int EARLIER = 2;
    /**
     * FIELD
     */

    public final static int ILLEGALARGUMENT = -1;

	// for SALES_STATUS, SALESORG_COUNTRY
	/* obsolete with MN33416775
	public static final int BUYABLE = 0;
	public static final int HIDE = 1;
	public static final int CART = 2;

	public static Hashtable m_hshMTRNCODE = new Hashtable();
    static {
        m_hshMTRNCODE.put("Z0","Yes,No,Yes");
		m_hshMTRNCODE.put("YA","No,Yes,No");
		m_hshMTRNCODE.put("Z2","No,No,No");
		m_hshMTRNCODE.put("Z3","No,Yes,No");
		m_hshMTRNCODE.put("Z4","Yes,No,Yes");
		m_hshMTRNCODE.put("ZJ","No,Yes,No");
		m_hshMTRNCODE.put("Z7","No,Yes,No");
		m_hshMTRNCODE.put("ZB","No,No,No");
		m_hshMTRNCODE.put("ZE","Yes,No,Yes");
		m_hshMTRNCODE.put("ZF","No,No,No");
		m_hshMTRNCODE.put("ZG","No,Yes,No");
		m_hshMTRNCODE.put("ZH","No,Yes,No");
		m_hshMTRNCODE.put("ZM","Yes,No,Yes");
		m_hshMTRNCODE.put("ZN","Yes,No,Yes");
		m_hshMTRNCODE.put("ZQ","Yes,No,Yes");
		m_hshMTRNCODE.put("ZV","No,Yes,No");
		m_hshMTRNCODE.put("ZZ","No,Yes,No");
 		m_hshMTRNCODE.put("na","No,Yes,No");
	}

    public String getValueFromMtrnCode(String _strMATERIALSTATUS, int _iType) {
        String strTraceBase = "PDGUtility getValueFromMtrnCode method ";
	//	D.ebug(D.EBUG_SPEW,strTraceBase + _strMATERIALSTATUS + ":" + _iType);
        String strMtrnCodes = (String)m_hshMTRNCODE.get(_strMATERIALSTATUS);
        if (strMtrnCodes != null && strMtrnCodes.length() > 0) {
            StringTokenizer st = new StringTokenizer(strMtrnCodes, ",");
            if (st.countTokens() == 3) {
                String strBuyable = st.nextToken();
                String strHide = st.nextToken();
                String strCart = st.nextToken();
                switch (_iType) {
                    case BUYABLE:
                        return strBuyable;
                    case HIDE:
                        return strHide;
                    case CART:
                        return strCart;
                    default:
                        return "";
                }
            }
        }
        return "";
    }
	// END for SALES_STATUS, SALESORG_COUNTRY
	*/

	public static final String DEFAULTMTRNCODE = "YA";

    private RowSelectableTable getRowSelectableTable(EntityList _eList) {
        int ii = _eList.getEntityGroupCount();
        for (int i = 0; i < ii; ++i) {
            EntityGroup eg = _eList.getEntityGroup(i);
            if (eg.isDisplayable()) {
                return eg.getEntityGroupTable();
            }
        }
        return null;
    }

    private String checkMaxLength(EANAttribute _eanAttr, String _strValue) {
        EANMetaAttribute eanMA = _eanAttr.getMetaAttribute();
        if (_strValue != null) {
            //max length validation
            int len = eanMA.getMaxLen();
            int strLen = _strValue.length();
            if (len != 0 && strLen != 0 && strLen > len) {
                _strValue = _strValue.substring(0, len);
            }
        }
        return _strValue;
    }

    private String checkMaxLength(EANMetaAttribute _ma, String _strValue) {
        if (_strValue != null) {
            //max length validation
            int len = _ma.getMaxLen();
            int strLen = _strValue.length();
            if (len != 0 && strLen != 0 && strLen > len) {
                _strValue = _strValue.substring(0, len);
            }
        }
        return _strValue;
    }

    private void checkReqInfoMissing(RowSelectableTable _eiRst) {
        String strTraceBase = " PDGUtility checkReqInfoMissing method ";
        D.ebug(D.EBUG_SPEW, strTraceBase);
        try {
            for (int r = 0; r < _eiRst.getRowCount(); r++) {
                EANAttribute att = (EANAttribute) _eiRst.getEANObject(r, 1);
                if (att.isRequired() && !att.hasData() && att.isEditable()) {
                    if (att instanceof EANTextAttribute) {
                        _eiRst.put(r, 1, "PDG");
                    } else if (att instanceof EANFlagAttribute) {
                        EANFlagAttribute fa = (EANFlagAttribute) att;
                        EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) fa.getMetaAttribute();
                        MetaFlag mf = mfa.getMetaFlag(0);
                        fa.put(mf.getFlagCode(), true);
                    }
                }
            }
        } catch (EANBusinessRuleException bre) {
            bre.printStackTrace();
        }
    }

    private String[] getValueArray(String _s) {
        String[] aReturn = null;
        Vector v = new Vector();
        if (_s.indexOf(',') > -1) {
            StringTokenizer st = new StringTokenizer(_s, ",");
            while (st.hasMoreTokens()) {
                v.addElement(st.nextToken().trim());
            }
        } else {
            v.addElement(_s);
        }

        aReturn = new String[v.size()];
        v.copyInto(aReturn);
        return aReturn;
    }

 /*    BH SR87, SR655 - extended combounique rule
  * private Attribute checkBusinessRule(EntityItem _ei, Attribute _attribute, EANMetaAttribute _ma, OPICMList _attList) {
        String strTraceBase = "PDGUtility checkBusinessRule method";
        if (_attribute instanceof Text) {
            Text text1 = (Text) _attribute;
            text1.m_strLongDescription = _ma.getLongDescription();

            if (_ma.isUnique()) {
                text1.m_bUnique = true;
                text1.m_strUniqueClass = _ma.getUniqueClass();
                text1.m_strUniqueType = _ma.getUniqueType();
            }

            if (_ma.isComboUnique() || _ma.isComboUniqueOptional()) {
                for (int i = 0; i < _ma.getComboUniqueAttributeCode().size(); i++) {

                    EANAttribute att = null;

                    String strAttCode = _ma.getComboUniqueAttributeCode(i);
                    // check in the list of attributes for update first
                    String str = (String) _attList.get(_ei.getEntityType() + ":" + strAttCode);
                    if (str != null && str.length() > 0) {
                        StringTokenizer st = new StringTokenizer(str, "=");
                        text1.m_bComboUnique = _ma.isComboUnique();
                        text1.m_bComboUniqueOptional = _ma.isComboUniqueOptional();
                        text1.m_strComboAttributeCode = st.nextToken();
                        text1.m_strComboAttributeValue = st.nextToken();
                        text1.m_strComboUniqueGrouping = _ma.getComboUniqueGrouping();
                        if (_ma.isComboUniqueOptional()) {
                            text1.m_bComboUniqueOptRequiredAtt = _ma.isComboUniqueOptionalRequiredAttribute();
                        }
                        break;
                    } else {
                        Enumeration e = _attList.keys();
                        while (e.hasMoreElements()) {
                            System.out.println(strTraceBase+" "+e.nextElement());
                        }
                    }

                    // We need to check this only if there is a value in the sister attribute
                    att = _ei.getAttribute(strAttCode);
                    if (att != null && att.isActive()) {
                        text1.m_bComboUnique = _ma.isComboUnique();
                        text1.m_bComboUniqueOptional = _ma.isComboUniqueOptional();
                        text1.m_strComboAttributeCode = strAttCode;
                        text1.m_strComboAttributeDesc = att.getMetaAttribute().getLongDescription();
                        text1.m_strComboUniqueGrouping = att.getMetaAttribute().getComboUniqueGrouping();
                        if (_ma.isComboUniqueOptional()) {
                            text1.m_bComboUniqueOptRequiredAtt = _ma.isComboUniqueOptionalRequiredAttribute();
                        }
                        if (att instanceof EANFlagAttribute) {
                            EANFlagAttribute fa = (EANFlagAttribute) att;
                            text1.m_strComboAttributeValue = fa.getFlagCodes();
                        } else {
                            text1.m_strComboAttributeValue = att.toString();
                        }
                    }
                }
            }
            return text1;
        } else if (_attribute instanceof SingleFlag) {
            SingleFlag sf = (SingleFlag) _attribute;

            String strComboAttributeCode = null;
            String strComboAttributeValue = null;
            String strComboAttributeDesc = null;

            if (_ma.isComboUnique()) {
                for (int i = 0; i < _ma.getComboUniqueAttributeCode().size(); i++) {
                    EANAttribute att = null;
                    String strAttCode = _ma.getComboUniqueAttributeCode(i);
                    // check in the list of attributes for update first
                    String str = (String) _attList.get(_ei.getEntityType() + ":" + strAttCode);
                    if (str != null && str.length() > 0) {
                        StringTokenizer st = new StringTokenizer(str, "=");
                        strComboAttributeCode = st.nextToken();
                        strComboAttributeValue = st.nextToken();
                        break;
                    } else {
                        Enumeration e = _attList.keys();
                        D.ebug(D.EBUG_SPEW,strTraceBase + " in SingleFlag: not found in _attList " + _ei.getEntityType() + ":" + strAttCode);
                        while (e.hasMoreElements()) {
                            System.out.println(strTraceBase+" "+e.nextElement());
                        }
                    }

                    // We need to check this only if there is a value in the sister attribute
                    att = _ei.getAttribute(strAttCode);
                    if (att != null) {
                        strComboAttributeCode = strAttCode;
                        strComboAttributeValue = att.toString();
                        strComboAttributeDesc = att.getMetaAttribute().getLongDescription();
                        break;
                    }
                }
            }

            if (_ma.isUnique()) {
                sf.m_bUnique = true;
                sf.m_strUniqueClass = _ma.getUniqueClass();
                sf.m_strUniqueType = _ma.getUniqueType();
                //                sf.m_strFlagDescription = toString();
                sf.m_strDescription = _ma.getLongDescription();
            }

            if (_ma.isComboUnique()) {
                sf.m_bComboUnique = _ma.isComboUnique();
                sf.m_strComboAttributeCode = strComboAttributeCode;
                sf.m_strComboAttributeValue = strComboAttributeValue;
                sf.m_strComboAttributeDesc = strComboAttributeDesc;
                //                sf.m_strFlagDescription = toString();
                sf.m_strLongDescription = _ma.getLongDescription();
                sf.m_strComboUniqueGrouping = _ma.getComboUniqueGrouping();
            }
            return sf;
        } else if (_attribute instanceof MultipleFlag) {
            MultipleFlag mf = (MultipleFlag) _attribute;
            String strComboAttributeCode = null;
            String strComboAttributeValue = null;
            String strComboAttributeDesc = null;

            if (_ma.isComboUnique()) {
                for (int i = 0; i < _ma.getComboUniqueAttributeCode().size(); i++) {
                    EANAttribute att = null;
                    String strAttCode = _ma.getComboUniqueAttributeCode(i);
                    // check in the list of attributes for update first
                    String str = (String) _attList.get(_ei.getEntityType() + ":" + strAttCode);
                    if (str != null && str.length() > 0) {
                        StringTokenizer st = new StringTokenizer(str, "=");
                        strComboAttributeCode = st.nextToken();
                        strComboAttributeValue = st.nextToken();
                        break;
                    }

                    // We need to check this only if there is a value in the sister attribute
                    att = _ei.getAttribute(strAttCode);
                    if (att != null) {
                        strComboAttributeCode = strAttCode;
                        strComboAttributeValue = att.toString();
                        strComboAttributeDesc = att.getMetaAttribute().getLongDescription();
                        break;
                    }
                }
            }

            if (_ma.isComboUnique()) {
                mf.m_bComboUnique = _ma.isComboUnique();
                mf.m_strComboAttributeCode = strComboAttributeCode;
                mf.m_strComboAttributeValue = strComboAttributeValue;
                mf.m_strComboAttributeDesc = strComboAttributeDesc;
                mf.m_strLongDescription = _ma.getLongDescription();
                mf.m_strComboUniqueGrouping = _ma.getComboUniqueGrouping();
            }
            return mf;
        }

        return null;
    }*/

    /**
     * getEntityGroup
     *
     * @param _strEntityType
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getEntityGroup(String _strEntityType) {
        return (EntityGroup) m_egList.get(_strEntityType);
    }

    /**
     * updateEntity
     *
     * @param _db
     * @param _prof
     * @param _ei
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws java.sql.SQLException
     * @throws COM.ibm.eannounce.objects.SBRException
     *  @author David Bigelow
     */
    public void updateEntity(Database _db, Profile _prof, EntityItem _ei) throws MiddlewareException, MiddlewareRequestException, SQLException, SBRException {
        OPICMList attList = new OPICMList();

        if (_ei == null) {
            return;
        }

        for (int ii = 0; ii < _ei.getAttributeCount(); ii++) {
            EANAttribute att = _ei.getAttribute(ii);
            String strAttrCode = att.getAttributeCode();
            String strAttrValue = getAttrValue(_ei, strAttrCode);
            if (strAttrValue != null && strAttrValue.length() > 0) {
                attList.put(strAttrCode, strAttrCode + "=" + strAttrValue);
            }
        }
        if (attList.size() > 0) {
            updateAttribute(_db, _prof, _ei, attList);
        }
    }

    /**
     * updateAttribute
     *
     * @param _db
     * @param _prof
     * @param _ei
     * @param _attList
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws java.sql.SQLException
     * @throws COM.ibm.eannounce.objects.SBRException
     *  @author David Bigelow
     */
    public void updateAttribute(Database _db, Profile _prof, EntityItem _ei, OPICMList _attList) throws MiddlewareException, MiddlewareRequestException, SQLException, SBRException {

        String strTraceBase = " PDGUtility updateAttribute method";

        byte[] baValue = null;
        String strExt = null;
        String strAttributeClass = null;
        Blob blob = null;
        Text t = null;
        MultipleFlag mf = null;
        LongText lt = null;
        SingleFlag sf = null;

        StringTokenizer st2 = null;

        // Pull some profile info...
        int iOPWGID = _prof.getOPWGID();
        int iTranID = _prof.getTranID();
        int iNLSID = _prof.getReadLanguage().getNLSID();
        String strEnterprise = _prof.getEnterprise();
        SBRException sbrEx = new SBRException();

        _db.debug(D.EBUG_SPEW, strTraceBase);

        try {
            Profile prof = setProfValOnEffOn(_db, _prof);
            DatePackage dpNow = _db.getDates();
            String strNow = dpNow.getNow();
            String strForever = dpNow.getForever();
            ControlBlock cbOn = new ControlBlock(strNow, strForever, strNow, strForever, iOPWGID, iTranID);
            ControlBlock cbOff = new ControlBlock(strNow, strNow, strNow, strNow, iOPWGID, iTranID);

            Vector vctReturnEntityKeys = new Vector();
            Vector vctAtts = new Vector();

            String strEntityType = _ei.getEntityType();
            int iEntityID = _ei.getEntityID();

            ReturnEntityKey rek = new ReturnEntityKey(strEntityType, iEntityID, true);
            EntityGroup eg = (EntityGroup) m_egList.get(strEntityType);

            _db.debug(D.EBUG_SPEW, strTraceBase + " updating entity: " + strEnterprise + ":" + strEntityType + ":" + iEntityID);

            _prof = prof;

            if (eg == null) {
                eg = new EntityGroup(null, _db, _prof, strEntityType, "Edit", false);
                m_egList.put(eg.getEntityType(), eg);
            }

            _ei = new EntityItem(eg, _prof, _db, _ei.getEntityType(), _ei.getEntityID());

            for (int j = 0; j < _attList.size(); j++) {
                String str = (String) _attList.getAt(j);
                int iEqual = str.indexOf("=");
                String strAttributeCode = str.substring(0,iEqual);
                String strAttributeValue = str.substring(iEqual+1);

				_db.debug(D.EBUG_SPEW, strTraceBase + " str: " + str + ":" + strAttributeCode);
                String strOldValue = getAttrValue(_ei, strAttributeCode);
                EANMetaAttribute ma = eg.getMetaAttribute(strAttributeCode);
                if (ma == null ) {
					if (strAttributeCode.equals("ALL")) {
						_db.debug(D.EBUG_SPEW, strTraceBase + " in ALL ");
						continue;
					} else {
					    sbrEx.add(strTraceBase + " meta missing: " + strAttributeCode);
                    	throw sbrEx;
					}
                }
				//_db.debug(D.EBUG_SPEW, strTraceBase + " keep going ");
                strAttributeClass = ma.getAttributeType();
                switch (strAttributeClass.charAt(0)) {
                case 'T' :
                case 'I' :
                    if (strAttributeValue != null && strAttributeValue.trim().length() > 0) {
                        if (!strAttributeValue.equals(strOldValue)) {
                            strAttributeValue = checkMaxLength(ma, strAttributeValue);
                            t = new Text(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, cbOn);
                            //t = (Text) checkBusinessRule(_ei, t, ma, _attList);
                            // BH SR87, SR655 - extended combounique rule
                            EANTextAttribute.updateUniquenessAttrs(_ei,t, ma,true, _attList);
                        }
                    } else {
                        if (strOldValue != null && strOldValue.trim().length() > 0) {
                            t = new Text(strEnterprise, strEntityType, iEntityID, strAttributeCode, strOldValue, iNLSID, cbOff);
                        }
                    }
                    if (t != null) {
                        vctAtts.addElement(t);
                    }
                    break;
                case 'F' :
                    if (strAttributeValue != null && strAttributeValue.trim().length() > 0) {
                        if (!strAttributeValue.equals(strOldValue)) {
                            StringTokenizer st1 = new StringTokenizer(strAttributeValue, ",");
                            if (st1.hasMoreTokens()) {
                                while (st1.hasMoreTokens()) {
                                    String s = st1.nextToken();
                                    mf = new MultipleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, s, iNLSID, cbOn);
                                    vctAtts.addElement(mf);
                                    // BH SR87, SR655 - extended combounique rule
                                    String comboArray[] = EANFlagAttribute.updateUniquenessAttrs(_ei, ma, _attList);
                                    EANFlagAttribute.updateUniquenessAttrs(_ei, ma, mf,	s, comboArray);
                                }
                            } else {
                                mf = new MultipleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, cbOn);
                                vctAtts.addElement(mf);
                                // BH SR87, SR655 - extended combounique rule
                                String comboArray[] = EANFlagAttribute.updateUniquenessAttrs(_ei, ma, _attList);
                                EANFlagAttribute.updateUniquenessAttrs(_ei, ma, mf,	strAttributeValue, comboArray);
                            }

                            // to remove unwanted flags
                            st2 = new StringTokenizer(strOldValue, ",");
                            if (st2.hasMoreTokens()) {
                                while (st2.hasMoreTokens()) {
                                    String s = st2.nextToken();
                                    if (strAttributeValue.indexOf(s) < 0) {
                                        mf = new MultipleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, s, iNLSID, cbOff);
                                        if (mf != null) {
                                            vctAtts.addElement(mf);
                                        }
                                    }
                                }
                            } else {
                                if (strAttributeValue.indexOf(strOldValue) < 0) {
                                    mf = new MultipleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, strOldValue, iNLSID, cbOff);
                                    if (mf != null) {
                                        vctAtts.addElement(mf);
                                    }
                                }
                            }

                        }
                    } else {
                        if (strOldValue != null && strOldValue.trim().length() > 0) {
                            StringTokenizer st1 = new StringTokenizer(strOldValue, ",");
                            if (st1.hasMoreTokens()) {
                                while (st1.hasMoreTokens()) {
                                    String s = st1.nextToken();
                                    mf = new MultipleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, s, iNLSID, cbOff);
                                    if (mf != null) {
                                        vctAtts.addElement(mf);
                                    }
                                }
                            } else {
                                mf = new MultipleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, strOldValue, iNLSID, cbOff);
                                if (mf != null) {
                                    vctAtts.addElement(mf);
                                }
                            }
                        }
                    }

                    break;
                case 'U' :
                case 'S' :
                case 'A' :
                    if (strAttributeValue != null && strAttributeValue.trim().length() > 0) {
                        if (!strAttributeValue.equals(strOldValue)) {
                            sf = new SingleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, cbOn);
                            //sf = (SingleFlag) checkBusinessRule(_ei, sf, ma, _attList);
                            // BH SR87, SR655 - extended combounique rule
                            String comboArray[] = EANFlagAttribute.updateUniquenessAttrs(_ei, ma, _attList);
                            EANFlagAttribute.updateUniquenessAttrs(_ei, ma, sf,	strAttributeValue, comboArray);
                        }
                    } else {
                        if (strOldValue != null && strOldValue.trim().length() > 0) {
                            sf = new SingleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, strOldValue, iNLSID, cbOff);
                        }
                    }
                    if (sf != null) {
                        vctAtts.addElement(sf);
                    }
                    break;
                case 'L' :
                case 'X' :
                    if (strAttributeValue != null && strAttributeValue.trim().length() > 0) {
                        if (!strAttributeValue.equals(strOldValue)) {
                            lt = new LongText(strEnterprise, strEntityType, iEntityID, strAttributeCode, checkMaxLength(ma, strAttributeValue), iNLSID, cbOn);
                        }
                    } else {
                        if (strOldValue != null && strOldValue.trim().length() > 0) {
                            lt = new LongText(strEnterprise, strEntityType, iEntityID, strAttributeCode, strOldValue, iNLSID, cbOff);
                        }
                    }

                    if (lt != null) {
                        vctAtts.addElement(lt);
                    }
                    break;
                case 'B' :
                    baValue = strAttributeValue.getBytes();
                    strExt = _ei.getKey() + strAttributeCode + ".txt";
                    blob = new Blob(strEnterprise, strEntityType, iEntityID, strAttributeCode, baValue, strExt, iNLSID, cbOn);
                    if (blob != null) {
                        vctAtts.addElement(blob);
                    }
                    break;
                default :
                    _db.debug(D.EBUG_ERR, "**No home for AttributeClass" + strAttributeClass + ":");
                }
            }

            rek.m_vctAttributes = vctAtts;
            vctReturnEntityKeys.addElement(rek);
            //updating entity items
            _db.update(_prof, vctReturnEntityKeys);

        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
    }
    /*
    public void updateAttribute(Database _db, Profile _prof, EntityItem _ei, OPICMList _attList) throws MiddlewareException, MiddlewareRequestException, SQLException, SBRException {

        String strTraceBase = " PDGUtility updateAttribute method";

        byte[] baValue = null;
        String strExt = null;
        String strAttributeClass = null;
        Blob blob = null;
        Text t = null;
        MultipleFlag mf = null;
        LongText lt = null;
        SingleFlag sf = null;

        StringTokenizer st2 = null;

        // Pull some profile info...
        int iOPWGID = _prof.getOPWGID();
        int iTranID = _prof.getTranID();
        int iNLSID = _prof.getReadLanguage().getNLSID();
        String strEnterprise = _prof.getEnterprise();
        SBRException sbrEx = new SBRException();

        _db.debug(D.EBUG_SPEW, strTraceBase);

        try {
            Profile prof = setProfValOnEffOn(_db, _prof);
            DatePackage dpNow = _db.getDates();
            String strNow = dpNow.getNow();
            String strForever = dpNow.getForever();
            ControlBlock cbOn = new ControlBlock(strNow, strForever, strNow, strForever, iOPWGID, iTranID);
            ControlBlock cbOff = new ControlBlock(strNow, strNow, strNow, strNow, iOPWGID, iTranID);

            Vector vctReturnEntityKeys = new Vector();
            Vector vctAtts = new Vector();

            String strEntityType = _ei.getEntityType();
            int iEntityID = _ei.getEntityID();

            ReturnEntityKey rek = new ReturnEntityKey(strEntityType, iEntityID, true);
            EntityGroup eg = (EntityGroup) m_egList.get(strEntityType);

            _db.debug(D.EBUG_SPEW, strTraceBase + " updating entity: " + strEnterprise + ":" + strEntityType + ":" + iEntityID);

            _prof = prof;

            if (eg == null) {
                eg = new EntityGroup(null, _db, _prof, strEntityType, "Edit", false);

                m_egList.put(eg.getEntityType(), eg);
            }

            _ei = new EntityItem(eg, _prof, _db, _ei.getEntityType(), _ei.getEntityID());

            for (int j = 0; j < _attList.size(); j++) {
                String str = (String) _attList.getAt(j);
                //StringTokenizer st = new StringTokenizer(str, "=");
                int iEqual = str.indexOf("=");
                String strAttributeCode = str.substring(0,iEqual);
                String strAttributeValue = str.substring(iEqual+1);

//                String strAttributeCode = st.nextToken();
//                String strAttributeValue = st.nextToken();
				_db.debug(D.EBUG_SPEW, strTraceBase + " str: " + str + ":" + strAttributeCode);
                String strOldValue = getAttrValue(_ei, strAttributeCode);
                EANMetaAttribute ma = eg.getMetaAttribute(strAttributeCode);
                if (ma == null ) {
					if (strAttributeCode.equals("ALL")) {
						_db.debug(D.EBUG_SPEW, strTraceBase + " in ALL ");
						continue;
					} else {
					    sbrEx.add(strTraceBase + " meta missing: " + strAttributeCode);
                    	throw sbrEx;
					}
                }
				//_db.debug(D.EBUG_SPEW, strTraceBase + " keep going ");
                strAttributeClass = ma.getAttributeType();
                switch (strAttributeClass.charAt(0)) {
                case 'T' :
                case 'I' :
                    if (strAttributeValue != null && strAttributeValue.trim().length() > 0) {
                        if (!strAttributeValue.equals(strOldValue)) {
                            strAttributeValue = checkMaxLength(ma, strAttributeValue);
                            t = new Text(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, cbOn);
                            t = (Text) checkBusinessRule(_ei, t, ma, _attList);
                        }
                    } else {
                        if (strOldValue != null && strOldValue.trim().length() > 0) {
                            t = new Text(strEnterprise, strEntityType, iEntityID, strAttributeCode, strOldValue, iNLSID, cbOff);
                        }
                    }
                    if (t != null) {
                        vctAtts.addElement(t);
                    }
                    break;
                case 'F' :
                    if (strAttributeValue != null && strAttributeValue.trim().length() > 0) {
                        if (!strAttributeValue.equals(strOldValue)) {
                            StringTokenizer st1 = new StringTokenizer(strAttributeValue, ",");
                            if (st1.hasMoreTokens()) {
                                while (st1.hasMoreTokens()) {
                                    String s = st1.nextToken();
                                    mf = new MultipleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, s, iNLSID, cbOn);
                                    if (mf != null) {
                                        vctAtts.addElement(mf);
                                    }
                                }
                            } else {
                                mf = new MultipleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, cbOn);
                                if (mf != null) {
                                    vctAtts.addElement(mf);
                                }
                            }

                            // to remove unwanted flags
                            st2 = new StringTokenizer(strOldValue, ",");
                            if (st2.hasMoreTokens()) {
                                while (st2.hasMoreTokens()) {
                                    String s = st2.nextToken();
                                    if (strAttributeValue.indexOf(s) < 0) {
                                        mf = new MultipleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, s, iNLSID, cbOff);
                                        if (mf != null) {
                                            vctAtts.addElement(mf);
                                        }
                                    }
                                }
                            } else {
                                if (strAttributeValue.indexOf(strOldValue) < 0) {
                                    mf = new MultipleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, strOldValue, iNLSID, cbOff);
                                    if (mf != null) {
                                        vctAtts.addElement(mf);
                                    }
                                }
                            }

                        }
                    } else {
                        if (strOldValue != null && strOldValue.trim().length() > 0) {
                            StringTokenizer st1 = new StringTokenizer(strOldValue, ",");
                            if (st1.hasMoreTokens()) {
                                while (st1.hasMoreTokens()) {
                                    String s = st1.nextToken();
                                    mf = new MultipleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, s, iNLSID, cbOff);
                                    if (mf != null) {
                                        vctAtts.addElement(mf);
                                    }
                                }
                            } else {
                                mf = new MultipleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, strOldValue, iNLSID, cbOff);
                                if (mf != null) {
                                    vctAtts.addElement(mf);
                                }
                            }
                        }
                    }

                    break;
                case 'U' :
                case 'S' :
                case 'A' :
                    if (strAttributeValue != null && strAttributeValue.trim().length() > 0) {
                        if (!strAttributeValue.equals(strOldValue)) {
                            sf = new SingleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, cbOn);
                            sf = (SingleFlag) checkBusinessRule(_ei, sf, ma, _attList);
                        }
                    } else {
                        if (strOldValue != null && strOldValue.trim().length() > 0) {
                            sf = new SingleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, strOldValue, iNLSID, cbOff);
                        }
                    }
                    if (sf != null) {
                        vctAtts.addElement(sf);
                    }
                    break;
                case 'L' :
                case 'X' :
                    if (strAttributeValue != null && strAttributeValue.trim().length() > 0) {
                        if (!strAttributeValue.equals(strOldValue)) {
                            lt = new LongText(strEnterprise, strEntityType, iEntityID, strAttributeCode, checkMaxLength(ma, strAttributeValue), iNLSID, cbOn);
                        }
                    } else {
                        if (strOldValue != null && strOldValue.trim().length() > 0) {
                            lt = new LongText(strEnterprise, strEntityType, iEntityID, strAttributeCode, strOldValue, iNLSID, cbOff);
                        }
                    }

                    if (lt != null) {
                        vctAtts.addElement(lt);
                    }
                    break;
                case 'B' :
                    baValue = strAttributeValue.getBytes();
                    strExt = _ei.getKey() + strAttributeCode + ".txt";
                    blob = new Blob(strEnterprise, strEntityType, iEntityID, strAttributeCode, baValue, strExt, iNLSID, cbOn);
                    if (blob != null) {
                        vctAtts.addElement(blob);
                    }
                    break;
                default :
                    _db.debug(D.EBUG_ERR, "**No home for AttributeClass" + strAttributeClass + ":");
                }
            }

            rek.m_vctAttributes = vctAtts;
            vctReturnEntityKeys.addElement(rek);
            //updating entity items
            _db.update(_prof, vctReturnEntityKeys);

        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
    }*/
    private OPICMList getValueList(String _s) {

        OPICMList list = new OPICMList();
        if (_s.indexOf(',') > -1) {
            StringTokenizer st = new StringTokenizer(_s, ",");
            while (st.hasMoreTokens()) {
                String s1 = st.nextToken().trim();
                list.put(s1, s1);
            }
        } else {
            list.put(_s, _s);
        }

        return list;
    }

    /**
     * findEntityItem
     *
     * @param _eiList
     * @param _strEntityType
     * @param _strAttributes
     * @return
     *  @author David Bigelow
     */
    public EntityItem findEntityItem(EANList _eiList, String _strEntityType, String _strAttributes) {
		return findEntityItem(_eiList, _strEntityType, _strAttributes, false);
	}
    /**
     * findEntityItem
     *
     * @param _eiList
     * @param _strEntityType
     * @param _strAttributes
     * @return
     *  @author David Bigelow
     */
    public EntityItem findEntityItem(EANList _eiList, String _strEntityType, String _strAttributes, boolean _bExactMatch) {
        String strTraceBase = " PDGUtility findEntityItem method TRACE";

	//D.ebug(D.EBUG_SPEW,strTraceBase + ":" + _strEntityType + ":" + _strAttributes);
        StringTokenizer st1 = null;
        String strAttrCode = null;
        String strAttrValue = null;
        String[] valArray = null;
        OPICMList valList = null;

        EANAttribute att = null;

        for (int i = 0; i < _eiList.size(); i++) {
            EntityItem ei = (EntityItem) _eiList.getAt(i);
            if (ei != null && ei.getEntityType().equals(_strEntityType)) {
                boolean bMatch = true;

                StringTokenizer st = new StringTokenizer(_strAttributes, ";");
                while (st.hasMoreTokens()) {
                    String s = st.nextToken();
                    //D.ebug(D.EBUG_SPEW,strTraceBase + " find s: " + s);
                    if (s.substring(0, 3).equals("map")) {
                        s = s.substring(4);

                        st1 = new StringTokenizer(s, "=");
                        if (st1.countTokens() < 2) {
                            continue;
                        }
                        strAttrCode = st1.nextToken().trim();
                        strAttrValue = st1.nextToken().trim();
                        valArray = getValueArray(strAttrValue);
                        if (valList != null){
                        	valList.clear();
                        }
                        valList = getValueList(strAttrValue);

						if (strAttrCode.equals("ENTITYKEY")) {
                            String strEIKey = ei.getKey().trim();
                            //D.ebug(D.EBUG_SPEW,strTraceBase + " in ENTITYKEY: " + strAttrCode + ", " + strAttrValue + ", strEIKey: " + strEIKey);

                            if (strEIKey.equals(strAttrValue)) {
                                D.ebug(D.EBUG_SPEW,strTraceBase + " return ei: " + ei.getKey());
                                if (valList!= null){
                                	valList.clear();
                                }
                                return ei;
                            } else {
                                bMatch = false;
                            }
                        } else {
                            att = ei.getAttribute(strAttrCode);
                            if (att != null) {
                                if (att instanceof EANTextAttribute) {
                                    String attValue = ((String) att.get()).trim();
                                    if (!attValue.equals(strAttrValue)) {
                                        bMatch = false;
                                    }
                                } else if (att instanceof EANFlagAttribute) {
                                    MetaFlag[] mfa = (MetaFlag[]) att.get();
                                    boolean bFlag = true;

                                    for (int v = 0; v < valArray.length; v++) {
                                        String val = valArray[v];
                                        for (int f = 0; f < mfa.length; f++) {
                                            MetaFlag mf = mfa[f];
                                            String flagCode = mf.getFlagCode();
                                            String desc = mf.getLongDescription();

                                            if (mf.isSelected()) {
                                               //D.ebug(D.EBUG_SPEW,strTraceBase + " selected flagcode: " + flagCode + ":" + desc);
                                               // selected flag code not in search list
                                               if (_bExactMatch) {
                                                   if (valList.get(flagCode) == null && valList.get(desc) == null) {
                                                      //D.ebug(D.EBUG_SPEW,strTraceBase + " selected flag code not in search list" + flagCode + ":" + desc);
                                                       bFlag = false;
                                                       break;
                                                   }
                                               }

                                               if (!flagCode.equals(val) && !desc.equals(val)) {
                                                   bFlag = false;
                                               } else {
                                                   bFlag = true;
                                                   break;
                                               }
                                           }
                                       }
                                       //D.ebug(D.EBUG_SPEW,strTraceBase + ":" + bFlag + ":" + att.getKey());
                                       if (bFlag && !(att instanceof MultiFlagAttribute)) {
                                           break;
                                       }

                                       if (!bFlag && (att instanceof MultiFlagAttribute)) {
                                           break;
                                       }
                                   }

                                   if (!bFlag) {
                                       bMatch = false;
                                   }
                               }
                           } else {
                               D.ebug(D.EBUG_SPEW,strTraceBase + " att is null " + strAttrCode+" for "+ei.getKey());
                               bMatch = false;
                           }
                       }
                    }
                }

                if (bMatch) {
                    if (valList!= null){
                    	valList.clear();
                    }
                    return ei;
                }
            }
        }
        if (valList!= null){
        	valList.clear();
        }
        return null;
    }

    /**
     * findEntityItem
     *
     * @param _eiList
     * @param _strEntityType
     * @param _strAttributes
     * @return
     *  @author David Bigelow
     */
    public EntityItem[] findMatchEntityItems(EANList _eiList, String _strEntityType, String _strAttributes, boolean _bExactMatch) {
        String strTraceBase = " PDGUtility findEntityItem method TRACE";

//	D.ebug(D.EBUG_SPEW,strTraceBase + ":" + _strEntityType + ":" + _strAttributes);
        StringTokenizer st1 = null;
        String strAttrCode = null;
        String strAttrValue = null;
        String[] valArray = null;
        OPICMList valList = null;
        EANList eiMatchList = new EANList();
        EANAttribute att = null;

        for (int i = 0; i < _eiList.size(); i++) {
            EntityItem ei = (EntityItem) _eiList.getAt(i);
            if (ei != null && ei.getEntityType().equals(_strEntityType)) {
                boolean bMatch = true;

                StringTokenizer st = new StringTokenizer(_strAttributes, ";");
                while (st.hasMoreTokens()) {
                    String s = st.nextToken();
                    D.ebug(D.EBUG_SPEW,strTraceBase + " find s: " + s);
                    if (s.substring(0, 3).equals("map")) {
                        s = s.substring(4);

                        st1 = new StringTokenizer(s, "=");
                        if (st1.countTokens() < 2) {
                            continue;
                        }
                        strAttrCode = st1.nextToken().trim();
                        strAttrValue = st1.nextToken().trim();
                        valArray = getValueArray(strAttrValue);
                        valList = getValueList(strAttrValue);

                        if (strAttrCode.equals("ENTITYKEY")) {
                            String strEIKey = ei.getKey().trim();
                            D.ebug(D.EBUG_SPEW,strTraceBase + " in ENTITYKEY: " + strAttrCode + ", " + strAttrValue + ", strEIKey: " + strEIKey);

                            if (strEIKey.equals(strAttrValue)) {
                                D.ebug(D.EBUG_SPEW,strTraceBase + " return ei: " + ei.getKey());
                                eiMatchList.put(ei);

                            } else {
                                bMatch = false;
                            }
                        } else {
                            att = ei.getAttribute(strAttrCode);
                            if (att != null) {
                                if (att instanceof EANTextAttribute) {
                                    String attValue = ((String) att.get()).trim();
                                    if (!attValue.equals(strAttrValue)) {
                                        bMatch = false;
                                    }
                                } else if (att instanceof EANFlagAttribute) {
                                    MetaFlag[] mfa = (MetaFlag[]) att.get();
                                    boolean bFlag = true;

                                    for (int v = 0; v < valArray.length; v++) {
                                        String val = valArray[v];

                                        for (int f = 0; f < mfa.length; f++) {
                                            MetaFlag mf = mfa[f];
                                            String flagCode = mf.getFlagCode();
                                            String desc = mf.getLongDescription();

                                            if (mf.isSelected()) {
                                               //D.ebug(D.EBUG_SPEW,strTraceBase + " selected flagcode: " + flagCode + ":" + desc);
                                               // selected flag code not in search list
                                               if (_bExactMatch) {
                                                   if (valList.get(flagCode) == null && valList.get(desc) == null) {
                                                           //D.ebug(D.EBUG_SPEW,strTraceBase + " selected flag code not in search list" + flagCode + ":" + desc);
                                                       bFlag = false;
                                                       break;
                                                   }
                                               }

                                               if (!flagCode.equals(val) && !desc.equals(val)) {
                                                   bFlag = false;
                                               } else {
                                                   bFlag = true;
                                                   break;
                                               }
                                           }
                                       }
                                       //D.ebug(D.EBUG_SPEW,strTraceBase + ":" + bFlag + ":" + att.getKey());
                                       if (bFlag && !(att instanceof MultiFlagAttribute)) {
                                           break;
                                       }

                                       if (!bFlag && (att instanceof MultiFlagAttribute)) {
                                           break;
                                       }
                                   }

                                   if (!bFlag) {
                                       bMatch = false;
                                   }
                               }
                           } else {
                               D.ebug(D.EBUG_SPEW,strTraceBase + " att is null " + strAttrCode);
                               bMatch = false;
                           }
                       }
                    }
                }

                if (bMatch) {
                    eiMatchList.put(ei);
                }
            }
        }
        EntityItem[] aeiReturn = new EntityItem[eiMatchList.size()];
        eiMatchList.copyTo(aeiReturn);
        return aeiReturn;
    }

    public EntityItem[] getMatchedChildrens(EntityList _el, EntityItem _eiParent, String _strChildType, String _strRelatorType, StringBuffer _sbMatch, boolean _bExactMatch) {
        String strTraceBase = "CATLGPUBPDG getMatchedChildrens method ";
        //D.ebug(D.EBUG_SPEW, strTraceBase + ":" + _eiParent.getKey() + ":" + _strRelatorType + ":" + _sbMatch.toString());
        EANList list = new EANList();

        EntityGroup egRelator = _el.getEntityGroup(_strRelatorType);
        if (egRelator==null){
        	D.ebug(D.EBUG_ERR, strTraceBase + ": had NULL entitygroup for " + _strRelatorType + " in :" +
        		_eiParent.getKey() + ":"+_strChildType+":" + _sbMatch);
        	return null;
		}

        for (int w=0; w < egRelator.getEntityItemCount(); w++) {
            EntityItem eiRel = egRelator.getEntityItem(w);
            EntityItem eiP = (EntityItem)eiRel.getUpLink(0);
            EntityItem eiC = (EntityItem)eiRel.getDownLink(0);
            if (eiP.getKey().equals(_eiParent.getKey())) {
                list.put(eiC);
            }
        }
        EntityItem[] aeiReturn = findMatchEntityItems(list, _strChildType, _sbMatch.toString(), _bExactMatch);
        return aeiReturn;
    }

    /**
     * createEntity
     *
     * @param _db
     * @param _prof
     * @param _strEntityType
     * @param _attList
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws java.sql.SQLException
     * @return
     *  @author David Bigelow
     */
    public EntityItem createEntity(Database _db, Profile _prof, String _strEntityType, OPICMList _attList) throws MiddlewareException, MiddlewareRequestException, SQLException {

        String strTraceBase = " PDGUtility createEntity method";

        String strAttributeCode = null;
        String strAttributeValue = null;
        String strAttributeClass = null;
        StringTokenizer st1 = null;

        Text t = null;
        SingleFlag sf = null;
        LongText lt = null;
        EANMetaAttribute ma = null;
        int iSemiColon = -1;

        // Pull some profile info...
        int iOPWGID = _prof.getOPWGID();
        int iTranID = _prof.getTranID();
        int iNLSID = _prof.getReadLanguage().getNLSID();
        String strEnterprise = _prof.getEnterprise();
        EntityItem eiReturn = null;

        _db.debug(D.EBUG_SPEW, strTraceBase);

        try {

            OPICMList ol = null;

            DatePackage dpNow = _db.getDates();
            String strNow = dpNow.getNow();
            String strForever = dpNow.getForever();

            ControlBlock cbOn = new ControlBlock(strNow, strForever, strNow, strForever, iOPWGID, iTranID);
            Vector vctReturnEntityKeys = new Vector();
            Vector vctAtts = new Vector();

            int iEntityID = -1;
            ReturnEntityKey rek = new ReturnEntityKey(_strEntityType, iEntityID, true);
            EntityGroup eg = (EntityGroup) m_egList.get(_strEntityType);

            eiReturn = new EntityItem(null, _prof, _strEntityType, iEntityID);

            if (eg == null) {
                eg = new EntityGroup(null, _db, _prof, _strEntityType, "Edit", false);
                m_egList.put(eg.getEntityType(), eg);
            }

            for (int j = 0; j < _attList.size(); j++) {

                String str = (String) _attList.getAt(j);
                StringTokenizer st = new StringTokenizer(str, "=");

                if (st.countTokens() < 2) {
                    continue;
                }
                strAttributeCode = st.nextToken();
                if (strAttributeCode.substring(0, 3).equals("map")) {
                    strAttributeCode = strAttributeCode.substring(4);
                }

                iSemiColon = strAttributeCode.indexOf(":");
                if (iSemiColon > -1) {
                    strAttributeCode = strAttributeCode.substring(iSemiColon + 1);
                }
                strAttributeValue = st.nextToken();
                ma = eg.getMetaAttribute(strAttributeCode);
                _db.test(ma != null, strTraceBase + " meta missing: " + strAttributeCode);
                strAttributeClass = ma.getAttributeType();
                switch (strAttributeClass.charAt(0)) {
                case 'T' :
                case 'I' :
                    t = new Text(strEnterprise, _strEntityType, iEntityID, strAttributeCode, checkMaxLength(ma, strAttributeValue), iNLSID, cbOn);
                    if (t != null) {
                        vctAtts.addElement(t);
                    }
                    break;
                case 'F' :
                    st1 = new StringTokenizer(strAttributeValue, ",");
                    if (st1.hasMoreTokens()) {
                        while (st1.hasMoreTokens()) {
                            String s = st1.nextToken();
                            MultipleFlag mf = new MultipleFlag(strEnterprise, _strEntityType, iEntityID, strAttributeCode, s, iNLSID, cbOn);
                            if (mf != null) {
                                vctAtts.addElement(mf);
                            }
                        }
                    } else {
                        MultipleFlag mf = new MultipleFlag(strEnterprise, _strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, cbOn);
                        if (mf != null) {
                            vctAtts.addElement(mf);
                        }
                    }
                    break;
                case 'U' :
                case 'S' :
                case 'A' :
                    sf = new SingleFlag(strEnterprise, _strEntityType, iEntityID, strAttributeCode, strAttributeValue, iNLSID, cbOn);
                    if (sf != null) {
                        vctAtts.addElement(sf);
                    }
                    break;
                case 'L' :
                    lt = new LongText(strEnterprise, _strEntityType, iEntityID, strAttributeCode, checkMaxLength(ma, strAttributeValue), iNLSID, cbOn);
                    if (lt != null) {
                        vctAtts.addElement(lt);
                    }
                    break;
                default :
                    _db.debug(D.EBUG_ERR, "**No home for AttributeClass" + strAttributeClass + ":");
                }
            }

            rek.m_vctAttributes = vctAtts;
            vctReturnEntityKeys.addElement(rek);

            //updating entity items
            ol = _db.update(_prof, vctReturnEntityKeys);
            if (ol != null) {
                Object obj = ol.get(eiReturn.getKey());
                if (obj instanceof ReturnEntityKey) {
                    rek = (ReturnEntityKey) obj;
                    if (rek.m_iEntityID < 0) {
                        if (rek.isPosted() && rek.isActive()) {
                            eiReturn = new EntityItem(null, _prof, _strEntityType, rek.getReturnID()); // Triggers key change
                        }
                    }
                } else {
                    D.ebug(D.EBUG_SPEW,"EIOL Entity:" + eiReturn.getKey() + ":Cannot be found in the return list");
                }
            }
        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
        return eiReturn;
    }

    /**
     * linkEntities
     *
     * @param _db
     * @param _prof
     * @param _eip
     * @param _aeic
     * @param _strRelatorInfo
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.SBRException
     * @return
     *  @author David Bigelow
     */
    public OPICMList linkEntities(Database _db, Profile _prof, EntityItem _eip, EntityItem[] _aeic, String _strRelatorInfo) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = " PDGUtility linkEntities method ";

        Vector vctReturnEntityKeys = new Vector();
        int id = 0;

        OPICMList ol = null;

        String strRelatorType = "";
        String strRelatorAttr = "";
        int iAttrO = _strRelatorInfo.indexOf("[");
        int iAttrC = _strRelatorInfo.indexOf("]");

        _db.debug(D.EBUG_SPEW, strTraceBase);
        _db.debug(D.EBUG_SPEW, " _strRelatorInfo: " + _strRelatorInfo);

        if (_aeic == null) {
	        _db.debug(D.EBUG_SPEW, strTraceBase + " children are null");
            return null;
        }
        if (_eip == null) {
	        _db.debug(D.EBUG_SPEW, strTraceBase + " parent is null");
            return null;
        }

        if (iAttrO > -1) {
            strRelatorType = _strRelatorInfo.substring(0, iAttrO);
            strRelatorAttr = _strRelatorInfo.substring(iAttrO + 1, (iAttrC > -1 ? iAttrC : _strRelatorInfo.length()));
        } else {
            strRelatorType = _strRelatorInfo;
        }

        for (int i = 0; i < _aeic.length; i++) {
            EntityItem eic = _aeic[i];
            vctReturnEntityKeys.addElement(new ReturnRelatorKey(strRelatorType, --id, _eip.getEntityType(), _eip.getEntityID(), eic.getEntityType(), eic.getEntityID(), true));
        }

        ol = link(_db, _prof, vctReturnEntityKeys, "NODUPES");
        if (ol != null) {
            for (int i = 0; i < _aeic.length; i++) {
                EntityItem ei = _aeic[i];
                m_sbActivities.append("<ITEM><ACTION>Find/Link</ACTION><ENTITYKEY>" + ei.getKey() + "</ENTITYKEY><ENTITYDISPLAY>" + PDGUtility.convertToHTML(ei.getLongDescription()) + "</ENTITYDISPLAY><PARENT>" + _eip.getKey() + ":" + PDGUtility.convertToHTML(_eip.getLongDescription()) + "</PARENT></ITEM>" + NEW_LINE);
            }
        }

        if (strRelatorAttr.length() > 0) {
            //prepare the list of attributes
            StringTokenizer stAtt = null;
            OPICMList attList = new OPICMList();
            StringTokenizer stTemp = new StringTokenizer(strRelatorAttr, ";");
            while (stTemp.hasMoreTokens()) {
                String str = stTemp.nextToken();
                // take out the map prefix
                if (str.indexOf("map") > -1) {
                    str = str.substring(4);
                }
                stAtt = new StringTokenizer(str, "=");
                if (stAtt.hasMoreTokens()) {
                    String strCode = stAtt.nextToken();
                    attList.put(strCode, str);
                }
            }

            for (int i = 0; i < ol.size(); i++) {
                Object obj = ol.getAt(i);
                if (obj instanceof ReturnRelatorKey) {
                    ReturnRelatorKey rrk = (ReturnRelatorKey) obj;
                    EntityItem ei = new EntityItem(null, _prof, rrk.getEntityType(), rrk.getReturnID());
                    updateAttribute(_db, _prof, ei, attList);
                }
            }
        }
        return ol;
    }

    /**
     * link
     *
     * @param _db
     * @param _prof
     * @param _vctReturnEntityKeys
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     *  @author David Bigelow
     */
    public void link(Database _db, Profile _prof, Vector _vctReturnEntityKeys) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
        //    _db.debug(D.EBUG_SPEW, strTraceBase + ":TRACE:3A");

        link(_db, _prof, _vctReturnEntityKeys, "NODUPES");
    }

    /**
     * isDigit
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public boolean isDigit(String _s) {
        for (int i = 0; i < _s.length(); i++) {
            char c = _s.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * getOptFeatIDAbr
     *
     * @param _strOftFeatureID
     * @return
     *  @author David Bigelow
     */
    public String getOptFeatIDAbr(String _strOftFeatureID) {
        // get Optional Feature ID
        String desc = _strOftFeatureID.substring(1);
        String sReturn = desc;
        if (isDigit(desc)) {
            int fi = Integer.parseInt(desc);
            if (fi >= 0 && fi < m_arrFeatId.length) {
                sReturn = m_arrFeatId[fi];
            }
        }
        return sReturn;
    }

    /**
     * getRegTypeAbr
     *
     * @param _strRegType
     * @return
     *  @author David Bigelow
     */
    public String getRegTypeAbr(String _strRegType) {
        if (_strRegType.equals("PrePay")) { // PrePay
            return "0";
        } else if (_strRegType.equals("AfterLicense")) { //AfterLicense
            return "1";
        } else if (_strRegType.equals("Renewal")) { // Renewal
            return "2";
        } else {
            return _strRegType;
        }
    }

    /**
     * getAttrValue
     *
     * @param _ei
     * @param _strAttrCode
     * @return
     *  @author David Bigelow
     */
    public String getAttrValue(EntityItem _ei, String _strAttrCode) {
        StringBuffer sb = new StringBuffer();
        if (_ei == null) {
            return sb.toString();
        }
        EANAttribute att = _ei.getAttribute(_strAttrCode);
        if (att instanceof EANTextAttribute) {
        	sb.append((String) att.get());
        } else if (att instanceof EANFlagAttribute) {
        	MetaFlag[] amf = (MetaFlag[]) att.get();
        	boolean bFirst = true;
        	for (int f = 0; f < amf.length; f++) {
        		if (amf[f].isSelected()) {
        			String flagCode = amf[f].getFlagCode();
        			sb.append(bFirst ? "" : ",");
        			sb.append(flagCode);
        			bFirst = false;
        		}
        	}
        }

        return sb.toString();
    }

    /**
     * getAttrValueDesc
     *
     * @param _ei
     * @param _strAttrCode
     * @return
     *  @author David Bigelow
     */
    public String getAttrValueDesc(EntityItem _ei, String _strAttrCode) {
        StringBuffer sb = new StringBuffer();
        EANAttribute att = _ei.getAttribute(_strAttrCode);
        if (att != null) {
            if (att instanceof EANTextAttribute) {
                sb.append((String) att.get());
            } else if (att instanceof EANFlagAttribute) {
                MetaFlag[] amf = (MetaFlag[]) att.get();
                boolean bFirst = true;
                for (int f = 0; f < amf.length; f++) {
                    if (amf[f].isSelected()) {
                        String flagDesc = amf[f].getLongDescription().trim();
                        sb.append(bFirst ? "" : ",");
                        sb.append(flagDesc);
                        bFirst = false;
                    }
                }
            }
        }
        return sb.toString();
    }

    private EntityItem[] getEntityItemAsArray(EntityList _el, String _strEntityType) {
        String strTraceBase = "PDGUtility getEntityItemAsArray method. ";
        int size = -1;
        EntityItem[] aeiReturn = null;
        EANList eanl = new EANList();
        EntityGroup eg = null;

        EntityGroup egParent = _el.getParentEntityGroup();

        if (egParent != null && egParent.getEntityType().equals(_strEntityType)) {
			eg = egParent;
		} else {
			eg = _el.getEntityGroup(_strEntityType);
		}

        if (eg != null) {
            for (int j = 0; j < eg.getEntityItemCount(); j++) {
                EntityItem ei = eg.getEntityItem(j);
                if (ei.getEntityID() > 0) {
                    eanl.put(ei);
                }
            }
        } else {
            D.ebug(D.EBUG_SPEW, strTraceBase + " entitygroup " + _strEntityType + " is null");
        }

        size = eanl.size();
        aeiReturn = new EntityItem[size];
        for (int i = 0; i < eanl.size(); i++) {
            aeiReturn[i] = (EntityItem) eanl.getAt(i);
            // tm gets heapdump if too many searches are done in a pdg
            // keep the entity and its attributes, deref the list
			Vector linkVct = aeiReturn[i].getDownLink();
			if (linkVct.size()>0){
				EANEntity eaArray[] = new EANEntity[linkVct.size()];
				linkVct.copyInto(eaArray);
				for (int a=0; a<eaArray.length; a++){
					aeiReturn[i].removeDownLink(eaArray[a]);
				}
			}
			linkVct = aeiReturn[i].getUpLink();
			if (linkVct.size()>0){
				EANEntity eaArray[] = new EANEntity[linkVct.size()];
				linkVct.copyInto(eaArray);
				for (int a=0; a<eaArray.length; a++){
					aeiReturn[i].removeUpLink(eaArray[a]);
				}
			}
			eg.removeEntityItem(aeiReturn[i]);
        }
        eanl.clear();
        //_el.dereference(); cant do this here, EntityItems are returned

        return aeiReturn;
    }

    private EntityItem getEntityItemToReturn(RowSelectableTable _rst, int _iRow, String _strEntityType) {
        String strTraceBase = " PDGUtility getEntityItemToReturn method ";
        D.ebug(D.EBUG_SPEW,strTraceBase + _iRow + ":" + _strEntityType);
        EntityItem eiReturn = null;

        if (_iRow >= 0 && _iRow < _rst.getRowCount()) {
            EntityItem ei = (EntityItem) _rst.getRow(_iRow);
            D.ebug(D.EBUG_SPEW,strTraceBase + " ei: " + ei.dump(false));
            if (ei == null) {
                return null;
            }

            if (ei.getEntityType().equals(_strEntityType) && ei.getEntityID() > 0) {
                eiReturn = ei;
            } else {
                EntityGroup eg = ei.getEntityGroup();
                if (eg == null) {
                    return null;
                }
                if (eg.isRelator() || eg.isAssoc()) {
                    EntityItem eic = (EntityItem) ei.getDownLink(0);
                    if (eic.getEntityType().equals(_strEntityType) && eic.getEntityID() > 0) {
                        eiReturn = eic;
                    }
                }
            }
        }
        return eiReturn;
    }

    /**
     * createEntityByRST
     *
     * @param _db
     * @param _prof
     * @param _eiParent
     * @param _attList
     * @param _strCai
     * @param _strRelatorType
     * @param _strEntityType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.SBRException
     * @return
     *  @author David Bigelow
     */
    public EntityItem createEntityByRST(Database _db, Profile _prof, EntityItem _eiParent, OPICMList _attList, String _strCai, String _strRelatorType, String _strEntityType) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {

        CreateActionItem cai = null;
        EntityItem eiReturn = null;
        SBRException sbrEx = new SBRException();

        String strTraceBase = " PDGUtility createEntityByRST method TRACE";
        _db.debug(D.EBUG_SPEW, strTraceBase + ":" + _eiParent.getKey() + ":" + _strCai + ":" + _strRelatorType + ":" + _strEntityType);

        if (_strCai == null || _strCai.length() <= 0) {
            _db.debug(D.EBUG_SPEW, strTraceBase + " CreateActionItem is null. " + _strRelatorType);
            return null;
        }

        // I think we bypass the row selectable table altogher
        // and we simply take the EntityItem out of row one
        // then we take the child..
        // then we plug and chug

        Object obj = m_caiList.get(_strCai);
        if ((obj != null) && (obj instanceof CreateActionItem)) {
            cai = (CreateActionItem)obj;
        }

        if (cai == null) {
            cai = new CreateActionItem(null, _db, _prof, _strCai);
            // DWB disable UI specific functions for load
            cai.disableUIAfterCache();
            cai.disableWorkflow();
            m_caiList.put(_strCai, cai);
        }

        try {
            int row = -1;
            EntityItem ei = null;
            RowSelectableTable tmpTable = null;
            RowSelectableTable rst = null;

            boolean bFirstTime = false;
            EntityItem[] aeiParent = { _eiParent };
            EntityList el = (EntityList) m_elList.get(_strRelatorType);
            if (el == null) {
                el = new EntityList(_db, _prof, cai, aeiParent);
                m_elList.put(_strRelatorType, el);
                bFirstTime = true;
            }

            rst = (RowSelectableTable) m_caiTable.get(_strCai);
            if (rst == null) {
                rst = getRowSelectableTable(el);
                m_caiTable.put(_strCai, rst);
            }

            if (!bFirstTime) {
                rst.addRow();
            }

            row = rst.getRowCount() - 1;
            ei = (EntityItem) rst.getRow(row);
            tmpTable = ei.getEntityItemTable();
            // input Classified attributes first.
            //_db.debug(D.EBUG_SPEW, strTraceBase + " input Classified attributes ");

            for (int i = 0; i < _attList.size(); i++) {
                int r;
                String attCode = null;
                String strValue = null;
                StringTokenizer st = null;

                String strAtt = (String) _attList.getAt(i);
                if (strAtt.indexOf("=") < 0) {
                    _db.debug(D.EBUG_SPEW, strTraceBase + " attribute string not in format: " + strAtt);
                    continue;
                }

                st = new StringTokenizer(strAtt, "=");
                if (st.countTokens() < 2) {
                    continue;
                }

                int iEqual = strAtt.indexOf("=");
                attCode = strAtt.substring(0,iEqual);
                strValue = strAtt.substring(iEqual+1);//st.nextToken();

                r = tmpTable.getRowIndex(attCode);
                if (r < 0) {
                    r = tmpTable.getRowIndex(attCode + ":C");
                }

                if (r < 0) {
                    r = tmpTable.getRowIndex(attCode + ":R");
                }


                if (r >= 0 && r < tmpTable.getRowCount()) {
                    EANFoundation ean = tmpTable.getEANObject(r, 1);
                    if (ean instanceof EANAttribute) {
                        EANAttribute att = (EANAttribute) ean;
                        EANMetaAttribute ma = att.getMetaAttribute();
                        if (ma.isClassified()) {
                            if (att instanceof EANTextAttribute) {
                                try {
                                    EANTextAttribute ta = (EANTextAttribute) att;
                                    ta.put(checkMaxLength(att, strValue));
                                } catch (EANBusinessRuleException bre) {
                                    bre.printStackTrace();
                                    sbrEx.add(bre.getMessage());
                                    throw sbrEx;
                                }
                            } else if (att instanceof EANFlagAttribute) {

                                boolean bFlag = false;
                                String[] valArray = null;
                                MetaFlag[] amf = null;

                                EANFlagAttribute fa = (EANFlagAttribute) att;

                                // remove unwanted default value
                                if (ma.hasDefaultValue()) {
                                    //System.out.println(strTraceBase + " remove DefaultValue flag: " + ma.getDefaultValue());
                                    fa.put(ma.getDefaultValue(), false);
                                }
                                valArray = getValueArray(strValue);
                                amf = (MetaFlag[]) fa.get();
                                for (int v = 0; v < valArray.length; v++) {
                                    String str = valArray[v];

                                    // need to use this put method for classified attribute,
                                    // for some classified attribute, I only have longdescription as inputs that come from the request, ex AFHWSERIES.

                                    for (int f = 0; f < amf.length; f++) {
                                        String flagCode = amf[f].getFlagCode();
                                        String desc = amf[f].getLongDescription();
                                        if (flagCode.equals(str) || desc.equals(str)) {
                                            amf[f].setSelected(true);
                                            bFlag = true;
                                        }
                                    }

                                    if (bFlag && !(att instanceof MultiFlagAttribute)) {
                                        break;
                                    }
                                }
                                try {
                                    fa.put(amf);
                                } catch (EANBusinessRuleException bre) {
                                    bre.printStackTrace();
                                    sbrEx.add(bre.getMessage());
                                    throw sbrEx;

                                }

                            }
                        }
                    }
                }
            }

            // we need to refresh and recalc rows

            tmpTable.refresh();

            for (int i = 0; i < _attList.size(); i++) {

                StringTokenizer st = null;

                String attCode = null;
                String strValue = null;
                int r = -1;

                String strAtt = (String) _attList.getAt(i);
                if (strAtt.indexOf("=") < 0) {
                    continue;
                }

                st = new StringTokenizer(strAtt, "=");
                if (st.countTokens() < 2) {
                    continue;
                }

                int iEqual = strAtt.indexOf("=");
                attCode = strAtt.substring(0,iEqual);
                strValue = strAtt.substring(iEqual+1);

                r = tmpTable.getRowIndex(attCode);
                if (r < 0) {
                    r = tmpTable.getRowIndex(attCode + ":C");
                }

                if (r < 0) {
                    r = tmpTable.getRowIndex(attCode + ":R");
                }

                if (r >= 0 && r < tmpTable.getRowCount()) {
                    EANFoundation ean = tmpTable.getEANObject(r, 1);
                    if (ean instanceof EANAttribute) {
                        EANAttribute att = (EANAttribute) ean;
                        EANMetaAttribute ma = att.getMetaAttribute();
                        if (!ma.isClassified()) {
                            if (att instanceof EANTextAttribute) {
                                try {
                                    EANTextAttribute ta = (EANTextAttribute) att;
                                    ta.put(checkMaxLength(att, strValue));
                                } catch (EANBusinessRuleException bre) {
                                    bre.printStackTrace();
                                    sbrEx.add(bre.getMessage());
                                    throw sbrEx;
                                }
                            } else if (att instanceof EANFlagAttribute) {
                                EANFlagAttribute fa = (EANFlagAttribute) att;
                                MetaFlag[] amf = null;
                                MetaFlag[] amf2 = null;
                                String[] valArray = null;
                                boolean bFlag = false;

                                if (ma.isSelectHelper()) {
                                    amf = (MetaFlag[]) fa.get();
                                }

                                // remove unwanted flag value
                                amf2 = (MetaFlag[]) fa.get();
                                for (int f = 0; f < amf2.length; f++) {
                                    if (amf2[f].isSelected()) {
                                       //System.out.println(strTraceBase + " remove the unwanted flag: " + amf2[f].toString());
                                       fa.put(amf2[f].getFlagCode(), false);
                                   }
                                }

                                valArray = getValueArray(strValue);

                                for (int v = 0; v < valArray.length; v++) {
                                    String str = valArray[v];

                                    if (!fa.putViaDescription(str, true)) {
                                        if (fa.put(str, true)) {
                                            bFlag = true;
                                        }
                                    } else {
                                        bFlag = true;
                                    }

                                    if (bFlag && !(att instanceof MultiFlagAttribute)) {
                                        break;
                                    }
                                }

                                if (ma.isSelectHelper()) {
                                    try {
                                        fa.runAutoSelects(amf);
                                    } catch (EANBusinessRuleException ex) {
                                        ex.printStackTrace();
                                        sbrEx.add(ex.getMessage());
                                        throw sbrEx;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    _db.debug(D.EBUG_SPEW,"EntityType: " + _strEntityType + " Unable to find row for att: " + attCode);
                }
            }

            //      _db.debug(D.EBUG_SPEW, strTraceBase + " checking for any missing data here");
            checkReqInfoMissing(tmpTable);

            //      _db.debug(D.EBUG_SPEW, strTraceBase + " hooking up parent");
            rst.setParentEntityItem(row, _eiParent);

//            _db.debug(D.EBUG_SPEW, strTraceBase + " commiting to pdh");
//            for (int r = 0; r < tmpTable.getRowCount(); r++) {
//                _db.debug(D.EBUG_SPEW, strTraceBase + ":" + r + ":" + tmpTable.getRowKey(r) + ":" + tmpTable.get(r, 1).toString() );
//            }

            try {
                rst.commit(_db);
            } catch (EANBusinessRuleException e) {
                SBRException sbr = new SBRException();
                sbr.add(e.getMessage());
                e.printStackTrace();
                throw sbr;
            } catch (RemoteException re) {
                re.printStackTrace();
            } catch (MiddlewareException _mex) {
                rst.removeRow(rst.getRowCount() - 1);
                throw _mex;
            }

            eiReturn = getEntityItemToReturn(rst, row, _strEntityType);
            if (eiReturn != null) {
                //_db.debug(D.EBUG_SPEW,strTraceBase + " eiReturn : " + eiReturn.dump(false));
                m_sbActivities.append("<ITEM><ACTION>Create</ACTION><ENTITYKEY>" + eiReturn.getKey() + "</ENTITYKEY><ENTITYDISPLAY>" + PDGUtility.convertToHTML(eiReturn.getLongDescription()) + "</ENTITYDISPLAY><PARENT>" + _eiParent.getKey() + ":" + _eiParent.getLongDescription() + "</PARENT></ITEM>" + NEW_LINE);
                // make sure PDHDOMAIN is set
            	// set PDHDOMAIN MN32841099
            	setPDHdomain(_db,  _prof, eiReturn);
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return eiReturn;
    }

    /**
     * dynaSearchII
     *
     * @param _db
     * @param _prof
     * @param _eiParent
     * @param _strSai
     * @param _strEntityType
     * @param _strAttributes
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.SBRException
     * @return
     *  @author David Bigelow
     */
    public EntityItem[] dynaSearchII(Database _db, Profile _prof, EntityItem _eiParent, String _strSai, String _strEntityType, String _strAttributes) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {

        EntityList el = null;
        MetaFlag[] amf = null;

        RowSelectableTable rst = null;
        StringTokenizer st = null;
        StringTokenizer st1 = null;

        String strAttrCode = null;
        String strAttrValue = null;
        String[] valArray = null;
        Hashtable htAtts = new Hashtable();

        String strTraceBase = " PDGUtility dynaSearchII method TRACE";
        SBRException sbrEx = new SBRException();
        SearchActionItem sai = (SearchActionItem) m_saiList.get(_strSai);
        _db.debug(D.EBUG_SPEW, strTraceBase + " SearchActionItem key: " + _strSai + " strEntityType: " + _strEntityType + " Attributes: " + _strAttributes);

        if (sai == null) {
            sai = new SearchActionItem(null, _db, _prof, _strSai, false);
            //m_saiList.put(_strSai, sai);
        }

        rst = (RowSelectableTable)m_saiTable.get(_strSai);

        if (rst == null) {
            rst = sai.getDynaSearchTable(_db);
            //m_saiTable.put(_strSai, rst);
        }
        rst.rollback();

        st = new StringTokenizer(_strAttributes, ";");
        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            int r = 0;
            if (s.substring(0, 3).equals("map")) {
                s = s.substring(4);
                st1 = new StringTokenizer(s, "=");
                if (st1.countTokens() < 2) {
                    continue;
                }

                strAttrCode = st1.nextToken().trim();
                strAttrValue = st1.nextToken().trim();

                valArray = getValueArray(strAttrValue);

                r = rst.getRowIndex(strAttrCode);
                if (r < 0) {
                    r = rst.getRowIndex(strAttrCode + ":P");
                }
                if (r < 0) {
                    r = rst.getRowIndex(strAttrCode + ":C");
                }
                if (r < 0) {
                    r = rst.getRowIndex(strAttrCode + ":R");
                }
                if (r >= 0 && r < rst.getRowCount()) {
                    htAtts.put(r + "", strAttrCode);
                    EANAttribute att = (EANAttribute) rst.getEANObject(r, 1);
                    if (att instanceof EANTextAttribute) {
                        try {
                            rst.put(r, 1, strAttrValue);
                        } catch (EANBusinessRuleException bre) {
                            bre.printStackTrace();
                            sbrEx.add(bre.getMessage());
                            throw sbrEx;
                        }
                    } else if (att instanceof EANFlagAttribute) {
                        boolean bFlag = false;
                        EANFlagAttribute fa = (EANFlagAttribute) att;
                        //EANMetaAttribute ma = fa.getMetaAttribute();

                        amf = (MetaFlag[]) fa.get();
                        for (int v = 0; v < valArray.length; v++) {
                            String val = valArray[v];

                            // need to use this put method for classified attribute,
                            // for some classified attribute, I only have longdescription as inputs that come from the request, ex AFHWSERIES.

                            for (int f = 0; f < amf.length; f++) {
                                String flagCode = amf[f].getFlagCode();
                                String desc = amf[f].getLongDescription();
                                if (flagCode.equals(val) || desc.equals(val)) {
                                    amf[f].setSelected(true);
                                    bFlag = true;
                                }
                            }

                            if (bFlag && !(att instanceof MultiFlagAttribute)) {
                                break;
                            }
                        }

                        if (!bFlag) {
                            //_db.debug(D.EBUG_SPEW,strTraceBase + " can't input for flag attribute " + s);
                            sbrEx.add("Unable to input for flag attribute " + s);
                            throw sbrEx;
                        }
                        try {
                            fa.put(amf);
                        } catch (EANBusinessRuleException bre) {
                            bre.printStackTrace();
                            sbrEx.add(bre.getMessage());
                            throw sbrEx;
                        }
                    }
                } else {
                    sbrEx.add("EntityType: " + _strEntityType + " Unable to search on att: " + strAttrCode);
                    throw sbrEx;
                }
            }
        }

        // remove unwanted values
        for (int r=0; r < rst.getRowCount(); r++) {
            if (htAtts.get(r + "") == null) {
                EANAttribute att = (EANAttribute) rst.getEANObject(r, 1);
                if (att instanceof EANTextAttribute) {
                    try {
                        rst.put(r, 1, "");
                    } catch (EANBusinessRuleException bre) {
                        bre.printStackTrace();
                        sbrEx.add(bre.getMessage());
                        throw sbrEx;
                    }
                } else if (att instanceof EANFlagAttribute) {

                    EANFlagAttribute fa = (EANFlagAttribute) att;
                    //EANMetaAttribute ma = fa.getMetaAttribute();

                    MetaFlag[] amf2 = (MetaFlag[]) fa.get();
                    for (int f = 0; f < amf2.length; f++) {
                        if (amf2[f].isSelected()) {
            	            fa.put(amf2[f].getFlagCode(), false);
                        }
                    }
                }
            }
        }

        sai.setCheckLimit(false);
        sai.disableUIAfterCache();

        _prof = setProfValOnEffOn(_db, _prof);
        el = sai.executeAction(_db, _prof);
        EntityItem[] aeiReturn = getEntityItemAsArray(el, _strEntityType);
        el = null;
        htAtts.clear();

        return aeiReturn;
    }

    /**
     * dynaSearch
     *
     * @param _db
     * @param _prof
     * @param _eiParent
     * @param _strSai
     * @param _strEntityType
     * @param _strAttributes
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.SBRException
     * @return
     *  @author David Bigelow
     */
    public EntityItem[] dynaSearch(Database _db, Profile _prof, EntityItem _eiParent, String _strSai, String _strEntityType, String _strAttributes) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {

		//System.out.println("**********************Starting PDG dynaSearch**********************");
		//System.out.println("_eiParent:" + _eiParent + "_strSai:" + _strSai + "_strEntityType:" + _strEntityType + "_strAttributes:" + _strAttributes);
        String strTraceBase = " PDGUtility dynaSearch method TRACE";

        RowSelectableTable rst = null;
        StringTokenizer st = null;
        StringTokenizer st1 = null;
        EntityList el = null;
        String strAttrCode = null;
        String strAttrValue = null;
        String[] valArray = null;
        Hashtable htAtts = new Hashtable();

        SBRException sbrEx = new SBRException();

        SearchActionItem sai = (SearchActionItem) m_saiList.get(_strSai);

        _db.debug(D.EBUG_SPEW, strTraceBase + " SearchActionItem key: " + _strSai + " strEntityType: " + _strEntityType + " Attributes: " + _strAttributes);

        if (sai == null) {
            sai = new SearchActionItem(null, _db, _prof, _strSai, false);
            //m_saiList.put(_strSai, sai);
        }

        rst = (RowSelectableTable)m_saiTable.get(_strSai);

        if (rst == null) {
            rst = sai.getDynaSearchTable(_db);
           // m_saiTable.put(_strSai, rst);
            if(rst==null){
            	throw new MiddlewareException("Error getting table for search action: "+sai.getActionItemKey());
            }
        }

        rst.rollback();

        st = new StringTokenizer(_strAttributes, ";");
        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            int r = 0;

            if (s.substring(0, 3).equals("map")) {
                s = s.substring(4);
                st1 = new StringTokenizer(s, "=");
                if (st1.countTokens() < 2) {
                    continue;
                }

                strAttrCode = st1.nextToken().trim();
                strAttrValue = st1.nextToken().trim();

                valArray = getValueArray(strAttrValue);
                _db.debug(D.EBUG_SPEW,strTraceBase+"Checking :"+_strEntityType + ":" + strAttrCode+":"+strAttrValue);
                r = rst.getRowIndex(_strEntityType + ":" + strAttrCode);
                if (r < 0) {
                    r = rst.getRowIndex(_strEntityType + ":" + strAttrCode + ":C");
                }
                if (r < 0) {
                    r = rst.getRowIndex(_strEntityType + ":" + strAttrCode + ":R");
                }

                if (r >= 0 && r < rst.getRowCount()) {
                    htAtts.put(r + "", _strEntityType + ":" + strAttrCode);
                    EANAttribute att = (EANAttribute) rst.getEANObject(r, 1);
                    if (att instanceof EANTextAttribute) {
                        try {
                            rst.put(r, 1, strAttrValue);
                        } catch (EANBusinessRuleException bre) {
                            bre.printStackTrace();
                            sbrEx.add(bre.getMessage());
                            throw sbrEx;
                        }
                    } else if (att instanceof EANFlagAttribute) {
                        boolean bFlag = false;
                        MetaFlag[] amf = null;
                        EANFlagAttribute fa = (EANFlagAttribute) att;
                        //EANMetaAttribute ma = fa.getMetaAttribute();

                        amf = (MetaFlag[]) fa.get();
                        for (int v = 0; v < valArray.length; v++) {
                            String val = valArray[v];

                            // need to use this put method for classified attribute,
                            // for some classified attribute, I only have longdescription as inputs that come from the request, ex AFHWSERIES.
                            _db.debug(D.EBUG_SPEW,strTraceBase+"Checking Flag Attribute matches for "+att.getAttributeCode());
                            for (int f = 0; f < amf.length; f++) {
                                String flagCode = amf[f].getFlagCode();
                                String desc = amf[f].getLongDescription();
//                                _db.debug(D.EBUG_SPEW,strTraceBase+"Val="+val+":flagcode="+flagCode+":desc="+desc);
                                if (flagCode.equals(val) || desc.equals(val)) {
                                    amf[f].setSelected(true);
                                    bFlag = true;
                                }
                            }

                            if (bFlag && !(att instanceof MultiFlagAttribute)) {
                                break;
                            }
                        }
                        if (!bFlag) {

                            sbrEx.add("Unable to input for flag attribute " + s);
                            throw sbrEx;
                        }

                        try {
                            fa.put(amf);
                        } catch (EANBusinessRuleException bre) {
                            bre.printStackTrace();
                            sbrEx.add(bre.getMessage());
                            throw sbrEx;
                        }
                    }
                } else {
                    sbrEx.add("EntityType: " + _strEntityType + " Unable to search on att: " + strAttrCode);
                    throw sbrEx;
                }
            }
        }

        // remove unwanted values
        for (int r=0; r < rst.getRowCount(); r++) {
            if (htAtts.get(r + "") == null) {
                EANAttribute att = (EANAttribute) rst.getEANObject(r, 1);
                if (att instanceof EANTextAttribute) {
                    try {
                        rst.put(r, 1, "");
                    } catch (EANBusinessRuleException bre) {
                        bre.printStackTrace();
                        sbrEx.add(bre.getMessage());
                        throw sbrEx;
                    }
                } else if (att instanceof EANFlagAttribute) {

                    EANFlagAttribute fa = (EANFlagAttribute) att;
                    //EANMetaAttribute ma = fa.getMetaAttribute();

                    MetaFlag[] amf2 = (MetaFlag[]) fa.get();
                    for (int f = 0; f < amf2.length; f++) {
                        if (amf2[f].isSelected()) {
            	            fa.put(amf2[f].getFlagCode(), false);
                        }
                    }
                }
            }
        }

        sai.setCheckLimit(false);
        sai.disableUIAfterCache();
        _prof = setProfValOnEffOn(_db, _prof);

        el = sai.executeAction(_db, _prof);

//        return getEntityItemAsArray(el, _strEntityType);
		//System.out.println("el:" + el.dump(false) + " _strEntityType:" + _strEntityType);
        EntityItem[] aeiReturn = getEntityItemAsArray(el, _strEntityType);
        el = null;

        htAtts.clear();
		//System.out.println("**********************Done PDG dynaSearch**********************");
        return aeiReturn;
    }

    /**
     * checkOptFeatureIDFormat
     *
     * @param _s
     * @param _type
     * @param _bProduct
     * @param _sbr
     * @return
     *  @author David Bigelow
     */
    public SBRException checkOptFeatureIDFormat(String _s, int _type, boolean _bProduct, SBRException _sbr) {

        String str = null;
        char c;

        if (_s == null || _s.length() != 3) {
            _sbr.add("OPTFEATUREID " + _s + ": length is not equal 3");
        }

        str = _s.substring(1);
        if (!isDigit(str)) {
            _sbr.add("OPTFEATUREID " + _s + ": last 2 characters are not digits");
        } else {
            int i = Integer.parseInt(str);
            if (i > 41) {
                _sbr.add("OPTFEATUREID " + _s + ": last 2 characters have to be less than 42");
            }
        }

        if (_bProduct && (!str.equals("00"))) {
            _sbr.add("Last 2 digits of OPTFEATUREID has to be 00");
        } else if (!_bProduct && (str.equals("00"))) {
            _sbr.add("Last 2 digits of OPTFEATUREID has to be different from 00");
        }

        c = _s.charAt(0);
        switch (_type) {
        case OF_PRODUCT :
            if (c != 'f') {
                _sbr.add("OPTFEATUREID " + _s + ": is not in format fxx");
            }
            break;
        case OF_SUBSCRIPTION :
            if (c != 's') {
                _sbr.add("OPTFEATUREID " + _s + ": is not in format sxx");
            }
            break;
        case OF_MAINTENANCE :
            if (c != 'm') {
                _sbr.add("OPTFEATUREID " + _s + ": is not in format mxx");
            }
            break;
        case OF_SUPPORT :
            if (c != 'p') {
                _sbr.add("OPTFEATUREID " + _s + ": is not in format pxx");
            }
            break;
        default :
            break;
        }
        return _sbr;
    }

    /**
     * findHWHipoMG
     *
     * @param _db
     * @param _prof
     * @param _eiCOF
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @return
     *  @author David Bigelow
     */
    public EntityItem findHWHipoMG(Database _db, Profile _prof, EntityItem _eiCOF) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
        EntityItem eiReturn = null;

        // Find Hardware-HipoSupply COFOOFMGMTGRP
        EntityItem[] aeiCOMOF = { _eiCOF };
        NavActionItem nai = new NavActionItem(null, _db, _prof, "NAVCOFOOFMGMTGRP1");
        EntityList el = EntityList.getEntityList(_db, _prof, nai, aeiCOMOF);
        for (int i = 0; i < el.getEntityGroupCount(); i++) {
            EntityGroup eg = el.getEntityGroup(i);
            if (eg.getEntityType().equals("COFOOFMGMTGRP")) {
                for (int j = 0; j < eg.getEntityItemCount(); j++) {
                    eiReturn = eg.getEntityItem(j);
                    return eiReturn;
                }
            }
        }

        return eiReturn;
    }

    /**
     * sepLongText
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public Vector sepLongText(String _s) {
        Vector vecReturn = new Vector();
        StringTokenizer st = new StringTokenizer(_s, NEW_LINE + "");
        while (st.hasMoreElements()) {
            String str = st.nextToken().trim();
            vecReturn.addElement(str);
        }
        return vecReturn;
    }

    /**
     * getSearchString
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public StringBuffer getSearchString(String _s) {
        StringBuffer sbReturn = new StringBuffer();
        StringTokenizer st = new StringTokenizer(_s, ";");
        while (st.hasMoreTokens()) {
            sbReturn.append("map_" + st.nextToken().trim() + ";");
        }

        return sbReturn;
    }

    /**
     * checkSuppProdString
     *
     * @param _s
     * @param _sbrex
     * @return
     *  @author David Bigelow
     */
    public SBRException checkSuppProdString(String _s, SBRException _sbrex) {
        StringTokenizer st = new StringTokenizer(_s, NEW_LINE + "");
        while (st.hasMoreElements()) {
            String str = st.nextToken().trim();
            int iSep = str.indexOf("-");
            if (iSep < 0) {
                _sbrex.add(str + " is not in format pppp-ppp");
            } else {
                String strMachType = str.substring(0, iSep);
                String strModel = str.substring(iSep + 1);
                if (strMachType.length() > 4) {
                    _sbrex.add(strMachType + " MACHTYPE length has to be 4");
                }

                if (strModel.length() != 3) {
                    _sbrex.add(strModel + " MODEL length has to be 3");
                }
            }
        }
        return _sbrex;
    }

    /**
     * getAttributeListForUpdate
     *
     * @param _strEntityType
     * @param _strAttributes
     * @return
     *  @author David Bigelow
     */
    public OPICMList getAttributeListForUpdate(String _strEntityType, String _strAttributes) {

        StringTokenizer stAtt = null;

        OPICMList attList = new OPICMList();

        StringTokenizer stTemp = new StringTokenizer(_strAttributes, ";");

        // prepare list of entity attribute
        while (stTemp.hasMoreTokens()) {
            String str = stTemp.nextToken();
            // take out the map prefix
            if (str.indexOf("map") > -1) {
                str = str.substring(4);
            }
            stAtt = new StringTokenizer(str, "=");
            if (stAtt.hasMoreTokens()) {
                String strCode = stAtt.nextToken();
                attList.put(_strEntityType + ":" + strCode, str);
            }
        }
        return attList;
    }

    /**
     * getAttributeListForCreate
     *
     * @param _strEntityType
     * @param _strAttributes
     * @param _strRelatorInfo
     * @return
     *  @author David Bigelow
     */
    public OPICMList getAttributeListForCreate(String _strEntityType, String _strAttributes, String _strRelatorInfo) {
       // String strTraceBase = "PDGUtility getAttributeListForCreate ";
        String strRelatorType = null;
        String strRelatorAttr = null;
        StringTokenizer stAtt = null;

        int iAttrO = -1;
        int iAttrC = -1;

        OPICMList attList = new OPICMList();
        StringTokenizer stTemp = new StringTokenizer(_strAttributes, ";");

        // prepare list of entity attribute
        while (stTemp.hasMoreTokens()) {
            String str = stTemp.nextToken();
            // take out the map prefix
            if (str.indexOf("map") > -1) {
                str = str.substring(4);
            }
            stAtt = new StringTokenizer(str, "=");
            if (stAtt.hasMoreTokens()) {
                String strCode = stAtt.nextToken();
                attList.put(_strEntityType + ":" + strCode, _strEntityType + ":" + str);
            }
        }

        // prepare list of relator attribute
        iAttrO = _strRelatorInfo.indexOf("[");
        iAttrC = _strRelatorInfo.indexOf("]");
        if (iAttrO > -1) {
            strRelatorType = _strRelatorInfo.substring(0, iAttrO);
            strRelatorAttr = _strRelatorInfo.substring(iAttrO + 1, (iAttrC > -1 ? iAttrC : _strRelatorInfo.length()));
        } else {
            strRelatorType = _strRelatorInfo;
        }

        if (strRelatorAttr != null && strRelatorAttr.length() > 0) {

            //prepare the list of attributes
            stTemp = new StringTokenizer(strRelatorAttr, ";");
            while (stTemp.hasMoreTokens()) {
                String str = stTemp.nextToken();
                // take out the map prefix
                if (str.indexOf("map") > -1) {
                    str = str.substring(4);
                }
                stAtt = new StringTokenizer(str, "=");
                if (stAtt.hasMoreTokens()) {
                    String strCode = stAtt.nextToken();
                    attList.put(strRelatorType + ":" + strCode, strRelatorType + ":" + str);
                }
            }
        }

        return attList;
    }

    /**
     * checkGenAreaOverlap
     *
     * @param _geoVec
     * @param _sbrex
     * @return
     *  @author David Bigelow
     */
    public SBRException checkGenAreaOverlap(Vector _geoVec, SBRException _sbrex) {
        boolean bFoundWW = false;
        for (int i = 0; i < _geoVec.size(); i++) {
            String s = (String) _geoVec.elementAt(i);
            if (s.equals("Worldwide")) {
                bFoundWW = true;
                break;
            }
        }

        if (bFoundWW && _geoVec.size() > 1) {
            _sbrex.add("Overlapping General areas are selected");
        }

        return _sbrex;
    }

    /**
     * setProfValOnEffOn
     *
     * @param _db
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public Profile setProfValOnEffOn(Database _db, Profile _prof) throws MiddlewareException {
        DatePackage dpNow = _db.getDates();
        String strTimeStampNow = dpNow.getNow();
        _prof.setValOn(strTimeStampNow);
        _prof.setEffOn(strTimeStampNow);
        _prof.setNow(strTimeStampNow);
        _prof.setEndOfDay(dpNow.getEndOfDay());
        return _prof;
    }

    /**
     * setProfValOnEffOn
     *
     * @param _rdi
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public Profile setProfValOnEffOn(RemoteDatabaseInterface _rdi, Profile _prof) throws MiddlewareException {
        ReturnDataResultSetGroup ld = null;
        String strTimeStampNow = null;
        try {
            ld = _rdi.remoteGBL2028();
            strTimeStampNow = ld.getColumn(0, 0, 0);
            _prof.setValOn(strTimeStampNow);
            _prof.setEffOn(strTimeStampNow);
            _prof.setNow(strTimeStampNow);
            return _prof;
        } catch (Exception _ce) {
            _ce.printStackTrace();
        }
        return null;
    }

    private String adjustErrorMessage(String _strOriginalEM, String _strErrorMessage, String _strPDGAction) {

        int iAction = -1;

        int iBracket = _strOriginalEM.indexOf("{");
        if (iBracket < 0) {
            _strOriginalEM = "";
        }
        iAction = _strOriginalEM.indexOf("{" + _strPDGAction);

        while (iAction >= 0) {
            String str1 = _strOriginalEM.substring(0, iAction);
            String str2 = _strOriginalEM.substring(iAction);
            int iEndAction = str2.indexOf("}");
            if (iEndAction >= 0) {
                str2 = str2.substring(iEndAction + 1);
            } else {
                str2 = "";
            }
            _strOriginalEM = str1 + str2;
            iAction = _strOriginalEM.indexOf("{" + _strPDGAction);
        }

        if (_strErrorMessage.length() > 0) {
            _strOriginalEM = _strOriginalEM + "{" + _strPDGAction + ":" + _strErrorMessage + "}";
        }
        return _strOriginalEM;
    }

    /**
     * savePDGStatusXML
     *
     * @param _db
     * @param _prof
     * @param _ei
     * @param _strStatus
     * @param _strMsg
     * @param _strPDGAction
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws java.sql.SQLException
     *  @author David Bigelow
     */
    public void savePDGStatusXML(Database _db, Profile _prof, EntityItem _ei, String _strStatus, String _strMsg, String _strPDGAction) throws MiddlewareException, MiddlewareRequestException, SQLException {

        String strExt = null;
        Blob blob = null;
        byte[] baValue = null;

        EANMetaAttribute ma = null;
        EANAttribute attEM = null;
        String strOriginalEM = null;
        StringBuffer sb = null;

        String strEnterprise = _prof.getEnterprise();
        int iOPWGID = _prof.getOPWGID();
        int iTranID = _prof.getTranID();
        int iNLSID = _prof.getReadLanguage().getNLSID();
        String strEntityType = _ei.getEntityType();
        int iEntityID = _ei.getEntityID();

        try {

            //String strNow = _prof.getNow(); this is time profile was instantiated, could be several days ago
            // Set up current time
            DatePackage dp = _db.getDates();
            String strNow = dp.getNow();
            String strForever = Profile.FOREVER;
            ControlBlock cbOn = new ControlBlock(strNow, strForever, strNow, strForever, iOPWGID, iTranID);
            ControlBlock cbOff = new ControlBlock(strNow, strNow, strNow, strNow, iOPWGID, iTranID);

            Vector vctReturnEntityKeys = new Vector();
            Vector vctAtts = new Vector();
            ReturnEntityKey rek = new ReturnEntityKey(strEntityType, iEntityID, true);
            EntityGroup eg = (EntityGroup) m_egList.get(strEntityType);

            if (eg == null) {
                // need true to get metacolumnorder
                eg = new EntityGroup(null, _db, _prof, strEntityType, "Edit", true);
                m_egList.put(eg.getEntityType(), eg);
            }

            ma = eg.getMetaAttribute("AFPDGSTATUS");
            attEM = _ei.getAttribute("AFPDGERRORMSG");
            strOriginalEM = "";
            if (attEM != null) {
                strOriginalEM = attEM.toString();
            }
            if (ma != null) {
                Text sf = new Text(strEnterprise, strEntityType, iEntityID, "AFPDGSTATUS", _strStatus + _strPDGAction, iNLSID, cbOn);
                if (sf != null) {
                    vctAtts.addElement(sf);
                }
            }

            if (_strStatus.equals(STATUS_PASSED)) {
                ma = eg.getMetaAttribute("AFPDGERRORMSG");
                if (ma != null) {
                    String strEM = adjustErrorMessage(strOriginalEM, _strMsg, _strPDGAction);
                    if (strEM.length() > 0) {
                        LongText lt = new LongText(strEnterprise, strEntityType, iEntityID, "AFPDGERRORMSG", strEM, iNLSID, cbOn);
                        if (lt != null) {
                            vctAtts.addElement(lt);
                        }
                    } else {
                        LongText lt = new LongText(strEnterprise, strEntityType, iEntityID, "AFPDGERRORMSG", "no error", iNLSID, cbOff);
                        if (lt != null) {
                            vctAtts.addElement(lt);
                        }
                    }
                }
            } else if (_strStatus.equals(STATUS_ERROR)) {
                ma = eg.getMetaAttribute("AFPDGERRORMSG");
                if (ma != null && _strMsg.length() > 0) {
                    String strEM = adjustErrorMessage(strOriginalEM, _strMsg, _strPDGAction);
                    if (strEM.length() > 0) {
                        LongText lt = new LongText(strEnterprise, strEntityType, iEntityID, "AFPDGERRORMSG", strEM, iNLSID, cbOn);
                        if (lt != null) {
                            vctAtts.addElement(lt);
                        }
                    } else {
                        LongText lt = new LongText(strEnterprise, strEntityType, iEntityID, "AFPDGERRORMSG", "no error", iNLSID, cbOff);
                        if (lt != null) {
                            vctAtts.addElement(lt);
                        }
                    }
                }
            } else if (_strStatus.equals(STATUS_COMPLETE)) {
                ma = eg.getMetaAttribute("AFPDGERRORMSG");
                if (ma != null && _strMsg.length() > 0) {
                    String strEM = adjustErrorMessage(strOriginalEM, "", _strPDGAction);
                    if (strEM.length() > 0) {
                        LongText lt = new LongText(strEnterprise, strEntityType, iEntityID, "AFPDGERRORMSG", strEM, iNLSID, cbOn);
                        if (lt != null) {
                            vctAtts.addElement(lt);
                        }
                    } else {
                        LongText lt = new LongText(strEnterprise, strEntityType, iEntityID, "AFPDGERRORMSG", "no error", iNLSID, cbOff);
                        if (lt != null) {
                            vctAtts.addElement(lt);
                        }
                    }
                }

                ma = eg.getMetaAttribute("AFPDGRESULT");
                if (ma != null && _strMsg.length() > 0) {
                    EANAttribute resultAtt = _ei.getAttribute("AFPDGRESULT");
                    String strPrevValue = "";
                    if (resultAtt != null) {
                        EANBlobAttribute att = (EANBlobAttribute) resultAtt;
                        byte[] ba = att.getBlobValue(null, _db);
                        strPrevValue = new String(ba);
                    }

                    sb = new StringBuffer();
                    sb.append("<DOCUMENT>" + NEW_LINE);
                    sb.append(NEW_LINE + "<RUN>" + NEW_LINE);
                    sb.append(NEW_LINE + "<TIMESTAMP>" + strNow + "</TIMESTAMP>" + NEW_LINE);
                    sb.append(getPDGEntityXMLDisplay(eg, _ei));
                    sb.append(_strMsg);
                    sb.append("</RUN>" + NEW_LINE);
                    sb.append("</DOCUMENT>" + NEW_LINE);

                    baValue = null;
                    try {
                        StringBuffer sbTemp = new StringBuffer();
                    	if (strPrevValue.length()>MAX_RESULT_LEN){
                    		// find first <hr and remove before it
                    		int id = strPrevValue.indexOf("<hr");
                    		if (id!=-1){
                    			String endValue = strPrevValue.substring(id);
                    			// get end tag
                    			id = endValue.indexOf(">");
                    			if (id != -1){
                    				strPrevValue = endValue.substring(id+1);
                    			}
                    		}
                    	}
                        sbTemp.append(strPrevValue);
                        sbTemp.append(getPDGReportHTMLfromXML(sb.toString()));
                        baValue = sbTemp.toString().getBytes();
                    } catch (Exception ex) {
                        ex.getMessage();
                        baValue = sb.toString().getBytes();
                    }

                    strExt = _ei.getKey() + ".html";
                    blob = new Blob(strEnterprise, strEntityType, iEntityID, "AFPDGRESULT", baValue, strExt, iNLSID, cbOn);
                    if (blob != null) {
                        vctAtts.addElement(blob);
                    }
                }
            }

            rek.m_vctAttributes = vctAtts;
            vctReturnEntityKeys.addElement(rek);

            _db.update(_prof, vctReturnEntityKeys, false, true);
        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * savePDGViewXML
     *
     * @param _db
     * @param _prof
     * @param _ei
     * @param _strMsg
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws java.sql.SQLException
     *  @author David Bigelow
     */
    public void savePDGViewXML(Database _db, Profile _prof, EntityItem _ei, String _strMsg) throws MiddlewareException, MiddlewareRequestException, SQLException {

        String strExt = null;
        Blob blob = null;
        EANMetaAttribute ma = null;
        byte[] baValue = null;

        String strEnterprise = _prof.getEnterprise();
        int iOPWGID = _prof.getOPWGID();
        int iTranID = _prof.getTranID();
        int iNLSID = _prof.getReadLanguage().getNLSID();
        String strEntityType = _ei.getEntityType();
        int iEntityID = _ei.getEntityID();

        StringBuffer sb = new StringBuffer();

        try {
            DatePackage dpNow = _db.getDates();
            String strNow = dpNow.getNow();
            String strForever = dpNow.getForever();
            ControlBlock cbOn = new ControlBlock(strNow, strForever, strNow, strForever, iOPWGID, iTranID);

            Vector vctReturnEntityKeys = new Vector();
            Vector vctAtts = new Vector();
            ReturnEntityKey rek = new ReturnEntityKey(strEntityType, iEntityID, true);

            EntityGroup eg = (EntityGroup) m_egList.get(strEntityType);
            if (eg == null) {
                // need to be true to get metacolumnorder
                eg = new EntityGroup(null, _db, _prof, strEntityType, "Edit", true);
                m_egList.put(eg.getEntityType(), eg);
            }

            ma = eg.getMetaAttribute("AFPDGVIEW");
            if (ma == null) {
                return;
            }

            if (_strMsg.length() > 0) {
                EANAttribute resultAtt = _ei.getAttribute("AFPDGVIEW");
                String strPrevValue = "";
                if (resultAtt != null) {
                    EANBlobAttribute att = (EANBlobAttribute) resultAtt;
                    byte[] ba = att.getBlobValue(null, _db);
                    strPrevValue = new String(ba);
                }

                sb = new StringBuffer();
                sb.append("<DOCUMENT>" + NEW_LINE);
                sb.append(NEW_LINE + "<RUN>" + NEW_LINE);
                sb.append(NEW_LINE + "<TIMESTAMP>" + strNow + "</TIMESTAMP>" + NEW_LINE);
                sb.append(getPDGEntityXMLDisplay(eg, _ei));
                sb.append(_strMsg);
                sb.append("</RUN>" + NEW_LINE);
                sb.append("</DOCUMENT>" + NEW_LINE);

                baValue = null;
                try {
                    StringBuffer sbTemp = new StringBuffer();
                	if (strPrevValue.length()>MAX_RESULT_LEN){
                		// find first <hr and remove before it
                		int id = strPrevValue.indexOf("<hr");
                		if (id!=-1){
                			String endValue = strPrevValue.substring(id);
                			// get end tag
                			id = endValue.indexOf(">");
                			if (id != -1){
                				strPrevValue = endValue.substring(id+1);
                			}
                		}
                	}
                    sbTemp.append(strPrevValue);
                    sbTemp.append(getPDGReportHTMLfromXML(sb.toString()));
                    baValue = sbTemp.toString().getBytes();
                } catch (Exception ex) {
                    ex.getMessage();
                    baValue = sb.toString().getBytes();
                }

                strExt = _ei.getKey() + "VIEW.html";

                blob = new Blob(strEnterprise, strEntityType, iEntityID, "AFPDGVIEW", baValue, strExt, iNLSID, cbOn);
                if (blob != null) {
                    vctAtts.addElement(blob);
                }
            }

            rek.m_vctAttributes = vctAtts;
            vctReturnEntityKeys.addElement(rek);
            //updating entity items
            _db.update(_prof, vctReturnEntityKeys);

        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * getViewXMLString
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public String getViewXMLString(String _s) {
        StringTokenizer st = new StringTokenizer(_s, NEW_LINE + "");
        StringBuffer sb = new StringBuffer();

        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            sb.append("<ITEM>");
            if (s.indexOf("|") >= 0) {
                StringTokenizer st1 = new StringTokenizer(s, "|");
                int cnt = st1.countTokens();
                if (st1.hasMoreTokens()) {
                    String strParentEntity = st1.nextToken().trim();
                    int iLevel = Integer.parseInt(st1.nextToken().trim());
                    String strEntity = st1.nextToken().trim();
                    if (cnt == 6){ // this is direction, bypass it
                    	strEntity = st1.nextToken().trim();
					}
                    String strAction = st1.nextToken().trim();
                    String strRelatorInfo = st1.nextToken().trim();
                    sb.append("<PARENT>" + strParentEntity + "</PARENT>");
                    sb.append("<LEVEL>" + iLevel + "</LEVEL>");
                    sb.append("<ENTITY>" + strEntity + "</ENTITY>");
                    sb.append("<ACTION>" + strAction + "</ACTION>");
                    sb.append("<RELATOR>" + strRelatorInfo + "</RELATOR>");
                }
            } else {
                sb.append(s);
            }
            sb.append("</ITEM>" + NEW_LINE);
        }
        return sb.toString();
    }

    /**
     * getPDGEntityXMLDisplay
     *
     * @param _eg
     * @param _ei
     * @return
     *  @author David Bigelow
     */
    public String getPDGEntityXMLDisplay(EntityGroup _eg, EntityItem _ei) {
        StringBuffer sb = new StringBuffer();
        MetaColumnOrderGroup mcog = _eg.getMetaColumnOrderGroup();
        RowSelectableTable rst = _ei.getEntityItemTable();
        sb.append("<PDGENTITY type=\"" + _ei.getKey() + "\">");
        if (mcog != null) {

            for (int i = 0; i < mcog.getMetaColumnOrderItemCount(); i++) {
                MetaColumnOrderItem mcoi = mcog.getMetaColumnOrderItem(i);
                String strAttrCode = mcoi.getAttributeCode();
                EANAttribute att = _ei.getAttribute(strAttrCode);
                sb.append("<row><col>" + mcoi.getAttributeDescription() + "</col>");
                sb.append("<col>");
                if (att != null) {
                    sb.append(att.toString());
                }
                sb.append("</col></row>");
            }
        } else {

            for (int r = 0; r < rst.getRowCount(); r++) {
                sb.append("<row>");
                for (int c = 0; c < rst.getColumnCount(); c++) {
                    String s = (String) rst.get(r, c, true);
                    sb.append("<col>");
                    sb.append(s);
                    sb.append("</col>");
                }
                sb.append("</row>");
            }
        }

        // add uplink
        sb.append("<row>");
        sb.append("<col>Up Link Items</col><col>");
        for (int ii = 0; ii < _ei.getUpLinkCount(); ii++) {
            EANEntity ent = _ei.getUpLink(ii);
            sb.append((ii != 0 ? ", " : "") + ent.getKey());
        }
        sb.append("</col></row>");
        sb.append("<row><col>Down Link Items</col><col>");
        for (int ii = 0; ii < _ei.getDownLinkCount(); ii++) {
            EANEntity ent = _ei.getDownLink(ii);
            sb.append((ii != 0 ? ", " : "") + ent.getKey());
        }
        sb.append("</col></row>");
        sb.append("</PDGENTITY>" + NEW_LINE);
        return sb.toString();
    }

    /**
     * resetActivities
     *
     *  @author David Bigelow
     */
    public void resetActivities() {
        m_sbActivities = new StringBuffer();
    }

    /**
     * getActivities
     *
     * @return
     *  @author David Bigelow
     */
    public StringBuffer getActivities() {
        return m_sbActivities;
    }

    /**
     * createAndCopy
     *
     * @param _db
     * @param _prof
     * @param _eiFrom
     * @param _strToEntityType
     * @param _strExcludeCopy
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws java.sql.SQLException
     * @return
     *  @author David Bigelow
     */
    public EntityItem createAndCopy(Database _db, Profile _prof, EntityItem _eiFrom, String _strToEntityType, String _strExcludeCopy)
    	throws MiddlewareException, MiddlewareRequestException, SQLException,SBRException
    {

        EntityItem eiReturn = null;
        String strEntityType = null;
        int iEntityID = -1;
        int iEntityIDNew = -1;

        ReturnStatus returnStatus = new ReturnStatus(-1);
        ReturnID idNew = new ReturnID(0);
        String strEnterprise = _prof.getEnterprise();
        int iOPWGID = _prof.getOPWGID();
        int iTranID = _prof.getTranID();
        int iSessionID = _prof.getSessionID();
        String strRoleCode = _prof.getRoleCode();
		int iNLSID = _prof.getReadLanguage().getNLSID();

        if (_eiFrom == null) {
            return null;
        }

        strEntityType = _eiFrom.getEntityType();
        iEntityID = _eiFrom.getEntityID();

        try {
            DatePackage dpNow = _db.getDates();
            String strNow = dpNow.getNow();
            String strForever = dpNow.getForever();
            String strEndOfDay = dpNow.getEndOfDay();

            // Make the entity
            idNew = _db.callGBL2092(returnStatus, iOPWGID, iSessionID, strEnterprise, _strToEntityType, new ReturnID(0), iTranID, strNow, strForever, iNLSID);
            _db.freeStatement();
            _db.isPending();

            // Set the new iEntity2ID
            iEntityIDNew = idNew.intValue();

            // Copy the attributes .. what about default values?
            _db.callGBL2268(returnStatus, iOPWGID, strEnterprise, strRoleCode, strEntityType, iEntityID, _strToEntityType, iEntityIDNew, iTranID, strEndOfDay, strEndOfDay, _strExcludeCopy);
            _db.freeStatement();
            _db.isPending();

            eiReturn = new EntityItem(null, _prof, _strToEntityType, iEntityIDNew);

            // set PDHDOMAIN MN32841099
            setPDHdomain(_db,  _prof, eiReturn);

        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
        return eiReturn;
    }

    /**
     * setPDHdomain MN32841099
     * change from relator to association for WGMODEL prevents access to WG from entity now
     * set pdhdomain so association can find the new entities
     *
     * @param _db
     * @param _prof
     * @param eiReturn
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws java.sql.SQLException, SBRException
     */
    public void setPDHdomain(Database _db, Profile _prof, EntityItem eiReturn)
    throws MiddlewareException, MiddlewareRequestException, SQLException,SBRException
    {
		String attrCode = "PDHDOMAIN";
		String eidomain = getAttrValue(eiReturn, attrCode);
        _db.debug(D.EBUG_SPEW,"PDGUtility.setPDHdomain(): entered for "+eiReturn.getKey());
        if (eidomain != null && eidomain.length()>0){
        	// don't overwrite any values set in the template
            _db.debug(D.EBUG_SPEW,"PDGUtility.setPDHdomain(): PDHDOMAIN already set to "+
            		eidomain+" for "+eiReturn.getEntityType());
        	return;
        }
		OPICMList attList = new OPICMList();

        //  Get the entityid
        int iEntityID = _prof.getDefaultIndex();
        String strAttrValue = null;
        //
        // go make the default entity
        //
        EntityGroup eg = new EntityGroup(null, _db, _prof, eiReturn.getEntityType(), "Edit", false);
        EANMetaAttribute ma = eg.getMetaAttribute(attrCode);
        if (ma != null) {
	        EntityItem defItem = new EntityItem(eg, null, _db, eiReturn.getEntityType(), iEntityID);
			strAttrValue = getAttrValue(defItem, attrCode);
			if (strAttrValue ==null || strAttrValue.length()==0){
				// get it from the profile
				strAttrValue = _prof.getPDHDomainFlagCodes();
			}else{
				// entityitem does not have PDHDOMAIN attribute
            	_db.debug(D.EBUG_SPEW,"PDGUtility.setPDHdomain(): "+strAttrValue+" value for PDHDOMAIN for "+defItem.getKey());
			}
		}else{
			// entitytype does not have PDHDOMAIN attribute
            _db.debug(D.EBUG_SPEW,"PDGUtility.setPDHdomain(): no meta for PDHDOMAIN for "+eiReturn.getEntityType());
		}

		if (strAttrValue !=null && strAttrValue.length()>0){
	        attList.put(attrCode, attrCode + "=" + strAttrValue);
       		updateAttribute(_db, _prof, eiReturn, attList);
		}

     }
    /**
     * copyEntity
     *
     * @param _db
     * @param _prof
     * @param _eiFrom
     * @param _eiTo
     * @param _strExcludeCopy
     * @param _bRelator
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws java.sql.SQLException
     * @return
     *  @author David Bigelow
     */
    public EntityItem copyEntity(Database _db, Profile _prof, EntityItem _eiFrom, EntityItem _eiTo, String _strExcludeCopy, boolean _bRelator)
    throws MiddlewareException, MiddlewareRequestException, SQLException, SBRException {
        String strEntityType = null;
        String strToEntityType = null;
        EntityItem eiReturn = null;

        int iToEntityID = -1;
        int iEntityID = -1;

        ReturnStatus returnStatus = new ReturnStatus(-1);
        String strEnterprise = _prof.getEnterprise();
        int iOPWGID = _prof.getOPWGID();
        int iTranID = _prof.getTranID();
        int iSessionID = _prof.getSessionID();
        String strRoleCode = _prof.getRoleCode();
		int iNLSID = _prof.getReadLanguage().getNLSID();
        if (_eiFrom == null || _eiTo == null) {
            return null;
        }

        strEntityType = _eiFrom.getEntityType();
        iEntityID = _eiFrom.getEntityID();
        strToEntityType = _eiTo.getEntityType();
        iToEntityID = _eiTo.getEntityID();

        try {
            DatePackage dpNow = _db.getDates();
            String strNow = dpNow.getNow();
            String strForever = dpNow.getForever();
            String strEndOfDay = dpNow.getEndOfDay();

            if (_bRelator) {
                // need to put in entity table
                _db.callGBL2092(returnStatus, iOPWGID, iSessionID, strEnterprise, strToEntityType, new ReturnID(iToEntityID), iTranID, strNow, strForever, iNLSID);
                _db.commit();
                _db.freeStatement();
                _db.isPending();

            }
            // Copy the attributes .. what about default values?
            _db.callGBL2268(returnStatus, iOPWGID, strEnterprise, strRoleCode, strEntityType, iEntityID, strToEntityType, iToEntityID, iTranID, strEndOfDay, strEndOfDay, _strExcludeCopy);
            _db.freeStatement();
            _db.isPending();

            eiReturn = new EntityItem(null, _prof, strToEntityType, iToEntityID);

            // set PDHDOMAIN MN32841099
            setPDHdomain(_db,  _prof, eiReturn);

        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
        return eiReturn;
    }

    /**
     * getAvailForFiveRegions
     *
     * @param _db
     * @param _prof
     * @param _eiPDG
     * @param _eiParent
     * @param _strAvailSai
     * @param _strCai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.SBRException
     * @return
     *  @author David Bigelow
     */
    public EANList getAvailForFiveRegions(Database _db, Profile _prof, EntityItem _eiPDG, EntityItem _eiParent, String _strAvailSai, String _strCai) throws MiddlewareRequestException, MiddlewareException, SQLException, MiddlewareShutdownInProgressException, SBRException {

        String sGeo = null;
        String strEntity = null;
        String strEntityType = null;
        String strAttributes = null;
        StringTokenizer stGeo = null;

        TestPDG pdgObject = null;
        StringBuffer sbMissing = null;
        StringTokenizer st = null;
        String strAction = null;
        String strRelatorInfo = null;
        EntityItem foundEI = null;

        int i = -1;
        int i1 = -1;
        int iFind = -1;
        int iCreate = -1;
        int iLink = -1;

        // get Avail StringBuffer.
        EANList availList = new EANList();
        OPICMList infoList = new OPICMList();
        infoList.put("PDG", _eiPDG);
        infoList.put("GEOIND", "GENAREASELECTION");
        pdgObject = new TestPDG(_db, _prof, null, infoList, null, AVAIL5REGIONS);
        sbMissing = pdgObject.getMissingEntities();

        st = new StringTokenizer(sbMissing.toString(), NEW_LINE);

        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            StringTokenizer st1 = new StringTokenizer(s, "|");

            if (st1.hasMoreTokens()) {

                st1.nextToken().trim();
                Integer.parseInt(st1.nextToken());

                // get stuff for Entity
                strEntity = st1.nextToken();
                i1 = strEntity.indexOf(":");
                strEntityType = strEntity;
                strAttributes = "";
                if (i1 > -1) {
                    strEntityType = strEntity.substring(0, i1);
                    strAttributes = strEntity.substring(i1 + 1);
                }

                strAction = st1.nextToken();
                strRelatorInfo = st1.nextToken();

                i = strAttributes.indexOf("GENAREASELECTION");
                sGeo = "";
                if (i > -1) {
                    sGeo = strAttributes.substring(i);
                    stGeo = new StringTokenizer(sGeo, ";");
                    if (stGeo.hasMoreTokens()) {
                        sGeo = stGeo.nextToken();
                    }
                    stGeo = new StringTokenizer(sGeo, "=");
                    if (stGeo.hasMoreTokens()) {
                        sGeo = stGeo.nextToken();
                        sGeo = stGeo.nextToken();
                    }
                }

                //find the item if needed
                iFind = strAction.indexOf("find");
                foundEI = null;
                if (iFind > -1) {
                    EntityItem[] aei = dynaSearch(_db, _prof, _eiPDG, _strAvailSai, strEntityType, strAttributes);

                    //if found, add to list, continue with next Avail
                    if (aei.length > 0) {
                        foundEI = aei[0];
                        availList.put(sGeo, foundEI);
                    }
                }

                // link them if there's command link
                iLink = strAction.indexOf("linkParent");
                if (iLink > -1 && foundEI != null) {
                    EntityItem[] aei = { foundEI };
                    if (_eiParent != null) {
                        linkEntities(_db, _prof, _eiParent, aei, strRelatorInfo);
                        // if not need to restart, continue with next item
                        continue;
                    }
                }

                // create the item
                iCreate = strAction.indexOf("create");
                if (iCreate > -1) {
                    //prepare the list of attributes
                    OPICMList attList = getAttributeListForCreate(strEntityType, strAttributes, strRelatorInfo);

                    EntityItem ei = createEntityByRST(_db, _prof, _eiParent, attList, _strCai, strRelatorInfo, strEntityType);

                    _db.test(ei != null, " ei is null for: " + s);
                    availList.put(sGeo, ei);
                }
            }
        }
        return availList;
    }

    /********************************************************************************
    * Convert string into valid html.  Special HTML characters are converted.
    *
    * @param txt    String to convert
    * @return String
    */
    public static String convertToHTML(String txt)
    {
        String retVal=null;
        StringBuffer htmlSB = new StringBuffer();
        StringCharacterIterator sci = null;
        char ch = ' ';
        if (txt != null) {
            sci = new StringCharacterIterator(txt);
            ch = sci.first();
            while(ch != CharacterIterator.DONE)
            {
                switch(ch)
                {
                case '<': // could be saved as &lt; also. this will be &#60;
                case '>': // could be saved as &gt; also. this will be &#62;
                case '"': // could be saved as &quot; also. this will be &#34;
                // this should be included too, but left out to be consistent with west coast
                //case '&': // ignore entity references such as &lt; if user typed it, user will see it
                          // could be saved as &amp; also. this will be &#38;
                    htmlSB.append("&#"+((int)ch)+";");
                    break;
                case '\n':  // maintain new lines
                    htmlSB.append("<br />");
                    break;
                default:
                    htmlSB.append(ch);
                    break;
                }
                ch = sci.next();
            }
            retVal = htmlSB.toString();
        }

        return retVal;
    }

    /**
     * getPDGReportHTMLfromXML
     *
     * @param xmlSt
     * @throws java.lang.Exception
     * @return
     *  @author David Bigelow
     */
    public static String getPDGReportHTMLfromXML(String xmlSt) throws Exception {
        String xslSt =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\">"
            + " <xsl:output method=\"html\"/>"
            + " <xsl:template match=\"/DOCUMENT\">"
            + "  <xsl:for-each select=\"./RUN\">"
            + "   <xsl:call-template name=\"PROCESS_RUN\"/>"
            + "   <br />"
            + "   <br />"
            + "   <hr />"
            + "   <br />"
            + "   <br />"
            + "  </xsl:for-each>"
            + " </xsl:template>"
            + " <xsl:template name=\"PROCESS_RUN\">"
            + "  <xsl:variable name=\"TIMESTAMP\" select=\"string(./TIMESTAMP)\"/>"
            + "  <xsl:value-of select=\"concat('Time Stamp : ',$TIMESTAMP)\"/>"
            + "  <xsl:for-each select=\"./PDGACTTIONITEM\">"
            + "   <br />"
            + "   <xsl:call-template name=\"PROCESS_PDGACTTIONITEM\"/>"
            + "  </xsl:for-each>"
            + "  <xsl:for-each select=\"./TEMPLATEFILE\">"
            + "   <br />"
            + "   <xsl:call-template name=\"PROCESS_TEMPLATEFILE\"/>"
            + "  </xsl:for-each>"
            + "  <xsl:for-each select=\"./PDGENTITY\">"
            + "   <br />"
            + "   <xsl:call-template name=\"PROCESS_PDGENTITY\"/>"
            + "  </xsl:for-each>"
            + "  <xsl:for-each select=\"./MSG\">"
            + "   <br />"
            + "   <xsl:call-template name=\"PROCESS_MSG\"/>"
            + "  </xsl:for-each>"

            + "  <xsl:for-each select=\"./DEBUG\">"
            + "   <xsl:call-template name=\"PROCESS_DEBUG\"/>"
            + "  </xsl:for-each>"

            + "  <table border=\"1\">"
            + "   <xsl:for-each select=\"./ITEM[1]\">"
            + "    <xsl:call-template name=\"PROCESS_ITEMTITLE\"/>"
            + "   </xsl:for-each>"
            + "   <br />"
            + "   <xsl:for-each select=\"./ITEM\">"
            + "    <xsl:call-template name=\"PROCESS_ITEM\"/>"
            + "   </xsl:for-each>"
            + "  </table>"
            + " </xsl:template>"
            + " <xsl:template name=\"PROCESS_PDGENTITY\">"
            + "  <br />"
            + "  <xsl:value-of select=\"string('PDG Request inputs : ') \"/>"
            + "  <xsl:value-of select=\"string(@type) \"/>"
            + "  <center>"
            + "   <table border=\"1\">"
            + "    <xsl:for-each select=\"row\">"
            + "     <tr>"
            + "      <xsl:for-each select=\"col\">"
            + "       <td>"
            + "        <xsl:variable name=\"P\" select=\"string(.)\"/>"
            + "        <xsl:value-of select=\"string($P)\"/>"
            + "       </td>"
            + "      </xsl:for-each>"
            + "     </tr>"
            + "    </xsl:for-each>"
            + "   </table>"
            + "  </center>"
            + " </xsl:template>"
            + " <xsl:template name=\"PROCESS_PDGACTTIONITEM\">"
            + "  <xsl:value-of select=\"string('PDG ActionItem :  ')\"/>"
            + "  <xsl:variable name=\"PDGACTTIONITEM\" select=\"string(.)\"/>"
            + "  <xsl:value-of select=\"string($PDGACTTIONITEM)\"/>"
            + " </xsl:template>"
            + " <xsl:template name=\"PROCESS_TEMPLATEFILE\">"
            + "  <xsl:value-of select=\"string('Template File  :  ')\"/>"
            + "  <xsl:variable name=\"TEMPLATEFILE\" select=\"string(.)\"/>"
            + "  <xsl:value-of select=\"string($TEMPLATEFILE)\"/>"
            + " </xsl:template>"
            + " <xsl:template name=\"PROCESS_MSG\">"
            + "  <xsl:value-of select=\"string(' ')\"/>"
            + "  <xsl:variable name=\"MSG\" select=\"string(.)\"/>"
            + "  <xsl:value-of select=\"string($MSG)\"/>"
            + " </xsl:template>"

            + " <xsl:template name=\"PROCESS_DEBUG\">"
            + "  <xsl:value-of select=\"string(' ')\"/>"
            + "  <xsl:variable name=\"DEBUG\" select=\"string(.)\"/>"
            + "  <xsl:comment> "
            + "  <xsl:value-of select=\"string($DEBUG)\"/>"
            + "  </xsl:comment>"
            + " </xsl:template>"

            + " <xsl:template name=\"PROCESS_ITEMTITLE\">"
            + "  <tr>"
            + "   <xsl:for-each select=\"./*\">"
            + "    <th>"
            + "     <xsl:value-of select=\"name()\"/>"
            + "    </th>"
            + "   </xsl:for-each>"
            + "  </tr>"
            + " </xsl:template>"
            + " <xsl:template name=\"PROCESS_ITEM\">"
            + "  <tr>"
            + "   <xsl:for-each select=\"./*\">"
            + "    <td>"
            + "     <xsl:value-of select=\"string(./.)\"/>"
            + "    </td>"
            + "   </xsl:for-each>"
            + "  </tr>"
            + " </xsl:template>"
            + "</xsl:stylesheet>"
            + "";

        StringReader inputReader = new StringReader(xmlSt);
        StringReader mapReader = new StringReader(xslSt);
        StringWriter outputWriter = new StringWriter();

        try {
            transform(inputReader, mapReader, outputWriter);
            return outputWriter.toString();
        } catch (Exception e) {
            outputWriter.write(e.getMessage());
            throw e;
        } finally {
            inputReader.close();
            mapReader.close();
            outputWriter.close();
        }

    }
    /**
     * Insert the method's description here.
     * Creation date: (5/1/2003 3:33:59 PM)
     * @return boolean
     */

    private static boolean transform(Reader inputReader, Reader mappingReader, Writer outputWriter) throws Exception {

        if (inputReader == null) {
            throw new Exception("Input reader is null");
        }
        if (mappingReader == null) {
            throw new Exception("Mapping reader is null");
        }
        if (outputWriter == null) {
            throw new Exception("Output writer is null");
        }
        try {
            javax.xml.transform.stream.StreamSource inputSource = new javax.xml.transform.stream.StreamSource(inputReader);
            javax.xml.transform.stream.StreamSource mappingSource = new javax.xml.transform.stream.StreamSource(mappingReader);
            javax.xml.transform.stream.StreamResult outputResult = new javax.xml.transform.stream.StreamResult(outputWriter);
            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer(mappingSource);

			transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");

            transformer.transform(inputSource, outputResult);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }

    /**
     * getGA
     *
     * @param _eg
     * @param _strGenAreaNameCode
     * @return
     *  @author David Bigelow
     */
    public EntityItem getGA(EntityGroup _eg, String _strGenAreaNameCode) {
        EntityItem eiGA = null;
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
            EntityItem ei = _eg.getEntityItem(i);
            EANFlagAttribute att = (EANFlagAttribute) ei.getAttribute("GENAREANAME");
            if (att != null) {
                if (att.isSelected(_strGenAreaNameCode)) {
                    eiGA = ei;
                    break;
                }
            }
        }
        return eiGA;
    }

    /**
     * getChildrenEntityIds
     *
     * @param _elist
     * @param _strParentType
     * @param _iParentId
     * @param _strChildEtype
     * @param _strParentChildType
     * @return
     *  @author David Bigelow
     */
    public Vector getChildrenEntityIds(EntityList _elist, String _strParentType, int _iParentId, String _strChildEtype, String _strParentChildType) {
        Vector ids = new Vector(1);

        // get the navigation group for the relator
        EntityGroup entGroup = _elist.getEntityGroup(_strParentChildType);

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

    /**
     * getParentEntityIds
     *
     * @param _elist
     * @param _strChildType
     * @param _iChildID
     * @param _strParentType
     * @param _strRelatorType
     * @return
     *  @author David Bigelow
     */
    public Vector getParentEntityIds(EntityList _elist, String _strChildType, int _iChildID, String _strParentType, String _strRelatorType) {
        Vector ids = new Vector(1);

        // get the Entity group for the relator
        EntityGroup entGroup = _elist.getEntityGroup(_strRelatorType);

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
        return ids;
    }

    /**
     * getRelatorEntityIds
     *
     * @param _elist
     * @param _strParentType
     * @param _iParentId
     * @param _strChildEtype
     * @param _iEntityId
     * @param _strParentChildType
     * @return
     *  @author David Bigelow
     */
    public Vector getRelatorEntityIds(EntityList _elist, String _strParentType, int _iParentId, String _strChildEtype, int _iEntityId, String _strParentChildType) {
        Vector ids = new Vector(1);

        // get the navigation group for the relator
        EntityGroup entGroup = _elist.getEntityGroup(_strParentChildType);

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
                        if (ei.getDownLink(at).getEntityType().equals(_strChildEtype) && ei.getDownLink(at).getEntityID() == _iEntityId) {
                            ids.addElement(new Integer(ei.getEntityID()));
                        }
                    }
                }
            }
        }

        return ids;
    }

    /**
     * removeLink
     *
     * @param _db
     * @param _prof
     * @param _elist
     * @param _aeip
     * @param _aeic
     * @param _strRelatorType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     *  @author David Bigelow
     */
    public void removeLink(Database _db, Profile _prof, EntityList _elist, EntityItem[] _aeip, EntityItem[] _aeic, String _strRelatorType) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {

        if (_aeip == null) {
            return;
        }

        for (int i = 0; i < _aeip.length; i++) {
            EntityItem eip = _aeip[i];
            removeLink(_db, _prof, _elist, eip, _aeic, _strRelatorType);
        }

    }

    /**
     * removeLink
     *
     * @param _db
     * @param _prof
     * @param _elist
     * @param _eip
     * @param _aeic
     * @param _strRelatorType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     *  @author David Bigelow
     */
    public void removeLink(Database _db, Profile _prof, EntityList _elist, EntityItem _eip, EntityItem[] _aeic, String _strRelatorType) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
        Vector vctReturnRelatorKeys = new Vector();

        if (_aeic == null) {
            return;
        }

        for (int i = 0; i < _aeic.length; i++) {
            EntityItem eic = _aeic[i];
            Vector v = getRelatorEntityIds(_elist, _eip.getEntityType(), _eip.getEntityID(), eic.getEntityType(), eic.getEntityID(), _strRelatorType);
            for (int j = 0; j < v.size(); j++) {
                int id = ((Integer) v.elementAt(j)).intValue();
                vctReturnRelatorKeys.addElement(new ReturnRelatorKey(_strRelatorType, id, _eip.getEntityType(), _eip.getEntityID(), eic.getEntityType(), eic.getEntityID(), false));
                m_sbActivities.append("<ITEM><ACTION>Remove Link</ACTION><ENTITYKEY>" + eic.getKey() + "</ENTITYKEY><ENTITYDISPLAY>" + PDGUtility.convertToHTML(eic.getLongDescription()) + "</ENTITYDISPLAY><PARENT>" + _eip.getKey() + ":" + PDGUtility.convertToHTML(_eip.getLongDescription()) + "</PARENT></ITEM>" + NEW_LINE);
            }
        }
        link(_db, _prof, vctReturnRelatorKeys, "");
    }

    /**
     * linkCopy
     *
     * @param _db
     * @param _prof
     * @param _vctReturnRelatorKeys
     * @param _iCopyCount
     * @param _strLinkOption
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @return
     *  @author David Bigelow
     */
    public OPICMList linkCopy(Database _db, Profile _prof, Vector _vctReturnRelatorKeys, int _iCopyCount, String _strLinkOption) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
        String strTraceBase = "PDGUtility linkCopy method";

        OPICMList olReturn = new OPICMList();
        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        ReturnRelatorKey rrk = null;
        ReturnID idNew = new ReturnID(0);
        ReturnStatus returnStatus = new ReturnStatus(-1);

        // Pull some profile info...
        int iOpenID = _prof.getOPWGID();
        int iTranID = _prof.getTranID();
        int iSessionID = _prof.getSessionID();
        String strEnterprise = _prof.getEnterprise();
        String strRoleCode = _prof.getRoleCode();
		int iNLSID = _prof.getReadLanguage().getNLSID();
        // Some basis EntityType, EntityID objects
        String strEntityType = null;
        int iEntityID = 0;
        String strEntity1Type = null;
        int iEntity1ID = 0;
        String strEntity2Type = null;
        int iEntity2ID = 0;

        // Some new place holders
        int iEntity2IDNew = 0;

        try {
            DatePackage dpNow = _db.getDates();
            String strNow = dpNow.getNow();
            String strForever = dpNow.getForever();
            String strEndOfDay = dpNow.getEndOfDay();

            if (_strLinkOption.equalsIgnoreCase("REPLACEALL")) {

                EANList elist = new EANList();
                String strKey = null;
                EntityItem ei = null;

                _db.debug(D.EBUG_SPEW,strTraceBase + " Generating list of existing relators between the parent and child and deactivating them ");

                // Loop through the vectors
                for (int i = 0; i < _vctReturnRelatorKeys.size(); i++) {
                    if (_vctReturnRelatorKeys.elementAt(i) instanceof ReturnRelatorKey) {
                        // Lets pull out the meaningfull information
                        rrk = (ReturnRelatorKey) _vctReturnRelatorKeys.elementAt(i);
                        _db.test(rrk.getEntityType() != null, "entityType is null");
                        _db.test(rrk.getEntity1Type() != null, "entity1Type is null");
                        _db.test(rrk.getEntity1ID() > 0, "entity1ID <= 0");
                        _db.test(rrk.getEntity2Type() != null, "entity2Type is null");
                        _db.test(rrk.getEntity2ID() > 0, "entity2ID <= 0");

                        strEntityType = rrk.getEntityType();
                        strEntity1Type = rrk.getEntity1Type();
                        iEntity1ID = rrk.getEntity1ID();
                        strEntity2Type = rrk.getEntity2Type();
                        iEntity2ID = rrk.getEntity2ID();

                        // Get a list of existing relators to deactivate
                        try {
                            rs = _db.callGBL7940(returnStatus, strEnterprise, strRoleCode, strEntityType, strEntity1Type, iEntity1ID, strNow, strNow);
                            rdrs = new ReturnDataResultSet(rs);
                        } finally {
							if (rs!=null){
	                            rs.close();
	                            rs = null;
							}
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();
                        }
                        for (int ii = 0; ii < rdrs.size(); ii++) {
                            strEntityType = rdrs.getColumn(ii, 0).trim();
                            iEntityID = rdrs.getColumnInt(ii, 1);
                            strKey = strEntityType + iEntityID;
                            ei = (EntityItem) elist.get(strKey);

                            _db.debug(D.EBUG_SPEW, "gbl7940 answer: " + strEntityType + ":" + iEntityID + ":");

                            if (ei == null) {
                                _db.debug(D.EBUG_SPEW, "Placing in list:" + strKey);
                                elist.put(new EntityItem(null, _prof, strEntityType, iEntityID));
                            }
                        }

                        // Here we go at turning off all existing... relators before we continue
                        for (int ii = 0; ii < elist.size(); ii++) {
                            ei = (EntityItem) elist.getAt(ii);
                            strEntityType = ei.getEntityType();
                            iEntityID = ei.getEntityID();
                            _db.callGBL7937(returnStatus, strEnterprise, iOpenID, strEntityType, iEntityID, iTranID);
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();
                        }
                    }
                }
            }

            for (int ii = 0; ii < _vctReturnRelatorKeys.size(); ii++) {
                if (_vctReturnRelatorKeys.elementAt(ii) instanceof ReturnRelatorKey) {
                    rrk = (ReturnRelatorKey) _vctReturnRelatorKeys.elementAt(ii);
                    _db.test(rrk.getEntityType() != null, "entityType is null");
                    _db.test(rrk.getEntity1Type() != null, "entity1Type is null");
                    _db.test(rrk.getEntity1ID() > 0, "entity1ID <= 0");
                    _db.test(rrk.getEntity2Type() != null, "entity2Type is null");
                    _db.test(rrk.getEntity2ID() > 0, "entity2ID <= 0");

                    strEntity1Type = rrk.getEntity1Type();
                    iEntity1ID = rrk.getEntity1ID();
                    strEntityType = rrk.getEntityType();
                    iEntityID = 0;
                    strEntity2Type = rrk.getEntity2Type();
                    iEntity2ID = rrk.getEntity2ID();
                    iEntity2IDNew = 0;

                    for (int x = 0; x < _iCopyCount; x++) {
                        // Make the entity
                        idNew = _db.callGBL2092(returnStatus, iOpenID, iSessionID, strEnterprise, strEntity2Type, new ReturnID(0), iTranID, strNow, strForever, iNLSID);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();

                        // Set the new iEntity2ID
                        iEntity2IDNew = idNew.intValue();

                        // Copy the attributes .. what about default values?
                        _db.callGBL2268(returnStatus, iOpenID, strEnterprise, strRoleCode, strEntity2Type, iEntity2ID, strEntity2Type, iEntity2IDNew, iTranID, strEndOfDay, strEndOfDay, "0");
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                        // Now link it up
                        // TODO NLSREF
                        idNew = _db.callGBL2098(returnStatus, iOpenID, iSessionID, strEnterprise, strEntityType, new ReturnID(0), strEntity1Type, iEntity1ID, strEntity2Type, iEntity2IDNew, iTranID, strNow, strForever,iNLSID);
                        iEntityID = idNew.intValue();

                        rrk.setReturnID(idNew);
                        rrk.setPosted(true);
                        olReturn.put(rrk);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();

                    }
                }
            }
        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
        return olReturn;
    }

    /**
     * getEntityAttributes
     *
     * @param _ei
     * @return
     *  @author David Bigelow
     */
    public OPICMList getEntityAttributes(EntityItem _ei) {

        OPICMList attList = new OPICMList();
        for (int ii = 0; ii < _ei.getAttributeCount(); ii++) {
            EANAttribute att = _ei.getAttribute(ii);
            String strAttrCode = att.getAttributeCode();
            String strAttrValue = getAttrValue(_ei, strAttrCode);
            if (strAttrValue != null && strAttrValue.length() > 0) {
                attList.put(strAttrCode, strAttrCode + "=" + strAttrValue);
            }
        }

        return attList;
    }

    /**
     * linkCopyRel
     *
     * @param _db
     * @param _prof
     * @param _vctReturnRelatorKeys
     * @param _eiR
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.SBRException
     *  @author David Bigelow
     */
    public void linkCopyRel(Database _db, Profile _prof, Vector _vctReturnRelatorKeys, EntityItem _eiR) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {

        EntityItem ei = null;
        String strEntityType = null;
        EntityGroup eg = null;
        OPICMList attrList = null;

        OPICMList ol = link(_db, _prof, _vctReturnRelatorKeys, "NODUPES");
        if (ol.size() > 0) {
            // get orginal Relator attributes
            strEntityType = _eiR.getEntityType();
            eg = (EntityGroup) m_egList.get(strEntityType);
            if (eg == null) {
                eg = new EntityGroup(null, _db, _prof, strEntityType, "Edit", false);
                m_egList.put(eg.getEntityType(), eg);
            }

            _eiR = new EntityItem(eg, _prof, _db, _eiR.getEntityType(), _eiR.getEntityID());

            attrList = getEntityAttributes(_eiR);

            for (int i = 0; i < ol.size(); i++) {
                Object obj = ol.getAt(i);
                if (obj instanceof ReturnRelatorKey) {
                    ReturnRelatorKey rrk = (ReturnRelatorKey) obj;
                    _prof = setProfValOnEffOn(_db, _prof);
                    ei = new EntityItem(null, _prof, rrk.getEntityType(), rrk.getReturnID());
                    updateAttribute(_db, _prof, ei, attrList);
                }
            }
        }
    }

    private OPICMList link(Database _db, Profile _prof, Vector _vctReturnRelatorKeys, String _strLinkOption) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {

        // The stored procedure ReturnStatus
        OPICMList olReturn = new OPICMList();

        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        ReturnRelatorKey rrk = null;
        ReturnID idNew = new ReturnID(0);
        ReturnStatus returnStatus = new ReturnStatus(-1);

        // Placeholders for dates
        String strNow = null;
        String strForever = null;
        //String strEndOfDay = null;

        // Pull some profile info...
        int iOpenID = _prof.getOPWGID();
        int iTranID = _prof.getTranID();
        int iSessionID = _prof.getSessionID();
        String strEnterprise = _prof.getEnterprise();
        String strRoleCode = _prof.getRoleCode();
		int iNLSID = _prof.getReadLanguage().getNLSID();
        // Some basis EntityType, EntityID objects
        EntityItem ei = null;

        String strEntityType = null;
        int iEntityID = 0;
        String strEntity1Type = null;
        int iEntity1ID = 0;
        String strEntity2Type = null;
        int iEntity2ID = 0;

        // Some new place holders
        try {

            DatePackage dpNow = _db.getDates();
            strNow = dpNow.getNow();
            strForever = dpNow.getForever();
            //strEndOfDay = dpNow.getEndOfDay();

            if (_strLinkOption.equalsIgnoreCase("REPLACEALL")) {
                EANList elist = new EANList();
                String strKey = null;

                // Loop through the vectors
                for (int i = 0; i < _vctReturnRelatorKeys.size(); i++) {
                    if (_vctReturnRelatorKeys.elementAt(i) instanceof ReturnRelatorKey) {
                        // Lets pull out the meaningfull information
                        rrk = (ReturnRelatorKey) _vctReturnRelatorKeys.elementAt(i);
                        _db.test(rrk.getEntityType() != null, "entityType is null");
                        _db.test(rrk.getEntity1Type() != null, "entity1Type is null");
                        _db.test(rrk.getEntity1ID() > 0, "entity1ID <= 0");
                        _db.test(rrk.getEntity2Type() != null, "entity2Type is null");
                        _db.test(rrk.getEntity2ID() > 0, "entity2ID <= 0");

                        strEntityType = rrk.getEntityType();
                        strEntity1Type = rrk.getEntity1Type();
                        iEntity1ID = rrk.getEntity1ID();
                        strEntity2Type = rrk.getEntity2Type();
                        iEntity2ID = rrk.getEntity2ID();

                        // Get a list of existing relators to deactivate
                        try {
                            rs = _db.callGBL7940(returnStatus, strEnterprise, strRoleCode, strEntityType, strEntity1Type, iEntity1ID, strNow, strNow);
                            rdrs = new ReturnDataResultSet(rs);
                        } finally {
							if (rs!=null){
                	            rs.close();
                	            rs = null;
							}
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();
                        }

                        for (int ii = 0; ii < rdrs.size(); ii++) {
                            strEntityType = rdrs.getColumn(ii, 0).trim();
                            iEntityID = rdrs.getColumnInt(ii, 1);
                            _db.debug(D.EBUG_SPEW, "gbl7940 answer: " + strEntityType + ":" + iEntityID + ":");
                            strKey = strEntityType + iEntityID;

                            ei = (EntityItem) elist.get(strKey);
                            if (ei == null) {
                                elist.put(new EntityItem(null, _prof, strEntityType, iEntityID));
                            }
                        }

                        // Here we go at turning off all existing... relators before we continue

                        for (int ii = 0; ii < elist.size(); ii++) {
                            ei = (EntityItem) elist.getAt(ii);
                            strEntityType = ei.getEntityType();
                            iEntityID = ei.getEntityID();
                            _db.callGBL7937(returnStatus, strEnterprise, iOpenID, strEntityType, iEntityID, iTranID);
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();
                        }
                    }
                }
            }

            for (int i = 0; i < _vctReturnRelatorKeys.size(); i++) {
                if (_vctReturnRelatorKeys.elementAt(i) instanceof ReturnRelatorKey) {
                    boolean bexists = false;

                    rrk = (ReturnRelatorKey) _vctReturnRelatorKeys.elementAt(i);
                    _db.debug(D.EBUG_SPEW, "rrk:" + rrk.toString());
                    _db.test(rrk.getEntityType() != null, "entityType is null");
                    _db.test(rrk.getEntity1Type() != null, "entity1Type is null");
                    _db.test(rrk.getEntity1ID() > 0, "entity1ID <= 0");
                    _db.test(rrk.getEntity2Type() != null, "entity2Type is null");
                    _db.test(rrk.getEntity2ID() > 0, "entity2ID <= 0");
                    strEntity1Type = rrk.getEntity1Type();
                    iEntity1ID = rrk.getEntity1ID();
                    strEntityType = rrk.getEntityType();
                    iEntityID = rrk.getEntityID();
                    strEntity2Type = rrk.getEntity2Type();
                    iEntity2ID = rrk.getEntity2ID();

                    if (_strLinkOption.equalsIgnoreCase("NODUPES")) {
                        // Check for a dupe
                        rs = _db.callGBL2991(returnStatus, iOpenID, strEnterprise, rrk.getEntityType(), rrk.getEntity1Type(), rrk.getEntity1ID(), rrk.getEntity2Type(), rrk.getEntity2ID(), strNow, strNow);
                        if (rs.next()) {
                            bexists = true;
                            _db.debug(D.EBUG_SPEW, "No Dups found existing relator.. skipping add." + rrk.toString());
                        }
                        rs.close();
                        rs = null;
                        _db.freeStatement();
                        _db.isPending();
                    }

                    if (_strLinkOption.equalsIgnoreCase("") || _strLinkOption.equalsIgnoreCase("REPLACEALL") || (_strLinkOption.equalsIgnoreCase("NODUPES") && !bexists)) {

                        // Create the new relator.. Not sure what is up with the de-activating stuff here... this may serve if the relatorid is positive
                        // TODO NLSREF
                        if (rrk.isActive()) {
                            idNew = _db.callGBL2098(returnStatus, iOpenID, iSessionID, strEnterprise, strEntityType, new ReturnID(iEntityID), strEntity1Type, iEntity1ID, strEntity2Type, iEntity2ID, iTranID, strNow, strForever,iNLSID);
                        } else {
                            idNew = _db.callGBL2098(returnStatus, iOpenID, iSessionID, strEnterprise, strEntityType, new ReturnID(iEntityID), strEntity1Type, iEntity1ID, strEntity2Type, iEntity2ID, iTranID, strNow, strNow,iNLSID);
                        }
                        rrk.setReturnID(idNew);
                        rrk.setPosted(true);
                        olReturn.put(rrk);

                        _db.freeStatement();
                        _db.isPending();

                    } else {
                        rrk.setPosted(false);
                        olReturn.put(rrk);
                    }
                }
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
            _db.commit();
        }

        return olReturn;

    }

    /**
     * getFlagCodeForLikedDesc
     *
     * @param _db
     * @param _prof
     * @param _strAttrCode
     * @param _strLongDesc
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public String[] getFlagCodeForLikedDesc(Database _db, Profile _prof, String _strAttrCode, String _strLongDesc) throws SQLException, MiddlewareException {
        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        ReturnStatus returnStatus = new ReturnStatus(-1);

        // Placeholders for dates
        String strNow = null;
//        String strForever = null;
//        String strEndOfDay = null;

        Vector v = new Vector();

        // Pull some profile info...
        String strEnterprise = _prof.getEnterprise();
        String[] aFlagCodes = null;
        try {
            DatePackage dpNow = _db.getDates();
            strNow = dpNow.getNow();
//            strForever = dpNow.getForever();
//            strEndOfDay = dpNow.getEndOfDay();
            if (_strLongDesc.length() > 128) {
				_strLongDesc = _strLongDesc.substring(0, 128);
			}
            try {
				_db.debug(D.EBUG_SPEW, "gbl2914 parms: " + _strAttrCode + ":" + _strLongDesc);
                rs = _db.callGBL2914(returnStatus, strEnterprise, _strAttrCode, _strLongDesc, strNow, strNow, 1, 1);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                if (rs!=null){
					rs.close();
	                rs = null;
				}
                _db.freeStatement();
                _db.isPending();
            }
            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strDescClass = rdrs.getColumn(ii, 0).trim();
                String strLongDesc = rdrs.getColumn(ii, 1).trim();
                String strExpired = rdrs.getColumn(ii, 2).trim();
                _db.debug(D.EBUG_SPEW, "gbl2914 answer: " + strDescClass + ":" + strLongDesc + ":" + strExpired);
                if (strExpired.equals("N")) {
                	v.addElement(strDescClass);
				}
            }
            aFlagCodes = new String[v.size()];
            v.copyInto(aFlagCodes);
        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();

        }
        return aFlagCodes;
    }

    /**
     * getFlagCodeForLikedDesc
     *
     * @param _db
     * @param _prof
     * @param _strAttrCode
     * @param _strLongDesc
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public String[] getFlagCodeForExactDesc(Database _db, Profile _prof, String _strAttrCode, String _strLongDesc) throws SQLException, MiddlewareException {
        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        ReturnStatus returnStatus = new ReturnStatus(-1);

        // Placeholders for dates
        String strNow = null;
//        String strForever = null;
//        String strEndOfDay = null;

        Vector v = new Vector();

        // Pull some profile info...
        String strEnterprise = _prof.getEnterprise();
        String[] aFlagCodes = null;
        try {
            DatePackage dpNow = _db.getDates();
            strNow = dpNow.getNow();
//            strForever = dpNow.getForever();
//            strEndOfDay = dpNow.getEndOfDay();
            if (_strLongDesc.length() > 128) {
                _strLongDesc = _strLongDesc.substring(0, 128);
            }
            try {
                _db.debug(D.EBUG_SPEW, "gbl2914 parms: " + _strAttrCode + ":" + _strLongDesc);
                rs = _db.callGBL2914(returnStatus, strEnterprise, _strAttrCode, _strLongDesc, strNow, strNow, 1, 2);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
				if (rs!=null){
	                rs.close();
	                rs = null;
				}
                _db.freeStatement();
                _db.isPending();
            }
            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strDescClass = rdrs.getColumn(ii, 0).trim();
                String strLongDesc = rdrs.getColumn(ii, 1).trim();
                _db.debug(D.EBUG_SPEW, "gbl2914 answer: " + strDescClass + ":" + strLongDesc + ":");
                v.addElement(strDescClass);
            }
            aFlagCodes = new String[v.size()];
            v.copyInto(aFlagCodes);
        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();

        }
        return aFlagCodes;
    }

	private static String changeDF(String _strDate) {
           //XX-XX-XXXX
           StringBuffer sb = new StringBuffer();
           if (_strDate.length() == 10) {
               if ((_strDate.charAt(2) == '-') && (_strDate.charAt(5) == '-')) { //XX-XX-XXXX
                   sb.append(_strDate.substring(6) + "-" + _strDate.substring(0,2) + "-" + _strDate.substring(3,5));
               } else {
                   sb.append(_strDate);
               }
           }

           return sb.toString();
	}

    /**
     * dateCompare
     *
     * @param _strDate1
     * @param _strDate2
     * @return
     *  @author David Bigelow
     */
    public int dateCompare(String _strDate1, String _strDate2) {
       if (_strDate1 != null && _strDate1.length() == 10 && _strDate2 != null && _strDate2.length() == 10) {
           Date date1 = Date.valueOf(changeDF(_strDate1));
           Date date2 = Date.valueOf(changeDF(_strDate2));

           if (date1.before(date2)) {
               return EARLIER;
           } else if (date1.after(date2)) {
               return LATER;
           }

           return EQUAL;
       } else {
           return ILLEGALARGUMENT;
       }
    }

    /**
     * longdateCompare
     *
     * @param _strDate1
     * @param _strDate2
     * @return
     *  @author David Bigelow
     */
    public int longDateCompare(String _strDate1, String _strDate2) {
       if (_strDate1 != null && _strDate1.length() == 26 && _strDate2 != null && _strDate2.length() == 26) {
          // in format "1980-01-01-00.00.00.000000"
          Calendar c = Calendar.getInstance();
          int year1 = Integer.parseInt(_strDate1.substring(0,4));
          int month1 = Integer.parseInt(_strDate1.substring(5,7));
          int date1 = Integer.parseInt(_strDate1.substring(8,10));
          int hour1 = Integer.parseInt(_strDate1.substring(11,13));
          int minute1 = Integer.parseInt(_strDate1.substring(14,16));
          int second1 = Integer.parseInt(_strDate1.substring(17,19));

          int year2 = Integer.parseInt(_strDate2.substring(0,4));
          int month2 = Integer.parseInt(_strDate2.substring(5,7));
          int date2 = Integer.parseInt(_strDate2.substring(8,10));
          int hour2 = Integer.parseInt(_strDate2.substring(11,13));
          int minute2 = Integer.parseInt(_strDate2.substring(14,16));
          int second2 = Integer.parseInt(_strDate2.substring(17,19));

          c.set(year1, month1, date1, hour1, minute1, second1);
          long time1 = c.getTime().getTime();

          c.set(year2, month2, date2, hour2, minute2, second2);
          long time2 = c.getTime().getTime();

          if (time1 < time2) {
              return EARLIER;
          } else if (time1 > time2) {
              return LATER;
          }

          return EQUAL;
      } else {
          return ILLEGALARGUMENT;
      }
    }

    /**
     * replace
     *
     * @param _s
     * @param _s1
     * @param _s2
     * @return
     *  @author David Bigelow
     */
    public String replace(String _s, String _s1, String _s2) {
        String sResult = "";
        int iTab = _s.indexOf(_s1);

        while (_s.length() > 0 && iTab >= 0) {
            sResult = sResult + _s.substring(0, iTab) + _s2;
            _s = _s.substring(iTab + _s1.length());
            iTab = _s.indexOf(_s1);
        }
        sResult = sResult + _s;
        return sResult;
    }

    public EntityList dynaSearchIIForEntityList(Database _db, Profile _prof, EntityItem _eiParent, String _strSai, String _strEntityType, String _strAttributes) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {

        EntityList el = null;
        MetaFlag[] amf = null;

        RowSelectableTable rst = null;
        StringTokenizer st = null;
        StringTokenizer st1 = null;

        String strAttrCode = null;
        String strAttrValue = null;
        String[] valArray = null;

        String strTraceBase = " PDGUtility dynaSearchIIForEntityList method TRACE";
        SBRException sbrEx = new SBRException();
        SearchActionItem sai = (SearchActionItem) m_saiList.get(_strSai);

        if (sai == null) {
            sai = new SearchActionItem(null, _db, _prof, _strSai, false);
            //m_saiList.put(_strSai, sai);
        }

        rst = (RowSelectableTable)m_saiTable.get(_strSai);

        if (rst == null) {
            rst = sai.getDynaSearchTable(_db);
            //m_saiTable.put(_strSai, rst);
        }

        rst.rollback();

        st = new StringTokenizer(_strAttributes, ";");
        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            int r = 0;
            if (s.substring(0, 3).equals("map")) {
                s = s.substring(4);
                st1 = new StringTokenizer(s, "=");
                if (st1.countTokens() < 2) {
                    continue;
                }

                strAttrCode = st1.nextToken().trim();
                strAttrValue = st1.nextToken().trim();

                valArray = getValueArray(strAttrValue);

                r = rst.getRowIndex(strAttrCode);
                if (r < 0) {
                    r = rst.getRowIndex(strAttrCode + ":P");
                }
                if (r < 0) {
                    r = rst.getRowIndex(strAttrCode + ":C");
                }
                if (r < 0) {
                    r = rst.getRowIndex(strAttrCode + ":R");
                }
                if (r >= 0 && r < rst.getRowCount()) {
                    EANAttribute att = (EANAttribute) rst.getEANObject(r, 1);
                    if (att instanceof EANTextAttribute) {
                        try {
                            rst.put(r, 1, strAttrValue);
                        } catch (EANBusinessRuleException bre) {
                            bre.printStackTrace();
                            sbrEx.add(bre.getMessage());
                            throw sbrEx;
                        }
                    } else if (att instanceof EANFlagAttribute) {
                        boolean bFlag = false;
                        EANFlagAttribute fa = (EANFlagAttribute) att;
                        EANMetaAttribute ma = fa.getMetaAttribute();
                        // remove unwanted default value in multi flag
                        if (ma.hasDefaultValue() && fa instanceof MultiFlagAttribute) {
                            fa.put(ma.getDefaultValue(), false);
                        }

                        amf = (MetaFlag[]) fa.get();
                        for (int v = 0; v < valArray.length; v++) {
                            String val = valArray[v];

                            // need to use this put method for classified attribute,
                            // for some classified attribute, I only have longdescription as inputs that come from the request, ex AFHWSERIES.

                            for (int f = 0; f < amf.length; f++) {
                                String flagCode = amf[f].getFlagCode();
                                String desc = amf[f].getLongDescription();
                                if (flagCode.equals(val) || desc.equals(val)) {
                                    amf[f].setSelected(true);
                                    bFlag = true;
                                }
                            }

                            if (bFlag && !(att instanceof MultiFlagAttribute)) {
                                break;
                            }
                        }

                        if (!bFlag) {
                            _db.debug(D.EBUG_SPEW,strTraceBase + " can't input for flag attribute " + s);
                            sbrEx.add("Unable to input for flag attribute " + s);
                            throw sbrEx;
                        }
                        try {
                            fa.put(amf);
                        } catch (EANBusinessRuleException bre) {
                            bre.printStackTrace();
                            sbrEx.add(bre.getMessage());
                            throw sbrEx;
                        }
                    }
                } else {
                    sbrEx.add("EntityType: " + _strEntityType + " Unable to search on att: " + strAttrCode);
                    throw sbrEx;
                }
            }
        }

        sai.setCheckLimit(false);
        sai.disableUIAfterCache();

        _prof = setProfValOnEffOn(_db, _prof);
        el = sai.executeAction(_db, _prof);

        return el;
    }

    public OPICMList getCopyAttList(EntityItem _eiFrom, String _strEntityType, String[] aCopyAttributes) {
        OPICMList attList = new OPICMList();
        for (int i=0; i < aCopyAttributes.length; i++) {
            String str = aCopyAttributes[i];
            int iEqual = str.indexOf("=");
            String strAttrTo = str.substring(0, iEqual);
            String strAttrFrom = str.substring(iEqual+1);
            String strAttrValue = getAttrValue(_eiFrom, strAttrFrom);
            if (strAttrValue.length() > 0) {
                attList.put(_strEntityType + ":" + strAttrTo, strAttrTo + "=" + strAttrValue);
            }
        }

        return attList;
    }

    public boolean isDateFormat(String _strDate) {
        //String strTraceBase = "PDGUtility isDateFormat ";
        if ((_strDate != null && _strDate.length() == 10) || (_strDate != null && _strDate.length() == 26)) {
            if (_strDate.length() == 10) {
                if (_strDate.charAt(4) == '-' && _strDate.charAt(7) == '-') {
                   // is in format XXXX-XX-XX
                   for (int i=0; i < _strDate.length(); i++) {
                       char c = _strDate.charAt(i);
                       if (i!=4 && i!=7) {
                           if (!Character.isDigit(c)) {
                               return false;
                           }
                       }
                   }
               } else {
                   return false;
               }
           } else if (_strDate.length() == 26) {
               // in format "1980-01-01-00.00.00.000000"
               if (_strDate.charAt(4) == '-' && _strDate.charAt(7) == '-' && _strDate.charAt(10) == '-'
                   && _strDate.charAt(13) == '.' && _strDate.charAt(16) == '.' && _strDate.charAt(19) == '.') {
                   for (int i=0; i < _strDate.length(); i++) {
                       char c = _strDate.charAt(i);
                       if (i != 4 && i != 7 && i != 10 && i != 13 && i != 16 && i != 19) {
                           if (!Character.isDigit(c)) {
                               return false;
                           }
                       }
                   }

               } else {
                   return false;
               }
           }
       } else {
           return false;
       }
       return true;
   }

   public String getDate(String _strDate, int _days) {
       if (_strDate == null || _strDate.length() <= 0) {
           return "";
       }
       Date date1 = Date.valueOf(_strDate);

       long id1 = date1.getTime()/1000;
       long time = _days * 24 * 60 * 60;
       long id2 = id1 + time;
       Date date2 = new Date(id2*1000);

       return date2.toString();
   }

   public String getEarlierDate(String _strDate, int _days) {
       if (_strDate == null || _strDate.length() <= 0) {
           return "";
       }
       Date date1 = Date.valueOf(_strDate);

       long id1 = date1.getTime()/1000;
       long time = _days * 24 * 60 * 60;
       long id2 = id1 - time;
       Date date2 = new Date(id2*1000);

       return date2.toString();
   }


   public String getMtrnCodes(Database _db, Profile _prof, String _strGenareaCode, String _strMtnr, String _strCondType) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
       String strTraceBase = "PDGUtility getMtrnCodes method ";
       //_db.debug(D.EBUG_SPEW,strTraceBase + _strGenareaCode + ":" + _strMtnr + ":" + _strCondType);
       if (_strGenareaCode == null || _strGenareaCode.length() <=0) {
           return DEFAULTMTRNCODE;
       }
       // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        ReturnStatus returnStatus = new ReturnStatus(-1);

        try {
            rs = _db.callGBL9304(returnStatus, _strGenareaCode, _strMtnr, _strCondType);
            rdrs = new ReturnDataResultSet(rs);
        } finally {
			if (rs!=null){
	            rs.close();
	            rs = null;
			}
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }

        String strSalesStatus = "";
        for (int ii = 0; ii < rdrs.size(); ii++) {
            String strMATERIALSTATUS = rdrs.getColumn(ii,0).trim();
            _db.debug(D.EBUG_SPEW,strTraceBase + " GBL9304 result: " + strMATERIALSTATUS);
            if (strMATERIALSTATUS != null && strMATERIALSTATUS.length() > 0) {
                strSalesStatus = strMATERIALSTATUS;
                break;
            }
        }

        if (strSalesStatus != null && strSalesStatus.length() > 0) {
            return strSalesStatus;
        }
        return DEFAULTMTRNCODE;

    }

    public EANList getSalesStatusForCountry(Database _db, Profile _prof, String _strGenareaCode) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
        String strTraceBase = "PDGUtility getSalesStatusForCountry method ";
        //_db.debug(D.EBUG_SPEW,strTraceBase + _strGenareaCode);
        // Initialize some SP specific objects needed in this method
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;
        ReturnStatus returnStatus = new ReturnStatus(-1);
        EANList returnList = new EANList();
        try {
            rs = _db.callGBL9305(returnStatus, _strGenareaCode);
            rdrs = new ReturnDataResultSet(rs);
        } finally {
			if (rs!=null){
	            rs.close();
	            rs = null;
			}
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
        for (int ii = 0; ii < rdrs.size(); ii++) {
            String strMATNR = rdrs.getColumn(ii,0).trim();
            String strVARCOND = rdrs.getColumn(ii,1).trim();
            String strVARCONDTYPE = rdrs.getColumn(ii,2).trim();
            String strSALESORG = rdrs.getColumn(ii,3).trim();
            String strMATERIALSTATUS = rdrs.getColumn(ii,4).trim();
            String strMATERIALSTATUSDATE = rdrs.getColumn(ii,5).trim();
            String strLASTUPDATED = rdrs.getColumn(ii,6).trim();
            String strMARKEDFORDELETION = rdrs.getColumn(ii,7).trim();
            _db.debug(D.EBUG_SPEW, strTraceBase + " gbl 9305 result:" + strMATNR + ":" + strVARCOND
                      + ":" + strVARCONDTYPE + ":" + strSALESORG + ":" + strMATERIALSTATUS
                      + ":" + strMATERIALSTATUSDATE + ":" + strLASTUPDATED + ":" + strMARKEDFORDELETION);

            String strKey = strMATNR + strVARCOND + strVARCONDTYPE + strSALESORG;
            if (returnList.get(strKey) == null) {
                SalesStatusItem ssi = new SalesStatusItem(_prof, strKey);
                ssi.setMATNR(strMATNR);
                ssi.setVARCOND(strVARCOND);
                ssi.setVARCONDTYPE(strVARCONDTYPE);
                ssi.setSALESORG(strSALESORG);
                ssi.setMATERIALSTATUS(strMATERIALSTATUS);
                ssi.setMATERIALSTATUSDATE(strMATERIALSTATUSDATE);
                ssi.setLASTUPDATED(strLASTUPDATED);
                ssi.setMARKEDFORDELETION(strMARKEDFORDELETION);
                returnList.put(ssi);
            }

        }
        return returnList;

    }

    public String getGENAREACODE(Database _db, Profile _prof, ExtractActionItem _xai, EntityItem _ei, String _strCountry) throws  SQLException,MiddlewareException, MiddlewareShutdownInProgressException {
        //String strTraceBase = "PDGUtility getGENAREACODE method ";
        //_db.debug(D.EBUG_SPEW,strTraceBase + _strCountry);
        String strGENAREACODE = "";
        EntityItem [] eiParm = {_ei};
        EntityList elGA = EntityList.getEntityList(_db, _prof, _xai, eiParm);
        EntityGroup egGA = elGA.getEntityGroup("GENERALAREA");
        for (int i=0; i < egGA.getEntityItemCount(); i++) {
            EntityItem eiGA = egGA.getEntityItem(i);
            String strGENAREANAME = getAttrValue(eiGA, "GENAREANAME");
            if (strGENAREANAME.equals(_strCountry)) {
                strGENAREACODE = getAttrValue(eiGA, "GENAREACODE") + ":" + getAttrValueDesc(eiGA, "GENAREACODE");
                //_db.debug(D.EBUG_SPEW,strTraceBase + strGENAREACODE);
                return strGENAREACODE;
            }
        }

		elGA.dereference();
        return strGENAREACODE;
    }

    public boolean isActive(Database _db, Profile _prof, EntityItem _ei) {
        //String strTraceBase = "PDGUtility isActive method ";
        try {
            EntityChangeHistoryGroup chg = new EntityChangeHistoryGroup(_db, _prof, _ei);
            for (int i=0; i < chg.getChangeHistoryItemCount(); i++) {
                EntityChangeHistoryItem chi = (EntityChangeHistoryItem) chg.getChangeHistoryItem(i);

                //_db.debug(D.EBUG_SPEW, strTraceBase + chi.dump(false));
                if (chi.isValid()) {
                    return true;
                }
            }
        } catch (Exception _ex) {
            _ex.printStackTrace();
        }
        return false;
    }

    public String getPreviousValue(Database _db, Profile _prof, EntityItem _ei, String _strAttrCode) {
        String strTraceBase = "PDGUtility getPreviousValue method ";
        String strValue = "";
        EANAttribute att = _ei.getAttribute(_strAttrCode);
        if (att != null) {

            try {
                AttributeChangeHistoryGroup achg = new AttributeChangeHistoryGroup(_db, _prof, att);

                String[] aSort = new String[achg.getChangeHistoryItemCount()];
                for (int i=0; i < achg.getChangeHistoryItemCount(); i++) {
                    AttributeChangeHistoryItem chi = (AttributeChangeHistoryItem) achg.getChangeHistoryItem(i);

                    //_db.debug(D.EBUG_SPEW, strTraceBase + chi.dump(false));
                    String strChangeDate = chi.getChangeDate();
                    aSort[i] = strChangeDate + ":" + chi.getKey();
                }
                Arrays.sort(aSort);

                int iP = aSort.length - 2;
                if (iP > 0) {
                    String s = aSort[iP];
                    int iColon = s.indexOf(":");
                    String strKey = s.substring(iColon+1);
                    AttributeChangeHistoryItem preItem = (AttributeChangeHistoryItem)achg.getChangeHistoryItem(strKey);
                    if (att instanceof EANFlagAttribute) {
                        strValue = preItem.getFlagCode();
                    } else {
                        strValue = preItem.getAttributeValue();
                    }
                }
            } catch (Exception ex) {
                System.out.println(strTraceBase + ex.toString());
                ex.printStackTrace(System.out);
            }

        }
        return strValue;
    }

    public ChangeHistoryItem getCurrentChangeItem(ChangeHistoryGroup _chg) {
        for (int i = 0; i < _chg.getChangeHistoryItemCount(); i++) {
            ChangeHistoryItem chi = (ChangeHistoryItem) _chg.getChangeHistoryItem(i);

            if (chi.isValid()) {
                return chi;
            }
        }

        return null;
    }

    public void queueEI(Database _db, Profile _prof, String _strEntityType, int _iEntityID, String _strQueueName) {
        //String strTraceBase = "PDGUtility queueEI method ";
        try {
            ResultSet rs = null;

            String strEnterprise = _prof.getEnterprise();
            int iOPWGID = _prof.getOPWGID();
            String strValOn = _prof.getValOn();
            String strSQL = "select count(*) from opicm.queue " +
                            "where enterprise = '" + strEnterprise + "' " +
                            "and queue = '" + _strQueueName + "' " +
                            "and entitytype = '" + _strEntityType + "' " +
                            "and entityid = " + _iEntityID + " " +
                            "and status in (20, 50)";
            PreparedStatement ps = null;

            Connection con = _db.getPDHConnection();

            ps = con.prepareStatement(strSQL);
            rs = ps.executeQuery();
            //_db.debug(D.EBUG_DETAIL, strTraceBase + " executed SQL:" + strSQL);
            int iCount = 0;
            if (rs.next()) {
                iCount = rs.getInt(1);
            }
            //_db.debug(D.EBUG_DETAIL, strTraceBase + " iCount: " + iCount);
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            con = null;
            _db.commit();
            _db.freeStatement();
            _db.isPending();

            if (iCount <= 0) {
                _db.callGBL8009(new ReturnStatus(-1), strEnterprise, iOPWGID, 0, _strQueueName, 20, _strEntityType, _iEntityID, strValOn, FOREVER);
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void generateData(Database _db, Profile _prof, StringBuffer _sbMissing, EntityItem _eiRoot) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "PDGUtility generateData ";
        //_db.debug(D.EBUG_SPEW, strTraceBase);

        //m_sbActivities = new StringBuffer();
        Vector vctReturnEntityKeys = new Vector();
        EANList eiList = new EANList();
        try {
            //boolean bRestart = false;
            StringTokenizer st = new StringTokenizer(_sbMissing.toString(),"\n");
            Hashtable ht = new Hashtable();

            while (st.hasMoreTokens()) {
                String s = st.nextToken();
                //_db.debug(D.EBUG_SPEW,strTraceBase + "s: " + s);
                StringTokenizer st1 = new StringTokenizer(s,"|");

                if (st1.hasMoreTokens()) {
                    boolean bContWCreate = true;
                    EntityItem currentEI = null;

                    String strParentEntity = st1.nextToken().trim();
                    int iLevel = Integer.parseInt(st1.nextToken());
                    String strDirection = st1.nextToken().trim();
                    // get parent for later links
                    EntityItem eiParent = null;
                    if (strParentEntity != null && strParentEntity.length() > 0) {
                        StringTokenizer stParent = new StringTokenizer(strParentEntity,"-");
                        if (stParent.hasMoreTokens()) {
                            String strParentType = stParent.nextToken();
                            int iParentID = Integer.parseInt(stParent.nextToken());
                            eiParent = new EntityItem(null, _prof, strParentType, iParentID);
                        }
                    } else {
                        eiParent = (EntityItem)ht.get((iLevel-1) + "");
                    }

                    if (eiParent == null) {
                        eiParent = _eiRoot;
                    }

                    // get stuff for Entity
                    String strEntity = st1.nextToken();
                    int i1 = strEntity.indexOf(":");
                    String strEntityType = strEntity;
                    String strAttributes = "";
                    if (i1 > -1 ){
                        strEntityType = strEntity.substring(0, i1);
                        strAttributes = strEntity.substring(i1+1);
                    }

                    String strAction = st1.nextToken();

                    int iSaveAct = strAction.indexOf("saveAct");
                    // get Relator info
                    String strRelatorInfo = st1.nextToken();
                    String strRelatorType = "";
                    int iAttrO = strRelatorInfo.indexOf("[");
                    if (iAttrO > -1) {
                        strRelatorType = strRelatorInfo.substring(0,iAttrO);
                    } else {
                        strRelatorType = strRelatorInfo;
                    }

                    //find the item if needed
                    int iFind = strAction.indexOf("find");
                    if (iFind > -1) {
                        if (strAttributes.indexOf("map") >= 0) {
                            int iEqual = strAttributes.indexOf("=");
                            String strHead = strAttributes.substring(4, iEqual);
                            currentEI = findEntityItem(eiList, strEntityType, strAttributes);
                            if (currentEI == null) {
                                String strSai = (String)m_saiList.get(strEntityType);
                                EntityItem[] aei = null;
                                if (strHead.indexOf(":") >= 0) {
                                    aei = dynaSearchII(_db, _prof, _eiRoot, strSai, strEntityType, strAttributes);
                                } else {
                                    aei = dynaSearch(_db, _prof, _eiRoot, strSai, strEntityType, strAttributes);
                                }

                                if (aei.length > 0) {
                                    currentEI = aei[0];
                                    // save for later search
                                    eiList.put(currentEI);
                                    bContWCreate = false;
                                }

                                if (currentEI != null) {
                                    ht.put(iLevel + "", currentEI);
                                }
                            }
                        }
                    }

                    int iUpdate = strAction.indexOf("update");
                    if (iUpdate > -1 && currentEI != null) {
                        OPICMList attList = getAttributeListForUpdate(strEntityType, strAttributes);

                        updateAttribute(_db, _prof, currentEI, attList);
                        bContWCreate = false;
                    }

                    // link them if there's command link
                    int iLink = strAction.indexOf("linkParent");
                    if (iLink > -1 && currentEI != null) {
                       // use parent entity, relator,link

                       if (eiParent != null) {
                           OPICMList ol = null;

                           int iCopy = strRelatorInfo.indexOf("copyUpdate");

                           EntityItem eiCR = null;
                           if (iCopy >= 0) {
                               String strRType = "";
                               String strRAttr = "";
                               int iAO = strRelatorInfo.indexOf("[");
                               int iAC = strRelatorInfo.indexOf("]");
                               if (iAO > -1) {
                                   strRType = strRelatorInfo.substring(0,iAO);
                                   strRAttr = strRelatorInfo.substring(iAO+1, (iAC > -1 ? iAC: strRelatorInfo.length()));
                               }

                               String strCopyR = strRAttr.substring(iCopy);
                               int iE = strCopyR.indexOf(";");
                               if (iE > -1) {
                                   strCopyR = strCopyR.substring(0,iE);
                               }
                               int iUn = strCopyR.indexOf("_");

                               if (iUn > -1) {
                                   String strCF = strCopyR.substring(iUn+1);
                                   eiCR = (EntityItem)eiList.get(strCF);
                                   if (eiCR == null) {
                                       _db.debug(D.EBUG_SPEW,strTraceBase + "copy relator 03: eiCR is null");
                                   }
                               }
                               strRelatorInfo = strRType;
                           }

                           if (strDirection.equals("U")) {
                               EntityItem[] aei = {eiParent};
                               ol = linkEntities(_db, _prof, currentEI, aei, strRelatorInfo);
                           } else {
                               EntityItem[] aei = {currentEI};
                               ol = linkEntities(_db, _prof, eiParent, aei, strRelatorInfo);
                           }

                           for (int i=0; i < ol.size(); i++) {
                               Object obj = ol.getAt(i);
                               if (obj instanceof ReturnRelatorKey) {
                                   ReturnRelatorKey rrk = (ReturnRelatorKey)obj;
                                   String strRType = rrk.getEntityType();

                                   EntityItem ei = new EntityItem(null, _prof, rrk.getEntityType(), rrk.getReturnID());
                                   if (eiCR != null) {
                                       copyEntity(_db, _prof, eiCR, ei, "Y", true);
                                   }
                                   EntityGroup egR = getEntityGroup(strRType);
                                   if (egR ==null) {
                                       egR = new EntityGroup(null, _db, _prof, strRType, "Edit", false);
                                   }
                                   _prof = setProfValOnEffOn(_db, _prof);
                                   ei = new EntityItem(egR, _prof, _db, rrk.getEntityType(), rrk.getReturnID());
                                   eiList.put(ei);
                               }
                           }

                           if (iSaveAct >= 0) {
                               m_sbActivities.append("<br />" + getActivities());
                               resetActivities();
                           }

                           // after find/link, pull the VE, and test again if required.
                           int iRestart = strAction.indexOf("restart");
                           if (iRestart > -1) {
                               //bRestart = true;
                               break;
                           }
                       }

                       bContWCreate = false;
                   }

                   if (bContWCreate) {
                       int iCreate = strAction.indexOf("create");
                       if (iCreate > -1) {
                           //prepare the list of attributes
                           OPICMList attList = getAttributeListForCreate(strEntityType, strAttributes, strRelatorInfo);
                           String strCai = (String)m_caiSList.get(strRelatorType);
                           if (strDirection.equals("U")) {
                              // create stand alone entity
                              attList = getAttributeListForCreate(strEntityType, strAttributes, "");
                              strCai = (String)m_caiSList.get(strEntityType);
                          }

                          currentEI = createEntityByRST(_db, _prof, eiParent, attList, strCai, strRelatorType, strEntityType);
                          _db.test(currentEI != null, " ei is null for: " + s);
                          ht.put(iLevel + "", currentEI);

                          // save for later search
                          eiList.put(currentEI);

                          if (strDirection.equals("U")) {
                              // link to 1 level up
                              EntityItem[] aei = {eiParent};
                              OPICMList ol = linkEntities(_db, _prof, currentEI, aei, strRelatorInfo);

                              for (int i=0; i < ol.size(); i++) {
                                  Object obj = ol.getAt(i);
                                  if (obj instanceof ReturnRelatorKey) {
                                      ReturnRelatorKey rrk = (ReturnRelatorKey)obj;
                                      String strRType = rrk.getEntityType();
                                      EntityGroup eg = getEntityGroup(strRType);
                                      if (eg ==null) {
                                          eg = new EntityGroup(null, _db, _prof, strRType, "Edit", false);
                                      }
                                      _prof = setProfValOnEffOn(_db, _prof);
                                      EntityItem ei = new EntityItem(eg, _prof, _db, rrk.getEntityType(), rrk.getReturnID());
                                      eiList.put(ei);
                                  }
                              }
                          }

                          if (iSaveAct >= 0) {
                              m_sbActivities.append("<br />" + getActivities());
                              resetActivities();
                          }
                      }

                      // create the item
                      int iCreateCopy = strAction.indexOf("copyUpdate");
                      if (iCreateCopy > -1) {
                         //prepare the list of attributes
                         //OPICMList attList = getAttributeListForCreate(strEntityType, strAttributes, strRelatorInfo);
                         String strCopyUpdate = strAction.substring(iCreateCopy);
                         int iEnd = strCopyUpdate.indexOf(";");
                         if (iEnd > -1) {
                             strCopyUpdate = strCopyUpdate.substring(0,iEnd);
                         }
                         int iU = strCopyUpdate.indexOf("_");
                         EntityItem eiCF = null;
                         if (iU > -1) {
                             String strCF = strCopyUpdate.substring(iU+1);
                             eiCF = (EntityItem)eiList.get(strCF);
                         }

                         if (eiCF == null) {
                             continue;
                         }
                         currentEI = createAndCopy(_db, _prof, eiCF, strEntityType, "Y");

                         _db.test(currentEI != null, " ei is null for " + s);
                         ht.put(iLevel + "", currentEI);

                         // update some attributes
                         OPICMList attL = getAttributeListForUpdate(strEntityType, strAttributes);

                         _prof = setProfValOnEffOn(_db, _prof);

                         try {
                             updateAttribute(_db, _prof, currentEI, attL);
                         } catch (MiddlewareException _mex) {
                             _mex.printStackTrace();
                             EANUtility.deactivateEntity(_db, _prof, currentEI);
                         }
                         eiList.put(currentEI);

                         if (iSaveAct >= 0) {
                             m_sbActivities.append("<ITEM><ACTION>Create</ACTION><ENTITYKEY>" + currentEI.getKey() + "</ENTITYKEY><ENTITYDISPLAY>" + PDGUtility.convertToHTML(currentEI.toString()) + "</ENTITYDISPLAY><PARENT>" + eiParent.getKey() + ":" + PDGUtility.convertToHTML(eiParent.toString()) + "</PARENT></ITEM>\n");
                             resetActivities();
                         }

                         int iCopy = strRelatorInfo.indexOf("copyUpdate");

                         EntityItem eiCR = null;
                         if (iCopy >= 0) {
                             String strRType = "";
                             String strRAttr = "";
                             int iAO = strRelatorInfo.indexOf("[");
                             int iAC = strRelatorInfo.indexOf("]");
                             if (iAO > -1) {
                                 strRType = strRelatorInfo.substring(0,iAO);
                                 strRAttr = strRelatorInfo.substring(iAO+1, (iAC > -1 ? iAC: strRelatorInfo.length()));
                             }

                             String strCopyR = strRAttr.substring(iCopy);
                             int iE = strCopyR.indexOf(";");
                             if (iE > -1) {
                                 strCopyR = strCopyR.substring(0,iE);
                             }
                             int iUn = strCopyR.indexOf("_");

                             if (iUn > -1) {
                                 String strCF = strCopyR.substring(iUn+1);
                                 eiCR = (EntityItem)eiList.get(strCF);

                                 if (eiCR == null) {
                                     _db.debug(D.EBUG_SPEW,strTraceBase + "copy relator 03: eiCR is null");
                                 }
                             }
                             strRelatorInfo = strRType;

                         }

                         // link to the parent

                         OPICMList ol = null;

                         if (strDirection.equals("U")) {
                             EntityItem[] aei = {eiParent};
                             ol = linkEntities(_db, _prof, currentEI, aei, strRelatorInfo);
                         } else {
                             EntityItem[] aei = {currentEI};
                             ol = linkEntities(_db, _prof, eiParent, aei, strRelatorInfo);
                         }

                         for (int i=0; i < ol.size(); i++) {
                             Object obj = ol.getAt(i);
                             if (obj instanceof ReturnRelatorKey) {
                                 ReturnRelatorKey rrk = (ReturnRelatorKey)obj;
                                 String strRType = rrk.getEntityType();
                                 EntityItem ei = new EntityItem(null, _prof, rrk.getEntityType(), rrk.getReturnID());
                                 if (eiCR != null) {
                                     copyEntity(_db, _prof, eiCR, ei, "Y", true);
                                 }
                                 EntityGroup egR = getEntityGroup(strRType);
                                 if (egR ==null) {
                                     egR = new EntityGroup(null, _db, _prof, strRType, "Edit", false);
                                 }
                                 _prof = setProfValOnEffOn(_db, _prof);
                                 ei = new EntityItem(egR, _prof, _db, rrk.getEntityType(), rrk.getReturnID());
                                 eiList.put(ei);
                             }
                         }
                     }
                 }

                 int ilinkAllInEL = strAction.indexOf("linkAllInEL");
                 if (ilinkAllInEL > -1) {
                     String strLinkAllEI = strAction.substring(ilinkAllInEL);
                     int iEnd = strLinkAllEI.indexOf(";");
                     if (iEnd > -1) {
                         strLinkAllEI=strLinkAllEI.substring(0,iEnd);
                     }

                     int iU = strLinkAllEI.indexOf("_");
                     if (iU > -1) {
                         strLinkAllEI = strLinkAllEI.substring(iU+1);
                     }

                     if (strLinkAllEI != null) {
                     } else {
                         _db.debug(D.EBUG_SPEW,strTraceBase + " linkAllInEL eg is null");
                     }

                 }

                 // save entities for later link to root
                 int iLinkRoot = strAction.indexOf("linkRoot");
                 if (iLinkRoot > -1) {
                     String strLinkRoot = strAction.substring(iLinkRoot);
                     int iEnd = strLinkRoot.indexOf(";");
                     if (iEnd > -1) {
                         strLinkRoot=strLinkRoot.substring(0,iEnd);
                     }
                     int iU = strLinkRoot.indexOf("_");
                     if (iU > -1) {
                         String strRelator = strLinkRoot.substring(iU+1);
                         EntityItem[] aei = {currentEI};
                         linkEntities(_db, _prof, _eiRoot, aei, strRelator);
                     }
                 }

                 int iSaveEI = strAction.indexOf("saveEI");
                 if (iSaveEI >=0 ) {
                     _db.debug(D.EBUG_SPEW,strTraceBase + " save entity");
                     //m_savedEIList.put(currentEI);
                 }
             }
         }

     } catch (SBRException ex) {
         if (vctReturnEntityKeys.size() > 0) {
             link(_db, _prof, vctReturnEntityKeys);
         }

         throw ex;
     }
 }

    public void putCreateAction(String _strKey, String _strAction) {
        if (_strKey != null && _strAction != null) {
            m_caiSList.put(_strKey, _strAction);
        }
    }

    public void putSearchAction(String _strKey, String _strAction) {
        if (_strKey != null && _strAction != null) {
            m_saiList.put(_strKey, _strAction);
        }
    }

    public void memory(boolean _bShow) {
        System.out.println("PDGUtility memory before gc: " + D.etermineMemory());
        System.gc();
        System.out.println("PDGUtility memory after gc: " + D.etermineMemory());
    }

    public Vector getChangedEntities(Database _db, Profile _prof, String _strEntityType, String _strVE, String _strStartDate, String _strEndDate, int _iType, int _iFilter, String _strChangedET, int _iChangedID) {
        String strTraceBase = "PDGUtility getChangedEntities method ";
        // Here we insert record into the trsNetterPass2 table
        // _db.callGBL8101(returnStatus, iSessionID, strEnterprise, strEntityType, iEntityID, strActionItemKey, m_strEndDate);
        Vector vReturn = new Vector();
        try {
            ReturnDataResultSet rdrs = null;
            ResultSet rs = null;

            String strEnterprise = _prof.getEnterprise();
            String strRoleCode = _prof.getRoleCode();
            int iSessionID = _db.getNewSessionID();

            // Now.. pass bypass 1

            try {
                rs = _db.callGBL8104( new ReturnStatus(-1),
                                      iSessionID,
                                      strEnterprise,
                                      _strEntityType,
                                      _strVE,
                                      strRoleCode,
                                      _strStartDate,
                                      _strEndDate,
                                      _iType,
                                      _strChangedET,
                                      _iChangedID,
                                      _iFilter);
                // Lets load them into a return data result set that contains all our transactions
                rdrs = new ReturnDataResultSet(rs);
            } finally {
				if (rs!=null){
                	rs.close();
                	rs = null;
				}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            _db.debug(D.EBUG_DETAIL, strTraceBase + "gbl8104:recordcount:" + rdrs.size());

            for (int i = 0; i < rdrs.size(); i++) {
                StringBuffer sb = new StringBuffer();
                int y = 0;
                String strGenArea = rdrs.getColumn(i, y++);
                String strRootType = rdrs.getColumn(i, y++);
                int iRootID = rdrs.getColumnInt(i, y++);
                String strRootTran = rdrs.getColumn(i, y++);
                String strChildType = rdrs.getColumn(i, y++);
                int iChildID = rdrs.getColumnInt(i, y++);
                String strChildTran = rdrs.getColumn(i, y++);
                int iChildLevel = rdrs.getColumnInt(i, y++);
                String strChildClass = rdrs.getColumn(i, y++);
                String strChildPath = rdrs.getColumn(i, y++);
                String strRelChildType = rdrs.getColumn(i, y++);
                int iRelChildID = rdrs.getColumnInt(i, y++);
                String strRelParentType = rdrs.getColumn(i, y++);
                int iRelParentID = rdrs.getColumnInt(i, y++);
                String strChildGenArea = rdrs.getColumn(i, y++);

                sb.append(strGenArea
                          + ":"
                          + strRootType
                          + ":"
                          + iRootID
                          + ":"
                          + strRootTran
                          + ":"
                          + strChildType
                          + ":"
                          + iChildID
                          + ":"
                          + strChildTran
                          + ":"
                          + iChildLevel
                          + ":"
                          + strChildClass
                          + ":"
                          + strChildPath
                          + ":"
                          + strRelChildType
                          + ":"
                          + iRelChildID
                          + ":"
                          + strRelParentType
                          + ":"
                          + iRelParentID
                          + ":"
                          + strChildGenArea);
                _db.debug(D.EBUG_SPEW,strTraceBase + "gbl8104:answer:" + sb.toString());
                vReturn.addElement(sb.toString());
            }

            // Now remove all the records to clean up after yourself
            _db.callGBL8109(new ReturnStatus(-1), iSessionID, strEnterprise);
            _db.commit();
            _db.freeStatement();
            _db.isPending();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MiddlewareException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
        } finally {
                // TO DO
        }
        return vReturn;
    }

    public String getEarliestTime(Database _db, Profile _prof, String _strEntityType) {
        ResultSet rs = null;
        String strValFrom = "";
        String strEnterprise=_prof.getEnterprise();

        String strSQL1 =
            "SELECT " +
            " min(valfrom) " +
            "FROM opicm.entity E " +
            "WHERE E.Enterprise = ? " +
            "AND E.EntityType = ? " +
            "AND E.ValFrom <= current timestamp AND  current timestamp < E.ValTo " +
            "AND E.EffFrom <= current timestamp AND current timestamp < E.EffTo ";

        PreparedStatement ps = null;

        try {
            Connection con = _db.getPDHConnection();
            ps = con.prepareStatement(strSQL1);
            //MyETs
            ps.setString(1,strEnterprise);
            ps.setString(2, _strEntityType);
            rs = ps.executeQuery();

            ReturnDataResultSet rdrs = new ReturnDataResultSet(rs);
            rs.close();
            rs = null;
            _db.commit();
            _db.freeStatement();
            _db.isPending();

            for (int ii = 0; ii < rdrs.size(); ii++) {
                strValFrom = rdrs.getColumnDate(ii, 0);

                _db.debug(D.EBUG_SPEW, "gbl6033:answer:" + strValFrom);

                break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(rs != null) {
                    rs.close();
                    rs = null;
                }
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return strValFrom;
    }
}
