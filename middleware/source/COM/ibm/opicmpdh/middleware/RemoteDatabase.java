//
// TEMPLATE: REMOTEDBMETHODS.TXT (2020-06-15-05.16.43.622000)
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: remotedbmethods.txt,v $
// Revision 1.477  2014/06/26 19:09:20  wendy
// allow rmi serial port number to be defined
//
// Revision 1.476  2014/02/24 14:58:54  wendy
// RCQ285768 - view cached XML in JUI
//
// Revision 1.475  2013/10/24 17:31:04  wendy
// added getDynaSearchTable() for search picklist perf
//
// Revision 1.474  2013/09/19 14:57:33  wendy
// add abr queue status
//
// Revision 1.473  2011/09/13 21:49:17  wendy
// return users information in command
//
// Revision 1.472  2011/09/13 20:52:11  wendy
// getInstanceName was infinite loop
//
// Revision 1.471  2011/09/09 17:23:32  wendy
// add securelogin for RMI
//
// Revision 1.470  2010/05/17 13:05:41  wendy
// remove dumpstack
//
// Revision 1.469  2009/05/15 17:51:14  wendy
// Change return type for checkVELockOwners()
//
// Revision 1.468  2009/05/14 18:15:28  wendy
// Fix typo
//
// Revision 1.467  2009/05/14 18:08:20  wendy
// Added methods for UI performance improvements
//
// Revision 1.466  2009/03/12 19:56:05  wendy
// back out JCE (encryption) for now - requires jre1.4
//
// Revision 1.465  2009/03/11 18:24:03  wendy
// Added encrypted login
//
// Revision 1.464  2008/08/08 21:35:34  wendy
// CQ00006067-WI : LA CTO - More support for QueryAction
//
// Revision 1.463  2008/07/31 18:54:00  wendy
// CQ00006067-WI : LA CTO - Added support for QueryAction
//
// Revision 1.462  2007/05/23 14:35:52  wendy
// Use dbcurrent for debug msgs when possible to get connectid
//
// Revision 1.461  2007/04/23 13:34:36  wendy
// Null dbcurrent causing JUI hangs, hiding other exceptions
//
// Revision 1.460  2006/10/05 16:17:22  roger
// Fix
//
// Revision 1.459  2006/03/10 17:39:59  joan
// remove not needed method for pdg
//
// Revision 1.458  2006/03/07 16:37:31  joan
// remove some not needed methods
//
// Revision 1.457  2005/10/11 18:27:06  joan
// fix compile
//
// Revision 1.456  2005/10/11 18:20:03  joan
// add new method for PDG
//
// Revision 1.455  2005/08/11 17:21:27  tony
// added EntityItem array to catalog function
//
// Revision 1.454  2005/08/11 16:03:01  tony
// fixed compiler errors
//
// Revision 1.453  2005/08/11 15:55:41  tony
// remote execution of addtional processes.
//
// Revision 1.452  2005/05/27 15:26:24  tony
// added finally
//
// Revision 1.451  2005/05/25 17:16:24  tony
// compile
//
// Revision 1.450  2005/05/25 17:09:16  tony
// reLoad
//
// Revision 1.449  2005/05/24 21:20:20  tony
// fix
//
// Revision 1.448  2005/05/24 21:13:27  tony
// mw proper pass thru
//
// Revision 1.447  2005/05/24 20:44:05  roger
// Fix
//
// Revision 1.446  2005/05/24 20:42:16  roger
// Fix
//
// Revision 1.445  2005/05/24 16:08:46  roger
// Primitive mail message support
//
// Revision 1.444  2005/05/09 19:19:31  tony
// backward compat
//
// Revision 1.443  2005/05/09 18:01:53  tony
// fix compilation error.
//
// Revision 1.442  2005/05/09 17:55:24  tony
// improved versioning logic.
//
// Revision 1.441  2005/03/24 18:30:54  joan
// work on flag maintenance
//
// Revision 1.440  2005/03/07 23:09:53  joan
// work on flag maintenance
//
// Revision 1.439  2005/03/07 19:58:13  joan
// work on flag maintenance
//
// Revision 1.438  2005/03/04 17:44:37  joan
// add methods for flag code maintenance
//
// Revision 1.437  2005/03/01 23:39:21  joan
// work on Meta Maintenance
//
// Revision 1.436  2005/02/10 00:54:54  joan
// add throw exception
//
// Revision 1.435  2005/02/08 20:44:50  roger
// prune dead code
//
// Revision 1.434  2005/01/14 18:57:55  tony
// improved update logic.
//
// Revision 1.433  2004/12/17 18:35:09  joan
// add new method
//
// Revision 1.432  2004/11/22 23:01:06  gregg
// MetaColumnOrderGroup on SearchBinder
//
// Revision 1.431  2004/10/28 19:15:24  joan
// add freeconnection for PDG
//
// Revision 1.430  2004/10/13 18:51:28  tony
// added softwareImageUpdate Version request.
//
// Revision 1.429  2004/10/12 21:54:13  tony
// fix compiler error.
//
// Revision 1.428  2004/10/12 21:36:13  tony
// Added getSoftwareImage and putSoftwareImage to
// allow for on-line updating of the application.
//
// Revision 1.427  2004/08/13 16:55:05  roger
// Fix it
//
// Revision 1.426  2004/08/13 16:33:38  roger
// BluePageEntryGroup support
//
// Revision 1.425  2004/08/13 16:31:19  roger
// BluePageEntryGroup support
//
// Revision 1.424  2004/07/30 17:15:39  gregg
// cleanup object pool code
//
// Revision 1.423  2004/07/26 22:08:53  joan
// add unlockEntityItems
//
// Revision 1.422  2004/07/26 17:49:55  joan
// fix compile
//
// Revision 1.421  2004/07/26 17:39:20  joan
// fix compile
//
// Revision 1.420  2004/07/26 17:27:57  joan
// fix compile
//
// Revision 1.419  2004/07/26 17:06:01  joan
// add lockEntityItems
//
// Revision 1.418  2004/07/25 07:12:59  dave
// final tuning
//
// Revision 1.417  2004/07/25 03:23:34  dave
// more RMI config exposure
//
// Revision 1.416  2004/07/24 23:20:44  dave
// fin tix
//
// Revision 1.415  2004/07/24 23:09:50  dave
// more RMI config control in props
//
// Revision 1.414  2004/07/24 22:16:01  dave
// more RMI controls for trace and debug
//
// Revision 1.413  2004/07/24 22:13:05  dave
// more rmi logging options
//
// Revision 1.412  2004/07/24 22:09:03  dave
// more control RMI connection Pool
//
// Revision 1.411  2004/07/24 21:47:04  dave
// new RMI debuging properties (as opposed to -D)
//
// Revision 1.410  2004/07/22 18:03:58  dave
// caught fish.. runtime error catch now follows through to
// user
//
// Revision 1.409  2004/06/21 15:02:36  joan
// add ABRStatus action
//
// Revision 1.408  2004/06/18 19:45:56  roger
// Catch SQL trouble
//
// Revision 1.407  2004/06/18 19:29:33  roger
// Want to use specialized BluePageException
//
// Revision 1.406  2004/06/18 16:27:58  roger
// Return something
//
// Revision 1.405  2004/06/18 16:10:57  roger
// Support new BluePageEntry feature and object
//
// Revision 1.404  2004/06/15 20:36:42  joan
// add CopyActionItem
//
// Revision 1.403  2004/06/14 20:43:35  roger
// Needed here too
//
// Revision 1.402  2004/06/08 17:05:23  joan
// add methods
//
// Revision 1.401  2004/05/27 21:01:49  joan
// fix compile
//
// Revision 1.400  2004/05/27 20:05:09  joan
// add collectInfo method
//
// Revision 1.399  2004/05/21 22:37:12  joan
// more adjustment
//
// Revision 1.398  2004/05/21 21:55:28  joan
// work on chain action
//
// Revision 1.397  2004/05/20 22:09:57  joan
// work on chain action
//
// Revision 1.396  2004/05/11 17:31:06  gregg
// saveRuntime
//
// Revision 1.395  2004/05/10 21:47:42  gregg
// fix
//
// Revision 1.394  2004/05/10 21:39:58  gregg
// method name change: getLatestRuntime-->getRuntime
//
// Revision 1.393  2004/05/10 21:33:42  gregg
// getLatestRuntime
//
// Revision 1.392  2004/04/23 16:26:07  gregg
// getExtractActionItem
//
// Revision 1.391  2004/04/23 16:12:47  gregg
// getWatchdogActionItem, getWorkflowActionItem
//
// Revision 1.390  2004/04/20 20:32:12  gregg
// genVEChangeXML
//
// Revision 1.389  2004/03/25 23:22:45  dave
// adding version visibility for clients
//
// Revision 1.388  2004/03/19 21:20:29  gregg
// iDupMode to putBookmarkedActionItem
//
// Revision 1.387  2004/03/19 19:26:55  gregg
// returning BookmarkItem on store
//
// Revision 1.386  2004/03/15 21:09:11  gregg
// getLinkActionItem
//
// Revision 1.385  2004/03/12 19:29:33  gregg
// nother phix
//
// Revision 1.384  2004/03/12 19:23:22  gregg
// compile fix
//
// Revision 1.383  2004/03/12 19:12:38  gregg
// getCommonProfiles() method
//
// Revision 1.382  2004/01/12 20:35:03  dave
// fixing buggs #1
//
// Revision 1.381  2004/01/08 22:21:51  dave
// Lets try to cache some Action Trees
//
// Revision 1.380  2003/12/16 23:40:50  joan
// work on CR
//
// Revision 1.379  2003/12/16 22:28:33  gregg
// compile fix
//
// Revision 1.378  2003/12/16 22:07:51  gregg
// getMetaColumnOrderForMatrix
//
// Revision 1.377  2003/11/21 21:04:51  joan
// remove throw exception
//
// Revision 1.376  2003/11/21 20:49:11  joan
// throw exception
//
// Revision 1.375  2003/10/30 01:13:44  dave
// removing boos
//
// Revision 1.374  2003/10/29 21:52:58  dave
// More trace for workflow
//
// Revision 1.373  2003/09/29 20:30:48  joan
// throw exception
//
// Revision 1.372  2003/09/26 20:46:30  joan
// add queuedABR method
//
// Revision 1.371  2003/09/04 17:05:59  dave
// changing PHD to PDH
//
// Revision 1.370  2003/08/25 20:57:35  dave
// clean up on remote di
//
// Revision 1.369  2003/08/25 20:45:43  dave
// more cleanup
//
// Revision 1.368  2003/08/25 20:06:48  dave
// streamlining the SearchActionItem to not
// carry the full entityGroup upon creation, but to
// go get it .. when the search action item is actually invoked
// with an exec or rexec
//
// Revision 1.367  2003/08/20 16:17:21  dave
// exposing setTranslationPackageStatus to remote interface
//
// Revision 1.366  2003/08/15 20:32:48  dave
// fixing syntax
//
// Revision 1.365  2003/08/15 20:21:29  dave
// adding remote ETS interface back
//
// Revision 1.364  2003/07/24 15:58:43  joan
// fix compile error
//
// Revision 1.363  2003/07/24 15:51:19  joan
// add InactiveGroup
//
// Revision 1.362  2003/06/26 22:39:27  joan
// move changes from v111
//
// Revision 1.361  2003/06/04 17:44:38  roger
// Expose hasPDH() and hasODS() to RemoteDatabase users
//
// Revision 1.360  2003/06/02 21:48:11  roger
// Property name changed
//
// Revision 1.359  2003/06/02 17:07:49  roger
// Property names have changed for PDH+ODS
//
// Revision 1.358  2003/05/29 18:17:50  gregg
// getSearchActionItem method
//
// Revision 1.357  2003/05/16 15:43:04  joan
// add SPDGActionItem
//
// Revision 1.356  2003/05/15 17:34:25  dave
// adding copyCount to the max calcuator
//
// Revision 1.355  2003/05/15 16:17:19  joan
// work on lock
//
// Revision 1.354  2003/05/14 23:32:15  joan
// add getAllSoftLockforWGID method
//
// Revision 1.353  2003/05/13 23:54:19  joan
// add refreshEntityItem
//
// Revision 1.352  2003/05/12 23:56:39  joan
// catch exception
//
// Revision 1.351  2003/05/09 21:58:24  gregg
// remove emf parent param from ChangeHistoryGroup constructor
//
// Revision 1.350  2003/05/07 20:47:34  dave
// commenting out remote access to the Mail Stuff to see if
// anything in Tony's code breaks
//
// Revision 1.349  2003/04/30 20:18:39  roger
// Clean up
//
// Revision 1.348  2003/04/30 17:26:52  roger
// Expose method for mail
//
// Revision 1.347  2003/04/29 17:05:49  dave
// clean up and removal of uneeded function
//
// Revision 1.346  2003/04/28 19:04:26  dave
// link consolidation.. II
//
// Revision 1.345  2003/04/16 19:52:47  joan
// fix PDG creating entity
//
// Revision 1.344  2003/04/16 16:04:37  roger
// Added stub for logout method
//
// Revision 1.343  2003/04/15 22:18:31  joan
// add finally section in viewMissingData and executeAction for PDG
//
// Revision 1.342  2003/04/15 17:58:25  gregg
// setting a max limit on # of Bookmarks stored in database
//
// Revision 1.341  2003/04/02 16:35:55  dave
// final push to dup sessiontag info when a new instand of a profile
// is created from an existing profile
//
// Revision 1.340  2003/03/29 00:55:22  gregg
// kompyle phicks
//
// Revision 1.339  2003/03/29 00:46:28  gregg
// add enterprise column to bookmark
//
// Revision 1.338  2003/03/27 19:07:50  gregg
// getMetaColumnOrderGroup fix
//
// Revision 1.337  2003/03/27 18:59:16  gregg
// changes to getMetaColumnOrderGroup to avoid passing EnttiyGroup object
//
// Revision 1.336  2003/03/26 18:50:37  gregg
// getNewProfileInstance method
//
// Revision 1.335  2003/03/20 20:20:23  gregg
// MiddlewareRequestException for getChangeHistoryGroup methods
//
// Revision 1.334  2003/03/20 00:59:31  gregg
// Kompyle ficks
//
// Revision 1.333  2003/03/20 00:49:47  gregg
// getMetaEntityList method
//
// Revision 1.332  2003/03/19 20:41:49  gregg
// some more Exception throwing in Bookmark methods
//
// Revision 1.331  2003/03/19 18:51:03  gregg
// pass primitive params into deleteBookmark (no entire object)
//
// Revision 1.330  2003/03/19 18:46:25  gregg
// deleteBookmark method
//
// Revision 1.329  2003/03/18 22:27:56  gregg
// throw MiddlewareShutdownInProgressException in getBookmarkGroup
//
// Revision 1.328  2003/03/18 22:16:18  gregg
// putBookmarkedActionItem fix
//
// Revision 1.327  2003/03/18 21:58:44  gregg
// getBookmarkGroup method
//
// Revision 1.326  2003/03/18 21:25:47  gregg
// putBookmaredActionItem
//
// Revision 1.325  2003/03/18 01:41:50  gregg
// throw Exception in getBookmarkedActionItem method
//
// Revision 1.324  2003/03/18 01:33:08  gregg
// compile
//
// Revision 1.323  2003/03/18 01:23:32  gregg
// getBookmarkedActionItem method
//
// Revision 1.322  2003/03/17 20:31:03  gregg
// executeAction on NavActionItem
//
// Revision 1.321  2003/03/14 21:53:47  joan
// fix compile
//
// Revision 1.320  2003/03/14 21:16:50  joan
// fix viewMissingData
//
// Revision 1.319  2003/03/14 17:56:00  gregg
// updatePdhMetaRow
//
// Revision 1.318  2003/03/13 23:26:49  gregg
// more compile fix
//
// Revision 1.317  2003/03/13 23:13:17  gregg
// compile fix
//
// Revision 1.316  2003/03/13 22:51:10  gregg
// rollback
//
// Revision 1.315  2003/03/11 17:07:08  gregg
// getMetaColumnOrderGroup method
//
// Revision 1.314  2003/03/10 16:59:12  joan
// work on  PDG
//
// Revision 1.313  2003/03/05 18:24:08  gregg
// getAttributeChangeHostoryGroup
//
// Revision 1.312  2003/03/04 01:00:18  joan
// add pdg action item
//
// Revision 1.311  2003/03/03 18:36:37  joan
// add PDG action item
//
// Revision 1.310  2003/03/03 17:36:07  gregg
// getEntityChangeHistoryGroup method
//
// Revision 1.309  2003/02/05 00:09:40  gregg
// getIntervalGroup methods
//
// Revision 1.308  2003/01/29 00:12:15  joan
// add getGeneralAreaList
//
// Revision 1.307  2003/01/20 23:00:49  joan
// add methods to get VELockOwner and the list for VELock/Entity/Relator
//
// Revision 1.306  2003/01/16 21:31:41  joan
// add code to check for VELock when link
//
// Revision 1.305  2003/01/14 00:56:36  joan
// add checkOrphan
//
// Revision 1.304  2003/01/07 18:23:34  joan
// throw orphan exception
//
// Revision 1.303  2003/01/06 16:27:24  joan
// add getParentChildList method
//
// Revision 1.302  2002/12/23 22:17:53  joan
// add resetWGDefault method
//
// Revision 1.301  2002/12/13 21:30:54  joan
// fix bugs
//
// Revision 1.300  2002/12/13 21:05:45  joan
// fix compile errors
//
// Revision 1.299  2002/12/13 20:57:39  joan
// fix compile
//
// Revision 1.298  2002/12/13 20:41:04  joan
// fix for addition column in Softlock table
//
// Revision 1.297  2002/12/06 21:57:24  joan
// add getLockListForLockEntity method
//
// Revision 1.296  2002/11/19 18:27:47  joan
// adjust lock, unlock
//
// Revision 1.295  2002/11/19 00:06:31  joan
// adjust isLocked method
//
// Revision 1.294  2002/10/29 22:50:27  dave
// trapping the LockException and rethrowing it to the client
//
// Revision 1.293  2002/10/25 23:19:31  joan
// fix compile
//
// Revision 1.292  2002/10/25 22:47:08  joan
// add getListOfCountriesForAVAIL method
//
// Revision 1.291  2002/10/24 20:42:32  dave
// trying to free a bad connection on Asserition failure
//
// Revision 1.290  2002/10/23 22:53:06  bala
// Plug in remote methods to refresh the DG queue
//
// Revision 1.289  2002/10/15 20:33:33  joan
// add HWUPGRADEActionItem
//
// Revision 1.288  2002/10/14 23:13:01  joan
// fix compile
//
// Revision 1.287  2002/10/14 23:03:08  joan
// fix compile
//
// Revision 1.286  2002/10/14 22:53:53  joan
// add method to get HWUpgradeList
//
// Revision 1.285  2002/10/10 23:03:39  dave
// do not check for required - until you save it
//
// Revision 1.284  2002/10/10 20:48:30  dave
// minor mods for SearchActionItem and DynaSearch
//
// Revision 1.283  2002/10/08 18:05:06  dave
// more fixes hopefully
//
// Revision 1.282  2002/10/08 16:58:50  dave
// compile fixes
//
// Revision 1.281  2002/10/08 16:20:54  dave
// putting in the backend stub for DynaSearch
//
// Revision 1.280  2002/10/04 23:43:58  joan
// add HWPDG
//
// Revision 1.279  2002/09/11 16:07:04  dave
// syntax
//
// Revision 1.278  2002/09/11 15:58:09  dave
// adding remote database interface stuff
//
// Revision 1.277  2002/09/09 20:42:54  joan
// throw SBRException
//
// Revision 1.276  2002/08/30 16:41:06  roger
// Remove a nasty test line
//
// Revision 1.275  2002/08/29 22:46:08  roger
// Test this
//
// Revision 1.274  2002/08/12 21:33:49  dave
// idl queue transfer logic for ECCM idl
//
// Revision 1.273  2002/08/12 18:28:20  bala
// Added updateWGDefault method
//
// Revision 1.272  2002/07/30 01:29:08  dave
// more clean up
//
// Revision 1.271  2002/07/30 01:15:16  dave
// more cleanup
//
// Revision 1.270  2002/07/30 01:00:05  dave
// whopps brought back to life
//
// Revision 1.269  2002/07/30 00:51:06  dave
// more cleanup and removal
//
// Revision 1.268  2002/07/23 17:24:06  joan
// fix getLockList
//
// Revision 1.267  2002/07/23 16:51:08  joan
// fix getLockList
//
// Revision 1.266  2002/07/19 23:36:34  joan
// fix getSoftLock
//
// Revision 1.265  2002/07/19 22:35:04  joan
// fix errors
//
// Revision 1.264  2002/07/19 22:24:41  joan
// fix locklist
//
// Revision 1.263  2002/07/18 21:51:50  joan
// add SBRActionItem
//
// Revision 1.262  2002/06/25 20:36:11  joan
// add create method for whereused
//
// Revision 1.261  2002/06/21 15:46:14  joan
// add WhereUsedActionItem methods
//
// Revision 1.260  2002/06/18 18:28:10  joan
// working on picklist
//
// Revision 1.259  2002/06/06 20:54:04  joan
// working on link
//
// Revision 1.258  2002/06/05 17:37:46  joan
// add MatrixActionItem
//
// Revision 1.257  2002/05/24 18:15:05  bala
// Changed update method
// updateEntity,updateReturnEntityKey, updateReturnRelatorKey
// Added new signature to update and above methods
// to accept a "updateAttributesOnly" flag to bypass
// the sp update of entity rows when set to true
//
// Revision 1.256  2002/05/15 19:52:24  gregg
// set wdai in executeAction(WatchdogActionItem)
//
// Revision 1.255  2002/05/15 19:47:05  gregg
// return a WatchdogActionItem in executeAction(WatchdogActionItem)
//
// Revision 1.254  2002/05/15 16:08:16  joan
// fix errors
//
// Revision 1.253  2002/05/15 15:58:51  joan
// add executeAction for LockActionItem
//
// Revision 1.252  2002/05/14 22:25:41  gregg
// executeAction for Watchdog
//
// Revision 1.251  2002/05/13 16:42:11  joan
// fixing unlock method
//
// Revision 1.250  2002/05/10 20:45:56  joan
// fixing lock
//
// Revision 1.249  2002/05/09 22:43:28  joan
// fix throwing exceptions
//
// Revision 1.248  2002/05/09 20:27:22  joan
// working on throwing exception
//
// Revision 1.247  2002/05/08 16:58:35  joan
// fixing error
//
// Revision 1.246  2002/05/08 16:38:13  joan
// fixing compile errors
//
// Revision 1.245  2002/05/02 20:09:16  joan
// working on DeleteActionItem
//
// Revision 1.244  2002/04/22 20:27:42  joan
// syntax
//
// Revision 1.243  2002/04/22 20:18:00  joan
// working on unlock
//
// Revision 1.242  2002/04/22 17:51:34  dave
// more function removal
//
// Revision 1.241  2002/04/22 17:16:13  dave
// attempting to fix remotedatabase
//
// Revision 1.240  2002/04/22 16:37:54  dave
// stripping 1.0 functionality from the 1.1 arch
//
// Revision 1.239  2002/04/19 20:13:56  joan
// working on lock
//
// Revision 1.238  2002/04/17 21:54:43  joan
// fix errors
//
// Revision 1.237  2002/04/17 21:46:41  joan
// add methods to clear lock
//
// Revision 1.236  2002/04/17 18:43:23  joan
// syntax
//
// Revision 1.235  2002/04/17 18:33:24  joan
// syntax
//
// Revision 1.234  2002/04/17 18:13:36  joan
// fixing createLock method and add lockgroup methods in entityitem
//
// Revision 1.233  2002/04/16 23:44:09  joan
// add lock method
//
// Revision 1.232  2002/04/16 22:17:40  joan
// add getLockList method
//
// Revision 1.231  2002/04/05 18:43:40  dave
// first attempt at the extract action item
//
// Revision 1.230  2002/03/23 20:37:01  dave
// fixed update problem and renamed OPICMUpdate to update
//
// Revision 1.229  2002/03/22 18:39:14  bala
// OPICM Update
//
// Revision 1.228  2002/03/20 17:25:36  roger
// Remove getall console command
//
// Revision 1.227  2002/03/18 19:32:44  dave
// remove EANAddressable from external interface
//
// Revision 1.226  2002/03/08 18:58:46  dave
// first attempt at bringing edit online
//
// Revision 1.225  2002/03/07 22:18:31  dave
// too many getEntityGroup's in rdi
//
// Revision 1.224  2002/03/07 22:08:48  dave
// adding get EntityGroup to the database interface...
//
// Revision 1.223  2002/03/06 18:16:35  dave
// adding the Search Action Item
//
// Revision 1.222  2002/03/06 00:39:32  bala
// Add OPICMUpdate Method
//
// Revision 1.221  2002/03/05 17:04:52  dave
// added methods to EntityList for Create
//
// Revision 1.220  2002/02/26 17:47:37  dave
// more syntax
//
// Revision 1.219  2002/02/26 17:34:53  dave
// syntax fixes
//
// Revision 1.218  2002/02/26 17:25:04  dave
// merging the new link into the mix
//
// Revision 1.217  2002/02/20 20:10:50  dave
// Added the getMetaLink method
//
// Revision 1.216  2002/02/18 18:42:18  dave
// adding cart methods
//
// Revision 1.215  2002/02/14 18:54:09  dave
// syntax fixes
//
// Revision 1.214  2002/02/14 18:39:57  dave
// integrating getEntityList into the remote Interface
//
// Revision 1.213  2002/01/29 23:14:40  roger
// Reapply deactivateBlob change
//
// Revision 1.212  2002/01/29 23:01:24  roger
// Restored 1.209 revision
//
// Revision 1.209  2002/01/29 01:13:19  roger
// Yank date from here, makes more sense in Database
//
// Revision 1.208  2002/01/24 22:03:05  roger
// Clean up
//
// Revision 1.207  2002/01/24 21:39:56  roger
// Display log messages when creating/deleting semaphore files
//
// Revision 1.206  2002/01/18 17:05:33  roger
// Clean up
//
// Revision 1.205  2002/01/16 18:36:47  roger
// Change order of D.ebug settings
//
// Revision 1.204  2002/01/16 18:23:37  roger
// Set D.ebug properties (on/off, level, redirection) consistently in DB/RDB/Pool
//
// Revision 1.203  2002/01/16 17:56:40  roger
// Shutdown method belongs in DatabasePool not RemoteDatabase
//
// Revision 1.202  2002/01/15 22:24:07  roger
// Use Pool's init method
//
// Revision 1.201  2002/01/15 22:07:44  roger
// Changes for WAS pool
//
// Revision 1.200  2002/01/15 21:04:35  roger
// Remove instance name, now on UDP log only
//
// Revision 1.199  2002/01/07 21:25:07  joan
// add logic for serialhistorygroup and deactivateBlob
//
// Revision 1.198  2002/01/02 17:57:09  dave
// fixing minor stuff
//
// Revision 1.197  2002/01/02 17:50:12  dave
// sync between 1.0 and 1.1
//
// Revision 1.196  2001/12/26 22:46:46  joan
// add new putBlob and getBlob methods
//
// Revision 1.195  2001/12/04 21:36:57  joan
// 17369
//
// Revision 1.194  2001/11/29 18:40:08  roger
// Set TrustStore property programatically
//
// Revision 1.193  2001/11/29 17:13:28  roger
// Wrong variable name
//
// Revision 1.192  2001/11/29 17:07:39  roger
// Change property names
//
// Revision 1.191  2001/11/09 20:36:46  roger
// D.ebugs
//
// Revision 1.190  2001/11/09 20:30:59  roger
// D.ebug and static
//
// Revision 1.189  2001/11/09 20:14:47  roger
// D.ebug messages
//
// Revision 1.188  2001/11/09 19:38:02  roger
// D.ebug
//
// Revision 1.187  2001/11/09 19:32:04  roger
// debug messages
//
// Revision 1.186  2001/11/09 19:16:38  roger
// Version literal
//
// Revision 1.185  2001/11/09 19:09:42  roger
// Show version literal from database
//
// Revision 1.184  2001/11/06 19:03:52  roger
// Change comment
//
// Revision 1.183  2001/11/06 19:01:37  roger
// Show SSL parms and some clean-up
//
// Revision 1.182  2001/11/02 18:55:51  roger
// Client doesn't need those 2 extra parms
//
// Revision 1.181  2001/11/02 18:54:18  roger
// SSL Support
//
// Revision 1.180  2001/11/01 21:45:42  roger
// Implement SSL socket type
//
// Revision 1.179  2001/10/25 23:30:36  bala
// typo
//
// Revision 1.178  2001/10/25 23:24:50  bala
// added getFlagAttrHistory method
//
// Revision 1.177  2001/10/25 19:06:51  bala
// debug
//
// Revision 1.176  2001/10/25 18:13:58  bala
// debug
//
// Revision 1.175  2001/10/25 18:03:30  bala
// modified createVeasnavobj to accept valon and effon dates
// added wrapper to createveasnavobj to get the dates from profile
// add new method to return a delta of a ve navobj between 2 sets of dates
//
// Revision 1.174  2001/10/18 00:10:11  dave
// minor fixes
//
// Revision 1.173  2001/10/18 00:03:36  dave
// more changes
//
// Revision 1.172  2001/10/17 23:48:01  dave
// more tracing stuff
//
// Revision 1.171  2001/10/15 21:36:21  dave
// encorporated call into remote object
//
// Revision 1.170  2001/10/11 23:37:16  dave
// fixes
//
// Revision 1.169  2001/10/11 23:25:46  dave
// added getMetaFlagAttributeList to the remote interface
//
// Revision 1.168  2001/10/08 03:12:23  dave
// syntax
//
// Revision 1.167  2001/10/08 03:02:57  dave
// trace statements in prep for ldap use in POK
//
// Revision 1.166  2001/10/05 17:29:07  bala
// Converting sp PSG0006 to PSG0106 database method
//
// Revision 1.165  2001/10/03 16:50:03  bala
// Added remoteDBint methods to createVEasNavObj
//
// Revision 1.164  2001/09/26 20:23:40  dave
// added getNavigateObject to remote accessors
//
// Revision 1.163  2001/09/20 22:17:58  dave
// fixes for new search
//
// Revision 1.162  2001/09/20 17:42:58  roger
// Fix
//
// Revision 1.161  2001/09/20 17:16:36  roger
// Fixes
//
// Revision 1.160  2001/09/20 16:51:45  roger
// Use accessors for objects
//
// Revision 1.159  2001/09/14 22:10:18  dave
// more syntax
//
// Revision 1.158  2001/09/13 16:46:17  roger
// Profile changes
//
// Revision 1.157  2001/09/13 16:17:54  roger
// Profile changes
//
// Revision 1.156  2001/09/13 15:54:21  roger
// Profile changes
//
// Revision 1.155  2001/09/12 23:21:40  roger
// Profile changes
//
// Revision 1.154  2001/09/12 21:56:48  roger
// Profile changes
//
// Revision 1.153  2001/09/12 17:28:32  roger
// More Profile changes
//
// Revision 1.152  2001/09/12 16:26:06  roger
// Had a dupe login method
//
// Revision 1.151  2001/09/12 16:20:15  roger
// Return type wrong
//
// Revision 1.150  2001/09/12 16:04:52  roger
// Clean up and more Profile changes
//
// Revision 1.149  2001/09/11 22:12:11  roger
// Remove dupe entrypoints method
//
// Revision 1.148  2001/09/11 21:57:56  roger
// Convert ConnectionItem + SessionObject + NLSID to Profile
//
// Revision 1.147  2001/09/10 17:43:56  dave
// syntax fix
//
// Revision 1.146  2001/09/10 17:36:03  dave
// exposing new entrypoints in remote object
//
// Revision 1.145  2001/09/07 22:26:08  roger
// Remove imports of login package
//
// Revision 1.144  2001/08/30 21:38:46  roger
// Changes for login10
//
// Revision 1.143  2001/08/30 18:04:52  roger
// login10 and login11
//
// Revision 1.142  2001/08/29 22:37:20  roger
// Wrong method
//
// Revision 1.141  2001/08/29 22:31:42  roger
// Needed to rename, return variable not enough signature difference
//
// Revision 1.140  2001/08/29 22:24:02  roger
// Put LDAP feature in database instead
//
// Revision 1.139  2001/08/24 00:25:37  roger
// Trouble getting member variable use method instead
//
// Revision 1.138  2001/08/24 00:18:04  roger
// Must be static variable
//
// Revision 1.137  2001/08/24 00:10:32  roger
// Retrieve ldap_server property
//
// Revision 1.136  2001/08/23 23:10:46  roger
// Change to login method (can have multiple)
//
// Revision 1.135  2001/08/22 16:54:10  roger
// Removed author RM
//
// Revision 1.134  2001/08/15 16:31:54  dave
// added import statements for report
//
// Revision 1.133  2001/08/15 16:23:58  dave
// ReportSet exposed through RMI
//
// Revision 1.132  2001/08/13 17:41:03  roger
// More fixes
//
// Revision 1.131  2001/08/13 17:17:56  roger
// More finals
//
// Revision 1.130  2001/08/13 16:02:46  roger
// More fixes
//
// Revision 1.129  2001/08/13 15:54:26  roger
// Fixes
//
// Revision 1.128  2001/08/13 15:45:16  roger
// Authenticate now ready to test
//
// Revision 1.127  2001/08/08 18:54:37  dave
// added import statements for the RemoteObjects templates
//
// Revision 1.126  2001/08/08 18:35:04  dave
// fixes to API on searchRequest (syntax)
//
// Revision 1.125  2001/08/08 18:17:06  dave
// hooking up the getSearchRequest to the RemoteDatabase object
//
// Revision 1.124  2001/08/07 22:10:13  roger
// Wiped an undesired period
//
// Revision 1.123  2001/07/27 00:15:09  roger
// Remove ~all~ the fishing code, finally's are exec'ing OK
//
// Revision 1.122  2001/07/26 17:47:26  roger
// upped the anti from 250 to 500 per IBM/PCD
//
// Revision 1.121  2001/06/29 15:46:14  roger
// Changed "complete." to "complete"
//
// Revision 1.120  2001/06/27 20:29:57  roger
// Fish for finallys which don't execute
//
// Revision 1.119  2001/06/27 19:39:01  roger
// Use consistent literals for ease of grepping
//
// Revision 1.118  2001/06/27 19:26:03  roger
// Initialize instance name in constructor, not main
//
// Revision 1.117  2001/06/27 18:33:48  roger
// Instance name was not being looked-up
//
// Revision 1.116  2001/06/27 18:10:01  roger
// Remove displayMemory calls
//
// Revision 1.115  2001/06/27 15:29:35  roger
// isPending should follow getConnection before using the connection
//
// Revision 1.114  2001/06/27 15:14:47  roger
// Ensure freeStatement has isPending as next statement
//
// Revision 1.113  2001/06/27 15:06:41  roger
// Remove memory displays
//
// Revision 1.112  2001/06/26 23:39:30  dave
// more fixes and traces for final drop
//
// Revision 1.111  2001/06/26 21:38:40  roger
// Misc
//
// Revision 1.110  2001/06/26 15:37:57  roger
// More D.ebug to debug cleanup
//
// Revision 1.109  2001/06/26 15:07:56  roger
// More D.ebug to debug changes
//
// Revision 1.108  2001/06/24 20:15:27  dave
// syntax
//
// Revision 1.107  2001/06/24 19:53:51  dave
// removed the recconnect from remote and database
//
// Revision 1.106  2001/06/20 14:57:02  dave
// literal change
//
// Revision 1.105  2001/06/19 00:46:09  dave
// fixed getnerate
//
// Revision 1.104  2001/06/18 20:26:41  dave
// accident on remotedatabase save.. caused conflict
//
// Revision 1.103  2001/06/18 19:51:11  dave
// Fix from .html to .htm
//
// Revision 1.102  2001/06/18 19:39:36  roger
// strInstanceName not available in static method (main)
//
// Revision 1.101  2001/06/16 06:17:25  roger
// Include instance name on Ready
//
// Revision 1.100  2001/06/16 04:41:25  roger
// More D.ebug to debug changes
//
// Revision 1.99  2001/06/16 00:54:52  roger
// Use debug, test, display from dbCurrent
//
// Revision 1.98  2001/06/15 22:35:58  roger
// Added debug, display, displayMemory methods
//
// Revision 1.97  2001/06/15 22:14:14  roger
// D.ebug and T.est for Remote
//
// Revision 1.96  2001/06/06 23:31:18  dave
// syntax
//
// Revision 1.95  2001/06/02 20:16:13  dave
// added boolean to deactivatedList for unrestricted case
//
// Revision 1.94  2001/06/02 19:31:23  dave
// removing system.out's and messages
//
// Revision 1.93  2001/06/02 17:49:49  dave
// capping the number of Entities a client can get to 200 through
// RMI
//
// Revision 1.92  2001/06/01 17:22:01  dave
// changed PDHAttribute to string on getHelpText parm
//
// Revision 1.91  2001/05/30 17:45:02  dave
// attempts to fix helptext
//
// Revision 1.90  2001/05/11 15:41:35  dave
// fix to return results 1 from 2913
//
// Revision 1.89  2001/05/09 23:47:15  dave
// moved clearcache to databasepool (where it belongs)
//
// Revision 1.88  2001/05/09 21:18:13  dave
// clearing cache file
//
// Revision 1.87  2001/05/07 22:51:39  dave
// Removed the reconnect from a middleware exception from
// navigate .. only rconnect on runtimes and SQLExceptions
//
// Revision 1.86  2001/05/07 22:25:48  dave
// removed reconnect logic when a passwordexception is thrown
//
// Revision 1.85  2001/05/07 21:19:22  dave
// basic syntax error catching
//
// Revision 1.84  2001/05/07 21:02:02  dave
// clean up on changePassword
//
// Revision 1.83  2001/05/07 20:35:50  dave
// first attempt at setting up the clearing of the Object Cache
//
// Revision 1.82  2001/05/04 20:09:12  dave
// LockException added to deactivateEntity
//
// Revision 1.81  2001/05/02 23:31:05  dave
// final adjustments for LinkException
//
// Revision 1.80  2001/05/02 22:27:49  dave
// fix for LinkException
//
// Revision 1.79  2001/05/02 20:44:58  dave
// LinkUI Fixes Wave II
//
// Revision 1.78  2001/05/02 20:10:56  dave
// cleaned up linkUI and added 100 transaction limit on the
// remote side to not allow for big transactions through the UI
//
// Revision 1.77  2001/04/28 20:57:10  dave
// added key '=' sign for compile
//
// Revision 1.76  2001/04/28 20:30:27  dave
// added base logic to create navigate objects from pdhitems
//
// Revision 1.75  2001/04/26 21:25:59  roger
// Clean up
//
// Revision 1.74  2001/04/25 15:40:57  dave
// Login exception removed for changePassword method(s)
//
// Revision 1.73  2001/04/23 20:17:09  dave
// Clean up and trace statements .. more cleanup
//
// Revision 1.72  2001/04/23 15:10:51  dave
// changed PDHAttribute to PDHMetaAttribute in getHelpText
//
// Revision 1.71  2001/04/23 02:18:39  dave
// final syntax fixs for getHelpText
//
// Revision 1.70  2001/04/23 02:02:28  dave
// first pass at getHelpText
//
// Revision 1.69  2001/04/23 01:07:46  dave
// syntax error fixes for trace enhancements
//
// Revision 1.68  2001/04/23 00:32:45  dave
// fixed numerous syntax errors around the variable strTraceBase
//
// Revision 1.67  2001/04/22 23:42:59  dave
// Massive Cleanup and debug reporting for future troubleshooting
//
// Revision 1.66  2001/04/19 18:49:48  dave
// fixed some syntax errors
//
// Revision 1.65  2001/04/19 18:24:58  dave
// added assesions, exception handling and logging
//
// Revision 1.64  2001/04/18 23:07:16  dave
// Added a tremendous ammount of debug statements in the
// navigate method (along with connection id dumps in the debug
// statements in hopes to trap an error where the connection
// is not being freed properly
//
// Revision 1.63  2001/04/18 17:38:01  dave
// more trace and exception handling for putblob
//
// Revision 1.62  2001/04/18 15:58:51  dave
// added extra exception handling for putBlob for tracking system resource limitations
//
// Revision 1.61  2001/04/12 14:54:11  dave
// assertion checks on navigate. Misc trapping of exceptions
//
// Revision 1.60  2001/04/11 23:30:54  dave
// syntax fixes and ensuring _ is used for parm variables
//
// Revision 1.59  2001/04/11 23:06:10  dave
// added multiple parent option on copyLink along with copy count
//
// Revision 1.58  2001/04/11 21:35:00  dave
// added more exception checking to the RMI create virtual entity
//
// Revision 1.57  2001/04/10 20:46:18  dave
// fixed more blimy syntax errors
//
// Revision 1.56  2001/04/10 19:56:44  dave
// added a persistence flag to the clearsoftlock call(s)
//
// Revision 1.55  2001/04/10 19:49:04  dave
// Added RemoteMethods for getAllSoftLocksforOPENID
//
// Revision 1.54  2001/04/10 16:20:56  dave
// added more exception trapping to the navigate method
//
// Revision 1.53  2001/04/10 04:28:16  dave
// First Pass at adding persistence to the create softlock process
//
// Revision 1.52  2001/04/09 22:41:28  dave
// Excetion to Exception
//
// Revision 1.51  2001/04/09 21:15:44  dave
// Added exception checking for change password
//
// Revision 1.50  2001/04/05 21:51:09  dave
// syntax fixes
//
// Revision 1.49  2001/04/05 21:22:17  dave
// syntax fixes
//
// Revision 1.48  2001/04/05 20:37:58  dave
// change for clearAllSoftLocksbyOperator
//
// Revision 1.47  2001/04/04 22:52:05  dave
// parm fix for generateConfigurableOffering call
//
// Revision 1.46  2001/04/04 22:30:56  dave
// fixed bad parm object on generateConfigurableOfferings
//
// Revision 1.45  2001/04/04 22:03:26  gregg
// change for genConfigOffering
//
// Revision 1.44  2001/04/03 04:24:58  dave
// fixed syntax errors..
//
// Revision 1.43  2001/04/03 03:56:39  dave
// fix for RMI entityUpdate call
//
// Revision 1.42  2001/04/02 21:15:33  dave
// fixed putBlob for TranID using either the TranID from the ConnectionItem
// or the DUMMY_INT to ensure no hardcoding is involved
//
// Revision 1.41  2001/04/02 19:08:27  dave
// Show All users trace updates
//
// Revision 1.40  2001/03/30 23:13:46  dave
// trace for showAllUsers
//
// Revision 1.39  2001/03/30 21:02:42  dave
// Added base logic for showAllUsers
//
// Revision 1.38  2001/03/30 01:15:11  dave
// final errors for changepassword migration
//
// Revision 1.37  2001/03/30 00:45:48  dave
// More Syntax error fixes
//
// Revision 1.36  2001/03/30 00:27:07  dave
// first pass at syntax errors for changepassword
//
// Revision 1.35  2001/03/30 00:09:11  dave
// first attempt at migrating the change password logic
//
// Revision 1.34  2001/03/28 04:34:22  dave
// syntax errors for where used
//
// Revision 1.33  2001/03/28 04:14:30  dave
// stubbed out whereused function to return simple NavigateObject
//
// Revision 1.32  2001/03/27 04:16:20  dave
// Added stubs to middleware for moveLink
// (rename of cutpaste)
//
// Revision 1.31  2001/03/27 01:21:03  roger
// Misc formatting clean up
//
// Revision 1.30  2001/03/23 18:51:36  dave
// Clean up, parameter fixes for copyLink
//
// Revision 1.29  2001/03/23 18:26:26  dave
// Syntax errors for copyLink addressed
//
// Revision 1.28  2001/03/23 17:49:04  dave
// Clean up and syntax error fixes for copyLink
//
// Revision 1.27  2001/03/23 17:22:38  dave
// Based code for the copy/paste (changed to copyLink).. more to follow
//
// Revision 1.26  2001/03/22 08:23:14  dave
// Added foundation for deactivateUNDO in JDBC, RMI, and Interface
//
// Revision 1.25  2001/03/22 07:47:30  dave
// Added PDHInactiveEntityGroup as the class that gets returned from the deactivatedList method
//
// Revision 1.24  2001/03/21 21:43:20  dave
// Fixed Syntax Errors
//
// Revision 1.23  2001/03/21 21:16:42  roger
// Make the GBL####A SPs named GBL####
//
// Revision 1.22  2001/03/21 21:07:08  dave
// Added based code for deactivateList
//
// Revision 1.21  2001/03/21 18:09:13  roger
// Added branding code
//
// Revision 1.20  2001/03/21 15:30:51  roger
// Put v2.2.3 link method back in temporarily for eTS
//
// Revision 1.19  2001/03/21 01:11:14  roger
// Misc clean up
//
// Revision 1.18  2001/03/21 01:07:31  roger
// Misc clean up
//
// Revision 1.17  2001/03/21 01:04:47  roger
// Removed main method try wrapper
//
// Revision 1.16  2001/03/20 21:49:32  dave
// renamed Link to LinkUI
//
// Revision 1.15  2001/03/20 21:36:52  roger
// Removed ConnectionItem from declarePackage
//
// Revision 1.14  2001/03/20 21:06:44  roger
// Include ConnectionItem to declarePackage for link method
//
// Revision 1.13  2001/03/19 23:19:57  roger
// Wrapped main method in try block
//
// Revision 1.12  2001/03/19 23:08:05  roger
// Clean up alignment
//
// Revision 1.11  2001/03/16 22:09:57  roger
// Restored createVirtualEntity from v2.2.3
//
// Revision 1.10  2001/03/16 18:32:39  roger
// Included updatePDH method
//
// Revision 1.9  2001/03/16 16:59:49  roger
// Changed WHY2028 to GBL2028A
//
// Revision 1.8  2001/03/16 16:30:24  roger
// Needed import statement for eTS
//
// Revision 1.7  2001/03/16 16:28:54  roger
// Put space after comma
//
// Revision 1.6  2001/03/16 04:43:41  roger
// Included eTS methods from v2.2.3
//
// Revision 1.5  2001/03/16 03:18:49  roger
// Added Log keyword
//
package COM.ibm.opicmpdh.middleware;

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.*;
import java.util.Enumeration;
//import COM.ibm.opicmpdh.objects.*;
import COM.ibm.opicmpdh.middleware.Connections;
import COM.ibm.opicmpdh.middleware.ConnectionInformation;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.opicmpdh.translation.*;
import COM.ibm.eannounce.objects.*;
import javax.mail.internet.InternetAddress;

/**
 * This is the server side (implementation) of the RemoteDatabase
 * @version 2020-06-15-05.16.43.622000
 * @see Database
 * @see DatabasePool
 * @see ReturnData
 */
public class RemoteDatabase extends UnicastRemoteObject implements RemoteDatabaseInterface, Runnable {


  public static final String c_strBuildTimeStamp = "2020-06-15-05.16.43.622000";

  // Class initialization code
  static {
      D.isplay("e-announce Remote Database <" + c_strBuildTimeStamp + ">");
  }

  // Class constants
  private static final String CLASS_BRAND = "$Id: remotedbmethods.txt,v 1.477 2014/06/26 19:09:20 wendy Exp $";

  // Class variables
  //private static int c_iSleepTime = 0;
  //private static int c_iInstanceCount = 0;
  private static int c_iMaxConnections = 0;
  private static DatabasePool c_dbpOPICM = new DatabasePool();
  //private static boolean c_bTrace = true;
  //private static String c_strNow = null;
  //private static String c_strForever = null;
  //private static String c_strEpoch = null;
  private static int c_iHousekeepingPause = 0;
  private static Thread c_thShutdown = null;
  private static String c_strShutdownTimeOfDay = null;
  private static String c_strInstancePurpose = null;
  private static String c_strSocketType = MiddlewareServerProperties.getSocketType();
  private static String c_strSSLPassPhrase = MiddlewareServerProperties.getSSLPassPhrase();
  private static String c_strSSLKeyStore = MiddlewareServerProperties.getSSLKeyStore();
  private static String c_strSSLTrustStore = MiddlewareServerProperties.getSSLTrustStore();
  private static int c_iRmiPort = MiddlewareServerProperties.getRMISerialPortNumber();
  private static Connections c_Connections = new Connections();


  //Instance variables
  private Database dbConsole = null;
 // private D m_display = new D();
  private String m_strInstanceName = "iname";

  /**
   * Creates the server-side RemoteDatabaseObject, binds to the RMI registry, tests (optional) by making local call to database
   */
  public static void main(String[] arg) {

      // Throw out the init file to keep connections at bay...
      initFileCreate();

      // System.setSecurityManager(new RMISecurityManager());
      c_iMaxConnections = MiddlewareServerProperties.getDatabaseConnections();
      c_iHousekeepingPause = MiddlewareServerProperties.getConnectionCheckerQuantum();

      // Redirect output if requested
      String strLogFileName = MiddlewareServerProperties.getLogFileName();
      if (strLogFileName.length() > 0) {
          D.ebugSetOut(strLogFileName);
      }
      D.ebugLevel(MiddlewareServerProperties.getDebugTraceLevel());
      D.ebug(MiddlewareServerProperties.getTrace());
      D.ebug(D.EBUG_DETAIL, "(RemoteDatabase) tracing enabled");

      D.ebug(D.EBUG_INFO, "Verify connection: " + MiddlewareServerProperties.getTestConnect());
      D.isplay("Using " + c_iMaxConnections + " " + ((c_iMaxConnections == 1) ? "connection" : "connections") + " to DB2");

      c_strShutdownTimeOfDay = MiddlewareServerProperties.getShutdownTimeOfDay();

      D.ebug(D.EBUG_INFO, "Shutdown every day at: " + c_strShutdownTimeOfDay);

      c_strInstancePurpose = MiddlewareServerProperties.getInstancePurpose();

      D.ebug(D.EBUG_INFO, "Instance purpose: " + c_strInstancePurpose);

      D.ebug(D.EBUG_INFO, "Socket Type: " + c_strSocketType);
      D.ebug(D.EBUG_INFO, "SSL PassPhrase: " + c_strSSLPassPhrase);
      D.ebug(D.EBUG_INFO, "SSL KeyStore File Name: " + c_strSSLKeyStore);
      D.ebug(D.EBUG_INFO, "SSL TrustStore File Name: " + c_strSSLTrustStore);

      // Create and bind the object
      System.getProperties().put("java.rmi.server.hostname", MiddlewareServerProperties.getServerBindIpAddress());

      // Lets set up all the RMI settings that can be set.. from the MiddlewareServerProperties Obejct
      System.setProperty("javax.net.ssl.trustStore", c_strSSLTrustStore);

      // Should we log rmi calls?
      System.setProperty("java.rmi.server.logCalls", MiddlewareServerProperties.getJavaRMIServerLogCalls());
      System.setProperty("sun.rmi.log.debug", MiddlewareServerProperties.getSunRMILogDebug());
      System.setProperty("sun.rmi.server.exceptionTrace", MiddlewareServerProperties.getSunRMIServerExceptionTrace());
      System.setProperty("sun.rmi.transport.tcp.logLevel", MiddlewareServerProperties.getSunRMITransportTCPLogLevel());
      System.setProperty("sun.rmi.transport.logLevel", MiddlewareServerProperties.getSunRMITransportLogLevel());
      System.setProperty("sun.rmi.transport.tcp.readTimeout", MiddlewareServerProperties.getSunRMITransportTCPReadTimeout());
      //System.setProperty("sun.rmi.transport.connectionTimeout", MiddlewareClientProperties.getSunRMITransportConnectionTimeout());
      System.setProperty("sun.rmi.transport.tcp.noConnectionPool", MiddlewareServerProperties.getSunRMITransportTCPNoConnectionPool());
      System.setProperty("sun.rmi.transport.tcp.connectionPool", MiddlewareServerProperties.getSunRMITransportTCPConnectionPool());
      System.setProperty("sun.rmi.dgc.logLevel", MiddlewareServerProperties.getSunRMIDGCLogLevel());
      System.setProperty("java.rmi.dgc.leaseValue", MiddlewareServerProperties.getJavaRMIDGCLeaseValue());
      System.setProperty("sun.rmi.dgc.server.gcInterval", MiddlewareServerProperties.getSunRMIDGCServerGCInterval());

      // display All System Properties
      System.getProperties().list(System.out);

      try {
          D.ebug(D.EBUG_INFO, "Creating a registry for RMI object(s) on port " + MiddlewareServerProperties.getServerBindPort());
          LocateRegistry.createRegistry(Integer.parseInt(MiddlewareServerProperties.getServerBindPort()));
      } catch (Exception x) {
          D.ebug("Error creating registry: " + x);
      }

      try {
          D.ebug(D.EBUG_INFO, "Binding " + MiddlewareServerProperties.getDatabaseObjectName() + " to ip address " + MiddlewareServerProperties.getServerBindIpAddress() + ":" + MiddlewareServerProperties.getServerBindPort());
          Naming.rebind("rmi://" + MiddlewareServerProperties.getServerBindIpAddress() + ":" + MiddlewareServerProperties.getServerBindPort() + "/" + MiddlewareServerProperties.getDatabaseObjectName(), new RemoteDatabase());
          D.ebug(D.EBUG_INFO, "Bind was successful");
      } catch (java.rmi.ConnectException cx) {
          D.ebug(D.EBUG_ERR, "There is no registry for the MIDDLEWARE server: " + cx);
          System.exit(-1);
      } catch (java.net.MalformedURLException mux) {
          D.ebug(D.EBUG_ERR, "Can't bind the remote object: " + mux);
          System.exit(-1);
      } catch (java.rmi.RemoteException rx) {
          D.ebug(D.EBUG_ERR, "Can't create the remote object -or- registry: " + rx);
          System.exit(-1);
      }

      // Prepare the DatabasePool for use
      c_dbpOPICM.init();
  }

  /**
   * Create the <code>RemoteDatabase</code>
   */
  public RemoteDatabase() throws RemoteException {

      //Use custom sockets to support SSL and other features in the future
      super(c_iRmiPort, new CustomClientSocketFactory(c_strSocketType), new CustomServerSocketFactory(c_strSocketType, c_strSSLPassPhrase, c_strSSLKeyStore));

      m_strInstanceName = MiddlewareServerDynamicProperties.getInstanceName();

      //Setup and start the shutdown thread to gracefully shutdown the server
      if (c_thShutdown == null) {
          try {
              c_thShutdown = new Thread(this);

              c_thShutdown.setDaemon(true);
              c_thShutdown.setPriority(Thread.MIN_PRIORITY);
              c_thShutdown.start();
          } catch (Exception x) {
              debug(D.EBUG_ERR, "Exception creating shutdown thread " + x);
              System.exit(-1);
          }
      }
  }

  /**
   * Properties have changed, change appropriate behaviours
   */
  protected final static void reloadProperties() {
      c_iHousekeepingPause = MiddlewareServerProperties.getConnectionCheckerQuantum();
  }

  /**
   * Run the shutdown thread
   */
  public final void run() {

      boolean bForever = true;

      while (bForever) {
          try {

              //Sleep a bit
              Thread.sleep(c_iHousekeepingPause);
          } catch (Exception x) {}

          //Is time of day shutdown enabled?
          if (c_strShutdownTimeOfDay.length() == 4) {
              debug(D.EBUG_SPEW, "middleware shutdown is scheduled for " + c_strShutdownTimeOfDay);

              SimpleDateFormat sdfNow = new SimpleDateFormat("HHmm");
              String strNow = sdfNow.format(new java.util.Date());

              //Is it time to shutdown?
              if (strNow.compareTo(c_strShutdownTimeOfDay) == 0) {
                  this.shutdown();
                  this.shut();
                  display("middleware scheduled shutdown");
                  display("goodbye!");
                  System.exit(0);
              }
          }

          //Does a shutdown/cycle file exist?
          File f1 = new File("mwshut");
          File f2 = new File("mwcycle");

          if (f1.exists() || f2.exists()) {
              display("Shutting down the middleware");
              this.shutdown();

              //Exit
              display("middleware has been shutdown");
              display("goodbye!");
              System.exit(0);
          }
      }
  }

  /**
   * Shutdown procedure
   */
  private final void shutdown() {
      c_dbpOPICM.status();

      // Unbind the object to stop new users from connecting
      try {
          Naming.unbind(MiddlewareServerProperties.getDatabaseObjectName());
      } catch (Exception x) {}

      // Request the DatabasePool to shutdown
      c_dbpOPICM.shutdown();
  }

  /**
   * Console mode methods
   */
  private final String[] execute(String strCommand) {

      StringTokenizer tokCommand = new StringTokenizer(strCommand);
      String[] astrOutput = null;
      String strToken = null;
      Vector vctOutput = new Vector();

      debug(D.EBUG_DETAIL, "console command: " + strCommand);
      vctOutput.addElement(" ");

      strToken = tokCommand.nextToken();

      if (strToken.equalsIgnoreCase("users")) {
          for (Enumeration e = c_Connections.elements(); e.hasMoreElements(); ) {
 	  		  ConnectionInformation ci = (ConnectionInformation) e.nextElement();
              //System.out.println(ci);
              vctOutput.addElement(ci.toString());
          }
      } else if (strToken.equalsIgnoreCase("free")) {
          if (dbConsole != null) {
              vctOutput.addElement("freed connection [" + c_dbpOPICM.whichConnection(dbConsole) + "]");
              free();
          } else {
              vctOutput.addElement("You DO NOT have a database connection");
          }
      } else if (strToken.equalsIgnoreCase("reset")) {
          try {
              int iWhich = Integer.parseInt(tokCommand.nextToken());

              reset(iWhich);
          } catch (NoSuchElementException ex) {
              reset();
          } catch (NumberFormatException nx) {
              vctOutput.addElement("invalid argument");
          }
      } else if (strToken.equalsIgnoreCase("test")) {
          if (dbConsole != null) {
              try {
                  test(dbConsole);
              } catch (Exception x) {
                  vctOutput.addElement("test failed" + x);
              }
          } else {
              vctOutput.addElement("You DO NOT have a database connection");
          }
      } else if (strToken.equalsIgnoreCase("reconnect")) {
          try {
              int iWhich = Integer.parseInt(tokCommand.nextToken());

              reconnect(iWhich);
          } catch (NoSuchElementException ex) {
              vctOutput.addElement("expected argument");
          } catch (NumberFormatException nx) {
              vctOutput.addElement("invalid argument");
          }
      } else if (strToken.equalsIgnoreCase("get")) {
          try {
              dbConsole = get();

              int iWhich = c_dbpOPICM.whichConnection(dbConsole);

              vctOutput.addElement("Acquired connection [" + iWhich + "]");
          } catch (MiddlewareWaitTimeoutException x) {
              vctOutput.addElement("Can't get a connection");
          }
      } else if (strToken.equalsIgnoreCase("load")) {
          if (tokCommand.nextToken().equalsIgnoreCase("parm")) {
              loadParms();
          } else {
              vctOutput.addElement("invalid argument");
          }
      } else if (strToken.equalsIgnoreCase("reset")) {
          reset();
          vctOutput.addElement("Middleware will reset on next PULSE");
      } else if (strToken.equalsIgnoreCase("shut")) {
          shut();
          vctOutput.addElement("Middleware will shut on next PULSE");
      } else if (strToken.equalsIgnoreCase("cycle")) {
          cycle();
          vctOutput.addElement("Middleware will cycle on next PULSE");
      } else if (strToken.equalsIgnoreCase("stat")) {
          stat();
      } else if (strToken.equalsIgnoreCase("memory") || strToken.equalsIgnoreCase("mem")) {
          vctOutput.addElement(memory());
      } else if (strToken.equalsIgnoreCase("version") || strToken.equalsIgnoreCase("ver")) {
          vctOutput.addElement(version());
      } else if (strToken.equalsIgnoreCase("?") || strToken.equalsIgnoreCase("help")) {
          vctOutput.addElement(help());
      } else {
          vctOutput.addElement("invalid command");
      }

      astrOutput = new String[vctOutput.size()];

      for (int j = 0; j < vctOutput.size(); j++) {
          astrOutput[j] = (String) vctOutput.elementAt(j);

          debug(D.EBUG_DETAIL, "console output: " + astrOutput[j]);
      }

      return astrOutput;
  }

  // Pool related commands
  private final void free() {
      c_dbpOPICM.freeConnection(dbConsole);

      dbConsole = null;
  }
  private final void test(Database dbCurrent) throws Exception {
      ReturnStatus returnStatus = new ReturnStatus(0);

      dbCurrent.callGBL2028(returnStatus);
  }
  private final void reconnect(int iWhich) {
  }
  private final Database get() throws MiddlewareWaitTimeoutException {
      Database x = null;

      try {
          x = c_dbpOPICM.getConnection("console");
      } catch (MiddlewareShutdownInProgressException sx) {}

      return x;
  }
  /*private String setTraceBase(String _s, int _i) {
      return "RMI:" + _s;
  }

  // Database related commands
  private final void callproc() {
  }*/

  // General middleware commands
  private final String help() {
      return "get/test/free/reconnect #/reset #/shut/cycle/reset/mem/stat/ver/load parm/users/quit/exit/stop/help";
  }
  private final void loadParms() {
      MiddlewareServerProperties.reloadProperties();
  }
  private final void stat() {
      c_dbpOPICM.status();
  }
  private final void shut() {
      try {
          File f1 = new File("mwshut");
          FileWriter fw1 = new FileWriter(f1);
          D.isplay("Creating mwshut to change operational mode");
          fw1.write(' ');
          fw1.close();
      } catch (Exception x) {}
  }
  private final void cycle() {
      try {
          File f1 = new File("mwcycle");
          FileWriter fw1 = new FileWriter(f1);
          D.isplay("Creating mwcycle to change operational mode");
          fw1.write(' ');
          fw1.close();
      } catch (Exception x) {}
  }
  private static void initFileCreate() {
      try {
          File f1 = new File("mwinit");
          FileWriter fw1 = new FileWriter(f1);
          D.isplay("Creating mwinit to change operational mode");
          fw1.write(' ');
          fw1.close();
      } catch (Exception x) {}
  }

 /* private static void initFileRemove() {
      try {
          File f1 = new File("mwinit");
          if (f1.exists()) {
              D.isplay("Removing mwinit to change operational mode");
              f1.delete();
          }
      } catch (Exception x) {}
  }*/

  private final void reset(int iWhich) {
      c_dbpOPICM.reset(iWhich);
  }
  private final void reset() {
      try {
          File f1 = new File("mwreset");
          FileWriter fw1 = new FileWriter(f1);
          D.isplay("Creating mwreset to change operational mode");
          fw1.write(' ');
          fw1.close();
      } catch (Exception x) {}
  }
  private final String memory() {
      return D.etermineMemory();
  }
  private final String version() {

      try {
          String strVersion;
          RemoteDatabase rdb = new RemoteDatabase();

          strVersion = rdb.getVersion();
          rdb = null;

          return strVersion;
      } catch (RemoteException x) {
          return "unknown version";
      }
  }

  /**
   * Output display/debug message
   */
  public final void display(String _strMessage) {
      //D d = m_display;

      D.isplay(_strMessage);
  }

  /**
   * Output display/debug message
   */
  public final void debug(int _iDebugLevel, String _strMessage) {
      //D d = m_display;

      D.ebug(_iDebugLevel, _strMessage);
  }

  /**
   * Output display/debug message
   */
  public final void debug(String _strMessage) {
      //D d = m_display;

      D.ebug(_strMessage);
  }

  /**
   * Output memory usage
   */
  public final void displayMemory() {
      //D d = m_display;

      D.isplayMemory();
  }

  /**
   * A simplistic assertion feature
   */
  public final void test(boolean _bValue, String _strCaseDescription) throws MiddlewareRequestException {
      if (!_bValue) {
          display("Assertion failed: " + _strCaseDescription);

          throw new MiddlewareRequestException(_strCaseDescription);
      }
  }

  /**
   * Get the current server-based date/time
   * calls GBL2028
   * /
  private final void getNow(Database dbCurrent) throws MiddlewareException {

    //The stored procedure ReturnStatus
    ReturnStatus returnStatus = new ReturnStatus(-1);

    //The ResultSet
    ResultSet rs = null;

    //Information about exception location
    String strMethod = "getNow";
    String strSP = null;

    try {
      dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");

      strSP = "gbl2028";

      //Get the current time
      rs = dbCurrent.callGBL2028(returnStatus);

      rs.next();

      //And store in the class variables
      c_strNow = new String(rs.getString(1).trim());
      c_strForever = new String(rs.getString(2).trim());
      c_strEpoch = new String(rs.getString(3).trim());

      dbCurrent.debug(D.EBUG_DETAIL, "getNow:Now: " + c_strNow);
      dbCurrent.debug(D.EBUG_DETAIL, "getNow:Forever: " + c_strForever);
      dbCurrent.debug(D.EBUG_DETAIL, "getNow:Epoch: " + c_strEpoch);
      rs.close();

      rs = null;
    } catch (RuntimeException rx) {

      //DO NOT ROLLBACK
      dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);

      StringWriter writer = new StringWriter();

      rx.printStackTrace(new PrintWriter(writer));

      String x = writer.toString();

      dbCurrent.debug(D.EBUG_ERR, "" + x);
      dbCurrent.freeStatement();
      dbCurrent.isPending();

      throw new MiddlewareException("(" + strMethod + ") RuntimeException: " + rx);
    } catch (SQLException x) {

      //DO NOT ROLLBACK
      dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
      dbCurrent.freeStatement();
      dbCurrent.isPending();

      throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);
    } finally {

      //DO NOT FREE THE CONNECTION
      //Free any statement
      dbCurrent.freeStatement();
      dbCurrent.isPending();
      dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
    }
  }*/

  //The remote methods

  /**
   * @return the date/time this class was generated
   */
  public final String getVersion() throws RemoteException {
      return "2020-06-15-05.16.43.622000";
  }


  /*
   * Does the database have a connection to a PDH?
   */
  public final boolean hasPDH() throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      String strMethod = "hasPDH";
      boolean bReturn = false;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);
          bReturn = dbCurrent.hasPDH();
          dbCurrent.freeStatement();
          dbCurrent.isPending();
          c_dbpOPICM.freeConnection(dbCurrent);

      } catch (RuntimeException rx) {
          StringWriter writer = new StringWriter();
          rx.printStackTrace(new PrintWriter(writer));
          String x = writer.toString();

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, strMethod + " RuntimeException trapped at: " + strMethod + " " + rx);
              dbCurrent.debug(D.EBUG_ERR, strMethod+ " " + x);
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete - error");
          }else{
              D.ebug(D.EBUG_ERR, strMethod + " RuntimeException trapped at: " + strMethod + " " + rx);
              D.ebug(D.EBUG_ERR, strMethod+ " " + x);
              D.ebug(D.EBUG_DETAIL, strMethod + " complete - error");
          }

          throw new MiddlewareException("RMI RuntimeException trapped at: " + strMethod + rx);

      } catch (MiddlewareException mx) {
          StringWriter writer = new StringWriter();
          mx.printStackTrace(new PrintWriter(writer));
          String x = writer.toString();

          if (dbCurrent !=null){
              dbCurrent.debug(D.EBUG_ERR, strMethod+ " MiddlewareException trapped at: " + strMethod + " " + mx);
              dbCurrent.debug(D.EBUG_ERR, strMethod + " " + x);
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete - error");
          }else{
              D.ebug(D.EBUG_ERR, strMethod+ " MiddlewareException trapped at: " + strMethod + " " + mx);
              D.ebug(D.EBUG_ERR, strMethod + " " + x);
              D.ebug(D.EBUG_DETAIL, strMethod + " complete - error");
          }

          throw mx;

      } catch (SQLException x) {

          if (dbCurrent != null){
              dbCurrent.debug(D.EBUG_ERR, strMethod + " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }

              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete - error");
          }else{
              D.ebug(D.EBUG_ERR, strMethod + " SQLException trapped at: " + strMethod + " " + x);
              D.ebug(D.EBUG_DETAIL, strMethod + " complete - error");
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      }

      dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
      return bReturn;
  }

  /*
   * Does the database have a connection to an ODS?
   */
  public final boolean hasODS() throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      String strMethod = "hasODS";
      boolean bReturn = false;

      try {
          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          D.ebug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);
          bReturn = dbCurrent.hasODS();
          dbCurrent.freeStatement();
          dbCurrent.isPending();
          c_dbpOPICM.freeConnection(dbCurrent);

      } catch (RuntimeException rx) {
          StringWriter writer = new StringWriter();
          rx.printStackTrace(new PrintWriter(writer));
          String x = writer.toString();

          if (dbCurrent != null){
              dbCurrent.debug(D.EBUG_ERR, strMethod + " RuntimeException trapped at: " + strMethod + " " + rx);
              dbCurrent.debug(D.EBUG_ERR, strMethod+ " " + x);
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete - error");
          }else{
              D.ebug(D.EBUG_ERR, strMethod + " RuntimeException trapped at: " + strMethod + " " + rx);
              D.ebug(D.EBUG_ERR, strMethod+ " " + x);
              D.ebug(D.EBUG_DETAIL, strMethod + " complete - error");
          }

          throw new MiddlewareException("RMI RuntimeException trapped at: " + strMethod + rx);

      } catch (MiddlewareException mx) {
          StringWriter writer = new StringWriter();
          mx.printStackTrace(new PrintWriter(writer));
          String x = writer.toString();

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, strMethod+ " MiddlewareException trapped at: " + strMethod + " " + mx);
              dbCurrent.debug(D.EBUG_ERR, strMethod + " " + x);
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete - error");
          }else{
              D.ebug(D.EBUG_ERR, strMethod+ " MiddlewareException trapped at: " + strMethod + " " + mx);
              D.ebug(D.EBUG_ERR, strMethod + " " + x);
              D.ebug(D.EBUG_DETAIL, strMethod + " complete - error");
          }

          throw mx;

      } catch (SQLException x) {
          if (dbCurrent != null){
              dbCurrent.debug(D.EBUG_ERR, strMethod + " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  D.ebug(D.EBUG_ERR, "rollback failed " + sx);
              }

              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete - error");
          }else{
              D.ebug(D.EBUG_ERR, strMethod + " SQLException trapped at: " + strMethod + " " + x);
              D.ebug(D.EBUG_DETAIL, strMethod + " complete - error");
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      }

      dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
      return bReturn;

  }

  /*
   * Retrieves a collection of MetaFlagAttributes that are all related by dependent flags
   */
  public final MetaFlagAttributeList getMetaFlagAttributeList(Profile _prof, String _strAttributeCode) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      String strMethod = "getMetaFlagAttributeList";
      MetaFlagAttributeList mfalReturn = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);
          mfalReturn = dbCurrent.getMetaFlagAttributeList(_prof, _strAttributeCode);

          dbCurrent.commit();
          dbCurrent.freeStatement();
          dbCurrent.isPending();
          c_dbpOPICM.freeConnection(dbCurrent);

      } catch (RuntimeException rx) {
          StringWriter writer = new StringWriter();
          rx.printStackTrace(new PrintWriter(writer));
          String x = writer.toString();

          if (dbCurrent != null){
              dbCurrent.debug(D.EBUG_ERR, strMethod + " RuntimeException trapped at: " + strMethod + " " + rx);
              dbCurrent.debug(D.EBUG_ERR, strMethod+ " " + x);
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete - error");
          }else{
              D.ebug(D.EBUG_ERR, strMethod + " RuntimeException trapped at: " + strMethod + " " + rx);
              D.ebug(D.EBUG_ERR, strMethod+ " " + x);
              D.ebug(D.EBUG_DETAIL, strMethod + " complete - error");
          }

          throw new MiddlewareException("RMI RuntimeException trapped at: " + strMethod + rx);

      } catch (MiddlewareException mx) {
          StringWriter writer = new StringWriter();
          mx.printStackTrace(new PrintWriter(writer));
          String x = writer.toString();

          if (dbCurrent != null){
              dbCurrent.debug(D.EBUG_ERR, strMethod+ " MiddlewareException trapped at: " + strMethod + " " + mx);
              dbCurrent.debug(D.EBUG_ERR, strMethod + " " + x);
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete - error");
          }else{
              D.ebug(D.EBUG_ERR, strMethod+ " MiddlewareException trapped at: " + strMethod + " " + mx);
              D.ebug(D.EBUG_ERR, strMethod + " " + x);
              D.ebug(D.EBUG_DETAIL, strMethod + " complete - error");
          }

          throw mx;

      } catch (SQLException x) {

          if (dbCurrent != null){
              dbCurrent.debug(D.EBUG_ERR, strMethod + " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }

              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete - error");
          }else{
              D.ebug(D.EBUG_ERR, strMethod + " SQLException trapped at: " + strMethod + " " + x);
              D.ebug(D.EBUG_DETAIL, strMethod + " complete - error");
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      }

      dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
      //Return the output 
      return mfalReturn;

  }

  /*
   * Generates a SerialHistoryGroup
   */
  public final SerialHistoryGroup getSerialHistoryGroup(Profile _prof, String _s) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      SerialHistoryGroup shgReturn = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getSerialHistoryGroup";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          shgReturn = dbCurrent.getSerialHistoryGroup(_prof, _s);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent != null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent != null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return shgReturn;
  }

  /**
   * reset Object Cache call
   */
  /*
  public final boolean resetObjectCache(Profile _prof) throws RemoteException, MiddlewareException {

    String strMethod = "resetObjectCache";

    try {
      debug(D.EBUG_DETAIL, strMethod + " transaction");
      debug(D.EBUG_DETAIL, "Need to verify that this is allowable");
      c_dbpOPICM.resetObjectCache();
    } catch (RuntimeException rx) {
      debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + " " + rx);

      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();

      debug(D.EBUG_ERR, "" + x);
      throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
    } finally {
      debug(D.EBUG_DETAIL, strMethod + " complete");
    }

    //Return the output
    return true;
  }
   */

  /**
   * getBlob transaction
   */
  public final COM.ibm.opicmpdh.objects.Blob getBlob(Profile _prof, String entityType, int entityID, String attributeCode) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      //Available connection to a database
      Database dbCurrent = null;
      COM.ibm.opicmpdh.objects.Blob blobNew = null;

      //Information about exception location
      String strMethod = "getBlob";
      String strSP = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          //this stuff s/b in jdbc method
          dbCurrent.test(_prof.getEnterprise() != null, "enterprise is null");
          dbCurrent.test(entityType != null, "entityType is null");
          dbCurrent.test(entityID > 0, "entityID <= 0");
          dbCurrent.test(attributeCode != null, "attributeCode is null");
          dbCurrent.test(_prof.getReadLanguage().getNLSID() > 0, "nlsID <= 0");
          dbCurrent.test(_prof.getValOn() != null, "valOn is null");
          dbCurrent.test(_prof.getEffOn() != null, "effOn is null");
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:Enterprise: " + _prof.getEnterprise());
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EntityType: " + entityType);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EntityID: " + entityID);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:AttributeCode: " + attributeCode);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:NLSID: " + _prof.getReadLanguage().getNLSID());
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:ValOn: " + _prof.getValOn());
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EffOn: " + _prof.getEffOn());

          strSP = "getBlob";
          blobNew = dbCurrent.getBlob(_prof, entityType, entityID, attributeCode);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");

          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent != null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  D.ebug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);
      } catch (Exception x) {
          logException(dbCurrent, strMethod, x, "Exception");
      } finally {
          if (dbCurrent != null){
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return blobNew;
  }

  /**
   * getBlob transaction that doesn't depend on nlsid
   */
  public final COM.ibm.opicmpdh.objects.Blob getBlob(Profile _prof, String entityType, int entityID, String attributeCode, int _nlsID) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      //Available connection to a database
      Database dbCurrent = null;
      COM.ibm.opicmpdh.objects.Blob blobNew = null;

      //Information about exception location
      String strMethod = "getBlob";
      String strSP = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          D.ebug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          //this stuff s/b in jdbc method
          dbCurrent.test(_prof.getEnterprise() != null, "enterprise is null");
          dbCurrent.test(entityType != null, "entityType is null");
          dbCurrent.test(entityID > 0, "entityID <= 0");
          dbCurrent.test(attributeCode != null, "attributeCode is null");
          dbCurrent.test(_nlsID > 0, "nlsID <= 0");
          dbCurrent.test(_prof.getValOn() != null, "valOn is null");
          dbCurrent.test(_prof.getEffOn() != null, "effOn is null");
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:Enterprise: " + _prof.getEnterprise());
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EntityType: " + entityType);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EntityID: " + entityID);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:AttributeCode: " + attributeCode);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:NLSID: " + _nlsID);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:ValOn: " + _prof.getValOn());
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EffOn: " + _prof.getEffOn());

          strSP = "getBlob";
          blobNew = dbCurrent.getBlob(_prof, entityType, entityID, attributeCode, _nlsID);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");

          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent != null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);
      } catch (Exception x) {
          StringWriter writer = new StringWriter();
          x.printStackTrace(new PrintWriter(writer));

          String y = writer.toString();

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " Exception trapped at: " + strMethod + " " + x+" "+y);
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " - complete - error");
          }else{
              D.ebug(D.EBUG_ERR, " Exception trapped at: " + strMethod + " " + x+" "+y);
              D.ebug(D.EBUG_DETAIL, strMethod + " - complete - error");
          }
      } finally {
          if (dbCurrent!=null){
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return blobNew;
  }

  /**
   * putBlob transaction
   */
  public final ReturnDataResultSetGroup putBlob(Profile _prof, COM.ibm.opicmpdh.objects.Blob blobAttribute) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      //Dummy return cause void not good with RMI
      ReturnDataResultSetGroup rdrsgDummyReturn = new ReturnDataResultSetGroup();

      //Available connection to a database
      Database dbCurrent = null;

      //Information about exception location
      String strMethod = "putBlob";
      String strSP = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          //This stuff s/b in jdbc method
          dbCurrent.test(_prof.getEnterprise() != null, "putBlob:enterprise is null");
          dbCurrent.debug("putBlob:enterprise:length = " + _prof.getEnterprise().length());
          dbCurrent.test(_prof.getEnterprise().length() <= 8, "putBlob:enterprise length not <= 8 [passed length=" + _prof.getEnterprise().length() + "]");
          dbCurrent.test(_prof.getOPWGID() > 0, "putBlob:OPWGID <= 0");
          dbCurrent.test(blobAttribute != null, "putBlob:blobAttribute is null");
          dbCurrent.debug(D.EBUG_DETAIL, "putBlob:Enterprise: " + _prof.getEnterprise());
          dbCurrent.debug(D.EBUG_DETAIL, "putBlob:OPWGID: " + _prof.getOPWGID());

          strSP = "putBlob";

          dbCurrent.test(blobAttribute.getEntityType() != null, "putBlob:blobAttribute.getEntityType() is null");
          dbCurrent.debug("putBlob:blobAttribute.getEntityType():length = " + blobAttribute.getEntityType().length());
          dbCurrent.test(blobAttribute.getEntityType().length() <= 32, "putBlob:blobAttribute.getEntityType() length not <= 32 [passed length=" + blobAttribute.getEntityType().length() + "]");
          dbCurrent.test(blobAttribute.getEntityID() > 0, "putBlob:getEntityID() <= 0");
          dbCurrent.test(blobAttribute.getAttributeCode() != null, "putBlob:blobAttribute.getAttributeCode() is null");
          dbCurrent.debug("putBlob:blobAttribute.getAttributeCode():length = " + blobAttribute.getAttributeCode().length());
          dbCurrent.test(blobAttribute.getAttributeCode().length() <= 32, "putBlob:blobAttribute.getAttributeCode() length not <= 32 [passed length=" + blobAttribute.getAttributeCode().length() + "]");
          dbCurrent.test(blobAttribute.getBlobExtension() != null, "putBlob:blobAttribute.getBlobExtension() is null");
          dbCurrent.debug("putBlob:blobAttribute.getBlobExtension:length = " + blobAttribute.getBlobExtension().length());
          dbCurrent.test(blobAttribute.getBlobExtension().length() <= 32, "putBlob:blobAttribute.getBlobExtension() length not <= 32 [passed length=" + blobAttribute.getBlobExtension().length() + "]");
          dbCurrent.test(blobAttribute.getNLSID() > 0, "putBlob:getNLSID() <= 0");
          dbCurrent.test(blobAttribute.getControlBlock() != null, "putBlob:blobAttribute.getControlBlock() is null");
          dbCurrent.test(blobAttribute.getControlBlock().getEffFrom() != null, "putBlob:blobAttribute.getControlBlock().getEffFrom() is null");
          dbCurrent.debug("putBlob:blobAttribute.getControlBlock().getEffFrom():length = " + blobAttribute.getControlBlock().getEffFrom().length());
          dbCurrent.test(blobAttribute.getControlBlock().getEffFrom().length() <= 26, "putBlob:blobAttribute.getControlBlock().getEffFrom() length not <= 26 [passed length=" + blobAttribute.getControlBlock().getEffFrom().length() + "]");
          dbCurrent.test(Validate.isoDate(blobAttribute.getControlBlock().getEffFrom()) == true, "putBlob:blobAttribute.getControlBlock().getEffFrom() is not a valid ISO date [" + blobAttribute.getControlBlock().getEffFrom() + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
          dbCurrent.test(blobAttribute.getControlBlock().getEffTo() != null, "putBlob:blobAttribute.getControlBlock().getEffTo() is null");
          dbCurrent.debug("putBlob:blobAttribute.getControlBlock().getEffTo():length = " + blobAttribute.getControlBlock().getEffTo().length());
          dbCurrent.test(blobAttribute.getControlBlock().getEffTo().length() <= 26, "putBlob:blobAttribute.getControlBlock().getEffTo() length not <= 26 [passed length=" + blobAttribute.getControlBlock().getEffTo().length() + "]");
          dbCurrent.test(Validate.isoDate(blobAttribute.getControlBlock().getEffTo()) == true, "putBlob:blobAttribute.getControlBlock().getEffTo() is not a valid ISO date [" + blobAttribute.getControlBlock().getEffTo() + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
          dbCurrent.test(blobAttribute.getBAAttributeValue() != null, "putBlob:blobAttribute.getBAAttributeValue() is null");
          dbCurrent.putBlob(_prof, blobAttribute.getEntityType(), blobAttribute.getEntityID(), blobAttribute.getAttributeCode(), blobAttribute.getBlobExtension(), blobAttribute.getControlBlock().getEffFrom(), blobAttribute.getControlBlock().getEffTo(), blobAttribute.getBAAttributeValue());
          dbCurrent.commit();
      } catch (RuntimeException rx) {
          StringWriter writer = new StringWriter();
          rx.printStackTrace(new PrintWriter(writer));
          String x = writer.toString();

          if (dbCurrent !=null){
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }

              dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + " " + rx);
              dbCurrent.debug(D.EBUG_ERR, "" + x);
          }else{
              D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + " " + rx);
              D.ebug(D.EBUG_ERR, "" + x);
          }

          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");

          throw mx;
      } catch (SQLException x) {
          if (dbCurrent !=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent !=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return rdrsgDummyReturn;
  }

  /**
   * putBlob transaction that updates the record if it exists, insert into the table if it doesn't
   */
  public final ReturnDataResultSetGroup putBlob(Profile _prof, COM.ibm.opicmpdh.objects.Blob blobAttribute, int _nlsID) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      //Dummy return cause void not good with RMI
      ReturnDataResultSetGroup rdrsgDummyReturn = new ReturnDataResultSetGroup();

      //Available connection to a database
      Database dbCurrent = null;

      //Information about exception location
      String strMethod = "putBlob";
      String strSP = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          //This stuff s/b in jdbc method
          dbCurrent.test(_prof.getEnterprise() != null, "putBlob:enterprise is null");
          dbCurrent.debug("putBlob:enterprise:length = " + _prof.getEnterprise().length());
          dbCurrent.test(_prof.getEnterprise().length() <= 8, "putBlob:enterprise length not <= 8 [passed length=" + _prof.getEnterprise().length() + "]");
          dbCurrent.test(_prof.getOPWGID() > 0, "putBlob:OPWGID <= 0");
          dbCurrent.test(blobAttribute != null, "putBlob:blobAttribute is null");
          dbCurrent.debug(D.EBUG_DETAIL, "putBlob:Enterprise: " + _prof.getEnterprise());
          dbCurrent.debug(D.EBUG_DETAIL, "putBlob:OPWGID: " + _prof.getOPWGID());

          strSP = "putBlob";

          dbCurrent.test(blobAttribute.getEntityType() != null, "putBlob:blobAttribute.getEntityType() is null");
          dbCurrent.debug("putBlob:blobAttribute.getEntityType():length = " + blobAttribute.getEntityType().length());
          dbCurrent.test(blobAttribute.getEntityType().length() <= 32, "putBlob:blobAttribute.getEntityType() length not <= 32 [passed length=" + blobAttribute.getEntityType().length() + "]");
          dbCurrent.test(blobAttribute.getEntityID() > 0, "putBlob:getEntityID() <= 0");
          dbCurrent.test(blobAttribute.getAttributeCode() != null, "putBlob:blobAttribute.getAttributeCode() is null");
          dbCurrent.debug("putBlob:blobAttribute.getAttributeCode():length = " + blobAttribute.getAttributeCode().length());
          dbCurrent.test(blobAttribute.getAttributeCode().length() <= 32, "putBlob:blobAttribute.getAttributeCode() length not <= 32 [passed length=" + blobAttribute.getAttributeCode().length() + "]");
          dbCurrent.test(blobAttribute.getBlobExtension() != null, "putBlob:blobAttribute.getBlobExtension() is null");
          dbCurrent.debug("putBlob:blobAttribute.getBlobExtension:length = " + blobAttribute.getBlobExtension().length());
          dbCurrent.test(blobAttribute.getBlobExtension().length() <= 32, "putBlob:blobAttribute.getBlobExtension() length not <= 32 [passed length=" + blobAttribute.getBlobExtension().length() + "]");
          dbCurrent.test(blobAttribute.getNLSID() > 0, "putBlob:getNLSID() <= 0");
          dbCurrent.test(blobAttribute.getControlBlock() != null, "putBlob:blobAttribute.getControlBlock() is null");
          dbCurrent.test(blobAttribute.getControlBlock().getEffFrom() != null, "putBlob:blobAttribute.getControlBlock().getEffFrom() is null");
          dbCurrent.debug("putBlob:blobAttribute.getControlBlock().getEffFrom():length = " + blobAttribute.getControlBlock().getEffFrom().length());
          dbCurrent.test(blobAttribute.getControlBlock().getEffFrom().length() <= 26, "putBlob:blobAttribute.getControlBlock().getEffFrom() length not <= 26 [passed length=" + blobAttribute.getControlBlock().getEffFrom().length() + "]");
          dbCurrent.test(Validate.isoDate(blobAttribute.getControlBlock().getEffFrom()) == true, "putBlob:blobAttribute.getControlBlock().getEffFrom() is not a valid ISO date [" + blobAttribute.getControlBlock().getEffFrom() + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
          dbCurrent.test(blobAttribute.getControlBlock().getEffTo() != null, "putBlob:blobAttribute.getControlBlock().getEffTo() is null");
          dbCurrent.debug("putBlob:blobAttribute.getControlBlock().getEffTo():length = " + blobAttribute.getControlBlock().getEffTo().length());
          dbCurrent.test(blobAttribute.getControlBlock().getEffTo().length() <= 26, "putBlob:blobAttribute.getControlBlock().getEffTo() length not <= 26 [passed length=" + blobAttribute.getControlBlock().getEffTo().length() + "]");
          dbCurrent.test(Validate.isoDate(blobAttribute.getControlBlock().getEffTo()) == true, "putBlob:blobAttribute.getControlBlock().getEffTo() is not a valid ISO date [" + blobAttribute.getControlBlock().getEffTo() + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
          dbCurrent.test(blobAttribute.getBAAttributeValue() != null, "putBlob:blobAttribute.getBAAttributeValue() is null");
          dbCurrent.putBlob(_prof, blobAttribute.getEntityType(), blobAttribute.getEntityID(), blobAttribute.getAttributeCode(), blobAttribute.getBlobExtension(), blobAttribute.getControlBlock().getEffFrom(), blobAttribute.getControlBlock().getEffTo(), blobAttribute.getBAAttributeValue(), _nlsID);
          dbCurrent.commit();
      } catch (RuntimeException rx) {
          StringWriter writer = new StringWriter();
          rx.printStackTrace(new PrintWriter(writer));

          String x = writer.toString();

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + " " + rx);
              dbCurrent.debug(D.EBUG_ERR, "" + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + " " + rx);
              D.ebug(D.EBUG_ERR, "" + x);
          }

          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");

          throw mx;
      } catch (SQLException x) {

          if (dbCurrent !=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent !=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return rdrsgDummyReturn;
  }

  /**
   * deactivateBlob transaction
   */
  public ReturnDataResultSetGroup deactivateBlob(Profile _prof, String _strEntityType, int _iEntityID, String _strAttributeCode, int _iNLSID) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      //Dummy return cause void not good with RMI
      ReturnDataResultSetGroup rdrsgDummyReturn = new ReturnDataResultSetGroup();

      //Available connection to a database
      Database dbCurrent = null;

      //Information about exception location
      String strMethod = "deactivateBlob";
      String strSP = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          //This stuff s/b in jdbc method
          dbCurrent.test(_prof.getEnterprise() != null, "deactivateBlob:enterprise is null");
          dbCurrent.debug("deactivateBlob:enterprise:length = " + _prof.getEnterprise().length());
          dbCurrent.test(_prof.getEnterprise().length() <= 8, "deactivateBlob:enterprise length not <= 8 [passed length=" + _prof.getEnterprise().length() + "]");
          dbCurrent.test(_prof.getOPWGID() > 0, "deactivateBlob:OPWGID <= 0");
          dbCurrent.debug(D.EBUG_DETAIL, "deactivateBlob:Enterprise: " + _prof.getEnterprise());
          dbCurrent.debug(D.EBUG_DETAIL, "deactivateBlob:OPWGID: " + _prof.getOPWGID());

          strSP = "deactivateBlob";

          dbCurrent.test(_strEntityType != null, "deactivateBlob:_strEntityType is null");
          dbCurrent.debug("deactivateBlob:_strEntityType:length = " + _strEntityType.length());
          dbCurrent.test(_strEntityType.length() <= 32, "deactivateBlob:_strEntityType length not <= 32 [passed length=" + _strEntityType.length() + "]");
          dbCurrent.test(_iEntityID > 0, "deactivateBlob:_iEntityID <= 0");
          dbCurrent.test(_strAttributeCode != null, "deactivateBlob:_strAttributeCode is null");
          dbCurrent.debug("deactivateBlob:_strAttributeCode:length = " + _strAttributeCode.length());
          dbCurrent.test(_strAttributeCode.length() <= 32, "deactivateBlob:_strAttributeCode length not <= 32 [passed length=" + _strAttributeCode.length() + "]");
          dbCurrent.test(_iNLSID > 0, "deactivate:_iNLSID <= 0");
          dbCurrent.deactivateBlob(_prof, _strEntityType, _iEntityID, _strAttributeCode, _iNLSID);
          dbCurrent.commit();
      } catch (RuntimeException rx) {
          StringWriter writer = new StringWriter();
          rx.printStackTrace(new PrintWriter(writer));
          String x = writer.toString();

          if (dbCurrent !=null){
              dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + " " + rx);
              dbCurrent.debug(D.EBUG_ERR, "" + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + " " + rx);
              D.ebug(D.EBUG_ERR, "" + x);
          }

          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent != null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent != null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return rdrsgDummyReturn;
  }


  public final String getHelpText(Profile _prof, String _strAttributeCode) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      String strReturn = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getHelpText";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          strReturn = dbCurrent.getHelpText(_prof, _strAttributeCode);

          dbCurrent.commit();
          dbCurrent.isPending();
          c_dbpOPICM.freeConnection(dbCurrent);
      } catch (RuntimeException rx) {
          StringWriter writer = new StringWriter();
          rx.printStackTrace(new PrintWriter(writer));
          String x = writer.toString();

          if (dbCurrent != null){
              dbCurrent.debug(D.EBUG_ERR, " RuntimeException trapped at: " + strMethod + " " + rx);
              dbCurrent.debug(D.EBUG_ERR, " " + x);
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, " complete - error");
          }else{
              D.ebug(D.EBUG_ERR, " RuntimeException trapped at: " + strMethod + " " + rx);
              D.ebug(D.EBUG_ERR, " " + x);
              D.ebug(D.EBUG_DETAIL, " complete - error");
          }

          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent != null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
              dbCurrent.debug(D.EBUG_DETAIL, "RMI: " + strMethod + " complete - error");
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              D.ebug(D.EBUG_DETAIL, "RMI: " + strMethod + " complete - error");
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent != null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return strReturn;
  }

  /**
   * "testThruPut" receives data from client and checks length
   * Makes no database calls
   * @return the input string is passed back
   */
  public final String testThruPut(int iLogStringLength, String strLogString) throws RemoteException {
      return strLogString;
  }

  /**
   * "log" outputs the provided string to the middleware log file
   * Makes no database calls
   */
  public final String log(String strLogString) throws RemoteException {
      debug(strLogString);

      return "";
  }

  /**
   * "Ping" the middleware to see if connection is still alive
   * Makes no database calls
   * @return Returns the String "pong" if all is well
   */
  public final ReturnDataResultSetGroup ping() throws RemoteException {
      return new ReturnDataResultSetGroup(new ReturnDataResultSet(new ReturnDataRow("pong")));
  }

  /**
   * A method for executing middleware commands
   */
  public final String[] command(String strCommand) throws RemoteException {
      return this.execute(strCommand);
  }



  /**
   * getEntityList method
   * @exception RemoteException
   * @exception MiddlewareException
   * @exception MiddlewareShutdownInProgressException
   */
  public EntityList getEntityList(Profile _prof) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "getEntityList (Cart)";
      String strSP = null;
      EntityList elReturn = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          elReturn = dbCurrent.getEntityList(_prof);
          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {

          if (dbCurrent!= null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!= null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return elReturn;
  }

  /**
   * getEntityList method
   * @exception RemoteException
   * @exception MiddlewareException
   * @exception MiddlewareShutdownInProgressException
   */
  public final EntityList getEntityList(Profile _prof, NavActionItem _ai, EntityItem[] _aei, boolean _bActionTree) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "getEntityList(Nav)";
      String strSP = null;
      EntityList elReturn = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          elReturn = dbCurrent.getEntityList(_prof, _ai, _aei, _bActionTree);
          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!= null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!= null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return elReturn;

  }

  /*****
  * reduce number of methods in static block
  */
  public EntityList getEntityList(Profile _prof, EANActionItem _ai, EntityItem[] _aei) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException
  {
      if (_ai instanceof ExtractActionItem){
          return getEntityList(_prof, (ExtractActionItem) _ai, _aei);
      }
      if (_ai instanceof CreateActionItem){
          return getEntityList(_prof, (CreateActionItem) _ai, _aei);
      }
      if (_ai instanceof EditActionItem){
          return getEntityList(_prof, (EditActionItem) _ai, _aei);
      } 
      if (_ai instanceof CopyActionItem){
          return getEntityList(_prof, (CopyActionItem) _ai, _aei);
      }  
      if (_ai instanceof ABRStatusActionItem){
          return getEntityList(_prof, (ABRStatusActionItem) _ai, _aei);
      }  
      
      return null;
  }

  /**
   * getEntityList method
   * @exception RemoteException
   * @exception MiddlewareException
   * @exception MiddlewareShutdownInProgressException
   */
  private EntityList getEntityList(Profile _prof, ExtractActionItem _ai, EntityItem[] _aei) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "getEntityList(Extract)";
      String strSP = null;
      EntityList elReturn = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          elReturn = dbCurrent.getEntityList(_prof, _ai, _aei);
          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return elReturn;

  }

  /**
   * getEntityList method
   * @exception RemoteException
   * @exception MiddlewareException
   * @exception MiddlewareShutdownInProgressException
   */
  private EntityList getEntityList(Profile _prof, CreateActionItem _ai, EntityItem[] _aei) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "getEntityList(Create)";
      String strSP = null;
      EntityList elReturn = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          elReturn = dbCurrent.getEntityList(_prof, _ai, _aei);
          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent != null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent != null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return elReturn;

  }

  /**
   * getEntityList method
   * @exception RemoteException
   * @exception MiddlewareException
   * @exception MiddlewareShutdownInProgressException
   */
  private EntityList getEntityList(Profile _prof, EditActionItem _ai, EntityItem[] _aei) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "getEntityList(Edit)";
      String strSP = null;
      EntityList elReturn = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          elReturn = dbCurrent.getEntityList(_prof, _ai, _aei);
          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException rx) {
          StringWriter writer = new StringWriter();
          rx.printStackTrace(new PrintWriter(writer));
          String x = writer.toString();
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + " " + rx);
              dbCurrent.debug(D.EBUG_ERR, "" + x);
          }else{
              D.ebug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + " " + rx);
              D.ebug(D.EBUG_ERR, "" + x);
          }

          // Rethrow the error after clean ujp
          throw rx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return elReturn;

  }

  /**
   * getEntityList method
   * @exception RemoteException
   * @exception MiddlewareException
   * @exception MiddlewareShutdownInProgressException
   */
  private EntityList getEntityList(Profile _prof, CopyActionItem _ai, EntityItem[] _aei) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "getEntityList(Copy)";
      String strSP = null;
      EntityList elReturn = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          elReturn = dbCurrent.getEntityList(_prof, _ai, _aei);
          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException rx) {
          logException(dbCurrent, strMethod, rx, "MiddlewareException");
          // Rethrow the error after clean ujp
          throw rx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return elReturn;

  }

  /**
   * getEntityList method
   * @exception RemoteException
   * @exception MiddlewareException
   * @exception MiddlewareShutdownInProgressException
   */
  private EntityList getEntityList(Profile _prof, ABRStatusActionItem _ai, EntityItem[] _aei) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "getEntityList(ABRStatus)";
      String strSP = null;
      EntityList elReturn = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          elReturn = dbCurrent.getEntityList(_prof, _ai, _aei);
          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException rx) {
          logException(dbCurrent, strMethod, rx, "MiddlewareException");
          // Rethrow the error after clean ujp
          throw rx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return elReturn;

  }

  /**
   * getEntityList method
   * @exception RemoteException
   * @exception MiddlewareException
   * @exception MiddlewareShutdownInProgressException
   */
  public final EntityList getEntityList(Profile _prof, SearchActionItem _ai) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "getEntityList(Search)";
      String strSP = null;
      EntityList elReturn = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          elReturn = dbCurrent.getEntityList(_prof, _ai);
          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");

          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return elReturn;

  }

  /**
   * secure login method
   * @exception LoginException
   * @exception VersionException
   * @exception RemoteException
   * @exception MiddlewareException
   * @exception MiddlewareShutdownInProgressException
   */
  public final ProfileSet secureLogin(byte[][] encryptedUidPw,String _strVersionLiteral) throws LoginException, VersionException, RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {
      return secureLogin(encryptedUidPw, _strVersionLiteral, "");
  }
  /**
   * secure login
   * @param encryptedUidPw
   * @param _strVersionLiteral
   * @param _strClient
   * @return
   * @throws VersionException
   * @throws LoginException
   * @throws RemoteException
   * @throws MiddlewareException
   * @throws MiddlewareShutdownInProgressException
   */
  public final ProfileSet secureLogin(byte[][] encryptedUidPw, String _strVersionLiteral,String _strClient)
  throws VersionException, LoginException, RemoteException, MiddlewareException, MiddlewareShutdownInProgressException
  {
      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "securelogin";
      String strSP = null;
      ProfileSet psReturn = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);          

          psReturn = dbCurrent.secureLogin(encryptedUidPw, _strVersionLiteral, _strClient);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);

      } catch (LoginException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "Login exception trapped at: " + strMethod + "." + strSP + " " + x);
          }else{
              D.ebug(D.EBUG_ERR, "Login exception trapped at: " + strMethod + "." + strSP + " " + x);
          }
          throw x;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  D.ebug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return psReturn;
  }
  /**
   * login method
   * @exception LoginException
   * @exception VersionException
   * @exception RemoteException
   * @exception MiddlewareException
   * @exception MiddlewareShutdownInProgressException
   * /
  public final ProfileSet login(String _strOPName, String _strPassword, String _strVersionLiteral) throws LoginException, VersionException, RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {
      return login(_strOPName, _strPassword, _strVersionLiteral, "");
  }*/

  /**
   * login method - this needs to exist until all autoupdates are completed or user won't be able to login
   * @exception LoginException
   * @exception VersionException
   * @exception RemoteException
   * @exception MiddlewareException
   * @exception MiddlewareShutdownInProgressException
   */
  public final ProfileSet login(String _strOPName, String _strPassword, String _strVersionLiteral, String _strClient) throws LoginException, VersionException, RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "login";
      String strSP = null;
      ProfileSet psReturn = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          psReturn = dbCurrent.login(_strOPName, _strPassword, _strVersionLiteral, _strClient);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);

      } catch (LoginException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "Login exception trapped at: " + strMethod + "." + strSP + " " + x);
          }else{
              D.ebug(D.EBUG_ERR, "Login exception trapped at: " + strMethod + "." + strSP + " " + x);
          }
          throw x;

      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  D.ebug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return psReturn;
  }

  /**
   * logout method
   * @exception RemoteException
   * @exception MiddlewareException
   * @exception MiddlewareShutdownInProgressException
   */
  public final void logout() throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "logout";
      String strSP = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);
          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
  }

  /*
   * New father of all link routines
   */
  public final OPICMList link(Profile _prof, Vector _vctReturnRelatorKeys, String _strLinkOption, int _iSwitch, int _iCopyCount, boolean _bCheckOrphan) throws LinkException, RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;
      OPICMList olReturn = null;
      //Information about exception location
      String strMethod = "linkII";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          // Check for transaction count
          if ((_vctReturnRelatorKeys.size() * _iCopyCount) > 500) {
              throw new LinkException("This request generates over " + _vctReturnRelatorKeys.size() + " Relationships.  You are only allowed to generate 500 per transaction.  " + "Please reduce your selection and try again");
          }

          olReturn = dbCurrent.link(_prof, _vctReturnRelatorKeys, _strLinkOption, _iSwitch, _iCopyCount, _bCheckOrphan);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (LinkException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " LinkException trapped at: " + strMethod + " " + x);
          }else{
              D.ebug(D.EBUG_ERR, " LinkException trapped at: " + strMethod + " " + x);
          }
          throw x;
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return olReturn;
  }

  /**
   * RMI:
   * OPICMUpdate transaction
   */
  public OPICMList update(Profile _prof, Vector vctTransactions) throws RemoteException, MiddlewareException, MiddlewareBusinessRuleException, MiddlewareShutdownInProgressException {

      //Available connection to a database
      Database dbCurrent = null;
      OPICMList olReturn = new OPICMList();
      String strMethod = "update";
     // int iConnectionID = 0;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          olReturn = dbCurrent.update(_prof, vctTransactions);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");

          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareBusinessRuleException brx) {
          logException(dbCurrent, strMethod, brx, "MiddlewareBusinessRuleException");

          throw brx;
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }

      }

      //Return the output
      return olReturn;
  }


  /**
   * RMI:
   * update transaction
   */
  public OPICMList update(Profile _prof, Vector vctTransactions,boolean bCheckDeactivatedEntity) throws RemoteException, MiddlewareException, MiddlewareBusinessRuleException, MiddlewareShutdownInProgressException {

      //Available connection to a database
      Database dbCurrent = null;
      OPICMList olReturn = new OPICMList();
      String strMethod = "update";
      //int iConnectionID = 0;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          olReturn = dbCurrent.update(_prof, vctTransactions,bCheckDeactivatedEntity);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareBusinessRuleException brx) {
          logException(dbCurrent, strMethod, brx, "MiddlewareBusinessRuleException");
          throw brx;
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return olReturn;

  }

  //********************************************
  /**
   * RMI:
   * update transaction
   */
  public OPICMList update(Profile _prof, Vector vctTransactions,boolean bCheckDeactivatedEntity,boolean _bAttributesOnly) throws RemoteException, MiddlewareException, MiddlewareBusinessRuleException, MiddlewareShutdownInProgressException {

      //Available connection to a database
      Database dbCurrent = null;
      OPICMList olReturn = new OPICMList();
      String strMethod = "update";
     // int iConnectionID = 0;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          olReturn = dbCurrent.update(_prof, vctTransactions,bCheckDeactivatedEntity,_bAttributesOnly);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareBusinessRuleException brx) {
          logException(dbCurrent, strMethod, brx, "MiddlewareBusinessRuleException");
          throw brx;
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }

      }

      //Return the output
      return olReturn;
  }

  /**
   * RMI:
   * updateWGDefault transaction
   */
  public OPICMList updateWGDefault(Profile _prof, Vector vctTransactions) throws RemoteException, MiddlewareException, MiddlewareBusinessRuleException, MiddlewareShutdownInProgressException {

      //Available connection to a database
      Database dbCurrent = null;
      OPICMList olReturn = new OPICMList();
      String strMethod = "updateWGDefault";
     // int iConnectionID = 0;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          olReturn = dbCurrent.updateWGDefault(_prof, vctTransactions);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareBusinessRuleException brx) {
          logException(dbCurrent, strMethod, brx, "MiddlewareBusinessRuleException");
          throw brx;
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return olReturn;
  }

  /**
   * RMI:
   * resetWGDefault transaction
   */
  public void resetWGDefault(Profile _prof, Vector vctTransactions) throws RemoteException, MiddlewareException, MiddlewareBusinessRuleException, MiddlewareShutdownInProgressException {

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "resetWGDefault";
      //int iConnectionID = 0;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.resetWGDefault(_prof, vctTransactions);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareBusinessRuleException brx) {
          logException(dbCurrent, strMethod, brx, "MiddlewareBusinessRuleException");
          throw brx;
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
  }

  /*
   * Generates a LockList
   */
  public final LockList getLockList(Profile _prof, boolean _bGetLock) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      LockList llReturn = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getLockList";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          llReturn = dbCurrent.getLockList(_prof, _bGetLock);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);   
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return llReturn;
  }

  /*
   * Generates a LockList for LockEntity
   */
  public final LockList getLockListForLockEntity(Profile _prof, EntityItem _lockEI, String _strLockOwner) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      LockList llReturn = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getLockListForLockEntity";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          llReturn = dbCurrent.getLockListForLockEntity(_prof, _lockEI, _strLockOwner);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return llReturn;
  }

  /*
   * lock EntityItem
   */
  public final LockGroup getLockGroup(Profile _prof, EntityItem _lockEI, EntityItem _ei, String _strLockOwner, int _iLockType, boolean _bCreateLock) throws LinkException, RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
     // String strNow = null;

      //Information about exception location
      String strMethod = "lock";
     // int iConnectionID = -1;

      LockGroup lgReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          lgReturn = dbCurrent.getLockGroup(_prof, _lockEI, _ei, _strLockOwner, _iLockType, _bCreateLock);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (LinkException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " LinkException trapped at: " + strMethod + " " + x);
          }else{
              D.ebug(D.EBUG_ERR, " LinkException trapped at: " + strMethod + " " + x);
          }
          throw x;
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return lgReturn;
  }

  /*
   * lock EntityItem
   */
  public final LockGroup[] getLockGroups(Profile _prof, EntityItem _lockEI, EntityItem[] _aei, String _strLockOwner, int _iLockType, boolean _bCreateLock) throws LinkException, RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "getLockGroups";
      //int iConnectionID = -1;

      LockGroup[] algReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          algReturn = dbCurrent.getLockGroups(_prof, _lockEI, _aei, _strLockOwner, _iLockType, _bCreateLock);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");

          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (LinkException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " LinkException trapped at: " + strMethod + " " + x);
          }else{
              D.ebug(D.EBUG_ERR, " LinkException trapped at: " + strMethod + " " + x);
          }
          throw x;
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }
          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return algReturn;
  }

  /*
   * clearLock
   */
  public final void clearLock(Profile _prof, String _strEntityType, int _iEntityID, String _strLockEntityType, int _iLockEntityID, String _strLockOwner) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
     // String strNow = null;

      //Information about exception location
      String strMethod = "clearLock";
     // int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.clearLock(_prof, _strEntityType, _iEntityID, _strLockEntityType, _iLockEntityID, _strLockOwner);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);      
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }

      }
  }

  /*
   * clearLock
   */
  public final void clearLocks(Profile _prof, EntityItem[] _aei, String _strLockEntityType, int _iLockEntityID, String _strLockOwner, int _iLockType) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "clearLocks";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
        //  iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.clearLocks(_prof, _aei, _strLockEntityType, _iLockEntityID, _strLockOwner, _iLockType);
          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }
          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }

      }
  }

  /*
   * Generates a ParentChildList
   */
  public final ParentChildList getParentChildList(Profile _prof, EntityItem _pEI, EntityItem _cEI) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      ParentChildList pclReturn = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getParentChildList";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          pclReturn = dbCurrent.getParentChildList(_prof, _pEI, _cEI);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return pclReturn;
  }

  /**
   * getEntityGroup method
   * @exception RemoteException
   * @exception MiddlewareException
   * @exception MiddlewareShutdownInProgressException
   */
  public final EntityGroup getEntityGroup(Profile _prof, String _strEntityType, String _strPurpose) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "getEntityGroup";
      String strSP = null;
      EntityGroup egReturn = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          egReturn = dbCurrent.getEntityGroup(_prof, _strEntityType, _strPurpose);
          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return egReturn;

  }

  /*
   * execute a DeleteActionItem
   */
  public final void executeAction(Profile _prof, DeleteActionItem _dai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException, LockException, EANBusinessRuleException {

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "executeAction DeleteActionItem";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.executeAction(_prof, _dai);
          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (LockException rx) {
          logException(dbCurrent, strMethod, rx, "LockException");
          throw rx;
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } catch (EANBusinessRuleException x) {
          dbCurrent.debug(D.EBUG_ERR, " EANBusinessRuleException trapped at: " + strMethod + " " + x);
          dbCurrent.freeStatement();
          dbCurrent.isPending();
          c_dbpOPICM.freeConnection(dbCurrent);
          throw x;

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
  }

  /*
   * execute a LinkActionItem
   */
  public final Object executeAction(Profile _prof, LinkActionItem _lai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException, LockException, EANBusinessRuleException, WorkflowException {

      Database dbCurrent = null;
     // String strNow = null;

      //Information about exception location
      String strMethod = "executeAction LinkActionItem";
      //int iConnectionID = -1;
      Object oReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          oReturn = dbCurrent.executeAction(_prof, _lai);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");

          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } catch (EANBusinessRuleException x) {
          dbCurrent.debug(D.EBUG_ERR, " EANBusinessRuleException trapped at: " + strMethod + " " + x);
          throw x;

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return oReturn;
  }

  /*
   * execute a LinkActionItem
   */
  public final Vector executeLink(Profile _prof, LinkActionItem _lai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException, LockException, EANBusinessRuleException, WorkflowException {

      Database dbCurrent = null;
    //  String strNow = null;

      //Information about exception location
      String strMethod = "executeAction LinkActionItem";
     // int iConnectionID = -1;
      Vector vReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          vReturn = dbCurrent.executeLink(_prof, _lai);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } catch (EANBusinessRuleException x) {
          dbCurrent.debug(D.EBUG_ERR, " EANBusinessRuleException trapped at: " + strMethod + " " + x);
          throw x;

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent); 
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return vReturn;
  }

  /*
   * execute a WorkflowActionItem
   */
  public final void executeAction(Profile _prof, WorkflowActionItem _wai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException, WorkflowException {

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "executeAction WorkflowActionItem";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.executeAction(_prof, _wai);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
  }

  /**
   * execute a WatchdogActionItem
   */
  public final WatchdogActionItem executeAction(WatchdogActionItem _wdai) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException, WorkflowException {

      Database dbCurrent = null;
     // String strNow = null;

      //Information about exception location
      String strMethod = "executeAction WatchdogActionItem";
     // int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();
          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);
          _wdai = dbCurrent.executeAction(_wdai);
          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);          
          }
          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return _wdai;
  }

  /*
   * execute a LockActionItem
   */
  public final void executeAction(Profile _prof, LockActionItem _lai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "executeAction LockActionItem";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.executeAction(_prof, _lai);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

  }

  /**
   * execute a NavActionItem
   */
  public final EntityList executeAction(Profile _prof, NavActionItem _nai) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      EntityList el = null;

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "executeAction NavActionItem";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          el = dbCurrent.executeAction(_prof, _nai);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }
          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return el;
  }

  /*
   * execute a MatrixActionItem
   */
  public final MatrixList executeAction(Profile _prof, MatrixActionItem _mai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
     // String strNow = null;

      //Information about exception location
      String strMethod = "executeAction MatrixActionItem";
      //int iConnectionID = -1;

      MatrixList mlReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          mlReturn = dbCurrent.executeAction(_prof, _mai);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }
          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return mlReturn;
  }

  /*
   * execute a SearchActionItem
   */
  public final EntityList executeAction(Profile _prof, SearchActionItem _sai) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
     // String strNow = null;

      //Information about exception location
      String strMethod = "executeAction SearchActionItem";
      //int iConnectionID = -1;

      EntityList elReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          elReturn = dbCurrent.executeAction(_prof, _sai);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return elReturn;

  }

  /*
   * get picklist from a MatrixActionItem
   */
  public final EntityList generatePickList(Profile _prof, MatrixActionItem _mai, String _strRelatorType) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
    //  String strNow = null;

      //Information about exception location
      String strMethod = "generatePickList MatrixActionItem";
    //  int iConnectionID = -1;

      EntityList elReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          elReturn = dbCurrent.generatePickList(_prof, _mai, _strRelatorType);

          dbCurrent.commit();

          //Free any statement
          dbCurrent.freeStatement();
          dbCurrent.isPending();
          c_dbpOPICM.freeConnection(dbCurrent);
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return elReturn;
  }

  /*
   * execute a WhereUsedActionItem
   */
  public final WhereUsedList executeAction(Profile _prof, WhereUsedActionItem _wuai, boolean _bMeta) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "executeAction WhereUsedActionItem";
      //int iConnectionID = -1;

      WhereUsedList wulReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          wulReturn = dbCurrent.executeAction(_prof, _wuai, _bMeta);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return wulReturn;
  }

  /*
   * get picklist from a WhereUsedActionItem
   */
  public final EntityList generatePickList(Profile _prof, WhereUsedActionItem _wuai, String _strRelatorType) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
     // String strNow = null;

      //Information about exception location
      String strMethod = "generatePickList WhereUsedActionItem";
     // int iConnectionID = -1;

      EntityList elReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
        //  iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          elReturn = dbCurrent.generatePickList(_prof, _wuai, _strRelatorType);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return elReturn;
  }

  /*
   * createEntity from a WhereUsedActionItem
   */
  public final EntityList createEntity(Profile _prof, WhereUsedActionItem _wuai, String _strRelatorType) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
     // String strNow = null;

      //Information about exception location
      String strMethod = "createEntity WhereUsedActionItem";
      //int iConnectionID = -1;

      EntityList elReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          elReturn = dbCurrent.createEntity(_prof, _wuai, _strRelatorType);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return elReturn;
  }

  public final void transferQueues(Profile _prof, int _iOLDOPWG, String _strQueueName, int _iStartID, int _iEndID) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
     // String strNow = null;

      //Information about exception location
      String strMethod = "transferQueues";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.test(_iEndID - _iStartID < 5001,"Cannot have a range of greater that 5000");
          dbCurrent.transferQueues(_prof, _iOLDOPWG, _strQueueName, _iStartID, _iEndID);
          dbCurrent.commit();


      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
  }

  /**
   * DescriptionChangeList method
   * @exception RemoteException
   * @exception MiddlewareException
   * @exception MiddlewareShutdownInProgressException
   */
  public final DescriptionChangeList getDescriptionChangeList(Profile _prof, int _iChangeType, String _strStartDate, String _strEndDate) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "DescriptionChangeList()";
      String strSP = null;
      DescriptionChangeList dclReturn = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dclReturn = dbCurrent.getDescriptionChangeList(_prof, _iChangeType, _strStartDate, _strEndDate);
          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return dclReturn;

  }

  /**
   * refreshDGQueue method
   * @exception RemoteException
   * @exception MiddlewareException
   * @exception MiddlewareShutdownInProgressException
   */
  public final void refreshDGQueue(Profile _prof) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "refreshDGQueue()";
      String strSP = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.refreshDGQueue(_prof);
          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }


  }

  /*
   * returns a list of countries' names of the AVAIL entity
   */
  public final String[] getListOfCountriesForAVAIL(Profile _prof, EntityItem _ei) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
     // String strNow = null;
      String[] aReturn = null;
      //Information about exception location
      String strMethod = "getListOfCountriesForAVAIL";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          aReturn = dbCurrent.getListOfCountriesForAVAIL( _prof, _ei);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return aReturn;
  }

  /*
   * returns a list of VE Lock Owners for an entity
   */
  public final String[] getVELockOwners(Profile _prof, EntityItem _ei) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;
      String[] aReturn = null;
      //Information about exception location
      String strMethod = "getVELockOwners";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          aReturn = dbCurrent.getVELockOwners( _prof, _ei);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");

          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return aReturn;
  }
  public final String[][] checkVELockOwners(Profile _prof, EntityItem[] _eia) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      String[][] aReturn = null;
      //Information about exception location
      String strMethod = "checkVELockOwners";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          aReturn = dbCurrent.checkVELockOwners( _prof, _eia);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");

          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return aReturn;
  }
  
  public final VELockERList getVELockERList(Profile _prof, String _strEntityType) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;
      VELockERList lReturn = null;
      //Information about exception location
      String strMethod = "getVELockERList";
     // int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          lReturn = dbCurrent.getVELockERList( _prof, _strEntityType);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return lReturn;
  }

  public final GeneralAreaList getGeneralAreaList(Profile _prof) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
     // String strNow = null;
      GeneralAreaList galReturn = null;
      //Information about exception location
      String strMethod = "getVELockERList";
     // int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          galReturn = dbCurrent.getGeneralAreaList( _prof);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return galReturn;
  }

  public IntervalGroup getIntervalGroup(EANMetaFoundation _emf, Profile _prof, String _strQueueType) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;
      IntervalGroup ig = null;
      //Information about exception location
      String strMethod = "getIntervalGroup1";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          ig = dbCurrent.getIntervalGroup(_emf,_prof, _strQueueType);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }
          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return ig;
  }

  public IntervalGroup getIntervalGroup(EANMetaFoundation _emf, Profile _prof, String _strQueueType, String _strStartDate, String _strEndDate) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;
      IntervalGroup ig = null;
      //Information about exception location
      String strMethod = "getIntervalGroup2";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          ig = dbCurrent.getIntervalGroup(_emf,_prof, _strQueueType, _strStartDate, _strEndDate);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return ig;
  }

  public EntityChangeHistoryGroup getEntityChangeHistoryGroup(Profile _prof, EntityItem _ei) throws RemoteException, MiddlewareException,MiddlewareRequestException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
     // String strNow = null;
      EntityChangeHistoryGroup echg = null;
      //Information about exception location
      String strMethod = "getEntityChangeHistoryGroup";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          echg = dbCurrent.getEntityChangeHistoryGroup(_prof,_ei);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return echg;
  }

  public AttributeChangeHistoryGroup getAttributeChangeHistoryGroup(Profile _prof, EANAttribute _ea) throws RemoteException, MiddlewareException,MiddlewareRequestException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;
      AttributeChangeHistoryGroup achg = null;
      //Information about exception location
      String strMethod = "getAttributeChangeHistoryGroup";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          achg = dbCurrent.getAttributeChangeHistoryGroup(_prof,_ea);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return achg;

  }

  public MetaColumnOrderGroup getMetaColumnOrderGroup(String _strEntityType, Profile _prof) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {
      return getMetaColumnOrderGroup(_strEntityType, _prof, false);
  }
  public MetaColumnOrderGroup getMetaColumnOrderGroup(String _strEntityType, Profile _prof, boolean isQueryType) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      MetaColumnOrderGroup mcog = null;
      //Information about exception location
      String strMethod = "getMetaColumnOrderGroup(EntityGroup)";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //int iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction isQueryType "+isQueryType);
         // Thread.dumpStack();

          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          if (isQueryType){
              mcog = new MetaColumnOrderGroup(dbCurrent, new QueryGroup(dbCurrent, _prof, _strEntityType));
          }else {
              mcog = dbCurrent.getMetaColumnOrderGroup(_strEntityType,_prof);
          }

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return mcog;
  }

  public MetaColumnOrderGroup getMetaColumnOrderGroupForMatrix(String _strEntityType, String _strMatrixActionItemKey, Profile _prof) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;
      MetaColumnOrderGroup mcog = null;
      //Information about exception location
      String strMethod = "getMetaColumnOrderGroupForMatrix";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          mcog = dbCurrent.getMetaColumnOrderGroupForMatrix(_strEntityType,_strMatrixActionItemKey,_prof);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return mcog;
  }

  public MetaColumnOrderGroup getMetaColumnOrderGroup(SearchBinder _searchBinder) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
     // String strNow = null;
      MetaColumnOrderGroup mcog = null;
      //Information about exception location
      String strMethod = "getMetaColumnOrderGroup(SearchBinder)";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
        //  iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          mcog = dbCurrent.getMetaColumnOrderGroup(_searchBinder);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return mcog;
  }

  public MetaRow updatePdhMetaRow(MetaRow _mr) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
    //  String strNow = null;
      //Information about exception location
      String strMethod = "updatePdhMetaRow";
    //  int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
        //  iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          _mr.updatePdh(dbCurrent);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return _mr;
  }

//methods for PDG
  private void executeAction(Profile _prof, PDGActionItem _pdgai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException, SBRException {

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "executeAction PDGActionItem";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.executeAction(_prof, _pdgai);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } catch (SBRException rx) {
          logException(dbCurrent, strMethod, rx, "SBRException");
          throw rx;
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
  }

  public final void queuedABR(Profile _prof, PDGActionItem _pdgai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException, SBRException {

      Database dbCurrent = null;
     // String strNow = null;

      //Information about exception location
      String strMethod = "queuedABR PDGActionItem";
     // int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
        //  iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.queuedABR(_prof, _pdgai);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } catch (SBRException rx) {
          logException(dbCurrent, strMethod, rx, "SBRException");
          throw rx;
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
  }

  public final byte[] viewMissingData(Profile _prof, PDGActionItem _pdgai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException, SBRException {
      byte[] baReturn = null;
      Database dbCurrent = null;
     // String strNow = null;

      //Information about exception location
      String strMethod = "viewMissingData PDGActionItem";
     // int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          baReturn = dbCurrent.viewMissingData(_prof, _pdgai);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } catch (SBRException rx) {
          logException(dbCurrent, strMethod, rx, "SBRException");
          throw rx;
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return baReturn;
  }

  public final PDGCollectInfoList collectInfo(Profile _prof, int _iStep, PDGActionItem _pdgai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException, SBRException {
      PDGCollectInfoList listReturn = null;
      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "collectInfo PDGActionItem";
     // int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          listReturn = dbCurrent.collectInfo(_prof, _iStep, _pdgai);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return listReturn;
  }

  public final PDGCollectInfoList collectInfo(Profile _prof, EANMetaAttribute _meta, PDGActionItem _pdgai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException, SBRException {
      PDGCollectInfoList listReturn = null;
      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "collectInfo PDGActionItem";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          listReturn = dbCurrent.collectInfo(_prof, _meta, _pdgai);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return listReturn;
  }

//methods for SPDG
  public final void executeAction(Profile _prof, PDGTemplateActionItem _spdgai) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
  {
    if (_spdgai instanceof SPDGActionItem){
        executeAction(_prof, (SPDGActionItem) _spdgai);
    }else if (_spdgai instanceof PDGActionItem){
        executeAction(_prof, (PDGActionItem) _spdgai);
    }
    
  }
  private void executeAction(Profile _prof, SPDGActionItem _spdgai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException, SBRException {

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "executeAction SPDGActionItem";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.executeAction(_prof, _spdgai);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } catch (SBRException rx) {
          logException(dbCurrent, strMethod, rx, "SBRException");
          throw rx;
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
  }

  public final byte[] viewMissingData(Profile _prof, SPDGActionItem _spdgai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException, SBRException {
      byte[] baReturn = null;
      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "viewMissingData SPDGActionItem";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          baReturn = dbCurrent.viewMissingData(_prof, _spdgai);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }
          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } catch (SBRException rx) {
          logException(dbCurrent, strMethod, rx, "SBRException");
          throw rx;
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return baReturn;
  }

  public final void queuedABR(Profile _prof, SPDGActionItem _spdgai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException, SBRException {

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "queuedABR SPDGActionItem";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.queuedABR(_prof, _spdgai);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } catch (SBRException rx) {
          logException(dbCurrent, strMethod, rx, "SBRException");
          throw rx;
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
  }

  // end PDG methods

  public final EANActionItem getBookmarkedActionItem(Profile _prof, String _strActionItemKey, String _strUserDescription) throws Exception, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;
      //Information about exception location
      String strMethod = "getBookmarkedActionItem";
      //int iConnectionID = -1;

      EANActionItem eai = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          eai = dbCurrent.getBookmarkedActionItem(_prof,_strActionItemKey,_strUserDescription);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return eai;
  }

  public final BookmarkItem putBookmarkedActionItem(Profile _prof, EANActionItem _eai, String _strUserDescription, int _iDupMode) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, BookmarkException {

      Database dbCurrent = null;
      //String strNow = null;
      //Information about exception location
      String strMethod = "putBookmarkedActionItem";
     // int iConnectionID = -1;

      BookmarkItem biReturn = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          biReturn = dbCurrent.putBookmarkedActionItem(_prof,_eai,_strUserDescription,_iDupMode);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
      return biReturn;
  }

  public final BookmarkGroup getBookmarkGroup(Profile _prof) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
      Database dbCurrent = null;
     // String strNow = null;
      //Information about exception location
      String strMethod = "getBookmarkGroup";
      //int iConnectionID = -1;

      BookmarkGroup bg = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          bg = dbCurrent.getBookmarkGroup(_prof);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
      return bg;
  }

  public final void deleteBookmark(String _strEnterprise, int _iOPID, String _strActionItemKey, String _strUserDescription) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
      Database dbCurrent = null;
      //String strNow = null;
      //Information about exception location
      String strMethod = "deleteBookmark";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.deleteBookmark(_strEnterprise, _iOPID,_strActionItemKey,_strUserDescription);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
      return;
  }

  public final MetaEntityList getMetaEntityList(Profile _prof) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
      Database dbCurrent = null;
      //String strNow = null;
      //Information about exception location
      String strMethod = "getMetaEntityList";
      //int iConnectionID = -1;

      MetaEntityList mel = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          mel = dbCurrent.getMetaEntityList(_prof);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }
          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
      return mel;
  }

  public final Profile getNewProfileInstance(Profile _prof) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
      Database dbCurrent = null;
      //String strNow = null;
      //Information about exception location
      String strMethod = "getNewProfileInstance";
      //int iConnectionID = -1;

      Profile profNew = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          // Get the new Profile
          profNew = _prof.getNewInstance(dbCurrent);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return profNew;
  }

  public final EntityItem refreshEntityItem(Profile _prof, EntityGroup _eg, EntityItem _ei) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      EntityItem eiReturn = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "refreshEntityItem";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          eiReturn = dbCurrent.refreshEntityItem(_prof, _eg, _ei);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return eiReturn;
  }

  /*
   * Generates a LockList
   */
  public final LockList getAllSoftLocksForWGID(Profile _prof) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      LockList llReturn = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getAllSoftLocksForWGID";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          llReturn = dbCurrent.getAllSoftLocksForWGID(_prof);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return llReturn;
  }

  public final SearchActionItem getSearchActionItem(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      SearchActionItem sai = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getSearchActionItem";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          sai = dbCurrent.getSearchActionItem(_emf,_prof,_strActionItemKey);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return sai;
  }

  public final NavActionItem getNavActionItem(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      NavActionItem nai = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getNavActionItem";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          nai = dbCurrent.getNavActionItem(_emf,_prof,_strActionItemKey);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return nai;
  }

  public final CreateActionItem getCreateActionItem(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      CreateActionItem cai = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getCreateActionItem";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          cai = dbCurrent.getCreateActionItem(_emf,_prof,_strActionItemKey);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return cai;
  }

  public final DeleteActionItem getDeleteActionItem(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      DeleteActionItem dai = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getDeleteActionItem";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dai = dbCurrent.getDeleteActionItem(_emf,_prof,_strActionItemKey);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return dai;
  }

  public final WhereUsedActionItem getWhereUsedActionItem(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      WhereUsedActionItem wuai = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getWhereUsedActionItem";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          wuai = dbCurrent.getWhereUsedActionItem(_emf,_prof,_strActionItemKey);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return wuai;
  }

  public final LinkActionItem getLinkActionItem(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      LinkActionItem lai = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getLinkActionItem";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          lai = dbCurrent.getLinkActionItem(_emf,_prof,_strActionItemKey);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return lai;
  }

  public final WatchdogActionItem getWatchdogActionItem(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      WatchdogActionItem wdai = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getWatchdogActionItem";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          wdai = dbCurrent.getWatchdogActionItem(_emf,_prof,_strActionItemKey);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return wdai;
  }

  public final WorkflowActionItem getWorkflowActionItem(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      WorkflowActionItem wfai = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getWorkflowActionItem";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          wfai = dbCurrent.getWorkflowActionItem(_emf,_prof,_strActionItemKey);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return wfai;
  }

  public final ExtractActionItem getExtractActionItem(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      ExtractActionItem exai = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getExtractActionItem";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          exai = dbCurrent.getExtractActionItem(_emf,_prof,_strActionItemKey);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return exai;
  }

  public final EditActionItem getEditActionItem(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      EditActionItem eai = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getEditActionItem";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          eai = dbCurrent.getEditActionItem(_emf,_prof,_strActionItemKey);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return eai;
  }

  public final LockActionItem getLockActionItem(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      LockActionItem lai = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getLockActionItem";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          lai = dbCurrent.getLockActionItem(_emf,_prof,_strActionItemKey);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return lai;
  }

  public final MatrixActionItem getMatrixActionItem(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      MatrixActionItem mai = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getMatrixActionItem";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          mai = dbCurrent.getMatrixActionItem(_emf,_prof,_strActionItemKey);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return mai;
  }

  public final ReportActionItem getReportActionItem(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      ReportActionItem rai = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getReportActionItem";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          rai = dbCurrent.getReportActionItem(_emf,_prof,_strActionItemKey);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return rai;
  }

  public final InactiveGroup getInactiveGroup(Profile _prof, String _strStartDate, boolean _bViewAll) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      InactiveGroup iagReturn = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getInactiveGroup";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          iagReturn = dbCurrent.getInactiveGroup(_prof,_strStartDate, _bViewAll);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return iagReturn;
  }

  public final InactiveGroup deactivatedUndo(Profile _prof, InactiveGroup _iag, InactiveItem[] _aiai)  throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      InactiveGroup iagReturn = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "deactivatedUndo";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          iagReturn = dbCurrent.deactivatedUndo(_prof,_iag, _aiai);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return iagReturn;
  }

  public final Vector getPackagesAwaitingTranslation(Profile _prof) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      Vector vctReturn = new Vector();

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getPackagesAwaitingTranslation";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          vctReturn = Translation.getPackagesAwaitingTranslation(dbCurrent, _prof);
          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return vctReturn;

  }

  public final TranslationPackage pullPDHPackageForTranslation (Profile _prof, PackageID _pkID) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      TranslationPackage tpReturn = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "pullPDHPackageForTranslation";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);
          dbCurrent.debug(D.EBUG_DETAIL,"TRACE:" + strMethod + ":" + _pkID);
          tpReturn = Translation.pullPDHPackageForTranslation(dbCurrent, _prof, _pkID);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return tpReturn;

  }

  public final void putETSTranslatedPackage(Profile _prof, TranslationPackage _trnp) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "putETSTranslatedPackage";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          Translation.putETSTranslatedPackage(dbCurrent, _prof, _trnp);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

  }

  public final void setTranslationPackageStatus(Profile _prof, PackageID _pkID, PackageStatus _ps) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "setTranslationPackageStatus";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          Translation.setStatus(dbCurrent, _prof, _pkID, _ps);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

  }


  /**
   * getBlobNow
   **/
  public final COM.ibm.opicmpdh.objects.Blob getBlobNow(Profile _prof, String _strEntityType, int _iEntityID, String _strAttributeCode, int _iNLSID) throws Exception, RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getBlobNow";
      COM.ibm.opicmpdh.objects.Blob bReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          bReturn = dbCurrent.getBlobNow(_prof, _strEntityType, _iEntityID, _strAttributeCode, _iNLSID);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return bReturn;

  }

  public ProfileSet getCommonProfiles(Profile _prof) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getCommonProfiles";
      ProfileSet psReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          psReturn = dbCurrent.getCommonProfiles(_prof);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return psReturn;
  }


  public StringBuffer genVEChangeXML(String _strAppRoot, Profile _prof, String _strExtractActionItem, String _strT1, String _strT2, String _strEntityType, int _iEntityID) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "genVEChangeXML(";
      StringBuffer sbReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          sbReturn = dbCurrent.genVEChangeXML(_strAppRoot, _prof, _strExtractActionItem, _strT1, _strT2, _strEntityType, _iEntityID);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return sbReturn;
  }

  public String getRuntime(String _strEnterprise, String _strRunType) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getRuntime";
      String strReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          strReturn = dbCurrent.getRuntime(_strEnterprise,_strRunType);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return strReturn;
  }

  public void saveRuntime(String _strEnterprise, String _strRunType, String _strRunTime) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "saveRuntime";
//    String strReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.saveRuntime(_strEnterprise,_strRunType,_strRunTime);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return;
  }

  public BluePageEntry getBluePageEntry(InternetAddress _iaEmailAddress) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, BluePageException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "getBluePageEntry";
    //  String strSP = null;
      BluePageEntry bpeReturn = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          bpeReturn = dbCurrent.getBluePageEntry(_iaEmailAddress);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return bpeReturn;
  }

  public BluePageEntryGroup getBluePageEntryGroup(String _strLastName, String _strFirstName) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException, BluePageException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "getBluePageEntryGroup";
     // String strSP = null;
      BluePageEntryGroup bpegReturn = null;

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          bpegReturn = dbCurrent.getBluePageEntryGroup(_strLastName, _strFirstName);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return bpegReturn;
  }

  /**
   * getSoftwareImage transaction
   */
  public final COM.ibm.opicmpdh.objects.Blob getSoftwareImage(Profile _prof, String _enterprise, String entityType, int entityID, String attributeCode) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      //Available connection to a database
      Database dbCurrent = null;
      COM.ibm.opicmpdh.objects.Blob blobNew = null;

      //Information about exception location
      String strMethod = "getSoftwareImage";
      String strSP = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          //this stuff s/b in jdbc method
          dbCurrent.test(_enterprise != null, "enterprise is null");
          dbCurrent.test(entityType != null, "entityType is null");
          dbCurrent.test(entityID > 0, "entityID <= 0");
          dbCurrent.test(attributeCode != null, "attributeCode is null");
          dbCurrent.test(_prof.getReadLanguage().getNLSID() > 0, "nlsID <= 0");
          dbCurrent.test(_prof.getValOn() != null, "valOn is null");
          dbCurrent.test(_prof.getEffOn() != null, "effOn is null");
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:Enterprise: " + _enterprise);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EntityType: " + entityType);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EntityID: " + entityID);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:AttributeCode: " + attributeCode);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:NLSID: " + _prof.getReadLanguage().getNLSID());
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:ValOn: " + _prof.getValOn());
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EffOn: " + _prof.getEffOn());

          strSP = "getSoftwareImage";
          blobNew = dbCurrent.getSoftwareImage(_prof, _enterprise, entityType, entityID, attributeCode);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);
      } catch (Exception x) {
          logException(dbCurrent, strMethod, x, "Exception");
      } finally {
          if (dbCurrent!=null){
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return blobNew;
  }
  /**
   * getSoftwareImage transaction
   */
  public final COM.ibm.opicmpdh.objects.Blob getSoftwareImage(String _enterprise, String entityType, int entityID, String attributeCode,String strValOn,String strEffOn) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      //Available connection to a database
      Database dbCurrent = null;
      COM.ibm.opicmpdh.objects.Blob blobNew = null;

      //Information about exception location
      String strMethod = "getSoftwareImage";
      String strSP = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          //this stuff s/b in jdbc method
          dbCurrent.test(_enterprise != null, "enterprise is null");
          dbCurrent.test(entityType != null, "entityType is null");
          dbCurrent.test(entityID > 0, "entityID <= 0");
          dbCurrent.test(attributeCode != null, "attributeCode is null");
          dbCurrent.test(strValOn != null, "valOn is null");
          dbCurrent.test(strEffOn != null, "effOn is null");
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:Enterprise: " + _enterprise);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EntityType: " + entityType);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EntityID: " + entityID);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:AttributeCode: " + attributeCode);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:NLSID: " + 1);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:ValOn: " + strValOn);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EffOn: " + strEffOn);

          strSP = "getSoftwareImage";
          blobNew = dbCurrent.getSoftwareImage(_enterprise, entityType, entityID, attributeCode,strValOn,strEffOn);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);
      } catch (Exception x) {
          logException(dbCurrent, strMethod, x, "Exception");
      } finally {
          if (dbCurrent!=null){
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return blobNew;
  }
  /**
   * getSoftwareImage transaction
   */
  public final COM.ibm.opicmpdh.objects.Blob getSoftwareImageVersion(Profile _prof, String _enterprise, String entityType, int entityID, String attributeCode) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      //Available connection to a database
      Database dbCurrent = null;
      COM.ibm.opicmpdh.objects.Blob blobNew = null;

      //Information about exception location
      String strMethod = "getSoftwareImageVersion";
      String strSP = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          //this stuff s/b in jdbc method
          dbCurrent.test(_enterprise != null, "enterprise is null");
          dbCurrent.test(entityType != null, "entityType is null");
          dbCurrent.test(entityID > 0, "entityID <= 0");
          dbCurrent.test(attributeCode != null, "attributeCode is null");
          dbCurrent.test(_prof.getReadLanguage().getNLSID() > 0, "nlsID <= 0");
          dbCurrent.test(_prof.getValOn() != null, "valOn is null");
          dbCurrent.test(_prof.getEffOn() != null, "effOn is null");
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:Enterprise: " + _enterprise);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EntityType: " + entityType);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EntityID: " + entityID);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:AttributeCode: " + attributeCode);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:NLSID: " + _prof.getReadLanguage().getNLSID());
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:ValOn: " + _prof.getValOn());
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EffOn: " + _prof.getEffOn());

          strSP = "getSoftwareImageVersion";
          blobNew = dbCurrent.getSoftwareImageVersion(_prof, _enterprise, entityType, entityID, attributeCode);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);
      } catch (Exception x) {
          logException(dbCurrent, strMethod, x, "Exception");
      } finally {
          if (dbCurrent!=null){
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return blobNew;
  }
  /**
   * getSoftwareImage transaction
   */
  public final COM.ibm.opicmpdh.objects.Blob getSoftwareImageVersion(Profile _prof, String _enterprise, String entityType, int entityID, String attributeCode, String _clientVersion) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      //Available connection to a database
      Database dbCurrent = null;
      COM.ibm.opicmpdh.objects.Blob blobNew = null;

      //Information about exception location
      String strMethod = "getSoftwareImageVersion";
      String strSP = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          //this stuff s/b in jdbc method
          dbCurrent.test(_enterprise != null, "enterprise is null");
          dbCurrent.test(entityType != null, "entityType is null");
          dbCurrent.test(entityID > 0, "entityID <= 0");
          dbCurrent.test(attributeCode != null, "attributeCode is null");
          dbCurrent.test(_prof.getReadLanguage().getNLSID() > 0, "nlsID <= 0");
          dbCurrent.test(_prof.getValOn() != null, "valOn is null");
          dbCurrent.test(_prof.getEffOn() != null, "effOn is null");
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:Enterprise: " + _enterprise);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EntityType: " + entityType);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EntityID: " + entityID);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:AttributeCode: " + attributeCode);
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:NLSID: " + _prof.getReadLanguage().getNLSID());
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:ValOn: " + _prof.getValOn());
          dbCurrent.debug(D.EBUG_DETAIL, "getBlob:EffOn: " + _prof.getEffOn());

          strSP = "getSoftwareImageVersion";
          blobNew = dbCurrent.getSoftwareImageVersion(_prof, _enterprise, entityType, entityID, attributeCode, _clientVersion);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);
      } catch (Exception x) {
          logException(dbCurrent, strMethod, x, "Exception");
      } finally {
          if (dbCurrent!=null){
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return blobNew;
  }
  /**
   * putSoftwareImage transaction
   */
  public final ReturnDataResultSetGroup putSoftwareImage(Profile _prof, String _enterprise, COM.ibm.opicmpdh.objects.Blob blobAttribute) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      //Dummy return cause void not good with RMI
      ReturnDataResultSetGroup rdrsgDummyReturn = new ReturnDataResultSetGroup();

      //Available connection to a database
      Database dbCurrent = null;

      //Information about exception location
      String strMethod = "putSoftwareImage";
      String strSP = null;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          //This stuff s/b in jdbc method
          dbCurrent.test(_enterprise != null, "putSoftwareImage:enterprise is null");
          dbCurrent.debug("putSoftwareImage:enterprise:length = " + _enterprise.length());
          dbCurrent.test(_enterprise.length() <= 8, "putSoftwareImage:enterprise length not <= 8 [passed length=" + _enterprise.length() + "]");
          dbCurrent.test(_prof.getOPWGID() > 0, "putSoftwareImage:OPWGID <= 0");
          dbCurrent.test(blobAttribute != null, "putSoftwareImage:blobAttribute is null");
          dbCurrent.debug(D.EBUG_DETAIL, "putSoftwareImage:Enterprise: " + _enterprise);
          dbCurrent.debug(D.EBUG_DETAIL, "putSoftwareImage:OPWGID: " + _prof.getOPWGID());

          strSP = "putSoftwareImage";

          dbCurrent.test(blobAttribute.getEntityType() != null, "putSoftwareImage:blobAttribute.getEntityType() is null");
          dbCurrent.debug("putSoftwareImage:blobAttribute.getEntityType():length = " + blobAttribute.getEntityType().length());
          dbCurrent.test(blobAttribute.getEntityType().length() <= 32, "putSoftwareImage:blobAttribute.getEntityType() length not <= 32 [passed length=" + blobAttribute.getEntityType().length() + "]");
          dbCurrent.test(blobAttribute.getEntityID() > 0, "putSoftwareImage:getEntityID() <= 0");
          dbCurrent.test(blobAttribute.getAttributeCode() != null, "putSoftwareImage:blobAttribute.getAttributeCode() is null");
          dbCurrent.debug("putSoftwareImage:blobAttribute.getAttributeCode():length = " + blobAttribute.getAttributeCode().length());
          dbCurrent.test(blobAttribute.getAttributeCode().length() <= 32, "putSoftwareImage:blobAttribute.getAttributeCode() length not <= 32 [passed length=" + blobAttribute.getAttributeCode().length() + "]");
          dbCurrent.test(blobAttribute.getBlobExtension() != null, "putSoftwareImage:blobAttribute.getBlobExtension() is null");
          dbCurrent.debug("putSoftwareImage:blobAttribute.getBlobExtension:length = " + blobAttribute.getBlobExtension().length());
          dbCurrent.test(blobAttribute.getBlobExtension().length() <= 32, "putSoftwareImage:blobAttribute.getBlobExtension() length not <= 32 [passed length=" + blobAttribute.getBlobExtension().length() + "]");
          dbCurrent.test(blobAttribute.getNLSID() > 0, "putSoftwareImage:getNLSID() <= 0");
          dbCurrent.test(blobAttribute.getControlBlock() != null, "putSoftwareImage:blobAttribute.getControlBlock() is null");
          dbCurrent.test(blobAttribute.getControlBlock().getEffFrom() != null, "putSoftwareImage:blobAttribute.getControlBlock().getEffFrom() is null");
          dbCurrent.debug("putSoftwareImage:blobAttribute.getControlBlock().getEffFrom():length = " + blobAttribute.getControlBlock().getEffFrom().length());
          dbCurrent.test(blobAttribute.getControlBlock().getEffFrom().length() <= 26, "putSoftwareImage:blobAttribute.getControlBlock().getEffFrom() length not <= 26 [passed length=" + blobAttribute.getControlBlock().getEffFrom().length() + "]");
          dbCurrent.test(Validate.isoDate(blobAttribute.getControlBlock().getEffFrom()) == true, "putSoftwareImage:blobAttribute.getControlBlock().getEffFrom() is not a valid ISO date [" + blobAttribute.getControlBlock().getEffFrom() + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
          dbCurrent.test(blobAttribute.getControlBlock().getEffTo() != null, "putSoftwareImage:blobAttribute.getControlBlock().getEffTo() is null");
          dbCurrent.debug("putSoftwareImage:blobAttribute.getControlBlock().getEffTo():length = " + blobAttribute.getControlBlock().getEffTo().length());
          dbCurrent.test(blobAttribute.getControlBlock().getEffTo().length() <= 26, "putSoftwareImage:blobAttribute.getControlBlock().getEffTo() length not <= 26 [passed length=" + blobAttribute.getControlBlock().getEffTo().length() + "]");
          dbCurrent.test(Validate.isoDate(blobAttribute.getControlBlock().getEffTo()) == true, "putSoftwareImage:blobAttribute.getControlBlock().getEffTo() is not a valid ISO date [" + blobAttribute.getControlBlock().getEffTo() + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
          dbCurrent.test(blobAttribute.getBAAttributeValue() != null, "putSoftwareImage:blobAttribute.getBAAttributeValue() is null");
          dbCurrent.putSoftwareImage(_prof,_enterprise, blobAttribute.getEntityType(), blobAttribute.getEntityID(), blobAttribute.getAttributeCode(), blobAttribute.getBlobExtension(), blobAttribute.getControlBlock().getEffFrom(), blobAttribute.getControlBlock().getEffTo(), blobAttribute.getBAAttributeValue());
          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          if (dbCurrent!=null){
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }

          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return rdrsgDummyReturn;
  }

  public final void cleanUpPartNo(EntityItem _ei) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException{

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "cleanUpPartNo";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.cleanUpPartNo(_ei);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
  }


  /*
   * Generates a MetaFlagMaintList
   */
  public final MetaFlagMaintList getMetaFlagMaintList(MetaMaintActionItem _mmai, Profile _prof, String _strAttributeCode) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      MetaFlagMaintList mlReturn = null;

      //Available connection to a database
      Database dbCurrent = null;
      String strMethod = "getMetaFlagMaintList";

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          mlReturn = dbCurrent.getMetaFlagMaintList(_mmai, _prof, _strAttributeCode);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      //Return the output
      return mlReturn;
  }

  /*
   * addFlagCodes
   */
  public final OPICMList addFlagCodes(Profile _prof, String _strAttributeCode, MetaFlagMaintItem[] _amfmi) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "addFlagCodes";
      //int iConnectionID = -1;
      OPICMList rList = new OPICMList();
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          rList = dbCurrent.addFlagCodes(_prof, _strAttributeCode, _amfmi);
          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }

      }

      return rList;
  }

  /*
   * expireFlagCodes
   */
  public final OPICMList expireFlagCodes(Profile _prof, String _strAttributeCode, MetaFlagMaintItem[] _amfmi) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "expireFlagCodes";
      //int iConnectionID = -1;
      OPICMList rList = new OPICMList();
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          rList = dbCurrent.expireFlagCodes(_prof, _strAttributeCode, _amfmi);
          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return rList;
  }

  /*
   * unexpireFlagCodes
   */
  public final OPICMList unexpireFlagCodes(Profile _prof, String _strAttributeCode, MetaFlagMaintItem[] _amfmi) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "unexpireFlagCodes";
     // int iConnectionID = -1;
      OPICMList rList = new OPICMList();
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          rList = dbCurrent.unexpireFlagCodes(_prof, _strAttributeCode, _amfmi);
          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }

      }

      return rList;
  }

  /*
   * clearCacheForAttribute
   */
  public final void clearCacheForAttribute(Profile _prof, String _strAttributeCode) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "clearCacheForAttribute";
      //int iConnectionID = -1;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.clearCacheForAttribute(_prof, _strAttributeCode);
          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }

      }    
  }

  /*
   * clearCacheForAttribute
   */
  public final EANMetaAttribute buildMetaAttribute(MetaFlagMaintList _mfml, EANMetaAttribute _meta) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "buildMetaAttribute";
      //int iConnectionID = -1;
      EANMetaAttribute meta = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          meta = dbCurrent.buildMetaAttribute(_mfml, _meta);
          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }

      }

      return meta;    
  }  

  public final void sendEmail(String _strTo, String _strSubject, String _strBody) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "sendEmail";
      String strSP = "";

      try {

          // Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          dbCurrent.sendEmail(_strTo, _strSubject, _strBody);
//        dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("RuntimeException trapped at: " + strMethod + rx);
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, "SQLException trapped at: " + strMethod + "." + strSP + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              // Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
  }

  /**
   * get target Version
   * @parameter profile
   * @parameter array
   * @parameter END OF TIME
   * @return array
   * @author Tony
   */
  public String[] getTargetVersions(Profile _prof, String[] _in, String _eod) throws MiddlewareException, MiddlewareShutdownInProgressException {

      // Available connection to a database
      Database dbCurrent = null;

      // Information about exception location
      String strMethod = "getTargetVersions";
      String [] out = null;
      try {

          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          out = dbCurrent.getTargetVersions(_prof,_in,_eod);
      } catch (Exception _ex) {
          _ex.printStackTrace();
      } finally {
          if (dbCurrent!=null){
              dbCurrent.freeStatement();
              dbCurrent.isPending();
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
      return out;
  }  

  /**
   * execute additional EANActionItem
   */
  public final Object execAdditional(Profile _prof, EANActionItem _eai, EntityItem[] _ei, Object[] _parms) throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {

      Object out = null;

      Database dbCurrent = null;
      //String strNow = null;

      //Information about exception location
      String strMethod = "executeAdditional EANActionItem";
      //int iConnectionID = -1;

      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          //iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          out = dbCurrent.execAdditional(_prof, _eai,_ei,_parms);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);

      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return out;
  }

  /**
   * execute a QueryActionItem
   */
  public final QueryList executeAction(Profile _prof, QueryActionItem _qai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;

      //Information about exception location
      String strMethod = "executeAction QueryActionItem";
     // int iConnectionID = -1;

      QueryList qReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
         // iConnectionID = dbCurrent.getConnectionID();

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          qReturn = _qai.exec(dbCurrent,_prof);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRollbackException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return qReturn;
  }
  
  /**
  * get cached xml - RCQ285768
  * @param entityType
  * @param entityId
  * @return
  * @throws RemoteException
  * @throws MiddlewareException
  * @throws MiddlewareShutdownInProgressException
  */
  public final String getCachedXML(String entityType, int entityId) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;

      //Information about exception location
      String strMethod = "getCachedXML";

      String strReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          strReturn = dbCurrent.getCachedXML(entityType, entityId);

          dbCurrent.commit();
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {
          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRequestException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return strReturn;
  }
  
  /**
   * get ABRs queued status
   */
  public final ABRQueueStatusList getABRQueueStatus(Profile _prof) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException {

      Database dbCurrent = null;

      //Information about exception location
      String strMethod = "getABRQueueStatus";

      ABRQueueStatusList abrReturn = null;
      try {

          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);

          dbCurrent.debug(D.EBUG_DETAIL, strMethod + " transaction");
          dbCurrent.isPending();
          dbCurrent.setAutoCommit(false);

          abrReturn = new ABRQueueStatusList(dbCurrent,_prof);

          dbCurrent.commit();

      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      } catch (SQLException x) {

          if (dbCurrent!=null){
              dbCurrent.debug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
              try {
                  dbCurrent.rollback();
              } catch (SQLException sx) {
                  dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
              }
          }else{
              D.ebug(D.EBUG_ERR, " SQLException trapped at: " + strMethod + " " + x);
          }

          throw new MiddlewareRequestException("(" + strMethod + ") SQLException: " + x);
      } finally {
          if (dbCurrent!=null){
              //Free any statement
              dbCurrent.freeStatement();
              dbCurrent.isPending();

              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return abrReturn;
  }

  public RowSelectableTable getDynaSearchTable(SearchActionItem sai) throws RemoteException, MiddlewareException,MiddlewareShutdownInProgressException
  {
	  RowSelectableTable rst = null;
	  Database dbCurrent = null;
      //Information about exception location
      String strMethod = "getDynaSearchTable";

      try {
          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          rst = sai.getDynaSearchTable(dbCurrent);
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      }  finally {
          if (dbCurrent!=null){
              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }
      return rst;
  }
  
  public String getInstanceName() throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException {
      return m_strInstanceName;
  }
  private void logException(Database dbCurrent, String strMethod, Exception rx, String exname){
      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      if (dbCurrent!=null){
          dbCurrent.debug(D.EBUG_ERR, " "+exname+" trapped at: " + strMethod + " " + rx);
          dbCurrent.debug(D.EBUG_ERR, "" + x);
      }else{
          D.ebug(D.EBUG_ERR, exname+" trapped at: " + strMethod + " " + rx);
          D.ebug(D.EBUG_ERR, "" + x);
      }
  } 
  public boolean hasChanges(Profile _prof, EntityItem[] _ei, String _fromDate, String _actionKey)
    throws RemoteException, MiddlewareException, MiddlewareShutdownInProgressException
  {
      boolean hasChgs = true;
      Database dbCurrent = null;

      //Information about exception location
      String strMethod = "hasChanges";
      try {
          //Grab a connection to use for this call
          dbCurrent = c_dbpOPICM.getConnection(strMethod);
          hasChgs = dbCurrent.hasChanges(_prof,_ei,_fromDate, _actionKey) ;
      } catch (RuntimeException rx) {
          logException(dbCurrent, strMethod, rx, "RuntimeException");
          throw new MiddlewareException("MRMI RuntimeException trapped at: " + strMethod + rx);
      } catch (MiddlewareException mx) {
          logException(dbCurrent, strMethod, mx, "MiddlewareException");
          throw mx;
      }  finally {
          if (dbCurrent!=null){
              //Free the connection
              c_dbpOPICM.freeConnection(dbCurrent);
              dbCurrent.debug(D.EBUG_DETAIL, strMethod + " complete");
          }else{
              D.ebug(D.EBUG_DETAIL, strMethod + " complete");
          }
      }

      return hasChgs;
  }
//
// TEMPLATE: REMOTEDBMETHOD.TXT (2020-06-15-05.16.43.622000)
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: remotedbmethod.txt,v $
// Revision 1.13  2009/03/11 18:15:55  wendy
// Fix jre1.4 compiler warnings
//
// Revision 1.12  2007/05/23 14:35:52  wendy
// Use dbcurrent for debug msgs when possible to get connectid
//
// Revision 1.11  2007/04/23 13:34:36  wendy
// Null dbcurrent causing JUI hangs, hiding other exceptions
//
// Revision 1.10  2005/02/03 17:06:10  roger
// Change javadoc directives
//
// Revision 1.9  2001/06/27 18:10:01  roger
// Remove displayMemory calls
//
// Revision 1.8  2001/06/16 01:59:08  roger
// More changes to support connectionID in all log output
//
// Revision 1.7  2001/06/15 22:14:13  roger
// D.ebug and T.est for Remote
//
// Revision 1.6  2001/03/16 04:26:16  roger
// Removed dead code
//
// Revision 1.5  2001/03/16 03:18:49  roger
// Added Log keyword
//

/**
 * Exposes stored procedure GBL99992 (Return the current server time values Now/Forever/Epoch) through RMI
 * @author generated code
 * @return the stored procedure output as a <code>ReturnDataResultSetGroup</code>
 * @exception RemoteException, MiddlewareException
 */
  public ReturnDataResultSetGroup remoteGBL99992
  (
  ) throws RemoteException, MiddlewareException {
    // The ResultSet
    ResultSet rs = null;
    // ReturnData containing result sets for the current operation
    ReturnDataResultSetGroup rdrsgReturn = new ReturnDataResultSetGroup(5, 1);
    String strReturnData = null;
    // Return value (for insert/update)
    ReturnID returnID = new ReturnID(0);
    // ReturnStatus from call to Stored Proc
    ReturnStatus returnStatus = new ReturnStatus(-1);
    // Information about exception location
    String strMethod = "remoteGBL99992";
    String strSP = "GBL99992";
    Database dbCurrent = null;
    MiddlewareException mx = null;

    try {
      // Connection to use for this call
      dbCurrent = c_dbpOPICM.getConnection(strMethod);
      dbCurrent.test(dbCurrent != null, "remoteGBL99992:Unable to acquire a connection");
      dbCurrent.debug(D.EBUG_DETAIL, "CALL GBL99992 from remoteGBL99992");
        // Build a line with combined parameter values
        StringBuffer strbParms = new StringBuffer();
        strbParms.append("" + returnStatus);
        String strParms = new String(strbParms);
        // Output the line
        debug(D.EBUG_DETAIL, "GBL99992 SPPARMS " + strParms);
      // If this is a UI instance only, tell caller to go elsewhere (not welcome here)
      if (c_strInstancePurpose.equals("U")) {
        throw new MiddlewareRequestException("Please use the WebSphere middleware instance for reports");
      }
try {
c_Connections.recordCall(RemoteServer.getClientHost(), "remoteGBL99992");
}
catch (Exception x) { debug("error getting host info"); }
      dbCurrent.setAutoCommit(false);
      dbCurrent.isPending();
      // Call the Stored Procedure
      rs = dbCurrent.callGBL99992
        (
          returnStatus
        );
      // If there is a resultset, return it
      if (rs != null) {
        // Place the ReturnDataResultSet into a ReturnDataResultSetGroup
        rdrsgReturn.addElement(new ReturnDataResultSet(rs, strSP));
      }
      dbCurrent.commit();
    }
    catch (RuntimeException rx) {
      mx = new MiddlewareException("(remoteGBL99992) RuntimeException: " + rx);
      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
          dbCurrent.debug(D.EBUG_ERR, "" + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
        D.ebug(D.EBUG_ERR, "" + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL99992 complete - error");
      throw mx;
    }
    catch (MiddlewareException x) {
      mx = x;
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL99992 complete - error");
      throw mx;      
    }
    catch (Exception x) {
      mx = new MiddlewareException("(remoteGBL99992) Exception: " + x);
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL99992 complete - error");
      throw mx;      
    }
    finally {
      // All ResultSets are processed, dispose of the SQL statement
      if (rs != null) {
        try {
          rs.close();
        }
        catch (SQLException x) {
          D.ebug(D.EBUG_ERR, "rs close failure in remoteGBL99992");
        }
        rs = null;
      }
      if (dbCurrent != null) {
        dbCurrent.freeStatement();
        c_dbpOPICM.freeConnection(dbCurrent);
      }
      /*if (mx != null) {
        D.ebug(D.EBUG_DETAIL, "GBL99992 complete - error");
        throw mx;
      } else {
        D.ebug(D.EBUG_DETAIL, "GBL99992 complete");
        // return the result vector (or void)
        return rdrsgReturn;
      }*/
    }
    D.ebug(D.EBUG_DETAIL, "GBL99992 complete");
    // return the result vector (or void)
    return rdrsgReturn;
  }

//
// TEMPLATE: REMOTEDBMETHOD.TXT (2020-06-15-05.16.43.622000)
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: remotedbmethod.txt,v $
// Revision 1.13  2009/03/11 18:15:55  wendy
// Fix jre1.4 compiler warnings
//
// Revision 1.12  2007/05/23 14:35:52  wendy
// Use dbcurrent for debug msgs when possible to get connectid
//
// Revision 1.11  2007/04/23 13:34:36  wendy
// Null dbcurrent causing JUI hangs, hiding other exceptions
//
// Revision 1.10  2005/02/03 17:06:10  roger
// Change javadoc directives
//
// Revision 1.9  2001/06/27 18:10:01  roger
// Remove displayMemory calls
//
// Revision 1.8  2001/06/16 01:59:08  roger
// More changes to support connectionID in all log output
//
// Revision 1.7  2001/06/15 22:14:13  roger
// D.ebug and T.est for Remote
//
// Revision 1.6  2001/03/16 04:26:16  roger
// Removed dead code
//
// Revision 1.5  2001/03/16 03:18:49  roger
// Added Log keyword
//

/**
 * Exposes stored procedure GBL9999 (Return the current server time values Now/Forever/Epoch) through RMI
 * @author generated code
 * @return the stored procedure output as a <code>ReturnDataResultSetGroup</code>
 * @exception RemoteException, MiddlewareException
 */
  public ReturnDataResultSetGroup remoteGBL9999
  (
  ) throws RemoteException, MiddlewareException {
    // The ResultSet
    ResultSet rs = null;
    // ReturnData containing result sets for the current operation
    ReturnDataResultSetGroup rdrsgReturn = new ReturnDataResultSetGroup(5, 1);
    String strReturnData = null;
    // Return value (for insert/update)
    ReturnID returnID = new ReturnID(0);
    // ReturnStatus from call to Stored Proc
    ReturnStatus returnStatus = new ReturnStatus(-1);
    // Information about exception location
    String strMethod = "remoteGBL9999";
    String strSP = "GBL9999";
    Database dbCurrent = null;
    MiddlewareException mx = null;

    try {
      // Connection to use for this call
      dbCurrent = c_dbpOPICM.getConnection(strMethod);
      dbCurrent.test(dbCurrent != null, "remoteGBL9999:Unable to acquire a connection");
      dbCurrent.debug(D.EBUG_DETAIL, "CALL GBL9999 from remoteGBL9999");
        // Build a line with combined parameter values
        StringBuffer strbParms = new StringBuffer();
        strbParms.append("" + returnStatus);
        String strParms = new String(strbParms);
        // Output the line
        debug(D.EBUG_DETAIL, "GBL9999 SPPARMS " + strParms);
      // If this is a UI instance only, tell caller to go elsewhere (not welcome here)
      if (c_strInstancePurpose.equals("U")) {
        throw new MiddlewareRequestException("Please use the WebSphere middleware instance for reports");
      }
try {
c_Connections.recordCall(RemoteServer.getClientHost(), "remoteGBL9999");
}
catch (Exception x) { debug("error getting host info"); }
      dbCurrent.setAutoCommit(false);
      dbCurrent.isPending();
      // Call the Stored Procedure
      rs = dbCurrent.callGBL9999
        (
          returnStatus
        );
      // If there is a resultset, return it
      if (rs != null) {
        // Place the ReturnDataResultSet into a ReturnDataResultSetGroup
        rdrsgReturn.addElement(new ReturnDataResultSet(rs, strSP));
      }
      dbCurrent.commit();
    }
    catch (RuntimeException rx) {
      mx = new MiddlewareException("(remoteGBL9999) RuntimeException: " + rx);
      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
          dbCurrent.debug(D.EBUG_ERR, "" + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
        D.ebug(D.EBUG_ERR, "" + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL9999 complete - error");
      throw mx;
    }
    catch (MiddlewareException x) {
      mx = x;
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL9999 complete - error");
      throw mx;      
    }
    catch (Exception x) {
      mx = new MiddlewareException("(remoteGBL9999) Exception: " + x);
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL9999 complete - error");
      throw mx;      
    }
    finally {
      // All ResultSets are processed, dispose of the SQL statement
      if (rs != null) {
        try {
          rs.close();
        }
        catch (SQLException x) {
          D.ebug(D.EBUG_ERR, "rs close failure in remoteGBL9999");
        }
        rs = null;
      }
      if (dbCurrent != null) {
        dbCurrent.freeStatement();
        c_dbpOPICM.freeConnection(dbCurrent);
      }
      /*if (mx != null) {
        D.ebug(D.EBUG_DETAIL, "GBL9999 complete - error");
        throw mx;
      } else {
        D.ebug(D.EBUG_DETAIL, "GBL9999 complete");
        // return the result vector (or void)
        return rdrsgReturn;
      }*/
    }
    D.ebug(D.EBUG_DETAIL, "GBL9999 complete");
    // return the result vector (or void)
    return rdrsgReturn;
  }

//
// TEMPLATE: REMOTEDBMETHOD.TXT (2020-06-15-05.16.43.622000)
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: remotedbmethod.txt,v $
// Revision 1.13  2009/03/11 18:15:55  wendy
// Fix jre1.4 compiler warnings
//
// Revision 1.12  2007/05/23 14:35:52  wendy
// Use dbcurrent for debug msgs when possible to get connectid
//
// Revision 1.11  2007/04/23 13:34:36  wendy
// Null dbcurrent causing JUI hangs, hiding other exceptions
//
// Revision 1.10  2005/02/03 17:06:10  roger
// Change javadoc directives
//
// Revision 1.9  2001/06/27 18:10:01  roger
// Remove displayMemory calls
//
// Revision 1.8  2001/06/16 01:59:08  roger
// More changes to support connectionID in all log output
//
// Revision 1.7  2001/06/15 22:14:13  roger
// D.ebug and T.est for Remote
//
// Revision 1.6  2001/03/16 04:26:16  roger
// Removed dead code
//
// Revision 1.5  2001/03/16 03:18:49  roger
// Added Log keyword
//

/**
 * Exposes stored procedure GBL0003 (Rule51:fetch an attribute value) through RMI
 * @author generated code
 * @return the stored procedure output as a <code>ReturnDataResultSetGroup</code>
 * @exception RemoteException, MiddlewareException
 */
  public ReturnDataResultSetGroup remoteGBL0003
  (
    String enterprise
  , String entityType
  , int entityID
  , String attributeCode
  , int nlsID
  ) throws RemoteException, MiddlewareException {
    // The ResultSet
    ResultSet rs = null;
    // ReturnData containing result sets for the current operation
    ReturnDataResultSetGroup rdrsgReturn = new ReturnDataResultSetGroup(5, 1);
    String strReturnData = null;
    // Return value (for insert/update)
    ReturnID returnID = new ReturnID(0);
    // ReturnStatus from call to Stored Proc
    ReturnStatus returnStatus = new ReturnStatus(-1);
    // Information about exception location
    String strMethod = "remoteGBL0003";
    String strSP = "GBL0003";
    Database dbCurrent = null;
    MiddlewareException mx = null;

    try {
      // Connection to use for this call
      dbCurrent = c_dbpOPICM.getConnection(strMethod);
      dbCurrent.test(dbCurrent != null, "remoteGBL0003:Unable to acquire a connection");
      dbCurrent.debug(D.EBUG_DETAIL, "CALL GBL0003 from remoteGBL0003");
        test(enterprise != null, "GBL0003:Enterprise is null");
        debug(D.EBUG_SPEW, "GBL0003:enterprise:length = " + (enterprise.getBytes("UTF8")).length);
        test((enterprise.getBytes("UTF8")).length <= 16, "GBL0003:enterprise length not <= 16 [passed length=" + (enterprise.getBytes("UTF8")).length + "]");
        test(entityType != null, "GBL0003:EntityType is null");
        debug(D.EBUG_SPEW, "GBL0003:entityType:length = " + (entityType.getBytes("UTF8")).length);
        test((entityType.getBytes("UTF8")).length <= 32, "GBL0003:entityType length not <= 32 [passed length=" + (entityType.getBytes("UTF8")).length + "]");
        test(attributeCode != null, "GBL0003:AttributeCode is null");
        debug(D.EBUG_SPEW, "GBL0003:attributeCode:length = " + (attributeCode.getBytes("UTF8")).length);
        test((attributeCode.getBytes("UTF8")).length <= 32, "GBL0003:attributeCode length not <= 32 [passed length=" + (attributeCode.getBytes("UTF8")).length + "]");
        // Build a line with combined parameter values
        StringBuffer strbParms = new StringBuffer();
        strbParms.append("" + returnStatus);
        strbParms.append(":" + enterprise);
        strbParms.append(":" + entityType);
        strbParms.append(":" + entityID);
        strbParms.append(":" + attributeCode);
        strbParms.append(":" + nlsID);
        String strParms = new String(strbParms);
        // Output the line
        debug(D.EBUG_DETAIL, "GBL0003 SPPARMS " + strParms);
      // If this is a UI instance only, tell caller to go elsewhere (not welcome here)
      if (c_strInstancePurpose.equals("U")) {
        throw new MiddlewareRequestException("Please use the WebSphere middleware instance for reports");
      }
try {
c_Connections.recordCall(RemoteServer.getClientHost(), "remoteGBL0003");
}
catch (Exception x) { debug("error getting host info"); }
      dbCurrent.setAutoCommit(false);
      dbCurrent.isPending();
      // Call the Stored Procedure
      rs = dbCurrent.callGBL0003
        (
          returnStatus
        , enterprise
        , entityType
        , entityID
        , attributeCode
        , nlsID
        );
      // If there is a resultset, return it
      if (rs != null) {
        // Place the ReturnDataResultSet into a ReturnDataResultSetGroup
        rdrsgReturn.addElement(new ReturnDataResultSet(rs, strSP));
      }
      dbCurrent.commit();
    }
    catch (RuntimeException rx) {
      mx = new MiddlewareException("(remoteGBL0003) RuntimeException: " + rx);
      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
          dbCurrent.debug(D.EBUG_ERR, "" + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
        D.ebug(D.EBUG_ERR, "" + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL0003 complete - error");
      throw mx;
    }
    catch (MiddlewareException x) {
      mx = x;
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL0003 complete - error");
      throw mx;      
    }
    catch (Exception x) {
      mx = new MiddlewareException("(remoteGBL0003) Exception: " + x);
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL0003 complete - error");
      throw mx;      
    }
    finally {
      // All ResultSets are processed, dispose of the SQL statement
      if (rs != null) {
        try {
          rs.close();
        }
        catch (SQLException x) {
          D.ebug(D.EBUG_ERR, "rs close failure in remoteGBL0003");
        }
        rs = null;
      }
      if (dbCurrent != null) {
        dbCurrent.freeStatement();
        c_dbpOPICM.freeConnection(dbCurrent);
      }
      /*if (mx != null) {
        D.ebug(D.EBUG_DETAIL, "GBL0003 complete - error");
        throw mx;
      } else {
        D.ebug(D.EBUG_DETAIL, "GBL0003 complete");
        // return the result vector (or void)
        return rdrsgReturn;
      }*/
    }
    D.ebug(D.EBUG_DETAIL, "GBL0003 complete");
    // return the result vector (or void)
    return rdrsgReturn;
  }

//
// TEMPLATE: REMOTEDBMETHOD.TXT (2020-06-15-05.16.43.622000)
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: remotedbmethod.txt,v $
// Revision 1.13  2009/03/11 18:15:55  wendy
// Fix jre1.4 compiler warnings
//
// Revision 1.12  2007/05/23 14:35:52  wendy
// Use dbcurrent for debug msgs when possible to get connectid
//
// Revision 1.11  2007/04/23 13:34:36  wendy
// Null dbcurrent causing JUI hangs, hiding other exceptions
//
// Revision 1.10  2005/02/03 17:06:10  roger
// Change javadoc directives
//
// Revision 1.9  2001/06/27 18:10:01  roger
// Remove displayMemory calls
//
// Revision 1.8  2001/06/16 01:59:08  roger
// More changes to support connectionID in all log output
//
// Revision 1.7  2001/06/15 22:14:13  roger
// D.ebug and T.est for Remote
//
// Revision 1.6  2001/03/16 04:26:16  roger
// Removed dead code
//
// Revision 1.5  2001/03/16 03:18:49  roger
// Added Log keyword
//

/**
 * Exposes stored procedure GBL0006 (Rule51:is this a rule 51 entitytype?) through RMI
 * @author generated code
 * @return the stored procedure output as a <code>ReturnDataResultSetGroup</code>
 * @exception RemoteException, MiddlewareException
 */
  public ReturnDataResultSetGroup remoteGBL0006
  (
    String enterprise
  , String entityType
  ) throws RemoteException, MiddlewareException {
    // The ResultSet
    ResultSet rs = null;
    // ReturnData containing result sets for the current operation
    ReturnDataResultSetGroup rdrsgReturn = new ReturnDataResultSetGroup(5, 1);
    String strReturnData = null;
    // Return value (for insert/update)
    ReturnID returnID = new ReturnID(0);
    // ReturnStatus from call to Stored Proc
    ReturnStatus returnStatus = new ReturnStatus(-1);
    // Information about exception location
    String strMethod = "remoteGBL0006";
    String strSP = "GBL0006";
    Database dbCurrent = null;
    MiddlewareException mx = null;

    try {
      // Connection to use for this call
      dbCurrent = c_dbpOPICM.getConnection(strMethod);
      dbCurrent.test(dbCurrent != null, "remoteGBL0006:Unable to acquire a connection");
      dbCurrent.debug(D.EBUG_DETAIL, "CALL GBL0006 from remoteGBL0006");
        test(enterprise != null, "GBL0006:Enterprise is null");
        debug(D.EBUG_SPEW, "GBL0006:enterprise:length = " + (enterprise.getBytes("UTF8")).length);
        test((enterprise.getBytes("UTF8")).length <= 16, "GBL0006:enterprise length not <= 16 [passed length=" + (enterprise.getBytes("UTF8")).length + "]");
        test(entityType != null, "GBL0006:EntityType is null");
        debug(D.EBUG_SPEW, "GBL0006:entityType:length = " + (entityType.getBytes("UTF8")).length);
        test((entityType.getBytes("UTF8")).length <= 32, "GBL0006:entityType length not <= 32 [passed length=" + (entityType.getBytes("UTF8")).length + "]");
        // Build a line with combined parameter values
        StringBuffer strbParms = new StringBuffer();
        strbParms.append("" + returnStatus);
        strbParms.append(":" + enterprise);
        strbParms.append(":" + entityType);
        String strParms = new String(strbParms);
        // Output the line
        debug(D.EBUG_DETAIL, "GBL0006 SPPARMS " + strParms);
      // If this is a UI instance only, tell caller to go elsewhere (not welcome here)
      if (c_strInstancePurpose.equals("U")) {
        throw new MiddlewareRequestException("Please use the WebSphere middleware instance for reports");
      }
try {
c_Connections.recordCall(RemoteServer.getClientHost(), "remoteGBL0006");
}
catch (Exception x) { debug("error getting host info"); }
      dbCurrent.setAutoCommit(false);
      dbCurrent.isPending();
      // Call the Stored Procedure
      rs = dbCurrent.callGBL0006
        (
          returnStatus
        , enterprise
        , entityType
        );
      // If there is a resultset, return it
      if (rs != null) {
        // Place the ReturnDataResultSet into a ReturnDataResultSetGroup
        rdrsgReturn.addElement(new ReturnDataResultSet(rs, strSP));
      }
      dbCurrent.commit();
    }
    catch (RuntimeException rx) {
      mx = new MiddlewareException("(remoteGBL0006) RuntimeException: " + rx);
      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
          dbCurrent.debug(D.EBUG_ERR, "" + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
        D.ebug(D.EBUG_ERR, "" + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL0006 complete - error");
      throw mx;
    }
    catch (MiddlewareException x) {
      mx = x;
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL0006 complete - error");
      throw mx;      
    }
    catch (Exception x) {
      mx = new MiddlewareException("(remoteGBL0006) Exception: " + x);
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL0006 complete - error");
      throw mx;      
    }
    finally {
      // All ResultSets are processed, dispose of the SQL statement
      if (rs != null) {
        try {
          rs.close();
        }
        catch (SQLException x) {
          D.ebug(D.EBUG_ERR, "rs close failure in remoteGBL0006");
        }
        rs = null;
      }
      if (dbCurrent != null) {
        dbCurrent.freeStatement();
        c_dbpOPICM.freeConnection(dbCurrent);
      }
      /*if (mx != null) {
        D.ebug(D.EBUG_DETAIL, "GBL0006 complete - error");
        throw mx;
      } else {
        D.ebug(D.EBUG_DETAIL, "GBL0006 complete");
        // return the result vector (or void)
        return rdrsgReturn;
      }*/
    }
    D.ebug(D.EBUG_DETAIL, "GBL0006 complete");
    // return the result vector (or void)
    return rdrsgReturn;
  }

//
// TEMPLATE: REMOTEDBMETHOD.TXT (2020-06-15-05.16.43.622000)
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: remotedbmethod.txt,v $
// Revision 1.13  2009/03/11 18:15:55  wendy
// Fix jre1.4 compiler warnings
//
// Revision 1.12  2007/05/23 14:35:52  wendy
// Use dbcurrent for debug msgs when possible to get connectid
//
// Revision 1.11  2007/04/23 13:34:36  wendy
// Null dbcurrent causing JUI hangs, hiding other exceptions
//
// Revision 1.10  2005/02/03 17:06:10  roger
// Change javadoc directives
//
// Revision 1.9  2001/06/27 18:10:01  roger
// Remove displayMemory calls
//
// Revision 1.8  2001/06/16 01:59:08  roger
// More changes to support connectionID in all log output
//
// Revision 1.7  2001/06/15 22:14:13  roger
// D.ebug and T.est for Remote
//
// Revision 1.6  2001/03/16 04:26:16  roger
// Removed dead code
//
// Revision 1.5  2001/03/16 03:18:49  roger
// Added Log keyword
//

/**
 * Exposes stored procedure GBL2028 (Return the current server time values Now/Forever/Epoch) through RMI
 * @author generated code
 * @return the stored procedure output as a <code>ReturnDataResultSetGroup</code>
 * @exception RemoteException, MiddlewareException
 */
  public ReturnDataResultSetGroup remoteGBL2028
  (
  ) throws RemoteException, MiddlewareException {
    // The ResultSet
    ResultSet rs = null;
    // ReturnData containing result sets for the current operation
    ReturnDataResultSetGroup rdrsgReturn = new ReturnDataResultSetGroup(5, 1);
    String strReturnData = null;
    // Return value (for insert/update)
    ReturnID returnID = new ReturnID(0);
    // ReturnStatus from call to Stored Proc
    ReturnStatus returnStatus = new ReturnStatus(-1);
    // Information about exception location
    String strMethod = "remoteGBL2028";
    String strSP = "GBL2028";
    Database dbCurrent = null;
    MiddlewareException mx = null;

    try {
      // Connection to use for this call
      dbCurrent = c_dbpOPICM.getConnection(strMethod);
      dbCurrent.test(dbCurrent != null, "remoteGBL2028:Unable to acquire a connection");
      dbCurrent.debug(D.EBUG_DETAIL, "CALL GBL2028 from remoteGBL2028");
        // Build a line with combined parameter values
        StringBuffer strbParms = new StringBuffer();
        strbParms.append("" + returnStatus);
        String strParms = new String(strbParms);
        // Output the line
        debug(D.EBUG_DETAIL, "GBL2028 SPPARMS " + strParms);
      // If this is a UI instance only, tell caller to go elsewhere (not welcome here)
      if (c_strInstancePurpose.equals("U")) {
        throw new MiddlewareRequestException("Please use the WebSphere middleware instance for reports");
      }
try {
c_Connections.recordCall(RemoteServer.getClientHost(), "remoteGBL2028");
}
catch (Exception x) { debug("error getting host info"); }
      dbCurrent.setAutoCommit(false);
      dbCurrent.isPending();
      // Call the Stored Procedure
      rs = dbCurrent.callGBL2028
        (
          returnStatus
        );
      // If there is a resultset, return it
      if (rs != null) {
        // Place the ReturnDataResultSet into a ReturnDataResultSetGroup
        rdrsgReturn.addElement(new ReturnDataResultSet(rs, strSP));
      }
      dbCurrent.commit();
    }
    catch (RuntimeException rx) {
      mx = new MiddlewareException("(remoteGBL2028) RuntimeException: " + rx);
      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
          dbCurrent.debug(D.EBUG_ERR, "" + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
        D.ebug(D.EBUG_ERR, "" + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL2028 complete - error");
      throw mx;
    }
    catch (MiddlewareException x) {
      mx = x;
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL2028 complete - error");
      throw mx;      
    }
    catch (Exception x) {
      mx = new MiddlewareException("(remoteGBL2028) Exception: " + x);
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL2028 complete - error");
      throw mx;      
    }
    finally {
      // All ResultSets are processed, dispose of the SQL statement
      if (rs != null) {
        try {
          rs.close();
        }
        catch (SQLException x) {
          D.ebug(D.EBUG_ERR, "rs close failure in remoteGBL2028");
        }
        rs = null;
      }
      if (dbCurrent != null) {
        dbCurrent.freeStatement();
        c_dbpOPICM.freeConnection(dbCurrent);
      }
      /*if (mx != null) {
        D.ebug(D.EBUG_DETAIL, "GBL2028 complete - error");
        throw mx;
      } else {
        D.ebug(D.EBUG_DETAIL, "GBL2028 complete");
        // return the result vector (or void)
        return rdrsgReturn;
      }*/
    }
    D.ebug(D.EBUG_DETAIL, "GBL2028 complete");
    // return the result vector (or void)
    return rdrsgReturn;
  }

//
// TEMPLATE: REMOTEDBMETHOD.TXT (2020-06-15-05.16.43.622000)
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: remotedbmethod.txt,v $
// Revision 1.13  2009/03/11 18:15:55  wendy
// Fix jre1.4 compiler warnings
//
// Revision 1.12  2007/05/23 14:35:52  wendy
// Use dbcurrent for debug msgs when possible to get connectid
//
// Revision 1.11  2007/04/23 13:34:36  wendy
// Null dbcurrent causing JUI hangs, hiding other exceptions
//
// Revision 1.10  2005/02/03 17:06:10  roger
// Change javadoc directives
//
// Revision 1.9  2001/06/27 18:10:01  roger
// Remove displayMemory calls
//
// Revision 1.8  2001/06/16 01:59:08  roger
// More changes to support connectionID in all log output
//
// Revision 1.7  2001/06/15 22:14:13  roger
// D.ebug and T.est for Remote
//
// Revision 1.6  2001/03/16 04:26:16  roger
// Removed dead code
//
// Revision 1.5  2001/03/16 03:18:49  roger
// Added Log keyword
//

/**
 * Exposes stored procedure GBL2031 (Insert Soft Lock) through RMI
 * @author generated code
 * @return the stored procedure output as a <code>ReturnDataResultSetGroup</code>
 * @exception RemoteException, MiddlewareException
 */
  public void remoteGBL2031
  (
    String enterprise
  , String entityType
  , int entityID
  , int lockLevel
  , String lockEntityType
  , int lockEntityID
  , String lockOwner
  , int oPWGID
  , int tranID
  ) throws RemoteException, MiddlewareException {
    // The ResultSet
    ResultSet rs = null;
    // ReturnData containing result sets for the current operation
    ReturnDataResultSetGroup rdrsgReturn = new ReturnDataResultSetGroup(5, 1);
    String strReturnData = null;
    // Return value (for insert/update)
    ReturnID returnID = new ReturnID(0);
    // ReturnStatus from call to Stored Proc
    ReturnStatus returnStatus = new ReturnStatus(-1);
    // Information about exception location
    String strMethod = "remoteGBL2031";
    String strSP = "GBL2031";
    Database dbCurrent = null;
    MiddlewareException mx = null;

    try {
      // Connection to use for this call
      dbCurrent = c_dbpOPICM.getConnection(strMethod);
      dbCurrent.test(dbCurrent != null, "remoteGBL2031:Unable to acquire a connection");
      dbCurrent.debug(D.EBUG_DETAIL, "CALL GBL2031 from remoteGBL2031");
        test(enterprise != null, "GBL2031:Enterprise is null");
        debug(D.EBUG_SPEW, "GBL2031:enterprise:length = " + (enterprise.getBytes("UTF8")).length);
        test((enterprise.getBytes("UTF8")).length <= 16, "GBL2031:enterprise length not <= 16 [passed length=" + (enterprise.getBytes("UTF8")).length + "]");
        test(entityType != null, "GBL2031:EntityType is null");
        debug(D.EBUG_SPEW, "GBL2031:entityType:length = " + (entityType.getBytes("UTF8")).length);
        test((entityType.getBytes("UTF8")).length <= 32, "GBL2031:entityType length not <= 32 [passed length=" + (entityType.getBytes("UTF8")).length + "]");
        test(lockEntityType != null, "GBL2031:LockEntityType is null");
        debug(D.EBUG_SPEW, "GBL2031:lockEntityType:length = " + (lockEntityType.getBytes("UTF8")).length);
        test((lockEntityType.getBytes("UTF8")).length <= 32, "GBL2031:lockEntityType length not <= 32 [passed length=" + (lockEntityType.getBytes("UTF8")).length + "]");
        test(lockOwner != null, "GBL2031:LockOwner is null");
        debug(D.EBUG_SPEW, "GBL2031:lockOwner:length = " + (lockOwner.getBytes("UTF8")).length);
        test((lockOwner.getBytes("UTF8")).length <= 32, "GBL2031:lockOwner length not <= 32 [passed length=" + (lockOwner.getBytes("UTF8")).length + "]");
        // Build a line with combined parameter values
        StringBuffer strbParms = new StringBuffer();
        strbParms.append("" + returnStatus);
        strbParms.append(":" + enterprise);
        strbParms.append(":" + entityType);
        strbParms.append(":" + entityID);
        strbParms.append(":" + lockLevel);
        strbParms.append(":" + lockEntityType);
        strbParms.append(":" + lockEntityID);
        strbParms.append(":" + lockOwner);
        strbParms.append(":" + oPWGID);
        strbParms.append(":" + tranID);
        String strParms = new String(strbParms);
        // Output the line
        debug(D.EBUG_DETAIL, "GBL2031 SPPARMS " + strParms);
      // If this is a UI instance only, tell caller to go elsewhere (not welcome here)
      if (c_strInstancePurpose.equals("U")) {
        throw new MiddlewareRequestException("Please use the WebSphere middleware instance for reports");
      }
try {
c_Connections.recordCall(RemoteServer.getClientHost(), "remoteGBL2031");
}
catch (Exception x) { debug("error getting host info"); }
      dbCurrent.setAutoCommit(false);
      dbCurrent.isPending();
      // Call the Stored Procedure
      dbCurrent.callGBL2031
        (
          returnStatus
        , enterprise
        , entityType
        , entityID
        , lockLevel
        , lockEntityType
        , lockEntityID
        , lockOwner
        , oPWGID
        , tranID
        );
      // If there is a resultset, return it
      if (rs != null) {
        // Place the ReturnDataResultSet into a ReturnDataResultSetGroup
        rdrsgReturn.addElement(new ReturnDataResultSet(rs, strSP));
      }
      dbCurrent.commit();
    }
    catch (RuntimeException rx) {
      mx = new MiddlewareException("(remoteGBL2031) RuntimeException: " + rx);
      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
          dbCurrent.debug(D.EBUG_ERR, "" + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
        D.ebug(D.EBUG_ERR, "" + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL2031 complete - error");
      throw mx;
    }
    catch (MiddlewareException x) {
      mx = x;
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL2031 complete - error");
      throw mx;      
    }
    catch (Exception x) {
      mx = new MiddlewareException("(remoteGBL2031) Exception: " + x);
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL2031 complete - error");
      throw mx;      
    }
    finally {
      // All ResultSets are processed, dispose of the SQL statement
      if (rs != null) {
        try {
          rs.close();
        }
        catch (SQLException x) {
          D.ebug(D.EBUG_ERR, "rs close failure in remoteGBL2031");
        }
        rs = null;
      }
      if (dbCurrent != null) {
        dbCurrent.freeStatement();
        c_dbpOPICM.freeConnection(dbCurrent);
      }
      /*if (mx != null) {
        D.ebug(D.EBUG_DETAIL, "GBL2031 complete - error");
        throw mx;
      } else {
        D.ebug(D.EBUG_DETAIL, "GBL2031 complete");
        // return the result vector (or void)
        return;
      }*/
    }
    D.ebug(D.EBUG_DETAIL, "GBL2031 complete");
    // return the result vector (or void)
    return;
  }

//
// TEMPLATE: REMOTEDBMETHOD.TXT (2020-06-15-05.16.43.622000)
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: remotedbmethod.txt,v $
// Revision 1.13  2009/03/11 18:15:55  wendy
// Fix jre1.4 compiler warnings
//
// Revision 1.12  2007/05/23 14:35:52  wendy
// Use dbcurrent for debug msgs when possible to get connectid
//
// Revision 1.11  2007/04/23 13:34:36  wendy
// Null dbcurrent causing JUI hangs, hiding other exceptions
//
// Revision 1.10  2005/02/03 17:06:10  roger
// Change javadoc directives
//
// Revision 1.9  2001/06/27 18:10:01  roger
// Remove displayMemory calls
//
// Revision 1.8  2001/06/16 01:59:08  roger
// More changes to support connectionID in all log output
//
// Revision 1.7  2001/06/15 22:14:13  roger
// D.ebug and T.est for Remote
//
// Revision 1.6  2001/03/16 04:26:16  roger
// Removed dead code
//
// Revision 1.5  2001/03/16 03:18:49  roger
// Added Log keyword
//

/**
 * Exposes stored procedure GBL2055 (Return all parent entity ids related to the passed entity through the relatortype for the given Enterprise.) through RMI
 * @author generated code
 * @return the stored procedure output as a <code>ReturnDataResultSetGroup</code>
 * @exception RemoteException, MiddlewareException
 */
  public ReturnDataResultSetGroup remoteGBL2055
  (
    String enterprise
  , String relatorType
  , String entity2Type
  , int entity2ID
  , String valOn
  , String effOn
  ) throws RemoteException, MiddlewareException {
    // The ResultSet
    ResultSet rs = null;
    // ReturnData containing result sets for the current operation
    ReturnDataResultSetGroup rdrsgReturn = new ReturnDataResultSetGroup(5, 1);
    String strReturnData = null;
    // Return value (for insert/update)
    ReturnID returnID = new ReturnID(0);
    // ReturnStatus from call to Stored Proc
    ReturnStatus returnStatus = new ReturnStatus(-1);
    // Information about exception location
    String strMethod = "remoteGBL2055";
    String strSP = "GBL2055";
    Database dbCurrent = null;
    MiddlewareException mx = null;

    try {
      // Connection to use for this call
      dbCurrent = c_dbpOPICM.getConnection(strMethod);
      dbCurrent.test(dbCurrent != null, "remoteGBL2055:Unable to acquire a connection");
      dbCurrent.debug(D.EBUG_DETAIL, "CALL GBL2055 from remoteGBL2055");
        test(enterprise != null, "GBL2055:Enterprise is null");
        debug(D.EBUG_SPEW, "GBL2055:enterprise:length = " + (enterprise.getBytes("UTF8")).length);
        test((enterprise.getBytes("UTF8")).length <= 16, "GBL2055:enterprise length not <= 16 [passed length=" + (enterprise.getBytes("UTF8")).length + "]");
        test(relatorType != null, "GBL2055:RelatorType is null");
        debug(D.EBUG_SPEW, "GBL2055:relatorType:length = " + (relatorType.getBytes("UTF8")).length);
        test((relatorType.getBytes("UTF8")).length <= 32, "GBL2055:relatorType length not <= 32 [passed length=" + (relatorType.getBytes("UTF8")).length + "]");
        test(entity2Type != null, "GBL2055:Entity2Type is null");
        debug(D.EBUG_SPEW, "GBL2055:entity2Type:length = " + (entity2Type.getBytes("UTF8")).length);
        test((entity2Type.getBytes("UTF8")).length <= 32, "GBL2055:entity2Type length not <= 32 [passed length=" + (entity2Type.getBytes("UTF8")).length + "]");
        test(valOn != null, "GBL2055:ValOn is null");
        debug(D.EBUG_SPEW, "GBL2055:valOn:length = " + (valOn.getBytes("UTF8")).length);
        test((valOn.getBytes("UTF8")).length <= 26, "GBL2055:valOn length not <= 26 [passed length=" + (valOn.getBytes("UTF8")).length + "]");
        test(Validate.isoDate(valOn) == true, "GBL2055:valOn is not a valid ISO date [" + valOn + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
        test(effOn != null, "GBL2055:EffOn is null");
        debug(D.EBUG_SPEW, "GBL2055:effOn:length = " + (effOn.getBytes("UTF8")).length);
        test((effOn.getBytes("UTF8")).length <= 26, "GBL2055:effOn length not <= 26 [passed length=" + (effOn.getBytes("UTF8")).length + "]");
        test(Validate.isoDate(effOn) == true, "GBL2055:effOn is not a valid ISO date [" + effOn + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
        // Build a line with combined parameter values
        StringBuffer strbParms = new StringBuffer();
        strbParms.append("" + returnStatus);
        strbParms.append(":" + enterprise);
        strbParms.append(":" + relatorType);
        strbParms.append(":" + entity2Type);
        strbParms.append(":" + entity2ID);
        strbParms.append(":" + valOn);
        strbParms.append(":" + effOn);
        String strParms = new String(strbParms);
        // Output the line
        debug(D.EBUG_DETAIL, "GBL2055 SPPARMS " + strParms);
      // If this is a UI instance only, tell caller to go elsewhere (not welcome here)
      if (c_strInstancePurpose.equals("U")) {
        throw new MiddlewareRequestException("Please use the WebSphere middleware instance for reports");
      }
try {
c_Connections.recordCall(RemoteServer.getClientHost(), "remoteGBL2055");
}
catch (Exception x) { debug("error getting host info"); }
      dbCurrent.setAutoCommit(false);
      dbCurrent.isPending();
      // Call the Stored Procedure
      rs = dbCurrent.callGBL2055
        (
          returnStatus
        , enterprise
        , relatorType
        , entity2Type
        , entity2ID
        , valOn
        , effOn
        );
      // If there is a resultset, return it
      if (rs != null) {
        // Place the ReturnDataResultSet into a ReturnDataResultSetGroup
        rdrsgReturn.addElement(new ReturnDataResultSet(rs, strSP));
      }
      dbCurrent.commit();
    }
    catch (RuntimeException rx) {
      mx = new MiddlewareException("(remoteGBL2055) RuntimeException: " + rx);
      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
          dbCurrent.debug(D.EBUG_ERR, "" + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
        D.ebug(D.EBUG_ERR, "" + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL2055 complete - error");
      throw mx;
    }
    catch (MiddlewareException x) {
      mx = x;
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL2055 complete - error");
      throw mx;      
    }
    catch (Exception x) {
      mx = new MiddlewareException("(remoteGBL2055) Exception: " + x);
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL2055 complete - error");
      throw mx;      
    }
    finally {
      // All ResultSets are processed, dispose of the SQL statement
      if (rs != null) {
        try {
          rs.close();
        }
        catch (SQLException x) {
          D.ebug(D.EBUG_ERR, "rs close failure in remoteGBL2055");
        }
        rs = null;
      }
      if (dbCurrent != null) {
        dbCurrent.freeStatement();
        c_dbpOPICM.freeConnection(dbCurrent);
      }
      /*if (mx != null) {
        D.ebug(D.EBUG_DETAIL, "GBL2055 complete - error");
        throw mx;
      } else {
        D.ebug(D.EBUG_DETAIL, "GBL2055 complete");
        // return the result vector (or void)
        return rdrsgReturn;
      }*/
    }
    D.ebug(D.EBUG_DETAIL, "GBL2055 complete");
    // return the result vector (or void)
    return rdrsgReturn;
  }

//
// TEMPLATE: REMOTEDBMETHOD.TXT (2020-06-15-05.16.43.622000)
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: remotedbmethod.txt,v $
// Revision 1.13  2009/03/11 18:15:55  wendy
// Fix jre1.4 compiler warnings
//
// Revision 1.12  2007/05/23 14:35:52  wendy
// Use dbcurrent for debug msgs when possible to get connectid
//
// Revision 1.11  2007/04/23 13:34:36  wendy
// Null dbcurrent causing JUI hangs, hiding other exceptions
//
// Revision 1.10  2005/02/03 17:06:10  roger
// Change javadoc directives
//
// Revision 1.9  2001/06/27 18:10:01  roger
// Remove displayMemory calls
//
// Revision 1.8  2001/06/16 01:59:08  roger
// More changes to support connectionID in all log output
//
// Revision 1.7  2001/06/15 22:14:13  roger
// D.ebug and T.est for Remote
//
// Revision 1.6  2001/03/16 04:26:16  roger
// Removed dead code
//
// Revision 1.5  2001/03/16 03:18:49  roger
// Added Log keyword
//

/**
 * Exposes stored procedure GBL7003 ( Returns class and NLS Description based upon the given ActionKey NLSID) through RMI
 * @author generated code
 * @return the stored procedure output as a <code>ReturnDataResultSetGroup</code>
 * @exception RemoteException, MiddlewareException
 */
  public ReturnDataResultSetGroup remoteGBL7003
  (
    String enterprise
  , String actionType
  , int nlsID
  , String valOn
  , String effOn
  ) throws RemoteException, MiddlewareException {
    // The ResultSet
    ResultSet rs = null;
    // ReturnData containing result sets for the current operation
    ReturnDataResultSetGroup rdrsgReturn = new ReturnDataResultSetGroup(5, 1);
    String strReturnData = null;
    // Return value (for insert/update)
    ReturnID returnID = new ReturnID(0);
    // ReturnStatus from call to Stored Proc
    ReturnStatus returnStatus = new ReturnStatus(-1);
    // Information about exception location
    String strMethod = "remoteGBL7003";
    String strSP = "GBL7003";
    Database dbCurrent = null;
    MiddlewareException mx = null;

    try {
      // Connection to use for this call
      dbCurrent = c_dbpOPICM.getConnection(strMethod);
      dbCurrent.test(dbCurrent != null, "remoteGBL7003:Unable to acquire a connection");
      dbCurrent.debug(D.EBUG_DETAIL, "CALL GBL7003 from remoteGBL7003");
        test(enterprise != null, "GBL7003:Enterprise is null");
        debug(D.EBUG_SPEW, "GBL7003:enterprise:length = " + (enterprise.getBytes("UTF8")).length);
        test((enterprise.getBytes("UTF8")).length <= 16, "GBL7003:enterprise length not <= 16 [passed length=" + (enterprise.getBytes("UTF8")).length + "]");
        test(actionType != null, "GBL7003:ActionType is null");
        debug(D.EBUG_SPEW, "GBL7003:actionType:length = " + (actionType.getBytes("UTF8")).length);
        test((actionType.getBytes("UTF8")).length <= 32, "GBL7003:actionType length not <= 32 [passed length=" + (actionType.getBytes("UTF8")).length + "]");
        test(valOn != null, "GBL7003:ValOn is null");
        debug(D.EBUG_SPEW, "GBL7003:valOn:length = " + (valOn.getBytes("UTF8")).length);
        test((valOn.getBytes("UTF8")).length <= 26, "GBL7003:valOn length not <= 26 [passed length=" + (valOn.getBytes("UTF8")).length + "]");
        test(Validate.isoDate(valOn) == true, "GBL7003:valOn is not a valid ISO date [" + valOn + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
        test(effOn != null, "GBL7003:EffOn is null");
        debug(D.EBUG_SPEW, "GBL7003:effOn:length = " + (effOn.getBytes("UTF8")).length);
        test((effOn.getBytes("UTF8")).length <= 26, "GBL7003:effOn length not <= 26 [passed length=" + (effOn.getBytes("UTF8")).length + "]");
        test(Validate.isoDate(effOn) == true, "GBL7003:effOn is not a valid ISO date [" + effOn + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
        // Build a line with combined parameter values
        StringBuffer strbParms = new StringBuffer();
        strbParms.append("" + returnStatus);
        strbParms.append(":" + enterprise);
        strbParms.append(":" + actionType);
        strbParms.append(":" + nlsID);
        strbParms.append(":" + valOn);
        strbParms.append(":" + effOn);
        String strParms = new String(strbParms);
        // Output the line
        debug(D.EBUG_DETAIL, "GBL7003 SPPARMS " + strParms);
      // If this is a UI instance only, tell caller to go elsewhere (not welcome here)
      if (c_strInstancePurpose.equals("U")) {
        throw new MiddlewareRequestException("Please use the WebSphere middleware instance for reports");
      }
try {
c_Connections.recordCall(RemoteServer.getClientHost(), "remoteGBL7003");
}
catch (Exception x) { debug("error getting host info"); }
      dbCurrent.setAutoCommit(false);
      dbCurrent.isPending();
      // Call the Stored Procedure
      rs = dbCurrent.callGBL7003
        (
          returnStatus
        , enterprise
        , actionType
        , nlsID
        , valOn
        , effOn
        );
      // If there is a resultset, return it
      if (rs != null) {
        // Place the ReturnDataResultSet into a ReturnDataResultSetGroup
        rdrsgReturn.addElement(new ReturnDataResultSet(rs, strSP));
      }
      dbCurrent.commit();
    }
    catch (RuntimeException rx) {
      mx = new MiddlewareException("(remoteGBL7003) RuntimeException: " + rx);
      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
          dbCurrent.debug(D.EBUG_ERR, "" + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
        D.ebug(D.EBUG_ERR, "" + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7003 complete - error");
      throw mx;
    }
    catch (MiddlewareException x) {
      mx = x;
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7003 complete - error");
      throw mx;      
    }
    catch (Exception x) {
      mx = new MiddlewareException("(remoteGBL7003) Exception: " + x);
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7003 complete - error");
      throw mx;      
    }
    finally {
      // All ResultSets are processed, dispose of the SQL statement
      if (rs != null) {
        try {
          rs.close();
        }
        catch (SQLException x) {
          D.ebug(D.EBUG_ERR, "rs close failure in remoteGBL7003");
        }
        rs = null;
      }
      if (dbCurrent != null) {
        dbCurrent.freeStatement();
        c_dbpOPICM.freeConnection(dbCurrent);
      }
      /*if (mx != null) {
        D.ebug(D.EBUG_DETAIL, "GBL7003 complete - error");
        throw mx;
      } else {
        D.ebug(D.EBUG_DETAIL, "GBL7003 complete");
        // return the result vector (or void)
        return rdrsgReturn;
      }*/
    }
    D.ebug(D.EBUG_DETAIL, "GBL7003 complete");
    // return the result vector (or void)
    return rdrsgReturn;
  }

//
// TEMPLATE: REMOTEDBMETHOD.TXT (2020-06-15-05.16.43.622000)
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: remotedbmethod.txt,v $
// Revision 1.13  2009/03/11 18:15:55  wendy
// Fix jre1.4 compiler warnings
//
// Revision 1.12  2007/05/23 14:35:52  wendy
// Use dbcurrent for debug msgs when possible to get connectid
//
// Revision 1.11  2007/04/23 13:34:36  wendy
// Null dbcurrent causing JUI hangs, hiding other exceptions
//
// Revision 1.10  2005/02/03 17:06:10  roger
// Change javadoc directives
//
// Revision 1.9  2001/06/27 18:10:01  roger
// Remove displayMemory calls
//
// Revision 1.8  2001/06/16 01:59:08  roger
// More changes to support connectionID in all log output
//
// Revision 1.7  2001/06/15 22:14:13  roger
// D.ebug and T.est for Remote
//
// Revision 1.6  2001/03/16 04:26:16  roger
// Removed dead code
//
// Revision 1.5  2001/03/16 03:18:49  roger
// Added Log keyword
//

/**
 * Exposes stored procedure GBL7008 ( Returns class and NLS Description based upon the given ActionKey NLSID) through RMI
 * @author generated code
 * @return the stored procedure output as a <code>ReturnDataResultSetGroup</code>
 * @exception RemoteException, MiddlewareException
 */
  public ReturnDataResultSetGroup remoteGBL7008
  (
    String enterprise
  , String actionType
  , String valOn
  , String effOn
  ) throws RemoteException, MiddlewareException {
    // The ResultSet
    ResultSet rs = null;
    // ReturnData containing result sets for the current operation
    ReturnDataResultSetGroup rdrsgReturn = new ReturnDataResultSetGroup(5, 1);
    String strReturnData = null;
    // Return value (for insert/update)
    ReturnID returnID = new ReturnID(0);
    // ReturnStatus from call to Stored Proc
    ReturnStatus returnStatus = new ReturnStatus(-1);
    // Information about exception location
    String strMethod = "remoteGBL7008";
    String strSP = "GBL7008";
    Database dbCurrent = null;
    MiddlewareException mx = null;

    try {
      // Connection to use for this call
      dbCurrent = c_dbpOPICM.getConnection(strMethod);
      dbCurrent.test(dbCurrent != null, "remoteGBL7008:Unable to acquire a connection");
      dbCurrent.debug(D.EBUG_DETAIL, "CALL GBL7008 from remoteGBL7008");
        test(enterprise != null, "GBL7008:Enterprise is null");
        debug(D.EBUG_SPEW, "GBL7008:enterprise:length = " + (enterprise.getBytes("UTF8")).length);
        test((enterprise.getBytes("UTF8")).length <= 16, "GBL7008:enterprise length not <= 16 [passed length=" + (enterprise.getBytes("UTF8")).length + "]");
        test(actionType != null, "GBL7008:ActionType is null");
        debug(D.EBUG_SPEW, "GBL7008:actionType:length = " + (actionType.getBytes("UTF8")).length);
        test((actionType.getBytes("UTF8")).length <= 32, "GBL7008:actionType length not <= 32 [passed length=" + (actionType.getBytes("UTF8")).length + "]");
        test(valOn != null, "GBL7008:ValOn is null");
        debug(D.EBUG_SPEW, "GBL7008:valOn:length = " + (valOn.getBytes("UTF8")).length);
        test((valOn.getBytes("UTF8")).length <= 26, "GBL7008:valOn length not <= 26 [passed length=" + (valOn.getBytes("UTF8")).length + "]");
        test(Validate.isoDate(valOn) == true, "GBL7008:valOn is not a valid ISO date [" + valOn + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
        test(effOn != null, "GBL7008:EffOn is null");
        debug(D.EBUG_SPEW, "GBL7008:effOn:length = " + (effOn.getBytes("UTF8")).length);
        test((effOn.getBytes("UTF8")).length <= 26, "GBL7008:effOn length not <= 26 [passed length=" + (effOn.getBytes("UTF8")).length + "]");
        test(Validate.isoDate(effOn) == true, "GBL7008:effOn is not a valid ISO date [" + effOn + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
        // Build a line with combined parameter values
        StringBuffer strbParms = new StringBuffer();
        strbParms.append("" + returnStatus);
        strbParms.append(":" + enterprise);
        strbParms.append(":" + actionType);
        strbParms.append(":" + valOn);
        strbParms.append(":" + effOn);
        String strParms = new String(strbParms);
        // Output the line
        debug(D.EBUG_DETAIL, "GBL7008 SPPARMS " + strParms);
      // If this is a UI instance only, tell caller to go elsewhere (not welcome here)
      if (c_strInstancePurpose.equals("U")) {
        throw new MiddlewareRequestException("Please use the WebSphere middleware instance for reports");
      }
try {
c_Connections.recordCall(RemoteServer.getClientHost(), "remoteGBL7008");
}
catch (Exception x) { debug("error getting host info"); }
      dbCurrent.setAutoCommit(false);
      dbCurrent.isPending();
      // Call the Stored Procedure
      rs = dbCurrent.callGBL7008
        (
          returnStatus
        , enterprise
        , actionType
        , valOn
        , effOn
        );
      // If there is a resultset, return it
      if (rs != null) {
        // Place the ReturnDataResultSet into a ReturnDataResultSetGroup
        rdrsgReturn.addElement(new ReturnDataResultSet(rs, strSP));
      }
      dbCurrent.commit();
    }
    catch (RuntimeException rx) {
      mx = new MiddlewareException("(remoteGBL7008) RuntimeException: " + rx);
      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
          dbCurrent.debug(D.EBUG_ERR, "" + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
        D.ebug(D.EBUG_ERR, "" + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7008 complete - error");
      throw mx;
    }
    catch (MiddlewareException x) {
      mx = x;
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7008 complete - error");
      throw mx;      
    }
    catch (Exception x) {
      mx = new MiddlewareException("(remoteGBL7008) Exception: " + x);
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7008 complete - error");
      throw mx;      
    }
    finally {
      // All ResultSets are processed, dispose of the SQL statement
      if (rs != null) {
        try {
          rs.close();
        }
        catch (SQLException x) {
          D.ebug(D.EBUG_ERR, "rs close failure in remoteGBL7008");
        }
        rs = null;
      }
      if (dbCurrent != null) {
        dbCurrent.freeStatement();
        c_dbpOPICM.freeConnection(dbCurrent);
      }
      /*if (mx != null) {
        D.ebug(D.EBUG_DETAIL, "GBL7008 complete - error");
        throw mx;
      } else {
        D.ebug(D.EBUG_DETAIL, "GBL7008 complete");
        // return the result vector (or void)
        return rdrsgReturn;
      }*/
    }
    D.ebug(D.EBUG_DETAIL, "GBL7008 complete");
    // return the result vector (or void)
    return rdrsgReturn;
  }

//
// TEMPLATE: REMOTEDBMETHOD.TXT (2020-06-15-05.16.43.622000)
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: remotedbmethod.txt,v $
// Revision 1.13  2009/03/11 18:15:55  wendy
// Fix jre1.4 compiler warnings
//
// Revision 1.12  2007/05/23 14:35:52  wendy
// Use dbcurrent for debug msgs when possible to get connectid
//
// Revision 1.11  2007/04/23 13:34:36  wendy
// Null dbcurrent causing JUI hangs, hiding other exceptions
//
// Revision 1.10  2005/02/03 17:06:10  roger
// Change javadoc directives
//
// Revision 1.9  2001/06/27 18:10:01  roger
// Remove displayMemory calls
//
// Revision 1.8  2001/06/16 01:59:08  roger
// More changes to support connectionID in all log output
//
// Revision 1.7  2001/06/15 22:14:13  roger
// D.ebug and T.est for Remote
//
// Revision 1.6  2001/03/16 04:26:16  roger
// Removed dead code
//
// Revision 1.5  2001/03/16 03:18:49  roger
// Added Log keyword
//

/**
 * Exposes stored procedure GBL7030 ( Returns Attributes of an Action) through RMI
 * @author generated code
 * @return the stored procedure output as a <code>ReturnDataResultSetGroup</code>
 * @exception RemoteException, MiddlewareException
 */
  public ReturnDataResultSetGroup remoteGBL7030
  (
    String enterprise
  , String actionType
  , String valOn
  , String effOn
  ) throws RemoteException, MiddlewareException {
    // The ResultSet
    ResultSet rs = null;
    // ReturnData containing result sets for the current operation
    ReturnDataResultSetGroup rdrsgReturn = new ReturnDataResultSetGroup(5, 1);
    String strReturnData = null;
    // Return value (for insert/update)
    ReturnID returnID = new ReturnID(0);
    // ReturnStatus from call to Stored Proc
    ReturnStatus returnStatus = new ReturnStatus(-1);
    // Information about exception location
    String strMethod = "remoteGBL7030";
    String strSP = "GBL7030";
    Database dbCurrent = null;
    MiddlewareException mx = null;

    try {
      // Connection to use for this call
      dbCurrent = c_dbpOPICM.getConnection(strMethod);
      dbCurrent.test(dbCurrent != null, "remoteGBL7030:Unable to acquire a connection");
      dbCurrent.debug(D.EBUG_DETAIL, "CALL GBL7030 from remoteGBL7030");
        test(enterprise != null, "GBL7030:Enterprise is null");
        debug(D.EBUG_SPEW, "GBL7030:enterprise:length = " + (enterprise.getBytes("UTF8")).length);
        test((enterprise.getBytes("UTF8")).length <= 16, "GBL7030:enterprise length not <= 16 [passed length=" + (enterprise.getBytes("UTF8")).length + "]");
        test(actionType != null, "GBL7030:ActionType is null");
        debug(D.EBUG_SPEW, "GBL7030:actionType:length = " + (actionType.getBytes("UTF8")).length);
        test((actionType.getBytes("UTF8")).length <= 32, "GBL7030:actionType length not <= 32 [passed length=" + (actionType.getBytes("UTF8")).length + "]");
        test(valOn != null, "GBL7030:ValOn is null");
        debug(D.EBUG_SPEW, "GBL7030:valOn:length = " + (valOn.getBytes("UTF8")).length);
        test((valOn.getBytes("UTF8")).length <= 26, "GBL7030:valOn length not <= 26 [passed length=" + (valOn.getBytes("UTF8")).length + "]");
        test(Validate.isoDate(valOn) == true, "GBL7030:valOn is not a valid ISO date [" + valOn + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
        test(effOn != null, "GBL7030:EffOn is null");
        debug(D.EBUG_SPEW, "GBL7030:effOn:length = " + (effOn.getBytes("UTF8")).length);
        test((effOn.getBytes("UTF8")).length <= 26, "GBL7030:effOn length not <= 26 [passed length=" + (effOn.getBytes("UTF8")).length + "]");
        test(Validate.isoDate(effOn) == true, "GBL7030:effOn is not a valid ISO date [" + effOn + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
        // Build a line with combined parameter values
        StringBuffer strbParms = new StringBuffer();
        strbParms.append("" + returnStatus);
        strbParms.append(":" + enterprise);
        strbParms.append(":" + actionType);
        strbParms.append(":" + valOn);
        strbParms.append(":" + effOn);
        String strParms = new String(strbParms);
        // Output the line
        debug(D.EBUG_DETAIL, "GBL7030 SPPARMS " + strParms);
      // If this is a UI instance only, tell caller to go elsewhere (not welcome here)
      if (c_strInstancePurpose.equals("U")) {
        throw new MiddlewareRequestException("Please use the WebSphere middleware instance for reports");
      }
try {
c_Connections.recordCall(RemoteServer.getClientHost(), "remoteGBL7030");
}
catch (Exception x) { debug("error getting host info"); }
      dbCurrent.setAutoCommit(false);
      dbCurrent.isPending();
      // Call the Stored Procedure
      rs = dbCurrent.callGBL7030
        (
          returnStatus
        , enterprise
        , actionType
        , valOn
        , effOn
        );
      // If there is a resultset, return it
      if (rs != null) {
        // Place the ReturnDataResultSet into a ReturnDataResultSetGroup
        rdrsgReturn.addElement(new ReturnDataResultSet(rs, strSP));
      }
      dbCurrent.commit();
    }
    catch (RuntimeException rx) {
      mx = new MiddlewareException("(remoteGBL7030) RuntimeException: " + rx);
      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
          dbCurrent.debug(D.EBUG_ERR, "" + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
        D.ebug(D.EBUG_ERR, "" + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7030 complete - error");
      throw mx;
    }
    catch (MiddlewareException x) {
      mx = x;
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7030 complete - error");
      throw mx;      
    }
    catch (Exception x) {
      mx = new MiddlewareException("(remoteGBL7030) Exception: " + x);
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7030 complete - error");
      throw mx;      
    }
    finally {
      // All ResultSets are processed, dispose of the SQL statement
      if (rs != null) {
        try {
          rs.close();
        }
        catch (SQLException x) {
          D.ebug(D.EBUG_ERR, "rs close failure in remoteGBL7030");
        }
        rs = null;
      }
      if (dbCurrent != null) {
        dbCurrent.freeStatement();
        c_dbpOPICM.freeConnection(dbCurrent);
      }
      /*if (mx != null) {
        D.ebug(D.EBUG_DETAIL, "GBL7030 complete - error");
        throw mx;
      } else {
        D.ebug(D.EBUG_DETAIL, "GBL7030 complete");
        // return the result vector (or void)
        return rdrsgReturn;
      }*/
    }
    D.ebug(D.EBUG_DETAIL, "GBL7030 complete");
    // return the result vector (or void)
    return rdrsgReturn;
  }

//
// TEMPLATE: REMOTEDBMETHOD.TXT (2020-06-15-05.16.43.622000)
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: remotedbmethod.txt,v $
// Revision 1.13  2009/03/11 18:15:55  wendy
// Fix jre1.4 compiler warnings
//
// Revision 1.12  2007/05/23 14:35:52  wendy
// Use dbcurrent for debug msgs when possible to get connectid
//
// Revision 1.11  2007/04/23 13:34:36  wendy
// Null dbcurrent causing JUI hangs, hiding other exceptions
//
// Revision 1.10  2005/02/03 17:06:10  roger
// Change javadoc directives
//
// Revision 1.9  2001/06/27 18:10:01  roger
// Remove displayMemory calls
//
// Revision 1.8  2001/06/16 01:59:08  roger
// More changes to support connectionID in all log output
//
// Revision 1.7  2001/06/15 22:14:13  roger
// D.ebug and T.est for Remote
//
// Revision 1.6  2001/03/16 04:26:16  roger
// Removed dead code
//
// Revision 1.5  2001/03/16 03:18:49  roger
// Added Log keyword
//

/**
 * Exposes stored procedure GBL7065 (Get all Allowable Flag Values for the given Workgroup) through RMI
 * @author generated code
 * @return the stored procedure output as a <code>ReturnDataResultSetGroup</code>
 * @exception RemoteException, MiddlewareException
 */
  public ReturnDataResultSetGroup remoteGBL7065
  (
    String enterprise
  , int wGID
  , String valOn
  , String effOn
  ) throws RemoteException, MiddlewareException {
    // The ResultSet
    ResultSet rs = null;
    // ReturnData containing result sets for the current operation
    ReturnDataResultSetGroup rdrsgReturn = new ReturnDataResultSetGroup(5, 1);
    String strReturnData = null;
    // Return value (for insert/update)
    ReturnID returnID = new ReturnID(0);
    // ReturnStatus from call to Stored Proc
    ReturnStatus returnStatus = new ReturnStatus(-1);
    // Information about exception location
    String strMethod = "remoteGBL7065";
    String strSP = "GBL7065";
    Database dbCurrent = null;
    MiddlewareException mx = null;

    try {
      // Connection to use for this call
      dbCurrent = c_dbpOPICM.getConnection(strMethod);
      dbCurrent.test(dbCurrent != null, "remoteGBL7065:Unable to acquire a connection");
      dbCurrent.debug(D.EBUG_DETAIL, "CALL GBL7065 from remoteGBL7065");
        test(enterprise != null, "GBL7065:Enterprise is null");
        debug(D.EBUG_SPEW, "GBL7065:enterprise:length = " + (enterprise.getBytes("UTF8")).length);
        test((enterprise.getBytes("UTF8")).length <= 16, "GBL7065:enterprise length not <= 16 [passed length=" + (enterprise.getBytes("UTF8")).length + "]");
        test(valOn != null, "GBL7065:ValOn is null");
        debug(D.EBUG_SPEW, "GBL7065:valOn:length = " + (valOn.getBytes("UTF8")).length);
        test((valOn.getBytes("UTF8")).length <= 26, "GBL7065:valOn length not <= 26 [passed length=" + (valOn.getBytes("UTF8")).length + "]");
        test(Validate.isoDate(valOn) == true, "GBL7065:valOn is not a valid ISO date [" + valOn + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
        test(effOn != null, "GBL7065:EffOn is null");
        debug(D.EBUG_SPEW, "GBL7065:effOn:length = " + (effOn.getBytes("UTF8")).length);
        test((effOn.getBytes("UTF8")).length <= 26, "GBL7065:effOn length not <= 26 [passed length=" + (effOn.getBytes("UTF8")).length + "]");
        test(Validate.isoDate(effOn) == true, "GBL7065:effOn is not a valid ISO date [" + effOn + "] should be 'yyyy-MM-dd-HH.mm.ss.SSS000'");
        // Build a line with combined parameter values
        StringBuffer strbParms = new StringBuffer();
        strbParms.append("" + returnStatus);
        strbParms.append(":" + enterprise);
        strbParms.append(":" + wGID);
        strbParms.append(":" + valOn);
        strbParms.append(":" + effOn);
        String strParms = new String(strbParms);
        // Output the line
        debug(D.EBUG_DETAIL, "GBL7065 SPPARMS " + strParms);
      // If this is a UI instance only, tell caller to go elsewhere (not welcome here)
      if (c_strInstancePurpose.equals("U")) {
        throw new MiddlewareRequestException("Please use the WebSphere middleware instance for reports");
      }
try {
c_Connections.recordCall(RemoteServer.getClientHost(), "remoteGBL7065");
}
catch (Exception x) { debug("error getting host info"); }
      dbCurrent.setAutoCommit(false);
      dbCurrent.isPending();
      // Call the Stored Procedure
      rs = dbCurrent.callGBL7065
        (
          returnStatus
        , enterprise
        , wGID
        , valOn
        , effOn
        );
      // If there is a resultset, return it
      if (rs != null) {
        // Place the ReturnDataResultSet into a ReturnDataResultSetGroup
        rdrsgReturn.addElement(new ReturnDataResultSet(rs, strSP));
      }
      dbCurrent.commit();
    }
    catch (RuntimeException rx) {
      mx = new MiddlewareException("(remoteGBL7065) RuntimeException: " + rx);
      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
          dbCurrent.debug(D.EBUG_ERR, "" + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
        D.ebug(D.EBUG_ERR, "" + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7065 complete - error");
      throw mx;
    }
    catch (MiddlewareException x) {
      mx = x;
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7065 complete - error");
      throw mx;      
    }
    catch (Exception x) {
      mx = new MiddlewareException("(remoteGBL7065) Exception: " + x);
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7065 complete - error");
      throw mx;      
    }
    finally {
      // All ResultSets are processed, dispose of the SQL statement
      if (rs != null) {
        try {
          rs.close();
        }
        catch (SQLException x) {
          D.ebug(D.EBUG_ERR, "rs close failure in remoteGBL7065");
        }
        rs = null;
      }
      if (dbCurrent != null) {
        dbCurrent.freeStatement();
        c_dbpOPICM.freeConnection(dbCurrent);
      }
      /*if (mx != null) {
        D.ebug(D.EBUG_DETAIL, "GBL7065 complete - error");
        throw mx;
      } else {
        D.ebug(D.EBUG_DETAIL, "GBL7065 complete");
        // return the result vector (or void)
        return rdrsgReturn;
      }*/
    }
    D.ebug(D.EBUG_DETAIL, "GBL7065 complete");
    // return the result vector (or void)
    return rdrsgReturn;
  }

//
// TEMPLATE: REMOTEDBMETHOD.TXT (2020-06-15-05.16.43.622000)
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: remotedbmethod.txt,v $
// Revision 1.13  2009/03/11 18:15:55  wendy
// Fix jre1.4 compiler warnings
//
// Revision 1.12  2007/05/23 14:35:52  wendy
// Use dbcurrent for debug msgs when possible to get connectid
//
// Revision 1.11  2007/04/23 13:34:36  wendy
// Null dbcurrent causing JUI hangs, hiding other exceptions
//
// Revision 1.10  2005/02/03 17:06:10  roger
// Change javadoc directives
//
// Revision 1.9  2001/06/27 18:10:01  roger
// Remove displayMemory calls
//
// Revision 1.8  2001/06/16 01:59:08  roger
// More changes to support connectionID in all log output
//
// Revision 1.7  2001/06/15 22:14:13  roger
// D.ebug and T.est for Remote
//
// Revision 1.6  2001/03/16 04:26:16  roger
// Removed dead code
//
// Revision 1.5  2001/03/16 03:18:49  roger
// Added Log keyword
//

/**
 * Exposes stored procedure GBL7549 (delete MetaColOrder info) through RMI
 * @author generated code
 * @return the stored procedure output as a <code>ReturnDataResultSetGroup</code>
 * @exception RemoteException, MiddlewareException
 */
  public void remoteGBL7549
  (
    String enterprise
  , int oPWGID
  , String entityType
  ) throws RemoteException, MiddlewareException {
    // The ResultSet
    ResultSet rs = null;
    // ReturnData containing result sets for the current operation
    ReturnDataResultSetGroup rdrsgReturn = new ReturnDataResultSetGroup(5, 1);
    String strReturnData = null;
    // Return value (for insert/update)
    ReturnID returnID = new ReturnID(0);
    // ReturnStatus from call to Stored Proc
    ReturnStatus returnStatus = new ReturnStatus(-1);
    // Information about exception location
    String strMethod = "remoteGBL7549";
    String strSP = "GBL7549";
    Database dbCurrent = null;
    MiddlewareException mx = null;

    try {
      // Connection to use for this call
      dbCurrent = c_dbpOPICM.getConnection(strMethod);
      dbCurrent.test(dbCurrent != null, "remoteGBL7549:Unable to acquire a connection");
      dbCurrent.debug(D.EBUG_DETAIL, "CALL GBL7549 from remoteGBL7549");
        test(enterprise != null, "GBL7549:Enterprise is null");
        debug(D.EBUG_SPEW, "GBL7549:enterprise:length = " + (enterprise.getBytes("UTF8")).length);
        test((enterprise.getBytes("UTF8")).length <= 16, "GBL7549:enterprise length not <= 16 [passed length=" + (enterprise.getBytes("UTF8")).length + "]");
        test(entityType != null, "GBL7549:EntityType is null");
        debug(D.EBUG_SPEW, "GBL7549:entityType:length = " + (entityType.getBytes("UTF8")).length);
        test((entityType.getBytes("UTF8")).length <= 32, "GBL7549:entityType length not <= 32 [passed length=" + (entityType.getBytes("UTF8")).length + "]");
        // Build a line with combined parameter values
        StringBuffer strbParms = new StringBuffer();
        strbParms.append("" + returnStatus);
        strbParms.append(":" + enterprise);
        strbParms.append(":" + oPWGID);
        strbParms.append(":" + entityType);
        String strParms = new String(strbParms);
        // Output the line
        debug(D.EBUG_DETAIL, "GBL7549 SPPARMS " + strParms);
      // If this is a UI instance only, tell caller to go elsewhere (not welcome here)
      if (c_strInstancePurpose.equals("U")) {
        throw new MiddlewareRequestException("Please use the WebSphere middleware instance for reports");
      }
try {
c_Connections.recordCall(RemoteServer.getClientHost(), "remoteGBL7549");
}
catch (Exception x) { debug("error getting host info"); }
      dbCurrent.setAutoCommit(false);
      dbCurrent.isPending();
      // Call the Stored Procedure
      dbCurrent.callGBL7549
        (
          returnStatus
        , enterprise
        , oPWGID
        , entityType
        );
      // If there is a resultset, return it
      if (rs != null) {
        // Place the ReturnDataResultSet into a ReturnDataResultSetGroup
        rdrsgReturn.addElement(new ReturnDataResultSet(rs, strSP));
      }
      dbCurrent.commit();
    }
    catch (RuntimeException rx) {
      mx = new MiddlewareException("(remoteGBL7549) RuntimeException: " + rx);
      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
          dbCurrent.debug(D.EBUG_ERR, "" + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
        D.ebug(D.EBUG_ERR, "" + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7549 complete - error");
      throw mx;
    }
    catch (MiddlewareException x) {
      mx = x;
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7549 complete - error");
      throw mx;      
    }
    catch (Exception x) {
      mx = new MiddlewareException("(remoteGBL7549) Exception: " + x);
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7549 complete - error");
      throw mx;      
    }
    finally {
      // All ResultSets are processed, dispose of the SQL statement
      if (rs != null) {
        try {
          rs.close();
        }
        catch (SQLException x) {
          D.ebug(D.EBUG_ERR, "rs close failure in remoteGBL7549");
        }
        rs = null;
      }
      if (dbCurrent != null) {
        dbCurrent.freeStatement();
        c_dbpOPICM.freeConnection(dbCurrent);
      }
      /*if (mx != null) {
        D.ebug(D.EBUG_DETAIL, "GBL7549 complete - error");
        throw mx;
      } else {
        D.ebug(D.EBUG_DETAIL, "GBL7549 complete");
        // return the result vector (or void)
        return;
      }*/
    }
    D.ebug(D.EBUG_DETAIL, "GBL7549 complete");
    // return the result vector (or void)
    return;
  }

//
// TEMPLATE: REMOTEDBMETHOD.TXT (2020-06-15-05.16.43.622000)
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: remotedbmethod.txt,v $
// Revision 1.13  2009/03/11 18:15:55  wendy
// Fix jre1.4 compiler warnings
//
// Revision 1.12  2007/05/23 14:35:52  wendy
// Use dbcurrent for debug msgs when possible to get connectid
//
// Revision 1.11  2007/04/23 13:34:36  wendy
// Null dbcurrent causing JUI hangs, hiding other exceptions
//
// Revision 1.10  2005/02/03 17:06:10  roger
// Change javadoc directives
//
// Revision 1.9  2001/06/27 18:10:01  roger
// Remove displayMemory calls
//
// Revision 1.8  2001/06/16 01:59:08  roger
// More changes to support connectionID in all log output
//
// Revision 1.7  2001/06/15 22:14:13  roger
// D.ebug and T.est for Remote
//
// Revision 1.6  2001/03/16 04:26:16  roger
// Removed dead code
//
// Revision 1.5  2001/03/16 03:18:49  roger
// Added Log keyword
//

/**
 * Exposes stored procedure GBL7567 (Grab a specific error message from the MetaDescription table) through RMI
 * @author generated code
 * @return the stored procedure output as a <code>ReturnDataResultSetGroup</code>
 * @exception RemoteException, MiddlewareException
 */
  public ReturnDataResultSetGroup remoteGBL7567
  (
    String enterprise
  , String descriptionType
  , int nlsID
  ) throws RemoteException, MiddlewareException {
    // The ResultSet
    ResultSet rs = null;
    // ReturnData containing result sets for the current operation
    ReturnDataResultSetGroup rdrsgReturn = new ReturnDataResultSetGroup(5, 1);
    String strReturnData = null;
    // Return value (for insert/update)
    ReturnID returnID = new ReturnID(0);
    // ReturnStatus from call to Stored Proc
    ReturnStatus returnStatus = new ReturnStatus(-1);
    // Information about exception location
    String strMethod = "remoteGBL7567";
    String strSP = "GBL7567";
    Database dbCurrent = null;
    MiddlewareException mx = null;

    try {
      // Connection to use for this call
      dbCurrent = c_dbpOPICM.getConnection(strMethod);
      dbCurrent.test(dbCurrent != null, "remoteGBL7567:Unable to acquire a connection");
      dbCurrent.debug(D.EBUG_DETAIL, "CALL GBL7567 from remoteGBL7567");
        test(enterprise != null, "GBL7567:Enterprise is null");
        debug(D.EBUG_SPEW, "GBL7567:enterprise:length = " + (enterprise.getBytes("UTF8")).length);
        test((enterprise.getBytes("UTF8")).length <= 16, "GBL7567:enterprise length not <= 16 [passed length=" + (enterprise.getBytes("UTF8")).length + "]");
        test(descriptionType != null, "GBL7567:DescriptionType is null");
        debug(D.EBUG_SPEW, "GBL7567:descriptionType:length = " + (descriptionType.getBytes("UTF8")).length);
        test((descriptionType.getBytes("UTF8")).length <= 32, "GBL7567:descriptionType length not <= 32 [passed length=" + (descriptionType.getBytes("UTF8")).length + "]");
        // Build a line with combined parameter values
        StringBuffer strbParms = new StringBuffer();
        strbParms.append("" + returnStatus);
        strbParms.append(":" + enterprise);
        strbParms.append(":" + descriptionType);
        strbParms.append(":" + nlsID);
        String strParms = new String(strbParms);
        // Output the line
        debug(D.EBUG_DETAIL, "GBL7567 SPPARMS " + strParms);
      // If this is a UI instance only, tell caller to go elsewhere (not welcome here)
      if (c_strInstancePurpose.equals("U")) {
        throw new MiddlewareRequestException("Please use the WebSphere middleware instance for reports");
      }
try {
c_Connections.recordCall(RemoteServer.getClientHost(), "remoteGBL7567");
}
catch (Exception x) { debug("error getting host info"); }
      dbCurrent.setAutoCommit(false);
      dbCurrent.isPending();
      // Call the Stored Procedure
      rs = dbCurrent.callGBL7567
        (
          returnStatus
        , enterprise
        , descriptionType
        , nlsID
        );
      // If there is a resultset, return it
      if (rs != null) {
        // Place the ReturnDataResultSet into a ReturnDataResultSetGroup
        rdrsgReturn.addElement(new ReturnDataResultSet(rs, strSP));
      }
      dbCurrent.commit();
    }
    catch (RuntimeException rx) {
      mx = new MiddlewareException("(remoteGBL7567) RuntimeException: " + rx);
      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
          dbCurrent.debug(D.EBUG_ERR, "" + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
        D.ebug(D.EBUG_ERR, "" + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7567 complete - error");
      throw mx;
    }
    catch (MiddlewareException x) {
      mx = x;
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7567 complete - error");
      throw mx;      
    }
    catch (Exception x) {
      mx = new MiddlewareException("(remoteGBL7567) Exception: " + x);
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL7567 complete - error");
      throw mx;      
    }
    finally {
      // All ResultSets are processed, dispose of the SQL statement
      if (rs != null) {
        try {
          rs.close();
        }
        catch (SQLException x) {
          D.ebug(D.EBUG_ERR, "rs close failure in remoteGBL7567");
        }
        rs = null;
      }
      if (dbCurrent != null) {
        dbCurrent.freeStatement();
        c_dbpOPICM.freeConnection(dbCurrent);
      }
      /*if (mx != null) {
        D.ebug(D.EBUG_DETAIL, "GBL7567 complete - error");
        throw mx;
      } else {
        D.ebug(D.EBUG_DETAIL, "GBL7567 complete");
        // return the result vector (or void)
        return rdrsgReturn;
      }*/
    }
    D.ebug(D.EBUG_DETAIL, "GBL7567 complete");
    // return the result vector (or void)
    return rdrsgReturn;
  }

//
// TEMPLATE: REMOTEDBMETHOD.TXT (2020-06-15-05.16.43.622000)
//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: remotedbmethod.txt,v $
// Revision 1.13  2009/03/11 18:15:55  wendy
// Fix jre1.4 compiler warnings
//
// Revision 1.12  2007/05/23 14:35:52  wendy
// Use dbcurrent for debug msgs when possible to get connectid
//
// Revision 1.11  2007/04/23 13:34:36  wendy
// Null dbcurrent causing JUI hangs, hiding other exceptions
//
// Revision 1.10  2005/02/03 17:06:10  roger
// Change javadoc directives
//
// Revision 1.9  2001/06/27 18:10:01  roger
// Remove displayMemory calls
//
// Revision 1.8  2001/06/16 01:59:08  roger
// More changes to support connectionID in all log output
//
// Revision 1.7  2001/06/15 22:14:13  roger
// D.ebug and T.est for Remote
//
// Revision 1.6  2001/03/16 04:26:16  roger
// Removed dead code
//
// Revision 1.5  2001/03/16 03:18:49  roger
// Added Log keyword
//

/**
 * Exposes stored procedure GBL8004 (Pulls back the Action Template used in GBL8000 for API use) through RMI
 * @author generated code
 * @return the stored procedure output as a <code>ReturnDataResultSetGroup</code>
 * @exception RemoteException, MiddlewareException
 */
  public ReturnDataResultSetGroup remoteGBL8004
  (
    String enterprise
  , String entityType
  , String actionType
  ) throws RemoteException, MiddlewareException {
    // The ResultSet
    ResultSet rs = null;
    // ReturnData containing result sets for the current operation
    ReturnDataResultSetGroup rdrsgReturn = new ReturnDataResultSetGroup(5, 1);
    String strReturnData = null;
    // Return value (for insert/update)
    ReturnID returnID = new ReturnID(0);
    // ReturnStatus from call to Stored Proc
    ReturnStatus returnStatus = new ReturnStatus(-1);
    // Information about exception location
    String strMethod = "remoteGBL8004";
    String strSP = "GBL8004";
    Database dbCurrent = null;
    MiddlewareException mx = null;

    try {
      // Connection to use for this call
      dbCurrent = c_dbpOPICM.getConnection(strMethod);
      dbCurrent.test(dbCurrent != null, "remoteGBL8004:Unable to acquire a connection");
      dbCurrent.debug(D.EBUG_DETAIL, "CALL GBL8004 from remoteGBL8004");
        test(enterprise != null, "GBL8004:Enterprise is null");
        debug(D.EBUG_SPEW, "GBL8004:enterprise:length = " + (enterprise.getBytes("UTF8")).length);
        test((enterprise.getBytes("UTF8")).length <= 16, "GBL8004:enterprise length not <= 16 [passed length=" + (enterprise.getBytes("UTF8")).length + "]");
        test(entityType != null, "GBL8004:EntityType is null");
        debug(D.EBUG_SPEW, "GBL8004:entityType:length = " + (entityType.getBytes("UTF8")).length);
        test((entityType.getBytes("UTF8")).length <= 32, "GBL8004:entityType length not <= 32 [passed length=" + (entityType.getBytes("UTF8")).length + "]");
        test(actionType != null, "GBL8004:ActionType is null");
        debug(D.EBUG_SPEW, "GBL8004:actionType:length = " + (actionType.getBytes("UTF8")).length);
        test((actionType.getBytes("UTF8")).length <= 32, "GBL8004:actionType length not <= 32 [passed length=" + (actionType.getBytes("UTF8")).length + "]");
        // Build a line with combined parameter values
        StringBuffer strbParms = new StringBuffer();
        strbParms.append("" + returnStatus);
        strbParms.append(":" + enterprise);
        strbParms.append(":" + entityType);
        strbParms.append(":" + actionType);
        String strParms = new String(strbParms);
        // Output the line
        debug(D.EBUG_DETAIL, "GBL8004 SPPARMS " + strParms);
      // If this is a UI instance only, tell caller to go elsewhere (not welcome here)
      if (c_strInstancePurpose.equals("U")) {
        throw new MiddlewareRequestException("Please use the WebSphere middleware instance for reports");
      }
try {
c_Connections.recordCall(RemoteServer.getClientHost(), "remoteGBL8004");
}
catch (Exception x) { debug("error getting host info"); }
      dbCurrent.setAutoCommit(false);
      dbCurrent.isPending();
      // Call the Stored Procedure
      rs = dbCurrent.callGBL8004
        (
          returnStatus
        , enterprise
        , entityType
        , actionType
        );
      // If there is a resultset, return it
      if (rs != null) {
        // Place the ReturnDataResultSet into a ReturnDataResultSetGroup
        rdrsgReturn.addElement(new ReturnDataResultSet(rs, strSP));
      }
      dbCurrent.commit();
    }
    catch (RuntimeException rx) {
      mx = new MiddlewareException("(remoteGBL8004) RuntimeException: " + rx);
      StringWriter writer = new StringWriter();
      rx.printStackTrace(new PrintWriter(writer));
      String x = writer.toString();
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
          dbCurrent.debug(D.EBUG_ERR, "" + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "RuntimeException trapped at: " + strMethod + "." + strSP + " " + rx);
        D.ebug(D.EBUG_ERR, "" + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL8004 complete - error");
      throw mx;
    }
    catch (MiddlewareException x) {
      mx = x;
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "MiddlewareException trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL8004 complete - error");
      throw mx;      
    }
    catch (Exception x) {
      mx = new MiddlewareException("(remoteGBL8004) Exception: " + x);
      if (dbCurrent != null){
          dbCurrent.debug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
          try {
            dbCurrent.rollback();
          }
          catch (SQLException sx) {
            dbCurrent.debug(D.EBUG_ERR, "rollback failed " + sx);
          }
      }else{
        D.ebug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
      }
      D.ebug(D.EBUG_DETAIL, "GBL8004 complete - error");
      throw mx;      
    }
    finally {
      // All ResultSets are processed, dispose of the SQL statement
      if (rs != null) {
        try {
          rs.close();
        }
        catch (SQLException x) {
          D.ebug(D.EBUG_ERR, "rs close failure in remoteGBL8004");
        }
        rs = null;
      }
      if (dbCurrent != null) {
        dbCurrent.freeStatement();
        c_dbpOPICM.freeConnection(dbCurrent);
      }
      /*if (mx != null) {
        D.ebug(D.EBUG_DETAIL, "GBL8004 complete - error");
        throw mx;
      } else {
        D.ebug(D.EBUG_DETAIL, "GBL8004 complete");
        // return the result vector (or void)
        return rdrsgReturn;
      }*/
    }
    D.ebug(D.EBUG_DETAIL, "GBL8004 complete");
    // return the result vector (or void)
    return rdrsgReturn;
  }

}
