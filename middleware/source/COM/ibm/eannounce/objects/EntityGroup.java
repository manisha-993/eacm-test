// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: EntityGroup.java,v $
// Revision 1.614  2013/11/01 15:59:35  wendy
// PR20627 - set EOD in parent profile for loading cache
//
// Revision 1.613  2013/05/16 21:19:48  wendy
// another perf update for large amt of data
//
// Revision 1.612  2013/05/01 18:34:09  wendy
// perf updates for large amt of data
//
// Revision 1.611  2012/11/08 21:35:21  wendy
// RCQ00210066-WI Ref CQ00014746 use EOD to build cache, rollback if error instead of commit
//
// Revision 1.610  2012/09/05 14:39:22  wendy
// RCQ00213801  Capability enhancement
//
// Revision 1.609  2011/10/05 00:10:22  wendy
// use stringbuffer.tostring instead of new string
//
// Revision 1.608  2010/11/08 20:39:27  wendy
// Replace EANList with Vector and reuse string objects to reduce memory requirements
//
// Revision 1.607  2010/10/18 11:53:14  wendy
// make dereference public
//
// Revision 1.606  2010/07/21 20:28:06  wendy
// add more info to debug msg
//
// Revision 1.605  2010/04/27 17:20:50  wendy
// put longdescription into group when created with rdi
//
// Revision 1.604  2009/08/31 13:16:41  wendy
// MN 38666284 - CQ00022911 continued for duplicate action
//
// Revision 1.603  2009/05/11 15:41:48  wendy
// Support dereference for memory release
//
// Revision 1.602  2009/04/15 20:15:39  wendy
// Maintain a list for lookup to prevent returning the wrong EANObject
//
// Revision 1.601  2009/03/10 21:23:13  wendy
// MN38666284 - CQ00022911:make sure parent exists
//
// Revision 1.600  2009/03/05 22:12:42  wendy
// MN38666284 - CQ00022911:Creating elements from search not linking back to Workgroup
//
// Revision 1.599  2008/04/29 19:27:55  wendy
// MN35270066 VEEdit rewrite
//
// Revision 1.598  2008/01/31 22:56:00  wendy
// Cleanup RSA warnings
//
// Revision 1.597  2008/01/24 15:37:11  wendy
// RQ1112073342 fix to prevent using profile from cache
//
// Revision 1.596  2007/09/07 19:48:34  wendy
// TIR76CM62 - prevent usage of expired flags for WG defaults
//
// Revision 1.595  2007/08/22 18:57:19  bala
// Check for null parent before checking VeEdit object
//
// Revision 1.594  2007/08/03 17:20:41  wendy
// fix mistake in finally block
//
// Revision 1.593  2007/08/02 21:04:25  wendy
// RQ0713072645 Enhancement 2
//
// Revision 1.592  2007/06/19 17:54:40  wendy
// prevent NPE if meta isn't found
//
// Revision 1.591  2007/06/18 21:55:55  wendy
// Prevent null pointers in search request
//
// Revision 1.590  2007/05/29 17:26:27  wendy
// RQ1103065049 support sets of dependent flags
//
// Revision 1.589  2007/04/24 18:16:26  wendy
// Added support for VEEdit delete
//
// Revision 1.588  2007/04/11 19:23:09  wendy
// TIR6YZSJJ can not save attr on relators in VEEDIT
//
// Revision 1.587  2007/02/27 03:51:36  tony
// Fix for WGDefault on VEEdit Phase1
//
// Revision 1.586  2006/10/05 19:27:56  roger
// Fix
//
// Revision 1.585  2006/10/05 16:00:30  roger
// Fix
//
// Revision 1.584  2006/10/05 15:44:43  roger
// Log putBlob calls for caching into TCOUNT table
//
// Revision 1.583  2006/06/16 07:08:26  dave
// more closing memory leaks
//
// Revision 1.582  2006/06/16 07:02:14  dave
// should not be setting part of Dependent Group
//
// Revision 1.581  2006/06/16 06:59:55  dave
// ok.. found it
//
// Revision 1.580  2006/06/16 06:25:04  dave
// EntittyGroup does not need to be
// in the DependentAttributeValue object
//
// Revision 1.579  2006/05/30 21:32:14  dave
// more consice error messages
//
// Revision 1.578  2006/05/01 16:32:51  roger
// Fix #1
//
// Revision 1.577  2006/04/26 21:43:44  tony
//  CR093005678
//  CR0930055856
// Added VALIDATIONRULE
//
// Revision 1.576  2006/04/10 22:32:46  tony
// Fixed defaulting issue
//
// Revision 1.575  2006/03/22 18:15:14  tony
// 6N5NG7
//
// Revision 1.574  2006/03/21 18:50:56  tony
// 6MV4U7 wrapup
//
// Revision 1.573  2006/03/21 00:10:45  tony
// 6MV4U7
//
// Revision 1.572  2006/03/08 00:31:53  joan
// make changes for SetAttributeType
//
// Revision 1.571  2006/02/20 21:39:46  joan
// clean up System.out.println
//
// Revision 1.570  2006/02/20 19:23:50  tony
// 6LY42Z
//
// Revision 1.569  2006/02/13 21:24:30  tony
// TIR USRO-R-WMOY-6LMSP9
//
// Revision 1.568  2006/02/13 19:33:54  tony
// TIR USRO-R-WMOY-6LMSP9
//
// Revision 1.567  2006/01/12 23:14:18  joan
// work on CR0817052746
//
// Revision 1.566  2005/12/08 18:31:28  tony
// fixed null pointer on saveall of VEEdit
//
// Revision 1.565  2005/11/15 16:53:04  tony
// fixed duplicate of VEEdit
//
// Revision 1.564  2005/11/15 15:50:23  tony
// logic repaired
//
// Revision 1.563  2005/11/14 22:07:36  tony
// fixed logic
//
// Revision 1.562  2005/11/14 15:16:45  tony
// VEEdit_Iteration3
// PRODSTRUCT Functionality.
//
// Revision 1.561  2005/11/04 23:02:17  tony
// VEEdit_Iteration2 tuning
//
// Revision 1.560  2005/11/04 14:52:09  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.559  2005/10/12 19:59:32  tony
// VEEdit_Create
//
// Revision 1.558  2005/10/12 19:24:51  tony
// updated veEdit logic
//
// Revision 1.557  2005/10/12 19:17:56  tony
// VEEdit_Create
//
// Revision 1.556  2005/09/28 19:11:13  tony
// TIR USRO-R-TMAY-6GLQLW
//
// Revision 1.555  2005/09/08 17:57:26  tony
// Updated VEEdit Logic to Improve functionality
//
// Revision 1.554  2005/09/02 15:32:58  tony
// VEEdit functionality addition for CR 0815056514
//
// Revision 1.553  2005/05/04 21:11:09  joan
// fix duplicate
//
// Revision 1.552  2005/03/31 22:50:57  gregg
// fixing parent references for cached UniqueAttribtueGroup within EntityGroup
//
// Revision 1.551  2005/03/31 22:18:32  gregg
// more in ownIt()
//
// Revision 1.550  2005/03/11 22:42:53  dave
// removing some auto genned stuff
//
// Revision 1.549  2005/03/07 22:16:46  dave
// fixing null pointer
//
// Revision 1.548  2005/03/07 21:35:28  dave
// throwing in some trace
//
// Revision 1.547  2005/03/04 18:26:22  dave
// Jtest actions for the day
//
// Revision 1.546  2005/03/03 21:25:16  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.545  2005/03/02 21:51:07  dave
// jTesting EntityGroup
//
// Revision 1.544  2005/03/02 17:25:19  dave
// trapping null pointer
//
// Revision 1.543  2005/03/01 01:52:00  dave
// clean up trace
//
// Revision 1.542  2005/03/01 01:05:26  dave
// some trace
//
// Revision 1.541  2005/03/01 00:30:55  dave
// Jtest and TIR testing
//
// Revision 1.540  2005/02/28 23:36:23  dave
// small syntax
//
// Revision 1.539  2005/02/28 23:31:01  dave
// more Jtest
//
// Revision 1.538  2005/02/23 22:07:08  gregg
// cleaning up Exception messagae for DependentAttributeValue
//
// Revision 1.537  2005/02/23 20:30:16  gregg
// workin on more for CR7137 (UniqueAttribute)
//
// Revision 1.536  2005/02/22 19:18:32  gregg
// messageString on Local Rule
//
// Revision 1.535  2005/02/22 17:49:58  gregg
// updating
//
// Revision 1.534  2005/02/21 21:20:37  gregg
// more SearchActionItem API for DependentAttributeValue
//
// Revision 1.533  2005/02/21 20:06:26  gregg
// some more adjustments to DEpAttVal
//
// Revision 1.532  2005/02/21 19:02:43  gregg
// implement Search in DependentAttributeValue
//
// Revision 1.531  2005/02/18 22:49:02  gregg
// fix
//
// Revision 1.530  2005/02/18 22:33:55  gregg
// hasLocalRulegroup
//
// Revision 1.529  2005/02/18 22:31:18  gregg
// LocalRuleGroup
//
// Revision 1.528  2005/02/18 21:49:22  gregg
// remove debugs
//
// Revision 1.527  2005/02/18 19:35:22  gregg
// fix
//
// Revision 1.526  2005/02/18 19:30:32  gregg
// mulitple values in one group(CR 3148)
//
// Revision 1.525  2005/02/18 00:14:15  gregg
// some debugs
//
// Revision 1.524  2005/02/17 19:10:47  gregg
// null otr fix + some debugs
//
// Revision 1.523  2005/02/17 17:42:05  gregg
// allNLS
//
// Revision 1.522  2005/02/01 17:39:30  gregg
// conditional value for UniqueAttribute check
//
// Revision 1.521  2005/01/28 23:07:15  gregg
// updates for DependentAttributeValue
//
// Revision 1.520  2005/01/28 22:19:16  gregg
// 2 more fixes
//
// Revision 1.519  2005/01/28 22:13:43  gregg
// kompyle phixksez
//
// Revision 1.518  2005/01/28 22:06:46  gregg
// DependentAttributeValue
//
// Revision 1.517  2005/01/20 22:57:00  gregg
// cleaning up after the scourge of Rule51
//
// Revision 1.516  2005/01/18 21:46:49  dave
// more parm debug cleanup
//
// Revision 1.515  2005/01/18 00:19:09  gregg
// setRule51Group(egtmp.getRule51Group());
//
// Revision 1.514  2005/01/17 19:27:47  gregg
// change Rule51 constructor
//
// Revision 1.513  2005/01/16 23:27:04  gregg
// fix
//
// Revision 1.512  2005/01/16 23:21:37  gregg
// Rule51
//
// Revision 1.511  2005/01/11 19:39:34  gregg
// fixing
//
// Revision 1.510  2005/01/11 19:06:28  gregg
// more UniqueAttributes
//
// Revision 1.509  2005/01/10 21:47:48  joan
// work on multiple edit
//
// Revision 1.508  2005/01/07 21:26:31  gregg
// setActive on UniqueAttributeGroup
//
// Revision 1.507  2005/01/07 20:57:56  gregg
// ensure all attributes exist in a UniqueAttributeGroup if we're gonna perform checks
//
// Revision 1.506  2005/01/06 21:45:08  gregg
// more UniqueAttGroups
//
// Revision 1.505  2005/01/06 00:16:51  gregg
// add Enterprise to UniqueAttributeGroup constructor
//
// Revision 1.504  2005/01/05 23:57:41  gregg
// add EntityType to UniqueAttributeGroup constructor
//
// Revision 1.503  2005/01/05 23:13:03  gregg
// fix
//
// Revision 1.502  2005/01/05 22:58:26  gregg
// more UniqueAttributeGroup
//
// Revision 1.501  2005/01/05 22:34:25  gregg
// setting up UniqueAttributeGroup on EntityGroup
//
// Revision 1.500  2005/01/05 19:24:08  joan
// add new method
//
// Revision 1.499  2004/12/14 23:18:22  joan
// fix for abr status
//
// Revision 1.498  2004/11/17 21:34:40  joan
// fix in getColumnList for showRelParentChild
//
// Revision 1.497  2004/11/17 20:18:04  gregg
// remove debugs
//
// Revision 1.496  2004/11/16 23:53:45  gregg
// some display traces
//
// Revision 1.495  2004/11/16 22:56:41  gregg
// some fixes found while looking at EntityList Edit constructor
//
// Revision 1.494  2004/11/15 23:06:19  gregg
// notha fix fer keys
//
// Revision 1.493  2004/11/15 22:53:10  gregg
// getColumnList fix
//
// Revision 1.492  2004/11/15 22:35:22  gregg
// work on column orders for ActionItems where parent.entitytype == child.entitytype
//
// Revision 1.491  2004/11/15 19:59:04  gregg
// getColumnList: lets try and get Parent Attribute columns in there when entitytype.child == entitytype.parent
//
// Revision 1.490  2004/11/09 23:58:57  joan
// work on show parent child
//
// Revision 1.489  2004/11/09 21:00:03  joan
// work on showing both parent and child in navigate
//
// Revision 1.488  2004/11/03 23:12:43  gregg
// MetaColumnOrderGroup for NavActionItems
// MetaLinkAttr switch on ActionItem ColumnOrderControl
// Apply ColumnOrderControl for EntityItems
// Cleanup Debugs
//
// Revision 1.487  2004/11/03 19:18:54  gregg
// hasMetaColumnOrderGroup check on EANActionItem
//
// Revision 1.486  2004/11/03 19:15:18  gregg
// storing MetaColumnOrderGroup object in EANActionItem; built on parent EntityList, where applicaple only
//
// Revision 1.485  2004/11/02 23:45:19  gregg
// mnore work on colorder by actionitem
//
// Revision 1.484  2004/11/02 21:44:19  gregg
// more MetaColumnOrder logic based on ActionItemKey
//
// Revision 1.483  2004/10/25 22:59:45  joan
// fix TIR
//
// Revision 1.482  2004/10/25 21:13:06  joan
// work on create parent
//
// Revision 1.481  2004/10/25 17:23:00  joan
// work on create parent
//
// Revision 1.480  2004/10/21 22:45:55  dave
// attempting to speed up rendering by removing the need to
// create a new String buffer
//
// Revision 1.479  2004/09/24 18:15:44  joan
// fixes
//
// Revision 1.478  2004/08/23 16:56:17  dave
// need to publicize some stuff
//
// Revision 1.477  2004/08/17 22:43:31  joan
// work on search
//
// Revision 1.476  2004/07/30 19:48:03  joan
// work on edit
//
// Revision 1.475  2004/07/27 22:40:21  joan
// fix bug
//
// Revision 1.474  2004/06/22 21:39:10  joan
// work on CR
//
// Revision 1.473  2004/06/21 19:06:51  joan
// work on display parent
//
// Revision 1.472  2004/06/21 15:25:42  joan
// fix compile
//
// Revision 1.471  2004/06/18 20:28:38  joan
// work on edit relator
//
// Revision 1.470  2004/06/18 17:11:17  joan
// work on edit relator
//
// Revision 1.469  2004/06/16 20:10:17  joan
// work on CopyActionItem
//
// Revision 1.468  2004/06/08 17:51:31  joan
// throw exception
//
// Revision 1.467  2004/06/08 17:37:11  joan
// add method
//
// Revision 1.466  2004/06/02 23:08:11  dave
// removing trace
//
// Revision 1.465  2004/06/02 22:35:31  dave
// more and more and more trace
//
// Revision 1.464  2004/06/02 22:15:02  dave
// more trace in entity item
//
// Revision 1.463  2004/06/02 21:55:45  dave
// trace
//
// Revision 1.462  2004/04/09 19:37:19  joan
// add duplicate method
//
// Revision 1.461  2004/03/31 17:57:57  gregg
// setCapability("R") on derived attributes.
//
// Revision 1.460  2004/03/31 17:46:13  gregg
// going for derived entityid attribute
//
// Revision 1.459  2004/01/14 18:17:44  dave
// more trimming of stuff
//
// Revision 1.458  2004/01/13 22:41:11  dave
// more space squeezing
//
// Revision 1.457  2004/01/13 21:54:26  dave
// more parent/profile fixes
//
// Revision 1.456  2004/01/13 17:56:33  dave
// more squeezing
//
// Revision 1.455  2004/01/12 21:57:08  dave
// more cleanup
//
// Revision 1.454  2004/01/12 21:48:39  dave
// more space claiming
//
// Revision 1.453  2004/01/09 22:31:54  dave
// some syntax
//
// Revision 1.452  2004/01/09 22:26:06  dave
// perf changes I
//
// Revision 1.451  2003/12/11 18:59:01  dave
// trying to add column order to search in jui
//
// Revision 1.450  2003/12/04 18:33:24  dave
// fixed addrow for deferred create
//
// Revision 1.449  2003/12/03 22:05:25  dave
// more changes
//
// Revision 1.448  2003/12/03 21:38:58  dave
// more deferred locking
//
// Revision 1.447  2003/11/21 18:41:54  dave
// adding deferred creation of restrictiongroup
//
// Revision 1.446  2003/10/30 02:48:19  dave
// trace
//
// Revision 1.445  2003/10/30 00:43:32  dave
// fixing all the profile references
//
// Revision 1.444  2003/10/08 18:22:44  joan
// add getActualColumnListCount and getActualRowListCount methods
//
// Revision 1.443  2003/08/28 16:28:03  joan
// adjust link method to have link option
//
// Revision 1.442  2003/08/25 20:45:42  dave
// more cleanup
//
// Revision 1.441  2003/08/25 20:22:31  dave
// Some cleanup on streamlining searchactionitem
//
// Revision 1.440  2003/08/25 20:06:46  dave
// streamlining the SearchActionItem to not
// carry the full entityGroup upon creation, but to
// go get it .. when the search action item is actually invoked
// with an exec or rexec
//
// Revision 1.439  2003/08/18 21:05:08  dave
// Adding  the sequencing chain to the islocked to not
// induced each cell for being locked in the islocked of
// entityItem (kludge)
//
// Revision 1.438  2003/07/10 19:22:07  dave
// cornered class cast exception
//
// Revision 1.437  2003/07/10 19:00:27  dave
// fixing class cast exception in the getCacheKey
//
// Revision 1.436  2003/07/02 22:11:13  gregg
// check for an Extract's getRoleCodeOverride in getCacheKey() - this could mean gen a different key.
//
// Revision 1.435  2003/07/01 22:13:40  gregg
// check for/use roleCodeOverride in buildEntityGroupFromScratch()method
//
// Revision 1.434  2003/06/25 18:43:58  joan
// move changes from v111
//
// Revision 1.433.2.4  2003/06/25 00:15:26  joan
// fix compile
//
// Revision 1.433.2.3  2003/06/24 23:37:24  joan
// fix WhereUsedActionItem constructor
//
// Revision 1.433.2.2  2003/06/05 18:24:31  joan
// refresh default for attributes in classification list.
//
// Revision 1.433.2.1  2003/06/05 15:26:45  joan
// add System.out for debugging
//
// Revision 1.433  2003/05/28 17:57:21  dave
// Trace Cleanup
//
// Revision 1.432  2003/05/27 20:55:28  dave
// Looking at Where Used
//
// Revision 1.431  2003/05/19 16:03:40  dave
// Clean up for descriptions, and exception flagging
//
// Revision 1.430  2003/05/15 19:27:24  dave
// implementing isReadOnly I
//
// Revision 1.429  2003/05/14 19:34:25  dave
// Making sure cached entitygroup is clear of any Entity Items
//
// Revision 1.428  2003/05/14 17:54:59  dave
// more trace
//
// Revision 1.427  2003/05/14 17:13:49  dave
// trace for extra row in grid
//
// Revision 1.426  2003/05/14 16:09:23  dave
// trace for null pointer
//
// Revision 1.425  2003/05/13 16:11:17  dave
// more trace statements
//
// Revision 1.424  2003/05/12 23:57:17  joan
// change getMetaColumnOrderGroup to public
//
// Revision 1.423  2003/05/09 22:33:03  dave
// more cleanup and trace for controlling ui/non ui needs
//
// Revision 1.422  2003/05/09 20:11:06  dave
// commonize a routine getBlobNow
//
// Revision 1.421  2003/05/09 19:38:39  dave
// making a blobnow
//
// Revision 1.420  2003/05/02 15:49:38  dave
// cleaning up commit and stand alone Create
//
// Revision 1.419  2003/05/01 18:54:29  dave
// too many {
//
// Revision 1.418  2003/05/01 18:23:29  dave
// Create StandAlone I
//
// Revision 1.417  2003/05/01 17:49:13  dave
// Allowing relatorless create
//
// Revision 1.416  2003/05/01 17:38:23  dave
// first pass at EditActionItem controlling the create
//
// Revision 1.415  2003/04/30 21:55:21  dave
// minor fix to comment out refreshclassifications for now on
// entitygroup row select
//
// Revision 1.413  2003/04/28 21:10:01  dave
// more Entity/Role removal logic
//
// Revision 1.412  2003/04/28 20:52:35  gregg
// more autoselect flags
//
// Revision 1.411  2003/04/28 19:37:22  gregg
// auto select flags
//
// Revision 1.410  2003/04/24 18:32:17  dave
// getting rid of traces and system out printlns
//
// Revision 1.409  2003/04/24 16:48:01  dave
// trying to pin point refreshClassifications to trigger when
// a lock was aquired successfully the first time
//
// Revision 1.408  2003/04/23 22:46:38  dave
// fix
//
// Revision 1.407  2003/04/23 22:28:44  dave
// cleanup on getClassification
//
// Revision 1.406  2003/04/23 22:12:58  dave
// clean up and invokation of setClassifications..
//
// Revision 1.405  2003/04/23 21:21:09  dave
// adding all fields back to grid
//
// Revision 1.404  2003/04/16 00:15:40  gregg
// remove DisplayOrder constants
//
// Revision 1.403  2003/04/15 22:05:39  gregg
// removing references to MetaLinkAttribute's DisplayOrder
//
// Revision 1.402  2003/04/14 18:22:36  gregg
// sort by entity1type, entity2type constants (for relators)
//
// Revision 1.401  2003/04/14 15:37:28  dave
// clean up and verification on getMetaLinkGroup
//
// Revision 1.400  2003/04/13 00:08:32  dave
// apply sort order to isSearchLike
//
// Revision 1.399  2003/04/12 23:50:35  dave
// search simplification III
//
// Revision 1.398  2003/04/12 23:36:03  dave
// missspelled search
//
// Revision 1.397  2003/04/12 22:40:36  dave
// syntax
//
// Revision 1.396  2003/04/12 22:31:40  dave
// clean up and reformatting.
// Search Lite weight II
//
// Revision 1.395  2003/04/08 02:38:18  dave
// commit(*)
//
// Revision 1.394  2003/04/02 21:01:02  dave
// removing setperm in GBL7502
//
// Revision 1.393  2003/04/02 19:47:08  dave
// removing the need for addperm in 8140
//
// Revision 1.392  2003/03/28 00:34:45  gregg
// remove more System.outs
//
// Revision 1.391  2003/03/27 23:09:09  dave
// remove trace statement
//
// Revision 1.390  2003/03/27 23:07:22  dave
// adding some timely commits to free up result sets
//
// Revision 1.389  2003/03/27 22:56:24  gregg
// merge conflict fix in getMetaColumnOrderGroup method
//
// Revision 1.388  2003/03/27 22:53:34  gregg
// more MEtaColumnOrderGroup
//
// Revision 1.387  2003/03/27 22:45:16  dave
// speeding up outofcirculation logic
//
// Revision 1.386  2003/03/27 20:06:03  gregg
// 2nd MetaColumnOrderGroup Constructor
//
// Revision 1.385  2003/03/27 19:34:36  gregg
// fix
//
// Revision 1.384  2003/03/27 19:21:51  gregg
// compile fix
//
// Revision 1.383  2003/03/27 18:59:18  gregg
// changes to getMetaColumnOrderGroup to avoid passing EnttiyGroup object
//
// Revision 1.382  2003/03/26 22:21:22  gregg
// some missing paren's
//
// Revision 1.381  2003/03/26 22:15:18  gregg
// in getColumnList: recognize case where getParent() is a MetaEntityList
//
// Revision 1.380  2003/03/26 00:00:57  gregg
// tying off apply MetaColumnOrder to RST
//
// Revision 1.379  2003/03/25 21:14:05  gregg
// only build MetaColumnOrderGroup if isEditLike()
//
// Revision 1.378  2003/03/25 20:31:43  gregg
// more applyColumnOrders for relator case
//
// Revision 1.377  2003/03/25 17:52:27  gregg
// fix null ptr while building mcog
//
// Revision 1.376  2003/03/25 17:34:04  gregg
// some applyColRoders to getColumnList
//
// Revision 1.375  2003/03/24 22:18:28  gregg
// apply MetaColOrder logic to Vertical+Horizontal
//
// Revision 1.374  2003/03/21 00:15:38  gregg
// some prearation for apply MetaColumnOrder Logic, but not enforcing it yet due to code drop...
//
// Revision 1.373  2003/03/20 22:39:34  gregg
// applyColumnOrders to getColumnList
//
// Revision 1.372  2003/03/14 00:11:48  gregg
// compile fix
//
// Revision 1.371  2003/03/13 21:17:21  gregg
// setMetaColumnOrderGroup method
//
// Revision 1.370  2003/03/11 17:23:13  gregg
// change signature of getMetaColumnOrderGroup method
//
// Revision 1.369  2003/03/11 17:11:43  gregg
// kompile ficks
//
// Revision 1.368  2003/03/11 17:10:30  gregg
// getMetaColumnOrderGroup
//
// Revision 1.367  2003/02/19 00:15:24  gregg
// in getColumnList() method -> put some desc's on Implicators so we can later populate filterKey desc's (these are really taken from E.G. columns) for a derived filterGroup
//
// Revision 1.366  2003/01/29 20:34:04  gregg
// setDependentFlagList method made protected
//
// Revision 1.365  2003/01/29 20:33:10  gregg
// setDependentFlagList method
//
// Revision 1.364  2003/01/29 00:15:07  gregg
// inside buildEntityGroupFromScratch method:  removed try{} block around Dep Flags/ State Machine building (gbl8133 logic) and instead check nulls and continue to next for loop iteration if fails
// (this way one bad MetaTransition row won't spoil the bunch)
//
// Revision 1.363  2003/01/24 17:51:05  dave
// pin pointing getCache Exception
//
// Revision 1.362  2003/01/24 17:15:38  dave
// added trace statements to getCache
//
// Revision 1.361  2003/01/21 00:20:35  joan
// adjust link method to test VE lock
//
// Revision 1.360  2003/01/20 18:33:39  gregg
// Test for null MetaFlagAttribute in genOutOfCirculation() method.
// This can result when the current profile's role does not have capability for an OOC attr belonging to this entity.
//
// Revision 1.359  2003/01/14 22:05:05  joan
// adjust removeLink method
//
// Revision 1.358  2003/01/10 00:10:56  dave
// added resetEntityItem on the EntityGroup
//
// Revision 1.357  2003/01/09 18:21:50  gregg
// update RestrictionGroups, ResetGroups in updatePdhMeta method
//
// Revision 1.356  2003/01/08 21:44:04  joan
// add getWhereUsedList
//
// Revision 1.355  2003/01/07 01:01:28  gregg
// in updatePdhMeta method : refresh cache on RequiredGroup updates
//
// Revision 1.354  2003/01/06 23:09:24  gregg
// _rg.setParent(this) in putRestrictionGroup, putRequiredGroup, putResetGroup, putClassificationGroup to ensure parent/child relationship is coupled
//
// Revision 1.353  2003/01/03 21:27:33  gregg
// update/expire RequiredGroups Meta in updatePdhMeta method
//
// Revision 1.352  2003/01/03 20:48:40  gregg
// removeRestrictionGroup, removeRequiredGroup, removeResetGroup methods
//
// Revision 1.351  2002/12/16 20:33:49  gregg
// fix for relator upadtes in updatePdhMeta
//
// Revision 1.350  2002/12/16 19:32:25  gregg
// more better bChange logic in updatePdhMeta
//
// Revision 1.349  2002/12/16 19:06:43  gregg
// in updatePdhMeta method: dont reset cache if no changed made.
//
// Revision 1.348  2002/12/16 18:23:35  gregg
// return a boolean in updatePdhMeta method indicating whether any database updates were performed.
//
// Revision 1.347  2002/12/09 21:52:05  dave
// trying to eliminate null pointer
//
// Revision 1.346  2002/12/05 22:44:17  dave
// syntax
//
// Revision 1.345  2002/12/05 22:15:27  dave
// Out Of Circulation Logic
//
// Revision 1.344  2002/11/21 23:01:58  gregg
// made setCapability method protected (was private)
//
// Revision 1.343  2002/11/19 23:26:55  joan
// fix hasLock method
//
// Revision 1.342  2002/11/19 18:27:42  joan
// adjust lock, unlock
//
// Revision 1.341  2002/11/19 00:59:48  gregg
// compile fix
//
// Revision 1.340  2002/11/19 00:06:26  joan
// adjust isLocked method
//
// Revision 1.339  2002/11/18 21:20:24  gregg
// ignore case logic on performFilter
//
// Revision 1.338  2002/11/18 18:38:16  gregg
// syntax
//
// Revision 1.337  2002/11/18 18:28:10  gregg
// allow use of wildcards on performFilter()
//
// Revision 1.336  2002/11/13 20:48:45  gregg
// fix in updatePdhMeta --> only update ClassificationGroups if Edit Like
//
// Revision 1.335  2002/11/13 19:46:04  gregg
// fix in updatePdhMeta -> only update attributes if edit like
//
// Revision 1.334  2002/11/12 17:18:27  dave
// System.out.println clean up
//
// Revision 1.333  2002/11/11 22:18:53  dave
// changed order of refresh
//
// Revision 1.332  2002/11/08 22:13:01  gregg
// minor fix in updatePdhMeta
//
// Revision 1.331  2002/11/08 21:41:34  gregg
// in updatePdhMeta method -> some more logic for updating ClassificationGroup Meta
//
// Revision 1.330  2002/11/08 19:59:22  gregg
// in updatePdhMeta -> update ClassificationGroup objects
//
// Revision 1.329  2002/11/07 19:06:40  gregg
// setAssoc() method made protected (was private)
//
// Revision 1.328  2002/11/06 22:37:21  gregg
// removed display statements
//
// Revision 1.327  2002/11/06 21:42:20  gregg
// removeClassificationGroup method
//
// Revision 1.326  2002/11/06 19:12:51  gregg
// introduced deep update for updatePdhMeta method
//
// Revision 1.325  2002/11/06 00:48:27  gregg
// some debug stmts in updatePdhMeta for timings
//
// Revision 1.324  2002/11/05 21:14:44  gregg
// made setGlobalClassificationGroup() method public (was private)
//
// Revision 1.323  2002/11/05 00:01:00  dave
// has changes problem
//
// Revision 1.322  2002/11/04 21:31:34  dave
// Trace Statements
//
// Revision 1.321  2002/11/04 21:10:28  dave
// misfit relator/edit scenario.. can never create entity2's directly
//
// Revision 1.320  2002/11/04 21:06:11  dave
// syntax
//
// Revision 1.319  2002/10/31 00:06:39  dave
// added more includeds
//
// Revision 1.318  2002/10/30 22:39:12  dave
// more cleanup
//
// Revision 1.317  2002/10/30 22:36:19  dave
// clean up
//
// Revision 1.316  2002/10/30 22:02:32  dave
// added exception throwing to commit
//
// Revision 1.315  2002/10/29 00:02:54  dave
// backing out row commit for 1.1
//
// Revision 1.314  2002/10/28 23:49:13  dave
// attempting the first commit with a row index
//
// Revision 1.313  2002/10/22 18:12:28  dave
// syntax
//
// Revision 1.312  2002/10/22 18:10:22  dave
// more trace
//
// Revision 1.311  2002/10/22 17:59:45  dave
// trace statements enabled
//
// Revision 1.310  2002/10/22 17:50:04  dave
// misc fixes
//
// Revision 1.309  2002/10/18 20:18:51  joan
// add isMatrixEditable method
//
// Revision 1.308  2002/10/15 23:29:47  gregg
// when updatePdhMeta->expire ALL cache for entityType
//
// Revision 1.307  2002/10/15 18:23:32  gregg
// detachPdhMeta for atts in updatePdhMeta
//
// Revision 1.306  2002/10/15 00:35:39  gregg
// Additional "NoAtts" type purpose for use by MetaEntityList -- should solve the isNavigate() cache inconsitencies
//
// Revision 1.305  2002/10/14 21:47:02  gregg
// in updatePdhMeta method: default new entities to "C" capability
//
// Revision 1.304  2002/10/14 17:02:36  dave
// Trapping null pointer
//
// Revision 1.303  2002/10/10 17:00:26  dave
// Final Search II changes
//
// Revision 1.302  2002/10/09 21:32:56  dave
// added isDynaTable to EANTableWrapper interface
//
// Revision 1.301  2002/10/09 20:38:36  dave
// syntax fix
//
// Revision 1.300  2002/10/09 20:26:37  dave
// making sure we can edit things in an EntityGroup when used
// as part of a SearchActionItem
//
// Revision 1.299  2002/10/09 15:33:25  dave
// DynaSearch II
//
// Revision 1.298  2002/10/07 17:41:37  joan
// add getLockGroup method
//
// Revision 1.297  2002/10/04 21:33:09  dave
// syntax fix
//
// Revision 1.296  2002/10/04 21:21:25  dave
// fix on Exclude from copy.. and Attribute Missing
//
// Revision 1.295  2002/10/04 21:10:05  dave
// null pointer trap
//
// Revision 1.294  2002/10/04 20:32:31  joan
// debug null pointer
//
// Revision 1.293  2002/10/04 20:13:15  joan
// debug null pointer
//
// Revision 1.292  2002/10/04 17:45:23  gregg
// remove some debugs
//
// Revision 1.291  2002/10/04 17:17:15  gregg
// debugging stmts for excludeFromcopy
//
// Revision 1.290  2002/10/04 16:23:08  gregg
// excludeFromCopy
//
// Revision 1.289  2002/10/03 21:49:11  dave
// Found it!
//
// Revision 1.288  2002/10/03 21:03:36  dave
// more dissplay
//
// Revision 1.287  2002/10/03 20:55:42  dave
// Tracing for getColumnList
//
// Revision 1.286  2002/10/03 18:14:23  dave
// ruse of eg.AddRow in the CreateActionItem
// constructor of EntityList
//
// Revision 1.285  2002/10/02 22:56:38  dave
// Need entityList of cart to hook it up properly
//
// Revision 1.284  2002/10/02 22:31:22  dave
// more trace
//
// Revision 1.283  2002/10/02 15:24:52  dave
// only calculate Classifications for EntityGroup columns
// when we are 'editlike'
//
// Revision 1.282  2002/09/27 18:52:56  gregg
// some static sort/filter types arrays mods
//
// Revision 1.281  2002/09/27 17:20:16  dave
// syntax
//
// Revision 1.280  2002/09/27 17:10:58  dave
// made addRow a boolean
//
// Revision 1.279  2002/09/26 22:27:55  dave
// fixing add row , and CreateActionItem in entitylist constructor
// to use the same seed for counting  backwards
//
// Revision 1.278  2002/09/26 19:37:16  gregg
// sort atts by display order added
//
// Revision 1.277  2002/09/26 19:31:06  gregg
// some change to filter logic
//
// Revision 1.276  2002/09/26 17:35:12  gregg
// redfine some sort type constants
//
// Revision 1.275  2002/09/26 17:08:58  gregg
// implement EANSortable list (on EANMetaAttributes)
//
// Revision 1.274  2002/09/25 22:25:09  dave
// stupd sintax
//
// Revision 1.273  2002/09/25 22:17:03  dave
// syntax
//
// Revision 1.272  2002/09/25 22:11:20  dave
// removal of commented out code
//
// Revision 1.271  2002/09/25 22:08:26  dave
// fixing nullpointer in EntityGroup
//
// Revision 1.270  2002/09/25 00:07:16  dave
// syntax
//
// Revision 1.269  2002/09/24 23:58:57  dave
// making sure we pull the cache out properly for EntityGroup
//
// Revision 1.268  2002/09/24 23:28:43  dave
// changed an && to an || in getColumnList for EntityGroup Table
//
// Revision 1.267  2002/09/24 23:13:51  dave
// only show global classifications and the classification atts on create
// in EntityItem getEntityItemTable
//
// Revision 1.266  2002/09/24 23:04:55  dave
// quick syntx fix
//
// Revision 1.265  2002/09/24 22:56:47  dave
// grid Classifications
//
// Revision 1.264  2002/09/23 15:53:17  dave
// syntax
//
// Revision 1.263  2002/09/23 15:32:16  dave
// more changes for Classify
//
// Revision 1.262  2002/09/20 23:56:23  joan
// fix null pointer
//
// Revision 1.261  2002/09/20 22:17:51  dave
// adding dump info for Classification on EntityGroup
//
// Revision 1.260  2002/09/20 20:54:01  dave
// Classification Wave II and other
//
// Revision 1.259  2002/09/20 17:33:25  dave
// add classification to entityGroup
//
// Revision 1.258  2002/09/13 20:08:01  dave
// change for null pointer on canEdit
//
// Revision 1.257  2002/09/10 21:51:35  dave
// syntax
//
// Revision 1.256  2002/09/10 21:35:28  dave
// fixes for canEdit and canCreate
//
// Revision 1.255  2002/09/10 20:13:35  gregg
// resetActionGroupList method
//
// Revision 1.254  2002/09/09 21:17:26  gregg
// getActionGroupList method
//
// Revision 1.253  2002/09/06 20:02:01  dave
// fixing isEditable for EntityGroup,Item, Attribute
//
// Revision 1.252  2002/09/06 17:52:52  dave
// tracing on edit update
//
// Revision 1.251  2002/09/06 17:48:03  gregg
// more sort/filter fixes
//
// Revision 1.250  2002/09/06 17:38:47  gregg
// SortFilterInfo now uses String sort/filter key constants (were ints)
//
// Revision 1.249  2002/09/06 17:15:43  dave
// attempts to honor view only in action item and entity capability
//
// Revision 1.248  2002/09/04 23:11:36  gregg
// if assertions fail in restrictions/depFlag/SM building loop --> print error, but continue to build EntityGroup w/out bailing
//
// Revision 1.247  2002/09/04 21:30:27  gregg
// some debugging for abrs in Extracts
//
// Revision 1.246  2002/08/27 21:34:51  joan
// debug addRow
//
// Revision 1.245  2002/08/21 21:29:59  joan
// debug addRow
//
// Revision 1.244  2002/08/14 22:08:39  bala
// change the generateUpdateEntity call with parm
//
// Revision 1.243  2002/08/08 22:31:30  gregg
// getCompareField(), setCompareField(String) methods for EANComparable interface
//
// Revision 1.242  2002/08/08 20:51:49  joan
// fix setParentEntityItem
//
// Revision 1.241  2002/08/08 20:07:26  joan
// fix setParentEntityItem
//
// Revision 1.240  2002/08/07 22:21:43  gregg
// getDependentFlagList() method
//
// Revision 1.239  2002/07/26 16:35:53  gregg
// FILTER_ATTS_BY_ATTTYPEMAPPING
//
// Revision 1.238  2002/07/19 23:24:34  gregg
// skip dependent flag/state machine element building for Extracts
//
// Revision 1.237  2002/07/19 17:25:23  gregg
// sort/filter for meta display stuff...
//
// Revision 1.236  2002/07/18 18:04:51  gregg
// displayableForFilter
//
// Revision 1.235  2002/07/16 15:38:19  joan
// working on method to return the array of actionitems
//
// Revision 1.234  2002/07/09 21:31:09  gregg
// setEntityItem method
//
// Revision 1.233  2002/07/08 17:53:42  joan
// fix link method
//
// Revision 1.232  2002/07/08 16:05:29  joan
// fix link method
//
// Revision 1.231  2002/06/25 20:36:08  joan
// add create method for whereused
//
// Revision 1.230  2002/06/25 17:49:36  joan
// add link and removeLink methods
//
// Revision 1.229  2002/06/19 15:52:19  joan
// work on add column in matrix
//
// Revision 1.228  2002/06/17 23:53:46  joan
// add addColumn method
//
// Revision 1.227  2002/06/17 18:22:11  gregg
// sortMetaAttributes method
//
// Revision 1.226  2002/06/17 17:15:50  gregg
// made ResetGroup accessr methods public, added sort by entityClass
//
// Revision 1.225  2002/06/14 23:30:06  gregg
// EANComparable stuff
//
// Revision 1.224  2002/06/05 22:18:19  joan
// work on put and rollback
//
// Revision 1.223  2002/06/05 20:32:12  gregg
// putCache is now protected
//
// Revision 1.222  2002/06/05 16:28:49  joan
// add getMatrixValue method
//
// Revision 1.221  2002/06/04 21:38:06  gregg
// getActionGroupMeta method
//
// Revision 1.220  2002/06/03 18:14:26  gregg
// in getActionGroup() method: parent could also be a MetaEntityList
//
// Revision 1.219  2002/05/30 22:49:53  joan
// throw MiddlewareBusinessRuleException when committing
//
// Revision 1.218  2002/05/29 23:44:52  joan
// fix null pointer
//
// Revision 1.217  2002/05/24 17:37:10  gregg
// in updatePdhMeta  method -> entityClass = Entity (for Entities AND Relators)
//
// Revision 1.216  2002/05/21 16:36:01  joan
// remove System.out
//
// Revision 1.215  2002/05/20 23:23:25  joan
// debug setParentEntityItem
//
// Revision 1.214  2002/05/20 21:31:10  joan
// add setParentEntityItem
//
// Revision 1.213  2002/05/20 19:49:59  joan
// fixing error
//
// Revision 1.212  2002/05/20 18:47:58  joan
// when adding new row and creating, set up the parent entity item with entityid = -1
// when parent entitygroup has more than one entity item,
// and throw exception when commit
//
// Revision 1.211  2002/05/20 17:05:09  joan
// working on addRow
//
// Revision 1.210  2002/05/20 16:07:36  joan
// working on addRow
//
// Revision 1.209  2002/05/17 23:39:38  joan
// working on addRow
//
// Revision 1.208  2002/05/17 23:28:28  joan
// debug
//
// Revision 1.207  2002/05/17 23:12:37  joan
// set eiparent to null in addRow
//
// Revision 1.206  2002/05/14 17:47:06  joan
// working on LockActionItem
//
// Revision 1.205  2002/05/13 20:40:33  joan
// add resetLockGroup method
//
// Revision 1.204  2002/05/10 22:04:55  joan
// add hasLock method
//
// Revision 1.203  2002/05/10 20:45:54  joan
// fixing lock
//
// Revision 1.202  2002/05/08 20:19:32  dave
// syntax error fixes
//
// Revision 1.201  2002/05/08 19:56:40  dave
// attempting to throw the BusinessRuleException on Commit
//
// Revision 1.200  2002/05/07 21:51:49  gregg
// replaced gbl1040 w/ gbl8140, gbl1033 w/ gbl8133, gbl1076 w/ gbl8176
//
// Revision 1.199  2002/05/02 23:45:08  gregg
// debugging
//
// Revision 1.198  2002/05/02 22:18:59  gregg
// cache key stuff for Extract
//
// Revision 1.197  2002/05/02 17:34:45  gregg
// for extract, build EANMetaFlagAttributes w/ all NLS flag values
//
// Revision 1.196  2002/05/02 16:49:44  gregg
// isExtract
//
// Revision 1.195  2002/05/01 20:57:15  dave
// fixing alittle commenting and debugging
//
// Revision 1.194  2002/05/01 16:43:15  gregg
// display some more debug info (394...)
//
// Revision 1.193  2002/04/25 19:47:43  joan
// add code for blob
//
// Revision 1.192  2002/04/24 20:28:47  joan
// working on removeRow
//
// Revision 1.191  2002/04/24 18:04:37  joan
// add removeRow method
//
// Revision 1.190  2002/04/24 16:03:54  dave
// minor clean up for ActionGroupBinder
//
// Revision 1.189  2002/04/22 22:18:24  joan
// working on unlock
//
// Revision 1.188  2002/04/22 18:08:38  joan
// add unlock method
//
// Revision 1.187  2002/04/19 22:34:06  joan
// change isLocked interface to include profile as parameter
//
// Revision 1.186  2002/04/19 22:06:19  dave
// making sense of swapkey = resetKey
//
// Revision 1.185  2002/04/19 17:34:22  joan
// fixing compile errors
//
// Revision 1.184  2002/04/19 17:28:44  joan
// fixing error
//
// Revision 1.183  2002/04/19 17:17:02  joan
// change isLocked  interface
//
// Revision 1.182  2002/04/19 16:37:51  dave
// fixing merge conflict
//
// Revision 1.181  2002/04/18 23:21:20  dave
// basic sketch for lock in RowSelectableTable
//
// Revision 1.180  2002/04/18 21:11:37  gregg
// made setRelator public
//
// Revision 1.179  2002/04/18 21:10:30  gregg
// some more updatePdhMeta for relatorTypes
//
// Revision 1.178  2002/04/17 20:17:05  dave
// new XMLAttribute and its MetaPartner
//
// Revision 1.177  2002/04/16 21:55:52  joan
// syntax
//
// Revision 1.176  2002/04/16 21:02:04  gregg
// updatePdhMeta for relator types
//
// Revision 1.175  2002/04/12 22:44:18  joan
// throws exception
//
// Revision 1.174  2002/04/12 21:18:02  dave
// introduced english only concept in EANMetaAttribute
//
// Revision 1.173  2002/04/12 16:42:04  dave
// added isLocked to the tableDef
//
// Revision 1.172  2002/04/11 19:50:41  gregg
// buildEntityGroupfromScratch - skip EANMetaAttributes that throw exceptions
//
// Revision 1.171  2002/04/11 18:15:08  dave
// Trace statement adjustment and null pointer fix
//
// Revision 1.170  2002/04/10 23:54:04  dave
// syntax
//
// Revision 1.169  2002/04/10 23:38:51  dave
// rearranged the col order to show child first on relator
//
// Revision 1.168  2002/04/10 20:44:14  dave
// more closes on io objects
//
// Revision 1.167  2002/04/10 17:31:17  dave
// dump statement move
//
// Revision 1.166  2002/04/10 16:58:15  dave
// better logging during a dump for StateTransitions stuff
//
// Revision 1.165  2002/04/09 23:39:54  dave
// parenthasis problems
//
// Revision 1.164  2002/04/09 23:29:05  dave
// first attempt at making the state transition compile
//
// Revision 1.163  2002/04/09 20:19:58  gregg
// Cacheable interface is now named EANCacheable
//
// Revision 1.162  2002/04/09 18:46:27  gregg
// implement Cacheable for external cache management
//
// Revision 1.161  2002/04/09 18:02:55  gregg
// EANMetaAttribute.setNavigate(b) now performed in EANMetaAttribute class
//
// Revision 1.160  2002/04/08 17:18:53  dave
// Turned on role selectability on Data
//
// Revision 1.159  2002/04/03 20:56:53  dave
// Attempting to share edit and create cache object
//
// Revision 1.158  2002/04/03 18:13:28  dave
// trace statements to find out why we have multiple status flags on the toString
//
// Revision 1.157  2002/04/03 01:25:32  dave
// changes to commit process
//
// Revision 1.156  2002/04/03 00:19:37  dave
// seperated hasChanges from pendingChanges
//
// Revision 1.155  2002/04/02 23:58:29  dave
// trace statements
//
// Revision 1.154  2002/04/02 21:43:54  dave
// syntax
//
// Revision 1.153  2002/04/02 21:25:49  dave
// added hasChanges()
//
// Revision 1.152  2002/04/02 18:17:39  dave
// more refreshRestriction
//
// Revision 1.151  2002/04/02 01:12:08  dave
// first stab at restriction
//
// Revision 1.150  2002/04/01 21:32:54  dave
// missing parans
//
// Revision 1.149  2002/04/01 21:16:02  dave
// bracket fix
//
// Revision 1.148  2002/04/01 19:05:47  dave
// fixing a null pointer
//
// Revision 1.147  2002/03/28 18:52:40  dave
// tweeking the update routine so we have  properly
// set the ReturnRelatorKey, etc
//
// Revision 1.146  2002/03/28 18:29:00  dave
//  checking them all in for syntax
//
// Revision 1.145  2002/03/28 18:18:25  dave
// close the loop on add record
//
// Revision 1.144  2002/03/28 01:19:13  dave
// made sure child is at least writable
//
// Revision 1.143  2002/03/28 00:22:15  dave
// syntax
//
// Revision 1.142  2002/03/28 00:12:23  dave
// syntax fixes
//
// Revision 1.141  2002/03/27 23:51:56  dave
// syntax for addRow, etc
//
// Revision 1.140  2002/03/27 22:34:20  dave
// Row Selectable Table row Add logic. First attempt
//
// Revision 1.139  2002/03/26 22:28:17  dave
// forgot the word new...
//
// Revision 1.138  2002/03/26 22:14:28  dave
// syntax fix
//
// Revision 1.137  2002/03/26 22:04:24  dave
// typo on null pointer trap
//
// Revision 1.136  2002/03/26 21:30:00  dave
// fix null pointer
//
// Revision 1.135  2002/03/26 20:58:11  dave
// adding commit local logic so we have cornered updates
//
// Revision 1.134  2002/03/26 19:18:31  dave
// fixes and patches
//
// Revision 1.133  2002/03/26 19:03:51  dave
// trace and fix for bad parent
//
// Revision 1.132  2002/03/26 18:43:34  dave
// data integrity check for hierarchical structure management
//
// Revision 1.131  2002/03/25 20:33:37  dave
// More trace statements
//
// Revision 1.130  2002/03/23 20:36:58  dave
// fixed update problem and renamed OPICMUpdate to update
//
// Revision 1.129  2002/03/23 01:57:47  dave
// syntax
//
// Revision 1.128  2002/03/23 01:27:39  dave
// more fixes
//
// Revision 1.127  2002/03/23 01:08:26  dave
// first attempt at update
//
// Revision 1.126  2002/03/22 22:14:30  dave
// more fixes
//
// Revision 1.125  2002/03/22 21:26:54  dave
// more syntax cleanup
//
// Revision 1.124  2002/03/22 21:10:34  dave
// trace for addToStack
//
// Revision 1.123  2002/03/21 18:38:57  dave
// added getHelp to the EANTableModel
//
// Revision 1.122  2002/03/21 00:22:56  dave
// adding rollback logic to the rowSelectable table
//
// Revision 1.121  2002/03/20 18:33:55  dave
// expanding the get for the EANAddressable to
// include a verbose boolean to dictate what gets sent back
//
// Revision 1.120  2002/03/18 22:30:45  gregg
// use deactivateBlob() method for cache reset
//
// Revision 1.119  2002/03/15 23:32:18  gregg
// expireCache() when EntityGroup is expired
//
// Revision 1.118  2002/03/15 22:24:34  gregg
// fixes for cache update, etc
//
// Revision 1.117  2002/03/14 23:53:41  gregg
// buildObjectFromScratch(), update cache on pdh update, check for NoCache purpose
//
// Revision 1.116  2002/03/14 02:16:29  dave
// null pointer fix on cache
//
// Revision 1.115  2002/03/14 02:00:12  dave
// fix on clone to zero out parent
//
// Revision 1.114  2002/03/14 01:42:53  dave
// syntax check
//
// Revision 1.113  2002/03/14 01:30:28  dave
// trace in dump for null pointer
//
// Revision 1.112  2002/03/14 00:48:29  dave
// error fixes
//
// Revision 1.111  2002/03/14 00:38:43  dave
// first attempt a caching information for entity group
//
// Revision 1.110  2002/03/13 23:36:50  dave
// more fixes
//
// Revision 1.109  2002/03/13 23:28:37  dave
// import cleanup
//
// Revision 1.108  2002/03/13 23:19:39  dave
// more fixes
//
// Revision 1.107  2002/03/13 23:07:07  dave
// fix for EANFlagAttribute
//
// Revision 1.106  2002/03/13 22:48:24  dave
// First attempt at caching
//
// Revision 1.105  2002/03/13 22:31:18  dave
// syntax
//
// Revision 1.104  2002/03/13 20:27:44  dave
// more try, catch
//
// Revision 1.103  2002/03/13 20:20:59  dave
// more try catch stuff
//
// Revision 1.102  2002/03/13 19:41:19  dave
// moving things inside the try block
//
// Revision 1.101  2002/03/13 19:15:22  dave
// syntax fixes
//
// Revision 1.100  2002/03/13 18:47:06  dave
// syntax fixes
//
// Revision 1.99  2002/03/13 18:28:19  dave
// attempt at creating the putCache information
//
// Revision 1.98  2002/03/12 22:14:50  dave
// missing ')'
//
// Revision 1.97  2002/03/12 01:33:18  gregg
// use detachPdhMeta() to remove attributes (dont expire them)
//
// Revision 1.96  2002/03/11 21:22:39  dave
// syntax for first stage of edit
//
// Revision 1.95  2002/03/08 23:20:24  gregg
// flip-flopped some Long/Short desc's
//
// Revision 1.94  2002/03/08 18:00:01  dave
// nullpointer fixed in setAssoc Logic
//
// Revision 1.93  2002/03/07 23:25:29  dave
// new sp for edit (loading trsTable with all kinds of information)
//
// Revision 1.92  2002/03/07 21:48:41  gregg
// updatePdhMeta - expire description nls stuff
//
// Revision 1.91  2002/03/06 00:18:49  gregg
// added linkCode as a parameter to gbl7507
//
// Revision 1.90  2002/03/05 23:32:16  gregg
// return type EANMetaAttribute on removeMetaAttribute()
//
// Revision 1.89  2002/03/05 22:49:33  gregg
// getMetaLinkAttrRows()..
//
// Revision 1.88  2002/03/05 20:54:36  gregg
// expire/update MetaAttributes in updatePdhMeta()
//
// Revision 1.87  2002/03/05 05:01:26  dave
// more fixes
//
// Revision 1.86  2002/03/05 04:29:16  dave
// more trace and debug
//
// Revision 1.85  2002/03/05 04:17:44  dave
// trace and debug fixes
//
// Revision 1.84  2002/03/05 02:41:05  dave
// null pointer fix
//
// Revision 1.83  2002/03/05 02:18:25  dave
// missing paren's
//
// Revision 1.82  2002/03/05 02:11:50  dave
// more generalizations on EntityGroup and purpose logic
//
// Revision 1.81  2002/03/05 01:55:42  dave
// adding Create as the purpose for pulling all attributes in EntityGroup
// constructor
//
// Revision 1.80  2002/03/05 01:31:16  dave
// more syntax fixes
//
// Revision 1.79  2002/03/02 00:42:44  gregg
// made RequiredGroup/RestrictedGroup accessors public
//
// Revision 1.78  2002/03/02 00:18:32  gregg
// ...entityClass is always "Entity" (for now..)
//
// Revision 1.77  2002/03/01 21:15:24  gregg
// made removeMetaAttribute() public
//
// Revision 1.76  2002/03/01 20:48:03  joan
// fixing getMetaLinkList
//
// Revision 1.75  2002/03/01 20:17:05  dave
// more syntax
//
// Revision 1.74  2002/03/01 19:31:13  dave
// syntax fixes and display corrections
//
// Revision 1.73  2002/03/01 19:00:53  dave
// merged conflicts
//
// Revision 1.72  2002/03/01 18:44:02  gregg
// added meta update methods
//
// Revision 1.71  2002/03/01 18:17:16  joan
// fix getMetaLinkList method
//
// Revision 1.70  2002/03/01 17:22:44  joan
// work on getMetaLinkList method
//
// Revision 1.69  2002/03/01 17:01:54  joan
// working on getMetaLinkList method
//
// Revision 1.68  2002/02/28 22:17:45  gregg
// if no specific purpose is specified -> create no attributes
//
// Revision 1.67  2002/02/28 19:09:22  dave
// adding 7502 and adding navflag to denote nav attributes
//
// Revision 1.66  2002/02/28 00:50:53  dave
// syntax
//
// Revision 1.65  2002/02/28 00:40:20  dave
// more syntax
//
// Revision 1.64  2002/02/28 00:25:23  dave
// syntax errors
//
// Revision 1.63  2002/02/28 00:08:59  dave
// more syntax
//
// Revision 1.62  2002/02/27 23:57:18  dave
// more syntax fixes
//
// Revision 1.61  2002/02/27 23:20:25  dave
// simple syntax fix
//
// Revision 1.60  2002/02/27 23:10:44  dave
// added reset, restriction
//
// Revision 1.59  2002/02/27 21:09:24  dave
// more syntax
//
// Revision 1.58  2002/02/27 20:58:05  dave
// more syntax fixes
//
// Revision 1.57  2002/02/27 20:48:08  dave
// syntax fixes
//
// Revision 1.56  2002/02/27 20:37:16  dave
// adding state machines and the such to entityGroup
//
// Revision 1.55  2002/02/27 00:36:00  dave
// syntax
//
// Revision 1.54  2002/02/27 00:20:17  dave
// modified isDisplayable. to only display Entity Tabs w/ orphaned items
//
// Revision 1.53  2002/02/26 21:43:59  dave
// ensuring I am setting the rs = null after a close
//
// Revision 1.52  2002/02/21 19:26:13  dave
// added Link to static entityList
//
// Revision 1.51  2002/02/21 17:02:50  dave
// change to entitygroup constructor parm (EntityList = em)
//
// Revision 1.50  2002/02/20 22:01:39  dave
// more fixes to snytax
//
// Revision 1.49  2002/02/20 21:45:46  dave
// a bunch of fixes for link and entitygroup visibility
//
// Revision 1.48  2002/02/20 20:43:51  dave
// syntax fixes
//
// Revision 1.47  2002/02/20 20:22:27  dave
// added the isRelator isAssoc logic
//
// Revision 1.46  2002/02/20 20:10:50  dave
// Added the getMetaLink method
//
// Revision 1.45  2002/02/19 17:18:16  dave
// forcing EntityGroup object on parm line
//
// Revision 1.44  2002/02/19 17:09:33  dave
// more changes to equals on NavigateGroup
//
// Revision 1.43  2002/02/19 00:55:43  dave
// fix
//
// Revision 1.42  2002/02/18 23:02:18  dave
// syntax at low level - adding isActive Boolean to denote 'live'
// things in the meta model
//
// Revision 1.41  2002/02/18 19:06:48  dave
// ensuring the entityitem's and metaattributes's parents are the entity group
//
// Revision 1.40  2002/02/18 18:42:17  dave
// adding cart methods
//
// Revision 1.39  2002/02/18 17:52:22  dave
// syntax
//
// Revision 1.38  2002/02/18 17:39:44  dave
// more fixes
//
// Revision 1.37  2002/02/18 17:25:22  dave
// more function add for 1.1
//
// Revision 1.36  2002/02/15 23:07:51  dave
// setting Entity2Type in relator case wrong
//
// Revision 1.35  2002/02/15 20:31:49  dave
// fix for relator ident in EntityGroup
//
// Revision 1.34  2002/02/15 20:13:52  dave
// changed getProfile to look to parent for setting if parent exists
//
// Revision 1.33  2002/02/15 18:06:52  dave
// expanded EAN Table structures
//
// Revision 1.32  2002/02/14 23:11:07  dave
// added method to EntityGroup to getEntityItemsAsArray
//
// Revision 1.31  2002/02/14 01:51:20  dave
// try catch and init var problem
//
// Revision 1.30  2002/02/14 01:44:25  dave
// more syntax fixes
//
// Revision 1.29  2002/02/14 01:21:17  dave
// created a binder for action groups so relator/entity can be shown
//
// Revision 1.28  2002/02/13 21:58:21  dave
// more table stuff
//
// Revision 1.27  2002/02/13 21:42:55  dave
// fixing more syntax
//
// Revision 1.26  2002/02/13 21:32:18  dave
// more table generating stuff
//
// Revision 1.25  2002/02/13 18:53:47  dave
// first attempt at a table model for the entitygroups in the list
//
// Revision 1.24  2002/02/13 17:59:46  dave
// fixing isDisplayable
//
// Revision 1.23  2002/02/13 17:43:55  dave
// provided an isdisplayable function for the entityGroup
//
// Revision 1.22  2002/02/13 00:17:10  dave
// clean up on dumps and adding misc toStrings
//
// Revision 1.21  2002/02/12 23:56:04  dave
// more fixes
//
// Revision 1.20  2002/02/12 23:44:42  dave
// minor fix
//
// Revision 1.19  2002/02/12 23:35:36  dave
// added purpose to the NavActionItem
//
// Revision 1.18  2002/02/12 22:47:01  dave
// more fixes
//
// Revision 1.17  2002/02/12 22:28:18  dave
// adding status attribute object
//
// Revision 1.16  2002/02/12 17:27:38  dave
// added dump info to entityitem
//
// Revision 1.15  2002/02/12 17:14:35  dave
// more dump fixes
//
// Revision 1.14  2002/02/11 19:35:57  dave
// more dump re-arranging
//
// Revision 1.13  2002/02/11 08:46:47  dave
// more fixes to dump and syntax
//
// Revision 1.12  2002/02/11 08:36:43  dave
// more syntax
//
// Revision 1.11  2002/02/11 08:24:52  dave
// more fixes for better dump and debug
//
// Revision 1.10  2002/02/06 00:42:39  dave
// more fixes to base code
//
// Revision 1.9  2002/02/06 00:02:56  dave
// more syntax fixes
//
// Revision 1.8  2002/02/05 23:25:19  dave
// removed a file that was posted in error
//
// Revision 1.7  2002/02/05 23:11:30  dave
// more baseline fixes
//
// Revision 1.6  2002/02/05 18:17:48  dave
// more fixes
//
// Revision 1.5  2002/02/05 18:01:37  dave
// more import statement fixes
//
// Revision 1.4  2002/02/05 17:38:06  dave
// missing bracket
//
// Revision 1.3  2002/02/05 17:26:40  dave
// missing paren
//
// Revision 1.2  2002/02/05 16:39:14  dave
// more expansion of abstract model
//
// Revision 1.1  2002/02/05 00:11:57  dave
// more structure
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import COM.ibm.opicmpdh.objects.Blob;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.transactions.OPICMList;

// Exceptions
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSetGroup;
import COM.ibm.opicmpdh.middleware.LockException;
import java.sql.SQLException;
import java.rmi.RemoteException;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.StringTokenizer;
  
/**
 * This is a container that holds all the information for a MetaEntity
 *
 * @author     davidbig
 * @created    April 11, 2003
 */
public class EntityGroup extends EANMetaEntity implements EANAddressable, EANTableWrapper, EANCacheable, EANComparable, EANSortableList {

    /**
     * @serial
     */
    final static long serialVersionUID = 1L;

    /**
     *  Description of the Field
     */
    public static final String DESCRIPTION = "0";

    private MetaLinkGroup m_mlg = null;
    private String m_strCapability = null;
    private String m_strEntity1Type = null;
    private String m_strEntity2Type = null;
    private boolean m_bRelator = false;
    private boolean m_bAssoc = false;
    private String m_strPurpose = null;
    private boolean m_bParentLike = false;

    private boolean m_bForceMode = false;
    private boolean m_bForceDisplay = false;
    private boolean m_bClassify = false;

    // Permissions
    private boolean m_bRead = true;
    private boolean m_bCreate = false;
    private boolean m_bWrite = false;
    private boolean m_bUsedInSearch = false;

    // Classification, Restriction, Required, and Reset Groups
    //memchg private EANList m_elClassify = null;
    private Vector m_elClassify = null;
    //memchg private EANList m_elRestricted = null;
    private Vector m_elRestricted = null;
   //memchg  private EANList m_elRequired = null;
    private Vector m_elRequired = null;
    //memchg  private EANList m_elReset = null;
    private Vector m_elReset = null;
    //memchg private EANList m_elooc = null;
    private Vector m_elooc = null;
    //memchg private EANList m_elLoc = null;
    private Vector m_elLoc = null;

    // Holds the classification group
    private ClassificationGroup m_clsgGlobal = null;
    private ActionGroupList m_actionGroupList = null;

    private Hashtable m_hashUniqueAttGroups = null;
    private Hashtable m_hashDepAttVal = null;
    private Rule51Group m_r51Grp = null;

    //MetaColumnOrderGroup
    private MetaColumnOrderGroup m_mcog = null;

    // String sorting/filtering constants for EANComparable, EANSortableList
    /**
     * FIELD
     */
    public static String[] c_saSortTypes = {EANMetaAttribute.SORT_BY_LONGDESC, EANMetaAttribute.SORT_BY_SHORTDESC, EANMetaAttribute.SORT_BY_ATTCODE,
        EANMetaAttribute.SORT_BY_ATTTYPE, EANMetaAttribute.SORT_BY_ATTTYPEMAPPING};
    /**
     * FIELD
     */
    public final static String SORT_ATTS_BY_LONGDESC = c_saSortTypes[0];
    /**
     * FIELD
     */
    public final static String SORT_ATTS_BY_SHORTDESC = c_saSortTypes[1];
    /**
     * FIELD
     */
    public final static String SORT_ATTS_BY_ATTCODE = c_saSortTypes[2];
    /**
     * FIELD
     */
    public final static String SORT_ATTS_BY_ATTTYPE = c_saSortTypes[3];
    /**
     * FIELD
     */
    public final static String SORT_ATTS_BY_ATTTYPEMAPPING = c_saSortTypes[4];

    /**
     * FIELD
     */
    public final static String SORT_BY_LONGDESC = "LD";
    /**
     * FIELD
     */
    public final static String SORT_BY_SHORTDESC = "SD";
    /**
     * FIELD
     */
    public final static String SORT_BY_ENTITYTYPE = "ET";
    /**
     * FIELD
     */
    public final static String SORT_BY_ENTITYCLASS = "EC";
    /**
     * FIELD
     */
    public final static String SORT_BY_ENTITY1TYPE = "E1T";
    /**
     * FIELD
     */
    public final static String SORT_BY_ENTITY2TYPE = "E2T";

    private boolean m_bDisplayableForFilter = true;

    /**
     * FIELD
     */
    public static String[] c_saFilterTypes = c_saSortTypes;
    /**
     * FIELD
     */
    public final static String FILTER_ATTS_BY_LONGDESC = c_saFilterTypes[0];
    /**
     * FIELD
     */
    public final static String FILTER_ATTS_BY_SHORTDESC = c_saFilterTypes[1];
    /**
     * FIELD
     */
    public final static String FILTER_ATTS_BY_ATTCODE = c_saFilterTypes[2];
    /**
     * FIELD
     */
    public final static String FILTER_ATTS_BY_ATTTYPE = c_saFilterTypes[3];
    /**
     * FIELD
     */
    public final static String FILTER_ATTS_BY_ATTTYPEMAPPING = c_saFilterTypes[4];

    private SortFilterInfo m_SFInfo = null;
    private DependentFlagList m_depFlagList = null;

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: EntityGroup.java,v 1.614 2013/11/01 15:59:35 wendy Exp $ - local Copy";
    }

    /**
     * main
     *
     * @param arg
     *  @author David Bigelow
     */
    public static void main(String arg[]) {
    }

    /**
     *Constructor for the EntityGroup object
     *
     * @param  _prof                           Description of the Parameter
     * @param  _strEntityType                  Description of the Parameter
     * @param  _strPurpose                     Description of the Parameter
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public EntityGroup(Profile _prof, String _strEntityType, String _strPurpose) throws MiddlewareRequestException {
        super(null, _prof, _strEntityType);
        setPurpose(_strPurpose);
        m_SFInfo = new SortFilterInfo(EANMetaAttribute.SORT_BY_LONGDESC, true, EntityGroup.FILTER_ATTS_BY_LONGDESC, null);
        setGlobalClassificationGroup(new ClassificationGroup(this, "GLOBAL", "1==1"));
    }

    /**
     * EntityGroup
     *
     * @param _ef
     * @param _db
     * @param _prof
     * @param _strEntityType
     * @param _strPurpose
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public EntityGroup(EANMetaFoundation _ef, Database _db, Profile _prof, String _strEntityType, String _strPurpose) throws SQLException,
        MiddlewareException, MiddlewareRequestException {
        this(_ef, _db, _prof, _strEntityType, _strPurpose, true,null);
    }
    public EntityGroup(EANMetaFoundation _ef, Database _db, Profile _prof, String _strEntityType, String _strPurpose, Hashtable memTbl) throws SQLException,
    	MiddlewareException, MiddlewareRequestException {
    	this(_ef, _db, _prof, _strEntityType, _strPurpose, true,memTbl);
    }
    /**
     *  New constructor for rdi trick - we retrieve an EntityGroup from the RMI interface and make it our own..
     *
     * @param _ef
     * @param _rdi
     * @param _prof
     * @param _strEntityType
     * @param _strPurpose
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     */
    public EntityGroup(EANMetaFoundation _ef, RemoteDatabaseInterface _rdi, Profile _prof, String _strEntityType, String _strPurpose) throws
        RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

        super(_ef, _prof, _strEntityType);
        try {
            EntityGroup egtmp = _rdi.getEntityGroup(_prof, _strEntityType, _strPurpose);

            setPurpose(_strPurpose);
            m_SFInfo = new SortFilterInfo(EANMetaAttribute.SORT_BY_LONGDESC, true, EntityGroup.FILTER_ATTS_BY_LONGDESC, null);
            setGlobalClassificationGroup(new ClassificationGroup(this, "GLOBAL", "1==1"));

            setCapability(egtmp.getCapability());
            setMetaLinkGroup(egtmp.getMetaLinkGroup());
            setMetaAttribute(egtmp.getMetaAttribute());
            setResetGroup(egtmp.getResetGroup());
            setRequiredGroup(egtmp.getRequiredGroup());
            setRestrictionGroup(egtmp.getRestrictionGroup());
            setClassificationGroup(egtmp.getClassificationGroup());
            setClassify(egtmp.isClassified());
            setGlobalClassificationGroup(egtmp.getGlobalClassificationGroup());
            setUniqueAttributeGroup(egtmp.getUniqueAttributeGroup());
            setRule51Group(egtmp.getRule51Group());
            setDependentAttributeValue(egtmp.getDependentAttributeValue());
            setLocalRuleGroup(egtmp.getLocalRuleGroup());
            putLongDescription(egtmp.getLongDescription());
            if (egtmp.isRelator()) {
                setRelator(true, egtmp.getEntity1Type(), egtmp.getEntity2Type());
            }
            if (egtmp.isAssoc()) {
                setAssoc(true, egtmp.getEntity1Type(), egtmp.getEntity2Type());
            }
            ownIt();
            setMetaColumnOrderGroup(egtmp.getMetaColumnOrderGroup());
            // Check to ensure the the newly created entityGroup has no data in it
            // Make sure we do not have anyRows going on here
            if (getEntityItemCount() > 0) {
                D.ebug(D.EBUG_WARN, "EntityGroup.Constructor.egCache. EntityItem List is not Empty.. clearing now. TRACE:" + getKey());
                resetEntityItem();
            }
            egtmp = null;
        }
        finally {
        }
    }

    /**
     *  New constructor for self containment
     *
     * @param  _ef                             Description of the Parameter
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @param  _strEntityType                  Description of the Parameter
     * @param  _strPurpose                     Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     * @param _bUI
     */
    public EntityGroup(EANMetaFoundation _ef, Database _db, Profile _prof, String _strEntityType, String _strPurpose, 
    		boolean _bUI) throws SQLException, MiddlewareException, MiddlewareRequestException {
    	this(_ef, _db, _prof, _strEntityType, _strPurpose,	_bUI, null);
    }
    public EntityGroup(EANMetaFoundation _ef, Database _db, Profile _prof, String _strEntityType, String _strPurpose, 
    		boolean _bUI, Hashtable memTbl) throws  SQLException, MiddlewareException, MiddlewareRequestException {

        super(_ef, _prof, _strEntityType);

        setPurpose(_strPurpose);
        m_SFInfo = new SortFilterInfo(EANMetaAttribute.SORT_BY_LONGDESC, true, EntityGroup.FILTER_ATTS_BY_LONGDESC, null);
        setGlobalClassificationGroup(new ClassificationGroup(this, "GLOBAL", "1==1"));
        //attempt to use less memory, hang onto strings already found
        Hashtable mymemTbl = null;
        if(memTbl==null){ // may not be passed in, so create it here
        	mymemTbl = new Hashtable();
        	memTbl = mymemTbl;
        }
        try {

            EntityGroup egCache = null;

            //bypass cache part ONLY if specified
            if (!_strPurpose.equals("NoCache")) {
                egCache = getCache(_db);
            }

            if (egCache != null) {

                _db.debug(D.EBUG_DETAIL, "*** EntityGroup Cache Item found ***" + getKey());

                // Get the description and capability...
                // and is it a relator.. assoc

                m_hsh1 = egCache.m_hsh1;
                m_hsh2 = egCache.m_hsh2;
                if (egCache.isRelator()) {
                    setRelator(true, egCache.getEntity1Type(), egCache.getEntity2Type());
                }
                if (egCache.isAssoc()) {
                    setAssoc(true, egCache.getEntity1Type(), egCache.getEntity2Type());
                }
                setCapability(egCache.getCapability());
                // setPurpose(egCache.getPurpose());  Do not set the purpose here... it is done up top
                setMetaLinkGroup(egCache.getMetaLinkGroup());
                setMetaAttribute(egCache.getMetaAttribute());
                setResetGroup(egCache.getResetGroup());
                setRequiredGroup(egCache.getRequiredGroup());
                setRestrictionGroup(egCache.getRestrictionGroup());
                setClassificationGroup(egCache.getClassificationGroup());
                setClassify(egCache.isClassified());
                setGlobalClassificationGroup(egCache.getGlobalClassificationGroup());
                setUniqueAttributeGroup(egCache.getUniqueAttributeGroup());
                setRule51Group(egCache.getRule51Group());
                setDependentAttributeValue(egCache.getDependentAttributeValue());
                setLocalRuleGroup(egCache.getLocalRuleGroup());
                ownIt();
                // Check to ensure the the newly created entityGroup has no data in it
                // Make sure we do not have anyRows going on here
                if (getEntityItemCount() > 0) {
                    _db.debug(D.EBUG_WARN, "EntityGroup.Constructor.egCache. EntityItem List is not Empty.. clearing now. TRACE:" + getKey());
                    resetEntityItem();
                }
                egCache = null;
            }
            else {
                buildEntityGroupFromScratch(_db,memTbl);
            }

            // Lets get Out Of Circulation Information, if we are in an edit type request and _bUI is true
            if (isEditLike() && _bUI) {
                setOutOfCirculation(genOutOfCirculation(_db));
            }
            else {
                //setOutOfCirculation(new EANList());
            }
            // note - after cache!
            if ( (isEditLike() || isSearchLike()) && _bUI) {
                m_mcog = null;
                //Stopwatch sw = new Stopwatch();
                //sw.start();
                try {
                    setMetaColumnOrderGroup(new MetaColumnOrderGroup(_db, this));
                }
                catch (Exception exc) {
                    exc.printStackTrace(System.out);
                }
            }
            //
        }
        finally {
        	if(mymemTbl!=null){
        		mymemTbl.clear();
        		mymemTbl = null;
        	}
            _db.freeStatement();
            _db.isPending();

        }
    }

    /*
     *  This guy will pull all the out of Circulation info into
     *  an EANList of MetaFlag Values.
     */
    /**
     *  Description of the Method
     *
     * @param  _db                             Description of the Parameter
     * @return                                 Description of the Return Value
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    // memchg   public EANList genOutOfCirculation(Database _db)
    public Vector genOutOfCirculation(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        EANMetaFlagAttribute mfa = null;
        MetaFlag mf = null;

        ReturnStatus returnStatus = new ReturnStatus( -1);
        //memchg EANList elReturn = new EANList();
        Vector elReturn = new Vector();

        // Pull out some key info..
        String strEnterprise = getProfile().getEnterprise();
        String strValOn = getProfile().getValOn();
        String strEffOn = getProfile().getEffOn();

        try {
            rs = _db.callGBL5000(returnStatus, strEnterprise, getEntityType(), strValOn, strEffOn);
            rdrs = new ReturnDataResultSet(rs);
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }

        //DWB here is process the out of circulation stuff
        for (int ii = 0; ii < rdrs.size(); ii++) {
            String str1 = rdrs.getColumn(ii, 0);
            String str2 = rdrs.getColumn(ii, 1);
            _db.debug(D.EBUG_SPEW, "gbl5000:answers:" + str1 + ":" + str2);

            mfa = (EANMetaFlagAttribute) getMetaAttribute(str1);

            // Lets get the MetaFlag
            if (mfa != null) {
                mf = mfa.getMetaFlag(str2);
                if (mf != null) {
                	if(!elReturn.contains(mf)){
                		elReturn.add(mf);//memchg .put(mf);
                	}
                }
            }
            else {
                _db.debug(D.EBUG_SPEW, "Could not retrieve MetaFlagAttribute:" + str1 + " in EntityGroup.genOutOfCirculation() for " + getEntityType());
            }
        }

        return elReturn;
    }

    /**
     * Perform the 'Guts' of EntityGroup object build
     *
     * @param  _db                             Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    private void buildEntityGroupFromScratch(Database _db,Hashtable memTbl) throws SQLException, MiddlewareException, MiddlewareRequestException {

    	
    	DatePackage dp = _db.getDates();
            
        //RCQ00210066-WI Ref CQ00014746 - BH W1 R1 - EACM support history of Metadata in PDH
        //Enabling Technology to change how CACHE is built
        Profile profile = getProfile();
     	String valonOrig = profile.getValOn();
     	String effonOrig = profile.getEffOn();
     	
        if (!getPurpose().equals("NoCache")) {
        	profile.setValOnEffOn(dp.getEndOfDay(), dp.getEndOfDay()); //PR20627
        	// must set it this.profile because building meta objects use this.getprofile
        	// cant setProfile() because parent profile overrides it
        }
                
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;

        ReturnStatus returnStatus = new ReturnStatus( -1);

        // Pull out some key info..
        int iNLSID = getProfile().getReadLanguage().getNLSID();
        String strRoleCode = null;
        boolean success = false;

        String strEnterprise = getProfile().getEnterprise();
        String strValOn = getProfile().getValOn();
        String strEffOn = getProfile().getEffOn();

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        // GAB - 07/01/03 - if we came from an ExtractActionItem AND we specify a roleCodeOverride then use this!!
        //                  else: business as usual w/ current profile's RoleCode.
        // Note: we are assuming that we are coming from the database/ExtractActionItem constructor of EntityList
        //       (this should be the case!!).
        if (getEntityList() != null && 
            getEntityList().getParentActionItem() instanceof ExtractActionItem &&
            ( (ExtractActionItem) getEntityList().getParentActionItem()).hasRoleCodeOverride()) {
            strRoleCode = ( (ExtractActionItem) getEntityList().getParentActionItem()).getRoleCodeOverride();
        }
        else {
            strRoleCode = getProfile().getRoleCode();
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Get the description, here is where we add info to say it is also used as a Relator...
        try {
            rs = _db.callGBL8140(returnStatus, strRoleCode, strEnterprise, getEntityType(), iNLSID, strValOn, strEffOn);
            while (rs.next()) {
                String str2 = EANUtility.reuseObjects(memTbl,rs.getString(2).trim());
                String str3 = EANUtility.reuseObjects(memTbl,rs.getString(3).trim());
                int i4 = rs.getInt(4);
                String str5 = EANUtility.reuseObjects(memTbl,rs.getString(5).trim());
                String str6 = EANUtility.reuseObjects(memTbl,rs.getString(6).trim());
                String str7 = EANUtility.reuseObjects(memTbl,rs.getString(7).trim());
                String str8 = EANUtility.reuseObjects(memTbl,rs.getString(8).trim());
                String strClassify = EANUtility.reuseObjects(memTbl,rs.getString(9).trim());
                       		
                _db.debug(D.EBUG_SPEW,
                          "gbl8140 answer:" + getEntityType() + ":" + str2 + ":" + str3 + ":" + i4 + ":" + str5 + ":" + str6 + ":" + str7 + ":" +
                          str8 + ":" + strClassify);
                setCapability(str2);
                setClassify(strClassify);
                //putShortDescription(i4, str5);
                putLongDescription(i4, str3);

                // if this doubles as a relator.. we need to anotate it here..
                // We will also need to know if this group is also an association place holder

                if (str6.charAt(0) == 'R') {
                    setRelator(true, str7, str8);
                }
                if (str6.charAt(0) == 'A') {
                    setAssoc(true, str7, str8);
                }
            }
            success = true;
        }
        finally {
            if (rs != null) {
            	try{
        			rs.close();
        		} catch (SQLException x) {
        			_db.debug(D.EBUG_DETAIL, "buildEntityGroupFromScratch: "+getKey()+" ERROR failure closing ResultSet "+x);
        		} 
                rs = null;
            }
            if(success){
            	_db.commit();
            }else{
            	_db.rollback();
            }
            _db.freeStatement();
            _db.isPending();
        }

        //
        // Lets get the MetaLinkGroup here
        // It is used in several places
        //
        setMetaLinkGroup(new MetaLinkGroup(null, _db, getProfile(), getEntityType()));

        try{
        	success = false;
        	//
        	// Now.. lets get all the meta attributes.. given the flavor of EntityGroup
        	//
        	if (isNavigateLike()) {
        		rs = _db.callGBL7020(returnStatus, strEnterprise, getEntityType(), strValOn, strEffOn);
        		rdrs = new ReturnDataResultSet(rs);
        		success = true;
        		rs.close();
        		rs = null;
        	}
        	else if (isEditLike()) {
        		rs = _db.callGBL7502(returnStatus, strEnterprise, getEntityType(), strRoleCode, strValOn, strEffOn);
        		rdrs = new ReturnDataResultSet(rs);
        		success = true;
        		rs.close();
        		rs = null;
        	}
        	else if (isSearchLike()) {
        		rs = _db.callGBL7550(returnStatus, strEnterprise, getEntityType(), strValOn, strEffOn);
        		rdrs = new ReturnDataResultSet(rs);
        		success = true;
        		rs.close();
        		rs = null;
        	}
        	else if (isABRStatusLike()) {
        		rs = _db.callGBL7021(returnStatus, strEnterprise, getEntityType(), strValOn, strEffOn);
        		rdrs = new ReturnDataResultSet(rs);
        		success = true;
        		rs.close();
        		rs = null;
        	}
        	else {
        		rdrs = new ReturnDataResultSet();
        		success = true;
        	}
        }finally{
        	if (rs != null) {
        		try{
        			rs.close();
        		} catch (SQLException x) {
        			_db.debug(D.EBUG_DETAIL, "buildEntityGroupFromScratch: "+getKey()+" ERROR failure closing ResultSet "+x);
        		} 
        		rs = null;
        	}
        	if(success){
        		_db.commit();
        	}else{
        		_db.rollback();
        	}
        	_db.freeStatement();
        	_db.isPending();
        }

		_db.debug(D.EBUG_DETAIL, "buildEntityGroupFromScratch: "+getKey()+" adding "+rdrs.size()+" attributes");

        //DWB here is where we convert associations....
        for (int ii = 0; ii < rdrs.size(); ii++) {
            boolean bAllNls = isExtractLike();
            int iColOrder = rdrs.getColumnInt(ii, 0);
            // This is ignored in the app - it provides order for the SP
            String strAttributeCode = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 1));
            String strAttributeType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 2));
             
        	_db.debug(D.EBUG_DETAIL, "buildEntityGroupFromScratch: "+getKey()+" adding attribute["+ii+"] "+strAttributeCode);
            _db.debug(D.EBUG_SPEW, "gbl7020/gbl7502/gbl7550/gbl7021:answers:" + iColOrder + ":" + strAttributeCode + ":" + strAttributeType);

            try {

                switch (strAttributeType.charAt(0)) {

                    case 'T':
                    case 'I':

                        putMetaAttribute(new MetaTextAttribute(this, _db, null, strAttributeCode));
                        break;
                    case 'L':

                        putMetaAttribute(new MetaLongTextAttribute(this, _db, null, strAttributeCode));
                        break;
                    case 'X':

                        putMetaAttribute(new MetaXMLAttribute(this, _db, null, strAttributeCode));
                        break;
                    case 'F':
                        MetaMultiFlagAttribute metaMF = new MetaMultiFlagAttribute(this, _db, null, strAttributeCode, bAllNls,memTbl);
                        metaMF.setAttributeType(strAttributeType);
                        putMetaAttribute(metaMF);

                        //putMetaAttribute(new MetaMultiFlagAttribute(this, _db, null, strAttributeCode, bAllNls));
                        break;
                    case 'U':
                        MetaSingleFlagAttribute metaSF = new MetaSingleFlagAttribute(this, _db, null, strAttributeCode, 
                        		bAllNls,memTbl);
                        metaSF.setAttributeType(strAttributeType);
                        putMetaAttribute(metaSF);

                        //putMetaAttribute(new MetaSingleFlagAttribute(this, _db, null, strAttributeCode, bAllNls));
                        break;
                    case 'S':

                        putMetaAttribute(new MetaStatusAttribute(this, _db, null, strAttributeCode, bAllNls, memTbl));
                        break;
                    case 'A':

                        putMetaAttribute(new MetaTaskAttribute(this, _db, null, strAttributeCode, bAllNls, memTbl));
                        break;
                    case 'B':

                        putMetaAttribute(new MetaBlobAttribute(this, _db, null, strAttributeCode));
                        break;

                    case 'D':

                        if (strAttributeType.charAt(1) == 'T') {
                            MetaTextAttribute mtaDerived = new MetaTextAttribute(this, _db, null, strAttributeCode);
                            mtaDerived.setDerived(true);
                            mtaDerived.setCapability("R");
                            putMetaAttribute(mtaDerived);
                        }
                        break;
                    default:
                        break;
                }
            }
            catch (Exception exc) {
                //if we cant build the attribute for whatever reason -> skip this attribute but dont fail the EntityGroup construction!
                _db.debug(D.EBUG_ERR, "ERROR building attribute: '" + strAttributeCode + "' for " + getEntityType() +
                          " " + exc.toString());
                exc.printStackTrace();
            }
        }

        try{
        	success = false;
        	if (isEditLike() || isSearchLike()) {

        		// Now we have all the attributes.. lets now overlay all the restrictions, dependent flags, and
        		// State Machine information

        		if (!isExtractLike()) {
        			/*
                 RQ1103065049
                 transCode 	attr1		flag1		attr2		flag2
                 MODELDFS3	COFCAT		102			COFSUBCAT	169
                 MODELDFS3	COFCAT		102			COFSUBCAT	167
                 MODELDFS3	COFCAT		103			COFSUBCAT	169
                 NEWTHING1	MODELDFS3	102:169		COFGRP		202
                 NEWTHING1	MODELDFS3	102:167		COFGRP		203
                 NEWTHING1	MODELDFS3	103:169		COFGRP		205
                 NEXT1		NEWTHING1	102:169:202	COFSUBGRP	321
                 NEXT1		NEWTHING1	102:167:203	COFSUBGRP	322
                 NEXT1		NEWTHING1	103:169:205	COFSUBGRP	323
        			 */
        			Hashtable transTbl = new Hashtable(); // RQ1103065049 hang onto flag transitions to build dependent sets
        			_db.debug(D.EBUG_SPEW,
        					"building restrictions, dep flags, SMs for EntityGroup \"" + getEntityType() + "\", getPurpose() = \"" + getPurpose() +
        			"\".");

        			// let us retrieve any Cascading Dependent Choice Fields or State Machine Elements

        			rs = _db.callGBL8133(returnStatus, strEnterprise, getEntityType(), strValOn, strEffOn);
        			rdrs = new ReturnDataResultSet(rs);
        			rs.close();
        			rs = null;
   
        			_db.commit();
        			_db.freeStatement();
        			_db.isPending();

        			for (int ii = 0; ii < rdrs.size(); ii++) {
        				EANMetaFlagAttribute mfa1 = null;
        				EANMetaFlagAttribute mfa2 = null;

        				MetaFlag mf1 = null;
        				MetaFlag mf2 = null;

        				String strClass = EANUtility.reuseObjects(memTbl, rdrs.getColumn(ii, 0));
        				String strTranCode = EANUtility.reuseObjects(memTbl, rdrs.getColumn(ii, 1));
        				String strAttributeCode1 = EANUtility.reuseObjects(memTbl, rdrs.getColumn(ii, 2));
        				String strFlagCode1 = EANUtility.reuseObjects(memTbl, rdrs.getColumn(ii, 3));
        				String strAttributeCode2 = EANUtility.reuseObjects(memTbl, rdrs.getColumn(ii, 4));
        				String strFlagCode2 = EANUtility.reuseObjects(memTbl, rdrs.getColumn(ii, 5));
        				String strTargetAttributeCode = EANUtility.reuseObjects(memTbl, rdrs.getColumn(ii, 6));
        				String strMethod = EANUtility.reuseObjects(memTbl, rdrs.getColumn(ii, 7));
        				String strValue = EANUtility.reuseObjects(memTbl, rdrs.getColumn(ii, 8));

        				// flagcode1 may have ':' delimiters now, so use | for debug
        				_db.debug(D.EBUG_SPEW,
        						"gbl8133:answers " + strClass + "|" + strTranCode + "|" + strAttributeCode1 + "|" + strFlagCode1 + "|" + strAttributeCode2 +
        						"|" + strFlagCode2 + "|" + strTargetAttributeCode + "|" + strMethod + "|" + strValue);

        				// See if this is a workflow record
        				if (strClass.equals("WF") && strAttributeCode1.equals(strAttributeCode2)) {
        					StateTransition st = null;
        					MetaStatusAttribute msa = (MetaStatusAttribute) getMetaAttribute(strAttributeCode1);

        					//GAB - print an error but dont bail out - this allows ECCM to exclude ABR atts
        					// 1/28/03 - removed try{} block which was outside of this for loop b/c/ it was preventing rest of DepFlag list to be built once error was caught
        					//_db.test(msa != null, " 394 Cannot find the controlling meta Attribute in the list. Meta Config Problem:" + strAttributeCode1);
        					if (msa == null) {
        						_db.debug(D.EBUG_ERR,
        								"*** WARNING *** 394 Cannot find the controlling meta Attribute in the list for "+getEntityType()+". Meta Config Problem:" + strAttributeCode1);
        						continue;
        						// i.e. bail out of this iteration of for loop
        					}

        					// Get or create the state transition...
        					st = msa.getStateTransition(strFlagCode1, strFlagCode2);
        					if (st == null) {
        						st = new StateTransition(msa, strTranCode, strFlagCode1, strFlagCode2);
        						msa.putStateTransition(st);
        					}

        					// Make sure it is set or check
        					//_db.test(strMethod.equals("Set") || strMethod.equals("Check"), " 407 Transition Method is neither Set nor Check.. it is:" + strValue);
        					if (! (strMethod.equals("Set") || strMethod.equals("Check"))) {
        						_db.debug(D.EBUG_ERR, "*** WARNING *** 407 Transition Method is neither Set nor Check for "+getEntityType()+".. it is:" + strValue);
        						continue;
        					}

        					if (strMethod.equals("Set")) {
        						// Now that we have the correct State Transition
        						EANMetaAttribute ma = getMetaAttribute(strTargetAttributeCode);
        						//_db.test(ma != null, " 407 Cannot find the controlling meta Attribute in the list. Meta Config Problem:" + strTargetAttributeCode);
        						if (ma == null) {
        							_db.debug(D.EBUG_ERR,
        									"*** WARNING *** 407 Cannot find the controlling meta Attribute in the list for "+getEntityType()+". Meta Config Problem:" +
        									strTargetAttributeCode);
        							continue;
        						}
        						st.putSetItem(ma, strValue);
        					}
        					else {
        						st.setEval(strValue);
        					}
        				}

        				// Now that the transition control is set..
        				// we need to set up the filtered lists. etc
        				// first get the controlling attribute
        				mfa1 = (EANMetaFlagAttribute) getMetaAttribute(strAttributeCode1);
        				mfa2 = (EANMetaFlagAttribute) getMetaAttribute(strAttributeCode2);

        				//_db.test( mfa1 != null, " 418 Cannot find the controlling meta Attribute in the list. Meta Config Problem:" + strAttributeCode1);
        				if (strFlagCode1.indexOf(":") != -1) { //RQ1103065049 extend dependent flag set support
        					if (mfa2 == null) {
        						_db.debug(D.EBUG_ERR,
        								"EntityGroup.buildFromScratch(). *** WARNING *** 419b Cannot find the controlled meta Attribute in the list for "+getEntityType()+". Meta Config Problem:" +
        								strAttributeCode2 + " in transition " + strTranCode);
        						continue;
        					}

        					mf2 = mfa2.getMetaFlag(strFlagCode2);
        					if (mf2 == null) {
        						_db.debug(D.EBUG_ERR,
        								"EntityGroup.buildFromScratch(). *** WARNING *** 421b Cannot find the controlled meta Flag in the list for "+getEntityType()+". Meta Config Problem:" +
        								strFlagCode2 + " for " + strAttributeCode2 + " in transition " + strTranCode);
        						continue;
        					}

        					// attribute1 refers to another transitioncode, flagcode1 =>flag1:flag2 from that other transition
        					String transKey = strTranCode + ":" + strFlagCode1 + ":" + strFlagCode2;
        					String filterTransKey = strAttributeCode1 + ":" + strFlagCode1;
        					transTbl.put(transKey, new TransitionItem(filterTransKey, mf2));
        					// end RQ1103065049
        					continue;
        				}
        				else if (mfa1 == null) {
        					_db.debug(D.EBUG_ERR,
        							"EntityGroup.buildFromScratch().  *** WARNING *** 418 Cannot find the controlling meta Attribute in the list for "+getEntityType()+". Meta Config Problem:" +
        							strAttributeCode1);
        					continue;
        				}

        				//_db.test( mfa2 != null, " 419 Cannot find the controlling meta Attribute in the list. Meta Config Problem:" + strAttributeCode2);
        				if (mfa2 == null) {
        					_db.debug(D.EBUG_ERR,
        							"EntityGroup.buildFromScratch(). *** WARNING *** 419 Cannot find the controlled meta Attribute in the list for "+getEntityType()+". Meta Config Problem:" +
        							strAttributeCode2);
        					continue;
        				}

        				mf1 = mfa1.getMetaFlag(strFlagCode1);
        				mf2 = mfa2.getMetaFlag(strFlagCode2);
        				//_db.test( mf1 != null, " 420 Cannot find the controlling meta Flag in the list. Meta Config Problem:" + strFlagCode1);
        				if (mf1 == null) {
        					_db.debug(D.EBUG_ERR,
        							"EntityGroup.buildFromScratch(). *** WARNING *** 420 Cannot find the controlling meta Flag in the list for "+getEntityType()+". Meta Config Problem:" +
        							strFlagCode1);
        					continue;
        				}
        				//_db.test( mf2 != null, " 421 Cannot find the controlling meta Flag in the list. Meta Config Problem:" + strFlagCode2);
        				if (mf2 == null) {
        					_db.debug(D.EBUG_ERR,
        							"EntityGroup.buildFromScratch(). *** WARNING *** 421 Cannot find the controlled meta Flag in the list for "+getEntityType()+". Meta Config Problem:" +
        							strFlagCode2);
        					continue;
        				}

        				// GAB 042803 - Only execute this block for Workflow and DependentFlags.
        				//            - This should cover *ALL* cases other than AutoSelect (AS) flags.
        				if (strClass.equals("WF") || strClass.equals("DF")) {
        					// O.K. Link them up
        					mf1.addController(mf2);
        					mf2.addFilter(mf1);
        					//RQ1103065049 extend dependent flag support
        					if (strClass.equals("DF")) {
        						// hang onto this transition with key= transcode:flag1:flag2; value=TransitionItem
        						// a dependent flag set may refer to this
        						String transKey = strTranCode + ":" + strFlagCode1 + ":" + strFlagCode2;
        						transTbl.put(transKey, new TransitionItem(mf1, mf2));
        					} // end RQ1103065049
        				}
        				else if (strClass.equals("AS")) { // end WF & DF loop, start AS loop. Added GAB 042803.
        					mf1.addAutoSelectFlag(mf2);
        				}
        			}

        			//RQ1103065049 extend dependent flag support
        			// handle dependent flag sets now
        			// get each dependent transition, have to do this after all transitions have been read in
        			//key is the controlling transitioncode:flag1:flag2:flag2..., value is TransitionItem
        			for (Enumeration eNum = transTbl.keys(); eNum.hasMoreElements(); ) {
        				String transCode = (String)eNum.nextElement();
        				TransitionItem transItem = (TransitionItem) transTbl.get(transCode);
        				String filterTrans = transItem.getFilteringTrans();
        				// there will only be 1 metaflag in a transitionitem if it has multiple filters
        				MetaFlag mf2 = (MetaFlag) transItem.getMetaFlagVct().firstElement();
        				if (filterTrans == null) { // this is a normal paired transition, could be a part of a set though
        					continue;
        				}

        				//memchg EANList transSetList = new EANList();
        				Vector transSetList = new Vector();
        				// find all transitions that control this one
        				while (filterTrans != null) {
        					transItem = (TransitionItem) transTbl.get(filterTrans);
        					if (transItem == null) {
        						_db.debug(D.EBUG_ERR, "EntityGroup.buildFromScratch(). *** WARNING *** has filtering transition " +
        								transCode + " but Cannot find the transition " + filterTrans + " in the list for "+getEntityType()+". Meta Config Problem");
        						break;
        					}
        					filterTrans = transItem.getFilteringTrans();
        					for (int i = 0; i < transItem.getMetaFlagVct().size(); i++) {
        						//memchg transSetList.put( (MetaFlag) transItem.getMetaFlagVct().elementAt(i));
        						MetaFlag mf = (MetaFlag) transItem.getMetaFlagVct().elementAt(i);
        						if(!transSetList.contains(mf)){
        							transSetList.add(mf);
        						}
        					}
        				}

        				if (transSetList.size() == 0) {
        					_db.debug(D.EBUG_ERR, "EntityGroup.buildFromScratch(). *** WARNING *** has filtering transition " +
        							transCode + " but Cannot any controlling transitions in the list for "+getEntityType()+". Meta Config Problem");
        					continue;
        				}
        				// set all controlling metaflags for mf2
        				for (int c = 0; c < transSetList.size(); c++) {
        					MetaFlag mf1 = (MetaFlag) transSetList.elementAt(c);//memchg .getAt(c);
        					mf1.addControllerSet(mf2, transSetList);
        				}
        				// set all filtering metaflags for this metaflag
        				mf2.addFilterSet(transSetList);
        			}
        			// release memory
        			for (Enumeration eNum = transTbl.elements(); eNum.hasMoreElements(); ) {
        				TransitionItem transItem = (TransitionItem)eNum.nextElement();
        				transItem.dereference();
        			}
        			transTbl.clear();
        			// end RQ1103065049
        		} // end !extractlike

        		//
        		// Only need if its editlike or Extract Like
        		//

        		if (isEditLike() || isExtractLike()) {

        			rs = _db.callGBL8176(returnStatus, strEnterprise, getEntityType(), strValOn, strEffOn);
        			rdrs = new ReturnDataResultSet(rs);
        			rs.close();
        			rs = null;
        			_db.commit();
        			_db.freeStatement();
        			_db.isPending();


        			for (int ii = 0; ii < rdrs.size(); ii++) {
        				String strGroupKey = EANUtility.reuseObjects(memTbl, rdrs.getColumn(ii, 0)); // Entity/Group; linktype2
        				String strGroupClass = EANUtility.reuseObjects(memTbl, rdrs.getColumn(ii, 1)); // Entity/Group; linkcode
        				String strGroupControlValue = EANUtility.reuseObjects(memTbl, rdrs.getColumn(ii, 2)); // Entity/Group; linkvalue
        				String strAttributeCode = EANUtility.reuseObjects(memTbl, rdrs.getColumn(ii, 3)); // Group/Attribute; linktype2
        				String strDummy1 = EANUtility.reuseObjects(memTbl, rdrs.getColumn(ii, 4)); // Group/Attribute; linkcode
        				String strDummy2 = EANUtility.reuseObjects(memTbl, rdrs.getColumn(ii, 5)); // Group/Attribute; linkvalue

        				// Restriction, Required, Reset Answers

        				_db.debug(D.EBUG_SPEW,
        						"gbl8176:answers:" + strGroupKey + ":" + strGroupClass + ":" + strGroupControlValue + ":" + strAttributeCode + ":" +
        						strDummy1 + ":" + strDummy2 + ":");

        				if (strGroupClass.startsWith("Restriction")) {
        					RestrictionGroup rg = getRestrictionGroup(strGroupKey);
        					if (rg == null) {
        						rg = new RestrictionGroup(this, strGroupKey, strGroupControlValue);
        						putRestrictionGroup(rg);
        						if (strGroupClass.endsWith("Deferred")) {
        							rg.setDeferred(true);
        						}
        					}
        					// There is a notion of a global Lock and if the attributecode
        					if (strAttributeCode.equals("GLOBALLOCK")) {
        						rg.setGlobalLock(true);
        					}
        					else if (strAttributeCode.equals("ABRGLOBALLOCK")) {
        						rg.setABRGlobalLock(true);
        					}
        					else {
        						rg.addAttributeCode(strAttributeCode);
        					}
        				}
        				else if (strGroupClass.equals("Required")) {
        					RequiredGroup rqg = getRequiredGroup(strGroupKey);
        					if (rqg == null) {
        						rqg = new RequiredGroup(this, strGroupKey, strGroupControlValue);
        						putRequiredGroup(rqg);
        					}
        					rqg.addAttributeCode(strAttributeCode);
        				}
        				else if (strGroupClass.equals("Reset")) {
        					ResetGroup rstg = getResetGroup(strGroupKey);
        					if (rstg == null) {
        						rstg = new ResetGroup(this, strGroupKey, strGroupControlValue);
        						putResetGroup(rstg);
        					}
        					rstg.addAttributeCode(strAttributeCode);
        				}
        				else if (strGroupClass.equals("Classify")) {
        					ClassificationGroup clsg = getClassificationGroup(strGroupKey);
        					if (clsg == null) {
        						clsg = new ClassificationGroup(this, strGroupKey, strGroupControlValue);
        						putClassificationGroup(clsg);
        					}
        					if (strAttributeCode.equals("GLOBALCLASSIFY")) {
        						clsg.setGlobalClassify(true);
        						m_clsgGlobal = clsg;
        					}
        					else {
        						clsg.addAttributeCode(strAttributeCode);
        					}
        				}
        				else if (strGroupClass.equals("UniqueAttribute")) {
        					UniqueAttributeGroup uag = getUniqueAttributeGroup(strGroupKey);
        					if (uag == null) {
        						uag = new UniqueAttributeGroup(getProfile(), getEntityType(), strGroupKey, strGroupControlValue);
        						putUniqueAttributeGroup(uag);
        					}
        					uag.addAttributeCode(strAttributeCode);
        				}
        				else if (strGroupClass.equals("Rule51")) {
        					Rule51Group r51Grp = new Rule51Group(getProfile(), getEntityType(), strDummy2, strDummy1, strAttributeCode, strGroupKey);
        					setRule51Group(r51Grp);
        				}
        				else if (strGroupClass.equals("DependentValue")) {

        					DependentAttributeValue.DepAttValItem davi = null;

        					String strVerEntType = strDummy1;
        					StringTokenizer st1 = new StringTokenizer(strDummy2, ":");
        					String strVerAttCode = st1.nextToken();
        					String strVerAttType = st1.nextToken();
        					DependentAttributeValue dav = getDependentAttributeValue(strGroupKey);
        					if (dav == null) {
        						String strSearchAI = strGroupControlValue;
        						dav = new DependentAttributeValue(getProfile(), strGroupKey, strSearchAI);
        						putDependentAttributeValue(dav);
        					}
        					davi = dav.addItem(getEntityType(), strAttributeCode, strVerEntType, strVerAttCode);
        					davi.setVerifyAttributeType(strVerAttType);
        				}
        				else if (strGroupClass.equals("LocalRule")) {
        					LocalRuleGroup lrg = getLocalRuleGroup(strGroupKey);
        					if (lrg == null) {
//      						need to use the EntityGroup's profile
        						lrg = new LocalRuleGroup(this, null, strGroupKey);
        						putLocalRuleGroup(lrg);
        					}
        					if (strAttributeCode.equals("Evaluation")) {
        						String strItemKey = strDummy1;
        						String strEval = strDummy2;
        						LocalRuleItem lri = lrg.getItem(strItemKey);
        						if (lri == null) {
//      							need to used the Local Group's profile
        							lri = new LocalRuleItem(lrg, null, strItemKey);
        							lrg.putItem(lri);
        						}
        						lri.setEvaluationString(strEval);
        					}
        					if (strAttributeCode.equals("Message")) {
        						String strItemKey = strDummy1;
        						String strMessage = strDummy2;
        						LocalRuleItem lri = lrg.getItem(strItemKey);
        						if (lri == null) {
        							lri = new LocalRuleItem(lrg, null, strItemKey);
        							lrg.putItem(lri);
        						}
        						lri.setMessageString(strMessage);
        					}
        					if (strAttributeCode.equals("Full")) {
        						String strItemKey = strDummy1;
        						boolean bFull = strDummy2.equalsIgnoreCase("Full");
        						LocalRuleItem lri = lrg.getItem(strItemKey);
        						if (lri == null) {
        							lri = new LocalRuleItem(lrg, null, strItemKey);
        							lrg.putItem(lri);
        						}
        						lri.setFull(bFull);
        					}
        					_db.debug(D.EBUG_DETAIL, "We have found ourselves a LocalRule!!!:" + lrg.dump(false));
        				}
        			}

        			//excludeFromCopy for Entity/Attributes
        			_db.debug(D.EBUG_DETAIL,
        					"calling gbl7508(" + getProfile().getEnterprise() + ",Entity/Attribute," + getEntityType() + ",Copy," + strValOn + "," +
        					strEffOn + ")");
        			rs = _db.callGBL7508(new ReturnStatus( -1), getProfile().getEnterprise(), "Entity/Attribute", getEntityType(), "Copy", strValOn,
        					strEffOn);
        			rdrs = new ReturnDataResultSet(rs);
        			rs.close();
        			rs = null;
        			_db.freeStatement();
        			_db.isPending();
        			for (int row = 0; row < rdrs.getRowCount(); row++) {
        				String strAttCode = rdrs.getColumn(row, 0);
        				String strLinkValue = rdrs.getColumn(row, 1);
        				//this should be 'N'
        				_db.debug(D.EBUG_SPEW, "gbl7508 answers:" + strAttCode + ":" + strLinkValue);
        				if (strLinkValue.equals("N")) {
        					EANMetaAttribute ma = getMetaAttribute(strAttCode);
        					if (ma != null) {
        						getMetaAttribute(strAttCode).setExcludeFromCopy(true);
        					}
        					else {
        						_db.debug(D.EBUG_ERR, "*** WARNING *** cannot find metaAttribute:" + strAttCode + ": in exclude from Copy Setup for "+getEntityType());
        					}
        				}
        			}
        		}
        	}
    		success = true;
        }finally{
        	if (rs != null) {
        		try{
        			rs.close();
        		} catch (SQLException x) {
        			_db.debug(D.EBUG_DETAIL, "buildEntityGroupFromScratch: "+getKey()+" ERROR failure closing ResultSet "+x);
        		} 
        		rs = null;
        	}
        	if(success){
        		_db.commit();
        	}else{
        		_db.rollback();
        	}
        	_db.freeStatement();
        	_db.isPending();
        }
        //end isEditLike()

        // Make sure we do not have anyRows going on here
        if (getEntityItemCount() > 0) {
            _db.debug(D.EBUG_WARN, "EntityGroup.buildEntityGroupFromScratch.EntityItemList is not Empty for "+getEntityType()+".. clearing now. TRACE:" + getKey());
            resetEntityItem();
        }

        // Put this in cache for now, we are done
        //- only if an entity was successfully pulled from database - don't store nulls!!
        if (!MetaEntityList.isNewEntityType(_db, getProfile(), getEntityType())) {
            putCache(_db);
        }
        
     	profile.setValOnEffOn(valonOrig, effonOrig); //RCQ00210066-WI - restore the profile
    }
    
    /** RQ1103065049
     * Class used to hold info needed to build sets of dependent flags
     */
    private class TransitionItem {
        private String filterTransition = null;
        Vector metaFlagVct = new Vector();
        TransitionItem(String t, MetaFlag mf) {
            filterTransition = t;
            metaFlagVct.addElement(mf);
        }

        TransitionItem(MetaFlag mf1, MetaFlag mf2) {
            metaFlagVct.addElement(mf1);
            metaFlagVct.addElement(mf2);
        }

        String getFilteringTrans() {
            return filterTransition;
        }

        Vector getMetaFlagVct() {
            return metaFlagVct;
        }

        void dereference() {
            filterTransition = null;
            metaFlagVct.clear();
        }

        public String toString() {
            return "trans: " + filterTransition + " mfvct: " + metaFlagVct;
        }
    }

    /**
     * Get a DependentFlagList object from Exisiting data w/in EntityGroupStructure
     *
     * @return                                 The dependentFlagList value
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public DependentFlagList getDependentFlagList() throws MiddlewareRequestException {
        if (m_depFlagList == null) {
            m_depFlagList = new DependentFlagList(this, getProfile());
        }
        return m_depFlagList;
    }

    /**
     *  Sets the dependentFlagList attribute of the EntityGroup object
     *
     * @param  _dfl  The new dependentFlagList value
     */
    protected void setDependentFlagList(DependentFlagList _dfl) {
        m_depFlagList = _dfl;
    }

    //
    //  ACCESSORS
    //

    // General

    /*
     *  are there any EntityItems in this group
     */
    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public boolean hasData() {
        return getData().size() > 0;
    }

    /*
     *  Returns the Key of this Object for e-announce
     *  This is the EntityType common to all EntityItems managed
     *  by this group
     *  @return String (EntityType)
     */
    /**
     *  Gets the entityType attribute of the EntityGroup object
     *
     * @return    The entityType value
     */
    public String getEntityType() {
        return getKey();
    }

    /**
     *  Gets the entityList attribute of the EntityGroup object
     *
     * @return    The entityList value
     */
    public EntityList getEntityList() {
        if (getParent() instanceof EntityList) {
            return (EntityList) getParent();
        }
        return null;
    }

    /**
     *  Gets the entity1Type attribute of the EntityGroup object
     *
     * @return    The entity1Type value
     */
    public String getEntity1Type() {
        return m_strEntity1Type;
    }

    /**
     *  Gets the entity2Type attribute of the EntityGroup object
     *
     * @return    The entity2Type value
     */
    public String getEntity2Type() {
        return m_strEntity2Type;
    }

    /*
     *  fetches the action group associated with
     *  this entity group
     */
    /**
     *  Gets the actionGroup attribute of the EntityGroup object
     *
     * @return    The actionGroup value
     */
    public ActionGroup getActionGroup() {
        EntityList el = (EntityList) getParent();
        if (el == null) {
            return null;
        }
        return el.getActionGroupByEntityType(getEntityType());
    }

    /**
     * get the ActionGroup associated with this EntityType - build MetaData from scratch
     *
     * @param  _db                      Description of the Parameter
     * @return                          The actionGroupList value
     * @exception  SQLException         Description of the Exception
     * @exception  MiddlewareException  Description of the Exception
     */
    public ActionGroupList getActionGroupList(Database _db) throws SQLException, MiddlewareException {
        if (m_actionGroupList == null) {
            m_actionGroupList = new ActionGroupList(this, _db, getProfile());
        }
        return m_actionGroupList;
    }

    /**
     * force the next call to getActionGroupList to pull from the database
     */
    public void resetActionGroupList() {
        m_actionGroupList = null;
    }

    /**
     *  Gets the downLinkActionGroup attribute of the EntityGroup object
     *
     * @return    The downLinkActionGroup value
     */
    protected ActionGroup getDownLinkActionGroup() {
        if (isRelator() || isAssoc()) {
            EntityList el = (EntityList) getParent();
            if (el == null) {
                return null;
            }
            return el.getActionGroupByEntityType(getEntity2Type());
        }
        return null;
    }

    // EntityItem Accessors

    /**
     *  Gets the entityItem attribute of the EntityGroup object
     *
     * @return    The entityItem value
     */
    public EANList getEntityItem() {
        return getData();
    }

    /**
     *  Gets the entityItem attribute of the EntityGroup object
     *
     * @param  _s  Description of the Parameter
     * @return     The entityItem value
     */
    public EntityItem getEntityItem(String _s) {
        return (EntityItem) getData(_s);
    }

    /**
     *  Gets the entityItem attribute of the EntityGroup object
     *
     * @param  _str  Description of the Parameter
     * @param  _i    Description of the Parameter
     * @return       The entityItem value
     */
    public EntityItem getEntityItem(String _str, int _i) {
        return (EntityItem) getData(_str + _i);
    }

    /**
     *  Gets the entityItem attribute of the EntityGroup object
     *
     * @param  _i  Description of the Parameter
     * @return     The entityItem value
     */
    public EntityItem getEntityItem(int _i) {
        return (EntityItem) getData(_i);
    }

    /**
     *  Gets the entityItemCount attribute of the EntityGroup object
     *
     * @return    The entityItemCount value
     */
    public int getEntityItemCount() {
        return getDataCount();
    }

    /**
     *  Sets the entityItem attribute of the EntityGroup object
     *
     * @param  _el  The new entityItem value
     */
    protected void setEntityItem(EANList _el) {
        setData(_el);
    }

    /**
     *  Description of the Method
     *
     * @param  _str  Description of the Parameter
     * @param  _i    Description of the Parameter
     * @return       Description of the Return Value
     */
    public boolean containsEntityItem(String _str, int _i) {
        return containsData(_str + _i);
    }

    // EANMetaAttribute Accessors

    /**
     *  Gets the metaAttribute attribute of the EntityGroup object
     *
     * @return    The metaAttribute value
     */
    public EANList getMetaAttribute() {
        return getMeta();
    }

    /**
     *  Sets the metaAttribute attribute of the EntityGroup object
     *
     * @param  _el  The new metaAttribute value
     */
    public void setMetaAttribute(EANList _el) {
        setMeta(_el);
    }

    /**
     *  Gets the metaAttribute attribute of the EntityGroup object
     *
     * @param  _str  Description of the Parameter
     * @return       The metaAttribute value
     */
    public EANMetaAttribute getMetaAttribute(String _str) {
        Object o = getMeta(_str); //VEEdit CR0815056514
        if (o instanceof Implicator) { //VEEdit CR0815056514
            o = ( (Implicator) o).getParent(); //VEEdit CR0815056514
        } //VEEdit CR0815056514
        return (EANMetaAttribute) o; //VEEdit CR0815056514
//VEEdit CR0815056514        return (EANMetaAttribute) getMeta(_str);
    }

    /**
     *  Gets the metaAttribute attribute of the EntityGroup object
     *
     * @param  _i  Description of the Parameter
     * @return     The metaAttribute value
     */
    public EANMetaAttribute getMetaAttribute(int _i) {
        Object o = getMeta(_i); //VEEdit CR0815056514
        if (o instanceof Implicator) { //VEEdit CR0815056514
            o = ( (Implicator) o).getParent(); //VEEdit CR0815056514
        } //VEEdit CR0815056514
        return (EANMetaAttribute) o; //VEEdit CR0815056514
//VEEdit CR0815056514        return (EANMetaAttribute)getMeta(_i);
    }

    /**
     *  Gets the metaAttributeCount attribute of the EntityGroup object
     *
     * @return    The metaAttributeCount value
     */
    public int getMetaAttributeCount() {
        return getMetaCount();
    }

    /**
     *  Gets the metaLinkGroup attribute of the EntityGroup object
     *
     * @return    The metaLinkGroup value
     */
    public MetaLinkGroup getMetaLinkGroup() {
        return m_mlg;
    }

    // Misc Accessors

    /*
     *  We are displayable if we are a relator.. or an
     *  Association tab
     *
     *  return always true for now
     */
    /**
     *  Gets the displayable attribute of the EntityGroup object
     *
     * @return    The displayable value
     */
    public boolean isDisplayable() {

        // If some overriding force mode has been set
        // return that.. otherwise calculate it from the known facts

        if (isForceMode()) {
            return isForceDisplay();
        }

        // Lets calculate

        if (isParentLike() && getEntityItemCount() == 0) {
            return false;
        }

        if (isRelator() || isAssoc()) {
            return true;
        }

        // I must be an entity.. so if anychildren have an uplink.. I am covered
        // otherwise.. I must show myself
        for (int ii = 0; ii < getEntityItemCount(); ii++) {
            EntityItem ei = getEntityItem(ii);
            if (!ei.hasUpLinks()) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Gets the creatable attribute of the EntityGroup object
     *
     * @return    The creatable value
     */
    public boolean isCreatable() {
        return m_bCreate;
    }

    /**
     *  Gets the writable attribute of the EntityGroup object
     *
     * @return    The writable value
     */
    public boolean isWritable() {
        return m_bWrite;
    }

    /**
     *  Gets the readable attribute of the EntityGroup object
     *
     * @return    The readable value
     */
    public boolean isReadable() {
        return m_bRead;
    }

    /**
     *  Gets the relator attribute of the EntityGroup object
     *
     * @return    The relator value
     */
    public boolean isRelator() {
        return m_bRelator;
    }

    /**
     *  Gets the assoc attribute of the EntityGroup object
     *
     * @return    The assoc value
     */
    public boolean isAssoc() {
        return m_bAssoc;
    }

    /**
     *  Gets the classified attribute of the EntityGroup object
     *
     * @return    The classified value
     */
    public boolean isClassified() {
        return m_bClassify;
    }

    //
    // Some function based on. .
    /**
     *  Gets the editable attribute of the EntityGroup object
     *
     * @return    The editable value
     */
    public boolean isEditable() {
        return false;
    }

    /**
     *  Gets the entityItemsAsArray attribute of the EntityGroup object
     *
     * @return    The entityItemsAsArray value
     */
    public EntityItem[] getEntityItemsAsArray() {
        EntityItem[] aei = new EntityItem[getEntityItemCount()];
        getEntityItem().copyTo(aei);
        return aei;
    }

    /**
     *  Gets the capability attribute of the EntityGroup object
     *
     * @return    The capability value
     */
    protected String getCapability() {
        return m_strCapability;
    }

    //
    // Mutators
    //

    // General Mutators

    // EntityItem Mutators

    /**
     *  Description of the Method
     * 
     * @param  _ei  Description of the Parameter
     */
    public void putEntityItem(EntityItem _ei) {
        putData(_ei);
        _ei.setParent(this);
    }

    /**
     *  Description of the Method
     *
     * @param  _ei  Description of the Parameter
     * @return      Description of the Return Value
     */
    public EntityItem removeEntityItem(EntityItem _ei) {
        return (EntityItem) removeData(_ei);
    }

    /*
     *  Attempts to reset the EntityItem List for this group
     *  Known  memory leak issues over time
     *  Use only at programmer caution
     */
    /**
     *  Description of the Method
     */
    public void resetEntityItem() {
        for (int ii = 0; ii < getEntityItemCount(); ii++) {
            EntityItem ei = getEntityItem(ii);
            ei.resetAttribute();
            ei.setParent(null);
        }
        resetData();
    }

    // EANMetaAttribute Mutators

    /**
     *  Description of the Method
     *
     * @param  _ma  Description of the Parameter
     */
    public void putMetaAttribute(EANMetaAttribute _ma) {
        putMeta(_ma);
        _ma.setParent(this);
    }

    /**
     *  Description of the Method
     *
     * @param  _ma  Description of the Parameter
     * @return      Description of the Return Value
     */
    public EANMetaAttribute removeMetaAttribute(EANMetaAttribute _ma) {
        return (EANMetaAttribute) removeMeta(_ma);
    }

    // Capability Mutators

    /**
     *  Sets the write attribute of the EntityGroup object
     *
     * @param  _b  The new write value
     */
    protected void setWrite(boolean _b) {
        m_bWrite = _b;
    }

    /**
     *  Sets the create attribute of the EntityGroup object
     *
     * @param  _b  The new create value
     */
    protected void setCreate(boolean _b) {
        m_bCreate = _b;
    }

    /**
     *  Sets the read attribute of the EntityGroup object
     *
     * @param  _b  The new read value
     */
    protected void setRead(boolean _b) {
        m_bRead = _b;
    }

    /**
     *  Sets the capability attribute of the EntityGroup object
     *
     * @param  _str  The new capability value
     */
    protected void setCapability(String _str) {
        if (_str == null) {
            D.ebug(D.EBUG_SPEW, "EntityGroup.setCapability. *** Capability is null ***:" + getKey());
            return;
        }
        // allow for N for RCQ00213801
    	if(_str.charAt(0) == MetaLink.NOT_LINKALL){
    		if(_str.length()>1){
    			_str = _str.substring(1); // let rest of code read the next character
    		}else{
    			_str="R"; // restore the default
    		}
    	}
        
       /* setRead(_str.charAt(0) == 'R' || _str.charAt(0) == 'W' || _str.charAt(0) == 'C');
        setWrite(_str.charAt(0) == 'W' || _str.charAt(0) == 'C');
        setCreate(_str.charAt(0) == 'C');*/
        setRead(_str.charAt(0) == MetaLink.READ || _str.charAt(0) == MetaLink.WRITE || _str.charAt(0) == MetaLink.CREATE);
        setWrite(_str.charAt(0) == MetaLink.WRITE || _str.charAt(0) == MetaLink.CREATE);
        setCreate(_str.charAt(0) == MetaLink.CREATE);
        m_strCapability = _str;
    }

    /**
     *  Sets the classify attribute of the EntityGroup object
     *
     * @param  _str  The new classify value
     */
    private void setClassify(String _str) {
        m_bClassify = (_str.charAt(0) == 'Y');
    }

    /**
     *  Sets the classify attribute of the EntityGroup object
     *
     * @param  _b  The new classify value
     */
    private void setClassify(boolean _b) {
        m_bClassify = _b;
    }

    // MetaLinkGroup Mutators

    /**
     *  Sets the metaLinkGroup attribute of the EntityGroup object
     *
     * @param  _mlg  The new metaLinkGroup value
     */
    protected void setMetaLinkGroup(MetaLinkGroup _mlg) {
        m_mlg = _mlg;
        if (m_mlg != null) {
            m_mlg.setParent(this);
        }
    }

    /*
     *  Sets if this guy doubles as a relaotr
     */
    /**
     *  Sets the relator attribute of the EntityGroup object
     *
     * @param  _b     The new relator value
     * @param  _str1  The new relator value
     * @param  _str2  The new relator value
     */
    public void setRelator(boolean _b, String _str1, String _str2) {
        m_bRelator = _b;
        m_strEntity1Type = _b ? _str1 : "";
        m_strEntity2Type = _b ? _str2 : "";
    }

    /*
     *  Sets if this guy doubles as an association
     */
    /**
     *  Sets the assoc attribute of the EntityGroup object
     *
     * @param  _b     The new assoc value
     * @param  _str1  The new assoc value
     * @param  _str2  The new assoc value
     */
    protected void setAssoc(boolean _b, String _str1, String _str2) {
        m_bAssoc = _b;
        m_strEntity1Type = _b ? _str1 : "";
        m_strEntity2Type = _b ? _str2 : "";
    }

    /*
     *  This performs object cleanup
     */
    /**
     *  Description of the Method
     */
    public void dereference() {
    	EntityItem eiArray[] = this.getEntityItemsAsArray();
    	for (int i=0; i<eiArray.length; i++){
    		eiArray[i].dereference();
    		eiArray[i]=null;
    	}

        // Step backwards through the Meta List
        for (int ii = getMetaAttributeCount() - 1; ii >= 0; ii--) {
            EANMetaAttribute ma = getMetaAttribute(ii);
            if (ma != null) {
                ma.dereference();
            }
        }
        
        m_strCapability = null;
        m_strEntity1Type = null;
        m_strEntity2Type = null;
        m_strPurpose = null;
        
        if (m_mlg!=null){
        	m_mlg.dereference();
        	m_mlg = null;
        }
        if (m_mcog!=null){
        	m_mcog.dereference();
        	m_mcog = null;
        }
        if (m_SFInfo != null){
        	m_SFInfo.dereference();
        	m_SFInfo = null;
        }
        if (m_r51Grp != null){
        	m_r51Grp.dereference();
        	m_r51Grp = null;
        }
        if (m_depFlagList != null){
        	m_depFlagList.dereference();
        	m_depFlagList = null;
        }
        if (m_clsgGlobal != null){
        	m_clsgGlobal.dereference();
        	m_clsgGlobal = null;
        }
        if (m_elClassify != null){
        	for (int i=0; i<getClassificationGroupCount(); i++){
        		ClassificationGroup cg = getClassificationGroup(i);
        		cg.dereference();
        	}
        	m_elClassify.clear();
        	m_elClassify = null;
        }
        if (m_elRestricted != null){
        	for (int i=0; i<getRestrictionGroupCount(); i++){
        		RestrictionGroup cg = getRestrictionGroup(i);
        		cg.dereference();
        	}
        	m_elRestricted.clear();
        	m_elRestricted = null;
        }
        if (m_elRequired != null){
        	for (int i=0; i<getRequiredGroupCount(); i++){
        		RequiredGroup cg = getRequiredGroup(i);
        		cg.dereference();
        	}
        	m_elRequired.clear();
        	m_elRequired = null;
        }
        
        if (m_elReset != null){
        	for (int i=0; i<getResetGroupCount(); i++){
        		ResetGroup cg = getResetGroup(i);
        		cg.dereference();
        	}
        	m_elReset.clear();
        	m_elReset = null;
        }
       
        if (m_elooc != null){
        	for (int i=0; i<getOutOfCirculationCount(); i++) {
        		MetaFlag mf = getOutOfCirculation(i);
        		mf.dereference();
        	}
        	m_elooc.clear();
        	m_elooc = null;
        }
        
        if (m_elLoc != null){
        	for (int i=0; i<getLocalRuleGroupCount(); i++){
        		LocalRuleGroup cg = getLocalRuleGroup(i);
        		cg.dereference();
        	}
        	m_elLoc.clear();
        	m_elLoc = null;
        }
        if (m_actionGroupList != null){
        	m_actionGroupList.dereference();
        	m_actionGroupList = null;
        }
        
        if (m_hashUniqueAttGroups!= null){
        	Enumeration e = m_hashUniqueAttGroups.elements();
        	while (e.hasMoreElements()) {
        		UniqueAttributeGroup uag = (UniqueAttributeGroup) e.nextElement();
        		uag.dereference();
        	}
        	m_hashUniqueAttGroups.clear();
        	m_hashUniqueAttGroups = null;
        }
        
        if (m_hashDepAttVal!= null){
        	Enumeration e = m_hashDepAttVal.elements();
        	while (e.hasMoreElements()) {
        		DependentAttributeValue uag = (DependentAttributeValue) e.nextElement();
        		uag.dereference();
        	}
        	m_hashDepAttVal.clear();
        	m_hashDepAttVal = null;
        }
        
        super.dereference();
    }

    /**
     *  Sets the purpose attribute of the EntityGroup object
     *
     * @param  _str  The new purpose value
     */
    protected void setPurpose(String _str) {
        m_strPurpose = _str;
    }

    /**
     *  Gets the purpose attribute of the EntityGroup object
     *
     * @return    The purpose value
     */
    public String getPurpose() {
        return m_strPurpose;
    }

    /**
     *  Description of the Method
     *
     * @param  _bBrief  Description of the Parameter
     * @return          Description of the Return Value
     */
    public String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();
        strbResult.append(NEW_LINE + "EntityGroup:" + getKey() + ":ShortDesc:" + getShortDescription() + ":LongDesc:" + getLongDescription() + ":");
        strbResult.append(NEW_LINE + "hasData:" + hasData() + ":isDisplayable:" + isDisplayable() + ":isCreatable:" + isCreatable() + ":isWritable:" +
                          isWritable() + ":isReadable:" + isReadable() + ":isRelator:" + isRelator() + ":isAssoc:" + isAssoc() + ":isClassified:" +
                          isClassified());
        strbResult.append(NEW_LINE + "EntityGroupProfile:" + getProfile().toString());
        strbResult.append(NEW_LINE + "EntityType1/2:" + getEntity1Type() + ":" + getEntity2Type());
        strbResult.append(NEW_LINE + "Purpose:" + getPurpose());

        if (!_bBrief) {
            if (getMetaLinkGroup() != null) {
                strbResult.append(NEW_LINE + "MetaLinkGroup:" + NEW_LINE + getMetaLinkGroup().dump(_bBrief));
            }
            strbResult.append(NEW_LINE + "MetaAttributes:" + NEW_LINE);
            for (int ii = 0; ii < getMetaAttributeCount(); ii++) {
                EANMetaAttribute ma = getMetaAttribute(ii);
                strbResult.append(NEW_LINE + "MA:" + ii + ":" + ma.dump(_bBrief));
            }
            strbResult.append(NEW_LINE + "RequiredGroups:" + NEW_LINE);
            for (int ii = 0; ii < getRequiredGroupCount(); ii++) {
                RequiredGroup rg = getRequiredGroup(ii);
                strbResult.append(NEW_LINE + "RS:" + ii + ":" + rg.dump(_bBrief));
            }
            strbResult.append(NEW_LINE + "RestictionGroups:" + NEW_LINE);
            for (int ii = 0; ii < getRestrictionGroupCount(); ii++) {
                RestrictionGroup rg = getRestrictionGroup(ii);
                strbResult.append(NEW_LINE + "RS:" + ii + ":" + rg.dump(_bBrief));
            }
            strbResult.append(NEW_LINE + "ResetGroups:" + NEW_LINE);
            for (int ii = 0; ii < getResetGroupCount(); ii++) {
                ResetGroup rg = getResetGroup(ii);
                strbResult.append(NEW_LINE + "RS:" + ii + ":" + rg.dump(_bBrief));
            }
            strbResult.append(NEW_LINE + "ClassificationGroups:" + NEW_LINE);
            for (int ii = 0; ii < getClassificationGroupCount(); ii++) {
                ClassificationGroup cg = getClassificationGroup(ii);
                strbResult.append(NEW_LINE + "CG:" + ii + ":" + cg.dump(_bBrief));
            }
            strbResult.append(NEW_LINE + "Flags Out of Circulation:" + NEW_LINE);
            for (int ii = 0; ii < getOutOfCirculationCount(); ii++) {
                MetaFlag mf = getOutOfCirculation(ii);
                strbResult.append(NEW_LINE + "mf:" + ii + ":" + mf.dump(_bBrief));
            }

            strbResult.append(NEW_LINE + "EntityItems:" + NEW_LINE);
            for (int ii = 0; ii < getEntityItemCount(); ii++) {
                EntityItem ei = getEntityItem(ii);
                strbResult.append(NEW_LINE + "EI:" + ii + ":" + ei.dump(_bBrief));
            }

        }

        return strbResult.toString();
    }

    /*
     *  This returns the object that we are interested in based on the _str key
     */
    /**
     *  Description of the Method
     *
     * @param  _str  Description of the Parameter
     * @param  _b    Description of the Parameter
     * @return       Description of the Return Value
     */
    public Object get(String _str, boolean _b) {
        if (_str.equals(DESCRIPTION)) {
            return getLongDescription();
        }
        return null;
    }

    /**
     *  Gets the help attribute of the EntityGroup object
     *
     * @param  _str  Description of the Parameter
     * @return       The help value
     */
    public String getHelp(String _str) {
        return getLongDescription();
    }

    /**
     *  Gets the eANObject attribute of the EntityGroup object
     *
     * @param  _str  Description of the Parameter
     * @return       The eANObject value
     */
    public EANFoundation getEANObject(String _str) {
        return this;
    }

    /*
     *  Nothing is editable .. so it is always false
     */
    /**
     *  Description of the Method
     *
     * @param  _s                            Description of the Parameter
     * @param  _o                            Description of the Parameter
     * @return                               Description of the Return Value
     * @exception  EANBusinessRuleException  Description of the Exception
     */
    public boolean put(String _s, Object _o) throws EANBusinessRuleException {
        return false;
    }

    /*
     *  Nothing is Editable .. so it is always false
     */
    /**
     *  Gets the editable attribute of the EntityGroup object
     *
     * @param  _s  Description of the Parameter
     * @return     The editable value
     */
    public boolean isEditable(String _s) {
        return false;
    }

    /*
     *  Nothing to Lock.. so it is always false
     */
    /**
     *  Gets the locked attribute of the EntityGroup object
     *
     * @param  _s            Description of the Parameter
     * @param  _rdi          Description of the Parameter
     * @param  _db           Description of the Parameter
     * @param  _ll           Description of the Parameter
     * @param  _prof         Description of the Parameter
     * @param  _lockOwnerEI  Description of the Parameter
     * @param  _iLockType    Description of the Parameter
     * @param  _bCreateLock  Description of the Parameter
     * @return               The locked value
     * @param _strTime
     */
    public boolean isLocked(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI,
                            int _iLockType, String _strTime, boolean _bCreateLock) {
        return false;
    }

    /*
     *  No LockGroup to return
     */
    /**
     *  Gets the lockGroup attribute of the EntityGroup object
     *
     * @param  _s  Description of the Parameter
     * @return     The lockGroup value
     */
    public LockGroup getLockGroup(String _s) {
        return null;
    }

    /**
     *  Description of the Method
     *
     * @param  _str          Description of the Parameter
     * @param  _lockOwnerEI  Description of the Parameter
     * @param  _prof         Description of the Parameter
     * @return               Description of the Return Value
     */
    public boolean hasLock(String _str, EntityItem _lockOwnerEI, Profile _prof) {
        return false;
    }

    /**
     *  Description of the Method
     *
     * @param  _s  Description of the Parameter
     */
    public void rollback(String _s) {
    }

    /**
     *  Description of the Method
     *
     * @param  _db                                        Description of the Parameter
     * @param  _rdi                                       Description of the Parameter
     * @exception  EANBusinessRuleException               Description of the Exception
     * @exception  MiddlewareBusinessRuleException        Description of the Exception
     * @exception  RemoteException                        Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  SQLException                           Description of the Exception
     */
    public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException, RemoteException,
        MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {

        // Here we loop through each entityItem (and its child)
        // and build a vct Of Transactions...

        EANList eanlst = new EANList();
        OPICMList ol = null;
        Vector vctReturnEntityKeys = new Vector();
        Vector vctDeleteParentItems = new Vector();

        // we do not want to pop the stack because if the update fails the
        // client would like to try again
        //log this in jui so user can see something is happening
        D.ebug(D.EBUG_INFO, "*** EntityGroup Checking commit for " + getDataCount() + " " + getEntityType() + " entities and their children");
        
    	EntityItem lockOwnerEI = new EntityItem(null, getProfile(), Profile.OPWG_TYPE, getProfile().getOPWGID());
        for (int ii = 0; ii < getDataCount(); ii++) {

            EntityItem ei = (EntityItem) getData(ii);
            // make sure the user still has lock to this item - entity may still have some added default values even though it was unlocked and should not be saved
            //VE edit can not use this check
            if(!isVEEdit() && ei.getEntityID()>0 && !ei.hasLock(lockOwnerEI, getProfile())){ 
            	if(this.isRelator()){
            		// check downlink for lock
            		if(ei.getDownLink(0)!=null){
            			EntityItem dnitem = (EntityItem)ei.getDownLink(0);
            			if(!dnitem.hasLock(lockOwnerEI, getProfile())){
            				D.ebug(D.EBUG_INFO, "*** EntityGroup item[" + ii + "] skipping relator " +
            						ei.getKey()+" downlink "+dnitem.getKey()+" is not locked");
            				continue;
            			}
            		}
            	}else{
            		D.ebug(D.EBUG_INFO, "*** EntityGroup item[" + ii + "] skipping " + ei.getKey()+" is not locked");
            		continue;
            	}
            }
            D.ebug(D.EBUG_INFO, "*** EntityGroup Checking item[" + ii + "] " + ei.getKey());
            
            ReturnEntityKey rek = ei.generateUpdateEntity(true);
            if (rek != null) {
                vctReturnEntityKeys.addElement(rek);
                eanlst.put(ei);
            }

            EntityItemException valException = ei.validateEntityItem(_db, _rdi); //CR093005678 CR0930055856
            if (valException != null) { //CR093005678 CR0930055856
                throw valException; //CR093005678 CR0930055856
            } //CR093005678 CR0930055856

            // Do not forget to drag any children with you
            D.ebug(D.EBUG_INFO, "*** EntityGroup Checking VeEdit");
            if (getEntityList() != null && getEntityList().isVEEdit()) {
                //VEEdit_delete MN30841458
                boolean deleteNeeded = ei.handleVEEdit(_db, _rdi, eanlst, vctReturnEntityKeys, vctDeleteParentItems);
                if (deleteNeeded) { // delete is needed
                    D.ebug(D.EBUG_INFO, "*** EntityGroup VEEdit will delete children for item[" + ii + "] " + ei.getKey());
                    continue;
                }
            }
            else if (isRelator() || isAssoc()) {
                boolean bGoDown = true;
                EntityList entl = getEntityList();
                if (entl != null && entl.isEditRelatorOnly()) {
                    bGoDown = false;
                }

                if (bGoDown) {
                    EntityItem eiDown = (EntityItem) ei.getDownLink(0);
                    if (entl != null && entl.isCreateParent()) {
                        eiDown = (EntityItem) ei.getUpLink(0);
                    }
                    if (eiDown != null) {
                        if (!eanlst.containsKey(eiDown.getKey())) {
                            ReturnEntityKey rekDown = eiDown.generateUpdateEntity(true);
                            if (rekDown != null) {
                                vctReturnEntityKeys.addElement(rekDown);
                                eanlst.put(eiDown);
                            }
                        }
                    }
                }

                // Check for Newness and the fact that it is not an Assoc placeholder
                if (ei.getEntityID() < 0 && !isAssoc()) {
                    if (entl != null && entl.isCreateParent()) {
                        EntityItem eip = null;
                        EntityItem eid = (EntityItem) ei.getDownLink(0);
                        if (eid.getEntityID() < 0) {
                            EntityItemException eie = new EntityItemException();
                            eie.add(ei, "ERR:EntityGroup.commit().1. No Child Item is selected.  Please select a child for EntityItem:" + ei.toString());
                            throw eie;
                        }
                        eip = (EntityItem) ei.getUpLink(0);
                        vctReturnEntityKeys.addElement(new ReturnRelatorKey(ei.getEntityType(), ei.getEntityID(), eip.getEntityType(),
                            eip.getEntityID(), eid.getEntityType(), eid.getEntityID(), true));
                        if (!eanlst.containsKey(ei.getKey())) {
                            eanlst.put(ei);
                        }
                    }
                    else {
                        EntityItem eid = null;
                        EntityItem eip = (EntityItem) ei.getUpLink(0);
                        if (eip.getEntityID() < 0) {
                            EntityItemException eie = new EntityItemException();
                            eie.add(ei,
                                    "ERR:EntityGroup.commit().1. No Parent Item is selected.  Please select a parent for EntityItem:" + ei.toString() +
                                    ", eip is:" + eip.getEntityID() + ", key is:" + eip.getKey());
                            throw eie;
                        }
                        eid = (EntityItem) ei.getDownLink(0);
                        vctReturnEntityKeys.addElement(new ReturnRelatorKey(ei.getEntityType(), ei.getEntityID(), eip.getEntityType(),
                            eip.getEntityID(), eid.getEntityType(), eid.getEntityID(), true));
                        if (!eanlst.containsKey(ei.getKey())) {
                            eanlst.put(ei);
                        }
                    }
                }
            }else{
            	// handle case where search and edit and then create is done, commit is on the entity not the relator
            	//MN 38666284 - CQ00022911
            	EntityList eList = getEntityList();
            	if (ei.getEntityID() < 0 && eList!=null){
            		EANActionItem action = eList.getParentActionItem();
            		if (action instanceof EditActionItem){
            			EditActionItem eai = (EditActionItem)action;
            			if (eai.canCreate()){
            				EntityItem eiRelator = (EntityItem) ei.getUpLink(0);
            				if (eiRelator != null) {
            					EntityItem eiparent = (EntityItem) eiRelator.getUpLink(0);
            					if (eiparent!=null){
            						vctReturnEntityKeys.addElement(new ReturnRelatorKey(eiRelator.getEntityType(), eiRelator.getEntityID(), 
            								eiparent.getEntityType(),
            								eiparent.getEntityID(), ei.getEntityType(), ei.getEntityID(), true));
            						if (!eanlst.containsKey(ei.getKey())) {
            							eanlst.put(ei);
            						}
            					}
            				}	 
            			}
            		}
            	}
            }
        }
        
        lockOwnerEI.dereference();

        if (vctDeleteParentItems.size() > 0) { // delete is needed
            D.ebug(D.EBUG_INFO, "*** EntityGroup VEEdit deleting children for " + vctDeleteParentItems.size() + " entities");
            try {
                EntityItem.doVEEditDelete(getEntityList(), vctDeleteParentItems, _db, _rdi);
            }
            catch (LockException lex) {
                lex.printStackTrace();
            }
        }
        if (eanlst.size() > 0) {
            // Now.. we attempt the update.. EEK!
            //log this in jui so user can see something is happening
            D.ebug(D.EBUG_INFO, "*** EntityGroup Updating " + eanlst.size() + " " + getEntityType() + " entities and their children");

            if (_rdi != null) {
                ol = _rdi.update(getProfile(), vctReturnEntityKeys);
            }
            else if (_db != null) {
                ol = _db.update(getProfile(), vctReturnEntityKeys);
            }

            // So now if everything went ok.. we commit local on all EntityItems in the list

            if (ol == null) {
                D.ebug(D.EBUG_DETAIL, "ERR:EntityGroup.commit().2. OL in return call from update is null!");
            }
            else {
                for (int ii = 0; ii < eanlst.size(); ii++) {
                    EntityItem ei = (EntityItem) eanlst.getAt(ii);
                    EntityGroup eg = ei.getEntityGroup();

                    // is this null?

                    if (ol.get(ei.getKey()) instanceof ReturnEntityKey) {
                        ReturnEntityKey rek = (ReturnEntityKey) ol.get(ei.getKey());
                        if (rek.m_iEntityID < 0) {
                            if (rek.isPosted() && rek.isActive()) {
                                String strOld = ei.getKey();
                                ei.setEntityID(rek.getReturnID());
                                eg.getData().resetKey(strOld);
                            }
                        }
                    }
                    else if (ol.get(ei.getKey()) instanceof ReturnRelatorKey) {
                        ReturnRelatorKey rrk = (ReturnRelatorKey) ol.get(ei.getKey());
                        if (rrk.getEntityID() < 0) {
                            if (rrk.isPosted() && rrk.isActive()) {
                                String strOld = ei.getKey();
                                ei.setEntityID(rrk.getReturnID());
                                eg.getData().resetKey(strOld);
                            }
                        }
                    }
                    else {
                        D.ebug(D.EBUG_SPEW, "OL Entity:" + ei.getKey() + ":Cannot be found in the return list");
                    }
                    ei.commitLocal();
                }
                //log this in jui so user can see something is happening
                D.ebug(D.EBUG_INFO, "*** EntityGroup Completed updates for " + eanlst.size() + " " + getEntityType() + " entities and their children");
            }
        }

    }

    /**
     *  return the column information here..
     *  Here we must build an array of implicators
     *  with a unique Key. (EntityType.AttributeCode)
     *  We have to get the columns from both the Relator..
     *  and the Entity2Type.
     *
     *  Make sure if we were created w a Navigate Purpose in mind.. only include the
     *  navigate meta attributes as columns.
     *  This returns the keys for navigate and for horizontal edit.
     *
     * @return    The columnList value
     */
    public EANList getColumnList() {
        EANList el = new EANList();
        EANActionItem ai = null;
        MetaColumnOrderGroup mcog = null;
        boolean bCreateParent = false;

        String strRelTag = (isRelator() ? "R" : "C");

        // If we are a relator.. lets  get the Child EntityList first
        if ( (isRelator() || isAssoc()) && ! (getParent() instanceof MetaEntityList) && !isVEEdit()) {
            // if this EntityGroup is from a MetaEntityList --> dont execute this part
            EntityList entl = getEntityList();

            if (entl != null) {
                ai = entl.getParentActionItem();
                bCreateParent = entl.isCreateParent();

                if (bCreateParent) {
                    EntityGroup egUp = entl.getEntityGroup(getEntity1Type());
                    if (egUp != null) {
//                        System.out.println("path up to: " + getEntity1Type());
                        el = egUp.getColumnList();
                    }
                    else {
                    }
                }
                else {
                    EntityGroup egDown = entl.getEntityGroup(getEntity2Type());
                    if (egDown != null) {
//                System.out.println("path down to: " + getEntity2Type());
                        el = egDown.getColumnList();
                    }
                }
                // for edit relator, add columns for parent entity

                if (entl.isEditRelatorOnly() || entl.showRelParentChild(getEntityType())) {
//             System.out.println("    processing: " + getEntityType());
                    EntityGroup egUp = null;
                    if (isRelator() && entl.isEditRelatorOnly()) { //6MV4U7
                        String str1Type = getEntity1Type(); //6N5NG7
                        EntityGroup peg = entl.getParentEntityGroup(); //6N5NG7
                        if (peg != null && peg.getEntityType().equals(str1Type)) { //6N5NG7
                            egUp = peg; //6N5NG7
                        }
                        else { //6N5NG7
                            egUp = entl.getEntityGroup(str1Type); //6MV4U7
                        } //6N5NG7
                    }
                    else if (entl.isEditRelatorOnly()) {
                        egUp = entl.getParentEntityGroup();
                    }
                    else {
                        egUp = entl.getEntityGroup(getEntity1Type());
                    }

                    if (egUp != null) {
//                        System.out.println("egUp is: " + egUp.getEntityType());
                        for (int ii = 0; ii < egUp.getMetaAttributeCount(); ii++) {
                            EANMetaAttribute ma = egUp.getMetaAttribute(ii);
                            try {
                                Implicator maImplicator = new Implicator(ma, getProfile(), getEntity1Type() + ":" + ma.getKey() + ":P");
                                maImplicator.putShortDescription(ma.getShortDescription());
                                maImplicator.putLongDescription(ma.getLongDescription());
//                                System.out.println("Up1: " + getEntity1Type() + ":" + ma.getKey() + ":P");
                                el.put(maImplicator);
                            }
                            catch (Exception x) {
                                x.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

//    System.out.println("processing attributes for: " + getEntityType());
        for (int ii = 0; ii < getMetaAttributeCount(); ii++) {
            Object o = getMeta(ii);
            if (o instanceof Implicator) {
                Implicator imp = (Implicator) o;
                if (isVEEdit()){
                    el.put(imp);
				}
            }
            else {
                EANMetaAttribute ma = (EANMetaAttribute) o;
                if ( (isNavigateLike() && ma.isNavigate()) || !isNavigateLike()) {
                    try {
                        Implicator maImplicator = new Implicator(ma, getProfile(), getEntityType() + ":" + ma.getKey() + ":" + strRelTag);
                        maImplicator.putShortDescription(ma.getShortDescription());
                        maImplicator.putLongDescription(ma.getLongDescription());
//                System.out.println("put Implicator: " + getEntityType() + ":" + ma.getKey() + ":" + strRelTag);
                        el.put(maImplicator);
                    }
                    catch (Exception x) {
                        x.printStackTrace();
                    }
                }
            }
        }

        // apply MetaColumnOrderGroup if we have one...

        // first check action item

        if (ai != null && ai.hasMetaColumnOrderGroup()) {
            mcog = ai.getMetaColumnOrderGroup();
            D.ebug(D.EBUG_SPEW, "mcog found for ActionItem (" + ai.getKey() + ") - using this one!!");
        }
        // else fallback on entitygroup
        if (mcog == null) {
            mcog = getMetaColumnOrderGroup();
            D.ebug(D.EBUG_SPEW, "mcog found for EntityGroup (" + getKey() + ") - using this one!!");
        }

        if (mcog != null) {
            el = RowSelectableTable.applyColumnOrders(mcog, el);
        }

        //get any missing objs loaded
        if (isVEEdit()){
			for (int ii=0; ii<getEntityItemCount(); ii++){
				EntityItem ei = getEntityItem(ii);
				for (int i = 0; i < el.size(); i++) {
					try {
						Implicator myImp = (Implicator) el.getAt(i);
						ei.getEANObject(myImp.getKey());
					}
					catch (Exception exc) {
						exc.printStackTrace();
					}
				}
			}
		}else{
			for (int i = 0; i < el.size(); i++) {
				try {
					Implicator myImp = (Implicator) el.getAt(i);
					EANMetaAttribute myAtt = (EANMetaAttribute) myImp.getParent();
					EntityGroup myEg = (EntityGroup) myAtt.getParent();
					EntityItem myEi = myEg.getEntityItem(0);
					if (myEi != null) {
						//myEi.getEANObject(myEg.getEntityType() + ":" + myAtt.getAttributeCode());
						myEi.getThisEANObject(myAtt.getAttributeCode()); // get it directly, key not needed
					}
				}
				catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		}
        
        return el;
    }

    /**
     *  Gets the rowList attribute of the EntityGroup object
     *
     * @return    The rowList value
     */
    public EANList getRowList() {
        return getData();
    }

    /*
     *  This is for a row selectable table model...
     */
    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public boolean canEdit() {
        Profile prof = getProfile();
        EANActionItem ai = null;
        EntityList el = getEntityList();
        if (el == null) {
            return false;
        }
        ai = el.getParentActionItem();
        if (ai == null) {
            return false;
        }

        //
        // OK the basic criteria has been met
        //

        // If we are being used in a Search Action Item
        // always return true
        if (isUsedInSearch()) {
            return true;
        }

        if (ai.isVEEdit()) {
            ExtractActionItem eai = (ExtractActionItem) ai;
            return eai.canEdit();
        }

        if (! (ai instanceof EditActionItem || ai instanceof CreateActionItem || ai instanceof CopyActionItem)) {
            return false;
        }

        //
        // If read only is turned on
        // we need to return false
        if (prof.isReadOnly()) {
            return false;
        }

        // If you can create.. you are ok here
        if (ai instanceof CreateActionItem) {
            return true;
        }

        // If you can copy.. you are ok here
        if (ai instanceof CopyActionItem) {
            return true;
        }

        // OK .. we have an edit.. Lets see if view only is on
        if (ai instanceof EditActionItem) {
            if (! ( (EditActionItem) ai).canEdit()) {
                return false;
            }
            else {
                EditActionItem eai = (EditActionItem) ai;
                if (eai.isEditRelatorOnly()) {
                    return isRelator();
                }
                else {
                    return true;
                }
            }
        }

        return true;
    }

    /*
     *  This is in the context of the RowSelectable Table.  Add a new row
     *  To The table that is used by this as a reference.
     */
    /**
     *  Adds a feature to the Row attribute of the EntityGroup object
     *
     * @return    Description of the Return Value
     */
    public boolean addRow() {
        try {
            if (canCreate()) {
                EntityList el = getEntityList();
                boolean bCreateParent = el.isCreateParent();
                EANActionItem ai = el.getParentActionItem();
                EntityItem ei = new EntityItem(this, null, getEntityType(), ai.getID());
                putEntityItem(ei);
                ei.refreshClassifications();
                ei.refreshRestrictions();
                ei.refreshDefaults();
                ei.refreshRequired();
                ei.refreshResets();
                ei.refreshClassifications(); //TIR USRO-R-WMOY-6LMSP9
                //
                // O.K.  in either case.. we will either build a
                // dummy Assoc Record.. or a placeholder for the Relator
                //
                if (isRelator() || isAssoc()) {
                    if (bCreateParent) {

                        EntityGroup egChild = el.getParentEntityGroup();
                        if(egChild==null){ // searchbinder needed to add relator entitygroup, parentgroup doesnt exist
                        	return false;
                        }
                        EntityGroup egParent = null;
                        EntityItem eiParent = null;
                        EntityItem eiChild = null;

                        if (egChild.getEntityItemCount() == 1) {
                            eiChild = egChild.getEntityItem(0);
                        }
                        else if (egChild.getEntityItemCount() > 1) {
                            // We put it in the never never land place now
                            eiChild = new EntityItem(egChild, null, egChild.getEntityType(), -1);
                        }

                        egParent = el.getEntityGroup(getEntity1Type());
                        eiParent = new EntityItem(egParent, null, egParent.getEntityType(), ai.getID());
                        egParent.putEntityItem(eiParent);
                        // Now hook them all up
                        ei.putUpLink(eiParent);
                        ei.putDownLink(eiChild);

                        D.ebug(D.EBUG_SPEW, "EntityGroup addRow in createParent 03 " + eiParent.getKey());

                        // Refresh the defaults and restrictions here for the parent
                        eiParent.refreshClassifications();
                        eiParent.refreshRestrictions();
                        eiParent.refreshDefaults();
                        eiParent.refreshRequired();
                        eiParent.refreshResets();
                        eiParent.refreshClassifications(); //TIR USRO-R-WMOY-6LMSP9
                    }
                    else {
                        // Pick the first one in the ParentItem list...
                        EntityGroup egParent = el.getParentEntityGroup();
                        if(egParent==null){ // searchbinder needed to add relator entitygroup, parentgroup doesnt exist
                        	return false;
                        }
                        EntityGroup egChild = null;
                        EntityItem eiChild = null;
                        EntityItem eiParent = null;

                        if (egParent.getEntityItemCount() == 1) {
                            eiParent = egParent.getEntityItem(0);
                        }
                        else if (egParent.getEntityItemCount() > 1) {
                            // We put it in the never never land place now
                            eiParent = new EntityItem(egParent, null, egParent.getEntityType(), -1);
                        }

                        egChild = el.getEntityGroup(getEntity2Type());
                        eiChild = new EntityItem(egChild, null, egChild.getEntityType(), ai.getID());
                        egChild.putEntityItem(eiChild);
                        // Now hook them all up
                        ei.putUpLink(eiParent);
                        ei.putDownLink(eiChild);

                        // Refresh the defaults and restrictions here for the child
                        eiChild.refreshClassifications();
                        eiChild.refreshRestrictions();
                        eiChild.refreshDefaults();
                        eiChild.refreshRequired();
                        eiChild.refreshResets();
                        eiChild.refreshClassifications(); //TIR USRO-R-WMOY-6LMSP9
                    }
                }else{
                	// not a relator or assoc
                	// handle case where search and edit and then create is done, is on the entity not the relator
                	if (ai instanceof EditActionItem) {
						EditActionItem eai = (EditActionItem) ai;
						if (eai.canCreate()){ //MN 38666284 - CQ00022911
							repairStructure(eai, ei);
                        }
                    }   	        	
                }
                return true;
            }
        }
        catch (Exception x) {
            x.printStackTrace();
        }

        return false;
    }
    /**
     * handle case where search and edit and then create is done, is on the entity not the relator
     * adding a row (creating a new entity) needs parent to link to
     * MN 38666284 - CQ00022911
     * @param eai
     * @param ei
     * @throws MiddlewareRequestException
     */
    private void repairStructure(EditActionItem eai, EntityItem ei) 
    throws MiddlewareRequestException
    {
    	EntityList el = getEntityList();
    	MetaLink  ml = eai.getMetaLink();
    	if(ml != null){
    		// Pick the first one in the ParentItem list...
    		EntityGroup egparent = el.getParentEntityGroup();
    		if (egparent==null){
    			this.removeEntityItem(ei);
    			throw new MiddlewareRequestException("Parent group not found, cannot link "+ei.getKey()+
    					" back through "+ml.getEntityType()+" to "+ml.getEntity1Type());
    		}
    		EntityItem parent = egparent.getEntityItem(0);
    		// create a new relator
    		EntityGroup egRelator = el.getEntityGroup(ml.getEntityType());
    		if (egRelator==null){
    			this.removeEntityItem(ei);
    			throw new MiddlewareRequestException("Relator group "+
    					ml.getEntityType()+" not found, cannot link "+ei.getKey()+
    					" back to "+ml.getEntity1Type());
    		}
    		// create relator using same negative id as entity 
    		EntityItem eiRel = new EntityItem(egRelator, null, egRelator.getEntityType(), ei.getEntityID());
    		egRelator.putEntityItem(eiRel);

    		// Now hook them all up
    		eiRel.putUpLink(parent);
    		eiRel.putDownLink(ei);
    	}else{
    		D.ebug("Warning: "+eai.getActionItemKey()+" can create but does not have MetaLink defining relator");
    	}
    }
    /**
     *  Description of the Method
     *
     * @param  _strKey  Description of the Parameter
     */
    public void removeRow(String _strKey) {
        try {
            if (canCreate()) {
                EntityList el = getEntityList();
                EntityItem ei = getEntityItem(_strKey);
                if (ei.isNew()) {
                    if (isRelator() || isAssoc()) {
                        // remove the child
                        if (el.isCreateParent()) {
                            EntityGroup egParent = el.getEntityGroup(getEntity1Type());
                            EntityItem eiUp = (EntityItem) ei.getUpLink(0);
                            egParent.removeEntityItem(eiUp);
                            ei.removeUpLink(eiUp);

                        }
                        else {
                            EntityGroup egChild = el.getEntityGroup(getEntity2Type());
                            EntityItem eiDown = (EntityItem) ei.getDownLink(0);
                            egChild.removeEntityItem(eiDown);
                            ei.removeDownLink(eiDown);
                        }
                    }
                    removeEntityItem(ei);
                }
            }
        }
        catch (Exception x) {
            x.printStackTrace();
        }
    }

    /*
     *  This is for a row selectable table model...
     */
    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public boolean canCreate() {
        EntityList el = getEntityList();
        EANActionItem ai = el.getParentActionItem();

        if (ai instanceof CreateActionItem) {
            return true;
        }

        // DynaSearch needs to add rows
        if (ai instanceof SearchActionItem) {
            return true;
        }

        // Return canCreate...
        if (ai instanceof EditActionItem) {
            return ( (EditActionItem) ai).canCreate();
        }

        if (ai instanceof ExtractActionItem) { //VEEdit_Create
            return ( (ExtractActionItem) ai).canCreate(); //VEEdit_Create
        } //VEEdit_Create

        return false;
    }

    /*
     *  Returns a basic table adaptor for client rendering of EntityLists
     */
    /**
     *  Gets the entityGroupTable attribute of the EntityGroup object
     *
     * @return    The entityGroupTable value
     */
    public RowSelectableTable getEntityGroupTable() {
        return new RowSelectableTable(this, getLongDescription());
    }

    /**
     *  Gets the actionGroupTable attribute of the EntityGroup object
     *
     * @return    The actionGroupTable value
     */
    public RowSelectableTable getActionGroupTable() {
        try {
            ActionGroupBinder agb = new ActionGroupBinder(this, getProfile(), getActionGroup(), getDownLinkActionGroup());
            return new RowSelectableTable(agb, agb.toString());
        }
        catch (Exception x) {
            x.printStackTrace();
        }
        return null;
    }

    /**
     *  Are these two objects equal?
     *
     * @param  _eg  Description of the Parameter
     * @return      Description of the Return Value
     */
    public final boolean equals(EntityGroup _eg) {

        if (_eg == null) {
            return false;
        }

        if (!getProfile().equals(_eg.getProfile())) {
            return false;
        }

        getProfile().getReadLanguage();
        _eg.getProfile().getReadLanguage();

        if (!getKey().equals(_eg.getKey())) {
            return false;
        }

        // loop through each one and compare

        for (int ii = 0; ii < getEntityItemCount(); ii++) {
            EntityItem ei = getEntityItem(ii);
            EntityItem ei2 = _eg.getEntityItem(ei.getKey());
            if (ei2 == null) {
                return false;
            }
        }

        return true;
    }

    /*
     *  Returns the set of MetaLink objects that intersect
     */
    /**
     *  Gets the metaLinkList attribute of the EntityGroup object
     *
     * @param  _egChild  Description of the Parameter
     * @return           The metaLinkList value
     */
    public MetaLink[] getMetaLinkList(EntityGroup _egChild) {

        MetaLinkGroup mlgChild = null;
        MetaLinkGroup mlgE2Parent = null;
        MetaLink[] aml = null;

        EANList el = new EANList();
        MetaLinkGroup mlgParent = getMetaLinkGroup();

        if (_egChild == null) {
            MetaLink[] amle = new MetaLink[el.size()];
            D.ebug(D.EBUG_SPEW, "EntityGroup.getMetaLinkList():_egChild is null. returning empty list");
            el.copyTo(amle);
            return amle;
        }

        if (mlgParent == null) {
            MetaLink[] amle = new MetaLink[el.size()];
            D.ebug(D.EBUG_SPEW, "EntityGroup.getMetaLinkList():-1:mlgParent is null. returning empty list");
            el.copyTo(amle);
            return amle;
        }

        // if the child is a relator, we need to get its entity2 group

        if (_egChild.isRelator() || _egChild.isAssoc()) {
            EntityGroup cEGChild = null;
            EntityList cEntl = (EntityList) _egChild.getParent();
            if (cEntl == null) {
                MetaLink[] amle = new MetaLink[el.size()];
                D.ebug(D.EBUG_SPEW, "EntityGroup.getMetaLinkList():0:cEntl is null. returning empty list");
                el.copyTo(amle);
                return amle;
            }
            cEGChild = cEntl.getEntityGroup(_egChild.getEntity2Type());
            if (cEGChild == null) {
                MetaLink[] amle = new MetaLink[el.size()];
                D.ebug(D.EBUG_SPEW, "EntityGroup.getMetaLinkList():1:cEGChild is null. returning empty list");
                el.copyTo(amle);
                return amle;
            }
            mlgChild = cEGChild.getMetaLinkGroup();
        }
        else {
            mlgChild = _egChild.getMetaLinkGroup();
        }

        if (mlgChild == null) {
            MetaLink[] amle = new MetaLink[el.size()];
            D.ebug(D.EBUG_SPEW, "EntityGroup.getMetaLinkList():2:mlgChild is null. returning empty list");
            el.copyTo(amle);
            return amle;
        }

        // If we are a relator.. we need to get the other
        if (isRelator() || isAssoc()) {
            EntityGroup egChild = null;
            EntityList entl = (EntityList) getParent();
            if (entl == null) {
                MetaLink[] amle = new MetaLink[el.size()];
                D.ebug(D.EBUG_SPEW, "EntityGroup.getMetaLinkList():3:ent1 is null. returning empty list");
                el.copyTo(amle);
                return amle;
            }
            egChild = entl.getEntityGroup(getEntity2Type());
            if (egChild == null) {
                MetaLink[] amle = new MetaLink[el.size()];
                D.ebug(D.EBUG_SPEW,"EntityGroup.getMetaLinkList():4:egChild is null. returning empty list");
                el.copyTo(amle);
                return amle;
            }
            mlgE2Parent = egChild.getMetaLinkGroup();
        }

        for (int ii = 0; ii < mlgChild.getMetaLinkCount(MetaLinkGroup.UP); ii++) {
            MetaLink mlChild = mlgChild.getMetaLink(MetaLinkGroup.UP, ii);
            MetaLink mlParent = mlgParent.getMetaLink(MetaLinkGroup.DOWN, mlChild.getKey());
            if (mlParent != null && mlParent.isWritable()) {
                // We found a match
                try {
                    el.put(new MetaLink(getProfile(), mlParent));
                }
                catch (Exception x) {
                    x.printStackTrace();
                }
            }

            // If we are a relator.. we must also look down to the navigate Group (entity2Type)
            if (isRelator() || isAssoc()) {
                mlParent = mlgE2Parent.getMetaLink(MetaLinkGroup.DOWN, mlChild.getKey());
                if (mlParent != null && mlParent.isWritable()) {
                    try {
                        el.put(new MetaLink(getProfile(), mlParent));
                    }
                    catch (Exception x) {
                        x.printStackTrace();
                    }
                }
            }
        }

        aml = new MetaLink[el.size()];
        el.copyTo(aml);
        return aml;
    }

    /**
     *  Sets the parentLike attribute of the EntityGroup object
     *
     * @param  _b  The new parentLike value
     */
    public void setParentLike(boolean _b) {
        m_bParentLike = _b;
    }

    /**
     *  Gets the parentLike attribute of the EntityGroup object
     *
     * @return    The parentLike value
     */
    public boolean isParentLike() {
        return m_bParentLike;
    }

    //
    //  Restricted Group Methods
    //

    /**
     *  Gets the restrictionGroupCount attribute of the EntityGroup object
     *
     * @return    The restrictionGroupCount value
     */
    public int getRestrictionGroupCount() {
        if (m_elRestricted == null) {
            return 0;
        }
        return m_elRestricted.size();
    }

    /**
     *  Gets the restrictionGroup attribute of the EntityGroup object
     *
     * @param  _str  Description of the Parameter
     * @return       The restrictionGroup value
     */
    public RestrictionGroup getRestrictionGroup(String _str) {
        if (m_elRestricted == null) {
            return null;
        }
        for(int i=0; i<m_elRestricted.size(); i++){
        	RestrictionGroup eaf = (RestrictionGroup)m_elRestricted.elementAt(i);
        	if(eaf.getKey().equals(_str)){
        		return eaf;
        	}
        }
        return null;
       //memchg  return (RestrictionGroup) m_elRestricted.get(_str);
    }

    /**
     *  Gets the restrictionGroup attribute of the EntityGroup object
     *
     * @param  _i  Description of the Parameter
     * @return     The restrictionGroup value
     */
    public RestrictionGroup getRestrictionGroup(int _i) {
        if (m_elRestricted == null || _i >= m_elRestricted.size()) {
            return null;
        }
        return (RestrictionGroup) m_elRestricted.elementAt(_i);//memchg .getAt(_i);
    }

    /**
     *  Description of the Method
     *
     * @param  _rg  Description of the Parameter
     */
    public void putRestrictionGroup(RestrictionGroup _rg) {
        if (m_elRestricted == null) {
            m_elRestricted = new Vector();// memchg EANList();
        }
        _rg.setParent(this);
        if(!m_elRestricted.contains(_rg)){
        	m_elRestricted.add(_rg);//memchg put(_rg);
        }
    }

    /**
     *  Gets the restrictionGroup attribute of the EntityGroup object
     *
     * @return    The restrictionGroup value
     */
    //memchg protected EANList getRestrictionGroup() {
    protected Vector getRestrictionGroup() {
        //if (m_elRestricted == null) return null;
        return m_elRestricted;
    }

    /**
     *  Sets the restrictionGroup attribute of the EntityGroup object
     *
     * @param  _el  The new restrictionGroup value
     */
   //memchg  private void setRestrictionGroup(EANList _el) {
    private void setRestrictionGroup(Vector _el) {
        m_elRestricted = _el;
    }

    /**
     *  Description of the Method
     *
     * @param  _rg  Description of the Parameter
     */
    public void removeRestrictionGroup(RestrictionGroup _rg) {
        if (m_elRestricted == null) {
            return;
        }
        if (_rg != null) {
            m_elRestricted.remove(_rg);
        }
    }

    //
    //  Required Group Methods
    //

    /**
     *  Gets the requiredGroupCount attribute of the EntityGroup object
     *
     * @return    The requiredGroupCount value
     */
    public int getRequiredGroupCount() {
        if (m_elRequired == null) {
            return 0;
        }
        return m_elRequired.size();
    }

    /**
     *  Gets the requiredGroup attribute of the EntityGroup object
     *
     * @param  _str  Description of the Parameter
     * @return       The requiredGroup value
     */
    public RequiredGroup getRequiredGroup(String _str) {
        if (m_elRequired == null) {
            return null;
        }
       
        for(int i=0; i<m_elRequired.size(); i++){
        	RequiredGroup eaf = (RequiredGroup)m_elRequired.elementAt(i);
        	if(eaf.getKey().equals(_str)){
        		return eaf;
        	}
        }
        return null;
       //memchg  return (RequiredGroup) m_elRequired.get(_str);
    }

    /**
     *  Gets the requiredGroup attribute of the EntityGroup object
     *
     * @param  _i  Description of the Parameter
     * @return     The requiredGroup value
     */
    public RequiredGroup getRequiredGroup(int _i) {
        if (m_elRequired == null || _i>= m_elRequired.size()) {
            return null;
        }
        return (RequiredGroup) m_elRequired.elementAt(_i);//memchg .getAt(_i);
    }

    /**
     *  Description of the Method
     *
     * @param  _rg  Description of the Parameter
     */
    public void putRequiredGroup(RequiredGroup _rg) {
        if (m_elRequired == null) {
            m_elRequired = new Vector();//memchg EANList();
        }
        _rg.setParent(this);
        if(!m_elRequired.contains(_rg)){
        	m_elRequired.add(_rg);//memchg .put(_rg);
        }
    }

    /**
     *  Gets the requiredGroup attribute of the EntityGroup object
     *
     * @return    The requiredGroup value
     *  memchg  protected EANList getRequiredGroup() {
     */
    protected Vector getRequiredGroup() {
        //if (m_elRequired == null) m_elRequired = new EANList();
        return m_elRequired;
    }

    /**
     *  Sets the requiredGroup attribute of the EntityGroup object
     *
     * @param  _el  The new requiredGroup value
     *  memchg    private void setRequiredGroup(EANList _el) {
     */
    private void setRequiredGroup(Vector _el) {
        m_elRequired = _el;
    }

    /**
     *  Description of the Method
     *
     * @param  _rg  Description of the Parameter
     */
    public void removeRequiredGroup(RequiredGroup _rg) {
        if (m_elRequired == null) {
            return;
        }
        if (_rg != null) {
            m_elRequired.remove(_rg);
        }
    }

    //
    //  Out Of CirculationFlag Codes
    //
    /**
     * getOutOfCirculationCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getOutOfCirculationCount() {
        if (m_elooc == null) {
            return 0;
        }
        return m_elooc.size();
    }

    /**
     * getOutOfCirculation
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public MetaFlag getOutOfCirculation(int _i) {
        if (m_elooc == null || _i >= m_elooc.size()) {
            return null;
        }
        return (MetaFlag) m_elooc.elementAt(_i);//memchg .getAt(_i);
    }

    /**
     * isOutOfCirculation
     *
     * @param _mf
     * @return
     *  @author David Bigelow
     */
    public boolean isOutOfCirculation(MetaFlag _mf) {
        if (m_elooc == null) {
            return false;
        }
        return m_elooc.contains(_mf);//memchg .containsKey(_mf.getKey());
    }

    /**
     *  Description of the Method
     *
     * @param  _mf  Description of the Parameter
     */
    public void putOutOfCirculation(MetaFlag _mf) {
        if (m_elooc == null) {
            m_elooc = new Vector();//memchg EANList();
        }
        if(!m_elooc.contains(_mf)){
        	m_elooc.add(_mf);//memchg .put(_mf);
        }
    }

    /**
     *  Sets the outOfCirculation attribute of the EntityGroup object
     *
     * @param  _el  The new outOfCirculation value
     *   memchg private void setOutOfCirculation(EANList _el) {
     */
    private void setOutOfCirculation(Vector _el) {
        m_elooc = _el;
    }

    //
    //  Reset Group Methods
    //

    /**
     *  Gets the resetGroupCount attribute of the EntityGroup object
     *
     * @return    The resetGroupCount value
     */
    public int getResetGroupCount() {
        if (m_elReset == null) {
            return 0;
        }
        return m_elReset.size();
    }

    /**
     *  Gets the resetGroup attribute of the EntityGroup object
     *
     * @param  _str  Description of the Parameter
     * @return       The resetGroup value
     */
    public ResetGroup getResetGroup(String _str) {
        if (m_elReset == null) {
            return null;
        }
        for(int i=0; i<m_elReset.size(); i++){
        	ResetGroup eaf = (ResetGroup)m_elReset.elementAt(i);
        	if(eaf.getKey().equals(_str)){
        		return eaf;
        	}
        }
        return null;
        //memchg return (ResetGroup) m_elReset.get(_str);
    }

    /**
     *  Gets the resetGroup attribute of the EntityGroup object
     *
     * @param  _i  Description of the Parameter
     * @return     The resetGroup value
     */
    public ResetGroup getResetGroup(int _i) {
        if (m_elReset == null || _i>=m_elReset.size()) {
            return null;
        }
        return (ResetGroup) m_elReset.elementAt(_i);//memchg .getAt(_i);
    }

    /**
     *  Description of the Method
     *
     * @param  _rg  Description of the Parameter
     */
    public void putResetGroup(ResetGroup _rg) {
        if (m_elReset == null) {
            m_elReset = new Vector();//memchg EANList();
        }
        _rg.setParent(this);
        if(!m_elReset.contains(_rg)){
        	m_elReset.add(_rg);//memchg .put(_rg);
        }
    }

    /**
     *  Gets the resetGroup attribute of the EntityGroup object
     *
     * @return    The resetGroup value
     *  memchg    protected EANList getResetGroup() {
     */
    protected Vector getResetGroup() {
        //if (m_elReset == null) m_elReset = new EANList();
        return m_elReset;
    }

    /**
     *  Sets the resetGroup attribute of the EntityGroup object
     *
     * @param  _el  The new resetGroup value
     *  memchg    private void setResetGroup(EANList _el) {
     */
    private void setResetGroup(Vector _el) {
        m_elReset = _el;
    }

    /**
     *  Description of the Method
     *
     * @param  _rg  Description of the Parameter
     */
    public void removeResetGroup(ResetGroup _rg) {
        if (m_elReset == null) {
            return;
        }
        if (_rg != null) {
            m_elReset.remove(_rg);
        }
    }

    //
    //  Classification Group Methods
    //

    /**
     *  Gets the classificationGroupCount attribute of the EntityGroup object
     *
     * @return    The classificationGroupCount value
     */
    public int getClassificationGroupCount() {
        if (m_elClassify == null) {
            return 0;
        }
        return m_elClassify.size();
    }

    /**
     *  Gets the classificationGroup attribute of the EntityGroup object
     *
     * @param  _str  Description of the Parameter
     * @return       The classificationGroup value
     */
    public ClassificationGroup getClassificationGroup(String _str) {
        if (m_elClassify == null) {
            return null;
        }
        
        for(int i=0; i<m_elClassify.size(); i++){
        	ClassificationGroup eaf = (ClassificationGroup)m_elClassify.elementAt(i);
        	if(eaf.getKey().equals(_str)){
        		return eaf;
        	}
        }
        return null;
       //memchg  return (ClassificationGroup) m_elClassify.get(_str);
    }

    /**
     *  Gets the classificationGroup attribute of the EntityGroup object
     *
     * @param  _i  Description of the Parameter
     * @return     The classificationGroup value
     */
    public ClassificationGroup getClassificationGroup(int _i) {
        if (m_elClassify == null || _i>=m_elClassify.size()) {
            return null;
        }
        return (ClassificationGroup) m_elClassify.elementAt(_i);//memchg .getAt(_i);
    }

    /**
     *  Description of the Method
     *
     * @param  _clsg  Description of the Parameter
     */
    public void putClassificationGroup(ClassificationGroup _clsg) {
        if (m_elClassify == null) {
            m_elClassify = new Vector();//memchg EANList();
        }
        _clsg.setParent(this);
        if(!m_elClassify.contains(_clsg)){
        	m_elClassify.add(_clsg);//memchg .put(_clsg);
        }
        // If this guy is a global guy.. mark it on the way in
        if (_clsg.isGlobalClassify()) {
            m_clsgGlobal = _clsg;
        }
    }

    /**
     *  Description of the Method
     *
     * @param  _clsg  Description of the Parameter
     */
    public void removeClassificationGroup(ClassificationGroup _clsg) {
        if (m_elClassify == null) {
            return;
        }
        if (_clsg != null) {
            m_elClassify.remove(_clsg);
        }
    }

    /**
     *  Gets the classificationGroup attribute of the EntityGroup object
     *
     * @return    The classificationGroup value
     *    memchg  protected EANList getClassificationGroup()
     */
    protected Vector getClassificationGroup() {
        // if (m_elClassify == null) m_elClassify = new EANList();
        return m_elClassify;
    }

    /**
     *  Sets the classificationGroup attribute of the EntityGroup object
     *
     * @param  _el  The new classificationGroup value
     *  memchg   private void setClassificationGroup(EANList _el) {
     */
    private void setClassificationGroup(Vector _el) {
        m_elClassify = _el;
    }

    //
    //  Local Rule Group
    //

    /**
     * getLocalRuleGroupCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getLocalRuleGroupCount() {
        if (m_elLoc == null) {
            return 0;
        }
        return m_elLoc.size();
    }

    /**
     * getLocalRuleGroup
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public LocalRuleGroup getLocalRuleGroup(int _i) {
        if (m_elLoc == null || _i>= m_elLoc.size()) {
            return null;
        }
        return (LocalRuleGroup) m_elLoc.elementAt(_i);//memchg .getAt(_i);
    }

    /**
     * getLocalRuleGroup
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public LocalRuleGroup getLocalRuleGroup(String _s) {
        if (m_elLoc == null) {
            return null;
        }

        for(int i=0; i<m_elLoc.size(); i++){
        	LocalRuleGroup eaf = (LocalRuleGroup)m_elLoc.elementAt(i);
        	if(eaf.getKey().equals(_s)){
        		return eaf;
        	}
        }
        return null;
       //memchg  return (LocalRuleGroup) m_elLoc.get(_s);
    }

    /**
     * putLocalRuleGroup
     *
     * @param _lrg
     *  @author David Bigelow
     */
    public void putLocalRuleGroup(LocalRuleGroup _lrg) {
        if (m_elLoc == null) {
            m_elLoc = new Vector();// memchg EANList();
        }
        if(!m_elLoc.contains(_lrg)){
        	m_elLoc.add(_lrg);//memchg .put(_lrg);
        }
    }

    /**
     * getLocalRuleGroup
     *
     * @return
     *  @author David Bigelow
     *    memchg   protected EANList getLocalRuleGroup() {
     */
    protected Vector getLocalRuleGroup() {
        return m_elLoc;
    }

    private void setLocalRuleGroup(Vector _el) {
        m_elLoc = _el;
    }

    /**
     * hasLocalRuleGroup
     *
     * @return
     *  @author David Bigelow
     */
    protected boolean hasLocalRuleGroup() {
        return (m_elLoc != null && m_elLoc.size() > 0);
    }

    // UniqueAttributeGroups

    /**
     * getUniqueAttributeGroup
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    protected UniqueAttributeGroup getUniqueAttributeGroup(String _strKey) {
        if (m_hashUniqueAttGroups == null) {
            return null;
        }
        return (UniqueAttributeGroup) m_hashUniqueAttGroups.get(_strKey);
    }

    private void putUniqueAttributeGroup(UniqueAttributeGroup _uag) {
        if (m_hashUniqueAttGroups == null) {
            m_hashUniqueAttGroups = new Hashtable();
        }
        m_hashUniqueAttGroups.put(_uag.getKey(), _uag);
    }

    /**
     * getUniqueAttributeGroupCount
     *
     * @return
     *  @author David Bigelow
     */
    public final int getUniqueAttributeGroupCount() {
        if (!hasUniqueAttributeGroup()) {
            return 0;
        }
        return m_hashUniqueAttGroups.size();
    }

    /**
     * hasUniqueAttributeGroup
     *
     * @return
     *  @author David Bigelow
     */
    public final boolean hasUniqueAttributeGroup() {
        return (m_hashUniqueAttGroups != null && m_hashUniqueAttGroups.size() > 0);
    }

    /**
     * getUniqueAttributeGroupVector
     *
     * @return
     *  @author David Bigelow
     */
    protected final Vector getUniqueAttributeGroupVector() {
        Enumeration e = null;
        try {
            Vector v = new Vector();
            if (!hasUniqueAttributeGroup()) {
                return v;
            }
            e = m_hashUniqueAttGroups.elements();
            while (e.hasMoreElements()) {
                UniqueAttributeGroup uag = (UniqueAttributeGroup) e.nextElement();
                v.addElement(uag.getCopy());
            }
            return v;
        }
        catch (MiddlewareRequestException exc) {
            System.err.println("EntityGroup.getUniqueAttributeGroupVector():" + exc.toString());
            exc.printStackTrace(System.err);
        }
        return null;
    }

    private final Hashtable getUniqueAttributeGroup() {
        return m_hashUniqueAttGroups;
    }

    private final void setUniqueAttributeGroup(Hashtable _hsh) {
        m_hashUniqueAttGroups = _hsh;
    }

    // DependentAttributeValue

    /**
     * getDependentAttributeValue
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    protected DependentAttributeValue getDependentAttributeValue(String _strKey) {
        if (m_hashDepAttVal == null) {
            return null;
        }
        return (DependentAttributeValue) m_hashDepAttVal.get(_strKey);
    }

    private void putDependentAttributeValue(DependentAttributeValue _dav) {
        if (m_hashDepAttVal == null) {
            m_hashDepAttVal = new Hashtable();
        }
        m_hashDepAttVal.put(_dav.getKey(), _dav);
    }

    /**
     * getDependentAttributeValueCount
     *
     * @return
     *  @author David Bigelow
     */
    public final int getDependentAttributeValueCount() {
        if (!hasDependentAttributeValue()) {
            return 0;
        }
        return m_hashDepAttVal.size();
    }

    /**
     * hasDependentAttributeValue
     *
     * @return
     *  @author David Bigelow
     */
    public final boolean hasDependentAttributeValue() {
        return (m_hashDepAttVal != null && m_hashDepAttVal.size() > 0);
    }

    /**
     * getDependentAttributeValueVector
     *
     * @return
     *  @author David Bigelow
     */
    protected final Vector getDependentAttributeValueVector() {
        Enumeration e = null;
        try {
            Vector v = new Vector();
            if (!hasDependentAttributeValue()) {
                return v;
            }
            e = m_hashDepAttVal.elements();
            while (e.hasMoreElements()) {
                v.addElement( ( (DependentAttributeValue) e.nextElement()).getCopy());
            }
            return v;
        }
        catch (MiddlewareRequestException exc) {
            System.err.println("EntityGroup.getDependentAttributeValueVector():" + exc.toString());
            exc.printStackTrace(System.err);
        }
        return null;
    }

    private final Hashtable getDependentAttributeValue() {
        return m_hashDepAttVal;
    }

    private final void setDependentAttributeValue(Hashtable _hsh) {
        m_hashDepAttVal = _hsh;
    }

    private final void setRule51Group(Rule51Group _r51Grp) {
        m_r51Grp = _r51Grp;
    }

    /**
     * hasRule51Group
     *
     * @return
     *  @author David Bigelow
     */
    public final boolean hasRule51Group() {
        return (m_r51Grp != null);
    }

    /**
     * getRule51Group
     *
     * @return
     *  @author David Bigelow
     */
    public final Rule51Group getRule51Group() {
        return m_r51Grp;
    }

    //
    //  Force mode switced for visibility
    //

    /**
     *  Sets the forceMode attribute of the EntityGroup object
     *
     * @param  _b  The new forceMode value
     */
    protected void setForceMode(boolean _b) {
        m_bForceMode = _b;
    }

    /**
     *  Sets the forceDisplay attribute of the EntityGroup object
     *
     * @param  _b  The new forceDisplay value
     */
    protected void setForceDisplay(boolean _b) {
        m_bForceDisplay = _b;
    }

    /**
     *  Gets the forceMode attribute of the EntityGroup object
     *
     * @return    The forceMode value
     */
    protected boolean isForceMode() {
        return m_bForceMode;
    }

    /**
     *  Gets the forceDisplay attribute of the EntityGroup object
     *
     * @return    The forceDisplay value
     */
    protected boolean isForceDisplay() {
        return m_bForceDisplay;
    }

    /**
     *  Gets the noAttributesLike attribute of the EntityGroup object
     *
     * @return    The noAttributesLike value
     */
    protected boolean isNoAttributesLike() {
        return isNoAttributesLike(getPurpose());
    }

    /**
     *  Gets the navigateLike attribute of the EntityGroup object
     *
     * @return    The navigateLike value
     */
    protected boolean isNavigateLike() {
        return isNavigateLike(getPurpose());
    }

    /**
     *  Gets the editLike attribute of the EntityGroup object
     *
     * @return    The editLike value
     */
    protected boolean isEditLike() {
        return isEditLike(getPurpose());
    }

    /**
     *  Gets the isSearchLike attribute of the EntityGroup object
     *
     * @return    The isSearchLike value
     */
    protected boolean isSearchLike() {
        return getPurpose().equals("Search");
    }

    /**
     * isABRStatusLike
     *
     * @return
     *  @author David Bigelow
     */
    protected boolean isABRStatusLike() {
        return getPurpose().equals("ABRStatus");
    }

    /**
     *  Gets the extractLike attribute of the EntityGroup object
     *
     * @return    The extractLike value
     */
    protected boolean isExtractLike() {
        return isExtractLike(getPurpose());
    }

    /**
     * We might need to figure this out without an instance (e.g. cache mgmt.)
     *
     * @param  _strPurpose  Description of the Parameter
     * @return              The editLike value
     */
    protected static boolean isEditLike(String _strPurpose) {
        return _strPurpose.equals("Edit") || _strPurpose.equals("Create") || _strPurpose.equals("NoCache") || _strPurpose.equals("Extract");
    }

    /**
     * We might need to figure this out without an instance (e.g. cache mgmt.)
     *
     * @param  _strPurpose  Description of the Parameter
     * @return              The noAttributesLike value
     */
    protected static boolean isNoAttributesLike(String _strPurpose) {
        return _strPurpose.equals("NoAtts");
    }

    /**
     * We might need to figure this out without an instance (e.g. cache mgmt.)
     *
     * @param  _strPurpose  Description of the Parameter
     * @return              The navigateLike value
     */
    protected static boolean isNavigateLike(String _strPurpose) {
        return _strPurpose.equals("Navigate") || _strPurpose.equals("Picklist") || _strPurpose.equals("Cart");
    }

    /**
     * We might need to figure this out without an instance (e.g. cache mgmt.)
     *
     * @param  _strPurpose  Description of the Parameter
     * @return              The extractLike value
     */
    protected static boolean isExtractLike(String _strPurpose) {
        return _strPurpose.equals("Extract");
    }

    /*
     *  This clones the meta structure only!
     */
    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    /*protected EntityGroup cloneMetaStructure() {

        //Serialization approach...

        EntityGroup clone = null;
        byte[] byteArray = null;
        ByteArrayOutputStream BAout = null;
        ByteArrayInputStream BAin = null;
        ObjectOutputStream Oout = null;
        ObjectInputStream Oin = null;

        // First close all the possible serialization leaks
        EANFoundation ef = getParent();
        EANList elst = getData();
        setParent(null);
        resetData();

        try {
            //put object into stream
            try {
                BAout = new ByteArrayOutputStream();
                Oout = new ObjectOutputStream(BAout);
                Oout.writeObject(this);
                Oout.flush();
                Oout.reset();
            }
            finally {
            	if(Oout!=null){
            		Oout.close();
            	}
            	if(BAout!=null){
            		BAout.close();
            	}
            }
            byteArray = BAout.toByteArray();
            BAout.close();

            //now turn around and pull object out of stream
            try {
                BAin = new ByteArrayInputStream(byteArray);
                Oin = new ObjectInputStream(BAin);
                clone = (EntityGroup) Oin.readObject();
            }
            finally {
            	if(Oin!=null){
            		Oin.close();
            	}
            	if(BAin!=null){
            		BAin.close();
            	}
            }
            byteArray = null;
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        finally {
            setParent(ef);
            setData(elst);
        }

        return clone;
    }*/

    /**
     * clone the meta for this group by writing it to the stream and getting bytes
     * @return
     */
    private byte[] cloneMetaStructureAsBytes() {

        //Serialization approach...

        byte[] byteArray = null;
        ByteArrayOutputStream BAout = null;
        ObjectOutputStream Oout = null;

        // First close all the possible serialization leaks
        EANFoundation ef = getParent();
        EANList elst = getData();
        setParent(null);
        resetData();

        try {
            //put object into stream
            try {
                BAout = new ByteArrayOutputStream();
                Oout = new ObjectOutputStream(BAout);
                Oout.writeObject(this);
                Oout.flush();
                Oout.reset();
            }
            finally {
            	if(Oout!=null){
            		Oout.close();
            	}
            	if(BAout!=null){
            		BAout.close();
            	}
            }
            byteArray = BAout.toByteArray();
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        finally {
            setParent(ef);
            setData(elst);
        }

        return byteArray;
    }
 
    /**
     * insert/update cache record
     *
     * @param  _db  Description of the Parameter
     */
    protected void putCache(Database _db) {
        putCache(_db, false);
    }

    /**
     * expire cache record
     *
     * @param  _db  Description of the Parameter
     */
    public void expireCache(Database _db) {
        putCache(_db, true);
    }

    /**
     * This will return the value for the attributecode column in the blob table
     *
     * @return    The cacheKey value
     */
    public String getCacheKey() {

        String strRoleCode = getProfile().getRoleCode();

        // Substitute any override information out of here
        EntityList el = getEntityList();
        //String strAIKey = "none";
        if (el != null) {
            EANActionItem ai = el.getParentActionItem();
            if (ai instanceof ExtractActionItem) {
                ExtractActionItem eai = (ExtractActionItem) ai;
                if (eai.hasRoleCodeOverride()) {
                    strRoleCode = eai.getRoleCodeOverride();
                }
            }

           /* if (ai == null) {
                D.ebug(D.EBUG_SPEW, "TRACE EntityGroup.getCacheKey is null!");
            }
            else {
                D.ebug(D.EBUG_SPEW, "TRACE EntityGroup.getCacheKey is NOT null!");
          //      strAIKey = ai.getKey();
            }*/
            if (ai != null && ai.isAllNLS()) {
                strRoleCode = strRoleCode + "allNLS";
            }
        }

        return getCacheKey(strRoleCode, getPurpose());
    }

    /**
     * GAB - 07/02/03 - are ensuring that the generation/look up of all cache keys pass through this method....
     */
    private static String getCacheKey(String _strRoleCode, String _strPurpose) {
        String strEndTag = "Navigate";
        if (isExtractLike(_strPurpose)) {
            strEndTag = "Extract";
        }
        else if (isEditLike(_strPurpose)) {
            strEndTag = "Edit";
        }
        else if (_strPurpose.equals("Search")) {
            strEndTag = "Search";
        }
        else if (_strPurpose.equals("ABRStatus")) {
            strEndTag = "ABRStatus";
        }
        else if (isNoAttributesLike(_strPurpose)) {
            strEndTag = "NoAtts";
        }
        return "EG" + _strRoleCode + strEndTag;
    }

    /**
     * getCacheKey
     *
     * @param _prof
     * @param _strPurpose
     * @return
     *  @author David Bigelow
     */
    public static String getCacheKey(Profile _prof, String _strPurpose) {
        return getCacheKey(_prof.getRoleCode(), _strPurpose);
    }

    private void putCache(Database _db, boolean bExpire) {

        DatePackage dp = null;
        String strKey = getCacheKey();
        byte[] ba = null;

        //if "NoCache" is specified -> don't update this guy
        if (getPurpose().equals("NoCache")) {
            return;
        }

        boolean success = false;
        try {
        	dp = _db.getDates();

        	//update OR expire
        	if (bExpire) {
        		_db.deactivateBlob(getProfile(), getKey(), 0, strKey, getNLSID());
        	}
        	else {
        		/*only write it once when cloning, use bytes from there*/
        		ba = cloneMetaStructureAsBytes();

        		_db.callGBL9974(new ReturnStatus( -1), _db.getInstanceName(), "pc");
        		_db.freeStatement();
        		_db.isPending();
    
        		_db.putBlob(getProfile(), getKey(), 0, strKey, "CACHE", dp.getNow(), dp.getForever(), ba, getNLSID());

        	}
        	success = true;
        }
        catch (Exception x) {
            _db.debug(D.EBUG_ERR, "EntityGroup.putCache "+getKey()+" ERROR "+x.getMessage());
            x.printStackTrace();
        }finally{
        	try{
        		if(success){
        			_db.commit();
        		}else{
        			_db.rollback();
        		}
        	}catch(Exception e){}
        	_db.freeStatement();
            _db.isPending();
        }
    }

    /**
     *  Gets the cache attribute of the EntityGroup object
     *
     * @param  _db  Description of the Parameter
     * @return      The cache value
     */
    private EntityGroup getCache(Database _db) {

        String strKey = getCacheKey();
        byte[] ba = null;
        ByteArrayInputStream baisObject = null;
        ObjectInputStream oisObject = null;
        BufferedInputStream bis = null;
        EntityGroup egShell = null;

        try {

            // go get the blob
            Blob b = _db.getBlobNow(getProfile(), getKey(), 0, strKey, 0);
    
            _db.commit();
            _db.freeStatement();
            _db.isPending();
            ba = b.getBAAttributeValue();
            if (ba == null || ba.length == 0) {
                _db.debug(D.EBUG_WARN, "EntityGroup.getCache()  ** No Cache Object Found for "+strKey+" **");
                return null;
            }

            baisObject = new ByteArrayInputStream(ba);
            bis = new BufferedInputStream(baisObject, 1000000);
            oisObject = new ObjectInputStream(bis);
            egShell = (EntityGroup) oisObject.readObject();
    
            return egShell;
        }
        catch (Exception x) {
            _db.debug(D.EBUG_ERR, "EntityGroup.getCache() "+strKey+" ERROR "+x.getMessage());
            x.printStackTrace();
        }
        finally {
        	if(oisObject!=null){
        		try{
        			oisObject.close();
        		} catch (java.io.IOException x) {
        			_db.debug(D.EBUG_DETAIL, "EntityGroup.getCache(): "+strKey+" ERROR failure closing ObjectInputStream "+x);
        		} 
        		oisObject=null;
        	}
           	if(bis!=null){
        		try{
        			bis.close();
        		} catch (java.io.IOException x) {
        			_db.debug(D.EBUG_DETAIL, "EntityGroup.getCache(): "+strKey+" ERROR failure closing BufferedInputStream "+x);
        		} 
        		bis=null;
        	}
        	if(baisObject!=null){
        		try{
        			baisObject.close();
        		} catch (java.io.IOException x) {
        			_db.debug(D.EBUG_DETAIL, "EntityGroup.getCache(): "+strKey+" ERROR failure closing ByteArrayInputStream "+x);
        		} 
        		baisObject=null;
        	}
            ba = null;
            _db.freeStatement();
            _db.isPending();

        }

        return null;
    }

    /**
     *  Description of the Method
     */
    private void ownIt() {

        // Reset Group's
        for (int ii = 0; ii < getResetGroupCount(); ii++) {
            ResetGroup rg = getResetGroup(ii);
            rg.setParent(this);
        }

        // Required Group's
        for (int ii = 0; ii < getRequiredGroupCount(); ii++) {
            RequiredGroup rg = getRequiredGroup(ii);
            rg.setParent(this);
        }

        // Restriction Group's
        for (int ii = 0; ii < getRestrictionGroupCount(); ii++) {
            RestrictionGroup rg = getRestrictionGroup(ii);
            rg.setParent(this);
        }

        // Restriction Group's
        for (int ii = 0; ii < getClassificationGroupCount(); ii++) {
            ClassificationGroup cg = getClassificationGroup(ii);
            cg.setParent(this);
        }

        // Meta Attribute
        for (int ii = 0; ii < getMetaAttributeCount(); ii++) {
            EANMetaAttribute att = getMetaAttribute(ii);
            att.setParent(this);
        }

        // UniqueAttributeGroup
        //
        // again.. we do not want this
        // this guy also gets sent up
        // we do not want to send up any parent entitygroup
        // Which has all the entities in it..
        //
        if (getUniqueAttributeGroup() != null) {
            Enumeration eNum = getUniqueAttributeGroup().keys();
            while (eNum.hasMoreElements()) {
				UniqueAttributeGroup ug = (UniqueAttributeGroup) getUniqueAttributeGroup().get(eNum.nextElement());
                ug.setParent(null);
                ug.setProfile(getProfile()); // must use the current profile, not the one from the cache RQ1112073342
            }
        }

        // Rule51Group
        if (getRule51Group() != null) {
            getRule51Group().setParent(null);
            getRule51Group().setProfile(getProfile()); // must use the current profile, not the one from the cache RQ1112073342
        }

        //DependentAttributeValue
        // we do not need a parent here.
        // this gets sent back through RMI...
        //
        if (getDependentAttributeValue() != null) {
            Enumeration eNum = getDependentAttributeValue().keys();
            while (eNum.hasMoreElements()) {
				DependentAttributeValue dg = (DependentAttributeValue) getDependentAttributeValue().get(eNum.nextElement());
                dg.setParent(null);
                dg.setProfile(getProfile()); // must use the current profile, not the one from the cache RQ1112073342
                // this was a problem when search was launched to check attributes, it used the profile from the
                // cache, the cache had Manager profile for iseries and the code was looking for eserver so search failed
            }
        }

        // LocalRuleGroup
        for (int ii = 0; ii < getLocalRuleGroupCount(); ii++) {
            getLocalRuleGroup(ii).setParent(this);
        }

    }

    /**
     *  Description of the Method
     */
    protected void checkDataIntegrity() {

        int ihc = hashCode();

        for (int ii = 0; ii < getEntityItemCount(); ii++) {
            EntityItem ei = getEntityItem(ii);
            EANFoundation eanf = ei.getParent();
            if (ihc != eanf.hashCode()) {
                ei.setParent(this);
            }
            ei.checkDataIntegrity();
        }
    }

    /*
     *  used by the RowSelectableTable to see if any changes are pending
     */
    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public boolean hasChanges() {
        for (int ii = 0; ii < getEntityItemCount(); ii++) {
            EntityItem ei = getEntityItem(ii);
            if (ei.hasChanges()){// need this to get VEEdit checked
                return true;
            }
        }

        return false;
    }

    /**
     * rearrange the MetaAttributes so that it is sorted alphabetically by the specified type
     *
     * @param  _strSortType  Description of the Parameter
     * @param  _bAscending   Description of the Parameter
     * @concurrency $none
     */
    public synchronized void sortMetaAttributes(String _strSortType, boolean _bAscending) {
        EANMetaAttribute[] aMa = new EANMetaAttribute[getMetaAttributeCount()];
        EANComparator ec = new EANComparator(_bAscending);

        for (int i = 0; i < getMetaAttributeCount(); i++) {
            EANMetaAttribute ma = getMetaAttribute(i);
            ma.setSortType(_strSortType);
            aMa[i] = ma;
        }
        Arrays.sort(aMa, ec);
        resetMeta();
        for (int i = 0; i < aMa.length; i++) {
            EANMetaAttribute ma = aMa[i];
            putMeta(ma);
        }
    }

    /**
     * get the sort key
     *
     * @return    Description of the Return Value
     */
    public String toCompareString() {
        if (getSortType().equals(SORT_BY_LONGDESC)) {
            return getLongDescription();
        }
        if (getSortType().equals(SORT_BY_SHORTDESC)) {
            return getShortDescription();
        }
        if (getSortType().equals(SORT_BY_ENTITYTYPE)) {
            return getEntityType();
        }
        if (getSortType().equals(SORT_BY_ENTITYCLASS)) {
            //order by EntityClass + EntityType
            if (isRelator()) {
                return "Relator";
            }
            if (isAssoc()) {
                return "Assoc";
            }
            else {
                return "Entity";
            }
        }
        if (getSortType().equals(SORT_BY_ENTITY1TYPE)) {
            if (isRelator()) {
                return getEntity1Type();
            }
            return "--";
        }
        if (getSortType().equals(SORT_BY_ENTITY2TYPE)) {
            if (isRelator()) {
                return getEntity2Type();
            }
            return "--";
        }
        //this shouldn't occur
        return toString();
    }

    ///////
    /*
     *  these replaced by sortInfo methods below...
     *  public void setSortType(int _i) {
     *  m_iSortType = _i;
     *  return;
     *  }
     *  public int getSortType() {
     *  return m_iSortType;
     *  }
     */
    ///////

    /**
     *  Sets the displayableForFilter attribute of the EntityGroup object
     *
     * @param  _b  The new displayableForFilter value
     */
    public void setDisplayableForFilter(boolean _b) {
        m_bDisplayableForFilter = _b;
    }

    /**
     *  Gets the displayableForFilter attribute of the EntityGroup object
     *
     * @return    The displayableForFilter value
     */
    public boolean isDisplayableForFilter() {
        return m_bDisplayableForFilter;
    }

    ////////
    // SORT FILTER INFO
    ////////

    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public String dumpSFInfo() {
        return getSFInfo().dump();
    }

    /**
     * @param  _strFilterType  The new filterType value
     */
    //protected SortFilterInfo getSFInfo() {
    //    return m_SFInfo;
    //}

    /**
     * set FilterType on MetaAtts
     *
     * @param  _strFilterType  The new filterType value
     */
    public void setFilterType(String _strFilterType) {
        getSFInfo().setFilterType(_strFilterType);
    }

    /**
     *  Gets the filterType attribute of the EntityGroup object
     *
     * @return    The filterType value
     */
    public String getFilterType() {
        return getSFInfo().getFilterType();
    }

    /**
     * set Filter on MetaAtts
     *
     * @param  _strFilter  The new filter value
     */
    public void setFilter(String _strFilter) {
        getSFInfo().setFilter(_strFilter);
    }

    /**
     *  Gets the filter attribute of the EntityGroup object
     *
     * @return    The filter value
     */
    public String getFilter() {
        return getSFInfo().getFilter();
    }

    /**
     * reset Filter on MetaAtts
     */
    public void resetFilter() {
        getSFInfo().resetFilter();
        resetDisplayableMetaAttributes();
    }

    /**
     * set the sort type
     *
     * @param  _s  The new sortType value
     */
    public void setSortType(String _s) {
        getSFInfo().setSortType(_s);
    }

    /**
     *  Gets the sortType attribute of the EntityGroup object
     *
     * @return    The sortType value
     */
    public String getSortType() {
        return getSFInfo().getSortType();
    }

    /**
     *  Sets the compareField attribute of the EntityGroup object
     *
     * @param  _s  The new compareField value
     */
    public void setCompareField(String _s) {
        setSortType(_s);
    }

    /**
     *  Gets the compareField attribute of the EntityGroup object
     *
     * @return    The compareField value
     */
    public String getCompareField() {
        return getSortType();
    }

    /**
     * set the Ascending Flag
     * @param  _b  The new ascending value
     */
    public void setAscending(boolean _b) {
        getSFInfo().setAscending(_b);
    }

    /**
     *  Gets the ascending attribute of the EntityGroup object
     *
     * @return    The ascending value
     */
    public boolean isAscending() {
        return getSFInfo().isAscending();
    }

    /**
     *  Description of the Method
     */
    private void resetDisplayableMetaAttributes() {
        for (int i = 0; i < getMetaAttributeCount(); i++) {
            getMetaAttribute(i).setDisplayableForFilter(true);
        }
    }

    /**
     * the way we want to do this is:
     *   - retain last sort by until explicitely set
     *   - if the last sort was the same type: if asc -> do desc, and visa-versa
     *   - retain last filter/filter type
     *   - if m_strFilter != null -> apply filter
     *
     * @param  _bUseWildcardsForfilter  Description of the Parameter
     * @concurrency $none
     */
    public synchronized void performSortFilter(boolean _bUseWildcardsForfilter) {
        performSort();
        performFilter(_bUseWildcardsForfilter);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////   UPDATE METHODS   ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Update the changed EntityGroups to the PDH
     *
     * @param  _bDeepUpdate                    if true go all the way down to MetaFlags (same as updatEPdhMeta(db))-- else stop at MetaAttribute level
     * @param  _db                             Description of the Parameter
     * @return                                 Description of the Return Value
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public boolean updatePdhMeta(Database _db, boolean _bDeepUpdate) throws SQLException, MiddlewareException, MiddlewareRequestException {
        return updatePdhMeta(_db, false, _bDeepUpdate);
    }

    /**
     * Update the changed EntityGroups to the PDH
     *
     * @param  _db                             Description of the Parameter
     * @return                                 Description of the Return Value
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public boolean updatePdhMeta(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        return updatePdhMeta(_db, false, true);
    }

    /**
     * Expire the changed EntityGroups in the PDH
     *
     * @param  _db                             Description of the Parameter
     * @return                                 Description of the Return Value
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public boolean expirePdhMeta(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        boolean bChanged = updatePdhMeta(_db, true, true);
        //now make sure this guy is removed from its parent MetaEntityList if it has one...
        MetaEntityList oMel = (MetaEntityList)this.getParent();
        if (oMel == null) {
            return bChanged;
        }
        //use removeData as opposed to removeEntityGroup so that we dont add this guy back into the expire list!
        oMel.removeData(this);
        return bChanged;
    }

    /**
     * toggle expire
     *
     * @param  _db                             Description of the Parameter
     * @param  _bIsExpire                      Description of the Parameter
     * @param  _bDeepUpdate                    Description of the Parameter
     * @return                                 Description of the Return Value
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    private boolean updatePdhMeta(Database _db, boolean _bIsExpire, boolean _bDeepUpdate) throws SQLException, MiddlewareException,
        MiddlewareRequestException {
        boolean bChanged = false;
        //Stopwatch sw1 = new Stopwatch();sw1.start();
        //Stopwatch sw3 = new Stopwatch();sw3.start();
        EntityGroup oEg_db = new EntityGroup( (EANMetaFoundation) getParent(), _db, getProfile(), getEntityType(), "Edit");

        //1) MetaEntity table
        EANList oEl = getMetaEntityRowsForUpdate(_db, oEg_db, _bIsExpire);
        for (int i = 0; i < oEl.size(); i++) {
            MetaEntityRow oMeRow = (MetaEntityRow) oEl.getAt(i);
            oMeRow.updatePdh(_db);
            bChanged = true;
        }
        //2) MetaDescription table
        oEl = getMetaDescriptionRowsForUpdate(_db, oEg_db, _bIsExpire);
        for (int i = 0; i < oEl.size(); i++) {
            MetaDescriptionRow oMdRow = (MetaDescriptionRow) oEl.getAt(i);
            oMdRow.updatePdh(_db);
            bChanged = true;
        }
        //3) MetaLinkAttr table
        oEl = getMetaLinkAttrRowsForUpdate(_db, oEg_db, _bIsExpire);
        for (int i = 0; i < oEl.size(); i++) {
            MetaLinkAttrRow oMlaRow = (MetaLinkAttrRow) oEl.getAt(i);
            oMlaRow.updatePdh(_db);
            bChanged = true;
        }

        //Now expire/update MetaAttributes
        //expire
        if (_bIsExpire) {
            //we cant go access these things by index, because we are removing them....
            EANList eListDetaches = new EANList();
            for (int i = 0; i < oEg_db.getMetaAttributeCount(); i++) {
                EANMetaAttribute oEmAtt_db = oEg_db.getMetaAttribute(i);
                eListDetaches.put(oEmAtt_db);
            }
            for (int i = 0; i < eListDetaches.size(); i++) {
                EANMetaAttribute oEmAtt_db = (EANMetaAttribute) eListDetaches.getAt(i);
                bChanged = (oEmAtt_db.detachPdhMeta(_db) ? true : bChanged);
            }
        }
        else if (isEditLike()) {
            //update
            //1) get atts that are in db, !in current -> expire these
            //we cant go access atts by index, because we are removing some of them....
            EANList eListAtts = new EANList();
            for (int i = 0; i < oEg_db.getMetaAttributeCount(); i++) {
                EANMetaAttribute oEmAtt_db = oEg_db.getMetaAttribute(i);
                eListAtts.put(oEmAtt_db);
            }
            for (int i = 0; i < eListAtts.size(); i++) {
                EANMetaAttribute oEmAtt_db = (EANMetaAttribute) eListAtts.getAt(i);
                boolean bFound = false;
                for (int j = 0; j < this.getMetaAttributeCount(); j++) {
                    EANMetaAttribute oEmAtt = this.getMetaAttribute(j);
                    if (oEmAtt.getAttributeCode().equals(oEmAtt_db.getAttributeCode())) {
                        bFound = true;
                        j = this.getMetaAttributeCount();
                        //break
                    }
                }
                if (!bFound) {
                    bChanged = (oEmAtt_db.detachPdhMeta(_db) ? true : bChanged);
                }
            }
            //2) update all other atts that are currently in the EntityGroup
            for (int i = 0; i < this.getMetaAttributeCount(); i++) {
                EANMetaAttribute oEmAtt = this.getMetaAttribute(i);
                //Stopwatch sw2 = new Stopwatch();sw2.start();
                bChanged = (oEmAtt.updatePdhMeta(_db, _bDeepUpdate) ? true : bChanged);
                //_db.debug(D.EBUG_SPEW,"timing EntityGroup sw2 (" + oEmAtt.getAttributeCode() + "):" + sw2.finish());
            }

            // RequiredGroups
            if (!isExtractLike()) {
                if (_bIsExpire) {
                    for (int i = 0; i < oEg_db.getRequiredGroupCount(); i++) {
                        bChanged = (oEg_db.getRequiredGroup(i).expirePdhMeta(_db) ? true : bChanged);
                    }
                }
                else {
                    //expire those in db, !in current
                    for (int i = 0; i < oEg_db.getRequiredGroupCount(); i++) {
                        if (this.getRequiredGroup(oEg_db.getRequiredGroup(i).getKey()) == null) {
                            bChanged = (oEg_db.getRequiredGroup(i).expirePdhMeta(_db) ? true : bChanged);
                        }
                    }
                    //update all thos in current -> let RequiredGroup sort out changes/adds....
                    for (int i = 0; i < this.getRequiredGroupCount(); i++) {
                        bChanged = (this.getRequiredGroup(i).updatePdhMeta(_db) ? true : bChanged);
                    }
                }
            }

            //ResetGroups
            if (!isExtractLike()) {
                if (_bIsExpire) {
                    for (int i = 0; i < oEg_db.getResetGroupCount(); i++) {
                        bChanged = (oEg_db.getResetGroup(i).expirePdhMeta(_db) ? true : bChanged);
                    }
                }
                else {
                    //expire those in db, !in current
                    for (int i = 0; i < oEg_db.getResetGroupCount(); i++) {
                        if (this.getResetGroup(oEg_db.getResetGroup(i).getKey()) == null) {
                            bChanged = (oEg_db.getResetGroup(i).expirePdhMeta(_db) ? true : bChanged);
                        }
                    }
                    //update all thos in current -> let ResetGroup sort out changes/adds....
                    for (int i = 0; i < this.getResetGroupCount(); i++) {
                        bChanged = (this.getResetGroup(i).updatePdhMeta(_db) ? true : bChanged);
                    }
                }
            }

            //RestrictionGroups
            if (!isExtractLike()) {
                if (_bIsExpire) {
                    for (int i = 0; i < oEg_db.getRestrictionGroupCount(); i++) {
                        bChanged = (oEg_db.getRestrictionGroup(i).expirePdhMeta(_db) ? true : bChanged);
                    }
                }
                else {
                    //expire those in db, !in current
                    for (int i = 0; i < oEg_db.getRestrictionGroupCount(); i++) {
                        if (this.getRestrictionGroup(oEg_db.getRestrictionGroup(i).getKey()) == null) {
                            bChanged = (oEg_db.getRestrictionGroup(i).expirePdhMeta(_db) ? true : bChanged);
                        }
                    }
                    //update all thos in current -> let RestrictionGroup sort out changes/adds....
                    for (int i = 0; i < this.getRestrictionGroupCount(); i++) {
                        bChanged = (this.getRestrictionGroup(i).updatePdhMeta(_db) ? true : bChanged);
                    }
                }
            }
        }
        ///

        //update Classification Groups...
        if (_bIsExpire) {
            for (int i = 0; i < oEg_db.getClassificationGroupCount(); i++) {
                oEg_db.getClassificationGroup(i).expirePdhMeta(_db);
                bChanged = true;
            }
        }
        else if (_bDeepUpdate && isEditLike()) {
            // 1) find those in db -> !in current and expire them
            for (int i = 0; i < oEg_db.getClassificationGroupCount(); i++) {
                String strGroupKey_db = oEg_db.getClassificationGroup(i).getKey();
                boolean bFound = false;
                for (int j = 0; j < this.getClassificationGroupCount(); j++) {
                    String strGroupKey_this = this.getClassificationGroup(j).getKey();
                    if (strGroupKey_db.equals(strGroupKey_this)) {
                        bFound = true;
                        j = this.getClassificationGroupCount();
                        continue;
                    }
                }
                if (!bFound) {
                    bChanged = (oEg_db.getClassificationGroup(i).expirePdhMeta(_db) ? true : bChanged);
                }
            }
            // 2) update the rest...
            for (int i = 0; i < this.getClassificationGroupCount(); i++) {
                bChanged = (this.getClassificationGroup(i).updatePdhMeta(_db) ? true : bChanged);
            }
        }

        //_db.debug(D.EBUG_SPEW,"timing EntityGroup sw1:" + sw1.finish());
        //now update the cache!
        if (bChanged) {
            new MetaCacheManager(getProfile()).expireEGCacheAllRolesAllNls(_db, this.getEntityType());
            //_db.debug(D.EBUG_SPEW,"timing EntityGroup sw3:" + sw3.finish());
            putCache(_db);
        }
        return bChanged;
    }

    /**
     * Get the MetaLinkAttrRows for any changes relevent to MetaLinkAttr table
     * 1) if new Entity -> get one MLA row
     * 2) else if cap has changed -> get one changed row
     *
     * @param  _db                             Description of the Parameter
     * @param  _oEg_db                         Description of the Parameter
     * @param  _bIsExpire                      Description of the Parameter
     * @return                                 The metaLinkAttrRowsForUpdate value
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    private EANList getMetaLinkAttrRowsForUpdate(Database _db, EntityGroup _oEg_db, boolean _bIsExpire) throws SQLException, MiddlewareException,
        MiddlewareRequestException {
        EANList eList = new EANList();
        String strEntType = this.getEntityType();
        String strEntClass = "Entity";
        String strLinkType = "Role/" + strEntClass;
        String strRoleCode = getProfile().getRoleCode();
        String strLinkCode = "Capability";
        String strNow = _db.getDates().getNow();
        String strForever = _db.getDates().getForever();
        String strEnterprise = getProfile().getEnterprise();
        String strValOn = getProfile().getValOn();
        String strEffOn = getProfile().getEffOn();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        boolean bIsNewEntity = MetaEntityList.isNewEntityType(_db, getProfile(), strEntType);
        String strCap = "R";
        String strCap_db = "R";

        if (isWritable()) {
            strCap = "W";
        }
        if (isCreatable()) {
            strCap = "C";
        }
        if (_oEg_db.isWritable()) {
            strCap_db = "W";
        }
        if (_oEg_db.isCreatable()) {
            strCap_db = "C";
        }

        //UPDATE
        if (!_bIsExpire) {
            //1, 2)
            //default new entities to "C"
            if (bIsNewEntity) {
                strCap = "C";
            }
            if (bIsNewEntity || !strCap.equals(strCap_db)) {
                eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strRoleCode, strEntType, strLinkCode, strCap, strNow, strForever, strNow,
                                              strForever, 2));
                //we must add for UPDATEAL...
                if (!strRoleCode.equals("UPDATEAL")) {
                    eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, "UPDATEAL", strEntType, strLinkCode, "C", strNow, strForever, strNow,
                                                  strForever, 2));
                }
            }

            //isRelator()
            //cases: 1)new relator: add 1 row
            //       2)change to relator: add 1 row
            //       3)still relator, but change e1,e2: expire 1 row, add 1 row
            //       4)was relator, now isnt: remove 1 row
            if (isRelator()) {
                if (bIsNewEntity) {
                    //1)
                    //_db.debug(D.EBUG_SPEW,"(UPDATE)1");
                    eList.put(new MetaLinkAttrRow(getProfile(), getEntityType(), getEntity1Type(), getEntity2Type(), "Relator", "L", strNow,
                                                  strForever, strNow, strForever, 2));
                }
                //2)
                else if (!_oEg_db.isRelator()) {
                    //_db.debug(D.EBUG_SPEW,"(UPDATE)2");
                    eList.put(new MetaLinkAttrRow(getProfile(), getEntityType(), getEntity1Type(), getEntity2Type(), "Relator", "L", strNow,
                                                  strForever, strNow, strForever, 2));
                    //3)
                }
                else {
                    if (! (_oEg_db.getEntity1Type().equals(getEntity1Type())) || ! (_oEg_db.getEntity2Type().equals(getEntity2Type()))) {
                        //_db.debug(D.EBUG_SPEW,"(UPDATE)3");
                        //expire db
                        eList.put(new MetaLinkAttrRow(getProfile(), _oEg_db.getEntityType(), _oEg_db.getEntity1Type(), _oEg_db.getEntity2Type(),
                            "Relator", "L", strNow, strNow, strNow, strNow, 2));
                        //update new
                        eList.put(new MetaLinkAttrRow(getProfile(), getEntityType(), getEntity1Type(), getEntity2Type(), "Relator", "L", strNow,
                            strForever, strNow, strForever, 2));
                    }
                }
            }
            else {
                //4)
                if (!bIsNewEntity) {
                    if (!isRelator() && _oEg_db.isRelator()) {
                        //_db.debug(D.EBUG_SPEW,"(UPDATE)4");
                        eList.put(new MetaLinkAttrRow(getProfile(), _oEg_db.getEntityType(), _oEg_db.getEntity1Type(), _oEg_db.getEntity2Type(),
                            "Relator", "L", strNow, strNow, strNow, strNow, 2));
                    }
                }
            }

        }
        //EXPIRE
        else if (!bIsNewEntity) {
            //get ALL roleCodes that maintain this Entity (including the current profile's roleCode)
            try {
                rs = _db.callGBL7507(new ReturnStatus( -1), strEnterprise, strLinkType, strEntType, strLinkCode, strValOn, strEffOn);
                rdrs = new ReturnDataResultSet(rs);
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strRoleCodeExp = rdrs.getColumn(row, 0);
                String strCapExp = rdrs.getColumn(row, 1);
                eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strRoleCodeExp, strEntType, strLinkCode, strCapExp, strNow, strNow, strNow,
                                              strNow, 2));
            }
            if (isRelator()) {
                eList.put(new MetaLinkAttrRow(getProfile(), getEntityType(), getEntity1Type(), getEntity2Type(), "Relator", "L", strNow, strNow,
                                              strNow, strNow, 2));
            }
        }
        return eList;
    }

    /**
     * Get the MetaDescriptionRows for any changes relavent to MetaDescription table.
     * 1) if new entity -> get one row for update.
     * 2) if descriptions have changed -> get row w/ new descriptions (gbl2909 will take care of the rest)
     * 3) entclass has changed
     *
     * @param  _db                             Description of the Parameter
     * @param  _oEg_db                         Description of the Parameter
     * @param  _bIsExpire                      Description of the Parameter
     * @return                                 The metaDescriptionRowsForUpdate value
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    private EANList getMetaDescriptionRowsForUpdate(Database _db, EntityGroup _oEg_db, boolean _bIsExpire) throws SQLException, MiddlewareException,
        MiddlewareRequestException {
        EANList eList = new EANList();
        String strEntType = this.getEntityType();
        String strShortDesc = this.getShortDescription();
        String strLongDesc = this.getLongDescription();
        String strNow = _db.getDates().getNow();
        String strForever = _db.getDates().getForever();
        String strEnterprise = getProfile().getEnterprise();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        String strEntClass = "Entity";
        String strEntClass_db = "Entity";
        String strShortDesc_db = _oEg_db.getShortDescription();
        String strLongDesc_db = _oEg_db.getLongDescription();

        boolean bIsNewEntity = MetaEntityList.isNewEntityType(_db, getProfile(), strEntType);

        //UPDATE
        if (!_bIsExpire) {
            //1) new Entity
            if (bIsNewEntity) {
                MetaDescriptionRow oMdRow = new MetaDescriptionRow(getProfile(), strEntType, strEntClass, strShortDesc, strLongDesc,
                    getProfile().getReadLanguage().getNLSID(), strNow, strForever, strNow, strForever, 2);
                eList.put(oMdRow);
            }
            else {
                // update the description and/or entityClass if required...

                //2) update descriptions if necessary
                if (!strShortDesc.equals(strShortDesc_db) || !strLongDesc.equals(strLongDesc_db)) {
                    eList.put(new MetaDescriptionRow(getProfile(), strEntType, strEntClass, strShortDesc, strLongDesc,
                        getProfile().getReadLanguage().getNLSID(), strNow, strForever, strNow, strForever, 2));
                }
                //
            }
        }
        //EXPIRE
        else if (!bIsNewEntity) {
            //if it isnt in database -> we dont need to remove it from database (duh..)
            //we must expire ALL nlsId's!
            try {
                rs = _db.callGBL7511(new ReturnStatus( -1), strEnterprise, strEntType, strEntClass_db);
                rdrs = new ReturnDataResultSet(rs);
            }
            finally {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                int iNLSID = rdrs.getColumnInt(row, 0);
                String strShortDesc_nls = rdrs.getColumn(row, 1);
                String strLongDesc_nls = rdrs.getColumn(row, 2);
                _db.debug(D.EBUG_SPEW, "gbl7511 answers: " + iNLSID + ":" + strShortDesc_nls + ":" + strLongDesc_nls);
                _db.debug(D.EBUG_SPEW,
                          "MetaDescriptionRow: [" + strEntType + "," + strEntClass_db + "," + strShortDesc_nls + "," + strLongDesc_nls + "," + iNLSID +
                          "," + strNow + "(*4),2]");
                eList.put(new MetaDescriptionRow(getProfile(), strEntType, strEntClass_db, strShortDesc_nls, strLongDesc_nls, iNLSID, strNow, strNow,
                                                 strNow, strNow, 2));
            }
        }
        return eList;
    }

    /**
     * Get the MetaEntityRows for any changes relavent to MetaEntity table.
     * 1) if new entity -> get one row for update.
     * 2) else if NOT new entity AND entityClass has changed -> get old row to expire, new row to update.
     *
     * @param  _db                             Description of the Parameter
     * @param  _oEg_db                         Description of the Parameter
     * @param  _bIsExpire                      Description of the Parameter
     * @return                                 The metaEntityRowsForUpdate value
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    private EANList getMetaEntityRowsForUpdate(Database _db, EntityGroup _oEg_db, boolean _bIsExpire) throws SQLException, MiddlewareException,
        MiddlewareRequestException {

        EANList eList = new EANList();
        String strEntType = this.getEntityType();
        String strNow = _db.getDates().getNow();
        String strForever = _db.getDates().getForever();
        String strEntClass = "Entity";

        //does this entity exist in the MetaEntity table??
        boolean bIsNewEntity = MetaEntityList.isNewEntityType(_db, getProfile(), strEntType);

        //UPDATE
        if (!_bIsExpire) {
            ////// 1) New Entity case
            if (bIsNewEntity) {
                eList.put(new MetaEntityRow(getProfile(), strEntType, strEntClass, strNow, strForever, strNow, strForever, 2));
            }
        }
        //EXPIRE
        else if (!bIsNewEntity) {
            //if it isnt in database -> we dont need to remove it from database (duh..)
            eList.put(new MetaEntityRow(getProfile(), strEntType, strEntClass, strNow, strNow, strNow, strNow, 2));
        }
        return eList;
    }

    /*
     *  Nothing to unlock
     */
    /**
     *  Description of the Method
     *
     * @param  _s            Description of the Parameter
     * @param  _rdi          Description of the Parameter
     * @param  _db           Description of the Parameter
     * @param  _ll           Description of the Parameter
     * @param  _prof         Description of the Parameter
     * @param  _lockOwnerEI  Description of the Parameter
     * @param  _iLockType    Description of the Parameter
     */
    public void unlock(String _s, RemoteDatabaseInterface _rdi, Database _db, LockList _ll, Profile _prof, EntityItem _lockOwnerEI, int _iLockType) {
    }

    /**
     *  Description of the Method
     *
     * @param  _s   Description of the Parameter
     * @param  _ll  Description of the Parameter
     */
    public void resetLockGroup(String _s, LockList _ll) {
    }

    /**
     *  Sets the parentEntityItem attribute of the EntityGroup object
     *
     * @param  _ei  The new parentEntityItem value
     */
    public void setParentEntityItem(EntityItem _ei) {
    }

    /**
     *  Gets the matrixValue attribute of the EntityGroup object
     *
     * @param  _str  Description of the Parameter
     * @return       The matrixValue value
     */
    public Object getMatrixValue(String _str) {
        return null;
    }

    /**
     *  Description of the Method
     *
     * @param  _str  Description of the Parameter
     * @param  _o    Description of the Parameter
     */
    public void putMatrixValue(String _str, Object _o) {
    }

    /**
     *  Description of the Method
     */
    public void rollbackMatrix() {
    }

    /**
     *  Gets the matrixEditable attribute of the EntityGroup object
     *
     * @param  _str  Description of the Parameter
     * @return       The matrixEditable value
     */
    public boolean isMatrixEditable(String _str) {
        return false;
    }

    /**
     *  Adds a feature to the Column attribute of the EntityGroup object
     *
     * @param  _ean  The feature to be added to the Column attribute
     */
    public void addColumn(EANFoundation _ean) {
    }

    /**
     *  Description of the Method
     *
     * @param  _db              Description of the Parameter
     * @param  _rdi             Description of the Parameter
     * @param  _prof            Description of the Parameter
     * @param  _strRelatorType  Description of the Parameter
     * @return                  Description of the Return Value
     */
    public EntityList generatePickList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }

    /**
     *  Description of the Method
     *
     * @param  _db                           Description of the Parameter
     * @param  _rdi                          Description of the Parameter
     * @param  _prof                         Description of the Parameter
     * @param  _strRowKey                    Description of the Parameter
     * @return                               Description of the Return Value
     * @exception  EANBusinessRuleException  Description of the Exception
     */
    public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey) throws EANBusinessRuleException {
        return false;
    }

    /**
     *  Description of the Method
     *
     * @param  _db                           Description of the Parameter
     * @param  _rdi                          Description of the Parameter
     * @param  _prof                         Description of the Parameter
     * @param  _strRowKey                    Description of the Parameter
     * @param  _aeiChild                     Description of the Parameter
     * @return                               Description of the Return Value
     * @exception  EANBusinessRuleException  Description of the Exception
     * @param _strLinkOption
     */
    public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey, EntityItem[] _aeiChild,
                                String _strLinkOption) throws EANBusinessRuleException {
        return null;
    }

    /**
     *  Description of the Method
     *
     * @param  _db              Description of the Parameter
     * @param  _rdi             Description of the Parameter
     * @param  _prof            Description of the Parameter
     * @param  _strRelatorType  Description of the Parameter
     * @return                  Description of the Return Value
     */
    public EntityList create(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }

    /**
     *  Description of the Method
     *
     * @param  _db              Description of the Parameter
     * @param  _rdi             Description of the Parameter
     * @param  _prof            Description of the Parameter
     * @return                  Description of the Return Value
     * @param _astrKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     */
    public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException, MiddlewareException,
        MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        return null;
    }

    /**
     *  Gets the whereUsedList attribute of the EntityGroup object
     *
     * @param  _db              Description of the Parameter
     * @param  _rdi             Description of the Parameter
     * @param  _prof            Description of the Parameter
     * @param  _strRelatorType  Description of the Parameter
     * @return                  The whereUsedList value
     */
    public WhereUsedList getWhereUsedList(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRelatorType) {
        return null;
    }

    /**
     *  Gets the actionItemsAsArray attribute of the EntityGroup object
     *
     * @param  _strKey  Description of the Parameter
     * @return          The actionItemsAsArray value
     * @param _db
     * @param _rdi
     * @param _prof
     */
    public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) {
        return null;
    }

    /**
     *  Gets the globalClassificationGroup attribute of the EntityGroup object
     *
     * @return    The globalClassificationGroup value
     */
    public ClassificationGroup getGlobalClassificationGroup() {
        return m_clsgGlobal;
    }

    /**
     *  Sets the globalClassificationGroup attribute of the EntityGroup object
     *
     * @param  _cg  The new globalClassificationGroup value
     */
    public void setGlobalClassificationGroup(ClassificationGroup _cg) {
        if (_cg != null) {
            m_clsgGlobal = _cg;
        }
    }

    //////////////////////////////////////////////
    //EANSortable List methods
    //////////////////////////////////////////////
    /**
     *  Gets the filterTypesArray attribute of the EntityGroup object
     *
     * @return    The filterTypesArray value
     */
    public String[] getFilterTypesArray() {
        return c_saFilterTypes;
    }

    /**
     *  Gets the sortTypesArray attribute of the EntityGroup object
     *
     * @return    The sortTypesArray value
     */
    public String[] getSortTypesArray() {
        return c_saSortTypes;
    }

    /**
     *  Gets the sFInfo attribute of the EntityGroup object
     *
     * @return    The sFInfo value
     */
    public SortFilterInfo getSFInfo() {
        return m_SFInfo;
    }

    /**
     *  Gets the objectCount attribute of the EntityGroup object
     *
     * @return    The objectCount value
     */
    public int getObjectCount() {
        return getMetaAttributeCount();
    }

    /**
     *  Gets the object attribute of the EntityGroup object
     *
     * @param  _i  Description of the Parameter
     * @return     The object value
     */
    public EANComparable getObject(int _i) {
        return getMetaAttribute(_i);
    }

    /**
     *  Description of the Method
     *
     * @param  _compObj  Description of the Parameter
     */
    public void putObject(EANComparable _compObj) {
        putMetaAttribute( (EANMetaAttribute) _compObj);
    }

    /**
     *  Description of the Method
     *
     * @param  _compObj  Description of the Parameter
     */
    public void removeObject(EANComparable _compObj) {
        removeMetaAttribute( (EANMetaAttribute) _compObj);
    }

    /**
     *  Gets the objectFiltered attribute of the EntityGroup object
     *
     * @param  _i  Description of the Parameter
     * @return     The objectFiltered value
     */
    public boolean isObjectFiltered(int _i) {
        return!getMetaAttribute(_i).isDisplayableForFilter();
    }

    /**
     *  Sets the objectFiltered attribute of the EntityGroup object
     *
     * @param  _i  The new objectFiltered value
     * @param  _b  The new objectFiltered value
     */
    public void setObjectFiltered(int _i, boolean _b) {
        getMetaAttribute(_i).setDisplayableForFilter(!_b);
    }

    /**
     *  Description of the Method
     *
     * @param  _bUseWildcards  Description of the Parameter
     */
    public void performFilter(boolean _bUseWildcards) {
        //first go through and set the displayable
        //  - note that it is up to the calling party to check for isDisplayable on MetaRoles
        resetDisplayableMetaAttributes();
        if (getSFInfo().getFilter() != null) {
            String strFilter = getSFInfo().getFilter();
            for (int i = 0; i < getMetaAttributeCount(); i++) {
                String strToCompare = "";
                if (getSFInfo().getFilterType().equals(FILTER_ATTS_BY_ATTCODE)) {
                    strToCompare = getMetaAttribute(i).getAttributeCode();
                }
                else if (getSFInfo().getFilterType().equals(FILTER_ATTS_BY_ATTTYPE)) {
                    strToCompare = getMetaAttribute(i).getAttributeType();
                }
                else if (getSFInfo().getFilterType().equals(FILTER_ATTS_BY_ATTTYPEMAPPING)) {
                    strToCompare = getMetaAttribute(i).getAttributeTypeMapping();
                }
                else if (getSFInfo().getFilterType().equals(FILTER_ATTS_BY_SHORTDESC)) {
                    strToCompare = getMetaAttribute(i).getShortDescription();
                }
                else {
                    //default - by long description
                    strToCompare = getMetaAttribute(i).getLongDescription();
                }
                if (!_bUseWildcards) {
                    if (strToCompare.length() < strFilter.length()) {
                        getMetaAttribute(i).setDisplayableForFilter(false);
                    }
                    else if (!strToCompare.substring(0, strFilter.length()).equalsIgnoreCase(strFilter)) {
                        getMetaAttribute(i).setDisplayableForFilter(false);
                    }
                }
                else {
                    if (!SortFilterInfo.equalsWithWildcards(strFilter, strToCompare, new char[] {'*', '%'}, true)) {
                        getMetaAttribute(i).setDisplayableForFilter(false);
                    }
                }
            }
        }
    }

    /**
     *  Description of the Method
     */
    public void performSort() {
        sortMetaAttributes(getSFInfo().getSortType(), getSFInfo().isAscending());
    }

    /**
     *  Sets the usedInSearch attribute of the EntityGroup object
     *
     * @param  _b  The new usedInSearch value
     */
    protected void setUsedInSearch(boolean _b) {
        m_bUsedInSearch = _b;
    }

    /**
     *  Gets the usedInSearch attribute of the EntityGroup object
     *
     * @return    The usedInSearch value
     */
    public boolean isUsedInSearch() {
        return m_bUsedInSearch;
    }

    /**
     *  Gets the dynaTable attribute of the EntityGroup object
     *
     * @return    The dynaTable value
     */
    public boolean isDynaTable() {
        return isUsedInSearch();
    }

    /*
     *  Get all entityItems in this group and reassign ownership to this object
     */
    /**
     *  Description of the Method
     */
    protected void reassignEntityItems() {
        for (int ii = 0; ii < getEntityItemCount(); ii++) {
            EntityItem ei = getEntityItem(ii);
            ei.reassign(this);
        }
    }

    /**
     *  Gets the metaColumnOrderGroup attribute of the EntityGroup object
     *
     * @param  _db                                        Description of the Parameter
     * @param  _rdi                                       Description of the Parameter
     * @return                                            The metaColumnOrderGroup value
     * @exception  RemoteException                        Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  SQLException                           Description of the Exception
     */
    public MetaColumnOrderGroup getMetaColumnOrderGroup(Database _db, RemoteDatabaseInterface _rdi) throws RemoteException, MiddlewareException,
        MiddlewareShutdownInProgressException, SQLException {
        if (m_mcog == null) {
            if (_db != null) {
                MetaColumnOrderGroup mcog = _db.getMetaColumnOrderGroup(getEntityType(), getProfile());
                mcog.setParent(this);
                setMetaColumnOrderGroup(mcog);
            }
            else if (_rdi != null) {
                MetaColumnOrderGroup mcog = _rdi.getMetaColumnOrderGroup(getEntityType(), getProfile());
                mcog.setParent(this);
                setMetaColumnOrderGroup(mcog);
            }
        }
        return m_mcog;
    }

    /**
     *  Sets the metaColumnOrderGroup attribute of the EntityGroup object
     *
     * @param  _mcog  The new metaColumnOrderGroup value
     */
    protected void setMetaColumnOrderGroup(MetaColumnOrderGroup _mcog) {
        m_mcog = _mcog;
        if (m_mcog != null) {
            m_mcog.setParent(this);
        }
    }

    /**
     *  Gets the metaColumnOrderGroup attribute of the EntityGroup object
     *
     * @return    The metaColumnOrderGroup value
     */
    public MetaColumnOrderGroup getMetaColumnOrderGroup() {
        return m_mcog;
    }

    /**
     * getActualColumnListCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getActualColumnListCount() {
        return getActualColumnList().size();
    }

    private EANList getActualColumnList() {

        //    System.out.println("EntityGroup.getActualColumnList()" + getKey());

        EANList el = new EANList();

        if ( (isRelator() || isAssoc()) && ! (getParent() instanceof MetaEntityList)) {
            // if this EntityGroup is from a MetaEntityList --> dont execute this part
            EntityList entl = getEntityList();
            if (entl != null) {
                EntityGroup egDown = entl.getEntityGroup(getEntity2Type());
                if (egDown != null) {
                    el = egDown.getActualColumnList();
                }
            }
        }

        for (int ii = 0; ii < getMetaAttributeCount(); ii++) {
            EANMetaAttribute ma = getMetaAttribute(ii);
            if ( (isNavigateLike() && ma.isNavigate()) || !isNavigateLike()) {
                try {
                    Implicator maImplicator = new Implicator(ma, getProfile(), getEntityType() + ":" + ma.getKey());
                    maImplicator.putShortDescription(ma.getShortDescription());
                    maImplicator.putLongDescription(ma.getLongDescription());
                    el.put(maImplicator);
                }
                catch (Exception x) {
                    x.printStackTrace();
                }
            }
        }

        return el;
    }

    /**
     * getActualRowListCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getActualRowListCount() {
        return getData().size();
    }

    /**
     * add row based on existing data
     * VEEdit_Iteration2
     * @param _key
     * @return boolean
     */
    public boolean addRow(String _key) {
        EntityList el = getEntityList();
        boolean bOut = false;
        if (el != null) {
            EANActionItem ean = el.getParentActionItem();
            if (ean.isVEEdit()) {
                EntityItem eiOrg = getEntityItem(_key);
                if (eiOrg instanceof VEEditItem){
					try{
						((VEEditItem)eiOrg).copyVE();
						bOut = true;
					}catch(Exception e){
						e.printStackTrace();
					}
				}else{
					System.err.println("EntityGroup.addRow unsupported for "+_key);
				}
            }
        }
        return bOut;
    }

    /*
     *  This is in the context of the RowSelectable Table.  duplicate a row
     *  To The table that is used by this as a reference.
     */
    /**
     *  Adds a feature to the Row attribute of the EntityGroup object
     *
     * @return    Description of the Return Value
     * @param _strKey
     * @param _iDup
     */
    public boolean duplicate(String _strKey, int _iDup) {
        EntityGroup egChild = null;
        EntityItem eiChild = null;
        EntityGroup egParent = null;
        EntityItem eiParent = null;
        EntityItem eiChildOrg = null;
        try {
            if (canCreate()) {
                EntityList el = getEntityList();
                EANActionItem ai = el.getParentActionItem();

                EntityItem eiOrg = getEntityItem(_strKey);
                if (eiOrg == null) {
                    return false;
                }

                if (ai != null && ai.isVEEdit()) {
					boolean bOut = false;
					if (eiOrg instanceof VEEditItem){
						try{
							for (int cop = 0; cop < _iDup; ++cop) {
								((VEEditItem)eiOrg).copyVE();
							}
							bOut = true;
						}catch(Exception e){
							e.printStackTrace();
						}
					}else{
						System.err.println("EntityGroup.duplicate unsupported for "+_strKey);
					}

                    return bOut;
                } //VEEdit

                for (int i = 0; i < _iDup; i++) {
                    EntityItem ei = new EntityItem(this, null, getEntityType(), ai.getID());
                    putEntityItem(ei);

                    ei.duplicate(eiOrg);
                    //
                    // O.K.  in either case.. we will either build a
                    // dummy Assoc Record.. or a placeholder for the Relator
                    //
                    if (isRelator() || isAssoc()) {

                        // Pick the first one in the ParentItem list...

                        egParent = el.getParentEntityGroup();
                        eiParent = null;
                        if (egParent.getEntityItemCount() == 1) {
                            eiParent = egParent.getEntityItem(0);
                        }
                        else if (egParent.getEntityItemCount() > 1) {
                            // We put it in the never never land place now
                            eiParent = new EntityItem(egParent, null, egParent.getEntityType(), -1);
                        }

                        if (el.isCreateParent()) {
                            egChild = el.getEntityGroup(getEntity1Type());
                            eiChild = new EntityItem(egChild, null, egChild.getEntityType(), ai.getID());

                            egChild.putEntityItem(eiChild);
                            // Now hook them all up

                            ei.putDownLink(eiParent);
                            ei.putUpLink(eiChild);

                            eiChildOrg = (EntityItem) eiOrg.getUpLink(0);
                            eiChild.duplicate(eiChildOrg);
                        }
                        else {
                            egChild = el.getEntityGroup(getEntity2Type());
                            eiChild = new EntityItem(egChild, null, egChild.getEntityType(), ai.getID());

                            egChild.putEntityItem(eiChild);
                            // Now hook them all up

                            ei.putUpLink(eiParent);
                            ei.putDownLink(eiChild);

                            eiChildOrg = (EntityItem) eiOrg.getDownLink(0);
                            eiChild.duplicate(eiChildOrg);
                        }

                    }else{
                    	// not a relator or assoc
                    	// handle case where search and edit and then create is done, is on the entity not the relator
                    	if (ai instanceof EditActionItem) {
    						EditActionItem eai = (EditActionItem) ai;
    						if (eai.canCreate()){ //MN 38666284 - CQ00022911 continued
    							repairStructure(eai, ei);
                            }
                        }
                    }
                }
                return true;
            }
        }
        catch (Exception x) {
            x.printStackTrace();
        }

        return false;
    }

    /**
     * (non-Javadoc)
     * linkAndRefresh
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#linkAndRefresh(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.LinkActionItem)
     */
    public Object linkAndRefresh(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws SQLException,
        MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException, RemoteException {
        return null;
    }

    /**
     * (non-Javadoc)
     * isParentAttribute
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isParentAttribute(java.lang.String)
     */
    public boolean isParentAttribute(String _strEntityType) {

        EntityGroup egParent = null;

        EntityList entl = getEntityList();
        if (entl == null) {
            return false;
        }

        if (isRelator()) {
            if (getEntity1Type().equals(_strEntityType)) {
                return true;
            }
            return false;

        }

        egParent = entl.getParentEntityGroup();
        if (egParent != null && egParent.getEntityType().equals(_strEntityType)) {
            return true;
        }
        return false;
    }

    /**
     * (non-Javadoc)
     * isChildAttribute
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#isChildAttribute(java.lang.String)
     */
    public boolean isChildAttribute(String _strEntityType) {
        EntityGroup egParent = null;
        EntityList entl = getEntityList();
        if (entl == null) {
            return false;
        }

        if (isRelator()) {
            if (getEntity2Type().equals(_strEntityType)) {
                return true;
            }
            return false;
        }

        egParent = entl.getParentEntityGroup();
        if (egParent != null && !egParent.getEntityType().equals(_strEntityType)) {
            return true;
        }
        return false;
    }

    /**
     * am i a VEEdit
     * VEEdit_Iteration3
     *
     * @return boolean
     * @author tony
     */
    public boolean isVEEdit() {
        EntityList el = getEntityList();
        if (el != null) {
            return el.isVEEdit();
        }
        return false;
    }

    /**
     *  Description of the Method
     *
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     */
    protected void enforceWorkGroupDomain(Database _db, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareException {
        EANList eltmp1 = new EANList(EANMetaEntity.LIST_SIZE);
        EANList eltmp2 = new EANList(EANMetaEntity.LIST_SIZE);

        ReturnStatus returnStatus = new ReturnStatus();
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        String strEnterprise = _prof.getEnterprise();
        int iWGID = _prof.getWGID();
        String strValOn = _prof.getValOn();
        String strEffOn = _prof.getEffOn();

        SearchActionItem sai = null;
        if (getEntityList() != null && getEntityList().getParentActionItem() instanceof SearchActionItem) {
            sai = (SearchActionItem) getEntityList().getParentActionItem();
        }

        try {
            rs = _db.callGBL7065(returnStatus, strEnterprise, iWGID, strValOn, strEffOn);
            rdrs = new ReturnDataResultSet(rs);
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }

        for (int ii = 0; ii < rdrs.size(); ii++) {
            String strAttributeCode = rdrs.getColumn(ii, 0);
            // This is the AttributeCode
            String strAttributeValue = rdrs.getColumn(ii, 1);
            // This is the AttributeCode

            _db.debug(D.EBUG_SPEW, "gbl7065:answers:" + strAttributeCode + ":" + strAttributeValue);
            //RQ0713072645
            if (sai != null) {
                if (strAttributeCode.equals(sai.getWGDomainAttrCode()) &&
                    !strAttributeCode.equals(sai.getDomainAttrCode())) {
                    // use values from WG's attrcode, but use domainattrcode in entity search
                    strAttributeCode = sai.getDomainAttrCode();
                    D.ebug(D.EBUG_SPEW,
                           "gbl7065:answers override:" + sai.getWGDomainAttrCode() + " to " + strAttributeCode);
                }
            } // end RQ0713072645

            // Tuck it away
            eltmp2.put(new Implicator(null, getProfile(), strAttributeCode + strAttributeValue));

            String strEntityType = getEntityType();
            EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) getMetaAttribute(strAttributeCode);
            if (mfa != null && (!eltmp1.containsKey(strEntityType + strAttributeCode))) {
                eltmp1.put(new Implicator(mfa, null, strEntityType + strAttributeCode));
            }
        }

        // Now we have everything that is needs to be worked on in the EANList
        // Turn them all off
        for (int ii = 0; ii < eltmp1.size(); ii++) {
            Implicator imp = (Implicator) eltmp1.getAt(ii);
            EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) imp.getParent();
            Vector v = new Vector();
            for (int iy = 0; iy < mfa.getMetaFlagCount(); iy++) {
                MetaFlag mf = mfa.getMetaFlag(iy);
                if (!eltmp2.containsKey(mf.getKey())) {
                    mf.setExpired(true);
                }
                else {
					if (mf.isExpired()){ //TIR76CM62
						D.ebug(D.EBUG_ERR, "ERROR: Attempting to use expired flag "+
							mf.getFlagCode()+" from "+mfa.getAttributeCode());
					}else{
	                    v.addElement(mf.getFlagCode());
					}
                }
            }
            String[] as1 = new String[v.size()];
            v.toArray(as1);
            mfa.setWGDefaultValues(as1);
        }
    }

    /**
     *  Description of the Method
     *
     * @param  _prof                           Description of the Parameter
     * @exception  MiddlewareRequestException  Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @param _ro
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     */
    protected void enforceWorkGroupDomain(RemoteDatabaseInterface _ro, Profile _prof) throws RemoteException, MiddlewareException,
        MiddlewareRequestException, MiddlewareShutdownInProgressException {
        String strEnterprise = _prof.getEnterprise();
        int iWGID = _prof.getWGID();
        String strValOn = _prof.getValOn();
        String strEffOn = _prof.getEffOn();

        ReturnDataResultSetGroup rdrsg = _ro.remoteGBL7065(strEnterprise, iWGID, strValOn, strEffOn);
        ReturnDataResultSet rdrs = rdrsg.getResultSet(0);

        EANList eltmp1 = new EANList(EANMetaEntity.LIST_SIZE);
        EANList eltmp2 = new EANList(EANMetaEntity.LIST_SIZE);

        SearchActionItem sai = null;
        if (getEntityList() != null && getEntityList().getParentActionItem() instanceof SearchActionItem) {
            sai = (SearchActionItem) getEntityList().getParentActionItem();
        }

        for (int ii = 0; ii < rdrs.size(); ii++) {
            String strAttributeCode = rdrs.getColumn(ii, 0);
            // This is the AttributeCode
            String strAttributeValue = rdrs.getColumn(ii, 1);
            // This is the AttributeCode

            D.ebug(D.EBUG_SPEW, "gbl7065:answers:" + strAttributeCode + ":" + strAttributeValue);
            //RQ0713072645
            if (sai != null) {
                if (strAttributeCode.equals(sai.getWGDomainAttrCode()) &&
                    !strAttributeCode.equals(sai.getDomainAttrCode())) {
                    // use values from WG's attrcode, but use domainattrcode in entity search
                    strAttributeCode = sai.getDomainAttrCode();
                    D.ebug(D.EBUG_SPEW,
                           "gbl7065:answers override:" + sai.getWGDomainAttrCode() + " to " + strAttributeCode);
                }
            } // end RQ0713072645

            // Tuck it away
            eltmp2.put(new Implicator(null, getProfile(), strAttributeCode + strAttributeValue));

            String strEntityType = getEntityType();
            EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) getMetaAttribute(strAttributeCode);
            if (mfa != null && (!eltmp1.containsKey(strEntityType + strAttributeCode))) {
                eltmp1.put(new Implicator(mfa, null, strEntityType + strAttributeCode));
            }
        }

        // Now we have everything that is needs to be worked on in the EANList
        // Turn them all off
        for (int ii = 0; ii < eltmp1.size(); ii++) {
            Implicator imp = (Implicator) eltmp1.getAt(ii);
            EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) imp.getParent();
            Vector v = new Vector();
            for (int iy = 0; iy < mfa.getMetaFlagCount(); iy++) {
                MetaFlag mf = mfa.getMetaFlag(iy);
                if (!eltmp2.containsKey(mf.getKey())) {
                    mf.setExpired(true);
                }
                else {
					if (mf.isExpired()){ //TIR76CM62
						D.ebug(D.EBUG_ERR, "ERROR: Attempting to use expired flag "+
							mf.getFlagCode()+" from "+mfa.getAttributeCode());
					}else{
	                    v.addElement(mf.getFlagCode());
					}

                }
            }
            String[] as1 = new String[v.size()];
            v.toArray(as1);
            mfa.setWGDefaultValues(as1);
        }
    }

}
