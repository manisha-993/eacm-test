// Licensed Materials -- Property of IBM
//
// (c) Copyright International Business Machines Corporation, 2003
// All Rights Reserved.
//
// $Log: ODSMethods.java,v $
// Revision 1.468  2006/11/01 22:21:02  gregg
// Here is the full requirement for setting withdrawaldate in HVEC land.
// This is for CSOL, CVAR, CCTO, CB.
// Set the ODS withdrawal date to PDH withdrawal date if
// 1. there is a record in ODS and ODS withdrawal date is null
// 2. there is a record in ODS and ODS & PDH withdrawal dates mismatch (irrespective of PDH withdrawal is in the past or not).
// Do not set the ODS withdrawal date and do not insert/update the record in the ODS if
// 1. ODS & PDH withdrawals match.
// 2. there is no record for this product in the ODS (this means, don't insert the record in the ODS).
//
// Revision 1.467  2006/10/13 18:23:46  gregg
// debugs for skipWDrawDate
//
// Revision 1.466  2006/08/08 22:31:57  gregg
// skipWithdraw: per PRaveen, do not insert records in ODS for pdh withdraw dates in the past if they do not already exist.
//
// Revision 1.465  2006/06/07 01:03:47  gregg
// cut and paste induced npe fix
//
// Revision 1.464  2006/06/07 00:46:24  gregg
// more OF-SBB-CDR
//
// Revision 1.463  2006/06/06 23:49:36  gregg
// added OF-SBB-CDR relationship. Apparently it was never in for CDR and needs to be.
//
// Revision 1.462  2006/03/08 20:38:12  gregg
// yikes, ok removing updatevalfrom logic again.. instead, adding necessary relators to crosspostrelator property
//
// Revision 1.461  2006/03/08 20:17:05  gregg
// adding back in crossPostII for relator case
//
// Revision 1.460  2006/03/07 22:33:08  gregg
// back out previous changes only
//
// Revision 1.459  2006/02/09 21:09:19  gregg
// crossPostII fix
//
// Revision 1.458  2006/02/09 19:59:33  gregg
// more crossPostII for granparent recrsive crossposting
//
// Revision 1.457  2006/02/01 22:03:33  gregg
// prepping for CR: crosspost valfrom update up to grandparents
//
// Revision 1.456  2006/01/31 19:05:37  dave
// 9200 cleanup
//
// Revision 1.455  2006/01/10 18:40:11  gregg
// MN26472195
//
// Revision 1.454  2005/12/05 23:05:01  gregg
// multiattribtues:fix for VAR/WWPSGCDROPTITYPE child sql
//
// Revision 1.453  2005/12/02 20:50:24  gregg
// debugging
//
// Revision 1.452  2005/12/02 20:38:40  gregg
// debug stmt
//
// Revision 1.451  2005/09/10 18:45:34  dave
// backed off some SPEW
//
// Revision 1.450  2005/09/10 16:39:02  dave
// parm change
//
// Revision 1.449  2005/09/10 15:59:39  dave
// turning spew to detail for multiatts
//
// Revision 1.448  2005/09/07 20:06:40  gregg
// remove dead multiatt columns/disable resetMultiAttributeTable
//
// Revision 1.447  2005/09/07 20:04:42  gregg
// update?
//
// Revision 1.446  2005/08/30 20:40:08  gregg
// fix for strOFWSDESCChildSQL
//
// Revision 1.445  2005/08/30 20:21:31  gregg
// getting rid of last merging
//
// Revision 1.443  2005/08/29 20:47:52  gregg
// no longer reset multiattr table on init
//
// Revision 1.442  2005/08/29 19:21:37  gregg
// fix order by for child sql - let's go etype,eid,attcode,nls
//
// Revision 1.441  2005/08/29 18:44:19  gregg
// some rel fix stuff
//
// Revision 1.440  2005/08/29 18:05:44  gregg
// of root fix
//
// Revision 1.439  2005/08/29 17:50:44  gregg
// more null atts for multiattr (process all value removals)
//
// Revision 1.438  2005/08/26 23:10:26  gregg
// mass fix multiattr root finder SQL to not target by attribute
//
// Revision 1.437  2005/08/26 20:36:14  gregg
// sql
//
// Revision 1.436  2005/08/26 19:29:04  gregg
// more root sql fix
//
// Revision 1.435  2005/08/26 17:57:13  gregg
// fixing some root SQL to not look for atts
//
// Revision 1.434  2005/08/26 00:07:21  gregg
// more delete logic
//
// Revision 1.433  2005/08/25 23:42:26  gregg
// trying delete/insert pthing for multiattribute
//
// Revision 1.432  2005/08/25 22:58:16  gregg
// fix for deletes
//
// Revision 1.431  2005/08/25 21:57:11  gregg
// sql fix
//
// Revision 1.430  2005/08/25 21:32:48  gregg
// More MultiaTtribute:Lets get roots of deleted relators from PDH when one-off.
//
// Revision 1.429  2005/08/24 21:17:32  gregg
// some more debugs
//
// Revision 1.428  2005/08/24 20:45:22  gregg
// fix OFAPPROVALS_CERTS sql for real
//
// Revision 1.427  2005/08/24 17:47:33  gregg
// fix nls issue for multiattr/OFAPPROVALS_CERTS
//
// Revision 1.426  2005/08/24 17:40:43  gregg
// multiattribute add relator case + some trace stmts
//
// Revision 1.425  2005/08/23 22:29:14  gregg
// some fancy logging
//
// Revision 1.424  2005/08/23 22:06:42  gregg
// od1 != id1
//
// Revision 1.423  2005/08/22 14:57:37  gregg
// fix
//
// Revision 1.422  2005/08/22 14:03:12  gregg
// sql order by fix
//
// Revision 1.421  2005/08/20 01:25:53  gregg
// trimming!!!!
//
// Revision 1.420  2005/08/20 00:39:15  gregg
// union all -> union
//
// Revision 1.419  2005/08/20 00:27:19  gregg
// hacky fix for dup's in processMultiAttrNLSRecord
//
// Revision 1.418  2005/08/19 23:57:09  gregg
// PORT tweak for multiatts
//
// Revision 1.417  2005/08/18 18:53:19  gregg
// more sql
//
// Revision 1.416  2005/08/18 18:33:14  gregg
// more
//
// Revision 1.415  2005/08/18 18:10:33  gregg
// sql fix
//
// Revision 1.414  2005/08/18 17:39:43  gregg
// multiattribute att mappings
//
// Revision 1.413  2005/08/18 17:05:43  gregg
// nother PSL fix
//
// Revision 1.412  2005/08/18 16:53:20  gregg
// PSL/PSLAVAIL multiatt
//
// Revision 1.411  2005/08/17 23:56:07  gregg
// fix
//
// Revision 1.410  2005/08/17 23:45:04  gregg
// rdrs merge
//
// Revision 1.409  2005/08/17 23:40:07  gregg
// m.a.
//
// Revision 1.408  2005/08/17 22:46:01  gregg
// sql fix
//
// Revision 1.407  2005/08/17 22:21:22  gregg
// lotsa multiattributestuff
//
// Revision 1.406  2005/08/17 19:15:46  gregg
// change format of value from getMultiAttributeRelatorPath
//
// Revision 1.405  2005/08/17 18:03:58  gregg
// exception handling
//
// Revision 1.404  2005/08/17 17:58:38  gregg
// compile fix
//
// Revision 1.403  2005/08/17 17:49:52  gregg
// Ok, first pass at unlink/link fix for multiattributes
//
// Revision 1.402  2005/08/15 16:35:42  gregg
// sql fix
//
// Revision 1.401  2005/08/13 00:40:22  gregg
// last multiattr fix of the day.
//
// Revision 1.400  2005/08/13 00:13:49  gregg
// m.a.
//
// Revision 1.399  2005/08/12 23:52:32  gregg
// more deletes for multiattribute
//
// Revision 1.398  2005/08/12 22:29:29  gregg
// EXception throwing
//
// Revision 1.397  2005/08/12 22:23:35  gregg
// fix for deletes for multiattribute
//
// Revision 1.396  2005/08/12 20:18:15  gregg
// more bracket fix
//
// Revision 1.395  2005/08/12 19:50:29  gregg
// bracket fix.
//
// Revision 1.394  2005/08/12 19:38:39  gregg
// debug
//
// Revision 1.393  2005/08/12 17:48:51  gregg
// more debug
//
// Revision 1.392  2005/08/12 17:17:26  gregg
// more debugs
//
// Revision 1.391  2005/08/12 16:49:35  gregg
// some debugs
//
// Revision 1.390  2005/08/04 20:15:16  gregg
// fix
//
// Revision 1.389  2005/08/04 19:01:50  gregg
// more sql
//
// Revision 1.388  2005/08/04 18:26:59  gregg
// fix for multiattr sql
//
// Revision 1.387  2005/08/04 16:47:06  gregg
// multiattribute rootid fix
//
// Revision 1.386  2005/08/03 21:13:41  gregg
// sql fixes for multiattribute
//
// Revision 1.385  2005/08/03 17:06:21  gregg
// multiattribute performance tuning
//
// Revision 1.384  2005/08/03 00:24:15  gregg
// more multiattributes
//
// Revision 1.383  2005/08/02 20:12:53  gregg
// propertyize nls defs for multiattribute
//
// Revision 1.382  2005/08/02 18:27:03  gregg
// multiattribute - append "|" at end of ea. val
//
// Revision 1.381  2005/08/02 17:18:26  gregg
// nls specific queries for multiattribute init
//
// Revision 1.380  2005/08/01 19:24:29  gregg
// some adjustments for multiattr net logic + property-ize multiatrribute table name
//
// Revision 1.379  2005/07/29 21:45:07  gregg
// more refining init SQL for multiattributes..
//
// Revision 1.378  2005/07/28 18:20:02  gregg
// concat PSG to OFAPPROVALSCERTS in multiattr init
//
// Revision 1.377  2005/07/28 16:35:11  gregg
// moreSQL for multiattrs
//
// Revision 1.376  2005/07/27 20:42:02  gregg
// SQL change for multiattributes + temporarily disable someof init.
//
// Revision 1.375  2005/07/26 00:17:42  gregg
// catch an exception
//
// Revision 1.374  2005/07/25 21:23:01  gregg
// more logging changes for multiattribute
//
// Revision 1.373  2005/07/25 20:23:37  gregg
// commit after each rdrs - multiattributes...
//
// Revision 1.372  2005/07/25 17:11:47  gregg
// some performance tuning for init MultiAttribute table
//
// Revision 1.371  2005/07/25 17:04:30  gregg
// fix for TIF SQL
//
// Revision 1.370  2005/07/21 22:28:40  gregg
// fix
//
// Revision 1.369  2005/07/21 22:22:29  gregg
// np fix
//
// Revision 1.368  2005/07/21 21:28:47  gregg
// skip sort routine for init load, order by SQL
//
// Revision 1.367  2005/07/21 20:37:55  gregg
// more trace stmts
//
// Revision 1.366  2005/07/21 17:26:05  gregg
// braking up multiattribute init load SQL1/2
//
// Revision 1.365  2005/07/20 22:55:15  gregg
// temporarily disabling SQL1 in initMultiAttrs
//
// Revision 1.364  2005/07/20 22:31:52  gregg
// more debugs
//
// Revision 1.363  2005/07/20 21:27:08  gregg
// some debugs for multi atts
//
// Revision 1.362  2005/07/19 21:48:58  gregg
// multiattribute mappings v2.0
//
// Revision 1.361  2005/07/06 17:49:10  gregg
// fix for skip withdraw date logic. Now check for existence of ALL ods NLS records.
//
// Revision 1.360  2005/06/21 17:55:18  gregg
// date comping in isSkipWithdraw
//
// Revision 1.359  2005/06/21 17:35:45  gregg
// null ptr fix
//
// Revision 1.358  2005/06/21 17:29:14  gregg
// s'more debugs
//
// Revision 1.357  2005/06/21 16:47:16  gregg
// debug
//
// Revision 1.356  2005/06/16 16:37:03  gregg
// withdraw date filtering
//
// Revision 1.355  2005/06/14 07:40:30  dave
// fixing null pointer in code
//
// Revision 1.354  2005/06/08 21:33:28  gregg
// fix
//
// Revision 1.353  2005/06/08 17:23:26  gregg
// fix my Stopwatch
//
// Revision 1.352  2005/06/08 16:48:31  gregg
// null ptr fix
//
// Revision 1.351  2005/06/08 16:25:28  gregg
// split up SQL in initMultiAttributes()
//
// Revision 1.350  2005/06/02 22:07:42  gregg
// some tracing
//
// Revision 1.349  2005/05/19 22:32:50  gregg
// trace stmt
//
// Revision 1.348  2005/05/19 18:14:38  gregg
// comment
//
// Revision 1.347  2005/05/18 20:51:48  gregg
// fix
//
// Revision 1.346  2005/05/18 19:53:42  gregg
// put back sql for multi init
//
// Revision 1.345  2005/05/16 18:31:52  gregg
// debugging
//
// Revision 1.344  2005/05/13 18:16:39  gregg
// init SQL
//
// Revision 1.343  2005/05/13 17:44:09  gregg
// one more fix
//
// Revision 1.342  2005/05/13 17:30:34  gregg
// fixes
//
// Revision 1.341  2005/05/13 17:00:44  gregg
// more prepping rollup logic for init
//
// Revision 1.340  2005/05/13 16:36:05  gregg
// prepping rollup logic for init
//
// Revision 1.339  2005/05/12 22:49:14  gregg
// fix
//
// Revision 1.338  2005/05/12 20:52:26  gregg
// rollup sql for csol-war, cvar fm/ws/war
//
// Revision 1.337  2005/05/12 17:05:37  gregg
// PSL/PSLAVAIL
//
// Revision 1.336  2005/05/11 20:51:45  gregg
// sql fix
//
// Revision 1.335  2005/05/11 20:40:00  gregg
// more SEC sql for rollup atts
//
// Revision 1.334  2005/05/11 19:46:59  gregg
// add SEC sql
//
// Revision 1.333  2005/05/10 23:01:55  gregg
// more sql and such
//
// Revision 1.332  2005/05/10 22:50:09  gregg
// sql fixes
//
// Revision 1.331  2005/05/10 22:03:54  gregg
// okay, goin for ports...
//
// Revision 1.330  2005/05/10 19:37:27  gregg
// compile/logic fix
//
// Revision 1.328  2005/05/10 19:26:30  gregg
// rollup atts ... different att code mappings for same attribute bleh
//
// Revision 1.327  2005/05/09 22:22:38  gregg
// fix
//
// Revision 1.326  2005/05/09 22:07:28  gregg
// sql phix
//
// Revision 1.325  2005/05/09 21:43:37  gregg
// more..
//
// Revision 1.324  2005/05/09 21:03:37  gregg
// rollup atts for OF child/root
//
// Revision 1.323  2005/05/09 18:40:36  gregg
// SQL fix
//
// Revision 1.322  2005/05/09 18:08:37  gregg
// lets try and get some data into MULTIATTRIBUTE table
//
// Revision 1.321  2005/05/03 20:46:01  gregg
// sql fix
//
// Revision 1.320  2005/05/03 19:23:59  gregg
// sql fix
//
// Revision 1.319  2005/05/03 19:13:49  gregg
// fix
//
// Revision 1.318  2005/05/03 18:53:15  gregg
// ok attempt to come full crcle and grab child rollup vals
//
// Revision 1.317  2005/05/03 17:52:56  gregg
// more for multi atts
//
// Revision 1.316  2005/05/03 17:02:03  gregg
// more in processRollupAttributes
//
// Revision 1.315  2005/05/02 22:43:57  gregg
// lets try and get some roots for m_vctRootRollupAttrs
//
// Revision 1.314  2005/04/28 16:36:42  gregg
// some more debugs + compile fix
//
// Revision 1.313  2005/04/27 22:43:45  gregg
// checkRollupAttribute
//
// Revision 1.312  2005/04/27 21:43:49  gregg
// more debugs
//
// Revision 1.311  2005/04/27 21:22:36  gregg
// rollup attributes for deletes
//
// Revision 1.310  2005/04/27 20:44:58  gregg
// sum debugs and such
//
// Revision 1.309  2005/04/26 20:45:19  gregg
// i vs. j
//
// Revision 1.308  2005/04/26 18:16:01  gregg
// compile fix
//
// Revision 1.307  2005/04/26 18:02:41  gregg
// rollup attributes
//
// Revision 1.306  2005/02/18 20:44:21  dave
// test change for comment
//
// Revision 1.305  2005/02/16 19:26:46  dave
// fixed another null pointer
//
// Revision 1.304  2005/02/16 18:46:11  dave
// fix for null pointer
//
// Revision 1.303  2005/02/08 21:51:45  dave
// Jtest clean up
//
// Revision 1.302  2005/01/31 18:37:32  dave
// Jtest  cleanup
//
// Revision 1.301  2005/01/20 22:57:44  gregg
// fix chuck
//
// Revision 1.300  2005/01/20 22:08:09  dave
// fixing iEnd in updateODSTable logic.  It was seting
// itself to chunksize when it needed to be startID + chunksize
//
// Revision 1.299  2005/01/18 21:46:52  dave
// more parm debug cleanup
//
// Revision 1.298  2004/12/29 19:23:56  dave
// synatc
//
// Revision 1.297  2004/12/29 18:59:47  dave
// changes 5 to 1 minute.. and fix to ensure
// that we do not remove records from the prodattrelator
// table if a relator record still exists in the table
//
// Revision 1.296  2004/11/08 21:18:31  bala
// debug rebuildmetadesc nullpointer
//
// Revision 1.295  2004/10/26 22:05:18  dave
// fixing 21408334
//
// Revision 1.294  2004/09/22 22:01:07  dave
// tracking
//
// Revision 1.293  2004/09/16 00:24:47  dave
// one more field swap
//
// Revision 1.292  2004/09/13 22:37:06  dave
// more blob filtering
//
// Revision 1.291  2004/09/13 22:26:51  dave
// more robust attribute pulling
//
// Revision 1.290  2004/09/11 19:41:43  dave
// temp comment to focus on software
//
// Revision 1.289  2004/09/09 17:01:48  dave
// ok.. more trace.. reverse order of parameters
//
// Revision 1.288  2004/09/03 07:08:18  dave
// default to USA FC on genareaname
//
// Revision 1.287  2004/09/01 19:07:33  dave
// 20398666  set valfrom when ever
// you update a FKEY field
//
// Revision 1.286  2004/08/27 17:30:46  dave
// fixing SOFTWARE sql
//
// Revision 1.285  2004/08/25 15:51:50  dave
// more meta.. and of course do not remove the fkey
// if target is unpublished
//
// Revision 1.284  2004/08/21 16:06:35  dave
// put back
//
// Revision 1.283  2004/08/21 15:13:26  dave
// more surgery
//
// Revision 1.282  2004/08/21 07:41:39  dave
// minor change
//
// Revision 1.281  2004/08/16 03:55:29  dave
// created an ODS stored procedure to pull out
// rules for a given projectid and genarename_fc
//
// Revision 1.280  2004/08/09 17:57:40  dave
// fixing ODSNET to run all vs software update only
//
// Revision 1.279  2004/08/06 16:01:03  dave
// more trace in ODSMethods
//
// Revision 1.278  2004/07/16 02:28:11  dave
// valfrom >= needs to besimply valfrom >for updatesoftware
//
// Revision 1.277  2004/06/11 16:53:47  gregg
// software SBBSO dup relator del logic to VAR entity check
//
// Revision 1.276  2004/06/10 21:09:46  gregg
// more sql
//
// Revision 1.275  2004/06/10 20:58:30  gregg
// sql fix
//
// Revision 1.274  2004/06/10 20:48:15  gregg
// compile fix
//
// Revision 1.273  2004/06/10 20:41:05  gregg
// MN 19562053
//
// Revision 1.272  2004/06/02 17:08:31  joan
// fix compile
//
// Revision 1.271  2004/06/02 07:45:25  dave
// syntax
//
// Revision 1.270  2004/06/01 21:02:04  dave
// odslength for multi value flags on description change add
//
// Revision 1.269  2004/05/24 16:58:47  gregg
// more logging for said nls changes
//
// Revision 1.268  2004/05/24 16:44:07  gregg
// nlsid - specific logic in crossPostValFromCheck
//
// Revision 1.267  2004/04/21 22:37:02  gregg
// (attempting) to skip all processing of DERIVED_EID
//
// Revision 1.266  2004/04/16 19:27:34  gregg
// some more descriptionChange adjustments
//
// Revision 1.265  2004/04/15 23:28:44  gregg
// minor fix in rebuildSoftwareForEntities
//
// Revision 1.264  2004/04/15 16:01:21  gregg
// sync w/ v1.2 (updateSoftware for VAR, CVAR)
//
// Revision 1.263  2004/04/08 17:29:37  gregg
// comments
//
// Revision 1.262  2004/04/08 17:28:15  gregg
// updateDescriptionChanges: need to check for multi flags in crosspost logic as well
//
// Revision 1.261  2004/04/07 21:59:44  gregg
// sync w/ v12: cross-post logic in updateDescriptionChanges
//
// Revision 1.260  2004/04/07 20:22:15  gregg
// sync w/ v12 (check multi flag in updateDescriptionhangeForItem)
//
// Revision 1.259  2004/04/07 18:45:01  gregg
// more detailed trace stmts for skip Entity/Attribute in Description Change logic
//
// Revision 1.258  2004/04/07 18:29:25  gregg
// fix update description changes: MFTable SQL
//
// Revision 1.257  2004/04/07 18:11:07  gregg
// comment
//
// Revision 1.256  2004/04/07 18:07:34  gregg
// more (minor) sync w/ v12
//
// Revision 1.255  2004/04/07 18:02:18  gregg
// sync w/ v12
//
// Revision 1.254  2004/04/06 22:31:15  gregg
// updateDescriptionChangeForItem --> hit MFTable
//
// Revision 1.253  2004/03/23 22:35:27  gregg
// updateDecsriptionChanges (sync w/ v1.2)
//
// Revision 1.252  2004/03/10 22:17:09  gregg
// more excludeEntityFromUpdate logic
//
// Revision 1.251  2004/03/04 00:08:03  gregg
// some debugs
//
// Revision 1.250  2004/03/03 23:29:16  gregg
// null ptr fix
//
// Revision 1.249  2004/03/03 22:17:17  gregg
// some null vs. empty String stuff
//
// Revision 1.248  2004/03/03 20:18:07  gregg
// null ptr fix
//
// Revision 1.247  2004/03/03 19:32:52  gregg
// null ptr fix
//
// Revision 1.246  2004/03/03 19:07:00  gregg
// one mo' fix
//
// Revision 1.245  2004/03/03 18:58:43  gregg
// compile fix
//
// Revision 1.244  2004/03/03 18:48:37  gregg
// attribute subset logic
//
// Revision 1.243  2004/03/02 20:51:49  gregg
// add Debug stmt for excludeEntityFromUpdate
//
// Revision 1.242  2004/03/02 20:49:38  gregg
// excludeEntityFromUpdate
//
// Revision 1.241  2004/02/26 00:15:22  gregg
// delete/unpublish by NLSID logic
//
// Revision 1.240  2004/02/25 23:10:24  gregg
// updateODSTable - remve invalid NLS logic, first pass (debug only)
//
// Revision 1.239  2004/02/25 18:47:19  gregg
// fix sum variable names for less confusing code
//
// Revision 1.238  2004/02/23 22:34:41  gregg
// updateSoftware merge conflict (chop suuuey!!)
//
// Revision 1.237  2004/02/23 22:27:20  gregg
// fix in updateSoftware (use m_strLastRun)
//
// Revision 1.236  2004/02/23 21:39:34  dave
// fkey safety net
//
// Revision 1.235  2004/02/20 00:39:37  gregg
// move deletes to rebuildSoftwareForEntities() method to ensure Conection.commit()'s are consistent
//
// Revision 1.234  2004/02/20 00:28:08  gregg
// use correct strInsertSQL in rebuildSoftwareForEntities()
//
// Revision 1.233  2004/02/20 00:19:52  gregg
// updateSoftware() first pass
//
// Revision 1.232  2004/02/14 17:00:13  dave
// syntax
//
// Revision 1.231  2004/02/14 07:38:24  dave
// DMNET fixes
//
// Revision 1.230  2004/01/19 23:25:11  dave
// go back to ol fashioned string
//
// Revision 1.229  2004/01/19 23:16:09  dave
// did we get it this time?
//
// Revision 1.228  2004/01/19 23:10:14  dave
// more date tricks
//
// Revision 1.227  2004/01/19 23:05:06  dave
// setting lenient to true
//
// Revision 1.226  2004/01/19 23:00:11  dave
// Try a new date parsing technique
//
// Revision 1.225  2004/01/19 22:24:04  dave
// attributetype = "D"?
//
// Revision 1.224  2004/01/19 21:58:17  dave
// fixing rtrim to trim
//
// Revision 1.223  2004/01/19 21:52:11  dave
// putting back entire source
//
// Revision 1.222  2004/01/19 21:33:58  dave
// more trace
//
// Revision 1.221  2004/01/19 20:43:52  dave
// another syntax fix
//
// Revision 1.220  2004/01/19 20:38:06  dave
// make sure we return a value
//
// Revision 1.219  2004/01/19 20:29:23  dave
// make sure we only put values of 254 or less into the prodattribute table
//
// Revision 1.218  2004/01/19 19:25:44  dave
// one more change
//
// Revision 1.217  2004/01/19 19:21:59  dave
// increasing 50 to 100
//
// Revision 1.216  2004/01/19 19:11:47  dave
// syntax
//
// Revision 1.215  2004/01/19 19:05:06  dave
// if any date comes  back w/ the word Open ..
// then i null out the date as it moves to the ECCM side
// of the database
//
// Revision 1.214  2004/01/19 18:51:52  dave
// cleaner logging
//
// Revision 1.213  2004/01/19 18:49:56  dave
// some Numeric fields have long odslengths..
// (we are now defaulting to 10 or 50)
//
// Revision 1.212  2004/01/19 04:31:47  dave
// more trace
//
// Revision 1.211  2004/01/19 04:13:11  dave
// out of bounds fix
//
// Revision 1.210  2004/01/19 04:05:30  dave
// string tokenizer fix
//
// Revision 1.209  2004/01/19 03:36:37  dave
// remove return Vector
//
// Revision 1.208  2004/01/19 03:29:53  dave
// more syntax
//
// Revision 1.207  2004/01/19 03:16:08  dave
// more syntax
//
// Revision 1.206  2004/01/19 03:02:44  dave
// working in the crosspostII logic
//
// Revision 1.205  2004/01/17 04:29:53  dave
// syntax
//
// Revision 1.204  2004/01/17 04:21:15  dave
// when a relator changes gets added or deleted.. we
// need to update the parent entity if a prodattrelator
//
// Revision 1.203  2004/01/17 03:17:32  dave
// back out change
//
// Revision 1.202  2004/01/17 02:27:57  dave
// syntax
//
// Revision 1.201  2004/01/17 02:19:08  dave
// fixing dmnet and relator deletes
//
// Revision 1.200  2004/01/05 23:23:27  dave
// found it!
//
// Revision 1.199  2004/01/05 23:18:37  dave
// more syntax sql cleanup
//
// Revision 1.198  2004/01/05 23:04:44  dave
// minor fix on postCross fix
//
// Revision 1.197  2004/01/05 20:04:23  dave
// syntax
//
// Revision 1.196  2004/01/05 19:50:00  dave
// syntax
//
// Revision 1.195  2004/01/05 19:41:47  dave
// first pass at crossref post
//
// Revision 1.194  2004/01/05 01:54:29  dave
// added m_bResetMetaAttributes logic to
// loop through all the entity group and add any new
// attributes that are in the conrol file.. but not yet
// in the table
//
// Revision 1.193  2004/01/01 20:04:45  dave
// reduced debug statements
//
// Revision 1.192  2004/01/01 19:33:08  dave
// more changes for eccm AT
//
// Revision 1.191  2004/01/01 19:12:05  dave
// more fixes
//
// Revision 1.190  2003/12/31 20:44:23  dave
// null to 0 on setInt
//
// Revision 1.189  2003/12/31 20:37:14  dave
// syntax
//
// Revision 1.188  2003/12/31 19:56:27  dave
// Expanded trace
//
// Revision 1.187  2003/12/31 19:55:18  dave
// trying to accomidate null attributes
//
// Revision 1.186  2003/12/30 08:26:40  dave
// more spacing problem
//
// Revision 1.185  2003/12/30 08:19:26  dave
// more db pointer fix
//
// Revision 1.184  2003/12/30 08:08:36  dave
// more sql syntax
//
// Revision 1.183  2003/12/30 07:57:20  dave
// cannot distinct on an blob field
//
// Revision 1.182  2003/12/30 07:36:28  dave
// another syntax fix
//
// Revision 1.181  2003/12/29 23:06:40  dave
// sql syntax fix
//
// Revision 1.180  2003/12/29 21:06:21  dave
// syntax on psDFKEY
//
// Revision 1.179  2003/12/29 20:59:17  dave
// ECCM SQL Fixes for eccm.prodattrelator
//
// Revision 1.178  2003/12/22 21:41:41  dave
// sql fixes
//
// Revision 1.177  2003/12/22 21:37:57  dave
// syntax
//
// Revision 1.176  2003/12/22 21:24:53  dave
// more primary key fixing up so it is no longer part of the
// table def
//
// Revision 1.175  2003/12/22 20:37:13  dave
// addressed eccm.attribute primary key
//
// Revision 1.174  2003/12/16 22:56:18  dave
// fix and more CHAR 13 changes
//
// Revision 1.173  2003/12/16 22:44:19  dave
// new script for reference tables CR1121034237
//
// Revision 1.172  2003/12/10 17:29:56  dave
// syntax
//
// Revision 1.171  2003/12/10 17:23:34  dave
// syntx
//
// Revision 1.170  2003/12/10 17:13:23  dave
// fixed blob extension
//
// Revision 1.169  2003/12/09 23:30:45  dave
// syntax fixes
//
// Revision 1.168  2003/12/09 22:59:34  dave
// adding blob net changes pass I
//
// Revision 1.167  2003/12/08 22:08:19  dave
// fixed SQL error
//
// Revision 1.166  2003/12/08 21:52:30  dave
// updates to ECCM stuff
//
// Revision 1.165  2003/12/08 21:20:43  dave
// syntax
//
// Revision 1.164  2003/12/08 21:12:37  dave
// fixing the file name for CR1105033124
//
// Revision 1.163  2003/12/08 19:02:19  dave
// fixing prodattr delete
//
// Revision 1.162  2003/12/08 18:57:54  dave
// syntax fixes
//
// Revision 1.161  2003/12/08 18:48:54  dave
// Attempting to add the DFFLockUnlock stuff
//
// Revision 1.160  2003/12/05 21:15:26  dave
// added DISTINCT
//
// Revision 1.159  2003/12/05 19:53:57  dave
// skipping OPWG blob recs
//
// Revision 1.158  2003/12/05 19:23:05  dave
// SQL syntax
//
// Revision 1.157  2003/12/05 19:01:12  dave
// sql syntax
//
// Revision 1.156  2003/12/05 18:13:49  dave
// SQL Fix on Inner SQL
//
// Revision 1.155  2003/12/05 18:05:26  dave
// fixing array out of bounds issue
//
// Revision 1.154  2003/12/05 17:56:06  dave
// syntax
//
// Revision 1.153  2003/12/05 17:47:53  dave
// LOB 32k issue work around
//
// Revision 1.152  2003/12/04 23:47:14  dave
// more fixes to blob sql
//
// Revision 1.151  2003/12/04 23:37:35  dave
// more fixin
//
// Revision 1.150  2003/12/04 23:24:35  dave
// more blob filtering
//
// Revision 1.149  2003/12/04 23:17:23  dave
// more blob filtering
//
// Revision 1.148  2003/12/04 22:02:38  dave
// blobo stuff
//
// Revision 1.147  2003/12/04 21:55:20  dave
// syntax
//
// Revision 1.146  2003/12/04 21:34:46  dave
// trace for dmi blob
//
// Revision 1.145  2003/12/04 20:56:54  dave
// made entityid > 0
//
// Revision 1.144  2003/12/04 20:43:58  dave
// varying the allignment
//
// Revision 1.143  2003/12/04 20:18:47  dave
// more syntax
//
// Revision 1.142  2003/12/04 20:10:45  dave
// need opicm.blob for pdh
//
// Revision 1.141  2003/12/04 19:49:49  dave
// blob fix for DMINIT
//
// Revision 1.140  2003/11/18 20:56:20  dave
// setting nlsid earlier in loop
//
// Revision 1.139  2003/11/18 20:39:37  dave
// adding more ZERO length debuging
//
// Revision 1.138  2003/11/17 23:24:54  dave
// fixing a bug where DMNET always thought there
// was an existing record in the ODS for
// a given entityid/nlsid combo
//
// Revision 1.137  2003/11/17 21:22:11  dave
// Changing unpublish from 'N' to 'D'
//
// Revision 1.136  2003/11/17 20:22:35  dave
// enabling rebuild blob flag
//
// Revision 1.135  2003/11/17 19:16:22  dave
// syntax
//
// Revision 1.134  2003/11/17 18:02:45  dave
// adding the blob gen logic for dminit
//
// Revision 1.133  2003/11/13 19:25:46  dave
// syntax
//
// Revision 1.132  2003/11/13 19:24:44  dave
// added properties file to control what gets put into
// the attrgroup table
//
// Revision 1.131  2003/11/13 19:02:18  dave
// more fixes
//
// Revision 1.130  2003/11/12 17:21:36  dave
// Added Rate_Card table to the protected list
//
// Revision 1.129  2003/11/11 22:19:01  joan
// fix compile error
//
// Revision 1.128  2003/11/11 21:49:39  dave
// one more skip place for zero length attributes
//
// Revision 1.127  2003/11/11 21:47:44  dave
// Added the zero length attribute skip on insert and update
//
// Revision 1.126  2003/10/09 16:34:52  dave
// adding additional eccm table protection
//
// Revision 1.125  2003/10/06 21:18:20  dave
// got to add commits on table creates
//
// Revision 1.124  2003/10/06 20:47:19  dave
// fixing delete log logic
//
// Revision 1.123  2003/10/06 20:33:42  dave
// adding the rebuildDeleteLog L
//
// Revision 1.122  2003/10/06 19:03:37  dave
// fixing delete update
//
// Revision 1.121  2003/10/06 18:39:31  dave
// more trace
//
// Revision 1.120  2003/10/06 18:12:16  dave
// adding all the delete controls for a record
//
// Revision 1.119  2003/10/06 05:25:01  dave
// tiny dancer
//
// Revision 1.118  2003/10/06 05:07:59  dave
// picking up on deletes
//
// Revision 1.117  2003/10/06 05:03:04  dave
// converting to spew
//
// Revision 1.116  2003/10/06 04:57:55  dave
// needed a commit on runtime
//
// Revision 1.115  2003/10/06 04:34:23  dave
// added FC for update logic
//
// Revision 1.114  2003/10/06 04:03:19  dave
// trace
//
// Revision 1.113  2003/10/06 03:26:32  dave
// minor fixes
//
// Revision 1.112  2003/10/06 03:05:03  dave
// ODSNet II
//
// Revision 1.111  2003/10/03 20:01:43  dave
// more dmnet changes
//
// Revision 1.110  2003/10/03 17:18:51  dave
// Net Changes I
//
// Revision 1.109  2003/10/02 23:26:33  dave
// adding nlsid to csol/software sql
//
// Revision 1.108  2003/10/02 23:15:40  dave
// whoops bad sql
//
// Revision 1.107  2003/10/02 23:04:26  dave
// SQL Simplification for rebuildSoftwareTable
//
// Revision 1.106  2003/10/02 22:13:49  dave
// fixing the spew vs detail
//
// Revision 1.105  2003/10/02 22:07:35  dave
// tracking for rec counts
//
// Revision 1.104  2003/10/02 21:52:27  dave
// fixing commit size
//
// Revision 1.103  2003/10/02 21:49:06  dave
// less trace
//
// Revision 1.102  2003/10/02 21:45:52  dave
// adding csol software regen
//
// Revision 1.101  2003/10/02 21:18:39  dave
// trapping nulls in bad software cols
//
// Revision 1.100  2003/10/02 21:05:28  dave
// forgot the last one
//
// Revision 1.99  2003/10/02 20:57:35  dave
// one final commit
//
// Revision 1.98  2003/10/02 20:41:56  dave
// adding commit count logic to software  build
//
// Revision 1.97  2003/10/02 20:21:00  dave
// More Trace
//
// Revision 1.96  2003/10/02 19:56:34  dave
// forgot the schema thing for the software table rebuild
//
// Revision 1.95  2003/10/02 19:40:27  dave
// bad DDL language
//
// Revision 1.94  2003/10/02 19:29:14  dave
// spelling on spelling
//
// Revision 1.93  2003/10/02 19:28:41  dave
// spelling
//
// Revision 1.91  2003/10/01 20:54:11  dave
// adding publish flag logic to tables
//
// Revision 1.90  2003/10/01 20:06:34  dave
// more trace
//
// Revision 1.89  2003/10/01 18:42:58  dave
// adding geomap logic
//
// Revision 1.88  2003/09/29 18:34:58  dave
// Fkey problem
//
// Revision 1.87  2003/09/29 02:45:18  dave
// logic clean up on sf atts
//
// Revision 1.86  2003/09/29 02:25:21  dave
// tracking, etc
//
// Revision 1.85  2003/09/29 02:05:19  dave
// DDL Syntax errors
//
// Revision 1.84  2003/09/29 01:52:07  dave
// moding flag table to house descriptions in
// nls for speeds and feeds attributes
//
// Revision 1.83  2003/09/24 17:47:47  dave
// tablespace fix
//
// Revision 1.82  2003/09/23 21:16:06  dave
// found nls problem in prodattribute table
//
// Revision 1.81  2003/09/22 03:52:16  dave
// Found FKey
//
// Revision 1.80  2003/09/22 03:32:20  dave
// more refinement
//
// Revision 1.79  2003/09/22 03:03:20  dave
// more refining (keeping lvchar at end of table)
//
// Revision 1.78  2003/09/22 02:43:42  dave
// missing columns
//
// Revision 1.77  2003/09/22 02:39:23  dave
// parm miscount
//
// Revision 1.76  2003/09/22 02:21:49  dave
// more refinement
//
// Revision 1.75  2003/09/22 01:56:06  dave
// more refinement
//
// Revision 1.74  2003/09/22 01:29:57  dave
// syntax
//
// Revision 1.73  2003/09/22 01:19:15  dave
// refine refine
//
// Revision 1.72  2003/09/22 01:06:35  dave
// ID needs to be ENTITYID
//
// Revision 1.71  2003/09/22 01:02:58  dave
// more refinement
//
// Revision 1.70  2003/09/22 00:19:34  dave
// do not blow away the restartable table
//
// Revision 1.69  2003/09/22 00:17:15  dave
// PinitializeRestartTable
//
// Revision 1.68  2003/09/21 23:04:45  dave
// more function
//
// Revision 1.67  2003/09/21 22:29:02  dave
// adding PRODATTRELATOR TABLE
//
// Revision 1.66  2003/09/21 22:21:53  dave
// Fix to FKEY
//
// Revision 1.65  2003/09/21 21:31:31  dave
// added key info to fkey
//
// Revision 1.64  2003/09/21 21:14:27  dave
// implementing foriegn keys
//
// Revision 1.63  2003/09/21 20:33:23  dave
// syntax and new ods.server.properties
//
// Revision 1.62  2003/09/21 20:21:38  dave
// syntax
//
// Revision 1.61  2003/09/21 20:06:22  dave
// NLS fixes
//
// Revision 1.60  2003/09/21 19:56:24  dave
// more changes to properties files
//
// Revision 1.59  2003/09/21 19:18:15  dave
// starting ODSInitII
//
// Revision 1.58  2003/09/19 20:34:56  dave
// CHAR 16
//
// Revision 1.57  2003/09/19 20:15:34  dave
// adding flag code field
//
// Revision 1.56  2003/09/19 18:00:54  dave
// missing an execute on the ps
//
// Revision 1.55  2003/09/19 17:16:03  dave
// Syntax
//
// Revision 1.54  2003/09/19 17:04:05  dave
// more commits
//
// Revision 1.53  2003/09/19 01:00:38  dave
// more refining
//
// Revision 1.52  2003/09/19 00:54:13  dave
// table Name fix
//
// Revision 1.51  2003/09/19 00:41:07  dave
// sql fix
//
// Revision 1.50  2003/09/19 00:19:08  dave
// one more time
//
// Revision 1.49  2003/09/19 00:07:32  dave
//  more fixes
//
// Revision 1.48  2003/09/18 23:55:08  dave
// another fix
//
// Revision 1.46  2003/09/18 23:34:02  dave
// more SQL statement fixes
//
// Revision 1.45  2003/09/18 23:16:54  dave
// Fixing Create SQL
//
// Revision 1.44  2003/09/18 22:59:19  dave
// more odsII changes
//
// Revision 1.43  2003/09/16 23:01:37  dave
// index adding
//
// Revision 1.42  2003/09/16 17:00:22  dave
// fixing restart
//
// Revision 1.41  2003/09/16 01:00:04  dave
// adding the multatttable
//
// Revision 1.40  2003/09/16 00:24:04  dave
// adding attribute transforms
//
// Revision 1.39  2003/09/16 00:12:50  dave
// more changes
//
// Revision 1.38  2003/09/15 23:43:45  dave
// adding the new PRODATTMAP
//
// Revision 1.37  2003/09/15 22:52:59  dave
// more restart logic
//
// Revision 1.36  2003/09/15 22:29:10  dave
// fixing syntax
//
// Revision 1.35  2003/09/15 22:05:44  dave
// change flag back to longvarchar
//
// Revision 1.34  2003/09/15 19:48:28  dave
// adding restart logic
//
// Revision 1.33  2003/09/15 19:05:33  dave
// Syntax
//
// Revision 1.32  2003/09/15 18:34:07  dave
// misc fixes and usenglish only flag for flag support
//
// Revision 1.31  2003/09/15 04:56:15  dave
// more gc
//
// Revision 1.30  2003/09/15 04:33:32  dave
// tossing is garb coll
//
// Revision 1.29  2003/09/15 02:06:37  dave
// int cannot  be alpha
//
// Revision 1.28  2003/09/15 01:58:34  dave
// Let the setString convert to Date
//
// Revision 1.27  2003/09/15 01:31:57  dave
// more NLS fixes
//
// Revision 1.26  2003/09/15 00:42:42  dave
// fix to isRelator and < par/child
//
// Revision 1.25  2003/09/14 23:42:31  dave
// fixing ISPACE TSPACE
//
// Revision 1.24  2003/09/14 23:05:51  dave
// private to protected
//
// Revision 1.23  2003/09/14 22:20:35  dave
// converging on ODSInit
//
// Revision 1.22  2003/09/14 02:49:44  dave
// syntax
//
// Revision 1.21  2003/09/14 02:37:54  dave
// do not forget "I"
//
// Revision 1.20  2003/09/14 01:01:43  dave
// more minor syntax
//
// Revision 1.19  2003/09/14 00:58:22  dave
// more misc changes
//
// Revision 1.18  2003/09/14 00:35:40  dave
// more refining
//
// Revision 1.17  2003/09/12 22:42:55  dave
// sytnax fix
//
// Revision 1.16  2003/09/12 22:24:14  dave
// checking in some changes
//
// Revision 1.15  2003/09/12 21:15:23  dave
// Checking translation length limit
//
// Revision 1.14  2003/09/12 17:29:34  dave
// eccm prodatt tables
//
// Revision 1.13  2003/09/12 16:49:37  dave
// syntax fix
//
// Revision 1.12  2003/09/12 01:45:56  dave
// new metaflagfixes
//
// Revision 1.11  2003/09/12 01:06:35  dave
// Relator Data when no nlsid comes back
//
// Revision 1.10  2003/09/12 00:06:50  dave
// Adding Status to the meta flag pull
//
// Revision 1.9  2003/09/11 23:40:20  dave
// fixing ;U; in flag table
//
// Revision 1.8  2003/09/11 23:03:12  dave
// syntax
//
// Revision 1.7  2003/09/11 22:44:28  dave
// syntax
//
// Revision 1.6  2003/09/11 22:38:04  dave
// adding AllNLSid
//
// Revision 1.5  2003/09/10 18:41:38  dave
// Adding getODSConnection()
//
// Revision 1.4  2003/09/09 22:07:31  dave
// removing profile links in attribute creation
//
// Revision 1.3  2003/04/17 01:27:41  bala
// add the ods table check to ALTER it if its necessary
//
// Revision 1.2  2003/04/15 18:25:37  bala
// plug in the net changes piece
//
// Revision 1.1  2003/02/21 18:05:26  bala
// Initial Checkin
//
//

package COM.ibm.eannounce.ods;

import COM.ibm.eannounce.objects.BlobAttribute;
import COM.ibm.eannounce.objects.DescriptionChangeItem;
import COM.ibm.eannounce.objects.DescriptionChangeList;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANBlobAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EANMetaFlagAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.LongTextAttribute;
import COM.ibm.eannounce.objects.MetaBlobAttribute;
import COM.ibm.eannounce.objects.MetaEntityList;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.eannounce.objects.MetaLongTextAttribute;
import COM.ibm.eannounce.objects.MetaMultiFlagAttribute;
import COM.ibm.eannounce.objects.MetaSingleFlagAttribute;
import COM.ibm.eannounce.objects.MetaStatusAttribute;
import COM.ibm.eannounce.objects.MetaTaskAttribute;
import COM.ibm.eannounce.objects.MetaTextAttribute;
import COM.ibm.eannounce.objects.MetaXMLAttribute;
import COM.ibm.eannounce.objects.MultiFlagAttribute;
import COM.ibm.eannounce.objects.SingleFlagAttribute;
import COM.ibm.eannounce.objects.StatusAttribute;
import COM.ibm.eannounce.objects.TaskAttribute;
import COM.ibm.eannounce.objects.TextAttribute;

import COM.ibm.eannounce.objects.XMLAttribute;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnDataRow;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.transactions.NLSItem;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Enumeration;

import COM.ibm.opicmpdh.transactions.OPICMBlobValue;
import COM.ibm.opicmpdh.middleware.Stopwatch;
import COM.ibm.opicmpdh.middleware.SortUtil;

import java.text.DateFormat;

/**
 *  This contains all the methods used to manipulate the ODS
 *
 *
 *@author     Administrator
 *@created    February 11, 2003
 */
public abstract class ODSMethods {

    /**
     * number of blob trsnasctions to let go prior to a commit
     */
    public static final int BLOB_COMMIT_COUNT = 100;

    /**
     * INDEX_NAME_LENGTH
     */
    public static final int INDEX_NAME_LENGTH = 15;

    /**
     * NEW LINE
     */
    public static final String NEW_LINE = "\n";
    /**
     * TAB
     *
     */
    public static final String TAB = "\t";

    /**
     * FIELD
     */
    protected static Hashtable c_hshGAMap = new Hashtable();

    /**
     * FIELD
     */
    protected Database m_dbPDH = new Database();
    /**
     * FIELD
     */
    protected Connection m_conODS = null;
    /**
     * FIELD
     */
    protected Connection m_conPDH = null;
    /**
     * FIELD
     */
    protected Profile m_prof = null;
    /**
     * FIELD
     */
    protected String m_strNow = null;
    /**
     * FIELD
     */
    protected String m_strLastRun = null;
    /**
     * FIELD
     */
    protected String m_strForever = null;
    /**
     * FIELD
     */
    protected String m_strEpoch = null;

    /**
     * FIELD
     */
    protected DateFormat m_df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.JAPAN);
    /**
     * FIELD
     */
    protected String m_strurlODS = ODSServerProperties.getDatabaseURL();
    /**
     * FIELD
     */
    protected String m_struidODS = ODSServerProperties.getDatabaseUser();
    /**
     * FIELD
     */
    protected String m_strpwdODS = ODSServerProperties.getDatabasePassword();
    /**
     * FIELD
     */
    protected DatabaseMetaData m_dbMetaODS = null;
    /**
     * FIELD
     */
    protected String m_strODSSchema = ODSServerProperties.getDatabaseSchema();
    /**
     * FIELD
     */
    protected String m_strTableSpace = ODSServerProperties.getDefaultTableSpace(m_strODSSchema);
    /**
     * FIELD
     */
    protected String m_strIndexSpace = ODSServerProperties.getDefaultIndexSpace(m_strODSSchema);
    /**
     * FIELD
     */
    protected int m_iChuckSize = ODSServerProperties.getChunkSize();
    /**
     * FIELD
     */
    protected boolean m_bRebuild = false;
    /**
     * FIELD
     */
    protected boolean m_bInit = false;
    /**
     * FIELD
     */
    protected boolean m_bInitAll = false;
    /**
     * FIELD
     */
    protected boolean m_bInitOne = false;
    /**
     * FIELD
     */
    protected boolean m_bNetChange = false;
    /**
     * FIELD
     */
    protected boolean m_bDropAll = false;
    /**
     * FIELD
     */
    protected boolean m_bMultiFlag = false;
    /**
     * FIELD
     */
    protected boolean m_bSoftwareFlag = false;
    /**
     * FIELD
     */
    protected boolean m_bMetaDesc = false;
    /**
     * FIELD
     */
    protected boolean m_bRestart = false;
    /**
     * FIELD
     */
    protected boolean m_bRebuildBlob = false;
    /**
     * FIELD
     */
    protected boolean m_bResetMetaAttributes = false;

    /**
     * FIELD
     */
    protected boolean m_bRebuildDeleteLog = false;
    /**
     * FIELD
     */
    protected String m_strTableName = "";

    /**
     * FIELD!
     */
    protected Vector m_vctRollupAttrs = new Vector();
    /**
     * FIELD!
     */
    protected Vector m_vctRootRollupAttrs = new Vector();
    /**
     * FIELD!
     */
    protected Hashtable m_HashMultiAttEntityGroups = new Hashtable();

    /**
     *  Sets the oDSConnection attribute of the ODSMethods object
     */
    protected void setODSConnection() {
        try {
            D.ebug(D.EBUG_DETAIL, "Connecting to ODS");
            m_conODS = m_dbPDH.getODSConnection();
            m_conODS.setAutoCommit(false);
        }
        catch (Exception ex) {
            D.ebug(D.EBUG_ERR, "odsConnectionError" + ex.getMessage());
            System.exit( -1);
        }

    }

    /**
     *  Sets the oDSConnection attribute of the ODSMethods object
     */
    protected void setPDHConnection() {
        try {
            D.ebug(D.EBUG_DETAIL, "Connecting to ODS");
            m_conPDH = m_dbPDH.getPDHConnection();
            m_conPDH.setAutoCommit(true);
        }
        catch (Exception ex) {
            D.ebug(D.EBUG_ERR, "pdhConnectionError" + ex.getMessage());
            System.exit( -1);
        }
    }

    /**
     *  Gets the Connection attribute of the ODSMethods object
     *
     *@return    The Connection object
     */
    protected Connection getODSConnection() {
        return m_conODS;
    }

    /**
     *  Gets the databaseObject attribute of the ODSMethods object
     *
     *@return    The databaseObject value
     */
    protected Database getDatabaseObject() {
        return m_dbPDH;
    }

    /**
     *  Constructor for the setDatabaseObject object
     *
     * @param _dbPDH
     */
    protected void setDatabaseObject(Database _dbPDH) {
        m_dbPDH = _dbPDH;
    }

    /**
     *  Sets the profile attribute of the ODSMethods object
     *
     *@exception  MiddlewareException
     *@exception  SQLException
     */
    protected void setProfile() throws MiddlewareException, SQLException {
        m_prof = new Profile(m_dbPDH, ODSServerProperties.getProfileEnterprise(), ODSServerProperties.getProfileOPWGID());
    }

    /**
     *  Gets the profile attribute of the ODSMethods object
     *
     *@return    The profile value
     */
    protected Profile getProfile() {
        return m_prof;
    }

    /**
     *  Sets the profile,now and forever  attributes of the ODSMethods object
     */
    protected void setDateTimeVars() {
        DatePackage dpNow = new DatePackage(m_dbPDH);
        m_prof.setValOn(dpNow.getEndOfDay());
        m_prof.setEffOn(dpNow.getEndOfDay());
        m_strNow = dpNow.getNow();
        m_strForever = dpNow.getForever();
    }

    /**
     * Rebuilds the Blob Table
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    protected void rebuildBlobTable() throws SQLException, MiddlewareException {

        int count = 0;
        int errcount = 0;
        int reploop = 0;

        String strTableName = m_strODSSchema + ".BLOB";

        String strDropSQL = "DROP TABLE " + strTableName;
        String strTableSQL = "CREATE TABLE " + strTableName + " (" + "VALFROM TIMESTAMP NOT NULL, " +
            "ENTITYTYPE CHAR(32) NOT NULL, " + "ENTITYID INTEGER NOT NULL, " + "ATTRIBUTECODE CHAR(32) NOT NULL, " +
            "NLSID INT NOT NULL, " + "BLOBEXTENSION CHAR(32) NOT NULL," + "ATTRIBUTEVALUE BLOB(50M) NOT NULL " + ")";

        String strIndexSQL = " CREATE UNIQUE INDEX " + strTableName + "_PK ON " + strTableName +
            "(ENTITYID, ENTITYTYPE,ATTRIBUTECODE, NLSID)";
        String strInsertSQL = "INSERT INTO " + strTableName + "( " + "VALFROM, " + "ENTITYTYPE, " + "ENTITYID, " +
            "ATTRIBUTECODE, " + "NLSID, " + "BLOBEXTENSION, " + "ATTRIBUTEVALUE " + ") " + "VALUES (?,?,?,?,?,?,?)";

        String strOuterSelectSQL = "SELECT DISTINCT " + "B.ENTITYTYPE, " + "B.ENTITYID, " + "B.ATTRIBUTECODE, " + "B.NLSID " +
            "FROM OPICM.BLOB B" + " WHERE " + "B.ENTERPRISE = '" + m_prof.getEnterprise() + "' AND " + "B.ValFrom <= '" + m_strNow +
            "' AND '" + m_strNow + "' < B.ValTo AND " + "B.EffFrom <= '" + m_strNow + "' AND '" + m_strNow + "' < B.EffTo  " + " AND ENTITYTYPE NOT IN ('OPWG','DGENTITY', 'XLATEGRP','METAXLATEGRP') AND ENTITYID > 0 AND ATTRIBUTECODE NOT IN ('ETSOBJECT','SERIALHISTORY','PRUSERPREFERENCES','CHQETSMOBJECT') FOR READ ONLY";

        String strInnerSelectSQL = "SELECT " + "B.BLOBEXTENSION, " + "B.ATTRIBUTEVALUE " + "FROM OPICM.BLOB B " + "WHERE " +
            "B.ENTERPRISE = '" + m_prof.getEnterprise() + "' AND " +
            "B.EntityType = ? AND B.EntityID = ? AND B.ATTRIBUTECODE = ? AND B.NLSID = ? AND " + "B.ValFrom <= '" + m_strNow +
            "' AND '" + m_strNow + "' < B.ValTo AND " + "B.EffFrom <= '" + m_strNow + "' AND '" + m_strNow + "' < B.EffTo";

        String strTableSpace = ODSServerProperties.getTableSpace(m_strODSSchema, "BLOB");
        String strIndexSpace = ODSServerProperties.getIndexSpace(m_strODSSchema, "BLOB");

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        Statement ddlstmt = null;
        PreparedStatement insertrecord = null;
        PreparedStatement getBlob1 = null;
        PreparedStatement getBlob2 = null;

        try {

            strTableSpace = (strTableSpace.equals("DEFAULT") ? m_strTableSpace : strTableSpace);
            strIndexSpace = (strIndexSpace.equals("DEFAULT") ? m_strIndexSpace : strIndexSpace);
            strTableSQL = strTableSQL + (strTableSpace.equals("DEFAULT") ? "" : " IN " + strTableSpace) +
                (strIndexSpace.equals("DEFAULT") ? "" : " INDEX IN " + strIndexSpace);

            ddlstmt = m_conODS.createStatement();

            // Lets try to drop the table...

            try {
                D.ebug(D.EBUG_INFO, "rebuildBlob:dropping Table:" + strTableName);
                ddlstmt.executeUpdate(strDropSQL);
                m_conODS.commit();
            }
            catch (SQLException ex) {
                D.ebug(D.EBUG_INFO, "rebuildBlob:Skipping Table Drop:" + strTableName + ":" + ex.getMessage());
            }

            D.ebug(D.EBUG_INFO, "rebuildBlob:creating table:" + strTableSQL);
            ddlstmt.executeUpdate(strTableSQL);
            m_conODS.commit();

            // Now the index
            D.ebug(D.EBUG_INFO, "rebuildBlob:creating index:" + strIndexSQL);
            ddlstmt.executeUpdate(strIndexSQL);
            m_conODS.commit();

            D.ebug(D.EBUG_INFO, "rebuildBlob:Start.");

        }
        finally {
            ddlstmt.close();
        }

        try {
            D.ebug(D.EBUG_INFO, "rebuildBlob:OPICM Blob OuterQuery Begins.");
            getBlob1 = m_conPDH.prepareStatement(strOuterSelectSQL);
            rs = getBlob1.executeQuery();
            D.ebug(D.EBUG_INFO, "rebuildBlob:OPICM Blob OuterQuery Ends");
            rdrs = new ReturnDataResultSet(rs);

        }
        finally {

            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (getBlob1 != null) {
                getBlob1.close();
                getBlob1 = null;
            }
            m_dbPDH.commit();
            m_dbPDH.freeStatement();
        }

        insertrecord = m_conODS.prepareStatement(strInsertSQL);
        getBlob2 = m_conPDH.prepareStatement(strInnerSelectSQL);

        try {

            for (int i = 0; i < rdrs.size(); i++) {

                String strEntityType = "empty";
                int iEntityID = 0;
                String strAttributeCode = "empty";
                int iNLSID = 0;
                String strBlobExtension = "empty";
                int iblobsize = 0;

                try {

                    strEntityType = rdrs.getColumn(i, 0);
                    iEntityID = rdrs.getColumnInt(i, 1);
                    strAttributeCode = rdrs.getColumn(i, 2);
                    iNLSID = rdrs.getColumnInt(i, 3);

                    getBlob2.setString(1, strEntityType);
                    getBlob2.setInt(2, iEntityID);
                    getBlob2.setString(3, strAttributeCode);
                    getBlob2.setInt(4, iNLSID);

                    rs = getBlob2.executeQuery();
                    if (rs.next()) {
                        byte[] mybytes = null;
                        strBlobExtension = rs.getString(1);
                        mybytes = rs.getBytes(2);
                        iblobsize = mybytes.length;

                        D.ebug(D.EBUG_INFO,
                            "rebuildBlob:about to insert:  " + NEW_LINE + TAB + ":ET:" + strEntityType + NEW_LINE + TAB + ":EID:" +
                            iEntityID + NEW_LINE + TAB + ":AC:" + strAttributeCode + NEW_LINE + TAB + ":NLS:" + iNLSID + NEW_LINE +
                            TAB + ":BEX:" + strBlobExtension + NEW_LINE + TAB + ":BSZ:" + iblobsize + NEW_LINE + TAB + ":COUNT:" +
                            count);

                        insertrecord.clearParameters();
                        insertrecord.setString(1, m_strNow); // Valfrom
                        insertrecord.setString(2, strEntityType); // EntityType
                        insertrecord.setInt(3, iEntityID); // Entityid
                        insertrecord.setString(4, strAttributeCode); // AttributeCode
                        insertrecord.setInt(5, iNLSID); // NLSID
                        insertrecord.setString(6, strBlobExtension); // NLSID
                        insertrecord.setBytes(7, mybytes); //
                        insertrecord.executeUpdate();
                        count++;
                        reploop++;
                        if (reploop == BLOB_COMMIT_COUNT) {
                            D.ebug(D.EBUG_INFO, "rebuildBlob:commit on:" + count);
                            m_conODS.commit();
                            reploop = 0;
                        }
                    }

                }
                catch (SQLException ex) {
                    errcount++;
                    D.ebug(D.EBUG_ERR,
                        "rebuildBlob:SQL Panic, Skipping insert.  " + NEW_LINE + TAB + ":ET:" + strEntityType + NEW_LINE + TAB +
                        ":EID:" + iEntityID + NEW_LINE + TAB + ":AC:" + strAttributeCode + NEW_LINE + TAB + ":NLS:" + iNLSID +
                        NEW_LINE + TAB + ":BEX:" + strBlobExtension + NEW_LINE + TAB + ":BSZ:" + iblobsize + NEW_LINE + TAB +
                        ":COUNT:" + count + NEW_LINE + TAB + ":MESS" + ex.getMessage());
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    m_dbPDH.commit();
                    m_dbPDH.freeStatement();
                }
            }

        }
        finally {
            getBlob2.close();
            insertrecord.close();
            m_conODS.commit();
            m_conPDH.commit();
        }

        D.ebug(D.EBUG_INFO, "rebuildBlob:Finished.  # of Recs Processed:" + count + "  # number of errors:" + errcount);

    }

    /**
     * Rebuilds the Blob Table
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    protected void updateBlobTable() throws SQLException, MiddlewareException {

        String strTableName = m_strODSSchema + ".BLOB";

        String strInsertSQL = "INSERT INTO " + strTableName + "( " + "VALFROM, " + "ENTITYTYPE, " + "ENTITYID, " +
            "ATTRIBUTECODE, " + "NLSID, " + "BLOBEXTENSION, " + "ATTRIBUTEVALUE " + ") " + "VALUES (?,?,?,?,?,?,?)";

        String strSelectSQL = "Select EntityID FROM " + strTableName + " WHERE " + "ENTITYTYPE = ? AND " + "ENTITYID = ? AND " +
            "ATTRIBUTECODE = ? AND " + "NLSID = ? ";
        String strUpdateSQL = "UPDATE " + strTableName + " SET VALFROM =  ?, BLOBEXTENSION = ?, ATTRIBUTEVALUE = ? " + " WHERE " +
            "ENTITYTYPE = ? AND " + "ENTITYID = ? AND " + "ATTRIBUTECODE = ? AND " + "NLSID = ? ";
        String strDeleteSQL = "Delete FROM  " + strTableName + " WHERE " + "ENTITYTYPE = ? AND " + "ENTITYID = ? AND " +
            "ATTRIBUTECODE = ? AND " + "NLSID = ? ";

        String strSelectPDHSQL = "SELECT  " + "B.ENTITYTYPE, " + "B.ENTITYID, " + "B.ATTRIBUTECODE, " + "B.NLSID, " + "B.EffTo, " +
            "B.BlobExtension, " + "B.AttributeValue " + "FROM OPICM.BLOBX B " + " WHERE " + "B.ENTERPRISE = '" +
            m_prof.getEnterprise() + "' AND " + "B.ValFrom >= '" + m_strLastRun + "' AND " +
            "B.ValTo = '9999-12-31-00.00.00.000000' AND " + "B.ENTITYTYPE <> 'OPWG' AND B.ENTITYID > 0 AND B.ATTRIBUTECODE NOT IN ('ETSOBJECT','SERIALHISTORY','PRUSERPREFERENCES','CHQETSMOBJECT') FOR READ ONLY";

        int count = 0;
        int errcount = 0;
        int reploop = 0;

        String strEntityType = "empty";
        int iEntityID = 0;
        String strAttributeCode = "empty";
        int iNLSID = 0;
        String strBlobExtension = "empty";
        int iblobsize = 0;
        byte[] mybytes = null;
        boolean bActive = false;

        PreparedStatement insertrecord = null;
        PreparedStatement selectrecord = null;
        PreparedStatement updaterecord = null;
        PreparedStatement deleterecord = null;
        PreparedStatement getBlob1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;

        try {
            D.ebug(D.EBUG_INFO, "rebuildBlob:Start.");

            getBlob1 = m_conPDH.prepareStatement(strSelectPDHSQL);
            deleterecord = m_conODS.prepareStatement(strDeleteSQL);
            insertrecord = m_conODS.prepareStatement(strInsertSQL);
            selectrecord = m_conODS.prepareStatement(strSelectSQL);
            updaterecord = m_conODS.prepareStatement(strUpdateSQL);

            D.ebug(D.EBUG_INFO, "rebuildBlob:OPICM Blob Query Begins.");
            rs = getBlob1.executeQuery();
            D.ebug(D.EBUG_INFO, "rebuildBlob:OPICM Blob Query Ends");

            while (rs.next()) {
                strEntityType = rs.getString(1);
                iEntityID = rs.getInt(2);
                strAttributeCode = rs.getString(3);
                iNLSID = rs.getInt(4);
                bActive = rs.getString(5).startsWith("9999-12-31");
                strBlobExtension = rs.getString(6);
                mybytes = rs.getBytes(7);
                iblobsize = mybytes.length;
                D.ebug(D.EBUG_INFO,
                    "updateBlob:about to insert:  " + NEW_LINE + TAB + ":ET:" + strEntityType + NEW_LINE + TAB + ":EID:" +
                    iEntityID + NEW_LINE + TAB + ":AC:" + strAttributeCode + NEW_LINE + TAB + ":NLS:" + iNLSID + NEW_LINE + TAB +
                    ":BEX:" + strBlobExtension + NEW_LINE + TAB + ":BSZ:" + iblobsize + NEW_LINE + TAB + ":Active:" + bActive +
                    NEW_LINE + TAB + ":COUNT:" + count);

                if (!bActive) {
                    deleterecord.clearParameters();
                    deleterecord.setString(1, strEntityType);
                    deleterecord.setInt(2, iEntityID);
                    deleterecord.setString(3, strAttributeCode);
                    deleterecord.setInt(4, iNLSID);
                    deleterecord.executeUpdate();

                } else {

                    try {

                        selectrecord.clearParameters();
                        selectrecord.setString(1, strEntityType);
                        selectrecord.setInt(2, iEntityID);
                        selectrecord.setString(3, strAttributeCode);
                        selectrecord.setInt(4, iNLSID);
                        rs1 = selectrecord.executeQuery();
                        if (rs1.next()) {
                            updaterecord.clearParameters();
                            updaterecord.setString(1, m_strNow);
                            updaterecord.setString(2, strBlobExtension);
                            updaterecord.setBytes(3, mybytes);
                            updaterecord.setString(4, strEntityType);
                            updaterecord.setInt(5, iEntityID);
                            updaterecord.setString(6, strAttributeCode);
                            updaterecord.setInt(7, iNLSID);
                            updaterecord.executeUpdate();
                        } else {
                            insertrecord.clearParameters();
                            insertrecord.setString(1, m_strNow);
                            insertrecord.setString(2, strEntityType);
                            insertrecord.setInt(3, iEntityID);
                            insertrecord.setString(4, strAttributeCode);
                            insertrecord.setInt(5, iNLSID);
                            insertrecord.setString(6, strBlobExtension);
                            insertrecord.setBytes(7, mybytes);
                            insertrecord.executeUpdate();
                        }
                    }
                    finally {
                        if (rs1 != null) {
                            rs1.close();
                            rs1 = null;
                        }
                    }
                }

                count++;
                reploop++;
                if (reploop == BLOB_COMMIT_COUNT) {
                    D.ebug(D.EBUG_INFO, "updateBlob:commit on:" + count);
                    m_conODS.commit();
                    reploop = 0;
                }
            }
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }

            getBlob1.close();
            insertrecord.close();
            updaterecord.close();
            deleterecord.close();
            selectrecord.close();
            m_conODS.commit();
            m_dbPDH.commit();
            m_dbPDH.freeStatement();
        }

        D.ebug(D.EBUG_INFO, "updateBlob:Finished.  # of Recs Processed:" + count + "  # number of errors:" + errcount);

    }

    /**
     *  This rebuiilds the Metadescription Table
     *
     *@exception  SQLException
     *@exception  MiddlewareException
     */
    protected void rebuildMetaDescription() throws SQLException, MiddlewareException {

        String strTableName = m_strODSSchema + ".METAFLAGTABLE";
        NLSItem nlsi = m_prof.getReadLanguage();

        String strDropSQL = "DROP TABLE " + strTableName;
        String strTableSQL = "CREATE TABLE " + strTableName + " (" + "VALFROM TIMESTAMP NOT NULL, " +
            "ATTRIBUTECODE CHAR(32) NOT NULL, " + "NLSID INT NOT NULL, " + "ATTRIBUTEVALUE CHAR(32) NOT NULL, " +
            "SHORTDESCRIPTION CHAR(64), " + "LONGDESCRIPTION CHAR(128) " + ")";

        String strIndexSQL = " CREATE UNIQUE INDEX " + strTableName + "_PK ON " + strTableName +
            "(ATTRIBUTECODE, ATTRIBUTEVALUE, NLSID)";
        String strInsertSQL = " INSERT INTO " + strTableName + " ( " + "VALFROM, " + "ATTRIBUTECODE, " + "NLSID, " +
            "ATTRIBUTEVALUE, " + "SHORTDESCRIPTION, " + "LONGDESCRIPTION " + "  ) " + "VALUES (?,?,?,?,?,?)";

        String strTableSpace = ODSServerProperties.getTableSpace(m_strODSSchema, "FLAG");
        String strIndexSpace = ODSServerProperties.getIndexSpace(m_strODSSchema, "FLAG");

        String strAttributeCode = null;
        String strAttributeType = null;

        Statement ddlstmt = null;
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        PreparedStatement psFlag = null;

        try {

            ddlstmt = m_conODS.createStatement();

            strTableSpace = (strTableSpace.equals("DEFAULT") ? m_strTableSpace : strTableSpace);
            strIndexSpace = (strIndexSpace.equals("DEFAULT") ? m_strIndexSpace : strIndexSpace);
            strTableSQL = strTableSQL + (strTableSpace.equals("DEFAULT") ? "" : " IN " + strTableSpace) +
                (strIndexSpace.equals("DEFAULT") ? "" : " INDEX IN " + strIndexSpace);

            // Let us drop the table first
            try {
                D.ebug(D.EBUG_INFO, "rebuildMetaDesc:dropping Table:" + strTableName);
                ddlstmt.executeUpdate(strDropSQL);
                m_conODS.commit();
            }
            catch (SQLException ex) {
                D.ebug(D.EBUG_INFO, "rebuildMetaDesc:Skipping Table Drop:" + strTableName);
                ex.printStackTrace();
            }

            D.ebug(D.EBUG_INFO, "rebuildMetaDesc:creating table:" + strTableSQL);
            ddlstmt.executeUpdate(strTableSQL);

            // Now the index
            D.ebug(D.EBUG_INFO, "rebuildMetaDesc:creating index:" + strIndexSQL);
            ddlstmt.executeUpdate(strIndexSQL);

        }
        finally {

            if (ddlstmt != null) {
                ddlstmt.close();
                ddlstmt = null;
            }
            m_conODS.commit();
        }

        //
        // Lets get some meta stuff
        //
        try {
            rs = m_dbPDH.callGBL5714(new ReturnStatus( -1), m_prof.getEnterprise(), m_prof.getRoleCode());
            rdrs = new ReturnDataResultSet(rs);

        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }

            m_dbPDH.freeStatement();
            m_dbPDH.isPending();
            m_dbPDH.commit();
        }

        try {

            EANMetaFlagAttribute mfa = null;
            psFlag = m_conODS.prepareStatement(strInsertSQL);

            for (int ii = 0; ii < rdrs.size(); ii++) {
                strAttributeCode = rdrs.getColumn(ii, 0);
                strAttributeType = rdrs.getColumn(ii, 1);
                mfa = new MetaMultiFlagAttribute(null, m_dbPDH, m_prof, strAttributeCode, true);

                D.ebug(D.EBUG_INFO, "rebuildMetaDesc:Creating Rows for :" + strAttributeCode + ":" + strAttributeType);

                for (int ix = 0; ix < m_prof.getReadLanguages().size(); ix++) {
                    m_prof.setReadLanguage(ix);
                    for (int iy = 0; iy < mfa.getMetaFlagCount(); iy++) {
                        MetaFlag mf = mfa.getMetaFlag(iy);
                        String strFlagCode = mf.getFlagCode();
                        String strShortDescription = mf.getShortDescription();
                        String strLongDescription = mf.getLongDescription();
                        int iNLSID = mf.getNLSID();
                        psFlag.setString(1, m_strNow);
                        psFlag.setString(2, strAttributeCode);
                        psFlag.setInt(3, iNLSID);
                        psFlag.setString(4, strFlagCode);
                        psFlag.setString(5, strShortDescription);
                        psFlag.setString(6, strLongDescription);
                        psFlag.execute();
                        D.ebug(D.EBUG_INFO,
                               "rebuildMetaDesc:inserting record:" + strAttributeCode + ":" + iNLSID + ":" + strFlagCode + ":" +
                               strShortDescription + ":" + strLongDescription);
                    }
                }
                m_conODS.commit();
            }
        }
        catch (NullPointerException ne) {
            D.ebug(D.EBUG_INFO, "rebuildMetaDesc:Error while creating metadescription rows for Attribute:" + strAttributeCode);
            throw ne;
        }
        finally {
            if (psFlag != null) {
                psFlag.close();
                psFlag = null;
            }
        }

        //
        // Set it back
        //
        m_prof.setReadLanguage(nlsi);

    }

    /**
     * rebuildSoftware
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    protected void rebuildSoftware() throws SQLException, MiddlewareException {

        String strTableName = m_strODSSchema + ".SOFTWARE";
        String strDropSQL = "DROP TABLE " + strTableName;
        String strTableSQL = "CREATE TABLE " + strTableName + " (" + "VALFROM TIMESTAMP NOT NULL WITH DEFAULT CURRENT TIMESTAMP, " +
            "ENTITYTYPE VARCHAR(32) NOT NULL, " + "ENTITYID INT NOT NULL," + "NLSID INT NOT NULL, " +
            "CATEGORY_FC CHAR(16) NOT NULL, " + "CATEGORY VARCHAR(32) NOT NULL, " + "VALUE VARCHAR(1024) " + ")";

        String strIndexSQL = " CREATE UNIQUE INDEX " + strTableName + "_PK ON " + strTableName +
            "(ENTITYID, ENTITYTYPE, NLSID, CATEGORY_FC)";
        String strSQL1 = "select distinct id1 " + "from " + m_strODSSchema + ".ofso ofso ";
        String strSQL2 = "select distinct id1 " + "from " + m_strODSSchema + ".csolso csolso ";
        String strSQL3 = "select distinct varsbb.id1 " + "from " + m_strODSSchema + ".varsbb varsbb " + "inner join " +
            m_strODSSchema + ".sbbso sbbso " + " on sbbso.id1 = varsbb.id2";
        String strSQL4 = "select distinct cvarsbb.id1 " + "from " + m_strODSSchema + ".cvarsbb cvarsbb " + "inner join  " +
            m_strODSSchema + ".sbbso sbbso " + " on sbbso.id1 = cvarsbb.id2";

        String strTableSpace = ODSServerProperties.getTableSpace(m_strODSSchema, "SOFTWARE");
        String strIndexSpace = ODSServerProperties.getIndexSpace(m_strODSSchema, "SOFTWARE");

        PreparedStatement psSO1 = null;
        PreparedStatement psSO2 = null;
        PreparedStatement psSO3 = null;
        PreparedStatement psSO4 = null;

        ResultSet rs = null;
        Statement ddlstmt = null;
        ReturnDataResultSet rdrsOF = null;
        ReturnDataResultSet rdrsCSOL = null;
        ReturnDataResultSet rdrsVAR = null;
        ReturnDataResultSet rdrsCVAR = null;

        try {

            ddlstmt = m_conODS.createStatement();
            strTableSpace = (strTableSpace.equals("DEFAULT") ? m_strTableSpace : strTableSpace);
            strIndexSpace = (strIndexSpace.equals("DEFAULT") ? m_strIndexSpace : strIndexSpace);
            strTableSQL = strTableSQL + (strTableSpace.equals("DEFAULT") ? "" : " IN " + strTableSpace) +
                (strIndexSpace.equals("DEFAULT") ? "" : " INDEX IN " + strIndexSpace);

            try {
                D.ebug(D.EBUG_INFO, "rebuildSoftware:dropping Table:" + strTableName);
                ddlstmt.executeUpdate(strDropSQL);
                m_conODS.commit();
            }
            catch (SQLException ex) {
                D.ebug(D.EBUG_INFO, "rebuildSoftware:Skipping Table Drop:" + strTableName + ":" + ex.getMessage());
            }

            D.ebug(D.EBUG_INFO, "rebuildSoftware:creating table:" + strTableSQL);
            ddlstmt.executeUpdate(strTableSQL);

            // Now the index
            D.ebug(D.EBUG_INFO, "rebuildSoftware:creating index:" + strIndexSQL);
            ddlstmt.executeUpdate(strIndexSQL);

        }
        finally {
            if (ddlstmt != null) {
                ddlstmt.close();
                ddlstmt = null;
            }
            m_conODS.commit();
        }

        psSO1 = m_conODS.prepareStatement(strSQL1);
        try {
            rs = psSO1.executeQuery();
            rdrsOF = new ReturnDataResultSet(rs);
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (psSO1 != null) {
                psSO1.close();
                psSO1 = null;
            }
        }

        D.ebug(D.EBUG_INFO, "rebuildSoftware:Scannig OF software titles: count is:" + rdrsOF.size());

        psSO2 = m_conODS.prepareStatement(strSQL2);

        try {
            rs = psSO2.executeQuery();
            rdrsCSOL = new ReturnDataResultSet(rs);

        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (psSO2 != null) {
                psSO2.close();
                psSO2 = null;
            }
        }

        D.ebug(D.EBUG_INFO, "rebuildSoftware:Scannig CSOL software titles: count is:" + rdrsCSOL.size());

        psSO3 = m_conODS.prepareStatement(strSQL3);

        try {
            rs = psSO3.executeQuery();
            rdrsVAR = new ReturnDataResultSet(rs);

        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (psSO3 != null) {
                psSO3.close();
                psSO3 = null;
            }
        }

        D.ebug(D.EBUG_INFO, "rebuildSoftware:Scannig VAR software titles: count is:" + rdrsVAR.size());

        psSO4 = m_conODS.prepareStatement(strSQL4);

        try {
            rs = psSO4.executeQuery();
            rdrsCVAR = new ReturnDataResultSet(rs);
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (psSO4 != null) {
                psSO4.close();
                psSO4 = null;
            }
        }
        D.ebug(D.EBUG_INFO, "rebuildSoftware:Scannig CVAR software titles: count is:" + rdrsCVAR.size());

        rebuildSoftwareForEntities(rdrsOF, rdrsCSOL, rdrsVAR, rdrsCVAR, false);

        m_conODS.commit();

    }

    /**
     * updateSoftware
     *
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @author Dave
     */
    protected void updateSoftware() throws SQLException, MiddlewareException {

        // Get a List of any OF changes since the last time this thing ran
        String strOFEntitySQL = "SELECT " + " DISTINCT OF.ENTITYID " + "FROM " + m_strODSSchema + ".OF OF " + " WHERE " +
            "    OF.ValFrom > '" + m_strLastRun + "' " + "AND OF.ValTo = '9999-12-31-00.00.00.000000' " + "UNION " + "SELECT " +
            " DISTINCT OFSO.ID1 " + "FROM " + m_strODSSchema + ".SO SO " + "INNER JOIN " + m_strODSSchema + ".OFSO OFSO ON " +
            "    OFSO.ID2 = SO.ENTITYID " + "AND OFSO.valto = '9999-12-31-00.00.00.000000' " + " WHERE " + "    SO.ValFrom > '" +
            m_strLastRun + "' " + "AND SO.ValTo = '9999-12-31-00.00.00.000000' " + "UNION " + "SELECT " + " DISTINCT OFSO.ID1 " +
            "FROM " + m_strODSSchema + ".OFSO OFSO " + " WHERE " + "    OFSO.ValFrom > '" + m_strLastRun + "' " +
            "AND OFSO.ValTo = '9999-12-31-00.00.00.000000' " + "UNION " + "SELECT " + " DISTINCT CASE(RTRIM(DL.ENTITYTYPE)) " +
            "    WHEN 'OF' THEN DL.ENTITYID " + "    WHEN 'OFSO' THEN DL.ENTITY1ID " + "    WHEN 'SO' THEN COALESCE((" +
            "                    SELECT MAX(OFSODL.ID1) from " + m_strODSSchema + ".ofso ofsodl WHERE " +
            "                    ofsodl.id2 = DL.ENTITYID " + "                   ), -1)" + " END " + "FROM " + m_strODSSchema +
            ".DELETELOG DL " + " WHERE " + "    DL.ENTITYTYPE IN ('OF','SO','OFSO') " + "AND DL.ValFrom > '" + m_strLastRun + "'";

        String strCSOLEntitySQL = "SELECT " + " DISTINCT CSOL.ENTITYID " + "FROM " + m_strODSSchema + ".CSOL CSOL " + " WHERE " +
            "    CSOL.ValFrom > '" + m_strLastRun + "' " + "AND CSOL.ValTo = '9999-12-31-00.00.00.000000' " + "UNION " + "SELECT " +
            " DISTINCT CSOLSO.ID1 " + "FROM " + m_strODSSchema + ".SO SO " + "INNER JOIN " + m_strODSSchema + ".CSOLSO CSOLSO ON " +
            "    CSOLSO.ID2 = SO.ENTITYID " + "AND CSOLSO.valto = '9999-12-31-00.00.00.000000' " + " WHERE " + "    SO.ValFrom > '" +
            m_strLastRun + "' " + "AND SO.ValTo = '9999-12-31-00.00.00.000000' " + "UNION " + "SELECT " + " DISTINCT CSOLSO.ID1 " +
            "FROM " + m_strODSSchema + ".CSOLSO CSOLSO " + " WHERE " + "    CSOLSO.ValFrom > '" + m_strLastRun + "' " +
            "AND CSOLSO.ValTo = '9999-12-31-00.00.00.000000' " + "UNION " + "SELECT " + " DISTINCT CASE(RTRIM(DL.ENTITYTYPE)) " +
            "    WHEN 'CSOL' THEN DL.ENTITYID " + "    WHEN 'CSOLSO' THEN DL.ENTITY1ID " + "    WHEN 'SO' THEN COALESCE((" +
            "                    SELECT MAX(CSOLSODL.ID1) from " + m_strODSSchema + ".csolso csolsodl WHERE " +
            "                    csolsodl.id2 = DL.ENTITYID " + "                   ), -1)" + " END " + "FROM " + m_strODSSchema +
            ".DELETELOG DL " + " WHERE " + "    DL.ENTITYTYPE IN ('CSOL','SO','CSOLSO') " + "AND DL.ValFrom > '" + m_strLastRun +
            "'";

        String strVAREntitySQL = "SELECT  " + " DISTINCT VAR.ENTITYID  " + "FROM   " + m_strODSSchema + ".VAR VAR  " + " WHERE  " +
            "    VAR.ValFrom > '" + m_strLastRun + "'  " + "AND VAR.ValTo = '9999-12-31-00.00.00.000000'  " +
            // 2) changed SO
            "UNION  " + "SELECT  " + " DISTINCT VARSBB.ID1  " + "FROM   " + m_strODSSchema + ".VARSBB VARSBB  " + "INNER JOIN   " +
            m_strODSSchema + ".SO SO ON " + "    SO.ValFrom > '" + m_strLastRun + "'  " +
            "AND SO.ValTo = '9999-12-31-00.00.00.000000'  " + "INNER JOIN   " + m_strODSSchema + ".SBBSO SBBSO ON " +
            "    SBBSO.ID1 = VARSBB.ID2 " + "AND SBBSO.ID2 = SO.ENTITYID  " +
            "AND SBBSO.valto = '9999-12-31-00.00.00.000000'              " + "WHERE " + "    VARSBB.ID2 = SBBSO.ID1  " +
            "AND VARSBB.valto = '9999-12-31-00.00.00.000000'  " +
            // 3) changed VARSBB
            "UNION  " + "SELECT  " + " DISTINCT VARSBB.ID1  " + "FROM  " + m_strODSSchema + ".VARSBB VARSBB  " + "INNER JOIN   " +
            m_strODSSchema + ".SBBSO SBBSO ON " + "    SBBSO.ID1 = VARSBB.ID2 " +
            "AND SBBSO.valto = '9999-12-31-00.00.00.000000'              " + " WHERE  " + "    VARSBB.ValFrom > '" + m_strLastRun +
            "'  " + "AND VARSBB.ValTo = '9999-12-31-00.00.00.000000'  " +
            // 4) changed SBBSO
            "UNION  " + "SELECT  " + " DISTINCT VARSBB.ID1  " + "FROM  " + m_strODSSchema + ".SBBSO SBBSO " + "INNER JOIN   " +
            m_strODSSchema + ".VARSBB VARSBB ON " + "    VARSBB.ID2 = SBBSO.ID1 " +
            "AND VARSBB.valto = '9999-12-31-00.00.00.000000'              " + " WHERE  " + "    SBBSO.ValFrom > '" + m_strLastRun +
            "'  " + "AND SBBSO.ValTo = '9999-12-31-00.00.00.000000'  " +
            // 5) delete logs
            "UNION  " + "SELECT  " + " DISTINCT CASE(RTRIM(DL.ENTITYTYPE))  " + "    WHEN 'VAR' THEN DL.ENTITYID  " +
            "    WHEN 'VARSBB' THEN DL.ENTITY1ID  " + "    WHEN 'SO' THEN COALESCE(( " + "         SELECT  " +
            "          MAX(VARSBB.ID1)  " + "         FROM   " + m_strODSSchema + ".SBBSO SBBSO " + "         INNER JOIN   " +
            m_strODSSchema + ".VARSBB VARSBB ON " + "             VARSBB.ID2 = SBBSO.ID1  " +
            "         AND VARSBB.valto = '9999-12-31-00.00.00.000000'  " + "           WHERE " +
            "             SBBSO.ID1 = VARSBB.ID2 " + "         AND SBBSO.ID2 = DL.ENTITYID  " +
            "         AND DL.EntityType = 'SO' " + "         AND SBBSO.valto = '9999-12-31-00.00.00.000000'   " +
            "                    ),-1) " + "    WHEN 'SBBSO' THEN COALESCE(( " + "         SELECT  " +
            "          MAX(VARSBB.ID1)  " + "         FROM   " + m_strODSSchema + ".DELETELOG DLSBBSO " + "         INNER JOIN   " +
            m_strODSSchema + ".VARSBB VARSBB ON  " + "             VARSBB.ID2 = DLSBBSO.ENTITY1ID  " +
            "         AND VARSBB.valto = '9999-12-31-00.00.00.000000' " + "           WHERE " +
            "             DLSBBSO.ENTITYTYPE = 'SBBSO'    " + "         AND DLSBBSO.ENTITY1ID = VARSBB.ID2 " +
            "         AND DLSBBSO.ENTITYID = DL.ENTITYID  " + "         AND DLSBBSO.ValFrom > '" + m_strLastRun + "'" +
            "                    ),-1) " + " END  " + "FROM   " + m_strODSSchema + ".DELETELOG DL  " + " WHERE  " +
            "    DL.ENTITYTYPE IN ('VAR','SO','VARSBB', 'SBBSO') " + "AND DL.ValFrom > '" + m_strLastRun + "'";

        String strCVAREntitySQL =
            // 1) changed CVARs
            "SELECT  " + " DISTINCT CVAR.ENTITYID  " + "FROM   " + m_strODSSchema + ".CVAR CVAR  " + " WHERE  " +
            "    CVAR.ValFrom > '" + m_strLastRun + "'  " + "AND CVAR.ValTo = '9999-12-31-00.00.00.000000'  " +
            // 2) changed SO
            "UNION  " + "SELECT  " + " DISTINCT CVARSBB.ID1  " + "FROM   " + m_strODSSchema + ".CVARSBB CVARSBB  " +
            "INNER JOIN   " + m_strODSSchema + ".SO SO ON " + "    SO.ValFrom > '" + m_strLastRun + "'  " +
            "AND SO.ValTo = '9999-12-31-00.00.00.000000'  " + "INNER JOIN   " + m_strODSSchema + ".SBBSO SBBSO ON " +
            "    SBBSO.ID1 = CVARSBB.ID2 " + "AND SBBSO.ID2 = SO.ENTITYID  " +
            "AND SBBSO.valto = '9999-12-31-00.00.00.000000'              " + "WHERE " + "    CVARSBB.ID2 = SBBSO.ID1  " +
            "AND CVARSBB.valto = '9999-12-31-00.00.00.000000'  " +
            // 3) changed CVARSBB
            "UNION  " + "SELECT  " + " DISTINCT CVARSBB.ID1  " + "FROM  " + m_strODSSchema + ".CVARSBB CVARSBB  " + "INNER JOIN   " +
            m_strODSSchema + ".SBBSO SBBSO ON " + "    SBBSO.ID1 = CVARSBB.ID2 " +
            "AND SBBSO.valto = '9999-12-31-00.00.00.000000'              " + " WHERE  " + "    CVARSBB.ValFrom > '" + m_strLastRun +
            "'  " + "AND CVARSBB.ValTo = '9999-12-31-00.00.00.000000'  " +
            // 4) changed SBBSO
            "UNION  " + "SELECT  " + " DISTINCT CVARSBB.ID1  " + "FROM  " + m_strODSSchema + ".SBBSO SBBSO " + "INNER JOIN   " +
            m_strODSSchema + ".CVARSBB CVARSBB ON " + "    CVARSBB.ID2 = SBBSO.ID1 " +
            "AND CVARSBB.valto = '9999-12-31-00.00.00.000000'              " + " WHERE  " + "    SBBSO.ValFrom > '" + m_strLastRun +
            "'  " + "AND SBBSO.ValTo = '9999-12-31-00.00.00.000000'  " +
            // 5) delete logs
            "UNION  " + "SELECT  " + " DISTINCT CASE(RTRIM(DL.ENTITYTYPE))  " + "    WHEN 'CVAR' THEN DL.ENTITYID  " +
            "    WHEN 'CVARSBB' THEN DL.ENTITY1ID  " + "    WHEN 'SO' THEN COALESCE(( " + "         SELECT  " +
            "          MAX(CVARSBB.ID1) " + "         FROM   " + m_strODSSchema + ".SBBSO SBBSO " + "         INNER JOIN   " +
            m_strODSSchema + ".CVARSBB CVARSBB ON " + "             CVARSBB.ID2 = SBBSO.ID1  " +
            "         AND CVARSBB.valto = '9999-12-31-00.00.00.000000'  " + "           WHERE " +
            "             SBBSO.ID1 = CVARSBB.ID2 " + "         AND SBBSO.ID2 = DL.ENTITYID  " +
            "         AND DL.ENTITYTYPE = 'SO' " + "         AND SBBSO.valto = '9999-12-31-00.00.00.000000'   " +
            "                    ),-1) " + "    WHEN 'SBBSO' THEN COALESCE(( " + "         SELECT  " +
            "          MAX(CVARSBB.ID1)  " + "         FROM   " + m_strODSSchema + ".DELETELOG DLSBBSO " + "         INNER JOIN   " +
            m_strODSSchema + ".CVARSBB CVARSBB ON  " + "             CVARSBB.ID2 = DLSBBSO.ENTITY1ID  " +
            "         AND CVARSBB.valto = '9999-12-31-00.00.00.000000' " + "           WHERE " +
            "             DLSBBSO.ENTITYTYPE = 'SBBSO'    " + "         AND DLSBBSO.ENTITY1ID = CVARSBB.ID2 " +
            "         AND DLSBBSO.ENTITYID = DL.ENTITYID  " + "         AND DLSBBSO.ValFrom > '" + m_strLastRun + "'" +
            "                    ),-1) " + " END  " + "FROM   " + m_strODSSchema + ".DELETELOG DL  " + " WHERE  " +
            "    DL.ENTITYTYPE IN ('CVAR','SO','CVARSBB', 'SBBSO') " + "AND DL.ValFrom > '" + m_strLastRun + "'";

        ResultSet rs = null;
        PreparedStatement psOF = null;
        PreparedStatement psCSOL = null;
        PreparedStatement psVAR = null;
        PreparedStatement psCVAR = null;

        ReturnDataResultSet rdrsOF = null;
        ReturnDataResultSet rdrsCSOL = null;
        ReturnDataResultSet rdrsVAR = null;
        ReturnDataResultSet rdrsCVAR = null;

        try {

            D.ebug(D.EBUG_DETAIL, "strOFEntitySQL:" + strOFEntitySQL);

            psOF = m_conODS.prepareStatement(strOFEntitySQL);
            rs = psOF.executeQuery();
            rdrsOF = new ReturnDataResultSet(rs);

        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (psOF != null) {
                psOF.close();
                psOF = null;
            }
            m_conODS.commit();
        }

        D.ebug(D.EBUG_INFO, "updateSoftware:Scanning OF software titles: count is:" + rdrsOF.size());

        try {
            psCSOL = m_conODS.prepareStatement(strCSOLEntitySQL);
            rs = psCSOL.executeQuery();
            rdrsCSOL = new ReturnDataResultSet(rs);
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (psCSOL != null) {
                psCSOL.close();
                psCSOL = null;
            }
            m_conODS.commit();
        }
        D.ebug(D.EBUG_INFO, "updateSoftware:Scanning CSOL software titles: count is:" + rdrsCSOL.size());

        try {
            psVAR = m_conODS.prepareStatement(strVAREntitySQL);
            rs = psVAR.executeQuery();
            rdrsVAR = new ReturnDataResultSet(rs);
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (psVAR != null) {
                psVAR.close();
                psVAR = null;
            }
            m_conODS.commit();
        }
        D.ebug(D.EBUG_INFO, "updateSoftware:Scanning VAR software titles: count is:" + rdrsVAR.size());

        try {
            psCVAR = m_conODS.prepareStatement(strCVAREntitySQL);
            rs = psCVAR.executeQuery();
            rdrsCVAR = new ReturnDataResultSet(rs);
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (psCVAR != null) {
                psCVAR.close();
                psCVAR = null;
            }
            m_conODS.commit();
        }
        D.ebug(D.EBUG_INFO, "updateSoftware:Scanning CVAR software titles: count is:" + rdrsCVAR.size());

        rebuildSoftwareForEntities(rdrsOF, rdrsCSOL, rdrsVAR, rdrsCVAR, true);
        m_conODS.commit();
    }

    /**
     * Pulled out of rebuildSoftware() method -- this guy will build records in the Software table for
     * the passed OFs, CSOLs.
     */
    private void rebuildSoftwareForEntities(ReturnDataResultSet _rdrsOF, ReturnDataResultSet _rdrsCSOL,
                                            ReturnDataResultSet _rdrsVAR, ReturnDataResultSet _rdrsCVAR, boolean _bDeleteFirst) throws
        SQLException, MiddlewareException {

        String strTableName = m_strODSSchema + ".SOFTWARE";

        //
        // This is used in the case of a delete (all NLSID's)
        //
        String strDeleteSoftwareSQL = "Delete FROM  " + m_strODSSchema + ".SOFTWARE" + " WHERE " + "ENTITYTYPE = ? AND " +
            "ENTITYID = ?";

        String strSQL_OF = "select distinct of.nlsid, coalesce(so.SOPRODCATEGORY_FC,'MISSING FC'), coalesce(so.SOPRODCATEGORY, 'MISSING CAT'), coalesce(so.SOFTWARETITLE, 'MISSING TITLE') " +
            " from " + m_strODSSchema + ".of of " + " join " + m_strODSSchema + ".ofso ofso on " + " ofso.id1 = of.entityid " +
            " join " + m_strODSSchema + ".so so on " + " so.entityid = ofso.id2 and " + " so.nlsid = of.nlsid " + " where " +
            "   of.entityid = ? " + " order by 1,2";

        String strSQL_CSOL = "select distinct csol.nlsid, coalesce(so.SOPRODCATEGORY_FC,'MISSING FC'), coalesce(so.SOPRODCATEGORY, 'MISSING CAT'), coalesce(so.SOFTWARETITLE, 'MISSING TITLE') " +
            " from " + m_strODSSchema + ".csol csol " + " join " + m_strODSSchema + ".csolso csolso on " +
            " csolso.id1 = csol.entityid " + " join " + m_strODSSchema + ".so so on " + " so.entityid = csolso.id2 and " +
            " so.nlsid = csol.nlsid " + " where " + "   csol.entityid = ? " + " order by 1,2";

        String strSQL_VAR = "select distinct var.nlsid, coalesce(so.SOPRODCATEGORY_FC,'MISSING FC'), coalesce(so.SOPRODCATEGORY, 'MISSING CAT'), coalesce(so.SOFTWARETITLE, 'MISSING TITLE') " +
            " from " + m_strODSSchema + ".var var " + " join " + m_strODSSchema + ".varsbb varsbb on " +
            " varsbb.id1 = var.entityid " + " join " + m_strODSSchema + ".sbbso sbbso on " + " sbbso.id1 = varsbb.id2 " + " join " +
            m_strODSSchema + ".so so on " + " so.entityid = sbbso.id2 and " + " so.nlsid = var.nlsid " + " where " +
            "   var.entityid = ? " + " order by 1,2";

        String strSQL_CVAR = "select distinct cvar.nlsid, coalesce(so.SOPRODCATEGORY_FC,'MISSING FC'), coalesce(so.SOPRODCATEGORY, 'MISSING CAT'), coalesce(so.SOFTWARETITLE, 'MISSING TITLE') " +
            " from " + m_strODSSchema + ".cvar cvar " + " join " + m_strODSSchema + ".cvarsbb cvarsbb on " +
            " cvarsbb.id1 = cvar.entityid " + " join " + m_strODSSchema + ".sbbso sbbso on " + " sbbso.id1 = cvarsbb.id2 " +
            " join " + m_strODSSchema + ".so so on " + " so.entityid = sbbso.id2 and " + " so.nlsid = cvar.nlsid " + " where " +
            "   cvar.entityid = ? " + " order by 1,2";

        String strInsertSQL = "INSERT INTO " + strTableName + " ( " + "ENTITYTYPE, " + "ENTITYID, " + "NLSID, " + "CATEGORY_FC, " +
            "CATEGORY, " + "VALUE " + "  ) " + "VALUES (?,?,?,?,?,?)";

        int icc = 0;

        PreparedStatement psOF = null;
        PreparedStatement psCSOL = null;
        PreparedStatement psVAR = null;
        PreparedStatement psCVAR = null;
        PreparedStatement psIns = null;
        PreparedStatement psDel = null;

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        try {
            psIns = m_conODS.prepareStatement(strInsertSQL);
            psDel = m_conODS.prepareStatement(strDeleteSoftwareSQL);

            try {

                psOF = m_conODS.prepareStatement(strSQL_OF);

                // ok.. lets track a hash table of software items to only process them once
                // and lets keep track and send a mail when we find multiples..
                // and lets keep track of the length so we do not over extend what we have

                // FIRST OF
                for (int ii = 0; ii < _rdrsOF.size(); ii++) {

                    boolean bfirstpass = true;
                    int iPriorNLSID = 0;
                    String strValue = "";
                    String strPriorCATFC = "";
                    String strPriorCAT = "";

                    int iEntityID = _rdrsOF.getColumnInt(ii, 0);
                    if (_bDeleteFirst) {
                        psDel.setString(1, "OF");
                        psDel.setInt(2, iEntityID);
                        psDel.execute();
                    }
                    //
                    // O.K. lets go populate the SoftwareTable
                    //
                    try {
                        psOF.setInt(1, iEntityID);
                        rs = psOF.executeQuery();
                        rdrs = new ReturnDataResultSet(rs);
                    }
                    finally {
                        if (rs != null) {
                            rs.close();
                            rs = null;
                        }
                    }
                    D.ebug(D.EBUG_DETAIL, "rebuildSoftware:# " + ii + ". Scannig OF software titles for EID:" + iEntityID);

                    for (int ix = 0; ix < rdrs.size(); ix++) {
                        int iNLSID = rdrs.getColumnInt(ix, 0);
                        String strCATFC = rdrs.getColumn(ix, 1);
                        String strCAT = rdrs.getColumn(ix, 2);
                        String strST = rdrs.getColumn(ix, 3);
                        D.ebug(D.EBUG_SPEW,
                               "rebuildSoftware:OF Software titles:answers:EID:" + iEntityID + ":NLS:" + iNLSID + ":" + strCATFC +
                               ":" + strCAT + ":" + strST);
                        if (bfirstpass) {
                            bfirstpass = false;
                            iPriorNLSID = iNLSID;
                            strPriorCATFC = strCATFC;
                            strPriorCAT = strCAT;
                            strValue = strST;
                        } else if (strPriorCATFC.equals(strCATFC) && iPriorNLSID == iNLSID) {
                            strValue = (strValue.length() == 0 ? strST : strValue + ";" + strST);
                        } else {
                            // we need to insert a new record here
                            // then reset everything
                            psIns.setString(1, "OF");
                            psIns.setInt(2, iEntityID);
                            psIns.setInt(3, iPriorNLSID);
                            psIns.setString(4, strPriorCATFC);
                            psIns.setString(5, strPriorCAT);
                            psIns.setString(6, strValue);
                            D.ebug(D.EBUG_SPEW,
                                   "rebuildSoftware:inserting record:OF:" + iEntityID + ":" + iPriorNLSID + ":" + strPriorCATFC +
                                   ":" + strPriorCAT + ":" + strValue);
                            psIns.execute();
                            iPriorNLSID = iNLSID;
                            strPriorCATFC = strCATFC;
                            strPriorCAT = strCAT;
                            strValue = strST;
                        }
                    }

                    if (rdrs.size() > 0) {
                        // do not forget the last one
                        psIns.setString(1, "OF");
                        psIns.setInt(2, iEntityID);
                        psIns.setInt(3, iPriorNLSID);
                        psIns.setString(4, strPriorCATFC);
                        psIns.setString(5, strPriorCAT);
                        psIns.setString(6, strValue);
                        D.ebug(D.EBUG_SPEW,
                               "rebuildSoftware:inserting record:OF:" + iEntityID + ":" + iPriorNLSID + ":" + strPriorCATFC + ":" +
                               strPriorCAT + ":" + strValue);
                        psIns.execute();
                    }

                    if (icc > m_iChuckSize) {
                        icc = 0;
                        D.ebug(D.EBUG_INFO, "rebuildSoftware:COMMITTING data.");
                        m_conODS.commit();
                    } else {
                        icc++;
                    }
                }

            }
            finally {
                if (psOF != null) {
                    psOF.close();
                    psOF = null;
                }
            }

            try {

                psCSOL = m_conODS.prepareStatement(strSQL_CSOL);
                for (int ii = 0; ii < _rdrsCSOL.size(); ii++) {

                    boolean bfirstpass = true;
                    int iPriorNLSID = 0;
                    String strValue = "";
                    String strPriorCATFC = "";
                    String strPriorCAT = "";

                    int iEntityID = _rdrsCSOL.getColumnInt(ii, 0);

                    if (_bDeleteFirst) {
                        psDel.setString(1, "CSOL");
                        psDel.setInt(2, iEntityID);
                        psDel.execute();
                    }

                    // O.K. lets go populate the SoftwareTable
                    try {
                        psCSOL.setInt(1, iEntityID);
                        rs = psCSOL.executeQuery();
                        rdrs = new ReturnDataResultSet(rs);
                    }
                    finally {
                        if (rs != null) {
                            rs.close();
                            rs = null;
                        }
                    }

                    D.ebug(D.EBUG_DETAIL, "rebuildSoftware:# " + ii + ". Scannig CSOL software titles for EID:" + iEntityID);

                    for (int ix = 0; ix < rdrs.size(); ix++) {
                        int iNLSID = rdrs.getColumnInt(ix, 0);
                        String strCATFC = rdrs.getColumn(ix, 1);
                        String strCAT = rdrs.getColumn(ix, 2);
                        String strST = rdrs.getColumn(ix, 3);
                        D.ebug(D.EBUG_SPEW,
                               "rebuildSoftware:CSOL Software titles:answers:EID:" + iEntityID + ":NLS:" + iNLSID + ":" + strCATFC +
                               ":" + strCAT + ":" + strST);
                        if (bfirstpass) {
                            bfirstpass = false;
                            iPriorNLSID = iNLSID;
                            strPriorCATFC = strCATFC;
                            strPriorCAT = strCAT;
                            strValue = strST;
                        } else if (strPriorCATFC.equals(strCATFC) && iPriorNLSID == iNLSID) {
                            strValue = (strValue.length() == 0 ? strST : strValue + ";" + strST);
                        } else {
                            psIns.setString(1, "CSOL");
                            psIns.setInt(2, iEntityID);
                            psIns.setInt(3, iPriorNLSID);
                            psIns.setString(4, strPriorCATFC);
                            psIns.setString(5, strPriorCAT);
                            psIns.setString(6, strValue);
                            D.ebug(D.EBUG_SPEW,
                                   "rebuildSoftware:inserting record:CSOL:" + iEntityID + ":" + iPriorNLSID + ":" + strPriorCATFC +
                                   ":" + strPriorCAT + ":" + strValue);
                            psIns.execute();
                            iPriorNLSID = iNLSID;
                            strPriorCATFC = strCATFC;
                            strPriorCAT = strCAT;
                            strValue = strST;
                        }
                    }
                    if (rdrs.size() > 0) {
                        // do not forget the last one
                        psIns.setString(1, "CSOL");
                        psIns.setInt(2, iEntityID);
                        psIns.setInt(3, iPriorNLSID);
                        psIns.setString(4, strPriorCATFC);
                        psIns.setString(5, strPriorCAT);
                        psIns.setString(6, strValue);
                        D.ebug(D.EBUG_SPEW,
                               "rebuildSoftware:inserting record:CSOL:" + iEntityID + ":" + iPriorNLSID + ":" + strPriorCATFC + ":" +
                               strPriorCAT + ":" + strValue);
                        psIns.execute();
                    }

                    if (icc > m_iChuckSize) {
                        icc = 0;
                        D.ebug(D.EBUG_INFO, "rebuildSoftware:COMMITTING data.");
                        m_conODS.commit();
                    } else {
                        icc++;
                    }
                }
            }
            finally {
                if (psCSOL != null) {
                    psCSOL.close();
                    psCSOL = null;
                }
            }

            // NOW VAR
            try {
                psVAR = m_conODS.prepareStatement(strSQL_VAR);

                for (int ii = 0; ii < _rdrsVAR.size(); ii++) {
                    boolean bfirstpass = true;
                    int iPriorNLSID = 0;
                    String strValue = "";
                    String strPriorCATFC = "";
                    String strPriorCAT = "";
                    int iEntityID = _rdrsVAR.getColumnInt(ii, 0);

                    if (_bDeleteFirst) {
                        psDel.setString(1, "VAR");
                        psDel.setInt(2, iEntityID);
                        psDel.execute();
                    }

                    //
                    // O.K. lets go populate the SoftwareTable
                    //
                    try {
                        psVAR.setInt(1, iEntityID);
                        rs = psVAR.executeQuery();
                        rdrs = new ReturnDataResultSet(rs);
                    }
                    finally {
                        if (rs != null) {
                            rs.close();
                            rs = null;
                        }
                    }

                    D.ebug(D.EBUG_DETAIL, "rebuildSoftware:# " + ii + ". Scannig VAR software titles for EID:" + iEntityID);
                    for (int ix = 0; ix < rdrs.size(); ix++) {
                        int iNLSID = rdrs.getColumnInt(ix, 0);
                        String strCATFC = rdrs.getColumn(ix, 1);
                        String strCAT = rdrs.getColumn(ix, 2);
                        String strST = rdrs.getColumn(ix, 3);
                        D.ebug(D.EBUG_SPEW,
                               "rebuildSoftware:VAR Software titles:answers:EID:" + iEntityID + ":NLS:" + iNLSID + ":" + strCATFC +
                               ":" + strCAT + ":" + strST);
                        if (bfirstpass) {
                            bfirstpass = false;
                            iPriorNLSID = iNLSID;
                            strPriorCATFC = strCATFC;
                            strPriorCAT = strCAT;
                            strValue = strST;
                        } else if (strPriorCATFC.equals(strCATFC) && iPriorNLSID == iNLSID) {
                            strValue = (strValue.length() == 0 ? strST : strValue + ";" + strST);
                        } else {
                            // we need to insert a new record here
                            // then reset everything
                            psIns.setString(1, "VAR");
                            psIns.setInt(2, iEntityID);
                            psIns.setInt(3, iPriorNLSID);
                            psIns.setString(4, strPriorCATFC);
                            psIns.setString(5, strPriorCAT);
                            psIns.setString(6, strValue);
                            D.ebug(D.EBUG_SPEW,
                                   "rebuildSoftware:inserting record:VAR:" + iEntityID + ":" + iPriorNLSID + ":" + strPriorCATFC +
                                   ":" + strPriorCAT + ":" + strValue);
                            psIns.execute();
                            iPriorNLSID = iNLSID;
                            strPriorCATFC = strCATFC;
                            strPriorCAT = strCAT;
                            strValue = strST;
                        }
                    }
                    if (rdrs.size() > 0) {
                        // do not forget the last one
                        psIns.setString(1, "VAR");
                        psIns.setInt(2, iEntityID);
                        psIns.setInt(3, iPriorNLSID);
                        psIns.setString(4, strPriorCATFC);
                        psIns.setString(5, strPriorCAT);
                        psIns.setString(6, strValue);
                        D.ebug(D.EBUG_SPEW,
                               "rebuildSoftware:inserting record:VAR:" + iEntityID + ":" + iPriorNLSID + ":" + strPriorCATFC + ":" +
                               strPriorCAT + ":" + strValue);
                        psIns.execute();
                    }

                    if (icc > m_iChuckSize) {
                        icc = 0;
                        D.ebug(D.EBUG_INFO, "rebuildSoftware:COMMITTING data.");
                        m_conODS.commit();
                    } else {
                        icc++;
                    }
                }
            }
            finally {
                if (psVAR != null) {
                    psVAR.close();
                    psVAR = null;
                }
            }

            try {

                psCVAR = m_conODS.prepareStatement(strSQL_CVAR);

                // NOW CVAR
                for (int ii = 0; ii < _rdrsCVAR.size(); ii++) {
                    boolean bfirstpass = true;
                    int iPriorNLSID = 0;
                    String strValue = "";
                    String strPriorCATFC = "";
                    String strPriorCAT = "";
                    int iEntityID = _rdrsCVAR.getColumnInt(ii, 0);

                    if (_bDeleteFirst) {
                        psDel.setString(1, "CVAR");
                        psDel.setInt(2, iEntityID);
                        psDel.execute();
                    }

                    //
                    // O.K. lets go populate the SoftwareTable
                    //
                    try {
                        psCVAR.setInt(1, iEntityID);
                        rs = psCVAR.executeQuery();
                        rdrs = new ReturnDataResultSet(rs);
                    }
                    finally {
                        if (rs != null) {
                            rs.close();
                            rs = null;
                        }
                    }

                    D.ebug(D.EBUG_DETAIL, "rebuildSoftware:# " + ii + ". Scannig CVAR software titles for EID:" + iEntityID);

                    for (int ix = 0; ix < rdrs.size(); ix++) {
                        int iNLSID = rdrs.getColumnInt(ix, 0);
                        String strCATFC = rdrs.getColumn(ix, 1);
                        String strCAT = rdrs.getColumn(ix, 2);
                        String strST = rdrs.getColumn(ix, 3);
                        D.ebug(D.EBUG_SPEW,
                               "rebuildSoftware:CVAR Software titles:answers:EID:" + iEntityID + ":NLS:" + iNLSID + ":" + strCATFC +
                               ":" + strCAT + ":" + strST);
                        if (bfirstpass) {
                            bfirstpass = false;
                            iPriorNLSID = iNLSID;
                            strPriorCATFC = strCATFC;
                            strPriorCAT = strCAT;
                            strValue = strST;
                        } else if (strPriorCATFC.equals(strCATFC) && iPriorNLSID == iNLSID) {
                            strValue = (strValue.length() == 0 ? strST : strValue + ";" + strST);
                        } else {
                            // we need to insert a new record here
                            // then reset everything
                            psIns.setString(1, "CVAR");
                            psIns.setInt(2, iEntityID);
                            psIns.setInt(3, iPriorNLSID);
                            psIns.setString(4, strPriorCATFC);
                            psIns.setString(5, strPriorCAT);
                            psIns.setString(6, strValue);
                            D.ebug(D.EBUG_SPEW,
                                   "rebuildSoftware:inserting record:CVAR:" + iEntityID + ":" + iPriorNLSID + ":" + strPriorCATFC +
                                   ":" + strPriorCAT + ":" + strValue);
                            psIns.execute();
                            iPriorNLSID = iNLSID;
                            strPriorCATFC = strCATFC;
                            strPriorCAT = strCAT;
                            strValue = strST;
                        }
                    }
                    if (rdrs.size() > 0) {
                        // do not forget the last one
                        psIns.setString(1, "CVAR");
                        psIns.setInt(2, iEntityID);
                        psIns.setInt(3, iPriorNLSID);
                        psIns.setString(4, strPriorCATFC);
                        psIns.setString(5, strPriorCAT);
                        psIns.setString(6, strValue);
                        D.ebug(D.EBUG_SPEW,
                               "rebuildSoftware:inserting record:CVAR:" + iEntityID + ":" + iPriorNLSID + ":" + strPriorCATFC + ":" +
                               strPriorCAT + ":" + strValue);
                        psIns.execute();
                    }

                    if (icc > m_iChuckSize) {
                        icc = 0;
                        D.ebug(D.EBUG_INFO, "rebuildSoftware:COMMITTING data.");
                        m_conODS.commit();
                    } else {
                        icc++;
                    }
                }
            }
            finally {
                if (psCVAR != null) {
                    psCVAR.close();
                    psCVAR = null;
                }
            }

        }
        finally {
            psIns.close();
            psDel.close();
            m_conODS.commit();
        }
    }

    /**
     *  Rebuilds the Flag Table
     *
     *@exception  SQLException
     */
    protected void resetMultiFlagTable() throws SQLException {

        String strTableName = m_strODSSchema + ".FLAG";
        String strDropSQL = "DROP TABLE " + strTableName;
        String strTableSQL = "CREATE TABLE " + strTableName + " (" + "VALFROM TIMESTAMP NOT NULL, " +
            "ENTITYTYPE CHAR(16) NOT NULL, " + "ENTITYID INTEGER NOT NULL, " + "NLSID INTEGER NOT NULL, " +
            "ATTRIBUTECODE CHAR(32) NOT NULL, " + "FLAGCODE CHAR(16) NOT NULL, " + "SFVALUE INT NOT NULL, " +
            "FLAGDESCRIPTION VARCHAR(128) NOT NULL " + ")";

        String strTableSpace = ODSServerProperties.getTableSpace(m_strODSSchema, "FLAG");
        String strIndexSpace = ODSServerProperties.getIndexSpace(m_strODSSchema, "FLAG");
        String strIndexSQL = " CREATE UNIQUE INDEX " + strTableName + "_PK ON " + strTableName +
            "(ENTITYID, ENTITYTYPE, NLSID, ATTRIBUTECODE, FLAGCODE)";

        Statement ddlstmt = null;

        try {

            strTableSpace = (strTableSpace.equals("DEFAULT") ? m_strTableSpace : strTableSpace);
            strIndexSpace = (strIndexSpace.equals("DEFAULT") ? m_strIndexSpace : strIndexSpace);
            strTableSQL = strTableSQL + (strTableSpace.equals("DEFAULT") ? "" : " IN " + strTableSpace) +
                (strIndexSpace.equals("DEFAULT") ? "" : " INDEX IN " + strIndexSpace);

            ddlstmt = m_conODS.createStatement();
            try {
                D.ebug(D.EBUG_INFO, "resetMultiFlagTable:dropping Table:" + strTableName);
                ddlstmt.executeUpdate(strDropSQL);
                m_conODS.commit();
            }
            catch (SQLException ex) {
                D.ebug(D.EBUG_INFO, "resetMultiFlagTable:Skipping Table Drop:" + strTableName + ":" + ex.getMessage());
            }

            D.ebug(D.EBUG_INFO, "resetMultiFlagTable:creating table:" + strTableSQL);
            ddlstmt.executeUpdate(strTableSQL);

            // Now the index
            D.ebug(D.EBUG_INFO, "resetMultiFlagTable:creating index:" + strIndexSQL);
            ddlstmt.executeUpdate(strIndexSQL);
        }
        finally {
            if (ddlstmt != null) {
                ddlstmt.close();
                ddlstmt = null;
                m_conODS.commit();
            }
        }
    }

    /**
     *  Drops all the tables in the database schema specified
     *
     *@exception  SQLException
     */
    protected void dropAllTables() throws SQLException {

        //
        // Here is a hashtable of protected tables that should not be dropped
        //
        Hashtable hsh1 = new Hashtable();

        String strTableListSQL = "Select tabname from syscat.tables where tabschema = '" + m_strODSSchema + "' AND TYPE = 'T'";

        Statement stat = null;
        ResultSet rs = null;
        Statement ddlstmt = null;
        ReturnDataResultSet rdrs = null;

        //
        //  Base tables that do not need to be dropped
        //
        hsh1.put("METAFLAGTABLE", "Y");
        hsh1.put("RESTARTABLE", "Y");
        hsh1.put("ATTRUNITS", "Y");
        hsh1.put("PSGCTNLS", "Y");
        hsh1.put("PSGNLS", "Y");
        hsh1.put("PROD_RATE_CARD", "Y");
        hsh1.put("RATE_CARD", "Y");
        hsh1.put("PROD_CHAR_VALUE", "Y");
        hsh1.put("CCECTRY", "Y");
        hsh1.put("PROJSERBRANDFAM", "Y");
        hsh1.put("CHAR_VALUE", "Y");
        hsh1.put("ELEMENT_REPORT", "Y");
        hsh1.put("IFMLOCK", "Y");
        hsh1.put("PRODUCT_PRICE", "Y");
        hsh1.put("PROD_PRODUCT_PRICE", "Y");
        hsh1.put("RATECARDDESC", "Y");
        hsh1.put("RATECARD", "Y");
        hsh1.put("GAMAP", "Y");
        hsh1.put("PRODUCT", "Y");
        hsh1.put("PRODUCTDETAIL", "Y");
        hsh1.put("SECURITY", "Y");

        //
        // Summary tables that do not need to be dropped
        //
        hsh1.put("PRDMESSAGE", "Y");
        hsh1.put("PRODUCTCOUNTRY", "Y");
        hsh1.put("ATTRWITHUNIT", "Y");
        hsh1.put("CATIMAGE", "Y");
        hsh1.put("COUNTRYREGION", "Y");
        hsh1.put("CBUNSPSC", "Y");
        hsh1.put("PSGSERVICETYPE", "Y");
        hsh1.put("AUDIENCE", "Y");
        hsh1.put("COMPAT", "Y");
        hsh1.put("SBBALLDATES", "Y");
        hsh1.put("PRDIMAGE", "Y");
        hsh1.put("CBPARTS", "Y");
        hsh1.put("PRDROOT", "Y");
        hsh1.put("PACKAGE1", "Y");

        D.ebug(D.EBUG_INFO, "dropAllTables:Generating List:" + strTableListSQL);

        try {
            stat = m_conODS.createStatement();
            rs = stat.executeQuery(strTableListSQL);
            rdrs = new ReturnDataResultSet(rs);
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stat != null) {
                stat.close();
                stat = null;
            }
            m_conODS.commit();
        }

        // O.K.  Lets get all the known tables for a schema and lets remove them!

        try {
            ddlstmt = m_conODS.createStatement();
            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strTableName = rdrs.getColumn(ii, 0);
                String strDropSQL = "DROP TABLE " + m_strODSSchema + "." + strTableName;
                // Let us drop the table first
                try {
                    D.ebug(D.EBUG_INFO, "dropAllTables:dropping Table:" + strDropSQL);
                    if (hsh1.containsKey(strTableName)) {
                        D.ebug(D.EBUG_INFO, "dropAllTables:Skipping Table Drop:" + strTableName);
                    } else {
                        ddlstmt.executeUpdate(strDropSQL);
                    }
                    m_conODS.commit();
                }
                catch (SQLException ex) {
                    D.ebug(D.EBUG_INFO, "dropAllTables:Skipping Table Drop:" + strDropSQL + ":" + ex.getMessage());
                }
            }
        }
        finally {
            if (ddlstmt != null) {
                ddlstmt.close();
                ddlstmt = null;
            }
        }
    }

    /**
     *  This will drop and recreate the ods table
     *
     *@param  _eg               Entity Group of the entity to reset
     *@exception  SQLException
     */
    public void resetODSTable(EntityGroup _eg) throws SQLException {

        Statement ddlstmt = null;
        String strEntityType = _eg.getEntityType();
        String strTableName = m_strODSSchema + "." + strEntityType;

        String strDropSQL = "DROP TABLE " + strTableName;
        String strIndexSQL = " CREATE UNIQUE INDEX " + m_strODSSchema + "." +
            (strEntityType.length() > INDEX_NAME_LENGTH ? strEntityType.substring(0, INDEX_NAME_LENGTH) : strEntityType) +
            "_PK ON " + strTableName + "(ENTITYID, NLSID, VALFROM)";
        String strIndexR1SQL = " CREATE UNIQUE INDEX " + m_strODSSchema + "." +
            (strEntityType.length() > INDEX_NAME_LENGTH ? strEntityType.substring(0, INDEX_NAME_LENGTH) : strEntityType) +
            "_IX1 ON " + strTableName + "(ID1,ID2,EntityID,NLSID)";
        String strIndexR2SQL = " CREATE UNIQUE INDEX " + m_strODSSchema + "." +
            (strEntityType.length() > INDEX_NAME_LENGTH ? strEntityType.substring(0, INDEX_NAME_LENGTH) : strEntityType) +
            "_IX2 ON " + strTableName + "(ID2,ID1,EntityID,NLSID)";
        String strAlterSQL = "";

        String strTableSQL = (_eg.isRelator() ?
            "CREATE TABLE " + strTableName + "(ENTITYID INT NOT NULL, NLSID INT NOT NULL, ID1 INT, ID2 INT, VALFROM TIMESTAMP NOT NULL, VALTO TIMESTAMP NOT NULL " :
                              "CREATE TABLE " + strTableName +
                              "(ENTITYID INT NOT NULL, NLSID INT NOT NULL, VALFROM TIMESTAMP NOT NULL, VALTO TIMESTAMP NOT NULL ");

        String strTableSpace = ODSServerProperties.getTableSpace(m_strODSSchema, strEntityType);
        String strIndexSpace = ODSServerProperties.getIndexSpace(m_strODSSchema, strEntityType);

        String strFKey = ODSServerProperties.getFKeyMap(m_strODSSchema, strEntityType);
        boolean bFKEY = (!strFKey.equals("n"));
        boolean bPubFlag = ODSServerProperties.hasPublishFlag(m_strODSSchema, strEntityType);

        // Let us drop the table first
        try {
            ddlstmt = m_conODS.createStatement();
            D.ebug(D.EBUG_INFO, "resetODSTable:dropping Table:" + strTableName);
            ddlstmt.executeUpdate(strDropSQL);
        }
        catch (SQLException ex) {
            D.ebug(D.EBUG_INFO, "resetODSTable:Skipping Table Drop:" + strTableName + ":" + ex.getMessage());
        }
        finally {
            if (ddlstmt != null) {
                ddlstmt.close();
                ddlstmt = null;
            }
            m_conODS.commit();
        }

        if (bFKEY) {
            D.ebug(D.EBUG_INFO, "resetODSTable:Adding FKey to table:" + strTableName + ", " + strFKey);
            strTableSQL = strTableSQL + ", " + strFKey + " INT ";
        }

        // Lets look at the publish flag property

        if (bPubFlag) {
            D.ebug(D.EBUG_INFO, "resetODSTable:Adding PublishFlag to table:" + strTableName);
            strTableSQL = strTableSQL + ", PUBLISHFLAG CHAR(1) NOT NULL WITH DEFAULT 'Y' ";
        }

        //
        // ALL Type 'A' and 'B' attributes are skipped
        // A = ODS never needs
        // B = will be gotten from its own table.
        //
        // First go after all non lob attributes
        for (int ii = 0; ii < _eg.getMetaAttributeCount(); ii++) {
            EANMetaAttribute ma = _eg.getMetaAttribute(ii);
            if (includeColumn(ma)) {
                if (! (ma.getAttributeType().equals("F") || ma.getAttributeType().equals("X") || ma.getAttributeType().equals("L"))) {
                    strAlterSQL = strAlterSQL + ", " + ma.getAttributeCode() + " " + getDBFieldType(ma);
                    if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S")) {
                        strAlterSQL = strAlterSQL + ", " + ma.getAttributeCode() + "_FC" + " CHAR(16)";
                    }
                }
            }
        }

        // now go after lob attributes (X,F,L)
        // First go after all non lob attributes
        for (int ii = 0; ii < _eg.getMetaAttributeCount(); ii++) {
            EANMetaAttribute ma = _eg.getMetaAttribute(ii);
            if (includeColumn(ma)) {
                if (ma.getAttributeType().equals("F") || ma.getAttributeType().equals("X") || ma.getAttributeType().equals("L")) {
                    strAlterSQL = strAlterSQL + ", " + ma.getAttributeCode() + " " + getDBFieldType(ma);
                }
            }
        }

        // O.K. Put it all together and go for the create base
        strTableSQL = strTableSQL + strAlterSQL + ")";

        // Now .. Lets make the tableSpace, and Ispace Thing
        strTableSpace = (strTableSpace.equals("DEFAULT") ? m_strTableSpace : strTableSpace);
        strIndexSpace = (strIndexSpace.equals("DEFAULT") ? m_strIndexSpace : strIndexSpace);
        strTableSQL = strTableSQL + (strTableSpace.equals("DEFAULT") ? "" : " IN " + strTableSpace) +
            (strIndexSpace.equals("DEFAULT") ? "" : " INDEX IN " + strIndexSpace);

        try {
            ddlstmt = m_conODS.createStatement();
            D.ebug(D.EBUG_INFO, "resetODSTable:creating table:" + strTableSQL);
            ddlstmt.executeUpdate(strTableSQL);

            // Now the index
            D.ebug(D.EBUG_INFO, "resetODSTable:creating index:" + strIndexSQL);
            ddlstmt.executeUpdate(strIndexSQL);

            if (_eg.isRelator()) {
                ddlstmt.executeUpdate(strIndexR1SQL);
                ddlstmt.executeUpdate(strIndexR2SQL);
            }

            if (bFKEY) {
                String strIndexFKSQL = " CREATE UNIQUE INDEX " + m_strODSSchema + "." +
                    (strEntityType.length() > INDEX_NAME_LENGTH ? strEntityType.substring(0, INDEX_NAME_LENGTH) : strEntityType) +
                    "_FK ON " + strTableName + "(" + strFKey + ", ENTITYID, NLSID, VALFROM)";

                ddlstmt.executeUpdate(strIndexFKSQL);
            }
        }
        finally {
            if (ddlstmt != null) {
                ddlstmt.close();
                ddlstmt = null;
            }
            m_conODS.commit();
        }
    }

    /**
     *  Gets the ODS Column substitute of the Attribute given a EANMetaAttribute
     *  object
     *
     *@param  _ma  The EANMetaAttribute
     *@return      The ODS Column Type
     */
    protected String getDBFieldType(EANMetaAttribute _ma) {

        //
        //  Here we are dealing with a text variant
        //

        String strODSLen = (_ma.getOdsLength() > 0 ? "0" + _ma.getOdsLength() : "0" + ODSServerProperties.getVarCharColumnLength());

        if (_ma.getAttributeType().equals("T") || _ma.getAttributeType().equals("I")) {
            if (_ma.isDate()) {
                return "DATE";
            } else if (_ma.isTime()) {
                return "TIME";
            } else if (_ma.isInteger() && !_ma.isAlpha()) {
                return "INT";
            } else if (_ma.isDecimal() || _ma.isNumeric()) {
                if (_ma.getOdsLength() > 10) {
                    m_dbPDH.debug(D.EBUG_DETAIL,
                        "getDBFieldType: *** Curiously large odslength for a number," + _ma.getAttributeCode() + ":" + strODSLen +
                        ".  Setting to " + strODSLen + " instead of 10");
                    return "VARCHAR(" + strODSLen + ")";
                } else {
                    return "CHAR(10)";
                }
            } else {
                return "VARCHAR(" + strODSLen + ")";
            }
        } else if (_ma.getAttributeType().equals("U")) {
            return "VARCHAR(" + strODSLen + ")";
        } else if (_ma.getAttributeType().equals("S")) {
            return "VARCHAR(" + strODSLen + ")";
        } else if (_ma.getAttributeType().equals("F")) {
            return "LONG VARCHAR";
        } else if (_ma.getAttributeType().equals("L")) {
            return "LONG VARCHAR";
        } else if (_ma.getAttributeType().equals("X")) {
            return "LONG VARCHAR";
        } else if (_ma.getAttributeType().equals("B")) {
            return "BLOB (10M)";
        } else {
            return "VARCHAR(25)";
        }
    }

    /**
     *  This will populate the ODS table with data from the corresponding PDH
     *  entity
     *
     *@param  _eg                      EntityGroup object for the ODS Table
     *@exception  SQLException
     *@exception  MiddlewareException
     */
    protected void populateODSTable(EntityGroup _eg) throws SQLException, MiddlewareException {

        int iSessionID = m_dbPDH.getNewSessionID();
        int iStartID = 0;
        int iEndID = 0;
        int iMaxID = 0;
        int iEntityCount = 0;
        boolean bLoop = false;
        EANList elMulti = new EANList();
        EANList elTrans = new EANList();
        String strEntityType = _eg.getEntityType();
        String strEntity1Type = null;
        String strEntity2Type = null;

        String strEnterprise = m_prof.getEnterprise();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        ReturnStatus returnStatus = new ReturnStatus( -1);

        EntityGroup eg1 = null;
        EntityGroup eg2 = null;

        boolean bATTREL = false;
        boolean bFKEY = false;

        String strInsertHEADSQL = null;
        String strInsertFIELDSSQL = null;
        String strInsertMARKERSQL = null;

        String strDeleteFLAGSQL = null;
        String strDeletePRODATTMAPSQL = null;
        String strInsertFLAGSQL = null;
        String strInsertPRODATTMAPSQL = null;
        String strUpdateFKey = null;
        String strATTRSQL = null;

        PreparedStatement psFlag = null;
        PreparedStatement psTrans = null;
        PreparedStatement psFlagDel = null;
        PreparedStatement psProdAttrDel = null;
        PreparedStatement psFKey = null;
        PreparedStatement psATTR = null;

        System.gc();

        // Set up the possibility for relator management ...

        // O.K.  lets strip this information from some international flags..
        // Some flags can only be us. english only
        setEnglishOnlyFlags(_eg);

        try {
            //Now get the max rows we have to retrieve for this entity
            m_dbPDH.debug(D.EBUG_DETAIL, "gbl9001:params:" + strEnterprise + ":" + strEntityType + ":" + m_strODSSchema);
            rs = m_dbPDH.callGBL9001(returnStatus, strEnterprise, strEntityType, m_strODSSchema);
            if (rs.next()) {
                iMaxID = rs.getInt(1);
                iStartID = rs.getInt(2);
                iEntityCount = rs.getInt(3);
                m_dbPDH.debug(D.EBUG_DETAIL, "gbl9001:answer: Max:" + iMaxID + ":Start:" + iStartID + ":Rows:" + iEntityCount);
            }
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            m_dbPDH.commit();
            m_dbPDH.freeStatement();
            m_dbPDH.isPending();
        }

        // Lets do some fancy Calculations here..
        //iStartID = iEndID + 1;
        iEndID = (iEndID + m_iChuckSize > iMaxID ? iMaxID : iEndID + m_iChuckSize);
        bLoop = iStartID <= iMaxID;

        if (_eg.isRelator()) {
            strEntity1Type = _eg.getEntity1Type();
            strEntity2Type = _eg.getEntity2Type();
        }

        bATTREL = _eg.isRelator() && ODSServerProperties.isProdAttributeRelator(m_strODSSchema, strEntityType);
        bFKEY = ODSServerProperties.isFKeyMapRelator(m_strODSSchema, strEntityType) &&
            !ODSServerProperties.getFKeyMap(m_strODSSchema, strEntity2Type).equals("n");

        strDeleteFLAGSQL = "DELETE FROM " + m_strODSSchema + ".FLAG " + " WHERE EntityType = ? AND EntityID = ?";
        strDeletePRODATTMAPSQL = "DELETE FROM " + m_strODSSchema + ".PRODATTRIBUTE " + " WHERE EntityType = ? AND EntityID = ?";
        strInsertFLAGSQL = "INSERT  INTO " + m_strODSSchema + ".FLAG " +
            " (VALFROM,ENTITYTYPE,ENTITYID, NLSID, ATTRIBUTECODE, FLAGCODE,SFVALUE,FLAGDESCRIPTION) " + " VALUES (?,?,?,?,?,?,?,?)";
        strInsertPRODATTMAPSQL = "INSERT  INTO " + m_strODSSchema + ".PRODATTRIBUTE " +
            "(ENTITYTYPE, ENTITYID, ATTRIBUTECODE, ATTRIBUTEVALUE, NLSID, FLAGCODE, VALFROM) " + " VALUES (?,?,?,?,?,?,?)";
        strUpdateFKey = "UPDATE  " + m_strODSSchema + "." + strEntity2Type + " SET " + strEntity1Type +
            "ID = ?, valfrom = ? WHERE ENTITYID = ?";
        strATTRSQL = "INSERT INTO " + m_strODSSchema + ".PRODATTRELATOR " +
            " (ENTITY1ID,ENTITY1TYPE,ENTITY2ID,ENTITY2TYPE,VALFROM) " + " VALUES(?,?,?,?,?)";

        try {
            // Do some prepared statements here..
            psFlag = m_conODS.prepareStatement(strInsertFLAGSQL);
            psTrans = m_conODS.prepareStatement(strInsertPRODATTMAPSQL);
            psFlagDel = m_conODS.prepareStatement(strDeleteFLAGSQL);
            psProdAttrDel = m_conODS.prepareStatement(strDeletePRODATTMAPSQL);
            psFKey = m_conODS.prepareStatement(strUpdateFKey);
            psATTR = m_conODS.prepareStatement(strATTRSQL);

            //
            // Lets see if we want to do that forgien key trick.
            //

            while (bLoop) {

                System.gc();

                if (_eg.isRelator()) {
                    eg1 = new EntityGroup(null, m_dbPDH, m_prof, strEntity1Type, "No Atts");
                    eg2 = new EntityGroup(null, m_dbPDH, m_prof, strEntity2Type, "No Atts");
                }

                try {
                    //Get the entities which will fulfill the filter criteria set in the meta for this entity
                    m_dbPDH.debug(D.EBUG_DETAIL,
                                  "gbl9000:params:" + iSessionID + ":" + strEnterprise + ":" + strEntityType + ":" + m_strODSSchema +
                                  ":" + iStartID + ":" + iEndID);
                    rs = m_dbPDH.callGBL9000(returnStatus, iSessionID, strEnterprise, strEntityType, m_strODSSchema, iStartID,
                                             iEndID);
                    rdrs = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs == null) {
                        rs.close();
                        rs = null;
                    }
                    m_dbPDH.commit();
                    m_dbPDH.freeStatement();
                    m_dbPDH.isPending();
                }

                // O.K.  Lets tuck all the entity items into the group
                for (int ii = 0; ii < rdrs.size(); ii++) {
                    int iEntity1ID = rdrs.getColumnInt(ii, 0);
                    int iEntityID = rdrs.getColumnInt(ii, 1);
                    int iEntity2ID = rdrs.getColumnInt(ii, 2);
                    String strValFrom = rdrs.getColumnDate(ii, 3);
                    String strValTo = rdrs.getColumnDate(ii, 4);
                    EntityItem ei1 = null;
                    EntityItem ei2 = null;
                    EntityItem ei = null;
                    m_dbPDH.debug(D.EBUG_SPEW,
                                  "gbl9000:answers:" + iEntity1ID + ":" + iEntityID + ":" + iEntity2ID + ":" + strValFrom + ":" +
                                  strValTo);
                    if (!_eg.containsEntityItem(strEntityType, iEntityID) &&
                        ( (_eg.isRelator() && iEntity1ID > 0 && iEntity2ID > 0) || !_eg.isRelator())) {
                        ei = new EntityItem(_eg, null, strEntityType, iEntityID);
                        _eg.putEntityItem(ei);
                    }
                    if (_eg.isRelator() && iEntity1ID > 0 && iEntity2ID > 0) {
                        ei1 = new EntityItem(eg1, null, strEntity1Type, iEntity1ID);
                        eg1.putEntityItem(ei1);
                        ei2 = new EntityItem(eg2, null, strEntity2Type, iEntity2ID);
                        eg2.putEntityItem(ei2);
                        ei.putUpLink(ei1);
                        ei.putDownLink(ei2);
                    }
                }

                // Now ... we fill out all the attributes if anything was populated
                // from above

                if (rdrs.size() > 0) {
                    popAllAttributeValues(_eg, iSessionID, false);
                }

                // Now remove all the records to clean up after yourself
                // We need a simpler way to do this

                m_dbPDH.callGBL8105(returnStatus, iSessionID);
                m_dbPDH.commit();
                m_dbPDH.freeStatement();
                m_dbPDH.isPending();

                // Now.. lets build the SQL table insert statement

                strInsertHEADSQL = "INSERT INTO " + m_strODSSchema + "." + strEntityType;
                strInsertFIELDSSQL = (_eg.isRelator() ? " (EntityID, NLSID, ID1, ID2, VALFROM, VALTO" :
                                      " (EntityID, NLSID, VALFROM, VALTO");
                strInsertMARKERSQL = (_eg.isRelator() ? " VALUES (?, ?, ?, ?, ?, ?" : " VALUES (?, ?, ?, ?");

                // Here  we build the rest of the SQL Statement

                for (int ii = 0; ii < _eg.getEntityItemCount(); ii++) {
                    NLSItem nlsCurrent = null;
                    int[] iAllNLS = {
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

                    String strInsertFIELDS = "";
                    String strInsertMARKER = "";

                    EntityItem ei = _eg.getEntityItem(ii);

                    try {

                        D.ebug(D.EBUG_DETAIL, "ODSMethods.populateODSTable:ET:" + ei.getEntityType() + ":EID:" + ei.getEntityID());

                        // O.K.  if we are not in an INIT ALL State.. or we are in a restatewe have to remove
                        // multi flags from the flag table first .. for each entity or were we caught in the middle of a restart?
                        if (! (m_bInitAll || m_bMultiFlag) || m_bRestart) {
                            try {
                                psFlagDel.setString(1, strEntityType);
                                psFlagDel.setInt(2, ei.getEntityID());
                                D.ebug(D.EBUG_DETAIL,
                                       "ODSMethods.populateODSTable:Deleting MultiFlagValues Entity info for (" + ei.getEntityType() +
                                       ":" + ei.getEntityID() + ") " + ei.toString());
                                psFlagDel.execute();
                                psProdAttrDel.setString(1, strEntityType);
                                psProdAttrDel.setInt(2, ei.getEntityID());
                                D.ebug(D.EBUG_DETAIL,
                                       "ODSMethods.populateODSTable:Deleting Product Attribute Map Entity info for (" +
                                       ei.getEntityType() + ":" + ei.getEntityID() + ") " + ei.toString());
                                psProdAttrDel.execute();

                            }
                            catch (SQLException ex) {
                                D.ebug(D.EBUG_ERR, "**Deleting Multi Flag Entity:" + ex.getMessage());
                            }
                        }

                        // If its default mode.. then lets just get a list of inuse NLSIDs.
                        if (ODSServerProperties.defNLSMode(m_strODSSchema, _eg.getEntityType())) {
                            iAllNLS = ei.getInUseNLSIDs();
                            // If there are no languages default to english
                            if (iAllNLS.length == 0) {
                                iAllNLS = new int[1];
                                iAllNLS[0] = 1;
                            }
                        } else if (ODSServerProperties.geoNLSMode(m_strODSSchema, _eg.getEntityType())) {
                            iAllNLS = getGeoNLSArray(ei);
                        }

                        elMulti = new EANList();
                        elTrans = new EANList();

                        //This will have to be repeated for all the languages enabled for the entity TBD
                        for (int il = 0; il < iAllNLS.length; il++) {

                            String strInsertSQL = null;
                            PreparedStatement psInsert = null;

                            int iz = 0;

                            //Set the NLSid to be read in the profile so that we retrieve the correct translations
                            nlsCurrent = new NLSItem(iAllNLS[il], "Current translation");
                            m_prof.setReadLanguage(nlsCurrent);
                            D.ebug(D.EBUG_INFO,
                                   "Setting readLanguage for Entity :" + ei.getEntityType() + ":" + ei.getEntityID() + ": to :" +
                                   iAllNLS[il]);

                            strInsertFIELDS = "";
                            strInsertMARKER = "";
                            for (int iy = 0; iy < ei.getAttributeCount(); iy++) {
                                EANAttribute att = ei.getAttribute(iy);
                                EANMetaAttribute ma = att.getMetaAttribute();
                                if (isDerivedEntityID(ma)) {
                                    continue;
                                }
                                if (att.toString().length() == 0) {
                                    D.ebug(D.EBUG_INFO,
                                           att.getAttributeCode() + "!!! ZERO LENGTH ATT FOUND AND SKIPPED. Section #1 !!!");
                                    continue;
                                }

                                if (includeColumn(ma) && att.toString().length() > 0) {
                                    D.ebug(D.EBUG_SPEW, "AC#COUNT1#:" + iy + ":" + att.getAttributeCode());
                                    strInsertFIELDS = strInsertFIELDS + ", " + ma.getAttributeCode();
                                    strInsertMARKER = strInsertMARKER + ", ?";
                                    if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S")) {
                                        strInsertFIELDS = strInsertFIELDS + ", " + ma.getAttributeCode() + "_FC";
                                        strInsertMARKER = strInsertMARKER + ", ?";
                                    }
                                }
                            }

                            strInsertSQL = strInsertHEADSQL + strInsertFIELDSSQL + strInsertFIELDS + ")" + strInsertMARKERSQL +
                                strInsertMARKER + ")";

                            D.ebug(D.EBUG_SPEW, strInsertSQL);

                            psInsert = m_conODS.prepareStatement(strInsertSQL);
                            psInsert.setInt(1, ei.getEntityID());
                            psInsert.setInt(2, iAllNLS[il]); // Setting the ODS NLS here

                            iz = 0;
                            if (_eg.isRelator()) {
                                EntityItem eip = (EntityItem) ei.getUpLink(0);
                                EntityItem eic = (EntityItem) ei.getDownLink(0);

                                psInsert.setInt(3, (eip == null ? -1 : eip.getEntityID()));
                                psInsert.setInt(4, (eic == null ? -1 : eic.getEntityID()));

                                psInsert.setString(5, m_strNow);
                                psInsert.setString(6, m_strForever);
                                iz = 7;
                            } else {
                                psInsert.setString(3, m_strNow);
                                psInsert.setString(4, m_strForever);
                                iz = 5;
                            }

                            for (int iy = 0; iy < ei.getAttributeCount(); iy++) {
                                EANAttribute att = ei.getAttribute(iy);
                                EANMetaAttribute ma = att.getMetaAttribute();
                                if (att.toString().length() == 0) {
                                    D.ebug(D.EBUG_INFO,
                                           att.getAttributeCode() + "!!! ZERO LENGTH ATT FOUND AND SKIPPED Section #2 !!!");
                                    continue;
                                }
                                if (isDerivedEntityID(ma)) {
                                    continue;
                                }

                                // If its a column we need..
                                // and its a multi value flag that is needed and is not a SF Flag
                                // lets collect some flag info right now!
                                // and some s and feed attributes
                                if (il == 0) {
                                    if (ma.getAttributeType().equals("F") &&
                                        !ODSServerProperties.isProdAttribute(m_strODSSchema, ma.getAttributeCode())) {
                                        elMulti.put(att);
                                    }
                                }

                                if (ODSServerProperties.isProdAttribute(m_strODSSchema, ma.getAttributeCode())) {

                                    D.ebug(D.EBUG_INFO,
                                        "->\tProdAttribute:Found:" + ei.getEntityType() + ":" + ei.getEntityID() + ":" +
                                        ma.getAttributeCode() + ":" + ma.getAttributeType() + ":" + nlsCurrent);
                                    if (!ma.getAttributeType().equals("F")) {
                                        psTrans.setString(1, ei.getEntityType());
                                        psTrans.setInt(2, ei.getEntityID());
                                        psTrans.setString(3, att.getAttributeCode());
                                        psTrans.setString(4, getFirst254Char(att.getAttributeCode(), att.toString()));
                                        psTrans.setInt(5, iAllNLS[il]);
                                        if (att instanceof EANFlagAttribute) {
                                            EANFlagAttribute fa = (EANFlagAttribute) att;
                                            psTrans.setString(6, fa.getFirstActiveFlagCode());
                                        } else {
                                            psTrans.setString(6, "--");
                                        }
                                        psTrans.setString(7, m_strNow);
                                        psTrans.execute();

                                    } else {
                                        EANFlagAttribute fa = (EANFlagAttribute) att;
                                        EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) ma;
                                        // Loop through and collect all turned on values.
                                        Iterator it = fa.getPDHHashtable().keySet().iterator();

                                        psFlag.setString(1, m_strNow);
                                        psFlag.setString(2, ei.getEntityType());
                                        psFlag.setInt(3, ei.getEntityID());
                                        psFlag.setInt(4, iAllNLS[il]);
                                        psFlag.setString(5, fa.getAttributeCode());

                                        while (it.hasNext()) {
                                            String strFlagCode = (String) it.next();
                                            MetaFlag mf = mfa.getMetaFlag(strFlagCode);
                                            psFlag.setString(6, strFlagCode);
                                            psFlag.setInt(7, 1);
                                            psFlag.setString(8, mf.getLongDescription());
                                            psFlag.execute();
                                        }
                                    }
                                }

                                // Now lets build the table columns for the table row update

                                if (includeColumn(ma) && att.toString().length() > 0) {

                                    D.ebug(D.EBUG_SPEW,
                                        "AC#COUNT2#:" + iy + ":" + att.getAttributeCode() + ":" + ma.getAttributeType() + ":IN:" +
                                        ma.isInteger() + ":DT:" + ma.isDate() + ":AL:" + ma.isAlpha() + ":" + att.toString());

                                    // Temp use string to set the date and let it be converted  by DB2 to the proper date
                                    if (ma.isDate() && ma.getAttributeType().equals("T")) {
                                        try {
                                            if (att.toString().trim().toUpperCase().equals("OPEN") ||
                                                att.toString().trim().equals("Open")) {
                                                D.ebug(D.EBUG_ERR,
                                                    "**DATE IN " + ma.getAttributeCode() + " -- Set to OPEN in PDH, setting to null in ODS");
                                                psInsert.setString(iz, null);
                                            } else {
                                                psInsert.setString(iz, getODSString(att));
                                                //uDate = m_df.parse(att.toString());
                                                //psInsert.setDate(iz,uDate.getTime());
                                                //psInsert.setDate(iz,new java.sql.Date(uDate.getTime() ));
                                            }
                                        }
                                        catch (Exception dx) {
                                            D.ebug(D.EBUG_ERR,
                                                "**#0 PARAMETER SET ERROR:  Date format Exception for (" + ei.getEntityType() + ":" +
                                                ei.getEntityID() + ":" + att.getAttributeCode() + ") - Value (" + att.toString() +
                                                ").  Using null." + ":" + dx.getMessage());
                                            psInsert.setString(iz, null);
                                        }
                                    } else if (ma.isInteger() && !ma.isAlpha() && ma.getAttributeType().equals("T")) {
                                        try {
                                            psInsert.setInt(iz, Integer.valueOf(att.toString()).intValue());
                                        }
                                        catch (NumberFormatException nx) {
                                            D.ebug(D.EBUG_ERR,
                                                "**#1 PARAMETER SET ERROR:  Number format Exception for (" + ei.getEntityType() +
                                                ":" + ei.getEntityID() + ":" + att.getAttributeCode() + ") - Value (" +
                                                att.toString() + ").  Using zero(0) instead." + ":" + nx.getMessage());
                                            psInsert.setInt(iz, 0);
                                        }
                                    } else {
                                        try {
                                            psInsert.setString(iz, getODSString(att));
                                        }
                                        catch (SQLException nx) {
                                            D.ebug(D.EBUG_ERR,
                                                "**#2 BASIC TEXT PARAMETER SET ERROR: SQLError for (" + ei.getEntityType() + ":" +
                                                ei.getEntityID() + ":" + att.getAttributeCode() + ") - Value (" + att.toString() +
                                                ").  Using null instead." + ":" + nx.getMessage());
                                            psInsert.setString(iz, null);
                                        }
                                    }
                                    iz++;

                                    // Lets look for the flag code option
                                    //
                                    if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S")) {
                                        EANFlagAttribute fa = (EANFlagAttribute) att;
                                        try {
                                            psInsert.setString(iz, fa.getFirstActiveFlagCode());
                                        }
                                        catch (SQLException nx) {
                                            D.ebug(D.EBUG_ERR,
                                                "**#3 PARAMETER SET ERROR: SQLError for (" + ei.getEntityType() + ":" +
                                                ei.getEntityID() + ":" + att.getAttributeCode() + ") - Value (" + att.toString() +
                                                ").  Using null instead." + ":" + nx.getMessage());
                                            psInsert.setString(iz, null);
                                        }
                                        iz++;
                                    }

                                }
                            }

                            D.ebug(D.EBUG_INFO,
                                   "Inserting Entity(" + ei.getEntityType() + ":" + ei.getEntityID() + ") " + ei.toString());

                            try {
                                psInsert.execute();
                            }
                            finally {
                                psInsert.close();
                                psInsert = null;
                            }
                        }

                        //
                        // set it back to the first language in list for basic processing
                        //

                        nlsCurrent = new NLSItem(iAllNLS[0], "Current translation");
                        m_prof.setReadLanguage(nlsCurrent);

                        //
                        // This is normal flag processing that is not considered one of the S&F attributes
                        // this nlsid = 0
                        if (elMulti.size() > 0) {
                            psFlag.setString(1, m_strNow);
                            psFlag.setString(2, strEntityType);
                            psFlag.setInt(3, ei.getEntityID());
                            psFlag.setInt(4, iAllNLS[0]);
                            D.ebug(D.EBUG_INFO, "->\tMultiFlags:Starting:" + ei.getEntityType() + ":" + ei.getEntityID());
                            for (int iy = 0; iy < elMulti.size(); iy++) {
                                EANFlagAttribute fa = (EANFlagAttribute) elMulti.getAt(iy);
                                EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) fa.getMetaAttribute();
                                Iterator it = fa.getPDHHashtable().keySet().iterator();
                                String strAttributeCode = fa.getAttributeCode();

                                psFlag.setString(5, strAttributeCode);

                                while (it.hasNext()) {
                                    String strFlagCode = (String) it.next();
                                    MetaFlag mf = mfa.getMetaFlag(strFlagCode);
                                    psFlag.setString(6, strFlagCode);
                                    psFlag.setInt(7, 0);
                                    psFlag.setString(8, mf.getLongDescription());
                                    psFlag.execute();
                                }
                            }
                            D.ebug(D.EBUG_INFO, "->\tMultiFlags:Finishing:" + ei.getEntityType() + ":" + ei.getEntityID());

                        }

                        // Lets take care of the fkey here
                        if (bFKEY) {

                            int iID1 = ( (EntityItem) ei.getUpLink(0)).getEntityID();
                            int iID2 = ( (EntityItem) ei.getDownLink(0)).getEntityID();

                            psFKey.setInt(1, iID1);
                            psFKey.setString(2, m_strNow);
                            psFKey.setInt(3, iID2);
                            D.ebug(D.EBUG_INFO,
                                   "->\tFKEY:Starting:" + ei.getEntityType() + ":" + ei.getEntityID() + ":" + iID1 + " --> " + iID2);
                            psFKey.execute();
                            D.ebug(D.EBUG_INFO,
                                   "->\tFKEY:Finishing:" + ei.getEntityType() + ":" + ei.getEntityID() + ":" + iID1 + " --> " +
                                   iID2);
                        }

                        // if this was a relator table.. lets see if it qualifies for the PRODATTRELATOR INSERT!
                        //
                        if (bATTREL) {

                            int iEntity1ID = ( (EntityItem) ei.getUpLink(0)).getEntityID();
                            int iEntity2ID = ( (EntityItem) ei.getDownLink(0)).getEntityID();

                            strEntity1Type = _eg.getEntity1Type();
                            strEntity2Type = _eg.getEntity2Type();

                            psATTR.setInt(1, iEntity1ID);
                            psATTR.setString(2, strEntity1Type);
                            psATTR.setInt(3, iEntity2ID);
                            psATTR.setString(4, strEntity2Type);
                            psATTR.setString(5, m_strNow);
                            D.ebug(D.EBUG_INFO,
                                "->\tProdAttRelator:Starting:" + ei.getEntityType() + ":" + ei.getEntityID() + ":E1:" +
                                strEntity1Type + "-" + iEntity1ID + ":E2" + strEntity2Type + "-" + iEntity2ID);
                            try {
                                psATTR.execute();
                            }
                            catch (SQLException x) {
                                D.ebug(D.EBUG_INFO, "->\tProdAttRelator:Error:" + x.getMessage());
                            }
                            D.ebug(D.EBUG_INFO,
                                "->\tProdAttRelator:Finishing:" + ei.getEntityType() + ":" + ei.getEntityID() + ":E1:" +
                                strEntity1Type + "-" + iEntity1ID + ":E2" + strEntity2Type + "-" + iEntity2ID);

                        }

                    }
                    catch (SQLException ex) {
                        String strErr = "";
                        D.ebug(D.EBUG_ERR,
                               "** INSERTION ERROR *** Skipping Entity (" + ei.getEntityType() + ":" + ei.getEntityID() + ") Error is: " +
                               ex.getMessage());
                        for (int ierr = 0; ierr < ei.getAttributeCount(); ierr++) {
                            EANAttribute eanAtt = ei.getAttribute(ierr);
                            strErr = strErr + eanAtt.getAttributeCode();
                            strErr = strErr + ":" + eanAtt.toString() + NEW_LINE;
                        }
                        D.ebug(D.EBUG_ERR, "Values are ******:" + NEW_LINE + strErr);
                    }
                }

                m_conODS.commit();
                _eg.resetEntityItem();

                if (eg1 != null) {
                    eg1.resetEntityItem();
                }
                if (eg2 != null) {
                    eg2.resetEntityItem();
                }
                System.gc();

                // O.k. Lets bump up the intervals
                iStartID = iEndID + 1;
                iEndID = (iEndID + m_iChuckSize > iMaxID ? iMaxID : iEndID + m_iChuckSize);
                bLoop = iStartID <= iMaxID;

            }
        }
        finally {
            // Close the stuff out
            psFlag.close();
            psFlag = null;
            psTrans.close();
            psTrans = null;
            psFlagDel.close();
            psFlagDel = null;
            psProdAttrDel.close();
            psProdAttrDel = null;
            psFKey.close();
            psFKey = null;
            psATTR.close();
            psATTR = null;
        }
    }

    /**
     *  Repopulates all the Entities and its attributes from the rows retrieved from the database for the entity group
     *
     *@param  _eg                      EntityGroup object
     *@param  _iSessionID              Session Id
     *@param  _bPullExpiredAtts        SP switch (Do we only want only VALID attributes, or do we pull all - i.e. true means pick up 'turned-off' attributes)
     *@exception  SQLException
     *@exception  MiddlewareException
     */
    private void popAllAttributeValues(EntityGroup _eg, int _iSessionID, boolean _bPullExpiredAtts) throws SQLException,
        MiddlewareException {

        ReturnStatus returnStatus = new ReturnStatus();
        ResultSet rs = null;

        String strEnterprise = m_prof.getEnterprise();
        String strValOn = m_prof.getValOn();
        String strEffOn = m_prof.getEffOn();

        // Lets set the rules to lax here
        m_df.setLenient(true);

        try {
            if (_bPullExpiredAtts) {
                rs = m_dbPDH.callGBL9006(returnStatus, strEnterprise, _iSessionID, m_strLastRun, m_strNow);
            } else {
                rs = m_dbPDH.callGBL9002(returnStatus, strEnterprise, _iSessionID, strValOn, strEffOn);
            }

            while (rs.next()) {

                EANAttribute att = null;
                TextAttribute ta = null;
                SingleFlagAttribute sfa = null;
                StatusAttribute sa = null;
                MultiFlagAttribute mfa = null;
                TaskAttribute tska = null;
                LongTextAttribute lta = null;
                XMLAttribute xa = null;
                BlobAttribute ba = null;

                String str1 = rs.getString(1).trim();
                int i1 = rs.getInt(2);
                String str2 = rs.getString(3).trim();
                int i2 = rs.getInt(4);
                String str3 = rs.getString(5).trim();
                String str4 = rs.getString(6).trim();
                String str5 = rs.getString(7).trim();

                EntityItem ei = _eg.getEntityItem(str1, i1);
                EANMetaAttribute ma = _eg.getMetaAttribute(str2);

                m_dbPDH.debug(D.EBUG_SPEW,
                              (_bPullExpiredAtts ? "gbl9006" : "gbl9002") + ":answers:" + str1 + ":" + i1 + ":" + str2 + ":" + i2 +
                              ":" + str3 + ":" + str4 + ":" + str5);

                if (ei == null) {
                    m_dbPDH.debug(D.EBUG_ERR, "** Attribute Information for a non Existiant Entity Item:" + str1 + ":" + i1 + ":");
                    continue;
                }
                if (ma == null) {
                    continue;
                }
                if (isDerivedEntityID(ma)) {
                    continue;
                }

                att = ei.getAttribute(str2);
                if (att == null) {
                    if (ma instanceof MetaTextAttribute) {
                        ta = new TextAttribute(ei, null, (MetaTextAttribute) ma);
                        ei.putAttribute(ta);
                    } else if (ma instanceof MetaSingleFlagAttribute) {
                        sfa = new SingleFlagAttribute(ei, null, (MetaSingleFlagAttribute) ma);
                        ei.putAttribute(sfa);
                    } else if (ma instanceof MetaMultiFlagAttribute) {
                        mfa = new MultiFlagAttribute(ei, null, (MetaMultiFlagAttribute) ma);
                        ei.putAttribute(mfa);
                    } else if (ma instanceof MetaStatusAttribute) {
                        sa = new StatusAttribute(ei, null, (MetaStatusAttribute) ma);
                        ei.putAttribute(sa);
                    } else if (ma instanceof MetaTaskAttribute) {
                        tska = new TaskAttribute(ei, null, (MetaTaskAttribute) ma);
                        ei.putAttribute(tska);
                    } else if (ma instanceof MetaLongTextAttribute) {
                        lta = new LongTextAttribute(ei, null, (MetaLongTextAttribute) ma);
                        ei.putAttribute(lta);
                    } else if (ma instanceof MetaXMLAttribute) {
                        xa = new XMLAttribute(ei, null, (MetaXMLAttribute) ma);
                        ei.putAttribute(xa);
                    } else if (ma instanceof MetaBlobAttribute) {
                        ba = new BlobAttribute(ei, null, (MetaBlobAttribute) ma);
                        ei.putAttribute(ba);
                    }
                } else {
                    if (ma instanceof MetaTextAttribute) {
                        ta = (TextAttribute) att;
                        ei.putAttribute(ta);
                    } else if (ma instanceof MetaSingleFlagAttribute) {
                        sfa = (SingleFlagAttribute) att;
                        ei.putAttribute(sfa);
                    } else if (ma instanceof MetaMultiFlagAttribute) {
                        mfa = (MultiFlagAttribute) att;
                        ei.putAttribute(mfa);
                    } else if (ma instanceof MetaStatusAttribute) {
                        sa = (StatusAttribute) att;
                        ei.putAttribute(sa);
                    } else if (ma instanceof MetaTaskAttribute) {
                        tska = (TaskAttribute) att;
                        ei.putAttribute(tska);
                    } else if (ma instanceof MetaLongTextAttribute) {
                        lta = (LongTextAttribute) att;
                        ei.putAttribute(lta);
                    } else if (ma instanceof MetaXMLAttribute) {
                        xa = (XMLAttribute) att;
                        ei.putAttribute(xa);
                    } else if (ma instanceof MetaBlobAttribute) {
                        ba = (BlobAttribute) att;
                        ei.putAttribute(ba);
                    }
                }

                // OK.. drop the value into the structure

                if (ma instanceof MetaTextAttribute) {
                    ta.putPDHData(i2, str3);
                    if (_bPullExpiredAtts) {
                        ta.setValFrom(str4);
                    }
                } else if (ma instanceof MetaSingleFlagAttribute) {
                    sfa.putPDHFlag(str3);
                    if (_bPullExpiredAtts) {
                        sfa.setValFrom(str4);
                    }
                } else if (ma instanceof MetaMultiFlagAttribute) {
                    mfa.putPDHFlag(str3);
                    if (_bPullExpiredAtts) {
                        mfa.setValFrom(str4);
                    }
                } else if (ma instanceof MetaStatusAttribute) {
                    sa.putPDHFlag(str3);
                    if (_bPullExpiredAtts) {
                        sa.setValFrom(str4);
                    }
                } else if (ma instanceof MetaTaskAttribute) {
                    tska.putPDHFlag(str3);
                    if (_bPullExpiredAtts) {
                        tska.setValFrom(str4);
                    }
                } else if (ma instanceof MetaLongTextAttribute) {
                    lta.putPDHData(i2, str3);
                    if (_bPullExpiredAtts) {
                        lta.setValFrom(str4);
                    }
                } else if (ma instanceof MetaXMLAttribute) {
                    xa.putPDHData(i2, str3);
                    if (_bPullExpiredAtts) {
                        xa.setValFrom(str4);
                    }
                } else if (ma instanceof MetaBlobAttribute) {
                    ba.putPDHData(i2, new OPICMBlobValue(i2, str3, null));
                    if (_bPullExpiredAtts) {
                        ba.setValFrom(str4);
                    }
                } else {
                    m_dbPDH.debug(D.EBUG_ERR, "**Unknown Meta Type for" + str2 + ":");
                }
            }

        }
        finally {

            if (rs != null) {
                rs.close();
                rs = null;
            }
            m_dbPDH.freeStatement();
            m_dbPDH.isPending();
            m_dbPDH.commit();
        }

    }

    /**
     * This guy will take long text verbetum (as with XML)
     * it will turn MultiValueFlags into words seperated by returns.
     * it will ensure maximum length values are forced.. so they fit within the boundries of
     * the ods table.
     * making sure we trim characters.. on character boundries
     * until it fits..
     *  Gets the oDSString attribute of the ODSMethods object
     *
     *@param  _att  Description of the Parameter
     *@return       The oDSString value
     */
    private String getODSString(EANAttribute _att) {

        EANMetaAttribute ma = _att.getMetaAttribute();
        EntityItem ei = _att.getEntityItem();

        // are we the special MKT_IMG_FILENAME field that needs to be derived?
        if (ma.getAttributeCode().equals("MKT_IMG_FILENAME")) {
            EANBlobAttribute ba = (BlobAttribute) ei.getAttribute("MKTING_IMAGE");
            String strExtension = ".unk";
            String strBlobExt = (ba == null ? _att.toString() : ba.getBlobExtension());
            int i = strBlobExt.lastIndexOf('.');
            if (i > -1) {
                strExtension = strBlobExt.substring(i);
            }
            return ei.getEntityType() + ei.getEntityID() + "_MKT_IMG_FILENAME" + "_" + ei.getNLSItem().getNLSID() + strExtension;
        }

        // If there is no meta attribute.. then return the String Value.. untouched.
        if (ma == null) {
            return _att.toString();
        }

        // If Long Text, or XML.. we return as is
        if (ma.getAttributeType().equals("L")) {
            return _att.toString();
        }

        if (ma.getAttributeType().equals("X")) {
            return _att.toString();
        }

        // O.K.  If multi valued.. we remove the "* " 's so we have flag description followed by /n
        if (ma.getAttributeType().equals("F")) {
            StringTokenizer stMain = new StringTokenizer(_att.toString().replace('\n', ';'), "* ");
            String strAnswer = "";
            while (stMain.hasMoreTokens()) {
                strAnswer = strAnswer + stMain.nextToken().trim();
            }
            return strAnswer;
        }

        // O.K.  All other AttributeTypes .. here we go ..

        try {
            // Get the default max length.. or if one is available in the Meta Attribute.. use it...
            int iMaxLength = (ma.getOdsLength() > -1 ? ma.getOdsLength() : ODSServerProperties.getVarCharColumnLength());

            String strAnswer = _att.toString();
            int iLength = strAnswer.getBytes("UTF8").length;

            if (iLength > iMaxLength) {
                D.ebug(D.EBUG_WARN,
                    "**getODSString: Length Exceeds max length for (" + ei.getEntityType() + ":" + ei.getEntityID() + ":" +
                    _att.getAttributeCode() + ") - Value (" + _att.toString() + ") - len:" + iLength + ":max:" + iMaxLength +
                    ":offby:" + (iLength - iMaxLength));
                while (iLength > iMaxLength) {
                    strAnswer = strAnswer.substring(0, strAnswer.length() - 1);
                    iLength = strAnswer.getBytes("UTF8").length;
                }
            }
            return strAnswer;
        }
        catch (UnsupportedEncodingException ex) {
            D.ebug(D.EBUG_ERR,
                "**getODSString: UnsupportedEncodingException (" + ei.getEntityType() + ":" + ei.getEntityID() + ":" +
                _att.getAttributeCode() + ") - Value (" + _att.toString() + ").  Passing entire string back." + ex.getMessage());
        }

        return _att.toString();
    }

    /**
     * Gets the first 254 characters
     *
     * @param _strAttributeCode
     * @param _str
     * @return
     * @author Dave
     */
    protected String getFirst254Char(String _strAttributeCode, String _str) {
        try {

            int iMaxLength = 254;
            int iLength = _str.getBytes("UTF8").length;
            String strAnswer = _str;

            if (iLength > iMaxLength) {
                D.ebug(D.EBUG_WARN,
                       "**getFirst254Char: Length Exceeds max length for prodattriute table for " + _strAttributeCode + " (which is 254 chars) for :" +
                       _str);
                while (iLength > iMaxLength) {
                    strAnswer = strAnswer.substring(0, strAnswer.length() - 1);
                    iLength = strAnswer.getBytes("UTF8").length;
                }
            }
            _str = strAnswer;
        }
        catch (UnsupportedEncodingException ex) {
            D.ebug(D.EBUG_ERR, "**getFirst254Char: UnsupportedEncodingException.  Passing entire string back." + ex.getMessage());
        }

        return _str;

    }

    /**
     *  This method initializes the deletelog table
     */
    protected void rebuildDeleteLog() {

        ResultSet rs = null;
        Statement stmtDroptable = null;
        Statement stmtCreatetable = null;

        String ODS_ET_WIDTH = "32";
        String strEnterprise = m_prof.getEnterprise();

        String strDropStmt = "DROP TABLE " + m_strODSSchema + ".DELETELOG";
        String strCreateStmt = "CREATE TABLE " + m_strODSSchema + ".DELETELOG " + "(TYPE CHAR(32) NOT NULL," +
            "   ENTITYTYPE CHAR(" + ODS_ET_WIDTH + " ) NOT NULL, " + " ENTITYID INTEGER NOT NULL, " + " ENTITY1TYPE CHAR(" +
            ODS_ET_WIDTH + " ), " + " ENTITY1ID INTEGER ," + " ENTITY2TYPE CHAR(" + ODS_ET_WIDTH + " ), " + " ENTITY2ID INTEGER ," +
            " ATTRIBUTECODE CHAR(32) , " + " ATTRIBUTEVALUE CHAR(24) , " +
            " VALFROM TIMESTAMP NOT NULL WITH DEFAULT CURRENT TIMESTAMP, " + " PRIMARY KEY (TYPE,ENTITYTYPE,ENTITYID,VALFROM)) ";

        try {

            DatabaseMetaData dbMeta = m_conODS.getMetaData();

            String[] strMetaDB2Token = {
                "TABLE"}; // Look only for tables
            boolean bTableThere = false; // flag to indicate if the table is currently present

            try {
                rs = dbMeta.getTables(null, m_strODSSchema, "DELETELOG", strMetaDB2Token); //  Check for the table ...
                if (rs.next()) {
                    bTableThere = true; // We have found it...
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            }

            if (bTableThere) {
                //Drop the table since we found it
                m_dbPDH.debug(D.EBUG_DETAIL, "rebuildDeleteLog" + strEnterprise + ": DROPPING DELETELOG");
                try {
                    stmtDroptable = m_conODS.createStatement();
                    stmtDroptable.execute(strDropStmt);
                }
                finally {
                    stmtDroptable.close();
                }

            } else {
                m_dbPDH.debug(D.EBUG_DETAIL, "rebuildDeleteLog" + strEnterprise + ":DELETELOG DROP BYPASSED");
            }
            m_dbPDH.debug(D.EBUG_DETAIL, "rebuildDeleteLog" + strEnterprise + ":CREATING NEW DELETELOG");
            try {
                stmtCreatetable = m_conODS.createStatement();
                stmtCreatetable.execute(strCreateStmt);
            }
            finally {
                stmtCreatetable.close();
            }

        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "rebuildDeleteLog" + strEnterprise + ":ERROR:" + e.getMessage());
            System.exit(1);
        }
        finally {
            try {
                m_conODS.commit();
            }
            catch (SQLException ex) {
                m_dbPDH.debug(D.EBUG_ERR, "rebuildDeleteLog" + strEnterprise + ":COMMIT ERROR:" + ex.getMessage());
            }
        }
    } //End method rebuildDeleteLog

    /**
     *  Intializes the timetable
     */
    protected void initializeTimeTable() {

        String strDropTimeTableStmt = "DROP TABLE " + m_strODSSchema + ".TIMETABLE";
        String strCreateNewTimeTableStmt = "CREATE TABLE " + m_strODSSchema +
            ".TIMETABLE (RUNTIME TIMESTAMP NOT NULL, RUNTYPE VARCHAR(1), " + "PRIMARY KEY (RUNTIME)) ";

        ResultSet rs = null;
        Statement stmtDropTimetable = null;
        Statement stmtCreateTimetable = null;

        try {
            DatabaseMetaData dbMeta = m_conODS.getMetaData();

            String[] strMetaDB2Token = {
                "TABLE"}; // Look only for tables
            boolean bTableThere = false; // flag to indicate if the table is currently present
            try {
                rs = dbMeta.getTables(null, m_strODSSchema, "TIMETABLE", strMetaDB2Token); //  Check for the table ...
                if (rs.next()) {
                    bTableThere = true; // We have found it...
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            }

            if (bTableThere) {
                //Drop the table since we found it
                try {
                    stmtDropTimetable = m_conODS.createStatement();
                    m_dbPDH.debug(D.EBUG_INFO, "initializeTimeTable:DROPPING TIMETABLE");
                    stmtDropTimetable.execute(strDropTimeTableStmt);
                }
                finally {
                    stmtDropTimetable.close();
                }
            } else {
                m_dbPDH.debug(D.EBUG_INFO, "initializeTimeTable:TIMETABLE DROP BYPASSED");
            }

            try {
                m_dbPDH.debug(D.EBUG_INFO, "initializeTimeTable:CREATING NEW TIMETABLE");
                stmtCreateTimetable = m_conODS.createStatement();
                stmtCreateTimetable.execute(strCreateNewTimeTableStmt);
            }
            finally {
                stmtCreateTimetable.close();
            }
            m_conODS.commit();

        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "initializeTimeTable:ERROR:" + e.getMessage());
            System.exit(1);
        }

    } //End method initializeTimeTable

    /**
     *  This table is used to track if an ODS Table has been completed
     *  It can only be deleted if there contains no data.
     *  otherwise we are in restart mode and
     *  the table names in this table are a list of tables
     *  that need to be skipped
     */
    protected void initializeRestartTable() {

        String strCreateTableStmt = "CREATE TABLE " + m_strODSSchema +
            ".RESTARTABLE (RUNTIME TIMESTAMP NOT NULL, TABLENAME VARCHAR(32)) ";
        String strCountTableStmt = "SELECT COUNT(*) FROM " + m_strODSSchema + ".RESTARTABLE ";

        ResultSet rs = null;
        Statement stmt = null;
        Statement stmtCreatetable = null;

        try {
            DatabaseMetaData dbMeta = m_conODS.getMetaData();

            String[] strMetaDB2Token = {
                "TABLE"}; // Look only for tables
            boolean bTableThere = false; // flag to indicate if the table is currently present
            try {
                rs = dbMeta.getTables(null, m_strODSSchema, "RESTARTABLE", strMetaDB2Token); //  Check for the table ...
                if (rs.next()) {
                    bTableThere = true; // We have found it...
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
            }

            if (bTableThere) {
                int iCount = 0;
                try {
                    stmt = m_conODS.createStatement();
                    rs = stmt.executeQuery(strCountTableStmt);
                    if (rs.next()) {
                        iCount = rs.getInt(1);
                    }
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    stmt.close();
                }
                if (iCount > 0) {
                    m_bRestart = true;
                    m_dbPDH.debug(D.EBUG_INFO, "initializeRestartTable:RESTARTABLE  CLEARING BYPASSED -- It is not empty");
                }
            } else {
                m_dbPDH.debug(D.EBUG_INFO, "initializeRestartTable:CREATING NEW RESTARTABLE");
                try {
                    stmtCreatetable = m_conODS.createStatement();
                    stmtCreatetable.execute(strCreateTableStmt);
                }
                finally {
                    if (stmtCreatetable != null) {
                        stmtCreatetable.close();
                        stmtCreatetable = null;
                    }
                }
                m_conODS.commit();
            }
        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "initializeRestartTable:ERROR:" + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * resetProdAttTables
     *
     *  @author David Bigelow
     */
    protected void resetProdAttTables() {

        String strTableSpace = null;
        String strIndexSpace = null;
        String strDropTableStmt = null;
        String strCreateTableStmt = null;
        String strIndex1SQL = null;

        String strDropStmt2 = null;
        String strCreateStmt2 = null;
        String strIndex2SQL = null;

        ResultSet rs = null;
        Statement stmtDropTable = null;
        Statement stmtCreateTable = null;
        Statement stmtDrop2 = null;
        Statement stmtCreate2 = null;

        strTableSpace = ODSServerProperties.getTableSpace(m_strODSSchema, "PRODATTRIBUTE");
        strIndexSpace = ODSServerProperties.getIndexSpace(m_strODSSchema, "PRODATTRIBUTE");
        strTableSpace = (strTableSpace.equals("DEFAULT") ? m_strTableSpace : strTableSpace);
        strIndexSpace = (strIndexSpace.equals("DEFAULT") ? m_strIndexSpace : strIndexSpace);

        strDropTableStmt = "DROP TABLE " + m_strODSSchema + ".PRODATTRIBUTE";
        strCreateTableStmt = "CREATE TABLE " + m_strODSSchema + ".PRODATTRIBUTE " + " (" + " ENTITYTYPE VARCHAR(32) NOT NULL," +
            " ENTITYID INT NOT NULL, " + " ATTRIBUTECODE VARCHAR(32) NOT NULL,  " + " ATTRIBUTEVALUE VARCHAR(254), " +
            " NLSID INT NOT NULL, " + " FLAGCODE VARCHAR(16), " + " VALFROM TIMESTAMP NOT NULL WITH DEFAULT CURRENT TIMESTAMP)";

        strIndex1SQL = " CREATE UNIQUE INDEX " + m_strODSSchema + ".PRODATTRIBUTE_PK ON " + m_strODSSchema + ".PRODATTRIBUTE " +
            "(ENTITYID, ENTITYTYPE,NLSID, ATTRIBUTECODE)";

        strCreateTableStmt = strCreateTableStmt + (strTableSpace.equals("DEFAULT") ? "" : " IN " + strTableSpace) +
            (strIndexSpace.equals("DEFAULT") ? "" : " INDEX IN " + strIndexSpace);

        strTableSpace = ODSServerProperties.getTableSpace(m_strODSSchema, "PRODATTRELATOR");
        strIndexSpace = ODSServerProperties.getIndexSpace(m_strODSSchema, "PRODATTRELATOR");
        strTableSpace = (strTableSpace.equals("DEFAULT") ? m_strTableSpace : strTableSpace);
        strIndexSpace = (strIndexSpace.equals("DEFAULT") ? m_strIndexSpace : strIndexSpace);

        strDropStmt2 = "DROP TABLE " + m_strODSSchema + ".PRODATTRELATOR";
        strCreateStmt2 = "CREATE TABLE " + m_strODSSchema + ".PRODATTRELATOR" + " (" + " ENTITY1TYPE VARCHAR(32) NOT NULL," +
            " ENTITY1ID INT NOT NULL, " + " ENTITY2TYPE VARCHAR(32) NOT NULL, " + " ENTITY2ID INT NOT NULL, " +
            " VALFROM TIMESTAMP NOT NULL WITH DEFAULT CURRENT TIMESTAMP) ";

        strIndex2SQL = " CREATE UNIQUE INDEX " + m_strODSSchema + ".PRODATTRELATOR_PK ON " + m_strODSSchema + ".PRODATTRELATOR " +
            "(ENTITY2ID, ENTITY2TYPE,ENTITY1ID, ENTITY1TYPE)";
        strCreateStmt2 = strCreateStmt2 + (strTableSpace.equals("DEFAULT") ? "" : " IN " + strTableSpace) +
            (strIndexSpace.equals("DEFAULT") ? "" : " INDEX IN " + strIndexSpace);

        try {

            DatabaseMetaData dbMeta = m_conODS.getMetaData();

            String[] strMetaDB2Token = {
                "TABLE"}; // Look only for tables
            boolean bTableThere = false; // flag to indicate if the table is currently present

            try {
                rs = dbMeta.getTables(null, m_strODSSchema, "PRODATTRIBUTE", strMetaDB2Token); //  Check for the table ...
                if (rs.next()) {
                    bTableThere = true; // We have found it...
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            }

            if (bTableThere) {
                //Drop the table since we found it
                try {
                    stmtDropTable = m_conODS.createStatement();
                    m_dbPDH.debug(D.EBUG_INFO, "resetProdAttTables:DROPPING PRODATTRIBUTE");
                    stmtDropTable.execute(strDropTableStmt);
                }
                finally {
                    stmtDropTable.close();
                    m_conODS.commit();
                }
            } else {
                m_dbPDH.debug(D.EBUG_INFO, "resetProdAttTables:PRODATTRIBUTE DROP BYPASSED");
            }
            m_dbPDH.debug(D.EBUG_INFO, "resetProdAttTables:CREATING NEW PRODATTRIBUTE");
            try {
                stmtCreateTable = m_conODS.createStatement();
                stmtCreateTable.execute(strCreateTableStmt);
                stmtCreateTable.execute(strIndex1SQL);
            }
            finally {
                stmtCreateTable.close();
            }

            bTableThere = false; // flag to indicate if the table is currently present
            try {
                rs = dbMeta.getTables(null, m_strODSSchema, "PRODATTRELATOR", strMetaDB2Token); //  Check for the table ...
                if (rs.next()) {
                    bTableThere = true; // We have found it...
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            }

            if (bTableThere) {
                //Drop the table since we found it
                try {
                    stmtDrop2 = m_conODS.createStatement();
                    m_dbPDH.debug(D.EBUG_INFO, "resetProdAttTables:DROPPING PRODATTRELATOR");
                    stmtDrop2.execute(strDropStmt2);
                }
                finally {
                    stmtDrop2.close();
                    m_conODS.commit();
                }
            } else {
                m_dbPDH.debug(D.EBUG_INFO, "resetProdAttTables:PRODATTRELATOR DROP BYPASSED");
            }

            m_dbPDH.debug(D.EBUG_INFO, "resetProdAttTables:CREATING NEW PRODATTRELATOR");
            try {
                stmtCreate2 = m_conODS.createStatement();
                stmtCreate2.execute(strCreateStmt2);
                stmtCreate2.execute(strIndex2SQL);
            }
            finally {
                stmtCreate2.close();
            }
        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "resetProdAttTables:ERROR:" + e.getMessage());
            System.exit(1);
        }

    }

    /**
     * resetAttributeTable
     *
     *  @author David Bigelow
     */
    protected void resetAttributeTable() {

        ResultSet rs = null;
        Statement stmtDrop1 = null;
        Statement stmtCreate1 = null;
        Statement stmtDrop2 = null;
        Statement stmtCreate2 = null;

        String[] strMetaDB2Token = {
            "TABLE"}; // Look only for tables
        boolean bTableThere = false; // flag to indicate if the table is currently present

        String strDrop1 = "DROP TABLE " + m_strODSSchema + ".ATTRIBUTE";
        String strCreate1 = "CREATE TABLE " + m_strODSSchema + ".ATTRIBUTE " + " (" + " ENTITYTYPE VARCHAR(32) NOT NULL," +
            " ATTRIBUTETYPE CHAR (1) NOT NULL," + " ATTRIBUTECODE VARCHAR(32) NOT NULL,  " +
            " ATTRIBUTETOKEN VARCHAR(32) NOT NULL, " + " DESCRIPTION VARCHAR(64) NOT NULL, " + " NLSID INT NOT NULL, " +
            " ATTRSORTORDER INT NOT NULL WITH DEFAULT 9999, " + " VALFROM TIMESTAMP NOT NULL WITH DEFAULT CURRENT TIMESTAMP) ";
        //" PRIMARY KEY (ENTITYTYPE, NLSID, ATTRIBUTECODE)) ";

        String strDropStmt2 = "DROP TABLE " + m_strODSSchema + ".ATTRGROUP ";
        String strCreateStmt2 = "CREATE TABLE " + m_strODSSchema + ".ATTRGROUP " + " (" + " ENTITYTYPE VARCHAR(32) NOT NULL," +
            " DESCRIPTION VARCHAR(64) NOT NULL," + " GROUPTOKEN VARCHAR(32) NOT NULL,  " + " NLSID INT NOT NULL, " +
            " GROUPSORT INT NOT NULL WITH DEFAULT 9999, " + " VALFROM TIMESTAMP NOT NULL WITH DEFAULT CURRENT TIMESTAMP, " +
            " PRIMARY KEY (ENTITYTYPE, NLSID)) ";

        try {

            //
            //  first lets do the Attribute Table
            //

            DatabaseMetaData dbMeta = m_conODS.getMetaData();
            try {
                rs = dbMeta.getTables(null, m_strODSSchema, "ATTRIBUTE", strMetaDB2Token);
                if (rs.next()) {
                    bTableThere = true; // We have found it...
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            }

            if (bTableThere) {
                //Drop the table since we found it
                try {
                    stmtDrop1 = m_conODS.createStatement();
                    m_dbPDH.debug(D.EBUG_INFO, "resetAttributeTable:DROPPING ATTRIBUTE");
                    stmtDrop1.execute(strDrop1);
                }
                finally {
                    stmtDrop1.close();
                    stmtDrop1 = null;
                }

            } else {
                m_dbPDH.debug(D.EBUG_INFO, "resetAttributeTable:ATTRIBUTE DROP BYPASSED");
            }

            m_dbPDH.debug(D.EBUG_INFO, "resetAttributeTable:CREATING NEW ATTRIBUTE");
            try {
                stmtCreate1 = m_conODS.createStatement();
                stmtCreate1.execute(strCreate1);
            }
            finally {
                stmtCreate1.close();
                stmtCreate1 = null;
            }

            //
            //  Now.. lets do the AttributeGroup Table
            //

            bTableThere = false; // flag to indicate if the table is currently present
            try {
                rs = dbMeta.getTables(null, m_strODSSchema, "ATTRGROUP", strMetaDB2Token); //  Check for the table ...
                if (rs.next()) {
                    bTableThere = true; // We have found it...
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            }

            if (bTableThere) {
                //Drop the table since we found it
                try {
                    stmtDrop2 = m_conODS.createStatement();
                    m_dbPDH.debug(D.EBUG_INFO, "resetAttributeTable:DROPPING ATTRGROUP");
                    stmtDrop2.execute(strDropStmt2);
                }
                finally {
                    stmtDrop2.close();
                    stmtDrop2 = null;
                }

            } else {
                m_dbPDH.debug(D.EBUG_INFO, "resetAttributeTable:ATTRGROUP DROP BYPASSED");
            }
            m_dbPDH.debug(D.EBUG_INFO, "resetAttributeTable:CREATING NEW ATTRGROUP");
            try {
                stmtCreate2 = m_conODS.createStatement();
                stmtCreate2.execute(strCreateStmt2);
            }
            finally {
                stmtCreate2.close();
                stmtCreate2 = null;
            }
            m_conODS.commit();
        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "resetAttributeTable:ERROR:" + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * addMetaControlRecords
     *
     * @param _eg
     *  @author David Bigelow
     */
    protected void addMetaControlRecords(EntityGroup _eg) {

        String strInsert1 = "INSERT INTO " + m_strODSSchema + ".ATTRGROUP  " + "(ENTITYTYPE, " + " DESCRIPTION, " + " GROUPTOKEN, " +
            " NLSID) " + " VALUES (?,?,?,?)";

        String strInsert2 = "INSERT INTO " + m_strODSSchema + ".ATTRIBUTE  " + "(ENTITYTYPE, " + " ATTRIBUTETYPE, " +
            " ATTRIBUTECODE, " + " ATTRIBUTETOKEN, " + " DESCRIPTION, " + " NLSID) " + " VALUES (?,?,?,?,?,?) ";

        String strEntCount = "SELECT COUNT(*) FROM " + m_strODSSchema + ".ATTRGROUP WHERE " + " ENTITYTYPE = '" + _eg.getEntityType() +
            "' AND " + " NLSID = 1";

        PreparedStatement ps = null;
        Statement stmt = null;
        ResultSet rs = null;

        int iCount = 0;

        try {

            m_dbPDH.debug(D.EBUG_INFO, "addMetaControlRecords:Inserting " + _eg.getEntityType() + " INTO ATTRGROUP");
            try {
                stmt = m_conODS.createStatement();
                rs = stmt.executeQuery(strEntCount);

                if (rs.next()) {
                    iCount = rs.getInt(1);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
            if (iCount == 0 && ODSServerProperties.isProdAttrGroup(m_strODSSchema, _eg.getEntityType())) {
                m_dbPDH.debug(D.EBUG_ERR, "addMetaControlRecords:ADDING TO ATTRGROUP TABLE:" + _eg.getEntityType());
                try {
                    ps = m_conODS.prepareStatement(strInsert1);
                    ps.setString(1, _eg.getEntityType());
                    ps.setString(2, _eg.getLongDescription());
                    ps.setString(3, "PSG" + _eg.getEntityType());
                    ps.setInt(4, 1);
                    ps.execute();
                }
                finally {
                    ps.close();
                    ps = null;
                    m_conODS.commit();
                }
            }

            for (int i = 0; i < _eg.getMetaAttributeCount(); i++) {
                EANMetaAttribute ma = _eg.getMetaAttribute(i);
                if (ODSServerProperties.isProdAttribute(m_strODSSchema, ma.getAttributeCode())) {
                    String strCount = "SELECT COUNT(*) FROM " + m_strODSSchema + ".ATTRIBUTE WHERE " + " ENTITYTYPE = '" +
                        _eg.getEntityType() + "' AND " + " ATTRIBUTECODE = '" + ma.getAttributeCode() + "' AND " + " NLSID = 1";

                    try {
                        stmt = m_conODS.createStatement();
                        rs = stmt.executeQuery(strCount);
                        iCount = 0;
                        if (rs.next()) {
                            iCount = rs.getInt(1);
                        }
                    }
                    finally {
                        if (rs != null) {
                            rs.close();
                            rs = null;
                        }
                        if (stmt != null) {
                            stmt.close();
                            stmt = null;
                        }
                    }

                    if (iCount == 0) {
                        m_dbPDH.debug(D.EBUG_INFO, "addMetaControlRecords:ADDING:" + ma.getAttributeCode());
                        try {
                            ps = m_conODS.prepareStatement(strInsert2);
                            ps.setString(1, _eg.getEntityType());
                            ps.setString(2, ma.getAttributeType());
                            ps.setString(3, ma.getAttributeCode());
                            ps.setString(4, ODSServerProperties.getProdAttributeMap(m_strODSSchema, ma.getAttributeCode()));
                            ps.setString(5, ma.getLongDescription());
                            ps.setInt(6, 1);
                            ps.execute();
                        }
                        finally {
                            if (ps != null) {
                                ps.close();
                                ps = null;
                            }
                            m_conODS.commit();
                        }
                    }
                }
            }
            m_conODS.commit();
        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "addMetaControlRecords:ERROR:" + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * addToRestartTable
     *
     * @param _strTableName
     *  @author David Bigelow
     */
    protected void addToRestartTable(String _strTableName) {
        String strInsertTableStmt = "INSERT INTO " + m_strODSSchema + ".RESTARTABLE  VALUES(CURRENT TIMESTAMP, '" + _strTableName +
            "')";

        Statement stmtInserttable = null;

        try {
            m_dbPDH.debug(D.EBUG_INFO, "addToRestartTable:Inserting " + _strTableName + " INTO RESTARTABLE");
            try {
                stmtInserttable = m_conODS.createStatement();
                stmtInserttable.execute(strInsertTableStmt);
            }
            finally {
                if (stmtInserttable != null) {
                    stmtInserttable.close();
                    stmtInserttable = null;
                }
            }
            m_bRestart = false; // Done w/ restart
            m_conODS.commit();
        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "addToRestartTable:ERROR:" + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * isInRestartTable
     *
     * @param _strTableName
     * @return
     *  @author David Bigelow
     */
    protected boolean isInRestartTable(String _strTableName) {
        String strCountTableStmt = "SELECT COUNT(*) FROM " + m_strODSSchema + ".RESTARTABLE  WHERE TABLENAME = '" + _strTableName +
            "'";

        Statement stmt = null;
        ResultSet rs = null;
        int iCount = 0;

        try {
            m_dbPDH.debug(D.EBUG_INFO, "isInRestartTable:checking " + _strTableName + " IN RESTARTABLE");
            try {
                stmt = m_conODS.createStatement();
                rs = stmt.executeQuery(strCountTableStmt);
                iCount = 0;
                if (rs.next()) {
                    iCount = rs.getInt(1);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
            return (iCount > 0);
        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "isInRestartTable:ERROR:" + e.getMessage());
            System.exit(1);
        }
        return false;
    }

    /**
     * clearRestartTable
     *
     *  @author David Bigelow
     */
    protected void clearRestartTable() {

        String strDeleteTableStmt = "DELETE FROM " + m_strODSSchema + ".RESTARTABLE ";

        try {
            Statement stmtDeletetable = m_conODS.createStatement();
            m_dbPDH.debug(D.EBUG_INFO, "clearRestartTable:CLEARING OUT RESTARTABLE");
            try {
                stmtDeletetable.execute(strDeleteTableStmt);
            }
            finally {
                stmtDeletetable.close();
                stmtDeletetable = null;
            }
            m_conODS.commit();
            m_bRestart = false;
        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "clearRestartTable:ERROR:" + e.getMessage());
            System.exit(1);
        }

    }

    /**
     * setTimestampInTimetable
     *
     *  @author David Bigelow
     */
    protected void setTimestampInTimetable() {
        String strInsertTimestampStmt = "INSERT INTO " + m_strODSSchema + ".TIMETABLE " + "(RUNTIME,RUNTYPE) VALUES('" + m_strNow +
            "','I')";
        try {
            Statement stmtInsertTimeStamp = m_conODS.createStatement();
            try {
                stmtInsertTimeStamp.execute(strInsertTimestampStmt);
            }
            finally {
                stmtInsertTimeStamp.close();
                stmtInsertTimeStamp = null;
            }
            m_conODS.commit();
        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "setTimestampInTimetable:ERROR:Insert into timetable:" + e.getMessage());
            System.exit( -1);
        }

    }

    /**
     * setIFMLock
     *
     *  @author David Bigelow
     */
    protected void setIFMLock() {

        String strSelect = "SELECT * FROM ECCM.IFMLOCK WHERE PROCESS_ID = 2";
        String strUpdate = "UPDATE ECCM.IFMLOCK SET STATUS = 1, START_TIME = CURRENT TIMESTAMP WHERE PROCESS_ID = 2";
        String strInsert = "INSERT INTO ECCM.IFMLOCK (PROCESS_ID,PROCESS_NAME,STATUS,START_TIME, END_TIME) VALUES " +
            "(2,'DMNET',0,CURRENT TIMESTAMP,CURRENT TIMESTAMP)";

        Statement stmt = null;
        ResultSet rs = null;
        Statement stmtInsertTable = null;
        Statement stmtUpdateTable = null;

        boolean bExists = false;

        try {

            try {
                stmt = m_conODS.createStatement();
                rs = stmt.executeQuery(strSelect);
                bExists = rs.next();
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                m_conODS.commit();
            }

            if (!bExists) {
                m_dbPDH.debug(D.EBUG_INFO, "setIFMLock:Inserting PROCESS_ID = 2 INTO ECCM.IFMLOCK");
                try {
                    stmtInsertTable = m_conODS.createStatement();
                    stmtInsertTable.execute(strInsert);
                }
                finally {
                    if (stmtInsertTable != null) {
                        stmtInsertTable.close();
                        stmtInsertTable = null;
                    }
                    m_conODS.commit();
                }
            }

            m_dbPDH.debug(D.EBUG_INFO, "setIFMLock:Update PROCESS_ID = 2 INTO ECCM.IFMLOCK");
            try {
                stmtUpdateTable = m_conODS.createStatement();
                stmtUpdateTable.execute(strUpdate);
            }
            finally {
                if (stmtUpdateTable != null) {
                    stmtUpdateTable.close();
                    stmtUpdateTable = null;
                }
                m_conODS.commit();
            }

        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "setIFMLock:ERROR: on ECCM.IFMLOCK:" + e.getMessage());
            System.exit( -1);
        }

    }

    /**
     * clearIFMLock
     *
     *  @author David Bigelow
     */
    protected void clearIFMLock() {

        String strUpdate = "UPDATE ECCM.IFMLOCK SET STATUS = 0, END_TIME = CURRENT TIMESTAMP WHERE PROCESS_ID = 2";
        Statement stmtUpdateTable = null;

        try {

            m_dbPDH.debug(D.EBUG_INFO, "setIFMLock:Update PROCESS_ID = 2 INTO ECCM.IFMLOCK");

            // force a change
            try {
                stmtUpdateTable = m_conODS.createStatement();
                stmtUpdateTable.execute(strUpdate);
            }
            finally {
                if (stmtUpdateTable != null) {
                    stmtUpdateTable.close();
                    stmtUpdateTable = null;
                }
            }
            m_conODS.commit();

        }
        catch (SQLException e) {
            m_dbPDH.debug(D.EBUG_ERR, "setIFMLock:ERROR: on ECCM.IFMLOCK:" + e.getMessage());
            System.exit( -1);
        }

    }

    /**
     * We can only run if process 8,9, and 11 are not
     * @return
     * @author Dave
     */
    protected final boolean canDMNetRun() {

        String strStatement = "SELECT COUNT(*) from ECCM.IFMLOCK where STATUS = 1 AND PROCESS_ID IN (8,11,19)";
        int iAnswer = 0;

        Statement stmt = null;
        ResultSet rs = null;

        try {

            try {
                stmt = m_conODS.createStatement();
                rs = stmt.executeQuery(strStatement);
                if (rs.next()) {
                    iAnswer = rs.getInt(1);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }

        }
        catch (SQLException se) {
            m_dbPDH.debug(D.EBUG_ERR, "checkRunability:ERROR:" + se.getMessage());
            System.exit( -1);
        }
        return iAnswer == 0;

    }

    /**
     * getLastRuntime
     *
     * @return
     *  @author David Bigelow
     */
    protected String getLastRuntime() {

        String strStatement = "SELECT RUNTIME FROM " + m_strODSSchema + ".TIMETABLE ORDER BY RUNTIME DESC";
        String strAnswer = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {

            try {
                stmt = m_conODS.createStatement();
                rs = stmt.executeQuery(strStatement);
                if (rs.next()) {
                    strAnswer = rs.getString(1).trim();
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }

        }
        catch (SQLException se) {
            m_dbPDH.debug(D.EBUG_ERR, "getLastRuntime:ERROR:" + se.getMessage());
            System.exit( -1);
        }
        return strAnswer;
    }

    /**
     *  This will update the ODS table with data from the corresponding PDH
     *  entities which have changed
     *
     *@param  _eg                      EntityGroup object for the ODS Table
     *@exception  SQLException
     *@exception  MiddlewareException
     */
    protected void updateODSTable(EntityGroup _eg) throws SQLException, MiddlewareException {

        int iSessionID = m_dbPDH.getNewSessionID();
        int iStartID = 0;
        int iEndID = 0;
        int iMaxID = 0;
        int iEntityCount = 0;

        boolean bLoop = false;
        boolean bATTREL = false;
        boolean bFKEY = false;
        boolean bCROSSREF = false;
        boolean bFKEYCHECK = false;
        boolean bAttSubSet = false;

        String strDeleteFLAGSQL = null;
        String strDeletePRODATTMAPSQL = null;
        String strDeleteEntitySQL = null;
        String strDPUBFSQL = null;
        String strInsertFLAGSQL = null;
        String strInsertDLOGSQL = null;
        String strInsertPRODATTMAPSQL = null;
        String strUpdateFKey = null;
        String strUpdateDFKEY = null;
        String strUpdateDFKEYPUB = null;
        String strATTRSQL = null;
        String strDELATTRSQL = null;
        String strGetExistingRel = null;
        String strGetExistingNLS = null;
        String strDeleteEntityNLSSQL = null;
        String strDPUBFNLSSQL = null;

        // Do some prepared statements here..
        PreparedStatement psFlag = null;
        PreparedStatement psTrans = null;
        PreparedStatement psFlagDel = null;
        PreparedStatement psProdAttrDel = null;
        PreparedStatement psFKey = null;
        PreparedStatement psDFKEY = null;
        PreparedStatement psDFKEYPUB = null;
        PreparedStatement psATTR = null;
        PreparedStatement psDELATTR = null;
        PreparedStatement psDEL = null;
        PreparedStatement psDPUBF = null;
        PreparedStatement psDLOG = null;
        PreparedStatement psGetNLS = null;
        PreparedStatement psDELNLS = null;
        PreparedStatement psDPUBFNLS = null;
        PreparedStatement psExistRel = null;

        String strUpdateHeadSQL = null;
        String strUpdateFootSQL = null;
        String strUpdateFIELDS = null;

        String strInsertSQL = null;
        String strUpdateSQL = null;

        String strInsertHEADSQL = null;
        String strInsertFIELDSSQL = null;
        String strInsertMARKERSQL = null;

        String strInsertFIELDS = "";
        String strInsertMARKER = "";

        EANList elMulti = new EANList();
        EANList elTrans = new EANList();

        EntityGroup eg1 = null;
        EntityGroup eg2 = null;

        String strEntityType = _eg.getEntityType();
        String strEntity1Type = null;
        String strEntity2Type = null;

        String strEnterprise = m_prof.getEnterprise();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        ReturnStatus returnStatus = new ReturnStatus( -1);

        // Lets set the rules to lax here
        m_df.setLenient(true);

        // O.K.  lets strip this information from some international flags..
        // Some flags can only be us. english only
        setEnglishOnlyFlags(_eg);

        System.gc();

        try {
            //Now get the max rows we have to retrieve for this entity
            m_dbPDH.debug(D.EBUG_DETAIL,
                          "gbl9003:params:" + strEnterprise + ":" + strEntityType + ":" + m_strODSSchema + ":" + m_strLastRun + ":" +
                          m_strNow);
            try {
                rs = m_dbPDH.callGBL9003(returnStatus, strEnterprise, strEntityType, m_strODSSchema, m_strLastRun, m_strNow);
                if (rs.next()) {
                    iMaxID = rs.getInt(1);
                    iStartID = rs.getInt(2);
                    iEntityCount = rs.getInt(3);
                    m_dbPDH.debug(D.EBUG_DETAIL, "gbl9003:answer: Max:" + iMaxID + ":Start:" + iStartID + ":Rows:" + iEntityCount);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                m_dbPDH.commit();
                m_dbPDH.freeStatement();
                m_dbPDH.isPending();
            }

            // New iEndID calculations
            iEndID = ( (iStartID + m_iChuckSize) > iMaxID ? iMaxID : iStartID + m_iChuckSize);
            bLoop = iStartID <= iMaxID;

            if (_eg.isRelator()) {
                strEntity1Type = _eg.getEntity1Type();
                strEntity2Type = _eg.getEntity2Type();
            }

            bATTREL = _eg.isRelator() && ODSServerProperties.isProdAttributeRelator(m_strODSSchema, strEntityType);
            bFKEY = ODSServerProperties.isFKeyMapRelator(m_strODSSchema, strEntityType) &&
                !ODSServerProperties.getFKeyMap(m_strODSSchema, strEntity2Type).equals("n");

            bCROSSREF = ODSServerProperties.isCrossPostEntity(m_strODSSchema, _eg.getEntityType()) ||
                ODSServerProperties.isProdAttrGroup(m_strODSSchema, _eg.getEntityType()) || bATTREL ||
                ODSServerProperties.isCrossPostRelator(m_strODSSchema, _eg.getEntityType());

            bFKEYCHECK = ODSServerProperties.hasFKeyMap(m_strODSSchema, _eg.getEntityType());

            bAttSubSet = ODSServerProperties.hasAttributeSubset(m_strODSSchema, _eg.getEntityType());

            strDeleteFLAGSQL = "DELETE FROM " + m_strODSSchema + ".FLAG " + " WHERE EntityType = ? AND EntityID = ?";
            strDeletePRODATTMAPSQL = "DELETE FROM " + m_strODSSchema + ".PRODATTRIBUTE " + " WHERE EntityType = ? AND EntityID = ?";
            strDeleteEntitySQL = "DELETE FROM " + m_strODSSchema + "." + strEntityType + " WHERE  EntityID = ?";
            strDPUBFSQL = "UPDATE " + m_strODSSchema + "." + strEntityType + " SET PublishFlag = 'D' WHERE ENTITYID = ?";

            strInsertFLAGSQL = "INSERT  INTO " + m_strODSSchema + ".FLAG " +
                " (VALFROM,ENTITYTYPE,ENTITYID, NLSID, ATTRIBUTECODE, FLAGCODE,SFVALUE,FLAGDESCRIPTION) " +
                " VALUES (?,?,?,?,?,?,?,?)";

            strInsertDLOGSQL = "INSERT  INTO " + m_strODSSchema + ".DELETELOG " +
                " (TYPE, ENTITYTYPE, ENTITYID, ENTITY1TYPE, ENTITY1ID, ENTITY2TYPE, ENTITY2ID) " + " VALUES (?,?,?,?,?,?,?)";

            strInsertPRODATTMAPSQL = "INSERT  INTO " + m_strODSSchema + ".PRODATTRIBUTE " +
                "(ENTITYTYPE, ENTITYID, ATTRIBUTECODE, ATTRIBUTEVALUE, NLSID, FLAGCODE, VALFROM) " + " VALUES (?,?,?,?,?,?,?)";

            strUpdateFKey = "UPDATE  " + m_strODSSchema + "." + strEntity2Type + " SET " + strEntity1Type +
                "ID = ?, valfrom = ? WHERE ENTITYID = ?";
            strUpdateDFKEY = "UPDATE  " + m_strODSSchema + "." + strEntity2Type + " SET " + strEntity1Type + "ID = " +
                " (select max(id1) from " + m_strODSSchema + "." + strEntityType + " Where id2 = ? and entityid <> ?) " +
                ", valfrom = ? WHERE ENTITYID = ? AND " + strEntity1Type + "ID = ?";

            //
            // ONLY UPDATE to NULL of Publish is still on .. lets not null it out..
            //
            strUpdateDFKEYPUB = "UPDATE  " + m_strODSSchema + "." + strEntity2Type + " SET " + strEntity1Type + "ID = " +
                " (select max(id1) from " + m_strODSSchema + "." + strEntityType + " Where id2 = ? and entityid  <> ?) " +
                ", valfrom = ? WHERE PUBLISHFLAG = 'Y' AND ENTITYID = ? AND " + strEntity1Type + "ID = ?";

            strATTRSQL = "INSERT INTO " + m_strODSSchema + ".PRODATTRELATOR " +
                " (ENTITY1ID,ENTITY1TYPE,ENTITY2ID,ENTITY2TYPE,VALFROM) " + " VALUES(?,?,?,?,?)";
            strDELATTRSQL = "DELETE FROM " + m_strODSSchema + ".PRODATTRELATOR  WHERE " +
                " ENTITY1TYPE = ? AND ENTITY1ID = ? AND ENTITY2TYPE = ? AND ENTITY2ID = ?";
            strGetExistingRel = "SELECT COUNT(*) FROM " + m_strODSSchema + "." + strEntityType +
                " WHERE ENTITYID <> ? AND ID1 = ? and ID2 = ?";
            strGetExistingNLS = "SELECT DISTINCT NLSID FROM " + m_strODSSchema + "." + strEntityType + " WHERE ENTITYID = ?";
            strDeleteEntityNLSSQL = "DELETE FROM " + m_strODSSchema + "." + strEntityType + " WHERE  EntityID = ? and NLSID = ?";
            strDPUBFNLSSQL = "UPDATE " + m_strODSSchema + "." + strEntityType +
                " SET PublishFlag = 'D' WHERE ENTITYID = ? AND NLSID = ?";

            // Do some prepared statements here..
            psFlag = m_conODS.prepareStatement(strInsertFLAGSQL);
            psTrans = m_conODS.prepareStatement(strInsertPRODATTMAPSQL);
            psFlagDel = m_conODS.prepareStatement(strDeleteFLAGSQL);
            psProdAttrDel = m_conODS.prepareStatement(strDeletePRODATTMAPSQL);
            psFKey = m_conODS.prepareStatement(strUpdateFKey);
            psDFKEY = m_conODS.prepareStatement(strUpdateDFKEY);
            psDFKEYPUB = m_conODS.prepareStatement(strUpdateDFKEYPUB);
            psATTR = m_conODS.prepareStatement(strATTRSQL);
            psDELATTR = m_conODS.prepareStatement(strDELATTRSQL);
            psDEL = m_conODS.prepareStatement(strDeleteEntitySQL);
            psDPUBF = m_conODS.prepareStatement(strDPUBFSQL);
            psDLOG = m_conODS.prepareStatement(strInsertDLOGSQL);
            psGetNLS = m_conODS.prepareStatement(strGetExistingNLS);
            psDELNLS = m_conODS.prepareStatement(strDeleteEntityNLSSQL);
            psDPUBFNLS = m_conODS.prepareStatement(strDPUBFNLSSQL);
            psExistRel = m_conODS.prepareStatement(strGetExistingRel);

            System.gc();
            if (_eg.isRelator()) {
                eg1 = new EntityGroup(null, m_dbPDH, m_prof, strEntity1Type, "No Atts");
                eg2 = new EntityGroup(null, m_dbPDH, m_prof, strEntity2Type, "No Atts");
            }

            while (bLoop) {

                //Get the entities which will fulfill the filter criteria set in the meta for this entity
                m_dbPDH.debug(D.EBUG_DETAIL,
                    "gbl9005:params:" + iSessionID + ":" + strEnterprise + ":" + strEntityType + ":" + m_strODSSchema + ":" +
                    iStartID + ":" + iEndID + ":" + m_strLastRun + ":" + m_strNow);
                rs = m_dbPDH.callGBL9005(returnStatus, iSessionID, strEnterprise, strEntityType, m_strODSSchema, iStartID, iEndID,
                                         m_strLastRun, m_strNow);
                rdrs = new ReturnDataResultSet(rs);
                rs.close();
                m_dbPDH.commit();
                rs = null;
                m_dbPDH.freeStatement();
                m_dbPDH.isPending();

                // O.K.  Lets tuck all the entity items into the group
                for (int ii = 0; ii < rdrs.size(); ii++) {

                    EntityItem ei1 = null;
                    EntityItem ei2 = null;
                    EntityItem ei = null;

                    int iEntity1ID = rdrs.getColumnInt(ii, 0);
                    int iEntityID = rdrs.getColumnInt(ii, 1);
                    int iEntity2ID = rdrs.getColumnInt(ii, 2);
                    String strValFrom = rdrs.getColumnDate(ii, 3);
                    String strEffTo = rdrs.getColumnDate(ii, 4);
                    m_dbPDH.debug(D.EBUG_DETAIL,
                                  "gbl9005:answers:" + iEntity1ID + ":" + iEntityID + ":" + iEntity2ID + ":" + strValFrom + ":" +
                                  strEffTo);
                    if (!_eg.containsEntityItem(strEntityType, iEntityID) &&
                        ( (_eg.isRelator() && iEntity1ID > 0 && iEntity2ID > 0) || !_eg.isRelator())) {
                        ei = new EntityItem(_eg, null, strEntityType, iEntityID);
                        // If this date is not forever set active for this guy to false..
                        ei.setActive(strEffTo.equals(m_strForever));
                        _eg.putEntityItem(ei);
                    }
                    if (_eg.isRelator() && iEntity1ID > 0 && iEntity2ID > 0) {
                        ei1 = new EntityItem(eg1, null, strEntity1Type, iEntity1ID);
                        eg1.putEntityItem(ei1);
                        ei2 = new EntityItem(eg2, null, strEntity2Type, iEntity2ID);
                        eg2.putEntityItem(ei2);
                        ei.putUpLink(ei1);
                        ei.putDownLink(ei2);
                    }
                }

                // Now ... we fill out all the attributes if anything was populated
                // from above
                //
                // only look for subsets if we need to

                if (rdrs.size() > 0) {
                    popAllAttributeValues(_eg, iSessionID, bAttSubSet);
                }

                // Now remove all the records to clean up after yourself
                // We need a simpler way to do this

                m_dbPDH.callGBL8105(returnStatus, iSessionID);
                m_dbPDH.commit();
                m_dbPDH.freeStatement();
                m_dbPDH.isPending();

                strUpdateHeadSQL = "UPDATE " + m_strODSSchema + "." + strEntityType + " SET ";
                strUpdateFootSQL = "  VALFROM = ? WHERE EntityID = ? AND NLSID = ?";
                strUpdateFIELDS = "";

                strInsertSQL = "";
                strUpdateSQL = "";

                strInsertHEADSQL = "INSERT INTO " + m_strODSSchema + "." + strEntityType;
                strInsertFIELDSSQL = (_eg.isRelator() ? " (EntityID, NLSID, ID1, ID2, VALFROM, VALTO" :
                                      " (EntityID, NLSID, VALFROM, VALTO");
                strInsertMARKERSQL = (_eg.isRelator() ? " VALUES (?, ?, ?, ?, ?, ?" : " VALUES (?, ?, ?, ?");

                strInsertFIELDS = "";
                strInsertMARKER = "";

                // Here we build the rest of the SQL Statement
                for (int ii = 0; ii < _eg.getEntityItemCount(); ii++) {

                    EntityItem ei = _eg.getEntityItem(ii);

                    D.ebug(D.EBUG_SPEW,
                           "Processing EntityItem:" + ei.getEntityType() + ":" + ei.getEntityID() + " is active?" + ei.isActive());

                    if (ODSServerProperties.excludeEntityFromUpdate(m_strODSSchema, ei)) {
                        D.ebug(D.EBUG_DETAIL,
                               "ODSMethods.updateODSTable:excludeEntityFromUpdate:ET:" + ei.getEntityType() + ":EID:" +
                               ei.getEntityID());
                        // we need to remove any records for this entity if they exist...
                        if (ODSServerProperties.hasPublishFlag(m_strODSSchema, ei.getEntityType())) {
                            D.ebug(D.EBUG_DETAIL,
                                   "ODSMethods.updateODSTable:excludeEntityFromUpdate:UnPublishing: Entity:" + ei.getEntityType() +
                                   ":EID:" + ei.getEntityID());
                            psDPUBF.setInt(1, ei.getEntityID());
                            psDPUBF.execute();
                        } else {
                            D.ebug(D.EBUG_DETAIL,
                                   "ODSMethods.updateODSTable:excludeEntityFromUpdate:Deleting Entity:" + ei.getEntityType() +
                                   ":EID:" + ei.getEntityID());
                            psDEL.setInt(1, ei.getEntityID());
                            psDEL.execute();
                        }
                        continue;
                    }

                    // GAB 061505 We want to "filter out" (skip) any CSOL,CCTO,CVAR,CB that are withdrawn.
                    if (isSkipWithdraw(ei)) {
                        continue;
                    }

                    // needs to be active here...
                    if (!hasRelevantAttributeChanges(ei) && ei.isActive()) {
                        continue;
                    } else if (!ei.isActive()) {
                        if (ODSServerProperties.isMultiAttributeEntity(m_strODSSchema, ei.getEntityType())) {
                            for (int iy = 0; iy < _eg.getMetaAttributeCount(); iy++) {
                                EANMetaAttribute ma = _eg.getMetaAttribute(iy);
                                if (ODSServerProperties.getRollupAttributeMappings(m_strODSSchema, ei.getEntityType(),
                                    ma.getAttributeCode()).length > 0) {
                                    D.ebug(D.EBUG_SPEW,
                                           "b4 popMultiAttributeRecord (delete) for " + ei.getEntityType() + "." + ma.getAttributeCode() +
                                           ":" + ei.getEntityID());
                                    popMultiAttributeRoot(ei.getEntityType(), ma.getAttributeCode(), ei.getEntityID());
                                }
                            }
                        }
                        //
                        //if(_eg.isRelator() && ODSServerProperties.isMultiAttributeRelator(m_strODSSchema,_eg.getEntityType())) {
                        //    popMultiAttributeRootForRel(ei.getEntityType(),ei.getEntityID());
                        //}
                    }
                    //
                    // Moved this outside of block... we don't care if the relator is added or removed, we just want it!
                    //
                    if (_eg.isRelator() && ODSServerProperties.isMultiAttributeRelator(m_strODSSchema, _eg.getEntityType())) {
                        popMultiAttributeRootForRel(ei);
                    }
                    //
                    try {
                        // O.K.  Lets try to delete it and all multi flags and everything else we are can remove
                        // Right here..
                        // If it is active.. we will put it back
                        D.ebug(D.EBUG_DETAIL, "ODSMethods.updateODSTable:ET:" + ei.getEntityType() + ":EID:" + ei.getEntityID());
                        try {
                            psFlagDel.setString(1, strEntityType);
                            psFlagDel.setInt(2, ei.getEntityID());
                            D.ebug(D.EBUG_DETAIL,
                                   "ODSMethods.updateODSTable:Deleting MultiFlagValues Entity info for (" + ei.getEntityType() +
                                   ":" + ei.getEntityID() + ") " + ei.toString());
                            psFlagDel.execute();
                        }
                        catch (SQLException ex) {
                            D.ebug(D.EBUG_ERR, "ODSMethods.updateODSTable.Warning on Flags:" + ex.getMessage());
                        }
                        try {
                            psProdAttrDel.setString(1, strEntityType);
                            psProdAttrDel.setInt(2, ei.getEntityID());
                            D.ebug(D.EBUG_DETAIL,
                                   "ODSMethods.updateODSTable:Deleting Product Attribute Map Entity info for (" + ei.getEntityType() +
                                   ":" + ei.getEntityID() + ") " + ei.toString());
                            psProdAttrDel.execute();
                        }
                        catch (SQLException ex) {
                            D.ebug(D.EBUG_ERR, "ODSMethods.updateODSTable.Warning on Product Attributes:" + ex.getMessage());
                        }

                        if (!ei.isActive()) {
                            // Do we delete . or unpublish?

                            // O.K.  if we need to cross reference post then before we delete anything.. here is the place to do it!

                            if (bCROSSREF) {
                                if (bATTREL || _eg.isRelator()) {
                                    updateValFrom(_eg.getEntity1Type(), ei.getUpLink(0).getEntityID());
                                } else if (ODSServerProperties.isCrossPostEntity(m_strODSSchema, _eg.getEntityType())) {
                                    crossPostII(ei.getEntityType(), ei.getEntityID());
                                } else {
                                    crossPostValFromCheck(_eg.getEntityType(), ei.getEntityID(), 0);
                                }
                            }

                            try {
                                if (ODSServerProperties.hasPublishFlag(m_strODSSchema, strEntityType)) {
                                    D.ebug(D.EBUG_DETAIL,
                                           "ODSMethods.updateODSTable:UnPublishing Entity:" + ei.getEntityType() + ":EID:" +
                                           ei.getEntityID());
                                    psDPUBF.setInt(1, ei.getEntityID());
                                    psDPUBF.execute();
                                } else {
                                    D.ebug(D.EBUG_DETAIL,
                                           "ODSMethods.updateODSTable:Deleting Deactivated Entity:" + ei.getEntityType() + ":EID:" +
                                           ei.getEntityID());
                                    psDEL.setInt(1, ei.getEntityID());
                                    psDEL.execute();
                                }
                            }
                            catch (SQLException ex) {
                                D.ebug(D.EBUG_ERR, "ODSMethods.updateODSTable.Warning on Delete/Unpublish Entity:" + ex.getMessage());
                            }

                            // O.K.  are a relator w/ a forgien key?
                            // Only unlink this FKey if we are not a publishable item
                            if (bFKEY) {
                                if (!ODSServerProperties.hasPublishFlag(m_strODSSchema, strEntity2Type)) {
                                    try {
                                        int iID1 = ( (EntityItem) ei.getUpLink(0)).getEntityID();
                                        int iID2 = ( (EntityItem) ei.getDownLink(0)).getEntityID();
                                        int iID = ei.getEntityID();
                                        D.ebug(D.EBUG_DETAIL,
                                               "ODSMethods.updateODSTable:Unlinking Forgien Key:" + ei.getEntityType() + ":EID:" +
                                               ei.getEntityID());
                                        psDFKEY.setInt(1, iID2);
                                        psDFKEY.setInt(2, iID);
                                        psDFKEY.setString(3, m_strNow);
                                        psDFKEY.setInt(4, iID2);
                                        psDFKEY.setInt(5, iID1);
                                        psDFKEY.execute();
                                    }
                                    catch (SQLException ex) {
                                        D.ebug(D.EBUG_ERR,
                                               "ODSMethods.updateODSTable.Warning on Unlinking Forgien Key:" + ex.getMessage());
                                    }
                                } else {
                                    try {
                                        int iID1 = ( (EntityItem) ei.getUpLink(0)).getEntityID();
                                        int iID2 = ( (EntityItem) ei.getDownLink(0)).getEntityID();
                                        int iID = ei.getEntityID();
                                        D.ebug(D.EBUG_DETAIL,
                                               "ODSMethods.updateODSTable:Unlinking Forgien Key PO." + ei.getEntityType() + ":EID:" +
                                               ei.getEntityID());
                                        psDFKEYPUB.setInt(1, iID2);
                                        psDFKEYPUB.setInt(2, iID);
                                        psDFKEYPUB.setString(3, m_strNow);
                                        psDFKEYPUB.setInt(4, iID2);
                                        psDFKEYPUB.setInt(5, iID1);
                                        psDFKEYPUB.execute();
                                    }
                                    catch (SQLException ex) {
                                        D.ebug(D.EBUG_ERR,
                                               "ODSMethods.updateODSTable.Warning on Unlinking Forgien Key PO:" + ex.getMessage());
                                    }
                                }
                            }

                            //
                            // O.K.  are we a prod Attr Relator?
                            // Here .. we only remove if its the last one in the prodattr relator table...
                            //

                            if (bATTREL) {
                                //
                                // Additional sql to see if another record still exists in the eccm.<entitytype table> where id1 = id1 and
                                // id2 = id2 and entityid not equal to the current entityid.
                                //
                                int iEntity1ID = ( (EntityItem) ei.getUpLink(0)).getEntityID();
                                int iEntity2ID = ( (EntityItem) ei.getDownLink(0)).getEntityID();

                                strEntity1Type = _eg.getEntity1Type();
                                strEntity2Type = _eg.getEntity2Type();

                                D.ebug(D.EBUG_DETAIL,
                                    "ODSMethods.updateODSTable:Checking for alternative similar relators " + ei.getEntityType() +
                                    ":ID:" + ei.getEntityID() + ":PAR:" + strEntity1Type + ":" + iEntity1ID + ":CHD:" +
                                    strEntity2Type + ":" + iEntity2ID);

                                try {
                                    int iCount = 0;
                                    rs = null;

                                    psExistRel.setInt(1, ei.getEntityID());
                                    psExistRel.setInt(2, iEntity1ID);
                                    psExistRel.setInt(3, iEntity2ID);

                                    try {
                                        rs = psExistRel.executeQuery();
                                        //
                                        // Since this is a count sql.. you should always get something back...
                                        rs.next();
                                        iCount = rs.getInt(1);
                                    }
                                    finally {
                                        if (rs != null) {
                                            rs.close();
                                            rs = null;
                                        }
                                    }

                                    if (iCount == 0) {
                                        D.ebug(D.EBUG_DETAIL,
                                            "ODSMethods.updateODSTable:Deleting ProdAttr Relator for " + ei.getEntityType() +
                                            ":ID:" + ei.getEntityID() + ":PAR:" + strEntity1Type + ":" + iEntity1ID + ":CHD:" +
                                            strEntity2Type + ":" + iEntity2ID);
                                        psDELATTR.setString(1, strEntity1Type);
                                        psDELATTR.setInt(2, iEntity1ID);
                                        psDELATTR.setString(3, strEntity2Type);
                                        psDELATTR.setInt(4, iEntity2ID);
                                        psDELATTR.execute();
                                    } else {
                                        D.ebug(D.EBUG_DETAIL,
                                            "ODSMethods.updateODSTable:SKIPPING prodAttr Delete because other relationships still exist " +
                                            ei.getEntityType() + ":ID:" + ei.getEntityID() + ":PAR:" + strEntity1Type + ":" +
                                            iEntity1ID + ":CHD:" + strEntity2Type + ":" + iEntity2ID);
                                    }

                                }
                                catch (SQLException ex) {
                                    D.ebug(D.EBUG_ERR,
                                           "ODSMethods.updateODSTable.Warning on Deleting ProdAttr Relator:" + ex.getMessage());
                                }
                            }

                            // Finally, tagg the delete log for anyone who needs to know..
                            D.ebug(D.EBUG_DETAIL,
                                   "ODSMethods.updateODSTable:Logging Delete for :" + ei.getEntityType() + ":EID:" + ei.getEntityID());

                            try {
                                psDLOG.setString(1, (_eg.isRelator() ? "RELATOR" : "ENTITY"));
                                psDLOG.setString(2, ei.getEntityType());
                                psDLOG.setInt(3, ei.getEntityID());
                                if (_eg.isRelator()) {
                                    int iEntity1ID = ( (EntityItem) ei.getUpLink(0)).getEntityID();
                                    int iEntity2ID = ( (EntityItem) ei.getDownLink(0)).getEntityID();
                                    strEntity1Type = _eg.getEntity1Type();
                                    strEntity2Type = _eg.getEntity2Type();
                                    psDLOG.setString(4, strEntity1Type);
                                    psDLOG.setInt(5, iEntity1ID);
                                    psDLOG.setString(6, strEntity2Type);
                                    psDLOG.setInt(7, iEntity2ID);
                                } else {
                                    psDLOG.setString(4, null);
                                    psDLOG.setInt(5, 0);
                                    psDLOG.setString(6, null);
                                    psDLOG.setInt(7, 0);
                                }
                                psDLOG.execute();
                            }
                            catch (SQLException ex) {
                                D.ebug(D.EBUG_ERR, "ODSMethods.updateODSTable.Warning on Inserting Delete Image:" + ex.getMessage());
                            }

                        } else {

                            NLSItem nlsCurrent = null;
                            int[] iAllNLS = {
                                1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                            if (ODSServerProperties.defNLSMode(m_strODSSchema, _eg.getEntityType())) {
                                System.out.println("DWB --> NLSDEFMODE" + ei);
                                iAllNLS = ei.getInUseNLSIDs();
                                if (iAllNLS.length == 0) {
                                    iAllNLS = new int[1];
                                    iAllNLS[0] = 1;
                                }
                            } else if (ODSServerProperties.geoNLSMode(m_strODSSchema, _eg.getEntityType())) {
                                iAllNLS = getGeoNLSArray(ei);
                                System.out.println("DWB --> GEOMODE" + ei);
                            } else {
                                System.out.println("DWB --> ALL MODE" + ei);
                            }

                            elMulti = new EANList();
                            elTrans = new EANList();

                            //This will have to be repeated for all the languages enabled for the entity TBD
                            for (int il = 0; il < iAllNLS.length; il++) {
                                boolean bEntityExists = false;
                                int iz = 1;

                                PreparedStatement ps = null;

                                if (entityExistsInODS(ei.getEntityType(), ei.getEntityID(), iAllNLS[il])) {
                                    bEntityExists = true;
                                    strUpdateFIELDS = "";
                                } else {
                                    bEntityExists = false;
                                    strInsertFIELDS = "";
                                    strInsertMARKER = "";
                                }

                                D.ebug(D.EBUG_DETAIL,
                                    "ODSMethods.updateODSTable:" + (bEntityExists ? "Updating " : "Inserting ") + "Entity " +
                                    ei.getEntityType() + ":EID:" + ei.getEntityID() + ":" + iAllNLS[il]);

                                // This loop needs to be by metaattribute..updating things to null that do not have an attribute
                                for (int iy = 0; iy < _eg.getMetaAttributeCount(); iy++) {
                                    EANMetaAttribute ma = _eg.getMetaAttribute(iy);
                                    EANAttribute att = null;
                                    if (isDerivedEntityID(ma)) {
                                        continue;
                                    }

                                    att = ei.getAttribute(ma.getAttributeCode());

                                    // a null value here means an expired attribute value!
                                    if (att != null && (att.get() == null || att.get().equals(""))) {
                                        D.ebug(D.EBUG_DETAIL,
                                            "ODSMethods.updateODSTable:removing attribute w/ null value:EI" + ei.getEntityType() +
                                            ":" + ei.getEntityID() + ":" + att.getAttributeCode() + ":" + att.getValFrom());
                                        ei.removeAttribute(att);
                                    }

                                    if (includeColumn(ma)) {
                                        D.ebug(D.EBUG_SPEW,
                                               "AC#COUNT1#:" + iy + ":" + (att == null ? "no att" : att.getAttributeCode()));
                                        if (bEntityExists) {
                                            strUpdateFIELDS = (strUpdateFIELDS.length() > 0) ?
                                                strUpdateFIELDS + ", " + ma.getAttributeCode() + " = ? " :
                                                " " + ma.getAttributeCode() + " = ?";
                                            if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S")) {
                                                strUpdateFIELDS = (strUpdateFIELDS.length() > 0) ?
                                                    strUpdateFIELDS + ", " + ma.getAttributeCode() + "_FC = ? " :
                                                    " " + ma.getAttributeCode() + "_FC = ?";
                                            }
                                        } else {
                                            strInsertFIELDS = strInsertFIELDS + ", " + ma.getAttributeCode();
                                            strInsertMARKER = strInsertMARKER + ", ?";
                                            if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S")) {
                                                strInsertFIELDS = strInsertFIELDS + ", " + ma.getAttributeCode() + "_FC";
                                                strInsertMARKER = strInsertMARKER + ", ?";
                                            }
                                        }
                                    }
                                }

                                if (bEntityExists) {
                                    strUpdateSQL = strUpdateHeadSQL + (strUpdateFIELDS.length() > 0 ? strUpdateFIELDS + "," : " ") +
                                        strUpdateFootSQL;
                                    D.ebug(D.EBUG_SPEW, "ODSMethods.updateODSTable:" + strUpdateSQL);
                                    ps = m_conODS.prepareStatement(strUpdateSQL);
                                } else {
                                    strInsertSQL = strInsertHEADSQL + strInsertFIELDSSQL + strInsertFIELDS + ")" +
                                        strInsertMARKERSQL + strInsertMARKER + ")";
                                    D.ebug(D.EBUG_SPEW, "ODSMethods.updateODSTable:" + strInsertSQL);
                                    ps = m_conODS.prepareStatement(strInsertSQL);
                                }

                                //Set the NLSid to be read in the profile so that we retrieve the correct translations
                                nlsCurrent = new NLSItem(iAllNLS[il], "Current translation");
                                m_prof.setReadLanguage(nlsCurrent);
                                D.ebug(D.EBUG_INFO,
                                       "Setting readLanguage for Entity :" + ei.getEntityType() + ":" + ei.getEntityID() + ": to :" +
                                       iAllNLS[il]);

                                iz = 1;

                                if (!bEntityExists) {

                                    ps.setInt(1, ei.getEntityID());
                                    ps.setInt(2, iAllNLS[il]); // Setting the ODS NLS here
                                    if (_eg.isRelator()) {
                                        EntityItem eip = (EntityItem) ei.getUpLink(0);
                                        EntityItem eic = (EntityItem) ei.getDownLink(0);

                                        ps.setInt(3, (eip == null ? -1 : eip.getEntityID()));
                                        ps.setInt(4, (eic == null ? -1 : eic.getEntityID()));

                                        ps.setString(5, m_strNow);
                                        ps.setString(6, m_strForever);
                                        iz = 7;
                                    } else {
                                        ps.setString(3, m_strNow);
                                        ps.setString(4, m_strForever);
                                        iz = 5;
                                    }
                                }

                                // Lets get the attributes here

                                for (int iy = 0; iy < _eg.getMetaAttributeCount(); iy++) {
                                    EANMetaAttribute ma = _eg.getMetaAttribute(iy);
                                    EANAttribute att = ei.getAttribute(ma.getAttributeCode());

                                    if (il == 0) {
                                        if (ma.getAttributeType().equals("F") &&
                                            !ODSServerProperties.isProdAttribute(m_strODSSchema, ma.getAttributeCode()) && att != null) {
                                            elMulti.put(att);
                                        }
                                    }

                                    if (ODSServerProperties.isProdAttribute(m_strODSSchema, ma.getAttributeCode())) {
                                        D.ebug(D.EBUG_INFO,
                                            "->\tProdAttribute:Found:" + ei.getEntityType() + ":" + ei.getEntityID() + ":" +
                                            ma.getAttributeCode() + ":" + ma.getAttributeType() + ":" + nlsCurrent);
                                        if (!ma.getAttributeType().equals("F") && att != null) {
                                            psTrans.setString(1, ei.getEntityType());
                                            psTrans.setInt(2, ei.getEntityID());
                                            psTrans.setString(3, att.getAttributeCode());
                                            psTrans.setString(4, getFirst254Char(att.getAttributeCode(), att.toString()));
                                            psTrans.setInt(5, iAllNLS[il]);
                                            if (att instanceof EANFlagAttribute) {
                                                EANFlagAttribute fa = (EANFlagAttribute) att;
                                                psTrans.setString(6, fa.getFirstActiveFlagCode());
                                            } else {
                                                psTrans.setString(6, "--");
                                            }
                                            psTrans.setString(7, m_strNow);
                                            psTrans.execute();

                                        } else if (att != null) {

                                            EANFlagAttribute fa = (EANFlagAttribute) att;
                                            EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) ma;
                                            Iterator it = fa.getPDHHashtable().keySet().iterator();

                                            psFlag.setString(1, m_strNow);
                                            psFlag.setString(2, ei.getEntityType());
                                            psFlag.setInt(3, ei.getEntityID());
                                            psFlag.setInt(4, iAllNLS[il]);
                                            psFlag.setString(5, fa.getAttributeCode());

                                            // Loop through and collect all turned on values.
                                            while (it.hasNext()) {
                                                String strFlagCode = (String) it.next();
                                                MetaFlag mf = mfa.getMetaFlag(strFlagCode);
                                                psFlag.setString(6, strFlagCode);
                                                psFlag.setInt(7, 1);
                                                psFlag.setString(8, mf.getLongDescription());
                                                psFlag.execute();
                                            }
                                        }
                                    }

                                    // Now lets build the table columns for the table row update
                                    if (includeColumn(ma)) {

                                        D.ebug(D.EBUG_SPEW,
                                            "AC#COUNT2#:" + iy + ":" + ma.getAttributeCode() + ":" + ma.getAttributeType() + ":IN:" +
                                            ma.isInteger() + ":DT:" + ma.isDate() + ":AL:" + ma.isAlpha() + ":" +
                                            (att == null ? "null" : att.toString()));

                                        if (ma.isDate() && ma.getAttributeType().equals("T")) {
                                            try {
                                                if (att != null) {
                                                    if (att.toString().trim().toUpperCase().equals("OPEN") ||
                                                        att.toString().trim().equals("Open")) {
                                                        D.ebug(D.EBUG_ERR,
                                                            "**DATE IN " + ma.getAttributeCode() + " -- Set to OPEN in PDH, setting to null in ODS");
                                                        ps.setString(iz, null);
                                                    } else {
                                                        ps.setString(iz, getODSString(att));
                                                    }
                                                } else {
                                                    ps.setString(iz, null);
                                                }
                                            }
                                            catch (Exception dx) {
                                                D.ebug(D.EBUG_ERR,
                                                    "**PARAMETER SET ERROR:  Date format Exception for (" + ei.getEntityType() +
                                                    ":" + ei.getEntityID() + ":" + ma.getAttributeCode() + ") - Value (" +
                                                    (att == null ? "null" : att.toString()) + ").  Using null." + ":" +
                                                    dx.getMessage());
                                                ps.setString(iz, null);
                                            }
                                        } else if (ma.isInteger() && !ma.isAlpha() && ma.getAttributeType().equals("T")) {
                                            try {
                                                if (att != null) {
                                                    ps.setInt(iz, Integer.valueOf(att.toString()).intValue());
                                                } else {
                                                    ps.setInt(iz, 0);
                                                }
                                            }
                                            catch (NumberFormatException nx) {
                                                D.ebug(D.EBUG_ERR,
                                                    "**PARAMETER SET ERROR:  Number format Exception for (" + ei.getEntityType() +
                                                    ":" + ei.getEntityID() + ":" + ma.getAttributeCode() + ") - Value (" +
                                                    (att == null ? "null" : att.toString()) + ").  Using zero(0) instead." + ":" +
                                                    nx.getMessage());
                                                ps.setInt(iz, 0);
                                            }
                                        } else {
                                            try {
                                                if (att != null) {
                                                    ps.setString(iz, getODSString(att));
                                                } else {
                                                    ps.setString(iz, null);
                                                }
                                            }
                                            catch (SQLException nx) {
                                                D.ebug(D.EBUG_ERR,
                                                    "**PARAMETER SET ERROR: String SQLError for (" + ei.getEntityType() + ":" +
                                                    ei.getEntityID() + ":" + ma.getAttributeCode() + ") - Value (" +
                                                    (att == null ? "null" : att.toString()) + ").  Using null instead." + ":" +
                                                    nx.getMessage());
                                                ps.setString(iz, null);
                                            }
                                        }

                                        iz++;

                                        // Lets look for the flag code option
                                        //
                                        if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S")) {
                                            if (att != null) {
                                                EANFlagAttribute fa = (EANFlagAttribute) att;
                                                try {
                                                    ps.setString(iz, fa.getFirstActiveFlagCode());
                                                }
                                                catch (SQLException nx) {
                                                    D.ebug(D.EBUG_ERR,
                                                        "**PARAMETER SET ERROR: FC SQLError for (" + ei.getEntityType() + ":" +
                                                        ei.getEntityID() + ":" + ma.getAttributeCode() + ") - Value (" +
                                                        (att == null ? "null" : att.toString()) + ").  Using null instead." + ":" +
                                                        nx.getMessage());
                                                    ps.setString(iz, null);
                                                }
                                            } else {
                                                try {
                                                    ps.setString(iz, null);
                                                }
                                                catch (SQLException nx) {
                                                    D.ebug(D.EBUG_ERR,
                                                        "**PARAMETER SET ERROR: FC SQLError for (" + ei.getEntityType() + ":" +
                                                        ei.getEntityID() + ":" + ma.getAttributeCode() + ") - Value (" +
                                                        (att == null ? "null" : att.toString()) + ").  Using null instead." + ":" +
                                                        nx.getMessage());
                                                    ps.setString(iz, null);
                                                }
                                            }
                                            iz++;
                                        }

                                    }

                                }

                                if (bEntityExists) { //this is the update to existing Entity
                                    ps.setString(iz, m_strNow);
                                    iz++;
                                    ps.setInt(iz, ei.getEntityID());
                                    iz++;
                                    ps.setInt(iz, iAllNLS[il]);
                                    D.ebug(D.EBUG_INFO,
                                           "Updating Entity(" + ei.getEntityType() + ":" + ei.getEntityID() + ") " + ei.toString());
                                } else {
                                    D.ebug(D.EBUG_INFO,
                                           "Inserting Entity(" + ei.getEntityType() + ":" + ei.getEntityID() + ") " + ei.toString());
                                }

                                try {
                                    ps.execute();
                                }
                                finally {
                                    ps.close();
                                    ps = null;
                                }
                            } // End of NLS Loop

                            // NLS cleanup IF geo enabled
                            if (ODSServerProperties.geoNLSMode(m_strODSSchema, _eg.getEntityType())) {
                                ResultSet rsNLS = null;
                                ReturnDataResultSet rdrsNLS = null;

                                try {
                                    psGetNLS.setInt(1, ei.getEntityID());
                                    rsNLS = psGetNLS.executeQuery();
                                    rdrsNLS = new ReturnDataResultSet(rsNLS);
                                }
                                finally {
                                    if (rsNLS != null) {
                                        rsNLS.close();
                                        rsNLS = null;
                                    }
                                }
                                for (int iNLS1 = 0; iNLS1 < rdrsNLS.getRowCount(); iNLS1++) {
                                    // if this nls exists for this entityid in the PDH, but is NOT a valid/current NLS then expire...
                                    int iNLS_pdh = rdrsNLS.getColumnInt(iNLS1, 0);
                                    boolean bValidNLS = false;
                                    for (int iNLS2 = 0; iNLS2 < iAllNLS.length; iNLS2++) {
                                        if (iAllNLS[iNLS2] == iNLS_pdh) {
                                            bValidNLS = true;
                                            break;
                                        }
                                    }
                                    if (!bValidNLS) {
                                        D.ebug(D.EBUG_INFO,
                                               "Removing Invalid NLS:" + iNLS_pdh + " for " + strEntityType + ":" + ei.getEntityID() +
                                               "...");
                                        if (ODSServerProperties.hasPublishFlag(m_strODSSchema, strEntityType)) {
                                            D.ebug(D.EBUG_DETAIL,
                                                "ODSMethods.updateODSTable:UnPublishing Invlaid NLS:" + iNLS_pdh + " for Entity:" +
                                                ei.getEntityType() + ":EID:" + ei.getEntityID());
                                            psDPUBFNLS.setInt(1, ei.getEntityID());
                                            psDPUBFNLS.setInt(2, iNLS_pdh);
                                            psDPUBFNLS.execute();
                                        } else {
                                            D.ebug(D.EBUG_DETAIL,
                                                "ODSMethods.updateODSTable:Deleting NLS:" + iNLS_pdh + " for Entity:" +
                                                ei.getEntityType() + ":EID:" + ei.getEntityID());
                                            psDELNLS.setInt(1, ei.getEntityID());
                                            psDELNLS.setInt(2, iNLS_pdh);
                                            psDELNLS.execute();
                                        }
                                    }
                                }

                            }
                            // END NLS cleanup

                            //
                            // This is normal flag processing that is not considered one of the S&F attributes
                            // this nlsid = 0
                            if (elMulti.size() > 0) {
                                psFlag.setString(1, m_strNow);
                                psFlag.setString(2, strEntityType);
                                psFlag.setInt(3, ei.getEntityID());
                                psFlag.setInt(4, 1);
                                D.ebug(D.EBUG_INFO, "->\tMultiFlags:Starting:" + ei.getEntityType() + ":" + ei.getEntityID());
                                for (int iy = 0; iy < elMulti.size(); iy++) {
                                    EANFlagAttribute fa = (EANFlagAttribute) elMulti.getAt(iy);
                                    EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) fa.getMetaAttribute();

                                    Iterator it = fa.getPDHHashtable().keySet().iterator();

                                    String strAttributeCode = fa.getAttributeCode();
                                    psFlag.setString(5, strAttributeCode);

                                    // Loop through and collect all turned on values.
                                    while (it.hasNext()) {
                                        String strFlagCode = (String) it.next();
                                        MetaFlag mf = mfa.getMetaFlag(strFlagCode);
                                        psFlag.setString(6, strFlagCode);
                                        psFlag.setInt(7, 0);
                                        psFlag.setString(8, mf.getLongDescription());
                                        psFlag.execute();
                                    }
                                }
                                D.ebug(D.EBUG_INFO, "->\tMultiFlags:Finishing:" + ei.getEntityType() + ":" + ei.getEntityID());
                            }

                            // Lets take care of the fkey here
                            if (bFKEY) {

                                int iID1 = ( (EntityItem) ei.getUpLink(0)).getEntityID();
                                int iID2 = ( (EntityItem) ei.getDownLink(0)).getEntityID();

                                psFKey.setInt(1, iID1);
                                psFKey.setString(2, m_strNow);
                                psFKey.setInt(3, iID2);
                                D.ebug(D.EBUG_INFO,
                                       "->\tFKEY:Starting:" + ei.getEntityType() + ":" + ei.getEntityID() + ":" + iID1 + " --> " +
                                       iID2);
                                psFKey.execute();
                                D.ebug(D.EBUG_INFO,
                                       "->\tFKEY:Finishing:" + ei.getEntityType() + ":" + ei.getEntityID() + ":" + iID1 + " --> " +
                                       iID2);
                            }

                            // if this was a relator table.. lets see if it qualifies for the PRODATTRELATOR INSERT!
                            //
                            if (bATTREL) {

                                int iEntity1ID = ( (EntityItem) ei.getUpLink(0)).getEntityID();
                                int iEntity2ID = ( (EntityItem) ei.getDownLink(0)).getEntityID();

                                strEntity1Type = _eg.getEntity1Type();
                                strEntity2Type = _eg.getEntity2Type();

                                psATTR.setInt(1, iEntity1ID);
                                psATTR.setString(2, strEntity1Type);
                                psATTR.setInt(3, iEntity2ID);
                                psATTR.setString(4, strEntity2Type);
                                psATTR.setString(5, m_strNow);
                                D.ebug(D.EBUG_INFO,
                                    "->\tProdAttRelator:Starting:" + ei.getEntityType() + ":" + ei.getEntityID() + ":E1:" +
                                    strEntity1Type + "-" + iEntity1ID + ":E2" + strEntity2Type + "-" + iEntity2ID);
                                try {
                                    psATTR.execute();
                                }
                                catch (SQLException x) {
                                    D.ebug(D.EBUG_INFO, "->\tProdAttRelator:Error:" + x.getMessage());
                                }
                                D.ebug(D.EBUG_INFO,
                                    "->\tProdAttRelator:Finishing:" + ei.getEntityType() + ":" + ei.getEntityID() + ":E1:" +
                                    strEntity1Type + "-" + iEntity1ID + ":E2" + strEntity2Type + "-" + iEntity2ID);
                            }

                            // O.K.  if we need to cross reference post after everything has been added updated

                            if (bCROSSREF) {
                                if (bATTREL || _eg.isRelator()) {
                                    updateValFrom(_eg.getEntity1Type(), ei.getUpLink(0).getEntityID());
                                } else if (ODSServerProperties.isCrossPostEntity(m_strODSSchema, _eg.getEntityType())) {
                                    crossPostII(ei.getEntityType(), ei.getEntityID());
                                } else {
                                    crossPostValFromCheck(_eg.getEntityType(), ei.getEntityID(), 0);
                                }
                            }

                            //
                            // Lets sync the foriegn Key here ..
                            //
                            if (bFKEYCHECK) {
                                syncFKey(_eg.getEntityType(), ei.getEntityID());
                            }

                        }

                    }
                    catch (SQLException ex) {
                        String strErr = "";
                        D.ebug(D.EBUG_ERR,
                               "** INSERTION ERROR *** Skipping Entity (" + ei.getEntityType() + ":" + ei.getEntityID() + ") Error is: " +
                               ex.getMessage());
                        for (int ierr = 0; ierr < ei.getAttributeCount(); ierr++) {
                            EANAttribute eanAtt = ei.getAttribute(ierr);
                            strErr = strErr + eanAtt.getAttributeCode();
                            strErr = strErr + ":" + eanAtt.toString() + NEW_LINE;
                        }
                        D.ebug(D.EBUG_ERR, "Values are ******" + NEW_LINE + strErr);
                    }

                }

                m_conODS.commit();
                _eg.resetEntityItem();

                if (eg1 != null) {
                    eg1.resetEntityItem();
                }
                if (eg2 != null) {
                    eg2.resetEntityItem();
                }
                System.gc();

                // O.k. Lets bump up the intervals
                iStartID = iEndID + 1;
                iEndID = (iEndID + m_iChuckSize > iMaxID ? iMaxID : iEndID + m_iChuckSize);
                bLoop = iStartID <= iMaxID;

            }
        }
        finally {
            // Close the stuff out
            psFlag.close();
            psFlag = null;
            psTrans.close();
            psTrans = null;
            psFlagDel.close();
            psFlagDel = null;
            psProdAttrDel.close();
            psProdAttrDel = null;
            psFKey.close();
            psFKey = null;
            psATTR.close();
            psATTR = null;
            psDELATTR.close();
            psDELATTR = null;
            psDEL.close();
            psDEL = null;
            psDPUBF.close();
            psDPUBF = null;
            psDLOG.close();
            psDLOG = null;
            psDFKEY.close();
            psDFKEY = null;
            psDFKEYPUB.close();
            psDFKEYPUB = null;
            psGetNLS.close();
            psGetNLS = null;
            psDELNLS.close();
            psDELNLS = null;
            psDPUBFNLS.close();
            psDPUBFNLS = null;
            psExistRel.close();
            psExistRel = null;
        }

        return;
    }

    /**
     * Convert from yyyy-MM-dd hh:mm:ss.ffffff to yyyy-MM-dd-hh:mm:ss.ffffff
     */
    private String getISO(String _s1) {
        StringBuffer sb = new StringBuffer();
        StringTokenizer st = new StringTokenizer(_s1, " ");
        sb.append(st.nextToken());
        sb.append("-");
        sb.append(st.nextToken().replace(':', '.'));
        return sb.toString();
    }

    /**
     * Checks for and updates any flag records which have had their MetaDescriptions changed within the given interval.
     * Hits tables:
     * 1) PRODATTR
     * 2) Relevant Entity's Base table (if NON-MULTI-FLAG only)
     * 3) FLAG table (if MULTI-FLAG only)
     * 4) METAFLAGTABLE
     *
     * @param _mel
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    protected void updateDescriptionChanges(MetaEntityList _mel) throws SQLException, MiddlewareException {

        DescriptionChangeList dcList = m_dbPDH.getDescriptionChangeList(m_prof, DescriptionChangeList.FLAG_CHANGES,
            getISO(m_strLastRun), m_strNow);
        D.ebug(D.EBUG_INFO, "updateDescriptionChanges: updating " + dcList.getDescriptionChangeItemCount() + " descriptions...");

        for (int iDC = 0; iDC < dcList.getDescriptionChangeItemCount(); iDC++) {
            DescriptionChangeItem dci = dcList.getDescriptionChangeItem(iDC);
            String strEntityType = dci.getEntityType();
            String strAttributeCode = dci.getAttributeCode();
            int iNLSID = dci.getNLSID();
            String strFlagCode = dci.getFlagCode();

            // dont worry, this should be a 'complete' EG w/ atts at this point
            EntityGroup eg = _mel.getEntityGroup(strEntityType);
            EANMetaAttribute ema = null;

            if (eg == null || !ODSServerProperties.includeTable(m_strODSSchema, strEntityType)) {
                D.ebug(D.EBUG_INFO,
                    "updateDescriptionChanges: skipping Entity " + strEntityType + ". Not found in List. (AttributeCode=" +
                    strAttributeCode + ", FlagCode=" + strFlagCode + ", NLSID=" + iNLSID + ")");
                continue;
            }
            ema = eg.getMetaAttribute(strAttributeCode);
            if (ema == null || !ODSServerProperties.isAttributeSubset(m_strODSSchema, strEntityType, strAttributeCode)) {
                D.ebug(D.EBUG_INFO,
                    "updateDescriptionChanges: skipping Attribute " + strEntityType + "." + strAttributeCode + ". Not an include Attribute. (FlagCode=" +
                    strFlagCode + ", NLSID=" + iNLSID + ")");
                continue;
            }
            try {
                updateDescriptionChangeForItem(dci, (EANMetaFlagAttribute) ema);
            }
            catch (SQLException sqlExc) {
                D.ebug(D.EBUG_ERR,
                       "updateDescriptionChanges: ERROR at updateDescriptionChangeForItem:" + strEntityType + ":" + strAttributeCode +
                       ":" + strFlagCode + ":MSG:" + sqlExc.getMessage());
            }
        }
    }

    private void updateDescriptionChangeForItem(DescriptionChangeItem _dcItem, EANMetaFlagAttribute _ema) throws SQLException,
        MiddlewareException {

        int iLength = 128;
        boolean bNewRecord = false;

        String strTruncatedLD = null;
        String strNewShortDescription = null;
        String strUpdateProdAttrSQL = null;
        String strUpdateFlagSQL = null;
        String strUpdateMFTSQL = null;
        String strUpdateEntitySQL = null;
        String strInsertMFTSQL = null;
        String strQueryMFTSQL = null;
        String strMFTSQL = null;
        String strCrossPostSQL = null;

        Statement stUpdateProdAttr = null;
        Statement stQueryMFT = null;
        Statement stUpdateMFT = null;
        Statement stUpdateFlag = null;
        ResultSet rsMFT = null;
        ReturnDataResultSet rdrsMFT = null;
        Statement stCrossPost = null;
        Statement stUpdateEntity = null;
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        boolean bMultiFlag = (!ODSServerProperties.isMultiFlag(m_strODSSchema) && _ema.getAttributeType().equals("F"));
        String strEntityType = _dcItem.getEntityType();
        String strAttributeCode = _dcItem.getAttributeCode();
        String strFlagCode = _dcItem.getFlagCode();
        String strNewLongDescription = _dcItem.getNewLDescription();

        int iNLSID = _dcItem.getNLSID();

        if (!bMultiFlag) {
            iLength = ODSServerProperties.getVarCharColumnLength();
            if (_ema.getOdsLength() > 0) {
                iLength = _ema.getOdsLength();
            }
        }

        strTruncatedLD = (strNewLongDescription.length() > iLength ? strNewLongDescription.substring(0, iLength) :
                          strNewLongDescription);
        strNewShortDescription = _dcItem.getNewSDescription();

        strUpdateProdAttrSQL = "UPDATE " + m_strODSSchema + ".PRODATTRIBUTE " + " SET " + "   ValFrom = '" + m_strNow + "' " +
            " , AttributeValue = '" + strNewLongDescription + "' " + " WHERE " + "     EntityType = '" + strEntityType + "' " +
            " AND AttributeCode = '" + strAttributeCode + "' " + " AND FlagCode = '" + strFlagCode + "' " + " AND NLSID = " +
            iNLSID;

        strUpdateFlagSQL = "UPDATE " + m_strODSSchema + ".FLAG " + " SET " + "   ValFrom = '" + m_strNow + "' " +
            " , FlagDescription = '" + strNewLongDescription + "' " + " WHERE " + "     EntityType = '" + strEntityType + "' " +
            " AND AttributeCode = '" + strAttributeCode + "' " + " AND FlagCode = '" + strFlagCode + "' " + " AND NLSID = " +
            iNLSID;

        strUpdateEntitySQL = "UPDATE " + m_strODSSchema + "." + strEntityType + " SET " + "   ValFrom = '" + m_strNow + "' " +
            " , " + strAttributeCode + " = '" + strTruncatedLD + "' " + " WHERE " + strAttributeCode + "_FC = '" + strFlagCode +
            "' " + " AND NLSID = " + iNLSID;

        strUpdateMFTSQL = "UPDATE " + m_strODSSchema + ".METAFLAGTABLE " + " SET " + "   ValFrom = '" + m_strNow + "' " +
            " , SHORTDESCRIPTION = '" + strNewShortDescription + "' " + " , LONGDESCRIPTION = '" + strNewLongDescription + "' " +
            " WHERE " + " ATTRIBUTECODE = '" + strAttributeCode + "' " + " AND ATTRIBUTEVALUE = '" + strFlagCode + "' " +
            " AND NLSID = " + iNLSID;

        strInsertMFTSQL = "INSERT INTO " + m_strODSSchema + ".METAFLAGTABLE " + " VALUES( " + " '" + m_strNow + "' " + ",'" +
            strAttributeCode + "' " + ", " + iNLSID + " " + ",'" + strFlagCode + "' " + ",'" + strNewShortDescription + "' " + ",'" +
            strNewLongDescription + "') ";

        strQueryMFTSQL = " SELECT COUNT(*) FROM " + m_strODSSchema + ".METAFLAGTABLE WHERE " + " ATTRIBUTECODE = '" +
            strAttributeCode + "'  AND " + " ATTRIBUTEVALUE = '" + strFlagCode + "'";

        try {
            D.ebug(D.EBUG_INFO,
                   "updateDescriptionChanges:" + strEntityType + ":" + strAttributeCode + ":" + strFlagCode + ":" + iNLSID + ":" +
                   strNewLongDescription);
            D.ebug(D.EBUG_INFO, "updateDescriptionChanges:strUpdateProdAttrSQL:" + strUpdateProdAttrSQL);

            stUpdateProdAttr = m_conODS.createStatement();

            stUpdateProdAttr.executeUpdate(strUpdateProdAttrSQL);
        }
        finally {
            if (stUpdateProdAttr != null) {
                stUpdateProdAttr.close();
                stUpdateProdAttr = null;
            }
        }
        if (bMultiFlag) {
            D.ebug(D.EBUG_INFO, "updateDescriptionChanges:MULTI-FLAG:strUpdateFlagSQL:" + strUpdateFlagSQL);
            try {
                stUpdateFlag = m_conODS.createStatement();
                stUpdateFlag.executeUpdate(strUpdateFlagSQL);
            }
            finally {
                if (stUpdateFlag != null) {
                    stUpdateFlag.close();
                    stUpdateFlag = null;
                }
            }
        } else {
            D.ebug(D.EBUG_INFO, "updateDescriptionChanges:NON-MULTI-FLAG:strUpdateEntitySQL:" + strUpdateEntitySQL);
            try {
                stUpdateEntity = m_conODS.createStatement();
                stUpdateEntity.executeUpdate(strUpdateEntitySQL);
            }
            finally {
                if (stUpdateEntity != null) {
                    stUpdateEntity.close();
                    stUpdateEntity = null;
                }
            }
        }

        D.ebug(D.EBUG_INFO, "updateDescriptionChanges:strQueryMFTSQL:" + strQueryMFTSQL);

        try {
            stQueryMFT = m_conODS.createStatement();
            rsMFT = stQueryMFT.executeQuery(strQueryMFTSQL);
            rdrsMFT = new ReturnDataResultSet(rsMFT);
        }
        finally {
            if (rsMFT != null) {
                rsMFT.close();
                rsMFT = null;
            }
            if (stQueryMFT != null) {
                stQueryMFT.close();
                stQueryMFT = null;
            }
        }

        bNewRecord = (rdrsMFT.getColumnInt(0, 0) == 0);
        strMFTSQL = (bNewRecord ? strInsertMFTSQL : strUpdateMFTSQL);

        D.ebug(D.EBUG_INFO, "updateDescriptionChanges:strMFTSQL:" + strMFTSQL);

        try {
            stUpdateMFT = m_conODS.createStatement();
            stUpdateMFT.executeUpdate(strMFTSQL);
        }
        finally {
            if (stUpdateMFT != null) {
                stUpdateMFT.close();
                stUpdateMFT = null;
            }
        }

        // if multi --> we need to check this from flag table.
        if (bMultiFlag) {
            strCrossPostSQL = "SELECT EntityID FROM " + m_strODSSchema + ".FLAG " + " WHERE " + " ENTITYTYPE = '" + strEntityType +
                "' AND " + " ATTRIBUTECODE = '" + strAttributeCode + "' AND " + " FLAGCODE = '" + strFlagCode + "' AND " +
                " NLSID = " + iNLSID;
        } else {
            strCrossPostSQL = "SELECT EntityID FROM " + m_strODSSchema + "." + strEntityType + " WHERE " + strAttributeCode +
                "_FC = '" + strFlagCode + "' AND " + " NLSID = " + iNLSID;
        }

        try {

            try {
                stCrossPost = m_conODS.createStatement();
                rs = stCrossPost.executeQuery(strCrossPostSQL);
                rdrs = new ReturnDataResultSet(rs);
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stCrossPost != null) {
                    stCrossPost.close();
                    stCrossPost = null;
                }
            }

            for (int i = 0; i < rdrs.getRowCount(); i++) {
                int iEntityID = rdrs.getColumnInt(i, 0);
                D.ebug(D.EBUG_INFO, "updateDescriptionChanges:Checking Cross Post for Entity:" + strEntityType + ":" + iEntityID);
                crossPostValFromCheck(strEntityType, iEntityID, iNLSID);
            }
        }
        catch (SQLException sqlExc) {
            D.ebug(D.EBUG_ERR,
                "updateDescriptionChanges: ERROR at updateDescriptionChangeForItem check cross post entities:" + strEntityType +
                ":" + strAttributeCode + ":" + strFlagCode + ":MSG:" + sqlExc.getMessage());
        }

        return ;
    }

    private boolean hasRelevantAttributeChanges(EntityItem _ei) {
        D.ebug(D.EBUG_DETAIL, " 1 hasRelevantAttributeChanges for:" + _ei.getKey() + " isActive?" + _ei.isActive());
        //
        boolean bHasChanges = false;
        if (!ODSServerProperties.hasAttributeSubset(m_strODSSchema, _ei.getEntityType())) {
            // return true b/c this implies that one of the attributes were changed
            //D.ebug(D.EBUG_SPEW,"hasRelevantAttributeChanges  NOT an attribute subset ... return true");
            // ok, since we are NOT an attribute subset entity, we need to check any rollup attributes real quick here if applicable.
            if (ODSServerProperties.performRollupMultiAttributes(m_strODSSchema)) {
                for (int i = 0; i < _ei.getEntityGroup().getMetaAttributeCount(); i++) {
                    EANMetaAttribute ma = _ei.getEntityGroup().getMetaAttribute(i);
                    EANAttribute att = _ei.getAttribute(ma.getAttributeCode());
                    //
                    if (ODSServerProperties.isRollupAttribute(m_strODSSchema, _ei.getEntityType(), ma.getAttributeCode())
                        /*&& att == null*/) {
                        D.ebug(D.EBUG_SPEW,
                               "hasRelevantAttributeChanges, att is null for " + _ei.getKey() + ":" + ma.getAttributeCode());
                    }

                    //
                    //if (att == null) {
                    //	continue;
                    //}
                    checkRollupAttribute(_ei, ma);
                }
            }
            //
            return true;
        }
        for (int i = 0; i < _ei.getEntityGroup().getMetaAttributeCount(); i++) {
            EANAttribute att = null;
            String strValFrom = null;

            EANMetaAttribute ma = _ei.getEntityGroup().getMetaAttribute(i);
            if (isDerivedEntityID(ma)) {
                //D.ebug(D.EBUG_SPEW,"  hasRelevantAttributeChanges is a derived att, continue...");
                continue;
            }
            att = _ei.getAttribute(ma.getAttributeCode());
            if (att == null) {
                D.ebug(D.EBUG_SPEW,
                       "hasRelevantAttributeChanges att == null for " + _ei.getKey() + "." + ma.getAttributeCode() + ", continue...");
                continue;
            }
            //D.ebug(D.EBUG_DETAIL, " 2 hasRelevantAttributeChanges for:" + _ei.getKey() + "," + att.getKey());

            strValFrom = att.getValFrom();
            // if valfrom is null (was never set), than we came from Init --> we want everything
            if (strValFrom == null) {
                D.ebug(D.EBUG_SPEW,
                       "hasRelevantAttributeChanges strValFrom is null for " + _ei.getKey() + "." + ma.getAttributeCode() +
                       " return true");
                return true;
            }
            if (!ODSServerProperties.isAttributeSubset(m_strODSSchema, _ei.getEntityType(), att.getAttributeCode())) {
                // we dont care about any changes to this guy....
                D.ebug(D.EBUG_SPEW,
                    "hasRelevantAttributeChanges: skipping check of attribute:" + att.getAttributeCode() + ":for EI:" +
                    _ei.getEntityType() + ":" + _ei.getEntityID() + "(not in subset)");
                continue;
            }
            D.ebug(D.EBUG_SPEW, "hasRelevantAttributeChanges, strValFrom = " + strValFrom);
            if (strValFrom.compareTo(m_strLastRun) >= 0) {

                //GAB 042605: now we must continue the loop, so that we can find any attributes to rollup

                // found a relevant change!
                //D.ebug(
                //    D.EBUG_SPEW,
                //    "hasRelevantAttributeChanges: found a change in attribute:"
                //        + att.getAttributeCode()
                //        + ":for EI:"
                //        + _ei.getEntityType()
                //        + ":"
                //        + _ei.getEntityID()
                //        + ". We will process this entity.");
                // check even on deletes ... re this is just a tip-off htat we need to refresh the image for this root entity!
                D.ebug(D.EBUG_SPEW, "hasRelevantAttributeChanges checkingRollupAttributes");
                checkRollupAttribute(_ei, ma);
                bHasChanges = true;
            } else {
                D.ebug(D.EBUG_SPEW, "hasRelevantAttributeChanges NOT checkRollupAttributes");
            }
        }
        // no changes we care about in this entity...
        if (!bHasChanges) {
            D.ebug(D.EBUG_SPEW,
                   "hasRelevantAttributeChanges: found no relevant attribute changes for entity:" + _ei.getEntityType() + ":" +
                   _ei.getEntityID() + ". We will skip processing this entity.");
        } else {
            D.ebug(D.EBUG_SPEW,
                "hasRelevantAttributeChanges: found a change " + ":for EI:" + _ei.getEntityType() + ":" + _ei.getEntityID() +
                ". We will process this entity.");
        }
        return bHasChanges;
    }

    /**
     * Check for the existence of rollup attributes. Any of these which are found, must be rolles up and
     *  placed in the root table after we are done.
     */
    private final void checkRollupAttribute(EntityItem _ei, EANMetaAttribute _att) {
        D.ebug(D.EBUG_SPEW,
               "checkRollupAttribute for " + _ei.getKey() + "." + _att.getAttributeCode() + ", isActive?" + _ei.isActive());
        if (!ODSServerProperties.isRollupAttribute(m_strODSSchema, _ei.getEntityType(), _att.getAttributeCode())) {
            return;
        }
        D.ebug(D.EBUG_SPEW,
               "Found Rollup Attribute:" + _ei.getEntityType() + "." + _att.getAttributeCode() + "(EID=" + _ei.getEntityID() +
               ") isActive?" + _ei.isActive());
        // key is: etype:attcode:eid
        String strKey = _ei.getEntityType() + ":" + _att.getAttributeCode() + ":" + _ei.getEntityID();
        m_vctRollupAttrs.addElement(strKey);
        return;
    }

    protected final void initMultiAttributes() throws SQLException {
        Stopwatch sw1 = new Stopwatch();
        sw1.start();

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        //
        PreparedStatement ps = null;

        // this SQL should match the net SQL below. remember this, if anything is changed!!!
        /*
                String strSQL1  = "select distinct 'OF', pad.entity1id, f.nlsid, rtrim('WW_' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join   eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'OF' ";
                String strSQL2  = "select distinct 'OF', of.entityid, of.nlsid, rtrim('WW_PSG' || f.attributecode), (select rtrim(longdescription) from    eccm.metaflagtable where        attributecode = f.attributecode and ATTRIBUTEVALUE= f.flagcode and nlsid =of.nlsid) FROM        eccm.of of join eccm.flag f on f.entitytype = 'OF' and f.entityid = of.entityid and f.attributecode in ('OFAPPROVALS_CERTS') and f.nlsid = of.nlsid  ";
                String strSQL3  = "select distinct 'OF', ofsbb.ID1, f.nlsid, rtrim( 'WW_' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from      eccm.ofsbb ofsbb join   eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = ofsbb.id2 join      eccm.flag f on f.entitytype = pr.entity2type and f.entityid = pr.entity2id and f.sfvalue = 1 where      ofsbb.nlsid = 1  ";
                String strSQL4  = "select distinct 'VAR', pad.entity1id, f.nlsid, rtrim('WW_' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join    eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'VAR'  ";
                String strSQL5  = "select distinct 'VAR', varsbb.ID1, f.nlsid, rtrim('WW_' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from    eccm.varsbb varsbb join eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = varsbb.id2 join     eccm.flag f on f.entitytype = pr.entity2type and f.entityid = pr.entity2id and f.sfvalue = 1 where      varsbb.nlsid = 1  ";
                String strSQL6  = "select distinct 'OF', ofport.id1, p.nlsid, case p.PORTCATEGORY_FC when '11108' THEN 'WW_PSGSERIALPORTTYPE_PL' when '11107' THEN 'WW_PSGPARA_PORTTYPE_PL' when '11106' THEN 'WW_PSGEXPANPORTTYPE' when '11105' THEN 'WW_PSGAUDIOPORTNAME' end case, p.PortType from      eccm.ofport ofport join eccm.port p on p.entityid = ofport.id2 where    p.portcategory_fc in ('11108','11107','11106','11105')  ";
                String strSQL7  = "select distinct 'OF', ofsbb.id1, p.nlsid, case p.PORTCATEGORY_FC when '11108' THEN 'WW_PSGSERIALPORTTYPE_PL' when '11107' THEN 'WW_PSGPARA_PORTTYPE_PL' when '11106' THEN 'WW_PSGEXPANPORTTYPE' when '11105' THEN 'WW_PSGAUDIOPORTNAME' end case, p.PortType from eccm.ofsbb ofsbb join   eccm.sbbport sbbport on sbbport.id1 = ofsbb.id2 join eccm.port p on p.entityid = sbbport.id2 where   p.portcategory_fc in ('11108','11107','11106','11105')  ";
                String strSQL8  = "select distinct 'VAR', varsbb.id1, p.nlsid, case p.PORTCATEGORY_FC when '11108' THEN 'WW_PSGSERIALPORTTYPE_PL' when '11107' THEN 'WW_PSGPARA_PORTTYPE_PL' when '11106' THEN 'WW_PSGEXPANPORTTYPE' when '11105' THEN 'WW_PSGAUDIOPORTNAME' end case, p.PortType from eccm.varsbb varsbb join eccm.sbbport sbbport on sbbport.id1 = varsbb.id2 join eccm.port p on p.entityid = sbbport.id2 where   p.portcategory_fc in ('11108','11107','11106','11105')  ";
                String strSQL9  = "select distinct 'OF', ofport.id1, p.nlsid, 'WW_PSGEXPANSIONPORTTYPE', p.PortType from   eccm.ofport ofport join eccm.port p on p.entityid = ofport.id2 where    p.portcategory_fc in ('11106')  ";
                String strSQL10 = "select distinct 'OF', ofsbb.id1, p.nlsid, 'WW_PSGEXPANSIONPORTTYPE', p.PortType from    eccm.ofsbb ofsbb join   eccm.sbbport sbbport on sbbport.id1 = ofsbb.id2 join    eccm.port p on p.entityid = sbbport.id2 where p.portcategory_fc in ('11106')  ";
                String strSQL11 = "select distinct 'VAR', varsbb.id1, p.nlsid, 'WW_PSGEXPANSIONPORTTYPE', p.PortType from  eccm.varsbb varsbb join eccm.sbbport sbbport on sbbport.id1 = varsbb.id2 join   eccm.port p on p.entityid = sbbport.id2 where p.portcategory_fc in ('11106')  ";
                String strSQL12 = "select distinct 'OF', ofsec.id1, s.nlsid, 'WW_PSGSECURITYIFR', s.SecType from   eccm.ofsec ofsec join eccm.sec s on s.entityid = ofsec.id2  ";
                String strSQL13 = "select distinct 'OF', ofsbb.id1, s.nlsid, 'WW_PSGSECURITYIFR', s.SecType from    eccm.ofsbb ofsbb join  eccm.sbbsec sbbsec on sbbsec.id1 = ofsbb.id2 join eccm.sec s on s.entityid = sbbsec.id2  ";
                String strSQL14 = "select distinct 'VAR', varsbb.id1, s.nlsid,'WW_PSGSECURITYIFR', s.SecType from  eccm.varsbb varsbb join eccm.sbbsec sbbsec on sbbsec.id1 = varsbb.id2 join   eccm.sec s on s.entityid = sbbsec.id2  ";
                String strSQL15 = "select distinct 'CSOL', csol.entityid, f.nlsid, 'CT_PSGWARRANTYTYPE', rtrim(f.flagdescription) from     eccm.csol csol join        eccm.csolwar csolwar on csolwar.id1 = csol.entityid and csolwar.nlsid = 1 join  eccm.flag f on f.entitytype = 'WAR' and f.entityid = csolwar.id2 and f.sfvalue = 1 and f.nlsid = csol.nlsid and f.attributecode='WARRANTYTYPE'  ";
                String strSQL16 = "select distinct 'CVAR', cvar.entityid, f.nlsid,'CT_PSGWARRANTYTYPE', rtrim(f.flagdescription) from      eccm.cvar cvar join eccm.cvarwar cvarwar on cvarwar.id1 = cvar.entityid and cvarwar.nlsid = 1 join  eccm.flag f on f.entitytype = 'WAR' and f.entityid = cvarwar.id2 and f.sfvalue = 1 and f.nlsid = cvar.nlsid and f.attributecode='WARRANTYTYPE'  ";
                String strSQL17 = "select distinct 'CVAR', cvar.entityid, f.nlsid,'CT_PSGWSSPEED', rtrim(f.flagdescription) from   eccm.cvar cvar join     eccm.cvarsbb cvarsbb on cvarsbb.id1= cvar.entityid join         eccm.sbbws sbbws on sbbws.id1 = cvarsbb.id2 and sbbws.nlsid = 1 join    eccm.flag f on f.entitytype ='WS' and f.entityid = sbbws.id2 and f.sfvalue = 1  and f.nlsid=cvar.nlsid and f.attributecode='WSSPEED'  ";
                String strSQL18 = "select distinct 'CVAR', cvar.entityid, f.nlsid , concat('CT_PSG', f.attributecode), rtrim(f .flagdescription) from       eccm.cvar cvar join     eccm.cvarsbb cvarsbb on cvarsbb.id1= cvar.entityid join         eccm.sbbfm sbbfm on sbbfm.id1 = cvarsbb.id2 and sbbfm.nlsid = 1 join    eccm.flag f on f.entitytype ='FM' and f.entityid = sbbfm.id2 and f.sfvalue = 1 and f.nlsid=cvar.nlsid and f.attributecode in ('DATARATEFAX','DATARATEMODEM') ";
                String strSQL19 = "select distinct 'OF', ofpsl.id1, psl.nlsid, 'WW_MDCARDSLOT', rtrim( char(psl.SLOTS_TOTAL)) || '(' || rtrim( char(pslavail.SLOTS_AVAIL )) || ') ' || psl.SLOT_TYPE from eccm.ofpsl ofpsl join eccm.psl psl on ofpsl.id2 = psl.entityid join eccm.ofpslavail ofpslavail on ofpslavail.id1 = ofpsl.id1 join eccm.pslavail pslavail on ofpslavail.id2 = pslavail.entityid where pslavail.nlsid = psl.nlsid and pslavail.slotavailtype = psl.slotavailtype  ";
                String strSQL20 = "select distinct 'VAR', varsbb.id1, psl.nlsid, 'WW_MDCARDSLOT', rtrim( char(psl.SLOTS_TOTAL)) || '(' || rtrim( char(pslavail.SLOTS_AVAIL )) || ') ' || psl.SLOT_TYPE from eccm.varsbb varsbb join eccm.sbbpsl spsl on varsbb.id2 = spsl.id1 join eccm.psl psl on spsl.id2 = psl.entityid join eccm.varpslavail varpslavail on varpslavail.id1 = varsbb.id1 join eccm.pslavail pslavail on varpslavail.id2 = pslavail.entityid where pslavail.nlsid = psl.nlsid and pslavail.slotavailtype = psl.slotavailtype ";
         */
        // CDR.CDROPTITYPE
        String strSQL1 = "select distinct 'OF', pad.entity1id, cdr.nlsid,'WWPSGCDROPTITYPE', cdr.CDROPTITYPE from eccm.prodattrelator pad join   eccm.CDR cdr on pad.entity2type = 'CDR' and cdr.entityid = pad.entity2id where    pad.entity1type = 'OF' and CDR.nlsid = (?) and 999 <> (?) order by 1,2,4,3";

        // DD
        String strSQL2 = "select distinct 'OF', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join   eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'OF' and f.entitytype = 'DD' and f.attributecode = 'TECHNOLOGYFEATURES' and f.nlsid = (?) and 999 <> (?) order by 1,2,4,3";

        // FM
        String strSQL3 = "select distinct 'OF', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join   eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'OF' and f.entitytype = 'FM' and f.attributecode in ('DATARATEFAX','DATARATEMODEM') and f.nlsid = (?)  " + " union select distinct 'OF', ofsbb.ID1, f.nlsid, rtrim( '' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from      eccm.ofsbb ofsbb join   eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = ofsbb.id2 join      eccm.flag f on f.entitytype = pr.entity2type and f.entityid = pr.entity2id and f.sfvalue = 1 where      ofsbb.nlsid = 1 and f.entitytype = 'FM' and f.attributecode in ('DATARATEFAX','DATARATEMODEM') and f.nlsid = (?) order by 1,2,4,3";

        // HDC
        String strSQL4 = "select distinct 'OF', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join   eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'OF' and f.entitytype = 'HDC' and f.attributecode = 'CONTROLLERNAME' and f.nlsid = (?)  " + " union select distinct 'OF', ofsbb.ID1, f.nlsid, rtrim( '' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from      eccm.ofsbb ofsbb join   eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = ofsbb.id2 join      eccm.flag f on f.entitytype = pr.entity2type and f.entityid = pr.entity2id and f.sfvalue = 1 where      ofsbb.nlsid = 1 and f.entitytype = 'HDC' and f.attributecode = 'CONTROLLERNAME' and f.nlsid = (?) order by 1,2,4,3";

        // MON
        String strSQL5 = "select distinct 'OF', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join   eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'OF' and f.entitytype = 'MON' and f.attributecode = 'TILTSWIVEL' and f.nlsid = (?) and 999 <> (?) order by 1,2,4,3";

        // NIC.DATARATE
        String strSQL6 = "select distinct 'OF', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join   eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'OF' and f.entitytype = 'NIC' and f.attributecode = 'DATARATE' and f.nlsid = (?)  " + " union select distinct 'OF', ofsbb.ID1, f.nlsid, rtrim( '' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from      eccm.ofsbb ofsbb join   eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = ofsbb.id2 join      eccm.flag f on f.entitytype = pr.entity2type and f.entityid = pr.entity2id and f.sfvalue = 1 where      ofsbb.nlsid = 1 and f.entitytype = 'NIC' and f.attributecode = 'DATARATE' and f.nlsid = (?) order by 1,2,4,3";

        // NP
        String strSQL7 = "select distinct 'OF', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join   eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'OF' and f.entitytype = 'NP' and attributecode in ('INPUTBIN_MAX','INPUTBIN_STD','OUTPUTBIN_MAX','OUTPUTBIN_STD','PRINTSPEEDS','PRTRESOLUTNAME','SIZESUPPORTNAME') and f.nlsid = (?) and 999 <> (?) order by 1,2,4,3";

        // PP
        String strSQL8 = "select distinct 'OF', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join   eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'OF' and f.entitytype = 'PP' and f.attributecode = 'ORIENTALLOW' and f.nlsid = (?)  " + " union select distinct 'OF', ofsbb.ID1, f.nlsid, rtrim( '' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from      eccm.ofsbb ofsbb join   eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = ofsbb.id2 join      eccm.flag f on f.entitytype = pr.entity2type and f.entityid = pr.entity2id and f.sfvalue = 1 where      ofsbb.nlsid = 1 and f.entitytype = 'PP' and f.attributecode = 'ORIENTALLOW' and f.nlsid = (?) order by 1,2,4,3";

        // TI.OSSUPPORTNAME
        String strSQL9 = "select distinct 'OF', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join   eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'OF' and f.entitytype = 'TI' and f.attributecode = 'OSSUPPORTNAME' and f.nlsid = (?)  " + " union select distinct 'OF', ofsbb.ID1, f.nlsid, rtrim( '' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from      eccm.ofsbb ofsbb join   eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = ofsbb.id2 join      eccm.flag f on f.entitytype = pr.entity2type and f.entityid = pr.entity2id and f.sfvalue = 1 where      ofsbb.nlsid = 1 and f.entitytype = 'TI' and f.attributecode = 'OSSUPPORTNAME' and f.nlsid = (?) order by 1,2,4,3";
//here
        // TIF.TICOMMFEAT
        String strSQL10 = "select distinct 'OF', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join   eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'OF' and f.entitytype = 'TIF' and f.attributecode = 'TICOMMFEAT' and f.nlsid = (?) " + " union select distinct 'OF', ofsbb.ID1, f.nlsid, rtrim( '' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from      eccm.ofsbb ofsbb join   eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = ofsbb.id2 join      eccm.flag f on f.entitytype = pr.entity2type and f.entityid = pr.entity2id and f.sfvalue = 1 where      ofsbb.nlsid = f.nlsid and f.entitytype = 'TIF' and f.attributecode = 'TICOMMFEAT' and f.nlsid = (?) order by 1,2,4,3";

        // TIF.TISECURITYFEAT
        String strSQL11 = "select distinct 'OF', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join   eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'OF' and f.entitytype = 'TIF' and F.attributecode = 'TISECURITYFEAT' and f.nlsid = (?)  " + " union select distinct 'OF', ofsbb.ID1, f.nlsid, rtrim( '' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from      eccm.ofsbb ofsbb join   eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = ofsbb.id2 join      eccm.flag f on f.entitytype = pr.entity2type and f.entityid = pr.entity2id and f.sfvalue = 1 where      ofsbb.nlsid = 1 and f.entitytype = 'TIF' and f.attributecode = 'TISECURITYFEAT' and f.nlsid = (?) order by 1,2,4,3";

        // WAR
        String strSQL12 = "select distinct 'OF', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join   eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'OF' and f.entitytype = 'WAR' and f.attributecode = 'WARRANTYTYPE' and f.nlsid = (?) and 999 <> (?) order by 1,2,4,3";

        // OF
        String strSQL13 = "select distinct 'OF', of.entityid, of.nlsid, rtrim('PSG' || f.attributecode), (select rtrim(longdescription) from    eccm.metaflagtable where        attributecode = f.attributecode and ATTRIBUTEVALUE= f.flagcode and nlsid =of.nlsid) FROM        eccm.of of join eccm.flag f on f.entitytype = 'OF' and f.entityid = of.entityid and f.attributecode in ('OFAPPROVALS_CERTS') and f.nlsid = of.nlsid and f.nlsid = (?) and 999 <> (?) order by 1,2,4,3";
        //

        //WS.WSSPEED
        String strSQL14 = "select distinct 'OF', ofsbb.ID1, f.nlsid, rtrim( '' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from      eccm.ofsbb ofsbb join   eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = ofsbb.id2 join      eccm.flag f on f.entitytype = pr.entity2type and f.entityid = pr.entity2id and f.sfvalue = 1 where      ofsbb.nlsid = 1 and f.entitytype = 'WS' and f.attributecode = 'WSSPEED' and f.nlsid = (?) " + " union select distinct 'OF', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join   eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'OF' and f.entitytype = 'WS' and f.attributecode = 'WSSPEED' and f.nlsid = (?) order by 1,2,4,3";

        String strSQL15 = "select distinct 'OF', ofsbb.ID1, ws.nlsid, 'WWPSGWSDESC', rtrim(ws.WSDESC) from      eccm.ofsbb ofsbb join   eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = ofsbb.id2 join      eccm.ws ws on pr.entity2type = 'WS'  and ws.entityid = pr.entity2id where      ofsbb.nlsid = 1 and ws.nlsid = (?)   " + " union select distinct 'OF', pad.entity1id, ws.nlsid, 'WWPSGWSDESC', rtrim(ws.WSDESC) from eccm.prodattrelator pad join   eccm.ws ws on pad.entity2type = 'WS' and ws.entityid = pad.entity2id  where    pad.entity1type = 'OF' and ws.nlsid = (?)  order by 1,2,4,3";

        String strSQL16 = "select distinct 'VAR', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join    eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'VAR'   and f.entitytype = 'DD'  and f.attributecode = 'TECHNOLOGYFEATURES' and f.nlsid = (?) and 999 <> (?) order by 1,2,4,3";

        String strSQL17 = "select distinct 'VAR', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join    eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'VAR'   and f.entitytype = 'WAR' and f.attributecode ='WARRANTYTYPE' and f.nlsid = (?)  " + " union select distinct 'VAR', varsbb.ID1, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from    eccm.varsbb varsbb join eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = varsbb.id2 join     eccm.flag f on f.entitytype = pr.entity2type and f.entityid = pr.entity2id and f.sfvalue = 1 where      varsbb.nlsid = 1   and f.entitytype = 'WAR' and f.attributecode = 'WARRANTYTYPE'  and f.nlsid = (?)  order by 1,2,4,3";

        String strSQL18 = "select distinct 'VAR', varsbb.ID1, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from    eccm.varsbb varsbb join eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = varsbb.id2 join     eccm.flag f on f.entitytype = pr.entity2type and f.entityid = pr.entity2id and f.sfvalue = 1 where      varsbb.nlsid = 1   and f.entitytype in ('FM','HDC','NIC','PP','TI','TIF','WS') and f.attributecode in ('DATARATEFAX','DATARATEMODEM','CONTROLLERNAME','DATARATE','ORIENTALLOW','OSSUPPORTNAME','TICOMMFEAT','TISECURITYFEAT','WSSPEED') and f.nlsid = (?)  and 999 <> (?)  order by 1,2,4,3";

        String strSQL19 = "select distinct 'VAR', varsbb.ID1, ws.nlsid, 'WWPSGWSDESC', rtrim(ws.WSDESC) from    eccm.varsbb varsbb join eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = varsbb.id2 join     eccm.ws ws on pr.entity2type = 'WS' and ws.entityid = pr.entity2id where      varsbb.nlsid = 1  and ws.nlsid = (?)  and 999 <> (?)  order by 1,2,4,3";

        String strSQL20 = "select distinct 'OF', ofport.id1, p.nlsid, case p.PORTCATEGORY_FC when '11108' THEN 'PSGSERIALPORTTYPE_PL' when '11107' THEN 'PSGPARA_PORTTYPE_PL' when '11106' THEN 'PSGEXPANPORTTYPE' end case, p.PortType from      eccm.ofport ofport join eccm.port p on p.entityid = ofport.id2 where    p.portcategory_fc in ('11108','11107','11106')   and p.nlsid = (?)  " + " union select distinct 'OF', ofsbb.id1, p.nlsid, case p.PORTCATEGORY_FC when '11108' THEN 'PSGSERIALPORTTYPE_PL' when '11107' THEN 'PSGPARA_PORTTYPE_PL' when '11106' THEN 'PSGEXPANPORTTYPE'  end case, p.PortType from eccm.ofsbb ofsbb join   eccm.sbbport sbbport on sbbport.id1 = ofsbb.id2 join eccm.port p on p.entityid = sbbport.id2 where   p.portcategory_fc in ('11108','11107','11106')   and p.nlsid = (?)  order by 1,2,4,3";

        String strSQL21 = "select distinct 'VAR', varsbb.id1, p.nlsid, case p.PORTCATEGORY_FC when '11108' THEN 'PSGSERIALPORTTYPE_PL' when '11107' THEN 'PSGPARA_PORTTYPE_PL' when '11106' THEN 'PSGEXPANPORTTYPE' end case, p.PortType from eccm.varsbb varsbb join eccm.sbbport sbbport on sbbport.id1 = varsbb.id2 join eccm.port p on p.entityid = sbbport.id2 where   p.portcategory_fc in ('11108','11107','11106')   and p.nlsid = (?)  and 999 <> (?)  order by 1,2,4,3";

        String strSQL22 = "select distinct 'OF', ofport.id1, p.nlsid, 'PSGEXPANPORTTYPE', p.PortType from   eccm.ofport ofport join eccm.port p on p.entityid = ofport.id2 where    p.portcategory_fc in ('11106') and ofport.nlsid = p.nlsid  and p.nlsid = (?) " + " union select distinct 'OF', ofsbb.id1, p.nlsid, 'PSGEXPANPORTTYPE', p.PortType from    eccm.ofsbb ofsbb join   eccm.sbbport sbbport on sbbport.id1 = ofsbb.id2 join    eccm.port p on p.entityid = sbbport.id2 where p.portcategory_fc in ('11106')  and p.nlsid = (?)  order by 1,2,4,3 ";

        String strSQL23 = "select distinct 'VAR', varsbb.id1, p.nlsid, 'PSGEXPANPORTTYPE', p.PortType from  eccm.varsbb varsbb join eccm.sbbport sbbport on sbbport.id1 = varsbb.id2 join   eccm.port p on p.entityid = sbbport.id2 where p.portcategory_fc in ('11106')   and p.nlsid = (?)  and 999 <> (?)  order by 1,2,4,3";

        String strSQL24 = "select distinct 'OF', ofsec.id1, s.nlsid, 'PSGSECURITYIFR', s.SecType from   eccm.ofsec ofsec join eccm.sec s on s.entityid = ofsec.id2   and s.nlsid = (?)  " + " union select distinct 'OF', ofsbb.id1, s.nlsid, 'PSGSECURITYIFR', s.SecType from    eccm.ofsbb ofsbb join  eccm.sbbsec sbbsec on sbbsec.id1 = ofsbb.id2 join eccm.sec s on s.entityid = sbbsec.id2  and s.nlsid = (?)  order by 1,2,4,3 ";

        String strSQL25 = "select distinct 'VAR', varsbb.id1, s.nlsid,'PSGSECURITYIFR', s.SecType from  eccm.varsbb varsbb join eccm.sbbsec sbbsec on sbbsec.id1 = varsbb.id2 join   eccm.sec s on s.entityid = sbbsec.id2  and s.nlsid = (?)  and 999 <> (?)  order by 1,2,4,3 ";

        String strSQL26 = "select distinct 'CSOL', csol.entityid, f.nlsid, 'CT_PSGWARRANTYTYPE', rtrim(f.flagdescription) from     eccm.csol csol join        eccm.csolwar csolwar on csolwar.id1 = csol.entityid and csolwar.nlsid = 1 join  eccm.flag f on f.entitytype = 'WAR' and f.entityid = csolwar.id2 and f.sfvalue = 1 and f.nlsid = csol.nlsid and f.attributecode='WARRANTYTYPE'  and f.nlsid = (?)  and 999 <> (?)  order by 1,2,4,3 ";

        String strSQL27 = "select distinct 'CVAR', cvar.entityid, f.nlsid,'CT_PSGWARRANTYTYPE', rtrim(f.flagdescription) from      eccm.cvar cvar join eccm.cvarwar cvarwar on cvarwar.id1 = cvar.entityid and cvarwar.nlsid = 1 join  eccm.flag f on f.entitytype = 'WAR' and f.entityid = cvarwar.id2 and f.sfvalue = 1 and f.nlsid = cvar.nlsid and f.attributecode='WARRANTYTYPE'  and f.nlsid = (?)  and 999 <> (?)  order by 1,2,4,3 ";

        String strSQL28 = "select distinct 'CVAR', cvar.entityid, f.nlsid,'CTPSGWSSPEED', rtrim(f.flagdescription) from   eccm.cvar cvar join     eccm.cvarsbb cvarsbb on cvarsbb.id1= cvar.entityid join         eccm.sbbws sbbws on sbbws.id1 = cvarsbb.id2 and sbbws.nlsid = 1 join    eccm.flag f on f.entitytype ='WS' and f.entityid = sbbws.id2 and f.sfvalue = 1  and f.nlsid=cvar.nlsid and f.attributecode='WSSPEED'  and f.nlsid = (?)  and 999 <> (?)  order by 1,2,4,3 ";

        String strSQL29 = "select distinct 'CVAR', cvar.entityid, ws.nlsid,'CTPSGWSDESC', ws.WSDESC from   eccm.cvar cvar join     eccm.cvarsbb cvarsbb on cvarsbb.id1= cvar.entityid join         eccm.sbbws sbbws on sbbws.id1 = cvarsbb.id2 and sbbws.nlsid = 1 join    eccm.ws ws on ws.entityid = sbbws.id2 and ws.nlsid=cvar.nlsid and ws.nlsid = (?)  and 999 <> (?)  order by 1,2,4,3 ";

        String strSQL30 = "select distinct 'CVAR', cvar.entityid, f.nlsid , concat('CTPSG', f.attributecode), rtrim(f.flagdescription) from       eccm.cvar cvar join     eccm.cvarsbb cvarsbb on cvarsbb.id1= cvar.entityid join         eccm.sbbfm sbbfm on sbbfm.id1 = cvarsbb.id2 and sbbfm.nlsid = 1 join    eccm.flag f on f.entitytype ='FM' and f.entityid = sbbfm.id2 and f.sfvalue = 1 and f.nlsid=cvar.nlsid and f.attributecode in ('DATARATEFAX','DATARATEMODEM')  and f.nlsid = (?)  and 999 <> (?)  order by 1,2,4,3";

        String strSQL31 = "select distinct 'OF', ofpsl.id1, psl.nlsid, 'WWMDCARDSLOT', rtrim( char(psl.SLOTS_TOTAL)) || '(' || rtrim( char(pslavail.SLOTS_AVAIL )) || ') ' || psl.SLOT_TYPE from eccm.ofpsl ofpsl join eccm.psl psl on ofpsl.id2 = psl.entityid join eccm.ofpslavail ofpslavail on ofpslavail.id1 = ofpsl.id1 join eccm.pslavail pslavail on ofpslavail.id2 = pslavail.entityid where pslavail.nlsid = psl.nlsid and pslavail.slotavailtype = psl.slotavailtype   and psl.nlsid = (?)  and 999 <> (?)  order by 1,2,4,3";

        String strSQL32 = "select distinct 'VAR', varsbb.id1, psl.nlsid, 'WWMDCARDSLOT', rtrim( char(psl.SLOTS_TOTAL)) || '(' || rtrim( char(pslavail.SLOTS_AVAIL )) || ') ' || psl.SLOT_TYPE from eccm.varsbb varsbb join eccm.sbbpsl spsl on varsbb.id2 = spsl.id1 join eccm.psl psl on spsl.id2 = psl.entityid join eccm.varpslavail varpslavail on varpslavail.id1 = varsbb.id1 join eccm.pslavail pslavail on varpslavail.id2 = pslavail.entityid where pslavail.nlsid = psl.nlsid and pslavail.slotavailtype = psl.slotavailtype  and psl.nlsid = (?)  and 999 <> (?)  order by 1,2,4,3";

        // VAR.CDROPTITYPE
        String strSQL33 = "select distinct 'VAR', varsbb.ID1, cdr.nlsid,'WWPSGCDROPTITYPE', cdr.CDROPTITYPE from eccm.varsbb varsbb join eccm.prodattrelator pad on pad.entity1type = 'SBB' and pad.entity1id = varsbb.id2 join    eccm.CDR cdr on pad.entity2type = 'CDR' and cdr.entityid = pad.entity2id where CDR.nlsid = (?)  and 999 <> (?)  order by 1,2,4,3";

        String[] saSQL = new String[] {
            strSQL1, strSQL2, strSQL3, strSQL4, strSQL5, strSQL6, strSQL7, strSQL8, strSQL9, strSQL10, strSQL11, strSQL12, strSQL13,
            strSQL14, strSQL15, strSQL16, strSQL17, strSQL18, strSQL19, strSQL20, strSQL21, strSQL22, strSQL23, strSQL24, strSQL25,
            strSQL26, strSQL27, strSQL28, strSQL29, strSQL30, strSQL31, strSQL32, strSQL33};

        // find a way to get these...
        Integer[] iaNLS = ODSServerProperties.getMultiAttributeNLSs(m_strODSSchema);

        for (int i = 0; i < saSQL.length; i++) {

            for (int xx = 0; xx < iaNLS.length; xx++) {

                int iNLS = iaNLS[xx].intValue();

                D.ebug(D.EBUG_ERR, "initMultiAttributes() start processing SQL " + (i + 1) + "...");

                Stopwatch sw2 = new Stopwatch();
                sw2.start();
                String strSQL = saSQL[i];
                D.ebug(D.EBUG_ERR, "initMultiAttributes() strSQL:" + strSQL);
                ps = m_conODS.prepareStatement(strSQL);
                ps.setInt(1, iNLS);
                ps.setInt(2, iNLS);
                try {
                    D.ebug(D.EBUG_DETAIL, "initMultiAttributes() before executeQuery()...");
                    rs = ps.executeQuery();
                    D.ebug(D.EBUG_DETAIL, "initMultiAttributes() ...after executeQuery()");
                    rdrs = new ReturnDataResultSet(rs);
                    D.ebug(D.EBUG_DETAIL, "initMultiAttributes() built rdrs");
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (ps != null) {
                        ps.close();
                        ps = null;
                    }
                }
                if (rdrs != null && rdrs.getRowCount() > 0) {
                    processMultiAttributeRdrs(rdrs, true);
                    D.ebug(D.EBUG_DETAIL, "initMultiAttributes():before commit (" + rdrs.size() + ") rows...");
                    m_conODS.commit();
                    D.ebug(D.EBUG_DETAIL, "initMultiAttributes():commit done!");
                } else {
                    D.ebug(D.EBUG_DETAIL, "initMultiAttributes(), rdrs is NULL! (SQL" + (i + 1) + ") NLS:" + iNLS);
                }
                D.ebug(D.EBUG_DETAIL,
                       "Timing MultiAttributes processing (Init:SQL" + (i + 1) + "), took :" + sw2.finish() + " for [" + rdrs.size() +
                       "] entities. (NLS:" + iNLS + ")");
                rdrs = null;
            }
        }
        D.ebug(D.EBUG_DETAIL, "Timing MultiAttributes processing (Init TOTAL), took :" + sw1.finish());
    }

    /**
     * This will be performed att the end of all other processing.
     *  Applicable attributes were found during "main" processing.
     *    >>> For NET Changes Onkly!! <<<
     *
     *  TODO:
     *    track deleted attributes in deletelog (?)
     *
     */
    protected final void processNetMultiAttributes() throws SQLException {

        ResultSet rs = null;
        ReturnDataResultSet rdrsRoot = null;
        ReturnDataResultSet rdrsChild = null;
        //
        PreparedStatement psOFChild0 = null;
        PreparedStatement psOFChild1 = null;
        PreparedStatement psVARChild1 = null;
        PreparedStatement psOFPORTChild1 = null;
        PreparedStatement psVARPORTChild1 = null;
        PreparedStatement psOFSECChild1 = null;
        PreparedStatement psVARSECChild1 = null;
        PreparedStatement psOFPSLChild1 = null;
        PreparedStatement psVARPSLChild1 = null;
        //PreparedStatement psOFPSLAVAILChild1 = null; // we can use the PSL sql for these
        //PreparedStatement psVARPSLAVAILChild1 = null;// we can use the PSL sql for these
        PreparedStatement psCSOLWARChild1 = null;
        PreparedStatement psCVARWARChild1 = null;
        PreparedStatement psCVARWSChild1 = null;
        PreparedStatement psCVARFMChild1 = null;
        PreparedStatement psOFWSDESCChild = null;
        PreparedStatement psVARWSDESCChild = null;
        PreparedStatement psOFCDRChild = null;
        PreparedStatement psVARCDRChild = null;

        //////////////////////////////////////////////////////////////////////
        // these are SQLs to run to determine children+values of parents... //
        //////////////////////////////////////////////////////////////////////

        String strVARCDRChildSQL = "select distinct 'VAR', varsbb.ID1, cdr.nlsid,'WWPSGCDROPTITYPE', cdr.CDROPTITYPE from eccm.varsbb varsbb join eccm.prodattrelator pad on pad.entity1type = 'SBB' and pad.entity1id = varsbb.id2 join    eccm.CDR cdr on pad.entity2type = 'CDR' and cdr.entityid = pad.entity2id where varsbb.id1 = (?)  order by 1,2,4,3";

        //String strOFCDRChildSQL = "select distinct 'OF', pad.entity1id, cdr.nlsid,'WWPSGCDROPTITYPE', cdr.CDROPTITYPE from eccm.prodattrelator pad join   eccm.CDR cdr on pad.entity2type = 'CDR' and cdr.entityid = pad.entity2id where    pad.entity1type = 'OF' and pad.entity1id = (?) order by 1,2,4,3";
        // GAB - 060606: added OF-SBB-CDR relationship. Apparently it was never in for CDR and needs to be.
        String strOFCDRChildSQL = "select distinct 'OF', pad.entity1id, cdr.nlsid, 'WWPSGCDROPTITYPE', cdr.CDROPTITYPE  from eccm.prodattrelator pad join   eccm.CDR cdr on pad.entity2type = 'CDR' and cdr.entityid = pad.entity2id where    pad.entity1type = 'OF' and pad.entity1id = (?) " +
                            "union select distinct 'OF', ofsbb.ID1,     cdr.nlsid, 'WWPSGCDROPTITYPE', cdr.CDROPTITYPE from eccm.ofsbb ofsbb join eccm.prodattrelator pad on pad.entity1type = 'SBB' and pad.entity1id = ofsbb.id2 join    eccm.CDR cdr on pad.entity2type = 'CDR' and cdr.entityid = pad.entity2id where ofsbb.id1 = (?) "
                           + "order by 1,2,4,3";

        String strOFChild0SQL = "SELECT distinct 'OF', of.entityid, of.nlsid, rtrim('PSG' || f.attributecode), rtrim(f.flagdescription) FROM        eccm.of of join eccm.flag f on f.entitytype = 'OF' and f.entityid = of.entityid and f.attributecode in ('OFAPPROVALS_CERTS') and f.nlsid = of.nlsid " +
            " and f.entityid = (?) and f.attributecode = (?) order by 1,2,4,3"; // OFAPPROVALS_CERTS

        // find 'OF' children for DD,FM,HDC,MON,NIC,NP,OF,PORT,PP,PSL,PSLAVAIL,TI,TIF,WAR,WS
        String strOFChild1SQL = "select distinct 'OF', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from eccm.prodattrelator pad join   eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'OF' " +
            "   and f.entitytype = (?) and pad.entity1id = (?) and f.attributecode = (?)" + " union select distinct 'OF', ofsbb.ID1, f.nlsid, rtrim( '' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription)  from      eccm.ofsbb ofsbb join   eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = ofsbb.id2 join      eccm.flag f on f.entitytype = pr.entity2type and f.entityid = pr.entity2id and f.sfvalue = 1 where      ofsbb.nlsid = 1" +
            "   and f.entitytype = (?) and ofsbb.ID1 = (?) and f.attributecode = (?) order by 1,2,4,3";

        // find 'VAR' children for CDR,DD,FM,HDC,MON,NIC,NP,OF,PORT,PP,PSL,PSLAVAIL,TI,TIF,WAR,WS
        String strVARChild1SQL = "select distinct 'VAR', pad.entity1id, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription)  from eccm.prodattrelator pad join    eccm.flag f on f.entitytype = pad.entity2type and f.entityid = pad.entity2id and f.sfvalue = 1 where    pad.entity1type = 'VAR' " +
            "   and f.entitytype = (?) and pad.entity1id = (?) and f.attributecode = (?)" + " union select distinct 'VAR', varsbb.ID1, f.nlsid, rtrim('' concat (select att.attributetoken from eccm.attribute att where att.attributecode = f.attributecode)), rtrim(f.flagdescription) from    eccm.varsbb varsbb join eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = varsbb.id2 join     eccm.flag f on f.entitytype = pr.entity2type and f.entityid = pr.entity2id and f.sfvalue = 1 where      varsbb.nlsid = 1" +
            "   and f.entitytype = (?) and varsbb.ID1 = (?) and f.attributecode = (?) order by 1,2,4,3";

        // WS.WSDESC
        String strOFWSDESCChildSQL = "select distinct 'OF', pad.entity1id, ws.nlsid, 'WWPSGWSDESC', rtrim(ws.WSDESC) from eccm.prodattrelator pad join   eccm.ws ws on pad.entity2type = 'WS' and ws.entityid = pad.entity2id  where    pad.entity1type = 'OF' " +
            " and pad.entity1id = (?)" + " union select distinct 'OF', ofsbb.ID1, ws.nlsid, 'WWPSGWSDESC', rtrim(ws.WSDESC) from      eccm.ofsbb ofsbb join   eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = ofsbb.id2 join      eccm.ws ws on pr.entity2type = 'WS'  and ws.entityid = pr.entity2id where      ofsbb.nlsid = 1 " +
            " and ofsbb.id1 = (?) order by 1,2,4,3";

        // WS.WSDESC
        String strVARWSDESCChildSQL = "select distinct 'VAR', pad.entity1id, ws.nlsid, 'WWPSGWSDESC', rtrim(ws.WSDESC) from eccm.prodattrelator pad join   eccm.ws ws on pad.entity2type = 'WS' and ws.entityid = pad.entity2id  where    pad.entity1type = 'VAR' " +
            " and pad.entity1id = (?)" + " union select distinct 'VAR', varsbb.ID1, ws.nlsid, 'WWPSGWSDESC', rtrim(ws.WSDESC) from      eccm.varsbb varsbb join   eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = varsbb.id2 join      eccm.ws ws on pr.entity2type = 'WS'  and ws.entityid = pr.entity2id where      varsbb.nlsid = 1 " +
            " and varsbb.id1 = (?) order by 1,2,4,3";

        // PORTS
        String strOFPORTChild1SQL = "select distinct'OF', ofport.id1, p.nlsid, case p.PORTCATEGORY_FC when '11108' THEN 'PSGSERIALPORTTYPE_PL' when '11107' THEN 'PSGPARA_PORTTYPE_PL' when '11106' THEN 'PSGEXPANPORTTYPE' end case, p.PortType from      eccm.ofport ofport join eccm.port p on p.entityid = ofport.id2 where    p.portcategory_fc in ('11108','11107','11106') " +
            " and ofport.id1 = (?) " + "union select distinct 'OF', ofsbb.id1, p.nlsid, case p.PORTCATEGORY_FC when '11108' THEN 'PSGSERIALPORTTYPE_PL' when '11107' THEN 'PSGPARA_PORTTYPE_PL' when '11106' THEN 'PSGEXPANPORTTYPE' end case, p.PortType from eccm.ofsbb ofsbb join   eccm.sbbport sbbport on sbbport.id1 = ofsbb.id2 join eccm.port p on p.entityid = sbbport.id2 where   p.portcategory_fc in ('11108','11107','11106') " +
            " and ofsbb.id1 = (?) " + "union select distinct 'OF', ofport.id1, p.nlsid, 'PSGEXPANPORTTYPE', p.PortType from   eccm.ofport ofport join eccm.port p on p.entityid = ofport.id2 where    p.portcategory_fc in ('11106') " +
            " and ofport.id1 = (?) " + "union select distinct 'OF', ofsbb.id1, p.nlsid, 'PSGEXPANPORTTYPE', p.PortType from    eccm.ofsbb ofsbb join   eccm.sbbport sbbport on sbbport.id1 = ofsbb.id2 join    eccm.port p on p.entityid = sbbport.id2 where p.portcategory_fc in ('11106') " +
            " and ofsbb.id1 = (?) order by 1,2,4,3";

        String strVARPORTChild1SQL = "select distinct 'VAR', varsbb.id1, p.nlsid, case p.PORTCATEGORY_FC when '11108' THEN 'PSGSERIALPORTTYPE_PL' when '11107' THEN 'PSGPARA_PORTTYPE_PL' when '11106' THEN 'PSGEXPANPORTTYPE' end case, p.PortType from eccm.varsbb varsbb join eccm.sbbport sbbport on sbbport.id1 = varsbb.id2 join eccm.port p on p.entityid = sbbport.id2 where   p.portcategory_fc in ('11108','11107','11106') " +
            " and varsbb.id1 = (?) " + "union select distinct 'VAR', varsbb.id1, p.nlsid, 'PSGEXPANPORTTYPE', p.PortType from  eccm.varsbb varsbb join eccm.sbbport sbbport on sbbport.id1 = varsbb.id2 join   eccm.port p on p.entityid = sbbport.id2 where p.portcategory_fc in ('11106') " +
            " and varsbb.id1 = (?) order by 1,2,4,3";

        // SEC
        String strOFSECChild1SQL = "select distinct 'OF', ofsec.id1, s.nlsid, 'PSGSECURITYIFR', s.SecType from   eccm.ofsec ofsec join eccm.sec s on s.entityid = ofsec.id2 " +
            " and ofsec.id1 = (?) " + "union select distinct 'OF', ofsbb.id1, s.nlsid, 'PSGSECURITYIFR', s.SecType from    eccm.ofsbb ofsbb join  eccm.sbbsec sbbsec on sbbsec.id1 = ofsbb.id2 join eccm.sec s on s.entityid = sbbsec.id2 " +
            " and ofsbb.id1 = (?) order by 1,2,4,3";

        String strVARSECChild1SQL = "select distinct 'VAR', varsbb.id1, s.nlsid,'PSGSECURITYIFR', s.SecType from  eccm.varsbb varsbb join eccm.sbbsec sbbsec on sbbsec.id1 = varsbb.id2 join   eccm.sec s on s.entityid = sbbsec.id2 " +
            " and varsbb.id1 = (?) order by 1,2,4,3";

        // PSL/PSLAVAIL
        String strOFPSLChild1SQL = "select distinct 'OF', ofpsl.id1, psl.nlsid, 'WWMDCARDSLOT', rtrim( char(psl.SLOTS_TOTAL)) || '(' || rtrim( char(pslavail.SLOTS_AVAIL )) || ') ' || psl.SLOT_TYPE from eccm.ofpsl ofpsl join eccm.psl psl on ofpsl.id2 = psl.entityid join eccm.ofpslavail ofpslavail on ofpslavail.id1 = ofpsl.id1 join eccm.pslavail pslavail on ofpslavail.id2 = pslavail.entityid where pslavail.nlsid = psl.nlsid and pslavail.slotavailtype = psl.slotavailtype " +
            " and ofpsl.id1 = (?) order by 1,2,4,3";

////////////////
        String strVARPSLChild1SQL = "select distinct 'VAR', varsbb.id1, psl.nlsid, 'WWMDCARDSLOT', rtrim( char(psl.SLOTS_TOTAL)) || '(' || rtrim( char(pslavail.SLOTS_AVAIL )) || ') ' || psl.SLOT_TYPE from eccm.varsbb varsbb join eccm.sbbpsl spsl on varsbb.id2 = spsl.id1 join eccm.psl psl on spsl.id2 = psl.entityid join eccm.varpslavail varpslavail on varpslavail.id1 = varsbb.id1 join eccm.pslavail pslavail on varpslavail.id2 = pslavail.entityid where pslavail.nlsid = psl.nlsid and pslavail.slotavailtype = psl.slotavailtype " +
            " and varsbb.id1 = (?) order by 1,2,4,3";

        // WAR.WARRANTYTYPE
        String strCSOLWARChild1SQL = "select distinct 'CSOL', csol.entityid, f.nlsid, 'CT_PSGWARRANTYTYPE', rtrim(f.flagdescription) from     eccm.csol csol join        eccm.csolwar csolwar on csolwar.id1 = csol.entityid and csolwar.nlsid = 1 join  eccm.flag f on f.entitytype = 'WAR' and f.entityid = csolwar.id2 and f.sfvalue = 1 and f.nlsid = csol.nlsid and f.attributecode='WARRANTYTYPE'  " +
            " and csol.entityid = (?) order by 1,2,4,3";

        String strCVARWARChild1SQL = "select distinct 'CVAR', cvar.entityid, f.nlsid,'CT_PSGWARRANTYTYPE', rtrim(f.flagdescription) from      eccm.cvar cvar join eccm.cvarwar cvarwar on cvarwar.id1 = cvar.entityid and cvarwar.nlsid = 1 join  eccm.flag f on f.entitytype = 'WAR' and f.entityid = cvarwar.id2 and f.sfvalue = 1 and f.nlsid = cvar.nlsid and f.attributecode='WARRANTYTYPE'  " +
            " and cvar.entityid = (?) order by 1,2,4,3";

        // WS.WSSPEED,
        String strCVARWSChild1SQL = "select distinct 'CVAR', cvar.entityid, f.nlsid,'CTPSGWSSPEED', rtrim(f.flagdescription) from   eccm.cvar cvar join     eccm.cvarsbb cvarsbb on cvarsbb.id1= cvar.entityid join         eccm.sbbws sbbws on sbbws.id1 = cvarsbb.id2 and sbbws.nlsid = 1 join    eccm.flag f on f.entitytype ='WS' and f.entityid = sbbws.id2 and f.sfvalue = 1  and f.nlsid=cvar.nlsid and f.attributecode='WSSPEED' " +
            " and cvar.entityid = (?)" + " union select distinct 'CVAR', cvar.entityid, ws.nlsid,'CTPSGWSDESC', ws.WSDESC from   eccm.cvar cvar join     eccm.cvarsbb cvarsbb on cvarsbb.id1= cvar.entityid join         eccm.sbbws sbbws on sbbws.id1 = cvarsbb.id2 and sbbws.nlsid = 1 join    eccm.ws ws on ws.entityid = sbbws.id2 and ws.nlsid=cvar.nlsid and " +
            " cvar.entityid = (?) order by 1,2,4,3";

        // FM
        String strCVARFMChild1SQL = "select distinct 'CVAR', cvar.entityid, f.nlsid , concat('CTPSG', f.attributecode), rtrim(f .flagdescription) from       eccm.cvar cvar join     eccm.cvarsbb cvarsbb on cvarsbb.id1= cvar.entityid join         eccm.sbbfm sbbfm on sbbfm.id1 = cvarsbb.id2 and sbbfm.nlsid = 1 join    eccm.flag f on f.entitytype ='FM' and f.entityid = sbbfm.id2 and f.sfvalue = 1 and f.nlsid=cvar.nlsid and f.attributecode in ('DATARATEFAX','DATARATEMODEM') " +
            " and cvar.entityid = (?) order by 1,2,4,3";

        // What we're doing here is:
        //  RE: we already pulled the relevant affected attributes out in our "main" logic above...
        //  1) [outter "i" loop]: go through all ~changed~ (or deleted - remember this!!!) attributes and find
        //                        which parent we care about rolling up values for.
        //  2) [middle "j" loop]: go through the parent type which we care about, and, depending on the specific
        //                        entitytype/attcode child we are looking at, find the specific parent entity. to roll up for
        //  3) [inner "k" loop]:  this is just placing the result from (2) into a vector to save off for final processing once
        //                        we got em all. and check for duplicate parents so we dont repeat ourselves.
        //  4)                    Now, let's go through all the parents and run sort of the convers of (2). that is,
        //                        get ALL the children (based on specific etype/attcode) and values to role up on said parent entity.
        //  5)                    ????
        //         TODO: lets build rollup tables and such


        D.ebug(D.EBUG_DETAIL, "number of affected rollup attributes found:" + m_vctRollupAttrs.size());
        // lets just display what we've got fer now...
        for (int i = 0; i < m_vctRollupAttrs.size(); i++) {
            String strKey = (String) m_vctRollupAttrs.elementAt(i);
            StringTokenizer st = new StringTokenizer(strKey, ":");
            String strEntityType = st.nextToken();
            String strAttCode = st.nextToken();
            String strEntityID = st.nextToken();
            int iEntityID = -99;
            try {
                iEntityID = Integer.parseInt(strEntityID);
            }
            catch (NumberFormatException nfe) {
                D.ebug(D.EBUG_ERR, "ERROR parsing processMultiAttributes(): not an int, dummy. (" + strEntityID + ")");
            }
            popMultiAttributeRoot(strEntityType, strAttCode, iEntityID);
        }

        //
        //
        // NOW... lets go through all affected root entities and grab all corresponding children to rollup...
        //
        //
        D.ebug(D.EBUG_DETAIL, "m_vctRootRollupAttrs Total Size is: " + m_vctRootRollupAttrs.size());
        String s1 = null;
        String s2 = null;
        boolean bCommit = false;
        for (int i = 0; i < m_vctRootRollupAttrs.size(); i++) {
            Stopwatch sw = new Stopwatch();
            sw.start();
            String strVal = (String) m_vctRootRollupAttrs.elementAt(i);
            D.ebug(D.EBUG_DETAIL, "m_vctRootRollupAttrs at [" + i + "] = " + strVal);
            StringTokenizer st = new StringTokenizer(strVal, ":");
            String strRootType = st.nextToken().trim();
            String strRootID = st.nextToken().trim();
            int iRootID = -1;
            try {
                iRootID = Integer.parseInt(strRootID);
            }
            catch (NumberFormatException nfe) {
                D.ebug(D.EBUG_DETAIL, "nfe at 6924");
            }
            String strChildType = st.nextToken().trim();
            String strChildAttCode = st.nextToken().trim();
            //String strRootAttCodeMapping = st.nextToken().trim();
            //
            D.ebug(D.EBUG_DETAIL, "strRootType:" + strRootType + ",strChildType:" + strChildType);

            // for logging...
            if (s1 == null) {
                s1 = strChildType + ":" + strChildAttCode;
                D.ebug(D.EBUG_DETAIL, "Looking at new ChildType/AttCode:" + s1);
            }
            s2 = strChildType + ":" + strChildAttCode;
            if (!s2.equals(s1)) {
                s1 = s2;
                D.ebug(D.EBUG_DETAIL, "Looking at new ChildType/AttCode:" + s2);
                bCommit = true;
            }
            //

            if (strRootType.equals("OF") &&
                (strChildType.equals("DD") || strChildType.equals("FM") || strChildType.equals("HDC") || strChildType.equals("MON") ||
                 strChildType.equals("NIC") || strChildType.equals("NP") ||
                 //strChildType.equals("PORT") ||
                 strChildType.equals("PP") ||
                 //strChildType.equals("PSL") ||
                 //strChildType.equals("PSLAVAIL") ||
                 strChildType.equals("TI") || strChildType.equals("TIF") || strChildType.equals("WAR") || strChildType.equals("WS"))) {
                D.ebug(D.EBUG_DETAIL, "case 1!");
                if (psOFChild1 == null) {
                    psOFChild1 = m_conODS.prepareStatement(strOFChild1SQL);
                } else {
                    psOFChild1.clearParameters();
                }
                D.ebug(D.EBUG_DETAIL, "strOFChild1SQL:" + strOFChild1SQL);
                psOFChild1.setString(1, strChildType);
                psOFChild1.setInt(2, iRootID);
                psOFChild1.setString(3, strChildAttCode);
                // itsa union so we have to double up...
                psOFChild1.setString(4, strChildType);
                psOFChild1.setInt(5, iRootID);
                psOFChild1.setString(6, strChildAttCode);
                try {
                    rs = psOFChild1.executeQuery();
                    rdrsChild = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psOFChild1 != null) {
                        psOFChild1.close();
                        psOFChild1 = null;
                    }
                }
                // WSDESC
                if (strChildAttCode.equals("WSDESC")) {
                    psOFWSDESCChild = m_conODS.prepareStatement(strOFWSDESCChildSQL);
                    psOFWSDESCChild.setInt(1, iRootID);
                    // itsa union so we have to double up...
                    psOFWSDESCChild.setInt(2, iRootID);
                    try {
                        rs = psOFWSDESCChild.executeQuery();
                        ReturnDataResultSet rdrsTemp = new ReturnDataResultSet(rs);
                        if (rdrsChild != null) {
                            rdrsChild.addAll(rdrsTemp);
                        } else {
                            rdrsChild = rdrsTemp;
                        }
                        rdrsTemp = null;
                    }
                    finally {
                        if (rs != null) {
                            rs.close();
                            rs = null;
                        }
                        if (psOFWSDESCChild != null) {
                            psOFWSDESCChild.close();
                            psOFWSDESCChild = null;
                        }
                    }
                }
            } else {
                D.ebug(D.EBUG_DETAIL, "NO case 1!");
            }
            if (strRootType.equals("VAR") &&
                (strChildType.equals("DD") || strChildType.equals("FM") || strChildType.equals("HDC") || strChildType.equals("MON") ||
                 strChildType.equals("NIC") || strChildType.equals("NP") ||
                 //strChildType.equals("PORT") ||
                 strChildType.equals("PP") ||
                 //strChildType.equals("PSL") ||
                 //strChildType.equals("PSLAVAIL") ||
                 strChildType.equals("TI") || strChildType.equals("TIF") || strChildType.equals("WAR") || strChildType.equals("WS"))) {
                if (psVARChild1 == null) {
                    psVARChild1 = m_conODS.prepareStatement(strVARChild1SQL);
                } else {
                    psVARChild1.clearParameters();
                }
                psVARChild1.setString(1, strChildType);
                psVARChild1.setInt(2, iRootID);
                psVARChild1.setString(3, strChildAttCode);
                // itsa union so we have to double up...
                psVARChild1.setString(4, strChildType);
                psVARChild1.setInt(5, iRootID);
                psVARChild1.setString(6, strChildAttCode);

                try {
                    rs = psVARChild1.executeQuery();
                    rdrsChild = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psVARChild1 != null) {
                        psVARChild1.close();
                        psVARChild1 = null;
                    }
                }

                // GAB- Added 01.10.06 per MN26472195
                // WSDESC
                if (strChildAttCode.equals("WSDESC")) {
                    D.ebug(D.EBUG_SPEW, "Going for WSDESC off of VAR:" + iRootID);
                    psVARWSDESCChild = m_conODS.prepareStatement(strVARWSDESCChildSQL);
                    psVARWSDESCChild.setInt(1, iRootID);
                    // itsa union so we have to double up...
                    psVARWSDESCChild.setInt(2, iRootID);
                    try {
                        rs = psVARWSDESCChild.executeQuery();
                        ReturnDataResultSet rdrsTemp = new ReturnDataResultSet(rs);
                        if (rdrsChild != null) {
                            rdrsChild.addAll(rdrsTemp);
                        } else {
                            rdrsChild = rdrsTemp;
                        }
                        rdrsTemp = null;
                    }
                    finally {
                        if (rs != null) {
                            rs.close();
                            rs = null;
                        }
                        if (psVARWSDESCChild != null) {
                            psVARWSDESCChild.close();
                            psVARWSDESCChild = null;
                        }
                    }
                }

            }
            if (strRootType.equals("OF") && strChildType.equals("OF")) {
                psOFChild0 = m_conODS.prepareStatement(strOFChild0SQL);
                psOFChild0.setInt(1, iRootID);
                psOFChild0.setString(2, strChildAttCode);
                try {
                    rs = psOFChild0.executeQuery();
                    rdrsChild = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psOFChild0 != null) {
                        psOFChild0.close();
                        psOFChild0 = null;
                    }
                }
            }
            if (strRootType.equals("OF") && strChildType.equals("PORT")) {
                psOFPORTChild1 = m_conODS.prepareStatement(strOFPORTChild1SQL);
                psOFPORTChild1.setInt(1, iRootID);
                psOFPORTChild1.setInt(2, iRootID);
                psOFPORTChild1.setInt(3, iRootID);
                psOFPORTChild1.setInt(4, iRootID);
                try {
                    rs = psOFPORTChild1.executeQuery();
                    rdrsChild = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psOFPORTChild1 != null) {
                        psOFPORTChild1.close();
                        psOFPORTChild1 = null;
                    }
                }
            }
            if (strRootType.equals("VAR") && strChildType.equals("PORT")) {
                psVARPORTChild1 = m_conODS.prepareStatement(strVARPORTChild1SQL);
                psVARPORTChild1.setInt(1, iRootID);
                psVARPORTChild1.setInt(2, iRootID);
                try {
                    rs = psVARPORTChild1.executeQuery();
                    rdrsChild = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psVARPORTChild1 != null) {
                        psVARPORTChild1.close();
                        psVARPORTChild1 = null;
                    }
                }
            }
            if (strRootType.equals("OF") && strChildType.equals("SEC")) {
                psOFSECChild1 = m_conODS.prepareStatement(strOFSECChild1SQL);
                psOFSECChild1.setInt(1, iRootID);
                psOFSECChild1.setInt(2, iRootID);
                try {
                    rs = psOFSECChild1.executeQuery();
                    rdrsChild = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psOFSECChild1 != null) {
                        psOFSECChild1.close();
                        psOFSECChild1 = null;
                    }
                }
            }
            if (strRootType.equals("VAR") && strChildType.equals("SEC")) {
                psVARSECChild1 = m_conODS.prepareStatement(strVARSECChild1SQL);
                psVARSECChild1.setInt(1, iRootID);
                //psVARSECChild1.setInt(2,iRootID);
                try {
                    rs = psVARSECChild1.executeQuery();
                    rdrsChild = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psVARSECChild1 != null) {
                        psVARSECChild1.close();
                        psVARSECChild1 = null;
                    }
                }
            }
            if (strRootType.equals("OF") && (strChildType.equals("PSL") || strChildType.equals("PSLAVAIL"))) {
                psOFPSLChild1 = m_conODS.prepareStatement(strOFPSLChild1SQL);
                psOFPSLChild1.setInt(1, iRootID);
                try {
                    rs = psOFPSLChild1.executeQuery();
                    rdrsChild = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psOFPSLChild1 != null) {
                        psOFPSLChild1.close();
                        psOFPSLChild1 = null;
                    }
                }
            }
            if (strRootType.equals("VAR") && (strChildType.equals("PSL") || strChildType.equals("PSLAVAIL"))) {
                psVARPSLChild1 = m_conODS.prepareStatement(strVARPSLChild1SQL);
                psVARPSLChild1.setInt(1, iRootID);
                try {
                    rs = psVARPSLChild1.executeQuery();
                    rdrsChild = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psVARPSLChild1 != null) {
                        psVARPSLChild1.close();
                        psVARPSLChild1 = null;
                    }
                }
            }
            if (strRootType.equals("CSOL") && strChildType.equals("WAR")) {
                psCSOLWARChild1 = m_conODS.prepareStatement(strCSOLWARChild1SQL);
                psCSOLWARChild1.setInt(1, iRootID);
                try {
                    rs = psCSOLWARChild1.executeQuery();
                    rdrsChild = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psCSOLWARChild1 != null) {
                        psCSOLWARChild1.close();
                        psCSOLWARChild1 = null;
                    }
                }
            }
            if (strRootType.equals("CVAR") && strChildType.equals("WAR")) {
                psCVARWARChild1 = m_conODS.prepareStatement(strCVARWARChild1SQL);
                psCVARWARChild1.setInt(1, iRootID);
                try {
                    rs = psCVARWARChild1.executeQuery();
                    rdrsChild = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psCVARWARChild1 != null) {
                        psCVARWARChild1.close();
                        psCVARWARChild1 = null;
                    }
                }
            }
            if (strRootType.equals("CVAR") && strChildType.equals("WS")) {
                psCVARWSChild1 = m_conODS.prepareStatement(strCVARWSChild1SQL);
                psCVARWSChild1.setInt(1, iRootID);
                psCVARWSChild1.setInt(2, iRootID);
                try {
                    rs = psCVARWSChild1.executeQuery();
                    rdrsChild = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psCVARWSChild1 != null) {
                        psCVARWSChild1.close();
                        psCVARWSChild1 = null;
                    }
                }
            }
            if (strRootType.equals("CVAR") && strChildType.equals("FM")) {
                psCVARFMChild1 = m_conODS.prepareStatement(strCVARFMChild1SQL);
                psCVARFMChild1.setInt(1, iRootID);
                try {
                    rs = psCVARFMChild1.executeQuery();
                    rdrsChild = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psCVARFMChild1 != null) {
                        psCVARFMChild1.close();
                        psCVARFMChild1 = null;
                    }
                }
            }
            if (strRootType.equals("OF") && strChildType.equals("CDR")) {
                if (psOFCDRChild == null) {
                    psOFCDRChild = m_conODS.prepareStatement(strOFCDRChildSQL);
                } else {
                    psOFCDRChild.clearParameters();
                }

                D.ebug(D.EBUG_DETAIL, "in CDR Children search for OF:" + iRootID);

                psOFCDRChild.setInt(1, iRootID);
                psOFCDRChild.setInt(2, iRootID);
                try {
                    rs = psOFCDRChild.executeQuery();
                    rdrsChild = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psOFCDRChild != null) {
                        psOFCDRChild.close();
                        psOFCDRChild = null;
                    }
                }
            }
            if (strRootType.equals("VAR") && strChildType.equals("CDR")) {
                if (psVARCDRChild == null) {
                    psVARCDRChild = m_conODS.prepareStatement(strVARCDRChildSQL);
                } else {
                    psVARCDRChild.clearParameters();
                }

                psVARCDRChild.setInt(1, iRootID);
                try {
                    rs = psVARCDRChild.executeQuery();
                    rdrsChild = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psVARCDRChild != null) {
                        psVARCDRChild.close();
                        psVARCDRChild = null;
                    }
                }
            }
            // now, process our children to rollup!!!..
            //  - remember, there are NLSID's we have to sort out...
            if (rdrsChild != null) {
                D.ebug(D.EBUG_DETAIL, "rdrsChild size = " + rdrsChild.size());
                // we need to check if the attcodes coming back are anything we care about...
                //
                // Basically, if nothing comes back from the rdrs which matches the attribute we found, then that
                // means we don't want this attribute anymore --> delete.
                //
                // let's sort out one's to delete...
                D.ebug(D.EBUG_DETAIL, "getMultiAttributeMappings for " + strChildAttCode);
                String[] saMappings = ODSServerProperties.getMultiAttributeMappings(m_strODSSchema, strChildAttCode);
                Vector vctDeletes = new Vector();

                ATTMAP_LOOP:for (int ig = 0; ig < saMappings.length; ig++) {
                    String strAttCodeMap = saMappings[ig].trim();
                    boolean bFound = false;
                    for (int ib = 0; ib < rdrsChild.size(); ib++) {
                        String strRdrsAttCode = rdrsChild.getColumn(ib, 3);
                        D.ebug(D.EBUG_SPEW,
                               ig + "," + ib + ":" + "strAttCodeMap:\"" + strAttCodeMap + "\", strRdrsAttCode\"" + strRdrsAttCode +
                               "\"");
                        if (strAttCodeMap.equals(strRdrsAttCode)) {
                            continue ATTMAP_LOOP;
                        }
                    }
                    if (!bFound) {
                        vctDeletes.addElement(strAttCodeMap);
                    }
                }

                for (int ii = 0; ii < vctDeletes.size(); ii++) {
                    String strDelAtt = (String) vctDeletes.elementAt(ii);
                    deleteMultiAttributeRecordForAttribute(strRootType, iRootID, strDelAtt);
                }

                if (rdrsChild.size() > 0) {
                    D.ebug(D.EBUG_DETAIL, "update/insert");
                    // now insert...
                    processMultiAttributeRdrs(rdrsChild, false);
                } else {
                    D.ebug(D.EBUG_DETAIL, "delete");
                    deleteMultiAttributeRecordAllMappings(strRootType, iRootID, strChildAttCode);
                }
            }
            D.ebug(D.EBUG_DETAIL,
                   "Timing MultiAttributes processing (Net), took :" + sw.finish() + "  [" + rdrsChild.size() + "] entities.");
            if (bCommit) {
                D.ebug(D.EBUG_DETAIL, "committing...");
                m_conODS.commit();
                bCommit = false;
                D.ebug(D.EBUG_DETAIL, "...committing done.");
            }
        } // END final child fetch root
    }

    /**
     * This is where we have an unlinked or linked relator which impacts a multiattribute record.
     */
    private final void popMultiAttributeRootForRel(EntityItem _ei) throws SQLException {
        String strRelType = _ei.getEntityType();

        int iRelID = _ei.getEntityID();

        D.ebug(D.EBUG_DETAIL, "popMultiAttributeRootForRel(\"" + strRelType + "\"," + iRelID);

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        PreparedStatement ps = null;
        //
        String strSQL = null;
        String strChildType = null;
        //
        String[] saPaths = ODSServerProperties.getMultiAttributeRelatorPath(m_strODSSchema, strRelType);
        for (int ig = 0; ig < saPaths.length; ig++) {
            String strPath = saPaths[ig];
            strPath = strPath.trim();
            D.ebug(D.EBUG_DETAIL, "popMultiAttributeRootForRel: found path " + strPath + " for " + strRelType);
            // so ultimately we want to store:
            // RootEntityType : RootEntityID : AttributeCode
            // We have a removed or added relator type.
            // Let's get 1) the path up to the top, 2) the child entity
            // REMEMBER: we don't care about the childID at this point. Just the child typ + RootID!!!!
            //
            //
            // Ok, this is the twice-removed case
            StringTokenizer st = new StringTokenizer(strPath, ".");
            String strRootType = st.nextToken().trim();
            strPath = st.nextToken().trim();
            boolean bTwiceRemoved = false;
            if (strPath.indexOf(":") > 0) {
                bTwiceRemoved = true;
            }
            //

            //
            //
            // This gets T-ricky....
            // we can always get the parent from PDH object if it is dircetly attached to Relator in question. Easy.
            // - if two up, try and grab from ODS. If it isn't there, it just means that the one-up relator will be processed
            //   at some point during this run so we're covered.
            //
            if (bTwiceRemoved) {
                st = new StringTokenizer(strPath, ":");
                String strRelTypeUp = st.nextToken().trim();
                strChildType = st.nextToken().trim();
                //
                EntityItem eiUp = (EntityItem) _ei.getUpLink(0);
                int iUpID = eiUp.getEntityID();
                //
                strSQL = "SELECT DISTINCT R1.ID1 FROM  " + m_strODSSchema + "." + strRelTypeUp + " R1 WHERE " +
                    " R1.VALTO > CURRENT TIMESTAMP AND R1.ID2 = " + iUpID;
            } else { // We just want the fargin parent!!
                //strSQL = "SELECT DISTINCT ID1 FROM " + m_strODSSchema + "." + _strRelType + " WHERE VALTO > CURRENT TIMESTAMP AND ENTITYID = " + _iRelID;
                strChildType = strPath.trim();
                EntityItem eiRoot = (EntityItem) _ei.getUpLink(0);
                int iRootID = eiRoot.getEntityID();
                strSQL = "(no SQL required) We are getting parent:" + strRootType + " (" + eiRoot.getEntityType() + ")" + ":" +
                    iRootID;
                // hacky, but since we already have code for it below...
                rdrs = new ReturnDataResultSet();
                ReturnDataRow rdr = new ReturnDataRow();
                rdr.addElement(String.valueOf(iRootID));
                rdrs.addElement(rdr);
            }
            D.ebug(D.EBUG_DETAIL, "popMultiAttributeRootForRel:SQL:" + strSQL);

            if (bTwiceRemoved) {
                ps = m_conODS.prepareStatement(strSQL);
                //ps.setInt(1,_iRelID);
                try {
                    rs = ps.executeQuery();
                    rdrs = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (ps != null) {
                        ps.close();
                        ps = null;
                    }
                }
            }

            D.ebug(D.EBUG_DETAIL, "popMultiAttributeRootForRel:rdrs size:" + rdrs.size());
            // NOW we have a rootType,rootID,childType...we need to fish out the relevant attributecodes of child
            // (probably a more efficient way to do this but for now we have code that uses
            //  rootType:rootID:childType:attcode so let's have at it..)
            //


            for (int k = 0; k < rdrs.size(); k++) {
                int iRootID = rdrs.getColumnInt(k, 0);
                // let's get a child EntityGroup...we probably want to store this in memory...
                EntityGroup eg = (EntityGroup) m_HashMultiAttEntityGroups.get(strChildType);
                if (eg == null) {
                    try {
                        eg = new EntityGroup(null, m_dbPDH, m_prof, strChildType, "Extract");
                        m_HashMultiAttEntityGroups.put(eg.getEntityType(), eg);
                    }
                    catch (MiddlewareException x1) {
                        D.ebug(D.EBUG_ERR, "popMultiAttributeRootForRel:ERROR creating EntityGroup for " + strChildType);
                        x1.printStackTrace(System.err);
                    }
                }
                for (int iy = 0; iy < eg.getMetaAttributeCount(); iy++) {
                    EANMetaAttribute ma = eg.getMetaAttribute(iy);
                    if (ODSServerProperties.getRollupAttributeMappings(m_strODSSchema, eg.getEntityType(), ma.getAttributeCode()).
                        length > 0) {
                        String strAttCode = ma.getAttributeCode();
                        String strRootKey = strRootType + ":" + iRootID + ":" + strChildType + ":" + strAttCode;
                        D.ebug(D.EBUG_DETAIL, "popMultiAttributeRootForRel root key is: " + strRootKey);
                        if (!m_vctRootRollupAttrs.contains(strRootKey)) {
                            m_vctRootRollupAttrs.addElement(strRootKey);
                        }
                    }
                }
            }
        }
    }

    /**
     * This is when we have a child type, childid, attribute
     */
    private final void popMultiAttributeRoot(String _strEntityType, String _strAttCode, int _iEntityID) throws SQLException {
        _strEntityType = _strEntityType.trim();
        _strAttCode = _strAttCode.trim();

        ResultSet rs = null;
        ReturnDataResultSet rdrsRoot = null;
        ReturnDataResultSet rdrsChild = null;
        //
        PreparedStatement psOFRoot0 = null;
        PreparedStatement psOFRoot1 = null;
        PreparedStatement psVARRoot1 = null;
        PreparedStatement psOFPORTRoot1 = null;
        PreparedStatement psVARPORTRoot1 = null;
        PreparedStatement psOFSECRoot1 = null;
        PreparedStatement psVARSECRoot1 = null;
        PreparedStatement psOFPSLRoot1 = null;
        PreparedStatement psVARPSLRoot1 = null;
        PreparedStatement psOFPSLAVAILRoot1 = null;
        PreparedStatement psVARPSLAVAILRoot1 = null;
        PreparedStatement psCSOLWARRoot1 = null;
        PreparedStatement psCVARWARRoot1 = null;
        PreparedStatement psCVARWSRoot1 = null;
        PreparedStatement psCVARFMRoot1 = null;
        PreparedStatement psOFCDRRoot = null;
        PreparedStatement psVARCDRRoot = null;
        //////////////////////////////////////////////////////////////////
        // these are SQLs to run to determine parent rollup entities... //
        //////////////////////////////////////////////////////////////////

        String strVARCDRRootSQL = "select distinct 'VAR', varsbb.ID1  from eccm.varsbb varsbb join eccm.prodattrelator pad on pad.entity1type = 'SBB' and pad.entity1id = varsbb.id2 join    eccm.CDR cdr on pad.entity2type = 'CDR' and cdr.entityid = pad.entity2id where CDR.entityid = (?) ";

        String strOFCDRRootSQL = "select distinct 'OF', pad.entity1id   from eccm.prodattrelator pad join   eccm.CDR cdr on pad.entity2type = 'CDR' and cdr.entityid = pad.entity2id where    pad.entity1type = 'OF' and CDR.entityid = (?) ";

        // find value(s) directly off OF:
        //String strOFRoot0SQL = "SELECT distinct 'OF', of.entityid FROM  eccm.of of join eccm.flag f on f.entitytype = 'OF' and f.entityid = of.entityid and f.attributecode in ('OFAPPROVALS_CERTS') and f.nlsid = of.nlsid " +
        //                       " and f.entityid = (?) and f.attributecode = (?)"; // OFAPPROVALS_CERTS
        String strOFRoot0SQL = "SELECT distinct 'OF', entityid from eccm.of where entityid = (?)";

        // find 'OF' roots for CDR,DD,FM,HDC,MON,NIC,NP,OF,PORT?,PP,PSL,PSLAVAIL,TI,TIF,WAR,WS
        String strOFRoot1SQL = "select distinct 'OF', pad.entity1id from eccm.prodattrelator pad where    pad.entity1type = 'OF' " +
            "   and pad.entity2type = (?) and pad.entity2id = (?) " + " union select distinct 'OF', ofsbb.ID1  from      eccm.ofsbb ofsbb join   eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = ofsbb.id2 where      ofsbb.nlsid = 1" +
            "   and pr.entity2type = (?) and pr.entity2id = (?)";

        // find 'VAR' roots for CDR,DD,FM,HDC,MON,NIC,NP,OF,PORT,PP,PSL,PSLAVAIL,TI,TIF,WAR,WS
        String strVARRoot1SQL =
            "select distinct 'VAR', pad.entity1id  from eccm.prodattrelator pad where    pad.entity1type = 'VAR' " +
            "   and pad.entity2type = (?) and pad.entity2id = (?) " + " union select distinct 'VAR', varsbb.ID1 from    eccm.varsbb varsbb join eccm.prodattrelator pr on pr.entity1type = 'SBB' and pr.entity1id = varsbb.id2 where      varsbb.nlsid = 1" +
            "   and pr.entity2type = (?) and pr.entity2id = (?) ";

        // PORTS
        String strOFPORTRoot1SQL = "select distinct 'OF', ofport.id1  from   eccm.ofport ofport where " + " ofport.id2 = (?) " +
            "union select distinct 'OF', ofsbb.id1  from eccm.ofsbb ofsbb join   eccm.sbbport sbbport on sbbport.id1 = ofsbb.id2 " +
            " and sbbport.id2 = (?) ";

        String strVARPORTRoot1SQL =
            "select distinct 'VAR', varsbb.id1 from  eccm.varsbb varsbb join eccm.sbbport sbbport on sbbport.id1 = varsbb.id2 " +
            " and sbbport.id2 = (?) ";

        // SEC
        String strOFSECRoot1SQL =
            "select distinct 'OF', ofsec.id1 from   eccm.ofsec ofsec join eccm.sec s on s.entityid = ofsec.id2 " +
            " and s.entityid = (?) " + "union select distinct 'OF', ofsbb.id1 from    eccm.ofsbb ofsbb join  eccm.sbbsec sbbsec on sbbsec.id1 = ofsbb.id2 join eccm.sec s on s.entityid = sbbsec.id2 " +
            " and s.entityid = (?)";

        String strVARSECRoot1SQL = "select distinct 'VAR', varsbb.id1 from  eccm.varsbb varsbb join eccm.sbbsec sbbsec on sbbsec.id1 = varsbb.id2 join   eccm.sec s on s.entityid = sbbsec.id2 " +
            " and s.entityid = (?)";

        // PSL
        String strOFPSLRoot1SQL = "select distinct 'OF', ofpsl.id1 from eccm.ofpsl ofpsl where " + "   ofpsl.id2 = (?)";

        String strVARPSLRoot1SQL =
            "select distinct 'VAR', varsbb.id1 from eccm.varsbb varsbb join eccm.sbbpsl sbbpsl on sbbpsl.id1 = varsbb.id2 " +
            " where sbbpsl.id2 = (?)";

        // PSLAVAIL

        String strOFPSLAVAILRoot1SQL = "select distinct 'OF', ofpslavail.id1 from eccm.ofpslavail ofpslavail where  " +
            "   ofpslavail.id2 = (?)";

        String strVARPSLAVAILRoot1SQL = "select distinct 'VAR', varpslavail.id1 from eccm.varpslavail varpslavail where " +
            " varpslavail.id2 = (?)";

        // WAR.WARRANTYTYPE
        String strCSOLWARRoot1SQL = "select distinct 'CSOL', csol.entityid  from     eccm.csol csol join        eccm.csolwar csolwar on csolwar.id1 = csol.entityid and csolwar.nlsid = 1 " +
            " and csolwar.id2 = (?)";

        String strCVARWARRoot1SQL = "select distinct 'CVAR', cvar.entityid   from      eccm.cvar cvar join eccm.cvarwar cvarwar on cvarwar.id1 = cvar.entityid and cvarwar.nlsid = 1  " +
            " and cvarwar.id2 = (?)";

        // WS.WSSPEED
        String strCVARWSRoot1SQL = "select distinct 'CVAR', cvar.entityid  from   eccm.cvar cvar join     eccm.cvarsbb cvarsbb on cvarsbb.id1= cvar.entityid join         eccm.sbbws sbbws on sbbws.id1 = cvarsbb.id2 and sbbws.nlsid = 1  " +
            " and sbbws.id2 = (?)";

        // FM
        String strCVARFMRoot1SQL = "select distinct 'CVAR', cvar.entityid   from       eccm.cvar cvar join     eccm.cvarsbb cvarsbb on cvarsbb.id1= cvar.entityid join         eccm.sbbfm sbbfm on sbbfm.id1 = cvarsbb.id2 and sbbfm.nlsid = 1  " +
            " and sbbfm.id2 = (?)";

        // this is the pdh entitytype/attributecode
        String[] saVals = ODSServerProperties.getRollupAttributeMappings(m_strODSSchema, _strEntityType, _strAttCode);
        // remember, strKey is: EntityType:AttribtueCode:EntityID

        // 1) lets go through and fetch parents (root) of this etype/eid/attcode
        // (this will be OF, SBB, VAR, or CVAR)
        String strKey = _strEntityType + ":" + _strAttCode + ":" + _iEntityID;
        D.ebug(D.EBUG_DETAIL, "processMultiAttributes, checking:" + strKey);
        for (int j = 0; j < saVals.length; j++) {
            String strRoot = saVals[j];
            //D.ebug(D.EBUG_DETAIL," -- " + strRoot);
            //StringTokenizer st2 = new StringTokenizer(strRoot,".");
            String strRootType = strRoot; //st2.nextToken();
            //String strRootAttCodeMapping = st2.nextToken();
            D.ebug(D.EBUG_DETAIL, "processMultiAttributes: strRootType:" + strRootType); // + ",strRootAttCodeMapping:" + strRootAttCodeMapping);
            //
            D.ebug(D.EBUG_DETAIL, "processMultiAttributes:strRootType:\"" + strRootType + "\"");
            D.ebug(D.EBUG_DETAIL, "processMultiAttributes:strEntityType:\"" + _strEntityType + "\"");
            if (strRootType.equals("OF") &&
                (_strEntityType.equals("CDR") || _strEntityType.equals("DD") || _strEntityType.equals("FM") ||
                 _strEntityType.equals("HDC") || _strEntityType.equals("MON") || _strEntityType.equals("NIC") ||
                 _strEntityType.equals("NP") ||
                 //_strEntityType.equals("PORT") ||
                 _strEntityType.equals("PP") ||
                 //_strEntityType.equals("PSL") ||
                 //_strEntityType.equals("PSLAVAIL") ||
                 _strEntityType.equals("TI") || _strEntityType.equals("TIF") || _strEntityType.equals("WAR") ||
                 _strEntityType.equals("WS"))) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block A");
                psOFRoot1 = m_conODS.prepareStatement(strOFRoot1SQL);
                psOFRoot1.setString(1, _strEntityType);
                psOFRoot1.setInt(2, _iEntityID);
                //psOFRoot1.setString(3,_strAttCode);
                // itsa union so we have to double up...
                psOFRoot1.setString(3, _strEntityType);
                psOFRoot1.setInt(4, _iEntityID);
                //psOFRoot1.setString(6,_strAttCode);
                try {
                    rs = psOFRoot1.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psOFRoot1 != null) {
                        psOFRoot1.close();
                        psOFRoot1 = null;
                    }
                }
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes: psOFRoot rdrs size (1):" + rdrsRoot.size());
            }
            if (strRootType.equals("VAR") &&
                (_strEntityType.equals("CDR") || _strEntityType.equals("DD") || _strEntityType.equals("FM") ||
                 _strEntityType.equals("HDC") || _strEntityType.equals("MON") || _strEntityType.equals("NIC") ||
                 _strEntityType.equals("NP") ||
                 //_strEntityType.equals("PORT") ||
                 _strEntityType.equals("PP") ||
                 //_strEntityType.equals("PSL") ||
                 //_strEntityType.equals("PSLAVAIL") ||
                 _strEntityType.equals("TI") || _strEntityType.equals("TIF") || _strEntityType.equals("WAR") ||
                 _strEntityType.equals("WS"))) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block B:" + _strEntityType + ":" + _iEntityID);
                psVARRoot1 = m_conODS.prepareStatement(strVARRoot1SQL);
                psVARRoot1.setString(1, _strEntityType);
                psVARRoot1.setInt(2, _iEntityID);
                //psVARRoot1.setString(3,_strAttCode);
                // itsa union so we have to double up...
                psVARRoot1.setString(3, _strEntityType);
                psVARRoot1.setInt(4, _iEntityID);
                //psVARRoot1.setString(6,_strAttCode);

                try {
                    rs = psVARRoot1.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);

                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psVARRoot1 != null) {
                        psVARRoot1.close();
                        psVARRoot1 = null;
                    }
                }
            }
            if (strRootType.equals("OF") && _strEntityType.equals("OF")) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block C");
                psOFRoot0 = m_conODS.prepareStatement(strOFRoot0SQL);
                psOFRoot0.setInt(1, _iEntityID);
                //psOFRoot0.setString(2,_strAttCode);
                try {
                    rs = psOFRoot0.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psOFRoot0 != null) {
                        psOFRoot0.close();
                        psOFRoot0 = null;
                    }
                }
            }
            if (strRootType.equals("OF") && _strEntityType.equals("PORT")) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block D");
                psOFPORTRoot1 = m_conODS.prepareStatement(strOFPORTRoot1SQL);
                // 2 unions...DWB & Praveen
                psOFPORTRoot1.setInt(1, _iEntityID);
                psOFPORTRoot1.setInt(2, _iEntityID);
                try {

                    ReturnDataResultSet rdrsTemp = rdrsRoot;
                    rs = psOFPORTRoot1.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);
                    if (rdrsTemp != null) {
                        mergeAndSort(rdrsRoot, rdrsTemp, new int[] {0, 1});
                    }

                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psOFPORTRoot1 != null) {
                        psOFPORTRoot1.close();
                        psOFPORTRoot1 = null;
                    }
                }
            }
            if (strRootType.equals("VAR") && _strEntityType.equals("PORT")) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block E");
                psVARPORTRoot1 = m_conODS.prepareStatement(strVARPORTRoot1SQL);
                psVARPORTRoot1.setInt(1, _iEntityID);
                try {

                    ReturnDataResultSet rdrsTemp = rdrsRoot;
                    rs = psVARPORTRoot1.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);
                    if (rdrsTemp != null) {
                        mergeAndSort(rdrsRoot, rdrsTemp, new int[] {0, 1});
                    }

                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psVARPORTRoot1 != null) {
                        psVARPORTRoot1.close();
                        psVARPORTRoot1 = null;
                    }
                }
            }
            if (strRootType.equals("OF") && _strEntityType.equals("SEC")) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block F");
                psOFSECRoot1 = m_conODS.prepareStatement(strOFSECRoot1SQL);
                psOFSECRoot1.setInt(1, _iEntityID);
                psOFSECRoot1.setInt(2, _iEntityID);
                try {

                    ReturnDataResultSet rdrsTemp = rdrsRoot;
                    rs = psOFSECRoot1.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);
                    if (rdrsTemp != null) {
                        mergeAndSort(rdrsRoot, rdrsTemp, new int[] {0, 1});
                    }

                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psOFSECRoot1 != null) {
                        psOFSECRoot1.close();
                        psOFSECRoot1 = null;
                    }
                }
            }
            if (strRootType.equals("VAR") && _strEntityType.equals("SEC")) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block G");
                psVARSECRoot1 = m_conODS.prepareStatement(strVARSECRoot1SQL);
                psVARSECRoot1.setInt(1, _iEntityID);
                try {

                    ReturnDataResultSet rdrsTemp = rdrsRoot;
                    rs = psVARSECRoot1.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);
                    if (rdrsTemp != null) {
                        mergeAndSort(rdrsRoot, rdrsTemp, new int[] {0, 1});
                    }

                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psVARSECRoot1 != null) {
                        psVARSECRoot1.close();
                        psVARSECRoot1 = null;
                    }
                }
            }
            if (strRootType.equals("OF") && _strEntityType.equals("PSL")) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block H");
                psOFPSLRoot1 = m_conODS.prepareStatement(strOFPSLRoot1SQL);
                psOFPSLRoot1.setInt(1, _iEntityID);
                try {

                    ReturnDataResultSet rdrsTemp = rdrsRoot;
                    rs = psOFPSLRoot1.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);
                    if (rdrsTemp != null) {
                        mergeAndSort(rdrsRoot, rdrsTemp, new int[] {0, 1});
                    }

                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psOFPSLRoot1 != null) {
                        psOFPSLRoot1.close();
                        psOFPSLRoot1 = null;
                    }
                }
            }
            if (strRootType.equals("OF") && _strEntityType.equals("PSLAVAIL")) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block I");
                psOFPSLAVAILRoot1 = m_conODS.prepareStatement(strOFPSLAVAILRoot1SQL);
                psOFPSLAVAILRoot1.setInt(1, _iEntityID);
                try {

                    ReturnDataResultSet rdrsTemp = rdrsRoot;
                    rs = psOFPSLAVAILRoot1.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);
                    if (rdrsTemp != null) {
                        mergeAndSort(rdrsRoot, rdrsTemp, new int[] {0, 1});
                    }
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psOFPSLAVAILRoot1 != null) {
                        psOFPSLAVAILRoot1.close();
                        psOFPSLAVAILRoot1 = null;
                    }
                }
            }
            if (strRootType.equals("VAR") && _strEntityType.equals("PSL")) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block J");
                psVARPSLRoot1 = m_conODS.prepareStatement(strVARPSLRoot1SQL);
                psVARPSLRoot1.setInt(1, _iEntityID);
                try {
                    ReturnDataResultSet rdrsTemp = rdrsRoot;
                    rs = psVARPSLRoot1.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);
                    if (rdrsTemp != null) {
                        mergeAndSort(rdrsRoot, rdrsTemp, new int[] {0, 1});
                    }

                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psVARPSLRoot1 != null) {
                        psVARPSLRoot1.close();
                        psVARPSLRoot1 = null;
                    }
                }
            }
            if (strRootType.equals("VAR") && _strEntityType.equals("PSLAVAIL")) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block K");
                psVARPSLAVAILRoot1 = m_conODS.prepareStatement(strVARPSLAVAILRoot1SQL);
                psVARPSLAVAILRoot1.setInt(1, _iEntityID);
                try {

                    ReturnDataResultSet rdrsTemp = rdrsRoot;
                    rs = psVARPSLAVAILRoot1.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);
                    if (rdrsTemp != null) {
                        mergeAndSort(rdrsRoot, rdrsTemp, new int[] {0, 1});
                    }

                    if (rdrsTemp != null) {
                        mergeAndSort(rdrsRoot, rdrsTemp, new int[] {0, 1});
                    }
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psVARPSLAVAILRoot1 != null) {
                        psVARPSLAVAILRoot1.close();
                        psVARPSLAVAILRoot1 = null;
                    }
                }
            }
            if (strRootType.equals("CSOL") && _strEntityType.equals("WAR")) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block L");
                psCSOLWARRoot1 = m_conODS.prepareStatement(strCSOLWARRoot1SQL);
                psCSOLWARRoot1.setInt(1, _iEntityID);
                try {
                    rs = psCSOLWARRoot1.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psCSOLWARRoot1 != null) {
                        psCSOLWARRoot1.close();
                        psCSOLWARRoot1 = null;
                    }
                }
            }
            if (strRootType.equals("CVAR") && _strEntityType.equals("WAR")) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block M");
                psCVARWARRoot1 = m_conODS.prepareStatement(strCVARWARRoot1SQL);
                psCVARWARRoot1.setInt(1, _iEntityID);
                try {
                    rs = psCVARWARRoot1.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psCVARWARRoot1 != null) {
                        psCVARWARRoot1.close();
                        psCVARWARRoot1 = null;
                    }
                }
            }
            if (strRootType.equals("CVAR") && _strEntityType.equals("WS")) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block N");
                psCVARWSRoot1 = m_conODS.prepareStatement(strCVARWSRoot1SQL);
                psCVARWSRoot1.setInt(1, _iEntityID);
                try {
                    rs = psCVARWSRoot1.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psCVARWSRoot1 != null) {
                        psCVARWSRoot1.close();
                        psCVARWSRoot1 = null;
                    }
                }
            }
            if (strRootType.equals("CVAR") && _strEntityType.equals("FM")) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block O");
                psCVARFMRoot1 = m_conODS.prepareStatement(strCVARFMRoot1SQL);
                psCVARFMRoot1.setInt(1, _iEntityID);
                try {
                    rs = psCVARFMRoot1.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psCVARFMRoot1 != null) {
                        psCVARFMRoot1.close();
                        psCVARFMRoot1 = null;
                    }
                }
            }
            if (strRootType.equals("OF") && _strEntityType.equals("CDR")) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block P (OF:" + _iEntityID + ")");
                psOFCDRRoot = m_conODS.prepareStatement(strOFCDRRootSQL);
                psOFCDRRoot.setInt(1, _iEntityID);
                try {

                    ReturnDataResultSet rdrsTemp = rdrsRoot;
                    rs = psOFCDRRoot.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);


					if (rdrsTemp != null) {
					    mergeAndSort(rdrsRoot, rdrsTemp, new int[] {0, 1});
                    }

                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psOFCDRRoot != null) {
                        psOFCDRRoot.close();
                        psOFCDRRoot = null;
                    }
                }
            }
            if (strRootType.equals("VAR") && _strEntityType.equals("CDR")) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes:Block Q");
                psVARCDRRoot = m_conODS.prepareStatement(strVARCDRRootSQL);
                psVARCDRRoot.setInt(1, _iEntityID);
                try {
                    rs = psVARCDRRoot.executeQuery();
                    rdrsRoot = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (psVARCDRRoot != null) {
                        psVARCDRRoot.close();
                        psVARCDRRoot = null;
                    }
                }
            }
            // now, process the roots...
            if (rdrsRoot != null) {
                D.ebug(D.EBUG_DETAIL, "processMultiAttributes (2): psOFRoot rdrs size:" + rdrsRoot.size());
                for (int k = 0; k < rdrsRoot.size(); k++) {
                    String strRootTypeFinal = rdrsRoot.getColumn(k, 0);
                    int iRootIDFinal = rdrsRoot.getColumnInt(k, 1);
                    // root type + root id + child type + child att code (changed att) + root attCode mapping
                    String strRootKey = strRootTypeFinal + ":" + iRootIDFinal + ":" + _strEntityType + ":" + _strAttCode; // + ":" + strRootAttCodeMapping;
                    if (!m_vctRootRollupAttrs.contains(strRootKey)) {
                        m_vctRootRollupAttrs.addElement(strRootKey);
                    }
                }
            }
        }
        //... ok, continue the loop....
    }

    private final void mergeAndSort(ReturnDataResultSet _rdrs1, ReturnDataResultSet _rdrs2, int[] _ai) {
        _rdrs1.addAll(_rdrs2);
        D.ebug(D.EBUG_SPEW, "merge and sort " + _rdrs1.size() + "," + _rdrs2.size());
        new SortUtil().sort(_rdrs1, new int[] {0, 1});
    }

    /**
     * Here is where we process/insert our records to MultiAttr table.
     * Re-used for both net and init.
     */
    private final void processMultiAttributeRdrs(ReturnDataResultSet _rdrs, boolean _bInit) throws SQLException {

        D.ebug(D.EBUG_DETAIL, "processMultiAttributes start, rdrs.size():" + _rdrs.getRowCount());

        //
        // let's get this thing ordered by EntityType, EntityID
        //if(!_bInit) {
        //    D.ebug(D.EBUG_DETAIL,"processMultiAttributes: sorting...");
        //    new SortUtil().sort(_rdrs,new int[]{0,1});
        //}
        //
        int iLastRootID = -1;
        int iLastNLSID = -1;
        String strLastAttCode = null;
        //Hashtable hashValsByNLS = new Hashtable();
        StringBuffer sbRollupValByNLS = new StringBuffer();
        String strRootType = null;
        int iRootID = -2;
        String strRootAttCodeMapping = null;
        int iRollupNLSID = -1;
        //
        //
        for (int k = 0; k < _rdrs.size(); k++) {
            strRootType = _rdrs.getColumn(k, 0);
            iRootID = _rdrs.getColumnInt(k, 1);
            iRollupNLSID = _rdrs.getColumnInt(k, 2);
            strRootAttCodeMapping = _rdrs.getColumn(k, 3); // this is kinda redundant since the val is already stored in strRootAttCodeMapping
            String strOneRollupVal = _rdrs.getColumn(k, 4);
            if (strOneRollupVal == null) {
                D.ebug(D.EBUG_SPEW, "processMultiAttributes: rollup val is null for:" + strRootType + ":" + iRootID + " skipping..");
                continue;
            }
            D.ebug(D.EBUG_SPEW,
                   "processMultiAttributes: one rollup record:" + strRootType + ":" + iRootID + ":" + iRollupNLSID + ":" +
                   strRootAttCodeMapping + ":" + strOneRollupVal);

            if (k % 1000 == 0) {
                D.ebug(D.EBUG_SPEW, "processMultiAttributes: " + strRootType + " " + k + " of " + _rdrs.size() + " records");
            }

            //
            // when processing net changes as usual, this will not get executed, as we are only
            // going to be processing one roottype/rootid at a time.
            //
            // for init, we process all roottypes/ids, **ordered by roottype/rootid**
            // so commit in 'chunks' of like roottype/rootid
            //
            boolean bNextEID = ( (iRootID != iLastRootID) && (iLastRootID > 0));
            boolean bNextNLSID = ( (iRollupNLSID != iLastNLSID) && (iLastNLSID > 0));
            boolean bNextAttCode = ( (strLastAttCode != null) && !strLastAttCode.equals(strRootAttCodeMapping));
            if (bNextEID || bNextNLSID || bNextAttCode) {
                int iCommitRootID = iRootID;
                int iCommitNLSID = iRollupNLSID;
                String strCommitAttCode = strRootAttCodeMapping;
                if (bNextEID) {
                    iCommitRootID = iLastRootID;
                }
                if (bNextNLSID) {
                    iCommitNLSID = iLastNLSID;
                }
                if (bNextAttCode) {
                    strCommitAttCode = strLastAttCode;
                }
                processMultiAttrNLSRecord(strRootType, iCommitRootID, strCommitAttCode, iCommitNLSID, sbRollupValByNLS, _bInit);
                sbRollupValByNLS = new StringBuffer();
            }
            //
            //String strKey = String.valueOf(iRollupNLSID) + ":" + strRootAttCodeMapping;
            //StringBuffer sbRollupValByNLS = (StringBuffer)hashValsByNLS.get(strKey);
            if (sbRollupValByNLS == null || sbRollupValByNLS.length() == 0) {
                sbRollupValByNLS = new StringBuffer(strOneRollupVal);
            } else {
                //sbRollupValByNLS.append("|");
                sbRollupValByNLS.append(strOneRollupVal);
            }
            // we want to append this at end of one value, even for last value!
            sbRollupValByNLS.append("|");
            //
            //hashValsByNLS.put(strKey,sbRollupValByNLS);
            iLastRootID = iRootID;
            iLastNLSID = iRollupNLSID;
            strLastAttCode = strRootAttCodeMapping;
        }
        // now one last time to catch the final record(s)...
        processMultiAttrNLSRecord(strRootType, iRootID, strRootAttCodeMapping, iRollupNLSID, sbRollupValByNLS, _bInit);
    }

    /**
     * Just split this logic out because its called twice above.
     */
    private void processMultiAttrNLSRecord(String _strRootType, int _iRootID, String _strAttCode, int _iNLSID, StringBuffer _sbVal,
                                           boolean _bInit) throws SQLException {

        String strNLSVal = _sbVal.toString();

        //ok, very hacky...but oh well
        /*
               Vector v = new Vector();
              StringTokenizer st = new StringTokenizer(strNLSVal,"|");
              while(st.hasMoreTokens()) {
            String s = st.nextToken();
            if(!v.contains(s)) {
              v.addElement(s + "|");
             }
               }
               strNLSVal = new String();
               for(int i = 0; i < v.size(); i++) {
         strNLSVal+=((String)v.elementAt(i));
            }
         */
        D.ebug(D.EBUG_DETAIL,
               "processMultiAttributes: final rollup val for " + _strRootType + "." + _strAttCode + ":" + _iRootID + ":" +
               strNLSVal + "(NLS:" + _iNLSID);
        putMultiAttribute(_strRootType, _iRootID, _iNLSID, _strAttCode, strNLSVal, _bInit);
        //}
    }

    /**
     * physically commit to table.
     */
    protected void putMultiAttribute(String _strEntityType, int _iEntityID, int _iNLSID, String _strAttCode, String _strAttValue,
                                     boolean _bInit) throws SQLException {

        boolean bUpdate = false;
        // no need for checks on init!!
        if (!_bInit) {
            bUpdate = entityExistsInMultiAttrTable(_strEntityType, _iEntityID, _strAttCode, _iNLSID);
        }
        PreparedStatement psInsert = null;
        PreparedStatement psUpdate = null;

        String strBaseTabName = ODSServerProperties.getMultiAttributeTableName(m_strODSSchema);
        String strTableName = m_strODSSchema + "." + strBaseTabName;
        String strInsertSQL = "INSERT INTO " + strTableName + " values(?,?,?,?,?,?)";
        String strUpdateSQL = "UPDATE " + strTableName +
            " set ATTRIBUTEVALUE = (?), VALFROM = (?) WHERE ENTITYTYPE = (?) and ENTITYID = (?) AND ATTRIBUTECODE = (?) AND NLSID = (?)";

        if (bUpdate) {
            psUpdate = m_conODS.prepareStatement(strUpdateSQL);
            psUpdate.setString(1, _strAttValue);
            psUpdate.setString(2, m_strNow);
            psUpdate.setString(3, _strEntityType);
            psUpdate.setInt(4, _iEntityID);
            psUpdate.setString(5, _strAttCode);
            psUpdate.setInt(6, _iNLSID);

            try {
                psUpdate.execute();
            }
            catch (Exception exc) {
                D.ebug(D.EBUG_ERR, "Error updating " + _strEntityType + ":" + _iEntityID + ":" + exc.toString());
                exc.printStackTrace(System.err);
            }
            finally {
                psUpdate.close();
                psUpdate = null;
            }
        } else {
            psInsert = m_conODS.prepareStatement(strInsertSQL);
            psInsert.setString(1, m_strNow);
            psInsert.setString(2, _strEntityType);
            psInsert.setInt(3, _iEntityID);
            psInsert.setInt(4, _iNLSID);
            psInsert.setString(5, _strAttCode);
            psInsert.setString(6, _strAttValue);
            //psInsert.setString(7,"");
            //psInsert.setString(8,"");
            try {
                psInsert.execute();
            }
            catch (Exception exc) {
                D.ebug(D.EBUG_ERR, "Error inserting " + _strEntityType + ":" + _iEntityID + ":" + exc.toString());
                exc.printStackTrace(System.err);
            }
            finally {
                psInsert.close();
                psInsert = null;
            }
        }

    }

    private final void deleteMultiAttributeRecordForAttribute(String _strRootType, int _iRootID, String _strAttCodeMapping) throws
        SQLException {

        D.ebug(D.EBUG_DETAIL, "deleteMultiAttributeRecordForAttribute:" + _strRootType + ":" + _iRootID + ":" + _strAttCodeMapping);

        PreparedStatement psDel = null;

        String strDelSQL = "delete from " + m_strODSSchema + "." + ODSServerProperties.getMultiAttributeTableName(m_strODSSchema) +
            " where " + " entitytype = '" + _strRootType + "' and attributecode = '" + _strAttCodeMapping + "' and entityid = " +
            _iRootID;

        D.ebug(D.EBUG_DETAIL, "deleteMultiAttributeRecordForAttribute SQL:" + strDelSQL);

        //D.ebug(D.EBUG_DETAIL,"deleteMultiAttributeRecord setting 1 = " + _strRootType);
        //D.ebug(D.EBUG_DETAIL,"deleteMultiAttributeRecord setting 2 = " + "(" + sbInAtts.toString() + ")");
        //D.ebug(D.EBUG_DETAIL,"deleteMultiAttributeRecord setting 3 = " + _iRootID);

        psDel = m_conODS.prepareStatement(strDelSQL);
        //psDel.setString(1, _strRootType);
        //psDel.setString(2, "(" + sbInAtts.toString() + ")");
        //psDel.setInt(3, _iRootID);
        try {
            psDel.executeUpdate();
        }
        finally {
            if (psDel != null) {
                psDel.close();
                psDel = null;
            }
        }
    }

    private final void deleteMultiAttributeRecordAllMappings(String _strRootType, int _iRootID, String _strAttCode) throws
        SQLException {

        D.ebug(D.EBUG_DETAIL, "deleteMultiAttributeRecordAllMappings:" + _strRootType + ":" + _iRootID + ":" + _strAttCode);

        PreparedStatement psDel = null;

        String[] saMappings = ODSServerProperties.getMultiAttributeMappings(m_strODSSchema, _strAttCode);
        if (saMappings.length == 0) {
            D.ebug(D.EBUG_DETAIL,
                   "deleteMultiAttributeRecordAllMappings: WARNING could not find mapping for:" + _strRootType + "." + _strAttCode);
        }
        StringBuffer sbInAtts = new StringBuffer();
        for (int i = 0; i < saMappings.length; i++) {
            if (i > 0) {
                sbInAtts.append(",");
            }
            sbInAtts.append("'");
            sbInAtts.append(saMappings[i]);
            sbInAtts.append("'");
        }
        D.ebug(D.EBUG_DETAIL,
               "deleteMultiAttributeRecordAllMappings:mapping for:\"" + _strAttCode + "\" is \"" + sbInAtts.toString() + "\"");

        String strDelSQL = "delete from " + m_strODSSchema + "." + ODSServerProperties.getMultiAttributeTableName(m_strODSSchema) +
            " where " + " entitytype = '" + _strRootType + "' and attributecode in (" + sbInAtts.toString() + ") and entityid = " +
            _iRootID;

        D.ebug(D.EBUG_DETAIL, "deleteMultiAttributeRecordAllMappings SQL:" + strDelSQL);

        //D.ebug(D.EBUG_DETAIL,"deleteMultiAttributeRecord setting 1 = " + _strRootType);
        //D.ebug(D.EBUG_DETAIL,"deleteMultiAttributeRecord setting 2 = " + "(" + sbInAtts.toString() + ")");
        //D.ebug(D.EBUG_DETAIL,"deleteMultiAttributeRecord setting 3 = " + _iRootID);

        psDel = m_conODS.prepareStatement(strDelSQL);
        //psDel.setString(1, _strRootType);
        //psDel.setString(2, "(" + sbInAtts.toString() + ")");
        //psDel.setInt(3, _iRootID);
        try {
            psDel.executeUpdate();
        }
        finally {
            if (psDel != null) {
                psDel.close();
                psDel = null;
            }
        }
    }

    protected boolean entityExistsInMultiAttrTable(String _strEntityType, int _iEntityID, String _strAttCode, int _iNLSID) throws
        SQLException {
        D.ebug(D.EBUG_DETAIL,
               "checking entityExistsInMultiAttrTable:" + _strEntityType + ":" + _iEntityID + ":" + _strAttCode + ":" + _iNLSID);

        boolean bFound = false;
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        PreparedStatement ps = null;

        String strBaseTabName = ODSServerProperties.getMultiAttributeTableName(m_strODSSchema);
        String strTableName = m_strODSSchema + "." + strBaseTabName;
        String strSQL = "SELECT count(*) from " + strTableName + " where " + " ENTITYTYPE = (?) AND " + " ENTITYID = (?) AND " +
            " ATTRIBUTECODE = (?) AND " + " NLSID = (?)";
        ps = m_conODS.prepareStatement(strSQL);
        ps.setString(1, _strEntityType);
        ps.setInt(2, _iEntityID);
        ps.setString(3, _strAttCode);
        ps.setInt(4, _iNLSID);
        try {
            rs = ps.executeQuery();
            rdrs = new ReturnDataResultSet(rs);
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }
        if (rdrs.getColumnInt(0, 0) != 0) {
            bFound = true;
        }
        D.ebug(D.EBUG_DETAIL,
               "entityExistsInMultiAttrTable:" + _strEntityType + ":" + _iEntityID + ":" + _strAttCode + ":" + _iNLSID + "?" +
               bFound);
        return bFound;
    }

    protected void resetMultiAttributeTable() throws SQLException {

        String strBaseTabName = ODSServerProperties.getMultiAttributeTableName(m_strODSSchema);
        String strTableName = m_strODSSchema + "." + strBaseTabName;
        String strDropSQL = "DROP TABLE " + strTableName;
        String strTableSQL = "CREATE TABLE " + strTableName + " (" + "VALFROM TIMESTAMP NOT NULL, " +
            "ENTITYTYPE VARCHAR(16) NOT NULL, " + "ENTITYID INTEGER NOT NULL, " + "NLSID INTEGER NOT NULL, " +
            "ATTRIBUTECODE VARCHAR(32) NOT NULL, " + "ATTRIBUTEVALUE VARCHAR(1536) NOT NULL " + ")";

        String strTableSpace = ODSServerProperties.getTableSpace(m_strODSSchema, strBaseTabName);
        String strIndexSpace = ODSServerProperties.getIndexSpace(m_strODSSchema, strBaseTabName);
        String strIndexSQL = " CREATE UNIQUE INDEX " + strTableName + "_PK ON " + strTableName +
            "(ENTITYID, ENTITYTYPE, NLSID, ATTRIBUTECODE)";

        Statement ddlstmt = null;

        try {

            strTableSpace = (strTableSpace.equals("DEFAULT") ? m_strTableSpace : strTableSpace);
            strIndexSpace = (strIndexSpace.equals("DEFAULT") ? m_strIndexSpace : strIndexSpace);
            strTableSQL = strTableSQL + (strTableSpace.equals("DEFAULT") ? "" : " IN " + strTableSpace) +
                (strIndexSpace.equals("DEFAULT") ? "" : " INDEX IN " + strIndexSpace);

            ddlstmt = m_conODS.createStatement();
            try {
                D.ebug(D.EBUG_INFO, "resetMultiAttributeTable:dropping Table:" + strTableName);
                ddlstmt.executeUpdate(strDropSQL);
                m_conODS.commit();
            }
            catch (SQLException ex) {
                D.ebug(D.EBUG_INFO, "resetMultiAttributeTable:Skipping Table Drop:" + strTableName + ":" + ex.getMessage());
            }

            D.ebug(D.EBUG_INFO, "resetMultiAttributeTable:creating table:" + strTableSQL);
            ddlstmt.executeUpdate(strTableSQL);

            // Now the index
            D.ebug(D.EBUG_INFO, "resetMultiAttributeTable:creating index:" + strIndexSQL);
            ddlstmt.executeUpdate(strIndexSQL);
        }
        finally {
            if (ddlstmt != null) {
                ddlstmt.close();
                ddlstmt = null;
                m_conODS.commit();
            }
        }
    }

    /**
     * NLSID == 0 means ALL NLS's.
     * -- else only corsspost for indicated NLSID
     *
     * @param _strEntityType
     * @param _iEntityID
     * @param _iNLSID
     */
    protected void crossPostValFromCheck(String _strEntityType, int _iEntityID, int _iNLSID) {

        String strSQL1 = "SELECT DISTINCT Entity1Type, Entity1ID FROM " + m_strODSSchema + ".PRODATTRELATOR " +
            " WHERE ENTITY2TYPE = '" + _strEntityType + "' AND ENTITY2ID = " + _iEntityID + " AND VALFROM <> '" + m_strNow +
            "' FOR READ ONLY";

        Statement s1 = null;
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        Statement s2 = null;

        try {

            D.ebug(D.EBUG_DETAIL,
                   "crossPostValFromCheck.called for Entity (" + _strEntityType + ":" + _iEntityID + ":" + _iNLSID + ")");

            try {
                s1 = m_conODS.createStatement();
                rs = s1.executeQuery(strSQL1);
                rdrs = new ReturnDataResultSet(rs);
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (s1 != null) {
                    s1.close();
                    s1 = null;
                }
            }

            for (int i = 0; i < rdrs.size(); i++) {
                String strTableName = rdrs.getColumn(i, 0);
                int iEntityID = rdrs.getColumnInt(i, 1);
                String strSQL2 = "UPDATE " + m_strODSSchema + "." + strTableName + " SET VALFROM = '" + m_strNow +
                    "' WHERE ENTITYID = " + iEntityID;
                D.ebug(D.EBUG_DETAIL,
                    "crossPostValFromCheck.updating valfrom for (" + strTableName + ":" + iEntityID + ":NLSID " +
                    (_iNLSID == 0 ? "<ALL NLSIDs>" : String.valueOf(_iNLSID)) + ") to value: " + m_strNow);
                // GAB 052404 - if nlsid <> 1 -> update only for specified NLS
                if (_iNLSID != 0) {
                    strSQL2 = strSQL2 + " AND NLSID = " + _iNLSID;
                }
                //
                try {
                    s2 = m_conODS.createStatement();
                    s2.executeUpdate(strSQL2);
                }
                finally {
                    if (s2 != null) {
                        s2.close();
                        s1 = null;
                    }
                }

                // GAB 2/1/06 - this is where we'll recurse up our tree of cross-post relators
                if(ODSServerProperties.isCrossPostEntity(m_strODSSchema, strTableName)) {
				    crossPostII(strTableName, iEntityID); // etc, etc...
                }

            }
        }
        catch (SQLException ex) {
            D.ebug(D.EBUG_ERR,
                   "crossPostValFromCheck ** ERROR *** Skipping Entity (" + _strEntityType + ":" + _iEntityID + ") Error is: " +
                   ex.getMessage());
        }
    }

    /**
     * crossPostII
     *
     * @param _ei
     *  @author David Bigelow
     */
    protected void crossPostII(String _strEntityType, int _iEntityID) {

        String strTokens = ODSServerProperties.getCrossPostEntities(m_strODSSchema, _strEntityType);

        // We could do all of this in one SQL.
        // but We want to log each change in the log file
        StringTokenizer st = new StringTokenizer(strTokens, ",");
        while (st.hasMoreTokens()) {
            String strPair = st.nextToken();
            StringTokenizer st1 = new StringTokenizer(strPair, ":");
            String strRelatorTable = st1.nextToken();
            String strSQL1 = "SELECT ID1 FROM " + m_strODSSchema + "." + strRelatorTable + " WHERE ID2 = " + _iEntityID +
                " FOR READ ONLY";
            String strParentEntity = st1.nextToken();

            Statement s1 = null;
            ResultSet rs = null;
            Statement s2 = null;
            ReturnDataResultSet rdrs = null;

            D.ebug(D.EBUG_DETAIL,
                   "crossPostII.called for Entity (" + _strEntityType + ":" + _iEntityID + ") = " + strRelatorTable +
                   ":" + strParentEntity);

            try {

                // Make sure we close stuff
                try {
                    s1 = m_conODS.createStatement();
                    rs = s1.executeQuery(strSQL1);
                    rdrs = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (s1 != null) {
                        s1.close();
                        s1 = null;
                    }
                }

                // O.K.  Lets update all the tables we need to update
                for (int i = 0; i < rdrs.size(); i++) {
                    int iEntityID = rdrs.getColumnInt(i, 0);
                    String strSQL2 = "UPDATE " + m_strODSSchema + "." + strParentEntity + " SET VALFROM = '" + m_strNow +
                        "' WHERE ENTITYID = " + iEntityID;
                    D.ebug(D.EBUG_DETAIL,
                           "crossPostII.updating valfrom for (" + strParentEntity + ":" + iEntityID + ") to value: " + m_strNow);

                    try {
                        s2 = m_conODS.createStatement();
                        s2.executeUpdate(strSQL2);
                    }
                    finally {
                        if (s2 != null) {
                            s2.close();
                            s2 = null;
                        }
                    }

                	// GAB 2/1/06 - this is where we'll recurse up our tree of cross-post relators
                	if(ODSServerProperties.isCrossPostEntity(m_strODSSchema, strParentEntity)) {
					    crossPostII(strParentEntity, iEntityID); // etc, etc...
                	}

                }
            }
            catch (SQLException ex) {
                D.ebug(D.EBUG_ERR,
                       "crossPostII ** ERROR *** Skipping Entity (" + _strEntityType + ":" + _iEntityID + ") Error is: " +
                       ex.getMessage());
            }
        }
    }

    /**
     * updateValFrom
     *
     * @param _strEntityType
     * @param _iEntityID
     *  @author David Bigelow
     */
    protected void updateValFrom(String _strEntityType, int _iEntityID) {

        String strSQL2 = "UPDATE " + m_strODSSchema + "." + _strEntityType + " SET VALFROM = '" + m_strNow + "' WHERE ENTITYID = " +
            _iEntityID;

        Statement s2 = null;

        try {
            D.ebug(D.EBUG_DETAIL, "updateValFrom.called for Entity (" + _strEntityType + ":" + _iEntityID + ")");
            try {
                s2 = m_conODS.createStatement();
                s2.executeUpdate(strSQL2);
            }
            finally {
                if (s2 != null) {
                    s2.close();
                    s2 = null;
                }
            }
        }
        catch (SQLException ex) {
            D.ebug(D.EBUG_ERR,
                   "updateValFrom ** ERROR *** Skipping Entity (" + _strEntityType + ":" + _iEntityID + ") Error is: " +
                   ex.getMessage());
        }
    }

    /**
     * entityExistsInODS
     *
     * @param _strEtype
     * @param _iEid
     * @param _iNls
     * @return
     *  @author David Bigelow
     */
    protected boolean entityExistsInODS(String _strEtype, int _iEid, int _iNls) {
        String strCheckExistSQL = "SELECT entityid from " + m_strODSSchema + "." + _strEtype + " WHERE  EntityID = ? AND NLSID = ?";
        boolean bAnswer = false;

        PreparedStatement psCheck = null;
        ResultSet rs = null;

        try {
            try {
                psCheck = m_conODS.prepareStatement(strCheckExistSQL);
                psCheck.setInt(1, _iEid);
                psCheck.setInt(2, _iNls);
                rs = psCheck.executeQuery();
                if (rs.next()) {
                    bAnswer = true;
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (psCheck != null) {
                    psCheck.close();
                    psCheck = null;
                }
            }
        }
        catch (SQLException se) {
            m_dbPDH.debug(D.EBUG_ERR, "entityExistsinODS:ERROR:" + se.getMessage());
            System.exit( -1);
        }
        return bAnswer;
    }

    /**
     * calculateLastRuntime
     *
     *  @author David Bigelow
     */
    protected void calculateLastRuntime() {
        m_strLastRun = getLastRuntime();
    }

    /**
     * getFlagAttributes
     *
     * @param _eg
     * @return
     *  @author David Bigelow
     */
    protected Vector getFlagAttributes(EntityGroup _eg) {
        EANMetaAttribute eanMetaAttr = null;
        Vector vReturn = new Vector();
        for (int i = 0; i < _eg.getMetaAttributeCount(); i++) {
            eanMetaAttr = _eg.getMetaAttribute(i);
            if (eanMetaAttr.getAttributeType().equals("F") || eanMetaAttr.getAttributeType().equals("U") ||
                eanMetaAttr.getAttributeType().equals("S")) {
                vReturn.add(eanMetaAttr.getAttributeCode());
            }
        }
        return vReturn;
    }

    /**
     * deleteExpiredRows
     *
     * @param _strEType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *  @author David Bigelow
     */
    protected void deleteExpiredRows(String _strEType) throws SQLException, MiddlewareException {

        String strDeleteSQL = "DELETE FROM " + m_strODSSchema + "." + _strEType + " WHERE EntityID IN (?)";
        String strEntityList = null;
        String strEnterprise = m_prof.getEnterprise();
        int iRowsUpdated = 0;

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        ReturnStatus returnStatus = new ReturnStatus( -1);
        PreparedStatement ps = null;

        m_dbPDH.debug(D.EBUG_DETAIL, "gbl9004:params:" + strEnterprise + ":" + _strEType + ":" + m_strLastRun + ":" + m_strNow);
        try {
            rs = m_dbPDH.callGBL9004(returnStatus, strEnterprise, _strEType, m_strLastRun, m_strNow);
            rdrs = new ReturnDataResultSet(rs);
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            m_dbPDH.commit();
            m_dbPDH.freeStatement();
            m_dbPDH.isPending();
        }

        /*
         * We have the list of all the expired entities from the PDH.
         *   Now create an update statement to delete all of them from the ODS table
         */

        if (rdrs.size() > 0) { //Get the first one
            strEntityList = rdrs.getColumn(0, 0);
        } else {
            m_dbPDH.debug(D.EBUG_INFO, "deleteExpiredRows: no Entities found to Delete");
            return;
        }

        for (int i = 1; i < rdrs.size(); i++) {
            strEntityList = strEntityList + "," + rdrs.getColumn(i, 0); //and then the rest
        }

        try {
            ps = m_conODS.prepareStatement(strDeleteSQL);
            ps.setString(1, strEntityList);
            iRowsUpdated = ps.executeUpdate();
            m_dbPDH.debug(D.EBUG_INFO,
                          "deleteExpiredRows: " + iRowsUpdated + "Deleted Entities " + strEntityList + " from ODS table " +
                          _strEType);
        }
        catch (SQLException ex) {
            m_dbPDH.debug(D.EBUG_INFO,
                          "deleteExpiredRows: Problem in deleting from ODS table " + _strEType + ":" + strEntityList + ":" +
                          ex.getMessage());
        }
        finally {
            if (ps != null) {
                ps.close();
                ps = null;
            }
        }

    }

    /**
     * checkODSTable
     *
     * @param _eg
     *  @author David Bigelow
     */
    protected void checkODSTable(EntityGroup _eg) {
        Hashtable hTableColumns = new Hashtable();
        String strAlterSQL = "";
        String strEntityType = _eg.getEntityType();
        String strAlterTableSQL = "ALTER TABLE " + m_strODSSchema + "." + strEntityType;
        String strColName = null;
        String[] strAFilter = {
            "TABLE"};

        boolean bAlterTable = false;
        boolean blnfound = false;

        Statement ddlstmt = null;
        ResultSet rs = null;
        Statement stmtAlterTable = null;

        try {
            DatabaseMetaData dbMetaODS = getODSDbMetaData();

            try {
                ddlstmt = m_conODS.createStatement();
                m_dbPDH.debug(D.EBUG_INFO, "checkODSTable:ET:" + strEntityType);
                rs = dbMetaODS.getTables(null, m_strODSSchema, strEntityType, strAFilter);

                if (rs.next()) {
                    blnfound = true;
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (ddlstmt != null) {
                    ddlstmt.close();
                    ddlstmt = null;
                }
            }

            if (!blnfound) { //Table not found...so create a new one
                resetODSTable(_eg);
                return;
            }

            /*
             * Table is found...so now store its columns to compare with the PDH meta
             */
            try {
                rs = dbMetaODS.getColumns(null, "%", strEntityType, "%");
                while (rs.next()) {
                    strColName = rs.getString(4).trim();
                    hTableColumns.put(strColName, " ");
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            }
            // First go after all non lob attributes
            for (int ii = 0; ii < _eg.getMetaAttributeCount(); ii++) {
                EANMetaAttribute ma = _eg.getMetaAttribute(ii);

                if (includeColumn(ma)) {

                    if (hTableColumns.get(ma.getAttributeCode()) != null) { //Column is found..so dont do anything
                        continue;
                    }
                    if (! (ma.getAttributeType().equals("F") || ma.getAttributeType().equals("X") ||
                           ma.getAttributeType().equals("L"))) {
                        bAlterTable = true;
                        m_dbPDH.debug(D.EBUG_INFO, "checkODSTable: New Column Found: " + ma.getAttributeCode());
                        strAlterSQL = strAlterSQL + " ADD COLUMN " + ma.getAttributeCode() + " " + getDBFieldType(ma);
                        if (ma.getAttributeType().equals("U") || ma.getAttributeType().equals("S")) {
                            strAlterSQL = strAlterSQL + " ADD COLUMN " + ma.getAttributeCode() + "_FC CHAR(16)";
                        }
                    }
                }
            }

            // now go after lob attributes (X,FL)
            for (int ii = 0; ii < _eg.getMetaAttributeCount(); ii++) {
                EANMetaAttribute ma = _eg.getMetaAttribute(ii);

                if (includeColumn(ma)) {
                    if (hTableColumns.get(ma.getAttributeCode()) != null) { //Column is found..so dont do anything
                        continue;
                    }
                    if (ma.getAttributeType().equals("F") || ma.getAttributeType().equals("X") || ma.getAttributeType().equals("L")) {
                        bAlterTable = true;
                        m_dbPDH.debug(D.EBUG_INFO, "checkODSTable: New Column Found: " + ma.getAttributeCode());
                        strAlterSQL = strAlterSQL + " ADD COLUMN " + ma.getAttributeCode() + " " + getDBFieldType(ma);
                    }
                }
            }

            //Alter only if any new columns found

            if (!bAlterTable) {
                m_dbPDH.debug(D.EBUG_INFO, "checkODSTable: No Alterations for  strAlterTableSQL");
                return;
            }

            m_dbPDH.debug(D.EBUG_INFO, "checkODSTable: Altering Table " + strAlterTableSQL + " with DDL " + strAlterSQL);

            try {
                stmtAlterTable = m_conODS.createStatement();
                stmtAlterTable.executeUpdate(strAlterTableSQL + " " + strAlterSQL);
            }
            finally {
                if (stmtAlterTable != null) {
                    stmtAlterTable.close();
                    stmtAlterTable = null;
                }
            }

        }
        catch (SQLException ex) {
            m_dbPDH.debug(D.EBUG_ERR,
                          "checkODSTableDef:*error* with AlterStatement:" + ex.getMessage() + NEW_LINE + strAlterTableSQL + " " +
                          strAlterSQL);
            System.exit(0);
        }
    }

    /**
     * getODSDbMetaData
     *
     * @throws java.sql.SQLException
     * @return
     *  @author David Bigelow
     */
    protected DatabaseMetaData getODSDbMetaData() throws SQLException {
        if (m_dbMetaODS == null) {
            m_dbMetaODS = m_conODS.getMetaData();
        }
        return m_dbMetaODS;
    }

    /**
     * setEnglishOnlyFlags
     *
     * @param _eg
     *  @author David Bigelow
     */
    protected void setEnglishOnlyFlags(EntityGroup _eg) {
        m_dbPDH.debug(D.EBUG_DETAIL, "setEnglishOnlyFlags Checking : " + _eg.getEntityType());
        for (int x = 0; x < _eg.getMetaAttributeCount(); x++) {
            EANMetaAttribute ma = _eg.getMetaAttribute(x);
            if (ODSServerProperties.isEnglishOnly(m_strODSSchema, ma.getAttributeCode()) || ma.getAttributeType().equals("S")) {
                D.ebug("ODSMethods.setEnglishOnlyFlags.setting: " + ma + " To US English Only");
                ma.setUSEnglishOnly(true);
            }
        }
    }

    /**
     * includeColumn
     *
     * @param _ma
     * @return
     *  @author David Bigelow
     */
    protected boolean includeColumn(EANMetaAttribute _ma) {
        return (!_ma.getAttributeType().equals("A") && !_ma.getAttributeType().equals("B") &&
            ! (!ODSServerProperties.isMultiFlag(m_strODSSchema) && _ma.getAttributeType().equals("F")) && !isDerivedEntityID(_ma));
    }

    private boolean isDerivedEntityID(EANMetaAttribute _ma) {
        return _ma.getAttributeCode().equals("DERIVED_EID");
    }

    /**
     * generateGeoMap
     *
     *  @author David Bigelow
     */
    protected void generateGeoMap() {

        String strSQL = "SELECT genareaname_fc, nlsid from gbli.gamap";
        Statement stmt = null;
        ResultSet rs = null;

        c_hshGAMap = new Hashtable();

        try {

            D.ebug("generateGeoMap.Starting...");
            try {
                stmt = m_conODS.createStatement();
                rs = stmt.executeQuery(strSQL);
                while (rs.next()) {
                    String strGA = rs.getString(1).trim();
                    String strNLS = rs.getInt(2) + "";
                    if (c_hshGAMap.containsKey(strGA)) {
                        String strNLSs = (String) c_hshGAMap.get(strGA);
                        strNLS = strNLSs + ":" + strNLS;
                    }
                    // Now.. lets place this back to see what we get
                    D.ebug("generateGeoMap.putting values:" + strGA + " (" + strNLS + ")");
                    c_hshGAMap.put(strGA, strNLS);
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }

        }
        catch (SQLException se) {
            m_dbPDH.debug(D.EBUG_ERR, "generateGeoMap:ERROR:" + se.getMessage());
            System.exit( -1);
        }
        D.ebug("generateGeoMap.Finished...");
    }

    /**
     * getGeoNLSArray
     *
     * @param ei
     * @return
     *  @author David Bigelow
     */
    protected int[] getGeoNLSArray(EntityItem ei) {
        String strFlagCode = null;
        String strNLS = null;
        StringTokenizer st = null;

        int[] iAllNLS = {
            1};
        int i = 0;

        EANFlagAttribute fa = (EANFlagAttribute) ei.getAttribute("GENAREANAME");
        if (fa == null) {
            D.ebug("getGeoNLSArray.General Area Attribute does not exist for this entity " + ei.toString());
            return iAllNLS;
        }
        strFlagCode = fa.getFirstActiveFlagCode();

        if (!c_hshGAMap.containsKey(strFlagCode)) {
            D.ebug("getGeoNLSArray.Flagcode not found for general area: " + strFlagCode);
            return iAllNLS;
        }

        strNLS = (String) c_hshGAMap.get(strFlagCode);
        st = new StringTokenizer(strNLS, ":");
        iAllNLS = new int[st.countTokens()];
        while (st.hasMoreTokens()) {
            iAllNLS[i++] = Integer.valueOf(st.nextToken()).intValue();
            D.ebug("getGeoNLSArray.adding: " + iAllNLS[i - 1] + " to the geo calc for " + strFlagCode + " for " + ei);
        }
        return iAllNLS;
    }

    /**
     * syncFKey
     *
     * @param _strEntityType
     * @param _iEntityID
     *  @author David Bigelow
     */
    protected void syncFKey(String _strEntityType, int _iEntityID) {

        String strFKey = ODSServerProperties.getFKeyMap(m_strODSSchema, _strEntityType);
        String strFKeyPair = ODSServerProperties.getFKeyMapPair(m_strODSSchema, _strEntityType);

        String strETable = m_strODSSchema + "." + _strEntityType;
        String strRTable = m_strODSSchema + "." + strFKeyPair;

        String strSQL = " update " + strETable + " " + _strEntityType + " set " + _strEntityType + "." + strFKey + " = " +
            " (select max(id1) from " + strRTable + " " + strFKeyPair + " where  " + strFKeyPair + ".id2 = " + _strEntityType +
            ".entityid) " + " where " + _strEntityType + "." + strFKey + " is null and " + _strEntityType + ".EntityID = " +
            _iEntityID;

        Statement s1 = null;
        D.ebug("syncFKey.Starting for : " + _strEntityType + ":" + _iEntityID + "  For " + strETable + ":" + strRTable);

        try {
            try {
                s1 = m_conODS.createStatement();
                s1.executeUpdate(strSQL);
            }
            finally {
                if (s1 != null) {
                    s1.close();
                    s1 = null;
                }
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        D.ebug("syncFKey.Finishing for : " + _strEntityType + ":" + _iEntityID + "  For " + strETable + ":" + strRTable);

    }

    /**
     * Verbatim:
     * Here is the full requirement for setting withdrawaldate in HVEC land.
     * This is for CSOL, CVAR, CCTO, CB.
     *
     * Set the ODS withdrawal date to PDH withdrawal date if
     * 1. there is a record in ODS and ODS withdrawal date is null
     * 2. there is a record in ODS and ODS & PDH withdrawal dates mismatch (irrespective of PDH withdrawal is in the past or not).
     *
     * Do not set the ODS withdrawal date and do not insert/update the record in the ODS if
     * 1. ODS & PDH withdrawals match.
     * 2. there is no record for this product in the ODS (this means, don't insert the record in the ODS).
     *
     */
    private final boolean isSkipWithdraw(EntityItem _ei) throws SQLException {

        D.ebug("isSkipWithdraw for:" + _ei.getKey());

        String strEntityType = _ei.getEntityType();
        String strAttributeCode = null;
        int iEntityID = _ei.getEntityID();
        String strODSDate = null;
        String strPDHDate = null;
        String strCurrDate = null;

        PreparedStatement ps = null;
        String strSQL = null;
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        if (!ODSServerProperties.isWithdrawCheckEntity(m_strODSSchema, strEntityType)) {
			D.ebug("isSkipWithdraw returning false (1)");
            return false;
        }
        for (int j = 0; j < _ei.getAttributeCount(); j++) {
            EANAttribute att = _ei.getAttribute(j);
            EANMetaAttribute ma = att.getMetaAttribute();
            strAttributeCode = ma.getAttributeCode();
            if (att.toString().equals("")) {
                continue;
            }
            if (!ODSServerProperties.isWithdrawCheckAttribute(m_strODSSchema, strEntityType, strAttributeCode)) {
                continue;
            } else {

                strPDHDate = att.toString().trim();
                strCurrDate = m_strNow.substring(0, 10);
                D.ebug("isSkipWithdraw - strPDHDate:" + strPDHDate + ", strCurrDate:" + strCurrDate);
                if (strPDHDate.compareTo(strCurrDate) > 0) {
                    D.ebug("isSkipWithdraw returning false (2)");
                    return false;
                }

                strSQL = "SELECT " + strAttributeCode + " FROM " + m_strODSSchema + "." + strEntityType + " WHERE ENTITYID = " +
                    iEntityID;
                D.ebug("isSkipWithdraw strSQL:" + strSQL);

                ps = m_conODS.prepareStatement(strSQL);
                try {
                    rs = ps.executeQuery();
                    rdrs = new ReturnDataResultSet(rs);
                }
                finally {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (ps != null) {
                        ps.close();
                        ps = null;
                    }
                }
                D.ebug("isSkipWithdraw rdrs count:" + ( (rdrs == null) ? "null" : String.valueOf(rdrs.getRowCount())));

                if (rdrs.getRowCount() == 0) {
                    if(strPDHDate.compareTo(strCurrDate) < 0) { // skip
                        D.ebug("isSkipWithdraw returning true (1)");
                    	return true;
					} else {
						D.ebug("isSkipWithdraw returning false (3)");
						return false;
					}
                } else if(rdrs.getColumn(0, 0) == null) {
						return false;
				} else {
                    strODSDate = rdrs.getColumn(0,0).trim();
                }
                D.ebug("isSkipWithdraw for " + strEntityType + "." + strAttributeCode + ": ODS=\"" + strODSDate + "\", PDH=\"" +
                       strPDHDate + "\"");
                if (!strPDHDate.equals(strODSDate)) {
					D.ebug("isSkipWithdraw returning false (4)");
                    return false;
                }
                if (strPDHDate.compareTo(strCurrDate) <= 0) {
					D.ebug("isSkipWithdraw returning true (2)");
                    return true;
                }
            }
        }
        D.ebug("isSkipWithdraw returning false (5)");
        return false;
    }

}
