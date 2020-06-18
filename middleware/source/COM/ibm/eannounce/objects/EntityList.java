// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EntityList.java,v $
// Revision 1.762  2014/04/17 18:27:05  wendy
// RCQ 216919 EACM BH - Add New Attributes to Search
//
// Revision 1.761  2014/02/28 21:17:47  wendy
// RCQ242344 Translation support fallback
//
// Revision 1.760  2014/01/22 21:15:12  wendy
// RTC 1080073 - LS getting outofmemory exceptions
//
// Revision 1.759  2013/11/13 19:15:58  wendy
// CQ 227988 - added mergeLists()
//
// Revision 1.758  2013/10/24 17:36:02  wendy
// search picklist perf updates
//
// Revision 1.757  2013/07/26 19:05:55  wendy
// fix VEEdit transitions RCQ 222905 BH Catalog overrides
//
// Revision 1.756  2012/11/08 21:41:40  wendy
// RCQ00210066-WI Ref CQ00014746 use EOD to build cache, rollback if error instead of commit, cache vector of entitytypes, not list of entitygroups, clip for edit no longer generate fake relators and all unused parents
//
// Revision 1.755  2012/04/16 16:33:28  wendy
// fillcopy/fillpaste perf updates
//
// Revision 1.754  2010/11/08 20:38:47  wendy
// Reuse string objects to reduce memory requirements
//
// Revision 1.753  2010/09/15 17:55:18  wendy
// Added subset() for JUI checks
//
// Revision 1.752  2010/01/04 18:51:28  wendy
// added debug msg
//
// Revision 1.751  2009/11/03 18:54:52  wendy
// SR11, SR15 and SR17 restrict create of relator
//
// Revision 1.750  2009/08/26 17:56:17  wendy
// SR5 updates
//
// Revision 1.749  2009/08/25 12:54:05  wendy
// PDG reuse actions, dont deref action
//
// Revision 1.748  2009/08/18 20:19:48  wendy
// Provide method to allow session cleanup
//
// Revision 1.747  2009/05/20 01:20:44  wendy
// Add execution DTS
//
// Revision 1.746  2009/05/11 14:22:08  wendy
// Support turning off domain check for all actions
//
// Revision 1.745  2009/04/18 00:36:21  wendy
// Make sure domain is not deactivated in domain restricted searches
//
// Revision 1.744  2009/03/12 15:08:41  wendy
// Added actionkey to domain msg
//
// Revision 1.743  2009/03/10 21:22:28  wendy
// MN38666284 - CQ00022911:make sure parent exists
//
// Revision 1.742  2009/03/05 22:12:42  wendy
// MN38666284 - CQ00022911:Creating elements from search not linking back to Workgroup
//
// Revision 1.741  2009/01/19 13:13:22  wendy
// More debug output
//
// Revision 1.740  2008/10/10 13:20:47  wendy
// Allow UI to recognize search limit failure to improve performance
//
// Revision 1.739  2008/04/29 19:27:56  wendy
// MN35270066 VEEdit rewrite
//
// Revision 1.738  2008/02/01 22:10:08  wendy
// Cleanup RSA warnings
//
// Revision 1.737  2008/01/21 13:37:18  wendy
// Added support to turn off domain check on workflowaction
//
// Revision 1.736  2007/11/07 14:42:41  wendy
// MN33607291 error when parent and child are same entitytypes MODEL:MODELREL:MODEL
//
// Revision 1.735  2007/09/11 19:25:05  wendy
// JRE 1.3.1 does not support StringBuffer.append(StringBuffer)
//
// Revision 1.734  2007/09/07 19:48:35  wendy
// TIR76CM62 - prevent usage of expired flags for WG defaults
//
// Revision 1.733  2007/08/21 00:16:52  wendy
// Add chk if rs != null before close()
//
// Revision 1.732  2007/08/15 14:36:37  wendy
// RQ0713072645- Enhancement 3
//
// Revision 1.731  2007/08/09 14:19:11  wendy
// debug msgs for domain chk
//
// Revision 1.730  2007/08/08 17:43:13  wendy
// RQ0713072645 enable domain check, but can be turned off in mw properties
//
// Revision 1.729  2007/08/03 20:39:44  wendy
// Corrected setting default domain on copyaction
//
// Revision 1.728  2007/08/03 13:01:55  chris
// Added sleep and message to retry.
//
// Revision 1.727  2007/08/02 21:11:49  wendy
// RQ0713072645 place holder
//
// Revision 1.726  2007/08/02 15:00:02  chris
// Fix for MN32526975 records not being cleaned up in trsnavigate
//
// Revision 1.725  2007/07/31 16:52:11  bala
// Chunking for call to gbl8000 using extract action item
//
// Revision 1.724  2007/06/18 19:07:16  wendy
// RQ0927066214
// XCC GX SR001.5.001 EACM Enabling Technology - Bread Crumbs
//
// Revision 1.723  2007/06/04 20:05:21  wendy
// Added info to error msgs
//
// Revision 1.722  2007/05/11 19:32:42  chris
// Make SearchLimit 100,000 by default.

// can be overidden with System property of UniqueAttributeCheck.limit and SearchActionItem.limit
//
// Revision 1.721  2007/05/01 22:58:43  bala
// rebuilding again from version 1.719 to remove merge conflicts
//
// Revision 1.720  2007/04/23 17:20:31  bala
// call gbl8000 with debug parms
//
// Revision 1.719  2007/04/10 18:05:14  wendy
// removed debug msgs
//
// Revision 1.718  2007/03/30 02:41:45  tony
// *** empty log message ***
//
// Revision 1.717  2007/03/27 17:13:39  chris
// Cleanup multiple sessionid's created in Search
//
// Revision 1.716  2007/03/27 14:24:51  chris
// Movel last call to GBL8105 in constructors to the finally block to ensure it cleans up after itself when an exception occurs
//
// Revision 1.715  2007/03/22 20:52:10  chris
// add limit to gbl9000 call
//
// Revision 1.714  2007/02/27 03:51:37  tony
// Fix for WGDefault on VEEdit Phase1
//
// Revision 1.713  2006/11/09 00:30:54  tony
// TIR USRO-R-JPRD-6UYLY6
//
// Revision 1.712  2006/10/05 19:27:56  roger
// Fix
//
// Revision 1.711  2006/10/05 16:00:30  roger
// Fix
//
// Revision 1.710  2006/10/05 15:44:43  roger
// Log putBlob calls for caching into TCOUNT table
//
// Revision 1.709  2006/09/22 20:46:09  joan
// adjust search limit on filter by entity
//
// Revision 1.708  2006/06/01 15:05:23  joan
// fix null pointer
//
// Revision 1.707  2006/05/30 21:32:14  dave
// more consice error messages
//
// Revision 1.706  2006/05/18 16:23:11  tony
// added notes for cr0202062350
//
// Revision 1.705  2006/05/03 19:33:47  joan
// remove comment
//
// Revision 1.704  2006/03/23 20:22:09  tony
// 6MV4U7
// multiple relationships were not being processed
// properly when they had the same entity1key
// or same entity2key
//
// Revision 1.703  2006/03/17 21:54:38  tony
// 6MV4U7
// Reworked editRelator.
// if either end of the relator is a relator, that
// information will be expanded to the entites
// at the end of that relationship.
//
// Revision 1.702  2006/03/17 18:50:57  joan
// changes for search filter entity
//
// Revision 1.701  2006/02/22 18:17:07  tony
// fixed AttributeDisplay and TrailAttribute display so they were correct
// they were processing in inverse order.
//
// Revision 1.700  2006/02/20 21:39:47  joan
// clean up System.out.println
//
// Revision 1.699  2006/02/20 19:23:51  tony
// 6LY42Z
//
// Revision 1.698  2006/01/31 21:55:33  dave
// turned off navigate deletes for seach for now trsNavigate
//
// Revision 1.697  2006/01/12 23:14:19  joan
// work on CR0817052746
//
// Revision 1.696  2005/11/14 15:16:45  tony
// VEEdit_Iteration3
// PRODSTRUCT Functionality.
//
// Revision 1.695  2005/11/10 21:25:16  tony
// VEEdit_Iteration3
//
// Revision 1.694  2005/11/08 15:24:39  tony
// VEEdit_Ordering
//
// Revision 1.693  2005/11/04 14:52:10  tony
// VEEdit_Iteration2
// updated VEEdit logic to meet new requirements.
//
// Revision 1.692  2005/09/28 19:11:14  tony
// TIR USRO-R-TMAY-6GLQLW
//
// Revision 1.691  2005/09/02 15:32:58  tony
// VEEdit functionality addition for CR 0815056514
//
// Revision 1.690  2005/08/30 21:05:41  joan
// remove comments by cvs
//
// Revision 1.689  2005/08/30 21:00:54  joan
// remove comments made by cvs to compile
//
// Revision 1.688  2005/08/30 17:39:14  dave
// new cat comments
//
// Revision 1.683  2005/07/22 19:47:08  joan
// fixes
//
// Revision 1.682  2005/07/15 20:11:16  joan
// fixes
//
// Revision 1.681  2005/07/15 16:50:02  joan
// fix compile
//
// Revision 1.680  2005/07/15 16:31:41  joan
// work on filter search on entity
//
// Revision 1.687  2005/08/16 18:11:13  tony
// updated catalog viewer logic and adjusted pull
// criteria
//
// Revision 1.686  2005/08/11 17:52:22  tony
// improved EntityList creation.
//
// Revision 1.685  2005/08/10 16:23:29  tony
// fixed logic.
//
// Revision 1.684  2005/08/04 15:51:39  tony
// catalog mods
//
// Revision 1.683  2005/07/22 19:47:08  joan
// fixes
//
// Revision 1.682  2005/07/15 20:11:16  joan
// fixes
//
// Revision 1.681  2005/07/15 16:50:02  joan
// fix compile
//
// Revision 1.680  2005/07/15 16:31:41  joan
// work on filter search on entity
//
// Revision 1.679  2005/06/07 19:56:49  gregg
// finish up the whole 1019 thing
//
// Revision 1.678  2005/06/07 18:25:40  gregg
// change to gbl1019 to obtain e1/e2 within SP
//
// Revision 1.677  2005/06/07 18:06:09  gregg
// fix
//
// Revision 1.676  2005/06/07 18:01:58  gregg
// more for 1019
//
// Revision 1.675  2005/06/07 17:50:29  gregg
// debug
//
// Revision 1.674  2005/06/06 21:41:05  gregg
// 1019.. remember to uncomment 8105 after testing!!!
//
// Revision 1.673  2005/04/20 22:01:11  joan
// fixes for search
//
// Revision 1.672  2005/04/20 21:57:22  joan
// fixes for search
//
// Revision 1.671  2005/03/11 22:42:53  dave
// removing some auto genned stuff
//
// Revision 1.670  2005/03/03 21:25:17  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.669  2005/03/03 01:46:01  dave
// Big Jtest Checkin
//
// Revision 1.668  2005/02/17 16:57:25  joan
// work on wild card in search
//
// Revision 1.667  2005/02/16 21:10:07  gregg
// allNLS
//
// Revision 1.666  2005/02/01 00:42:52  dave
// fixing SPEW log file
//
// Revision 1.665  2005/01/18 21:46:49  dave
// more parm debug cleanup
//
// Revision 1.664  2005/01/18 18:42:11  gregg
// remove some dead Rule51 code
//
// Revision 1.663  2005/01/18 01:04:20  gregg
// store Rule51Group on MetaLink
//
// Revision 1.662  2005/01/10 21:47:49  joan
// work on multiple edit
//
// Revision 1.661  2005/01/05 19:24:08  joan
// add new method
//
// Revision 1.660  2004/11/23 23:06:21  gregg
// more colorder control on Search ... EntityList this time
//
// Revision 1.659  2004/11/23 22:56:13  gregg
// more colorders for Search Actions
//
// Revision 1.658  2004/11/22 22:08:44  gregg
// undo previous changes
//
// Revision 1.657  2004/11/22 21:53:34  gregg
// MetaColumnOrderGroups for SearchActions
//
// Revision 1.656  2004/11/18 22:24:20  dave
// syntax
//
// Revision 1.655  2004/11/18 22:18:57  dave
// ok.. looking for forcedisplay
//
// Revision 1.654  2004/11/18 20:26:24  dave
// syntax
//
// Revision 1.653  2004/11/18 20:19:24  dave
// making all tabs show in the event of a isShowParent Action Item
//
// Revision 1.652  2004/11/17 20:18:05  gregg
// remove debugs
//
// Revision 1.651  2004/11/16 22:56:41  gregg
// some fixes found while looking at EntityList Edit constructor
//
// Revision 1.650  2004/11/12 01:16:23  joan
// work on search picklist
//
// Revision 1.649  2004/11/09 21:00:04  joan
// work on showing both parent and child in navigate
//
// Revision 1.648  2004/11/05 22:22:57  dave
// changing something to Spew
//
// Revision 1.647  2004/11/04 16:50:39  gregg
// move method, comments
//
// Revision 1.646  2004/11/03 23:12:43  gregg
// MetaColumnOrderGroup for NavActionItems
// MetaLinkAttr switch on ActionItem ColumnOrderControl
// Apply ColumnOrderControl for EntityItems
// Cleanup Debugs
//
// Revision 1.645  2004/11/03 19:23:41  gregg
// lets throw sum Exceptions
//
// Revision 1.644  2004/11/03 19:18:55  gregg
// hasMetaColumnOrderGroup check on EANActionItem
//
// Revision 1.643  2004/11/03 19:15:17  gregg
// storing MetaColumnOrderGroup object in EANActionItem; built on parent EntityList, where applicaple only
//
// Revision 1.642  2004/10/29 18:58:06  dave
// closing in
//
// Revision 1.641  2004/10/29 18:09:53  dave
// one more syntax
//
// Revision 1.640  2004/10/29 18:04:57  dave
// syntas
//
// Revision 1.639  2004/10/29 17:58:12  dave
// speeding up search
//
// Revision 1.638  2004/10/29 17:36:56  dave
// attempting to block move data for relator grabber
//
// Revision 1.637  2004/10/28 00:27:23  dave
// attempting to fix null pointer.. when
// domain enabled search on relator (from both parent
// and child).. the child may not have the flag meta
// that is on the parent... (from the worgroup)
//
// Revision 1.636  2004/10/25 23:51:23  dave
// more attempted reuse
//
// Revision 1.635  2004/10/25 23:18:05  dave
// lets see what we got
//
// Revision 1.634  2004/10/25 23:01:24  dave
// trace
//
// Revision 1.633  2004/10/25 21:12:34  dave
// another tax
//
// Revision 1.632  2004/10/25 21:07:18  dave
// trying to reduce the footprint of the EntityList Nav
// Object by reusing strings within the object
// instead of those coming over from the ReturnDataResult
// Set
//
// Revision 1.631  2004/10/25 17:23:00  joan
// work on create parent
//
// Revision 1.630  2004/10/14 23:08:10  dave
// more syntax
//
// Revision 1.629  2004/10/14 22:55:37  dave
// syntax
//
// Revision 1.628  2004/10/14 22:34:15  dave
// trying to get enforced Domain in search
//
// Revision 1.627  2004/10/13 20:25:24  joan
// fix null pointer
//
// Revision 1.626  2004/10/13 17:52:29  joan
// add populating derived id for parent entity group extract action
//
// Revision 1.625  2004/10/11 23:21:08  dave
// removed trace
//
// Revision 1.624  2004/10/11 23:03:34  dave
// do not need to set parent entity group on
// grab by Relator ID here
//
// Revision 1.623  2004/10/11 22:49:17  dave
// more trace
//
// Revision 1.622  2004/10/11 22:29:27  dave
// re trace
//
// Revision 1.621  2004/09/21 19:54:07  joan
// fixes
//
// Revision 1.620  2004/09/15 21:20:17  joan
// work on get no down link when add to cart
//
// Revision 1.619  2004/09/15 18:04:33  joan
// work on BluePage
//
// Revision 1.618  2004/09/10 17:50:09  joan
// working on link entities to relators for 3.0a
//
// Revision 1.617  2004/08/30 21:00:32  dave
// more set active information
//
// Revision 1.616  2004/08/27 21:50:17  dave
// last fix
//
// Revision 1.615  2004/08/27 21:44:50  dave
// more stuff
//
// Revision 1.614  2004/08/27 21:29:11  dave
// new API to pull ve's based upon target entitytype, trancode and
// an interval
//
// Revision 1.613  2004/08/19 00:04:38  joan
// work on search
//
// Revision 1.612  2004/08/18 22:25:20  joan
// work on search
//
// Revision 1.611  2004/08/18 21:54:18  joan
// work on search
//
// Revision 1.610  2004/08/18 17:25:03  joan
// work on search
//
// Revision 1.609  2004/08/18 00:02:29  joan
// work on search
//
// Revision 1.608  2004/08/17 22:43:31  joan
// work on search
//
// Revision 1.607  2004/08/13 17:12:18  roger
// Use new constructor
//
// Revision 1.606  2004/08/12 21:13:54  joan
// fix bug
//
// Revision 1.605  2004/08/06 05:19:29  dave
// ok..jury rigged.. but may be ok
//
// Revision 1.604  2004/08/06 05:06:00  dave
// more trickery
//
// Revision 1.603  2004/08/06 05:04:56  dave
// trying to work in path
//
// Revision 1.602  2004/08/06 04:47:46  dave
// more cleanup
//
// Revision 1.601  2004/08/06 04:22:42  dave
// some trickery for ECCM
//
// Revision 1.600  2004/08/06 03:45:11  dave
// syntax
//
// Revision 1.599  2004/08/06 03:35:47  dave
// rounding out new contructor
//
// Revision 1.598  2004/08/05 21:02:29  dave
// small syntax error
//
// Revision 1.597  2004/08/05 20:47:57  dave
// GBL9005 change and more ECCM II
//
// Revision 1.596  2004/08/05 05:43:52  dave
// syntax
//
// Revision 1.595  2004/08/05 05:38:20  dave
// first cut at new EntityList Constructor
//
// Revision 1.594  2004/07/30 19:48:03  joan
// work on edit
//
// Revision 1.593  2004/07/30 16:05:39  joan
// work on use Root EI
//
// Revision 1.592  2004/07/25 05:02:07  dave
// undo Remote trick
//
// Revision 1.591  2004/07/25 04:20:20  dave
// lets try some remote dereferencing
//
// Revision 1.590  2004/07/10 23:47:32  dave
// placed the put cache in the proper side of the bracket
// were were always putting the cache.. even when we
// did not build it (for navigate)
//
// Revision 1.589  2004/07/09 19:53:43  joan
// change to MiddlewareException
//
// Revision 1.588  2004/06/25 16:18:30  joan
// work on BluePage
//
// Revision 1.587  2004/06/22 21:39:10  joan
// work on CR
//
// Revision 1.586  2004/06/21 19:49:56  joan
// work on ABR status
//
// Revision 1.585  2004/06/21 19:06:51  joan
// work on display parent
//
// Revision 1.584  2004/06/21 15:53:21  joan
// fix compile
//
// Revision 1.583  2004/06/21 15:48:01  joan
// fix compile
//
// Revision 1.582  2004/06/21 15:25:42  joan
// fix compile
//
// Revision 1.581  2004/06/21 15:02:35  joan
// add ABRStatus action
//
// Revision 1.580  2004/06/18 21:39:40  joan
// work on show parent for nav action
//
// Revision 1.579  2004/06/18 17:11:18  joan
// work on edit relator
//
// Revision 1.578  2004/06/16 20:10:18  joan
// work on CopyActionItem
//
// Revision 1.577  2004/06/15 20:48:25  joan
// fix compile
//
// Revision 1.576  2004/06/15 20:36:42  joan
// add CopyActionItem
//
// Revision 1.575  2004/06/08 17:51:31  joan
// throw exception
//
// Revision 1.574  2004/06/08 17:28:34  joan
// add method
//
// Revision 1.573  2004/06/02 23:08:12  dave
// removing trace
//
// Revision 1.572  2004/06/02 22:35:32  dave
// more and more and more trace
//
// Revision 1.571  2004/06/02 21:35:36  dave
// trace for dump
//
// Revision 1.570  2004/06/02 21:34:09  dave
// more trace
//
// Revision 1.569  2004/06/02 21:23:01  dave
// debug trace for dump
//
// Revision 1.568  2004/05/24 22:34:18  dave
// int cleanup in prep for ECCM 2.0
//
// Revision 1.567  2004/05/21 17:13:07  dave
// trace for create statement
//
// Revision 1.566  2004/05/19 18:05:46  gregg
// FB 53803 (derived EntityGroup needs to be navigate style)
//
// Revision 1.565  2004/04/29 16:41:44  gregg
// include DERIVED_EID for navigate
//
// Revision 1.564  2004/04/23 19:56:56  dave
// reviewing pending CCE XML creation logic
//
// Revision 1.563  2004/04/15 18:29:27  joan
// add LastOPWGID in EntityItem
//
// Revision 1.562  2004/04/15 17:22:16  joan
// don't want to use cache for flag research
//
// Revision 1.561  2004/04/14 23:20:31  joan
// fix error
//
// Revision 1.560  2004/04/14 22:21:07  joan
// flag research
//
// Revision 1.559  2004/04/14 21:53:14  joan
// initial load
//
// Revision 1.558  2004/04/09 19:37:19  joan
// add duplicate method
//
// Revision 1.557  2004/04/07 22:12:25  gregg
// check for getParentEntityGroup() != null in dereference()
//
// Revision 1.556  2004/04/05 22:19:10  gregg
// ome more grabByEntityID search
//
// Revision 1.555  2004/04/05 20:12:57  gregg
// fix debug
//
// Revision 1.554  2004/04/05 20:05:53  gregg
// debug stmt.
//
// Revision 1.553  2004/04/03 00:13:34  gregg
// compile fix
//
// Revision 1.552  2004/04/03 00:07:46  gregg
// s'more GrabByEntityID
//
// Revision 1.551  2004/04/02 23:38:33  gregg
// introduce gbl1018 for SearchACtionItem Constructor:GrabByEntityID logic
//
// Revision 1.550  2004/04/02 23:31:49  gregg
// more SearchActionItem for GrabByEntityID logic
//
// Revision 1.549  2004/04/02 22:44:30  gregg
// null ptr fix for grabByEntityID
//
// Revision 1.548  2004/04/01 00:32:26  gregg
// SearchActionItem.grabbyEntityID
//
// Revision 1.547  2004/03/31 20:12:43  gregg
// more put logic for derived attribtues
//
// Revision 1.546  2004/03/31 19:00:35  gregg
// change :EID to DERIVED_EID (':' is no bueno in attcode)
//
// Revision 1.545  2004/03/31 17:46:13  gregg
// going for derived entityid attribute
//
// Revision 1.544  2004/03/23 23:51:25  joan
// fix fb on general search
//
// Revision 1.543  2004/01/22 22:07:50  gregg
// moved previous fix out of for loop
//
// Revision 1.542  2004/01/22 21:41:16  gregg
// fix for FB#53576 (set parentActionItem in Extract constructor)
//
// Revision 1.541  2004/01/16 19:50:34  dave
// used cached actionlist for editActionItem (only server cache for now)
//
// Revision 1.540  2004/01/15 01:05:08  dave
// minor syntax
//
// Revision 1.539  2004/01/15 00:57:39  dave
// fixing cached nlsid problem for EL
//
// Revision 1.538  2004/01/15 00:19:51  dave
// making the parent of an EditAction always a Navigate Group
//
// Revision 1.537  2004/01/13 21:54:26  dave
// more parent/profile fixes
//
// Revision 1.536  2004/01/13 00:07:27  dave
// making entitygroup parent after looking for the cached
// entitygroup
//
// Revision 1.535  2004/01/12 21:06:31  dave
// i command you to compile!
//
// Revision 1.534  2004/01/09 22:26:06  dave
// perf changes I
//
// Revision 1.533  2004/01/08 23:05:31  dave
// Trace
//
// Revision 1.532  2004/01/08 22:49:02  dave
// more clean up for ActionTree I
//
// Revision 1.531  2004/01/08 22:39:19  dave
// fixes for ActionTree I
//
// Revision 1.530  2004/01/08 22:21:50  dave
// Lets try to cache some Action Trees
//
// Revision 1.529  2004/01/08 21:22:26  dave
// cleaning up actionList.. does it need a parent?
//
// Revision 1.528  2004/01/08 20:36:46  dave
// syntax
//
// Revision 1.527  2004/01/08 19:58:12  dave
// testing out AL local cache technique
//
// Revision 1.526  2003/12/03 22:05:49  dave
// commit file
//
// Revision 1.525  2003/12/03 21:38:58  dave
// more deferred locking
//
// Revision 1.524  2003/11/13 19:01:13  dave
// reinstituting GBL8006
//
// Revision 1.523  2003/10/30 19:17:14  dave
// added valon effon to dynasearch
//
// Revision 1.522  2003/10/20 23:06:34  dave
// cleaning up key
//
// Revision 1.521  2003/10/15 19:16:16  dave
// more display trickery
//
// Revision 1.520  2003/10/15 18:08:35  dave
// fix to displayable
//
// Revision 1.519  2003/10/15 17:11:59  dave
// submitted changes for ispicklst
//
// Revision 1.518  2003/10/15 16:01:08  dave
// isdisplayable fix
//
// Revision 1.517  2003/10/14 22:39:33  dave
// more null pointer in cache fix
//
// Revision 1.516  2003/10/14 22:18:08  dave
// more caching changes
//
// Revision 1.515  2003/10/14 19:54:26  dave
// attempting to cache NavActionItems as well for those complex
// matrix ve's
//
// Revision 1.514  2003/10/13 22:02:48  dave
// syntax
//
// Revision 1.513  2003/10/13 21:45:25  dave
// fix to extract key cache
//
// Revision 1.512  2003/09/24 16:50:40  joan
// fixes for dynamic search domain
//
// Revision 1.511  2003/09/23 23:53:25  joan
// work on dynamic search
//
// Revision 1.510  2003/09/18 22:16:00  joan
// comment out adjusted code for dynamic search
//
// Revision 1.509  2003/09/17 23:33:52  joan
// add the attribute in SearchAction entity group for Domain
//
// Revision 1.508  2003/09/11 21:05:25  dave
// fixed a null pointer
//
// Revision 1.507  2003/09/10 21:08:48  dave
// o top y
//
// Revision 1.506  2003/09/10 21:00:20  dave
// fixing FlagFilter
//
// Revision 1.505  2003/09/09 22:07:30  dave
// removing profile links in attribute creation
//
// Revision 1.504  2003/09/09 19:58:14  dave
// AutoFilter on Dynamic search
//
// Revision 1.503  2003/09/04 20:00:42  dave
// syntax
//
// Revision 1.502  2003/09/04 19:36:27  dave
// syntax fixes
//
// Revision 1.501  2003/09/04 18:55:12  dave
// adding Enterprise and OPID negativity
//
// Revision 1.500  2003/09/02 22:08:54  dave
// Some syntax
//
// Revision 1.499  2003/09/02 21:57:38  dave
// attempting to make all selected parents available in EditAction Item
//
// Revision 1.498  2003/08/28 16:28:04  joan
// adjust link method to have link option
//
// Revision 1.497  2003/08/27 22:41:11  dave
// minor cleanup
//
// Revision 1.496  2003/08/27 21:51:20  dave
// syntax fix and enacting entitylist caching
//
// Revision 1.495  2003/08/27 21:40:18  dave
// Fixing Null pointer when we implemnt isHomeEnabled
// and deferring the VE lock loading algorythem until
// the lock is executed. (used to be created during Link
// ActionItem contructor)
//
// Revision 1.494  2003/08/25 20:57:34  dave
// clean up on remote di
//
// Revision 1.493  2003/08/21 23:49:58  joan
// work on general search
//
// Revision 1.492  2003/08/21 17:33:11  joan
// fix general search
//
// Revision 1.491  2003/07/16 19:54:34  dave
// syntax
//
// Revision 1.490  2003/07/16 19:38:20  dave
// adding the skipcleanup logic and retain sessionid logic to
// EntityList for Extract
//
// Revision 1.489  2003/06/25 18:43:59  joan
// move changes from v111
//
// Revision 1.488  2003/06/24 23:58:42  dave
// Translation I
//
// Revision 1.487  2003/06/24 23:47:54  dave
// Translation part I
//
// Revision 1.486  2003/06/19 20:56:08  dave
// fixing navlimit capping
//
// Revision 1.485  2003/06/17 19:44:52  joan
// move changes from v111
//
// Revision 1.484  2003/06/13 19:59:34  dave
// adding tasks to navigatable fields
//
// Revision 1.483  2003/06/07 19:16:07  dave
// bumped 1000 to 2000
//
// Revision 1.482  2003/06/06 00:04:18  joan
// move changes from v111
//
// Revision 1.481  2003/06/03 18:40:07  gregg
// setEntityItems() on the ~passed~ NavActionItem in getEntityList() method
//
// Revision 1.480  2003/05/27 20:55:29  dave
// Looking at Where Used
//
// Revision 1.479  2003/05/27 19:58:48  dave
// more Tracking on the trsNavigateTable
//
// Revision 1.478  2003/05/27 19:51:04  dave
// Adding ObjectType to GBL8115
//
// Revision 1.477  2003/05/27 18:31:42  dave
// Rmi clipping and serialization tracking
//
// Revision 1.476  2003/05/15 15:38:50  bala
// 'public' putEntityGroup
//
// Revision 1.475  2003/05/14 20:05:28  dave
// do not auto add the entityitem to the parent group
//
// Revision 1.474  2003/05/13 17:38:46  dave
// simplifying getting the entitylist default values call
//
// Revision 1.473  2003/05/12 20:40:09  dave
// clarifying warning messages
//
// Revision 1.472  2003/05/11 02:54:27  dave
// more clarity on doesHaveParents
//
// Revision 1.471  2003/05/11 02:04:40  dave
// making EANlists bigger
//
// Revision 1.470  2003/05/11 01:22:51  dave
// more getnow and sp cleanup
//
// Revision 1.469  2003/05/10 08:45:38  dave
// de-UIing the Create for back end processing
//
// Revision 1.468  2003/05/10 05:33:30  dave
// optional likes processing
//
// Revision 1.467  2003/05/10 04:50:00  dave
// putting text a higher priority
//
// Revision 1.466  2003/05/09 23:17:34  dave
// more StreamLining
//
// Revision 1.465  2003/05/09 22:40:12  dave
// minor syntax errors
//
// Revision 1.464  2003/05/09 22:33:04  dave
// more cleanup and trace for controlling ui/non ui needs
//
// Revision 1.463  2003/05/09 21:18:00  dave
// introducing the concept of turning off not needed things in
// action item execution
//
// Revision 1.462  2003/05/09 20:42:56  dave
// Trying to comment out action items off an extract
// (we think we do not need them)
//
// Revision 1.461  2003/05/09 19:53:51  dave
// getcacheEntityList needs to use getBlobNow
//
// Revision 1.460  2003/05/09 19:38:40  dave
// making a blobnow
//
// Revision 1.459  2003/05/06 16:35:19  dave
// Display fixes
//
// Revision 1.458  2003/05/06 16:14:58  dave
// more equivelence
//
// Revision 1.457  2003/05/06 00:39:17  dave
// more equivelent
//
// Revision 1.456  2003/05/06 00:23:09  dave
// trace and 31000 >>> 32000 in EANMetaAttribute
//
// Revision 1.455  2003/05/06 00:11:42  dave
// tracer
//
// Revision 1.454  2003/05/05 23:53:25  dave
// equivelent
//
// Revision 1.453  2003/05/05 23:26:36  dave
// hopefully fixing equvalient
//
// Revision 1.452  2003/05/02 15:35:58  dave
// trying to get freeStanding Create to work
//
// Revision 1.451  2003/05/01 18:23:30  dave
// Create StandAlone I
//
// Revision 1.450  2003/05/01 17:49:14  dave
// Allowing relatorless create
//
// Revision 1.449  2003/04/24 18:32:18  dave
// getting rid of traces and system out printlns
//
// Revision 1.448  2003/04/23 22:12:59  dave
// clean up and invokation of setClassifications..
//
// Revision 1.447  2003/04/21 22:35:24  dave
// removing the refreshRRR from the edit call
//
// Revision 1.446  2003/04/15 17:38:58  joan
// add checking for path in NavActionItem
//
// Revision 1.445  2003/04/12 23:15:25  dave
// too many rs.closes() after setting to null
//
// Revision 1.444  2003/04/12 22:56:00  dave
// commit it
//
// Revision 1.443  2003/04/12 22:40:36  dave
// syntax
//
// Revision 1.442  2003/04/12 22:31:41  dave
// clean up and reformatting.
// Search Lite weight II
//
// Revision 1.441  2003/04/11 19:16:29  dave
// syntax fix
//
// Revision 1.440  2003/04/11 18:50:22  dave
// general clean up and understanding.
// first step in making seach based entitygroup for liteweight
// processing
//
// Revision 1.439  2003/04/08 03:37:52  dave
// syntax
//
// Revision 1.438  2003/04/08 03:31:12  dave
// more trace
//
// Revision 1.437  2003/04/02 23:49:24  dave
// handing exception in a different way
//
// Revision 1.436  2003/04/02 23:46:13  dave
// Added middlewareshutdown in progress to contructor
//
// Revision 1.435  2003/04/02 23:34:15  dave
// added refreshQueue to EntityList constructure when
// NavActionItem is DGWORKQUEUE
//
// Revision 1.434  2003/04/02 19:23:37  dave
// removed trace statement
//
// Revision 1.433  2003/04/02 19:16:18  dave
// trace for multiple rows in edit
//
// Revision 1.432  2003/04/02 00:07:09  dave
// sytanx
//
// Revision 1.431  2003/04/01 23:58:38  dave
// reverting to orig passthru logic
//
// Revision 1.430  2003/04/01 22:50:23  dave
// xatyns
//
// Revision 1.429  2003/04/01 22:39:21  dave
// setting the parent to WG right now to see if the
// passthru entities will render in navigate
//
// Revision 1.428  2003/04/01 21:41:03  dave
// minor syntax
//
// Revision 1.427  2003/04/01 21:29:33  dave
// more trace for Nav and EntityList
//
// Revision 1.426  2003/04/01 17:52:19  dave
// missing paren
//
// Revision 1.425  2003/04/01 17:18:35  dave
// implementing NavActionItem tagging II
//
// Revision 1.424  2003/04/01 02:37:50  dave
// minor syntax fix
//
// Revision 1.423  2003/04/01 02:27:36  dave
// setTagInfo Implementation
//
// Revision 1.422  2003/04/01 01:49:03  dave
// closing in on Tagging
//
// Revision 1.421  2003/04/01 01:16:40  dave
// final compile for this effort
//
// Revision 1.420  2003/03/31 23:51:52  dave
// tagging and passthru logic I
//
// Revision 1.419  2003/03/27 23:07:22  dave
// adding some timely commits to free up result sets
//
// Revision 1.418  2003/03/27 19:22:14  dave
// clipping more stuff on the Nav Action Item Send
//
// Revision 1.417  2003/03/19 01:26:50  gregg
// setEntityItems on NavActionItem passed into the static getEntityList method in addition to Constructor.
//
// Revision 1.416  2003/03/17 20:31:00  gregg
// executeAction on NavActionItem
//
// Revision 1.415  2003/03/10 18:16:18  dave
// fixed merge conflict to put back what I had
//
// Revision 1.414  2003/03/10 17:42:25  joan
// resolve conflict
//
// Revision 1.413  2003/03/07 23:26:41  dave
// merge conflict
//
// Revision 1.412  2003/03/07 23:24:10  dave
// typo fix
//
// Revision 1.411  2003/03/07 21:00:50  dave
// making interval used for GBL8116, GBL8114 to
// only pull an interval's worth of data from the Queue
// table to the Navigate table
//
// Revision 1.410  2003/02/21 16:12:00  joan
// adjust entity group to pull out all attributes for search
//
// Revision 1.409  2003/02/06 22:29:02  gregg
// gbl7538 logic
//
// Revision 1.408  2003/02/06 20:47:36  gregg
// convert valfrom result from gbl7520 correct iso timestamp format
//
// Revision 1.407  2003/02/06 02:32:09  gregg
// one more time
//
// Revision 1.406  2003/02/06 02:25:09  gregg
// compile fix
//
// Revision 1.405  2003/02/06 02:13:21  gregg
// slight adjustments to wasModifiedInInterval logic to incorporate 'one-way-valve' on boolean
// (once EntityItem is detected as modified w/in an interval dont change this!!)
//
// Revision 1.404  2003/02/06 00:53:45  gregg
// format strValOn8000 timestamp
//
// Revision 1.403  2003/02/06 00:31:24  gregg
// add strValOn8000 to debug spew
//
// Revision 1.402  2003/02/04 21:13:52  gregg
// calculateEntityItemsForInterval logic
//
// Revision 1.401  2003/02/04 20:41:22  gregg
// In ExtractActionItem constructor: set relator modification date if queuesourced (assumes valon from 8000 records this date)
//
// Revision 1.400  2003/02/04 20:21:54  gregg
// call ei.setLatestDateAttrModIfGreater(..) in popAllAttributeValues method if extract
//
// Revision 1.399  2003/01/21 00:20:35  joan
// adjust link method to test VE lock
//
// Revision 1.398  2003/01/14 22:05:06  joan
// adjust removeLink method
//
// Revision 1.397  2003/01/08 21:44:05  joan
// add getWhereUsedList
//
// Revision 1.396  2002/12/23 23:46:26  joan
// add removeDefaultEntityItem
//
// Revision 1.395  2002/12/20 19:33:28  dave
// syntax
//
// Revision 1.394  2002/12/20 19:19:45  dave
// simple auto flag filter on dyna search
//
// Revision 1.393  2002/12/13 22:57:10  dave
// more fixes to cyntgacx
//
// Revision 1.392  2002/12/13 22:35:14  dave
// syntax
//
// Revision 1.391  2002/12/13 22:22:55  dave
// domain enabled search for dynamic search
//
// Revision 1.390  2002/11/27 18:53:17  dave
// added import statement
//
// Revision 1.389  2002/11/27 17:33:45  dave
// more tree type funtions
//
// Revision 1.388  2002/11/22 21:50:02  dave
// some cleanup
//
// Revision 1.387  2002/11/22 19:21:19  dave
// generalizing step generator
//
// Revision 1.386  2002/11/22 19:04:15  dave
// shin-tax
//
// Revision 1.385  2002/11/22 18:42:27  dave
// more modification
//
// Revision 1.384  2002/11/22 18:39:00  dave
// Extract Action Path
//
// Revision 1.383  2002/11/21 20:58:13  dave
// pull fix
//
// Revision 1.382  2002/11/21 00:20:43  dave
// syntax
//
// Revision 1.381  2002/11/21 00:08:44  dave
// syntax fixes
//
// Revision 1.380  2002/11/21 00:05:31  dave
// Trace fixes
//
// Revision 1.379  2002/11/20 23:57:17  dave
// do not visit the same level twice at the same entity
//
// Revision 1.378  2002/11/20 23:44:26  dave
// cleaner trace
//
// Revision 1.377  2002/11/20 21:13:15  dave
// syntax
//
// Revision 1.376  2002/11/20 20:58:51  dave
// more trace
//
// Revision 1.375  2002/11/20 20:30:51  dave
// CYNTAUGHX
//
// Revision 1.374  2002/11/20 20:22:13  dave
// bug
//
// Revision 1.373  2002/11/20 20:15:25  dave
// more testing
//
// Revision 1.372  2002/11/20 20:11:54  dave
// minor adjustments
//
// Revision 1.371  2002/11/20 19:11:11  dave
// more syntax
//
// Revision 1.370  2002/11/20 18:46:07  dave
// more fixes
//
// Revision 1.369  2002/11/20 18:38:27  dave
// fixing syntax
//
// Revision 1.368  2002/11/20 18:28:59  dave
// syntax fixes
//
// Revision 1.367  2002/11/20 18:18:09  dave
// fancy recursive dump for structure
//
// Revision 1.366  2002/11/20 16:11:54  dave
// added leveling to entitylist and entityitem so we
// can smart search the object for a thread of info
//
// Revision 1.365  2002/11/14 22:08:32  dave
// syntax
//
// Revision 1.364  2002/11/14 22:01:06  dave
// deabled state maching in search context
//
// Revision 1.363  2002/11/07 21:41:17  dave
// syntax fix
//
// Revision 1.362  2002/11/07 21:31:49  dave
// trying to work on edit kludge
//
// Revision 1.361  2002/11/07 17:12:27  dave
// ensuring parents have at least nav info when we do a matrix
//
// Revision 1.360  2002/11/06 22:40:11  gregg
// removing display statements
//
// Revision 1.359  2002/11/06 22:05:04  joan
// fix typo.
//
// Revision 1.358  2002/11/06 21:50:42  dave
// better syntax error messages here
//
// Revision 1.357  2002/11/05 18:29:07  dave
// trying to make attributes show up on parent create
//
// Revision 1.356  2002/11/04 23:08:24  dave
// more fixes for kludge
//
// Revision 1.355  2002/11/04 22:48:41  dave
// null pointer fix
//
// Revision 1.354  2002/11/04 22:29:46  dave
// more fixes for edit kludge
//
// Revision 1.353  2002/11/04 22:12:04  dave
// set force display mode
//
// Revision 1.352  2002/11/04 21:59:26  dave
// trying to force renegade edit
//
// Revision 1.351  2002/11/01 22:41:45  dave
// more EntityOnly Edit tricks
//
// Revision 1.350  2002/11/01 22:27:46  dave
// sliding in entity2type onlys in the Edit /w relator case
//
// Revision 1.349  2002/11/01 19:03:37  joan
// fix null pointer
//
// Revision 1.348  2002/11/01 17:25:30  joan
// fix null pointer in equivalent method
//
// Revision 1.347  2002/10/31 21:43:53  dave
// fix to memory leak
//
// Revision 1.346  2002/10/31 21:35:19  dave
// trapped the memory leak
//
// Revision 1.345  2002/10/31 20:32:07  dave
// more syntax
//
// Revision 1.344  2002/10/31 20:21:57  dave
// more trace statements
//
// Revision 1.343  2002/10/31 19:22:16  dave
// fix on linkchild, syntax fix for memory trap
//
// Revision 1.342  2002/10/31 19:06:01  dave
// more memory watching
//
// Revision 1.341  2002/10/31 18:24:21  dave
// syntax needed a try/catch
//
// Revision 1.340  2002/10/31 18:04:00  dave
// syntax error
//
// Revision 1.339  2002/10/31 17:52:56  dave
// trying to clean up getEntitylist logic (clipping.. etc)
//
// Revision 1.338  2002/10/30 22:57:18  dave
// syntax on import
//
// Revision 1.337  2002/10/30 22:39:13  dave
// more cleanup
//
// Revision 1.336  2002/10/30 22:36:19  dave
// clean up
//
// Revision 1.335  2002/10/30 22:02:33  dave
// added exception throwing to commit
//
// Revision 1.334  2002/10/29 00:02:55  dave
// backing out row commit for 1.1
//
// Revision 1.333  2002/10/28 23:49:14  dave
// attempting the first commit with a row index
//
// Revision 1.332  2002/10/28 23:02:20  dave
// need to include isAssoc when adding to the Cart
//
// Revision 1.331  2002/10/28 22:57:38  dave
// cleaning up addToCart Logic
//
// Revision 1.330  2002/10/28 22:05:30  dave
// including the root's attributes in all extract pulls
//
// Revision 1.329  2002/10/18 21:28:57  roger
// Added comment for POK trouble
//
// Revision 1.328  2002/10/18 20:18:52  joan
// add isMatrixEditable method
//
// Revision 1.327  2002/10/16 21:06:54  dave
// Fix for reduce loging
//
// Revision 1.326  2002/10/14 23:33:16  dave
// More precice debug information in EntityList
//
// Revision 1.325  2002/10/11 20:36:30  dave
// better debug management
//
// Revision 1.324  2002/10/11 00:32:17  dave
// trapping null pointer
//
// Revision 1.323  2002/10/11 00:18:43  dave
// More trace on absent parent
//
// Revision 1.322  2002/10/10 22:04:59  dave
// when pullAttributesTrue.. we need to treat SearchActionItem as an edit
//
// Revision 1.321  2002/10/10 21:32:21  dave
// fixing iStep
//
// Revision 1.320  2002/10/10 21:19:04  dave
// syntax
//
// Revision 1.319  2002/10/10 21:08:40  dave
// syntax fixes
//
// Revision 1.318  2002/10/10 20:57:54  dave
// syntax fix
//
// Revision 1.317  2002/10/10 20:48:30  dave
// minor mods for SearchActionItem and DynaSearch
//
// Revision 1.316  2002/10/10 19:30:11  dave
// setting hooks to catch uplink.. down link fishes
//
// Revision 1.315  2002/10/10 18:54:44  dave
// Class Cast exception fix + isPickList
//
// Revision 1.314  2002/10/10 18:27:29  dave
// paren problem
//
// Revision 1.313  2002/10/10 18:20:48  dave
// Need to catch business rule exception
//
// Revision 1.312  2002/10/10 18:12:47  dave
// Wave fixes II
//
// Revision 1.311  2002/10/10 18:03:47  dave
// Syntax fixes wave i
//
// Revision 1.310  2002/10/10 17:32:49  dave
// syntax clean up
//
// Revision 1.309  2002/10/10 17:13:55  dave
// Localizing parent info on Search Action Item
//
// Revision 1.308  2002/10/10 17:00:27  dave
// Final Search II changes
//
// Revision 1.307  2002/10/09 21:32:56  dave
// added isDynaTable to EANTableWrapper interface
//
// Revision 1.306  2002/10/03 21:49:12  dave
// Found it!
//
// Revision 1.305  2002/10/03 18:14:24  dave
// ruse of eg.AddRow in the CreateActionItem
// constructor of EntityList
//
// Revision 1.304  2002/09/27 17:10:59  dave
// made addRow a boolean
//
// Revision 1.303  2002/09/26 23:39:32  joan
// remove System.out
//
// Revision 1.302  2002/09/26 22:37:58  dave
// syntax errors
//
// Revision 1.301  2002/09/26 22:27:55  dave
// fixing add row , and CreateActionItem in entitylist constructor
// to use the same seed for counting  backwards
//
// Revision 1.300  2002/09/26 21:05:48  joan
// fix null pointer
//
// Revision 1.299  2002/09/26 00:13:18  dave
// template  fix to include == 0
//
// Revision 1.298  2002/09/25 17:37:14  joan
// remove System.out
//
// Revision 1.297  2002/09/20 21:25:12  dave
// syntax fixing for classification wave II
//
// Revision 1.296  2002/09/20 21:14:49  dave
// more classifications
//
// Revision 1.295  2002/09/17 23:32:35  dave
// syntax
//
// Revision 1.294  2002/09/17 23:24:34  dave
// ridding null pointers from the system
//
// Revision 1.293  2002/09/17 22:53:17  dave
// fix to ensure entity2type loads
//
// Revision 1.292  2002/09/17 21:38:24  dave
// fix to syntax
//
// Revision 1.291  2002/09/17 21:28:20  dave
// trace and compare for EditAction in EntityList
//
// Revision 1.290  2002/09/17 20:25:35  dave
// syntax
//
// Revision 1.289  2002/09/17 20:19:03  dave
// throwing exception
//
// Revision 1.288  2002/09/17 20:17:54  dave
// syntax fix
//
// Revision 1.287  2002/09/17 20:08:56  dave
// insuring we clip properly for edit and we must make sure we
// are clipping
//
// Revision 1.286  2002/09/17 19:39:27  dave
// more syntax
//
// Revision 1.285  2002/09/17 19:14:19  dave
// forcing displayable to true
//
// Revision 1.284  2002/09/16 22:17:41  dave
// syntax
//
// Revision 1.283  2002/09/16 22:08:50  dave
// syntax
//
// Revision 1.282  2002/09/16 21:57:24  dave
// more EditActionItem in EntityList constructor
//
// Revision 1.281  2002/09/16 21:15:02  dave
// more fixes for true Entity2Type editing
//
// Revision 1.280  2002/09/16 20:55:38  dave
// syntax
//
// Revision 1.279  2002/09/16 20:47:14  dave
// Attempting an entity2type only edit
//
// Revision 1.278  2002/09/16 16:47:03  joan
// fix null pointer
//
// Revision 1.277  2002/09/12 16:37:54  dave
// adding multi valued flag to navigate stuff
//
// Revision 1.276  2002/09/11 23:14:09  joan
// call refreshRequired for entityid < 0
//
// Revision 1.275  2002/09/11 21:51:23  joan
// debug refreshRequired
//
// Revision 1.274  2002/09/11 20:51:50  dave
// try  .. catch ... in EntityItem create block
//
// Revision 1.273  2002/09/11 20:36:49  dave
// EntityItem sig change
//
// Revision 1.272  2002/09/11 20:19:37  dave
// Attempting out first clipShift to for ReportActionItems
//
// Revision 1.271  2002/09/10 17:52:51  joan
// debug null pointer
//
// Revision 1.270  2002/09/10 15:39:16  joan
// debug null pointer
//
// Revision 1.269  2002/09/09 17:37:37  dave
// gekey case problem
//
// Revision 1.268  2002/09/09 17:15:12  dave
// tracing for Edit to ensure nothing bigger is being sent up
//
// Revision 1.267  2002/09/09 16:08:59  dave
// syntax change
//
// Revision 1.266  2002/09/09 15:59:24  dave
// removing the requirement for needing a meta link during edit
//
// Revision 1.265  2002/09/04 16:19:24  joan
// fix null pointer
//
// Revision 1.264  2002/09/03 19:46:57  dave
// adding commits to speed the navigation table up abit
//
// Revision 1.263  2002/08/30 22:42:51  joan
// debug getEntityList ExtractActionItem
//
// Revision 1.262  2002/08/28 16:07:27  joan
// debug getID
//
// Revision 1.261  2002/08/26 19:01:41  dave
// first attempt at getting to the dg work queue
//
// Revision 1.260  2002/08/26 17:51:18  joan
// debug Create
//
// Revision 1.259  2002/08/26 16:46:05  joan
// debug CreateActionItem
//
// Revision 1.258  2002/08/26 15:52:43  joan
// debug  CreateActionItem
//
// Revision 1.257  2002/08/23 22:45:06  dave
// image rep of 8002 from 1.0.1
//
// Revision 1.256  2002/08/12 18:16:06  joan
// add code to pull all attributes in SearchActionItem
//
// Revision 1.255  2002/08/02 01:20:42  gregg
// if queuesource -> reset target entitytype
//
// Revision 1.254  2002/08/02 00:58:42  gregg
// debugging - removed 8105 for Extract getEntityList
//
// Revision 1.253  2002/07/31 00:02:39  joan
// debug error
//
// Revision 1.252  2002/07/30 18:21:18  dave
// syntax scoping issue
//
// Revision 1.251  2002/07/30 18:05:46  dave
// QUEUE II logic
//
// Revision 1.250  2002/07/26 20:06:13  joan
// debug
//
// Revision 1.249  2002/07/25 22:23:15  gregg
// setActionList in EntityList( ... ExtractActionItem ....) constructor - this was missing b4
//
// Revision 1.248  2002/07/20 00:08:15  joan
// debug
//
// Revision 1.247  2002/07/19 22:24:39  joan
// fix locklist
//
// Revision 1.246  2002/07/16 22:25:13  joan
// work on action item
//
// Revision 1.245  2002/07/16 15:38:20  joan
// working on method to return the array of actionitems
//
// Revision 1.244  2002/07/08 17:53:42  joan
// fix link method
//
// Revision 1.243  2002/07/08 16:05:29  joan
// fix link method
//
// Revision 1.242  2002/06/27 22:51:51  joan
// fix equivalent method
//
// Revision 1.241  2002/06/27 21:50:53  joan
// fix equivalent method
//
// Revision 1.240  2002/06/27 21:12:24  joan
// fix equivalent method
//
// Revision 1.239  2002/06/27 17:31:46  joan
// debug
//
// Revision 1.238  2002/06/26 22:27:04  joan
// fixing bugs
//
// Revision 1.237  2002/06/25 20:36:08  joan
// add create method for whereused
//
// Revision 1.236  2002/06/25 17:49:37  joan
// add link and removeLink methods
//
// Revision 1.235  2002/06/21 21:21:56  joan
// fix null pointer
//
// Revision 1.234  2002/06/21 18:05:13  joan
// fix equivalent method
//
// Revision 1.233  2002/06/19 15:52:19  joan
// work on add column in matrix
//
// Revision 1.232  2002/06/17 23:53:47  joan
// add addColumn method
//
// Revision 1.231  2002/06/07 00:44:03  gregg
// more syntax
//
// Revision 1.230  2002/06/07 00:43:27  gregg
// syntax
//
// Revision 1.229  2002/06/07 00:33:48  gregg
// pullTargetEntitiesOnly Logic for getEntityList(Extract...)
//
// Revision 1.228  2002/06/06 22:49:05  gregg
// in getEntityList(Extract....) -> skip attribute pull if specified
//
// Revision 1.227  2002/06/05 22:18:20  joan
// work on put and rollback
//
// Revision 1.226  2002/06/05 16:28:49  joan
// add getMatrixValue method
//
// Revision 1.225  2002/05/30 22:49:53  joan
// throw MiddlewareBusinessRuleException when committing
//
// Revision 1.224  2002/05/29 23:44:36  joan
// work on addToCart
//
// Revision 1.223  2002/05/29 21:45:40  joan
// working on addToCart
//
// Revision 1.222  2002/05/28 22:26:00  joan
// add equivalent method to check whether entity items is in entity list
//
// Revision 1.221  2002/05/21 23:02:51  joan
// add putCartEntityGroup method
//
// Revision 1.220  2002/05/21 16:37:42  joan
// add to addToCart method the logic to choose not to get downlink entity
//
// Revision 1.219  2002/05/20 22:49:06  dave
// syntax fix
//
// Revision 1.218  2002/05/20 22:31:12  dave
// new edit/create defaults in action item
//
// Revision 1.217  2002/05/20 18:47:58  joan
// when adding new row and creating, set up the parent entity item with entityid = -1
// when parent entitygroup has more than one entity item,
// and throw exception when commit
//
// Revision 1.216  2002/05/17 18:37:10  dave
// buffer increase
//
// Revision 1.215  2002/05/16 20:13:02  dave
// attempting a buffered read on cache
//
// Revision 1.214  2002/05/16 19:51:21  dave
// trace for cache and uncache
//
// Revision 1.213  2002/05/16 18:36:04  dave
// uncomressing compress
//
// Revision 1.212  2002/05/16 18:05:24  dave
// reorder of the closes
//
// Revision 1.211  2002/05/16 17:44:28  dave
// syntax
//
// Revision 1.210  2002/05/16 17:35:37  dave
// introduced compressed cache
//
// Revision 1.209  2002/05/16 16:59:23  joan
// fix compile error
//
// Revision 1.208  2002/05/16 15:56:03  dave
// extra logging statements
//
// Revision 1.207  2002/05/16 00:45:04  dave
// simplified key for entitylist
//
// Revision 1.206  2002/05/16 00:26:31  gregg
// debug stmt "cannot find home for..."
//
// Revision 1.205  2002/05/16 00:25:09  dave
// syntax
//
// Revision 1.204  2002/05/16 00:23:51  dave
// syntax fix
//
// Revision 1.203  2002/05/16 00:13:15  dave
// caching entitygrouplist for extract
//
// Revision 1.202  2002/05/15 15:35:49  joan
// fix compile error
//
// Revision 1.201  2002/05/15 00:56:23  dave
// fix to remove domain control off from 8012
//
// Revision 1.200  2002/05/14 23:10:44  dave
// changes for joan and my shift right
//
// Revision 1.199  2002/05/08 19:56:41  dave
// attempting to throw the BusinessRuleException on Commit
//
// Revision 1.198  2002/05/03 18:37:09  gregg
// replaced gbl1015 w/ gbl8115, gbl1005 w/ gbl8105
//
// Revision 1.197  2002/05/03 16:36:07  dave
// added isExtractAction for popAllAttributes so Parent
// EntityItems get attributes
//
// Revision 1.196  2002/05/01 23:18:40  gregg
// debug the correct SP in popAllAttributeValues
//
// Revision 1.195  2002/05/01 20:57:15  dave
// fixing alittle commenting and debugging
//
// Revision 1.194  2002/04/29 16:01:26  joan
// add code for blob attribute
//
// Revision 1.193  2002/04/27 00:01:04  joan
// fixing error
//
// Revision 1.192  2002/04/26 23:58:52  joan
// add code in EntityList for blob attribute
//
// Revision 1.191  2002/04/25 21:18:42  dave
// attempting to implement the go home function in navigate
//
// Revision 1.190  2002/04/24 18:04:37  joan
// add removeRow method
//
// Revision 1.189  2002/04/23 23:01:10  gregg
// rename boolean param in popAllAttributeValues -> _bExtract to make more sense
//
// Revision 1.188  2002/04/22 17:28:21  gregg
// For Constructor using ExtractActionItems - use gbl7520 to grab atts for all NLS's.
//
// Revision 1.187  2002/04/19 21:16:22  dave
// adding LongText and XMLAttribute pulls
//
// Revision 1.186  2002/04/11 18:15:09  dave
// Trace statement adjustment and null pointer fix
//
// Revision 1.185  2002/04/11 00:55:49  dave
// syntax
//
// Revision 1.184  2002/04/11 00:48:50  dave
// minor logic fix to clip method
//
// Revision 1.183  2002/04/11 00:30:04  dave
// syntax fix
//
// Revision 1.182  2002/04/11 00:20:44  dave
// syntax
//
// Revision 1.181  2002/04/11 00:13:18  dave
// attempt at plugging leaks in object when sending up to RMI
//
// Revision 1.180  2002/04/10 20:44:15  dave
// more closes on io objects
//
// Revision 1.179  2002/04/08 23:10:59  dave
// put back the shift right logic on the create action item
//
// Revision 1.178  2002/04/08 22:32:24  dave
// removal of System.out files
//
// Revision 1.177  2002/04/08 22:30:00  dave
// fix on CreateEntity Action Item (putting wrong thing in wrong group
//
// Revision 1.176  2002/04/08 20:47:15  dave
// more trace statements
//
// Revision 1.175  2002/04/08 19:43:29  dave
// tracing create action item problem
//
// Revision 1.174  2002/04/05 20:16:55  dave
// syntax fixes
//
// Revision 1.173  2002/04/05 20:06:01  dave
// syntax errors fixed
//
// Revision 1.172  2002/04/05 18:43:37  dave
// first attempt at the extract action item
//
// Revision 1.171  2002/04/04 18:32:16  dave
// added more refreshReset stuff
//
// Revision 1.170  2002/04/04 00:29:05  dave
// fix isFormsCapable
//
// Revision 1.169  2002/04/03 23:10:24  dave
// system.out.println removal
//
// Revision 1.168  2002/04/03 18:38:09  dave
// clearing out attributes on the edit..
//
// Revision 1.167  2002/04/02 23:58:29  dave
// trace statements
//
// Revision 1.166  2002/04/02 21:25:49  dave
// added hasChanges()
//
// Revision 1.165  2002/04/02 18:17:40  dave
// more refreshRestriction
//
// Revision 1.164  2002/03/27 22:34:21  dave
// Row Selectable Table row Add logic. First attempt
//
// Revision 1.163  2002/03/26 22:57:21  dave
// write the check dependencies code to turn off flags
// that no longer should be turned on
//
// Revision 1.162  2002/03/26 19:25:31  dave
// more changes
//
// Revision 1.161  2002/03/26 19:18:31  dave
// fixes and patches
//
// Revision 1.160  2002/03/26 19:03:52  dave
// trace and fix for bad parent
//
// Revision 1.159  2002/03/26 18:43:34  dave
// data integrity check for hierarchical structure management
//
// Revision 1.158  2002/03/26 18:24:57  dave
// more trace statements
//
// Revision 1.157  2002/03/26 17:47:40  dave
// more trace
//
// Revision 1.156  2002/03/22 21:26:55  dave
// more syntax cleanup
//
// Revision 1.155  2002/03/20 23:19:30  dave
// Fixing SP7006 and other to use trsEntity Table
//
// Revision 1.154  2002/03/19 19:40:47  dave
// make sure we refresh defaults after every put. to
// ensure default values are not left blank
//
// Revision 1.153  2002/03/19 05:08:50  dave
// syntax
//
// Revision 1.152  2002/03/19 04:57:44  dave
// first pass at context sensitive defaults
//
// Revision 1.151  2002/03/09 00:45:35  dave
// syntax fix
//
// Revision 1.150  2002/03/09 00:34:37  dave
// fix to pick up parent values
//
// Revision 1.149  2002/03/08 22:56:37  dave
// fixing null pointer and SP7007
//
// Revision 1.148  2002/03/08 21:44:45  dave
// added a defaultindex for default values testing
//
// Revision 1.147  2002/03/08 21:11:51  dave
// syntax fix
//
// Revision 1.146  2002/03/08 20:21:18  dave
// more fixes to work on edit
//
// Revision 1.145  2002/03/08 20:06:36  dave
// fixing class cast exception
//
// Revision 1.144  2002/03/08 19:07:37  dave
// fixes to sinfull tax
//
// Revision 1.143  2002/03/08 18:58:44  dave
// first attempt at bringing edit online
//
// Revision 1.142  2002/03/08 18:46:56  dave
// first attempt at pulling data for edit (text and flags only)
//
// Revision 1.141  2002/03/08 17:28:29  dave
// minor display fixes
//
// Revision 1.140  2002/03/08 02:40:01  dave
// adding to Nav Instantiate for EntityList for test :enforceWorkGroupDomain(_db,_prof);
//
// Revision 1.139  2002/03/08 02:33:23  dave
// syntax
//
// Revision 1.138  2002/03/08 02:26:38  dave
// syntax
//
// Revision 1.137  2002/03/08 02:03:30  dave
// workgroup domain control on edit/create
//
// Revision 1.136  2002/03/08 00:40:31  dave
// new sp for flag retardation
//
// Revision 1.135  2002/03/08 00:27:47  dave
// more fixes
//
// Revision 1.134  2002/03/08 00:16:51  dave
// changes
//
// Revision 1.133  2002/03/07 23:32:35  dave
// missing {}
//
// Revision 1.132  2002/03/07 23:25:29  dave
// new sp for edit (loading trsTable with all kinds of information)
//
// Revision 1.131  2002/03/07 20:12:04  dave
// more null pointer for action item parent in actionlist
//
// Revision 1.130  2002/03/07 19:01:58  dave
// Fix to ActionItem object
//
// Revision 1.129  2002/03/07 18:46:05  dave
// more fixes to popAttribute... method
//
// Revision 1.128  2002/03/07 18:35:53  dave
// null pointer fix. you do not have a parentEntityGroup
// in the case of a search
//
// Revision 1.127  2002/03/07 16:57:24  dave
// NullPointerException
//
// Revision 1.126  2002/03/06 19:06:33  dave
// commenting problems
//
// Revision 1.125  2002/03/06 18:47:44  dave
// uncommented out the entitylist statics for getEntityList
//
// Revision 1.124  2002/03/06 18:16:33  dave
// adding the Search Action Item
//
// Revision 1.123  2002/03/06 17:54:29  joan
// move static methods from EntityList to EANUtility
//
// Revision 1.122  2002/03/06 01:12:17  dave
// fixes for varname
//
// Revision 1.121  2002/03/06 00:54:14  dave
// more syntax for Search API
//
// Revision 1.120  2002/03/06 00:46:01  dave
// more syntax fixes
//
// Revision 1.119  2002/03/06 00:38:06  dave
// fixed the var missname
//
// Revision 1.118  2002/03/05 23:53:45  dave
// Rounding out the SearchActionItem
//
// Revision 1.117  2002/03/05 23:08:34  dave
// first pass at the new Search Action Item function for EntityList
//
// Revision 1.116  2002/03/05 18:52:34  joan
// move the link method from EntityList to EANUtility
//
// Revision 1.115  2002/03/05 17:21:41  dave
// minor fixes
//
// Revision 1.114  2002/03/05 17:04:50  dave
// added methods to EntityList for Create
//
// Revision 1.113  2002/03/05 04:35:49  dave
// syntax
//
// Revision 1.112  2002/03/05 04:25:25  dave
// paren problem
//
// Revision 1.111  2002/03/05 04:17:44  dave
// trace and debug fixes
//
// Revision 1.110  2002/03/05 03:09:28  dave
// checking in the create issue
//
// Revision 1.109  2002/03/05 01:43:45  dave
// fixing more throws logic
//
// Revision 1.108  2002/03/05 01:31:16  dave
// more syntax fixes
//
// Revision 1.107  2002/03/05 01:18:06  dave
// more syntax
//
// Revision 1.106  2002/03/04 23:35:02  dave
// numerous fixes
//
// Revision 1.105  2002/03/04 23:19:12  dave
// working on the createActionItem and entitylist constructor
//
// Revision 1.104  2002/03/04 17:29:51  joan
// fix linkEntityItems method, look down on child entity to get
// the right match
//
// Revision 1.103  2002/03/01 23:06:18  dave
// missing getKey on entityGroup
//
// Revision 1.102  2002/03/01 22:10:02  dave
// casting problems in NavActionItem
//
// Revision 1.101  2002/03/01 22:03:34  dave
// more NAVActionItem fixing
//
// Revision 1.100  2002/03/01 20:57:58  dave
// more fixes to SP's and tweeking to how assocs are
// handled in entitygroups
//
// Revision 1.99  2002/03/01 20:03:06  dave
// typo on cast
//
// Revision 1.98  2002/03/01 19:31:13  dave
// syntax fixes and display corrections
//
// Revision 1.97  2002/03/01 19:21:57  dave
// syntax for action item changes
//
// Revision 1.96  2002/03/01 19:00:53  dave
// merged conflicts
//
// Revision 1.95  2002/02/28 19:36:21  dave
// fix to syntax
//
// Revision 1.94  2002/02/28 19:30:15  dave
// simpified the Pulling of data for Attributes
//
// Revision 1.93  2002/02/26 21:44:00  dave
// ensuring I am setting the rs = null after a close
//
// Revision 1.92  2002/02/26 17:47:37  dave
// more syntax
//
// Revision 1.91  2002/02/26 17:34:52  dave
// syntax fixes
//
// Revision 1.90  2002/02/26 17:25:02  dave
// merging the new link into the mix
//
// Revision 1.89  2002/02/22 18:35:55  dave
// fixes to Syntax
//
// Revision 1.88  2002/02/22 18:28:57  dave
// more syntax
//
// Revision 1.87  2002/02/22 18:16:18  dave
// more syntax fixes
//
// Revision 1.86  2002/02/22 18:03:15  dave
// more syntax fixes in import includes
//
// Revision 1.85  2002/02/22 17:51:58  dave
// more syntax fixes
//
// Revision 1.84  2002/02/22 17:44:57  dave
// syntax fixes for linking
//
// Revision 1.83  2002/02/22 17:35:11  dave
// added EANObject as in interface
//
// Revision 1.82  2002/02/21 21:57:30  dave
// generalization on the link function
//
// Revision 1.81  2002/02/21 21:04:32  dave
// more fixes
//
// Revision 1.80  2002/02/21 20:46:37  dave
// syntax
//
// Revision 1.79  2002/02/21 20:31:04  dave
// more fixes to syntax
//
// Revision 1.78  2002/02/21 19:34:21  dave
// syntax fixes
//
// Revision 1.77  2002/02/21 19:26:13  dave
// added Link to static entityList
//
// Revision 1.76  2002/02/20 21:51:54  dave
// redundant egParent defined
//
// Revision 1.75  2002/02/20 21:45:46  dave
// a bunch of fixes for link and entitygroup visibility
//
// Revision 1.74  2002/02/20 21:19:35  dave
// cleaned up parententitygroup to ensure we do not have any
// entity items ending up in both the parentgroup and a generic
// entitygroup
//
// Revision 1.73  2002/02/20 00:11:06  dave
// more fixes
//
// Revision 1.72  2002/02/19 23:31:31  dave
// null pointer fix.. use the
//
// Revision 1.71  2002/02/19 22:02:01  dave
// more fixes to add cart
//
// Revision 1.70  2002/02/19 21:41:53  dave
// more fixable
//
// Revision 1.69  2002/02/19 20:20:36  dave
// more engine encapsulation for the database side
//
// Revision 1.68  2002/02/19 19:58:13  dave
// more fixes
//
// Revision 1.67  2002/02/19 19:51:07  dave
// more static errors
//
// Revision 1.66  2002/02/19 19:35:18  dave
// more Static getEntityList stuff
//
// Revision 1.65  2002/02/19 19:19:55  dave
// Beginnings of objsorbing the nav engine into Static Section of EntityList
//
// Revision 1.64  2002/02/19 18:57:23  dave
// fixes equals and added system.out
//
// Revision 1.63  2002/02/19 18:46:55  dave
// syntax fix
//
// Revision 1.62  2002/02/19 18:28:59  dave
// added transfer Attributes function
//
// Revision 1.61  2002/02/18 22:23:06  dave
// more exception catching
//
// Revision 1.60  2002/02/18 22:15:03  dave
// bracketing problem
//
// Revision 1.59  2002/02/18 22:09:01  dave
// more exception checking
//
// Revision 1.58  2002/02/18 21:58:41  dave
// more remote exception handling
//
// Revision 1.57  2002/02/18 21:47:12  dave
// added more exceptions to addcart
//
// Revision 1.56  2002/02/18 20:31:17  dave
// syntax.. missing return value
//
// Revision 1.55  2002/02/18 19:39:12  dave
// kicking back EntityGroup used in addToCartLogic
//
// Revision 1.54  2002/02/18 19:06:48  dave
// ensuring the entityitem's and metaattributes's parents are the entity group
//
// Revision 1.53  2002/02/18 19:01:49  dave
// more syntax error fixes
//
// Revision 1.52  2002/02/18 18:47:15  dave
// more fixes
//
// Revision 1.51  2002/02/18 18:42:17  dave
// adding cart methods
//
// Revision 1.50  2002/02/18 17:39:44  dave
// more fixes
//
// Revision 1.49  2002/02/18 17:25:23  dave
// more function add for 1.1
//
// Revision 1.48  2002/02/15 21:22:24  dave
// fixing null pointer for the actionGroupBinder
//
// Revision 1.47  2002/02/15 20:13:52  dave
// changed getProfile to look to parent for setting if parent exists
//
// Revision 1.46  2002/02/15 19:16:36  dave
// added getKey
//
// Revision 1.45  2002/02/15 18:56:15  dave
// adding more get active entitygroup index stuff
//
// Revision 1.44  2002/02/15 18:18:31  dave
// more fixes for EAN  table structrure
//
// Revision 1.43  2002/02/15 18:06:52  dave
// expanded EAN Table structures
//
// Revision 1.42  2002/02/15 00:09:04  dave
// fixed bug
//
// Revision 1.41  2002/02/15 00:00:31  dave
// added entitygroup tracker and fixed a boudry  text in EANList
//
// Revision 1.40  2002/02/13 19:10:37  dave
// contant fix
//
// Revision 1.39  2002/02/13 19:02:34  dave
// syntax error fix
//
// Revision 1.38  2002/02/13 18:53:47  dave
// first attempt at a table model for the entitygroups in the list
//
// Revision 1.37  2002/02/12 23:35:36  dave
// added purpose to the NavActionItem
//
// Revision 1.36  2002/02/12 22:47:01  dave
// more fixes
//
// Revision 1.35  2002/02/12 22:28:18  dave
// adding status attribute object
//
// Revision 1.34  2002/02/12 21:59:29  dave
// more syntax fixes
//
// Revision 1.32  2002/02/12 21:01:58  dave
// added toString methods for diplay help
//
// Revision 1.31  2002/02/12 18:51:44  dave
// more changes to dump
//
// Revision 1.30  2002/02/12 18:10:23  dave
// more changes
//
// Revision 1.29  2002/02/12 17:14:36  dave
// more dump fixes
//
// Revision 1.28  2002/02/11 18:33:29  dave
// more dump writing
//
// Revision 1.27  2002/02/11 08:46:47  dave
// more fixes to dump and syntax
//
// Revision 1.26  2002/02/11 08:36:43  dave
// more syntax
//
// Revision 1.25  2002/02/11 08:24:52  dave
// more fixes for better dump and debug
//
// Revision 1.24  2002/02/11 08:13:33  dave
// expanding the dump statements to support debugging
//
// Revision 1.23  2002/02/11 07:55:16  dave
// more fixes
//
// Revision 1.22  2002/02/11 07:43:34  dave
// more fixes
//
// Revision 1.21  2002/02/11 07:35:08  dave
// adding data side
//
// Revision 1.20  2002/02/11 07:23:09  dave
// new objects to commit
//
// Revision 1.19  2002/02/11 06:41:26  dave
// fix to include the WG EntityItem in the EntityGroup
//
// Revision 1.18  2002/02/11 05:36:26  dave
// changed OPWG to WG on EntryPoint
//
// Revision 1.17  2002/02/10 01:13:03  dave
// more fixes
//
// Revision 1.16  2002/02/10 01:03:28  dave
// mass sp fixes for compile
//
// Revision 1.15  2002/02/10 00:22:02  dave
// more fixes
//
// Revision 1.14  2002/02/10 00:13:22  dave
// constructor null pointer fix
//
// Revision 1.13  2002/02/09 23:19:10  dave
// test into entry points
//
// Revision 1.12  2002/02/09 22:09:24  dave
// Abstracted the Action Item in the Action List
//
// Revision 1.11  2002/02/09 21:39:34  dave
// syntax fixes
//
// Revision 1.10  2002/02/06 16:39:44  dave
// commented out attribute level information for now
//
// Revision 1.9  2002/02/06 16:18:28  dave
// more syntax
//
// Revision 1.8  2002/02/06 15:26:29  dave
// more syntax
//
// Revision 1.7  2002/02/06 15:23:07  dave
// more fixes to sytax
//
// Revision 1.6  2002/02/06 00:56:12  dave
// more base changes
//
// Revision 1.5  2002/02/06 00:42:39  dave
// more fixes to base code
//
// Revision 1.4  2002/02/06 00:02:56  dave
// more syntax fixes
//
// Revision 1.3  2002/02/05 17:50:13  dave
// adjusted import statements
//
// Revision 1.2  2002/02/05 16:39:14  dave
// more expansion of abstract model
//
// Revision 1.1  2002/02/05 00:11:57  dave
// more structure
//
//
package COM.ibm.eannounce.objects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Vector;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.T;
import COM.ibm.opicmpdh.objects.Blob;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.NLSItem;
import COM.ibm.opicmpdh.transactions.OPICMBlobValue;
import COM.ibm.opicmpdh.transactions.BluePageEntry;

// Exceptions
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.SearchExceedsLimitException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

import java.sql.SQLException;
import java.rmi.RemoteException;
import COM.ibm.opicmpdh.middleware.LockException;
import javax.mail.internet.InternetAddress;

import COM.ibm.opicmpdh.middleware.MiddlewareServerProperties;

/**
 * This Object it the top level container for all e-announce data objects
 *
 * @author     davidbig
 * @created    April 11, 2003
 */
public class EntityList extends EANMetaEntity implements EANTableWrapper {
  
    /**
     * @serial
     */
    final static long serialVersionUID = 1L;

    // Here are the things tracked at this level for Parent Information
    private EntityGroup m_egParent = null;
    //private EntityGroup m_egTag = null;
    private EntityItem m_eiTag = null;
    private EANActionItem m_aiParent = null;
    private EANList m_elDefaultEntityItems = new EANList();
    private ActionList m_al = null;
    private int m_iActiveEntityGroup = -1;
    private int m_iExtractSessionID = -1;
    private String executionDTS=null;

    /**
     * FIELD
     */
    //public final static int LINK_MOVE = 0;
    /**
     * FIELD
     */
    //public final static int LINK_COPY = 1;
    /**
     * FIELD
     */
    //public final static int LINK_DEFAULT = 2;
    private boolean m_bParentDisplayable = false;

    private final static char QM = '?';
    private final static char UL = '_';
    private final static char STAR = '*';
    private final static char PER = '%';

    /**
     * Main method which performs a simple test of this class
     *
     * @param  arg  Description of the Parameter
     */
    public static void main(String arg[]) {
    }

    /*
     *  This is used to create a local cart for any client.
     *  It is basically an empty Shell
     */
    /**
     *Constructor for the EntityList object
     *
     * @param  _prof                           Description of the Parameter
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public EntityList(Profile _prof) throws MiddlewareRequestException {
        super(null, _prof, "Cart");
        setParentActionItem(new NavActionItem(this, _prof, "Cart"));
    }

    /*
     *  This constructor represents how you perform a Navigation
     *  When you first enter the System with just a Database Object
     *  and a profile
     *  It will return an entity list that contains the WorkGroup Entity for the passed profile
     */
    /**
     *Constructor for the EntityList object
     *
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public EntityList(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException {
        this(_db, _prof, new NavActionItem(null, _db, _prof, "EntryPoint"), null, "WG", true);
    }

    /**
     * New constructor to kick back an entity list filled with the VE's who's root entities have been
     * touched by speicific transID's
     *
     * @param _db
     * @param _prof
     * @param _iTranID
     * @param _ai
     * @param _strTargetEntityType
     * @param _strStartDate
     * @param _strEndDate
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public EntityList(Database _db, Profile _prof, int _iTranID, ExtractActionItem _ai, String _strTargetEntityType,
                      String _strStartDate, String _strEndDate) throws SQLException, MiddlewareException,
        MiddlewareRequestException {
        super(null, _prof, _ai.getKey() + _strTargetEntityType);
        
        //attempt to use less memory, hang onto strings already found
        java.util.Hashtable memTbl = new java.util.Hashtable();
        
        setParentEntityGroup(new EntityGroup(this, _db, null, _strTargetEntityType, _ai.getPurpose(), false,memTbl));

        int iSessionID = 0;
        ReturnStatus returnStatus = new ReturnStatus( -1);

        try {
            ReturnDataResultSet rdrs = null;
            ResultSet rs = null;
            String strTargetEntityType = _strTargetEntityType;
            String strEnterprise = getProfile().getEnterprise();
            String strValOn = getProfile().getValOn();
            String strEffOn = getProfile().getEffOn();
            String strRoleCode = getProfile().getRoleCode();
            String strActionItemKey = _ai.getActionItemKey();
            String strStartDate = _strStartDate;
            String strEndDate = _strEndDate;

            int iNLSID = getProfile().getReadLanguage().getNLSID();
            int iTranID = _iTranID;

            // Lets get the parent back for future use
            EntityGroup egRoot = getParentEntityGroup();
            EntityGroup egParent = egRoot;
            EANList eltmp = getCachedEntityGroupList(_db, _ai, iNLSID,memTbl);

            iSessionID = _db.getNewSessionID();
            setExtractSessionID(iSessionID);

            //
            //  Checking for a Cached EntityGroup list so we do not have to load each one individually
            //
            if (eltmp != null) {
                setEntityGroup(eltmp);
            }
            else {
               	//RCQ00210066-WI 
            	DatePackage dp = new DatePackage(_db);
            	boolean success = false;
            	
                EntityGroup eg = null;
                // Retrieve the Action Class and the Action Description.
                try {
                    //RCQ00210066-WI rs = _db.callGBL7004(returnStatus, strEnterprise, strRoleCode, strActionItemKey, strValOn, strEffOn);
                    rs = _db.callGBL7004(returnStatus, strEnterprise, strRoleCode, strActionItemKey, dp.getEndOfDay(), dp.getEndOfDay());
                    rdrs = new ReturnDataResultSet(rs);
                    success = true;
                }
                finally {
                	if (rs!=null){
                    	try{
                			rs.close();
                		} catch (SQLException x) {
                			_db.debug(D.EBUG_DETAIL, "EntityList: "+getKey()+" ERROR failure closing ResultSet "+x);
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

                setParentActionItem(_ai);

                for (int i = 0; i < rdrs.size(); i++) {
                    String strEntityType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 0));
                    _db.debug(D.EBUG_SPEW, "gbl7004:answer:" + strEntityType + ":");
                    eg = getEntityGroup(strEntityType);
                    if (eg == null) {
                        eg = new EntityGroup(this, _db, getProfile(), strEntityType, _ai.getPurpose(), false, memTbl);
                        putEntityGroup(eg);
                        if (eg.getKey().equals(egRoot.getKey())) {
                            eg.setParentLike(true);
                        }
                    }
                }
                setParentActionItem(null);
                putCachedEntityGroupList(_db, _ai, iNLSID);
            }

            setParentActionItem(_ai);
            getParentActionItem();

            _db.test(getParentEntityGroup() != null, "ParentEntityGroup is null");

            try {
                rs = _db.callGBL8103(returnStatus, iSessionID, iTranID, strEnterprise, strTargetEntityType, strStartDate,
                                     strEndDate);
                rdrs = new ReturnDataResultSet(rs);
            }
            finally {
				if (rs !=null){
                	rs.close();
                	rs = null;
				}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            _db.debug(D.EBUG_DETAIL, "gbl8103:recordcount:" + rdrs.size());

            for (int i = 0; i < rdrs.size(); i++) {
                EntityItem eiRoot = null;
                int iRootID = rdrs.getColumnInt(i, 0);
                _db.debug(D.EBUG_SPEW, "gbl8103:answer:" + iRootID);

                if (egRoot.containsEntityItem(strTargetEntityType, iRootID)) {
                    eiRoot = egRoot.getEntityItem(strTargetEntityType, iRootID);
                }
                else {
                    eiRoot = new EntityItem(egRoot, null, strTargetEntityType, iRootID);
                    egRoot.putEntityItem(eiRoot);
                }
            }

            //
            // Now .. lets get all the stuff underneath it.
            //
            try {
                rs = _db.callGBL8000(returnStatus, iSessionID, strEnterprise, strTargetEntityType, strActionItemKey, "Y", strValOn,
                                     strEffOn, (D.ebugShow() == D.EBUG_SPEW ? 1 : 0));
                rdrs = new ReturnDataResultSet(rs);
            }
            finally {
				if (rs !=null){
                	rs.close();
                	rs = null;
				}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            _db.debug(D.EBUG_DETAIL, "gbl8000:recordcount:" + rdrs.size());

            for (int ii = 0; ii < rdrs.size(); ii++) {

                EntityGroup eg1 = null;
                EntityGroup eg2 = null;
                EntityGroup eg3 = null;

                EntityItem ei1 = null;
                EntityItem ei2 = null;
                EntityItem ei3 = null;

                int iLevel = rdrs.getColumnInt(ii, 0);
                String strEntity1Type = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 1));
                int iEntity1ID = rdrs.getColumnInt(ii, 2);
                String strEntityType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 3));
                int iEntityID = rdrs.getColumnInt(ii, 4);
                String strEntity2Type = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 5));
                int iEntity2ID = rdrs.getColumnInt(ii, 6);
                int iLeaf = rdrs.getColumnInt(ii, 7);
                String strValOn8000 = rdrs.getColumnDate(ii, 8);

                if (_ai.pullTargetEntitiesOnly()) {
                    // Skip it if its not a target Entity
                    if (! (strEntity1Type.equals(_ai.getTargetEntityType()) || strEntityType.equals(_ai.getTargetEntityType()) ||
                           strEntity2Type.equals(_ai.getTargetEntityType()))) {
                        continue;
                    }
                }

                _db.debug(D.EBUG_SPEW,
                          "gbl8000:answer:" + iLevel + ":" + iLeaf + ":" + strEntity1Type + ":" + iEntity1ID + ":" + strEntityType +
                          ":" + iEntityID + ":" + strEntity2Type + ":" + iEntity2ID + ":" + strValOn8000);

                // The root Entity Items should always been in eg3
                // In an extract.. there shouuld always be an entityGroup in the general list that matches the root entitytype

                if (iLevel > -1) {
                    eg1 = getEntityGroup(strEntity1Type);
                    eg2 = getEntityGroup(strEntityType);
                }
                eg3 = getEntityGroup(strEntity2Type);

                if (iLevel != -1 && ( (eg1 == null) || (eg2 == null) || (eg3 == null))) {
                    _db.debug(D.EBUG_ERR, "EAN1234 ** Accidental birth of entity group !! call planned parenthood for help");
                    continue;
                }
                else if (iLevel == -1 && eg3 == null) {
                    _db.debug(D.EBUG_ERR, "EAN12335 ** Accidental birth of entity group at -1!! call planned parenthood for help");
                    continue;
                }

                // Groups are now good so lets go onto items
                // Esnure we use the EGParent Group when possible
                if (iLevel != -1) {
                    if (eg1.containsEntityItem(strEntity1Type, iEntity1ID)) {
                        ei1 = eg1.getEntityItem(strEntity1Type, iEntity1ID);
                    }
                    else if (egParent.containsEntityItem(strEntity1Type, iEntity1ID)) {
                        ei1 = egParent.getEntityItem(strEntity1Type, iEntity1ID);
                    }
                    else {
                        ei1 = new EntityItem(eg1, null, strEntity1Type, iEntity1ID);
                        eg1.putEntityItem(ei1);
                    }
                    if (eg2.containsEntityItem(strEntityType, iEntityID)) {
                        ei2 = eg2.getEntityItem(strEntityType, iEntityID);
                    }
                    else if (egParent.containsEntityItem(strEntityType, iEntityID)) {
                        ei2 = egParent.getEntityItem(strEntityType, iEntityID);
                    }
                    else {
                        ei2 = new EntityItem(eg2, null, strEntityType, iEntityID);
                        eg2.putEntityItem(ei2);
                    }
                }

                if (eg3.containsEntityItem(strEntity2Type, iEntity2ID)) {
                    ei3 = eg3.getEntityItem(strEntity2Type, iEntity2ID);
                }
                else if (egParent.containsEntityItem(strEntity2Type, iEntity2ID)) {
                    ei3 = egParent.getEntityItem(strEntity2Type, iEntity2ID);
                }
                else {
                    ei3 = new EntityItem(eg3, null, strEntity2Type, iEntity2ID);
                    eg3.putEntityItem(ei3);
                }

                // Now hook them all up (If they were not the original Parent Records)

                if (iLevel != -1) {
                    _db.test(ei1 != null, "CAUGHT A FISH.ie1 is null:" + strEntity1Type + ":" + iEntity1ID);
                    _db.test(ei3 != null, "CAUGHT A FISH.ie3 is null:" + strEntity2Type + ":" + iEntity2ID);
                    ei2.putUpLink(ei1);
                    ei2.putDownLink(ei3);

                    // Here is where we put the level's
                    ei1.setLevel(iLevel);
                    ei2.setLevel(iLevel);
                    ei3.setLevel(iLevel);
                }
            }

            // Lets now pull all the attributes we have here
            // One call for every column
            // grab all NLS's
            // grab attributes -> only if specified in ExtractActionItem

            if (_ai.pullAttributes()) {
                popAllAttributeValues(_db, _prof, iSessionID, _ai);
            }

        }
        finally {
            _db.freeStatement();
            _db.isPending();
            memTbl.clear();
            // Now remove all the records to clean up after yourself
            // We need a simpler way to do this
            // We must skip this if there is information other processes may want to us
            // for another action item that is related to this information
            if (!_ai.getSkipCleanup()) {
                _db.callGBL8105(returnStatus, iSessionID);
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
        }

    }

    /**
     *Constructor for the EntityList object
     *
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @param  _ai                             Description of the Parameter
     * @param  _aei                            Description of the Parameter
     * @param  _strEntityType                  Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public EntityList(Database _db, Profile _prof, ExtractActionItem _ai, EntityItem[] _aei, String _strEntityType) throws
        SQLException, MiddlewareException, MiddlewareRequestException {

        super(null, _prof, _ai.getKey() + _strEntityType);
        int iSessionID = 0;
        ReturnStatus returnStatus = new ReturnStatus( -1);

        EntityList.checkDomain(_prof,_ai,_aei); //RQ0713072645
        //attempt to use less memory, hang onto strings already found
        java.util.Hashtable memTbl = new java.util.Hashtable();
        
        try {
            EntityGroup egParent = null;
            ExtractActionItem aiParent = null;
            iSessionID = _db.getNewSessionID();

            ReturnDataResultSet rdrs = null;
            ResultSet rs = null;
            String strStartEntityType = _strEntityType;
            String strEnterprise = getProfile().getEnterprise();
            String strValOn = getProfile().getValOn();
            String strEffOn = getProfile().getEffOn();
            String strRoleCode = getProfile().getRoleCode();
            String strActionItemKey = _ai.getActionItemKey();
            int iOPWGID = getProfile().getOPWGID();
            int iOPID = getProfile().getOPID();
            int iNLSID = getProfile().getReadLanguage().getNLSID();
            boolean bChunking = false;
      
            EANList eltmp = getCachedEntityGroupList(_db, _ai, iNLSID,memTbl);
        
            setParentEntityGroup(new EntityGroup(this, _db, null, _strEntityType, _ai.getPurpose(), false,memTbl));

            // Lets get the parent back for future use
            egParent = getParentEntityGroup();

            setExtractSessionID(iSessionID);
            //
            //  Checking for a Cached EntityGroup list so we do not have to load each one individually
            //

            if (eltmp != null) {
                setEntityGroup(eltmp);
            }
            else {
               	//RCQ00210066-WI 
            	DatePackage dp = new DatePackage(_db);
            	boolean success = false;
                // Retrieve the Action Class and the Action Description.
                try {
                	//RCQ00210066-WI rs = _db.callGBL7004(returnStatus, strEnterprise, strRoleCode, strActionItemKey, strValOn, strEffOn);
                	rs = _db.callGBL7004(returnStatus, strEnterprise, strRoleCode, strActionItemKey, dp.getEndOfDay(), dp.getEndOfDay());
                    rdrs = new ReturnDataResultSet(rs);
                    success = true;
                }
                finally {
                   	if (rs!=null){
                    	try{
                			rs.close();
                		} catch (SQLException x) {
                			_db.debug(D.EBUG_DETAIL, "EntityList: "+getKey()+" ERROR failure closing ResultSet "+x);
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

                // GAB - 01/22/04 - F.B. # 53576:
                // Set this here because we need the EntityGroup's parent EntityList's (i.e. this)
                // parent ActionItem while building the EntityGroup from scratch.  We will set it to null
                // again immediately after to ensure we aren't serializing too much.  We again set this later below..
                setParentActionItem(_ai);
                for (int i = 0; i < rdrs.size(); i++) {
                    String strEntityType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 0));
                    EntityGroup eg = getEntityGroup(strEntityType);
                    _db.debug(D.EBUG_SPEW, "gbl7004:answer:" + strEntityType + ":");
                    if (eg == null) {
//TIR USRO-R-JPRD-6UYLY6                        eg = new EntityGroup(this, _db, getProfile(), strEntityType, strPurpose, false); //VEEdit_Iteration2Order
                        eg = new EntityGroup(this, _db, getProfile(), strEntityType, "Edit", false,memTbl); //TIR USRO-R-JPRD-6UYLY6
//VEEdit_Iteration2Order                        eg = new EntityGroup(this, _db, getProfile(), strEntityType, _ai.getPurpose, false);

//                        popDefaultEntityItems(_db, _prof, getParentActionItem());
                        eg.enforceWorkGroupDomain(_db, _prof);

                        putEntityGroup(eg);
                        // Is this a parent like EntityGroup?...  if so .. mark it so the software does not get confused
                        if (eg.getKey().equals(egParent.getKey())) {
                            eg.setParentLike(true);
                        }
                    }
                }

                setParentActionItem(null);
                
                //  Lets put the cache in for the next guy.. (timing issues)
                putCachedEntityGroupList(_db, _ai, iNLSID);
            }

            // Par down some flag values for WG domain enforcement
            // We are not editing .. so we do not have to do this yet DWB
            //enforceWorkGroupDomain(_db, _prof);
            if (_ai.isVEEdit()) {
                popDefaultEntityItems(_db, _prof, getParentActionItem());
                enforceWorkGroupDomain(_db, _prof);
            }
            setParentActionItem(_ai);
            aiParent = (ExtractActionItem) getParentActionItem();

            aiParent.generateVESteps(_db, _prof, _strEntityType);
            setParentEntityItems(_aei);

            _db.test(getParentEntityGroup() != null, "ParentEntityGroup is null");

            //
            // Here we load the trsNavigate Table with either items from a Queue
            // or
            // EntityItems in the Parent Entity Group
            //
            int iChunkSize = MiddlewareServerProperties.getVeExtractChunkItemSize(strActionItemKey); //Try chunking this to avoid a huge extract load to gbl8000
            int iLastChunk = 0;
            int iItem = 0;
            do { //Chunking Starts here

                if (_ai.isQueueSourced()) {

                    // Only move an interval's worth of data over if interval processing is
                    // requested
                    if (_ai.hasIntervalItem()) {
                        String strStartDate = _ai.getIntervalItem().getStartDate();
                        String strEndDate = _ai.getIntervalItem().getEndDate();
                        // Use the Queue table as a primer
                        _db.debug(D.EBUG_DETAIL,
                                  "gbl8114:params:" + iSessionID + ":" + strEnterprise + ":" + iOPWGID + ":" + iOPID + ":" +
                                  _ai.getActionItemKey() + ":" + strStartDate + ":" + strEndDate);
                        _db.callGBL8114(returnStatus, iSessionID, strEnterprise, iOPWGID, iOPID, _ai.getActionItemKey(), strStartDate,
                                        strEndDate);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                        //this needs to be updated for QUEUE types
                    }
                    else {
                        // Use the Queue table as a primer
                        _db.debug(D.EBUG_DETAIL,
                                  "gbl8116:params:" + iSessionID + ":" + strEnterprise + ":" + iOPWGID + ":" + iOPID + ":" +
                                  _ai.getActionItemKey());
                        _db.callGBL8116(returnStatus, iSessionID, strEnterprise, iOPWGID, iOPID, _ai.getActionItemKey());
                        _db.freeStatement();
                        _db.isPending();
                        //this needs to be updated for QUEUE types
                    }

                    strStartEntityType = _ai.getTargetEntityType();

                }
                else {
                    bChunking = true;
                    // Use the True Parents as a Primer
                    _db.debug(D.EBUG_SPEW, "Chunking Extract :CHUNKSIZE:" + iChunkSize + "PROCESSING:" + iItem + ":OF:" + egParent.getEntityItemCount());
                    for (; iItem < egParent.getEntityItemCount(); iItem++) {
                        EntityItem eiParent = egParent.getEntityItem(iItem);
                        _db.debug(D.EBUG_DETAIL,
                                  "gbl8115:params:" + iSessionID + ":" + strEnterprise + ":" + _ai.getActionItemKey() + ":" +
                                  eiParent.getEntityType() + ":" + eiParent.getEntityID() + ":" + strValOn + ":" + strEffOn);
                        _db.callGBL8115(returnStatus, iSessionID, strEnterprise, _ai.getActionItemKey(), eiParent.getEntityType(),
                                        eiParent.getEntityID(), strValOn, strEffOn);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();

                        if (egParent.isRelator()) { //VEEdit_Iteration3
                            _db.debug(D.EBUG_DETAIL,
                                      "gbl1019:params:" + iSessionID + ":" + strEnterprise + ":" + _ai.getActionItemKey() + ":" +
                                      eiParent.getEntityType() + ":" + eiParent.getEntityID() + ":" + strValOn + ":" + strEffOn); //VEEdit_Iteration3
                            _db.callGBL1019(returnStatus, iSessionID, strEnterprise, _ai.getActionItemKey(), eiParent.getEntityType(),
                                            eiParent.getEntityID(), strValOn, strEffOn); //VEEdit_Iteration3
                            _db.commit(); //VEEdit_Iteration3
                            _db.freeStatement(); //VEEdit_Iteration3
                            _db.isPending(); //VEEdit_Iteration3
                        } //VEEdit_Iteration3
                        iLastChunk++;
                        if (iLastChunk > iChunkSize - 1) {
                            iLastChunk = 0;
                            break;
                        }
                    }
                    if (iItem >= egParent.getEntityItemCount()) {
                        bChunking = false;
                    }
                }

                // Now that the entities are all inplace in the navigate table.. lets commit and clean up
                _db.commit();
                _db.freeStatement();
                _db.isPending();

                //
                // Now.. we run the Navigate SP Engine
                //
                try {
                    rs = _db.callGBL8000(returnStatus, iSessionID, strEnterprise, strStartEntityType, strActionItemKey, "Y", strValOn,
                                         strEffOn, (D.ebugShow() == D.EBUG_SPEW ? 1 : 0));
                    // Lets load them into a return data result set
                    rdrs = new ReturnDataResultSet(rs);
                }
                finally {
					if (rs!=null){
                    	rs.close();
                    	rs = null;
					}
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
                _db.debug(D.EBUG_DETAIL, "gbl8000:recordcount:" + rdrs.size());
                
                for (int ii = 0; ii < rdrs.size(); ii++) {

                    EntityGroup eg1 = null;
                    EntityGroup eg2 = null;
                    EntityGroup eg3 = null;

                    EntityItem ei1 = null;
                    EntityItem ei2 = null;
                    EntityItem ei3 = null;

                    int iLevel = rdrs.getColumnInt(ii, 0);
                    String strEntity1Type = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 1));
                    int iEntity1ID = rdrs.getColumnInt(ii, 2);
                    String strEntityType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 3));
                    int iEntityID = rdrs.getColumnInt(ii, 4);
                    String strEntity2Type = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 5));
                    int iEntity2ID = rdrs.getColumnInt(ii, 6);
                    int iLeaf = rdrs.getColumnInt(ii, 7);
                    String strValOn8000 = rdrs.getColumnDate(ii, 8);

                    if (_ai.pullTargetEntitiesOnly()) {
                        // Skip it if its not a target Entity
                        if (! (strEntity1Type.equals(_ai.getTargetEntityType()) || strEntityType.equals(_ai.getTargetEntityType()) ||
                               strEntity2Type.equals(_ai.getTargetEntityType()))) {
                            continue;
                        }
                    }

                    _db.debug(D.EBUG_SPEW,
                              "gbl8000:answer:" + iLevel + ":" + iLeaf + ":" + strEntity1Type + ":" + iEntity1ID + ":" + strEntityType +
                              ":" + iEntityID + ":" + strEntity2Type + ":" + iEntity2ID + ":" + strValOn8000);

                    // The root Entity Items should always been in eg3
                    // In an extract.. there shouuld always be an entityGroup in the general list that matches the root entitytype

                    if (iLevel > -1) {
                        eg1 = getEntityGroup(strEntity1Type);
                        eg2 = getEntityGroup(strEntityType);
                    }
                    eg3 = getEntityGroup(strEntity2Type);

                    if (iLevel != -1 && ( (eg1 == null) || (eg2 == null) || (eg3 == null))) {
                        _db.debug(D.EBUG_ERR,
                                "gbl8000:answer:" + iLevel + ":" + iLeaf + ":" + strEntity1Type + ":" + iEntity1ID + ":" + strEntityType +
                                ":" + iEntityID + ":" + strEntity2Type + ":" + iEntity2ID + ":" + strValOn8000);
                        _db.debug(D.EBUG_ERR, "EAN1234 ** Accidental birth of entity group !! call planned parenthood for help");
                        continue;
                    }
                    else if (iLevel == -1 && eg3 == null) {
                        _db.debug(D.EBUG_ERR,
                                "gbl8000:answer:" + iLevel + ":" + iLeaf + ":" + strEntity1Type + ":" + iEntity1ID + ":" + strEntityType +
                                ":" + iEntityID + ":" + strEntity2Type + ":" + iEntity2ID + ":" + strValOn8000);
                        _db.debug(D.EBUG_ERR, "EAN12335 ** Accidental birth of entity group at -1!! call planned parenthood for help");
                        continue;
                    }

                    // Groups are now good so lets go onto items
                    // Esnure we use the EGParent Group when possible

                    if (iLevel != -1) {
                        if (eg1.containsEntityItem(strEntity1Type, iEntity1ID)) {
                            ei1 = eg1.getEntityItem(strEntity1Type, iEntity1ID);
                        }
                        else if (egParent.containsEntityItem(strEntity1Type, iEntity1ID)) {
                            ei1 = egParent.getEntityItem(strEntity1Type, iEntity1ID);
                        }
                        else {
                            ei1 = new EntityItem(eg1, null, strEntity1Type, iEntity1ID);
                            eg1.putEntityItem(ei1);
                        }
                        if (eg2.containsEntityItem(strEntityType, iEntityID)) {
                            ei2 = eg2.getEntityItem(strEntityType, iEntityID);
                        }
                        else if (egParent.containsEntityItem(strEntityType, iEntityID)) {
                            ei2 = egParent.getEntityItem(strEntityType, iEntityID);
                        }
                        else {
                            ei2 = new EntityItem(eg2, null, strEntityType, iEntityID);
                            eg2.putEntityItem(ei2);
                        }
                    }

                    if (eg3.containsEntityItem(strEntity2Type, iEntity2ID)) {
                        ei3 = eg3.getEntityItem(strEntity2Type, iEntity2ID);
                    }
                    else if (egParent.containsEntityItem(strEntity2Type, iEntity2ID)) {
                        ei3 = egParent.getEntityItem(strEntity2Type, iEntity2ID);
                    }
                    else {
                        ei3 = new EntityItem(eg3, null, strEntity2Type, iEntity2ID);
                        eg3.putEntityItem(ei3);
                    }

                    // Now hook them all up (If they were not the original Parent Records)

                    if (iLevel != -1) {
                        _db.test(ei1 != null, "CAUGHT A FISH.ie1 is null:" + strEntity1Type + ":" + iEntity1ID);
                        _db.test(ei3 != null, "CAUGHT A FISH.ie3 is null:" + strEntity2Type + ":" + iEntity2ID);
                        ei2.putUpLink(ei1);
                        ei2.putDownLink(ei3);

                        // Here is where we put the level's
                        ei1.setLevel(iLevel);
                        ei2.setLevel(iLevel);
                        ei3.setLevel(iLevel);

                        // if QueueSourced -> set latest changed date on relator
                        if (_ai.isQueueSourced() && _ai.hasIntervalItem()) {
                            ei2.calcRelatorModified(strValOn8000, _ai.getIntervalItem());
                        }
                    }
                }

                // Lets now pull all the attributes we have here
                // One call for every column
                // grab all NLS's
                // grab attributes -> only if specified in ExtractActionItem
                if (_ai.pullAttributes()) {
                    popAllAttributeValues(_db, _prof, iSessionID, _ai);
                }

                if (_ai.isVEEdit()) {
                    ExtractActionItem eai = (ExtractActionItem) _ai;
                    String strRoot = eai.getTargetType();
                    EntityGroup egDispRoot = null;
                    if (strRoot.equals("Parent")) {
                        egDispRoot = egParent;
                        m_bParentDisplayable = true;
                    }
                    else {
                        egDispRoot = getEntityGroup(strRoot);
                    }

                    if (egDispRoot != null) {
                        String strType = egDispRoot.getEntityType();
                        egDispRoot.setForceMode(true);
                        egDispRoot.setForceDisplay(true);
                        if (!eai.isDisplayable(strType)) { //VEEdit_Iteration3
                            while (egDispRoot.getMetaAttributeCount() > 0) { //VEEdit_Iteration3
                                EANMetaAttribute meta = egDispRoot.getMetaAttribute(0); //VEEdit_Iteration3
                                egDispRoot.removeMetaAttribute(meta); //VEEdit_Iteration3
                            } //VEEdit_Iteration3
                        }
                        else if (eai.isNavOnly(strType)) {
                            for (int z = (egDispRoot.getMetaAttributeCount() - 1); z >= 0; --z) {
                                EANMetaAttribute meta = egDispRoot.getMetaAttribute(z);
                                if (!meta.isNavigate()) {
                                    egDispRoot.removeMetaAttribute(meta);
                                }
                            }
                        }

                        loadVirtualAttributes(egDispRoot, eai);

                        for (int i = 0; i < getEntityGroupCount(); ++i) {
                            EntityGroup eg = getEntityGroup(i);

                            MetaColumnOrderGroup mcog = eg.getMetaColumnOrderGroup();
                            if (mcog == null) {
                                try {
                                    //mcog = eg.getMetaColumnOrderGroup(_db, null); this builds metacols twice
                                    EntityGroup mcogGrp = new EntityGroup(null, _db, _prof, eg.getEntityType(), "Edit",memTbl);
                                    mcog = mcogGrp.getMetaColumnOrderGroup();
                                } catch (Exception _ex) {}
                            }
                            if (!egDispRoot.getEntityType().equals(eg.getEntityType())) {
                                eg.setForceMode(true);
                                eg.setForceDisplay(false);
                                String sType = eg.getEntityType();

								if (eai.isDisplayable(sType)) {
									if (mcog != null) {
										for (int x = 0; x < mcog.getMetaColumnOrderItemCount(); ++x) {
											MetaColumnOrderItem mcoi = mcog.getMetaColumnOrderItem(x);
											String attCode = mcoi.getAttributeCode();
											EANMetaAttribute meta = eg.getMetaAttribute(attCode);
                                			String strKey = eg.getEntityType() + ":" + meta.getKey();

											if (eai.isFull(sType) || meta.isNavigate()) {
												try {
													Implicator imp = new Implicator(meta, null,strKey);
													egDispRoot.putMeta(imp);
												}
												catch (Exception _x) {
													_x.printStackTrace();
												}
											}
										}
									} else {
										for (int x = 0; x < eg.getMetaAttributeCount(); ++x) {
											EANMetaAttribute meta = eg.getMetaAttribute(x);
                                			String strKey = eg.getEntityType() + ":" + meta.getKey();
											if (eai.isFull(sType) || meta.isNavigate()) {
												try {
													Implicator imp = new Implicator(meta, null,strKey);
													egDispRoot.putMeta(imp);
												}
												catch (Exception _x) {
													_x.printStackTrace();
												}
											}
										}
									}
								}
                            }
                            else if (mcog != null){
                                egDispRoot.setMetaColumnOrderGroup(mcog);
                            }
                        } // end group loop
                    } //end disproot ok

                    addVirtualEntities();
                }
                _db.freeStatement();
                _db.isPending();
                // Now remove all the records to clean up after yourself
                // We need a simpler way to do this
                // We must skip this if there is information other processes may want to us
                // for another action item that is related to this information
                if (!_ai.getSkipCleanup()) {
                    _db.callGBL8105(returnStatus, iSessionID);
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }

            }
            while (bChunking);
        }
        finally {
            _db.freeStatement();
            _db.isPending();
            
            memTbl.clear();
            // Now remove all the records to clean up after yourself
            // We need a simpler way to do this
            // We must skip this if there is information other processes may want to us
            // for another action item that is related to this information
            if (!_ai.getSkipCleanup()) {
                _db.callGBL8105(returnStatus, iSessionID);
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
        }
    }

    /***************
     * attempt to cleanup session id if action had it turned off
     * @param _db
     * @param iSessionID
     */
    public static void cleanUpSessionID(Database _db, int iSessionID){
    	ReturnStatus returnStatus = new ReturnStatus( -1);
    	try	{
    		_db.callGBL8105(returnStatus, iSessionID);
    		_db.commit();
    		_db.freeStatement();
    		_db.isPending();
    	}catch(Exception exc){
    		 _db.debug(D.EBUG_ERR, "Error cleaning up session id "+iSessionID+" "+exc.getMessage());
             exc.printStackTrace();
    	}
    }
    /**
     * add in virtual entities
     *
     */
    private void addVirtualEntities() throws MiddlewareRequestException
    {
        EANActionItem eact = getParentActionItem();
        if (eact instanceof ExtractActionItem && eact.isVEEdit()) {
			ExtractActionItem ean = (ExtractActionItem)eact;
			EntityGroup peg = getParentEntityGroup();
			int pegCnt = peg.getEntityItemCount();

			Vector displayTypesVct = getVEEditDisplayTypes(peg.getEntityType());
			StringTokenizer st = new StringTokenizer(ean.getVEPath(),":"); // D:MODELBOM
			String pathdir = st.nextToken();
			String pathtype = st.nextToken();

			for (int p=0; p<pegCnt; p++){
				VEEditItem vei = (VEEditItem)peg.getEntityItem(p);
				// add to original root
				Vector displayableToAddVct = new Vector(1);
				Vector veiVct = new Vector(1);
				veiVct.add(vei);
				boolean origRootDone = false;
				// create one vei for each path
				if (pathdir.equals("D")){
					Vector dnlink = vei.getDownLink();
					// duplicate root for each downlink that matches the pathtype
					for (int i=0; i<dnlink.size(); i++){
						EntityItem dnitem = (EntityItem)dnlink.elementAt(i); // MODELBOM
						if (pathtype.equals(dnitem.getEntityType())){
							if (origRootDone){ // orig root done
								VEEditItem vei2 = new VEEditItem(vei, ean.getID());
								peg.putEntityItem(vei2);
								vei2.setAttribute(vei); // group isnt set until now
								vei2.addEntity(dnitem);
								vei2.putDownLink(dnitem);
								if(dnitem.hasDownLinks()){ //BOM
									vei2.addEntity((EntityItem)dnitem.getDownLink(0));
								}
								veiVct.add(vei2);
							}else{ // do original root
								origRootDone = true;
								vei.addEntity(dnitem);
								if(dnitem.hasDownLinks()){ //BOM
									vei.addEntity((EntityItem)dnitem.getDownLink(0));
								}
							}
						}else{
							// is it displayable
							if (ean.isDisplayable(dnitem.getEntityType()) ||
								displayTypesVct.contains(dnitem.getEntityType())) {
								displayableToAddVct.add(dnitem);
							}
							if(dnitem.hasDownLinks()){
								Vector dnlink2 = dnitem.getDownLink();
								for (int iu=0; iu<dnlink2.size(); iu++){
									EntityItem item2 = (EntityItem)dnlink2.elementAt(iu);
									// is it displayable
									if (ean.isDisplayable(item2.getEntityType())||
										displayTypesVct.contains(item2.getEntityType())) {
										displayableToAddVct.add(item2);
									}
								}
							}
						}
					}// end each dnlink
					//look at uplinks for displayables
					Vector uplink = vei.getUpLink();
					for (int i=0; i<uplink.size(); i++){
						EntityItem item = (EntityItem)uplink.elementAt(i);
						// is it displayable
						if (ean.isDisplayable(item.getEntityType())||
								displayTypesVct.contains(item.getEntityType())) {
							displayableToAddVct.add(item);
						}
						if (item.hasUpLinks()){
							Vector uplink2 = item.getUpLink();
							for (int iu=0; iu<uplink2.size(); iu++){
								EntityItem item2 = (EntityItem)uplink2.elementAt(iu);
								// is it displayable
								if (ean.isDisplayable(item2.getEntityType())||
									displayTypesVct.contains(item2.getEntityType())) {
									displayableToAddVct.add(item2);
								}
							}
						}
					}

					// add all displayables to roots
					for (int v=0; v<veiVct.size(); v++){
						VEEditItem vei2 = (VEEditItem)veiVct.elementAt(v);
						for (int d=0; d<displayableToAddVct.size(); d++){
							EntityItem item2 = (EntityItem)displayableToAddVct.elementAt(d);
							vei2.addEntity(item2);
						}
					}
				}// is down
				else{ // look up
					Vector uplink = vei.getUpLink();
					// duplicate root for each uplink
					for (int i=0; i<uplink.size(); i++){
						EntityItem dnitem = (EntityItem)uplink.elementAt(i);
						if (pathtype.equals(dnitem.getEntityType())){
							if (origRootDone){ // orig root done
								VEEditItem vei2 = new VEEditItem(vei, ean.getID());
								peg.putEntityItem(vei2);
								vei2.setAttribute(vei); // group isnt set until now
								vei2.addEntity(dnitem);
								vei2.putUpLink(dnitem);
								if(dnitem.hasUpLinks()){
									vei2.addEntity((EntityItem)dnitem.getUpLink(0));
								}
								veiVct.add(vei2);
							}else{ // do original root
								origRootDone = true;
								vei.addEntity(dnitem);
								if(dnitem.hasUpLinks()){
									vei.addEntity((EntityItem)dnitem.getUpLink(0));
								}
							}
						}else{
							// is it displayable
							if (ean.isDisplayable(dnitem.getEntityType()) ||
								displayTypesVct.contains(dnitem.getEntityType())) {
								displayableToAddVct.add(dnitem);
							}
							if(dnitem.hasUpLinks()){
								Vector dnlink2 = dnitem.getUpLink();
								for (int iu=0; iu<dnlink2.size(); iu++){
									EntityItem item2 = (EntityItem)dnlink2.elementAt(iu);
									// is it displayable
									if (ean.isDisplayable(item2.getEntityType())||
										displayTypesVct.contains(item2.getEntityType())) {
										displayableToAddVct.add(item2);
									}
								}
							}

						}
					}// end each uplink
					//look at dnlinks for displayables
					Vector dnlink = vei.getDownLink();
					for (int i=0; i<dnlink.size(); i++){
						EntityItem item = (EntityItem)dnlink.elementAt(i);
						// is it displayable
						if (ean.isDisplayable(item.getEntityType())||
								displayTypesVct.contains(item.getEntityType())) {
							displayableToAddVct.add(item);
						}
						if (item.hasDownLinks()){
							Vector uplink2 = item.getDownLink();
							for (int iu=0; iu<uplink2.size(); iu++){
								EntityItem item2 = (EntityItem)uplink2.elementAt(iu);
								// is it displayable
								if (ean.isDisplayable(item2.getEntityType())||
									displayTypesVct.contains(item2.getEntityType())) {
									displayableToAddVct.add(item2);
								}
							}
						}
					}

					// add all displayables to roots
					for (int v=0; v<veiVct.size(); v++){
						VEEditItem vei2 = (VEEditItem)veiVct.elementAt(v);
						for (int d=0; d<displayableToAddVct.size(); d++){
							EntityItem item2 = (EntityItem)displayableToAddVct.elementAt(d);
							vei2.addEntity(item2);
						}
					}
				}//end look up

				displayableToAddVct.clear();
			} // end parent loop
        }
    }

	private Vector getVEEditDisplayTypes(String rootType){
		Vector displayTypeVct = new Vector(1);
		EANActionItem eact = getParentActionItem();
		if (eact instanceof ExtractActionItem && eact.isVEEdit()) {
			ExtractActionItem ean = (ExtractActionItem)eact;
			String[] dataPath = ean.getDisplayAttributes();
			if (dataPath != null) {
				for (int j = 0; j < dataPath.length; ++j) {
					VEPath[] tmpPath = ean.getPathFromString(dataPath[j]);
					if (tmpPath != null) {
						for (int t=0; t<tmpPath.length; t++){
							VEPath target = tmpPath[t];
							if (target.getType() != null && !rootType.equals(target.getType())){
								displayTypeVct.add(target.getType());
							}
						}
					}
				}
			}
		}

		return displayTypeVct;
	}

    private void loadVirtualAttributes(EntityGroup _egParent, ExtractActionItem _xtract) {
        if (_egParent != null && _xtract.hasDisplayAttribute()) {
			String[] dataPath = _xtract.getDisplayAttributes();
			if (dataPath != null) {
				for (int j = 0; j < dataPath.length; ++j) {
					VEPath[] tmpPath = _xtract.getPathFromString(dataPath[j]);
					if (tmpPath != null) {
						loadVirtualAttributes(_egParent, tmpPath);
					}
				}
			}
        }
    }

    private void loadVirtualAttributes(EntityGroup _eg, VEPath[] _path) {
        if (_eg != null && _path != null) {
            int xx = _path.length - 1;
            if (xx >= 0) {
                VEPath target = _path[xx];
                EntityGroup tmpEG = getEntityGroup(target.getType());
                if (tmpEG != null) {
                    String[] atts = target.getAttributes();
                    if (atts != null) {
                        for (int i = 0; i < atts.length; ++i) {
                            EANMetaAttribute meta = tmpEG.getMetaAttribute(atts[i]);
                            if (meta != null) {
                                String strKey = tmpEG.getEntityType() + ":" + meta.getKey();
                                for (int x = 0; x < tmpEG.getEntityItemCount(); ++x) {
                                    EntityItem ei = tmpEG.getEntityItem(x);
                                    EANAttribute att = (EANAttribute) ei.getEANObject(strKey);
                                    if (att != null) {
                                        att.refreshDefaults();
                                    }
                                }

                                try {
                                    Implicator imp = new Implicator(meta, null, strKey);
                                    _eg.putMeta(imp);
                                }
                                catch (Exception _ex) {
									_ex.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /*
     *  This represents the e-announce 1.1 constructor that will be used
     *  to generate all EntityLists with a Navigate Action Item
     */
    /**
     * get the timestamp for the execution of this action
     * @return
     */
    public String getExecutionDTS() { return executionDTS;}
    /**
     * save the timestamp for the execution of this action
     * @param _db
     */
    private void setExecutionDTS(Database _db){
    	DatePackage dp = new DatePackage(_db);
    	executionDTS = dp.getNow();
    }
    /**
     *Constructor for the EntityList object
     *
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @param  _ai                             Description of the Parameter
     * @param  _aei                            Description of the Parameter
     * @param  _strEntityType                  Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     * @param _bActionTree
     */
    public EntityList(Database _db, Profile _prof, NavActionItem _ai, EntityItem[] _aei, String _strEntityType,
                      boolean _bActionTree) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(null, _prof, _ai.getKey() + _strEntityType);

		EntityList.checkDomain(_prof,_ai,_aei); //RQ0713072645

		//save timestamp for this pull of data
		setExecutionDTS(_db);
		
        int iSessionID = 0;
        ReturnStatus returnStatus = new ReturnStatus( -1);
     	java.util.Hashtable memTbl = new Hashtable();
        try {

            EntityGroup egParent = null;

            // Lets go fill out the detail now
            ReturnDataResultSet rdrs = null;
            ResultSet rs = null;

            String strStartEntityType = _strEntityType;
            String strEnterprise = getProfile().getEnterprise();
            String strValOn = getProfile().getValOn();
            String strEffOn = getProfile().getEffOn();
            String strRoleCode = getProfile().getRoleCode();
            String strActionItemKey = _ai.getActionItemKey();
            int iOPWGID = getProfile().getOPWGID();
            int iWGID = getProfile().getWGID();
            int iOPID = getProfile().getOPID();
            int iNLSID = getProfile().getReadLanguage().getNLSID();

            // get a new session id for the navigate transaction...
            iSessionID = _db.getNewSessionID();

            //
            // O.K. if we are executing a DGWORKQUEUE.. we have to execute
            // The refresh queue
            //
            if (_ai.getActionItemKey().equals("DGWORKQUEUE")) {
                try {
                    _db.refreshDGQueue(getProfile());
                }
                catch (MiddlewareShutdownInProgressException x) {
                    _db.debug(D.EBUG_DETAIL, x.getMessage());
                }
            }

            if (_ai.isFlagResearch()) {
                // no cache needed for this type of navaction b/c each time it's different
                String strAttributeCode = _ai.getAttributeCode();
                _db.test( (strAttributeCode != null && strAttributeCode.length() > 0),
                         "Attribute Code in Flag Research NavActionItem is blank.");
                try {
                    rs = _db.callGBL7668(returnStatus, strEnterprise, strAttributeCode, strValOn, strEffOn);
                    rdrs = new ReturnDataResultSet(rs);
                }
                finally {
					if (rs !=null){
	                    rs.close();
	                    rs = null;
					}
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
                for (int i = 0; i < rdrs.size(); i++) {
                    String strEntityType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 0));
                    EntityGroup eg = getEntityGroup(strEntityType);

                    _db.debug(D.EBUG_SPEW, "gbl7668:answer:" + strEntityType + ":");

                    if (eg == null) {
                        eg = new EntityGroup(this, _db, null, strEntityType, _ai.getPurpose(),memTbl);
                        putEntityGroup(eg);
                        // Is this a parent like EntityGroup?...  if so .. mark it so the software does not get confused
                        if (eg.getKey().equals(_strEntityType)) {
                            eg.setParentLike(true);
                        }
                        if (_ai.isForceDisplay(eg)) {
                            eg.setForceMode(true);
                            eg.setForceDisplay(true);
                        }
                    }
                }

            }
            else {

                //
                //  Checking for a Cached EntityGroup list so we do not have to load each one individually
                //
                EANList eltmp = getCachedEntityGroupList(_db, _ai, iNLSID,memTbl);
  
                if (eltmp != null) {
                    setEntityGroup(eltmp);
                    for (int i = 0; i < getEntityGroupCount(); i++) {
                        EntityGroup eg = getEntityGroup(i);
                        eg.setParent(this);
                        // If its either a picklist, or a path
                        // we need to shut down isdisplayables on everything except the target EG
                        if (_ai.isPicklist() || _ai.isPath()) {
                            eg.setForceMode(true);
                            eg.setForceDisplay(eg.getKey().equals(_ai.getTargetEntityGroup()));
                        }
                        if (_ai.isForceDisplay(eg)) {
                            eg.setForceMode(true);
                            eg.setForceDisplay(true);
                        }
                    }

                }
                else {
                	//RCQ00210066-WI 
                	DatePackage dp = new DatePackage(_db);
                	boolean success = false;
                    // Retrieve the Action Class and the Action Description.
                    try {
                        //RCQ00210066 rs = _db.callGBL7004(returnStatus, strEnterprise, strRoleCode, strActionItemKey, strValOn, strEffOn);
                        rs = _db.callGBL7004(returnStatus, strEnterprise, strRoleCode, strActionItemKey, dp.getEndOfDay(), dp.getEndOfDay());
                        rdrs = new ReturnDataResultSet(rs);
                        success = true;
                    }
                    finally {
                       	if (rs!=null){
                        	try{
                    			rs.close();
                    		} catch (SQLException x) {
                    			_db.debug(D.EBUG_DETAIL, "EntityList: "+getKey()+" ERROR failure closing ResultSet "+x);
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

                    for (int i = 0; i < rdrs.size(); i++) {
                        String strEntityType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 0));
                        EntityGroup eg = getEntityGroup(strEntityType);

                        _db.debug(D.EBUG_SPEW, "gbl7004:answer:" + strEntityType + ":");

                        if (eg == null) {
                            eg = new EntityGroup(this, _db, null, strEntityType, _ai.getPurpose(),memTbl);
                            putEntityGroup(eg);
                            // Is this a parent like EntityGroup?...  if so .. mark it so the software does not get confused
                            if (eg.getKey().equals(_strEntityType)) {
                                eg.setParentLike(true);
                            }
                            // If its either a picklist, or a path
                            // we need to shut down isdisplayables on everything except the target EG
                            if (_ai.isPicklist() || _ai.isPath()) {
                                eg.setForceMode(true);
                                eg.setForceDisplay(eg.getKey().equals(_ai.getTargetEntityGroup()));
                            }

                            if (_ai.isForceDisplay(eg)) {
                                eg.setForceMode(true);
                                eg.setForceDisplay(true);
                            }

                        }
                    }

                    //  Lets put the cache in for the next guy.. (timing issues)
                    putCachedEntityGroupList(_db, _ai, iNLSID);
                }

            }

            // Set up some parent stuff
            setParentEntityGroup(new EntityGroup(this, _db, null, _strEntityType, _ai.getPurpose()));

            // Lets get the parent back for future use
            egParent = getParentEntityGroup();
            setParentActionItem(_ai);
            setParentEntityItems(_aei);

            // GB - 3/17/03 - set NavActionItem's parent EntityItems so that we can replay action later
            _ai.setEntityItems(_aei);
            _ai.checkTagging(_db, getProfile(), _aei);

            // Lets set up the Action List
            if (_bActionTree) {
                setActionList(new ActionList(null, _db, getProfile(), getParentActionItem(), true));
            }
            else {
                _db.debug(D.EBUG_INFO, "EntityList:NavActionItem: *** CALLER IS BYPASSING ACTION LIST GEN ***");
            }

            // See if we have to get some tagging infor
            setTagInfo(_db, _prof);

            //we don't need data if catalog source
            if (_ai.isCatalogDataSource()) { //20050816
                return; //20050816
            } //20050816

            // Now lets get all the Data (YeHaaa!)
            // If it is an entryPoint .. a go home request.. load the navigateTable with the WorkGroup information
            if (isEntryPoint() || _ai.isHomeEnabled()) {

                EntityGroup eg = getEntityGroup("WG");

                _db.debug(D.EBUG_DETAIL,
                          "EntityList:NavActionItem:" + (isEntryPoint() ? "Entry Point" : "HomeEnabled") + " Detected");
                _db.debug(D.EBUG_DETAIL,
                          "gbl8115:params:" + iSessionID + ":" + strEnterprise + ":" + _ai.getActionItemKey() + ":" + "WG" + ":" +
                          iWGID + ":" + strValOn + ":" + strEffOn + ":");
                _db.callGBL8115(returnStatus, iSessionID, strEnterprise, _ai.getActionItemKey(), "WG", iWGID, strValOn, strEffOn);
                _db.commit();
                _db.freeStatement();
                _db.isPending();

                // Now .. lets put this guy into the navigate Group
                eg.putEntityItem(new EntityItem(eg, null, _db, "WG", iWGID));
            }
            else if (_ai.isPassThru()) {
                EntityGroup eg = null;

                _db.debug(D.EBUG_DETAIL, "EntityList:NavActionItem:PassThru Detected");
                _db.test(egParent != null, "ParentEntityGroup Array is null");
                eg = getEntityGroup(egParent.getEntityType());
                for (int ii = 0; ii < egParent.getEntityItemCount(); ii++) {
                    EntityItem eiParent = egParent.getEntityItem(ii);
                    _db.debug(D.EBUG_DETAIL,
                              "gbl8115:params:" + iSessionID + ":" + strEnterprise + ":" + _ai.getActionItemKey() + ":" +
                              eiParent.getEntityType() + ":" + eiParent.getEntityID() + ":" + strValOn + ":" + strEffOn);
                    _db.callGBL8115(returnStatus, iSessionID, strEnterprise, _ai.getActionItemKey(), eiParent.getEntityType(),
                                    eiParent.getEntityID(), strValOn, strEffOn);
                    _db.freeStatement();
                    _db.isPending();
                    // Go get the information and instanciate the EntityItem
                    eg.putEntityItem(new EntityItem(eg, null, _db, eiParent.getEntityType(), eiParent.getEntityID()));
                }

            }
            else if (_ai.isFlagResearch()) {
                try {
                    rs = _db.callGBL7666(returnStatus, iSessionID, _ai.getActionItemKey(), strEnterprise, _ai.getAttributeCode(),
                                         _ai.getAttributeValue(), strValOn, strEffOn);
                    rdrs = new ReturnDataResultSet(rs);
                }
                finally {
					if (rs != null){
                    	rs.close();
                    	rs = null;
					}
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
                _db.debug(D.EBUG_DETAIL, "gbl7666:recordcount:" + rdrs.size());

                if (_ai.checkLimit() && !_ai.getActionItemKey().equals("DGWORKQUEUE")) {
                    if (rdrs.size() > NavActionItem.NAVLIMIT) {
                        // might need to clean up before throwing exception
                        _db.callGBL8105(returnStatus, iSessionID);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();

                        _db.test(rdrs.size() <= NavActionItem.NAVLIMIT,
                                 "Number of returned records exceeds the limit of " + NavActionItem.NAVLIMIT + ". (ok)");
                    }
                }

                for (int ii = 0; ii < rdrs.size(); ii++) {

                    EntityItem ei = null;

                    String strEntityType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 0));
                    int iEntityID = rdrs.getColumnInt(ii, 1);
                    int iLevel = rdrs.getColumnInt(ii, 2);
                    EntityGroup eg = getEntityGroup(strEntityType);

                    _db.debug(D.EBUG_SPEW, "gbl7666:answer:" + strEntityType + ":" + iEntityID);

                    if (eg == null) {
                        _db.debug(D.EBUG_ERR, "EAN01234 ** Accidental birth of entity group !! call planned parenthood for help");
                        continue;
                    }

                    // Groups are now good so lets go onto items
                    // Esnure we use the EGParent Group when possible

                    if (!eg.containsEntityItem(strEntityType, iEntityID)) {
                        ei = new EntityItem(eg, null, strEntityType, iEntityID);
                        ei.setLastOPWGID(iLevel);
                        eg.putEntityItem(ei);
                    }
                }

            }
            else {

                if (_ai.isQueueSourced()) {

                    _db.debug(D.EBUG_DETAIL, "EntityList:NavActionItem:QueueSourced Detected");

                    // Use the Queue table as a primer
                    _db.debug(D.EBUG_DETAIL,
                              "gbl8116:params:" + iSessionID + ":" + strEnterprise + ":" + iOPWGID + ":" + iOPID + ":" +
                              _ai.getActionItemKey());
                    _db.callGBL8116(returnStatus, iSessionID, strEnterprise, iOPWGID, iOPID, _ai.getActionItemKey());
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                    //this needs to be updated for QUEUE types
                    strStartEntityType = _ai.getTargetEntityType();

                }
                else {

                    _db.debug(D.EBUG_DETAIL, "EntityList:NavActionItem:Normal Mode ...");
                    _db.test(getParentEntityGroup() != null, "ParentEntityGroup Array is null");

                    for (int ii = 0; ii < egParent.getEntityItemCount(); ii++) {
                        EntityItem eiParent = egParent.getEntityItem(ii);
                        _db.debug(D.EBUG_DETAIL,
                                  "gbl8115:params:" + iSessionID + ":" + strEnterprise + ":" + _ai.getActionItemKey() + ":" +
                                  eiParent.getEntityType() + ":" + eiParent.getEntityID() + ":" + strValOn + ":" + strEffOn);
                        _db.callGBL8115(returnStatus, iSessionID, strEnterprise, _ai.getActionItemKey(), eiParent.getEntityType(),
                                        eiParent.getEntityID(), strValOn, strEffOn);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();

                        // GAB 060605: special case where the Root itself is a relator. We need to "preload" parent/child entities.
                        if (egParent.isRelator()) {
                            _db.debug(D.EBUG_DETAIL,
                                      "gbl1019:params:" + iSessionID + ":" + strEnterprise + ":" + _ai.getActionItemKey() + ":" +
                                      eiParent.getEntityType() + ":" + eiParent.getEntityID() + ":" + strValOn + ":" + strEffOn);
                            _db.callGBL1019(returnStatus, iSessionID, strEnterprise, _ai.getActionItemKey(), eiParent.getEntityType(),
                                            eiParent.getEntityID(), strValOn, strEffOn);
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();
                        }
                    }
                }

                // Lets now execute the basic Navigation transaction...
                _db.debug(D.EBUG_DETAIL,
                          "gbl8000:params:" + iSessionID + ":" + strEnterprise + ":" + strStartEntityType + ":" + strActionItemKey +
                          ":" + (_ai.isQueueSourced() ? "Y" : "N") + ":" + strValOn + ":" + strEffOn);
                rs = _db.callGBL8000(returnStatus, iSessionID, strEnterprise, strStartEntityType, strActionItemKey,
                                     (_ai.isQueueSourced() ? "Y" : "N"), strValOn, strEffOn, (D.ebugShow() == D.EBUG_SPEW ? 1 : 0));

                // Lets load them into a return data result set
                rdrs = new ReturnDataResultSet(rs);
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
                _db.debug(D.EBUG_DETAIL, "gbl8000:recordcount:" + rdrs.size());

                if (_ai.checkLimit() && !_ai.getActionItemKey().equals("DGWORKQUEUE")) {
                    if (rdrs.size() > NavActionItem.NAVLIMIT) {
                        // might need to clean up before throwing exception
                        _db.callGBL8105(returnStatus, iSessionID);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();

                        _db.test(rdrs.size() <= NavActionItem.NAVLIMIT,
                                 "Number of returned records exceeds the limit of " + NavActionItem.NAVLIMIT + ". (ok)");
                    }
                }

                for (int ii = 0; ii < rdrs.size(); ii++) {

                    EntityGroup eg1 = null;
                    EntityGroup eg2 = null;
                    EntityGroup eg3 = null;

                    EntityItem ei1 = null;
                    EntityItem ei2 = null;
                    EntityItem ei3 = null;

                    int iLevel = rdrs.getColumnInt(ii, 0);
                    String strEntity1Type = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 1));
                    int iEntity1ID = rdrs.getColumnInt(ii, 2);
                    String strEntityType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 3));
                    int iEntityID = rdrs.getColumnInt(ii, 4);
                    String strEntity2Type = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 5));
                    int iEntity2ID = rdrs.getColumnInt(ii, 6);
                    int iLeaf = rdrs.getColumnInt(ii, 7);
                    rdrs.getColumnDate(ii, 8);

                    _db.debug(D.EBUG_SPEW,
                              "gbl8000:answer:" + iLevel + ":" + iLeaf + ":" + strEntity1Type + ":" + iEntity1ID + ":" + strEntityType +
                              ":" + iEntityID + ":" + strEntity2Type + ":" + iEntity2ID);

                    if (iLevel > -1) {
                        eg1 = getEntityGroup(strEntity1Type);
                        eg2 = getEntityGroup(strEntityType);
                    }
                    eg3 = getEntityGroup(strEntity2Type);

                    if (iLevel != -1 && ( (eg1 == null) || (eg2 == null) || (eg3 == null))) {
                        _db.debug(D.EBUG_ERR, "EAN01234 ** Accidental birth of entity group !! call planned parenthood for help");
                        continue;
                    }
                    else if (iLevel == -1 && eg3 == null) {
                        _db.debug(D.EBUG_ERR,
                                  "EAN012335 ** Accidental birth of entity group at -1!! call planned parenthood for help");
                        continue;
                    }

                    // Groups are now good so lets go onto items
                    // Esnure we use the EGParent Group when possible

                    if (iLevel != -1) {
                        if (eg1.containsEntityItem(strEntity1Type, iEntity1ID)) {
                            ei1 = eg1.getEntityItem(strEntity1Type, iEntity1ID);
                        }
                        else if (egParent.containsEntityItem(strEntity1Type, iEntity1ID)) {
                            ei1 = egParent.getEntityItem(strEntity1Type, iEntity1ID);
                        }
                        else {
                            ei1 = new EntityItem(eg1, null, strEntity1Type, iEntity1ID);
                            eg1.putEntityItem(ei1);
                        }
                        if (eg2.containsEntityItem(strEntityType, iEntityID)) {
                            ei2 = eg2.getEntityItem(strEntityType, iEntityID);
                        }
                        else if (egParent.containsEntityItem(strEntityType, iEntityID)) {
                            ei2 = egParent.getEntityItem(strEntityType, iEntityID);
                        }
                        else {
                            ei2 = new EntityItem(eg2, null, strEntityType, iEntityID);
                            eg2.putEntityItem(ei2);
                        }
                    }

                    if (eg3.containsEntityItem(strEntity2Type, iEntity2ID)) {
                        ei3 = eg3.getEntityItem(strEntity2Type, iEntity2ID);
                    }
                    else if (egParent.containsEntityItem(strEntity2Type, iEntity2ID)) {
                        ei3 = egParent.getEntityItem(strEntity2Type, iEntity2ID);
                    }
                    else {
                        ei3 = new EntityItem(eg3, null, strEntity2Type, iEntity2ID);
                        eg3.putEntityItem(ei3);
                    }

                    // Now hook them all up (If they were not the original Parent Records)

                    if (iLevel != -1) {
                        _db.test(ei1 != null, "CAUGHT A FISH.ie1 is null:" + strEntity1Type + ":" + iEntity1ID);
                        _db.test(ei3 != null, "CAUGHT A FISH.ie1 is null:" + strEntity2Type + ":" + iEntity2ID);
                        ei2.putUpLink(ei1);
                        ei2.putDownLink(ei3);
                        // Here is where we put the level's
                        ei1.setLevel(iLevel);
                        ei2.setLevel(iLevel);
                        ei3.setLevel(iLevel);
                    }
                }
            }

            m_bParentDisplayable = _ai.isShowParent();

            // O.K.  here is where we say what is visible
            // Lets now pull all the attributes we have here
            // One call for every column

//catalog mods            popNavAttributeValues(_db, strEnterprise, iSessionID, iNLSID, strValOn, strEffOn);

            popNavAttributeValues(_db, _ai, strEnterprise, iSessionID, iNLSID, strValOn, strEffOn); //catalog mods

            if (getParentActionItem().isMetaColumnOrderControl()) {
                buildActionItemColumnOrders(_db, getParentActionItem());
            }
        }
        finally {
            _db.freeStatement();
            _db.isPending();
            memTbl.clear();
            // Now remove all the records to clean up after yourself
            _db.callGBL8105(returnStatus, iSessionID);
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }

    }

    /*
     *  This represents the e-announce 1.1 constructor that will be used
     *  to generate all EntityLists from Create Actions.
     */
    /**
     *Constructor for the EntityList object
     *
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @param  _ai                             Description of the Parameter
     * @param  _aei                            Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public EntityList(Database _db, Profile _prof, CreateActionItem _ai, EntityItem[] _aei) throws SQLException,
        MiddlewareException, MiddlewareRequestException {

        super(null, _prof, _prof.toString() + _ai.toString());
        try {

            MetaLink ml = null;

            _db.debug(D.EBUG_DETAIL, "EntityList w/ CreateActionItem:" + getKey()+" "+_ai.getActionItemKey());
            _db.test(_aei != null, "EntityList.init.createActionItem. parent EntityItem array cannot be null!");

            setParentActionItem(_ai);

            // This may be null because we are creating w/o any real relator
            ml = _ai.getMetaLink();
            // This represents all the possible Parent Entities The new children can be created from
            // or simply a placeholder when we are in StandAlone Create mode
            String purpose = _ai.getPurpose();
            if(_ai.isPeerCreate()){ //RTC 1080073 - LS getting outofmemory exceptions
            	purpose = "Navigate"; // only need navigate attributes for parent - improve perf, reduce memory
            }
            setParentEntityGroup(new EntityGroup(this, _db, null, _aei[0].getEntityType(), purpose,//_ai.getPurpose(),
                                                 _ai.isUIAfterCacheEnabled()));
            // Lets set up the Parent Stuff
            setParentEntityItems(_aei);

            // DWB Lets set up the Action List.
            if (_ai.isWorkflowEnabled()) {
                setActionList(new ActionList(null, _db, getProfile(), getParentActionItem()));
                setTagInfo(_db, _prof);
            }

            // Since this is a create.. we need to simply get back the two EntityGroups in Question
            // 1) the one from the relator type.
            // 2) the one from the entity2Type

            // We have to get default values here for both the Relator and The Entity2Type
            // In this case.. there may be no relator

            if (!_ai.isStandAlone()) {
            	Hashtable rdrsCRTbl = new Hashtable();
                if (_ai.isCreateParent()) {
                    EntityGroup egRelator = new EntityGroup(this, _db, _prof, ml.getEntityType(), _ai.getPurpose(),
                        _ai.isUIAfterCacheEnabled());
                    EntityGroup egEntity1Type = new EntityGroup(this, _db, _prof, ml.getEntity1Type(), _ai.getPurpose(),
                        _ai.isUIAfterCacheEnabled());
                    putEntityGroup(egRelator);
                    putEntityGroup(egEntity1Type);

                    // So lets get the next session id if we have UI stuff enabled
                    if (_ai.isUIAfterCacheEnabled()) {
                        popDefaultEntityItems(_db, _prof, getParentActionItem());
                        // Par down some flag values for WG domain enforcement
                        enforceWorkGroupDomain(_db, _prof);
                    }
                    // Now.. add the new row

                    egRelator.addRow();
                    // check to see if link is allowed SR11, SR15 and SR17. cant do after this because entity
                    // is created but not linked so must do here
                    EntityItemException eie = new EntityItemException();
                    for (int a=0; a<getParentEntityGroup().getEntityItemCount(); a++){//parent is really child
                    	for (int c=0; c<egEntity1Type.getEntityItemCount(); c++){ //parent
                    		EANUtility.canCreateRelator(_db, _prof, ml.getEntityType(), 
                    				egEntity1Type.getEntityItem(c),
                    				getParentEntityGroup().getEntityItem(a),  eie,rdrsCRTbl);
                    		if(eie.getErrorCount() > 0) {
                    			MiddlewareBusinessRuleException mbrx = 
                    				new MiddlewareBusinessRuleException("Unable to create "+egEntity1Type);
                    			mbrx.add(eie.getObject(0), mbrx.getMessage()+" for: "+ eie.getMessage());
                    			rdrsCRTbl.clear();
                    			throw mbrx;
                    		}
                    	}
                    }
                }
                else {
                    EntityGroup egRelator = new EntityGroup(this, _db, _prof, ml.getEntityType(), _ai.getPurpose(),
                        _ai.isUIAfterCacheEnabled());
                    EntityGroup egEntity2Type = new EntityGroup(this, _db, _prof, ml.getEntity2Type(), _ai.getPurpose(),
                        _ai.isUIAfterCacheEnabled());
                    putEntityGroup(egRelator);
                    putEntityGroup(egEntity2Type);

                    // So lets get the next session id if we have UI stuff enabled
                    if (_ai.isUIAfterCacheEnabled()) {
                        popDefaultEntityItems(_db, _prof, getParentActionItem());
                        // Par down some flag values for WG domain enforcement
                        enforceWorkGroupDomain(_db, _prof);
                    }
                    // Now.. add the new row
                    for (int i=0; i<_ai.getNumberOfItems(); i++) {// SR5 need many new items at once
                    	egRelator.addRow();
                    }
                    // check to see if link is allowed SR11, SR15 and SR17. cant do after this because entity
                    // is created but not linked so must do here
                    EntityItemException eie = new EntityItemException();
                    for (int a=0; a<getParentEntityGroup().getEntityItemCount(); a++){
                    	for (int c=0; c<egEntity2Type.getEntityItemCount(); c++){
                    		EANUtility.canCreateRelator(_db, _prof, ml.getEntityType(), 
                    				getParentEntityGroup().getEntityItem(a), 
                    				egEntity2Type.getEntityItem(c), eie,rdrsCRTbl);
                    		if(eie.getErrorCount() > 0) {
                    			MiddlewareBusinessRuleException mbrx = 
                    				new MiddlewareBusinessRuleException("Unable to create "+egEntity2Type);
                    			mbrx.add(eie.getObject(0), mbrx.getMessage()+" for: "+eie.getMessage());
                    			rdrsCRTbl.clear();
                    			throw mbrx;
                    		}
                    	}
                    }
                }
                rdrsCRTbl.clear();
            }
            else if (_ai.isBluePage()) {

                String strEmailAddress = null;

                EntityGroup egEntity2Type = new EntityGroup(this, _db, _prof, "OP", _ai.getPurpose(), _ai.isUIAfterCacheEnabled());
                putEntityGroup(egEntity2Type);

                // So lets get the next session id
                if (_ai.isUIAfterCacheEnabled()) {
                    popDefaultEntityItems(_db, _prof, getParentActionItem());
                    // Par down some flag values for WG domain enforcement
                    enforceWorkGroupDomain(_db, _prof);
                }

                // Now.. add the new row
                strEmailAddress = _ai.getEmailAddress();
                if (strEmailAddress != null && strEmailAddress.length() > 0) {
                    InternetAddress iaEmailAddress = null;
                    BluePageEntry bpe = null;
                    EntityItem ei = null;

                    try {
                        iaEmailAddress = new InternetAddress(strEmailAddress);
                    }
                    catch (Exception x) {
                        System.out.println("Bad email address " + x);
                    }
                    bpe = _db.getBluePageEntry(iaEmailAddress);
                    egEntity2Type.addRow();
                    ei = egEntity2Type.getEntityItem(egEntity2Type.getEntityItemCount() - 1);
                    if (ei != null) {
                        ei = setBluePageEntry(ei, bpe);
                    }
                }
                else {
                    EntityItem ei = null;
                    BluePageEntry[] abpe = _ai.getSelectedBluePageEntries();
                    _db.test(abpe != null && abpe.length > 0, "No selected BluePageEntry");

                    for (int i = 0; i < abpe.length; i++) {
                        BluePageEntry bpe = abpe[i];
                        egEntity2Type.addRow();
                        ei = egEntity2Type.getEntityItem(egEntity2Type.getEntityItemCount() - 1);
                        if (ei != null) {
                            ei = setBluePageEntry(ei, bpe);
                        }
                    }
                }

            }
            else {
                EntityGroup egEntity2Type = new EntityGroup(this, _db, _prof, _ai.getStandAloneEntityType(), _ai.getPurpose(),
                    _ai.isUIAfterCacheEnabled());
                putEntityGroup(egEntity2Type);

                // So lets get the next session id
                if (_ai.isUIAfterCacheEnabled()) {
                    popDefaultEntityItems(_db, _prof, getParentActionItem());
                    // Par down some flag values for WG domain enforcement
                    enforceWorkGroupDomain(_db, _prof);
                }
                // Now.. add the new row
               	egEntity2Type.addRow();
            }
        }
        finally {

            _db.freeStatement();
            _db.isPending();

        }        
    }

    /*
     *  This represents the e-announce 1.1 constructor that will be used
     *  to generate all EntityLists from Create Actions.
     */
    /**
     *Constructor for the EntityList object
     *
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @param  _ai                             Description of the Parameter
     * @param  _aei                            Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public EntityList(Database _db, Profile _prof, EditActionItem _ai, EntityItem[] _aei) throws SQLException, MiddlewareException,
        MiddlewareRequestException {

        super(null, _prof, _prof.toString() + _ai.toString());
        int iSessionID = 0;
        ReturnStatus returnStatus = new ReturnStatus( -1);
        // attempt to use less memory
     	java.util.Hashtable memTbl = new Hashtable();
        try {

            MetaLink ml = null;
            EntityGroup eg = null;
            EditActionItem ai = null;
            boolean bEditKludge = false;

            iSessionID = _db.getNewSessionID();
            String strEnterprise = getProfile().getEnterprise();
            String strValOn = getProfile().getValOn();
            String strEffOn = getProfile().getEffOn();

            _db.test(_aei != null, "Entity Item array is null");
            _db.test(_aei.length > 0, "Entity Item array is zero length");
            _db.debug(D.EBUG_DETAIL, "EntityList w/ Edit Action Item: " + getKey()+" "+_ai.getActionItemKey());
            
            ml = _ai.getMetaLink();
            // If we do not have a meta link attrbite and we still want to use a relator for editing.
            // we must produce it on the fly..
            // Otherwise.. we are skipping it all together...
            if (ml == null && !_ai.isEntityOnly()) {
                _db.debug(D.EBUG_DETAIL,
                          "There is no ML object in the EditActionItem.  We will manufacture one for:" + _aei[0].getEntityType());
                ml = new MetaLink(null, _db, _prof, _aei[0].getEntityType());
                _ai.setMetaLink(ml);
            }

            _db.test(! (ml == null && !_ai.isEntityOnly()),
                     "There is no ML object in the EditActionItem.. this is a serious problem.");
 
            setParentActionItem(_ai);
            setActionList(new ActionList(null, _db, getProfile(), getParentActionItem(), true));
            setTagInfo(_db, _prof);
            ai = (EditActionItem) getParentActionItem();

            // This represents all the possible Parent Entities The new children can be created from
            if (! (_ai.isEntityOnly() || _ai.isEditRelatorOnly())) {
                EntityGroup egRelator = null;
                EntityGroup egEntity2Type = null;

                // Always Navigate Here!
                setParentEntityGroup(new EntityGroup(this, _db, null, ml.getEntity1Type(), "Navigate",memTbl));
                egRelator = new EntityGroup(this, _db, null, ml.getEntityType(), _ai.getPurpose(),memTbl);
                putEntityGroup(egRelator);
                
                // Get the EntityGroup for the Entity2Type
                egEntity2Type = new EntityGroup(this, _db, null, ml.getEntity2Type(), _ai.getPurpose(),memTbl);
                putEntityGroup(egEntity2Type);
                
                for (int ii = 0; ii < _aei.length; ii++) {
                    // See what we got here
                    EntityItem ei = _aei[ii];
                    EntityItem eid = null;
                    EntityItem eir = null;
                    EntityItem eip = null;

                    if (ei.getEntityType().equals(egRelator.getKey())) {

                        eir = _aei[ii];
                        eip = (EntityItem) _aei[ii].getUpLink(0);
                        // If the relatorID > 0.. lets hook it all up.. else .. simply place the parent in the Parent EntityGroup
                        if (eir.getEntityID() > 0) {
                            _db.test(eip != null, "There is no Linkable parent on this guy!");
                            eid = (EntityItem) _aei[ii].getDownLink(0);
                            _db.test(eid != null, "There is no downlink on this guy!");

                            eir.resetAttribute();
                            eid.resetAttribute();
                            eip.resetAttribute();

                            getParentEntityGroup().putEntityItem(eip);
                            eip.reassign(getParentEntityGroup());

                            egRelator.putEntityItem(eir);
                            eir.reassign(egRelator);

                            egEntity2Type.putEntityItem(eid);
                            eid.reassign(egEntity2Type);
                        }
                        else {
                            getParentEntityGroup().putEntityItem(eip);
                            eip.reassign(getParentEntityGroup());
                        }

                    }
                    else if (ei.getEntityType().equals(egEntity2Type.getKey())) {
                        // We have came across a scenario where
                        // the relator was never passed into the edit
                        // So we have to do some unnatural things
                        bEditKludge = true;
                        egEntity2Type.setForceMode(true);
                        egEntity2Type.setForceDisplay(true);
                        egRelator.setForceMode(true);
                        egRelator.setForceDisplay(false);
                        egEntity2Type.putEntityItem(ei);
                        ei.resetAttribute();
                        ei.reassign(egEntity2Type);
                    }
                }
            }
            else if (_ai.isEditRelatorOnly()) {
                EntityGroup egRelator = new EntityGroup(this, _db, null, ml.getEntityType(), _ai.getPurpose(),memTbl);
                //Always Navigate Here!
                EntityGroup egEntity1Type = new EntityGroup(this, _db, null, ml.getEntity1Type(), "Navigate",memTbl); //6MV4U7
                EntityGroup egEntity2Type = new EntityGroup(this, _db, null, ml.getEntity2Type(), "Navigate",memTbl);

                EntityGroup eg1Par = null; //6MV4U7
                EntityGroup eg1Kid = null; //6MV4U7
                EntityGroup eg2Par = null; //6MV4U7
                EntityGroup eg2Kid = null; //6MV4U7

                /*
                 6MV4U7
                 ------
                 If the relator's entity1type or entity2type is a relator then
                 we need to adjust the extract to get more information.
                 */

                if (egEntity1Type.isRelator()) { //6MV4U7
                    String strEType = ml.getEntity1Type(); //6MV4U7
                    MetaLink ml2 = new MetaLink(null, _db, _prof, strEType); //6MV4U7
                    if (ml2 != null) { //6MV4U7
                        eg1Par = new EntityGroup(this, _db, null, ml2.getEntity1Type(), "Navigate",memTbl); //6MV4U7
                        eg1Kid = new EntityGroup(this, _db, null, ml2.getEntity2Type(), "Navigate",memTbl); //6MV4U7
                        eg1Par.setForceMode(true); //6MV4U7
                        eg1Par.setForceDisplay(false); //6MV4U7
                        eg1Kid.setForceMode(true); //6MV4U7
                        eg1Kid.setForceDisplay(false); //6MV4U7
                        putEntityGroup(eg1Par); //6MV4U7
                        putEntityGroup(eg1Kid); //6MV4U7
                    } //6MV4U7
                } //6MV4U7
                if (egEntity2Type.isRelator()) { //6MV4U7
                    String strEType = ml.getEntity2Type(); //6MV4U7
                    MetaLink ml2 = new MetaLink(null, _db, _prof, strEType); //6MV4U7
                    if (ml2 != null) { //6MV4U7
                        eg2Par = new EntityGroup(this, _db, null, ml2.getEntity1Type(), "Navigate",memTbl); //6MV4U7
                        eg2Kid = new EntityGroup(this, _db, null, ml2.getEntity2Type(), "Navigate",memTbl); //6MV4U7
                        eg2Par.setForceMode(true); //6MV4U7
                        eg2Par.setForceDisplay(false); //6MV4U7
                        eg2Kid.setForceMode(true); //6MV4U7
                        eg2Kid.setForceDisplay(false); //6MV4U7
                        putEntityGroup(eg2Par); //6MV4U7
                        putEntityGroup(eg2Kid); //6MV4U7
                    } //6MV4U7
                } //6MV4U7

                // Always Navigate Here!
//6MV4U7                setParentEntityGroup(new EntityGroup(this, _db, null, ml.getEntity1Type(), "Navigate"));

                setParentEntityGroup(egEntity1Type); //6MV4U7
                putEntityGroup(egRelator);
                putEntityGroup(egEntity2Type);
 
                for (int ii = 0; ii < _aei.length; ii++) {

                    EntityItem eir = null;
                    EntityItem eip = null;
                    EntityItem eid = null;

                    EntityItem ei = _aei[ii];
                    if (ei.getEntityType().equals(egRelator.getKey())) {
                        eir = _aei[ii];
                        eip = (EntityItem) _aei[ii].getUpLink(0);

                        // If the relatorID > 0.. lets hook it all up..
                        if (eir.getEntityID() > 0) {
                            _db.test(eip != null, "There is no Linkable parent on this guy!");
                            eid = (EntityItem) _aei[ii].getDownLink(0);
                            _db.test(eid != null, "There is no downlink on this guy!");

                            eir.resetAttribute();
                            eid.resetAttribute();
                            eip.resetAttribute();

                            getParentEntityGroup().putEntityItem(eip);
                            eip.reassign(getParentEntityGroup());

                            //egEntity1Type.putEntityItem(eip);
                            //eip.reassign(egEntity1Type);

                            egRelator.putEntityItem(eir);
                            eir.reassign(egRelator);

                            egEntity2Type.putEntityItem(eid);
                            eid.reassign(egEntity2Type);
                        }
                        else {
                            getParentEntityGroup().putEntityItem(eip);
                            eip.reassign(getParentEntityGroup());

                        }

                    }
                    else if (ei.getEntityType().equals(egEntity2Type.getKey())) {
                        // We have came across a scenario where
                        // the relator was never passed into the edit
                        // So we have to do some unnatural things
                        bEditKludge = true;
                        egEntity2Type.setForceMode(true);
                        egEntity2Type.setForceDisplay(true);
                        egRelator.setForceMode(true);
                        egRelator.setForceDisplay(false);
                        egEntity2Type.putEntityItem(ei);
                        ei.resetAttribute();
                        ei.reassign(egEntity2Type);
                    }
                }
            }
            else {
                // Get the EntityGroup for the Entity2Type
                EntityGroup egEntity2Type = new EntityGroup(this, _db, _prof, _ai.getTargetEntity(), _ai.getPurpose(),memTbl);
                putEntityGroup(egEntity2Type);
                // Force this guy to show its true colors
                egEntity2Type.setForceMode(true);
                egEntity2Type.setForceDisplay(true);

                for (int ii = 0; ii < _aei.length; ii++) {
                    // See what we got here
                    EntityItem ei = _aei[ii];
                    // We are dealing with an Entity2Type only edit
                    if (ei.getEntityType().equals(egEntity2Type.getKey())) {
                        egEntity2Type.putEntityItem(ei);
                        ei.resetAttribute();
                        ei.reassign(egEntity2Type);
                    }
                }

                // the relator and parent are not needed for Edit, but if a new entity is
                // created they are needed.  problem with search-edit-create
                // search doesnt return structure, edit doesnt need it, but create needs to link to parent
                //MN 38666284 - CQ00022911
                if (_ai.canCreate()){
                	if(ml!=null){
                		repairStructure(_db, _prof, _aei, ml, memTbl);
                	}else{
                		_db.debug(D.EBUG_ERR, "Warning: "+_ai.getActionItemKey()+" can create but does not have MetaLink defining relator");
                	}
                } 
            }

            // Now all the groups are set up lets
            // par down some flag values for WG domain enforcement
            enforceWorkGroupDomain(_db, _prof);

            // Lets assumme the simple case where the entityid's are relators .. their parents are the ParentEntity types
            // and their kids are the entity2type
            // we believe that you truely only edit things through a relator.. not as stand alone things...
            // We will see how far we can take this...
            popDefaultEntityItems(_db, _prof, getParentActionItem());

            // Now .. for each Relator.. we need to place it into the trsNavigateTable

            if (ai.isEntityOnly()) {
                eg = getEntityGroup(_ai.getTargetEntity());
            }
            else if (bEditKludge) {
                eg = getEntityGroup(ml.getEntity2Type());
            }
            else {
                eg = getEntityGroup(ml.getEntityType());
            }
            for (int ii = 0; ii < eg.getEntityItemCount(); ii++) {
                String strEntityType;
                int iEntityID;
                String strEntity1Type;
                int iEntity1ID;
                String strEntity2Type;
                int iEntity2ID;

                if (ai.isEntityOnly()) {
                    EntityItem ei = eg.getEntityItem(ii);
                    strEntity2Type = ei.getEntityType();
                    iEntity2ID = ei.getEntityID();

                    _db.debug(D.EBUG_DETAIL,
                              "gbl8115:params:" + iSessionID + ":" + strEnterprise + ":" + _ai.getActionItemKey() + ":" +
                              strEntity2Type + ":" + iEntity2ID + ":" + strValOn + ":" + strEffOn + ":");
                    _db.callGBL8115(returnStatus, iSessionID, strEnterprise, _ai.getActionItemKey(), strEntity2Type, iEntity2ID,
                                    strValOn, strEffOn);
                    _db.freeStatement();
                    _db.isPending();
                }
                else {
                    EntityItem eir = eg.getEntityItem(ii);
                    EntityItem eip = (EntityItem) eir.getUpLink(0);
                    EntityItem eic = (EntityItem) eir.getDownLink(0);
                    
                    strEntityType = eir.getEntityType();
                    iEntityID = eir.getEntityID();
                    strEntity1Type = (eip != null ? eip.getEntityType() : eir.getEntityType());
                    iEntity1ID = (eip != null ? eip.getEntityID() : eir.getEntityID());
                    strEntity2Type = (eic != null ? eic.getEntityType() : eir.getEntityType());
                    iEntity2ID = (eic != null ? eic.getEntityID() : eir.getEntityID());
                    _db.debug(D.EBUG_DETAIL,
                              "gbl1017:params:" + iSessionID + ":" + strEnterprise + ":" + strEntity1Type + ":" + iEntity1ID + ":" +
                              strEntityType + ":" + iEntityID + ":" + strEntity2Type + ":" + iEntity2ID + ":" + strValOn + ":" +
                              strEffOn + ":");
                    _db.callGBL1017(returnStatus, iSessionID, strEnterprise, strEntity1Type, iEntity1ID, strEntityType, iEntityID,
                                    strEntity2Type, iEntity2ID, strValOn, strEffOn);
                    _db.freeStatement();
                    _db.isPending();
                }
            }

            mapRelations(_db, _prof, iSessionID, memTbl); //6MV4U7

            // They are now in.. lets get the data! - NLS sensitive
            popAllAttributeValues(_db, _prof, iSessionID, _ai);

            //
            if (getParentActionItem().isMetaColumnOrderControl()) {
                buildActionItemColumnOrders(_db, getParentActionItem());
            }
            
            // UI actions need to know restrictions in edit, before getting lock
            for (int iD1 = 0; iD1 < this.getEntityGroupCount(); iD1++) {
            	EntityGroup egD = this.getEntityGroup(iD1);
            	for (int iD2 = 0; iD2 < egD.getEntityItemCount(); iD2++) {
            		EntityItem eiD = egD.getEntityItem(iD2);
            		eiD.refreshRestrictions();
            	}
            }
        }
        finally {
            _db.freeStatement();
            _db.isPending();
            memTbl.clear();
            // Now remove all the records to clean up after yourself
            _db.callGBL8105(returnStatus, iSessionID);
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
    }
    
    /**
     * the relator and parent are not needed for Edit, but if a new entity is
     * created they are needed.  problem with search-edit-create
     * search doesnt return structure, edit doesnt need it, but create needs to link to parent
     * MN 38666284 - CQ00022911
     * @param _db
     * @param _prof
     * @param _aei
     * @param ml
     * @throws MiddlewareException
     * @throws SQLException
     */
    private void repairStructure(Database _db, Profile _prof, EntityItem _aei[], MetaLink ml, Hashtable memTbl) 
    throws MiddlewareException, SQLException
    {
    	PreparedStatement ps = null;
    	try{
    		String strSQL1 =
    			"SELECT entityid,entity1id " +
    			"FROM opicm.relator "+
    			"WHERE enterprise = ? " +
    			"AND Entity2Type = ? " +
    			"AND Entity2Id = ? " +
    			"AND EntityType = ? " +
    			"AND Entity1Type = ? " +
    			"AND ValFrom <= current timestamp AND  current timestamp < ValTo " +
    			"AND EffFrom <= current timestamp AND current timestamp < EffTo "+
    			"with ur";

    		Connection con = _db.getPDHConnection();
    		ps = con.prepareStatement(strSQL1);
    		EntityGroup egRelator = null;
			
    		for (int ii = 0; ii < _aei.length; ii++) {
    			EntityItem ei = _aei[ii];   
    			// find relator and parent ids
    			int reid = 0;
    			int peid=0;
    			ResultSet rs = null;
    			ps.clearParameters();
    			try {
    				ps.setString(1,_prof.getEnterprise());
    				ps.setString(2,ei.getEntityType());
    				ps.setInt(3, ei.getEntityID());
    				ps.setString(4,ml.getEntityType());
    				ps.setString(5,ml.getEntity1Type());
    				rs = ps.executeQuery();

    				while(rs.next()) {
    					reid = rs.getInt(1);	
    					peid = rs.getInt(2);	
    				}

    				if (reid>0&&peid>0){
    					if (egRelator == null){
    						// Always Navigate to build group
    						setParentEntityGroup(new EntityGroup(this, _db, null, ml.getEntity1Type(), "Navigate",memTbl));
    						egRelator = new EntityGroup(this, _db, null, ml.getEntityType(),"Navigate",memTbl);
    						putEntityGroup(egRelator);
    					}
    					EntityItem relitem = new EntityItem(egRelator, null, egRelator.getEntityType(), reid);
    					egRelator.putEntityItem(relitem);
    					EntityItem parentitem = new EntityItem(this.getParentEntityGroup(), null, 
    							getParentEntityGroup().getEntityType(), peid);
    					this.getParentEntityGroup().putEntityItem(parentitem);
    					// Now hook them all up
    					relitem.putUpLink(parentitem);
    					relitem.putDownLink(ei);
    					_db.debug(D.EBUG_SPEW, "EntityList.repairStructure relitem "+relitem.getKey()+" parentitem "+parentitem.getKey());
    				}else{
    					// cant find parent for item getting edited so cant link any new ones back
    					_db.debug(D.EBUG_ERR, "EntityList.repairStructure NO parent found for "+ei.getKey());
    					_db.debug(D.EBUG_ERR, "Warning: "+this.getParentActionItem().getActionItemKey()+" can create but Parent could not be found");
    				}
    			} finally {
    				if(rs != null) {
    					rs.close();
    					rs = null;
    				}
    			}	  
    		}           
    	}finally{
    		if (ps != null) {
    			ps.close();
    			ps = null;
    		}	
    	}    
    }

    /**
     * EntityList
     *
     * @param _db
     * @param _prof
     * @param _ai
     * @param _aei
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public EntityList(Database _db, Profile _prof, CopyActionItem _ai, EntityItem[] _aei) throws SQLException, MiddlewareException,
        MiddlewareRequestException {

        super(null, _prof, _prof.toString() + _ai.toString());
        int iSessionID = 0;
        ReturnStatus returnStatus = new ReturnStatus( -1);
        try {

            CopyActionItem cai = null;
            EntityGroup egParent = null;
            EntityGroup egEntityType = null;
            int iDup = 0;

            // Lets go fill out the detail now
            String strEnterprise = getProfile().getEnterprise();
            String strValOn = getProfile().getValOn();
            String strEffOn = getProfile().getEffOn();
            iSessionID = _db.getNewSessionID();

            _db.debug(D.EBUG_DETAIL, "EntityList w/ CopyActionItem:" + getKey());
            _db.test(_aei != null, "EntityList.init.CopyActionItem. parent EntityItem array cannot be null!");

            setParentActionItem(_ai);
            cai = (CopyActionItem) getParentActionItem();

            setParentEntityGroup(new EntityGroup(this, _db, null, _aei[0].getEntityType(), "Edit", _ai.isUIAfterCacheEnabled()));
            // Lets set up the Parent Stuff
            setParentEntityItems(_aei);

            // We have to get default values here
            if (_ai.isUIAfterCacheEnabled()) {
                popDefaultEntityItems(_db, _prof, getParentActionItem());
                // there are no entity groups yet, at this point.. do this after creating an entitygroup
                //enforceWorkGroupDomain(_db, _prof);
            }

            egParent = getParentEntityGroup();
            //pull out all attributes for original entities
            for (int ii = 0; ii < egParent.getEntityItemCount(); ii++) {
                EntityItem ei = egParent.getEntityItem(ii);
                String strEntityType = ei.getEntityType();
                int iEntityID = ei.getEntityID();

                _db.debug(D.EBUG_DETAIL,
                          "gbl8115:params:" + iSessionID + ":" + strEnterprise + ":" + _ai.getActionItemKey() + ":" + strEntityType +
                          ":" + iEntityID + ":" + strValOn + ":" + strEffOn + ":");
                _db.callGBL8115(returnStatus, iSessionID, strEnterprise, _ai.getActionItemKey(), strEntityType, iEntityID, strValOn,
                                strEffOn);
                _db.freeStatement();
                _db.isPending();
            }

            // They are now in.. lets get the data! - NLS sensitive
            popAllAttributeValues(_db, _prof, iSessionID, _ai);

            // Now.. make copy
            egEntityType = new EntityGroup(this, _db, _prof, _ai.getTargetEntity(), "Edit", _ai.isUIAfterCacheEnabled());
            putEntityGroup(egEntityType);
            egEntityType.setForceMode(true);
            egEntityType.setForceDisplay(true);

            if (_ai.isUIAfterCacheEnabled()) {
                // there is an entity group now, so get the wg defaults
                enforceWorkGroupDomain(_db, _prof);
            }

            iDup = cai.getNumOfCopy();

            for (int j = 0; j < egParent.getEntityItemCount(); j++) {
                EntityItem eiOrg = egParent.getEntityItem(j);
                if (eiOrg.getEntityType().equals(egEntityType.getEntityType())) {
                    for (int i = 0; i < iDup; i++) {
                        EntityItem ei = new EntityItem(egEntityType, null, egEntityType.getEntityType(), cai.getID());
                        egEntityType.putEntityItem(ei);
                        ei.duplicate(eiOrg);
                    }
                }
            }
        }
        catch (EANBusinessRuleException _bre) {
            _bre.printStackTrace();
        }
        finally {
            _db.freeStatement();
            _db.isPending();

            // Now remove all the records to clean up after yourself
            _db.callGBL8105(returnStatus, iSessionID);
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * EntityList
     *
     * @param _db
     * @param _prof
     * @param _ai
     * @param _aei
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public EntityList(Database _db, Profile _prof, ABRStatusActionItem _ai, EntityItem[] _aei) throws SQLException,
        MiddlewareException, MiddlewareRequestException {

        super(null, _prof, _prof.toString() + _ai.toString());

        int iSessionID = 0;
        ReturnStatus returnStatus = new ReturnStatus( -1);
        try {

            EntityGroup eg = null;

            // Lets go fill out the detail now
            String strEnterprise = getProfile().getEnterprise();
            String strValOn = getProfile().getValOn();
            String strEffOn = getProfile().getEffOn();
            int iNLSID = getProfile().getReadLanguage().getNLSID();
            iSessionID = _db.getNewSessionID();

            _db.debug(D.EBUG_DETAIL, "EntityList w/ ABRStatusActionItem:" + getKey());
            _db.test(_aei != null, "EntityList.init.ABRStatusActionItem. parent EntityItem array cannot be null!");

            setParentActionItem(_ai);
            getParentActionItem();

            setParentEntityGroup(new EntityGroup(this, _db, null, _aei[0].getEntityType(), "Navigate", true));
            // Lets set up the Parent Stuff
            setParentEntityItems(_aei);
            eg = new EntityGroup(this, _db, null, _aei[0].getEntityType(), "ABRStatus", true);
            putEntityGroup(eg);

            //pull out all attributes for original entities

            for (int ii = 0; ii < _aei.length; ii++) {
                EntityItem ei = _aei[ii];
                String strEntityType = ei.getEntityType();
                int iEntityID = ei.getEntityID();

                eg.putEntityItem(ei);
                ei.reassign(eg);
                _db.debug(D.EBUG_DETAIL,
                          "gbl8115:params:" + iSessionID + ":" + strEnterprise + ":" + _ai.getActionItemKey() + ":" + strEntityType +
                          ":" + iEntityID + ":" + strValOn + ":" + strEffOn + ":");
                _db.callGBL8115(returnStatus, iSessionID, strEnterprise, _ai.getActionItemKey(), strEntityType, iEntityID, strValOn,
                                strEffOn);
                _db.freeStatement();
                _db.isPending();
            }

            // They are now in.. lets get the data! - NLS sensitive
            popABRStatusAttributeValues(_db, strEnterprise, iSessionID, iNLSID, strValOn, strEffOn);

        }
        finally {
            _db.freeStatement();
            _db.isPending();

            // Now remove all the records to clean up after yourself
            _db.callGBL8105(returnStatus, iSessionID);
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
    }

    /*
     *  This represents the e-announce 1.1 constructor that will be used
     *  to generate all EntityLists from a Search Action Item.
     */
    /**
     *Constructor for the EntityList object
     *
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @param  _ai                             Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     * original code
    public EntityList(Database _db, Profile _prof, SearchActionItem _ai) throws SQLException, MiddlewareException,
        MiddlewareRequestException {

        super(null, _prof, _prof.toString() + _ai.toString());
        
        ReturnStatus returnStatus = new ReturnStatus( -1);
        int iSessionID = 0;
        int iNextSessionID = 0;
        // attempt to use less memory
     	java.util.Hashtable memTbl = new Hashtable();
        try {   	
        	// save timestamp for this pull of data
    		setExecutionDTS(_db);
    		
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            ReturnDataResultSet rdrs1 = null;
            String strEnterprise = getProfile().getEnterprise();
            String strValOn = getProfile().getValOn();
            String strEffOn = getProfile().getEffOn();
            String strActionItemKey = _ai.getActionItemKey();
            int iOPWGID = getProfile().getOPWGID();
            int iWGID = getProfile().getWGID();
            int iNLSID = getProfile().getReadLanguage().getNLSID();
            iSessionID = _db.getNewSessionID();

            setParentActionItem(_ai);

            if (_ai.isWorkflowEnabled()) {
                setActionList(new ActionList(null, _db, getProfile(), getParentActionItem(), true));
                // Set the Tag Info
                setTagInfo(_db, _prof);
            }

            // If we have parentinfo to tend to...
            // Lets put it in an entityGroup and go for it
            if (_ai.isParentInfoPresent()) {
                setParentEntityGroup(new EntityGroup(this, _db, null, _ai.getParentEntityType(), "Navigate",
                    _ai.isUIAfterCacheEnabled(),memTbl));
                setParentEntityItems(_ai.getParentEntityItems());
            }

            // Lets discover what type of Search This is.. if it is not a DynaSearch..

            if (_ai.isGrabByEntityID()) {
                if (_ai.getGrabEntityID() < 0) {
                    _db.debug(D.EBUG_WARN, "EntityList w/ SearchActionItem. No GrabEntityID set.");
                }
                else {
                    // first let's store the EntityGroup from ActionItem...
                    //EntityGroup egD = _ai.getEntityGroup(_db);
                    EntityGroup egD = new EntityGroup(this, _db, getProfile(), _ai.getEntityGroup(_db).getEntityType(),
                        _ai.getPurpose(), _ai.isUIAfterCacheEnabled(),memTbl);
                    Vector vctRems = new Vector(egD.getEntityItemCount());
                    putEntityGroup(egD);
                    // purge this EntityGroup of any EntityItems which may be in there already -- we've had a -1 EID b4
                    for (int i = 0; i < egD.getEntityItemCount(); i++) {
                        vctRems.addElement(egD.getEntityItem(i));
                    }
                    for (int i = 0; i < vctRems.size(); i++) {
                        egD.removeEntityItem( (EntityItem) vctRems.elementAt(i));
                    }
                    // we're o.k., so load the trsnavigate table w/ our EID...
                    _db.debug(D.EBUG_DETAIL,
                              "EntityList w/ SearchActionItem. Loading TrsNavigate w/ GrabEntityID:" +
                              _ai.getEntityGroup(_db).getEntityType() + ":" + _ai.getGrabEntityID() + ".");
                    try {
                        rs = _db.callGBL1018(returnStatus, iSessionID, strEnterprise, egD.getEntityType(), _ai.getGrabEntityID(),
                                             strValOn, strEffOn);
                        rdrs = new ReturnDataResultSet(rs);
                    }
                    finally {
						if (rs !=null){
                        	rs.close();
                        	rs = null;
						}
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                    }

                    for (int i = 0; i < rdrs.getRowCount(); i++) {
                        String strEntityTypeD = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 0));
                        int iEntityIDD = rdrs.getColumnInt(i, 1);
                        EntityItem eiD = new EntityItem(egD, null, strEntityTypeD, iEntityIDD);
                        _db.debug(D.EBUG_SPEW, "gbl1018:answers:" + strEntityTypeD + ":" + iEntityIDD);
                        egD.putEntityItem(eiD);
                    }
                }

            }
            else if (_ai.isDynaSearchEnabled()) {
                EANList eiFilterList = new EANList();
                String strParentEntityType = _ai.getParentEntityType();
                String strFilterEntityType = "";
                EntityList elFilter = null;
                EANList eiList = _ai.getParentEntityItems();

                _db.debug(D.EBUG_DETAIL, "EntityList w/ SearchActionItem.DynaSearch TRACE " + getKey());

                _db.debug(D.EBUG_SPEW, " filter list isFilterByEntity: " + strParentEntityType + ":" + _ai.hasFilterByEntity());
                if (_ai.hasFilterByEntity()) {
                    _db.test(eiList.size() > 0, "There is no Parent Entity Items in your Search Object to find data!");
                    String strNavAction = _ai.getFilterActionKey(strParentEntityType);
                    _db.test(strNavAction.length() > 0, "Navigate Action is blank in your Search Object to find data!");
                    if (eiList.size() > 0) {
                        EntityItem[] aei = {
                            (EntityItem) eiList.getAt(0)};
                        try {
                            NavActionItem nai = new NavActionItem(null, _db, _prof, strNavAction);
                            strFilterEntityType = nai.getTargetEntityGroup();
                            elFilter = EntityList.getEntityList(_db, _prof, nai, aei);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        _db.debug(D.EBUG_SPEW, " filter list isFilterByEntity: " + strFilterEntityType);

                        EntityGroup eg = elFilter.getEntityGroup(strFilterEntityType);

                        if (eg != null) {
                            for (int j = 0; j < eg.getEntityItemCount(); j++) {
                                eiFilterList.put(eg.getEntityItem(j));
                            }
                        }
                        else {
                            _db.debug(D.EBUG_SPEW, " filter list isFilterByEntity: eg is null");
                        }
                    }

                    //for debugging purpose
                    _db.debug(D.EBUG_SPEW, "filter entity list size:" + eiFilterList.size());
                    for (int i = 0; i < eiFilterList.size(); i++) {
                        EntityItem ei = (EntityItem) eiFilterList.getAt(i);
                        _db.debug(D.EBUG_SPEW, "filter list, entity:" + ei.getKey());
                    }
                }

                if (_ai.isGrabRelator()) {

                    // search for entities 1 first

                    EANList eltmp1 = new EANList(EANMetaEntity.LIST_SIZE);
                    EANList eltmp2 = new EANList(EANMetaEntity.LIST_SIZE);

                    int iStep1 = 0;

                    Vector vct1 = new Vector();
                    String strRelatorType = _ai.getTargetEntityGroup();
                    EntityGroup eg2 = _ai.getEntityGroup2();
                    EntityItem ei2 = eg2.getEntityItem(0);
                    EntityGroup eg1 = _ai.getEntityGroup1();
                    EntityItem ei1 = eg1.getEntityItem(0);

                    _db.test(ei1 != null, "There is no Entity Item in your Search Object 1 to use to find data!");
                    _db.test(ei2 != null, "There is no Entity Item in your Search Object 2 to use to find data!");

                    // Here .. we insert the filters for the search
                    // These fields should not be searchable.. (selectable by the user)
                    // If domain search enabled.. we need to get the list of currently enabled
                    // PDH Domains. and insert them into here now.
                    // Only do this if workflow is Enabled... for UI purposes DWB  This is for speed

                    if (_ai.isDomainControlled() && _ai.isWorkflowEnabled()) {
                        _db.debug(D.EBUG_DETAIL, "EntityList.DynaSearch.Domain grabrelator TRACE Controlled Enabled");
                        // Lets add all the PDH Domain Info here...
                        rs = _db.callGBL7065(returnStatus, strEnterprise, iWGID, strValOn, strEffOn);
                        rdrs = new ReturnDataResultSet(rs);
                        rs.close();
                        rs = null;
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();

                        for (int ii = 0; ii < rdrs.size(); ii++) {
                            String strAttributeCode = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 0));
                            String strAttributeValue = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 1));
                            String strAttributeType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 2));

                            _db.debug(D.EBUG_SPEW,
                                      "EntityList.search.grabrel.gbl7065:answers:" + strAttributeCode + ":" + strAttributeValue + ":" + strAttributeType);

							//RQ0713072645
                            if (strAttributeCode.equals(_ai.getWGDomainAttrCode())&&
                            	!strAttributeCode.equals(_ai.getDomainAttrCode())) {
                            	// use values from WG's attrcode, but use domainattrcode in entity search
                            	strAttributeCode = _ai.getDomainAttrCode();
                            	_db.debug(D.EBUG_SPEW,
                                      "gbl7065:answers override:" + _ai.getWGDomainAttrCode()+" to "+ strAttributeCode);
							} // end RQ0713072645

                            EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) eg1.getMetaAttribute(strAttributeCode);
                            eltmp2.put(new Implicator(null, getProfile(), strAttributeCode + strAttributeValue));

                            // if meta flag attribute not in entity group yet, need to tuck it in.
                            if (mfa == null && strAttributeCode.equals(_ai.getDomainAttrCode())) {
                                switch (strAttributeType.charAt(0)) {
                                    case 'F':
                                        mfa = new MetaMultiFlagAttribute(this, _db, getProfile(), strAttributeCode, true,memTbl);
                                        break;
                                    case 'U':
                                        mfa = new MetaSingleFlagAttribute(this, _db, getProfile(), strAttributeCode, true,memTbl);
                                        break;
                                    case 'S':
                                        mfa = new MetaStatusAttribute(this, _db, getProfile(), strAttributeCode, true,memTbl);
                                        break;
                                    case 'A':
                                        mfa = new MetaTaskAttribute(this, _db, getProfile(), strAttributeCode, true,memTbl);
                                        break;
                                    default:
                                        break;
                                }

                                if (mfa != null) {
                                    eg1.putMetaAttribute(mfa);
                                    eg2.putMetaAttribute(mfa);
                                }

                            }
                            if (mfa != null && (!eltmp1.containsKey(strAttributeCode)) &&
                                strAttributeCode.equals(_ai.getDomainAttrCode())) {
                                eltmp1.put(new Implicator(mfa, null, strAttributeCode));
                            }
                            // Make sure user didnt deactivate this attribute
                            if (strAttributeCode.equals(_ai.getDomainAttrCode())){
                            	EANFlagAttribute att = (EANFlagAttribute)ei1.getAttribute(strAttributeCode);
                            	if (att!=null &!att.isActive()){
                            		att.setActive(true);
                            	}
                            	att = (EANFlagAttribute)ei2.getAttribute(strAttributeCode);
                            	if (att!=null &!att.isActive()){
                            		att.setActive(true);
                            	}
                            }
                        }
                    }

                    // Go through each value on the change stack .. and find the values and place them into the
                    // dyna search table.

					//RQ0713072645
					// make sure user didnt deselect one of the domains
					Vector prevDefinedVct = new Vector();
					for (int ii = 0; ii < eltmp1.size(); ii++) {
						Implicator imp = (Implicator) eltmp1.getAt(ii);
						EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) imp.getParent();
						EANFlagAttribute fa = (EANFlagAttribute) ei1.getEANObject(eg1.getEntityType() + ":" +
							mfa.getAttributeCode());
						if(fa !=null){
							// Get all the Flag values.
							MetaFlag[] mfArray = (MetaFlag[]) fa.get();
							for (int i = 0; i < mfArray.length; i++)
							{
								// get selection
								if (mfArray[i].isSelected())
								{
									prevDefinedVct.addElement(fa);
									_db.debug(D.EBUG_SPEW,
										"EntityList.searchaction has predefined "+mfa.getAttributeCode()+" "+mfArray[i]);
									break;
								}
							}
						}
					}// end RQ0713072645

                    // Now we have everything that is needs to be worked on in the EANList
                    // So lets set all the flags because they have to be searched.
                    for (int ii = 0; ii < eltmp1.size(); ii++) {
                        Implicator imp = (Implicator) eltmp1.getAt(ii);
                        EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) imp.getParent();
                        EANFlagAttribute fa = (EANFlagAttribute) ei1.getEANObject(eg1.getEntityType() + ":" + mfa.getAttributeCode());
                        mfa.setDomainControlled(true);

						if (prevDefinedVct.contains(fa)){ // RQ0713072645
							_db.debug(D.EBUG_SPEW,
								"EntityList.searchaction skipping predefined "+fa.getAttributeCode());
							continue;
						}

                        // Protect Against a null pointer
                        if (fa != null) {
                            for (int iy = 0; iy < mfa.getMetaFlagCount(); iy++) {
                                MetaFlag mf = mfa.getMetaFlag(iy);
                                if (eltmp2.containsKey(mf.getKey())) {
                                    fa.put(mf.getFlagCode(), true);
                                }
                            }
                        }
                        else {
                            _db.debug(" *** WARNING ***, domain enabled search ei1 " + ei1 + " does not contain " +
                                      mfa.getAttributeCode());
                        }
                    }
                    prevDefinedVct.clear();
                    // Now.. lets fill out the search table

                    // Do the Text first.. because they should yield quicker results than flags
                    for (int ii = 0; ii < eg1.getMetaAttributeCount(); ii++) {
                        String strEntityType = eg1.getEntityType();
                        EANMetaAttribute ma = eg1.getMetaAttribute(ii);
                        EANAttribute att = ei1.getAttribute(ma.getAttributeCode());
                        if ( (ma.isSearchable() || ma.isDomainControlled()) && att != null && !att.toString().equals("") &&
                            att.isActive() && att instanceof TextAttribute) {
                            iStep1++;
                            _db.callGBL8119(returnStatus, iSessionID, iStep1, strEnterprise, strEntityType, ma.getAttributeCode(),
                                            getSearchText(att.toString()));
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();
                        }
                    }

                    // Do non Text second
                    for (int ii = 0; ii < eg1.getMetaAttributeCount(); ii++) {

                        String strEntityType = eg1.getEntityType();
                        EANMetaAttribute ma = eg1.getMetaAttribute(ii);
                        EANAttribute att = ei1.getAttribute(ma.getAttributeCode());
                        if ( (ma.isSearchable() || ma.isDomainControlled()) && att != null && !att.toString().equals("") &&
                            att.isActive() && att instanceof EANFlagAttribute) {
                            EANFlagAttribute fa = (EANFlagAttribute) att;
                            MetaFlag[] amf = (MetaFlag[]) fa.get();
                            iStep1++;
                            for (int ij = 0; ij < amf.length; ij++) {
                                if (amf[ij].isSelected()) {
                                    _db.callGBL8119(returnStatus, iSessionID, iStep1, strEnterprise, strEntityType,
                                        ma.getAttributeCode(), amf[ij].getFlagCode());
                                    _db.commit();
                                    _db.freeStatement();
                                    _db.isPending();
                                }
                            }
                        }
                    }

                    // Is there an auto filter set
                    // the m_vctAutoFlagsFilter is greater than zero
                    // We need to insrt them here into the DynaSearch Table
                    // These should be last!!
                    for (int y = 0; y < _ai.m_vctAutoFlagFilter.size(); y++) {
                        String strTokens = (String) _ai.m_vctAutoFlagFilter.elementAt(y);
                        StringTokenizer st = new StringTokenizer(strTokens, ":");
                        String strEntityType = eg1.getEntityType();
                        String strAttributeCode = st.nextToken();
                        st.nextToken();
                        if (!vct1.contains(strEntityType + ":" + strAttributeCode)) {
                            vct1.addElement(strEntityType + ":" + strAttributeCode);
                        }
                    }
                    for (int y = 0; y < _ai.m_vctAutoFlagFilter.size(); y++) {
                        String strTokens = (String) _ai.m_vctAutoFlagFilter.elementAt(y);
                        StringTokenizer st = new StringTokenizer(strTokens, ":");
                        String strEntityType = eg1.getEntityType();
                        String strAttributeCode = st.nextToken();
                        String strAttributeValue = st.nextToken();
                        int iTmpStep = iStep1 + vct1.indexOf(strEntityType + ":" + strAttributeCode) + 1;
                        _db.callGBL8119(returnStatus, iSessionID, iTmpStep, strEnterprise, strEntityType, strAttributeCode,
                                        strAttributeValue);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                    }
                    iStep1 = iStep1 + vct1.size();

                    // find entity 1 first
                    try {
                        rs = _db.callGBL9200(returnStatus, iSessionID, strEnterprise, strActionItemKey,
                                             (_ai.isLikeMatchingEnabled() ? 1 : 0), strValOn, strEffOn,
                                             (_ai.checkLimit() ? Integer.parseInt(System.getProperty("SearchActionItem.limit", "100000")) : 200000));
                        rdrs = new ReturnDataResultSet(rs);
                    }
                    finally {
						if (rs!=null){
                        	rs.close();
                        	rs = null;
						}
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                    }

                    if (rdrs.size() > 0) {

                        iNextSessionID = _db.getNewSessionID();

                        // Just for Debug here..
                        for (int i = 0; i < rdrs.size(); i++) {
                            String strEntityType = rdrs.getColumn(i, 0);
                            int iEntityID = rdrs.getColumnInt(i, 1);
                            _db.debug(D.EBUG_SPEW, "gbl9200:answer:" + strEntityType + ":" + iEntityID);
                        }

                        // o.k.  let call one sp that moves the relator and all the info
                        // back into the trsNavigate  table.. with a complete image
                        try {
                            rs = _db.callGBL2954(returnStatus, iOPWGID, iSessionID, iNextSessionID, strEnterprise, strActionItemKey,
                                                 strRelatorType, strValOn, strEffOn);
                            rdrs1 = new ReturnDataResultSet(rs);
                        }
                        finally {
							if (rs !=null){
                            	rs.close();
                            	rs = null;
							}
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();
                        }
                        //
                        // OK.. we are now using a new session id
                        int tmp = iSessionID;
                        iSessionID = iNextSessionID;
                        iNextSessionID = tmp;

                        // if any e2's were found.. there is at least on potential relator out there
                        // and the  e2's are sitting there in the queue table.
                        //
                        if (rdrs1.size() > 0) {

                            Vector vct2 = new Vector();
                            int iStep2 = 0;

                            for (int ii = 0; ii < eltmp1.size(); ii++) {
                                Implicator imp = (Implicator) eltmp1.getAt(ii);
                                EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) imp.getParent();
                                EANFlagAttribute fa = (EANFlagAttribute) ei2.getEANObject(eg2.getEntityType() + ":" +
                                    mfa.getAttributeCode());
                                mfa.setDomainControlled(true);
                                //Generate the Object
                                // DWB.. if there is no flag attribute that matches and we have a domain controlled
                                // situation.. what do we do?
                                // for now.. we simply skip attempting to manipulate the the flags here..
                                if (fa != null) {
                                    for (int iy = 0; iy < mfa.getMetaFlagCount(); iy++) {
                                        MetaFlag mf = mfa.getMetaFlag(iy);
                                        if (eltmp2.containsKey(mf.getKey())) {
                                            fa.put(mf.getFlagCode(), true);
                                        }
                                    }
                                }
                                else {
                                    _db.debug(" *** WARNING ***, domain enabled search ei2 " + ei2 + " does not contain " +
                                              mfa.getAttributeCode());
                                }
                            }

                            // Now.. lets fill out the search table
                            // D.W.B.  Do the Text first.. because they should yield quicker results than flags
                            for (int ii = 0; ii < eg2.getMetaAttributeCount(); ii++) {
                                String strEntityType = eg2.getEntityType();
                                EANMetaAttribute ma = eg2.getMetaAttribute(ii);
                                EANAttribute att = ei2.getAttribute(ma.getAttributeCode());
                                if ( (ma.isSearchable() || ma.isDomainControlled()) && att != null && !att.toString().equals("") &&
                                    att.isActive() && att instanceof TextAttribute) {
                                    iStep2++;
                                    _db.callGBL8119(returnStatus, iSessionID, iStep2, strEnterprise, strEntityType,
                                        ma.getAttributeCode(), getSearchText(att.toString()));
                                    _db.commit();
                                    _db.freeStatement();
                                    _db.isPending();
                                }
                            }

                            // D.W.B.  Do non Text second
                            for (int ii = 0; ii < eg2.getMetaAttributeCount(); ii++) {

                                String strEntityType = eg2.getEntityType();
                                EANMetaAttribute ma = eg2.getMetaAttribute(ii);
                                EANAttribute att = ei2.getAttribute(ma.getAttributeCode());
                                if ( (ma.isSearchable() || ma.isDomainControlled()) && att != null && !att.toString().equals("") &&
                                    att.isActive() && att instanceof EANFlagAttribute) {
                                    EANFlagAttribute fa = (EANFlagAttribute) att;
                                    MetaFlag[] amf = (MetaFlag[]) fa.get();
                                    iStep2++;
                                    for (int ij = 0; ij < amf.length; ij++) {
                                        if (amf[ij].isSelected()) {
                                            _db.callGBL8119(returnStatus, iSessionID, iStep2, strEnterprise, strEntityType,
                                                ma.getAttributeCode(), amf[ij].getFlagCode());
                                            _db.commit();
                                            _db.freeStatement();
                                            _db.isPending();
                                        }
                                    }
                                }
                            }

                            // Is there an auto filter set
                            // the m_vctAutoFlagsFilter is greater than zero
                            // We need to insrt them here into the DynaSearch Table
                            // These should be last!!
                            for (int y = 0; y < _ai.m_vctAutoFlagFilter.size(); y++) {
                                String strTokens = (String) _ai.m_vctAutoFlagFilter.elementAt(y);
                                StringTokenizer st = new StringTokenizer(strTokens, ":");
                                String strEntityType = eg2.getEntityType();
                                String strAttributeCode = st.nextToken();
                                st.nextToken();
                                if (!vct2.contains(strEntityType + ":" + strAttributeCode)) {
                                    vct2.addElement(strEntityType + ":" + strAttributeCode);
                                }
                            }
                            for (int y = 0; y < _ai.m_vctAutoFlagFilter.size(); y++) {
                                String strTokens = (String) _ai.m_vctAutoFlagFilter.elementAt(y);
                                StringTokenizer st = new StringTokenizer(strTokens, ":");
                                String strEntityType = eg2.getEntityType();
                                String strAttributeCode = st.nextToken();
                                String strAttributeValue = st.nextToken();
                                int iTmpStep = iStep1 + vct2.indexOf(strEntityType + ":" + strAttributeCode) + 1;
                                _db.callGBL8119(returnStatus, iSessionID, iTmpStep, strEnterprise, strEntityType, strAttributeCode,
                                                strAttributeValue);
                                _db.commit();
                                _db.freeStatement();
                                _db.isPending();
                            }
                            iStep2 = iStep2 + vct2.size();

                            // Lets have an augmented search

                            rs = _db.callGBL9203(returnStatus, iSessionID, strEnterprise, strActionItemKey,
                                                 (_ai.isLikeMatchingEnabled() ? 1 : 0), strValOn, strEffOn);
                            rdrs = new ReturnDataResultSet(rs);
                            rs.close();
                            rs = null;
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();

                            for (int i = 0; i < rdrs.size(); i++) {

                                EntityGroup eGroupR = null;
                                EntityGroup eGroup2 = null;
                                EntityItem eItem1 = null;
                                EntityItem eItem2 = null;

                                String strEntity1Type = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 0));
                                int iEntity1ID = rdrs.getColumnInt(i, 1);
                                String strEntityType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 2));
                                int iEntityID = rdrs.getColumnInt(i, 3);
                                String strEntity2Type = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 4));
                                int iEntity2ID = rdrs.getColumnInt(i, 5);

                                EntityGroup eGroup1 = getEntityGroup(strEntity1Type);

                                _db.debug(D.EBUG_SPEW,
                                          "gbl9203:answer:" + strEntity1Type + ":" + iEntity1ID + ":" + strEntityType + ":" +
                                          iEntityID + ":" + strEntity2Type + ":" + iEntity2ID);

                                if (_ai.isFilterByEntity(strParentEntityType)) {
                                    if (strEntity1Type.equals(strFilterEntityType)) {
                                        EntityItem eiF1 = new EntityItem(null, _prof, strEntity1Type, iEntity1ID);
                                        if (eiFilterList.get(eiF1.getKey()) == null) {
                                            _db.debug(D.EBUG_SPEW,
                                                "not in filter list, skipping the entity:" + strEntity1Type + ":" + iEntity1ID);
                                            continue;
                                        }
                                    }

                                    if (strEntity2Type.equals(strFilterEntityType)) {
                                        EntityItem eiF2 = new EntityItem(null, _prof, strEntity2Type, iEntity2ID);
                                        if (eiFilterList.get(eiF2.getKey()) == null) {
                                            _db.debug(D.EBUG_SPEW,
                                                "not in filter list, skipping the entity:" + strEntity2Type + ":" + iEntity2ID);
                                            continue;
                                        }
                                    }

                                }

                                if ( (eGroup1 == null)) {
                                    _db.debug(D.EBUG_DETAIL, "EntityList.Search.Dyna.Making new EntityGroup:" + strEntity1Type);
                                    eGroup1 = new EntityGroup(this, _db, getProfile(), strEntity1Type, _ai.getPurpose(),
                                        _ai.isUIAfterCacheEnabled(),memTbl);
                                    putEntityGroup(eGroup1);

                                    if (isPicklist() || isPath()) {
                                        eGroup1.setForceMode(true);
                                        eGroup1.setForceDisplay(eGroup1.getKey().equals(_ai.getTargetEntityGroup()));
                                    }
                                }

                                // Lets get the Entity Item in there!
                                eItem1 = eGroup1.getEntityItem(strEntity1Type, iEntity1ID);
                                if (!eGroup1.containsEntityItem(strEntity1Type, iEntity1ID)) {
                                    eItem1 = new EntityItem(eGroup1, null, strEntity1Type, iEntity1ID);
                                    eGroup1.putEntityItem(eItem1);
                                }

                                eGroup2 = getEntityGroup(strEntity2Type);
                                if ( (eGroup2 == null)) {
                                    _db.debug(D.EBUG_DETAIL, "EntityList.Search.Dyna.Making new EntityGroup:" + strEntity2Type);
                                    eGroup2 = new EntityGroup(this, _db, getProfile(), strEntity2Type, _ai.getPurpose(),
                                        _ai.isUIAfterCacheEnabled(),memTbl);
                                    putEntityGroup(eGroup2);
                                    if (isPicklist() || isPath()) {
                                        eGroup2.setForceMode(true);
                                        eGroup2.setForceDisplay(eGroup2.getKey().equals(_ai.getTargetEntityGroup()));
                                    }

                                }

                                // Lets get the Entity Item in there!
                                eItem2 = eGroup2.getEntityItem(strEntity2Type, iEntity2ID);
                                if (!eGroup2.containsEntityItem(strEntity2Type, iEntity2ID)) {
                                    eItem2 = new EntityItem(eGroup2, null, strEntity2Type, iEntity2ID);
                                    eGroup2.putEntityItem(eItem2);
                                }

                                eGroupR = getEntityGroup(strEntityType);
                                if ( (eGroupR == null)) {
                                    _db.debug(D.EBUG_DETAIL, "EntityList.Search.Dyna.Making new EntityGroup:" + strEntityType);
                                    eGroupR = new EntityGroup(this, _db, getProfile(), strEntityType, _ai.getPurpose(),
                                        _ai.isUIAfterCacheEnabled(),memTbl);
                                    putEntityGroup(eGroupR);
                                    if (isPicklist() || isPath()) {
                                        eGroupR.setForceMode(true);
                                        eGroupR.setForceDisplay(eGroupR.getKey().equals(_ai.getTargetEntityGroup()));
                                    }

                                }

                                // Lets get the Entity Item in there!
                                if (!eGroupR.containsEntityItem(strEntityType, iEntityID)) {
                                    EntityItem eItemR = new EntityItem(eGroupR, null, strEntityType, iEntityID);
                                    eGroupR.putEntityItem(eItemR);
                                    eItemR.putUpLink(eItem1);
                                    eItemR.putDownLink(eItem2);
                                }
                            }
                        }
                    }
                }
                else {

                    int iStep = 0;

                    Vector vct1 = new Vector();

                    EntityGroup eg = _ai.getEntityGroup(_db);
                    EntityItem ei = eg.getEntityItem(0);
                    _db.test(ei != null, "There is no Entity Item in your Search Object to use to find data!");

                    // Go through each value on the change stack .. and find the values and place them into the
                    // dyna search table.

                    // Here .. we insert the filters for the search
                    // These fields should not be searchable.. (selectable by the user)
                    // If domain search enabled.. we need to get the list of currently enabled
                    // PDH Domains. and insert them into here now.
                    // Only do this if workflow is Enabled... for UI purposes DWB  This is for speed
                    if (_ai.isDomainControlled() && _ai.isWorkflowEnabled()) {

                        EANList eltmp1 = new EANList(EANMetaEntity.LIST_SIZE);
                        EANList eltmp2 = new EANList(EANMetaEntity.LIST_SIZE);

                        _db.debug(D.EBUG_DETAIL, "EntityList.DynaSearch.Domain TRACE Controlled Enabled");
                        // Lets add all the PDH Domain Info here...
                        try {
                            rs = _db.callGBL7065(returnStatus, strEnterprise, iWGID, strValOn, strEffOn);
                            rdrs = new ReturnDataResultSet(rs);
                        }
                        finally {
							if (rs !=null){
                            	rs.close();
                            	rs = null;
							}
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();
                        }
                        for (int ii = 0; ii < rdrs.size(); ii++) {
                            String strAttributeCode = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 0));
                            // This is the AttributeCode
                            String strAttributeValue = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 1));
                            String strAttributeType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 2));

                            _db.debug(D.EBUG_SPEW,
                                      "EntityList.search.gbl7065:answers:" + strAttributeCode + ":" + strAttributeValue + ":" + strAttributeType);

							//RQ0713072645
                            if (strAttributeCode.equals(_ai.getWGDomainAttrCode())&&
                            	!strAttributeCode.equals(_ai.getDomainAttrCode())) {
                            	// use values from WG's attrcode, but use domainattrcode in entity search
                            	strAttributeCode = _ai.getDomainAttrCode();
                            	_db.debug(D.EBUG_SPEW,
                                      "gbl7065:answers override:" + _ai.getWGDomainAttrCode()+" to "+ strAttributeCode);
							} // end RQ0713072645

                            // This is the AttributeCode
                            EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) eg.getMetaAttribute(strAttributeCode);

                            // Tuck it away
                            eltmp2.put(new Implicator(null, getProfile(), strAttributeCode + strAttributeValue));

                            // if meta flag attribute not in entity group yet, need to tuck it in.
                            if (mfa == null && strAttributeCode.equals(_ai.getDomainAttrCode())) {
                                switch (strAttributeType.charAt(0)) {
                                    case 'F':
                                        mfa = new MetaMultiFlagAttribute(this, _db, getProfile(), strAttributeCode, true, memTbl);
                                        break;
                                    case 'U':
                                        mfa = new MetaSingleFlagAttribute(this, _db, getProfile(), strAttributeCode, true, memTbl);
                                        break;
                                    case 'S':
                                        mfa = new MetaStatusAttribute(this, _db, getProfile(), strAttributeCode, true, memTbl);
                                        break;
                                    case 'A':
                                        mfa = new MetaTaskAttribute(this, _db, getProfile(), strAttributeCode, true,memTbl);
                                        break;
                                    default:
                                        break;
                                }

                                if (mfa != null) {
                                    eg.putMetaAttribute(mfa);
                                }
                            }
                            if (mfa != null && (!eltmp1.containsKey(strAttributeCode)) &&
                                strAttributeCode.equals(_ai.getDomainAttrCode())) {
                                eltmp1.put(new Implicator(mfa, null, strAttributeCode));
                            }
                            //Make sure user didnt deactivate this attribute
                            if (strAttributeCode.equals(_ai.getDomainAttrCode())){
                            	EANFlagAttribute att = (EANFlagAttribute)ei.getAttribute(strAttributeCode);
                            	if (att!=null && !att.isActive()){
                            		att.setActive(true);
                            	}                            	
                            }
                        }

                        // Now we have everything that is needs to be worked on in the EANList
                        // So lets set all the flags because they have to be searched.

						// RQ0713072645
                        // make sure user didnt deselect one of the domains
                        Vector prevDefinedVct = new Vector();
                        for (int ii = 0; ii < eltmp1.size(); ii++) {
                            Implicator imp = (Implicator) eltmp1.getAt(ii);
                            EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) imp.getParent();
                            EANFlagAttribute fa = (EANFlagAttribute) ei.getEANObject(eg.getEntityType() + ":" +
                                mfa.getAttributeCode());

							if(fa !=null){
								// Get all the Flag values.
								MetaFlag[] mfArray = (MetaFlag[]) fa.get();
								for (int i = 0; i < mfArray.length; i++)
								{
									// get selection
									if (mfArray[i].isSelected())
									{
										prevDefinedVct.addElement(fa);
										_db.debug(D.EBUG_SPEW,
											"EntityList.searchaction has predefined "+mfa.getAttributeCode()+" "+mfArray[i]);
										break;
									}
								}
							}
                        }// end RQ0713072645

                        for (int ii = 0; ii < eltmp1.size(); ii++) {
                            Implicator imp = (Implicator) eltmp1.getAt(ii);
                            EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) imp.getParent();
                            EANFlagAttribute fa = (EANFlagAttribute) ei.getEANObject(eg.getEntityType() + ":" +
                                mfa.getAttributeCode());
                            mfa.setDomainControlled(true);

                            if (prevDefinedVct.contains(fa)){ // RQ0713072645
	                            _db.debug(D.EBUG_SPEW,
									"EntityList.searchaction skipping predefined "+fa.getAttributeCode());
								continue;
							}

                            //Generate the Object
                            for (int iy = 0; iy < mfa.getMetaFlagCount(); iy++) {
                                MetaFlag mf = mfa.getMetaFlag(iy);
                                if (eltmp2.containsKey(mf.getKey())) {
                                    fa.put(mf.getFlagCode(), true);
                                }
                            }
                        }
                        prevDefinedVct.clear();
                   }

                    // Now.. lets fill out the search table

                    // D.W.B.  Do the Text first.. because they should yield quicker results than flags
                    for (int ii = 0; ii < eg.getMetaAttributeCount(); ii++) {
                        String strEntityType = eg.getEntityType();
                        EANMetaAttribute ma = eg.getMetaAttribute(ii);
                        EANAttribute att = ei.getAttribute(ma.getAttributeCode());
                        if ( (ma.isSearchable() || ma.isDomainControlled()) && att != null && !att.toString().equals("") &&
                            att.isActive() && att instanceof TextAttribute) {
                            iStep++;
                            _db.callGBL8119(returnStatus, iSessionID, iStep, strEnterprise, strEntityType, ma.getAttributeCode(),
                                            getSearchText(att.toString()));
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();
                        }
                    }

                    // D.W.B.  Do non Text second
                    for (int ii = 0; ii < eg.getMetaAttributeCount(); ii++) {

                        String strEntityType = eg.getEntityType();
                        EANMetaAttribute ma = eg.getMetaAttribute(ii);
                        EANAttribute att = ei.getAttribute(ma.getAttributeCode());
                        if ( (ma.isSearchable() || ma.isDomainControlled()) && att != null && !att.toString().equals("") &&
                            att.isActive() && att instanceof EANFlagAttribute) {
                            EANFlagAttribute fa = (EANFlagAttribute) att;
                            MetaFlag[] amf = (MetaFlag[]) fa.get();
                            iStep++;
                            for (int ij = 0; ij < amf.length; ij++) {
                                if (amf[ij].isSelected()) {
                                    _db.callGBL8119(returnStatus, iSessionID, iStep, strEnterprise, strEntityType,
                                        ma.getAttributeCode(), amf[ij].getFlagCode());
                                    _db.commit();
                                    _db.freeStatement();
                                    _db.isPending();
                                }
                            }
                        }
                    }

                    // Is there an auto filter set
                    // the m_vctAutoFlagsFilter is greater than zero
                    // We need to insrt them here into the DynaSearch Table
                    // These should be last!!
                    for (int y = 0; y < _ai.m_vctAutoFlagFilter.size(); y++) {
                        String strTokens = (String) _ai.m_vctAutoFlagFilter.elementAt(y);
                        StringTokenizer st = new StringTokenizer(strTokens, ":");
                        String strEntityType = eg.getEntityType();
                        String strAttributeCode = st.nextToken();
                        st.nextToken();
                        if (!vct1.contains(strEntityType + ":" + strAttributeCode)) {
                            vct1.addElement(strEntityType + ":" + strAttributeCode);
                        }
                    }
                    for (int y = 0; y < _ai.m_vctAutoFlagFilter.size(); y++) {
                        String strTokens = (String) _ai.m_vctAutoFlagFilter.elementAt(y);
                        StringTokenizer st = new StringTokenizer(strTokens, ":");
                        String strEntityType = eg.getEntityType();
                        String strAttributeCode = st.nextToken();
                        String strAttributeValue = st.nextToken();
                        int iTmpStep = iStep + vct1.indexOf(strEntityType + ":" + strAttributeCode) + 1;
                        _db.callGBL8119(returnStatus, iSessionID, iTmpStep, strEnterprise, strEntityType, strAttributeCode,
                                        strAttributeValue);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                    }
                    iStep = iStep + vct1.size();

                    // Now they are all in.. lets do the Dyna Search
                    // We need to see now if we are searching from the queue..
                    if (_ai.isQueueSearch()) {
                        try {
                            rs = _db.callGBL8127(returnStatus, iSessionID, strEnterprise, _ai.getActionItemKey(),
                                                 (_ai.isLikeMatchingEnabled() ? 1 : 0), _ai.m_strQueueSearch);
                            rdrs = new ReturnDataResultSet(rs);
                        }
                        finally {
							if (rs != null){
                            	rs.close();
                            	rs = null;
							}
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();
                        }
                    }
                    else {

                        try {
                            rs = _db.callGBL9200(returnStatus, iSessionID, strEnterprise, _ai.getActionItemKey(),
                                                 (_ai.isLikeMatchingEnabled() ? 1 : 0), strValOn, strEffOn,
                                                 (_ai.checkLimit() ? Integer.parseInt(System.getProperty("SearchActionItem.limit", "100000")) :
                                                  200000));
                            rdrs = new ReturnDataResultSet(rs);
                        }
                        finally {
							if (rs!=null){
                            	rs.close();
                            	rs = null;
							}
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();
                        }
                    }

                    if (_ai.checkLimit()) {
                        if (rdrs.size() > SearchActionItem.SEARCHLIMIT) {

                            if (_ai.hasFilterByEntity() && (eiFilterList.size() < SearchActionItem.SEARCHLIMIT)) {
                                System.out.println("search filter by entity. filterlist size: " + eiFilterList.size() + " less than " +
                                    SearchActionItem.SEARCHLIMIT);
                            }
                            else {

                                // might want to clean up before throwing exception
                                // DWB Temp stop this for debugs
                                _db.callGBL8105(returnStatus, iSessionID);
                                _db.commit();
                                _db.freeStatement();
                                _db.isPending();

                                String msg =  " Number of records exceeds the limit of " + SearchActionItem.SEARCHLIMIT +
                                ". Please refine the search. (ok)";
                                D.isplay("Assertion failed: " + msg);
                                throw new SearchExceedsLimitException(msg);
                                //_db.test(rdrs.size() <= SearchActionItem.SEARCHLIMIT,
                                  //       " Number of records exceeds the limit of " + SearchActionItem.SEARCHLIMIT +
                                    //     ". Please refine the search. (ok)");
                            }
                        }
                    }

                    for (int i = 0; i < rdrs.size(); i++) {

                        EntityItem ei1 = null;

                        String strEntityType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 0));
                        int iEntityID = rdrs.getColumnInt(i, 1);

                        EntityGroup eg1 = getEntityGroup(strEntityType);

                        _db.debug(D.EBUG_SPEW, "gbl9200/gbl8127:answer:" + strEntityType + ":" + iEntityID + ":");

                        if ( (eg1 == null)) {
                            _db.debug(D.EBUG_DETAIL, "EntityList.Search.Dyna.Making new EntityGroup:" + strEntityType);
                            eg1 = new EntityGroup(this, _db, getProfile(), strEntityType, _ai.getPurpose(),
                                                  _ai.isUIAfterCacheEnabled(),memTbl);
                            putEntityGroup(eg1);

                            // If this is augmented for path or picklist either a picklist or a path..
                            // we need to shut down isdisplayables on everything except the target EG
                            if (isPicklist() || isPath()) {

                                eg.setForceMode(true);
                                eg.setForceDisplay(eg.getKey().equals(_ai.getTargetEntityGroup()));
                            }
                        }

                        if (_ai.isFilterByEntity(strParentEntityType)) {
                            if (strEntityType.equals(strFilterEntityType)) {
                                EntityItem eiF = new EntityItem(null, _prof, strEntityType, iEntityID);
                                if (eiFilterList.get(eiF.getKey()) == null) {
                                    _db.debug(D.EBUG_SPEW, "not in filter list, skipping the entity:" + strEntityType + ":" + iEntityID);
                                    continue;
                                }
                            }
                        }

                        // Lets get the Entity Item in there!
                        if (!eg1.containsEntityItem(strEntityType, iEntityID)) {
                            ei1 = new EntityItem(eg1, null, strEntityType, iEntityID);
                            eg1.putEntityItem(ei1);
                        }
                    }
                }
            }
            else {

                int iStep = 1;

                String strSearchString = _ai.getSearchString();

                _db.debug(D.EBUG_DETAIL, "EntityList w/ SearchActionItem.Search:" + getKey());

                //
                // There can be multiple target Entity Types's so lets get them here
                //
                if (_ai.useSearchRequest()) {
                    SearchRequest sr = _ai.getSearchRequest();

                    for (int i = 0; i < sr.getSearchTargetCount(); i++) {
                        SearchTarget st = sr.getSearchTarget(i);
                        if (st.isSelected()) {
                            String strEntityType = st.getEntityType();
                            String strAttributeCode = st.getAttributeCode();
                            EntityGroup eg = getEntityGroup(strEntityType);

                            _db.callGBL8119(returnStatus, iSessionID, iStep, strEnterprise, strEntityType, strAttributeCode,
                                            strSearchString);
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();

                            if (eg == null) {
                                eg = new EntityGroup(this, _db, _prof, strEntityType, _ai.getPurpose(), 
                                		_ai.isUIAfterCacheEnabled(),memTbl);
                                putEntityGroup(eg);
                                // If this is augmented for path or picklist either a picklist or a path..
                                // we need to shut down isdisplayables on everything except the target EG
                                if (isPicklist() || isPath()) {
                                    eg.setForceMode(true);
                                    eg.setForceDisplay(eg.getKey().equals(_ai.getTargetEntityGroup()));
                                }
                            }
                        }
                    }

                }
                else {
                    SearchRequest sr = _ai.getSearchRequest();

                    for (int i = 0; i < sr.getSearchTargetCount(); i++) {
                        SearchTarget st = sr.getSearchTarget(i);
                        String strEntityType = st.getEntityType();
                        String strAttributeCode = st.getAttributeCode();
                        EntityGroup eg = getEntityGroup(strEntityType);

                        _db.callGBL8119(returnStatus, iSessionID, iStep, strEnterprise, strEntityType, strAttributeCode,
                                        strSearchString);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();

                        if (eg == null) {
                            eg = new EntityGroup(this, _db, _prof, strEntityType, _ai.getPurpose(), 
                            		_ai.isUIAfterCacheEnabled(),memTbl);
                            putEntityGroup(eg);
                            // If this is augmented for path or picklist either a picklist or a path..
                            // we need to shut down isdisplayables on everything except the target EG
                            if (isPicklist() || isPath()) {
                                eg.setForceMode(true);
                                eg.setForceDisplay(eg.getKey().equals(_ai.getTargetEntityGroup()));
                            }
                        }
                    }
                }

                // Now we go after the data
                // Lets now execute the basic Navigation transaction...

                _db.debug(D.EBUG_DETAIL,
                          "gbl8001:params:" + iSessionID + ":" + strEnterprise + ":" + strActionItemKey + ":" + strSearchString +
                          ":" + iWGID + ":" + strValOn + ":" + strEffOn);
                rs = _db.callGBL8001(returnStatus, iSessionID, strEnterprise, strActionItemKey, strSearchString, iWGID, strValOn,
                                     strEffOn);
                rdrs = new ReturnDataResultSet(rs);
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
                if (_ai.checkLimit()) {
                    if (rdrs.size() > SearchActionItem.SEARCHLIMIT) {
                        // might want to clean up before throwing exception
                        // DWB Testing here
                        _db.callGBL8105(returnStatus, iSessionID);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();

                        String msg =  " Number of records exceeds the limit of " + SearchActionItem.SEARCHLIMIT +
                        ". Please refine the search. (ok)";
                        D.isplay("Assertion failed: " + msg);
                        throw new SearchExceedsLimitException(msg);
                        //_db.test(rdrs.size() <= SearchActionItem.SEARCHLIMIT,
                          //       " Number of records exceeds the limit of " + SearchActionItem.SEARCHLIMIT +
                            //     ". Please refine the search. (ok)");
                    }
                }

                for (int i = 0; i < rdrs.size(); i++) {

                    EntityItem ei1 = null;

                    String strEntityType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 0).trim());
                    int iEntityID = rdrs.getColumnInt(i, 1);
                    EntityGroup eg1 = getEntityGroup(strEntityType);

                    _db.debug(D.EBUG_SPEW, "gbl8001:answer:" + strEntityType + ":" + iEntityID + ":");

                    if (eg1 == null) {
                        continue;
                    }

                    // Groups are now good so lets go onto items
                    // Esnure we use the EGParent Group when possible

                    if (!eg1.containsEntityItem(strEntityType, iEntityID)) {
                        ei1 = new EntityItem(eg1, null, strEntityType, iEntityID);
                        eg1.putEntityItem(ei1);
                    }
                }

                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();

            }

            if (_ai.pullAttributes()) {
                popAllAttributeValues(_db, _prof, iSessionID, _ai);
            }
            else {

//catalog mods                popNavAttributeValues(_db, strEnterprise, iSessionID, iNLSID, strValOn, strEffOn);
                popNavAttributeValues(_db, _ai, strEnterprise, iSessionID, iNLSID, strValOn, strEffOn); //catalog mods
            }

            if (getParentActionItem().isMetaColumnOrderControl()) {
                buildActionItemColumnOrders(_db, getParentActionItem());
            }

        }
        finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
           	memTbl.clear();
           	
            // Now remove all the records to clean up after yourself
            // DWB temp
            D.ebug(D.EBUG_SPEW, "Search cleanup session id's: " + iSessionID + ", " + iNextSessionID);
			int nTries = 3;
			do {
				returnStatus = new ReturnStatus(-1);
				_db.callGBL8105(returnStatus, iSessionID);
				_db.commit();
				_db.freeStatement();
				_db.isPending();
				if (returnStatus.intValue() != 0) {
					D.ebug(D.EBUG_DETAIL, "GBL8105 did not return SP_OK");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						D.ebug(D.EBUG_DETAIL, e.getMessage());
					}
				}
			} while (returnStatus.intValue() != 0 && nTries-- > 0);
			nTries = 3;
			do {
				returnStatus = new ReturnStatus(-1);
				_db.callGBL8105(returnStatus, iNextSessionID);
				_db.commit();
				_db.freeStatement();
				_db.isPending();
				if (returnStatus.intValue() != 0) {
					D.ebug(D.EBUG_DETAIL, "GBL8105 did not return SP_OK");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						D.ebug(D.EBUG_DETAIL, e.getMessage());
					}
				}
			} while (returnStatus.intValue() != 0 && nTries-- > 0);
        }
    }
     */
    public EntityList(Database _db, Profile _prof, SearchActionItem _ai) throws SQLException, MiddlewareException,
        MiddlewareRequestException {

        super(null, _prof, _prof.toString() + _ai.toString());
        
        ReturnStatus returnStatus = new ReturnStatus( -1);
        int iSessionID = 0;
        int iNextSessionID = 0;
        // attempt to use less memory
     	java.util.Hashtable memTbl = new Hashtable();
     	
        try {   	
        	// save timestamp for this pull of data
    		setExecutionDTS(_db);
    		
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            ReturnDataResultSet rdrs1 = null;
            String strEnterprise = getProfile().getEnterprise();
            String strValOn = getProfile().getValOn();
            String strEffOn = getProfile().getEffOn();
            String strActionItemKey = _ai.getActionItemKey();
            int iOPWGID = getProfile().getOPWGID();
            int iWGID = getProfile().getWGID();
            int iNLSID = getProfile().getReadLanguage().getNLSID();
            iSessionID = _db.getNewSessionID();
            EntityGroup cleanupFilteredGroup = null;

            setParentActionItem(_ai);

            if (_ai.isWorkflowEnabled()) {
                setActionList(new ActionList(null, _db, getProfile(), getParentActionItem(), true));
                // Set the Tag Info
                setTagInfo(_db, _prof);
            }

            // If we have parentinfo to tend to...
            // Lets put it in an entityGroup and go for it
            if (_ai.isParentInfoPresent()) {
                setParentEntityGroup(new EntityGroup(this, _db, null, _ai.getParentEntityType(), "Navigate",
                    _ai.isUIAfterCacheEnabled(),memTbl));
                setParentEntityItems(_ai.getParentEntityItems());
            }
            
            // Lets discover what type of Search This is.. if it is not a DynaSearch..

            if (_ai.isGrabByEntityID()) {
                if (_ai.getGrabEntityID() < 0) {
                    _db.debug(D.EBUG_WARN, "EntityList w/ SearchActionItem. No GrabEntityID set.");
                }
                else {
                    // first let's store the EntityGroup from ActionItem...
                    //EntityGroup egD = _ai.getEntityGroup(_db);
                    EntityGroup egD = new EntityGroup(this, _db, getProfile(), _ai.getEntityGroup(_db).getEntityType(),
                        _ai.getPurpose(), _ai.isUIAfterCacheEnabled(),memTbl);
                    Vector vctRems = new Vector(egD.getEntityItemCount());
                    putEntityGroup(egD);
                    // purge this EntityGroup of any EntityItems which may be in there already -- we've had a -1 EID b4
                    for (int i = 0; i < egD.getEntityItemCount(); i++) {
                        vctRems.addElement(egD.getEntityItem(i));
                    }
                    for (int i = 0; i < vctRems.size(); i++) {
                        egD.removeEntityItem( (EntityItem) vctRems.elementAt(i));
                    }
                    // we're o.k., so load the trsnavigate table w/ our EID...
                    _db.debug(D.EBUG_DETAIL,
                              "EntityList w/ SearchActionItem. Loading TrsNavigate w/ GrabEntityID:" +
                              _ai.getEntityGroup(_db).getEntityType() + ":" + _ai.getGrabEntityID() + ".");
                    try {
                        rs = _db.callGBL1018(returnStatus, iSessionID, strEnterprise, egD.getEntityType(), _ai.getGrabEntityID(),
                                             strValOn, strEffOn);
                        rdrs = new ReturnDataResultSet(rs);
                    }
                    finally {
						if (rs !=null){
                        	rs.close();
                        	rs = null;
						}
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                    }

                    for (int i = 0; i < rdrs.getRowCount(); i++) {
                        String strEntityTypeD = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 0));
                        int iEntityIDD = rdrs.getColumnInt(i, 1);
                        EntityItem eiD = new EntityItem(egD, null, strEntityTypeD, iEntityIDD);
                        _db.debug(D.EBUG_SPEW, "gbl1018:answers:" + strEntityTypeD + ":" + iEntityIDD);
                        egD.putEntityItem(eiD);
                    }
                }

            }
            else if (_ai.isDynaSearchEnabled()) {
                EANList eiFilterList = new EANList();
                String strParentEntityType = _ai.getParentEntityType();
                String strFilterEntityType = "";
                EntityList elFilter = null;
                EANList eiList = _ai.getParentEntityItems();

                _db.debug(D.EBUG_DETAIL, "EntityList w/ SearchActionItem.DynaSearch TRACE " + getKey());

                _db.debug(D.EBUG_SPEW, " filter list isFilterByEntity: " + strParentEntityType + ":" + _ai.hasFilterByEntity());
                if (_ai.hasFilterByEntity()) {
                    _db.test(eiList.size() > 0, "There is no Parent Entity Items in your Search Object to find data!");
                    String strNavAction = _ai.getFilterActionKey(strParentEntityType);
                    _db.test(strNavAction.length() > 0, "Navigate Action is blank in your Search Object to find data!");
                    if (eiList.size() > 0) {
                        EntityItem[] aei = {
                            (EntityItem) eiList.getAt(0)};
                        try {
                            NavActionItem nai = new NavActionItem(null, _db, _prof, strNavAction);
                            strFilterEntityType = nai.getTargetEntityGroup();
                            elFilter = EntityList.getEntityList(_db, _prof, nai, aei);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        _db.debug(D.EBUG_SPEW, " filter list isFilterByEntity: " + strFilterEntityType);

                        EntityGroup eg = elFilter.getEntityGroup(strFilterEntityType);

                        if (eg != null) {
                            for (int j = 0; j < eg.getEntityItemCount(); j++) {
                                eiFilterList.put(eg.getEntityItem(j));
                            }
                        }
                        else {
                            _db.debug(D.EBUG_SPEW, " filter list isFilterByEntity: eg is null");
                        }
                    }

                    //for debugging purpose
                    _db.debug(D.EBUG_SPEW, "filter entity list size:" + eiFilterList.size());
                    for (int i = 0; i < eiFilterList.size(); i++) {
                        EntityItem ei = (EntityItem) eiFilterList.getAt(i);
                        _db.debug(D.EBUG_SPEW, "filter list, entity:" + ei.getKey());
                    }
                }

                if (_ai.isGrabRelator()) {
                	
                    // search for entities 1 first
                    int iStep1 = 0;

                    String strRelatorType = _ai.getTargetEntityGroup();
                    EntityGroup eg2 = _ai.getEntityGroup2();
                    EntityItem ei2 = eg2.getEntityItem(0);
                    EntityGroup eg1 = _ai.getEntityGroup1();
                    EntityItem ei1 = eg1.getEntityItem(0);

                    _db.test(ei1 != null, "There is no "+eg1.getEntityType()+" Entity Item in your Search Object 1 to use to find data!");
                    _db.test(ei2 != null, "There is no "+eg2.getEntityType()+" Entity Item in your Search Object 2 to use to find data!");

                    // Here .. we insert the filters for the search
                    // These fields should not be searchable.. (selectable by the user)
                    // If domain search enabled.. we need to get the list of currently enabled
                    // PDH Domains. and insert them into here now.
                    // Only do this if workflow is Enabled... for UI purposes DWB  This is for speed
                    findSearchDomainControls(_db, eg1, eg2, ei1, ei2, memTbl);

                    // Go through each value on the change stack .. and find the values and place them into the
                    // dyna search table.

                    // Now.. lets fill out the search table
                    iStep1 = loadSearchTables(_db,iSessionID, eg1, ei1);
                    // Is there an auto filter set
                    // the m_vctAutoFlagsFilter is greater than zero
                    // We need to insert them here into the DynaSearch Table
                    // These should be last!!
                    iStep1 += loadSearchAutoFilters(_db, iSessionID, eg1.getEntityType(), iStep1);
                    // find entity 1 first
                    try {
                        rs = _db.callGBL9200(returnStatus, iSessionID, strEnterprise, strActionItemKey,
                                             (_ai.isLikeMatchingEnabled() ? 1 : 0), strValOn, strEffOn,
                                             (_ai.checkLimit() ? Integer.parseInt(System.getProperty("SearchActionItem.limit", "100000")) : 200000));
                        rdrs = new ReturnDataResultSet(rs);
                    }
                    finally {
						if (rs!=null){
                        	rs.close();
                        	rs = null;
						}
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                    }

                    if (rdrs.size() > 0) {

                        iNextSessionID = _db.getNewSessionID();

                        // Just for Debug here..
                        for (int i = 0; i < rdrs.size(); i++) {
                            String strEntityType = rdrs.getColumn(i, 0);
                            int iEntityID = rdrs.getColumnInt(i, 1);
                            _db.debug(D.EBUG_SPEW, "gbl9200:answer:" + strEntityType + ":" + iEntityID);
                        }

                        // o.k.  let call one sp that moves the relator and all the info
                        // back into the trsNavigate  table.. with a complete image
                        try {
                        	rs = null;
                            rs = _db.callGBL2954(returnStatus, iOPWGID, iSessionID, iNextSessionID, strEnterprise, strActionItemKey,
                                                 strRelatorType, strValOn, strEffOn);
                            rdrs1 = new ReturnDataResultSet(rs);
                        }
                        finally {
							if (rs !=null){
                            	rs.close();
                            	rs = null;
							}
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();
                        }

                        //
                        // OK.. we are now using a new session id
                        int tmp = iSessionID;
                        iSessionID = iNextSessionID;
                        iNextSessionID = tmp;

                        // if any e2's were found.. there is at least on potential relator out there
                        // and the  e2's are sitting there in the queue table.
                        //
                        if (rdrs1.size() > 0) {
                            int iStep2 = loadSearchTables(_db,iSessionID, eg2, ei2);
                            // Is there an auto filter set
                            // the m_vctAutoFlagsFilter is greater than zero
                            // We need to insrt them here into the DynaSearch Table
                            // These should be last!! NOTE istep1 was used not istep2 in orig code
                            iStep2 += loadSearchAutoFilters(_db, iSessionID, eg2.getEntityType(), iStep1);


                            int iStep3=0;
                            if(_ai.isUseRelatorAttr()){
                            	//RCQ216919
                            	EntityGroup eg = _ai.getEntityGroup();
                            	EntityItem ei = eg.getEntityItem(0);
                            	iStep3 = loadSearchTables(_db,iSessionID, eg, ei);
                            	// Is there an auto filter set
                            	// the m_vctAutoFlagsFilter is greater than zero
                            	// We need to insrt them here into the DynaSearch Table
                            	iStep3 += loadSearchAutoFilters(_db, iSessionID, eg.getEntityType(), iStep2);
                            }

                            // Lets have an augmented search
                            try{
                            	rs = null;
                            	if(iStep3>0){
                            		// RCQ216919 
                            		rs = _db.callGBL9202(returnStatus, iSessionID, strEnterprise, strActionItemKey,
                                			(_ai.isLikeMatchingEnabled() ? 1 : 0), strValOn, strEffOn);	
                            	}else{
                            		// use original augmented search even if isUseRelatorAttr because no attr were set
                            		rs = _db.callGBL9203(returnStatus, iSessionID, strEnterprise, strActionItemKey,
                            			(_ai.isLikeMatchingEnabled() ? 1 : 0), strValOn, strEffOn);
                            	}
                            	rdrs = new ReturnDataResultSet(rs);
                            }finally{
                            	if(rs!=null){
                            		rs.close();
                            		rs = null;
                            	}
                            	_db.commit();
                            	_db.freeStatement();
                            	_db.isPending();
                            }
                   	
                            for (int i = 0; i < rdrs.size(); i++) {

                                EntityGroup eGroupR = null;
                                EntityGroup eGroup2 = null;
                                EntityItem eItem1 = null;
                                EntityItem eItem2 = null;

                                String strEntity1Type = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 0));
                                int iEntity1ID = rdrs.getColumnInt(i, 1);
                                String strEntityType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 2));
                                int iEntityID = rdrs.getColumnInt(i, 3);
                                String strEntity2Type = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 4));
                                int iEntity2ID = rdrs.getColumnInt(i, 5);

                                EntityGroup eGroup1 = getEntityGroup(strEntity1Type);

                                _db.debug(D.EBUG_SPEW,
                                          "gbl9203:answer:" + strEntity1Type + ":" + iEntity1ID + ":" + strEntityType + ":" +
                                          iEntityID + ":" + strEntity2Type + ":" + iEntity2ID);
                                
                                eGroupR = getEntityGroup(strEntityType);
                                if (eGroupR == null) {
                                    _db.debug(D.EBUG_DETAIL, "EntityList.Search.Dyna.Making new EntityGroup:" + strEntityType);
                                    eGroupR = new EntityGroup(this, _db, getProfile(), strEntityType, _ai.getPurpose(),
                                        _ai.isUIAfterCacheEnabled(),memTbl);
                                    putEntityGroup(eGroupR);
                                    if (isPicklist() || isPath()) {
                                        eGroupR.setForceMode(true);
                                        eGroupR.setForceDisplay(eGroupR.getKey().equals(_ai.getTargetEntityGroup()));
                                    }
                                }

                                if (_ai.isFilterByEntity(strParentEntityType)) {
                                    if (strEntity1Type.equals(strFilterEntityType)) {
                                    	if (eiFilterList.get(strEntity1Type+iEntity1ID) == null) {
                                            _db.debug(D.EBUG_SPEW,
                                                "not in filter list, skipping the entity:" + strEntity1Type + ":" + iEntity1ID);
                                            cleanupFilteredGroup = eGroupR;  // remove all filtered out and restore valid
                                            continue;
                                        }
                                    }

                                    if (strEntity2Type.equals(strFilterEntityType)) {
                                    	if (eiFilterList.get(strEntity2Type+iEntity2ID) == null) {
                                            _db.debug(D.EBUG_SPEW,
                                                "not in filter list, skipping the entity:" + strEntity2Type + ":" + iEntity2ID);
                                            cleanupFilteredGroup = eGroupR;
                                            continue;
                                        }
                                    }

                                }

                                if (eGroup1 == null) {
                                    _db.debug(D.EBUG_DETAIL, "EntityList.Search.Dyna.Making new EntityGroup:" + strEntity1Type);
                                    eGroup1 = new EntityGroup(this, _db, getProfile(), strEntity1Type, _ai.getPurpose(),
                                        _ai.isUIAfterCacheEnabled(),memTbl);
                                    putEntityGroup(eGroup1);

                                    if (isPicklist() || isPath()) {
                                        eGroup1.setForceMode(true);
                                        eGroup1.setForceDisplay(eGroup1.getKey().equals(_ai.getTargetEntityGroup()));
                                    }
                                }

                                // Lets get the Entity Item in there!
                                eItem1 = eGroup1.getEntityItem(strEntity1Type, iEntity1ID);
                                if (!eGroup1.containsEntityItem(strEntity1Type, iEntity1ID)) {
                                    eItem1 = new EntityItem(eGroup1, null, strEntity1Type, iEntity1ID);
                                    eGroup1.putEntityItem(eItem1);
                                }

                                eGroup2 = getEntityGroup(strEntity2Type);
                                if (eGroup2 == null) {
                                    _db.debug(D.EBUG_DETAIL, "EntityList.Search.Dyna.Making new EntityGroup:" + strEntity2Type);
                                    eGroup2 = new EntityGroup(this, _db, getProfile(), strEntity2Type, _ai.getPurpose(),
                                        _ai.isUIAfterCacheEnabled(),memTbl);
                                    putEntityGroup(eGroup2);
                                    if (isPicklist() || isPath()) {
                                        eGroup2.setForceMode(true);
                                        eGroup2.setForceDisplay(eGroup2.getKey().equals(_ai.getTargetEntityGroup()));
                                    }

                                }

                                // Lets get the Entity Item in there!
                                eItem2 = eGroup2.getEntityItem(strEntity2Type, iEntity2ID);
                                if (!eGroup2.containsEntityItem(strEntity2Type, iEntity2ID)) {
                                    eItem2 = new EntityItem(eGroup2, null, strEntity2Type, iEntity2ID);
                                    eGroup2.putEntityItem(eItem2);
                                }

                                // Lets get the Entity Item in there!
                                if (!eGroupR.containsEntityItem(strEntityType, iEntityID)) {
                                    EntityItem eItemR = new EntityItem(eGroupR, null, strEntityType, iEntityID);
                                    eGroupR.putEntityItem(eItemR);
                                    eItemR.putUpLink(eItem1);
                                    eItemR.putDownLink(eItem2);
                                }
                            }
                        }
                    }
                }
                else {

                    int iStep = 0;
                    EntityGroup eg = _ai.getEntityGroup(_db);
                    EntityItem ei = eg.getEntityItem(0);
                    _db.test(ei != null, "There is no "+eg.getEntityType()+" Entity Item in your Search Object to use to find data!");

                    // Go through each value on the change stack .. and find the values and place them into the
                    // dyna search table.

                    // Here .. we insert the filters for the search
                    // These fields should not be searchable.. (selectable by the user)
                    // If domain search enabled.. we need to get the list of currently enabled
                    // PDH Domains. and insert them into here now.
                    // Only do this if workflow is Enabled... for UI purposes DWB  This is for speed
                    findSearchDomainControls(_db, eg, null, ei, null, memTbl);

                    // Now.. lets fill out the search table
                    iStep = loadSearchTables(_db, iSessionID, eg, ei);

                    // Is there an auto filter set
                    // the m_vctAutoFlagsFilter is greater than zero
                    // We need to insert them here into the DynaSearch Table
                    // These should be last!!
                    iStep += loadSearchAutoFilters(_db, iSessionID, eg.getEntityType(), iStep);

                    // Now they are all in.. lets do the Dyna Search
                    // We need to see now if we are searching from the queue..
                    if (_ai.isQueueSearch()) {
                        try {
                            rs = _db.callGBL8127(returnStatus, iSessionID, strEnterprise, _ai.getActionItemKey(),
                                                 (_ai.isLikeMatchingEnabled() ? 1 : 0), _ai.m_strQueueSearch);
                            rdrs = new ReturnDataResultSet(rs);
                        }
                        finally {
							if (rs != null){
                            	rs.close();
                            	rs = null;
							}
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();
                        }
                    }
                    else {

                        try {
                            rs = _db.callGBL9200(returnStatus, iSessionID, strEnterprise, _ai.getActionItemKey(),
                                                 (_ai.isLikeMatchingEnabled() ? 1 : 0), strValOn, strEffOn,
                                                 (_ai.checkLimit() ? Integer.parseInt(System.getProperty("SearchActionItem.limit", "100000")) :
                                                  200000));
                            rdrs = new ReturnDataResultSet(rs);
                        }
                        finally {
							if (rs!=null){
                            	rs.close();
                            	rs = null;
							}
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();
                        }
                    }

                    if (_ai.checkLimit()) {
                        if (rdrs.size() > SearchActionItem.SEARCHLIMIT) {

                            if (_ai.hasFilterByEntity() && (eiFilterList.size() < SearchActionItem.SEARCHLIMIT)) {
                                System.out.println("search filter by entity. filterlist size: " + eiFilterList.size() + " less than " +
                                    SearchActionItem.SEARCHLIMIT);
                            }
                            else {

                                // might want to clean up before throwing exception
                                // DWB Temp stop this for debugs
                            	cleanupSession(_db,iSessionID);

                                String msg =  " Number of records exceeds the limit of " + SearchActionItem.SEARCHLIMIT +
                                ". Please refine the search. (ok)";
                                D.isplay("Assertion failed: " + msg);
                                throw new SearchExceedsLimitException(msg);
                                //_db.test(rdrs.size() <= SearchActionItem.SEARCHLIMIT,
                                  //       " Number of records exceeds the limit of " + SearchActionItem.SEARCHLIMIT +
                                    //     ". Please refine the search. (ok)");
                            }
                        }
                    }

                    for (int i = 0; i < rdrs.size(); i++) {

                        EntityItem ei1 = null;

                        String strEntityType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 0));
                        int iEntityID = rdrs.getColumnInt(i, 1);

                        EntityGroup eg1 = getEntityGroup(strEntityType);

                        _db.debug(D.EBUG_SPEW, "gbl9200/gbl8127:answer:" + strEntityType + ":" + iEntityID + ":");

                        if (eg1 == null) {
                            _db.debug(D.EBUG_DETAIL, "EntityList.Search.Dyna.Making new EntityGroup:" + strEntityType);
                            eg1 = new EntityGroup(this, _db, getProfile(), strEntityType, _ai.getPurpose(),
                                                  _ai.isUIAfterCacheEnabled(),memTbl);
                            putEntityGroup(eg1);

                            // If this is augmented for path or picklist either a picklist or a path..
                            // we need to shut down isdisplayables on everything except the target EG
                            if (isPicklist() || isPath()) {

                                eg.setForceMode(true);
                                eg.setForceDisplay(eg.getKey().equals(_ai.getTargetEntityGroup()));
                            }
                        }

                        if (_ai.isFilterByEntity(strParentEntityType)) {
                            if (strEntityType.equals(strFilterEntityType)) {
                            	if (eiFilterList.get(strEntityType+iEntityID) == null) { 
                                    _db.debug(D.EBUG_SPEW, "not in filter list, skipping the entity:" + strEntityType + ":" + iEntityID);
                                    cleanupFilteredGroup = eg1;  // remove all filtered out and restore valid
                                    continue;
                                }
                            }
                        }

                        // Lets get the Entity Item in there!
                        if (!eg1.containsEntityItem(strEntityType, iEntityID)) {
                            ei1 = new EntityItem(eg1, null, strEntityType, iEntityID);
                            eg1.putEntityItem(ei1);
                        }
                    }
                }
            }
            else {

                int iStep = 1;

                String strSearchString = _ai.getSearchString();

                _db.debug(D.EBUG_DETAIL, "EntityList w/ SearchActionItem.Search:" + getKey());

                //
                // There can be multiple target Entity Types's so lets get them here
                //
                if (_ai.useSearchRequest()) {
                    SearchRequest sr = _ai.getSearchRequest();

                    for (int i = 0; i < sr.getSearchTargetCount(); i++) {
                        SearchTarget st = sr.getSearchTarget(i);
                        if (st.isSelected()) {
                            String strEntityType = st.getEntityType();
                            String strAttributeCode = st.getAttributeCode();
                            EntityGroup eg = getEntityGroup(strEntityType);

                            _db.callGBL8119(returnStatus, iSessionID, iStep, strEnterprise, strEntityType, strAttributeCode,
                                            strSearchString);
                            _db.commit();
                            _db.freeStatement();
                            _db.isPending();

                            if (eg == null) {
                                eg = new EntityGroup(this, _db, _prof, strEntityType, _ai.getPurpose(), 
                                		_ai.isUIAfterCacheEnabled(),memTbl);
                                putEntityGroup(eg);
                                // If this is augmented for path or picklist either a picklist or a path..
                                // we need to shut down isdisplayables on everything except the target EG
                                if (isPicklist() || isPath()) {
                                    eg.setForceMode(true);
                                    eg.setForceDisplay(eg.getKey().equals(_ai.getTargetEntityGroup()));
                                }
                            }
                        }
                    }

                }
                else {
                    SearchRequest sr = _ai.getSearchRequest();

                    for (int i = 0; i < sr.getSearchTargetCount(); i++) {
                        SearchTarget st = sr.getSearchTarget(i);
                        String strEntityType = st.getEntityType();
                        String strAttributeCode = st.getAttributeCode();
                        EntityGroup eg = getEntityGroup(strEntityType);

                        _db.callGBL8119(returnStatus, iSessionID, iStep, strEnterprise, strEntityType, strAttributeCode,
                                        strSearchString);
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();

                        if (eg == null) {
                            eg = new EntityGroup(this, _db, _prof, strEntityType, _ai.getPurpose(), 
                            		_ai.isUIAfterCacheEnabled(),memTbl);
                            putEntityGroup(eg);
                            // If this is augmented for path or picklist either a picklist or a path..
                            // we need to shut down isdisplayables on everything except the target EG
                            if (isPicklist() || isPath()) {
                                eg.setForceMode(true);
                                eg.setForceDisplay(eg.getKey().equals(_ai.getTargetEntityGroup()));
                            }
                        }
                    }
                }

                // Now we go after the data
                // Lets now execute the basic Navigation transaction...

                _db.debug(D.EBUG_DETAIL,
                          "gbl8001:params:" + iSessionID + ":" + strEnterprise + ":" + strActionItemKey + ":" + strSearchString +
                          ":" + iWGID + ":" + strValOn + ":" + strEffOn);
                try{
                	rs = _db.callGBL8001(returnStatus, iSessionID, strEnterprise, strActionItemKey, strSearchString, iWGID, strValOn,
                			strEffOn);
                	rdrs = new ReturnDataResultSet(rs);
                }finally{
                	if(rs!=null){
                		rs.close();
                		rs = null;
                	}
                	_db.commit();
                	_db.freeStatement();
                	_db.isPending();
                }
                if (_ai.checkLimit()) {
                    if (rdrs.size() > SearchActionItem.SEARCHLIMIT) {
                        // might want to clean up before throwing exception
                        // DWB Testing here
                    	cleanupSession(_db,iSessionID);

                        String msg =  " Number of records exceeds the limit of " + SearchActionItem.SEARCHLIMIT +
                        ". Please refine the search. (ok)";
                        D.isplay("Assertion failed: " + msg);
                        throw new SearchExceedsLimitException(msg);
                        //_db.test(rdrs.size() <= SearchActionItem.SEARCHLIMIT,
                          //       " Number of records exceeds the limit of " + SearchActionItem.SEARCHLIMIT +
                            //     ". Please refine the search. (ok)");
                    }
                }

                for (int i = 0; i < rdrs.size(); i++) {

                    EntityItem ei1 = null;

                    String strEntityType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(i, 0).trim());
                    int iEntityID = rdrs.getColumnInt(i, 1);
                    EntityGroup eg1 = getEntityGroup(strEntityType);

                    _db.debug(D.EBUG_SPEW, "gbl8001:answer:" + strEntityType + ":" + iEntityID + ":");

                    if (eg1 == null) {
                        continue;
                    }

                    // Groups are now good so lets go onto items
                    // Esnure we use the EGParent Group when possible

                    if (!eg1.containsEntityItem(strEntityType, iEntityID)) {
                        ei1 = new EntityItem(eg1, null, strEntityType, iEntityID);
                        eg1.putEntityItem(ei1);
                    }
                }

                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();

            }
            if(cleanupFilteredGroup != null){
            	// filtered search can leave many extra records in the trsnavigate table - clean it and
            	// put valid records back for the pop attributes
            	cleanupFilteredSearch(_db,_ai,iSessionID,cleanupFilteredGroup);
            }

            if (_ai.pullAttributes()) {
                popAllAttributeValues(_db, _prof, iSessionID, _ai);
            }
            else {

//catalog mods                popNavAttributeValues(_db, strEnterprise, iSessionID, iNLSID, strValOn, strEffOn);
                popNavAttributeValues(_db, _ai, strEnterprise, iSessionID, iNLSID, strValOn, strEffOn); //catalog mods
            }
        
            if (getParentActionItem().isMetaColumnOrderControl()) {
                buildActionItemColumnOrders(_db, getParentActionItem());
            }
        }
        finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
           	memTbl.clear();
           	
            // Now remove all the records to clean up after yourself
            // DWB temp
            D.ebug(D.EBUG_SPEW, "Search cleanup session id's: " + iSessionID + ", " + iNextSessionID);
			int nTries = 3;
			int status = -1;
			do {
				status = cleanupSession(_db,iSessionID);
				if (status != 0) {
					D.ebug(D.EBUG_DETAIL, "GBL8105 did not return SP_OK");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						D.ebug(D.EBUG_DETAIL, e.getMessage());
					}
				}
			} while (status != 0 && nTries-- > 0);
			nTries = 3;
			status = -1;
			do {
				status = cleanupSession(_db,iNextSessionID);
				if (status != 0) {
					D.ebug(D.EBUG_DETAIL, "GBL8105 did not return SP_OK");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						D.ebug(D.EBUG_DETAIL, e.getMessage());
					}
				}
			} while (status != 0 && nTries-- > 0);
        }
    }

	/**
	 * clear the trsnavigate table for this session id and restore valid entries for filtered search
	 * results
	 * @param _db
	 * @param _ai
	 * @param iSessionID
	 * @param eg
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
    private void cleanupFilteredSearch(Database _db,SearchActionItem _ai, 
    		int iSessionID, EntityGroup eg) throws MiddlewareException, SQLException {
    	// remove all records
    	D.ebug(D.EBUG_SPEW, "Search cleanup and restore filtered session id: " + iSessionID);
    	int nTries = 3;
    	String strEnterprise = getProfile().getEnterprise();
    	String strValOn = getProfile().getValOn();
    	String strEffOn = getProfile().getEffOn();
    	int status = -1;
    	do {
    		status = cleanupSession(_db,iSessionID);
    		if (status!= 0) {
    			D.ebug(D.EBUG_DETAIL, "GBL8105 did not return SP_OK");
    			try {
    				Thread.sleep(1000);
    			} catch (InterruptedException e) {
    				D.ebug(D.EBUG_DETAIL, e.getMessage());
    			}
    		}
    	} while (status != 0 && nTries-- > 0);

    	ReturnStatus returnStatus = new ReturnStatus(-1);
    	// put items for this search back
    	for (int ii = 0; ii < eg.getEntityItemCount(); ii++) {
    		String strEntityType;
    		int iEntityID;
    		String strEntity1Type;
    		int iEntity1ID;
    		String strEntity2Type;
    		int iEntity2ID;

    		if(_ai.isGrabRelator()){
    			EntityItem eir = eg.getEntityItem(ii);
    			EntityItem eip = (EntityItem) eir.getUpLink(0);
    			EntityItem eic = (EntityItem) eir.getDownLink(0);

    			strEntityType = eir.getEntityType();
    			iEntityID = eir.getEntityID();
    			strEntity1Type = (eip != null ? eip.getEntityType() : eir.getEntityType());
    			iEntity1ID = (eip != null ? eip.getEntityID() : eir.getEntityID());
    			strEntity2Type = (eic != null ? eic.getEntityType() : eir.getEntityType());
    			iEntity2ID = (eic != null ? eic.getEntityID() : eir.getEntityID());
    			_db.debug(D.EBUG_DETAIL,
    					"gbl1017:params:" + iSessionID + ":" + strEnterprise + ":" + strEntity1Type + ":" + iEntity1ID + ":" +
    							strEntityType + ":" + iEntityID + ":" + strEntity2Type + ":" + iEntity2ID + ":" + strValOn + ":" +
    							strEffOn + ":");
    			try{
    				_db.callGBL1017(returnStatus, iSessionID, strEnterprise, strEntity1Type, iEntity1ID, strEntityType, iEntityID,
    						strEntity2Type, iEntity2ID, strValOn, strEffOn);
    			}finally{
    				_db.freeStatement();
    				_db.isPending();
    			}
    		}else{
    			EntityItem ei = eg.getEntityItem(ii);
    			strEntity2Type = ei.getEntityType();
    			iEntity2ID = ei.getEntityID();

    			_db.debug(D.EBUG_DETAIL,
    					"gbl8115:params:" + iSessionID + ":" + strEnterprise + ":" + _ai.getActionItemKey() + ":" +
    							strEntity2Type + ":" + iEntity2ID + ":" + strValOn + ":" + strEffOn + ":");
    			try{
    				_db.callGBL8115(returnStatus, iSessionID, strEnterprise, _ai.getActionItemKey(), strEntity2Type, iEntity2ID,
    						strValOn, strEffOn);
    			}finally{
    				_db.freeStatement();
    				_db.isPending();
    			} 
    		}
    	}
    }
    
    /**
     * remove session id from trsnavigate table
     * @param db
     * @param iSessionID
     * @return
     * @throws MiddlewareException
     * @throws SQLException
     */
    private int cleanupSession(Database db,int iSessionID) throws MiddlewareException, SQLException{
    	ReturnStatus returnStatus = new ReturnStatus(-1);
		try{
			db.callGBL8105(returnStatus, iSessionID);
		}finally{
			db.commit();
			db.freeStatement();
			db.isPending();
		}
		return returnStatus.intValue();
    }
	
    /**
     * if this search action is domain controlled and workflow enabled, find the domain attributes and set those
     * values in the entity items for later use in search.
     * @param _db
     * @param eg1
     * @param eg2 may be null
     * @param ei1
     * @param ei2 may be null
     * @param memTbl
     * @throws MiddlewareException 
     * @throws SQLException 
     */
    private void findSearchDomainControls(Database _db, EntityGroup eg1, 
    		EntityGroup eg2, EntityItem ei1,
    		EntityItem ei2, Hashtable memTbl) throws MiddlewareException, SQLException{
    	// If domain search enabled.. we need to get the list of currently enabled
    	// PDH Domains. and insert them into here now.
    	// Only do this if workflow is Enabled... for UI purposes DWB  This is for speed
    	SearchActionItem sai = (SearchActionItem)this.getParentActionItem();

    	if (sai.isDomainControlled() && sai.isWorkflowEnabled()) {
    		ResultSet rs = null;
    		ReturnDataResultSet rdrs = null;
    		ReturnStatus returnStatus = new ReturnStatus( -1);

    		String strEnterprise = getProfile().getEnterprise();
    		String strValOn = getProfile().getValOn();
    		String strEffOn = getProfile().getEffOn();
    		int iWGID = getProfile().getWGID();
    		EANList eltmp1 = new EANList(EANMetaEntity.LIST_SIZE);
    		EANList eltmp2 = new EANList(EANMetaEntity.LIST_SIZE);

    		_db.debug(D.EBUG_DETAIL, "EntityList.DynaSearch.Domain TRACE Controlled Enabled");
    		// Lets add all the PDH Domain Info here...
    		try{
    			rs = _db.callGBL7065(returnStatus, strEnterprise, iWGID, strValOn, strEffOn);
    			rdrs = new ReturnDataResultSet(rs);
    		}finally{
    			if(rs !=null){
    				rs.close();
    				rs = null;
    			}
    			_db.commit();
    			_db.freeStatement();
    			_db.isPending();
    		}

    		for (int ii = 0; ii < rdrs.size(); ii++) {
    			String strAttributeCode = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 0));
    			String strAttributeValue = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 1));
    			String strAttributeType = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 2));

    			_db.debug(D.EBUG_SPEW,
    					"EntityList.search.gbl7065:answers:" + strAttributeCode + ":" + strAttributeValue + ":" + strAttributeType);

    			//RQ0713072645
    			if (strAttributeCode.equals(sai.getWGDomainAttrCode())&&
    					!strAttributeCode.equals(sai.getDomainAttrCode())) {
    				// use values from WG's attrcode, but use domainattrcode in entity search
    				strAttributeCode = sai.getDomainAttrCode();
    				_db.debug(D.EBUG_SPEW,
    						"gbl7065:answers override:" + sai.getWGDomainAttrCode()+" to "+ strAttributeCode);
    			} // end RQ0713072645

    			EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) eg1.getMetaAttribute(strAttributeCode);
    			EANMetaFlagAttribute mfa2 = null;
    			if(eg2 !=null){
    				mfa2 = (EANMetaFlagAttribute) eg2.getMetaAttribute(strAttributeCode);
    			}

    			eltmp2.put(new Implicator(null, getProfile(), strAttributeCode + strAttributeValue));

    			// if meta flag attribute not in entity group yet, need to tuck it in.
    			if (mfa == null && strAttributeCode.equals(sai.getDomainAttrCode())) {
    				switch (strAttributeType.charAt(0)) {
    				case 'F':
    					mfa = new MetaMultiFlagAttribute(this, _db, getProfile(), strAttributeCode, true,memTbl);
    					eg1.putMetaAttribute(mfa);
    					if(eg2!=null && mfa2==null){
    						mfa2 = new MetaMultiFlagAttribute(this, _db, getProfile(), strAttributeCode, true,memTbl);
    						eg2.putMetaAttribute(mfa2);
    					}
    					break;
    				case 'U':
    					mfa = new MetaSingleFlagAttribute(this, _db, getProfile(), strAttributeCode, true,memTbl);
    					eg1.putMetaAttribute(mfa);
    					if(eg2!=null && mfa2==null){
    						mfa2 = new MetaSingleFlagAttribute(this, _db, getProfile(), strAttributeCode, true,memTbl);
    						eg2.putMetaAttribute(mfa2);
    					}
    					break;
    				case 'S':
    					mfa = new MetaStatusAttribute(this, _db, getProfile(), strAttributeCode, true,memTbl);
    					if(eg2!=null && mfa2==null){
    						mfa2 = new MetaStatusAttribute(this, _db, getProfile(), strAttributeCode, true,memTbl);
    						eg2.putMetaAttribute(mfa2);
    					}
    					break;
    				case 'A':
    					mfa = new MetaTaskAttribute(this, _db, getProfile(), strAttributeCode, true,memTbl);
    					eg1.putMetaAttribute(mfa);
    					if(eg2!=null && mfa2==null){
    						mfa2 = new MetaTaskAttribute(this, _db, getProfile(), strAttributeCode, true,memTbl);
    						eg2.putMetaAttribute(mfa2);
    					}
    					break;
    				default:
    					break;
    				}
    			}
    			if (mfa != null && (!eltmp1.containsKey(strAttributeCode)) &&
    					strAttributeCode.equals(sai.getDomainAttrCode())) {
    				eltmp1.put(new Implicator(mfa, null, strAttributeCode));
    				mfa.setDomainControlled(true);
    				if(mfa2!=null){
    					mfa2.setDomainControlled(true);
    				}
    			}
    			// Make sure user didnt deactivate this attribute
    			if (strAttributeCode.equals(sai.getDomainAttrCode())){
    				EANFlagAttribute att = (EANFlagAttribute)ei1.getAttribute(strAttributeCode);
    				if (att!=null && !att.isActive()){
    					att.setActive(true);
    				}
    				if(ei2!=null){
    					att = (EANFlagAttribute)ei2.getAttribute(strAttributeCode);
    					if (att!=null && !att.isActive()){
    						att.setActive(true);
    					}
    				}
    			}
    		}


    		//RQ0713072645
    		// make sure user didnt deselect one of the domains
    		Vector prevDefinedVct = new Vector();
    		Vector prevDefinedVct2 = new Vector();

    		for (int ii = 0; ii < eltmp1.size(); ii++) {
    			Implicator imp = (Implicator) eltmp1.getAt(ii);
    			EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) imp.getParent();
    			EANFlagAttribute fa = (EANFlagAttribute) ei1.getEANObject(eg1.getEntityType() + ":" +
    					mfa.getAttributeCode());
    			if(fa !=null){
    				// Get all the Flag values.
    				MetaFlag[] mfArray = (MetaFlag[]) fa.get();
    				for (int i = 0; i < mfArray.length; i++){
    					// get selection
    					if (mfArray[i].isSelected()){
    						prevDefinedVct.addElement(fa);
    						_db.debug(D.EBUG_SPEW,
    								"EntityList.searchaction has predefined "+mfa.getAttributeCode()+" "+mfArray[i]+
    								" for "+ei1.getKey());
    						break;
    					}
    				}
    			}
    			if(ei2!=null){
    				fa = (EANFlagAttribute) ei2.getEANObject(eg2.getEntityType() + ":" +mfa.getAttributeCode());
    				if(fa !=null){
    					// Get all the Flag values.
    					MetaFlag[] mfArray = (MetaFlag[]) fa.get();
    					for (int i = 0; i < mfArray.length; i++){
    						// get selection
    						if (mfArray[i].isSelected()){
    							prevDefinedVct2.addElement(fa);
    							_db.debug(D.EBUG_SPEW,
    									"EntityList.searchaction has predefined "+mfa.getAttributeCode()+" "+mfArray[i]+
    									" for "+ei2.getKey());
    							break;
    						}
    					}
    				}
    			}
    		}// end RQ0713072645

    		// Now we have everything that is needs to be worked on in the EANList
    		// So lets set all the flags because they have to be searched.
    		for (int ii = 0; ii < eltmp1.size(); ii++) {
    			Implicator imp = (Implicator) eltmp1.getAt(ii);
    			EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) imp.getParent();
    			EANFlagAttribute fa = (EANFlagAttribute) ei1.getEANObject(eg1.getEntityType() + ":" + mfa.getAttributeCode());
    			//moved this mfa.setDomainControlled(true);

    			if (prevDefinedVct.contains(fa)){ // RQ0713072645
    				_db.debug(D.EBUG_SPEW,
    						"EntityList.searchaction skipping predefined "+fa.getAttributeCode()+" for "+ei1.getKey());
    				continue;
    			}

    			// Protect Against a null pointer
    			if (fa != null) {
    				for (int iy = 0; iy < mfa.getMetaFlagCount(); iy++) {
    					MetaFlag mf = mfa.getMetaFlag(iy);
    					if (eltmp2.containsKey(mf.getKey())) {
    						fa.put(mf.getFlagCode(), true);
    					}
    				}
    			}
    			else {
    				_db.debug(" *** WARNING ***, domain enabled search " + ei1.getKey()+ " does not contain " +
    						mfa.getAttributeCode());
    			}
    		}
    		prevDefinedVct.clear();

    		// look at the second group and item
    		if(eg2!=null){
    			for (int ii = 0; ii < eltmp1.size(); ii++) {
    				Implicator imp = (Implicator) eltmp1.getAt(ii);
    				EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) imp.getParent();
    				EANFlagAttribute fa = (EANFlagAttribute) ei2.getEANObject(eg2.getEntityType() + ":" + mfa.getAttributeCode());
    				//moved this because each group has its own mfa mfa.setDomainControlled(true);

    				if (prevDefinedVct2.contains(fa)){ // RQ0713072645
    					_db.debug(D.EBUG_SPEW,
    							"EntityList.searchaction skipping predefined "+fa.getAttributeCode()+" for "+ei2.getKey());
    					continue;
    				}

    				// Protect Against a null pointer
    				if (fa != null) {
    					for (int iy = 0; iy < mfa.getMetaFlagCount(); iy++) {
    						MetaFlag mf = mfa.getMetaFlag(iy);
    						if (eltmp2.containsKey(mf.getKey())) {
    							fa.put(mf.getFlagCode(), true);
    						}
    					}
    				}
    				else {
    					_db.debug(" *** WARNING ***, domain enabled search ei2 " + ei2.getKey() + " does not contain " +
    							mfa.getAttributeCode());
    				}
    			}
    			prevDefinedVct2.clear();
    		}

    		eltmp1.clear();
    		eltmp2.clear();
    	}
    }
    
    /**
     * load the tables for search
     * @param _db
     * @param iSessionID
     * @param eg1
     * @param ei1
     * @return step count
     * @throws MiddlewareException
     * @throws SQLException
     */
    private int loadSearchTables(Database _db, int iSessionID, EntityGroup eg1,
    		EntityItem ei1) throws MiddlewareException, SQLException{

    	int iStep1=0;
    	ReturnStatus returnStatus = new ReturnStatus( -1);

    	String strEnterprise = getProfile().getEnterprise();
		String strEntityType = eg1.getEntityType();

    	// Now.. lets fill out the search table
    	// Do the Text first.. because they should yield quicker results than flags
    	for (int ii = 0; ii < eg1.getMetaAttributeCount(); ii++) {
    		EANMetaAttribute ma = eg1.getMetaAttribute(ii);
    		EANAttribute att = ei1.getAttribute(ma.getAttributeCode());
    		if ( (ma.isSearchable() || ma.isDomainControlled()) && att != null && !att.toString().equals("") &&
    				att.isActive() && att instanceof TextAttribute) {
    			iStep1++;
    			try{
    				_db.callGBL8119(returnStatus, iSessionID, iStep1, strEnterprise, strEntityType, ma.getAttributeCode(),
    						getSearchText(att.toString()));
    			}finally{
    				_db.commit();
    				_db.freeStatement();
    				_db.isPending();
    			}
    		}
    	}

    	// Do non Text second
    	for (int ii = 0; ii < eg1.getMetaAttributeCount(); ii++) {
    		EANMetaAttribute ma = eg1.getMetaAttribute(ii);
    		EANAttribute att = ei1.getAttribute(ma.getAttributeCode());
    		if ( (ma.isSearchable() || ma.isDomainControlled()) && att != null && !att.toString().equals("") &&
    				att.isActive() && att instanceof EANFlagAttribute) {
    			EANFlagAttribute fa = (EANFlagAttribute) att;
    			MetaFlag[] amf = (MetaFlag[]) fa.get();
    			iStep1++;
    			for (int ij = 0; ij < amf.length; ij++) {
    				if (amf[ij].isSelected()) {
    					try{
    						_db.callGBL8119(returnStatus, iSessionID, iStep1, strEnterprise, strEntityType,
    								ma.getAttributeCode(), amf[ij].getFlagCode());
    					}finally{
    						_db.commit();
    						_db.freeStatement();
    						_db.isPending();
    					}
    				}
    			}
    		}
    	}

    	return iStep1;
    }
   
    
    /**
     * 
     * @param _db
     * @param iSessionID
     * @param strEntityType
     * @param iStep1
     * @return
     * @throws MiddlewareException
     * @throws SQLException
     */
    private int loadSearchAutoFilters(Database _db,int iSessionID,
    		String strEntityType, int iStep1) throws MiddlewareException, SQLException{
    	ReturnStatus returnStatus = new ReturnStatus( -1);

    	SearchActionItem sai = (SearchActionItem)this.getParentActionItem();
    	String strEnterprise = getProfile().getEnterprise();
    	Vector vct1 = new Vector();
    	
    	// Is there an auto filter set
    	// the m_vctAutoFlagsFilter is greater than zero
    	// We need to insert them here into the DynaSearch Table
    	// These should be last!!
    	if(sai.m_vctAutoFlagFilter!=null){
    		for (int y = 0; y < sai.m_vctAutoFlagFilter.size(); y++) {
    			String strTokens = (String) sai.m_vctAutoFlagFilter.elementAt(y);
    			StringTokenizer st = new StringTokenizer(strTokens, ":");
    			String strAttributeCode = st.nextToken();
    			st.nextToken();
    			if (!vct1.contains(strEntityType + ":" + strAttributeCode)) {
    				vct1.addElement(strEntityType + ":" + strAttributeCode);
    			}
    		}
    		for (int y = 0; y < sai.m_vctAutoFlagFilter.size(); y++) {
    			String strTokens = (String) sai.m_vctAutoFlagFilter.elementAt(y);
    			StringTokenizer st = new StringTokenizer(strTokens, ":");
    			String strAttributeCode = st.nextToken();
    			String strAttributeValue = st.nextToken();
    			int iTmpStep = iStep1 + vct1.indexOf(strEntityType + ":" + strAttributeCode) + 1;
    			try{
    				_db.callGBL8119(returnStatus, iSessionID, iTmpStep, strEnterprise, strEntityType, strAttributeCode,
    						strAttributeValue);
    			}finally{
    				_db.commit();
    				_db.freeStatement();
    				_db.isPending();
    			}
    		}
    	}
    	iStep1 = vct1.size();

    	vct1.clear();
    	
    	return iStep1;
    }
    /**
     *  Make a serialized copy of myself
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public EntityList cloneStructure() {

        //Serialization approach...
        EntityList clone = null;
        byte[] byteArray = null;
        ByteArrayOutputStream BAout = null;
        ByteArrayInputStream BAin = null;
        ObjectOutputStream Oout = null;
        ObjectInputStream Oin = null;

        try {
            //put object into stream
            try {
                BAout = new ByteArrayOutputStream();
                Oout = new ObjectOutputStream(BAout);
                Oout.writeObject(this);
                Oout.flush();
                Oout.reset();
                byteArray = BAout.toByteArray();
            }
            finally {

                Oout.close();
                BAout.close();
            }

            //now turn around and pull object out of stream
            try {
                BAin = new ByteArrayInputStream(byteArray);
                Oin = new ObjectInputStream(BAin);
                clone = (EntityList) Oin.readObject();
            }
            finally {
                Oin.close();
                BAin.close();
            }
            byteArray = null;

        }
        catch (Exception exc) {
            exc.printStackTrace();
        }

        return clone;
    }

    /**
     * Return the date/time this class was generated
     *
     * @return    the date/time this class was generated
     */
    public String getVersion() {
        return "$Id: EntityList.java,v 1.762 2014/04/17 18:27:05 wendy Exp $";
    }

    /**
     * Returns true if the passed info is inside this PDHGroup
     *
     * @param  _o  Description of the Parameter
     * @return     Description of the Return Value
     */
    public boolean equals(Object _o) {

        EntityList el = null;
        NLSItem nlsi = null;
        NLSItem nlsi2 = null;

        // First check the EntityList
        if (_o == null) {
            return false;
        }

        if (! (_o instanceof EntityList)) {
            return false;
        }

        el = (EntityList) _o;

        if (!el.getKey().equals(getKey())) {
            return false;
        }

        if (!getProfile().equals(el.getProfile())) {
            return false;
        }

        nlsi = getProfile().getReadLanguage();
        nlsi2 = el.getProfile().getReadLanguage();

        if (!nlsi.equals(nlsi2)) {
            return false;
        }

        // O.K. check parents

        if (!getParentActionItem().equals(el.getParentActionItem())) {
            return false;
        }
        if (!getParentEntityGroup().equals(el.getParentEntityGroup())) {
            return false;
        }

        return true;
    }

    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public String toString() {

        StringBuffer strbResult = new StringBuffer();
        if (m_aiParent != null) {
            strbResult.append(m_aiParent.toString() + " from ");
        }
        strbResult.append(getParentToStrings());
        if (getNLSID() != 1) {
            strbResult.append(" - " + getNLSItem().toString());
        }

        return strbResult.toString();
    }

    /** RQ0927066214
     *  get bread crumbs for UI
     *@param maxlen int max length
     *@param delim  String delimiter for one parent
     *@param delim2  String delimiter between parents
     *@return    String
     */
    public String getBreadCrumbs(int maxlen, String delim, String delim2) {

        StringBuffer strbResult = new StringBuffer();
        if (m_aiParent != null) { // get action text
            strbResult.append(m_aiParent.toString() + " from ");
        }
        // get source info
        if (m_aiParent instanceof NavActionItem) {
            String bcStr = ( (NavActionItem) m_aiParent).getBreadCrumbs(this, delim, delim2);
            if (bcStr.length() == 0) { // 'BreadCrumbs' not defined for this action
                bcStr = getParentToStrings(maxlen);
            }
            else {
                bcStr = "(" + truncate(bcStr, maxlen) + ")";
            }
            strbResult.append(bcStr);
        }
        else {
            strbResult.append(getParentToStrings(maxlen));
        }
        // add nls info
        if (getNLSID() != 1) {
            strbResult.append(" - " + getNLSItem().toString());
        }

        return strbResult.toString();
    }

    //
    // MUTATORS
    //

    /*
     *  Places the EntityGroup into this structure and sets its parent to this object.
     */
    /**
     *  Description of the Method
     *
     * @param  _eg  Description of the Parameter
     */
    public void putEntityGroup(EntityGroup _eg) {
        putData(_eg);
        _eg.setParent(this);
    }

    /*
     *  Replaces the entire EntityGroup object and resets the
     *  Parents in the passed EANList to this object
     */
    /**
     *  Sets the entityGroup attribute of the EntityList object
     *
     * @param  _eanList  The new entityGroup value
     */
    protected void setEntityGroup(EANList _eanList) {
        setData(_eanList);
        // Now.. we must set up ownership heere
        for (int i = 0; i < getEntityGroupCount(); i++) {
            EntityGroup eg = getEntityGroup(i);
            eg.setParent(this);
        }
    }

    /*
     *  Removes an EntityGroup and Returns it for further manipulation
     */
    /**
     *  Description of the Method
     *
     * @param  _eg  Description of the Parameter
     * @return      Description of the Return Value
     */
    public EntityGroup removeEntityGroup(EntityGroup _eg) {
        return (EntityGroup) removeData(_eg);
    }

    /*
     *  Sets the active entityGroup
     */
    /**
     *  Sets the activeEntityGroup attribute of the EntityList object
     *
     * @param  _eg  The new activeEntityGroup value
     */
    public void setActiveEntityGroup(EntityGroup _eg) {
        if (_eg == getParentEntityGroup()) {
            m_iActiveEntityGroup = -2;
        }
        else {
            m_iActiveEntityGroup = getData().indexOf(_eg);
        }
    }

    /**
     *  Sets the activeEntityGroup attribute of the EntityList object
     *
     * @param  _i  The new activeEntityGroup value
     */
    public void setActiveEntityGroup(int _i) {
        m_iActiveEntityGroup = _i;
    }

    //
    // ACCESSORS
    //

    /**
     *  Gets the entryPoint attribute of the EntityList object
     *
     * @return    The entryPoint value
     */
    public boolean isEntryPoint() {
        return getParentActionItem().getActionItemKey().equals("EntryPoint");
    }

    /**
     *  Gets the cartable attribute of the EntityList object
     *
     * @return    The cartable value
     */
    public boolean isCartable() {
        return getParentActionItem().getActionItemKey().equals("Cart");
    }

    /*
     *  Returns the ActionList associated with this object
     */
    /**
     *  Gets the actionList attribute of the EntityList object
     *
     * @return    The actionList value
     */
    public ActionList getActionList() {
        return m_al;
    }

    /*
     *
     */
    /**
     *  Gets the actionGroupByEntityType attribute of the EntityList object
     *
     * @param  _strEntityType  Description of the Parameter
     * @return                 The actionGroupByEntityType value
     */
    public ActionGroup getActionGroupByEntityType(String _strEntityType) {
        if (m_al == null) {
            return null;
        }
        return m_al.getActionGroup(_strEntityType, ActionList.KEY_BY_ENTITYTYPE);
    }

    /**
     *  Gets the activeEntityGroup attribute of the EntityList object
     *
     * @return    The activeEntityGroup value
     */
    public EntityGroup getActiveEntityGroup() {
        if (m_iActiveEntityGroup == -2) {
            return getParentEntityGroup();
        }
        return (EntityGroup) getData(m_iActiveEntityGroup);
    }

    // Parent Related Accessors

    /*
     *  Returns the ActionItem used to create this Obect
     */
    /**
     *  Gets the parentActionItem attribute of the EntityList object
     *
     * @return    The parentActionItem value
     */
    public EANActionItem getParentActionItem() {
        return m_aiParent;
    }

    /*
     *  Returns the EntityGroup that holds the Parents
     *  that were used to create this object
     */
    /**
     *  Gets the parentEntityGroup attribute of the EntityList object
     *
     * @return    The parentEntityGroup value
     */
    public EntityGroup getParentEntityGroup() {
        return m_egParent;
    }

    // Entity Group
    /*
     *  returns the EntityGroup found at key _str
     */
    /**
     *  Gets the entityGroup attribute of the EntityList object
     *
     * @param  _str  Description of the Parameter
     * @return       The entityGroup value
     */
    public EntityGroup getEntityGroup(String _str) {
        return (EntityGroup) getData(_str);
    }

    /*
     *  Returns the EntityGroup at Indix _i
     */
    /**
     *  Gets the entityGroup attribute of the EntityList object
     *
     * @param  _i  Description of the Parameter
     * @return     The entityGroup value
     */
    public EntityGroup getEntityGroup(int _i) {
        return (EntityGroup) getData(_i);
    }

    /*
     *  Returns the EntityGroup at Indix _i
     */
    /**
     *  Gets the defaultEntityItem attribute of the EntityList object
     *
     * @param  _i  Description of the Parameter
     * @return     The defaultEntityItem value
     */
    protected EntityItem getDefaultEntityItem(int _i) {
        return (EntityItem) m_elDefaultEntityItems.getAt(_i);
    }

    /*
     *  Returns the EntityGroup at Index _i
     */
    /**
     *  Gets the defaultEntityItem attribute of the EntityList object
     *
     * @param  _str  Description of the Parameter
     * @param  _i    Description of the Parameter
     * @return       The defaultEntityItem value
     */
    protected EntityItem getDefaultEntityItem(String _str, int _i) {
        return (EntityItem) m_elDefaultEntityItems.get(_str + _i);
    }

    /*
     *  Returns the EntityGroup at Indix _i
     */
    /**
     *  Gets the defaultEntityItemCount attribute of the EntityList object
     *
     * @return    The defaultEntityItemCount value
     */
    protected int getDefaultEntityItemCount() {
        return m_elDefaultEntityItems.size();
    }

    /*
     *  Returns the EntityGroup at Indix _i
     */
    /**
     *  Description of the Method
     *
     * @param  _ei  Description of the Parameter
     */
    protected void putDefaultEntityItem(EntityItem _ei) {
        m_elDefaultEntityItems.put(_ei);
    }

    /*
     *  Returns the EntityGroup at Indix _i
     */
    /**
     *  Description of the Method
     *
     * @param  _str  Description of the Parameter
     * @param  _i    Description of the Parameter
     */
    protected void removeDefaultEntityItem(String _str, int _i) {
        m_elDefaultEntityItems.remove(_str + _i);
    }

    /*
     *  Returns an EANList of the EntityGroups Managed by this Object
     */
    /**
     *  Gets the entityGroup attribute of the EntityList object
     *
     * @return    The entityGroup value
     */
    protected EANList getEntityGroup() {
        return getData();
    }

    /*
     *  Returns the number of EntityGroups managed by this object
     */
    /**
     *  Gets the entityGroupCount attribute of the EntityList object
     *
     * @return    The entityGroupCount value
     */
    public int getEntityGroupCount() {
        return getDataCount();
    }

    /**
     * isPicklist
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isPicklist() {
        if (isNavigate() || isSearch()) {
            if (getParentActionItem() instanceof NavActionItem) {
                return ( (NavActionItem) getParentActionItem()).isPicklist();
            }
            else {
                return ( (SearchActionItem) getParentActionItem()).isPicklist();
            }
        }
        return false;
    }

    /**
     *  Gets the search attribute of the EntityList object
     *
     * @return    The search value
     */
    public boolean isSearch() {
        return (getParentActionItem() instanceof SearchActionItem);
    }

    /**
     *  Gets the navigate attribute of the EntityList object
     *
     * @return    The navigate value
     */
    public boolean isNavigate() {
        return (getParentActionItem() instanceof NavActionItem);
    }

    /**
     * isABRStatus
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isABRStatus() {
        return (getParentActionItem() instanceof ABRStatusActionItem);
    }

    /**
     *  Gets the path attribute of the EntityList object
     *
     * @return    The path value
     */
    public boolean isPath() {
        if (isNavigate()) {
            return ( (NavActionItem) getParentActionItem()).isPath();
        }
        return false;
    }

    /*
     *  No need to make this public
     */

    // PRIVATE METHODS

    /*
     *  This sets the action list for this Entity List
     */
    /**
     *  Sets the actionList attribute of the EntityList object
     *
     * @param  _al  The new actionList value
     */
    protected void setActionList(ActionList _al) {
        m_al = _al;
    }

    /*
     *  This takes this array of EntityItems and
     *  places them into the parent EntityGroup
     */
    /**
     *  Sets the parentEntityItems attribute of the EntityList object
     *
     * @param  _aei  The new parentEntityItems value
     */
    private void setParentEntityItems(EntityItem[] _aei) {
        EntityGroup eg = getParentEntityGroup();
        if (_aei == null) {
            D.ebug(D.EBUG_SPEW, "** EntityList.setParentEntityItems.1. _aei is null");
            return;
        }
        if (eg == null) {
            D.ebug(D.EBUG_SPEW, "** EntityList.setParentEntityItems.1. parent Entity Group is null");
            return;
        }

        for (int ii = 0; ii < _aei.length; ii++) {
            try {
                EntityItem ei = null;
                // create an entity that can hold all the different entities in the row for VEEdit
				if (getParentActionItem() instanceof ExtractActionItem &&
					(((ExtractActionItem)getParentActionItem()).isVEEdit())) {
					ei = new VEEditItem(eg, null, _aei[ii]);
				}else{
					ei = new EntityItem(eg, null, _aei[ii]);
				}
                eg.putEntityItem(ei);
                ei.reassign(eg);
            }
            catch (Exception x) {
                x.printStackTrace();
            }
        }
    }

    /*
     *  This takes this array of EntityItems and
     *  places them into the parent EntityGroup
     */
    /**
     *  Sets the parentEntityItems attribute of the EntityList object
     *
     * @param  _el  The new parentEntityItems value
     */
    private void setParentEntityItems(EANList _el) {
        EntityGroup eg = getParentEntityGroup();

        if (_el == null) {
            D.ebug(D.EBUG_SPEW, "** EntityList.setParentEntityItems.2. _el is null");
            return;
        }

        if (eg == null) {
            D.ebug(D.EBUG_SPEW, "** EntityList.setParentEntityItems.1. parent Entity Group is null");
            return;
        }

        for (int ii = 0; ii < _el.size(); ii++) {
            try {
                EntityItem ei = new EntityItem(eg, null, (EntityItem) _el.getAt(ii));
                eg.putEntityItem(ei);
                ei.reassign(eg);
            }
            catch (Exception x) {
                // Do nothing
            }
        }
    }

    /*
     *  set Up the Parent EntityGroup
     *  for this object
     */
    /**
     *  Sets the parentEntityGroup attribute of the EntityList object
     *
     * @param  _eg  The new parentEntityGroup value
     */
    private void setParentEntityGroup(EntityGroup _eg) {
        m_egParent = _eg;
    }

    /*
     *  Set the Parent Action Item of this object.
     */
    /**
     *  Sets the parentActionItem attribute of the EntityList object
     *
     * @param  _ai                             The new parentActionItem value
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public void setParentActionItem(EANActionItem _ai) throws MiddlewareRequestException {
        if (_ai == null) {
            m_aiParent = null;
        }
        else if (_ai instanceof NavActionItem) {
            m_aiParent = new NavActionItem(this, (NavActionItem) _ai);
        }
        else if (_ai instanceof CreateActionItem) {
            m_aiParent = new CreateActionItem(this, (CreateActionItem) _ai);
        }
        else if (_ai instanceof SearchActionItem) {
            m_aiParent = new SearchActionItem(this, (SearchActionItem) _ai);
        }
        else if (_ai instanceof EditActionItem) {
            m_aiParent = new EditActionItem(this, (EditActionItem) _ai);
        }
        else if (_ai instanceof ExtractActionItem) {
            m_aiParent = new ExtractActionItem(this, (ExtractActionItem) _ai);
        }
        else if (_ai instanceof CopyActionItem) {
            m_aiParent = new CopyActionItem(this, (CopyActionItem) _ai);
        }
        else if (_ai instanceof ABRStatusActionItem) {
            m_aiParent = new ABRStatusActionItem(this, (ABRStatusActionItem) _ai);
        }
    }

    /**
     *  Used to isolate the pulling of attribute values for the information in the trsNavigate Table
     *  Description of the Method
     *
     * @param  _db                      Description of the Parameter
     * @param  _strEnterprise           Description of the Parameter
     * @param  _iSessionID              Description of the Parameter
     * @param  _iNLSID                  Description of the Parameter
     * @param  _strValOn                Description of the Parameter
     * @param  _strEffOn                Description of the Parameter
     * @exception  SQLException         Description of the Exception
     * @exception  MiddlewareException  Description of the Exception
     */

//catalog mods    private void popNavAttributeValues(Database _db, String _strEnterprise, int _iSessionID, int _iNLSID, String _strValOn, String _strEffOn) throws SQLException, MiddlewareException
    private void popNavAttributeValues(Database _db, EANActionItem _nai, String _strEnterprise, int _iSessionID, int _iNLSID,
                                       String _strValOn, String _strEffOn) throws SQLException, MiddlewareException { //catalog mods

        ReturnStatus returnStatus = new ReturnStatus();
        ResultSet rs = null;
        TextAttribute ta = null;
        SingleFlagAttribute sfa = null;
        StatusAttribute sa = null;
        TaskAttribute tka = null;
        MultiFlagAttribute mfa = null;

        try {
            rs = _db.callGBL7006(returnStatus, _strEnterprise, _iSessionID, _iNLSID, _strValOn, _strEffOn);
            while (rs != null && rs.next()) { //catalog mods

                EntityItem ei = null;
                EANMetaAttribute ma = null;
                EANAttribute att = null;

                String str1 = rs.getString(1).trim();
                int i1 = rs.getInt(2);
                String str2 = rs.getString(3).trim();
                int i2 = rs.getInt(4);
                String str3 = rs.getString(5).trim();

                EntityGroup eg = getEntityGroup(str1);

                _db.debug(D.EBUG_SPEW, "gbl7006:answers:" + str1 + ":" + i1 + ":" + str2 + ":" + i2 + ":" + str3);
                if (eg == null) {
                    _db.debug(D.EBUG_ERR, "** Navigate Attribute Information for a non Existent Entity Group:" + 
                    		str1 + ": "+getParentActionItem().dump(false));
                    continue;
                }

                // Lets get the ei in the group first
                ei = eg.getEntityItem(str1, i1);

                // Only see if an existing parent entity item exists if we are Navigate, Edit, or Create
                if (getParentActionItem().isNavAction() || getParentActionItem().isEditAction() ||
                    getParentActionItem().isCreateAction()) {
                    ei = getParentEntityGroup().getEntityItem(str1, i1);
                    if (ei == null) {
                        ei = eg.getEntityItem(str1, i1);
                    }
                    else {
                        eg = getParentEntityGroup();
                    }
                }

                if (ei == null) {
                    _db.debug(D.EBUG_ERR,
                              "** Navigate Attribute Information for a non Existent Entity Item:" + 
                              str1 + ":" + i1 + ": "+getParentActionItem().dump(false));
                    continue;
                }

                ma = eg.getMetaAttribute(str2);
                if (ma == null) {
                    _db.debug(D.EBUG_ERR, "** Navigate Attribute Information for a non existent MetaAttribute:" +
                              str2 + " for entityitem " + ei.getKey()+" "+getParentActionItem().dump(false));
                    continue;
                }

                // Set up some Att objects
                att = ei.getAttribute(str2);
                if (att == null) {
                    if (ma.isText()) {
                        ta = new TextAttribute(ei, null, (MetaTextAttribute) ma);
                        ei.putAttribute(ta);
                    }
                    else if (ma.isSingle()) {
                        sfa = new SingleFlagAttribute(ei, null, (MetaSingleFlagAttribute) ma);
                        ei.putAttribute(sfa);
                    }
                    else if (ma.isStatus()) {
                        sa = new StatusAttribute(ei, getProfile(), (MetaStatusAttribute) ma);
                        ei.putAttribute(sa);
                    }
                    else if (ma.isMulti()) {
                        mfa = new MultiFlagAttribute(ei, null, (MetaMultiFlagAttribute) ma);
                        ei.putAttribute(mfa);
                    }
                    else if (ma.isABR()) {
                        tka = new TaskAttribute(ei, null, (MetaTaskAttribute) ma);
                        ei.putAttribute(tka);
                    }
                }
                else {
                    if (ma.isText()) {
                        ta = (TextAttribute) att;
                    }
                    else if (ma.isSingle()) {
                        sfa = (SingleFlagAttribute) att;
                    }
                    else if (ma.isStatus()) {
                        sa = (StatusAttribute) att;
                    }
                    else if (ma.isABR()) {
                        tka = (TaskAttribute) att;
                    }
                    else if (ma.isMulti()) {
                        mfa = (MultiFlagAttribute) att;
                    }
                }

                // OK.. drop the value into the structure
                if (ma.isText()) {
                    ta.putPDHData(i2, str3);
                }
                else {
                    // Lets try to conserve objects here
                    // not make a new string and reuse the ones in the Meta Flag Object
                    //
                    EANMetaFlagAttribute mfl = (EANMetaFlagAttribute) ma;
                    MetaFlag mf = mfl.getMetaFlag(str3);
                    String strFlagCode = (mf != null ? mf.getFlagCode() : str3);
                    if (ma.isABR()) {
                        tka.putPDHFlag(strFlagCode);
                        _db.debug(D.EBUG_SPEW,
                                  "PERF --- strFlagCode is:" + strFlagCode.hashCode() + ":" + tka.getFirstActiveFlagCode().hashCode());
                    }
                    else if (ma.isSingle()) {
                        sfa.putPDHFlag(strFlagCode);
                        _db.debug(D.EBUG_SPEW,
                                  "PERF --- strFlagCode is:" + strFlagCode.hashCode() + ":" + sfa.getFirstActiveFlagCode().hashCode());
                    }
                    else if (ma.isStatus()) {
                        sa.putPDHFlag(strFlagCode);
                        _db.debug(D.EBUG_SPEW,
                                  "PERF --- strFlagCode is:" + strFlagCode.hashCode() + ":" + sa.getFirstActiveFlagCode().hashCode());
                    }
                    else if (ma.isMulti()) {
                        mfa.putPDHFlag(strFlagCode);
                        _db.debug(D.EBUG_SPEW,
                                  "PERF --- strFlagCode is:" + strFlagCode.hashCode() + ":" + mfa.getFirstActiveFlagCode().hashCode());

                    }
                    else {
                        _db.debug(D.EBUG_ERR,
                                  "WARN:EntityList.popNavAttributeValues(). **Unknown Meta Type **" + str1 + ":" + i1 + ":" + str2 +
                                  ":" + i2 + ":" + str3);
                    }

                }
            }
        }
        finally {
            if (rs != null) { //catalog mods
                rs.close();
                rs = null;
            } //catalog mods
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }

        // GB - 04/28/04 - add in any derived attributes for this EntityGroup...
        for (int iD1 = 0; iD1 < this.getEntityGroupCount(); iD1++) {
            EntityGroup egD = this.getEntityGroup(iD1);
            for (int iD2 = 0; iD2 < egD.getEntityItemCount(); iD2++) {
                EntityItem eiD = egD.getEntityItem(iD2);
                ATTRIBUTE_LOOP:for (int iD3 = 0; iD3 < egD.getMetaAttributeCount(); iD3++) {
                    EANMetaAttribute emaD = egD.getMetaAttribute(iD3);
                    if (!emaD.isDerived()) {
                        continue ATTRIBUTE_LOOP;
                    }
                    if (emaD.getAttributeCode().equals("DERIVED_EID")) {
                        try {
                            TextAttribute taD = new TextAttribute(eiD, null, (MetaTextAttribute) emaD);
                            eiD.putAttribute(taD);
                            taD.put(":EID");
                        }
                        catch (EANBusinessRuleException eBRExc) {
                            _db.debug(D.EBUG_ERR, "WARN:EntityList.popNavAttributeValues():on put(:EID):" + eBRExc.getMessage());
                            continue ATTRIBUTE_LOOP;
                        }
                    }
                }
            }
        }
        // end GB - 04/28/04

    }

    /*
     *  Used to isolate the pulling of attribute values for the information in the trsNavigate Table
     */
    /**
     *  Description of the Method
     *
     * @param  _db                      Description of the Parameter
     * @param  _prof                    Description of the Parameter
     * @param  _iSessionID              Description of the Parameter
     * @param  _ai                      Description of the Parameter
     * @exception  SQLException         Description of the Exception
     * @exception  MiddlewareException  Description of the Exception
     */
    private void popAllAttributeValues(Database _db, Profile _prof, int _iSessionID, EANActionItem _ai) throws SQLException, MiddlewareException {
        // 'true' was passed for SearchActionItem and ExtractActionItem EntityList constructors used _bExtract == true
        // ...sooooo....
        boolean bExtract = (_ai instanceof ExtractActionItem || _ai instanceof SearchActionItem ? true : false);

        ReturnStatus returnStatus = new ReturnStatus();
        ReturnDataResultSet rdrs7538 = null;
        ResultSet rs = null;
        EntityGroup egD = null;

        EntityGroup eg = null;
        EntityGroup egParent = null;
        EntityItem ei = null;
        EANMetaAttribute ma = null;

        String strEnterprise = _prof.getEnterprise();
        String strRoleCode = _prof.getRoleCode();
        String strValOn = _prof.getValOn();
        String strEffOn = _prof.getEffOn();
        int iNLSID = _prof.getReadLanguage().getNLSID();

        try {
        	// RCQ242344 if ExpireOtherNLS is in any meta attr, must get allnls for those attrs
        	// build a hashset with attrcodes, pull allnls and only hold other nls values for those attrs
        	HashSet needAllNLSAttrSet = null;
        	if((_ai instanceof EditActionItem) && iNLSID ==1 && // edit action and nlsid=1
        			!(bExtract || _ai.isAllNLS())) {	// if this is true, allnls will be pulled anyway
        		for (int g=0;g<getEntityGroupCount(); g++){
        			EntityGroup egg = getEntityGroup(g);
        			if(!egg.canEdit()){
        				continue;
        			}
        			for (int m=0;m<egg.getMetaAttributeCount(); m++){
        				EANMetaAttribute mattr = egg.getMetaAttribute(m);
        				if(mattr.isEditable() && mattr.isExpireOtherNLS()){
        					if(needAllNLSAttrSet==null){
        						needAllNLSAttrSet = new HashSet();
        					}
        					needAllNLSAttrSet.add(mattr.getAttributeCode());
        				}
        			}
        		}
        	}
        	
        	
            //for extractItems, we want all nls's. And for specified all NLS pulls.
            if (bExtract || _ai.isAllNLS() || needAllNLSAttrSet!=null) {
                rs = _db.callGBL7520(returnStatus, strEnterprise, _iSessionID, strRoleCode, strValOn, strEffOn);
            }
            else {
                rs = _db.callGBL7007(returnStatus, strEnterprise, _iSessionID, strRoleCode, iNLSID, strValOn, strEffOn);
            }

            while (rs != null && rs.next()) { //catalog mods
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
                String strAttrValFrom = "";
                if (bExtract || needAllNLSAttrSet!=null) {
                    strAttrValFrom = rs.getString(6).trim().replace(' ', '-').replace(':', '.');
                    _db.debug(D.EBUG_SPEW,
                              "gbl7520:answers:" + str1 + ":" + i1 + ":" + str2 + ":" + i2 + ":" + str3 + ":" + strAttrValFrom);
                }
                else {
                    _db.debug(D.EBUG_SPEW, "gbl7007:answers:" + str1 + ":" + i1 + ":" + str2 + ":" + i2 + ":" + str3);
                }

                eg = getEntityGroup(str1);
                egParent = getParentEntityGroup();

                // If we cannot find the EntityGroup..
                // Lets shoot for the parent
                if (eg == null) {
                    eg = egParent;
                }

                D.ebug(D.EBUG_SPEW, "EntityList - 7007 - " + eg.getEntityType() + " from " + _ai.getActionItemKey());

                ei = eg.getEntityItem(str1, i1);

                // If I cannot get it out of the normal EntityGroup.. I will look in the parent
                if (ei == null) {
                    //  _db.debug(D.EBUG_WARN,"popAllAttributeValues().Looking in egParent for EntityItem...:" + egParent.getKey());
                    ei = egParent.getEntityItem(str1, i1);
                    if (ei != null) {
                        //  _db.debug(D.EBUG_WARN,"popAllAttributeValues().Found EntityItem in Parent..");
                        eg = egParent;
                    }
                }

                // RM gets to here

                // If I cannot find the Entity Item .. Skip it!

                if (ei == null) {
                    _db.debug(D.EBUG_ERR, "**7007 Attribute Information for a non Existant Entity Item:" + str1 + ":" + i1 + ":");
                    continue;
                }

                // If this is an Extract -> update the date modified on this EntityItem if its later than previous mod. date
                if (_ai instanceof ExtractActionItem) {
                    ExtractActionItem eai = (ExtractActionItem) _ai;
                    if (eai.hasIntervalItem()) {
                        ei.calcAttributesModified(strAttrValFrom, eai.getIntervalItem());
                    }
                }

                ma = eg.getMetaAttribute(str2);
                if (ma == null) {
                    continue;
                }

                // Lets update the Attribute.. it can only text and flag types now...
                // Set up some Att objects

                att = ei.getAttribute(str2);
                if (att == null) {
                    if (ma instanceof MetaTextAttribute) {
                        ta = new TextAttribute(ei, null, (MetaTextAttribute) ma);
                        ei.putAttribute(ta);
                    }
                    else if (ma instanceof MetaSingleFlagAttribute) {
                        sfa = new SingleFlagAttribute(ei, null, (MetaSingleFlagAttribute) ma);
                        ei.putAttribute(sfa);
                    }
                    else if (ma instanceof MetaMultiFlagAttribute) {
                        mfa = new MultiFlagAttribute(ei, null, (MetaMultiFlagAttribute) ma);
                        ei.putAttribute(mfa);
                    }
                    else if (ma instanceof MetaStatusAttribute) {
                        sa = new StatusAttribute(ei, null, (MetaStatusAttribute) ma);
                        ei.putAttribute(sa);
                    }
                    else if (ma instanceof MetaTaskAttribute) {
                        tska = new TaskAttribute(ei, null, (MetaTaskAttribute) ma);
                        ei.putAttribute(tska);
                    }
                    else if (ma instanceof MetaLongTextAttribute) {
                        lta = new LongTextAttribute(ei, null, (MetaLongTextAttribute) ma);
                        ei.putAttribute(lta);
                    }
                    else if (ma instanceof MetaXMLAttribute) {
                        xa = new XMLAttribute(ei, getProfile(), (MetaXMLAttribute) ma);
                        ei.putAttribute(xa);
                    }
                    else if (ma instanceof MetaBlobAttribute) {
                        ba = new BlobAttribute(ei, null, (MetaBlobAttribute) ma);
                        ei.putAttribute(ba);
                    }
                }
                else {
                    if (ma instanceof MetaTextAttribute) {
                        ta = (TextAttribute) att;
                    }
                    else if (ma instanceof MetaSingleFlagAttribute) {
                        sfa = (SingleFlagAttribute) att;
                    }
                    else if (ma instanceof MetaMultiFlagAttribute) {
                        mfa = (MultiFlagAttribute) att;
                    }
                    else if (ma instanceof MetaStatusAttribute) {
                        sa = (StatusAttribute) att;
                    }
                    else if (ma instanceof MetaTaskAttribute) {
                        tska = (TaskAttribute) att;
                    }
                    else if (ma instanceof MetaLongTextAttribute) {
                        lta = (LongTextAttribute) att;
                    }
                    else if (ma instanceof MetaXMLAttribute) {
                        xa = (XMLAttribute) att;
                    }
                    else if (ma instanceof MetaBlobAttribute) {
                        ba = (BlobAttribute) att;
                    }
                }

                // OK.. drop the value into the structure
                if(needAllNLSAttrSet!=null && i2!=1){ //RCQ242344
                	// dont store other nls for attributes that are not in the set
                	if(!needAllNLSAttrSet.contains(ma.getAttributeCode())){
                		continue;
                	}
                }

                if (ma instanceof MetaTextAttribute) {
                    ta.putPDHData(i2, str3);
                }
                else if (ma instanceof MetaSingleFlagAttribute) {
                    sfa.putPDHFlag(str3);
                }
                else if (ma instanceof MetaMultiFlagAttribute) {
                    mfa.putPDHFlag(str3);
                }
                else if (ma instanceof MetaStatusAttribute) {
                    sa.putPDHFlag(str3);
                }
                else if (ma instanceof MetaTaskAttribute) {
                    tska.putPDHFlag(str3);
                }
                else if (ma instanceof MetaLongTextAttribute) {
                    lta.putPDHData(i2, str3);
                }
                else if (ma instanceof MetaXMLAttribute) {
                    xa.putPDHData(i2, str3);
                }
                else if (ma instanceof MetaBlobAttribute) {
                    ba.putPDHData(i2, new OPICMBlobValue(i2, str3, null));
                }
                else {
                    _db.debug(D.EBUG_ERR,
                              "WARN:EntityList.popAllAttributeValues(). **Unknown Meta Type **" + str1 + ":" + i1 + ":" + str2 +
                              ":" + i2 + ":" + str3);
                }
            }

            if(needAllNLSAttrSet!=null){
            	needAllNLSAttrSet.clear();
            	needAllNLSAttrSet = null;
            }
        }
        finally {
            if (rs != null) { //catalog mods
                rs.close();
                rs = null;
            } //catalog mods
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }

        // GB - 03/31/04 - add in any derived attributes for this EntityGroup...
        for (int iD1 = 0; iD1 < this.getEntityGroupCount(); iD1++) {
            egD = this.getEntityGroup(iD1);
            for (int iD2 = 0; iD2 < egD.getEntityItemCount(); iD2++) {
                EntityItem eiD = egD.getEntityItem(iD2);
                ATTRIBUTE_LOOP:for (int iD3 = 0; iD3 < egD.getMetaAttributeCount(); iD3++) {
                    EANMetaAttribute emaD = egD.getMetaAttribute(iD3);
                    if (!emaD.isDerived()) {
                        continue ATTRIBUTE_LOOP;
                    }
                    if (emaD.getAttributeCode().equals("DERIVED_EID")) {
                        try {
                            TextAttribute taD = new TextAttribute(eiD, null, (MetaTextAttribute) emaD);
                            eiD.putAttribute(taD);
                            taD.put(":EID");
                        }
                        catch (EANBusinessRuleException eBRExc) {
                            _db.debug(D.EBUG_ERR, "WARN:EntityList.popAllAttributeValues():on put(:EID):" + eBRExc.getMessage());
                            continue ATTRIBUTE_LOOP;
                        }
                    }
                }
            }
        }
        // end GB - 03/31/04

        // add DERIVED_EID for parent entitygroup also

        egD = this.getParentEntityGroup();
        if (egD != null) {
            for (int iD2 = 0; iD2 < egD.getEntityItemCount(); iD2++) {
                EntityItem eiD = egD.getEntityItem(iD2);
                ATTRIBUTE_LOOP:for (int iD3 = 0; iD3 < egD.getMetaAttributeCount(); iD3++) {
                    EANMetaAttribute emaD = egD.getMetaAttribute(iD3);
                    if (!emaD.isDerived()) {
                        continue ATTRIBUTE_LOOP;
                    }
                    if (emaD.getAttributeCode().equals("DERIVED_EID")) {
                        try {
                            TextAttribute taD = new TextAttribute(eiD, null, (MetaTextAttribute) emaD);
                            eiD.putAttribute(taD);
                            taD.put(":EID");
                        }
                        catch (EANBusinessRuleException eBRExc) {
                            _db.debug(D.EBUG_ERR, "WARN:EntityList.popAllAttributeValues():on put(:EID):" + eBRExc.getMessage());
                            continue ATTRIBUTE_LOOP;
                        }
                    }
                }
            }
        }

        // GB - 02/06/03 - If ExtractActionItem constructor AND if interval speicified ->
        if (_ai instanceof ExtractActionItem) {
            ExtractActionItem eai = (ExtractActionItem) _ai;
            if (eai.hasIntervalItem()) {
                String strStartDate = eai.getIntervalItem().getStartDate();
                String strEndDate = eai.getIntervalItem().getEndDate();
                try {
                    rs = _db.callGBL7538(returnStatus, strEnterprise, _iSessionID, strRoleCode, strStartDate, strEndDate);
                    rdrs7538 = new ReturnDataResultSet(rs);
                }
                finally {
					if (rs !=null){
                    	rs.close();
                    	rs = null;
					}
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
                for (int row = 0; row < rdrs7538.getRowCount(); row++) {
                    String strEntityType = rdrs7538.getColumn(row, 0);
                    int iEntityID = rdrs7538.getColumnInt(row, 1);
                    _db.debug(D.EBUG_SPEW, "gbl7538:answers:" + strEntityType + ":" + iEntityID);
                    //pull out EntityItems to set as changed
                    for (int i = 0; i < this.getEntityGroupCount(); i++) {
                        eg = this.getEntityGroup(i);
                        for (int j = 0; j < eg.getEntityItemCount(); j++) {
                            ei = eg.getEntityItem(j);
                            if (ei.getEntityType().equals(strEntityType) && ei.getEntityID() == iEntityID) {
                                ei.setModifiedInInterval();
                            }
                        }
                    }
                }
            }
        }
    }

    /*
     *  This only goes after default values...
     */
    /**
     *  Description of the Method
     *
     * @param  _db                      Description of the Parameter
     * @param  _prof                    Description of the Parameter
     * @param  _ai                      Description of the Parameter
     * @exception  SQLException         Description of the Exception
     * @exception  MiddlewareException  Description of the Exception
     */
    private void popDefaultEntityItems(Database _db, Profile _prof, EANActionItem _ai) throws SQLException, MiddlewareException {
        //  Get the entityid
        int iEntityID = _prof.getDefaultIndex();
        if (_ai instanceof EditActionItem) {
            EditActionItem eai = (EditActionItem) _ai;
            if (eai.hasDefaultIndex()) {
                iEntityID = eai.getDefaultIndex();
            }
        }
        else if (_ai instanceof CreateActionItem) {
            CreateActionItem cai = (CreateActionItem) _ai;
            if (cai.hasDefaultIndex()) {
                iEntityID = cai.getDefaultIndex();
            }
        }
        else if (_ai instanceof CopyActionItem) {
            CopyActionItem cai = (CopyActionItem) _ai;
            if (cai.hasDefaultIndex()) {
                iEntityID = cai.getDefaultIndex();
            }
        }
        //
        // go make the default entities..
        //
        for (int ii = 0; ii < getEntityGroupCount(); ii++) {
            EntityGroup eg = getEntityGroup(ii);
            String strEntityType = eg.getEntityType();
       	    putDefaultEntityItem(new EntityItem(eg, null, _db, strEntityType, iEntityID));
        }

    }

    /**
     * For Edits/Navs of certain ~multiple~ entities, we need to be able to define column orders for attributes across entities.
     * We'll do this by stashing the MetaColumnOrder group in the ActionItem.  this overrides any column orders on entities themselves.
     */
    private final void buildActionItemColumnOrders(Database _db, EANActionItem _ai) throws SQLException, MiddlewareException,
        MiddlewareRequestException {
        MetaColumnOrderGroup mcog = new MetaColumnOrderGroup(_db, this);
        if (mcog.getMetaColumnOrderItemCount() > 0) {
            D.ebug(D.EBUG_SPEW, "setting MetaColumnOrderGroup on Action Item:" + _ai.getKey());
            _ai.setMetaColumnOrderGroup(mcog);
        }
    }

    /**
     *  Gets the parentToStrings attribute of the EntityList object
     *
     * @return    The parentToStrings value
     */
    private String getParentToStrings() {
        return getParentToStrings(50);
    }

    /**
     *  Gets the parentToStrings attribute of the EntityList object
     *
     * @return    The parentToStrings value
     */
    private String getParentToStrings(int maxlen) {

        StringBuffer strbResult = new StringBuffer();
        EntityGroup egParent = getParentEntityGroup();

        if (egParent == null || egParent.getEntityItemCount() == 0) {
            return "()";
        }

        for (int ii = 0; ii < egParent.getEntityItemCount(); ii++) {
            if (ii > 0) {
                strbResult.append(" & ");
            }
            strbResult.append(egParent.getEntityItem(ii).toString());
        }
        /*
         cr0202062350
         we truncate to 50 characters we should extend
         this per the cr to show the first 100 characters.
         the code that is in place should actually work.
         */
        return "(" + truncate(strbResult.toString(), maxlen) + ")";
    }

    /*
     *  Supports toString
     */
    /**
     *  Description of the Method
     *
     * @param  _s    Description of the Parameter
     * @param  _len  Description of the Parameter
     * @return       Description of the Return Value
     */
    private String truncate(String _s, int _len) {
        if (_s == null) {
            return "";
        }
        if (_s.length() <= _len) {
            return _s;
        }
        return _s.substring(0, _len) + "...";
    }
 
    /*
     *  Cleans up all internal structures
     */
    /**
     *  Description of the Method
     */
    public void dereference() {
        for (int ii = getEntityGroupCount() - 1; ii >= 0; ii--) {
            EntityGroup eg = getEntityGroup(ii);
            eg.dereference();
            removeEntityGroup(eg);
        }
        if (getParentEntityGroup() != null) {
            getParentEntityGroup().dereference();
            setParentEntityGroup(null);
        }
        
        setActionList(null);

		if (m_eiTag!=null){
			m_eiTag.dereference();
			m_eiTag = null;
		}
		if (m_elDefaultEntityItems!=null){
			m_elDefaultEntityItems.clear();
			m_elDefaultEntityItems = null;
		}
		if (m_aiParent!=null){
			// PDG reuse actions m_aiParent.dereference();
			m_aiParent = null;
		}
		executionDTS=null;

        super.dereference();
    }

    /**
     *  Description of the Method
     *
     * @param  _bBrief  Description of the Parameter
     * @return          Description of the Return Value
     */
    public String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();

        if (_bBrief) {
            strbResult.append(getKey());
        }
        else {
            EntityGroup egParent = null;
            strbResult.append(NEW_LINE + "EntityList:" + getKey() + ":" + toString());
            strbResult.append(NEW_LINE + "---------------------------------------------");
            strbResult.append(NEW_LINE + "Profile:" + getProfile().toString());
            strbResult.append(NEW_LINE + "---------------------------------------------");
            strbResult.append(NEW_LINE + "ParentActionItem:" + getParentActionItem().dump(_bBrief));
            strbResult.append(NEW_LINE + "---------------------------------------------");
            if (getActionList() == null) {
                strbResult.append(NEW_LINE + "ActionList:**null**");
            }
            else {
                strbResult.append(NEW_LINE + "ActionList:" + getActionList().dump(_bBrief));
            }
            egParent = getParentEntityGroup();
            strbResult.append(NEW_LINE + "---------------------------------------------");
            strbResult.append(NEW_LINE + "ParentEntityGroup:");
            if (egParent == null) {
                strbResult.append(NEW_LINE + "ParentEntityGroup: is null");
            }
            else {
                strbResult.append(egParent.dump(_bBrief));
            }
            strbResult.append(NEW_LINE + "---------------------------------------------");
            strbResult.append(NEW_LINE + "Default Entity Items:");
            for (int ii = 0; ii < getDefaultEntityItemCount(); ii++) {
                EntityItem ei = getDefaultEntityItem(ii);
                strbResult.append(NEW_LINE + "DEI # " + ii + ":" + NEW_LINE + ei.dump(_bBrief));
            }
            strbResult.append(NEW_LINE + "---------------------------------------------");
            strbResult.append(NEW_LINE + "EntityGroups:");
            for (int ii = 0; ii < getEntityGroupCount(); ii++) {
                EntityGroup eg = getEntityGroup(ii);
                strbResult.append(NEW_LINE + "EG # " + ii + ":" + NEW_LINE + eg.dump(_bBrief));
            }
            strbResult.append(NEW_LINE + "---------------------------------------------");
        }

        return strbResult.toString();
    }

    //
    // This is the table wrapper stuff to Render its Entity Groups
    //

    /*
     *  return the column information here
     */
    /**
     *  Gets the columnList attribute of the EntityList object
     *
     * @return    The columnList value
     */
    public EANList getColumnList() {
        return getMeta();
    }

    /*
     *  Return only visible rows
     */
    /**
     *  Gets the rowList attribute of the EntityList object
     *
     * @return    The rowList value
     */
    public EANList getRowList() {
        EANList el = new EANList(EANMetaEntity.LIST_SIZE);
        for (int ii = 0; ii < getEntityGroupCount(); ii++) {
            EntityGroup eg = getEntityGroup(ii);
            if (eg.isDisplayable()) {
                el.put(eg);
            }
        }
        return el;
    }

    /*
     *  Returns a basic table adaptor for client rendering of EntityLists
     */
    /**
     *  Gets the table attribute of the EntityList object
     *
     * @return    The table value
     */
    public RowSelectableTable getTable() {

        try {
            // Meta for a simple table display using description
            putMeta(new MetaLabel(this, getProfile(), EntityGroup.DESCRIPTION, "Description", String.class));

        }
        catch (Exception x) {
            // Do nothing.
        }
        return new RowSelectableTable(this, getLongDescription());
    }

    /**
     *  Gets the activeEntityGroupIndex attribute of the EntityList object
     *
     * @return    The activeEntityGroupIndex value
     */
    public int getActiveEntityGroupIndex() {
        return m_iActiveEntityGroup;
    }

    //
    // Cart Interface
    //

    /*
     *  Adds an array of entityItems to this Object, used for a remote Database Connection
     */
    /**
     *  Adds a feature to the ToCart attribute of the EntityList object
     *
     * @param  _rdi                                       The feature to be added to the ToCart attribute
     * @param  _aei                                       The feature to be added to the ToCart attribute
     * @param  _bGetDownLink                              The feature to be added to the ToCart attribute
     * @return                                            Description of the Return Value
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  RemoteException                        Description of the Exception
     */
    public EntityGroup addToCart(RemoteDatabaseInterface _rdi, EntityItem[] _aei, boolean _bGetDownLink) throws
        MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {

        EntityGroup egCart = null;

        if (!getKey().equals("Cart")) {
            throw new MiddlewareRequestException("You are attempting to perform a Cart function on something other than a Cart");
        }
        for (int ii = 0; ii < _aei.length; ii++) {
            try {
                egCart = addToCart(_rdi, null, _aei[ii], _bGetDownLink);
            }
            catch (SQLException x) {
                // Should never happen
            }
        }

        return egCart;
    }

    /*
     *  Adds an array of entityItems to this Object, used with a Database Object
     */
    /**
     *  Adds a feature to the ToCart attribute of the EntityList object
     *
     * @param  _db                                        The feature to be added to the ToCart attribute
     * @param  _aei                                       The feature to be added to the ToCart attribute
     * @param  _bGetDownLink                              The feature to be added to the ToCart attribute
     * @return                                            Description of the Return Value
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  SQLException                           Description of the Exception
     */
    public EntityGroup addToCart(Database _db, EntityItem[] _aei, boolean _bGetDownLink) throws MiddlewareRequestException,
        MiddlewareException, MiddlewareShutdownInProgressException, SQLException {
        EntityGroup egCart = null;

        if (!getKey().equals("Cart")) {
            throw new MiddlewareRequestException("You are attempting to perform a Cart function on something other than a Cart");
        }
        for (int ii = 0; ii < _aei.length; ii++) {
            try {
                egCart = addToCart(null, _db, _aei[ii], _bGetDownLink);
            }
            catch (RemoteException x) {
                // Should never happen
            }
        }

        return egCart;
    }

    /**
     *  Adds a feature to the ToCart attribute of the EntityList object
     *
     * @param  _rdi                                       The feature to be added to the ToCart attribute
     * @param  _db                                        The feature to be added to the ToCart attribute
     * @param  _ei                                        The feature to be added to the ToCart attribute
     * @param  _bGetDownLink                              The feature to be added to the ToCart attribute
     * @return                                            Description of the Return Value
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  SQLException                           Description of the Exception
     * @exception  RemoteException                        Description of the Exception
     */
    public EntityGroup addToCart(RemoteDatabaseInterface _rdi, Database _db, EntityItem _ei, boolean _bGetDownLink) throws
        MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException, RemoteException {

        EntityList el = null;
        EntityGroup egCart = null;
        EntityItem eiCopy = null;
        EntityItem ei = null;

        EntityGroup eg = (EntityGroup) _ei.getParent();

        if (!getKey().equals("Cart")) {
            throw new MiddlewareRequestException("You are attempting to perform a Cart function on something other than a Cart");
        }

        // We must first hunt for the entity guy .. It needs to have a parent group
        if (eg == null) {
            D.ebug(D.EBUG_SPEW, "12100 - No parent for:" + _ei.getKey());
            return null;
        }

        ei = _ei;
        el = (EntityList) eg.getParent();
        if (el != null) {
            EANActionItem ai = el.getParentActionItem();
            if (ai instanceof NavActionItem) {
                NavActionItem nai = (NavActionItem) ai;
                if (nai.isNoDownLink(ei.getEntityType())) {
                    _bGetDownLink = false;
                }
            }
        }

        if (_bGetDownLink) {

            if (eg.isRelator() || eg.isAssoc()) {
                boolean bFound = false;
                String strEntity2Type = eg.getEntity2Type();
                for (int ii = 0; ii < _ei.getDownLinkCount(); ii++) {
                    EntityItem eiDown = (EntityItem) _ei.getDownLink(ii);
                    EntityGroup egDown = (EntityGroup) eiDown.getParent();
                    if (egDown.isRelator() || egDown.isAssoc()) {
                        // this is for 3.0a, relators can have links to entities
                        ei = _ei;
                        bFound = true;
                        break;
                    }
                    else {
                        if (eiDown.getEntityType().equals(strEntity2Type)) {
                            ei = eiDown;
                            bFound = true;
                            break;
                        }
                    }
                }
                if (!bFound) {
                    D.ebug(D.EBUG_SPEW, "121001 - Cannot add:" + ei + ": to Cart.. it is a relator w/ no matching child");
                    return null;
                }
            }
        }

        // We now have an ei that is workable. (True entity2type)
        // Now look for an existing Tab that will qualify..

        egCart = getEntityGroup(ei.getEntityType());

        // Make a new on if needed ...
        if (egCart == null) {
            if (_db == null) {
                egCart = _rdi.getEntityGroup(getProfile(), ei.getEntityType(), "Cart");
            }
            else {
                egCart = new EntityGroup(this, _db, getProfile(), ei.getEntityType(), "Cart");
            }
            putEntityGroup(egCart);
        }

        // Always make a copy
        eiCopy = new EntityItem(egCart, null, ei);
        egCart.putEntityItem(eiCopy);
        // make sure it is owned here.
        eiCopy.reassign(egCart);
        return egCart;
    }

    /**
     *  Gets the entityList attribute of the EntityList class
     *
     * @param  _rdi                                       Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @param  _ai                                        Description of the Parameter
     * @param  _aei                                       Description of the Parameter
     * @return                                            The entityList value
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  RemoteException                        Description of the Exception
     */
    public static EntityList getEntityList(RemoteDatabaseInterface _rdi, Profile _prof, NavActionItem _ai, EntityItem[] _aei) throws
        MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {

        ActionList al = ObjectPool.getInstance().getActionList(_prof.getRoleCode() + _ai.getActionItemKey() +
            _prof.getReadLanguage().getNLSID());
        try {
            EntityList el = getEntityList(_rdi, null, _prof, _ai, _aei, (al == null));
            if (al == null) {
                ObjectPool.getInstance().putActionList(el.getActionList());
                D.ebug("Putting Action List:" + el.getActionList());
            }
            else {
                el.setActionList(al);
                D.ebug("Found Action List :" + al);
            }

            return el;

        }
        catch (SQLException x) {
            // Should not happen
            return null;
        }
    }

    /*
     *  This handles Navigateion - generates anew list based upon the input.
     */
    /**
     *  Gets the entityList attribute of the EntityList class
     *
     * @param  _db                                        Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @param  _ai                                        Description of the Parameter
     * @param  _aei                                       Description of the Parameter
     * @return                                            The entityList value
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  SQLException                           Description of the Exception
     */
    public static EntityList getEntityList(Database _db, Profile _prof, NavActionItem _ai, EntityItem[] _aei) throws
        MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException {
        try {
            return getEntityList(null, _db, _prof, _ai, _aei, true);
        }
        catch (RemoteException x) {
            // Should not happen
            return null;
        }
    }

    /*
     *  This handles Navigation -  This guy generates anew list based upon the input
     */
    /**
     *  Gets the entityList attribute of the EntityList class
     *
     * @param  _rdi                                       Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @param  _ai                                        Description of the Parameter
     * @param  _aei                                       Description of the Parameter
     * @return                                            The entityList value
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  RemoteException                        Description of the Exception
     */
    public static EntityList getEntityList(RemoteDatabaseInterface _rdi, Profile _prof, ExtractActionItem _ai, EntityItem[] _aei) throws
        MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {
        try {
            return getEntityList(_rdi, null, _prof, _ai, _aei);
        }
        catch (SQLException x) {
            // Should not happen
            return null;
        }
    }

    /*
     *  This handles Navigateion - generates anew list based upon the input.
     */
    /**
     *  Gets the entityList attribute of the EntityList class
     *
     * @param  _db                                        Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @param  _ai                                        Description of the Parameter
     * @param  _aei                                       Description of the Parameter
     * @return                                            The entityList value
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  SQLException                           Description of the Exception
     */
    public static EntityList getEntityList(Database _db, Profile _prof, ExtractActionItem _ai, EntityItem[] _aei) throws
        MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException {
        try {
            return getEntityList(null, _db, _prof, _ai, _aei);
        }
        catch (RemoteException x) {
            // Should not happen
            return null;
        }
    }

    /*
     *  This handles the Creation Case - this generates a new EntityList set up with a create capability
     */
    /**
     *  Gets the entityList attribute of the EntityList class
     *
     * @param  _db                                        Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @param  _ai                                        Description of the Parameter
     * @param  _aei                                       Description of the Parameter
     * @return                                            The entityList value
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  SQLException                           Description of the Exception
     */
    public static EntityList getEntityList(Database _db, Profile _prof, CreateActionItem _ai, EntityItem[] _aei) throws
        MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException {

        EntityList.checkDomain(_prof,_ai,_aei); //RQ0713072645

        // We need to shift things to the right and clip the answer
        //
        return _db.getEntityList(_prof, new CreateActionItem(_ai), clip(shiftRight(_aei, _ai)));
    }

    /*
     *  This handles the Creation Case - this generates a new EntityList set up with a create capability
     *  We may have to clip the parents so we do not send a bunch of stuff up
     */
    /**
     *  Gets the entityList attribute of the EntityList class
     *
     * @param  _rdi                                       Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @param  _ai                                        Description of the Parameter
     * @param  _aei                                       Description of the Parameter
     * @return                                            The entityList value
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  RemoteException                        Description of the Exception
     */
    public static EntityList getEntityList(RemoteDatabaseInterface _rdi, Profile _prof, CreateActionItem _ai, EntityItem[] _aei) throws
        MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {

        EntityList.checkDomain(_prof,_ai,_aei); //RQ0713072645

        // DWB Clip the parents so we do not get any bleeding on the object in rmi
        // No shifting because the parents do not need to be shifted
        // What is passed should be the parent
        return _rdi.getEntityList(_prof, new CreateActionItem(_ai), clip(shiftRight(_aei, _ai)));
    }

    /*
     *  This handles the Edit Case - this generates a new EntityList set up with a create capability
     */
    /**
     *  Gets the entityList attribute of the EntityList class
     *
     * @param  _db                                        Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @param  _ai                                        Description of the Parameter
     * @param  _aei                                       Description of the Parameter
     * @return                                            The entityList value
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  SQLException                           Description of the Exception
     */
    public static EntityList getEntityList(Database _db, Profile _prof, EditActionItem _ai, EntityItem[] _aei) throws
        MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException {

        EntityItem[] aei = null;
        EditActionItem eai = null;

        T.est(_aei != null, "getEntityList(db, EditActionItem). _aei is null");
        T.est(_aei.length > 0, "getEntityList(db, EditActionItem). _aei has no entityItems in it");
        // Modify if to see if it is an entityonly in hiding
        _ai.modifyActionItem(_aei[0]);

        D.ebug(D.EBUG_DETAIL, "hasMetaColumnOrderGroup (" + _ai.getKey() + ") ??? C " + _ai.hasMetaColumnOrderGroup());

        aei = clip(_ai, _aei);
        eai = new EditActionItem(_ai);

        D.ebug(D.EBUG_DETAIL, "hasMetaColumnOrderGroup (" + _ai.getKey() + ") ??? D " + _ai.hasMetaColumnOrderGroup());

        EntityList list = _db.getEntityList(_prof, eai, aei);
        
        return list;
    }

    /*
     *  This handles theEdit Case - this generates a new EntityList set up with a create capability
     *  We may have to clip the parents so we do not send a bunch of stuff up
     */
    /**
     *  Gets the entityList attribute of the EntityList class
     *
     * @param  _rdi                                       Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @param  _ai                                        Description of the Parameter
     * @param  _aei                                       Description of the Parameter
     * @return                                            The entityList value
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  RemoteException                        Description of the Exception
     */
    public static EntityList getEntityList(RemoteDatabaseInterface _rdi, Profile _prof, EditActionItem _ai, EntityItem[] _aei) throws
        MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {

        EntityItem[] aei = null;
        EditActionItem eai = null;
        EntityList el = null;

        // No shifting because the parents do not need to be shifted
        // Since this is rmi  we have to take extra care in dealing ensure we do not send the whole object back
        // We attempt to clip the array of entites to ensure that we do not send the whole object back up
        T.est(_aei != null, "getEntityList(rdi, EditActionItem). _aei is null");
        T.est(_aei.length > 0, "getEntityList(rdi, EditActionItem). _aei has no entityItems in it");

        D.ebug(D.EBUG_DETAIL, "hasMetaColumnOrderGroup (" + _ai.getKey() + ") ??? C " + _ai.hasMetaColumnOrderGroup());

        // Modify if to see if it is an entityonly in hiding
        _ai.modifyActionItem(_aei[0]);

        aei = clip(_ai, _aei);

        eai = new EditActionItem(_ai);

        D.ebug(D.EBUG_DETAIL, "hasMetaColumnOrderGroup (" + _ai.getKey() + ") ??? D " + _ai.hasMetaColumnOrderGroup());

        el = _rdi.getEntityList(_prof, eai, aei);

        D.ebug(D.EBUG_DETAIL, "hasMetaColumnOrderGroup (" + _ai.getKey() + ") ??? E " + _ai.hasMetaColumnOrderGroup());
 
        return el;

    }

    /*
     *  This handles the Edit Case - this generates a new EntityList set up with a create capability
     */
    /**
     *  Gets the entityList attribute of the EntityList class
     *
     * @param  _db                                        Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @param  _ai                                        Description of the Parameter
     * @param  _aei                                       Description of the Parameter
     * @return                                            The entityList value
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  SQLException                           Description of the Exception
     */
    public static EntityList getEntityList(Database _db, Profile _prof, CopyActionItem _ai, EntityItem[] _aei) throws
        MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException {
        T.est(_aei != null, "getEntityList(db, CopyActionItem). _aei is null");
        T.est(_aei.length > 0, "getEntityList(db, CopyActionItem). _aei has no entityItems in it");

		EntityList.checkDomain(_prof,_ai,_aei); //RQ0713072645

        return _db.getEntityList(_prof, new CopyActionItem(_ai), clip(_ai, _aei));
    }

    /*
     *  This handles theCopy Case - this generates a new EntityList set up with a create capability
     *  We may have to clip the parents so we do not send a bunch of stuff up
     */
    /**
     *  Gets the entityList attribute of the EntityList class
     *
     * @param  _rdi                                       Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @param  _ai                                        Description of the Parameter
     * @param  _aei                                       Description of the Parameter
     * @return                                            The entityList value
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  RemoteException                        Description of the Exception
     */
    public static EntityList getEntityList(RemoteDatabaseInterface _rdi, Profile _prof, CopyActionItem _ai, EntityItem[] _aei) throws
        MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {
        // No shifting because the parents do not need to be shifted
        // Since this is rmi  we have to take extra care in dealing ensure we do not send the whole object back
        // We attempt to clip the array of entites to ensure that we do not send the whole object back up
        T.est(_aei != null, "getEntityList(rdi, CopyActionItem). _aei is null");
        T.est(_aei.length > 0, "getEntityList(rdi, CopyActionItem). _aei has no entityItems in it");

		EntityList.checkDomain(_prof,_ai,_aei); //RQ0713072645

        return _rdi.getEntityList(_prof, new CopyActionItem(_ai), clip(_ai, _aei));
    }

    /**
     * getEntityList
     *
     * @param _db
     * @param _prof
     * @param _ai
     * @param _aei
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.sql.SQLException
     * @return
     *  @author David Bigelow
     */
    public static EntityList getEntityList(Database _db, Profile _prof, ABRStatusActionItem _ai, EntityItem[] _aei) throws
        MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException {
        T.est(_aei != null, "getEntityList(db, CopyActionItem). _aei is null");
        T.est(_aei.length > 0, "getEntityList(db, CopyActionItem). _aei has no entityItems in it");

		EntityList.checkDomain(_prof,_ai,_aei); //RQ0713072645

        return _db.getEntityList(_prof, new ABRStatusActionItem(_ai), clip(_ai, _aei));
    }

    /**
     * getEntityList
     *
     * @param _rdi
     * @param _prof
     * @param _ai
     * @param _aei
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    public static EntityList getEntityList(RemoteDatabaseInterface _rdi, Profile _prof, ABRStatusActionItem _ai, EntityItem[] _aei) throws
        MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {
        // No shifting because the parents do not need to be shifted
        // Since this is rmi  we have to take extra care in dealing ensure we do not send the whole object back
        // We attempt to clip the array of entites to ensure that we do not send the whole object back up
        T.est(_aei != null, "getEntityList(rdi, CopyActionItem). _aei is null");
        T.est(_aei.length > 0, "getEntityList(rdi, CopyActionItem). _aei has no entityItems in it");

		EntityList.checkDomain(_prof,_ai,_aei); //RQ0713072645

        return _rdi.getEntityList(_prof, new ABRStatusActionItem(_ai), clip(_ai, _aei));
    }

    /*
     *  This handles the Creation Case - this generates a new EntityList set up with a create capability
     *  We may have to clip the parents so we do not send a bunch of stuff up
     */
    /**
     *  Gets the entityList attribute of the EntityList class
     *
     * @param  _rdi                                       Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @param  _ai                                        Description of the Parameter
     * @return                                            The entityList value
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  RemoteException                        Description of the Exception
     */
    public static EntityList getEntityList(RemoteDatabaseInterface _rdi, Profile _prof, SearchActionItem _ai) throws
        MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {
        // DWB Clip the parents so we do not get any bleeding on the object in rmi
        return _rdi.getEntityList(_prof, new SearchActionItem(_ai));
    }

    /*
     *  Shift right for the CreateActionItem type transactions
     */
    /**
     *  Description of the Method
     *
     * @param  _aei  Description of the Parameter
     * @param  _ai   Description of the Parameter
     * @return       Description of the Return Value
     */
    private static EntityItem[] shiftRight(EntityItem[] _aei, CreateActionItem _ai) {

        // Lets simply shift down to any Entity2Type if we need to..
        // if its a stand alone create

        if (_ai.isStandAlone()) {
            for (int ii = 0; ii < _aei.length; ii++) {
                for (int ij = 0; ij < _aei[ii].getDownLinkCount(); ij++) {
                    EntityItem ent = (EntityItem) _aei[ii].getDownLink(ij);
                    _aei[ii] = ent;
                }
            }
        }
        else {
            // We should have a MetaLink record to work with
            //
            String strParentEntityType = _ai.getMetaLink().getEntity1Type();
            if (_ai.isCreateParent()) {
                strParentEntityType = _ai.getMetaLink().getEntity2Type();
            }

            for (int ii = 0; ii < _aei.length; ii++) {
                if (! (_aei[ii].getEntityType().equals(strParentEntityType))) {
                    boolean bFound = false;
                    for (int ij = 0; ij < _aei[ii].getDownLinkCount(); ij++) {
                        EntityItem ent = (EntityItem) _aei[ii].getDownLink(ij);
                        if (ent.getEntityType().equals(strParentEntityType)) {
                            _aei[ii] = ent;
                            bFound = true;
                            break;
                        }
                    } // Lets look up now ...
                    for (int ij = 0; ij < _aei[ii].getUpLinkCount(); ij++) {
                        EntityItem ent = (EntityItem) _aei[ii].getUpLink(ij);
                        if (ent.getEntityType().equals(strParentEntityType)) {
                            _aei[ii] = ent;
                            bFound = true;
                            break;
                        }
                    }
                    if (!bFound) {
                        System.out.println("ERR:EntityList.shiftRight(CreateActionItem):Cannot find an entities to match (" +
                                           strParentEntityType + ") - " + _aei[0].toString() + "->DownLink:" +
                                           (_aei[0].getDownLink(0) == null ? "NONE" : _aei[0].getDownLink(0).getEntityType()));
                    }
                }
            }
        }

        return _aei;
    }

    /*
     *  This basically clips your array
     */
    /**
     *  Description of the Method
     *
     * @param  _aei  Description of the Parameter
     * @return       Description of the Return Value
     */
    private static EntityItem[] clip(EntityItem[] _aei) {

        try {
            for (int ii = 0; ii < _aei.length; ii++) {
                // DWB Change to ensure attributes go up
                //              _aei[ii] = new EntityItem(null,_aei[ii].getProfile(),_aei[ii].getEntityType(), _aei[ii].getEntityID());
                _aei[ii] = new EntityItem(null, _aei[ii].getProfile(), _aei[ii]);
            }
        }
        catch (Exception x) {
            x.printStackTrace();
        }

        return _aei;
    }

    /*
     *  Shift right for the Standard EntityItem/Extract Item Transactions transactions
     */
    /**
     *  Description of the Method
     *
     * @param  _aei  Description of the Parameter
     * @param  _ai   Description of the Parameter
     * @return       Description of the Return Value
     */
    public static EntityItem[] shiftRight(EntityItem[] _aei, ExtractActionItem _ai) {

        String strEntityType = _ai.getTargetEntityType();
        if (strEntityType == null) {
            return _aei;
        }
        if (strEntityType.equals("")) {
            return _aei;
        }
        for (int ii = 0; ii < _aei.length; ii++) {
            if (! (_aei[ii].getEntityType().equals(strEntityType))) {
                boolean bFound = false;
                for (int ij = 0; ij < _aei[ii].getDownLinkCount(); ij++) {
                    EntityItem ent = (EntityItem) _aei[ii].getDownLink(ij);
                    if (ent.getEntityType().equals(strEntityType)) {
                        _aei[ii] = ent;
                        bFound = true;
                    }
                }
                if (!bFound) {
                    System.out.println("Error: cannot find home for:" + _aei[0].toString() + "->DownLink:" +
                                       (_aei[0].getDownLink(0) == null ? "NONE" : _aei[0].getDownLink(0).getEntityType()));
                }
            }
        }

        return _aei;
    }

    /*
     *  Shift right for the EditActionItem type transactions
     */
    /**
     *  Description of the Method
     *
     * @param  _ai   Description of the Parameter
     * @param  _aei  Description of the Parameter
     * @return       Description of the Return Value
     */
    private static EntityItem[] clip(EditActionItem _ai, EntityItem[] _aei) {

        EntityItem[] aei = null;

        EANList el = new EANList(EANMetaEntity.LIST_SIZE);
        EANList elHoldE1 = new EANList(EANMetaEntity.LIST_SIZE);
        EANList elHoldE2 = new EANList(EANMetaEntity.LIST_SIZE);

        EntityGroup egParent = null;
        EntityGroup egRelator = null;
        for (int ii = 0; ii < _aei.length; ii++) {
            try {

                // OK if we are dealing with Entities only.. lets ensure that what was
                // passed matches the Edit Target Entity
                if (_ai.isEntityOnly()) {
                    // We may have to shift right to find the proper entitytype
                    if (_ai.getTargetEntity().equals(_aei[ii].getEntityType())) {
                        el.put(new EntityItem(null, _ai.getProfile(), _aei[ii]));
                    }
                    else {
                        EntityItem ei = (EntityItem) _aei[ii].getDownLink(0);
                        if (ei != null) {
                            if (_ai.getTargetEntity().equals(ei.getEntityType())) {
                                D.ebug(D.EBUG_SPEW, "Eclip:editTargetOnly: ** Shift and found");
                                el.put(new EntityItem(null, _ai.getProfile(), ei));
                            }
                            else {
                                D.ebug(D.EBUG_SPEW, "Eclip:editTargetOnly: ** Cannot find a thing");
                            }
                        }
                        else {
                            D.ebug(D.EBUG_SPEW, "Eclip:editTargetOnly: ** DOWNLINK IS NULL");
                        }
                    }
                }
                else {

                    // We should be dealing w/ a relator .. so lets take a risk
                    // Not so fast there babaloui
                    EntityItem eiRelator = new EntityItem(null, _ai.getProfile(), _aei[ii]);
                    EntityItem eiE1 = (EntityItem) _aei[ii].getUpLink(0);
                    EntityItem eiE2 = (EntityItem) _aei[ii].getDownLink(0);
                    // If everything is fine.. we have a good relator
                    if (eiE1 != null && eiE2 != null && eiRelator != null) {
                        if (egParent == null) {
                            egParent = eiE1.getEntityGroup();
                        }
                        if (egRelator == null) {
                            egRelator = _aei[ii].getEntityGroup();
                        }

                        if (elHoldE1.containsKey(eiE1.getKey())) {
                            eiE1 = (EntityItem) elHoldE1.get(eiE1.getKey());
                        }
                        else {
                            eiE1 = new EntityItem(null, _ai.getProfile(), eiE1);
                            elHoldE1.put(eiE1);
                        }

                        if (elHoldE2.containsKey(eiE2.getKey())) {
                            eiE2 = (EntityItem) elHoldE2.get(eiE2.getKey());
                        }
                        else {
                            eiE2 = new EntityItem(null, _ai.getProfile(), eiE2);
                            elHoldE2.put(eiE2);
                        }

                        eiRelator.putUpLink(eiE1);
                        eiRelator.putDownLink(eiE2);
                        el.put(eiRelator);

                    }
                    else {
                        // We must be working w/ an Entity2Type misfit..
                        // Lets change the action item on the fly
                        D.ebug(D.EBUG_SPEW, "***EntityList.getEntityList(EditActionItem): no Relator found");
                    }
                }
            }
            catch (Exception x) {
                x.printStackTrace();
            }
        }

        //
        // O.K.  Add any remaining Parents w/ Fictitous relators...
        //IN2992100 this causes performance and outofmemory issues for something like edit prodstruct and it is
        // on a model with 5000 features
        /*
        if (!_ai.isEntityOnly() && egParent != null) {
            for (int i = 0; i < egParent.getEntityItemCount(); i++) {
                EntityItem eiParent = egParent.getEntityItem(i);
                if (!elHoldE1.containsKey(eiParent.getKey())) {
                    try {
                        EntityItem eiRelator = new EntityItem(null, _ai.getProfile(), egRelator.getEntityType(), _ai.getID());
                        eiParent = new EntityItem(null, _ai.getProfile(), eiParent);
                        eiRelator.putUpLink(eiParent);
                        el.put(eiRelator);
                    }
                    catch (Exception x) {
                        x.printStackTrace();
                    }
                }
            }
        }*/

        aei = new EntityItem[el.size()];
        el.copyTo(aei);
        
        // release memory
        el.clear();
        elHoldE1.clear();
        elHoldE2.clear();
        el = null;
        elHoldE1 = null;
        elHoldE2 = null;
        
        return aei;
    }
 
    /*
     *  Shift right for the CopyActionItem type transactions
     */
    /**
     *  Description of the Method
     *
     * @param  _ai   Description of the Parameter
     * @param  _aei  Description of the Parameter
     * @return       Description of the Return Value
     */
    private static EntityItem[] clip(CopyActionItem _ai, EntityItem[] _aei) {

        EntityItem[] aei = null;
        EANList el = new EANList(EANMetaEntity.LIST_SIZE);

        for (int ii = 0; ii < _aei.length; ii++) {
            try {

                // We may have to shift right to find the proper entitytype
                if (_ai.getTargetEntity().equals(_aei[ii].getEntityType())) {
                    el.put(new EntityItem(null, _ai.getProfile(), _aei[ii]));
                }
                else {
                    EntityItem ei = (EntityItem) _aei[ii].getDownLink(0);
                    if (ei != null) {
                        if (_ai.getTargetEntity().equals(ei.getEntityType())) {
                            D.ebug(D.EBUG_SPEW, "Eclip:editTargetOnly: ** Shift and found");
                            el.put(new EntityItem(null, _ai.getProfile(), ei));
                        }
                        else {
                            D.ebug(D.EBUG_SPEW, "Eclip:editTargetOnly: ** Cannot find a thing");
                        }
                    }
                    else {
                        D.ebug(D.EBUG_SPEW, "Eclip:editTargetOnly: ** DOWNLINK IS NULL");
                    }
                }
            }
            catch (Exception x) {
                x.printStackTrace();
            }
        }

        aei = new EntityItem[el.size()];
        el.copyTo(aei);
        
        el.clear(); // release memory
        el = null;
        return aei;
    }

    private static EntityItem[] clip(ABRStatusActionItem _ai, EntityItem[] _aei) {

        EntityItem[] aei = null;

        EANList el = new EANList(EANMetaEntity.LIST_SIZE);

        for (int ii = 0; ii < _aei.length; ii++) {
            try {

                // We may have to shift right to find the proper entitytype
                if (_ai.getTargetEntity().equals(_aei[ii].getEntityType())) {
                    el.put(new EntityItem(null, _ai.getProfile(), _aei[ii]));
                }
                else {
                    EntityItem ei = (EntityItem) _aei[ii].getDownLink(0);
                    if (ei != null) {
                        if (_ai.getTargetEntity().equals(ei.getEntityType())) {
                            D.ebug(D.EBUG_SPEW, "Eclip:editTargetOnly: ** Shift and found");
                            el.put(new EntityItem(null, _ai.getProfile(), ei));
                        }
                        else {
                            D.ebug(D.EBUG_SPEW, "Eclip:editTargetOnly: ** Cannot find a thing");
                        }
                    }
                    else {
                        D.ebug(D.EBUG_SPEW, "Eclip:editTargetOnly: ** DOWNLINK IS NULL");
                    }
                }
            }
            catch (Exception x) {
                x.printStackTrace();
            }
        }

        aei = new EntityItem[el.size()];
        el.copyTo(aei);
        // release memory
        el.clear();
        el=null;
        return aei;
    }

    /*
     *  Shift right and clip for Report Action Items
     */
    /**
     *  Description of the Method
     *
     * @param  _ai   Description of the Parameter
     * @param  _aei  Description of the Parameter
     * @return       Description of the Return Value
     */
    public static EntityItem[] clipShift(ReportActionItem _ai, EntityItem[] _aei) {

        EntityItem[] aei = null;

        EANList elHold = new EANList();

        EANFoundation fn = _ai.getParent();
        if (fn instanceof ActionGroup) {
            ActionGroup ag = (ActionGroup) _ai.getParent();
            if (ag != null) {
                String strEntityType = ag.getEntityType();
                for (int ii = 0; ii < _aei.length; ii++) {
                    // Lets see if we have to downlink
                    if (! (_aei[ii].getEntityType().equals(strEntityType))) {
                        boolean bFound = false;
                        for (int ij = 0; ij < _aei[ii].getDownLinkCount(); ij++) {
                            EntityItem ent = (EntityItem) _aei[ii].getDownLink(ij);
                            if (ent.getEntityType().equals(strEntityType)) {
                                try {
                                    EntityItem ei = new EntityItem(ent);
                                    elHold.put(ei);
                                    ei.resetAttribute();
                                    bFound = true;
                                    break;
                                }
                                catch (Exception x) {
                                    x.printStackTrace();
                                }
                            }
                        }
                        if (!bFound) {
                            D.ebug(D.EBUG_SPEW, "Error: cannot find home for:" + _aei[ii].toString() + "->DownLink:" +
                                   (_aei[ii].getDownLink(0) == null ? "NONE" : _aei[0].getDownLink(0).getEntityType()));
                        }
                    }
                    else {
                        try {
                            EntityItem ei = new EntityItem(_aei[ii]);
                            elHold.put(ei);
                            ei.resetAttribute();
                        }
                        catch (Exception x) {
                            x.printStackTrace();
                        }
                    }
                }
            }
        }

        aei = new EntityItem[elHold.size()];
        elHold.copyTo(aei);
        return aei;
    }

    /**
     *  This plays tricks with the information to ensure that we generate the right set of parents based upon the NavActionItem.
     **/
    private static EntityList getEntityList(RemoteDatabaseInterface _rdi, Database _db, Profile _prof, NavActionItem _ai,
                                            EntityItem[] _aei, boolean _bActionTree) throws MiddlewareRequestException,
        MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, SQLException {

		EntityList.checkDomain(_prof,_ai,_aei); //RQ0713072645

        NavActionItem nai = null;

        EANFoundation fn = _ai.getParent();
        if (fn instanceof ActionGroup) {
            ActionGroup ag = (ActionGroup) _ai.getParent();
            if (ag != null) {
                String strEntityType = ag.getEntityType();
                if (_aei != null) {
                    for (int ii = 0; ii < _aei.length; ii++) {
                        if (! (_aei[ii].getEntityType().equals(strEntityType))) {
                            boolean bFound = false;
                            for (int ij = 0; ij < _aei[ii].getDownLinkCount(); ij++) {
                                EntityItem ent = (EntityItem) _aei[ii].getDownLink(ij);
                                if (ent.getEntityType().equals(strEntityType)) {
                                    _aei[ii] = ent;
                                    bFound = true;
                                    break;
                                }
                            }
                            if (!bFound) {
                                D.ebug(D.EBUG_SPEW, "Error: cannot find home for:" + _aei[0].getKey() + "->DownLink:" +
                                       (_aei[0].getDownLink(0) == null ? "NONE" : _aei[0].getDownLink(0).getEntityType()));
                            }
                        }
                    }
                }
                else {
                    D.ebug("EntityList.getEntityList.NavActionIsHomeEnabledIsTrue");
                }
            }
        }

        // Now clip the wings and reset the attributes.. we only need stub information
        if (_aei != null) {
            for (int ii = 0; ii < _aei.length; ii++) {
                _aei[ii] = new EntityItem(null, _prof, _aei[ii].getEntityType(), _aei[ii].getEntityID());
            }
        }

        // GAB 6/3/3 : THIS IS ESSENTIAL!!!
        // What this does is set ~passed~ NavActionItem's EntityItem[] reference!
        // Remember that this is ~NOT~ the same as setting this in the constructor!
        _ai.setEntityItems(_aei);

        nai = new NavActionItem(_ai);

        if (_db == null) {
            return _rdi.getEntityList(_prof, nai, _aei, _bActionTree);
        }
        else {
            return _db.getEntityList(_prof, nai, _aei, _bActionTree);
        }
    }

    /*
     *  This plays tricks with the information to ensure that we generate the right set of parents based upon the NavActionItem.
     */
    /**
     *  Gets the entityList attribute of the EntityList class
     *
     * @param  _rdi                                       Description of the Parameter
     * @param  _db                                        Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @param  _ai                                        Description of the Parameter
     * @param  _aei                                       Description of the Parameter
     * @return                                            The entityList value
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  RemoteException                        Description of the Exception
     * @exception  SQLException                           Description of the Exception
     */
    private static EntityList getEntityList(RemoteDatabaseInterface _rdi, Database _db, Profile _prof, ExtractActionItem _ai,
                                            EntityItem[] _aei) throws MiddlewareRequestException, MiddlewareException,
        MiddlewareShutdownInProgressException, RemoteException, SQLException {

		EntityList.checkDomain(_prof,_ai,_aei); //RQ0713072645

        EANFoundation fn = _ai.getParent();
        if (fn instanceof ActionGroup) {
            ActionGroup ag = (ActionGroup) _ai.getParent();
            if (ag != null) {
                String strEntityType = ag.getEntityType();
                for (int ii = 0; ii < _aei.length; ii++) {
                    if (! (_aei[ii].getEntityType().equals(strEntityType))) {
                        boolean bFound = false;
                        for (int ij = 0; ij < _aei[ii].getDownLinkCount(); ij++) {
                            EntityItem ent = (EntityItem) _aei[ii].getDownLink(ij);
                            if (ent.getEntityType().equals(strEntityType)) {
                                _aei[ii] = ent;
                                bFound = true;
                                break;
                            }
                        }
                        if (!bFound) {
                            D.ebug(D.EBUG_SPEW, "Error: cannot find home for:" + _aei[0].getKey() + "->DownLink:" +
                                   (_aei[0].getDownLink(0) == null ? "NONE" : _aei[0].getDownLink(0).getEntityType()));
                        }
                    }
                }
            }
        }

        // Now clip the wings and reset the attributes.. we only need stub information
        for (int ii = 0; ii < _aei.length; ii++) {
            //_aei[ii].resetAttribute();
            _aei[ii] = new EntityItem(null, _prof, _aei[ii].getEntityType(), _aei[ii].getEntityID());
        }

        if (_db == null) {
            return _rdi.getEntityList(_prof, new ExtractActionItem(_ai), _aei);
        }
        else {
            return _db.getEntityList(_prof, new ExtractActionItem(_ai), _aei);
        }
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
    protected void enforceWorkGroupDomain(Database _db, Profile _prof) throws SQLException, MiddlewareRequestException,
        MiddlewareException {
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
		if (getParentActionItem() instanceof SearchActionItem){
			sai = (SearchActionItem)getParentActionItem();
		}

        try {
            rs = _db.callGBL7065(returnStatus, strEnterprise, iWGID, strValOn, strEffOn);
            rdrs = new ReturnDataResultSet(rs);
        }
        finally {
			if (rs!=null){
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

            _db.debug(D.EBUG_SPEW, "EntityList.enforcedomain.gbl7065:answers:" + strAttributeCode + ":" + strAttributeValue);
			//RQ0713072645
			if (sai != null){
				if (strAttributeCode.equals(sai.getWGDomainAttrCode())&&
					!strAttributeCode.equals(sai.getDomainAttrCode())) {
					// use values from WG's attrcode, but use domainattrcode in entity search
					strAttributeCode = sai.getDomainAttrCode();
					D.ebug(D.EBUG_SPEW,
						  "gbl7065:answers override:" + sai.getWGDomainAttrCode()+" to "+ strAttributeCode);
				}
			} // end RQ0713072645

            // Tuck it away
            eltmp2.put(new Implicator(null, getProfile(), strAttributeCode + strAttributeValue));

            for (int iy = 0; iy < getEntityGroupCount(); iy++) {
                EntityGroup eg = getEntityGroup(iy);
                String strEntityType = eg.getEntityType();
                EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) eg.getMetaAttribute(strAttributeCode);
                if (mfa != null && (!eltmp1.containsKey(strEntityType + strAttributeCode))) {
                    eltmp1.put(new Implicator(mfa, null, strEntityType + strAttributeCode));
                }
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
		if (getParentActionItem() instanceof SearchActionItem){
			sai = (SearchActionItem)getParentActionItem();
		}

        for (int ii = 0; ii < rdrs.size(); ii++) {
            String strAttributeCode = rdrs.getColumn(ii, 0);
            // This is the AttributeCode
            String strAttributeValue = rdrs.getColumn(ii, 1);
            // This is the AttributeCode

            D.ebug(D.EBUG_SPEW, "EntityList.rmi.enforcedomain.gbl7065:answers:" + strAttributeCode + ":" + strAttributeValue);
			//RQ0713072645
			if (sai != null){
				if (strAttributeCode.equals(sai.getWGDomainAttrCode())&&
					!strAttributeCode.equals(sai.getDomainAttrCode())) {
					// use values from WG's attrcode, but use domainattrcode in entity search
					strAttributeCode = sai.getDomainAttrCode();
					D.ebug(D.EBUG_SPEW,
						  "gbl7065:answers override:" + sai.getWGDomainAttrCode()+" to "+ strAttributeCode);
				}
			} // end RQ0713072645

            // Tuck it away
            eltmp2.put(new Implicator(null, getProfile(), strAttributeCode + strAttributeValue));

            for (int iy = 0; iy < getEntityGroupCount(); iy++) {
                EntityGroup eg = getEntityGroup(iy);
                String strEntityType = eg.getEntityType();
                EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) eg.getMetaAttribute(strAttributeCode);
                if (mfa != null && (!eltmp1.containsKey(strEntityType + strAttributeCode))) {
                    eltmp1.put(new Implicator(mfa, null, strEntityType + strAttributeCode));
                }
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
    public void commit(Database _db, RemoteDatabaseInterface _rdi) throws EANBusinessRuleException, MiddlewareBusinessRuleException,
        RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, SQLException {
    }

    /**
     *  Description of the Method
     */
    protected void checkDataIntegrity() {

        int ihc = hashCode();

        for (int ii = 0; ii < getEntityGroupCount(); ii++) {
            EntityGroup eg = getEntityGroup(ii);
            EANFoundation eanf = eg.getParent();
            if (ihc != eanf.hashCode()) {
                D.ebug(D.EBUG_SPEW, "EL PC Integrity problem:" + getKey() + ":" + ihc + " != " + eanf.getKey() + ":" + eanf.hashCode());
                eg.setParent(this);
            }
            eg.checkDataIntegrity();
        }
    }

    /*
     *  This ensures that all entities have been tagged properly for Restrictions prior to
     */
    /**
     *  Description of the Method
     */
    protected void refreshRRR() {
        for (int ii = 0; ii < getEntityGroupCount(); ii++) {
            EntityGroup eg = getEntityGroup(ii);
            for (int iy = 0; iy < eg.getEntityItemCount(); iy++) {
                EntityItem ei = eg.getEntityItem(iy);
                ei.refreshRestrictions();
                ei.refreshRequired();
                ei.refreshResets();
                ei.refreshClassifications();
            }
        }
    }

    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public boolean canEdit() {
        return false;
    }

    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public boolean canCreate() {
        return false;
    }

    /**
     *  Adds a feature to the Row attribute of the EntityList object
     *
     * @return    Description of the Return Value
     */
    public boolean addRow() {
        return false;
    }

    /**
     *  Adds a feature to the Row attribute of the EntityList object
     *
     * @return    Description of the Return Value
     */
    public boolean addRow(String _sKey) {
        return false;
    }

    /**
     *  Description of the Method
     *
     * @param  _strKey  Description of the Parameter
     */
    public void removeRow(String _strKey) {
    }

    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public boolean hasChanges() {
        return false;
    }

    /**
     *  Gets the cachedEntityGroupList attribute of the EntityList object
     *
     * @param  _db  Description of the Parameter
     * @param  _ai  Description of the Parameter
     * @return      The cachedEntityGroupList value
     */
    private EANList getCachedEntityGroupList(Database _db, EANActionItem _ai, int _iNLSID,java.util.Hashtable memTbl) {

        byte[] ba = null;
        ByteArrayInputStream baisObject = null;
        BufferedInputStream bis = null;
        ObjectInputStream oisObject = null;
        EANList el = null;

        String strKey = "EL" + getProfile().getRoleCode() + _ai.getKey();
        try {

            // go get the blob.. It is NLS Independent right now
            Blob b = _db.getBlobNow(getProfile(), getKey(), 0, strKey, _iNLSID);
            _db.commit();
            _db.freeStatement();
            _db.isPending();
            ba = b.getBAAttributeValue();
            if (ba == null || ba.length==0) {
                _db.debug(D.EBUG_SPEW, "getCachedEntityGroupList:  ** No Cache Object Found for "+strKey+" **");
                return null;
            }

            Vector entityTypeVct = null;
            try {
                baisObject = new ByteArrayInputStream(ba);
                bis = new BufferedInputStream(baisObject, 1000000);
                oisObject = new ObjectInputStream(bis);
                Object obj = oisObject.readObject();
                if(obj instanceof EANList){
                	el = (EANList) obj;
                }else if(obj instanceof Vector){
                	entityTypeVct = (Vector)obj;
                }
            }
            finally {
             	if(oisObject!=null){
            		try{
            			oisObject.close();
            		} catch (java.io.IOException x) {
            			_db.debug(D.EBUG_DETAIL, "getCachedEntityGroupList: "+strKey+" ERROR failure closing ObjectInputStream "+x);
            		} 
            		oisObject=null;
            	}

            	if(bis!=null){
            		try{
            			bis.close();
            		} catch (java.io.IOException x) {
            			_db.debug(D.EBUG_DETAIL, "getCachedEntityGroupList: "+strKey+" ERROR failure closing BufferedInputStream "+x);
            		} 
            		bis=null;
            	}
            	
            	if(baisObject!=null){
            		try{
            			baisObject.close();
            		} catch (java.io.IOException x) {
            			_db.debug(D.EBUG_DETAIL, "getCachedEntityGroupList: "+strKey+" ERROR failure closing ByteArrayInputStream "+x);
            		} 
            		baisObject=null;
            	}

                ba = null;
            }

            if(el==null && entityTypeVct !=null){
            	el= new EANList();

            	//RCQ00210066-WI cant hide the entitygroups in this list any more, get them now
            	for (int i = 0; i < entityTypeVct.size(); i++) {
            		String strEntityType = EANUtility.reuseObjects(memTbl,entityTypeVct.elementAt(i).toString());
            		_db.debug(D.EBUG_DETAIL, "getCachedEntityGroupList: "+strKey+" loading "+strEntityType);
            		EntityGroup eg = getEntityGroup(strEntityType);
            		if (eg == null) {	
            			String purpose = _ai.getPurpose();
            			if(_ai.isVEEdit()){
            				purpose="Edit"; // must get all meta - transitions RCQ 222905 BH Catalog overrides
            			}
            			eg = new EntityGroup(this, _db, null, strEntityType, purpose,memTbl);
            		}
            		el.put(eg);
            	}
            	entityTypeVct.clear();
            	entityTypeVct=null;
            }
            
            return el;
        } catch (Exception x) {
            _db.debug(D.EBUG_ERR, "getCachedEntityGroupList: "+strKey+" ERROR "+x.getMessage());
            x.printStackTrace();
        }
        finally {
            _db.freeStatement();
            _db.isPending();
        }

        _db.debug(D.EBUG_SPEW, "getCachedEntityGroupList: "+strKey+" complete");
        return null;
    }

    /**
     *  Description of the Method
     *
     * @param  _db  Description of the Parameter
     * @param  _ai  Description of the Parameter
     */
    private void putCachedEntityGroupList(Database _db, EANActionItem _ai, int _iNLSID) {
        String strKey = "EL" + getProfile().getRoleCode() + _ai.getKey();

        byte[] ba = null;
        ByteArrayOutputStream baosObject = null;
        ObjectOutputStream oosObject = null;

        boolean success = false;
        try {

            DatePackage dp = _db.getDates();
           
            baosObject = new ByteArrayOutputStream();
            oosObject = new ObjectOutputStream(baosObject);

            EANList el = getEntityGroup();
            Vector entityTypeVct = new Vector(el.size());

            //RCQ00210066-WI cant hide the entitygroups in this list any more, get them now
            for (int i = 0; i < el.size(); i++) {
            	EntityGroup eg = getEntityGroup(i);
            	entityTypeVct.add(eg.getEntityType());
            	_db.debug(D.EBUG_DETAIL, "putCachedEntityGroupList: "+strKey+" caching "+eg.getEntityType());
            }

            //oosObject.writeObject(getEntityGroup());
            oosObject.writeObject(entityTypeVct);
            oosObject.flush();
            oosObject.reset();
            ba = new byte[baosObject.size()];
            ba = baosObject.toByteArray();
            _db.callGBL9974(new ReturnStatus( -1), _db.getInstanceName(), "pcegl");
            _db.freeStatement();
            _db.isPending();
            _db.putBlob(getProfile(), getKey(), 0, strKey, "CACHE", dp.getNow(), dp.getForever(), ba, _iNLSID);
            success = true;

        }
        catch (Exception x) {
            _db.debug(D.EBUG_ERR, "putCachedEntityGroupList: "+strKey+" ERROR "+x.getMessage());
        }
        finally {
        	try{
        		if(success){
        			_db.commit();
        		}else{
        			_db.rollback();
        		}
        	}catch(Exception e) {}

        	if(oosObject!=null){
        		try{
        			oosObject.close();
        		} catch (java.io.IOException x) {
        			_db.debug(D.EBUG_DETAIL, "putCachedEntityGroupList: "+strKey+" ERROR failure closing ObjectOutputStream "+x);
        		} 
        		oosObject=null;
        	}
        	if(baosObject!=null){
        		try{
        			baosObject.close();
        		} catch (java.io.IOException x) {
        			_db.debug(D.EBUG_DETAIL, "putCachedEntityGroupList: "+strKey+" ERROR failure closing ByteArrayOutputStream "+x);
        		} 
        		baosObject=null;
        	}
        	_db.freeStatement();
        	_db.isPending();
        }
    }

    /**
     *  Description of the Method
     *
     * @param  _eg  Description of the Parameter
     */
    public void putCartEntityGroup(EntityGroup _eg) {
        putEntityGroup(_eg);
    }

    /**
     * look for a match on the action and match of all entityitems
     *
     * @param  _ai   Description of the Parameter
     * @param  _aei  Description of the Parameter
     * @return       Description of the Return Value
     */
    public boolean equivalent(EntityItem[] _aei, EANActionItem _ai) {

        EANActionItem ai = null;
        String strActionItemKey = null;

        if (_aei == null) {
            return false;
        }
        if (_aei.length <= 0) {
            return false;
        }
        if (_ai == null) {
            return false;
        }

        ai = getParentActionItem();
        if (ai == null) {
            return false;
        }

        strActionItemKey = ai.getActionItemKey();
        if (!_ai.getActionItemKey().equals(strActionItemKey)) {
            return false;
        }

        // Here .. we get the action item
        // if it is edit.. then we want to see if all the
        // entities in the Array.. are present in the
        // EntityList.
        if (_ai instanceof EditActionItem) {

            // may do something with these ..
            // so lets get them

            boolean bdown = false;
            EntityGroup eg = getEntityGroup(_aei[0].getEntityType());

            // If we cannot find an entitygroup directly.. we need
            // to look downward from what was passed
            if (eg == null) {
                EntityItem eic = null;
                if (_aei[0].getDownLink(0) == null) {
                    return false;
                }
                eic = (EntityItem) _aei[0].getDownLink(0);
                eg = getEntityGroup(eic.getEntityType());
                bdown = true;
                if (eg == null) {
                    return false;
                }
            }

            // If we made it this far.. we found an entitygroup that has
            // either a direct reference. or a dowlink reference
            for (int y = 0; y < _aei.length; y++) {
                if (eg.getEntityItem( (bdown ? _aei[y].getDownLink(0).getKey() : _aei[y].getKey())) == null) {
                    return false;
                }
            }

            return true;
        }

        // all others.. right now .. we return false.
        // do not know what this will do for ExtractActionItem..
        // but for CreateActionItem.. we always want to return
        // false
        return false;
    }
    /**
     * look for a match on the action and any match of entityitem
     * @param _eia
     * @param _ai
     * @return
     */
	public boolean subset(EntityItem[] _aei, EANActionItem _ai) {
       	boolean anymatch = false;

        if (_aei == null || _aei.length == 0) {
            return anymatch;
        }
  
        if (_ai == null || getParentActionItem()==null) {
            return anymatch;
        }

        if (!_ai.getActionItemKey().equals(getParentActionItem().getActionItemKey())) {
            return anymatch;
        }

        // Here .. we get the action item
        // if it is edit.. then we want to see if all the
        // entities in the Array.. are present in the
        // EntityList.
        if (_ai instanceof EditActionItem) {

            // may do something with these ..
            // so lets get them

            boolean bdown = false;
            EntityGroup eg = getEntityGroup(_aei[0].getEntityType());

            // If we cannot find an entitygroup directly.. we need
            // to look downward from what was passed
            if (eg == null) {
                EntityItem eic = null;
                if (_aei[0].getDownLink(0) == null) {
                    return anymatch;
                }
                eic = (EntityItem) _aei[0].getDownLink(0);
                eg = getEntityGroup(eic.getEntityType());
                bdown = true;
                if (eg == null) {
                    return anymatch;
                }
            }

            // If we made it this far.. we found an entitygroup that has
            // either a direct reference. or a dowlink reference
            for (int y = 0; y < _aei.length; y++) {
                if (eg.getEntityItem( (bdown ? _aei[y].getDownLink(0).getKey() : _aei[y].getKey())) != null) {
                	anymatch = true;
                    break;
                }
            }
        } // end editaction

        // all others.. right now .. we return false.
        // do not know what this will do for ExtractActionItem..
        // but for CreateActionItem.. we always want to return
        // false
        return anymatch;
    }

    /**
     *  Gets the matrixValue attribute of the EntityList object
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
     *  Gets the matrixEditable attribute of the EntityList object
     *
     * @param  _str  Description of the Parameter
     * @return       The matrixEditable value
     */
    public boolean isMatrixEditable(String _str) {
        return false;
    }

    /**
     *  Description of the Method
     */
    public void rollbackMatrix() {
    }

    /**
     *  Adds a feature to the Column attribute of the EntityList object
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
    public boolean removeLink(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey) throws
        EANBusinessRuleException {
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
    public EANFoundation[] link(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strRowKey,
                                EntityItem[] _aeiChild, String _strLinkOption) throws EANBusinessRuleException {
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
    public EntityList edit(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String[] _astrKey) throws SQLException,
        MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, RemoteException {
        return null;
    }

    /**
     *  Gets the whereUsedList attribute of the EntityList object
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
     *  Gets the actionItemsAsArray attribute of the EntityList object
     *
     * @param  _strKey  Description of the Parameter
     * @return          The actionItemsAsArray value
     * @param _db
     * @param _rdi
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public Object[] getActionItemsAsArray(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, String _strKey) throws
        SQLException, MiddlewareException, MiddlewareRequestException {
        if (m_aiParent instanceof EditActionItem) {
            EditActionItem eai = (EditActionItem) m_aiParent;
            return eai.getActionItemArray();
        }
        else {
            return null;
        }
    }

    /**
     *  Gets the dynaTable attribute of the EntityList object
     *
     * @return    The dynaTable value
     */
    public boolean isDynaTable() {
        return false;
    }

    /**
     *  Description of the Method
     *
     * @param  _ei            Description of the Parameter
     * @param  _iLevel        Description of the Parameter
     * @param  _eiParent      Description of the Parameter
     * @param  _hshHistory    Description of the Parameter
     * @param  _iDepth        Description of the Parameter
     * @param  _strDirection  Description of the Parameter
     * @param  _hshMap        Description of the Parameter
     * @return                Description of the Return Value
     */
    public static StringBuffer pull(EntityItem _ei, int _iLevel, EntityItem _eiParent, Hashtable _hshHistory, int _iDepth,
                                    String _strDirection, Hashtable _hshMap) {

        StringBuffer strbResult = new StringBuffer();
        EntityGroup eg = (EntityGroup) _ei.getParent();

        strbResult.append(NEW_LINE + "" + _iDepth + ":" + _iLevel + ":" + (eg.isRelator() ? "R" : (eg.isAssoc() ? "A" : "E")) + ":" +
                          _ei.getEntityType() + ":" + _ei.getEntityID() + ":" + _eiParent.getEntityType() + ":" +
                          _eiParent.getEntityID() + ":" + _strDirection);

        if (_hshHistory.containsKey(_ei.getKey() + _iLevel)) {
            // Do nothing
        }
        else {

            _hshHistory.put(_ei.getKey() + _iLevel, "hi");
            if (_ei.getLevel(_iLevel + 1)) {
                for (int ii = 0; ii < _ei.getUpLinkCount(); ii++) {
                    EntityItem eiUp = (EntityItem) _ei.getUpLink(ii);
                    if (eiUp.getLevel(_iLevel + 1) && _hshMap.containsKey( (_iLevel + 1) + eiUp.getEntityType() + "U") &&
                        !eiUp.getKey().equals(_eiParent.getKey())) {
                        strbResult.append(pull(eiUp, _iLevel + 1, _ei, _hshHistory, _iDepth + 1, "U", _hshMap).toString());
                    }
                }
                for (int ii = 0; ii < _ei.getDownLinkCount(); ii++) {
                    EntityItem eiDown = (EntityItem) _ei.getDownLink(ii);
                    if (eiDown.getLevel(_iLevel + 1) && _hshMap.containsKey( (_iLevel + 1) + eiDown.getEntityType() + "D") &&
                        !eiDown.getKey().equals(_eiParent.getKey())) {
                        strbResult.append(pull(eiDown, _iLevel + 1, _ei, _hshHistory, _iDepth + 1, "D", _hshMap).toString());
                    }
                }
            }
            for (int ii = 0; ii < _ei.getUpLinkCount(); ii++) {
                EntityItem eiUp = (EntityItem) _ei.getUpLink(ii);
                if (eiUp.getLevel(_iLevel) && _hshMap.containsKey(_iLevel + eiUp.getEntityType() + "U") &&
                    !eiUp.getKey().equals(_eiParent.getKey())) {
                    strbResult.append(pull(eiUp, _iLevel, _ei, _hshHistory, _iDepth + 1, "U", _hshMap).toString());
                }
            }
            for (int ii = 0; ii < _ei.getDownLinkCount(); ii++) {
                EntityItem eiDown = (EntityItem) _ei.getDownLink(ii);
                if (eiDown.getLevel(_iLevel) && _hshMap.containsKey(_iLevel + eiDown.getEntityType() + "D") &&
                    !eiDown.getKey().equals(_eiParent.getKey())) {
                    strbResult.append(pull(eiDown, _iLevel, _ei, _hshHistory, _iDepth + 1, "D", _hshMap).toString());
                }
            }
        }

        return strbResult;
    }

    /**
     *  Description of the Method
     *
     * @param  _sb  Description of the Parameter
     * @return      Description of the Return Value
     */
    public static StringBuffer genEntityTree(StringBuffer _sb) {

        StringBuffer strbResult = new StringBuffer();
        StringTokenizer st1 = new StringTokenizer(_sb.toString(), NEW_LINE + "");
        while (st1.hasMoreTokens()) {
            StringTokenizer st2 = new StringTokenizer(st1.nextToken(), ":");
            if (st2.hasMoreTokens()) {
                int iDepth = Integer.parseInt(st2.nextToken());
                int iLevel = Integer.parseInt(st2.nextToken());
                String strClass = st2.nextToken();
                String strET = st2.nextToken();
                int iEID = Integer.parseInt(st2.nextToken());
                String strPET = st2.nextToken();
                int iPEID = Integer.parseInt(st2.nextToken());
                String strDir = st2.nextToken();

                strbResult.append(NEW_LINE + "" + iDepth + ":");
                for (int ii = 0; ii <= iDepth; ii++) {
                    strbResult.append("-");
                }
                strbResult.append("(" + strET + ":" + iEID + ") <-- (" + strPET + ":" + iPEID + ")\t" + strClass + ":" + iLevel +
                                  ":" + strDir);
            }
        }

        return strbResult;
    }

    /**
     *  Sets the tagInfo attribute of the EntityList object
     *
     * @param  _db                             The new tagInfo value
     * @param  _prof                           The new tagInfo value
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public void setTagInfo(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException {

        // O.K. we call an SP and make a group.. and EntityItem if found..
        ReturnStatus returnStatus = new ReturnStatus();
        ReturnDataResultSet rdrs = null;
        ResultSet rs = null;

        String strEnterprise = _prof.getEnterprise();
        int iSessionID = _prof.getSessionID();
        int iOPWGID = _prof.getOPWGID();

        try {
            rs = _db.callGBL7546(returnStatus, strEnterprise, iSessionID, iOPWGID);
            rdrs = new ReturnDataResultSet(rs);
        }
        finally {
			if (rs!=null){
            	rs.close();
            	rs = null;
			}
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }

        for (int ii = 0; ii < rdrs.size(); ii++) {
            String strEntityType = rdrs.getColumn(ii, 0);
            int iEntityID = rdrs.getColumnInt(ii, 1);

            EntityGroup eg = new EntityGroup(this, _db, _prof, strEntityType, "Navigate");
            EntityItem ei = new EntityItem(eg, _prof, _db, strEntityType, iEntityID);
            _db.debug(D.EBUG_SPEW, "gbl7546:answer:" + strEntityType + ":" + iEntityID);
            _db.debug(D.EBUG_SPEW, "EntityList:getTagInfo:" + ei.toString());
            eg.putEntityItem(ei);

            // Set the member variables
            //m_egTag = eg;
            m_eiTag = ei;
        }

    }

    /**
     *  Gets the tagDisplay attribute of the EntityList object
     *
     * @return    The tagDisplay value
     */
    public String getTagDisplay() {

        if (m_eiTag == null) {
            return "N/A";
        }

        return m_eiTag.toString();
    }

    /**
     * setExtractSessionID
     *
     * @param _i
     *  @author David Bigelow
     */
    protected void setExtractSessionID(int _i) {
        m_iExtractSessionID = _i;
    }

    /**
     * getExtractSessionID
     *
     * @return
     *  @author David Bigelow
     */
    public int getExtractSessionID() {
        return m_iExtractSessionID;
    }

    /**
     * (non-Javadoc)
     * duplicate
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#duplicate(java.lang.String, int)
     */
    public boolean duplicate(String _strKey, int _iDup) {
        return false;
    }

    /**
     * (non-Javadoc)
     * linkAndRefresh
     *
     * @see COM.ibm.eannounce.objects.EANTableWrapper#linkAndRefresh(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.LinkActionItem)
     */
    public Object linkAndRefresh(Database _db, RemoteDatabaseInterface _rdi, Profile _prof, LinkActionItem _lai) throws
        SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException,
        WorkflowException, RemoteException {
        return null;
    }

    /*
     *  Used to isolate the pulling of attribute values for the information in the trsNavigate Table
     */
    /**
     *  Description of the Method
     *
     * @param  _db                      Description of the Parameter
     * @param  _strEnterprise           Description of the Parameter
     * @param  _iSessionID              Description of the Parameter
     * @param  _iNLSID                  Description of the Parameter
     * @param  _strValOn                Description of the Parameter
     * @param  _strEffOn                Description of the Parameter
     * @exception  SQLException         Description of the Exception
     * @exception  MiddlewareException  Description of the Exception
     */

    private void popABRStatusAttributeValues(Database _db, String _strEnterprise, int _iSessionID, int _iNLSID, String _strValOn,
                                             String _strEffOn) throws SQLException, MiddlewareException {

        ReturnStatus returnStatus = new ReturnStatus();
        ResultSet rs = null;

        EntityItem ei = null;
        EANMetaAttribute ma = null;
        TextAttribute ta = null;
        SingleFlagAttribute sfa = null;
        StatusAttribute sa = null;
        TaskAttribute tka = null;
        MultiFlagAttribute mfa = null;
        EANAttribute att = null;

        try {
            rs = _db.callGBL7009(returnStatus, _strEnterprise, _iSessionID, _iNLSID, _strValOn, _strEffOn);
            while (rs.next()) {

                String str1 = rs.getString(1).trim();
                int i1 = rs.getInt(2);
                String str2 = rs.getString(3).trim();
                int i2 = rs.getInt(4);
                String str3 = rs.getString(5).trim();

                EntityGroup eg = getEntityGroup(str1);

                _db.debug(D.EBUG_SPEW, "gbl7009:answers:" + str1 + ":" + i1 + ":" + str2 + ":" + i2 + ":" + str3);

                if (eg == null) {
                    continue;
                }
                // Lets get the ei in the group first
                ei = eg.getEntityItem(str1, i1);

                if (ei == null) {
                    _db.debug(D.EBUG_ERR, "** ABR Attribute Information for a non Existiant Entity Item:" + str1 + ":" + i1 + ":");
                    continue;
                }

                ma = eg.getMetaAttribute(str2);
                if (ma == null) {
                    _db.debug(D.EBUG_ERR, "** ABR Attribute Information for a non existient MetaAttribute:" + str2 + ":");
                    continue;
                }

                // Set up some Att objects
                att = ei.getAttribute(str2);
                if (att == null) {
                    if (ma instanceof MetaTextAttribute) {
                        ta = new TextAttribute(ei, null, (MetaTextAttribute) ma);
                        ei.putAttribute(ta);
                    }
                    else if (ma instanceof MetaSingleFlagAttribute) {
                        sfa = new SingleFlagAttribute(ei, null, (MetaSingleFlagAttribute) ma);
                        ei.putAttribute(sfa);
                    }
                    else if (ma instanceof MetaStatusAttribute) {
                        sa = new StatusAttribute(ei, getProfile(), (MetaStatusAttribute) ma);
                        ei.putAttribute(sa);
                    }
                    else if (ma instanceof MetaMultiFlagAttribute) {
                        mfa = new MultiFlagAttribute(ei, null, (MetaMultiFlagAttribute) ma);
                        ei.putAttribute(mfa);
                    }
                    else if (ma instanceof MetaTaskAttribute) {
                        tka = new TaskAttribute(ei, null, (MetaTaskAttribute) ma);
                        ei.putAttribute(tka);
                    }
                }
                else {
                    if (ma instanceof MetaTextAttribute) {
                        ta = (TextAttribute) att;
                    }
                    else if (ma instanceof MetaSingleFlagAttribute) {
                        sfa = (SingleFlagAttribute) att;
                    }
                    else if (ma instanceof MetaStatusAttribute) {
                        sa = (StatusAttribute) att;
                    }
                    else if (ma instanceof MetaTaskAttribute) {
                        tka = (TaskAttribute) att;
                    }
                    else if (ma instanceof MetaMultiFlagAttribute) {
                        mfa = (MultiFlagAttribute) att;
                    }
                }

                // OK.. drop the value into the structure
                if (ma instanceof MetaTextAttribute) {
                    ta.putPDHData(i2, str3);
                }
                else if (ma instanceof MetaTaskAttribute) {
                    tka.putPDHFlag(str3);
                }
                else if (ma instanceof MetaSingleFlagAttribute) {
                    sfa.putPDHFlag(str3);
                }
                else if (ma instanceof MetaStatusAttribute) {
                    sa.putPDHFlag(str3);
                }
                else if (ma instanceof MetaMultiFlagAttribute) {
                    mfa.putPDHFlag(str3);
                }
                else {
                    _db.debug(D.EBUG_ERR,
                              "WARN:EntityList.popABRStatusAttributeValues(). **Unknown Meta Type **" + str1 + ":" + i1 + ":" +
                              str2 + ":" + i2 + ":" + str3);
                }
            }

        }
        finally {
			if (rs != null){
            	rs.close();
            	rs = null;
			}
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }

        // GB - 04/28/04 - add in any derived attributes for this EntityGroup...
        for (int iD1 = 0; iD1 < this.getEntityGroupCount(); iD1++) {
            EntityGroup egD = this.getEntityGroup(iD1);
            for (int iD2 = 0; iD2 < egD.getEntityItemCount(); iD2++) {
                EntityItem eiD = egD.getEntityItem(iD2);
                ATTRIBUTE_LOOP:for (int iD3 = 0; iD3 < egD.getMetaAttributeCount(); iD3++) {
                    EANMetaAttribute emaD = egD.getMetaAttribute(iD3);
                    if (!emaD.isDerived()) {
                        continue ATTRIBUTE_LOOP;
                    }
                    if (emaD.getAttributeCode().equals("DERIVED_EID")) {
                        try {
                            TextAttribute taD = new TextAttribute(eiD, null, (MetaTextAttribute) emaD);
                            eiD.putAttribute(taD);
                            taD.put(":EID");
                        }
                        catch (EANBusinessRuleException eBRExc) {
                            _db.debug(D.EBUG_ERR, "WARN:EntityList.popNavAttributeValues():on put(:EID):" + eBRExc.getMessage());
                            continue ATTRIBUTE_LOOP;
                        }
                    }
                }
            }
        }
        // end GB - 04/28/04

    }

    /**
     * isParentDisplayable
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isParentDisplayable() {
        return m_bParentDisplayable;
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

    private String removeNonAlpha(String _strValue) {
        String strReturn = "";
        if (_strValue != null) {
            for (int i = 0; i < _strValue.length(); i++) {
                char c = _strValue.charAt(i);
                if (Character.isLetter(c) || Character.isSpaceChar(c)) {
                    strReturn = strReturn + c;
                }
            }
        }
        return strReturn;
    }

    private EntityItem setBluePageEntry(EntityItem _ei, BluePageEntry _bpe) {

        EANAttribute att = null;
        String strName = null;
        int iComma = -1;
        String strLN = null;
        String strFN = null;
        int i = -1;

        String[] aOPAttrs = {
            "BLUEPAGESFLAG", "BLUEPAGESUID", "CITY", "COUNTRY", "DIRECTORY", "EMAIL", "FAXNUMBER", "FIRSTNAME", "ID", "INDVTITLE",
            "JOBTITLE", "LASTNAME", "MIDDLENAME", "PDHDOMAIN", "SITE", "STATE", "STREETADDRESS", "STREETADDRESS2", "SUBSCRGROUP",
            "SUBSCRGROUPNOMAIL", "TELEPHONE", "TIELINE", "USERNAME", "USERTOKEN", "VNETNODE", "VNETUID", "ZIPCODE"};

        String[] aBluePage = {
            "199", "USERID", "LOCCITY", "COUNTRY", "DIRECTORY", "INTERNET", "FAX", "", "", "", "", "", "", "", "", "", "", "", "",
            "", "XPHONE", "TIE", "USERID", "INTERNET", "NODE", "", ""};

        if (!_ei.getEntityType().equals("OP")) {
            return _ei;
        }

        for (int ii = 0; ii < aOPAttrs.length; ii++) {
            String strAttrCode = aOPAttrs[ii];
            String strBPKey = aBluePage[ii];
            D.ebug(D.EBUG_SPEW, "setBluePage strCode: " + strAttrCode + ", strBPKey: " + strBPKey);
            if (strBPKey == null || strBPKey.length() <= 0) {
                continue;
            }
            D.ebug(D.EBUG_SPEW, "BluePage input: " + _bpe.getProperty(strBPKey));

            att = _ei.getAttribute(strAttrCode);
            if (att != null) {
                try {
                    if (att instanceof EANTextAttribute) {
                        EANTextAttribute ta = (EANTextAttribute) att;
                        ta.put(checkMaxLength(att, _bpe.getProperty(strBPKey)));
                    }
                    else if (att instanceof EANFlagAttribute) {
                        EANFlagAttribute fa = (EANFlagAttribute) att;
                        fa.put(strBPKey, true);
                    }
                }
                catch (EANBusinessRuleException _bre) {
                    _bre.printStackTrace();
                }
            }
        }

        // set FIRSTNAME,LASTNAME, need to do something special here
        // LastName, LastName ....
        strName = _bpe.getName();
        iComma = strName.indexOf(',');
        strLN = (iComma > 0 ? strName.substring(0, iComma) : strName);
        strFN = removeNonAlpha(strName.substring(iComma + 1));
        i = strFN.indexOf("CONTRACTOR");
        if (i >= 0) {
            strFN = strFN.substring(0, i);
        }
        try {
            _ei.put(_ei.getEntityType() + ":FIRSTNAME", strFN);
        }
        catch (EANBusinessRuleException _bre) {
            _bre.printStackTrace();
        }

        try {
            _ei.put(_ei.getEntityType() + ":LASTNAME", strLN);
        }
        catch (EANBusinessRuleException _bre) {
            _bre.printStackTrace();
        }

        return _ei;
    }

    /**
     * isParentActionUseRootEI
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isParentActionUseRootEI() {
        EANActionItem eai = getParentActionItem();
        return eai.useRootEI();
    }

    /**
     * getRootEI
     *
     * @return
     *  @author David Bigelow
     */
    public EntityItem[] getRootEI() {
        EntityGroup eg = getParentEntityGroup();
        if (eg != null) {
            return eg.getEntityItemsAsArray();
        }
        return null;
    }

    /**
     * isEditEntityOnly
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isEditEntityOnly() {
        EANActionItem ean = getParentActionItem();
        if (ean instanceof EditActionItem) {
            EditActionItem eai = (EditActionItem) ean;
            return eai.isEntityOnly();
        }
        return false;
    }

    /**
     * isCreateParent
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isCreateParent() {
        EANActionItem ean = getParentActionItem();
        if (ean instanceof CreateActionItem) {
            CreateActionItem eai = (CreateActionItem) ean;
            return eai.isCreateParent();
        }
        return false;
    }

    /**
     * isEditRelatorOnly
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isEditRelatorOnly() {
        EANActionItem ean = getParentActionItem();
        if (ean instanceof EditActionItem) {
            EditActionItem eai = (EditActionItem) ean;
            return eai.isEditRelatorOnly();
        }
        return false;
    }

    /**
     * isVEEdit
     * @return boolean
     * @author tony
     */
    public boolean isVEEdit() { //VEEdit CR0815056514
        EANActionItem ean = getParentActionItem(); //VEEdit CR0815056514
        if (ean instanceof ExtractActionItem) { //VEEdit CR0815056514
            return ean.isVEEdit(); //VEEdit CR0815056514
        } //VEEdit CR0815056514
        return false; //VEEdit CR0815056514
    } //VEEdit CR0815056514

    /**
     * showRelParentChild
     *
     * @param _strRelatorType
     * @return
     *  @author David Bigelow
     */
    public boolean showRelParentChild(String _strRelatorType) {
        //    D.ebug(D.EBUG_SPEW,"EntityList showRelParentChild: " + _strRelatorType);
        EANActionItem ean = getParentActionItem();
        if (ean instanceof NavActionItem) {
            NavActionItem nai = (NavActionItem) ean;
            return nai.showRelParentChild(_strRelatorType);
        }
        else if (ean instanceof SearchActionItem) {
            SearchActionItem sai = (SearchActionItem) ean;
            return sai.showRelParentChild(_strRelatorType);
        }
        return false;
    }

    private String getSearchText(String _str) {
        _str = _str.replace(QM, UL);
        _str = _str.replace(STAR, PER);
        return _str;
    }

    /**
     *  create the entityList attribute of the EntityList class
     *
     * @param  _db
     * @param  _prof
     * @param  _ai
     * @param  _aei
     * @return                                            The entityList value
     * @exception  SQLException                        Description of the Exception
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  RemoteException                        Description of the Exception
     * @author tony
     */
    public static EntityList createEntityList(Database _db, Profile _prof, EANActionItem _ai, EntityItem[] _aei) throws
        SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException {
        if (_ai instanceof NavActionItem) {
            return getEntityList(_db, _prof, (NavActionItem) _ai, _aei);
        }
        return null;
    }

    /*
     * map the relationship(s)
     * 6MV4U7
     */
    private void mapRelations(Database _db, Profile _prof, int _iSessionID, Hashtable memTbl) {
        ReturnStatus returnStatus = new ReturnStatus();
        ResultSet rs = null;
        try {
            rs = _db.callGBL2222(returnStatus, _iSessionID, _prof.getEnterprise(), _prof.getValOn(), _prof.getEffOn());
            while (rs != null && rs.next()) {
                String sEnt1 = EANUtility.reuseObjects(memTbl,rs.getString(2).trim());
                int iEnt1 = rs.getInt(3);
                String sRel = EANUtility.reuseObjects(memTbl,rs.getString(4).trim());
                int iRel = rs.getInt(5);
                String sEnt2 = EANUtility.reuseObjects(memTbl,rs.getString(6).trim());
                int iEnt2 = rs.getInt(7);

     			_db.debug(D.EBUG_SPEW, "gbl2222 answers: "+sEnt1+":"+iEnt1+":" + sRel+":"+iRel+":"+sEnt2+":"+iEnt2);

                EntityGroup egRel = getEntityGroup(sRel);
                EntityGroup egParent = getEntityGroup(sEnt1);
                EntityGroup egKid = getEntityGroup(sEnt2);

                EntityItem eiParent = null;
                EntityItem eiRel = null;
                EntityItem eiKid = null;

                if (egParent != null && egRel != null && egKid != null) {
                    eiParent = egParent.getEntityItem(sEnt1, iEnt1);
                    eiRel = egRel.getEntityItem(sRel, iRel);
                    eiKid = egKid.getEntityItem(sEnt2, iEnt2);
                    if (eiRel != null) {
                        if (eiParent == null) {
							//MN33607291 look for parent in parent group
							// error when MODEL:MODELREL:MODEL
							if (sEnt1.equals(getParentEntityGroup().getEntityType())){
								eiParent = getParentEntityGroup().getEntityItem(sEnt1, iEnt1);
							}

							if (eiParent == null) {
								eiParent = new EntityItem(egParent, null, sEnt1, iEnt1);
								egParent.putEntityItem(eiParent);
							}
                        }

                        eiParent.putDownLink(eiRel);
                        eiRel.putUpLink(eiParent);
                        if (eiKid == null) {
                            eiKid = new EntityItem(egKid, null, sEnt2, iEnt2);
                            egKid.putEntityItem(eiKid);
                        }
                        eiRel.putDownLink(eiKid);
                        eiKid.putUpLink(eiRel);
                    }
                }
            }
        }
        catch (MiddlewareException _me) {
            _me.printStackTrace();
        }
        catch (SQLException _sql) {
            _sql.printStackTrace();
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                }
                catch (SQLException _sql) {
                }
                rs = null;
            }
            try {
                _db.commit();
            }
            catch (SQLException _sql) {
            }
            _db.freeStatement();
            _db.isPending();
        }
        return;
    }


	private static final int NO_DOMAIN = 0;			// returned when no PDHDOMAIN attr or has one but isnt NAV attr
	private static final int NO_DOMAIN_MATCH = 1;	// returned when PDHDOMAIN of profile does not match entity.PDHDOMAIN
	private static final int DOMAIN_MATCH = 2;		// returned when PDHDOMAIN of profile matches entity.PDHDOMAIN

	/***************************************
	* RQ0713072645
	* Make actions sensitive to the PROFILE's PDHDOMAINs
	*@param profile	Profile to use for PDHDOMAIN check
	*@param _nai	EANActionItem action item
	*@param item	EntityItem to check
	*@param dexc	DomainException to hold user message
	*@return int
	*/
	private static int checkDomain(Profile profile, EANActionItem _nai,EntityItem item, DomainException dexc)
	{
		if (profile ==null) {
			profile = item.getProfile();
		}

		if (!profile.getEnforcePDHDomain()){
			D.ebug(D.EBUG_SPEW,"EntityList.checkDomain is not turned on in middleware properties (need enforce.pdhdomain=true), so domain check will not be done");
			return DOMAIN_MATCH;
		}

		String pdhdomainStr = profile.getPDHDomainFlagCodes();
		EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute("PDHDOMAIN");
		if (fAtt==null){
			item.refreshDefaults();// attempt to get pdhdomain - if on a relator that was not edited, the domain will not be set
			item.commitLocal();
			fAtt = (EANFlagAttribute)item.getAttribute("PDHDOMAIN");
		}

		D.ebug(D.EBUG_SPEW,"EntityList.checkDomain checking profile: '"+profile.dumpPDHDomain(true)+"' fatt: '"+
			fAtt+"' action: '"+_nai+"' "+(_nai==null?"":_nai.getClass().getName())+" item: "+item.getKey());

		if (fAtt!=null){
			// PDHDOMAIN must be a nav attr, this will prevent this check in other enterprises
			EANMetaAttribute ma = fAtt.getMetaAttribute();

//System.out.println("EntityList.checkDomain ma: "+ma+" isnav: "+(ma==null?false:ma.isNavigate()));
//if (ma==null){
//java.lang.Thread.currentThread().dumpStack(); // just for debug as to why 
//}
			if (ma == null || !ma.isNavigate()){
				D.ebug(D.EBUG_INFO,"EntityList.checkDomain PDHDOMAIN metaattr: "+ma+" not a NAV attr, so domain check will not be done");
				return NO_DOMAIN;
			}

			if (fAtt.toString().length()==0){
				D.ebug(D.EBUG_INFO,"EntityList.checkDomain PDHDOMAIN is a NAV attr, BUT it has no value, allow action to execute");
				return DOMAIN_MATCH;
			}

			// check to see if profile has this value too
			MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
			for (int mfi = 0; mfi < mfArray.length; mfi++) {
				// Check that it is selected
				if (mfArray[mfi].isSelected() &&
					pdhdomainStr.indexOf(mfArray[mfi].getFlagCode())!= -1) {
					return DOMAIN_MATCH;
				}
			}
			// build err msg, if got here then no match found
			String msg = " Domain does not match user profile's domain, '"+profile.dumpPDHDomain(true)+"'.";
			if (_nai instanceof EditActionItem){
				msg = msg+" Edit is reduced to View.";
			}else{
				if (_nai ==null){  // cant always get action when invoked from a RowSelectableTable
					msg = msg+ " Action Cannot execute.";
				}else{
					String category = _nai.getCategoryLongDescription();
					if (category ==null || category.equals("NOT CATEGORIZED")){
						category = _nai.getActionClass();
					}
					msg = msg+ " Cannot execute '"+category+": "+_nai+"'("+_nai.getActionItemKey()+").";
				}
			}

			D.ebug(D.EBUG_SPEW,"EntityList.checkDomain "+msg+" "+item.getKey());
			dexc.add(item,msg);
			return NO_DOMAIN_MATCH;
		}

		return NO_DOMAIN;
	}

	/***************************************
	* RQ0713072645
	* Make actions sensitive to the PROFILE's PDHDOMAINs
	*
	* If the Entity that I am at has a list of PDHDOMAINs where at least one is in my PROFILE's list of PDHDOMAINs
	* or it does not have PDHDOMAIN, then the action behaves as it does today.
	*
	* If the Entity that I am at does not have a least one PDHDOMAIN that is in my PROFILE's list of PDHDOMAINs,
	* then the only action type that functions is "Edit" and it is limited to "View".
	* This is reflected in the table below where "No Op" means the Action does not Function.
	* 	ActionType		Effect
	* -------------------------
	* 	Copy			No Op
	* 	Create			No Op
	* 	Delete			No Op
	* 	Edit			View
	* 	Extract			No Op
	* 	Link			No Op
	* 	Matrix			No Op
	* 	Navigate		No Op
	* 	PDG				No Op
	* 	Report			No Op
	* 	Search			No Op
	* 	WhereUsed		No Op
	* 	Workflow		No Op
	*	VEEdit			No Op because complicated to turn all items off in one row
	* 		if (nai instanceof EditActionItem) {
	*		 // EntityItem's check will convert edit to view but this generates the msg, executing editaction should
	*		 // not call this
	*	}
	*
	*@param profile	Profile to use for PDHDOMAIN check
	*@param nai	EANActionItem action item
	*@param ei	EntityItem[] to check
	*@throws DomainException
	*/
	public static void checkDomain(Profile profile, EANActionItem nai,EntityItem[] ei) throws DomainException
	{
		if (nai instanceof LockActionItem || nai instanceof CGActionItem) {
			return;
		}

		// linkaction must flow, search action will limit choices
		if (nai instanceof LinkActionItem && !nai.mustCheckDomain()) {
			return;
		}

		// WorkflowActionItem may need to cross domains, like 'rpt in-basket' gets all reports
		// for the user across all domains, and UI allows deletion
		if (nai instanceof WorkflowActionItem && !nai.mustCheckDomain()) {
			return;
		}

		// allow peer create to flow, can't stop it in BUI without major code changes and
		// it doesn't use the selected item
		if (nai instanceof CreateActionItem && ((CreateActionItem)nai).isPeerCreate()) {
			return;
		}

		// allow search by entityid to flow, can't stop it in BUI without major code changes and
		// it doesn't use the selected item
		if (nai instanceof SearchActionItem && ((SearchActionItem)nai).isGrabByEntityID()) {
			return;
		}

		// allow nav to return home to flow, can't stop it in BUI without major code changes and
		// it doesn't use the selected item
		if (nai instanceof NavActionItem){
			if (((NavActionItem)nai).isHomeEnabled()) {
				return;
			}
			// check parent action, if where used, let it run enhancement-3 handles this now
			if (((NavActionItem)nai).getParentActionItem() instanceof WhereUsedActionItem){
				return;
			}
		}

		if (nai instanceof EditActionItem && !((EditActionItem)nai).canEdit()){
			return;
		}
		// allow for extracts pulling all data to display
		if (nai instanceof ExtractActionItem && nai.getActionItemKey().equalsIgnoreCase("dummy")){
			return;
		}
		
		// support turning off any action now thru meta
		if (nai!=null && !nai.mustCheckDomain()){
			D.ebug(D.EBUG_SPEW,"EntityList.checkDomain "+nai.getActionItemKey()+" domain check is turned off");
			return;
		}
		if(ei!=null){
			DomainException dexc = new DomainException();
			// look at selected items
			for (int i=0; i<ei.length; i++){
				EntityItem item = ei[i];
				int match = checkDomain(profile, nai,item, dexc);

				//if this is a relator and didnt find a pdhdomain on it, look at downlinks
				if (match == NO_DOMAIN){
					EntityGroup eg = item.getEntityGroup();
					if (eg!=null && (eg.isRelator()||eg.isAssoc())){ 
						
						// allow search to flow, when getAssociatedEntityType()!=null
						//SG  Action/Attribute    SRDAVAIL2   ENTITYTYPE  Link    MODEL 
						//SG  Action/Attribute    SRDAVAIL2   TYPE    RootEI  MODEL 
						// it doesn't use the selected item
						if (nai instanceof SearchActionItem && nai.getAssociatedEntityType()!=null &&
								((SearchActionItem)nai).useRootEI()) {
							// get parent
							for (int ir=0; ir<item.getUpLinkCount(); ir++){
								EntityItem eitem = (EntityItem)item.getUpLink(ir);
								if (eitem.getEntityType().equals(nai.getRootEntityType())){
									match = checkDomain(profile, nai,eitem, dexc);	
								}
							}
						}else{
							for (int ir=0; ir<item.getDownLinkCount(); ir++){
								EntityItem eitem = (EntityItem)item.getDownLink(ir);
								match = checkDomain(profile, nai,eitem, dexc);
							}
						}
					}
				}
			}
			if (dexc.getErrorCount()>0){
				throw dexc;
			}
		}
	}

	/***************************************
	* RQ0713072645
	* Make actions sensitive to the PROFILE's PDHDOMAINs
	*@param profile	Profile to use for PDHDOMAIN check
	*@param nai	EANActionItem action item
	*@param el	EANList to check
	*@throws DomainException
	*/
	public static void checkDomain(Profile profile, EANActionItem nai,EANList el) throws DomainException
	{
		if (el!=null){
			EntityItem eiArray[] = new EntityItem[el.size()];
			el.copyTo(eiArray);
			checkDomain(profile, nai,eiArray);
		}
	}

	/***************************************
	* RQ0713072645
	* Make actions sensitive to the PROFILE's PDHDOMAINs
	*@param profile	Profile to use for PDHDOMAIN check
	*@param nai	EANActionItem action item
	*@param ei	EntityItem to check
	*@throws DomainException
	*/
	public static void checkDomain(Profile profile, EANActionItem nai,EntityItem ei) throws DomainException
	{
		if (ei!=null){
			EntityItem eiArray[] = new EntityItem[]{ei};
			checkDomain(profile, nai,eiArray);
		}
	}
	
    /**
     * CQ 227988 need to do this in middleware classes because EntityItem levels are protected
     * merge list2 into list1, list2 will be dereferenced
     * only valid for lists with a single root entity
     * @param list1
     * @param list2
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     */
    public static void mergeLists(EntityList list1, EntityList list2) throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        //loop thru list1 and list2 and collect any items that have the same key
        Hashtable existTbl = new Hashtable();
        EntityItem theparent = list1.getParentEntityGroup().getEntityItem(0);
    	existTbl.put(theparent.getKey(), theparent);
    	
        for(int i=0; i<list1.getEntityGroupCount(); i++){
        	EntityGroup eg = list1.getEntityGroup(i);
        	EntityGroup eg2= list2.getEntityGroup(eg.getEntityType());
        	if(eg2!=null){
        		for(int e=0; e<eg2.getEntityItemCount(); e++){
        			EntityItem item = eg2.getEntityItem(e);
        			EntityItem item1 = eg.getEntityItem(item.getKey());
        			if(item1!=null){
        				existTbl.put(item1.getKey(), item1);
        			}
        		}
        	}
        }
        
        EntityGroup egArray[] = new EntityGroup[list2.getEntityGroupCount()];
        for(int i=0; i<list2.getEntityGroupCount(); i++){
        	EntityGroup eg = list2.getEntityGroup(i);
        	egArray[i]=eg;
        }
        // merge lists by moving entitygroups or entityitems or up and downlinks to existing items
        for(int i=0; i<egArray.length; i++){
        	EntityGroup eg = egArray[i];
        	mergeItems(list1, list2, eg.getEntityType(),existTbl);
        	egArray[i] = null;
        }
       	
       	// release memory
        egArray = null;
       	existTbl.clear();
       	existTbl = null;
        list2.dereference();
    }
    
    /**
     * merge items for specified entitygroup type
     * @param abr
     * @param list1
     * @param list2
     * @param groupname
     * @param existTbl
     */
    private static void mergeItems(EntityList list1, EntityList list2, String groupname, Hashtable existTbl){
        // add all entities from new list2 to first pull list1
        EntityGroup origGrp = list1.getEntityGroup(groupname);
        EntityGroup newGrp = list2.getEntityGroup(groupname);

        if(origGrp!=null){
        	//list1 entitygroup existed 
        	EntityItem eiArray[] = newGrp.getEntityItemsAsArray();
        	for (int i=0;i<eiArray.length; i++) {
        		EntityItem item = eiArray[i];
        		EntityItem list1Item = origGrp.getEntityItem(item.getKey());
        		if(list1Item==null){
        			// put it in the orig list group
        			origGrp.putEntityItem(item);
        			// must move metaattr to new group too
        			item.reassign(origGrp); 
        			// remove it from new list
        			newGrp.removeEntityItem(item);
        			//move any links to existing items
        			moveLinks(list1Item, item, existTbl);
        		}else{ 
        			//entityitem already existed
        			moveLinks(list1Item, item, existTbl);	
        		}
        	}
        }else{ 
        	// group did not exist in list1
        	list2.removeEntityGroup(newGrp);
        	list1.putEntityGroup(newGrp);
        	for (int i=0;i<newGrp.getEntityItemCount(); i++) {
        		EntityItem item = newGrp.getEntityItem(i);
        		//move any links to existing items
        		moveLinks(null, item, existTbl);
        	}
        }
    }

    /**
     * move any links from item2 to item1
     * @param abr
     * @param list1Item
     * @param item2
     * @param existTbl
     */
    private static void moveLinks(EntityItem list1Item, EntityItem item2, Hashtable existTbl)
    { 
    	if(list1Item==null){
        	//item did not exist in list1, the item2 was added to list1 entitygroup
    		//move any links to list1 items if they already existed
        	// does any of the item2 links refer to an existing key in list1
        	if(item2.getUpLinkCount()>0){
    			EntityItem upArray[] = new EntityItem[item2.getUpLinkCount()];
    			item2.getUpLink().copyInto(upArray);
    			for (int j=0; j<upArray.length; j++){
    				EntityItem upItem = upArray[j]; 
    				EntityItem upitem1 = (EntityItem)existTbl.get(upItem.getKey());
    				if(upitem1!=null){ // entity existed like MODEL, move up link to it from list2
    					// remove from list2 item and add to the list1 item
        				item2.removeUpLink(upItem);
        				item2.putUpLink(upitem1);
    				}

    				upArray[j]=null;
    			}
    			upArray = null;
    		}
    		if(item2.getDownLinkCount()>0){  
    			EntityItem downArray[] = new EntityItem[item2.getDownLinkCount()];
    			item2.getDownLink().copyInto(downArray);
    			for (int j=0; j<downArray.length; j++){
    				EntityItem dnItem = downArray[j]; 
    				EntityItem dnitem1 = (EntityItem)existTbl.get(dnItem.getKey());
    				if(dnitem1!=null){ // entity existed like MODEL, move down link to it from list2
    					// remove from list2 item and add to the list1 item
        				item2.removeDownLink(dnItem);
        				item2.putDownLink(dnitem1);
    				}
    				
    				downArray[j]=null;
    			}
    			downArray = null;
    		}
    	}else{
    		//merge the levels in item2 with list1item - a new up or down link is another level
    		boolean[] item2Levels = item2.getLevels();
    		for(int i=0;i<item2Levels.length; i++){
    			if(item2Levels[i]){
    				list1Item.setLevel(i);
    			}
    		}
    		
    		//entity already exists in list1, move up and downlinks to list1 from list2 if they dont exist already
    		if(item2.getUpLinkCount()>0){
    			EntityItem upArray[] = new EntityItem[item2.getUpLinkCount()];
    			item2.getUpLink().copyInto(upArray);
    			for (int j=0; j<upArray.length; j++){
    				EntityItem upItem = upArray[j]; 
    				EntityItem upitem1 = (EntityItem)existTbl.get(upItem.getKey());
    				if(upitem1==null){
    					// remove  from list2  item and add to list1 item
        				item2.removeUpLink(upItem);
        				list1Item.putUpLink(upItem);
    				}
    	
    				upArray[j]=null;
    			}
    			upArray = null;
    		}
    		if(item2.getDownLinkCount()>0){  
    			EntityItem downArray[] = new EntityItem[item2.getDownLinkCount()];
    			item2.getDownLink().copyInto(downArray);
    			for (int j=0; j<downArray.length; j++){
    				EntityItem dnItem = downArray[j]; 
    				EntityItem dnitem1 = (EntityItem)existTbl.get(dnItem.getKey());
    				if(dnitem1==null){
    					// remove  from list2  item and add to list1 item
    					item2.removeDownLink(dnItem);
    					list1Item.putDownLink(dnItem);
    				}
    				downArray[j]=null;
    			}
    			downArray = null;
    		}
    	}
    }
}
